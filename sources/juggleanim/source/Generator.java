// Generator.java
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
                         

	// This class defines a general object that is capable of generating tricks
	// and converting them into commands that the animator understands.
public abstract class Generator {

		// The built-in generators
	public static final String[] builtinGenerators = { "siteswap" };
	
		// This is a factory to create Generators from names.  Note the
		// naming convention.
	public static Generator getGenerator(String name) throws JuggleException {
		try {
			Object obj = Class.forName(name.toLowerCase()+"Generator").newInstance();
			if (!(obj instanceof Generator))
				throw new JuggleException("Generator type '"+name+"' doesn't work");
			return (Generator)obj;
		}
		catch (ClassNotFoundException cnfe) {
			throw new JuggleException("Generator type '"+name+"' not found");
		}
		catch (IllegalAccessException iae) {
			throw new JuggleException("Cannot access '"+name+"' generator file (security)");
		}
		catch (InstantiationException ie) {
			throw new JuggleException("Couldn't create '"+name+"' generator");
		}
	}
	
	public abstract String getStartupMessage();
	public abstract void showControls();
	public abstract void killControls();
	
			// use parameters from controller frame
	public abstract void initGenerate() throws JuggleException;
	
			// use command line args
	public abstract void initGenerate(String[] args) throws JuggleException;
	
	public void initGenerate(String arg) throws JuggleException {
		int	i, numargs;
		StringTokenizer st = new StringTokenizer(arg, " \n");
		
		numargs = st.countTokens();
		String args[] = new String[numargs];
		
		for (i = 0; i < numargs; i++)
			args[i] = st.nextToken();
		
		this.initGenerate(args);
	}
	
	public abstract int runGenerate(PrintStream ps);	// print out patterns
	public abstract int runGenerate(java.awt.List lst);	// save them to list
			// The following are identical to the above, but they will
			// stop after finding max_num patterns
	public abstract int runGenerate(PrintStream ps, int max_num) throws JuggleException;
	public abstract int runGenerate(java.awt.List lst, int max_num) throws JuggleException;
	
		// The following methods do the same things -- the choice of which
		// to use depends on context.  Use the first for Classname.method()
		// calls, and the second for Object.method() calls.  The problem is
		// that ((ClassA)(SubclassOfClassA b)).makeAnimatorInput() calls
		// the parent's version, not the subclasses's.  An instance method
		// is needed to call the child's.
	public static String makeAnimatorInput(String pattern)	 { return null; }
	public abstract String virtual_makeAnimatorInput(String pattern);
}
