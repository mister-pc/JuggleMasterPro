/*
 * @(#)ImportSiteswapsJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.siteswap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ImportSiteswapsJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ImportSiteswapsJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addActionListener(this);
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, true, false));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ImportSiteswapsJButton.actionPerformed()");
		this.objGcontrolJFrame.objGimportSiteswapsJMenuItem.actionPerformed(null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {

		final ImageIcon icoLrollOver =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_IMPORT_SITESWAPS,
																									2,
																									"Import");

		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_IMPORT_SITESWAPS_BW, 1, "Import");
		final ImageIcon icoLdisabled =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_DO_NOT_IMPORT_SITESWAPS,
																									0,
																									"DoNotImport");
		Tools.setIcons(this, icoL, icoLrollOver, icoLdisabled);
	}

	final public void setToolTipText() {
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
							&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																												? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_IMPORT_SITESWAPS)
																												: null);
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ImportSiteswapsJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
