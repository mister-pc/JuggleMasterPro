package fr.jugglemaster.control.pattern;

import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class PatternActions {

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfilteredShortcutIndex
	 */
	final public static void doApplyShortcut(final ControlJFrame objPcontrolJFrame, final int intPfilteredShortcutIndex) {
		objPcontrolJFrame.objGobjectsJList.setDirection(true);
		if (intPfilteredShortcutIndex >= 0) {
			final int intLfilteredPatternIndex =
													objPcontrolJFrame.objGobjectsJList.getFilteredPatternIndex(objPcontrolJFrame.objGshortcutsJComboBox.intGfilteredShortcutIndexA[intPfilteredShortcutIndex]);

			objPcontrolJFrame.objGobjectsJList.selectIndex(intLfilteredPatternIndex);
			objPcontrolJFrame.doStartJuggling(objPcontrolJFrame.objGobjectsJList.getPatternIndex(intLfilteredPatternIndex));
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public static void doReversePattern(final ControlJFrame objPcontrolJFrame) {

		objPcontrolJFrame.getJuggleMasterPro().doStopPattern();
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_MIRROR, objPcontrolJFrame.objGreversePatternJButton.isReversingPattern());
		objPcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP,
												objPcontrolJFrame.objGreversePatternJButton.isReversingPattern());
		objPcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE,
												objPcontrolJFrame.objGreversePatternJButton.isReversingPattern());

		objPcontrolJFrame.objGreversePatternJButton.doSwitchReversing();
		Tools.debug("PatternActions.doReversePattern(): ControlJFrame.doRestartJuggling()");
		objPcontrolJFrame.doRestartJuggling();
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}
