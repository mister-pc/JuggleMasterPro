/*
 * @(#)HeightJScrollBar.java 4.3.0
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
final public class HeightJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public HeightJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_HEIGHT_DEFAULT_VALUE,
				1,
				Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE,
				Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE + 1,
				5,
				Language.intS_TOOLTIP_HEIGHT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {
		final byte bytLpreviousValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_HEIGHT);
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_HEIGHT, bytLcurrentValue);

			// TODO : si pas de doRestartJuggling
			// setHeightJLabels();
			// objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
			Tools.debug("HeightJScrollBar.adjustmentValueChanged(): ControlJFrame.doRestartJuggling()");
			this.objGcontrolJFrame.doRestartJuggling();
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)HeightJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
