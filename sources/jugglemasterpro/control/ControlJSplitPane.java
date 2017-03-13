/*
 * @(#)PanelJSplitPane.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ControlJSplitPane extends JSplitPane implements ComponentListener {

	/**
	 * Constructs
	 * 
	 * @param objPleftComponent
	 * @param objPrightComponent
	 */
	public ControlJSplitPane(Component objPleftComponent, Component objPrightComponent) {

		super(JSplitPane.HORIZONTAL_SPLIT, true, objPleftComponent, objPrightComponent);
		this.setOpaque(true);
		this.setDividerSize(5);
		this.setOneTouchExpandable(false);

		// this.setDividerLocation(0.5F);
		// this.setResizeWeight(0.5F);

		((BasicSplitPaneUI) this.getUI()).getDivider().addComponentListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentHidden(ComponentEvent objPcomponentEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentMoved(ComponentEvent objPcomponentEvent) {
		Tools.debug("ControlJSplitPane.componentMoved()");
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentResized(ComponentEvent objPcomponentEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentShown(ComponentEvent objPcomponentEvent) {}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PanelJSplitPane.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
