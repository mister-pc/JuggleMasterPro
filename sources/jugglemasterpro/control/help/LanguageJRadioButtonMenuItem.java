/*
 * @(#)LanguageJRadioButtonMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.help;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JRadioButtonMenuItem;
import jugglemasterpro.control.ControlJFrame;
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
final public class LanguageJRadioButtonMenuItem extends JRadioButtonMenuItem implements ItemListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param intPlanguageIndex
	 */
	public LanguageJRadioButtonMenuItem(ControlJFrame objPcontrolJFrame, int intPlanguageIndex) {
		super(Strings.strS_EMPTY, intPlanguageIndex == 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGlanguageIndex = intPlanguageIndex;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.addItemListener(this);
	}

	final public void doLoadImages() {
		final ImageIcon icoLflag =
									this.objGcontrolJFrame	.getJuggleMasterPro()
															.getImageIcon(	Strings.doConcat(	this.objGcontrolJFrame.getJuggleMasterPro().strS_CODE_BASE,
																								Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA],
																								this.objGcontrolJFrame.getJuggleMasterPro().chrGpathSeparator,
																								this.objGcontrolJFrame.objGlanguageA[this.intGlanguageIndex].getPropertyValueString(Language.intS_LANGUAGE_ISO_639_1_CODE)),
																			Constants.intS_FILE_ICON_LANGUAGE,
																			0);
		this.setIcon(icoLflag);
		this.setDisabledIcon(icoLflag);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("LanguageJRadioButtonMenuItem.itemStateChanged()");
		if (objPitemEvent.getStateChange() == ItemEvent.SELECTED) {
			this.objGcontrolJFrame.setLanguage(this.intGlanguageIndex, true);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPstate
	 */
	final public void select() {
		this.removeItemListener(this);
		this.setSelected(true);
		this.addItemListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLabel() {
		this.setText(this.objGcontrolJFrame.objGlanguageA[this.intGlanguageIndex].getPropertyValueString(Language.intS_LANGUAGE_NAME));
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.objGlanguageA[this.intGlanguageIndex].getPropertyValueString(Language.intS_TOOLTIP_SET_LANGUAGE_MENU)
																												: null);
		if (this.objGcontrolJFrame.geLanguageIndex() == this.intGlanguageIndex) {
			this.select();
		}
	}

	private final int			intGlanguageIndex;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)LanguageJRadioButtonMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
