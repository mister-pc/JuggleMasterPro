package jugglemasterpro.control.ball;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesButtonModel;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class BallActions {

	/**
	 * @param objGcontrolJFrame
	 * @param value
	 */
	final public static void doAdjustBallsTrail(ControlJFrame objGcontrolJFrame, byte bytPtrailBallValue) {

		Tools.debug("BallsAction.doAdjustBallsTrail()");
		final byte bytLpreviousValue = objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL);
		if (bytPtrailBallValue != bytLpreviousValue) {
			objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL, bytPtrailBallValue);
			if (bytLpreviousValue == Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL || bytPtrailBallValue == Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL) {
				objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES);
			}
			if (objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED) {
				objGcontrolJFrame.doAddAction(Constants.intS_ACTION_REFRESH_DRAWING);
			}
			objGcontrolJFrame.setBallsControls();
			objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPdisplay
	 */
	final public static void doDisplayBalls(final ControlJFrame objPcontrolJFrame, final Boolean bolPstateBoolean) {
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_BALLS, bolPstateBoolean != ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		objPcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS,
												bolPstateBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN);
		if (bolPstateBoolean == ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN) {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES);
			if (objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING) {
				objPcontrolJFrame.doAddAction(Constants.intS_ACTION_RESET_BALLS);
			}
		} else if ((objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLASH) || objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_ROBOT))
					&& objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING) {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES
											| Constants.intS_ACTION_RESET_BALLS);
		}
		objPcontrolJFrame.setBallsControls();
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_REFRESH_DRAWING);
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}
