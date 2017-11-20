/*
 * @(#)SiteswapPlainDocument.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.siteswap;




import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class SiteswapPlainDocument extends PlainDocument {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	/**
	 * Constructs
	 */
	public SiteswapPlainDocument() {}




	/**
	 * Method description
	 * 
	 * @see
	 * @param intPposition
	 * @param strP
	 * @param objPattributeSet
	 * @throws BadLocationException
	 */
	@Override public void insertString(int intPposition, String strP, AttributeSet objPattributeSet) throws BadLocationException {
		if (Tools.isEmpty(strP)) {
			return;
		}
		final char[] chrLstringA = strP.toCharArray();
		final StringBuilder objLinsertedStringBuilder = new StringBuilder(chrLstringA.length);
		boolean bolLbeep = false;
		for (int intLindex = 0; intLindex < chrLstringA.length; ++intLindex) {
			if (Tools.isSiteswapChar(chrLstringA[intLindex])) {
				objLinsertedStringBuilder.append(chrLstringA[intLindex]);
			} else {
				bolLbeep = true;
			}
		}
		super.insertString(intPposition, objLinsertedStringBuilder.toString(), objPattributeSet);
		if (bolLbeep) {
			Constants.objS_GRAPHICS_TOOLKIT.beep();
		}
	}
}

/*
 * @(#)SiteswapPlainDocument.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
