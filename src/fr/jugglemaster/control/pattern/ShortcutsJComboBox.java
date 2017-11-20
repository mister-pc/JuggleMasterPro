/*
 * @(#)ShortcutsJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.pattern;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ActionMap;
import javax.swing.InputMap;
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
final public class ShortcutsJComboBox extends JComboBox<String> implements PopupMenuListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public int[]				intGfilteredShortcutIndexA;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ShortcutsJComboBox(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setRenderer(Constants.objS_GRAPHICS_DEFAULT_RENDERER);
		this.setMaximumRowCount(Constants.intS_GRAPHICS_MAXIMUM_COMBO_ROWS_NUMBER);
		this.addPopupMenuListener(this);
		this.addActionListener(this);
		final InputMap objLinputMap = this.getInputMap();
		final ActionMap objLactionMap = this.getActionMap();
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		objLactionMap.put(KeyEvent.VK_ENTER, new KeysAction(objPcontrolJFrame, KeysAction.bytS_SHORTCUTS_J_COMBO_BOX));
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), KeyEvent.VK_SPACE);
		objLactionMap.put(KeyEvent.VK_SPACE, new KeysAction(objPcontrolJFrame, KeysAction.bytS_SHORTCUTS_J_COMBO_BOX, KeyEvent.VK_SPACE));
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK), KeyEvent.VK_COPY);
		objLactionMap.put(KeyEvent.VK_COPY, new KeysAction(objPcontrolJFrame, KeysAction.bytS_SHORTCUTS_J_COMBO_BOX, KeyEvent.VK_COPY));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ShortcutsJComboBox.actionPerformed()");
		this.setEnabled(false);
		PatternActions.doApplyShortcut(this.objGcontrolJFrame, this.getSelectedIndex());
		this.setEnabled(true);
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfilteredObjectIndex
	 * @return
	 */
	final public int getFilteredShortcutIndex(int intPfilteredObjectIndex) {

		if (this.intGfilteredShortcutIndexA != null) {

			int intLshortcutIndex = Arrays.binarySearch(this.intGfilteredShortcutIndexA, intPfilteredObjectIndex);
			if (intLshortcutIndex < 0) {
				intLshortcutIndex = -2 - intLshortcutIndex;
			}
			return (intLshortcutIndex >= 0 && intLshortcutIndex < this.intGfilteredShortcutIndexA.length) ? intLshortcutIndex
																											: Constants.bytS_UNCLASS_NO_VALUE;
		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPshortcutIndex
	 * @return
	 */
	final public boolean isFilteredShortcut(int intPshortcutIndex) {
		if (this.intGfilteredShortcutIndexA != null && this.intGfilteredShortcutIndexA.length > 0
			&& intPshortcutIndex != Constants.bytS_UNCLASS_NO_VALUE) {
			return (Arrays.binarySearch(this.intGfilteredShortcutIndexA, intPshortcutIndex) >= 0);
		}
		return false;
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

		if (intPindex >= 0 && intPindex < this.getItemCount()) {
			this.removeActionListener(this);
			this.setSelectedIndex(intPindex);

			this.addActionListener(this);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfilteredObjectIndex
	 */
	final public void selectShortcut(int intPfilteredObjectIndex) {
		this.selectIndex(this.getFilteredShortcutIndex(intPfilteredObjectIndex));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfilteredShortcutAL
	 * @param objPfilteredShortcutStringIndexAL
	 * @param intPfilteredShortcutsNumber
	 */
	final public void setList(	ArrayList<String> strPfilteredShortcutAL,
								ArrayList<Integer> objPfilteredShortcutStringIndexAL,
								int intPfilteredShortcutsNumber) {

		this.intGfilteredShortcutIndexA = intPfilteredShortcutsNumber > 0 ? new int[intPfilteredShortcutsNumber] : null;
		for (int intLshortcutIndex = 0; intLshortcutIndex < intPfilteredShortcutsNumber; ++intLshortcutIndex) {
			this.intGfilteredShortcutIndexA[intLshortcutIndex] = objPfilteredShortcutStringIndexAL.get(intLshortcutIndex);
		}
		this.removeActionListener(this);
		this.removeAllItems();
		if (intPfilteredShortcutsNumber > 0) {
			for (final String strLfilteredShortcut : strPfilteredShortcutAL) {

				this.addItem(strLfilteredShortcut);
			}
		}
		this.addActionListener(this);

	}

	final public void setToolTipText() {
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS)
							&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																												? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_SHORTCUTS)
																												: null);
	}
}

/*
 * @(#)ShortcutsJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
