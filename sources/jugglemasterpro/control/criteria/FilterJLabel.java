/**
 * @(#)BallsNumberJLabel.java
 * @author
 * @version 1.00 2009/5/30
 */
package jugglemasterpro.control.criteria;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

public class FilterJLabel extends JLabel implements MouseListener {

	public FilterJLabel(ControlJFrame objPcontrolJFrame, byte bytPbooleanGlobalFilter) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGbooleanGlobalFilter = bytPbooleanGlobalFilter;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setLabelFor(objPcontrolJFrame.objGballsNumberJCheckBox);
		this.addMouseListener(this);
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	@Override final public void mouseClicked(MouseEvent objPmouseEvent) {

		JCheckBox objLjCheckBox = null;
		switch (this.bytGbooleanGlobalFilter) {
			case Constants.bytS_BOOLEAN_GLOBAL_MARK:
				objLjCheckBox = this.objGcontrolJFrame.objGmarkJCheckBox;
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_SKILL:
				objLjCheckBox = this.objGcontrolJFrame.objGskillJCheckBox;
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER:
				objLjCheckBox = this.objGcontrolJFrame.objGballsNumberJCheckBox;
				break;
		}

		if (objLjCheckBox != null && objLjCheckBox.isEnabled()) {
			final boolean bolLselected = !objLjCheckBox.isSelected();
			objLjCheckBox.setSelected(bolLselected);
			this.objGcontrolJFrame.setFiltersControls();
		}
	}

	@Override final public void mouseEntered(MouseEvent objPmouseEvent) {}

	@Override final public void mouseExited(MouseEvent objPmouseEvent) {}

	@Override final public void mousePressed(MouseEvent objPmouseEvent) {}

	@Override final public void mouseReleased(MouseEvent objPmouseEvent) {}

	public final void setToolTipText() {
		int intLtootip = Constants.bytS_UNCLASS_NO_VALUE;
		final byte bytLpatternsManagerType = this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType;
		final boolean bolLedition =
									bytLpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
										|| bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN;
		switch (this.bytGbooleanGlobalFilter) {
			case Constants.bytS_BOOLEAN_GLOBAL_MARK:
				intLtootip =
								Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MARK)
																											? Language.intS_TOOLTIP_DEACTIVATE_MARK_FILTER
																											: Language.intS_TOOLTIP_ACTIVATE_MARK_FILTER;
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_SKILL:
				intLtootip =
								Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_SKILL)
																											? Language.intS_TOOLTIP_DEACTIVATE_SKILL_FILTER
																											: Language.intS_TOOLTIP_ACTIVATE_SKILL_FILTER;
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER:
				intLtootip =
								Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER)
																													? Language.intS_TOOLTIP_DEACTIVATE_BALLS_NUMBER_FILTER
																													: Language.intS_TOOLTIP_ACTIVATE_BALLS_NUMBER_FILTER;
				break;
		}
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && bolLedition
																																	? this.objGcontrolJFrame.getLanguageString(intLtootip)
																																	: null);
	}

	private final byte			bytGbooleanGlobalFilter;

	private final ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
