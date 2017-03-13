/*
 * @(#)JugglePattern.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.pattern;

import java.util.Arrays;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Pattern {

	/**
	 * Constructs
	 * 
	 * @param objPpatternsManager
	 * @param intPballsNumber
	 * @param bolPplayable
	 * @param bolPlocalValueAA
	 * @param bytPlocalValueAA
	 * @param strPlocalAA
	 */
	public Pattern(	PatternsManager objPpatternsManager,
					int intPballsNumber,
					boolean bolPplayable,
					Boolean[][] bolPlocalValueAA,
					Byte[][] bytPlocalValueAA,
					String[][] strPlocalAA) {

		this.objGpatternsManager = objPpatternsManager;

		// Object definitions :
		this.intGballsNumberA = new int[2];
		this.intGballsNumberA[Constants.bytS_UNCLASS_CURRENT] = this.intGballsNumberA[Constants.bytS_UNCLASS_INITIAL] = intPballsNumber;
		this.bolGplayableA = new boolean[2];
		this.bolGplayableA[Constants.bytS_UNCLASS_CURRENT] = this.bolGplayableA[Constants.bytS_UNCLASS_INITIAL] = bolPplayable;

		this.bolGlocalValueAA = new Boolean[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER][2];
		for (byte bytLbooleanControlIndex = 0; bytLbooleanControlIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanControlIndex) {
			this.bolGlocalValueAA[bytLbooleanControlIndex][Constants.bytS_UNCLASS_CURRENT] =
																								new Boolean(bolPlocalValueAA[bytLbooleanControlIndex][Constants.bytS_UNCLASS_INITIAL]);
			this.bolGlocalValueAA[bytLbooleanControlIndex][Constants.bytS_UNCLASS_INITIAL] =
																								new Boolean(bolPlocalValueAA[bytLbooleanControlIndex][Constants.bytS_UNCLASS_INITIAL]);
		}

		this.bytGlocalValueAA = new Byte[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER][2];
		for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
			this.bytGlocalValueAA[bytLbyteControlIndex][Constants.bytS_UNCLASS_CURRENT] =
																							new Byte(bytPlocalValueAA[bytLbyteControlIndex][Constants.bytS_UNCLASS_INITIAL]);
			this.bytGlocalValueAA[bytLbyteControlIndex][Constants.bytS_UNCLASS_INITIAL] =
																							new Byte(bytPlocalValueAA[bytLbyteControlIndex][Constants.bytS_UNCLASS_INITIAL]);
		}

		this.strGlocalAA = new String[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER][2];
		for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
			this.strGlocalAA[bytLstringControlIndex][Constants.bytS_UNCLASS_CURRENT] =
																						this.strGlocalAA[bytLstringControlIndex][Constants.bytS_UNCLASS_INITIAL] =
																																									strPlocalAA[bytLstringControlIndex][Constants.bytS_UNCLASS_INITIAL];
		}
		this.bolGstarredSiteswapA = new boolean[2];
		final boolean bolLstarredSiteswap = this.isStarredSiteswap();
		Arrays.fill(this.bolGstarredSiteswapA, bolLstarredSiteswap);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doReload() {

		this.intGballsNumberA[Constants.bytS_UNCLASS_CURRENT] = this.intGballsNumberA[Constants.bytS_UNCLASS_INITIAL];

		for (byte bytLbooleanControlIndex = 0; bytLbooleanControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbooleanControlIndex) {
			this.bolGlocalValueAA[bytLbooleanControlIndex][Constants.bytS_UNCLASS_CURRENT] =
																								this.bolGlocalValueAA[bytLbooleanControlIndex][Constants.bytS_UNCLASS_INITIAL];
		}

		for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
			this.bytGlocalValueAA[bytLbyteControlIndex][Constants.bytS_UNCLASS_CURRENT] =
																							this.bytGlocalValueAA[bytLbyteControlIndex][Constants.bytS_UNCLASS_INITIAL];
		}

		for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
			this.strGlocalAA[bytLstringControlIndex][Constants.bytS_UNCLASS_CURRENT] =
																						this.strGlocalAA[bytLstringControlIndex][Constants.bytS_UNCLASS_INITIAL];
		}

		this.bolGplayableA[Constants.bytS_UNCLASS_CURRENT] = this.bolGplayableA[Constants.bytS_UNCLASS_INITIAL];
		this.bolGstarredSiteswapA[Constants.bytS_UNCLASS_CURRENT] = this.bolGstarredSiteswapA[Constants.bytS_UNCLASS_INITIAL];
	}

	final public boolean isStarredSiteswap() {
		return this.isStarredSiteswap(Constants.bytS_UNCLASS_CURRENT);
	}

	final public boolean isStarredSiteswap(byte bytPinitialOrCurrent) {
		return (bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL || bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT)
				&& new Siteswap(this.strGlocalAA[Constants.bytS_STRING_LOCAL_SITESWAP][bytPinitialOrCurrent]).isStarred();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public String toString() {
		return this.toString(Constants.bytS_EXTENSION_JMP, Constants.bytS_UNCLASS_CURRENT, true, true, false, true, false, null, null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPjMPFileFormat
	 * @param bytPinitialOrCurrent
	 * @param bolPstyle
	 * @param bolPdetailedStyle
	 * @param bolPreverseStyle
	 * @param bolPsiteswap
	 * @param bolPreverseSiteswap
	 * @param strPreverse
	 * @param strPinvalid
	 * @return
	 */
	final public String toString(	byte bytPfileFormat,
									byte bytPinitialOrCurrent,
									boolean bolPstyle,
									boolean bolPdetailedStyle,
									boolean bolPreverseStyle,
									boolean bolPsiteswap,
									boolean bolPreverseSiteswap,
									String strPreverse,
									String strPinvalid) {

		final byte bytLsiteswapControlType =
												bolPreverseSiteswap ? Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP
																	: Constants.bytS_STRING_LOCAL_SITESWAP;

		final Siteswap objLsiteswap =
										bolPsiteswap ? new Siteswap(this.strGlocalAA[Constants.bytS_STRING_LOCAL_SITESWAP][bytPinitialOrCurrent])
													: null;

		final boolean bolLquotes =
									bolPsiteswap
										&& bytPfileFormat == Constants.bytS_EXTENSION_JMP
										&& (this.strGlocalAA[bytLsiteswapControlType][bytPinitialOrCurrent].indexOf(' ') != -1 || objLsiteswap	.toString(	false,
																																							false)
																																				.equals(Strings.strS_EMPTY));

		String strLsiteswap =
								bolPreverseSiteswap ? objLsiteswap.getReverseSiteswapString(bytPfileFormat == Constants.bytS_EXTENSION_JMP,
																							false,
																							this.bolGstarredSiteswapA[bytPinitialOrCurrent])
													: objLsiteswap.toString(bytPfileFormat, false, this.bolGstarredSiteswapA[bytPinitialOrCurrent]);
		if (bytPfileFormat == Constants.bytS_EXTENSION_JM && strLsiteswap.equals(Strings.strS_EMPTY)) {
			strLsiteswap = "0";
			Tools.err("Error: empty siteswap");
		}

		final String strLpatternName =
										!Strings.unspace(this.strGlocalAA[Constants.bytS_STRING_LOCAL_PATTERN][bytPinitialOrCurrent])
												.equals(objLsiteswap.toString(false, false))
																							? Strings.doConcat(	' ',
																												this.strGlocalAA[Constants.bytS_STRING_LOCAL_PATTERN][bytPinitialOrCurrent],
																												bolPreverseSiteswap
																																	? Strings.doConcat(	" [",
																																						strPreverse,
																																						']')
																																	: null) : null;

		final String strLcomment =
									bytPfileFormat == Constants.bytS_EXTENSION_JMP
																					? Strings.doConcat(	" ;",
																										this.intGballsNumberA[bytPinitialOrCurrent] >= 0
																																						? Strings.doConcat(	Strings.strS_SPACE,
																																											this.intGballsNumberA[bytPinitialOrCurrent])
																																						: null,
																										objLsiteswap.bytGstatus != Constants.bytS_STATE_SITESWAP_PLAYABLE
																																											? Strings.doConcat(	Strings.strS_SPACE,
																																																'[',
																																																strPinvalid,
																																																']')
																																											: null)
																					: null;

		return Strings.doConcat(bolPstyle
											? this.objGpatternsManager	.getStyle(this.strGlocalAA[Constants.bytS_STRING_LOCAL_STYLE][bytPinitialOrCurrent])
																		.toString(bytPfileFormat, bolPdetailedStyle, bolPreverseStyle, strPreverse)
											: null,
								bolPsiteswap ? Strings.doConcat(bolLquotes ? '"' : null,
																strLsiteswap,
																bolLquotes ? '"' : null,
																strLpatternName,
																strLcomment,
																Strings.strS_LINE_SEPARATOR) : null);
	}

	public Boolean[][]				bolGlocalValueAA;
	public boolean[]				bolGplayableA;

	public boolean[]				bolGstarredSiteswapA;

	public Byte[][]					bytGlocalValueAA;

	public int[]					intGballsNumberA;

	private final PatternsManager	objGpatternsManager;

	public String[][]				strGlocalAA;

	@SuppressWarnings("unused")
	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	// final private static void log(Object... objPobjectA) {
	// if (JugglePattern.bolS_UNCLASS_LOG) {
	// JuggleTools.log(JuggleStrings.doConcat(objPobjectA, objPobjectA != null ? ' ' : null, JuggleTools.getTraceString()));
	// }
	// }
	//
	// /**
	// * Method description
	// *
	// * @see
	// */
	// @SuppressWarnings("unused") final private static void logIn() {
	// JugglePattern.log("In  :");
	// }
	//
	// /**
	// * Method description
	// *
	// * @see
	// */
	// @SuppressWarnings("unused") final private static void logOut() {
	// JugglePattern.log("Out :");
	// }
}

/*
 * @(#)JugglePattern.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
