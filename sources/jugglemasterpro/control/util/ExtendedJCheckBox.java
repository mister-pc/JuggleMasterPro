package jugglemasterpro.control.util;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JToolTip;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

public class ExtendedJCheckBox extends JCheckBox implements ItemListener {

	public ExtendedJCheckBox(ControlJFrame objPcontrolJFrame) {
		this(objPcontrolJFrame, null, Constants.bytS_UNCLASS_NO_VALUE, Constants.bytS_UNCLASS_NO_VALUE, false);
	}

	public ExtendedJCheckBox(ControlJFrame objPcontrolJFrame, boolean bolPselected) {
		this(objPcontrolJFrame, null, Constants.bytS_UNCLASS_NO_VALUE, Constants.bytS_UNCLASS_NO_VALUE, bolPselected);
	}

	public ExtendedJCheckBox(ControlJFrame objPcontrolJFrame, int intPtooltip) {
		this(objPcontrolJFrame, null, intPtooltip, intPtooltip, false);
	}

	public ExtendedJCheckBox(ControlJFrame objPcontrolJFrame, int intPactivationTooltip, int intPdeactivationTooltip) {
		this(objPcontrolJFrame, null, intPactivationTooltip, intPdeactivationTooltip, false);
	}

	public ExtendedJCheckBox(ControlJFrame objPcontrolJFrame, String strPtext, boolean bolPselected) {
		this(objPcontrolJFrame, strPtext, Constants.bytS_UNCLASS_NO_VALUE, Constants.bytS_UNCLASS_NO_VALUE, bolPselected);
	}

	public ExtendedJCheckBox(ControlJFrame objPcontrolJFrame, String strPtext, int intPtooltip, boolean bolPselected) {
		this(objPcontrolJFrame, strPtext, intPtooltip, intPtooltip, bolPselected);
	}

	public ExtendedJCheckBox(	ControlJFrame objPcontrolJFrame,
								String strPtext,
								int intPactivationTooltip,
								int intPdeactivationTooltip,
								boolean bolPselected) {

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGactivationTooltip = intPactivationTooltip;
		this.intGdeactivationTooltip = intPdeactivationTooltip;
		if (strPtext != null) {
			this.setText(strPtext);
		}
		this.setFocusable(false);
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.addItemListener(this);
		this.select(bolPselected);
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	@Override public void itemStateChanged(ItemEvent objPitemEvent) {
		Tools.debug("ExtendedJCheckBox.itemStateChanged(", objPitemEvent.getStateChange() == ItemEvent.SELECTED, ')');
	}

	final public void select(boolean bolPselected) {
		this.removeItemListener(this);
		this.setSelected(bolPselected);
		this.setToolTipText();
		this.addItemListener(this);
	}

	public void setToolTipText() {

		int intLtooltip = Constants.bytS_UNCLASS_NO_VALUE;
		try {
			intLtooltip =
							Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && this.isEnabled()
																																		? this.isSelected()
																																							? this.intGdeactivationTooltip
																																							: this.intGactivationTooltip
																																		: Constants.bytS_UNCLASS_NO_VALUE;
		} catch (final Throwable objPthrowable) {
			intLtooltip = Constants.bytS_UNCLASS_NO_VALUE;
		}
		this.setToolTipText(intLtooltip != Constants.bytS_UNCLASS_NO_VALUE ? this.objGcontrolJFrame.getLanguageString(intLtooltip) : null);
	}

	private final int				intGactivationTooltip;

	private final int				intGdeactivationTooltip;

	protected final ControlJFrame	objGcontrolJFrame;

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
