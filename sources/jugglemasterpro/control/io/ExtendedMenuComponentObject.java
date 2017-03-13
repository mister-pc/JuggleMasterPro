/*
 * @(#)JuggleMenuComponentObject.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.io;




import java.util.ArrayList;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ExtendedMenuComponentObject extends Object {

	@SuppressWarnings("unused")
	final private static long							serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	private final String								strGiconReference;

	private final String								strGlabel;

	private final String								strGlinkReference;

	private final ArrayList<ExtendedMenuComponentObject>	objGsubMenuAL;




	/**
	 * Constructs
	 * 
	 * @param strPlinkReference
	 * @param strPiconReference
	 * @param strPlabel
	 * @param bolPmenuItem
	 */
	public ExtendedMenuComponentObject(String strPlinkReference, String strPiconReference, String strPlabel, boolean bolPmenuItem) {
		final boolean bolLseparator =
										bolPmenuItem
											&& (Strings.areNullEqual(strPlinkReference, Strings.strS_SEPARATOR)
												|| Strings.areNullEqual(strPiconReference, Strings.strS_SEPARATOR) || Strings.areNullEqual(	strPlabel,
																																											Strings.strS_SEPARATOR));

		this.strGlinkReference =
										(bolPmenuItem ? bolLseparator ? Strings.strS_SEPARATOR
																		: strPlinkReference != null ? new String(strPlinkReference)
																											: null : null);
		this.strGiconReference =
										(bolLseparator ? Strings.strS_SEPARATOR
														: strPiconReference != null ? new String(strPiconReference) : null);
		this.strGlabel = new String(bolLseparator ? Strings.strS_SEPARATOR : strPlabel);
		this.objGsubMenuAL = bolPmenuItem ? null : new ArrayList<ExtendedMenuComponentObject>(1);
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getIconReferenceString() {
		return this.strGiconReference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getLabelString() {
		return this.strGlabel;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getLinkReferenceString() {
		return this.strGlinkReference;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public ArrayList<ExtendedMenuComponentObject> getSubMenuAL() {
		return this.objGsubMenuAL;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public boolean isMenu() {
		return (this.objGsubMenuAL != null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public boolean isMenuItem() {
		return (this.objGsubMenuAL == null);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public boolean isSeparator() {
		return (this.isMenuItem() && this.strGlabel.equals(Strings.strS_SEPARATOR));
	}
}

/*
 * @(#)JuggleMenuComponentObject.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
