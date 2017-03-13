package jugglemasterpro.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.attribute.standard.Sides;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class PrintSidesSubJPanel extends JPanel implements ActionListener {

	public PrintSidesSubJPanel(PrintJDialog objPprintJDialog, ControlJFrame objPcontrolJFrame) {

		this.objGprintJDialog = objPprintJDialog;

		// Create widgets :
		final ButtonGroup objLbuttonGroup = new ButtonGroup();
		this.objGoneSidePrintIconRadioJButton =
												new PrintIconRadioJButton(	objPcontrolJFrame,
																			"radiobutton.oneside",
																			Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_ONE_SIDE],
																			true,
																			objLbuttonGroup,
																			this);
		this.objGoneSidePrintIconRadioJButton.addActionListener(this);
		this.objGtumblePrintIconRadioJButton =
												new PrintIconRadioJButton(	objPcontrolJFrame,
																			"radiobutton.tumble",
																			Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_TUMBLE],
																			false,
																			objLbuttonGroup,
																			this);
		this.objGtumblePrintIconRadioJButton.addActionListener(this);
		this.objGduplexPrintIconRadioJButton =
												new PrintIconRadioJButton(	objPcontrolJFrame,
																			"radiobutton.duplex",
																			Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DUPLEX],
																			false,
																			objLbuttonGroup,
																			this);
		this.objGduplexPrintIconRadioJButton.addActionListener(this);

		this.setOpaque(true);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.sides"), this.objGprintJDialog.getControlJFrame().getFont()));
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

		this.add(this.objGoneSidePrintIconRadioJButton, objLextendedGridBagConstraints);
		this.add(this.objGtumblePrintIconRadioJButton, objLextendedGridBagConstraints);
		this.add(this.objGduplexPrintIconRadioJButton, objLextendedGridBagConstraints);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		final Object objLsourceObject = objPactionEvent.getSource();

		if (this.objGoneSidePrintIconRadioJButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(Sides.ONE_SIDED);
		} else if (this.objGtumblePrintIconRadioJButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(Sides.TUMBLE);
		} else if (this.objGduplexPrintIconRadioJButton.isSameAs(objLsourceObject)) {
			this.objGprintJDialog.getAttributes().add(Sides.DUPLEX);
		}
	}

	final public void setValues() {

		Tools.debug("PrintSidesSubJPanel.setValues()");
		// Set enabled values :
		boolean bolLoneSideEnabled = false;
		boolean bolLtumbleEnabled = false;
		boolean bolLduplexEnabled = false;

		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(Sides.class)) {
			final Object objLvaluesObject =
											this.objGprintJDialog	.getPrintService()
																	.getSupportedAttributeValues(	Sides.class,
																									null,
																									this.objGprintJDialog.getAttributes());

			if (objLvaluesObject instanceof Sides[]) {
				for (final Sides objLsides : (Sides[]) objLvaluesObject) {
					bolLoneSideEnabled |= (objLsides == Sides.ONE_SIDED);
					bolLtumbleEnabled |= (objLsides == Sides.TUMBLE);
					bolLduplexEnabled |= (objLsides == Sides.DUPLEX);
				}
			}
		}
		this.objGoneSidePrintIconRadioJButton.setEnabled(bolLoneSideEnabled);
		this.objGtumblePrintIconRadioJButton.setEnabled(bolLtumbleEnabled);
		this.objGduplexPrintIconRadioJButton.setEnabled(bolLduplexEnabled);

		// Set selected values :
		Sides objLsides = (Sides) this.objGprintJDialog.getAttributes().get(Sides.class);
		if (objLsides == null) {
			objLsides = (Sides) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(Sides.class);
			if (objLsides == null) {
				objLsides = Sides.ONE_SIDED;
			}
		}

		if (objLsides == Sides.ONE_SIDED) {
			this.objGoneSidePrintIconRadioJButton.setSelected(true);
		} else if (objLsides == Sides.TUMBLE) {
			this.objGtumblePrintIconRadioJButton.setSelected(true);
		} else {
			this.objGduplexPrintIconRadioJButton.setSelected(true);
		}
	}

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final PrintIconRadioJButton	objGduplexPrintIconRadioJButton;

	private final PrintIconRadioJButton	objGoneSidePrintIconRadioJButton;

	private final PrintJDialog			objGprintJDialog;

	private final PrintIconRadioJButton	objGtumblePrintIconRadioJButton;
}
