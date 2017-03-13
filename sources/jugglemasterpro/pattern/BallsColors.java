package jugglemasterpro.pattern;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.text.Style;
import javax.swing.text.StyleContext;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

public final class BallsColors {

	final public static byte		bytS_BALLS_COLORS_LETTER_AQUA			= 15;

	final public static byte		bytS_BALLS_COLORS_LETTER_BLUE			= 16;

	final public static byte		bytS_BALLS_COLORS_LETTER_CRYSTAL		= 4;

	final public static byte		bytS_BALLS_COLORS_LETTER_CYAN			= 14;

	final public static byte		bytS_BALLS_COLORS_LETTER_DARK			= 0;

	final public static byte		bytS_BALLS_COLORS_LETTER_DEFAULT;

	final public static byte		bytS_BALLS_COLORS_LETTER_EMERALD		= 19;

	final public static byte		bytS_BALLS_COLORS_LETTER_FUCHSIA		= 11;

	final public static byte		bytS_BALLS_COLORS_LETTER_GREEN			= 20;

	final public static byte		bytS_BALLS_COLORS_LETTER_HONEY			= 22;

	final public static byte		bytS_BALLS_COLORS_LETTER_INDIGO			= 10;

	final public static byte		bytS_BALLS_COLORS_LETTER_KHAKI			= 21;
	final public static byte		bytS_BALLS_COLORS_LETTER_LAVENDER		= 13;

	final public static byte		bytS_BALLS_COLORS_LETTER_MAROON			= 24;

	final public static byte		bytS_BALLS_COLORS_LETTER_NIGHT			= 18;

	final public static byte		bytS_BALLS_COLORS_LETTER_ORANGE			= 7;
	final public static byte		bytS_BALLS_COLORS_LETTER_PINK			= 12;

	final public static byte		bytS_BALLS_COLORS_LETTER_QUARTZ			= 2;
	final public static byte		bytS_BALLS_COLORS_LETTER_RED			= 8;
	final public static byte		bytS_BALLS_COLORS_LETTER_SAFFRON		= 6;
	final public static byte		bytS_BALLS_COLORS_LETTER_TAN			= 23;
	final public static byte		bytS_BALLS_COLORS_LETTER_ULTRAMARINE	= 17;
	final public static byte		bytS_BALLS_COLORS_LETTER_VERMILION		= 9;
	final public static byte		bytS_BALLS_COLORS_LETTER_WHITE			= 3;
	final public static byte		bytS_BALLS_COLORS_LETTER_YELLOW			= 5;
	final public static byte		bytS_BALLS_COLORS_LETTER_ZINC			= 1;
	final public static byte		bytS_BALLS_COLORS_LETTERS_NUMBER;
	final public static byte		bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE	= 5;
	final public static byte		bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE	= 10;

	final public static int			bytS_BALLS_COLORS_SHADE_MINIMUM_VALUE	= 0;
	final public static byte		bytS_BALLS_COLORS_SHADES_NUMBER;
	final public static char[]		chrS_BALLS_COLORS_LETTER_A;
	final public static int			intS_BALLS_COLORS_DEFAULT_VALUE;
	final public static Style		objS_BALLS_COLORS_DEFAULT_STYLE			=
																				StyleContext.getDefaultStyleContext()
																							.getStyle(StyleContext.DEFAULT_STYLE);
	final public static Color[][]	objS_COLORS_BALL_COLOR_A_A;
	@SuppressWarnings("unused")
	final private static long		serialVersionUID						= Constants.lngS_ENGINE_VERSION_NUMBER;

	// public static Color objS_TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

	final public static String[]	strS_BALLS_COLORS_LETTER_A;

	static {
		bytS_BALLS_COLORS_LETTERS_NUMBER = BallsColors.bytS_BALLS_COLORS_LETTER_MAROON + 1;
		bytS_BALLS_COLORS_LETTER_DEFAULT = BallsColors.bytS_BALLS_COLORS_LETTER_RED;
		bytS_BALLS_COLORS_SHADES_NUMBER = BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE + 1;
		objS_COLORS_BALL_COLOR_A_A = new Color[BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER][3];
		chrS_BALLS_COLORS_LETTER_A = new char[BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER];
		strS_BALLS_COLORS_LETTER_A = new String[BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER];
		intS_BALLS_COLORS_DEFAULT_VALUE =
											BallsColors.getLogicalColorValue(	BallsColors.bytS_BALLS_COLORS_LETTER_DEFAULT,
																					BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE);
		// StyleConstants.setFontFamily(JuggleBallColors.objS_BALLS_COLORS_DEFAULT_STYLE, this.objGcontrolJFrame.getFont().getFamily());
		// StyleConstants.setFontSize(JuggleBallColors.objS_BALLS_COLORS_DEFAULT_STYLE, this.objGcontrolJFrame.getFont().getSize());
		BallsColors.setColors();
		BallsColors.setColorsLetters();
		BallsColors.setColorsLabels();
	}

	final public static Color getBallColor(int intPlogicalColorValue, boolean bolPtrueColor) {
		return BallsColors.getGammaBallColor(	intPlogicalColorValue,
												Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE,
												bolPtrueColor,
												true);
	}

	final public static String getBallColorHTMLString(int intPlogicalColorValue, boolean bolPtrueColor) {
		final Color objLcolor = BallsColors.getBallColor(intPlogicalColorValue, bolPtrueColor);
		final int intLrGB[] = new int[] { objLcolor.getRed(), objLcolor.getGreen(), objLcolor.getBlue() };
		final StringBuilder objLcolorHTMLStringBuilder = new StringBuilder("#");
		for (final int intLcolorComponent : intLrGB) {
			final StringBuilder objLstringBuilder = new StringBuilder(Integer.toHexString(intLcolorComponent).toUpperCase());
			while (objLstringBuilder.length() < 2) {
				objLstringBuilder.insert(0, '0');
			}
			objLcolorHTMLStringBuilder.append(objLstringBuilder);
		}
		return objLcolorHTMLStringBuilder.toString();
	}

	final public static char getBallColorLetterChar(char chrPletter) {
		final char chrLcolorLetter = Character.toUpperCase(chrPletter);
		for (final char chrLcolorChar : BallsColors.chrS_BALLS_COLORS_LETTER_A) {
			if (chrLcolorLetter == chrLcolorChar) {
				return chrLcolorChar;
			}
		}
		return Strings.chrS_UNCLASS_NULL_CHAR;
	}

	public final static char getBallColorLetterChar(int intPlogicalColorValue) {
		final byte bytLcolorLetterIndex = (byte) (intPlogicalColorValue / 256);
		return (bytLcolorLetterIndex >= 0 && bytLcolorLetterIndex < BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER)
																													? BallsColors.chrS_BALLS_COLORS_LETTER_A[bytLcolorLetterIndex]
																													: Strings.chrS_UNCLASS_NULL_CHAR;
	}

	final private static byte getBallColorLetterValue(char chrPletter) {
		if (chrPletter != Strings.chrS_UNCLASS_NULL_CHAR) {
			final char chrLupperCaseLetter = Character.toUpperCase(chrPletter);
			for (byte bytLcolorLetterIndex = 0; bytLcolorLetterIndex < BallsColors.chrS_BALLS_COLORS_LETTER_A.length; ++bytLcolorLetterIndex) {
				if (chrLupperCaseLetter == BallsColors.chrS_BALLS_COLORS_LETTER_A[bytLcolorLetterIndex]) {
					return bytLcolorLetterIndex;
				}
			}
		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	final public static byte getBallColorLetterValue(int intPlogicalColorValue) {
		final byte bytLletter = (byte) (intPlogicalColorValue / 256);
		return bytLletter >= 0 && bytLletter < BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER ? bytLletter : Constants.bytS_UNCLASS_NO_VALUE;
	}

	final public static char getBallColorShadeChar(char chrPshade) {

		final int intLshade = chrPshade - '0';
		return intLshade > 0 && intLshade < BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE ? chrPshade : Strings.chrS_UNCLASS_NULL_CHAR;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPcolors
	 * @return
	 */

	public final static byte getBallColorShadeValue(int intPlogicalColorValue) {
		return BallsColors.getBallColorShadeValue(intPlogicalColorValue, false);
	}

	public final static byte getBallColorShadeValue(int intPlogicalColorValue, boolean bolPtrueColor) {
		int intLshade = intPlogicalColorValue & 255;
		intLshade =
					intLshade >= 0 && intLshade <= BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE ? (byte) intLshade
																									: Constants.bytS_UNCLASS_NO_VALUE;
		if (bolPtrueColor) {
			switch (intLshade) {
				case 0:
					return BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE;
				case BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE:
					return BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE - 1;
			}
		}
		return (byte) intLshade;
	}

	final public static String getBallColorString(int intPlogicalColorValue, boolean bolPtrueValue) {
		final char chrLcolorLetter = BallsColors.getBallColorLetterChar(intPlogicalColorValue);
		final byte bytLshade = BallsColors.getBallColorShadeValue(intPlogicalColorValue);
		final char chrLcolorShade =
									bytLshade != Constants.bytS_UNCLASS_NO_VALUE && bytLshade != 0
										&& (bytLshade != BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE || !bolPtrueValue)
																																? String.valueOf(bytLshade)
																																		.charAt(0)
																																: Strings.chrS_UNCLASS_NULL_CHAR;
		return Strings.doConcat(chrLcolorLetter, chrLcolorShade);
	}

	final public static Color getGammaBallColor(int intPlogicalColorValue, byte bytPgammaCorrection, boolean bolPtrueColor) {
		return BallsColors.getGammaBallColor(intPlogicalColorValue, bytPgammaCorrection, bolPtrueColor, true);
	}

	final public static Color getGammaBallColor(int intPlogicalColorValue,
												byte bytPgammaCorrection,
												boolean bolPtrueColor,
												boolean bolPtransparency) {

		// Set color letter and shade :
		final byte bytLballColorLetterValue = BallsColors.getBallColorLetterValue(BallsColors.getBallColorLetterChar(intPlogicalColorValue));
		final byte bytLballColorShade = BallsColors.getBallColorShadeValue(intPlogicalColorValue, bolPtrueColor);

		// Set dark and light colors :
		final Color[] objLcolorA = BallsColors.objS_COLORS_BALL_COLOR_A_A[bytLballColorLetterValue];
		final int intLlightColorShade =
										bytLballColorShade > BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE
																												? BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE - 1
																												: BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE;
		final int intLlightColorIndex =
										bytLballColorShade > BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE ? Constants.bytS_ENGINE_LIGHT
																												: Constants.bytS_ENGINE_NEUTRAL;
		final int[] intLlightColorComponentA =
												new int[] { objLcolorA[intLlightColorIndex].getRed(), objLcolorA[intLlightColorIndex].getGreen(),
													objLcolorA[intLlightColorIndex].getBlue() };
		final int intLdarkColorShade =
										bytLballColorShade > BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE
																												? BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE
																												: BallsColors.bytS_BALLS_COLORS_SHADE_MINIMUM_VALUE + 1;
		final int intLdarkColorIndex =
										bytLballColorShade > BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE ? Constants.bytS_ENGINE_NEUTRAL
																												: Constants.bytS_ENGINE_DARK;
		final int[] intLdarkColorComponentA =
												new int[] { objLcolorA[intLdarkColorIndex].getRed(), objLcolorA[intLdarkColorIndex].getGreen(),
													objLcolorA[intLdarkColorIndex].getBlue() };

		// Calculate result color :
		final int[] intLresultColorA = new int[3];
		for (byte bytLredGreenBlueIndex = 0; bytLredGreenBlueIndex < 3; ++bytLredGreenBlueIndex) {
			intLresultColorA[bytLredGreenBlueIndex] =
														Tools.getGammaComponentValue(	(intLdarkColorComponentA[bytLredGreenBlueIndex] - intLlightColorComponentA[bytLredGreenBlueIndex])
																							* (bytLballColorShade - intLlightColorShade)
																							/ (intLdarkColorShade - intLlightColorShade)
																							+ intLlightColorComponentA[bytLredGreenBlueIndex],
																						bytPgammaCorrection);

		}
		if (bolPtransparency && bytLballColorLetterValue == BallsColors.bytS_BALLS_COLORS_LETTER_CRYSTAL) {
			final byte bytLcrystalShade =
											(byte) Math.max(BallsColors.bytS_BALLS_COLORS_SHADE_MINIMUM_VALUE + 2,
															Math.min(bytLballColorShade, BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE - 2));
			final int intLalpha =
									128 * (BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE - bytLcrystalShade - 2)
										/ (BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE - BallsColors.bytS_BALLS_COLORS_SHADE_MINIMUM_VALUE - 4);
			return new Color(intLresultColorA[0], intLresultColorA[1], intLresultColorA[2], intLalpha);
		}
		return new Color(intLresultColorA[0], intLresultColorA[1], intLresultColorA[2]);
	}

	final public static int getLogicalColorTrueValue(int intPlogicalColorValue) {
		int intLcolorValue = intPlogicalColorValue;
		switch (BallsColors.getBallColorShadeValue(intPlogicalColorValue)) {
			case 0:
				intLcolorValue =
									BallsColors.getLogicalColorValue(	BallsColors.getBallColorLetterValue(intPlogicalColorValue),
																			BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE);
				break;
			case BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE:
				intLcolorValue =
									BallsColors.getLogicalColorValue(	BallsColors.getBallColorLetterValue(intPlogicalColorValue),
																			(byte) (BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE - 1));
		}
		return intLcolorValue;
	}

	final public static int getLogicalColorValue(byte bytPcolorLetterValue, byte bytPcolorShadeValue) {
		return BallsColors.getLogicalColorValue(bytPcolorLetterValue, bytPcolorShadeValue, false);
	}

	final public static int getLogicalColorValue(byte bytPcolorLetterValue, byte bytPcolorShadeValue, boolean bolPtrueColor) {
		byte bytLcolorShadeValue = bytPcolorShadeValue;
		if (bolPtrueColor) {
			switch (bytPcolorShadeValue) {
				case 0:
					bytLcolorShadeValue = BallsColors.bytS_BALLS_COLORS_SHADE_DEFAULT_VALUE;
					break;
				case BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE:
					--bytLcolorShadeValue;
					break;
			}
		}
		return 0 <= bytPcolorLetterValue && bytPcolorLetterValue < BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER && 0 <= bytPcolorShadeValue
				&& bytPcolorShadeValue <= BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE ? 256 * bytPcolorLetterValue + bytPcolorShadeValue
																							: Constants.bytS_UNCLASS_NO_VALUE;
	}

	final public static boolean isBallColorLetterChar(char chrP) {
		return BallsColors.getBallColorLetterChar(chrP) != Strings.chrS_UNCLASS_NULL_CHAR;
	}

	// final public static Color getBallColor(int intPcolorValue, boolean bolPtrueColor, boolean bolPtransparency) {
	// return JuggleBallsColors.getGammaBallColor(intPcolorValue,
	// Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE,
	// bolPtrueColor,
	// bolPtransparency);
	// }

	final public static boolean isBallColorShadeChar(char chrP) {
		return BallsColors.getBallColorShadeChar(chrP) != Strings.chrS_UNCLASS_NULL_CHAR;
	}

	final private static void setColors() {
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_AQUA][Constants.bytS_ENGINE_LIGHT] = new Color(0x33CCFF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_AQUA][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x1794FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_AQUA][Constants.bytS_ENGINE_DARK] = new Color(0x0066FF);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_BLUE][Constants.bytS_ENGINE_LIGHT] = new Color(0x0066FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_BLUE][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x0648FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_BLUE][Constants.bytS_ENGINE_DARK] = new Color(0x1111FF);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_CYAN][Constants.bytS_ENGINE_LIGHT] = new Color(0xCCFFFF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_CYAN][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x78E3FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_CYAN][Constants.bytS_ENGINE_DARK] = new Color(0x33CCFF);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_DARK][Constants.bytS_ENGINE_LIGHT] = new Color(0x4C4C4C);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_DARK][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x262626);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_DARK][Constants.bytS_ENGINE_DARK] = new Color(0x000000);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_EMERALD][Constants.bytS_ENGINE_LIGHT] = new Color(0x1F851F);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_EMERALD][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x0E580E);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_EMERALD][Constants.bytS_ENGINE_DARK] = new Color(0x003300);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_FUCHSIA][Constants.bytS_ENGINE_LIGHT] = new Color(0xFF93FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_FUCHSIA][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xD64AD6);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_FUCHSIA][Constants.bytS_ENGINE_DARK] = new Color(0xAD00AD);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_GREEN][Constants.bytS_ENGINE_LIGHT] = new Color(0x99FF66);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_GREEN][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x4AB038);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_GREEN][Constants.bytS_ENGINE_DARK] = new Color(0x1F851F);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_HONEY][Constants.bytS_ENGINE_LIGHT] = new Color(0xF8EDD2);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_HONEY][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xDBBC6E);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_HONEY][Constants.bytS_ENGINE_DARK] = new Color(0xC4941D);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_INDIGO][Constants.bytS_ENGINE_LIGHT] = new Color(0x9966FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_INDIGO][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x723DC0);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_INDIGO][Constants.bytS_ENGINE_DARK] = new Color(0x380062);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_KHAKI][Constants.bytS_ENGINE_LIGHT] = new Color(0xC1CFA3);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_KHAKI][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x7C8B5A);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_KHAKI][Constants.bytS_ENGINE_DARK] = new Color(0x364712);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_LAVENDER][Constants.bytS_ENGINE_LIGHT] = new Color(0xEDDFEF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_LAVENDER][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xBB96F9);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_LAVENDER][Constants.bytS_ENGINE_DARK] = new Color(0x9966FF);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_MAROON][Constants.bytS_ENGINE_LIGHT] = new Color(0x98530F);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_MAROON][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x6B3500);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_MAROON][Constants.bytS_ENGINE_DARK] = new Color(0x4D2700);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_NIGHT][Constants.bytS_ENGINE_LIGHT] = new Color(0x000099);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_NIGHT][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x000066);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_NIGHT][Constants.bytS_ENGINE_DARK] = new Color(0x000033);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ORANGE][Constants.bytS_ENGINE_LIGHT] = new Color(0xFF9F35);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ORANGE][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xF57315);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ORANGE][Constants.bytS_ENGINE_DARK] = new Color(0xEE5500);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_PINK][Constants.bytS_ENGINE_LIGHT] = new Color(0xFFC9FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_PINK][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xFFAFFF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_PINK][Constants.bytS_ENGINE_DARK] = new Color(0xFF93FF);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_QUARTZ][Constants.bytS_ENGINE_LIGHT] = new Color(0xDBDBDB);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_QUARTZ][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xC6C6C6);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_QUARTZ][Constants.bytS_ENGINE_DARK] = new Color(0xA0A0A0);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_RED][Constants.bytS_ENGINE_LIGHT] = new Color(0xEE5500);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_RED][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xDA2200);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_RED][Constants.bytS_ENGINE_DARK] = new Color(0xCC0000);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_SAFFRON][Constants.bytS_ENGINE_LIGHT] = new Color(0xFFDA47);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_SAFFRON][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xFFBA3D);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_SAFFRON][Constants.bytS_ENGINE_DARK] = new Color(0xFF9F35);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_TAN][Constants.bytS_ENGINE_LIGHT] = new Color(0xE3BA92);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_TAN][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xB1753A);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_TAN][Constants.bytS_ENGINE_DARK] = new Color(0x98530F);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ULTRAMARINE][Constants.bytS_ENGINE_LIGHT] = new Color(0x1111FF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ULTRAMARINE][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x0808C7);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ULTRAMARINE][Constants.bytS_ENGINE_DARK] = new Color(0x000099);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_VERMILION][Constants.bytS_ENGINE_LIGHT] = new Color(0xCC0000);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_VERMILION][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x900000);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_VERMILION][Constants.bytS_ENGINE_DARK] = new Color(0x550000);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_WHITE][Constants.bytS_ENGINE_LIGHT] = new Color(0xFFFFFF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_WHITE][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xF4F4F4);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_WHITE][Constants.bytS_ENGINE_DARK] = new Color(0xDBDBDB);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_CRYSTAL][Constants.bytS_ENGINE_LIGHT] = new Color(0xFFFFFF);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_CRYSTAL][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xE0E0EC);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_CRYSTAL][Constants.bytS_ENGINE_DARK] = new Color(0xCCCCE0);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_YELLOW][Constants.bytS_ENGINE_LIGHT] = new Color(0xFFFFC1);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_YELLOW][Constants.bytS_ENGINE_NEUTRAL] = new Color(0xFFE147);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_YELLOW][Constants.bytS_ENGINE_DARK] = new Color(0xFFDA47);

		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ZINC][Constants.bytS_ENGINE_LIGHT] = new Color(0xA0A0A0);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ZINC][Constants.bytS_ENGINE_NEUTRAL] = new Color(0x6C6C6C);
		BallsColors.objS_COLORS_BALL_COLOR_A_A[BallsColors.bytS_BALLS_COLORS_LETTER_ZINC][Constants.bytS_ENGINE_DARK] = new Color(0x4C4C4C);

	}

	final private static void setColorsLabels() {
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_AQUA] = "Aqua";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_BLUE] = "Blue";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_CYAN] = "Cyan";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_DARK] = "Dark";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_EMERALD] = "Emerald";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_FUCHSIA] = "Fuchsia";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_GREEN] = "Green";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_HONEY] = "Honey";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_INDIGO] = "Indigo";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_KHAKI] = "Khaki";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_LAVENDER] = "Lavender";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_MAROON] = "Maroon";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_NIGHT] = "Night";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_ORANGE] = "Orange";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_PINK] = "Pink";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_QUARTZ] = "Quartz";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_RED] = "Red";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_SAFFRON] = "Saffron";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_TAN] = "Tan";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_ULTRAMARINE] = "Ultramarine";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_VERMILION] = "Vermilion";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_WHITE] = "White";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_CRYSTAL] = "Crystal";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_YELLOW] = "Yellow";
		BallsColors.strS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_ZINC] = "Zinc";
	}

	final private static void setColorsLetters() {
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_AQUA] = 'A';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_BLUE] = 'B';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_CYAN] = 'C';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_DARK] = 'D';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_EMERALD] = 'E';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_FUCHSIA] = 'F';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_GREEN] = 'G';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_HONEY] = 'H';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_INDIGO] = 'I';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_KHAKI] = 'K';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_LAVENDER] = 'L';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_MAROON] = 'M';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_NIGHT] = 'N';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_ORANGE] = 'O';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_PINK] = 'P';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_QUARTZ] = 'Q';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_RED] = 'R';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_SAFFRON] = 'S';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_TAN] = 'T';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_ULTRAMARINE] = 'U';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_VERMILION] = 'V';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_WHITE] = 'W';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_CRYSTAL] = 'X';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_YELLOW] = 'Y';
		BallsColors.chrS_BALLS_COLORS_LETTER_A[BallsColors.bytS_BALLS_COLORS_LETTER_ZINC] = 'Z';
	}

	private Boolean			bolGstateBoolean;

	private int[]			intGcolorValueA;

	// private final int[] intGcursorIndexA;
	private int				intGerrorIndex;

	private final String[]	strGcolorsA;

	public BallsColors(String strPcolors) {
		this.strGcolorsA = new String[2];
		this.strGcolorsA[Constants.bytS_UNCLASS_INITIAL] = new String(strPcolors);
		this.strGcolorsA[Constants.bytS_UNCLASS_CURRENT] = Strings.strS_EMPTY;
		this.intGcolorValueA = null;
		this.intGerrorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		this.bolGstateBoolean = Boolean.TRUE;

		if (!Tools.isEmpty(strPcolors)) {

			// Parse string :
			final ArrayList<Integer> objLvalueIntegerAL = new ArrayList<Integer>(strPcolors.length() / 2 + 1);
			final StringBuilder objLcurrentColorsStringBuilder = new StringBuilder(strPcolors.length());

			final char[] chrLcolorA = strPcolors.toCharArray();
			byte bytLballColorLetterValue = Constants.bytS_UNCLASS_NO_VALUE;
			byte bytLballColorShadeValue = 0;
			Boolean bolLcolorLetterBoolean = null;
			for (int intLcharIndex = 0; intLcharIndex < chrLcolorA.length && this.bolGstateBoolean != Boolean.FALSE; ++intLcharIndex) {
				final char chrLcolor = chrLcolorA[intLcharIndex];

				// Space char :
				if (chrLcolor == ' ' || chrLcolor == '\t') {
					this.setError(intLcharIndex, false);
					continue;
				}

				final char chrLballColorLetter = BallsColors.getBallColorLetterChar(chrLcolor);
				final char chrLballColorShade = BallsColors.getBallColorShadeChar(chrLcolor);

				switch (chrLballColorLetter) {

					case Strings.chrS_UNCLASS_NULL_CHAR:
						switch (chrLballColorShade) {
							case Strings.chrS_UNCLASS_NULL_CHAR:
								// Neither a color shade, nor a color letter :
								this.setError(intLcharIndex, true);
								return;

							default:
								// Color shade :
								if (bolLcolorLetterBoolean != Boolean.FALSE) { // A letter was waited
									this.setError(intLcharIndex, false);
								}
								bytLballColorShadeValue = (byte) (chrLballColorShade - '0');
								bolLcolorLetterBoolean = Boolean.TRUE;
						}
						break;

					default:
						// Color letter :
						if (bytLballColorLetterValue != Constants.bytS_UNCLASS_NO_VALUE) {
							final int intLcolorValue = BallsColors.getLogicalColorValue(bytLballColorLetterValue, bytLballColorShadeValue);
							objLvalueIntegerAL.add(intLcolorValue);
							objLcurrentColorsStringBuilder.append(BallsColors.getBallColorString(intLcolorValue, false));
						}
						bytLballColorLetterValue = BallsColors.getBallColorLetterValue(chrLcolor);
						bytLballColorShadeValue = 0;
						bolLcolorLetterBoolean = Boolean.FALSE;
				}
			}

			// Last color letter :
			if (bytLballColorLetterValue != Constants.bytS_UNCLASS_NO_VALUE) {
				final int intLcolorValue = BallsColors.getLogicalColorValue(bytLballColorLetterValue, bytLballColorShadeValue);
				objLvalueIntegerAL.add(intLcolorValue);
				objLcurrentColorsStringBuilder.append(BallsColors.getBallColorString(intLcolorValue, false));
			}

			// Build array :
			if (objLvalueIntegerAL.size() > 0) {
				this.intGcolorValueA = new int[objLvalueIntegerAL.size()];
				for (int intLindex = 0; intLindex < this.intGcolorValueA.length; ++intLindex) {
					this.intGcolorValueA[intLindex] = objLvalueIntegerAL.get(intLindex).intValue();
				}
			}
			this.strGcolorsA[Constants.bytS_UNCLASS_CURRENT] = objLcurrentColorsStringBuilder.toString();
		}
	}

	final public String getBallsColorsString() {
		return this.getBallsColorsString(Constants.bytS_UNCLASS_CURRENT);
	}

	final public String getBallsColorsString(byte bytPinitialOrCurrent) {
		return bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL || bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT
																																? this.strGcolorsA[bytPinitialOrCurrent]
																																: null;
	}

	final public int getColorsNumber() {
		return this.intGcolorValueA != null ? this.intGcolorValueA.length : 0;
	}

	final public int getErrorIndex() {
		return this.intGerrorIndex;
	}

	final public int[] getLogicalColorsValues() {
		return this.getLogicalColorsValues(false);
	}

	final public int[] getLogicalColorsValues(boolean bolPtrueColors) {
		if (bolPtrueColors && this.intGcolorValueA != null) {
			final int[] intLcolorValueA = new int[this.intGcolorValueA.length];
			for (int intLcolorValueIndex = 0; intLcolorValueIndex < this.intGcolorValueA.length; ++intLcolorValueIndex) {
				intLcolorValueA[intLcolorValueIndex] = BallsColors.getLogicalColorTrueValue(this.intGcolorValueA[intLcolorValueIndex]);
			}
			return intLcolorValueA;
		}
		return this.intGcolorValueA;
	}

	final public Boolean getState() {
		return this.bolGstateBoolean;
	}

	final public ArrayList<Integer> getUsefulLogicalColorsValues() {

		if (this.intGcolorValueA == null) {
			return new ArrayList<Integer>();
		}

		// Add current color values :
		final ArrayList<Integer> objLcolorValueAL = new ArrayList<Integer>(this.intGcolorValueA.length);
		for (final int intLcolorValue : this.intGcolorValueA) {
			final int intLlogicalTrueColorValue = BallsColors.getLogicalColorTrueValue(intLcolorValue);
			if (!objLcolorValueAL.contains(intLlogicalTrueColorValue)) {
				objLcolorValueAL.add(intLlogicalTrueColorValue);
			}
		}

		// Add color default value :
		final int intLdefaultColorValue = BallsColors.intS_BALLS_COLORS_DEFAULT_VALUE;
		if (!objLcolorValueAL.contains(intLdefaultColorValue)) {
			objLcolorValueAL.add(intLdefaultColorValue);
		}

		return objLcolorValueAL;
	}

	final private void setError(int intPindex, boolean bolPfatal) {
		if (this.intGerrorIndex == Constants.bytS_UNCLASS_NO_VALUE || bolPfatal) {
			this.intGerrorIndex = intPindex;
		}
		if (bolPfatal) {
			this.strGcolorsA[Constants.bytS_UNCLASS_CURRENT] = null;
			this.bolGstateBoolean = Boolean.FALSE;
		} else {
			this.bolGstateBoolean = null;
		}
	}

	@Override final public String toString() {
		final StringBuilder objLstringBuilder = new StringBuilder();
		objLstringBuilder.append(Strings.doConcat(	"Sequence : '",
													this.strGcolorsA[Constants.bytS_UNCLASS_CURRENT]/*
																									 * .substring( 0,
																									 * this.intGcursorIndexA[Constants.bytS_UNCLASS_CURRENT]),
																									 * '|',
																									 * this.strGcolorsA[Constants.bytS_UNCLASS_CURRENT].substring(this.intGcursorIndexA[Constants.bytS_UNCLASS_CURRENT])
																									 */,
													'\'',
													Strings.strS_LINE_SEPARATOR));

		if (this.intGcolorValueA != null) {
			for (int intLcolorValueIndex = 0; intLcolorValueIndex < this.intGcolorValueA.length; ++intLcolorValueIndex) {
				objLstringBuilder.append(Strings.doConcat(	intLcolorValueIndex == 0 ? "Color(s) : " : "                    ",
															BallsColors.getBallColor(this.intGcolorValueA[intLcolorValueIndex], true),
															Strings.strS_LINE_SEPARATOR));
			}
		}
		return objLstringBuilder.toString();
	}
}
