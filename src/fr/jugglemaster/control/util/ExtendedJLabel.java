package fr.jugglemaster.control.util;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.SwingConstants;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

public class ExtendedJLabel extends JLabel implements MouseListener {

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame) {
		this(objPcontrolJFrame, null, null, null, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, Color objPbackgroundColor) {
		this(objPcontrolJFrame, null, null, null, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, Color objPbackgroundColor, int intPtooltip) {
		this(objPcontrolJFrame, null, null, null, objPbackgroundColor, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, Icon objLicon) {
		this(objPcontrolJFrame, null, null, objLicon, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, Icon objLicon, Color objPbackgroundColor) {
		this(objPcontrolJFrame, null, null, objLicon, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, Icon objLicon, Color objPbackgroundColor, int intPtooltip) {
		this(objPcontrolJFrame, null, null, objLicon, objPbackgroundColor, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, Icon objLicon, int intPtooltip) {
		this(objPcontrolJFrame, null, null, objLicon, null, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, int intPtooltip) {
		this(objPcontrolJFrame, null, null, null, null, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener) {
		this(objPcontrolJFrame, objPmouseListener, null, null, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, Color objPbackgroundColor) {
		this(objPcontrolJFrame, objPmouseListener, null, null, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, Color objPbackgroundColor, int intPtooltip) {
		this(objPcontrolJFrame, objPmouseListener, null, null, objPbackgroundColor, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, Icon objLicon) {
		this(objPcontrolJFrame, objPmouseListener, null, objLicon, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, Icon objLicon, Color objPbackgroundColor) {
		this(objPcontrolJFrame, objPmouseListener, null, objLicon, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, Icon objLicon, Color objPbackgroundColor, int intPtooltip) {
		this(objPcontrolJFrame, objPmouseListener, null, objLicon, objPbackgroundColor, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, Icon objLicon, int intPtooltip) {
		this(objPcontrolJFrame, objPmouseListener, null, objLicon, null, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, int intPtooltip) {
		this(objPcontrolJFrame, objPmouseListener, null, null, null, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, String strPlabel) {
		this(objPcontrolJFrame, objPmouseListener, strPlabel, null, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, String strPlabel, Color objPbackgroundColor) {
		this(objPcontrolJFrame, objPmouseListener, strPlabel, null, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(	ControlJFrame objPcontrolJFrame,
							MouseListener objPmouseListener,
							String strPlabel,
							Color objPbackgroundColor,
							int intPtooltip) {
		this(objPcontrolJFrame, objPmouseListener, strPlabel, null, objPbackgroundColor, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, String strPlabel, Icon objLicon) {
		this(objPcontrolJFrame, objPmouseListener, strPlabel, objLicon, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, String strPlabel, Icon objLicon, Color objPbackgroundColor) {
		this(objPcontrolJFrame, objPmouseListener, strPlabel, objLicon, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(	ControlJFrame objPcontrolJFrame,
							MouseListener objPmouseListener,
							String strPlabel,
							Icon objLicon,
							Color objPbackgroundColor,
							int intPtooltip) {

		super(strPlabel, objLicon, SwingConstants.CENTER);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGtooltip = intPtooltip;
		this.setFont(this.objGcontrolJFrame.getFont());
		if (objPbackgroundColor != null) {
			this.setBackground(objPbackgroundColor);
		}
		this.setOpaque(true);
		if (objPmouseListener != null) {
			this.objGmouseListener = objPmouseListener;
			this.addMouseListener(this.objGmouseListener);
		} else {
			if (this.objGmouseListener != null) {
				this.removeMouseListener(this.objGmouseListener);
			}
		}
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, String strPlabel, Icon objLicon, int intPtooltip) {
		this(objPcontrolJFrame, objPmouseListener, strPlabel, objLicon, null, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, MouseListener objPmouseListener, String strPlabel, int intPtooltip) {
		this(objPcontrolJFrame, objPmouseListener, strPlabel, null, null, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, String strPlabel) {
		this(objPcontrolJFrame, null, strPlabel, null, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, String strPlabel, Color objPbackgroundColor) {
		this(objPcontrolJFrame, null, strPlabel, null, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, String strPlabel, Color objPbackgroundColor, int intPtooltip) {
		this(objPcontrolJFrame, null, strPlabel, null, objPbackgroundColor, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, String strPlabel, Icon objLicon) {
		this(objPcontrolJFrame, null, strPlabel, objLicon, null, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, String strPlabel, Icon objLicon, Color objPbackgroundColor) {
		this(objPcontrolJFrame, null, strPlabel, objLicon, objPbackgroundColor, Constants.bytS_UNCLASS_NO_VALUE);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, String strPlabel, Icon objLicon, int intPtooltip) {
		this(objPcontrolJFrame, null, strPlabel, objLicon, null, intPtooltip);
	}

	public ExtendedJLabel(ControlJFrame objPcontrolJFrame, String strPlabel, int intPtooltip) {
		this(objPcontrolJFrame, null, strPlabel, null, null, intPtooltip);
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	@Override public void mouseClicked(MouseEvent objPmouseEvent) {}

	@Override public void mouseEntered(MouseEvent objPmouseEvent) {}

	@Override public void mouseExited(MouseEvent objPmouseEvent) {}

	@Override public void mousePressed(MouseEvent objPmouseEvent) {}

	@Override public void mouseReleased(MouseEvent objPmouseEvent) {}

	public void setToolTipText() {
		this.setToolTipText(Preferences.getGlobalBooleanPreference(this.objGmouseListener != null ? Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS
																									: Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS)
																																					? this.objGcontrolJFrame.getLanguageString(this.intGtooltip)
																																					: null);
	}

	private final int				intGtooltip;

	protected final ControlJFrame	objGcontrolJFrame;

	private MouseListener			objGmouseListener;

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
