package jugglemasterpro.engine.util;

import java.io.IOException;
import java.io.OutputStream;

import jugglemasterpro.util.Constants;

// ==============================================================================
// Adapted from Jef Poskanzer's Java port by way of J. M. G. Elliott.
// K Weiner 12/00

class LZWEncoder {

	static final int			HSIZE				= 5003;										// 80% occupancy

	static final int			intS_BITS			= 12;
	private static final int	intS_EOF			= -1;
	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	// Number of characters so far in this 'packet'
	int							a_count;
	// Define the storage for the packet accumulator
	byte[]						accum				= new byte[256];
	// block compression parameters -- after all codes are used up,
	// and compression rate changes, start over.
	boolean						bolGclear			= false;

	// GIFCOMPR.C - GIF Image compression routines
	//
	// Lempel-Ziv compression based on 'compress'. GIF modifications by
	// David Rowley (mgardi@watdcsu.waterloo.edu)

	// General DEFINEs

	int							ClearCode;

	int[]						codetab				= new int[LZWEncoder.HSIZE];

	// GIF Image compression - modified 'compress'
	//
	// Based on: compress.c - File compression ala IEEE Computer, June 1984.
	//
	// By Authors: Spencer W. Thomas (decvax!harpo!utah-cs!utah-gr!thomas)
	// Jim McKie (decvax!mcvax!jim)
	// Steve Davies (decvax!vax135!petsd!peora!srd)
	// Ken Turkowski (decvax!decwrl!turtlevax!ken)
	// James A. Woods (decvax!ihnp4!ames!jaw)
	// Joe Orost (decvax!vax135!petsd!joe)

	int							cur_accum			= 0;
	int							cur_bits			= 0;
	private int					curPixel;
	int							EOFCode;

	int							free_ent			= 0;											// first unused entry
	int							g_init_bits;

	int							hsize				= LZWEncoder.HSIZE;							// for dynamic table sizing

	int[]						htab				= new int[LZWEncoder.HSIZE];

	private final int			imgW, imgH;

	// Algorithm: use open addressing double hashing (no chaining) on the
	// prefix code / next character combination. We do a variant of Knuth's
	// algorithm D (vol. 3, sec. 6.4) along with G. Knott's relatively-prime
	// secondary probe. Here, the modular division first probe is gives way
	// to a faster exclusive-or manipulation. Also do block compression with
	// an adaptive reset, whereby the code table is cleared when the compression
	// ratio decreases, but after the table fills. The variable-length output
	// codes are re-sized at this point, and a special CLEAR code is generated
	// for the decompressor. Late addition: construct the table according to
	// file size for noticeable speed improvement on small files. Please direct
	// questions about this implementation to ames!jaw.

	private final int			initCodeSize;

	int							masks[]				=
														{ 0x0000, 0x0001, 0x0003, 0x0007, 0x000F, 0x001F, 0x003F, 0x007F, 0x00FF, 0x01FF, 0x03FF,
		0x07FF, 0x0FFF, 0x1FFF, 0x3FFF, 0x7FFF, 0xFFFF	};
	int							maxbits				= LZWEncoder.intS_BITS;						// user settable max # bits/code

	// output
	//
	// Output the given code.
	// Inputs:
	// code: A n_bits-bit integer. If == -1, then EOF. This assumes
	// that n_bits =< wordsize - 1.
	// Outputs:
	// Outputs code to the file.
	// Assumptions:
	// Chars are 8 bits long.
	// Algorithm:
	// Maintain a BITS character long buffer (so that 8 codes will
	// fit in it exactly). Use the VAX insv instruction to insert each
	// code in turn. When the buffer fills up empty it and start over.

	int							maxcode;															// maximum code, given n_bits
	int							maxmaxcode			= 1 << LZWEncoder.intS_BITS;					// should NEVER generate this code

	int							n_bits;															// number of bits/code

	private final byte[]		pixAry;

	private int					remaining;


	LZWEncoder(int width, int height, byte[] pixels, int color_depth) {
		this.imgW = width;
		this.imgH = height;
		this.pixAry = pixels;
		this.initCodeSize = Math.max(2, color_depth);
	}

	// Add a character to the end of the current packet, and if it is 254
	// characters, flush the packet to disk.
	void char_out(byte c, OutputStream outs) throws IOException {
		this.accum[this.a_count++] = c;
		if (this.a_count >= 254) {
			this.flush_char(outs);
		}
	}

	// Clear out the hash table

	// table clear for block compress
	void cl_block(OutputStream outs) throws IOException {
		this.cl_hash(this.hsize);
		this.free_ent = this.ClearCode + 2;
		this.bolGclear = true;

		this.output(this.ClearCode, outs);
	}

	// reset code table
	void cl_hash(int hsize1) {
		for (int i = 0; i < hsize1; ++i) {
			this.htab[i] = -1;
		}
	}

	void compress(int init_bits, OutputStream outs) throws IOException {
		int fcode;
		int i /* = 0 */;
		int c;
		int ent;
		int disp;
		int hsize_reg;
		int hshift;

		// Set up the globals: g_init_bits - initial number of bits
		this.g_init_bits = init_bits;

		// Set up the necessary values
		this.bolGclear = false;
		this.n_bits = this.g_init_bits;
		this.maxcode = this.MAXCODE(this.n_bits);

		this.ClearCode = 1 << (init_bits - 1);
		this.EOFCode = this.ClearCode + 1;
		this.free_ent = this.ClearCode + 2;

		this.a_count = 0; // clear packet

		ent = this.nextPixel();

		hshift = 0;
		for (fcode = this.hsize; fcode < 65536; fcode *= 2) {
			++hshift;
		}
		hshift = 8 - hshift; // set hash code range bound

		hsize_reg = this.hsize;
		this.cl_hash(hsize_reg); // clear hash table

		this.output(this.ClearCode, outs);

		outer_loop: while ((c = this.nextPixel()) != LZWEncoder.intS_EOF) {
			fcode = (c << this.maxbits) + ent;
			i = (c << hshift) ^ ent; // xor hashing

			if (this.htab[i] == fcode) {
				ent = this.codetab[i];
				continue;
			} else if (this.htab[i] >= 0) // non-empty slot
			{
				disp = hsize_reg - i; // secondary hash (after G. Knott)
				if (i == 0) {
					disp = 1;
				}
				do {
					if ((i -= disp) < 0) {
						i += hsize_reg;
					}

					if (this.htab[i] == fcode) {
						ent = this.codetab[i];
						continue outer_loop;
					}
				} while (this.htab[i] >= 0);
			}
			this.output(ent, outs);
			ent = c;
			if (this.free_ent < this.maxmaxcode) {
				this.codetab[i] = this.free_ent++; // code -> hashtable
				this.htab[i] = fcode;
			} else {
				this.cl_block(outs);
			}
		}
		// Put out the final code.
		this.output(ent, outs);
		this.output(this.EOFCode, outs);
	}


	void encode(OutputStream os) throws IOException {
		os.write(this.initCodeSize); // write "initial code size" byte

		this.remaining = this.imgW * this.imgH; // reset navigation variables
		this.curPixel = 0;

		this.compress(this.initCodeSize + 1, os); // compress and write the pixel data

		os.write(0); // write block terminator
	}

	// Flush the packet to disk, and reset the accumulator
	void flush_char(OutputStream outs) throws IOException {
		if (this.a_count > 0) {
			outs.write(this.a_count);
			outs.write(this.accum, 0, this.a_count);
			this.a_count = 0;
		}
	}

	final int MAXCODE(int n_bits1) {
		return (1 << n_bits1) - 1;
	}


	// Return the next pixel from the image

	private int nextPixel() {
		if (this.remaining == 0) {
			return LZWEncoder.intS_EOF;
		}

		--this.remaining;

		final byte pix = this.pixAry[this.curPixel++];

		return pix & 0xff;
	}

	void output(int code, OutputStream outs) throws IOException {
		this.cur_accum &= this.masks[this.cur_bits];

		if (this.cur_bits > 0) {
			this.cur_accum |= (code << this.cur_bits);
		} else {
			this.cur_accum = code;
		}

		this.cur_bits += this.n_bits;

		while (this.cur_bits >= 8) {
			this.char_out((byte) (this.cur_accum & 0xff), outs);
			this.cur_accum >>= 8;
			this.cur_bits -= 8;
		}

		// If the next entry is going to be too big for the code size,
		// then increase it, if possible.
		if (this.free_ent > this.maxcode || this.bolGclear) {
			if (this.bolGclear) {
				this.maxcode = this.MAXCODE(this.n_bits = this.g_init_bits);
				this.bolGclear = false;
			} else {
				++this.n_bits;
				if (this.n_bits == this.maxbits) {
					this.maxcode = this.maxmaxcode;
				} else {
					this.maxcode = this.MAXCODE(this.n_bits);
				}
			}
		}

		if (code == this.EOFCode) {
			// At EOF, write the rest of the buffer.
			while (this.cur_bits > 0) {
				this.char_out((byte) (this.cur_accum & 0xff), outs);
				this.cur_accum >>= 8;
				this.cur_bits -= 8;
			}

			this.flush_char(outs);
		}
	}
}
