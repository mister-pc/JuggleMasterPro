package jugglemasterpro.pattern.io;

import java.util.ArrayList;

import jugglemasterpro.pattern.Pattern;
import jugglemasterpro.pattern.PatternsManager;
import jugglemasterpro.pattern.Siteswap;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

abstract public class PatternsFileParser {

	public PatternsFileParser(PatternsFilesManager objPpatternsFilesManager) {
		this.objGpatternsFilesManager = objPpatternsFilesManager;
		this.objGpatternsManager = this.objGpatternsFilesManager.getPatternsManager();
		this.objGpatternsFileParameterParser = new FileParameterParser(this.objGpatternsManager);
		this.objGpatternsFileStyleParser = new PatternsFileStyleParser(this, this.objGpatternsManager);
		this.objGconsoleObjectAL = new ArrayList<ConsoleObject>();
	}

	final protected static Byte getByteValue(String strPvalue, int intLmin, int intLmax, boolean bolPstrictInterval, boolean bolPuserValueNotAllowed) {

		final String strLvalue = strPvalue.trim();
		if (strLvalue.equalsIgnoreCase(Constants.strS_STRING_LOCAL_USER)) {
			if (bolPuserValueNotAllowed) {
				return Constants.bytS_UNCLASS_NO_VALUE;
			}
			return null;
		}
		byte bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
		try {
			bytLvalue = Byte.parseByte(strLvalue);
			if (bytLvalue < intLmin || intLmax < bytLvalue) {
				if (bolPstrictInterval) {
					bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
				} else {
					bytLvalue = (byte) Math.max(intLmin, Math.min(bytLvalue, intLmax));
				}
			}
		} catch (final Throwable objPthrowable) {
			bytLvalue = Constants.bytS_UNCLASS_NO_VALUE;
		}
		return bytLvalue;
	}

	final protected boolean deprecatedDoParseShortParameter(char chrPparameterLetter, String objPvalue) {

		final Object objLvalue = Tools.getParameterValueType(objPvalue);
		switch (chrPparameterLetter) {

			// Height :
			case '^':
				if (objLvalue instanceof Byte) {
					this.objGpatternsManager.setByte(	Constants.bytS_BYTE_LOCAL_HEIGHT,
														(byte) Math.min(Math.max(	((Byte) objLvalue).byteValue(),
																					Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE),
																		Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE));
					return true;
				} else if (objLvalue instanceof Float) {
					this.objGpatternsManager.setByte(	Constants.bytS_BYTE_LOCAL_HEIGHT,
														(byte) Math.min(Math.max(	(byte) (((Float) objLvalue).floatValue()),
																					Constants.bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE),
																		Constants.bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE));
					return true;
				}
				return false;

				// Skill :
			case '&':
				if (objLvalue instanceof Byte || objLvalue instanceof String) {
					final byte bytLvalue = FileParameterParser.getSkillValue(objPvalue);
					if (bytLvalue != Constants.bytS_UNCLASS_NO_VALUE) {
						this.objGpatternsManager.setByte(Constants.bytS_BYTE_LOCAL_SKILL, bytLvalue);
						return true;
					}
				}
				return false;

				// Dwell :
			case '@':
				if (objLvalue instanceof Byte) {
					this.objGpatternsManager.setByte(	Constants.bytS_BYTE_LOCAL_DWELL,
														(byte) Math.min(Math.max(	((Byte) objLvalue).byteValue(),
																					Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE),
																		Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE));
					return true;
				} else if (objLvalue instanceof Float) {
					this.objGpatternsManager.setByte(	Constants.bytS_BYTE_LOCAL_DWELL,
														(byte) Math.min(Math.max(	(byte) (((Float) objLvalue).floatValue()),
																					Constants.bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE),
																		Constants.bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE));
					return true;
				}
				return false;
		}
		return false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPlanguageIndex
	 * @param bytPtype
	 * @param objPpatternsBufferedReader
	 * @param intPfileLineCursorIndex
	 */
	final public void doAddToConsole(int intPlanguageIndex, PatternsFile objPpatternsFile, int intPfileLineCursorIndex) {
		this.doAddToConsole(intPlanguageIndex,
							objPpatternsFile.getReferenceString(),
							objPpatternsFile.getLineString(),
							objPpatternsFile.getLineIndex(),
							intPfileLineCursorIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPlanguageIndex
	 * @param bytPtype
	 * @param strPfileLine
	 * @param intPfileLineIndex
	 * @param intPfileLineCursorIndex
	 */
	final public void doAddToConsole(	int intPlanguageIndex,
										String strPfileReference,
										String strPfileLine,
										int intPfileLineIndex,
										int intPfileLineCursorIndex) {

		// Appending object to console :
		final byte bytLlevel = ConsoleObject.getLevel(intPlanguageIndex);
		byte bytLlevelControl = Constants.bytS_UNCLASS_NO_VALUE;
		switch (bytLlevel) {
			case ConsoleObject.bytS_WARNING_LEVEL:
				bytLlevelControl = Constants.bytS_BOOLEAN_LOCAL_WARNINGS;
				break;
			case ConsoleObject.bytS_ERROR_LEVEL:
				bytLlevelControl = Constants.bytS_BOOLEAN_LOCAL_ERRORS;
				break;
			case ConsoleObject.bytS_INFO_LEVEL:
				bytLlevelControl = Constants.bytS_BOOLEAN_LOCAL_INFO;
				break;
		}
		final ConsoleObject objLconsoleObject =
												new ConsoleObject(	intPlanguageIndex,
																	this.objGpatternsManager.bolGbooleanIsLocalA[bytLlevelControl],
																	strPfileReference,
																	strPfileLine,
																	intPfileLineIndex,
																	intPfileLineCursorIndex);
		this.objGconsoleObjectAL.add(objLconsoleObject);
		// if (bytLlevel == ConsoleObject.bytS_WARNING_LEVEL) {
		// this.objGpatternsFilesManager.objGwarningConsoleObjectAL.add(objLconsoleObject);
		// ++this.objGpatternsFilesManager.intGglobalWarningsNumber;
		// }
		// if (bytLlevel == ConsoleObject.bytS_ERROR_LEVEL) {
		// this.objGpatternsFilesManager.objGerrorConsoleObjectAL.add(objLconsoleObject);
		// ++this.objGpatternsFilesManager.intGglobalFatalErrorsNumber;
		// }
		//
		// if ((bytLlevel == ConsoleObject.bytS_WARNING_LEVEL || bytLlevel == ConsoleObject.bytS_ERROR_LEVEL)
		// && this.objGpatternsManager.bolGbooleanIsLocalA[bytLlevel == ConsoleObject.bytS_ERROR_LEVEL ? Constants.bytS_BOOLEAN_LOCAL_ERRORS
		// : Constants.bytS_BOOLEAN_LOCAL_WARNINGS]) {
		// if (bytLlevel == ConsoleObject.bytS_ERROR_LEVEL) {
		// ++this.objGpatternsFilesManager.intGlocalFatalErrorsNumber;
		// } else {
		// ++this.objGpatternsFilesManager.intGlocalWarningsNumber;
		// }
		// }
	}

	abstract ArrayList<Object> doParsePatternsFile(	PatternsFile objPfileBufferedReader,
													boolean bolPnewLists,
													boolean bolPsiteswaps,
													boolean bolPstyles,
													String strPpatternName,
													boolean bolPonlyThisPattern,
													int intPstartPatternOccurrence);

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectAL
	 * @param objPpatternsBufferedReader
	 * @param strPpatternName
	 * @param bolPnoSiteswapStyleErrorEnabled
	 * @param bolPstyles
	 * @return
	 */
	final protected int doParseSiteswap(ArrayList<Object> objPobjectAL,
										PatternsFile objPpatternsFile,
										String strPpatternName,
										boolean bolPonlyThisPattern,
										int intPstartPatternOccurrence,
										int intPcurrentPatternOccurrence,
										boolean bolPnoSiteswapStyleErrorEnabled,
										boolean bolPstyles) {

		final String strLuncommentUntabFileLine = Strings.uncommentUntab(objPpatternsFile.getLineString());
		String strLsiteswap = null, strLpatternName = null;
		final boolean bolLquotes = (strLuncommentUntabFileLine.charAt(0) == '"');
		int intLsiteswapSpaceIndex = strLuncommentUntabFileLine.indexOf(' ');

		if (intLsiteswapSpaceIndex == -1) {
			intLsiteswapSpaceIndex = strLuncommentUntabFileLine.length();
		}

		int intLsiteswapEndIndex = (bolLquotes ? strLuncommentUntabFileLine.indexOf('"', 1) : intLsiteswapSpaceIndex);

		if (intLsiteswapEndIndex == -1) {
			intLsiteswapEndIndex = strLuncommentUntabFileLine.length();
		}

		try {
			strLsiteswap = strLuncommentUntabFileLine.substring(bolLquotes ? 1 : 0, intLsiteswapEndIndex);
			strLpatternName = strLuncommentUntabFileLine.substring(intLsiteswapEndIndex + (bolLquotes ? 1 : 0)).trim();
		} catch (final Throwable objPthrowable) {
			// In case of a corrupted (or binary) file :
			Tools.err("Error while parsing pattern file");
			return Constants.bytS_UNCLASS_NO_VALUE;
		}

		if (strLpatternName.equals(Strings.strS_EMPTY)) {
			strLpatternName = strLsiteswap.trim();
		}

		// Testing pattern name :
		if (bolPonlyThisPattern && strPpatternName != null
			&& !Strings.getUpperCaseString(strPpatternName).equals(Strings.getUpperCaseString(strLpatternName))) {
			return Constants.bytS_UNCLASS_NO_VALUE;
		}

		// Analyzing siteswap :
		final Siteswap objLsiteswap = new Siteswap(strLsiteswap);
		final int intLsiteswapErrorIndex =
											objLsiteswap.intGerrorCursorIndex != Constants.bytS_UNCLASS_NO_VALUE
																												? (bolLquotes ? 1 : 0)
																													+ objLsiteswap.intGerrorCursorIndex
																												: Constants.bytS_UNCLASS_NO_VALUE;
		switch (objLsiteswap.bytGstatus) {
			case Constants.bytS_STATE_SITESWAP_FORBIDDEN_CHAR:
				this.doAddToConsole(Language.intS_CONSOLE_SITESWAP_FORBIDDEN_CHAR, objPpatternsFile, intLsiteswapErrorIndex);
				return Constants.bytS_UNCLASS_NO_VALUE;
			case Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR:
				this.doAddToConsole(Language.intS_CONSOLE_SITESWAP_UNEXPECTED_CHAR, objPpatternsFile, intLsiteswapErrorIndex);
				break;
			case Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE:
				this.doAddToConsole(Language.intS_CONSOLE_SITESWAP_ODD_THROW_VALUE, objPpatternsFile, intLsiteswapErrorIndex);
				break;
			case Constants.bytS_STATE_SITESWAP_EMPTY:
				this.doAddToConsole(Language.intS_CONSOLE_SITESWAP_EMPTY, objPpatternsFile, intLsiteswapErrorIndex);
				break;
			case Constants.bytS_STATE_SITESWAP_UNPLAYABLE:
				this.doAddToConsole(Language.intS_CONSOLE_SITESWAP_UNPLAYABLE_SEQUENCE, objPpatternsFile, intLsiteswapErrorIndex);
				break;
			case Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER:
				this.doAddToConsole(Language.intS_CONSOLE_SITESWAP_UNKNOWN_BALLS_NUMBER, objPpatternsFile, intLsiteswapErrorIndex);
				break;
			case Constants.bytS_STATE_SITESWAP_PLAYABLE:
				break;
		}

		if (bolPnoSiteswapStyleErrorEnabled) {
			this.doAddToConsole(Language.intS_CONSOLE_SITESWAP_WITHOUT_STYLE, objPpatternsFile, Constants.bytS_UNCLASS_NO_VALUE);
		}

		this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_PATTERN, bolPstyles ? strLpatternName : strLsiteswap);
		this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_SITESWAP, strLsiteswap);
		this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP, objLsiteswap.getReverseSiteswapString());
		this.objGpatternsManager.setString(	Constants.bytS_STRING_LOCAL_REVERSE_COLORS,
											objLsiteswap.getReverseColorsString(this.objGpatternsManager.getStringValue(Constants.bytS_STRING_LOCAL_COLORS,
																														Constants.bytS_UNCLASS_INITIAL)));
		objPobjectAL.add(new Pattern(	this.objGpatternsManager,
										objLsiteswap.intGballsNumber,
										objLsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE,
										this.objGpatternsManager.bolGglobalValueAA,
										this.objGpatternsManager.bytGglobalValueAA,
										this.objGpatternsManager.strGglobalAA));
		++this.objGpatternsManager.intGpatternsNumber;

		// Return value :
		if (strPpatternName != null && Strings.getUpperCaseString(strLpatternName).equals(Strings.getUpperCaseString(strPpatternName))
			&& intPcurrentPatternOccurrence > 1) {
			this.objGpatternsFilesManager.intGstartObjectIndex = objPobjectAL.size() - 1;
			this.objGpatternsFilesManager.bolGstartObject = false;
			return intPcurrentPatternOccurrence - 1;
		}

		if (this.objGpatternsFilesManager.bolGstartObject
			&& (Tools.isEmpty(strPpatternName) || intPstartPatternOccurrence == intPcurrentPatternOccurrence)) {
			this.objGpatternsFilesManager.intGstartObjectIndex = objPobjectAL.size() - 1;
			this.objGpatternsFilesManager.bolGstartObject = false;
		}
		return 0;
	}

	// final private byte bytGfileExtension;

	final protected boolean setByteValue(byte bytPcontrolType, Byte bytPvalue) {
		if (bytPvalue != Constants.bytS_UNCLASS_NO_VALUE) {
			this.objGpatternsManager.setByte(bytPcontrolType, bytPvalue);
			if (bytPvalue != null) {
				this.objGpatternsManager.bolGbyteIsFileDefinedA[bytPcontrolType] = true;
			}
			return true;
		}
		return false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPpatternsBufferedReader
	 * @param bolPnewLists
	 * @param bolPstyles
	 * @param bolPsiteswaps
	 * @return
	 */
	protected byte								bytGpatternsFileExtension;
	final protected ArrayList<ConsoleObject>	objGconsoleObjectAL;
	final protected FileParameterParser			objGpatternsFileParameterParser;
	final protected PatternsFilesManager		objGpatternsFilesManager;
	final protected PatternsFileStyleParser		objGpatternsFileStyleParser;

	final protected PatternsManager				objGpatternsManager;

	@SuppressWarnings("unused")
	final private static long					serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
