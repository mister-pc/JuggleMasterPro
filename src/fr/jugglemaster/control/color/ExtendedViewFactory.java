/*
 * @(#)ExtendedViewFactory.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.color;




import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import fr.jugglemaster.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ExtendedViewFactory implements ViewFactory {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	private final ViewFactory	objLparentViewFactory;




	/**
	 * Constructs
	 * 
	 * @param objPparentViewFactory
	 */
	public ExtendedViewFactory(ViewFactory objPparentViewFactory) {
		this.objLparentViewFactory = objPparentViewFactory;
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param objPelement
	 * @return
	 */
	@Override final public View create(Element objPelement) {
		if (AbstractDocument.ParagraphElementName.equals(objPelement.getName())) {
			return new ExtendedParagraphView(objPelement);
		}
		return this.objLparentViewFactory.create(objPelement);
	}
}

/*
 * @(#)ExtendedViewFactory.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
