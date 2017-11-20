/*
 * @(#)RefreshSiteswapJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.siteswap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
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
final public class RefreshSiteswapJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public RefreshSiteswapJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addActionListener(this);
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, true, false));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("RefreshSiteswapJButton.actionPerformed(): SiteswapActions.doRefreshSiteswap()");
		SiteswapActions.doRefreshSiteswap(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		final ImageIcon icoLrollOver =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_REFRESH_SITESWAP,
																									2,
																									"Actualize");

		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_REFRESH_SITESWAP_BW, 1, "Actualize");

		final ImageIcon icoLdisabled =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_DO_NOT_REFRESH_SITESWAP,
																									0,
																									"DoNot");

		Tools.setIcons(this, icoL, icoLrollOver, icoLdisabled);
	}

	public void setToolTipText() {
		final boolean bolLrefreshSiteswap =
											this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
												&& this.objGcontrolJFrame.getFilteredPatternsNumber() > 0
												&& this.objGcontrolJFrame.getJuggleMasterPro().objGsiteswap.bytGstatus >= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER
												&& (!this.objGcontrolJFrame.getJuggleMasterPro().objGsiteswap	.toString()
																												.equals(this.objGcontrolJFrame.getControlString(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
																																																														? Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP
																																																														: Constants.bytS_STRING_LOCAL_SITESWAP)) || this.objGcontrolJFrame.getJuggleMasterPro().objGsiteswap.bolGsymmetric);

		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && bolLrefreshSiteswap
																																			? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_REFRESH_SITESWAP)
																																			: null);
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)RefreshSiteswapJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
