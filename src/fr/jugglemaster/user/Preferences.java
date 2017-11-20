/*
 * @(#)JugglePreferences.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.user;

import fr.jugglemaster.pattern.BallsColors;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Preferences {

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static boolean getGlobalBooleanPreference(byte bytPpreferenceType) {
		return Preferences.loadGlobalBooleanPreference(bytPpreferenceType, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static byte getGlobalBytePreference(byte bytPpreferenceType) {
		return Preferences.loadGlobalBytePreference(bytPpreferenceType, false);
	}

	public static String getGlobalStringPreference(byte bytPpreferenceType) {
		return Preferences.loadGlobalStringPreference(bytPpreferenceType, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPallPreferences
	 * @return
	 */
	final public static String getInfo() {
		final StringBuilder objLpreferencesStringBuilder =
															new StringBuilder(64
																				+ (Constants.intS_UNCLASS_CONTROLS_STRINGS_LENGTH + 24)
																				* (Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
																					+ Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER
																					+ Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER
																					+ Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER + Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER));

		objLpreferencesStringBuilder.append(Strings.doConcat(	"JugglePreferences (ROM, RAM) :",
																Strings.strS_LINE_SEPARATOR,
																Strings.getCharsString('¯', 43),
																Strings.strS_LINE_SEPARATOR,
																Strings.strS_LINE_SEPARATOR));

		if (Preferences.objSpreferences != null) {
			synchronized (Preferences.objSpreferences) {

				// Global boolean preferences :
				for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
					objLpreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BOOLEAN_GLOBAL,
																			Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[bytLpreferenceIndex],
																			" = (",
																			Strings.getFixedLengthString(	Preferences.objSpreferences.getBoolean(	Constants.strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																					Preferences.bolSglobalPreferenceA[bytLpreferenceIndex]),
																											5),
																			", ",
																			Strings.getFixedLengthString(	Preferences.bolSglobalPreferenceA[bytLpreferenceIndex],
																											5),
																			')',
																			(Preferences.objSpreferences.getBoolean(Constants.strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																													Preferences.bolSglobalPreferenceA[bytLpreferenceIndex]) != Preferences.bolSglobalPreferenceA[bytLpreferenceIndex])
																																																										? Strings.strS_RIGHT_ASTERIX
																																																										: null,
																			Strings.strS_LINE_SEPARATOR));
				}

				// Local boolean preferences :
				for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
					objLpreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BOOLEAN_LOCAL,
																			Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLpreferenceIndex][0],
																			" = (",
																			Strings.getFixedLengthString(	Preferences.objSpreferences.getBoolean(	Constants.strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																					Preferences.bolSlocalPreferenceA[bytLpreferenceIndex]),
																											5),
																			", ",
																			Strings.getFixedLengthString(	Preferences.bolSlocalPreferenceA[bytLpreferenceIndex],
																											5),
																			')',
																			(Preferences.objSpreferences.getBoolean(Constants.strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																													Preferences.bolSlocalPreferenceA[bytLpreferenceIndex]) != Preferences.bolSlocalPreferenceA[bytLpreferenceIndex])
																																																									? Strings.strS_RIGHT_ASTERIX
																																																									: null,
																			Strings.strS_LINE_SEPARATOR));
				}

				// Global byte preferences :
				for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
					objLpreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BYTE_GLOBAL,
																			Constants.strS_BYTE_GLOBAL_CONTROL_A[bytLpreferenceIndex],
																			" = (",
																			Strings.getValueString(	Preferences.objSpreferences.getInt(	Constants.strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																		Preferences.bytSglobalPreferenceA[bytLpreferenceIndex]),
																									2,
																									0,
																									2,
																									true),
																			", ",
																			Strings.getValueString(	Preferences.bytSglobalPreferenceA[bytLpreferenceIndex],
																									2,
																									0,
																									2,
																									true),
																			')',
																			(Preferences.objSpreferences.getInt(Constants.strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																												Preferences.bytSglobalPreferenceA[bytLpreferenceIndex]) != Preferences.bytSglobalPreferenceA[bytLpreferenceIndex])
																																																									? Strings.strS_RIGHT_ASTERIX
																																																									: null,
																			Strings.strS_LINE_SEPARATOR));
				}

				// Local byte preferences :
				for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
					objLpreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_BYTE_LOCAL,
																			Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLpreferenceIndex][0],
																			" = (",
																			Strings.getValueString(	Preferences.objSpreferences.getInt(	Constants.strS_BYTE_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																		Preferences.bytSlocalPreferenceA[bytLpreferenceIndex]),
																									2,
																									0,
																									2,
																									true),
																			", ",
																			Strings.getValueString(	Preferences.bytSlocalPreferenceA[bytLpreferenceIndex],
																									2,
																									0,
																									2,
																									true),
																			')',
																			(Preferences.objSpreferences.getInt(Constants.strS_BYTE_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																												Preferences.bytSlocalPreferenceA[bytLpreferenceIndex]) != Preferences.bytSlocalPreferenceA[bytLpreferenceIndex])
																																																								? Strings.strS_RIGHT_ASTERIX
																																																								: null,
																			Strings.strS_LINE_SEPARATOR));
				}

				// Global string preferences :
				for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
					final String strLdiskPreference =
														Preferences.objSpreferences.get(Constants.strS_STRING_GLOBAL_CONTROL_A[bytLpreferenceIndex],
																						Preferences.strSglobalPreferenceA[bytLpreferenceIndex]);
					objLpreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_STRING_GLOBAL,
																			Constants.strS_STRING_GLOBAL_CONTROL_A[bytLpreferenceIndex],
																			" = (\"",
																			Preferences.objSpreferences.get(Constants.strS_STRING_GLOBAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																											Preferences.strSglobalPreferenceA[bytLpreferenceIndex]),
																			"\", \"",
																			Preferences.strSglobalPreferenceA[bytLpreferenceIndex],
																			"\")",
																			Strings.areNullEqual(	strLdiskPreference,
																									Preferences.strSglobalPreferenceA[bytLpreferenceIndex])
																																							? null
																																							: Strings.strS_RIGHT_ASTERIX,
																			Strings.strS_LINE_SEPARATOR));
				}

				// Local string preferences :
				for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {

					final String strLdiskPreference =
														Preferences.objSpreferences.get(Constants.strS_STRING_LOCAL_CONTROL_A[bytLpreferenceIndex],
																						Preferences.strSlocalPreferenceA[bytLpreferenceIndex]);
					objLpreferencesStringBuilder.append(Strings.doConcat(	Constants.strS_STRING_LOCAL,
																			Constants.strS_STRING_LOCAL_CONTROL_A[bytLpreferenceIndex],
																			" = (\"",
																			Preferences.objSpreferences.get(Constants.strS_STRING_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																											Preferences.strSlocalPreferenceA[bytLpreferenceIndex]),
																			"\", \"",
																			Preferences.strSlocalPreferenceA[bytLpreferenceIndex],
																			"\")",
																			Strings.areNullEqual(	strLdiskPreference,
																									Preferences.strSlocalPreferenceA[bytLpreferenceIndex])
																																							? null
																																							: Strings.strS_RIGHT_ASTERIX,
																			Strings.strS_LINE_SEPARATOR));
				}
			}
		} else {

			// No preference :
			objLpreferencesStringBuilder.append(Strings.doConcat("No recorded preferences.", Strings.strS_LINE_SEPARATOR));
		}

		return objLpreferencesStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static boolean getLocalBooleanPreference(byte bytPpreferenceType) {
		return Preferences.loadLocalBooleanPreference(bytPpreferenceType, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static byte getLocalBytePreference(byte bytPpreferenceType) {
		return Preferences.loadLocalBytePreference(bytPpreferenceType, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static String getLocalStringPreference(byte bytPpreferenceType) {
		return Preferences.loadLocalStringPreference(bytPpreferenceType, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpermanentPreference
	 * @return
	 */
	private static boolean loadGlobalBooleanPreference(byte bytPpreferenceType, boolean bolPpermanentPreference) {
		boolean bolLpreference = false;

		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER)) {
			try {
				bolLpreference =
									(((Preferences.objSpreferences != null) && bolPpermanentPreference)
																										? Preferences.objSpreferences.getBoolean(	Constants.strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
																																					Preferences.bolSglobalPreferenceA[bytPpreferenceType])
																										: Preferences.bolSglobalPreferenceA[bytPpreferenceType]);
			} catch (final Throwable objPthrowable) {
				Tools.err(Strings.doConcat("Error while reading preference ", Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[bytPpreferenceType]));
			}
		}

		return bolLpreference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpermanentPreference
	 * @return
	 */
	private static byte loadGlobalBytePreference(byte bytPpreferenceType, boolean bolPpermanentPreference) {
		byte bytLpreference = Constants.bytS_UNCLASS_NO_VALUE;

		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER)) {
			try {
				bytLpreference =
									(byte) (((Preferences.objSpreferences != null) && bolPpermanentPreference)
																												? Preferences.objSpreferences.getInt(	Constants.strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
																																						Preferences.bytSglobalPreferenceA[bytPpreferenceType])
																												: Preferences.bytSglobalPreferenceA[bytPpreferenceType]);
			} catch (final Throwable objPthrowable) {
				Tools.err(Strings.doConcat("Error while reading preference ", Constants.strS_BYTE_GLOBAL_CONTROL_A[bytPpreferenceType]));
			}
		}

		return bytLpreference;
	}

	private static String loadGlobalStringPreference(byte bytPpreferenceType, boolean bolPpermanentPreference) {
		String strLpreference = Strings.strS_EMPTY;

		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER)) {
			try {
				strLpreference =
									(((Preferences.objSpreferences != null) && bolPpermanentPreference)
																										? Preferences.objSpreferences.get(	Constants.strS_STRING_GLOBAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
																																			Preferences.strSglobalPreferenceA[bytPpreferenceType])
																										: Preferences.strSglobalPreferenceA[bytPpreferenceType]);
			} catch (final Throwable objPthrowable) {
				Tools.err(Strings.doConcat("Error while reading preference ", Constants.strS_STRING_GLOBAL_CONTROL_A[bytPpreferenceType]));
			}
		}

		return strLpreference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpermanentPreference
	 * @return
	 */
	private static boolean loadLocalBooleanPreference(byte bytPpreferenceType, boolean bolPpermanentPreference) {
		boolean bolLpreference = false;

		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER)) {
			try {
				bolLpreference =
									(((Preferences.objSpreferences != null) && bolPpermanentPreference)
																										? Preferences.objSpreferences.getBoolean(	Constants.strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
																																					Preferences.bolSlocalPreferenceA[bytPpreferenceType])
																										: Preferences.bolSlocalPreferenceA[bytPpreferenceType]);
			} catch (final Throwable objPthrowable) {
				Tools.err(Strings.doConcat("Error while reading preference ", Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytPpreferenceType][0]));
			}
		}
		return bolLpreference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpermanentPreference
	 * @return
	 */
	private static byte loadLocalBytePreference(byte bytPpreferenceType, boolean bolPpermanentPreference) {
		byte bytLpreference = Constants.bytS_UNCLASS_NO_VALUE;

		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER)) {
			try {
				bytLpreference =
									(byte) (((Preferences.objSpreferences != null) && bolPpermanentPreference)
																												? Preferences.objSpreferences.getInt(	Constants.strS_BYTE_LOCAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
																																						Preferences.bytSlocalPreferenceA[bytPpreferenceType])
																												: Preferences.bytSlocalPreferenceA[bytPpreferenceType]);
			} catch (final Throwable objPthrowable) {
				Tools.err(Strings.doConcat("Error while reading preference ", Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytPpreferenceType][0]));
			}
		}

		return bytLpreference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpermanentPreference
	 * @return
	 */
	private static String loadLocalStringPreference(byte bytPpreferenceType, boolean bolPpermanentPreference) {
		String strLpreference = null;

		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER)) {
			try {
				strLpreference =
									(((Preferences.objSpreferences != null) && bolPpermanentPreference)
																										? Preferences.objSpreferences.get(	Constants.strS_STRING_LOCAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
																																			Preferences.strSlocalPreferenceA[bytPpreferenceType])
																										: Preferences.strSlocalPreferenceA[bytPpreferenceType]);
			} catch (final Throwable objPthrowable) {
				Tools.err(Strings.doConcat("Error while reading preference ", Constants.strS_STRING_LOCAL_CONTROL_A[bytPpreferenceType]));
			}
		}
		return strLpreference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static boolean readGlobalBooleanPreference(byte bytPpreferenceType) {
		return Preferences.loadGlobalBooleanPreference(bytPpreferenceType, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static byte readGlobalBytePreference(byte bytPpreferenceType) {
		return Preferences.loadGlobalBytePreference(bytPpreferenceType, true);
	}

	public static String readGlobalStringPreference(byte bytPpreferenceType) {
		return Preferences.loadGlobalStringPreference(bytPpreferenceType, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static boolean readLocalBooleanPreference(byte bytPpreferenceType) {
		return Preferences.loadLocalBooleanPreference(bytPpreferenceType, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static byte readLocalBytePreference(byte bytPpreferenceType) {
		return Preferences.loadLocalBytePreference(bytPpreferenceType, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @return
	 */
	public static String readLocalStringPreference(byte bytPpreferenceType) {
		return Preferences.loadLocalStringPreference(bytPpreferenceType, true);
	}

	public static void resetAllPreferences() {
		Preferences.resetPreferences(true);
	}

	public static void resetHiddenPreferences() {
		Preferences.resetPreferences(false);
	}

	public static void resetPreferences(boolean bolPallPreferences) {

		// Local boolean values :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			switch (bytLpreferenceIndex) {
				case Constants.bytS_BOOLEAN_LOCAL_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_COLORS:
				case Constants.bytS_BOOLEAN_LOCAL_STYLE:
				case Constants.bytS_BOOLEAN_LOCAL_LIGHT:
				case Constants.bytS_BOOLEAN_LOCAL_INFO:
				case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
				case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
				case Constants.bytS_BOOLEAN_LOCAL_BALLS:
				case Constants.bytS_BOOLEAN_LOCAL_EDITION:
				case Constants.bytS_BOOLEAN_LOCAL_SPEED:
				case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
					Preferences.saveLocalBooleanPreference(bytLpreferenceIndex, true, true);

					break;

				case Constants.bytS_BOOLEAN_LOCAL_MIRROR:
				case Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS:
				case Constants.bytS_BOOLEAN_LOCAL_FX:
				case Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS:
				case Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS:
				case Constants.bytS_BOOLEAN_LOCAL_SITESWAP:
				case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
				case Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE:
				case Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP:
				case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
				case Constants.bytS_BOOLEAN_LOCAL_FLASH:
				case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
				case Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL:
				case Constants.bytS_BOOLEAN_LOCAL_METRONOME:
				case Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS:
					Preferences.saveLocalBooleanPreference(bytLpreferenceIndex, false, true);

					break;
			}
		}

		// Global boolean values :
		if (bolPallPreferences) {
			for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
				Preferences.saveGlobalBooleanPreference(bytLpreferenceIndex, bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER
																				&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_MARK
																				&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_SKILL, true);
			}
		}

		// Local byte values :
		final byte[] bytLdefaultValueA = new byte[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];

		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS] = Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_STROBE] = Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_SKILL] = Constants.bytS_BYTE_LOCAL_SKILL_AMATEUR;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_MARK] = Constants.bytS_BYTE_LOCAL_MARK_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY] = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT] = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL] = Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_SPEED] = Constants.bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED] = Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_FLUIDITY] = Constants.bytS_BYTE_LOCAL_FLUIDITY_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_HEIGHT] = Constants.bytS_BYTE_LOCAL_HEIGHT_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_DWELL] = Constants.bytS_BYTE_LOCAL_DWELL_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_DEFAULTS] = Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE;

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			if (bolPallPreferences || bytLpreferenceIndex != Constants.bytS_BYTE_LOCAL_BALLS_TRAIL
				&& bytLpreferenceIndex != Constants.bytS_BYTE_LOCAL_SPEED && bytLpreferenceIndex != Constants.bytS_BYTE_LOCAL_FLUIDITY) {
				Preferences.saveLocalBytePreference(bytLpreferenceIndex, bytLdefaultValueA[bytLpreferenceIndex], true);
			}
		}

		// Global byte values :
		if (bolPallPreferences) {
			Preferences.saveGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER,
													Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE,
													true);
			Preferences.saveGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER,
													Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MAXIMUM_VALUE,
													true);
			Preferences.saveGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_SKILL, Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER, true);
			Preferences.saveGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_SKILL, Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE, true);
			Preferences.saveGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_MARK, Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE, true);
			Preferences.saveGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_MARK, Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE, true);
			Preferences.saveGlobalBytePreference(	Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION,
													Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE,
													true);
		}

		// Local string values :
		String[] strLdefaultValueA = new String[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];

		strLdefaultValueA[Constants.bytS_STRING_LOCAL_SITESWAP_DAY] = Constants.strS_STRING_LOCAL_SITESWAP_DAY_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_TOOLTIP] = Strings.strS_EMPTY;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT] = Constants.strS_STRING_LOCAL_SITESWAP_NIGHT_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] = Constants.strS_STRING_LOCAL_BACKGROUND_DAY_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT] = Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_JUGGLER_DAY] = Constants.strS_STRING_LOCAL_JUGGLER_DAY_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT] = Constants.strS_STRING_LOCAL_JUGGLER_NIGHT_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_COLORS] = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_REVERSE_COLORS] = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_PATTERN] = Strings.strS_EMPTY;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_STYLE] = Constants.strS_STRING_LOCAL_STYLE_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_SITESWAP] = Constants.strS_STRING_LOCAL_SITESWAP_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP] = Constants.strS_STRING_LOCAL_SITESWAP_DEFAULT;
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			if (bolPallPreferences || bytLpreferenceIndex != Constants.bytS_STRING_LOCAL_SITESWAP_DAY
				&& bytLpreferenceIndex != Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT
				&& bytLpreferenceIndex != Constants.bytS_STRING_LOCAL_BACKGROUND_DAY
				&& bytLpreferenceIndex != Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT
				&& bytLpreferenceIndex != Constants.bytS_STRING_LOCAL_JUGGLER_DAY && bytLpreferenceIndex != Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT) {
				Preferences.saveLocalStringPreference(bytLpreferenceIndex, strLdefaultValueA[bytLpreferenceIndex], true);
			}
		}

		// Global string values :
		if (bolPallPreferences) {
			strLdefaultValueA = new String[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER];
			strLdefaultValueA[Constants.bytS_STRING_GLOBAL_LANGUAGE] = "fr";
			for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
				Preferences.saveGlobalStringPreference(bytLpreferenceIndex, strLdefaultValueA[bytLpreferenceIndex], true);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpreferenceValue
	 * @param bolPpermanentPreference
	 */
	final private static void saveGlobalBooleanPreference(byte bytPpreferenceType, boolean bolPpreferenceValue, boolean bolPpermanentPreference) {
		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER)) {
			Preferences.bolSglobalPreferenceA[bytPpreferenceType] = bolPpreferenceValue;

			if ((Preferences.objSpreferences != null) && bolPpermanentPreference) {
				try {
					Preferences.objSpreferences.putBoolean(	Constants.strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
															Preferences.bolSglobalPreferenceA[bytPpreferenceType]);
					Preferences.objSpreferences.flush();
				} catch (final Throwable objPthrowable) {
					Tools.err(Strings.doConcat("Error while writing preference ", Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[bytPpreferenceType]));
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPpreferenceValue
	 * @param bolPpermanentPreference
	 */
	final private static void saveGlobalBytePreference(byte bytPpreferenceType, byte bytPpreferenceValue, boolean bolPpermanentPreference) {
		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER)) {
			byte bytLpreferenceValue = bytPpreferenceValue;
			switch (bytPpreferenceType) {
				case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
				case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
					bytLpreferenceValue =
											(byte) Math.max(Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER,
															Math.min(Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE, bytLpreferenceValue));
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
				case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
					bytLpreferenceValue =
											(byte) Math.max(Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE,
															Math.min(Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE, bytLpreferenceValue));
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
				case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
					bytLpreferenceValue =
											(byte) Math.max(Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE,
															Math.min(Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MAXIMUM_VALUE, bytLpreferenceValue));
					break;
				case Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION:
					bytLpreferenceValue =
											(byte) Math.max(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_MINIMUM_VALUE,
															Math.min(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_MAXIMUM_VALUE, bytLpreferenceValue));
					break;
			}
			Preferences.bytSglobalPreferenceA[bytPpreferenceType] = bytLpreferenceValue;

			if ((Preferences.objSpreferences != null) && bolPpermanentPreference) {
				try {
					Preferences.objSpreferences.putInt(	Constants.strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
														Preferences.bytSglobalPreferenceA[bytPpreferenceType]);
					Preferences.objSpreferences.flush();
				} catch (final Throwable objPthrowable) {
					Tools.err(Strings.doConcat("Error while writing preference ", Constants.strS_BYTE_GLOBAL_CONTROL_A[bytPpreferenceType]));
				}
			}
		}
	}

	final private static void saveGlobalStringPreference(byte bytPpreferenceType, String strPpreferenceValue, boolean bolPpermanentPreference) {
		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER)) {
			Preferences.strSglobalPreferenceA[bytPpreferenceType] = strPpreferenceValue;

			if ((Preferences.objSpreferences != null) && bolPpermanentPreference) {
				try {
					Preferences.objSpreferences.put(Constants.strS_STRING_GLOBAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
													Preferences.strSglobalPreferenceA[bytPpreferenceType]);
					Preferences.objSpreferences.flush();
				} catch (final Throwable objPthrowable) {
					Tools.err(Strings.doConcat("Error while writing preference ", Constants.strS_STRING_GLOBAL_CONTROL_A[bytPpreferenceType]));
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpreference
	 * @param bolPpermanentPreference
	 */
	final private static void saveLocalBooleanPreference(byte bytPpreferenceType, boolean bolPpreference, boolean bolPpermanentPreference) {
		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER)) {
			Preferences.bolSlocalPreferenceA[bytPpreferenceType] = bolPpreference;

			if ((Preferences.objSpreferences != null) && bolPpermanentPreference) {
				try {
					Preferences.objSpreferences.putBoolean(	Constants.strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
															Preferences.bolSlocalPreferenceA[bytPpreferenceType]);
					Preferences.objSpreferences.flush();
				} catch (final Throwable objPthrowable) {
					Tools.err(Strings.doConcat("Error while writing preference ", Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytPpreferenceType][0]));
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPpreferenceValue
	 * @param bolPpermanentPreference
	 */
	private static void saveLocalBytePreference(byte bytPpreferenceType, byte bytPpreferenceValue, boolean bolPpermanentPreference) {
		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER)) {
			switch (bytPpreferenceType) {
				case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS] =
																									(byte) Math.max(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH,
																													Math.min(	Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE,
																																bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_STROBE:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_STROBE] =
																							(byte) Math.max(Constants.bytS_BYTE_LOCAL_STROBE_MINIMUM_RATE,
																											Math.min(	Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE,
																														bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL] =
																								(byte) Math.max(Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE,
																												Math.min(	Constants.bytS_BYTE_LOCAL_BALLS_MAXIMUM_VALUE,
																															bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_SKILL:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_SKILL] =
																						(byte) Math.max(Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER,
																										Math.min(	Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE,
																													bytPpreferenceValue));

					break;
				case Constants.bytS_BYTE_LOCAL_MARK:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_MARK] =
																						(byte) Math.max(Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE,
																										Math.min(	Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE,
																													bytPpreferenceValue));

					break;
				case Constants.bytS_BYTE_LOCAL_DEFAULTS:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_DEFAULTS] =
																							(byte) Math.max(Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE,
																											Math.min(	Constants.bytS_BYTE_LOCAL_DEFAULTS_MAXIMUM_VALUE,
																														bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY:
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT:
					Preferences.bytSlocalPreferenceA[bytPpreferenceType] =
																			(byte) Math.max(Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM,
																							Math.min(	Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL,
																										bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_SPEED:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_SPEED] =
																						(byte) Math.max(Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE,
																										Math.min(	Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE,
																													bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED] =
																									(byte) Math.max(Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_MINIMUM_VALUE,
																													Math.min(	Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_MAXIMUM_VALUE,
																																bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_FLUIDITY:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_FLUIDITY] =
																							(byte) Math.max(Constants.bytS_BYTE_LOCAL_FLUIDITY_MINIMUM_VALUE,
																											Math.min(	Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE,
																														bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_HEIGHT:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_HEIGHT] =
																							(byte) Math.max(Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE,
																											Math.min(	Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE,
																														bytPpreferenceValue));

					break;

				case Constants.bytS_BYTE_LOCAL_DWELL:
					Preferences.bytSlocalPreferenceA[Constants.bytS_BYTE_LOCAL_DWELL] =
																						(byte) Math.max(Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE,
																										Math.min(	Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE,
																													bytPpreferenceValue));

					break;
			}

			if ((Preferences.objSpreferences != null) && bolPpermanentPreference) {
				try {
					Preferences.objSpreferences.putInt(	Constants.strS_BYTE_LOCAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
														Preferences.bytSlocalPreferenceA[bytPpreferenceType]);
					Preferences.objSpreferences.flush();
				} catch (final Throwable objPthrowable) {
					Tools.err(Strings.doConcat("Error while writing preference ", Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytPpreferenceType][0]));
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param strPpreference
	 * @param bolPpermanentPreference
	 */
	final private static void saveLocalStringPreference(byte bytPpreferenceType, String strPpreference, boolean bolPpermanentPreference) {
		if ((bytPpreferenceType >= 0) && (bytPpreferenceType < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER)) {
			String strLpreferenceFormatted = null;

			switch (bytPpreferenceType) {
				case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
				case Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT:
				case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
				case Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT:
				case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
				case Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT:
					strLpreferenceFormatted = strPpreference;

					if (Tools.getPenColor(strPpreference) == null) {
						return;
					}

					break;

				case Constants.bytS_STRING_LOCAL_PATTERN:
				case Constants.bytS_STRING_LOCAL_STYLE:
				case Constants.bytS_STRING_LOCAL_SITESWAP:
				case Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP:
				case Constants.bytS_STRING_LOCAL_TOOLTIP:
					strLpreferenceFormatted = strPpreference;

					break;

				case Constants.bytS_STRING_LOCAL_COLORS:
				case Constants.bytS_STRING_LOCAL_REVERSE_COLORS:
					strLpreferenceFormatted = new BallsColors(strPpreference).getBallsColorsString();
					if (strLpreferenceFormatted == null) {
						return;
					}

					break;
			}

			Preferences.strSlocalPreferenceA[bytPpreferenceType] = strLpreferenceFormatted;

			if ((Preferences.objSpreferences != null) && bolPpermanentPreference) {
				try {
					Preferences.objSpreferences.put(Constants.strS_STRING_LOCAL_CONTROL_PREFERENCE_A[bytPpreferenceType],
													Preferences.strSlocalPreferenceA[bytPpreferenceType]);
					Preferences.objSpreferences.flush();
				} catch (final Throwable objPthrowable) {
					Tools.err(Strings.doConcat("Error while writing preference ", Constants.strS_STRING_LOCAL_CONTROL_A[bytPpreferenceType]));
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpreferenceValue
	 */
	final public static void setGlobalBooleanPreference(byte bytPpreferenceType, boolean bolPpreferenceValue) {
		Preferences.saveGlobalBooleanPreference(bytPpreferenceType, bolPpreferenceValue, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPpreferenceValue
	 */
	final public static void setGlobalBytePreference(byte bytPpreferenceType, byte bytPpreferenceValue) {
		Preferences.saveGlobalBytePreference(bytPpreferenceType, bytPpreferenceValue, false);
	}

	final public static void setGlobalStringPreference(byte bytPpreferenceType, String strPpreferenceValue) {
		Preferences.saveGlobalStringPreference(bytPpreferenceType, strPpreferenceValue, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpreferenceValue
	 */
	final public static void setLocalBooleanPreference(byte bytPpreferenceType, boolean bolPpreferenceValue) {
		Preferences.saveLocalBooleanPreference(bytPpreferenceType, bolPpreferenceValue, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPpreferenceValue
	 */
	final public static void setLocalBytePreference(byte bytPpreferenceType, byte bytPpreferenceValue) {
		Preferences.saveLocalBytePreference(bytPpreferenceType, bytPpreferenceValue, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param strPpreference
	 */
	final public static void setLocalStringPreference(byte bytPpreferenceType, String strPpreference) {
		Preferences.saveLocalStringPreference(bytPpreferenceType, strPpreference, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpreferenceValue
	 */
	final public static void writeGlobalBooleanPreference(byte bytPpreferenceType, boolean bolPpreferenceValue) {
		// JugglePreferences.logIn();
		Preferences.saveGlobalBooleanPreference(bytPpreferenceType, bolPpreferenceValue, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPpreferenceValue
	 */
	public static void writeGlobalBytePreference(byte bytPpreferenceType, byte bytPpreferenceValue) {
		Preferences.saveGlobalBytePreference(bytPpreferenceType, bytPpreferenceValue, true);
	}

	final public static void writeGlobalStringPreference(byte bytPpreferenceType, String strPpreferenceValue) {
		// JugglePreferences.logIn();
		Preferences.saveGlobalStringPreference(bytPpreferenceType, strPpreferenceValue, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bolPpreferenceValue
	 */
	final public static void writeLocalBooleanPreference(byte bytPpreferenceType, boolean bolPpreferenceValue) {
		// JugglePreferences.logIn();
		Preferences.saveLocalBooleanPreference(bytPpreferenceType, bolPpreferenceValue, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param bytPpreferenceValue
	 */
	public static void writeLocalBytePreference(byte bytPpreferenceType, byte bytPpreferenceValue) {
		Preferences.saveLocalBytePreference(bytPpreferenceType, bytPpreferenceValue, true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPpreferenceType
	 * @param strPpreference
	 */
	public static void writeLocalStringPreference(byte bytPpreferenceType, String strPpreference) {
		Preferences.saveLocalStringPreference(bytPpreferenceType, strPpreference, true);
	}

	final private static boolean[]						bolSglobalPreferenceA;

	final private static boolean[]						bolSlocalPreferenceA;

	final private static byte[]							bytSglobalPreferenceA;

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	// final private static void log(Object... objPobjectA) {
	// if (JugglePreferences.bolS_UNCLASS_LOG) {
	// JuggleTools.log(JuggleTools.doConcat(objPobjectA, objPobjectA != null ? ' ' : null, JuggleTools.getTraceString()));
	// }
	// }
	//
	// /**
	// * Method description
	// *
	// * @see
	// */
	// final private static void logIn() {
	// JugglePreferences.log("In  :");
	// }
	//
	// /**
	// * Method description
	// *
	// * @see
	// */
	// @SuppressWarnings("unused") final private static void logOut() {
	// JugglePreferences.log("Out :");
	// }

	final private static byte[]							bytSlocalPreferenceA;

	final private static java.util.prefs.Preferences	objSpreferences;

	@SuppressWarnings("unused")
	final private static long							serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private static String[]						strSglobalPreferenceA;

	final private static String[]						strSlocalPreferenceA;

	static {

		// Construction :
		java.util.prefs.Preferences objLpreferences = null;

		try {
			objLpreferences = java.util.prefs.Preferences.systemNodeForPackage(fr.jugglemaster.engine.JuggleMasterPro.class);
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while initializing preferences");
		}
		objSpreferences = objLpreferences;

		// Preference values :
		bolSglobalPreferenceA = new boolean[Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER];
		bolSlocalPreferenceA = new boolean[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER];
		bytSglobalPreferenceA = new byte[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		bytSlocalPreferenceA = new byte[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];
		strSlocalPreferenceA = new String[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];
		strSglobalPreferenceA = new String[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER];

		// Local boolean default values :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			switch (bytLpreferenceIndex) {
				case Constants.bytS_BOOLEAN_LOCAL_COLORS:
				case Constants.bytS_BOOLEAN_LOCAL_STYLE:
				case Constants.bytS_BOOLEAN_LOCAL_LIGHT:
				case Constants.bytS_BOOLEAN_LOCAL_ERRORS:
				case Constants.bytS_BOOLEAN_LOCAL_WARNINGS:
				case Constants.bytS_BOOLEAN_LOCAL_INFO:
				case Constants.bytS_BOOLEAN_LOCAL_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER:
				case Constants.bytS_BOOLEAN_LOCAL_BALLS:
				case Constants.bytS_BOOLEAN_LOCAL_EDITION:
				case Constants.bytS_BOOLEAN_LOCAL_SPEED:
				case Constants.bytS_BOOLEAN_LOCAL_FLUIDITY:
					Preferences.bolSlocalPreferenceA[bytLpreferenceIndex] =
																			((Preferences.objSpreferences == null)
																													? true
																													: Preferences.objSpreferences.getBoolean(	Constants.strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																								true));
					break;

				case Constants.bytS_BOOLEAN_LOCAL_MIRROR:
				case Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS:
				case Constants.bytS_BOOLEAN_LOCAL_FX:
				case Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS:
				case Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS:
				case Constants.bytS_BOOLEAN_LOCAL_SITESWAP:
				case Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS:
				case Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE:
				case Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP:
				case Constants.bytS_BOOLEAN_LOCAL_DEFAULTS:
				case Constants.bytS_BOOLEAN_LOCAL_FLASH:
				case Constants.bytS_BOOLEAN_LOCAL_ROBOT:
				case Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS:
				case Constants.bytS_BOOLEAN_LOCAL_METRONOME:
				case Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL:
				case Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS:
					Preferences.bolSlocalPreferenceA[bytLpreferenceIndex] =
																			((Preferences.objSpreferences == null)
																													? false
																													: Preferences.objSpreferences.getBoolean(	Constants.strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																								false));
					break;
			}

			Preferences.saveLocalBooleanPreference(bytLpreferenceIndex, Preferences.bolSlocalPreferenceA[bytLpreferenceIndex], true);
		}

		// Global boolean default values :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			Preferences.bolSglobalPreferenceA[bytLpreferenceIndex] =
																		((Preferences.objSpreferences == null)
																												? bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER
																													&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_SKILL
																													&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_MARK
																												: Preferences.objSpreferences.getBoolean(	Constants.strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																							bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER
																																								&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_SKILL
																																								&& bytLpreferenceIndex != Constants.bytS_BOOLEAN_GLOBAL_MARK));
			Preferences.saveGlobalBooleanPreference(bytLpreferenceIndex, Preferences.bolSglobalPreferenceA[bytLpreferenceIndex], true);
		}

		// Global byte default values :
		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			byte bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
			switch (bytLpreferenceIndex) {
				case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
					bytLvalue = Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE;
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
					bytLvalue = Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MAXIMUM_VALUE;
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
					bytLvalue = Constants.bytS_BYTE_LOCAL_SKILL_BEGINNER;
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
					bytLvalue = Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE;
					break;
				case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
					bytLvalue = Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE;
					break;
				case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
					bytLvalue = Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE;
					break;
				case Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION:
					bytLvalue = Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE;
					break;
				default:
					bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
					break;
			}
			Preferences.bytSglobalPreferenceA[bytLpreferenceIndex] =
																		(Preferences.objSpreferences == null)
																												? bytLvalue
																												: (byte) Preferences.objSpreferences.getInt(Constants.strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																							bytLvalue);
			Preferences.saveGlobalBytePreference(bytLpreferenceIndex, Preferences.bytSglobalPreferenceA[bytLpreferenceIndex], true);
		}

		// Local byte default values :
		final byte[] bytLdefaultValueA = new byte[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];

		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS] = Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_STROBE] = Constants.bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_SKILL] = Constants.bytS_BYTE_LOCAL_SKILL_AMATEUR;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_MARK] = Constants.bytS_BYTE_LOCAL_MARK_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL] = Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY] = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT] = Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_SPEED] = Constants.bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED] = Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_FLUIDITY] = Constants.bytS_BYTE_LOCAL_FLUIDITY_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_HEIGHT] = Constants.bytS_BYTE_LOCAL_HEIGHT_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_DWELL] = Constants.bytS_BYTE_LOCAL_DWELL_DEFAULT_VALUE;
		bytLdefaultValueA[Constants.bytS_BYTE_LOCAL_DEFAULTS] = Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE;

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			Preferences.bytSlocalPreferenceA[bytLpreferenceIndex] =
																	((Preferences.objSpreferences == null)
																											? bytLdefaultValueA[bytLpreferenceIndex]
																											: (byte) Preferences.objSpreferences.getInt(Constants.strS_BYTE_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																						bytLdefaultValueA[bytLpreferenceIndex]));
			Preferences.saveLocalBytePreference(bytLpreferenceIndex, Preferences.bytSlocalPreferenceA[bytLpreferenceIndex], true);
		}

		// Global string default values :
		Preferences.strSglobalPreferenceA[Constants.bytS_STRING_GLOBAL_LANGUAGE] =
																					((Preferences.objSpreferences == null)
																															// ?
																															// Constants.strS_STRING_GLOBAL_LANGUAGE_DEFAULT
																															? Strings.strS_EMPTY
																															: Preferences.objSpreferences.get(	Constants.strS_STRING_GLOBAL_CONTROL_PREFERENCE_A[Constants.bytS_STRING_GLOBAL_LANGUAGE],
																																								// Constants.strS_STRING_GLOBAL_LANGUAGE_DEFAULT));
																																								Strings.strS_EMPTY));
		Preferences.saveLocalStringPreference(	Constants.bytS_STRING_GLOBAL_LANGUAGE,
												Preferences.strSlocalPreferenceA[Constants.bytS_STRING_GLOBAL_LANGUAGE],
												true);

		// Local string default values :
		final String[] strLdefaultValueA = new String[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];

		strLdefaultValueA[Constants.bytS_STRING_LOCAL_SITESWAP_DAY] = Constants.strS_STRING_LOCAL_SITESWAP_DAY_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT] = Constants.strS_STRING_LOCAL_SITESWAP_NIGHT_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] = Constants.strS_STRING_LOCAL_BACKGROUND_DAY_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT] = Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_JUGGLER_DAY] = Constants.strS_STRING_LOCAL_JUGGLER_DAY_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT] = Constants.strS_STRING_LOCAL_JUGGLER_NIGHT_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_PATTERN] = Strings.strS_EMPTY;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_COLORS] = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_REVERSE_COLORS] = Constants.strS_STRING_LOCAL_COLORS_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_STYLE] = Constants.strS_STRING_LOCAL_STYLE_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_SITESWAP] = Constants.strS_STRING_LOCAL_SITESWAP_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP] = Constants.strS_STRING_LOCAL_SITESWAP_DEFAULT;
		strLdefaultValueA[Constants.bytS_STRING_LOCAL_TOOLTIP] = null;

		for (byte bytLpreferenceIndex = 0; bytLpreferenceIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLpreferenceIndex) {
			Preferences.strSlocalPreferenceA[bytLpreferenceIndex] =
																	((Preferences.objSpreferences == null)
																											? strLdefaultValueA[bytLpreferenceIndex]
																											: Preferences.objSpreferences.get(	Constants.strS_STRING_LOCAL_CONTROL_PREFERENCE_A[bytLpreferenceIndex],
																																				strLdefaultValueA[bytLpreferenceIndex]));
			Preferences.saveLocalStringPreference(bytLpreferenceIndex, Preferences.strSlocalPreferenceA[bytLpreferenceIndex], true);
		}

		// Preferences.resetAllPreferences();
		Tools.out(Preferences.getInfo());
	}
}

/*
 * @(#)JugglePreferences.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
