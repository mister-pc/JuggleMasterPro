/*
 * @(#)ConsoleJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.data;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.control.window.JDialogWindowListener;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ConsoleJDialog extends JDialog {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean				bolGalreadyDisplayed;

	public ConsoleJButton		objGcloseConsoleJButton;

	public JTextArea			objGconsoleJTextArea;

	private final ControlJFrame	objGcontrolJFrame;

	public ConsoleJButton		objGcopyConsoleJButton;
	public ConsoleJButton		objGfreeConsoleJButton;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ConsoleJDialog(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame.getJuggleMasterPro().getFrame(), false);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGalreadyDisplayed = false;
		this.setLayout(new GridBagLayout());

		this.objGconsoleJTextArea = new JTextArea();
		this.objGconsoleJTextArea.setOpaque(true);
		this.objGconsoleJTextArea.setEditable(false);
		this.objGconsoleJTextArea.setFont(new Font("Courier", Font.PLAIN, 12));
		final JScrollPane objLjScrollPane =
											new JScrollPane(this.objGconsoleJTextArea,
															ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
															ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		objLjScrollPane.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.objGcloseConsoleJButton = new ConsoleJButton(this.objGcontrolJFrame, false, true);
		this.objGcloseConsoleJButton.setFont(this.objGcontrolJFrame.getFont());
		this.objGfreeConsoleJButton = new ConsoleJButton(this.objGcontrolJFrame, false, false);
		this.objGfreeConsoleJButton.setFont(this.objGcontrolJFrame.getFont());
		this.objGcopyConsoleJButton = new ConsoleJButton(this.objGcontrolJFrame, true, false);
		this.objGcopyConsoleJButton.setFont(this.objGcontrolJFrame.getFont());
		final JPanel objLjButtonsJPanel = new JPanel();
		objLjButtonsJPanel.setOpaque(true);
		objLjButtonsJPanel.setLayout(new BoxLayout(objLjButtonsJPanel, BoxLayout.LINE_AXIS));
		objLjButtonsJPanel.add(Box.createHorizontalGlue());
		objLjButtonsJPanel.add(this.objGcopyConsoleJButton);
		objLjButtonsJPanel.add(Box.createHorizontalGlue());
		objLjButtonsJPanel.add(this.objGfreeConsoleJButton);
		objLjButtonsJPanel.add(Box.createHorizontalGlue());
		objLjButtonsJPanel.add(this.objGcloseConsoleJButton);
		objLjButtonsJPanel.add(Box.createHorizontalGlue());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											5,
																											5,
																											5,
																											5,
																											GridBagConstraints.BOTH,
																											1F,
																											1F);
		this.add(objLjScrollPane, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1F, 0F);
		this.add(objLjButtonsJPanel, objLextendedGridBagConstraints);
		this.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this, false));
	}

	final public void doLoadImages() {
		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_CONSOLE, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
		this.objGcloseConsoleJButton.doLoadImages();
		this.objGfreeConsoleJButton.doLoadImages();
		this.objGcopyConsoleJButton.doLoadImages();
		this.validate();
		this.pack();
		this.setMinimumSize(new Dimension(this.getWidth(), 0));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPfileName
	 * @param bolPredisplay
	 */
	final public void setContent(String strPfileName, boolean bolPredisplay) {
		final String strLconsole = this.objGcontrolJFrame.getPatternsManager().getPatternsFileManager().getConsoleString(this.objGcontrolJFrame.getLanguage());
		final StringBuilder strLtitle = new StringBuilder();
		if (strPfileName != null) {
			strLtitle.append(strPfileName);
			strLtitle.append(" - ");
		}
		strLtitle.append(this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_CONSOLE));
		this.setTitle(strLtitle.toString());

		this.objGconsoleJTextArea.setText(strLconsole);
		this.objGconsoleJTextArea.setCaretPosition(0);
		final boolean bolLdisplayed = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_ERRORS) && strLconsole.length() > 0;
		this.objGfreeConsoleJButton.setEnabled(bolLdisplayed);
		this.objGcopyConsoleJButton.setEnabled(bolLdisplayed);

		if (bolPredisplay) {
			this.setVisible(bolLdisplayed);
			this.objGcontrolJFrame.objGconsoleJCheckBoxMenuItem.selectState(bolLdisplayed);
		}
	}

	final public void setLabels() {
		this.objGcloseConsoleJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_CLOSE));
		this.objGcloseConsoleJButton.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																			? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_DO_NOT_DISPLAY_CONSOLE_MENU)
																																			: null);
		this.objGfreeConsoleJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_FREE));
		this.objGfreeConsoleJButton.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																			? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_FREE_CONSOLE)
																																			: null);
		this.objGcopyConsoleJButton.setText(this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_COPY));
		this.objGcopyConsoleJButton.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																			? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_CONSOLE_COPY)
																																			: null);
		this.validate();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void setVisible(boolean bolPvisible) {
		if (bolPvisible && !this.bolGalreadyDisplayed) {
			this.objGcontrolJFrame.setWindowBounds(	this,
													Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE,
													Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE / 2);
			this.bolGalreadyDisplayed = true;
		}
		this.objGcontrolJFrame.objGconsoleJCheckBoxMenuItem.setLabel();
		super.setVisible(bolPvisible);
	}
}

/*
 * @(#)ConsoleJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
