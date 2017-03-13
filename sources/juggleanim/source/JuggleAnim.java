// JuggleAnim.java
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


import java.applet.*;
import java.awt.*;
import java.io.*;
import java.net.*;


// This class is the applet itself.  It parses the parameters given to it and
// creates PhysicalPattern, PhysicalHands, and PhysicalJuggler objects of
// the required types.  These objects are then used to create a JuggleMovie,
// which is animated by calling the JuggleMovie's draw_frame() method
// repeatedly from an independent thread that we start up.  Clicking within
// the applet pauses the animation.

public class JuggleAnim extends Applet {
	protected PatternViewer 		pv;
	protected PatternViewerWindow	pvw;
	protected Reader	 			patternfile;
	protected String				title;
	protected String				pattern;
	
	static public final String version = "1.4";
	
	
	public void init() {
		String patfilename = getParameter("patternfile");
		
		if (patfilename != null) {
			try {
				URL patURL = new URL(getCodeBase(), patfilename);
				patternfile = new InputStreamReader(patURL.openStream());
			}
			catch (MalformedURLException mue) {
				showStatus("Syntax error in pattern file URL '"+patfilename+"'");
			}
			catch (IOException ioe) {
				showStatus("Couldn't open pattern file");
			}
		}
		
		try {
			String config = getParameter("config");
			String type = getParameter("type");
			
			title = getParameter("title");
			if (title == null)
				title = "JuggleAnim";
			pattern = getParameter("pattern");

			if (type != null && type.trim().equalsIgnoreCase("window")) {
				this.pvw = new PatternViewerWindow(title, patternfile, config);
			} else {
				pv = new PatternViewer(patternfile, config, null);
				this.setLayout(new GridLayout(1,1));
				this.add(pv);
//				this.doLayout();
			}
		}
		catch (JuggleException je) {
			showStatus(je.getMessage());
		}
	}
	
	public void start() {		// Overrides Applet.start()
		if (pv != null)
			pv.animatePattern(title, pattern);
		if (pvw != null)
			pvw.animatePattern(title, pattern);
	}
	
	public void destroy() {		// Overrides Applet.destroy()
		if (pv != null) {
			pv.dispose();
			pv = null;
		}
		if (pvw != null) {
			pvw.dispose();
			pvw = null;
		}
	}

	
	public static void main(String[] args) {
		String config = "animator=panel;generator=siteswap;editor=true";
		Reader patternfile = null;
		
		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			System.out.println(
"JuggleAnim "+JuggleAnim.version+", copyright 2002 by Jack Boyce and others\n\n" +
"This program is free software; you can redistribute it and/or modify it under\n" +
"the terms of the GNU General Public License\n\n" +
"Five optional command line arguments can be used:\n" +
"   <pattern> <config> <pattern file name> <width> <height>\n" +
"See the JuggleAnim documentation for the formatting of the pattern and config\n" +
"parameters.\n\n" +
"Example:\n" +
"   JuggleAnim \"mode=siteswap;pattern=441\" \"animator=panel;generator=siteswap\"\n"
			);
			return;
		}
		
		try {
			String pattern = "mode=siteswap;pattern=3;slowdown=2.0;tps=3.5;mat_style={15,0}{4,0}";
			
			if (args.length > 0) {
				pattern = args[0];
				if (args.length > 1) {
					config = args[1];
			
					if ((args.length == 3) || (args.length == 5)) {
						try {
							patternfile = new FileReader(args[2]);
						}
						catch (IOException ioe) {
							throw new JuggleException("Couldn't open pattern file");
						}
					}
				}
			}
		
			PatternViewerWindow pvwin = null;
			
			if (args.length < 4)
				pvwin = new PatternViewerWindow("JuggleAnim", patternfile, config);
			else if (args.length == 4)
				pvwin = new PatternViewerWindow("JuggleAnim", Integer.parseInt(args[2]),
								Integer.parseInt(args[3]), null, config);
			else if (args.length == 5)
				pvwin = new PatternViewerWindow("JuggleAnim", Integer.parseInt(args[3]),
								Integer.parseInt(args[4]), patternfile, config);
			else
				throw new JuggleException("Syntax error in command line input");
			
			pvwin.animatePattern("JuggleAnim", pattern);
		}
		catch (JuggleException je) {
			System.out.println(je.getMessage());
		}
		catch (NumberFormatException nfe) {
			System.out.println("Syntax error in window dimensions");
		}
	}

}
