/*
 * @(#)InvalidPatternsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.criteria;

import java.awt.event.ItemEvent;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJCheckBox;
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
final public class InvalidPatternsJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public InvalidPatternsJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, Language.intS_TOOLTIP_DISPLAY_INVALID_PATTERNS, Language.intS_TOOLTIP_DO_NOT_DISPLAY_INVALID_PATTERNS);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("InvalidPatternsJCheckBox.itemStateChanged()");
		this.validate();
		Preferences.setGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		this.objGcontrolJFrame.objGobjectsJList.setLists();
		this.objGcontrolJFrame.setMenusEnabled();
		this.setToolTipText();
		this.objGcontrolJFrame.objGinvalidPatternsJLabel.setToolTipText();
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)InvalidPatternsJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
