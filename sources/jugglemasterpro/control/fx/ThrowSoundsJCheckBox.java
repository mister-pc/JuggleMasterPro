/*
 * @(#)ThrowSoundsJCheckBox.java 4.3.0
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
final public class ThrowSoundsJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ThrowSoundsJCheckBox(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_THROWS_SOUND, Language.intS_TOOLTIP_DEACTIVATE_THROWS_SOUND);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("ThrowSoundsJCheckBox.itemStateChanged()");
		this.validate();
		this.objGcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		this.objGcontrolJFrame.setLightAndSoundsControls();
		this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ThrowSoundsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
