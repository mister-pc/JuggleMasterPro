// JuggleMovie.java
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


// A single frame in the JuggleMovie class defined below

    class JuggleMovieFrame {
      public int			frame_duration;		// in milliseconds
      public int			frame_images;		// number of images
      public Coordinate[]	images_coord;
      public int[]		images_x;
      public int[]		images_y;
      public Image[]		images;
      public int			frame_lines;		// number of lines
      public Coordinate[]	lines_scoord;
      public Coordinate[] lines_ecoord;
      public int[]		lines_startx;
      public int[]		lines_starty;
      public int[]		lines_endx;
      public int[]		lines_endy;
      public int			frame_ellipses;
      public Coordinate[]	ellipses_scoord;
      public Coordinate[] ellipses_ecoord;
      public int[]		ellipses_startx;
      public int[]		ellipses_starty;
      public int[]		ellipses_endx;
      public int[]		ellipses_endy;
   
       JuggleMovieFrame(int images, int lines, int ellipses) {
         this.images_coord = new Coordinate[images];
         this.images_x = new int[images];
         this.images_y = new int[images];
         this.images = new Image[images];
         this.lines_scoord = new Coordinate[lines];
         this.lines_ecoord = new Coordinate[lines];
         this.lines_startx = new int[lines];
         this.lines_starty = new int[lines];
         this.lines_endx = new int[lines];
         this.lines_endy = new int[lines];
         this.ellipses_scoord = new Coordinate[ellipses];
         this.ellipses_ecoord = new Coordinate[ellipses];
         this.ellipses_startx = new int[ellipses];
         this.ellipses_starty = new int[ellipses];
         this.ellipses_endx = new int[ellipses];
         this.ellipses_endy = new int[ellipses];
      }
   }



// This class represents a movie of a juggling pattern.  It provides facilities
// for creating a movie, adding frames to it, and then playing it back.  In
// this version, frames consist of collections of images and lines.

    class JuggleMovie {
      protected int				max_images;
      protected int				max_lines;
      protected int				max_ellipses;
      protected int				movie_status;
      protected int				frame_status;
      protected int 				current_frame_num;
      protected int				loop_frame_num;
      protected int				num_frames;
      protected Vector			framelist = null;
      protected JuggleMovieFrame	current_frame = null;
	  protected int[]			repetition_imageperm;
		protected int[]			imageperm;
		
       JuggleMovie(int images, int lines, int ellipses) {
         this.max_images = images;
         this.max_lines = lines;
         this.max_ellipses = ellipses;
         this.current_frame_num = 0;
         this.loop_frame_num = 0;
         this.framelist = new Vector(20, 20);
		 
		 this.imageperm = new int[max_images];
		 this.repetition_imageperm = new int[max_images];
		 for (int i = 0; i < max_images; i++)
			repetition_imageperm[i] = imageperm[i] = i;
      }
   
       public void open_movie() throws JuggleException {
         if (movie_status != 0)
            throw new JuggleException("Can only open a new movie");
      
         movie_status = 1;
         framelist = new Vector(10, 10);
      }
   
       public void close_movie() throws JuggleException {
         if (movie_status < 1)
            throw new JuggleException("Can't close an unopened movie");
         else if (movie_status > 1)
            throw new JuggleException("Movie was already closed");
      
         movie_status = 2;
         num_frames = current_frame_num;
         framelist.trimToSize();
         current_frame_num = 0;
         current_frame = (JuggleMovieFrame)framelist.elementAt(current_frame_num);
      }
   
       public void open_frame(int ticks) throws JuggleException {
         if (movie_status != 1)
            throw new JuggleException("Can't open frame in unopened movie");
         if (frame_status == 1)
            throw new JuggleException("Frame is already open");
         frame_status = 1;
         current_frame = new JuggleMovieFrame(max_images, max_lines, max_ellipses);
         current_frame.frame_duration = ticks;
         framelist.addElement(current_frame);
      }
   
       public void close_frame() throws JuggleException {
         if (movie_status != 1)
            throw new JuggleException("Can't close frame in unopened movie");
         if (frame_status == 0)
            throw new JuggleException("Can't close unopened frame");
         frame_status = 0;
         current_frame_num++;
      }
   
       public void start_loop() throws JuggleException {
         if (movie_status != 1)
            throw new JuggleException("Can't start loop in unopened movie");
         if (frame_status == 1)
            throw new JuggleException("Can't start loop with frame open");
         loop_frame_num = current_frame_num;
      }
   
       public void add_image(Image img, Coordinate c) throws JuggleException {
         if (frame_status == 0)
            throw new JuggleException("Can't add image to unopened frame");
      
         int		temp = current_frame.frame_images;
         if (temp == max_images)
            throw new JuggleException("Too many images in frame");
      
         current_frame.images[temp] = img;
         current_frame.images_coord[temp] = c;
         current_frame.frame_images++;
      }
   
       public void add_line(Coordinate c_start, Coordinate c_end) throws JuggleException {
         if (frame_status == 0)
            throw new JuggleException("Can't add line to unopened frame");
      
         int		temp = current_frame.frame_lines;
         if (temp == max_lines)
            throw new JuggleException("Too many lines in frame");
         current_frame.lines_scoord[temp] = c_start;
         current_frame.lines_ecoord[temp] = c_end;
         current_frame.frame_lines++;
      }
   
       public void add_ellipse(Coordinate c_start, Coordinate c_end) throws JuggleException {
         if (frame_status == 0)
            throw new JuggleException("Can't add ellipse to unopened frame");
      
         int		temp = current_frame.frame_ellipses;
         if (temp == max_ellipses)
            throw new JuggleException("Too many ellipses in frame");
         current_frame.ellipses_scoord[temp] = c_start;
         current_frame.ellipses_ecoord[temp] = c_end;
         current_frame.frame_ellipses++;
      }
   
       public void	advance_frame() {
         if (++current_frame_num >= num_frames) {
            current_frame_num = loop_frame_num;
			for (int i = 0; i < max_images; i++)
				imageperm[i] = repetition_imageperm[imageperm[i]];
		}
         current_frame = (JuggleMovieFrame)framelist.elementAt(current_frame_num);
      }
   
/*
       public void	rewind_frame() {
         if (--current_frame_num < loop_frame_num)
            current_frame_num = num_frames - 1;
         current_frame = (JuggleMovieFrame)framelist.elementAt(current_frame_num);
      }
*/
   
       public int getLoopStartFrameNum() {
         return loop_frame_num;
      }
   
       public int getLoopEndFrameNum() {
         return (num_frames-1);
      }
   
       public void setFrameNum(int num) {
         if (num >= 0 && num < num_frames) {
            current_frame_num = num;
            current_frame = (JuggleMovieFrame)framelist.elementAt(num);

			for (int i = 0; i < max_images; i++)
				imageperm[i] = i;
         }
      }
   
       public int getFrameNum() {
         return current_frame_num;
      }
 
/*
       public void	rewind_movie() throws JuggleException {
         if (movie_status != 2)
            throw new JuggleException("Can't rewind unfinished movie");
      
         current_frame_num = 0;
         current_frame = (JuggleMovieFrame)framelist.elementAt(current_frame_num);
      }
*/
   
       public Color[] getColors() {
         return new Color[] { Color.black };
      }
   
       public void draw_frame(Graphics g, Component comp) {
         int			i, tempx, tempy;
         Image		img;
      
         for (i = 0; i < current_frame.frame_lines; i++)
            g.drawLine(current_frame.lines_startx[i], current_frame.lines_starty[i],
               current_frame.lines_endx[i], current_frame.lines_endy[i]);
      
         for (i = 0; i < current_frame.frame_ellipses; i++) {
            tempx = current_frame.ellipses_startx[i];
            tempy = current_frame.ellipses_starty[i];
            g.drawOval(tempx, tempy, current_frame.ellipses_endx[i] - tempx,
               current_frame.ellipses_endy[i] - tempy);
         }
      
         for (i = 0; i < current_frame.frame_images; i++)  {
            img = current_frame.images[imageperm[i]];
            g.drawImage(img, current_frame.images_x[i],
               current_frame.images_y[i], comp);
         }
      }
   
       public int get_duration() {
         return current_frame.frame_duration;
      }
   
       public void coord_to_xy(double zoom, int originx, int originy, int ballwidth,
       int ballheight) {
         int					i, j;
         JuggleMovieFrame	fr = null;
      
         for (i = 0; i < framelist.size(); i++) {
            fr = (JuggleMovieFrame)framelist.elementAt(i);
            for (j = 0; j < fr.frame_lines; j++) {
               fr.lines_startx[j] = originx + (int)(0.5 + zoom * fr.lines_scoord[j].x);
               fr.lines_starty[j] = originy - (int)(0.5 + zoom * fr.lines_scoord[j].y);
               fr.lines_endx[j] = originx + (int)(0.5 + zoom * fr.lines_ecoord[j].x);
               fr.lines_endy[j] = originy - (int)(0.5 + zoom * fr.lines_ecoord[j].y);
            }
            for (j = 0; j < fr.frame_ellipses; j++) {
               fr.ellipses_startx[j] = originx + (int)(0.5 + zoom * fr.ellipses_scoord[j].x);
               fr.ellipses_starty[j] = originy - (int)(0.5 + zoom * fr.ellipses_scoord[j].y);
               fr.ellipses_endx[j] = originx + (int)(0.5 + zoom * fr.ellipses_ecoord[j].x);
               fr.ellipses_endy[j] = originy - (int)(0.5 + zoom * fr.ellipses_ecoord[j].y);
            }
            for (j = 0; j < fr.frame_images; j++) {
               fr.images_x[j] = originx - ballwidth/2 + (int)(0.5 + zoom * fr.images_coord[j].x);
               fr.images_y[j] = originy - ballheight - (int)(0.5 + zoom * fr.images_coord[j].y);
            }
         }
      }
   
       public void flush_coords() {
         int					i, j;
         JuggleMovieFrame	fr = null;
      
         for (i = 0; i < framelist.size(); i++) {
            fr = (JuggleMovieFrame)framelist.elementAt(i);
            for (j = 0; j < fr.frame_lines; j++) {
               fr.lines_scoord[j] = null;
               fr.lines_ecoord[j] = null;
            }
            for (j = 0; j < fr.frame_ellipses; j++) {
               fr.ellipses_scoord[j] = null;
               fr.ellipses_ecoord[j] = null;
            }
            for (j = 0; j < fr.frame_images; j++)
               fr.images_coord[j] = null;
         }
      }
   
       public void find_max_min(Coordinate coordmax, Coordinate coordmin) {
         int					i, j;
         JuggleMovieFrame	fr = null;
         Coordinate			c = null;
      
         for (i = 0; i < framelist.size(); i++) {
            fr = (JuggleMovieFrame)framelist.elementAt(i);
            for (j = 0; j < fr.frame_lines; j++) {
               c = fr.lines_scoord[j];
               if (c.x > coordmax.x)	coordmax.x = c.x;
               if (c.x < coordmin.x)	coordmin.x = c.x;
               if (c.y > coordmax.y)	coordmax.y = c.y;
               if (c.y < coordmin.y)	coordmin.y = c.y;
               c = fr.lines_ecoord[j];
               if (c.x > coordmax.x)	coordmax.x = c.x;
               if (c.x < coordmin.x)	coordmin.x = c.x;
               if (c.y > coordmax.y)	coordmax.y = c.y;
               if (c.y < coordmin.y)	coordmin.y = c.y;
            }
            for (j = 0; j < fr.frame_ellipses; j++) {
               c = fr.ellipses_scoord[j];
               if (c.x > coordmax.x)	coordmax.x = c.x;
               if (c.x < coordmin.x)	coordmin.x = c.x;
               if (c.y > coordmax.y)	coordmax.y = c.y;
               if (c.y < coordmin.y)	coordmin.y = c.y;
               c = fr.ellipses_ecoord[j];
               if (c.x > coordmax.x)	coordmax.x = c.x;
               if (c.x < coordmin.x)	coordmin.x = c.x;
               if (c.y > coordmax.y)	coordmax.y = c.y;
               if (c.y < coordmin.y)	coordmin.y = c.y;
            }
            for (j = 0; j < fr.frame_images; j++) {
               c = fr.images_coord[j];
               if (c.x > coordmax.x)	coordmax.x = c.x;
               if (c.x < coordmin.x)	coordmin.x = c.x;
               if (c.y > coordmax.y)	coordmax.y = c.y;
               if (c.y < coordmin.y)	coordmin.y = c.y;
            }
         }
      }
   
   // sets an image for all the balls
       public void	set_image(Image ballimage) {
         int					i, j;
         JuggleMovieFrame	fr = null;
      
         for (i = 0; i < framelist.size(); i++) {
            fr = (JuggleMovieFrame)framelist.elementAt(i);
            for (j = 0; j < fr.frame_images; j++)
               if (fr.images[j] == null)
                  fr.images[j] = ballimage;
         }
      }
   
   // sets an image for each ball
       public void	set_images(Image[] ballimages)
      {
         int					i, j;
         JuggleMovieFrame	fr = null;
      
         for (i = 0; i < framelist.size(); i++)
         {
            fr = (JuggleMovieFrame)framelist.elementAt(i);
            for (j = 0; j < fr.frame_images; j++)
               if (fr.images[j] == null)
               {
                  try
                  {
                     fr.images[j] = ballimages[j];
                  }
                      catch (NullPointerException e)
                     {
                        System.out.println("Invalid ball image "+i);
                     }
               }
         }
      }
   
		// set the permutation of the images that is applied after
		// each iteration through the pattern
	public void setImagePermutation(int[] perm) {
		for (int i = 0; i < max_images; i++)
			repetition_imageperm[i] = (i < perm.length) ? perm[i] : i;
	}
   }



// The following class provides a simple movie.  You give it a PhysicalGroup
// object and it creates a movie, scaling it to fit within the specified
// screen rectangle.
// In the constructor you can either specify a ball image to use, or let the
// program make one of the correct size.

    class JuggleMovieSimple extends JuggleMovie {
	  protected PhysicalGroup		group = null;
      protected BallPathList		bpl = null;
      protected BallPathList		hpl = null;
      protected PhysicalJuggler	jug = null;
      protected Image[]				ballimage = null;
      protected double			balldiam;
      protected Component			comp;
      protected int				ballwidth, ballheight;
      protected Rectangle			r;
      protected Coordinate		coordmin, coordmax;
   
   
       JuggleMovieSimple(PhysicalGroup group, double balldiam, Component comp,
       Rectangle r, double fps, double slowdown) throws JuggleException {
         super(group.getBallPathList().getActors() +
            group.getHandPathList().getActors(),
            group.getJuggler().getMaxLines(), group.getJuggler().getMaxEllipses());
      
		 this.group = group;
         this.bpl = group.getBallPathList();
         this.hpl = group.getHandPathList();
         this.jug = group.getJuggler();
         this.r = r;
         this.balldiam = balldiam;
         this.comp = comp;
         this.ballimage = new Image[bpl.getActors()];
         makeMovie(fps, slowdown);
      }
   
   
       JuggleMovieSimple(PhysicalGroup group, Image ballimage, Component comp,
       Rectangle r, double fps, double slowdown) throws JuggleException {
         super(group.getBallPathList().getActors() +
            group.getHandPathList().getActors(),
            group.getJuggler().getMaxLines(), group.getJuggler().getMaxEllipses());
      
         this.group = group;
		 this.bpl = group.getBallPathList();
         this.hpl = group.getHandPathList();
         this.jug = group.getJuggler();
         this.r = r;
         this.ballimage = new Image[bpl.getActors()];
      
      	// Use a MediaTracker object to wait for the image to finish loading.
         MediaTracker mt = new MediaTracker(comp);
         mt.addImage(ballimage, 0);
         try {
            mt.waitForID(0);
         } 
             catch (InterruptedException e) {
            // what should we do here?
               throw new JuggleException("Image loading interrupted");
            }
         if (mt.isErrorID(0))
            throw new JuggleException("Error loading images");
      	// at this point we know that the image has finished loading
      	// with no errors, and the Image "ballimage" is valid.
      
         ballwidth = ballimage.getWidth(comp);
         ballheight = ballimage.getHeight(comp);
         this.ballimage[0] = ballimage;
      
         makeMovie(fps, slowdown);
      }
   
   
       protected void makeMovie(double fps, double slowdown) throws JuggleException {	
         double		frame_width, frame_height;		// meters
         double		zoom;							// pixels per meter
         int			originx, originy;
         int			i, j;
         Vector		vec;
         int			x0, y0, x1, y1;
         double		t = 0.0;
         int			loop_frames = (int)(0.5 + slowdown * fps * bpl.getLoopTime());
         Coordinate	coord1 = null, coord2 = null;
      
         open_movie();
      
      	// Adjust fps so that the loop contains an integer number of frames.
         fps = (double)loop_frames / slowdown / bpl.getLoopTime();
      
         while (t <= bpl.getStartupTime()) {
            open_frame((int)(0.5 + 1000/fps));
         	// First draw the juggler.  Tell the PhysicalJuggler object where
         	// the hands are, and then have it give us a list of lines.
            coord1 = hpl.getCoord(0,t);
            coord2 = hpl.getCoord(1,t);
            if ((coord1 != null) && (coord2 != null)) {
               jug.placeHands(coord1, coord2);
               vec = jug.getLines();
               for (i = 0; i < vec.size(); i += 2) {
                  coord1 = (Coordinate)vec.elementAt(i);
                  coord2 = (Coordinate)vec.elementAt(i + 1);
                  add_line(coord1, coord2);
               }
               vec = jug.getEllipses();
               for (i = 0; i < vec.size(); i += 2) {
                  coord1 = (Coordinate)vec.elementAt(i);
                  coord2 = (Coordinate)vec.elementAt(i + 1);
                  add_ellipse(coord1, coord2);
               }
            }
            for (i = 0; i < bpl.getActors(); i++) {
               coord1 = bpl.getCoord(i, t);
               if (coord1 != null)
                  add_image(ballimage[i], coord1);
            }
            close_frame();
            t += 1.0 / fps / slowdown;
         }
      
         start_loop();
      
         for (i = 0; i < loop_frames; i++) {
            open_frame((int)(0.5 + 1000/fps));
            coord1 = hpl.getCoord(0,t);
            coord2 = hpl.getCoord(1,t);
            if ((coord1 != null) && (coord2 != null)) {
               jug.placeHands(coord1, coord2);
               vec = jug.getLines();
               for (j = 0; j < vec.size(); j += 2) {
                  coord1 = (Coordinate)vec.elementAt(j);
                  coord2 = (Coordinate)vec.elementAt(j + 1);
                  add_line(coord1, coord2);
               }
               vec = jug.getEllipses();
               for (j = 0; j < vec.size(); j += 2) {
                  coord1 = (Coordinate)vec.elementAt(j);
                  coord2 = (Coordinate)vec.elementAt(j + 1);
                  add_ellipse(coord1, coord2);
               }
            }
            for (j = 0; j < bpl.getActors(); j++) {
               coord1 = bpl.getCoord(j, t);
               if (coord1 != null)
                  add_image(ballimage[j], coord1);
            }
            close_frame();
            t += 1.0 / fps / slowdown;
         }
      
         close_movie();
      
         Coordinate	coordmax = new Coordinate(0.0, 0.0);
         Coordinate	coordmin = new Coordinate(0.0, 0.0);
         find_max_min(coordmax, coordmin);
      
         for (int ball_cycler = 0; ball_cycler < bpl.getActors(); ball_cycler++)
         {
            if (ballimage[ball_cycler] == null) {		// need to make our own ball
               frame_width = 2.0 * Math.max(Math.abs(coordmax.x),
                  Math.abs(coordmin.x)) + balldiam;
               frame_height = coordmax.y - coordmin.y + balldiam;
            
               zoom = Math.min((double)r.width/frame_width,
                  (double)r.height/frame_height);
               int ball_pixel_size = (int)(0.5 + zoom * balldiam);
               if (ball_pixel_size < 1)
                  ball_pixel_size = 1;
            
            // Now we should create a ball image of diameter ball_pixel_size
            // pixels and put it in the variable ballimage.
               ballimage[ball_cycler] = comp.createImage(ball_pixel_size, ball_pixel_size);
               Graphics g = ballimage[ball_cycler].getGraphics();
               g.setColor(comp.getBackground());
               g.fillRect(0, 0, ball_pixel_size, ball_pixel_size);
            //g.setColor(Color.red);
               g.setColor(group.getBallColor(ball_cycler));
            //	g.fillOval(0, 0, ball_pixel_size, ball_pixel_size);
            // The following lines are necessary because the fillOval method
            // doesn't work properly in some Java implementations.
               double r = ((double)ball_pixel_size) / 2.0;
               double x, y;
               int offset;
               for (i = 0; i < (int)r; i++) {
                  y = r - 0.5 - (double)i;
                  x = Math.sqrt(r*r - y*y);
                  offset = (int)(r - x + 0.5);
                  g.drawLine(offset, i, ball_pixel_size-offset-1, i);
                  g.drawLine(offset, ball_pixel_size-i-1, ball_pixel_size-offset-1,
                     ball_pixel_size-i-1);
               }
               if ((ball_pixel_size & 1) != 0)
                  g.drawLine(0, (int)r, ball_pixel_size-1, (int)r);
            
               //set_image(ballimage);
               ballwidth = ballheight = ball_pixel_size;
            }
         }
         set_images(ballimage);
      	// Now calculate the zoom factor that we need to fit the
      	// pattern into the desired screen Rectangle r.
         frame_width = 2.0 * Math.max(Math.abs(coordmax.x), Math.abs(coordmin.x));
         frame_height = coordmax.y - coordmin.y;
         zoom = Math.min((double)(r.width - ballwidth) / frame_width,
            (double)(r.height - ballheight) / frame_height);
         originx = r.x + r.width / 2;
         originy = r.y + ballheight / 2 +
            (int)(0.5 + 0.5 * (r.height + zoom*(coordmax.y+coordmin.y)));
         coord_to_xy(zoom, originx, originy, ballwidth, ballheight);
      }

       public Color[] getColors() {
			Color[] ballcolors = group.getColors();
			Color[] result = new Color[ballcolors.length + 1];
			
			for (int i = 0; i < ballcolors.length; i++)
				result[i] = ballcolors[i];
				
			result[ballcolors.length] = Color.black;
			return result;
      }
   }
