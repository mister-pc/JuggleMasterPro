/*
 * @(#)AboutJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.help;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.control.window.JDialogWindowListener;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class AboutJDialog extends JDialog implements HyperlinkListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final ControlJFrame	objGcontrolJFrame;

	private final JEditorPane	objGjEditorPane;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public AboutJDialog(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, objPcontrolJFrame.getLanguageString(Language.intS_TITLE_ABOUT), true);

		this.objGcontrolJFrame = objPcontrolJFrame;

		this.objGjEditorPane = new JEditorPane();
		this.objGjEditorPane.setContentType("text/html");
		this.objGjEditorPane.setFont(this.objGcontrolJFrame.getFont());
		this.objGjEditorPane.setOpaque(true);
		this.objGjEditorPane.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.objGjEditorPane.setEditable(false);
		this.objGjEditorPane.addHyperlinkListener(this);

		this.setLayout(new GridBagLayout());
		this.setResizable(false);
		this.add(this.objGjEditorPane, new ExtendedGridBagConstraints(0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1F, 1F));
		this.validate();
		this.pack();
		this.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this, false));
	}

	final public void doLoadImages() {
		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_ABOUT, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPhyperlinkEvent
	 */
	@Override final public void hyperlinkUpdate(HyperlinkEvent objPhyperlinkEvent) {
		if (objPhyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			HelpActions.doOpenHyperlink(this.objGcontrolJFrame, objPhyperlinkEvent.getDescription(), false);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPhtml
	 * @return
	 */
	final public boolean setContent(String strPhtml) {

		try {
			this.objGjEditorPane.setText(strPhtml);
			this.objGjEditorPane.validate();
		} catch (final Throwable objPsecondThrowable) {
			Tools.err("Error while setting about window");
			return false;
		}
		return true;
	}
}

/*
 * @(#)AboutJDialog.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
