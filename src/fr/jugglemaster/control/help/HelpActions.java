package fr.jugglemaster.control.help;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URL;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.io.FileActions;
import fr.jugglemaster.control.window.PopUpJDialog;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;

final public class HelpActions {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPhyperlink
	 * @param bolPopenWithDefaultApplication
	 */
	final public static void doOpenHyperlink(	final ControlJFrame objPcontrolJFrame,
												final String strPhyperlink,
												final boolean bolPopenWithDefaultApplication) {

		final boolean bolLmailTo = strPhyperlink.toLowerCase().startsWith("mailto:");
		boolean bolLpopUpalreadyDisplayed = false;
		if (!bolLmailTo && !bolPopenWithDefaultApplication) {
			bolLpopUpalreadyDisplayed = true;
			new PopUpJDialog(	objPcontrolJFrame,
								Constants.bytS_BOOLEAN_GLOBAL_NAVIGATOR_CONFIGURATION,
								Constants.intS_FILE_ICON_INFORMATION,
								objPcontrolJFrame.getLanguageString(Language.intS_TITLE_POP_UP_MANAGER),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_CONFIGURE_YOUR_NAVIGATOR_TO_VISIT_THE_PAGE_TOKEN_1,
																	strPhyperlink),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_CONFIGURE_YOUR_NAVIGATOR_TO_VISIT_THE_PAGE_TOKEN_2,
																	strPhyperlink),
								true);
		}

		try {
			if (Desktop.isDesktopSupported()) {
				if (bolPopenWithDefaultApplication && objPcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted) {
					try {
						Desktop.getDesktop().open(new File(strPhyperlink).getAbsoluteFile());
					} catch (final Throwable objPinnerThrowable) {
						if (!bolLpopUpalreadyDisplayed) {
							new PopUpJDialog(	objPcontrolJFrame,
												Constants.bytS_BOOLEAN_GLOBAL_NAVIGATOR_CONFIGURATION,
												Constants.intS_FILE_ICON_INFORMATION,
												objPcontrolJFrame.getLanguageString(Language.intS_TITLE_POP_UP_MANAGER),
												objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_CONFIGURE_YOUR_NAVIGATOR_TO_VISIT_THE_PAGE_TOKEN_1,
																					strPhyperlink),
												objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_CONFIGURE_YOUR_NAVIGATOR_TO_VISIT_THE_PAGE_TOKEN_2,
																					strPhyperlink),
												true);
						}
						Desktop.getDesktop().browse(new URI(strPhyperlink));
					}
				} else {
					if (bolLmailTo) {
						Desktop.getDesktop().mail(new URI(strPhyperlink));
					} else {
						if (objPcontrolJFrame.getJuggleMasterPro().bolGprogramTrusted
							|| objPcontrolJFrame.getJuggleMasterPro().bytGprogramType == Constants.bytS_STATE_PROGRAM_LOCAL_APPLET) {
							try {
								Desktop.getDesktop().browse(new File(strPhyperlink).getAbsoluteFile().toURI());
							} catch (final Throwable objPinnerThrowable) {
								Desktop.getDesktop().browse(new URI(strPhyperlink));
							}
						} else {
							objPcontrolJFrame.getJuggleMasterPro().getAppletContext().showDocument(new URL(strPhyperlink), "_blank");
						}
					}
				}
			} else {
				if (objPcontrolJFrame.getJuggleMasterPro().bytGprogramType == Constants.bytS_STATE_PROGRAM_LOCAL_APPLET
					|| objPcontrolJFrame.getJuggleMasterPro().bytGprogramType == Constants.bytS_STATE_PROGRAM_WEB_APPLET) {
					objPcontrolJFrame.getJuggleMasterPro().getAppletContext().showDocument(new URL(strPhyperlink), "_blank");
				} else {
					new Throwable();
				}
			}
		} catch (final Throwable objPthrowable) {
			FileActions.doDisplayFileLoadFailurePopUp(objPcontrolJFrame, strPhyperlink);
		}
	}

}
