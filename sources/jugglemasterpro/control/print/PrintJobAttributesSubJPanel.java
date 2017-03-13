package jugglemasterpro.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.RequestingUserName;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class PrintJobAttributesSubJPanel extends JPanel implements /* ActionListener, *//* ChangeListener, */FocusListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	// private JLabel objGpriorityJLabel;
	private final JLabel		objGjobNameJLabel;

	// private JSpinner objGpriorityJSpinner;
	// private SpinnerNumberModel objGspinnerNumberModel;
	// private JCheckBox objGsheetsJCheckBox;
	private final JTextField	objGjobNameJTextField;

	private final PrintJDialog	objGprintJDialog;

	private final JLabel		objGuserNameJLabel;
	private final JTextField	objGuserNameJTextField;

	public PrintJobAttributesSubJPanel(PrintJDialog objPprintJDialog) {

		this.objGprintJDialog = objPprintJDialog;

		// Create widgets :
		// this.objGsheetsJCheckBox = new JCheckBox(JuggleTools.getLocaleString("checkbox.jobsheets"));
		// this.objGsheetsJCheckBox.setMnemonic(JuggleTools.getMnemonicChar("checkbox.jobsheets"));
		// this.objGsheetsJCheckBox.addActionListener(this);
		final String strLjobName = "label.jobname";
		this.objGjobNameJLabel = new JLabel(Tools.getLocaleString(strLjobName), SwingConstants.TRAILING);
		this.objGjobNameJLabel.setDisplayedMnemonic(Tools.getMnemonicChar(strLjobName));
		this.objGjobNameJLabel.setOpaque(true);
		this.objGjobNameJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGjobNameJTextField = new JTextField();
		this.objGjobNameJLabel.setLabelFor(this.objGjobNameJTextField);
		this.objGjobNameJTextField.setOpaque(true);
		this.objGjobNameJTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGjobNameJTextField.addFocusListener(this);
		this.objGjobNameJTextField.setFocusAccelerator(Tools.getMnemonicChar(strLjobName));
		this.objGjobNameJTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString(strLjobName));

		final String strLuserName = "label.username";
		this.objGuserNameJLabel = new JLabel(Tools.getLocaleString(strLuserName), SwingConstants.TRAILING);
		this.objGuserNameJLabel.setDisplayedMnemonic(Tools.getMnemonicChar(strLuserName));
		this.objGuserNameJLabel.setOpaque(true);
		this.objGuserNameJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGuserNameJTextField = new JTextField();
		this.objGuserNameJLabel.setLabelFor(this.objGuserNameJTextField);
		this.objGuserNameJTextField.addFocusListener(this);
		this.objGuserNameJTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGuserNameJTextField.setOpaque(true);
		this.objGuserNameJTextField.setFocusAccelerator(Tools.getMnemonicChar(strLuserName));
		this.objGuserNameJTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString(strLuserName));

		// Add widgets :
		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.jobattributes"), this.objGprintJDialog.getControlJFrame()
																																.getFont()));
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.LINE_END,
																											3,
																											3,
																											6,
																											6,
																											GridBagConstraints.HORIZONTAL,
																											1.0F,
																											0.0F);

		this.add(this.objGjobNameJLabel, objLextendedGridBagConstraints);
		this.add(this.objGuserNameJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, GridBagConstraints.RELATIVE);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		this.add(this.objGjobNameJTextField, objLextendedGridBagConstraints);
		this.add(this.objGuserNameJTextField, objLextendedGridBagConstraints);
		// c.fill = GridBagConstraints.NONE;
		// c.insets = objS_COMP_INSETS;
		// c.weighty = 1.0;

		// c.anchor = GridBagConstraints.LINE_START;
		// addToGB(this.objGsheetsJCheckBox, this, gridbag, c);

		// JPanel pnlTop = new JPanel();
		// objGpriorityJLabel = new JLabel(JuggleTools.getLocaleString("label.priority"), JLabel.TRAILING);
		// objGpriorityJLabel.setDisplayedMnemonic(JuggleTools.getMnemonicChar("label.priority"));

		// pnlTop.add(objGpriorityJLabel);
		// objGspinnerNumberModel = new SpinnerNumberModel(1, 1, 100, 1);
		// objGpriorityJSpinner = new JSpinner(objGspinnerNumberModel);
		// objGpriorityJLabel.setLabelFor(objGpriorityJSpinner);
		// REMIND
		// ((JSpinner.NumberEditor)objGpriorityJSpinner.getEditor()).getTextField().setColumns(3);
		// objGpriorityJSpinner.addChangeListener(this);
		// pnlTop.add(objGpriorityJSpinner);

		// c.anchor = GridBagConstraints.LINE_END;
		// c.gridwidth = GridBagConstraints.REMAINDER;
		// pnlTop.getAccessibleContext().setAccessibleName(
		// JuggleTools.getLocaleString("label.priority"));
		// addToGB(pnlTop, this, gridbag, c);

		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.anchor = GridBagConstraints.CENTER;
		// c.weightx = 0.0;
		// c.gridwidth = 1;
		// addToGB(objGjobNameJLabel, this, gridbag, c);
		// c.weightx = 1.0;
		// c.gridwidth = GridBagConstraints.REMAINDER;
		// addToGB(objGjobNameJTextField, this, gridbag, c);

		// c.weightx = 0.0;
		// c.gridwidth = 1;
		// addToGB(objGuserNameJLabel, this, gridbag, c);
		// c.gridwidth = GridBagConstraints.REMAINDER;
		// addToGB(objGuserNameJTextField, this, gridbag, c);
	}

	// final public void actionPerformed(ActionEvent objPactionEvent) {
	// if (objGsheetsJCheckBox.isSelected()) {
	// objGhashPrintRequestAttributeSet.add(JobSheets.STANDARD);
	// } else {
	// objGhashPrintRequestAttributeSet.add(JobSheets.NONE);
	// }
	// }

	// final public void stateChanged(ChangeEvent objPchangeEvent) {
	// objGhashPrintRequestAttributeSet.add(new JobPriority(objGspinnerNumberModel.getNumber().intValue()));
	// }

	final public void focusGained(FocusEvent objPfocusEvent) {}

	final public void focusLost(FocusEvent objPfocusEvent) {
		final Object objLsourceObject = objPfocusEvent.getSource();

		if (objLsourceObject == this.objGjobNameJTextField) {
			this.objGprintJDialog.getAttributes().add(new JobName(this.objGjobNameJTextField.getText(), Locale.getDefault()));
		} else if (objLsourceObject == this.objGuserNameJTextField) {
			this.objGprintJDialog.getAttributes().add(new RequestingUserName(this.objGuserNameJTextField.getText(), Locale.getDefault()));
		}
	}

	final public void setValues() {

		// boolean jsSupported = false;
		// boolean jpSupported = false;

		// setup JobSheets checkbox
		// if (objGprintService.isAttributeCategorySupported(JobSheets.class)) {
		// jsSupported = true;
		// }
		// JobSheets js = (JobSheets)objGhashPrintRequestAttributeSet.get(JobSheets.class);
		// if (js == null) {
		// js = (JobSheets)objGprintService.getDefaultAttributeValue(JobSheets.class);
		// if (js == null) {
		// js = JobSheets.NONE;
		// }
		// }
		// objGsheetsJCheckBox.setSelected(js != JobSheets.NONE);
		// objGsheetsJCheckBox.setEnabled(jsSupported);

		// setup JobPriority spinner
		// if (objGprintService.isAttributeCategorySupported(JobPriority.class)) {
		// jpSupported = true;
		// }
		// JobPriority jp = (JobPriority)objGhashPrintRequestAttributeSet.get(JobPriority.class);
		// if (jp == null) {
		// jp = (JobPriority)objGprintService.getDefaultAttributeValue(JobPriority.class);
		// if (jp == null) {
		// jp = new JobPriority(1);
		// }
		// }
		// int value = jp.getValue();
		// if ((value < 1) || (value > 100)) {
		// value = 1;
		// }
		// objGspinnerNumberModel.setValue(new Integer(value));
		// objGpriorityJLabel.setEnabled(jpSupported);
		// objGpriorityJSpinner.setEnabled(jpSupported);

		// Setup job name :
		boolean bolLjobNameEnabled = false;
		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(JobName.class)) {
			bolLjobNameEnabled = true;
		}
		JobName objLjobName = (JobName) this.objGprintJDialog.getAttributes().get(JobName.class);
		if (objLjobName == null) {
			objLjobName = (JobName) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(JobName.class);
			if (objLjobName == null) {
				objLjobName = new JobName("", Locale.getDefault());
			}
		}
		this.objGjobNameJTextField.setText(objLjobName.getValue());
		this.objGjobNameJTextField.setEnabled(bolLjobNameEnabled);
		this.objGjobNameJLabel.setEnabled(bolLjobNameEnabled);

		// Setup user name :
		boolean bolLuserNameEnabled = false;
		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(RequestingUserName.class)) {
			bolLuserNameEnabled = true;
		}
		RequestingUserName objLrequestingUserName = (RequestingUserName) this.objGprintJDialog.getAttributes().get(RequestingUserName.class);
		if (objLrequestingUserName == null) {
			objLrequestingUserName = (RequestingUserName) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(RequestingUserName.class);
			if (objLrequestingUserName == null) {
				objLrequestingUserName = new RequestingUserName("", Locale.getDefault());
			}
		}
		this.objGuserNameJTextField.setText(objLrequestingUserName.getValue());
		this.objGuserNameJTextField.setEnabled(bolLuserNameEnabled);
		this.objGuserNameJLabel.setEnabled(bolLuserNameEnabled);
	}
}
