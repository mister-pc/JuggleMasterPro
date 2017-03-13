/*
 * @(#)ReverseSiteswapJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.siteswap;

import java.awt.event.ItemEvent;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.io.ExtendedTransferHandler;
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
final public class ReverseSiteswapJCheckBox extends ExtendedJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ReverseSiteswapJCheckBox(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, Language.intS_TOOLTIP_REVERSE_SITESWAP, Language.intS_TOOLTIP_DO_NOT_REVERSE_SITESWAP);
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, true, false));
	}

	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("ReverseSiteswapJCheckBox.itemStateChanged()");
		final boolean bolLreverse = (objPitemEvent.getStateChange() == ItemEvent.SELECTED);
		this.validate();
		this.objGcontrolJFrame.getJuggleMasterPro().doStopPattern();
		this.objGcontrolJFrame.saveControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP, bolLreverse);

		Tools.debug("ReverseSiteswapJCheckBox.itemStateChanged(): ControlJFrame.doRestartJuggling()");
		this.objGcontrolJFrame.doRestartJuggling();
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ReverseSiteswapJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
