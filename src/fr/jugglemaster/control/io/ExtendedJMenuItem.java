/*
 * @(#)JuggleJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JToolTip;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */

final public class ExtendedJMenuItem extends JMenuItem {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPmenuItemType
	 * @param bytPiconFileType
	 * @param objPactionListener
	 */
	public ExtendedJMenuItem(ControlJFrame objPcontrolJFrame, byte bytPmenuItemType, int intPiconFileType, ActionListener objPactionListener) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGmenuItemType = bytPmenuItemType;
		this.intGiconFileType = intPiconFileType;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		switch (this.bytGmenuItemType) {
			case ExtendedJMenuItem.bytS_RELOAD_PATTERN:
				this.setAccelerator(Constants.keyS_RELOAD_PATTERN);
				break;
			case ExtendedJMenuItem.bytS_DETAILED_COPY:
				this.setAccelerator(Constants.keyS_DETAILED_COPY);
				break;
			case ExtendedJMenuItem.bytS_FREE_CLIPBOARD:
				this.setAccelerator(Constants.keyS_FREE_CLIPBOARD);
				break;
			case ExtendedJMenuItem.bytS_SIMPLE_COPY:
				this.setAccelerator(Constants.keyS_SIMPLE_COPY);
				break;
			case ExtendedJMenuItem.bytS_HELP:
				this.setAccelerator(Constants.keyS_HELP);
				break;
			case ExtendedJMenuItem.bytS_SITESWAP_THEORY:
				this.setAccelerator(Constants.keyS_SITESWAP_THEORY);
				break;
		}

		this.setActionListener(objPactionListener);
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(this.intGiconFileType, 0));
	}

	final public void setActionListener(ActionListener objPactionListener) {
		if (objPactionListener != null) {
			this.objGactionListener = objPactionListener;
			this.addActionListener(objPactionListener);
		} else {
			this.removeActionListener(this.objGactionListener);
		}
	}

	final public void setLabel() {
		int intLmenuLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		int intLtooltipLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		switch (this.bytGmenuItemType) {
			case ExtendedJMenuItem.bytS_RELOAD_PATTERN:
				intLmenuLanguageIndex = Language.intS_MENU_RELOAD_PATTERN;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_RELOAD_PATTERN_MENU;
				break;
			case ExtendedJMenuItem.bytS_DETAILED_COPY:
				intLmenuLanguageIndex = Language.intS_MENU_DETAILED_COPY;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_DETAILED_COPY_MENU;
				break;
			case ExtendedJMenuItem.bytS_FREE_CLIPBOARD:
				intLmenuLanguageIndex = Language.intS_MENU_FREE_CLIPBOARD;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_FREE_CLIPBOARD_MENU;
				break;
			case ExtendedJMenuItem.bytS_SIMPLE_COPY:
				intLmenuLanguageIndex = Language.intS_MENU_SIMPLE_COPY;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_SIMPLE_COPY_MENU;
				break;
			case ExtendedJMenuItem.bytS_HELP:
				intLmenuLanguageIndex = Language.intS_MENU_JUGGLE_MASTER_PRO;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_JUGGLE_MASTER_PRO_MENU;
				break;
			case ExtendedJMenuItem.bytS_SITESWAP_THEORY:
				intLmenuLanguageIndex = Language.intS_MENU_SITESWAP_THEORY;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_SITESWAP_THEORY_MENU;
				break;
		}
		this.objGcontrolJFrame.setMenuItemLabel(this, intLmenuLanguageIndex);
		this.objGcontrolJFrame.setMenuMnemonic(this, intLmenuLanguageIndex);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(intLtooltipLanguageIndex)
																												: null);
	}

	final private byte			bytGmenuItemType;
	final private int			intGiconFileType;

	private ActionListener		objGactionListener;
	final private ControlJFrame	objGcontrolJFrame;
	final static public byte	bytS_DETAILED_COPY		= 2;
	final static public byte	bytS_FREE_CLIPBOARD		= 3;

	final static public byte	bytS_HELP				= 0;

	final static public byte	bytS_RELOAD_PATTERN		= 5;

	final static public byte	bytS_SIMPLE_COPY		= 4;

	final static public byte	bytS_SITESWAP_THEORY	= 1;

	final private static long	serialVersionUID		= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)JuggleJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
