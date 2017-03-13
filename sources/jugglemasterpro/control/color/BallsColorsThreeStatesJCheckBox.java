/*
 * @(#)ColorsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.color;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class BallsColorsThreeStatesJCheckBox extends ThreeStatesJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public BallsColorsThreeStatesJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_COLORS, Language.intS_TOOLTIP_MIX_COLORS, Language.intS_TOOLTIP_DEACTIVATE_COLORS);
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
		ColorActions.doDisplayColors(this.objGcontrolJFrame, bolPstateBoolean);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ColorsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
