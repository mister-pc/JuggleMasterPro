/*
 * @(#)ExportJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.io;

import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.window.JDialogWindowListener;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ExportJDialog extends JDialog {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPiOType
	 * @param bolPjMPFile
	 * @param strPfileDialogTitle
	 */
	public ExportJDialog(ControlJFrame objPcontrolJFrame, byte bytPiOType, byte bytPfileExtension, String strPfileDialogTitle) {
		super(objPcontrolJFrame, strPfileDialogTitle, true);

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setTextArea(bytPiOType, bytPfileExtension);

		this.setSize();

		this.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this, true));
		this.setVisible(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	private void setSize() {
		this.objGcontrolJFrame.setWindowBounds(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPiOType
	 * @param bolPjMPFile
	 */
	private void setTextArea(byte bytPiOType, byte bytPfileExtension) {

		final JTextArea objLjTextArea =
										new JTextArea(25, Math.max(	135,
																	this.objGcontrolJFrame	.getLanguageString(Language.intS_MESSAGE_SEPARATOR_LINE)
																							.length()));

		switch (bytPiOType) {
			case WriteFileJMenuItem.bytS_PATTERNS_FILE:
			case WriteFileJMenuItem.bytS_SITESWAPS_FILE:
				objLjTextArea.setText(this.objGcontrolJFrame.getPatternsManager()
															.toFileString(	Constants.bytS_UNCLASS_CURRENT,
																			bytPfileExtension,
																			true,
																			bytPiOType == WriteFileJMenuItem.bytS_PATTERNS_FILE,
																			true,
																			this.objGcontrolJFrame.objGobjectsJList.intGfilteredObjectIndexA,
																			this.objGcontrolJFrame.objGshortcutsJComboBox.intGfilteredShortcutIndexA,
																			this.objGcontrolJFrame.getLanguage()));
				break;
			case WriteFileJMenuItem.bytS_STYLES_FILE:
				objLjTextArea.setText(this.objGcontrolJFrame.getPatternsManager().toFileString(	Constants.bytS_UNCLASS_CURRENT,
																								bytPfileExtension,
																								false,
																								true,
																								false,
																								null,
																								null,
																								this.objGcontrolJFrame.getLanguage()));
				break;
			case WriteFileJMenuItem.bytS_NEW_PATTERNS_FILE:
				objLjTextArea.setText(this.objGcontrolJFrame.getLanguageString(bytPfileExtension == Constants.bytS_EXTENSION_JMP
																																? Language.intS_MESSAGE_PATTERNS_JMP_FILE_HEADER
																																: bytPfileExtension == Constants.bytS_EXTENSION_JM
																																													? Language.intS_MESSAGE_PATTERNS_JM_FILE_HEADER
																																													: bytPfileExtension == Constants.bytS_EXTENSION_JAP
																																																										? Language.intS_MESSAGE_PATTERNS_JAP_FILE_HEADER
																																																										: Constants.bytS_UNCLASS_NO_VALUE));
				break;
			case WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE:
				objLjTextArea.setText(Language.getNewLanguageString(bytPiOType == WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE));
				break;
			case WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE:
				objLjTextArea.setText(this.objGcontrolJFrame.getLanguage().getLanguageString());
				break;
		}

		objLjTextArea.setFont(new Font("Courier", Font.PLAIN, 11));
		objLjTextArea.setCaretPosition(0);
		objLjTextArea.setLineWrap(false);
		objLjTextArea.setOpaque(true);
		objLjTextArea.setEditable(false);

		final JScrollPane objLscrollPane =
											new JScrollPane(objLjTextArea,
															ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
															ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		objLscrollPane.setOpaque(true);
		objLscrollPane.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.add(objLscrollPane);
		this.validate();
		this.pack();
	}
}

/*
 * @(#)ExportJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
