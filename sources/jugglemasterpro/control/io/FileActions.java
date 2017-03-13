package jugglemasterpro.control.io;

import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.window.ChoiceJDialog;
import jugglemasterpro.control.window.PopUpJDialog;
import jugglemasterpro.engine.JuggleMasterPro;
import jugglemasterpro.pattern.PatternsManager;
import jugglemasterpro.pattern.io.PatternsFile;
import jugglemasterpro.pattern.io.PatternsFilesManager;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

final public class FileActions {

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public static void doCheckPatternsMenuItems(final ControlJFrame objPcontrolJFrame) {
		FileActions.doUncheckPatternsMenuItems(objPcontrolJFrame.objGpatternsExtendedJMenu);
		FileActions.doUncheckPatternsMenuItems(objPcontrolJFrame.objGrecentFilesJuggleJMenu);
		final byte bytLpatternsManagerType = objPcontrolJFrame.getPatternsManager().bytGpatternsManagerType;
		if (bytLpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS) {

			final int intLfilesNumber = objPcontrolJFrame.getPatternsManager().getPatternsFileManager().strGreferenceAL.size();
			final PatternsFilesManager objLpatternsFilesParser = objPcontrolJFrame.getPatternsManager().getPatternsFileManager();
			for (int intLfileIndex = 0; intLfileIndex < intLfilesNumber; ++intLfileIndex) {
				final PatternsFileJCheckBoxMenuItem objLpatternsFileJCheckBoxMenuItem =
																						new PatternsFileJCheckBoxMenuItem(	objPcontrolJFrame,
																															objLpatternsFilesParser.strGtitleAL.get(intLfileIndex),
																															null,
																															objLpatternsFilesParser.strGreferenceAL.get(intLfileIndex),
																															intLfileIndex == 0,
																															objLpatternsFilesParser.bolGsiteswapsAL.get(intLfileIndex),
																															objLpatternsFilesParser.bolGstylesAL.get(intLfileIndex));
				FileActions.doCheckPatternsMenuItems(objPcontrolJFrame.objGpatternsExtendedJMenu, objLpatternsFileJCheckBoxMenuItem);
				FileActions.doCheckPatternsMenuItems(objPcontrolJFrame.objGrecentFilesJuggleJMenu, objLpatternsFileJCheckBoxMenuItem);
			}
		}
	}

	public final static void doCheckPatternsMenuItems(final JMenu objPjMenu, final PatternsFileJCheckBoxMenuItem objPpatternsFileJCheckBoxMenuItem) {

		if (objPjMenu != null) {
			final int intLmenuItemsNumber = objPjMenu.getItemCount();

			for (int intLitemIndex = 0; intLitemIndex < intLmenuItemsNumber; intLitemIndex++) {
				final JMenuItem objLjMenuItem = objPjMenu.getItem(intLitemIndex);
				if (objLjMenuItem instanceof PatternsFileJCheckBoxMenuItem) {
					final PatternsFileJCheckBoxMenuItem objLpatternsFileJCheckBoxMenuItem = (PatternsFileJCheckBoxMenuItem) objLjMenuItem;
					if (objLpatternsFileJCheckBoxMenuItem.equals(objPpatternsFileJCheckBoxMenuItem)) {
						objLpatternsFileJCheckBoxMenuItem.select(true);
					}
				} else if (objLjMenuItem instanceof JMenu) {
					FileActions.doCheckPatternsMenuItems((JMenu) objLjMenuItem, objPpatternsFileJCheckBoxMenuItem);
				}
			}
		}
	}

	final public static void doDisplayFileLoadFailurePopUp(final ControlJFrame objPcontrolJFrame, final String strPfileName) {
		objPcontrolJFrame.setMouseCursorEnabled(true);
		new PopUpJDialog(	objPcontrolJFrame,
							Constants.bytS_BOOLEAN_GLOBAL_FILE_ACCESS_ERRORS,
							Constants.intS_FILE_ICON_ERROR,
							objPcontrolJFrame.getLanguageString(Language.intS_TITLE_UNREACHABLE_DATA_SOURCE),
							objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_FILE_TOKEN_UNREACHABLE_OR_UNFOUNDABLE, strPfileName),
							objPcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted
																						? null
																						: objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_APPLET_SECURITY_RESTRICTIONS_WARNING),
							false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfileName
	 * @param strPtitle
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 */
	final public static void doImportJugglePatternsManager(	final ControlJFrame objPcontrolJFrame,
															final String strPfileName,
															final String strPfileTitle,
															final boolean bolPsiteswaps,
															final boolean bolPstyles) {

		final PatternsFile objLpatternsFile = new PatternsFile(null, strPfileName, strPfileTitle);
		final ImageIcon icoL = objPcontrolJFrame.getFileImageIcon(false, bolPsiteswaps, bolPstyles);
		if (objPcontrolJFrame.getPatternsManager().getPatternsFileManager().doLoadPatternsFile(objLpatternsFile,
																								icoL,
																								false,
																								bolPsiteswaps,
																								bolPstyles,
																								null,
																								false,
																								Constants.bytS_UNCLASS_NO_VALUE)) {

			objPcontrolJFrame.objGconsoleJDialog.setContent(objPcontrolJFrame.getPatternsManager().getPatternsFileManager().getTitlesString(), true);
			if (bolPsiteswaps) {
				objPcontrolJFrame.objGobjectsJList.setLists();
				objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
			}
			if (bolPstyles) {
				final int intLselectedStyleIndex = objPcontrolJFrame.objGstyleJComboBox.getSelectedIndex();
				objPcontrolJFrame.objGstyleJComboBox.setList();
				objPcontrolJFrame.objGstyleJComboBox.setSortOrderAscending(true);
				objPcontrolJFrame.objGstyleJComboBox.selectIndex(intLselectedStyleIndex);
			}
			objPcontrolJFrame.objGrecentFilesJuggleJMenu.doAddRecentFile(strPfileName, strPfileTitle, icoL, false, bolPsiteswaps, bolPstyles);
			objPcontrolJFrame.setMenusEnabled();
			FileActions.doCheckPatternsMenuItems(objPcontrolJFrame);
			objPcontrolJFrame.objGobjectsJList.requestFocusInWindow();
		} else {
			FileActions.doDisplayFileLoadFailurePopUp(objPcontrolJFrame, strPfileName);
		}
	}

	final public static boolean doLoadJugglePatternsManager(final ControlJFrame objPcontrolJFrame,
															final byte bytPpatternsManagerType,
															final boolean bolPdisplayPopUp,
															final String strPfileName,
															final String strPtitle,
															final ImageIcon icoP,
															final boolean bolPcancelFilters,
															final boolean bolPsiteswaps,
															final boolean bolPstyles,
															final String strPpattern,
															final int intPpatternOccurence) {
		return FileActions.doLoadJugglePatternsManager(	objPcontrolJFrame,
														objPcontrolJFrame.getJuggleMasterPro(),
														bytPpatternsManagerType,
														bolPdisplayPopUp,
														strPfileName,
														strPtitle,
														icoP,
														bolPcancelFilters,
														bolPsiteswaps,
														bolPstyles,
														strPpattern,
														intPpatternOccurence);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpatternsManagerType
	 * @param strPfileName
	 * @param strPtitle
	 * @return
	 */
	final public static boolean doLoadJugglePatternsManager(final ControlJFrame objPcontrolJFrame,
															final byte bytPpatternsManagerType,
															final String strPfileName,
															final String strPtitle,
															final ImageIcon icoP,
															final boolean bolPcancelFilters) {
		return FileActions.doLoadJugglePatternsManager(	objPcontrolJFrame,
														objPcontrolJFrame.getJuggleMasterPro(),
														bytPpatternsManagerType,
														true,
														strPfileName,
														strPtitle,
														icoP,
														bolPcancelFilters,
														true,
														true,
														null,
														0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpatternsManagerType
	 * @param strPfileName
	 * @param strPtitle
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 * @return
	 */
	final public static boolean doLoadJugglePatternsManager(final ControlJFrame objPcontrolJFrame,
															final byte bytPpatternsManagerType,
															final String strPfileName,
															final String strPtitle,
															final ImageIcon icoP,
															final boolean bolPcancelFilters,
															final boolean bolPsiteswaps,
															final boolean bolPstyles) {
		return FileActions.doLoadJugglePatternsManager(	objPcontrolJFrame,
														objPcontrolJFrame.getJuggleMasterPro(),
														bytPpatternsManagerType,
														true,
														strPfileName,
														strPtitle,
														icoP,
														bolPcancelFilters,
														bolPsiteswaps,
														bolPstyles,
														null,
														0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpatternsManagerType
	 * @param strPfileName
	 * @param strPtitle
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 * @param strPpattern
	 * @param intPpatternOccurence
	 * @return
	 */
	final public static boolean doLoadJugglePatternsManager(final ControlJFrame objPcontrolJFrame,
															final byte bytPpatternsManagerType,
															final String strPfileName,
															final String strPtitle,
															final ImageIcon icoP,
															final boolean bolPcancelFilters,
															final boolean bolPsiteswaps,
															final boolean bolPstyles,
															final String strPpattern,
															final int intPpatternOccurence) {
		return FileActions.doLoadJugglePatternsManager(	objPcontrolJFrame,
														objPcontrolJFrame.getJuggleMasterPro(),
														bytPpatternsManagerType,
														true,
														strPfileName,
														strPtitle,
														icoP,
														bolPcancelFilters,
														bolPsiteswaps,
														bolPstyles,
														strPpattern,
														intPpatternOccurence);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpatternsManagerType
	 * @param strPfileName
	 * @param strPtitle
	 * @param strPpattern
	 * @param intPpatternOccurence
	 * @return
	 */
	final public static boolean doLoadJugglePatternsManager(final ControlJFrame objPcontrolJFrame,
															final byte bytPpatternsManagerType,
															final String strPfileName,
															final String strPtitle,
															final ImageIcon icoP,
															final boolean bolPcancelFilters,
															final String strPpattern,
															final int intPpatternOccurence) {
		return FileActions.doLoadJugglePatternsManager(	objPcontrolJFrame,
														objPcontrolJFrame.getJuggleMasterPro(),
														bytPpatternsManagerType,
														true,
														strPfileName,
														strPtitle,
														icoP,
														bolPcancelFilters,
														true,
														true,
														strPpattern,
														intPpatternOccurence);
	}

	final public static boolean doLoadJugglePatternsManager(final ControlJFrame objPcontrolJFrame,
															final JuggleMasterPro objPjuggleMasterPro,
															final byte bytPpatternsManagerType,
															final boolean bolPdisplayPopUp,
															final String strPfileName,
															final String strPtitle,
															final ImageIcon icoP,
															final boolean bolPcancelFilters,
															final boolean bolPsiteswaps,
															final boolean bolPstyles,
															final String strPpattern,
															final int intPpatternOccurence) {
		if (objPcontrolJFrame != null) {
			objPcontrolJFrame.setJButtonsEnabled(false);
			objPcontrolJFrame.setMouseCursorEnabled(false);
			objPcontrolJFrame.setLoadingPatternsFile(true);
		}
		Tools.debug("FileActions.doLoadJugglePatternsManager(bytPpatternsManagerType = ",
					bytPpatternsManagerType,
					", bolPdisplayPopUp = ",
					bolPdisplayPopUp,
					", strPfileName = ",
					strPfileName,
					", strPtitle = ",
					strPtitle,
					", bolPcancelFilters = ",
					bolPcancelFilters,
					", bolPsiteswaps = ",
					bolPsiteswaps,
					", bolPstyles = ",
					bolPstyles,
					", strPpattern = ",
					strPpattern,
					", intPpatternOccurence = ",
					intPpatternOccurence,
					')');
		PatternsManager objLpatternsManager;
		if (bolPcancelFilters && !bolPsiteswaps && bolPstyles) {
			objLpatternsManager = objPjuggleMasterPro.getNewPatternsManager(Constants.bytS_MANAGER_NEW_PATTERN);
		} else {
			objLpatternsManager =
									objPjuggleMasterPro.getNewPatternsManager(	bytPpatternsManagerType,
																				strPfileName,
																				strPtitle,
																				icoP,
																				bolPsiteswaps,
																				bolPstyles,
																				strPpattern,
																				intPpatternOccurence);
		}
		if (objPcontrolJFrame != null) {
			objPcontrolJFrame.setLoadingPatternsFile(false);
		}

		// Display load file error pop-up :
		if (objLpatternsManager == null) {
			if (bytPpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS && objPcontrolJFrame != null) {
				if (bolPdisplayPopUp) {
					FileActions.doDisplayFileLoadFailurePopUp(objPcontrolJFrame, strPfileName);
				}
				objPcontrolJFrame.setJButtonsEnabled(true);
				objPcontrolJFrame.setMenusEnabled();
			}
			return false;
		}

		/* Setting file data : */
		objPjuggleMasterPro.doStopPattern();

		if (objPcontrolJFrame != null) {
			objPcontrolJFrame.setJugglePatternsManager(objLpatternsManager);
			if (bolPcancelFilters && !bolPsiteswaps && bolPstyles) {
				FileActions.doImportJugglePatternsManager(objPcontrolJFrame, strPfileName, strPtitle, bolPsiteswaps, bolPstyles);
			}
			objPjuggleMasterPro.intGobjectIndex = objLpatternsManager.getPatternsFileManager().intGstartObjectIndex;
			objPjuggleMasterPro.bolGforceExit = false;
			objPcontrolJFrame.objGstyleJComboBox.setSortOrderAscending(true);
			if (bolPcancelFilters) {
				objPcontrolJFrame.objGfiltersJDialog.setFiltersEnabled(false);
			}
			objPcontrolJFrame.objGstyleJComboBox.setList();

			objPcontrolJFrame.objGobjectsJList.setLists();
			objPcontrolJFrame.setFiltersControls();

			objPcontrolJFrame.setJButtonsEnabled(true);
			objPcontrolJFrame.objGrecentFilesJuggleJMenu.doAddRecentFile(strPfileName, strPtitle, icoP, true, bolPsiteswaps, bolPstyles);
			objPcontrolJFrame.setMenusEnabled();
			objPcontrolJFrame.setMouseCursorEnabled(true);

			// Display file syntax error pop-up :
			objPcontrolJFrame.objGconsoleJDialog.setContent(strPfileName, true);
			objPcontrolJFrame.objGobjectsJList.requestFocusInWindow();
		}

		System.gc();
		return true;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public static void doReloadPatternsFiles(final ControlJFrame objPcontrolJFrame) {
		objPcontrolJFrame.doHidePopUps();
		final int intLlastPatternIndex = objPcontrolJFrame.getJuggleMasterPro().intGobjectIndex;

		final PatternsFilesManager objLpatternsFilesParser = objPcontrolJFrame.getPatternsManager().getPatternsFileManager();
		final ArrayList<String> strLreferenceAL = new ArrayList<String>(objLpatternsFilesParser.strGreferenceAL);
		final ArrayList<String> strLtitleAL = new ArrayList<String>(objLpatternsFilesParser.strGtitleAL);
		final ArrayList<ImageIcon> imgLAL = new ArrayList<ImageIcon>(objLpatternsFilesParser.imgGAL);
		final ArrayList<Boolean> bolLsiteswapsAL = new ArrayList<Boolean>(objLpatternsFilesParser.bolGsiteswapsAL);
		final ArrayList<Boolean> bolLstylesAL = new ArrayList<Boolean>(objLpatternsFilesParser.bolGstylesAL);
		final int intLsize = strLreferenceAL.size();

		boolean bolLnewLists = true;
		for (int intLindex = 0; intLindex < intLsize; ++intLindex) {
			if (bolLnewLists) {
				bolLnewLists =
								!FileActions.doLoadJugglePatternsManager(	objPcontrolJFrame,
																			Constants.bytS_MANAGER_FILES_PATTERNS,
																			strLreferenceAL.get(intLindex),
																			strLtitleAL.get(intLindex),
																			imgLAL.get(intLindex),
																			false);
			} else {
				FileActions.doImportJugglePatternsManager(	objPcontrolJFrame,
															strLreferenceAL.get(intLindex),
															strLtitleAL.get(intLindex),
															bolLsiteswapsAL.get(intLindex),
															bolLstylesAL.get(intLindex));
			}
		}
		if (!bolLnewLists) {
			objPcontrolJFrame.doStartJuggling(objPcontrolJFrame.objGobjectsJList.getPatternIndex(objPcontrolJFrame.objGobjectsJList.getFilteredPatternRenewedIndex(	intLlastPatternIndex,
																																									false)));
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPnewLists
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 */
	final public static void doSelectAndLoadFile(	final ControlJFrame objPcontrolJFrame,
													final boolean bolPnewLists,
													final boolean bolPsiteswaps,
													final boolean bolPstyles) {

		objPcontrolJFrame.doHidePopUps();

		// Build the dialog title :
		final String strLtitle =
									objPcontrolJFrame.getLanguageString(bolPnewLists
																					? Language.intS_TITLE_OPEN_FILES
																					: bolPsiteswaps && bolPstyles
																													? Language.intS_TITLE_IMPORT_PATTERNS
																													: bolPsiteswaps
																																	? Language.intS_TITLE_IMPORT_SITESWAPS
																																	: Language.intS_TITLE_IMPORT_STYLES);

		// Display file chooser dialog :
		objPcontrolJFrame.setMouseCursorEnabled(false);
		final ExtendedJFileChooser objLextendedJFileChooser =
																new ExtendedJFileChooser(	objPcontrolJFrame,
																							strLtitle,
																							null,
																							false,
																							new javax.swing.filechooser.FileFilter[] {
																								new FileNameExtensionFilter(Strings.doConcat(	objPcontrolJFrame.getLanguageString(Language.intS_COMBOBOX_JM_OR_JMP_FILES),
																																				" (*.",
																																				Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM],
																																				", *.",
																																				Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JMP],
																																				')'),
																															Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM],
																															Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JMP]),
																								new FileNameExtensionFilter(Strings.doConcat(	objPcontrolJFrame.getLanguageString(Language.intS_COMBOBOX_JMP_FILES),
																																				" (*.",
																																				Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JMP],
																																				')'),
																															Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JMP]),
																								new FileNameExtensionFilter(Strings.doConcat(	objPcontrolJFrame.getLanguageString(Language.intS_COMBOBOX_JM_FILES),
																																				" (*.",
																																				Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM],
																																				')'),
																															Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM]),
																								new FileNameExtensionFilter(Strings.doConcat(	objPcontrolJFrame.getLanguageString(Language.intS_COMBOBOX_JAP_FILES),
																																				" (*.",
																																				Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JAP],
																																				')'),
																															Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JAP]), });
		Tools.doSetFont(objLextendedJFileChooser, objPcontrolJFrame.getFont());
		if (objLextendedJFileChooser.showOpenDialog() == JFileChooser.CANCEL_OPTION) {
			objPcontrolJFrame.setMouseCursorEnabled(true);
			return;
		}

		// Get selected file :
		final File[] objLfileA = objLextendedJFileChooser.getSelectedFiles();
		for (int intLfileIndex = 0; intLfileIndex < objLfileA.length; ++intLfileIndex) {
			if (intLfileIndex == 0) {
				ExtendedJFileChooser.strS_LOADING_PATH = objLfileA[intLfileIndex].getParent();
			}
			final String strLloadedFileName = objLfileA[intLfileIndex].getAbsolutePath();
			final String strLmenu = Strings.getRightDotTrimmedString(objLfileA[intLfileIndex].getName());
			objPcontrolJFrame.setLoadingPatternsFile(true);
			if (intLfileIndex == 0 && bolPnewLists) {

				// Create new pattern manager :
				new PatternsFileJCheckBoxMenuItem(objPcontrolJFrame, strLmenu, Strings.strS_EMPTY, strLloadedFileName).itemStateChanged(bolPnewLists
																																					? new ItemEvent(objPcontrolJFrame.objGpatternsExtendedJMenu,
																																									Constants.bytS_UNCLASS_NO_VALUE,
																																									null,
																																									Constants.bytS_UNCLASS_NO_VALUE)
																																					: null);
			} else {

				// Append new styles / patterns to the current pattern manager :
				FileActions.doImportJugglePatternsManager(objPcontrolJFrame, strLloadedFileName, strLmenu, bolPsiteswaps, bolPstyles);
			}
		}
		objPcontrolJFrame.setLoadingPatternsFile(false);
		objPcontrolJFrame.setMouseCursorEnabled(true);
	}

	final public static void doTakeScreenPlay(ControlJFrame objPcontrolJFrame, boolean bolPactive) {

		final byte bytLanimationStatus = objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus;
		objPcontrolJFrame.getJuggleMasterPro().doPausePattern();

		// Finish capture and close :
		if (!bolPactive) {
			objPcontrolJFrame.getJuggleMasterPro().objGanimatedGIFEncoder.finish();
			objPcontrolJFrame.getJuggleMasterPro().bolGscreenPlay = false;
			objPcontrolJFrame.getJuggleMasterPro().bolGcoloredBorder = false;
			new PopUpJDialog(	objPcontrolJFrame,
								Constants.bytS_BOOLEAN_GLOBAL_SCREEN_PLAY_INFORMATION,
								Constants.intS_FILE_ICON_INFORMATION,
								objPcontrolJFrame.getLanguageString(Language.intS_TITLE_SCREEN_PLAY),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_SCREEN_PLAY_FINISHED),
								null,
								false);
			objPcontrolJFrame.objGstartScreenPlayJMenuItem.setLabel(false);
			objPcontrolJFrame.setMenusEnabled();
			objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus = bytLanimationStatus;
			return;
		}

		// Deal with current capture before starting another :
		final boolean bolLscreenPlay = objPcontrolJFrame.getJuggleMasterPro().bolGscreenPlay;
		if (bolLscreenPlay) {
			objPcontrolJFrame.getJuggleMasterPro().bolGscreenPlay = false;
			final ChoiceJDialog objLchoiceJDialog =
													new ChoiceJDialog(	objPcontrolJFrame,
																		objPcontrolJFrame.getLanguageString(Language.intS_TITLE_ACTIVE_SCREEN_PLAY),
																		Constants.intS_FILE_ICON_ALERT,
																		objPcontrolJFrame.getLanguageString(Language.intS_BUTTON_OK),
																		Language.intS_TOOLTIP_FINISH_SCREEN_PLAY,
																		Constants.intS_FILE_ICON_OK_BW,
																		Constants.intS_FILE_ICON_OK,
																		objPcontrolJFrame.getLanguageString(Language.intS_BUTTON_CANCEL),
																		Language.intS_TOOLTIP_CONTINUE_SCREEN_PLAY,
																		Constants.intS_FILE_ICON_CANCEL_BW,
																		Constants.intS_FILE_ICON_CANCEL,
																		ChoiceJDialog.bytS_SECOND_CHOICE,
																		objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_SCREEN_PLAY_TO_FINISH_BEFORE_STARTING_ANOTHER_ONE_1),
																		objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_SCREEN_PLAY_TO_FINISH_BEFORE_STARTING_ANOTHER_ONE_2));
			objLchoiceJDialog.setVisible(true);
			final byte bytLchoice = objLchoiceJDialog.bytGchoice;
			objLchoiceJDialog.dispose();
			if (bytLchoice != ChoiceJDialog.bytS_FIRST_CHOICE) {
				objPcontrolJFrame.getJuggleMasterPro().bolGscreenPlay = true;
				objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus = bytLanimationStatus;
				return;
			}
			objPcontrolJFrame.getJuggleMasterPro().objGanimatedGIFEncoder.finish();
			objPcontrolJFrame.getJuggleMasterPro().bolGcoloredBorder = false;
			new PopUpJDialog(	objPcontrolJFrame,
								Constants.bytS_BOOLEAN_GLOBAL_SCREEN_PLAY_INFORMATION,
								Constants.intS_FILE_ICON_INFORMATION,
								objPcontrolJFrame.getLanguageString(Language.intS_TITLE_SCREEN_PLAY),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_SCREEN_PLAY_FINISHED),
								null,
								false);
		}

		// Start screen play capture :
		objPcontrolJFrame.getJuggleMasterPro().bolGplayScreenPlaySound = objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SOUNDS);
		final boolean bolLactionPerformed =
											new WriteFileJMenuItem(	objPcontrolJFrame,
																	WriteFileJMenuItem.bytS_SCREEN_PLAY_FILE,
																	Constants.intS_FILE_ICON_SCREEN_PLAY).isActionPerformed();
		objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus = bytLanimationStatus;
		// TODO : start screenPlay !!
		objPcontrolJFrame.getJuggleMasterPro().bolGscreenPlay = bolLactionPerformed;
		objPcontrolJFrame.getJuggleMasterPro().bolGcoloredBorder = bolLactionPerformed;
		objPcontrolJFrame.objGstartScreenPlayJMenuItem.setLabel(bolLactionPerformed);
		objPcontrolJFrame.setMenusEnabled();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public static void doTakeScreenShot(final ControlJFrame objPcontrolJFrame) {
		final byte bytLanimationStatus = objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus;
		objPcontrolJFrame.getJuggleMasterPro().doPausePattern();
		try {
			objPcontrolJFrame.getJuggleMasterPro().bolGcoloredBorder = true;
			objPcontrolJFrame.getJuggleMasterPro().bolGplayScreenShotSound = objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SOUNDS);
			objPcontrolJFrame.getJuggleMasterPro().doPlaySounds();
			new WriteFileJMenuItem(objPcontrolJFrame, WriteFileJMenuItem.bytS_SCREEN_SHOT_FILE, Constants.intS_FILE_ICON_SCREEN_SHOT).actionPerformed(null);
		} catch (final Throwable objPthrowable) {
			new PopUpJDialog(	objPcontrolJFrame,
								Constants.bytS_UNCLASS_NO_VALUE,
								Constants.intS_FILE_ICON_ERROR,
								objPcontrolJFrame.getLanguageString(Language.intS_TITLE_SCREEN_SHOT),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_PHOTO_CAMERA_FAULTY),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_PATTERN_IMAGE_NUMERIZATION_FAILED),
								false);
		}

		objPcontrolJFrame.getJuggleMasterPro().bolGcoloredBorder = false;
		objPcontrolJFrame.getJuggleMasterPro().bolGplayScreenShotSound = false;
		objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus = bytLanimationStatus;
		// if (bytLanimationStatus != Constants.bytS_STATE_JUGGLING) {
		// objPcontrolJFrame.doAddAction(Constants.intS_ACTION_DRAW_SITESWAP);
		// }
	}

	final public static void doUncheckPatternsMenuItems(final JMenu objPjMenu) {
		if (objPjMenu != null) {
			final int intLmenuItemsNumber = objPjMenu.getItemCount();

			for (int intLitemIndex = 0; intLitemIndex < intLmenuItemsNumber; intLitemIndex++) {
				final JMenuItem objLjMenuItem = objPjMenu.getItem(intLitemIndex);
				if (objLjMenuItem instanceof PatternsFileJCheckBoxMenuItem) {
					((PatternsFileJCheckBoxMenuItem) objLjMenuItem).select(false);
				} else if (objLjMenuItem instanceof JMenu) {
					FileActions.doUncheckPatternsMenuItems((JMenu) objLjMenuItem);
				}
			}
		}
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}
