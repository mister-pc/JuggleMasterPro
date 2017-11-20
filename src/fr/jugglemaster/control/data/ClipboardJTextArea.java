/*
 * @(#)ClipboardJTextArea.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.data;

import java.awt.Font;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ClipboardJTextArea extends JTextArea implements InputMethodListener, KeyListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ClipboardJTextArea(ControlJFrame objPcontrolJFrame) {
		super(Strings.strS_EMPTY, 1, 1);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.setOpaque(true);
		this.setFont(new Font("Courier", Font.PLAIN, 11));
		this.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.addKeyListener(this);
		this.addInputMethodListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPinputMethodEvent
	 */
	@Override final public void caretPositionChanged(InputMethodEvent objPinputMethodEvent) {
		this.objGcontrolJFrame.objGclipboardJDialog.setClipboardEnabled();
	}

	final public void doCopy() {

		final String strLtext = this.getText();
		final boolean bolLnotEmpty = !strLtext.equals(Strings.strS_EMPTY);
		if (bolLnotEmpty && this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
			try {
				Constants.objS_GRAPHICS_TOOLKIT.getSystemClipboard().setContents(new StringSelection(strLtext), null);
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while copying program console into clipboard");
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPinputMethodEvent
	 */
	@Override final public void inputMethodTextChanged(InputMethodEvent objPinputMethodEvent) {
		this.objGcontrolJFrame.objGclipboardJDialog.setClipboardEnabled();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyPressed(KeyEvent objPkeyEvent) {

		final KeyStroke objLkeyStroke = KeyStroke.getKeyStrokeForEvent(objPkeyEvent);

		if (objLkeyStroke.equals(Constants.keyS_OPEN)) {
			if (this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
				this.objGcontrolJFrame.objGopenFileJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_NEW_PATTERNS_FILE)) {
			this.objGcontrolJFrame.objGnewPatternsFileJMenuItem.actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_NEW_PATTERN)) {

			this.objGcontrolJFrame.objGnewPatternJMenuItem.actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_EXPORT_PATTERNS)) {
			if (this.objGcontrolJFrame.objGexportSiteswapsFileJMenuItem.isEnabled()) {
				this.objGcontrolJFrame.objGexportSiteswapsFileJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_SIMPLE_COPY)) {
			(new ClipboardJButton(this.objGcontrolJFrame, Strings.strS_EMPTY, false, false)).actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_DETAILED_COPY)) {
			(new ClipboardJButton(this.objGcontrolJFrame, Strings.strS_EMPTY, true, false)).actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_FREE_CLIPBOARD)) {
			(new ClipboardJButton(this.objGcontrolJFrame, Strings.strS_EMPTY, false, true)).actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_QUIT)) {
			this.objGcontrolJFrame.objGclipboardJDialog.setVisible(false);
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.selectState(false);
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.setEnabled(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																				|| !this.objGcontrolJFrame.objGclipboardJDialog.objGclipboardJTextArea	.getText()
																																						.equals(Strings.strS_EMPTY));
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_DISPLAY_CLIPBOARD)) {
			this.objGcontrolJFrame.objGclipboardJDialog.setVisible(!this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.getState());
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.selectState(!this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.getState());
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.setEnabled(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																				|| !this.objGcontrolJFrame.objGclipboardJDialog.objGclipboardJTextArea	.getText()
																																						.equals(Strings.strS_EMPTY));
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_HELP)) {
			this.objGcontrolJFrame.objGhelpJMenuItem.getActionListeners()[0].actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_ABOUT)) {
			this.objGcontrolJFrame.objGaboutJMenuItem.actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_REVERSE_ALL)) {
			this.objGcontrolJFrame	.getJuggleMasterPro()
									.setControlSelected(Constants.bytS_BOOLEAN_LOCAL_MIRROR,
														!this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_MIRROR));
			this.objGcontrolJFrame	.getJuggleMasterPro()
									.setControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP,
														!this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP));
			this.objGcontrolJFrame	.getJuggleMasterPro()
									.setControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE,
														!this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE));
			this.objGcontrolJFrame.objGreversePatternJButton.doSwitchReversing();
			Tools.debug("ClipboardJTextArea.keyPressed(): ControlJFrame.doRestartJuggling()");
			this.objGcontrolJFrame.doRestartJuggling();
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_RELOAD_PATTERN)) {
			this.objGcontrolJFrame.objGreloadPatternJButton.actionPerformed(null);
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_RELOAD_FILE)) {
			if (this.objGcontrolJFrame.objGreloadFileJMenuItem.isEnabled()) {
				this.objGcontrolJFrame.objGreloadFileJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_FIND)) {
			if (this.objGcontrolJFrame.objGfindJMenuItem.isEnabled()) {
				this.objGcontrolJFrame.objGfindJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_FIND_PREVIOUS)) {
			if (this.objGcontrolJFrame.objGfindPreviousJMenuItem.isEnabled()) {
				this.objGcontrolJFrame.objGfindPreviousJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_FIND_NEXT)) {
			if (this.objGcontrolJFrame.objGfindNextJMenuItem.isEnabled()) {
				this.objGcontrolJFrame.objGfindNextJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_PRINT)) {
			if (this.objGcontrolJFrame.objGprintJMenuItem.isEnabled()) {
				this.objGcontrolJFrame.objGprintJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}

		if (objLkeyStroke.equals(Constants.keyS_PREFERENCES)) {
			if (this.objGcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
				this.objGcontrolJFrame.objGpreferencesJMenuItem.actionPerformed(null);
			}
			objPkeyEvent.consume();
			return;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyReleased(KeyEvent objPkeyEvent) {
		this.objGcontrolJFrame.objGclipboardJDialog.setClipboardEnabled();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyTyped(KeyEvent objPkeyEvent) {
		this.objGcontrolJFrame.objGclipboardJDialog.setClipboardEnabled();
	}

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ClipboardJTextArea.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
