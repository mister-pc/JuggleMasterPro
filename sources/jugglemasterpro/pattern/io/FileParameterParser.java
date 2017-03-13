package jugglemasterpro.pattern.io;

import java.awt.Color;

import jugglemasterpro.pattern.BallsColors;
import jugglemasterpro.pattern.Pattern;
import jugglemasterpro.pattern.PatternsManager;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

final public class FileParameterParser {

	public FileParameterParser(PatternsManager objPpatternsManager) {
		this.objGpatternsManager = objPpatternsManager;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPalternateColors
	 * @return
	 */
	final private static byte getAlternateColorsValue(String strPalternateColors) {
		if (strPalternateColors.equalsIgnoreCase("catch") || strPalternateColors.equalsIgnoreCase("ï¿½")) {
			return Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
		}
		if (strPalternateColors.equalsIgnoreCase("count") || Strings.unspace(strPalternateColors).equalsIgnoreCase("/t")
			|| strPalternateColors.equalsIgnoreCase("t")) {
			return Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_COUNT;
		}

		byte bytLvalue;
		try {
			bytLvalue = Byte.parseByte(strPalternateColors.substring(strPalternateColors.startsWith("/") ? 1 : 0));
		} catch (final Throwable objPthrowable) {
			return Constants.bytS_UNCLASS_NO_VALUE;
		}

		return (((bytLvalue > 0) && (bytLvalue <= Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE
													- Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MINIMUM_RATE_VALUE + 1))
																															? (byte) Math.min(	Math.max(	Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE
																																								- bytLvalue
																																								+ 1,
																																							Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MINIMUM_RATE_VALUE),
																																				Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE)
																															: bytLvalue == 0
																																			? Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH
																																			: Constants.bytS_UNCLASS_NO_VALUE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPballs
	 * @return
	 */
	final private static byte getBallsValue(String strPballs) {
		byte bytLvalue;

		try {
			bytLvalue = Byte.parseByte(strPballs);
		} catch (final Throwable objPthrowable) {
			return (strPballs.equalsIgnoreCase("trail") ? Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL : Constants.bytS_UNCLASS_NO_VALUE);
		}

		return (((bytLvalue >= Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE) && (bytLvalue < Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL))
																																			? bytLvalue
																																			: Constants.bytS_UNCLASS_NO_VALUE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPjuggler
	 * @return
	 */
	final private static byte getJugglerValue(String strPjuggler) {

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
	 * Method description
	 * 
	 * @see
	 * @param strPskill
	 * @return
	 */
	final public static byte getSkillValue(String strPskill) {
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

		byte bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
		try {
			bytLvalue = Byte.parseByte(strPskill);
		} catch (final Throwable objPthrowable) {
			bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
		}
		return Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER <= bytLvalue && bytLvalue <= Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE
																																? bytLvalue
																																: Constants.bytS_UNCLASS_NO_VALUE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPstrobe
	 * @return
	 */
	final private static byte getStrobeValue(String strPstrobe) {
		if (strPstrobe.equalsIgnoreCase("catch") || strPstrobe.equalsIgnoreCase("\370")) {
			return Constants.bytS_BYTE_LOCAL_STROBE_EACH_CATCH;
		}

		if (strPstrobe.equalsIgnoreCase("count") || Strings.unspace(strPstrobe).equalsIgnoreCase("/t") || strPstrobe.equalsIgnoreCase("t")) {
			return Constants.bytS_BYTE_LOCAL_STROBE_EACH_COUNT;
		}

		byte bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
		try {
			bytLvalue = Byte.parseByte(strPstrobe.startsWith("/") ? strPstrobe.substring(1).trim() : strPstrobe);
		} catch (final Throwable objPthrowable) {
			return Constants.bytS_UNCLASS_NO_VALUE;
		}
		return (byte) Math.max(Constants.bytS_BYTE_LOCAL_STROBE_MINIMUM_RATE, Math.min(bytLvalue, Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param objPpatternsParameter
	 * @param objPpatternsBufferedReader
	 * @param bolPnewLists
	 * @param bolPstyles
	 * @param bolPsiteswaps
	 * @return
	 */
	final private Object[] deprecatedDoParseBooleanParameter(	byte bytPcontrolType,
																FileParameter objPpatternsParameter,
																PatternsFile objPpatternsBufferedReader,
																boolean bolPnewLists,
																boolean bolPstyles,
																boolean bolPsiteswaps) {

		final Object objLparameterValueTypeObject = Tools.getParameterValueType(objPpatternsParameter.getString());
		final boolean bolLparameter = bolPsiteswaps && (bolPnewLists || this.objGpatternsManager.bolGbooleanIsLocalA[bytPcontrolType]);

		// Style definition :
		if ((bytPcontrolType == Constants.bytS_BOOLEAN_LOCAL_STYLE) && !(objLparameterValueTypeObject instanceof Boolean)
			&& !(objLparameterValueTypeObject instanceof Character)) {
			return (bolPstyles ? new Object[] {
				Strings.uncomment(objPpatternsBufferedReader.getLineString()).substring(objPpatternsParameter.getValuesIndex()),
				objPpatternsParameter.getValuesIndex() } : null);
		}

		// Boolean parameter name with a byte parameter value :
		if (objLparameterValueTypeObject instanceof Byte) {
			final byte bytLvalue = ((Byte) objLparameterValueTypeObject).byteValue();

			switch (bytPcontrolType) {

				// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
					return (bolLparameter ? this.deprecatedDoParseByteParameter(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS,
																				objPpatternsParameter,
																				objPpatternsBufferedReader,
																				bolPnewLists,
																				bolPstyles,
																				bolPsiteswaps) : null);

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
					if (bolLparameter
						&& objPpatternsParameter.getName()
												.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][1])) {

						final byte bytLbyteControlType =
															this.objGpatternsManager.getBooleanValue(	Constants.bytS_BOOLEAN_LOCAL_LIGHT,
																										Constants.bytS_UNCLASS_INITIAL)
																																		? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																		: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT;
						this.objGpatternsManager.setByte(bytLbyteControlType, (bytLvalue > 0) ? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL
																								: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM);
						this.objGpatternsManager.bolGbyteIsFileDefinedA[bytLbyteControlType] = true;

						break;
					}
					return (bolLparameter
											? this.deprecratedDoParseStringParameter(	Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_JUGGLER_DAY,
																														this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]),
																						objPpatternsParameter,
																						objPpatternsBufferedReader,
																						bolPnewLists,
																						bolPstyles,
																						bolPsiteswaps) : null);

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_FLASH:
				case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
					return (bolLparameter ? this.deprecatedDoParseByteParameter(Constants.bytS_BYTE_LOCAL_STROBE,
																				objPpatternsParameter,
																				objPpatternsBufferedReader,
																				bolPnewLists,
																				bolPstyles,
																				bolPsiteswaps) : null);

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
					return (bolLparameter ? this.deprecatedDoParseByteParameter(Constants.bytS_BYTE_LOCAL_DEFAULTS,
																				objPpatternsParameter,
																				objPpatternsBufferedReader,
																				bolPnewLists,
																				bolPstyles,
																				bolPsiteswaps) : null);

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_COLORS:
					return (bolLparameter ? this.deprecratedDoParseStringParameter(	Constants.bytS_STRING_LOCAL_COLORS,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_BALLS:
					return bolLparameter ? this.deprecatedDoParseByteParameter(	Constants.bytS_BYTE_LOCAL_BALLS_TRAIL,
																				objPpatternsParameter,
																				objPpatternsBufferedReader,
																				bolPnewLists,
																				bolPstyles,
																				bolPsiteswaps) : null;

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_SPEED:
					return bolLparameter ? this.deprecatedDoParseByteParameter(	Constants.bytS_BYTE_LOCAL_SPEED,
																				objPpatternsParameter,
																				objPpatternsBufferedReader,
																				bolPnewLists,
																				bolPstyles,
																				bolPsiteswaps) : null;

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
					return bolLparameter ? this.deprecatedDoParseByteParameter(	Constants.bytS_BYTE_LOCAL_FLUIDITY,
																				objPpatternsParameter,
																				objPpatternsBufferedReader,
																				bolPnewLists,
																				bolPstyles,
																				bolPsiteswaps) : null;

					// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_EDITION:
					if (bolPnewLists || bolPsiteswaps) {
						this.objGpatternsManager.bolGglobalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] = (bytLvalue > 0);
						this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytPcontrolType] = true;
					}

					break;

				// Byte parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
				case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
				case Constants.bytS_BOOLEAN_LOCAL_INFO:
					this.objGpatternsManager.bolGglobalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] = (bytLvalue > 0);
					this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytPcontrolType] = true;
					break;

				// Byte parameter value :
				default:
					if (bolLparameter) {
						this.objGpatternsManager.setBoolean(bytPcontrolType, bytLvalue > 0);
						this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytPcontrolType] = true;
					}
			}

			return null;
		}

		// Boolean parameter name with a float parameter value :
		if (objLparameterValueTypeObject instanceof Float) {
			switch (bytPcontrolType) {

				// Float parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_SPEED:
					return bolLparameter ? this.deprecatedDoParseByteParameter(	Constants.bytS_BYTE_LOCAL_SPEED,
																				objPpatternsParameter,
																				objPpatternsBufferedReader,
																				bolPnewLists,
																				bolPstyles,
																				bolPsiteswaps) : null;

					// Float parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_COLORS:
					return bolLparameter ? this.deprecratedDoParseStringParameter(	Constants.bytS_STRING_LOCAL_COLORS,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null;

					// Float parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
					if (bolLparameter) {
						final Color objLcolor = Tools.getPenColor(objPpatternsParameter.getString());

						if (objLcolor != null) {
							final byte bytLstringControlType =
																Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_JUGGLER_DAY,
																								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]);
							this.objGpatternsManager.setString(bytLstringControlType, objPpatternsParameter.getString());
							this.objGpatternsManager.bolGstringIsFileDefinedA[bytLstringControlType] = true;

							return null;
						}
					}

					// Float parameter value :
					//$FALL-THROUGH$
				default:
					if (bolLparameter) {
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());
					}

					return null;
			}
		}

		// Boolean parameter name with a boolean parameter value :
		if (objLparameterValueTypeObject instanceof Boolean) {
			final boolean bolLenabled = ((Boolean) objLparameterValueTypeObject).booleanValue();
			switch (bytPcontrolType) {

				// Boolean parameter value :
				case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
				case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
				case Constants.bytS_BOOLEAN_LOCAL_INFO:
					this.objGpatternsManager.bolGglobalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] = bolLenabled;
					this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytPcontrolType] = true;

					break;

				// Boolean parameter value :
				default:
					if (bolLparameter) {
						this.objGpatternsManager.setBoolean(bytPcontrolType, bolLenabled);
						this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytPcontrolType] = true;
					}
			}

			return null;
		}

		// Boolean parameter name with the 'user' value :
		if (objLparameterValueTypeObject instanceof Short) {
			if (bolLparameter) {
				switch (bytPcontrolType) {
					case Constants.bytS_BOOLEAN_LOCAL_MIRROR:
					case Constants.bytS_BOOLEAN_LOCAL_SITESWAP:
					case Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP:
					case Constants.bytS_BOOLEAN_LOCAL_STYLE:
					case Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE:
					case Constants.bytS_BOOLEAN_LOCAL_FX:
					case Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS:
					case Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS:
					case Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS:
					case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
					case Constants.bytS_BOOLEAN_LOCAL_SOUNDS:
					case Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS:
					case Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS:
					case Constants.bytS_BOOLEAN_LOCAL_METRONOME:
					case Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL:
					case Constants.bytS_BOOLEAN_LOCAL_LIGHT:
					case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
					case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
					case Constants.bytS_BOOLEAN_LOCAL_INFO:
					case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
					case Constants.bytS_BOOLEAN_LOCAL_BALLS:
					case Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS:
					case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
					case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
					case Constants.bytS_BOOLEAN_LOCAL_SPEED:
					case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
					case Constants.bytS_BOOLEAN_LOCAL_FLASH:
						this.objGpatternsManager.setBoolean(bytPcontrolType, Preferences.getLocalBooleanPreference(bytPcontrolType));
						break;

					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());
						return null;
				}

				byte bytLbyteControlType = Constants.bytS_UNCLASS_NO_VALUE;
				switch (bytPcontrolType) {
					case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
					case Constants.bytS_BOOLEAN_LOCAL_FLASH:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_STROBE;
						break;
					case Constants.bytS_BOOLEAN_LOCAL_BALLS:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_BALLS_TRAIL;
						break;
					case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
						bytLbyteControlType =
												this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]
																																								? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																								: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT;
						break;
					case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_FLUIDITY;
						break;
					case Constants.bytS_BOOLEAN_LOCAL_SPEED:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_SPEED;
						break;
					case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS;
						break;
					case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_DEFAULTS;
						break;
				}
				if (bytLbyteControlType != Constants.bytS_UNCLASS_NO_VALUE) {
					this.objGpatternsManager.setByte(bytLbyteControlType, null);
				}

				byte bytLstringControlType = Constants.bytS_UNCLASS_NO_VALUE;
				switch (bytPcontrolType) {
					case Constants.bytS_BOOLEAN_LOCAL_COLORS:
						bytLstringControlType = Constants.bytS_STRING_LOCAL_COLORS;
						break;
					case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
						bytLstringControlType =
												Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_JUGGLER_DAY,
																				this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]);
						break;
				}
				if (bytLstringControlType != Constants.bytS_UNCLASS_NO_VALUE) {
					this.objGpatternsManager.setString(bytLstringControlType, null);
				}
			}
			return null;
		}

		// Boolean parameter name with a global/local parameter value :
		if (objLparameterValueTypeObject instanceof Character) {
			final boolean bolLlocal = (((Character) objLparameterValueTypeObject).charValue() == 'L');
			byte bytLbyteControlType = Constants.bytS_UNCLASS_NO_VALUE;

			switch (bytPcontrolType) {
				case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
					bytLbyteControlType = Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS;

					break;

				case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
					bytLbyteControlType = Constants.bytS_BYTE_LOCAL_DEFAULTS;

					break;

				case Constants.bytS_BOOLEAN_LOCAL_BALLS:
					bytLbyteControlType = Constants.bytS_BYTE_LOCAL_BALLS_TRAIL;

					break;

				case Constants.bytS_BOOLEAN_LOCAL_SPEED:
					bytLbyteControlType = Constants.bytS_BYTE_LOCAL_SPEED;

					break;

				case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
					bytLbyteControlType = Constants.bytS_BYTE_LOCAL_FLUIDITY;

					break;

				case Constants.bytS_BOOLEAN_LOCAL_EDITION:
					if (bolPnewLists || (bolPsiteswaps && bolLlocal)) {
						this.objGpatternsManager.bolGbooleanIsLocalA[bytPcontrolType] = bolLlocal;
						this.objGpatternsManager.bolGbooleanIsLocallyDefinedA[bytPcontrolType] = true;
					}

					break;

				case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
				case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
				case Constants.bytS_BOOLEAN_LOCAL_INFO:
					this.objGpatternsManager.bolGbooleanIsLocalA[bytPcontrolType] = bolLlocal;
					this.objGpatternsManager.bolGbooleanIsLocallyDefinedA[bytPcontrolType] = true;
					break;
			}

			if (bolPnewLists || bolLlocal) {
				// if (bytPcontrolType == Constants.bytS_BOOLEAN_LOCAL_EDITION) {
				// Tools.verbose(
				// "bolPnewLists : ",
				// bolPnewLists,
				// " - \nbolLlocal : ",
				// bolLlocal,
				// " - \nbytPcontrolType : ",
				// bytPcontrolType,
				// " - \nbolGbooleanIsLocalA[bytPcontrolType] : ",
				// bolGbooleanIsLocalA[bytPcontrolType]);
				// }
				if (!bolPnewLists && !this.objGpatternsManager.bolGbooleanIsLocalA[bytPcontrolType]) {
					this.setAllBooleansToLocal(bytPcontrolType);
				}

				this.objGpatternsManager.bolGbooleanIsLocalA[bytPcontrolType] = bolLlocal;
				this.objGpatternsManager.bolGbooleanIsLocallyDefinedA[bytPcontrolType] = true;

				switch (bytPcontrolType) {
					case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
					case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
					case Constants.bytS_BOOLEAN_LOCAL_BALLS:
					case Constants.bytS_BOOLEAN_LOCAL_SPEED:
					case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
						if (!bolPnewLists && !this.objGpatternsManager.bolGbyteIsLocalA[bytLbyteControlType]) {
							this.setAllBytesToLocal(bytLbyteControlType);
						}

						this.objGpatternsManager.bolGbyteIsLocalA[bytLbyteControlType] = bolLlocal;
						this.objGpatternsManager.bolGbyteIsLocallyDefinedA[bytLbyteControlType] = true;
				}
			}

			return null;
		}

		// Boolean parameter name with a string parameter value :
		if (objLparameterValueTypeObject instanceof String) {
			if (bolLparameter) {
				switch (bytPcontrolType) {

					// String parameter value :
					case Constants.bytS_BOOLEAN_LOCAL_COLORS:

						return (bolLparameter ? this.deprecratedDoParseStringParameter(	Constants.bytS_STRING_LOCAL_COLORS,
																						objPpatternsParameter,
																						objPpatternsBufferedReader,
																						bolPnewLists,
																						bolPstyles,
																						bolPsiteswaps) : null);

						// String parameter value :
					case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:

						return (bolLparameter ? this.deprecatedDoParseByteParameter(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

						// String parameter value :
					case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:

						return (bolLparameter
												? this.deprecratedDoParseStringParameter(	Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_JUGGLER_DAY,
																															this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]),
																							objPpatternsParameter,
																							objPpatternsBufferedReader,
																							bolPnewLists,
																							bolPstyles,
																							bolPsiteswaps) : null);

						// String parameter value :
					case Constants.bytS_BOOLEAN_LOCAL_FLASH:
					case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
						return (bolLparameter ? this.deprecatedDoParseByteParameter(Constants.bytS_BYTE_LOCAL_STROBE,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

						// String parameter value :
					case Constants.bytS_BOOLEAN_LOCAL_BALLS:

						return (bolLparameter ? this.deprecatedDoParseByteParameter(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

						// String parameter value :
					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());

						return null;
				}
			}
		}

		return null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param objPpatternsParameter
	 * @param objPpatternsBufferedReader
	 * @param bolPnewLists
	 * @param bolPstyles
	 * @param bolPsiteswaps
	 * @return
	 */
	final private Object[] deprecatedDoParseByteParameter(	byte bytPcontrolType,
															FileParameter objPpatternsParameter,
															PatternsFile objPpatternsBufferedReader,
															boolean bolPnewLists,
															boolean bolPstyles,
															boolean bolPsiteswaps) {
		final Object objLparameterValueTypeObject = Tools.getParameterValueType(objPpatternsParameter.getString());
		final boolean bolLparameter = bolPsiteswaps && (bolPnewLists || this.objGpatternsManager.bolGbyteIsLocalA[bytPcontrolType]);

		// Byte parameter name with a byte parameter value :
		if (objLparameterValueTypeObject instanceof Byte) {
			if (bolLparameter) {
				byte bytLvalue = ((Byte) objLparameterValueTypeObject).byteValue();

				switch (bytPcontrolType) {

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_SPEED:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(bytLvalue * 10, Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE));

						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
						final byte bytLalternateColorsValue = FileParameterParser.getAlternateColorsValue(objPpatternsParameter.getString());

						if (bytLalternateColorsValue == Constants.bytS_UNCLASS_NO_VALUE) {
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValuesIndex());

							return null;
						}
						this.objGpatternsManager.setByte(bytPcontrolType, bytLalternateColorsValue);

						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_SKILL:
						bytLvalue = FileParameterParser.getSkillValue(objPpatternsParameter.getString());
						if (bytLvalue == Constants.bytS_UNCLASS_NO_VALUE) {
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValuesIndex());
							return null;
						}
						this.objGpatternsManager.setByte(bytPcontrolType, bytLvalue);
						break;

					case Constants.bytS_BYTE_LOCAL_MARK:
						bytLvalue =
									(byte) (Math.min(	Math.max(bytLvalue - 1, Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE),
														Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE));
						if (bytLvalue == Constants.bytS_UNCLASS_NO_VALUE) {
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValuesIndex());
							return null;
						}
						this.objGpatternsManager.setByte(bytPcontrolType, bytLvalue);
						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_FLUIDITY:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(bytLvalue, Constants.bytS_BYTE_LOCAL_FLUIDITY_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE));

						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
						return (bolLparameter
												? this.deprecratedDoParseStringParameter(	(bytPcontrolType == Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY)
																																							? Constants.bytS_STRING_LOCAL_JUGGLER_DAY
																																							: Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT,
																							objPpatternsParameter,
																							objPpatternsBufferedReader,
																							bolPnewLists,
																							bolPstyles,
																							bolPsiteswaps) : null);

						// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_HEIGHT:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(bytLvalue, Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE));
						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_STROBE:
						final byte bytLstrobeValue = FileParameterParser.getStrobeValue(objPpatternsParameter.getString());

						if (bytLstrobeValue == Constants.bytS_UNCLASS_NO_VALUE) {
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValuesIndex());

							return null;
						}
						this.objGpatternsManager.setByte(bytPcontrolType, bytLstrobeValue);

						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_DWELL:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(bytLvalue, Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE));

						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_DEFAULTS:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(bytLvalue, Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_DEFAULTS_MAXIMUM_VALUE));

						break;

					// Byte parameter value :
					case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
						this.objGpatternsManager.setByte(bytPcontrolType, FileParameterParser.getBallsValue(objPpatternsParameter.getString()));
				}

				if (bolPnewLists) {
					this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPcontrolType] = true;
				}
			}

			return null;
		}

		// Byte parameter name with a float parameter value :
		if (objLparameterValueTypeObject instanceof Float) {
			if (bolLparameter) {
				final byte bytLvalue = (byte) ((Float) objLparameterValueTypeObject).floatValue();

				switch (bytPcontrolType) {

					// Float parameter value :
					case Constants.bytS_BYTE_LOCAL_SPEED:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(	(byte) (((Float) objLparameterValueTypeObject).floatValue() / 10F),
																						Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE));

						break;

					// Float parameter value :
					case Constants.bytS_BYTE_LOCAL_HEIGHT:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(bytLvalue, Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE));

						break;

					// Float parameter value :
					case Constants.bytS_BYTE_LOCAL_DWELL:
						this.objGpatternsManager.setByte(	bytPcontrolType,
															(byte) Math.min(Math.max(bytLvalue, Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE),
																			Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE));

						break;

					// Float parameter value :
					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());

						return null;
				}

				if (bolPnewLists) {
					this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPcontrolType] = true;
				}
			}

			return null;
		}

		// Byte parameter name with a boolean parameter value :
		if (objLparameterValueTypeObject instanceof Boolean) {
			if (bolLparameter) {
				final boolean bolLvalue = ((Boolean) objLparameterValueTypeObject).booleanValue();

				switch (bytPcontrolType) {

					// Boolean parameter value :
					case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
					case Constants.bytS_BYTE_LOCAL_STROBE:
					case Constants.bytS_BYTE_LOCAL_DEFAULTS:
					case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
					case Constants.bytS_BYTE_LOCAL_SPEED:
					case Constants.bytS_BYTE_LOCAL_FLUIDITY:
						byte bytLbooleanControlType = Constants.bytS_UNCLASS_NO_VALUE;

						switch (bytPcontrolType) {
							case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
								bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS;

								break;

							case Constants.bytS_BYTE_LOCAL_STROBE:
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FLASH][Constants.bytS_UNCLASS_INITIAL] =
																																					false;
								bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_ROBOT;
								break;

							case Constants.bytS_BYTE_LOCAL_DEFAULTS:
								bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_DEFAULTS;

								break;

							case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
								bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_BALLS;

								break;

							case Constants.bytS_BYTE_LOCAL_SPEED:
								bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_SPEED;

								break;

							case Constants.bytS_BYTE_LOCAL_FLUIDITY:
								bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_FLUIDITY;

								break;
						}

						this.objGpatternsManager.setBoolean(bytLbooleanControlType, bolLvalue);
						this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytLbooleanControlType] = true;

						break;

					// Boolean parameter value :
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
						this.objGpatternsManager.setByte(bytPcontrolType, (bolLvalue ? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL
																					: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM));

						if (bolPnewLists) {
							this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPcontrolType] = true;
						}

						break;

					// Boolean parameter value :
					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());
				}
			}

			return null;
		}

		// Byte parameter name with the 'user' value :
		if (objLparameterValueTypeObject instanceof Short) {
			if (bolLparameter) {
				switch (bytPcontrolType) {
					case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
					case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
					case Constants.bytS_BYTE_LOCAL_DEFAULTS:
					case Constants.bytS_BYTE_LOCAL_STROBE:
					case Constants.bytS_BYTE_LOCAL_HEIGHT:
					case Constants.bytS_BYTE_LOCAL_SPEED:
					case Constants.bytS_BYTE_LOCAL_DWELL:
					case Constants.bytS_BYTE_LOCAL_FLUIDITY:
						this.objGpatternsManager.setByte(bytPcontrolType, null);
						break;
					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());
						return null;
				}

				byte bytLbooleanControlType = Constants.bytS_UNCLASS_NO_VALUE;
				switch (bytPcontrolType) {
					case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_BALLS;
						break;
					case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS;
						break;
					case Constants.bytS_BYTE_LOCAL_DEFAULTS:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_DEFAULTS;
						break;
					case Constants.bytS_BYTE_LOCAL_STROBE:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_ROBOT;
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_FLASH][Constants.bytS_UNCLASS_INITIAL] =
																																			Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_FLASH);
						break;
				}
				if (bytLbooleanControlType != Constants.bytS_UNCLASS_NO_VALUE) {
					this.objGpatternsManager.setBoolean(bytLbooleanControlType, Preferences.getLocalBooleanPreference(bytLbooleanControlType));
				}

				byte bytLstringControlType = Constants.bytS_UNCLASS_NO_VALUE;
				switch (bytPcontrolType) {
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
						bytLstringControlType = Constants.bytS_STRING_LOCAL_JUGGLER_DAY;
						break;
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
						bytLstringControlType = Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT;
						break;
				}
				if (bytLstringControlType != Constants.bytS_UNCLASS_NO_VALUE) {
					this.objGpatternsManager.setString(bytLstringControlType, null);
				}
			}
			return null;
		}

		// Byte parameter name with a global/local parameter value :
		if (objLparameterValueTypeObject instanceof Character) {
			final boolean bolLlocal = (((Character) objLparameterValueTypeObject).charValue() == 'L');

			if (bolPnewLists || bolLlocal) {
				if (!bolPnewLists && !this.objGpatternsManager.bolGbyteIsLocalA[bytPcontrolType]) {
					this.setAllBytesToLocal(bytPcontrolType);
				}

				this.objGpatternsManager.bolGbyteIsLocalA[bytPcontrolType] = bolLlocal;
				this.objGpatternsManager.bolGbyteIsLocallyDefinedA[bytPcontrolType] = true;

				byte bytLbooleanControlType = Constants.bytS_UNCLASS_NO_VALUE;

				switch (bytPcontrolType) {
					case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS;

						break;

					case Constants.bytS_BYTE_LOCAL_DEFAULTS:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_DEFAULTS;

						break;

					case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_BALLS;

						break;

					case Constants.bytS_BYTE_LOCAL_SPEED:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_SPEED;

						break;

					case Constants.bytS_BYTE_LOCAL_FLUIDITY:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_FLUIDITY;

						break;
				}

				if (bytLbooleanControlType != Constants.bytS_UNCLASS_NO_VALUE) {
					if (!bolPnewLists && !this.objGpatternsManager.bolGbooleanIsLocalA[bytLbooleanControlType]) {
						this.setAllBooleansToLocal(bytLbooleanControlType);
					}

					this.objGpatternsManager.bolGbooleanIsLocalA[bytLbooleanControlType] = bolLlocal;
					this.objGpatternsManager.bolGbooleanIsLocallyDefinedA[bytLbooleanControlType] = true;
				}
			}

			return null;
		}

		// Byte parameter name with a string parameter value :
		if (objLparameterValueTypeObject instanceof String) {
			if (bolLparameter) {
				final String strLvalue = (String) objLparameterValueTypeObject;
				byte bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;

				switch (bytPcontrolType) {

					// String parameter value :
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
						return bolLparameter
											? this.deprecratedDoParseStringParameter(	bytPcontrolType == Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																						? Constants.bytS_STRING_LOCAL_JUGGLER_DAY
																																						: Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT,
																						objPpatternsParameter,
																						objPpatternsBufferedReader,
																						bolPnewLists,
																						bolPstyles,
																						bolPsiteswaps) : null;

						// String parameter value :
					case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
						bytLvalue = FileParameterParser.getAlternateColorsValue(strLvalue);

						break;

					// String parameter value :
					case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
						bytLvalue = FileParameterParser.getBallsValue(strLvalue);

						break;

					// String parameter value :
					case Constants.bytS_BYTE_LOCAL_STROBE:
						bytLvalue = FileParameterParser.getStrobeValue(strLvalue);
						break;

					case Constants.bytS_BYTE_LOCAL_SKILL:
						bytLvalue = FileParameterParser.getSkillValue(strLvalue);
						break;
				}

				if (bytLvalue == Constants.bytS_UNCLASS_NO_VALUE) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValuesIndex());
				} else {
					this.objGpatternsManager.setByte(bytPcontrolType, bytLvalue);

					if (bolPnewLists) {
						this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPcontrolType] = true;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param objPpatternsParameter
	 * @param objPpatternsBufferedReader
	 * @param bolPnewLists
	 * @param bolPstyles
	 * @param bolPsiteswaps
	 * @return
	 */
	final private Object[] deprecatedDoParseOtherParameter(	byte bytPcontrolType,
															FileParameter objPpatternsParameter,
															PatternsFile objPpatternsBufferedReader,
															boolean bolPnewLists,
															boolean bolPstyles,
															boolean bolPsiteswaps) {
		final Object objLparameterValueTypeObject = Tools.getParameterValueType(objPpatternsParameter.getString());
		final boolean bolLparameter = bolPnewLists || (bolPsiteswaps && bolPstyles);

		// Other parameter name with the 'user' value :
		if (objLparameterValueTypeObject instanceof Short) {
			if (bolLparameter) {
				if (bytPcontrolType == Constants.bytS_OTHER_LOCAL_BACKGROUND) {
					this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_BACKGROUND_DAY, null);
					this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT, null);
				} else {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValuesIndex());
				}
			}
			return null;
		}

		// Gravity parameter :
		if (bytPcontrolType == Constants.bytS_OTHER_LOCAL_GRAVITY) {
			if (bolLparameter) {
				final boolean bolLvalue = ((objLparameterValueTypeObject instanceof Byte) || (objLparameterValueTypeObject instanceof Float));
				final int intLvalue =
										(int) ((objLparameterValueTypeObject instanceof Byte)
																								? ((Byte) objLparameterValueTypeObject).byteValue()
																								: (objLparameterValueTypeObject instanceof Float)
																																					? Float.valueOf(objPpatternsParameter.getString())
																																					: Constants.bytS_UNCLASS_NO_VALUE);

				if (Math.abs(10 - intLvalue) > 1) {
					this.doAddToConsole(bolLvalue ? Language.intS_CONSOLE_PARAMETER_DEPRECATION : Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										bolLvalue ? ConsoleObject.bytS_WARNING_LEVEL : ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										bolLvalue ? objPpatternsParameter.getNameIndex() : objPpatternsParameter.getValuesIndex());
				}
			}

			return null;
		}

		// Other parameter name with a global/local parameter value :
		if (objLparameterValueTypeObject instanceof Character) {
			final boolean bolLlocal = ((Character) objLparameterValueTypeObject).charValue() == 'L';

			if (bytPcontrolType == Constants.bytS_OTHER_LOCAL_BACKGROUND) {
				if (bolPnewLists || (bolLlocal && bolPsiteswaps && bolPstyles)) {
					if (!bolPnewLists) {
						if (!this.objGpatternsManager.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY]) {
							this.setAllStringsToLocal(Constants.bytS_STRING_LOCAL_BACKGROUND_DAY);
						}

						if (!this.objGpatternsManager.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT]) {
							this.setAllStringsToLocal(Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT);
						}
					}

					this.objGpatternsManager.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] =
																												this.objGpatternsManager.bolGstringIsLocalA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT] =
																																																			bolLlocal;
					this.objGpatternsManager.bolGstringIsLocallyDefinedA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] =
																														this.objGpatternsManager.bolGstringIsLocallyDefinedA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT] =
																																																								true;
				}
			} else if ((bytPcontrolType == Constants.bytS_OTHER_LOCAL_START_PATTERN) && bolLparameter) {
				this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
									ConsoleObject.bytS_ERROR_LEVEL,
									objPpatternsBufferedReader,
									objPpatternsParameter.getValuesIndex());
			}

			return null;
		}

		if (bytPcontrolType == Constants.bytS_OTHER_LOCAL_BACKGROUND) {
			if (bolLparameter) {
				final Color objLbackgroundColor = Tools.getPenColor(objPpatternsParameter.getString());

				if ((objLparameterValueTypeObject instanceof Boolean) || (objLbackgroundColor == null)) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValuesIndex());
				} else {
					final byte bytLbyteControlType =
														Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_BACKGROUND_DAY,
																						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]);
					this.objGpatternsManager.setString(bytLbyteControlType, objPpatternsParameter.getString().toUpperCase());
					this.objGpatternsManager.bolGstringIsFileDefinedA[bytLbyteControlType] = true;
				}
			}

			return null;
		}

		if ((bytPcontrolType == Constants.bytS_OTHER_LOCAL_START_PATTERN) && bolLparameter) {

			// Other parameter name with a boolean parameter value :
			if ((objLparameterValueTypeObject instanceof Float) || (objLparameterValueTypeObject instanceof String)) {
				this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
									ConsoleObject.bytS_ERROR_LEVEL,
									objPpatternsBufferedReader,
									objPpatternsParameter.getValuesIndex());
			} else {
				this.objGpatternsManager.getPatternsFileManager().bolGstartObject =
																					((objLparameterValueTypeObject instanceof Boolean)
																																		? ((Boolean) objLparameterValueTypeObject).booleanValue()
																																		: ((Byte) objLparameterValueTypeObject).byteValue() > 0);
			}
		}

		return null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param objPpatternsParameter
	 * @param objPpatternsBufferedReader
	 * @param bolPnewLists
	 * @param bolPstyles
	 * @param bolPsiteswaps
	 * @return
	 */
	final private Object[] deprecratedDoParseStringParameter(	byte bytPcontrolType,
																FileParameter objPpatternsParameter,
																PatternsFile objPpatternsBufferedReader,
																boolean bolPnewLists,
																boolean bolPstyles,
																boolean bolPsiteswaps) {
		final Object objLparameterValueTypeObject = Tools.getParameterValueType(objPpatternsParameter.getString());
		final boolean bolLparameter = bolPsiteswaps && (bolPnewLists || this.objGpatternsManager.bolGstringIsLocalA[bytPcontrolType]);

		// Reverse color case :
		if (bytPcontrolType == Constants.bytS_STRING_LOCAL_REVERSE_COLORS) {
			this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_UNKNOWN_NAME,
								ConsoleObject.bytS_ERROR_LEVEL,
								objPpatternsBufferedReader,
								objPpatternsParameter.getNameIndex());
			return null;
		}

		// String parameter name with a byte, float or string parameter value :
		if ((objLparameterValueTypeObject instanceof Byte) || (objLparameterValueTypeObject instanceof Float)
			|| (objLparameterValueTypeObject instanceof String)) {
			switch (bytPcontrolType) {

				// Float or string parameter value :
				case Constants.bytS_STRING_LOCAL_STYLE:
					return (bolPstyles ? new Object[] {
						Strings.uncomment(objPpatternsBufferedReader.getLineString()).substring(objPpatternsParameter.getValuesIndex()),
						objPpatternsParameter.getValuesIndex() } : null);

					// Float or string parameter value :
				case Constants.bytS_STRING_LOCAL_SITESWAP:
					return (bolLparameter ? this.deprecatedDoParseBooleanParameter(	Constants.bytS_BOOLEAN_LOCAL_SITESWAP,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

					// Float or string parameter value :
				case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
					return (bolLparameter ? this.deprecatedDoParseBooleanParameter(	Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

					// Float or string parameter value :
				case Constants.bytS_STRING_LOCAL_PATTERN:
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_UNKNOWN_NAME,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getNameIndex());
					return null;

					// Float or string parameter value :
				case Constants.bytS_STRING_LOCAL_COLORS:
					if (bolLparameter) {
						final BallsColors objLballColors = new BallsColors(objPpatternsParameter.getString());

						if (objLballColors.getState() != Boolean.FALSE) {
							this.objGpatternsManager.setString(bytPcontrolType, objLballColors.getBallsColorsString());

							if (bolPnewLists) {
								this.objGpatternsManager.bolGstringIsFileDefinedA[bytPcontrolType] = true;
								this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_REVERSE_COLORS] = true;
							}
						}
						if (objLballColors.getState() == null) {
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_COLORS_MALFORMED_VALUE,
												ConsoleObject.bytS_WARNING_LEVEL,
												objPpatternsBufferedReader,
												objLballColors.getErrorIndex() + 1);
							// Tools.verbose("Couleurs en warning : ", objLballColors);
						} else if (objLballColors.getState() == Boolean.FALSE) {
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_COLORS_UNKNOWN_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objLballColors.getErrorIndex() + 1);
							// Tools.verbose("Couleurs erronées : ", objLballColors);
						}
					}

					return null;

					// Float or string parameter value :
				case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
				case Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT:
				case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
				case Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT:
					if (bolLparameter) {
						final Color objLbackgroundColor = Tools.getPenColor(objPpatternsParameter.getString());

						if (objLbackgroundColor == null) {
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValuesIndex());
						} else {
							this.objGpatternsManager.strGglobalAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] =
																														objPpatternsParameter	.getString()
																																				.toUpperCase();

							if (bolPnewLists) {
								this.objGpatternsManager.bolGstringIsFileDefinedA[bytPcontrolType] = true;
							}
						}
					}

					return null;

					// Float or string parameter value :
				case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
				case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
					if (bolLparameter) {
						final byte bytLjugglerValue = FileParameterParser.getJugglerValue(objPpatternsParameter.getString());
						final byte bytLbyteControlType =
															((bytPcontrolType == Constants.bytS_STRING_LOCAL_JUGGLER_DAY)
																															? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																															: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT);

						if (bytLjugglerValue != Constants.bytS_UNCLASS_NO_VALUE) {
							this.objGpatternsManager.setByte(bytLbyteControlType, bytLjugglerValue);
							this.objGpatternsManager.bolGbyteIsFileDefinedA[bytLbyteControlType] = true;
						} else {
							final Color objLjugglerColor = Tools.getPenColor(objPpatternsParameter.getString());

							if (objLjugglerColor != null) {
								this.objGpatternsManager.strGglobalAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] =
																															objPpatternsParameter	.getString()
																																					.toUpperCase();

								if (bolPnewLists) {
									this.objGpatternsManager.bolGstringIsFileDefinedA[bytPcontrolType] = true;
								}
							} else {
								this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
													ConsoleObject.bytS_ERROR_LEVEL,
													objPpatternsBufferedReader,
													objPpatternsParameter.getValuesIndex());
							}
						}
					}

					return null;
				case Constants.bytS_STRING_LOCAL_TOOLTIP:
					final String strLvalueA[] = objPpatternsParameter.getValues();
					String strLtooltip = Strings.strS_EMPTY;
					for (final String strLvalue : strLvalueA) {
						strLtooltip = Strings.doConcat(strLtooltip, strLvalue, Strings.strS_SPACE);
					}
					this.objGpatternsManager.strGglobalAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] = strLtooltip;

					if (bolPnewLists) {
						this.objGpatternsManager.bolGstringIsFileDefinedA[bytPcontrolType] = true;
					}
					break;
				default:
					break;
			}
		}

		// String parameter name with a boolean parameter value :
		if (objLparameterValueTypeObject instanceof Boolean) {
			if (bolLparameter) {
				byte bytLbooleanControlType = Constants.bytS_UNCLASS_NO_VALUE;

				switch (bytPcontrolType) {

					// Boolean parameter value :
					case Constants.bytS_STRING_LOCAL_STYLE:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_STYLE;

						break;

					// Boolean parameter value :
					case Constants.bytS_STRING_LOCAL_SITESWAP:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_SITESWAP;
						break;

					// Boolean parameter value :
					case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP;
						break;

					// Boolean parameter value :
					case Constants.bytS_STRING_LOCAL_COLORS:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_COLORS;

						break;

					// Boolean parameter value :
					case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
					case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
						return (bolLparameter
												? this.deprecatedDoParseByteParameter(	(bytPcontrolType == Constants.bytS_STRING_LOCAL_JUGGLER_DAY)
																																					? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																					: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT,
																						objPpatternsParameter,
																						objPpatternsBufferedReader,
																						bolPnewLists,
																						bolPstyles,
																						bolPsiteswaps) : null);

						// Boolean parameter value :
					case Constants.bytS_STRING_LOCAL_PATTERN:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_UNKNOWN_NAME,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getNameIndex());
						return null;
					case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
					case Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT:
					case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
					case Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());

						return null;
				}

				this.objGpatternsManager.setBoolean(bytLbooleanControlType, ((Boolean) objLparameterValueTypeObject).booleanValue());
				this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytLbooleanControlType] = true;
			}

			return null;
		}

		// String parameter name with the 'user' value :
		if (objLparameterValueTypeObject instanceof Short) {
			if (bolLparameter) {
				switch (bytPcontrolType) {
					case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
					case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
					case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
					case Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT:
					case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
					case Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT:
					case Constants.bytS_STRING_LOCAL_COLORS:
						this.objGpatternsManager.strGglobalAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] =
																													Preferences.getLocalStringPreference(bytPcontrolType);
						break;
					case Constants.bytS_STRING_LOCAL_STYLE:
					case Constants.bytS_STRING_LOCAL_SITESWAP:
					case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
						break;
					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValuesIndex());

						return null;
				}

				byte bytLbooleanControlType = Constants.bytS_UNCLASS_INITIAL;
				switch (bytPcontrolType) {
					case Constants.bytS_STRING_LOCAL_COLORS:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_COLORS;
						break;
					case Constants.bytS_STRING_LOCAL_STYLE:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_STYLE;
						break;
					case Constants.bytS_STRING_LOCAL_SITESWAP:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_SITESWAP;
						break;
					case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
						bytLbooleanControlType = Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP;
						break;
				}
				if (bytLbooleanControlType != Constants.bytS_UNCLASS_INITIAL) {
					this.objGpatternsManager.setBoolean(bytLbooleanControlType, Preferences.getLocalBooleanPreference(bytLbooleanControlType));
				}

				byte bytLbyteControlType = Constants.bytS_UNCLASS_INITIAL;
				switch (bytPcontrolType) {
					case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY;
						break;
					case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
						bytLbyteControlType = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT;
						break;
				}
				if (bytLbyteControlType != Constants.bytS_UNCLASS_INITIAL) {
					this.objGpatternsManager.setByte(bytLbyteControlType, null);
				}
			}
			return null;
		}

		// String parameter name with a global/local parameter value :
		if (objLparameterValueTypeObject instanceof Character) {
			switch (bytPcontrolType) {
				case Constants.bytS_STRING_LOCAL_SITESWAP:
					return (bolPsiteswaps ? this.deprecatedDoParseBooleanParameter(	Constants.bytS_BOOLEAN_LOCAL_SITESWAP,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

				case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
					return (bolPsiteswaps ? this.deprecatedDoParseBooleanParameter(	Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

				case Constants.bytS_STRING_LOCAL_STYLE:
					return (bolLparameter ? this.deprecatedDoParseBooleanParameter(	Constants.bytS_BOOLEAN_LOCAL_STYLE,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

				case Constants.bytS_STRING_LOCAL_PATTERN:
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_UNKNOWN_NAME,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getNameIndex());
					return null;

				case Constants.bytS_STRING_LOCAL_COLORS:
					return (bolLparameter ? this.deprecatedDoParseBooleanParameter(	Constants.bytS_BOOLEAN_LOCAL_COLORS,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

				default:
					final boolean bolLlocal = (((Character) objLparameterValueTypeObject).charValue() == 'L');

					if (bolPnewLists || bolLlocal) {
						if (!bolPnewLists && !this.objGpatternsManager.bolGstringIsLocalA[bytPcontrolType]) {
							this.setAllStringsToLocal(bytPcontrolType);
						}

						this.objGpatternsManager.bolGstringIsLocalA[bytPcontrolType] = bolLlocal;
						this.objGpatternsManager.bolGstringIsLocallyDefinedA[bytPcontrolType] = true;
					}
			}
			switch (bytPcontrolType) {
				case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
				case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
					return (bolLparameter
											? this.deprecatedDoParseByteParameter(	(bytPcontrolType == Constants.bytS_STRING_LOCAL_JUGGLER_DAY)
																																				? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																				: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT,
																					objPpatternsParameter,
																					objPpatternsBufferedReader,
																					bolPnewLists,
																					bolPstyles,
																					bolPsiteswaps) : null);

			}
		}
		return null;
	}

	final private void doAddJugglerValue(byte bytPjugglerBodyValue, boolean bolPday, boolean bolPnight, boolean bolPreset) {

		final boolean[] bolLarmsA = { false, false };
		final boolean[] bolLheadA = { false, false };
		final boolean[] bolLhandsA = { false, false };

		for (byte bytLnightAndDay = Constants.bytS_ENGINE_NIGHT; bytLnightAndDay < Constants.bytS_ENGINE_DAY; ++bytLnightAndDay) {
			if (bytLnightAndDay == Constants.bytS_ENGINE_NIGHT && !bolPnight || bytLnightAndDay == Constants.bytS_ENGINE_DAY && !bolPday) {
				continue;
			}

			final byte bytLjugglerBodyControl =
												bytLnightAndDay == Constants.bytS_ENGINE_NIGHT ? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT
																								: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY;
			if (bolPreset) {
				this.objGpatternsManager.setByte(bytLjugglerBodyControl, Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM);
				bolLarmsA[bytLnightAndDay] = false;
				bolLhandsA[bytLnightAndDay] = false;
				bolLheadA[bytLnightAndDay] = false;
			} else {
				switch (this.objGpatternsManager.bytGglobalValueAA[bytLjugglerBodyControl][Constants.bytS_UNCLASS_INITIAL]) {
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS:
						bolLarmsA[bytLnightAndDay] = true;
						//$FALL-THROUGH$
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS:
						bolLhandsA[bytLnightAndDay] = true;
						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL:
						bolLarmsA[bytLnightAndDay] = true;
						//$FALL-THROUGH$
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS:
						bolLhandsA[bytLnightAndDay] = true;
						//$FALL-THROUGH$
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD:
						bolLheadA[bytLnightAndDay] = true;
						break;

					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS:
						bolLheadA[bytLnightAndDay] = true;
						//$FALL-THROUGH$
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS:
						bolLarmsA[bytLnightAndDay] = true;
						break;
					case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM:
					default:
						break;
				}
			}

			switch (bytPjugglerBodyValue) {
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL:
					bolLarmsA[bytLnightAndDay] = true;
					//$FALL-THROUGH$
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS:
					bolLhandsA[bytLnightAndDay] = true;
					//$FALL-THROUGH$
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD:
					bolLheadA[bytLnightAndDay] = true;
					break;

				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS:
					bolLarmsA[bytLnightAndDay] = true;
					//$FALL-THROUGH$
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS:
					bolLhandsA[bytLnightAndDay] = true;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS:
					bolLheadA[bytLnightAndDay] = true;
					//$FALL-THROUGH$
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS:
					bolLarmsA[bytLnightAndDay] = true;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM:
					bolLheadA[bytLnightAndDay] = false;
					bolLarmsA[bytLnightAndDay] = false;
					bolLhandsA[bytLnightAndDay] = false;
					break;
				default:
					break;
			}

			byte bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
			if (bolLheadA[bytLnightAndDay]) {
				if (bolLarmsA[bytLnightAndDay]) {
					if (bolLhandsA[bytLnightAndDay]) {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
					} else {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS;
					}
				} else {
					if (bolLhandsA[bytLnightAndDay]) {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS;
					} else {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD;
					}
				}
			} else {
				if (bolLarmsA[bytLnightAndDay]) {
					if (bolLhandsA[bytLnightAndDay]) {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS;
					} else {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS;
					}
				} else {
					if (bolLhandsA[bytLnightAndDay]) {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS;
					} else {
						bytLvalue = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
					}
				}
			}
			this.objGpatternsManager.setByte(bytLjugglerBodyControl, bytLvalue);
		}
	}

	final private void doAddToConsole(int intPlanguageIndex, byte bytPtype, PatternsFile objPpatternsBufferedReader, int intPfileLineCursorIndex) {
	// TODO : doAddToConsole
	// this.doAddToConsole(intPlanguageIndex, bytPtype, objPpatternsBufferedReader, intPfileLineCursorIndex);
	}

	final public void doParseBackgroundParameter(	FileParameter objPpatternsParameter,
													PatternsFile objPpatternsBufferedReader,
													boolean bolPsiteswaps,
													boolean bolPnewLists) {
		if (bolPsiteswaps) {
			final boolean bolLjMparameter =
											objPpatternsParameter	.getName()
																	.equalsIgnoreCase(Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY][1])
												|| objPpatternsParameter.getName()
																		.equalsIgnoreCase(Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT][1]);
			// BC parameter :
			if (bolLjMparameter) {
				if (objPpatternsParameter.getValuesNumber() > 1) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_MULTIPLE_VALUES,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(1));
					return;
				}

				final Color objLbackgroundColor = Tools.getPenColor(objPpatternsParameter.getString(0));
				if (objLbackgroundColor == null) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(0));
					return;
				}
				final byte bytLcontrolType =
												Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_BACKGROUND_DAY,
																				this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]);
				this.objGpatternsManager.strGglobalAA[bytLcontrolType][Constants.bytS_UNCLASS_INITIAL] =
																											objPpatternsParameter	.getString()
																																	.toUpperCase();
				if (bolPnewLists) {
					this.objGpatternsManager.bolGstringIsFileDefinedA[bytLcontrolType] = true;
				}
				return;
			}

			boolean bolLdayValue = false;
			boolean bolLnightValue = false;

			// For each parameter value :
			for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {
				final byte bytLvalueType = objPpatternsParameter.getValueType(intLparameterValueIndex);
				final boolean bolLday =
										bolLdayValue
											|| !bolLnightValue
											&& this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL];
				final boolean bolLnight =
											bolLnightValue
												|| !bolLdayValue
												&& !this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL];
				switch (bytLvalueType) {
					case FileParameter.bytS_PARAMETER_TYPE_BYTE:
					case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
					case FileParameter.bytS_PARAMETER_TYPE_STRING:

						bolLdayValue = false;
						bolLnightValue = false;

						final String strLvalue = objPpatternsParameter.getString(intLparameterValueIndex);
						if (bytLvalueType == FileParameter.bytS_PARAMETER_TYPE_STRING) {
							// "day" or "night" :
							if (strLvalue.equalsIgnoreCase("day")) { // TODO : variabiliser & isFileDefined ?
								bolLdayValue = true;
								break;
							}
							if (strLvalue.equalsIgnoreCase("night")) { // TODO : variabiliser & isFileDefined ?
								bolLnightValue = true;
								break;
							}
						}

						// Color :
						final Color objLbackgroundColor = Tools.getPenColor(strLvalue);
						if (objLbackgroundColor != null) {
							if (bolLday) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY][Constants.bytS_UNCLASS_INITIAL] =
																																					new String(strLvalue).toUpperCase();
								if (bolPnewLists) {
									this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] = true;
								}
							}
							if (bolLnight) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																																						new String(strLvalue).toUpperCase();
								if (bolPnewLists) {
									this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT] = true;
								}
							}
							break;
						}

						// Error :
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;

					case FileParameter.bytS_PARAMETER_TYPE_USER:
						if (bolLday) {
							this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY][Constants.bytS_UNCLASS_INITIAL] =
																																				Preferences.getLocalStringPreference(Constants.bytS_STRING_LOCAL_BACKGROUND_DAY);
						}
						if (bolLnight) {
							this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																																					Preferences.getLocalStringPreference(Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT);
						}
						break;

					case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
						final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);
						if (bolLday) {
							this.setStringLocalness(Constants.bytS_STRING_LOCAL_BACKGROUND_DAY, bolLlocal, bolPnewLists);
						}

						if (bolLnight) {
							this.setStringLocalness(Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT, bolLlocal, bolPnewLists);
						}
						break;

					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;
				}
			}
		}
	}

	final public void doParseBallsParameter(FileParameter objPpatternsParameter,
											PatternsFile objPpatternsBufferedReader,
											boolean bolPsiteswaps,
											boolean bolPnewLists) {
	// TODO : doParseBallsParameter
	}

	final public void doParseDwellParameter(FileParameter objPpatternsParameter, PatternsFile objPpatternsBufferedReader, boolean bolPsiteswaps) {
		this.doParseHeightOrDwellParameter(objPpatternsParameter, objPpatternsBufferedReader, bolPsiteswaps, Constants.bytS_BYTE_LOCAL_DWELL);
	}

	final public void doParseFluidityParameter(FileParameter objPpatternsParameter, PatternsFile objPpatternsBufferedReader, boolean bolPnewLists) {
		this.doParseSpeedOrFluidityParameter(objPpatternsParameter, objPpatternsBufferedReader, Constants.bytS_BYTE_LOCAL_FLUIDITY, bolPnewLists);
	}

	final public void doParseGravityParameter(FileParameter objPpatternsParameter, PatternsFile objPpatternsBufferedReader) {

		if (objPpatternsParameter.getValuesNumber() > 1) {
			this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_MULTIPLE_VALUES,
								ConsoleObject.bytS_ERROR_LEVEL,
								objPpatternsBufferedReader,
								objPpatternsParameter.getValueIndex(1));
			return;
		}

		switch (objPpatternsParameter.getValueType(0)) {
			case FileParameter.bytS_PARAMETER_TYPE_BYTE:
			case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
				this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_DEPRECATION,
									ConsoleObject.bytS_WARNING_LEVEL,
									objPpatternsBufferedReader,
									objPpatternsParameter.getValueIndex(0));
				break;
			default:
				this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
									ConsoleObject.bytS_ERROR_LEVEL,
									objPpatternsBufferedReader,
									objPpatternsParameter.getValueIndex(0));
		}
	}

	final private void doParseHeightOrDwellParameter(	FileParameter objPpatternsParameter,
														PatternsFile objPpatternsBufferedReader,
														boolean bolPsiteswaps,
														byte bytPbyteLocalControl) {

		if (bolPsiteswaps) {

			final char chrLsingleChar = bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_HEIGHT ? '^' : '@';
			final byte bytLbyteLocalMinimumValue =
													bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_HEIGHT
																											? Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE
																											: Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE;
			final byte bytLbyteLocalMaximumValue =
													bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_HEIGHT
																											? Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE
																											: Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE;

			if (!this.hasParsedShortcutOrJMParameter(	objPpatternsParameter,
														objPpatternsBufferedReader,
														bytPbyteLocalControl,
														false,
														chrLsingleChar,
														bytLbyteLocalMinimumValue,
														bytLbyteLocalMaximumValue,
														true)) {

				// For each 'height'/'dwell' parameter value :
				for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {

					switch (objPpatternsParameter.getValueType(intLparameterValueIndex)) {
						case FileParameter.bytS_PARAMETER_TYPE_BYTE:
						case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
							this.objGpatternsManager.setByte(	bytPbyteLocalControl,
																objPpatternsParameter.getByteValue(	intLparameterValueIndex,
																									bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_HEIGHT
																																							? Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE
																																							: Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE,
																									bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_HEIGHT
																																							? Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE
																																							: Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE));
							this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPbyteLocalControl] = true;
							break;
						case FileParameter.bytS_PARAMETER_TYPE_USER:
							this.objGpatternsManager.setByte(bytPbyteLocalControl, null);
							break;
						case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
							final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);
							this.doAddToConsole(bolLlocal ? Language.intS_CONSOLE_PARAMETER_SINGLE_VALUE
															: Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												bolLlocal ? ConsoleObject.bytS_WARNING_LEVEL : ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValueIndex(intLparameterValueIndex));
							break;
						case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
						case FileParameter.bytS_PARAMETER_TYPE_STRING:
						default:
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValueIndex(intLparameterValueIndex));
							break;
					}
				}
			}
		}
	}

	final public void doParseHeightParameter(FileParameter objPpatternsParameter, PatternsFile objPpatternsBufferedReader, boolean bolPsiteswaps) {
		this.doParseHeightOrDwellParameter(objPpatternsParameter, objPpatternsBufferedReader, bolPsiteswaps, Constants.bytS_BYTE_LOCAL_HEIGHT);
	}

	final public void doParseJugglerParameter(	FileParameter objPpatternsParameter,
												PatternsFile objPpatternsBufferedReader,
												boolean bolPsiteswaps,
												boolean bolPstyles,
												boolean bolPnewLists) {
		if (bolPsiteswaps && bolPstyles) {
			final boolean bolLjMparameter =
											objPpatternsParameter	.getName()
																	.equalsIgnoreCase(Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY][1])
												|| objPpatternsParameter.getName()
																		.equalsIgnoreCase(Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT][1]);
			// HD parameter :
			if (bolLjMparameter) {
				if (objPpatternsParameter.getValuesNumber() > 1) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_MULTIPLE_VALUES,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(1));
					return;
				}

				final byte bytLvalue = objPpatternsParameter.getByteValue(0, (byte) 0, (byte) 1);
				if (bytLvalue == Constants.bytS_UNCLASS_NO_VALUE) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(0));
					return;
				}
				final byte bytLcontrolType =
												this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL]
																																								? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																								: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT;
				if (bytLvalue > 0) {
					this.objGpatternsManager.setByte(bytLcontrolType, Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL);
				}
				this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][Constants.bytS_UNCLASS_INITIAL] = bytLvalue > 0;
				if (bolPnewLists) {
					this.objGpatternsManager.bolGbyteIsFileDefinedA[bytLcontrolType] = true;
				}
				return;
			}

			boolean bolLdayValue = false;
			boolean bolLnightValue = false;
			boolean bolLjuggler = false;
			boolean bolLtrail = false;
			boolean bolLcolor = false;
			byte bytLvisibility = Constants.bytS_UNCLASS_NO_VALUE;
			boolean bolLresetValues = false;

			// For each parameter value :
			for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {
				final byte bytLvalueType = objPpatternsParameter.getValueType(intLparameterValueIndex);
				final boolean bolLday =
										bolLdayValue
											|| !bolLnightValue
											&& this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL];
				final boolean bolLnight =
											bolLnightValue
												|| !bolLdayValue
												&& !this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL];

				switch (bytLvalueType) {
					case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][Constants.bytS_UNCLASS_INITIAL] =
																																			objPpatternsParameter.getBooleanValue(intLparameterValueIndex);
						if (bolPnewLists) {
							this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER] = true;
						}
						break;

					case FileParameter.bytS_PARAMETER_TYPE_BYTE:
					case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
					case FileParameter.bytS_PARAMETER_TYPE_STRING:
						if (bolLresetValues) {
							bolLdayValue = false;
							bolLnightValue = false;
							bolLjuggler = false;
							bolLtrail = false;
							bytLvisibility = Constants.bytS_UNCLASS_NO_VALUE;
							bolLcolor = false;
							bolLresetValues = false;
						}

						final String strLvalue = objPpatternsParameter.getString(intLparameterValueIndex).toUpperCase();
						if (bytLvalueType == FileParameter.bytS_PARAMETER_TYPE_STRING) {
							// "day" or "night" :
							if (strLvalue.equalsIgnoreCase("day")) { // TODO : variabiliser & isFileDefined ?
								bolLdayValue = true;
								break;
							}
							if (strLvalue.equalsIgnoreCase("night")) { // TODO : variabiliser & isFileDefined ?
								bolLnightValue = true;
								break;
							}

							// "trail" :
							if (strLvalue.equals("trail")) {
								bolLtrail = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL][Constants.bytS_UNCLASS_INITIAL] =
																																							true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][Constants.bytS_UNCLASS_INITIAL] =
																																					true;
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL] = true;
								}
								break;
							}

							// Visibility :
							final byte bytLjuggler = FileParameterParser.getJugglerValue(strLvalue);
							if (bytLjuggler != Constants.bytS_UNCLASS_NO_VALUE) {
								this.doAddJugglerValue(bytLjuggler, bolLday, bolLnight, bytLvisibility == Constants.bytS_UNCLASS_NO_VALUE);
								bytLvisibility = bytLjuggler;
								if (bolPnewLists) {
									if (bolLday) {
										this.objGpatternsManager.bolGbyteIsFileDefinedA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY] = true;
									}
									if (bolLnight) {
										this.objGpatternsManager.bolGbyteIsFileDefinedA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT] = true;
									}
								}
								break;
							}
						}

						// Color value :
						final Color objLjugglerColor = Tools.getPenColor(strLvalue);
						if (objLjugglerColor != null) {
							bolLcolor = true;
							if (bolLday) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_DAY][Constants.bytS_UNCLASS_INITIAL] =
																																					new String(strLvalue);
								if (bolPnewLists) {
									this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_JUGGLER_DAY] = true;
								}
							}
							if (bolLnight) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																																					new String(strLvalue);
								if (bolPnewLists) {
									this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT] = true;
								}
							}
							break;
						}

						// Error :
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;

					case FileParameter.bytS_PARAMETER_TYPE_USER:
						if (!bolLjuggler && bytLvisibility == Constants.bytS_UNCLASS_NO_VALUE && !bolLcolor) {
							bolLjuggler = true;
							bolLtrail = true;
							bytLvisibility = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
							bolLcolor = true;
						}
						if (bolLjuggler) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][Constants.bytS_UNCLASS_INITIAL] =
																																				Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_JUGGLER);
						}
						if (bolLtrail) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL][Constants.bytS_UNCLASS_INITIAL] =
																																						Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL);
						}
						if (bytLvisibility != Constants.bytS_UNCLASS_NO_VALUE) {
							if (bolLday) {
								this.objGpatternsManager.setByte(Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY, null);
							}
							if (bolLnight) {
								this.objGpatternsManager.setByte(Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT, null);
							}
						}
						if (bolLcolor) {
							if (bolLday) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_DAY][Constants.bytS_UNCLASS_INITIAL] =
																																					Preferences.getLocalStringPreference(Constants.bytS_STRING_LOCAL_BACKGROUND_DAY);
							}
							if (bolLnight) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																																					Preferences.getLocalStringPreference(Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT);
							}
						}
						bolLresetValues = true;
						break;

					case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
						final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);

						if (!bolLjuggler && bytLvisibility == Constants.bytS_UNCLASS_NO_VALUE && !bolLcolor) {
							bolLjuggler = true;
							bolLtrail = true;
							bytLvisibility = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
							bolLcolor = true;
						}
						if (bolLjuggler) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_JUGGLER, bolLlocal, bolPnewLists);
						}
						if (bolLtrail) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL, bolLlocal, bolPnewLists);
						}
						if (bytLvisibility != Constants.bytS_UNCLASS_NO_VALUE) {
							if (bolLday) {
								this.setByteLocalness(Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY, bolLlocal, bolPnewLists);
								Preferences.getLocalBytePreference(Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY);
							}
							if (bolLnight) {
								this.setByteLocalness(Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT, bolLlocal, bolPnewLists);
							}
						}
						if (bolLcolor) {
							if (bolLday) {
								this.setStringLocalness(Constants.bytS_STRING_LOCAL_JUGGLER_DAY, bolLlocal, bolPnewLists);
							}
							if (bolLnight) {
								this.setStringLocalness(Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT, bolLlocal, bolPnewLists);
							}
						}
						bolLresetValues = true;
						break;

					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;
				}
			}
		}
	}

	final public void doParseLogParameter(FileParameter objPpatternsParameter, PatternsFile objPpatternsBufferedReader, boolean bolPnewLists) {

		boolean bolLnotices = false;
		boolean bolLwarnings = false;
		boolean bolLerrors = false;
		boolean bolLresetValues = false;

		// For each parameter value :
		for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {

			final byte bytLvalueType = objPpatternsParameter.getValueType(intLparameterValueIndex);
			switch (bytLvalueType) {
				case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
				case FileParameter.bytS_PARAMETER_TYPE_USER:
					final boolean bolLvalue = objPpatternsParameter.getBooleanValue(intLparameterValueIndex);
					if (!bolLnotices && !bolLwarnings && !bolLerrors) {
						bolLnotices = true;
						bolLwarnings = true;
						bolLerrors = true;
					}
					if (bolLnotices) {
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_INFO][Constants.bytS_UNCLASS_INITIAL] =
																																		bytLvalueType == FileParameter.bytS_PARAMETER_TYPE_USER
																																																? Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_INFO)
																																																: bolLvalue;
						if (bytLvalueType != FileParameter.bytS_PARAMETER_TYPE_USER) {
							this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_INFO] = true;
						}
					}
					if (bolLwarnings) {
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_WARNINGS][Constants.bytS_UNCLASS_INITIAL] =
																																			bytLvalueType == FileParameter.bytS_PARAMETER_TYPE_USER
																																																	? Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_WARNINGS)
																																																	: bolLvalue;
						if (bytLvalueType != FileParameter.bytS_PARAMETER_TYPE_USER) {
							this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_WARNINGS] = true;
						}
					}
					if (bolLerrors) {
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_ERRORS][Constants.bytS_UNCLASS_INITIAL] =
																																			bytLvalueType == FileParameter.bytS_PARAMETER_TYPE_USER
																																																	? Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_ERRORS)
																																																	: bolLvalue;
						if (bytLvalueType != FileParameter.bytS_PARAMETER_TYPE_USER) {
							this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_ERRORS] = true;
						}
					}
					bolLresetValues = true;
					break;

				case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
					final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);
					if (!bolLnotices && !bolLwarnings && !bolLerrors) {
						bolLnotices = true;
						bolLwarnings = true;
						bolLerrors = true;
					}
					if (bolLnotices) {
						this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_INFO, bolLlocal, bolPnewLists);
					}
					if (bolLwarnings) {
						this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_WARNINGS, bolLlocal, bolPnewLists);
					}
					if (bolLerrors) {
						this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_ERRORS, bolLlocal, bolPnewLists);
					}
					bolLresetValues = true;
					break;

				case FileParameter.bytS_PARAMETER_TYPE_STRING:
					if (bolLresetValues) {
						bolLnotices = false;
						bolLwarnings = false;
						bolLerrors = false;
						bolLresetValues = false;
					}
					final String strLvalue = objPpatternsParameter.getStringValue(intLparameterValueIndex);
					// TODO : Créer Constants.bytS_BOOLEAN_LOCAL_NOTICES
					// if (strLvalue.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_NOTICES][0])) {
					// objLnotice = true;
					// this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_NOTICES][Constants.bytS_UNCLASS_INITIAL] = true;
					// if (bolPnewLists) {
					// this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_NOTICES] = true;
					// }
					// break;
					// }

					if (strLvalue.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_WARNINGS][0])) {
						bolLwarnings = true;
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_WARNINGS][Constants.bytS_UNCLASS_INITIAL] = true;
						this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_WARNINGS] = true;
						break;
					}

					if (strLvalue.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_ERRORS][0])) {
						bolLerrors = true;
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_ERRORS][Constants.bytS_UNCLASS_INITIAL] = true;
						this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_ERRORS] = true;
						break;
					}

					//$FALL-THROUGH$
				case FileParameter.bytS_PARAMETER_TYPE_BYTE:
				case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
				default:
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(intLparameterValueIndex));
					break;
			}
		}
	}

	final public Object[] doParseParameter(PatternsFile objPpatternsBufferedReader, boolean bolPnewLists, boolean bolPstyles, boolean bolPsiteswaps) {
		byte bytLstate = FileParameterParser.bytS_STARTING_STATE;
		int intLcursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		int intLparameterNameIndex = Constants.bytS_UNCLASS_NO_VALUE;
		int intLparameterValueIndex = Constants.bytS_UNCLASS_NO_VALUE;
		final StringBuilder objLparameterNameStringBuilder = new StringBuilder(16);
		final StringBuilder objLparameterValueStringBuilder = new StringBuilder(16);
		String strLparameterTypeAndIndex = new String();

		final char chrLuncommentFileLineA[] = Strings.uncomment(objPpatternsBufferedReader.getLineString()).toLowerCase().toCharArray();

		// Parameter name and value extraction :
		for (intLcursorIndex = 1; intLcursorIndex < chrLuncommentFileLineA.length; ++intLcursorIndex) {

			// Space :
			if (chrLuncommentFileLineA[intLcursorIndex] == ' ') {
				if (bytLstate == FileParameterParser.bytS_GETTING_PARAMETER_NAME) {
					bytLstate = FileParameterParser.bytS_AFTER_PARAMETER_NAME;

					if (Constants.objS_ENGINE_PARAMETERS_HASH_MAP.containsKey(objLparameterNameStringBuilder.toString())) {
						strLparameterTypeAndIndex = Constants.objS_ENGINE_PARAMETERS_HASH_MAP.get(objLparameterNameStringBuilder.toString());

						continue;
					}
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_UNKNOWN_NAME,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										intLparameterNameIndex);

					return null;
				} else if (bytLstate == FileParameterParser.bytS_GETTING_PARAMETER_VALUE) {
					objLparameterValueStringBuilder.append(chrLuncommentFileLineA[intLcursorIndex]);
				}

				continue;
			}

			// Equals symbol :
			if (chrLuncommentFileLineA[intLcursorIndex] == '=') {
				if (bytLstate == FileParameterParser.bytS_GETTING_PARAMETER_NAME) {
					bytLstate = FileParameterParser.bytS_AFTER_EQUALS_SYMBOL;

					if (Constants.objS_ENGINE_PARAMETERS_HASH_MAP.containsKey(objLparameterNameStringBuilder.toString())) {
						intLparameterValueIndex = intLcursorIndex + 1;
						strLparameterTypeAndIndex = Constants.objS_ENGINE_PARAMETERS_HASH_MAP.get(objLparameterNameStringBuilder.toString());
					} else {
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_UNKNOWN_NAME,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											intLparameterNameIndex);

						return null;
					}
				} else if (bytLstate == FileParameterParser.bytS_AFTER_PARAMETER_NAME) {
					bytLstate = FileParameterParser.bytS_AFTER_EQUALS_SYMBOL;
					intLparameterValueIndex = intLcursorIndex + 1;
				} else if (bytLstate == FileParameterParser.bytS_GETTING_PARAMETER_VALUE) {
					objLparameterValueStringBuilder.append(chrLuncommentFileLineA[intLcursorIndex]);
				} else {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_UNEXPECTED_CHARACTER,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										intLcursorIndex);
					return null;
				}

				continue;
			}

			// Default - any other char :
			switch (bytLstate) {
				case FileParameterParser.bytS_STARTING_STATE:
					intLparameterNameIndex = intLcursorIndex;
					bytLstate = FileParameterParser.bytS_GETTING_PARAMETER_NAME;

					//$FALL-THROUGH$
				case FileParameterParser.bytS_GETTING_PARAMETER_NAME:
					objLparameterNameStringBuilder.append(chrLuncommentFileLineA[intLcursorIndex]);

					break;

				case FileParameterParser.bytS_AFTER_PARAMETER_NAME:
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_EXPECTED_EQUALS_SYMBOL,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										intLcursorIndex);

					return null;

				case FileParameterParser.bytS_AFTER_EQUALS_SYMBOL:
					bytLstate = FileParameterParser.bytS_GETTING_PARAMETER_VALUE;
					intLparameterValueIndex = intLcursorIndex;

					//$FALL-THROUGH$
				case FileParameterParser.bytS_GETTING_PARAMETER_VALUE:
					objLparameterValueStringBuilder.append(chrLuncommentFileLineA[intLcursorIndex]);

					break;
			}
		}

		// At the end of the extraction :
		switch (bytLstate) {
			case FileParameterParser.bytS_STARTING_STATE:
				this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_EXPECTED_NAME, ConsoleObject.bytS_ERROR_LEVEL, objPpatternsBufferedReader, 1);

				return null;

			case FileParameterParser.bytS_GETTING_PARAMETER_NAME:
			case FileParameterParser.bytS_AFTER_PARAMETER_NAME:
				this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_MISSING_VALUE,
									ConsoleObject.bytS_ERROR_LEVEL,
									objPpatternsBufferedReader,
									intLcursorIndex);

				return null;
		}

		// Parameter name and value analyzis :
		objLparameterNameStringBuilder.trimToSize();
		objLparameterValueStringBuilder.trimToSize();

		final byte bytLcontrolType = Byte.valueOf(strLparameterTypeAndIndex.substring(1));
		final FileParameter objLpatternsParameter =
													new FileParameter(	objLparameterNameStringBuilder.toString(),
																		intLparameterNameIndex,
																		objLparameterValueStringBuilder.toString(),
																		intLparameterValueIndex);

		switch (strLparameterTypeAndIndex.charAt(0)) {
			case 'i':
				return this.deprecatedDoParseByteParameter(	bytLcontrolType,
															objLpatternsParameter,
															objPpatternsBufferedReader,
															bolPnewLists,
															bolPstyles,
															bolPsiteswaps);

			case 'b':
				return this.deprecatedDoParseBooleanParameter(	bytLcontrolType,
																objLpatternsParameter,
																objPpatternsBufferedReader,
																bolPnewLists,
																bolPstyles,
																bolPsiteswaps);

			case 's':
				return this.deprecratedDoParseStringParameter(	bytLcontrolType,
																objLpatternsParameter,
																objPpatternsBufferedReader,
																bolPnewLists,
																bolPstyles,
																bolPsiteswaps);

			case 'o':
				return this.deprecatedDoParseOtherParameter(bytLcontrolType,
															objLpatternsParameter,
															objPpatternsBufferedReader,
															bolPnewLists,
															bolPstyles,
															bolPsiteswaps);

			default:
				this.doAddToConsole(Language.intS_CONSOLE_NO_SENSE_ERROR,
									ConsoleObject.bytS_ERROR_LEVEL,
									objPpatternsBufferedReader,
									Constants.bytS_UNCLASS_NO_VALUE);

				return null;
		}
	}

	final public void doParsePatternParameter(	FileParameter objPpatternsParameter,
												PatternsFile objPpatternsBufferedReader,
												boolean bolPsiteswaps,
												boolean bolPnewLists) {
	// TODO : doParsePatternParameter
	}

	final public void doParseShortcutOrJMParameter() {
	// TODO : point d'entrée des paramètres raccourcis et des paramètres spécifiques JM
	}

	final public void doParseSiteswapParameter(	FileParameter objPpatternsParameter,
												PatternsFile objPpatternsBufferedReader,
												boolean bolPsiteswaps,
												boolean bolPstyles,
												boolean bolPnewLists) {
		if (bolPsiteswaps && bolPstyles) {
			final boolean bolLjMparameter =
											objPpatternsParameter	.getName()
																	.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][1]);
			// PD parameter :
			if (bolLjMparameter) {
				if (objPpatternsParameter.getValuesNumber() > 1) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_MULTIPLE_VALUES,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(1));
					return;
				}

				final byte bytLvalue = objPpatternsParameter.getByteValue(0, (byte) 0, (byte) 1);
				if (bytLvalue == Constants.bytS_UNCLASS_NO_VALUE) {
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(0));
					return;
				}
				if (bytLvalue == 0) {
					this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][Constants.bytS_UNCLASS_INITIAL] = false;
				}
				this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] = bytLvalue > 0;
				if (bolPnewLists) {
					this.objGpatternsManager.bolGbyteIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP] = true;
				}
				return;
			}

			boolean bolLdayValue = false;
			boolean bolLnightValue = false;
			boolean bolLsiteswap = false;
			boolean bolLreverse = false;
			boolean bolLthrows = false;
			boolean bolLcolor = false;
			boolean bolLresetValues = false;

			// For each parameter value :
			for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {
				final byte bytLvalueType = objPpatternsParameter.getValueType(intLparameterValueIndex);
				final boolean bolLday =
										bolLdayValue
											|| !bolLnightValue
											&& this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL];
				final boolean bolLnight =
											bolLnightValue
												|| !bolLdayValue
												&& !this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_LIGHT][Constants.bytS_UNCLASS_INITIAL];

				switch (bytLvalueType) {
					case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
						final boolean bolLvalue = objPpatternsParameter.getBooleanValue(intLparameterValueIndex);
						if (bolLresetValues) {
							bolLdayValue = false;
							bolLnightValue = false;
							bolLsiteswap = false;
							bolLreverse = false;
							bolLthrows = false;
							bolLcolor = false;
							bolLresetValues = false;
						}
						if (bolLreverse || bolLthrows) {
							if (bolLreverse && !bolLvalue) {
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																							false;
							}
							if (bolLthrows && !bolLvalue) {
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][Constants.bytS_UNCLASS_INITIAL] =
																																							false;
							}
						} else {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																				bolLvalue;
							if (bolPnewLists) {
								this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP] = true;
							}
						}
						break;

					case FileParameter.bytS_PARAMETER_TYPE_BYTE:
					case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
					case FileParameter.bytS_PARAMETER_TYPE_STRING:
						if (bolLresetValues) {
							bolLdayValue = false;
							bolLnightValue = false;
							bolLsiteswap = false;
							bolLreverse = false;
							bolLthrows = false;
							bolLcolor = false;
							bolLresetValues = false;
						}

						final String strLvalue = objPpatternsParameter.getString(intLparameterValueIndex).toUpperCase();
						if (bytLvalueType == FileParameter.bytS_PARAMETER_TYPE_STRING) {
							// "day" or "night" :
							if (strLvalue.equalsIgnoreCase("day")) { // TODO : variabiliser & isFileDefined ?
								bolLdayValue = true;
								break;
							}
							if (strLvalue.equalsIgnoreCase("night")) { // TODO : variabiliser & isFileDefined ?
								bolLnightValue = true;
								break;
							}

							// "balls" :
							if (strLvalue.equals("balls")) {
								bolLthrows = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][Constants.bytS_UNCLASS_INITIAL] =
																																							true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																					true;
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS] = true;
								}
								break;
							}

							// "reverse" :
							if (strLvalue.equals("reverse")) {
								bolLreverse = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																							true;
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP] = true;
								}
								break;
							}
						}

						// Color value :
						final Color objLsiteswapColor = Tools.getPenColor(strLvalue);
						if (objLsiteswapColor != null) {
							bolLcolor = true;
							if (bolLday) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP_DAY][Constants.bytS_UNCLASS_INITIAL] =
																																					new String(strLvalue);
								if (bolPnewLists) {
									this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_SITESWAP_DAY] = true;
								}
							}
							if (bolLnight) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																																					new String(strLvalue);
								if (bolPnewLists) {
									this.objGpatternsManager.bolGstringIsFileDefinedA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT] = true;
								}
							}
							break;
						}

						// Error :
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;

					case FileParameter.bytS_PARAMETER_TYPE_USER:
						if (!bolLsiteswap && !bolLreverse && !bolLthrows && !bolLcolor) {
							bolLsiteswap = true;
							bolLreverse = true;
							bolLthrows = true;
							bolLcolor = true;
						}
						if (bolLsiteswap) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																				Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_SITESWAP);
						}
						if (bolLthrows) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][Constants.bytS_UNCLASS_INITIAL] =
																																						Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS);
						}
						if (bolLreverse) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP][Constants.bytS_UNCLASS_INITIAL] =
																																						Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP);
						}
						if (bolLcolor) {
							if (bolLday) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP_DAY][Constants.bytS_UNCLASS_INITIAL] =
																																					Preferences.getLocalStringPreference(Constants.bytS_STRING_LOCAL_SITESWAP_DAY);
							}
							if (bolLnight) {
								this.objGpatternsManager.strGglobalAA[Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT][Constants.bytS_UNCLASS_INITIAL] =
																																					Preferences.getLocalStringPreference(Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT);
							}
						}
						bolLresetValues = true;
						break;

					case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
						final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);

						if (!bolLsiteswap && !bolLreverse && !bolLthrows && !bolLcolor) {
							bolLsiteswap = true;
							bolLreverse = true;
							bolLthrows = true;
							bolLcolor = true;
						}
						if (bolLsiteswap) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_SITESWAP, bolLlocal, bolPnewLists);
						}
						if (bolLreverse) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP, bolLlocal, bolPnewLists);
						}
						if (bolLthrows) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS, bolLlocal, bolPnewLists);
						}
						if (bolLcolor) {
							if (bolLday) {
								this.setStringLocalness(Constants.bytS_STRING_LOCAL_SITESWAP_DAY, bolLlocal, bolPnewLists);
							}
							if (bolLnight) {
								this.setStringLocalness(Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT, bolLlocal, bolPnewLists);
							}
						}
						bolLresetValues = true;
						break;

					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;
				}
			}
		}
	}

	final public void doParseSoundsParameter(	FileParameter objPpatternsParameter,
												PatternsFile objPpatternsBufferedReader,
												boolean bolPsiteswaps,
												boolean bolPnewLists) {

		if (bolPsiteswaps) {

			if (!this.hasParsedShortcutOrJMParameter(	objPpatternsParameter,
														objPpatternsBufferedReader,
														Constants.bytS_BOOLEAN_LOCAL_SOUNDS,
														true,
														Strings.chrS_UNCLASS_NULL_CHAR,
														(byte) 0,
														(byte) 1,
														bolPnewLists)) {
				boolean bolLmetronome = false;
				boolean bolLcatches = false;
				boolean bolLthrows = false;
				boolean bolLresetValues = false;

				// For each parameter value :
				for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {

					final byte bytLparameterType = objPpatternsParameter.getValueType(intLparameterValueIndex);
					switch (bytLparameterType) {

						case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
						case FileParameter.bytS_PARAMETER_TYPE_USER:
							final boolean bolLvalue = objPpatternsParameter.getBooleanValue(intLparameterValueIndex);
							if (!bolLcatches && !bolLthrows && !bolLmetronome) {
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_SOUNDS][Constants.bytS_UNCLASS_INITIAL] =
																																					bytLparameterType == FileParameter.bytS_PARAMETER_TYPE_USER
																																																				? Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_SOUNDS)
																																																				: bolLvalue;
							} else {
								if (bolLcatches) {
									this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS][Constants.bytS_UNCLASS_INITIAL] =
																																							bytLparameterType == FileParameter.bytS_PARAMETER_TYPE_USER
																																																						? Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS)
																																																						: bolLvalue;
								}
								if (bolLthrows) {
									this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS][Constants.bytS_UNCLASS_INITIAL] =
																																							bytLparameterType == FileParameter.bytS_PARAMETER_TYPE_USER
																																																						? Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS)
																																																						: bolLvalue;
								}
								if (bolLmetronome) {
									this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_METRONOME][Constants.bytS_UNCLASS_INITIAL] =
																																							bytLparameterType == FileParameter.bytS_PARAMETER_TYPE_USER
																																																						? Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_METRONOME)
																																																						: bolLvalue;
								}
							}
							bolLresetValues = true;
							break;

						case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
							final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);
							if (!bolLcatches && !bolLthrows && !bolLmetronome) {
								this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_SOUNDS, bolLlocal, bolPnewLists);
							} else {
								if (bolLcatches) {
									this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS, bolLlocal, bolPnewLists);
								}
								if (bolLthrows) {
									this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS, bolLlocal, bolPnewLists);
								}
								if (bolLmetronome) {
									this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_METRONOME, bolLlocal, bolPnewLists);
								}
							}
							bolLresetValues = true;
							break;

						case FileParameter.bytS_PARAMETER_TYPE_STRING:
							if (bolLresetValues) {
								bolLmetronome = false;
								bolLcatches = false;
								bolLthrows = false;
								bolLresetValues = false;
							}
							final String strL = objPpatternsParameter.getStringValue(intLparameterValueIndex);

							if (strL.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS][0])) {
								bolLcatches = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS][Constants.bytS_UNCLASS_INITIAL] =
																																						true;
								this.objGpatternsManager.bolGbyteIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS] = true;
								break;
							}
							if (strL.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS][0])) {
								bolLthrows = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS][Constants.bytS_UNCLASS_INITIAL] =
																																						true;
								this.objGpatternsManager.bolGbyteIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS] = true;
								break;
							}
							if (strL.equalsIgnoreCase(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_METRONOME][0])) {
								bolLmetronome = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_METRONOME][Constants.bytS_UNCLASS_INITIAL] =
																																						true;
								this.objGpatternsManager.bolGbyteIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_METRONOME] = true;
								break;
							}

							//$FALL-THROUGH$
						case FileParameter.bytS_PARAMETER_TYPE_BYTE:
						case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
						default:
							this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
												ConsoleObject.bytS_ERROR_LEVEL,
												objPpatternsBufferedReader,
												objPpatternsParameter.getValueIndex(intLparameterValueIndex));
							break;
					}
				}
			}
		}
	}

	final private void doParseSpeedOrFluidityParameter(	FileParameter objPpatternsParameter,
														PatternsFile objPpatternsBufferedReader,
														byte bytPbyteLocalControl,
														boolean bolPnewLists) {

		final char chrLsingleChar = bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_SPEED ? '$' : '&';
		final byte bytLbyteLocalMinimumValue =
												bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_SPEED
																										? Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE
																										: Constants.bytS_BYTE_LOCAL_FLUIDITY_MINIMUM_VALUE;
		final byte bytLbyteLocalMaximumValue =
												bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_SPEED
																										? Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE
																										: Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE;

		if (!this.hasParsedShortcutOrJMParameter(	objPpatternsParameter,
													objPpatternsBufferedReader,
													bytPbyteLocalControl,
													false,
													chrLsingleChar,
													bytLbyteLocalMinimumValue,
													bytLbyteLocalMaximumValue,
													bolPnewLists)) {

			final byte bytLbooleanLocalControl =
													bytPbyteLocalControl == Constants.bytS_BYTE_LOCAL_SPEED ? Constants.bytS_BOOLEAN_LOCAL_SPEED
																											: Constants.bytS_BOOLEAN_LOCAL_FLUIDITY;

			Boolean bolLBoolean = null;
			Byte bytLByte = null;
			boolean bolLresetValues = false;

			// For each 'speed'/'fluidity' parameter value :
			for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {

				switch (objPpatternsParameter.getValueType(intLparameterValueIndex)) {
					case FileParameter.bytS_PARAMETER_TYPE_BYTE:
					case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
						if (bolLresetValues) {
							bolLBoolean = null;
							bolLresetValues = false;
						}
						bytLByte = objPpatternsParameter.getByteValue(intLparameterValueIndex, bytLbyteLocalMinimumValue, bytLbyteLocalMaximumValue);
						this.objGpatternsManager.setByte(bytPbyteLocalControl, bytLByte);
						this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPbyteLocalControl] = true;
						break;
					case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
						if (bolLresetValues) {
							bytLByte = null;
							bolLresetValues = false;
						}
						bolLBoolean = objPpatternsParameter.getBooleanValue(intLparameterValueIndex);
						this.objGpatternsManager.bolGglobalValueAA[bytLbooleanLocalControl][Constants.bytS_UNCLASS_INITIAL] = bolLBoolean;
						this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytLbooleanLocalControl] = true;
						break;

					case FileParameter.bytS_PARAMETER_TYPE_USER:
						if (bytLByte == null && bolLBoolean == null) {
							bytLByte = 0;
							bolLBoolean = Boolean.TRUE;
						}
						if (bolLBoolean != null) {
							this.objGpatternsManager.bolGglobalValueAA[bytLbooleanLocalControl][Constants.bytS_UNCLASS_INITIAL] =
																																	Preferences.getLocalBooleanPreference(bytLbooleanLocalControl);
						}
						if (bytLByte != null) {
							this.objGpatternsManager.setByte(bytPbyteLocalControl, null);
						}
						bolLresetValues = true;
						break;

					case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
						if (bytLByte == null && bolLBoolean == null) {
							bytLByte = 0;
							bolLBoolean = Boolean.TRUE;
						}

						final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);
						if (bolLBoolean != null) {
							this.setBooleanLocalness(bytLbooleanLocalControl, bolLlocal, bolPnewLists);
						}
						if (bytLByte != null) {
							this.setByteLocalness(bytPbyteLocalControl, bolLlocal, bolPnewLists);
						}
						bolLresetValues = true;
						break;
					case FileParameter.bytS_PARAMETER_TYPE_STRING:
					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;
				}
			}
		}
	}

	final public void doParseSpeedParameter(FileParameter objPpatternsParameter, PatternsFile objPpatternsBufferedReader, boolean bolPnewLists) {
		this.doParseSpeedOrFluidityParameter(objPpatternsParameter, objPpatternsBufferedReader, Constants.bytS_BYTE_LOCAL_SPEED, bolPnewLists);
	}

	final public void doParseStyleParameter(FileParameter objPpatternsParameter,
											PatternsFile objPpatternsBufferedReader,
											boolean bolPstyles,
											boolean bolPnewLists) {
		if (bolPstyles) {

			boolean bolLstyle = false;
			boolean bolLreverse = false;
			boolean bolLrandom = false;
			boolean bolLdefaults = false;
			boolean bolLresetValues = false;

			// For each parameter value :
			for (int intLparameterValueIndex = 0, intLparameterValuesNumber = objPpatternsParameter.getValuesNumber(); intLparameterValueIndex < intLparameterValuesNumber; ++intLparameterValueIndex) {
				final byte bytLvalueType = objPpatternsParameter.getValueType(intLparameterValueIndex);
				switch (bytLvalueType) {
					case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
						final boolean bolLvalue = objPpatternsParameter.getBooleanValue(intLparameterValueIndex);
						bolLresetValues = true;
						if (bolLreverse || bolLrandom || bolLdefaults) {
							if (bolLreverse) {
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																							bolLvalue;
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE] = true;
								}
							}
							if (bolLdefaults) {
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																					bolLvalue;
								if (!bolLvalue) {
									this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																								false;
								}
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS] = true;
								}
							}
							if (bolLrandom) {
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																							bolLvalue;
								if (bolLvalue) {
									this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																						true;
								}
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS] = true;
								}
							}
							break;
						}
						this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL] = bolLvalue;
						if (bolPnewLists) {
							this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_STYLE] = true;
						}
						break;

					case FileParameter.bytS_PARAMETER_TYPE_STRING:
						if (bolLresetValues) {
							bolLstyle = false;
							bolLreverse = false;
							bolLrandom = false;
							bolLdefaults = false;
							bolLresetValues = false;
						}

						final String strLvalue = objPpatternsParameter.getString(intLparameterValueIndex).toUpperCase();
						if (bytLvalueType == FileParameter.bytS_PARAMETER_TYPE_STRING) {

							// "reverse" :
							if (strLvalue.equals("reverse")) {
								bolLreverse = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																							true;
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE] = true;
								}
								break;
							}

							// "defaults" :
							if (strLvalue.equals("defaults")) {
								bolLdefaults = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																					true;
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS] = true;
								}
								break;
							}

							// "random" :
							if (strLvalue.equals("random")) {
								bolLrandom = true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																							true;
								this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																					true;
								if (bolPnewLists) {
									this.objGpatternsManager.bolGbooleanIsFileDefinedA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS] = true;
								}
								break;
							}
						}

						// Error :
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;

					case FileParameter.bytS_PARAMETER_TYPE_USER:
						if (!bolLstyle && !bolLreverse && !bolLdefaults && !bolLrandom) {
							bolLstyle = true;
							bolLreverse = true;
							bolLdefaults = true;
							bolLrandom = true;
						}
						if (bolLstyle) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																				Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_STYLE);
						}
						if (bolLreverse) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE][Constants.bytS_UNCLASS_INITIAL] =
																																						Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE);
						}
						if (bolLdefaults) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																				Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_DEFAULTS);
						}
						if (bolLrandom) {
							this.objGpatternsManager.bolGglobalValueAA[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][Constants.bytS_UNCLASS_INITIAL] =
																																						Preferences.getLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS);
						}
						bolLresetValues = true;
						break;

					case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
						final boolean bolLlocal = objPpatternsParameter.getLocalValue(intLparameterValueIndex);

						if (!bolLstyle && !bolLreverse && !bolLdefaults && !bolLrandom) {
							bolLstyle = true;
							bolLreverse = true;
							bolLdefaults = true;
							bolLrandom = true;
						}
						if (bolLstyle) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_STYLE, bolLlocal, bolPnewLists);
						}
						if (bolLreverse) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE, bolLlocal, bolPnewLists);
						}
						if (bolLdefaults) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_DEFAULTS, bolLlocal, bolPnewLists);
						}
						if (bolLrandom) {
							this.setBooleanLocalness(Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS, bolLlocal, bolPnewLists);
						}
						bolLresetValues = true;
						break;

					case FileParameter.bytS_PARAMETER_TYPE_BYTE:
					case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
					default:
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(intLparameterValueIndex));
						break;
				}
			}
		}
	}

	final private boolean hasParsedShortcutOrJMParameter(	FileParameter objPpatternsParameter,
															PatternsFile objPpatternsBufferedReader,
															byte bytPlocalControl,
															boolean bolPbooleanParameter,
															char chrPsingleChar,
															byte bytPbyteLocalMinimumValue,
															byte bytPbyteLocalMaximumValue,
															boolean bolPnewLists) {
		final boolean bolLjMparameter = objPpatternsParameter.getName().equalsIgnoreCase(Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytPlocalControl][1]);
		final boolean bolLsingleCharParameter = objPpatternsParameter.getName().compareTo(Character.toString(chrPsingleChar)) == 0;
		if (bolLsingleCharParameter || bolLjMparameter) {
			if (objPpatternsParameter.getValuesNumber() > 1) {
				this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_MULTIPLE_VALUES,
									ConsoleObject.bytS_ERROR_LEVEL,
									objPpatternsBufferedReader,
									objPpatternsParameter.getValueIndex(1));
				return true;
			}
			switch (objPpatternsParameter.getValueType(0)) {
				case FileParameter.bytS_PARAMETER_TYPE_BYTE:
					if (bolLjMparameter && !bolPbooleanParameter) {
						this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
											ConsoleObject.bytS_ERROR_LEVEL,
											objPpatternsBufferedReader,
											objPpatternsParameter.getValueIndex(0));
						break;
					}
					//$FALL-THROUGH$
				case FileParameter.bytS_PARAMETER_TYPE_FLOAT:
					if (bolPbooleanParameter) {
						this.objGpatternsManager.setBoolean(bytPlocalControl, objPpatternsParameter.getByteValue(	0,
																													bytPbyteLocalMinimumValue,
																													bytPbyteLocalMaximumValue) > 0);
					} else {
						this.objGpatternsManager.setByte(bytPlocalControl, objPpatternsParameter.getByteValue(	0,
																												bytPbyteLocalMinimumValue,
																												bytPbyteLocalMaximumValue));
					}
					if (bolPnewLists) {
						if (bolPbooleanParameter) {
							this.objGpatternsManager.bolGbooleanIsFileDefinedA[bytPlocalControl] = true;
						} else {
							this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPlocalControl] = true;
						}
					}
					break;
				case FileParameter.bytS_PARAMETER_TYPE_BOOLEAN:
				case FileParameter.bytS_PARAMETER_TYPE_USER:
				case FileParameter.bytS_PARAMETER_TYPE_LOCAL:
				case FileParameter.bytS_PARAMETER_TYPE_STRING:
				default:
					this.doAddToConsole(Language.intS_CONSOLE_PARAMETER_INVALID_VALUE,
										ConsoleObject.bytS_ERROR_LEVEL,
										objPpatternsBufferedReader,
										objPpatternsParameter.getValueIndex(0));
			}
			return true;
		}
		return false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 */
	final private void setAllBooleansToLocal(byte bytPcontrolType) {

		Pattern objLpattern = null;

		for (int intLobjectIndex = 0, intLobjectsListLength = this.objGpatternsManager.objGobjectA.length; intLobjectIndex < intLobjectsListLength; ++intLobjectIndex) {
			objLpattern = this.objGpatternsManager.getPattern(intLobjectIndex);

			if (objLpattern != null) {
				objLpattern.bolGlocalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] =
																								this.objGpatternsManager.bolGglobalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL];
				objLpattern.bolGlocalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																								this.objGpatternsManager.bolGglobalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_CURRENT];
			}
		}
	}

	final private void setAllBytesToLocal(byte bytPcontrolType) {
		if (this.objGpatternsManager.objGobjectA != null) {
			Pattern objLpattern = null;

			for (int intLobjectIndex = 0, intLobjectsListLength = this.objGpatternsManager.objGobjectA.length; intLobjectIndex < intLobjectsListLength; ++intLobjectIndex) {
				objLpattern = this.objGpatternsManager.getPattern(intLobjectIndex);

				if (objLpattern != null) {
					objLpattern.bytGlocalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] =
																									this.objGpatternsManager.bytGglobalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL];
					objLpattern.bytGlocalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																									this.objGpatternsManager.bytGglobalValueAA[bytPcontrolType][Constants.bytS_UNCLASS_CURRENT];
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 */
	final private void setAllStringsToLocal(byte bytPcontrolType) {
		if (this.objGpatternsManager.objGobjectA != null) {
			Pattern objLpattern = null;

			for (int intLobjectIndex = 0, intLobjectsListLength = this.objGpatternsManager.objGobjectA.length; intLobjectIndex < intLobjectsListLength; ++intLobjectIndex) {
				objLpattern = this.objGpatternsManager.getPattern(intLobjectIndex);

				if (objLpattern != null) {
					objLpattern.strGlocalAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL] =
																								new String(this.objGpatternsManager.strGglobalAA[bytPcontrolType][Constants.bytS_UNCLASS_INITIAL]);
					objLpattern.strGlocalAA[bytPcontrolType][Constants.bytS_UNCLASS_CURRENT] =
																								new String(this.objGpatternsManager.strGglobalAA[bytPcontrolType][Constants.bytS_UNCLASS_CURRENT]);
				}
			}
		}
	}

	final private void setBooleanLocalness(byte bytPbooleanLocalControl, boolean bolPlocal, boolean bolPnewLists) {
		if (bolPnewLists || bolPlocal) {
			if (!bolPnewLists && !this.objGpatternsManager.bolGbooleanIsLocalA[bytPbooleanLocalControl]) {
				this.setAllStringsToLocal(bytPbooleanLocalControl);
			}
			this.objGpatternsManager.bolGbooleanIsLocalA[bytPbooleanLocalControl] = bolPlocal;
			this.objGpatternsManager.bolGbooleanIsLocallyDefinedA[bytPbooleanLocalControl] = true;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 */
	final private void setByteLocalness(byte bytPbyteLocalControl, boolean bolPlocal, boolean bolPnewLists) {
		if (bolPnewLists || bolPlocal) {
			if (!bolPnewLists && !this.objGpatternsManager.bolGbyteIsLocalA[bytPbyteLocalControl]) {
				this.setAllBytesToLocal(bytPbyteLocalControl);
			}
			this.objGpatternsManager.bolGbyteIsLocalA[bytPbyteLocalControl] = bolPlocal;
			this.objGpatternsManager.bolGbyteIsLocallyDefinedA[bytPbyteLocalControl] = true;
		}
	}

	final private void setStringLocalness(byte bytPstringLocalControl, boolean bolPlocal, boolean bolPnewLists) {
		if (bolPnewLists || bolPlocal) {
			if (!bolPnewLists && !this.objGpatternsManager.bolGstringIsLocalA[bytPstringLocalControl]) {
				this.setAllStringsToLocal(bytPstringLocalControl);
			}
			this.objGpatternsManager.bolGstringIsLocalA[bytPstringLocalControl] = bolPlocal;
			this.objGpatternsManager.bolGstringIsLocallyDefinedA[bytPstringLocalControl] = true;
		}
	}

	private final PatternsManager	objGpatternsManager;

	final private static byte		bytS_AFTER_EQUALS_SYMBOL		= 3;

	final private static byte		bytS_AFTER_PARAMETER_NAME		= 2;

	final private static byte		bytS_GETTING_PARAMETER_NAME		= 1;

	final private static byte		bytS_GETTING_PARAMETER_VALUE	= 4;

	final private static byte		bytS_STARTING_STATE				= 0;

	@SuppressWarnings("unused")
	final private static long		serialVersionUID				= Constants.lngS_ENGINE_VERSION_NUMBER;

}
