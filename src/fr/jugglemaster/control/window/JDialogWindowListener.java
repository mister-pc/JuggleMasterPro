/*
 * @(#)JDialogWindowListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.window;




import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.DefaultEscapeAction;
import fr.jugglemaster.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class JDialogWindowListener implements WindowListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPdialog
	 * @param bolPdispose
	 */
	public JDialogWindowListener(ControlJFrame objPcontrolJFrame, JDialog objPdialog, boolean bolPdispose) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGjDialog = objPdialog;
		this.bolGdispose = bolPdispose;
		final JRootPane objLrootPane = objPdialog.getRootPane();
		final InputMap objLinputMap = objLrootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		objLinputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Constants.strS_ENGINE_ESCAPE_ACTION);
		objLrootPane.getActionMap().put(Constants.strS_ENGINE_ESCAPE_ACTION, new DefaultEscapeAction(this));
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowActivated(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosed(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosing(WindowEvent objPwindowEvent) {

		if (this.objGjDialog == this.objGcontrolJFrame.objGclipboardJDialog) {
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.selectState(false);
			this.objGcontrolJFrame.objGclipboardJCheckBoxMenuItem.setEnabled(this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
																			|| !this.objGcontrolJFrame.objGclipboardJDialog.isEmpty());
		}
		if (this.objGjDialog == this.objGcontrolJFrame.objGconsoleJDialog) {
			this.objGcontrolJFrame.objGconsoleJCheckBoxMenuItem.selectState(false);
		}

		this.objGjDialog.setVisible(false);
		if (this.bolGdispose) {
			this.objGjDialog.dispose();
			this.objGjDialog = null;
		}
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeactivated(WindowEvent objPwindowEvent) {}




	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeiconified(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowIconified(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowOpened(WindowEvent objPwindowEvent) {}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean		bolGdispose;

	private JDialog				objGjDialog;

	private final ControlJFrame	objGcontrolJFrame;
}

/*
 * @(#)JDialogWindowListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
