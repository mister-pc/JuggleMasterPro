package fr.jugglemaster.control.util;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.help.LinksJMenuItemActionListener;
import fr.jugglemaster.control.io.ExtendedMenuComponentObject;
import fr.jugglemaster.control.io.PatternsFileJCheckBoxMenuItem;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

final public class MenusTools {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPjMenu
	 * @param intPmenuType
	 * @param bolPlinks
	 * @return
	 */
	final public static boolean doCreateComponentsMenu(	ControlJFrame objPcontrolJFrame,
														JMenu objPjMenu,
														String strPfilePath,
														int intPmenuType,
														boolean bolPlinks) {

		final ArrayList<ArrayList<String>> strLlinkIconAndComponentALAL =
																			MenusTools.getJMenuComponentStringALAL(	strPfilePath,
																													intPmenuType,
																													bolPlinks);
		if (strLlinkIconAndComponentALAL.size() > 0) {
			final ArrayList<ExtendedMenuComponentObject> objLfullJMenuComponentAL = new ArrayList<ExtendedMenuComponentObject>(1);

			final ArrayList<String> strLlinkReferenceAL = strLlinkIconAndComponentALAL.get(0);
			final ArrayList<String> strLiconReferenceAL = strLlinkIconAndComponentALAL.get(1);
			final ArrayList<String> strLmenuComponentFullAL = strLlinkIconAndComponentALAL.get(2);
			final int intLmenuComponentsLinesNumber = strLmenuComponentFullAL.size();

			// Loop each menu line :
			for (int intLmenuComponentsLineIndex = 0; intLmenuComponentsLineIndex < intLmenuComponentsLinesNumber; ++intLmenuComponentsLineIndex) {

				final String strLlinkReference = strLlinkReferenceAL.get(intLmenuComponentsLineIndex);
				final String strLiconReference = strLiconReferenceAL.get(intLmenuComponentsLineIndex);
				final ArrayList<String> strLmenuComponentSingleAL = new ArrayList<String>(1);

				final String strLmenuComponentFull = strLmenuComponentFullAL.get(intLmenuComponentsLineIndex);
				int intLcomponentsSeparatorIndex = strLmenuComponentFull.indexOf(Strings.strS_SEPARATOR);
				if (intLcomponentsSeparatorIndex == -1) {
					intLcomponentsSeparatorIndex = strLmenuComponentFull.length() - Strings.strS_SEPARATOR.length();
				}
				StringBuilder objLmenuComponentFullStringBuilder =
																	new StringBuilder(strLmenuComponentFull	.substring(	0,
																														intLcomponentsSeparatorIndex
																															+ Strings.strS_SEPARATOR.length())
																											.trim());

				// Token the full component string :
				intLcomponentsSeparatorIndex = objLmenuComponentFullStringBuilder.indexOf("->");
				while (intLcomponentsSeparatorIndex != -1) {
					if (intLcomponentsSeparatorIndex > 0) {
						strLmenuComponentSingleAL.add(objLmenuComponentFullStringBuilder.substring(0, intLcomponentsSeparatorIndex).trim());
					}
					objLmenuComponentFullStringBuilder.delete(0, intLcomponentsSeparatorIndex + 2);
					objLmenuComponentFullStringBuilder = new StringBuilder(objLmenuComponentFullStringBuilder.toString().trim());
					intLcomponentsSeparatorIndex = objLmenuComponentFullStringBuilder.indexOf("->");
				}
				strLmenuComponentSingleAL.add(objLmenuComponentFullStringBuilder.toString());
				objLmenuComponentFullStringBuilder = null;
				int intLmenuComponentsNumber = strLmenuComponentSingleAL.size();
				final boolean bolLfinalMenuItemComponent = strLmenuComponentSingleAL.get(intLmenuComponentsNumber - 1).length() > 0;
				if (!bolLfinalMenuItemComponent) {
					--intLmenuComponentsNumber;
				}

				// Add the menu component object to the tree :
				ArrayList<ExtendedMenuComponentObject> objLmenuComponentCursorAL = objLfullJMenuComponentAL;
				boolean bolLnewMenuComponent = false;

				for (int intLmenuComponentIndex = 0; intLmenuComponentIndex < intLmenuComponentsNumber; ++intLmenuComponentIndex) {
					final String strLmenuComponent = strLmenuComponentSingleAL.get(intLmenuComponentIndex);
					final boolean bolLfinalComponent = (intLmenuComponentIndex == intLmenuComponentsNumber - 1);
					bolLnewMenuComponent =
											bolLnewMenuComponent
												|| bolLfinalComponent
												|| objLmenuComponentCursorAL.size() == 0
												|| !strLmenuComponent.equals(objLmenuComponentCursorAL	.get(objLmenuComponentCursorAL.size() - 1)
																										.getLabelString());

					if (bolLnewMenuComponent) {
						final ExtendedMenuComponentObject objLnewMenuComponentObject =
																						new ExtendedMenuComponentObject(bolLfinalComponent
																																			? strLlinkReference
																																			: null,
																														bolLfinalComponent
																																			? strLiconReference
																																			: null,
																														strLmenuComponent,
																														bolLfinalComponent
																															&& bolLfinalMenuItemComponent);
						objLmenuComponentCursorAL.add(objLnewMenuComponentObject);
						objLmenuComponentCursorAL = objLnewMenuComponentObject.getSubMenuAL();
					} else {
						objLmenuComponentCursorAL = objLmenuComponentCursorAL.get(objLmenuComponentCursorAL.size() - 1).getSubMenuAL();
					}
				}
			}

			// Transform the menu item vector into menu items :
			MenusTools.doCreateRecursiveMenuJComponents(objPcontrolJFrame,
														objPjMenu,
														strPfilePath,
														objLfullJMenuComponentAL,
														intPmenuType,
														Strings.strS_EMPTY);
			return intLmenuComponentsLinesNumber > 0;
		}
		return false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPfatherJMenu
	 * @param objPcomponentObjectAL
	 * @param intPmenuType
	 * @param strPjugglePanelTitle
	 */
	final public static void doCreateRecursiveMenuJComponents(	ControlJFrame objPcontrolJFrame,
																JMenu objPfatherJMenu,
																String strPdataFolder,
																ArrayList<ExtendedMenuComponentObject> objPcomponentObjectAL,
																int intPmenuType,
																String strPjugglePanelTitle) {

		final StringBuilder objLcontrolPanelTitleStringBuilder = new StringBuilder(64);
		for (final ExtendedMenuComponentObject objLmenuComponentObject : objPcomponentObjectAL) {

			// Setting the panel title :
			objLcontrolPanelTitleStringBuilder.setLength(0);
			objLcontrolPanelTitleStringBuilder.append(Strings.undoubleSpaceTrim(objLmenuComponentObject.getLabelString()));
			if (intPmenuType == Constants.intS_FILE_TEXT_PATTERNS && strPjugglePanelTitle.length() > 0) {
				objLcontrolPanelTitleStringBuilder.insert(0, " - ");
				objLcontrolPanelTitleStringBuilder.insert(0, strPjugglePanelTitle);
			}

			if (objLmenuComponentObject.isMenuItem()) {

				// Getting the icon image :
				ImageIcon icoL = null;
				if (objLmenuComponentObject.getIconReferenceString() != null
					&& !objLmenuComponentObject.getIconReferenceString().equals(Strings.strS_SEPARATOR)) {
					icoL =
							objPcontrolJFrame.getJuggleMasterPro().getImageIcon(strPdataFolder,
																				objLmenuComponentObject.getIconReferenceString(),
																				Constants.bytS_UNCLASS_NO_VALUE);
				}
				if (intPmenuType == Constants.intS_FILE_TEXT_PATTERNS && icoL == null) {
					icoL =
							objPcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DEFAULT_PATTERNS,
																				Constants.bytS_UNCLASS_NO_VALUE);
				}

				// Creating a new menu item :
				final boolean bolLseparator = objLmenuComponentObject.isSeparator();
				final String strLlinkReference = objLmenuComponentObject.getLinkReferenceString();
				JMenuItem objLnewJMenuItem = null;
				if (intPmenuType == Constants.intS_FILE_TEXT_PATTERNS) {
					objLnewJMenuItem =
										new PatternsFileJCheckBoxMenuItem(	objPcontrolJFrame,
																			objLcontrolPanelTitleStringBuilder.toString(),
																			objLmenuComponentObject.getLabelString(),
																			Strings.doConcat(strPdataFolder, strLlinkReference),
																			icoL);
				} else {
					objLnewJMenuItem = new JMenuItem();
				}
				MenusTools.setNoMnemonicLabel(objLnewJMenuItem, objLmenuComponentObject.getLabelString());
				MenusTools.setMenuMnemonic(objLnewJMenuItem, objLmenuComponentObject.getLabelString());

				// Set menu item icon :
				objLnewJMenuItem.setIcon(icoL);
				objLnewJMenuItem.setFont(objPcontrolJFrame.getFont());

				// Add a listener :
				if (strLlinkReference != null) {
					switch (intPmenuType) {
						case Constants.intS_FILE_TEXT_VIDEO:
						case Constants.intS_FILE_TEXT_LINKS:
							objLnewJMenuItem.addActionListener(new LinksJMenuItemActionListener(objPcontrolJFrame,
																								objLnewJMenuItem,
																								strLlinkReference,
																								intPmenuType == Constants.intS_FILE_TEXT_VIDEO));
							objLnewJMenuItem.setToolTipText(strLlinkReference.replace("%20", Strings.strS_SPACE).replace("%e9", "�"));
							break;
						case Constants.intS_FILE_TEXT_RECORDS:
							objLnewJMenuItem.setEnabled(false);
							objLnewJMenuItem.addActionListener(new LinksJMenuItemActionListener(objPcontrolJFrame,
																								objLnewJMenuItem,
																								Strings.strS_EMPTY,
																								intPmenuType == Constants.intS_FILE_TEXT_RECORDS));
							break;
					}
				}

				if (bolLseparator) {
					objPfatherJMenu.addSeparator();
				} else {
					objPfatherJMenu.add(objLnewJMenuItem);
				}
			} else {

				// Create new sub-menu :
				final JMenu objLsubJMenu = new JMenu();
				final String strLmenu = Strings.doConcat(objLmenuComponentObject.getLabelString(), Strings.strS_SPACES);
				MenusTools.setNoMnemonicLabel(objLsubJMenu, strLmenu);
				MenusTools.setMenuMnemonic(objLsubJMenu, strLmenu);
				objLsubJMenu.setFont(objPcontrolJFrame.getFont());
				if (objLmenuComponentObject.getIconReferenceString() != null) {
					objLsubJMenu.setIcon(objPcontrolJFrame.getJuggleMasterPro().getImageIcon(	strPdataFolder,
																								objLmenuComponentObject.getIconReferenceString(),
																								Constants.bytS_UNCLASS_NO_VALUE,
																								Strings.strS_EMPTY));
				}
				MenusTools.doCreateRecursiveMenuJComponents(objPcontrolJFrame,
															objLsubJMenu,
															strPdataFolder,
															objLmenuComponentObject.getSubMenuAL(),
															intPmenuType,
															objLcontrolPanelTitleStringBuilder.toString());
				objPfatherJMenu.add(objLsubJMenu);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPlinksType
	 * @param bolPlinks
	 * @return
	 */
	final public static ArrayList<ArrayList<String>> getJMenuComponentStringALAL(String strPfilePath, int intPlinksType, boolean bolPlinks) {

		final ArrayList<ArrayList<String>> strLlinkIconAndJLabelALAL = new ArrayList<ArrayList<String>>(3);

		// Open the file :
		final BufferedReader objLbufferedReader = Tools.getBufferedReader(Strings.doConcat(strPfilePath,
		// this.objGjuggleMasterPro.strGcodeBase,
																							// Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA],
																							// this.objGjuggleMasterPro.chrGpathSeparator,
																							// this.getLanguageString(JuggleLanguage.intS_LANGUAGE_ISO_639_1_CODE),
																							// this.objGjuggleMasterPro.chrGpathSeparator,
																							Constants.strS_FILE_NAME_A[intPlinksType]));
		if (objLbufferedReader == null) {
			return strLlinkIconAndJLabelALAL;
		}

		// Local initializations :
		boolean bolLmenuComponentAdded = false;
		final StringBuilder objLfileLineStringBuilder = new StringBuilder(256);
		final ArrayList<String> strLlinkReferenceAL = new ArrayList<String>(1);
		final ArrayList<String> strLiconReferenceAL = new ArrayList<String>(1);
		final ArrayList<String> strLmenuComponentFullJLabelAL = new ArrayList<String>(1);

		// Analyze each file line :
		while (true) {

			// Get the file line :
			try {
				objLfileLineStringBuilder.setLength(0);
				objLfileLineStringBuilder.append(Strings.uncommentUntabTrim(objLbufferedReader.readLine()));
			} catch (final Throwable objPthrowable) {
				break;
			}
			if (objLfileLineStringBuilder.length() == 0) {
				continue;
			}

			// Extracting the link reference :
			final ArrayList<Object> strLtokenAL = Strings.getTokens(objLfileLineStringBuilder, bolPlinks ? 3 : 2, false);

			// Adding the link reference and label :
			final String strLlinkReference = (String) strLtokenAL.get(0);

			strLlinkReferenceAL.add(bolPlinks && strLlinkReference.length() > 0 ? strLlinkReference : null);
			final String strLiconReference = (String) strLtokenAL.get(bolPlinks ? 1 : 0);
			strLiconReferenceAL.add(strLiconReference.length() > 0 ? strLiconReference : null);
			strLmenuComponentFullJLabelAL.add((String) strLtokenAL.get(bolPlinks ? 2 : 1));
			bolLmenuComponentAdded = true;
		}

		// Close the file :
		try {
			objLbufferedReader.close();
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while closing file descriptor");
		}

		if (bolLmenuComponentAdded) {
			strLlinkIconAndJLabelALAL.add(strLlinkReferenceAL);
			strLlinkIconAndJLabelALAL.add(strLiconReferenceAL);
			strLlinkIconAndJLabelALAL.add(strLmenuComponentFullJLabelAL);
		}
		return strLlinkIconAndJLabelALAL;
	}

	final public static String getNoMnemonicLabel(String strPlabel) {
		if (strPlabel == null) {
			return Strings.strS_EMPTY;
		}
		final char[] chrLlabelA = strPlabel.toCharArray();
		final StringBuilder strLmenuItemLabel = new StringBuilder(chrLlabelA.length);
		boolean bolLampersand = false;
		for (final char chrLlabel : chrLlabelA) {
			switch (chrLlabel) {
				case '&':
					if (bolLampersand) {
						strLmenuItemLabel.append('&');
						bolLampersand = false;
					} else {
						bolLampersand = true;
					}
					break;
				default:
					strLmenuItemLabel.append(chrLlabel);
					bolLampersand = false;
			}
		}
		return strLmenuItemLabel.toString();
	}

	/**
	 * @param objPjMenuItem
	 * @param languageString
	 * @return
	 */
	final public static void setMenuMnemonic(JMenuItem objPjMenuItem, String strPmenuLabel) {

		if (strPmenuLabel != null) {
			final String strLlanguage = strPmenuLabel.replace("&&", Strings.strS_EMPTY);
			if (strLlanguage.length() > 0) {
				char chrLmnemonic = Strings.chrS_UNCLASS_NULL_CHAR;
				final int intLampIndex = strLlanguage.indexOf('&');
				if (intLampIndex != -1 && intLampIndex - 1 < strLlanguage.length()) {
					chrLmnemonic = Character.toTitleCase(strLlanguage.charAt(intLampIndex + 1));
				} else {
					chrLmnemonic = Strings.chrS_UNCLASS_NULL_CHAR;
				}
				if (chrLmnemonic != Strings.chrS_UNCLASS_NULL_CHAR) {
					objPjMenuItem.setMnemonic(chrLmnemonic);
				}
				int intLmnemonic = KeyEvent.VK_UNDEFINED;
				switch (chrLmnemonic) {
					case '&':
						intLmnemonic = KeyEvent.VK_AMPERSAND;
						break;
					case 'A':
					case 'á':
					case 'Á':
					case 'à':
					case 'À':
					case 'â':
					case 'Â':
					case 'ã':
					case 'Ã':
					case 'ä':
					case 'Ä':
					case 'æ':
					case 'Æ':
						intLmnemonic = KeyEvent.VK_A;
						break;
					case 'B':
						intLmnemonic = KeyEvent.VK_B;
						break;
					case 'C':
					case 'ç':
					case 'Ç':
						intLmnemonic = KeyEvent.VK_C;
						break;
					case 'D':
						intLmnemonic = KeyEvent.VK_D;
						break;
					case 'E':
					case 'é':
					case '\u00c9':
					case 'è':
					case 'È':
					case 'ê':
					case 'Ê':
					case 'ë':
					case 'Ë':
						intLmnemonic = KeyEvent.VK_E;
						break;
					case 'F':
						intLmnemonic = KeyEvent.VK_F;
						break;
					case 'G':
						intLmnemonic = KeyEvent.VK_G;
						break;
					case 'H':
						intLmnemonic = KeyEvent.VK_H;
						break;
					case 'I':
					case 'í':
					case 'Í':
					case 'ì':
					case 'Ì':
					case 'î':
					case 'Î':
						intLmnemonic = KeyEvent.VK_I;
						break;
					case 'J':
						intLmnemonic = KeyEvent.VK_J;
						break;
					case 'K':
						intLmnemonic = KeyEvent.VK_K;
						break;
					case 'L':
						intLmnemonic = KeyEvent.VK_L;
						break;
					case 'M':
						intLmnemonic = KeyEvent.VK_M;
						break;
					case 'N':
					case 'ñ':
					case 'Ñ':
						intLmnemonic = KeyEvent.VK_N;
						break;
					case 'O':
					case 'ó':
					case 'Ó':
					case 'ô':
					case 'ò':
					case 'Ò':
					case 'Ô':
					case 'ö':
					case 'Ö':
					case 'õ':
					case 'Õ':
						intLmnemonic = KeyEvent.VK_O;
						break;
					case 'P':
						intLmnemonic = KeyEvent.VK_P;
						break;
					case 'Q':
						intLmnemonic = KeyEvent.VK_Q;
						break;
					case 'R':
						intLmnemonic = KeyEvent.VK_R;
						break;
					case 'S':
					case 'ß':
						intLmnemonic = KeyEvent.VK_S;
						break;
					case 'T':
						intLmnemonic = KeyEvent.VK_T;
						break;
					case 'U':
					case 'ù':
					case 'Ù':
					case 'ú':
					case 'Ú':
					case 'û':
					case 'Û':
					case 'ü':
					case 'Ü':
						intLmnemonic = KeyEvent.VK_U;
						break;
					case 'V':
						intLmnemonic = KeyEvent.VK_V;
						break;
					case 'W':
						intLmnemonic = KeyEvent.VK_W;
						break;
					case 'X':
						intLmnemonic = KeyEvent.VK_X;
						break;
					case 'Y':
					case 'ý':
					case 'Ý':
						intLmnemonic = KeyEvent.VK_Y;
						break;
					case 'Z':
						intLmnemonic = KeyEvent.VK_Z;
						break;
					default:
						intLmnemonic = KeyEvent.VK_UNDEFINED;
				}
				if (intLmnemonic != KeyEvent.VK_UNDEFINED) {
					objPjMenuItem.setMnemonic(intLmnemonic);
					try {
						objPjMenuItem.setDisplayedMnemonicIndex(intLampIndex);
					} catch (final Throwable objPthrowable) {
						Tools.err("Error while setting a menu mnemonic index for '", strPmenuLabel, '\'');
					}
				}
			} else {
				objPjMenuItem.setMnemonic(KeyEvent.VK_UNDEFINED);
			}
		}
	}

	final public static void setNoMnemonicLabel(JMenuItem objPjMenuItem, String objPlabelString) {

		objPjMenuItem.setText(MenusTools.getNoMnemonicLabel(objPlabelString));
	}
}
