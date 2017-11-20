/*
 * @(#)DefaultRenderer.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.util;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import fr.jugglemaster.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class DefaultRenderer extends JLabel implements ListCellRenderer<Object> {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPjList
	 * @param objPobject
	 * @param intPindex
	 * @param bolPselected
	 * @param bolPfocused
	 * @return
	 */
	@Override public Component getListCellRendererComponent(JList<? extends Object> objPjList,
															Object objPobject,
															int intPindex,
															boolean bolPselected,
															boolean bolPfocused) {

		this.setForeground(bolPselected ? Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR : objPjList.getForeground());
		this.setBackground(bolPselected ? objPjList.getSelectionBackground() : Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setText(objPobject != null ? objPobject.toString() : null);
		this.setHorizontalAlignment(SwingConstants.LEFT);
		this.setFont(objPjList.getFont());
		this.setOpaque(true);
		return this;
	}
}

/*
 * @(#)DefaultRenderer.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
