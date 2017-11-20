/**
 * 
 */
package fr.jugglemaster.control.criteria;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.Icon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * @author BeLO
 */
public class MarkJButton extends ExtendedJButton implements ActionListener {

	public MarkJButton(	ControlJFrame objPcontrolJFrame,
						boolean bolPlistener,
						boolean bolPpreferences,
						int intPtooltipEnabledLanguageIndex,
						int intPtooltipDisabledLanguageIndex) {
		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setSize(new Dimension(16, 16));
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.bolGenabled = false;
		this.bolGpreferences = bolPpreferences;
		this.intGenabledLanguageIndex = intPtooltipEnabledLanguageIndex;
		this.intGdisabledLanguageIndex = intPtooltipDisabledLanguageIndex;
		if (bolPlistener) {
			this.addActionListener(this);
		}
	}

	/**
	 * @param objPcontrolJFrame
	 */
	public MarkJButton(ControlJFrame objPcontrolJFrame, int intPtooltipEnabledLanguageIndex) {
		this(objPcontrolJFrame, false, false, intPtooltipEnabledLanguageIndex, intPtooltipEnabledLanguageIndex);
	}

	@Override public void actionPerformed(ActionEvent objPactionEvent) {
		if (this.bolGpreferences) {
			if (this.objGcontrolJFrame.objGpreferencesJDialog != null) {
				Tools.debug("MarkCanvas.mouseClicked(preferencesJDialog)");
				final ItemEvent objLitemEvent =
												new ItemEvent(	this.objGcontrolJFrame.objGpreferencesJDialog.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_MARK],
																ItemEvent.ITEM_STATE_CHANGED,
																this,
																Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MARK)
																																			? ItemEvent.DESELECTED
																																			: ItemEvent.SELECTED);

				this.objGcontrolJFrame.objGpreferencesJDialog.doApplyBooleanGlobalCheckChange(	Constants.bytS_BOOLEAN_GLOBAL_MARK,
																								!Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MARK));
				this.objGcontrolJFrame.objGpreferencesJDialog.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_MARK].itemStateChanged(objLitemEvent);

			}
			return;
		}

		Tools.debug("MarkJButton.actionPerformed()");
		final ItemEvent objLitemEvent =
										new ItemEvent(	this.objGcontrolJFrame.objGmarkJCheckBox,
														ItemEvent.ITEM_STATE_CHANGED,
														this,
														Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MARK)
																																	? ItemEvent.DESELECTED
																																	: ItemEvent.SELECTED);
		this.objGcontrolJFrame.objGmarkJCheckBox.itemStateChanged(objLitemEvent);
	}

	final public void doLoadImages() {
		if (MarkJButton.icoSmark == null) {
			MarkJButton.icoSmark = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_MARK, 1);
		}
		if (MarkJButton.icoSmarkBW == null) {
			MarkJButton.icoSmarkBW = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_MARK_BW, 1);
		}
		this.setEnabled(this.bolGenabled);
	}

	@Override final public void setEnabled(boolean bolPenabled) {

		this.bolGenabled = bolPenabled;
		this.setIcon(this.bolGenabled ? MarkJButton.icoSmark : MarkJButton.icoSmarkBW);
		final boolean bolLedition =
									this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
										|| this.objGcontrolJFrame.isBooleanLocal(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		super.setEnabled(bolLedition);
		this.setToolTipText();
	}

	final public void setToolTipText() {
		final boolean bolLedition =
									this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
										|| this.objGcontrolJFrame.isBooleanLocal(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && bolLedition
																																	? this.objGcontrolJFrame.getLanguageString(this.bolGenabled
																																												&& this.intGdisabledLanguageIndex != this.intGenabledLanguageIndex
																																												|| this.intGdisabledLanguageIndex == this.intGenabledLanguageIndex
																																																													? this.intGdisabledLanguageIndex
																																																													: this.intGenabledLanguageIndex)
																																	: null);
	}

	private boolean				bolGenabled;

	private final boolean		bolGpreferences;
	private final int			intGdisabledLanguageIndex;

	final private int			intGenabledLanguageIndex;

	private final ControlJFrame	objGcontrolJFrame;

	private static Icon			icoSmark;

	private static Icon			icoSmarkBW;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
