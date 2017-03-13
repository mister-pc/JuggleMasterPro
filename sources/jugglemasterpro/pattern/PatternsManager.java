/*
 * @(#)JugglePatternsManager.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.ImageIcon;

import jugglemasterpro.control.util.MenusTools;
import jugglemasterpro.pattern.io.PatternsFile;
import jugglemasterpro.pattern.io.PatternsFilesManager;
import jugglemasterpro.user.Language;
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
final public class PatternsManager implements Comparator<Object> {

	/**
	 * Constructs
	 * 
	 * @param bytPpatternsManagerType
	 */
	public PatternsManager(byte bytPpatternsManagerType) {
		this(bytPpatternsManagerType, null, null, true, true, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	/**
	 * Constructs
	 * 
	 * @param bytPpatternsManagerType
	 * @param objPpatternsBufferedReader
	 * @param strPtitle
	 */
	public PatternsManager(byte bytPpatternsManagerType, PatternsFile objPpatternsBufferedReader, ImageIcon imgP) {
		this(bytPpatternsManagerType, objPpatternsBufferedReader, imgP, true, true, null, 0);
	}

	/**
	 * Constructs
	 * 
	 * @param bytPpatternsManagerType
	 * @param objPpatternsBufferedReader
	 * @param strPtitle
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 * @param strPpattern
	 * @param intPstartPatternOccurrence
	 */
	public PatternsManager(	byte bytPpatternsManagerType,
							PatternsFile objPpatternsFile,
							ImageIcon imgP,
							boolean bolPsiteswaps,
							boolean bolPstyles,
							String strPpattern,
							int intPstartPatternOccurrence) {

		this.bytGpatternsManagerType = bytPpatternsManagerType;
		this.objGpatternsFileManager = new PatternsFilesManager(this);
		this.setDefaultProperties();
		this.initInitialValues(true, objPpatternsFile == null ? Constants.bytS_EXTENSION_JMP : objPpatternsFile.getFileFormat());

		switch (bytPpatternsManagerType) {
			case Constants.bytS_MANAGER_NEW_ABSTRACT_LANGUAGE:
				this.doCreateNewLanguagePatternsManager();
				return;

			case Constants.bytS_MANAGER_JM_PATTERN:
				this.doCreateJMPatternManager();
				break;

			case Constants.bytS_MANAGER_NEW_PATTERN:
				this.doCreateNewPatternManager();
				break;

			case Constants.bytS_MANAGER_FILES_PATTERNS:
				this.objGpatternsFileManager.doLoadPatternsFile(	objPpatternsFile,
																	imgP,
																	true,
																	bolPsiteswaps,
																	bolPstyles,
																	strPpattern,
																	false,
																	intPstartPatternOccurrence);
				break;

			case Constants.bytS_MANAGER_NO_PATTERN:
			default:
				this.doCreateNoPatternManager();
				break;
		}
		this.initCurrentValues();
		// this.log(this.getInfo());
	}

	/**
	 * Constructs
	 * 
	 * @param bytPpatternsManagerType
	 * @param objPpatternsBufferedReader
	 * @param strPtitle
	 * @param strPpattern
	 */
	public PatternsManager(byte bytPpatternsManagerType, PatternsFile objPpatternsBufferedReader, ImageIcon imgP, String strPpattern) {
		this(bytPpatternsManagerType, objPpatternsBufferedReader, imgP, true, true, strPpattern, strPpattern != null ? 1 : 0);
	}

	/**
	 * Constructs
	 * 
	 * @param bytPpatternsManagerType
	 * @param objPpatternsBufferedReader
	 * @param strPtitle
	 * @param strPpattern
	 * @param intPstartPatternOccurrence
	 */
	public PatternsManager(	byte bytPpatternsManagerType,
							PatternsFile objPpatternsBufferedReader,
							ImageIcon imgP,
							String strPpattern,
							int intPstartPatternOccurrence) {
		this(bytPpatternsManagerType, objPpatternsBufferedReader, imgP, true, true, strPpattern, intPstartPatternOccurrence);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPvalue
	 * @param bolPjMPFileFormat
	 * @return
	 */
	final public static String getByteParameterValueString(byte bytPcontrolType, byte bytPvalue, byte bytPfileFormat) {
		final StringBuilder objLstringBuilder = new StringBuilder(8);

		switch (bytPcontrolType) {
			case Constants.bytS_BYTE_LOCAL_SPEED:
				objLstringBuilder.append(Strings.getValueString(bytPvalue / 10F, 1, 1, 3, false));
				break;

			case Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED:
				objLstringBuilder.append(Strings.doConcat(bytPvalue * 10, '%'));
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
				switch (bytPvalue) {
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM:
						objLstringBuilder.append("phantom");

						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS:
						objLstringBuilder.append("hands");

						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS:
						objLstringBuilder.append("arms");

						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS:
						objLstringBuilder.append("arms hands");

						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD:
						objLstringBuilder.append("head");

						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS:
						objLstringBuilder.append("head hands");

						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS:
						objLstringBuilder.append("head arms");

						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL:
						objLstringBuilder.append("all");

						break;
				}

				break;

			case Constants.bytS_BYTE_LOCAL_SKILL:
				switch (bytPvalue) {
					case Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER:
						objLstringBuilder.append("beginner");
						break;

					case Constants.bytS_BYTE_LOCAL_SKILL_AMATEUR:
						objLstringBuilder.append("amateur");
						break;

					case Constants.bytS_BYTE_LOCAL_SKILL_CONFIRMED:
						objLstringBuilder.append("confirmed");
						break;

					case Constants.bytS_BYTE_LOCAL_SKILL_PRO:
						objLstringBuilder.append("pro");
						break;

					case Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE:
						objLstringBuilder.append("impossible");
						break;
				}
				break;

			case Constants.bytS_BYTE_LOCAL_HEIGHT:
			case Constants.bytS_BYTE_LOCAL_FLUIDITY:
			case Constants.bytS_BYTE_LOCAL_DWELL:
				objLstringBuilder.append(Strings.getValueString(bytPvalue / (bytPfileFormat == Constants.bytS_EXTENSION_JMP ? 1F : 100F),
																bytPfileFormat == Constants.bytS_EXTENSION_JMP ? 2 : 1,
																bytPfileFormat == Constants.bytS_EXTENSION_JMP ? 0 : 2,
																bytPfileFormat == Constants.bytS_EXTENSION_JMP ? 2 : 4,
																false));

				break;

			case Constants.bytS_BYTE_LOCAL_DEFAULTS:
				objLstringBuilder.append(bytPvalue);

				break;

			case Constants.bytS_BYTE_LOCAL_STROBE:
				switch (bytPvalue) {
					case Constants.bytS_BYTE_LOCAL_STROBE_EACH_COUNT:
						objLstringBuilder.append("count");
						break;
					case Constants.bytS_BYTE_LOCAL_STROBE_EACH_CATCH:
						objLstringBuilder.append("catch");
						break;
					default:
						objLstringBuilder.append(Strings.doConcat('/', bytPvalue));
						break;
				}
				break;

			case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
				if ((bytPvalue < Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL) && (bytPvalue >= Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE)) {
					objLstringBuilder.append(bytPvalue);
				}

				if (bytPvalue == Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL) {
					objLstringBuilder.append("trail");
				}

				break;

			case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
				if (bytPvalue == Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH) {
					objLstringBuilder.append("catch");
				} else if (bytPvalue == Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_COUNT) {
					objLstringBuilder.append("count");
				} else if (bytPvalue <= Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE) {
					objLstringBuilder.append(Strings.doConcat('/', Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE - bytPvalue + 1));
				}

				break;
		}

		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPfirstParameterObject
	 * @param objPsecondParameterObject
	 * @return
	 */
	@Override public final int compare(Object objPfirstParameterObject, Object objPsecondParameterObject) {

		final String[] strLparameterA = new String[2];

		strLparameterA[0] = objPfirstParameterObject.toString().toLowerCase();
		strLparameterA[1] = objPsecondParameterObject.toString().toLowerCase();

		// Set parameter name & value :
		final String[] strLparameterNameA = new String[2];
		final String[] strLparameterValueA = new String[2];

		for (byte bytLparameterIndex = 0; bytLparameterIndex < 2; ++bytLparameterIndex) {
			final int intLequalSymbolGndex = strLparameterA[bytLparameterIndex].indexOf('=');

			if (intLequalSymbolGndex == -1) {
				strLparameterNameA[bytLparameterIndex] = strLparameterA[bytLparameterIndex].trim();
				strLparameterValueA[bytLparameterIndex] = Strings.strS_EMPTY;
			} else {
				strLparameterNameA[bytLparameterIndex] = strLparameterA[bytLparameterIndex].substring(0, intLequalSymbolGndex).trim();
				strLparameterValueA[bytLparameterIndex] = strLparameterA[bytLparameterIndex].substring(intLequalSymbolGndex + 1).trim();
			}
		}

		// Different parameter names :
		if (!strLparameterNameA[0].equals(strLparameterNameA[1])) {
			return strLparameterA[0].compareTo(strLparameterA[1]);
		}

		// Initializations :
		final byte bytL_GLOBAL = 0;
		final byte bytL_LOCAL = 1;
		final byte bytL_TRUE = 2;
		final byte bytL_FALSE = 3;
		final byte bytL_VALUE = 4;
		final String[] strL_VALUE_A = new String[bytL_VALUE];

		strL_VALUE_A[bytL_GLOBAL] = "global";
		strL_VALUE_A[bytL_LOCAL] = "local";
		strL_VALUE_A[bytL_TRUE] = "true";
		strL_VALUE_A[bytL_FALSE] = "false";

		// Find parameter value types :
		final byte[] bytLparameterValueType = new byte[2];

		bytLparameterValueType[0] = bytLparameterValueType[1] = bytL_VALUE;

		for (byte bytLparameterIndex = 0; bytLparameterIndex < 2; ++bytLparameterIndex) {
			for (byte bytLvalueType = bytL_LOCAL; (bytLvalueType < bytL_VALUE) && (bytLparameterValueType[bytLparameterIndex] == bytL_VALUE); ++bytLvalueType) {
				if (strLparameterValueA[bytLparameterIndex].equals(strL_VALUE_A[bytLvalueType])) {
					bytLparameterValueType[bytLparameterIndex] = bytLvalueType;
				}
			}
		}

		// Compare values :
		return ((bytLparameterValueType[0] < bytLparameterValueType[1])
																		? -1
																		: (bytLparameterValueType[0] > bytLparameterValueType[1])
																																	? 1
																																	: (bytLparameterValueType[0] < bytL_VALUE)
																																												? 0
																																												: strLparameterValueA[0].compareTo(strLparameterValueA[1]));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateJMPatternManager() {

		// Set initial boolean control values :
		for (byte bytLbooleanValueIndex = 0; bytLbooleanValueIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanValueIndex) {
			switch (bytLbooleanValueIndex) {
				case Constants.bytS_BOOLEAN_LOCAL_MIRROR:
				case Constants.bytS_BOOLEAN_LOCAL_SITESWAP:
				case Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP:
				case Constants.bytS_BOOLEAN_LOCAL_FX:
				case Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE:
				case Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS:
				case Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS:
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL:
				case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
				case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
				case Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS:
				case Constants.bytS_BOOLEAN_LOCAL_FLASH:
				case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
				case Constants.bytS_BOOLEAN_LOCAL_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_METRONOME:
				case Constants.bytS_BOOLEAN_LOCAL_LIGHT:
				case Constants.bytS_BOOLEAN_LOCAL_SPEED:
				case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
				case Constants.bytS_BOOLEAN_LOCAL_EDITION:
				case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
				case Constants.bytS_BOOLEAN_LOCAL_INFO:
				case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
				case Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS:
					this.bolGglobalValueAA[bytLbooleanValueIndex][Constants.bytS_UNCLASS_INITIAL] = false;
					break;
				case Constants.bytS_BOOLEAN_LOCAL_STYLE:
				case Constants.bytS_BOOLEAN_LOCAL_BALLS:
				case Constants.bytS_BOOLEAN_LOCAL_COLORS:
					this.bolGglobalValueAA[bytLbooleanValueIndex][Constants.bytS_UNCLASS_INITIAL] = true;
					break;
			}
		}

		this.bolGbooleanIsLocalA[Constants.bytS_BOOLEAN_LOCAL_EDITION] = false;

		// Set initial byte control values :
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_SPEED][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED][Constants.bytS_UNCLASS_INITIAL] =
																											Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_DEFAULT_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS][Constants.bytS_UNCLASS_INITIAL] =
																												Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_FLUIDITY][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																												Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY][Constants.bytS_UNCLASS_INITIAL] =
																												Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_HEIGHT][Constants.bytS_UNCLASS_INITIAL] = (byte) 6;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_STROBE][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_STROBE_MINIMUM_RATE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_SKILL][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_MARK][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_DWELL][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL;

		// Set initial string control values :
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_PATTERN][Constants.bytS_UNCLASS_INITIAL] = Strings.strS_EMPTY;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] = "2,2(6,2(,2(,6(2(2(6,6()()2,6(2(6(,6()2";
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] = "(,6()6(,2(,2(6,6()()6,2(,2(,6(2(2(2,2(6,2";
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL] = "JM";
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_COLORS][Constants.bytS_UNCLASS_INITIAL] = "RW";
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_REVERSE_COLORS][Constants.bytS_UNCLASS_INITIAL] = "WR";
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																											Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT][Constants.bytS_UNCLASS_INITIAL] = "FFF";
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT][Constants.bytS_UNCLASS_INITIAL] = "F00";

		// Create JM style :
		final byte[] bytLjMA =
								Constants.bytS_ENGINE_COORDONATES_NUMBER == 4 ? new byte[] { -1, 6, 0, 0, -1, 6, 0, 0, 17, 6, 0, 0, 17, 6, 0, 0, -1,
									6, 0, 0, -1, 6, 0, 0, 17, 6, 0, 0, 7, 6, 0, 0, -1, 6, 0, 0, -1, 6, 0, 0, 7, 6, 0, 0, 7, 6, 0, 0, -1, 6, 0, 0, -1,
									6, 0, 0, 7, 6, 0, 0, 7, 6, 0, 0, -1, 6, 0, 0, -1, 6, 0, 0, 7, 6, 0, 0, 17, 6, 0, 0, -1, 6, 0, 0, -1, 6, 0, 0, 17,
									6, 0, 0, 17, 6, 0, 0, 8, 6, 0, 0, 8, 6, 0, 0, 17, 6, 0, 0, 7, 6, 0, 0, 8, 6, 0, 0, 8, 6, 0, 0, 7, 6, 0, 0, 7, 6,
									0, 0, 8, 6, 0, 0, 8, 6, 0, 0, 7, 6, 0, 0, 7, 6, 0, 0, 17, 6, 0, 0, 17, 6, 0, 0, 7, 6, 0, 0, 17, 6, 0, 0, 17, 6,
									0, 0, 17, 6, 0, 0, 17, 6, 0, 0, 17, 6, 0, 0, 17, 6, 0, 0, 17, 6, 0, 0, 17, 6, 0, 0, 7, 6, 0, 0, 8, 6, 0, 0, 8, 6,
									0, 0, 7, 6, 0, 0, 7, 6, 0, 0, 8, 6, 0, 0, 8, 6, 0, 0, 7, 6, 0, 0, 7, 6, 0, 0, 8, 6, 0, 0, 8, 6, 0, 0, 7, 6, 0, 0,
									17, 6, 0, 0 } : new byte[] { -1, 6, 0, -1, 6, 0, 17, 6, 0, 17, 6, 0, -1, 6, 0, -1, 6, 0, 17, 6, 0, 7, 6, 0, -1,
									6, 0, -1, 6, 0, 7, 6, 0, 7, 6, 0, -1, 6, 0, -1, 6, 0, 7, 6, 0, 7, 6, 0, -1, 6, 0, -1, 6, 0, 7, 6, 0, 17, 6, 0,
									-1, 6, 0, -1, 6, 0, 17, 6, 0, 17, 6, 0, 8, 6, 0, 8, 6, 0, 17, 6, 0, 7, 6, 0, 8, 6, 0, 8, 6, 0, 7, 6, 0, 7, 6, 0,
									8, 6, 0, 8, 6, 0, 7, 6, 0, 7, 6, 0, 17, 6, 0, 17, 6, 0, 7, 6, 0, 17, 6, 0, 17, 6, 0, 17, 6, 0, 17, 6, 0, 17, 6,
									0, 17, 6, 0, 17, 6, 0, 17, 6, 0, 7, 6, 0, 8, 6, 0, 8, 6, 0, 7, 6, 0, 7, 6, 0, 8, 6, 0, 8, 6, 0, 7, 6, 0, 7, 6, 0,
									8, 6, 0, 8, 6, 0, 7, 6, 0, 17, 6, 0 };
		final boolean[] bolLjMZOrderA = new boolean[bytLjMA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER];
		for (int intLjMZOrderIndex = 0; intLjMZOrderIndex < bytLjMA.length / Constants.bytS_ENGINE_COORDONATES_NUMBER; ++intLjMZOrderIndex) {
			bolLjMZOrderA[intLjMZOrderIndex] = true;
		}
		final Style objLjMJuggleStyle =
										new Style(	this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL],
													bytLjMA,
													(byte) 0,
													bolLjMZOrderA,
													bolLjMZOrderA,
													bolLjMZOrderA,
													bolLjMZOrderA,
													bolLjMZOrderA,
													bolLjMZOrderA,
													bolLjMZOrderA,
													true,
													false,
													false,
													true,
													false,
													true,
													false,
													true);

		this.strGstyleAL = new ArrayList<String>(2);
		this.strGstyleAL.add(Constants.strS_STRING_LOCAL_STYLE_DEFAULT);
		this.strGstyleAL.add(this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL]);
		this.objGstylesHashMap.put(this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL], objLjMJuggleStyle);

		// Create JM pattern :
		final Pattern objLjMJugglePattern = new Pattern(this, 2, true, this.bolGglobalValueAA, this.bytGglobalValueAA, this.strGglobalAA);

		// Add JM pattern :
		final ArrayList<Object> objLobjectAL = new ArrayList<Object>(9);

		objLobjectAL.add(Strings.strS_EMPTY);
		objLobjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_JUGGLE_MASTER_PRO]);
		objLobjectAL.add(Strings.strS_EMPTY);
		objLobjectAL.add(Strings.doConcat("\251 ", Constants.strS_ENGINE_COPYRIGHT_YEARS, ' ', Constants.strS_ENGINE_ARNAUD_BELO));
		objLobjectAL.add("____________");
		objLobjectAL.add(Strings.strS_EMPTY);
		objLobjectAL.add(objLjMJugglePattern);
		this.getPatternsFileManager().initJMPatternManager(objLobjectAL.size() - 1);
		objLobjectAL.add(Strings.strS_EMPTY);
		objLobjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_CHOOSE_YOUR_PATTERNS_OR_LOOK_FOR_HELP]);
		this.objGobjectA = objLobjectAL.toArray();
		this.intGpatternsNumber = 1;
		// this.log(this.getInfo());
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateNewLanguagePatternsManager() {

		// Boolean controls :
		Arrays.fill(this.bolGbooleanIsFileDefinedA, true);
		Arrays.fill(this.bolGbooleanIsUserDefinedA, true);
		Arrays.fill(this.bolGbooleanIsLocalA, false);
		Arrays.fill(this.bolGbooleanIsLocallyDefinedA, true);
		for (byte bytLcontrolIndex = 0; bytLcontrolIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLcontrolIndex) {
			this.bolGglobalValueAA[bytLcontrolIndex][Constants.bytS_UNCLASS_CURRENT] = true;
			this.bolGglobalValueAA[bytLcontrolIndex][Constants.bytS_UNCLASS_INITIAL] = false;
		}
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_ROBOT][Constants.bytS_UNCLASS_CURRENT] = false;
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_EDITION][Constants.bytS_UNCLASS_INITIAL] = true;

		// Byte controls :
		Arrays.fill(this.bolGbyteIsFileDefinedA, true);
		Arrays.fill(this.bolGbyteIsUserDefinedA, true);
		Arrays.fill(this.bolGbyteIsLocalA, false);
		Arrays.fill(this.bolGbyteIsLocallyDefinedA, true);

		for (byte bytLcontrolIndex = 0; bytLcontrolIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLcontrolIndex) {
			this.bytGglobalValueAA[bytLcontrolIndex][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_UNCLASS_NO_VALUE;
		}

		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_SPEED][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED][Constants.bytS_UNCLASS_CURRENT] =
																											Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT][Constants.bytS_UNCLASS_CURRENT] =
																												Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY][Constants.bytS_UNCLASS_CURRENT] =
																												Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_HEIGHT][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_FLUIDITY][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_STROBE][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_STROBE_EACH_COUNT;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_SKILL][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_MARK][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_DWELL][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_CURRENT] = Constants.bytS_BYTE_LOCAL_DEFAULTS_MAXIMUM_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL][Constants.bytS_UNCLASS_CURRENT] =
																										(byte) (Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL - 1);
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS][Constants.bytS_UNCLASS_CURRENT] =
																												Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_COUNT;

		// String controls :
		Arrays.fill(this.bolGstringIsFileDefinedA, true);
		Arrays.fill(this.bolGstringIsUserDefinedA, true);
		Arrays.fill(this.bolGstringIsLocalA, false);
		Arrays.fill(this.bolGstringIsLocallyDefinedA, true);

		for (byte bytLcontrolIndex = 0; bytLcontrolIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLcontrolIndex) {
			this.strGglobalAA[bytLcontrolIndex][Constants.bytS_UNCLASS_INITIAL] = Strings.strS_EMPTY;
		}
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_CURRENT] = Constants.strS_STRING_LOCAL_STYLE_DEFAULT;

		this.strGglobalAA[Constants.bytS_STRING_LOCAL_REVERSE_COLORS][Constants.bytS_UNCLASS_CURRENT] = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_PATTERN][Constants.bytS_UNCLASS_CURRENT] = Constants.strS_ENGINE_VERSION_NUMBER;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT][Constants.bytS_UNCLASS_CURRENT] =
																										Constants.strS_STRING_LOCAL_JUGGLER_NIGHT_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_DAY][Constants.bytS_UNCLASS_CURRENT] = Constants.strS_STRING_LOCAL_JUGGLER_DAY_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_COLORS][Constants.bytS_UNCLASS_CURRENT] = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT][Constants.bytS_UNCLASS_CURRENT] =
																											Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY][Constants.bytS_UNCLASS_CURRENT] =
																										Constants.strS_STRING_LOCAL_BACKGROUND_DAY_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP_DAY][Constants.bytS_UNCLASS_CURRENT] =
																										Constants.strS_STRING_LOCAL_SITESWAP_DAY_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_TOOLTIP][Constants.bytS_UNCLASS_CURRENT] = null;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT][Constants.bytS_UNCLASS_CURRENT] =
																										Constants.strS_STRING_LOCAL_SITESWAP_NIGHT_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_CURRENT] = Constants.strS_ENGINE_TET_DE_KRAN_STYLE;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_CURRENT] = "[XXX]";

		// General properties :
		this.getPatternsFileManager().initNewLanguagePatternsManager();
		this.intGpatternsNumber = 1;
		this.intGshortcutIndexA = null;

		final Pattern objLpattern = new Pattern(this, 99, false, this.bolGglobalValueAA, this.bytGglobalValueAA, this.strGglobalAA);

		this.objGobjectA = new Object[] { objLpattern };
		this.strGstyleAL = new ArrayList<String>(2);
		this.strGstyleAL.add(Constants.strS_STRING_LOCAL_STYLE_DEFAULT);
		this.strGstyleAL.add(Constants.strS_ENGINE_TET_DE_KRAN_STYLE);
		this.objGstylesHashMap.put(Constants.strS_ENGINE_TET_DE_KRAN_STYLE, Style.objS_TET_DE_KRAN_JUGGLE_STYLE);
		// Tools.verbose(this.getInfo(true));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateNewPatternManager() {

		// Set initial boolean control values :
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS][Constants.bytS_UNCLASS_INITIAL] =
																												this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][Constants.bytS_UNCLASS_INITIAL] =
																																																						this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																																																														true;
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																												this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																																						this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS][Constants.bytS_UNCLASS_INITIAL] =
																																																																																this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																										false;
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_EDITION][Constants.bytS_UNCLASS_INITIAL] =
																										this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SPEED][Constants.bytS_UNCLASS_INITIAL] =
																																																		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FLUIDITY][Constants.bytS_UNCLASS_INITIAL] =
																																																																										this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																																										this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																																																																		true;
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FX][Constants.bytS_UNCLASS_INITIAL] =
																									this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FLASH][Constants.bytS_UNCLASS_INITIAL] =
																																																	this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_ROBOT][Constants.bytS_UNCLASS_INITIAL] =
																																																																									this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																	false;
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_COLORS][Constants.bytS_UNCLASS_INITIAL] = true;
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_BALLS][Constants.bytS_UNCLASS_INITIAL] = true;

		// Set initial byte control values :
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																												Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY][Constants.bytS_UNCLASS_INITIAL] =
																												Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_HEIGHT][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_HEIGHT_DEFAULT_VALUE;
		this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_DWELL][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_DWELL_DEFAULT_VALUE;

		// Set initial string control values :
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_PATTERN][Constants.bytS_UNCLASS_INITIAL] =
																									Strings.doConcat(	MenusTools.getNoMnemonicLabel(Language.strS_TOKEN_A[Language.bytS_TOKEN_NEW_PATTERN_MENU]),
																														Strings.strS_ELLIPSIS);
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL] = Constants.strS_STRING_LOCAL_STYLE_DEFAULT;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] = Strings.strS_EMPTY;
		this.strGglobalAA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] = Strings.strS_EMPTY;

		final Pattern objLnewJugglePattern =
												new Pattern(this,
															Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER,
															false,
															this.bolGglobalValueAA,
															this.bytGglobalValueAA,
															this.strGglobalAA);

		this.strGstyleAL = new ArrayList<String>(1);
		this.strGstyleAL.add(Constants.strS_STRING_LOCAL_STYLE_DEFAULT);

		final ArrayList<Object> objLobjectAL = new ArrayList<Object>(11);

		objLobjectAL.add(Strings.strS_SPACE);
		objLobjectAL.add(Strings.strS_SPACE);
		objLobjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_JUGGLE_MASTER_PRO]);
		objLobjectAL.add(Strings.strS_SPACE);
		objLobjectAL.add(Strings.doConcat("\251 ", Constants.strS_ENGINE_COPYRIGHT_YEARS, ' ', Constants.strS_ENGINE_ARNAUD_BELO));
		objLobjectAL.add(Strings.strS_SPACE);
		objLobjectAL.add("____________");
		objLobjectAL.add(Strings.strS_SPACE);
		objLobjectAL.add(objLnewJugglePattern);
		this.getPatternsFileManager().initNewPatternManager(objLobjectAL.size() - 1);
		objLobjectAL.add(Strings.strS_SPACE);
		objLobjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_CHOOSE_YOUR_PATTERNS_OR_LOOK_FOR_HELP]);
		this.objGobjectA = objLobjectAL.toArray();

		// Set global properties :
		this.intGpatternsNumber = 1;
		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_ERRORS][Constants.bytS_UNCLASS_INITIAL] =
																										this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_WARNINGS][Constants.bytS_UNCLASS_INITIAL] =
																																																		this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_INFO][Constants.bytS_UNCLASS_INITIAL] =
																																																																									this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_INFO][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doCreateNoPatternManager() {
		this.doCreateNoPatternManager(null, null, null, false, false, null, null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfileName
	 * @param strPtitle
	 * @param objPobjectAL
	 * @param strPstyle
	 */
	final public void doCreateNoPatternManager(	String strPfileName,
												String strPtitle,
												ImageIcon imgP,
												boolean bolPsiteswaps,
												boolean bolPstyles,
												ArrayList<Object> objPobjectAL,
												String strPstyle) {

		this.getPatternsFileManager().initNoPatternManager(strPfileName, strPtitle, imgP, bolPsiteswaps, bolPstyles, strPstyle != null);

		if (strPstyle != null) {

			// Set initial boolean control values :
			this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS][Constants.bytS_UNCLASS_INITIAL] =
																													this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][Constants.bytS_UNCLASS_INITIAL] =
																																																							this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																																																															true;
			this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																													this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																																							false;
			this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_EDITION][Constants.bytS_UNCLASS_INITIAL] =
																											this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SPEED][Constants.bytS_UNCLASS_INITIAL] =
																																																			this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FLUIDITY][Constants.bytS_UNCLASS_INITIAL] =
																																																																											this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																			this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][Constants.bytS_UNCLASS_INITIAL] =
																																																																																																																											true;
			this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FX][Constants.bytS_UNCLASS_INITIAL] = false;
			this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL] =
																											this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_BALLS][Constants.bytS_UNCLASS_INITIAL] =
																																																			true;

			// Set initial byte control values :
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																													Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY][Constants.bytS_UNCLASS_INITIAL] =
																													Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_HEIGHT][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_HEIGHT_DEFAULT_VALUE;
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_STROBE][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE;
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_DWELL][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_DWELL_DEFAULT_VALUE;
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL][Constants.bytS_UNCLASS_INITIAL] =
																											Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE;
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_SKILL][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_SKILL_AMATEUR;
			this.bytGglobalValueAA[Constants.bytS_BYTE_LOCAL_MARK][Constants.bytS_UNCLASS_INITIAL] = Constants.bytS_BYTE_LOCAL_MARK_DEFAULT_VALUE;

			// Set initial string control values :
			this.strGglobalAA[Constants.bytS_STRING_LOCAL_PATTERN][Constants.bytS_UNCLASS_INITIAL] = Strings.strS_EMPTY;
			this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL] = strPstyle;
			this.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] = Strings.strS_EMPTY;
			this.strGglobalAA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] = Strings.strS_EMPTY;
			final Pattern objLemptyJugglePattern =
													new Pattern(this,
																Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER,
																false,
																this.bolGglobalValueAA,
																this.bytGglobalValueAA,
																this.strGglobalAA);

			objPobjectAL.add(objLemptyJugglePattern);
			this.getPatternsFileManager().intGstartObjectIndex = objPobjectAL.size() - 1;
			this.objGobjectA = objPobjectAL.toArray();
			this.bytGpatternsManagerType = Constants.bytS_MANAGER_FILES_PATTERNS;
		} else {

			this.strGstyleAL = new ArrayList<String>(2);
			this.strGstyleAL.add(Constants.strS_STRING_LOCAL_STYLE_DEFAULT);
			this.strGstyleAL.add(this.strGglobalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL]);

			// Build list :
			final ArrayList<Object> objLobjectsListObjectAL = new ArrayList<Object>(13);

			objLobjectsListObjectAL.add(Strings.strS_EMPTY);
			objLobjectsListObjectAL.add(Strings.strS_EMPTY);
			objLobjectsListObjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_JUGGLE_MASTER_PRO]);
			objLobjectsListObjectAL.add(Strings.strS_EMPTY);
			objLobjectsListObjectAL.add(Strings.doConcat("\251 ", Constants.strS_ENGINE_COPYRIGHT_YEARS, ' ', Constants.strS_ENGINE_ARNAUD_BELO));
			objLobjectsListObjectAL.add("____________");
			objLobjectsListObjectAL.add(Strings.strS_EMPTY);
			objLobjectsListObjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_NO_PATTERN_IN_THIS_FILE]);
			objLobjectsListObjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_LOOK_FOR_HELP]);
			this.objGobjectA = objLobjectsListObjectAL.toArray();
			this.bytGpatternsManagerType = Constants.bytS_MANAGER_NO_PATTERN;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 */
	final public void doResetPattern(int intPobjectIndex) {
		if (this.isPattern(intPobjectIndex)) {
			final Pattern objLpattern = (Pattern) this.objGobjectA[intPobjectIndex];

			// Reset boolean properties :
			Arrays.fill(this.bolGbooleanIsUserDefinedA, false);
			for (byte bytLcontrolType = 0; bytLcontrolType < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLcontrolType) {
				if (this.bolGbooleanIsFileDefinedA[bytLcontrolType]) {
					this.bolGglobalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																								this.bolGglobalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_INITIAL];
					objLpattern.bolGlocalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																									objLpattern.bolGlocalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_INITIAL];
				}
			}

			// Reset byte properties :
			Arrays.fill(this.bolGbyteIsUserDefinedA, false);
			for (byte bytLcontrolType = 0; bytLcontrolType < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLcontrolType) {
				if (this.bolGbyteIsFileDefinedA[bytLcontrolType]) {
					this.bytGglobalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																								this.bytGglobalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_INITIAL];
					objLpattern.bytGlocalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																									objLpattern.bytGlocalValueAA[bytLcontrolType][Constants.bytS_UNCLASS_INITIAL];
				}
			}

			// Reset string properties :
			Arrays.fill(this.bolGstringIsUserDefinedA, false);
			for (byte bytLcontrolType = 0; bytLcontrolType < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLcontrolType) {
				if (this.bolGstringIsFileDefinedA[bytLcontrolType]) {
					this.strGglobalAA[bytLcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																							this.strGglobalAA[bytLcontrolType][Constants.bytS_UNCLASS_INITIAL];
					objLpattern.strGlocalAA[bytLcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																								objLpattern.strGlocalAA[bytLcontrolType][Constants.bytS_UNCLASS_INITIAL];
				}
			}

			// Reset inner properties :
			objLpattern.intGballsNumberA[Constants.bytS_UNCLASS_CURRENT] = objLpattern.intGballsNumberA[Constants.bytS_UNCLASS_INITIAL];
			objLpattern.bolGplayableA[Constants.bytS_UNCLASS_CURRENT] = objLpattern.bolGplayableA[Constants.bytS_UNCLASS_INITIAL];
		}
	}

	final public Boolean getBoolean(byte bytPcontrolIndex) {
		return this.getPatternBoolean(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public Boolean getBoolean(byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		return this.getPatternBoolean(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bolPjMPFileFormat
	 * @param bytPinitialOrCurrent
	 * @param bolPlocality
	 * @return
	 */
	final private String getBooleanParameterDefinitionString(	int intPobjectIndex,
																byte bytPcontrolType,
																byte bytPfileFormat,
																byte bytPinitialOrCurrent,
																boolean bolPlocality) {

		final String strLparameter = Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytPcontrolType][bytPfileFormat];
		return strLparameter != null
									? Strings.doConcat(	'#',
														strLparameter,
														bytPfileFormat == Constants.bytS_EXTENSION_JMP
																										? Strings.doConcat(	" = ",
																															bolPlocality
																																		? this.bolGbooleanIsLocalA[bytPcontrolType]
																																													? "local"
																																													: "global"
																																		: this.getPatternBooleanValue(	intPobjectIndex,
																																										bytPcontrolType,
																																										bytPinitialOrCurrent)
																																																? "true"
																																																: "false")
																										: Strings.doConcat(	'=',
																															this.getPatternBooleanValue(bolPlocality
																																									? intPobjectIndex
																																									: Constants.bytS_UNCLASS_NO_VALUE,
																																						bytPcontrolType,
																																						bytPinitialOrCurrent)
																																												? "1"
																																												: "0"))
									: null;
	}

	final public boolean getBooleanValue(byte bytPcontrolIndex) {
		return this.getPatternBooleanValue(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public boolean getBooleanValue(byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		return this.getPatternBooleanValue(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent);
	}

	final public Byte getByte(byte bytPcontrolIndex) {
		return this.getPatternByte(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public Byte getByte(byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		return this.getPatternByte(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bolPjMPFileFormat
	 * @param bytPinitialOrCurrent
	 * @param bolPlocality
	 * @return
	 */
	final private String getByteParameterDefinitionString(	int intPobjectIndex,
															byte bytPcontrolType,
															byte bytPfileFormat,
															byte bytPinitialOrCurrent,
															boolean bolPlocality) {
		final String strLparameter = Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytPcontrolType][bytPfileFormat];
		return strLparameter != null
									? Strings.doConcat(	'#',
														Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytPcontrolType][bytPfileFormat],
														bytPfileFormat == Constants.bytS_EXTENSION_JMP
																										? Strings.doConcat(	" = ",
																															bolPlocality
																																		? this.bolGbyteIsLocalA[bytPcontrolType]
																																												? "local"
																																												: "global"
																																		: PatternsManager.getByteParameterValueString(	bytPcontrolType,
																																														this.getPatternByteValue(	intPobjectIndex,
																																																					bytPcontrolType,
																																																					bytPinitialOrCurrent),
																																														bytPfileFormat))
																										: Strings.doConcat(	'=',
																															PatternsManager.getByteParameterValueString(bytPcontrolType,
																																										this.getPatternByteValue(	intPobjectIndex,
																																																	bytPcontrolType,
																																																	bytPinitialOrCurrent),
																																										bytPfileFormat)))
									: null;
	}

	final public byte getByteValue(byte bytPcontrolIndex) {
		return this.getPatternByteValue(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public byte getByteValue(byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		return this.getPatternByteValue(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getInfo() {
		return this.getInfo(false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPfull
	 * @return
	 */
	final public String getInfo(boolean bolPfull) {
		final StringBuilder objLgetInfoStringBuilder = new StringBuilder(bolPfull ? 32768 : 256);

		objLgetInfoStringBuilder.append(Strings.doConcat(	"Associated file name(s) : ",
															this.getPatternsFileManager().strGreferenceAL,
															Strings.strS_LINE_SEPARATOR,
															"Manager title           : ",
															this.getPatternsFileManager().getTitlesString(),
															Strings.strS_LINE_SEPARATOR,
															"Siteswaps               : ",
															this.getPatternsFileManager().bolGsiteswapsAL,
															Strings.strS_LINE_SEPARATOR,
															"Styles                  : ",
															this.getPatternsFileManager().bolGstylesAL,
															Strings.strS_LINE_SEPARATOR,
															"Style(s) found          : ",
															this.getPatternsFileManager().bolGstyleFound,
															Strings.strS_LINE_SEPARATOR));
		if (bolPfull && this.objGobjectA != null) {
			for (int intLobjectIndex = 0; intLobjectIndex < this.objGobjectA.length; ++intLobjectIndex) {
				objLgetInfoStringBuilder.append(Strings.doConcat(	"OBJECT ",
																	intLobjectIndex,
																	'/',
																	this.objGobjectA.length,
																	" :",
																	Strings.strS_LINE_SEPARATOR,
																	this.getInfo(intLobjectIndex, false, false, false, false)));
			}
		}

		objLgetInfoStringBuilder.trimToSize();
		return objLgetInfoStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @return
	 */
	final public String getInfo(int intPobjectIndex, boolean bolPsiteswap, boolean bolPstyle, boolean bolPproperties, boolean bolPinitial) {
		final StringBuilder objLinfoStringBuilder = new StringBuilder(4096);

		if (this.isPattern(intPobjectIndex)) {
			final Pattern objLpattern = this.getPattern(intPobjectIndex);

			// Pattern name :
			final String strL = Strings.getCharsString('¯', 22);

			objLinfoStringBuilder.append(Strings.doConcat(Strings.strS_LINE_SEPARATOR, Strings.strS_LINE_SEPARATOR, "Pattern              : ("));
			if (bolPinitial) {
				objLinfoStringBuilder.append(Strings.doConcat(this.getPatternStringValue(	intPobjectIndex,
																							Constants.bytS_STRING_LOCAL_PATTERN,
																							Constants.bytS_UNCLASS_INITIAL), ", "));
			}
			objLinfoStringBuilder.append(Strings.doConcat(	this.getPatternStringValue(	intPobjectIndex,
																						Constants.bytS_STRING_LOCAL_PATTERN,
																						Constants.bytS_UNCLASS_CURRENT),
															')',
															Strings.strS_LINE_SEPARATOR,
															strL,
															Strings.strS_LINE_SEPARATOR));

			// Siteswap :
			if (bolPsiteswap) {
				if (bolPinitial) {
					objLinfoStringBuilder.append(Strings.doConcat(	"Siteswap (I)         :",
																	Strings.strS_LINE_SEPARATOR,
																	strL,
																	Strings.strS_LINE_SEPARATOR,
																	(new Siteswap(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL])).getInfo()));
				}
				objLinfoStringBuilder.append(Strings.doConcat(	"Siteswap (C)         :",
																Strings.strS_LINE_SEPARATOR,
																strL,
																Strings.strS_LINE_SEPARATOR,
																(new Siteswap(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_CURRENT])).getInfo()));
				if (bolPinitial) {
					objLinfoStringBuilder.append(Strings.doConcat(	"Reverse Siteswap (I) :",
																	Strings.strS_LINE_SEPARATOR,
																	strL,
																	Strings.strS_LINE_SEPARATOR,
																	(new Siteswap(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL])).getInfo()));
				}
				objLinfoStringBuilder.append(Strings.doConcat(	"Reverse Siteswap (C) :",
																Strings.strS_LINE_SEPARATOR,
																strL,
																Strings.strS_LINE_SEPARATOR,
																(new Siteswap(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_CURRENT])).getInfo()));
			}

			// Style :
			if (bolPstyle) {
				try {
					final Style[] objLstyleA = new Style[2];
					if (bolPinitial) {
						objLstyleA[Constants.bytS_UNCLASS_INITIAL] =
																		this.getStyle(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL]);
						final String strLinitValuesComment = "Style (I) :";
						objLinfoStringBuilder.append(Strings.doConcat(	strLinitValuesComment,
																		Strings.strS_LINE_SEPARATOR,
																		Strings.getCharsString('¯', strLinitValuesComment.length()),
																		Strings.strS_LINE_SEPARATOR,
																		objLstyleA[Constants.bytS_UNCLASS_INITIAL].getInfo()));
					}
					objLstyleA[Constants.bytS_UNCLASS_CURRENT] =
																	this.getStyle(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_CURRENT]);
					final String strLcurrentValuesComment = "Style (C) :";
					objLinfoStringBuilder.append(Strings.doConcat(	strLcurrentValuesComment,
																	Strings.strS_LINE_SEPARATOR,
																	Strings.getCharsString('¯', strLcurrentValuesComment.length()),
																	Strings.strS_LINE_SEPARATOR,
																	objLstyleA[Constants.bytS_UNCLASS_CURRENT].getInfo()));
				} catch (final Throwable objPthrowable) {}
			}

			// Control header :
			if (bolPproperties) {
				final String strLheader =
											Strings.doConcat(	Strings.getFixedLengthString(	"objL<property>",
																								Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH),
																" = (<initial>, <current> [<fileDefined>][<userDefined>]) <range> :");
				objLinfoStringBuilder.append(Strings.doConcat(	strLheader,
																Strings.strS_LINE_SEPARATOR,
																Strings.getCharsString('¯', strLheader.length()),
																Strings.strS_LINE_SEPARATOR));

				// Local string controls :
				for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
					objLinfoStringBuilder.append(Strings.doConcat(	Constants.strS_STRING_LOCAL,
																	Constants.strS_STRING_LOCAL_CONTROL_A[bytLstringControlIndex],
																	" = (\"",
																	this.getPatternStringValue(	intPobjectIndex,
																								bytLstringControlIndex,
																								Constants.bytS_UNCLASS_INITIAL),
																	"\", \"",
																	this.getPatternStringValue(	intPobjectIndex,
																								bytLstringControlIndex,
																								Constants.bytS_UNCLASS_CURRENT),
																	'"',
																	this.bolGstringIsFileDefinedA[bytLstringControlIndex] ? " F" : "  ",
																	this.bolGstringIsUserDefinedA[bytLstringControlIndex] ? 'U' : ' ',
																	") ",
																	this.bolGstringIsLocalA[bytLstringControlIndex] ? "Local  " : "Global ",
																	this.bolGstringIsLocallyDefinedA[bytLstringControlIndex] ? 'F' : ' ',
																	((this.getPatternStringValue(	intPobjectIndex,
																									bytLstringControlIndex,
																									Constants.bytS_UNCLASS_INITIAL) != null) && !this	.getPatternStringValue(	intPobjectIndex,
																																												bytLstringControlIndex,
																																												Constants.bytS_UNCLASS_INITIAL)
																																						.equals(this.getPatternStringValue(	intPobjectIndex,
																																															bytLstringControlIndex,
																																															Constants.bytS_UNCLASS_CURRENT)))
																																																								? '*'
																																																								: ' ',
																	Strings.strS_LINE_SEPARATOR));
				}

				// Local boolean controls :
				for (byte bytLbooleanControlIndex = 0; bytLbooleanControlIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanControlIndex) {
					final int intLcontrolStringIndex = (Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLbooleanControlIndex][0] != null) ? 0 : 1;

					objLinfoStringBuilder.append(Strings.doConcat(	Constants.strS_BOOLEAN_LOCAL,
																	Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLbooleanControlIndex][intLcontrolStringIndex],
																	" = (",
																	Strings.getFixedLengthString(	this.getPatternBooleanValue(intPobjectIndex,
																																bytLbooleanControlIndex,
																																Constants.bytS_UNCLASS_INITIAL),
																									5),
																	", ",
																	Strings.getFixedLengthString(	this.getPatternBooleanValue(intPobjectIndex,
																																bytLbooleanControlIndex,
																																Constants.bytS_UNCLASS_CURRENT),
																									5),
																	this.bolGbooleanIsFileDefinedA[bytLbooleanControlIndex] ? " F" : "  ",
																	this.bolGbooleanIsUserDefinedA[bytLbooleanControlIndex] ? 'U' : ' ',
																	") ",
																	this.bolGbooleanIsLocalA[bytLbooleanControlIndex] ? "Local  " : "Global ",
																	this.bolGbooleanIsLocallyDefinedA[bytLbooleanControlIndex] ? 'F' : ' ',
																	(this.getPatternBooleanValue(	intPobjectIndex,
																									bytLbooleanControlIndex,
																									Constants.bytS_UNCLASS_INITIAL) != this.getPatternBooleanValue(	intPobjectIndex,
																																									bytLbooleanControlIndex,
																																									Constants.bytS_UNCLASS_CURRENT))
																																																	? '*'
																																																	: ' ',
																	Strings.strS_LINE_SEPARATOR));
				}

				// Local byte controls :
				for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
					objLinfoStringBuilder.append(Strings.doConcat(	Constants.strS_BYTE_LOCAL,
																	Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLbyteControlIndex][0],
																	" = (",
																	Strings.getFixedLengthString(	Byte.toString(this.getPatternByteValue(	intPobjectIndex,
																																			bytLbyteControlIndex,
																																			Constants.bytS_UNCLASS_INITIAL)),
																									3),
																	", ",
																	Strings.getFixedLengthString(	Byte.toString(this.getPatternByteValue(	intPobjectIndex,
																																			bytLbyteControlIndex,
																																			Constants.bytS_UNCLASS_CURRENT)),
																									3),
																	this.bolGbyteIsFileDefinedA[bytLbyteControlIndex] ? " F" : "  ",
																	this.bolGbyteIsUserDefinedA[bytLbyteControlIndex] ? 'U' : ' ',
																	") ",
																	this.bolGbyteIsLocalA[bytLbyteControlIndex] ? "Local  " : "Global ",
																	this.bolGbyteIsLocallyDefinedA[bytLbyteControlIndex] ? 'F' : ' ',
																	(this.getPatternByteValue(	intPobjectIndex,
																								bytLbyteControlIndex,
																								Constants.bytS_UNCLASS_INITIAL) != this.getPatternByteValue(intPobjectIndex,
																																							bytLbyteControlIndex,
																																							Constants.bytS_UNCLASS_CURRENT))
																																															? '*'
																																															: ' ',
																	Strings.strS_LINE_SEPARATOR));
				}
			}
		} else {
			final String strLcomment =
										this.getPatternStringValue(	intPobjectIndex,
																	Constants.bytS_STRING_LOCAL_PATTERN,
																	Constants.bytS_UNCLASS_INITIAL);

			objLinfoStringBuilder.append(Strings.doConcat(	(strLcomment == null) ? "No pattern at the specified index ("
																					: "Specified index pattern (",
															intPobjectIndex,
															(strLcomment == null) ? ")." : Strings.doConcat(") corresponds to this comment :",
																											Strings.strS_LINE_SEPARATOR,
																											'\'',
																											strLcomment,
																											'\'',
																											Strings.strS_LINE_SEPARATOR)));
		}

		return objLinfoStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getJugglerValue(int intPobjectIndex, byte bytPinitialOrCurrent) {
		return this.getPatternByteValue(intPobjectIndex,
										this.getPatternBooleanValue(intPobjectIndex, Constants.bytS_BOOLEAN_LOCAL_LIGHT, bytPinitialOrCurrent)
																																				? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																				: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT,
										bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @return
	 */
	final public Pattern getPattern(int intPobjectIndex) {
		Object objLobject;

		try {
			objLobject = this.objGobjectA[intPobjectIndex];
		} catch (final Throwable objPthrowable) {
			return null;
		}

		return ((objLobject instanceof Pattern) ? (Pattern) objLobject : null);
	}

	final public Boolean getPatternBoolean(int intPobjectIndex, byte bytPcontrolIndex) {
		return this.getPatternBoolean(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public Boolean getPatternBoolean(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		boolean bolLtetDeKran = false;
		switch (bytPcontrolIndex) {
			case Constants.bytS_BOOLEAN_LOCAL_STYLE:
			case Constants.bytS_BOOLEAN_LOCAL_LIGHT:
			case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
			case Constants.bytS_BOOLEAN_LOCAL_COLORS:
			case Constants.bytS_BOOLEAN_LOCAL_BALLS:
				bolLtetDeKran = true;
		}

		return bytPcontrolIndex >= 0 && bytPcontrolIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER
				&& (bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL || bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT)
																																		? intPobjectIndex >= 0
																																			&& intPobjectIndex < this.objGobjectA.length
																																														? this.bolGbooleanIsLocalA[bytPcontrolIndex]
																																															&& this.isPattern(intPobjectIndex)
																																																								? ((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[bytPcontrolIndex][bytPinitialOrCurrent]
																																																								: this.bolGglobalValueAA[bytPcontrolIndex][bytPinitialOrCurrent]
																																														: bolLtetDeKran
																																		: false;
	}

	final public boolean getPatternBooleanValue(int intPobjectIndex, byte bytPcontrolIndex) {
		return this.getPatternBooleanValue(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public boolean getPatternBooleanValue(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		final Boolean bolLselected = this.getPatternBoolean(intPobjectIndex, bytPcontrolIndex, bytPinitialOrCurrent);
		if (bolLselected == null) {
			return Preferences.getLocalBooleanPreference(bytPcontrolIndex);
		}
		return bolLselected;
	}

	final public Byte getPatternByte(int intPobjectIndex, byte bytPcontrolIndex) {
		return this.getPatternByte(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public Byte getPatternByte(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		byte bytLtetDeKran = Constants.bytS_UNCLASS_NO_VALUE;
		switch (bytPcontrolIndex) {
			case Constants.bytS_BYTE_LOCAL_SPEED:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE;
				break;
			case Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_MAXIMUM_VALUE;
				break;
			case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
				break;
			case Constants.bytS_BYTE_LOCAL_FLUIDITY:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE;
				break;
			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
				break;
			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
				break;
			case Constants.bytS_BYTE_LOCAL_HEIGHT:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE;
				break;
			case Constants.bytS_BYTE_LOCAL_STROBE:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE;
				break;
			case Constants.bytS_BYTE_LOCAL_SKILL:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE;
				break;
			case Constants.bytS_BYTE_LOCAL_MARK:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE;
				break;
			case Constants.bytS_BYTE_LOCAL_DWELL:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE;
				break;
			case Constants.bytS_BYTE_LOCAL_DEFAULTS:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE;
				break;
			case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
				bytLtetDeKran = Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE;
				break;
		}

		return bytPcontrolIndex >= 0 && bytPcontrolIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
				&& (bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL || bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT)
																																		? intPobjectIndex >= 0
																																			&& intPobjectIndex < this.objGobjectA.length
																																														? (this.bolGbyteIsLocalA[bytPcontrolIndex] && this.isPattern(intPobjectIndex))
																																																																		? ((Pattern) this.objGobjectA[intPobjectIndex]).bytGlocalValueAA[bytPcontrolIndex][bytPinitialOrCurrent]
																																																																		: this.bytGglobalValueAA[bytPcontrolIndex][bytPinitialOrCurrent]
																																														: bytLtetDeKran
																																		: Constants.bytS_UNCLASS_NO_VALUE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolIndex
	 * @return
	 */
	final public byte getPatternByteValue(int intPobjectIndex, byte bytPcontrolIndex) {
		return this.getPatternByteValue(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public byte getPatternByteValue(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		final Byte bytLcontrolValue = this.getPatternByte(intPobjectIndex, bytPcontrolIndex, bytPinitialOrCurrent);
		if (bytLcontrolValue == null) {
			return Preferences.getLocalBytePreference(bytPcontrolIndex);
		}
		return bytLcontrolValue;
	}

	final public PatternsFilesManager getPatternsFileManager() {
		return this.objGpatternsFileManager;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolIndex
	 * @return
	 */
	final public String getPatternString(int intPobjectIndex, byte bytPcontrolIndex) {
		return this.getPatternString(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public String getPatternString(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		String strLtetDeKran = Strings.strS_EMPTY;
		switch (bytPcontrolIndex) {
			case Constants.bytS_STRING_LOCAL_PATTERN:
				strLtetDeKran = Strings.strS_EMPTY;
				break;
			case Constants.bytS_STRING_LOCAL_COLORS:
				strLtetDeKran = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
				break;
			case Constants.bytS_STRING_LOCAL_REVERSE_COLORS:
				strLtetDeKran = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
				break;
			case Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT:
				strLtetDeKran = Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT;
				break;
			case Constants.bytS_STRING_LOCAL_STYLE:
				strLtetDeKran = Constants.strS_ENGINE_TET_DE_KRAN_STYLE;
				break;
			case Constants.bytS_STRING_LOCAL_SITESWAP:
				strLtetDeKran = "4,4()";
				break;
			case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
				strLtetDeKran = "()4,4";
				break;
		}

		return bytPcontrolIndex >= 0 && bytPcontrolIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER && this.objGobjectA != null
				&& (bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL || bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT)
																																		? intPobjectIndex >= 0
																																			&& intPobjectIndex < this.objGobjectA.length
																																														? this.isPattern(intPobjectIndex)
																																																							? this.bolGstringIsLocalA[bytPcontrolIndex]
																																																																		? ((Pattern) this.objGobjectA[intPobjectIndex]).strGlocalAA[bytPcontrolIndex][bytPinitialOrCurrent]
																																																																		: this.strGglobalAA[bytPcontrolIndex][bytPinitialOrCurrent]
																																																							: this.objGobjectA[intPobjectIndex].toString()
																																														: strLtetDeKran
																																		: Strings.strS_EMPTY;
	}

	final public String getPatternStringValue(int intPobjectIndex, byte bytPcontrolIndex) {
		return this.getPatternStringValue(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public String getPatternStringValue(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		final String strLcontrolValue = this.getPatternString(intPobjectIndex, bytPcontrolIndex, bytPinitialOrCurrent);
		if (strLcontrolValue == null) {
			return Preferences.getLocalStringPreference(bytPcontrolIndex);
		}
		return strLcontrolValue;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @return
	 */
	final public int getShortcutIndex(int intPobjectIndex) {
		if (this.intGshortcutIndexA != null && this.intGshortcutIndexA.length > 0 && intPobjectIndex > this.intGshortcutIndexA[0]) {
			int intLlowerShortcutIndex = 0;
			int intLupperShortcutIndex = this.intGshortcutIndexA.length - 1;
			while (intLupperShortcutIndex - intLlowerShortcutIndex > 1) {
				final int intLmiddleShortcutIndex = (intLupperShortcutIndex + intLlowerShortcutIndex) / 2;
				if (this.intGshortcutIndexA[intLmiddleShortcutIndex] < intPobjectIndex) {
					intLlowerShortcutIndex = intLmiddleShortcutIndex;
				} else if (this.intGshortcutIndexA[intLmiddleShortcutIndex] == intPobjectIndex) {
					return this.intGshortcutIndexA[intLmiddleShortcutIndex];
				} else {
					intLupperShortcutIndex = intLmiddleShortcutIndex;
				}
			}
			return intLlowerShortcutIndex;
		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	final public String getString(byte bytPcontrolIndex) {
		return this.getPatternString(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public String getString(byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		return this.getPatternString(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @param bolPlocality
	 * @return
	 */
	final private String getStringParameterDefinitionString(int intPobjectIndex, byte bytPcontrolType, byte bytPinitialOrCurrent, boolean bolPlocality) {
		final String strLparameter = Constants.strS_STRING_LOCAL_CONTROL_A[bytPcontrolType];
		return strLparameter != null
									? Strings.doConcat(	((bytPcontrolType == Constants.bytS_STRING_LOCAL_COLORS) && !bolPlocality)
																																	? "$ "
																																	: Strings.doConcat(	'#',
																																						Constants.strS_STRING_LOCAL_CONTROL_A[bytPcontrolType],
																																						" = "),
														bolPlocality ? this.bolGstringIsLocalA[bytPcontrolType] ? "local" : "global"
																	: this.getPatternStringValue(	intPobjectIndex,
																									bytPcontrolType,
																									bytPinitialOrCurrent)) : null;
	}

	final public String getStringValue(byte bytPcontrolIndex) {
		return this.getPatternStringValue(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	final public String getStringValue(byte bytPcontrolIndex, byte bytPinitialOrCurrent) {
		return this.getPatternStringValue(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPstyle
	 * @return
	 */
	final public Style getStyle(String strPstyle) {
		return this.getStyle(strPstyle, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPstyle
	 * @param bolPnoReverse
	 * @return
	 */
	final public Style getStyle(String strPstyle, boolean bolPnoReverse) {

		// Tools.verbose("Appel à getJuggleStyle(", strPstyle, ", ", bolPnoReverse, ")");
		return strPstyle != null ? new Style(this.objGstylesHashMap.get(strPstyle), bolPnoReverse) : null;
	}

	final public boolean hasPatternChanged(int intPpatternIndex) {

		// Check pattern :
		if (this.bytGpatternsManagerType != Constants.bytS_MANAGER_FILES_PATTERNS
			&& this.bytGpatternsManagerType != Constants.bytS_MANAGER_NEW_PATTERN || intPpatternIndex < 0
			|| intPpatternIndex >= this.objGobjectA.length || !(this.objGobjectA[intPpatternIndex] instanceof Pattern)) {
			return false;
		}

		final Pattern objLpattern = (Pattern) this.objGobjectA[intPpatternIndex];

		// Check byte values :
		for (byte bytLbyteValueIndex = 0; bytLbyteValueIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteValueIndex) {
			if (this.bolGbyteIsLocalA[bytLbyteValueIndex]
				&& this.bolGbyteIsFileDefinedA[bytLbyteValueIndex]
				&& objLpattern.bytGlocalValueAA[bytLbyteValueIndex][Constants.bytS_UNCLASS_CURRENT] != objLpattern.bytGlocalValueAA[bytLbyteValueIndex][Constants.bytS_UNCLASS_INITIAL]) {
				return true;
			}
		}

		// Check boolean values :
		for (byte bytLbooleanValueIndex = 0; bytLbooleanValueIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanValueIndex) {
			if (this.bolGbooleanIsLocalA[bytLbooleanValueIndex]
				&& this.bolGbooleanIsFileDefinedA[bytLbooleanValueIndex]
				&& objLpattern.bolGlocalValueAA[bytLbooleanValueIndex][Constants.bytS_UNCLASS_CURRENT] != objLpattern.bolGlocalValueAA[bytLbooleanValueIndex][Constants.bytS_UNCLASS_INITIAL]) {
				return true;
			}
		}

		// Check string values :
		for (byte bytLstringValueIndex = 0; bytLstringValueIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringValueIndex) {
			if (this.bolGstringIsLocalA[bytLstringValueIndex]
				&& this.bolGstringIsFileDefinedA[bytLstringValueIndex]
				&& !Strings.areNullEqual(	objLpattern.strGlocalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_CURRENT],
											objLpattern.strGlocalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_INITIAL])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initCurrentValues() {
		for (byte bytLbyteValueIndex = 0; bytLbyteValueIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteValueIndex) {
			this.bytGglobalValueAA[bytLbyteValueIndex][Constants.bytS_UNCLASS_CURRENT] =
																							this.bytGglobalValueAA[bytLbyteValueIndex][Constants.bytS_UNCLASS_INITIAL];
		}

		for (byte bytLbooleanValueIndex = 0; bytLbooleanValueIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanValueIndex) {
			this.bolGglobalValueAA[bytLbooleanValueIndex][Constants.bytS_UNCLASS_CURRENT] =
																							this.bolGglobalValueAA[bytLbooleanValueIndex][Constants.bytS_UNCLASS_INITIAL];
		}

		for (byte bytLstringValueIndex = 0; bytLstringValueIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringValueIndex) {
			this.strGglobalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_CURRENT] =
																						(this.strGglobalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_INITIAL] != null)
																																											? new String(this.strGglobalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_INITIAL])
																																											: null;
		}

		this.getPatternsFileManager().objGwrongStyleAL = null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initInitialValues(boolean bolPfullInit, byte bytPfileFormat) {

		Tools.debug("PatternsManager.initInitialValues(", bolPfullInit, ", ", bytPfileFormat, ')');
		if (bolPfullInit && this.bolGglobalValueAA == null) {
			this.bolGglobalValueAA = new Boolean[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER][2];
		}
		for (byte bytLbooleanValueIndex = 0; bytLbooleanValueIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanValueIndex) {

			if (bolPfullInit) {
				if (bytPfileFormat == Constants.bytS_EXTENSION_JM) {
					boolean bolLenabled = false;
					switch (bytLbooleanValueIndex) {
						case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
						case Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS:
						case Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS:
						case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
						case Constants.bytS_BOOLEAN_LOCAL_FLASH:
						case Constants.bytS_BOOLEAN_LOCAL_FX:
						case Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL:
						case Constants.bytS_BOOLEAN_LOCAL_METRONOME:
						case Constants.bytS_BOOLEAN_LOCAL_MIRROR:
						case Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS:
						case Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS:
						case Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP:
						case Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE:
						case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
						case Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS:
							break;
						case Constants.bytS_BOOLEAN_LOCAL_BALLS:
						case Constants.bytS_BOOLEAN_LOCAL_COLORS:
						case Constants.bytS_BOOLEAN_LOCAL_EDITION:
						case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
						case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
						case Constants.bytS_BOOLEAN_LOCAL_LIGHT:
						case Constants.bytS_BOOLEAN_LOCAL_SITESWAP:
						case Constants.bytS_BOOLEAN_LOCAL_SOUNDS:
						case Constants.bytS_BOOLEAN_LOCAL_SPEED:
						case Constants.bytS_BOOLEAN_LOCAL_STYLE:
						case Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS:
							bolLenabled = true;
							break;
						case Constants.bytS_BOOLEAN_LOCAL_INFO:
						case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
						case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
							bolLenabled = Preferences.getLocalBooleanPreference(bytLbooleanValueIndex);
							break;
					}
					this.setBoolean(bytLbooleanValueIndex, bolLenabled);
					continue;
				}
				this.setBoolean(bytLbooleanValueIndex, Preferences.getLocalBooleanPreference(bytLbooleanValueIndex));
				continue;
			}
			if (this.bolGbooleanIsLocalA[bytLbooleanValueIndex]) {
				this.setBoolean(bytLbooleanValueIndex, Preferences.getLocalBooleanPreference(bytLbooleanValueIndex));
			}
		}

		if (bolPfullInit && this.bytGglobalValueAA == null) {
			this.bytGglobalValueAA = new Byte[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER][2];
		}
		for (byte bytLbyteValueIndex = 0; bytLbyteValueIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteValueIndex) {
			if (bolPfullInit) {
				if (bytPfileFormat == Constants.bytS_EXTENSION_JM) {
					byte bytLvalue = Preferences.getLocalBytePreference(bytLbyteValueIndex);
					switch (bytLbyteValueIndex) {
						case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
							bytLvalue = Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
							break;
						case Constants.bytS_BYTE_LOCAL_DEFAULTS:
							bytLvalue = Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_DWELL:
							bytLvalue = Constants.bytS_BYTE_LOCAL_DWELL_DEFAULT_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_FLUIDITY:
							bytLvalue = Constants.bytS_BYTE_LOCAL_FLUIDITY_DEFAULT_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_SKILL:
							bytLvalue = Constants.bytS_BYTE_LOCAL_SKILL_AMATEUR;
							break;
						case Constants.bytS_BYTE_LOCAL_MARK:
							bytLvalue = Constants.bytS_BYTE_LOCAL_MARK_DEFAULT_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_SPEED:
							bytLvalue = Constants.bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED:
							bytLvalue = Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_DEFAULT_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_STROBE:
							bytLvalue = Constants.bytS_BYTE_LOCAL_STROBE_MINIMUM_RATE;
							break;
						case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
							bytLvalue = Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_DEFAULT_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_HEIGHT:
							bytLvalue = Constants.bytS_BYTE_LOCAL_HEIGHT_DEFAULT_VALUE;
							break;
						case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
							bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
							break;
						case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
							bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
							break;
					}
					this.bytGglobalValueAA[bytLbyteValueIndex][Constants.bytS_UNCLASS_INITIAL] = bytLvalue;
					// this.setControlValue(bytLbyteValueIndex, bytLvalue);
					continue;
				}
				this.bytGglobalValueAA[bytLbyteValueIndex][Constants.bytS_UNCLASS_INITIAL] = Preferences.getLocalBytePreference(bytLbyteValueIndex);
				// this.setControlValue(bytLbyteValueIndex, JugglePreferences.getLocalBytePreference(bytLbyteValueIndex));
				continue;
			}
			if (this.bolGbyteIsLocalA[bytLbyteValueIndex]) {
				this.setByte(bytLbyteValueIndex, Preferences.getLocalBytePreference(bytLbyteValueIndex));
			}
		}

		if (bolPfullInit && this.strGglobalAA == null) {
			this.strGglobalAA = new String[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER][2];
		}
		for (byte bytLstringValueIndex = 0; bytLstringValueIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringValueIndex) {
			if (bolPfullInit) {
				if (bytPfileFormat == Constants.bytS_EXTENSION_JM) {
					String strLvalue = Strings.strS_EMPTY;
					switch (bytLstringValueIndex) {
						case Constants.bytS_STRING_LOCAL_PATTERN:
						case Constants.bytS_STRING_LOCAL_REVERSE_COLORS:
						case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
						case Constants.bytS_STRING_LOCAL_SITESWAP:
						case Constants.bytS_STRING_LOCAL_STYLE:
							break;
						case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
							strLvalue = Constants.strS_STRING_LOCAL_BACKGROUND_DAY_DEFAULT;
							break;
						case Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT:
							strLvalue = Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT;
							break;
						case Constants.bytS_STRING_LOCAL_COLORS:
							strLvalue = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
							break;
						case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
							strLvalue = Constants.strS_STRING_LOCAL_JUGGLER_DAY_DEFAULT;
							break;
						case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
							strLvalue = Constants.strS_STRING_LOCAL_JUGGLER_NIGHT_DEFAULT;
							break;
						case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
							strLvalue = Constants.strS_STRING_LOCAL_SITESWAP_DAY_DEFAULT;
							break;
						case Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT:
							strLvalue = Constants.strS_STRING_LOCAL_SITESWAP_NIGHT_DEFAULT;
							break;
					}
					this.strGglobalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_INITIAL] = strLvalue;
					// this.setControlString(bytLstringValueIndex, strLvalue);
					continue;
				}
				// this.setControlString(bytLstringValueIndex, JugglePreferences.getLocalStringPreference(bytLstringValueIndex));
				this.strGglobalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_INITIAL] = Preferences.getLocalStringPreference(bytLstringValueIndex);
				continue;
			}
			if (this.bolGstringIsLocalA[bytLstringValueIndex]) {
				this.strGglobalAA[bytLstringValueIndex][Constants.bytS_UNCLASS_INITIAL] = Preferences.getLocalStringPreference(bytLstringValueIndex);
				// this.setControlString(bytLstringValueIndex, JugglePreferences.getLocalStringPreference(bytLstringValueIndex));
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @return
	 */
	final public boolean isPattern(int intPobjectIndex) {
		try {
			return this.objGobjectA[intPobjectIndex] instanceof Pattern;
		} catch (final Throwable objPthrowable) {
			return false;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @return
	 */
	final public boolean isShortcut(int intPobjectIndex) {
		return (this.intGshortcutIndexA != null && Arrays.binarySearch(this.intGshortcutIndexA, intPobjectIndex) >= 0);
	}

	final public void setBoolean(byte bytPcontrolIndex, Boolean bolPenabled) {
		this.setPatternBoolean(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_INITIAL, bolPenabled);
	}

	final public void setBoolean(byte bytPcontrolIndex, byte bytPinitialOrCurrent, Boolean bolPenabled) {
		this.setPatternBoolean(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent, bolPenabled);
	}

	final public void setByte(byte bytPcontrolIndex, byte bytPinitialOrCurrent, Byte bytPvalue) {
		this.setPatternByte(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent, bytPvalue);
	}

	final public void setByte(byte bytPcontrolIndex, Byte bytPvalue) {
		this.setPatternByte(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_INITIAL, bytPvalue);
	}

	final private void setDefaultProperties() {

		// If values are defined or not :
		this.bolGbyteIsFileDefinedA = new boolean[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];
		this.bolGbyteIsUserDefinedA = new boolean[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];
		Arrays.fill(this.bolGbyteIsFileDefinedA, false);
		Arrays.fill(this.bolGbyteIsUserDefinedA, false);

		this.bolGbooleanIsFileDefinedA = new boolean[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER];
		this.bolGbooleanIsUserDefinedA = new boolean[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER];
		Arrays.fill(this.bolGbooleanIsFileDefinedA, false);
		Arrays.fill(this.bolGbooleanIsUserDefinedA, false);

		this.bolGstringIsFileDefinedA = new boolean[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];
		this.bolGstringIsUserDefinedA = new boolean[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];
		for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
			boolean bolLdefined = false;
			switch (bytLstringControlIndex) {
				case Constants.bytS_STRING_LOCAL_PATTERN:
				case Constants.bytS_STRING_LOCAL_SITESWAP:
				case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
				case Constants.bytS_STRING_LOCAL_STYLE:
					bolLdefined = true;
			}
			this.bolGstringIsFileDefinedA[bytLstringControlIndex] = this.bolGstringIsUserDefinedA[bytLstringControlIndex] = bolLdefined;
		}

		// If global/local are user-defined or not :
		this.bolGbyteIsLocallyDefinedA = new boolean[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];
		Arrays.fill(this.bolGbyteIsLocallyDefinedA, false);

		this.bolGbooleanIsLocallyDefinedA = new boolean[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER];
		Arrays.fill(this.bolGbooleanIsLocallyDefinedA, false);

		this.bolGstringIsLocallyDefinedA = new boolean[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];
		Arrays.fill(this.bolGstringIsLocallyDefinedA, false);

		// If values are global or local :
		this.bolGbyteIsLocalA = new boolean[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];
		this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED] =
																			this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_HEIGHT] =
																																		this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_SKILL] =
																																																	this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_MARK] =
																																																															this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_DWELL] =
																																																																														true;
		this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_SPEED] =
																	this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS] =
																																		this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_FLUIDITY] =
																																																	this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT] =
																																																																			this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY] =
																																																																																				this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_DEFAULTS] =
																																																																																																			this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_STROBE] =
																																																																																																																		this.bolGbyteIsLocalA[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL] =
																																																																																																																																		false;
		this.bolGbooleanIsLocalA = new boolean[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER];

		Arrays.fill(this.bolGbooleanIsLocalA, false);
		this.bolGbooleanIsLocalA[Constants.bytS_BOOLEAN_LOCAL_EDITION] = true;

		this.bolGstringIsLocalA = new boolean[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];
		this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_SITESWAP_DAY] =
																			this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_TOOLTIP] =
																																			this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT] =
																																																					this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] =
																																																																							this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT] =
																																																																																									this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_JUGGLER_DAY] =
																																																																																																										this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT] =
																																																																																																																												false;
		this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_PATTERN] =
																		this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_COLORS] =
																																		this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_REVERSE_COLORS] =
																																																				this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_STYLE] =
																																																																				this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_SITESWAP] =
																																																																																				this.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP] =
																																																																																																						true;

		// Style properties :
		this.objGstylesHashMap = new HashMap<String, Style>();
		this.objGstylesHashMap.put(Constants.strS_STRING_LOCAL_STYLE_DEFAULT, Style.objS_DEFAULT_JUGGLE_STYLE);
		this.objGstylesHashMap.put(Constants.strS_ENGINE_TET_DE_KRAN_STYLE, Style.objS_TET_DE_KRAN_JUGGLE_STYLE);

		// Pattern properties :
		this.intGpatternsNumber = 0;
		this.intGshortcutIndexA = null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPinitialOrCurrent
	 * @param bytPvalue
	 */
	final public void setJugglerValue(int intPobjectIndex, byte bytPinitialOrCurrent, byte bytPvalue) {
		this.setPatternByte(intPobjectIndex,
							this.getPatternBooleanValue(intPobjectIndex, Constants.bytS_BOOLEAN_LOCAL_LIGHT, bytPinitialOrCurrent)
																																	? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																	: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT,
							bytPinitialOrCurrent,
							bytPvalue);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolIndex
	 * @param bytPinitialOrCurrent
	 * @param bolPenabled
	 */
	final public void setPatternBoolean(int intPobjectIndex, byte bytPcontrolIndex, Boolean bolPenabled) {
		this.setPatternBoolean(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_INITIAL, bolPenabled);
	}

	final public void setPatternBoolean(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent, Boolean bolPenabled) {
		if ((bytPcontrolIndex >= 0) && (bytPcontrolIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER)
			&& ((bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL) || (bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT))) {

			final boolean bolLpattern = this.isPattern(intPobjectIndex);
			if (bolLpattern) {
				((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[bytPcontrolIndex][bytPinitialOrCurrent] = bolPenabled;
			}
			this.bolGglobalValueAA[bytPcontrolIndex][bytPinitialOrCurrent] = bolPenabled;

			if (bolPenabled) {
				if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_FLASH) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_ROBOT][bytPinitialOrCurrent] = false;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_ROBOT][bytPinitialOrCurrent] =
																																					false;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][bytPinitialOrCurrent] = true;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][bytPinitialOrCurrent] =
																																						true;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_ROBOT) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FLASH][bytPinitialOrCurrent] = false;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_FLASH][bytPinitialOrCurrent] =
																																					false;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_COLORS][bytPinitialOrCurrent] = true;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_COLORS][bytPinitialOrCurrent] =
																																					true;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][bytPinitialOrCurrent] = true;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][bytPinitialOrCurrent] =
																																						true;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][bytPinitialOrCurrent] = true;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][bytPinitialOrCurrent] =
																																						true;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_BALLS][bytPinitialOrCurrent] = true;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_BALLS][bytPinitialOrCurrent] =
																																					true;
					}
				}
			} else {
				if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_SITESWAP) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][bytPinitialOrCurrent] = false;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][bytPinitialOrCurrent] =
																																								false;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_JUGGLER) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL][bytPinitialOrCurrent] = false;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL][bytPinitialOrCurrent] =
																																							false;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_BALLS) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS][bytPinitialOrCurrent] = false;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS][bytPinitialOrCurrent] =
																																							false;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_COLORS) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS][bytPinitialOrCurrent] = false;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS][bytPinitialOrCurrent] =
																																							false;
					}
				} else if (bytPcontrolIndex == Constants.bytS_BOOLEAN_LOCAL_DEFAULTS) {
					this.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][bytPinitialOrCurrent] = false;
					if (bolLpattern) {
						((Pattern) this.objGobjectA[intPobjectIndex]).bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][bytPinitialOrCurrent] =
																																								false;
					}
				}
			}
		}
	}

	final public void setPatternByte(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent, Byte bytPvalue) {
		if ((bytPcontrolIndex >= 0) && (bytPcontrolIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER)
			&& ((bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL) || (bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT))) {

			if (this.isPattern(intPobjectIndex)) {
				((Pattern) this.objGobjectA[intPobjectIndex]).bytGlocalValueAA[bytPcontrolIndex][bytPinitialOrCurrent] = bytPvalue;
			}
			this.bytGglobalValueAA[bytPcontrolIndex][bytPinitialOrCurrent] = bytPvalue;
		}
	}

	final public void setPatternByte(int intPobjectIndex, byte bytPcontrolIndex, Byte bytPvalue) {
		this.setPatternByte(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_INITIAL, bytPvalue);
	}

	final public void setPatternString(int intPobjectIndex, byte bytPcontrolIndex, byte bytPinitialOrCurrent, String strP) {
		if ((bytPcontrolIndex >= 0) && (bytPcontrolIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER)
			&& ((bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL) || (bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT))) {
			if (this.isPattern(intPobjectIndex)) {
				((Pattern) this.objGobjectA[intPobjectIndex]).strGlocalAA[bytPcontrolIndex][bytPinitialOrCurrent] = new String(strP);
			}
			this.strGglobalAA[bytPcontrolIndex][bytPinitialOrCurrent] = new String(strP);
		}
	}

	final public void setPatternString(int intPobjectIndex, byte bytPcontrolIndex, String strP) {
		this.setPatternString(intPobjectIndex, bytPcontrolIndex, Constants.bytS_UNCLASS_INITIAL, strP);
	}

	final public void setString(byte bytPcontrolIndex, byte bytPinitialOrCurrent, String strP) {
		this.setPatternString(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, bytPinitialOrCurrent, strP);
	}

	final public void setString(byte bytPcontrolIndex, String strP) {
		this.setPatternString(Constants.bytS_UNCLASS_NO_VALUE, bytPcontrolIndex, Constants.bytS_UNCLASS_INITIAL, strP);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 * @param bytPinitialOrCurrent
	 * @param bolPjMPFileFormat
	 * @param objPlanguage
	 * @return
	 */
	final public String toFileString(	byte bytPinitialOrCurrent,
										byte bytPextensionFileFormat,
										boolean bolPsiteswaps,
										boolean bolPstyles,
										boolean bolPfilters,
										int[] intPfilteredObjectIndexA,
										int[] intPfilteredShortcutIndexA,
										Language objPlanguage) {

		if ((bytPinitialOrCurrent != Constants.bytS_UNCLASS_INITIAL) && (bytPinitialOrCurrent != Constants.bytS_UNCLASS_CURRENT)) {
			return null;
		}

		final String strLseparatorLine = objPlanguage.getPropertyValueString(Language.intS_MESSAGE_SEPARATOR_LINE);

		// Set pattern list :
		int[] intLfilteredObjectIndexA = intPfilteredObjectIndexA;
		int[] intLfilteredShortcutIndexA = intPfilteredShortcutIndexA;
		if (bolPsiteswaps && !bolPfilters) {
			if (this.objGobjectA != null && this.objGobjectA.length > 0) {
				intLfilteredObjectIndexA = new int[this.objGobjectA.length];
				for (int intLfilteredObjectIndex = 0; intLfilteredObjectIndex < intLfilteredObjectIndexA.length; ++intLfilteredObjectIndex) {
					intLfilteredObjectIndexA[intLfilteredObjectIndex] = intLfilteredObjectIndex;
				}
			} else {
				intLfilteredObjectIndexA = null;
			}
			if (this.intGshortcutIndexA != null && this.intGshortcutIndexA.length > 0) {
				intLfilteredShortcutIndexA = new int[this.intGshortcutIndexA.length];
				System.arraycopy(this.intGshortcutIndexA, 0, intLfilteredShortcutIndexA, 0, this.intGshortcutIndexA.length);
			} else {
				intLfilteredShortcutIndexA = null;
			}
		}

		// Declare returned string :
		final StringBuilder objLtoStringBuilder =
													new StringBuilder(16384 + 512 * (intLfilteredObjectIndexA != null
																														? intLfilteredObjectIndexA.length
																														: 0));

		// Write header :
		objLtoStringBuilder.append(Strings.doConcat(this.toHeaderString(bytPextensionFileFormat, objPlanguage), Strings.strS_LINE_SEPARATOR));

		// Write header parameters :
		if (bolPsiteswaps) {
			objLtoStringBuilder.append(Strings.doConcat(this.toHeaderParametersString(bytPinitialOrCurrent, bytPextensionFileFormat),
														Strings.strS_LINE_SEPARATOR,
														strLseparatorLine,
														Strings.strS_LINE_SEPARATOR));
		}

		// Write header styles :
		if (bolPstyles) {
			objLtoStringBuilder.append(Strings.doConcat(this.toHeaderStylesString(	bytPinitialOrCurrent,
																					bytPextensionFileFormat,
																					intLfilteredObjectIndexA),
														strLseparatorLine,
														Strings.strS_LINE_SEPARATOR,
														intLfilteredObjectIndexA));
		}

		// Patterns :
		boolean bolLshortcut;

		if (bolPsiteswaps && this.objGobjectA != null) {

			if (intLfilteredObjectIndexA != null) {
				for (int intLfilteredObjectIndex = 0, intLfilteredShortcutIndex = 0, intLpreviousPatternIndex = Constants.bytS_UNCLASS_NO_VALUE; intLfilteredObjectIndex < intLfilteredObjectIndexA.length; ++intLfilteredObjectIndex) {
					if (this.objGobjectA[intLfilteredObjectIndexA[intLfilteredObjectIndex]] instanceof String) {

						// Write comment or shortcut :
						if (intLfilteredShortcutIndexA != null) {
							while ((intLfilteredShortcutIndex < intLfilteredShortcutIndexA.length - 1)
									&& (intLfilteredShortcutIndexA[intLfilteredShortcutIndex] < intLfilteredObjectIndex)) {
								++intLfilteredShortcutIndex;
							}
						}

						final String strLcomment = ((String) this.objGobjectA[intLfilteredObjectIndexA[intLfilteredObjectIndex]]);
						bolLshortcut =
										((intLfilteredShortcutIndexA != null) && (intLfilteredObjectIndex == intLfilteredShortcutIndexA[intLfilteredShortcutIndex]));
						objLtoStringBuilder.append(Strings.doConcat('/',
																	bolLshortcut ? '[' : null,
																	strLcomment.trim().length() > 0
																		|| bytPextensionFileFormat == Constants.bytS_EXTENSION_JMP ? strLcomment
																																	: " ",
																	bolLshortcut ? ']' : null));
						intLpreviousPatternIndex = Constants.bytS_UNCLASS_NO_VALUE;
					} else if (this.getPatternBooleanValue(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
															Constants.bytS_BOOLEAN_LOCAL_EDITION,
															bytPinitialOrCurrent)
								&& (bytPextensionFileFormat == Constants.bytS_EXTENSION_JMP || !bolPstyles || this	.getStyle(this.getPatternStringValue(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
																																							Constants.bytS_STRING_LOCAL_STYLE,
																																							bytPinitialOrCurrent))
																													.isJMStyle())) {

						// Write pattern :
						final ArrayList<String> objLlocalParametersArrayList =
																				new ArrayList<String>(Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER
																										+ Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
																										+ Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER);

						// Prepare boolean parameters :
						if (bytPextensionFileFormat == Constants.bytS_EXTENSION_JMP) {
							for (byte bytLbooleanControlIndex = 0; bytLbooleanControlIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanControlIndex) {
								if ((Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLbooleanControlIndex][bytPextensionFileFormat] != null)
									&& this.bolGbooleanIsLocalA[bytLbooleanControlIndex]
									&& (!this.isPattern(intLpreviousPatternIndex) || (this.getPatternBooleanValue(	intLpreviousPatternIndex,
																													bytLbooleanControlIndex,
																													bytPinitialOrCurrent) != this.getPatternBooleanValue(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
																																											bytLbooleanControlIndex,
																																											bytPinitialOrCurrent)))) {
									objLlocalParametersArrayList.add(this.getBooleanParameterDefinitionString(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
																												bytLbooleanControlIndex,
																												bytPextensionFileFormat,
																												bytPinitialOrCurrent,
																												false));
								}
							}
						}

						// Prepare byte parameters :
						for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
							if ((Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLbyteControlIndex][bytPextensionFileFormat] != null)
								&& (this.bolGbyteIsLocalA[bytLbyteControlIndex] || bytPextensionFileFormat == Constants.bytS_EXTENSION_JM)
								&& (!this.isPattern(intLpreviousPatternIndex) || (bytLbyteControlIndex == Constants.bytS_BYTE_LOCAL_HEIGHT)
									|| (bytLbyteControlIndex == Constants.bytS_BYTE_LOCAL_DWELL) || (this.getPatternByteValue(	intLpreviousPatternIndex,
																																bytLbyteControlIndex,
																																bytPinitialOrCurrent) != this.getPatternByteValue(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
																																													bytLbyteControlIndex,
																																													bytPinitialOrCurrent)))) {
								objLlocalParametersArrayList.add(this.getByteParameterDefinitionString(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
																										bytLbyteControlIndex,
																										bytPextensionFileFormat,
																										bytPinitialOrCurrent,
																										false));
							}
						}

						// Prepare string parameters :
						if (bytPextensionFileFormat == Constants.bytS_EXTENSION_JMP) {
							for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
								if ((bytLstringControlIndex != Constants.bytS_STRING_LOCAL_PATTERN)
									&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_SITESWAP)
									&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP)
									&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_REVERSE_COLORS)
									&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_STYLE)
									&& (Constants.strS_STRING_LOCAL_CONTROL_A[bytLstringControlIndex] != null)
									&& this.bolGstringIsLocalA[bytLstringControlIndex]
									&& (!this.isPattern(intLpreviousPatternIndex) || (bytLstringControlIndex == Constants.bytS_STRING_LOCAL_COLORS) || ((this.getPatternStringValue(intLpreviousPatternIndex,
																																													bytLstringControlIndex,
																																													bytPinitialOrCurrent) != null) && !this	.getPatternStringValue(	intLpreviousPatternIndex,
																																																													bytLstringControlIndex,
																																																													bytPinitialOrCurrent)
																																																							.equals(this.getPatternStringValue(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
																																																																bytLstringControlIndex,
																																																																bytPinitialOrCurrent))))) {
									objLlocalParametersArrayList.add(this.getStringParameterDefinitionString(	intLfilteredObjectIndexA[intLfilteredObjectIndex],
																												bytLstringControlIndex,
																												bytPinitialOrCurrent,
																												false));
								}
							}
						}

						// Prepare Start Pattern parameter :
						if (bytPextensionFileFormat == Constants.bytS_EXTENSION_JMP
							&& intLfilteredObjectIndexA[intLfilteredObjectIndex] == this.getPatternsFileManager().intGstartObjectIndex) {
							objLlocalParametersArrayList.add(Strings.doConcat(	'#',
																				Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_START_PATTERN][0],
																				" = true"));
						}

						// Sort & write parameters :
						Collections.sort(objLlocalParametersArrayList, this);

						for (final String strLlocalParameter : objLlocalParametersArrayList) {
							objLtoStringBuilder.append(Strings.doConcat(strLlocalParameter, Strings.strS_LINE_SEPARATOR));
						}

						// Pattern siteswap & name :
						final Pattern objLpattern = (Pattern) this.objGobjectA[intLfilteredObjectIndexA[intLfilteredObjectIndex]];

						objLtoStringBuilder.append(objLpattern.toString(bytPextensionFileFormat,
																		bytPinitialOrCurrent,
																		bolPstyles,
																		false,
																		false,
																		bolPsiteswaps,
																		false,
																		objPlanguage.getPropertyValueString(Language.intS_LABEL_REVERSE),
																		objPlanguage.getPropertyValueString(Language.intS_LABEL_INVALID)));
						intLpreviousPatternIndex = intLfilteredObjectIndexA[intLfilteredObjectIndex];
					} else {
						continue;
					}

					objLtoStringBuilder.append(Strings.strS_LINE_SEPARATOR);
				}
			}
		}
		return objLtoStringBuilder.toString();
	}

	final private String toHeaderParametersString(byte bytPinitialOrCurrent, byte bytPfileExtension) {
		final ArrayList<String> strLparameterAL =
													new ArrayList<String>(Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
																			+ Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER
																			+ Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER);

		// Header boolean parameters :
		final byte bytLcontrolStringTypeIndex = bytPfileExtension;
		for (byte bytLbooleanControlIndex = 0; bytLbooleanControlIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanControlIndex) {
			if (Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLbooleanControlIndex][bytLcontrolStringTypeIndex] != null) {
				if (bytPfileExtension == Constants.bytS_EXTENSION_JMP
					&& (this.bolGbooleanIsFileDefinedA[bytLbooleanControlIndex] || (this.bolGbooleanIsUserDefinedA[bytLbooleanControlIndex] && (bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT)))
					&& !this.bolGbooleanIsLocalA[bytLbooleanControlIndex] || bytPfileExtension == Constants.bytS_EXTENSION_JM) {
					final String strLbooleanParameterDefinition =
																	this.getBooleanParameterDefinitionString(	Constants.bytS_UNCLASS_NO_VALUE,
																												bytLbooleanControlIndex,
																												bytPfileExtension,
																												bytPinitialOrCurrent,
																												false);
					if (strLbooleanParameterDefinition != null) {
						strLparameterAL.add(strLbooleanParameterDefinition);
					}
				}

				if (this.bolGbooleanIsLocallyDefinedA[bytLbooleanControlIndex] && bytPfileExtension == Constants.bytS_EXTENSION_JMP) {
					strLparameterAL.add(this.getBooleanParameterDefinitionString(	Constants.bytS_UNCLASS_NO_VALUE,
																					bytLbooleanControlIndex,
																					bytPfileExtension,
																					bytPinitialOrCurrent,
																					true));
				}
			}
		}

		// Header byte parameters :
		for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
			if (Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLbyteControlIndex][bytPfileExtension] != null) {
				if (bytPfileExtension == Constants.bytS_EXTENSION_JMP
					&& (this.bolGbyteIsFileDefinedA[bytLbyteControlIndex] || (this.bolGbyteIsUserDefinedA[bytLbyteControlIndex] && (bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT)))
					&& !this.bolGbyteIsLocalA[bytLbyteControlIndex] || bytPfileExtension == Constants.bytS_EXTENSION_JM
					&& bytLbyteControlIndex != Constants.bytS_BYTE_LOCAL_HEIGHT && bytLbyteControlIndex != Constants.bytS_BYTE_LOCAL_DWELL) {
					final String strLbyteParameterDefinition =
																this.getByteParameterDefinitionString(	Constants.bytS_UNCLASS_NO_VALUE,
																										bytLbyteControlIndex,
																										bytPfileExtension,
																										bytPinitialOrCurrent,
																										false);
					if (strLbyteParameterDefinition != null) {
						strLparameterAL.add(strLbyteParameterDefinition);
					}
				}

				if (this.bolGbyteIsLocallyDefinedA[bytLbyteControlIndex] && bytPfileExtension == Constants.bytS_EXTENSION_JMP) {
					strLparameterAL.add(this.getByteParameterDefinitionString(	Constants.bytS_UNCLASS_NO_VALUE,
																				bytLbyteControlIndex,
																				bytPfileExtension,
																				bytPinitialOrCurrent,
																				true));
				}
			}
		}

		// Header string parameters :
		if (bytPfileExtension == Constants.bytS_EXTENSION_JMP) {
			for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
				if ((bytLstringControlIndex != Constants.bytS_STRING_LOCAL_PATTERN)
					&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_SITESWAP)
					&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP)
					&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_STYLE)
					&& (Constants.strS_STRING_LOCAL_CONTROL_A[bytLstringControlIndex] != null)) {
					if ((this.bolGstringIsFileDefinedA[bytLstringControlIndex] || (this.bolGstringIsUserDefinedA[bytLstringControlIndex] && (bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT)))
						&& !this.bolGstringIsLocalA[bytLstringControlIndex]) {
						strLparameterAL.add(this.getStringParameterDefinitionString(Constants.bytS_UNCLASS_NO_VALUE,
																					bytLstringControlIndex,
																					bytPinitialOrCurrent,
																					false));
					}

					if (this.bolGstringIsLocallyDefinedA[bytLstringControlIndex]) {
						strLparameterAL.add(this.getStringParameterDefinitionString(Constants.bytS_UNCLASS_NO_VALUE,
																					bytLstringControlIndex,
																					bytPinitialOrCurrent,
																					true));
					}
				}
			}
		}

		// BC parameter special case :
		if (bytPfileExtension == Constants.bytS_EXTENSION_JM
			&& (this.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] || this.bolGstringIsUserDefinedA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY])) {
			strLparameterAL.add(Strings.doConcat(	'#',
													Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_BACKGROUND][1],
													'=',
													Tools.getPenColorString(Tools.getPenColor(this.getStringValue(	Constants.bytS_STRING_LOCAL_BACKGROUND_DAY,
																													bytPinitialOrCurrent)),
																			false)));
		}

		// Sort & write header parameters :
		Collections.sort(strLparameterAL, this);
		final StringBuilder objLtoStringBuilder =
													new StringBuilder((Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
																		+ Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER + Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER) * 16);
		for (final String strLparameter : strLparameterAL) {
			objLtoStringBuilder.append(Strings.doConcat(strLparameter, Strings.strS_LINE_SEPARATOR));
		}

		return objLtoStringBuilder.toString();
	}

	final private String toHeaderString(byte bytPfileFormat, Language objPlanguage) {
		// File header :
		StringBuilder objLtoStringBuilder =
											new StringBuilder(objPlanguage.getPropertyValueString(bytPfileFormat == Constants.bytS_EXTENSION_JMP
																																				? Language.intS_MESSAGE_PATTERNS_JMP_FILE_HEADER
																																				: bytPfileFormat == Constants.bytS_EXTENSION_JM
																																																? Language.intS_MESSAGE_PATTERNS_JM_FILE_HEADER
																																																: bytPfileFormat == Constants.bytS_EXTENSION_JAP
																																																												? Language.intS_MESSAGE_PATTERNS_JAP_FILE_HEADER
																																																												: Constants.bytS_UNCLASS_NO_VALUE));

		final String strLtitleToken = "<title>";
		final int intLtitleIndex = objLtoStringBuilder.toString().toLowerCase().indexOf(strLtitleToken);
		if (intLtitleIndex >= 0) {
			objLtoStringBuilder =
									new StringBuilder(Strings.doConcat(	objLtoStringBuilder.substring(0, intLtitleIndex),
																		this.getPatternsFileManager().getTitlesString(),
																		objLtoStringBuilder.substring(intLtitleIndex + strLtitleToken.length())));
		}
		return objLtoStringBuilder.toString();
	}

	final private String toHeaderStylesString(byte bytPinitialOrCurrent, byte bytPfileFormat, int[] intPfilteredObjectIndexA) {
		final int intLstylesNumber = this.strGstyleAL.size();
		final StringBuilder objLtoStringBuilder = new StringBuilder();
		if (bytPinitialOrCurrent == Constants.bytS_UNCLASS_INITIAL) {
			for (int intLstyleIndex = 0; intLstyleIndex < intLstylesNumber; ++intLstyleIndex) {
				final Style objLstyle = this.getStyle(this.strGstyleAL.get(intLstyleIndex));

				if (!objLstyle.strGstyle.equals(Constants.strS_STRING_LOCAL_STYLE_DEFAULT)
					&& (objLstyle.isJMStyle() || bytPfileFormat == Constants.bytS_EXTENSION_JMP)) {
					objLtoStringBuilder.append(Strings.doConcat(objLstyle.toString(bytPfileFormat, true), Strings.strS_LINE_SEPARATOR));
				}
			}
		} else if (bytPinitialOrCurrent == Constants.bytS_UNCLASS_CURRENT) {
			final HashSet<String> objLselectedStylesHashSet = new HashSet<String>(intLstylesNumber);
			if (intPfilteredObjectIndexA != null) {
				for (int intLfilteredObjectIndex = 0; intLfilteredObjectIndex < intPfilteredObjectIndexA.length; ++intLfilteredObjectIndex) {
					if (this.objGobjectA[intPfilteredObjectIndexA[intLfilteredObjectIndex]] instanceof Pattern
						&& this.getPatternBooleanValue(	intPfilteredObjectIndexA[intLfilteredObjectIndex],
														Constants.bytS_BOOLEAN_LOCAL_EDITION,
														bytPinitialOrCurrent)) {
						objLselectedStylesHashSet.add(((Pattern) this.objGobjectA[intPfilteredObjectIndexA[intLfilteredObjectIndex]]).strGlocalAA[Constants.bytS_STRING_LOCAL_STYLE][Constants.bytS_UNCLASS_CURRENT]);
					}
				}
			}
			for (int intLstyleIndex = 0; intLstyleIndex < intLstylesNumber; ++intLstyleIndex) {
				final String strLstyle = this.strGstyleAL.get(intLstyleIndex);
				if (!strLstyle.equals(Constants.strS_STRING_LOCAL_STYLE_DEFAULT) && objLselectedStylesHashSet.contains(strLstyle)) {
					final Style objLstyle = this.getStyle(strLstyle);

					if (objLstyle.isJMStyle() || bytPfileFormat == Constants.bytS_EXTENSION_JMP) {
						objLtoStringBuilder.append(Strings.doConcat(objLstyle.toString(bytPfileFormat, true), Strings.strS_LINE_SEPARATOR));
					}
				}
			}
		}
		return objLtoStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bolPjMPFileFormat
	 * @param bolPdetailed
	 * @param objPlanguage
	 * @return
	 */
	final public String toPatternString(int intPobjectIndex, byte bytPfileFormat, boolean bolPdetailed, Language objPlanguage) {
		final StringBuilder objLtoStringBuilder = new StringBuilder(bolPdetailed ? 2048 : 1024);

		if (this.isPattern(intPobjectIndex)) {
			final ArrayList<String> strLparameterAL =
														new ArrayList<String>(bolPdetailed ? Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
																								+ Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER
																								+ Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER : 3);

			// Boolean parameter values :
			if (bolPdetailed) {
				for (byte bytLbooleanControlIndex = 0; bytLbooleanControlIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbooleanControlIndex) {
					if ((Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLbooleanControlIndex][bytPfileFormat] != null)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_ERRORS)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_WARNINGS)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_INFO)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_EDITION)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_SPEED)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_FLUIDITY)
						&& (bytLbooleanControlIndex != Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE)) {
						final String strLbooleanParameterDefinition =
																		this.getBooleanParameterDefinitionString(	intPobjectIndex,
																													bytLbooleanControlIndex,
																													bytPfileFormat,
																													Constants.bytS_UNCLASS_CURRENT,
																													false);
						if (strLbooleanParameterDefinition != null) {
							strLparameterAL.add(strLbooleanParameterDefinition);
						}
					}
				}
			}

			// Byte parameter values :
			for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
				if ((Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLbyteControlIndex][bytPfileFormat] != null)
					&& (bolPdetailed || (bytLbyteControlIndex == Constants.bytS_BYTE_LOCAL_HEIGHT) || (bytLbyteControlIndex == Constants.bytS_BYTE_LOCAL_DWELL))) {
					final String strLbyteParameterDefinition =
																this.getByteParameterDefinitionString(	intPobjectIndex,
																										bytLbyteControlIndex,
																										bytPfileFormat,
																										Constants.bytS_UNCLASS_CURRENT,
																										false);
					if (strLbyteParameterDefinition != null) {
						strLparameterAL.add(strLbyteParameterDefinition);
					}
				}
			}

			// String parameter values :
			for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
				if ((bytLstringControlIndex != Constants.bytS_STRING_LOCAL_PATTERN) && (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_STYLE)
					&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_SITESWAP)
					&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP)
					&& (bytLstringControlIndex != Constants.bytS_STRING_LOCAL_REVERSE_COLORS)
					&& (Constants.strS_STRING_LOCAL_CONTROL_A[bytLstringControlIndex] != null)
					&& (bolPdetailed || (bytLstringControlIndex == Constants.bytS_STRING_LOCAL_COLORS))) {
					strLparameterAL.add(this.getStringParameterDefinitionString(intPobjectIndex,
																				bytLstringControlIndex,
																				Constants.bytS_UNCLASS_CURRENT,
																				false));
				}
			}

			// Sort parameter values :
			Collections.sort(strLparameterAL, this);

			for (final String strLparameter : strLparameterAL) {
				objLtoStringBuilder.append(Strings.doConcat(strLparameter, Strings.strS_LINE_SEPARATOR));
			}

			// Pattern :
			objLtoStringBuilder.append(((Pattern) this.objGobjectA[intPobjectIndex]).toString(	bytPfileFormat,
																								Constants.bytS_UNCLASS_CURRENT,
																								true,
																								true,
																								this.getPatternBooleanValue(intPobjectIndex,
																															Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE),
																								true,
																								this.getPatternBooleanValue(intPobjectIndex,
																															Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP),
																								objPlanguage.getPropertyValueString(Language.intS_LABEL_REVERSE),
																								objPlanguage.getPropertyValueString(Language.intS_LABEL_INVALID)));
		}

		return objLtoStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPlanguage
	 * @return
	 */
	final public String toString(Language objPlanguage) {
		return this.toFileString(Constants.bytS_UNCLASS_CURRENT, Constants.bytS_EXTENSION_JMP, true, true, true, null, null, objPlanguage);
	}

	public boolean[]					bolGbooleanIsFileDefinedA;

	public boolean[]					bolGbooleanIsLocalA;

	public boolean[]					bolGbooleanIsLocallyDefinedA;

	public boolean[]					bolGbooleanIsUserDefinedA;

	public boolean[]					bolGbyteIsFileDefinedA;

	public boolean[]					bolGbyteIsLocalA;

	public boolean[]					bolGbyteIsLocallyDefinedA;

	public boolean[]					bolGbyteIsUserDefinedA;

	public Boolean[][]					bolGglobalValueAA;

	public boolean[]					bolGstringIsFileDefinedA;

	public boolean[]					bolGstringIsLocalA;

	public boolean[]					bolGstringIsLocallyDefinedA;

	public boolean[]					bolGstringIsUserDefinedA;

	public Byte[][]						bytGglobalValueAA;

	public byte							bytGpatternsManagerType;

	public int							intGpatternsNumber;

	public int[]						intGshortcutIndexA;

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPinitialOrCurrent
	 * @param bolPjMPFileFormat
	 * @param objPlanguage
	 * @return
	 */
	// final public String toFileString(byte bytPinitialOrCurrent, boolean bolPjMPFileFormat, JuggleLanguage objPlanguage) {
	// return this.toFileString(bytPinitialOrCurrent, bolPjMPFileFormat, false, null, null, objPlanguage);
	// }

	public Object[]						objGobjectA;

	private final PatternsFilesManager	objGpatternsFileManager;

	public HashMap<String, Style>		objGstylesHashMap;

	public String[][]					strGglobalAA;

	public ArrayList<String>			strGstyleAL;

	@SuppressWarnings("unused")
	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	// final private static void log(Object... objPobjectA) {
	// if (JugglePatternsManager.bolS_UNCLASS_LOG) {
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
	// JugglePatternsManager.log("In  :");
	// }
	//
	// /* @Override */
	//
	// /**
	// * Method description
	// *
	// * @see
	// */
	// @SuppressWarnings("unused") final private static void logOut() {
	// JugglePatternsManager.log("Out :");
	// }
}

/*
 * @(#)JugglePatternsManager.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
