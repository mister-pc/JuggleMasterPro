/**
 * 
 */
package fr.jugglemaster.control.util;

import java.awt.event.ActionEvent;
import fr.jugglemaster.util.Constants;

/**
 * @author BeLO
 */
public class ExtendedActionEvent extends ActionEvent {

	/**
	 * @param source
	 * @param id
	 * @param command
	 */
	public ExtendedActionEvent(ActionEvent objPactionEvent) {
		super(objPactionEvent.getSource(), objPactionEvent.getID(), objPactionEvent.getActionCommand());
	}

	final public void consumeEvent() {
		this.consume();
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}
