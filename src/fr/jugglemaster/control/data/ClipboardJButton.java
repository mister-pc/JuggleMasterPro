/*
 * @(#)ClipboardJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ClipboardJButton extends ExtendedJButton implements ActionListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean		bolGclose;

	final private boolean		bolGdetailedCopy;

	final private boolean		bolGfreeClipboard;

	final private boolean		bolGsystemCopy;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bolPdetailedCopy
	 * @param bolPfreeClipboard
	 */
	public ClipboardJButton(ControlJFrame objPcontrolJFrame, boolean bolPdetailedCopy) {
		this(objPcontrolJFrame, Strings.strS_EMPTY, bolPdetailedCopy, false, false);
	}

	public ClipboardJButton(ControlJFrame objPcontrolJFrame, boolean bolPdetailedCopy, boolean bolPfreeClipboard) {
		this(objPcontrolJFrame, Strings.strS_EMPTY, bolPdetailedCopy, bolPfreeClipboard, false);
	}

	public ClipboardJButton(ControlJFrame objPcontrolJFrame, boolean bolPdetailedCopy, boolean bolPfreeClipboard, boolean bolPsystemCopy) {
		this(objPcontrolJFrame, Strings.strS_EMPTY, bolPdetailedCopy, bolPfreeClipboard, bolPsystemCopy, false);
	}

	public ClipboardJButton(ControlJFrame objPcontrolJFrame,
							boolean bolPdetailedCopy,
							boolean bolPfreeClipboard,
							boolean bolPsystemCopy,
							boolean bolPclose) {
		this(objPcontrolJFrame, Strings.strS_EMPTY, bolPdetailedCopy, bolPfreeClipboard, bolPsystemCopy, bolPclose);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPlabel
	 */
	public ClipboardJButton(ControlJFrame objPcontrolJFrame, String strPlabel) {
		this(objPcontrolJFrame, strPlabel, false, false, true);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPlabel
	 * @param bolPdetailedCopy
	 * @param bolPfreeClipboard
	 */
	public ClipboardJButton(ControlJFrame objPcontrolJFrame, String strPlabel, boolean bolPdetailedCopy) {
		this(objPcontrolJFrame, strPlabel, bolPdetailedCopy, false, false, false);
	}

	public ClipboardJButton(ControlJFrame objPcontrolJFrame, String strPlabel, boolean bolPdetailedCopy, boolean bolPfreeClipboard) {
		this(objPcontrolJFrame, strPlabel, bolPdetailedCopy, bolPfreeClipboard, false, false);
	}

	public ClipboardJButton(ControlJFrame objPcontrolJFrame,
							String strPlabel,
							boolean bolPdetailedCopy,
							boolean bolPfreeClipboard,
							boolean bolPsystemCopy) {
		this(objPcontrolJFrame, strPlabel, bolPdetailedCopy, bolPfreeClipboard, bolPsystemCopy, false);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPlabel
	 * @param bolPdetailedCopy
	 * @param bolPfreeClipboard
	 * @param bolPsystemCopy
	 */
	public ClipboardJButton(ControlJFrame objPcontrolJFrame,
							String strPlabel,
							boolean bolPdetailedCopy,
							boolean bolPfreeClipboard,
							boolean bolPsystemCopy,
							boolean bolPclose) {

		super(objPcontrolJFrame, strPlabel);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGdetailedCopy = bolPdetailedCopy;
		this.bolGfreeClipboard = bolPfreeClipboard;
		this.bolGsystemCopy = bolPsystemCopy;
		this.bolGclose = bolPclose;

		this.setEnabled(!bolPfreeClipboard && !bolPsystemCopy);
		this.addActionListener(this);
		this.setToolTipText();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("ClipboardJButton.actionPerformed()");
		// Copy the clipboard area to the system clipboard :
		if (this.bolGsystemCopy) {
			this.objGcontrolJFrame.objGclipboardJDialog.objGclipboardJTextArea.doCopy();
			return;
		}

		// Close the clipboard window :
		if (this.bolGclose) {
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.selectState(false);
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.setEnabled(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																				|| !this.objGcontrolJFrame.objGclipboardJDialog.isEmpty());

			this.objGcontrolJFrame.objGclipboardJDialog.setVisible(false);
			return;
		}
		// Bring dialog to front :
		if (this.objGcontrolJFrame.objGclipboardJDialog.isVisible()) {
			this.objGcontrolJFrame.objGclipboardJDialog.requestFocus();
			this.objGcontrolJFrame.objGclipboardJDialog.objGclipboardJTextArea.requestFocusInWindow();
		}

		// Free clipboard :
		if (this.bolGfreeClipboard) {
			this.objGcontrolJFrame.objGclipboardJDialog.objGclipboardJTextArea.setText(Strings.strS_EMPTY);
		} else {

			// Return if no data copy rights :
			if (!this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
				|| this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus != Constants.bytS_STATE_ANIMATION_PAUSED
				&& this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus != Constants.bytS_STATE_ANIMATION_PAUSING
				&& this.objGcontrolJFrame.getJuggleMasterPro().bytGanimationStatus != Constants.bytS_STATE_ANIMATION_JUGGLING) {
				return;
			}

			// Copy pattern at the end of the clipboard :
			final String strLfullPattern =
											Strings.doConcat(	this.objGcontrolJFrame	.getLanguage()
																						.getTranslatedTokensString(	this.objGcontrolJFrame	.getPatternsManager()
																																			.toPatternString(	this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex,
																																								Constants.bytS_EXTENSION_JMP,
																																								this.bolGdetailedCopy,
																																								this.objGcontrolJFrame.getLanguage()),
																													this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType),
																Strings.strS_LINE_SEPARATOR);

			// ClipboardJDialog.this.objGclipboardJTextArea.setText(JuggleTools.doConcat(objGclipboardJTextArea.getText(),
			// strLfullPattern));
			this.objGcontrolJFrame.objGclipboardJDialog.objGclipboardJTextArea.insert(	strLfullPattern,
																						this.objGcontrolJFrame.objGclipboardJDialog.objGclipboardJTextArea.getCaretPosition());
		}

		this.objGcontrolJFrame.objGclipboardJDialog.setClipboardEnabled();
	}

	final public void doLoadImages() {

		int intLiconFile =
							this.bolGclose
											? Constants.intS_FILE_ICON_CLOSE_CLIPBOARD_BW
											: this.bolGsystemCopy
																	? Constants.intS_FILE_ICON_COPY_CLIPBOARD_BW
																	: this.bolGfreeClipboard
																							? Constants.intS_FILE_ICON_FREE_CLIPBOARD_BW
																							: this.bolGdetailedCopy
																													? Constants.intS_FILE_ICON_DETAILED_COPY_BW
																													: Constants.intS_FILE_ICON_SIMPLE_COPY_BW;
		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFile, 2);

		intLiconFile =
						this.bolGclose
										? Constants.intS_FILE_ICON_CLOSE_CLIPBOARD
										: this.bolGsystemCopy
																? Constants.intS_FILE_ICON_COPY_CLIPBOARD
																: this.bolGfreeClipboard
																						? Constants.intS_FILE_ICON_FREE_CLIPBOARD
																						: this.bolGdetailedCopy
																												? Constants.intS_FILE_ICON_DETAILED_COPY
																												: Constants.intS_FILE_ICON_SIMPLE_COPY;
		final ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFile, 2);
		Tools.setIcons(this, icoL, icoLrollOver);
	}

	final public void setToolTipText() {
		int intLtooltip = Constants.bytS_UNCLASS_NO_VALUE;
		if (this.bolGclose) {
			intLtooltip = Language.intS_TOOLTIP_CLOSE_CLIPBOARD;
		} else if (this.bolGsystemCopy) {
			intLtooltip = Language.intS_TOOLTIP_CLIPBOARD_COPY;
		} else if (this.bolGfreeClipboard) {
			intLtooltip = Language.intS_TOOLTIP_FREE_CLIPBOARD;
		} else if (this.bolGdetailedCopy) {
			intLtooltip = Language.intS_TOOLTIP_DETAILED_COPY;
		} else {
			intLtooltip = Language.intS_TOOLTIP_SIMPLE_COPY;
		}

		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																													? this.objGcontrolJFrame.getLanguageString(intLtooltip)
																													: null);

	}
}

/*
 * @(#)ClipboardJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
