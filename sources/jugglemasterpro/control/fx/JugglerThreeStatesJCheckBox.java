/*
 * @(#)JugglerJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.fx;

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
final public class JugglerThreeStatesJCheckBox extends ThreeStatesJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public JugglerThreeStatesJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Language.intS_TOOLTIP_DISPLAY_JUGGLER,
				Language.intS_TOOLTIP_TRACE_JUGGLER,
				Language.intS_TOOLTIP_DO_NOT_DISPLAY_JUGGLER);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(Boolean bolPBoolean) {

		final boolean bolLtrail = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL);
		final boolean bolLjuggler = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER);
		final boolean bolLfX = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX);

		this.objGcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_JUGGLER,
													bolPBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
														|| bolPBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN);
		this.objGcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL,
													bolPBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN);
		this.objGcontrolJFrame.setJugglerControls();
		this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);

		if (bolPBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN) {
			this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_REFRESH_DRAWING);
		}

		if (bolPBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN || bolPBoolean == ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN) {
			if (bolLtrail && bolLjuggler && !bolLfX) {
				this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
			}
		}
		if (bolPBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN) {
			FXActions.doDisplayJuggler(this.objGcontrolJFrame, bolPBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN);
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
/*
 * @(#)JugglerJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
