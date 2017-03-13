/*
 * @(#)ReplayThread.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.motion;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ReplayThread implements Runnable {

	final private static int		intS_REPLAY_TIME	= 500;
	@SuppressWarnings("unused")
	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private PlayPauseJButton	objGplayPauseJButton;

	/**
	 * Constructs
	 * 
	 * @param objPplayPauseJButton
	 */
	public ReplayThread(PlayPauseJButton objPplayPauseJButton) {
		this.objGplayPauseJButton = objPplayPauseJButton;
		new Thread(this).start();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void run() {
		Tools.sleep(ReplayThread.intS_REPLAY_TIME, "Error while setting replay time countdown");
		if (this.objGplayPauseJButton.bolGreplay) {
			this.objGplayPauseJButton.setImage(false);
			this.objGplayPauseJButton.setToolTipText();
			this.objGplayPauseJButton.bolGreplay = false;
		}
	}
}

/*
 * @(#)ReplayThread.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
