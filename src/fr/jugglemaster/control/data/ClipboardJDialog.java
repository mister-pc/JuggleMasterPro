/*
 * @(#)ClipboardJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.data;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.window.JDialogWindowListener;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ClipboardJDialog extends JDialog {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	public ClipboardJTextArea	objGclipboardJTextArea;

	private ClipboardJButton	objGcloseClipboardJButton;

	final private ControlJFrame	objGcontrolJFrame;

	public ClipboardJButton		objGcopyClipboardJButton;

	public ClipboardJButton		objGdetailedCopyJButton;

	public ClipboardJButton		objGfreeClipboardJButton;

	public ClipboardJButton		objGsimpleCopyJButton;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ClipboardJDialog(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, Strings.strS_EMPTY, false);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this, false));

		this.doCreateWidgets();
		this.doAddWidgets();
	}

	final private void doAddWidgets() {
		this.setLayout(new GridBagLayout());
		final JScrollPane objLjScrollPane =
											new JScrollPane(this.objGclipboardJTextArea,
															ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
															ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		objLjScrollPane.setOpaque(true);
		this.add(objLjScrollPane, new ExtendedGridBagConstraints(	0,
																	0,
																	1,
																	1,
																	GridBagConstraints.CENTER,
																	5,
																	0,
																	5,
																	5,
																	GridBagConstraints.BOTH,
																	1.0F,
																	1.0F));

		final JPanel objLbuttonsJPanel = new JPanel();
		objLbuttonsJPanel.setLayout(new BoxLayout(objLbuttonsJPanel, BoxLayout.LINE_AXIS));
		objLbuttonsJPanel.setOpaque(true);
		objLbuttonsJPanel.add(Box.createHorizontalGlue());
		objLbuttonsJPanel.add(this.objGsimpleCopyJButton);
		objLbuttonsJPanel.add(this.objGdetailedCopyJButton);
		objLbuttonsJPanel.add(Box.createHorizontalGlue());
		objLbuttonsJPanel.add(this.objGcopyClipboardJButton);
		objLbuttonsJPanel.add(this.objGfreeClipboardJButton);
		objLbuttonsJPanel.add(Box.createHorizontalGlue());
		objLbuttonsJPanel.add(this.objGcloseClipboardJButton);
		objLbuttonsJPanel.add(Box.createHorizontalGlue());
		this.add(objLbuttonsJPanel, new ExtendedGridBagConstraints(	0,
																	1,
																	1,
																	1,
																	GridBagConstraints.CENTER,
																	10,
																	10,
																	10,
																	10,
																	GridBagConstraints.HORIZONTAL,
																	1.0F,
																	0.0F));
	}

	final private void doCreateWidgets() {
		this.objGclipboardJTextArea = new ClipboardJTextArea(this.objGcontrolJFrame);
		this.objGclipboardJTextArea.setOpaque(true);
		this.objGsimpleCopyJButton =
										new ClipboardJButton(	this.objGcontrolJFrame,
																this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_SIMPLE_COPY),
																false,
																false);
		this.objGdetailedCopyJButton =
										new ClipboardJButton(	this.objGcontrolJFrame,
																this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_DETAILED_COPY),
																true,
																false);
		this.objGcopyClipboardJButton =
										new ClipboardJButton(	this.objGcontrolJFrame,
																this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_COPY));
		this.objGfreeClipboardJButton =
										new ClipboardJButton(	this.objGcontrolJFrame,
																this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_FREE),
																false,
																true);
		this.objGcloseClipboardJButton =
											new ClipboardJButton(	this.objGcontrolJFrame,
																	this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_CLOSE),
																	false,
																	false,
																	false,
																	true);
	}

	final public void doLoadImages() {
		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_CLIPBOARD, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);

		this.objGcopyClipboardJButton.doLoadImages();
		this.objGdetailedCopyJButton.doLoadImages();
		this.objGfreeClipboardJButton.doLoadImages();
		this.objGsimpleCopyJButton.doLoadImages();
		this.objGcloseClipboardJButton.doLoadImages();
		this.validate();
		this.pack();
		this.setMinimumSize(new Dimension(this.getWidth(), 0));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPdetailedCopy
	 * @param bolPfreeClipboard
	 * @return
	 */
	final public ClipboardJButton getClipboardJButton(boolean bolPdetailedCopy, boolean bolPfreeClipboard) {
		return bolPfreeClipboard ? this.objGfreeClipboardJButton : bolPdetailedCopy ? this.objGdetailedCopyJButton : this.objGsimpleCopyJButton;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public boolean isEmpty() {
		return this.objGclipboardJTextArea == null || Tools.isEmpty(this.objGclipboardJTextArea.getText());
	}

	final public void setClipboardEnabled() {
		final String strLtext = this.objGclipboardJTextArea.getText();
		final boolean bolLnotEmpty = !strLtext.equals(Strings.strS_EMPTY);

		this.objGcontrolJFrame.objGfreeClipboardJuggleJMenuItem.setEnabled(bolLnotEmpty);
		this.objGfreeClipboardJButton.setEnabled(bolLnotEmpty);
		this.objGcopyClipboardJButton.setEnabled(bolLnotEmpty);
		this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.setEnabled(bolLnotEmpty
																			|| this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPstatus
	 */
	final public void setCopyEnabled(boolean bolPstatus) {
		this.objGsimpleCopyJButton.setEnabled(bolPstatus);
		this.objGdetailedCopyJButton.setEnabled(bolPstatus);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPtext
	 */
	final public void setCopyText(String strPtext) {
		this.objGcopyClipboardJButton.setText(strPtext);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPtext
	 */
	final public void setDetailedText(String strPtext) {
		this.objGdetailedCopyJButton.setText(strPtext);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPtext
	 */
	final public void setFreeText(String strPtext) {
		this.objGfreeClipboardJButton.setText(strPtext);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPtext
	 */
	final public void setSimpleText(String strPtext) {
		this.objGsimpleCopyJButton.setText(strPtext);
	}
}

/*
 * @(#)ClipboardJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
