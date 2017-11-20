/*
 * @(#)AlternateColorsJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.color;

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
final public class AlternateColorsJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public AlternateColorsJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH,
				1,
				Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH,
				Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE + 1,
				1,
				Language.intS_TOOLTIP_ALTERNATE_COLORS_RATIO);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {
		Tools.debug("AlternateColorsJScrollBar.adjustmentValueChanged()");
		ColorActions.doAdjustAlternateRate(this.objGcontrolJFrame, (byte) objPadjustmentEvent.getValue());
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)AlternateColorsJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
