/*
 * @(#)DwellJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.motion;

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
final public class DwellJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public DwellJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_DWELL_DEFAULT_VALUE,
				1,
				Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE,
				Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE + 1,
				5,
				Language.intS_TOOLTIP_DWELL);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {
		// this.objGcontrolJFrame.log("Restart par DwellJScrollBar");
		final byte bytLpreviousValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_DWELL);
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_DWELL, bytLcurrentValue);
			// this.objGcontrolJFrame.setDwellControls(); // TODO : Si pas de doRestartJuggling
			// this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_ANIMATION_PROPERTIES);
			// this.objGcontrolJFrame.setTitles();

			Tools.debug("DwellJScrollBar.adjustmentValueChanged(): ControlJFrame.doRestartJuggling()");
			this.objGcontrolJFrame.doRestartJuggling();
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)DwellJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
