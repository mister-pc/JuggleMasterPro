/*
 * @(#)ConsoleObject.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.pattern.io;

import java.text.SimpleDateFormat;
import java.util.Date;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ConsoleObject {

	/**
	 * Constructs
	 * 
	 * @param intPlanguageIndex
	 * @param bytPtype
	 * @param intPfileLineIndex
	 * @param intPfileLineCursorIndex
	 * @param bolPlocal
	 * @param strPfileLine
	 */
	public ConsoleObject(int intPlanguageIndex, boolean bolPlocal) {
		this(intPlanguageIndex, bolPlocal, null, null, Constants.bytS_UNCLASS_NO_VALUE, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ConsoleObject(int intPlanguageIndex, boolean bolPlocal, String strPfile) {
		this(intPlanguageIndex, bolPlocal, strPfile, null, Constants.bytS_UNCLASS_NO_VALUE, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ConsoleObject(int intPlanguageIndex, boolean bolPlocal, String strPfile, String strPfileLine, int intPfileLineIndex) {
		this(intPlanguageIndex, bolPlocal, strPfile, strPfileLine, intPfileLineIndex, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ConsoleObject(	int intPlanguageIndex,
							boolean bolPlocal,
							String strPfile,
							String strPfileLine,
							int intPfileLineIndex,
							int intPfileLineCursorIndex) {

		// TODO : ConsoleObject.strPfile n'est encore alimenté par rien
		this.lngGtimestamp = System.currentTimeMillis();

		this.intGlanguageIndex = intPlanguageIndex;
		this.bolGlocal = bolPlocal;
		this.strGfile = strPfile;
		this.strGfileLine = strPfileLine;
		this.intGfileLineIndex = intPfileLineIndex;
		this.intGfileLineCursorIndex = intPfileLineCursorIndex;
	}

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
				return ConsoleObject.bytS_ERROR_LEVEL;
			case Language.intS_CONSOLE_PARAMETER_COLORS_MALFORMED_VALUE:
			case Language.intS_CONSOLE_PARAMETER_DEPRECATION:
			case Language.intS_CONSOLE_PARAMETER_SINGLE_VALUE:
			case Language.intS_CONSOLE_SITESWAP_EMPTY:
			case Language.intS_CONSOLE_SITESWAP_WITHOUT_STYLE:
			case Language.intS_CONSOLE_STYLE_REDEFINITION:
			case Language.intS_CONSOLE_STYLE_WRONG_REDEFINITION:
			case Language.intS_CONSOLE_STYLE_WRONG_REFERENCE:
				return ConsoleObject.bytS_WARNING_LEVEL;
		}
		return ConsoleObject.bytS_INFO_LEVEL;
	}

	final public String getDate(ControlJFrame objPcontrolJFrame) {
		return (new SimpleDateFormat(objPcontrolJFrame.getLanguageString(this.intGlanguageIndex))).format(new Date(this.lngGtimestamp));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return Index of the file line which encountered the problem
	 */
	final public int getFileLineIndex() {
		return this.intGfileLineIndex;
	}

	final public byte getLevel() {
		return ConsoleObject.getLevel(this.intGlanguageIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPfatal
	 *            If the problem encountered is an error or just a warning
	 * @param bolPlocal
	 * @param objPlanguage
	 * @return
	 */
	final public String toString(boolean bolPlocal, Language objPlanguage) {
		if (bolPlocal && !this.bolGlocal) {
			return Strings.strS_EMPTY;
		}

		// Line index & line string :
		final String strLheader =
									Strings.doConcat(	objPlanguage.getPropertyValueString(Language.intS_LABEL_LINE_INDEX),
														' ',
														this.intGfileLineIndex,
														" : ");

		// Line cursor :
		final int intLheaderStringLength = strLheader.length();
		final StringBuilder objLlineCursorStringBuilder = new StringBuilder(intLheaderStringLength + this.intGfileLineCursorIndex + 4);

		if (this.intGfileLineCursorIndex >= 0) {
			for (int intLcursorIndex = 0; intLcursorIndex < intLheaderStringLength + this.intGfileLineCursorIndex; ++intLcursorIndex) {
				objLlineCursorStringBuilder.append('-');
			}

			objLlineCursorStringBuilder.append(Strings.doConcat('^', Strings.strS_LINE_SEPARATOR));
		}

		// Get error string :
		int intLlevelLanguageIndex = Language.intS_LABEL_INFO;
		switch (this.getLevel()) {
			case ConsoleObject.bytS_ERROR_LEVEL:
				intLlevelLanguageIndex = Language.intS_LABEL_ERROR;
				break;
			case ConsoleObject.bytS_WARNING_LEVEL:
				intLlevelLanguageIndex = Language.intS_LABEL_WARNING;
				break;
			default:
				intLlevelLanguageIndex = Language.intS_LABEL_INFO;
		}
		return Strings.doConcat(strLheader,
								this.strGfileLine,
								Strings.strS_LINE_SEPARATOR,
								objLlineCursorStringBuilder,
								objPlanguage.getPropertyValueString(intLlevelLanguageIndex),
								" : ",
								objPlanguage.getPropertyValueString(this.intGlanguageIndex),
								'.',
								Strings.strS_LINE_SEPARATOR);
	}

	private final boolean		bolGlocal;

	private final int			intGfileLineCursorIndex;

	private final int			intGfileLineIndex;

	private final int			intGlanguageIndex;

	private final long			lngGtimestamp;

	private final String		strGfile;

	private final String		strGfileLine;

	final public static byte	bytS_ERROR_LEVEL	= 2;

	final public static byte	bytS_INFO_LEVEL		= 0;
	final public static byte	bytS_WARNING_LEVEL	= 1;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}

/*
 * @(#)ConsoleObject.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
