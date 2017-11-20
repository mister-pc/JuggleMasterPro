package fr.jugglemaster.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

final public class Strings {

	final public static boolean areNullEqual(Object objPfirstObject, Object objPsecondObject) {
		return Strings.toString(objPfirstObject).equals(Strings.toString(objPsecondObject));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 * @return
	 */
	final public static String doConcat(Object... objPobjectA) {
		final int intLarraySize = objPobjectA.length;

		if (intLarraySize > 0) {

			// Getting the tokens and the final length :
			final String[] strLA = new String[intLarraySize];
			int intLstringBuilderLength = 0;

			for (int intLobjectIndex = 0; intLobjectIndex < intLarraySize; ++intLobjectIndex) {
				if (objPobjectA[intLobjectIndex] != null) {
					if (objPobjectA[intLobjectIndex].getClass().isArray()) {
						final int intLlength = Array.getLength(objPobjectA[intLobjectIndex]);
						final String[] strLsubA = new String[intLlength];
						int intLsubStringBuilderLength = 2;
						for (int intLindex = 0; intLindex < intLlength; ++intLindex) {
							strLsubA[intLindex] = Strings.doConcat(Array.get(objPobjectA[intLobjectIndex], intLindex));
							intLsubStringBuilderLength += strLsubA[intLindex].length() + 2;
						}
						final StringBuilder objLsubStringBuilder = new StringBuilder(intLsubStringBuilderLength);
						if (intLlength > 1) {
							objLsubStringBuilder.append('(');
							for (int intLindex = 0; intLindex < intLlength; ++intLindex) {
								if (intLindex > 0) {
									objLsubStringBuilder.append(", ");
								}
								objLsubStringBuilder.append(intLindex);
								objLsubStringBuilder.append(" => ");
								objLsubStringBuilder.append(strLsubA[intLindex]);
							}
							objLsubStringBuilder.append(')');
						} else if (intLlength == 1) {
							objLsubStringBuilder.append(strLsubA[0]);
						}
						strLA[intLobjectIndex] = objLsubStringBuilder.toString();
					} else {
						if (objPobjectA[intLobjectIndex] instanceof Boolean) {
							strLA[intLobjectIndex] = (Boolean) objPobjectA[intLobjectIndex] ? "true " : "false";
						} else if (objPobjectA[intLobjectIndex] instanceof Character
									&& ((Character) objPobjectA[intLobjectIndex]).charValue() == Strings.chrS_UNCLASS_NULL_CHAR) {
							strLA[intLobjectIndex] = Strings.strS_EMPTY;
						} else {
							try {
								strLA[intLobjectIndex] = objPobjectA[intLobjectIndex].toString();
							} catch (final Throwable objPthrowable) {
								strLA[intLobjectIndex] = null;
							}
						}
					}
					if (strLA[intLobjectIndex] != null) {
						intLstringBuilderLength += strLA[intLobjectIndex].length();
					}
				} else {
					strLA[intLobjectIndex] = null;
				}
			}

			// Building the final string :
			final StringBuilder objPstringBuilder = new StringBuilder(intLstringBuilderLength);

			for (int intLobjectIndex = 0; intLobjectIndex < intLarraySize; ++intLobjectIndex) {
				if (strLA[intLobjectIndex] != null) {
					objPstringBuilder.append(strLA[intLobjectIndex]);
				}
			}

			return objPstringBuilder.toString();
		}
		return Strings.strS_EMPTY;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param chrP
	 * @param intPcharsNumber
	 * @return
	 */
	final public static String getCharsString(char chrP, int intPcharsNumber) {
		final StringBuilder objLreturnedSpacesStringBuilder = new StringBuilder(intPcharsNumber);

		for (int intLspaceIndex = 0; intLspaceIndex < intPcharsNumber; ++intLspaceIndex) {
			objLreturnedSpacesStringBuilder.append(chrP);
		}
		return objLreturnedSpacesStringBuilder.toString();
	}

	final public static String getEnlargedString(String objPstring) {
		return Strings.doConcat(' ', objPstring, ' ');
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPstringObject
	 * @param intPlength
	 * @return
	 */
	final public static String getFixedLengthString(Object objPstringObject, int intPlength) {
		return Strings.getFixedLengthString(objPstringObject, intPlength, false, true, ' ');
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPstringObject
	 * @param intPlength
	 * @param bolPrightAligned
	 * @return
	 */
	final public static String getFixedLengthString(Object objPstringObject, int intPlength, boolean bolPrightAligned) {
		return Strings.getFixedLengthString(objPstringObject, intPlength, bolPrightAligned, true, ' ');
	}

	final public static String getFixedLengthString(Object objPstringObject, int intPlength, boolean bolPrightAligned, boolean bolPtruncate) {
		return Strings.getFixedLengthString(objPstringObject, intPlength, bolPrightAligned, bolPtruncate, ' ');
	}

	final public static String getFixedLengthString(Object objPstringObject,
													int intPlength,
													boolean bolPrightAligned,
													boolean bolPtruncate,
													char chrPfillWith) {
		final String strL = objPstringObject.toString();
		final int intLlength = strL.length();

		if (intLlength < intPlength) {
			return bolPrightAligned ? Strings.doConcat(Strings.getCharsString(chrPfillWith, intPlength - intLlength), strL)
									: Strings.doConcat(strL, Strings.getCharsString(chrPfillWith, intPlength - intLlength));
		}
		return bolPtruncate ? strL.substring(0, intPlength) : strL;
	}

	final public static int getIdenticalCharsNumber(String strPfirst, String strPsecond) {
		return Strings.getIdenticalCharsNumber(strPfirst, strPsecond, true);
	}

	final public static int getIdenticalCharsNumber(String strPfirst, String strPsecond, boolean bolPleftToRightDirection) {
		if (Tools.isEmpty(strPfirst) || Tools.isEmpty(strPsecond)) {
			return 0;
		}
		final char[] chrLfirstCharA = strPfirst.toCharArray();
		final char[] chrLsecondCharA = strPsecond.toCharArray();
		int intLidenticalCharsNumber = 0;
		while (intLidenticalCharsNumber < chrLfirstCharA.length && intLidenticalCharsNumber < chrLsecondCharA.length) {
			if (chrLfirstCharA[bolPleftToRightDirection ? intLidenticalCharsNumber : chrLfirstCharA.length - intLidenticalCharsNumber - 1] != chrLsecondCharA[bolPleftToRightDirection
																																														? intLidenticalCharsNumber
																																														: chrLsecondCharA.length
																																															- intLidenticalCharsNumber
																																															- 1]) {
				return intLidenticalCharsNumber;
			}
			++intLidenticalCharsNumber;
		}
		return intLidenticalCharsNumber;
	}

	final private static String getLineSeparatorString() {
		String strLlineSeparator = "\n";
		try {
			strLlineSeparator = System.getProperty("line.separator", "\n");
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while retrieving separator system property");
			strLlineSeparator = "\n";
		}
		return strLlineSeparator;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String getRightDotTrimmedString(String strP) {
		final char[] chrLstringA = strP.toCharArray();
		int intLlastDotIndex = chrLstringA.length - 1;

		while (intLlastDotIndex >= 0 && chrLstringA[intLlastDotIndex] != '.') {
			--intLlastDotIndex;
		}

		return intLlastDotIndex < 0 ? strP : strP.substring(0, intLlastDotIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String getRightTrimmedString(String strP) {
		final char[] chrLstringA = strP.toCharArray();
		int intLlastSpaceIndex = chrLstringA.length;

		while ((intLlastSpaceIndex > 0) && ((chrLstringA[intLlastSpaceIndex - 1] == ' ') || (chrLstringA[intLlastSpaceIndex - 1] == '\t'))) {
			--intLlastSpaceIndex;
		}

		return strP.substring(0, intLlastSpaceIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPspacesNumber
	 * @return
	 */
	final public static String getSpacesString(int intPspacesNumber) {
		return Strings.getCharsString(' ', intPspacesNumber);
	}

	final public static ArrayList<Object> getTokens(Object objPobject) {
		return Strings.getTokens(objPobject, Constants.bytS_UNCLASS_NO_VALUE, false, ' ');
	}

	final public static ArrayList<Object> getTokens(Object objPobject, boolean bolPindexes) {
		return Strings.getTokens(objPobject, Constants.bytS_UNCLASS_NO_VALUE, bolPindexes, ' ');
	}

	final public static ArrayList<Object> getTokens(Object objPobject, boolean bolPindexes, char chrPseparator) {
		return Strings.getTokens(objPobject, Constants.bytS_UNCLASS_NO_VALUE, bolPindexes, chrPseparator);
	}

	final public static ArrayList<Object> getTokens(Object objPobject, char chrPseparator) {
		return Strings.getTokens(objPobject, Constants.bytS_UNCLASS_NO_VALUE, false, chrPseparator);
	}

	final public static ArrayList<Object> getTokens(Object objPobject, int intPpreferredTokensNumber) {
		return Strings.getTokens(objPobject, intPpreferredTokensNumber, false, ' ');
	}

	final public static ArrayList<Object> getTokens(Object objPobject, int intPpreferredTokensNumber, boolean bolPindexes) {
		return Strings.getTokens(objPobject, intPpreferredTokensNumber, bolPindexes, ' ');
	}

	/**
	 * Returns the array of tokens contained in objPobject (usually of String type),
	 * with the last token formed by all the last ones if the total number of tokens should be
	 * greater than intPpreferredTokensNumber (if intPpreferredTokensNumber > 0).
	 * If bolPindexes, each token is followed by its position index in objPobject.
	 * 
	 * @param objPobject
	 * @param intPpreferredTokensNumber
	 * @param bolPindexes
	 * @return
	 */
	final public static ArrayList<Object> getTokens(Object objPobject, int intPpreferredTokensNumber, boolean bolPindexes, char chrPseparator) {
		final char[] chrLstringA = objPobject.toString().toCharArray();
		final StringBuilder objLtokenStringBuilder = new StringBuilder(chrLstringA.length);
		final ArrayList<Object> objLtokenStringAndStartIndexIntegerAL = new ArrayList<Object>();
		boolean bolLsingleQuotes = false;
		boolean bolLdoubleQuotes = false;
		int intLtokenStartIndex = 0;

		for (int intLstringIndex = 0; intLstringIndex < chrLstringA.length; ++intLstringIndex) {

			// Separator :
			// ////////////
			if (chrLstringA[intLstringIndex] == chrPseparator) {
				// If the token is quoted :
				if (bolLsingleQuotes || bolLdoubleQuotes) {
					if (objLtokenStringBuilder.length() == 0) {
						intLtokenStartIndex = intLstringIndex;
					}
					objLtokenStringBuilder.append(chrLstringA[intLstringIndex]);
					continue;
				}

				// If the space looks like ending the token :
				if (objLtokenStringBuilder.length() > 0) {
					if (intPpreferredTokensNumber <= 0
						|| objLtokenStringAndStartIndexIntegerAL.size() / (bolPindexes ? 2 : 1) < intPpreferredTokensNumber - 1) {
						objLtokenStringAndStartIndexIntegerAL.add(objLtokenStringBuilder.toString());
						if (bolPindexes) {
							objLtokenStringAndStartIndexIntegerAL.add(intLtokenStartIndex);
						}
						objLtokenStringBuilder.setLength(0);
					} else {
						objLtokenStringBuilder.append(chrLstringA[intLstringIndex]);
					}
				}
				continue;
			}

			// Quote :
			// ////////
			if (chrLstringA[intLstringIndex] == '\'' || chrLstringA[intLstringIndex] == '"') {
				// If the quote looks like ending a token :
				if ((chrLstringA[intLstringIndex] == '"') ? bolLdoubleQuotes : bolLsingleQuotes) {
					if ((intLstringIndex == chrLstringA.length - 1) || (chrLstringA[intLstringIndex + 1] == chrPseparator)) {
						objLtokenStringAndStartIndexIntegerAL.add(objLtokenStringBuilder.toString());
						if (bolPindexes) {
							objLtokenStringAndStartIndexIntegerAL.add(intLtokenStartIndex);
						}
						objLtokenStringBuilder.setLength(0);
						bolLsingleQuotes = bolLdoubleQuotes = false;
					} else {
						if (objLtokenStringBuilder.length() == 0) {
							intLtokenStartIndex = intLstringIndex;
						}
						objLtokenStringBuilder.append(chrLstringA[intLstringIndex]);
					}
					continue;
				}

				// If the quote is inside an already other-quoted token :
				if ((chrLstringA[intLstringIndex] == '"') ? bolLsingleQuotes : bolLdoubleQuotes) {
					if (objLtokenStringBuilder.length() == 0) {
						intLtokenStartIndex = intLstringIndex;
					}

					objLtokenStringBuilder.append(chrLstringA[intLstringIndex]);

					continue;
				}

				// If the quote starts a new token :
				if (objLtokenStringBuilder.length() == 0) {
					if (chrLstringA[intLstringIndex] == '"') {
						bolLdoubleQuotes = true;
					} else {
						bolLsingleQuotes = true;
					}
					intLtokenStartIndex = intLstringIndex;
					continue;
				}

				// The quote belongs to the token :
				if (objLtokenStringBuilder.length() == 0) {
					intLtokenStartIndex = intLstringIndex;
				}
				objLtokenStringBuilder.append(chrLstringA[intLstringIndex]);
				continue;
			}

			// Other char :
			if (objLtokenStringBuilder.length() == 0) {
				intLtokenStartIndex = intLstringIndex;
			}
			objLtokenStringBuilder.append(chrLstringA[intLstringIndex]);
			continue;
		}

		// Treating of the last token :
		if (objLtokenStringBuilder.length() > 0) {
			objLtokenStringAndStartIndexIntegerAL.add(objLtokenStringBuilder.toString());
			if (bolPindexes) {
				objLtokenStringAndStartIndexIntegerAL.add(intLtokenStartIndex);
			}
		}

		// Adjusting the number of tokens (if less than intPpreferredTokensNumber) :
		if (intPpreferredTokensNumber > 0) {
			while (objLtokenStringAndStartIndexIntegerAL.size() / (bolPindexes ? 2 : 1) < intPpreferredTokensNumber) {
				objLtokenStringAndStartIndexIntegerAL.add(Strings.strS_EMPTY);
				if (bolPindexes) {
					objLtokenStringAndStartIndexIntegerAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);
				}
			}
		}
		return objLtokenStringAndStartIndexIntegerAL;
	}

	final public static ArrayList<Object> getTokens(Object objPobject, int intPpreferredTokensNumber, char chrPseparator) {
		return Strings.getTokens(objPobject, intPpreferredTokensNumber, false, chrPseparator);
	}

	final public static String getTrimmedNullString(Object objPobject) {

		if (objPobject == null) {
			return null;
		}
		String strL = objPobject.toString();
		if (strL != null) {
			strL = Strings.undoubleSpaceTrim(strL);
			if (strL.length() == 0) {
				strL = null;
			}
		}

		return strL;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String getTrimmedString(String strP) {
		if (strP != null) {

			final char[] chrLstringA = strP.toCharArray();
			int intLlastSpaceIndex = chrLstringA.length;
			int intLfirstCharIndex = 0;

			while ((intLlastSpaceIndex > 0) && ((chrLstringA[intLlastSpaceIndex - 1] == ' ') || (chrLstringA[intLlastSpaceIndex - 1] == '\t'))) {
				--intLlastSpaceIndex;
			}
			while (intLfirstCharIndex < chrLstringA.length && (chrLstringA[intLfirstCharIndex] == ' ' || chrLstringA[intLfirstCharIndex] == '\t')) {
				++intLfirstCharIndex;
			}

			return intLfirstCharIndex < intLlastSpaceIndex ? strP.substring(intLfirstCharIndex, intLlastSpaceIndex - intLfirstCharIndex)
															: Strings.strS_EMPTY;
		}
		return null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param chrP
	 * @return
	 */
	final public static char getUpperCaseChar(char chrP) {
		char chrL = Character.toUpperCase(chrP);
		switch (chrL) {
			case '\u00c0': // À
			case '\u00c1': // Á
			case '\u00c2': // Â
			case '\u00c3': // Ã
			case '\u00c4': // Ä
			case '\u00c5': // Å
			case '\u00c6': // Æ
			case '\u0040': // @
				chrL = 'A';
				break;
			case '\u00df': // ß
				chrL = 'B';
				break;
			case '\u00a2': // Cent
			case '\u00c7': // Ç
			case '\u00a9': // ©
				chrL = 'C';
				break;
			case '\u00c8': // È
			case '\u00c9': // É
			case '\u00ca': // Ê
			case '\u00cb': // Ë
			case '\u20ac': // €
				chrL = 'E';
				break;
			case '\u00cc': // Ì
			case '\u00cd': // Í
			case '\u00ce': // Î
			case '\u00cf': // Ï
				chrL = 'I';
				break;
			case '\u00a3': // £
				chrL = 'L';
				break;
			case '\u00d1': // Ñ
				chrL = 'N';
				break;
			case '\u00d2': // Ò
			case '\u00d3': // Ó
			case '\u00d4': // Ô
			case '\u00d5': // Õ
			case '\u00d6': // Ö
			case '\u0152': // Œ
			case '\u00d8': // Ø
				chrL = 'O';
				break;
			case '\u00ae': // ®
				chrL = 'R';
				break;
			case '\u0160': // Š
			case '\u0024': // $
				chrL = 'S';
				break;
			case '\u00d9': // Ù
			case '\u00da': // Ú
			case '\u00db': // Û
			case '\u00dc': // Ü
				chrL = 'U';
				break;
			case '\u00d7': // ×
				chrL = 'X';
				break;
			case '\u0178': // Ÿ
			case '\u00a5': // ¥
			case '\u00dd': // Ý
				chrL = 'Y';
				break;
			case '\u017d': // Ž
				chrL = 'Z';
				break;
		}
		return chrL;
	}

	final public static String getUpperCaseString(String strP) {

		if (strP == null) {
			return null;
		}
		final char[] chrLupperCaseA = strP.toCharArray();
		final StringBuilder objLstringBuilder = new StringBuilder(strP.length());
		for (final char chrL : chrLupperCaseA) {
			objLstringBuilder.append(Strings.getUpperCaseChar(chrL));
		}
		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param fltPvalue
	 * @param intPintegerNumbers
	 * @param intPdecimalNumbers
	 * @param intPlength
	 * @param bolPrightAligned
	 * @return
	 */
	final public static String getValueString(	float fltPvalue,
												int intPintegerNumbers,
												int intPdecimalNumbers,
												int intPlength,
												boolean bolPrightAligned) {
		final StringBuilder objLvalueStringBuilder = new StringBuilder(intPlength);

		// Integer part :
		objLvalueStringBuilder.append((int) fltPvalue);

		while (objLvalueStringBuilder.length() < intPintegerNumbers) {
			objLvalueStringBuilder.insert(0, 0);
		}

		// Decimal part :
		if (intPdecimalNumbers > 0) {
			objLvalueStringBuilder.append('.');
			objLvalueStringBuilder.append(Math.round((fltPvalue - ((int) fltPvalue)) * (float) Math.pow(10, intPdecimalNumbers)));
		}

		// Length :
		final int intLobjLvalueStringBuilderLength = objLvalueStringBuilder.length();

		if (intLobjLvalueStringBuilderLength > intPlength) {
			objLvalueStringBuilder.delete(intPlength, intLobjLvalueStringBuilderLength);
		} else if (bolPrightAligned) {
			objLvalueStringBuilder.insert(0, Strings.getSpacesString(intPlength - intLobjLvalueStringBuilderLength));
		} else {
			objLvalueStringBuilder.append(Strings.getSpacesString(intPlength - intLobjLvalueStringBuilderLength));
		}

		objLvalueStringBuilder.trimToSize();

		return objLvalueStringBuilder.toString();
	}

	final public static String toString(Object objPobject) {
		return objPobject == null ? Strings.strS_EMPTY : objPobject instanceof String ? (String) objPobject : objPobject.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String uncomment(String strP) {

		int intLsemiColonIndex = Constants.bytS_UNCLASS_NO_VALUE;
		final char[] chrLstringA = strP.toCharArray();
		for (int intLcharIndexed = 0; intLcharIndexed < chrLstringA.length; ++intLcharIndexed) {
			if (chrLstringA[intLcharIndexed] == ';') {
				intLsemiColonIndex = intLcharIndexed;
				break;
			}
		}

		while ((intLsemiColonIndex > 0) && (chrLstringA[intLsemiColonIndex - 1] == ' ' || chrLstringA[intLsemiColonIndex - 1] == '\t')) {
			--intLsemiColonIndex;
		}

		return (intLsemiColonIndex >= 0 ? strP.substring(0, intLsemiColonIndex) : strP);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String uncommentUntab(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLuncommentabbedStringBuilder = new StringBuilder(chrLstringA.length);

		for (final char chrLindexed : chrLstringA) {
			if (chrLindexed == ';') {
				break;
			}
			objLuncommentabbedStringBuilder.append((chrLindexed == '\t') ? ' ' : chrLindexed);
		}

		objLuncommentabbedStringBuilder.trimToSize();
		return objLuncommentabbedStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String uncommentUntabTrim(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLuncommentTabTrimStringBuilder = new StringBuilder(chrLstringA.length);
		final StringBuilder objLtempSpaceStringBuilder = new StringBuilder(1);
		boolean bolLspaceBefore = true;;

		for (final char chrLindexed : chrLstringA) {
			if (chrLindexed == ';') {
				break;
			}

			switch (chrLindexed) {
				case ' ':
				case '\t':
					if (!bolLspaceBefore) {
						objLtempSpaceStringBuilder.append(' ');
					}

					break;

				default:
					if (objLtempSpaceStringBuilder.length() > 0) {
						objLuncommentTabTrimStringBuilder.append(objLtempSpaceStringBuilder);
						objLtempSpaceStringBuilder.setLength(0);
					}

					bolLspaceBefore = false;
					objLuncommentTabTrimStringBuilder.append(chrLindexed);
			}
		}
		objLuncommentTabTrimStringBuilder.trimToSize();
		return objLuncommentTabTrimStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String undoubleSpace(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLundoubleSpaceStringBuilder = new StringBuilder(chrLstringA.length);
		boolean bolLspace = false;

		for (final char chrLindexed : chrLstringA) {
			switch (chrLindexed) {
				case ' ':
				case '\t':
					bolLspace = true;
					break;

				default:
					if (bolLspace) {
						objLundoubleSpaceStringBuilder.append(' ');
						bolLspace = false;
					}
					objLundoubleSpaceStringBuilder.append(chrLindexed);
			}
		}
		if (bolLspace) {
			objLundoubleSpaceStringBuilder.append(' ');
		}
		return objLundoubleSpaceStringBuilder.toString();
	}

	final public static String undoubleSpaceTrim(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLundoubleSpaceStringBuilder = new StringBuilder(chrLstringA.length);
		boolean bolLspace = false;

		for (final char chrLindexed : chrLstringA) {
			switch (chrLindexed) {
				case ' ':
				case '\t':
					if (objLundoubleSpaceStringBuilder.length() == 0) {
						break;
					}

					bolLspace = true;
					break;

				default:
					if (bolLspace) {
						objLundoubleSpaceStringBuilder.append(' ');
						bolLspace = false;
					}

					objLundoubleSpaceStringBuilder.append(chrLindexed);
			}
		}

		return objLundoubleSpaceStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String unspace(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLstringBuilder = new StringBuilder(chrLstringA.length);
		for (final char chrLindexed : chrLstringA) {
			if (chrLindexed != ' ' && chrLindexed != '\t') {
				objLstringBuilder.append(chrLindexed);
			}
		}
		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String untab(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLuntabbedStringBuilder = new StringBuilder(chrLstringA.length);

		for (final char chrLstringIndexChar : chrLstringA) {
			objLuntabbedStringBuilder.append(chrLstringIndexChar == '\t' ? ' ' : chrLstringIndexChar);
		}
		objLuntabbedStringBuilder.trimToSize();
		return objLuntabbedStringBuilder.toString();
	}

	final public static String untabDashTrim(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLtabTrimStringBuilder = new StringBuilder(chrLstringA.length);
		final StringBuilder objLtempSpaceStringBuilder = new StringBuilder(1);
		boolean bolLspaceBefore = true;

		for (final char chrLstringIndexChar : chrLstringA) {
			switch (chrLstringIndexChar) {
				case ' ':
				case '\t':
					if (!bolLspaceBefore) {
						objLtempSpaceStringBuilder.append(' ');
					}
					break;

				case '-':
				case '/':
					if (objLtabTrimStringBuilder.length() == 0) {
						break;
					}

					//$FALL-THROUGH$
				default:
					if (objLtempSpaceStringBuilder.length() > 0) {
						objLtabTrimStringBuilder.append(objLtempSpaceStringBuilder);
						objLtempSpaceStringBuilder.setLength(0);
					}

					bolLspaceBefore = false;
					objLtabTrimStringBuilder.append(chrLstringIndexChar);
			}
		}

		objLtabTrimStringBuilder.trimToSize();

		return objLtabTrimStringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @return
	 */
	final public static String untabTrim(String strP) {

		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLtabTrimStringBuilder = new StringBuilder(chrLstringA.length);
		final StringBuilder objLtempSpaceStringBuilder = new StringBuilder(1);
		boolean bolLspaceBefore = true;

		for (final char chrLstringIndexChar : chrLstringA) {
			switch (chrLstringIndexChar) {
				case ' ':
				case '\t':
					if (!bolLspaceBefore) {
						objLtempSpaceStringBuilder.append(' ');
					}

					break;

				default:
					if (objLtempSpaceStringBuilder.length() > 0) {
						objLtabTrimStringBuilder.append(objLtempSpaceStringBuilder);
						objLtempSpaceStringBuilder.setLength(0);
					}

					bolLspaceBefore = false;
					objLtabTrimStringBuilder.append(chrLstringIndexChar);
			}
		}

		objLtabTrimStringBuilder.trimToSize();

		return objLtabTrimStringBuilder.toString();
	}

	// final public static char[] chrS_COLOR_A;
	final public static char						chrS_UNCLASS_NULL_CHAR			= (char) 0;

	final public static HashMap<Character, String>	objS_URL_ENCODING_CHAR_STRING_HASHMAP;

	@SuppressWarnings("unused")
	final private static long						serialVersionUID				= Constants.lngS_ENGINE_VERSION_NUMBER;

	final public static String						strS_ELLIPSIS					= "...";

	final public static String						strS_EMPTY						= "";

	final public static String						strS_HTML_BR					= "<br/>";

	final public static String						strS_HTML_CLOSING				= "</html>";

	final public static String						strS_HTML_CLOSING_NO_BREAK		= "</nobr>";

	final public static String						strS_HTML_CLOSING_SPAN			= "</span>";

	final public static String						strS_HTML_CLOSING_SPAN_STYLE	= "'>";

	final public static String						strS_HTML_COLOR_STYLE			= "color: ";

	final public static String						strS_HTML_FONT_STYLE			= "font-style: ";

	final public static String						strS_HTML_ITALIC_STYLE			= "italic; ";

	final public static String						strS_HTML_NORMAL_STYLE			= "normal; ";

	final public static String						strS_HTML_OPENING				= "<html>";

	final public static String						strS_HTML_OPENING_NO_BREAK		= "<nobr>";

	final public static String						strS_HTML_OPENING_SPAN_STYLE	= "<span style='";

	final public static String						strS_HTML_SPACE					= "&nbsp;";

	public final static String						strS_JUGGLE_MASTER				= "JuggleMaster";

	final public static String						strS_JUGGLE_MASTER_URL			= "http://www.jugglemaster.fr";

	final public static String						strS_LEFT_ASTERIX				= "* ";

	final public static String						strS_LINE_SEPARATOR;

	final public static String						strS_RIGHT_ASTERIX				= " *";

	final public static String						strS_SEPARATOR					= "--";

	final public static String						strS_SPACE						= " ";

	final public static String						strS_SPACES						= "        ";

	static {
		strS_LINE_SEPARATOR = Strings.getLineSeparatorString();
		objS_URL_ENCODING_CHAR_STRING_HASHMAP = new HashMap<Character, String>(10);
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put(' ', "%20");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('à', "%e0");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('â', "%e2");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('ç', "%e7");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('è', "%e8");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('é', "%e9");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('ê', "%ea");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('î', "%ee");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('ô', "%f4");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('ù', "%f9");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('û', "%fb");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put(';', "%3b");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('/', "%2f");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('?', "%3f");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put(':', "%3a");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('=', "%3d");
		Strings.objS_URL_ENCODING_CHAR_STRING_HASHMAP.put('+', "%2b");
	}
}
