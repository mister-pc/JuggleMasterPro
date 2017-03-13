package jugglemasterpro.control.util;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import javax.swing.JToolTip;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

public class ExtendedJScrollBar extends JScrollBar implements AdjustmentListener {

	public ExtendedJScrollBar(ControlJFrame objPcontrolJFrame, int intP1, int intP2, int intP3, int intP4, int intPblockIncrement) {
		this(objPcontrolJFrame, intP1, intP2, intP3, intP4, intPblockIncrement, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJScrollBar(ControlJFrame objPcontrolJFrame, int intP1, int intP2, int intP3, int intP4, int intPblockIncrement, int intPtooltip) {

		super(Adjustable.HORIZONTAL, intP1, intP2, intP3, intP4);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGtooltip = intPtooltip;
		this.setBlockIncrement(intPblockIncrement);
		this.setOpaque(true);
		this.setFocusable(false);
		this.addAdjustmentListener(this);
	}

	@Override public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {}

	@Override final public JToolTip createToolTip() {
		return this.objGcontrolJFrame != null ? Tools.getJuggleToolTip(this.objGcontrolJFrame) : null;
	}

	final public void selectValue(int intPvalue) {
		this.removeAdjustmentListener(this);
		this.setValue(intPvalue);
		// this.setToolTipText();
		this.addAdjustmentListener(this);
	}

	final public void setToolTipText() {
		final int intLtooltip =
								Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
									&& this.objGcontrolJFrame != null
									&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																														? this.intGtooltip
																														: Constants.bytS_UNCLASS_NO_VALUE;
		this.setToolTipText(intLtooltip != Constants.bytS_UNCLASS_NO_VALUE ? this.objGcontrolJFrame.getLanguageString(this.intGtooltip) : null);
	}

	final protected int				intGtooltip;

	protected final ControlJFrame	objGcontrolJFrame;

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
