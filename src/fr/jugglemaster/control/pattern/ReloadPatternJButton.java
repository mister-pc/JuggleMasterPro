/*
 * @(#)ReloadPatternJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.pattern;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJButton;
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
final public class ReloadPatternJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ReloadPatternJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ReloadPatternJButton.actionPerformed(): ControlJFrame.doRestartJuggling()");
		this.objGcontrolJFrame.getPatternsManager().doResetPattern(this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex);
		this.objGcontrolJFrame.doRestartJuggling();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_RELOAD_PATTERN_BW, 1, "Reload");

		final ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_RELOAD_PATTERN, 2, "Reload");

		final ImageIcon icoLdisabled =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_DO_NOT_RELOAD_PATTERN,
																									0,
																									"DoNot");
		Tools.setIcons(this, icoL, icoLrollOver, icoLdisabled);
	}

	final public void setToolTipText() {

		final boolean bolLedition = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && bolLedition
																																	? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_RELOAD_PATTERN)
																																	: null);
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ReloadPatternJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
