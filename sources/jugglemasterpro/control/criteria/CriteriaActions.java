package jugglemasterpro.control.criteria;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class CriteriaActions {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final public static void doCheckFilters(final ControlJFrame objPcontrolJFrame) {
		objPcontrolJFrame.objGobjectsJList.setLists();
		objPcontrolJFrame.setFiltersControls();
		objPcontrolJFrame.setMenusEnabled();
		objPcontrolJFrame.validate();
		final int intLpreviousPatternIndex = objPcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		final int intLnextPatternIndex =
											objPcontrolJFrame.objGobjectsJList.getPatternIndex(objPcontrolJFrame.objGobjectsJList.getFilteredPatternRenewedIndex(intLpreviousPatternIndex));
		if (intLnextPatternIndex != intLpreviousPatternIndex) {
			objPcontrolJFrame.doStartJuggling(intLnextPatternIndex);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPfilterType
	 */
	final public static void doFilterBallsNumber(final ControlJFrame objPcontrolJFrame, final byte bytPbyteGlobalBallsNumber) {
		final byte bytLlowBallsNumber = (byte) (objPcontrolJFrame.objGlowBallsNumberJComboBox.getSelectedIndex() + 1);
		final byte bytLhighBallsNumber = (byte) (objPcontrolJFrame.objGhighBallsNumberJComboBox.getSelectedIndex() + 1);
		Preferences.setGlobalBytePreference(bytPbyteGlobalBallsNumber,
											bytPbyteGlobalBallsNumber == Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER ? bytLhighBallsNumber
																														: bytLlowBallsNumber);
		if (bytLlowBallsNumber > bytLhighBallsNumber) {
			switch (bytPbyteGlobalBallsNumber) {
				case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
					Preferences.setGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER, bytLlowBallsNumber);
					objPcontrolJFrame.objGhighBallsNumberJComboBox.selectIndex(bytLlowBallsNumber
																				- Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
					Preferences.setGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER, bytLhighBallsNumber);
					objPcontrolJFrame.objGlowBallsNumberJComboBox.selectIndex(bytLhighBallsNumber
																				- Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
					break;
			}
		}
	}

	final public static void doFilterGlobalMark(final ControlJFrame objPcontrolJFrame, final byte bytPbyteGlobalMark) {

		Tools.debug("CriteriaActions.doFilterGlobalMark(", bytPbyteGlobalMark, ')');
		final byte bytLlowMark = (byte) (objPcontrolJFrame.objGlowMarkJComboBox.getSelectedIndex());
		final byte bytLhighMark = (byte) (objPcontrolJFrame.objGhighMarkJComboBox.getSelectedIndex());
		Preferences.setGlobalBytePreference(bytPbyteGlobalMark, bytPbyteGlobalMark == Constants.bytS_BYTE_GLOBAL_HIGH_MARK ? bytLhighMark
																															: bytLlowMark);
		if (bytLlowMark > bytLhighMark) {
			switch (bytPbyteGlobalMark) {
				case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
					Preferences.setGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_MARK, bytLlowMark);
					objPcontrolJFrame.objGhighMarkJComboBox.selectIndex(bytLlowMark);
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
					Preferences.setGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_MARK, bytLhighMark);
					objPcontrolJFrame.objGlowMarkJComboBox.selectIndex(bytLhighMark);
					break;
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPfilterType
	 */
	final public static void doFilterGlobalSkill(final ControlJFrame objPcontrolJFrame, final byte bytPbyteGlobalSkill) {
		final byte bytLlowSkill = (byte) objPcontrolJFrame.objGlowSkillJComboBox.getSelectedIndex();
		final byte bytLhighSkill = (byte) objPcontrolJFrame.objGhighSkillJComboBox.getSelectedIndex();
		Preferences.setGlobalBytePreference(bytPbyteGlobalSkill, bytPbyteGlobalSkill == Constants.bytS_BYTE_GLOBAL_HIGH_SKILL ? bytLhighSkill
																																: bytLlowSkill);
		if (bytLlowSkill > bytLhighSkill) {
			switch (bytPbyteGlobalSkill) {
				case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
					Preferences.setGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_SKILL, bytLlowSkill);
					objPcontrolJFrame.objGhighSkillJComboBox.selectIndex(bytLlowSkill);
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
					Preferences.setGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_SKILL, bytLhighSkill);
					objPcontrolJFrame.objGlowSkillJComboBox.selectIndex(bytLhighSkill);
					break;
			}
		}
	}
}
