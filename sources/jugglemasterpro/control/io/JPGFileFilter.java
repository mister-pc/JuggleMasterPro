/*
 * @(#)JPGFileFilter.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.io;

import java.io.File;

import jugglemasterpro.util.Constants;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class JPGFileFilter extends javax.swing.filechooser.FileFilter {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final String		strGdescription;

	/**
	 * Constructs
	 * 
	 * @param strPdescription
	 */
	public JPGFileFilter(String strPdescription) {
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
		final String strLfileExtension = objPfile.getName().substring(objPfile.getName().lastIndexOf('.') + 1);
		return objPfile.isDirectory()
				|| strLfileExtension.equalsIgnoreCase(Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPG])
				|| strLfileExtension.equalsIgnoreCase(Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPEG]);
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
 * @(#)JPGFileFilter.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
