package fr.jugglemaster.control.criteria;

import java.awt.event.MouseEvent;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;

final public class InvalidPatternsJLabel extends ExtendedJLabel {

	public InvalidPatternsJLabel(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame);
		this.addMouseListener(this);
	}

	@Override final public void mouseClicked(MouseEvent objPmouseEvent) {

		Preferences.setGlobalBooleanPreference(	Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS,
												!Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS));
		this.objGcontrolJFrame.objGobjectsJList.setLists();
		this.objGcontrolJFrame.setMenusEnabled();
		this.objGcontrolJFrame.setFiltersControls();
		this.setToolTipText();
		this.objGcontrolJFrame.objGinvalidPatternsJCheckBox.setToolTipText();
	}

	@Override final public void setToolTipText() {
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && this.isEnabled()
																																		? this.objGcontrolJFrame.getLanguageString(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS)
																																																																			? Language.intS_TOOLTIP_DO_NOT_DISPLAY_INVALID_PATTERNS
																																																																			: Language.intS_TOOLTIP_DISPLAY_INVALID_PATTERNS)
																																		: null);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}
