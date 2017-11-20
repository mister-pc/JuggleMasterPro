package fr.jugglemaster.control.util;

import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JToolTip;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

public class ExtendedJButton extends JButton {

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	final protected ControlJFrame	objGcontrolJFrame;

	public ExtendedJButton(ControlJFrame objPcontrolJFrame) {
		this(objPcontrolJFrame, null, Constants.bytS_UNCLASS_NO_VALUE, null);
	}

	public ExtendedJButton(ControlJFrame objPcontrolJFrame, ActionListener objPactionListener) {
		this(objPcontrolJFrame, null, Constants.bytS_UNCLASS_NO_VALUE, objPactionListener);
	}

	public ExtendedJButton(ControlJFrame objPcontrolJFrame, int intPmargins) {
		this(objPcontrolJFrame, null, intPmargins, null);
	}

	public ExtendedJButton(ControlJFrame objPcontrolJFrame, int intPmargins, ActionListener objPactionListener) {
		this(objPcontrolJFrame, null, intPmargins, objPactionListener);
	}

	public ExtendedJButton(ControlJFrame objPcontrolJFrame, String strPlabel) {
		this(objPcontrolJFrame, strPlabel, Constants.bytS_UNCLASS_NO_VALUE, null);
	}

	public ExtendedJButton(ControlJFrame objPcontrolJFrame, String strPlabel, int intPmargins) {
		this(objPcontrolJFrame, strPlabel, intPmargins, null);
	}

	public ExtendedJButton(ControlJFrame objPcontrolJFrame, String strPlabel, int intPmargins, ActionListener objPactionListener) {

		super(strPlabel);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setFont(objPcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setRolloverEnabled(true);
		this.setFocusable(false);
		if (intPmargins != Constants.bytS_UNCLASS_NO_VALUE) {
			this.setMargin(new Insets(intPmargins, intPmargins, intPmargins, intPmargins));
		}
		if (objPactionListener != null) {
			this.addActionListener(objPactionListener);
		}
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}
}
