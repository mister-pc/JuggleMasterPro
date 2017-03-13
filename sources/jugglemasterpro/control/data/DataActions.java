package jugglemasterpro.control.data;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.window.PopUpJDialog;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;

final public class DataActions {

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPdisplay
	 */
	final public static void doDisplayClipboard(final ControlJFrame objPcontrolJFrame, final boolean bolPdisplay) {

		objPcontrolJFrame.objGclipboardJCheckBoxMenuItem.setLabel();
		if (bolPdisplay) {
			if (!objPcontrolJFrame.objGclipboardJCheckBoxMenuItem.bolGalreadyDisplayed) {
				objPcontrolJFrame.setWindowBounds(	objPcontrolJFrame.objGclipboardJDialog,
													Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE,
													Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE / 2);
				objPcontrolJFrame.objGclipboardJCheckBoxMenuItem.bolGalreadyDisplayed = true;
			}
			objPcontrolJFrame.objGclipboardJDialog.setVisible(true);
			new PopUpJDialog(	objPcontrolJFrame,
								objPcontrolJFrame.objGclipboardJDialog,
								Constants.bytS_BOOLEAN_GLOBAL_CLIPBOARD_INFORMATION,
								Constants.intS_FILE_ICON_INFORMATION,
								objPcontrolJFrame.getLanguageString(Language.intS_TITLE_PATTERNS_CLIPBOARD_USAGE),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_EDIT_SELECT_AND_COPY_THE_PATTERNS_PARAMETERS_YOU_NEED_1),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_EDIT_SELECT_AND_COPY_THE_PATTERNS_PARAMETERS_YOU_NEED_2),
								true);
		} else {
			objPcontrolJFrame.objGclipboardJDialog.setVisible(false);
			objPcontrolJFrame.objGclipboardJCheckBoxMenuItem.selectState(false);
		}
	}

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

}
