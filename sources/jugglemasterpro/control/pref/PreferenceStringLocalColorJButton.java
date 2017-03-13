/*
 * @(#)PreferenceStringLocalColorJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJButton;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferenceStringLocalColorJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param bytPcolorPreferenceType
	 */
	public PreferenceStringLocalColorJButton(ControlJFrame objPcontrolJFrame, PreferencesJDialog objPpreferencesJDialog, byte bytPcolorPreferenceType) {
		super(objPcontrolJFrame, Strings.strS_EMPTY);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGcolorPreferenceType = bytPcolorPreferenceType;
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("PreferenceStringLocalColorJButton.actionPerformed()");
		int intLtitleStringLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		switch (this.bytGcolorPreferenceType) {
			case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_DAY:
				intLtitleStringLanguageIndex = Language.intS_TITLE_DAY_SITESWAP_COLOR;
				break;
			case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_SITESWAP_NIGHT:
				intLtitleStringLanguageIndex = Language.intS_TITLE_NIGHT_SITESWAP_COLOR;
				break;
			case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_DAY:
				intLtitleStringLanguageIndex = Language.intS_TITLE_DAY_BACKGROUND_COLOR;
				break;
			case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_BACKGROUND_NIGHT:
				intLtitleStringLanguageIndex = Language.intS_TITLE_NIGHT_BACKGROUND_COLOR;
				break;
			case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_DAY:
				intLtitleStringLanguageIndex = Language.intS_TITLE_DAY_JUGGLER_COLOR;
				break;
			case PreferencesJDialog.bytS_STRING_LOCAL_PREFERENCE_JUGGLER_NIGHT:
				intLtitleStringLanguageIndex = Language.intS_TITLE_NIGHT_JUGGLER_COLOR;
				break;
		}

		if (this.objGcolorChooserJDialog == null) {

			// Color chooser dialog title :
			// Color chooser dialog :
			this.objGjColorChooser = new PreferencesJColorChooser(this.objGcontrolJFrame, this.objGpreferencesJDialog, this.bytGcolorPreferenceType);
			this.objGjColorChooser.setOpaque(true);
			this.objGcolorChooserJDialog =
											JColorChooser.createDialog(	this.objGpreferencesJDialog,
																		Strings.strS_EMPTY,
																		true,
																		this.objGjColorChooser,
																		this.objGjColorChooser.getActionListener(true),
																		this.objGjColorChooser.getActionListener(false));
			Tools.doSetFont(this.objGcolorChooserJDialog, this.objGcontrolJFrame.getFont());
			this.objGcolorChooserJDialog.validate();
			this.objGcolorChooserJDialog.pack();
			final Rectangle objLcolorChooserJDialogRectangle = this.objGcolorChooserJDialog.getBounds();
			this.objGcolorChooserJDialog.setBounds(	(int) objLcolorChooserJDialogRectangle.getX() - 10,
													(int) objLcolorChooserJDialogRectangle.getY() - 20,
													(int) objLcolorChooserJDialogRectangle.getWidth() + 20,
													(int) objLcolorChooserJDialogRectangle.getHeight() + 20);
			this.objGcolorChooserJDialog.setResizable(false);
		}

		// Display the color chooser dialog :
		this.objGjColorChooser.stateChanged(null);
		this.objGcolorChooserJDialog.setTitle(this.objGpreferencesJDialog.getLanguageString(intLtitleStringLanguageIndex));
		this.objGcolorChooserJDialog.setVisible(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void disposeJDialog() {
		if (this.objGcolorChooserJDialog != null) {
			this.objGcolorChooserJDialog.setVisible(false);
			this.objGcolorChooserJDialog.dispose();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPgraphics
	 */
	@Override final public void paintComponent(Graphics objPgraphics) {

		// Draw a colored rectangle in place of the button :
		final int intLjButtonWidth = this.getWidth();
		final int intLjButtonHeight = this.getHeight();
		final BufferedImage imgLbuffer =
												Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(intLjButtonWidth,
																											intLjButtonHeight,
																											Transparency.OPAQUE);
		final Graphics2D objLgraphics2D = (Graphics2D) imgLbuffer.getGraphics();
		objLgraphics2D.setColor(Tools.getPenGammaColor(	this.objGpreferencesJDialog.strGstringLocalAA[this.bytGcolorPreferenceType][Constants.bytS_UNCLASS_TEMPORARY_CURRENT],
														Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION)));
		objLgraphics2D.fillRect(1, 1, intLjButtonWidth - 2, intLjButtonHeight - 2);
		objLgraphics2D.setColor(Color.BLACK);
		objLgraphics2D.drawRect(0, 0, intLjButtonWidth, intLjButtonHeight);
		objLgraphics2D.dispose();
		objPgraphics.drawImage(imgLbuffer, 0, 0, null);
	}

	final private static long			serialVersionUID		= Constants.lngS_ENGINE_VERSION_NUMBER;
	final private byte					bytGcolorPreferenceType;

	private JDialog						objGcolorChooserJDialog	= null;

	final private ControlJFrame			objGcontrolJFrame;

	private PreferencesJColorChooser	objGjColorChooser		= null;

	final private PreferencesJDialog	objGpreferencesJDialog;
}

/*
 * @(#)PreferenceStringLocalColorJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
