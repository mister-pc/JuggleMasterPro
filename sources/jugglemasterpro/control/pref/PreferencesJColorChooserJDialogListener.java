/*
 * @(#)PreferencesJColorChooserJDialogListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferencesJColorChooserJDialogListener implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPpreferencesJDialog
	 * @param objPjColorChooser
	 * @param bytPcolorPreferenceType
	 * @param bolPaccept
	 */
	public PreferencesJColorChooserJDialogListener(	PreferencesJDialog objPpreferencesJDialog,
													PreferencesJColorChooser objPjColorChooser,
													byte bytPcolorPreferenceType,
													boolean bolPaccept) {
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.objGjColorChooser = objPjColorChooser;
		this.bytGcolorPreferenceType = bytPcolorPreferenceType;
		this.bolGaccept = bolPaccept;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("PreferencesJColorChooserJDialogListener.actionPerformed()");
		// Accept or cancel color chooser dialog choice :
		if (this.bolGaccept) {
			this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_CURRENT] =
																															this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_TEMPORARY_CURRENT];
		} else {
			this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																	this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_CURRENT];
			this.objGjColorChooser.setColor(Tools.getPenColor(this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_CURRENT]));
			this.objGpreferencesJDialog.setDialogJButtonsEnabled();
		}
		this.objGpreferencesJDialog.setJButtonsColors();
		Tools.debug("PreferencesJColorChooserJDialogListener.actionPerformed()");
		// Tools.verbose(this.objGpreferencesJDialog.getInfo());
	}

	final boolean					bolGaccept;
	final byte						bytGcolorPreferenceType;
	final PreferencesJColorChooser	objGjColorChooser;

	final PreferencesJDialog		objGpreferencesJDialog;

	@SuppressWarnings("unused")
	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PreferencesJColorChooserJDialogListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
