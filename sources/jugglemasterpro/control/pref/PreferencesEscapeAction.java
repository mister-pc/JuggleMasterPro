/*
 * @(#)PreferencesEscapeAction.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;




import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import jugglemasterpro.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferencesEscapeAction extends AbstractAction {

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	private final PreferencesJDialog	objGpreferencesJDialog;




	/**
	 * Constructs
	 * 
	 * @param objPpreferencesJDialog
	 */
	public PreferencesEscapeAction(PreferencesJDialog objPpreferencesJDialog) {
		this.objGpreferencesJDialog = objPpreferencesJDialog;
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override public void actionPerformed(ActionEvent objPactionEvent) {
		this.objGpreferencesJDialog.objGcancelJButton.actionPerformed(null);
	}
}

/*
 * @(#)PreferencesEscapeAction.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
