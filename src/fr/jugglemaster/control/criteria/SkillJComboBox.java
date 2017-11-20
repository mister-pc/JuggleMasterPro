/*
 * @(#)SkillJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.criteria;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.color.ColorActions;
import fr.jugglemaster.control.util.KeysAction;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

// import static java.lang.Math.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class SkillJComboBox extends JComboBox<String> implements PopupMenuListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean		bolGglobal;

	private final byte			bytGbyteFilterType;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPfilterType
	 * @param bolPglobal
	 */
	public SkillJComboBox(ControlJFrame objPcontrolJFrame, byte bytPbyteFilterType, boolean bolPglobal) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGbyteFilterType = bytPbyteFilterType;
		this.bolGglobal = bolPglobal;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setRenderer(Constants.objS_GRAPHICS_DEFAULT_RENDERER);
		this.setList();
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), KeyEvent.VK_SPACE);
		this.getActionMap()
			.put(	KeyEvent.VK_ENTER,
					new KeysAction(	objPcontrolJFrame,
									bolPglobal ? bytPbyteFilterType == Constants.bytS_BYTE_GLOBAL_LOW_SKILL ? KeysAction.bytS_LOW_SKILL_J_COMBO_BOX
																											: KeysAction.bytS_HIGH_SKILL_J_COMBO_BOX
												: KeysAction.bytS_SKILL_J_COMBO_BOX));
		this.getActionMap()
			.put(	KeyEvent.VK_SPACE,
					new KeysAction(	objPcontrolJFrame,
									bolPglobal ? bytPbyteFilterType == Constants.bytS_BYTE_GLOBAL_LOW_SKILL ? KeysAction.bytS_LOW_SKILL_J_COMBO_BOX
																											: KeysAction.bytS_HIGH_SKILL_J_COMBO_BOX
												: KeysAction.bytS_SKILL_J_COMBO_BOX,
									KeyEvent.VK_SPACE));
		this.addPopupMenuListener(this);
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("SkillJComboBox.actionPerformed()");
		if (this.bolGglobal) {
			CriteriaActions.doFilterGlobalSkill(this.objGcontrolJFrame, this.bytGbyteFilterType);
			CriteriaActions.doCheckFilters(this.objGcontrolJFrame);
		} else {
			final byte bytLskill = (byte) this.getSelectedIndex();
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_SKILL, bytLskill);
			this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
		}
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	@Override final public void popupMenuCanceled(PopupMenuEvent objPpopupMenuEvent) {}

	@Override final public void popupMenuWillBecomeInvisible(PopupMenuEvent objPpopupMenuEvent) {}

	@Override final public void popupMenuWillBecomeVisible(PopupMenuEvent objPpopupMenuEvent) {
		ColorActions.doHideColorsChoosers(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPindex
	 */
	final public void selectIndex(int intPindex) {
		this.removeActionListener(this);
		this.setSelectedIndex(intPindex);
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 */
	final public void selectString(String strP) {
		this.removeActionListener(this);
		this.setSelectedItem(strP);
		this.addActionListener(this);
	}

	final public void setItems() {
		final int intLselectedIndex = this.getSelectedIndex();
		this.removeActionListener(this);
		this.setList();
		this.setSelectedIndex(intLselectedIndex);
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setList() {
		this.removeAllItems();
		for (final int intLskillLanguageIndex : Language.intS_LANGUAGE_SKILL_INDEX_A) {
			this.addItem(this.objGcontrolJFrame.getLanguageString(intLskillLanguageIndex));
		}
		this.setMaximumRowCount(Constants.bytS_BYTE_LOCAL_SKILLS_NUMBER);
	}

	final public void setToolTipText() {

		final byte bytLpatternsManagerType = this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType;
		final boolean bolLedition =
									bytLpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
										|| bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN;
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS) && bolLedition
																																? this.objGcontrolJFrame.getLanguageString(this.bolGglobal
																																															? this.bytGbyteFilterType == Constants.bytS_BYTE_GLOBAL_LOW_SKILL
																																																																? Language.intS_TOOLTIP_MIN_SKILL
																																																																: Language.intS_TOOLTIP_MAX_SKILL
																																															: Language.intS_TOOLTIP_SKILL)
																																: null);
	}
}

/*
 * @(#)SkillJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
