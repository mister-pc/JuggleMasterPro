package fr.jugglemaster.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.util.Constants;

final public class PrintPageSetupJPanel extends JPanel {

	final private static long				serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final PrintMarginsSubJPanel		objGprintMarginsSubJPanel;
	private final PrintMediaSubJPanel		objGprintMediaSubJPanel;
	private final PrintOrientationSubJPanel	objGprintOrientationSubJPanel;

	public PrintPageSetupJPanel(PrintJDialog objPprintJDialog, ControlJFrame objPcontrolJFrame) {

		// Create widgets :
		this.objGprintMarginsSubJPanel = new PrintMarginsSubJPanel(objPprintJDialog);
		this.objGprintMediaSubJPanel = new PrintMediaSubJPanel(objPprintJDialog, this.objGprintMarginsSubJPanel);
		this.objGprintOrientationSubJPanel = new PrintOrientationSubJPanel(objPprintJDialog, objPcontrolJFrame, this.objGprintMarginsSubJPanel);

		// Add widgets :
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

		this.add(this.objGprintMediaSubJPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(GridBagConstraints.RELATIVE, 1, 1, 1);
		this.add(this.objGprintOrientationSubJPanel, objLextendedGridBagConstraints);
		this.add(this.objGprintMarginsSubJPanel, objLextendedGridBagConstraints);
	}

	final public void setValues() {
		this.objGprintMediaSubJPanel.setValues();
		this.objGprintOrientationSubJPanel.setValues();
		this.objGprintMarginsSubJPanel.setValues();
	}

}
