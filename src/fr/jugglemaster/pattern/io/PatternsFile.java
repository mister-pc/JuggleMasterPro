/*
 * @(#)PatternsBufferedReader.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.pattern.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
public final class PatternsFile {

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPcodeBase
	 * @param strPfileName
	 * @return
	 */
	PatternsFile(String strPcodeBase, String strPfileName) {
		this(strPcodeBase, strPfileName, null);
	}

	public PatternsFile(String strPcodeBase, String strPfileReference, String strPfileTitle) {

		this.strGfileReference = new String(strPfileReference);
		this.setFileFormat(this.strGfileReference);
		this.strGfileTitle = strPfileTitle;
		this.intGlineIndex = 0;
		if (strPfileTitle == null) {
			if (!Tools.isEmpty(strPfileReference)) {
				final int intLlastPathSeparatorIndex =
														Math.max(	Math.max(strPfileReference.lastIndexOf('/'), strPfileReference.lastIndexOf('\\')),
																	strPfileReference.lastIndexOf(File.separatorChar));
				if (intLlastPathSeparatorIndex >= 0) {
					this.strGfileTitle = Strings.getRightDotTrimmedString(strPfileReference.substring(intLlastPathSeparatorIndex + 1));
				}
			}
		}

		BufferedReader objLbufferedReader = null;
		for (byte bytLreferenceIndex = 0; bytLreferenceIndex < 2 && objLbufferedReader == null; ++bytLreferenceIndex) {
			final String strLfullReference = (bytLreferenceIndex == 0) ? strPfileReference : Strings.doConcat(strPcodeBase, strPfileReference);
			try {
				objLbufferedReader = new BufferedReader(new FileReader(strLfullReference));
			} catch (final Throwable objPfirstThrowable) {
				try {
					objLbufferedReader = new BufferedReader(new InputStreamReader(new URL(strLfullReference).openStream()));
				} catch (final Throwable objPsecondThrowable) {
					objLbufferedReader = null;
				}
			}
		}

		if (objLbufferedReader == null) {
			Tools.err("Error while trying to open '", strPfileReference, "'");
			this.strGfileLineA = null;
		} else {
			final ArrayList<String> strLlineAL = new ArrayList<String>(1024);
			while (true) {
				try {
					strLlineAL.add(Strings.untabTrim(objLbufferedReader.readLine()));
				} catch (final Throwable objPreadThrowable) {
					try {
						objLbufferedReader.close();
					} catch (final Throwable objPcloseThrowable) {
						Tools.err("Error while closing pattern file descriptor");
					}
					break;
				}
			}

			final int intLlinesNumber = strLlineAL.size();
			if (intLlinesNumber > 0) {
				this.strGfileLineA = new String[intLlinesNumber];
				for (int intLlineIndex = 0; intLlineIndex < intLlinesNumber; ++intLlineIndex) {
					this.strGfileLineA[intLlineIndex] = strLlineAL.get(intLlineIndex);
				}
			} else {
				this.strGfileLineA = null;
			}
		}
	}

	final public byte getFileFormat() {
		return this.bytGfileFormat;
	}

	final public int getLineIndex() {
		return this.intGlineIndex;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getLineString() {
		return this.getLineString(this.intGlineIndex);
	}

	final public String getLineString(int intPlineIndex) {
		return this.strGfileLineA != null && 0 <= intPlineIndex && intPlineIndex < this.strGfileLineA.length ? this.strGfileLineA[intPlineIndex]
																											: null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getReferenceString() {
		return this.strGfileReference;
	}

	final public String getTitleString() {
		return this.strGfileTitle;
	}

	final private void setFileFormat(String strPfileName) {

		this.bytGfileFormat = Constants.bytS_EXTENSION_JMP;
		final String strLlowerCaseFileName = strPfileName.toLowerCase();
		for (final byte bytLextension : new byte[] { Constants.bytS_EXTENSION_JMP, Constants.bytS_EXTENSION_JM, Constants.bytS_EXTENSION_JAP }) {
			if (strLlowerCaseFileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[bytLextension].toLowerCase()))) {
				this.bytGfileFormat = bytLextension;
				break;
			}
		}
	}

	final public boolean setLineIndex(int intPlineIndex) {
		this.intGlineIndex = intPlineIndex;
		return this.strGfileLineA != null && 0 <= intPlineIndex && intPlineIndex < this.strGfileLineA.length;
	}

	final public boolean setNextLineIndex() {
		return this.setLineIndex(this.intGlineIndex + 1);
	}

	final public boolean setPreviousLineIndex() {
		return this.setLineIndex(this.intGlineIndex - 1);
	}

	private byte				bytGfileFormat;

	private int					intGlineIndex;

	private String[]			strGfileLineA;

	private final String		strGfileReference;

	private String				strGfileTitle;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PatternsBufferedReader.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
