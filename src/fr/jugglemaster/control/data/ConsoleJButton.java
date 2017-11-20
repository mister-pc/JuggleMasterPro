/*
 * @(#)ConsoleJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.data;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ConsoleJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param bolPcopy
	 * @param bolPclose
	 */
	public ConsoleJButton(ControlJFrame objPcontrolJFrame, boolean bolPcopy, boolean bolPclose) {

		super(objPcontrolJFrame);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGcopy = bolPcopy;
		this.bolGclose = bolPclose;
		this.addActionListener(this);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("ConsoleJButton.actionPerformed()");

		// Close dialog :
		if (this.bolGclose) {
			this.objGcontrolJFrame.objGconsoleJDialog.setVisible(false);
			this.objGcontrolJFrame.objGconsoleJCheckBoxMenuItem.selectState(false);
			return;
		}

		// Bring dialog to front :
		if (this.objGcontrolJFrame.objGconsoleJDialog.isVisible()) {
			this.objGcontrolJFrame.objGconsoleJDialog.requestFocus();
			this.objGcontrolJFrame.objGconsoleJDialog.objGconsoleJTextArea.requestFocusInWindow();
		}

		// Copy console :
		if (this.bolGcopy) {
			try {
				Constants.objS_GRAPHICS_TOOLKIT	.getSystemClipboard()
												.setContents(	new StringSelection(this.objGcontrolJFrame.objGconsoleJDialog.objGconsoleJTextArea.getText()),
																null);
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while copying program console into clipboard");
			}
		} else {
			// Free :
			this.objGcontrolJFrame.objGconsoleJDialog.objGconsoleJTextArea.setText(Strings.strS_EMPTY);
			this.objGcontrolJFrame.objGconsoleJDialog.objGfreeConsoleJButton.setEnabled(false);
			this.objGcontrolJFrame.objGconsoleJDialog.objGcopyConsoleJButton.setEnabled(false);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	final public void doLoadImages() {

		int intLiconFileType =
								this.bolGcopy ? Constants.intS_FILE_ICON_COPY_CONSOLE_BW
												: this.bolGclose ? Constants.intS_FILE_ICON_CLOSE_CONSOLE_BW
																: Constants.intS_FILE_ICON_FREE_CONSOLE_BW;
		final ImageIcon icoL = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFileType, 2);

		intLiconFileType =
							this.bolGcopy ? Constants.intS_FILE_ICON_COPY_CONSOLE : this.bolGclose ? Constants.intS_FILE_ICON_CLOSE_CONSOLE
																									: Constants.intS_FILE_ICON_FREE_CONSOLE;
		final ImageIcon icoLrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFileType, 2);

		Tools.setIcons(this, icoL, icoLrollOver);
	}

	final private boolean		bolGclose;

	final private boolean		bolGcopy;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ConsoleJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
