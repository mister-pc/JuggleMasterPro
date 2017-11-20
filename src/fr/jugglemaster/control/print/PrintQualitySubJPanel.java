package fr.jugglemaster.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.attribute.standard.PrintQuality;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class PrintQualitySubJPanel extends JPanel implements ActionListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final JRadioButton	objGdraftJRadioButton;

	private final JRadioButton	objGhighJRadioButton;

	private final JRadioButton	objGnormalJRadioButton;
	private final PrintJDialog	objGprintJDialog;

	public PrintQualitySubJPanel(PrintJDialog objPprintJDialog) {

		this.objGprintJDialog = objPprintJDialog;

		// Set widgets :
		this.objGdraftJRadioButton = new JRadioButton(Tools.getLocaleString("radiobutton.draftq"));
		this.objGdraftJRadioButton.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGdraftJRadioButton.setOpaque(true);
		this.objGdraftJRadioButton.setMnemonic(Tools.getMnemonicChar("radiobutton.draftq"));
		this.objGdraftJRadioButton.addActionListener(this);
		this.objGnormalJRadioButton = new JRadioButton(Tools.getLocaleString("radiobutton.normalq"));
		this.objGnormalJRadioButton.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGnormalJRadioButton.setOpaque(true);
		this.objGnormalJRadioButton.setMnemonic(Tools.getMnemonicChar("radiobutton.normalq"));
		this.objGnormalJRadioButton.addActionListener(this);
		this.objGnormalJRadioButton.setSelected(true);
		this.objGhighJRadioButton = new JRadioButton(Tools.getLocaleString("radiobutton.highq"));
		this.objGhighJRadioButton.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGhighJRadioButton.setOpaque(true);
		this.objGhighJRadioButton.setMnemonic(Tools.getMnemonicChar("radiobutton.highq"));
		this.objGhighJRadioButton.addActionListener(this);
		final ButtonGroup objLbuttonGroup = new ButtonGroup();
		objLbuttonGroup.add(this.objGdraftJRadioButton);
		objLbuttonGroup.add(this.objGnormalJRadioButton);
		objLbuttonGroup.add(this.objGhighJRadioButton);

		// Add widgets :
		this.setOpaque(true);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.quality"), this.objGprintJDialog.getControlJFrame().getFont()));
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											6,
																											6,
																											6,
																											6,
																											GridBagConstraints.BOTH,
																											0.0F,
																											1.0F);
		this.add(this.objGdraftJRadioButton, objLextendedGridBagConstraints);
		this.add(this.objGnormalJRadioButton, objLextendedGridBagConstraints);
		this.add(this.objGhighJRadioButton, objLextendedGridBagConstraints);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		final Object objPsourceObject = objPactionEvent.getSource();

		if (objPsourceObject == this.objGdraftJRadioButton) {
			this.objGprintJDialog.getAttributes().add(PrintQuality.DRAFT);
		} else if (objPsourceObject == this.objGnormalJRadioButton) {
			this.objGprintJDialog.getAttributes().add(PrintQuality.NORMAL);
		} else if (objPsourceObject == this.objGhighJRadioButton) {
			this.objGprintJDialog.getAttributes().add(PrintQuality.HIGH);
		}
	}

	final public void setValues() {

		// Set enabled values :
		boolean bolLdraftEnabled = false;
		boolean bolLnormalEnabled = false;
		boolean bolLhighEnabled = false;

		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(PrintQuality.class)) {
			final Object objLvaluesObject =
											this.objGprintJDialog	.getPrintService()
																	.getSupportedAttributeValues(	PrintQuality.class,
																									null,
																									this.objGprintJDialog.getAttributes());
			if (objLvaluesObject instanceof PrintQuality[]) {
				for (final PrintQuality objLprintQuality : (PrintQuality[]) objLvaluesObject) {
					bolLdraftEnabled |= (objLprintQuality == PrintQuality.DRAFT);
					bolLnormalEnabled |= (objLprintQuality == PrintQuality.NORMAL);
					bolLhighEnabled |= (objLprintQuality == PrintQuality.HIGH);
				}
			}
		}

		this.objGdraftJRadioButton.setEnabled(bolLdraftEnabled);
		this.objGnormalJRadioButton.setEnabled(bolLnormalEnabled);
		this.objGhighJRadioButton.setEnabled(bolLhighEnabled);

		// Set selected value :
		PrintQuality objLprintQuality = (PrintQuality) this.objGprintJDialog.getAttributes().get(PrintQuality.class);
		if (objLprintQuality == null) {
			objLprintQuality = (PrintQuality) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(PrintQuality.class);
			if (objLprintQuality == null) {
				objLprintQuality = PrintQuality.NORMAL;
			}
		}

		if (objLprintQuality == PrintQuality.DRAFT) {
			this.objGdraftJRadioButton.setSelected(true);
		} else if (objLprintQuality == PrintQuality.NORMAL) {
			this.objGnormalJRadioButton.setSelected(true);
		} else {
			this.objGhighJRadioButton.setSelected(true);
		}
	}
}
