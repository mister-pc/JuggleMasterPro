package jugglemasterpro.pattern;

import java.util.ArrayList;
import java.util.Arrays;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

final public class Siteswap {

	@SuppressWarnings("unused") final private static boolean	bolS_UNCLASS_LOG	= false;

	@SuppressWarnings("unused")
	final private static long									serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean[]											bolGspaceAfterA;

	public boolean												bolGstarred;

	public boolean												bolGsymmetric;

	public boolean												bolGsynchro;

	public byte[][]												bytGballThrowAA;

	public byte													bytGhighestThrow;

	public byte													bytGstatus;

	public int													intGballsNumber;

	public int													intGerrorCursorIndex;

	public int[]												intGpermutationA;

	public int													intGrecurrentThrowsNumber;

	private int[]												intGsequenceA;

	public int													intGsequencesMaxLength;

	public int[]												intGthrowBallsNumberA;

	private int[]												intGthrowLengthA;

	public int													intGthrowsNumber;

	public String												strGsiteswap;

	public Siteswap(Siteswap objPsiteswap) {

		// Copy objects :
		this.strGsiteswap = new String(objPsiteswap.strGsiteswap);

		// Copy values :
		this.intGthrowsNumber = objPsiteswap.intGthrowsNumber;
		this.intGrecurrentThrowsNumber = objPsiteswap.intGrecurrentThrowsNumber;
		this.intGballsNumber = objPsiteswap.intGballsNumber;
		this.bytGstatus = objPsiteswap.bytGstatus;
		this.bytGhighestThrow = objPsiteswap.bytGhighestThrow;
		this.bolGsynchro = objPsiteswap.bolGsynchro;
		this.bolGsymmetric = objPsiteswap.bolGsymmetric;

		// Copy ball number per throw :
		try {
			this.intGthrowBallsNumberA = new int[this.intGthrowsNumber];
			System.arraycopy(objPsiteswap.intGthrowBallsNumberA, 0, this.intGthrowBallsNumberA, 0, this.intGthrowsNumber);
		} catch (final Throwable objPthrowable) {
			this.intGthrowBallsNumberA = null;
		}

		// Copy siteswap values :
		try {
			this.bytGballThrowAA = new byte[this.intGthrowsNumber][objPsiteswap.bytGballThrowAA[0].length];
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				for (int intLballIndex = 0; intLballIndex < objPsiteswap.intGthrowBallsNumberA[intLthrowIndex]; ++intLballIndex) {
					this.bytGballThrowAA[intLthrowIndex][intLballIndex] = objPsiteswap.bytGballThrowAA[intLthrowIndex][intLballIndex];
				}
			}
		} catch (final Throwable objPthrowable) {
			this.bytGballThrowAA = null;
		}

		// Copy sequence array :
		try {
			this.intGsequenceA = new int[objPsiteswap.intGsequenceA.length];
			System.arraycopy(objPsiteswap.intGsequenceA, 0, this.intGsequenceA, 0, objPsiteswap.intGsequenceA.length);
		} catch (final Throwable objPthrowable) {
			this.intGsequenceA = null;
		}

		// Copy 'space after throw' booleans :
		try {
			this.bolGspaceAfterA = new boolean[this.intGthrowsNumber];
			System.arraycopy(objPsiteswap.bolGspaceAfterA, 0, this.bolGspaceAfterA, 0, this.intGthrowsNumber);
		} catch (final Throwable objPthrowable) {
			this.bolGspaceAfterA = null;
		}

		// Copy throw string lengths :
		try {
			this.intGthrowLengthA = new int[this.intGthrowsNumber];
			System.arraycopy(objPsiteswap.intGthrowLengthA, 0, this.intGthrowLengthA, 0, this.intGthrowsNumber);
		} catch (final Throwable objPthrowable) {
			this.intGthrowLengthA = null;
		}
	}

	public Siteswap(String strPsiteswap) {

		// Constructor by creation :
		this.strGsiteswap = new String(strPsiteswap);
		this.bolGsynchro = false;
		this.bytGhighestThrow = Constants.bytS_UNCLASS_NO_VALUE;
		this.intGballsNumber = Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER;
		this.intGthrowsNumber = Constants.bytS_UNCLASS_NO_VALUE;

		this.setStatus(strPsiteswap);
		this.setSymmetric();
	}

	final public byte compare(Siteswap objPsiteswap) {
		if (this.strGsiteswap.equalsIgnoreCase(objPsiteswap.strGsiteswap)) {
			return Constants.bytS_STATE_SITESWAPS_IDENTICAL;
		}

		if (this.bytGstatus != Constants.bytS_STATE_SITESWAP_PLAYABLE || objPsiteswap.bytGstatus != Constants.bytS_STATE_SITESWAP_PLAYABLE
			|| this.intGthrowsNumber != objPsiteswap.intGthrowsNumber) {
			return Constants.bytS_STATE_SITESWAPS_NON_IDENTICAL;
		}

		if (!Arrays.equals(this.intGthrowBallsNumberA, objPsiteswap.intGthrowBallsNumberA)) {
			return Constants.bytS_STATE_SITESWAPS_NON_IDENTICAL;
		}
		for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
			for (int intLthrowBallIndex = 0; intLthrowBallIndex < this.intGthrowBallsNumberA[intLthrowIndex]; ++intLthrowBallIndex) {
				if (this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] != objPsiteswap.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex]) {
					return Constants.bytS_STATE_SITESWAPS_NON_IDENTICAL;
				}
			}
		}

		if (!Arrays.equals(this.bolGspaceAfterA, objPsiteswap.bolGspaceAfterA)) {
			return Constants.bytS_STATE_SITESWAPS_NON_IDENTICAL_SPACES;
		}

		return Constants.bytS_STATE_SITESWAPS_IDENTICAL;
	}

	@SuppressWarnings("unchecked") final public void doReverse() {

		if (this.bytGstatus >= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER) {

			// Initialization of the secondary vectors :
			final ArrayList<Byte>[] bytLreverseSiteswapThrowBallByteALA = new ArrayList[this.intGthrowsNumber];
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				bytLreverseSiteswapThrowBallByteALA[intLthrowIndex] = new ArrayList<Byte>(1);
			}

			// Reverse each secondary vector :
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				for (final byte bytLsiteswap : this.bytGballThrowAA[intLthrowIndex]) {
					if (bytLsiteswap != 0) {
						final int intLreverseBytesVectorIndex =
																(this.intGthrowsNumber
																	- (this.bolGsynchro && bytLsiteswap < 0 ? (intLthrowIndex - bytLsiteswap - 2
																												* (intLthrowIndex % 2) + 1)
																												% this.intGthrowsNumber
																											: (intLthrowIndex + bytLsiteswap)
																												% this.intGthrowsNumber) - 1);

						bytLreverseSiteswapThrowBallByteALA[intLreverseBytesVectorIndex].add(Byte.valueOf(bytLsiteswap));
					}
				}
			}

			// Primary ArrayList initialization :
			final ArrayList<ArrayList<Byte>> bytLreverseSiteswapThrowByteALAL = new ArrayList<ArrayList<Byte>>(this.intGthrowsNumber);

			for (final ArrayList<Byte> bytLreverseSiteswapThrowBallByteAL : bytLreverseSiteswapThrowBallByteALA) {
				bytLreverseSiteswapThrowByteALAL.add(bytLreverseSiteswapThrowBallByteAL);
			}

			// Copy in memory of the ancient data - for the 'space after throw' calculation :
			final byte[][] bytLoldSiteswapAA = new byte[this.intGthrowsNumber][this.bytGballThrowAA[0].length];
			final int[] intLthrowBallsOldNumberA = new int[this.intGthrowsNumber];

			System.arraycopy(this.intGthrowBallsNumberA, 0, intLthrowBallsOldNumberA, 0, this.intGthrowsNumber);
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				for (int intLballIndex = 0; intLballIndex < this.intGthrowBallsNumberA[intLthrowIndex]; ++intLballIndex) {
					bytLoldSiteswapAA[intLthrowIndex][intLballIndex] = this.bytGballThrowAA[intLthrowIndex][intLballIndex];
				}
			}
			final boolean[] bolLspacesAfterOldA = new boolean[this.intGthrowsNumber];
			System.arraycopy(this.bolGspaceAfterA, 0, bolLspacesAfterOldA, 0, this.intGthrowsNumber);

			// Reverse array transformation :
			this.setSiteswapAA(bytLreverseSiteswapThrowByteALAL);
			// Calculation of the 'spaces after doReverse throws' :
			if (this.equals(this.intGthrowsNumber, intLthrowBallsOldNumberA, bytLoldSiteswapAA)) {
				System.arraycopy(bolLspacesAfterOldA, 0, this.bolGspaceAfterA, 0, this.intGthrowsNumber);
			} else {
				Arrays.fill(this.bolGspaceAfterA, false);
				for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
					if (bolLspacesAfterOldA[intLthrowIndex]) {
						if (this.bolGsynchro) {
							for (byte bytLdelta = 0; bytLdelta < 2; ++bytLdelta) {
								if (intLthrowBallsOldNumberA[Tools.getEvenValue(intLthrowIndex) + bytLdelta] == 0) {
									int intLdestinationThrowIndex =
																	Tools.getEvenValue(this.intGthrowsNumber - intLthrowIndex - 3) + intLthrowIndex
																		- Tools.getEvenValue(intLthrowIndex);

									while (intLdestinationThrowIndex < 0) {
										intLdestinationThrowIndex += this.intGthrowsNumber;
									}
									this.bolGspaceAfterA[intLdestinationThrowIndex] = true;
								} else {
									for (int intLballIndex = 0, intLvalue = intLthrowBallsOldNumberA[Tools.getEvenValue(intLthrowIndex) + bytLdelta]; intLballIndex < intLvalue; ++intLballIndex) {
										int intLdestinationThrowIndex =
																		Tools.getEvenValue(this.intGthrowsNumber
																							- intLthrowIndex
																							- Math.abs(bytLoldSiteswapAA[Tools.getEvenValue(intLthrowIndex)
																															+ bytLdelta][intLballIndex])
																							- 3)
																			+ intLthrowIndex - Tools.getEvenValue(intLthrowIndex);
										while (intLdestinationThrowIndex < 0) {
											intLdestinationThrowIndex += this.intGthrowsNumber;
										}
										this.bolGspaceAfterA[intLdestinationThrowIndex] = true;
									}
								}
							}
						} else {
							if (intLthrowBallsOldNumberA[intLthrowIndex] == 0) {
								int intLdestinationThrowIndex = (this.intGthrowsNumber - intLthrowIndex - 2) % this.intGthrowsNumber;

								while (intLdestinationThrowIndex < 0) {
									intLdestinationThrowIndex += this.intGthrowsNumber;
								}
								this.bolGspaceAfterA[intLdestinationThrowIndex] = true;
							} else {
								for (int intLballIndex = 0; intLballIndex < intLthrowBallsOldNumberA[intLthrowIndex]; ++intLballIndex) {
									int intLdestinationThrowIndex =
																	(this.intGthrowsNumber - intLthrowIndex
																		- bytLoldSiteswapAA[intLthrowIndex][intLballIndex] - 2)
																		% this.intGthrowsNumber;

									while (intLdestinationThrowIndex < 0) {
										intLdestinationThrowIndex += this.intGthrowsNumber;
									}
									this.bolGspaceAfterA[intLdestinationThrowIndex] = true;
								}
							}
						}
					}
				}
			}

			// Calculation of the doReverse siteswap string :
			this.strGsiteswap = this.toString();
		}
	}

	final public boolean equals(int intPthrowsNumber, int[] intPthrowBallsNumberA, byte[][] bytPsiteswapAA) {
		if (this.intGthrowsNumber != intPthrowsNumber || this.intGthrowBallsNumberA.length != intPthrowBallsNumberA.length) {
			return false;
		}
		for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
			if (this.intGthrowBallsNumberA[intLthrowIndex] != intPthrowBallsNumberA[intLthrowIndex]) {
				return false;
			}
			for (int intLthrowBallIndex = 0; intLthrowBallIndex < this.intGthrowBallsNumberA[intLthrowIndex]; ++intLthrowBallIndex) {
				if (this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] != bytPsiteswapAA[intLthrowIndex][intLthrowBallIndex]) {
					return false;
				}
			}
		}
		return true;
	}

	final private ArrayList<ArrayList<Byte>> getAsynchroEncodingByteALAL(char[] chrPsiteswapA) {

		// Initializations :
		final Byte bytL_SPACE_Byte = Byte.valueOf(Constants.bytS_ENGINE_THROWS_VALUES_NUMBER);
		final Byte bytL_ZERO_Byte = Byte.valueOf((byte) 0);
		final ArrayList<ArrayList<Byte>> bytLsiteswapByteALAL = new ArrayList<ArrayList<Byte>>(8);

		ArrayList<Byte> bytLthrowByteAL = new ArrayList<Byte>(1);
		boolean bolLspaceBefore = false, bolLspaceAfter = false, bolLmultiplex = false;
		final boolean bolLclosingParenthesis = false;
		this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		this.bytGstatus = Constants.bytS_STATE_SITESWAP_MAYBE_PLAYABLE;

		// Siteswap analysis :
		for (int intLsiteswapCharIndex = 0; intLsiteswapCharIndex < chrPsiteswapA.length; ++intLsiteswapCharIndex) {

			switch (chrPsiteswapA[intLsiteswapCharIndex]) {

				// Quotes :
				case '"':
					break;

				// Separator :
				case ' ':
					if (bytLthrowByteAL.size() == 0) {
						bolLspaceBefore = true;
					} else {
						bolLspaceAfter = true;
					}
					break;

				// Opening parenthesis :
				case '(':
					if (bytLsiteswapByteALAL.size() > 0) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}
					if (bolLclosingParenthesis) {
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}
					break;

				// Starting multiplex :
				case '[':
					if (bolLclosingParenthesis || bolLmultiplex) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}
					if (bytLthrowByteAL.size() > 0) {
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						bolLspaceBefore = bolLspaceAfter = false;
						bytLthrowByteAL = new ArrayList<Byte>(1);
					}
					bolLmultiplex = true;
					break;

				// Ending multiplex :
				case ']':
					if (bolLclosingParenthesis || !bolLmultiplex) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}

					if (bytLthrowByteAL.size() == 0) {
						bytLthrowByteAL.add(bytL_ZERO_Byte);
					}
					bolLmultiplex = false;
					break;

				// Symmetric sequence :
				case '*':
					if (bolLmultiplex && bytLthrowByteAL.size() == 0) {
						bytLthrowByteAL.add(bytL_ZERO_Byte);
					}

					int intLsiteswapByteALALSize = bytLsiteswapByteALAL.size();
					if (bytLthrowByteAL.size() > 0) {
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						++intLsiteswapByteALALSize;
					} else {
						if (intLsiteswapByteALALSize == 0) {
							this.intGerrorCursorIndex = intLsiteswapCharIndex;
							this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
							return null;
						}
						bytLthrowByteAL = bytLsiteswapByteALAL.get(intLsiteswapByteALALSize - 1);
					}

					if (bolLspaceBefore) {
						bytLthrowByteAL.add(0, bytL_SPACE_Byte);
					}
					if (bolLspaceAfter) {
						bytLthrowByteAL.add(bytL_SPACE_Byte);
					}
					bytLsiteswapByteALAL.set(intLsiteswapByteALALSize - 1, bytLthrowByteAL);
					bolLmultiplex = bolLspaceBefore = bolLspaceAfter = false;

					for (int intLthrowIndex = 0, intLthrowsNumber = bytLsiteswapByteALAL.size(); intLthrowIndex < intLthrowsNumber; ++intLthrowIndex) {
						bytLsiteswapByteALAL.add(new ArrayList<Byte>(bytLsiteswapByteALAL.get(intLthrowIndex)));
					}
					bytLthrowByteAL = new ArrayList<Byte>(1);
					break;

				// Closing parenthesis :
				case ')':
					if (bolLclosingParenthesis) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}
					this.intGerrorCursorIndex = intLsiteswapCharIndex;
					break;

				// Throw value :
				default:
					final Byte bytLthrowByte = Tools.getThrowByte(chrPsiteswapA[intLsiteswapCharIndex]);
					if (bytLthrowByte == null) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus =
											(Tools.isSiteswapChar(chrPsiteswapA[intLsiteswapCharIndex])
																										? Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR
																										: Constants.bytS_STATE_SITESWAP_FORBIDDEN_CHAR);
						return null;
					}

					if (!bolLmultiplex && bytLthrowByteAL.size() > 0) {
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						bolLspaceBefore = bolLspaceAfter = false;
						bytLthrowByteAL = new ArrayList<Byte>(1);
					}

					bytLthrowByteAL.add(bytLthrowByte);
					bolLspaceAfter = false;
					break;
			}
		}

		// Adding the potential last throw :
		if (bytLthrowByteAL.size() > 0) {
			if (bolLspaceBefore) {
				bytLthrowByteAL.add(0, bytL_SPACE_Byte);
			}
			if (bolLspaceAfter) {
				bytLthrowByteAL.add(bytL_SPACE_Byte);
			}
			bytLsiteswapByteALAL.add(bytLthrowByteAL);
		} else {
			final int intLsiteswapByteALALSize = bytLsiteswapByteALAL.size();
			if (bolLspaceBefore && intLsiteswapByteALALSize > 0) {
				bytLthrowByteAL = bytLsiteswapByteALAL.get(intLsiteswapByteALALSize - 1);
				bytLthrowByteAL.add(bytL_SPACE_Byte);
				bytLsiteswapByteALAL.set(intLsiteswapByteALALSize - 1, bytLthrowByteAL);
			}
		}

		// Returning value :
		if (bytLsiteswapByteALAL.size() == 0) {
			this.bytGstatus = Constants.bytS_STATE_SITESWAP_EMPTY;
			this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
			return null;
		}

		// Siteswap properties :
		this.bytGstatus = Constants.bytS_STATE_SITESWAP_MAYBE_PLAYABLE;
		this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		return bytLsiteswapByteALAL;
	}

	final public String getInfo() {
		final StringBuilder objLstringBuilder = new StringBuilder();
		objLstringBuilder.append(Strings.doConcat(	"Status             : ",
													this.bytGstatus == Constants.bytS_STATE_SITESWAP_FORBIDDEN_CHAR
																													? "Forbidden char"
																													: this.bytGstatus == Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR
																																														? "Unexpected char"
																																														: this.bytGstatus == Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE
																																																															? "Odd throw value"
																																																															: this.bytGstatus == Constants.bytS_STATE_SITESWAP_EMPTY
																																																																													? "Empty siteswap"
																																																																													: this.bytGstatus == Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER
																																																																																															? "Unknown ball number"
																																																																																															: this.bytGstatus == Constants.bytS_STATE_SITESWAP_UNPLAYABLE
																																																																																																															? "Unplayable"
																																																																																																															: this.bytGstatus == Constants.bytS_STATE_SITESWAP_MAYBE_PLAYABLE
																																																																																																																																? "Maybe playable"
																																																																																																																																: this.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE
																																																																																																																																															? Strings.doConcat(	"Playable (",
																																																																																																																																																				this.bolGsynchro
																																																																																																																																																								? "S"
																																																																																																																																																								: "As",
																																																																																																																																																				"ynchro)")
																																																																																																																																															: "Unknown",
													Strings.strS_LINE_SEPARATOR,
													"Siteswap string    : '",
													this.strGsiteswap,
													'\'',
													Strings.strS_LINE_SEPARATOR));

		if (this.bytGstatus > Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE) {
			final String strLwellFormed = this.toString(true, false);
			if (!strLwellFormed.equals(this.strGsiteswap)) {
				objLstringBuilder.append(Strings.doConcat("Well-formed string : '", strLwellFormed, '\'', Strings.strS_LINE_SEPARATOR));
			}
			objLstringBuilder.append(Strings.doConcat(	"Throw number       : ",
														this.intGthrowsNumber,
														" (theoric : ",
														this.intGrecurrentThrowsNumber,
														')',
														Strings.strS_LINE_SEPARATOR,
														"Ball number        : ",
														this.intGballsNumber < 0 ? "Unknown" : this.intGballsNumber,
														Strings.strS_LINE_SEPARATOR,
														"Symmetric          : ",
														this.bolGsymmetric ? this.toString(true, false, true) : this.bolGsymmetric,
														Strings.strS_LINE_SEPARATOR));
		}
		if (this.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {

			for (int intLsequenceIndex = 0, intLsequencesNumber = this.getSequencesNumber(Constants.intS_GRAPHICS_ANIMATION_DEFAULT_SIZE); intLsequenceIndex < intLsequencesNumber - 1; intLsequenceIndex++) {
				final String strLsequence =
													this.toString(this.intGsequenceA[intLsequenceIndex], this.intGsequenceA[intLsequenceIndex + 1]
																											+ (this.bolGsynchro ? 1 : 0), true, false);
				objLstringBuilder.append(Strings.doConcat(	"Sequence ",
															intLsequenceIndex + 1,
															'/',
															this.intGsequenceA.length - 1,
															"       : '",
															strLsequence,
															"\' (",
															Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(strLsequence),
															')',
															Strings.strS_LINE_SEPARATOR));
			}
		}
		objLstringBuilder.append(Strings.strS_LINE_SEPARATOR);
		return objLstringBuilder.toString();
	}

	final public String getReverseColorsString(String strPcolors) { // TODO : getReverseColorsString
		return strPcolors;
	}

	final public Siteswap getReverseJuggleSiteswap() {
		final Siteswap objLsiteswap = new Siteswap(this);
		objLsiteswap.doReverse();
		return objLsiteswap;
	}

	final public Style getReverseJuggleStyle(Style objPstyle) { // TODO : getReverseJuggleStyle
		return objPstyle;
	}

	final public String getReverseSiteswapString() {
		return this.getReverseSiteswapString(true, false);
	}

	final public String getReverseSiteswapString(boolean bolPspaces, boolean bolPdoubleSpaces) {
		return this.getReverseJuggleSiteswap().toString(bolPspaces, bolPdoubleSpaces);
	}

	final public String getReverseSiteswapString(boolean bolPspaces, boolean bolPdoubleSpaces, boolean bolPstarred) {
		return this.getReverseJuggleSiteswap().toString(bolPspaces, bolPdoubleSpaces, bolPstarred);
	}

	final public int getSequenceIndex(int intPindex, int intPsiteswapWidth) {
		if (this.intGsequenceA == null) {
			this.setSequenceA(intPsiteswapWidth);
		}
		return this.intGsequenceA[Math.max(0, Math.min(this.intGsequenceA.length - 1, intPindex))];
	}

	final public int getSequencesNumber(int intPsiteswapWidth) {
		if (this.intGsequenceA == null) {
			this.setSequenceA(intPsiteswapWidth);
		}
		return this.intGsequenceA.length;
	}

	@SuppressWarnings("unchecked") final private ArrayList<ArrayList<Byte>> getSynchroEncodingByteALAL(char[] chrPsiteswapA) {

		// Initializations :
		final Byte bytL_SPACE_Byte = Byte.valueOf(Constants.bytS_ENGINE_THROWS_VALUES_NUMBER);
		final Byte bytL_ZERO_Byte = Byte.valueOf((byte) 0);
		final byte bytL_STARTING = 0;
		final byte bytL_OPENING_PARENTHESIS = 1;
		final byte bytL_COMMA = 2;
		final byte bytL_CLOSING_PARENTHESIS = 3;

		int intLoddThrowIndex = Constants.bytS_UNCLASS_NO_VALUE;
		byte bytLstate = bytL_STARTING;
		boolean bolLspaceBefore = false, bolLspaceAfter = false, bolLmultiplex = false;

		final ArrayList<ArrayList<Byte>> bytLsiteswapByteALAL = new ArrayList<ArrayList<Byte>>(8);

		ArrayList<Byte> bytLthrowByteAL = new ArrayList<Byte>(1);

		this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		this.bytGstatus = Constants.bytS_STATE_SITESWAP_MAYBE_PLAYABLE;

		// Siteswap analysis :
		for (int intLsiteswapCharIndex = 0; intLsiteswapCharIndex < chrPsiteswapA.length; ++intLsiteswapCharIndex) {

			switch (chrPsiteswapA[intLsiteswapCharIndex]) {

				// Quotes :
				case '"':
					break;

				// Separator :
				case ' ':
					if (bytLthrowByteAL.size() == 0) {
						bolLspaceBefore = true;
					} else {
						bolLspaceAfter = true;
					}
					break;

				// Opening parenthesis :
				case '(':
					if (bytLstate == bytL_OPENING_PARENTHESIS) {
						if (bytLthrowByteAL.size() == 0) {
							this.intGerrorCursorIndex = intLsiteswapCharIndex;
							this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
							return null;
						}
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						bolLspaceBefore = bolLspaceAfter = bolLmultiplex = false;
						bytLthrowByteAL = new ArrayList<Byte>(1);
					}

					if (bytLstate != bytL_STARTING) {
						if (bytLthrowByteAL.size() == 0) {
							bytLthrowByteAL.add(bytL_ZERO_Byte);
						}
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						bolLspaceBefore = bolLspaceAfter = bolLmultiplex = false;
						bytLthrowByteAL = new ArrayList<Byte>(1);
					}
					this.intGerrorCursorIndex = intLsiteswapCharIndex;
					bytLstate = bytL_OPENING_PARENTHESIS;
					break;

				// Comma :
				case ',':
					if (bytLstate == bytL_STARTING || bytLstate == bytL_CLOSING_PARENTHESIS || bytLstate == bytL_COMMA) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}
					if (bytLthrowByteAL.size() == 0) {
						bytLthrowByteAL.add(bytL_ZERO_Byte);
					}
					if (bolLspaceBefore) {
						bytLthrowByteAL.add(0, bytL_SPACE_Byte);
					}
					if (bolLspaceAfter) {
						bytLthrowByteAL.add(bytL_SPACE_Byte);
					}
					bytLsiteswapByteALAL.add(bytLthrowByteAL);
					bolLspaceBefore = bolLspaceAfter = bolLmultiplex = false;
					bytLthrowByteAL = new ArrayList<Byte>(1);
					bytLstate = bytL_COMMA;
					break;

				// Closing parenthesis :
				case ')':
					if (bytLstate == bytL_STARTING || bytLstate == bytL_CLOSING_PARENTHESIS) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}

					if (bytLstate == bytL_OPENING_PARENTHESIS) {
						if (bytLthrowByteAL.size() == 0) {
							bytLthrowByteAL.add(bytL_ZERO_Byte);
						}
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						bolLspaceBefore = bolLspaceAfter = bolLmultiplex = false;
						bytLthrowByteAL = new ArrayList<Byte>(1);
					}
					bytLstate = bytL_CLOSING_PARENTHESIS;
					break;

				// Starting multiplex :
				case '[':
					if (bytLstate == bytL_STARTING) {
						bytLstate = bytL_OPENING_PARENTHESIS;
					}
					if (bolLmultiplex) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}
					if (bytLstate == bytL_CLOSING_PARENTHESIS) {
						if (bytLthrowByteAL.size() == 0) {
							bytLthrowByteAL.add(bytL_ZERO_Byte);
						}
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						bolLspaceBefore = bolLspaceAfter = false;
						bytLthrowByteAL = new ArrayList<Byte>(1);
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						bytLstate = bytL_OPENING_PARENTHESIS;
					}
					bolLspaceAfter = false;
					bolLmultiplex = true;
					break;

				// Ending multiplex :
				case ']':
					if (!bolLmultiplex || bytLstate == bytL_CLOSING_PARENTHESIS || bytLstate == bytL_STARTING) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}
					bolLspaceAfter = false;
					bolLmultiplex = false;
					break;

				// Inverted throws :
				case '*':
					if (bytLstate == bytL_STARTING || bytLstate == bytL_OPENING_PARENTHESIS) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
						return null;
					}

					// Insert the current throw actually in memory in the list :
					if (bytLthrowByteAL.size() > 0) {
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
					}
					bytLsiteswapByteALAL.add(bytLthrowByteAL);

					// Append inverted throws :
					final ArrayList<Byte>[] bytLthrowByteALA = new ArrayList[2];
					for (int intLsiteswapByteALALIndex = 0, intLsiteswapByteALALSize = bytLsiteswapByteALAL.size(); intLsiteswapByteALALIndex < intLsiteswapByteALALSize; intLsiteswapByteALALIndex +=
																																																		2) {
						bytLthrowByteALA[0] = bytLsiteswapByteALAL.get(intLsiteswapByteALALIndex);
						bytLthrowByteALA[1] = bytLsiteswapByteALAL.get(intLsiteswapByteALALIndex + 1);
						final int[] intLthrowBallsNumberA = new int[] { bytLthrowByteALA[0].size(), bytLthrowByteALA[1].size() };

						// Build the first inverted throw :
						final ArrayList<Byte>[] bytLnewThrowByteALA = new ArrayList[2];
						bytLnewThrowByteALA[0] = new ArrayList<Byte>(1);
						bytLnewThrowByteALA[1] = new ArrayList<Byte>(1);
						final int[] intLthrowBallIndexA = new int[] { 0, 0 };

						// Add spaces before :
						for (byte bytLside = 0; bytLside < 2; ++bytLside) {
							while (intLthrowBallIndexA[bytLside] < intLthrowBallsNumberA[bytLside]
									&& bytLthrowByteALA[bytLside].get(intLthrowBallIndexA[bytLside]) == bytL_SPACE_Byte) {
								++intLthrowBallIndexA[bytLside];
							}
							if (intLthrowBallIndexA[bytLside] > 0) {
								bytLnewThrowByteALA[bytLside].add(bytL_SPACE_Byte);
							}
						}

						// Add throw values :
						for (byte bytLside = 0; bytLside < 2; ++bytLside) {
							while (intLthrowBallIndexA[bytLside] < intLthrowBallsNumberA[bytLside]) {
								final Byte bytLthrowByte = bytLthrowByteALA[bytLside].get(intLthrowBallIndexA[bytLside]);
								if (bytLthrowByte != bytL_SPACE_Byte) {
									bytLnewThrowByteALA[bytLside == 0 ? 1 : 0].add(bytLthrowByte);
									++intLthrowBallIndexA[bytLside];
								} else {
									break;
								}
							}
						}

						// Add spaces after :
						for (byte bytLside = 0; bytLside < 2; ++bytLside) {
							if (intLthrowBallIndexA[bytLside] < intLthrowBallsNumberA[bytLside]
								&& bytLthrowByteALA[bytLside].get(intLthrowBallIndexA[bytLside]) == bytL_SPACE_Byte) {
								bytLnewThrowByteALA[bytLside].add(bytL_SPACE_Byte);
							}
						}

						bytLsiteswapByteALAL.add(new ArrayList<Byte>(bytLnewThrowByteALA[0]));
						if (intLsiteswapByteALALIndex == intLsiteswapByteALALSize - 2) {
							bytLthrowByteAL = bytLnewThrowByteALA[1];
						} else {
							bytLsiteswapByteALAL.add(new ArrayList<Byte>(bytLnewThrowByteALA[1]));
						}
					}
					bytLstate = bytL_CLOSING_PARENTHESIS;
					break;

				// Throw value :
				default:
					final Byte bytLthrowByte = Tools.getThrowByte(chrPsiteswapA[intLsiteswapCharIndex]);
					if (bytLthrowByte == null) {
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						this.bytGstatus = Constants.bytS_STATE_SITESWAP_FORBIDDEN_CHAR;
						return null;
					}

					if (bytLstate == bytL_STARTING) {
						bytLstate = bytL_OPENING_PARENTHESIS;
					}
					if (bytLstate == bytL_CLOSING_PARENTHESIS) {
						if (bytLthrowByteAL.size() == 0) {
							bytLthrowByteAL.add(bytL_ZERO_Byte);
						}
						if (bolLspaceBefore) {
							bytLthrowByteAL.add(0, bytL_SPACE_Byte);
						}
						if (bolLspaceAfter) {
							bytLthrowByteAL.add(bytL_SPACE_Byte);
						}
						bytLsiteswapByteALAL.add(bytLthrowByteAL);
						bolLspaceBefore = bolLspaceAfter = bolLmultiplex = false;
						bytLthrowByteAL = new ArrayList<Byte>(1);
						this.intGerrorCursorIndex = intLsiteswapCharIndex;
						bytLstate = bytL_OPENING_PARENTHESIS;
					}

					final byte bytLthrow = bytLthrowByte.byteValue();
					if ((bytLthrow & 1) == 1) {
						if (bytLthrow != ('X' - ('A' - 10)) || bytLthrowByteAL.size() == 0) {
							intLoddThrowIndex = intLsiteswapCharIndex;
							bytLthrowByteAL.add(bytLthrowByte);
						} else {
							bytLthrowByteAL.set(bytLthrowByteAL.size() - 1, Byte.valueOf((byte) -bytLthrowByteAL.get(bytLthrowByteAL.size() - 1)
																												.byteValue()));
						}
					} else {
						bytLthrowByteAL.add(bytLthrowByte);
					}
					bolLspaceAfter = false;
					break;
			}
		}

		// Adding the last throw :
		if (bytLstate == bytL_OPENING_PARENTHESIS) {
			if (bytLthrowByteAL.size() == 0) {
				this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR;
				return null;
			}
			if (bolLspaceBefore) {
				bytLthrowByteAL.add(0, bytL_SPACE_Byte);
			}
			if (bolLspaceAfter) {
				bytLthrowByteAL.add(bytL_SPACE_Byte);
			}
			bytLsiteswapByteALAL.add(bytLthrowByteAL);
			bolLspaceBefore = bolLspaceAfter = false;
			bytLthrowByteAL = new ArrayList<Byte>(1);
		}

		if (bytLthrowByteAL.size() == 0) {
			bytLthrowByteAL.add(bytL_ZERO_Byte);
		}
		if (bolLspaceBefore) {
			bytLthrowByteAL.add(0, bytL_SPACE_Byte);
		}
		if (bolLspaceAfter) {
			bytLthrowByteAL.add(bytL_SPACE_Byte);
		}
		bytLsiteswapByteALAL.add(bytLthrowByteAL);

		// Returning value :
		if (bytLsiteswapByteALAL.size() == 0) {
			this.bytGstatus = Constants.bytS_STATE_SITESWAP_EMPTY;
			this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
			return null;
		}

		// Siteswap properties :
		if (intLoddThrowIndex >= 0) {
			this.bytGstatus = Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE;
			this.intGerrorCursorIndex = intLoddThrowIndex;
		} else {
			this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		}
		return bytLsiteswapByteALAL;
	}

	final public String getUsefulColorsString(String strPcolors) {

		final int intLcolorsStringLength = strPcolors.length();
		if (intLcolorsStringLength == 0) {
			return new String();
		}

		int intLusefulColorsNumber = this.intGballsNumber;
		if (intLusefulColorsNumber <= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER) {
			intLusefulColorsNumber = intLcolorsStringLength;
		}

		final StringBuilder objLusefulColorsStringBuilder = new StringBuilder(strPcolors.length() + intLusefulColorsNumber);
		objLusefulColorsStringBuilder.append(strPcolors);

		while (objLusefulColorsStringBuilder.length() < intLusefulColorsNumber) {
			objLusefulColorsStringBuilder.append(objLusefulColorsStringBuilder);
		}
		return objLusefulColorsStringBuilder.substring(0, intLusefulColorsNumber);
	}

	final public boolean isNoCatch(int intPcatchIndex) {
		final int intLcatchIndex = intPcatchIndex % this.intGthrowsNumber;
		for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
			if (Math.abs(intLcatchIndex - intLthrowIndex) > this.bytGhighestThrow + (this.bolGsynchro ? 1 : 0)) {
				continue;
			}
			for (int intLthrowBallIndex = 0; intLthrowBallIndex < this.intGthrowBallsNumberA[intLthrowIndex]; ++intLthrowBallIndex) {
				if (this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] != 0 || this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] != 2) {
					if (this.bolGsynchro) {
						if (intLcatchIndex == (intLthrowIndex + Math.abs(this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex]) + (this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] < 0
																																																	? Tools.getSignedBoolean((intLthrowIndex & 1) == 0)
																																																	: 0))
												% this.intGthrowsNumber) {
							return false;
						}
					} else {
						if ((intLthrowIndex + this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex]) % this.intGthrowsNumber == intPcatchIndex) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	final public boolean isNoThrow(int intPthrowIndex) {
		final int intLthrowIndex = intPthrowIndex % this.intGthrowsNumber;
		for (int intLthrowBallIndex = 0; intLthrowBallIndex < this.intGthrowBallsNumberA[intLthrowIndex]; ++intLthrowBallIndex) {
			if (this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] != 0 && this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] != 2) {
				return false;
			}
		}
		return true;
	}

	final private boolean isPlayable() {

		this.intGballsNumber = 0;
		this.bytGhighestThrow = 0;

		for (final byte[] bytLsiteswapA : this.bytGballThrowAA) {
			for (final byte bytLsiteswap : bytLsiteswapA) {
				this.intGballsNumber += Math.abs(bytLsiteswap);
				this.bytGhighestThrow = (byte) Math.max(this.bytGhighestThrow, Math.abs(bytLsiteswap));
			}
		}
		if (this.intGballsNumber % this.intGthrowsNumber != 0) {
			this.intGballsNumber = Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER;
			this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER;
			return false;
		}

		this.intGballsNumber /= this.intGthrowsNumber;
		this.intGpermutationA = new int[this.intGthrowsNumber + Math.max(this.bytGhighestThrow, 3)];
		Arrays.fill(this.intGpermutationA, 0);

		for (int intLballIndex = 0; intLballIndex <= this.intGballsNumber; ++intLballIndex) {
			int intLindex1;

			for (intLindex1 = 0; intLindex1 < this.intGthrowsNumber + this.bytGhighestThrow
									&& this.intGpermutationA[intLindex1] == this.intGthrowBallsNumberA[intLindex1 % this.intGthrowsNumber]; ++intLindex1) {
				;
			}
			if (intLballIndex == this.intGballsNumber) {
				if (intLindex1 != this.intGthrowsNumber + this.bytGhighestThrow) {
					this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNPLAYABLE;
					return false;
				}
				break;
			}
			while (intLindex1 < this.intGthrowsNumber + this.bytGhighestThrow) {
				if (this.intGpermutationA[intLindex1] == this.intGthrowBallsNumberA[intLindex1 % this.intGthrowsNumber]) {
					this.bytGstatus = Constants.bytS_STATE_SITESWAP_UNPLAYABLE;
					return false;
				}
				++this.intGpermutationA[intLindex1];

				final int intLindex2 =
										this.bytGballThrowAA[intLindex1 % this.intGthrowsNumber][this.intGthrowBallsNumberA[intLindex1
																															% this.intGthrowsNumber]
																									- this.intGpermutationA[intLindex1]];

				intLindex1 += (this.bolGsynchro && intLindex2 < 0 ? Tools.getSignedBoolean(intLindex1 % 2 == 0) - intLindex2 : intLindex2);
			}
		}
		this.bytGstatus = Constants.bytS_STATE_SITESWAP_PLAYABLE;
		return true;
	}

	final public boolean isStarred() {
		return this.bolGsymmetric && this.strGsiteswap.equals(this.toString(true, false, true));
	}

	final public void setSequenceA(int intPsiteswapWidth) {

		if (this.bytGstatus < Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER) {
			return;
		}
		final int intL_DISPLAYABLE_LENGTH = 3 * intPsiteswapWidth / 4;

		this.intGthrowLengthA = new int[this.intGthrowsNumber];

		int intLthrowMaxLength = 0;
		int intLthrowsTotalLength = 0;
		final byte bytLbooleanSynchro = this.bolGsynchro ? (byte) 1 : (byte) 0;

		// ERREUR ICI !!! En fait, il faudrait pouvoir initialiser une variable static objGfontMetrics dans
		// JuggleSiteswap
		// à partir de la classe JuggleMasterProJFrame, avant de l'utiliser ici...

		// JPanel jpanel = new JPanel();
		// java.awt.Graphics g = jpanel.getGraphics();
		// g = new java.awt.Graphics();
		// FontMetrics objLfontMetrics = java.awt.Font.getFont((String) null).getFontMetrics();
		// FontMetrics objLfontMetrics = new
		// JPanel().getGraphics().getFontMetrics(java.awt.Font.getFont(Constants.strS_UNCLASS_ARNAUD_BELO));

		for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; intLthrowIndex += bytLbooleanSynchro + 1) {
			this.intGthrowLengthA[intLthrowIndex] =
													Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(this.toString(	intLthrowIndex,
																													intLthrowIndex
																														+ bytLbooleanSynchro,
																													true,
																													true));
			if (this.bolGsynchro) {
				this.intGthrowLengthA[intLthrowIndex + 1] = 0;
			}
			intLthrowMaxLength = Math.max(intLthrowMaxLength, this.intGthrowLengthA[intLthrowIndex]);
			intLthrowsTotalLength += this.intGthrowLengthA[intLthrowIndex];
		}

		int intLsequencesNumber = 1;

		while ((float) intLthrowsTotalLength / (float) intL_DISPLAYABLE_LENGTH > intLsequencesNumber) {
			++intLsequencesNumber;
			if (intLthrowMaxLength < intL_DISPLAYABLE_LENGTH) {
				intLthrowsTotalLength += intLthrowMaxLength;
			}
		}

		ArrayList<Integer> objLsequenceIntegerAL;

		while (true) {
			final int intLsequenceAverageLength = Math.max(intLthrowsTotalLength / intLsequencesNumber, 2 * intL_DISPLAYABLE_LENGTH / 3);
			int intLspacedThrowIndex = Constants.bytS_UNCLASS_NO_VALUE;
			int intLsequenceLength = this.intGthrowLengthA[0];
			int intLsequenceThrowsNumber = (this.bolGsynchro ? (this.intGthrowsNumber == 2 ? 1 : 0) : (this.intGthrowsNumber == 1 ? 1 : 0));

			objLsequenceIntegerAL = new ArrayList<Integer>(intLsequencesNumber + 1);
			objLsequenceIntegerAL.add(Integer.valueOf(0));

			for (int intLthrowIndex = 1 + bytLbooleanSynchro; intLthrowIndex < this.intGthrowsNumber; intLthrowIndex += 1 + bytLbooleanSynchro) {
				if (intLsequenceLength + this.intGthrowLengthA[intLthrowIndex] < intLsequenceAverageLength
					|| intLsequenceLength + this.intGthrowLengthA[intLthrowIndex] == intLsequenceAverageLength && intLsequencesNumber == 1) {

					++intLsequenceThrowsNumber;
					intLsequenceLength += this.intGthrowLengthA[intLthrowIndex];

					if (this.bolGspaceAfterA[intLthrowIndex + bytLbooleanSynchro] && intLsequenceLength > intLsequenceAverageLength / 2) {
						intLspacedThrowIndex = intLthrowIndex;
					}

					continue;
				}

				if (intLsequenceThrowsNumber == 0) {
					++intLsequenceThrowsNumber;
					intLsequenceLength += this.intGthrowLengthA[intLthrowIndex];
				} else {
					objLsequenceIntegerAL.add(Integer.valueOf(intLspacedThrowIndex == Constants.bytS_UNCLASS_NO_VALUE ? intLthrowIndex
																														: intLspacedThrowIndex));
					intLsequenceLength =
											this.intGthrowLengthA[(intLspacedThrowIndex == Constants.bytS_UNCLASS_NO_VALUE ? intLthrowIndex
																															: intLspacedThrowIndex)];
					intLsequenceThrowsNumber = 0;
					if (intLspacedThrowIndex != Constants.bytS_UNCLASS_NO_VALUE) {
						intLthrowIndex = intLspacedThrowIndex;
						intLspacedThrowIndex = Constants.bytS_UNCLASS_NO_VALUE;
					}
				}
			}
			if (intLsequenceThrowsNumber > 0) {
				objLsequenceIntegerAL.add(Integer.valueOf(this.intGthrowsNumber - bytLbooleanSynchro - 1));
			}
			if (objLsequenceIntegerAL.size() <= intLsequencesNumber + 1) {
				break;
			}
			++intLsequencesNumber;
			intLthrowsTotalLength += intLthrowMaxLength;
		}

		this.intGsequenceA = new int[objLsequenceIntegerAL.size()];
		for (int intLsequenceIndex = 0; intLsequenceIndex < this.intGsequenceA.length; ++intLsequenceIndex) {
			this.intGsequenceA[intLsequenceIndex] = objLsequenceIntegerAL.get(intLsequenceIndex).intValue();
		}

		this.intGsequencesMaxLength = 0;
		for (int intLsequenceIndex = 0; intLsequenceIndex < this.intGsequenceA.length - 1; ++intLsequenceIndex) {
			this.intGsequencesMaxLength =
											Math.max(	this.intGsequencesMaxLength,
														Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(this.toString(	this.intGsequenceA[intLsequenceIndex],
																														this.intGsequenceA[intLsequenceIndex + 1]
																															+ (this.bolGsynchro ? 1
																																				: 0),
																														true,
																														true)));
		}
	}

	final private void setSiteswapAA(ArrayList<ArrayList<Byte>> bytPsiteswapByteALAL) {

		// Null Siteswap :
		if (bytPsiteswapByteALAL == null) {
			this.bytGstatus = Constants.bytS_STATE_SITESWAP_EMPTY;
			this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		} else {
			// Initializations :
			int intLthrowBallsMaxNumber = 1;
			this.intGthrowsNumber = bytPsiteswapByteALAL.size();
			this.intGthrowBallsNumberA = new int[this.intGthrowsNumber];
			Arrays.fill(this.intGthrowBallsNumberA, 0);
			this.bolGspaceAfterA = new boolean[this.intGthrowsNumber];
			Arrays.fill(this.bolGspaceAfterA, false);

			// Determining 'space after throw' boolean & array final dimension :
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				final ArrayList<Byte> bytLthrowBallByteAL = bytPsiteswapByteALAL.get(intLthrowIndex);
				boolean bolLbeforeThrow = true;
				for (int intLthrowBallIndex = 0; intLthrowBallIndex < bytLthrowBallByteAL.size(); ++intLthrowBallIndex) {
					switch (bytLthrowBallByteAL.get(intLthrowBallIndex).byteValue()) {

						// Space :
						case Constants.bytS_ENGINE_THROWS_VALUES_NUMBER:
							bytLthrowBallByteAL.remove(intLthrowBallIndex);
							--intLthrowBallIndex;
							this.bolGspaceAfterA[(intLthrowIndex + this.intGthrowsNumber - (bolLbeforeThrow ? 1 : 0)) % this.intGthrowsNumber] = true;
							break;

						// Throw :
						case 0:
							bolLbeforeThrow = false;
							bytLthrowBallByteAL.remove(intLthrowBallIndex);
							--intLthrowBallIndex;
							break;

						default:
							bolLbeforeThrow = false;
							++this.intGthrowBallsNumberA[intLthrowIndex];
					}
				}

				if (bytLthrowBallByteAL.size() == 0) {
					bytLthrowBallByteAL.add(Byte.valueOf((byte) 0));
				}
				intLthrowBallsMaxNumber = Math.max(intLthrowBallsMaxNumber, this.intGthrowBallsNumberA[intLthrowIndex]);
			}

			// Building array :
			this.bytGballThrowAA = new byte[this.intGthrowsNumber][intLthrowBallsMaxNumber];
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				final ArrayList<Byte> bytLthrowBallByteAL = bytPsiteswapByteALAL.get(intLthrowIndex);
				for (int intLthrowBallIndex = 0, intLthrowBallsNumber = bytLthrowBallByteAL.size(); intLthrowBallIndex < intLthrowBallsNumber; ++intLthrowBallIndex) {
					this.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex] = bytLthrowBallByteAL.get(intLthrowBallIndex).byteValue();
				}
			}

			// Setting recurrent throw number :
			this.intGrecurrentThrowsNumber = this.intGthrowsNumber;
			boolean bolLrecurrence = false;
			for (int intLrecurrentFactor = this.intGthrowsNumber; !bolLrecurrence && intLrecurrentFactor > 1; --intLrecurrentFactor) {
				if (this.intGthrowsNumber % intLrecurrentFactor == 0) {
					final int intLperiodLength = this.intGthrowsNumber / intLrecurrentFactor;
					bolLrecurrence = true;
					for (int intLperiodIndex = 0; bolLrecurrence && intLperiodIndex < intLperiodLength; ++intLperiodIndex) {
						final int intLthrowBallsNumber = this.intGthrowBallsNumberA[intLperiodIndex];
						for (int intLthrowIndex = intLperiodIndex + intLperiodLength; bolLrecurrence && intLthrowIndex < this.intGthrowsNumber; intLthrowIndex +=
																																									intLperiodLength) {
							if (this.intGthrowBallsNumberA[intLthrowIndex] != intLthrowBallsNumber) {
								bolLrecurrence = false;
							}
						}
						for (int intLballThrowIndex = 0; intLballThrowIndex < intLthrowBallsNumber; ++intLballThrowIndex) {
							final byte bytLthrow = this.bytGballThrowAA[intLperiodIndex][intLballThrowIndex];
							for (int intLthrowIndex = intLperiodIndex + intLperiodLength; bolLrecurrence && intLthrowIndex < this.intGthrowsNumber; intLthrowIndex +=
																																										intLperiodLength) {
								if (this.bytGballThrowAA[intLthrowIndex][intLballThrowIndex] != bytLthrow) {
									bolLrecurrence = false;
								}
							}
						}
					}
					if (bolLrecurrence) {
						this.intGrecurrentThrowsNumber = this.intGthrowsNumber / intLrecurrentFactor;
						break;
					}
				}
			}
			if (this.bolGsynchro && this.intGrecurrentThrowsNumber == 1) {
				this.intGrecurrentThrowsNumber = 2;
			}
		}
	}

	final public void setSpaceAfterA(Siteswap objPsiteswap) {
		System.arraycopy(objPsiteswap.bolGspaceAfterA, 0, this.bolGspaceAfterA, 0, this.intGthrowsNumber);
		this.intGsequenceA = null;
	}

	final private void setStatus(String strPsiteswap) {

		ArrayList<ArrayList<Byte>> bytLByteALAL;

		// Default values :
		this.bolGsymmetric = false;

		// Empty siteswap :
		if (strPsiteswap.trim().length() == 0) {
			this.bytGstatus = Constants.bytS_STATE_SITESWAP_EMPTY;
			this.intGerrorCursorIndex = Constants.bytS_UNCLASS_NO_VALUE;
		} else {
			final char[] chrLsiteswapA = strPsiteswap.toCharArray();

			// Synchro & asynchro :
			if (chrLsiteswapA[0] == '(') {
				bytLByteALAL = this.getSynchroEncodingByteALAL(chrLsiteswapA);
				if (bytLByteALAL == null) {
					final byte bytLstatus = this.bytGstatus;
					final int intLerrorIndex = this.intGerrorCursorIndex;
					bytLByteALAL = this.getAsynchroEncodingByteALAL(chrLsiteswapA);
					if (bytLByteALAL == null) {
						if (bytLstatus > this.bytGstatus) {
							this.bytGstatus = bytLstatus;
							this.intGerrorCursorIndex = intLerrorIndex;
							this.bolGsynchro = true;
						} else {
							this.bolGsynchro = false;
						}
					} else {
						this.bolGsynchro = false;
					}
				} else {
					this.bolGsynchro = true;
				}

				// Asynchro & synchro :
			} else {
				bytLByteALAL = this.getAsynchroEncodingByteALAL(chrLsiteswapA);
				if (bytLByteALAL == null) {
					final byte bytLstatus = this.bytGstatus;
					final int intLerrorIndex = this.intGerrorCursorIndex;
					bytLByteALAL = this.getSynchroEncodingByteALAL(chrLsiteswapA);
					if (bytLByteALAL == null) {
						if (bytLstatus >= this.bytGstatus) {
							this.bytGstatus = bytLstatus;
							this.intGerrorCursorIndex = intLerrorIndex;
							this.bolGsynchro = false;
						} else {
							this.bolGsynchro = true;
						}
					} else {
						this.bolGsynchro = true;
					}
				} else {
					this.bolGsynchro = false;
				}
			}

			// Siteswap validation :
			switch (this.bytGstatus) {
				case Constants.bytS_STATE_SITESWAP_FORBIDDEN_CHAR:
				case Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR:
				case Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE:
					this.intGballsNumber = Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER;
					break;
				case Constants.bytS_STATE_SITESWAP_EMPTY:
					this.intGballsNumber = 0;
					break;
				case Constants.bytS_STATE_SITESWAP_MAYBE_PLAYABLE:
					this.setSiteswapAA(bytLByteALAL);
					this.isPlayable();
			}
		}
	}

	final public void setSymmetric() {
		switch (this.bytGstatus) {
			case Constants.bytS_STATE_SITESWAP_FORBIDDEN_CHAR:
			case Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR:
			case Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE:
			case Constants.bytS_STATE_SITESWAP_EMPTY:
				return;

			case Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER:
			case Constants.bytS_STATE_SITESWAP_UNPLAYABLE:
			case Constants.bytS_STATE_SITESWAP_MAYBE_PLAYABLE:
			case Constants.bytS_STATE_SITESWAP_PLAYABLE:
				if (this.bolGsynchro) {
					if (this.intGthrowsNumber >= 4 && this.intGthrowsNumber % 4 == 0) {
						for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber / 2; intLthrowIndex += 2) {
							if (this.bolGspaceAfterA[intLthrowIndex] != this.bolGspaceAfterA[intLthrowIndex + this.intGthrowsNumber / 2]
								|| this.intGthrowBallsNumberA[intLthrowIndex] != this.intGthrowBallsNumberA[intLthrowIndex + this.intGthrowsNumber
																											/ 2 + 1]
								|| this.intGthrowBallsNumberA[intLthrowIndex + 1] != this.intGthrowBallsNumberA[intLthrowIndex
																												+ this.intGthrowsNumber / 2]
								|| !Arrays.equals(this.bytGballThrowAA[intLthrowIndex], this.bytGballThrowAA[intLthrowIndex + this.intGthrowsNumber
																												/ 2 + 1])
								|| !Arrays.equals(this.bytGballThrowAA[intLthrowIndex + 1], this.bytGballThrowAA[intLthrowIndex
																													+ this.intGthrowsNumber / 2])) {
								return;
							}
						}
					} else {
						return;
					}
				} else if (this.intGthrowsNumber >= 2 && this.intGthrowsNumber % 2 == 0) {
					for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber / 2; ++intLthrowIndex) {
						if (this.bolGspaceAfterA[intLthrowIndex] != this.bolGspaceAfterA[intLthrowIndex + this.intGthrowsNumber / 2]
							|| this.intGthrowBallsNumberA[intLthrowIndex] != this.intGthrowBallsNumberA[intLthrowIndex + this.intGthrowsNumber / 2]
							|| !Arrays.equals(this.bytGballThrowAA[intLthrowIndex], this.bytGballThrowAA[intLthrowIndex + this.intGthrowsNumber / 2])) {
							return;
						}
					}
				} else {
					return;
				}
				this.bolGsymmetric = true;
				// this.bolGstarred = this.strGsiteswap.equals(this.toString(true, false, true));
		}
	}

	@Override final public String toString() {
		return this.toString(true, false, false);
	}

	final public String toString(boolean bolPspaces, boolean bolPdoubleSpaces) {
		return this.toString(bolPspaces, bolPdoubleSpaces, false);
	}

	final public String toString(boolean bolPspaces, boolean bolPdoubleSpaces, boolean bolPstarred) {
		String strLsiteswap = this.strGsiteswap; // Constants.strS_UNCLASS_EMPTY;
		if (this.bytGstatus >= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER) {
			if (bolPstarred && this.bolGsymmetric) {
				strLsiteswap = Strings.doConcat(this.toString(0, this.intGthrowsNumber / 2 - 1, bolPspaces, bolPdoubleSpaces), '*');
			} else {
				strLsiteswap = this.toString(0, this.intGthrowsNumber - 1, bolPspaces, bolPdoubleSpaces);
			}
		}
		return strLsiteswap;
	}

	final public String toString(int intPthrowIndex, boolean bolPspaces, boolean bolPdoubleSpaces) {

		if (this.bytGstatus < Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER || intPthrowIndex >= this.intGthrowsNumber || intPthrowIndex < 0) {
			return Strings.strS_EMPTY;
		}

		// Before 'throw value(s)' :
		final StringBuilder objLtoStringBuilder = new StringBuilder(16);
		if (this.bolGsynchro && ((intPthrowIndex & 1) == 0)) {
			objLtoStringBuilder.append('(');
		}

		// Throw value(s) :
		if (this.intGthrowBallsNumberA[intPthrowIndex] == 0) {
			objLtoStringBuilder.append('0');
		} else {
			if (this.intGthrowBallsNumberA[intPthrowIndex] > 1) {
				objLtoStringBuilder.append('[');
			}
			for (int intLballIndex = 0; intLballIndex < this.intGthrowBallsNumberA[intPthrowIndex]; ++intLballIndex) {
				objLtoStringBuilder.append(Tools.getThrowValueString(this.bytGballThrowAA[intPthrowIndex][intLballIndex]));
			}
			if (this.intGthrowBallsNumberA[intPthrowIndex] > 1) {
				objLtoStringBuilder.append(']');
			}
		}

		// After 'throw value(s)' :
		if (this.bolGsynchro) {
			objLtoStringBuilder.append((intPthrowIndex & 1) == 0 ? ',' : ')');
		}
		if (this.bolGspaceAfterA[intPthrowIndex] && bolPspaces) {
			objLtoStringBuilder.append(' ');
			if (bolPdoubleSpaces) {
				objLtoStringBuilder.append(' ');
			}
		}

		objLtoStringBuilder.trimToSize();
		return objLtoStringBuilder.toString();
	}

	final private String toString(int intPbeginningThrowIndex, int intPendingThrowIndex, boolean bolPspaces, boolean bolPdoubleSpaces) {

		// intPbeginningThrowIndex inclusive & intPendingThrowIndex exclusive
		final StringBuilder objLtoStringBuilder = new StringBuilder((intPendingThrowIndex - intPbeginningThrowIndex) * (this.bolGsynchro ? 5 : 2));

		for (int intPthrowIndex = intPbeginningThrowIndex; intPthrowIndex <= intPendingThrowIndex; ++intPthrowIndex) {
			if (intPthrowIndex == this.intGthrowsNumber) {
				break;
			}
			objLtoStringBuilder.append(this.toString(intPthrowIndex, bolPspaces, bolPdoubleSpaces));
		}
		objLtoStringBuilder.trimToSize();
		return objLtoStringBuilder.toString();
	}

	final public static String getBallsNumberString(int intPballsNumber, boolean bolPlongFormat) {
		return ((intPballsNumber == Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER)
																						? "?"
																						: ((bolPlongFormat && (intPballsNumber > 99)) || (!bolPlongFormat && (intPballsNumber > 9)))
																																													? ">"
																																													: Integer.toString(intPballsNumber));
	}

	// final private static void log(Object... objPobjectA) {
	// if (Constants.bolS_UNCLASS_LOG) {
	// JuggleTools.log(JuggleTools.doConcat(objPobjectA, objPobjectA != null ? ' ' : null, JuggleTools.getTraceString()));
	// }
	// }
	//
	// @SuppressWarnings("unused") final private static void logIn() {
	// JuggleSiteswap.log("In  :");
	// }
	//
	// @SuppressWarnings("unused") final private static void logOut() {
	// JuggleSiteswap.log("Out :");
	// }
}

/*
 * @(#)Constants.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
