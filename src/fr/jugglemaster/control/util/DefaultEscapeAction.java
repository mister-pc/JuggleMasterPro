/*
 * @(#)DefaultEscapeAction.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.util;




import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import javax.swing.AbstractAction;
import fr.jugglemaster.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class DefaultEscapeAction extends AbstractAction {

	/**
	 * Constructs
	 * 
	 * @param objPwindowListener
	 */
	public DefaultEscapeAction(WindowListener objPwindowListener) {
		super(Constants.strS_ENGINE_ESCAPE_ACTION);
		this.objGwindowListener = objPwindowListener;
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override public void actionPerformed(ActionEvent objPactionEvent) {
		this.objGwindowListener.windowClosing(null);
	}




	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	final private WindowListener	objGwindowListener;
}

/*
 * @(#)DefaultEscapeAction.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
