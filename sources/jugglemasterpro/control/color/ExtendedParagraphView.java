/*
 * @(#)ExtendedParagraphView.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.color;




import javax.swing.text.Element;
import javax.swing.text.ParagraphView;

import jugglemasterpro.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ExtendedParagraphView extends ParagraphView {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	/**
	 * Constructs
	 * 
	 * @param objPelement
	 */
	public ExtendedParagraphView(Element objPelement) {
		super(objPelement);
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param intPaxis
	 * @return
	 */
	@Override final public float getMinimumSpan(int intPaxis) {
		return super.getPreferredSpan(intPaxis);
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param intPwidth
	 * @param intPheight
	 */
	@Override final protected void layout(int intPwidth, int intPheight) {
		super.layout(Short.MAX_VALUE, intPheight);
	}
}

/*
 * @(#)ExtendedParagraphView.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
