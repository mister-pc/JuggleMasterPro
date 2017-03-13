/*
 * @(#)PanelListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import jugglemasterpro.control.color.ColorActions;
import jugglemasterpro.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ControlJFrameListener implements ComponentListener, WindowListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ControlJFrameListener(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentHidden(ComponentEvent objPcomponentEvent) {
		ColorActions.doHideColorsChoosers(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentMoved(ComponentEvent objPcomponentEvent) {

		synchronized (Constants.objS_ENGINE_FRAMES_LOCK_OBJECT) {
			this.setBounds();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentResized(ComponentEvent objPcomponentEvent) {

		synchronized (Constants.objS_ENGINE_FRAMES_LOCK_OBJECT) {
			this.setBounds();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcomponentEvent
	 */
	@Override final public void componentShown(ComponentEvent objPcomponentEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowActivated(WindowEvent objPwindowEvent) {
		this.objGcontrolJFrame.doActivatePanel();
	}

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
		this.objGcontrolJFrame.getJuggleMasterPro().doQuit(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeactivated(WindowEvent objPwindowEvent) {
		ColorActions.doHideColorsChoosers(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeiconified(WindowEvent objPwindowEvent) {

		ColorActions.doHideColorsChoosers(this.objGcontrolJFrame);
		this.objGcontrolJFrame.getJuggleMasterPro().getFrame().setExtendedState(Frame.NORMAL);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowIconified(WindowEvent objPwindowEvent) {

		ColorActions.doHideColorsChoosers(this.objGcontrolJFrame);
		this.objGcontrolJFrame.getJuggleMasterPro().getFrame().setExtendedState(Frame.ICONIFIED);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowOpened(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setBounds() {

		Rectangle objLjuggleMasterProJFrameRectangle = this.objGcontrolJFrame.getJuggleMasterPro().getFrame().getBounds();

		Rectangle objLcontrolJFrameRectangle = this.objGcontrolJFrame.getBounds();
		boolean bolLsetBounds = false;

		// Maximized :
		if (this.objGcontrolJFrame.getExtendedState() == Frame.MAXIMIZED_BOTH) {
			objLcontrolJFrameRectangle =
											new Rectangle(	Constants.intS_GRAPHICS_SCREEN_X,
															Constants.intS_GRAPHICS_SCREEN_Y,
															Constants.intS_GRAPHICS_SCREEN_WIDTH / 2,
															Constants.intS_GRAPHICS_SCREEN_HEIGHT);
			objLjuggleMasterProJFrameRectangle =
													new Rectangle(	Constants.intS_GRAPHICS_SCREEN_WIDTH / 2,
																	Constants.intS_GRAPHICS_SCREEN_Y,
																	Constants.intS_GRAPHICS_SCREEN_WIDTH / 2,
																	Constants.intS_GRAPHICS_SCREEN_HEIGHT);
			bolLsetBounds = true;
		}

		// Maximum width :
		if (objLjuggleMasterProJFrameRectangle.getWidth() > Constants.intS_GRAPHICS_SCREEN_WIDTH) {
			objLjuggleMasterProJFrameRectangle.width = Constants.intS_GRAPHICS_SCREEN_WIDTH;
			bolLsetBounds = true;
		}
		if (objLcontrolJFrameRectangle.getWidth() > Constants.intS_GRAPHICS_SCREEN_WIDTH) {
			objLcontrolJFrameRectangle.width = Constants.intS_GRAPHICS_SCREEN_WIDTH;
			bolLsetBounds = true;
		}

		// Maximum height :
		if (objLjuggleMasterProJFrameRectangle.getHeight() > Constants.intS_GRAPHICS_SCREEN_HEIGHT) {
			objLjuggleMasterProJFrameRectangle.height = Constants.intS_GRAPHICS_SCREEN_HEIGHT;
			bolLsetBounds = true;
		}
		if (objLcontrolJFrameRectangle.getHeight() > Constants.intS_GRAPHICS_SCREEN_HEIGHT) {
			objLcontrolJFrameRectangle.height = Constants.intS_GRAPHICS_SCREEN_HEIGHT;
			bolLsetBounds = true;
		}

		// Same height :
		if (objLjuggleMasterProJFrameRectangle.getHeight() != objLcontrolJFrameRectangle.getHeight()) {
			objLjuggleMasterProJFrameRectangle.height = (int) objLcontrolJFrameRectangle.getHeight();
			bolLsetBounds = true;
		}

		// Correct X location :
		if (objLcontrolJFrameRectangle.getX() + objLcontrolJFrameRectangle.getWidth() != objLjuggleMasterProJFrameRectangle.getX()) {
			objLjuggleMasterProJFrameRectangle.x = (int) (objLcontrolJFrameRectangle.getX() + objLcontrolJFrameRectangle.getWidth());
			bolLsetBounds = true;
		}

		// Same Y location :
		if (objLjuggleMasterProJFrameRectangle.getY() != objLcontrolJFrameRectangle.getY()) {
			objLjuggleMasterProJFrameRectangle.y = (int) objLcontrolJFrameRectangle.getY();
			bolLsetBounds = true;
		}

		// Set bounds :
		if (bolLsetBounds) {
			this.objGcontrolJFrame.setBounds(objLcontrolJFrameRectangle, objLjuggleMasterProJFrameRectangle);
		}
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private ControlJFrame	objGcontrolJFrame;
}

/*
 * @(#)PanelListener.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
