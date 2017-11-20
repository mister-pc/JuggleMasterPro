/*
 * @(#)PreferencesDialogJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.pref;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferencesDialogJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param intPlanguageIndex
	 */
	public PreferencesDialogJButton(ControlJFrame objPcontrolJFrame, PreferencesJDialog objPpreferencesJDialog, int intPlanguageIndex) {
		this(objPcontrolJFrame, objPpreferencesJDialog, intPlanguageIndex, false, false, true);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param intPlanguageIndex
	 * @param bolPsavePreferences
	 * @param bolPcloseDialog
	 */
	public PreferencesDialogJButton(ControlJFrame objPcontrolJFrame,
									PreferencesJDialog objPpreferencesJDialog,
									int intPlanguageIndex,
									boolean bolPsavePreferences,
									boolean bolPcloseDialog) {
		this(objPcontrolJFrame, objPpreferencesJDialog, intPlanguageIndex, bolPsavePreferences, bolPcloseDialog, false);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param intPlanguageIndex
	 * @param bolPsavePreferences
	 * @param bolPcloseDialog
	 * @param bolPresetPreferences
	 */
	private PreferencesDialogJButton(	ControlJFrame objPcontrolJFrame,
										PreferencesJDialog objPpreferencesJDialog,
										int intPlanguageIndex,
										boolean bolPsavePreferences,
										boolean bolPcloseDialog,
										boolean bolPresetPreferences) {
		super(objPcontrolJFrame, objPcontrolJFrame.getLanguageString(intPlanguageIndex), Constants.objS_GRAPHICS_FONT_METRICS.getAscent() / 2);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.intGlanguageIndex = intPlanguageIndex;
		this.bolGsavePreferences = bolPsavePreferences;
		this.bolGresetPreferences = bolPresetPreferences;
		this.bolGcloseDialog = bolPcloseDialog;
		this.doLoadImages();
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("PreferencesDialogJButton.actionPerformed()");
		this.objGpreferencesJDialog.setMouseCursorEnabled(false);

		// Apply changes :
		if (this.bolGresetPreferences) {
			this.objGpreferencesJDialog.doReset();
		} else if (this.bolGsavePreferences) {
			this.objGpreferencesJDialog.doApply();

			// Cancel changes :
		} else {
			this.objGpreferencesJDialog.doCancel();
		}

		Tools.debug("PreferencesDialogJButton.actionPerformed()");
		// Tools.debug(this.objGpreferencesJDialog.getInfo());
		Tools.debug(Preferences.getInfo());

		this.objGpreferencesJDialog.setMouseCursorEnabled(true);

		// Close dialog :
		if (this.bolGcloseDialog) {

			// for (byte bytLstringPreferenceIndex = 0; bytLstringPreferenceIndex < bytS_STRING_LOCAL_CONTROLS_NUMBER;
			// ++bytLstringPreferenceIndex) {
			// objGstringLocalPreferenceColorJButtonA[bytLstringPreferenceIndex].disposeJDialog();
			// }
			for (final PreferenceStringLocalColorJButton objLcolorPreferenceJButton : this.objGpreferencesJDialog.objGstringLocalColorJButtonA) {
				objLcolorPreferenceJButton.disposeJDialog();
			}
			this.objGpreferencesJDialog.setVisible(false);
			this.objGcontrolJFrame.objGpreferencesJDialog = null;
			this.objGpreferencesJDialog.dispose();
		} else {
			if (!this.bolGsavePreferences) {
				this.objGpreferencesJDialog.setWidgetsValues();
			}
			this.objGpreferencesJDialog.setDialogJButtonsEnabled();
		}
	}

	final public void doLoadImages() {

		int intLiconFile =
							this.bolGresetPreferences ? Constants.intS_FILE_ICON_RESET_BW
														: this.bolGsavePreferences ? this.bolGcloseDialog ? Constants.intS_FILE_ICON_OK_BW
																											: Constants.intS_FILE_ICON_APPLY_BW
																					: this.bolGcloseDialog ? Constants.intS_FILE_ICON_CANCEL_BW
																											: Constants.intS_FILE_ICON_RESTORE_BW;
		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFile, 2);

		intLiconFile =
						this.bolGresetPreferences ? Constants.intS_FILE_ICON_RESET
													: this.bolGsavePreferences ? this.bolGcloseDialog ? Constants.intS_FILE_ICON_OK
																										: Constants.intS_FILE_ICON_APPLY
																				: this.bolGcloseDialog ? Constants.intS_FILE_ICON_CANCEL
																										: Constants.intS_FILE_ICON_RESTORE;
		final ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFile, 2);

		Tools.setIcons(this, icoL, icoLrollOver);
	}

	final public void setText() {
		this.setText(this.objGpreferencesJDialog.getLanguageString(this.intGlanguageIndex));
	}

	final boolean				bolGcloseDialog;
	final boolean				bolGresetPreferences;

	final boolean				bolGsavePreferences;

	final int					intGlanguageIndex;

	BufferedImage				objGbufferedImage;

	final ControlJFrame			objGcontrolJFrame;

	final PreferencesJDialog	objGpreferencesJDialog;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PreferencesDialogJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
