// JuggleAnimDialog.java
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

                         
class JuggleAnimDialog extends Dialog {
	protected TextField 	tf1, tf2, tf3;
	protected Checkbox 		cb1, cb2;
	protected Button 		but1, but2;
	
	protected JuggleControls newjc;
//	protected boolean finished = false;
	
	protected final static int border = 10;
	
	
	public JuggleAnimDialog(Frame parent) {
			// set up dialog
		super(parent, "Animation Preferences", true);
		this.setResizable(false);

		GridBagLayout gb = new GridBagLayout();
		
		this.setLayout(gb);
		
		Panel p1 = new Panel();			// animator type
		p1.setLayout(gb);
		Label lab1 = new Label("Frames Per Second:");
		p1.add(lab1);
		gb.setConstraints(lab1, make_constraints(GridBagConstraints.EAST,0,0,
					new Insets(0,0,0,0)));
		tf1 = new TextField(5);
		p1.add(tf1);
		gb.setConstraints(tf1, make_constraints(GridBagConstraints.WEST,1,0,
					new Insets(0,0,0,0)));
		Label lab2 = new Label("Slowdown Factor:");
		p1.add(lab2);
		gb.setConstraints(lab2, make_constraints(GridBagConstraints.EAST,0,1,
					new Insets(0,0,0,0)));
		tf2 = new TextField(5);
		p1.add(tf2);
		gb.setConstraints(tf2, make_constraints(GridBagConstraints.WEST,1,1,
					new Insets(0,0,0,0)));
		Label lab3 = new Label("Border (Pixels):");
		p1.add(lab3);
		gb.setConstraints(lab3, make_constraints(GridBagConstraints.EAST,0,2,
					new Insets(0,0,0,0)));
		tf3 = new TextField(5);
		p1.add(tf3);
		gb.setConstraints(tf3, make_constraints(GridBagConstraints.WEST,1,2,
					new Insets(0,0,0,0)));
					
		cb1 = new Checkbox("Double Buffer");
		
		cb2 = new Checkbox("Start Paused");
		
		Panel p2 = new Panel();			// buttons at bottom
		p2.setLayout(gb);
		but1 = new Button("Cancel");
		
		but1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		p2.add(but1);
		gb.setConstraints(but1, make_constraints(GridBagConstraints.EAST,0,0,
					new Insets(0,0,0,0)));
		but2 = new Button("OK");
		
		but2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int tempint;
				double tempdouble;
				
				setVisible(false);
						// read out prefs
				newjc = new JuggleControls(newjc);	// clone old controls
				
				try {
					tempdouble = Double.valueOf(tf1.getText()).doubleValue();
					if (tempdouble > 0.0) newjc.fps = tempdouble;
				} catch (NumberFormatException e) {
				}
				
				try {
					tempdouble = Double.valueOf(tf2.getText()).doubleValue();
					if (tempdouble > 0.0) newjc.slowdown = tempdouble;
				} catch (NumberFormatException e) {
				}
				
				try {
					tempint = Integer.parseInt(tf3.getText());
					if (tempint >= 0) newjc.border = tempint;
				} catch (NumberFormatException e) {
				}
				
				newjc.dbuffer = cb1.getState();
				newjc.startPause = cb2.getState();
			}
		});
		
		p2.add(but2);
		gb.setConstraints(but2, make_constraints(GridBagConstraints.EAST,1,0,
					new Insets(0,10,0,0)));
					
		this.add(p1);		// now make the whole window
		gb.setConstraints(p1, make_constraints(GridBagConstraints.WEST,0,0,
					new Insets(border,border,3,border)));
		this.add(cb1);
		gb.setConstraints(cb1, make_constraints(GridBagConstraints.WEST,0,1,
					new Insets(3,border,0,border)));
		this.add(cb2);
		gb.setConstraints(cb2, make_constraints(GridBagConstraints.WEST,0,2,
					new Insets(0,border,3,border)));
		this.add(p2);
		gb.setConstraints(p2, make_constraints(GridBagConstraints.EAST,0,3,
					new Insets(3,border,border,border)));
		
		this.pack();
	}
	
	protected static GridBagConstraints make_constraints(int location, int gridx, int gridy,
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
	
	public JuggleControls getPrefs(JuggleControls oldjc) {
		newjc = oldjc;
//		finished = false;
		
		tf1.setText(Double.toString(oldjc.fps));
		tf2.setText(Double.toString(oldjc.slowdown));
		tf3.setText(Integer.toString(oldjc.border));
		cb1.setState(oldjc.dbuffer);
		cb2.setState(oldjc.startPause);
		
		this.show();			// supposed to block until one of the buttons is hit
					// some Javas don't -- this is the workaround
/*		while (!finished) {
			synchronized (this) {
				try {
					wait(200);
				} catch (InterruptedException ie) {}
			}
		}*/
		return newjc;
	}
}
