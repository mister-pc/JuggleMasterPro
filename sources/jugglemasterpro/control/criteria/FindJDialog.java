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
import jugglemasterpro.control.window.ChoiceJDialog;
import jugglemasterpro.control.window.PopUpJDialog;
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
final public class FindJDialog extends CriteriaJDialog {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public FindJDialog(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame);

		this.lngGlastActionTimestamp = Constants.bytS_UNCLASS_NO_VALUE;
		this.objGnextJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_FIND_NEXT, this);
		this.objGpreviousJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_FIND_PREVIOUS, this);
		this.objGcancelJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_CANCEL_SEARCH, this);
		this.objGcloseJButton = new CriteriaJButton(this.objGcontrolJFrame, Language.intS_TOOLTIP_CLOSE_SEARCH, this);

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
		this.add(super.objGcriteriaJPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		this.add(objLbuttonsJPanel, objLextendedGridBagConstraints);

		this.setAble();
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("FindJDialog.actionPerformed()");
		if (super.isActionPerformed(objPactionEvent)) {
			this.setAble();
			this.objGpreviousJButton.setToolTipText();
			this.objGnextJButton.setToolTipText();
		} else {
			final long lngLtimeStamp = objPactionEvent.getWhen();
			if (lngLtimeStamp > this.lngGlastActionTimestamp) {
				this.lngGlastActionTimestamp = lngLtimeStamp;
				final Object objLsource = objPactionEvent.getSource();
				if (objLsource == this.objGcancelJButton) {
					super.objGpatternsJComboBox.setSelectedIndex(Constants.bytS_UNCLASS_NO_VALUE);
					super.objGasynchroSequencesJComboBox.setSelectedIndex(Constants.bytS_UNCLASS_NO_VALUE);
					super.objGsynchroSequencesJComboBox.setSelectedIndex(Constants.bytS_UNCLASS_NO_VALUE);
					super.objGstylesJComboBox.setSelectedIndex(Constants.bytS_UNCLASS_NO_VALUE);
					this.setVisible(false);
					return;
				}

				// Update combobox values :
				this.objGpatternsJComboBox.doAddCurrentItem();
				if (super.getSequenceAble(true) == Boolean.TRUE) {
					super.objGsynchroSequencesJComboBox.doAddCurrentItem();
				}
				if (super.getSequenceAble(false) == Boolean.TRUE) {
					super.objGasynchroSequencesJComboBox.doAddCurrentItem();
				}
				super.objGstylesJComboBox.doAddCurrentItem();

				Tools.out("Informations de FindJDialog :");
				Tools.out(this.getInfo());
				if (objLsource == this.objGnextJButton || objLsource == this.objGpreviousJButton) {
					this.doFind(objLsource == this.objGnextJButton);
				}

				if (objLsource == this.objGcloseJButton) {
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
																											5,
																											5,
																											0,
																											0,
																											GridBagConstraints.HORIZONTAL,
																											1.0F,
																											0.0F);
		objLjPanel.add(this.objGnextJButton, objLextendedGridBagConstraints);
		objLjPanel.add(this.objGpreviousJButton, objLextendedGridBagConstraints);
		objLjPanel.add(this.objGcloseJButton, objLextendedGridBagConstraints);
		objLjPanel.add(this.objGcancelJButton, objLextendedGridBagConstraints);
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

		// Left labels :
		super.objGcriteriaJPanel.add(super.objGcombinationJLabel, objLextendedGridBagConstraints);
		super.objGcriteriaJPanel.add(super.objGpatternJLabel, objLextendedGridBagConstraints);
		super.objGcriteriaJPanel.add(super.objGsiteswapJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(0, 4);
		super.objGcriteriaJPanel.add(super.objGstyleJLabel, objLextendedGridBagConstraints);

		// Colons :
		objLextendedGridBagConstraints.setGridLocation(1, GridBagConstraints.RELATIVE);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		super.objGcriteriaJPanel.add(this.objGcombinationColonJLabel, objLextendedGridBagConstraints);
		super.objGcriteriaJPanel.add(this.objGpatternColonJLabel, objLextendedGridBagConstraints);
		super.objGcriteriaJPanel.add(this.objGsynchroDashJLabel, objLextendedGridBagConstraints);
		super.objGcriteriaJPanel.add(this.objGasynchroDashJLabel, objLextendedGridBagConstraints);
		super.objGcriteriaJPanel.add(this.objGstyleColonJLabel, objLextendedGridBagConstraints);

		// Siteswaps :
		objLextendedGridBagConstraints.setGridLocation(2, 2);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		super.objGcriteriaJPanel.add(super.objGsynchroJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(2, 3);
		super.objGcriteriaJPanel.add(super.objGasynchroJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLextendedGridBagConstraints.setGridLocation(3, 2);
		super.objGcriteriaJPanel.add(this.objGsynchroColonJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(3, 3);
		super.objGcriteriaJPanel.add(this.objGasynchroColonJLabel, objLextendedGridBagConstraints);

		// Combination :
		final JPanel objLcombinationJPanel = new JPanel();
		objLcombinationJPanel.setOpaque(true);
		objLcombinationJPanel.setLayout(new BoxLayout(objLcombinationJPanel, BoxLayout.LINE_AXIS));
		objLcombinationJPanel.add(Box.createHorizontalGlue());
		objLcombinationJPanel.add(super.objGpermissiveJRadioButton);
		objLcombinationJPanel.add(Box.createHorizontalGlue());
		objLcombinationJPanel.add(super.objGrestrictiveJRadioButton);
		objLcombinationJPanel.add(Box.createHorizontalGlue());
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1.0F, 0.0F);
		objLextendedGridBagConstraints.setGridBounds(2, 0, 3, 1);
		super.objGcriteriaJPanel.add(objLcombinationJPanel, objLextendedGridBagConstraints);

		// Combo boxes :
		objLextendedGridBagConstraints.setGridLocation(2, 1);
		super.objGcriteriaJPanel.add(super.objGpatternsJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(4, 2, 1, 1);
		super.objGcriteriaJPanel.add(super.objGsynchroSequencesJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(4, 3);
		super.objGcriteriaJPanel.add(super.objGasynchroSequencesJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(2, 4, 3, 1);
		super.objGcriteriaJPanel.add(super.objGstylesJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(5, 1, 1, 1);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		super.objGcriteriaJPanel.add(super.objGclearPatternJButton, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(5, 2);
		super.objGcriteriaJPanel.add(super.objGclearSynchroJButton, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(5, 3);
		super.objGcriteriaJPanel.add(super.objGclearAsynchroJButton, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(5, 4);
		super.objGcriteriaJPanel.add(super.objGclearStyleJButton, objLextendedGridBagConstraints);
	}

	final public void doFind(boolean bolPdirection) {

		super.objGsynchroSequencesJComboBox.setEditableText(this.getSequencesString(true));
		super.objGasynchroSequencesJComboBox.setEditableText(this.getSequencesString(false));
		final int intLinitialPatternIndex = super.objGcontrolJFrame.objGobjectsJList.getSelectedIndex();
		final int intLfilteredObjectsNumber = super.objGcontrolJFrame.objGobjectsJList.intGfilteredObjectIndexA.length;
		final byte bytLdirection = Tools.getSignedBoolean(bolPdirection);
		for (int intLfilteredObjectIndex = intLinitialPatternIndex + bytLdirection;; intLfilteredObjectIndex += bytLdirection) {
			if (intLfilteredObjectIndex < 0 || intLfilteredObjectIndex == intLfilteredObjectsNumber) {
				final ChoiceJDialog objLchoiceJDialog =
														new ChoiceJDialog(	this.objGcontrolJFrame,
																			this,
																			Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_FIND),
																								Strings.strS_ELLIPSIS),
																			Constants.intS_FILE_ICON_QUESTION,
																			this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_YES),
																			intLfilteredObjectIndex < 0
																										? Language.intS_TOOLTIP_CONTINUE_SEARCHING_FROM_THE_LIST_END
																										: Language.intS_TOOLTIP_CONTINUE_SEARCHING_FROM_THE_TOP_OF_THE_LIST,
																			Constants.intS_FILE_ICON_OK_BW,
																			Constants.intS_FILE_ICON_OK,
																			this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_NO),
																			intLfilteredObjectIndex < 0
																										? Language.intS_TOOLTIP_ABORT_SEARCHING_FROM_THE_LIST_END
																										: Language.intS_TOOLTIP_ABORT_SEARCHING_FROM_THE_TOP_OF_THE_LIST,
																			Constants.intS_FILE_ICON_CANCEL_BW,
																			Constants.intS_FILE_ICON_CANCEL,
																			this.objGcontrolJFrame.getLanguageString(intLfilteredObjectIndex < 0
																																				? Language.intS_MESSAGE_TOP_OF_THE_LIST_REACHED
																																				: Language.intS_MESSAGE_END_OF_THE_LIST_REACHED),
																			this.objGcontrolJFrame.getLanguageString(intLfilteredObjectIndex < 0
																																				? Language.intS_QUESTION_CONTINUE_FROM_THE_END_OF_THE_LIST
																																				: Language.intS_QUESTION_CONTINUE_FROM_THE_TOP_OF_THE_LIST));
				objLchoiceJDialog.setVisible(true);
				final boolean bolLconfirm = (objLchoiceJDialog.bytGchoice == ChoiceJDialog.bytS_FIRST_CHOICE);
				objLchoiceJDialog.dispose();
				if (!bolLconfirm) {
					return;
				}
				intLfilteredObjectIndex = intLfilteredObjectIndex < 0 ? intLfilteredObjectsNumber - 1 : 0;
			}

			final int intLobjectIndex = this.objGcontrolJFrame.objGobjectsJList.intGfilteredObjectIndexA[intLfilteredObjectIndex];
			if (super.objGcontrolJFrame.getPatternsManager().isPattern(intLobjectIndex)
				&& super.isPatternMatching(	super.objGcontrolJFrame.getPatternsManager().objGobjectA[intLobjectIndex],
											super.objGcontrolJFrame.isControlSelected(	intLobjectIndex,
																						Constants.bytS_BOOLEAN_LOCAL_EDITION,
																						Constants.bytS_UNCLASS_CURRENT),
											true)) {
				super.objGcontrolJFrame.objGobjectsJList.selectIndex(intLfilteredObjectIndex);
				super.objGcontrolJFrame.doStartJuggling(intLobjectIndex);
				return;
			}

			if (intLinitialPatternIndex == intLfilteredObjectIndex) {
				break;
			}
		}

		new PopUpJDialog(	super.objGcontrolJFrame,
							Constants.bytS_UNCLASS_NO_VALUE,
							Constants.intS_FILE_ICON_ALERT,
							this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_FIND),
							super.objGcontrolJFrame.getLanguageString(Language.intS_MESSAGE_NO_PATTERN_FOUND),
							null,
							false);
	}

	@Override final public void doLoadImages() {

		super.doLoadImages();
		ImageIcon icoL = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FIND_NEXT, 2);
		ImageIcon icoLbW = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FIND_NEXT_BW, 2);
		Tools.setIcons(this.objGnextJButton, icoLbW, icoL);

		icoL = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FIND_PREVIOUS, 2);
		icoLbW = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FIND_PREVIOUS_BW, 2);
		Tools.setIcons(this.objGpreviousJButton, icoLbW, icoL);

		icoL = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLOSE_FIND, 2);
		icoLbW = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLOSE_FIND_BW, 2);
		Tools.setIcons(this.objGcloseJButton, icoLbW, icoL);

		icoL = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CANCEL_FIND, 2);
		icoLbW = super.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CANCEL_FIND_BW, 2);
		Tools.setIcons(this.objGcancelJButton, icoLbW, icoL);

		final Image imgL = super.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_FIND, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
	}

	@Override final public void setAble() {
		final boolean bolLfindable = super.isAble();
		this.objGpreviousJButton.setEnabled(bolLfindable);
		this.objGnextJButton.setEnabled(bolLfindable);
	}

	@Override final public void setButtons() {
		this.objGpreviousJButton.setToolTipText();
		this.objGnextJButton.setToolTipText();
	}

	@Override final public void setLabels() {

		this.setTitle(this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_FIND));

		super.setLabels();
		this.objGnextJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_FIND_NEXT));
		this.objGnextJButton.setToolTipText();
		this.objGpreviousJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_FIND_PREVIOUS));
		this.objGpreviousJButton.setToolTipText();
		this.objGcloseJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_CLOSE));
		this.objGcloseJButton.setToolTipText();
		this.objGcancelJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_CANCEL));
		this.objGcancelJButton.setToolTipText();
	}

	@Override final public void setVisible(boolean bolPvisible) {

		if (bolPvisible) {
			if (!this.bolGalreadyDisplayed) {
				this.bolGalreadyDisplayed = true;
				this.objGcontrolJFrame.setWindowBounds(this, Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE, this.getHeight());
				this.objGpatternsJComboBox.requestFocusInWindow();
			}
			this.setAble();
			this.objGpreviousJButton.setToolTipText();
			this.objGnextJButton.setToolTipText();
		}
		super.setVisible(bolPvisible);
	}

	private long					lngGlastActionTimestamp;

	private final CriteriaJButton	objGcancelJButton;

	private final CriteriaJButton	objGcloseJButton;

	private final CriteriaJButton	objGnextJButton;

	private final CriteriaJButton	objGpreviousJButton;

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
