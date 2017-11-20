/*
 * @(#)JuggleMasterProJFrame.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.engine.window;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
import fr.jugglemaster.engine.JuggleMasterPro;
import fr.jugglemaster.gear.BallTrail;
import fr.jugglemaster.gear.Hand;
import fr.jugglemaster.pattern.BallsColors;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class AnimationJFrame extends JFrame implements ComponentListener, MouseListener, WindowListener {

	final private static long					serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public boolean								bolGdontFocusJuggleMasterPro;								// TODO : bolGdontFocusJuggleMasterPro A JARTER

	// private boolean bolGmouseOver;

	public float								fltGframeSizeRatio;

	private BufferedImage						imgGanimationBuffer;

	private BufferedImage[]						imgGbackgroundBufferA;

	private BufferedImage[]						imgGballMaskingBufferA;

	private BufferedImage						imgGballsBackBackTrailsBuffer;

	private BufferedImage						imgGballsBackFrontTrailsBuffer;

	private BufferedImage						imgGballsFrontBackTrailsBuffer;

	private BufferedImage						imgGballsFrontFrontTrailsBuffer;

	private BufferedImage						imgGcurrentThrowCircleBuffer;

	private BufferedImage[][][]					imgGhandBufferAAA;

	public Image								imgGjmp;

	private BufferedImage						imgGjugglerBackTrailsBuffer;

	private BufferedImage						imgGjugglerFrontTrailsBuffer;

	private BufferedImage[][]					imgGmaskingHandBufferAA;

	public BufferedImage						imgGscreenPlayBuffer;

	private BufferedImage						imgGsiteswapBuffer;

	public int									intGanimationHeight;

	public int									intGanimationWidth;

	public int									intGbackgroundHeight;

	public int									intGbackgroundWidth;

	public int									intGballsWidth;

	public int									intGhandsHeight;

	public int									intGhandsWidth;

	public int									intGmarginHeight;

	public int									intGmarginWidth;

	private int									intGsiteswapWidth;

	private HashMap<Integer, Boolean[]>			objGballColorValueAHashMap;

	private HashMap<Integer, BufferedImage[]>	objGballColorValueBufferedImageAHashMap;

	private Font								objGboldFont;

	private final ControlJFrame					objGcontrolJFrame;

	private final JuggleMasterPro				objGjuggleMasterPro;

	private Font								objGnormalFont;

	private Font								objGthrowValuesBoldFont;

	private Font								objGthrowValuesNormalFont;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPjuggleMasterPro
	 */
	public AnimationJFrame(ControlJFrame objPcontrolJFrame, JuggleMasterPro objPjuggleMasterPro) {
		super("\370 JuggleMaster Pro");
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGjuggleMasterPro = objPjuggleMasterPro;
		this.bolGdontFocusJuggleMasterPro = false;
		this.fltGframeSizeRatio = 1.0F;
		this.intGanimationWidth = 0;
		this.intGanimationHeight = 0;
		this.intGbackgroundWidth = 0;
		this.intGbackgroundHeight = 0;
		this.intGmarginWidth = 0;
		this.intGmarginHeight = 0;
		this.intGsiteswapWidth = 0;
		// this.bolGmouseOver = false;

		// Icon :
		this.setIconImage(this.objGjuggleMasterPro.getImage(Constants.intS_FILE_ICON_FRAME, Constants.bytS_UNCLASS_NO_VALUE));
		this.imgGjmp = this.objGjuggleMasterPro.getImage(Constants.intS_FILE_ICON_JMP, 0);
		this.setCursor(Constants.objS_GRAPHICS_TOOLKIT.createCustomCursor(this.imgGjmp, new Point(0, 0), "JMP"));

		final int intLmiddleY = Constants.intS_GRAPHICS_SCREEN_Y + Constants.intS_GRAPHICS_SCREEN_HEIGHT / 2;
		final int intLposX = Constants.intS_GRAPHICS_SCREEN_X + Constants.intS_GRAPHICS_SCREEN_WIDTH / 2;
		final int intLposY = Math.max(Constants.intS_GRAPHICS_SCREEN_Y, intLmiddleY - Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE / 2);
		final int intLwidth = Math.min(Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE, Constants.intS_GRAPHICS_SCREEN_WIDTH / 2);
		final int intLheight = Math.min(Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE, Constants.intS_GRAPHICS_SCREEN_HEIGHT);
		this.setBounds(intLposX, intLposY, intLwidth, intLheight);
		this.setLayout(null);
		this.setTransferHandler(new ExtendedTransferHandler(this.objGcontrolJFrame, true, true));

		this.addWindowListener(this);
		this.addComponentListener(this);
		this.addMouseListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentHidden(ComponentEvent objPcomponentEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override public void componentMoved(ComponentEvent objPcomponentEvent) {
		synchronized (Constants.objS_ENGINE_FRAMES_LOCK_OBJECT) {
			this.setBounds();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentResized(ComponentEvent objPcomponentEvent) {
		synchronized (Constants.objS_ENGINE_FRAMES_LOCK_OBJECT) {
			this.setBounds();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentShown(ComponentEvent objPcomponentEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doActions() {

		if (this.objGjuggleMasterPro.intGactions != Constants.intS_ACTION_DO_NOTHING) {
			synchronized (Constants.objS_ENGINE_GRAPHICS_LOCK_OBJECT) {

				// Recreate background images :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_BACKGROUND_IMAGES)) {
					this.doCreateBackgroundImages();
				}

				// Recreate animation image :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_ANIMATION_IMAGE)) {
					this.doCreateAnimationImage();
				}

				// Reset animation properties :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_INIT_ANIMATION_PROPERTIES)) {
					this.objGjuggleMasterPro.initAnimationProperties();
					this.objGjuggleMasterPro.objGdataJFrame.initAnimationValues();
				}

				// Draw background :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					final Graphics objLanimationGraphics = this.imgGanimationBuffer.getGraphics();
					objLanimationGraphics.drawImage(this.imgGbackgroundBufferA[this.objGjuggleMasterPro.bytGlight], 0, 0, null);
					objLanimationGraphics.dispose();
				}

				// Reset juggler trails :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {

					this.imgGjugglerBackTrailsBuffer =
														Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGanimationWidth,
																													this.intGanimationHeight,
																													Transparency.TRANSLUCENT);
					this.imgGjugglerFrontTrailsBuffer =
														Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGanimationWidth,
																													this.intGanimationHeight,
																													Transparency.TRANSLUCENT);
				}

				// Reset colors :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_REINIT_COLORS)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					this.objGjuggleMasterPro.initBallsColors();
					this.doCreateBallsImages(Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_BALLS_IMAGES));

					if (this.objGjuggleMasterPro.objGsiteswap.intGballsNumber > 0 && this.objGjuggleMasterPro.objGballTrailALA[0].size() > 0) {
						for (int intLballIndex = 0; intLballIndex < this.objGjuggleMasterPro.objGsiteswap.intGballsNumber; ++intLballIndex) {
							final BallTrail objLballTrail = this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].get(0);
							objLballTrail.intGcolor =
														this.objGjuggleMasterPro.isAlternateBallColor(intLballIndex)
																													? this.objGjuggleMasterPro.objGballA[intLballIndex].intGalternateColor
																													: this.objGjuggleMasterPro.objGballA[intLballIndex].intGcolor;
							this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].remove(0);
							this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].add(0, objLballTrail);
						}
					}
				}

				// Reset balls trails :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					this.imgGballsFrontFrontTrailsBuffer =
															Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGanimationWidth,
																														this.intGanimationHeight,
																														Transparency.TRANSLUCENT);
					this.imgGballsFrontBackTrailsBuffer =
															Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGanimationWidth,
																														this.intGanimationHeight,
																														Transparency.TRANSLUCENT);
					this.imgGballsBackFrontTrailsBuffer =
															Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGanimationWidth,
																														this.intGanimationHeight,
																														Transparency.TRANSLUCENT);
					this.imgGballsBackBackTrailsBuffer =
															Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGanimationWidth,
																														this.intGanimationHeight,
																														Transparency.TRANSLUCENT);
				}
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RESET_BALLS)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					for (final ArrayList<BallTrail> objLballTrailAL : this.objGjuggleMasterPro.objGballTrailALA) {
						objLballTrailAL.clear();
					}
				}

				// Reset hand images :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_HANDS_IMAGES)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					this.doCreateHandsImages();
				}

				// Reset ball erasing images :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_BALLS_ERASING_IMAGES)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					this.doCreateBallsErasingImages();
				}

				// Reset ball images :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_BALLS_IMAGES)
					&& !Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_REINIT_COLORS)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					this.doCreateBallsImages(true);
				}

				// Reset style :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RESET_STYLE)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					for (int intLindex = 0; intLindex * this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA.length < this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA.length; ++intLindex) {
						System.arraycopy(	this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA,
											0,
											this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA,
											intLindex * this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA.length,
											this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA.length);
					}
				}

				// Reset siteswap image :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_RECREATE_SITESWAP_IMAGE)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					this.objGjuggleMasterPro.getSiteswap().setSequenceA(this.intGsiteswapWidth);
					this.doCreateSiteswapImage();
				}

				// Redraw siteswap :
				if (Tools.intersects(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_DRAW_SITESWAP
																			| Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE)
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					Tools.debug("AnimationJFrame.doActions(): AnimationJFrame.doDrawSiteswap()");
					this.doDrawSiteswap();
				}

				// Refresh drawing :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_REFRESH_DRAWING)
					&& this.objGjuggleMasterPro.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED
					&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
					this.doDrawJugglerAndBalls();
					if (!Tools.intersects(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_DRAW_SITESWAP
																				| Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE)
						&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
						Tools.debug("AnimationJFrame.doActions(): AnimationJFrame.doDrawSiteswap()");
						this.doDrawSiteswap();
					}
				}

				// Animation data frame :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_HIDE_DATA_FRAME)) {
					this.objGjuggleMasterPro.objGdataJFrame.setVisible(false);
				} else if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_SHOW_DATA_FRAME)) {
					this.objGjuggleMasterPro.objGdataJFrame.setVisible(true);
				} else if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_INIT_DATA_FRAME)) {
					this.objGjuggleMasterPro.objGdataJFrame.setVisible();
				}

				// Titles :
				if (Tools.contains(this.objGjuggleMasterPro.intGactions, Constants.intS_ACTION_INIT_TITLES)) {
					this.objGcontrolJFrame.setTitles();
				}

				this.objGjuggleMasterPro.intGactions = Constants.intS_ACTION_DO_NOTHING;
			}
		}
	}

	final public void doCreateAnimationImage() {
		// Tools.verbose("On rentre dans doCreateAnimationImage()");
		this.imgGanimationBuffer =
									Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(Math.max(1, this.intGanimationWidth),
																								Math.max(1, this.intGanimationHeight),
																								Transparency.OPAQUE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doCreateBackgroundImages() {
		// Tools.verbose("On rentre dans doCreateBackgroundImages()");
		this.imgGbackgroundBufferA = new BufferedImage[2];
		for (int intLbackgroundIndex = Constants.bytS_ENGINE_NIGHT; intLbackgroundIndex <= Constants.bytS_ENGINE_DAY; ++intLbackgroundIndex) {
			this.imgGbackgroundBufferA[intLbackgroundIndex] =
																Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(Constants.intS_GRAPHICS_SCREEN_WIDTH,
																															Constants.intS_GRAPHICS_SCREEN_HEIGHT,
																															Transparency.OPAQUE);

			final Graphics2D objLbackgroundGraphics2D = (Graphics2D) this.imgGbackgroundBufferA[intLbackgroundIndex].getGraphics();

			objLbackgroundGraphics2D.setBackground(this.objGjuggleMasterPro.objGbackgroundColorA[intLbackgroundIndex]);
			objLbackgroundGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[intLbackgroundIndex]);
			objLbackgroundGraphics2D.fillRect(0, 0, Constants.intS_GRAPHICS_SCREEN_WIDTH, Constants.intS_GRAPHICS_SCREEN_HEIGHT);
			objLbackgroundGraphics2D.dispose();
		}
	}

	final private void doCreateBallsErasingImages() {

		final int intLballRadius =
									(Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS * this.objGjuggleMasterPro.intGdrawingSize)
										/ Constants.intS_GRAPHICS_MAXIMUM_DRAWING_SIZE;
		final int intLimageWidth = (int) Math.ceil(2 * Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS * this.fltGframeSizeRatio);
		final int intLimageHeight = (int) Math.ceil(2 * Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS * this.fltGframeSizeRatio);
		this.imgGballMaskingBufferA = new BufferedImage[2];

		for (byte bytLmode = Constants.bytS_ENGINE_NIGHT; bytLmode <= Constants.bytS_ENGINE_DAY; ++bytLmode) {
			this.imgGballMaskingBufferA[bytLmode] =
													Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(intLimageWidth,
																												intLimageHeight,
																												Transparency.TRANSLUCENT);

			final Graphics2D objLballMaskingGraphics2D = (Graphics2D) this.imgGballMaskingBufferA[bytLmode].getGraphics();

			objLballMaskingGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			objLballMaskingGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[bytLmode]);

			objLballMaskingGraphics2D.fillOval(	(int) Math.floor((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius)
																	* this.fltGframeSizeRatio) - 3,
												(int) Math.floor((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius)
																	* this.fltGframeSizeRatio) - 3,
												(int) Math.ceil(2 * intLballRadius * this.fltGframeSizeRatio) + 6,
												(int) Math.ceil(2 * intLballRadius * this.fltGframeSizeRatio) + 6);

			objLballMaskingGraphics2D.dispose();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateBallsImages(boolean bolPclear) {

		// Get useful color values :
		final ArrayList<Integer> objLimageColorValueAL = this.objGjuggleMasterPro.objGballColors.getUsefulLogicalColorsValues();
		if (objLimageColorValueAL.size() == 0
		/*
		 * && this.objGjuggleMasterPro .getControlString(this.objGjuggleMasterPro.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
		 * ? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
		 * : Constants.bytS_STRING_LOCAL_COLORS)
		 * .length() == 0
		 */) {
			// Tools.verbose( "Hop !!! JuggleCouleurs : ",
			// this.objGjuggleMasterPro.objGballColors,
			// " - qui donne en useful :\r\n",
			// this.objGjuggleMasterPro.objGballColors.getUsefulColorsValues(),
			// ", à partir des appels suivants :");
			// JuggleTools.trace(true);
			return;
		}
		// Add trace color values :
		if (this.objGjuggleMasterPro.objGballTrailALA != null) {
			for (final ArrayList<BallTrail> objLballTrailAL : this.objGjuggleMasterPro.objGballTrailALA) {
				for (final BallTrail objLballTrail : objLballTrailAL) {
					if (!objLimageColorValueAL.contains(objLballTrail.intGcolor)) {
						objLimageColorValueAL.add(objLballTrail.intGcolor);
					}
				}
			}
		}

		// No color at all :
		if (objLimageColorValueAL.size() == 0) {
			this.objGballColorValueBufferedImageAHashMap = null;
			this.objGballColorValueAHashMap = null;
			return;
		}

		// Transform list into array :
		final int[] intLimageLogicalBallColorValueA = new int[objLimageColorValueAL.size()];
		for (int intLcolorIndex = 0; intLcolorIndex < intLimageLogicalBallColorValueA.length; ++intLcolorIndex) {
			intLimageLogicalBallColorValueA[intLcolorIndex] = objLimageColorValueAL.get(intLcolorIndex);
		}

		// Set image size :
		final int intLballRadius =
									(Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS * this.objGjuggleMasterPro.intGdrawingSize)
										/ Constants.intS_GRAPHICS_MAXIMUM_DRAWING_SIZE;
		this.intGballsWidth = (int) Math.ceil(2 * Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS * this.fltGframeSizeRatio);

		final HashMap<Integer, BufferedImage[]> objLbufferedImageHM = new HashMap<Integer, BufferedImage[]>(intLimageLogicalBallColorValueA.length);
		final HashMap<Integer, Boolean[]> objLcolorValueHM = new HashMap<Integer, Boolean[]>(intLimageLogicalBallColorValueA.length);
		final byte bytLgammaCorrection = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION);
		for (int intLimageIndex = 0; intLimageIndex < intLimageLogicalBallColorValueA.length; ++intLimageIndex) {

			// Put old images if no clear to do :
			if (!bolPclear && this.objGballColorValueBufferedImageAHashMap != null
				&& this.objGballColorValueBufferedImageAHashMap.containsKey(intLimageLogicalBallColorValueA[intLimageIndex])) {
				objLbufferedImageHM.put(intLimageLogicalBallColorValueA[intLimageIndex],
										this.objGballColorValueBufferedImageAHashMap.get(intLimageLogicalBallColorValueA[intLimageIndex]));
				objLcolorValueHM.put(	intLimageLogicalBallColorValueA[intLimageIndex],
										this.objGballColorValueAHashMap.get(intLimageLogicalBallColorValueA[intLimageIndex]));
				continue;
			}

			// Build colored ball images :
			final byte bytLcolorLetterValue = BallsColors.getBallColorLetterValue(intLimageLogicalBallColorValueA[intLimageIndex]);
			final byte bytLcolorShadeValue = BallsColors.getBallColorShadeValue(intLimageLogicalBallColorValueA[intLimageIndex], true);
			final BufferedImage[] objLbufferedImageA = new BufferedImage[2];
			final Boolean[] bolLlightnessBooleanA = new Boolean[2];
			final Color[] objLcolorA = new Color[2];
			for (byte bytLcolorMode = 0; bytLcolorMode <= Constants.bytS_ENGINE_LIGHT; ++bytLcolorMode) {
				objLcolorA[bytLcolorMode] =
											BallsColors.getGammaBallColor(	BallsColors.getLogicalColorValue(	bytLcolorLetterValue,
																												(byte) (bytLcolorShadeValue + (bytLcolorMode == Constants.bytS_ENGINE_LIGHT
																																															? 1
																																															: -1))),
																			bytLgammaCorrection,
																			false);
			}
			for (byte bytLcolorMode = 0; bytLcolorMode <= Constants.bytS_ENGINE_LIGHT; ++bytLcolorMode) {
				objLbufferedImageA[bytLcolorMode] =
													Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGballsWidth,
																												this.intGballsWidth,
																												Transparency.TRANSLUCENT);
				final Graphics2D objLballGraphics2D = (Graphics2D) objLbufferedImageA[bytLcolorMode].getGraphics();
				objLballGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				// Draw border :
				objLballGraphics2D.setColor(objLcolorA[bytLcolorMode == Constants.bytS_ENGINE_LIGHT ? Constants.bytS_ENGINE_DARK
																									: Constants.bytS_ENGINE_LIGHT]);
				objLballGraphics2D.fillOval(Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius) * this.fltGframeSizeRatio),
											Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius) * this.fltGframeSizeRatio),
											Math.round(2 * intLballRadius * this.fltGframeSizeRatio),
											Math.round(2 * intLballRadius * this.fltGframeSizeRatio));
				// Fill inside :
				bolLlightnessBooleanA[bytLcolorMode] = Tools.isLightColor(objLcolorA[bytLcolorMode]);
				objLballGraphics2D.setColor(objLcolorA[bytLcolorMode]);
				objLballGraphics2D.fillOval(Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius) * this.fltGframeSizeRatio) + 1,
											Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius) * this.fltGframeSizeRatio) + 1,
											Math.round(2 * intLballRadius * this.fltGframeSizeRatio) - 2,
											Math.round(2 * intLballRadius * this.fltGframeSizeRatio) - 2);
				objLballGraphics2D.dispose();
			}
			objLbufferedImageHM.put(intLimageLogicalBallColorValueA[intLimageIndex], objLbufferedImageA);
			objLcolorValueHM.put(intLimageLogicalBallColorValueA[intLimageIndex], bolLlightnessBooleanA);
		}
		this.objGballColorValueBufferedImageAHashMap = objLbufferedImageHM;
		this.objGballColorValueAHashMap = objLcolorValueHM;

		// Construct round throw circles :
		if (bolPclear) {

			this.imgGcurrentThrowCircleBuffer =
												Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGballsWidth,
																											this.intGballsWidth,
																											Transparency.TRANSLUCENT);
			final Graphics2D objLcircleGraphics2D = (Graphics2D) this.imgGcurrentThrowCircleBuffer.getGraphics();
			objLcircleGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			objLcircleGraphics2D.setStroke(new BasicStroke(1.5F * this.fltGframeSizeRatio, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			for (int intLdelta = 0; intLdelta < intLballRadius * this.fltGframeSizeRatio; intLdelta++) {
				objLcircleGraphics2D.setColor(intLdelta % 2 == 0 ? Color.BLACK : Color.WHITE);
				objLcircleGraphics2D.setComposite(AlphaComposite.getInstance(	AlphaComposite.SRC_OVER,
																				(float) Math.pow(	1.0F - (intLdelta / (intLballRadius * this.fltGframeSizeRatio)),
																									4)));
				objLcircleGraphics2D.drawOval(	Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius) * this.fltGframeSizeRatio
															+ intLdelta),
												Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS - intLballRadius) * this.fltGframeSizeRatio
															+ intLdelta),
												Math.round(2.0F * (intLballRadius * this.fltGframeSizeRatio - intLdelta)),
												Math.round(2.0F * (intLballRadius * this.fltGframeSizeRatio - intLdelta)));
			}

			// Set throw value font size :
			float fltLsize = 2.0F;
			final String strLstandard = "mm";
			while (objLcircleGraphics2D.getFontMetrics(this.objGcontrolJFrame.getFont().deriveFont(fltLsize)).stringWidth(strLstandard) < 2
																																			* intLballRadius
																																			* this.fltGframeSizeRatio) {
				fltLsize += 1.0F;
			}
			this.objGthrowValuesNormalFont = this.objGcontrolJFrame.getFont().deriveFont(fltLsize - 1.0F);
			this.objGthrowValuesBoldFont = this.objGthrowValuesNormalFont.deriveFont(Font.BOLD);
			objLcircleGraphics2D.dispose();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateHandsImages() {

		final int[] intLbodyMatrixA = { 0, 18, 0, 23, 17, 23, 20, 22, 22, 20, 23, 17, 23, 12, 18, 12, 18, 16, 16, 18, 0, 18, 12, 15, 23, 17 };

		for (byte bytLmatrixIndex = 0; bytLmatrixIndex < intLbodyMatrixA.length; ++bytLmatrixIndex) {
			intLbodyMatrixA[bytLmatrixIndex] =
												((intLbodyMatrixA[bytLmatrixIndex] - Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS) * this.objGjuggleMasterPro.intGdrawingSize)
													/ Constants.intS_GRAPHICS_MAXIMUM_DRAWING_SIZE;
		}
		this.objGjuggleMasterPro.intGhandDeltaX = intLbodyMatrixA[22] + 2;
		this.objGjuggleMasterPro.intGhandDeltaY = intLbodyMatrixA[23] + 2;
		this.objGjuggleMasterPro.objGbody.setArmDeltaX(intLbodyMatrixA[24]);
		this.objGjuggleMasterPro.objGbody.setArmDeltaY(intLbodyMatrixA[25]);

		this.imgGhandBufferAAA = new BufferedImage[2][2][3]; // [night/day][left/right][thin/normal/thick]
		this.imgGmaskingHandBufferAA = new BufferedImage[2][2]; // [night/day][left/right]

		final Graphics2D[][][] objLhandGraphics2DAAA = new Graphics2D[2][2][3];
		final Graphics2D[][] objLmaskingHandGraphics2DAA = new Graphics2D[2][2];

		this.intGhandsWidth = (int) Math.ceil(3 * Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS * this.fltGframeSizeRatio);
		this.intGhandsHeight = (int) Math.ceil(24 * this.fltGframeSizeRatio);
		// Setting graphics properties for each hand draw mode :
		for (byte bytLnightToDay = Constants.bytS_ENGINE_NIGHT; bytLnightToDay <= Constants.bytS_ENGINE_DAY; ++bytLnightToDay) {
			for (byte bytLleftToRight = Constants.bytS_ENGINE_LEFT_SIDE; bytLleftToRight <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLleftToRight) {
				for (byte bytLthickness = Constants.bytS_ENGINE_THICKNESS_THIN; bytLthickness <= Constants.bytS_ENGINE_THICKNESS_STRONG; ++bytLthickness) {

					// Visible hand image :
					this.imgGhandBufferAAA[bytLnightToDay][bytLleftToRight][bytLthickness] =
																								Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGhandsWidth,
																																							this.intGhandsHeight,
																																							Transparency.TRANSLUCENT);
					objLhandGraphics2DAAA[bytLnightToDay][bytLleftToRight][bytLthickness] =
																							(Graphics2D) this.imgGhandBufferAAA[bytLnightToDay][bytLleftToRight][bytLthickness].getGraphics();

					// Blank outline thickness :
					objLhandGraphics2DAAA[bytLnightToDay][bytLleftToRight][bytLthickness].setRenderingHint(	RenderingHints.KEY_ANTIALIASING,
																											RenderingHints.VALUE_ANTIALIAS_ON);
					objLhandGraphics2DAAA[bytLnightToDay][bytLleftToRight][bytLthickness].setStroke(new BasicStroke(bytLthickness == Constants.bytS_ENGINE_THICKNESS_THIN
																																											? 6.5F
																																											: 6.5F,
																													bytLthickness == Constants.bytS_ENGINE_THICKNESS_THIN
																																											? BasicStroke.CAP_ROUND
																																											: BasicStroke.CAP_BUTT,
																													bytLthickness == Constants.bytS_ENGINE_THICKNESS_THIN
																																											? BasicStroke.JOIN_ROUND
																																											: BasicStroke.JOIN_BEVEL));

					// Blank outline color :
					objLhandGraphics2DAAA[bytLnightToDay][bytLleftToRight][bytLthickness].setColor(this.objGjuggleMasterPro.objGbackgroundColorA[bytLnightToDay]);
				}

				// Masking hand image :
				this.imgGmaskingHandBufferAA[bytLnightToDay][bytLleftToRight] =
																				Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(this.intGhandsWidth,
																																			this.intGhandsHeight,
																																			Transparency.TRANSLUCENT);
				objLmaskingHandGraphics2DAA[bytLnightToDay][bytLleftToRight] =
																				(Graphics2D) this.imgGmaskingHandBufferAA[bytLnightToDay][bytLleftToRight].getGraphics();

				// Masking thickness :
				objLmaskingHandGraphics2DAA[bytLnightToDay][bytLleftToRight].setRenderingHint(	RenderingHints.KEY_ANTIALIASING,
																								RenderingHints.VALUE_ANTIALIAS_ON);
				objLmaskingHandGraphics2DAA[bytLnightToDay][bytLleftToRight].setStroke(new BasicStroke(	4.5F,
																										BasicStroke.CAP_ROUND,
																										BasicStroke.JOIN_ROUND));

				// Masking color :
				objLmaskingHandGraphics2DAA[bytLnightToDay][bytLleftToRight].setColor(this.objGjuggleMasterPro.objGbackgroundColorA[bytLnightToDay]);
			}
		}

		// Drawing visible hand outlines :
		for (byte bytLmatrixIndex = 0; bytLmatrixIndex < 20; bytLmatrixIndex += 2) {
			for (byte bytLnightToDay = Constants.bytS_ENGINE_NIGHT; bytLnightToDay <= Constants.bytS_ENGINE_DAY; ++bytLnightToDay) {
				for (byte bytLthickness = Constants.bytS_ENGINE_THICKNESS_THIN; bytLthickness <= Constants.bytS_ENGINE_THICKNESS_STRONG; ++bytLthickness) {

					// Left bytLside :
					objLhandGraphics2DAAA[bytLnightToDay][Constants.bytS_ENGINE_LEFT_SIDE][bytLthickness].drawLine(	Math.round((11 - intLbodyMatrixA[bytLmatrixIndex + 0])
																																* this.fltGframeSizeRatio),
																													Math.round((11 + intLbodyMatrixA[bytLmatrixIndex + 1])
																																* this.fltGframeSizeRatio),
																													Math.round((11 - intLbodyMatrixA[bytLmatrixIndex + 2])
																																* this.fltGframeSizeRatio),
																													Math.round((11 + intLbodyMatrixA[bytLmatrixIndex + 3])
																																* this.fltGframeSizeRatio));

					// Right bytLside :
					objLhandGraphics2DAAA[bytLnightToDay][Constants.bytS_ENGINE_RIGHT_SIDE][bytLthickness].drawLine(Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + intLbodyMatrixA[bytLmatrixIndex + 0])
																																* this.fltGframeSizeRatio) - 1,
																													Math.round((11 + intLbodyMatrixA[bytLmatrixIndex + 1])
																																* this.fltGframeSizeRatio),
																													Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + intLbodyMatrixA[bytLmatrixIndex + 2])
																																* this.fltGframeSizeRatio) - 1,
																													Math.round((11 + intLbodyMatrixA[bytLmatrixIndex + 3])
																																* this.fltGframeSizeRatio));
				}
			}
		}

		// Setting graphics properties for the visible inline hand draw :
		for (byte bytLnightToDay = Constants.bytS_ENGINE_NIGHT; bytLnightToDay <= Constants.bytS_ENGINE_DAY; ++bytLnightToDay) {
			for (byte bytLleftToRight = Constants.bytS_ENGINE_LEFT_SIDE; bytLleftToRight <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLleftToRight) {
				for (byte bytLthickness = Constants.bytS_ENGINE_THICKNESS_THIN; bytLthickness <= Constants.bytS_ENGINE_THICKNESS_STRONG; ++bytLthickness) {
					objLhandGraphics2DAAA[bytLnightToDay][bytLleftToRight][bytLthickness].setStroke(new BasicStroke(bytLthickness == Constants.bytS_ENGINE_THICKNESS_THIN
																																											? 1.5F
																																											: bytLthickness == Constants.bytS_ENGINE_THICKNESS_NORMAL
																																																										? 2.0F
																																																										: 2.5F,
																													BasicStroke.CAP_ROUND,
																													BasicStroke.JOIN_ROUND));
					objLhandGraphics2DAAA[bytLnightToDay][bytLleftToRight][bytLthickness].setColor(this.objGjuggleMasterPro.objGjugglerColorA[bytLnightToDay]);
				}
			}
		}

		// Drawing visible hand inlines & masking hands :
		for (byte bytLmatrixIndex = 0; bytLmatrixIndex < 20; bytLmatrixIndex += 2) {
			for (byte bytLnightToDay = Constants.bytS_ENGINE_NIGHT; bytLnightToDay <= Constants.bytS_ENGINE_DAY; ++bytLnightToDay) {
				for (byte bytLthickness = Constants.bytS_ENGINE_THICKNESS_THIN; bytLthickness <= Constants.bytS_ENGINE_THICKNESS_STRONG; ++bytLthickness) {

					// Left visible hand :
					objLhandGraphics2DAAA[bytLnightToDay][Constants.bytS_ENGINE_LEFT_SIDE][bytLthickness].drawLine(	Math.round((12 - intLbodyMatrixA[bytLmatrixIndex + 0])
																																* this.fltGframeSizeRatio),
																													Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 1])
																																* this.fltGframeSizeRatio),
																													Math.round((12 - intLbodyMatrixA[bytLmatrixIndex + 2])
																																* this.fltGframeSizeRatio),
																													Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 3])
																																* this.fltGframeSizeRatio));

					// Right visible hand :
					objLhandGraphics2DAAA[bytLnightToDay][Constants.bytS_ENGINE_RIGHT_SIDE][bytLthickness].drawLine(Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + intLbodyMatrixA[bytLmatrixIndex + 0])
																																* this.fltGframeSizeRatio),
																													Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 1])
																																* this.fltGframeSizeRatio),
																													Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + intLbodyMatrixA[bytLmatrixIndex + 2])
																																* this.fltGframeSizeRatio),
																													Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 3])
																																* this.fltGframeSizeRatio));
				}

				// Left masking hand :
				objLmaskingHandGraphics2DAA[bytLnightToDay][Constants.bytS_ENGINE_LEFT_SIDE].drawLine(	Math.round((12 - intLbodyMatrixA[bytLmatrixIndex + 0])
																													* this.fltGframeSizeRatio),
																										Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 1])
																													* this.fltGframeSizeRatio),
																										Math.round((12 - intLbodyMatrixA[bytLmatrixIndex + 2])
																													* this.fltGframeSizeRatio),
																										Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 3])
																													* this.fltGframeSizeRatio));

				// Right masking hand :
				objLmaskingHandGraphics2DAA[bytLnightToDay][Constants.bytS_ENGINE_RIGHT_SIDE].drawLine(	Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + intLbodyMatrixA[bytLmatrixIndex + 0])
																													* this.fltGframeSizeRatio),
																										Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 1])
																													* this.fltGframeSizeRatio),
																										Math.round((Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS + intLbodyMatrixA[bytLmatrixIndex + 2])
																													* this.fltGframeSizeRatio),
																										Math.round((10 + intLbodyMatrixA[bytLmatrixIndex + 3])
																													* this.fltGframeSizeRatio));
			}
		}

		// Dispose graphics :
		for (byte bytLnightToDay = Constants.bytS_ENGINE_NIGHT; bytLnightToDay <= Constants.bytS_ENGINE_DAY; ++bytLnightToDay) {
			for (byte bytLleftToRight = Constants.bytS_ENGINE_LEFT_SIDE; bytLleftToRight <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLleftToRight) {
				for (byte bytLthickness = Constants.bytS_ENGINE_THICKNESS_THIN; bytLthickness <= Constants.bytS_ENGINE_THICKNESS_STRONG; ++bytLthickness) {
					objLhandGraphics2DAAA[bytLnightToDay][bytLleftToRight][bytLthickness].dispose();
				}
				objLmaskingHandGraphics2DAA[bytLnightToDay][bytLleftToRight].dispose();
			}
		}
	}

	final private void doCreateSiteswapImage() {
		this.imgGsiteswapBuffer =
									Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(Math.max(1, this.intGsiteswapWidth),
																								Constants.intS_GRAPHICS_SITESWAP_HEIGHT,
																								Transparency.OPAQUE);
	}

	final private boolean doDrawArm(Graphics2D objLgraphics2D, boolean bolPbodyFront, byte bytPside) {

		boolean bolLdrewSomething = false;

		if (this.objGjuggleMasterPro.objGhandA[bytPside].bolGarmVisible
			&& (this.objGjuggleMasterPro.objGhandA[bytPside].fltGposZ < 0F && !bolPbodyFront
				|| this.objGjuggleMasterPro.objGhandA[bytPside].fltGposZ == 0F
				&& this.objGjuggleMasterPro.objGhandA[bytPside].bolGbodyHandZOrder == bolPbodyFront || this.objGjuggleMasterPro.objGhandA[bytPside].fltGposZ > 0F
																										&& bolPbodyFront)) {
			final boolean bolLfrontHands = this.objGjuggleMasterPro.objGhandA[bytPside].bolGarmHandZOrder;
			bolLdrewSomething = true;
			for (byte bytLarmIndex = (byte) (bolLfrontHands ? 1 : 0); bolLfrontHands ? bytLarmIndex >= 0 : bytLarmIndex <= 1; bytLarmIndex +=
																																				(byte) (bolLfrontHands
																																										? -1
																																										: 1)) {

				// Arm border :
				if (!Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_TRAIL)) {
					objLgraphics2D.setStroke(new BasicStroke(	bytLarmIndex == 1 ? bolPbodyFront ? 6.0F : 7.0F : 7.5F,
																BasicStroke.CAP_BUTT,
																BasicStroke.JOIN_BEVEL));
					objLgraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
					this.objGjuggleMasterPro.objGbody.drawArmPart(objLgraphics2D, this.fltGframeSizeRatio, bytPside, bytLarmIndex);
				}

				// Arm :
				objLgraphics2D.setStroke(new BasicStroke(	this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].getArmWidth(this.objGjuggleMasterPro.objGhandA[bytPside].fltGposZ,
																																			bytLarmIndex,
																																			this.objGjuggleMasterPro.objGhandA[bytPside].bolGarmHandZOrder),
															// bolPbodyFront ? bytLarmIndex == 1 ? 2F
															// : 2.5F : 1.5F,
															BasicStroke.CAP_ROUND,
															BasicStroke.JOIN_ROUND));
				objLgraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
				this.objGjuggleMasterPro.objGbody.drawArmPart(objLgraphics2D, this.fltGframeSizeRatio, bytPside, bytLarmIndex);
			}
		}
		return bolLdrewSomething;
	}

	final private boolean doDrawArmsAndHands(boolean bolPbodyFront) {

		boolean bolLdrewSomething = false;

		// Set graphics :
		final Graphics2D objLjugglerGraphics2D =
													(Graphics2D) (Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																					Constants.bytS_BIT_VISIBILITY_TRAIL)
																														? bolPbodyFront
																																		? this.imgGjugglerFrontTrailsBuffer.getGraphics()
																																		: this.imgGjugglerBackTrailsBuffer.getGraphics()
																														: this.imgGanimationBuffer.getGraphics());
		objLjugglerGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.doOptimizeGraphics(objLjugglerGraphics2D);

		final boolean bolLleftHandDeeper =
											Tools.intersects(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HANDS
																												| Constants.bytS_BIT_VISIBILITY_ARMS)
												&& (this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].fltGposZ < this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].fltGposZ || this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].fltGposZ == this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].fltGposZ
																																																					&& (!this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bolGbodyHandZOrder
																																																						&& this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bolGbodyHandZOrder || this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bolGbodyHandZOrder == this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bolGbodyHandZOrder
																																																																														&& this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bolGarmHandZOrder
																																																																														&& !this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bolGarmHandZOrder));

		// Hands & arms :
		objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
		for (byte bytLside = (bolLleftHandDeeper ? Constants.bytS_ENGINE_LEFT_SIDE : Constants.bytS_ENGINE_RIGHT_SIDE); bolLleftHandDeeper
																																			? bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE
																																			: bytLside >= Constants.bytS_ENGINE_LEFT_SIDE; bytLside +=
																																																		Tools.getSignedBoolean(bolLleftHandDeeper)) {
			if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HANDS)) {
				bolLdrewSomething |= this.doDrawHand(objLjugglerGraphics2D, bolPbodyFront, false, bytLside);
			}
			if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_ARMS)) {
				bolLdrewSomething |= this.doDrawArm(objLjugglerGraphics2D, bolPbodyFront, bytLside);
			}
			if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HANDS)) {
				bolLdrewSomething |= this.doDrawHand(objLjugglerGraphics2D, bolPbodyFront, true, bytLside);
			}
		}

		objLjugglerGraphics2D.dispose();

		if (bolLdrewSomething && Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_TRAIL)) {
			final Graphics objLbackgroundGraphics2D = this.imgGanimationBuffer.getGraphics();
			objLbackgroundGraphics2D.drawImage(bolPbodyFront ? this.imgGjugglerFrontTrailsBuffer : this.imgGjugglerBackTrailsBuffer, 0, 0, null);
			objLbackgroundGraphics2D.dispose();
		}
		return bolLdrewSomething;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics2D
	 * @param objPballTrail
	 */
	final private void doDrawBall(Graphics2D objPgraphics2D, BallTrail objPballTrail, boolean bolPlighterColors, boolean bolPcurrentThrow) {

		// Draw ball :
		final int intLcolor =
								this.objGjuggleMasterPro.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_COLORS)
																												? objPballTrail.intGcolor
																												: BallsColors.intS_BALLS_COLORS_DEFAULT_VALUE;

		try {
			final BufferedImage imgLbuffer =
												this.objGballColorValueBufferedImageAHashMap.get(intLcolor)[bolPlighterColors
																																? Constants.bytS_ENGINE_LIGHT
																																: Constants.bytS_ENGINE_DARK];
			objPgraphics2D.drawImage(	imgLbuffer,
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + objPballTrail.intGposX) * this.fltGframeSizeRatio),
										Math.round(objPballTrail.intGposY * this.fltGframeSizeRatio),
										null);

			// Draw current throw ball circle :
			if (bolPcurrentThrow) {
				objPgraphics2D.drawImage(	this.imgGcurrentThrowCircleBuffer,
											Math.round((Constants.intS_GRAPHICS_CORRECTION_X + objPballTrail.intGposX) * this.fltGframeSizeRatio),
											Math.round(objPballTrail.intGposY * this.fltGframeSizeRatio),
											null);
			}
		} catch (final Throwable objPthrowable) {
			Tools.err(	"Error while drawing ball (",
							intLcolor,
							") - ",
							this.objGballColorValueBufferedImageAHashMap.keySet(),
							"\n",
							this.objGballColorValueBufferedImageAHashMap.values());
			Tools.debug(this.objGjuggleMasterPro.objGpatternsManager.toPatternString(	this.objGjuggleMasterPro.intGobjectIndex,
																						Constants.bytS_EXTENSION_JMP,
																						true,
																						this.objGcontrolJFrame.objGlanguageA[this.objGcontrolJFrame.geLanguageIndex()]));
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPfrontBalls
	 */
	final private void doDrawBalls(boolean bolPbodyBalls, boolean bolParmsBalls) {

		final Graphics2D objLballsGraphics2D =
												(Graphics2D) (this.objGjuggleMasterPro.bytGballsVisibility == Constants.bytS_BIT_VISIBILITY_TRAIL
																																					? bolPbodyBalls
																																									? bolParmsBalls
																																													? this.imgGballsFrontFrontTrailsBuffer.getGraphics()
																																													: this.imgGballsFrontBackTrailsBuffer.getGraphics()
																																									: bolParmsBalls
																																													? this.imgGballsBackFrontTrailsBuffer.getGraphics()
																																													: this.imgGballsBackBackTrailsBuffer.getGraphics()
																																					: this.imgGanimationBuffer.getGraphics());

		this.doOptimizeGraphics(objLballsGraphics2D);
		final int intLtrailsNumber =
										(this.objGjuggleMasterPro.bytGballsVisibility == Constants.bytS_BIT_VISIBILITY_TRAIL
																															? Math.min(	this.objGjuggleMasterPro.bolGthrowsValues
																																													? 1
																																													: 0,
																																		this.objGjuggleMasterPro.objGballTrailALA[0].size() - 1)
																															: this.objGjuggleMasterPro.objGballTrailALA[0].size() - 1);
		for (int intLtrailIndex = intLtrailsNumber; intLtrailIndex >= 0; --intLtrailIndex) {
			for (final ArrayList<BallTrail> objLballTrailAL : this.objGjuggleMasterPro.objGballTrailALA) {
				final BallTrail objLballTrail = objLballTrailAL.get(intLtrailIndex);

				if (objLballTrail.bolGvisible
					&& (objLballTrail.fltGposZ < 0F && !bolPbodyBalls || objLballTrail.fltGposZ == 0F
						&& objLballTrail.bolGbodyBallZOrder == bolPbodyBalls || objLballTrail.fltGposZ > 0F && bolPbodyBalls)
					&& objLballTrail.bolGarmsBallZOrder == bolParmsBalls && objLballTrail.bytGthrow != 0) {

					final boolean bolLmarkCurrentThrow =
															this.objGjuggleMasterPro.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS)
																&& !this.objGjuggleMasterPro.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_FX)
																&& intLtrailIndex == 0
																&& objLballTrail.intGlastCatchTimer > 0
																&& (objLballTrail.intGlastCatchTimer + 1 == this.objGjuggleMasterPro.lngGthrowsCount || objLballTrail.intGlastCatchTimer + 2 == this.objGjuggleMasterPro.lngGthrowsCount
																																						&& this.objGjuggleMasterPro.objGsiteswap.bolGsynchro);
					final boolean bolLlighterColors = (Math.random() >= 0.5F);

					this.doDrawBall(objLballsGraphics2D, objLballTrail, bolLlighterColors, bolLmarkCurrentThrow);
					if (intLtrailIndex == 0 && this.objGjuggleMasterPro.bolGthrowsValues && !this.objGjuggleMasterPro.bolGfX) {
						this.doDrawThrow(objLballsGraphics2D, objLballTrail, bolLlighterColors);
					}
				}
			}
		}
		objLballsGraphics2D.dispose();

		if (this.objGjuggleMasterPro.bytGballsVisibility == Constants.bytS_BIT_VISIBILITY_TRAIL) {
			final Graphics2D objLanimationGraphics2D = (Graphics2D) this.imgGanimationBuffer.getGraphics();
			this.doOptimizeGraphics(objLanimationGraphics2D);
			objLanimationGraphics2D.drawImage(bolPbodyBalls ? bolParmsBalls ? this.imgGballsFrontFrontTrailsBuffer
																			: this.imgGballsFrontBackTrailsBuffer
															: bolParmsBalls ? this.imgGballsBackFrontTrailsBuffer
																			: this.imgGballsBackBackTrailsBuffer, 0, 0, null);
			objLanimationGraphics2D.dispose();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */

	final private void doDrawChest() {

		// Set graphics :
		final Graphics2D objLjugglerGraphics2D =
													(Graphics2D) (Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																					Constants.bytS_BIT_VISIBILITY_TRAIL)
																														? this.imgGjugglerBackTrailsBuffer.getGraphics()
																														: this.imgGanimationBuffer.getGraphics());
		objLjugglerGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.doOptimizeGraphics(objLjugglerGraphics2D);

		// Inside chest :
		if (this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbackBalls
			|| this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbackArms) {
			objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);

			objLjugglerGraphics2D.setComposite(AlphaComposite.getInstance(	AlphaComposite.SRC_OVER,
																			this.objGjuggleMasterPro.bolGfX
																				|| Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																									Constants.bytS_BIT_VISIBILITY_TRAIL) ? 1.0F
																																		: 0.85F));
			objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
			objLjugglerGraphics2D.setStroke(new BasicStroke(1F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			this.objGjuggleMasterPro.objGbody.fillBody(	objLjugglerGraphics2D,
														this.fltGframeSizeRatio,
														Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																		Constants.bytS_BIT_VISIBILITY_TRAIL),
														this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bolGarmVisible,
														this.objGjuggleMasterPro.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bolGarmVisible);
		}

		// Chest :
		if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HEAD)) {
			// Chest sides :
			objLjugglerGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F));
			objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGchestColorA[this.objGjuggleMasterPro.bytGlight]);
			this.objGjuggleMasterPro.objGbody.drawChest(objLjugglerGraphics2D, this.fltGframeSizeRatio);

			// Chest top (neck) :
			objLjugglerGraphics2D.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
			for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
				for (byte bytLarmIndex = 3; bytLarmIndex < 5; ++bytLarmIndex) {
					this.objGjuggleMasterPro.objGbody.drawArmPart(objLjugglerGraphics2D, this.fltGframeSizeRatio, bytLside, bytLarmIndex);
				}
			}
		}

		// Inside head :
		if (this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbackBalls
			|| this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbackArms
			|| Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HEAD)) {
			objLjugglerGraphics2D.setComposite(AlphaComposite.getInstance(	AlphaComposite.SRC_OVER,
																			this.objGjuggleMasterPro.bolGfX
																				|| Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																									Constants.bytS_BIT_VISIBILITY_TRAIL) ? 1.0F
																																		: 0.9F));
			objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
			this.objGjuggleMasterPro.objGbody.drawHead(objLjugglerGraphics2D, this.fltGframeSizeRatio, true);
		}

		if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HEAD)) {
			// Head :
			objLjugglerGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F));
			objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
			this.objGjuggleMasterPro.objGbody.drawHead(objLjugglerGraphics2D, this.fltGframeSizeRatio, false);
			objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGchestColorA[this.objGjuggleMasterPro.bytGlight]);
			this.objGjuggleMasterPro.objGbody.drawEyes(objLjugglerGraphics2D, this.fltGframeSizeRatio, false);

			// Juggler trail :
			if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_TRAIL)) {
				final Graphics objLanimationGraphics2D = this.imgGanimationBuffer.getGraphics();
				objLanimationGraphics2D.drawImage(this.imgGjugglerBackTrailsBuffer, 0, 0, null);
				// objLanimationGraphics2D.drawImage(this.objGjugglerFrontTrailsBufferedImage, 0, 0, null);
				objLanimationGraphics2D.dispose();
			}
		}
		objLjugglerGraphics2D.dispose();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics2D
	 */
	final private void doDrawColoredBorder(Graphics2D objPgraphics2D) {

		final Insets objLinsets = this.getInsets();

		final byte bytLthickness = 4;
		final int intLx0 = objLinsets.left;
		final int intLy0 = objLinsets.top;
		final int intLx1 = objLinsets.left + this.intGbackgroundWidth;
		final int intLy1 = objLinsets.top + this.intGbackgroundHeight;

		// North border :
		objPgraphics2D.setStroke(new BasicStroke(0.5F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		objPgraphics2D.setPaint(new GradientPaint(intLx0, intLy0, Color.WHITE, intLx1, intLy0, Color.ORANGE, false));
		objPgraphics2D.fillRect(intLx0, intLy0, intLx1 - intLx0, bytLthickness);

		// West border :
		objPgraphics2D.setPaint(new GradientPaint(intLx0, intLy0, Color.WHITE, intLx0, intLy1, Color.ORANGE, false));
		objPgraphics2D.fillRect(intLx0, intLy0, bytLthickness, intLy1 - intLy0);

		// East border :
		objPgraphics2D.setPaint(new GradientPaint(intLx1, intLy0, Color.ORANGE, intLx1, intLy1, Color.RED, false));
		objPgraphics2D.fillRect(intLx1 - bytLthickness, intLy0, bytLthickness, intLy1 - intLy0);

		// South border :
		objPgraphics2D.setPaint(new GradientPaint(intLx0, intLy1, Color.ORANGE, intLx1, intLy1, Color.RED, false));
		objPgraphics2D.fillRect(intLx0, intLy1 - bytLthickness, intLx1 - intLx0, bytLthickness);
	}

	final private boolean doDrawHand(Graphics2D objPgraphics2D, boolean bolPbodyFront, boolean bolParmFront, byte bytPside) {

		boolean bolLdrewSomething = false;
		final Hand objLhand = this.objGjuggleMasterPro.objGhandA[bytPside];
		if (objLhand.bolGhandVisible
			&& (objLhand.fltGposZ < 0 && !bolPbodyFront || objLhand.fltGposZ == 0 && objLhand.bolGbodyHandZOrder == bolPbodyFront || objLhand.fltGposZ > 0
																																		&& bolPbodyFront)
			&& objLhand.bolGarmHandZOrder == bolParmFront) {

			byte bytLthickness = Constants.bytS_ENGINE_THICKNESS_STRONG;
			if (objLhand.fltGposZ < 0) {
				final float fltLthicknessRatio =
													objLhand.fltGposZ
														/ this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGhandsMinValueZ;
				if (fltLthicknessRatio > 0.33F) {
					if (fltLthicknessRatio < 0.67F) {
						bytLthickness = Constants.bytS_ENGINE_THICKNESS_NORMAL;
					} else {
						bytLthickness = Constants.bytS_ENGINE_THICKNESS_THIN;
					}
				}
			}
			objPgraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
			objPgraphics2D.drawImage(	this.imgGhandBufferAAA[this.objGjuggleMasterPro.bytGlight][bytPside][bytLthickness],
										Math.round((Constants.intS_GRAPHICS_CORRECTION_X + objLhand.intGposX) * this.fltGframeSizeRatio),
										Math.round(objLhand.intGposY * this.fltGframeSizeRatio),
										null);
			bolLdrewSomething = true;
			// this.doDrawHand(
			// objLgraphics2D,
			// ,
			// objLhand.intGposX,
			// objLhand.intGposY);
		}
		return bolLdrewSomething;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doDrawJugglerAndBalls() {

		this.doStoreBallsTrails();

		final boolean bolLdraw =
									!this.objGjuggleMasterPro.bolGrobot && !this.objGjuggleMasterPro.bolGflash
										|| this.objGjuggleMasterPro.bytGstrobeRatio > 0;
		final boolean bolLdrawBalls = bolLdraw && this.objGjuggleMasterPro.bytGballsVisibility != Constants.bytS_BIT_VISIBILITY_NONE;
		final boolean bolLdrawArmsOrHands =
											bolLdraw
												&& !this.objGjuggleMasterPro.bolGfX
												&& Tools.intersects(this.objGjuggleMasterPro.bytGjugglerVisibility,
																	Constants.bytS_BIT_VISIBILITY_ARMS | Constants.bytS_BIT_VISIBILITY_HANDS);
		final boolean bolLdrawHead =
										bolLdraw
											&& !this.objGjuggleMasterPro.bolGfX
											&& (this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbackBalls
												|| this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbackArms || Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																																						Constants.bytS_BIT_VISIBILITY_HEAD));
		final boolean bolLdrawArms =
										bolLdraw && !this.objGjuggleMasterPro.bolGfX
											&& Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_ARMS);

		final boolean bolLbodyTrail =
										bolLdraw && !this.objGjuggleMasterPro.bolGfX
											&& Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_TRAIL);
		// Background objects :
		if (bolLdrawBalls && this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGarmsBackBalls) {
			this.doDrawBalls(false, false);
		}
		if (bolLdrawArmsOrHands && this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGbackArms) {
			this.doDrawArmsAndHands(false);
		}
		if (bolLdrawBalls && this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGarmsFrontBalls) {
			this.doDrawBalls(false, true);
		}

		// Juggler body :
		if (bolLdrawHead) {
			this.doDrawChest();
		}

		// Shoulders :
		if (bolLdrawArms) {
			this.doDrawShoulders();
		}
		boolean bolLdrewBodyFrontTrails = false;

		// Foreground balls :
		if (bolLdrawBalls && this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGarmsBackBalls) {
			this.doDrawBalls(true, false);
		}

		// Foreground body :
		if (bolLdrawArmsOrHands && this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGfrontArms) {
			bolLdrewBodyFrontTrails = this.doDrawArmsAndHands(true) && bolLbodyTrail;
		}
		if (bolLbodyTrail && !bolLdrewBodyFrontTrails) {
			final Graphics2D objLjugglerGraphics2D = (Graphics2D) this.imgGanimationBuffer.getGraphics();
			objLjugglerGraphics2D.drawImage(this.imgGjugglerFrontTrailsBuffer, 0, 0, null);
			objLjugglerGraphics2D.dispose();
		}

		// Foreground balls :
		if (bolLdrawBalls && this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bolGarmsFrontBalls) {
			this.doDrawBalls(true, true);
		}
	}

	final private void doDrawShoulders() {

		// Set graphics :
		final Graphics2D objLjugglerGraphics2D =
													(Graphics2D) (Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																					Constants.bytS_BIT_VISIBILITY_TRAIL)
																														? this.imgGjugglerFrontTrailsBuffer.getGraphics()
																														: this.imgGanimationBuffer.getGraphics());
		objLjugglerGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.doOptimizeGraphics(objLjugglerGraphics2D);

		// Draw shoulders :
		objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
		for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
			if (this.objGjuggleMasterPro.objGhandA[bytLside].bolGarmVisible) {
				objLjugglerGraphics2D.setStroke(new BasicStroke(this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].getArmWidth(this.objGjuggleMasterPro.objGhandA[bytLside].fltGposZ,
																																				(byte) 2,
																																				this.objGjuggleMasterPro.objGhandA[bytLside].bolGarmHandZOrder),
																BasicStroke.CAP_ROUND,
																BasicStroke.JOIN_ROUND));
				this.objGjuggleMasterPro.objGbody.drawArmPart(objLjugglerGraphics2D, this.fltGframeSizeRatio, bytLside, (byte) 2);
			}
		}
		objLjugglerGraphics2D.dispose();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doDrawSiteswap() {

		if (this.imgGsiteswapBuffer != null && this.objGjuggleMasterPro.objGsiteswap.bytGstatus >= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER) {

			final Graphics2D objLsiteswapGraphics2D = (Graphics2D) this.imgGsiteswapBuffer.getGraphics();

			objLsiteswapGraphics2D.setFont(this.objGboldFont);
			objLsiteswapGraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			this.doOptimizeGraphics(objLsiteswapGraphics2D);
			objLsiteswapGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
			objLsiteswapGraphics2D.fillRect(0, 0, this.intGsiteswapWidth, Constants.intS_GRAPHICS_SITESWAP_HEIGHT);

			final int intLcursorLeftPosX = (this.intGsiteswapWidth - this.objGjuggleMasterPro.objGsiteswap.intGsequencesMaxLength) / 2;
			final int intLsiteswapPosY = Constants.intS_GRAPHICS_SITESWAP_HEIGHT - objLsiteswapGraphics2D.getFontMetrics().getDescent();

			// Draw the first '.' or '...' :
			if (!this.objGjuggleMasterPro.bolGfX) {
				final String strLdots = Strings.doConcat('.', this.objGjuggleMasterPro.intGcurrentSequenceIndex > 0 ? ".." : null, ' ');
				if (this.objGjuggleMasterPro.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_SITESWAP)) {
					objLsiteswapGraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
					objLsiteswapGraphics2D.drawString(	strLdots,
														intLcursorLeftPosX - objLsiteswapGraphics2D.getFontMetrics().stringWidth(strLdots),
														intLsiteswapPosY);
				}

				// Draw the throws :
				int intLdeltaX = 0;
				for (int intLthrowIndex =
											this.objGjuggleMasterPro.objGsiteswap.getSequenceIndex(	this.objGjuggleMasterPro.intGcurrentSequenceIndex,
																									this.intGsiteswapWidth); intLthrowIndex <= this.objGjuggleMasterPro.objGsiteswap.getSequenceIndex(	this.objGjuggleMasterPro.intGcurrentSequenceIndex + 1,
																																																		this.intGsiteswapWidth)
																																				+ (this.objGjuggleMasterPro.objGsiteswap.bolGsynchro
																																																	? 1
																																																	: 0); ++intLthrowIndex) {
					final String strLthrow = this.objGjuggleMasterPro.objGsiteswap.toString(intLthrowIndex, true, true);

					if (this.objGjuggleMasterPro.bolGdisplayedThrowA[intLthrowIndex]) {
						objLsiteswapGraphics2D.setColor(intLthrowIndex == this.objGjuggleMasterPro.intGcurrentThrowIndex
														|| this.objGjuggleMasterPro.objGsiteswap.bolGsynchro
														&& (Tools.getEvenValue(intLthrowIndex) == this.objGjuggleMasterPro.intGcurrentThrowIndex || Tools.getEvenValue(this.objGjuggleMasterPro.intGcurrentThrowIndex) == intLthrowIndex)
																																																											? this.objGjuggleMasterPro.objGsiteswapColorA[this.objGjuggleMasterPro.bytGlight]
																																																											: this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
						objLsiteswapGraphics2D.drawString(strLthrow, intLcursorLeftPosX + intLdeltaX, intLsiteswapPosY);
					}
					intLdeltaX += objLsiteswapGraphics2D.getFontMetrics().stringWidth(strLthrow);
				}

				// Draw the last '...' :
				if (this.objGjuggleMasterPro.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_SITESWAP)) {
					objLsiteswapGraphics2D.setColor(this.objGjuggleMasterPro.objGjugglerColorA[this.objGjuggleMasterPro.bytGlight]);
					objLsiteswapGraphics2D.drawString(	Strings.doConcat(	'.',
																			this.objGjuggleMasterPro.intGcurrentSequenceIndex < this.objGjuggleMasterPro.objGsiteswap.getSequencesNumber(this.intGsiteswapWidth) - 2
																																																					? ".."
																																																					: null,
																			' '),
														intLcursorLeftPosX + intLdeltaX,
														intLsiteswapPosY);
				}
			}
			objLsiteswapGraphics2D.dispose();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics2D
	 * @param objPballTrail
	 */
	final private void doDrawThrow(Graphics2D objPgraphics2D, BallTrail objPballTrail, boolean bolPlighterColors) {

		objPgraphics2D.setFont(objPballTrail.intGcolor != Constants.bytS_UNCLASS_NO_VALUE
								|| this.objGjuggleMasterPro.getControlValue(Constants.bytS_BYTE_LOCAL_HEIGHT) < Constants.bytS_ENGINE_THROWS_VALUES_NUMBER
																																							? this.objGthrowValuesBoldFont
																																							: this.objGthrowValuesNormalFont);
		final String strLthrow = Tools.getThrowValueString(objPballTrail.bytGthrow);
		final int intLthrowStringWidth = objPgraphics2D.getFontMetrics().stringWidth(strLthrow);

		// Writing the throw back value :
		final boolean bolLlightColor =
										this.objGballColorValueAHashMap.get(this.objGjuggleMasterPro.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_COLORS)
																																							? objPballTrail.intGcolor
																																							: BallsColors.intS_BALLS_COLORS_DEFAULT_VALUE)[bolPlighterColors
																																																							? Constants.bytS_ENGINE_LIGHT
																																																							: Constants.bytS_ENGINE_DARK];
		objPgraphics2D.setColor(bolLlightColor ? Color.WHITE : Color.BLACK);
		objPgraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		objPgraphics2D.drawString(	strLthrow,
									(Constants.intS_GRAPHICS_CORRECTION_X + objPballTrail.intGposX + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS)
										* this.fltGframeSizeRatio - intLthrowStringWidth / 2 + 1,
									(objPballTrail.intGposY + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS) * this.fltGframeSizeRatio
										+ objPgraphics2D.getFontMetrics().getAscent() / 2 - 1);
		objPgraphics2D.setFont(this.objGthrowValuesNormalFont);

		// Writing the throw front value :
		objPgraphics2D.setColor(bolLlightColor ? Color.BLACK : Color.WHITE);
		objPgraphics2D.drawString(	strLthrow,
									(Constants.intS_GRAPHICS_CORRECTION_X + objPballTrail.intGposX + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS)
										* this.fltGframeSizeRatio - intLthrowStringWidth / 2,
									(objPballTrail.intGposY + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS) * this.fltGframeSizeRatio
										+ objPgraphics2D.getFontMetrics().getAscent() / 2 - 2);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics2D
	 * @param objPballTrail
	 */
	final private void doEraseBall(Graphics2D objPgraphics2D, BallTrail objPballTrail) {
		objPgraphics2D.drawImage(	this.imgGballMaskingBufferA[this.objGjuggleMasterPro.bytGlight],
									Math.round((Constants.intS_GRAPHICS_CORRECTION_X + objPballTrail.intGposX) * this.fltGframeSizeRatio),
									Math.round(objPballTrail.intGposY * this.fltGframeSizeRatio),
									null);

		this.doEraseThrow(objPgraphics2D, objPballTrail);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doEraseBalls() {
		final byte bytLballs =
								(byte) Math.max(this.objGjuggleMasterPro.getControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL),
												this.objGjuggleMasterPro.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING ? 0 : 1);
		switch (this.objGjuggleMasterPro.bytGballsVisibility) {

			case Constants.bytS_BIT_VISIBILITY_TRAIL:
				while (this.objGjuggleMasterPro.objGsiteswap.intGballsNumber > 0
						&& this.objGjuggleMasterPro.objGballTrailALA[0].size() > Constants.bytS_ENGINE_TRAIL_LENGTH) {
					for (int intLballIndex = 0; intLballIndex < this.objGjuggleMasterPro.objGsiteswap.intGballsNumber; ++intLballIndex) {
						this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].remove(this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].size() - 1);
					}
				}
				break;

			case Constants.bytS_BIT_VISIBILITY_BALLS:
			case Constants.bytS_BIT_VISIBILITY_NONE:

				final Graphics2D objLanimationGraphics2D = (Graphics2D) this.imgGanimationBuffer.getGraphics();
				for (int intLballIndex = 0; intLballIndex < this.objGjuggleMasterPro.objGsiteswap.intGballsNumber; ++intLballIndex) {
					boolean bolLthrowErased = false;
					for (byte bytLtrailIndex = (byte) (this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].size() - 1); bytLtrailIndex >= (this.objGjuggleMasterPro.bytGballsVisibility == Constants.bytS_BIT_VISIBILITY_BALLS
																																																									? bytLballs
																																																									: 1); --bytLtrailIndex) {
						try {
							final BallTrail objLballTrail = this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].remove(bytLtrailIndex);
							if (objLballTrail.bolGvisible) {
								this.doEraseBall(objLanimationGraphics2D, objLballTrail);
								if (bytLtrailIndex == 0) {
									this.doEraseThrow(objLanimationGraphics2D, objLballTrail);
									bolLthrowErased = true;
								}
							}
						} catch (final Throwable objPthrowable) {
							Tools.err("Error while erasing ball on animation window");
						}
					}
					if (!bolLthrowErased && !this.objGjuggleMasterPro.bolGthrowsValues
						&& !this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].isEmpty()) {
						this.doEraseThrow(objLanimationGraphics2D, this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].get(0));
					}
				}
				objLanimationGraphics2D.dispose();
				break;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doEraseJuggler() {

		// Set graphics properties :
		final Graphics2D objLjugglerGraphics2D = (Graphics2D) this.imgGanimationBuffer.getGraphics();
		objLjugglerGraphics2D.setRenderingHint(	RenderingHints.KEY_ANTIALIASING,
												Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_TRAIL)
																																					? RenderingHints.VALUE_ANTIALIAS_ON
																																					: RenderingHints.VALUE_ANTIALIAS_OFF);
		objLjugglerGraphics2D.setStroke(new BasicStroke(Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																		Constants.bytS_BIT_VISIBILITY_TRAIL) ? 1.5F : 5F,
														BasicStroke.CAP_ROUND,
														BasicStroke.JOIN_BEVEL));
		objLjugglerGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
		this.doOptimizeGraphics(objLjugglerGraphics2D);

		// Head :
		if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HEAD)) {
			if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_TRAIL)) {
				// Erase back trail image head :
				final Graphics2D objLjugglerBackTrailsGraphics2D = (Graphics2D) this.imgGjugglerBackTrailsBuffer.getGraphics();
				objLjugglerBackTrailsGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
				objLjugglerBackTrailsGraphics2D.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
				objLjugglerBackTrailsGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				this.doOptimizeGraphics(objLjugglerBackTrailsGraphics2D);
				this.objGjuggleMasterPro.objGbody.drawHead(objLjugglerBackTrailsGraphics2D, this.fltGframeSizeRatio, false);
				this.objGjuggleMasterPro.objGbody.drawEyes(objLjugglerBackTrailsGraphics2D, this.fltGframeSizeRatio, false);
			} else {
				// Erase background head :
				this.objGjuggleMasterPro.objGbody.drawHead(objLjugglerGraphics2D, this.fltGframeSizeRatio, false);
				this.objGjuggleMasterPro.objGbody.drawEyes(objLjugglerGraphics2D, this.fltGframeSizeRatio, false);
			}
		}

		// Chest :
		if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HEAD)) {
			this.objGjuggleMasterPro.objGbody.drawChest(objLjugglerGraphics2D, this.fltGframeSizeRatio);
			for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
				for (byte bytLarmIndex =
											(byte) (this.objGjuggleMasterPro.objGhandA[bytLside].bolGarmVisible
													&& !Tools.contains(	this.objGjuggleMasterPro.bytGjugglerVisibility,
																		Constants.bytS_BIT_VISIBILITY_TRAIL) ? 2 : 3); bytLarmIndex < 5; ++bytLarmIndex) {
					this.objGjuggleMasterPro.objGbody.drawArmPart(objLjugglerGraphics2D, this.fltGframeSizeRatio, bytLside, bytLarmIndex);
				}
			}
		}

		// Arms :
		if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_ARMS)) {
			if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_TRAIL)) {

				// Set front arm :
				final boolean[] bolLbackA = new boolean[2];
				final boolean[] bolLfrontA = new boolean[2];
				for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
					bolLbackA[bytLside] =
											this.objGjuggleMasterPro.objGhandA[bytLside].bolGarmVisible
												&& (this.objGjuggleMasterPro.objGhandA[bytLside].fltGposZ < 0 || this.objGjuggleMasterPro.objGhandA[bytLside].fltGposZ == 0F
																													&& !this.objGjuggleMasterPro.objGhandA[bytLside].bolGbodyHandZOrder);
					bolLfrontA[bytLside] =
											this.objGjuggleMasterPro.objGhandA[bytLside].bolGarmVisible
												&& (this.objGjuggleMasterPro.objGhandA[bytLside].fltGposZ > 0 || this.objGjuggleMasterPro.objGhandA[bytLside].fltGposZ == 0F
																													&& this.objGjuggleMasterPro.objGhandA[bytLside].bolGbodyHandZOrder);
				}
				final boolean bolLback = bolLbackA[Constants.bytS_ENGINE_LEFT_SIDE] || bolLbackA[Constants.bytS_ENGINE_RIGHT_SIDE];
				final boolean bolLfront = bolLfrontA[Constants.bytS_ENGINE_LEFT_SIDE] || bolLfrontA[Constants.bytS_ENGINE_RIGHT_SIDE];

				// Set pen properties :
				Graphics2D objLjugglerBackTrailsGraphics2D = null;
				Graphics2D objLjugglerFrontTrailsGraphics2D = null;
				if (bolLback) {
					objLjugglerBackTrailsGraphics2D = (Graphics2D) this.imgGjugglerBackTrailsBuffer.getGraphics();
					objLjugglerBackTrailsGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					this.doOptimizeGraphics(objLjugglerBackTrailsGraphics2D);
					objLjugglerBackTrailsGraphics2D.setStroke(new BasicStroke(0.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					objLjugglerBackTrailsGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
				}

				// Set pen properties :
				if (bolLfront) {
					objLjugglerFrontTrailsGraphics2D = (Graphics2D) this.imgGjugglerFrontTrailsBuffer.getGraphics();
					objLjugglerFrontTrailsGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					this.doOptimizeGraphics(objLjugglerFrontTrailsGraphics2D);
					objLjugglerFrontTrailsGraphics2D.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					objLjugglerFrontTrailsGraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
				}

				// Erase arms :
				for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
					for (byte bytLarmIndex = 1; bytLarmIndex >= 0; --bytLarmIndex) {
						if (bolLbackA[bytLside]) {
							this.objGjuggleMasterPro.objGbody.drawArmPart(	objLjugglerBackTrailsGraphics2D,
																			this.fltGframeSizeRatio,
																			bytLside,
																			bytLarmIndex);
						}
						if (bolLfrontA[bytLside]) {
							this.objGjuggleMasterPro.objGbody.drawArmPart(	objLjugglerFrontTrailsGraphics2D,
																			this.fltGframeSizeRatio,
																			bytLside,
																			bytLarmIndex);
						}
					}
				}
				if (bolLback) {
					objLjugglerBackTrailsGraphics2D.dispose();
				}
				if (bolLfront) {
					objLjugglerFrontTrailsGraphics2D.dispose();
				}
			} else {
				for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {
					for (byte bytLarmIndex = 4; bytLarmIndex >= 0; --bytLarmIndex) {
						this.objGjuggleMasterPro.objGbody.drawArmPart(objLjugglerGraphics2D, this.fltGframeSizeRatio, bytLside, bytLarmIndex);
					}
				}
			}
		}

		// Hands :
		if (Tools.contains(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_HANDS)) {
			for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {

				objLjugglerGraphics2D.drawImage(this.imgGmaskingHandBufferAA[this.objGjuggleMasterPro.bytGlight][bytLside],
												Math.round((Constants.intS_GRAPHICS_CORRECTION_X + this.objGjuggleMasterPro.objGhandA[bytLside].intGposX)
															* this.fltGframeSizeRatio),
												Math.round(this.objGjuggleMasterPro.objGhandA[bytLside].intGposY * this.fltGframeSizeRatio),
												null);
			}
		}

		objLjugglerGraphics2D.dispose();
	}

	final public void doEraseJugglerAndBalls() {
		if (!this.objGjuggleMasterPro.bolGrobot && !this.objGjuggleMasterPro.bolGflash) {
			if (!Tools.intersects(this.objGjuggleMasterPro.bytGjugglerVisibility, Constants.bytS_BIT_VISIBILITY_NONE)) {
				this.doEraseJuggler();
			}
			this.doEraseBalls();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics2D
	 * @param objPballTrail
	 */
	final private void doEraseThrow(Graphics2D objPgraphics2D, BallTrail objPballTrail) {

		if (!this.objGjuggleMasterPro.bolGfX && objPballTrail.bytGthrow != 0) {

			final String strLthrow = Tools.getThrowValueString(objPballTrail.bytGthrow);
			final int intLthrowStringWidth = objPgraphics2D.getFontMetrics().stringWidth(strLthrow);

			objPgraphics2D.setColor(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);

			// Writing the blank throw value :
			if (this.objGjuggleMasterPro.bytGballsVisibility < Constants.bytS_BIT_VISIBILITY_TRAIL) {

				objPgraphics2D.fillRect((int) Math.floor((Constants.intS_GRAPHICS_CORRECTION_X + objPballTrail.intGposX + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS)
															* this.fltGframeSizeRatio)
											- intLthrowStringWidth / 2,
										(int) Math.floor((objPballTrail.intGposY + Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS)
															* this.fltGframeSizeRatio)
											- objPgraphics2D.getFontMetrics().getAscent() / 2 - 2,
										intLthrowStringWidth + 3,
										objPgraphics2D.getFontMetrics().getAscent() + 3);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics2D
	 */
	final private void doOptimizeGraphics(Graphics2D objPgraphics2D) {
		objPgraphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		objPgraphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		objPgraphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		objPgraphics2D.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics2D
	 * @param objPinsets
	 */
	final public void doPaintFrame(Graphics2D objPgraphics2D) {
		this.doPaintFrame(objPgraphics2D, new Insets(0, 0, 0, 0));
	}

	final public void doPaintFrame(Graphics2D objPgraphics2D, Insets objPinsets) {

		if (this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE /*
																										 * &&
																										 * (this
																										 * .
																										 * objGjuggleMasterPro
																										 * .
																										 * bytGstrobeRatio
																										 * >
																										 * 0
																										 * ||
																										 * this
																										 * .
																										 * objGjuggleMasterPro
																										 * .
																										 * bolGrobot
																										 * )
																										 */) {

			if (this.intGmarginWidth > 0) {

				// Left margin :
				objPgraphics2D.drawImage(	this.imgGbackgroundBufferA[this.objGjuggleMasterPro.bytGlight],
											objPinsets.left + this.intGmarginWidth - Constants.intS_GRAPHICS_SCREEN_WIDTH,
											objPinsets.top + this.intGmarginHeight + Constants.intS_GRAPHICS_SITESWAP_HEIGHT,
											null);

				// Right margin :
				objPgraphics2D.drawImage(	this.imgGbackgroundBufferA[this.objGjuggleMasterPro.bytGlight],
											objPinsets.left + this.intGmarginWidth + this.intGanimationWidth,
											objPinsets.top + this.intGmarginHeight + Constants.intS_GRAPHICS_SITESWAP_HEIGHT,
											null);

			}

			if (this.intGmarginHeight > 0) {

				// Top margin :
				objPgraphics2D.drawImage(	this.imgGbackgroundBufferA[this.objGjuggleMasterPro.bytGlight],
											objPinsets.left,
											objPinsets.top + this.intGmarginHeight - Constants.intS_GRAPHICS_SCREEN_HEIGHT,
											null);

				// Bottom margin :
				objPgraphics2D.drawImage(	this.imgGbackgroundBufferA[this.objGjuggleMasterPro.bytGlight],
											objPinsets.left,
											objPinsets.top + this.intGmarginHeight + Constants.intS_GRAPHICS_SITESWAP_HEIGHT
												+ this.intGanimationHeight,
											null);
			}

			// Draw siteswap :
			objPgraphics2D.drawImage(this.imgGsiteswapBuffer, objPinsets.left, objPinsets.top + this.intGmarginHeight, null);

			// Draw animation image :
			objPgraphics2D.drawImage(	this.imgGanimationBuffer,
										objPinsets.left + this.intGmarginWidth,
										objPinsets.top + this.intGmarginHeight + Constants.intS_GRAPHICS_SITESWAP_HEIGHT,
										null);
		} else {
			// Draw empty board (background) :
			objPgraphics2D.drawImage(this.imgGbackgroundBufferA[this.objGjuggleMasterPro.bytGlight], objPinsets.left, objPinsets.top, null);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doRepaintFrame() {
		final Graphics2D objLjuggleMasterProJFrameGraphics2D = (Graphics2D) this.getGraphics();

		this.setBackground(this.objGjuggleMasterPro.objGbackgroundColorA[this.objGjuggleMasterPro.bytGlight]);
		this.doPaintFrame(objLjuggleMasterProJFrameGraphics2D, this.getInsets());
		if (this.objGjuggleMasterPro.bolGcoloredBorder) {
			this.doDrawColoredBorder(objLjuggleMasterProJFrameGraphics2D);
		}

		if (this.objGjuggleMasterPro.bolGscreenPlay) {
			final Graphics2D objLscreenPlayBufferedImageGraphics2D = (Graphics2D) this.imgGscreenPlayBuffer.getGraphics();
			this.doPaintFrame(objLscreenPlayBufferedImageGraphics2D, this.getInsets());
			objLscreenPlayBufferedImageGraphics2D.dispose();
			this.objGjuggleMasterPro.objGanimatedGIFEncoder.addFrame(this.imgGscreenPlayBuffer);
		}
		// if (this.bolGmouseOver && this.imgGjmp != null) {
		// final Point objLlocalMousePoint = MouseInfo.getPointerInfo().getLocation();
		// SwingUtilities.convertPointFromScreen(objLlocalMousePoint, this);
		// objLjuggleMasterProJFrameGraphics2D.drawImage( this.imgGjmp,
		// (int) objLlocalMousePoint.getX() + 20,
		// (int) objLlocalMousePoint.getY() + 20,
		// null);
		// }

		objLjuggleMasterProJFrameGraphics2D.dispose();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doStoreBallsTrails() {
		if (this.objGjuggleMasterPro.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING
			&& (this.objGjuggleMasterPro.bytGballsVisibility != Constants.bytS_BIT_VISIBILITY_NONE || this.objGjuggleMasterPro.objGballColors.getColorsNumber() == 0)) {
			for (int intLballIndex = 0; intLballIndex < this.objGjuggleMasterPro.objGsiteswap.intGballsNumber; ++intLballIndex) {
				this.objGjuggleMasterPro.objGballTrailALA[intLballIndex].add(	0,
																				new BallTrail(	intLballIndex,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].intGlastCatchTimer,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].intGposX,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].intGposY,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].fltGposZ,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].bolGbodyBallZOrder,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].bolGarmsBallZOrder,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].bolGvisible,
																								this.objGjuggleMasterPro.objGballA[intLballIndex].bytGthrow,
																								this.objGjuggleMasterPro.isAlternateBallColor(intLballIndex)
																																							? this.objGjuggleMasterPro.objGballA[intLballIndex].intGalternateColor
																																							: this.objGjuggleMasterPro.objGballA[intLballIndex].intGcolor));
			}
		}
	}

	final public int getBackgroundHeight() {
		return this.intGbackgroundHeight;
	}

	final public int getBackgroundWidth() {
		return this.intGbackgroundWidth;
	}

	final public int getSiteswapWidth() {
		return this.intGsiteswapWidth;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initBounds() {

		// Calculate new bounds :
		final Insets objLinsets = this.getInsets();
		this.intGbackgroundWidth = this.getWidth() - objLinsets.left - objLinsets.right;
		this.intGbackgroundHeight = this.getHeight() - objLinsets.top - objLinsets.bottom;

		final int intLsiteswapWidth = this.intGbackgroundWidth;

		final float fltLframeSizeRatio =
											Math.min(	(float) this.intGbackgroundWidth / (float) Constants.intS_GRAPHICS_ANIMATION_DEFAULT_SIZE,
														(float) Math.max(this.intGbackgroundHeight - Constants.intS_GRAPHICS_SITESWAP_HEIGHT, 1)
															/ (float) Constants.intS_GRAPHICS_ANIMATION_DEFAULT_SIZE);

		this.intGanimationWidth = Math.max(1, (int) Math.ceil(Constants.intS_GRAPHICS_ANIMATION_DEFAULT_SIZE * fltLframeSizeRatio));
		this.intGanimationHeight = Math.max(1, (int) Math.ceil(Constants.intS_GRAPHICS_ANIMATION_DEFAULT_SIZE * fltLframeSizeRatio));
		this.intGmarginWidth = Math.max(0, (this.intGbackgroundWidth - this.intGanimationWidth) / 2);
		this.intGmarginHeight = Math.max(0, (this.intGbackgroundHeight - this.intGanimationHeight - Constants.intS_GRAPHICS_SITESWAP_HEIGHT) / 2);

		// If the ratio has been modified :
		if (this.fltGframeSizeRatio != fltLframeSizeRatio) {
			this.fltGframeSizeRatio = fltLframeSizeRatio;
			this.objGjuggleMasterPro.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_ANIMATION_IMAGE
													| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
													| Constants.intS_ACTION_RECREATE_HANDS_IMAGES | Constants.intS_ACTION_RECREATE_BALLS_IMAGES
													| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES
													| Constants.intS_ACTION_RECREATE_BALLS_ERASING_IMAGES | Constants.intS_ACTION_REFRESH_DRAWING);
		}

		// If the siteswap image size has been modified :
		if (this.intGsiteswapWidth != intLsiteswapWidth) {
			this.intGsiteswapWidth = intLsiteswapWidth;
			this.objGjuggleMasterPro.doAddAction(Constants.intS_ACTION_RECREATE_SITESWAP_IMAGE | Constants.intS_ACTION_DRAW_SITESWAP);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initBufferingAndFonts() {

		// Hardware double-buffering :
		RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
		this.setIgnoreRepaint(true);

		// Waiting for the frame to be displayed :
		while (!this.isVisible()) {
			Thread.yield();
		}

		// Images :
		final Graphics2D objLjuggleMasterProJFrameGraphics2D = (Graphics2D) this.getGraphics();

		this.initBounds();

		// Font metrics :
		this.objGnormalFont = objLjuggleMasterProJFrameGraphics2D.getFont();
		this.objGboldFont = this.objGnormalFont.deriveFont(Font.BOLD);
		Constants.objS_GRAPHICS_FONT_METRICS = objLjuggleMasterProJFrameGraphics2D.getFontMetrics();
		objLjuggleMasterProJFrameGraphics2D.dispose();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPmouseEvent
	 */
	@Override final public void mouseClicked(MouseEvent objPmouseEvent) {
		if (this.objGjuggleMasterPro.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
			switch (this.objGjuggleMasterPro.bytGanimationStatus) {
				case Constants.bytS_STATE_ANIMATION_PAUSED:
					if (this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
						this.objGjuggleMasterPro.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_JUGGLING;
						if (this.objGcontrolJFrame != null) {
							this.objGcontrolJFrame.objGplayPauseJButton.setImage(false);
							this.objGcontrolJFrame.objGplayPauseJButton.setToolTipText();
						}
					}
					break;
				case Constants.bytS_STATE_ANIMATION_JUGGLING:
					this.objGjuggleMasterPro.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_PAUSED;
					if (this.objGcontrolJFrame != null) {
						this.objGcontrolJFrame.objGplayPauseJButton.setImage(false);
						this.objGcontrolJFrame.objGplayPauseJButton.setToolTipText();
					}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPmouseEvent
	 */
	@Override final public void mouseEntered(MouseEvent objPmouseEvent) {
	// this.bolGmouseOver = true;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPmouseEvent
	 */
	@Override final public void mouseExited(MouseEvent objPmouseEvent) {
	// this.bolGmouseOver = false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPmouseEvent
	 */
	@Override final public void mousePressed(MouseEvent objPmouseEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPmouseEvent
	 */
	@Override final public void mouseReleased(MouseEvent objPmouseEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setBounds() {

		Rectangle objLjuggleMasterProJFrameRectangle = this.objGjuggleMasterPro.getFrame().getBounds();
		Rectangle objLcontrolJFrameRectangle = this.objGcontrolJFrame.getBounds();
		boolean bolLsetBounds = false;

		// Maximized :
		if (this.objGjuggleMasterPro.getFrame().getExtendedState() == Frame.MAXIMIZED_BOTH) {
			objLcontrolJFrameRectangle =
											new Rectangle(	Constants.intS_GRAPHICS_SCREEN_X,
															Constants.intS_GRAPHICS_SCREEN_Y,
															Constants.intS_GRAPHICS_SCREEN_WIDTH / 2,
															Constants.intS_GRAPHICS_SCREEN_HEIGHT);
			objLjuggleMasterProJFrameRectangle =
													new Rectangle(	Constants.intS_GRAPHICS_SCREEN_X + Constants.intS_GRAPHICS_SCREEN_WIDTH / 2,
																	Constants.intS_GRAPHICS_SCREEN_Y,
																	Constants.intS_GRAPHICS_SCREEN_WIDTH / 2,
																	Constants.intS_GRAPHICS_SCREEN_HEIGHT);
			bolLsetBounds = true;
		}

		// Maximum width :
		if (objLjuggleMasterProJFrameRectangle.getWidth() > Constants.intS_GRAPHICS_SCREEN_WIDTH) {
			objLjuggleMasterProJFrameRectangle.width = Constants.intS_GRAPHICS_SCREEN_WIDTH;
			bolLsetBounds = true;
		}
		if (objLcontrolJFrameRectangle.getWidth() > Constants.intS_GRAPHICS_SCREEN_WIDTH) {
			objLcontrolJFrameRectangle.width = Constants.intS_GRAPHICS_SCREEN_WIDTH;
			bolLsetBounds = true;
		}

		// Maximum height :
		if (objLjuggleMasterProJFrameRectangle.getHeight() > Constants.intS_GRAPHICS_SCREEN_HEIGHT) {
			objLjuggleMasterProJFrameRectangle.height = Constants.intS_GRAPHICS_SCREEN_HEIGHT;
			bolLsetBounds = true;
		}
		if (objLcontrolJFrameRectangle.getHeight() > Constants.intS_GRAPHICS_SCREEN_HEIGHT) {
			objLcontrolJFrameRectangle.height = Constants.intS_GRAPHICS_SCREEN_HEIGHT;
			bolLsetBounds = true;
		}

		// Same height :
		if (objLcontrolJFrameRectangle.getHeight() != objLjuggleMasterProJFrameRectangle.getHeight()) {
			objLcontrolJFrameRectangle.height = (int) objLjuggleMasterProJFrameRectangle.getHeight();
			bolLsetBounds = true;
		}

		// Correct X location :
		if (objLcontrolJFrameRectangle.getX() + objLcontrolJFrameRectangle.getWidth() != objLjuggleMasterProJFrameRectangle.getX()) {
			objLcontrolJFrameRectangle.x = (int) (objLjuggleMasterProJFrameRectangle.getX() - objLcontrolJFrameRectangle.getWidth());
			bolLsetBounds = true;
		}

		// Same Y location :
		if (objLcontrolJFrameRectangle.getY() != objLjuggleMasterProJFrameRectangle.getY()) {
			objLcontrolJFrameRectangle.y = (int) objLjuggleMasterProJFrameRectangle.getY();
			bolLsetBounds = true;
		}

		// Set bounds :
		if (bolLsetBounds) {
			this.objGcontrolJFrame.setBounds(objLcontrolJFrameRectangle);
			this.objGcontrolJFrame.repaint();
			this.objGjuggleMasterPro.getFrame().setBounds(objLjuggleMasterProJFrameRectangle);
			this.objGjuggleMasterPro.getFrame().initBounds();
		}
		this.objGjuggleMasterPro.getFrame().initBounds();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowActivated(WindowEvent objPwindowEvent) {
		try { // Try, cos the object hasn't been completely initialized.

			// TODO DISPARAITRE, C'est JugglePanel qui crée ce Listener pour la Frame (si JP existe).
			if (this.objGjuggleMasterPro.getFrame().getExtendedState() != Frame.ICONIFIED) {
				this.objGjuggleMasterPro.getFrame().bolGdontFocusJuggleMasterPro = true;
				this.objGcontrolJFrame.requestFocus();
			}
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while activating animation window");
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosed(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosing(WindowEvent objPwindowEvent) {
		this.objGjuggleMasterPro.doQuit(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeactivated(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeiconified(WindowEvent objPwindowEvent) {
		this.objGcontrolJFrame.setExtendedState(Frame.NORMAL);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowIconified(WindowEvent objPwindowEvent) {
		this.objGcontrolJFrame.setExtendedState(Frame.ICONIFIED);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowOpened(WindowEvent objPwindowEvent) {}
}
