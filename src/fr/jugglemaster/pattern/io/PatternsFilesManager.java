package fr.jugglemaster.pattern.io;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import fr.jugglemaster.pattern.PatternsManager;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

final public class PatternsFilesManager {

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPpatternsBufferedReader
	 * @param strPtitle
	 * @param bolPnewLists
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 * @param strPpatternName
	 * @param intPstartPatternOccurrence
	 */
	public PatternsFilesManager(PatternsManager objPpatternsManager/* , byte bytPfileExtension */) {
		this.objGpatternsManager = objPpatternsManager;
		this.strGtitleAL = new ArrayList<String>(1);
		this.strGreferenceAL = new ArrayList<String>(1);
		this.imgGAL = new ArrayList<ImageIcon>(1);
		this.bolGsiteswapsAL = new ArrayList<Boolean>(1);
		this.bolGstylesAL = new ArrayList<Boolean>(1);
		this.objGwrongStyleAL = new ArrayList<String>();

		this.bolGstyleFound = false;
		this.bolGstartObject = true;
		this.intGstartObjectIndex = Constants.bytS_UNCLASS_NO_VALUE;
		this.objGerrorConsoleObjectAL = new ArrayList<ConsoleObject>();
		this.objGwarningConsoleObjectAL = new ArrayList<ConsoleObject>();
		this.intGglobalFatalErrorsNumber = this.intGlocalFatalErrorsNumber = this.intGglobalWarningsNumber = this.intGlocalWarningsNumber = 0;
	}

	// final private byte bytGfileExtension;

	@SuppressWarnings("unchecked") final public boolean doLoadPatternsFile(	PatternsFile objPpatternsFile,
																			ImageIcon imgPicon,
																			boolean bolPnewLists,
																			boolean bolPsiteswaps,
																			boolean bolPstyles,
																			String strPpatternName,
																			boolean bolPonlyThisPattern,
																			int intPstartPatternOccurrence) {

		// Add file properties :
		this.strGreferenceAL.add(objPpatternsFile.getReferenceString());
		this.strGtitleAL.add(objPpatternsFile.getTitleString());
		this.imgGAL.add(imgPicon);
		this.bolGsiteswapsAL.add(bolPsiteswaps);
		this.bolGstylesAL.add(bolPstyles);

		// Initializations :
		if (bolPnewLists) {
			this.objGpatternsManager.strGstyleAL = new ArrayList<String>(bolPstyles ? 64 : 1);
			this.objGpatternsManager.strGstyleAL.add(Constants.strS_STRING_LOCAL_STYLE_DEFAULT);
		} else {
			this.objGpatternsManager.initInitialValues(false, objPpatternsFile.getFileFormat());
			this.objGpatternsManager.setBoolean(Constants.bytS_BOOLEAN_LOCAL_ERRORS, true);
			this.objGpatternsManager.setBoolean(Constants.bytS_BOOLEAN_LOCAL_WARNINGS, true);
			this.objGpatternsManager.setBoolean(Constants.bytS_BOOLEAN_LOCAL_INFO, true);

			this.objGwrongStyleAL = new ArrayList<String>();
			this.objGerrorConsoleObjectAL = new ArrayList<ConsoleObject>();
			this.objGwarningConsoleObjectAL = new ArrayList<ConsoleObject>();
			this.intGglobalFatalErrorsNumber = this.intGlocalFatalErrorsNumber = this.intGlocalWarningsNumber = this.intGglobalWarningsNumber = 0;
			if (bolPsiteswaps) {
				this.objGpatternsManager.bytGpatternsManagerType = Constants.bytS_MANAGER_FILES_PATTERNS;
			}
		}
		this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_STYLE, Constants.strS_STRING_LOCAL_STYLE_DEFAULT);
		this.objGpatternsManager.setString(	Constants.bytS_STRING_LOCAL_STYLE,
											Constants.bytS_UNCLASS_CURRENT,
											Constants.strS_STRING_LOCAL_STYLE_DEFAULT);

		// Parse file :
		PatternsFileParser objLpatternsFileParser = null;
		switch (objPpatternsFile.getFileFormat()) {
			case Constants.bytS_EXTENSION_JAP:
				objLpatternsFileParser = new JAPFileParser(this);
				break;
			case Constants.bytS_EXTENSION_JM:
				objLpatternsFileParser = new JMFileParser(this);
				break;
			case Constants.bytS_EXTENSION_JMP:
			default:
				objLpatternsFileParser = new JMPFileParser(this);
				break;
		}
		final ArrayList<Object> objLAL =
											objLpatternsFileParser.doParsePatternsFile(	objPpatternsFile,
																						bolPnewLists,
																						bolPsiteswaps,
																						bolPstyles,
																						strPpatternName,
																						bolPonlyThisPattern,
																						intPstartPatternOccurrence);

		final ArrayList<Object> objLobjectAL = objLAL != null ? (ArrayList<Object>) objLAL.get(0) : new ArrayList<Object>(1);
		final ArrayList<Integer> intLshortcutIndexAL = objLAL != null ? (ArrayList<Integer>) objLAL.get(1) : new ArrayList<Integer>(1);
		final boolean bolLpatternFound = objLAL != null ? (Boolean) objLAL.get(2) : false;

		this.objGpatternsManager.initCurrentValues();
		if (bolPsiteswaps) {
			if (!bolLpatternFound) {
				if (bolPnewLists) {
					this.objGpatternsManager.doCreateNoPatternManager(	this.strGreferenceAL.get(0),
																		this.strGtitleAL.get(0),
																		this.imgGAL.get(0),
																		this.bolGsiteswapsAL.get(0),
																		this.bolGstylesAL.get(0),
																		objLobjectAL,
																		this.bolGstyleFound
																							? this.objGpatternsManager.getStringValue(	Constants.bytS_STRING_LOCAL_STYLE,
																																		Constants.bytS_UNCLASS_INITIAL)
																							: null);
				}
			} else {

				// Create (new) object list :
				if (bolPnewLists) {
					this.objGpatternsManager.objGobjectA = objLobjectAL.toArray();
				} else {
					objLobjectAL.add(0, Strings.strS_SPACE);
					objLobjectAL.add(	1,
										Strings.doConcat(	"[ ",
															(bolPsiteswaps && bolPstyles)
																							? Language.strS_TOKEN_A[Language.bytS_TOKEN_PATTERNS_IMPORTED_FROM]
																							: Language.strS_TOKEN_A[Language.bytS_TOKEN_SITESWAPS_IMPORTED_FROM],
															" : ",
															objPpatternsFile.getReferenceString(),
															" ]"));
					intLshortcutIndexAL.add(0, Integer.valueOf(this.objGpatternsManager.objGobjectA.length + 1));

					final Object[] objLpreviousObjectA = new Object[this.objGpatternsManager.objGobjectA.length];
					final Object[] objLimportedObjectA = objLobjectAL.toArray();
					System.arraycopy(this.objGpatternsManager.objGobjectA, 0, objLpreviousObjectA, 0, this.objGpatternsManager.objGobjectA.length);
					this.objGpatternsManager.objGobjectA = new Object[objLpreviousObjectA.length + objLimportedObjectA.length];
					System.arraycopy(objLpreviousObjectA, 0, this.objGpatternsManager.objGobjectA, 0, objLpreviousObjectA.length);
					System.arraycopy(	objLimportedObjectA,
										0,
										this.objGpatternsManager.objGobjectA,
										objLpreviousObjectA.length,
										objLimportedObjectA.length);
				}

				// Shortcut list serialization :
				if (bolPsiteswaps) {
					final int intLnewShortcutsNumber = intLshortcutIndexAL.size();

					if (bolPnewLists || (this.objGpatternsManager.intGshortcutIndexA == null)) {
						this.objGpatternsManager.intGshortcutIndexA = null;

						if (intLnewShortcutsNumber > 0) {
							this.objGpatternsManager.intGshortcutIndexA = new int[intLnewShortcutsNumber];

							for (int intLarrayListIndex = 0; intLarrayListIndex < intLnewShortcutsNumber; ++intLarrayListIndex) {
								this.objGpatternsManager.intGshortcutIndexA[intLarrayListIndex] =
																									intLshortcutIndexAL	.get(intLarrayListIndex)
																														.intValue();
							}
						}
					} else {
						if (intLnewShortcutsNumber > 0) {
							final int[] intLnewShortcutIndexA = new int[this.objGpatternsManager.intGshortcutIndexA.length + intLnewShortcutsNumber];

							System.arraycopy(	this.objGpatternsManager.intGshortcutIndexA,
												0,
												intLnewShortcutIndexA,
												0,
												this.objGpatternsManager.intGshortcutIndexA.length);

							for (int intLnewShortcutIndex = 0; intLnewShortcutIndex < intLnewShortcutsNumber; ++intLnewShortcutIndex) {
								intLnewShortcutIndexA[this.objGpatternsManager.intGshortcutIndexA.length + intLnewShortcutIndex] =
																																	intLshortcutIndexAL	.get(intLnewShortcutIndex)
																																						.intValue();
							}

							this.objGpatternsManager.intGshortcutIndexA = intLnewShortcutIndexA;
						}
					}

					// Suppress multiple occurrences of shortcuts by appending space(s) to the second one :
					if (this.objGpatternsManager.intGshortcutIndexA != null) {
						for (int intLshortcutToCompareIndex = 0; intLshortcutToCompareIndex < this.objGpatternsManager.intGshortcutIndexA.length; ++intLshortcutToCompareIndex) {
							for (int intLcomparedShortcutIndex = 0; intLcomparedShortcutIndex < intLshortcutToCompareIndex; ++intLcomparedShortcutIndex) {
								if (((String) this.objGpatternsManager.objGobjectA[this.objGpatternsManager.intGshortcutIndexA[intLshortcutToCompareIndex]]).equals(this.objGpatternsManager.objGobjectA[this.objGpatternsManager.intGshortcutIndexA[intLcomparedShortcutIndex]])) {
									this.objGpatternsManager.objGobjectA[this.objGpatternsManager.intGshortcutIndexA[intLshortcutToCompareIndex]] =
																																					Strings.doConcat(	this.objGpatternsManager.objGobjectA[this.objGpatternsManager.intGshortcutIndexA[intLshortcutToCompareIndex]],
																																										Strings.strS_SPACE);
									intLcomparedShortcutIndex = -1;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPlanguage
	 * @return
	 */
	final public String getConsoleString(Language objPlanguage) {

		final int intLfatalErrorsNumber = this.getFatalErrorsNumber();
		final int intLwarningsNumber = this.getWarningsNumber();
		if (intLfatalErrorsNumber + intLwarningsNumber > 0) {
			final StringBuilder objLerrorsStringBuilder = new StringBuilder(2048);

			// Set warning & error number header :
			objLerrorsStringBuilder.append(Strings.doConcat(intLwarningsNumber > 0
																					? Strings.doConcat(	'[',
																										objPlanguage.getPropertyValueString(Language.intS_LABEL_WARNINGS_NUMBER),
																										" : ",
																										intLwarningsNumber,
																										']',
																										Strings.strS_LINE_SEPARATOR) : null,
															intLfatalErrorsNumber > 0
																						? Strings.doConcat(	'[',
																											objPlanguage.getPropertyValueString(Language.intS_LABEL_ERRORS_NUMBER),
																											" : ",
																											intLfatalErrorsNumber,
																											']',
																											Strings.strS_LINE_SEPARATOR) : null));

			// Set warning & error list :
			boolean bolLlocal = false;
			int intLfatalErrorIndex = 0;
			int intLwarningIndex = 0;
			ConsoleObject objLfatalJuggleError = intLfatalErrorsNumber > 0 ? this.objGerrorConsoleObjectAL.get(intLfatalErrorIndex) : null;
			ConsoleObject objLwarningJuggleError = intLwarningsNumber > 0 ? this.objGwarningConsoleObjectAL.get(intLwarningIndex) : null;
			ConsoleObject objLerror = null;
			while (true) {
				final boolean bolLfatal =
											(objLwarningJuggleError != null
																			? objLfatalJuggleError != null
																				&& objLfatalJuggleError.getFileLineIndex() < objLwarningJuggleError.getFileLineIndex()
																																										? true
																																										: false
																			: true);
				objLerror = bolLfatal ? objLfatalJuggleError : objLwarningJuggleError;
				if (objLerror == null) {
					break;
				}
				if (bolLfatal) {
					++intLfatalErrorIndex;
					objLfatalJuggleError =
											intLfatalErrorIndex < intLfatalErrorsNumber ? this.objGerrorConsoleObjectAL.get(intLfatalErrorIndex)
																						: null;
					bolLlocal = this.objGpatternsManager.bolGbooleanIsLocalA[Constants.bytS_BOOLEAN_LOCAL_ERRORS];
				} else {
					++intLwarningIndex;
					objLwarningJuggleError = intLwarningIndex < intLwarningsNumber ? this.objGwarningConsoleObjectAL.get(intLwarningIndex) : null;
					bolLlocal = this.objGpatternsManager.bolGbooleanIsLocalA[Constants.bytS_BOOLEAN_LOCAL_WARNINGS];
				}

				objLerrorsStringBuilder.append(objLerror.toString(bolLlocal, objPlanguage));
			}

			return objLerrorsStringBuilder.toString();
		}
		return Strings.strS_EMPTY;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public int getFatalErrorsNumber() {
		return (this.objGpatternsManager.bolGbooleanIsLocalA[Constants.bytS_BOOLEAN_LOCAL_ERRORS] ? this.intGlocalFatalErrorsNumber
																									: this.intGglobalFatalErrorsNumber);
	}

	final public PatternsManager getPatternsManager() {
		return this.objGpatternsManager;
	}

	final public String getTitlesString() {

		final StringBuilder objLstringBuilder = new StringBuilder(64);
		final int intLsize = this.strGtitleAL.size();
		for (int intLtitleIndex = 0; intLtitleIndex < intLsize; ++intLtitleIndex) {
			final String strLtitle = this.strGtitleAL.get(intLtitleIndex);
			if (!Tools.isEmpty(strLtitle)) {
				if (intLtitleIndex > 0) {
					objLstringBuilder.append(" - ");
				}
				objLstringBuilder.append(strLtitle);
			}
		}

		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public int getWarningsNumber() {
		return (this.objGpatternsManager.bolGbooleanIsLocalA[Constants.bytS_BOOLEAN_LOCAL_WARNINGS] ? this.intGlocalWarningsNumber
																									: this.intGglobalWarningsNumber);
	}

	final public void initJMPatternManager(int intPstartObjectIndex) {
		this.intGstartObjectIndex = intPstartObjectIndex;
		this.bolGstyleFound = true;
		this.strGreferenceAL.add(null);
		this.strGtitleAL.add(null);
		this.imgGAL.add(null);
		this.bolGsiteswapsAL.add(true);
		this.bolGstylesAL.add(true);
	}

	final public void initNewLanguagePatternsManager() {
		this.bolGstyleFound = true;
		this.bolGstartObject = true;
		this.intGglobalFatalErrorsNumber =
											this.intGlocalFatalErrorsNumber =
																				this.intGglobalWarningsNumber =
																												this.intGstartObjectIndex =
																																			this.intGlocalWarningsNumber =
																																											0;
		this.strGreferenceAL.add(null);
		this.strGtitleAL.add(null);
		this.imgGAL.add(null);
		this.bolGsiteswapsAL.add(false);
		this.bolGstylesAL.add(false);
	}

	final public void initNewPatternManager(int intPstartObjectIndex) {
		this.intGstartObjectIndex = intPstartObjectIndex;
		this.bolGstyleFound = false;
		this.strGreferenceAL.add(null);
		this.strGtitleAL.add(null);
		this.imgGAL.add(null);
		this.bolGsiteswapsAL.add(true);
		this.bolGstylesAL.add(true);
	}

	final public void initNoPatternManager(	String strPfileName,
											String strPtitle,
											ImageIcon imgP,
											boolean bolPsiteswaps,
											boolean bolPstyles,
											boolean bolPstyleFound) {

		this.strGreferenceAL.clear();
		this.strGreferenceAL.add(strPfileName);
		this.strGtitleAL.clear();
		this.strGtitleAL.add(strPtitle);
		this.imgGAL.clear();
		this.imgGAL.add(imgP);
		this.bolGsiteswapsAL.clear();
		this.bolGsiteswapsAL.add(bolPsiteswaps);
		this.bolGstylesAL.clear();
		this.bolGstylesAL.add(bolPstyles);
		this.bolGstyleFound = bolPstyleFound;
	}

	public ArrayList<Boolean>		bolGsiteswapsAL;

	public boolean					bolGstartObject;

	public boolean					bolGstyleFound;

	public ArrayList<Boolean>		bolGstylesAL;

	public ArrayList<ImageIcon>		imgGAL;

	public int						intGglobalFatalErrorsNumber;

	public int						intGglobalWarningsNumber;

	public int						intGlocalFatalErrorsNumber;

	public int						intGlocalWarningsNumber;

	public int						intGstartObjectIndex	= Constants.bytS_UNCLASS_NO_VALUE;

	public ArrayList<ConsoleObject>	objGerrorConsoleObjectAL;

	final private PatternsManager	objGpatternsManager;

	public ArrayList<ConsoleObject>	objGwarningConsoleObjectAL;

	public ArrayList<String>		objGwrongStyleAL;

	public ArrayList<String>		strGreferenceAL;

	public ArrayList<String>		strGtitleAL;

	@SuppressWarnings("unused")
	final private static long		serialVersionUID		= Constants.lngS_ENGINE_VERSION_NUMBER;
}
