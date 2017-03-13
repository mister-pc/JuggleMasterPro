/*
 * @(#)JuggleStyle.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.pattern;

import java.util.Arrays;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Style {

	final public static Style	objS_DEFAULT_JUGGLE_STYLE;

	final public static Style	objS_TET_DE_KRAN_JUGGLE_STYLE;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	static {

		objS_DEFAULT_JUGGLE_STYLE =
									new Style(	Constants.strS_STRING_LOCAL_STYLE_DEFAULT,
												Constants.bytS_ENGINE_COORDONATES_NUMBER == 4 ? new byte[] { 13, 0, 0, 0, 4, 0, 0, 0 } : new byte[] {
		13, 0, 0, 4, 0, 0						},
												(byte) 0,
												new boolean[] { true, true },
												new boolean[] { true, true },
												new boolean[] { true, true },
												new boolean[] { true, true },
												new boolean[] { true, true },
												new boolean[] { true, true },
												new boolean[] { true, true },
												false,
												false,
												false,
												true,
												false,
												true,
												false,
												true);

		final byte[] bytLtetDeKranA =
										Constants.bytS_ENGINE_COORDONATES_NUMBER == 4 ? new byte[] { 3, 11, 0, 0, 3, 11, 0, 0, 20, 20, 0, 0, 20, 20,
		0, 0, 9, 14, 0, 0, 15, 17, 0, 0, 15, 17, 0, 0, 9, 14, 0, 0, 20, 20, 0, 0, 20, 20, 0, 0, 3, 11, 0, 0, 3, 11, 0, 0, 15, 17, 0, 0, 9, 14, 0, 0,
		9, 14, 0, 0, 15, 17, 0, 0		} : new byte[] { 3, 11, 0, 3, 11, 0, 20, 20, 0, 20, 20, 0, 9, 14, 0, 15, 17, 0, 15, 17, 0, 9, 14, 0, 20, 20,
		0, 20, 20, 0, 3, 11, 0, 3, 11, 0, 15, 17, 0, 9, 14, 0, 9, 14, 0, 15, 17, 0 };
		final boolean[] bolLtetDeKranZOrderA = new boolean[bytLtetDeKranA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER];
		Arrays.fill(bolLtetDeKranZOrderA, true);
		objS_TET_DE_KRAN_JUGGLE_STYLE =
										new Style(	Constants.strS_ENGINE_TET_DE_KRAN_STYLE,
													bytLtetDeKranA,
													(byte) 0,
													bolLtetDeKranZOrderA,
													bolLtetDeKranZOrderA,
													bolLtetDeKranZOrderA,
													bolLtetDeKranZOrderA,
													bolLtetDeKranZOrderA,
													bolLtetDeKranZOrderA,
													bolLtetDeKranZOrderA,
													true,
													false,
													false,
													true,
													false,
													true,
													false,
													true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	// final private static void log(Object... objPobjectA) {
	// if (JuggleStyle.bolS_UNCLASS_LOG) {
	// JuggleTools.log(JuggleTools.doConcat(objPobjectA, objPobjectA != null ? ' ' : null, JuggleTools.getTraceString()));
	// }
	// }
	//

	//
	// /**
	// * Method description
	// *
	// * @see
	// */
	// @SuppressWarnings("unused") final private static void logIn() {
	// JuggleStyle.log("In  :");
	// }
	//
	// /**
	// * Method description
	// *
	// * @see
	// */
	// @SuppressWarnings("unused") final private static void logOut() {
	// JuggleStyle.log("Out :");
	// }

	public boolean				bolGarmsBackBalls;

	public boolean[]			bolGarmsBallsZOrderA;

	public boolean				bolGarmsFrontBalls;

	public boolean[]			bolGarmsHandsZOrderA;

	public boolean[]			bolGarmsVisibleA;

	public boolean				bolGbackArms;

	public boolean				bolGbackBalls;

	public boolean[]			bolGballsVisibleA;

	public boolean[]			bolGbodyArmsZOrderA;

	public boolean[]			bolGbodyBallsZOrderA;

	public boolean				bolGfrontArms;

	public boolean				bolGfrontBalls;

	public boolean[]			bolGhandsVisibleA;

	public boolean				bolGyValues;

	public boolean				bolGzValues;

	public byte					bytGhandsMinValueZ;

	public byte[]				bytGstyleA;

	public String				strGstyle;

	/**
	 * Constructs
	 * 
	 * @param strPstyle
	 * @param bytPstyleValuesA
	 * @param bolParmsHandsZOrderA
	 * @param bolPballsZOrderA
	 * @param bolPyValues
	 * @param bolPfrontValues
	 * @param bolPbackValues
	 */
	public Style(	String strPstyle,
					byte[] bytPstyleValuesA,
					byte bytPminValueZ,
					boolean[] bolPbodyArmsZOrderA,
					boolean[] bolParmsVisibleA,
					boolean[] bolParmsHandsZOrderA,
					boolean[] bolPhandsVisibleA,
					boolean[] bolPbodyBallsZOrderA,
					boolean[] bolParmsBallsZOrderA,
					boolean[] bolPballsVisibleA,
					boolean bolPyValues,
					boolean bolPzValues,
					boolean bolPbackArms,
					boolean bolPfrontArms,
					boolean bolPbackBalls,
					boolean bolPfrontBalls,
					boolean bolParmsBackBalls,
					boolean bolParmsFrontBalls) {

		// Constructor for creation :
		this.strGstyle = strPstyle;
		if (bytPstyleValuesA.length > 0) {
			this.bytGstyleA = new byte[bytPstyleValuesA.length];
			System.arraycopy(bytPstyleValuesA, 0, this.bytGstyleA, 0, bytPstyleValuesA.length);
			this.bolGbodyArmsZOrderA = new boolean[bolPbodyArmsZOrderA.length];
			System.arraycopy(bolPbodyArmsZOrderA, 0, this.bolGbodyArmsZOrderA, 0, bolPbodyArmsZOrderA.length);
			this.bolGarmsHandsZOrderA = new boolean[bolParmsHandsZOrderA.length];
			System.arraycopy(bolParmsHandsZOrderA, 0, this.bolGarmsHandsZOrderA, 0, bolParmsHandsZOrderA.length);
			this.bolGbodyBallsZOrderA = new boolean[bolPbodyBallsZOrderA.length];
			System.arraycopy(bolPbodyBallsZOrderA, 0, this.bolGbodyBallsZOrderA, 0, bolPbodyBallsZOrderA.length);
			this.bolGarmsBallsZOrderA = new boolean[bolParmsBallsZOrderA.length];
			System.arraycopy(bolParmsBallsZOrderA, 0, this.bolGarmsBallsZOrderA, 0, bolParmsBallsZOrderA.length);
			this.bolGarmsVisibleA = new boolean[bolParmsVisibleA.length];
			System.arraycopy(bolParmsVisibleA, 0, this.bolGarmsVisibleA, 0, bolParmsVisibleA.length);
			this.bolGhandsVisibleA = new boolean[bolPhandsVisibleA.length];
			System.arraycopy(bolPhandsVisibleA, 0, this.bolGhandsVisibleA, 0, bolPhandsVisibleA.length);
			this.bolGballsVisibleA = new boolean[bolPballsVisibleA.length];
			System.arraycopy(bolPballsVisibleA, 0, this.bolGballsVisibleA, 0, bolPballsVisibleA.length);
		} else {
			this.bytGstyleA = null;
			this.bolGbodyArmsZOrderA = null;
			this.bolGarmsHandsZOrderA = null;
			this.bolGbodyBallsZOrderA = null;
			this.bolGarmsBallsZOrderA = null;
			this.bolGarmsVisibleA = null;
			this.bolGhandsVisibleA = null;
			this.bolGballsVisibleA = null;
		}
		this.bytGhandsMinValueZ = bytPminValueZ;
		this.bolGyValues = bolPyValues;
		this.bolGzValues = bolPzValues;
		this.bolGbackArms = bolPbackArms;
		this.bolGfrontArms = bolPfrontArms;
		this.bolGbackBalls = bolPbackBalls;
		this.bolGfrontBalls = bolPfrontBalls;
		this.bolGarmsBackBalls = bolParmsBackBalls;
		this.bolGarmsFrontBalls = bolParmsFrontBalls;
	}

	/**
	 * Constructs
	 * 
	 * @param objPstyle
	 * @param bolPnoReverse
	 */
	public Style(Style objPstyle, boolean bolPnoReverse) {

		// Constructor by copy :
		this.strGstyle = objPstyle.strGstyle;
		this.bytGstyleA = objPstyle.getStyleA(bolPnoReverse);
		this.bolGyValues = objPstyle.bolGyValues;
		this.bolGzValues = objPstyle.bolGzValues;
		this.bytGhandsMinValueZ = objPstyle.bytGhandsMinValueZ;
		this.bolGbackArms = objPstyle.bolGbackArms;
		this.bolGfrontArms = objPstyle.bolGfrontArms;
		this.bolGarmsBackBalls = objPstyle.bolGarmsBackBalls;
		this.bolGarmsFrontBalls = objPstyle.bolGarmsFrontBalls;
		this.bolGfrontBalls = objPstyle.bolGfrontBalls;
		this.bolGbackBalls = objPstyle.bolGbackBalls;
		this.bolGbodyBallsZOrderA = objPstyle.getBodyBallsZOrders(bolPnoReverse);
		this.bolGarmsBallsZOrderA = objPstyle.getArmsBallsZOrders(bolPnoReverse);
		this.bolGbodyArmsZOrderA = objPstyle.getBodyArmsZOrders(bolPnoReverse);
		this.bolGarmsHandsZOrderA = objPstyle.getArmsHandsZOrders(bolPnoReverse);
		this.bolGarmsVisibleA = objPstyle.getArmsVisible(bolPnoReverse);
		this.bolGhandsVisibleA = objPstyle.getHandsVisible(bolPnoReverse);
		this.bolGballsVisibleA = objPstyle.getBallsVisible(bolPnoReverse);
	}

	/**
	 * Constructs
	 * 
	 * @param objPstyle
	 * @param intPminimumLinesNumber
	 */
	public Style(Style objPstyle, int intPminimumLinesNumber) {

		// Constructor by copy,
		// possibly by multiplying the original style
		// so that the final line number is greater than intLminimumLinesNumber :
		this.strGstyle = objPstyle.strGstyle;
		this.bolGyValues = objPstyle.bolGyValues;
		this.bolGzValues = objPstyle.bolGzValues;
		this.bytGhandsMinValueZ = objPstyle.bytGhandsMinValueZ;
		this.bolGbackArms = objPstyle.bolGbackArms;
		this.bolGfrontArms = objPstyle.bolGfrontArms;
		this.bolGbackBalls = objPstyle.bolGbackBalls;
		this.bolGfrontBalls = objPstyle.bolGfrontBalls;
		this.bolGarmsBackBalls = objPstyle.bolGarmsBackBalls;
		this.bolGarmsFrontBalls = objPstyle.bolGarmsFrontBalls;

		final int intLratio = intPminimumLinesNumber / (objPstyle.bytGstyleA.length / (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER));
		this.bytGstyleA = new byte[objPstyle.bytGstyleA.length * intLratio];
		this.bolGbodyBallsZOrderA = new boolean[objPstyle.bolGbodyBallsZOrderA.length * intLratio];
		this.bolGarmsBallsZOrderA = new boolean[objPstyle.bolGarmsBallsZOrderA.length * intLratio];
		this.bolGbodyArmsZOrderA = new boolean[objPstyle.bolGbodyArmsZOrderA.length * intLratio];
		this.bolGarmsHandsZOrderA = new boolean[objPstyle.bolGarmsHandsZOrderA.length * intLratio];
		this.bolGarmsVisibleA = new boolean[objPstyle.bolGarmsVisibleA.length * intLratio];
		this.bolGhandsVisibleA = new boolean[objPstyle.bolGhandsVisibleA.length * intLratio];
		this.bolGballsVisibleA = new boolean[objPstyle.bolGballsVisibleA.length * intLratio];

		for (int intLindex = 0; intLindex < intLratio; ++intLindex) {
			System.arraycopy(objPstyle.bytGstyleA, 0, this.bytGstyleA, objPstyle.bytGstyleA.length * intLindex, objPstyle.bytGstyleA.length);
			System.arraycopy(	objPstyle.bolGbodyBallsZOrderA,
								0,
								this.bolGbodyBallsZOrderA,
								objPstyle.bolGbodyBallsZOrderA.length * intLindex,
								objPstyle.bolGbodyBallsZOrderA.length);
			System.arraycopy(	objPstyle.bolGarmsBallsZOrderA,
								0,
								this.bolGarmsBallsZOrderA,
								objPstyle.bolGarmsBallsZOrderA.length * intLindex,
								objPstyle.bolGarmsBallsZOrderA.length);
			System.arraycopy(	objPstyle.bolGbodyArmsZOrderA,
								0,
								this.bolGbodyArmsZOrderA,
								objPstyle.bolGbodyArmsZOrderA.length * intLindex,
								objPstyle.bolGbodyArmsZOrderA.length);
			System.arraycopy(	objPstyle.bolGarmsHandsZOrderA,
								0,
								this.bolGarmsHandsZOrderA,
								objPstyle.bolGarmsHandsZOrderA.length * intLindex,
								objPstyle.bolGarmsHandsZOrderA.length);

			System.arraycopy(	objPstyle.bolGarmsVisibleA,
								0,
								this.bolGarmsVisibleA,
								objPstyle.bolGarmsVisibleA.length * intLindex,
								objPstyle.bolGarmsVisibleA.length);
			System.arraycopy(	objPstyle.bolGhandsVisibleA,
								0,
								this.bolGhandsVisibleA,
								objPstyle.bolGhandsVisibleA.length * intLindex,
								objPstyle.bolGhandsVisibleA.length);
			System.arraycopy(	objPstyle.bolGballsVisibleA,
								0,
								this.bolGballsVisibleA,
								objPstyle.bolGballsVisibleA.length * intLindex,
								objPstyle.bolGballsVisibleA.length);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@SuppressWarnings("unused") final private void doReverse() {
		this.bytGstyleA = this.getStyleA(false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPstyle
	 * @return
	 */
	@Override final public boolean equals(Object objPobject) {
		return objPobject instanceof Style ? this.equals((Style) objPobject, true) : false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPstyle
	 * @param bolPnoReverse
	 * @return
	 */
	final public boolean equals(Style objPstyle, boolean bolPnoReverse) {

		if ((objPstyle == null) || (this.bytGstyleA.length != objPstyle.bytGstyleA.length)) {
			return false;
		}

		if (this.bytGhandsMinValueZ != objPstyle.bytGhandsMinValueZ || this.bolGyValues != objPstyle.bolGyValues
			|| this.bolGzValues != objPstyle.bolGzValues || this.bolGbackArms != objPstyle.bolGbackArms
			|| this.bolGfrontArms != objPstyle.bolGfrontArms || this.bolGbackBalls != objPstyle.bolGbackBalls
			|| this.bolGfrontBalls != objPstyle.bolGfrontBalls || this.bolGarmsBackBalls != objPstyle.bolGarmsBackBalls
			|| this.bolGarmsFrontBalls != objPstyle.bolGarmsFrontBalls) {
			return false;
		}
		final byte[] bytLstyleA = objPstyle.getStyleA(bolPnoReverse);
		if (!Arrays.equals(bytLstyleA, this.bytGstyleA)) {
			return false;
		}

		final boolean[] bolLbodyArmsZOrderA = objPstyle.getBodyArmsZOrders(bolPnoReverse);
		if (!Arrays.equals(bolLbodyArmsZOrderA, this.bolGbodyArmsZOrderA)) {
			return false;
		}

		final boolean[] bolLarmsHandsZOrderA = objPstyle.getArmsHandsZOrders(bolPnoReverse);
		if (!Arrays.equals(bolLarmsHandsZOrderA, this.bolGarmsHandsZOrderA)) {
			return false;
		}

		final boolean[] bolLarmsVisibleA = objPstyle.getArmsVisible(bolPnoReverse);
		if (!Arrays.equals(bolLarmsVisibleA, this.bolGarmsVisibleA)) {
			return false;
		}

		final boolean[] bolLhandsVisibleA = objPstyle.getHandsVisible(bolPnoReverse);
		if (!Arrays.equals(bolLhandsVisibleA, this.bolGhandsVisibleA)) {
			return false;
		}

		final boolean[] bolLballsVisibleA = objPstyle.getBallsVisible(bolPnoReverse);
		if (!Arrays.equals(bolLballsVisibleA, this.bolGballsVisibleA)) {
			return false;
		}

		final boolean[] bolLarmsBallsZOrderA = objPstyle.getArmsBallsZOrders(bolPnoReverse);
		if (!Arrays.equals(bolLarmsBallsZOrderA, this.bolGarmsBallsZOrderA)) {
			return false;
		}

		final boolean[] bolLbodyBallsZOrderA = objPstyle.getBodyBallsZOrders(bolPnoReverse);
		return (Arrays.equals(bolLbodyBallsZOrderA, this.bolGbodyBallsZOrderA));
	}

	final private boolean[] getArmsBallsZOrders(boolean bolPnoReverse) {
		final boolean[] bolLarmsBallsZOrderA = new boolean[this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER];
		if (bolPnoReverse) {
			System.arraycopy(this.bolGarmsBallsZOrderA, 0, bolLarmsBallsZOrderA, 0, this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER);
		} else {
			for (int intLarrayIndex = 0; intLarrayIndex < this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER; ++intLarrayIndex) {
				bolLarmsBallsZOrderA[intLarrayIndex] =
														this.bolGarmsBallsZOrderA[this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER
																					- intLarrayIndex - 1];
			}
		}
		return bolLarmsBallsZOrderA;
	}

	final private boolean[] getArmsHandsZOrders(boolean bolPnoReverse) {
		final int intLzStyleLength = this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER;
		final boolean[] bolLarmsHandsZOrderA = new boolean[intLzStyleLength];
		if (bolPnoReverse) {
			System.arraycopy(this.bolGarmsHandsZOrderA, 0, bolLarmsHandsZOrderA, 0, intLzStyleLength);
		} else {
			for (int intLarrayIndex = 0; intLarrayIndex < intLzStyleLength; ++intLarrayIndex) {
				bolLarmsHandsZOrderA[intLarrayIndex] = this.bolGarmsHandsZOrderA[intLzStyleLength - intLarrayIndex - 1];
			}
		}
		return bolLarmsHandsZOrderA;
	}

	final private boolean[] getArmsVisible(boolean bolPnoReverse) {
		final int intLzStyleLength = this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER;
		final boolean[] bolLarmsVisibleA = new boolean[intLzStyleLength];
		if (bolPnoReverse) {
			System.arraycopy(this.bolGarmsVisibleA, 0, bolLarmsVisibleA, 0, intLzStyleLength);
		} else {
			for (int intLarrayIndex = 0; intLarrayIndex < intLzStyleLength; ++intLarrayIndex) {
				bolLarmsVisibleA[intLarrayIndex] = this.bolGarmsVisibleA[intLzStyleLength - intLarrayIndex - 1];
			}
		}
		return bolLarmsVisibleA;
	}

	final public float getArmWidth(float fltPvalueZ, byte bytParmIndex, boolean bolPfrontHand) {
		if (fltPvalueZ >= 0F) {
			return bytParmIndex == 0 && bolPfrontHand ? 2.5F : 2.0F;
		}
		final float fltLminWidth = Math.max(1.5F, 2.0F - 0.5F * fltPvalueZ / this.bytGhandsMinValueZ);
		return (bytParmIndex == 0 && bolPfrontHand) ? (2.0F + fltLminWidth) / 2.0F : fltLminWidth;
	}

	final private boolean[] getBallsVisible(boolean bolPnoReverse) {
		final int intLzStyleLength = this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER;
		final boolean[] bolLballsVisibleA = new boolean[intLzStyleLength];
		if (bolPnoReverse) {
			System.arraycopy(this.bolGballsVisibleA, 0, bolLballsVisibleA, 0, intLzStyleLength);
		} else {
			for (int intLarrayIndex = 0; intLarrayIndex < intLzStyleLength; ++intLarrayIndex) {
				bolLballsVisibleA[intLarrayIndex] = this.bolGballsVisibleA[intLzStyleLength - intLarrayIndex - 1];
			}
		}
		return bolLballsVisibleA;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPnoReverse
	 * @return
	 */
	final private boolean[] getBodyArmsZOrders(boolean bolPnoReverse) {
		final int intLzStyleLength = this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER;
		final boolean[] bolLbodyArmsZOrderA = new boolean[intLzStyleLength];
		if (bolPnoReverse) {
			System.arraycopy(this.bolGbodyArmsZOrderA, 0, bolLbodyArmsZOrderA, 0, intLzStyleLength);
		} else {
			for (int intLarrayIndex = 0; intLarrayIndex < intLzStyleLength; ++intLarrayIndex) {
				bolLbodyArmsZOrderA[intLarrayIndex] = this.bolGbodyArmsZOrderA[intLzStyleLength - intLarrayIndex - 1];
			}
		}
		return bolLbodyArmsZOrderA;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPnoReverse
	 * @return
	 */
	final private boolean[] getBodyBallsZOrders(boolean bolPnoReverse) {
		final int intLzStyleLength = this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER;
		final boolean[] bolLbodyBallsZOrderA = new boolean[intLzStyleLength];
		if (bolPnoReverse) {
			System.arraycopy(this.bolGbodyBallsZOrderA, 0, bolLbodyBallsZOrderA, 0, intLzStyleLength);
		} else {
			for (int intLarrayIndex = 0; intLarrayIndex < intLzStyleLength; ++intLarrayIndex) {
				bolLbodyBallsZOrderA[intLarrayIndex] = this.bolGbodyBallsZOrderA[intLzStyleLength - intLarrayIndex - 1];
			}
		}
		return bolLbodyBallsZOrderA;
	}

	final private boolean[] getHandsVisible(boolean bolPnoReverse) {
		final int intLzStyleLength = this.bytGstyleA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER;
		final boolean[] bolLhandsVisibleA = new boolean[intLzStyleLength];
		if (bolPnoReverse) {
			System.arraycopy(this.bolGhandsVisibleA, 0, bolLhandsVisibleA, 0, intLzStyleLength);
		} else {
			for (int intLarrayIndex = 0; intLarrayIndex < intLzStyleLength; ++intLarrayIndex) {
				bolLhandsVisibleA[intLarrayIndex] = this.bolGhandsVisibleA[intLzStyleLength - intLarrayIndex - 1];
			}
		}
		return bolLhandsVisibleA;
	}

	final public String getInfo() {
		return Strings.doConcat("Y-Values : ",
								this.bolGyValues,
								" - Z-Values : ",
								this.bolGzValues,
								" - Hands Minimum Z-Value : ",
								this.bytGhandsMinValueZ,
								Strings.strS_LINE_SEPARATOR,
								" - Back Arms : ",
								this.bolGbackArms,
								" - Front Arms : ",
								this.bolGfrontArms,
								Strings.strS_LINE_SEPARATOR,
								"Back Balls : ",
								this.bolGbackBalls,
								" - Front Balls : ",
								this.bolGfrontBalls,
								Strings.strS_LINE_SEPARATOR,
								"Arms Back Balls : ",
								this.bolGarmsBackBalls,
								" - Arms Front Balls : ",
								this.bolGarmsFrontBalls,
								Strings.strS_LINE_SEPARATOR,
								this.toString(Constants.bytS_EXTENSION_JMP, true),
								Strings.strS_LINE_SEPARATOR);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPzValue
	 * @param bolPhandsZOrder
	 * @param bolPballsZOrder
	 * @return
	 */
	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPnoReverse
	 * @return
	 */
	// final private byte[] getStyleA(boolean bolPnoReverse) {
	// final byte[] bytLstyleA = new byte[this.bytGstyleA.length];
	//
	// if (bolPnoReverse) {
	// System.arraycopy(this.bytGstyleA, 0, bytLstyleA, 0, this.bytGstyleA.length);
	// } else {
	// for (int intLstyleArrayIndex = 0; intLstyleArrayIndex < this.bytGstyleA.length; intLstyleArrayIndex +=
	// Constants.bytS_ENGINE_COORDONATES_NUMBER) {
	// for (byte bytLcoordonateIndex = 0; bytLcoordonateIndex < Constants.bytS_ENGINE_COORDONATES_NUMBER; ++bytLcoordonateIndex) {
	// bytLstyleA[intLstyleArrayIndex + bytLcoordonateIndex] =
	// this.bytGstyleA[this.bytGstyleA.length - intLstyleArrayIndex
	// - Constants.bytS_ENGINE_COORDONATES_NUMBER
	// - bytLcoordonateIndex];
	// }
	// }
	// }
	//
	// return bytLstyleA;
	// }

	final private byte[] getStyleA(boolean bolPnoReverse) {
		final byte[] bytLstyleA = new byte[this.bytGstyleA.length];

		if (bolPnoReverse) {
			System.arraycopy(this.bytGstyleA, 0, bytLstyleA, 0, this.bytGstyleA.length);
		} else {
			for (int intLstyleArrayIndex = 0; intLstyleArrayIndex < this.bytGstyleA.length; intLstyleArrayIndex += 3) {
				bytLstyleA[intLstyleArrayIndex] = this.bytGstyleA[this.bytGstyleA.length - intLstyleArrayIndex - 3];
				bytLstyleA[intLstyleArrayIndex + 1] = this.bytGstyleA[this.bytGstyleA.length - intLstyleArrayIndex - 2];
				bytLstyleA[intLstyleArrayIndex + 2] = this.bytGstyleA[this.bytGstyleA.length - intLstyleArrayIndex - 1];
			}
		}

		return bytLstyleA;
	}

	@Override public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	// TODO : 4° coordonnée - facultatif
	final public boolean isJMStyle() {
		return (this.bytGstyleA.length <= 100 * Constants.bytS_ENGINE_COORDONATES_NUMBER);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public String toString() {
		return this.toString(Constants.bytS_EXTENSION_JMP, false, false, null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPjMPFileFormat
	 * @param bolPdetailed
	 * @return
	 */
	final public String toString(byte bytPfileFormat, boolean bolPdetailed) {
		return this.toString(bytPfileFormat, bolPdetailed, false, null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPjMPFileFormat
	 * @param bolPdetailed
	 * @param bolPreverse
	 * @param strPreverse
	 * @return
	 */
	final public String toString(byte bytPfileFormat, boolean bolPdetailed, boolean bolPreverse, String strPreverse) {
		final StringBuilder objLstringBuilder =
												new StringBuilder(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_STYLE][0].length()
																	+ 5 * (this.bytGstyleA.length + 1));

		// Write the style name :
		objLstringBuilder.append(bolPdetailed || bytPfileFormat == Constants.bytS_EXTENSION_JM
																								? Strings.doConcat(	'%',
																													bytPfileFormat == Constants.bytS_EXTENSION_JMP
																																									? ' '
																																									: null)
																								: Strings.doConcat(	'#',
																													Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_STYLE][0],
																													" = "));
		objLstringBuilder.append(this.strGstyle);

		if (bolPreverse) {
			objLstringBuilder.append(Strings.doConcat(" [", strPreverse, ']'));
		}

		objLstringBuilder.append(Strings.strS_LINE_SEPARATOR);

		// Write the style values :
		if (bolPdetailed) {
			final byte[] bytLstyleA = this.getStyleA(!bolPreverse);
			final int[] intLxYZStyleLengthA = new int[2 * Constants.bytS_ENGINE_COORDONATES_NUMBER];
			Arrays.fill(intLxYZStyleLengthA, 0);
			final int[] intLzOrderLengthA = new int[] { 0, 0 };

			// Set the max lengths for x, y & z :
			for (int intLxYZStyleIndex = 0; intLxYZStyleIndex < this.bytGstyleA.length; intLxYZStyleIndex +=
																												2 * Constants.bytS_ENGINE_COORDONATES_NUMBER) {
				for (byte bytLxYZStyleDelta = 0; bytLxYZStyleDelta < 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER; ++bytLxYZStyleDelta) {
					switch (bytLxYZStyleDelta % Constants.bytS_ENGINE_COORDONATES_NUMBER) {
						case 1:
							if (bytPfileFormat == Constants.bytS_EXTENSION_JMP && !this.bolGyValues && !this.bolGzValues) {
								intLxYZStyleLengthA[bytLxYZStyleDelta] = 0;
								break;
							}
							//$FALL-THROUGH$
						case 0:
							intLxYZStyleLengthA[bytLxYZStyleDelta] =
																		Math.max(	intLxYZStyleLengthA[bytLxYZStyleDelta],
																					(Byte.toString(bytLstyleA[intLxYZStyleIndex + bytLxYZStyleDelta])).length());
							break;

						// TODO : 4° coordonnée - JuggleStyle.toString
						case 2:
							if (bytPfileFormat == Constants.bytS_EXTENSION_JMP && this.bolGzValues) {
								final int intLzStyleIndex = (intLxYZStyleIndex + bytLxYZStyleDelta) / Constants.bytS_ENGINE_COORDONATES_NUMBER;
								intLxYZStyleLengthA[bytLxYZStyleDelta] =
																			Math.max(	intLxYZStyleLengthA[bytLxYZStyleDelta],
																						Tools	.getZValueString(	bytLstyleA[intLxYZStyleIndex
																																+ bytLxYZStyleDelta],
																													this.bolGbodyArmsZOrderA[intLzStyleIndex],
																													this.bolGarmsVisibleA[intLzStyleIndex],
																													this.bolGarmsHandsZOrderA[intLzStyleIndex],
																													this.bolGhandsVisibleA[intLzStyleIndex],
																													this.bolGbodyBallsZOrderA[intLzStyleIndex],
																													this.bolGarmsBallsZOrderA[intLzStyleIndex],
																													this.bolGballsVisibleA[intLzStyleIndex])
																								.length());
							} else {
								intLxYZStyleLengthA[bytLxYZStyleDelta] = 0;
							}
					}
				}
			}

			// Set the max lengths for the Z-order suffixes :
			if (bytPfileFormat == Constants.bytS_EXTENSION_JMP && this.bolGzValues) {
				for (int intLzOrderIndex = 0; intLzOrderIndex < this.bolGbodyArmsZOrderA.length
												&& (intLzOrderLengthA[0] < 2 || intLzOrderLengthA[1] < 2); ++intLzOrderIndex) {
					intLzOrderLengthA[intLzOrderIndex % 2] =
																Math.max(	intLzOrderLengthA[intLzOrderIndex % 2],
																			Tools	.getZOrderString(	bytLstyleA[intLzOrderIndex
																													* Constants.bytS_ENGINE_COORDONATES_NUMBER
																													+ 2],
																										this.bolGbodyArmsZOrderA[intLzOrderIndex],
																										this.bolGarmsHandsZOrderA[intLzOrderIndex],
																										this.bolGbodyBallsZOrderA[intLzOrderIndex],
																										this.bolGarmsBallsZOrderA[intLzOrderIndex])
																					.length());
				}
			}

			// Write each line of the style :
			for (int intLxYZStyleIndex = 0; intLxYZStyleIndex < this.bytGstyleA.length; intLxYZStyleIndex +=
																												2 * Constants.bytS_ENGINE_COORDONATES_NUMBER) {

				// Write each data of the line :
				for (byte bytLxYZStyleDelta = 0; bytLxYZStyleDelta < 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER; ++bytLxYZStyleDelta) {
					if (bytPfileFormat == Constants.bytS_EXTENSION_JMP && !this.bolGyValues && !this.bolGzValues) {
						if (bytLxYZStyleDelta == 1) {
							bytLxYZStyleDelta = Constants.bytS_ENGINE_COORDONATES_NUMBER;
						} else if (bytLxYZStyleDelta == Constants.bytS_ENGINE_COORDONATES_NUMBER + 1) {
							break;
						}
					}
					if (bytPfileFormat == Constants.bytS_EXTENSION_JM || !this.bolGzValues) {
						if (bytLxYZStyleDelta == 2) {
							bytLxYZStyleDelta = Constants.bytS_ENGINE_COORDONATES_NUMBER;
						} else if (bytLxYZStyleDelta == Constants.bytS_ENGINE_COORDONATES_NUMBER + 2) {
							break;
						}
					}

					// Write prefixes :
					// TODO : 4° coordonnée - JuggleStyle.toString
					switch (bytLxYZStyleDelta) {
						case 0:
							objLstringBuilder.append('{');
							break;

						case 3:
							objLstringBuilder.append("}{");
							break;

						case 1:
						case 2:
						case 4:
						case 5:
							objLstringBuilder.append(", ");
							break;
					}

					// Write the values :
					if (bytLxYZStyleDelta % Constants.bytS_ENGINE_COORDONATES_NUMBER == 2) {
						final int intLzStyleIndex = (intLxYZStyleIndex + bytLxYZStyleDelta) / Constants.bytS_ENGINE_COORDONATES_NUMBER;
						objLstringBuilder.append(Strings.doConcat(	Strings.getFixedLengthString(	Tools.getZValueString(	bytLstyleA[intLxYZStyleIndex
																																		+ bytLxYZStyleDelta],
																															this.bolGbodyArmsZOrderA[intLzStyleIndex],
																															this.bolGarmsVisibleA[intLzStyleIndex],
																															this.bolGarmsHandsZOrderA[intLzStyleIndex],
																															this.bolGhandsVisibleA[intLzStyleIndex],
																															this.bolGbodyBallsZOrderA[intLzStyleIndex],
																															this.bolGarmsBallsZOrderA[intLzStyleIndex],
																															this.bolGballsVisibleA[intLzStyleIndex]),
																									intLxYZStyleLengthA[bytLxYZStyleDelta],
																									true),
																	Strings.getFixedLengthString(	Tools.getZOrderString(	bytLstyleA[intLxYZStyleIndex
																																		+ bytLxYZStyleDelta],
																															this.bolGbodyArmsZOrderA[intLzStyleIndex],
																															this.bolGarmsHandsZOrderA[intLzStyleIndex],
																															this.bolGbodyBallsZOrderA[intLzStyleIndex],
																															this.bolGarmsBallsZOrderA[intLzStyleIndex]),
																									intLzOrderLengthA[bytLxYZStyleDelta
																														/ Constants.bytS_ENGINE_COORDONATES_NUMBER],
																									false)));
					} else {
						objLstringBuilder.append(Strings.getValueString(bytLstyleA[intLxYZStyleIndex + bytLxYZStyleDelta],
																		0,
																		0,
																		intLxYZStyleLengthA[bytLxYZStyleDelta],
																		true));
					}
				}
				objLstringBuilder.append('}');
				objLstringBuilder.append(Strings.strS_LINE_SEPARATOR);
			}
		}
		objLstringBuilder.trimToSize();
		// Tools.verbose("Style brut : ");
		// Tools.verbose("bolGbodyArmsZOrderA : ", bolGbodyArmsZOrderA);
		// Tools.verbose("bolGarmsHandsZOrderA : ", bolGarmsHandsZOrderA);
		// Tools.verbose("bolGbodyBallsZOrderA : ", bolGbodyBallsZOrderA);
		// Tools.verbose("bolGarmsBallsZOrderA : ", bolGarmsBallsZOrderA);
		return objLstringBuilder.toString();
	}
}

/*
 * @(#)JuggleStyle.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
