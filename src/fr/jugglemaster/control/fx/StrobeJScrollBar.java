/*
 * @(#)StrobeJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.fx;

import java.awt.event.AdjustmentEvent;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJScrollBar;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class StrobeJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public StrobeJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE,
				1,
				Constants.bytS_BYTE_LOCAL_STROBE_MINIMUM_RATE,
				Constants.bytS_BYTE_LOCAL_STROBE_EACH_CATCH + 1,
				1,
				Language.intS_TOOLTIP_FLASH_RATIO);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {

		Tools.debug("StrobeJScrollBar.adjustmentValueChanged()");
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		final byte bytLpreviousValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_STROBE);
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_STROBE, bytLcurrentValue);
			if ((this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLASH) || this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_ROBOT))
				&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_BALLS)
				&& this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL) == Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL) {
				this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
													| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
													| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES);
			}
			this.objGcontrolJFrame.setStrobeControls();
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)StrobeJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
