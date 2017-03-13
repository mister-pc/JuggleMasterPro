/*
 * @(#)DefaultsJScrollBar.java 4.3.0
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
final public class DefaultsJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public DefaultsJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE,
				1,
				Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE,
				Constants.bytS_BYTE_LOCAL_DEFAULTS_MAXIMUM_VALUE + 1,
				1,
				Language.intS_TOOLTIP_DEFAULTS);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {

		Tools.debug("DefaultsJScrollBar.adjustmentValueChanged()");
		final byte bytLpreviousValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_DEFAULTS);
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_DEFAULTS, bytLcurrentValue);
			this.objGcontrolJFrame.setDefaultsControls();
			this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)DefaultsJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
