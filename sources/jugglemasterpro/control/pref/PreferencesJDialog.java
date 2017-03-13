/*
 * @(#)PreferencesJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.criteria.CriteriaActions;
import jugglemasterpro.control.criteria.MarkJButton;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.control.util.ExtendedJLabel;
import jugglemasterpro.control.util.ExtendedJPanel;
import jugglemasterpro.control.window.ChoiceJDialog;
import jugglemasterpro.pattern.PatternsManager;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferencesJDialog extends JDialog implements WindowListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public PreferencesJDialog(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, objPcontrolJFrame.getLanguageString(Language.intS_TITLE_PREFERENCES), true);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGcontrolJFrame.objGpreferencesJDialog = this;

		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_PREFERENCES, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
		this.setLanguageCorrespondances();
		this.initDefaultValues();
		this.doCreateWidgets();
		this.doAddWidgets();
		this.setByteGlobalJLabels();
		// this.setStringGlobalJLabels();
		this.setJDialogDefaultKeys();
		this.addWindowListener(this);
		Tools.out(Preferences.getInfo());
		this.setVisible(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doAddWidgets() {

		// Prepare grid bag constraints :
		final ExtendedGridBagConstraints objLjcenteredExtendedGridBagConstraints =
																					new ExtendedGridBagConstraints(	GridBagConstraints.BOTH,
																													0.1F,
																													0.1F);
		// Message preferences :
		final JPanel objLpreferencesMessagesJPanel = new ExtendedJPanel();
		final ExtendedGridBagConstraints objLmessagesExtendedGridBagConstraints = new ExtendedGridBagConstraints();
		objLmessagesExtendedGridBagConstraints.setVerticalMargins(10);
		objLmessagesExtendedGridBagConstraints.setHorizontalMargins(5);
		objLpreferencesMessagesJPanel.add(new ExtendedJPanel(), objLmessagesExtendedGridBagConstraints);
		objLmessagesExtendedGridBagConstraints.setWeights(0.8F);
		objLpreferencesMessagesJPanel.add(this.getMessagesJPanel(), objLmessagesExtendedGridBagConstraints);
		objLmessagesExtendedGridBagConstraints.setWeights(0.1F);
		objLpreferencesMessagesJPanel.add(new ExtendedJPanel(), objLmessagesExtendedGridBagConstraints);

		// Value preferences :
		final JPanel objLpreferencesValuesJPanel = new JPanel(new GridBagLayout());
		objLpreferencesValuesJPanel.setOpaque(true);
		objLpreferencesValuesJPanel.add(this.getValuesJPanel(), objLjcenteredExtendedGridBagConstraints);

		// Filter preferences :
		final JPanel objLpreferencesFilterJPanel = new JPanel(new GridBagLayout());
		objLpreferencesFilterJPanel.setOpaque(true);
		objLpreferencesFilterJPanel.add(this.getFiltersJPanel(), objLjcenteredExtendedGridBagConstraints);

		// Sound preferences :
		final JPanel objLpreferencesSoundsJPanel = new JPanel(new GridBagLayout());
		objLpreferencesSoundsJPanel.setOpaque(true);
		objLpreferencesSoundsJPanel.add(this.getSoundsJPanel(), objLjcenteredExtendedGridBagConstraints);

		// Pen color preferences :
		final JPanel objLpreferencesColorsJPanel = new JPanel(new GridBagLayout());
		objLpreferencesColorsJPanel.setOpaque(true);
		objLpreferencesColorsJPanel.add(this.getColorsJPanel(), objLjcenteredExtendedGridBagConstraints);

		// Tabbed panels :
		final JPanel objLmainJPanel = new JPanel(new GridBagLayout());
		objLmainJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											10,
																											5,
																											5,
																											10,
																											GridBagConstraints.BOTH,
																											1.0F,
																											0.25F);

		this.objGjTabbedPane.addTab(Strings.getEnlargedString(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_MESSAGES)),
									this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_ALERT, 2, "Messages"),
									objLpreferencesMessagesJPanel);
		this.objGjTabbedPane.addTab(Strings.getEnlargedString(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_VALUES)),
									this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_VALUES, 2, "Values"),
									objLpreferencesValuesJPanel);
		this.objGjTabbedPane.addTab(Strings.getEnlargedString(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_FILTERS)),
									this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FILTER, 2, "Filters"),
									objLpreferencesFilterJPanel);
		this.objGjTabbedPane.addTab(Strings.getEnlargedString(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_SOUNDS)),
									this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_SOUNDS, 2, "Sounds"),
									objLpreferencesSoundsJPanel);
		this.objGjTabbedPane.addTab(Strings.getEnlargedString(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_COLORS)),
									this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_COLORS, 2, "Colors"),
									objLpreferencesColorsJPanel);
		objLmainJPanel.add(this.objGjTabbedPane, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setMargins(10, 10, 10, 10);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLmainJPanel.add(this.getTasksButtonsJPanel(), objLextendedGridBagConstraints);

		// Main dialog dimension :
		this.add(objLmainJPanel);
		this.setBounds(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPforce
	 */
	final public void doApply() {

		// Save global boolean preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {

			if (this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				Preferences.writeGlobalBooleanPreference(	bytLpreferenceIndex,
															this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);

				this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																								this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT];

			}
		}

		// Save local boolean preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {

			if (this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				Preferences.writeLocalBooleanPreference(this.bytGbooleanLocalControlIndexA[bytLpreferenceIndex],
														this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
				this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																								this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT];
				if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
					this.objGcontrolJFrame.getPatternsManager().bolGbooleanIsUserDefinedA[this.bytGbooleanLocalControlIndexA[bytLpreferenceIndex]] =
																																						true;
				}
				this.setPreferenceControlLocalBoolean(	bytLpreferenceIndex,
														this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
														true);

			}
		}

		// Save global byte preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			if (this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				Preferences.writeGlobalBytePreference(bytLpreferenceIndex, this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
				this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																								this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT];
				if (bytLpreferenceIndex == Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION) {
					this.objGcontrolJFrame.objGballColorChooserDropDownJButton.setPopUp();
				}
			}
		}

		// Save local byte preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			if (this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				Preferences.writeLocalBytePreference(	this.bytGbyteLocalControlIndexA[bytLpreferenceIndex],
														this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
				this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																							this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT];
				boolean bolLextraEnabled = false;
				switch (bytLpreferenceIndex) {
					case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED:
						bolLextraEnabled = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SPEED);
						break;
					case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY:
						bolLextraEnabled = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLUIDITY);
						break;
				}
				if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION) || bolLextraEnabled) {
					this.objGcontrolJFrame.getPatternsManager().bolGbyteIsUserDefinedA[this.bytGbyteLocalControlIndexA[bytLpreferenceIndex]] = true;
				}
				this.setPreferenceControlLocalValue(bytLpreferenceIndex,
													this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
													true);
			}
		}

		// Save local string preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			if (!this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT].equalsIgnoreCase(this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL])) {
				Preferences.writeLocalStringPreference(	this.bytGstringLocalControlIndexA[bytLpreferenceIndex],
														this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT]);
				this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																								this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																												this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT];
				if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
					this.objGcontrolJFrame.getPatternsManager().bolGstringIsUserDefinedA[this.bytGstringLocalControlIndexA[bytLpreferenceIndex]] =
																																					true;
				}
				this.setPreferenceControlLocalString(	bytLpreferenceIndex,
														this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT],
														true);
			}
		}

		// Save global string preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			if (!this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT].equalsIgnoreCase(this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL])) {

				this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																								this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT];
				Preferences.writeGlobalStringPreference(bytLpreferenceIndex,
														this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
				int intLlanguageIndex =
										this.objGcontrolJFrame.getLanguageIndex(this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
				if (intLlanguageIndex == Constants.bytS_UNCLASS_NO_VALUE) {
					intLlanguageIndex = this.intGdefaultJuggleLanguageIndex;
				}
				if (intLlanguageIndex != this.objGcontrolJFrame.geLanguageIndex()) {
					this.objGcontrolJFrame.setLanguage(intLlanguageIndex, false);
				}
			}
		}

		this.objGcontrolJFrame.setWidgetsControls();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPglobalPreference
	 * @param bolPselected
	 */
	final public void doApplyBooleanGlobalCheckChange(byte bytPbooleanGlobalPreferenceType, boolean bolPselected) {

		this.bolGbooleanGlobalAA[bytPbooleanGlobalPreferenceType][Constants.bytS_UNCLASS_CURRENT] = bolPselected;
		Preferences.setGlobalBooleanPreference(bytPbooleanGlobalPreferenceType, bolPselected);
		switch (bytPbooleanGlobalPreferenceType) {
			case Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER:
				this.objGcontrolJFrame.objGballsNumberJCheckBox.select(bolPselected);
				CriteriaActions.doFilterBallsNumber(this.objGcontrolJFrame, Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER);
				this.setByteGlobalJLabels();
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_SKILL:
				this.objGcontrolJFrame.objGskillJCheckBox.select(bolPselected);
				CriteriaActions.doFilterGlobalSkill(this.objGcontrolJFrame, Constants.bytS_BYTE_GLOBAL_LOW_SKILL);
				this.setByteGlobalJLabels();
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_MARK:
				this.objGcontrolJFrame.objGmarkJCheckBox.select(bolPselected);
				CriteriaActions.doFilterGlobalMark(this.objGcontrolJFrame, Constants.bytS_BYTE_GLOBAL_LOW_MARK);
				this.setByteGlobalJLabels();
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS:
			case Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS:
			case Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS:
				this.objGcontrolJFrame.setToolTipsProperties();
				this.objGcontrolJFrame.setMenusJLabels();
				break;
			case Constants.bytS_BOOLEAN_GLOBAL_TOOLTIPS:
				this.objGcontrolJFrame.setToolTipsProperties();
				this.objGcontrolJFrame.setLanguageJLabels(false);
				this.objGcontrolJFrame.setMenusJLabels();
				break;
		}
		CriteriaActions.doCheckFilters(this.objGcontrolJFrame);
		this.setDialogJButtonsEnabled();
		Tools.debug("PreferencesJDialog.doApplyBooleanGlobalCheckChange()");
		Tools.out(this.getInfo());
	}

	final public void doApplyBooleanLocalCheckChange(byte bytPpreferenceType, boolean bolPselected) {

		this.bolGbooleanLocalAA[bytPpreferenceType][Constants.bytS_UNCLASS_CURRENT] = bolPselected;
		this.setPreferenceControlLocalBoolean(bytPpreferenceType, bolPselected, false);
		switch (bytPpreferenceType) {
			case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_INFOS:
			case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_ERRORS:
			case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_WARNINGS:
				final boolean bolLvisible =
											bolPselected
												&& this.objGcontrolJFrame	.getPatternsManager()
																			.getBooleanValue(this.bytGbooleanLocalControlIndexA[bytPpreferenceType]);
				this.objGcontrolJFrame.objGconsoleJDialog.setVisible(bolLvisible);
				if (this.objGcontrolJFrame.objGconsoleJCheckBoxMenuItem.isEnabled()) {
					this.objGcontrolJFrame.objGconsoleJCheckBoxMenuItem.selectState(bolLvisible);
				}
				break;
			case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS:
				this.setSoundsJLabelsEnabled();
				this.objGcontrolJFrame.objGsoundsJCheckBox.select(bolPselected);
				break;
			case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_CATCH_SOUNDS:
				this.objGcontrolJFrame.objGcatchSoundsJCheckBox.select(bolPselected);
				break;
			case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_THROW_SOUNDS:
				this.objGcontrolJFrame.objGthrowSoundsJCheckBox.select(bolPselected);
				break;
			case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME:
				this.objGcontrolJFrame.objGmetronomeJCheckBox.select(bolPselected);
				break;
		}

		this.setDialogJButtonsEnabled();
		Tools.debug("PreferencesJDialog.doApplyBooleanLocalCheckChange()");
		Tools.out(this.getInfo());
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPfilterType
	 * @param intPselectedIndex
	 */
	final public void doApplyByteGlobalComboChange(byte bytPbyteGlobalFilterType, int intPselectedIndex) {

		// Memorize change :
		// //////////////////
		switch (bytPbyteGlobalFilterType) {
			case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
				if (this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] != intPselectedIndex
																										+ Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE) {

					this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] =
																										(byte) (intPselectedIndex + Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
					if (this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT] < this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT]) {
						this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT] =
																																this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT];
						this.objGcontrolJFrame.objGhighBallsNumberJComboBox.selectIndex(intPselectedIndex);
					}
					this.objGcontrolJFrame.objGlowBallsNumberJComboBox.selectIndex(intPselectedIndex);
				} else {
					return;
				}
				break;

			case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
				if (this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] != intPselectedIndex
																										+ Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE) {
					this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] =
																										(byte) (intPselectedIndex + Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
					if (this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT] > this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT]) {
						this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT] =
																																this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT];
						this.objGcontrolJFrame.objGlowBallsNumberJComboBox.selectIndex(intPselectedIndex);
					}
					this.objGcontrolJFrame.objGhighBallsNumberJComboBox.selectIndex(intPselectedIndex);
				} else {
					return;
				}
				break;

			case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
				if (this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] != intPselectedIndex) {
					this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] = (byte) (intPselectedIndex);
					if (this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT] < this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT]) {
						this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT] =
																														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT];
						this.objGcontrolJFrame.objGhighSkillJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT]);
					}
					this.objGcontrolJFrame.objGlowSkillJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT]);
				} else {
					return;
				}
				break;

			case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
				if (this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] != intPselectedIndex) {

					this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] = (byte) (intPselectedIndex);
					if (this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT] < this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT]) {
						this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT] =
																														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT];
						this.objGcontrolJFrame.objGlowSkillJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT]);
					}
					this.objGcontrolJFrame.objGhighSkillJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT]);
				} else {
					return;
				}
				break;

			case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
				if (this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] != intPselectedIndex) {
					this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] = (byte) (intPselectedIndex);
					if (this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT] < this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT]) {
						this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT] =
																														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT];
						this.objGcontrolJFrame.objGhighMarkJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT]);
					}
					this.objGcontrolJFrame.objGlowMarkJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT]);
				} else {
					return;
				}
				break;

			case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
				if (this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] != intPselectedIndex) {

					this.bytGbyteGlobalAA[bytPbyteGlobalFilterType][Constants.bytS_UNCLASS_CURRENT] = (byte) (intPselectedIndex);
					if (this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT] < this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT]) {
						this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT] =
																														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT];
						this.objGcontrolJFrame.objGlowMarkJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT]);
					}
					this.objGcontrolJFrame.objGhighMarkJComboBox.selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT]);
				} else {
					return;
				}
				break;
		}

		// Do change actions :
		// ////////////////////
		switch (bytPbyteGlobalFilterType) {
			case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
			case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
				Preferences.writeGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER,
														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT]);
				Preferences.writeGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER,
														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT]);
				CriteriaActions.doFilterBallsNumber(this.objGcontrolJFrame, bytPbyteGlobalFilterType);
				break;

			case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
			case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
				Preferences.writeGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_LOW_SKILL,
														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT]);
				Preferences.writeGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_HIGH_SKILL,
														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT]);
				CriteriaActions.doFilterGlobalSkill(this.objGcontrolJFrame, bytPbyteGlobalFilterType);
				break;

			case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
			case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
				Preferences.writeGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_LOW_MARK,
														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT]);
				Preferences.writeGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_HIGH_MARK,
														this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT]);
				CriteriaActions.doFilterGlobalMark(this.objGcontrolJFrame, bytPbyteGlobalFilterType);
				break;
		}
		this.setByteGlobalJLabels();
		this.objGcontrolJFrame.objGobjectsJList.setLists();
		this.setDialogJButtonsEnabled();
		Tools.out(this.getInfo());
	}

	public void doApplyByteGlobalScrollChange(byte bytPpreferenceType, byte bytPvalue) {
		this.bytGbyteGlobalAA[bytPpreferenceType][Constants.bytS_UNCLASS_CURRENT] = bytPvalue;
		this.setPreferenceControlGlobalValue(bytPpreferenceType, bytPvalue);
		this.setByteGlobalJLabels();
		this.setDialogJButtonsEnabled();
		Tools.debug("PreferencesJDialog.doApplyByteGlobalScrollChange()");
		Tools.out(this.getInfo());
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPvalue
	 */
	final public void doApplyByteLocalScrollChange(byte bytPpreferenceType, byte bytPvalue) {
		this.bytGbyteLocalAA[bytPpreferenceType][Constants.bytS_UNCLASS_CURRENT] = bytPvalue;
		this.setPreferenceControlLocalValue(bytPpreferenceType, bytPvalue, false);
		this.setByteLocalJLabels();
		this.setDialogJButtonsEnabled();
		Tools.debug("PreferencesJDialog.doApplyByteLocalScrollChange()");
		Tools.out(this.getInfo());
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPfilterType
	 * @param intPselectedIndex
	 */
	final public void doApplyStringGlobalComboChange(byte bytPfilterType, Object objPselectedObject) {

		switch (bytPfilterType) {
			case Constants.bytS_STRING_GLOBAL_LANGUAGE:
				String strLselected = Strings.strS_EMPTY;
				if (objPselectedObject instanceof Language) {
					final Language objLlanguage = (Language) objPselectedObject;
					strLselected = objLlanguage.getPropertyValueString(Language.intS_LANGUAGE_ISO_639_1_CODE);
				}
				this.strGstringGlobalAA[bytPfilterType][Constants.bytS_UNCLASS_CURRENT] = strLselected;
				this.setLanguage();
				break;
		}
		this.setDialogJButtonsEnabled();
		Tools.debug("PreferencesJDialog.doApplyStringGlobalComboChange()");
		Tools.out(this.getInfo());
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPforce
	 */
	final public void doCancel() {

		boolean bolLrefreshFilters = false;

		// Restore previous global boolean preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			if (this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																								this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL];
				switch (bytLpreferenceIndex) {
					case Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER:
						this.objGcontrolJFrame.objGballsNumberJCheckBox.select(this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
						bolLrefreshFilters = true;
						break;
					case Constants.bytS_BOOLEAN_GLOBAL_SKILL:
						this.objGcontrolJFrame.objGskillJCheckBox.select(this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
						bolLrefreshFilters = true;
						break;
					case Constants.bytS_BOOLEAN_GLOBAL_MARK:
						this.objGcontrolJFrame.objGmarkJCheckBox.select(this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
						bolLrefreshFilters = true;
						break;
					case Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS:
						this.objGcontrolJFrame.objGinvalidPatternsJCheckBox.select(this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
						this.objGcontrolJFrame.objGinvalidPatternsJCheckBox.setToolTipText();
						this.objGcontrolJFrame.objGinvalidPatternsJLabel.setToolTipText();
						bolLrefreshFilters = true;
						break;
				}
			}
		}

		// Restore previous local boolean preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			if (this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																								this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL];
				if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
					this.setPreferenceControlLocalBoolean(	bytLpreferenceIndex,
															this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE],
															false);
				}
			}
		}

		// Restore previous global byte preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			if (this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																								this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL];
			}
			switch (bytLpreferenceIndex) {
				case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
					this.objGcontrolJFrame.objGhighBallsNumberJComboBox.selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
																					- Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
					bolLrefreshFilters = true;
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
					this.objGcontrolJFrame.objGlowBallsNumberJComboBox.selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
																					- Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
					bolLrefreshFilters = true;
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
					this.objGcontrolJFrame.objGlowSkillJComboBox.selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
					bolLrefreshFilters = true;
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
					this.objGcontrolJFrame.objGhighSkillJComboBox.selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
					bolLrefreshFilters = true;
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
					this.objGcontrolJFrame.objGlowMarkJComboBox.selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
					bolLrefreshFilters = true;
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
					this.objGcontrolJFrame.objGhighMarkJComboBox.selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
					bolLrefreshFilters = true;
					break;
				case Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION:
					Preferences.writeGlobalBytePreference(	bytLpreferenceIndex,
															this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]);
					this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_BALLS_IMAGES);
					this.objGcontrolJFrame.objGballColorChooserDropDownJButton.setPopUp();
					break;
			}
		}

		// Restore previous local byte preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			if (this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] != this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL]) {
				this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																							this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL];
				this.setPreferenceControlLocalValue(bytLpreferenceIndex,
													this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE],
													false);
			}
		}

		// Restore previous global string preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			if (!Strings.areNullEqual(	this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
										this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL])) {

				this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																								this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL];
				if (bytLpreferenceIndex == Constants.bytS_STRING_GLOBAL_LANGUAGE) {
					this.setLanguage();
				}
			}
		}

		// Restore previous local string preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			if (!this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT].equalsIgnoreCase(this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL])) {
				this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																										this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																														this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL];
				this.setPreferenceControlLocalString(	bytLpreferenceIndex,
														this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE],
														false);
				if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
					final boolean bolLrefresh =
												((bytLpreferenceIndex == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY
													|| bytLpreferenceIndex == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY || bytLpreferenceIndex == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY) && this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT))
													|| ((bytLpreferenceIndex == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT
															|| bytLpreferenceIndex == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT || bytLpreferenceIndex == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT) && !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT));
					if (bolLrefresh) {
						switch (bytLpreferenceIndex) {
							case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY:
							case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT:
								this.objGcontrolJFrame.getJuggleMasterPro().initSiteswapColors();
								this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_DRAW_SITESWAP);
								break;
							case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY:
							case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT:
								this.objGcontrolJFrame.getJuggleMasterPro().setBackgroundColors();
								this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
																	| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
																	| Constants.intS_ACTION_RECREATE_HANDS_IMAGES
																	| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES
																	| Constants.intS_ACTION_DRAW_SITESWAP | Constants.intS_ACTION_REFRESH_DRAWING
																	| Constants.intS_ACTION_RECREATE_BALLS_ERASING_IMAGES
																	| Constants.intS_ACTION_RECREATE_BACKGROUND_IMAGES);
								break;
							case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY:
							case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT:
								if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER)
									&& !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)) {
									if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)) {
										this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
																			| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
									}
									this.objGcontrolJFrame.getJuggleMasterPro().initJugglerColors();
									this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_HANDS_IMAGES
																		| Constants.intS_ACTION_REFRESH_DRAWING);
								}
								break;
						}
					}
				}
			}
		}
		if (bolLrefreshFilters) {
			CriteriaActions.doCheckFilters(this.objGcontrolJFrame);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateWidgets() {

		// Tabbed pane :
		this.objGjTabbedPane = new JTabbedPane();

		// Global boolean check boxes :
		this.objGbooleanGlobalJCheckBoxA = new PreferenceBooleanJCheckBox[Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER];
		this.objGbooleanGlobalJLabelA = new PreferenceBooleanJLabel[Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.objGbooleanGlobalJCheckBoxA[bytLpreferenceIndex] =
																	new PreferenceBooleanJCheckBox(	this.objGcontrolJFrame,
																									this,
																									bytLpreferenceIndex,
																									true);
			this.objGbooleanGlobalJLabelA[bytLpreferenceIndex] =
																	new PreferenceBooleanJLabel(this.objGcontrolJFrame,
																								this,
																								bytLpreferenceIndex,
																								true,
																								this.intGbooleanGlobalLanguageIndexA[bytLpreferenceIndex]);
		}

		this.objGskillDotsJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":");
		this.objGmarkDotsJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":");

		this.objGballsNumberDotsJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":");

		// Local boolean check boxes :
		this.objGbooleanLocalJCheckBoxA = new PreferenceBooleanJCheckBox[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER];
		this.objGbooleanLocalJLabelA = new PreferenceBooleanJLabel[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGbooleanLocalJCheckBoxA[bytLpreferenceIndex] =
																	new PreferenceBooleanJCheckBox(	this.objGcontrolJFrame,
																									this,
																									bytLpreferenceIndex,
																									false);
			this.objGbooleanLocalJLabelA[bytLpreferenceIndex] =
																new PreferenceBooleanJLabel(this.objGcontrolJFrame,
																							this,
																							bytLpreferenceIndex,
																							false,
																							this.intGbooleanLocalLanguageIndexA[bytLpreferenceIndex]);
		}

		// Global byte combo boxes & scrollbar :
		this.objGbyteGlobalJLabelA = new PreferenceByteGlobalJLabel[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		this.objGbyteGlobalValueJLabelA = new ExtendedJLabel[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		this.objGbyteGlobalJComboBoxA = new PreferenceByteGlobalJComboBox[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		this.objGbyteGlobalJScrollBarA = new PreferenceByteGlobalJScrollBar[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {

			this.objGbyteGlobalJLabelA[bytLpreferenceIndex] = new PreferenceByteGlobalJLabel(this.objGcontrolJFrame, this, bytLpreferenceIndex);
			byte bytLbooleanGlobalFilter = Constants.bytS_UNCLASS_NO_VALUE;
			switch (bytLpreferenceIndex) {
				case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
				case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
					bytLbooleanGlobalFilter = Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER;
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
				case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
					bytLbooleanGlobalFilter = Constants.bytS_BOOLEAN_GLOBAL_SKILL;
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
				case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
					bytLbooleanGlobalFilter = Constants.bytS_BOOLEAN_GLOBAL_MARK;
					break;
			}
			this.objGbyteGlobalJLabelA[bytLpreferenceIndex].setEnabled(bytLpreferenceIndex == Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION
																		^ (bytLbooleanGlobalFilter != Constants.bytS_UNCLASS_NO_VALUE && this.bolGbooleanGlobalAA[bytLbooleanGlobalFilter][Constants.bytS_UNCLASS_CURRENT]));

			if (bytLpreferenceIndex == Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION) {
				this.objGbyteGlobalJScrollBarA[bytLpreferenceIndex] = new PreferenceByteGlobalJScrollBar(this, bytLpreferenceIndex);
				this.objGbyteGlobalValueJLabelA[bytLpreferenceIndex] =
																		new ExtendedJLabel(	this.objGcontrolJFrame,
																							this.objGcontrolJFrame.getLanguageString(this.intGbyteGlobalLanguageIndexA[bytLpreferenceIndex]));
				// TODO : activer la correction Gamma :
				this.objGbyteGlobalJScrollBarA[bytLpreferenceIndex].setEnabled(false);
				this.objGbyteGlobalJLabelA[bytLpreferenceIndex].setEnabled(false);
				this.objGbyteGlobalValueJLabelA[bytLpreferenceIndex].setEnabled(false);
			} else {
				this.objGbyteGlobalJComboBoxA[bytLpreferenceIndex] =
																		new PreferenceByteGlobalJComboBox(	this.objGcontrolJFrame,
																											this,
																											bytLpreferenceIndex);
			}
		}
		this.objGballsNumberJLabel =
										new ExtendedJLabel(	this.objGcontrolJFrame,
															new PreferenceBallsJLabelMouseAdapter(this),
															this.getLanguageString(Language.intS_LABEL_BALLS));
		this.objGmarkJButton =
								new MarkJButton(this.objGcontrolJFrame,
												true,
												true,
												Language.intS_TOOLTIP_ACTIVATE_MARK_FILTER,
												Language.intS_TOOLTIP_DEACTIVATE_MARK_FILTER);

		// Local byte scrollbars & labels :
		this.objGbyteLocalJScrollBarA = new PreferenceByteLocalJScrollBar[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER];
		this.objGbyteLocalJLabelA = new ExtendedJLabel[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER];
		this.objGbyteLocalValueJLabelA = new ExtendedJLabel[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGbyteLocalJScrollBarA[bytLpreferenceIndex] = new PreferenceByteLocalJScrollBar(this, bytLpreferenceIndex);
			this.objGbyteLocalJScrollBarA[bytLpreferenceIndex].setOpaque(true);
			this.objGbyteLocalJScrollBarA[bytLpreferenceIndex].setFont(this.objGcontrolJFrame.getFont());
			this.objGbyteLocalJLabelA[bytLpreferenceIndex] =
																new ExtendedJLabel(	this.objGcontrolJFrame,
																					Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(this.intGbyteLocalLanguageIndexA[bytLpreferenceIndex]),
																										" :"));
			this.objGbyteLocalValueJLabelA[bytLpreferenceIndex] = new ExtendedJLabel(this.objGcontrolJFrame);
		}
		this.setByteLocalJLabels();

		// Global string labels :
		this.objGstringGlobalJLabelA = new ExtendedJLabel[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER];
		this.objGstringGlobalJLabelA[Constants.bytS_STRING_GLOBAL_LANGUAGE] =
																				new ExtendedJLabel(	this.objGcontrolJFrame,
																									Strings.doConcat(	this.getLanguageString(this.intGstringGlobalLanguageIndexA[Constants.bytS_STRING_GLOBAL_LANGUAGE]),
																														" : "));
		this.objGstringGlobalJLabelA[Constants.bytS_STRING_GLOBAL_LANGUAGE].setHorizontalAlignment(SwingConstants.RIGHT);

		// Global string combo boxes :
		this.objGstringGlobalJComboBoxA = new PreferenceStringGlobalJComboBox[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.objGstringGlobalJComboBoxA[bytLpreferenceIndex] =
																	new PreferenceStringGlobalJComboBox(this.objGcontrolJFrame,
																										this,
																										bytLpreferenceIndex);
		}

		// Color buttons :
		this.objGstringLocalColorJButtonA = new PreferenceStringLocalColorJButton[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER];
		this.objGstringLocalJLabelA = new ExtendedJLabel[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGstringLocalColorJButtonA[bytLpreferenceIndex] =
																		new PreferenceStringLocalColorJButton(	this.objGcontrolJFrame,
																												this,
																												bytLpreferenceIndex);
			this.objGstringLocalJLabelA[bytLpreferenceIndex] =
																new ExtendedJLabel(	this.objGcontrolJFrame,
																					Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(this.intGstringLocalLanguageIndexA[bytLpreferenceIndex]),
																										" :"));
			switch (bytLpreferenceIndex) {
				case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY:
				case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY:
				case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY:
					this.objGstringLocalJLabelA[bytLpreferenceIndex].setEnabled(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																				&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT));
					break;
				case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT:
				case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT:
				case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT:
					this.objGstringLocalJLabelA[bytLpreferenceIndex].setEnabled(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																				&& !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT));
					break;
			}
		}

		// Buttons :
		this.objGoKJButton = new PreferencesDialogJButton(this.objGcontrolJFrame, this, Language.intS_BUTTON_OK, true, true);
		this.objGcancelJButton = new PreferencesDialogJButton(this.objGcontrolJFrame, this, Language.intS_BUTTON_CANCEL, false, true);
		this.objGapplyJButton = new PreferencesDialogJButton(this.objGcontrolJFrame, this, Language.intS_BUTTON_APPLY, true, false);
		this.objGrestoreJButton = new PreferencesDialogJButton(this.objGcontrolJFrame, this, Language.intS_BUTTON_RESTORE, false, false);
		this.objGresetJButton = new PreferencesDialogJButton(this.objGcontrolJFrame, this, Language.intS_BUTTON_DEFAULT);
		this.setDialogJButtonsEnabled();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doReset() {

		final ChoiceJDialog objLchoiceJDialog =
												new ChoiceJDialog(	this.objGcontrolJFrame,
																	this,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_RESET_PREFERENCES),
																	Constants.intS_FILE_ICON_ALERT,

																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_YES),
																	Language.intS_TOOLTIP_RESET_PREFERENCES,
																	Constants.intS_FILE_ICON_OK_BW,
																	Constants.intS_FILE_ICON_OK,

																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_NO),
																	Language.intS_TOOLTIP_DO_NOT_RESET_PREFERENCES,
																	Constants.intS_FILE_ICON_CANCEL_BW,
																	Constants.intS_FILE_ICON_CANCEL,

																	ChoiceJDialog.bytS_SECOND_CHOICE,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_QUESTION_RESET_PREFERENCES));
		objLchoiceJDialog.setVisible(true);
		final boolean bolLconfirm = (objLchoiceJDialog.bytGchoice == ChoiceJDialog.bytS_FIRST_CHOICE);
		objLchoiceJDialog.dispose();
		if (bolLconfirm) {

			// Reset hidden preferences :
			Preferences.resetHiddenPreferences();

			// Reset global boolean preferences :
			for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
				this.doApplyBooleanGlobalCheckChange(bytLpreferenceIndex, bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER
																			&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_MARK
																			&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_SKILL);
			}

			// Reset local boolean preferences :
			for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
				switch (bytLpreferenceIndex) {
					case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_INFOS:
					case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_WARNINGS:
					case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_ERRORS:
					case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS:
					case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_CATCH_SOUNDS:
						this.doApplyBooleanLocalCheckChange(bytLpreferenceIndex, true);
						break;
					case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_THROW_SOUNDS:
					case PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME:
						this.doApplyBooleanLocalCheckChange(bytLpreferenceIndex, false);
						break;
				}
			}

			// Reset global byte preferences :
			this.doApplyByteGlobalComboChange(Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER, Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE - 1);
			this.doApplyByteGlobalComboChange(Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER, Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MAXIMUM_VALUE - 1);
			this.doApplyByteGlobalComboChange(Constants.bytS_BYTE_GLOBAL_LOW_SKILL, Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER);
			this.doApplyByteGlobalComboChange(Constants.bytS_BYTE_GLOBAL_HIGH_SKILL, Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE);
			this.doApplyByteGlobalComboChange(Constants.bytS_BYTE_GLOBAL_LOW_MARK, Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE);
			this.doApplyByteGlobalComboChange(Constants.bytS_BYTE_GLOBAL_HIGH_MARK, Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE);
			this.doApplyByteGlobalScrollChange(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION, Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE);
			this.objGcontrolJFrame.objGballColorChooserDropDownJButton.setPopUp();

			// Reset local byte preferences :
			this.doApplyByteLocalScrollChange(	PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY,
												Constants.bytS_BYTE_LOCAL_FLUIDITY_DEFAULT_VALUE);
			this.doApplyByteLocalScrollChange(PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED, Constants.bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE);
			this.doApplyByteLocalScrollChange(PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS, Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE);

			// Reset local string preferences :
			this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																				this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY][Constants.bytS_UNCLASS_CURRENT] =
																																																																		Constants.strS_STRING_LOCAL_SITESWAP_DAY_DEFAULT;
			this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																				this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT][Constants.bytS_UNCLASS_CURRENT] =
																																																																			Constants.strS_STRING_LOCAL_SITESWAP_NIGHT_DEFAULT;

			this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																				this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY][Constants.bytS_UNCLASS_CURRENT] =
																																																																			Constants.strS_STRING_LOCAL_BACKGROUND_DAY_DEFAULT;
			this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																					this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT][Constants.bytS_UNCLASS_CURRENT] =
																																																																				Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT;
			this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																			this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY][Constants.bytS_UNCLASS_CURRENT] =
																																																																	Constants.strS_STRING_LOCAL_JUGGLER_DAY_DEFAULT;
			this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																				this.strGstringLocalAA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT][Constants.bytS_UNCLASS_CURRENT] =
																																																																		Constants.strS_STRING_LOCAL_JUGGLER_NIGHT_DEFAULT;
			this.setJButtonsColors();

			// Reset global string preferences :
			// this.doApplyStringGlobalCheckChange(Constants.bytS_STRING_GLOBAL_LANGUAGE, false);
			this.doApplyStringGlobalComboChange(Constants.bytS_STRING_GLOBAL_LANGUAGE, Strings.strS_EMPTY);
		}
	}

	final private JPanel getColorsJPanel() {
		final JPanel objLpreferencesColorsJPanel = new JPanel(new GridBagLayout());
		objLpreferencesColorsJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLcolorsJLabelsExtendedGridBagConstraints =
																						new ExtendedGridBagConstraints(	0,
																														GridBagConstraints.RELATIVE,
																														1,
																														1,
																														GridBagConstraints.EAST,
																														10,
																														10,
																														10,
																														5);
		final ExtendedGridBagConstraints objLcolorsJButtonsExtendedGridBagConstraints =
																						new ExtendedGridBagConstraints(	1,
																														GridBagConstraints.RELATIVE,
																														1,
																														1,
																														GridBagConstraints.WEST,
																														Constants.objS_GRAPHICS_FONT_METRICS.getAscent(),
																														Constants.objS_GRAPHICS_FONT_METRICS.getAscent(),
																														10,
																														10,
																														0,
																														10);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			if (bytLpreferenceIndex == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER / 2) {
				objLcolorsJLabelsExtendedGridBagConstraints.setGridBounds(2, GridBagConstraints.RELATIVE, 1, 1);
				objLcolorsJButtonsExtendedGridBagConstraints.setGridBounds(3, GridBagConstraints.RELATIVE, 1, 1);
			}
			objLpreferencesColorsJPanel.add(this.objGstringLocalJLabelA[bytLpreferenceIndex], objLcolorsJLabelsExtendedGridBagConstraints);
			objLpreferencesColorsJPanel.add(this.objGstringLocalColorJButtonA[bytLpreferenceIndex], objLcolorsJButtonsExtendedGridBagConstraints);
		}

		return objLpreferencesColorsJPanel;
	}

	final private JPanel getFiltersJPanel() {
		final JPanel objLpreferencesFilterJPanel = new JPanel(new GridBagLayout());
		objLpreferencesFilterJPanel.setOpaque(true);

		// Gamma correction :
		// ///////////////////
		final ExtendedGridBagConstraints objLfilterExtendedGridBagConstraints =
																				new ExtendedGridBagConstraints(	1,
																												0,
																												1,
																												1,
																												GridBagConstraints.EAST,
																												0,
																												0,
																												0,
																												10,
																												5,
																												0);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION], objLfilterExtendedGridBagConstraints); // Gamma Correction

		objLfilterExtendedGridBagConstraints.setGridLocation(2, 0);
		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		final ExtendedJLabel objLgammaDotsJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":");
		objLpreferencesFilterJPanel.add(objLgammaDotsJLabel, objLfilterExtendedGridBagConstraints); // :

		objLfilterExtendedGridBagConstraints.setGridBounds(3, 0, 4, 1);
		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1.0F, 0.0F);
		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 0);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJScrollBarA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION],
										objLfilterExtendedGridBagConstraints); // Gamma correction scrollbar

		objLfilterExtendedGridBagConstraints.setGridBounds(7, 0, 1, 1);
		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 5, 0);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 5);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalValueJLabelA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION],
										objLfilterExtendedGridBagConstraints); // Gamma correction value

		// Ball number :
		// //////////////
		objLfilterExtendedGridBagConstraints.setGridBounds(GridBagConstraints.RELATIVE, 1, 1, 1);
		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 0);
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER],
										objLfilterExtendedGridBagConstraints); // Balls Number checkbox
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJLabelA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER],
										objLfilterExtendedGridBagConstraints); // Balls Number label

		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLpreferencesFilterJPanel.add(this.objGballsNumberDotsJLabel, objLfilterExtendedGridBagConstraints); // :

		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER], objLfilterExtendedGridBagConstraints); // From

		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER],
										objLfilterExtendedGridBagConstraints); // Low ball number combobox

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER],
										objLfilterExtendedGridBagConstraints); // To

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER],
										objLfilterExtendedGridBagConstraints); // High ball number combobox

		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 5);
		objLpreferencesFilterJPanel.add(this.objGballsNumberJLabel, objLfilterExtendedGridBagConstraints); // Ball number label

		// Skill :
		// ////////
		objLfilterExtendedGridBagConstraints.setGridLocation(GridBagConstraints.RELATIVE, 2);
		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 0);
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_SKILL], objLfilterExtendedGridBagConstraints); // Skill
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJLabelA[Constants.bytS_BOOLEAN_GLOBAL_SKILL], objLfilterExtendedGridBagConstraints); // Skill

		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLpreferencesFilterJPanel.add(this.objGskillDotsJLabel, objLfilterExtendedGridBagConstraints); // :

		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL], objLfilterExtendedGridBagConstraints); // From

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL], objLfilterExtendedGridBagConstraints); // Low skill combobox

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL], objLfilterExtendedGridBagConstraints); // To

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL], objLfilterExtendedGridBagConstraints); // High skill combobox

		// Mark :
		// ///////
		objLfilterExtendedGridBagConstraints.setGridLocation(GridBagConstraints.RELATIVE, 3);
		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 0);
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_MARK], objLfilterExtendedGridBagConstraints); // Mark
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJLabelA[Constants.bytS_BOOLEAN_GLOBAL_MARK], objLfilterExtendedGridBagConstraints); // Mark

		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLpreferencesFilterJPanel.add(this.objGmarkDotsJLabel, objLfilterExtendedGridBagConstraints); // :

		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_LOW_MARK], objLfilterExtendedGridBagConstraints); // From

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_LOW_MARK], objLfilterExtendedGridBagConstraints); // Low mark combobox

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK], objLfilterExtendedGridBagConstraints); // To

		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.0F);
		objLpreferencesFilterJPanel.add(this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK], objLfilterExtendedGridBagConstraints); // High mark combobox

		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 5);
		objLpreferencesFilterJPanel.add(this.objGmarkJButton, objLfilterExtendedGridBagConstraints); // Mark star

		// Invalid patterns :
		// ///////////////////
		objLfilterExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLfilterExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 0);
		objLfilterExtendedGridBagConstraints.setGridBounds(0, 4, 1, 1);
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS],
										objLfilterExtendedGridBagConstraints); // Invalid patterns

		objLfilterExtendedGridBagConstraints.setGridBounds(1, 4, 7, 1);
		objLfilterExtendedGridBagConstraints.setMargins(0, 10, 5, 5);
		objLpreferencesFilterJPanel.add(this.objGbooleanGlobalJLabelA[Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS],
										objLfilterExtendedGridBagConstraints); // Invalid pattern label

		return objLpreferencesFilterJPanel;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getInfo() {

		// Global boolean preferences :
		final StringBuilder objLbooleanGlobalPreferencesStringBuilder =
																		new StringBuilder((Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 32)
																							* Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			objLbooleanGlobalPreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BOOLEAN_GLOBAL,
																				Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[bytLpreferenceIndex],
																				" = (     , ",
																				Strings.getFixedLengthString(	this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																												5),
																				", ",
																				Strings.getFixedLengthString(	this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
																												5),
																				')',
																				this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
																																																												? Strings.strS_RIGHT_ASTERIX
																																																												: null,
																				Strings.strS_LINE_SEPARATOR));
		}

		// Local boolean preferences :
		final StringBuilder objLbooleanLocalPreferencesStringBuilder =
																		new StringBuilder((Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 32)
																							* PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			objLbooleanLocalPreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BOOLEAN_LOCAL,
																				Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[this.bytGbooleanLocalControlIndexA[bytLpreferenceIndex]][0],
																				" = (",
																				Strings.getFixedLengthString(	this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE],
																												5),
																				", ",
																				Strings.getFixedLengthString(	this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																												5),
																				", ",
																				Strings.getFixedLengthString(	this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
																												5),
																				')',
																				this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
																																																											? Strings.strS_RIGHT_ASTERIX
																																																											: null,
																				Strings.strS_LINE_SEPARATOR));
		}

		// Global byte preferences :
		final StringBuilder objLbytGlobalPreferencesStringBuilder =
																	new StringBuilder((Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 32)
																						* Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			objLbytGlobalPreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BYTE_GLOBAL,
																			Constants.strS_BYTE_GLOBAL_CONTROL_A[bytLpreferenceIndex],
																			" = (  , ",
																			Strings.getValueString(	this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																									2,
																									0,
																									2,
																									true),
																			", ",
																			Strings.getValueString(	this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
																									2,
																									0,
																									2,
																									true),
																			')',
																			this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
																																																									? Strings.strS_RIGHT_ASTERIX
																																																									: null,
																			Strings.strS_LINE_SEPARATOR));
		}

		// Local Byte preferences :
		final StringBuilder objLbyteLocalPreferencesStringBuilder =
																	new StringBuilder((Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 32)
																						* PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			objLbyteLocalPreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BYTE_LOCAL,
																			Constants.strS_BYTE_LOCAL_CONTROL_A_A[this.bytGbyteLocalControlIndexA[bytLpreferenceIndex]][0],
																			" = (",
																			Strings.getValueString(	this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE],
																									2,
																									0,
																									2,
																									true),
																			", ",
																			Strings.getValueString(	this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																									2,
																									0,
																									2,
																									true),
																			", ",
																			Strings.getValueString(	this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
																									2,
																									0,
																									2,
																									true),
																			')',
																			this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
																																																									? Strings.strS_RIGHT_ASTERIX
																																																									: null,
																			Strings.strS_LINE_SEPARATOR));
		}

		// Global string preferences :
		final StringBuilder objLstringGlobalPreferencesStringBuilder =
																		new StringBuilder((Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 32)
																							* Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			final String strLmodifier =
										Strings.areNullEqual(	this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT])
																																				? null
																																				: Strings.strS_RIGHT_ASTERIX;

			objLstringGlobalPreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_STRING_GLOBAL,
																				Constants.strS_STRING_GLOBAL_CONTROL_A[bytLpreferenceIndex],
																				" = (  , \"",
																				this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																				"\", \"",
																				this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
																				"\")",
																				strLmodifier,
																				Strings.strS_LINE_SEPARATOR));
		}

		// Local string preferences :
		final StringBuilder objLstringLocalPreferencesStringBuilder =
																		new StringBuilder((Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 40)
																							* PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			objLstringLocalPreferencesStringBuilder.append(Strings.doConcat(Constants.strS_STRING_LOCAL,
																			Constants.strS_STRING_LOCAL_CONTROL_A[this.bytGstringLocalControlIndexA[bytLpreferenceIndex]],
																			" = (\"",
																			this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE],
																			"\", \"",
																			this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																			"\", \"",
																			this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT],
																			"\")",
																			Strings.areNullEqual(	this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL],
																									this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT])
																																												? null
																																												: Strings.strS_RIGHT_ASTERIX,
																			Strings.strS_LINE_SEPARATOR));
		}

		return Strings.doConcat(Strings.getFixedLengthString("Dialog Preferences", Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 1),
								" = (saved, init , curr ) :",
								Strings.strS_LINE_SEPARATOR,
								Strings.getCharsString('¯', Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 27),
								Strings.strS_LINE_SEPARATOR,
								Strings.strS_LINE_SEPARATOR,
								objLbooleanGlobalPreferencesStringBuilder,
								objLbooleanLocalPreferencesStringBuilder,
								objLbytGlobalPreferencesStringBuilder,
								objLbyteLocalPreferencesStringBuilder,
								objLstringGlobalPreferencesStringBuilder,
								objLstringLocalPreferencesStringBuilder,
								Strings.strS_LINE_SEPARATOR);
	}

	final public String getLanguageString(int intPpropertyIndex) {
		final int intLlanguageIndex =
										this.objGcontrolJFrame.getLanguageIndex(this.strGstringGlobalAA[Constants.bytS_STRING_GLOBAL_LANGUAGE][Constants.bytS_UNCLASS_CURRENT]);
		return this.objGcontrolJFrame.objGlanguageA[intLlanguageIndex >= 0 ? intLlanguageIndex : this.intGdefaultJuggleLanguageIndex].getPropertyValueString(intPpropertyIndex);
	}

	final private JPanel getMessagesJPanel() {

		final JPanel objLmessagesJPanel = new JPanel(new GridBagLayout());
		objLmessagesJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																											0,
																											1,
																											1,
																											GridBagConstraints.EAST,
																											0,
																											0,
																											10,
																											5,
																											5,
																											5);

		// Language (just) :
		objLmessagesJPanel.add(this.objGstringGlobalJLabelA[Constants.bytS_STRING_GLOBAL_LANGUAGE], objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setInsideLocation(GridBagConstraints.WEST);
		objLextendedGridBagConstraints.setGridWidth(2);
		objLextendedGridBagConstraints.setWeightX(1.0F);
		objLmessagesJPanel.add(this.objGstringGlobalJComboBoxA[Constants.bytS_STRING_GLOBAL_LANGUAGE], objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridWidth(1);

		// Tooltips (first row) :
		objLextendedGridBagConstraints.setGridLocation(0, objLextendedGridBagConstraints.getGridY() + 1);
		objLextendedGridBagConstraints.setInsideLocation(GridBagConstraints.EAST);
		objLextendedGridBagConstraints.setFillingMode(GridBagConstraints.NONE);
		Tools.out(objLextendedGridBagConstraints);
		objLmessagesJPanel.add(new ExtendedJLabel(this.objGcontrolJFrame, "Tooltips :"), objLextendedGridBagConstraints);

		final ExtendedJPanel objLcheckboxAndLabelTooltipJPanel = new ExtendedJPanel();
		final ExtendedGridBagConstraints objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints = new ExtendedGridBagConstraints();
		objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints.setGridY(0);
		objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints.setGridX(0);
		objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints.setInsideLocation(GridBagConstraints.WEST);
		objLcheckboxAndLabelTooltipJPanel.add(	this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_TOOLTIPS],
												objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints);
		objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints.setGridX(1);
		objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints.setGridY(0);
		objLcheckboxAndLabelTooltipJPanel.add(	this.objGbooleanGlobalJLabelA[Constants.bytS_BOOLEAN_GLOBAL_TOOLTIPS],
												objLcheckboxAndLabelTooltipJPanelextendedGridBagConstraints);
		objLextendedGridBagConstraints.setInsideLocation(GridBagConstraints.WEST);
		objLextendedGridBagConstraints.setGridX(1);
		Tools.out(objLextendedGridBagConstraints);
		objLmessagesJPanel.add(objLcheckboxAndLabelTooltipJPanel, objLextendedGridBagConstraints);

		// Other Tooltips :
		for (final byte bytLbooleanGlobalXTooltips : new byte[] { Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS,
			Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS, Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS }) {
			final ExtendedJPanel objLcheckboxAndLabelJPanel = new ExtendedJPanel();
			final ExtendedGridBagConstraints objLcheckboxAndLabelJPanelextendedGridBagConstraints = new ExtendedGridBagConstraints();
			objLcheckboxAndLabelJPanelextendedGridBagConstraints.setGridLocation(0, 0);
			objLcheckboxAndLabelJPanelextendedGridBagConstraints.setLeftMargin(30);
			objLcheckboxAndLabelJPanelextendedGridBagConstraints.setVerticalMargins(0, 3);
			objLcheckboxAndLabelJPanelextendedGridBagConstraints.setInsideLocation(GridBagConstraints.WEST);
			objLcheckboxAndLabelJPanel.add(	this.objGbooleanGlobalJCheckBoxA[bytLbooleanGlobalXTooltips],
											objLcheckboxAndLabelJPanelextendedGridBagConstraints);
			objLcheckboxAndLabelJPanelextendedGridBagConstraints.setGridLocation(1, 0);
			objLcheckboxAndLabelJPanelextendedGridBagConstraints.setLeftMargin(5);
			objLcheckboxAndLabelJPanel.add(	this.objGbooleanGlobalJLabelA[bytLbooleanGlobalXTooltips],
											objLcheckboxAndLabelJPanelextendedGridBagConstraints);
			objLextendedGridBagConstraints.setGridLocation(1, objLextendedGridBagConstraints.getGridY() + 1);
			objLextendedGridBagConstraints.setInsideLocation(GridBagConstraints.WEST);
			objLextendedGridBagConstraints.setVerticalMargins(0, 3);
			objLmessagesJPanel.add(objLcheckboxAndLabelJPanel, objLextendedGridBagConstraints);
		}

		// Pop-Up :
		objLextendedGridBagConstraints.setTopMargin(15);
		objLextendedGridBagConstraints.setGridLocation(0, objLextendedGridBagConstraints.getGridY() + 1);
		objLextendedGridBagConstraints.setInsideLocation(GridBagConstraints.EAST);
		objLmessagesJPanel.add(new JLabel("Pop-Up :"), objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridX(1);
		objLextendedGridBagConstraints.setLeftMargin(5);
		objLextendedGridBagConstraints.setInsideLocation(GridBagConstraints.WEST);
		for (final byte bytLpreferenceIndex : new byte[] { Constants.bytS_BOOLEAN_GLOBAL_CLIPBOARD_INFORMATION,
			Constants.bytS_BOOLEAN_GLOBAL_FILE_ACCESS_ERRORS, Constants.bytS_BOOLEAN_GLOBAL_FILE_EXPORT_INFORMATION,
			Constants.bytS_BOOLEAN_GLOBAL_NAVIGATOR_CONFIGURATION, Constants.bytS_BOOLEAN_GLOBAL_NEW_FILE_INFORMATION,
			Constants.bytS_BOOLEAN_GLOBAL_SCREEN_PLAY_INFORMATION }) {
			final JPanel objLjPanel = new JPanel();
			objLjPanel.setLayout(new BoxLayout(objLjPanel, BoxLayout.LINE_AXIS));
			objLjPanel.add(this.objGbooleanGlobalJCheckBoxA[bytLpreferenceIndex]);
			objLjPanel.add(this.objGbooleanGlobalJLabelA[bytLpreferenceIndex]);
			objLmessagesJPanel.add(objLjPanel, objLextendedGridBagConstraints);
			objLextendedGridBagConstraints.setGridY(objLextendedGridBagConstraints.getGridY() + 1);
			objLextendedGridBagConstraints.setVerticalMargins(3);
		}

		// File syntax warning & error preferences :
		// for (final byte bytLbooleanLocalMessage : new byte[] { PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_WARNINGS,
		// PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_ERRORS }) {
		// final JPanel objLjPanel = new JPanel();
		// objLjPanel.setLayout(new BoxLayout(objLjPanel, BoxLayout.LINE_AXIS));
		// objLjPanel.add(this.objGbooleanLocalJCheckBoxA[bytLbooleanLocalMessage]);
		// objLjPanel.add(this.objGbooleanLocalJLabelA[bytLbooleanLocalMessage]);
		// objLmessagesJCheckBoxesJPanel.add(objLjPanel, objLextendedGridBagConstraints);
		// }

		// Message preferences :
		return objLmessagesJPanel;
	}

	/**
	 * @return
	 */
	final private JPanel getSoundsJPanel() {

		final JPanel objLsoundsJCheckBoxesJPanel = new JPanel(new GridBagLayout());
		objLsoundsJCheckBoxesJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLjCheckBoxesExtendedGridBagConstraints = new ExtendedGridBagConstraints(GridBagConstraints.RELATIVE, 0);
		objLsoundsJCheckBoxesJPanel.add(this.objGbooleanLocalJCheckBoxA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS]);
		final JPanel objLsoundsJCheckBoxJPanel = new JPanel();
		objLsoundsJCheckBoxJPanel.setLayout(new BoxLayout(objLsoundsJCheckBoxJPanel, BoxLayout.LINE_AXIS));
		objLsoundsJCheckBoxJPanel.add(this.objGbooleanLocalJLabelA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS]);
		objLsoundsJCheckBoxJPanel.add(new JLabel(" :"));
		objLsoundsJCheckBoxesJPanel.add(objLsoundsJCheckBoxJPanel, objLjCheckBoxesExtendedGridBagConstraints);

		byte bytLposY = 1;
		for (final byte bytLbooleanLocalIndex : new byte[] { PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_CATCH_SOUNDS,
			PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_THROW_SOUNDS, PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME }) {
			final JPanel objLjPanel = new JPanel();
			objLjPanel.setLayout(new BoxLayout(objLjPanel, BoxLayout.LINE_AXIS));
			objLjPanel.add(this.objGbooleanLocalJCheckBoxA[bytLbooleanLocalIndex]);
			objLjPanel.add(this.objGbooleanLocalJLabelA[bytLbooleanLocalIndex]);
			this.setSoundsJLabelsEnabled();
			objLjCheckBoxesExtendedGridBagConstraints.setGridLocation(1, bytLposY++);
			objLsoundsJCheckBoxesJPanel.add(objLjPanel, objLjCheckBoxesExtendedGridBagConstraints);
		}
		return objLsoundsJCheckBoxesJPanel;
	}

	/**
	 * @return
	 */
	final private JPanel getTasksButtonsJPanel() {

		final JPanel objLjButtonsJPanel = new JPanel(new GridLayout(1, 5, 5, 0));
		objLjButtonsJPanel.setOpaque(true);
		objLjButtonsJPanel.add(this.objGoKJButton);
		objLjButtonsJPanel.add(this.objGcancelJButton);
		objLjButtonsJPanel.add(this.objGapplyJButton);
		objLjButtonsJPanel.add(this.objGrestoreJButton);
		objLjButtonsJPanel.add(this.objGresetJButton);
		return objLjButtonsJPanel;
	}

	final private JPanel getValuesJPanel() {
		final JPanel objLpreferencesValuesJPanel = new JPanel(new GridBagLayout());
		objLpreferencesValuesJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLJLabelsExtendedGridBagConstraints =
																					new ExtendedGridBagConstraints(	0,
																													GridBagConstraints.RELATIVE,
																													1,
																													1,
																													GridBagConstraints.EAST,
																													10,
																													10,
																													5,
																													0);
		final ExtendedGridBagConstraints objLjScrollBarsExtendedGridBagConstraints =
																						new ExtendedGridBagConstraints(	1,
																														GridBagConstraints.RELATIVE,
																														1,
																														1,
																														GridBagConstraints.CENTER,
																														10,
																														10,
																														5,
																														5,
																														GridBagConstraints.HORIZONTAL,
																														1.0F,
																														0.0F);
		final ExtendedGridBagConstraints objLvaluesJLabelsExtendedGridBagConstraints =
																						new ExtendedGridBagConstraints(	2,
																														GridBagConstraints.RELATIVE,
																														1,
																														1,
																														10,
																														10,
																														0,
																														5);
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			objLpreferencesValuesJPanel.add(this.objGbyteLocalJLabelA[bytLpreferenceIndex], objLJLabelsExtendedGridBagConstraints);
			objLpreferencesValuesJPanel.add(this.objGbyteLocalJScrollBarA[bytLpreferenceIndex], objLjScrollBarsExtendedGridBagConstraints);
			objLpreferencesValuesJPanel.add(this.objGbyteLocalValueJLabelA[bytLpreferenceIndex], objLvaluesJLabelsExtendedGridBagConstraints);
		}

		return objLpreferencesValuesJPanel;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void initDefaultValues() {

		// Global boolean preferences :
		this.bolGbooleanGlobalAA = new boolean[Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER][2];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																							this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																											Preferences.readGlobalBooleanPreference(bytLpreferenceIndex);
		}

		// Local boolean preferences :
		this.bolGbooleanLocalAA = new boolean[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER][3];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																							this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																											Preferences.readLocalBooleanPreference(this.bytGbooleanLocalControlIndexA[bytLpreferenceIndex]);
			this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE] =
																						this.objGcontrolJFrame.isControlSelected(this.bytGbooleanLocalControlIndexA[bytLpreferenceIndex]);
		}

		// Global byte preferences :
		this.bytGbyteGlobalAA = new byte[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER][2];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																							this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																											Preferences.readGlobalBytePreference(bytLpreferenceIndex);
		}

		// Local byte preferences :
		this.bytGbyteLocalAA = new byte[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER][3];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																						this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																									Preferences.readLocalBytePreference(this.bytGbyteLocalControlIndexA[bytLpreferenceIndex]);
			this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE] =
																						this.objGcontrolJFrame.getControlValue(this.bytGbyteLocalControlIndexA[bytLpreferenceIndex]);
		}

		// Local string preferences :
		this.strGstringLocalAA = new String[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER][4];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																							this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																											this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																																																	Preferences.readLocalStringPreference(this.bytGstringLocalControlIndexA[bytLpreferenceIndex]);
			this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_SAVE] =
																						new String(this.objGcontrolJFrame.getControlString(this.bytGstringLocalControlIndexA[bytLpreferenceIndex]));
		}

		// Global string preference :
		this.strGstringGlobalAA = new String[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER][2];
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			switch (bytLpreferenceIndex) {
				case Constants.bytS_STRING_GLOBAL_LANGUAGE:
					this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] =
																									this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] =
																																													Preferences.readGlobalStringPreference(bytLpreferenceIndex);
					break;
			}
		}

		this.intGpatternIndexA = new int[2];
		this.intGpatternIndexA[Constants.bytS_UNCLASS_INITIAL] =
																	this.intGpatternIndexA[Constants.bytS_UNCLASS_CURRENT] =
																																this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
	}

	final private void setBounds(boolean bolPcenter) {

		// Memorize current values :
		final int[] intLbytGlobalCurrentIndexA = new int[this.objGbyteGlobalJComboBoxA.length];
		for (int intLjComboBoxIndex = 0; intLjComboBoxIndex < this.objGbyteGlobalJComboBoxA.length; ++intLjComboBoxIndex) {
			if (intLjComboBoxIndex != Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION) {
				intLbytGlobalCurrentIndexA[intLjComboBoxIndex] = this.objGbyteGlobalJComboBoxA[intLjComboBoxIndex].getSelectedIndex();
			}
		}
		final int[] intLstringGlobalCurrentIndexA = new int[this.objGstringGlobalJComboBoxA.length];
		for (int intLjComboBoxIndex = 0; intLjComboBoxIndex < this.objGstringGlobalJComboBoxA.length; ++intLjComboBoxIndex) {
			intLstringGlobalCurrentIndexA[intLjComboBoxIndex] = this.objGstringGlobalJComboBoxA[intLjComboBoxIndex].getSelectedIndex();
		}

		// Set widest values :
		for (int intLjComboBoxIndex = 0; intLjComboBoxIndex < this.objGbyteGlobalJComboBoxA.length; ++intLjComboBoxIndex) {
			if (intLjComboBoxIndex != Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION) {
				this.objGbyteGlobalJComboBoxA[intLjComboBoxIndex].selectIndex(this.objGbyteGlobalJComboBoxA[intLjComboBoxIndex].getWidestValueIndex());
				this.objGbyteGlobalJComboBoxA[intLjComboBoxIndex].setPreferredSize(null);
			}
		}
		for (int intLjComboBoxIndex = 0; intLjComboBoxIndex < this.objGstringGlobalJComboBoxA.length; ++intLjComboBoxIndex) {
			this.objGstringGlobalJComboBoxA[intLjComboBoxIndex].selectIndex(this.objGstringGlobalJComboBoxA[intLjComboBoxIndex].getWidestValueIndex());
			this.objGstringGlobalJComboBoxA[intLjComboBoxIndex].setPreferredSize(null);
		}

		// Pack :
		int intLwidth = this.getWidth();
		int intLheight = this.getHeight();
		this.validate();
		// this.setVisible(false);
		this.pack();

		// Restore current values :
		for (int intLjComboBoxIndex = 0; intLjComboBoxIndex < this.objGbyteGlobalJComboBoxA.length; ++intLjComboBoxIndex) {
			if (intLjComboBoxIndex != Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION) {
				this.objGbyteGlobalJComboBoxA[intLjComboBoxIndex].selectIndex(intLbytGlobalCurrentIndexA[intLjComboBoxIndex]);
			}
		}
		for (int intLjComboBoxIndex = 0; intLjComboBoxIndex < this.objGstringGlobalJComboBoxA.length; ++intLjComboBoxIndex) {
			this.objGstringGlobalJComboBoxA[intLjComboBoxIndex].selectIndex(intLstringGlobalCurrentIndexA[intLjComboBoxIndex]);
		}

		// Resize combo boxes :
		// for (final PreferencebytGlobalJComboBox objLpreferencebytGlobalJComboBox : this.objGbytGlobalJComboBoxA) {
		// final Dimension objLdimension =
		// new Dimension( objLpreferencebytGlobalJComboBox.getWidth() + 64,
		// objLpreferencebytGlobalJComboBox.getHeight());
		// objLpreferencebytGlobalJComboBox.setSize(objLdimension);
		// objLpreferencebytGlobalJComboBox.setMinimumSize(objLdimension);
		// }
		// for (final PreferenceStringGlobalJComboBox objLpreferenceStringGlobalJComboBox : this.objGstringGlobalJComboBoxA) {
		// final Dimension objLdimension =
		// new Dimension( objLpreferenceStringGlobalJComboBox.getWidth() + 64,
		// objLpreferenceStringGlobalJComboBox.getHeight());
		// objLpreferenceStringGlobalJComboBox.setSize(objLdimension);
		// objLpreferenceStringGlobalJComboBox.setMinimumSize(objLdimension);
		// }
		if (bolPcenter) {
			this.objGcontrolJFrame.setWindowBounds(this, this.objGcontrolJFrame, true);
		} else {
			intLwidth = Math.max(intLwidth, this.getWidth());
			intLheight = Math.max(intLheight, this.getHeight());
			this.setSize(intLwidth, intLheight);
			final int intLlastX = this.getX() + intLwidth, intLlastY = this.getY() + intLheight;
			if (intLlastX > Constants.intS_GRAPHICS_SCREEN_WIDTH || intLlastY > Constants.intS_GRAPHICS_SCREEN_HEIGHT) {
				this.setLocation(Constants.intS_GRAPHICS_SCREEN_WIDTH - intLwidth, Constants.intS_GRAPHICS_SCREEN_HEIGHT - intLheight);
			}
		}
		// this.setVisible(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setByteGlobalJLabels() {

		// Gamma Correction :
		this.objGbyteGlobalJScrollBarA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION].selectValue(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION][Constants.bytS_UNCLASS_CURRENT]);
		this.objGbyteGlobalValueJLabelA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION].setText(Strings.doConcat(	"\327 ",
																												this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION][Constants.bytS_UNCLASS_CURRENT] / 10.0F));

		// Ball Number :
		final boolean bolLballsNumberSelected = this.bolGbooleanGlobalAA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT];
		this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER].select(bolLballsNumberSelected);

		this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER].setEnabled(bolLballsNumberSelected);
		this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER].selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT] - 1);

		this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER].setEnabled(bolLballsNumberSelected);
		this.objGballsNumberDotsJLabel.setEnabled(bolLballsNumberSelected);
		this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER].selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER][Constants.bytS_UNCLASS_CURRENT] - 1);
		this.objGballsNumberJLabel.setEnabled(bolLballsNumberSelected);

		// Skill :
		final boolean bolLskillSelected = this.bolGbooleanGlobalAA[Constants.bytS_BOOLEAN_GLOBAL_SKILL][Constants.bytS_UNCLASS_CURRENT];
		this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_SKILL].select(bolLskillSelected);
		this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL].setEnabled(bolLskillSelected);
		this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL].selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL][Constants.bytS_UNCLASS_CURRENT]);
		this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL].setEnabled(bolLskillSelected);
		this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL].selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL][Constants.bytS_UNCLASS_CURRENT]);
		this.objGskillDotsJLabel.setEnabled(bolLskillSelected);

		// Mark :
		final boolean bolLmarkSelected = this.bolGbooleanGlobalAA[Constants.bytS_BOOLEAN_GLOBAL_MARK][Constants.bytS_UNCLASS_CURRENT];
		this.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_MARK].select(bolLmarkSelected);
		this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_LOW_MARK].setEnabled(bolLmarkSelected);
		this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_LOW_MARK].selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_LOW_MARK][Constants.bytS_UNCLASS_CURRENT]);
		this.objGbyteGlobalJLabelA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK].setEnabled(bolLmarkSelected);
		this.objGbyteGlobalJComboBoxA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK].selectIndex(this.bytGbyteGlobalAA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK][Constants.bytS_UNCLASS_CURRENT]);
		this.objGmarkDotsJLabel.setEnabled(bolLmarkSelected);
		this.objGmarkJButton.setEnabled(bolLmarkSelected);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setByteLocalJLabels() {

		// Speed :
		this.objGbyteLocalValueJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED].setText(Strings.doConcat(	"\327 ",
																														PatternsManager.getByteParameterValueString(this.bytGbyteLocalControlIndexA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED],
																																									this.bytGbyteLocalAA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED][Constants.bytS_UNCLASS_CURRENT],
																																									Constants.bytS_EXTENSION_JMP)));
		boolean bolLenabled =
								this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SPEED)
									|| this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		this.objGbyteLocalValueJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED].setEnabled(bolLenabled);
		this.objGbyteLocalJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED].setEnabled(bolLenabled);

		// Fluidity :
		this.objGbyteLocalValueJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY].setText(Strings.doConcat(this.bytGbyteLocalAA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY][Constants.bytS_UNCLASS_CURRENT] < 10
																																																											? '0'
																																																											: null,
																														this.bytGbyteLocalAA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY][Constants.bytS_UNCLASS_CURRENT],
																														" %"));
		bolLenabled =
						this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLUIDITY)
							|| this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		this.objGbyteLocalValueJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY].setEnabled(bolLenabled);
		this.objGbyteLocalJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY].setEnabled(bolLenabled);

		// Balls :
		this.objGbyteLocalValueJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS].setText(this.bytGbyteLocalAA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS][Constants.bytS_UNCLASS_CURRENT] == Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE
																																																																? "\267"
																																																																: this.bytGbyteLocalAA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS][Constants.bytS_UNCLASS_CURRENT] == Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL
																																																																																																							? "\u221e"
																																																																																																							: Strings.doConcat(	this.bytGbyteLocalAA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS][Constants.bytS_UNCLASS_CURRENT],
																																																																																																												" ~"));
		bolLenabled = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		this.objGbyteLocalValueJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS].setEnabled(bolLenabled);
		this.objGbyteLocalJLabelA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS].setEnabled(bolLenabled);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setDialogJButtonsEnabled() {
		boolean bolLenabled = false;

		final byte bytLtypePreferencesMaximumNumber =
														(byte) Math.max(Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER,
																		Math.max(	PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER,
																					Math.max(	Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER,
																								Math.max(	PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER,
																											Math.max(	Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER,
																														PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER)))));
		for (byte bytLpreferenceIndex = 0; !bolLenabled && bytLpreferenceIndex < bytLtypePreferencesMaximumNumber; ++bytLpreferenceIndex) {
			if (bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER
				&& this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
				|| bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER
				&& this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
				|| bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER
				&& this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
				|| bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER
				&& this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
				|| bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER

				&& (this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]
				/* || this.bolGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL] != this.bolGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] */)
				|| bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER
				&& !this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_INITIAL].equals(this.strGstringLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_TEMPORARY_CURRENT])) {
				bolLenabled = true;
			}
		}
		this.objGapplyJButton.setEnabled(bolLenabled);
		this.objGrestoreJButton.setEnabled(bolLenabled);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setJButtonsColors() {

		// for (int intLjButtonIndex = 0; intLjButtonIndex < bytS_STRING_LOCAL_CONTROLS_NUMBER; ++intLjButtonIndex)
		// this.objGstringLocalPreferenceColorJButtonA[intLjButtonIndex].repaint();
		for (final PreferenceStringLocalColorJButton objLpreferenceJButton : this.objGstringLocalColorJButtonA) {
			objLpreferenceJButton.repaint();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setJDialogDefaultKeys() {

		final JRootPane objLrootPane = this.getRootPane();

		// Enter key :
		objLrootPane.setDefaultButton(this.objGoKJButton);

		// Escape key :
		final InputMap objLinputMap = objLrootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Constants.strS_ENGINE_ESCAPE_ACTION);
		objLrootPane.getActionMap().put(Constants.strS_ENGINE_ESCAPE_ACTION, new PreferencesEscapeAction(this));
	}

	final private void setLanguage() {

		this.setTitle(this.getLanguageString(Language.intS_TITLE_PREFERENCES));
		this.objGjTabbedPane.setTitleAt(0, Strings.getEnlargedString(this.getLanguageString(Language.intS_LABEL_MESSAGES)));
		this.objGjTabbedPane.setTitleAt(1, Strings.getEnlargedString(this.getLanguageString(Language.intS_LABEL_VALUES)));
		this.objGjTabbedPane.setTitleAt(2, Strings.getEnlargedString(this.getLanguageString(Language.intS_LABEL_FILTERS)));
		this.objGjTabbedPane.setTitleAt(3, Strings.getEnlargedString(this.getLanguageString(Language.intS_LABEL_SOUNDS)));
		this.objGjTabbedPane.setTitleAt(4, Strings.getEnlargedString(this.getLanguageString(Language.intS_LABEL_COLORS)));
		this.objGoKJButton.setText();
		this.objGcancelJButton.setText();
		this.objGapplyJButton.setText();
		this.objGrestoreJButton.setText();
		this.objGresetJButton.setText();

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.objGbooleanGlobalJLabelA[bytLpreferenceIndex].setText();
		}

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGbooleanLocalJLabelA[bytLpreferenceIndex].setText();
		}

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.objGstringGlobalJLabelA[bytLpreferenceIndex].setText(Strings.doConcat(	this.getLanguageString(this.intGstringGlobalLanguageIndexA[bytLpreferenceIndex]),
																						" :"));
		}

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGbyteLocalJLabelA[bytLpreferenceIndex].setText(Strings.doConcat(this.getLanguageString(this.intGbyteLocalLanguageIndexA[bytLpreferenceIndex]),
																					" :"));
		}

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.objGbyteGlobalJLabelA[bytLpreferenceIndex].setText(this.getLanguageString(this.intGbyteGlobalLanguageIndexA[bytLpreferenceIndex]));
			if (bytLpreferenceIndex != Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION) {
				this.objGbyteGlobalJComboBoxA[bytLpreferenceIndex].setList();
			}
		}
		this.objGballsNumberJLabel.setText(this.getLanguageString(Language.intS_LABEL_BALLS));

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGstringLocalJLabelA[bytLpreferenceIndex].setText(Strings.doConcat(	this.getLanguageString(this.intGstringLocalLanguageIndexA[bytLpreferenceIndex]),
																						" :"));
		}

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.objGstringGlobalJLabelA[bytLpreferenceIndex].setText(Strings.doConcat(	this.getLanguageString(this.intGstringGlobalLanguageIndexA[bytLpreferenceIndex]),
																						" :"));
			this.objGstringGlobalJComboBoxA[bytLpreferenceIndex].setList();
		}

		this.setBounds(false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setLanguageCorrespondances() {

		// Global boolean language index correspondances :
		this.intGbooleanGlobalLanguageIndexA = new int[Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER];
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_TOOLTIPS] = Language.intS_CHECKBOX_DISPLAY_TOOLTIPS;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS] = Language.intS_CHECKBOX_DISPLAY_MENUS_TOOLTIPS;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS] = Language.intS_CHECKBOX_DISPLAY_BUTTONS_TOOLTIPS;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS] = Language.intS_CHECKBOX_DISPLAY_FIELDS_TOOLTIPS;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_SCREEN_PLAY_INFORMATION] = Language.intS_CHECKBOX_SCREEN_PLAY_INFORMATION;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_FILE_EXPORT_INFORMATION] = Language.intS_CHECKBOX_FILE_EXPORT_INFORMATION;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_FILE_ACCESS_ERRORS] = Language.intS_CHECKBOX_FILE_ACCESS_ERRORS;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_CLIPBOARD_INFORMATION] = Language.intS_CHECKBOX_PATTERNS_CLIPBOARD_USAGE;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_NEW_FILE_INFORMATION] = Language.intS_CHECKBOX_NEW_FILE_INFORMATION;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_NAVIGATOR_CONFIGURATION] = Language.intS_CHECKBOX_NAVIGATOR_CONFIGURATION;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER] = Language.intS_LABEL_BALLS_NUMBER;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_SKILL] = Language.intS_LABEL_SKILL;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_MARK] = Language.intS_LABEL_MARK;
		this.intGbooleanGlobalLanguageIndexA[Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS] = Language.intS_CHECKBOX_INVALID_PATTERNS;

		// Local boolean language index correspondances :
		this.intGbooleanLocalLanguageIndexA = new int[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER];
		this.intGbooleanLocalLanguageIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_INFOS] = Language.intS_CHECKBOX_FILE_SYNTAX_INFOS;
		this.intGbooleanLocalLanguageIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_WARNINGS] = Language.intS_CHECKBOX_FILE_SYNTAX_WARNINGS;
		this.intGbooleanLocalLanguageIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_ERRORS] = Language.intS_CHECKBOX_FILE_SYNTAX_ERRORS;
		this.intGbooleanLocalLanguageIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS] = Language.intS_CHECKBOX_SOUNDS;
		this.intGbooleanLocalLanguageIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_CATCH_SOUNDS] = Language.intS_CHECKBOX_CATCH_SOUNDS;
		this.intGbooleanLocalLanguageIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_THROW_SOUNDS] = Language.intS_CHECKBOX_THROW_SOUNDS;
		this.intGbooleanLocalLanguageIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME] = Language.intS_CHECKBOX_METRONOME;

		// Global byte language index correspondances :
		this.intGbyteGlobalLanguageIndexA = new int[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		this.intGbyteGlobalLanguageIndexA[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER] = Language.intS_LABEL_FROM;
		this.intGbyteGlobalLanguageIndexA[Constants.bytS_BYTE_GLOBAL_LOW_SKILL] = Language.intS_LABEL_FROM;
		this.intGbyteGlobalLanguageIndexA[Constants.bytS_BYTE_GLOBAL_LOW_MARK] = Language.intS_LABEL_FROM;
		this.intGbyteGlobalLanguageIndexA[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER] = Language.intS_LABEL_TO;
		this.intGbyteGlobalLanguageIndexA[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL] = Language.intS_LABEL_TO;
		this.intGbyteGlobalLanguageIndexA[Constants.bytS_BYTE_GLOBAL_HIGH_MARK] = Language.intS_LABEL_TO;
		this.intGbyteGlobalLanguageIndexA[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION] = Language.intS_LABEL_GAMMA_CORRECTION;

		// Local byte language index correspondances :
		this.intGbyteLocalLanguageIndexA = new int[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER];
		this.intGbyteLocalLanguageIndexA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY] = Language.intS_LABEL_FLUIDITY;
		this.intGbyteLocalLanguageIndexA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED] = Language.intS_LABEL_SPEED;
		this.intGbyteLocalLanguageIndexA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS] = Language.intS_LABEL_BALLS;

		// Global string language index correspondances :
		this.intGstringGlobalLanguageIndexA = new int[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER];
		this.intGstringGlobalLanguageIndexA[Constants.bytS_STRING_GLOBAL_LANGUAGE] = Language.intS_LABEL_LANGUAGE;

		// Local string language index correspondances :
		this.intGstringLocalLanguageIndexA = new int[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER];
		this.intGstringLocalLanguageIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY] = Language.intS_LABEL_DAY_SITESWAP_COLOR;
		this.intGstringLocalLanguageIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT] = Language.intS_LABEL_NIGHT_SITESWAP_COLOR;
		this.intGstringLocalLanguageIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY] = Language.intS_LABEL_DAY_JUGGLER_COLOR;
		this.intGstringLocalLanguageIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT] = Language.intS_LABEL_NIGHT_JUGGLER_COLOR;
		this.intGstringLocalLanguageIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY] = Language.intS_LABEL_DAY_BACKGROUND_COLOR;
		this.intGstringLocalLanguageIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT] =
																												Language.intS_LABEL_NIGHT_BACKGROUND_COLOR;

		// Boolean control index correspondances :
		this.bytGbooleanLocalControlIndexA = new byte[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER];
		this.bytGbooleanLocalControlIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_INFOS] = Constants.bytS_BOOLEAN_LOCAL_INFO;
		this.bytGbooleanLocalControlIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_WARNINGS] = Constants.bytS_BOOLEAN_LOCAL_WARNINGS;
		this.bytGbooleanLocalControlIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_ERRORS] = Constants.bytS_BOOLEAN_LOCAL_ERRORS;
		this.bytGbooleanLocalControlIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS] = Constants.bytS_BOOLEAN_LOCAL_SOUNDS;
		this.bytGbooleanLocalControlIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_CATCH_SOUNDS] = Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS;
		this.bytGbooleanLocalControlIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_THROW_SOUNDS] = Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS;
		this.bytGbooleanLocalControlIndexA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME] = Constants.bytS_BOOLEAN_LOCAL_METRONOME;

		// Byte control index correspondances :
		this.bytGbyteLocalControlIndexA = new byte[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER];
		this.bytGbyteLocalControlIndexA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY] = Constants.bytS_BYTE_LOCAL_FLUIDITY;
		this.bytGbyteLocalControlIndexA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED] = Constants.bytS_BYTE_LOCAL_SPEED;
		this.bytGbyteLocalControlIndexA[PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS] = Constants.bytS_BYTE_LOCAL_BALLS_TRAIL;

		// String control index correspondances :
		this.bytGstringLocalControlIndexA = new byte[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCES_NUMBER];
		this.bytGstringLocalControlIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY] = Constants.bytS_STRING_LOCAL_JUGGLER_DAY;
		this.bytGstringLocalControlIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT] = Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT;
		this.bytGstringLocalControlIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY] = Constants.bytS_STRING_LOCAL_SITESWAP_DAY;
		this.bytGstringLocalControlIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT] =
																											Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT;
		this.bytGstringLocalControlIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY] =
																											Constants.bytS_STRING_LOCAL_BACKGROUND_DAY;
		this.bytGstringLocalControlIndexA[PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT] =
																												Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public void setMouseCursorEnabled(boolean bolPenabled) {
		this.objGcontrolJFrame.setMouseCursorEnabled(bolPenabled);
		this.setCursor(Cursor.getPredefinedCursor(bolPenabled ? Cursor.DEFAULT_CURSOR : Cursor.WAIT_CURSOR));
	}

	private void setPreferenceControlGlobalValue(byte bytPpreferenceType, byte bytPvalue) {
		switch (bytPpreferenceType) {
			case Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION:
				this.bytGbyteGlobalAA[bytPpreferenceType][Constants.bytS_UNCLASS_CURRENT] = bytPvalue;
				Preferences.setGlobalBytePreference(bytPpreferenceType, bytPvalue);
				for (final PreferenceStringLocalColorJButton objLstringLocalColorJButton : this.objGstringLocalColorJButtonA) {
					objLstringLocalColorJButton.repaint();
				}
				this.objGcontrolJFrame.objGsiteswapColorChooserDropDownJButton.stateChanged(null);
				this.objGcontrolJFrame.objGjugglerColorChooserDropDownJButton.stateChanged(null);
				this.objGcontrolJFrame.objGbackgroundColorChooserDropDownJButton.stateChanged(null);
				this.objGcontrolJFrame.setColorsControls();
				this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_ANIMATION_IMAGE | Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
													| Constants.intS_ACTION_RECREATE_BACKGROUND_IMAGES | Constants.intS_ACTION_RECREATE_BALLS_IMAGES
													| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES
													| Constants.intS_ACTION_RECREATE_BALLS_ERASING_IMAGES
													| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
													| Constants.intS_ACTION_RECREATE_HANDS_IMAGES | Constants.intS_ACTION_RECREATE_SITESWAP_IMAGE
													| Constants.intS_ACTION_DRAW_SITESWAP | Constants.intS_ACTION_REFRESH_DRAWING);
				break;
		}
	}

	final private void setPreferenceControlLocalBoolean(byte bytPpreferenceType, boolean bolPselected, boolean bolPallPatterns) {

		final boolean bolLallPatterns =
										bolPallPatterns
											&& !this.objGcontrolJFrame.isBooleanLocal(this.bytGbooleanLocalControlIndexA[bytPpreferenceType]);
		final int intLfirstObjectIndex = bolLallPatterns ? 0 : this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		final int intLlastObjectIndex =
										bolLallPatterns ? this.objGcontrolJFrame.getPatternsManager().objGobjectA.length - 1
														: this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		for (int intLobjectIndex = intLfirstObjectIndex; intLobjectIndex <= intLlastObjectIndex; ++intLobjectIndex) {
			if (this.objGcontrolJFrame.getPatternsManager().isPattern(intLobjectIndex)
				&& this.objGcontrolJFrame.getPatternsManager().getPatternBooleanValue(intLobjectIndex, Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
				this.objGcontrolJFrame.getPatternsManager().setPatternBoolean(	intLobjectIndex,
																				this.bytGbooleanLocalControlIndexA[bytPpreferenceType],
																				Constants.bytS_UNCLASS_CURRENT,
																				bolPselected);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param strPpreference
	 * @param bolPallPatterns
	 */
	final public void setPreferenceControlLocalString(byte bytPpreferenceType, String strPpreference, boolean bolPallPatterns) {

		final boolean bolLallPatterns =
										bolPallPatterns
											&& !this.objGcontrolJFrame.isStringLocal(this.bytGstringLocalControlIndexA[bytPpreferenceType]);
		final int intLfirstObjectIndex = bolLallPatterns ? 0 : this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		final int intLlastObjectIndex =
										bolLallPatterns ? this.objGcontrolJFrame.getPatternsManager().objGobjectA.length - 1
														: this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		for (int intLobjectIndex = intLfirstObjectIndex; intLobjectIndex <= intLlastObjectIndex; ++intLobjectIndex) {
			if (this.objGcontrolJFrame.getPatternsManager().isPattern(intLobjectIndex)
				&& this.objGcontrolJFrame.getPatternsManager().getPatternBooleanValue(intLobjectIndex, Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
				this.objGcontrolJFrame.getPatternsManager().setPatternString(	intLobjectIndex,
																				this.bytGstringLocalControlIndexA[bytPpreferenceType],
																				Constants.bytS_UNCLASS_CURRENT,
																				strPpreference);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPvalue
	 * @param bolPallPatterns
	 */
	final private void setPreferenceControlLocalValue(byte bytPpreferenceType, byte bytPvalue, boolean bolPallPatterns) {

		final boolean bolLallPatterns = bolPallPatterns && !this.objGcontrolJFrame.isByteLocal(this.bytGbyteLocalControlIndexA[bytPpreferenceType]);
		final int intLfirstObjectIndex = bolLallPatterns ? 0 : this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		final int intLlastObjectIndex =
										bolLallPatterns ? this.objGcontrolJFrame.getPatternsManager().objGobjectA.length - 1
														: this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		boolean bolLrestartJuggling = false;
		for (int intLobjectIndex = intLfirstObjectIndex; intLobjectIndex <= intLlastObjectIndex; ++intLobjectIndex) {
			if (this.objGcontrolJFrame.getPatternsManager().isPattern(intLobjectIndex)) {
				switch (bytPpreferenceType) {
					case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_SPEED:
						if (this.objGcontrolJFrame.getPatternsManager().getPatternBooleanValue(intLobjectIndex, Constants.bytS_BOOLEAN_LOCAL_SPEED)
							|| this.objGcontrolJFrame.getPatternsManager().getPatternBooleanValue(	intLobjectIndex,
																									Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
							this.objGcontrolJFrame.getPatternsManager().setPatternByte(	intLobjectIndex,
																						this.bytGbyteLocalControlIndexA[bytPpreferenceType],
																						Constants.bytS_UNCLASS_CURRENT,
																						bytPvalue);

							// this.objGspeedJScrollBar.selectValue(bytPvalue); // TODO : à faire si pas de restart
							// this.setSpeedJLabels();
							bolLrestartJuggling = true;
						}
						break;

					case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY:
						if (this.objGcontrolJFrame	.getPatternsManager()
													.getPatternBooleanValue(intLobjectIndex, Constants.bytS_BOOLEAN_LOCAL_FLUIDITY)
							|| this.objGcontrolJFrame.getPatternsManager().getPatternBooleanValue(	intLobjectIndex,
																									Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
							this.objGcontrolJFrame.getPatternsManager().setPatternByte(	intLobjectIndex,
																						this.bytGbyteLocalControlIndexA[bytPpreferenceType],
																						Constants.bytS_UNCLASS_CURRENT,
																						bytPvalue);

							// this.objGfluidityJScrollBar.selectValue(bytPvalue); // TODO : à faire si pas de restart
							// this.setFluidityJLabels();
							bolLrestartJuggling = true;
						}
						break;

					case PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_BALLS:
						if (this.objGcontrolJFrame.getPatternsManager().getPatternBooleanValue(intLobjectIndex, Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
							this.objGcontrolJFrame.getPatternsManager().setPatternByte(	intLobjectIndex,
																						this.bytGbyteLocalControlIndexA[bytPpreferenceType],
																						Constants.bytS_UNCLASS_CURRENT,
																						bytPvalue);
							this.objGcontrolJFrame.objGballsJScrollBar.selectValue(bytPvalue);
							this.objGcontrolJFrame.setBallsControls();
						}
						break;
				}
			}
		}
		if (bolLrestartJuggling) {
			this.objGcontrolJFrame.doRestartJuggling();
		}
	}

	final private void setSoundsJLabelsEnabled() {
		final boolean bolLenabled = this.bolGbooleanLocalAA[PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS][Constants.bytS_UNCLASS_CURRENT];
		for (final byte bytLsubSoundIndex : new byte[] { PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_CATCH_SOUNDS,
			PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_THROW_SOUNDS, PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME }) {
			this.objGbooleanLocalJLabelA[bytLsubSoundIndex].setEnabled(bolLenabled);
		}

	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setWidgetsValues() {

		// Global boolean preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			this.objGbooleanGlobalJCheckBoxA[bytLpreferenceIndex].select(this.bolGbooleanGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
		}

		// Local boolean preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGbooleanLocalJCheckBoxA[bytLpreferenceIndex].setSelected(this.bolGbooleanLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
		}

		// Global string preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			// this.objGstringGlobalJComboBoxA[bytLpreferenceIndex].selectItem(this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
			this.objGstringGlobalJComboBoxA[bytLpreferenceIndex].selectIndex(this.objGcontrolJFrame.getLanguageIndex(this.strGstringGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]) + 1);
		}

		// Global byte preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			switch (bytLpreferenceIndex) {
				case Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION:
					this.objGbyteGlobalJScrollBarA[bytLpreferenceIndex].selectValue(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
				case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
					this.objGbyteGlobalJComboBoxA[bytLpreferenceIndex].selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT] - 1);
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
				case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
					this.objGbyteGlobalJComboBoxA[bytLpreferenceIndex].selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
				case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
					this.objGbyteGlobalJComboBoxA[bytLpreferenceIndex].selectIndex(this.bytGbyteGlobalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
					break;
			}
		}

		// Local byte preferences :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCES_NUMBER; ++bytLpreferenceIndex) {
			this.objGbyteLocalJScrollBarA[bytLpreferenceIndex].selectValue(this.bytGbyteLocalAA[bytLpreferenceIndex][Constants.bytS_UNCLASS_CURRENT]);
		}
		this.setByteLocalJLabels();

		// Filter preferences :
		this.setByteGlobalJLabels();

		// Color preferences :
		this.setJButtonsColors();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowActivated(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosed(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosing(WindowEvent objPwindowEvent) {
		this.objGcancelJButton.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, "Close"));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeactivated(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeiconified(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowIconified(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowOpened(WindowEvent objPwindowEvent) {}

	public boolean[][]							bolGbooleanGlobalAA;

	public boolean[][]							bolGbooleanLocalAA;

	public byte[]								bytGbooleanLocalControlIndexA;

	public byte[][]								bytGbyteGlobalAA;
	public byte[][]								bytGbyteLocalAA;
	public byte[]								bytGbyteLocalControlIndexA;
	public byte[]								bytGstringLocalControlIndexA;
	public int[]								intGbooleanGlobalLanguageIndexA;
	public int[]								intGbooleanLocalLanguageIndexA;
	public int[]								intGbyteGlobalLanguageIndexA;
	public int[]								intGbyteLocalLanguageIndexA;

	private int									intGdefaultJuggleLanguageIndex;

	public int[]								intGpatternIndexA;

	public int[]								intGstringGlobalLanguageIndexA;

	public int[]								intGstringLocalLanguageIndexA;

	private PreferencesDialogJButton			objGapplyJButton;

	private ExtendedJLabel						objGballsNumberDotsJLabel;

	private ExtendedJLabel						objGballsNumberJLabel;

	public PreferenceBooleanJCheckBox[]			objGbooleanGlobalJCheckBoxA;

	PreferenceBooleanJLabel[]					objGbooleanGlobalJLabelA;

	PreferenceBooleanJCheckBox[]				objGbooleanLocalJCheckBoxA;

	private PreferenceBooleanJLabel[]			objGbooleanLocalJLabelA;

	private PreferenceByteGlobalJComboBox[]		objGbyteGlobalJComboBoxA;

	private PreferenceByteGlobalJLabel[]		objGbyteGlobalJLabelA;

	private PreferenceByteGlobalJScrollBar[]	objGbyteGlobalJScrollBarA;

	private ExtendedJLabel[]					objGbyteGlobalValueJLabelA;

	private ExtendedJLabel[]					objGbyteLocalJLabelA;

	private PreferenceByteLocalJScrollBar[]		objGbyteLocalJScrollBarA;

	private ExtendedJLabel[]					objGbyteLocalValueJLabelA;

	public PreferencesDialogJButton				objGcancelJButton;

	private final ControlJFrame					objGcontrolJFrame;

	private JTabbedPane							objGjTabbedPane;

	private ExtendedJLabel						objGmarkDotsJLabel;

	private MarkJButton							objGmarkJButton;

	private PreferencesDialogJButton			objGoKJButton;

	private PreferencesDialogJButton			objGresetJButton;

	private PreferencesDialogJButton			objGrestoreJButton;

	private ExtendedJLabel						objGskillDotsJLabel;

	private PreferenceStringGlobalJComboBox[]	objGstringGlobalJComboBoxA;

	private ExtendedJLabel[]					objGstringGlobalJLabelA;

	public PreferenceStringLocalColorJButton[]	objGstringLocalColorJButtonA;

	private ExtendedJLabel[]					objGstringLocalJLabelA;

	public String[][]							strGstringGlobalAA;

	public String[][]							strGstringLocalAA;

	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCE_CATCH_SOUNDS		= 4;

	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCE_ERRORS			= 2;

	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCE_INFOS				= 0;

	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME			= 6;

	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCE_SOUNDS			= 3;

	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCE_THROW_SOUNDS		= 5;
	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCE_WARNINGS			= 1;

	final private static byte					bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER;

	final public static byte					bytS_BYTE_LOCAL_PREFERENCE_BALLS				= 0;

	final public static byte					bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY				= 2;

	final public static byte					bytS_BYTE_LOCAL_PREFERENCE_SPEED				= 1;

	final public static byte					bytS_BYTE_LOCAL_PREFERENCES_NUMBER;

	final public static byte					bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY		= 2;

	final public static byte					bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT	= 5;

	final public static byte					bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY		= 1;

	final public static byte					bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT		= 4;

	final public static byte					bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY		= 0;

	final public static byte					bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT		= 3;

	final public static byte					bytS_STRING_LOCAL_PREFERENCES_NUMBER;

	final public static int						intS_PREFERENCES_DIALOG_WIDTH					= Constants.intS_GRAPHICS_ANIMATION_DEFAULT_SIZE + 32;

	final private static long					serialVersionUID								= Constants.lngS_ENGINE_VERSION_NUMBER;

	static {
		bytS_STRING_LOCAL_PREFERENCES_NUMBER = PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT + 1;
		bytS_BYTE_LOCAL_PREFERENCES_NUMBER = PreferencesJDialog.bytS_BYTE_LOCAL_PREFERENCE_FLUIDITY + 1;
		bytS_BOOLEAN_LOCAL_PREFERENCES_NUMBER = PreferencesJDialog.bytS_BOOLEAN_LOCAL_PREFERENCE_METRONOME + 1;
	}
}

/*
 * @(#)PreferencesJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
