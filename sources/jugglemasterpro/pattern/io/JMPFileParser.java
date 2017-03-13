package jugglemasterpro.pattern.io;

import java.awt.Color;
import java.util.ArrayList;

import jugglemasterpro.pattern.BallsColors;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

public final class JMPFileParser extends PatternsFileParser {

	public JMPFileParser(PatternsFilesManager objPpatternsFilesManager) {
		super(objPpatternsFilesManager);
		this.bytGpatternsFileExtension = Constants.bytS_EXTENSION_JMP;
	}

	@SuppressWarnings("unused") final private static boolean doCheckMnemonicValue(byte bytPmnemonic, String strPvalue, boolean bolPuserValueNotAllowed) {
		switch (bytPmnemonic) {
			case JMPFileParser.bytS_ITEM_SKILL:
				return JMPFileParser.getSkill(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_MARK:
				return JMPFileParser.getMark(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_COLORS:
				return JMPFileParser.getBallsColors(strPvalue, bolPuserValueNotAllowed).getState();
			case JMPFileParser.bytS_ITEM_TRAIL:
				return JMPFileParser.getBallsTrail(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_HEIGHT:
				return JMPFileParser.getHeight(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_DWELL:
				return JMPFileParser.getDwell(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_FLUIDITY:
				return JMPFileParser.getFluidity(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_SPEED:
				return JMPFileParser.getSpeed(strPvalue, bolPuserValueNotAllowed)[1] != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_DEFAULTS:
				return JMPFileParser.getDefaults(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			case JMPFileParser.bytS_ITEM_STROBE:
				return JMPFileParser.getStrobe(strPvalue, bolPuserValueNotAllowed) != Constants.bytS_UNCLASS_NO_VALUE;
			default:
				return false;
		}
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	@SuppressWarnings("unused")
	final private static Byte getAlternateColors(String strPalternateColors, boolean bolPuserValueNotAllowed) {

		if (strPalternateColors.equalsIgnoreCase("catch")) {
			return Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
		}
		if (strPalternateColors.equalsIgnoreCase("count") || Strings.unspace(strPalternateColors).equalsIgnoreCase("/t")
			|| strPalternateColors.equalsIgnoreCase("t")) {
			return Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_COUNT;
		}

		final byte bytLalternateColorsRatio =
												PatternsFileParser.getByteValue(strPalternateColors.substring(strPalternateColors.startsWith("/")
																																					? 1
																																					: 0),
																				0,
																				Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE
																					- Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MINIMUM_RATE_VALUE
																					+ 1,
																				true,
																				bolPuserValueNotAllowed);
		if (bytLalternateColorsRatio == 0) {
			return Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
		}
		if (bytLalternateColorsRatio != Constants.bytS_UNCLASS_NO_VALUE) {
			return (byte) Math.min(	Math.max(	Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE - bytLalternateColorsRatio + 1,
												Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MINIMUM_RATE_VALUE),
									Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE);
		}
		return bytLalternateColorsRatio;
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static BallsColors getBallsColors(String strPballsColors, boolean bolPuserValueNotAllowed) {
		return new BallsColors(strPballsColors);
	}

	final private static Byte getBallsTrail(String strPballs, boolean bolPuserValueNotAllowed) {

		if (strPballs.equalsIgnoreCase("full")) {
			return Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL;
		}
		return PatternsFileParser.getByteValue(	strPballs,
												Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE,
												Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL,
												false,
												bolPuserValueNotAllowed);
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static Byte getDefaults(String strPdefaults, boolean bolPuserValueNotAllowed) {
		return PatternsFileParser.getByteValue(	strPdefaults,
												Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE,
												Constants.bytS_BYTE_LOCAL_DEFAULTS_MAXIMUM_VALUE,
												false,
												bolPuserValueNotAllowed);
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static Byte getDwell(String strPdwell, boolean bolPuserValueNotAllowed) {
		return PatternsFileParser.getByteValue(	strPdwell.endsWith("%") ? strPdwell.substring(0, strPdwell.length() - 1) : strPdwell,
												Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE,
												Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE,
												true,
												bolPuserValueNotAllowed);
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static Byte getFluidity(String strPfluidity, boolean bolPuserValueNotAllowed) {
		return PatternsFileParser.getByteValue(	strPfluidity.endsWith("%") ? strPfluidity.substring(0, strPfluidity.length() - 1) : strPfluidity,
												Constants.bytS_BYTE_LOCAL_FLUIDITY_MINIMUM_VALUE,
												Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE,
												true,
												bolPuserValueNotAllowed);
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static Byte getHeight(String strPheight, boolean bolPuserValueNotAllowed) {
		return PatternsFileParser.getByteValue(	strPheight,
												Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE,
												Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE,
												false,
												bolPuserValueNotAllowed);
	}

	@SuppressWarnings("unused") final private static Byte getJugglerValue(String strPjuggler, boolean bolPuserValueNotAllowed) {

		if (strPjuggler.equalsIgnoreCase(Constants.strS_STRING_LOCAL_USER)) {
			return bolPuserValueNotAllowed ? Constants.bytS_UNCLASS_NO_VALUE : null;
		}
		if (strPjuggler.equalsIgnoreCase("phantom")) {
			return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
		}
		if (strPjuggler.equalsIgnoreCase("visible")) {
			return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
		}
		boolean bolLarms = false;
		boolean bolLhands = false;
		boolean bolLhead = false;
		for (final Object objLtokenObject : Strings.getTokens(strPjuggler)) {
			final String strLtoken = objLtokenObject.toString();
			if (strLtoken.equalsIgnoreCase("visible")) {
				bolLhead = true;
				bolLarms = true;
				bolLhands = true;
			} else if (strLtoken.equalsIgnoreCase("head")) {
				bolLhead = true;
			} else if (strLtoken.equalsIgnoreCase("arms")) {
				bolLarms = true;
			} else if (strLtoken.equalsIgnoreCase("hands")) {
				bolLhands = true;
			} else if (strLtoken.equalsIgnoreCase("phantom")) {
				bolLhead = false;
				bolLarms = false;
				bolLhands = false;
			} else {
				return Constants.bytS_UNCLASS_NO_VALUE;
			}
		}
		if (bolLhead) {
			if (bolLarms) {
				if (bolLhands) {
					return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
				}
				return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS;
			}
			if (bolLhands) {
				return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS;
			}
			return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD;
		}
		if (bolLarms) {
			if (bolLhands) {
				return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS;
			}
			return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS;
		}
		if (bolLhands) {
			return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS;
		}
		return Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;

	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static Byte getMark(String strPmark, boolean bolPuserValueNotAllowed) {
		return PatternsFileParser.getByteValue(	strPmark,
												Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE,
												Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE,
												false,
												bolPuserValueNotAllowed);
	}

	final private static byte getMnemonicItem(char chrPmnemonic) {
		if (chrPmnemonic == Strings.chrS_UNCLASS_NULL_CHAR) {
			return Constants.bytS_UNCLASS_NO_VALUE;
		}
		for (byte bytLmnemonic = 0; bytLmnemonic < JMPFileParser.bytS_ITEMS_NUMBER; ++bytLmnemonic) {
			if (chrPmnemonic == JMPFileParser.chrS_MNEMONIC_A[bytLmnemonic]) {
				return bytLmnemonic;
			}
		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	@SuppressWarnings("unused")
	final private static Color getPenColor(String strPpenColor, byte bytPcontrolIndex, boolean bolPuserValueNotAllowed) {

		String strLpenColor = null;
		if (strPpenColor.equalsIgnoreCase(Constants.strS_STRING_LOCAL_USER)) {
			if (bolPuserValueNotAllowed) {
				return null;
			}
			strLpenColor = Preferences.getLocalStringPreference(bytPcontrolIndex);
		} else {
			strLpenColor = strPpenColor;
		}
		return Tools.getPenColor(strLpenColor);
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static Byte getSkill(String strPskill, boolean bolPuserValueNotAllowed) {

		if (strPskill.equalsIgnoreCase("beginner")) {
			return Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER;
		}
		if (strPskill.equalsIgnoreCase("amateur")) {
			return Constants.bytS_BYTE_LOCAL_SKILL_AMATEUR;
		}
		if (strPskill.equalsIgnoreCase("confirmed")) {
			return Constants.bytS_BYTE_LOCAL_SKILL_CONFIRMED;
		}
		if (strPskill.equalsIgnoreCase("pro")) {
			return Constants.bytS_BYTE_LOCAL_SKILL_PRO;
		}
		if (strPskill.equalsIgnoreCase("impossible")) {
			return Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE;
		}
		return PatternsFileParser.getByteValue(	strPskill,
												Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER,
												Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE,
												true,
												bolPuserValueNotAllowed);
	}

	/**
	 * @param strPvalue
	 * @return
	 */
	final private static Byte[] getSpeed(String strPspeed, boolean bolPuserValueNotAllowed) {
		// TODO : getSpeed retourne un tableau (ctrl, value)
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see jugglemasterpro.pattern.io.PatternsFileParser#doParsePatternsFile(jugglemasterpro.pattern.io.FileBufferedReader, boolean, boolean, boolean, java.lang.String, boolean, int)
	 */
	final private static Byte getStrobe(String strPstrobe, boolean bolPuserValueNotAllowed) {
		if (strPstrobe.equalsIgnoreCase("catch") || strPstrobe.equalsIgnoreCase("\370")) {
			return Constants.bytS_BYTE_LOCAL_STROBE_EACH_CATCH;
		}
		if (strPstrobe.equalsIgnoreCase("count") || Strings.unspace(strPstrobe).equalsIgnoreCase("/t") || strPstrobe.equalsIgnoreCase("t")) {
			return Constants.bytS_BYTE_LOCAL_STROBE_EACH_COUNT;
		}
		return PatternsFileParser.getByteValue(	strPstrobe.startsWith("/") ? strPstrobe.substring(1) : strPstrobe,
												Constants.bytS_BYTE_LOCAL_STROBE_MINIMUM_RATE,
												Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE,
												true,
												bolPuserValueNotAllowed);
	}

	/**
	 * @param strP
	 * @return
	 */
	final private static String uncomment(String strP) {
		final int intLlength = strP.length();
		if (intLlength == 0) {
			return strP;
		}
		final StringBuilder strLuncommented = new StringBuilder(intLlength);
		boolean bolLspace = false;
		final char[] chrLA = strP.toCharArray();
		boolean bolLfirstSpaces = true;
		for (int intLindex = 0; intLindex < intLlength; ++intLindex) {

			// Space :
			if (chrLA[intLindex] == ' ' || chrLA[intLindex] == '\t') {
				if (bolLfirstSpaces) {
					continue;
				}
				bolLspace = true;
				continue;
			}

			// Comment :
			if (chrLA[intLindex] == '#' || chrLA[intLindex] == '/' && intLindex + 1 < intLlength && chrLA[intLindex + 1] == '/') {
				break;
			}

			// Another char :
			if (bolLspace) {
				strLuncommented.append(' ');
			}
			bolLfirstSpaces = bolLspace = false;
			strLuncommented.append(chrLA[intLindex]);
		}
		return new String(chrLA);
	}

	@Override ArrayList<Object> doParsePatternsFile(PatternsFile objPpatternsFile,
													boolean bolPnewLists,
													boolean bolPsiteswaps,
													boolean bolPstyles,
													String strPpatternName,
													boolean bolPonlyThisPattern,
													int intPstartPatternOccurrence) {

		// Returns an arrayList composed of :
		// ArrayList<Object> objects (patterns & comments)
		// ArrayList<Integer> shortcuts
		// Boolean pattern found

		// TODO : JMPFileParser.doParsePatternsFile
		Tools.debug(Strings.doConcat(	"JMPFileParser.doParsePatternsFile(",
										objPpatternsFile.getReferenceString(),
										", ",
										bolPnewLists,
										", ",
										bolPsiteswaps,
										", ",
										bolPstyles,
										", ",
										strPpatternName,
										", ",
										bolPonlyThisPattern,
										", ",
										intPstartPatternOccurrence,
										')'));

		// Declarations :
		boolean bolLsiteswapWithoutStyleErrorEnabled = bolPsiteswaps && bolPstyles;
		boolean bolLpatternFound = false;
		int intLobjectsNumber = bolPnewLists ? 0 : this.objGpatternsManager.objGobjectA.length;

		final int intLstartPatternOccurrence = Math.max(intPstartPatternOccurrence + 1, 1);
		int intLcurrentPatternOccurrence = intLstartPatternOccurrence;
		final ArrayList<Object> objLobjectAL = new ArrayList<Object>(128);
		final ArrayList<Integer> objLshortcutIndexIntegerAL = new ArrayList<Integer>(8);

		// Read the file till founding the pattern(s) :
		while (objPpatternsFile.setNextLineIndex() && (strPpatternName == null || !bolPonlyThisPattern || !bolLpatternFound)) {

			// First char analysis :
			final String strLline = JMPFileParser.uncomment(objPpatternsFile.getLineString());
			if (strLline.length() == 0) {
				continue;
			}
			final char[] chrLlineA = strLline.toCharArray();
			String strLdata = strLline.substring(1).trim();

			// Style definition with no style name associated :
			if (chrLlineA[0] == '{') {
				if (bolPstyles /* && !bolLstyleDefinitionMissingNameError */) {
					this.doAddToConsole(Language.intS_CONSOLE_STYLE_DEFINITION_MISSING_NAME, objPpatternsFile, 0);
				}
				// objPpatternsFile.doSkipStyleDefinition();
				continue;
			}

			// Comment line :
			if (chrLlineA[0] == '/') {
				if (bolPsiteswaps) {

					// Add shortcut :
					if (strLdata.startsWith("[") && strLdata.endsWith("]")) {
						strLdata = strLdata.substring(1, strLdata.length() - 1).trim();
						objLshortcutIndexIntegerAL.add(Integer.valueOf(intLobjectsNumber + (bolPnewLists ? 0 : 2)));
					}

					// Add comment :
					objLobjectAL.add(strLdata);
					++intLobjectsNumber;
				}
				continue;
			}

			// Mnemonic :
			final byte bytLmnemonicItem = JMPFileParser.getMnemonicItem(chrLlineA[0]);
			if (bytLmnemonicItem != Constants.bytS_UNCLASS_NO_VALUE) {
				if (strLdata.length() == 0) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_MISSING_VALUE, objPpatternsFile, 1);

				}
				Byte bytLvalue = Constants.bytS_UNCLASS_NO_VALUE - 1;
				switch (bytLmnemonicItem) {
					case JMPFileParser.bytS_ITEM_COLORS:
						// bytLvalue = JMPFileParser.;
						if (bolPsiteswaps) {
							final BallsColors objLballColors = JMPFileParser.getBallsColors(strLdata, true);

							if (objLballColors.getState() == null) {
								this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_COLORS_MALFORMED_VALUE,
													objPpatternsFile,
													objLballColors.getErrorIndex() + 1);
							} else if (objLballColors.getState() == Boolean.FALSE) {
								this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_COLORS_UNKNOWN_VALUE,
													objPpatternsFile,
													objLballColors.getErrorIndex() + 1);
							}
						}
						break;
					case JMPFileParser.bytS_ITEM_DEFAULTS:
						bytLvalue = JMPFileParser.getDefaults(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_DWELL:
						bytLvalue = JMPFileParser.getDwell(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_FLUIDITY:
						bytLvalue = JMPFileParser.getFluidity(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_HEIGHT:
						bytLvalue = JMPFileParser.getHeight(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_MARK:
						bytLvalue = JMPFileParser.getMark(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_SKILL:
						bytLvalue = JMPFileParser.getSkill(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_SPEED:
						// bytLvalue = JMPFileParser.getSpeed(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_STROBE:
						bytLvalue = JMPFileParser.getStrobe(strLdata, true);
						break;
					case JMPFileParser.bytS_ITEM_STYLE:
						if (bolPstyles) {
							if (strLdata.length() == 0) {
								this.doAddToConsole(Language.intS_CONSOLE_STYLE_NO_NAME, objPpatternsFile, Constants.bytS_UNCLASS_NO_VALUE);
								// objPpatternsFile.doSkipStyleDefinition();
							} else {
								int intLstyleNameIndex = 1;
								while ((intLstyleNameIndex < chrLlineA.length) && (chrLlineA[intLstyleNameIndex] == ' ')) {
									++intLstyleNameIndex;
								}

								if (this.objGpatternsFileStyleParser.doParseStyle(strLdata, intLstyleNameIndex, objPpatternsFile)) {
									this.objGpatternsFilesManager.bolGstyleFound = true;
									bolLsiteswapWithoutStyleErrorEnabled = false;
								}
							}
						} else {
							// objPpatternsFile.doSkipStyleDefinition();
						}
						break;
					case JMPFileParser.bytS_ITEM_TRAIL:
						bytLvalue = JMPFileParser.getBallsTrail(strLdata, true);
						break;
				}

				if (bytLvalue == Constants.bytS_UNCLASS_NO_VALUE - 1) {
					continue;
				}
				if (bytLvalue == Constants.bytS_UNCLASS_NO_VALUE) {
					final int intLerrorType =
												(chrLlineA[0] == '^' ? Language.intS_CONSOLE_PARAMETER_HEIGHT_UNKNOWN_VALUE
																	: chrLlineA[0] == '£' ? Language.intS_CONSOLE_PARAMETER_SKILL_UNKNOWN_VALUE
																							: Language.intS_CONSOLE_PARAMETER_DWELL_UNKNOWN_VALUE);
					final int intLerrorIndex = objPpatternsFile.getLineString().indexOf(strLdata, 1);
					this.doAddToConsole(intLerrorType, objPpatternsFile, intLerrorIndex);
				}
				continue;
			}

			// Parameter :
			final ArrayList<Object> objLtokenAndIndexAL = Strings.getTokens(strLline, 2, true, '=');
			final String strLparameter = ((String) (objLtokenAndIndexAL.get(0)));
			boolean bolLparameterFound = false;
			for (int intLindex = 0; intLindex < JMPFileParser.bytS_ITEMS_NUMBER; ++intLindex) {
				if (strLparameter.equalsIgnoreCase(JMPFileParser.strS_ITEM_A[intLindex])) {
					bolLparameterFound = true;
					final Object[] objLstyleParameterObjectA =
																this.objGpatternsFileParameterParser.doParseParameter(	objPpatternsFile,
																														bolPnewLists,
																														bolPstyles,
																														bolPsiteswaps);
					if (objLstyleParameterObjectA != null) {
						if (bolPstyles) {
							if (this.objGpatternsFileStyleParser.doParseStyle(	(String) objLstyleParameterObjectA[0],
																				((Integer) objLstyleParameterObjectA[1]).intValue(),
																				objPpatternsFile)) {
								this.objGpatternsFilesManager.bolGstyleFound = true;
								bolLsiteswapWithoutStyleErrorEnabled = false;
							}
						} else {
							// objPpatternsFile.doSkipStyleDefinition();
						}
					}
					break;
				}
			}
			if (bolLparameterFound) {
				continue;
			}

			// Siteswap & pattern name :
			chrLlineA[0] = Character.toLowerCase(chrLlineA[0]);
			if (((chrLlineA[0] >= '0') && (chrLlineA[0] <= '9')) || ((chrLlineA[0] >= 'a') && (chrLlineA[0] <= 'z')) || (chrLlineA[0] == '(')
				|| (chrLlineA[0] == '"') || (chrLlineA[0] == '\'') || (chrLlineA[0] == '[')) {
				if (bolPsiteswaps
					&& (bolPstyles || this.objGpatternsManager.getBooleanValue(Constants.bytS_BOOLEAN_LOCAL_EDITION, Constants.bytS_UNCLASS_INITIAL))) {

					final int intLreturnedPatternOccurrence =
																this.doParseSiteswap(	objLobjectAL,
																						objPpatternsFile,
																						strPpatternName,
																						bolPonlyThisPattern,
																						intLstartPatternOccurrence,
																						intLcurrentPatternOccurrence,
																						bolLsiteswapWithoutStyleErrorEnabled,
																						bolPstyles);
					if (intLreturnedPatternOccurrence >= 0) {
						bolLpatternFound = !bolPonlyThisPattern || intLreturnedPatternOccurrence == 1;
						bolLsiteswapWithoutStyleErrorEnabled = false;
						++intLobjectsNumber;
						if (strPpatternName != null && intLreturnedPatternOccurrence > 0) {
							intLcurrentPatternOccurrence = intLreturnedPatternOccurrence;
						}
					}
				}
			} else {
				this.doAddToConsole(Language.intS_CONSOLE_NO_SENSE_ERROR, objPpatternsFile, objPpatternsFile.getLineString().indexOf(chrLlineA[0]));
			}
		}

		this.objLreturnedAL.add(objLobjectAL);
		this.objLreturnedAL.add(objLshortcutIndexIntegerAL);
		this.objLreturnedAL.add(bolLpatternFound);
		return this.objLreturnedAL;
	}

	@SuppressWarnings("unused")
	final private boolean setBallsColors(BallsColors objPballsColors) {

		if (objPballsColors.getState() != Boolean.FALSE) {
			this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_COLORS, objPballsColors.getBallsColorsString());
			this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_COLORS] =
																									this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_REVERSE_COLORS] =
																																																	true;
			return true;
		}
		return false;
	}

	final ArrayList<Object>			objLreturnedAL			= new ArrayList<Object>(3);

	final private static byte		bytS_ITEM_BACKGROUND	= 0;
	final private static byte		bytS_ITEM_BALLS			= 1;
	final private static byte		bytS_ITEM_COLORS		= 2;
	final private static byte		bytS_ITEM_DEFAULTS		= 3;
	final private static byte		bytS_ITEM_DWELL			= 4;
	final private static byte		bytS_ITEM_EDITION		= 5;
	final private static byte		bytS_ITEM_FLUIDITY		= 6;
	final private static byte		bytS_ITEM_FX			= 7;
	final private static byte		bytS_ITEM_HEIGHT		= 8;
	final private static byte		bytS_ITEM_JUGGLER		= 9;
	final private static byte		bytS_ITEM_LOG			= 10;
	final private static byte		bytS_ITEM_MARK			= 11;
	final private static byte		bytS_ITEM_MIRROR		= 12;
	final private static byte		bytS_ITEM_PATTERN		= 13;
	final private static byte		bytS_ITEM_SITESWAP		= 14;
	final private static byte		bytS_ITEM_SKILL			= 15;
	final private static byte		bytS_ITEM_SOUNDS		= 16;
	final private static byte		bytS_ITEM_SPEED			= 17;
	final private static byte		bytS_ITEM_START			= 18;
	final private static byte		bytS_ITEM_STROBE		= 19;
	final private static byte		bytS_ITEM_STYLE			= 20;
	final private static byte		bytS_ITEM_TITLE			= 21;
	final private static byte		bytS_ITEM_TRAIL			= 22;

	final private static byte		bytS_ITEMS_NUMBER;
	final private static char[]		chrS_MNEMONIC_A;
	@SuppressWarnings("unused")
	final private static long		serialVersionUID		= Constants.lngS_ENGINE_VERSION_NUMBER;
	final private static String[]	strS_ITEM_A;
	static {
		bytS_ITEMS_NUMBER = JMPFileParser.bytS_ITEM_TRAIL + 1;
		chrS_MNEMONIC_A = new char[JMPFileParser.bytS_ITEMS_NUMBER];
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_BACKGROUND] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_BALLS] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_COLORS] = ':';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_DEFAULTS] = '>';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_DWELL] = '@';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_EDITION] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_FLUIDITY] = '&';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_FX] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_HEIGHT] = '^';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_JUGGLER] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_LOG] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_MARK] = '*';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_MIRROR] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_PATTERN] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_SITESWAP] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_SKILL] = '£';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_SOUNDS] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_SPEED] = '$';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_START] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_STROBE] = '¤';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_STYLE] = '%';
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_TITLE] = Strings.chrS_UNCLASS_NULL_CHAR;
		JMPFileParser.chrS_MNEMONIC_A[JMPFileParser.bytS_ITEM_TRAIL] = '~';

		strS_ITEM_A = new String[JMPFileParser.bytS_ITEMS_NUMBER];
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_BACKGROUND] = "background";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_BALLS] = "balls";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_COLORS] = "colors";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_DEFAULTS] = "defaults";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_DWELL] = "dwell";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_EDITION] = "edition";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_FLUIDITY] = "fluidity";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_FX] = "fx";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_HEIGHT] = "height";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_JUGGLER] = "juggler";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_LOG] = "log";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_MARK] = "mark";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_MIRROR] = "mirror";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_PATTERN] = "pattern";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_SITESWAP] = "siteswap";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_SKILL] = "skill";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_SOUNDS] = "sounds";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_SPEED] = "speed";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_START] = "start";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_STROBE] = "strobe";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_STYLE] = "style";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_TITLE] = "title";
		JMPFileParser.strS_ITEM_A[JMPFileParser.bytS_ITEM_TRAIL] = "trail";
	}
}
