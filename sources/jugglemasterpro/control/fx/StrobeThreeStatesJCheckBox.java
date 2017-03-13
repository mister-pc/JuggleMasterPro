package jugglemasterpro.control.fx;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesJCheckBox;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;

final public class StrobeThreeStatesJCheckBox extends ThreeStatesJCheckBox {

	public StrobeThreeStatesJCheckBox(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, Language.intS_TOOLTIP_ACTIVATE_ROBOT, Language.intS_TOOLTIP_ACTIVATE_STROBE, Language.intS_TOOLTIP_DEACTIVATE_FLASH);
	}

	@Override final public void itemStateChanged(Boolean bolPstateBoolean) {
		FXActions.doStrobe(this.objGcontrolJFrame, bolPstateBoolean);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
