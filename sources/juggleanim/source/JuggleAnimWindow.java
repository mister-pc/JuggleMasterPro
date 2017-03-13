// JuggleAnimWindow.java
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


public class JuggleAnimWindow extends Frame implements ActionListener {
	protected JuggleAnimAnimator ja;
	protected boolean isalive;
	MenuBar menubar;
	Menu animmenu;

//	private int action;
//	private Thread gifthread;
	
//	public static final int ACTION_NONE = 0;
//	public static final int ACTION_CLOSE = 101;
//	public static final int ACTION_ANIMPREFS = 102;
	
	
	public JuggleAnimWindow(String name, String input) {
		this(name, input, 200, 250);
	}
	
	public JuggleAnimWindow(String name, String input, int width, int height) {
		super(name);
		this.setSize(width, height);
		this.setResizable(true);
//		this.action = ACTION_NONE;

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				if (action == ACTION_NONE)
					killJugglerWindow();
			}
		});
				
		ja = new JuggleAnimAnimator(this);
		
		menubar = new MenuBar();
		this.setMenuBar(menubar);
		animmenu = ja.makeMenu(this);
		menubar.add(animmenu);
		ja.updateMenu();
		
		GridBagLayout gb = new GridBagLayout();
		
		this.setLayout(gb);
		this.add(ja);
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.anchor = GridBagConstraints.CENTER;
		gbc1.fill = GridBagConstraints.BOTH;
		gbc1.gridheight = gbc1.gridwidth = 1;
		gbc1.gridx = gbc1.gridy = 0;
		gbc1.insets = new Insets(5,5,5,5);
		gbc1.weightx = gbc1.weighty = 1.0;
		
		gb.setConstraints(ja, gbc1);
		this.doLayout();
		this.show();
		ja.restartJuggle(input);
		this.isalive = true;
	}
	
	public void restartJuggle(String name, String input) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.setTitle(name);
		ja.restartJuggle(input);
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	public boolean isAlive() {
		return isalive;
	}
	
	public void killJugglerWindow() {
		this.dispose();
		this.isalive = false;
	}

		// Implements ActionListener to wait for MenuItem events
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("close")) {
			killJugglerWindow();
		}
	}
	
	
	public synchronized void dispose() {
		super.dispose();
		if (ja != null) {
			ja.dispose();
			ja = null;
		}
	}
}

