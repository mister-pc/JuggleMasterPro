/*
 * @(#)PreferenceByteLocalJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferenceByteLocalJScrollBar extends JScrollBar implements AdjustmentListener {

	/**
	 * Constructs
	 * 
	 * @param objPpreferencesJDialog
	 * @param bytPpreferenceType
	 */
	public PreferenceByteLocalJScrollBar(PreferencesJDialog objPpreferencesJDialog, byte bytPpreferenceType) {
		super(Adjustable.HORIZONTAL);

		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGpreferenceType = bytPpreferenceType;
		this.setOpaque(true);
		switch (this.bytGpreferenceType) {
			case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED:
				this.setValues(	this.objGpreferencesJDialog.bytGbyteLocalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_INITIAL],
								1,
								Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE,
								Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE + 1);
				this.setBlockIncrement(5);
				break;
			case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS:
				this.setValues(	this.objGpreferencesJDialog.bytGbyteLocalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_INITIAL],
								1,
								Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE,
								Constants.bytS_BYTE_LOCAL_BALLS_MAXIMUM_VALUE + 1);
				this.setBlockIncrement(5);
				break;
			case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY:
				this.setValues(	this.objGpreferencesJDialog.bytGbyteLocalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_INITIAL],
								1,
								Constants.bytS_BYTE_LOCAL_FLUIDITY_MINIMUM_VALUE,
								Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE + 1);
				this.setBlockIncrement(10);

				//$FALL-THROUGH$
			default:
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

		Tools.debug("PreferenceByteLocalJScrollBar.adjustmentValueChanged()");
		final byte bytLpreviousValue = this.objGpreferencesJDialog.bytGbyteLocalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_CURRENT];
		final byte bytLcurrentValue = (byte) objPadjustmentEvent.getValue();
		if (bytLcurrentValue != bytLpreviousValue) {
			this.objGpreferencesJDialog.doApplyByteLocalScrollChange(this.bytGpreferenceType, bytLcurrentValue);
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

/*
 * @(#)PreferenceByteLocalJScrollBar.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
