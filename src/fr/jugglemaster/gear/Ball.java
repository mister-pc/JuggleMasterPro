/*
 * @(#)JuggleBall.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.gear;

import fr.jugglemaster.engine.JuggleMasterPro;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Ball {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public boolean				bolGarmsBallZOrder;

	public boolean				bolGbodyBallZOrder;

	public boolean				bolGcatchAlternateColor;

	public boolean				bolGjustThrown;

	public boolean				bolGvisible;

	public byte					bytGanimationState;

	public byte					bytGcatchingHand;

	public byte					bytGtheoricDestinationX;

	public byte					bytGtheoricDestinationY;
	public byte					bytGtheoricDestinationZ;
	public byte					bytGthrow;
	public byte					bytGthrowingHand;
	public float				fltGposZ;
	public int					intGalternateColor;
	public int					intGcolor;
	public int					intGlastCatchTimer;
	public int					intGnextCatchTimer;
	public int					intGposX;
	public int					intGposY;

	/**
	 * Constructs
	 */
	public Ball() {

		// Creation :
		this.bolGjustThrown = false;
		this.bolGcatchAlternateColor = false;
	}

	/**
	 * Constructs
	 * 
	 * @param objPball
	 */
	public Ball(Ball objPball) {

		// Constructor by copy :
		this.bytGthrow = objPball.bytGthrow;
		this.intGposX = objPball.intGposX;
		this.intGposY = objPball.intGposY;
		this.fltGposZ = objPball.fltGposZ;
		this.intGnextCatchTimer = objPball.intGnextCatchTimer;
		this.intGlastCatchTimer = objPball.intGlastCatchTimer;
		this.bytGcatchingHand = objPball.bytGcatchingHand;
		this.bytGthrowingHand = objPball.bytGthrowingHand;
		this.bytGanimationState = objPball.bytGanimationState;
		this.bytGtheoricDestinationX = objPball.bytGtheoricDestinationX;
		this.bytGtheoricDestinationY = objPball.bytGtheoricDestinationY;
		this.bytGtheoricDestinationZ = objPball.bytGtheoricDestinationZ;
		this.bolGbodyBallZOrder = objPball.bolGbodyBallZOrder;
		this.bolGarmsBallZOrder = objPball.bolGarmsBallZOrder;
		this.bolGvisible = objPball.bolGvisible;
		this.intGcolor = objPball.intGcolor;
		this.intGalternateColor = objPball.intGalternateColor;
		this.bolGcatchAlternateColor = objPball.bolGcatchAlternateColor;
		this.bolGjustThrown = objPball.bolGjustThrown;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPjuggleMasterPro
	 * @return
	 */
	final public boolean move(JuggleMasterPro objPjuggleMasterPro) {
		int intLframesRelativeNumberTillTheoricDestination, intLvalueX1 = 0, intLvalueY1 = 0, intLvalueX2 = 0, intLvalueY2 = 0;
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
					&& (objPjuggleMasterPro.bolGmirror ^ (this.bytGcatchingHand != Constants.bytS_ENGINE_LEFT_SIDE))) {
					++intLsiteswapPermutationsIndex;
				}
				intLsiteswapPermutationsIndex %= objPjuggleMasterPro.objGsiteswap.intGthrowsNumber;
				this.bytGthrow =
									objPjuggleMasterPro.bytGsiteswapValueAA[intLsiteswapPermutationsIndex][objPjuggleMasterPro.objGsiteswap.intGpermutationA[intLsiteswapPermutationsIndex]];
				this.intGnextCatchTimer += Math.abs(this.bytGthrow);
				if (++objPjuggleMasterPro.objGsiteswap.intGpermutationA[intLsiteswapPermutationsIndex] >= objPjuggleMasterPro.intGballsNumberPerThrowA[intLsiteswapPermutationsIndex]) {
					objPjuggleMasterPro.objGsiteswap.intGpermutationA[intLsiteswapPermutationsIndex] = 0;
				}
				this.bytGthrowingHand = this.bytGcatchingHand;
				if ((this.bytGthrow & Constants.bytS_BIT_0) != 0 || this.bytGthrow < 0) {
					this.bytGcatchingHand =
											(this.bytGcatchingHand == Constants.bytS_ENGINE_LEFT_SIDE ? Constants.bytS_ENGINE_RIGHT_SIDE
																										: Constants.bytS_ENGINE_LEFT_SIDE);
				}
			}
			intLframesRelativeNumberTillTheoricDestination =
																(int) (objPjuggleMasterPro.lngGframesCount - (objPjuggleMasterPro.intGframesNumberPerThrow * Math.abs(this.intGnextCatchTimer)));
			bolLnewThrow = true;
		}

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
					&& (objPjuggleMasterPro.bolGmirror ^ (this.bytGcatchingHand != Constants.bytS_ENGINE_LEFT_SIDE))) {
					++intLthrowIndex;
				}
				intLthrowIndex %= objPjuggleMasterPro.objGsiteswap.intGthrowsNumber;
				if (this.bytGthrow == 1) {
					this.bytGanimationState |= Constants.bytS_BIT_2;
				} else {
					this.bytGanimationState &= -5;
				}
				for (int intLballIndex = 0; intLballIndex < objPjuggleMasterPro.intGballsNumberPerThrowA[intLthrowIndex]; ++intLballIndex) {
					switch (objPjuggleMasterPro.bytGsiteswapValueAA[intLthrowIndex][intLballIndex]) {
						case 2:
							break;

						case 1:
							objPjuggleMasterPro.objGhandA[this.bytGcatchingHand != Constants.bytS_ENGINE_LEFT_SIDE
																													? Constants.bytS_ENGINE_LEFT_SIDE
																													: Constants.bytS_ENGINE_RIGHT_SIDE].bytGanimationState |=
																																												Constants.bytS_BIT_3;

							//$FALL-THROUGH$
						default:
							objPjuggleMasterPro.objGhandA[this.bytGcatchingHand != Constants.bytS_ENGINE_LEFT_SIDE ? Constants.bytS_ENGINE_RIGHT_SIDE
																													: Constants.bytS_ENGINE_LEFT_SIDE].bytGanimationState |=
																																												Constants.bytS_BIT_3;
							this.bytGanimationState |= Constants.bytS_BIT_2;
					}
				}
			}
		}
		if ((this.bytGanimationState & Constants.bytS_BIT_2) == 0) {
			if (this.intGnextCatchTimer < 0) {
				this.setTheoricDestination(objPjuggleMasterPro, -this.intGnextCatchTimer, this.bytGcatchingHand);
				intLvalueX2 = intLvalueX1 = this.bytGtheoricDestinationX;
				intLvalueY2 = intLvalueY1 = this.bytGtheoricDestinationY;
			} else {
				final byte bytLdelta = (byte) ((this.bytGanimationState & Constants.bytS_BIT_1) != 0 ? 0 : 2);

				this.setTheoricDestination(objPjuggleMasterPro, this.intGnextCatchTimer - bytLdelta, this.bytGcatchingHand);
				intLvalueX1 = this.bytGtheoricDestinationX;
				intLvalueY1 = this.bytGtheoricDestinationY;
				this.setTheoricDestination(objPjuggleMasterPro, this.intGnextCatchTimer + 2 - bytLdelta, this.bytGcatchingHand);
				intLvalueX2 = this.bytGtheoricDestinationX;
				intLvalueY2 = this.bytGtheoricDestinationY;
				if (intLvalueX1 != intLvalueX2 || intLvalueY1 != intLvalueY2) {
					this.setTheoricDestination(objPjuggleMasterPro, this.intGnextCatchTimer + 1 - bytLdelta, this.bytGcatchingHand);
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
			this.setTheoricDestination(	objPjuggleMasterPro,
										this.bytGthrow == 1 ? this.intGlastCatchTimer + 1
															: (this.bytGanimationState & Constants.bytS_BIT_1) != 0 ? this.intGnextCatchTimer
																													: this.intGlastCatchTimer + 1,
										this.bytGthrow == 1 || (this.bytGanimationState & Constants.bytS_BIT_1) == 0 ? this.bytGthrowingHand
																													: this.bytGcatchingHand);
			intLvalueX1 = this.bytGtheoricDestinationX;
			intLvalueY1 = this.bytGtheoricDestinationY;
			this.setTheoricDestination(	objPjuggleMasterPro,
										this.bytGthrow == 1 || (this.bytGanimationState & Constants.bytS_BIT_1) != 0 ? this.intGnextCatchTimer + 1
																													: this.intGnextCatchTimer,
										this.bytGcatchingHand);
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
			if (this.bytGthrow == 1) {
				fltLvalue =
							((float) (intLframesRelativeNumberTillTheoricDestination - objPjuggleMasterPro.intGframesCountInitialForwardDelta) / (float) objPjuggleMasterPro.intGframesNumberPerThrow) * 2F + 1F;
				intLvalueY3 = (long) (objPjuggleMasterPro.fltGheightA[1] * (1F - fltLvalue * fltLvalue));
			} else if ((this.bytGanimationState & Constants.bytS_BIT_1) != 0) {
				fltLvalue =
							((float) intLframesRelativeNumberTillTheoricDestination / (float) objPjuggleMasterPro.intGframesCountInitialForwardDelta) * 2F - 1F;
				intLvalueY3 = (long) (objPjuggleMasterPro.fltGheightA[0] * (1F - fltLvalue * fltLvalue));
			} else {
				fltLvalue =
							((float) intLframesRelativeNumberTillTheoricDestination / (float) (objPjuggleMasterPro.intGframesNumberPerThrow
																								* Math.abs(this.bytGthrow) - objPjuggleMasterPro.intGframesCountInitialForwardDelta)) * 2F + 1F;
				intLvalueY3 = (long) (objPjuggleMasterPro.fltGheightA[Math.abs(this.bytGthrow)] * (1F - fltLvalue * fltLvalue));
			}
			intLvalueY3 =
							(long) (intLvalueY3 + ((fltLvalue * (intLvalueY2 - intLvalueY1) + intLvalueY2 + intLvalueY1) * objPjuggleMasterPro.intGdrawingSize) / 40F);
			fltLvalue = (fltLvalue * (intLvalueX2 - intLvalueX1) + intLvalueX2 + intLvalueX1) / 20F;
		}

		// Setting intGposX :
		this.intGposX = (int) (229L + (long) (fltLvalue * objPjuggleMasterPro.intGdrawingSize * 0.25F));
		if ((this.bytGanimationState & Constants.bytS_BIT_0) != 0) {
			this.intGposX += Tools.getSignedBoolean(this.bytGcatchingHand == Constants.bytS_ENGINE_RIGHT_SIDE) * objPjuggleMasterPro.intGhandDeltaX;
			intLvalueY3 -= objPjuggleMasterPro.intGhandDeltaY;
		}

		// Setting intGposY :
		this.intGposY = (int) (objPjuggleMasterPro.intGbaseY - intLvalueY3 - 11L);

		// Setting fltGposZ :
		if (intLframesRelativeNumberTillTheoricDestination == objPjuggleMasterPro.intGthrowLastFrameIndex
			|| intLframesRelativeNumberTillTheoricDestination == -1) {
			this.fltGposZ = this.bytGtheoricDestinationZ;
		} else {

			final float fltLdenominator =
											(intLframesRelativeNumberTillTheoricDestination)
												- (intLframesRelativeNumberTillTheoricDestination >= 0
																										? objPjuggleMasterPro.intGthrowLastFrameIndex + 1
																										: 0);
			if (fltLdenominator != 0F) {
				this.fltGposZ += (this.fltGposZ - this.bytGtheoricDestinationZ) / fltLdenominator;
			}
		}

		// Set flag for catches and sounds :
		if (intLframesRelativeNumberTillTheoricDestination == -1 && this.bytGthrow != 0 && this.bytGthrow != 2) {
			this.bolGcatchAlternateColor = !this.bolGcatchAlternateColor;
			objPjuggleMasterPro.objGhandA[this.bytGcatchingHand].bytGcatchBallsVolume =
																						(byte) Math.max(objPjuggleMasterPro.objGhandA[this.bytGcatchingHand].bytGcatchBallsVolume,
																										Hand.getSoundsVolume(this.bytGthrow));
			objPjuggleMasterPro.setBallRandomColor(this);
		}
		if (intLframesRelativeNumberTillTheoricDestination == objPjuggleMasterPro.intGthrowLastFrameIndex) {
			this.bolGjustThrown = true;
		} else {
			if (this.bolGjustThrown && this.bytGthrow != 2) {
				objPjuggleMasterPro.objGhandA[this.bytGthrowingHand].bytGthrowBallsVolume =
																							(byte) Math.max(objPjuggleMasterPro.objGhandA[this.bytGthrowingHand].bytGthrowBallsVolume,
																											Hand.getSoundsVolume(this.bytGthrow));
			}
			this.bolGjustThrown = false;
		}

		return (bolLnewThrow);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPjuggleMasterPro
	 * @param intPunknown
	 * @param bytPcatchingOrThrowingHand
	 */
	final private void setTheoricDestination(JuggleMasterPro objPjuggleMasterPro, int intPnextTimer, byte bytPcatchingOrThrowingHand) {

		int intLnextTimer = intPnextTimer;
		int intLdestinationStyleIndex;
		final boolean bolLmirror = objPjuggleMasterPro.bolGmirror;

		if (!objPjuggleMasterPro.objGsiteswap.bolGsynchro && (bolLmirror ^ (bytPcatchingOrThrowingHand == Constants.bytS_ENGINE_LEFT_SIDE))) {
			--intLnextTimer;
		}
		if ((intLnextTimer & Constants.bytS_BIT_0) != 0) {
			intLdestinationStyleIndex =
										((bolLmirror ? intLnextTimer + bytPcatchingOrThrowingHand - 1 : intLnextTimer - bytPcatchingOrThrowingHand) % (objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA.length / (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER)))
											* 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER + Constants.bytS_ENGINE_COORDONATES_NUMBER;
		} else {
			intLdestinationStyleIndex =
										((bolLmirror ? intLnextTimer + bytPcatchingOrThrowingHand : intLnextTimer + 1 - bytPcatchingOrThrowingHand) % (objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA.length / (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER)))
											* 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER;
		}

		try {
			final int intLzStyleIndex = intLdestinationStyleIndex / Constants.bytS_ENGINE_COORDONATES_NUMBER;
			this.bolGbodyBallZOrder = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbodyBallsZOrderA[intLzStyleIndex];
			this.bolGarmsBallZOrder = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGarmsBallsZOrderA[intLzStyleIndex];
			this.bolGvisible = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGballsVisibleA[intLzStyleIndex];
			this.bytGtheoricDestinationX =
											(byte) (Tools.getSignedBoolean(bytPcatchingOrThrowingHand == Constants.bytS_ENGINE_RIGHT_SIDE) * objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLdestinationStyleIndex]);
			this.bytGtheoricDestinationY = objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLdestinationStyleIndex + 1];
			this.bytGtheoricDestinationZ =
											(byte) (objPjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLdestinationStyleIndex + 2] * Tools.getSignedBoolean(this.bolGbodyBallZOrder));
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while calculating ball theoric destination");
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
								"color          = ",
								this.intGcolor,
								Strings.strS_LINE_SEPARATOR,
								"throw          = ",
								this.bytGthrow,
								Strings.strS_LINE_SEPARATOR,
								"posX           = ",
								this.intGposX,
								Strings.strS_LINE_SEPARATOR,
								"posY           = ",
								this.intGposY,
								Strings.strS_LINE_SEPARATOR,
								"visible        = ",
								this.bolGvisible,
								Strings.strS_LINE_SEPARATOR,
								"nextCatchTimer = ",
								this.intGnextCatchTimer,
								Strings.strS_LINE_SEPARATOR,
								"lastCatchTimer = ",
								this.intGlastCatchTimer,
								Strings.strS_LINE_SEPARATOR,
								"catchingHand   = ",
								this.bytGcatchingHand,
								Strings.strS_LINE_SEPARATOR,
								"throwingHand   = ",
								this.bytGthrowingHand,
								Strings.strS_LINE_SEPARATOR,
								"handStyleX     = ",
								this.bytGtheoricDestinationX,
								Strings.strS_LINE_SEPARATOR,
								"handStyleY     = ",
								this.bytGtheoricDestinationY,
								Strings.strS_LINE_SEPARATOR,
								"handStyleZ     = ",
								this.bytGtheoricDestinationZ,
								Strings.strS_LINE_SEPARATOR,
								"alternateColor = ",
								this.intGalternateColor,
								Strings.strS_LINE_SEPARATOR);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	// final private static void log(Object... objPobjectA) {
	// if (JuggleBall.bolS_UNCLASS_LOG) {
	// JuggleTools.log(JuggleStrings.doConcat(JuggleTools.getTraceString(), objPobjectA != null ? ' ' : null, objPobjectA));
	// }
	// }

	/**
	 * Method description
	 * 
	 * @see
	 */
	// @SuppressWarnings("unused") final private static void logIn() {
	// JuggleBall.log("In  :");
	// }

	/**
	 * Method description
	 * 
	 * @see
	 */
	// @SuppressWarnings("unused") final private static void logOut() {
	// JuggleBall.log("Out :");
	// }
}

/*
 * @(#)JuggleBall.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
