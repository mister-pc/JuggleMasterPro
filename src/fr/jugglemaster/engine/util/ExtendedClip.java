package fr.jugglemaster.engine.util;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import fr.jugglemaster.engine.JuggleMasterPro;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

final public class ExtendedClip {

	public ExtendedClip(JuggleMasterPro objPjuggleMasterPro, byte bytPsoundFileIndex) {

		this.bytGsoundFileIndex = bytPsoundFileIndex;
		try {
			final AudioInputStream objLaudioInputStream =
															AudioSystem.getAudioInputStream(new File(Strings.doConcat(	objPjuggleMasterPro.strS_CODE_BASE,
																														Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_SOUNDS],
																														File.separatorChar,
																														Constants.strS_FILE_SOUND_NAME_A[bytPsoundFileIndex])));
			final AudioFormat objLaudioFormat = objLaudioInputStream.getFormat();
			final DataLine.Info objLdataLineInfo =
													new DataLine.Info(Clip.class, objLaudioFormat, (int) objLaudioInputStream.getFrameLength()
																									* objLaudioFormat.getFrameSize());
			this.objGclip = (Clip) AudioSystem.getLine(objLdataLineInfo);
			this.objGclip.open(objLaudioInputStream);
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while initializing sound : ", Constants.strS_FILE_SOUND_NAME_A[bytPsoundFileIndex]);
			this.objGclip = null;
		}
	}

	final public float getVolume(int intPvolumePercentage) throws Throwable {

		if (this.objGclip != null && this.objGclip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			final FloatControl objLvolumeFloatControl = (FloatControl) this.objGclip.getControl(FloatControl.Type.MASTER_GAIN);
			final float fltLrange = (objLvolumeFloatControl.getMaximum() - objLvolumeFloatControl.getMinimum()) * 1.2F;
			return Math.min(fltLrange * Math.max(0, intPvolumePercentage) / 100.0F + objLvolumeFloatControl.getMinimum(),
							objLvolumeFloatControl.getMaximum());
		}
		throw new Throwable();
	}

	final public boolean play() {

		if (this.objGclip != null) {
			try {
				this.objGclip.stop();
				this.objGclip.setFramePosition(0);
				this.objGclip.start();
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while playing sound : ", Constants.strS_FILE_SOUND_NAME_A[this.bytGsoundFileIndex]);
				this.objGclip = null;
			}
		}
		return this.objGclip != null;
	}

	final public void setBalance(int intPbalancePercentage) {

		if (this.objGclip != null && this.objGclip.isControlSupported(FloatControl.Type.PAN)) {
			try {
				final FloatControl objLbalanceFloatControl = (FloatControl) this.objGclip.getControl(FloatControl.Type.PAN);
				objLbalanceFloatControl.setValue(Math.max(-100, Math.min(intPbalancePercentage, 100)) / 100.0F);
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while setting sound balance : ", Constants.strS_FILE_SOUND_NAME_A[this.bytGsoundFileIndex]);
			}
		}
	}

	final public void setVolume(int intPvolumePercentage) {

		try {
			final float fltLvolume = this.getVolume(intPvolumePercentage);
			final FloatControl objLvolumeFloatControl = (FloatControl) this.objGclip.getControl(FloatControl.Type.MASTER_GAIN);
			objLvolumeFloatControl.setValue(fltLvolume);
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while setting sound volume : ", Constants.strS_FILE_SOUND_NAME_A[this.bytGsoundFileIndex]);
		}
	}

	final private byte	bytGsoundFileIndex;

	private Clip		objGclip;
}
