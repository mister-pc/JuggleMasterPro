/*
 * @(#)DevelopmentJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.help;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.control.window.JDialogWindowListener;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class DevelopmentJMenuItem extends JMenuItem implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public DevelopmentJMenuItem(ControlJFrame objPcontrolJFrame) {

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGdevelopmentJTextArea = new JTextArea(25, 80);
		this.objGdevelopmentJTextArea.setFont(new Font("Courier", Font.PLAIN, 11));
		this.objGdevelopmentJTextArea.setOpaque(true);
		this.objGdevelopmentJTextArea.setEditable(false);
		this.objGcloseExtendedJButton = new ExtendedJButton(objPcontrolJFrame, this);

		// Build dialog :
		this.objGdevelopmentJDialog = this.getDevelopmentDialog(objPcontrolJFrame, this.objGdevelopmentJTextArea, this.objGcloseExtendedJButton);
		this.setOpaque(true);
		this.addActionListener(this);
		this.setAccelerator(Constants.keyS_DEVELOPMENT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("DevelopmentJMenuItem.actionPerformed()");
		if (objPactionEvent.getSource() == this.objGcloseExtendedJButton) {
			this.objGdevelopmentJDialog.setVisible(false);
		} else {
			this.objGcontrolJFrame.doHidePopUps();
			if (!this.bolGalreadyDisplayed) {
				this.objGcontrolJFrame.setWindowBounds(this.objGdevelopmentJDialog);
				this.bolGalreadyDisplayed = true;
			}
			this.objGdevelopmentJDialog.setVisible(true);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_DEVELOPMENT, 0);
		this.objGdevelopmentJDialog.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DEVELOPMENT, 0));

		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLOSE_DEVELOPMENT_BW, 2);
		final ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CLOSE_DEVELOPMENT, 2);
		Tools.setIcons(this.objGcloseExtendedJButton, icoL, icoLrollOver);
	}

	final private JDialog getDevelopmentDialog(	ControlJFrame objPcontrolJFrame,
												JTextArea objPdevelopmentJTextArea,
												ExtendedJButton objPdevelopmentCloseExtendedJButton) {

		// Development scrollpane :
		final JScrollPane objLdevelopmentJScrollPane =
														new JScrollPane(objPdevelopmentJTextArea,
																		ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
																		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		objLdevelopmentJScrollPane.setOpaque(true);

		// Development dialog :
		final JDialog objLdevelopmentJDialog =
												new JDialog(objPcontrolJFrame,
															objPcontrolJFrame.getLanguageString(Language.intS_TITLE_DEVELOPMENT),
															true);
		objLdevelopmentJDialog.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											0,
																											0,
																											0,
																											10,
																											0,
																											0,
																											GridBagConstraints.BOTH,
																											1.0F,
																											1.0F);

		// Add content :
		objLdevelopmentJDialog.add(objLdevelopmentJScrollPane, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		objLdevelopmentJDialog.add(objPdevelopmentCloseExtendedJButton, objLextendedGridBagConstraints);
		objLdevelopmentJDialog.addWindowListener(new JDialogWindowListener(objPcontrolJFrame, objLdevelopmentJDialog, false));
		return objLdevelopmentJDialog;
	}

	final public void setLabel() {

		final BufferedReader objLdevelopmentBufferedReader =
																Tools.getBufferedReader(Strings.doConcat(	this.objGcontrolJFrame.getJuggleMasterPro().strS_CODE_BASE,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA],
																											File.separatorChar,
																											this.objGcontrolJFrame.getLanguageString(Language.intS_LANGUAGE_ISO_639_1_CODE),
																											File.separatorChar,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_DEVELOPMENT]));
		final StringBuilder objLdevelopmentStringBuilder = new StringBuilder(2048);
		while (true) {
			String strLreadLine = null;
			try {
				strLreadLine = objLdevelopmentBufferedReader.readLine();
			} catch (final Throwable objPthrowable) {
				break;
			}
			if (strLreadLine != null) {
				objLdevelopmentStringBuilder.append(Strings.doConcat(strLreadLine, Strings.strS_LINE_SEPARATOR));
			} else {
				break;
			}
		}

		this.objGcloseExtendedJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_CLOSE));
		this.objGcloseExtendedJButton.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																			? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_CLOSE_DEVELOPMENT_DIALOG)
																																			: null);
		this.objGdevelopmentJTextArea.setText(objLdevelopmentStringBuilder.toString());
		this.objGdevelopmentJDialog.validate();
		this.objGdevelopmentJDialog.pack();
		this.objGdevelopmentJDialog.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_DEVELOPMENT);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_DEVELOPMENT);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_DEVELOPMENT_MENU)
																												: null);
	}

	private boolean					bolGalreadyDisplayed	= false;

	final private ExtendedJButton	objGcloseExtendedJButton;

	final private ControlJFrame		objGcontrolJFrame;

	private final JDialog			objGdevelopmentJDialog;

	final private JTextArea			objGdevelopmentJTextArea;

	final private static long		serialVersionUID		= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)DevelopmentJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
