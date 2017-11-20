/*
 * @(#)StyleJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.style;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.color.ColorActions;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
import fr.jugglemaster.control.util.KeysAction;
import fr.jugglemaster.pattern.PatternsManager;
import fr.jugglemaster.pattern.Style;
import fr.jugglemaster.user.Language;
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
final public class StyleJComboBox extends JComboBox<Style> implements Comparator<String>, KeyListener, PopupMenuListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean				bolGascending;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public StyleJComboBox(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGascending = true;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_STYLES));
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setMaximumRowCount(Constants.intS_GRAPHICS_MAXIMUM_COMBO_ROWS_NUMBER);
		this.addPopupMenuListener(this);
		this.addActionListener(this);
		final InputMap objLinputMap = this.getInputMap();
		final ActionMap objLactionMap = this.getActionMap();
		objLinputMap.clear();
		objLactionMap.clear();
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		objLactionMap.put(KeyEvent.VK_ENTER, new KeysAction(objPcontrolJFrame, KeysAction.bytS_STYLE_J_COMBO_BOX, KeyEvent.VK_ENTER));
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), KeyEvent.VK_SPACE);
		objLactionMap.put(KeyEvent.VK_SPACE, new KeysAction(objPcontrolJFrame, KeysAction.bytS_STYLE_J_COMBO_BOX, KeyEvent.VK_SPACE));
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK), KeyEvent.VK_COPY);
		objLactionMap.put(KeyEvent.VK_COPY, new KeysAction(objPcontrolJFrame, KeysAction.bytS_STYLE_J_COMBO_BOX, KeyEvent.VK_COPY));
		this.addKeyListener(this);
		this.setRenderer(new StylesJComboBoxRenderer(this.objGcontrolJFrame));
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, false, true, this.getTransferHandler()));

		// setDragEnabled(false);
		// setTransferHandler(JuggleTransferHandler(false, true));
		// } else {
		// setEnabled(false);
		// }
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("StyleJComboBox.actionPerformed()");
		this.objGcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_STYLE, true);
		this.objGcontrolJFrame.saveControlString(Constants.bytS_STRING_LOCAL_STYLE, ((Style) this.getSelectedItem()).strGstyle);

		Tools.debug("StyleJComboBox.actionPerformed(): ControlJFrame.doRestartJuggling()");
		this.objGcontrolJFrame.doRestartJuggling();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfirstParameter
	 * @param strPsecondParameter
	 * @return
	 */
	@Override public final int compare(String strPfirstParameter, String strPsecondParameter) {

		final String[] strLparameterA = new String[2];
		strLparameterA[0] = strPfirstParameter;
		strLparameterA[1] = strPsecondParameter;

		final StringBuilder[] objLparameterStringBuilderA = new StringBuilder[2];
		for (byte bytLparameterIndex = 0; bytLparameterIndex < 2; ++bytLparameterIndex) {
			final char[] chrLparameterStringA = strLparameterA[bytLparameterIndex].toCharArray();
			objLparameterStringBuilderA[bytLparameterIndex] = new StringBuilder(chrLparameterStringA.length);

			for (char chrLindexed : chrLparameterStringA) {
				chrLindexed = Strings.getUpperCaseChar(chrLindexed);
				if ((chrLindexed >= 'A' && chrLindexed <= 'Z') || (chrLindexed >= '0' && chrLindexed <= '9')) {
					objLparameterStringBuilderA[bytLparameterIndex].append(chrLindexed);
				}
			}
		}
		return Tools.getSignedBoolean(this.bolGascending)
				* objLparameterStringBuilderA[0].toString().compareTo(objLparameterStringBuilderA[1].toString());
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public boolean isSortOrderAscending() {
		return this.bolGascending;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyPressed(KeyEvent objPkeyEvent) {

		final int intLstylesNumber = this.objGcontrolJFrame.getPatternsManager().strGstyleAL.size();
		final int intLcurrentIndex = this.getSelectedIndex();
		final int intLlastIndex = intLstylesNumber - 1;
		final int intLkeyCode = objPkeyEvent.getKeyCode();
		final boolean bolLcontrolDown = objPkeyEvent.isControlDown();
		switch (intLkeyCode) {
			case Constants.intS_SHORTCUT_PLAY_PAUSE:
				break;

			case KeyEvent.VK_HOME:
			case KeyEvent.VK_END:
				final boolean bolLbegin = intLkeyCode == KeyEvent.VK_HOME;
				if (!bolLbegin && intLcurrentIndex != intLlastIndex || bolLbegin && intLcurrentIndex != 0) {
					this.selectIndex(bolLbegin ? 0 : intLlastIndex);
					this.actionPerformed(null);
				}
				objPkeyEvent.consume();
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_PAGE_DOWN:
				final boolean bolLpage = intLkeyCode == KeyEvent.VK_PAGE_UP || intLkeyCode == KeyEvent.VK_PAGE_DOWN;
				int intLnewIndex = intLcurrentIndex;
				if (bolLpage && bolLcontrolDown) {
					char chrLcurrentUpperCaseChar = Strings.chrS_UNCLASS_NULL_CHAR;
					try {
						chrLcurrentUpperCaseChar = Strings.getUpperCaseChar(((this.getItemAt(intLcurrentIndex))).strGstyle.charAt(0));
					} catch (final Throwable objPthrowable) {
						chrLcurrentUpperCaseChar = Strings.chrS_UNCLASS_NULL_CHAR;
					}

					final byte bytLdelta = Tools.getSignedBoolean(intLkeyCode == KeyEvent.VK_PAGE_DOWN);
					for (intLnewIndex = intLcurrentIndex + bytLdelta;; intLnewIndex += bytLdelta) {
						if (intLnewIndex == intLstylesNumber) {
							intLnewIndex = 0;
						} else if (intLnewIndex < 0) {
							intLnewIndex = intLlastIndex;
						}
						try {
							if (intLnewIndex == intLcurrentIndex
								|| Strings.getUpperCaseChar(((this.getItemAt(intLnewIndex))).strGstyle.charAt(0)) != chrLcurrentUpperCaseChar) {
								break;
							}
						} catch (final Throwable objPthrowable) {
							if (chrLcurrentUpperCaseChar != Strings.chrS_UNCLASS_NULL_CHAR) {
								break;
							}
						}
					}

				} else {
					intLnewIndex =
									intLcurrentIndex
										+ Tools.getSignedBoolean(intLkeyCode == KeyEvent.VK_DOWN || intLkeyCode == KeyEvent.VK_PAGE_DOWN)
										* (bolLpage ? Constants.intS_GRAPHICS_MAXIMUM_COMBO_ROWS_NUMBER : 1);

					if (intLnewIndex > intLlastIndex) {
						intLnewIndex = bolLpage && intLcurrentIndex != intLlastIndex ? intLlastIndex : 0;
					} else if (intLnewIndex < 0) {
						intLnewIndex = bolLpage && intLcurrentIndex != 0 ? 0 : intLlastIndex;
					}
				}
				if (intLnewIndex != intLcurrentIndex) {
					this.selectIndex(intLnewIndex);
					this.actionPerformed(null);
				}
				objPkeyEvent.consume();
				break;

			default:
				if (!bolLcontrolDown) {
					final char chrLupperCaseTypedChar = Strings.getUpperCaseChar(objPkeyEvent.getKeyChar());

					if (Character.isLetterOrDigit(chrLupperCaseTypedChar)) {
						for (int intLnextIndex = intLcurrentIndex + 1; intLnextIndex != intLcurrentIndex; ++intLnextIndex) {
							if (intLnextIndex > intLlastIndex) {
								intLnextIndex = 0;
							}
							if (Strings.getUpperCaseChar(((this.getItemAt(intLnextIndex))).strGstyle.charAt(0)) == chrLupperCaseTypedChar) {
								this.selectIndex(intLnextIndex);
								this.actionPerformed(null);
								break;
							}
						}
						objPkeyEvent.consume();
					}
				}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyReleased(KeyEvent objPkeyEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyTyped(KeyEvent objPkeyEvent) {}

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
	final public void selectItem(String strP) {
		final PatternsManager objLpatternsManager = this.objGcontrolJFrame.getPatternsManager();
		int intLstyleIndex = 0;
		for (final String strLstyle : objLpatternsManager.strGstyleAL) {
			if (strP.equals(strLstyle)) {
				this.selectIndex(intLstyleIndex);
				break;
			}
			++intLstyleIndex;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setList() {
		final PatternsManager objLpatternsManager = this.objGcontrolJFrame.getPatternsManager();
		if (objLpatternsManager.strGstyleAL != null) {
			// this.setVisible(false);
			// objGstyleBlankJComboBox.setVisible(true);
			this.removeActionListener(this);
			this.removeAllItems();
			for (final String strLstyle : objLpatternsManager.strGstyleAL) {
				this.addItem(objLpatternsManager.getStyle(strLstyle));
			}

			// objGstyleBlankJComboBox.setVisible(!bolLisStyleListVisible);
			// this.setVisible(bolLisStyleListVisible);
			this.addActionListener(this);
		}
		this.objGcontrolJFrame.objGsortStylesJButton.setImage(this.isSortOrderAscending());
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPascending
	 */
	final public void setSortOrderAscending(boolean bolPascending) {
		this.bolGascending = bolPascending;
	}

	final public void setToolTipText() {
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS) && this.isEnabled()
																																		? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_STYLES)
																																		: null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void sort() {
		Collections.sort(this.objGcontrolJFrame.getPatternsManager().strGstyleAL, this);
		this.bolGascending = !this.bolGascending;
	}
}

/*
 * @(#)StyleJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
