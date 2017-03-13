/*
 * @(#)BallsNumberJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.criteria;

import java.awt.event.ItemEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class BallsNumberJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public BallsNumberJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_BALLS_NUMBER_FILTER, Language.intS_TOOLTIP_DEACTIVATE_BALLS_NUMBER_FILTER);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {
		Tools.debug("BallsNumberJCheckBox.itemStateChanged()");
		this.validate();
		Preferences.setGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		CriteriaActions.doCheckFilters(this.objGcontrolJFrame);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)BallsNumberJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
