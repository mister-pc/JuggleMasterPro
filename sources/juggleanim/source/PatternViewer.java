// PatternViewer.java
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


class PatternViewerPrefs {
	public final static int ANIMATOR_NONE = 0;
	public final static int ANIMATOR_PANEL = 1;
	public final static int ANIMATOR_WINDOW = 2;
	public final static int ANIMATOR_MULTIPLEWINDOWS = 3;
	
	public int animator;
	public boolean generator, lister, editor;
	public String generatorname;
	
	public PatternViewerPrefs() {
		generator = false;			// defaults
		generatorname = null;
		editor = false;
		lister = false;
		animator = ANIMATOR_PANEL;
	}
}


public class PatternViewer extends Panel {
	protected PatternViewerPrefs pvp;
	protected PatternViewerWindow pvw;
	
	protected boolean patternfilemode;
	protected Font editorfont, patternfilefont, generatorfont;
	
	protected Panel generatorpanel = null, editorpanel = null;
	protected String[] jugglercommands;	// for patternfileview mode
	protected Button control_button, go_button;
	protected Label result_label;
	protected java.awt.List pattern_list;
	protected TextField editor_text;
	protected Generator gen;	
	protected JuggleAnimWindow jaw;		// pop up window
	protected JuggleAnimAnimator ja;	// animate in a panel
	
				
	public PatternViewer(Reader patternfile, String config, PatternViewerWindow pvw)
					throws JuggleException {
		editorfont = patternfilefont = generatorfont = new Font("Monospaced", Font.PLAIN, 10);
		this.pvw = pvw;
		this.pvp = new PatternViewerPrefs();	// start with default preferences
		this.configure(parseInput(config));
//		this.doLayout();
		if (patternfile != null)
			this.readfile(patternfile);
	}
	
	
	protected static PatternViewerPrefs parseInput(String config) throws JuggleException {
		PatternViewerPrefs newpvp = new PatternViewerPrefs();
		
			// parse the config string
		if (config != null) {
			StringTokenizer st3 = new StringTokenizer(config, ";");
			int numparams = st3.countTokens();
			String param, value;
			
			for (int index = 0; index < numparams; index++) {
				StringTokenizer st4 = new StringTokenizer(st3.nextToken(), "=");
				if (st4.countTokens() != 2)
					throw new JuggleException("Syntax error in config");
					
				param = st4.nextToken().trim();
				value = st4.nextToken().trim();
				
				if (param.equalsIgnoreCase("generator")) {
					newpvp.generatorname = value;
					newpvp.generator = true;
				} else if (param.equalsIgnoreCase("editor")) {
					if (value.equalsIgnoreCase("true"))
						newpvp.editor = true;
					else if (value.equalsIgnoreCase("false"))
						newpvp.editor = false;
					else
						throw new JuggleException("Unrecognized editor setting");
				} else if (param.equalsIgnoreCase("lister")) {
					if (value.equalsIgnoreCase("true"))
						newpvp.lister = true;
					else if (value.equalsIgnoreCase("false"))
						newpvp.lister = false;
					else
						throw new JuggleException("Unrecognized lister setting");
				} else if (param.equalsIgnoreCase("animator")) {
					if (value.equalsIgnoreCase("none"))
						newpvp.animator = PatternViewerPrefs.ANIMATOR_NONE;
					else if (value.equalsIgnoreCase("panel"))
						newpvp.animator = PatternViewerPrefs.ANIMATOR_PANEL;
					else if (value.equalsIgnoreCase("window"))
						newpvp.animator = PatternViewerPrefs.ANIMATOR_WINDOW;
					else if (value.equalsIgnoreCase("multiplewindows"))
						newpvp.animator = PatternViewerPrefs.ANIMATOR_MULTIPLEWINDOWS;
					else
						throw new JuggleException("Unrecognized animator type");
				} else {
					throw new JuggleException("Unrecognized option in config");
				}
			}			
		}
	
		if (newpvp.generator == true)
			newpvp.lister = true;			// generator needs place to list patterns
		
		if (newpvp.animator == PatternViewerPrefs.ANIMATOR_NONE)
			newpvp.editor = false;
			
		return newpvp;
	}
	
	
	public void configure(PatternViewerPrefs newpvp) throws JuggleException {
			// Get the generator object, if necessary
		if (newpvp.generator) {
			if (gen == null) {	// turning on -> keep list
				gen = Generator.getGenerator(newpvp.generatorname);
			} else if (!newpvp.generatorname.equalsIgnoreCase(pvp.generatorname)) {
				gen.killControls();		// clean up after last one
				gen = Generator.getGenerator(newpvp.generatorname);
				if (!patternfilemode && (pattern_list != null))
					pattern_list.removeAll();	// changing gens -> clear list
			}
		} else if (gen != null) {
			gen.killControls();
			if (!patternfilemode && (pattern_list != null))
				pattern_list.removeAll();	// turning off generator -> clear list
			gen = null;
		}
				
			// Now that we know what GUI elements to make, go about making them
			
		GridBagLayout gb = new GridBagLayout();
		
		if (newpvp.generator) {
			if (generatorpanel == null) {
				generatorpanel = new Panel();
				generatorpanel.setLayout(gb);
				
				Panel temppanel = new Panel();
				temppanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
				control_button = new Button("Show Controls");
				
				control_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						gen.showControls();
					}
				});
				
				temppanel.add(control_button);
				go_button = new Button("Generate!");
				
				go_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						try {
							gen.initGenerate();
							result_label.setText("Working...");
							pattern_list.setVisible(false);
							pattern_list.removeAll();
							if (patternfilemode) {
								patternfilemode = false;
								pattern_list.setFont(generatorfont);
							}
							int numpatterns = gen.runGenerate(pattern_list, 250);// limit of 250 patterns
							if (numpatterns == 0)
								result_label.setText("No patterns found");
							else if (numpatterns == 1)
								result_label.setText("1 pattern found");
							else
								result_label.setText(numpatterns + " patterns found");
							if (pvw != null)
								pvw.updateMenu();
						} catch (JuggleException je) {
							result_label.setText(je.getMessage());
						} finally {
							pattern_list.setVisible(true);
						}
					}
				});
				
				temppanel.add(go_button);
				
				result_label = new Label(gen.getStartupMessage(), Label.LEFT);
				
				generatorpanel.add(temppanel);
				gb.setConstraints(temppanel, make_constraints(GridBagConstraints.CENTER,
							GridBagConstraints.NONE,0,0, new Insets(0,0,0,0)));
				generatorpanel.add(result_label);
				GridBagConstraints gbc4 = make_constraints(GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL,0,1, new Insets(0,10,10,10));
				gbc4.weightx = 1.0;
				gb.setConstraints(result_label, gbc4);
			} else
				result_label.setText(gen.getStartupMessage());
		} else {
			generatorpanel = null;
			control_button = null;
			go_button = null;
			result_label = null;
		}
		
		if (newpvp.lister) {
			if (pattern_list == null) {
				pattern_list = new java.awt.List();
				pattern_list.setMultipleMode(false);
				
					// double click on List
				pattern_list.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (pvp.editor) {
							int index = pattern_list.getSelectedIndex();
							String temp;
							if (patternfilemode) {
								if ((temp = jugglercommands[index]) != null)
									animatePattern(pattern_list.getItem(index), temp);
							} else if (gen != null) {
								temp = pattern_list.getItem(index).trim();
								animatePattern(temp, gen.virtual_makeAnimatorInput(temp));
							}
						}
					}
				});

					// single click on List
				pattern_list.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent ie) {
						if (ie.getStateChange() != ItemEvent.SELECTED)
							return;
							
						int index = pattern_list.getSelectedIndex();

						if (pvp.editor) {
							if (patternfilemode)
								editor_text.setText(jugglercommands[index]);
							else
								editor_text.setText(pattern_list.getItem(index).trim());
						} else {
							String temp;
							if (patternfilemode) {
								if ((temp = jugglercommands[index]) != null)
									animatePattern(pattern_list.getItem(index), temp);
							} else if (gen != null) {
								temp = pattern_list.getItem(index).trim();
								animatePattern(temp, gen.virtual_makeAnimatorInput(temp));
							}
						}
					}
				});
			}
		} else
			pattern_list = null;
		
		if (newpvp.editor) {
			if (editorpanel == null) {
				editorpanel = new Panel();
				editorpanel.setLayout(gb);
				
	//			Label editor_label = new Label("Pattern:", Label.LEFT);
	//			editorpanel.add(editor_label);
	//			gb.setConstraints(editor_label, make_constraints(GridBagConstraints.WEST,
	//						GridBagConstraints.NONE,0,0, new Insets(0,0,0,0)));
				
				editor_text = new TextField(20);
				editor_text.setFont(editorfont);
				
				editor_text.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						animatePattern(null, editor_text.getText());
					}
				});

				editorpanel.add(editor_text);
				GridBagConstraints gbc3 = make_constraints(GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL,1,0, new Insets(0,0,0,0));
				gbc3.weightx = 1.0;
				gb.setConstraints(editor_text, gbc3);
	//			animate_button = new Button("Animate!");
	//			editorpanel.add(animate_button);
	//			gb.setConstraints(animate_button, make_constraints(GridBagConstraints.EAST,
	//						GridBagConstraints.NONE,2,0, new Insets(0,0,0,0)));
			}
		} else {
			editorpanel = null;
			editor_text = null;
		}
		
			// Now from these pieces construct the whole panel.  There are two
			// basic styles, depending on whether there's an animation panel or not.
		
		this.removeAll();
		
		if (newpvp.animator == PatternViewerPrefs.ANIMATOR_PANEL) {
			if (ja == null)
				ja = new JuggleAnimAnimator(this.pvw);		// JuggleAnimAnimator is a type of panel
			
			int gridwidth = (newpvp.lister || newpvp.generator) ? 2 : 1;
			this.setLayout(new GridLayout(1, gridwidth));
			
			Panel leftpanel;
			
			if (newpvp.editor) {
				leftpanel = new Panel();
				leftpanel.setLayout(gb);
				leftpanel.add(ja);
				GridBagConstraints gbc1 = make_constraints(GridBagConstraints.CENTER,
							GridBagConstraints.BOTH,0,0, new Insets(0,0,0,10));
				gbc1.weightx = gbc1.weighty = 1.0;
				gb.setConstraints(ja, gbc1);
				leftpanel.add(editorpanel);
				GridBagConstraints gbc2 = make_constraints(GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL,0,1, new Insets(10,0,0,10));
				gbc2.weightx = 1.0;
				gb.setConstraints(editorpanel, gbc2);
			} else
				leftpanel = ja;
			
			this.add(leftpanel);
						
			if (gridwidth == 2) {
				Component rightcomp;
				
				if (newpvp.generator) {
					Panel rightpanel = new Panel();
					rightpanel.setLayout(gb);
					rightpanel.add(generatorpanel);
					GridBagConstraints gbc1 = make_constraints(GridBagConstraints.WEST,
								GridBagConstraints.HORIZONTAL,0,0, new Insets(0,0,0,0));
					gbc1.weightx = 1.0;
					gb.setConstraints(generatorpanel, gbc1);
					
					rightpanel.add(pattern_list);
					GridBagConstraints gbc2 = make_constraints(GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,0,1, new Insets(0,0,0,0));
					gbc2.weightx = gbc2.weighty = 1.0;
					gb.setConstraints(pattern_list, gbc2);
					rightcomp = (Component)rightpanel;
				} else
					rightcomp = pattern_list;
					
				this.add(rightcomp);
			}
		} else {		// otherwise, animations pop up in a window(s)
			if (ja != null) {
				ja.dispose();
				ja = null;
			}
			this.setLayout(gb);
			if (newpvp.generator) {
				this.add(generatorpanel);
				GridBagConstraints gbc1 = make_constraints(GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL,0,0, new Insets(0,0,0,0));
				gbc1.weightx = 1.0;
				gb.setConstraints(generatorpanel, gbc1);
			}
			
			if (newpvp.lister) {
				this.add(pattern_list);
				GridBagConstraints gbc2 = make_constraints(GridBagConstraints.CENTER,
							GridBagConstraints.BOTH,0,1, new Insets(0,0,0,0));
				gbc2.weightx = gbc2.weighty = 1.0;
				gb.setConstraints(pattern_list, gbc2);
			}
			
			if (newpvp.editor) {
				this.add(editorpanel);
				GridBagConstraints gbc2 = make_constraints(GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL,0,2, new Insets(10,0,0,0));
				gbc2.weightx = 1.0;
				gb.setConstraints(editorpanel, gbc2);
			}
		}
		
		this.pvp = newpvp;
	}
		
	
	protected static GridBagConstraints make_constraints(int location, int fill,
					int gridx, int gridy, Insets ins) {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = location;
		gbc.fill = fill;
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = ins;
		gbc.weightx = gbc.weighty = 0.0;
		return gbc;
	}

	
	public void readfile(Reader instream) {
		if ((pvp.lister == false) || (pattern_list == null))
			return;
			
		BufferedReader dis = new BufferedReader(instream);
			// erase the list and start reading file
		pattern_list.setVisible(false);
		pattern_list.removeAll();
		pattern_list.setFont(patternfilefont);
		jugglercommands = null;
		Vector v1 = new Vector(50,10);
		try {
			String currentline;
			StringTokenizer st;
			int numtokens;
			
			for (;;) {
				currentline = dis.readLine();
				if (currentline == null)
					break;
				st = new StringTokenizer(currentline, "\t");
				numtokens = st.countTokens();
				
				if (numtokens > 0)
					pattern_list.add(st.nextToken());
				else
					pattern_list.add(" ");
				
				if (numtokens > 1)
					v1.addElement(st.nextToken());
				else
					v1.addElement(null);
			}
		}
		catch (IOException ioe) {
		}
		try {
			dis.close();
		}
		catch (IOException ioe) {
		}
		pattern_list.setVisible(true);
//		pattern_list.repaint();
		int numlines = v1.size();
		jugglercommands = new String[numlines];
		Enumeration enum = v1.elements();
		for (int index = 0; index < numlines; index++)
			jugglercommands[index] = (String)enum.nextElement();
		patternfilemode = true;
	}
	
	
	public void printpatterns(PrintWriter stream, boolean animcommands) {
		int num = pattern_list.getItemCount();
		String currentitem, animcommand;
		
		for (int i = 0; i < num; i++) {
			currentitem = pattern_list.getItem(i);
			if (animcommands) {
				stream.print(currentitem);
				stream.print('\t');
				if (patternfilemode)
					animcommand = jugglercommands[i];
				else
					animcommand = gen.virtual_makeAnimatorInput(currentitem);
				stream.println(animcommand);
			} else
				stream.println(currentitem);
		}
	}
		
	public void animatePattern(String label, String pattern) {
		if (pvp.animator == PatternViewerPrefs.ANIMATOR_NONE)
			return;
		if (pattern == null)
			return;
			
		pattern = pattern.trim();
		
		if (pattern.indexOf((int)'=') == -1) {	// not raw, assume siteswap notation
			label = pattern;
			char firstchar = pattern.charAt(0);
			
			if (firstchar == '(')
				pattern = "mode=syncsiteswap;pattern="+pattern+";slowdown=2.0";
			else if (firstchar == '<')
				return;		// can't do passing yet
			else
				pattern = "mode=siteswap;pattern="+pattern+";slowdown=2.0";
		} else if (label == null)	// otherwise user typing directly to animator
			label = "Pattern";
			
		switch (pvp.animator) {
			case PatternViewerPrefs.ANIMATOR_PANEL:
				ja.restartJuggle(pattern);		// output to panel
				break;
			case PatternViewerPrefs.ANIMATOR_WINDOW:
				if (jaw == null)
					jaw = new JuggleAnimWindow(label.trim(), pattern);
				else if (jaw.isAlive())
					jaw.restartJuggle(label.trim(), pattern);
				else
					jaw = new JuggleAnimWindow(label.trim(), pattern);
				break;
			case PatternViewerPrefs.ANIMATOR_MULTIPLEWINDOWS:
				new JuggleAnimWindow(label.trim(), pattern);
				break;
		}
	}
	
	public JuggleAnimAnimator getJuggleAnim() {
		return ja;
	}
	
	public int getAnimatorType() {
		return pvp.animator;
	}
	
	public int getPatternCount() {
		if (pattern_list == null)
			return 0;
		return pattern_list.getItemCount();
	}
	
	public void setPatternViewerWindow(PatternViewerWindow newpvw) {
		this.pvw = newpvw;
	}
	
	public PatternViewerPrefs getPrefs() {
		return pvp;
	}
	
	public void dispose() {
		if (ja != null) {
			ja.dispose();
			ja = null;
		}
		if (jaw != null) {
			jaw.dispose();
			jaw = null;
		}
		if (gen != null) {
			gen.killControls();
			gen = null;
		}
	}
	
	protected void finalize() {
		this.dispose();
	}
}

