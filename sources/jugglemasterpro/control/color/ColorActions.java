package jugglemasterpro.control.color;

import java.awt.Color;

import javax.swing.JButton;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesButtonModel;
import jugglemasterpro.pattern.BallsColors;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class ColorActions {

	/**
	 * @param objGcontrolJFrame
	 * @param value
	 */
	final public static void doAdjustAlternateRate(ControlJFrame objPcontrolJFrame, byte bytPalternateRateValue) {
		// TODO Auto-generated method stub
		final byte bytLpreviousValue = objPcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS);
		if (bytPalternateRateValue != bytLpreviousValue) {
			objPcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS, bytPalternateRateValue);
			objPcontrolJFrame.setColorsControls();
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public static void doDisplayAlternateColors(final ControlJFrame objPcontrolJFrame, final boolean bolPenabled) {
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS, bolPenabled);
		objPcontrolJFrame.setColorsControls();
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_REINIT_COLORS | Constants.intS_ACTION_REFRESH_DRAWING);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public static void doDisplayColors(final ControlJFrame objPcontrolJFrame, final Boolean bolPstateBoolean) {
		objPcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS,
												bolPstateBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN);
		objPcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_COLORS, bolPstateBoolean != ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		if (bolPstateBoolean != ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN) {
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_REINIT_COLORS);
		}
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_REFRESH_DRAWING);
		objPcontrolJFrame.setColorsControls();
	}

	final public static void doHideColorsChoosers(final ControlJFrame objPcontrolJFrame) {
		ColorActions.doHideColorsChoosers(objPcontrolJFrame, null);
	}

	final public static void doHideColorsChoosers(final ControlJFrame objPcontrolJFrame, final JButton objPexcludedJButton) {

		if (objPcontrolJFrame.objGsiteswapColorChooserDropDownJButton != objPexcludedJButton) {
			objPcontrolJFrame.objGsiteswapColorChooserDropDownJButton.setPopUpVisible(false);
		}
		if (objPcontrolJFrame.objGbackgroundColorChooserDropDownJButton != objPexcludedJButton) {
			objPcontrolJFrame.objGbackgroundColorChooserDropDownJButton.setPopUpVisible(false);
		}
		if (objPcontrolJFrame.objGjugglerColorChooserDropDownJButton != objPexcludedJButton) {
			objPcontrolJFrame.objGjugglerColorChooserDropDownJButton.setPopUpVisible(false);
		}
		if (objPcontrolJFrame.objGballColorChooserDropDownJButton != objPexcludedJButton) {
			objPcontrolJFrame.objGballColorChooserDropDownJButton.setPopUpVisible(false);
		}
	}

	/**
	 * @param objGcolorsJTextPane
	 * @param intPposition
	 * @param intPlength
	 */
	final public static void doRemoveBallsColorsChars(	BallsColorsJTextPane objPcolorsJTextPane,
														ColorsStyledDocument objPcolorsStyledDocument,
														int intPposition,
														int intPlength) {

		final String strLprevious = objPcolorsJTextPane.getText();
		final int intLpreviousLength = strLprevious.length();
		int intLposition = intPposition;
		if (intLposition < intLpreviousLength && intPlength > 0) {
			int intLlengthToDelete = Math.min(intPlength, intLpreviousLength - intLposition);

			// Delete shade following last letter :
			if (intLposition + intLlengthToDelete < intLpreviousLength
				&& BallsColors.isBallColorLetterChar(strLprevious.charAt(intLposition + intLlengthToDelete - 1))
				&& BallsColors.isBallColorShadeChar(strLprevious.charAt(intLposition + intLlengthToDelete))) {
				++intLlengthToDelete;
			}

			// Remove letter preceeding shade :
			if (intLposition > 0 && BallsColors.isBallColorShadeChar(strLprevious.charAt(intLposition))
				&& (strLprevious.charAt(intLposition) - '0') != BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE) {
				if (BallsColors.isBallColorLetterChar(strLprevious.charAt(intLposition - 1))) {
					--intLposition;
					++intLlengthToDelete;
				} else {
					Tools.err("Error while removing color shade char: associated color letter missing");
				}
			}

			// Remove chars :
			try {
				objPcolorsStyledDocument.superRemove(intLposition, intLlengthToDelete);
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while removing color sequence chars");
				return;
			}

			if (intLposition < intPposition) {
				final String strLsingleLetter = strLprevious.substring(intLposition, intLposition + 1);
				objPcolorsStyledDocument.doAddStyles(strLsingleLetter);
				try {
					objPcolorsStyledDocument.superInsertString(intLposition, strLsingleLetter, objPcolorsStyledDocument.getStyle(strLsingleLetter));
				} catch (final Throwable objPthrowable) {
					Tools.err("Error while inserting color letter");
				}
			}

			// Memorize new color sequence :
			final ControlJFrame objLcontrolJFrame = objPcolorsJTextPane.getControlJFrame();
			final boolean bolLreverse = objLcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP);
			objLcontrolJFrame.saveControlString(bolLreverse ? Constants.bytS_STRING_LOCAL_REVERSE_COLORS : Constants.bytS_STRING_LOCAL_COLORS,
												objPcolorsJTextPane.getText());
			objLcontrolJFrame.saveControlString(bolLreverse ? Constants.bytS_STRING_LOCAL_COLORS : Constants.bytS_STRING_LOCAL_REVERSE_COLORS,
												objLcontrolJFrame.getJuggleMasterPro().objGsiteswap.getReverseColorsString(objPcolorsJTextPane.getText()));
			objPcolorsJTextPane.getControlJFrame().doAddAction(Constants.intS_ACTION_REINIT_COLORS);
		}

		final byte bytLanimationStatus = objPcolorsJTextPane.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus;
		if (bytLanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSING || bytLanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED) {
			objPcolorsJTextPane.getControlJFrame().doAddAction(Constants.intS_ACTION_REINIT_COLORS | Constants.intS_ACTION_RECREATE_BALLS_IMAGES
																| Constants.intS_ACTION_REFRESH_DRAWING);
		}
		objPcolorsJTextPane.setToolTipText();
	}

	/**
	 * @param colorChooserDropDownJButton
	 * @param objGcontrolJFrame
	 */
	final public static void doSelectNewBallColorButtonValue(	ColorChooserDropDownJButton objPcolorChooserDropDownJButton,
																ControlJFrame objPcontrolJFrame,
																boolean bolPpenNotDropDownButton) {
		objPcolorChooserDropDownJButton.setPopUpVisible(false);
		final byte bytLlocalStringType = objPcolorChooserDropDownJButton.bytGlocalStringType;
		final byte bytLgammaCorrection = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION);
		Tools.trace();
		Tools.out("Couleur cliquée   : ", objPcolorChooserDropDownJButton.objGjColorChooser.getColor());
		final Color objLreverseGammaColor =
											Tools.getNoGammaColor(	bolPpenNotDropDownButton
																							? Tools.getPenColor(objPcontrolJFrame.getControlString(Tools.getEnlightedStringType(bytLlocalStringType,
																																												objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT))))
																							: objPcolorChooserDropDownJButton.objGjColorChooser.getColor(),
																	bytLgammaCorrection);
		Tools.out("Couleur renversée : ", objLreverseGammaColor);
		objPcontrolJFrame.saveControlString(Tools.getEnlightedStringType(	bytLlocalStringType,
																			objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT)),
											Tools.getPenColorString(objLreverseGammaColor, true));
		Tools.out("Couleur stylo     : ", Tools.getPenColorString(objLreverseGammaColor, true));
		objPcolorChooserDropDownJButton.objGjColorChooser.setColor(Tools.getGammaColor(objLreverseGammaColor, bytLgammaCorrection));
		Tools.out("Couleur bouton    : ", Tools.getGammaColor(objLreverseGammaColor, bytLgammaCorrection));

		switch (bytLlocalStringType) {
			case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
				// if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER)
				// && !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)) {
				if (objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)) {
					objPcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
				}
				objPcontrolJFrame.getJuggleMasterPro().initJugglerColors();
				objPcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_HANDS_IMAGES | Constants.intS_ACTION_REFRESH_DRAWING);
				// }
				break;
			case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
				objPcontrolJFrame.getJuggleMasterPro().setBackgroundColors();
				objPcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_HANDS_IMAGES | Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
												| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
												| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES | Constants.intS_ACTION_DRAW_SITESWAP
												| Constants.intS_ACTION_REFRESH_DRAWING | Constants.intS_ACTION_RECREATE_BACKGROUND_IMAGES
												| Constants.intS_ACTION_RECREATE_BALLS_ERASING_IMAGES);
				break;
			case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
				objPcontrolJFrame.getJuggleMasterPro().initSiteswapColors();
				objPcontrolJFrame.doAddAction(Constants.intS_ACTION_DRAW_SITESWAP);
				break;
		}

		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
