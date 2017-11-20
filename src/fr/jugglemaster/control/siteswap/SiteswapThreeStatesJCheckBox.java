/*
 * @(#)SiteswapJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.siteswap;

import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
import fr.jugglemaster.control.util.ThreeStatesJCheckBox;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class SiteswapThreeStatesJCheckBox extends ThreeStatesJCheckBox {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public SiteswapThreeStatesJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(	objPcontrolJFrame,
				Language.intS_TOOLTIP_DISPLAY_SITESWAP,
				Language.intS_TOOLTIP_DISPLAY_THROWS,
				Language.intS_TOOLTIP_DO_NOT_DISPLAY_SITESWAP);
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, true, false));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPitemEvent
	 */
	@Override final public void itemStateChanged(Boolean bolPstateBoolean) {
		SiteswapActions.doDisplaySiteswap(this.objGcontrolJFrame, bolPstateBoolean);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPselected
	 */
	// final public void select(
	// boolean bolPselected) {
	// this.removeItemListener(this);
	// this.setSelected(bolPselected);
	// this.addItemListener(this);
	// }
}

/*
 * @(#)SiteswapJCheckBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
