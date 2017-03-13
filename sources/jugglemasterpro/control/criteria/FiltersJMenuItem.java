/*
 * @(#)FiltersJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.criteria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
final public class FiltersJMenuItem extends JCheckBoxMenuItem implements ActionListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bolPprompt
	 * @param bolPforward
	 */
	public FiltersJMenuItem(ControlJFrame objPcontrolJFrame) {

		this.objGcontrolJFrame = objPcontrolJFrame;

		// this.setAccelerator(this.bolGprompt ? KeyStroke.getKeyStroke(Constants.intS_SHORTCUT_FIND,
		// ActionEvent.CTRL_MASK)
		// : KeyStroke.getKeyStroke(Constants.intS_SHORTCUT_FIND_AGAIN, this.bolGforward ? 0
		// : ActionEvent.SHIFT_MASK));
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setAccelerator(Constants.keyS_FILTERS);
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("FiltersJMenuItem.actionPerformed()");
		this.objGcontrolJFrame.objGfiltersJDialog.setVisible(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FILTER, 0));
	}

	final public void setLabel() {
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_FILTERS);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_FILTERS);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_FILTERS_MENU)
																												: null);
	}
}

/*
 * @(#)FindJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
