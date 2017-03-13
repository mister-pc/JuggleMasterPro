package jugglemasterpro.control.criteria;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.control.util.ExtendedJLabel;
import jugglemasterpro.pattern.Pattern;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class FiltersJDialog extends CriteriaJDialog {

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean					bolGactivated;

	private long					lngGlastActionTimestamp;

	private final CriteriaJButton	objGactivateJButton;

	private final ExtendedJLabel	objGasynchroTokensJLabel;

	private final CriteriaJButton	objGclearJButton;

	private final CriteriaJButton	objGcloseJButton;

	private final CriteriaJButton	objGdeactivateJButton;

	private final ExtendedJLabel	objGpatternTokensJLabel;

	private final ExtendedJLabel	objGstyleTokensJLabel;

	private final ExtendedJLabel	objGsynchroTokensJLabel;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public FiltersJDialog(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame);

		this.bolGactivated = false;

		this.lngGlastActionTimestamp = Constants.bytS_UNCLASS_NO_VALUE;
		this.objGclearJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_RESET_FILTERS, this);
		this.objGactivateJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_APPLY_FILTERS, this);
		this.objGdeactivateJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_DEACTIVATE_FILTERS, this);
		this.objGcloseJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_CLOSE_FILTERS, this);

		this.objGpatternTokensJLabel =
										new ExtendedJLabel(	this.objGcontrolJFrame,
															Strings.strS_SPACE,
															Language.intS_TOOLTIP_PATTERN_NAME_PART_APPLIED);
		this.objGsynchroTokensJLabel =
										new ExtendedJLabel(	this.objGcontrolJFrame,
															Strings.strS_SPACE,
															Language.intS_TOOLTIP_SYNCHRO_SITESWAP_PART_APPLIED);
		this.objGasynchroTokensJLabel =
										new ExtendedJLabel(	this.objGcontrolJFrame,
															Strings.strS_SPACE,
															Language.intS_TOOLTIP_ASYNCHRO_SITESWAP_PART_APPLIED);
		this.objGstyleTokensJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Strings.strS_SPACE, Language.intS_TOOLTIP_STYLE_NAME_PART_APPLIED);

		this.doAddCriteria();
		final JPanel objLbuttonsJPanel = this.doAddButtons();
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																											0,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											6,
																											6,
																											3,
																											3,
																											GridBagConstraints.HORIZONTAL,
																											1.0F,
																											0.0F);
		this.add(this.objGcriteriaJPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		this.add(objLbuttonsJPanel, objLextendedGridBagConstraints);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("FiltersJDialog.actionPerformed()");
		if (super.isActionPerformed(objPactionEvent)) {
			this.setAble();
			this.objGactivateJButton.setToolTipText();
			this.objGdeactivateJButton.setToolTipText();
			this.objGclearJButton.setToolTipText();
			this.objGcloseJButton.setToolTipText();
		} else {

			final long lngLtimeStamp = objPactionEvent.getWhen();
			if (lngLtimeStamp > this.lngGlastActionTimestamp) {
				this.lngGlastActionTimestamp = lngLtimeStamp;

				// Update combobox values :
				this.objGpatternsJComboBox.doAddCurrentItem();
				if (super.getSequenceAble(true) == Boolean.TRUE) {
					this.objGsynchroSequencesJComboBox.doAddCurrentItem();
				}
				if (super.getSequenceAble(false) == Boolean.TRUE) {
					this.objGasynchroSequencesJComboBox.doAddCurrentItem();
				}
				this.objGstylesJComboBox.doAddCurrentItem();

				final Object objLsource = objPactionEvent.getSource();
				if (objLsource == this.objGactivateJButton) {
					this.doFilter();
				} else if (objLsource == this.objGdeactivateJButton) {
					this.doUnfilter();
				} else if (objLsource == this.objGclearJButton) {
					this.doClearFields();
				} else if (objLsource == this.objGcloseJButton) {
					this.setVisible(false);
				}
			}
		}
	}

	final private JPanel doAddButtons() {

		final JPanel objLjPanel = new JPanel(new GridBagLayout());
		objLjPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											7,
																											7,
																											0,
																											0,
																											GridBagConstraints.HORIZONTAL,
																											1.0F,
																											0.0F);
		objLjPanel.add(this.objGactivateJButton, objLextendedGridBagConstraints);
		objLjPanel.add(this.objGdeactivateJButton, objLextendedGridBagConstraints);
		objLjPanel.add(this.objGclearJButton, objLextendedGridBagConstraints);
		objLjPanel.add(this.objGcloseJButton, objLextendedGridBagConstraints);
		return objLjPanel;
	}

	final private void doAddCriteria() {

		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.EAST,
																											0,
																											0,
																											3,
																											3,
																											3,
																											3);

		// Add left labels :
		this.objGcriteriaJPanel.add(this.objGcombinationJLabel, objLextendedGridBagConstraints);
		this.objGcriteriaJPanel.add(this.objGpatternJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(0, 3);
		this.objGcriteriaJPanel.add(this.objGsiteswapJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(0, 7);
		this.objGcriteriaJPanel.add(this.objGstyleJLabel, objLextendedGridBagConstraints);

		// Add semi-colon labels :
		objLextendedGridBagConstraints.setGridLocation(1, GridBagConstraints.RELATIVE);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		this.objGcriteriaJPanel.add(super.objGcombinationColonJLabel, objLextendedGridBagConstraints);
		this.objGcriteriaJPanel.add(this.objGpatternColonJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, 3);
		this.objGcriteriaJPanel.add(this.objGsynchroDashJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, 5);
		this.objGcriteriaJPanel.add(this.objGasynchroDashJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, 7);
		this.objGcriteriaJPanel.add(this.objGstyleColonJLabel, objLextendedGridBagConstraints);

		// Add siteswap labels :
		objLextendedGridBagConstraints.setGridLocation(2, 3);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		this.objGcriteriaJPanel.add(this.objGsynchroJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(2, 5);
		this.objGcriteriaJPanel.add(this.objGasynchroJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(3, 3);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		this.objGcriteriaJPanel.add(this.objGsynchroColonJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(3, 5);
		this.objGcriteriaJPanel.add(this.objGasynchroColonJLabel, objLextendedGridBagConstraints);

		// Add combo boxes and criteria labels :
		final JPanel objLcombinationJPanel = new JPanel();
		objLcombinationJPanel.setOpaque(true);
		objLcombinationJPanel.setLayout(new BoxLayout(objLcombinationJPanel, BoxLayout.LINE_AXIS));

		// Combination :
		objLcombinationJPanel.add(Box.createHorizontalGlue());
		objLcombinationJPanel.add(this.objGpermissiveJRadioButton);
		objLcombinationJPanel.add(Box.createHorizontalGlue());
		objLcombinationJPanel.add(this.objGrestrictiveJRadioButton);
		objLcombinationJPanel.add(Box.createHorizontalGlue());
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1.0F, 0.0F);
		objLextendedGridBagConstraints.setGridBounds(2, 0, 3, 1);
		this.objGcriteriaJPanel.add(objLcombinationJPanel, objLextendedGridBagConstraints);

		// Pattern :
		objLextendedGridBagConstraints.setGridLocation(2, 1);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		this.objGcriteriaJPanel.add(this.objGpatternsJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(2, 2);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		this.objGcriteriaJPanel.add(this.objGpatternTokensJLabel, objLextendedGridBagConstraints);

		// Siteswaps :
		objLextendedGridBagConstraints.setGridBounds(4, 3, 1, 1);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1.0F, 0.0F);
		this.objGcriteriaJPanel.add(this.objGsynchroSequencesJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(4, 4);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		this.objGcriteriaJPanel.add(this.objGsynchroTokensJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(4, 5);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1.0F, 0.0F);
		this.objGcriteriaJPanel.add(this.objGasynchroSequencesJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(4, 6);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		this.objGcriteriaJPanel.add(this.objGasynchroTokensJLabel, objLextendedGridBagConstraints);

		// Style :
		objLextendedGridBagConstraints.setGridBounds(2, 7, 3, 1);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1.0F, 0.0F);
		this.objGcriteriaJPanel.add(this.objGstylesJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(2, 8);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		this.objGcriteriaJPanel.add(this.objGstyleTokensJLabel, objLextendedGridBagConstraints);

		// Buttons :
		objLextendedGridBagConstraints.setGridBounds(5, 1, 1, 1);
		this.objGcriteriaJPanel.add(this.objGclearPatternJButton, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(5, 3);
		this.objGcriteriaJPanel.add(this.objGclearSynchroJButton, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(5, 5);
		this.objGcriteriaJPanel.add(this.objGclearAsynchroJButton, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(5, 7);
		this.objGcriteriaJPanel.add(this.objGclearStyleJButton, objLextendedGridBagConstraints);
	}

	final private void doClearCriteria() {

		this.objGpatternTokensJLabel.setText(Strings.strS_SPACE);
		this.objGsynchroTokensJLabel.setText(Strings.strS_SPACE);
		this.objGasynchroTokensJLabel.setText(Strings.strS_SPACE);
		this.objGstyleTokensJLabel.setText(Strings.strS_SPACE);
	}

	final private void doClearFields() {
		this.objGpatternsJComboBox.setEditableText(Strings.strS_EMPTY);
		this.objGsynchroSequencesJComboBox.setEditableText(Strings.strS_EMPTY);
		this.objGasynchroSequencesJComboBox.setEditableText(Strings.strS_EMPTY);
		this.objGstylesJComboBox.setEditableText(Strings.strS_EMPTY);
		this.setAble();
	}

	final public void doFilter() {
		this.doUpdateCriteria();
		this.setFiltersEnabled(true);
		this.objGcontrolJFrame.setFiltersControls();
		this.objGcontrolJFrame.setMenusEnabled();
		final int intLpreviousPatternIndex = this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		final int intLnextPatternIndex =
											this.objGcontrolJFrame.objGobjectsJList.getPatternIndex(this.objGcontrolJFrame.objGobjectsJList.getFilteredPatternRenewedIndex(intLpreviousPatternIndex));
		if (intLnextPatternIndex != intLpreviousPatternIndex) {
			this.objGcontrolJFrame.doStartJuggling(intLnextPatternIndex);
		}
	}

	@Override final public void doLoadImages() {

		super.doLoadImages();

		ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FILTER_BW, 2);
		ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FILTER, 2);
		Tools.setIcons(this.objGactivateJButton, icoL, icoLrollOver);

		icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DO_NOT_FILTER_BW, 2);
		icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DO_NOT_FILTER, 2);
		Tools.setIcons(this.objGdeactivateJButton, icoL, icoLrollOver);

		icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLEAR_FILTERS_BW, 2);
		icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLEAR_FILTERS, 2);
		Tools.setIcons(this.objGclearJButton, icoL, icoLrollOver);

		icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLOSE_FILTERS_BW, 2);
		icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLOSE_FILTERS, 2);
		Tools.setIcons(this.objGcloseJButton, icoL, icoLrollOver);

		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_FILTER, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
	}

	final public void doUnfilter() {
		this.doClearCriteria();
		this.setFiltersEnabled(false);
		this.objGcontrolJFrame.setFiltersControls();
		this.objGcontrolJFrame.setMenusEnabled();
		final int intLpreviousPatternIndex = this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		final int intLnextPatternIndex =
											this.objGcontrolJFrame.objGobjectsJList.getPatternIndex(this.objGcontrolJFrame.objGobjectsJList.getFilteredPatternRenewedIndex(intLpreviousPatternIndex));
		if (intLnextPatternIndex != intLpreviousPatternIndex) {
			this.objGcontrolJFrame.doStartJuggling(intLnextPatternIndex);
		}
	}

	final private void doUpdateCriteria() {

		String strL = this.objGpatternsJComboBox.getEditableText();
		this.objGpatternTokensJLabel.setText(Tools.isEmpty(strL) ? Strings.strS_SPACE : strL);
		strL = this.getSequencesString(true);
		this.objGsynchroSequencesJComboBox.setEditableText(strL);
		this.objGsynchroTokensJLabel.setText(Tools.isEmpty(strL) ? Strings.strS_SPACE : strL);
		strL = this.getSequencesString(false);
		this.objGasynchroSequencesJComboBox.setEditableText(strL);
		this.objGasynchroTokensJLabel.setText(Tools.isEmpty(strL) ? Strings.strS_SPACE : strL);
		strL = this.objGstylesJComboBox.getEditableText();
		this.objGstyleTokensJLabel.setText(Tools.isEmpty(strL) ? Strings.strS_SPACE : strL);
	}

	final public boolean isFiltering() {
		return this.bolGactivated;
	}

	final public boolean isPatternFiltered(Pattern objPpattern, boolean bolPedition) {
		return !this.bolGactivated || super.isPatternMatching(objPpattern, bolPedition, false);
	}

	@Override final public void setAble() {
		final boolean bolLable = super.isAble();
		this.objGactivateJButton.setEnabled(bolLable);
		this.objGclearJButton.setEnabled(bolLable);
	}

	@Override final public void setButtons() {
		this.objGactivateJButton.setToolTipText();
		this.objGclearJButton.setToolTipText();
	}

	final public void setFiltersEnabled(boolean bolPactivated) {

		this.bolGactivated = bolPactivated;
		this.objGdeactivateJButton.setEnabled(bolPactivated);
		this.objGcombinationJLabel.setEnabled(bolPactivated);
		this.objGcombinationColonJLabel.setEnabled(bolPactivated);
		this.objGpatternJLabel.setEnabled(bolPactivated);
		this.objGpatternColonJLabel.setEnabled(bolPactivated);
		this.objGpatternTokensJLabel.setEnabled(bolPactivated);
		this.objGsiteswapJLabel.setEnabled(bolPactivated);
		this.objGasynchroDashJLabel.setEnabled(bolPactivated);
		this.objGasynchroJLabel.setEnabled(bolPactivated);
		this.objGasynchroColonJLabel.setEnabled(bolPactivated);
		this.objGasynchroTokensJLabel.setEnabled(bolPactivated);
		this.objGsynchroDashJLabel.setEnabled(bolPactivated);
		this.objGsynchroJLabel.setEnabled(bolPactivated);
		this.objGsynchroColonJLabel.setEnabled(bolPactivated);
		this.objGsynchroTokensJLabel.setEnabled(bolPactivated);
		this.objGstyleJLabel.setEnabled(bolPactivated);
		this.objGstyleColonJLabel.setEnabled(bolPactivated);
		this.objGstyleTokensJLabel.setEnabled(bolPactivated);
		this.objGcontrolJFrame.objGfiltersJMenuItem.setState(bolPactivated);
		// this.objGcontrolJFrame.setFiltersValues();
	}

	@Override final public void setLabels() {

		this.setTitle(Strings.doConcat(this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_FILTERS), Strings.strS_ELLIPSIS));
		super.setLabels();

		this.objGactivateJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_APPLY));
		this.objGactivateJButton.setToolTipText();
		this.objGdeactivateJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_DEACTIVATE));
		this.objGdeactivateJButton.setToolTipText();
		this.objGclearJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_RESET));
		this.objGclearJButton.setToolTipText();
		this.objGcloseJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_CLOSE));
		this.objGcloseJButton.setToolTipText();

		this.objGpatternTokensJLabel.setToolTipText();
		this.objGsynchroTokensJLabel.setToolTipText();
		this.objGasynchroTokensJLabel.setToolTipText();
		this.objGstyleTokensJLabel.setToolTipText();
	}

	@Override final public void setVisible(boolean bolPvisible) {
		if (bolPvisible) {
			if (!this.bolGalreadyDisplayed) {
				this.bolGalreadyDisplayed = true;
				this.objGcontrolJFrame.setWindowBounds(this, Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE, this.getHeight());
				this.objGpatternsJComboBox.requestFocusInWindow();
			}
			this.objGactivateJButton.setEnabled(super.isAble());
			this.setFiltersEnabled(this.bolGactivated);
		}
		this.objGcontrolJFrame.setFiltersControls();
		super.setVisible(bolPvisible);
	}
}
