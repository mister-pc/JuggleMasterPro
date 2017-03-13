/*
 * @(#)ColorsStyledDocument.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.pattern.BallsColors;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ColorsStyledDocument extends DefaultStyledDocument {

	public ColorsStyledDocument(BallsColorsJTextPane objPcolorsJTextPane) {
		this.objGcolorsJTextPane = objPcolorsJTextPane;
	}

	@SuppressWarnings("unchecked") final void doAddStyles(String strPaddedColors) {

		// Get color values :
		final int[] intLaddedColorValueA = new BallsColors(strPaddedColors).getLogicalColorsValues();

		if (intLaddedColorValueA != null) {

			// Get initial style list :
			final ArrayList<String> strLinitialColorStyleAL = new ArrayList<String>(8);
			for (final Enumeration<String> objLinitialColorStyleStringE = (Enumeration<String>) this.getStyleNames(); objLinitialColorStyleStringE.hasMoreElements();) {
				final String strLinitialStyle = objLinitialColorStyleStringE.nextElement();
				if (!strLinitialColorStyleAL.contains(strLinitialStyle)) {
					strLinitialColorStyleAL.add(strLinitialStyle);
				}
			}

			// Get added style list :
			final ArrayList<String> strLaddedColorStyleAL = new ArrayList<String>(intLaddedColorValueA.length);
			for (final int intLaddedColorValue : intLaddedColorValueA) {
				final String strLaddedColorStyle = BallsColors.getBallColorString(intLaddedColorValue, false);
				if (!strLaddedColorStyleAL.contains(strLaddedColorStyle)) {
					strLaddedColorStyleAL.add(strLaddedColorStyle);
				}
			}

			// Add new styles :
			final byte bytLgammaCorrection = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION);
			for (final String strLaddedColorStyle : strLaddedColorStyleAL) {
				if (!strLinitialColorStyleAL.contains(strLaddedColorStyle)) {
					final Style objLcurrentStyle = this.addStyle(strLaddedColorStyle, BallsColors.objS_BALLS_COLORS_DEFAULT_STYLE);
					final Color objLbackgroundColor =
														BallsColors.getGammaBallColor(	new BallsColors(strLaddedColorStyle).getLogicalColorsValues()[0],
																						bytLgammaCorrection,
																						true);
					StyleConstants.setBackground(objLcurrentStyle, objLbackgroundColor);
					StyleConstants.setForeground(objLcurrentStyle, Tools.isLightColor(objLbackgroundColor) ? Color.BLACK : Color.WHITE);
				}
			}
		}
	}

	final public void doApplyStyles(BallsColors objPballColors) {

		// Add styled color sequence text :
		final int[] intLcolorValueA = objPballColors.getLogicalColorsValues();
		if (intLcolorValueA != null) {
			for (int intLcolorValueIndex = 0, intLcursorIndex = 0; intLcolorValueIndex < intLcolorValueA.length; ++intLcolorValueIndex) {
				try {
					final String strLballColor = BallsColors.getBallColorString(intLcolorValueA[intLcolorValueIndex], false);
					this.setCharacterAttributes(intLcursorIndex, strLballColor.length(), this.getStyle(strLballColor), true);
					intLcursorIndex += strLballColor.length();
				} catch (final Throwable objPthrowable) {}
			}
		}
	}

	@SuppressWarnings("unchecked") final public void doRemoveStyles() {

		// Get initial style list :
		for (final Enumeration<String> objLcolorStyleStringE = (Enumeration<String>) this.getStyleNames(); objLcolorStyleStringE.hasMoreElements();) {
			final String strLstyle = objLcolorStyleStringE.nextElement();
			if (!strLstyle.equals("default")) {
				this.removeStyle(strLstyle);
			}
		}
	}

	@Override final public void insertString(int intPposition, String strP, AttributeSet objPattributeSet) throws BadLocationException {

		final byte bytLanimationStatus = this.objGcolorsJTextPane.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus;
		final int intLlength = strP.length();
		final String strLprevious = this.objGcolorsJTextPane.getText();
		final int intLpreviousLength = strLprevious.length();
		if (intLlength == 0 || intPposition < 0 || intLpreviousLength < intPposition) {
			if (bytLanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSING || bytLanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED) {
				this.objGcolorsJTextPane.getControlJFrame().doAddAction(Constants.intS_ACTION_REINIT_COLORS
																		| Constants.intS_ACTION_RECREATE_BALLS_IMAGES
																		| Constants.intS_ACTION_REFRESH_DRAWING);
			}
			return;
		}

		char chrLbefore = intPposition > 0 ? strLprevious.charAt(intPposition - 1) : Strings.chrS_UNCLASS_NULL_CHAR;
		char chrLafter = intPposition < intLpreviousLength ? strLprevious.charAt(intPposition) : Strings.chrS_UNCLASS_NULL_CHAR;
		boolean bolLletter = intPposition == 0;

		// Insertion between letter and shade :
		int intLposition = intPposition;
		if (BallsColors.isBallColorLetterChar(chrLbefore) && BallsColors.isBallColorShadeChar(chrLafter)) {
			++intLposition;
			chrLbefore = chrLafter;
			chrLafter = intLposition < intLpreviousLength ? strLprevious.charAt(intPposition) : Strings.chrS_UNCLASS_NULL_CHAR;
		}

		// Build inserted string :
		final StringBuilder objLinsertedStringBuilder = new StringBuilder(intLlength);
		char chrLletter = Strings.chrS_UNCLASS_NULL_CHAR;
		char chrLshade = Strings.chrS_UNCLASS_NULL_CHAR;
		boolean bolLbeep = false;
		boolean bolLfirstChar = true;
		final char[] chrLinitialCharA = strP.toCharArray();
		for (final char chrLindexed : chrLinitialCharA) {
			chrLletter = BallsColors.getBallColorLetterChar(chrLindexed);

			// Letter :
			if (chrLletter != Strings.chrS_UNCLASS_NULL_CHAR) {
				if (chrLshade != Strings.chrS_UNCLASS_NULL_CHAR) {
					objLinsertedStringBuilder.append(chrLshade);
					chrLshade = Strings.chrS_UNCLASS_NULL_CHAR;
				}
				objLinsertedStringBuilder.append(chrLletter);
				bolLletter = false;
				bolLfirstChar = false;
				continue;
			}

			// Shade :
			final char chrLreadShade = BallsColors.getBallColorShadeChar(chrLindexed);
			if (chrLreadShade != Strings.chrS_UNCLASS_NULL_CHAR) {
				if (bolLletter) {
					bolLbeep = true;
					chrLshade = Strings.chrS_UNCLASS_NULL_CHAR;
				} else {
					// Replace previous shade :
					if (bolLfirstChar) {
						if (BallsColors.isBallColorShadeChar(chrLbefore)) {
							if (intLposition > 1) {
								final char chrLbeforeBefore = strLprevious.charAt(intLposition - 2);
								objLinsertedStringBuilder.append(chrLbeforeBefore);
								intLposition -= 2;
								super.remove(intLposition, 2);
								// this.doRemoveStyles(JuggleStrings.doConcat(chrLbeforeBefore, chrLbefore));
							} else {
								--intLposition;
								super.remove(intLposition, 1);
								Tools.err("Error while removing ball color shade: ball color letter missing");
							}
						} else if (BallsColors.isBallColorLetterChar(chrLbefore)) {
							objLinsertedStringBuilder.append(chrLbefore);
							super.remove(intLposition - 1, 1);
							--intLposition;
						} else {
							Tools.err("Error while inserting ball color shade: associated color letter missing");
						}
					}
					chrLshade = chrLreadShade;
					bolLletter = true;
					bolLfirstChar = false;
				}
				continue;
			}

			// Wrong char :
			bolLbeep = true;
		}

		if (chrLshade != Strings.chrS_UNCLASS_NULL_CHAR) {
			objLinsertedStringBuilder.append(chrLshade);
		}

		// Set new styles :
		final String strLinserted = objLinsertedStringBuilder.toString();
		if (strLinserted.length() > 0) {
			Tools.debug("ColorsStyledDocument.insertString(", strLinserted, ")");
			this.doAddStyles(strLinserted);
			final int[] intLinsertedColorValueA = new BallsColors(strLinserted).getLogicalColorsValues();
			int intLcursorDelta = 0;
			for (final int intLinsertedColorValue : intLinsertedColorValueA) {
				final String strLinsertedColor = BallsColors.getBallColorString(intLinsertedColorValue, false);
				super.insertString(intLposition + intLcursorDelta, strLinsertedColor, this.getStyle(strLinsertedColor));
				intLcursorDelta += strLinsertedColor.length();
			}

			// Memorize new color sequence :
			final ControlJFrame objLcontrolJFrame = this.objGcolorsJTextPane.getControlJFrame();
			final boolean bolLreverse = objLcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP);
			objLcontrolJFrame.saveControlString(bolLreverse ? Constants.bytS_STRING_LOCAL_REVERSE_COLORS : Constants.bytS_STRING_LOCAL_COLORS,
												this.objGcolorsJTextPane.getText());
			objLcontrolJFrame.saveControlString(bolLreverse ? Constants.bytS_STRING_LOCAL_COLORS : Constants.bytS_STRING_LOCAL_REVERSE_COLORS,
												objLcontrolJFrame.getJuggleMasterPro().objGsiteswap.getReverseColorsString(this.objGcolorsJTextPane.getText()));
			this.objGcolorsJTextPane.getControlJFrame().doAddAction(Constants.intS_ACTION_REINIT_COLORS);
		}
		if (bytLanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSING || bytLanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED) {
			Tools.out("Animation paused... Refreshing ball colors");
			this.objGcolorsJTextPane.getControlJFrame().doAddAction(Constants.intS_ACTION_REINIT_COLORS | Constants.intS_ACTION_RECREATE_BALLS_IMAGES
																	| Constants.intS_ACTION_REFRESH_DRAWING);
		}
		this.objGcolorsJTextPane.setToolTipText();

		// Beep on error :
		if (bolLbeep) {
			Constants.objS_GRAPHICS_TOOLKIT.beep();
		}
	}

	@Override final public void remove(int intPposition, int intPlength) {
		ColorActions.doRemoveBallsColorsChars(this.objGcolorsJTextPane, this, intPposition, intPlength);
	}

	/**
	 * @param intLposition
	 * @param strLsingleLetter
	 * @param style
	 */
	public void superInsertString(int intLposition, String strLsingleLetter, Style objPstyle) {
		try {
			super.insertString(intLposition, strLsingleLetter, objPstyle);
		} catch (final Throwable objPthrowable) {}

	}

	/**
	 * @param intLposition
	 * @param intLlengthToDelete
	 */
	public void superRemove(int intLposition, int intLlengthToDelete) {
		// TODO Auto-generated method stub
		try {
			super.remove(intLposition, intLlengthToDelete);
		} catch (final Throwable objPthrowable) {}
	}

	final private BallsColorsJTextPane	objGcolorsJTextPane;

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ColorsStyledDocument.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
