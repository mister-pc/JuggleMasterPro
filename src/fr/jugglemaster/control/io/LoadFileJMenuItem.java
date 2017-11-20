/*
 * @(#)LoadFileJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import fr.jugglemaster.control.ControlJFrame;
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
final public class LoadFileJMenuItem extends JMenuItem implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bolPnewLists
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 */
	public LoadFileJMenuItem(ControlJFrame objPcontrolJFrame, boolean bolPnewLists, boolean bolPsiteswaps, boolean bolPstyles) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGnewLists = bolPnewLists;
		this.bolGsiteswaps = bolPsiteswaps;
		this.bolGstyles = bolPstyles;
		KeyStroke objLshortcut = null;
		if (this.bolGnewLists) {
			objLshortcut = Constants.keyS_OPEN;
		} else {
			if (bolPsiteswaps) {
				if (bolPstyles) {
					objLshortcut = Constants.keyS_IMPORT_PATTERNS;
				} else {
					objLshortcut = Constants.keyS_IMPORT_SITESWAPS;
				}
			} else {
				if (bolPstyles) {
					objLshortcut = Constants.keyS_IMPORT_STYLES;
				}
			}
		}
		if (objLshortcut != null) {
			this.setAccelerator(objLshortcut);
		}
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("LoadFileJMenuItem.actionPerformed()");
		FileActions.doSelectAndLoadFile(this.objGcontrolJFrame, this.bolGnewLists, this.bolGsiteswaps, this.bolGstyles);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		byte bytLfileType = Constants.bytS_UNCLASS_NO_VALUE;
		byte bytLdoNotLoadFileType = Constants.bytS_UNCLASS_NO_VALUE;
		if (this.bolGnewLists) {
			bytLfileType = Constants.intS_FILE_ICON_LOAD_PATTERNS;
			bytLdoNotLoadFileType = Constants.intS_FILE_ICON_DO_NOT_LOAD_PATTERNS;
		} else {
			if (this.bolGsiteswaps) {
				if (this.bolGstyles) {
					bytLfileType = Constants.intS_FILE_ICON_IMPORT_PATTERNS;
					bytLdoNotLoadFileType = Constants.intS_FILE_ICON_DO_NOT_IMPORT_PATTERNS;
				} else {
					bytLfileType = Constants.intS_FILE_ICON_IMPORT_SITESWAPS;
					bytLdoNotLoadFileType = Constants.intS_FILE_ICON_DO_NOT_IMPORT_SITESWAPS;
				}
			} else {
				if (this.bolGstyles) {
					bytLfileType = Constants.intS_FILE_ICON_IMPORT_STYLES;
					bytLdoNotLoadFileType = Constants.intS_FILE_ICON_DO_NOT_IMPORT_STYLES;
				}
			}
		}
		this.icoGloadFile = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(bytLfileType, 0);
		this.icoGdoNotLoadFile = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(bytLdoNotLoadFileType, 0);

		this.setIcon(this.icoGloadFile);
		this.setDisabledIcon(this.icoGdoNotLoadFile);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLabel() {
		final int intLmenuLanguageIndex =
											this.bolGnewLists
																? Language.intS_MENU_OPEN_FILES
																: this.bolGsiteswaps || this.bolGstyles
																										? this.bolGsiteswaps && this.bolGstyles
																																				? Language.intS_MENU_IMPORT_PATTERNS
																																				: this.bolGsiteswaps
																																									? Language.intS_MENU_IMPORT_SITESWAPS
																																									: Language.intS_MENU_IMPORT_STYLES
																										: Constants.bytS_UNCLASS_NO_VALUE;
		final int intLtooltipLanguageIndex =
												this.bolGnewLists
																	? Language.intS_TOOLTIP_OPEN_PATTERNS_FILES_MENU
																	: this.bolGsiteswaps || this.bolGstyles
																											? this.bolGsiteswaps && this.bolGstyles
																																					? Language.intS_TOOLTIP_IMPORT_PATTERNS_FILES_MENU
																																					: this.bolGsiteswaps
																																										? Language.intS_TOOLTIP_IMPORT_SITESWAPS_FILES_MENU
																																										: Language.intS_TOOLTIP_IMPORT_STYLES_FILES_MENU
																											: Constants.bytS_UNCLASS_NO_VALUE;
		if (this.bolGnewLists || this.bolGsiteswaps || this.bolGstyles) {
			this.objGcontrolJFrame.setMenuItemLabel(this, intLmenuLanguageIndex);
		} else {
			this.setText(null);
		}
		this.objGcontrolJFrame.setMenuMnemonic(this, intLmenuLanguageIndex);

		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(intLtooltipLanguageIndex)
																												: null);
	}

	final boolean				bolGnewLists;
	final boolean				bolGsiteswaps;
	final boolean				bolGstyles;

	ImageIcon					icoGdoNotLoadFile;

	ImageIcon					icoGloadFile;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)LoadFileJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
