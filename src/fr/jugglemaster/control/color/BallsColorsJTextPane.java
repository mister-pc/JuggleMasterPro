/*
 * @(#)ColorsJTextPane.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.color;

import java.awt.Insets;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
import fr.jugglemaster.control.util.KeysAction;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class BallsColorsJTextPane extends JTextPane implements /* DocumentListener, */CaretListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public BallsColorsJTextPane(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setFont(this.getControlJFrame().getFont());
		this.setOpaque(true);
		this.setEditorKit(new ColorsJTextPaneNoWrapStyledEditorKit());
		this.objGcolorsStyledDocument = new ColorsStyledDocument(this);
		this.setStyledDocument(this.objGcolorsStyledDocument);
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		this.getActionMap().put(KeyEvent.VK_ENTER, new KeysAction(objPcontrolJFrame, KeysAction.bytS_COLORS_J_TEXT_PANE));
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), KeyEvent.VK_TAB);
		this.getActionMap().put(KeyEvent.VK_TAB, new KeysAction(objPcontrolJFrame, KeysAction.bytS_COLORS_J_TEXT_PANE, KeyEvent.VK_TAB));
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, true, true, this.getTransferHandler()));
		this.addCaretListener(this);
	}

	@Override final public void caretUpdate(CaretEvent objPcaretEvent) {
		try {
			// TODO : c'est bon, mais ça ne déplace rien... Peut-être un scroll à rajouter ?
			this.scrollRectToVisible(this.modelToView(this.getCaretPosition()));
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while scrolling ball color field visible part");
		}
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	final public ColorsStyledDocument getColorsStyledDocument() {
		return this.objGcolorsStyledDocument;
	}

	public ControlJFrame getControlJFrame() {
		return this.objGcontrolJFrame;
	}

	@Override final public boolean getScrollableTracksViewportHeight() {
		return true;
	}

	@Override final public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override final public void setEditable(boolean bolPeditable) {
		boolean bolLchanged = false;
		while (!bolLchanged) {
			try {
				super.setEditable(true);
				bolLchanged = true;
			} catch (final Throwable objPthrowable) {
				bolLchanged = false;
				Tools.sleep(0);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 */
	final public void setToolTipText() {

		final String strLtooltipText = this.objGcontrolJFrame.getControlString(Constants.bytS_STRING_LOCAL_COLORS);
		final Insets objLinsets = this.getInsets();
		if (Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS)
			&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
			Tools.debug("BallsColorsJTextPane.setToolTipText()");
			Tools.out("Largeur composant : ", this.getWidth() - (objLinsets.left + objLinsets.right));
			Tools.out("Texte (", this.getFontMetrics(this.getFont()).stringWidth(strLtooltipText), ") = ", strLtooltipText);
			if (!Tools.isEmpty(strLtooltipText)
				&& this.getFontMetrics(this.getFont()).stringWidth(strLtooltipText) > this.getWidth() - (objLinsets.left + objLinsets.right)) {
				super.setToolTipText(strLtooltipText);
			} else {
				super.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_COLORS));
			}
		} else {
			super.setToolTipText(null);
		}
	}

	final private ColorsStyledDocument	objGcolorsStyledDocument;

	public final ControlJFrame			objGcontrolJFrame;

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ColorsJTextPane.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
