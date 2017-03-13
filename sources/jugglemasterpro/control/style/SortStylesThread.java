/*
 * @(#)SortStylesThread.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.style;




import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.pattern.Style;
import jugglemasterpro.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class SortStylesThread implements Runnable {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	final private ControlJFrame	objGcontrolJFrame;




	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public SortStylesThread(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		new Thread(this).start();
	}




	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void run() {
		this.objGcontrolJFrame.objGsortStylesJButton.setEnabled(false);
		final String strLstyle = ((Style) this.objGcontrolJFrame.objGstyleJComboBox.getSelectedItem()).strGstyle;
		this.objGcontrolJFrame.objGstyleJComboBox.sort();
		this.objGcontrolJFrame.objGstyleJComboBox.setList();
		this.objGcontrolJFrame.objGstyleJComboBox.selectItem(strLstyle);
		this.objGcontrolJFrame.objGsortStylesJButton.setEnabled(true);
	}
}

/*
 * @(#)SortStylesThread.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
