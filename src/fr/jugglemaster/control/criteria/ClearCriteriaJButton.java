package fr.jugglemaster.control.criteria;

import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;

final public class ClearCriteriaJButton extends ExtendedJButton {

	public ClearCriteriaJButton(CriteriaJDialog objPcriteriaJDialog) {

		super(objPcriteriaJDialog.objGcontrolJFrame, 0);
		this.objGcriteriaJDialog = objPcriteriaJDialog;
		this.addActionListener(objPcriteriaJDialog);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																													? this.objGcriteriaJDialog.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_RESET_FILTERS)
																													: null);
	}

	@Override final public void setEnabled(boolean bolPenabled) {
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																													? this.objGcriteriaJDialog.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_RESET)
																													: null);
		super.setEnabled(bolPenabled);
	}

	final private CriteriaJDialog	objGcriteriaJDialog;

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
