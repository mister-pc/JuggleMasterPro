package jugglemasterpro.engine.component;

import javax.swing.JButton;

import jugglemasterpro.util.Constants;

final public class NoArrowJScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {

	final private static NoArrowJScrollBarUI	objS_NO_ARROW_J_SCROLL_BAR_UI	= new NoArrowJScrollBarUI();
	@SuppressWarnings("unused")
	final private static long					serialVersionUID				= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private static JButton getEmptyJButton() {
		final JButton objLjButton = new JButton();
		objLjButton.setSize(0, 0);
		return objLjButton;
	}

	final public static NoArrowJScrollBarUI getUI() {
		return NoArrowJScrollBarUI.objS_NO_ARROW_J_SCROLL_BAR_UI;
	}

	protected NoArrowJScrollBarUI() {}

	@Override protected JButton createDecreaseButton(int intPorientation) {
		return NoArrowJScrollBarUI.getEmptyJButton();
	}

	@Override protected JButton createIncreaseButton(int intPorientation) {
		return NoArrowJScrollBarUI.getEmptyJButton();
	}
}
