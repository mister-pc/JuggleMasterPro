/*
 * @(#)WriteFileJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.io;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.window.ChoiceJDialog;
import jugglemasterpro.control.window.PopUpJDialog;
import jugglemasterpro.engine.window.AnimationJFrame;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class WriteFileJMenuItem extends JMenuItem implements ActionListener {

	final public static byte	bytS_CURRENT_LANGUAGE_FILE		= 5;

	final public static byte	bytS_NEW_DEFAULT_LANGUAGE_FILE	= 9;

	final public static byte	bytS_NEW_EMPTY_LANGUAGE_FILE	= 8;

	final public static byte	bytS_NEW_LANGUAGE_FILE			= 4;

	final public static byte	bytS_NEW_PATTERNS_FILE			= 0;

	final public static byte	bytS_PATTERNS_FILE				= 1;

	final public static byte	bytS_SCREEN_PLAY_FILE			= 7;

	final public static byte	bytS_SCREEN_SHOT_FILE			= 6;

	final public static byte	bytS_SITESWAPS_FILE				= 2;

	final public static byte	bytS_STYLES_FILE				= 3;

	final private static long	serialVersionUID				= Constants.lngS_ENGINE_VERSION_NUMBER;

	private byte				bytGiOType						= Constants.bytS_UNCLASS_NO_VALUE;

	private int					intGiconFileType				= Constants.bytS_UNCLASS_NO_VALUE;

	private int					intGmenuLanguageIndex			= Constants.bytS_UNCLASS_NO_VALUE;

	private int					intGtitleLanguageIndex			= Constants.bytS_UNCLASS_NO_VALUE;

	int							intGtooltipLanguageIndex		= Constants.bytS_UNCLASS_NO_VALUE;

	final private ControlJFrame	objGcontrolJFrame;
	private KeyStroke			objGshortcutKeyStroke			= null;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPiOType
	 * @param bytPiconFileType
	 */
	public WriteFileJMenuItem(ControlJFrame objPcontrolJFrame, byte bytPiOType, int intPiconFileType) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGiOType = bytPiOType;
		this.intGiconFileType = intPiconFileType;

		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setProperties();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("WriteFileJMenuItem.actionPerformed()");
		this.isActionPerformed();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final private byte doChoiceFileType() {
		final ChoiceJDialog objLchoiceJDialog =
												new ChoiceJDialog(	this.objGcontrolJFrame,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_EXPORT_FORMAT),
																	Constants.intS_FILE_ICON_EXPORT,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_JM_FORMAT),
																	Language.intS_TOOLTIP_EXPORT_FILE_IN_JM_FORMAT,
																	Constants.intS_FILE_ICON_JM,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_JMP_FORMAT),
																	Language.intS_TOOLTIP_EXPORT_FILE_IN_JMP_FORMAT,
																	Constants.intS_FILE_ICON_JMP,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_JAP_FORMAT),
																	Language.intS_TOOLTIP_EXPORT_FILE_IN_JAP_FORMAT,
																	Constants.intS_FILE_ICON_JAP,
																	ChoiceJDialog.bytS_SECOND_CHOICE,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_QUESTION_EXPORT_FORMAT));
		objLchoiceJDialog.setVisible(true);
		final byte bytLfileType =
									(objLchoiceJDialog.bytGchoice == ChoiceJDialog.bytS_FIRST_CHOICE
																									? Constants.bytS_EXTENSION_JM
																									: objLchoiceJDialog.bytGchoice == ChoiceJDialog.bytS_SECOND_CHOICE
																																										? Constants.bytS_EXTENSION_JMP
																																										: objLchoiceJDialog.bytGchoice == ChoiceJDialog.bytS_THIRD_CHOICE
																																																											? Constants.bytS_EXTENSION_JAP
																																																											: Constants.bytS_UNCLASS_NO_VALUE);
		objLchoiceJDialog.dispose();
		return bytLfileType;
	}

	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(this.intGiconFileType, 0));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final private String getDialogTitle() {
		return this.objGcontrolJFrame.getLanguageString(this.intGtitleLanguageIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final private String[] getExtensionDescription() {
		final String[] strLextensionDescriptionA = new String[Constants.bytS_FILES_EXTENSIONS_NUMBER];
		for (byte bytLextensionType = 0; bytLextensionType < Constants.bytS_FILES_EXTENSIONS_NUMBER; ++bytLextensionType) {
			switch (bytLextensionType) {
				case Constants.bytS_EXTENSION_LANG_INI:
					strLextensionDescriptionA[bytLextensionType] =
																	Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(Language.intS_LANGUAGE_EXTENSION_DESCRIPTION_INDEX_A[bytLextensionType]),
																						" (",
																						Constants.strS_FILE_EXTENSION_A[bytLextensionType],
																						')');
					break;
				case Constants.bytS_EXTENSION_JPG:
					strLextensionDescriptionA[bytLextensionType] =
																	Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(Language.intS_LANGUAGE_EXTENSION_DESCRIPTION_INDEX_A[bytLextensionType]),
																						" (*.",
																						Constants.strS_FILE_EXTENSION_A[bytLextensionType],
																						", *.",
																						Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPEG],
																						')');
					break;
				default:
					strLextensionDescriptionA[bytLextensionType] =
																	Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(Language.intS_LANGUAGE_EXTENSION_DESCRIPTION_INDEX_A[bytLextensionType]),
																						" (*.",
																						Constants.strS_FILE_EXTENSION_A[bytLextensionType],
																						')');
					break;
			}
		}
		return strLextensionDescriptionA;
	}

	final private FileFilter[] getFileFilterA(String[] strPextensionDescriptionA) {

		FileFilter[] objLfileFilterA = null;
		switch (this.bytGiOType) {
			case WriteFileJMenuItem.bytS_PATTERNS_FILE:
			case WriteFileJMenuItem.bytS_SITESWAPS_FILE:
			case WriteFileJMenuItem.bytS_STYLES_FILE:
			case WriteFileJMenuItem.bytS_NEW_PATTERNS_FILE:
				objLfileFilterA =
									new FileNameExtensionFilter[] {
										new FileNameExtensionFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_JMP],
																	Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JMP]),
										new FileNameExtensionFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_JM],
																	Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM]),
										new FileNameExtensionFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_JAP],
																	Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JAP]) };
				break;

			case WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE:
			case WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE:

				objLfileFilterA =
									new javax.swing.filechooser.FileFilter[] {
										new INILanguageFileFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_LANG_INI]),
										new FileNameExtensionFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_INI],
																	Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_INI]) };
				break;
			case WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE:
				objLfileFilterA =
									new javax.swing.filechooser.FileFilter[] {
										new FileNameExtensionFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_PNG],
																	Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_PNG]),
										new JPGFileFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_JPG]),
										new FileNameExtensionFilter(strPextensionDescriptionA[Constants.bytS_EXTENSION_BMP],
																	Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_BMP]) };
				break;
			case WriteFileJMenuItem.bytS_SCREEN_PLAY_FILE:
				objLfileFilterA =
									new javax.swing.filechooser.FileFilter[] { new FileNameExtensionFilter(	strPextensionDescriptionA[Constants.bytS_EXTENSION_GIF],
																											Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_GIF]) };
				break;
		}
		return objLfileFilterA;
	}

	final public boolean isActionPerformed() {
		this.objGcontrolJFrame.doHidePopUps();

		if (this.bytGiOType == WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE) {
			final ChoiceJDialog objLchoiceJDialog =
													new ChoiceJDialog(	this.objGcontrolJFrame,
																		this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_NEW_LANGUAGE_CHOICE),
																		Constants.intS_FILE_ICON_QUESTION,
																		this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_NEW_DEFAULT_LANGUAGE),
																		Language.intS_TOOLTIP_NEW_DEFAULT_LANGUAGE_MENU,
																		Constants.intS_FILE_ICON_NEW_DEFAULT_LANGUAGE_BW,
																		Constants.intS_FILE_ICON_NEW_DEFAULT_LANGUAGE,
																		this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_NEW_EMPTY_LANGUAGE),
																		Language.intS_TOOLTIP_NEW_EMPTY_LANGUAGE_MENU,
																		Constants.intS_FILE_ICON_NEW_EMPTY_LANGUAGE_BW,
																		Constants.intS_FILE_ICON_NEW_EMPTY_LANGUAGE,
																		ChoiceJDialog.bytS_FIRST_CHOICE,
																		this.objGcontrolJFrame.getLanguageString(Language.intS_QUESTION_CHOOSE_YOUR_NEW_LANGUAGE_FORMAT));
			objLchoiceJDialog.setVisible(true);
			final byte bytLchoice = objLchoiceJDialog.bytGchoice;
			objLchoiceJDialog.dispose();
			switch (bytLchoice) {
				case ChoiceJDialog.bytS_FIRST_CHOICE:
					this.bytGiOType = WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE;
					break;
				case ChoiceJDialog.bytS_SECOND_CHOICE:
					this.bytGiOType = WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE;
					break;
				default:
					return false;
			}
		}
		final boolean bolLdone =
									this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted
																									? this.writeTrustedFile(this.getExtensionDescription())
																									: this.writeUntrustedFile();
		if (this.bytGiOType == WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE
			|| this.bytGiOType == WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE) {
			this.bytGiOType = WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE;
		}
		return bolLdone;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPdeletableFile
	 * @return
	 */
	final private Boolean isDeletionConfirmed(String strPdeletableFile) {
		final ChoiceJDialog objLchoiceJDialog =
												new ChoiceJDialog(	this.objGcontrolJFrame,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_EXISTING_FILE),
																	Constants.intS_FILE_ICON_ALERT,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_YES),
																	Language.intS_TOOLTIP_REPLACE_FILE,
																	Constants.intS_FILE_ICON_OK_BW,
																	Constants.intS_FILE_ICON_OK,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_NO),
																	Language.intS_TOOLTIP_DO_NOT_REPLACE_FILE,
																	Constants.intS_FILE_ICON_CANCEL_BW,
																	Constants.intS_FILE_ICON_CANCEL,
																	ChoiceJDialog.bytS_SECOND_CHOICE,
																	Strings.doConcat(this.objGcontrolJFrame.getLanguageString(	Language.intS_QUESTION_OVERWRITE_EXISTING_FILE_TOKEN_1,
																																strPdeletableFile)),
																	this.objGcontrolJFrame.getLanguageString(	Language.intS_QUESTION_OVERWRITE_EXISTING_FILE_TOKEN_2,
																												strPdeletableFile));
		objLchoiceJDialog.setVisible(true);
		final Boolean bolLconfirmBoolean =
											(objLchoiceJDialog.bytGchoice == ChoiceJDialog.bytS_FIRST_CHOICE
																											? true
																											: objLchoiceJDialog.bytGchoice == ChoiceJDialog.bytS_SECOND_CHOICE
																																												? false
																																												: null);
		objLchoiceJDialog.dispose();
		return bolLconfirmBoolean;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLabel() {
		this.objGcontrolJFrame.setMenuItemLabel(this, this.intGmenuLanguageIndex);
		this.objGcontrolJFrame.setMenuMnemonic(this, this.intGmenuLanguageIndex);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(this.intGtooltipLanguageIndex)
																												: null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPiOType
	 */
	final private void setProperties() {

		switch (this.bytGiOType) {
			case WriteFileJMenuItem.bytS_PATTERNS_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_EXPORT_PATTERNS_FILES;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_EXPORT_PATTERNS_FILES_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_EXPORT_PATTERNS_FILES;
				this.objGshortcutKeyStroke = Constants.keyS_EXPORT_PATTERNS;
				break;
			case WriteFileJMenuItem.bytS_SITESWAPS_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_EXPORT_SITESWAPS_FILES;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_EXPORT_SITESWAPS_FILES_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_EXPORT_SITESWAPS_FILES;
				this.objGshortcutKeyStroke = Constants.keyS_EXPORT_SITESWAPS;
				break;
			case WriteFileJMenuItem.bytS_STYLES_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_EXPORT_STYLES_FILES;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_EXPORT_STYLES_FILES_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_EXPORT_STYLES_FILES;
				this.objGshortcutKeyStroke = Constants.keyS_EXPORT_STYLES;
				break;
			case WriteFileJMenuItem.bytS_NEW_PATTERNS_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_NEW_PATTERNS_FILE;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_NEW_PATTERNS_FILE_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_NEW_PATTERNS_FILE;
				this.objGshortcutKeyStroke = Constants.keyS_NEW_PATTERNS_FILE;
				break;
			case WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_NEW_LANGUAGE_FILE;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_NEW_LANGUAGE_FILE_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_NEW_LANGUAGE_FILE;
				this.objGshortcutKeyStroke = Constants.keyS_NEW_LANGUAGE_FILE;
				break;
			case WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_NEW_EMPTY_LANGUAGE_FILE;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_NEW_EMPTY_LANGUAGE_FILE_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_NEW_EMPTY_LANGUAGE_FILE;
				this.objGshortcutKeyStroke = Constants.keyS_NEW_LANGUAGE_FILE;
				break;
			case WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_NEW_DEFAULT_LANGUAGE_FILE;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_NEW_DEFAULT_LANGUAGE_FILE_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_NEW_DEFAULT_LANGUAGE_FILE;
				this.objGshortcutKeyStroke = Constants.keyS_NEW_LANGUAGE_FILE;
				break;
			case WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_EXPORT_LANGUAGE_FILE;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_EXPORT_LANGUAGE_FILE_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_EXPORT_LANGUAGE_FILE;
				this.objGshortcutKeyStroke = Constants.keyS_CURRENT_LANGUAGE_FILE;
				break;
			case WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_SCREEN_SHOT;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_SCREEN_SHOT_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_SCREEN_SHOT;
				break;
			case WriteFileJMenuItem.bytS_SCREEN_PLAY_FILE:
				this.intGmenuLanguageIndex = Language.intS_MENU_SCREEN_PLAY;
				this.intGtooltipLanguageIndex = Language.intS_TOOLTIP_SCREEN_PLAY_MENU;
				this.intGtitleLanguageIndex = Language.intS_TITLE_SCREEN_PLAY;
				break;
		}

		if (this.objGshortcutKeyStroke != null) {
			this.setAccelerator(this.objGshortcutKeyStroke);
		}
		if (this.bytGiOType != WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE && this.bytGiOType != WriteFileJMenuItem.bytS_SCREEN_PLAY_FILE) {
			this.addActionListener(this);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfileDialogTitle
	 * @param strPextensionDescriptionA
	 */
	final private boolean writeTrustedFile(String[] strPextensionDescriptionA) {

		this.objGcontrolJFrame.setMouseCursorEnabled(false);
		// Set file filters :
		final FileFilter[] objLfileFilterA = this.getFileFilterA(strPextensionDescriptionA);
		if (objLfileFilterA == null) {
			return false;
		}

		// this.objGcontrolJFrame.setMouseCursorEnabled(false);
		final ExtendedJFileChooser objLextendedJFileChooser =
																new ExtendedJFileChooser(	this.objGcontrolJFrame,
																							this.getDialogTitle(),
																							this.bytGiOType == WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE
																																						? this.objGcontrolJFrame
																																						: null,
																							true,
																							objLfileFilterA);
		if (this.bytGiOType == WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE || this.bytGiOType == WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE
			|| this.bytGiOType == WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE
			|| this.bytGiOType == WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE) {
			objLextendedJFileChooser.setSelectedFile(new File("lang.ini"));
		} else if (this.bytGiOType != WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE && this.bytGiOType != WriteFileJMenuItem.bytS_SCREEN_PLAY_FILE) {
			objLextendedJFileChooser.setFileFilter(objLfileFilterA[1]);
		}
		Tools.doSetFont(objLextendedJFileChooser, this.objGcontrolJFrame.getFont());
		this.objGcontrolJFrame.setMouseCursorEnabled(true);
		while (true) {

			// Display file chooser dialog :
			if (objLextendedJFileChooser.showSaveDialog() == JFileChooser.CANCEL_OPTION) {
				return false;
			}

			// Get selected file :
			ExtendedJFileChooser.strS_SAVING_PATH = objLextendedJFileChooser.getSelectedFile().getParent();

			// Get file extension :
			final byte bytLextensionType =
											objLextendedJFileChooser.getSelectedExtensionType(	this.bytGiOType,
																								objLextendedJFileChooser.getSelectedFile().getName(),
																								strPextensionDescriptionA);

			// Append file extension :
			String strLselectedFile = objLextendedJFileChooser.getSelectedFile().getAbsolutePath();
			if (bytLextensionType != Constants.bytS_EXTENSION_LANG_INI && bytLextensionType != Constants.bytS_UNCLASS_NO_VALUE
				&& !strLselectedFile.toLowerCase().endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[bytLextensionType]))) {
				strLselectedFile = Strings.doConcat(strLselectedFile, '.', Constants.strS_FILE_EXTENSION_A[bytLextensionType]);
			}

			// Write file data :
			FileWriter objLnewFileWriter = null;
			try {
				final File objLselectedFile = new File(strLselectedFile);
				if (objLselectedFile.exists()) {
					final Boolean bolLconfirmDeletionBoolean = this.isDeletionConfirmed(strLselectedFile);
					if (bolLconfirmDeletionBoolean == null) {
						return false;
					}
					if (!bolLconfirmDeletionBoolean.booleanValue()) {
						continue;
					}
				}
				this.objGcontrolJFrame.setMouseCursorEnabled(false);
				objLnewFileWriter = new FileWriter(objLselectedFile);
				switch (this.bytGiOType) {
					case WriteFileJMenuItem.bytS_PATTERNS_FILE:
					case WriteFileJMenuItem.bytS_SITESWAPS_FILE:
						objLnewFileWriter.write(this.objGcontrolJFrame	.getPatternsManager()
																		.toFileString(	Constants.bytS_UNCLASS_CURRENT,
																						bytLextensionType,
																						true,
																						this.bytGiOType == WriteFileJMenuItem.bytS_PATTERNS_FILE,
																						true,
																						this.objGcontrolJFrame.objGobjectsJList.intGfilteredObjectIndexA,
																						this.objGcontrolJFrame.objGshortcutsJComboBox.intGfilteredShortcutIndexA,
																						this.objGcontrolJFrame.getLanguage()));
						objLnewFileWriter.close();
						break;
					case WriteFileJMenuItem.bytS_STYLES_FILE:
						objLnewFileWriter.write(this.objGcontrolJFrame.getPatternsManager().toFileString(	Constants.bytS_UNCLASS_INITIAL,
																											bytLextensionType,
																											false,
																											true,
																											false,
																											null,
																											null,
																											this.objGcontrolJFrame.getLanguage()));
						objLnewFileWriter.close();
						break;
					case WriteFileJMenuItem.bytS_NEW_PATTERNS_FILE:
						objLnewFileWriter.write(this.objGcontrolJFrame.getLanguageString(bytLextensionType == Constants.bytS_EXTENSION_JM
																																			? Language.intS_MESSAGE_PATTERNS_JM_FILE_HEADER
																																			: bytLextensionType == Constants.bytS_EXTENSION_JAP
																																																? Language.intS_MESSAGE_PATTERNS_JAP_FILE_HEADER
																																																: Language.intS_MESSAGE_PATTERNS_JMP_FILE_HEADER));
						objLnewFileWriter.close();
						break;
					case WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE:
					case WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE:
					case WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE:
						objLnewFileWriter.write(Language.getNewLanguageString(this.bytGiOType == WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE));
						objLnewFileWriter.close();
						break;
					case WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE:
						objLnewFileWriter.write(this.objGcontrolJFrame.getLanguage().getLanguageString());
						objLnewFileWriter.close();
						break;
					case WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE:
						objLnewFileWriter.close();
						AnimationJFrame objLjuggleMasterProJFrame = this.objGcontrolJFrame.getJuggleMasterPro().getFrame();
						final BufferedImage imgLscreenShotBuffer =
																	Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(objLjuggleMasterProJFrame.getBackgroundWidth(),
																																objLjuggleMasterProJFrame.getBackgroundHeight(),
																																Transparency.OPAQUE);
						final Graphics2D objLscreenShotGraphics2D = (Graphics2D) imgLscreenShotBuffer.getGraphics();

						objLjuggleMasterProJFrame.doPaintFrame(objLscreenShotGraphics2D);
						objLscreenShotGraphics2D.dispose();
						ImageIO.write(imgLscreenShotBuffer, Constants.strS_FILE_EXTENSION_A[bytLextensionType], objLselectedFile);
						break;

					case WriteFileJMenuItem.bytS_SCREEN_PLAY_FILE:
						objLnewFileWriter.close();
						objLjuggleMasterProJFrame = this.objGcontrolJFrame.getJuggleMasterPro().getFrame();
						objLjuggleMasterProJFrame.imgGscreenPlayBuffer =
																			Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(objLjuggleMasterProJFrame.getBackgroundWidth(),
																																		objLjuggleMasterProJFrame.getBackgroundHeight(),
																																		Transparency.OPAQUE);
						this.objGcontrolJFrame.getJuggleMasterPro().objGanimatedGIFEncoder.start(new FileOutputStream(objLselectedFile));
						break;
				}
			} catch (final Throwable objPfirstThrowable) {
				try {
					if (objLnewFileWriter != null) {
						objLnewFileWriter.close();
					}
				} catch (final Throwable objPsecondThrowable) {}
				this.objGcontrolJFrame.setMouseCursorEnabled(true);
				FileActions.doDisplayFileLoadFailurePopUp(this.objGcontrolJFrame, strLselectedFile);
				continue;
			}
			break;
		}
		System.gc();
		this.objGcontrolJFrame.setMouseCursorEnabled(true);
		return true;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfileDialogTitle
	 */
	final private boolean writeUntrustedFile() {

		final byte bytLfileFormat = this.doChoiceFileType();
		if (bytLfileFormat == Constants.bytS_UNCLASS_NO_VALUE) {
			return false;
		}

		int intLsecondLineLanguageStringIndex = Constants.bytS_UNCLASS_NO_VALUE;
		switch (this.bytGiOType) {
			case WriteFileJMenuItem.bytS_PATTERNS_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_PATTERNS_FILE;
				break;
			case WriteFileJMenuItem.bytS_SITESWAPS_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_SITESWAPS_FILE;
				break;
			case WriteFileJMenuItem.bytS_STYLES_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_STYLES_FILE;
				break;
			case WriteFileJMenuItem.bytS_NEW_PATTERNS_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_NEW_PATTERNS_FILE;
				break;
			case WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_NEW_LANGUAGE_FILE;
				break;
			case WriteFileJMenuItem.bytS_NEW_EMPTY_LANGUAGE_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_NEW_EMPTY_LANGUAGE_FILE;
				break;
			case WriteFileJMenuItem.bytS_NEW_DEFAULT_LANGUAGE_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_NEW_DEFAULT_LANGUAGE_FILE;
				break;
			case WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE:
				intLsecondLineLanguageStringIndex = Language.intS_MESSAGE_COPY_THEM_IN_YOUR_LANGUAGE_FILE;
				break;
		}

		new PopUpJDialog(	this.objGcontrolJFrame,
							Constants.bytS_BOOLEAN_GLOBAL_FILE_EXPORT_INFORMATION,
							Constants.intS_FILE_ICON_INFORMATION,
							this.getDialogTitle(),
							this.objGcontrolJFrame.getLanguageString(Language.intS_MESSAGE_SELECT_THESE_LINES_FROM_THIS_TEXT_AREA),
							this.objGcontrolJFrame.getLanguageString(intLsecondLineLanguageStringIndex),
							true);

		new ExportJDialog(this.objGcontrolJFrame, this.bytGiOType, bytLfileFormat, this.getDialogTitle());
		return true;
	}
}

/*
 * @(#)WriteFileJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
