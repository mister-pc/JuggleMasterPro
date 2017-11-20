package fr.jugglemaster.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class PrintOrientationSubJPanel extends JPanel implements ActionListener {

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final PrintIconRadioJButton	objGlandscapePrintIconRadioJButton;

	private final PrintIconRadioJButton	objGportraitPrintIconRadioJButton;

	private final PrintJDialog			objGprintJDialog;
	private final PrintMarginsSubJPanel	objGprintMarginsSubJPanel;
	private final PrintIconRadioJButton	objGreverseLandscapePrintIconRadioJButton;
	private final PrintIconRadioJButton	objGreversePortraitPrintIconRadioJButton;

	public PrintOrientationSubJPanel(PrintJDialog objPprintJDialog, ControlJFrame objPcontrolJFrame, PrintMarginsSubJPanel objPprintMarginsSubJPanel) {

		this.objGprintJDialog = objPprintJDialog;
		this.objGprintMarginsSubJPanel = objPprintMarginsSubJPanel;

		// Create widgets :
		final ButtonGroup objLbuttonGroup = new ButtonGroup();
		this.objGportraitPrintIconRadioJButton =
													new PrintIconRadioJButton(	objPcontrolJFrame,
																				"radiobutton.portrait",
																				Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PORTRAIT],
																				true,
																				objLbuttonGroup,
																				this);
		this.objGportraitPrintIconRadioJButton.addActionListener(this);

		this.objGlandscapePrintIconRadioJButton =
													new PrintIconRadioJButton(	objPcontrolJFrame,
																				"radiobutton.landscape",
																				Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_LANDSCAPE],
																				false,
																				objLbuttonGroup,
																				this);
		this.objGlandscapePrintIconRadioJButton.addActionListener(this);

		this.objGreversePortraitPrintIconRadioJButton =
														new PrintIconRadioJButton(	objPcontrolJFrame,
																					"radiobutton.revportrait",
																					Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_PORTRAIT],
																					false,
																					objLbuttonGroup,
																					this);
		this.objGreversePortraitPrintIconRadioJButton.addActionListener(this);

		this.objGreverseLandscapePrintIconRadioJButton =
															new PrintIconRadioJButton(	objPcontrolJFrame,
																						"radiobutton.revlandscape",
																						Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_LANDSCAPE],
																						false,
																						objLbuttonGroup,
																						this);
		this.objGreverseLandscapePrintIconRadioJButton.addActionListener(this);

		// Add widgets :
		this.setOpaque(true);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.orientation"), this.objGprintJDialog	.getControlJFrame()
																																.getFont()));
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											3,
																											3,
																											6,
																											6,
																											GridBagConstraints.BOTH,
																											0.0F,
																											1.0F);
		this.add(this.objGportraitPrintIconRadioJButton, objLextendedGridBagConstraints);
		this.add(this.objGlandscapePrintIconRadioJButton, objLextendedGridBagConstraints);
		this.add(this.objGreversePortraitPrintIconRadioJButton, objLextendedGridBagConstraints);
		this.add(this.objGreverseLandscapePrintIconRadioJButton, objLextendedGridBagConstraints);
	}

	final public void actionPerformed(ActionEvent objPactionEvent) {
		final Object objLsourceObject = objPactionEvent.getSource();

		if (this.objGportraitPrintIconRadioJButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(OrientationRequested.PORTRAIT);
		} else if (this.objGlandscapePrintIconRadioJButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(OrientationRequested.LANDSCAPE);
		} else if (this.objGreversePortraitPrintIconRadioJButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(OrientationRequested.REVERSE_PORTRAIT);
		} else if (this.objGreverseLandscapePrintIconRadioJButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(OrientationRequested.REVERSE_LANDSCAPE);
		}
		// orientation affects display of margins.
		if (this.objGprintMarginsSubJPanel != null) {
			this.objGprintMarginsSubJPanel.setValues();
		}
	}

	final public void setValues() {

		// Set selected values :
		boolean bolLportraitEnabled = false;
		boolean bolLlandscapeEnabled = false;
		boolean bolLreversePortraitEnabled = false;
		boolean bolLreverseLandscapeEnabled = false;

		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(OrientationRequested.class)) {
			final Object objLvaluesObject =
											this.objGprintJDialog	.getPrintService()
																	.getSupportedAttributeValues(	OrientationRequested.class,
																									null,
																									this.objGprintJDialog.getAttributes());

			if (objLvaluesObject instanceof OrientationRequested[]) {
				for (final OrientationRequested objLorientationRequested : (OrientationRequested[]) objLvaluesObject) {
					bolLportraitEnabled |= (objLorientationRequested == OrientationRequested.PORTRAIT);
					bolLlandscapeEnabled |= (objLorientationRequested == OrientationRequested.LANDSCAPE);
					bolLreversePortraitEnabled |= (objLorientationRequested == OrientationRequested.REVERSE_PORTRAIT);
					bolLreverseLandscapeEnabled |= (objLorientationRequested == OrientationRequested.REVERSE_LANDSCAPE);
				}
			}
		}
		this.objGportraitPrintIconRadioJButton.setEnabled(bolLportraitEnabled);
		this.objGlandscapePrintIconRadioJButton.setEnabled(bolLlandscapeEnabled);
		this.objGreversePortraitPrintIconRadioJButton.setEnabled(bolLreversePortraitEnabled);
		this.objGreverseLandscapePrintIconRadioJButton.setEnabled(bolLreverseLandscapeEnabled);

		// Set selected values :
		OrientationRequested objLorientationRequested = (OrientationRequested) this.objGprintJDialog.getAttributes().get(OrientationRequested.class);
		if (objLorientationRequested == null
			|| !this.objGprintJDialog.getPrintService().isAttributeValueSupported(	objLorientationRequested,
																					null,
																					this.objGprintJDialog.getAttributes())) {

			objLorientationRequested =
										(OrientationRequested) this.objGprintJDialog.getPrintService()
																					.getDefaultAttributeValue(OrientationRequested.class);
			if (!this.objGprintJDialog.getPrintService().isAttributeValueSupported(	objLorientationRequested,
																					null,
																					this.objGprintJDialog.getAttributes())) {
				objLorientationRequested = null;
				final Object objLvaluesObject =
												this.objGprintJDialog	.getPrintService()
																		.getSupportedAttributeValues(	OrientationRequested.class,
																										null,
																										this.objGprintJDialog.getAttributes());
				if (objLvaluesObject instanceof OrientationRequested[] && ((OrientationRequested[]) objLvaluesObject).length > 1) {
					objLorientationRequested = ((OrientationRequested[]) objLvaluesObject)[0];
				}
			}

			if (objLorientationRequested == null) {
				objLorientationRequested = OrientationRequested.PORTRAIT;
			}
			this.objGprintJDialog.getAttributes().add(objLorientationRequested);
		}

		if (objLorientationRequested == OrientationRequested.PORTRAIT) {
			this.objGportraitPrintIconRadioJButton.setSelected(true);
		} else if (objLorientationRequested == OrientationRequested.LANDSCAPE) {
			this.objGlandscapePrintIconRadioJButton.setSelected(true);
		} else if (objLorientationRequested == OrientationRequested.REVERSE_PORTRAIT) {
			this.objGreversePortraitPrintIconRadioJButton.setSelected(true);
		} else {
			this.objGreverseLandscapePrintIconRadioJButton.setSelected(true);
		}
	}
}
