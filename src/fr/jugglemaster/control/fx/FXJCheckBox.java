/*
 * @(#)FXJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.fx;

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
final public class FXJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public FXJCheckBox(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_FX, Language.intS_TOOLTIP_DEACTIVATE_FX);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {
		// this.setToolTipText(objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		Tools.debug("FXJCheckBox.itemStateChanged()");
		this.validate();
		FXActions.doDisplayFX(this.objGcontrolJFrame, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)FXJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
