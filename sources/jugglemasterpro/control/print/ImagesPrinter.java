package jugglemasterpro.control.print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.window.PopUpJDialog;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class ImagesPrinter implements Printable {

	@SuppressWarnings("unused")
	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final ControlJFrame		objGcontrolJFrame;
	private ArrayList<Image>		objGimageAL;
	private final PrintService[]	objGprintServiceA;

	public ImagesPrinter(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGprintServiceA = PrintServiceLookup.lookupPrintServices(null, null);
	}

	final public boolean isPrinterReady() {
		return this.objGprintServiceA.length > 0;
	}

	final public int print(Graphics objPgraphics, PageFormat objPpageFormat, int intPpageIndex) {

		if (this.objGimageAL != null && intPpageIndex < this.objGimageAL.size()) {

			final RenderedImage imgLrendered = (RenderedImage) this.objGimageAL.get(intPpageIndex);

			if (imgLrendered != null) {
				final Graphics2D objLgraphics2D = (Graphics2D) objPgraphics;
				objLgraphics2D.translate(objPpageFormat.getImageableX(), objPpageFormat.getImageableY());

				final double dblLxRatio = objPpageFormat.getImageableWidth() / imgLrendered.getWidth();
				final double dblLyRatio = objPpageFormat.getImageableHeight() / imgLrendered.getHeight();

				objLgraphics2D.scale(dblLxRatio, dblLyRatio);

				final AffineTransform objLaffineTransform =
															AffineTransform.getTranslateInstance(	objPpageFormat.getImageableX(),
																									objPpageFormat.getImageableY());
				objLgraphics2D.drawRenderedImage(imgLrendered, objLaffineTransform);

				return Printable.PAGE_EXISTS;
			}
		}
		return Printable.NO_SUCH_PAGE;
	}

	final public void setImages(ArrayList<Image> objLimageAL) {
		this.objGimageAL = objLimageAL;
	}

	final public Boolean showPrintJDialog() {

		final PrintService objLdefaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
		final PrintRequestAttributeSet objLprintRequestAttributeSet = new HashPrintRequestAttributeSet();

		if (this.objGprintServiceA.length > 0) {
			int intLdefaultServiceIndex = 0;
			if (objLdefaultPrintService != null) {
				for (intLdefaultServiceIndex = 0; intLdefaultServiceIndex < this.objGprintServiceA.length; intLdefaultServiceIndex++) {
					if (this.objGprintServiceA[intLdefaultServiceIndex].equals(objLdefaultPrintService)) {
						break;
					}
				}
			}
			if (intLdefaultServiceIndex == this.objGprintServiceA.length) {
				intLdefaultServiceIndex = 0;
			}

			ServiceUI.printDialog(	null,
									50,
									50,
									this.objGprintServiceA,
									this.objGprintServiceA[intLdefaultServiceIndex],
									null,
									objLprintRequestAttributeSet);

			final PrintJDialog objLprintJDialog = new PrintJDialog(this.objGcontrolJFrame, this.objGprintServiceA, intLdefaultServiceIndex);
			objLprintJDialog.setVisible();
			final PrintService objLprintService = objLprintJDialog.getPrintService();
			objLprintJDialog.dispose();

			if (objLprintService != null) {
				final DocPrintJob objLdocPrintJob = objLprintService.createPrintJob();
				final SimpleDoc objLsimpleDoc = new SimpleDoc(this, DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
				try {
					objLdocPrintJob.print(objLsimpleDoc, objLprintRequestAttributeSet);
					return new Boolean(true);
				} catch (final Throwable objPthrowable) {}
			} else {
				return null;
			}
		} else {
			new PopUpJDialog(	this.objGcontrolJFrame,
								Constants.bytS_UNCLASS_NO_VALUE,
								Constants.intS_FILE_ICON_ALERT,
								this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_PRINT_PATTERN),
								Tools.getLocaleString("dialog.noprintermsg"),
								null,
								true);
		}
		return new Boolean(false);
	}
}
