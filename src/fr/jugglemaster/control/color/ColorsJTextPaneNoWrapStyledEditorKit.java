/*
 * @(#)NoWrapStyledEditorKit.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.color;




import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;
import fr.jugglemaster.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ColorsJTextPaneNoWrapStyledEditorKit extends StyledEditorKit {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	protected ViewFactory		objLdefaultViewFactory;




	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public ViewFactory getViewFactory() {
		if (this.objLdefaultViewFactory == null) {
			this.objLdefaultViewFactory = new ExtendedViewFactory(super.getViewFactory());
		}
		return this.objLdefaultViewFactory;
	}
}

/*
 * @(#)NoWrapStyledEditorKit.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
