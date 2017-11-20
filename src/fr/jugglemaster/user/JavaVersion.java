/*
 * @(#)JuggleJavaVersion.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.user;




import java.applet.Applet;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;
import netscape.javascript.JSObject;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
public class JavaVersion extends Applet {

	/**
	 * Constructs
	 */
	public JavaVersion() {

		final JSObject objLwindowJSObject = JSObject.getWindow(this);
		final JSObject objLdocumentJSObject = (JSObject) objLwindowJSObject.getMember("document");
		final JSObject objLformsJSObject = (JSObject) objLdocumentJSObject.getMember("forms");
		final JSObject objLformJSObject = (JSObject) objLformsJSObject.getMember("javaVersionApplet");
		final JSObject objLelementsJSObject = (JSObject) objLformJSObject.getMember("elements");
		final JSObject objLjavaVersionJSObject = (JSObject) objLelementsJSObject.getMember("javaVersion");
		objLjavaVersionJSObject.setMember("value", this.getJavaVersion());
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public String getAppletInfo() {
		return Strings.doConcat(	"JuggleJavaVersion ",
										Constants.strS_ENGINE_VERSION_NUMBER,
										" - \251 ",
										Constants.strS_ENGINE_COPYRIGHT_YEARS,
										' ',
										Constants.strS_ENGINE_ARNAUD_BELO,
										" (jugglemaster@free.fr)");
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final private String getJavaVersion() {
		final String strLunknownVersion = "0";
		String strLjavaVersion = System.getProperty("java.version");
		final String strLuserOS = System.getProperty("os.name").toLowerCase();
		if (!strLuserOS.startsWith("mac os x") && !strLjavaVersion.startsWith("1.7")) {
			try {
				if ((strLjavaVersion.indexOf("_") != -1) && (strLjavaVersion.indexOf("_") != -1)) {
					strLjavaVersion = strLjavaVersion.split("_")[0].split("\\.")[1];
				} else {
					strLjavaVersion = strLunknownVersion;
				}
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while retrieving Java version");
				strLjavaVersion = strLunknownVersion;
			}
		} else {
			strLjavaVersion = strLunknownVersion;
		}
		return strLjavaVersion;
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)JuggleJavaVersion.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
