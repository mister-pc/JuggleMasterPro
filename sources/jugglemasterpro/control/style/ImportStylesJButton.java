/*
 * @(#)ImportStylesJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.style;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.io.ExtendedTransferHandler;
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
final public class ImportStylesJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ImportStylesJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addActionListener(this);
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, false, true));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ImportStylesJButton.actionPerformed()");
		this.objGcontrolJFrame.objGimportStylesJMenuItem.actionPerformed(null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		final ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_IMPORT_STYLES, 2, "Import");

		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_IMPORT_STYLES_BW, 1, "Import");

		final ImageIcon icoLdisabled =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_DO_NOT_IMPORT_STYLES,
																									0,
																									"DoNotImport");

		Tools.setIcons(this, icoL, icoLrollOver, icoLdisabled);
	}

	final public void setToolTipText() {
		final boolean bolLenabled =
									this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
										|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN;
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && bolLenabled
																																	? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_IMPORT_STYLES)
																																	: null);
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ImportStylesJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
