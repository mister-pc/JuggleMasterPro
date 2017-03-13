package jugglemasterpro.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.util.Constants;

final public class PrintAppearanceJPanel extends JPanel {

	final private static long					serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final PrintChromaticitySubJPanel	objGprintChromaticitySubJPanel;
	private final PrintJobAttributesSubJPanel	objGprintJobAttributesSubJPanel;
	private final PrintQualitySubJPanel			objGprintQualitySubJPanel;
	private final PrintSidesSubJPanel			objGprintSidesSubJPanel;

	public PrintAppearanceJPanel(PrintJDialog objPprintJDialog, ControlJFrame objPcontrolJFrame) {

		this.objGprintChromaticitySubJPanel = new PrintChromaticitySubJPanel(objPprintJDialog, objPcontrolJFrame);
		this.objGprintQualitySubJPanel = new PrintQualitySubJPanel(objPprintJDialog);
		this.objGprintSidesSubJPanel = new PrintSidesSubJPanel(objPprintJDialog, objPcontrolJFrame);
		this.objGprintJobAttributesSubJPanel = new PrintJobAttributesSubJPanel(objPprintJDialog);

		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																											0,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											6,
																											6,
																											6,
																											6,
																											GridBagConstraints.BOTH,
																											1.0F,
																											1.0F);

		this.add(this.objGprintChromaticitySubJPanel, objLextendedGridBagConstraints);
		this.add(this.objGprintQualitySubJPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(GridBagConstraints.RELATIVE, 1);
		this.add(this.objGprintSidesSubJPanel, objLextendedGridBagConstraints);
		this.add(this.objGprintJobAttributesSubJPanel, objLextendedGridBagConstraints);
	}

	final public void setValues() {
		this.objGprintChromaticitySubJPanel.setValues();
		this.objGprintQualitySubJPanel.setValues();
		this.objGprintSidesSubJPanel.setValues();
		this.objGprintJobAttributesSubJPanel.setValues();
	}
}
