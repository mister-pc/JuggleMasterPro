/*
 * @(#)DefaultsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.motion;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesButtonModel;
import jugglemasterpro.control.util.ThreeStatesJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class DefaultsThreeStatesJCheckBox extends ThreeStatesJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public DefaultsThreeStatesJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Language.intS_TOOLTIP_ACTIVATE_DEFAULTS,
				Language.intS_TOOLTIP_ACTIVATE_RELATIVE_DEFAUTS,
				Language.intS_TOOLTIP_DEACTIVATE_DEFAULTS);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setFocusable(false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(Boolean bolPstateBoolean) {
		this.objGcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_DEFAULTS,
													bolPstateBoolean != ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		this.objGcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS,
													bolPstateBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN);
		this.objGcontrolJFrame.setDefaultsControls();
		this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)DefaultsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
