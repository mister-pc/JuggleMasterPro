/*
 * @(#)INILanguageFileFilter.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.io;




import java.io.File;

import fr.jugglemaster.util.Constants;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class INILanguageFileFilter extends javax.swing.filechooser.FileFilter {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	private final String		strGdescription;




	/**
	 * Constructs
	 * 
	 * @param strPdescription
	 */
	public INILanguageFileFilter(String strPdescription) {
		this.strGdescription = strPdescription;
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @param objPfile
	 * @return
	 */
	@Override final public boolean accept(File objPfile) {
		return objPfile.isDirectory() || objPfile.getName().equalsIgnoreCase("lang.ini");
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public String getDescription() {
		return this.strGdescription;
	}
}

/*
 * @(#)INILanguageFileFilter.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
