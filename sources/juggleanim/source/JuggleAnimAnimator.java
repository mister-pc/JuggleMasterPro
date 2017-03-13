// JuggleAnimAnimator.java
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
   import java.lang.reflect.*;
   import java.net.*;


    class JuggleAnimAnimator extends Panel implements ActionListener, Runnable {
      protected int				appWidth;
      protected int				appHeight;
      protected Thread			engine;
      protected JuggleMovie		jm;
	  protected int				loopstorepeat;	// JuggleMovie loops until balls repeat
      protected boolean			engineStarted;
      protected boolean			enginePaused;
      protected boolean			engineRunning;
      protected boolean			loaded;
      public JuggleException		exception;
      protected String			message;
      protected Frame				parent = null;
      protected JuggleAnimDialog	jad;
   
      protected JuggleControls	jc;
   
      protected Graphics 			g = null;
      protected Graphics			offg = null;
      protected Image				offscreen = null;
      protected AMDProgressBarEmbed	pbe = null;
   
      protected boolean			usingmenu = false;
      protected MenuItem			animopen, animsave, animhtmlsave;
      protected MenuItem			animgifsave;
      protected MenuItem			animrestart, animprefs;
   
      public static final int 	ACTION_NONE = 0;
      public static final int 	ACTION_OPEN = 1;
      public static final int 	ACTION_SAVE = 2;
      public static final int 	ACTION_HTMLSAVE = 3;
      public static final int 	ACTION_GIFSAVE = 4;
      public static final int 	ACTION_RESTART = 5;
      public static final int		ACTION_ANIMPREFS = 6;
   
   
       public JuggleAnimAnimator() {
         this.addMouseListener(
                new MouseAdapter() {
                   public void mousePressed(MouseEvent me) {
                     if (engine != null && engine.isAlive())
                        setPaused(!enginePaused);
                  }
               });
      
         this.addComponentListener(
                new ComponentAdapter() {
                   public void componentResized(ComponentEvent e) {
                     if (engineStarted) {
                        JuggleAnimAnimator.this.restartJuggle();
                     }
                  }
               });
      
         this.setBackground(Color.white);
      }
   
       public JuggleAnimAnimator(Frame parent) {
         this();
         this.parent = parent;
      }
   
   	// stop the current animation thread, if one is running
       protected synchronized void killAnimationThread() {
         while ((engine != null) && engine.isAlive()) {
            setPaused(false);		// get thread out of pause so it can exit
            engineRunning = false;
            try {
               wait();				// wait for notify() from exiting run() method
            } 
                catch (InterruptedException ie) {
               }
         }
      
         engine = null;			// just in case animator doesn't initialize these
         engineStarted = false;
         enginePaused = false;
         engineRunning = false;
         exception = null;
         message = null;
         offg = null;
         offscreen = null;
         if (usingmenu)
            updateMenu();
      }
   
       public synchronized void setPaused(boolean wanttopause) {
         if ((enginePaused == true) && (wanttopause == false)) {
            notify();		// wake up wait() in run() method
         }
         enginePaused = wanttopause;
      }
   
   
       protected void restartJuggle(JuggleControls newjc) {
         killAnimationThread();
         appWidth = this.getSize().width;		// initialize variables to defaults
         appHeight = this.getSize().height;
         engine = null;
         jm = null;
         exception = null;
         message = null;
         jc = newjc;
         g = this.getGraphics();
         offg = null;
         offscreen = null;
         engine = new Thread(this);
         engine.start();
      }
   
       public void restartJuggle(String input) {
         try {
            restartJuggle(parseInput(input));
         } 
             catch (JuggleException je) {
               drawstring(je.getMessage());
               exception = je;
            }
      }
   
       public void restartJuggle() {
         if (jc != null)
            restartJuggle(jc);
      }
   
       public synchronized void setSize(int width, int height) {
         super.setSize(width, height);
         restartJuggle();
      }
   
       public synchronized void setSize(Dimension dim) {
         super.setSize(dim);
         restartJuggle();
      }
   
       public void run()  {		// Called when this object becomes a thread
         PhysicalPattern		pat = null;
         PhysicalHands		hands = null;
         PhysicalJuggler		jug = null;
         PhysicalGroup		group = null;
         Color				backcolor = null, jugcolor = null;
         long				time_start, time_wait;
      
         Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
         enginePaused = false;
         engineStarted = false;
         engineRunning = true;
         loaded = false;
      
         try {
         	// First create a PhysicalPattern of the type requested
            drawstring(message = "Parsing pattern");
         
            if (jc.modestring.equalsIgnoreCase("siteswap")) {
            	// could get siteswap-specific parameters here
               pat = new PhysicalAsyncSiteSwap(jc.patstring);
               hands = new PhysicalHandsDefault(2, jc.handpath);
               jug = new PhysicalJugglerDefault();
            } 
            else if (jc.modestring.equalsIgnoreCase("syncsiteswap")) {
               pat = new PhysicalSyncSiteSwap(jc.patstring);
               hands = new PhysicalHandsDefault(2, jc.handpath);
               jug = new PhysicalJugglerDefault();
            } 
            else
               throw new JuggleException("Unknown mode specified");
         
            if (pat != null) {
               Rectangle		r = null;
               PhysicalConst	con = null;
            
               r = new Rectangle(jc.border, jc.border,
                  appWidth-2*jc.border, appHeight-2*jc.border);
            
            	// The following is a hack which adjusts tps to
            	// achieve the desired maximum throw height.
               if (jc.mat_hr > 0.0) {
                  try {
                     jc.tps = Math.sqrt(jc.accel / (8.0 * jc.mat_hr *
                        (double)pat.getMaxThrow())) *
                        ((double)pat.getMaxThrow() - jc.dwell);
                  } 
                      catch (ArithmeticException e) {
                     }
               }
            
               con = new PhysicalConst(jc.accel, jc.tps, jc.dwell,
                  jc.handscoop, jc.balldiam, jc.bouncefrac);
            
               drawstring(message = "Laying out pattern");
            
               group = new PhysicalGroup(pat, hands, jug, con, jc.colors);
				loopstorepeat = group.getLoopsToRepeat();
				
            	// Now we make the movie.
               drawstring(message = "Making movie");
               if (jc.ballimage == null)
                  jm = new JuggleMovieSimple(group,
                     jc.balldiam, this, r, jc.fps, jc.slowdown);
               else			
                  jm = new JuggleMovieSimple(group,
                     jc.ballimage, this, r, jc.fps, jc.slowdown);
				jm.setImagePermutation(pat.getPathPerm());
               loaded = true;
            }
         
            if (!loaded)
               return;
         
            pat = null;					// we have our movie, now try to free up
            hands = null;				// some memory
            jug = null;
            group = null;
            jm.flush_coords();
            System.gc();
         
            if (jc.startPause) {
               drawstring(message = "Click to start");
               enginePaused = true;
               while (enginePaused && engineRunning) {
                  synchronized (this) {
                     try {
                        wait();
                     } 
                         catch (InterruptedException ie) {
                        }
                  }
               }
            }
         
            message = null;
            if (jc.dbuffer) {
               offscreen = this.createImage(appWidth, appHeight);
               offg = offscreen.getGraphics();
            }
         
            backcolor = this.getBackground();
            jugcolor = Color.black;
         
            time_start = System.currentTimeMillis();
         
            engineStarted = true;
            if (usingmenu)
               updateMenu();
         
            while (engineRunning)  {
               if (jc.dbuffer) {
                  offg.setColor(backcolor);
                  offg.fillRect(0, 0, appWidth, appHeight);
                  offg.setColor(jugcolor);
                  jm.draw_frame(offg, this);
                  g.drawImage(offscreen, 0, 0, this);
               } 
               else {
                  g.setColor(backcolor);
                  g.fillRect(0, 0, appWidth, appHeight);
                  g.setColor(jugcolor);
                  jm.draw_frame(g, this);
               }
			   
               try {
                  time_wait = jm.get_duration()-System.currentTimeMillis()+time_start;
                  if (time_wait > 0)
                     Thread.sleep(time_wait);
                  time_start = System.currentTimeMillis();
               } 
                   catch (InterruptedException ie)  {
                  // What should we do here?
                     throw new JuggleException("Animator was interrupted");
                  }
               jm.advance_frame();
            
               while (enginePaused && engineRunning) {
                  synchronized (this) {
                     try {
                        wait();
                     } 
                         catch (InterruptedException ie) {
                        }
                  }
               }
            }
         
         } 
             catch (JuggleException je) {
               drawstring(je.getMessage());
               exception = je;
            } finally {
            if (offg != null) {
               offg.dispose();
               offg = null;
            }
            engineStarted = engineRunning = enginePaused = false;
            engine = null;	// this is critical as it signals restartJuggle() that exit is occurring
         }
         synchronized (this) {
            notify();	// tell possible thread wait()ing in restartJuggle() that animator thread is exiting
         }
      }
   
       public void paint(Graphics g) {			// overrides Component.paint()
         if (this.pbe != null)
            this.pbe.paint(g);
         if (exception != null)
            drawstring(exception.getMessage());
         else if (message != null)
            drawstring(message);
         else if (loaded && (jm != null))
            jm.draw_frame(g, this);
      }
   
       protected void drawstring(String message) {
         int			x, y, width;
      
         if (g == null)
            return;
      
         FontMetrics	fm = g.getFontMetrics();
      
         width = fm.stringWidth(message);
         x = (appWidth > width) ? (appWidth-width)/2 : 0;
         y = (appHeight + fm.getHeight()) / 2;
         g.setColor(this.getBackground());
         g.fillRect(0, 0, appWidth, appHeight);
         g.setColor(Color.black);
         g.drawString(message, x, y);
      }
   
       public boolean getAnimInited() {
         return engineStarted;
      }
   
       public String getLastInput() {
         return (jc == null) ? null : jc.inputstring;
      }
   
       public JuggleControls getJuggleControls() {
         return jc;
      }
   
       public Menu makeMenu(ActionListener closetarget) {
         if (parent == null)		// need frame to implement actions
            return null;
      
         Menu animmenu = new Menu("Animation");
         this.animopen = new MenuItem("Open Pattern...");
         this.animopen.setActionCommand("open");
         this.animopen.addActionListener(this);
         animmenu.add(this.animopen);
         if (closetarget != null) {
            MenuItem animclose = new MenuItem("Close");
            animclose.setShortcut(new MenuShortcut(KeyEvent.VK_W, false));
            animclose.setActionCommand("close");
            animclose.addActionListener(closetarget);
            animmenu.add(animclose);
         }
         animmenu.addSeparator();
         this.animsave = new MenuItem("Save Pattern As...");
         this.animsave.setActionCommand("savepattern");
         this.animsave.addActionListener(this);
         animmenu.add(this.animsave);
         this.animhtmlsave = new MenuItem("Save HTML As...");
         this.animhtmlsave.setActionCommand("savehtml");
         this.animhtmlsave.addActionListener(this);
         animmenu.add(this.animhtmlsave);
         this.animgifsave = new MenuItem("Save Animated GIF As...");
         this.animgifsave.setActionCommand("savegifanim");
         this.animgifsave.addActionListener(this);
         animmenu.add(this.animgifsave);
         animmenu.addSeparator();
         this.animrestart = new MenuItem("Restart");
         this.animrestart.setActionCommand("restart");
         this.animrestart.addActionListener(this);
         animmenu.add(this.animrestart);
         animmenu.addSeparator();
         this.animprefs = new MenuItem("Animation Preferences");
         animprefs.setShortcut(new MenuShortcut(KeyEvent.VK_P, false));
         this.animprefs.setActionCommand("prefs");
         this.animprefs.addActionListener(this);
         animmenu.add(this.animprefs);
         this.usingmenu = true;
         updateMenu();
         return animmenu;
      }
   
   	// enable/disable menu options, as appropriate
       public void updateMenu() {
         if (engineStarted) {
            animsave.setEnabled(true);
            animhtmlsave.setEnabled(true);
            animgifsave.setEnabled(jc.dbuffer);
            animrestart.setEnabled(true);
            animprefs.setEnabled(true);
         } 
         else {
            animsave.setEnabled(false);
            animhtmlsave.setEnabled(false);
            animgifsave.setEnabled(false);
            animrestart.setEnabled(false);
            animprefs.setEnabled(false);
         }
      }
   
   		// HTML shell for save below:
      static final String html1 = "<HTML>\n<HEAD>\n<TITLE>";
      static final String html2 = "</TITLE>\n</HEAD>\n<BODY>\n" +
      "<p>\n<CENTER>\n<APPLET CODE=\"JuggleAnim.class\" ARCHIVE=\"JuggleAnim.jar\" WIDTH=400 HEIGHT=480>\n"+
      "<PARAM name=config value=\"lister=false;animator=panel;editor=false\">\n<PARAM name=pattern value=\"";
      static final String html3 = "\">\nIf you had a Java-capable browser there would be an animation here\n"+
      "</APPLET>\n</CENTER>\n</p>\n</BODY>\n</HTML>";
   
   
       public void actionPerformed(ActionEvent ae) {
         String command = ae.getActionCommand();
      
         if (command.equals("open"))
            doMenuCommand(ACTION_OPEN);
         else if (command.equals("savepattern"))
            doMenuCommand(ACTION_SAVE);
         else if (command.equals("savehtml"))
            doMenuCommand(ACTION_HTMLSAVE);
         else if (command.equals("savegifanim"))
            doMenuCommand(ACTION_GIFSAVE);
         else if (command.equals("restart"))
            doMenuCommand(ACTION_RESTART);
         else if (command.equals("prefs"))
            doMenuCommand(ACTION_ANIMPREFS);
      }
   
       public void doMenuCommand(int action) {
         switch (action) {
            case ACTION_NONE:
               break;
         
            case ACTION_OPEN:
               {
                  FileDialog fdialog = new FileDialog(parent, "Open Pattern", FileDialog.LOAD);
                  fdialog.pack();		// bug workaround?
                  fdialog.show();		// blocks until returns
                  String filename = fdialog.getFile();
                  String dir = fdialog.getDirectory();
                  fdialog.dispose();
               
                  if (filename != null) {
                     BufferedReader instream = null;
                  
                     try {
                        parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        String lineread = null;
                        StringBuffer sb = new StringBuffer();
                     
                        File infile = new File(dir, filename);
                        instream = new BufferedReader(new FileReader(infile));
                        while ((lineread = instream.readLine()) != null)
                           sb.append(lineread);
                        instream.close(); instream = null;
                        this.restartJuggle(sb.toString());
                        parent.setCursor(Cursor.getDefaultCursor());
                     } 
                         catch (FileNotFoundException ex) {
                           parent.setCursor(Cursor.getDefaultCursor());
                           new LabelDialog(parent, "Error", "File not found");
                        } 
                         catch (IOException ioex) {
                           if (instream != null) {
                              try { instream.close(); } 
                                  catch (IOException ioe) {}
                              instream = null;
                           }
                           parent.setCursor(Cursor.getDefaultCursor());
                           new LabelDialog(parent, "Error", "I/O Error while opening");
                        }
                  }
               }
               break;
         
            case ACTION_SAVE:
               if (this.getAnimInited()) {
                  FileDialog fdialog = new FileDialog(parent, "Save Pattern",
                  FileDialog.SAVE);
                  fdialog.pack();		// bug workaround?
                  fdialog.show();		// blocks until returns
                  String filename = fdialog.getFile();
                  String dir = fdialog.getDirectory();
                  fdialog.dispose();
               
                  if (filename != null) {
                     parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                     File outfile = null;
                     PrintWriter outstream = null;
                  
                     try {
                        outfile = new File(dir, filename);
                        outstream = new PrintWriter(new FileOutputStream(outfile));
                        outstream.println(this.getLastInput());
                        outstream.close(); outstream = null;
                        parent.setCursor(Cursor.getDefaultCursor());
                     } 
                         catch (IOException ioex) {
                           if (outstream != null) { outstream.close(); }
                           if (outfile != null) { outfile.delete(); }
                           parent.setCursor(Cursor.getDefaultCursor());
                           new LabelDialog(parent, "Error", "I/O Error while saving");
                        }
                  }
               } 
               else {
                  new LabelDialog(parent, "Message", "There is no animation running");
               		// blocks until OK hit
               }
               break;
         
            case ACTION_HTMLSAVE:
               if (this.getAnimInited()) {
                  FileDialog fdialog = new FileDialog(parent, "Save As HTML",
                  FileDialog.SAVE);
                  fdialog.pack();		// bug workaround?
                  fdialog.show();		// blocks until returns
                  String filename = fdialog.getFile();
                  String dir = fdialog.getDirectory();
                  fdialog.dispose();
               
                  if (filename != null) {
                     parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                     File outfile = null;
                     PrintWriter outstream = null;
                  
                     try {
                        outfile = new File(dir, filename);
                        outstream = new PrintWriter(new FileOutputStream(outfile));
                        outstream.print(JuggleAnimAnimator.html1);
                     	// print HTML title here.  Use "Pattern" for now.
                        outstream.print("Pattern");
                        outstream.print(JuggleAnimAnimator.html2);
                        outstream.print(this.getLastInput());
                        outstream.print(JuggleAnimAnimator.html3);
                        outstream.close(); outstream = null;
                        parent.setCursor(Cursor.getDefaultCursor());
                     } 
                         catch (IOException ioex) {
                           if (outstream != null) { outstream.close(); }
                           if (outfile != null) { outfile.delete(); }
                           parent.setCursor(Cursor.getDefaultCursor());
                           new LabelDialog(parent, "Error", "I/O Error while saving HTML");
                        }
                  }
               } 
               else {
                  new LabelDialog(parent, null, "There is no animation running");
               		// blocks until OK hit
               }
               break;
         
            case ACTION_GIFSAVE:
               if (this.getAnimInited()) {
                  FileDialog fdialog = new FileDialog(parent, "Save As Animated GIF",
                  FileDialog.SAVE);
                  fdialog.pack();		// bug workaround?
                  fdialog.show();		// blocks until returns
                  String filename = fdialog.getFile();
                  String dir = fdialog.getDirectory();
                  fdialog.dispose();
               
                  if (filename != null) {
                     File outfile = null;
                     FileOutputStream out = null;
                  //						ProgressBarDialog pbd = null;
                  
                     try {
                        outfile = new File(dir, filename);
                        out = new FileOutputStream(outfile);
                     //							pbd = new ProgressBarDialog(parent, "Progress",
                     //										"Saving Animated GIF...");
                        pbe = new AMDProgressBarEmbed(this);
                        Dimension panelsize = this.getSize();
                        int pbarwidth = (panelsize.width>220) ?
                        200 : panelsize.width-20;
                        pbe.reshape((panelsize.width-pbarwidth)/2,
                           panelsize.height/2-10, pbarwidth, 20);
                     //							pbd.show();
                        this.saveGIFAnim(pbe, out);
                        out.close(); out = null;
                     //							pbd.dispose();
                     //							pbd = null;
                        pbe = null;
                     } 
                         catch (Exception ex) {
                        // IO Error or user hit cancel
                           if (out != null) {
                              try { out.close(); } 
                                  catch (IOException ioe) {}
                              out = null;
                           }
                           if (outfile != null) { outfile.delete(); }
                           if (pbe != null)     { pbe = null; }
                        
                           if (ex instanceof IOException)
                              new LabelDialog(parent, "Error",
                                 "I/O Error while saving animation");
                        }
                  }
               } 
               else {
                  new LabelDialog(parent, "Message", "There is no animation running");
               }
               break;
         
            case ACTION_RESTART:
               parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
               this.restartJuggle();
               parent.setCursor(Cursor.getDefaultCursor());
               break;
         
            case ACTION_ANIMPREFS:
               if (jad == null)
                  jad = new JuggleAnimDialog(parent);
            
               JuggleControls oldjc = getJuggleControls();
               JuggleControls newjc = jad.getPrefs(oldjc);
               if (newjc != oldjc)	 {	// user made change?
                  parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                  restartJuggle(newjc);
                  parent.setCursor(Cursor.getDefaultCursor());
               }
               break;
         }
      }
   
   
       protected void saveGIFAnim(AMDProgressBarEmbed pbe, OutputStream out) throws IOException, JuggleException {
         if (!jc.dbuffer)
            return;
      
         loaded = false;		// disable paint()ing of animation frames
      
         Class gawclass = null;
         Method docolormap1 = null;	// public void doColorMap(Color color, boolean defaultcolor) throws IOException;
         Method docolormap2 = null;	// public void doColorMap(Color[] colorarray) throws IOException;
         Method docolormap3 = null;	// public void doColorMap(Color color) throws IOException;
         Method writeheader = null;	// public void writeHeader(OutputStream out) throws IOException;
         Method writedelay = null;	// public void writeDelay(int delay, OutputStream out) throws IOException;
         Method writegif = null;		// public void writeGIF(Image img, OutputStream out) throws IOException;
         Method writetrailer = null;	// public void writeTrailer(OutputStream out) throws IOException;
      
         try {
            gawclass = Class.forName("gifwriter.GIFAnimWriter");
         } 
             catch (ClassNotFoundException cnfe) {
               try {
                  URL[] jarurl = new URL[] {new URL("file:GIFAnimWriter.jar")};
                  URLClassLoader loader = new URLClassLoader(jarurl);
                  gawclass = Class.forName("gifwriter.GIFAnimWriter", true, loader);
               } 
                   catch (MalformedURLException ex) {
                     throw new JuggleException("Malformed URL");
                  } 
                   catch (ClassNotFoundException cnfe2) {
                     new LabelDialog(parent, "File not found", "Required file GIFAnimWriter.jar not found");
                     return;
                  }
            }
         try {
            docolormap1 = gawclass.getMethod("doColorMap", new Class[] {Color.class, Boolean.TYPE});
            docolormap2 = gawclass.getMethod("doColorMap", new Class[] {Color[].class});
            docolormap3 = gawclass.getMethod("doColorMap", new Class[] {Color.class});
            writeheader = gawclass.getMethod("writeHeader", new Class[] {OutputStream.class});
            writedelay = gawclass.getMethod("writeDelay", new Class[] {Integer.TYPE, OutputStream.class});
            writegif = gawclass.getMethod("writeGIF", new Class[] {Image.class, OutputStream.class});
            writetrailer = gawclass.getMethod("writeTrailer", new Class[] {OutputStream.class});
         } 
             catch (NoSuchMethodException nsme) {
               throw new JuggleException("Could not find method: "+nsme.getMessage());
            } 
             catch (SecurityException se) {
               throw new JuggleException("Method not accessible (security): "+se.getMessage());
            }
      
         int current_frame = jm.getFrameNum();
      
         try {
            this.setPaused(true);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         
         // Create the object that will actually do the writing
            Object gaw = null;
            try {
               gaw = gawclass.newInstance();
            } 
                catch (IllegalAccessException iae) {
                  throw new JuggleException("Cannot access gifwriter.GIFAnimWriter class (security)");
               } 
                catch (InstantiationException ie) {
                  throw new JuggleException("Could not instantiate gifwriter.GIFAnimWriter object");
               }
         
            Color backcolor = this.getBackground();
            Color jugcolor = Color.black;
         
         // Build the colormap that the animated GIF will use
         // first figure out how many distinct colors we have
            docolormap3.invoke(gaw, new Object[] {backcolor});
            docolormap3.invoke(gaw, new Object[] {jugcolor});
            docolormap2.invoke(gaw, new Object[] {jm.getColors()});
         // set black as default drawing color
            docolormap1.invoke(gaw, new Object[] {Color.black, new Boolean(true)});
         
            writeheader.invoke(gaw, new Object[] {out});
         
            int start_frame = jm.getLoopStartFrameNum();
            int end_frame = jm.getLoopEndFrameNum();
            int totalsteps = (end_frame - start_frame + 1) * this.loopstorepeat;
            int stepcount = 0;
         
            jm.setFrameNum(start_frame);
         
         // write out the individual frames
			for (int j = 0; j < this.loopstorepeat; j++) {
				for (int i = start_frame; i <= end_frame; i++) {
					writedelay.invoke(gaw, new Object[] {new Integer((int)(jm.get_duration()/10)), out});
					offg.setColor(backcolor);
					offg.fillRect(0, 0, appWidth, appHeight);
					offg.setColor(jugcolor);
					jm.draw_frame(offg, this);
					writegif.invoke(gaw, new Object[] {offscreen, out});
					jm.advance_frame();
					if (pbe != null) {
						stepcount++;
						pbe.setPercent((double)stepcount / (double)totalsteps);
					}
				}
			}
         
            writetrailer.invoke(gaw, new Object[] {out});
         
         } 
             catch (IllegalAccessException iae) {
               throw new JuggleException("AnimGIFSave IllegalAccessException: "+iae.getMessage());
            } 
             catch (IllegalArgumentException iae) {
               throw new JuggleException("AnimGIFSave IllegalArgumentException: "+iae.getMessage());
            } 
             catch (InvocationTargetException ite) {
               throw new JuggleException("AnimGIFSave InvocationTargetException: "+ite.getMessage());
            } 
             catch (JuggleException je) {
               new LabelDialog(parent, "Error", je.getMessage());
            } finally {
            jm.setFrameNum(current_frame);
            this.setCursor(Cursor.getDefaultCursor());
            this.setPaused(false);
         }
      }
   
       protected static JuggleControls parseInput(String input) throws JuggleException {
         int		tempint;
         double 	tempdouble;
         JuggleControls jc = new JuggleControls();
         double rightthrowx = 0.2;
         double rightcatchx = 0.5;
         double leftthrowx = -0.2;
         double leftcatchx = -0.5;
      
         jc.inputstring = input;
      
         StringTokenizer st3 = new StringTokenizer(input, ";");
         int numparams = st3.countTokens();
         String param, value;
      
         for (int index = 0; index < numparams; index++) {
            StringTokenizer st4 = new StringTokenizer(st3.nextToken(), "=");
            if (st4.countTokens() != 2)
               throw new JuggleException("Syntax error in input");
         
            param = st4.nextToken().trim();
            value = st4.nextToken().trim();
         
            if (param.equalsIgnoreCase("mode"))
               jc.modestring = value;
            else if (param.equalsIgnoreCase("border"))
               try {
                  tempint = Integer.parseInt(value);
                  jc.border = tempint;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("startpaused"))
               jc.startPause = Boolean.valueOf(value).booleanValue();
            else if (param.equalsIgnoreCase("dbuffer"))
               jc.dbuffer = Boolean.valueOf(value).booleanValue();
            else if (param.equalsIgnoreCase("g"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.accel = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("tps"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.tps = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("dwell"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  if ((tempdouble > 0.0) && (tempdouble < 1.9))
                     jc.dwell = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("throwx"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  rightthrowx = tempdouble;
                  leftthrowx = -tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("rightthrowx"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  rightthrowx = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("leftthrowx"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  leftthrowx = -tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("catchx"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  rightcatchx = tempdouble;
                  leftcatchx = -tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("rightcatchx"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  rightcatchx = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("leftcatchx"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  leftcatchx = -tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("balldiam"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.balldiam = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("bouncefrac"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.bouncefrac = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("slowdown"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.slowdown = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("fps"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.fps = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("handscoop"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.handscoop = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("pattern"))
               jc.patstring = value;
            else if (param.equalsIgnoreCase("colors"))
               jc.colors = value;
            else if (param.equalsIgnoreCase("mat_style")) {
               try {
                  StringTokenizer	st1 = new StringTokenizer(value, "}", false);
                  StringTokenizer st2 = null;
                  String			st = null;
                  int				size = st1.countTokens();
               
                  if ((size % 2) == 1)
                     throw new JuggleException();
                  boolean dbl = ((size%4)==0) ? false : true;
                  jc.handpath = new double[dbl ? 2*size : size][2];
                  try {
                     for (tempint = 0; tempint < size/2; tempint++) {
                        for (int i = 0; i < 2; i++) {
                           st = st1.nextToken().replace('{', ' ');
                           st2 = new StringTokenizer(st, ",", false);
                           jc.handpath[2*tempint+i][0] = 0.025 *
                              Double.valueOf(st2.nextToken()).doubleValue();
                           jc.handpath[2*tempint+i][1] = 0.05 *
                              Double.valueOf(st2.nextToken()).doubleValue();
                        }
                     }
                  } 
                      catch (NumberFormatException e) {
                        throw new JuggleException();
                     } 
                      catch (NoSuchElementException e) {
                        throw new JuggleException();
                     }
               
                  if (dbl) {
                     for (tempint = size; tempint < (2*size); tempint++) {
                        jc.handpath[tempint][0] = jc.handpath[tempint-size][0];
                        jc.handpath[tempint][1] = jc.handpath[tempint-size][1];
                     }
                  }
                  for (tempint = 2; tempint < (dbl?2*size:size); tempint+=4) {
                     jc.handpath[tempint][0] *= -1;		// convert left hand to
                     jc.handpath[tempint+1][0] *= -1;	// real coordinates
                  }
               } 
                   catch (JuggleException je) {
                     throw new JuggleException("Syntax error in hand path");
                  }
            }
            else if (param.equalsIgnoreCase("mat_DR"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.dwell = 2.0 * tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else if (param.equalsIgnoreCase("mat_HR"))
               try {
                  tempdouble = Double.valueOf(value).doubleValue();
                  jc.mat_hr = tempdouble;
               } 
                   catch (NumberFormatException e) {
                  }
            else
               throw new JuggleException("Syntax error in input");
         
         }
      
         if (jc.handpath == null) {			// not created by mat_style
            jc.handpath = new double[4][2];
            jc.handpath[0][0] = rightcatchx;
            jc.handpath[0][1] = 0.0;
            jc.handpath[1][0] = rightthrowx;
            jc.handpath[1][1] = 0.0;
            jc.handpath[2][0] = leftcatchx;
            jc.handpath[2][1] = 0.0;
            jc.handpath[3][0] = leftthrowx;
            jc.handpath[3][1] = 0.0;
         }
      
         return jc;
      }
   
       public void dispose() {
         killAnimationThread();
         if (jad != null) {
            jad.dispose();
            jad = null;
         }
      }
   }
