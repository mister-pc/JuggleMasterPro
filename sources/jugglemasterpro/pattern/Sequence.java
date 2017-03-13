/*
 * @(#)Sequence.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Sequence {

	/**
	 * Constructs
	 * 
	 * @param objPsequence
	 * @param bolPnotSymmetric
	 */
	private Sequence(Sequence objPsequence, boolean bolPnotSymmetric) {

		this.bolGsynchro = objPsequence.bolGsynchro;
		this.bolGstatus = objPsequence.bolGstatus;

		if (this.bolGstatus) {
			this.intGthrowsNumber = objPsequence.intGthrowsNumber;
			this.intGthrowChoiceNumberA = new int[this.intGthrowsNumber];

			this.intGthrowChoiceBallNumberAA = new int[this.intGthrowsNumber][objPsequence.intGthrowChoiceBallNumberAA[0].length];
			this.bytGthrowAAA =
								new byte[this.intGthrowsNumber][objPsequence.intGthrowChoiceBallNumberAA[0].length][objPsequence.bytGthrowAAA[0][0].length];
		}

		if (objPsequence.bolGstatus && objPsequence.bolGsynchro && !bolPnotSymmetric) {

			// Constructor by symmetrics :
			this.strGsequence = null;
			if (this.bolGstatus) {
				for (int intLthrowIndex = 0; intLthrowIndex < objPsequence.intGthrowsNumber; ++intLthrowIndex) {
					final int intLsymmetricThrowIndex = intLthrowIndex + 1 - 2 * (intLthrowIndex % 2);
					this.intGthrowChoiceNumberA[intLsymmetricThrowIndex] = objPsequence.intGthrowChoiceNumberA[intLthrowIndex];
					for (int intLthrowChoiceIndex = 0; intLthrowChoiceIndex < objPsequence.intGthrowChoiceNumberA[intLthrowIndex]; ++intLthrowChoiceIndex) {
						this.intGthrowChoiceBallNumberAA[intLsymmetricThrowIndex][intLthrowChoiceIndex] =
																											objPsequence.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex];
						for (int intLthrowChoiceBallIndex = 0; intLthrowChoiceBallIndex < objPsequence.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex]; ++intLthrowChoiceBallIndex) {
							this.bytGthrowAAA[intLsymmetricThrowIndex][intLthrowChoiceIndex][intLthrowChoiceBallIndex] =
																															objPsequence.bytGthrowAAA[intLthrowIndex][intLthrowChoiceIndex][intLthrowChoiceBallIndex];
						}
					}
				}
			}
		} else {

			// Constructor by copy :
			this.strGsequence = new String(objPsequence.strGsequence);
			if (this.bolGstatus) {
				System.arraycopy(objPsequence.intGthrowChoiceNumberA, 0, this.intGthrowChoiceNumberA, 0, this.intGthrowsNumber);
				for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
					System.arraycopy(	objPsequence.intGthrowChoiceBallNumberAA[intLthrowIndex],
										0,
										this.intGthrowChoiceBallNumberAA[intLthrowIndex],
										0,
										this.intGthrowChoiceNumberA[intLthrowIndex]);
					for (int intLthrowChoiceIndex = 0; intLthrowChoiceIndex < this.intGthrowChoiceNumberA[intLthrowIndex]; ++intLthrowChoiceIndex) {
						System.arraycopy(	objPsequence.bytGthrowAAA[intLthrowIndex][intLthrowChoiceIndex],
											0,
											this.bytGthrowAAA[intLthrowIndex][intLthrowChoiceIndex],
											0,
											this.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex]);
					}
				}
			}
		}
	}

	/**
	 * Constructs
	 * 
	 * @param strPsequence
	 * @param bolPsynchro
	 */
	private Sequence(String strPsequence, boolean bolPsynchro) {
		this.strGsequence = new String(strPsequence);
		this.bolGsynchro = bolPsynchro;
		this.intGerrorIndex = Constants.bytS_UNCLASS_NO_VALUE;

		final ArrayList<ArrayList<ArrayList<Byte>>> bytLsequenceByteALALAL =
																				this.bolGsynchro
																								? this.getSynchroSequenceByteALALAL(this.strGsequence)
																								: this.getAsynchroSequenceByteALALAL(this.strGsequence);
		this.bolGstatus = (bytLsequenceByteALALAL != null);
		if (this.bolGstatus) {
			this.setSequenceAAA(bytLsequenceByteALALAL);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPsequence
	 * @param bolPsynchro
	 * @return
	 */
	final public static Sequence getSequence(String strPsequence, boolean bolPsynchro) {
		return (!Tools.isEmpty(Strings.getTrimmedString(strPsequence)) ? new Sequence(strPsequence, bolPsynchro) : null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPthrowsSubsequenceA
	 * @param bytPthrowsSequenceA
	 * @return
	 */
	final private static boolean isPartOf(byte[] bytPsequenceThrowBallA, byte[] bytPsiteswapThrowBallA) {

		if (bytPsequenceThrowBallA == null || bytPsequenceThrowBallA.length == 0) {
			return true;
		}
		if (bytPsiteswapThrowBallA == null || bytPsiteswapThrowBallA.length == 0) {
			return false;
		}

		// Delete unused 'zeros' inside the siteswap throws :
		byte[] bytLsiteswapThrowBallA = null;
		final int intLsiteswapThrowBallsNumber = bytPsiteswapThrowBallA.length;
		int intLzerosNumber = 0;
		for (final byte bytLthrowValue : bytPsiteswapThrowBallA) {
			if (bytLthrowValue == 0) {
				++intLzerosNumber;
			}
		}
		if (intLzerosNumber == intLsiteswapThrowBallsNumber) {
			bytLsiteswapThrowBallA = new byte[] { 0 };
		} else {
			bytLsiteswapThrowBallA = new byte[intLsiteswapThrowBallsNumber - intLzerosNumber];
			int intLsiteswapIndex = 0;
			for (final byte bytLthrowValue : bytPsiteswapThrowBallA) {
				if (bytLthrowValue != 0) {
					bytLsiteswapThrowBallA[intLsiteswapIndex] = bytLthrowValue;
					++intLsiteswapIndex;
				}
			}
		}

		// Tools.verbose("Tentative de mappage du lancé : ", bytLsiteswapThrowBallA);
		// Tools.verbose("Avec la séquence              : ", bytPsequenceThrowBallA);

		// Check throws :
		final boolean[] bolLsiteswapMark = new boolean[bytLsiteswapThrowBallA.length];
		Arrays.fill(bolLsiteswapMark, false);
		for (int intLsequenceThrowBallIndex = 0; intLsequenceThrowBallIndex < bytPsequenceThrowBallA.length; ++intLsequenceThrowBallIndex) {
			boolean bolLfound = false;
			for (int intLsiteswapThrowBallIndex = 0; !bolLfound && intLsiteswapThrowBallIndex < bytLsiteswapThrowBallA.length; ++intLsiteswapThrowBallIndex) {
				if (!bolLsiteswapMark[intLsiteswapThrowBallIndex]) {
					switch (bytPsequenceThrowBallA[intLsequenceThrowBallIndex]) {
						case Constants.bytS_ENGINE_ANY_NON_NULL_THROW_VALUE:
							if (bytLsiteswapThrowBallA[intLsiteswapThrowBallIndex] != 0) {
								bolLsiteswapMark[intLsiteswapThrowBallIndex] = true;
								bolLfound = true;
							}
							break;

						default:
							if (bytLsiteswapThrowBallA[intLsiteswapThrowBallIndex] == bytPsequenceThrowBallA[intLsequenceThrowBallIndex]
								|| (bytPsequenceThrowBallA[intLsequenceThrowBallIndex] > Constants.bytS_ENGINE_THROWS_VALUES_NUMBER && Math.abs(bytLsiteswapThrowBallA[intLsiteswapThrowBallIndex]) == bytPsequenceThrowBallA[intLsequenceThrowBallIndex]
																																																		- Constants.bytS_ENGINE_THROWS_VALUES_NUMBER)) {
								bolLsiteswapMark[intLsiteswapThrowBallIndex] = true;
								bolLfound = true;
							}
							break;
					}
				}
			}
			if (!bolLfound) {
				// Tools.verbose("Retour false");
				return false;
			}
		}
		// Tools.verbose("Retour true");
		return true;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPsequence
	 * @return
	 */
	final private ArrayList<ArrayList<ArrayList<Byte>>> getAsynchroSequenceByteALALAL(String strPsequence) {
		// Tools.verbose("Entrée dans getAsynchroSequenceByteALALAL(", strPsequence, ")");
		final ArrayList<ArrayList<ArrayList<Byte>>> bytLsequenceByteALALAL = new ArrayList<ArrayList<ArrayList<Byte>>>(8);
		ArrayList<ArrayList<Byte>> bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
		ArrayList<Byte> objLthrowAL = new ArrayList<Byte>(2);
		final char[] chrLsequenceCharA = strPsequence.toCharArray();

		boolean bolLmultiplex = false;
		// Tools.verbose("Début de getAsynchroSequenceByteALALAL");
		for (int intLcharIndex = 0; intLcharIndex < chrLsequenceCharA.length; ++intLcharIndex) {
			final char chrLindexed = chrLsequenceCharA[intLcharIndex];
			// Tools.verbose("Caractère traité : ", chrLindexed);
			switch (chrLindexed) {
				case ' ':
					break;

				case '-':
					if (bolLmultiplex) {
						objLthrowAL.add(Constants.bytS_ENGINE_ANY_NON_NULL_THROW_VALUE);
					} else {
						bytLchoiceByteALAL.add(objLthrowAL);
						bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
						bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
						objLthrowAL = new ArrayList<Byte>(2);
					}
					break;

				case '+':
					objLthrowAL.add(Constants.bytS_ENGINE_ANY_NON_NULL_THROW_VALUE);
					if (!bolLmultiplex) {
						bytLchoiceByteALAL.add(objLthrowAL);
						bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
						bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
						objLthrowAL = new ArrayList<Byte>(2);
					}
					break;

				case '|':
					if (bolLmultiplex && objLthrowAL.size() > 0) {
						bytLchoiceByteALAL.add(objLthrowAL);
						objLthrowAL = new ArrayList<Byte>(2);
					} else {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}

					break;

				case '[':
					if (bolLmultiplex) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					bolLmultiplex = true;

					break;

				case ']':
					if (bolLmultiplex && objLthrowAL.size() > 0) {
						bytLchoiceByteALAL.add(objLthrowAL);
						bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
						bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
						objLthrowAL = new ArrayList<Byte>(2);
						bolLmultiplex = false;
					} else {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					break;

				case '*':
					final int intLinitialSize = bytLsequenceByteALALAL.size();
					if (bolLmultiplex && intLinitialSize > 0) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					for (int intLindex = 0; intLindex < intLinitialSize; ++intLindex) {
						bytLsequenceByteALALAL.add(bytLsequenceByteALALAL.get(intLindex));
					}
					break;

				default:
					final Byte bytLvalueByte = Tools.getThrowByte(chrLindexed);
					if (bytLvalueByte == null) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					objLthrowAL.add(bytLvalueByte);
					if (!bolLmultiplex) {
						bytLchoiceByteALAL.add(objLthrowAL);
						bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
						bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
						objLthrowAL = new ArrayList<Byte>(2);
					}
					break;
			}
		}
		if (bolLmultiplex) {
			if (objLthrowAL.size() > 0) {
				bytLchoiceByteALAL.add(objLthrowAL);
				bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
			} else {
				this.intGerrorIndex = chrLsequenceCharA.length - 1;
				return null;
			}
		}
		// Tools.verbose("Fin de getAsynchroSequenceByteALALAL");
		return bytLsequenceByteALALAL;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPthrowIndex
	 * @return
	 */

	final public String getInfo() {
		return Strings.doConcat("Sequence  : ",
								this.toString(),
								Strings.strS_LINE_SEPARATOR,
								"Statut    : ",
								this.bolGstatus,
								this.bolGstatus ? null : Strings.doConcat(" (", this.intGerrorIndex, ')'),
								Strings.strS_LINE_SEPARATOR,
								"Synchro   : ",
								this.bolGsynchro,
								Strings.strS_LINE_SEPARATOR,
								"# Throws  : ",
								this.intGthrowsNumber,
								Strings.strS_LINE_SEPARATOR,
								"# Choices : ",
								this.intGthrowChoiceNumberA,
								Strings.strS_LINE_SEPARATOR,
								"# Balls   : ",
								this.intGthrowChoiceBallNumberAA,
								Strings.strS_LINE_SEPARATOR,
								"Throws    : ",
								this.bytGthrowAAA);
	}

	final public boolean getStatus() {
		return this.bolGstatus;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final private Sequence getSymmetricSequence() {
		return new Sequence(this, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPsequence
	 * @return
	 */
	final private ArrayList<ArrayList<ArrayList<Byte>>> getSynchroSequenceByteALALAL(String strPsequence) {

		final byte bytL_STARTING_STATE = -1;
		final byte bytL_OPENING_PARENTHESIS_STATE = 0;
		final byte bytL_COMMA_STATE = 1;
		final byte bytL_CLOSING_PARENTHESIS_STATE = 2;

		final ArrayList<ArrayList<ArrayList<Byte>>> bytLsequenceByteALALAL = new ArrayList<ArrayList<ArrayList<Byte>>>(8);
		ArrayList<ArrayList<Byte>> bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
		ArrayList<Byte> objLthrowAL = new ArrayList<Byte>(2);
		final char[] chrLsequenceCharA = strPsequence.toCharArray();
		byte bytLstate = bytL_STARTING_STATE;
		Boolean bolLmultiplexBoolean = null;
		for (int intLcharIndex = 0; intLcharIndex < chrLsequenceCharA.length; ++intLcharIndex) {
			final char chrLindexed = chrLsequenceCharA[intLcharIndex];
			switch (chrLindexed) {
				case ' ':
					break;

				case '(':
				case ')':
				case '*':
					switch (bytLstate) {
						case bytL_OPENING_PARENTHESIS_STATE:
							if (objLthrowAL.size() > 0) {
								bytLchoiceByteALAL.add(objLthrowAL);
							} else if (bytLchoiceByteALAL.size() == 0) {
								objLthrowAL.add(Constants.bytS_ENGINE_ANY_THROW_VALUE);
								bytLchoiceByteALAL.add(objLthrowAL);
							}
							bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
							objLthrowAL = new ArrayList<Byte>(2);
							objLthrowAL.add(Constants.bytS_ENGINE_ANY_THROW_VALUE);
							bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
							bytLchoiceByteALAL.add(objLthrowAL);
							bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
							objLthrowAL = new ArrayList<Byte>(2);
							bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
							break;

						case bytL_COMMA_STATE:
							if (objLthrowAL.size() > 0) {
								bytLchoiceByteALAL.add(objLthrowAL);
							} else if (bytLchoiceByteALAL.size() == 0) {
								objLthrowAL.add(Constants.bytS_ENGINE_ANY_THROW_VALUE);
								bytLchoiceByteALAL.add(objLthrowAL);
							}
							bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
							objLthrowAL = new ArrayList<Byte>(2);
							bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
							break;

						case bytL_STARTING_STATE:
						case bytL_CLOSING_PARENTHESIS_STATE:
							if (chrLindexed == ')' || chrLindexed == '*' && bytLstate == bytL_STARTING_STATE) {
								this.intGerrorIndex = intLcharIndex;
								return null;
							}
							break;
					}
					bytLstate = chrLindexed == '(' ? bytL_OPENING_PARENTHESIS_STATE : bytL_CLOSING_PARENTHESIS_STATE;
					bolLmultiplexBoolean = null;

					// Append reverse tab :
					if (chrLindexed == '*') {
						for (int intLthrowIndex = 0, intLinitialThrowsNumber = bytLsequenceByteALALAL.size(); intLthrowIndex < intLinitialThrowsNumber; intLthrowIndex +=
																																											2) {
							bytLsequenceByteALALAL.add(bytLsequenceByteALALAL.get(intLthrowIndex + 1));
							bytLsequenceByteALALAL.add(bytLsequenceByteALALAL.get(intLthrowIndex));
						}
					}
					break;

				case ',':
					switch (bytLstate) {
						case bytL_STARTING_STATE:
						case bytL_OPENING_PARENTHESIS_STATE:
						case bytL_CLOSING_PARENTHESIS_STATE:
							if (objLthrowAL.size() > 0) {
								bytLchoiceByteALAL.add(objLthrowAL);
							} else if (bytLchoiceByteALAL.size() == 0) {
								objLthrowAL.add(Constants.bytS_ENGINE_ANY_THROW_VALUE);
								bytLchoiceByteALAL.add(objLthrowAL);
							}
							bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
							objLthrowAL = new ArrayList<Byte>(2);
							bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(4);
							break;

						case bytL_COMMA_STATE:
							this.intGerrorIndex = intLcharIndex;
							return null;
					}
					bytLstate = bytL_COMMA_STATE;
					bolLmultiplexBoolean = null;
					break;

				case '-':
				case '+':
					if (bolLmultiplexBoolean == Boolean.FALSE) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					objLthrowAL.add(chrLindexed == '+' ? Constants.bytS_ENGINE_ANY_NON_NULL_THROW_VALUE : Constants.bytS_ENGINE_ANY_THROW_VALUE);
					if (bytLstate == bytL_STARTING_STATE) {
						bytLstate = bytL_OPENING_PARENTHESIS_STATE;
					}
					break;

				case '|':
					if (bytLstate == bytL_CLOSING_PARENTHESIS_STATE || bytLstate == bytL_STARTING_STATE || objLthrowAL.size() == 0) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					bytLchoiceByteALAL.add(objLthrowAL);
					objLthrowAL = new ArrayList<Byte>(2);
					break;

				case '[':
					if (bolLmultiplexBoolean != null) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					bolLmultiplexBoolean = Boolean.TRUE;
					if (bytLstate == bytL_STARTING_STATE) {
						bytLstate = bytL_OPENING_PARENTHESIS_STATE;
					}
					break;

				case ']':
					if (bolLmultiplexBoolean != Boolean.TRUE) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					bolLmultiplexBoolean = Boolean.FALSE;
					break;

				default:
					if (bolLmultiplexBoolean == Boolean.FALSE) {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					final Byte bytLByte = Tools.getThrowByte(chrLindexed);
					if (bytLByte != null) {

						// Check odd value :
						byte bytLvalue = bytLByte.byteValue();
						if (bytLvalue % 2 != 0) {
							this.intGerrorIndex = intLcharIndex;
							return null;
						}

						// Look for the next char :
						char chrLnextChar = '\0';
						int intLnextCharIndex = intLcharIndex + 1;
						while (intLnextCharIndex < chrLsequenceCharA.length && chrLsequenceCharA[intLnextCharIndex] == ' ') {
							++intLnextCharIndex;
						}
						if (intLnextCharIndex != chrLsequenceCharA.length) {
							chrLnextChar = Strings.getUpperCaseChar(chrLsequenceCharA[intLnextCharIndex]);
						}

						// Check the next char :
						switch (chrLnextChar) {
							case '?':
								if (bytLvalue != 0) {
									bytLvalue += Constants.bytS_ENGINE_THROWS_VALUES_NUMBER;
								}
								intLcharIndex = intLnextCharIndex;
								break;
							case '×':
							case 'X':
								bytLvalue = (byte) -bytLvalue;
								intLcharIndex = intLnextCharIndex;
								break;
						}
						objLthrowAL.add(bytLvalue);
					} else {
						this.intGerrorIndex = intLcharIndex;
						return null;
					}
					if (bytLstate == bytL_STARTING_STATE) {
						bytLstate = bytL_OPENING_PARENTHESIS_STATE;
					}
					break;
			}
		}
		switch (bytLstate) {
			case bytL_OPENING_PARENTHESIS_STATE:
				if (objLthrowAL.size() > 0) {
					bytLchoiceByteALAL.add(objLthrowAL);
				} else if (bytLchoiceByteALAL.size() == 0) {
					objLthrowAL.add(Constants.bytS_ENGINE_ANY_THROW_VALUE);
					bytLchoiceByteALAL.add(objLthrowAL);
				}
				bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
				objLthrowAL = new ArrayList<Byte>(1);
				objLthrowAL.add(Constants.bytS_ENGINE_ANY_THROW_VALUE);
				bytLchoiceByteALAL = new ArrayList<ArrayList<Byte>>(1);
				bytLchoiceByteALAL.add(objLthrowAL);
				bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
				break;

			case bytL_COMMA_STATE:
				if (objLthrowAL.size() > 0) {
					bytLchoiceByteALAL.add(objLthrowAL);
				} else if (bytLchoiceByteALAL.size() == 0) {
					objLthrowAL.add(Constants.bytS_ENGINE_ANY_THROW_VALUE);
					bytLchoiceByteALAL.add(objLthrowAL);
				}
				bytLsequenceByteALALAL.add(bytLchoiceByteALAL);
				break;
		}

		return bytLsequenceByteALALAL;
	}

	final private byte[] getThrowA(ArrayList<Byte> objPthrowAL) {

		if (objPthrowAL == null) {
			return null;
		}

		int intLzerosNumber = 0;
		int intLnonZerosNumber = 0;

		// Count null and non-null values :
		for (int intLthrowIndex = 0; intLthrowIndex < objPthrowAL.size(); ++intLthrowIndex) {
			switch (objPthrowAL.get(intLthrowIndex).byteValue()) {
				case 0:
					intLzerosNumber++;
					break;
				case Constants.bytS_ENGINE_ANY_THROW_VALUE:
					if (objPthrowAL.size() == 1) {
						objPthrowAL.remove(intLthrowIndex);
						break;
					}
					objPthrowAL.set(intLthrowIndex, Constants.bytS_ENGINE_ANY_NON_NULL_THROW_VALUE);
					//$FALL-THROUGH$
				default:
					intLnonZerosNumber++;
			}
		}

		// Treat no non-null values case :
		if (intLnonZerosNumber == 0) {
			return (intLzerosNumber > 0 ? new byte[] { (byte) 0 } : null);
		}

		// Keep non-null values :
		Collections.sort(objPthrowAL);
		final byte[] bytLthrowA = new byte[intLnonZerosNumber];
		int intLnonZeroIndex = 0;
		for (final Byte bytLByte : objPthrowAL) {
			if (bytLByte.byteValue() != 0) {
				bytLthrowA[intLnonZeroIndex] = bytLByte.byteValue();
				intLnonZeroIndex++;
			}
		}

		return bytLthrowA;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPsiteswap
	 * @return
	 */
	final public boolean isPartOf(Siteswap objPsiteswap) {
		return this.isPartOf(objPsiteswap, this.bolGsynchro);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPsiteswap
	 * @param bolPcheckSymmetric
	 * @return
	 */
	final private boolean isPartOf(Siteswap objPsiteswap, boolean bolPcheckSymmetric) {

		// Tools.verbose("--------------------------------------------");
		// Tools.verbose("Comparaison ", bolPcheckSymmetric ? "" : "non-", "symétrique avec ",
		// objPsiteswap);
		boolean bolLpartOf = false;
		if (objPsiteswap != null && objPsiteswap.bolGsynchro == this.bolGsynchro && objPsiteswap.bytGstatus >= Constants.bytS_STATE_SITESWAP_EMPTY
			&& this.bolGstatus) {

			// Tools.verbose("Comparaison...");
			// Compare starting with each siteswap throw :
			for (int intLsiteswapThrowIndex = 0; !bolLpartOf && intLsiteswapThrowIndex < objPsiteswap.intGthrowsNumber; intLsiteswapThrowIndex +=
																																					objPsiteswap.bolGsynchro
																																											? 2
																																											: 1) {
				// Tools.verbose("intLsiteswapThrowIndex : ", intLsiteswapThrowIndex);
				bolLpartOf = true;
				for (int intLsequenceThrowDeltaIndex = 0; bolLpartOf && intLsequenceThrowDeltaIndex < this.intGthrowsNumber; ++intLsequenceThrowDeltaIndex) {
					// Tools.verbose("intLsequenceThrowDeltaIndex : ", intLsequenceThrowDeltaIndex);
					bolLpartOf = this.intGthrowChoiceNumberA[intLsequenceThrowDeltaIndex] == 0;
					for (int intLsequenceThrowChoiceIndex = 0; !bolLpartOf
																&& intLsequenceThrowChoiceIndex < this.intGthrowChoiceNumberA[intLsequenceThrowDeltaIndex]; ++intLsequenceThrowChoiceIndex) {
						// Tools.verbose("intLsequenceThrowChoiceIndex : ", intLsequenceThrowChoiceIndex);
						bolLpartOf =
										Sequence.isPartOf(	this.bytGthrowAAA[intLsequenceThrowDeltaIndex][intLsequenceThrowChoiceIndex],
															objPsiteswap.bytGballThrowAA[(intLsiteswapThrowIndex + intLsequenceThrowDeltaIndex)
																							% objPsiteswap.intGthrowsNumber]);
						// if (!bolLpartOf && intLsequenceThrowChoiceIndex ==
						// this.intGthrowChoiceNumberA[intLsequenceThrowDeltaIndex] - 1) {
						// } else {
						// bolLpartOf = true;
						// break;
						// }
					}
				}
			}
		}

		if (bolPcheckSymmetric && !bolLpartOf) {
			// Tools.verbose("Calcul du symétrique !");
			return this.getSymmetricSequence().isPartOf(objPsiteswap, false);
		}
		return bolLpartOf;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPsequenceByteALALAL
	 */
	final private void setSequenceAAA(ArrayList<ArrayList<ArrayList<Byte>>> bytPsequenceByteALALAL) {

		// Set throw number :
		this.intGthrowsNumber = bytPsequenceByteALALAL.size();

		// Set choice number per throw :
		this.intGthrowChoiceNumberA = new int[this.intGthrowsNumber];
		int intLthrowsChoicesMaxNumber = 0;
		for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
			this.intGthrowChoiceNumberA[intLthrowIndex] = bytPsequenceByteALALAL.get(intLthrowIndex).size();
			intLthrowsChoicesMaxNumber = Math.max(intLthrowsChoicesMaxNumber, this.intGthrowChoiceNumberA[intLthrowIndex]);
		}

		// Set ball number per choice :
		this.intGthrowChoiceBallNumberAA = new int[this.intGthrowsNumber][intLthrowsChoicesMaxNumber];
		int intLthrowsChoicesBallsLimitNumber = 0;
		for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
			for (int intLthrowChoiceIndex = 0; intLthrowChoiceIndex < this.intGthrowChoiceNumberA[intLthrowIndex]; ++intLthrowChoiceIndex) {
				this.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex] =
																							bytPsequenceByteALALAL	.get(intLthrowIndex)
																													.get(intLthrowChoiceIndex)
																													.size();
				intLthrowsChoicesBallsLimitNumber =
													Math.max(	intLthrowsChoicesBallsLimitNumber,
																this.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex]);
			}
		}

		// Set ball throws per choice :
		final byte[][][] bytLthrowAAA = new byte[this.intGthrowsNumber][intLthrowsChoicesMaxNumber][intLthrowsChoicesBallsLimitNumber];
		int intLthrowsChoicesBallsMaxNumber = 0;
		for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
			for (int intLthrowChoiceIndex = 0; intLthrowChoiceIndex < this.intGthrowChoiceNumberA[intLthrowIndex]; ++intLthrowChoiceIndex) {
				final byte[] bytLthrowA = this.getThrowA(bytPsequenceByteALALAL.get(intLthrowIndex).get(intLthrowChoiceIndex));
				if (bytLthrowA != null) {
					this.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex] = bytLthrowA.length;
					intLthrowsChoicesBallsMaxNumber = Math.max(intLthrowsChoicesBallsMaxNumber, bytLthrowA.length);
					System.arraycopy(bytLthrowA, 0, bytLthrowAAA[intLthrowIndex][intLthrowChoiceIndex], 0, bytLthrowA.length);
				} else {
					this.intGthrowChoiceNumberA[intLthrowIndex] = 0;
					// this.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex] = 0;
				}
			}
		}

		if (intLthrowsChoicesBallsMaxNumber == intLthrowsChoicesBallsLimitNumber) {
			this.bytGthrowAAA = bytLthrowAAA;
		} else {
			this.bytGthrowAAA = new byte[this.intGthrowsNumber][intLthrowsChoicesMaxNumber][intLthrowsChoicesBallsMaxNumber];
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				for (int intLthrowChoiceIndex = 0; intLthrowChoiceIndex < this.intGthrowChoiceNumberA[intLthrowIndex]; ++intLthrowChoiceIndex) {
					System.arraycopy(	bytLthrowAAA[intLthrowIndex][intLthrowChoiceIndex],
										0,
										this.bytGthrowAAA[intLthrowIndex][intLthrowChoiceIndex],
										0,
										this.intGthrowChoiceBallNumberAA[intLthrowIndex][intLthrowChoiceIndex]);
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public String toString() {
		if (this.bolGstatus) {
			final StringBuilder objLstringBuilder = new StringBuilder(this.strGsequence != null ? this.strGsequence.length() : 64);
			for (int intLthrowIndex = 0; intLthrowIndex < this.intGthrowsNumber; ++intLthrowIndex) {
				objLstringBuilder.append(this.toString(intLthrowIndex));
			}
			return objLstringBuilder.toString();
		}
		return this.strGsequence;
	}

	final private String toString(int intPthrowIndex) {

		final StringBuilder objLstringBuilder = new StringBuilder(this.bolGsynchro ? 16 : 8);
		final boolean bolLevenValue = (intPthrowIndex % 2 == 0);
		final boolean bolLmulti =
									this.intGthrowChoiceNumberA[intPthrowIndex] > 1 || this.intGthrowChoiceNumberA[intPthrowIndex] == 1
										&& this.intGthrowChoiceBallNumberAA[intPthrowIndex][0] > 1;

		// Header :
		if (this.bolGsynchro && bolLevenValue) {
			objLstringBuilder.append('(');
		}
		if (bolLmulti) {
			objLstringBuilder.append('[');
		}

		// Choices :
		if (this.intGthrowChoiceNumberA[intPthrowIndex] == 0) {
			objLstringBuilder.append('-');
		} else {
			for (int intLthrowChoiceIndex = 0; intLthrowChoiceIndex < this.intGthrowChoiceNumberA[intPthrowIndex]; ++intLthrowChoiceIndex) {
				if (intLthrowChoiceIndex > 0) {
					objLstringBuilder.append('|');
				}
				// if (intGthrowChoiceBallNumberAA[intPthrowIndex][intLthrowChoiceIndex] == 0) {
				// objLstringBuilder.append('-');
				// } else {
				for (int intLthrowChoiceBallIndex = 0; intLthrowChoiceBallIndex < this.intGthrowChoiceBallNumberAA[intPthrowIndex][intLthrowChoiceIndex]; ++intLthrowChoiceBallIndex) {
					objLstringBuilder.append(Tools.getThrowValueString(	this.bytGthrowAAA[intPthrowIndex][intLthrowChoiceIndex][intLthrowChoiceBallIndex],
																		bolLmulti));
				}
				// }
			}
		}
		if (bolLmulti) {
			objLstringBuilder.append(']');
		}

		// Footer :
		if (this.bolGsynchro) {
			objLstringBuilder.append(bolLevenValue ? ',' : ')');
		}

		return objLstringBuilder.toString();
	}

	private final boolean		bolGstatus;

	private final boolean		bolGsynchro;

	private byte[][][]			bytGthrowAAA;

	private int					intGerrorIndex;

	private int[][]				intGthrowChoiceBallNumberAA;

	private int[]				intGthrowChoiceNumberA;

	private int					intGthrowsNumber;

	// Conventions :
	// '-' : anything { -35, }
	// 'nx' : crossed throw { -34, -32, ..., -2 }
	// '0' : no throw { 0 }
	// 'n' : throw n { 1, 2, ..., 35 }
	// 'n?' : crossed or uncrossed throw { 38, 40, ..., 70 }
	// '+' : any non-null throw { 72 }
	// '*' : add inverted throws
	// [i|j] : throw i or j
	// [ijk|np] : triplex ijk or multiplex np
	private String				strGsequence;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)Sequence.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
