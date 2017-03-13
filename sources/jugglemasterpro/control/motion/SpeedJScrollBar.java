/*
 * @(#)SpeedJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.motion;

import java.awt.event.AdjustmentEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJScrollBar;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class SpeedJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public SpeedJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE,
				1,
				Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE,
				Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE + 1,
				5,
				Language.intS_TOOLTIP_SPEED);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {

		final byte bytLpreviousValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED);
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_SPEED, bytLcurrentValue);

			// TODO : si pas de doRestartJuggling
			// this.objGcontrolJFrame.setSpeedControls();
			// this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_INIT_ANIMATION_PROPERTIES);

			Tools.debug("SpeedJScrollBar.adjustmentValueChanged(): ControlJFrame.doRestartJuggling()");
			this.objGcontrolJFrame.doRestartJuggling();
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)SpeedJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
