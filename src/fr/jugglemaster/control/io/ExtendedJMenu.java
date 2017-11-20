/*
 * @(#)JuggleJMenu.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.color.ColorActions;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
public class ExtendedJMenu extends JMenu implements MenuListener {

	final public static byte		bytS_CLIPBOARD		= 0;

	final public static byte		bytS_EDITION		= 3;

	final public static byte		bytS_EXPORT			= 8;

	final public static byte		bytS_FILE			= 12;

	final public static byte		bytS_HELP			= 1;

	final public static byte		bytS_IMPORT			= 9;

	final public static byte		bytS_LANGUAGE		= 4;
	final public static byte		bytS_PATTERNS		= 2;
	final public static byte		bytS_RECENT			= 11;
	final public static byte		bytS_RECORDS_LINKS	= 5;
	final public static byte		bytS_SCREEN_PLAY	= 10;
	final public static byte		bytS_VIDEO_LINKS	= 6;
	final public static byte		bytS_WEB_LINKS		= 7;
	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final protected byte			bytGmenuType;
	final protected int				intGiconFileType;
	final protected ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPmenuType
	 * @param bytPiconFileType
	 */
	public ExtendedJMenu(ControlJFrame objPcontrolJFrame, byte bytPmenuType, int intPiconFileType) {
		super(Strings.strS_EMPTY, bytPmenuType == ExtendedJMenu.bytS_PATTERNS || bytPmenuType == ExtendedJMenu.bytS_FILE
									|| bytPmenuType == ExtendedJMenu.bytS_EDITION || bytPmenuType == ExtendedJMenu.bytS_HELP);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGiconFileType = intPiconFileType;
		this.bytGmenuType = bytPmenuType;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.addMenuListener(this);
	}

	final public void doLoadImages() {
		if (this.intGiconFileType != Constants.bytS_UNCLASS_NO_VALUE) {
			this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(this.intGiconFileType, 0));
		}
	}

	@Override public void menuCanceled(MenuEvent e) {}

	@Override public void menuDeselected(MenuEvent objPmenuEvent) {}

	@Override public void menuSelected(MenuEvent objPmenuEvent) {
		ColorActions.doHideColorsChoosers(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLabel() {
		int intLmenuLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		int intLtooltipLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		switch (this.bytGmenuType) {
			case ExtendedJMenu.bytS_CLIPBOARD:
				intLmenuLanguageIndex = Language.intS_MENU_CLIPBOARD;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_CLIPBOARD_MENU;
				break;
			case ExtendedJMenu.bytS_SCREEN_PLAY:
				intLmenuLanguageIndex = Language.intS_MENU_SCREEN_PLAY;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_SCREEN_PLAY_MENU;
				break;
			case ExtendedJMenu.bytS_HELP:
				intLmenuLanguageIndex = Language.intS_MENU_HELP;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_HELP_MENU;
				break;
			case ExtendedJMenu.bytS_EDITION:
				intLmenuLanguageIndex = Language.intS_MENU_EDITION;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_EDITION_MENU;
				break;
			case ExtendedJMenu.bytS_FILE:
				intLmenuLanguageIndex = Language.intS_MENU_FILE;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_FILE_MENU;
				break;
			case ExtendedJMenu.bytS_PATTERNS:
				intLmenuLanguageIndex = Language.intS_MENU_PATTERNS;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_PATTERNS_MENU;
				break;
			case ExtendedJMenu.bytS_IMPORT:
				intLmenuLanguageIndex = Language.intS_MENU_IMPORT_FILES;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_IMPORT_FILES_MENU;
				break;
			case ExtendedJMenu.bytS_EXPORT:
				intLmenuLanguageIndex = Language.intS_MENU_SAVE_FILES;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_SAVE_FILES_MENU;
				break;
			case ExtendedJMenu.bytS_LANGUAGE:
				intLmenuLanguageIndex = Language.intS_MENU_LANGUAGE;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_LANGUAGE_MENU;
				break;
			case ExtendedJMenu.bytS_RECORDS_LINKS:
				intLmenuLanguageIndex = Language.intS_MENU_RECORDS;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_RECORDS_MENU;
				break;
			case ExtendedJMenu.bytS_VIDEO_LINKS:
				intLmenuLanguageIndex = Language.intS_MENU_VIDEOS;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_VIDEO_LINKS_MENU;
				break;
			case ExtendedJMenu.bytS_WEB_LINKS:
				intLmenuLanguageIndex = Language.intS_MENU_LINKS;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_WEB_LINKS_MENU;
				break;
			case ExtendedJMenu.bytS_RECENT:
				intLmenuLanguageIndex = Language.intS_MENU_RECENT_FILES;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_RECENT_FILES_MENU;
				break;
		}
		this.objGcontrolJFrame.setMenuItemLabel(this, intLmenuLanguageIndex, Strings.strS_SPACES);
		this.objGcontrolJFrame.setMenuMnemonic(this, intLmenuLanguageIndex);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(intLtooltipLanguageIndex)
																												: null);
	}
}

/*
 * @(#)JuggleJMenu.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
