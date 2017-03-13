/*
 * @(#)JugglerJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.fx;

import java.awt.event.AdjustmentEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJScrollBar;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class JugglerJScrollBar extends ExtendedJScrollBar {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public JugglerJScrollBar(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL,
				1,
				Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM,
				Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL + 1,
				1,
				Language.intS_TOOLTIP_JUGGLER_BODY);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {

		Tools.debug("JugglerJScrollBar.adjustmentValueChanged()");
		final byte bytLpreviousValue = this.objGcontrolJFrame.getJugglerValue();
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGcontrolJFrame.saveControlValue(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT)
																																? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT,
													bytLcurrentValue);
			this.objGcontrolJFrame.setJugglerControls();
			if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)
				|| this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus != Constants.bytS_STATE_ANIMATION_JUGGLING) {
				boolean bolLrefreshTrail = false;
				switch (bytLcurrentValue) {
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL:
						bolLrefreshTrail = false;
						break;
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS:
						bolLrefreshTrail =
											bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD
												&& bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS
												&& bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
						break;
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS:
						bolLrefreshTrail =
											bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD
												&& bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS
												&& bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
						break;
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS:
						bolLrefreshTrail =
											bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS
												&& bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS
												&& bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
						break;
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD:
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS:
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS:
						bolLrefreshTrail = bytLpreviousValue != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
						break;
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM:
						bolLrefreshTrail = true;
						break;
				}
				if (bolLrefreshTrail) {
					this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
														| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
				}
			}
			// if (bytLpreviousValue == Constants.bytS_BYTE_LOCAL_JUGGLER_TRACED
			// && this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER)
			// && !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)) {
			// this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
			// | Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
			// }
			this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_REFRESH_DRAWING);
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)JugglerJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
