// PatternViewerDialog.java
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

                         
class PatternViewerDialog extends Dialog {
				// text fields in preferences panel
	protected Choice choice1, choice2;
	protected Checkbox cb1, cb2, cb3, cb4, cb5;
	protected CheckboxGroup cbg1;
	protected TextField tf1;
	protected Button but1, but2;
	
	protected PatternViewerPrefs newpvp;
	
	protected final static int border = 10;
	
	
	public PatternViewerDialog(Frame parent) {
			// set up dialog
		super(parent, "Layout Preferences", true);
		this.setResizable(false);

		GridBagLayout gb = new GridBagLayout();
		
		this.setLayout(gb);
		
		Panel p1 = new Panel();			// animator type
		p1.setLayout(gb);
		Label lab1 = new Label("Animator Type:");
		p1.add(lab1);
		gb.setConstraints(lab1, make_constraints(GridBagConstraints.WEST,0,0,
					new Insets(0,0,0,0)));
		choice1 = new Choice();
		choice1.addItem("None");
		choice1.addItem("Attached Panel");
		choice1.addItem("Separate Window");
		choice1.addItem("Multiple Windows");
		
		choice1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if ((ie.getStateChange() == ItemEvent.SELECTED) &&
								(choice1.getSelectedIndex() == 0))
					cb5.setState(false);	// no animator -> no editor allowed
			
			}
		});

		p1.add(choice1);
		gb.setConstraints(choice1, make_constraints(GridBagConstraints.WEST,1,0,
					new Insets(0,0,0,0)));
		
		cb1 = new Checkbox("Pattern Listing");
		cb1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.DESELECTED)
					cb2.setState(true);		// no listing -> no generator allowed
			}
		});
		Panel p2 = new Panel();			// Generator type
		p2.setLayout(gb);
		Label lab2 = new Label("Generator:");
		p2.add(lab2);
		gb.setConstraints(lab2, make_constraints(GridBagConstraints.WEST,0,0,
					new Insets(0,20,0,0)));
		cbg1 = new CheckboxGroup();
		cb2 = new Checkbox("None", cbg1, true);
		p2.add(cb2);
		gb.setConstraints(cb2, make_constraints(GridBagConstraints.WEST,1,0,
					new Insets(0,0,0,0)));
		cb3 = new Checkbox("Standard:", cbg1, false);
		p2.add(cb3);
		gb.setConstraints(cb3, make_constraints(GridBagConstraints.WEST,1,1,
					new Insets(0,0,0,0)));
		choice2 = new Choice();
		String[] builtins = Generator.builtinGenerators;
		for (int i = 0; i < builtins.length; i++)
			choice2.addItem(builtins[i]);
		p2.add(choice2);
		gb.setConstraints(choice2, make_constraints(GridBagConstraints.WEST,2,1,
					new Insets(0,0,0,0)));
		cb4 = new Checkbox("Other:", cbg1, false);
		p2.add(cb4);
		gb.setConstraints(cb4, make_constraints(GridBagConstraints.WEST,1,2,
					new Insets(0,0,0,0)));
		tf1 = new TextField(15);
		p2.add(tf1);
		gb.setConstraints(tf1, make_constraints(GridBagConstraints.WEST,2,2,
					new Insets(0,0,0,0)));
		
		cb5 = new Checkbox("Pattern input box");
		
		Panel p3 = new Panel();			// buttons at bottom
		p3.setLayout(gb);
		but1 = new Button("Cancel");
		
		but1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				setVisible(false);
			}
		});
		
		p3.add(but1);
		gb.setConstraints(but1, make_constraints(GridBagConstraints.EAST,0,0,
					new Insets(0,0,0,0)));
		but2 = new Button("OK");
		
		but2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				setVisible(false);
						// read out prefs
				newpvp = new PatternViewerPrefs();
				
				switch (choice1.getSelectedIndex()) {
					case 0:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_NONE;
						break;
					case 1:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_PANEL;
						break;
					case 2:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_WINDOW;
						break;
					case 3:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_MULTIPLEWINDOWS;
						break;
				}
				
				newpvp.lister = cb1.getState();
				
				if (cb2.getState()) {
					newpvp.generator = false;
					newpvp.generatorname = null;
				} else if (cb3.getState()) {
					newpvp.generator = true;
					newpvp.generatorname =
							Generator.builtinGenerators[choice2.getSelectedIndex()];
				} else {
					newpvp.generator = true;
					newpvp.generatorname = tf1.getText();
				}
				
				newpvp.editor = cb5.getState();
			}
		});

		p3.add(but2);
		gb.setConstraints(but2, make_constraints(GridBagConstraints.EAST,1,0,
					new Insets(0,10,0,0)));
					
		this.add(p1);		// now make the whole window
		gb.setConstraints(p1, make_constraints(GridBagConstraints.WEST,0,0,
					new Insets(border,border,3,border)));
		this.add(cb1);
		gb.setConstraints(cb1, make_constraints(GridBagConstraints.WEST,0,1,
					new Insets(3,border,2,border)));
		this.add(p2);
		gb.setConstraints(p2, make_constraints(GridBagConstraints.WEST,0,2,
					new Insets(0,border,3,border)));
		this.add(cb5);
		gb.setConstraints(cb5, make_constraints(GridBagConstraints.WEST,0,3,
					new Insets(3,border,3,border)));
		this.add(p3);
		gb.setConstraints(p3, make_constraints(GridBagConstraints.EAST,0,4,
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
	
	
/*	public boolean handleEvent(Event event) {
		if (event.id == Event.ACTION_EVENT) {
			if (event.target == cb1) {				// "Pattern listing" checkbox
				if (!cb1.getState())
					cb2.setState(true);				// no generator allowed
				return true;
			} else if (event.target == but1) {		// "Cancel" button
				this.hide();
				return true;
			} else if (event.target == but2) {		// "OK" button
				this.hide();
						// read out prefs
				newpvp = new PatternViewerPrefs();
				
				switch (choice1.getSelectedIndex()) {
					case 0:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_NONE;
						break;
					case 1:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_PANEL;
						break;
					case 2:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_WINDOW;
						break;
					case 3:
						newpvp.animator = PatternViewerPrefs.ANIMATOR_MULTIPLEWINDOWS;
						break;
				}
				
				newpvp.lister = cb1.getState();
				
				if (cb2.getState()) {
					newpvp.generator = false;
					newpvp.generatorname = null;
				} else if (cb3.getState()) {
					newpvp.generator = true;
					newpvp.generatorname =
							Generator.builtinGenerators[choice2.getSelectedIndex()];
				} else {
					newpvp.generator = true;
					newpvp.generatorname = tf1.getText();
				}
				
				newpvp.editor = cb5.getState();
				return true;
			} else if (event.target == choice1) {
				if (choice1.getSelectedIndex() == 0)
					cb5.setState(false);
				return true;
			}
		}
		
		return super.handleEvent(event);
	}*/
	
	
	public PatternViewerPrefs getPrefs(PatternViewerPrefs oldpvp) {
		newpvp = oldpvp;
		
		cb5.setState(oldpvp.editor);
		
		if (oldpvp.generator) {
			String[] builtins = Generator.builtinGenerators;
			boolean done = false;
			for (int i = 0; i < builtins.length; i++) {
				if (oldpvp.generatorname.equalsIgnoreCase(builtins[i])) {
					cb3.setState(true);
					choice2.select(i);
					done = true;
					break;
				}
			}
			if (!done) {
				cb4.setState(true);
				tf1.setText(oldpvp.generatorname);
			}
		} else
			cb2.setState(true);
		
		if (oldpvp.lister)
			cb1.setState(true);
		else {
			cb1.setState(false);
			cb2.setState(true);
		}
			
		switch (oldpvp.animator) {
			case PatternViewerPrefs.ANIMATOR_NONE:
				choice1.select(0);
				cb5.setState(false);
				break;
			case PatternViewerPrefs.ANIMATOR_PANEL:
				choice1.select(1);
				break;
			case PatternViewerPrefs.ANIMATOR_WINDOW:
				choice1.select(2);
				break;
			case PatternViewerPrefs.ANIMATOR_MULTIPLEWINDOWS:
				choice1.select(3);
				break;
		}
		
		this.show();		// blocks until one of the buttons is hit
				
		return newpvp;
	}
}
