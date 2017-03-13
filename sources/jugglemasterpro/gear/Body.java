/*
 * @(#)JuggleBody.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

// package jugglemasterpro;
package jugglemasterpro.gear;

import java.awt.Graphics2D;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

// import static java.lang.Math.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class Body {

	public Body() {
		this.intGarmXAA = new int[2][6];
		this.intGarmYAA = new int[2][6];
		this.intchestXAA = new int[2][3];
		this.intGchestYAA = new int[2][3];
	}

	final public void drawArmPart(Graphics2D objPgraphics2D, float fltPframeSizeRatio, byte bytPside, byte bytParmIndex) {
		objPgraphics2D.drawLine(Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[bytPside][bytParmIndex]) * fltPframeSizeRatio),
								Math.round(this.intGarmYAA[bytPside][bytParmIndex] * fltPframeSizeRatio),
								Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[bytPside][bytParmIndex + 1]) * fltPframeSizeRatio),
								Math.round(this.intGarmYAA[bytPside][bytParmIndex + 1] * fltPframeSizeRatio));
	}

	final public void drawChest(Graphics2D objPgraphics2D, float fltPframeSizeRatio) {
		for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
			objPgraphics2D.drawLine(Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[bytLside][1]) * fltPframeSizeRatio),
									Math.round(this.intGchestYAA[bytLside][1] * fltPframeSizeRatio),
									Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[bytLside][2]) * fltPframeSizeRatio),
									Math.round(this.intGchestYAA[bytLside][2] * fltPframeSizeRatio));
		}
	}

	final public void drawEyes(Graphics2D objPgraphics2D, float fltPframeSizeRatio, boolean bolPfill) {

		// TODO : le rectangle contenant l'arc contient toute l'ellipse, en fait...

		final float fltLheadDeltaX = this.intGheadRadius / 3.9F;
		final float fltLheadDeltaY = this.intGheadRadius / 2.0F;
		// final float fltLwidth = this.intGheadRadius / 6.0F;
		// final float fltLheightY = this.intGheadRadius / 11.0F;
		final float fltLwidth = this.intGheadRadius;
		final float fltLheightY = this.intGheadRadius;
		final int[] intLposYA =
								new int[] { Math.round((this.intGheadY - fltLheadDeltaY - fltLheightY) * fltPframeSizeRatio),
									Math.round((this.intGheadY - fltLheadDeltaY - fltLheightY / 2) * fltPframeSizeRatio) };
		final int[] intLratioHeightA =
										new int[] { Math.round(3 * fltPframeSizeRatio * fltLheightY / 4),
											Math.round(fltLheightY * fltPframeSizeRatio / 2) };
		final int intLupperDeltaAngle = 105;
		final int intLlowerDeltaAngle = 75;

		final int[] intLposXA = new int[2];
		intLposXA[Constants.bytS_ENGINE_LEFT_SIDE] =
														Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGheadX - fltLheadDeltaX - fltLwidth)
																	* fltPframeSizeRatio);
		intLposXA[Constants.bytS_ENGINE_RIGHT_SIDE] =
														Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGheadX + fltLheadDeltaX)
																	* fltPframeSizeRatio);

		final int intLratioWidth = Math.round(fltLwidth * fltPframeSizeRatio);

		if (bolPfill) {

			// Left eye upper part :
			objPgraphics2D.fillArc(	intLposXA[Constants.bytS_ENGINE_LEFT_SIDE],
									intLposYA[0],
									intLratioWidth,
									intLratioHeightA[0],
									30,
									intLupperDeltaAngle);
			// Left eye lower part :
			objPgraphics2D.fillArc(	intLposXA[Constants.bytS_ENGINE_LEFT_SIDE],
									intLposYA[1],
									intLratioWidth,
									intLratioHeightA[1],
									225,
									intLlowerDeltaAngle);

			// Right eye upper part :
			objPgraphics2D.fillArc(	intLposXA[Constants.bytS_ENGINE_RIGHT_SIDE],
									intLposYA[0],
									intLratioWidth,
									intLratioHeightA[0],
									45,
									intLupperDeltaAngle);
			// Right eye lower part :
			objPgraphics2D.fillArc(	intLposXA[Constants.bytS_ENGINE_RIGHT_SIDE],
									intLposYA[1],
									intLratioWidth,
									intLratioHeightA[1],
									240,
									intLlowerDeltaAngle);
		} else {
			// Left eye upper part :
			objPgraphics2D.drawArc(	intLposXA[Constants.bytS_ENGINE_LEFT_SIDE],
									intLposYA[0],
									intLratioWidth,
									intLratioHeightA[0],
									30,
									intLupperDeltaAngle);
			// Left eye lower part :
			objPgraphics2D.drawArc(	intLposXA[Constants.bytS_ENGINE_LEFT_SIDE],
									intLposYA[1],
									intLratioWidth,
									intLratioHeightA[1],
									225,
									intLlowerDeltaAngle);

			// Right eye upper part :
			objPgraphics2D.drawArc(	intLposXA[Constants.bytS_ENGINE_RIGHT_SIDE],
									intLposYA[0],
									intLratioWidth,
									intLratioHeightA[0],
									45,
									intLupperDeltaAngle);
			// Right eye lower part :
			objPgraphics2D.drawArc(	intLposXA[Constants.bytS_ENGINE_RIGHT_SIDE],
									intLposYA[1],
									intLratioWidth,
									intLratioHeightA[1],
									240,
									intLlowerDeltaAngle);
		}

		// Left eye upper part :
		objPgraphics2D.drawRect(intLposXA[Constants.bytS_ENGINE_LEFT_SIDE], intLposYA[0], intLratioWidth, intLratioHeightA[0]);
		// Left eye lower part :
		objPgraphics2D.drawRect(intLposXA[Constants.bytS_ENGINE_LEFT_SIDE], intLposYA[1], intLratioWidth, intLratioHeightA[1]);

		// Right eye upper part :
		objPgraphics2D.drawRect(intLposXA[Constants.bytS_ENGINE_RIGHT_SIDE], intLposYA[0], intLratioWidth, intLratioHeightA[0]);
		// Right eye lower part :
		objPgraphics2D.drawRect(intLposXA[Constants.bytS_ENGINE_RIGHT_SIDE], intLposYA[1], intLratioWidth, intLratioHeightA[1]);
	}

	final public void drawHead(Graphics2D objPgraphics2D, float fltPframeSizeRatio, boolean bolPfill) {
		if (bolPfill) {
			objPgraphics2D.fillOval(Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGheadX - this.intGheadRadius - 2) * fltPframeSizeRatio),
									Math.round((this.intGheadY - this.intGheadRadius * 1.1F - 2) * fltPframeSizeRatio),
									Math.round((2 * this.intGheadRadius + 4) * fltPframeSizeRatio),
									Math.round((2 * this.intGheadRadius * 1.1F + 4) * fltPframeSizeRatio));
		} else {
			objPgraphics2D.drawOval(Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGheadX - this.intGheadRadius) * fltPframeSizeRatio),
									Math.round((this.intGheadY - this.intGheadRadius * 1.1F) * fltPframeSizeRatio),
									Math.round(2 * this.intGheadRadius * fltPframeSizeRatio),
									Math.round(2 * this.intGheadRadius * 1.1F * fltPframeSizeRatio));
		}
	}

	final public void fillBody(	Graphics2D objPgraphics2D,
								float fltPframeSizeRatio,
								boolean bolPtrailed,
								boolean bolPleftArmVisible,
								boolean bolPrightArmVisible) {
		final byte bytLdelta0 = (byte) (bolPtrailed ? 0 : 2);
		final byte bytLdelta1 = (byte) (bolPtrailed ? 1 : 2);
		objPgraphics2D.fillPolygon(	new int[] {
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][5] - bytLdelta0)
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][4] - bytLdelta0)
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][3] - bytLdelta0)
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X
													+ this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][bolPleftArmVisible ? 2 : 3] - bytLdelta1)
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][2])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][1])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][0])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][0])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][0])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][0])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][1])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][2])
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X
													+ this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][bolPrightArmVisible ? 2 : 3] + bytLdelta1)
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][3] + bytLdelta0)
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][4] + bytLdelta0)
													* fltPframeSizeRatio),
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][5] + bytLdelta0)
													* fltPframeSizeRatio) },
									new int[] {
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_LEFT_SIDE][5]) * fltPframeSizeRatio),
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_LEFT_SIDE][4] - bytLdelta0) * fltPframeSizeRatio),
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_LEFT_SIDE][3] - bytLdelta0) * fltPframeSizeRatio),
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_LEFT_SIDE][bolPleftArmVisible ? 2 : 3] - bytLdelta1)
													* fltPframeSizeRatio),
										Math.round((this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][2]) * fltPframeSizeRatio),
										Math.round((this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][1]) * fltPframeSizeRatio),
										Math.round((this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][0]) * fltPframeSizeRatio),
										Math.round(Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE * fltPframeSizeRatio),
										Math.round(Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE * fltPframeSizeRatio),
										Math.round((this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][0]) * fltPframeSizeRatio),
										Math.round((this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][1]) * fltPframeSizeRatio),
										Math.round((this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][2]) * fltPframeSizeRatio),
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_RIGHT_SIDE][bolPrightArmVisible ? 2 : 3] - bytLdelta1)
													* fltPframeSizeRatio),
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_RIGHT_SIDE][3] - bytLdelta0) * fltPframeSizeRatio),
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_RIGHT_SIDE][4] - bytLdelta0) * fltPframeSizeRatio),
										Math.round((this.intGarmYAA[Constants.bytS_ENGINE_RIGHT_SIDE][5]) * fltPframeSizeRatio) },
									16);
	}

	final public int getArmDeltaX() {
		return this.intGarmDeltaX;
	}

	final public int getArmDeltaY() {
		return this.intGarmDeltaY;
	}

	final public int getMaximumArmX() {
		int intLmaximumPosX = this.intGarmXAA[0][0];
		for (final int[] intLarmXA : this.intGarmXAA) {
			for (final int intLarmX : intLarmXA) {
				intLmaximumPosX = Math.max(intLmaximumPosX, intLarmX);
			}
		}
		return intLmaximumPosX;
	}

	final public int getMinimumArmX() {
		int intLminimumPosX = this.intGarmXAA[0][0];
		for (final int[] intLarmXA : this.intGarmXAA) {
			for (final int intLarmX : intLarmXA) {
				intLminimumPosX = Math.min(intLminimumPosX, intLarmX);
			}
		}
		return intLminimumPosX;
	}

	final public void setArmDeltaX(int intPvalue) {
		this.intGarmDeltaX = intPvalue;
	}

	final public void setArmDeltaY(int intPvalue) {
		this.intGarmDeltaY = intPvalue;
	}

	final public void setBodyLines(Hand[] objPhandA, int intPdrawingSizeRatio, int intPbaseY) {

		final int intLparameterX = intPdrawingSizeRatio / 4;
		final int intLparameterY = intPbaseY - intPdrawingSizeRatio / 3;

		for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
			final byte bytLdelta = Tools.getSignedBoolean(bytLside == Constants.bytS_ENGINE_RIGHT_SIDE);

			this.intGarmXAA[bytLside][0] =
											objPhandA[bytLside].intGposX + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + this.intGarmDeltaX
												* bytLdelta;
			this.intGarmYAA[bytLside][0] = objPhandA[bytLside].intGposY + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + this.intGarmDeltaY;
			this.intGarmXAA[bytLside][1] =
											(this.intGarmXAA[bytLside][0] + (Constants.intS_GRAPHICS_HORIZONTAL_CENTER + intLparameterX * bytLdelta) * 2)
												/ 3 + bytLdelta * intPdrawingSizeRatio / 12;
			this.intGarmYAA[bytLside][1] = (this.intGarmYAA[bytLside][0] + intLparameterY) / 2 + intPdrawingSizeRatio / 8;
			this.intGarmXAA[bytLside][2] =
											(this.intGarmXAA[bytLside][1] + (Constants.intS_GRAPHICS_HORIZONTAL_CENTER + intLparameterX * bytLdelta) * 3) / 4;
			this.intGarmYAA[bytLside][2] = (this.intGarmYAA[bytLside][1] + intLparameterY * 2) / 3 - intPdrawingSizeRatio / 25;
			this.intGarmXAA[bytLside][3] =
											(this.intGarmXAA[bytLside][2] + (Constants.intS_GRAPHICS_HORIZONTAL_CENTER + intLparameterX * bytLdelta) * 2)
												/ 3 - bytLdelta * intPdrawingSizeRatio / 13;
			this.intGarmYAA[bytLside][3] = (this.intGarmYAA[bytLside][2] + intLparameterY * 2) / 3 - intPdrawingSizeRatio / 40;
		}

		final int intLmiddleX3 = (this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][3] + this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][3]) / 2;
		final int intLmiddleY3 = (this.intGarmYAA[Constants.bytS_ENGINE_RIGHT_SIDE][3] + this.intGarmYAA[Constants.bytS_ENGINE_LEFT_SIDE][3]) / 2;

		this.intGheadX = intLmiddleX3;
		this.intGheadY = ((intLmiddleY3 * 2 - (intPdrawingSizeRatio * 2) / 3) + intPbaseY) / 3;
		this.intGheadRadius = intPdrawingSizeRatio / Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS;

		for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
			this.intGarmXAA[bytLside][4] = (intLmiddleX3 * 2 + this.intGarmXAA[bytLside][3]) / 3;
			this.intGarmYAA[bytLside][4] = (intLmiddleY3 * 2 + this.intGarmYAA[bytLside][3]) / 3;
			this.intGarmXAA[bytLside][5] =
											(this.intGheadX + Tools.getSignedBoolean(bytLside == Constants.bytS_ENGINE_RIGHT_SIDE)
																* intPdrawingSizeRatio / 20);
			this.intGarmYAA[bytLside][5] = this.intGheadY + intPdrawingSizeRatio / 13;
		}
		this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][1] =
																this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][3]
																	+ (this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][4] - this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][3])
																	/ 3;
		this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][1] =
																this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][3]
																	- (this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][3] - this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][4])
																	/ 3;
		this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][1] =
																this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][1] =
																															this.intGarmYAA[Constants.bytS_ENGINE_LEFT_SIDE][4]
																																+ this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][3]
																																- this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][3];

		this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][2] =
																(this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][3] + this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][1]) / 2;
		this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][2] =
																(this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][3] + this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][1]) / 2;
		this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][2] =
																(this.intGarmYAA[Constants.bytS_ENGINE_LEFT_SIDE][3] + this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][1]) / 2;
		this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][2] =
																	(this.intGarmYAA[Constants.bytS_ENGINE_RIGHT_SIDE][3] + this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][1]) / 2;

		this.intchestXAA[Constants.bytS_ENGINE_LEFT_SIDE][0] = this.intGarmXAA[Constants.bytS_ENGINE_LEFT_SIDE][3];
		this.intchestXAA[Constants.bytS_ENGINE_RIGHT_SIDE][0] = this.intGarmXAA[Constants.bytS_ENGINE_RIGHT_SIDE][3];
		this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][0] =
																2 * this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][1]
																	- this.intGchestYAA[Constants.bytS_ENGINE_LEFT_SIDE][2];
		this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][0] =
																	2 * this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][1]
																		- this.intGchestYAA[Constants.bytS_ENGINE_RIGHT_SIDE][2];
	}

	private final int[][]		intchestXAA;

	private int					intGarmDeltaX, intGarmDeltaY;

	private final int[][]		intGarmXAA, intGarmYAA;

	private final int[][]		intGchestYAA;

	private int					intGheadX, intGheadY, intGheadRadius;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	// final private static void log(Object... objPobjectA) {
	// if (JuggleBody.bolS_UNCLASS_LOG) {
	// JuggleTools.log(JuggleStrings.doConcat(objPobjectA, objPobjectA != null ? ' ' : null, JuggleTools.getTraceString()));
	// }
	// }
	//
	// @SuppressWarnings("unused") final private static void logIn() {
	// JuggleBody.log("In  :");
	// }
	//
	// @SuppressWarnings("unused") final private static void logOut() {
	// JuggleBody.log("Out :");
	// }
}

/*
 * @(#)JuggleBody.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

// ~ Formatted in Sun Code Convention
