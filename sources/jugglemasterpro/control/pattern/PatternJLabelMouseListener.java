package jugglemasterpro.control.pattern;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import jugglemasterpro.control.ControlJFrame;

final public class PatternJLabelMouseListener implements MouseListener {

	private final ControlJFrame	objGcontrolJFrame;

	public PatternJLabelMouseListener(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
	}

	@Override public void mouseClicked(MouseEvent objPmouseEvent) {
		this.objGcontrolJFrame.doRestartJuggling();
	}

	@Override public void mouseEntered(MouseEvent objPmouseEvent) {}

	@Override public void mouseExited(MouseEvent objPmouseEvent) {}

	@Override public void mousePressed(MouseEvent objPmouseEvent) {}

	@Override public void mouseReleased(MouseEvent objPmouseEvent) {}

}
