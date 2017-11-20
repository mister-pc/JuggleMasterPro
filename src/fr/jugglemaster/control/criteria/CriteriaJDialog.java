package fr.jugglemaster.control.criteria;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.control.window.JDialogWindowListener;
import fr.jugglemaster.pattern.Pattern;
import fr.jugglemaster.pattern.Sequence;
import fr.jugglemaster.pattern.Siteswap;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

public abstract class CriteriaJDialog extends JDialog implements ActionListener {

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	protected boolean				bolGalreadyDisplayed;

	protected final ExtendedJLabel	objGasynchroColonJLabel;

	protected final ExtendedJLabel	objGasynchroDashJLabel;

	protected ExtendedJLabel		objGasynchroJLabel;

	protected Sequence[]			objGasynchroSequenceA;

	protected CriteriaJComboBox		objGasynchroSequencesJComboBox;
	protected ClearCriteriaJButton	objGclearAsynchroJButton;
	protected ClearCriteriaJButton	objGclearPatternJButton;

	protected ClearCriteriaJButton	objGclearStyleJButton;

	protected ClearCriteriaJButton	objGclearSynchroJButton;

	protected final ExtendedJLabel	objGcombinationColonJLabel;

	protected ExtendedJLabel		objGcombinationJLabel;

	protected ControlJFrame			objGcontrolJFrame;

	protected JPanel				objGcriteriaJPanel;

	protected final ExtendedJLabel	objGpatternColonJLabel;

	protected ExtendedJLabel		objGpatternJLabel;

	protected CriteriaJComboBox		objGpatternsJComboBox;
	protected JRadioButton			objGpermissiveJRadioButton;

	protected JRadioButton			objGrestrictiveJRadioButton;

	protected ExtendedJLabel		objGsiteswapJLabel;

	protected final ExtendedJLabel	objGstyleColonJLabel;

	protected ExtendedJLabel		objGstyleJLabel;

	protected CriteriaJComboBox		objGstylesJComboBox;
	protected final ExtendedJLabel	objGsynchroColonJLabel;
	protected final ExtendedJLabel	objGsynchroDashJLabel;
	protected ExtendedJLabel		objGsynchroJLabel;
	protected Sequence[]			objGsynchroSequenceA;
	protected CriteriaJComboBox		objGsynchroSequencesJComboBox;

	public CriteriaJDialog(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, false);

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGalreadyDisplayed = false;

		this.objGcombinationColonJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":", Language.intS_TOOLTIP_CRITERIA_COMBINATION);
		this.objGpermissiveJRadioButton = new JRadioButton();
		this.objGrestrictiveJRadioButton = new JRadioButton();
		final ButtonGroup objLbuttonGroup = new ButtonGroup();
		objLbuttonGroup.add(this.objGpermissiveJRadioButton);
		objLbuttonGroup.add(this.objGrestrictiveJRadioButton);
		this.objGpermissiveJRadioButton.setSelected(true);
		final Font objLfont = this.objGcontrolJFrame.getFont();
		this.objGpermissiveJRadioButton.setFont(objLfont);
		this.objGpermissiveJRadioButton.setOpaque(true);
		this.objGpermissiveJRadioButton.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PERMISSIVE_CRITERS));
		this.objGrestrictiveJRadioButton.setFont(objLfont);
		this.objGrestrictiveJRadioButton.setOpaque(true);
		this.objGrestrictiveJRadioButton.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_RESTRICTIVE_CRITERS));

		this.objGpatternsJComboBox = new CriteriaJComboBox(this, Language.intS_TOOLTIP_PATTERN_NAME_PART);
		this.objGpatternsJComboBox.setBackground(true);
		this.objGsynchroSequencesJComboBox = new CriteriaJComboBox(this, true, Language.intS_TOOLTIP_SYNCHRO_SITESWAP_PART);
		this.setSequences(true);

		this.objGasynchroSequencesJComboBox = new CriteriaJComboBox(this, false, Language.intS_TOOLTIP_ASYNCHRO_SITESWAP_PART);
		this.setSequences(false);

		this.objGstylesJComboBox = new CriteriaJComboBox(this, Language.intS_TOOLTIP_STYLE_NAME_PART);
		this.objGstylesJComboBox.setBackground(true);

		this.objGcombinationJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Language.intS_TOOLTIP_CRITERIA_COMBINATION);
		this.objGpatternJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Language.intS_TOOLTIP_PATTERN_NAME_PART);
		this.objGsiteswapJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Language.intS_TOOLTIP_SITESWAP_PART);
		this.objGstyleJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Language.intS_TOOLTIP_STYLE_NAME_PART);
		this.objGsynchroJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Language.intS_TOOLTIP_SYNCHRO_SITESWAP_PART);
		this.objGasynchroJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Language.intS_TOOLTIP_ASYNCHRO_SITESWAP_PART);

		this.objGpatternColonJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":", Language.intS_TOOLTIP_PATTERN_NAME_PART);
		this.objGasynchroColonJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":", Language.intS_TOOLTIP_ASYNCHRO_SITESWAP_PART);
		this.objGasynchroDashJLabel = new ExtendedJLabel(this.objGcontrolJFrame, "-", Language.intS_TOOLTIP_ASYNCHRO_SITESWAP_PART);
		this.objGsynchroColonJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":", Language.intS_TOOLTIP_SYNCHRO_SITESWAP_PART);
		this.objGsynchroDashJLabel = new ExtendedJLabel(this.objGcontrolJFrame, "-", Language.intS_TOOLTIP_SYNCHRO_SITESWAP_PART);
		this.objGstyleColonJLabel = new ExtendedJLabel(this.objGcontrolJFrame, ":", Language.intS_TOOLTIP_STYLE_NAME_PART);

		this.objGclearPatternJButton = new ClearCriteriaJButton(this);
		this.objGclearStyleJButton = new ClearCriteriaJButton(this);
		this.objGclearSynchroJButton = new ClearCriteriaJButton(this);
		this.objGclearAsynchroJButton = new ClearCriteriaJButton(this);

		this.setClearable();

		this.objGcriteriaJPanel = new JPanel(new GridBagLayout());
		this.objGcriteriaJPanel.setOpaque(true);
		this.setLayout(new GridBagLayout());
		this.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this, false));
	}

	@Override public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("CriteriaJDialog.actionPerformed()");
		this.isActionPerformed(objPactionEvent);
	}

	protected void doLoadImages() {

		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLEAR_FIELD, 2);
		final ImageIcon icoLbW = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLEAR_FIELD_BW, 2);

		Tools.setIcons(this.objGclearPatternJButton, icoLbW, icoL);
		Tools.setIcons(this.objGclearStyleJButton, icoLbW, icoL);
		Tools.setIcons(this.objGclearSynchroJButton, icoLbW, icoL);
		Tools.setIcons(this.objGclearAsynchroJButton, icoLbW, icoL);
	}

	final public String getInfo() {
		return Strings.doConcat("Criteria :",
								Strings.strS_LINE_SEPARATOR,
								"¯¯¯¯¯¯¯¯¯¯",
								Strings.strS_LINE_SEPARATOR,
								"Pattern  : '",
								this.objGpatternsJComboBox,
								"'",
								Strings.strS_LINE_SEPARATOR,
								"Synchro  : '",
								this.objGsynchroSequencesJComboBox,
								"'",
								Strings.strS_LINE_SEPARATOR,
								"Asynchro : '",
								this.objGasynchroSequencesJComboBox,
								"'",
								Strings.strS_LINE_SEPARATOR,
								"Style    : '",
								this.objGstylesJComboBox,
								"'",
								Strings.strS_LINE_SEPARATOR);
	}

	final public ControlJFrame getJugglePanel() {
		return this.objGcontrolJFrame;
	}

	final protected Boolean getSequenceAble(boolean bolPsynchro) {

		final Sequence[] objLsequenceA = bolPsynchro ? this.objGsynchroSequenceA : this.objGasynchroSequenceA;
		final CriteriaJComboBox objLcriteriaJComboBox = bolPsynchro ? this.objGsynchroSequencesJComboBox : this.objGasynchroSequencesJComboBox;
		if (objLsequenceA == null) {
			objLcriteriaJComboBox.setBackground(true);
			return null;
		}
		for (final Sequence objLsequence : objLsequenceA) {
			if (!objLsequence.getStatus()) {
				objLcriteriaJComboBox.setBackground(false);
				return Boolean.FALSE;
			}
		}
		objLcriteriaJComboBox.setBackground(true);
		return Boolean.TRUE;
	}

	final private Boolean getSequenceMatching(Pattern objPpattern, boolean bolPsynchro) {

		final Sequence[] objLsequenceA = bolPsynchro ? this.objGsynchroSequenceA : this.objGasynchroSequenceA;
		if (objLsequenceA != null) {
			final Siteswap objLsiteswap = new Siteswap(objPpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_CURRENT]);
			if (objLsiteswap.bytGstatus >= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER) {
				if (objLsiteswap.bolGsynchro == bolPsynchro) {
					for (final Sequence objLsequence : objLsequenceA) {
						if (!objLsequence.isPartOf(objLsiteswap)) {
							return Boolean.FALSE;
						}
					}
					return Boolean.TRUE;
				}
				return null;
			}
			return Boolean.FALSE;
		}
		return null;
	}

	final public String getSequencesString(boolean bolPsynchro) {
		final Sequence[] objLsequenceA = bolPsynchro ? this.objGsynchroSequenceA : this.objGasynchroSequenceA;
		if (objLsequenceA == null) {
			return Strings.strS_EMPTY;
		}

		final StringBuilder objLstringBuilder = new StringBuilder(8 * objLsequenceA.length);
		for (int intLsequenceIndex = 0; intLsequenceIndex < objLsequenceA.length; ++intLsequenceIndex) {
			if (intLsequenceIndex > 0) {
				objLstringBuilder.append(' ');
			}
			objLstringBuilder.append(objLsequenceA[intLsequenceIndex].toString());
		}
		return objLstringBuilder.toString();
	}

	final protected Sequence[] getSiteswapSequenceA(boolean bolPsynchro) {

		final String strLtokens =
									Strings.getTrimmedNullString((bolPsynchro ? this.objGsynchroSequencesJComboBox
																				: this.objGasynchroSequencesJComboBox).getEditableText());
		if (strLtokens == null) {
			return null;
		}
		final ArrayList<Object> objLtokenAL = Strings.getTokens(strLtokens);
		final int intLtokensNumber = objLtokenAL.size();
		final Sequence[] objLsequenceA = new Sequence[intLtokensNumber];
		for (int intLtokenIndex = 0; intLtokenIndex < intLtokensNumber; ++intLtokenIndex) {
			objLsequenceA[intLtokenIndex] = Sequence.getSequence((String) objLtokenAL.get(intLtokenIndex), bolPsynchro);
		}
		return objLsequenceA;
	}

	final public boolean isAble() {

		final Boolean bolLsynchroAbleBoolean = this.getSequenceAble(true);
		final Boolean bolLasynchroAbleBoolean = this.getSequenceAble(false);
		return (Strings.getTrimmedNullString(this.objGpatternsJComboBox.getEditableText()) != null
				|| Strings.getTrimmedNullString(this.objGstylesJComboBox.getEditableText()) != null || bolLsynchroAbleBoolean == Boolean.TRUE || bolLasynchroAbleBoolean == Boolean.TRUE)
				&& bolLsynchroAbleBoolean != Boolean.FALSE && bolLasynchroAbleBoolean != Boolean.FALSE;
	}

	final protected boolean isActionPerformed(ActionEvent objPactionEvent) {

		Object objLsourceObject = objPactionEvent.getSource();
		if (objLsourceObject == this.objGclearPatternJButton) {
			objLsourceObject = this.objGpatternsJComboBox;
		} else if (objLsourceObject == this.objGclearStyleJButton) {
			objLsourceObject = this.objGstylesJComboBox;
		} else if (objLsourceObject == this.objGclearSynchroJButton) {
			objLsourceObject = this.objGsynchroSequencesJComboBox;
		} else if (objLsourceObject == this.objGclearAsynchroJButton) {
			objLsourceObject = this.objGasynchroSequencesJComboBox;
		} else {
			objLsourceObject = null;
		}

		// Clear combobox field :
		if (objLsourceObject != null) {
			final CriteriaJComboBox objLcriteriaJComboBox = (CriteriaJComboBox) objLsourceObject;
			objLcriteriaJComboBox.setEditableText(null);
			objLcriteriaJComboBox.requestFocusInWindow();
		}

		this.setClearable();
		return objLsourceObject != null;
	}

	// returns :
	// Boolean.TRUE if matching,
	// null if matching something, but not the pattern name
	// Boolean.FALSE if not matching
	final public boolean isPatternMatching(Object objPpatternObject, boolean bolPedition, boolean bolPmatchName) {

		if (!(objPpatternObject instanceof Pattern)) {
			return false;
		}

		final Pattern objLpattern = (Pattern) objPpatternObject;
		final boolean bolLrestrictive = this.objGrestrictiveJRadioButton.isSelected();

		// Find pattern name :
		final String strLpatternTokens = Strings.getTrimmedNullString(this.objGpatternsJComboBox.getEditableText());
		// JuggleTools.verbose("Pattern name : ", this.objGpatternsJComboBox.getEditableText(), ", qui devient ",
		// strLpatternTokens);
		byte bytLpattern = -2;
		if (strLpatternTokens != null) {
			final ArrayList<Object> objLpatternTokenAL = Strings.getTokens(strLpatternTokens);
			final String strLupperCasePattern =
												Strings.getUpperCaseString(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_PATTERN][Constants.bytS_UNCLASS_CURRENT]);
			boolean bolLtokensFound = true;
			for (final Object objLpatternTokenObject : objLpatternTokenAL) {
				if (strLupperCasePattern.indexOf(Strings.getUpperCaseString(objLpatternTokenObject.toString())) == Constants.bytS_UNCLASS_NO_VALUE) {
					if (bolLrestrictive) {
						return false;
					}
					bolLtokensFound = false;
					break;
				}
			}
			if (bolLtokensFound && !bolLrestrictive) {
				return true;
			}
			bytLpattern = (byte) (bolLrestrictive ? 1 : -1);
		}
		if (!bolPedition) {
			return bolLrestrictive ? bolPmatchName ? bytLpattern == 1 : true : bolPmatchName ? false : bytLpattern == -2;
		}

		// Find style name :
		final String strLstyleTokens = Strings.getTrimmedNullString(this.objGstylesJComboBox.getEditableText());
		byte bytLstyle = -2;
		if (strLstyleTokens != null) {
			final ArrayList<Object> objLstyleTokenAL = Strings.getTokens(strLstyleTokens);
			final String strLupperCaseStyle =
												Strings.getUpperCaseString(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_CURRENT]);
			boolean bolLtokensFound = true;
			for (final Object objLstyleTokenObject : objLstyleTokenAL) {
				if (strLupperCaseStyle.indexOf(Strings.getUpperCaseString(objLstyleTokenObject.toString())) == Constants.bytS_UNCLASS_NO_VALUE) {
					if (bolLrestrictive) {
						return false;
					}
					bolLtokensFound = false;
					break;
				}
			}
			if (bolLtokensFound && !bolLrestrictive) {
				return true;
			}
			bytLstyle = (byte) (bolLrestrictive ? 1 : -1);
		}

		// Find asynchro siteswap :
		final String strLasynchroTokens = Strings.getTrimmedNullString(this.objGasynchroSequencesJComboBox.getEditableText());
		byte bytLasynchro = -2;
		if (strLasynchroTokens != null) {
			bytLasynchro = Tools.getSignedBoolean(this.getSequenceMatching(objLpattern, false));
			if (bytLasynchro < 0 && bolLrestrictive) {
				return false;
			}
			if (bytLasynchro > 0 && !bolLrestrictive) {
				return true;
			}
		}

		// Find synchro siteswap :
		final String strLsynchroTokens = Strings.getTrimmedNullString(this.objGsynchroSequencesJComboBox.getEditableText());
		byte bytLsynchro = -2;
		if (strLsynchroTokens != null) {
			bytLsynchro = Tools.getSignedBoolean(this.getSequenceMatching(objLpattern, true));
			if (bytLsynchro < 0 && bolLrestrictive) {
				return false;
			}
			if (bytLsynchro > 0 && !bolLrestrictive) {
				return true;
			}
		}

		final Boolean bolLsequencesBoolean =
												bytLasynchro == 1 || bytLsynchro == 1 ? Boolean.TRUE
																						: bytLasynchro == 0 || bytLsynchro == 0 || bytLasynchro == -1
																							|| bytLsynchro == -1 ? Boolean.FALSE : null;

		if (bolLrestrictive) {
			return bytLstyle != -1 && bolLsequencesBoolean != Boolean.FALSE ? true : false;
		}
		return bytLstyle == 1 || bolLsequencesBoolean == Boolean.TRUE ? true : false;
	}

	public void setAble() {}

	abstract public void setButtons();

	final public void setClearable() {
		this.objGclearPatternJButton.setEnabled(this.objGpatternsJComboBox.getEditableText().length() > 0);
		this.objGclearStyleJButton.setEnabled(this.objGstylesJComboBox.getEditableText().length() > 0);
		this.objGclearSynchroJButton.setEnabled(this.objGsynchroSequencesJComboBox.getEditableText().length() > 0);
		this.objGclearAsynchroJButton.setEnabled(this.objGasynchroSequencesJComboBox.getEditableText().length() > 0);
	}

	protected void setLabels() {
		this.objGpermissiveJRadioButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_CHECKBOX_PERMISSIVE_COMBINATION));
		this.objGpermissiveJRadioButton.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PERMISSIVE_CRITERS));
		this.objGrestrictiveJRadioButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_CHECKBOX_RESTRICTIVE_COMBINATION));
		this.objGrestrictiveJRadioButton.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_RESTRICTIVE_CRITERS));
		this.objGcombinationJLabel.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_COMBINATION));
		this.objGcombinationJLabel.setToolTipText();
		this.objGcombinationColonJLabel.setToolTipText();

		this.objGpatternJLabel.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_PATTERN));
		this.objGpatternJLabel.setToolTipText();
		this.objGpatternColonJLabel.setToolTipText();
		this.objGsiteswapJLabel.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_SITESWAP));
		this.objGsiteswapJLabel.setToolTipText();
		this.objGsynchroJLabel.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_SYNCHRO));
		this.objGsynchroJLabel.setToolTipText();
		this.objGsynchroColonJLabel.setToolTipText();
		this.objGsynchroDashJLabel.setToolTipText();
		this.objGasynchroJLabel.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_ASYNCHRO));
		this.objGasynchroJLabel.setToolTipText();
		this.objGasynchroColonJLabel.setToolTipText();
		this.objGasynchroDashJLabel.setToolTipText();
		this.objGstyleJLabel.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_STYLE));
		this.objGstyleJLabel.setToolTipText();
		this.objGstyleColonJLabel.setToolTipText();
		this.objGcriteriaJPanel.setBorder(Tools.getTitledBorder(this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_CRITERIA),
																this.objGcontrolJFrame.getFont()));
		this.validate();
		this.pack();
	}

	final public void setSequences(boolean bolPsynchro) {

		final Sequence[] objLsequenceA = this.getSiteswapSequenceA(bolPsynchro);
		if (bolPsynchro) {
			this.objGsynchroSequenceA = objLsequenceA;
		} else {
			this.objGasynchroSequenceA = objLsequenceA;
		}
	}
}
