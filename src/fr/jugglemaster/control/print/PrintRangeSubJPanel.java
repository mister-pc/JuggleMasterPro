package fr.jugglemaster.control.print;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import javax.print.attribute.standard.PageRanges;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.NumberFormatter;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;
import sun.print.SunPageSelection;

final public class PrintRangeSubJPanel extends JPanel implements ActionListener, FocusListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final public static void addToGB(Component comp, Container cont, GridBagLayout gridbag, GridBagConstraints constraints) {
		gridbag.setConstraints(comp, constraints);
		cont.add(comp);
	}

	final public static JRadioButton createRadioButton(String strPkey, ActionListener al) {
		final JRadioButton objLjRadioButton = new JRadioButton(Tools.getLocaleString(strPkey));
		objLjRadioButton.setMnemonic(Tools.getMnemonicChar(strPkey));
		objLjRadioButton.addActionListener(al);

		return objLjRadioButton;
	}

	private boolean						bolGprintRangeSupported;

	private final JRadioButton			objGallJRadioButton;

	private final PageRanges			objGallPageRanges	= new PageRanges(1, Integer.MAX_VALUE);

	private final JRadioButton			objGpagesJRadioButton;

	private final PrintJDialog			objGprintJDialog;

	private final JFormattedTextField	objGrangeFromJFormattedTextField;

	private final JFormattedTextField	objGrangeToJFormattedTextField;

	private final ExtendedJLabel		objGrangeToJLabel;

	private JRadioButton				objGselectJRadioButton;

	public PrintRangeSubJPanel(PrintJDialog objPprintJDialog) {

		this.objGprintJDialog = objPprintJDialog;
		final GridBagLayout gridbag = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();

		this.setLayout(gridbag);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.printrange"), this.objGprintJDialog.getControlJFrame().getFont()));

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(3, 6, 3, 6);
		c.gridwidth = GridBagConstraints.REMAINDER;

		final ButtonGroup bg = new ButtonGroup();
		final JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlTop.setOpaque(true);
		this.objGallJRadioButton = PrintRangeSubJPanel.createRadioButton("radiobutton.rangeall", this);
		this.objGallJRadioButton.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGallJRadioButton.setSelected(true);
		bg.add(this.objGallJRadioButton);
		pnlTop.add(this.objGallJRadioButton);
		PrintRangeSubJPanel.addToGB(pnlTop, this, gridbag, c);

		final JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlBottom.setOpaque(true);
		this.objGpagesJRadioButton = PrintRangeSubJPanel.createRadioButton("radiobutton.rangepages", this);
		this.objGpagesJRadioButton.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		bg.add(this.objGpagesJRadioButton);
		pnlBottom.add(this.objGpagesJRadioButton);
		final DecimalFormat format = new DecimalFormat("####0");
		format.setMinimumFractionDigits(0);
		format.setMaximumFractionDigits(0);
		format.setMinimumIntegerDigits(0);
		format.setMaximumIntegerDigits(5);
		format.setParseIntegerOnly(true);
		format.setDecimalSeparatorAlwaysShown(false);
		final NumberFormatter nf = new NumberFormatter(format);
		nf.setMinimum(new Integer(1));
		nf.setMaximum(new Integer(Integer.MAX_VALUE));
		nf.setAllowsInvalid(true);
		nf.setCommitsOnValidEdit(true);
		this.objGrangeFromJFormattedTextField = new JFormattedTextField(nf);
		this.objGrangeFromJFormattedTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGrangeFromJFormattedTextField.setColumns(4);
		this.objGrangeFromJFormattedTextField.setEnabled(false);
		this.objGrangeFromJFormattedTextField.addActionListener(this);
		this.objGrangeFromJFormattedTextField.addFocusListener(this);
		this.objGrangeFromJFormattedTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		this.objGrangeFromJFormattedTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString("radiobutton.rangepages"));
		pnlBottom.add(this.objGrangeFromJFormattedTextField);
		this.objGrangeToJLabel = new ExtendedJLabel(this.objGprintJDialog.getControlJFrame(), Tools.getLocaleString("label.rangeto"));
		this.objGrangeToJLabel.setEnabled(false);
		pnlBottom.add(this.objGrangeToJLabel);
		NumberFormatter objLnumberFormatter;
		try {
			objLnumberFormatter = (NumberFormatter) nf.clone();
		} catch (final Throwable objPthrowable) {
			objLnumberFormatter = new NumberFormatter();
		}
		this.objGrangeToJFormattedTextField = new JFormattedTextField(objLnumberFormatter);
		this.objGrangeToJFormattedTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGrangeToJFormattedTextField.setColumns(4);
		this.objGrangeToJFormattedTextField.setEnabled(false);
		this.objGrangeToJFormattedTextField.addFocusListener(this);
		this.objGrangeToJFormattedTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString("label.rangeto"));
		pnlBottom.add(this.objGrangeToJFormattedTextField);
		PrintRangeSubJPanel.addToGB(pnlBottom, this, gridbag, c);
	}

	final public void actionPerformed(ActionEvent objPactionEvent) {
		final Object objLsourceObject = objPactionEvent.getSource();
		// SunPageSelection select = SunPageSelection.ALL;

		this.setupRangeWidgets();

		if (objLsourceObject == this.objGallJRadioButton) {
			this.objGprintJDialog.getAttributes().add(this.objGallPageRanges);
		} else if (objLsourceObject == this.objGselectJRadioButton) {
			// select = SunPageSelection.SELECTION;
		} else if (objLsourceObject == this.objGpagesJRadioButton || objLsourceObject == this.objGrangeFromJFormattedTextField
					|| objLsourceObject == this.objGrangeToJFormattedTextField) {
			this.updateRangeAttribute();
			// select = SunPageSelection.RANGE;
		}
	}

	final public void focusGained(FocusEvent objPfocusEvent) {}

	final public void focusLost(FocusEvent objPfocusEvent) {
		final Object objLsourceObject = objPfocusEvent.getSource();

		if ((objLsourceObject == this.objGrangeFromJFormattedTextField) || (objLsourceObject == this.objGrangeToJFormattedTextField)) {
			this.updateRangeAttribute();
		}
	}

	final private void setupRangeWidgets() {

		final boolean bolLrangeEnabled = (this.objGpagesJRadioButton.isSelected() && this.bolGprintRangeSupported);
		this.objGrangeFromJFormattedTextField.setEnabled(bolLrangeEnabled);
		this.objGrangeToJFormattedTextField.setEnabled(bolLrangeEnabled);
		this.objGrangeToJLabel.setEnabled(bolLrangeEnabled);
	}

	final public void setValues() {

		this.bolGprintRangeSupported = this.objGprintJDialog.getPrintService().isAttributeCategorySupported(PageRanges.class);

		SunPageSelection objLsunPageSelection = SunPageSelection.ALL;
		int intLmin = 1;
		int intLmax = 1;

		final PageRanges objLpageRanges = (PageRanges) this.objGprintJDialog.getAttributes().get(PageRanges.class);
		if (objLpageRanges != null) {
			if (!objLpageRanges.equals(this.objGallPageRanges)) {
				objLsunPageSelection = SunPageSelection.RANGE;

				final int[][] intLmemberAA = objLpageRanges.getMembers();
				if (intLmemberAA.length > 0 && intLmemberAA[0].length > 1) {
					intLmin = intLmemberAA[0][0];
					intLmax = intLmemberAA[0][1];
				}
			}
		}

		if (objLsunPageSelection == SunPageSelection.ALL) {
			this.objGallJRadioButton.setSelected(true);
		} else if (objLsunPageSelection == SunPageSelection.SELECTION) {
			// Comment this for now - objGselectJRadioButton is not initialized
			// because Selection button is not added.
			// See PrintRangeSubJPanel above.

			// objGselectJRadioButton.setSelected(true);
		} else { // RANGE
			this.objGpagesJRadioButton.setSelected(true);
		}
		this.objGrangeFromJFormattedTextField.setValue(new Integer(intLmin));
		this.objGrangeToJFormattedTextField.setValue(new Integer(intLmax));
		this.objGallJRadioButton.setEnabled(this.bolGprintRangeSupported);
		this.objGpagesJRadioButton.setEnabled(this.bolGprintRangeSupported);
		this.setupRangeWidgets();
	}

	final private void updateRangeAttribute() {

		int intLmin;
		int intLmax;

		try {
			intLmin = Integer.parseInt(this.objGrangeFromJFormattedTextField.getText());
		} catch (final Throwable objPthrowable) {
			intLmin = 1;
		}
		try {
			intLmax = Integer.parseInt(this.objGrangeToJFormattedTextField.getText());
		} catch (final Throwable objPthrowable) {
			intLmax = intLmin;
		}

		if (intLmin < 1) {
			intLmin = 1;
			this.objGrangeFromJFormattedTextField.setValue(new Integer(intLmin));
		}
		if (intLmax < intLmin) {
			intLmax = intLmin;
			this.objGrangeToJFormattedTextField.setValue(new Integer(intLmin));
		}

		this.objGprintJDialog.getAttributes().add(new PageRanges(intLmin, intLmax));
	}
}
