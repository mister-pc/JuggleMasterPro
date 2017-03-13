/*
 * @(#)FindJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.criteria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
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
final public class FindJMenuItem extends JMenuItem implements ActionListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean		bolGforward;

	private final boolean		bolGprompt;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bolPprompt
	 * @param bolPforward
	 */
	public FindJMenuItem(ControlJFrame objPcontrolJFrame, boolean bolPprompt, boolean bolPforward) {

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGprompt = bolPprompt;
		this.bolGforward = bolPforward;

		this.setAccelerator(this.bolGprompt ? Constants.keyS_FIND : this.bolGforward ? Constants.keyS_FIND_NEXT
																								: Constants.keyS_FIND_PREVIOUS);
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

		Tools.debug("FindJMenuItem.actionPerformed()");
		if (this.bolGprompt || !this.objGcontrolJFrame.objGfindJDialog.isAble()) {
			this.objGcontrolJFrame.objGfindJDialog.setVisible(true);
		} else {
			this.objGcontrolJFrame.objGfindJDialog.doFind(this.bolGforward);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame	.getJuggleMasterPro()
											.getImageIcon(	this.bolGprompt ? Constants.intS_FILE_ICON_FIND
																			: this.bolGforward ? Constants.intS_FILE_ICON_FIND_NEXT
																								: Constants.intS_FILE_ICON_FIND_PREVIOUS,
															0));
	}

	final public void setLabel() {

		final int intLmenuLanguage =
										this.bolGprompt ? Language.intS_MENU_FIND : this.bolGforward ? Language.intS_MENU_FIND_NEXT
																									: Language.intS_MENU_FIND_PREVIOUS;
		final int intLtooltipLanguage =
										this.bolGprompt ? Language.intS_TOOLTIP_FIND_MENU
														: this.bolGforward ? Language.intS_TOOLTIP_FIND_NEXT_MENU
																			: Language.intS_TOOLTIP_FIND_PREVIOUS_MENU;
		this.objGcontrolJFrame.setMenuItemLabel(this, intLmenuLanguage);
		this.objGcontrolJFrame.setMenuMnemonic(this, intLmenuLanguage);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(intLtooltipLanguage)
																												: null);
	}
}

/*
 * @(#)FindJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
