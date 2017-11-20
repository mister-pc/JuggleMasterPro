package fr.jugglemaster.control.util;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;

import fr.jugglemaster.util.Constants;

final public class ThreeStatesButtonModel implements ButtonModel {

	public static final Boolean	bolS_SELECTED_BOOLEAN			= null;
	public static final Boolean	bolS_STRONGLY_SELECTED_BOOLEAN	= true;
	public static final Boolean	bolS_UNSELECTED_BOOLEAN			= false;
	@SuppressWarnings("unused")
	final private static long	serialVersionUID				= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final ButtonModel	objGbuttonModel;

	public ThreeStatesButtonModel(ButtonModel objPbuttonModel) {
		this.objGbuttonModel = objPbuttonModel;
	}

	@Override final public void addActionListener(ActionListener objPactionListener) {
		this.objGbuttonModel.addActionListener(objPactionListener);
	}

	@Override final public void addChangeListener(ChangeListener objPchangeListener) {
		this.objGbuttonModel.addChangeListener(objPchangeListener);
	}

	@Override final public void addItemListener(ItemListener objPitemListener) {
		this.objGbuttonModel.addItemListener(objPitemListener);
	}

	@Override final public String getActionCommand() {
		return this.objGbuttonModel.getActionCommand();
	}

	@Override final public int getMnemonic() {
		return this.objGbuttonModel.getMnemonic();
	}

	@Override final public Object[] getSelectedObjects() {
		return this.objGbuttonModel.getSelectedObjects();
	}

	final public Boolean getState() {

		return this.isSelected() ? this.isArmed() ? ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
													: ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN : ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN;
	}

	@Override final public boolean isArmed() {
		return this.objGbuttonModel.isArmed();
	}

	@Override final public boolean isEnabled() {
		return this.objGbuttonModel.isEnabled();
	}

	@Override final public boolean isPressed() {
		return this.objGbuttonModel.isPressed();
	}

	@Override final public boolean isRollover() {
		return this.objGbuttonModel.isRollover();
	}

	@Override final public boolean isSelected() {
		return this.objGbuttonModel.isSelected();
	}

	final public void nextState() {
		final Boolean bolLBoolean = this.getState();
		Boolean bolLnextBoolean = ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN;
		if (bolLBoolean == ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN) {
			bolLnextBoolean = ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN;
		} else if (bolLBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN) {
			bolLnextBoolean = ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN;
		}
		this.setState(bolLnextBoolean);
	}

	@Override final public void removeActionListener(ActionListener objPactionListener) {
		this.objGbuttonModel.removeActionListener(objPactionListener);
	}

	@Override final public void removeChangeListener(ChangeListener objPchangeListener) {
		this.objGbuttonModel.removeChangeListener(objPchangeListener);
	}

	@Override final public void removeItemListener(ItemListener objPitemListener) {
		this.objGbuttonModel.removeItemListener(objPitemListener);
	}

	@Override final public void setActionCommand(String strPaction) {
		this.objGbuttonModel.setActionCommand(strPaction);
	}

	@Override final public void setArmed(boolean b) {}

	@Override final public void setEnabled(boolean b) {
		this.objGbuttonModel.setEnabled(b);
	}

	@Override final public void setGroup(ButtonGroup objPbuttonGroup) {
		this.objGbuttonModel.setGroup(objPbuttonGroup);
	}

	@Override final public void setMnemonic(int intPkey) {
		this.objGbuttonModel.setMnemonic(intPkey);
	}

	@Override final public void setPressed(boolean bolPpressed) {
		this.objGbuttonModel.setPressed(bolPpressed);
	}

	@Override final public void setRollover(boolean bolProllOver) {
		this.objGbuttonModel.setRollover(bolProllOver);
	}

	@Override final public void setSelected(boolean bolPselected) {
		this.objGbuttonModel.setSelected(bolPselected);
	}

	final public void setState(Boolean bolPBoolean) {

		if (bolPBoolean == ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN) {
			this.objGbuttonModel.setArmed(false);
			this.setPressed(false);
			this.setSelected(false);
		} else if (bolPBoolean == ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN) {
			this.objGbuttonModel.setArmed(false);
			this.setPressed(false);
			this.setSelected(true);
		} else {
			this.objGbuttonModel.setArmed(true);
			this.setPressed(true);
			this.setSelected(true);
		}
	}
}
