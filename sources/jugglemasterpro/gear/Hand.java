/*
 * @(#)JuggleHand.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.gear;

import jugglemasterpro.engine.JuggleMasterPro;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Hand {

	/**
	 * Constructs
	 * 
	 * @param bytPside
	 */
	public Hand(byte bytPside) {

		// Creation :
		this.bytGside = bytPside;
		this.intGpreviousFramesRelativeNumberTillTheoricDestination = 0;
	}

	final public static byte getSoundsVolume(byte bytLthrow) {

		switch (Math.abs(bytLthrow)) {
			case 0:
				return 0;
			case 1:
				return 70;
			case 2:
				return 81;
			case 3:
				return 78;
			case 4:
				return 80;
			case 5:
				return 83;
			case 6:
				return 86;
			case 7:
				return 90;
			case 8:
				return 95;
			case 9:
				return 100;
			default:
				return 100;
		}

	}

	final public byte getSoundsBalance() {
		return (byte) (Math.max(-100, Math.min(Math.round(this.bytGtheoricDestinationX * 6), 100)));// * Tools.getSignedBoolean(!bolPmirror));

	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPjuggleMasterPro
	 * @return
	 */
	final public boolean move(JuggleMasterPro objPjuggleMasterPro) {
		int intLframesRelativeNumberTillTheoricDestination, // C'est le nombre de frames restantes jusqu'au prochain
		// mouvement

		// négatif (de -... à 0) du dernier lancé à la prochaine rattrape,
		// positif (de 0 à +...) de la dernière rattrape au prochain lancé.
		// Les bornes sont différentes en fonction du %main,
		// et donc le nombre de frames varie en fonction de %main
		intLvalueX1 = 0, intLvalueY1 = 0, intLvalueX2 = 0, intLvalueY2 = 0;
		boolean bolLnewThrow = false;

		if (this.intGnextCatchTimer < 0
			&& objPjuggleMasterPro.lngGframesCount >= (-this.intGnextCatchTimer * objPjuggleMasterPro.intGframesNumberPerThrow)) {
			this.intGnextCatchTimer = -this.intGnextCatchTimer;
		}

		// Set intLframesRelativeNumberTillTheoricDestination :
		intLframesRelativeNumberTillTheoricDestination =
															(int) (objPjuggleMasterPro.lngGframesCount - (objPjuggleMasterPro.intGframesNumberPerThrow * Math.abs(this.intGnextCatchTimer)));
		while (intLframesRelativeNumberTillTheoricDestination >= objPjuggleMasterPro.intGframesCountInitialForwardDelta) {
			this.bytGanimationState &= -3;
			this.intGlastCatchTimer = this.intGnextCatchTimer;
			if ((this.bytGanimationState & Constants.bytS_BIT_0) != 0) {
				this.intGnextCatchTimer += 2;
			} else {
				int intLsiteswapPermutationsIndex = this.intGnextCatchTimer;

				if (objPjuggleMasterPro.objGsiteswap.bolGsynchro
					&& (objPjuggleMasterPro.bolGmirror ^ (this.bytGside != Constants.bytS_ENGINE_LEFT_SIDE))) {
					++intLsiteswapPermutationsIndex;
				}
				intLsiteswapPermutationsIndex %= objPjuggleMasterPro.objGsiteswap.intGthrowsNumber;
				this.intGnextCatchTimer += 2;
				if (++objPjuggleMasterPro.objGsiteswap.intGpermutationA[intLsiteswapPermutationsIndex] >= objPjuggleMasterPro.intGballsNumberPerThrowA[intLsiteswapPermutationsIndex]) {
					objPjuggleMasterPro.objGsiteswap.intGpermutationA[intLsiteswapPermutationsIndex] = 0;
				}
			}
			intLframesRelativeNumberTillTheoricDestination =
																(int) (objPjuggleMasterPro.lngGframesCount - (objPjuggleMasterPro.intGframesNumberPerThrow * Math.abs(this.intGnextCatchTimer)));
			bolLnewThrow = true;
		}

		if (this.intGpreviousFramesRelativeNumberTillTheoricDestination > intLframesRelativeNumberTillTheoricDestination) {
			objPjuggleMasterPro.intGdwellFramesIndexesDelta =
																(this.intGpreviousFramesRelativeNumberTillTheoricDestination
																	+ intLframesRelativeNumberTillTheoricDestination + 1) / 2;
			objPjuggleMasterPro.intGthrowLastFrameIndex =
															objPjuggleMasterPro.intGdwellFramesIndexesDelta
																+ objPjuggleMasterPro.intGframesNumberPerThrow - 1;
		}
		// TODO : test ! je retire la condition !!! C'est pour que les balles dans le dos le soient dès le début de la
		// figure
		objPjuggleMasterPro.intGdwellFramesIndexesDelta =
															(this.intGpreviousFramesRelativeNumberTillTheoricDestination
																+ intLframesRelativeNumberTillTheoricDestination + 1) / 2;

		// Set bytGanimationState :
		if (this.intGnextCatchTimer >= 0 && intLframesRelativeNumberTillTheoricDestination >= 0
			&& (this.bytGanimationState & Constants.bytS_BIT_1) == 0) {
			this.bytGanimationState |= Constants.bytS_BIT_1;
			if ((this.bytGanimationState & Constants.bytS_BIT_0) != 0) {
				if ((this.bytGanimationState & Constants.bytS_BIT_3) != 0) {
					this.bytGanimationState = (byte) ((this.bytGanimationState | Constants.bytS_BIT_2) & -9);
				} else {
					this.bytGanimationState &= -5;
				}
			} else {
				int intLthrowIndex = this.intGnextCatchTimer;

				if (objPjuggleMasterPro.objGsiteswap.bolGsynchro
					&& (objPjuggleMasterPro.bolGmirror ^ (this.bytGside != Constants.bytS_ENGINE_LEFT_SIDE))) {
					++intLthrowIndex;
				}
				intLthrowIndex %= objPjuggleMasterPro.objGsiteswap.intGthrowsNumber;
				this.bytGanimationState &= -5;
				for (int intLballIndex = 0; intLballIndex < objPjuggleMasterPro.intGballsNumberPerThrowA[intLthrowIndex]; ++intLballIndex) {
					switch (objPjuggleMasterPro.bytGsiteswapValueAA[intLthrowIndex][intLballIndex]) {
						case 2:
							break;

						case 1:
							objPjuggleMasterPro.objGhandA[this.bytGside != Constants.bytS_ENGINE_LEFT_SIDE ? Constants.bytS_ENGINE_LEFT_SIDE
																											: Constants.bytS_ENGINE_RIGHT_SIDE].bytGanimationState |=
																																										Constants.bytS_BIT_3;

							//$FALL-THROUGH$
						default:
							this.bytGanimationState |= Constants.bytS_BIT_2 | Constants.bytS_BIT_3;
					}
				}
			}
		}

		if ((this.bytGanimationState & Constants.bytS_BIT_2) == 0) {
			if (this.intGnextCatchTimer < 0) {
				this.setTheoricDestination(objPjuggleMasterPro, -this.intGnextCatchTimer);
				intLvalueX2 = intLvalueX1 = this.bytGtheoricDestinationX;
				intLvalueY2 = intLvalueY1 = this.bytGtheoricDestinationY;
			} else {
				final byte bytLdelta = (byte) ((this.bytGanimationState & Constants.bytS_BIT_1) != 0 ? 0 : 2);

				this.setTheoricDestination(objPjuggleMasterPro, this.intGnextCatchTimer - bytLdelta);
				intLvalueX1 = this.bytGtheoricDestinationX;
				intLvalueY1 = this.bytGtheoricDestinationY;
				this.setTheoricDestination(objPjuggleMasterPro, this.intGnextCatchTimer + 2 - bytLdelta);
				intLvalueX2 = this.bytGtheoricDestinationX;
				intLvalueY2 = this.bytGtheoricDestinationY;
				if (intLvalueX1 != intLvalueX2 || intLvalueY1 != intLvalueY2) {
					this.setTheoricDestination(objPjuggleMasterPro, this.intGnextCatchTimer + 1 - bytLdelta);
					if (bytLdelta != 0) {
						intLvalueX1 = this.bytGtheoricDestinationX;
						intLvalueY1 = this.bytGtheoricDestinationY;
					} else {
						intLvalueX2 = this.bytGtheoricDestinationX;
						intLvalueY2 = this.bytGtheoricDestinationY;
					}
					if (intLvalueX1 != intLvalueX2 || intLvalueY1 != intLvalueY2) {
						this.bytGanimationState |= Constants.bytS_BIT_2;
					}
				}
			}
		}
		if ((this.bytGanimationState & Constants.bytS_BIT_2) != 0) {
			this.setTheoricDestination(objPjuggleMasterPro, (this.bytGanimationState & Constants.bytS_BIT_1) != 0 ? this.intGnextCatchTimer
																													: this.intGlastCatchTimer + 1);
			intLvalueX1 = this.bytGtheoricDestinationX;
			intLvalueY1 = this.bytGtheoricDestinationY;
			this.setTheoricDestination(objPjuggleMasterPro, (this.bytGanimationState & Constants.bytS_BIT_1) != 0 ? this.intGnextCatchTimer + 1
																													: this.intGnextCatchTimer);
			intLvalueX2 = this.bytGtheoricDestinationX;
			intLvalueY2 = this.bytGtheoricDestinationY;
		}

		long intLvalueY3;
		float fltLvalue;

		if ((this.bytGanimationState & Constants.bytS_BIT_0) == 0 && this.intGnextCatchTimer < 0) {
			if (intLvalueX1 == 0) {
				intLvalueY3 =
								(long) (((float) intLvalueY1 * (float) objPjuggleMasterPro.intGdrawingSize) / 20F - (((long) intLframesRelativeNumberTillTheoricDestination * (long) objPjuggleMasterPro.intGdrawingSize) / 12L / objPjuggleMasterPro.intGframesNumberPerThrow));
				fltLvalue = 0F;
			} else {
				intLvalueY3 = (long) (((float) intLvalueY1 * (float) objPjuggleMasterPro.intGdrawingSize) / 20F);
				fltLvalue =
							intLvalueX1 / 10F + (intLvalueX1 > 0 ? -1F : 1F) * intLframesRelativeNumberTillTheoricDestination / 6F
								/ objPjuggleMasterPro.intGframesNumberPerThrow;
			}
		} else if ((this.bytGanimationState & Constants.bytS_BIT_2) == 0) {
			intLvalueY3 = (long) (((float) intLvalueY1 * (float) objPjuggleMasterPro.intGdrawingSize) / 20F);
			fltLvalue = intLvalueX1 / 10F;
		} else {
			if ((this.bytGanimationState & Constants.bytS_BIT_1) != 0) {
				fltLvalue =
							((float) intLframesRelativeNumberTillTheoricDestination / (float) objPjuggleMasterPro.intGframesCountInitialForwardDelta) * 2F - 1F;
				intLvalueY3 = (long) (objPjuggleMasterPro.fltGheightA[0] * (1F - fltLvalue * fltLvalue));
			} else {
				fltLvalue =
							((float) intLframesRelativeNumberTillTheoricDestination / (float) (objPjuggleMasterPro.intGframesNumberPerThrow * 2 - objPjuggleMasterPro.intGframesCountInitialForwardDelta)) * 2F + 1F;
				intLvalueY3 = (long) (objPjuggleMasterPro.fltGheightA[2] * (1F - fltLvalue * fltLvalue));
			}
			intLvalueY3 =
							(long) (intLvalueY3 + ((fltLvalue * (intLvalueY2 - intLvalueY1) + intLvalueY2 + intLvalueY1) * objPjuggleMasterPro.intGdrawingSize) / 40F);
			fltLvalue = (fltLvalue * (intLvalueX2 - intLvalueX1) + intLvalueX2 + intLvalueX1) / 20F;
		}

		// Setting intGposX :
		this.intGposX = (int) (229L + (long) (fltLvalue * objPjuggleMasterPro.intGdrawingSize * 0.25F));
		if ((this.bytGanimationState & Constants.bytS_BIT_0) != 0) {
			this.intGposX += Tools.getSignedBoolean(this.bytGside == Constants.bytS_ENGINE_RIGHT_SIDE) * objPjuggleMasterPro.intGhandDeltaX;
			intLvalueY3 -= objPjuggleMasterPro.intGhandDeltaY;
		}

		// Setting intGposY :
		this.intGposY = (int) (objPjuggleMasterPro.intGbaseY - intLvalueY3 - 11L);

		// TODO : à checker - c'est pour jarter le restartJuggling des scrollBars
		// this.intGposX = Math.max(0, Math.min(this.intGposX, objPjuggleMasterPro.getFrame().intGanimationWidth));
		// this.intGposY = Math.max(0, Math.min(this.intGposY, objPjuggleMasterPro.getFrame().intGanimationHeight));

		// Setting fltGposZ :
		if (intLframesRelativeNumberTillTheoricDestination == objPjuggleMasterPro.intGthrowLastFrameIndex
			|| intLframesRelativeNumberTillTheoricDestination == -1) {
			this.fltGposZ = this.bytGtheoricDestinationZ;
		} else {
			final float fltLdenominator =
											((intLframesRelativeNumberTillTheoricDestination < -1 ? -1 : objPjuggleMasterPro.intGthrowLastFrameIndex)
												- intLframesRelativeNumberTillTheoricDestination + 1);
			if (fltLdenominator != 0F) {
				this.fltGposZ += (this.bytGtheoricDestinationZ - this.fltGposZ) / fltLdenominator;
			}
		}

		this.intGpreviousFramesRelativeNumberTillTheoricDestination = intLframesRelativeNumberTillTheoricDestination;
		return (bolLnewThrow);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPjuggleMasterPro
	 * @param intPunknown
	 */
	final private void setTheoricDestination(JuggleMasterPro objPjuggleMasterPro, int intPnextTimer) {

		int intLnextTimer = intPnextTimer;
		int intLdestinationStyleIndex;
		final boolean bolLmirror = objPjuggleMasterPro.bolGmirror;

		if (!objPjuggleMasterPro.objGsiteswap.bolGsynchro && (bolLmirror ^ (this.bytGside == Constants.bytS_ENGINE_LEFT_SIDE))) {
			--intLnextTimer;
		}
		if ((intLnextTimer & Constants.bytS_BIT_0) != 0) {
			intLdestinationStyleIndex =
										((bolLmirror ? intLnextTimer + this.bytGside - 1 : intLnextTimer - this.bytGside) % (objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA.length / (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER)))
											* 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER + Constants.bytS_ENGINE_COORDONATES_NUMBER;
		} else {
			intLdestinationStyleIndex =
										((bolLmirror ? intLnextTimer + this.bytGside : intLnextTimer + 1 - this.bytGside) % (objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA.length / (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER)))
											* 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER;
		}

		try {
			final int intLzStyleIndex = intLdestinationStyleIndex / Constants.bytS_ENGINE_COORDONATES_NUMBER;
			this.bolGbodyHandZOrder = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbodyArmsZOrderA[intLzStyleIndex];
			this.bolGarmVisible = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGarmsVisibleA[intLzStyleIndex];
			this.bolGarmHandZOrder = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGarmsHandsZOrderA[intLzStyleIndex];
			this.bolGhandVisible = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGhandsVisibleA[intLzStyleIndex];
			this.bytGtheoricDestinationX =
											(byte) (Tools.getSignedBoolean(this.bytGside == Constants.bytS_ENGINE_RIGHT_SIDE) * objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLdestinationStyleIndex]);
			this.bytGtheoricDestinationY = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLdestinationStyleIndex + 1];
			this.bytGtheoricDestinationZ =
											(byte) (objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLdestinationStyleIndex + 2] * Tools.getSignedBoolean(this.bolGbodyHandZOrder));
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while calculating hand theoric destination");
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public String toString() {
		return Strings.doConcat("animationState = ",
								this.bytGanimationState,
								Strings.strS_LINE_SEPARATOR,
								"posX           = ",
								this.intGposX,
								Strings.strS_LINE_SEPARATOR,
								"posY           = ",
								this.intGposY,
								Strings.strS_LINE_SEPARATOR,
								"nextCatchTimer = ",
								this.intGnextCatchTimer,
								Strings.strS_LINE_SEPARATOR,
								"lastCatchTimer = ",
								this.intGlastCatchTimer,
								Strings.strS_LINE_SEPARATOR,
								"handStyleX     = ",
								this.bytGtheoricDestinationX,
								Strings.strS_LINE_SEPARATOR,
								"handStyleY     = ",
								this.bytGtheoricDestinationY,
								Strings.strS_LINE_SEPARATOR,
								"handStyleZ     = ",
								this.bytGtheoricDestinationZ,
								Strings.strS_LINE_SEPARATOR);
	}

	public boolean				bolGarmHandZOrder;
	public boolean				bolGarmVisible;
	public boolean				bolGbodyHandZOrder;
	public boolean				bolGhandVisible;
	public byte					bytGanimationState;
	public byte					bytGcatchBallsVolume;
	public byte					bytGside;

	public byte					bytGtheoricDestinationX;
	public byte					bytGtheoricDestinationY;
	public byte					bytGtheoricDestinationZ;
	public byte					bytGthrowBallsVolume;
	public byte					bytLbalance;												// -100 - +100
	public float				fltGposZ;

	public int					intGlastCatchTimer;

	public int					intGnextCatchTimer;

	public int					intGposX;

	public int					intGposY;

	private int					intGpreviousFramesRelativeNumberTillTheoricDestination;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)JuggleHand.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
