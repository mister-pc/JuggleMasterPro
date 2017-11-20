/*
 * @(#)MarkJComboBox.java 4.3.0
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

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class MarkJComboBox extends JComboBox<Byte> implements PopupMenuListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean		bolGglobal;

	private final byte			bytGfilterType;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPfilterType
	 * @param bolPglobal
	 */
	public MarkJComboBox(ControlJFrame objPcontrolJFrame, byte bytPfilterType, boolean bolPglobal) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGfilterType = bytPfilterType;
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
									bolPglobal ? bytPfilterType == Constants.bytS_BYTE_GLOBAL_LOW_MARK ? KeysAction.bytS_LOW_MARK_J_COMBO_BOX
																										: KeysAction.bytS_HIGH_MARK_J_COMBO_BOX
												: KeysAction.bytS_MARK_J_COMBO_BOX));
		this.getActionMap()
			.put(	KeyEvent.VK_SPACE,
					new KeysAction(	objPcontrolJFrame,
									bolPglobal ? bytPfilterType == Constants.bytS_BYTE_GLOBAL_LOW_MARK ? KeysAction.bytS_LOW_MARK_J_COMBO_BOX
																										: KeysAction.bytS_HIGH_MARK_J_COMBO_BOX
												: KeysAction.bytS_MARK_J_COMBO_BOX,
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
		Tools.debug("MarkJComboBox.actionPerformed()");
		if (this.bolGglobal) {
			CriteriaActions.doFilterGlobalMark(this.objGcontrolJFrame, this.bytGfilterType);
			CriteriaActions.doCheckFilters(this.objGcontrolJFrame);
		} else {
			final byte bytLmark = (byte) this.getSelectedIndex();
			this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_MARK, bytLmark);
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
		final byte bytLmarksNumber = Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE - Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE + 1;
		for (byte bytLvisibleMarkValue = 1; bytLvisibleMarkValue <= bytLmarksNumber; ++bytLvisibleMarkValue) {
			this.addItem(bytLvisibleMarkValue);
		}
		this.setMaximumRowCount(bytLmarksNumber);
	}

	final public void setToolTipText() {

		final byte bytLpatternsManagerType = this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType;
		final boolean bolLedition =
									bytLpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
										|| bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN;
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS) && bolLedition
																																? this.objGcontrolJFrame.getLanguageString(this.bolGglobal
																																															? this.bytGfilterType == Constants.bytS_BYTE_GLOBAL_LOW_MARK
																																																														? Language.intS_TOOLTIP_MIN_MARK
																																																														: Language.intS_TOOLTIP_MAX_MARK
																																															: Language.intS_TOOLTIP_MARK)
																																: null);
	}
}

/*
 * @(#)MarkJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
