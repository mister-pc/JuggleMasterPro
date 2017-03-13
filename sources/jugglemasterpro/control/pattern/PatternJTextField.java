/*
 * @(#)PatternJTextField.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pattern;

import java.awt.Insets;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.io.ExtendedTransferHandler;
import jugglemasterpro.control.util.KeysAction;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PatternJTextField extends JTextField implements DocumentListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public PatternJTextField(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.getDocument().addDocumentListener(this);
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		this.getActionMap().put(KeyEvent.VK_ENTER, new KeysAction(objPcontrolJFrame, KeysAction.bytS_PATTERN_J_TEXT_FIELD));
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, true, true, this.getTransferHandler()));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPdocumentEvent
	 */
	@Override final public void changedUpdate(DocumentEvent objPdocumentEvent) {}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPdocumentEvent
	 */
	@Override final public void insertUpdate(DocumentEvent objPdocumentEvent) {
		final String strLtext = this.getText().trim();
		this.objGcontrolJFrame.saveControlString(Constants.bytS_STRING_LOCAL_PATTERN, strLtext);
		this.setToolTipText(strLtext);
		this.objGcontrolJFrame.setPatternControls();
		this.objGcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPdocumentEvent
	 */
	@Override final public void removeUpdate(DocumentEvent objPdocumentEvent) {
		this.insertUpdate(objPdocumentEvent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 */
	final public void setTextString(String strP) {
		this.setTextString(strP, 0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @param intPcaretPosition
	 */
	final public void setTextString(String strP, int intPcaretPosition) {
		this.getDocument().removeDocumentListener(this);
		super.setText(strP);
		this.setToolTipText(strP);
		if (intPcaretPosition >= 0 && intPcaretPosition < strP.length()) {
			this.setCaretPosition(intPcaretPosition);
		}
		this.getDocument().addDocumentListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 */
	@Override final public void setToolTipText(String strPtext) {
		final Insets objLinsets = this.getInsets();
		if (Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS)
			&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
			if (!Tools.isEmpty(strPtext)
				&& this.getFontMetrics(this.getFont()).stringWidth(strPtext) > this.getWidth() - (objLinsets.left + objLinsets.right)) {
				super.setToolTipText(strPtext);
			} else {
				super.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_PATTERN_NAME));
			}
		} else {
			super.setToolTipText(null);
		}
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)PatternJTextField.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
