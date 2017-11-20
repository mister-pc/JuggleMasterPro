/*
 * @(#)FluidityJScrollBar.java 4.3.0
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
final public class FluidityJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public FluidityJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_FLUIDITY_DEFAULT_VALUE,
				1,
				Constants.bytS_BYTE_LOCAL_FLUIDITY_MINIMUM_VALUE,
				Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE + 1,
				10,
				Language.intS_TOOLTIP_FLUIDITY);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {

		final byte bytLpreviousValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_FLUIDITY);
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_FLUIDITY, bytLcurrentValue);

			// TODO : Si pas de doRestartJuggling
			// this.objGcontrolJFrame.setFluidityJLabels();
			// this.objGcontrolJFrame.setTitles();
			Tools.debug("FluidityJScrollBar.adjustmentValueChanged(): ControlJFrame.doRestartJuggling()");
			this.objGcontrolJFrame.doRestartJuggling();
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)FluidityJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
