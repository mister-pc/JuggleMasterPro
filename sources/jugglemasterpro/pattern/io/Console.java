/*
 * @(#)ConsoleObject.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.pattern.io;

import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Console {

	final public static byte	bytS_ERROR_LEVEL	= 1;
	final public static byte	bytS_INFO_LEVEL		= 0;
	final public static byte	bytS_WARNING_LEVEL	= 0;
	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final public static byte getLevel(int intPlanguageIndex) {
		switch (intPlanguageIndex) {
			case Language.intS_CONSOLE_PARAMETER_COLORS_UNKNOWN_VALUE:
			case Language.intS_CONSOLE_PARAMETER_DWELL_UNKNOWN_VALUE:
			case Language.intS_CONSOLE_PARAMETER_HEIGHT_UNKNOWN_VALUE:
			case Language.intS_CONSOLE_NO_SENSE_ERROR:
			case Language.intS_CONSOLE_STYLE_NO_NAME:
			case Language.intS_CONSOLE_PARAMETER_EXPECTED_EQUALS_SYMBOL:
			case Language.intS_CONSOLE_PARAMETER_EXPECTED_NAME:
			case Language.intS_CONSOLE_PARAMETER_EXPECTED_NUMBER_SYMBOL:
			case Language.intS_CONSOLE_PARAMETER_INVALID_VALUE:
			case Language.intS_CONSOLE_PARAMETER_MISSING_VALUE:
			case Language.intS_CONSOLE_PARAMETER_MULTIPLE_VALUES:
			case Language.intS_CONSOLE_PARAMETER_UNKNOWN_NAME:
			case Language.intS_CONSOLE_PARAMETER_UNEXPECTED_CHARACTER:
			case Language.intS_CONSOLE_SITESWAP_FORBIDDEN_CHAR:
			case Language.intS_CONSOLE_SITESWAP_INVALID_SYNTAX:
			case Language.intS_CONSOLE_SITESWAP_ODD_THROW_VALUE:
			case Language.intS_CONSOLE_SITESWAP_UNEXPECTED_CHAR:
			case Language.intS_CONSOLE_SITESWAP_UNKNOWN_BALLS_NUMBER:
			case Language.intS_CONSOLE_SITESWAP_UNPLAYABLE_SEQUENCE:
			case Language.intS_CONSOLE_PARAMETER_SKILL_UNKNOWN_VALUE:
			case Language.intS_CONSOLE_STYLE_WRONG_DEFINITION:
			case Language.intS_CONSOLE_STYLE_DEFINITION_MISSING_NAME:
			case Language.intS_CONSOLE_STYLE_EMPTY_DUPLICATION:
			case Language.intS_CONSOLE_STYLE_MISSING_DEFINITION:
			case Language.intS_CONSOLE_STYLE_ODD_LINES_NUMBER_DUPLICATION:
			case Language.intS_CONSOLE_STYLE_VALUES_DEFINITION:
				return Console.bytS_ERROR_LEVEL;
			case Language.intS_CONSOLE_PARAMETER_COLORS_MALFORMED_VALUE:
			case Language.intS_CONSOLE_PARAMETER_DEPRECATION:
			case Language.intS_CONSOLE_PARAMETER_SINGLE_VALUE:
			case Language.intS_CONSOLE_SITESWAP_EMPTY:
			case Language.intS_CONSOLE_SITESWAP_WITHOUT_STYLE:
			case Language.intS_CONSOLE_STYLE_REDEFINITION:
			case Language.intS_CONSOLE_STYLE_WRONG_REDEFINITION:
			case Language.intS_CONSOLE_STYLE_WRONG_REFERENCE:
				return Console.bytS_WARNING_LEVEL;
		}
		return Console.bytS_INFO_LEVEL;
	}

	Console() {}
}
