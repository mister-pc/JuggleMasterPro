package jugglemasterpro.control.fx;

import java.util.Arrays;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesButtonModel;
import jugglemasterpro.util.Constants;

final public class FXActions {

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public static void doDisplayFX(final ControlJFrame objPcontrolJFrame, final boolean bolPenabled) {
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX, bolPenabled);
		if (objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_DEFAULTS)
			&& objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED) {
			objPcontrolJFrame.bolGdefaultsFxPause = bolPenabled;
		}
		objPcontrolJFrame.setStyleControls();
		objPcontrolJFrame.setBallsControls();
		objPcontrolJFrame.setJugglerControls();
		objPcontrolJFrame.setDefaultsControls();
		objPcontrolJFrame.setStrobeControls();
		if (bolPenabled) {
			Arrays.fill(objPcontrolJFrame.getJuggleMasterPro().bolGdisplayedThrowA, false);
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_DRAW_SITESWAP
											| Constants.intS_ACTION_RECREATE_SITESWAP_IMAGE | Constants.intS_ACTION_REFRESH_DRAWING);

			switch (objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus) {
				case Constants.bytS_STATE_ANIMATION_JUGGLING:
					objPcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
													| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES | Constants.intS_ACTION_RESET_BALLS
													| Constants.intS_ACTION_RESET_STYLE);
					break;
				case Constants.bytS_STATE_ANIMATION_PAUSED:
					objPcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES
													| Constants.intS_ACTION_REFRESH_DRAWING);
			}
		} else {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES
											| Constants.intS_ACTION_RESET_BALLS);
		}
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public static void doDisplayJuggler(final ControlJFrame objPcontrolJFrame, final boolean bolPenabled) {
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER, bolPenabled);
		if (!bolPenabled) {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE);
			if (objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)) {
				objPcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
			}
		}
		objPcontrolJFrame.setJugglerControls();
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_REFRESH_DRAWING);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public static void doStrobe(final ControlJFrame objPcontrolJFrame, final Boolean bolPenabledBoolean) {

		objPcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_FLASH,
												bolPenabledBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN);
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_ROBOT, bolPenabledBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN);
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE);
		if (objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_BALLS)
			&& objPcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL) == Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL) {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES);
		}
		if (objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)) {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
		}
		if (bolPenabledBoolean == ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN) {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_REFRESH_DRAWING);
		}
		objPcontrolJFrame.setStrobeControls();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public static void doSwitchLight(final ControlJFrame objPcontrolJFrame, final boolean bolPenabled) {
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT, bolPenabled);
		objPcontrolJFrame.objGjugglerJScrollBar.selectValue(objPcontrolJFrame.getJugglerValue());
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
										| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES | Constants.intS_ACTION_DRAW_SITESWAP
										| Constants.intS_ACTION_REFRESH_DRAWING);
		objPcontrolJFrame.setLightAndSoundsControls();
		objPcontrolJFrame.setJugglerControls();
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}
