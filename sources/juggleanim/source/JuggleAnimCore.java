// JuggleAnimCore.java
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
   import java.util.*;
	import java.lang.reflect.*;
	

// This applet animates a juggling pattern by making a "movie" of the pattern
// and then looping through it, drawing the frames repeatedly.  This movie
// (an instance of the JuggleMovie class) is just an explicit list of screen
// coordinates for all the objects in the scene; since none of the pattern
// layout and calculation is done on the fly, the animation is fairly fast.
//
// The real work, of course, lies in building the JuggleMovie object.  I do this
// in as simple and general-purpose a manner as possible by dividing the task
// into three parts, as follows:
//
//    1.  Parse the pattern, and layout the balls to provide a list of instructions
//        such as "ball 1 is thrown by hand 0 at t=1.2 seconds and is caught by
//        hand 1 at t=2.5 seconds", etc.  Note that none of the spatial information
//        has been layed out yet.  You can specify different ball path types (e.g.
//        bounce juggling, etc.) just by extending the "BallPath" class.
//
//    2.  Lay out the positions of the hands as a function of time.  After this
//        stage, we can convert the ball-path instructions from step 1 above into
//        actual paths, since we now know where the hands are at the catching and
//        throwing times.  After telling each BallPath object in the list where
//        its starting and ending positions are, the objects in the list are
//        individually responsible for calculating ball positions at all times
//        between these endpoints defined by the hands.  (A BallPathAir object
//        uses a parabolic path in space, for example.)
//
//    3.  Draw the juggler, which is here assumed to be just a function of the
//        positions of its hands.


// Each of these steps is accomplished using an instance of one of the following
// classes, in order:

    abstract class PhysicalPattern {
      protected PhysicalConst	con = null;
      protected BallPathList	bpl = null;
   
       public void				setConstants(PhysicalConst c) {
         con = new PhysicalConst(c);
      }
   
       public PhysicalConst	getConstants() {
         return new PhysicalConst(con);
      }
   
       public BallPathList		getBallPathList()	{ 
         return bpl; }
   
       public abstract int		getStartupThrows();		// undefined here
       public abstract int		getLoopThrows();
       public abstract int		getMaxThrow();
	   public abstract int[]	getPathPerm();
       public abstract void	layoutPattern(int startup_throws, int loop_throws,
				double tps) throws JuggleException;
   }


    abstract class PhysicalHands {
      protected PhysicalConst	con = null;
      protected BallPathList	hpl = null;
   
       public void				setConstants(PhysicalConst c) {
         con = new PhysicalConst(c);
      }
   
       public PhysicalConst	getConstants() {
         return new PhysicalConst(con);
      }
   
       public BallPathList		getHandPathList()	{ 
         return hpl; }
   
       public abstract int		getStartupThrows();		// undefined
       public abstract int		getLoopThrows();
       public abstract int		getHands();
       public abstract void	layoutHands(BallPathList bpl, double time) throws JuggleException;
   
   }


    class PhysicalJuggler {
      protected Coordinate	left = null, right = null;
   
       public void		placeHands(Coordinate right, Coordinate left) {
         this.left = new Coordinate(left);
         this.right = new Coordinate(right);
      }
   
       public int		getMaxLines() 		{ 
         return 0; }
       public int		getMaxEllipses()	{ 
         return 0; }
       public Vector	getLines() 			{ 
         return null; }
       public Vector	getEllipses()		{ 
         return null; }
   }


// And the following class groups everything together into a cohesive unit:

    class PhysicalGroup {
      protected PhysicalPattern	pat = null;
      protected PhysicalHands		hands = null;
      protected PhysicalJuggler	jug = null;
      protected PhysicalConst		con = null;
      protected int				startup_throws, loop_throws;
      protected Color[]	   ballsColors;
      protected int		numbercolors;
		protected int[] pathperm;
		
   
       PhysicalGroup(PhysicalPattern pat, PhysicalHands hands, PhysicalJuggler jug,
					PhysicalConst con, String colors) throws JuggleException {
         int pat_period, hand_period;
      
         this.pat = pat;
         this.hands = hands;
         this.jug = jug;
         this.con = con;
      
         pat.setConstants(con);
         hands.setConstants(con);
         startup_throws = Math.max(pat.getStartupThrows(), hands.getStartupThrows());
		 
         pat_period = pat.getLoopThrows();
         hand_period = hands.getLoopThrows();
		loop_throws = pat_period * hand_period / gcd(pat_period, hand_period);
     
         pat.layoutPattern(startup_throws, loop_throws, con.tps);
         hands.layoutHands(pat.getBallPathList(), 
            (startup_throws + loop_throws + pat.getMaxThrow() + 2) / con.tps);
         pat.getBallPathList().fixBalls(hands.getHandPathList());
		 
		 assignColors(colors);
      }
   
		// The following function calculates how many times we need to cycle
		// through the movie loop to cycle the ball colors back to their
		// starting point.  This is used to save animated GIFs.
		public int getLoopsToRepeat() {
			int[] perm = pat.getPathPerm();
			int result = 1;
			int balls = this.getBallPathList().getActors();
			boolean[] done = new boolean[balls];
			
			for (int i = 0; i < balls; i++) {
				if (done[i] == false) {
					done[i] = true;
					int path = i;
					int per = 0;
					
					do {
						path = perm[path];
						done[path] = true;
						per++;
					} while (path != i);
					
					Color[] colors = new Color[per];
					path = i;
					for (int j = 0; j < per; j++) {
						colors[j] = ballsColors[path];
						path = perm[path];
					}
					
					int cycleperm = per;
					outer:
					for (int j = 1; j < per; j++) {
						for (int k = 0; k < per; k++) {
							if (colors[k] != colors[(k+j) % per])
								continue outer;
						}
						cycleperm = j;
						break;
					}
					
					result = result * cycleperm / gcd(result, cycleperm);
				}
			}
			
			return result;
		}
		
		
       public BallPathList		getBallPathList()	{ 
         return pat.getBallPathList(); }
       public BallPathList		getHandPathList()	{ 
         return hands.getHandPathList(); }
       public PhysicalJuggler	getJuggler()		{ 
         return jug; }
   
       protected static int	gcd(int x, int y) {		// Euclid's algorithm (x>0, y>0)
         int	g = y;
      
         while (x > 0) {
            g = x;
            x = y % x;
            y = g;
         }
         return g;
      }
	  
	  protected void assignColors(String colors) {
		int actors = this.getBallPathList().getActors();
		
      // Create colors for balls
      // If colors parameter was defined
         if (colors != null)
         {
            StringTokenizer	st1 = new StringTokenizer(colors, "}", false);
            StringTokenizer	st2 = null;
            String				st = null;
            
			ballsColors = new Color[actors];
      
			this.numbercolors = st1.countTokens();
			if (numbercolors > actors)
				numbercolors = actors;
				
            try
            {
            // Parse the colors parameter
               for (int i = 0; i < numbercolors; i++)
               {
               // Look for next {...} block
                  st = st1.nextToken().replace('{', ' ').trim();
               
               // Parse commas
                  st2 = new StringTokenizer(st, ",", false);
               
					switch (st2.countTokens()) {
						case 1:
							// Use the value as a color name
							String name = st2.nextToken().trim().toLowerCase();
// System.out.println("looking for color named \""+name+"\"");
							try {
								Field f = Color.class.getField(name);
								ballsColors[i] = (Color)f.get(null);
							}
							catch (Exception e) {
							}
							if (ballsColors[i] == null) {
// System.out.println("...not found");
								ballsColors[i] = Color.red;
							}
							break;
						case 3:
							// Use the three values as RGB values
							ballsColors[i] = new Color(
								Integer.valueOf(st2.nextToken().trim()).intValue(),
								Integer.valueOf(st2.nextToken().trim()).intValue(),
								Integer.valueOf(st2.nextToken().trim()).intValue()
							);
							break;
						default:
							throw new JuggleException();
					}
               }
            }
            // If something goes wrong, reset colors so we can use default one
                catch (NumberFormatException e)
               {
                  ballsColors=null;
               } 
                catch (NoSuchElementException e)
               {
                  ballsColors=null;
               }
                catch (JuggleException e)
               {
                  ballsColors=null;
               }
			   
         // If all went good but some balls have no color, define their color
            if (ballsColors!=null && (numbercolors < actors))
				for (int i = numbercolors; i < actors; i++)
					ballsColors[i] = ballsColors[i - numbercolors];
         }
      
      // If no color was defined (or there was an error in their definition) apply default ones
         if (ballsColors == null) {
            Color[] defaultBallsColors = new Color[] {		// Colors for the balls
                  new Color(255,0,0),		// Red
                  new Color(0,170,0),		// Dark green
                  new Color(0,0,255),		// Blue
                  new Color(255,255,0),	// Yellow
                  new Color(0,255,255),	// Cyan
                  new Color(255,0,255),	// Magenta
                  new Color(255,128,0),	// Orange
                  new Color(128,255,0),	// Light green
                  new Color(255,0,0),		// Red
                  new Color(0,170,0),		// Dark green
                  new Color(0,0,255),		// Blue
                  new Color(255,255,0),	// Yellow
                  new Color(0,255,255),	// Cyan
                  new Color(255,0,255),	// Magenta
                  new Color(255,128,0),	// Orange
               };
			
			ballsColors = new Color[actors];
			for (int i = 0; i < actors; i++) {
				ballsColors[i] = defaultBallsColors[i % defaultBallsColors.length];
			}
			numbercolors = (actors > defaultBallsColors.length) ? defaultBallsColors.length : actors;
		}
	  }
	  
		public int getNumberOfColors() {
			return numbercolors;
		}
		
		public Color getBallColor(int ballnum) {
			if ((ballnum < 0) || (ballnum > this.getBallPathList().getActors()))
				return null;
				
			return ballsColors[ballnum];
		}
		
		public Color[] getColors() {
			Color[] result = new Color[getNumberOfColors()];
			
			for (int i = 0; i < getNumberOfColors(); i++)
				result[i] = ballsColors[i];
				
			return result;
		}
   }


// The following class is used to control the applet from outside (another applet).

    class JuggleControls {
      public String			inputstring;
      public String			modestring, patstring;
      public String        colors;
      public Image			ballimage = null;
      public double			accel = 9.8;
      public double			tps = 4.5;
      public double			dwell = 1.0;
      public double			balldiam = 0.1;
      public double			bouncefrac = 0.9;
      public double			slowdown = 1.0;
      public double			fps = 20.0;
      public double			handscoop = 0.2;
      public int				border = 0;
      public boolean			startPause = false;
      public boolean			dbuffer = true;
      public double[][]		handpath = null;
      public double			mat_hr = -1.0;	// for emulating Ken Matsuoka's format
   
       JuggleControls() { super(); }
   
       JuggleControls(JuggleControls jc) {
         if (jc.inputstring != null)		this.inputstring = jc.inputstring;
         if (jc.modestring != null)		this.modestring = jc.modestring;
         if (jc.colors != null)		   this.colors = jc.colors;
         if (jc.patstring != null)		this.patstring = jc.patstring;
         if (jc.ballimage != null)		this.ballimage = jc.ballimage;
         if (jc.accel >= 0.0)			this.accel = jc.accel;
         if (jc.tps >= 0.0)				this.tps = jc.tps;
         if (jc.dwell >= 0.0)			this.dwell = jc.dwell;
         if (jc.balldiam >= 0.0)			this.balldiam = jc.balldiam;
         if (jc.bouncefrac >= 0.0)		this.bouncefrac = jc.bouncefrac;
         if (jc.slowdown >= 0.0)			this.balldiam = jc.balldiam;
         if (jc.fps >= 0.0)				this.fps = jc.fps;
         this.handscoop = jc.handscoop;
         if (jc.border >= 0)				this.border = jc.border;
         this.startPause = jc.startPause;
         this.dbuffer = jc.dbuffer;
         if (jc.handpath != null)		this.handpath = jc.handpath;
         if (jc.mat_hr >= 0.0)			this.mat_hr = jc.mat_hr;
      }
   }



// Simple container class

    class Coordinate {
      public double	x, y, z;
   
       Coordinate(double x, double y) {
         this(x,y,0.0);
      }
   
       Coordinate(double x, double y, double z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }
   
       Coordinate(Coordinate c) {
         this.x = c.x;
         this.y = c.y;
         this.z = c.z;
      }
   
       public double getIndex(int index) {
         if (index == 0)			
            return x;
         else if (index == 1)	
            return y;
         else					
            return z;
      }
   
       public static Coordinate max(Coordinate coord1, Coordinate coord2) {
         return new Coordinate(Math.max(coord1.x, coord2.x),
            Math.max(coord1.y, coord2.y),
            Math.max(coord1.z, coord2.z));
      }
   
       public static Coordinate min(Coordinate coord1, Coordinate coord2) {
         return new Coordinate(Math.min(coord1.x, coord2.x),
            Math.min(coord1.y, coord2.y),
            Math.min(coord1.z, coord2.z));
      }
   }



// This class is used to describe the motion of a ball (or hand, or ...)
// from point (x0, y0) at one time to point (x1, y1) at a later time.  You
// define the path from time 0 to time t, and then use translateTime() to
// shift the time axis as preferred.  These objects are used by BallPathList
// to define a spacetime diagram for the entire pattern.
//
// Note that all distances are in meters and all times are in seconds.

    abstract class BallPath {
      protected int			start_hand, end_hand;
      protected double		start_time, end_time;
      protected Coordinate	start_coord = null, end_coord = null;
   
       public void	setPath(int end_hand, double t) {
         this.start_time = 0.0;
         this.end_time = t;
         this.start_hand = start_hand;
         this.end_hand = end_hand;
      }
   
       public void	translateTime(double deltat) {
         start_time += deltat;
         end_time += deltat;
      }
   
       public void fixBall(BallPathList hpl) throws JuggleException {
         start_coord = hpl.getCoord(start_hand, start_time);
         end_coord = hpl.getCoord(end_hand, end_time);
         fixInternalVariables();
      }
   
       public int			getStartHand()			{ 
         return start_hand; }
       public void			setStartHand(int sh)	{ start_hand = sh; }
       public int			getEndHand()			{ 
         return end_hand; }
       public double		getStartTime()			{ 
         return start_time; }
       public double		getEndTime()			{ 
         return end_time; }
       public double		getDuration()			{ 
         return (end_time-start_time); }
   
       protected abstract void		fixInternalVariables();	// undefined methods
       public abstract Coordinate 	getCoord(double time);
       public abstract Coordinate 	getMax();				// for screen layout purposes
       public abstract Coordinate 	getMin();
   }



// This is a straight-line, constant velocity path

    class BallPathLine extends BallPath {
      protected double	bx, cx;
      protected double	by, cy;
   
       protected void	fixInternalVariables() {
         cx = start_coord.x;
         cy = end_coord.x;
         bx = (end_coord.x - cx) / getDuration();
         by = (end_coord.y - cy) / getDuration();
      }
   
       public Coordinate getCoord(double time) {
         if ((time < start_time) || (time > end_time))
            return null;
         time -= start_time;
         return new Coordinate(cx+bx*time, cy+by*time);
      }
   
       public Coordinate getMax() {
         double	x, y;
      
         x = (bx > 0.0) ? (cx+bx*getDuration()) : cx;
         y = (by > 0.0) ? (cy+by*getDuration()) : cy;
         return new Coordinate(x, y);
      }
   
       public Coordinate getMin() {
         double	x, y;
      
         x = (bx > 0.0) ? cx : (cx+bx*getDuration());
         y = (by > 0.0) ? cy : (cy+by*getDuration());
         return new Coordinate(x, y);
      }
   }



// This is a path in the air

    class BallPathAir extends BallPath {
      protected double	bx, cx;
      protected double	ay, by, cy;
   
       BallPathAir(double accel) {
         ay = 0.5 * accel;
      }
   
       protected void	fixInternalVariables() {
         double	t = getDuration();
      
         if ((start_coord != null) && (end_coord != null)) {
            cx = start_coord.x;
            bx = (end_coord.x - start_coord.x) / t;
            cy = start_coord.y;
            by = (end_coord.y - start_coord.y) / t - ay * t;
         }
      }
   
       public Coordinate getCoord(double time) {
         if ((time < start_time) || (time > end_time))
            return null;
         time -= start_time;
         return new Coordinate(cx+bx*time, cy+time*(by+ay*time));
      }
   
       public Coordinate getMax() {
         double	x, y;
         double	duration = getDuration();
         double	temp = -by / (2.0 * ay);
      
         x = (bx > 0.0) ? (cx+bx*duration) : cx;
      
         if ((ay > 0.0) || (temp < 0.0) || (temp > duration))
            y = Math.max(cy, cy+duration*(by+ay*duration));
         else
            y = cy+temp*(by+ay*temp);
         return new Coordinate(x, y);
      }
   
       public Coordinate getMin() {
         double	x, y;
         double	duration = getDuration();
         double	temp = -by / (2.0 * ay);
      
         x = (bx > 0.0) ? cx : (cx+bx*duration);
      
         if ((ay < 0.0) || (temp < 0.0) || (temp > duration))
            y = Math.min(cy, cy+duration*(by+ay*duration));
         else
            y = cy+temp*(by+ay*temp);
         return new Coordinate(x, y);
      }
   }


// This is a bounced ball.

    class BallPathBounce extends BallPath {
      protected double	bx, cx;
      protected double	ay1, by1, cy1;		// before the bounce
      protected double	ay2, by2, cy2;		// after the bounce
      protected double	bounceplane;
      protected double	bouncefracsqrt;
      protected boolean	forced;				// true -> forced bounce
      protected double	bouncetime;
   
       BallPathBounce(double accel, double bounceplane, double bouncefrac, boolean forced) {
         ay1 = ay2 = 0.5 * accel;
         this.bounceplane = bounceplane;
         try {
            this.bouncefracsqrt = Math.sqrt(bouncefrac);
         } 
             catch (ArithmeticException e) {
               this.bouncefracsqrt = 1.0;
            }
         this.forced = forced;
      }
   
   	// The next function does all the real work of figuring out the ball's
   	// path.  It solves a cubic equation to find the time when the ball hits
   	// the ground
       protected void	fixInternalVariables() {
         double	t2 = getDuration();
         double	k0, k1, k2;
         double	q, r;
      
         if ((start_coord != null) && (end_coord != null)) {
         	// First do the x coordinate.  This is simple.
            cx = start_coord.x;
            bx = (end_coord.x - start_coord.x) / t2;
         
            cy1 = start_coord.y;
         
         	// The following are the coefficients of the cubic equation:
         	//     bouncetime^3 + k2 * bouncetime^2 + k1 * bouncetime + k0 = 0
            k2 = -t2*(2*ay2+bouncefracsqrt*ay1)/(ay2+bouncefracsqrt*ay1);
            k1 = (ay2*t2*t2+bounceplane-end_coord.y+bouncefracsqrt*(bounceplane-cy1))/
               (ay2+bouncefracsqrt*ay1);
            k0 = -t2*bouncefracsqrt*(bounceplane-cy1)/(ay2+bouncefracsqrt*ay1);
         	// Now look at the nature of the solution(s).  There is either one
         	// or three real roots.
            q = k1/3.0 - k2*k2/9.0;
            r = k1*k2/6.0 - k0/2.0 - k2*k2*k2/27.0;
         
            if ((q*q*q + r*r) > 0.0) {
            	// only one real root.  Find it.
               bouncetime = findRoot(k0, k1, k2, 0.0, t2);
            } 
            else {
            	// three real roots.  Decide which one to pick by looking
            	// at the "forced" variable.  Bracket the desired root by
            	// finding the extrema of our cubic.
               q = -k2 / 3.0;
               try {
                  r = Math.sqrt(k2*k2-3.0*k1) / 3.0;
               } 
                   catch (ArithmeticException e) {
                  // this should never happen, since extrema should be real
                     r = 0.0;
                  }
               if (forced)
                  bouncetime = findRoot(k0, k1, k2, 0.0, q-r);
               else
                  bouncetime = findRoot(k0, k1, k2, q-r, q+r);
            }
         
         	// Now we've solved for bouncetime.  The rest is simple.
            by1 = -ay1*bouncetime + (bounceplane-cy1)/bouncetime;
            by2 = -2*ay2*bouncetime - bouncefracsqrt*ay1*bouncetime
               -bouncefracsqrt*(bounceplane-cy1)/bouncetime;
            cy2 = bounceplane - ay2*bouncetime*bouncetime - by2*bouncetime;
         }
      }
   
       static protected double findRoot(double k0, double k1, double k2, double t1, double t2) {
         double	val1, val2, valtemp, t;
      
         val1 = ((t1+k2)*t1+k1)*t1+k0;
         val2 = ((t2+k2)*t2+k1)*t2+k0;
      
         if (val1*val2 > 0.0)
            return (t1+t2)/2.0;			// should never happen!
      
         while (Math.abs(t1-t2) > 0.000001) {
            t = (t1 + t2) / 2.0;
            valtemp = ((t+k2)*t+k1)*t+k0;
            if (valtemp*val1 > 0.0) {
               t1 = t;
               val1 = valtemp;
            } 
            else {
               t2 = t;
               val2 = valtemp;
            }
         }
         return t1;
      }
   
   
       public Coordinate getCoord(double time) {
         if ((time < start_time) || (time > end_time))
            return null;
         time -= start_time;
      
         double ypos;
      
         if (time < bouncetime)
            ypos = cy1 + time*(by1 + ay1*time);
         else
            ypos = cy2 + time*(by2 + ay2*time);
         return new Coordinate(cx+bx*time, ypos);
      }
   
       public Coordinate getMax() {
         double	x, y1, y2;
         double	duration = getDuration();
         double	temp = -by1 / (2.0 * ay1);
      
         x = (bx > 0.0) ? (cx+bx*duration) : cx;
      
         if ((ay1 > 0.0) || (temp < 0.0) || (temp > bouncetime))
            y1 = Math.max(cy1, cy1+bouncetime*(by1+ay1*bouncetime));
         else
            y1 = cy1+temp*(by1+ay1*temp);
      
         temp = -by2 / (2.0 * ay2);
         if ((ay2 > 0.0) || (temp < bouncetime) || (temp > duration))
            y2 = Math.max(cy2+bouncetime*(by2+ay2*bouncetime),
               cy2+duration*(by2+ay2*duration));
         else
            y2 = cy2+temp*(by2+ay2*temp);
      
         return new Coordinate(x, Math.max(y1, y2));
      }
   
       public Coordinate getMin() {
         double	x, y1, y2;
         double	duration = getDuration();
         double	temp = -by1 / (2.0 * ay1);
      
         x = (bx > 0.0) ? cx : (cx+bx*duration);
      
         if ((ay1 < 0.0) || (temp < 0.0) || (temp > bouncetime))
            y1 = Math.min(cy1, cy1+bouncetime*(by1+ay1*bouncetime));
         else
            y1 = cy1+temp*(by1+ay1*temp);
      
         temp = -by2 / (2.0 * ay2);
         if ((ay2 < 0.0) || (temp < bouncetime) || (temp > duration))
            y2 = Math.min(cy2+bouncetime*(by2+ay2*bouncetime),
               cy2+duration*(by2+ay2*duration));
         else
            y2 = cy2+temp*(by2+ay2*temp);
      
         return new Coordinate(x, Math.min(y1, y2));
      }
   }


// This is a ball's path as it's carried in a hand, which just locks the ball
// to the hand.

    class BallPathHand extends BallPath {
      protected BallPathList	hpl;
   
       public void 		fixBall(BallPathList hpl) throws JuggleException {
         this.hpl = hpl;
      }
   
       protected void		fixInternalVariables() {}		// unused
   
       public Coordinate 	getCoord(double time) {
         if ((time >= start_time) && (time <= end_time))
            return hpl.getCoord(start_hand, time);
         return null;
      }
   
       public Coordinate 	getMax() {
         return hpl.getMax(start_hand, start_time, end_time);
      }
   
       public Coordinate 	getMin() {
         return hpl.getMin(start_hand, start_time, end_time);
      }
   }


// The next class trivially extends the above case to describe the period of time
// when a ball is considered "held", as opposed to that when it is "scooped".
// See BallPathList.findNextThrow() below.

    class BallPathHold extends BallPathHand {
   
   }


// This is the path of a hand.  Some of the BallPath variables are ignored
// here.

    abstract class HandPath extends BallPath {
   	// use a different form of setPath() for hands.
       public void	setPath(Coordinate start, Coordinate end, double t) {
         this.start_time = 0.0;
         this.end_time = t;
         this.start_coord = start;
         this.end_coord = end;
         fixInternalVariables();
      }
   	// next method is empty, since path is already determined by setPath()
       public void	fixBall(BallPathList hpl) throws JuggleException {}
   
   // getCoord(), getMax(), getMin(), fixInternalVariables() are undefined
   }



// This next class creates a list of these BallPath objects to describe a
// set of objects as they move through spacetime along different paths.

    class BallPathList {
      protected int		actors;
      protected double	startup_time;
      protected double	loop_time;
      protected Vector[]	paths;
      protected double[]	current_times;
      protected int[]		current_hands;
	  
   
       BallPathList(int actors, double startup_time, double loop_time) {
         this.actors = actors;
         this.startup_time = startup_time;
         this.loop_time = loop_time;
         paths = new Vector[actors];
         for (int i = 0; i < actors; i++)
            paths[i] = new Vector(10, 10);
         current_times = new double[actors];
         current_hands = new int[actors];
      }
   
       public void setStartPoint(int actor, int hand, double time) throws JuggleException {
         if (paths[actor].size() > 0)
            throw new JuggleException("Can't set time on nonempty path list");
         current_hands[actor] = hand;
         current_times[actor] = time;
      }
   
   
       public void	addPath(int actor, BallPath bp) {
         bp.translateTime(current_times[actor]);
         bp.setStartHand(current_hands[actor]);
         current_hands[actor] = bp.getEndHand();
         current_times[actor] += bp.getDuration();
         paths[actor].addElement(bp);
      }
   
   
       public Coordinate getCoord(int actor, double time) {
         int			i;
         Coordinate	coord = null;
         BallPath	bp = null;
      
         for (i = 0; i < paths[actor].size(); i++) {
            bp = (BallPath)paths[actor].elementAt(i);
            coord = bp.getCoord(time);
            if (coord != null)
               return coord;
         }
         return null;
      }
   
   
       public Coordinate getMax() {
         BallPath	bp;
         Coordinate	temp = new Coordinate(0.0, 0.0), tempmax = null;
      
         for (int i = 0; i < actors; i++) {
            for (int j = 0; j < paths[i].size(); j++) {
               bp = (BallPath)paths[i].elementAt(j);
               tempmax = bp.getMax();
               if (tempmax != null)
                  temp = Coordinate.max(temp, tempmax);
            }
         }
         return temp;
      }
   
   
       public Coordinate getMin() {
         BallPath	bp;
         Coordinate	temp = new Coordinate(0.0, 0.0), tempmin = null;
      
         for (int i = 0; i < actors; i++) {
            for (int j = 0; j < paths[i].size(); j++) {
               bp = (BallPath)paths[i].elementAt(j);
               tempmin = bp.getMin();
               if (tempmin != null)
                  temp = Coordinate.min(temp, tempmin);
            }
         }
         return temp;
      }
   
   
   	// The next two methods don't do exactly what we'd like, which is to
   	// find the max and min coordinates of some path over an interval
   	// (start, end).  These definitions extend the interval to the nearest
   	// ball path endpoint.
       public Coordinate getMax(int actor, double start, double end) {
         BallPath	bp = null;
         Coordinate	coord = null, coordmax = null;
         int			i = 0, j;
      
         if (paths[actor].size() == 0)
            return new Coordinate(0.0, 0.0);
         do {
            bp = (BallPath)paths[actor].elementAt(i++);
            coord = bp.getCoord(start);
         } while ((i < paths[actor].size()) && (coord == null));
      
         if (coord == null)
            return new Coordinate(0.0, 0.0);
         j = i;
         coordmax = coord;
      
         while ((++j < paths[actor].size()) && (coord != null)) {
            bp = (BallPath)paths[actor].elementAt(j);
            coord = bp.getCoord(end);
            if (coord != null)
               coordmax = Coordinate.max(coord, coordmax);
         }
         return coordmax;
      }
   
   
       public Coordinate getMin(int actor, double start, double end) {
         BallPath	bp = null;
         Coordinate	coord = null, coordmin = null;
         int			i = 0, j;
      
         if (paths[actor].size() == 0)
            return new Coordinate(0.0, 0.0);
         do {
            bp = (BallPath)paths[actor].elementAt(i++);
            coord = bp.getCoord(start);
         } while ((i < paths[actor].size()) && (coord == null));
      
         if (coord == null)
            return new Coordinate(0.0, 0.0);
         j = i;
         coordmin = coord;
      
         while ((++j < paths[actor].size()) && (coord != null)) {
            bp = (BallPath)paths[actor].elementAt(j);
            coord = bp.getCoord(end);
            if (coord != null)
               coordmin = Coordinate.min(coord, coordmin);
         }
         return coordmin;
      }
   
   
       public void		fixBalls(BallPathList hpl) throws JuggleException {
         int		i, j;
      
         for (i = 0; i < actors; i++) {
            for (j = 0; j < paths[i].size(); j++)
               ((BallPath)paths[i].elementAt(j)).fixBall(hpl);
         }
      }
   
       public int 		getActors() 		{ 
         return actors; }
       public double 	getStartupTime() 	{ 
         return startup_time; }
       public double 	getLoopTime() 		{ 
         return loop_time; }
   
   
   	// The following method returns the next BallPath describing a throw
   	// out of the given hand (after the specified time).  The returned
   	// BallPath's getStartTime() and getDuration() methods can then be used
   	// to get timing information.  This method is useful mainly for
   	// implementing PhysicalHands objects.
       public BallPath	findNextThrow(int hand, double time, boolean holds) {
         BallPath	bp = null, bptemp = null;
         double		mintime = 0.0, delta;
         int			i, j;
      
         for (i = 0; i < actors; i++) {
            for (j = 0; j < paths[i].size(); j++) {
               bptemp = (BallPath)paths[i].elementAt(j);
               if (bptemp.getStartHand() != hand)
                  continue;
               if (holds) {
                  if ((bptemp instanceof BallPathHand) &&
                  (!(bptemp instanceof BallPathHold)))
                     continue;
               } 
               else {
                  if (bptemp instanceof BallPathHand)
                     continue;
               }
            
               delta = bptemp.getStartTime();
               if (delta > startup_time)
                  while (delta < time)
                     delta += loop_time;
               delta -= time;
               if (delta >= 0.0)
                  if ((delta < mintime) || (bp == null)) {
                     bp = bptemp;
                     mintime = delta;
                  }
            }
         }
      
         return bp;
      }
   
   }




// This class contains common constants that nearly all PhysicalPatterns
// will need.  Some fields may be ignored by certain types of pattern.

    class PhysicalConst {
      public double	g;				// acceleration of gravity (m/s^2)
      public double	tps;			// throws per second (average)
      public double	dwell;			// number of throw time units to spend in hand
      public double	handscoop;		// hand scoop below catch/throw pos. (m)
      public double	balldiam;		// ball diameter (m)
      public double	bouncefrac;		// fraction of energy ball retains after bounce
   
       PhysicalConst(double g, double tps, double dwell, double hs, double balldiam,
       double bouncefrac) {
         this.g = g;
         this.tps = tps;
         this.dwell = dwell;
         this.handscoop = hs;
         this.balldiam = balldiam;
         this.bouncefrac = bouncefrac;
      }
   
       PhysicalConst(PhysicalConst c)  {		// copy constructor
         this.g = c.g;
         this.tps = c.tps;
         this.dwell = c.dwell;
         this.handscoop = c.handscoop;
         this.balldiam = c.balldiam;
         this.bouncefrac = c.bouncefrac;
      }
   }



// This class represents a physical instance of an asynchronous (normal) site
// swap juggling pattern.

    class PhysicalAsyncSiteSwap extends PhysicalPattern {
      protected int[][]			pattern;			// throw values
      protected char[][]		mods;				// throw modifiers
      protected int[]			pattern_throws;		// # of throws
      protected int[][]			states;
	  protected int[]			pathperm;
      protected int				balls;
      protected int				max_throw = 0;
      protected int				length;
      protected int				max_occupancy = 1;
	  protected int				startup_throws, loop_throws;
   
   
       PhysicalAsyncSiteSwap(String pat) throws JuggleException {
         int			i, j, k, sum = 0;
         int			pat_index, mult_index;
         int			last_pat_index, last_mult_index;
         boolean		in_braces, mod_ok;
         char[]		input = pat.toCharArray();
         char		ch;
      
         for (i = 0; i < 2; i++) {
            pat_index = mult_index = 0;
            last_pat_index = last_mult_index = 0;
            in_braces = false;
            mod_ok = false;
         
            for (j = 0; j < input.length; j++) {
               ch = input[j];
            
               if (Character.isDigit(ch) || Character.isLowerCase(ch)) {
                  k = Character.digit(ch, 36);
                  if ((i == 1) && (k > 0)) {
                     pattern[pat_index][mult_index] = k;
                     pattern_throws[pat_index]++;
                     last_pat_index = pat_index;
                     last_mult_index = mult_index;
                     mod_ok = true;
                  }
                  if (in_braces) {
                     if (k > 0)
                        mult_index++;
                  } 
                  else
                     pat_index++;
               } 
               else if (Character.isUpperCase(ch)) {
                  if (i == 1) {
                     if (mod_ok)
                        mods[last_pat_index][last_mult_index] = ch;
                     else
                        throw new JuggleException("Misplaced throw modifier in pattern");
                     mod_ok = false;
                  }
               } 
               else if (ch == '[') {
                  if (in_braces)
                     throw new JuggleException("Mismatched brackets in pattern");
                  in_braces = true;
                  mod_ok = false;
               } 
               else if (ch == ']') {
                  if (!in_braces)
                     throw new JuggleException("Mismatched brackets in pattern");
                  if (mult_index > max_occupancy)
                     max_occupancy = mult_index;
                  pat_index++;
                  mult_index = 0;
                  in_braces = false;
                  mod_ok = false;
               }
            }
         
            if (in_braces)
               throw new JuggleException("Mismatched brackets in pattern");
         
            if (i == 0) {
               this.length = pat_index;
               pattern = new int[pat_index][max_occupancy];
               mods = new char[pat_index][max_occupancy];
               pattern_throws = new int[pat_index];
            }
         }
      
		// now build the states[] array
         try {
            for (i = 0; i < length; i++) {
               for (j = 0; j < max_occupancy; j++) {
                  sum += pattern[i][j];
                  if (pattern[i][j] > max_throw)
                     max_throw = pattern[i][j];
               }
            }
         
            states = new int[length+1][max_throw];
			
            if ((sum % length) != 0)
               throw new JuggleException();
            balls = sum / length;
         
            for (i = 0; i < max_throw; i++) {
               k = (length - 1) - i % length;
               for (j = 0; j < pattern_throws[k]; j++) {
                  sum = pattern[k][j];		// sum is just a temp variable
                  if (sum > i)
                     states[0][sum-i-1]++;
               }
            }

            if (states[0][0] != pattern_throws[0])
               throw new JuggleException();
         
            for (i = 1; i <= length; i++) {
               for (j = 1; j < max_throw; j++)
                  states[i][j-1] = states[i-1][j];
				
               states[i][max_throw-1] = 0;
				
               for (j = 0; j < pattern_throws[i-1]; j++) {
                  k = pattern[i-1][j];
                  if (k > 0)
                     states[i][k-1]++;
               }

               if ((i < length) && (states[i][0] != pattern_throws[i]))
                  throw new JuggleException();
            }
         
         } 
             catch (JuggleException je) {
               throw new JuggleException("Pattern is invalid");
            } 
             catch (IndexOutOfBoundsException e)  {
               throw new JuggleException("PASS: Internal error");
            }
      }
   
   
       public int	getStartupThrows() {
         int result = 0;
      
         for (int i = 0; i < max_throw; i++)
            if (states[0][i] != 0)
               result = i + 1;
         return result;
      }
   
       public int	getLoopThrows() { 
         return length; }
       public int	getMaxThrow() 	{ 
         return max_throw; }
		public int[] getPathPerm() {
			return pathperm;
		}
		
	
        public void	layoutPattern(int startup_throws, int loop_throws, double tps) throws JuggleException {
			double		startup_time = (double)startup_throws / tps;
			double		loop_time = (double)loop_throws / tps;
			int			i, throw_value; //, next_throw_value;
			char		mod_value;
			BallPath	bp = null;
			int			throw_index, throw_hand, mult_index;
			double		temp, time, total_time = startup_time + loop_time + 3.0/tps;
			double		borrow_time;

		this.startup_throws = startup_throws;
		this.loop_throws = loop_throws;

      	// First create a BallPathList object, which conveniently
      	// describes how the balls move.
         bpl = new BallPathList(balls, startup_time, loop_time);
      
         int[] used = new int[startup_throws + loop_throws + 5];	// should be big enough
		 int[] airtime_loopstart = new int[balls];
		 int[] airtime_loopend = new int[balls];
      
      	// Go through the balls individually, telling the BallPathList
      	// where they're going.
         for (i = 0; i < balls; i++) {
         	// First figure out on which throw number the ith ball shows
         	// up on.  Find the ith nonzero entry in states[0] to do this.
            throw_index = 0;
            while (pattern_throws[throw_index % length] == used[throw_index])
               throw_index++;
         
            time = throw_index / con.tps;
            borrow_time = 0.0;
         
         	// Tell the BallPathList when this ball starts
            bpl.setStartPoint(i, (throw_index%2), time);
         
         	// Now, keep adding to list until we have more than enough
         	// of the ball's path defined.  It doesn't hurt to define
         	// more than we need.
            while (time < total_time) {
            	// First do the path through the air
               mult_index = used[throw_index]++;
               throw_value = pattern[throw_index % length][mult_index];
               mod_value = mods[throw_index % length][mult_index];
               throw_hand = throw_index % 2;
            //				next_throw_value =
            //					pattern[(throw_index+throw_value)%length][used[throw_index+throw_value]];
            
               if (throw_value == 1) {
                  temp = (con.dwell - borrow_time) / con.tps;
                  bp = new BallPathHand();
                  bp.setPath(throw_hand, temp);
                  bpl.addPath(i, bp);
                  time += temp;
                  temp = (1.0 - 0.5 * con.dwell) / con.tps;
                  if (mod_value == 'B')
                     bp = new BallPathBounce(-con.g, -1.04, con.bouncefrac, false);
                  else if (mod_value == 'F')
                     bp = new BallPathBounce(-con.g, -1.04, con.bouncefrac, true);
                  else
                     bp = new BallPathAir(-con.g);
                  bp.setPath(1 - throw_hand, temp);
                  bpl.addPath(i, bp);
                  time += temp;
                  borrow_time = 0.5 * con.dwell;
               } 
               else if (throw_value == 2) {
                  temp = (con.dwell - borrow_time) / con.tps;
                  bp = new BallPathHand();
                  bp.setPath(throw_hand, temp);
                  bpl.addPath(i, bp);
                  time += temp;
                  temp = (2.0 - con.dwell) / con.tps;
                  bp = new BallPathHold();
                  bp.setPath(throw_hand, temp);
                  bpl.addPath(i, bp);
                  time += temp;
                  borrow_time = 0.0;
               } 
               else {
                  temp = (con.dwell - borrow_time) / con.tps;
                  bp = new BallPathHand();
                  bp.setPath(throw_hand, temp);
                  bpl.addPath(i, bp);
                  time += temp;
                  temp = ((double)throw_value - con.dwell) / con.tps;
                  if (mod_value == 'B')
                     bp = new BallPathBounce(-con.g, -1.04, con.bouncefrac, false);
                  else if (mod_value == 'F')
                     bp = new BallPathBounce(-con.g, -1.04, con.bouncefrac, true);
                  else
                     bp = new BallPathAir(-con.g);
                  bp.setPath((throw_value%2==0)?throw_hand:(1-throw_hand), temp);
                  bpl.addPath(i, bp);
                  time += temp;
                  borrow_time = 0.0;
               }
			   
			   if (throw_index < startup_throws) {
					int airtime = startup_throws - throw_index;
					airtime_loopstart[i] = airtime * max_occupancy + mult_index;
			   }
			   if (throw_index < (startup_throws + loop_throws)) {
					int airtime = startup_throws + loop_throws - throw_index;
					airtime_loopend[i] = airtime * max_occupancy + mult_index;
			   }
			   
               throw_index += throw_value;
            }
         }

		this.pathperm = new int[balls];

		outer:
		for (i = 0; i < balls; i++) {
			for (int j = 0; j < balls; j++) {
				if (airtime_loopstart[i] == airtime_loopend[j]) {
					pathperm[i] = j;
					continue outer;
				}
			}
			throw new JuggleException("Error in layoutPattern()");	// shouldn't happen for valid pattern
		}
	}
   
}



// A physical synchronous site swap.

class PhysicalSyncSiteSwap extends PhysicalPattern {
      protected int[][][]		pattern;
      protected char[][][]		mods;
      protected int[][]			pattern_throws;
      protected int[][][]		states;
	  protected int[]			pathperm;
      protected int				balls;
      protected int				max_throw = 0;
      protected int				length;
      protected int				max_occupancy = 1;
	  protected int				startup_throws, loop_throws;
   
   
       PhysicalSyncSiteSwap(String pat) throws JuggleException {
         int			i, j, k, l, m = 0, n;
         int			pat_index, hand_index, mult_index;
         int			last_pat_index, last_hand_index, last_mult_index;
         boolean		in_parens, in_braces;
         char[]		input = pat.toCharArray();
         char		ch;
         boolean		mod_ok;
      
         try {
            for (i = 0; i < 2; i++) {
               pat_index = mult_index = 0;
               hand_index = 1;
               last_pat_index = last_hand_index = last_mult_index = 0;
               in_parens = in_braces = false;
               mod_ok = false;
            
               for (j = 0; j < input.length; j++) {
                  ch = input[j];
               
                  if (ch == 'x') {
                     throw new JuggleException();
                  } 
                  else if (Character.isDigit(ch) || Character.isLowerCase(ch)) {
                     if (!in_parens)
                        throw new JuggleException();
                     if (!in_braces && (mult_index > 0))
                        throw new JuggleException();
                  
                     k = Character.digit(ch, 36);
                     if (k > 0) {
                        if ((k % 2) != 0)
                           throw new JuggleException();
                        if (i == 1) {
                           pattern[pat_index][hand_index][mult_index] = k;
                           pattern_throws[pat_index][hand_index]++;
                           last_pat_index = pat_index;
                           last_hand_index = hand_index;
                           last_mult_index = mult_index;
                           mod_ok = true;
                        }
                        if (((j+1) < input.length) && (input[j+1] == 'x')) {
                           j++;
                           if (i == 1)
                              pattern[pat_index][hand_index][mult_index] *= -1;
                        }
                     }
                  
                     if (!in_braces || (k > 0))
                        mult_index++;
                  } 
                  else if (Character.isUpperCase(ch)) {
                  	// process modifiers here
                     if (i == 1) {
                        if (mod_ok)
                           mods[last_pat_index][last_hand_index][last_mult_index] = ch;
                        else
                           throw new JuggleException();
                        mod_ok = false;
                     }
                  } 
                  else if (ch == '(') {
                     if (in_braces || in_parens)
                        throw new JuggleException();
                     in_parens = true;
                     hand_index = 1;
                     mult_index = 0;
                     mod_ok = false;
                  } 
                  else if (ch == ')') {
                     if (in_braces || !in_parens || (hand_index != 0))
                        throw new JuggleException();
                     in_parens = false;
                     pat_index++;
                     mod_ok = false;
                  } 
                  else if (ch == ',') {
                     if (in_braces || !in_parens)
                        throw new JuggleException();
                     if (hand_index-- == 0)
                        throw new JuggleException();
                     mult_index = 0;
                     mod_ok = false;
                  } 
                  else if (ch == '[') {
                     if (in_braces || (mult_index > 0))
                        throw new JuggleException();
                     in_braces = true;
                     mod_ok = false;
                  } 
                  else if (ch == ']') {
                     if (!in_braces)
                        throw new JuggleException();
                     if (mult_index > max_occupancy)
                        max_occupancy = mult_index;
                     in_braces = false;
                     mod_ok = false;
                  }
               }
            
               if (in_braces || in_parens)
                  throw new JuggleException();
            
               if (i == 0) {
                  this.length = pat_index;
                  pattern = new int[pat_index][2][max_occupancy];
                  mods = new char[pat_index][2][max_occupancy];
                  pattern_throws = new int[pat_index][2];
               }
            }
         } 
             catch (JuggleException je) {
               throw new JuggleException("Syntax error in pattern");
            }
      
      
         try {
            for (i = 0; i < length; i++) {
               for (j = 0; j < max_occupancy; j++) {
                  k = Math.abs(pattern[i][0][j]);
                  l = Math.abs(pattern[i][1][j]);
                  m += k + l;
                  if (k > max_throw)
                     max_throw = k;
                  if (l > max_throw)
                     max_throw = l;
               }
            }
         
            states = new int[length][2][max_throw/2];
            if ((balls % (2 * length)) != 0)
               throw new JuggleException();
            balls = m / length / 2;
         
            for (i = 0; i < max_throw/2; i++) {
               l = length - 1 - i%length;
               for (j = 0; j < 2; j++) {
                  for (k = 0; k < pattern_throws[l][j]; k++) {
                     m = pattern[l][j][k] / 2;
                     n = Math.abs(m);
                  
                     if (n > i)
                        states[0][(m<0)?(1-j):j][n-i-1]++;
                  }
               }
            }
            if (states[0][0][0] != pattern_throws[0][0])
               throw new JuggleException();
            if (states[0][1][0] != pattern_throws[0][1])
               throw new JuggleException();
         
            for (i = 1; i < length; i++) {
               for (j = 1; j < max_throw/2; j++) {
                  states[i][0][j-1] = states[i-1][0][j];
                  states[i][1][j-1] = states[i-1][1][j];
               }
               states[i][0][max_throw/2-1] = states[i][1][max_throw/2-1] = 0;
               for (j = 0; j < pattern_throws[i-1][0]; j++) {
                  k = pattern[i-1][0][j] / 2;
                  l = Math.abs(k);
                  if (l > 0)
                     states[i][(k>0)?0:1][l-1]++;
               }
               for (j = 0; j < pattern_throws[i-1][1]; j++) {
                  k = pattern[i-1][1][j] / 2;
                  l = Math.abs(k);
                  if (l > 0)
                     states[i][(k>0)?1:0][l-1]++;
               }
               if (states[i][0][0] != pattern_throws[i][0])
                  throw new JuggleException();
               if (states[i][1][0] != pattern_throws[i][1])
                  throw new JuggleException();
            }
         
         } 
             catch (IndexOutOfBoundsException e)  {
               throw new JuggleException("PSSS: Internal Error 2");
            } 
             catch (JuggleException je) {
               throw new JuggleException("Pattern is invalid");
            }
      }
   
   
		
       public int	getStartupThrows() {
         int result = 0;
      
         for (int i = 0; i < max_throw; i += 2)
            if ((states[0][0][i/2] != 0) || (states[0][1][i/2] != 0))
               result = i + 2;
         return result;
      }
   
	public int	getLoopThrows() { return 2*length; }
	public int	getMaxThrow() 	{ return max_throw; }
	
	public int[] getPathPerm() {
/*
System.out.println("startup_throws = "+startup_throws+", loop_throws = "+loop_throws);
String res = "";
for (int i = 0; i < balls; i++)
	res += pathperm[i] + ",";
System.out.println("pathperm = "+res);
*/
		return pathperm;
	}

   
		public void layoutPattern(int startup_throws, int loop_throws, double tps) throws JuggleException {
			double startup_time = (double)startup_throws / tps;
			double loop_time = (double)loop_throws / tps;
         int			i, j, k;
         BallPath	bp = null;
         int			throw_index, throw_value, hand_index, mult_index;
         char		mod_value;
         double		temp, time, total_time = startup_time + loop_time + 3.0/tps;
      
		this.startup_throws = startup_throws;
		this.loop_throws = loop_throws;
		
         bpl = new BallPathList(balls, startup_time, loop_time);
      
         int[][] used = new int[2 + (int)(total_time * tps / 2)][2];
		 int[] airtime_loopstart = new int[balls];
		 int[] airtime_loopend = new int[balls];
      
         for (i = 0; i < balls; i++) {
            throw_index = 0;
            hand_index = 0;
            while (used[throw_index/2][hand_index] ==
						pattern_throws[throw_index/2 % length][hand_index]) {
               if (hand_index == 1)
                  throw_index += 2;
               hand_index = 1 - hand_index;
            }
            time = (double)throw_index / con.tps;
         
            bpl.setStartPoint(i, hand_index, time);
         
            while (time < total_time) {
               mult_index = used[throw_index/2][hand_index]++;
               throw_value = pattern[throw_index/2 % length][hand_index][mult_index];
               mod_value = mods[throw_index/2 % length][hand_index][mult_index];
            
               k = Math.abs(throw_value);
            
               if (throw_value == 2) {
                  temp = con.dwell / con.tps;
                  bp = new BallPathHand();
                  bp.setPath(hand_index, temp);
                  time += temp;
                  bpl.addPath(i, bp);
                  temp = (2.0 - con.dwell) / con.tps;
                  bp = new BallPathHold();
                  bp.setPath(hand_index, temp);
                  time += temp;
                  bpl.addPath(i, bp);
               } 
               else {
                  temp = con.dwell / con.tps;
                  time += temp;
                  bp = new BallPathHand();
                  bp.setPath(hand_index, temp);
                  bpl.addPath(i, bp);
                  temp = ((double)k - con.dwell) / con.tps;
                  time += temp;
               
                  if (mod_value == 'B')
                     bp = new BallPathBounce(-con.g, -1.04, con.bouncefrac, false);
                  else if (mod_value == 'F')
                     bp = new BallPathBounce(-con.g, -1.04, con.bouncefrac, true);
                  else
                     bp = new BallPathAir(-con.g);
               
                  if (throw_value < 0)
                     hand_index = 1 - hand_index;
                  bp.setPath(hand_index, temp);
                  bpl.addPath(i, bp);
               }
			   
			   if (throw_index < startup_throws) {
					int airtime = startup_throws - throw_index;
					int handfrom = hand_index;
					int handto = (throw_value < 0) ? (1-hand_index) : hand_index;
					airtime_loopstart[i] = ((airtime * 2 + handfrom) * 2 + handto) * max_occupancy + mult_index;
			   }
			   if (throw_index < (startup_throws + loop_throws)) {
					int airtime = startup_throws + loop_throws - throw_index;
					int handfrom = hand_index;
					int handto = (throw_value < 0) ? (1-hand_index) : hand_index;
					airtime_loopend[i] = ((airtime * 2 + handfrom) * 2 + handto) * max_occupancy + mult_index;
			   }
			   
               throw_index += k;
            }
         }
      
		this.pathperm = new int[balls];

		outer:
		for (i = 0; i < balls; i++) {
			for (j = 0; j < balls; j++) {
				if (airtime_loopstart[i] == airtime_loopend[j]) {
// System.out.println("Mapped ball "+i+" onto ball "+j+", airtime = "+airtime_loopstart[i]);
					pathperm[i] = j;
					continue outer;
				}
			}
			throw new JuggleException("Error in layoutPattern()");	// shouldn't happen for valid pattern
		}
	}
   
}



    class HandPathDefault extends HandPath {
      protected double	bx, cx;
      protected double	ay, by, cy;
      protected double	handscoop;
   
       HandPathDefault(double handscoop)	{ this.handscoop = handscoop; }
   
       protected void	fixInternalVariables() {
         double	t = getDuration();
      
         ay = -4.0 * handscoop / (t * t);
         cx = start_coord.x;
         bx = (end_coord.x - start_coord.x) / t;
         cy = start_coord.y;
         by = (end_coord.y - start_coord.y) / t - ay * t;
      }
   
       public Coordinate getCoord(double time) {
         if ((time < start_time) || (time > end_time))
            return null;
         time -= start_time;
         return new Coordinate(cx+bx*time, cy+time*(by+ay*time));
      }
   
       public Coordinate getMax() {
         double	x, y;
         double	duration = getDuration();
         double	temp = -by / (2.0 * ay);
      
         x = (bx > 0.0) ? (cx+bx*duration) : cx;
      
         if ((ay > 0.0) || (temp < 0.0) || (temp > duration))
            y = Math.max(cy, cy+duration*(by+ay*duration));
         else
            y = cy+temp*(by+ay*temp);
         return new Coordinate(x, y);
      }
   
       public Coordinate getMin() {
         double	x, y;
         double	duration = getDuration();
         double	temp = -by / (2.0 * ay);
      
         x = (bx > 0.0) ? cx : (cx+bx*duration);
      
         if ((ay < 0.0) || (temp < 0.0) || (temp > duration))
            y = Math.min(cy, cy+duration*(by+ay*duration));
         else
            y = cy+temp*(by+ay*temp);
         return new Coordinate(x, y);
      }
   }


    class PhysicalHandsDefault extends PhysicalHands {
      protected int			hands;
      protected int			len;
      protected int			loop_throws;
      protected double[][]	pos_list;
   
       public int	getStartupThrows()		{ 
         return 0; }
       public int	getLoopThrows()			{ 
         return loop_throws; }
       public int	getHands()				{ 
         return hands; }
   
       PhysicalHandsDefault(int hands, double[][] pos_list) throws JuggleException {
         this.len = pos_list.length;
         this.hands = hands;
         if ((len % (2*hands)) != 0)
            throw new JuggleException("Hand path not multiple of "+(2*hands));
         this.loop_throws = len / 2;
         this.pos_list = pos_list;
      }
   
       public void	layoutHands(BallPathList bpl, double total_time) throws JuggleException {
         int					i, j;
         double				time, delay;
         HandPathDefault		hp = null;
         Coordinate			start = null;
         Coordinate			end = null;
         BallPath			bptemp, bptemp2;
      
         this.hpl = new BallPathList(hands, bpl.getStartupTime(), bpl.getLoopTime());
      
         for (i = 0; i < hands; i++) {
            time = -4.0 / con.tps;
            hpl.setStartPoint(i, 0, time);
            j = 2 * i;
            start = new Coordinate(pos_list[j%len][0], pos_list[j%len][1]);
         
            bptemp = bpl.findNextThrow(i, time, true);		// include holds
         
            if (bptemp == null) {		// hand is inactive for entire pattern
               hp = new HandPathDefault(0.0);
               hp.setPath(start, start, total_time-time+1.0);
               hpl.addPath(i, hp);
            } 
            else {
               delay = bptemp.getStartTime() - time - con.dwell/con.tps;
               hp = new HandPathDefault(0.0);
               hp.setPath(start, start, delay);
               hpl.addPath(i, hp);
               time += delay;
            
               while (time < total_time) {
                  end = new Coordinate(pos_list[(j+1)%len][0], pos_list[(j+1)%len][1]);
               
                  bptemp = bpl.findNextThrow(i, time, true);		// include holds
               
                  delay = bptemp.getStartTime() - time;
                  while (delay < 0.0)
                     delay += bpl.getLoopTime();
                  bptemp2 = bpl.findNextThrow(i, time, false);	// skip holds
               
                  if ((bptemp2 == null) || (Math.abs(bptemp.getStartTime() -
                  bptemp2.getStartTime()) > 0.001/con.tps) ||
                  (delay > (con.dwell+0.001)/con.tps)) {
                  			// next throw is a hold only or a 0 throw
                     if ((pos_list[j%len][0] == pos_list[(j+2*hands)%len][0]) &&
                     (pos_list[j%len][1] == pos_list[(j+2*hands)%len][1])) {
                     		// if this catch position and next catch position are
                     		// identical, just sit in one spot.
                        if (delay > (con.dwell+0.001)/con.tps)
                           delay = 2.0 / con.tps;	// next throw is a zero
                        else
                           delay += (2.0 - con.dwell) / con.tps;
                        hp = new HandPathDefault(0.0);
                        hp.setPath(start, start, delay);
                        hpl.addPath(i, hp);
                        j += 2 * hands;		// don't need to update 'start'
                        time += delay;
                        continue;
                     }
                  }
               
                  if (delay > (con.dwell+0.001)/con.tps)
                     delay = con.dwell / con.tps;
               
               	// scoop to throw
                  hp = new HandPathDefault(-con.handscoop);
                  hp.setPath(start, end, delay);
                  hpl.addPath(i, hp);
               	// go to next catch position
                  j += 2 * hands;
                  start = new Coordinate(pos_list[j%len][0], pos_list[j%len][1]);
                  hp = new HandPathDefault(con.handscoop);
                  hp.setPath(end, start, 2.0/con.tps - delay);
                  hpl.addPath(i, hp);
                  time += 2.0 / con.tps;
               }
            }
         }
      }
   
   }



//  Here are the implementations of our own jugglers

    class PhysicalJugglerDefault extends PhysicalJuggler {
       public int		getMaxLines()  		{ 
         return 10; }
       public int		getMaxEllipses()	{ 
         return 1; }
   
      public final static double shoulder_hw = 0.23;	// shoulder half-width (m)
      public final static double shoulder_h = 0.4;	// throw pos. to shoulder
      public final static double waist_hw = 0.17;		// waist half-width
      public final static double waist_h = -0.05;
      public final static double elbow_hw = 0.3;		// elbow "home"
      public final static double elbow_h = 0.06;
      public final static double elbow_slop = 0.12;
      public final static double hand_out = 0.05;		// outside width of hand
      public final static double hand_in = 0.05;
      public final static double head_hw = 0.10;		// head half-width
      public final static double head_h = 0.26;		// head height
      public final static double neck_h = 0.05;		// neck height
   
   
       public Vector	getLines() {
         Vector		vec = new Vector(20, 5);
         Coordinate	temp = null, lshoulder = null, rshoulder = null;
      
         lshoulder = new Coordinate(-shoulder_hw, shoulder_h);
         rshoulder = new Coordinate( shoulder_hw, shoulder_h);
      
         vec.addElement(lshoulder);
         vec.addElement(rshoulder);								// shoulders
         vec.addElement(rshoulder);
         vec.addElement(temp = new Coordinate(waist_hw, waist_h));// right side
         vec.addElement(temp);
         vec.addElement(temp = new Coordinate(-waist_hw, waist_h));	// waist
         vec.addElement(temp);
         vec.addElement(lshoulder);								// left side
      
         double relbow_x = elbow_hw + slop_func(right.x-elbow_hw, elbow_slop,
         -elbow_slop, 0.5, 0.5);
         double relbow_y = elbow_h + slop_func(right.y-elbow_h, elbow_slop,
         -elbow_slop, 0.5, 0.5);
         double lelbow_x = -elbow_hw - slop_func(-left.x-elbow_hw, elbow_slop,
         -elbow_slop, 0.5, 0.5);
         double lelbow_y = elbow_h + slop_func(left.y-elbow_h, elbow_slop,
         -elbow_slop, 0.5, 0.5);
      
         Coordinate	lelbow = new Coordinate(lelbow_x, lelbow_y);
         Coordinate	relbow = new Coordinate(relbow_x, relbow_y);
      
         vec.addElement(lshoulder);
         vec.addElement(lelbow);									// left upper arm
         vec.addElement(lelbow);
         vec.addElement(left);									// left forearm
         vec.addElement(rshoulder);
         vec.addElement(relbow);									// right upper arm
         vec.addElement(relbow);
         vec.addElement(right);									// right forearm
         vec.addElement(new Coordinate(left.x-hand_out, left.y));
         vec.addElement(new Coordinate(left.x+hand_in, left.y));	// left hand
         vec.addElement(new Coordinate(right.x-hand_in, right.y));
         vec.addElement(new Coordinate(right.x+hand_out, right.y));	// right hand
      
         return vec;
      }
   
       public Vector	getEllipses() {
         Vector		vec = new Vector(2, 5);
      
         vec.addElement(new Coordinate(-head_hw, shoulder_h+neck_h+head_h));
         vec.addElement(new Coordinate(head_hw, shoulder_h+neck_h));
      
         return vec;
      }
   
       double slop_func(double delta, double pos_slop, double neg_slop, double pos_frac, double neg_frac) {
         if (delta > pos_slop)
            return pos_frac * (delta - pos_slop);
         else if (delta < neg_slop)
            return neg_frac * (delta - neg_slop);
         return 0.0;
      }
   }
