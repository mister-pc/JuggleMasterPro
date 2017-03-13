/*
 * @(#)PreferenceBooleanJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.event.ItemEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJCheckBox;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferenceBooleanJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param bytPpreferenceType
	 * @param bolPglobalPreference
	 */
	public PreferenceBooleanJCheckBox(	ControlJFrame objPcontrolJFrame,
										PreferencesJDialog objPpreferencesJDialog,
										byte bytPpreferenceType,
										boolean bolPglobalPreference) {
		super(	objPcontrolJFrame,
				bolPglobalPreference ? objPpreferencesJDialog.bolGbooleanGlobalAA[bytPpreferenceType][Constants.bytS_UNCLASS_INITIAL]
									: objPpreferencesJDialog.bolGbooleanLocalAA[bytPpreferenceType][Constants.bytS_UNCLASS_INITIAL]);

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGpreferenceType = bytPpreferenceType;
		this.bolGglobalPreference = bolPglobalPreference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("PreferenceBooleanJCheckBox.itemStateChanged()");
		if (this.bolGglobalPreference) {
			this.objGpreferencesJDialog.doApplyBooleanGlobalCheckChange(this.bytGpreferenceType, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		} else {
			this.objGpreferencesJDialog.doApplyBooleanLocalCheckChange(this.bytGpreferenceType, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		}
	}

	final boolean				bolGglobalPreference;

	final byte					bytGpreferenceType;

	final ControlJFrame			objGcontrolJFrame;

	final PreferencesJDialog	objGpreferencesJDialog;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PreferenceBooleanJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
