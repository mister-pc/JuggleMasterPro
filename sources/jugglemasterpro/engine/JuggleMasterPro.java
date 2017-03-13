/*
 * @(#)JuggleMasterPro.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.engine;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.data.DataJFrame;
import jugglemasterpro.control.io.FileActions;
import jugglemasterpro.engine.util.AnimatedGIFEncoder;
import jugglemasterpro.engine.util.ExtendedClip;
import jugglemasterpro.engine.window.AnimationJFrame;
import jugglemasterpro.gear.Ball;
import jugglemasterpro.gear.BallTrail;
import jugglemasterpro.gear.Body;
import jugglemasterpro.gear.Hand;
import jugglemasterpro.pattern.BallsColors;
import jugglemasterpro.pattern.PatternsManager;
import jugglemasterpro.pattern.Siteswap;
import jugglemasterpro.pattern.Style;
import jugglemasterpro.pattern.io.PatternsFile;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * This class is the JMP program main entry point, starting either with the 'main' or 'init' method,
 * depending on the running mode (application or applet)
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class JuggleMasterPro extends JApplet implements Runnable, Thread.UncaughtExceptionHandler {

	/**
	 * Method description
	 * 
	 * @see 
	 * @param objPparametersStringsA : String array passed to the 'initCommandLineParameters' method, 
	 *                                 treated according with the values defined in Constants.setCommandLineParameters
	 */
	public static void main(String[] objPparametersStringsA) {

		final JuggleMasterPro objLjuggleMasterPro = new JuggleMasterPro();
		objLjuggleMasterPro.initProgramProperties(true);
		objLjuggleMasterPro.initCommandLineParameters(true, objPparametersStringsA);
		objLjuggleMasterPro.initFrames();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void destroy() {
		this.doStopPattern();

		// Dispose frame :
		try {
			this.objGjuggleMasterProJFrame.setVisible(false);
			this.objGjuggleMasterProJFrame.dispose();
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while closing animation window");
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPaction
	 */
	final public void doAddAction(int intPaction) {
		synchronized (Constants.objS_ENGINE_GRAPHICS_LOCK_OBJECT) {
			this.intGactions |= intPaction;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcontrolJFrame
	 */
	final public void doCreateJuggleMasterProFrame(ControlJFrame objPcontrolJFrame) {
		this.objGjuggleMasterProJFrame = new AnimationJFrame(objPcontrolJFrame, this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doJuggle() {

		// Stop juggling :
		if (this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_STOPPING || this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_STOPPED) {
			this.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_STOPPED;
			return;
		}

		// Refresh paused animation :
		if (this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_UNPLAYABLE) {
			try {
				this.objGjuggleMasterProJFrame.doActions();
			} catch (final Throwable objPthrowable) {}
			this.objGjuggleMasterProJFrame.doRepaintFrame();
			if (this.objGdataJFrame.isVisible()) {
				final Thread objLsetValuesThread = new Thread(this.objGdataJFrame);
				objLsetValuesThread.start();
			}
			return;
		}

		if (this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSING || this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED) {
			this.setDynamicRatios();
			this.objGjuggleMasterProJFrame.doActions();
			this.objGjuggleMasterProJFrame.doRepaintFrame();
			this.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_PAUSED;
			return;
		}

		// Increase frame counter :
		++this.lngGframesCount;
		if (this.lngGframesCount < this.intGframesCountInitialForwardDelta) {
			this.lngGframesCount = this.intGframesCountInitialForwardDelta;
			this.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
		}

		// Calculate new throw indexes :
		final int intLpreviousThrowIndex = this.intGcurrentThrowIndex;
		this.intGcurrentThrowIndex =
										(int) (((this.lngGframesCount - this.intGframesCountInitialForwardDelta) / this.intGframesNumberPerThrow) % this.objGsiteswap.intGthrowsNumber);
		final int intLcurrentSynchroThrowIndex =
													(this.objGsiteswap.bolGsynchro ? Tools.getEvenValue(this.intGcurrentThrowIndex)
																					: this.intGcurrentThrowIndex);
		if (this.intGcurrentThrowIndex != intLpreviousThrowIndex) {
			++this.lngGthrowsCount;
		}

		// Calculate new siteswap sequence index :
		if (intLcurrentSynchroThrowIndex > this.objGsiteswap.getSequenceIndex(this.intGcurrentSequenceIndex + 1, this.getFrame().getSiteswapWidth())) {
			++this.intGcurrentSequenceIndex;
		} else if (this.intGcurrentThrowIndex == 0) {
			this.intGcurrentSequenceIndex = 0;
		}

		// Display siteswap throw :
		this.bolGdisplayedThrowA[this.intGcurrentThrowIndex] = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_SITESWAP);
		if (this.objGsiteswap.bolGsynchro) {
			final byte bytLdelta = Tools.getSignedBoolean(this.intGcurrentThrowIndex % 2 == 0);
			this.bolGdisplayedThrowA[this.intGcurrentThrowIndex + bytLdelta] = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_SITESWAP);
		}

		// Erase juggler and balls :
		this.objGjuggleMasterProJFrame.doEraseJugglerAndBalls();

		// Do actions :
		this.setDynamicRatios();
		if (this.bolGnewSiteswapThrow) {
			this.setCurrentStyle();
		}
		if (this.bolGrobot && this.bytGstrobeRatio > 0 || this.bolGflash && this.bytGstrobeRatio <= 0) {
			this.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE);
			this.objGjuggleMasterProJFrame.doEraseBalls();
		}
		this.objGjuggleMasterProJFrame.doActions();

		// Move balls :
		this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bytGcatchBallsVolume =
																				this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bytGcatchBallsVolume =
																																						this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bytGthrowBallsVolume =
																																																								this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bytGthrowBallsVolume =
																																																																										(byte) 0;

		if (this.objGsiteswap.intGballsNumber > 0) {
			for (final Ball objLball : this.objGballA) {
				objLball.move(this);
			}
		}

		// Move hands :
		this.bolGnewSiteswapThrow = this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].move(this);
		this.bolGnewSiteswapThrow |= this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].move(this);
		if (this.bolGnewSiteswapThrow && this.intGcurrentThrowIndex == intLpreviousThrowIndex) {
			++this.lngGthrowsCount;
		}

		// Calculate body lines :
		JuggleMasterPro.this.objGbody.setBodyLines(this.objGhandA, JuggleMasterPro.this.intGdrawingSize, this.intGbaseY);

		// Draw juggler and balls :
		this.objGjuggleMasterProJFrame.doDrawJugglerAndBalls();

		// Draw siteswap :
		if (this.bolGnewSiteswapThrow) {
			this.objGjuggleMasterProJFrame.doDrawSiteswap();
		}

		// Play sounds :
		if (this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_SOUNDS) && !this.bolGfX) {
			this.doPlaySounds();
		}

		// Refresh data tables :
		try {
			if (this.objGdataJFrame.isVisible()) {
				final Thread objLsetValuesThread = new Thread(this.objGdataJFrame);
				objLsetValuesThread.start();
			}
		} catch (final Throwable objPthrowable) {}
		this.objGjuggleMasterProJFrame.doRepaintFrame();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadPattern() {

		// Load siteswap :
		final boolean bolLreverse = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP);
		this.objGsiteswap =
							new Siteswap(this.getPatternStringValue(bolLreverse ? Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP
																				: Constants.bytS_STRING_LOCAL_SITESWAP));
		if (this.objGsiteswap.bytGstatus >= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER) {
			this.setControlString(	bolLreverse ? Constants.bytS_STRING_LOCAL_SITESWAP : Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP,
									this.objGsiteswap.getReverseSiteswapString());
		}

		// Load ball colors :
		this.objGballColors =
								new BallsColors(this.getPatternStringValue(this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
																																						? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
																																						: Constants.bytS_STRING_LOCAL_COLORS));

		// Load style :
		this.objGstyleA[Constants.bytS_UNCLASS_INITIAL] =
															this.objGpatternsManager.getStyle(	this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_STYLE)
																																								? this.getPatternStringValue(Constants.bytS_STRING_LOCAL_STYLE)
																																								: Constants.strS_STRING_LOCAL_STYLE_DEFAULT,
																								!this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE));
		int intLcurrentStyleLinesNumber =
											this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA.length
												/ (Constants.bytS_ENGINE_COORDONATES_NUMBER * 2);
		while (intLcurrentStyleLinesNumber < 4 * this.objGsiteswap.bytGhighestThrow
				|| this.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE
				&& intLcurrentStyleLinesNumber % this.objGsiteswap.intGthrowsNumber != 0) {
			intLcurrentStyleLinesNumber +=
											this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA.length
												/ (Constants.bytS_ENGINE_COORDONATES_NUMBER * 2);
		}
		intLcurrentStyleLinesNumber *= 2;
		this.objGstyleA[Constants.bytS_UNCLASS_CURRENT] = new Style(this.objGstyleA[Constants.bytS_UNCLASS_INITIAL], intLcurrentStyleLinesNumber);

		// Set catches & throws that don't move :
		if (this.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE) {
			this.bolGnoStyleCatchA = new boolean[intLcurrentStyleLinesNumber];
			Arrays.fill(this.bolGnoStyleCatchA, true);
			this.bolGnoStyleThrowA = new boolean[intLcurrentStyleLinesNumber];
			Arrays.fill(this.bolGnoStyleThrowA, true);
			for (int intLthrowIndex = 0; intLthrowIndex < this.objGsiteswap.intGthrowsNumber; ++intLthrowIndex) {
				for (int intLthrowBallIndex = 0; intLthrowBallIndex < this.objGsiteswap.intGthrowBallsNumberA[intLthrowIndex]; ++intLthrowBallIndex) {
					final byte bytLthrow = this.objGsiteswap.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex];
					if (bytLthrow != 0 && bytLthrow != 2) {
						if (this.objGsiteswap.bolGsynchro) {
							this.bolGnoStyleCatchA[(intLthrowIndex + Math.abs(bytLthrow) + (bytLthrow > 0
																											? Tools.getSignedBoolean((intLthrowIndex & 1) == 0)
																											: 0))
													% this.objGsiteswap.intGthrowsNumber] = false;
							this.bolGnoStyleThrowA[intLthrowIndex + Tools.getSignedBoolean((intLthrowIndex & 1) == 0)] = false;
						} else {
							this.bolGnoStyleCatchA[(intLthrowIndex + bytLthrow) % this.objGsiteswap.intGthrowsNumber] = false;
							this.bolGnoStyleThrowA[intLthrowIndex] = false;
						}
					}
				}
			}

			for (int intLindex = 1; intLindex * this.objGsiteswap.intGthrowsNumber < intLcurrentStyleLinesNumber; ++intLindex) {
				System.arraycopy(	this.bolGnoStyleCatchA,
									0,
									this.bolGnoStyleCatchA,
									intLindex * this.objGsiteswap.intGthrowsNumber,
									this.objGsiteswap.intGthrowsNumber);
				System.arraycopy(	this.bolGnoStyleThrowA,
									0,
									this.bolGnoStyleThrowA,
									intLindex * this.objGsiteswap.intGthrowsNumber,
									this.objGsiteswap.intGthrowsNumber);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doLoadSounds() {
		switch (this.bytGprogramType) {
			case Constants.bytS_STATE_PROGRAM_LOCAL_APPLICATION:
				this.objGapplicationClipAA = new ExtendedClip[Constants.bytS_FILES_SOUNDS_NUMBER][Constants.bytS_ENGINE_SOUNDS_BUFFERING];
				for (byte bytLsoundFileIndex = 0; bytLsoundFileIndex < Constants.bytS_FILES_SOUNDS_NUMBER; ++bytLsoundFileIndex) {
					for (byte bytLbufferingIndex = 0; bytLbufferingIndex < Constants.bytS_ENGINE_SOUNDS_BUFFERING; ++bytLbufferingIndex) {
						this.objGapplicationClipAA[bytLsoundFileIndex][bytLbufferingIndex] = new ExtendedClip(this, bytLsoundFileIndex);
					}
				}
				break;

			case Constants.bytS_STATE_PROGRAM_LOCAL_APPLET:
			case Constants.bytS_STATE_PROGRAM_WEB_APPLET:
				this.objGappletAudioClipA = new AudioClip[Constants.bytS_FILES_SOUNDS_NUMBER];
				for (byte bytLsoundFileIndex = 0; bytLsoundFileIndex < Constants.bytS_FILES_SOUNDS_NUMBER; ++bytLsoundFileIndex) {
					try {
						this.objGappletAudioClipA[bytLsoundFileIndex] =
																		Applet.newAudioClip(new URL(Strings.doConcat(	this.strS_CODE_BASE,
																														Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_SOUNDS],
																														this.chrGpathSeparator,
																														Constants.strS_FILE_SOUND_NAME_A[bytLsoundFileIndex])));
					} catch (final Throwable objPthrowable) {
						Tools.err("Error while initializing sound");
						this.objGappletAudioClipA[bytLsoundFileIndex] = null;
					}
				}
				break;
		}
	}

	final public void doPausePattern() {
		this.doStopPattern(false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPsoundFileIndex
	 */

	final private void doPlaySound(byte bytPsoundFileIndex, byte bytPvolumePercentage, byte bytPbalancePercentage) {

		switch (this.bytGprogramType) {
			case Constants.bytS_STATE_PROGRAM_LOCAL_APPLICATION:
				this.objGapplicationClipAA[bytPsoundFileIndex][this.bytGbufferedSoundIndex].setBalance(bytPbalancePercentage);
				if (bytPvolumePercentage != Constants.bytS_UNCLASS_NO_VALUE) {
					this.objGapplicationClipAA[bytPsoundFileIndex][this.bytGbufferedSoundIndex].setVolume(bytPvolumePercentage);
				}
				this.objGapplicationClipAA[bytPsoundFileIndex][this.bytGbufferedSoundIndex].play();
				if (Constants.bolS_UNCLASS_DEBUG) {
					String strLsound = Strings.strS_EMPTY;
					try {
						strLsound =
									Strings.doConcat(	Character.toUpperCase(Constants.strS_FILE_SOUND_NAME_A[bytPsoundFileIndex].charAt(0)),
														Constants.strS_FILE_SOUND_NAME_A[bytPsoundFileIndex].substring(	1,
																														Constants.strS_FILE_SOUND_NAME_A[bytPsoundFileIndex].lastIndexOf('.')));
					} catch (final Exception Throwable) {
						strLsound = Constants.strS_FILE_SOUND_NAME_A[bytPsoundFileIndex];
					}
					try {
						final byte bytLvolumePercentage = bytPvolumePercentage == Constants.bytS_UNCLASS_NO_VALUE ? 100 : bytPvolumePercentage;
						Tools.out(Strings.doConcat(	strLsound,
													" sound at volume ",
													bytLvolumePercentage,
													"% (",
													this.objGapplicationClipAA[bytPsoundFileIndex][this.bytGbufferedSoundIndex].getVolume(bytLvolumePercentage),
													" dB) and balance ",
													bytPbalancePercentage,
													'%'));
					} catch (final Throwable objPthrowable) {};
				}
				break;

			case Constants.bytS_STATE_PROGRAM_LOCAL_APPLET:
			case Constants.bytS_STATE_PROGRAM_WEB_APPLET:
				if (this.objGappletAudioClipA[bytPsoundFileIndex] != null) {
					try {
						this.objGappletAudioClipA[bytPsoundFileIndex].play();
					} catch (final Throwable objPthrowable) {
						Tools.err("Error while playing sound : ", Constants.strS_FILE_SOUND_NAME_A[bytPsoundFileIndex]);
						this.objGappletAudioClipA[bytPsoundFileIndex] = null;
					}
				}
				break;
		}
	}

	// public boolean bolGdataJFrame;

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doPlaySounds() {

		// Init sound parameters :
		final byte[] bytLvolumeA = new byte[Constants.bytS_FILES_SOUNDS_NUMBER];
		final byte[] bytLbalanceA = new byte[Constants.bytS_FILES_SOUNDS_NUMBER];
		Arrays.fill(bytLvolumeA, (byte) 0);
		Arrays.fill(bytLbalanceA, Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE);

		for (byte bytLsoundIndex = 0; bytLsoundIndex < Constants.bytS_FILES_SOUNDS_NUMBER; ++bytLsoundIndex) {

			boolean bolLsound = false;
			byte bytLvolume = Constants.bytS_ENGINE_VOLUME_DEFAULT_VALUE;
			byte bytLbalance = Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE;

			switch (bytLsoundIndex) {
				case Constants.bytS_FILE_SOUND_SCREEN_PLAY:
					bolLsound = this.bolGplayScreenPlaySound;
					break;
				case Constants.bytS_FILE_SOUND_SCREEN_SHOT:
					bolLsound = this.bolGplayScreenShotSound;
					break;
				case Constants.bytS_FILE_SOUND_LEFT_CATCH:
					bolLsound =
								this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS)
									&& this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bytGcatchBallsVolume > 0
									&& this.lngGthrowsCount >= this.objGsiteswap.intGballsNumber && !this.bolGplayScreenShotSound;
					bytLvolume = this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bytGcatchBallsVolume;
					bytLbalance =
									bolLsound ? this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].getSoundsBalance()
												: Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE;
					break;
				case Constants.bytS_FILE_SOUND_RIGHT_CATCH:
					bolLsound =
								this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS)
									&& this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bytGcatchBallsVolume > 0
									&& this.lngGthrowsCount >= this.objGsiteswap.intGballsNumber && !this.bolGplayScreenShotSound;
					bytLvolume = this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bytGcatchBallsVolume;
					bytLbalance =
									bolLsound ? this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].getSoundsBalance()
												: Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE;
					break;
				case Constants.bytS_FILE_SOUND_LEFT_THROW:
					bolLsound =
								this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS)
									&& this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bytGthrowBallsVolume > 0 && !this.bolGplayScreenShotSound;
					bytLvolume = this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bytGthrowBallsVolume;
					bytLbalance =
									bolLsound ? this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].getSoundsBalance()
												: Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE;
					break;
				case Constants.bytS_FILE_SOUND_RIGHT_THROW:
					bolLsound =
								this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS)
									&& this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bytGthrowBallsVolume > 0 && !this.bolGplayScreenShotSound;
					bytLvolume = this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bytGthrowBallsVolume;
					bytLbalance =
									bolLsound ? this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].getSoundsBalance()
												: Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE;
					break;
				case Constants.bytS_FILE_SOUND_METRONOME:
					bolLsound =
								this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_METRONOME) && this.bolGnewSiteswapThrow
									&& !this.bolGplayScreenShotSound;
					break;
			}
			bytLvolumeA[bytLsoundIndex] = bolLsound ? bytLvolume : (byte) 0;
			bytLbalanceA[bytLsoundIndex] = bytLbalance;
		}

		// Sound buffering :
		boolean bolPsound = false;
		for (byte bytLsoundIndex = 0; bytLsoundIndex < Constants.bytS_FILES_SOUNDS_NUMBER; ++bytLsoundIndex) {
			if (bytLvolumeA[bytLsoundIndex] != 0) {
				bolPsound = true;
				if (bytLsoundIndex != Constants.bytS_FILE_SOUND_METRONOME) {
					bytLvolumeA[Constants.bytS_FILE_SOUND_METRONOME] = 0;
					break;
				}
			}
		}

		// Play sounds :
		if (bolPsound) {
			this.bytGbufferedSoundIndex++;
			if (this.bytGbufferedSoundIndex == Constants.bytS_ENGINE_SOUNDS_BUFFERING) {
				this.bytGbufferedSoundIndex = 0;
			}
			for (byte bytLsoundIndex = 0; bytLsoundIndex < Constants.bytS_FILES_SOUNDS_NUMBER; ++bytLsoundIndex) {
				if (bytLvolumeA[bytLsoundIndex] != 0) {
					this.doPlaySound(bytLsoundIndex, bytLvolumeA[bytLsoundIndex], bytLbalanceA[bytLsoundIndex]);
				}
			}
		}
		this.bolGplayScreenShotSound = false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcontrolJFrame
	 */
	final public void doQuit(ControlJFrame objPcontrolJFrame) {

		if (this.bolGscreenPlay) {
			this.objGanimatedGIFEncoder.finish();
		}
		// Play closing sound :
		if (Preferences.readLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_SOUNDS)) {
			this.doPlaySound(Constants.bytS_FILE_SOUND_CLOSE, Constants.bytS_ENGINE_VOLUME_DEFAULT_VALUE, Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE);
		}
		if (objPcontrolJFrame != null) {
			objPcontrolJFrame.dispose();
		}
		if (this.bolGforceExit) {
			Tools.sleep(808, "Error while deferrizing application termination");
			System.exit(0);
		} else {
			try {
				this.objGdataJFrame.setVisible(false);
				this.objGdataJFrame.dispose();
				this.objGdataJFrame = null;
				this.destroy();
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while destroying application frame");
			}
			System.exit(0);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 */
	final public void doStartPattern(int intPobjectIndex) {

		this.doStopPattern();
		this.intGactions = Constants.intS_ACTION_DO_NOTHING;
		this.bytGbufferedSoundIndex = 0;
		this.intGtheoricalMilliSecondsFramesDelay = 128;
		this.lngGrealMilliSecondsFramesLastTime = this.lngGrealMilliSecondsFramesNextTime = System.currentTimeMillis();

		this.intGobjectIndex = intPobjectIndex;
		this.doLoadPattern();
		switch (this.objGsiteswap.bytGstatus) {

			case Constants.bytS_STATE_SITESWAP_PLAYABLE:

				// Copy arrays :
				this.bytGsiteswapValueAA = new byte[this.objGsiteswap.intGthrowsNumber][this.objGsiteswap.bytGballThrowAA[0].length];
				this.intGballsNumberPerThrowA = new int[this.objGsiteswap.intGthrowsNumber];
				for (int intLthrowIndex = 0; intLthrowIndex < this.objGsiteswap.intGthrowsNumber; ++intLthrowIndex) {
					this.intGballsNumberPerThrowA[intLthrowIndex] = this.objGsiteswap.intGthrowBallsNumberA[intLthrowIndex];
					for (int intLthrowBallIndex = 0; intLthrowBallIndex < this.objGsiteswap.bytGballThrowAA[0].length; ++intLthrowBallIndex) {
						this.bytGsiteswapValueAA[intLthrowIndex][intLthrowBallIndex] =
																						this.objGsiteswap.bytGballThrowAA[intLthrowIndex][intLthrowBallIndex];
					}
				}

				// Init animation :
				this.setDynamicRatios();
				this.setBackgroundColors();
				this.setBalls();
				this.initJugglerColors();
				this.initBallsColors();
				this.initSiteswapColors();
				this.initGraphicsDimension();
				this.initBallsProperties();
				this.initAnimationProperties();
				this.initBallsAndHandsPositions();

				// Init counters :
				this.lngGframesCount = this.lngGthrowsCount = 0L;
				this.intGcurrentThrowIndex = this.intGcurrentSequenceIndex = 0;

				// Init throw dislays :
				this.bolGdisplayedThrowA = new boolean[this.objGsiteswap.intGthrowsNumber];
				Arrays.fill(this.bolGdisplayedThrowA, false);

				// Construct images :
				this.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
									| Constants.intS_ACTION_RECREATE_HANDS_IMAGES | Constants.intS_ACTION_RECREATE_BALLS_IMAGES
									| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES | Constants.intS_ACTION_RECREATE_BALLS_ERASING_IMAGES
									| Constants.intS_ACTION_RECREATE_SITESWAP_IMAGE | Constants.intS_ACTION_DRAW_SITESWAP);
				this.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_JUGGLING;
				break;

			case Constants.bytS_STATE_SITESWAP_UNPLAYABLE:
				this.setBalls();
				this.initBallsColors();
				//$FALL-THROUGH$
			case Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER:
			case Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE:
			case Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR:
			case Constants.bytS_STATE_SITESWAP_EMPTY:
				this.setBackgroundColors();
				this.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE | Constants.intS_ACTION_DRAW_SITESWAP);
				this.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_UNPLAYABLE;
				break;
		}

		// Set background :
		this.objGjuggleMasterProJFrame.initBounds();
		this.doAddAction(Constants.intS_ACTION_RECREATE_BACKGROUND_IMAGES | Constants.intS_ACTION_RECREATE_ANIMATION_IMAGE
							| Constants.intS_ACTION_INIT_DATA_FRAME);
		this.objGjuggleMasterProJFrame.doActions();

		// Start juggling :
		this.objGjugglingThread = new Thread(this);
		this.objGjugglingThread.start();
		Tools.out(this.objGpatternsManager.getInfo(this.intGobjectIndex, true, false, true, false));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doStopPattern() {
		this.doStopPattern(true);
	}

	final public void doStopPattern(boolean bolPfullStop) {

		if (this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED || this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSING
			|| this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING
			|| this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_UNPLAYABLE) {

			final byte bytLfinalStatus = bolPfullStop ? Constants.bytS_STATE_ANIMATION_STOPPED : Constants.bytS_STATE_ANIMATION_PAUSED;
			if (this.bytGanimationStatus != bytLfinalStatus) {
				this.bytGanimationStatus = bolPfullStop ? Constants.bytS_STATE_ANIMATION_STOPPING : Constants.bytS_STATE_ANIMATION_PAUSING;
				while (this.bytGanimationStatus != bytLfinalStatus) {
					try {
						Thread.yield();
					} catch (final Throwable objPthrowable) {
						Tools.err("Error while yielding processor time");
					}
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
	@Override final public String getAppletInfo() {
		return Tools.getJuggleMasterProInfo();
	}

	final private String getCommandLineHelp(ArrayList<String> strPbadParameterAL) {

		// Set parameter descriptions :
		final String[] strLparameterValueA = new String[Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER];
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_FILE] = "\"<path>\"";
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_PATTERN] = "\"<name>\"";
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_OCCURRENCE] = "<index>";
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_LANGUAGE] = "{ en | fr | de | es | it }";
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_CONSOLE] =
																				strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_EMBEDDED] =
																																						strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_ERRORS] =
																																																							strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_LOG] =
																																																																								strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_DEBUG] =
																																																																																									strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_LONG] =
																																																																																																											"{ true | false }";
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_SHORT] = Strings.strS_EMPTY;
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_QUESTION_MARK] = Strings.strS_EMPTY;
		strLparameterValueA[Constants.bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS] = Strings.strS_EMPTY;

		int intLmaxLength = 0;
		for (byte bytLparameterIndex = 0; bytLparameterIndex < Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER; ++bytLparameterIndex) {
			intLmaxLength =
							Math.max(intLmaxLength, Constants.strS_COMMAND_LINE_PARAMETER_A[bytLparameterIndex].length()
													+ strLparameterValueA[bytLparameterIndex].length());
		}

		final String[] strLparameterDescriptionA = new String[Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER];
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_FILE] = "Pattern file path to load";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_PATTERN] = "Pattern to start in the pattern file";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_OCCURRENCE] = "Occurrence of the pattern to start";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_LANGUAGE] = "Application language code { en, fr, de, es, it }";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_EMBEDDED] = "Embedding of the visual frame inside the applet";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_CONSOLE] = "Java console /";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_ERRORS] = "     including errors";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_LOG] = "File log :";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_DEBUG] = "     including debug";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_LONG] = "Help";
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_SHORT] = Strings.strS_EMPTY;
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_QUESTION_MARK] = Strings.strS_EMPTY;
		strLparameterDescriptionA[Constants.bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS] = Strings.strS_EMPTY;

		final StringBuilder objLstringBuilder = new StringBuilder(512);
		objLstringBuilder.append(Strings.doConcat(this.getAppletInfo(), Strings.strS_LINE_SEPARATOR, Strings.strS_LINE_SEPARATOR));

		String strLbadParameters = Strings.strS_EMPTY;
		final int intLbadParametersNumber = strPbadParameterAL.size();
		if (intLbadParametersNumber > 0) {
			final String strLlabel = intLbadParametersNumber > 1 ? "   Bad parameters : " : "   Bad parameter : ";
			final String strLblankLabel = Strings.getCharsString(' ', strLlabel.length());
			for (int intLparameterIndex = 0; intLparameterIndex < intLbadParametersNumber; ++intLparameterIndex) {
				strLbadParameters =
									Strings.doConcat(	strLbadParameters,
														intLparameterIndex == 0 ? strLlabel : strLblankLabel,
														strPbadParameterAL.get(intLparameterIndex),
														Strings.strS_LINE_SEPARATOR);
			}
			strLbadParameters = Strings.doConcat(strLbadParameters, Strings.strS_LINE_SEPARATOR);
		}

		if (!Constants.bolS_UNCLASS_CONSOLE) {
			objLstringBuilder.append(Strings.doConcat(this.getAppletInfo(), Strings.strS_LINE_SEPARATOR, Strings.strS_LINE_SEPARATOR));
		}

		objLstringBuilder.append(Strings.doConcat(strLbadParameters, "   Parameters :", Strings.strS_LINE_SEPARATOR));

		for (byte bytLparameterIndex = 0; bytLparameterIndex < Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER; ++bytLparameterIndex) {
			if (strLparameterDescriptionA[bytLparameterIndex].length() > 0) {
				objLstringBuilder.append(Strings.doConcat(	"      [ ",
															Constants.strS_COMMAND_LINE_PARAMETER_A[bytLparameterIndex],
															'=',
															strLparameterValueA[bytLparameterIndex],
															Strings.getSpacesString(intLmaxLength
																					- strLparameterValueA[bytLparameterIndex].length()
																					- Constants.strS_COMMAND_LINE_PARAMETER_A[bytLparameterIndex].length()),
															" ] : ",
															strLparameterDescriptionA[bytLparameterIndex],
															Strings.strS_LINE_SEPARATOR));
			}
		}
		objLstringBuilder.append(Strings.strS_LINE_SEPARATOR);

		return objLstringBuilder.toString();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public byte getControlValue(byte bytPcontrolType) {
		return this.getPatternByteValue(this.intGobjectIndex, bytPcontrolType, Constants.bytS_UNCLASS_CURRENT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getControlValue(byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.getPatternByteValue(this.intGobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public AnimationJFrame getFrame() {
		return this.objGjuggleMasterProJFrame;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPimageFileName
	 * @param intPpriority
	 * @return
	 */
	final public Image getImage(int intPimageFileIndex, int intPpriority) {
		return 0 <= intPimageFileIndex && intPimageFileIndex < Constants.strS_FILE_NAME_A.length
																								? this.getImage(Strings.doConcat(	this.strS_CODE_BASE,
																																	Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES]),
																												Constants.strS_FILE_NAME_A[intPimageFileIndex],
																												intPpriority) : null;
	}

	final public Image getImage(String strPimageFileName, int intPpriority) {
		return this.getImage(	Strings.doConcat(this.strS_CODE_BASE, Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES]),
								strPimageFileName,
								intPpriority);
	}

	final public Image getImage(String strPimageFilePath, int intPimageFileIndex, int intPpriority) {

		return this.getImage(strPimageFilePath, Constants.strS_FILE_NAME_A[intPimageFileIndex], intPpriority);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPimageFilePath
	 * @param strPimageFileName
	 * @param intPpriority
	 * @return
	 */

	final public Image getImage(String strPimageFilePath, String strPimageFileName, int intPpriority) {

		final String strLimageFileName = Strings.doConcat(strPimageFilePath, this.chrGpathSeparator, strPimageFileName);

		// Load image file :
		try {
			final Image imgL =
								this.bytGprogramType == Constants.bytS_STATE_PROGRAM_LOCAL_APPLICATION
																										? Constants.objS_GRAPHICS_TOOLKIT.getImage(strLimageFileName)
																										: this.getImage(new URL(strLimageFileName));
			if (intPpriority >= 0) {
				try {
					this.objGmediaTracker.addImage(imgL, intPpriority);
				} catch (final Throwable objPthrowable) {
					Tools.err("Error while tracking image '", strLimageFileName, '\'');
				}
			}

			// Check image size :
			final ImageIcon icoL = new ImageIcon(imgL);
			if (icoL.getIconWidth() > 0 && icoL.getIconHeight() > 0) {
				return imgL;
			}
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while loading image '", strLimageFileName, '\'');
		}
		return null;
	}

	final public ImageIcon getImageIcon(int intPimageFileIndex, int intPpriority) {
		return 0 <= intPimageFileIndex && intPimageFileIndex < Constants.strS_FILE_NAME_A.length
																								? this.getImageIcon(Strings.doConcat(	this.strS_CODE_BASE,
																																		Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES]),
																													Constants.strS_FILE_NAME_A[intPimageFileIndex],
																													intPpriority,
																													Strings.strS_EMPTY) : null;
	}

	final public ImageIcon getImageIcon(int intPimageFileIndex, int intPpriority, String strPreplacement) {
		return 0 <= intPimageFileIndex && intPimageFileIndex < Constants.strS_FILE_NAME_A.length
																								? this.getImageIcon(Strings.doConcat(	this.strS_CODE_BASE,
																																		Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES]),
																													Constants.strS_FILE_NAME_A[intPimageFileIndex],
																													intPpriority,
																													strPreplacement) : null;
	}

	final public ImageIcon getImageIcon(String strPimageFileName, int intPpriority) {
		return this.getImageIcon(	Strings.doConcat(this.strS_CODE_BASE, Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES]),
									strPimageFileName,
									intPpriority,
									Strings.strS_EMPTY);
	}

	final public ImageIcon getImageIcon(String strPimageFilePath, int intPimageFileIndex, int intPpriority) {

		return this.getImageIcon(strPimageFilePath, Constants.strS_FILE_NAME_A[intPimageFileIndex], intPpriority, Strings.strS_EMPTY);
	}

	final public ImageIcon getImageIcon(String strPimageFilePath, String strPimageFileName, int intPpriority) {
		return this.getImageIcon(strPimageFilePath, strPimageFileName, intPpriority, Strings.strS_EMPTY);
	}

	final public ImageIcon getImageIcon(String strPimageFilePath, String strPimageFileName, int intPpriority, String strPreplacement) {

		final String strLimageFileName = Strings.doConcat(strPimageFilePath, this.chrGpathSeparator, strPimageFileName);

		// Load image file :
		try {
			Image objLimage = null;

			try {
				objLimage = ImageIO.read(this.getClass().getClassLoader().getResource(strLimageFileName));
			} catch (final Throwable objPthrowable) {
				objLimage =
							this.bytGprogramType == Constants.bytS_STATE_PROGRAM_LOCAL_APPLICATION
																									? Constants.objS_GRAPHICS_TOOLKIT.getImage(strLimageFileName)
																									: this.getImage(new URL(strLimageFileName));
			}
			if (objLimage == null) {
				return null;
			}

			if (intPpriority >= 0) {
				try {
					this.objGmediaTracker.addImage(objLimage, intPpriority);
				} catch (final Throwable objPthrowable) {
					Tools.err("Error while tracking image '", strLimageFileName, '\'');
				}
			}

			// Check image size :
			final ImageIcon imgL = new ImageIcon(objLimage, strPreplacement);
			if (imgL.getIconWidth() > 0 && imgL.getIconHeight() > 0) {
				return imgL;
			}
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while loading image '", strLimageFileName, '\'');
		}
		return null;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public byte getJugglerValue() {
		return this.objGpatternsManager.getJugglerValue(this.intGobjectIndex, Constants.bytS_UNCLASS_CURRENT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getJugglerValue(byte bytPinitialOrCurrent) {
		return this.objGpatternsManager.getJugglerValue(this.intGobjectIndex, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getJugglerValue(int intPobjectIndex, byte bytPinitialOrCurrent) {
		return this.objGpatternsManager.getJugglerValue(intPobjectIndex, bytPinitialOrCurrent);
	}

	final public PatternsManager getNewPatternsManager(byte bytPpatternsManagerType) {
		switch (bytPpatternsManagerType) {
			case Constants.bytS_MANAGER_NO_PATTERN:
			case Constants.bytS_MANAGER_JM_PATTERN:
			case Constants.bytS_MANAGER_NEW_PATTERN:
				return this.getNewPatternsManager(bytPpatternsManagerType, null, null, null, true, true, null, 0);
			default:
				return null;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPlanguage
	 * @param bytPpatternsManagerType
	 * @param strPfileName
	 * @param strPtitle
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 * @param strPstartPattern
	 * @param intPstartPatternOccurence
	 * @return
	 */
	final public PatternsManager getNewPatternsManager(	byte bytPpatternsManagerType,
														String strPfileName,
														String strPfileTitle,
														ImageIcon imgP,
														boolean bolPsiteswaps,
														boolean bolPstyles,
														String strPstartPattern,
														int intPstartPatternOccurence) {

		PatternsManager objLpatternsManager = null;
		switch (bytPpatternsManagerType) {

			// Load pre-defined pattern manager :
			case Constants.bytS_MANAGER_NO_PATTERN:
			case Constants.bytS_MANAGER_JM_PATTERN:
			case Constants.bytS_MANAGER_NEW_PATTERN:
				objLpatternsManager = new PatternsManager(bytPpatternsManagerType);
				break;

			// Load new pattern manager file :
			case Constants.bytS_MANAGER_FILES_PATTERNS:

				// Set pattern file :
				final PatternsFile objLpatternsFile = new PatternsFile(this.strS_CODE_BASE, strPfileName, strPfileTitle);

				// Parse pattern file :
				objLpatternsManager =
										new PatternsManager(Constants.bytS_MANAGER_FILES_PATTERNS,
															objLpatternsFile,
															imgP,
															bolPsiteswaps,
															bolPstyles,
															strPstartPattern,
															intPstartPatternOccurence);
				break;

			default:

				// Load empty pattern manager :
				try {
					objLpatternsManager = new PatternsManager(Constants.bytS_MANAGER_NO_PATTERN);
				} catch (final Throwable objPthrowable) {
					Tools.err("Error while creating empty pattern manager");
					objLpatternsManager = null;
				}
				break;
		}
		return objLpatternsManager;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean getPatternBooleanValue(byte bytPcontrolType) {
		return this.objGpatternsManager.getPatternBooleanValue(this.intGobjectIndex, bytPcontrolType, Constants.bytS_UNCLASS_CURRENT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public boolean getPatternBooleanValue(byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGpatternsManager.getPatternBooleanValue(this.intGobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public boolean getPatternBooleanValue(int intPobjectIndex, byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGpatternsManager.getPatternBooleanValue(intPobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getPatternByteValue(int intPobjectIndex, byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return bytPcontrolType != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY && bytPcontrolType != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT
																																				? this.objGpatternsManager.getPatternByteValue(	intPobjectIndex,
																																																bytPcontrolType,
																																																bytPinitialOrCurrent)
																																				: this.objGpatternsManager.getJugglerValue(	intPobjectIndex,
																																															bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public String getPatternStringValue(byte bytPcontrolType) {
		return this.objGpatternsManager.getPatternStringValue(this.intGobjectIndex, bytPcontrolType, Constants.bytS_UNCLASS_CURRENT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public String getPatternStringValue(byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGpatternsManager.getPatternStringValue(this.intGobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public String getPatternStringValue(int intPobjectIndex, byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGpatternsManager.getPatternStringValue(intPobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public Siteswap getSiteswap() {
		return this.objGsiteswap;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPnoReverse
	 * @return
	 */
	final public Siteswap getSiteswap(boolean bolPnoReverse) {
		return this.getSiteswap(bolPnoReverse, Constants.bytS_UNCLASS_CURRENT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPnoReverse
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public Siteswap getSiteswap(boolean bolPnoReverse, byte bytPinitialOrCurrent) {
		return new Siteswap(this.getPatternStringValue(bolPnoReverse ? Constants.bytS_STRING_LOCAL_SITESWAP
																	: Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP, bytPinitialOrCurrent));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void init() {

	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initAnimationProperties() {

		this.bolGnewSiteswapThrow = false;
		if (this.objGsiteswap.bytGhighestThrow < 3) {
			this.objGsiteswap.bytGhighestThrow = 3;
		}

		// Set frame counters :
		this.fltGspeed =
							(this.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED) * this.getControlValue(Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED)
								/ 10F + 1) * 1.5F;
		this.fltGdwell = this.getControlValue(Constants.bytS_BYTE_LOCAL_DWELL);
		this.fltGfluidity = this.getControlValue(Constants.bytS_BYTE_LOCAL_FLUIDITY);
		final float fltL_1 =
								(this.fltGfluidity * (float) Math.sqrt(1.26F * this.objGsiteswap.bytGhighestThrow
																		* this.getControlValue(Constants.bytS_BYTE_LOCAL_HEIGHT)))
									/ ((this.objGsiteswap.bytGhighestThrow - this.fltGdwell / 50F) * this.fltGspeed);
		this.intGframesNumberPerThrow = Math.round(fltL_1);

		// if(intGframesNumberPerThrow == 0) return false; // Normalement ceci se passe au cours de la phase de
		// validation... On remplace cette ligne par la suivante :
		if (this.intGframesNumberPerThrow == 0) {
			this.intGframesNumberPerThrow = 1;
		}
		final float fltL_2 = fltL_1 * Tools.getSquare(this.fltGdwell) / 5000F;
		this.intGframesCountInitialForwardDelta = Math.min(2 * this.intGframesNumberPerThrow - 1, Math.max(Math.round(fltL_2), 1));
		this.intGdwellFramesIndexesDelta = 0;
		this.intGthrowLastFrameIndex = 1;

		// Set fltGheightA :
		float fltL3 = (fltL_1 / this.fltGfluidity) * this.fltGspeed * 2F / 30F;
		this.fltGheightA[0] = -0.2F * this.intGdrawingSize;
		this.fltGheightA[1] = (int) (1.25F * Tools.getSquare(fltL3) * this.intGdrawingSize);
		for (byte bytLthrowValue = 2; bytLthrowValue <= this.objGsiteswap.bytGhighestThrow; ++bytLthrowValue) {
			fltL3 = 2F * this.fltGspeed * (fltL_1 * bytLthrowValue - fltL_2) / (30F * this.fltGfluidity);
			this.fltGheightA[bytLthrowValue] = (int) (1.25F * Tools.getSquare(fltL3) * this.intGdrawingSize);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void initBallsAndHandsPositions() {

		// Init balls :
		if (this.objGsiteswap.intGballsNumber > 0) {
			for (final Ball objLball : this.objGballA) {
				objLball.bytGthrow = 0;
				objLball.intGposX = Constants.intS_GRAPHICS_HORIZONTAL_CENTER;
				objLball.intGposY = Constants.intS_GRAPHICS_VERTICAL_CENTER;
				objLball.fltGposZ = 0F;
			}
		}

		// Init hands :
		for (byte bytLside = Constants.bytS_ENGINE_LEFT_SIDE; bytLside <= Constants.bytS_ENGINE_RIGHT_SIDE; ++bytLside) {

			this.objGhandA[bytLside].intGnextCatchTimer =
															(this.objGsiteswap.bolGsynchro
																|| (this.bolGmirror == (bytLside == Constants.bytS_ENGINE_LEFT_SIDE)) ? 0 : -1);
			this.objGhandA[bytLside].bytGanimationState = 1;
			this.objGhandA[bytLside].intGposX = Constants.intS_GRAPHICS_HORIZONTAL_CENTER;
			this.objGhandA[bytLside].intGposY = Constants.intS_GRAPHICS_VERTICAL_CENTER;
			this.objGhandA[bytLside].bytGcatchBallsVolume = 0;
			this.objGhandA[bytLside].bytGthrowBallsVolume = 0;
		}
		Arrays.fill(this.objGsiteswap.intGpermutationA, 0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initBallsColors() {

		// Tools.verbose( "initBallsColors avec ",
		// this.getControlString(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
		// ? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
		// : Constants.bytS_STRING_LOCAL_COLORS));
		this.objGballColors =
								new BallsColors(this.getPatternStringValue(this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
																																						? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
																																						: Constants.bytS_STRING_LOCAL_COLORS));

		// Tools.verbose("... et qui donne : ", this.objGballColors);

		final int intLcolorsNumber = this.objGballColors.getColorsNumber();
		if (intLcolorsNumber > 0) {

			final boolean bolLrandomColors = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS);
			final boolean[] bolLmarkedColorIndexA = bolLrandomColors ? new boolean[intLcolorsNumber] : null;
			final int[] intLcolorValueA = this.objGballColors.getLogicalColorsValues(true);
			for (int intLballIndex = 0; intLballIndex < this.objGsiteswap.intGballsNumber; ++intLballIndex) {
				// Set normal color :
				int intLcolorIndex = bolLrandomColors ? (int) Math.floor(Math.random() * intLcolorsNumber) : intLballIndex % intLcolorsNumber;
				if (bolLrandomColors) {
					if (intLballIndex % intLcolorsNumber == 0) {
						Arrays.fill(bolLmarkedColorIndexA, false);
					}
					while (bolLmarkedColorIndexA[intLcolorIndex]) {
						intLcolorIndex = (intLcolorIndex + 1) % intLcolorsNumber;
					}
					bolLmarkedColorIndexA[intLcolorIndex] = true;
				}

				// Set alternate color :
				int intLalternateColorIndex = (intLcolorIndex + 1) % intLcolorsNumber;
				while (intLcolorValueA[intLcolorIndex] == intLcolorValueA[intLalternateColorIndex] && intLalternateColorIndex != intLcolorIndex) {
					intLalternateColorIndex = (intLalternateColorIndex + 1 == intLcolorsNumber ? 0 : intLalternateColorIndex + 1);
				}

				this.objGballA[intLballIndex].intGcolor = intLcolorValueA[intLcolorIndex];
				this.objGballA[intLballIndex].intGalternateColor = intLcolorValueA[intLalternateColorIndex];
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initBallsProperties() {

		Arrays.fill(this.objGsiteswap.intGpermutationA, 0);

		// For each ball :
		if (this.objGballA != null) {
			for (final Ball objLball : this.objGballA) {

				// Set intLsiteswapIndex1 :
				int intLsiteswapIndex1;
				for (intLsiteswapIndex1 = 0; intLsiteswapIndex1 < this.objGsiteswap.intGthrowsNumber + this.objGsiteswap.bytGhighestThrow
												&& this.objGsiteswap.intGpermutationA[intLsiteswapIndex1] == this.intGballsNumberPerThrowA[intLsiteswapIndex1
																																			% this.objGsiteswap.intGthrowsNumber]; ++intLsiteswapIndex1) {
					;
				}

				// Set ball properties :
				objLball.bytGanimationState = 0;
				objLball.bytGcatchingHand =
											objLball.bytGthrowingHand =
																		(this.bolGmirror
																			^ ((intLsiteswapIndex1 + (this.objGsiteswap.bolGsynchro
																																	? Constants.bytS_STATE_SITESWAP_SYNCHRO
																																	: Constants.bytS_STATE_SITESWAP_ASYNCHRO)) % 2 == 0)
																																														? Constants.bytS_ENGINE_RIGHT_SIDE
																																														: Constants.bytS_ENGINE_LEFT_SIDE);
				objLball.intGnextCatchTimer = (this.objGsiteswap.bolGsynchro ? -(intLsiteswapIndex1 / 2) * 2 : -intLsiteswapIndex1);

				// Correct intGpermutationA :
				while (intLsiteswapIndex1 < this.objGsiteswap.intGthrowsNumber + this.objGsiteswap.bytGhighestThrow) {
					++this.objGsiteswap.intGpermutationA[intLsiteswapIndex1];

					final int intLsiteswapIndex2 =
													this.bytGsiteswapValueAA[intLsiteswapIndex1 % this.objGsiteswap.intGthrowsNumber][this.intGballsNumberPerThrowA[intLsiteswapIndex1
																																									% this.objGsiteswap.intGthrowsNumber]
																																		- this.objGsiteswap.intGpermutationA[intLsiteswapIndex1]];

					intLsiteswapIndex1 +=
											(this.objGsiteswap.bolGsynchro && intLsiteswapIndex2 < 0
																									? Tools.getSignedBoolean(intLsiteswapIndex1 % 2 == 0)
																										- intLsiteswapIndex2 : intLsiteswapIndex2);
				}
			}
		}
	}

	final private void initCommandLineParameters(boolean bolPapplication, String... strPparameterA) {

		// Init default parameter values :
		this.bolGcommandLineHelp = false;
		this.strGcommandLineFileParameter = this.strGcommandLinePatternParameter = this.strGcommandLineLanguageParameter = null;
		this.intGcommandLineOccurrenceParameter = 0;

		if (bolPapplication) {

			// Application mode :
			if (strPparameterA.length > 0) {
				final ArrayList<String> strLbadParameterAL = new ArrayList<String>(strPparameterA.length);
				Parameter: for (final String strLcommandLineParameter : strPparameterA) {
					final int intLequalIndex = strLcommandLineParameter.indexOf('=');
					if (intLequalIndex > 0) {
						final String strLcommandLineParameterName = Strings.untabDashTrim(strLcommandLineParameter.substring(0, intLequalIndex));
						byte bytLparameterIndex = 0;
						for (; bytLparameterIndex < Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER; ++bytLparameterIndex) {
							if (strLcommandLineParameterName.equalsIgnoreCase(Constants.strS_COMMAND_LINE_PARAMETER_A[bytLparameterIndex].trim())
								&& this.setCommandLineParameter(bytLparameterIndex, strLcommandLineParameter.substring(intLequalIndex + 1))) {
								continue Parameter;
							}
						}
					} else {
						byte bytLparameterIndex = 0;
						final String strLcommandLineParameterName = Strings.untabDashTrim(strLcommandLineParameter);
						for (; bytLparameterIndex < Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER; ++bytLparameterIndex) {
							switch (bytLparameterIndex) {
								case Constants.bytS_COMMAND_LINE_PARAMETER_EMBEDDED:
								case Constants.bytS_COMMAND_LINE_PARAMETER_CONSOLE:
								case Constants.bytS_COMMAND_LINE_PARAMETER_LOG:
								case Constants.bytS_COMMAND_LINE_PARAMETER_ERRORS:
								case Constants.bytS_COMMAND_LINE_PARAMETER_DEBUG:
								case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_LONG:
								case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_SHORT:
								case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_QUESTION_MARK:
								case Constants.bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS:
									if (strLcommandLineParameterName.equalsIgnoreCase(Constants.strS_COMMAND_LINE_PARAMETER_A[bytLparameterIndex].trim())) {
										this.setCommandLineParameter(bytLparameterIndex, "true");
										bytLparameterIndex = Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER;
										continue Parameter;
									}
							}
						}
					}
					strLbadParameterAL.add(strLcommandLineParameter);
					this.bolGcommandLineHelp = true;
				}

				// Bad parameters found :
				if (this.bolGcommandLineHelp) {
					final String strLhelp = this.getCommandLineHelp(strLbadParameterAL);
					synchronized (System.out) {
						System.out.print(strLhelp);
						System.out.flush();
					}
					try {
						final FileWriter objLfileWriter = new FileWriter(Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LOG], true);
						objLfileWriter.append(strLhelp);
						objLfileWriter.flush();
						objLfileWriter.close();
					} catch (final Throwable objPthrowable) {}
					System.exit(0);
				}
			}

		} else {
			// Applet mode :
			for (byte bytLparameterIndex = 0; bytLparameterIndex < Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER; ++bytLparameterIndex) {
				final String strLcommandLineParameterValue = this.getParameter(Constants.strS_COMMAND_LINE_PARAMETER_A[bytLparameterIndex]);
				if (strLcommandLineParameterValue != null) {
					this.setCommandLineParameter(bytLparameterIndex, strLcommandLineParameterValue);
				}
			}
		}

		// Reset unusable parameter values :
		if (this.strGcommandLineFileParameter == null) {
			this.strGcommandLinePatternParameter = null;
		}
		this.intGcommandLineOccurrenceParameter =
													this.strGcommandLinePatternParameter == null
																								? 0
																								: Math.max(1, this.intGcommandLineOccurrenceParameter);
	}

	final private void initFrames() {

		// Juggle panel :
		final ControlJFrame objLcontrolJFrame = new ControlJFrame(this);
		this.objGmediaTracker = new MediaTracker(objLcontrolJFrame);

		// Field initializations :
		this.fltGheightA = new float[Constants.bytS_ENGINE_THROWS_VALUES_NUMBER];
		this.objGanimatedGIFEncoder = new AnimatedGIFEncoder();
		this.objGstyleA = new Style[2];
		this.objGbackgroundColorA = new Color[2];
		this.objGjugglerColorA = new Color[2];
		this.objGsiteswapColorA = new Color[2];
		this.objGchestColorA = new Color[2];

		// Juggle objects :
		objLcontrolJFrame.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_JUGGLER);
		this.objGbody = new Body();
		this.objGhandA = new Hand[2];
		this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE] = new Hand(Constants.bytS_ENGINE_LEFT_SIDE);
		this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE] = new Hand(Constants.bytS_ENGINE_RIGHT_SIDE);
		objLcontrolJFrame.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_JUGGLE_PATTERNS_MANAGER);
		this.objGpatternsManager = this.getNewPatternsManager(Constants.bytS_MANAGER_JM_PATTERN);
		this.intGobjectIndex = this.objGpatternsManager.getPatternsFileManager().intGstartObjectIndex;
		objLcontrolJFrame.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_LOADING_JM_PATTERN);
		this.doLoadPattern();

		// Display frames :
		this.setBackgroundColors();
		objLcontrolJFrame.doDisplayFrames();

		// Play opening sound :
		this.doLoadSounds();
		if (Preferences.readLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_SOUNDS)) {
			this.doPlaySound(Constants.bytS_FILE_SOUND_OPEN, Constants.bytS_ENGINE_VOLUME_DEFAULT_VALUE, Constants.bytS_ENGINE_BALANCE_DEFAULT_VALUE);
		}

		this.objGjuggleMasterProJFrame.initBufferingAndFonts();

		// Manage command line parameters :
		if (this.strGcommandLineFileParameter != null) {
			this.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_UNPLAYABLE;
			this.objGjuggleMasterProJFrame.doCreateBackgroundImages();
			new Thread(this).start();
			if (FileActions.doLoadJugglePatternsManager(objLcontrolJFrame,
														Constants.bytS_MANAGER_FILES_PATTERNS,
														false,
														this.strGcommandLineFileParameter,
														Strings.getRightDotTrimmedString(this.strGcommandLineFileParameter),
														objLcontrolJFrame.getFileImageIcon(true, true, true),
														true,
														true,
														true,
														this.strGcommandLinePatternParameter,
														this.intGcommandLineOccurrenceParameter)) {
				this.intGobjectIndex =
										objLcontrolJFrame.objGobjectsJList.getPatternIndex(objLcontrolJFrame.objGobjectsJList.getFilteredPatternRenewedIndex(	objLcontrolJFrame	.getPatternsManager()
																																													.getPatternsFileManager().intGstartObjectIndex,
																																								true));
				objLcontrolJFrame.doRestartJuggling();
				return;
			}
		}
		objLcontrolJFrame.doStartJuggling(this.intGobjectIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void initGraphicsDimension() {

		this.intGbaseY = 0;
		this.intGdrawingSize = 400;
		this.bytGdefaults = Constants.bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE;
		this.bolGdefaults = false;

		this.initBallsProperties();
		this.initAnimationProperties();
		this.initBallsAndHandsPositions();

		int intLmaximumPosY = 69;
		int intLminimumPosY = -211;
		int intLmaximumPosX = -1000;
		int intLminimumPosX = 1000;

		// Do a complete tour to init values :
		for (this.lngGframesCount = 0L; this.lngGframesCount < (this.intGframesNumberPerThrow * (this.objGsiteswap.intGthrowsNumber
																									+ this.objGsiteswap.bytGhighestThrow + this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA.length
																																			/ (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER))); ++this.lngGframesCount) {

			// Move balls :
			if (this.objGsiteswap.intGballsNumber > 0) {
				for (final Ball objLball : this.objGballA) {
					objLball.move(this);
					intLminimumPosX = Math.min(intLminimumPosX, objLball.intGposX);
					intLminimumPosY = Math.min(intLminimumPosY, objLball.intGposY);
					intLmaximumPosX = Math.max(intLmaximumPosX, objLball.intGposX);
					intLmaximumPosY = Math.max(intLmaximumPosY, objLball.intGposY);
				}
			}

			// Move hands :
			this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].move(this);
			this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].move(this);
			for (final Hand objLhand : this.objGhandA) {
				intLminimumPosX = Math.min(intLminimumPosX, objLhand.intGposX);
				intLminimumPosY = Math.min(intLminimumPosY, objLhand.intGposY);
				intLmaximumPosX = Math.max(intLmaximumPosX, objLhand.intGposX);
				intLmaximumPosY = Math.max(intLmaximumPosY, objLhand.intGposY);
			}

			// Move body :
			JuggleMasterPro.this.objGbody.setBodyLines(this.objGhandA, JuggleMasterPro.this.intGdrawingSize, this.intGbaseY);
			intLminimumPosX = Math.min(JuggleMasterPro.this.objGbody.getMinimumArmX(), intLminimumPosX);
			intLmaximumPosX = Math.min(JuggleMasterPro.this.objGbody.getMaximumArmX(), intLmaximumPosX);
		}

		if (intLmaximumPosY > intLminimumPosY) {
			this.intGdrawingSize = Math.min(Constants.intS_GRAPHICS_MAXIMUM_DRAWING_SIZE, (int) (136000F / (intLmaximumPosY - intLminimumPosY)));
			this.intGbaseY = (int) (370F - ((float) intLmaximumPosY * (float) this.intGdrawingSize) / 400F);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void initJugglerColors() {

		final byte bytLgammaCorrection = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION);
		for (byte bytLnightDay = Constants.bytS_ENGINE_NIGHT; bytLnightDay <= Constants.bytS_ENGINE_DAY; ++bytLnightDay) {

			// Set juggler color :
			this.objGjugglerColorA[bytLnightDay] =
													Tools.getPenGammaColor(	this.getPatternStringValue(Tools.getEnlightedStringType(Constants.bytS_STRING_LOCAL_JUGGLER_DAY,
																																	bytLnightDay == Constants.bytS_ENGINE_DAY)),
																			bytLgammaCorrection);
			if (this.objGjugglerColorA[bytLnightDay] == null) {
				this.objGjugglerColorA[bytLnightDay] =
														Tools.getPenGammaColor(	bytLnightDay == Constants.bytS_ENGINE_NIGHT
																															? Constants.strS_STRING_LOCAL_JUGGLER_NIGHT_DEFAULT
																															: Constants.strS_STRING_LOCAL_JUGGLER_DAY_DEFAULT,
																				bytLgammaCorrection);
			}

			// Set body color :
			this.objGchestColorA[bytLnightDay] = Tools.getMiddleColor(this.objGjugglerColorA[bytLnightDay], this.objGbackgroundColorA[bytLnightDay]);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	/**
	 * @param bolPapplication
	 */
	final public void initProgramProperties(boolean bolPapplication) {

		// Applet properties :
		// new JuggleJavaVersion();
		if (bolPapplication) {
			this.bytGprogramType = Constants.bytS_STATE_PROGRAM_LOCAL_APPLICATION;
			this.strS_CODE_BASE = null;
		} else {
			this.bytGprogramType = Constants.bytS_STATE_PROGRAM_WEB_APPLET;
			this.strS_CODE_BASE = this.getCodeBase().toString();
			if (this.strS_CODE_BASE.toLowerCase().startsWith("file:/")) {
				this.bytGprogramType = Constants.bytS_STATE_PROGRAM_LOCAL_APPLET;
			}
			this.getAppletContext().showStatus("\370 JuggleMaster Pro");
		}

		// Console properties :
		// try {
		// System.setOut(new PrintStream(System.out, true, "IBM850"));
		// } catch (final Throwable objPthrowable) {
		// Tools.errors("Error while setting console encoding");
		// }

		// Program main exception :
		if (Constants.bolS_UNCLASS_CATCH_ALL_EXCEPTIONS) {
			try {
				Thread.setDefaultUncaughtExceptionHandler(this);
			} catch (final Throwable objPthrowable) {
				Tools.err("Error trapped");
			}
		}

		// Program trust :
		this.bolGforceExit = true;
		try {
			final java.util.prefs.Preferences objLpreferences =
																java.util.prefs.Preferences.systemNodeForPackage(jugglemasterpro.engine.JuggleMasterPro.class);
			this.bolGprogramTrusted = objLpreferences != null;
		} catch (final Throwable objPthrowable) {
			this.bolGprogramTrusted = false;
		}

		// File separator :
		this.chrGpathSeparator =
									(this.bytGprogramType == Constants.bytS_STATE_PROGRAM_LOCAL_APPLICATION
										|| this.bytGprogramType == Constants.bytS_STATE_PROGRAM_LOCAL_APPLET ? File.separatorChar : '/');

		// Screen captures :
		this.bolGcoloredBorder = false;
		this.bolGplayScreenShotSound = false;
		this.bolGscreenPlay = false;
		this.bolGplayScreenPlaySound = false;
		// this.bolGdataJFrame = false;
	}

	final public void initSiteswapColors() {

		final byte bytLgammaCorrection = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION);
		for (byte bytLnightDay = Constants.bytS_ENGINE_NIGHT; bytLnightDay <= Constants.bytS_ENGINE_DAY; ++bytLnightDay) {

			// Set siteswap color :
			this.objGsiteswapColorA[bytLnightDay] =
													Tools.getPenGammaColor(	this.getPatternStringValue(Tools.getEnlightedStringType(Constants.bytS_STRING_LOCAL_SITESWAP_DAY,
																																	bytLnightDay == Constants.bytS_ENGINE_DAY)),
																			bytLgammaCorrection);
			if (this.objGsiteswapColorA[bytLnightDay] == null) {
				this.objGsiteswapColorA[bytLnightDay] =
														Tools.getPenGammaColor(	bytLnightDay == Constants.bytS_ENGINE_NIGHT
																															? Constants.strS_STRING_LOCAL_SITESWAP_NIGHT_DEFAULT
																															: Constants.strS_STRING_LOCAL_SITESWAP_DAY_DEFAULT,
																				bytLgammaCorrection);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPballIndex
	 * @return
	 */
	final public boolean isAlternateBallColor(int intPballIndex) {
		return (this.getControlValue(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS) == Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH
				&& this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS) ? this.objGballA[intPballIndex].bolGcatchAlternateColor
																								: this.bytGalternateColorsRatio < 0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean isBooleanLocal(byte bytPcontrolType) {
		return bytPcontrolType >= 0 && bytPcontrolType < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER
																										? this.objGpatternsManager.bolGbooleanIsLocalA[bytPcontrolType]
																										: false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean isByteLocal(byte bytPcontrolType) {
		return bytPcontrolType >= 0 && bytPcontrolType < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
																									? this.objGpatternsManager.bolGbyteIsLocalA[bytPcontrolType]
																									: false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean isStringLocal(byte bytPcontrolType) {
		return bytPcontrolType >= 0 && bytPcontrolType < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER
																									? this.objGpatternsManager.bolGstringIsLocalA[bytPcontrolType]
																									: false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void run() {

		while (this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING
				|| this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSING
				|| this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_PAUSED
				|| this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_UNPLAYABLE) {
			this.intGtheoricalMilliSecondsFramesDelay =
														(int) ((Constants.bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE * Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE) / (this.fltGfluidity * this.fltGspeed));
			// this.intGtheoricalMilliSecondsFramesDelay =
			// (int) (JuggleTools.getSquare(Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE) / JuggleTools.getSquare(this.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED)));
			// this.intGtheoricalMilliSecondsFramesDelay =
			// 1 + 10 * (Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE - this.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED));
			this.lngGrealMilliSecondsFramesLastTime = this.lngGrealMilliSecondsFramesNextTime;
			this.lngGrealMilliSecondsFramesNextTime = System.currentTimeMillis();
			this.objGanimatedGIFEncoder.setDelay(this.intGtheoricalMilliSecondsFramesDelay);
			this.doJuggle();
			Tools.sleep(this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING ? this.intGtheoricalMilliSecondsFramesDelay
																							: 128 / (this.bolGscreenPlay ? 2 : 1),
						"Error while putting animation to short sleep");
		}
		this.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_STOPPED;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setBackgroundColors() {

		final byte bytLgammaCorrection = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION);
		for (byte bytLnightDay = Constants.bytS_ENGINE_NIGHT; bytLnightDay <= Constants.bytS_ENGINE_DAY; ++bytLnightDay) {

			// Set color :
			this.objGbackgroundColorA[bytLnightDay] =
														Tools.getPenGammaColor(	this.getPatternStringValue(Tools.getEnlightedStringType(Constants.bytS_STRING_LOCAL_BACKGROUND_DAY,
																																		bytLnightDay == Constants.bytS_ENGINE_DAY)),
																				bytLgammaCorrection);

			// Set default color :
			if (this.objGbackgroundColorA[bytLnightDay] == null) {
				this.objGbackgroundColorA[bytLnightDay] =
															Tools.getPenGammaColor(	bytLnightDay == Constants.bytS_ENGINE_NIGHT
																																? Constants.strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT
																																: Constants.strS_STRING_LOCAL_BACKGROUND_DAY_DEFAULT,
																					bytLgammaCorrection);
			}
		}
	}

	final public void setBallRandomColor(Ball objPball) {

		if (this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS)
			&& this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS)
			&& this.getControlValue(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS) == Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH) {
			// this.strGcolors =
			// this.getControlString(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
			// ? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
			// : Constants.bytS_STRING_LOCAL_COLORS);

			final int intLcolorsNumber = this.objGballColors.getColorsNumber();
			if (intLcolorsNumber > 0) {

				// final char[] chrLcolorsStringA = this.strGcolors.toCharArray();
				final int intLcolorIndex = (int) Math.floor(Math.random() * intLcolorsNumber);
				int intLalternateColorIndex = (int) Math.floor(Math.random() * intLcolorsNumber);
				final int[] intLcolorValueA = this.objGballColors.getLogicalColorsValues(true);

				while (intLcolorValueA[intLcolorIndex] == intLcolorValueA[intLalternateColorIndex] && intLalternateColorIndex != intLcolorIndex) {
					intLalternateColorIndex = (intLalternateColorIndex + 1 == intLcolorsNumber ? 0 : intLalternateColorIndex + 1);
				}

				objPball.intGcolor = intLcolorValueA[intLcolorIndex];
				objPball.intGalternateColor = intLcolorValueA[intLalternateColorIndex];
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@SuppressWarnings("unchecked") final public void setBalls() {

		// Create ball objects :
		this.objGballA = this.objGsiteswap.intGballsNumber > 0 ? new Ball[this.objGsiteswap.intGballsNumber] : null;
		for (int intLballIndex = 0; intLballIndex < this.objGsiteswap.intGballsNumber; ++intLballIndex) {
			this.objGballA[intLballIndex] = new Ball();
		}

		// Create ball trails :
		this.objGballTrailALA = this.objGsiteswap.intGballsNumber > 0 ? new ArrayList[this.objGsiteswap.intGballsNumber] : null;
		for (int intLballIndex = 0; intLballIndex < this.objGsiteswap.intGballsNumber; ++intLballIndex) {
			this.objGballTrailALA[intLballIndex] = new ArrayList<BallTrail>(Constants.bytS_ENGINE_TRAIL_LENGTH);
		}
	}

	final private boolean setCommandLineParameter(byte bytPparameterType, String strPparameterValue) {

		final String strLparameterValue = strPparameterValue.trim();
		switch (bytPparameterType) {
			case Constants.bytS_COMMAND_LINE_PARAMETER_FILE:
				this.strGcommandLineFileParameter = Strings.getTrimmedNullString(strLparameterValue);
				break;
			case Constants.bytS_COMMAND_LINE_PARAMETER_PATTERN:
				this.strGcommandLinePatternParameter = Strings.getTrimmedNullString(strLparameterValue);
				break;
			case Constants.bytS_COMMAND_LINE_PARAMETER_OCCURRENCE:
				try {
					final int intLoccurrence = Integer.valueOf(strLparameterValue);
					if (intLoccurrence > 0) {
						this.intGcommandLineOccurrenceParameter = intLoccurrence;
					}
				} catch (final Throwable objPthrowable) {
					return false;
				}
				break;
			case Constants.bytS_COMMAND_LINE_PARAMETER_LANGUAGE:
				this.strGcommandLineLanguageParameter = strLparameterValue.toLowerCase();
				break;

			case Constants.bytS_COMMAND_LINE_PARAMETER_EMBEDDED:
			case Constants.bytS_COMMAND_LINE_PARAMETER_LOG:
			case Constants.bytS_COMMAND_LINE_PARAMETER_CONSOLE:
			case Constants.bytS_COMMAND_LINE_PARAMETER_ERRORS:
			case Constants.bytS_COMMAND_LINE_PARAMETER_DEBUG:
			case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_LONG:
			case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_SHORT:
			case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_QUESTION_MARK:
			case Constants.bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS:
				boolean bolL = false;
				if (strLparameterValue.equalsIgnoreCase("true") || strLparameterValue.equalsIgnoreCase("yes") || strLparameterValue.equals("1")) {
					bolL = true;
				} else if (strLparameterValue.equalsIgnoreCase("false") || strLparameterValue.equalsIgnoreCase("no")
							|| strLparameterValue.equals("0")) {
					bolL = false;
				} else {
					return false;
				}
				switch (bytPparameterType) {
					case Constants.bytS_COMMAND_LINE_PARAMETER_EMBEDDED:
						Constants.bolS_UNCLASS_EMBEDDED = bolL;
						break;
					case Constants.bytS_COMMAND_LINE_PARAMETER_DEBUG:
						Constants.bolS_UNCLASS_DEBUG = bolL;
						break;
					case Constants.bytS_COMMAND_LINE_PARAMETER_ERRORS:
						Constants.bolS_UNCLASS_ERRORS = bolL;
						break;
					case Constants.bytS_COMMAND_LINE_PARAMETER_LOG:
						Constants.bolS_UNCLASS_LOG = bolL;
						break;
					case Constants.bytS_COMMAND_LINE_PARAMETER_CONSOLE:
						Constants.bolS_UNCLASS_CONSOLE = bolL;
						break;
					case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_LONG:
					case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_SHORT:
					case Constants.bytS_COMMAND_LINE_PARAMETER_HELP_QUESTION_MARK:
						this.bolGcommandLineHelp = bolL;
						break;
					case Constants.bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS:
						Constants.bolS_UNCLASS_CATCH_ALL_EXCEPTIONS = bolL;
				}
				break;
		}
		return true;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bolPselected
	 */
	final public void setControlSelected(byte bytPcontrolType, boolean bolPselected) {
		this.objGpatternsManager.setPatternBoolean(this.intGobjectIndex, bytPcontrolType, Constants.bytS_UNCLASS_CURRENT, bolPselected);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param strP
	 */
	final public void setControlString(byte bytPcontrolType, String strP) {
		this.objGpatternsManager.setPatternString(this.intGobjectIndex, bytPcontrolType, Constants.bytS_UNCLASS_CURRENT, strP);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPvalue
	 */
	final public void setControlValue(byte bytPcontrolType, byte bytPvalue) {
		this.objGpatternsManager.setPatternByte(this.intGobjectIndex, bytPcontrolType, Constants.bytS_UNCLASS_CURRENT, bytPvalue);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setCurrentStyle() {

		// Set style array sizes :
		final int[] intLstyleLengthA = new int[2];
		final int[] intLstyleLinesNumberA = new int[2];
		for (byte bytLinitialOrCurrent = Constants.bytS_UNCLASS_INITIAL; bytLinitialOrCurrent <= Constants.bytS_UNCLASS_CURRENT; ++bytLinitialOrCurrent) {
			intLstyleLengthA[bytLinitialOrCurrent] = this.objGstyleA[bytLinitialOrCurrent].bytGstyleA.length;
			intLstyleLinesNumberA[bytLinitialOrCurrent] = intLstyleLengthA[bytLinitialOrCurrent] / (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER);
		}

		// Set new style values :
		final byte[] bytLstyleA = new byte[2];

		// For each synchro throw (if there) :
		for (byte bytLsynchroThrowIndex = 0; bytLsynchroThrowIndex < 2; ++bytLsynchroThrowIndex) {

			final int intLstyleLineToModifyIndex =
													(int) ((this.lngGthrowsCount + this.objGsiteswap.bytGhighestThrow + bytLsynchroThrowIndex + 1) % intLstyleLinesNumberA[Constants.bytS_UNCLASS_CURRENT]);
			final boolean bolLinitialCatchNotChanged =
														this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																													* Constants.bytS_ENGINE_COORDONATES_NUMBER
																													* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])] == this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																																																			* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																																																			* ((intLstyleLineToModifyIndex - 2 + intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL]) % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])]
															&& this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																															* Constants.bytS_ENGINE_COORDONATES_NUMBER
																															* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																															+ 1] == this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																																* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																																* ((intLstyleLineToModifyIndex - 2 + intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL]) % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																																																+ 1];

			final boolean bolLinitialCatchWillNotChange =
															this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																														* Constants.bytS_ENGINE_COORDONATES_NUMBER
																														* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])] == this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																																																				* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																																																				* ((intLstyleLineToModifyIndex + 2) % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])]
																&& this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																																+ 1] == this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																																	* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																																	* ((intLstyleLineToModifyIndex + 2) % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																																																	+ 1];

			final boolean bolLinitialCatchIsInitialThrow =
															this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																														* Constants.bytS_ENGINE_COORDONATES_NUMBER
																														* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])] == this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																																																				* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																																																				* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																																																																				+ Constants.bytS_ENGINE_COORDONATES_NUMBER]
																&& this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																																+ 1] == this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																																	* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																																	* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																																																	+ Constants.bytS_ENGINE_COORDONATES_NUMBER
																																																	+ 1];

			final boolean bolLcurrentCatchInsideDefaultsBorder =
																	Math.abs(this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[2
																																		* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																		* intLstyleLineToModifyIndex]
																				- this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																				* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																				* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])]) <= (this.bolGdefaults
																																																																? this.bytGdefaults
																																																																: 0)
																		&& Math.abs(this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[2
																																				* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																				* intLstyleLineToModifyIndex
																																				+ 1]
																					- this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[2
																																					* Constants.bytS_ENGINE_COORDONATES_NUMBER
																																					* (intLstyleLineToModifyIndex % intLstyleLinesNumberA[Constants.bytS_UNCLASS_INITIAL])
																																					+ 1]) <= (this.bolGdefaults
																																												? this.bytGdefaults / 1.5F
																																												: 0F);

			// For each style value :
			for (byte bytLvalueIndex = 0; bytLvalueIndex < 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER; ++bytLvalueIndex) {

				if (bytLvalueIndex % Constants.bytS_ENGINE_COORDONATES_NUMBER >= Constants.bytS_ENGINE_COORDONATES_NUMBER - 1) {
					continue;
				}

				final int intLcurrentStyleValueIndex = 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER * intLstyleLineToModifyIndex + bytLvalueIndex;
				if (bytLvalueIndex >= Constants.bytS_ENGINE_COORDONATES_NUMBER
					|| this.bolGfX
					|| this.bolGrelativeDefaults
					|| !bolLinitialCatchNotChanged
					|| !this.bolGnoStyleThrowA[(intLstyleLineToModifyIndex + intLstyleLinesNumberA[Constants.bytS_UNCLASS_CURRENT] - 2)
												% intLstyleLinesNumberA[Constants.bytS_UNCLASS_CURRENT]] || !bolLcurrentCatchInsideDefaultsBorder) {

					// Set random values :
					bytLstyleA[Constants.bytS_UNCLASS_INITIAL] =
																	this.objGstyleA[Constants.bytS_UNCLASS_INITIAL].bytGstyleA[intLcurrentStyleValueIndex
																																% intLstyleLengthA[Constants.bytS_UNCLASS_INITIAL]];
					bytLstyleA[Constants.bytS_UNCLASS_CURRENT] =
																	this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex];
					final float fltLdefaults = this.bytGdefaults / (bytLvalueIndex % Constants.bytS_ENGINE_COORDONATES_NUMBER == 1 ? 1.5F : 1F);
					final byte bytLrandom = (byte) (Math.round((2F * (float) Math.random() - 1F) * fltLdefaults));

					if (this.bolGdefaults) {

						// Relative defaults :
						if (this.bolGrelativeDefaults) {
							final byte bytLmin =
													(byte) Math.min(bytLvalueIndex % Constants.bytS_ENGINE_COORDONATES_NUMBER == 0 ? -30 : -10,
																	bytLstyleA[Constants.bytS_UNCLASS_INITIAL]);
							final byte bytLmax =
													(byte) Math.max(bytLvalueIndex % Constants.bytS_ENGINE_COORDONATES_NUMBER == 0 ? 30 : 20,
																	bytLstyleA[Constants.bytS_UNCLASS_INITIAL]);
							this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex] =
																														(byte) Math.min(Math.max(	bytLstyleA[Constants.bytS_UNCLASS_CURRENT]
																																						+ bytLrandom,
																																					bytLmin),
																																		bytLmax);

							// Non-relative defaults :
						} else {
							if (Math.abs(bytLstyleA[Constants.bytS_UNCLASS_CURRENT] - bytLstyleA[Constants.bytS_UNCLASS_INITIAL]) > fltLdefaults) {
								this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex] +=
																															this.bytGdefaults
																																* Tools.getSign(bytLstyleA[Constants.bytS_UNCLASS_INITIAL]
																																				- bytLstyleA[Constants.bytS_UNCLASS_CURRENT]);
							} else {
								this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex] += bytLrandom;
								this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex] =
																															(byte) Math.max(Math.min(	this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex],
																																						bytLstyleA[Constants.bytS_UNCLASS_INITIAL]
																																							+ fltLdefaults),
																																			bytLstyleA[Constants.bytS_UNCLASS_INITIAL]
																																				- fltLdefaults);
							}
						}
					} else {

						// No defaults :
						if (Math.abs(bytLstyleA[Constants.bytS_UNCLASS_CURRENT] - bytLstyleA[Constants.bytS_UNCLASS_INITIAL]) > fltLdefaults
							&& !this.bolGfX) {
							this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex] +=
																														this.bytGdefaults
																															* Tools.getSign(bytLstyleA[Constants.bytS_UNCLASS_INITIAL]
																																			- bytLstyleA[Constants.bytS_UNCLASS_CURRENT]);
						} else {
							this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex] =
																														bytLstyleA[Constants.bytS_UNCLASS_INITIAL];
						}
					}
				}

				// Set next catch if the same :
				if (bytLvalueIndex < 2 && this.bolGnoStyleThrowA[intLstyleLineToModifyIndex] && bolLinitialCatchWillNotChange
					&& !this.bolGrelativeDefaults) {
					this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[(intLcurrentStyleValueIndex + 4 * Constants.bytS_ENGINE_COORDONATES_NUMBER)
																				% intLstyleLengthA[Constants.bytS_UNCLASS_CURRENT]] =
																																		this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex];
				}

				// Set throw if equal to catch :
				if (bytLvalueIndex >= Constants.bytS_ENGINE_COORDONATES_NUMBER && bolLinitialCatchIsInitialThrow) {
					this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex] =
																												this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex
																																											- Constants.bytS_ENGINE_COORDONATES_NUMBER];
				}

				// Copy the next style sequence value :
				if (intLstyleLengthA[Constants.bytS_UNCLASS_CURRENT] > intLstyleLengthA[Constants.bytS_UNCLASS_INITIAL]) {
					final int intLnextStyleValueIndex =
														(intLcurrentStyleValueIndex + Tools.getSmallestMultiple(intLstyleLengthA[Constants.bytS_UNCLASS_INITIAL],
																												this.objGsiteswap.intGthrowsNumber))
															% intLstyleLengthA[Constants.bytS_UNCLASS_CURRENT];
					this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLnextStyleValueIndex] =
																											this.objGstyleA[Constants.bytS_UNCLASS_CURRENT].bytGstyleA[intLcurrentStyleValueIndex];
				}
			}
			if (!this.objGsiteswap.bolGsynchro) {
				break;
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setDynamicRatios() {

		// Mirror :
		this.bolGmirror = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_MIRROR);

		// Light :
		this.bolGlight = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_LIGHT);
		this.bytGlight = this.bolGlight ? Constants.bytS_ENGINE_DAY : Constants.bytS_ENGINE_NIGHT;

		// Throws :
		this.bolGthrowsValues = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS);

		// Fx :
		this.bolGfX = this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_FX);

		// Defaults :
		this.bolGdefaults = !this.bolGfX && this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_DEFAULTS);
		this.bytGdefaults = this.getControlValue(Constants.bytS_BYTE_LOCAL_DEFAULTS);
		this.bolGrelativeDefaults = this.bolGdefaults && this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS);

		// Flash & Robot :
		this.bolGflash =
							this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_FLASH)
								&& !this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_FX);
		this.bolGrobot =
							this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_ROBOT)
								&& !this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_FX);
		final byte bytLstrobe = (byte) (2 * this.getControlValue(Constants.bytS_BYTE_LOCAL_STROBE));
		if (this.bolGflash || this.bolGrobot) {
			switch (bytLstrobe) {
				case 2 * Constants.bytS_BYTE_LOCAL_STROBE_EACH_CATCH:
					this.bytGstrobeRatio =
											(byte) (this.objGhandA[Constants.bytS_ENGINE_LEFT_SIDE].bytGcatchBallsVolume > 0
													|| this.objGhandA[Constants.bytS_ENGINE_RIGHT_SIDE].bytGcatchBallsVolume > 0 ? 1 : 0);
					break;
				case 2 * Constants.bytS_BYTE_LOCAL_STROBE_EACH_COUNT:
					this.bytGstrobeRatio = (byte) (this.bolGnewSiteswapThrow ? 1 : 0);
					break;
				default:
					if (this.bytGanimationStatus == Constants.bytS_STATE_ANIMATION_JUGGLING) {
						--this.bytGstrobeRatio;
					}
					if (this.bytGstrobeRatio + bytLstrobe < 0) {
						this.bytGstrobeRatio = (byte) 1;
					}
			}
		} else {
			this.bytGstrobeRatio = (byte) 1;
		}

		// Juggler visibility :
		this.bytGjugglerVisibility =
										this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_JUGGLER) ? Constants.bytS_BIT_VISIBILITY_PHANTOM
																											: Constants.bytS_BIT_VISIBILITY_NONE;
		if (this.bytGjugglerVisibility != Constants.bytS_BIT_VISIBILITY_NONE) {
			switch (this.getJugglerValue()) {
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL:
					this.bytGjugglerVisibility =
													Constants.bytS_BIT_VISIBILITY_HANDS | Constants.bytS_BIT_VISIBILITY_ARMS
														| Constants.bytS_BIT_VISIBILITY_HEAD;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS:
					this.bytGjugglerVisibility = Constants.bytS_BIT_VISIBILITY_ARMS | Constants.bytS_BIT_VISIBILITY_HEAD;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS:
					this.bytGjugglerVisibility = Constants.bytS_BIT_VISIBILITY_HANDS | Constants.bytS_BIT_VISIBILITY_HEAD;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD:
					this.bytGjugglerVisibility = Constants.bytS_BIT_VISIBILITY_HEAD;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS:
					this.bytGjugglerVisibility = Constants.bytS_BIT_VISIBILITY_HANDS | Constants.bytS_BIT_VISIBILITY_ARMS;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS:
					this.bytGjugglerVisibility = Constants.bytS_BIT_VISIBILITY_ARMS;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS:
					this.bytGjugglerVisibility = Constants.bytS_BIT_VISIBILITY_HANDS;
					break;
				case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM:
					this.bytGjugglerVisibility = Constants.bytS_BIT_VISIBILITY_PHANTOM;
					break;
			}
			if (this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)
				&& this.bytGjugglerVisibility != Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM) {
				this.bytGjugglerVisibility |= Constants.bytS_BIT_VISIBILITY_TRAIL;
			}
		}

		// Ball visibility :
		this.bytGballsVisibility = Constants.bytS_BIT_VISIBILITY_NONE;
		if (this.objGsiteswap.intGballsNumber > 0 && this.objGballColors.getColorsNumber() > 0) {
			if (this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_BALLS)) {
				this.bytGballsVisibility = Constants.bytS_BIT_VISIBILITY_BALLS;
				if (this.getControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL) == Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL) {
					this.bytGballsVisibility |= Constants.bytS_BIT_VISIBILITY_TRAIL;
				}
			}
			if (this.bolGfX) {
				this.bytGballsVisibility = Constants.bytS_BIT_VISIBILITY_BALLS | Constants.bytS_BIT_VISIBILITY_TRAIL;
			}
		}

		// Alternate color ratio :
		if (this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS)) {
			final byte bytLalternateColors = this.getControlValue(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS);
			if (bytLalternateColors != Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH) {
				final byte bytLinitialAlternateColorsRatio = this.bytGalternateColorsRatio;
				switch (bytLalternateColors) {

					case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_COUNT:
						if (this.bolGnewSiteswapThrow) {
							this.bytGalternateColorsRatio = (byte) (this.bytGalternateColorsRatio < 0 ? 0 : -1);
						}
						break;

					default:
						final byte bytLfrequency = (byte) (Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE - bytLalternateColors + 1);
						++this.bytGalternateColorsRatio;
						if (this.bytGalternateColorsRatio >= bytLfrequency) {
							this.bytGalternateColorsRatio = (byte) -bytLfrequency;
						}
				}
				if (this.getPatternBooleanValue(Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS) && (this.bytGalternateColorsRatio < 0)
					^ (bytLinitialAlternateColorsRatio < 0)) {
					this.initBallsColors();
				}
			}
		} else {
			this.bytGalternateColorsRatio = 0;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPvalue
	 * @param bytPinitialOrCurrent
	 */
	final public void setJugglerValue(int intPobjectIndex, byte bytPvalue, byte bytPinitialOrCurrent) {
		this.objGpatternsManager.setJugglerValue(intPobjectIndex, bytPinitialOrCurrent, bytPvalue);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPpatternsManager
	 */
	final public void setPatternsManager(PatternsManager objPpatternsManager) {
		this.objGpatternsManager = objPpatternsManager;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void start() {

		// Applet properties :
		this.initProgramProperties(false);
		this.initCommandLineParameters(false, new String[] { null });
		this.initFrames();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPthread
	 * @param objPthrowable
	 */
	@Override final public void uncaughtException(Thread objPthread, Throwable objPthrowable) {
		Tools.err(Strings.doConcat("Exception in ", objPthread.getName(), " occured:"));
		if (Constants.bolS_UNCLASS_CONSOLE && Constants.bolS_UNCLASS_DEBUG) {
			objPthrowable.printStackTrace();
		}
	}

	public boolean					bolGcoloredBorder;

	private boolean					bolGcommandLineHelp;

	public boolean					bolGdefaults;

	public boolean[]				bolGdisplayedThrowA;

	public boolean					bolGflash;

	public boolean					bolGforceExit;

	public boolean					bolGfX;

	public boolean					bolGlight;

	public boolean					bolGmirror;

	public boolean					bolGnewSiteswapThrow;

	public boolean[]				bolGnoStyleCatchA;

	public boolean[]				bolGnoStyleThrowA;

	public boolean					bolGplayScreenPlaySound;

	public boolean					bolGplayScreenShotSound;

	public boolean					bolGprogramTrusted;

	public boolean					bolGrelativeDefaults;

	public boolean					bolGrobot;

	public boolean					bolGscreenPlay;

	public boolean					bolGthrowsValues;

	public byte						bytGalternateColorsRatio;

	public byte						bytGanimationStatus;

	public byte						bytGballsVisibility;

	private byte					bytGbufferedSoundIndex;

	public byte						bytGdefaults;

	public byte						bytGjugglerVisibility;

	public byte						bytGlight;

	public byte						bytGprogramType;

	public byte[][]					bytGsiteswapValueAA;

	public byte						bytGstrobeRatio;

	public char						chrGpathSeparator;

	private float					fltGdwell;

	private float					fltGfluidity;

	public float[]					fltGheightA;

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPobjectA
	 */
	// final private static void log(Object... objPobjectA) {
	// if (JuggleMasterPro.bolS_UNCLASS_LOG) {
	// JuggleTools.log(JuggleStrings.doConcat(objPobjectA, objPobjectA != null ? ' ' : null, JuggleTools.getTraceString()));
	// }
	// }

	private float					fltGspeed;

	public int						intGactions;

	public int[]					intGballsNumberPerThrowA;

	public int						intGbaseY;

	public int						intGcommandLineOccurrenceParameter;

	public int						intGcurrentSequenceIndex;

	public int						intGcurrentThrowIndex;

	public int						intGdrawingSize;

	public int						intGdwellFramesIndexesDelta;

	public int						intGframesCountInitialForwardDelta;

	public int						intGframesNumberPerThrow;

	public int						intGhandDeltaX;

	public int						intGhandDeltaY;

	public int						intGobjectIndex;

	public int						intGtheoricalMilliSecondsFramesDelay;

	public float					intGthrowLastFrameIndex;

	public long						lngGframesCount;

	public long						lngGrealMilliSecondsFramesLastTime;

	public long						lngGrealMilliSecondsFramesNextTime;

	public long						lngGthrowsCount;

	public AnimatedGIFEncoder		objGanimatedGIFEncoder;

	public AudioClip[]				objGappletAudioClipA;

	public ExtendedClip[][]			objGapplicationClipAA;

	public Color[]					objGbackgroundColorA;

	public Ball[]					objGballA;

	public BallsColors				objGballColors;

	public ArrayList<BallTrail>[]	objGballTrailALA;

	public Body						objGbody;

	public Color[]					objGchestColorA;

	public DataJFrame				objGdataJFrame;

	public Hand[]					objGhandA;

	public AnimationJFrame			objGjuggleMasterProJFrame;

	public Color[]					objGjugglerColorA;

	public Thread					objGjugglingThread;

	public MediaTracker				objGmediaTracker;

	public PatternsManager			objGpatternsManager;

	public Siteswap					objGsiteswap;

	public Color[]					objGsiteswapColorA;

	public Style[]					objGstyleA;

	public String					strGcommandLineFileParameter;

	public String					strGcommandLineLanguageParameter;

	/**
	 * Method description
	 * 
	 * @see
	 */
	// @SuppressWarnings("unused") final private static void logIn() {
	// JuggleMasterPro.log("In  :");
	// }

	public String					strGcommandLinePatternParameter;

	public String					strS_CODE_BASE;

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)JuggleMasterPro.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
