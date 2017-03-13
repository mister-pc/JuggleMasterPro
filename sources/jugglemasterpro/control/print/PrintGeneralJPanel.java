package jugglemasterpro.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.util.Constants;

final public class PrintGeneralJPanel extends JPanel {

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final PrintCopiesSubJPanel	objGprintCopiesSubJPanel;
	private final PrintRangeSubJPanel	objGprintRangeSubJPanel;
	private final PrintServiceSubJPanel	objGprintServiceSubJPanel;

	public PrintGeneralJPanel(PrintJDialog objPprintJDialog) {

		this.objGprintServiceSubJPanel = new PrintServiceSubJPanel(objPprintJDialog);
		this.objGprintRangeSubJPanel = new PrintRangeSubJPanel(objPprintJDialog);
		this.objGprintCopiesSubJPanel = new PrintCopiesSubJPanel(objPprintJDialog);

		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											0,
																											2,
																											1,
																											GridBagConstraints.CENTER,
																											6,
																											6,
																											6,
																											6,
																											GridBagConstraints.BOTH,
																											1.0F,
																											1.0F);
		this.add(this.objGprintServiceSubJPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(0, 1, 1, 1);
		this.add(this.objGprintRangeSubJPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, 1);
		this.add(this.objGprintCopiesSubJPanel, objLextendedGridBagConstraints);
	}

	final public void setValues() {
		this.objGprintServiceSubJPanel.setValues();
		this.objGprintRangeSubJPanel.setValues();
		this.objGprintCopiesSubJPanel.setValues();
	}
}
