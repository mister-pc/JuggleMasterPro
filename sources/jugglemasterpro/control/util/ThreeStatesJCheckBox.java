package jugglemasterpro.control.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JCheckBox;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;

abstract public class ThreeStatesJCheckBox extends JCheckBox implements MouseListener {

	public ThreeStatesJCheckBox(ControlJFrame objPcontrolJFrame, int intPactivateTooltip, int intPstronglyActivateTooltip, int intPdeactivateTooltip) {

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGthreeStatesButtonModel = new ThreeStatesButtonModel(this.getModel());
		this.intGactivateTooltip = intPactivateTooltip;
		this.intGstronglyActivateTooltip = intPstronglyActivateTooltip;
		this.intGdeactivateTooltip = intPdeactivateTooltip;
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setModel(this.objGthreeStatesButtonModel);
		this.setFocusable(false);
		super.addMouseListener(this);
	}

	@Override final public synchronized void addMouseListener(MouseListener objPmouseListener) {}

	final public Boolean getSelected() {
		return this.objGthreeStatesButtonModel.getState();
	}

	@Override final public boolean isSelected() {
		return this.objGthreeStatesButtonModel.getState() != ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN;
	}

	abstract public void itemStateChanged(Boolean bolPstateBoolean);

	@Override final public void mouseClicked(MouseEvent objPmouseEvent) {}

	@Override final public void mouseEntered(MouseEvent objPmouseEvent) {}

	@Override final public void mouseExited(MouseEvent objPmouseEvent) {}

	@Override final public void mousePressed(MouseEvent objPmouseEvent) {
		if (this.isEnabled()) {
			this.objGthreeStatesButtonModel.nextState();
			this.itemStateChanged(this.getSelected());
		}
	}

	@Override final public void mouseReleased(MouseEvent objPmouseEvent) {}

	final public void select(Boolean bolPBoolean) {
		this.objGthreeStatesButtonModel.setState(bolPBoolean);
		// this.setToolTipText();
	}

	@Override final public void setSelected(boolean bolPselected) {
		this.select(bolPselected ? ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN : ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
	}

	public final void setToolTipText() {

		int intLtooltip = Constants.bytS_UNCLASS_NO_VALUE;
		if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
			final Boolean bolLselectedBoolean = this.getSelected();
			if (bolLselectedBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN) {
				intLtooltip = this.intGstronglyActivateTooltip;
			} else if (bolLselectedBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN) {
				intLtooltip = this.intGdeactivateTooltip;
			} else {
				intLtooltip = this.intGactivateTooltip;
			}
		}
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
							&& intLtooltip != Constants.bytS_UNCLASS_NO_VALUE ? this.objGcontrolJFrame.getLanguageString(intLtooltip) : null);
	}

	final private int						intGactivateTooltip;

	final private int						intGdeactivateTooltip;

	final private int						intGstronglyActivateTooltip;

	final protected ControlJFrame			objGcontrolJFrame;

	final protected ThreeStatesButtonModel	objGthreeStatesButtonModel;

	/**
	 * 
	 */
	final private static long				serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
