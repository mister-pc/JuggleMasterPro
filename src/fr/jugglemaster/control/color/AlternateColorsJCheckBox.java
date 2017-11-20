/*
 * @(#)AlternateColorsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.color;

import java.awt.event.ItemEvent;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJCheckBox;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

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
