/**
 * 
 */
package fr.jugglemaster.control.util;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import fr.jugglemaster.util.Constants;

/**
 * @author BeLO
 */
public class ExtendedJPanel extends JPanel {

	public ExtendedJPanel() {
		this(new GridBagLayout());
	}

	public ExtendedJPanel(LayoutManager objPlayoutManager) {
		super(objPlayoutManager);
		this.setOpaque(true);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
