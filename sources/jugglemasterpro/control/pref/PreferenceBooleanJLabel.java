/*
 * @(#)PreferenceBooleanJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.event.MouseEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJLabel;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferenceBooleanJLabel extends ExtendedJLabel {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param bytPpreferenceType
	 * @param bolPglobalPreference
	 */
	public PreferenceBooleanJLabel(	ControlJFrame objPcontrolJFrame,
									PreferencesJDialog objPpreferencesJDialog,
									byte bytPpreferenceType,
									boolean bolPglobalPreference,
									int intPlanguageIndex) {
		super(objPcontrolJFrame);

		this.intGlanguageIndex = intPlanguageIndex;
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGpreferenceType = bytPpreferenceType;
		this.bolGglobalPreference = bolPglobalPreference;
		this.setText();
		this.addMouseListener(this);
	}

	@Override public void mouseClicked(MouseEvent objPmouseEvent) {
		Tools.debug("PreferenceBooleanJLabel.mouseClicked()");
		if (this.bolGglobalPreference) {
			final boolean bolLselected = !this.objGpreferencesJDialog.bolGbooleanGlobalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_CURRENT];
			this.objGpreferencesJDialog.doApplyBooleanGlobalCheckChange(this.bytGpreferenceType, bolLselected);
			this.objGpreferencesJDialog.objGbooleanGlobalJCheckBoxA[this.bytGpreferenceType].select(bolLselected);
		} else {
			final boolean bolLselected = !this.objGpreferencesJDialog.bolGbooleanLocalAA[this.bytGpreferenceType][Constants.bytS_UNCLASS_CURRENT];
			this.objGpreferencesJDialog.doApplyBooleanLocalCheckChange(this.bytGpreferenceType, bolLselected);
			this.objGpreferencesJDialog.objGbooleanLocalJCheckBoxA[this.bytGpreferenceType].select(bolLselected);
		}
	}

	final public void setText() {
		this.setText(this.objGpreferencesJDialog.getLanguageString(this.intGlanguageIndex));
	}

	final boolean				bolGglobalPreference;

	final byte					bytGpreferenceType;

	private final int			intGlanguageIndex;

	final ControlJFrame			objGcontrolJFrame;

	final PreferencesJDialog	objGpreferencesJDialog;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PreferenceBooleanJLabel.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
