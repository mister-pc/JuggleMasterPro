package jugglemasterpro.engine.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jugglemasterpro.util.Constants;

/**
 * Class AnimatedGIFEncoder - Encodes a GIF file consisting of one or
 * more frames.
 * 
 * <pre>
 * Example:
 *    AnimatedGIFEncoder e = new AnimatedGIFEncoder();
 *    e.start(outputFileName);
 *    e.setDelay(1000);   // 1 frame per sec
 *    e.addFrame(image1);
 *    e.addFrame(image2);
 *    e.finish();
 * </pre>
 * 
 * No copyright asserted on the source code of this class. May be used
 * for any purpose, however, refer to the Unisys LZW patent for restrictions
 * on use of the associated LZWEncoder class. Please forward any corrections
 * to kweiner@fmsware.com.
 * 
 * @author Kevin Weiner, FM Software
 * @version 1.03 November 2003
 */

public class AnimatedGIFEncoder {

	@SuppressWarnings("unused")
	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	final public static int		intS_COLORS_TABLE_SIZE		= 7;				// color table size (bits-1)
	protected boolean[]			bolGactivePaletteA			= new boolean[256]; // active palette entries
	protected boolean			bolGcloseStream				= false;			// close stream when finished
	protected boolean			bolGfirstFrame				= true;
	protected boolean			bolGsizeSet					= false;			// if false, get size from first frame
	protected boolean			bolGstarted					= false;			// ready to output frames
	protected byte[]			bytGindexedPixelA;								// converted frame indexed to palette
	protected byte[]			bytGpixelA;									// BGR byte array from frame
	protected byte[]			bytGrGBcolorA;									// RGB palette
	protected int				dispose						= -1;				// disposal code (-1 = use default)
	protected int				intGcolorDepth;								// number of bit planes
	protected int				intGdefaultSampleInterval	= 10;				// default sample interval for quantizer
	protected int				intGheight;
	protected int				intGnextFrameDelay			= 0;				// frame delay (hundredths)
	protected int				intGrepeatCount				= -1;				// no repeat
	protected int				intGtransparentColorIndex;						// transparent index in color table
	protected int				intGwidth;										// image size
	protected BufferedImage		imgGbuffer;								// current frame
	protected FileOutputStream	objGfileOutputStream;
	protected OutputStream		objGoutputStream;

	protected Color				objGtransparentColor		= null;			// transparent color if given

	/**
	 * Adds next GIF frame. The frame is not written immediately, but is
	 * actually deferred until the next frame is received so that timing
	 * data can be inserted. Invoking <code>finish()</code> flushes all
	 * frames. If <code>setSize</code> was not invoked, the size of the
	 * first image is used for all subsequent frames.
	 * 
	 * @param im
	 *            BufferedImage containing frame to write.
	 * @return true if successful.
	 */
	public boolean addFrame(BufferedImage imgPbuffer) {
		if ((imgPbuffer == null) || !this.bolGstarted) {
			return false;
		}
		try {
			if (!this.bolGsizeSet) {
				// use first frame's size
				this.setSize(imgPbuffer.getWidth(), imgPbuffer.getHeight());
			}
			this.imgGbuffer = imgPbuffer;
			this.getImagePixels(); // convert to correct format if necessary
			this.analyzePixels(); // build color table & map pixels
			if (this.bolGfirstFrame) {
				this.writeLSD(); // logical screen descriptor
				this.writePalette(); // global color table
				if (this.intGrepeatCount >= 0) {
					// use NS app extension to indicate reps
					this.writeNetscapeExtension();
				}
			}
			this.writeGraphicControlExtension(); // write graphic control extension
			this.writeImageDesc(); // image descriptor
			if (!this.bolGfirstFrame) {
				this.writePalette(); // local color table
			}
			this.writePixels(); // encode and write pixel data
			this.bolGfirstFrame = false;
		} catch (final Throwable objPthrowable) {
			return false;
		}

		return true;
	}

	/**
	 * Analyzes image colors and creates color map.
	 */
	protected void analyzePixels() {
		final int intLlength = this.bytGpixelA.length;
		final int intLpixelsNumber = intLlength / 3;
		this.bytGindexedPixelA = new byte[intLpixelsNumber];
		final NeuralNetworkQuantization objLneuralNetworkQuantization =
																		new NeuralNetworkQuantization(	this.bytGpixelA,
																										intLlength,
																										this.intGdefaultSampleInterval);
		// initialize quantizer
		this.bytGrGBcolorA = objLneuralNetworkQuantization.process(); // create reduced palette
		// convert map from BGR to RGB
		for (int intLcolorIndex = 0; intLcolorIndex < this.bytGrGBcolorA.length; intLcolorIndex += 3) {
			final byte bytLtemporaryColorValue = this.bytGrGBcolorA[intLcolorIndex];
			this.bytGrGBcolorA[intLcolorIndex] = this.bytGrGBcolorA[intLcolorIndex + 2];
			this.bytGrGBcolorA[intLcolorIndex + 2] = bytLtemporaryColorValue;
			this.bolGactivePaletteA[intLcolorIndex / 3] = false;
		}
		// map image pixels to new palette
		int k = 0;
		for (int intLpixelIndex = 0; intLpixelIndex < intLpixelsNumber; intLpixelIndex++) {
			final int index =
								objLneuralNetworkQuantization.map(	this.bytGpixelA[k++] & 0xff,
																	this.bytGpixelA[k++] & 0xff,
																	this.bytGpixelA[k++] & 0xff);
			this.bolGactivePaletteA[index] = true;
			this.bytGindexedPixelA[intLpixelIndex] = (byte) index;
		}
		this.bytGpixelA = null;
		this.intGcolorDepth = 8;
		// get closest match to transparent color if specified
		if (this.objGtransparentColor != null) {
			this.intGtransparentColorIndex = this.findClosestColorIndex(this.objGtransparentColor);
		}
	}

	/**
	 * Returns index of palette color closest to c
	 */
	protected int findClosestColorIndex(Color objPoriginalColor) {
		if (this.bytGrGBcolorA == null) {
			return Constants.bytS_UNCLASS_NO_VALUE;
		}
		final int r = objPoriginalColor.getRed();
		final int g = objPoriginalColor.getGreen();
		final int b = objPoriginalColor.getBlue();
		int minpos = 0;
		int dmin = 256 * 256 * 256;
		for (int i = 0; i < this.bytGrGBcolorA.length; i++) {
			final int dr = r - (this.bytGrGBcolorA[i++] & 0xff);
			final int dg = g - (this.bytGrGBcolorA[i++] & 0xff);
			final int db = b - (this.bytGrGBcolorA[i] & 0xff);
			final int d = dr * dr + dg * dg + db * db;
			final int index = i / 3;
			if (this.bolGactivePaletteA[index] && (d < dmin)) {
				dmin = d;
				minpos = index;
			}
		}
		return minpos;
	}

	/**
	 * Flushes any pending data and closes output file.
	 * If writing to an OutputStream, the stream is not
	 * closed.
	 */
	public boolean finish() {
		if (!this.bolGstarted) {
			return false;
		}
		boolean bolLfinished = true;
		this.bolGstarted = false;
		try {
			this.objGoutputStream.write(0x3b); // GIF trailer
			this.objGoutputStream.flush();
			if (this.bolGcloseStream) {
				this.objGoutputStream.close();
			}
		} catch (final Throwable objPthrowable) {
			bolLfinished = false;
		}

		// reset for subsequent use
		this.intGtransparentColorIndex = 0;
		this.objGoutputStream = null;
		this.imgGbuffer = null;
		this.bytGpixelA = null;
		this.bytGindexedPixelA = null;
		this.bytGrGBcolorA = null;
		this.bolGcloseStream = false;
		this.bolGfirstFrame = true;

		return bolLfinished;
	}

	/**
	 * Extracts image pixels into byte array "pixels"
	 */
	protected void getImagePixels() {
		if ((this.imgGbuffer.getWidth() != this.intGwidth) || (this.imgGbuffer.getHeight() != this.intGheight)
			|| (this.imgGbuffer.getType() != BufferedImage.TYPE_3BYTE_BGR)) {
			// create new image with right size/format
			final BufferedImage imgLbuffer = new BufferedImage(this.intGwidth, this.intGheight, BufferedImage.TYPE_3BYTE_BGR);
			final Graphics2D g = imgLbuffer.createGraphics();
			g.drawImage(this.imgGbuffer, 0, 0, null);
			this.imgGbuffer = imgLbuffer;
		}
		this.bytGpixelA = ((DataBufferByte) this.imgGbuffer.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Sets the delay time between each frame, or changes it
	 * for subsequent frames (applies to last frame added).
	 * 
	 * @param ms
	 *            int intGnextFrameDelay time in milliseconds
	 */
	public void setDelay(long lngPmilliSeconds) {
		this.intGnextFrameDelay = Math.round(lngPmilliSeconds / 10.0f);
	}

	/**
	 * Sets the GIF frame disposal code for the last added frame
	 * and any subsequent frames. Default is 0 if no transparent
	 * color has been set, otherwise 2.
	 * 
	 * @param code
	 *            int disposal code.
	 */
	public void setDispose(int intPdisposalCode) {
		if (intPdisposalCode >= 0) {
			this.dispose = intPdisposalCode;
		}
	}

	/**
	 * Sets frame rate in frames per second. Equivalent to <code>setDelay(1000/fps)</code>.
	 * 
	 * @param fps
	 *            float frame rate (frames per second)
	 */
	public void setFrameRate(float fltPframesPerSecond) {
		if (fltPframesPerSecond != 0f) {
			this.intGnextFrameDelay = Math.round(100f / fltPframesPerSecond);
		}
	}

	/**
	 * Sets quality of color quantization (conversion of images
	 * to the maximum 256 colors allowed by the GIF specification).
	 * Lower values (minimum = 1) produce better colors, but slow
	 * processing significantly. 10 is the default, and produces
	 * good color mapping at reasonable speeds. Values greater
	 * than 20 do not yield significant improvements in speed.
	 * 
	 * @param quality
	 *            int greater than 0.
	 * @return
	 */
	final public void setQuality(int intPquality) {
		this.intGdefaultSampleInterval = Math.max(intPquality, 1);
	}

	/**
	 * Sets the number of times the set of GIF frames
	 * should be played. Default is 1; 0 means play
	 * indefinitely. Must be invoked before the first
	 * image is added.
	 * 
	 * @param iter
	 *            int number of iterations.
	 * @return
	 */
	final public void setRepeat(int intPrepeatCount) {
		this.intGrepeatCount = intPrepeatCount;
	}

	/**
	 * Sets the GIF frame size. The default size is the
	 * size of the first frame added if this method is
	 * not invoked.
	 * 
	 * @param w
	 *            int frame width.
	 * @param h
	 *            int frame width.
	 */
	public void setSize(int intPwidth, int intPheight) {
		if (this.bolGstarted && !this.bolGfirstFrame) {
			return;
		}
		this.intGwidth = intPwidth;
		this.intGheight = intPheight;
		if (this.intGwidth < 1) {
			this.intGwidth = 320;
		}
		if (this.intGheight < 1) {
			this.intGheight = 240;
		}
		this.bolGsizeSet = true;
	}

	/**
	 * Sets the transparent color for the last added frame
	 * and any subsequent frames.
	 * Since all colors are subject to modification
	 * in the quantization process, the color in the final
	 * palette for each frame closest to the given color
	 * becomes the transparent color for that frame.
	 * May be set to null to indicate no transparent color.
	 * 
	 * @param c
	 *            Color to be treated as transparent on display.
	 */
	public void setTransparentColor(Color objPtransparentColor) {
		this.objGtransparentColor = objPtransparentColor;
	}

	/**
	 * Initiates GIF file creation on the given stream. The stream
	 * is not closed automatically.
	 * 
	 * @param os
	 *            OutputStream on which GIF images are written.
	 * @return false if initial write failed.
	 */
	public boolean start(OutputStream objPoutputStream) {
		if (objPoutputStream == null) {
			return false;
		}
		this.bolGstarted = true;
		this.bolGcloseStream = false;
		this.objGoutputStream = objPoutputStream;
		try {
			this.writeString("GIF89a"); // header
		} catch (final Throwable objPthrowable) {
			this.bolGstarted = false;
		}
		return this.bolGstarted;
	}

	/**
	 * Initiates writing of a GIF file with the specified name.
	 * 
	 * @param file
	 *            String containing output file name.
	 * @return false if open or initial write failed.
	 */
	final public boolean start(String strPfile) {
		boolean ok = true;
		try {
			this.objGoutputStream = new BufferedOutputStream(new FileOutputStream(strPfile));
			ok = this.start(this.objGoutputStream);
			this.bolGcloseStream = true;
		} catch (final IOException e) {
			ok = false;
		}
		return this.bolGstarted = ok;
	}

	/**
	 * Writes Graphic Control Extension
	 */
	final private void writeGraphicControlExtension() throws IOException {
		this.objGoutputStream.write(0x21); // extension introducer
		this.objGoutputStream.write(0xf9); // GCE label
		this.objGoutputStream.write(4); // data block size
		int intLtransparencyFlag, disp;
		if (this.objGtransparentColor == null) {
			intLtransparencyFlag = 0;
			disp = 0; // dispose = no action
		} else {
			intLtransparencyFlag = 1;
			disp = 2; // force clear if using transparent color
		}
		if (this.dispose >= 0) {
			disp = this.dispose & 7; // user override
		}
		disp <<= 2;

		// packed fields
		this.objGoutputStream.write(0 | // 1:3 reserved
									disp | // 4:6 disposal
									0 | // 7 user input - 0 = none
									intLtransparencyFlag); // 8 transparency flag

		this.writeShort(this.intGnextFrameDelay); // delay x 1/100 sec
		this.objGoutputStream.write(this.intGtransparentColorIndex); // transparent color index
		this.objGoutputStream.write(0); // block terminator
	}

	/**
	 * Writes Image Descriptor
	 */
	protected void writeImageDesc() throws IOException {
		this.objGoutputStream.write(0x2c); // image separator
		this.writeShort(0); // image position x,y = 0,0
		this.writeShort(0);
		this.writeShort(this.intGwidth); // image size
		this.writeShort(this.intGheight);
		// packed fields
		if (this.bolGfirstFrame) {
			// no LCT - GCT is used for first (or only) frame
			this.objGoutputStream.write(0);
		} else {
			// specify normal LCT
			this.objGoutputStream.write(0x80 | // 1 local color table 1=yes
										0 | // 2 interlace - 0=no
										0 | // 3 sorted - 0=no
										0 | // 4-5 reserved
										AnimatedGIFEncoder.intS_COLORS_TABLE_SIZE); // 6-8 size of color table
		}
	}

	/**
	 * Writes Logical Screen Descriptor
	 */
	protected void writeLSD() throws IOException {
		// logical screen size
		this.writeShort(this.intGwidth);
		this.writeShort(this.intGheight);
		// packed fields
		this.objGoutputStream.write((0x80 | // 1 : global color table flag = 1 (gct used)
		0x70 | // 2-4 : color resolution = 7
		0x00 | // 5 : gct sort flag = 0
		AnimatedGIFEncoder.intS_COLORS_TABLE_SIZE)); // 6-8 : gct size

		this.objGoutputStream.write(0); // background color index
		this.objGoutputStream.write(0); // pixel aspect ratio - assume 1:1
	}

	/**
	 * Writes Netscape application extension to define
	 * repeat count.
	 */
	protected void writeNetscapeExtension() throws IOException {
		this.objGoutputStream.write(0x21); // extension introducer
		this.objGoutputStream.write(0xff); // app extension label
		this.objGoutputStream.write(11); // block size
		this.writeString("NETSCAPE2.0"); // app id + auth code
		this.objGoutputStream.write(3); // sub-block size
		this.objGoutputStream.write(1); // loop sub-block id
		this.writeShort(this.intGrepeatCount); // loop count (extra iterations, 0=repeat forever)
		this.objGoutputStream.write(0); // block terminator
	}

	/**
	 * Writes color table
	 */
	protected void writePalette() throws IOException {
		this.objGoutputStream.write(this.bytGrGBcolorA, 0, this.bytGrGBcolorA.length);
		final int n = (3 * 256) - this.bytGrGBcolorA.length;
		for (int i = 0; i < n; i++) {
			this.objGoutputStream.write(0);
		}
	}

	/**
	 * Encodes and writes pixel data
	 */
	protected void writePixels() throws IOException {
		final LZWEncoder objLlZWEncoder = new LZWEncoder(this.intGwidth, this.intGheight, this.bytGindexedPixelA, this.intGcolorDepth);
		objLlZWEncoder.encode(this.objGoutputStream);
	}

	/**
	 * Write 16-bit value to output stream, LSB first
	 */
	protected void writeShort(int value) throws IOException {
		this.objGoutputStream.write(value & 0xff);
		this.objGoutputStream.write((value >> 8) & 0xff);
	}

	/**
	 * Writes string to output stream
	 */
	protected void writeString(String strP) throws IOException {
		final char[] chrLcharA = strP.toCharArray();
		for (final char chrLindexed : chrLcharA) {
			this.objGoutputStream.write((byte) chrLindexed);
		}
	}
}
