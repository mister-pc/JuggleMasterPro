/*
 * @(#)SiteswapJTextPane.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.siteswap;

import java.awt.Insets;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
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
final public class SiteswapJTextPane extends JTextField implements DocumentListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public SiteswapJTextPane(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.lngIlastUpdateTime = System.currentTimeMillis();

		// this.setEditorKit(new NoWrapStyledEditorKit());
		// this.setStyledDocument(new SiteswapStyledDocument());
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.getDocument().addDocumentListener(this);
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		this.getActionMap().put(KeyEvent.VK_ENTER, new KeysAction(objPcontrolJFrame, KeysAction.bytS_SITESWAP_J_TEXT_PANE));
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, true, false, this.getTransferHandler()));
		// this.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_SITESWAP));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPdocumentEvent
	 */
	@Override final public void changedUpdate(DocumentEvent objPdocumentEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final protected Document createDefaultModel() {
		return new SiteswapPlainDocument();
	}

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

		final long lngLcurrentTime = System.currentTimeMillis();
		if (lngLcurrentTime - this.lngIlastUpdateTime > 500L) {
			SiteswapActions.doApplySiteswap(this.objGcontrolJFrame, this.getText());
		}
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
	final public void selectText(String strP) {
		this.selectText(strP, 0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 * @param intPcaretPosition
	 */
	final public void selectText(String strP, int intPcaretPosition) {
		if (!strP.equals(this.getText())) {
			this.getDocument().removeDocumentListener(this);
			this.lngIlastUpdateTime = System.currentTimeMillis();
			this.setText(strP);
			if (intPcaretPosition >= 0 && intPcaretPosition < strP.length()) {
				this.setCaretPosition(intPcaretPosition);
			}
			this.setToolTipText();
			this.getDocument().addDocumentListener(this);
		}
	}

	public void setToolTipText() {

		final String strLsiteswap = this.objGcontrolJFrame.getJuggleMasterPro().objGsiteswap.strGsiteswap;
		if (Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS)
			&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
			final Insets objLinsets = this.getInsets();
			if (!Tools.isEmpty(strLsiteswap)
				&& this.getFontMetrics(this.getFont()).stringWidth(strLsiteswap) > this.getWidth() - (objLinsets.left + objLinsets.right)) {
				super.setToolTipText(strLsiteswap);
			} else {
				super.setToolTipText(this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_SITESWAP));
			}
		} else {
			super.setToolTipText(null);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 */
	@Override final public void setToolTipText(String strP) {}

	private long				lngIlastUpdateTime;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)SiteswapJTextPane.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
