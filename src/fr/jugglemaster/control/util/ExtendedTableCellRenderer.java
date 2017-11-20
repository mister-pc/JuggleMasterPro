package fr.jugglemaster.control.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import fr.jugglemaster.util.Constants;

final public class ExtendedTableCellRenderer extends DefaultTableCellRenderer {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public ExtendedTableCellRenderer() {
		this(Color.BLACK, Color.WHITE);
	}

	public ExtendedTableCellRenderer(Color objPbackgroundColor) {
		this(Color.BLACK, objPbackgroundColor);
	}

	public ExtendedTableCellRenderer(Color objPforegroundColor, Color objPbackgroundColor) {
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setFont(new Font("Courier", Font.PLAIN, 10));
		this.setForeground(objPforegroundColor);
		this.setBackground(objPbackgroundColor);
	}
}
