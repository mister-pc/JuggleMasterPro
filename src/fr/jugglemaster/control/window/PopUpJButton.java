/*
 * @(#)PopUpJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PopUpJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpopUpJDialog
	 * @param strPtext
	 * @param bytPpreference
	 */
	public PopUpJButton(ControlJFrame objPcontrolJFrame,
						PopUpJDialog objPpopUpJDialog,
						String strPtext,
						int intPtooltipLanguageIndex,
						byte bytPpreference) {
		super(objPcontrolJFrame, strPtext);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpopUpJDialog = objPpopUpJDialog;
		this.bytGpreference = bytPpreference;

		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_OK_BW, 2);
		final ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_OK, 2);
		Tools.setIcons(this, icoL, icoLrollOver);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																													? this.objGcontrolJFrame.getLanguageString(intPtooltipLanguageIndex)
																													: null);
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("PopUpJButton.actionPerformed()");
		if (this.bytGpreference != Constants.bytS_UNCLASS_NO_VALUE) {
			Preferences.writeGlobalBooleanPreference(this.bytGpreference, !this.objGpopUpJDialog.objGdoNotDisplayPopUpJCheckBox.isSelected());
		}
		this.objGpopUpJDialog.setVisible(false);
		this.objGpopUpJDialog.dispose();
	}

	final private byte			bytGpreference;
	final private ControlJFrame	objGcontrolJFrame;

	final private PopUpJDialog	objGpopUpJDialog;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PopUpJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
