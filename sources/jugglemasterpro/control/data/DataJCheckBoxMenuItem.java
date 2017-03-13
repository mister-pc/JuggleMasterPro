/*
 * @(#)DataJCheckBoxMenuItem.java 4.3.0
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
final public class DataJCheckBoxMenuItem extends JCheckBoxMenuItem implements ItemListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public DataJCheckBoxMenuItem(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setAccelerator(Constants.keyS_DATA_FRAME);
		this.addItemListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPstate
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DATA, 0));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("DataJCheckBoxMenuItem.itemStateChanged()");
		this.objGcontrolJFrame.doAddAction(objPitemEvent.getStateChange() == ItemEvent.SELECTED ? Constants.intS_ACTION_SHOW_DATA_FRAME
																								: Constants.intS_ACTION_HIDE_DATA_FRAME);
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
	final public void setLabels() {
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_DATA);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_DATA);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(this.isSelected()
																																											? Language.intS_TOOLTIP_DO_NOT_DISPLAY_DATA
																																											: Language.intS_TOOLTIP_DISPLAY_DATA)
																												: null);
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ConsoleJCheckBoxMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
