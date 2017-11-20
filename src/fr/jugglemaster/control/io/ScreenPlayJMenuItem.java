/*
 * @(#)StartScreenPlayJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
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
final public class ScreenPlayJMenuItem extends JMenuItem implements ActionListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private boolean		bolGstartScreenPlay;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ScreenPlayJMenuItem(ControlJFrame objPcontrolJFrame, boolean bolPstartScreenPlay) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGstartScreenPlay = bolPstartScreenPlay;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ScreenPlayJMenuItem.actionPerformed(): FileAction.doTakeScreenPlay()");
		FileActions.doTakeScreenPlay(this.objGcontrolJFrame, this.bolGstartScreenPlay);
	}

	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_START_SCREEN_PLAY, 0));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPscreenPlay
	 */
	final public void setLabel(boolean bolPactive) {

		final int intLmenuLanguageIndex =
											this.bolGstartScreenPlay ? bolPactive ? Language.intS_MENU_RESTART_SCREEN_PLAY
																					: Language.intS_MENU_START_SCREEN_PLAY
																	: Language.intS_MENU_STOP_SCREEN_PLAY;
		final int intLtooltipLanguageIndex =
												this.bolGstartScreenPlay ? bolPactive ? Language.intS_TOOLTIP_RESTART_SCREEN_PLAY_MENU
																						: Language.intS_TOOLTIP_START_SCREEN_PLAY_MENU
																		: Language.intS_TOOLTIP_STOP_SCREEN_PLAY_MENU;
		this.objGcontrolJFrame.setMenuItemLabel(this, intLmenuLanguageIndex);
		this.objGcontrolJFrame.setMenuMnemonic(this, intLmenuLanguageIndex);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(intLtooltipLanguageIndex)
																												: null);
	}
}

/*
 * @(#)StartScreenPlayJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
