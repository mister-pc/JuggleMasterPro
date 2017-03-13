/*
 * @(#)PreferenceBooleanJLabel.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.event.MouseEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJLabel;
import jugglemasterpro.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferenceByteGlobalJLabel extends ExtendedJLabel {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param bytPpreferenceType
	 */
	public PreferenceByteGlobalJLabel(ControlJFrame objPcontrolJFrame, PreferencesJDialog objPpreferencesJDialog, byte bytPpreferenceType) {
		super(objPcontrolJFrame, objPcontrolJFrame.getLanguageString(objPpreferencesJDialog.intGbyteGlobalLanguageIndexA[bytPpreferenceType]));

		this.intGlanguageIndex = objPpreferencesJDialog.intGbyteGlobalLanguageIndexA[bytPpreferenceType];;
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGpreferenceType = bytPpreferenceType;
		this.addMouseListener(this);
	}

	@Override public void mouseClicked(MouseEvent objPmouseEvent) {
		byte bytLbooleanGlobalType = Constants.bytS_UNCLASS_NO_VALUE;
		switch (this.bytGpreferenceType) {
			case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
			case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
				bytLbooleanGlobalType = Constants.bytS_BOOLEAN_GLOBAL_SKILL;
				break;
			case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
			case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
				bytLbooleanGlobalType = Constants.bytS_BOOLEAN_GLOBAL_MARK;
				break;
			case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
			case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
				bytLbooleanGlobalType = Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER;
				break;
			default:
				return;
		}
		this.objGpreferencesJDialog.doApplyBooleanGlobalCheckChange(bytLbooleanGlobalType,
																	!this.objGpreferencesJDialog.objGbooleanGlobalJCheckBoxA[bytLbooleanGlobalType].isSelected());
	}

	final public void setText() {
		this.setText(this.objGpreferencesJDialog.getLanguageString(this.intGlanguageIndex));
	}

	final byte					bytGpreferenceType;

	private final int			intGlanguageIndex;

	final ControlJFrame			objGcontrolJFrame;

	final PreferencesJDialog	objGpreferencesJDialog;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PreferenceBooleanJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
