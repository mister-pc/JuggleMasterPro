package fr.jugglemaster.control.pref;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

public final class PreferenceByteGlobalJScrollBar extends JScrollBar implements AdjustmentListener {

	/**
	 * Constructs
	 * 
	 * @param objPpreferencesJDialog
	 * @param bytPpreferenceType
	 */
	public PreferenceByteGlobalJScrollBar(PreferencesJDialog objPpreferencesJDialog, byte bytPpreferenceType) {
		super(Adjustable.HORIZONTAL);

		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGpreferenceType = bytPpreferenceType;
		this.setOpaque(true);
		switch (this.bytGpreferenceType) {
			case Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION:
				this.setValues(	this.objGpreferencesJDialog.bytGbyteGlobalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_INITIAL],
								1,
								Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_MINIMUM_VALUE,
								Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_MAXIMUM_VALUE + 1);
				this.setBlockIncrement(3);
				break;
		}

		this.addAdjustmentListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPadjustmentEvent
	 */
	@Override final public void adjustmentValueChanged(AdjustmentEvent objPadjustmentEvent) {

		Tools.debug("PreferenceByteGlobalJScrollBar.adjustmentValueChanged()");
		final byte bytLpreviousValue = this.objGpreferencesJDialog.bytGbyteGlobalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_CURRENT];
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGpreferencesJDialog.doApplyByteGlobalScrollChange(this.bytGpreferenceType, bytLcurrentValue);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPvalue
	 */
	final public void selectValue(int intPvalue) {
		this.removeAdjustmentListener(this);
		this.setValue(intPvalue);
		this.addAdjustmentListener(this);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final byte					bytGpreferenceType;

	final PreferencesJDialog	objGpreferencesJDialog;
}
