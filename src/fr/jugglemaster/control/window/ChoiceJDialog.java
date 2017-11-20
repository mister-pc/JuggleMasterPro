/*
 * @(#)ChoiceJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.window;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

// import static java.lang.Math.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ChoiceJDialog extends JDialog implements ActionListener {

	final public static byte	bytS_FIRST_CHOICE	= 1;

	final public static byte	bytS_SECOND_CHOICE	= 2;

	final public static byte	bytS_THIRD_CHOICE	= 3;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public byte					bytGchoice			= Constants.bytS_UNCLASS_NO_VALUE;

	final private ControlJFrame	objGcontrolJFrame;

	ExtendedJButton				objGfirstJButton;

	ExtendedJButton				objGsecondJButton;

	ExtendedJButton				objGthirdJButton;

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				intPfirstButtonToolTipIndex,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				intPsecondButtonToolTipIndex,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				intPfirstButtonToolTipIndex,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				intPsecondButtonToolTipIndex,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdButtonToolTipIndex,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				intPfirstButtonToolTipIndex,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				intPsecondButtonToolTipIndex,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				strPthirdJButton,
				intPthirdButtonToolTipIndex,
				intPthirdIconFileType,
				intPthirdRollOverIconFileType,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdButtonToolTipIndex,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				intPfirstButtonToolTipIndex,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				intPsecondButtonToolTipIndex,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				strPthirdJButton,
				intPthirdButtonToolTipIndex,
				intPthirdIconFileType,
				intPthirdRollOverIconFileType,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdRollOverIconFileType,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdRollOverIconFileType,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdIconFileType,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPdialogTitle
	 * @param bytPiconFileType
	 * @param strPfirstJButton
	 * @param bytPfirstIconFileType
	 * @param strPsecondJButton
	 * @param bytPsecondIconFileType
	 * @param strPlabelA
	 */
	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPcontrolJFrame,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdIconFileType,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				intPfirstButtonToolTipIndex,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				intPsecondButtonToolTipIndex,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				intPfirstButtonToolTipIndex,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				intPsecondButtonToolTipIndex,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdButtonToolTipIndex,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {

		super(objPcontrolJFrame, Strings.doConcat(strPdialogTitle, Strings.strS_ELLIPSIS), true);
		this.objGcontrolJFrame = objPcontrolJFrame;
		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(intPiconFileType, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											10,
																											0,
																											10,
																											10);
		for (final String strLlabel : strPlabelA) {
			this.add(new ExtendedJLabel(objPcontrolJFrame, strLlabel), objLextendedGridBagConstraints);
		}
		final JPanel objLjbuttonsJpanel = new JPanel(new GridLayout(1, 2, 15, 0));
		objLjbuttonsJpanel.setOpaque(true);

		// First button :
		this.objGfirstJButton = new ExtendedJButton(this.objGcontrolJFrame, strPfirstJButton);
		this.objGfirstJButton.setMargin(new Insets(5, 25, 5, 25));
		ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intPfirstIconFileType, 2);
		ImageIcon icoLrollOver =
									intPfirstRollOverIconFileType == intPfirstIconFileType
																							? icoL
																							: this.objGcontrolJFrame.getJuggleMasterPro()
																													.getImageIcon(	intPfirstRollOverIconFileType,
																																	2);
		Tools.setIcons(this.objGfirstJButton, icoL, icoLrollOver);
		this.objGfirstJButton.addActionListener(this);
		this.objGfirstJButton.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																	? this.objGcontrolJFrame.getLanguageString(intPfirstButtonToolTipIndex)
																																	: null);
		objLjbuttonsJpanel.add(this.objGfirstJButton);

		// Second button :
		this.objGsecondJButton = new ExtendedJButton(this.objGcontrolJFrame, strPsecondJButton);
		this.objGsecondJButton.setMargin(new Insets(5, 25, 5, 25));
		icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intPsecondIconFileType, 2);
		icoLrollOver =
						intPsecondRollOverIconFileType == intPsecondIconFileType
																				? icoL
																				: this.objGcontrolJFrame.getJuggleMasterPro()
																										.getImageIcon(	intPsecondRollOverIconFileType,
																														2);
		Tools.setIcons(this.objGsecondJButton, icoL, icoLrollOver);
		this.objGsecondJButton.addActionListener(this);
		this.objGsecondJButton.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																	? this.objGcontrolJFrame.getLanguageString(intPsecondButtonToolTipIndex)
																																	: null);
		objLjbuttonsJpanel.add(this.objGsecondJButton);

		// Third button :
		if (strPthirdJButton != null) {
			this.objGthirdJButton = new ExtendedJButton(this.objGcontrolJFrame, strPthirdJButton);
			this.objGthirdJButton.setMargin(new Insets(5, 25, 5, 25));
			icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intPthirdIconFileType, 2);
			icoLrollOver =
							intPthirdRollOverIconFileType == intPthirdIconFileType
																					? icoL
																					: this.objGcontrolJFrame.getJuggleMasterPro()
																											.getImageIcon(	intPthirdRollOverIconFileType,
																															2);
			Tools.setIcons(this.objGthirdJButton, icoL, icoLrollOver);
			this.objGthirdJButton.addActionListener(this);
			this.objGthirdJButton.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																		? this.objGcontrolJFrame.getLanguageString(intPthirdButtonToolTipIndex)
																																		: null);
			objLjbuttonsJpanel.add(this.objGthirdJButton);
		} else {
			this.objGthirdJButton = null;
		}

		// Panel :
		objLextendedGridBagConstraints.setMargins(15, 10, 10, 10);
		this.add(objLjbuttonsJpanel, objLextendedGridBagConstraints);
		this.validate();
		this.pack();
		this.objGcontrolJFrame.setWindowBounds(this, objPparentWindow != objPcontrolJFrame ? objPparentWindow : null);
		this.setResizable(false);

		// Focus :
		switch (bytPfocusedJButtonIndex) {
			case ChoiceJDialog.bytS_SECOND_CHOICE:
				this.objGsecondJButton.requestFocusInWindow();
				break;
			case ChoiceJDialog.bytS_THIRD_CHOICE:
				if (this.objGthirdJButton != null) {
					this.objGthirdJButton.requestFocusInWindow();
					break;
				}
				//$FALL-THROUGH$
			case ChoiceJDialog.bytS_FIRST_CHOICE:
			default:
				this.objGfirstJButton.requestFocusInWindow();
		}
		this.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this, true));
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstButtonToolTipIndex,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondButtonToolTipIndex,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdButtonToolTipIndex,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				intPfirstButtonToolTipIndex,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				intPsecondButtonToolTipIndex,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				strPthirdJButton,
				intPthirdButtonToolTipIndex,
				intPthirdIconFileType,
				intPthirdRollOverIconFileType,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	/**
	 * @param objGcontrolJFrame2
	 * @param preferencesJDialog
	 * @param languageString
	 * @param intsFileIconAlert
	 * @param languageString2
	 * @param intsFileIconOkBw
	 * @param intsFileIconOk
	 * @param languageString3
	 * @param intsFileIconCancelBw
	 * @param intsFileIconCancel
	 * @param b
	 * @param languageString4
	 */
	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							byte bytPfocusedJButtonIndex,
							String strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdRollOverIconFileType,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							int intPfirstRollOverIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							int intPsecondRollOverIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							int intPthirdRollOverIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstRollOverIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondRollOverIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdRollOverIconFileType,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				null,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				Constants.bytS_UNCLASS_NO_VALUE,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							byte bytPfocusedJButtonIndex,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdIconFileType,
				bytPfocusedJButtonIndex,
				strPlabelA);
	}

	public ChoiceJDialog(	ControlJFrame objPcontrolJFrame,
							Window objPparentWindow,
							String strPdialogTitle,
							int intPiconFileType,
							String strPfirstJButton,
							int intPfirstIconFileType,
							String strPsecondJButton,
							int intPsecondIconFileType,
							String strPthirdJButton,
							int intPthirdIconFileType,
							String... strPlabelA) {
		this(	objPcontrolJFrame,
				objPparentWindow,
				strPdialogTitle,
				intPiconFileType,
				strPfirstJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPfirstIconFileType,
				intPfirstIconFileType,
				strPsecondJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPsecondIconFileType,
				intPsecondIconFileType,
				strPthirdJButton,
				Constants.bytS_UNCLASS_NO_VALUE,
				intPthirdIconFileType,
				intPthirdIconFileType,
				ChoiceJDialog.bytS_FIRST_CHOICE,
				strPlabelA);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("ChoiceJDialog.actionPerformed()");
		final JButton objLactionedJButton = ((JButton) (objPactionEvent.getSource()));
		this.bytGchoice =
							(objLactionedJButton == this.objGfirstJButton
																			? ChoiceJDialog.bytS_FIRST_CHOICE
																			: objLactionedJButton == this.objGsecondJButton
																															? ChoiceJDialog.bytS_SECOND_CHOICE
																															: this.objGthirdJButton != null
																																&& objLactionedJButton == this.objGthirdJButton
																																												? ChoiceJDialog.bytS_THIRD_CHOICE
																																												: Constants.bytS_UNCLASS_NO_VALUE);
		this.setVisible(false);
	}
}

/*
 * @(#)ChoiceJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
