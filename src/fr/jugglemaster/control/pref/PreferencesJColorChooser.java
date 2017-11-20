/*
 * @(#)PreferencesJColorChooser.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.pref;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferencesJColorChooser extends JColorChooser implements ChangeListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param bytPcolorPreferenceType
	 */
	public PreferencesJColorChooser(ControlJFrame objPcontrolJFrame, PreferencesJDialog objPpreferencesJDialog, byte bytPcolorPreferenceType) {

		// Init panels :
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;

		this.bytGcolorPreferenceType = bytPcolorPreferenceType;
		this.setOpaque(true);
		this.setColor(Tools.getPenColor(this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_TEMPORARY_CURRENT]));
		this.setPreviewPanel(new JPanel());
		final AbstractColorChooserPanel[] objLabstractColorChooserPanelA = this.getChooserPanels();
		for (final AbstractColorChooserPanel objLabstractColorChooserPanel : objLabstractColorChooserPanelA) {
			if (!objLabstractColorChooserPanel.getClass().getName().equals("javax.swing.colorchooser.DefaultHSBChooserPanel")) {
				this.removeChooserPanel(objLabstractColorChooserPanel);
			}
		}

		// Actions.doSetFont(this, this.objGcontrolJFrame.getFont());
		// Init listeners :
		this.getSelectionModel().addChangeListener(this);
		this.objGoKActionListener =
									new PreferencesJColorChooserJDialogListener(this.objGpreferencesJDialog, this, this.bytGcolorPreferenceType, true);
		this.objGcancelActionListener =
										new PreferencesJColorChooserJDialogListener(this.objGpreferencesJDialog,
																					this,
																					this.bytGcolorPreferenceType,
																					false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPaccept
	 * @return
	 */
	final public ActionListener getActionListener(boolean bolPaccept) {
		return bolPaccept ? this.objGoKActionListener : this.objGcancelActionListener;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPchangeEvent
	 */
	final public void stateChanged(ChangeEvent objPchangeEvent) {

		Tools.traces();
		// Modify color in the color chooser dialog :
		final byte bytLgammaCorrection = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION);
		Tools.out("Couleur cliquée   : ", this.getColor());
		final Color objLreverseGammaColor = Tools.getNoGammaColor(this.getColor(), bytLgammaCorrection);
		Tools.out("Couleur renversée : ", objLreverseGammaColor);
		this.setColor(Tools.getGammaColor(objLreverseGammaColor, bytLgammaCorrection));
		Tools.out("Couleur retenue   : ", Tools.getGammaColor(objLreverseGammaColor, bytLgammaCorrection));
		this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_TEMPORARY_CURRENT] =
																																Tools.getPenColorString(objLreverseGammaColor,
																																						true);
		this.objGpreferencesJDialog.setPreferenceControlLocalString(this.bytGcolorPreferenceType,
																	this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_TEMPORARY_CURRENT],
																	false);

		if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
			final boolean bolLrefresh =
										((this.bytGcolorPreferenceType == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY
											|| this.bytGcolorPreferenceType == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY || this.bytGcolorPreferenceType == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY) && this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT))
											|| ((this.bytGcolorPreferenceType == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT
													|| this.bytGcolorPreferenceType == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT || this.bytGcolorPreferenceType == PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT) && !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT));
			if (bolLrefresh) {
				switch (this.bytGcolorPreferenceType) {
					case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY:
					case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT:
						this.objGcontrolJFrame.getJuggleMasterPro().initSiteswapColors();
						this.objGcontrolJFrame.objGsiteswapColorChooserDropDownJButton.repaint();
						this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_DRAW_SITESWAP);
						break;
					case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY:
					case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT:
						this.objGcontrolJFrame.getJuggleMasterPro().setBackgroundColors();
						this.objGcontrolJFrame.objGbackgroundColorChooserDropDownJButton.repaint();
						// this.objGcontrolJFrame.getJuggleMasterPro().getFrame().doCreateBallsErasingImages();
						// this.objGcontrolJFrame.getJuggleMasterPro().getFrame().doCreateBackgroundImages();
						this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_HANDS_IMAGES | Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
															| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES
															| Constants.intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES
															| Constants.intS_ACTION_DRAW_SITESWAP | Constants.intS_ACTION_REFRESH_DRAWING
															| Constants.intS_ACTION_RECREATE_BACKGROUND_IMAGES
															| Constants.intS_ACTION_RECREATE_BALLS_ERASING_IMAGES);
						break;
					case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY:
					case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT:
						this.objGcontrolJFrame.objGjugglerColorChooserDropDownJButton.repaint();
						if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER)
							&& !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)) {
							if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)) {
								this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE
																	| Constants.intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES);
							}
							this.objGcontrolJFrame.getJuggleMasterPro().initJugglerColors();
							this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_RECREATE_HANDS_IMAGES | Constants.intS_ACTION_REFRESH_DRAWING);
						}
						break;
				}
			}
		}

		this.objGpreferencesJDialog.setJButtonsColors();
		this.objGpreferencesJDialog.setDialogJButtonsEnabled();
		Tools.out(this.objGpreferencesJDialog.getInfo());
	}

	private final byte					bytGcolorPreferenceType;
	private final ActionListener		objGcancelActionListener;
	private final ControlJFrame			objGcontrolJFrame;

	private final ActionListener		objGoKActionListener;

	private final PreferencesJDialog	objGpreferencesJDialog;

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PreferencesJColorChooser.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
