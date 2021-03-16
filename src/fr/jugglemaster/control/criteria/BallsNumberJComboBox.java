/*
 * @(#)BallsNumberJComboBox.java 4.3.0
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
final public class BallsNumberJComboBox extends JComboBox<Object> implements PopupMenuListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final byte			bytGbyteGlobalBallsNumberFilter;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bytPfilterType
	 */
	public BallsNumberJComboBox(ControlJFrame objPcontrolJFrame, byte bytPfilterType) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGbyteGlobalBallsNumberFilter = bytPfilterType;
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setRenderer(Constants.objS_GRAPHICS_DEFAULT_RENDERER);
		for (byte bytLindex = 1; bytLindex < 9; ++bytLindex) {
			this.addItem(Byte.valueOf(bytLindex).toString());
		}
		this.addItem("9 +");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), KeyEvent.VK_SPACE);
		this.getActionMap()
			.put(	KeyEvent.VK_ENTER,
					new KeysAction(	objPcontrolJFrame,
									bytPfilterType == Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER ? KeysAction.bytS_LOW_BALLS_NUMBER_J_COMBO_BOX
																									: KeysAction.bytS_HIGH_BALLS_NUMBER_J_COMBO_BOX));
		this.getActionMap()
			.put(	KeyEvent.VK_SPACE,
					new KeysAction(	objPcontrolJFrame,
									bytPfilterType == Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER ? KeysAction.bytS_LOW_BALLS_NUMBER_J_COMBO_BOX
																									: KeysAction.bytS_HIGH_BALLS_NUMBER_J_COMBO_BOX,
									KeyEvent.VK_SPACE));
		this.addPopupMenuListener(this);
		this.addActionListener(this);
		this.setMaximumRowCount(9);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("BallsNumberJComboBox.actionPerformed()");
		ColorActions.doHideColorsChoosers(this.objGcontrolJFrame);
		CriteriaActions.doFilterBallsNumber(this.objGcontrolJFrame, this.bytGbyteGlobalBallsNumberFilter);
		CriteriaActions.doCheckFilters(this.objGcontrolJFrame);
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

	final public void setToolTipText() {
		final byte bytLpatternsManagerType = this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType;
		final boolean bolLedition =
									bytLpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
										|| bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN;
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS) && bolLedition
																																? this.objGcontrolJFrame.getLanguageString(this.bytGbyteGlobalBallsNumberFilter == Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER
																																																																? Language.intS_TOOLTIP_BALLS_MIN_NUMBER
																																																																: Language.intS_TOOLTIP_BALLS_MAX_NUMBER)
																																: null);
	}
}

/*
 * @(#)BallsNumberJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
