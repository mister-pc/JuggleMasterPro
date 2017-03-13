/*
 * @(#)LinksJMenuItemActionListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.help;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedActionEvent;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class LinksJMenuItemActionListener implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPlinkAddress
	 * @param bolPopenWithDefaultApplication
	 */
	public LinksJMenuItemActionListener(ControlJFrame objPcontrolJFrame,
										Component objPfatherComponent,
										String strPlinkAddress,
										boolean bolPopenWithDefaultApplication) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGfatherComponent = objPfatherComponent;
		this.strGlinkAddress = strPlinkAddress;
		this.bolGopenWithDefaultApplication = bolPopenWithDefaultApplication;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("LinksJMenuItemActionListener.actionPerformed()");
		if (this.strGlinkAddress != Strings.strS_EMPTY) {
			this.objGcontrolJFrame.doHidePopUps();
			HelpActions.doOpenHyperlink(this.objGcontrolJFrame, this.strGlinkAddress, this.bolGopenWithDefaultApplication);
		} else {
			if (this.objGfatherComponent instanceof JMenu) {
				// requestFocusInWindow();
				final ExtendedActionEvent objLactionEvent = new ExtendedActionEvent(objPactionEvent);
				objLactionEvent.consumeEvent();
				((JMenu) this.objGfatherComponent).setPopupMenuVisible(true);
			}
		}
	}

	final private boolean		bolGopenWithDefaultApplication;

	final private ControlJFrame	objGcontrolJFrame;
	private final Component		objGfatherComponent;

	final private String		strGlinkAddress;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)LinksJMenuItemActionListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
