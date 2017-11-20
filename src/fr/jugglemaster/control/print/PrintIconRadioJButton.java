package fr.jugglemaster.control.print;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class PrintIconRadioJButton extends JPanel {

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final ExtendedJLabel	objGjLabel;
	private final JRadioButton		objGjRadioButton;

	public PrintIconRadioJButton(	ControlJFrame objPcontrolJFrame,
									String strPkey,
									String strPimage,
									boolean bolPselected,
									ButtonGroup objPbuttonGroup,
									ActionListener objPactionListener) {

		// Create widgets :
		this.objGjLabel =
							new ExtendedJLabel(objPcontrolJFrame, objPcontrolJFrame	.getJuggleMasterPro()
																					.getImageIcon(strPimage, Constants.bytS_UNCLASS_NO_VALUE));
		this.objGjRadioButton = new JRadioButton(Tools.getLocaleString(strPkey));
		this.objGjRadioButton.setFont(objPcontrolJFrame.getFont());
		this.objGjRadioButton.setOpaque(true);
		this.objGjRadioButton.setMnemonic(Tools.getMnemonicChar(strPkey));
		this.objGjRadioButton.addActionListener(objPactionListener);
		this.objGjRadioButton.setSelected(bolPselected);
		objPbuttonGroup.add(this.objGjRadioButton);

		// Add widgets :
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.add(this.objGjLabel);
		this.add(this.objGjRadioButton);
		this.setOpaque(true);
	}

	final public void addActionListener(ActionListener objPactionListener) {
		this.objGjRadioButton.addActionListener(objPactionListener);
	}

	final public boolean isSameAs(Object objPsource) {
		return (this.objGjRadioButton == objPsource);
	}

	final public boolean isSelected() {
		return this.objGjRadioButton.isSelected();
	}

	@Override final public void setEnabled(boolean bolPenabled) {
		this.objGjRadioButton.setEnabled(bolPenabled);
		this.objGjLabel.setEnabled(bolPenabled);
	}

	final public void setSelected(boolean bolPenabled) {
		this.objGjRadioButton.setSelected(bolPenabled);
	}
}
