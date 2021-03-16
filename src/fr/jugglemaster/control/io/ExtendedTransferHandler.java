/*
 * @(#)ExtendedTransferHandler.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ExtendedTransferHandler extends TransferHandler {

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean			bolGsiteswaps;

	private final boolean			bolGstyles;

	private final ControlJFrame		objGcontrolJFrame;

	private final TransferHandler	objGtextTransferHandler;

	public ExtendedTransferHandler(ControlJFrame objPcontrolJFrame, boolean bolPsiteswaps, boolean bolPstyles) {
		this(objPcontrolJFrame, bolPsiteswaps, bolPstyles, null);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 */
	public ExtendedTransferHandler(ControlJFrame objPcontrolJFrame, boolean bolPsiteswaps, boolean bolPstyles, TransferHandler objPtextTransferHandler) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGsiteswaps = bolPsiteswaps;
		this.bolGstyles = bolPstyles;
		this.objGtextTransferHandler = objPtextTransferHandler;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPtransferSupport
	 * @return
	 */
	@Override final public boolean canImport(TransferHandler.TransferSupport objPtransferSupport) {

		if (this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
			final DataFlavor[] objLdataFlavorA = objPtransferSupport.getDataFlavors();
			if (objLdataFlavorA.length > 0) {
				if (objLdataFlavorA.length == 1) {
					return this.objGtextTransferHandler != null && objLdataFlavorA[0].equals(DataFlavor.stringFlavor)
							|| objLdataFlavorA[0].equals(DataFlavor.javaFileListFlavor);
				}
				for (final DataFlavor objLdataFlavor : objLdataFlavorA) {
					if (!objLdataFlavor.equals(DataFlavor.javaFileListFlavor)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override final protected Transferable createTransferable(JComponent objPjComponent) {
		return this.objGtextTransferHandler != null && objPjComponent instanceof JTextComponent
																								? new StringSelection(((JTextComponent) objPjComponent).getSelectedText())
																								: null;
	}

	@Override final protected void exportDone(JComponent objPsourceJComponent, Transferable objPdataTransferable, int intPaction) {
		if (intPaction == TransferHandler.MOVE && objPsourceJComponent instanceof JTextComponent
			&& ((JTextComponent) objPsourceJComponent).isEditable()) {
			((JTextComponent) objPsourceJComponent).replaceSelection(Strings.strS_EMPTY);
		}
	}

	@Override final public int getSourceActions(JComponent objPjComponent) {
		return this.objGtextTransferHandler != null ? this.objGtextTransferHandler.getSourceActions(objPjComponent) : TransferHandler.NONE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPtransferSupport
	 * @return
	 */
	@SuppressWarnings("unchecked") @Override final public boolean importData(TransferHandler.TransferSupport objPtransferSupport) {

		if (objPtransferSupport.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return this.objGtextTransferHandler != null && this.objGtextTransferHandler.importData(objPtransferSupport);
		}

		final boolean bolLsiteswaps =
										this.bolGsiteswaps
											|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NO_PATTERN
											|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_JM_PATTERN
											|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_ABSTRACT_LANGUAGE;

		java.util.List<File> objLfilesList = null;
		try {
			objLfilesList = (java.util.List<File>) objPtransferSupport.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		} catch (final Throwable objPthrowable) {
			return false;
		}
		for (int intLfileIndex = 0; intLfileIndex < objLfilesList.size(); ++intLfileIndex) {
			final File objLfile = objLfilesList.get(intLfileIndex);
			final String strLloadedFileName = objLfile.getAbsolutePath();
			this.objGcontrolJFrame.doHidePopUps();
			this.objGcontrolJFrame.setLoadingPatternsFile(true);

			final boolean bolLnewPatternManager =
													(intLfileIndex == 0)
														&& bolLsiteswaps
														&& (objPtransferSupport.getUserDropAction() == TransferHandler.MOVE
															|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NO_PATTERN
															|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_JM_PATTERN || this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_ABSTRACT_LANGUAGE);
			if (bolLnewPatternManager) {

				// Create new pattern manager :
				new PatternsFileJCheckBoxMenuItem(	this.objGcontrolJFrame,
													Strings.getRightDotTrimmedString(objLfile.getName()),
													Strings.strS_EMPTY,
													strLloadedFileName,
													true,
													bolLsiteswaps,
													this.bolGstyles).itemStateChanged(new ItemEvent(this.objGcontrolJFrame.objGpatternsExtendedJMenu,
																									Constants.bytS_UNCLASS_NO_VALUE,
																									null,
																									Constants.bytS_UNCLASS_NO_VALUE));
			} else {

				// Append new styles / patterns to the current pattern manager :
				FileActions.doImportJugglePatternsManager(	this.objGcontrolJFrame,
															strLloadedFileName,
															Strings.getRightDotTrimmedString(objLfile.getName()),
															bolLsiteswaps,
															this.bolGstyles);
			}

			this.objGcontrolJFrame.setLoadingPatternsFile(false);
		}
		return true;
	}
}

/*
 * @(#)ExtendedTransferHandler.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
