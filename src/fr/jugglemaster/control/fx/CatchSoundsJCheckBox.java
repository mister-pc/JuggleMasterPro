/*
 * @(#)CatchSoundsJCheckBox.java 4.3.0
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
final public class CatchSoundsJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public CatchSoundsJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_CATCHS_SOUND, Language.intS_TOOLTIP_DEACTIVATE_CATCHS_SOUND);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {
		// this.setToolTipText(objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		Tools.debug("CatchSoundsJCheckBox.itemStateChanged()");
		this.validate();
		this.objGcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		this.objGcontrolJFrame.setLightAndSoundsControls();
		this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)CatchSoundsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
