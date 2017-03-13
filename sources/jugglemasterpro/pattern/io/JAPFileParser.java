package jugglemasterpro.pattern.io;

import java.util.ArrayList;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

public final class JAPFileParser extends PatternsFileParser {

	/**
	 * @param objPpatternsFilesManager
	 */
	public JAPFileParser(PatternsFilesManager objPpatternsFilesManager) {
		super(objPpatternsFilesManager);
		this.bytGpatternsFileExtension = Constants.bytS_EXTENSION_JAP;
	}

	/*
	 * (non-Javadoc)
	 * @see jugglemasterpro.pattern.io.PatternsFileParser#doParsePatternsFile(jugglemasterpro.pattern.io.FileBufferedReader, boolean, boolean, boolean, java.lang.String, boolean, int)
	 */
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
		// ConsoleObject[]
		// Boolean pattern found

		// TODO : JAPFileParser.doParsePatternsFile
		Tools.debug(Strings.doConcat(	"JAPFileParser.doParsePatternsFile(",
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

		return null;
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
