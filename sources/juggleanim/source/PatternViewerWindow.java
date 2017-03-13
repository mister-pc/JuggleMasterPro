// PatternViewerWindow.java
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
import java.io.*;
import java.awt.event.*;


public class PatternViewerWindow extends Frame implements ActionListener {
	protected PatternViewer pv;
	protected PatternViewerDialog pvd;
	
	MenuBar menubar;
	Menu filemenu, animmenu;
	MenuItem openpatfile, /*patviewclose,*/ savepats, savenames;
	MenuItem aboutbox, patviewprefs, patviewquit;
	
	static final int ACTION_NONE = 0;
	static final int ACTION_OPENPATFILE = 1;
	static final int ACTION_SAVEPATFILE = 2;
	static final int ACTION_SAVEPATNAMES = 3;
	static final int ACTION_ABOUT = 4;
	static final int ACTION_LAYOUTPREFS = 5;
	static final int ACTION_QUIT = 6;
	
	
	public PatternViewerWindow(String name, Reader patternfile,
						String config) throws JuggleException {
		this(name, 800, 600, patternfile, config);
	}
						
	public PatternViewerWindow(String name, int width, int height, Reader patternfile,
						String config) throws JuggleException {
		super(name);
		this.setSize(width, height);
		this.setResizable(true);
		
		try {
			pv = new PatternViewer(patternfile, config, this);
			
			this.makeMenu();
			this.updateMenu();
			
			GridBagLayout gb = new GridBagLayout();
		
			this.setLayout(gb);
			this.add(pv);
			
			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.anchor = GridBagConstraints.CENTER;
			gbc1.fill = GridBagConstraints.BOTH;
			gbc1.gridheight = gbc1.gridwidth = 1;
			gbc1.gridx = gbc1.gridy = 0;
			gbc1.insets = new Insets(10,10,10,10);
			gbc1.weightx = gbc1.weighty = 1.0;
			
			gb.setConstraints(pv, gbc1);
			this.doLayout();
			this.show();
		}
		catch (JuggleException je) {
			this.dispose();
			throw je;
		}
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				PatternViewerWindow.this.dispose();
			}
		});
	}
	
	
	protected void makeMenu() {
		menubar = new MenuBar();
		this.setMenuBar(menubar);
		filemenu = new Menu("File");
		openpatfile = new MenuItem("Open Pattern List...");
		openpatfile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		openpatfile.setActionCommand("openpatfile");
		openpatfile.addActionListener(this);
		filemenu.add(openpatfile);
		filemenu.addSeparator();
		savepats = new MenuItem("Save Pattern List As...");
		savepats.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		savepats.setActionCommand("savepatfile");
		savepats.addActionListener(this);
		filemenu.add(savepats);
		savenames = new MenuItem("Save Pattern Names As...");
		savenames.setShortcut(new MenuShortcut(KeyEvent.VK_S, true));
		savenames.setActionCommand("savepatnames");
		savenames.addActionListener(this);
		filemenu.add(savenames);
		filemenu.addSeparator();
		aboutbox = new MenuItem("About JuggleAnim");
		aboutbox.setActionCommand("about");
		aboutbox.addActionListener(this);
		filemenu.add(aboutbox);
		patviewprefs = new MenuItem("Layout Preferences");
		patviewprefs.setActionCommand("layoutprefs");
		patviewprefs.addActionListener(this);
		filemenu.add(patviewprefs);
		filemenu.addSeparator();
		patviewquit = new MenuItem("Quit JuggleAnim");
		patviewquit.setShortcut(new MenuShortcut(KeyEvent.VK_Q, false));
		patviewquit.setActionCommand("quit");
		patviewquit.addActionListener(this);
		filemenu.add(patviewquit);
		menubar.add(filemenu);
		
		if (pv.getAnimatorType() == PatternViewerPrefs.ANIMATOR_PANEL) {
				// The animator is not a frame, so it can't handle its
				// own menu events.  PatternViewerWindow will have to
				// do this for it.
			animmenu = pv.getJuggleAnim().makeMenu(null);
			menubar.add(animmenu);
		}
	}
	
	
	protected synchronized void updateMenu() {
		if (pv.getPatternCount() == 0) {
			savepats.setEnabled(false);
			savenames.setEnabled(false);
		} else {
			savepats.setEnabled(true);
			savenames.setEnabled(true);
		}
	}
	
		// Implements ActionListener interface, to listen for MenuItem events
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		
		if (command.equals("openpatfile"))
			doMenuCommand(ACTION_OPENPATFILE);
		else if (command.equals("savepatfile"))
			doMenuCommand(ACTION_SAVEPATFILE);
		else if (command.equals("savepatnames"))
			doMenuCommand(ACTION_SAVEPATNAMES);
		else if (command.equals("about"))
			doMenuCommand(ACTION_ABOUT);
		else if (command.equals("layoutprefs"))
			doMenuCommand(ACTION_LAYOUTPREFS);
		else if (command.equals("quit"))
			doMenuCommand(ACTION_QUIT);
	}


	protected void doMenuCommand(int action) {
		switch (action) {
			case ACTION_OPENPATFILE:
				{
				FileDialog fdialog = new FileDialog(this, "Open Pattern File", FileDialog.LOAD);
				fdialog.pack();		// bug workaround?
				fdialog.show();		// blocks until returns
				String filename = fdialog.getFile();
				String dir = fdialog.getDirectory();
				fdialog.dispose();
				
				if (filename != null) {
					FileReader instream = null;
					
					try {
						this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						File infile = new File(dir, filename);
						instream = new FileReader(infile);
						pv.readfile(instream);
						this.updateMenu();
						this.setCursor(Cursor.getDefaultCursor());
					} catch (FileNotFoundException ex) {
						this.setCursor(Cursor.getDefaultCursor());
						new LabelDialog(this, "Error", "File not found");
					} catch (IOException ioex) {
						if (instream != null) {
							try { instream.close(); } catch (IOException ioe) {}
							instream = null;
						}
						this.setCursor(Cursor.getDefaultCursor());
						new LabelDialog(this, "Error", "I/O Error while opening");
					}
				}
				}
				break;
				
			case ACTION_SAVEPATFILE:
				{
				FileDialog fdialog = new FileDialog(this, "Save Pattern List",
								FileDialog.SAVE);
				fdialog.pack();		// bug workaround?
				fdialog.show();		// blocks until returns
				String filename = fdialog.getFile();
				String dir = fdialog.getDirectory();
				fdialog.dispose();
				
				if (filename != null) {
					File outfile = null;
					PrintWriter outstream = null;
					
					try {
						this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						outfile = new File(dir, filename);
						outstream = new PrintWriter(new FileOutputStream(outfile));
						pv.printpatterns(outstream, true);
						outstream.close(); outstream = null;
						this.setCursor(Cursor.getDefaultCursor());
					} catch (IOException ioex) {
						if (outstream != null) { outstream.close(); }
						if (outfile != null) { outfile.delete(); }
						this.setCursor(Cursor.getDefaultCursor());
						new LabelDialog(this, "Error", "I/O Error while saving");
					}
				}
				}
				break;
				
			case ACTION_SAVEPATNAMES:
				{
				FileDialog fdialog = new FileDialog(this, "Save Pattern Names",
								FileDialog.SAVE);
				fdialog.pack();		// bug workaround?
				fdialog.show();		// blocks until returns
				String filename = fdialog.getFile();
				String dir = fdialog.getDirectory();
				fdialog.dispose();
				
				if (filename != null) {
					File outfile = null;
					PrintWriter outstream = null;
					
					try {
						this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						outfile = new File(dir, filename);
						outstream = new PrintWriter(new FileOutputStream(outfile));
						pv.printpatterns(outstream, false);
						outstream.close(); outstream = null;
						this.setCursor(Cursor.getDefaultCursor());
					} catch (IOException ioex) {
						if (outstream != null) { outstream.close(); }
						if (outfile != null) { outfile.delete(); }
						this.setCursor(Cursor.getDefaultCursor());
						new LabelDialog(this, "Error", "I/O Error while saving");
					}
				}
				}
				break;
			
			case ACTION_ABOUT:
				AboutBox ab = new AboutBox();
				ab.setResizable(false);
				ab.setVisible(true);
				ab.show();
				break;
				
			case ACTION_LAYOUTPREFS:
				{
				if (this.pvd == null)
					pvd = new PatternViewerDialog(this);
				
				try {
					PatternViewerPrefs oldpvp = pv.getPrefs();
					PatternViewerPrefs newpvp = pvd.getPrefs(oldpvp);
					if (newpvp != oldpvp) {		// user made change?
						this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						pv.configure(newpvp);
						this.makeMenu();
						this.updateMenu();
						if (pv.getAnimatorType() == 
										PatternViewerPrefs.ANIMATOR_PANEL)
							pv.getJuggleAnim().updateMenu();

						this.validate();
						
						this.setCursor(Cursor.getDefaultCursor());
					}
				} catch (JuggleException je) {
					new LabelDialog(this, "Error", je.getMessage());
				}
				}
				break;
				
			case ACTION_QUIT:
				System.exit(0);
				break;
		}
	}
	
	public void animatePattern(String label, String pattern) {
		if (pv != null)
			pv.animatePattern(label, pattern);
	}
	
	public synchronized void dispose() {
		super.dispose();
		
		if (pv != null) {
			pv.dispose();
			pv = null;
		}
		if (pvd != null) {
			pvd.dispose();
			pvd = null;
		}
	}
}

