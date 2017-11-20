/*
 * @(#)ExtendedJFileChooser.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

// import static java.lang.Math.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ExtendedJFileChooser extends JFileChooser {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public static String		strS_LOADING_PATH;

	public static String		strS_SAVING_PATH;

	private final Window		objGcenteringReferenceWindow;

	private final ControlJFrame	objGcontrolJFrame;

	private final JFrame		objGparentJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPtitle
	 * @param bolPsave
	 * @param objPfileFilterA
	 */
	public ExtendedJFileChooser(ControlJFrame objPcontrolJFrame,
								String strPtitle,
								Window objPcenteringReferenceWindow,
								boolean bolPsave,
								javax.swing.filechooser.FileFilter... objPfileFilterA) {

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGcenteringReferenceWindow = objPcenteringReferenceWindow;
		try {
			ExtendedJFileChooser.strS_LOADING_PATH =
														ExtendedJFileChooser.strS_SAVING_PATH =
																								new File(	objPcontrolJFrame.getJuggleMasterPro().strS_CODE_BASE,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA]).getAbsolutePath();
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while setting default paths");
		}

		this.objGparentJFrame = new JFrame();
		this.objGparentJFrame.setIconImage(this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
		this.setDialogTitle(strPtitle);
		this.setAcceptAllFileFilterUsed(true);
		this.setFont(this.objGcontrolJFrame.getFont());

		for (final javax.swing.filechooser.FileFilter objLfileFilter : objPfileFilterA) {
			this.addChoosableFileFilter(objLfileFilter);
		}
		if (objPfileFilterA.length > 0) {
			this.setFileFilter(objPfileFilterA[0]);
		}
		this.setFileView(new ExtendedFileView(objPcontrolJFrame));
		this.setCurrentDirectory(new File(bolPsave ? ExtendedJFileChooser.strS_SAVING_PATH : ExtendedJFileChooser.strS_LOADING_PATH));
		this.setDialogType(bolPsave ? JFileChooser.SAVE_DIALOG : JFileChooser.OPEN_DIALOG);

		if (!Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)) {
			for (final Component objLcomponent : this.getComponents()) {
				final Class<?> objLclass = objLcomponent.getClass();
				for (final Class<?> objLinterface : objLclass.getInterfaces()) {
					if (objLinterface.getName() == "ItemSelectable") {
						((JComponent) objLcomponent).setToolTipText(null);
					}
				}
			}
		}
		// ExtendedJFileChooser.setNewFolderButtonEnable(this, !bolPsave);
	}

	// final private static boolean setNewFolderButtonEnable(Container objPcontainer, boolean bolPenabled) {
	// int intLcomponentsNumber = objPcontainer.getComponentCount();
	// Icon objLnewFolderIcon = UIManager.getIcon("FileChooser.newFolderIcon");
	// for (int intLcomponentIndex = 0; intLcomponentIndex < intLcomponentsNumber; intLcomponentIndex++) {
	// Component objLcomponent = objPcontainer.getComponent(intLcomponentIndex);
	// if (objLcomponent instanceof JButton) {
	// Icon objLicon = ((JButton) objLcomponent).getIcon();
	// if (objLicon != null && objLicon == objLnewFolderIcon)
	// ((JButton) objLcomponent).setEnabled(false);
	// return true;
	// } else if (objLcomponent instanceof Container) {
	// if (ExtendedJFileChooser.setNewFolderButtonEnable((Container) objLcomponent, bolPenabled)) {
	// return true;
	// }
	// }
	// }
	// return false;
	// }

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPparentComponent
	 * @return
	 * @throws HeadlessException
	 */
	@Override final protected JDialog createDialog(Component objPparentComponent) throws HeadlessException {
		final JDialog objLjDialog = super.createDialog(null);
		objLjDialog.validate();
		objLjDialog.pack();
		this.objGcontrolJFrame.setWindowBounds(objLjDialog, this.objGcenteringReferenceWindow, this.objGcenteringReferenceWindow != null);
		return objLjDialog;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPiOType
	 * @param strPselectedFile
	 * @param strPextensionDescriptionA
	 * @return
	 */
	final public byte getSelectedExtensionType(byte bytPiOType, String strPselectedFile, String[] strPextensionDescriptionA) {

		final String strLdescription = this.getFileFilter().getDescription();
		final String strLselectedFile = strPselectedFile.toLowerCase();

		switch (bytPiOType) {
			case WriteFileJMenuItem.bytS_PATTERNS_FILE:
			case WriteFileJMenuItem.bytS_SITESWAPS_FILE:
			case WriteFileJMenuItem.bytS_STYLES_FILE:
			case WriteFileJMenuItem.bytS_NEW_PATTERNS_FILE:
				if (strLdescription.equals(strPextensionDescriptionA[Constants.bytS_EXTENSION_JM])) {
					return Constants.bytS_EXTENSION_JM;
				} else if (strLdescription.equals(strPextensionDescriptionA[Constants.bytS_EXTENSION_JMP])) {
					return Constants.bytS_EXTENSION_JMP;
				} else if (strLdescription.equals(strPextensionDescriptionA[Constants.bytS_EXTENSION_JAP])) {
					return Constants.bytS_EXTENSION_JAP;
				} else if (strLselectedFile.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM]))) {
					return Constants.bytS_EXTENSION_JM;
				} else {
					return Constants.bytS_UNCLASS_NO_VALUE;
				}
			case WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE:
				if (strLdescription.equals(strPextensionDescriptionA[Constants.bytS_EXTENSION_LANG_INI])
					&& strLselectedFile.equals(Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_LANG_INI])) {
					return Constants.bytS_EXTENSION_LANG_INI;
				}
				return Constants.bytS_EXTENSION_INI;
			case WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE:
				if (strLdescription.equals(strPextensionDescriptionA[Constants.bytS_EXTENSION_JPG])) {
					if (strLselectedFile.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPEG]))) {
						return Constants.bytS_EXTENSION_JPEG;
					}
					return Constants.bytS_EXTENSION_JPG;
				} else if (strLdescription.equals(strPextensionDescriptionA[Constants.bytS_EXTENSION_BMP])) {
					return Constants.bytS_EXTENSION_BMP;
				} else if (strLdescription.equals(strPextensionDescriptionA[Constants.bytS_EXTENSION_PNG])) {
					return Constants.bytS_EXTENSION_PNG;
				} else if (strLselectedFile.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPG]))) {
					return Constants.bytS_EXTENSION_JPG;
				} else if (strLselectedFile.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPEG]))) {
					return Constants.bytS_EXTENSION_JPEG;
				} else if (strLselectedFile.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_BMP]))) {
					return Constants.bytS_EXTENSION_BMP;
				} else {
					return Constants.bytS_EXTENSION_PNG;
				}
			case WriteFileJMenuItem.bytS_SCREEN_PLAY_FILE:
				return Constants.bytS_EXTENSION_GIF;

		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public int showOpenDialog() {
		this.setMultiSelectionEnabled(true);
		return this.showOpenDialog(this.objGcontrolJFrame);
		// return this.showOpenDialog(this.objGparentJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public int showSaveDialog() {
		this.setMultiSelectionEnabled(false);
		return this.showSaveDialog(this.objGcontrolJFrame);
		// return this.showSaveDialog(this.objGparentJFrame);
	}
}

/*
 * @(#)ExtendedJFileChooser.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
