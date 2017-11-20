package fr.jugglemaster.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.CopiesSupported;
import javax.print.attribute.standard.SheetCollate;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class PrintCopiesSubJPanel extends JPanel implements ActionListener, ChangeListener {

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean						bolGcopiesSupported;

	private final JCheckBox				objGcollateJCheckBox;

	private final ExtendedJLabel		objGcopiesJLabel;

	private final JSpinner				objGcopiesJSpinner;

	private final PrintJDialog			objGprintJDialog;
	private final SpinnerNumberModel	objGspinnerNumberModel;

	public PrintCopiesSubJPanel(PrintJDialog objPprintJDialog) {

		// Create widgets :
		this.objGprintJDialog = objPprintJDialog;
		this.objGcopiesJLabel =
								new ExtendedJLabel(	this.objGprintJDialog.getControlJFrame(),
													Tools.getLocaleString("label.numcopies"),
													SwingConstants.TRAILING);
		this.objGcopiesJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.numcopies"));
		this.objGcopiesJLabel.getAccessibleContext().setAccessibleName(Tools.getLocaleString("label.numcopies"));
		this.objGspinnerNumberModel = new SpinnerNumberModel(1, 1, 999, 1);
		this.objGcopiesJSpinner = new JSpinner(this.objGspinnerNumberModel);
		this.objGcopiesJSpinner.setOpaque(true);
		this.objGcopiesJLabel.setLabelFor(this.objGcopiesJSpinner);
		((JSpinner.NumberEditor) this.objGcopiesJSpinner.getEditor()).getTextField().setColumns(3);
		this.objGcopiesJSpinner.addChangeListener(this);

		this.objGcollateJCheckBox = new JCheckBox(Tools.getLocaleString("checkbox.collate"));
		this.objGcollateJCheckBox.setMnemonic(Tools.getMnemonicChar("checkbox.collate"));
		this.objGcollateJCheckBox.addActionListener(this);
		this.objGcollateJCheckBox.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGcollateJCheckBox.setOpaque(true);
		this.objGcollateJCheckBox.setEnabled(false);

		// Display widgets :
		this.setOpaque(true);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.copies"), this.objGprintJDialog.getControlJFrame().getFont()));
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																											0,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											3,
																											3,
																											6,
																											6,
																											GridBagConstraints.HORIZONTAL,
																											1.0F,
																											1.0F);

		this.add(this.objGcopiesJLabel, objLextendedGridBagConstraints);
		this.add(this.objGcopiesJSpinner, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(0, 1, 2, 1);
		this.add(this.objGcollateJCheckBox, objLextendedGridBagConstraints);
	}

	final public void actionPerformed(ActionEvent e) {
		if (this.objGcollateJCheckBox.isSelected()) {
			this.objGprintJDialog.getAttributes().add(SheetCollate.COLLATED);
		} else {
			this.objGprintJDialog.getAttributes().add(SheetCollate.UNCOLLATED);
		}
	}

	final public void setValues() {

		boolean bolLcopiesSupported = false;
		this.bolGcopiesSupported = false;
		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(Copies.class)) {
			bolLcopiesSupported = true;
		}
		this.objGcopiesJSpinner.setEnabled(bolLcopiesSupported);
		this.objGcopiesJLabel.setEnabled(bolLcopiesSupported);

		// Spinner min & max values :
		CopiesSupported objLcopiesSupported =
												(CopiesSupported) this.objGprintJDialog.getPrintService().getSupportedAttributeValues(	Copies.class,
																																		null,
																																		null);
		if (objLcopiesSupported == null) {
			objLcopiesSupported = new CopiesSupported(1, 999);
		}
		final int[][] intLmemberAA = objLcopiesSupported.getMembers();
		int intLmin = 1;
		int intLmax = Integer.MAX_VALUE;
		if (intLmemberAA.length > 0 && intLmemberAA[0].length > 0) {
			intLmin = intLmemberAA[0][0];
			intLmax = intLmemberAA[0][1];
		}
		this.objGspinnerNumberModel.setMinimum(new Integer(intLmin));
		this.objGspinnerNumberModel.setMaximum(new Integer(intLmax));

		// Spinner value :
		Copies objLcopies = (Copies) this.objGprintJDialog.getAttributes().get(Copies.class);
		if (objLcopies == null) {
			objLcopies = (Copies) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(Copies.class);
			if (objLcopies == null) {
				objLcopies = new Copies(1);
			}
		}
		int intLvalue = objLcopies.getValue();
		if (intLvalue < intLmin || intLvalue > intLmax) {
			intLvalue = intLmin;
		}
		this.objGspinnerNumberModel.setValue(new Integer(intLvalue));

		// Collate checkbox state :
		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(SheetCollate.class)) {
			this.bolGcopiesSupported = true;
		}
		SheetCollate objLsheetCollate = (SheetCollate) this.objGprintJDialog.getAttributes().get(SheetCollate.class);
		if (objLsheetCollate == null) {
			objLsheetCollate = (SheetCollate) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(SheetCollate.class);
			if (objLsheetCollate == null) {
				objLsheetCollate = SheetCollate.UNCOLLATED;
			}
		}
		this.objGcollateJCheckBox.setSelected(objLsheetCollate == SheetCollate.COLLATED);
		this.updateCollateJCheckBox();
	}

	final public void stateChanged(ChangeEvent e) {
		this.updateCollateJCheckBox();
		this.objGprintJDialog.getAttributes().add(new Copies(this.objGspinnerNumberModel.getNumber().intValue()));
	}

	final private void updateCollateJCheckBox() {
		this.objGcollateJCheckBox.setEnabled(this.objGspinnerNumberModel.getNumber().intValue() > 1 && this.bolGcopiesSupported);
	}
}
