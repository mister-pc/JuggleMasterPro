/*
 * @(#)JuggleTools.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;
import fr.jugglemaster.control.ControlJFrame;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Tools {

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPcontainer
	 * @param intPcontainedValue
	 * @return
	 */
	final public static boolean contains(int intPcontainer, int intPcontainedValue) {
		return ((intPcontainer & intPcontainedValue) == intPcontainedValue);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	final public static void debug(Object... objPobjectA) {

		if (Constants.bolS_UNCLASS_DEBUG) {
			if (Constants.bolS_UNCLASS_CONSOLE) {

				synchronized (System.out) {
					final String objLdate = Strings.doConcat(Tools.getDate(), " -- ");
					System.out.print(objLdate);
					final String strLdebug =
												Strings	.doConcat(objPobjectA)
														.replace(	Strings.strS_LINE_SEPARATOR,
																	Strings.doConcat(	Strings.getCharsString(' ', objLdate.length()),
																						Strings.strS_LINE_SEPARATOR));

					System.out.print(strLdebug);
					System.out.print(Strings.strS_LINE_SEPARATOR);
					System.out.flush();
				}
			}

			if (Constants.bolS_UNCLASS_LOG) {
				Tools.log(objPobjectA);
			}
		}
	}

	public static void doSetFont(Container objLfatherContainer, Font objLfont) {

		objLfatherContainer.setFont(objLfont);
		for (final Component objLcomponent : objLfatherContainer.getComponents()) {
			if (objLcomponent instanceof Container) {
				Tools.doSetFont((Container) objLcomponent, objLfont);
			} else {
				objLcomponent.setFont(objLfont);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	final public static void err(Object... objPobjectA) {

		if (Constants.bolS_UNCLASS_ERRORS) {
			if (Constants.bolS_UNCLASS_CONSOLE) {

				synchronized (System.err) {
					System.err.println(Strings.doConcat(objPobjectA));
					System.err.flush();
				}
			}

			if (Constants.bolS_UNCLASS_LOG) {
				Tools.log(objPobjectA);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfileName
	 * @return
	 */
	final public static BufferedReader getBufferedReader(String strPfileName) {
		BufferedReader objLbufferedReader = null;

		try {
			objLbufferedReader = new BufferedReader(new FileReader(strPfileName));
		} catch (final Throwable objPfirstThrowable) {
			try {
				objLbufferedReader = new BufferedReader(new InputStreamReader(new URL(strPfileName).openStream()));
			} catch (final Throwable objPsecondThrowable) {
				Tools.err("Error while trying to open '", strPfileName, "'");
				return null;
			}
		}

		return objLbufferedReader;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public static String getDate() {
		final Date objLdate = new Date(System.currentTimeMillis() - Constants.lngS_ENGINE_START_DATE);
		return (new SimpleDateFormat("HH:mm:ss.SSS")).format(objLdate);
	}

	final public static Document getDocument(JComboBox<Object> objLjComboBox) {

		Document objLdocument;
		try {
			objLdocument = ((JTextField) objLjComboBox.getEditor().getEditorComponent()).getDocument();
		} catch (final Throwable objPthrowable) {
			objLdocument = null;
		}
		return objLdocument;
	}

	final public static byte getEnlightedStringType(byte bytPlocalStringType, boolean bolPlight) {

		byte bytLenlightedLocalStringType = bytPlocalStringType;
		switch (bytPlocalStringType) {
			case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
			case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
				bytLenlightedLocalStringType = bolPlight ? Constants.bytS_STRING_LOCAL_JUGGLER_DAY : Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT;
				break;
			case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
			case Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT:
				bytLenlightedLocalStringType = bolPlight ? Constants.bytS_STRING_LOCAL_BACKGROUND_DAY : Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT;
				break;
			case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
			case Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT:
				bytLenlightedLocalStringType = bolPlight ? Constants.bytS_STRING_LOCAL_SITESWAP_DAY : Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT;
				break;
		}
		return bytLenlightedLocalStringType;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPvalue
	 * @return
	 */
	final public static int getEvenValue(int intPvalue) {
		return intPvalue - (intPvalue & 1);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPsiteswap
	 * @param bolPjMP
	 * @return
	 */
	final public static String getFormattedSiteswapString(String strPsiteswap, boolean bolPjMP) {
		if (strPsiteswap == null || bolPjMP) {
			return strPsiteswap;
		}
		final char[] chrLsiteswapA = strPsiteswap.toCharArray();
		final StringBuilder objLstringBuilder = new StringBuilder(chrLsiteswapA.length);
		for (final char chrLindexed : chrLsiteswapA) {
			if (chrLindexed != ' ' && chrLindexed != '\t') {
				objLstringBuilder.append(chrLindexed != '×' ? chrLindexed : 'x');
			}
		}
		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPcolor
	 * @return
	 */

	final public static Color getGammaColor(Color objPcolor, byte bytPgammaCorrection) {
		return new Color(	Tools.getGammaComponentValue(objPcolor.getRed(), bytPgammaCorrection),
							Tools.getGammaComponentValue(objPcolor.getGreen(), bytPgammaCorrection),
							Tools.getGammaComponentValue(objPcolor.getBlue(), bytPgammaCorrection));
	}

	final public static int getGammaComponentValue(int intPnoGammaComponentValue, byte bytPgammaCorrection) {

		final int intLnoGammaComponentValue = Math.max(0, Math.min(intPnoGammaComponentValue, 255));
		int intLgammaComponentValue = intLnoGammaComponentValue;

		if (bytPgammaCorrection == Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE) {
			return intLgammaComponentValue;
		}

		final float fltLgammaRatio = Math.abs(bytPgammaCorrection - Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE) / 20F;
		if (bytPgammaCorrection > Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE) {
			intLgammaComponentValue += (255 - intLnoGammaComponentValue) * fltLgammaRatio;
		} else {
			intLgammaComponentValue *= 1F - fltLgammaRatio;
		}

		return Math.max(0, Math.min(intLgammaComponentValue, 255));
	}

	final public static String getJuggleMasterProInfo() {
		return Strings.doConcat(Strings.strS_JUGGLE_MASTER,
								" Pro ",
								Constants.strS_ENGINE_VERSION_NUMBER,
								" - \251 ",
								Constants.strS_ENGINE_COPYRIGHT_YEARS,
								' ',
								Constants.strS_ENGINE_ARNAUD_BELO,
								" (jugglemaster@free.fr)");
	}

	final public static JToolTip getJuggleToolTip(ControlJFrame objPcontrolJFrame) {
		final JToolTip objLjToolTip = new JToolTip();
		objLjToolTip.setFont(objPcontrolJFrame.getFont());
		objLjToolTip.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		return objLjToolTip;
	}

	final public static int getLength(int intPvalue) {
		return (intPvalue < 0 ? 1 : 0) + (int) Math.floor(Math.log10(intPvalue == 0 ? 1 : Math.abs(intPvalue))) + 1;
	}

	final public static String getLocaleString(String strPkey) {
		try {
			return Tools.objS_MESSAGE_RESOURCE_BUNDLE.getString(strPkey);
		} catch (final Throwable objPthrowable) {
			// throw new Error("Fatal: Resource for ServiceUI is broken; " +
			// "there is no " + strPkey + " strPkey in resource");
			return strPkey;
		}
	}

	final public static String getMediaName(String strPkey) {
		try {
			String strLnewKey = strPkey.replace(' ', '-');
			strLnewKey = strLnewKey.replace('#', 'n');

			return Tools.objS_MESSAGE_RESOURCE_BUNDLE.getString(strLnewKey);
		} catch (final Throwable objPthrowable) {
			return strPkey;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPfirstColor
	 * @param objPsecondColor
	 * @return
	 */
	final public static Color getMiddleColor(Color objPfirstColor, Color objPsecondColor) {
		return new Color(	(objPfirstColor.getRed() + objPsecondColor.getRed()) / 2,
							(objPfirstColor.getGreen() + objPsecondColor.getGreen()) / 2,
							(objPfirstColor.getBlue() + objPsecondColor.getBlue()) / 2);
	}

	/**
	 * Returns mnemonic character from resource
	 */
	final public static char getMnemonicChar(String strPkey) {
		final String strLmnemonic = Tools.getLocaleString(Strings.doConcat(strPkey, ".mnemonic"));
		if (strLmnemonic != null && strLmnemonic.length() > 0) {
			return strLmnemonic.charAt(0);
		}
		return Strings.chrS_UNCLASS_NULL_CHAR;
	}

	final public static Color getNoGammaColor(Color objPcolor, byte bytPgammaCorrection) {
		return new Color(	Tools.getNoGammaComponentValue(objPcolor.getRed(), bytPgammaCorrection),
							Tools.getNoGammaComponentValue(objPcolor.getGreen(), bytPgammaCorrection),
							Tools.getNoGammaComponentValue(objPcolor.getBlue(), bytPgammaCorrection));
	}

	final public static int getNoGammaComponentValue(int intPgammaComponentValue, byte bytPgammaCorrection) {
		final int intLgammaComponentValue = Math.max(0, Math.min(intPgammaComponentValue, 255));
		int intLnoGammaComponentValue = intLgammaComponentValue;

		if (bytPgammaCorrection == Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE) {
			return intLnoGammaComponentValue;
		}

		final float fltLgammaRatio = Math.abs(bytPgammaCorrection - Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE) / 20F;
		if (bytPgammaCorrection > Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE) {
			intLnoGammaComponentValue = (int) ((intLgammaComponentValue - 255F * fltLgammaRatio) / (1F - fltLgammaRatio));
		} else {
			intLnoGammaComponentValue /= (1F - fltLgammaRatio);
		}
		return Math.max(0, Math.min(intLnoGammaComponentValue, 255));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPvalue
	 * @return
	 */
	final public static int getNumberedBoolean(boolean bolPvalue) {
		return (bolPvalue ? 1 : 0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPvalue
	 * @return
	 */
	final public static int getOddValue(int intPvalue) {
		return intPvalue + 1 - (intPvalue & 1);
	}

	// final public static Object getParameterValueType(String strPparameterValue) {
	final public static Object getParameterValueType(String strPparameterValue) {

		// Byte value :
		try {
			final Byte bytLvalueByte = Byte.valueOf(strPparameterValue);

			if ((bytLvalueByte != null) && (bytLvalueByte.byteValue() >= 0)) {
				return bytLvalueByte;
			}
		} catch (final Throwable objPthrowable) {}

		// Float value :
		try {
			final Float objLvalueFloat = Float.valueOf(strPparameterValue);

			if ((objLvalueFloat != null) && (objLvalueFloat.floatValue() >= 0F)) {
				return Float.valueOf((int) (objLvalueFloat.floatValue() * 100));
			}
		} catch (final Throwable objPthrowable) {}

		final String strLlowerCaseValue = strPparameterValue.toLowerCase();
		// True value :
		if (strLlowerCaseValue.equals("true") || strLlowerCaseValue.equals("yes") || strLlowerCaseValue.equals("on")) {
			return Boolean.TRUE;
		}

		// False value :
		if (strLlowerCaseValue.equals("false") || strLlowerCaseValue.equals("no") || strLlowerCaseValue.equals("off")) {
			return Boolean.FALSE;
		}

		// Global/local value :
		if (strLlowerCaseValue.equals("local")) {
			return Character.valueOf('L');
		}

		if (strLlowerCaseValue.equals("global")) {
			return Character.valueOf('G');
		}

		if (strLlowerCaseValue.equals("user")) {
			return Short.valueOf((short) 0);
		}
		// String value :
		return strPparameterValue;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPparameterValue
	 * @return
	 */
	final public static Object[] getParameterValueTypeA(String[] strPparameterValueA) {

		Object[] objLobjectA = null;
		if (strPparameterValueA.length > 0) {
			objLobjectA = new Object[strPparameterValueA.length];
			for (int intLparameterValueIndex = 0; intLparameterValueIndex < strPparameterValueA.length; ++intLparameterValueIndex) {
				objLobjectA[intLparameterValueIndex] = Tools.getParameterValueType(strPparameterValueA[intLparameterValueIndex]);
			}
		}
		return objLobjectA;
	}

	final public static Color getPenColor(String strPcolor) {
		return Tools.getPenGammaColor(strPcolor, Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcolor
	 * @return
	 */
	final public static String getPenColorString(Color objPcolor) {
		return Tools.getPenColorString(objPcolor, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcolor
	 * @param bolPlongFormat
	 * @return
	 */
	final public static String getPenColorString(Color objPcolor, boolean bolPlongFormat) {
		return Tools.getPenColorString(objPcolor, bolPlongFormat, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcolor
	 * @param bolPlongFormat
	 * @param bolPhtmlFormat
	 * @return
	 */
	final public static String getPenColorString(Color objPcolor, boolean bolPlongFormat, boolean bolPhtmlFormat) {
		final boolean bolLlongFormat = bolPlongFormat || bolPhtmlFormat;
		String strLred = Integer.toHexString(objPcolor.getRed() / (bolLlongFormat ? 1 : 16));
		String strLgreen = Integer.toHexString(objPcolor.getGreen() / (bolLlongFormat ? 1 : 16));
		String strLblue = Integer.toHexString(objPcolor.getBlue() / (bolLlongFormat ? 1 : 16));

		if (bolLlongFormat) {
			if (strLred.length() == 1) {
				strLred = Strings.doConcat('0', strLred);
			}
			if (strLgreen.length() == 1) {
				strLgreen = Strings.doConcat('0', strLgreen);
			}
			if (strLblue.length() == 1) {
				strLblue = Strings.doConcat('0', strLblue);
			}

			if (!bolPhtmlFormat && strLred.charAt(0) == strLred.charAt(1) && strLgreen.charAt(0) == strLgreen.charAt(1)
				&& strLblue.charAt(0) == strLblue.charAt(1)) {
				strLred = strLred.substring(0, 1);
				strLgreen = strLgreen.substring(0, 1);
				strLblue = strLblue.substring(0, 1);
			}
		}
		return Strings.doConcat(bolPhtmlFormat ? '#' : null, strLred, strLgreen, strLblue).toUpperCase();
	}

	final public static Color getPenGammaColor(String strPcolor, byte bytPgammaCorrection) {
		if (strPcolor != null) {
			final char[] chrLcolorsStringA = strPcolor.toCharArray();
			if ((chrLcolorsStringA.length == 3) || (chrLcolorsStringA.length == 6)) {
				final int[] intLindexedValueA = new int[chrLcolorsStringA.length];

				for (byte bytLcharIndex = 0; bytLcharIndex < chrLcolorsStringA.length; ++bytLcharIndex) {

					if ((chrLcolorsStringA[bytLcharIndex] >= '0') && (chrLcolorsStringA[bytLcharIndex] <= '9')) {
						intLindexedValueA[bytLcharIndex] = chrLcolorsStringA[bytLcharIndex] - '0';
						continue;
					}

					final char chrLindexed = Character.toLowerCase(chrLcolorsStringA[bytLcharIndex]);
					if ((chrLindexed >= 'a') && (chrLindexed <= 'f')) {
						intLindexedValueA[bytLcharIndex] = chrLindexed - 'a' + 10;
						continue;
					}

					return null;
				}

				final int[] intLresultColorA = new int[3];
				final boolean bolLlongString = chrLcolorsStringA.length == 6;
				for (byte bytLredGreenBlueIndex = 0; bytLredGreenBlueIndex < 3; ++bytLredGreenBlueIndex) {
					intLresultColorA[bytLredGreenBlueIndex] =
																Tools.getGammaComponentValue(	bolLlongString
																												? intLindexedValueA[2 * bytLredGreenBlueIndex]
																													* 16
																													+ intLindexedValueA[2 * bytLredGreenBlueIndex]
																												: 17 * intLindexedValueA[bytLredGreenBlueIndex],
																								bytPgammaCorrection);
				}
				return new Color(intLresultColorA[0], intLresultColorA[1], intLresultColorA[2]);
			}
		}
		return null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param fltPnumber
	 * @return
	 */
	final public static float getSign(float fltPnumber) {
		return (fltPnumber >= 0F ? 1F : -1F);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPnumber
	 * @return
	 */
	final public static byte getSign(int intPnumber) {
		return (byte) (intPnumber >= 0 ? 1 : -1);
	}

	final public static byte getSignedBoolean(boolean bolPboolean) {
		return (byte) (bolPboolean ? 1 : -1);
	}

	final public static byte getSignedBoolean(Boolean bolPBoolean) {
		return (byte) (bolPBoolean == null ? 0 : bolPBoolean ? 1 : -1);
	}

	final public static byte getSignedCharValue(char chrP) {
		return (byte) (chrP == '+' || chrP == '#' ? 1 : -1);
	}

	final public static char getSignedValueChar(double lngGvalue) {
		return lngGvalue < 0L ? '-' : lngGvalue == 0L ? ' ' : '+';
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfirstNumber
	 * @param intPsecondNumber
	 * @return
	 */
	final public static int getSmallestMultiple(int intPfirstNumber, int intPsecondNumber) {
		final int intLfirstNumber = Math.abs(intPfirstNumber);
		final int intLsecondNumber = Math.abs(intPsecondNumber);
		final int intLminimumNumber = Math.min(intLfirstNumber, intLsecondNumber);
		int intLsmallestMultiple = intLminimumNumber;
		if (intLfirstNumber != intLsecondNumber) {
			final int intLmaximumNumber = Math.max(intLfirstNumber, intLsecondNumber);
			while (intLsmallestMultiple % intLmaximumNumber != 0) {
				intLsmallestMultiple += intLminimumNumber;
			}
		}
		return intLsmallestMultiple;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param fltPvalue
	 * @return
	 */
	final public static float getSquare(float fltPvalue) {
		return fltPvalue * fltPvalue;
	}

	final public static Byte getThrowByte(char chrPsiteswapChar) {
		char chrLsiteswapChar = Character.toUpperCase(chrPsiteswapChar);
		if (chrLsiteswapChar == '×') {
			chrLsiteswapChar = 'X';
		}
		if (chrLsiteswapChar >= 'A' && chrLsiteswapChar <= 'Z') {
			return (byte) ((chrLsiteswapChar - 'A') + 10);
		}
		if (chrLsiteswapChar >= '0' && chrLsiteswapChar <= '9') {
			return (byte) (chrLsiteswapChar - '0');
		}
		return null;
	}

	final public static String getThrowValueString(byte bytPvalue) {
		return Tools.getThrowValueString(bytPvalue, false);
	}

	final public static String getThrowValueString(byte bytPvalue, boolean bolPinsideMultiplex) {

		if (bytPvalue == Constants.bytS_ENGINE_ANY_NON_NULL_THROW_VALUE) {
			return bolPinsideMultiplex ? "-" : "+";
		}
		final byte bytLvalue =
								bytPvalue > Constants.bytS_ENGINE_THROWS_VALUES_NUMBER
																						? (byte) (bytPvalue - Constants.bytS_ENGINE_THROWS_VALUES_NUMBER)
																						: bytPvalue;

		return Strings.doConcat(Math.abs(bytLvalue) < 10 ? (char) ('0' + Math.abs(bytLvalue)) : (char) (('A' + Math.abs(bytLvalue)) - 10),
								bytLvalue < Constants.bytS_UNCLASS_NO_VALUE ? '\327'
																			: bytPvalue > Constants.bytS_ENGINE_THROWS_VALUES_NUMBER ? "?" : null);
	}

	final public static TitledBorder getTitledBorder(String strPtitle, Font objPfont) {
		final TitledBorder objLtitledBorder = BorderFactory.createTitledBorder(Strings.doConcat(' ', strPtitle, ' '));
		objLtitledBorder.setTitleFont(objPfont);
		return objLtitledBorder;
	}

	final public static String getZOrderString(	byte bytPzValue,
												boolean bolParmsZOrder,
												boolean bolPhandsZOrder,
												boolean bolPbodyBallsZOrder,
												boolean bolParmsBallsZOrder) {
		final StringBuilder objLstringBuilder = new StringBuilder(2);
		if (bolParmsZOrder) {
			if (bolPhandsZOrder) {
				if (bolPbodyBallsZOrder) {
					if (bolParmsBallsZOrder) {
						// objLstringBuilder.append("++");
					} else {
						objLstringBuilder.append("+±");
					}
				} else {
					if (bolParmsBallsZOrder) {
						objLstringBuilder.append("+-");
					} else {
						objLstringBuilder.append("+=");
					}
				}
			} else {
				if (bolPbodyBallsZOrder) {
					if (bolParmsBallsZOrder) {
						objLstringBuilder.append("±");
					} else {
						objLstringBuilder.append("‡‡");
					}
				} else {
					if (bolParmsBallsZOrder) {
						objLstringBuilder.append("±-");
					} else {
						objLstringBuilder.append("±=");
					}
				}
			}
		} else {
			if (bolPhandsZOrder) {
				if (bolPbodyBallsZOrder) {
					if (bolParmsBallsZOrder) {
						objLstringBuilder.append("-");
					} else {
						objLstringBuilder.append("-±");
					}
				} else {
					if (bolParmsBallsZOrder) {
						if (bytPzValue == 0) {
							objLstringBuilder.append("--");
						}
					} else {
						objLstringBuilder.append("-=");
					}
				}
			} else {
				if (bolPbodyBallsZOrder) {
					if (bolParmsBallsZOrder) {
						objLstringBuilder.append("=");
					} else {
						objLstringBuilder.append("=±");
					}
				} else {
					if (bolParmsBallsZOrder) {
						objLstringBuilder.append("=-");
					} else {
						objLstringBuilder.append("==");
					}
				}
			}
		}
		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param chrP
	 * @return
	 */
	final public static String getZValueString(	byte bytPzValue,
																			boolean bolPbodyArmsZOrder,
																			boolean bolParmsVisible,
																			boolean bolParmsHandsZOrder,
																			boolean bolPhandsVisible,
																			boolean bolPbodyBallsZOrder,
																			boolean bolParmsBallsZOrder,
																			boolean bolPballsVisible) {
		final StringBuilder objLstringBuilder = new StringBuilder(Tools.getLength(bytPzValue));
		final boolean bolLdefault = bolPbodyArmsZOrder || !bolParmsHandsZOrder || bolPbodyBallsZOrder || !bolParmsBallsZOrder;
		objLstringBuilder.append(Tools.getSignedBoolean(bolLdefault) * Math.abs(bytPzValue));
		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPcontainer
	 * @param intPcontainedValue
	 * @return
	 */
	final public static boolean intersects(int intPcontainer, int intPcontainedValue) {
		return ((intPcontainer & intPcontainedValue) != 0);
	}

	final public static boolean isDigit(char chrP) {
		return (chrP >= '0' && chrP <= '9');
	}

	final public static boolean isEmpty(Object objPobject) {
		return (objPobject == null || objPobject instanceof String && ((String) objPobject).length() == 0 || !(objPobject instanceof String)
																												&& objPobject.toString().length() == 0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPvalue
	 * @return
	 */
	final public static boolean isFront(char chrP) {
		return chrP == '#' || chrP == '+';
	}

	final public static boolean isLightColor(Color objPcolor) {
		if (objPcolor == null) {
			return false;
		}
		return (objPcolor.getRed() + objPcolor.getGreen() + objPcolor.getBlue()) / 3 > 127;
	}

	final public static boolean isNumberStart(char chrP) {
		return ((chrP == '+') || (chrP == '-') || ((chrP >= '0') && (chrP <= '9')));
	}

	final public static boolean isSequenceChar(char chrP, boolean bolPsynchro) {
		final char chrL = Strings.getUpperCaseChar(chrP);
		return ((chrL >= '0' && chrL <= '9' || chrL >= 'A' && chrL <= 'Z') && (!bolPsynchro || chrL == 'X' || Tools.getThrowByte(chrL) % 2 == 0)
				|| (chrL == '(' || chrL == ')' || chrL == ',' || chrL == '?') && bolPsynchro || chrL == '[' || chrL == ']' || chrL == '*'
				|| chrL == ' ' || chrL == '+' || chrL == '-' || chrL == '|');
	}

	final public static boolean isSign(char chrP) {
		return chrP == '-' || chrP == '+';
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param chrP
	 * @return
	 */
	final public static boolean isSiteswapChar(char chrP) {
		char chrL = Character.toUpperCase(chrP);
		if (chrL == '×') {
			chrL = 'X';
		}
		return (chrL >= '0' && chrL <= '9' || chrL >= 'A' && chrL <= 'Z' || chrL == '(' || chrL == ')' || chrL == '[' || chrL == ']' || chrL == ','
				|| chrL == '*' || chrL == ' ');
	}

	final public static boolean isSubFront(char chrP) {
		return chrP == '#' || chrP == '-';
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPuRL
	 * @return
	 */
	final public static boolean isURLAccessible(String strPuRL) {
		try {
			final HttpURLConnection objLhTTPURLConnection = (HttpURLConnection) new URL(strPuRL).openConnection();
			objLhTTPURLConnection.connect();
			return objLhTTPURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
		} catch (final Throwable objPthrowable) {
			return false;
		}
	}

	final public static boolean isZSuffix(char chrP) {
		return chrP == '=' || chrP == '-' || chrP == '+' || chrP == '#';
	}

	final public static void log(Object... objPobjectA) {

		if (Constants.bolS_UNCLASS_LOG) {
			try {
				final FileWriter objLfileWriter = new FileWriter(Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LOG], true);
				objLfileWriter.append(Strings.doConcat(Tools.getDate(), " -- "));
				objLfileWriter.append(Strings.doConcat(objPobjectA));
				objLfileWriter.append(Strings.strS_LINE_SEPARATOR);
				objLfileWriter.flush();
				objLfileWriter.close();
			} catch (final Throwable objPthrowable) {}
		}
	}

	final public static void out(Object... objPobjectA) {

		if (Constants.bolS_UNCLASS_CONSOLE) {
			synchronized (System.out) {
				System.out.println(Strings.doConcat(objPobjectA));
				System.out.flush();
			}
		}

		if (Constants.bolS_UNCLASS_LOG) {
			Tools.log(objPobjectA);
		}
	}

	final public static void setIcons(AbstractButton objPabstractButton, Icon objPicon) {
		Tools.setIcons(objPabstractButton, objPicon, objPicon, null);
	}

	final public static void setIcons(AbstractButton objPabstractButton, Icon objPicon, Icon objProllOverIcon) {
		Tools.setIcons(objPabstractButton, objPicon, objProllOverIcon, null);
	}

	final public static void setIcons(AbstractButton objPabstractButton, Icon objPicon, Icon objProllOverIcon, Icon objPdisabledIcon) {
		final Icon objLicon = objPicon != null ? objPicon : objProllOverIcon;
		final Icon objLrollOverIcon = objProllOverIcon != null ? objProllOverIcon : objPicon;
		objPabstractButton.setIcon(objLicon);
		objPabstractButton.setSelectedIcon(objLicon);
		objPabstractButton.setPressedIcon(objLrollOverIcon);
		objPabstractButton.setRolloverIcon(objLrollOverIcon);
		objPabstractButton.setRolloverSelectedIcon(objLrollOverIcon);
		if (objPdisabledIcon != null) {
			objPabstractButton.setDisabledIcon(objPdisabledIcon);
			objPabstractButton.setDisabledSelectedIcon(objPdisabledIcon);
		}
	}

	final public static boolean sleep(long lngPmilliSeconds) {
		return Tools.sleep(lngPmilliSeconds, null);
	}

	final public static boolean sleep(long lngPmilliSeconds, String strPconsoleMessage) {
		try {
			if (lngPmilliSeconds > 0) {
				Thread.sleep(lngPmilliSeconds);
			} else {
				Thread.yield();
			}
		} catch (final Throwable objPthrowable) {
			if (strPconsoleMessage != null) {
				Tools.err(strPconsoleMessage);
			}
			return false;
		}
		return true;
	}

	final public static void trace() {
		Tools.trace((Object[]) null);
	}

	final public static void trace(Object... objPobjectA) {
		if (Constants.bolS_UNCLASS_DEBUG) {
			final Throwable objLthrowable = new Throwable();
			final StackTraceElement[] objLstackTraceElementsA = objLthrowable.getStackTrace();

			// Exclude trace(s) from the Tools class :
			// ////////////////////////////////////////
			int intLfirstTraceElementIndex = 0;
			while (intLfirstTraceElementIndex < objLstackTraceElementsA.length
					&& objLstackTraceElementsA[intLfirstTraceElementIndex].getClassName().equals(Tools.class.getName())) {
				++intLfirstTraceElementIndex;
			}

			if (intLfirstTraceElementIndex < objLstackTraceElementsA.length) {

				// Init strings :
				// ///////////////
				final int intLlastTraceElementIndex = intLfirstTraceElementIndex + 1;
				final StringBuilder objLstringBuilder = new StringBuilder((intLlastTraceElementIndex - intLfirstTraceElementIndex) * 64);

				// Build result :
				// ///////////////
				objLstringBuilder.append(Tools.getDate());
				objLstringBuilder.append(" -> ");
				objLstringBuilder.append(objLstackTraceElementsA[intLfirstTraceElementIndex]);
				objLstringBuilder.append(Strings.strS_LINE_SEPARATOR);

				// Send result to the error console :
				// ///////////////////////////////////
				if (Constants.bolS_UNCLASS_CONSOLE) {
					synchronized (System.err) {
						System.err.print(objLstringBuilder);
						if (objPobjectA != null) {
							System.err.println(objPobjectA);
						}
						System.err.flush();
					}
				}

				// Write result to file log :
				// ///////////////////////////
				if (Constants.bolS_UNCLASS_LOG) {
					Tools.log(Strings.doConcat(objLstringBuilder), Strings.strS_LINE_SEPARATOR, Strings.doConcat(objPobjectA));
				}
			}
		}
	}

	final public static void traces() {

		if (Constants.bolS_UNCLASS_DEBUG) {
			final Throwable objLthrowable = new Throwable();
			final StackTraceElement[] objLstackTraceElementsA = objLthrowable.getStackTrace();

			// Exclude trace(s) from the JuggleTools class :
			// //////////////////////////////////////////////
			int intLfirstTraceElementIndex = 0;
			while (intLfirstTraceElementIndex < objLstackTraceElementsA.length
					&& objLstackTraceElementsA[intLfirstTraceElementIndex].getClassName().equals(Tools.class.getName())) {
				++intLfirstTraceElementIndex;
			}

			if (intLfirstTraceElementIndex < objLstackTraceElementsA.length) {

				// Init strings :
				// ///////////////
				final StringBuilder objLstringBuilder = new StringBuilder((objLstackTraceElementsA.length - intLfirstTraceElementIndex) * 64);
				final String strLdate = Tools.getDate();
				final String strLspaces = Strings.getSpacesString(strLdate.length());
				final String strLarrow = " -> ";

				// Build result :
				// ///////////////
				for (int intLtraceDelta = 0; intLtraceDelta < objLstackTraceElementsA.length - intLfirstTraceElementIndex; ++intLtraceDelta) {
					objLstringBuilder.append(intLtraceDelta == 0 ? strLdate : strLspaces);
					objLstringBuilder.append(strLarrow);
					objLstringBuilder.append(objLstackTraceElementsA[intLfirstTraceElementIndex + intLtraceDelta]);
					objLstringBuilder.append(Strings.strS_LINE_SEPARATOR);
				}

				// Send result to the error console :
				// ///////////////////////////////////
				if (Constants.bolS_UNCLASS_CONSOLE) {
					synchronized (System.err) {
						System.err.print(objLstringBuilder);
						System.err.flush();
					}
				}

				// Send result to file log :
				// //////////////////////////
				if (Constants.bolS_UNCLASS_LOG) {
					Tools.log(objLstringBuilder);
				}
			}
		}
	}

	final private static ResourceBundle	objS_MESSAGE_RESOURCE_BUNDLE;

	@SuppressWarnings("unused")
	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private static String			strS_BUNDLE			= "sun.print.resources.serviceui";

	static {
		ResourceBundle objLresourceBundle = null;
		try {
			objLresourceBundle = ResourceBundle.getBundle(Tools.strS_BUNDLE);
		} catch (final Throwable objPthrowable) {
			objLresourceBundle = null;
		}
		objS_MESSAGE_RESOURCE_BUNDLE = objLresourceBundle;

		// Reset debug log file :
		try {
			final FileWriter objLfileWriter = new FileWriter(Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LOG], false);
			final Date objLdate = new Date(System.currentTimeMillis());
			objLfileWriter.append(Strings.doConcat(	Tools.getJuggleMasterProInfo(),
													Strings.strS_LINE_SEPARATOR,
													(new SimpleDateFormat("dd/MM/yyyy -- kk:mm.SSS")).format(objLdate),
													Strings.strS_LINE_SEPARATOR,
													Strings.strS_LINE_SEPARATOR));
			objLfileWriter.flush();
			objLfileWriter.close();
		} catch (final Throwable objPthrowable) {}
	}

}

/*
 * @(#)JuggleTools.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
