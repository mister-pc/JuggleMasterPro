/*
 * @(#)PopUpJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.window;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JPanel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.util.ExtendedJCheckBox;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PopUpJDialog extends JDialog {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public ExtendedJCheckBox	objGdoNotDisplayPopUpJCheckBox;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPglobalBooleanPreference
	 * @param bytPiconFileType
	 * @param strPpopUpTitle
	 * @param strPfirstLine
	 * @param strPsecondLine
	 * @param bolPchecked
	 */
	public PopUpJDialog(ControlJFrame objPcontrolJFrame,
						byte bytPglobalBooleanPreference,
						int intPiconFileType,
						String strPpopUpTitle,
						String strPfirstLine,
						String strPsecondLine,
						boolean bolPchecked) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				bytPglobalBooleanPreference,
				intPiconFileType,
				strPpopUpTitle,
				strPfirstLine,
				strPsecondLine,
				bolPchecked);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPownerDialog
	 * @param bytPglobalBooleanPreference
	 * @param bytPiconFileType
	 * @param strPpopUpTitle
	 * @param strPfirstLine
	 * @param strPsecondLine
	 * @param bolPchecked
	 */
	public PopUpJDialog(ControlJFrame objPcontrolJFrame,
						Window objPownerWindow,
						byte bytPglobalBooleanPreference,
						int intPiconFileType,
						String strPpopUpTitle,
						String strPfirstLine,
						String strPsecondLine,
						boolean bolPchecked) {
		super(objPownerWindow, Strings.doConcat(strPpopUpTitle, Strings.strS_ELLIPSIS), Dialog.ModalityType.APPLICATION_MODAL);
		if (bytPglobalBooleanPreference == Constants.bytS_UNCLASS_NO_VALUE || Preferences.getGlobalBooleanPreference(bytPglobalBooleanPreference)) {
			this.buildPopUp(objPcontrolJFrame, bytPglobalBooleanPreference, intPiconFileType, strPfirstLine, strPsecondLine, bolPchecked);
		} else {
			this.dispose();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPownerObject
	 * @param bytPglobalBooleanPreference
	 * @param bytPiconFileType
	 * @param strPfirstLine
	 * @param strPsecondLine
	 * @param bolPchecked
	 */
	final private void buildPopUp(	ControlJFrame objPcontrolJFrame,
									byte bytPglobalBooleanPreference,
									int intPiconFileType,
									String strPfirstLine,
									String strPsecondLine,
									boolean bolPchecked) {

		final boolean bolLdisplaySecondLine = !Tools.isEmpty(strPsecondLine);
		final JPanel objLjPanel = new JPanel(new GridBagLayout());
		objLjPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLExtendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											10,
																											5,
																											10,
																											10);

		// Add message :
		ExtendedJLabel objLjLabel = new ExtendedJLabel(objPcontrolJFrame, strPfirstLine);
		objLjPanel.add(objLjLabel, objLExtendedGridBagConstraints);
		if (bolLdisplaySecondLine) {
			objLExtendedGridBagConstraints.setMargins(0, 5, 10, 10);
			objLjLabel = new ExtendedJLabel(objPcontrolJFrame, strPsecondLine);
			objLjPanel.add(objLjLabel, objLExtendedGridBagConstraints);
		}

		// Add OK button :
		final PopUpJButton objLoKJButton =
											new PopUpJButton(	objPcontrolJFrame,
																this,
																Strings.doConcat(	"   ",
																					objPcontrolJFrame.getLanguageString(Language.intS_BUTTON_OK),
																					"   "),
																Language.intS_TOOLTIP_CLOSE_POP_UP,
																bytPglobalBooleanPreference);
		objLoKJButton.setOpaque(true);
		this.getRootPane().setDefaultButton(objLoKJButton);
		objLExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, Constants.objS_GRAPHICS_FONT_METRICS.getAscent());
		objLExtendedGridBagConstraints.setMargins(10, 10, 10, 10);
		objLjPanel.add(objLoKJButton, objLExtendedGridBagConstraints);

		// Add checkbox :
		if (bytPglobalBooleanPreference != Constants.bytS_UNCLASS_NO_VALUE) {
			this.objGdoNotDisplayPopUpJCheckBox =
													new ExtendedJCheckBox(	objPcontrolJFrame,
																			objPcontrolJFrame.getLanguageString(Language.intS_CHECKBOX_DO_NOT_DISPLAY_THIS_MESSAGE_ANYMORE),
																			Language.intS_TOOLTIP_CONFIGURE_POP_UP_DISPLAY_PROPERTY,
																			bolPchecked);
			this.objGdoNotDisplayPopUpJCheckBox.setToolTipText();
			objLExtendedGridBagConstraints.setInside(GridBagConstraints.SOUTHWEST, 0, 0);
			objLExtendedGridBagConstraints.setMargins(0, 10, 10, 10);
			objLjPanel.add(this.objGdoNotDisplayPopUpJCheckBox, objLExtendedGridBagConstraints);
		}

		// Dialog frame properties :
		this.add(objLjPanel);
		if (intPiconFileType != Constants.bytS_UNCLASS_NO_VALUE) {
			this.setIconImage(objPcontrolJFrame.getJuggleMasterPro().getImage(intPiconFileType, 0));
		}

		this.validate();
		this.pack();
		objPcontrolJFrame.setWindowBounds(this);
		this.addWindowListener(new JDialogWindowListener(objPcontrolJFrame, this, true));
		this.setResizable(false);
		this.setVisible(true);
	}
}

/*
 * @(#)PopUpJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
