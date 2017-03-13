/*
 * @(#)SkillJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.criteria;

import java.awt.event.ItemEvent;
import javax.swing.JLabel;
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
final public class SkillJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public SkillJCheckBox(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_SKILL_FILTER, Language.intS_TOOLTIP_DEACTIVATE_SKILL_FILTER);
		this.setToolTipText(this.isSelected());
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("SkillJCheckBox.itemStateChanged()");
		this.setToolTipText(objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		this.validate();
		Preferences.setGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_SKILL, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		CriteriaActions.doCheckFilters(this.objGcontrolJFrame);
	}

	public final void setToolTipText(boolean bolPselected) {
		final String objLtooltipString =
											this.objGcontrolJFrame.getLanguageString(bolPselected ? Language.intS_TOOLTIP_DEACTIVATE_SKILL_FILTER
																									: Language.intS_TOOLTIP_ACTIVATE_SKILL_FILTER);
		super.setToolTipText(objLtooltipString);
		final JLabel[] objLjLabelA = { this.objGcontrolJFrame.objGfromSkillJLabel, this.objGcontrolJFrame.objGtoSkillJLabel };
		for (final JLabel objLjLabel : objLjLabelA) {
			if (objLjLabel != null) {
				objLjLabel.setToolTipText(objLtooltipString);
			}
		}
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)SkillJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
