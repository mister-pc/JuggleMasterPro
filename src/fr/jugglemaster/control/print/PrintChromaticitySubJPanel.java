package fr.jugglemaster.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.attribute.standard.Chromaticity;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class PrintChromaticitySubJPanel extends JPanel implements ActionListener {

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final PrintIconRadioJButton	objGcolorPrintIconJRadioButton;

	private final PrintIconRadioJButton	objGmonochromePrintIconJRadioButton;

	private final PrintJDialog			objGprintJDialog;

	public PrintChromaticitySubJPanel(PrintJDialog objPprintJDialog, ControlJFrame objPcontrolJFrame) {

		this.objGprintJDialog = objPprintJDialog;

		// Create widgets :
		final ButtonGroup objLbuttonGroup = new ButtonGroup();
		this.objGmonochromePrintIconJRadioButton =
													new PrintIconRadioJButton(	objPcontrolJFrame,
																				"radiobutton.monochrome",
																				Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JM],
																				true,
																				objLbuttonGroup,
																				this);
		this.objGcolorPrintIconJRadioButton =
												new PrintIconRadioJButton(	objPcontrolJFrame,
																			"radiobutton.color",
																			Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JMP],
																			false,
																			objLbuttonGroup,
																			this);

		// Add widgets :
		this.setOpaque(true);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.chromaticity"), this.objGprintJDialog	.getControlJFrame()
																																.getFont()));
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											GridBagConstraints.BOTH,
																											0.0F,
																											1.0F);

		this.add(this.objGmonochromePrintIconJRadioButton, objLextendedGridBagConstraints);
		this.add(this.objGcolorPrintIconJRadioButton, objLextendedGridBagConstraints);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		final Object objLsourceObject = objPactionEvent.getSource();

		if (this.objGmonochromePrintIconJRadioButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(Chromaticity.MONOCHROME);
		} else if (this.objGcolorPrintIconJRadioButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(Chromaticity.COLOR);
		}
	}

	final public void setValues() {

		boolean bolPmonochromeEnabled = false;
		boolean bolPcolorEnabled = false;

		// Set enabled values :
		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(Chromaticity.class)) {
			final Object objLvaluesObject =
											this.objGprintJDialog	.getPrintService()
																	.getSupportedAttributeValues(	Chromaticity.class,
																									null,
																									this.objGprintJDialog.getAttributes());

			if (objLvaluesObject instanceof Chromaticity[]) {
				for (final Chromaticity objLchromaticity : (Chromaticity[]) objLvaluesObject) {
					bolPmonochromeEnabled |= (objLchromaticity == Chromaticity.MONOCHROME);
					bolPcolorEnabled |= (objLchromaticity == Chromaticity.COLOR);
				}
			}
		}
		this.objGmonochromePrintIconJRadioButton.setEnabled(bolPmonochromeEnabled);
		this.objGcolorPrintIconJRadioButton.setEnabled(bolPcolorEnabled);

		// Set selected values :
		Chromaticity objLchromaticity = (Chromaticity) this.objGprintJDialog.getAttributes().get(Chromaticity.class);
		if (objLchromaticity == null) {
			objLchromaticity = (Chromaticity) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(Chromaticity.class);
			if (objLchromaticity == null) {
				objLchromaticity = Chromaticity.MONOCHROME;
			}
		}

		if (objLchromaticity == Chromaticity.MONOCHROME) {
			this.objGmonochromePrintIconJRadioButton.setSelected(true);
		} else {
			this.objGcolorPrintIconJRadioButton.setSelected(true);
		}
	}
}
