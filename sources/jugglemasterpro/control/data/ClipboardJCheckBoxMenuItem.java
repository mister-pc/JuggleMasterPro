/*
 * @(#)ClipboardJCheckBoxMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.data;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBoxMenuItem;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ClipboardJCheckBoxMenuItem extends JCheckBoxMenuItem implements ItemListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public boolean				bolGalreadyDisplayed;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ClipboardJCheckBoxMenuItem(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGalreadyDisplayed = false;
		this.setState(false);
		this.setAccelerator(Constants.keyS_DISPLAY_CLIPBOARD);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);

		// setShortcut(new MenuShortcut(intS_SHORTCUT_DISPLAY_CLIPBOARD, false));
		this.addItemListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DISPLAY_CLIPBOARD, 0));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("ClipboardJCheckBoxMenuItem.itemStateChanged()");
		DataActions.doDisplayClipboard(this.objGcontrolJFrame, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPselected
	 */
	final public void selectState(boolean bolPselected) {
		this.removeItemListener(this);
		this.setState(bolPselected);
		this.addItemListener(this);
	}

	final public void setLabel() {
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_DISPLAY_CLIPBOARD);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_DISPLAY_CLIPBOARD);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(this.isSelected()
																																											? Language.intS_TOOLTIP_DO_NOT_DISPLAY_CLIPBOARD_MENU
																																											: Language.intS_TOOLTIP_DISPLAY_CLIPBOARD_MENU)
																												: null);
	}
}

/*
 * @(#)ClipboardJCheckBoxMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
