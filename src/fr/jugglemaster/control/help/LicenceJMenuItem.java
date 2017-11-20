/*
 * @(#)LicenceJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.help;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.window.JDialogWindowListener;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

// import static java.lang.Math.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class LicenceJMenuItem extends JMenuItem implements ActionListener {

	final private static long	serialVersionUID		= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean				bolGalreadyDisplayed	= false;

	private final ControlJFrame	objGcontrolJFrame;

	private final JDialog		objGlicenceJDialog;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public LicenceJMenuItem(ControlJFrame objPcontrolJFrame) {

		this.objGcontrolJFrame = objPcontrolJFrame;

		// Licence dialog :
		this.objGlicenceJDialog = new JDialog(this.objGcontrolJFrame, this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_LICENCE), true);
		final JTextArea objLlicenceJTextArea = new JTextArea();
		objLlicenceJTextArea.setFont(new Font("Courier", Font.PLAIN, 11));
		objLlicenceJTextArea.setOpaque(true);
		objLlicenceJTextArea.setEditable(false);
		final JScrollPane objLjScrollPane =
											new JScrollPane(objLlicenceJTextArea,
															ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
															ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		objLjScrollPane.setOpaque(true);
		this.objGlicenceJDialog.add(objLjScrollPane);
		this.objGlicenceJDialog.validate();
		this.objGlicenceJDialog.pack();
		this.objGlicenceJDialog.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this.objGlicenceJDialog, false));
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.addActionListener(this);
		this.setAccelerator(Constants.keyS_LICENCE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("LicenceJMenuItem.actionPerformed()");
		this.objGcontrolJFrame.doHidePopUps();
		if (this.objGcontrolJFrame.doDisplayComingSoonPopUp()) {
			return;
		}
		if (!this.bolGalreadyDisplayed) {
			this.objGcontrolJFrame.setWindowBounds(this.objGlicenceJDialog);
			this.bolGalreadyDisplayed = true;
		}
		this.objGlicenceJDialog.setVisible(true);
	}

	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_LICENCE, 0));
		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_LICENCE, 0);
		this.objGlicenceJDialog.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLabel() {
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_LICENCE);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_LICENCE);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_LICENCE_MENU)
																												: null);
	}
}

/*
 * @(#)LicenceJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
