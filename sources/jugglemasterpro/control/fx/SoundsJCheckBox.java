/*
 * @(#)SoundsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.fx;

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
final public class SoundsJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public SoundsJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_SOUNDS, Language.intS_TOOLTIP_DEACTIVATE_SOUNDS);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("SoundsJCheckBox.itemStateChanged()");
		this.validate();
		this.objGcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_SOUNDS, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		this.objGcontrolJFrame.setLightAndSoundsControls();
		this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)SoundsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
