/*
 * @(#)BallsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.ball;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class BallsThreeStatesJCheckBox extends ThreeStatesJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public BallsThreeStatesJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Language.intS_TOOLTIP_DISPLAY_BALLS,
				Language.intS_TOOLTIP_DISPLAY_ACTIVE_THROWS,
				Language.intS_TOOLTIP_DO_NOT_DISPLAY_BALLS);
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
		BallActions.doDisplayBalls(this.objGcontrolJFrame, bolPstateBoolean);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPselected
	 */
	// final public void select(
	// boolean bolPselected) {
	// this.removeItemListener(this);
	// this.setSelected(bolPselected);
	// this.addItemListener(this);
	// }
}

/*
 * @(#)BallsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
