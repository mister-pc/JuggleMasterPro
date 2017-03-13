package jugglemasterpro.engine.util;

import jugglemasterpro.util.Constants;

/*
 * NeuQuant Neural-Net Quantization Algorithm
 * ------------------------------------------
 * Copyright (c) 1994 Anthony Dekker
 * NEUQUANT Neural-Net quantization algorithm by Anthony Dekker, 1994.
 * See "Kohonen neural networks for optimal colour quantization"
 * in "Network: Computation in Neural Systems" Vol. 5 (1994) pp 351-367.
 * for a discussion of the algorithm.
 * Any party obtaining a copy of these files from the author, directly or
 * indirectly, is granted, free of charge, a full and unrestricted irrevocable,
 * world-wide, paid up, royalty-free, nonexclusive right and license to deal
 * in this software and documentation files (the "Software"), including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons who receive
 * copies from any such party to do so, with the only requirement being
 * that this copyright notice remain intact.
 */

public class NeuralNetworkQuantization {

	final protected static int	intS_ALPHA_BIAS_SHIFT		= 10;
	final protected static int	intS_ALPHA_RAD_BIAS;

	final protected static int	intS_ALPHA_RADB_SHIFT;
	final protected static int	intS_BETA;
	protected static final int	intS_BETA_GAMMA;
	protected static final int	intS_BETA_SHIFT				= 10;
	protected static final int	intS_BIAS;
	protected static final int	intS_BIAS_SHIFT				= 16;
	protected static final int	intS_COLORS_NUMBER			= 256;
	protected static final int	intS_COLORS_VALUES_BIAS		= 4;

	protected static final int	intS_GAMMA;
	protected static final int	intS_GAMMA_SHIFT			= 10;
	protected static final int	intS_INIT_ALPHA;
	protected static final int	intS_INIT_RAD;
	protected static final int	intS_INIT_RADIUS;
	protected static final int	intS_LEARNING_CYCLES_NUMBER	= 100;
	protected static final int	intS_MAX_NET_POS;
	protected static final int	intS_MIN_PICTURE_BYTES;

	/* four primes near 500 - assume no image has a length so large */
	/* that it is divisible by all four primes */
	protected static final int	intS_PRIME_1				= 499;
	protected static final int	intS_PRIME_2				= 491;
	protected static final int	intS_PRIME_3				= 487;
	protected static final int	intS_PRIME_4				= 503;
	protected static final int	intS_RAD_BIAS;
	protected static final int	intS_RAD_BIAS_SHIFT			= 8;
	protected static final int	intS_RADIUS_BIAS;
	protected static final int	intS_RADIUS_BIAS_SHIFT		= 6;
	protected static final int	intS_RADIUS_DEC				= 30;
	@SuppressWarnings("unused")
	final private static long	serialVersionUID			= Constants.lngS_ENGINE_VERSION_NUMBER;

	protected byte[]			bytGpictureA;
	protected int				intGalphaDec;
	protected int[]				intGbiasA					= new int[NeuralNetworkQuantization.intS_COLORS_NUMBER];
	protected int[]				intGfreqA					= new int[NeuralNetworkQuantization.intS_COLORS_NUMBER];
	protected int				intGlengthCount;																		// H × W × 3
	protected int[]				intGnetIndexA				= new int[NeuralNetworkQuantization.intS_COLORS_NUMBER];
	protected int[][]			intGnetworkAA;
	protected int[]				intGradPowerA				= new int[NeuralNetworkQuantization.intS_INIT_RAD];
	protected int				intGsamplingFactor;

	static {
		intS_BIAS = (1 << NeuralNetworkQuantization.intS_BIAS_SHIFT);
		intS_RAD_BIAS = (1 << NeuralNetworkQuantization.intS_RAD_BIAS_SHIFT);
		intS_RADIUS_BIAS = (1 << NeuralNetworkQuantization.intS_RADIUS_BIAS_SHIFT);
		intS_INIT_RAD = (NeuralNetworkQuantization.intS_COLORS_NUMBER >> 3);
		intS_INIT_RADIUS = (NeuralNetworkQuantization.intS_INIT_RAD * NeuralNetworkQuantization.intS_RADIUS_BIAS);
		intS_ALPHA_RADB_SHIFT = (NeuralNetworkQuantization.intS_ALPHA_BIAS_SHIFT + NeuralNetworkQuantization.intS_RAD_BIAS_SHIFT);
		intS_ALPHA_RAD_BIAS = (1 << NeuralNetworkQuantization.intS_ALPHA_RADB_SHIFT);
		intS_INIT_ALPHA = (1 << NeuralNetworkQuantization.intS_ALPHA_BIAS_SHIFT);
		intS_BETA = (NeuralNetworkQuantization.intS_BIAS >> NeuralNetworkQuantization.intS_BETA_SHIFT); // == 1/1024
		intS_BETA_GAMMA =
							(NeuralNetworkQuantization.intS_BIAS << (NeuralNetworkQuantization.intS_GAMMA_SHIFT - NeuralNetworkQuantization.intS_BETA_SHIFT));
		intS_GAMMA = (1 << NeuralNetworkQuantization.intS_GAMMA_SHIFT); // == 1024
		intS_MIN_PICTURE_BYTES = (3 * NeuralNetworkQuantization.intS_PRIME_4);
		intS_MAX_NET_POS = (NeuralNetworkQuantization.intS_COLORS_NUMBER - 1);
	}

	/*
	 * Initialise network in range (0,0,0) to (255,255,255) and set parameters
	 * -----------------------------------------------------------------------
	 */
	public NeuralNetworkQuantization(byte[] bytPpictureA, int intPlength, int intPsamplingFactor) {

		int i;
		int[] p;

		this.bytGpictureA = bytPpictureA;
		this.intGlengthCount = intPlength;
		this.intGsamplingFactor = intPsamplingFactor;

		this.intGnetworkAA = new int[NeuralNetworkQuantization.intS_COLORS_NUMBER][];
		for (i = 0; i < NeuralNetworkQuantization.intS_COLORS_NUMBER; i++) {
			this.intGnetworkAA[i] = new int[4];
			p = this.intGnetworkAA[i];
			p[0] = p[1] = p[2] = (i << (NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS + 8)) / NeuralNetworkQuantization.intS_COLORS_NUMBER;
			this.intGfreqA[i] = NeuralNetworkQuantization.intS_BIAS / NeuralNetworkQuantization.intS_COLORS_NUMBER;
			this.intGbiasA[i] = 0;
		}
	}

	/*
	 * Move adjacent neurons by precomputed alpha*(1-((i-j)^2/[r]^2)) in radpower[|i-j|]
	 * ---------------------------------------------------------------------------------
	 */
	protected void alterneigh(int rad, int i, int b, int g, int r) {

		int j, k, lo, hi, a, m;
		int[] p;

		lo = i - rad;
		if (lo < -1) {
			lo = -1;
		}
		hi = i + rad;
		if (hi > NeuralNetworkQuantization.intS_COLORS_NUMBER) {
			hi = NeuralNetworkQuantization.intS_COLORS_NUMBER;
		}

		j = i + 1;
		k = i - 1;
		m = 1;
		while ((j < hi) || (k > lo)) {
			a = this.intGradPowerA[m++];
			if (j < hi) {
				p = this.intGnetworkAA[j++];
				try {
					p[0] -= (a * (p[0] - b)) / NeuralNetworkQuantization.intS_ALPHA_RAD_BIAS;
					p[1] -= (a * (p[1] - g)) / NeuralNetworkQuantization.intS_ALPHA_RAD_BIAS;
					p[2] -= (a * (p[2] - r)) / NeuralNetworkQuantization.intS_ALPHA_RAD_BIAS;
				} catch (final Exception e) {} // prevents 1.3 miscompilation
			}
			if (k > lo) {
				p = this.intGnetworkAA[k--];
				try {
					p[0] -= (a * (p[0] - b)) / NeuralNetworkQuantization.intS_ALPHA_RAD_BIAS;
					p[1] -= (a * (p[1] - g)) / NeuralNetworkQuantization.intS_ALPHA_RAD_BIAS;
					p[2] -= (a * (p[2] - r)) / NeuralNetworkQuantization.intS_ALPHA_RAD_BIAS;
				} catch (final Throwable objPthrowable) {}
			}
		}
	}

	/*
	 * Move neuron i towards biased (b,g,r) by factor alpha
	 */
	protected void altersingle(int intPalpha, int intPneuron, int intPblue, int intPgreen, int intPred) {

		/* alter hit neuron */
		final int[] n = this.intGnetworkAA[intPneuron];
		n[0] -= (intPalpha * (n[0] - intPblue)) / NeuralNetworkQuantization.intS_INIT_ALPHA;
		n[1] -= (intPalpha * (n[1] - intPgreen)) / NeuralNetworkQuantization.intS_INIT_ALPHA;
		n[2] -= (intPalpha * (n[2] - intPred)) / NeuralNetworkQuantization.intS_INIT_ALPHA;
	}

	public byte[] colorMap() {
		final byte[] bytLmapA = new byte[3 * NeuralNetworkQuantization.intS_COLORS_NUMBER];
		final int[] intLindexA = new int[NeuralNetworkQuantization.intS_COLORS_NUMBER];
		for (int intLcolorIndex = 0; intLcolorIndex < NeuralNetworkQuantization.intS_COLORS_NUMBER; intLcolorIndex++) {
			intLindexA[this.intGnetworkAA[intLcolorIndex][3]] = intLcolorIndex;
		}
		int k = 0;
		for (int intLcolorIndex = 0; intLcolorIndex < NeuralNetworkQuantization.intS_COLORS_NUMBER; intLcolorIndex++) {
			final int j = intLindexA[intLcolorIndex];
			bytLmapA[k++] = (byte) (this.intGnetworkAA[j][0]);
			bytLmapA[k++] = (byte) (this.intGnetworkAA[j][1]);
			bytLmapA[k++] = (byte) (this.intGnetworkAA[j][2]);
		}
		return bytLmapA;
	}

	/*
	 * Search for biased BGR values
	 * ----------------------------
	 */
	protected int contest(int intPblue, int intPgreen, int intPred) {

		/* finds closest neuron (min dist) and updates freq */
		/* finds best neuron (min dist-bias) and returns position */
		/* for frequently chosen neurons, freq[i] is high and bias[i] is negative */
		/* bias[i] = gamma*((1/netsize)-freq[i]) */

		int i, dist, a, biasdist, betafreq;
		int bestpos, bestbiaspos, bestd, bestbiasd;
		int[] n;

		bestd = ~((1) << 31);
		bestbiasd = bestd;
		bestpos = -1;
		bestbiaspos = bestpos;

		for (i = 0; i < NeuralNetworkQuantization.intS_COLORS_NUMBER; i++) {
			n = this.intGnetworkAA[i];
			dist = n[0] - intPblue;
			if (dist < 0) {
				dist = -dist;
			}
			a = n[1] - intPgreen;
			if (a < 0) {
				a = -a;
			}
			dist += a;
			a = n[2] - intPred;
			if (a < 0) {
				a = -a;
			}
			dist += a;
			if (dist < bestd) {
				bestd = dist;
				bestpos = i;
			}
			biasdist =
						dist
							- ((this.intGbiasA[i]) >> (NeuralNetworkQuantization.intS_BIAS_SHIFT - NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS));
			if (biasdist < bestbiasd) {
				bestbiasd = biasdist;
				bestbiaspos = i;
			}
			betafreq = (this.intGfreqA[i] >> NeuralNetworkQuantization.intS_BETA_SHIFT);
			this.intGfreqA[i] -= betafreq;
			this.intGbiasA[i] += (betafreq << NeuralNetworkQuantization.intS_GAMMA_SHIFT);
		}
		this.intGfreqA[bestpos] += NeuralNetworkQuantization.intS_BETA;
		this.intGbiasA[bestpos] -= NeuralNetworkQuantization.intS_BETA_GAMMA;
		return (bestbiaspos);
	}

	/*
	 * Insertion sort of network and building of netindex[0..255] (to do after unbias)
	 * -------------------------------------------------------------------------------
	 */
	public void inxbuild() {

		int i, j, smallpos, smallval;
		int[] p;
		int[] q;
		int previouscol, startpos;

		previouscol = 0;
		startpos = 0;
		for (i = 0; i < NeuralNetworkQuantization.intS_COLORS_NUMBER; i++) {
			p = this.intGnetworkAA[i];
			smallpos = i;
			smallval = p[1]; /* index on g */
			/* find smallest in i..netsize-1 */
			for (j = i + 1; j < NeuralNetworkQuantization.intS_COLORS_NUMBER; j++) {
				q = this.intGnetworkAA[j];
				if (q[1] < smallval) { /* index on g */
					smallpos = j;
					smallval = q[1]; /* index on g */
				}
			}
			q = this.intGnetworkAA[smallpos];
			/* swap p (i) and q (smallpos) entries */
			if (i != smallpos) {
				j = q[0];
				q[0] = p[0];
				p[0] = j;
				j = q[1];
				q[1] = p[1];
				p[1] = j;
				j = q[2];
				q[2] = p[2];
				p[2] = j;
				j = q[3];
				q[3] = p[3];
				p[3] = j;
			}
			/* smallval entry is now in position i */
			if (smallval != previouscol) {
				this.intGnetIndexA[previouscol] = (startpos + i) >> 1;
				for (j = previouscol + 1; j < smallval; j++) {
					this.intGnetIndexA[j] = i;
				}
				previouscol = smallval;
				startpos = i;
			}
		}
		this.intGnetIndexA[previouscol] = (startpos + NeuralNetworkQuantization.intS_MAX_NET_POS) >> 1;
		for (j = previouscol + 1; j < 256; j++) {
			this.intGnetIndexA[j] = NeuralNetworkQuantization.intS_MAX_NET_POS; /* really 256 */
		}
	}

	/*
	 * Main Learning Loop
	 * ------------------
	 */
	public void learn() {

		int i, j, b, g, r;
		int radius, rad, alpha, step, delta, samplepixels;
		byte[] p;
		int pix, lim;

		if (this.intGlengthCount < NeuralNetworkQuantization.intS_MIN_PICTURE_BYTES) {
			this.intGsamplingFactor = 1;
		}
		this.intGalphaDec = 30 + ((this.intGsamplingFactor - 1) / 3);
		p = this.bytGpictureA;
		pix = 0;
		lim = this.intGlengthCount;
		samplepixels = this.intGlengthCount / (3 * this.intGsamplingFactor);
		delta = samplepixels / NeuralNetworkQuantization.intS_LEARNING_CYCLES_NUMBER;
		alpha = NeuralNetworkQuantization.intS_INIT_ALPHA;
		radius = NeuralNetworkQuantization.intS_INIT_RADIUS;

		rad = radius >> NeuralNetworkQuantization.intS_RADIUS_BIAS_SHIFT;
		if (rad <= 1) {
			rad = 0;
		}
		for (i = 0; i < rad; i++) {
			this.intGradPowerA[i] = alpha * (((rad * rad - i * i) * NeuralNetworkQuantization.intS_RAD_BIAS) / (rad * rad));
		}

		// fprintf(stderr,"beginning 1D learning: initial radius=%d\n", rad);

		if (this.intGlengthCount < NeuralNetworkQuantization.intS_MIN_PICTURE_BYTES) {
			step = 3;
		} else if ((this.intGlengthCount % NeuralNetworkQuantization.intS_PRIME_1) != 0) {
			step = 3 * NeuralNetworkQuantization.intS_PRIME_1;
		} else {
			if ((this.intGlengthCount % NeuralNetworkQuantization.intS_PRIME_2) != 0) {
				step = 3 * NeuralNetworkQuantization.intS_PRIME_2;
			} else {
				if ((this.intGlengthCount % NeuralNetworkQuantization.intS_PRIME_3) != 0) {
					step = 3 * NeuralNetworkQuantization.intS_PRIME_3;
				} else {
					step = 3 * NeuralNetworkQuantization.intS_PRIME_4;
				}
			}
		}

		i = 0;
		while (i < samplepixels) {
			b = (p[pix + 0] & 0xff) << NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS;
			g = (p[pix + 1] & 0xff) << NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS;
			r = (p[pix + 2] & 0xff) << NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS;
			j = this.contest(b, g, r);

			this.altersingle(alpha, j, b, g, r);
			if (rad != 0) {
				this.alterneigh(rad, j, b, g, r); /* alter neighbours */
			}

			pix += step;
			if (pix >= lim) {
				pix -= this.intGlengthCount;
			}

			i++;
			if (delta == 0) {
				delta = 1;
			}
			if (i % delta == 0) {
				alpha -= alpha / this.intGalphaDec;
				radius -= radius / NeuralNetworkQuantization.intS_RADIUS_DEC;
				rad = radius >> NeuralNetworkQuantization.intS_RADIUS_BIAS_SHIFT;
				if (rad <= 1) {
					rad = 0;
				}
				for (j = 0; j < rad; j++) {
					this.intGradPowerA[j] = alpha * (((rad * rad - j * j) * NeuralNetworkQuantization.intS_RAD_BIAS) / (rad * rad));
				}
			}
		}
		// fprintf(stderr,"finished 1D learning: final alpha=%f !\n",((float)alpha)/initalpha);
	}

	/*
	 * Search for BGR values 0..255 (after net is unbiased) and return colour index
	 * ----------------------------------------------------------------------------
	 */
	public int map(int intPblue, int intPgreen, int intPred) {

		int i, j, dist, a, bestd;
		int[] p;
		int best;

		bestd = 1000; /* biggest possible dist is 256*3 */
		best = -1;
		i = this.intGnetIndexA[intPgreen]; /* index on g */
		j = i - 1; /* start at netindex[g] and work outwards */

		while ((i < NeuralNetworkQuantization.intS_COLORS_NUMBER) || (j >= 0)) {
			if (i < NeuralNetworkQuantization.intS_COLORS_NUMBER) {
				p = this.intGnetworkAA[i];
				dist = p[1] - intPgreen; /* inx key */
				if (dist >= bestd) {
					i = NeuralNetworkQuantization.intS_COLORS_NUMBER; /* stop iter */
				} else {
					i++;
					if (dist < 0) {
						dist = -dist;
					}
					a = p[0] - intPblue;
					if (a < 0) {
						a = -a;
					}
					dist += a;
					if (dist < bestd) {
						a = p[2] - intPred;
						if (a < 0) {
							a = -a;
						}
						dist += a;
						if (dist < bestd) {
							bestd = dist;
							best = p[3];
						}
					}
				}
			}
			if (j >= 0) {
				p = this.intGnetworkAA[j];
				dist = intPgreen - p[1]; /* inx key - reverse dif */
				if (dist >= bestd) {
					j = -1; /* stop iter */
				} else {
					j--;
					if (dist < 0) {
						dist = -dist;
					}
					a = p[0] - intPblue;
					if (a < 0) {
						a = -a;
					}
					dist += a;
					if (dist < bestd) {
						a = p[2] - intPred;
						if (a < 0) {
							a = -a;
						}
						dist += a;
						if (dist < bestd) {
							bestd = dist;
							best = p[3];
						}
					}
				}
			}
		}
		return (best);
	}

	public byte[] process() {
		this.learn();
		this.unbiasnet();
		this.inxbuild();
		return this.colorMap();
	}

	/*
	 * Unbias network to give byte values 0..255 and record position i to prepare for sort
	 * -----------------------------------------------------------------------------------
	 */
	public void unbiasnet() {

		int i;
		for (i = 0; i < NeuralNetworkQuantization.intS_COLORS_NUMBER; i++) {
			this.intGnetworkAA[i][0] >>= NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS;
			this.intGnetworkAA[i][1] >>= NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS;
			this.intGnetworkAA[i][2] >>= NeuralNetworkQuantization.intS_COLORS_VALUES_BIAS;
			this.intGnetworkAA[i][3] = i; /* record colour no */
		}
	}
}
