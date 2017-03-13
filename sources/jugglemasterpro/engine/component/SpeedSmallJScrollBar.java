package jugglemasterpro.engine.component;

import jugglemasterpro.control.util.ExtendedJScrollBar;
import jugglemasterpro.util.Constants;

final public class SpeedSmallJScrollBar extends ExtendedJScrollBar {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public SpeedSmallJScrollBar() {
		super(	null,
				Constants.bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE,
				1,
				Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE,
				Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE + 1,
				5,
				Constants.bytS_UNCLASS_NO_VALUE);
		this.setUI(NoArrowJScrollBarUI.getUI());
	}
}
