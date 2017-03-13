/*
 * @(#)ReverseStyleJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.style;

import java.awt.event.ItemEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.io.ExtendedTransferHandler;
import jugglemasterpro.control.util.ExtendedJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ReverseStyleJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ReverseStyleJCheckBox(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, Language.intS_TOOLTIP_REVERSE_STYLE, Language.intS_TOOLTIP_DO_NOT_REVERSE_STYLE);
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, false, true));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("ReverseStyleJCheckBox.itemStateChanged()");
		this.validate();
		this.objGcontrolJFrame.getJuggleMasterPro().doStopPattern();
		this.objGcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE, objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		Tools.debug("ReverseStyleCheckbox.itemStateChanged(): ControlJFrame.doRestartJuggling()");
		this.objGcontrolJFrame.doRestartJuggling();
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ReverseStyleJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
