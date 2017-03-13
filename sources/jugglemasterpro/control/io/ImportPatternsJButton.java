/*
 * @(#)ImportPatternsJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJButton;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ImportPatternsJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ImportPatternsJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ImportPatternsJButton.actionPerformed()");
		this.objGcontrolJFrame.objGimportPatternsJMenuItem.actionPerformed(null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {

		final ImageIcon icoLrollOver =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_IMPORT_PATTERNS,
																									2,
																									"Import");

		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_IMPORT_PATTERNS_BW, 1, "Import");

		final ImageIcon icoLdisabled =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_DO_NOT_IMPORT_PATTERNS,
																									0,
																									"DoNotImport");
		Tools.setIcons(this, icoL, icoLrollOver, icoLdisabled);
	}

	final public void setToolTipText() {
		if (this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
			this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
								&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																													? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_IMPORT_PATTERNS_FILES_MENU)
																													: null);
		}
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ImportPatternsJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
