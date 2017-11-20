package fr.jugglemaster.control.util;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.pattern.Style;
import fr.jugglemaster.util.Constants;

final public class KeysAction extends AbstractAction {

	final public static byte	bytS_COLORS_J_TEXT_PANE				= 4;

	final public static byte	bytS_HIGH_BALLS_NUMBER_J_COMBO_BOX	= 6;

	final public static byte	bytS_HIGH_MARK_J_COMBO_BOX			= 10;

	final public static byte	bytS_HIGH_SKILL_J_COMBO_BOX			= 8;
	final public static byte	bytS_LOW_BALLS_NUMBER_J_COMBO_BOX	= 5;
	final public static byte	bytS_LOW_MARK_J_COMBO_BOX			= 9;
	final public static byte	bytS_LOW_SKILL_J_COMBO_BOX			= 7;
	final public static byte	bytS_MARK_J_COMBO_BOX				= 13;
	final public static byte	bytS_OBJECTS_J_LIST					= 12;
	final public static byte	bytS_PATTERN_J_TEXT_FIELD			= 0;
	final public static byte	bytS_SHORTCUTS_J_COMBO_BOX			= 11;
	final public static byte	bytS_SITESWAP_J_TEXT_PANE			= 2;
	final public static byte	bytS_SKILL_J_COMBO_BOX				= 1;
	final public static byte	bytS_STYLE_J_COMBO_BOX				= 3;
	final private static long	serialVersionUID					= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final byte			bytGsourceType;
	private final int			intGkeyCode;
	private final ControlJFrame	objGcontrolJFrame;

	public KeysAction(ControlJFrame objPcontrolJFrame, byte bytPsourceType) {
		this(objPcontrolJFrame, bytPsourceType, KeyEvent.VK_ENTER);
	}

	public KeysAction(ControlJFrame objPcontrolJFrame, byte bytPsourceType, int intPkeyCode) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGsourceType = bytPsourceType;
		this.intGkeyCode = intPkeyCode;
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		switch (this.intGkeyCode) {
			case KeyEvent.VK_SPACE:
				this.objGcontrolJFrame.objGplayPauseJButton.actionPerformed(null);
				break;

			case KeyEvent.VK_COPY:
				switch (this.bytGsourceType) {
					case KeysAction.bytS_STYLE_J_COMBO_BOX:
						if (this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
							try {
								final Style objLstyle = (Style) this.objGcontrolJFrame.objGstyleJComboBox.getSelectedItem();
								if (objLstyle.strGstyle.length() > 0) {
									Constants.objS_GRAPHICS_TOOLKIT.getSystemClipboard().setContents(new StringSelection(objLstyle.strGstyle), null);
								}
							} catch (final Throwable objPthrowable) {}
						}
						break;
					case KeysAction.bytS_SHORTCUTS_J_COMBO_BOX:
						if (this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
							try {
								final String strL = (String) this.objGcontrolJFrame.objGshortcutsJComboBox.getSelectedItem();
								if (strL.length() > 0) {
									Constants.objS_GRAPHICS_TOOLKIT.getSystemClipboard().setContents(new StringSelection(strL), null);
								}
							} catch (final Throwable objPthrowable) {}
						}
						break;
				}
				break;

			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_TAB:
				switch (this.bytGsourceType) {
					case KeysAction.bytS_PATTERN_J_TEXT_FIELD:
						this.objGcontrolJFrame.objGskillJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_SKILL_J_COMBO_BOX:
						this.objGcontrolJFrame.objGmarkJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_MARK_J_COMBO_BOX:
						this.objGcontrolJFrame.objGsiteswapJTextPane.requestFocusInWindow();
						break;

					case KeysAction.bytS_SITESWAP_J_TEXT_PANE:
						this.objGcontrolJFrame.objGstyleJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_STYLE_J_COMBO_BOX:
						this.objGcontrolJFrame.objGcolorsJTextPane.requestFocusInWindow();
						break;

					case KeysAction.bytS_COLORS_J_TEXT_PANE:
						this.objGcontrolJFrame.objGlowBallsNumberJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_LOW_BALLS_NUMBER_J_COMBO_BOX:
						this.objGcontrolJFrame.objGhighBallsNumberJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_HIGH_BALLS_NUMBER_J_COMBO_BOX:
						this.objGcontrolJFrame.objGlowSkillJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_LOW_SKILL_J_COMBO_BOX:
						this.objGcontrolJFrame.objGhighSkillJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_HIGH_SKILL_J_COMBO_BOX:
						this.objGcontrolJFrame.objGlowMarkJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_LOW_MARK_J_COMBO_BOX:
						this.objGcontrolJFrame.objGhighMarkJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_HIGH_MARK_J_COMBO_BOX:
						this.objGcontrolJFrame.objGshortcutsJComboBox.requestFocusInWindow();
						break;

					case KeysAction.bytS_SHORTCUTS_J_COMBO_BOX:
						this.objGcontrolJFrame.objGobjectsJList.requestFocusInWindow();
						break;

					case KeysAction.bytS_OBJECTS_J_LIST:
						this.objGcontrolJFrame.doRestartJuggling();
						break;
				}
				break;
		}
	}
}
