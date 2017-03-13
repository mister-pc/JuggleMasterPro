/*
 * @(#)AlternateColorsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.color;

import java.awt.event.ItemEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class AlternateColorsJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public AlternateColorsJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, Language.intS_TOOLTIP_ALTERN_COLORS, Language.intS_TOOLTIP_DO_NOT_ALTERN_COLORS);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {
		// this.setToolTipText(objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		Tools.debug("AlternateColorsJCheckBox.itemStateChanged()");
		this.validate();
		ColorActions.doDisplayAlternateColors(this.objGcontrolJFrame, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)AlternateColorsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
