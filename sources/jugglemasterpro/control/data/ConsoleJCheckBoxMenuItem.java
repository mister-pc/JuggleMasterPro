/*
 * @(#)ConsoleJCheckBoxMenuItem.java 4.3.0
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
final public class ConsoleJCheckBoxMenuItem extends JCheckBoxMenuItem implements ItemListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ConsoleJCheckBoxMenuItem(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setAccelerator(Constants.keyS_CONSOLE);
		this.addItemListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPstate
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CONSOLE, 0));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	final public void itemStateChanged(ItemEvent objPitemEvent) {
		Tools.debug("ConsoleJCheckBoxMenuItem.itemStateChanged()");
		this.objGcontrolJFrame.objGconsoleJDialog.setVisible(objPitemEvent.getStateChange() == ItemEvent.SELECTED);
	}

	final public void selectState(boolean bolPstate) {
		this.removeItemListener(this);
		this.setState(bolPstate);
		this.addItemListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLabel() {
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_CONSOLE);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_CONSOLE);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(this.isSelected()
																																											? Language.intS_TOOLTIP_DO_NOT_DISPLAY_CONSOLE_MENU
																																											: Language.intS_TOOLTIP_DISPLAY_CONSOLE_MENU)
																												: null);
	}
}

/*
 * @(#)ConsoleJCheckBoxMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
