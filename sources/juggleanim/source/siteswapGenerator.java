// siteswapGenerator.java
//
// Copyright 2002 by Jack Boyce (jboyce@users.sourceforge.net) and others

/*
    This file is part of JuggleAnim.

    JuggleAnim is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    JuggleAnim is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JuggleAnim; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
                         
// This class is a port of the J2 siteswap pattern generator to Java.
// For now the "custom" mode has been commented out, although at some
// point it might be interesting to have a graphical rhythm editor.

/************************************************************************/
/*   J version 2.3               by Jack Boyce        12/91             */
/*                                  jboyce@tybalt.caltech.edu           */
/*                                                                      */
/*   This program finds all juggling site swap patterns for a given     */
/*   number of balls, maximum throw value, and pattern length.          */
/*   A state graph approach is used in order to speed up computation.   */
/*                                                                      */
/*   It is a complete rewrite of an earlier program written in 11/90    */
/*   which handled only non-multiplexed asynchronous solo site swaps.   */
/*   This version can generate multiplexed and nonmultiplexed tricks    */
/*   for an arbitrary number of people, number of hands, and throwing   */
/*   rhythm.  The built-in modes are asynchronous and synchronous solo  */
/*   juggling, and two person asynchronous passing.                     */
/*                                                                      */
/*   Include flag modified and the -simple flag added on 2/92           */
/*   Extra check (for speed) added to gen_loops() on 01/19/98           */
/*   Bug fix to find_start_end() on 02/18/99                            */
/************************************************************************/

public class siteswapGenerator extends Generator {
		// Different types of siteswaps
	protected final static int ASYNCH_SOLO = 0;	/* different types of modes */
	protected final static int SYNCH_SOLO = 1;
	protected final static int ASYNCH_PASSING = 2;
//	protected final static int CUSTOM = 3;
	 
		// Types of multiplexing filter slots
	private final static int MP_EMPTY = 0;
	private final static int MP_THROW = 1;
	private final static int MP_LOWER_BOUND = 2;
	private final static int TYPE = 0;
	private final static int FROM = 1;
	private final static int VALUE = 2;
	
		// Other random constants
			// # of chars. in file input buffer
//	private final static int BUFFER_SIZE = 160;
			// max. # of chars. printed per throw
	private final static int CHARS_PER_THROW = 20;
	
	protected final static int solo_rhythm_repunit[][] = { { 1 } };
	protected final static int synch_rhythm_repunit[][] = { { 1, 0 }, { 1, 0 } };
	protected final static int asynch_passing_rhythm_repunit[][] = { { 1 }, { 1 } };
		
		// Instance variables
	protected int pattern_rhythm[][][], pattern_state[][][];
	protected int pattern_throwcount[][], pattern_holes[][][];
	protected int pattern_throw_to[][][], pattern_throw_value[][][];
	protected int pattern_filter[][][][];
	
	protected int hands, max_occupancy, leader_person;
	protected int rhythm_repunit[][], rhythm_period;
	protected int holdthrow[], person_number[], scratch1[], scratch2[];
	protected int ground_state[][];
	protected int n, l, ht, xarray[], xparray[], iarray[];
	protected int numflag, groundflag;
	protected int fullflag, mp_filter, delaytime;
	protected boolean lameflag, sequenceflag;
	protected int mode, people, slot_size;
	protected StringBuffer starting_seq, pattern_buffer, ending_seq;

	protected boolean streamout;		// output to stream, or to list?
	protected PrintStream psout;
	protected java.awt.List listout;
	protected int max_num;				// maximum number to print
	
	protected siteswapControl control;			// contains controls for generator
	
		// The public methods
		
	public String getStartupMessage() {
		return "Welcome to the J2 Siteswap Generator";
	}
	
	public void showControls() {
		if (control == null)
			control = new siteswapControl();
		control.show();
	}
	
	public void killControls() {
		if (control != null)
			control.dispose();
	}
	
	public void initGenerate() throws JuggleException {
		if (control == null)
			initGenerate("5 7 5");		// default settings
		else
			initGenerate(control.getParams());
	}
	
	public void initGenerate(String[] args) throws JuggleException {
		int i, j, k, multiplex = 1;
		
		if (args.length < 3)
			throw new JuggleException("Must give n, max_throw, and length");
			
		max_occupancy = 0;
		leader_person = 1;
		numflag = 0;
		groundflag = 0;
		fullflag = mp_filter = 1;
		delaytime = 0;
		lameflag = false;
		sequenceflag = true;
		mode = ASYNCH_SOLO;          /* default mode */
		
		n = Integer.parseInt(args[0]);
		ht = Integer.parseInt(args[1]);
		l = Integer.parseInt(args[2]);
		
		if (n < 1)
			throw new JuggleException("Must have at least 1 object");
		if (l < 1)
			throw new JuggleException("Pattern length must be at least 1");

		xarray = new int[ht + 1];			/* excluded self-throws */
		xparray = new int[ht + 1];			/* excluded passes */
		iarray = new int[ht + 1];

		for (i = 3; i < args.length; i++) {
			if (args[i].equals("-n"))
				numflag = 1;
			else if (args[i].equals("-no"))
				numflag = 2;
			else if (args[i].equals("-g"))
				groundflag = 1;
			else if (args[i].equals("-ng"))
				groundflag = 2;
			else if (args[i].equals("-f"))
				fullflag = 0;
			else if (args[i].equals("-simple"))
				fullflag = 2; 
			else if (args[i].equals("-lame"))
				lameflag = true;
			else if (args[i].equals("-se"))
				sequenceflag = false;
			else if (args[i].equals("-s"))
				mode = SYNCH_SOLO;
			else if (args[i].equals("-p"))
				mode = ASYNCH_PASSING;
    /*  else if (!strcmp(argv[i], "-c")) {
         mode = CUSTOM;
         if (i != (argc - 1))
            custom_initialize(argv[++i]); 
         else {
            printf("No custom rhythm file given\n");
            exit(0);
         }
      }*/
			else if (args[i].equals("-mf"))
				mp_filter = 0;
			else if (args[i].equals("-m")) { 
				if ((i < (args.length - 1)) && (args[i + 1].charAt(0) != '-')) {
					multiplex = Integer.parseInt(args[i + 1]);
					i++;
				}
			}
			else if (args[i].equals("-d")) {
				if ((i < (args.length - 1)) && (args[i + 1].charAt(0) != '-')) {
					delaytime = Integer.parseInt(args[i + 1]);
					groundflag = 1;        // find only ground state tricks
					i++;
				}
			}
			else if (args[i].equals("-l")) {
				if ((i < (args.length - 1)) && (args[i + 1].charAt(0) != '-')) {
					leader_person = Integer.parseInt(args[i + 1]);
					i++;
				}
			}
			else if (args[i].equals("-x") || args[i].equals("-xs")) {
				i++;
				while ((i < args.length) && (args[i].charAt(0) != '-')) {
					j = Integer.parseInt(args[i]);
					if ((j >= 0) && (j <= ht))
						xarray[j] = 1;
					i++;
				}
				i--;
			}
			else if (args[i].equals("-xp")) {
				i++;
				while ((i < args.length) && (args[i].charAt(0) != '-')) {
					j = Integer.parseInt(args[i]);
					if ((j >= 0) && (j <= ht))
						xparray[j] = 1;
					i++;
				}
				i--;
			}
			else if (args[i].equals("-i")) {
				i++;
				while ((i < args.length) && (args[i].charAt(0) != '-')) {
					j = Integer.parseInt(args[i]);
					if ((j >= 0) && (j <= ht))
						iarray[j] = 1;
					i++;
				}
				i--;
			} else
				throw new JuggleException("Unrecognized option '"+args[i]+"'"); 
		}

		for (i = 0; i <= ht; i++)        /* include and exclude flags clash? */
			if ((iarray[i] == 1) && (xarray[i] == 1))
				throw new JuggleException("Included and excluded throws clash");

	//	if (mode != CUSTOM)
			initialize();

		if ((l % rhythm_period) != 0)
			throw new JuggleException("Pattern length must be a multiple of " +
						Integer.toString(rhythm_period));

       /*  The following variable slot_size serves two functions.  It  */
       /*  is the size of a slot used in the multiplexing filter, and  */
       /*  it is the number of throws allocated in memory.  The number */
       /*  of throws needs to be larger than L sometimes since these   */
       /*  same structures are used to find starting and ending        */
       /*  sequences (containing as many as HT elements).              */

		slot_size = ((ht > l) ? ht : l);
		slot_size += rhythm_period - (slot_size % rhythm_period);

		for (i = 0; i < hands; i++)
			for (j = 0; j < rhythm_period; j++)
				if ((k = rhythm_repunit[i][j]) > max_occupancy)
					max_occupancy = k;
		max_occupancy *= multiplex;
		if (max_occupancy == 1)       /* no multiplexing, turn off filter */
			mp_filter = 0;

		/*  Now allocate the memory space for the states, rhythms, and  */
		/*  throws in the pattern, plus other incidental variables.     */

		pattern_state = new int[slot_size+1][hands][ht];
		
		pattern_rhythm = new int[slot_size+1][hands][ht];
		for (i = 0; i < (slot_size + 1); i++)
			for (j = 0; j < hands; j++) 
				for (k = 0; k < ht; k++)
					pattern_rhythm[i][j][k] = 
  	         multiplex * rhythm_repunit[j][(k + i) % rhythm_period];

		pattern_holes = new int[l][hands][ht];

		pattern_throw_to = new int[slot_size][hands][max_occupancy];
		pattern_throw_value = new int[slot_size][hands][max_occupancy];

		if (mp_filter != 0)         /* allocate space for filter variables */
			pattern_filter = new int[l+1][hands][slot_size][3];

		pattern_throwcount = new int[l][hands];
		ground_state = new int[hands][ht];

		if (people > 1) {       /* passing communication delay variables */
			scratch1 = new int[hands];
			scratch2 = new int[hands];
		}

		find_ground();                /* find ground state */
	}
	
	
	public int runGenerate(PrintStream ps) {
		try {
			return runGenerate(ps, -1);			// no limit
		}
		catch (JuggleException je) {
		}
		return 0;		// can't happen
	}
			
	public int runGenerate(java.awt.List lst) {			// no limit
		try {
			return runGenerate(lst, -1);
		}
		catch (JuggleException je) {
		}
		return 0;		// can't happen
	}
	
	public int runGenerate(PrintStream ps, int max_num) throws JuggleException {
		streamout = true;
		psout = ps;
		this.max_num = max_num;
		int num;
		
		try {
			num = gen_patterns(0, 0, 0, 0); 
	
			if (numflag != 0) {
				if (num == 1)
					ps.println("1 pattern found");
				else
					ps.println(num + " patterns found");
			}
		}
		catch (JuggleException je) {
			String result = "Limit of "+max_num+" patterns exceeded";
			ps.println(result);
			throw new JuggleException(result);
		}
		
		return num;
	}
	
	public int runGenerate(java.awt.List lst, int max_num) throws JuggleException {
		streamout = false;
		listout = lst;
		this.max_num = max_num;
		
		try {
			return gen_patterns(0, 0, 0, 0);
		}
		catch (JuggleException je) {
			throw new JuggleException("Limit of "+max_num+" patterns exceeded");
		}
	}
	
	public static String makeAnimatorInput(String pattern) {
		StringTokenizer st = new StringTokenizer(pattern.toLowerCase(), " ");
		
		int numtokens = st.countTokens();
		
		String reppattern = st.nextToken();
		if (numtokens > 1)
			reppattern = st.nextToken();
		
		String animinput;
		char firstchar = reppattern.charAt(0);
		if (firstchar == '(')
			animinput = "mode=syncsiteswap;pattern="+reppattern+";slowdown=2.0";
		else if (firstchar == '<')
			animinput = null;
		else
			animinput = "mode=siteswap;pattern="+reppattern+";slowdown=2.0";
			
		return animinput;
	}
	
	public String virtual_makeAnimatorInput(String pattern) {
		return siteswapGenerator.makeAnimatorInput(pattern);
	}
	
		// Now the protected methods
		
		/* The following routine initializes stuff for the built-in rhythms. */
		/* This involves allocating and initializing arrays.                 */

	protected void initialize() {
		int i, j;

		switch (mode) {
			case ASYNCH_SOLO:
				rhythm_repunit = new int[1][1];
				rhythm_repunit[0][0] = solo_rhythm_repunit[0][0];
				hands = 1;
				rhythm_period = 1;
				people = 1;
				holdthrow = new int[1];
         		holdthrow[0] = 2;
				person_number = new int[1];
				person_number[0] = 1;
				break;
			case SYNCH_SOLO:
				rhythm_repunit = new int[2][2];
				for (i = 0; i < 2; i++)
					for (j = 0; j < 2; j++)
						rhythm_repunit[i][j] = synch_rhythm_repunit[i][j];
				hands = 2;
				rhythm_period = 2;
				people = 1;
				holdthrow = new int[2];
         		holdthrow[0] = holdthrow[1] = 2;
				person_number = new int[2];
				person_number[0] = person_number[1] = 1;
				break;
			case ASYNCH_PASSING:
				rhythm_repunit = new int[2][1];
				for (i = 0; i < 2; i++)
					rhythm_repunit[i][0] = asynch_passing_rhythm_repunit[i][0];
				hands = 2;
				rhythm_period = 1;
				people = 2;
				holdthrow = new int[2];
				holdthrow[0] = holdthrow[1] = 2;
				person_number = new int[2];
				person_number[0] = 1;
				person_number[1] = 2;
				break;
		}
	}


		/* This routine reads a custom rhythm file and parses it to get the */
		/* relevant information.  If there is an error it prints a message  */
		/* and then exits.                                                  */
	/*
	void custom_initialize(char *custom_file)   // name of file
	{
   int i, j, k, left_delim, right_delim;
   int last_period, last_person, person, hold, second_pass;
   char ch, *file_buffer;
   FILE *fp;

   if ((fp = fopen(custom_file, "r")) == NULL) {
      printf("File error: cannot open '%s'\n", custom_file);
      exit(0);
   }
   if ((file_buffer = (char *)malloc(BUFFER_SIZE * sizeof(char))) == 0)
      die();

   for (second_pass = 0; second_pass < 2; second_pass++) {
      hands = j = 0;
      people = last_person = 1;

      do {
         ch = (char)(i = fgetc(fp));

         if ((ch == (char)10) || (i == EOF)) {
            file_buffer[j] = (char)0;

            for (j = 0, k = 0; (ch = file_buffer[j]) && (ch != ';'); j++)
                if (ch == '|') {
                   if (++k == 1)
                      left_delim = j;
                   else if (k == 2)
                      right_delim = j;
                }
            if (ch == ';')
               file_buffer[j] = (char)0;        // terminate at comment

            if (k) {
               if (k != 2) {
                 printf("File error: need two rhythm delimiters per hand\n");
                  exit(0);
               }
                      // At this point the line checks out.  See if
                      // period is what we got last time.
               if (hands && ((right_delim-left_delim-1) != last_period)) {
                  printf("File error: rhythm period not constant\n");
                  exit(0);
               }
               last_period = right_delim - left_delim - 1;

                      // Now parse the line we've read in

               file_buffer[left_delim] = (char)0;
               person = atoi(file_buffer);

               if (hands) {
                  if (person == (last_person + 1)) {
                     people++;
                     last_person = person;
                  } else if (person != last_person) {
                     printf("File error: person numbers goofed up\n");
                     exit(0);
                  }
               } else if (person != 1) {
                  printf("File error: must start with person number 1\n");
                  exit(0);
               }

                      // Now put stuff in the allocated arrays

               if (second_pass) {
                  person_number[hands] = person;
                  hold = atoi(file_buffer + right_delim + 1);
                  holdthrow[hands] = (hold ? hold : 2);

                          // Fill the rhythm matrix
                  for (j = 0; j < rhythm_period; j++) {
                     ch = file_buffer[j + left_delim + 1];
                     if (((ch < '0') || (ch > '9')) && (ch != ' ')) {
                        printf("File error: bad character in rhythm\n");
                        exit(0);
                     }
                     if (ch == ' ')
                        ch = '0';
                     rhythm_repunit[hands][j] = (int)(ch - '0');
                  }
               }

               hands++;   // got valid line, increment counter
            }
            j = 0;    // reset buffer pointer for next read
         } else {
            file_buffer[j] = ch;
            if (++j >= BUFFER_SIZE) {
               printf("File error: input buffer overflow\n");
               exit(0);
            }
         }
      } while (i != EOF);

      if (!hands) {
         printf("File error: must have at least one hand\n");
         exit(0);
      }

      if (!second_pass) {        // allocate space after first pass
         rhythm_period = last_period;
         rhythm_repunit = alloc_array(hands, rhythm_period);
         if ((holdthrow = (int *)malloc(hands * sizeof(int))) == 0)
            die();
         if ((person_number = (int *)malloc(hands * sizeof(int))) == 0)
            die();
         rewind(fp);          // go back to start of file
      }
                  
   }

   (void)fclose(fp);        // close file and free memory
   free(file_buffer);
	}
	*/


		/*  valid_throw -- checks if a given throw is valid.  Check for        */
		/*                 excluded throws and a passing communication delay,  */
		/*                 as well a custom filter (if in CUSTOM mode).        */

	protected boolean valid_throw(int pos)
	{
		int i, j, k, balls_left, balls_thrown;

		for (i = 0; i < hands; i++) {            	// check for excluded throws
			if (pattern_rhythm[pos][i][0] != 0) {	// can we make a throw here?
				for (j = 0; (j < max_occupancy) &&
	                    ((k = pattern_throw_value[pos][i][j]) != 0); j++) {
					if ((people > 1) && (person_number[i] !=
                            person_number[pattern_throw_to[pos][i][j]])) {
						if (xparray[k] != 0)
							return false;
					} else if (xarray[k] != 0)
						return false;
				}
				if ((j == 0) && (xarray[0] != 0))
					return false;
			}
		}
		
          /*  Now check if we are allowing for a sufficient  */
          /*  communication delay, if we are passing.        */

		if ((people > 1) && (pos < delaytime)) {      /* need to check? */
            /*  First count the number of balls being thrown,       */
            /*  assuming no multiplexing.  Also check if leader is  */
            /*  forcing others to multiplex or make no throw.       */
			for (balls_thrown = 0, i = 0; i < hands; i++)
				if (pattern_rhythm[pos][i][0] != 0) {
					balls_thrown++;
					if ((pattern_state[pos][i][0] != 1) && 
								(person_number[i] != leader_person))
						return false;
				}

			balls_left = n;
			for (i = 0; (i < ht) && (balls_left != 0); i++)
				for (j = 0; (j < hands) && (balls_left != 0); j++)
					if (pattern_rhythm[pos + 1][j][i] != 0)
						if (--balls_left < balls_thrown) {
							scratch1[balls_left] = j;       /* dest hand # */
							scratch2[balls_left] = i + 1;   /* dest value */
						}

			if (balls_left != 0)
				return false;       /* this shouldn't happen, but die anyway */

			for (i = 0; i < hands; i++)
				if ((pattern_state[pos][i][0] != 0) && 
                            (person_number[i] != leader_person)) {
					for (j = 0, k = 1; (j < balls_thrown) && (k != 0); j++)
						if ((scratch1[j] == pattern_throw_to[pos][i][0]) &&
								(scratch2[j] == pattern_throw_value[pos][i][0]))
							scratch2[j] = k = 0;    // can't throw to spot again
					if (k != 0)
						return false;   /* wasn't throwing to empty position */
				}
		}

		return true;
	}


	protected boolean valid_pattern()
	{
		int i, j, k, m, q;
		boolean flag;
		
		for (i = 0; i <= ht; i++) {        /* check for included throws */
			if (iarray[i] != 0) {
				flag = true;
				for (j = 0; (j < l) && flag; j++) {
					for (k = 0; (k < hands) && flag; k++) {
						if (pattern_rhythm[j][k][0] != 0) {
							m = 0;
							do {
								q = pattern_throw_value[j][k][m];
								if ((q == i) && (k == pattern_throw_to[j][k][m]))
									flag = false;
								m++;
							} while ((m < max_occupancy) && (q != 0) && flag);
						}
					}
				}
				if (flag)
					return false;
			}
		}

//		if (mode == CUSTOM)
//			return true;
		if ((mode == ASYNCH_SOLO) && lameflag && (max_occupancy == 1)) {
			for (i = 0; i < (l - 1); i++)       /* check for '11' sequence */
				if ((pattern_throw_value[i][0][0] == 1) &&
							(pattern_throw_value[i+1][0][0] == 1))
					return false;
		}

          /********************************************************/
          /*  If you want to add an extra pattern filter for one  */
          /*  of the built-in modes, do it here.                  */
          /********************************************************/

		return true;
	}


								/* prints number as single character */
	protected void print_number(StringBuffer sb, int value)
	{
		sb.append(Character.toUpperCase(Character.forDigit(value, 36)));
	}


	protected void print_throw(StringBuffer sb, int throw_value[][],
					int throw_to[][], int rhythm[][])  /* prints single throw */
	{
		int i, j;

		for (i = 0, j = 1; (i < hands) && (j != 0); i++)
			if (rhythm[i][0] != 0)           /* supposed to make a throw? */
				j = 0;
		if (j != 0)
			return;      	/* can't make a throw, skip out */

		switch (mode) {
			case ASYNCH_SOLO:
				if ((max_occupancy > 1) && (throw_value[0][1] != 0)) {
					sb.append("[");
					for (i = 0; (i < max_occupancy) &&
	       		             	((j = throw_value[0][i]) != 0); i++)
						print_number(sb, j);
					sb.append("]");
				} else
					print_number(sb, throw_value[0][0]);
				break;
			case SYNCH_SOLO:
				sb.append("(");
				if ((max_occupancy > 1) && (throw_value[0][1] != 0)) {
					sb.append("[");
					for (i = 0; (i<max_occupancy) &&
								((j=throw_value[0][i]) != 0); i++) {
						print_number(sb, j);
						if (throw_to[0][i] != 0)
							sb.append("x");
					}
					sb.append("]");
				} else {
					print_number(sb, throw_value[0][0]);
					if (throw_to[0][0] != 0)
						sb.append("x");
				}
				sb.append(",");
				if ((max_occupancy > 1) && (throw_value[1][1] != 0)) {
					sb.append("[");
					for (i = 0; (i<max_occupancy) &&
								((j=throw_value[1][i]) != 0); i++) {
						print_number(sb, j);
						if (throw_to[1][i] == 0)
							sb.append("x");
					}
					sb.append("]");
				} else {
					print_number(sb, throw_value[1][0]);
					if (throw_to[1][0] == 0)
						sb.append("x");
				}
				sb.append(")");
				break;
			case ASYNCH_PASSING:
				sb.append("<");
				if ((max_occupancy > 1) && (throw_value[0][1] != 0)) {
					sb.append("[");
					for (i = 0; (i<max_occupancy) &&
								((j=throw_value[0][i]) != 0); i++) {
						print_number(sb, j);
						if (throw_to[0][i] != 0)
							sb.append("p");
					}
					sb.append("]");
				} else {
					print_number(sb, throw_value[0][0]);
					if (throw_to[0][0] != 0)
						sb.append("p");
				}
				sb.append("|");
				if ((max_occupancy > 1) && (throw_value[1][1] != 0)) {
					sb.append("[");
					for (i = 0; (i<max_occupancy) &&
								((j=throw_value[1][i]) != 0); i++) {
						print_number(sb, j);
						if (throw_to[1][i] != 1)
							sb.append("p");
					}
					sb.append("]");
				} else {
					print_number(sb, throw_value[1][0]);
					if (throw_to[1][0] != 1)
						sb.append("p");
				}
				sb.append(">");
				break;
//			case CUSTOM:
//				default_custom_print_throw(pos, throw, rhythm);
//				break;
		}
	}


/*  The following is the default routine that is used to print a  */
/*  throw when the program is in CUSTOM mode.                     */
/*
char *default_custom_print_throw(char *pos, struct throw **throw, int **rhythm)
{
   int i, j, k, m, q, lo_hand, hi_hand, multiplex, parens;
   char ch;

   if (people > 1)
      *pos++ = '<';

   for (i = 1; i <= people; i++) {

           // first find the hand numbers corresponding to person
      for (lo_hand = 0; person_number[lo_hand] != i; lo_hand++)
         ;
      for (hi_hand = lo_hand; (hi_hand < hands) && 
                         (person_number[hi_hand] == i); hi_hand++)
         ;

           // check rhythm to see if person is throwing this time
      for (j = lo_hand, k = 0; j < hi_hand; j++)
         if (rhythm[j][0])
            k++;

      if (k) {        // person should throw
         if (k > 1) {     // more than one hand throwing?
            *pos++ = '(';
            parens = 1;
         } else
            parens = 0;

         for (j = lo_hand; j < hi_hand; j++) {
            if (rhythm[j][0]) {      // this hand supposed to throw?
               if ((max_occupancy > 1) && throw[j][1].value) {
                  *pos++ = '[';        // multiplexing?
                  multiplex = 1;
               } else
                  multiplex = 0;

                  // Now loop thru the throws coming out of this hand

               for (k = 0; (k < max_occupancy) && 
                                         (m = throw[j][k].value); k++) {
                  pos = print_number(pos, m);    // print throw value

                  if (hands > 1) {    // ambiguity about destination?
                     if ((m = person_number[throw[j][k].to]) != i) {
                        *pos++ = ':';
						pos = print_number(pos, m);
                     }
										// person number

                     for (q = throw[j][k].to - 1, ch = 'a'; (q >= 0) &&
                              (person_number[q] == m); q--, ch++)
                        ;        // find hand # of destination person

                        // destination person has 1 hand, don't print
                     if ((ch != 'a') || ((q < (hands - 2)) &&
                                           (person_number[q + 2] == m)))
                        *pos++ = ch;             // print it
                  }

                  if (multiplex && (people > 1) &&
                        (k != (max_occupancy - 1)) && throw[j][k + 1].value)
                     *pos++ = '/';
								   // another multiplexed throw?
               }
               if (k == 0)
                  *pos++ = '0';

               if (multiplex)
                  *pos++ = ']';
            }

            if ((j < (hi_hand - 1)) && parens)  // put comma between hands
              *pos++ = ',';
         }
         if (parens)
            *pos++ = ')';
      }

      if (i < people)           // another person throwing next?
         *pos++ = '|';
   }

   if (people > 1)
      *pos++ = '>';

   return (pos);
}
*/

	protected void print_pattern()
	{
		int i, j, excited = 0, skip;
		StringBuffer outputline = new StringBuffer(hands*(2*ht+l)*CHARS_PER_THROW + 10);

		if (groundflag != 1) {
			if (sequenceflag) {
				if (mode == ASYNCH_SOLO)
					for (i = n - starting_seq.length(); i > 0; i--)
						outputline.append(" ");
				outputline.append(starting_seq.toString() + "  ");
			} else {
				excited = compare_states(ground_state, pattern_state[0]);
				if (excited != 0)
					outputline.append("* ");
				else
					outputline.append("  ");
			}
		}
			
		for (i = 0; i < l; i++)
			print_throw(outputline, pattern_throw_value[i], 
						pattern_throw_to[i], pattern_rhythm[i]);

		if (groundflag != 1) {				// there was another bug here
			if (sequenceflag)
				outputline.append("  " + ending_seq.toString());
			else if (excited != 0)
				outputline.append(" *");	// there was a bug here
		}
		
		if (streamout)
			psout.println(outputline.toString());
		else
			listout.add(outputline.toString());
	}


		/*  compare_states -- equality (0), lesser (-1), or greater (1)     */

	protected int compare_states(int state1[][], int state2[][])
	{
		int i, j, mo1 = 0, mo2 = 0;

		for (i = 0; i < hands; i++)
			for (j = 0; j < ht; j++) {
				if (state1[i][j] > mo1)
					mo1 = state1[i][j];
				if (state2[i][j] > mo2)
					mo2 = state2[i][j];
			}

		if (mo1 > mo2)
			return 1;
		if (mo1 < mo2)
			return -1;

		for (j = (ht - 1); j >= 0; j--)
			for (i = (hands - 1); i >= 0; i--) {
				mo1 = state1[i][j];
				mo2 = state2[i][j];
				if (mo1 > mo2)
					return 1;
				if (mo1 < mo2)
					return -1;
			}

		return 0;
	}


		/*  The next function is part of the implementation of the   */
		/*  multiplexing filter.  It adds a throw to a filter slot,  */
		/*  returning 1 if there is a collision, 0 otherwise.        */

	protected int mp_addthrow(int dest_slot[], int slot_hand, int type,
							int value, int from)
	{
		switch (type) {
			case MP_EMPTY:
				return 0;
			case MP_LOWER_BOUND:
				if (dest_slot[TYPE] == MP_EMPTY) {
					dest_slot[TYPE] = MP_LOWER_BOUND;
					dest_slot[VALUE] = value;
					dest_slot[FROM] = from;
				}
				return 0;
			case MP_THROW:
				if ((from == slot_hand) && (value == holdthrow[slot_hand]))
					return 0;           /* throw is a hold, so ignore it */

				switch (dest_slot[TYPE]) {
					case MP_EMPTY:
						dest_slot[TYPE] = MP_THROW;
						dest_slot[VALUE] = value;
						dest_slot[FROM] = from;
						return 0;
					case MP_LOWER_BOUND:
						if ((dest_slot[VALUE] <= value) || (dest_slot[VALUE] <=
										holdthrow[slot_hand])) {
							dest_slot[TYPE] = MP_THROW;
							dest_slot[VALUE] = value;
							dest_slot[FROM] = from;
							return 0;
						}
						break;       /* this kills recursion */
					case MP_THROW:
						if ((dest_slot[FROM] == from) &&
										(dest_slot[VALUE] == value))
							return 0;        /* throws from same place (clump) */
						break;       /* kills recursion */
				}
				break;
		}

		return 1;
	}


		/*  gen_loops -- This recursively generates loops, given a particular   */
		/*               starting state.                                        */

	protected int gen_loops(int pos, int throws_made, int min_throw,
				int min_hand, int num) throws JuggleException
/* int pos;              /* slot number in pattern that we're constructing */
/* int throws_made;      /* number of throws made out of current slot      */
/* int min_throw;        /* lowest we can throw this time                  */
/* int min_hand;         /* lowest hand we can throw to this time          */
/* unsigned int num;     /* number of valid patterns counted               */
	{
		int i, j, k, m, o;

		if (pos == l) {
			if ((compare_states(pattern_state[0], pattern_state[l]) == 0) &&
	                        valid_pattern()) {
				if (numflag != 2)
					print_pattern();
				if (num++ == max_num)
					throw new JuggleException();	// exceeded limit
			}
			return num;
		}

		if (throws_made == 0) 
			for (i = 0; i < hands; i++) {
				pattern_throwcount[pos][i] = pattern_state[pos][i][0];
				for (j = 0; j < ht; j++) {
					pattern_holes[pos][i][j] = pattern_rhythm[pos + 1][i][j];
					if (j != (ht - 1))
						pattern_holes[pos][i][j] -= pattern_state[pos][i][j + 1];
				}
				for (j = 0; j < max_occupancy; j++) {
					pattern_throw_to[pos][i][j] = i;      /* clear throw matrix */
					pattern_throw_value[pos][i][j] = 0;
				}
			}

		for (i = 0; (i < hands) && (pattern_throwcount[pos][i] == 0); i++)
 			;

		if (i == hands) {  /* done with current slot, move to next */ 
			if (!valid_throw(pos))              /* is the throw ok? */
				return num;

				/* first calculate the next state in ptrn, given last throw */
			for (j = 0; j < hands; j++)      /* shift state to the left */
				for (k = 0; k < ht; k++)
					pattern_state[pos + 1][j][k] = 
							( (k == (ht-1)) ? 0 : pattern_state[pos][j][k+1] );

                  /* add on the last throw */
			for (j = 0; j < hands; j++)
				for (k = 0; (k < max_occupancy) && 
	                      	((m = pattern_throw_value[pos][j][k]) != 0); k++) 
					pattern_state[pos + 1][pattern_throw_to[pos][j][k]][m - 1]++;
					
				/* Check if this is a valid state for a period-L pattern */
				/* This check added 01/19/98.                            */
			for (j = 0; j < hands; j++) {
				for (k = 0; k < ht; k++) {
					m = pattern_state[pos + 1][j][k];
					o = k;
					while ((o += l) < ht)
						if (pattern_state[pos + 1][j][o] > m)
							return num;     /* die (invalid state for this L) */
				}
			}
				/* end of new section */
			
			if (((pos + 1) % rhythm_period) == 0) {   
                 /* can we compare states? (rhythms must be same) */
				j = compare_states(pattern_state[0], pattern_state[pos + 1]);
				if ((fullflag != 0) && (pos != (l - 1)) && (j == 0))  /* intersection */
					return num;
				if (j == 1)       /* prevents cyclic perms. from being printed */
					return num;
			}

			if (fullflag == 2) {            /* list only simple loops? */
				for (j = 1; j <= pos; j++)
					if (((pos + 1 - j) % rhythm_period) == 0) { 
						if (compare_states(pattern_state[j],
										pattern_state[pos + 1]) == 0) 
							return num; 
					} 
			} 

          /*  Now do the multiplexing filter.  This ensures that,  */
          /*  other than holds, objects from only one source are   */
          /*  landing in any given hand (for example, a clump of   */
          /*  3's).  The implementation is a little complicated,   */
          /*  since I want to cut off the recursion as quickly as  */
          /*  possible to get speed on big searches.  This         */
          /*  precludes simply generating all patterns and then    */
          /*  throwing out the unwanted ones.                      */

			if (mp_filter != 0) {
				for (j = 0; j < hands; j++) {    /* shift filter frame to left */
					for (k = 0; k < (slot_size - 1); k++) {
						pattern_filter[pos + 1][j][k][TYPE] = 
									pattern_filter[pos][j][k + 1][TYPE];
						pattern_filter[pos + 1][j][k][FROM] =
									pattern_filter[pos][j][k + 1][FROM];
						pattern_filter[pos + 1][j][k][VALUE] =
									pattern_filter[pos][j][k + 1][VALUE];
					}
					pattern_filter[pos + 1][j][slot_size - 1][TYPE] = MP_EMPTY;
                                  /* empty slots shift in */

					if (mp_addthrow( pattern_filter[pos + 1][j][l - 1],
								j, pattern_filter[pos][j][0][TYPE],
								pattern_filter[pos][j][0][VALUE],
								pattern_filter[pos][j][0][FROM]) != 0)
						return num;
				}

				for (j = 0; j < hands; j++)           /* add on last throw */
					for (k = 0; (k < max_occupancy) &&
       				       ((m = pattern_throw_value[pos][j][k]) != 0); k++)
						if (mp_addthrow(
			pattern_filter[pos + 1][pattern_throw_to[pos][j][k]][m - 1], 
									pattern_throw_to[pos][j][k], MP_THROW, m, j) != 0)
							return num;        /* problem, so end recursion */
			}

			num = gen_loops(pos + 1, 0, 1, 0, num);       /* go to next slot */ 
		} else {
			m = --pattern_throwcount[pos][i];     /* record throw */
			k = min_hand;

			for (j = min_throw; j <= ht; j++) {
				for ( ; k < hands; k++) {
					if (pattern_holes[pos][k][j - 1] != 0) {/*can we throw to position?*/
						pattern_holes[pos][k][j - 1]--;
						pattern_throw_to[pos][i][m] = k;
						pattern_throw_value[pos][i][m] = j;
						if (m != 0)
							num = gen_loops(pos, throws_made + 1, j, k, num);
						else
							num = gen_loops(pos, throws_made + 1, 1, 0, num);
						pattern_holes[pos][k][j - 1]++;
					}
				}
				k = 0;
			}
			pattern_throwcount[pos][i]++;
		}

		return num;
	}


		/*  The next routine finds valid starting and ending sequences for      */
		/*  excited state patterns.  Note that these sequences are not unique.  */

	protected void find_start_end()
	{
		int i, j, k, m, q;
		boolean flag;

		starting_seq = new StringBuffer(hands * ht * CHARS_PER_THROW);
		ending_seq = new StringBuffer(hands * ht * CHARS_PER_THROW);
              
			  /* first find the starting sequence */
		i = slot_size;       /* throw position to start at (work back to gnd) */
		for (j = 0; j < hands; j++)
			for (k = 0; k < ht; k++)
				pattern_state[i][j][k] = pattern_state[0][j][k];   /* copy state */

		try {
			while (((i % rhythm_period) != 0) ||
						(compare_states(pattern_state[i], ground_state) != 0) ) {
				m = ht;            /* pointers to current ball we're pulling down */
				q = hands - 1;
				flag = true;
				
				for (j = 0; (j < hands) && flag; j++) {
					for (k = 0; k < max_occupancy; k++) {     /* clear throw matrix */
						pattern_throw_value[i - 1][j][k] = 0;
						pattern_throw_to[i - 1][j][k] = j;
					}
	
					pattern_state[i - 1][j][0] = 0;
					if (pattern_rhythm[i - 1][j][0] != 0) {
						while (flag && (pattern_state[i][q][m - 1] == 0)) {
							if (q-- == 0) {     /* go to next position to pull down */
								q = hands - 1;
								if (--m == 0)
									flag = false;
							}
						}
						if (flag) {
							pattern_throw_value[i - 1][j][0] = m;
							pattern_throw_to[i - 1][j][0] = q;
							pattern_state[i][q][m - 1]--;
							pattern_state[i - 1][j][0]++;
						}
					}
				}
	
				for (j = 0; j < hands; j++) {
					if (pattern_state[i][j][ht - 1] != 0) {
						starting_seq.append("?");
						throw new JuggleException();
					}
					for (k = 1; k < ht; k++)
						pattern_state[i - 1][j][k] = pattern_state[i][j][k - 1];
				}
	
				i--;
					
				if ((i == 0) && (compare_states(pattern_state[0], ground_state) != 0)) {
					starting_seq.append("?");
					throw new JuggleException();
				}
			}
	
			for ( ; i < slot_size; i++)		/* write starting seq. to buffer */
				print_throw(starting_seq, pattern_throw_value[i], 
							pattern_throw_to[i], pattern_rhythm[i]);
		} catch (JuggleException je) {
		}

         /*  Now construct an ending sequence.  Unlike the starting  */
         /*  sequence above, this time work forward to ground state. */

		i = 0;
		while (((i % rhythm_period) != 0) ||
					(compare_states(pattern_state[i], ground_state) != 0)) {
			m = 1;
			q = 0;
			for (j = 0; j < hands; j++) {
				for (k = 0; k < max_occupancy; k++) {
					pattern_throw_value[i][j][k] = 0;
					pattern_throw_to[i][j][k] = j;
				}
				for (k = pattern_state[i][j][0] - 1; k >= 0; k--) {
					flag = true;
					while (flag) {
						for ( ; (q < hands) && flag; q++) {
							if ((pattern_rhythm[i+1][q][m-1] != 0) && ((m >= ht) || 
                                  	(pattern_state[i][q][m] == 0))) {
								if (m > ht) {
									ending_seq.append("?");
									return;          /* no place to put ball */
								}
								pattern_throw_value[i][j][k] = m;
								pattern_throw_to[i][j][k] = q;
								flag = false;
							}
						}
						
						if (q == hands) {
							q = 0;
							m++;
						}
					}
				}
			}
			
			for (j = 0; j < hands; j++) {       /* shift the state left */
				for (k = 0; k < (ht - 1); k++)
					pattern_state[i+1][j][k] = pattern_state[i][j][k+1];
				pattern_state[i+1][j][ht-1] = 0;
			}
			for (j = 0; j < hands; j++)         /* add on the last throws */
				for (k = 0; (k < max_occupancy) && 
                    		((m = pattern_throw_value[i][j][k]) != 0); k++)
					pattern_state[i+1][pattern_throw_to[i][j][k]][m-1] = 1;
			if (++i > ht) {
				ending_seq.append("?");
				return;
			}
		}

		for (j = 0; j < i; j++)		/* write ending seq. to buffer */
			print_throw(ending_seq, pattern_throw_value[j], 
							pattern_throw_to[j], pattern_rhythm[j]);
	}


		/*  gen_patterns -- Recursively generates all possible starting        */
		/*                  states, calling gen_loops above to find the loops  */
		/*                  for each one.                                      */

	protected int gen_patterns(int balls_placed, int min_value, int min_to,
					int num) throws JuggleException
	{
		int i, j, k, m, q;

		if ((balls_placed == n) || (groundflag == 1)) {
			if (groundflag == 1) {    /* find only ground state patterns? */
				for (i = 0; i < hands; i++)
					for (j = 0; j < ht; j++)
						pattern_state[0][i][j] = ground_state[i][j];
			} else if ((groundflag == 2) &&
                     (compare_states(pattern_state[0], ground_state) == 0))
				return num;         /* don't find ground state patterns */

           /*  At this point our state is completed.  Check to see      */
           /*  if it's valid.  (Position X must be at least as large    */
           /*  as position X+L, where L = pattern length.)  Also set    */
           /*  up the initial multiplexing filter frame, if we need it. */

			for (i = 0; i < hands; i++) {
				for (j = 0; j < ht; j++) {

					k = pattern_state[0][i][j];
					if ((mp_filter != 0) && (k == 0))
						pattern_filter[0][i][j][TYPE] = MP_EMPTY;
					else {
						if (mp_filter != 0) {
							pattern_filter[0][i][j][VALUE] = j + 1;
							pattern_filter[0][i][j][FROM] = i;
							pattern_filter[0][i][j][TYPE] = MP_LOWER_BOUND;
						}

						m = j;
						while ((m += l) < ht) {
							if ((q = pattern_state[0][i][m]) > k)
								return num;     /* die (invalid state for this L) */
							if ((mp_filter != 0) && (q != 0)) {
								if ((q < k) && (j > holdthrow[i]))
									return num;  /* different throws into same hand */
								pattern_filter[0][i][j][VALUE] = m + 1;  /* new bound */
							}
						}
					}
				}
        
				if (mp_filter != 0)
					for ( ; j < slot_size; j++)
						pattern_filter[0][i][j][TYPE] = MP_EMPTY; /* clear rest of slot */
			}

			if ((numflag != 2) && sequenceflag) 
				find_start_end();/* find starting and ending sequences for state */

			return gen_loops(0, 0, 1, 0, num);   /* find patterns thru state */
		}

		if (balls_placed == 0) {        /* startup, clear state */
			for (i = 0; i < hands; i++)
				for (j = 0; j < ht; j++)
					pattern_state[0][i][j] = 0;
		}

		j = min_to;       /* ensures each state is generated only once */
		for (i = min_value; i < ht; i++) {
			for ( ; j < hands; j++) {
				if (pattern_state[0][j][i] < pattern_rhythm[0][j][i]) {
					pattern_state[0][j][i]++;
					num = gen_patterns(balls_placed + 1, i, j, num);   /* recursion */
					pattern_state[0][j][i]--;
				}
			}
			j = 0;
		}

		return num;
	}
	



		/*  find_ground -- Find the ground state for our rhythm.  Just put  */
		/*                 the balls in the lowest possible slots, with no  */
		/*                 multiplexing.                                    */

	protected void find_ground() throws JuggleException
	{
		int i, j, balls_left;

		balls_left = n;

		for (i = 0; i < hands; i++)       /* clear ground state array */
			for (j = 0; j < ht; j++)
				ground_state[i][j] = 0;

		for (i = 0; (i < ht) && (balls_left != 0); i++) 
			for (j = 0; (j < hands) && (balls_left != 0); j++) 
				if (pattern_rhythm[0][j][i] != 0) {      /* available slots */
					ground_state[j][i] = 1;
					balls_left--;
				}

		if (balls_left != 0)
			throw new JuggleException("Maximum throw value is too small");
	}


	public static void main(String[] args) {
		if (args.length < 3)
			System.out.println(
"JuggleAnim "+JuggleAnim.version+", copyright 2002 by Jack Boyce and others\n\n" +
"This program is free software; you can redistribute it and/or\n" +
"modify it under the terms of the GNU General Public License\n\n" +
"Arguments:  <number of objects> <max. throw> <pattern period> [-options]\n\n" +
"This program finds juggling patterns in a generalized form of siteswap\n" +
"notation.  For a full description of this notation and the program's\n" +
"operation, consult the accompanying documentation files.  All patterns\n" +
"satisfying the given constraints are listed by the program.  Solo asynch-\n" +
"ronous juggling is the default mode.\n\n" +
"Command line options:\n" +
" -s  solo synchronous mode     -m <number>  multiplexing with at most the\n" +
" -p  2 person passing mode          given number of simultaneous throws\n" +
"                               -mf  turn off multiplexing filter\n" +
" -n  show number of patterns   -d <number>  passing communication delay\n" +
" -no print number only         -l <number>  passing leader person number\n" +
" -g  ground state patterns     -x <throw> ..  exclude listed self-throws\n" +
" -ng excited state patterns    -xp <throw> .. exclude listed passes\n" +
" -f  full listing (decompos-   -i <throw> ..  must include listed self-\n" +
"     able patterns too)             throws\n" +
" -se disable starting/ending   -lame  remove '11' seq. in solo asynch mode\n" +
" -simple  list only non-decomposable patterns\n\n" +
"(This code was derived from the program J2 v2.3, written by Jack Boyce in\n" +
"February 1992.)\n"
			);
		else {
			siteswapGenerator ssg = new siteswapGenerator();
			
			try {
				ssg.initGenerate(args);
				ssg.runGenerate(System.out);	// no pattern number limit
			} catch (JuggleException je) {
				System.out.println("Error: "+ je.getMessage());
			}
		}
	}

}


class siteswapControl extends Frame {
				// text fields in control panel
	protected TextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9;	
	protected Checkbox cb1, cb2, cb3, cb4, cb5, cb6;
	protected Checkbox cb7, cb8, cb9, cb12, cb13;
	
	protected final static int border = 10;
	
	public siteswapControl() {
			// set up frame
		super("Siteswap Generator Controls");
		this.setResizable(false);

		GridBagLayout gb = new GridBagLayout();
		
		this.setLayout(gb);
		
		Panel p2 = new Panel();			// top of window
		p2.setLayout(gb);
		Label lab6 = new Label("Balls");
		p2.add(lab6);
		gb.setConstraints(lab6, make_constraints(GridBagConstraints.WEST,1,0,
					new Insets(0,3,0,15)));
		tf1 = new TextField("5", 3);
		p2.add(tf1);
		gb.setConstraints(tf1, make_constraints(GridBagConstraints.EAST,0,0));
		Label lab7 = new Label("Max. throw");
		p2.add(lab7);
		gb.setConstraints(lab7, make_constraints(GridBagConstraints.WEST,3,0,
					new Insets(0,3,0,15)));
		tf2 = new TextField("7", 3);
		p2.add(tf2);
		gb.setConstraints(tf2, make_constraints(GridBagConstraints.EAST,2,0));
		Label lab8 = new Label("Period");
		p2.add(lab8);
		gb.setConstraints(lab8, make_constraints(GridBagConstraints.WEST,5,0,
					new Insets(0,3,0,0)));
		tf3 = new TextField("5", 3);
		p2.add(tf3);
		gb.setConstraints(tf3, make_constraints(GridBagConstraints.EAST,4,0));
		
		
		Panel p6 = new Panel();
		p6.setLayout(gb);
		Label lab9 = new Label("Mode:");
		p6.add(lab9);
		gb.setConstraints(lab9, make_constraints(GridBagConstraints.WEST,0,0,
					new Insets(0,0,0,0)));
		CheckboxGroup cbg1 = new CheckboxGroup();
		cb1 = new Checkbox("Siteswap", cbg1, true);
		p6.add(cb1);
		gb.setConstraints(cb1, make_constraints(GridBagConstraints.WEST,0,1,
					new Insets(0,10,0,0)));
		cb2 = new Checkbox("Synchronous", cbg1, false);
		p6.add(cb2);
		gb.setConstraints(cb2, make_constraints(GridBagConstraints.WEST,0,2,
					new Insets(0,10,0,0)));
		cb3 = new Checkbox("Passing", cbg1, false);
		p6.add(cb3);
		gb.setConstraints(cb3, make_constraints(GridBagConstraints.WEST,0,3,
					new Insets(0,10,0,0)));
		
		Label lab10 = new Label("Compositions:");
		p6.add(lab10);
		gb.setConstraints(lab10, make_constraints(GridBagConstraints.WEST,0,4,
					new Insets(5,0,0,0)));
		CheckboxGroup cbg2 = new CheckboxGroup();
		cb5 = new Checkbox("All", cbg2, false);
		p6.add(cb5);
		gb.setConstraints(cb5, make_constraints(GridBagConstraints.WEST,0,5,
					new Insets(0,10,0,0)));
		cb4 = new Checkbox("Non-obvious", cbg2, true);
		p6.add(cb4);
		gb.setConstraints(cb4, make_constraints(GridBagConstraints.WEST,0,6,
					new Insets(0,10,0,0)));
		cb6 = new Checkbox("None (prime only)", cbg2, false);
		p6.add(cb6);
		gb.setConstraints(cb6, make_constraints(GridBagConstraints.WEST,0,7,
					new Insets(0,10,0,0)));
		
		Panel p8 = new Panel();
		p8.setLayout(gb);
		Label lab11 = new Label("Find:");
		p8.add(lab11);
		gb.setConstraints(lab11, make_constraints(GridBagConstraints.WEST,0,0,
					new Insets(0,0,0,0)));
		cb7 = new Checkbox("Ground state patterns", null, true);
		p8.add(cb7);
		gb.setConstraints(cb7, make_constraints(GridBagConstraints.WEST,0,1,
					new Insets(0,10,0,0)));
		cb8 = new Checkbox("Excited state patterns", null, true);
		p8.add(cb8);
		gb.setConstraints(cb8, make_constraints(GridBagConstraints.WEST,0,2,
					new Insets(0,10,0,0)));
		cb9 = new Checkbox("Transition throws", null, true);
		p8.add(cb9);
		gb.setConstraints(cb9, make_constraints(GridBagConstraints.WEST,0,3,
					new Insets(0,10,0,0)));
		
		Label lab12 = new Label("Multiplexing:");
		p8.add(lab12);
		gb.setConstraints(lab12, make_constraints(GridBagConstraints.WEST,0,4,
					new Insets(5,0,0,0)));
		cb12 = new Checkbox("Enable", null, false);
		p8.add(cb12);
		gb.setConstraints(cb12, make_constraints(GridBagConstraints.WEST,0,5,
					new Insets(0,10,0,0)));

		Panel p3 = new Panel();
		p3.setLayout(gb);
		tf9 = new TextField("2", 3);
		p3.add(tf9);
		gb.setConstraints(tf9, make_constraints(GridBagConstraints.EAST,0,0));
		Label lab13 = new Label("Simultaneous throws");
		p3.add(lab13);
		gb.setConstraints(lab13, make_constraints(GridBagConstraints.WEST,1,0,
					new Insets(0,3,0,0)));
		
		p8.add(p3);
		gb.setConstraints(p3, make_constraints(GridBagConstraints.WEST,0,6,
					new Insets(0,10,0,0)));
					
		cb13 = new Checkbox("No simultaneous catches", null, true);
		p8.add(cb13);
		gb.setConstraints(cb13, make_constraints(GridBagConstraints.WEST,0,7,
					new Insets(0,10,0,0)));
		
		Panel p4 = new Panel();		// whole middle part of window
		p4.setLayout(gb);
		p4.add(p6);
		gb.setConstraints(p6, make_constraints(GridBagConstraints.NORTHEAST,0,0));
		p4.add(p8);
		gb.setConstraints(p8, make_constraints(GridBagConstraints.NORTHWEST,1,0));
		
		Panel p1 = new Panel();
		p1.setLayout(gb);
		tf4 = new TextField(10);
		p1.add(tf4);
		gb.setConstraints(tf4, make_constraints(GridBagConstraints.EAST,0,0));
		tf5 = new TextField(10);
		p1.add(tf5);
		gb.setConstraints(tf5, make_constraints(GridBagConstraints.EAST,0,1));
		tf6 = new TextField(10);
		p1.add(tf6);
		gb.setConstraints(tf6, make_constraints(GridBagConstraints.EAST,0,2));
		tf7 = new TextField("0", 3);
		p1.add(tf7);
		gb.setConstraints(tf7, make_constraints(GridBagConstraints.EAST,0,3,
					new Insets(3,0,0,0)));
		tf8 = new TextField("1", 3);
		p1.add(tf8);
		gb.setConstraints(tf8, make_constraints(GridBagConstraints.EAST,0,4));
		Label lab1 = new Label("Exclude these (self) throws");
		p1.add(lab1);
		gb.setConstraints(lab1, make_constraints(GridBagConstraints.WEST,1,0,
					new Insets(0,3,0,0)));
		Label lab2 = new Label("Include these (self) throws");
		p1.add(lab2);
		gb.setConstraints(lab2, make_constraints(GridBagConstraints.WEST,1,1,
					new Insets(0,3,0,0)));
		Label lab3 = new Label("Exclude these passes");
		p1.add(lab3);
		gb.setConstraints(lab3, make_constraints(GridBagConstraints.WEST,1,2,
					new Insets(0,3,0,0)));
		Label lab4 = new Label("Passing communication delay");
		p1.add(lab4);
		gb.setConstraints(lab4, make_constraints(GridBagConstraints.WEST,1,3,
					new Insets(3,3,0,0)));
		Label lab5 = new Label("Passing leader slot number");
		p1.add(lab5);
		gb.setConstraints(lab5, make_constraints(GridBagConstraints.WEST,1,4,
					new Insets(0,3,0,0)));


		this.add(p2);		// now make the whole window
		gb.setConstraints(p2, make_constraints(GridBagConstraints.CENTER,0,0,
					new Insets(border,border,5,border)));
		this.add(p4);
		gb.setConstraints(p4, make_constraints(GridBagConstraints.CENTER,0,1,
					new Insets(5,border,5,border)));
		this.add(p1);
		gb.setConstraints(p1, make_constraints(GridBagConstraints.CENTER,0,2,
					new Insets(5,border,border,border)));
		
		this.pack();
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				setVisible(false);
			}
		});
	}
	
	protected GridBagConstraints make_constraints(int location, int gridx, int gridy) {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = location;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = gbc.weighty = 0.0;
		return gbc;
	}
	
	protected GridBagConstraints make_constraints(int location, int gridx, int gridy,
					Insets ins) {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = location;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = ins;
		gbc.weightx = gbc.weighty = 0.0;
		return gbc;
	}
	
	public String getParams() {
		StringBuffer sb = new StringBuffer(256);
		
		sb.append(tf1.getText() + " " + tf2.getText() + " " + tf3.getText());
		
		if (cb2.getState())
			sb.append(" -s");
		else if (cb3.getState()) {
			sb.append(" -p");
			if (tf6.getText().length() > 0)
				sb.append(" -xp " + tf6.getText());
			if (tf7.getText().length() > 0)
				sb.append(" -d " + tf7.getText());
			if (tf8.getText().length() > 0)
				sb.append(" -l " + tf8.getText());
		}
		
		if (cb5.getState())
			sb.append(" -f");
		else if (cb6.getState())
			sb.append(" -simple");
		
		if (cb7.getState() && !cb8.getState())
			sb.append(" -g");
		else if (!cb7.getState() && cb8.getState())
			sb.append(" -ng");
		
		if (!cb9.getState())
			sb.append(" -se");
			
		if (cb12.getState() && (tf9.getText().length() > 0)) {
			sb.append(" -m " + tf9.getText());
			if (!cb13.getState())
				sb.append(" -mf");
		}
		
		if (tf4.getText().length() > 0)
			sb.append(" -x " + tf4.getText());
		if (tf5.getText().length() > 0)
			sb.append(" -i " + tf5.getText());
			
		return sb.toString();
	}
}
