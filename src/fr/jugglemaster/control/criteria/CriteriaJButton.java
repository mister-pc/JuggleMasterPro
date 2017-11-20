package fr.jugglemaster.control.criteria;

import java.awt.event.ActionListener;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;

final public class CriteriaJButton extends ExtendedJButton {

	public CriteriaJButton(ControlJFrame objPcontrolJFrame, int intPtooltip) {
		this(objPcontrolJFrame, intPtooltip, null);
	}

	public CriteriaJButton(ControlJFrame objPcontrolJFrame, int intPtooltip, ActionListener objPactionListener) {
		super(objPcontrolJFrame, objPactionListener);
		this.intGtooltip = intPtooltip;
	}

	final public void setToolTipText() {
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
							&& this.intGtooltip != Constants.bytS_UNCLASS_NO_VALUE ? this.objGcontrolJFrame.getLanguageString(this.intGtooltip)
																					: null);
	}

	final private int			intGtooltip;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
