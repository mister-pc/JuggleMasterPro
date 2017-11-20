package fr.jugglemaster.control.print;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.window.PopUpJDialog;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;

final public class PrintActions {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public static void doPrint(final ControlJFrame objPcontrolJFrame) {

		if (objPcontrolJFrame.doDisplayComingSoonPopUp()) {
			return;
		}
		final byte bytLanimationStatus = objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus;
		objPcontrolJFrame.getJuggleMasterPro().bolGcoloredBorder = true;
		objPcontrolJFrame.setMouseCursorEnabled(false);
		objPcontrolJFrame.getJuggleMasterPro().doPausePattern();
		final ImagesPrinter objLimagesPrinter = new ImagesPrinter(objPcontrolJFrame);
		objPcontrolJFrame.setMouseCursorEnabled(true);
		if (objLimagesPrinter.isPrinterReady()) {
			final BufferedImage imgLbuffer =
													Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(objPcontrolJFrame	.getJuggleMasterPro()
																																	.getFrame()
																																	.getBackgroundWidth(),
																												objPcontrolJFrame	.getJuggleMasterPro()
																																	.getFrame()
																																	.getBackgroundHeight(),
																												Transparency.OPAQUE);
			final Graphics2D objLgraphics2D = (Graphics2D) imgLbuffer.getGraphics();
			objPcontrolJFrame.getJuggleMasterPro().getFrame().doPaintFrame(objLgraphics2D);
			objLgraphics2D.dispose();
			final ArrayList<Image> imgLAL = new ArrayList<Image>(1);
			imgLAL.add(imgLbuffer);
			objLimagesPrinter.setImages(imgLAL);
			final Boolean bolLprintedBoolean = objLimagesPrinter.showPrintJDialog();
			if (bolLprintedBoolean != null) {
				if (bolLprintedBoolean) {
					new PopUpJDialog(	objPcontrolJFrame,
										Constants.bytS_UNCLASS_NO_VALUE,
										Constants.intS_FILE_ICON_INFORMATION,
										objPcontrolJFrame.getLanguageString(Language.intS_TITLE_PRINT_PATTERN),
										objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_ANIMATION_SENT_TO_PRINTER),
										null,
										true);
				} else {
					new PopUpJDialog(	objPcontrolJFrame,
										Constants.bytS_UNCLASS_NO_VALUE,
										Constants.intS_FILE_ICON_ALERT,
										objPcontrolJFrame.getLanguageString(Language.intS_TITLE_PRINT_PATTERN),
										objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_PRINTING_ERROR_OCCURED),
										null,
										true);
				}
			}
		} else {
			new PopUpJDialog(	objPcontrolJFrame,
								Constants.bytS_UNCLASS_NO_VALUE,
								Constants.intS_FILE_ICON_ALERT,
								objPcontrolJFrame.getLanguageString(Language.intS_TITLE_PRINT_PATTERN),
								objPcontrolJFrame.getLanguageString(Language.intS_MESSAGE_NO_PRINTER_FOUND),
								null,
								true);
		}
		System.gc();
		objPcontrolJFrame.getJuggleMasterPro().bolGcoloredBorder = false;
		objPcontrolJFrame.getJuggleMasterPro().bytGanimationStatus = bytLanimationStatus;
	}

}
