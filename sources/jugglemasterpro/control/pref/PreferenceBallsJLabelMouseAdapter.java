/**
 * 
 */
package jugglemasterpro.control.pref;

import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * @author BeLO
 */
public class PreferenceBallsJLabelMouseAdapter extends MouseAdapter {

	public PreferenceBallsJLabelMouseAdapter(PreferencesJDialog objPpreferencesJDialog) {
		this.objGpreferencesJDialog = objPpreferencesJDialog;
	}

	@Override final public void mouseClicked(MouseEvent objPmouseEvent) {

		Tools.debug("PreferenceBallsJLabelMouseAdapter.mouseClicked()");
		this.objGpreferencesJDialog.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER].itemStateChanged(new ItemEvent(	this.objGpreferencesJDialog.objGbooleanGlobalJCheckBoxA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER],
																																			ItemEvent.ITEM_STATE_CHANGED,
																																			this.objGpreferencesJDialog.objGbooleanGlobalJLabelA[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER],
																																			Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER)
																																																								? ItemEvent.DESELECTED
																																																								: ItemEvent.SELECTED));
	}

	private final PreferencesJDialog	objGpreferencesJDialog;
}
