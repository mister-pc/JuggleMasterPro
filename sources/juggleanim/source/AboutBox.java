// AboutBox.java
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


public class AboutBox extends Frame implements ActionListener {
	public AboutBox() {
		super();
		GridBagLayout gb = new GridBagLayout();
		this.setLayout(gb);

		Label abouttext1 = new Label("JuggleAnim");
		abouttext1.setFont(new Font("SansSerif", Font.BOLD, 18));
		this.add(abouttext1);
		gb.setConstraints(abouttext1, make_constraints(GridBagConstraints.WEST,0,0,
					new Insets(15,15,0,15)));
					
		Label abouttext5 = new Label("Version " + JuggleAnim.version);
		abouttext5.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(abouttext5);
		gb.setConstraints(abouttext5, make_constraints(GridBagConstraints.WEST,0,1,
					new Insets(0,15,0,15)));

		Label abouttext2 = new Label("Copyright (C) 2002 by Jack Boyce and others");
		abouttext2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(abouttext2);
		gb.setConstraints(abouttext2, make_constraints(GridBagConstraints.WEST,0,2,
					new Insets(15,15,15,15)));

		Label abouttext3 = new Label("This program is free software; you can redistribute it and/or");
		abouttext3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(abouttext3);
		gb.setConstraints(abouttext3, make_constraints(GridBagConstraints.WEST,0,3,
					new Insets(0,15,0,15)));

		Label abouttext4 = new Label("modify it under the terms of the GNU General Public License");
		abouttext4.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(abouttext4);
		gb.setConstraints(abouttext4, make_constraints(GridBagConstraints.WEST,0,4,
					new Insets(0,15,0,15)));

		Button okbutton = new Button("OK");
		okbutton.addActionListener(this);
		this.add(okbutton);
		gb.setConstraints(okbutton, make_constraints(GridBagConstraints.EAST,0,5,
					new Insets(15,15,15,15)));
					
		this.pack();
	}
	

	public void actionPerformed(ActionEvent newEvent) {
		dispose();
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
}
