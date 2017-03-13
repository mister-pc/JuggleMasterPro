package jugglemasterpro.pattern.io;

import java.util.ArrayList;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

public final class JMFileParser extends PatternsFileParser {

	public JMFileParser(PatternsFilesManager objPpatternsFilesManager) {
		super(objPpatternsFilesManager);
		this.bytGpatternsFileExtension = Constants.bytS_EXTENSION_JM;
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

		// TODO : JMFileParser.doParsePatternsFile
		Tools.debug(Strings.doConcat(	"JMFileParser.doParsePatternsFile(",
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
