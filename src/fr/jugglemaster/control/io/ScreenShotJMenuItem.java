/*
 * @(#)ScreenShotJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
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
final public class ScreenShotJMenuItem extends JMenuItem implements ActionListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ScreenShotJMenuItem(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.addActionListener(this);
		this.setAccelerator(KeyStroke.getKeyStroke(Constants.keyS_SCREENSHOT, ActionEvent.CTRL_MASK));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ScreenShotJMenuItem.actionPerformed(): FileActions.doTakeScreenShot");
		FileActions.doTakeScreenShot(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_SCREEN_SHOT, 0));
	}

	final public void setLabel() {
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_SCREEN_SHOT);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_SCREEN_SHOT);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_SCREEN_SHOT_MENU)
																												: null);
	}
}

/*
 * @(#)ScreenShotJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
