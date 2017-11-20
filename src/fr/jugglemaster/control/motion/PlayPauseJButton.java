/*
 * @(#)PlayPauseJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.motion;

import java.awt.Insets;
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
final public class PlayPauseJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public PlayPauseJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGreplay = false;
		this.setMargin(new Insets(8, 20, 8, 20));
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("PlayPauseJButton.actionPerformed()");
		if (this.bolGreplay && this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus != Constants.bytS_STATE_ANIMATION_JUGGLING) {
			this.bolGreplay = false;
			this.setImage(false);
			this.setToolTipText();
			Tools.debug("PlayPauseJButton.actionPerformed(): ControlJFrame.doRestartJuggling()");
			this.objGcontrolJFrame.doRestartJuggling();
		} else {
			switch (this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus) {
				case Constants.bytS_STATE_ANIMATION_STOPPED:
				case Constants.bytS_STATE_ANIMATION_STOPPING:
					break;

				case Constants.bytS_STATE_ANIMATION_PAUSED:
					if (!this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_BALLS)) {
						this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_RESET_BALLS);
					}
					if (this.objGcontrolJFrame.bolGdefaultsFxPause) {
						this.objGcontrolJFrame.bolGdefaultsFxPause = false;
						this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
															| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
															| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES | Constants.intS_ACTION_RESET_BALLS
															| Constants.intS_ACTION_RESET_STYLE | Constants.intS_ACTION_REFRESH_DRAWING);
					}

					this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus = Constants.bytS_STATE_ANIMATION_JUGGLING;
					break;

				case Constants.bytS_STATE_ANIMATION_JUGGLING:
					this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus = Constants.bytS_STATE_ANIMATION_PAUSED;
					break;
			}
			this.bolGreplay = true;
			this.setImage(true);
			new ReplayThread(this);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.icoGplay =
						this.objGcontrolJFrame	.getJuggleMasterPro()
												.getImageIcon(	Constants.intS_FILE_ICON_PLAY,
																2,
																this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PLAY));
		this.icoGplayBW =
							this.objGcontrolJFrame	.getJuggleMasterPro()
													.getImageIcon(	Constants.intS_FILE_ICON_PLAY_BW,
																	1,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PLAY));
		this.icoGpause =
							this.objGcontrolJFrame	.getJuggleMasterPro()
													.getImageIcon(	Constants.intS_FILE_ICON_PAUSE,
																	2,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PAUSE));
		this.icoGpauseBW =
							this.objGcontrolJFrame	.getJuggleMasterPro()
													.getImageIcon(	Constants.intS_FILE_ICON_PAUSE_BW,
																	1,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PAUSE));
		this.icoGreplay =
							this.objGcontrolJFrame	.getJuggleMasterPro()
													.getImageIcon(	Constants.intS_FILE_ICON_REPLAY,
																	2,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_REPLAY));
		this.icoGreplayBW =
							this.objGcontrolJFrame	.getJuggleMasterPro()
													.getImageIcon(	Constants.intS_FILE_ICON_REPLAY_BW,
																	1,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_REPLAY));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPreplay
	 */
	final public void setImage(boolean bolPreplay) {

		ImageIcon icoLbutton = null, icoLhoveredButton = null;

		switch (this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus) {
			case Constants.bytS_STATE_ANIMATION_PAUSED:
			case Constants.bytS_STATE_ANIMATION_STOPPED:
				icoLbutton = (bolPreplay // && objGjuggleMasterPro.objGsiteswap.bytGstatus ==
										// Constants.bytS_STATE_SITESWAP_PLAYABLE
										? this.icoGreplayBW : this.icoGplayBW);
				icoLhoveredButton = (bolPreplay ? this.icoGreplay : this.icoGplay);
				this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																															? bolPreplay
																																		? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_REPLAY)
																																		: this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PLAY)
																															: null);
				break;

			case Constants.bytS_STATE_ANIMATION_JUGGLING:
				icoLbutton = this.icoGpauseBW;
				icoLhoveredButton = this.icoGpause;
				this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																															? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PAUSE)
																															: null);
				break;
		}
		Tools.setIcons(this, icoLbutton, icoLhoveredButton);
	}

	final public void setToolTipText() {
		this.setToolTipText(false);
	}

	final public void setToolTipText(boolean bolPreplay) {

		int intLtooltip = Constants.bytS_UNCLASS_NO_VALUE;
		if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SPEED)) {
			switch (this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus) {
				case Constants.bytS_STATE_ANIMATION_PAUSED:
				case Constants.bytS_STATE_ANIMATION_STOPPED:
					intLtooltip = bolPreplay ? Language.intS_TOOLTIP_REPLAY : Language.intS_TOOLTIP_PLAY;
					break;

				case Constants.bytS_STATE_ANIMATION_JUGGLING:
					intLtooltip = Language.intS_TOOLTIP_PAUSE;
					break;
			}
		}
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
							&& intLtooltip != Constants.bytS_UNCLASS_NO_VALUE ? this.objGcontrolJFrame.getLanguageString(intLtooltip) : null);
	}

	public boolean				bolGreplay;
	private ImageIcon			icoGpause;
	private ImageIcon			icoGpauseBW;

	private ImageIcon			icoGplay;

	private ImageIcon			icoGplayBW;

	private ImageIcon			icoGreplay;

	private ImageIcon			icoGreplayBW;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PlayPauseJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
