/*
 * @(#)MirrorJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.fx;

import java.awt.event.ItemEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class MirrorJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public MirrorJCheckBox(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, Language.intS_TOOLTIP_MIRROR, Language.intS_TOOLTIP_MIRROR);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {
		Tools.debug("MirrorJCheckBox.itemStateChanged()");
		this.objGcontrolJFrame.getJuggleMasterPro().doStopPattern();
		this.objGcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_MIRROR, objPitemEvent.getStateChange() == ItemEvent.SELECTED);

		// objGreversePatternJButton.setImage();
		Tools.debug("MirrorCheckbox.itemStateChanged(): ControlJFrame.doRestartJuggling()");
		this.objGcontrolJFrame.doRestartJuggling();
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)MirrorJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
