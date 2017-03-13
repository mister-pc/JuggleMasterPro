/*
 * @(#)StylesJComboBoxRenderer.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.style;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolTip;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.pattern.Siteswap;
import jugglemasterpro.pattern.Style;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class StylesJComboBoxRenderer extends JLabel implements ListCellRenderer<Style> {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public StylesJComboBoxRenderer(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

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
	@Override public Component getListCellRendererComponent(JList<? extends Style> objPjList,
															Style objPstyle,
															int intPindex,
															boolean bolPselected,
															boolean bolPfocused) {

		if (objPstyle == null) {
			return this;
		}
		final Font objLfont = objPjList.getFont();

		final int intLstyleLinesNumber = objPstyle.bytGstyleA.length / (2 * Constants.bytS_ENGINE_COORDONATES_NUMBER);
		final Siteswap objLsiteswap = this.objGcontrolJFrame.getJuggleMasterPro().objGsiteswap;
		final boolean bolLcurrentSiteswapCompatible =
														objLsiteswap.bytGstatus < 0
															|| ((intLstyleLinesNumber % objLsiteswap.intGrecurrentThrowsNumber == 0 || objLsiteswap.intGrecurrentThrowsNumber
																																		% intLstyleLinesNumber == 0) && (!objLsiteswap.bolGsynchro || intLstyleLinesNumber % 2 == 0));
		this.setEnabled(bolLcurrentSiteswapCompatible);

		this.setForeground(bolPselected ? Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR : objPjList.getForeground());
		this.setBackground(bolPselected ? objPjList.getSelectionBackground() : Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setText(objPstyle.strGstyle);
		this.setToolTipText(objPstyle.strGstyle);
		this.setHorizontalAlignment(SwingConstants.LEFT);
		this.setFont(objLfont);
		this.setOpaque(true);
		return this;
	}
}

/*
 * @(#)StylesJComboBoxRenderer.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
