package jugglemasterpro.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.Locale;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

final public class PrintMarginsSubJPanel extends JPanel implements ActionListener, FocusListener {

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private float						fltGbottomMargin;

	private float						fltGleftMargin;

	private float						fltGrightMargin;

	private float						fltGtopMargin;

	private final int					intGunits;

	private final JLabel				objGbottomJLabel;

	private Float						objGbottomMarginFloat;
	private final JFormattedTextField	objGbottomMarginJFormattedTextField;
	private final JLabel				objGleftJLabel;
	private Float						objGleftMarginFloat;
	private final JFormattedTextField	objGleftMarginJFormattedTextField;
	private final PrintJDialog			objGprintJDialog;
	private final JLabel				objGrightJLabel;
	private Float						objGrightMarginFloat;
	private final JFormattedTextField	objGrightMarginJFormattedTextField;
	private final JLabel				objGtopJLabel;
	private Float						objGtopMarginFloat;
	private final JFormattedTextField	objGtopMarginJFormattedTextField;

	public PrintMarginsSubJPanel(PrintJDialog objPprintJDialog) {

		// Init properties :
		this.objGprintJDialog = objPprintJDialog;
		this.fltGleftMargin = this.fltGrightMargin = this.fltGtopMargin = this.fltGbottomMargin = -1.0F;

		final String strLdefaultCountry = Locale.getDefault().getCountry();
		final boolean bolLamericanLocale =
											strLdefaultCountry != null
												&& (strLdefaultCountry.equals(Locale.US.getCountry()) || strLdefaultCountry.equals(Locale.CANADA.getCountry()));
		this.intGunits = bolLamericanLocale ? MediaPrintableArea.INCH : MediaPrintableArea.MM;

		final DecimalFormat objLdecimalFormat = new DecimalFormat(bolLamericanLocale ? "##.##" : "###.##");
		objLdecimalFormat.setMinimumIntegerDigits(1);
		objLdecimalFormat.setMaximumIntegerDigits(bolLamericanLocale ? 2 : 3);
		objLdecimalFormat.setParseIntegerOnly(false);
		objLdecimalFormat.setDecimalSeparatorAlwaysShown(true);
		objLdecimalFormat.setMinimumFractionDigits(1);
		objLdecimalFormat.setMaximumFractionDigits(2);
		final NumberFormatter objLnumberFormatter = new NumberFormatter(objLdecimalFormat);
		objLnumberFormatter.setMinimum(new Float(0.0F));
		objLnumberFormatter.setMaximum(new Float(999.0F));
		objLnumberFormatter.setAllowsInvalid(true);
		objLnumberFormatter.setCommitsOnValidEdit(true);

		String strLunits = Tools.getLocaleString(bolLamericanLocale ? "label.inches" : "label.millimetres");
		final int intLlength = strLunits.length();
		if (intLlength > 2) {
			if (strLunits.charAt(0) == '(' && strLunits.charAt(intLlength - 1) == ')') {
				strLunits = strLunits.substring(1, intLlength - 1);
			}
		}
		final String strLdots = " :";

		// Left margin :
		this.objGleftMarginJFormattedTextField = new JFormattedTextField(objLnumberFormatter);
		this.objGleftMarginJFormattedTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGleftMarginJFormattedTextField.setOpaque(true);
		this.objGleftMarginJFormattedTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString("label.leftmargin"));
		this.objGleftMarginJFormattedTextField.addFocusListener(this);
		this.objGleftMarginJFormattedTextField.addActionListener(this);
		this.objGleftJLabel =
								new JLabel(	Strings.doConcat(Tools.getLocaleString("label.leftmargin"), strLdots),
											SwingConstants.LEADING);
		this.objGleftJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGleftJLabel.setOpaque(true);
		this.objGleftJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.leftmargin"));
		this.objGleftJLabel.setLabelFor(this.objGleftMarginJFormattedTextField);

		// Right margin :
		this.objGrightMarginJFormattedTextField = new JFormattedTextField(objLnumberFormatter);
		this.objGrightMarginJFormattedTextField.setOpaque(true);
		this.objGrightMarginJFormattedTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGrightMarginJFormattedTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString("label.rightmargin"));
		this.objGrightMarginJFormattedTextField.addFocusListener(this);
		this.objGrightMarginJFormattedTextField.addActionListener(this);
		this.objGrightJLabel =
								new JLabel(	Strings.doConcat(Tools.getLocaleString("label.rightmargin"), strLdots),
											SwingConstants.LEADING);
		this.objGrightJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGrightJLabel.setOpaque(true);
		this.objGrightJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.rightmargin"));
		this.objGrightJLabel.setLabelFor(this.objGrightMarginJFormattedTextField);

		// Top margin :
		this.objGtopMarginJFormattedTextField = new JFormattedTextField(objLnumberFormatter);
		this.objGtopMarginJFormattedTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGtopMarginJFormattedTextField.setOpaque(true);
		this.objGtopMarginJFormattedTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString("label.topmargin"));
		this.objGtopMarginJFormattedTextField.addFocusListener(this);
		this.objGtopMarginJFormattedTextField.addActionListener(this);
		this.objGtopJLabel =
								new JLabel(	Strings.doConcat(Tools.getLocaleString("label.topmargin"), strLdots),
											SwingConstants.LEADING);
		this.objGtopJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGtopJLabel.setOpaque(true);
		this.objGtopJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.topmargin"));
		this.objGtopJLabel.setLabelFor(this.objGtopMarginJFormattedTextField);

		// Bottom margin :
		this.objGbottomMarginJFormattedTextField = new JFormattedTextField(objLnumberFormatter);
		this.objGbottomMarginJFormattedTextField.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGbottomMarginJFormattedTextField.setOpaque(true);
		this.objGbottomMarginJFormattedTextField.getAccessibleContext().setAccessibleName(Tools.getLocaleString("label.bottommargin"));
		this.objGbottomMarginJFormattedTextField.addFocusListener(this);
		this.objGbottomMarginJFormattedTextField.addActionListener(this);
		this.objGbottomJLabel =
								new JLabel(	Strings.doConcat(Tools.getLocaleString("label.bottommargin"), strLdots),
											SwingConstants.LEADING);
		this.objGbottomJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGbottomJLabel.setOpaque(true);
		this.objGbottomJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.bottommargin"));
		this.objGbottomJLabel.setLabelFor(this.objGbottomMarginJFormattedTextField);

		// Add widgets :
		this.setOpaque(true);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.margins"), this.objGprintJDialog.getControlJFrame().getFont()));
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																											0,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											0,
																											0,
																											2,
																											2,
																											GridBagConstraints.BOTH,
																											0.0F,
																											0.0F);
		final JPanel objLtopPanel = new JPanel(new GridBagLayout());
		objLtopPanel.setOpaque(true);
		objLtopPanel.add(this.objGtopJLabel, objLextendedGridBagConstraints);
		objLtopPanel.add(this.objGtopMarginJFormattedTextField, objLextendedGridBagConstraints);
		JLabel objLjLabel = new JLabel(strLunits);
		objLjLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		objLtopPanel.add(objLjLabel, objLextendedGridBagConstraints);
		final JPanel objLleftPanel = new JPanel(new GridBagLayout());
		objLleftPanel.setOpaque(true);
		objLleftPanel.add(this.objGleftJLabel, objLextendedGridBagConstraints);
		objLleftPanel.add(this.objGleftMarginJFormattedTextField, objLextendedGridBagConstraints);
		objLjLabel = new JLabel(strLunits);
		objLjLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		objLleftPanel.add(objLjLabel, objLextendedGridBagConstraints);
		final JPanel objLrightPanel = new JPanel(new GridBagLayout());
		objLrightPanel.setOpaque(true);
		objLrightPanel.add(this.objGrightJLabel, objLextendedGridBagConstraints);
		objLrightPanel.add(this.objGrightMarginJFormattedTextField, objLextendedGridBagConstraints);
		objLjLabel = new JLabel(strLunits);
		objLjLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		objLrightPanel.add(objLjLabel, objLextendedGridBagConstraints);
		final JPanel objLbottomPanel = new JPanel(new GridBagLayout());
		objLbottomPanel.setOpaque(true);
		objLbottomPanel.add(this.objGbottomJLabel, objLextendedGridBagConstraints);
		objLbottomPanel.add(this.objGbottomMarginJFormattedTextField, objLextendedGridBagConstraints);
		objLjLabel = new JLabel(strLunits);
		objLjLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		objLbottomPanel.add(objLjLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(0, 0, 2, 1);
		objLextendedGridBagConstraints.setMargins(3, 3, 6, 6);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.VERTICAL, 0.0F, 1.0F);
		this.add(objLtopPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(GridBagConstraints.RELATIVE, 1, 1, 1);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		this.add(objLleftPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		this.add(objLrightPanel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLextendedGridBagConstraints.setGridBounds(0, 2, 2, 1);
		this.add(objLbottomPanel, objLextendedGridBagConstraints);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		this.update(objPactionEvent.getSource());
	}

	@Override final public void focusGained(FocusEvent objPfocusEvent) {}

	@Override final public void focusLost(FocusEvent objPfocusEvent) {
		this.update(objPfocusEvent.getSource());
	}

	final public void setValues() {

		MediaPrintableArea objLmaxMediaPrintableArea = null;
		MediaSize objLmediaSize = null;
		Media objLmedia = (Media) this.objGprintJDialog.getAttributes().get(Media.class);

		if (objLmedia == null || !(objLmedia instanceof MediaSizeName)) {
			objLmedia = (Media) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(Media.class);
		}
		if (objLmedia != null && (objLmedia instanceof MediaSizeName)) {
			objLmediaSize = MediaSize.getMediaSizeForName((MediaSizeName) objLmedia);
		}
		if (objLmediaSize == null) {
			objLmediaSize = new MediaSize(8.5F, 11.0F, Size2DSyntax.INCH);
		}

		if (objLmedia != null) {
			final PrintRequestAttributeSet objLprintRequestAttributeSet = new HashPrintRequestAttributeSet(this.objGprintJDialog.getAttributes());
			objLprintRequestAttributeSet.add(objLmedia);

			final Object objLvaluesObject =
											this.objGprintJDialog.getPrintService().getSupportedAttributeValues(MediaPrintableArea.class,
																												null,
																												objLprintRequestAttributeSet);
			if (objLvaluesObject instanceof MediaPrintableArea[] && ((MediaPrintableArea[]) objLvaluesObject).length > 0) {
				objLmaxMediaPrintableArea = ((MediaPrintableArea[]) objLvaluesObject)[0];

			} else if (objLvaluesObject instanceof MediaPrintableArea) {
				objLmaxMediaPrintableArea = (MediaPrintableArea) objLvaluesObject;
			}
		}

		if (objLmaxMediaPrintableArea == null) {
			objLmaxMediaPrintableArea =
										new MediaPrintableArea(	0.0F,
																0.0F,
																objLmediaSize.getX(this.intGunits),
																objLmediaSize.getY(this.intGunits),
																this.intGunits);
		}

		float fltLwidth = objLmediaSize.getX(MediaPrintableArea.INCH);
		float fltLheight = objLmediaSize.getY(MediaPrintableArea.INCH);
		MediaPrintableArea objLmediaPrintableArea = (MediaPrintableArea) this.objGprintJDialog.getAttributes().get(MediaPrintableArea.class);
		if (objLmediaPrintableArea == null) {
			final float fltLmarginX = Math.min(fltLwidth / 5.0F, 1.0F);
			final float fltLmarginY = Math.min(fltLheight / 5.0F, 1.0F);
			objLmediaPrintableArea =
										new MediaPrintableArea(	fltLmarginX,
																fltLmarginY,
																fltLwidth - (2 * fltLmarginX),
																fltLheight - (2 * fltLmarginY),
																MediaPrintableArea.INCH);
			this.objGprintJDialog.getAttributes().add(objLmediaPrintableArea);
		}
		float fltLprintableAreaX = objLmediaPrintableArea.getX(this.intGunits);
		float fltLprintableAreaY = objLmediaPrintableArea.getY(this.intGunits);
		float fltLprintableAreaWidth = objLmediaPrintableArea.getWidth(this.intGunits);
		float fltLprintableAreaHeight = objLmediaPrintableArea.getHeight(this.intGunits);
		final float fltLmaxPrintableAreaX = objLmaxMediaPrintableArea.getX(this.intGunits);
		final float fltLmaxPrintableAreaY = objLmaxMediaPrintableArea.getY(this.intGunits);
		final float fltLmaxPrintableAreaWidth = objLmaxMediaPrintableArea.getWidth(this.intGunits);
		final float fltLmaxPrintableAreaHeight = objLmaxMediaPrintableArea.getHeight(this.intGunits);
		fltLwidth = objLmediaSize.getX(this.intGunits);
		fltLheight = objLmediaSize.getY(this.intGunits);

		// If the paper is set to something which is too small to
		// accommodate a specified printable area, perhaps carried
		// over from a larger paper, the adjustment that needs to be
		// performed should seem the most natural from a user's viewpoint.
		// Since the user is specifying margins, then we are biased
		// towards keeping the margins as close to what is specified as
		// possible, shrinking or growing the printable area.
		// But the API uses printable area, so you need to know the
		// media size in which the margins were previously interpreted,
		// or at least have a record of the margins.
		// In the case that this is the creation of this UI we do not
		// have this record, so we are somewhat reliant on the client
		// to supply a reasonable default

		boolean bolLinvalid =
								this.fltGleftMargin >= 0.0F || fltLprintableAreaX < fltLmaxPrintableAreaX
									|| fltLprintableAreaY < fltLmaxPrintableAreaY || fltLprintableAreaWidth > fltLmaxPrintableAreaWidth
									|| fltLprintableAreaHeight > fltLmaxPrintableAreaHeight;
		if (this.fltGleftMargin >= 0.0F) {
			if (this.fltGleftMargin + this.fltGrightMargin > fltLwidth) {
				fltLprintableAreaWidth = Math.min(fltLprintableAreaWidth, fltLmaxPrintableAreaWidth);
				fltLprintableAreaX = (fltLwidth - fltLprintableAreaWidth) / 2.0F;
			} else {
				fltLprintableAreaX = Math.max(this.fltGleftMargin, fltLmaxPrintableAreaX);
				fltLprintableAreaWidth = fltLwidth - fltLprintableAreaX - this.fltGrightMargin;
			}
			if (this.fltGtopMargin + this.fltGbottomMargin > fltLheight) {
				fltLprintableAreaHeight = Math.min(fltLprintableAreaHeight, fltLmaxPrintableAreaHeight);
				fltLprintableAreaY = (fltLheight - fltLprintableAreaHeight) / 2.0F;
			} else {
				fltLprintableAreaY = Math.max(this.fltGtopMargin, fltLmaxPrintableAreaY);
				fltLprintableAreaHeight = fltLheight - fltLprintableAreaY - this.fltGbottomMargin;
			}
		}
		fltLprintableAreaX = Math.max(fltLprintableAreaX, fltLmaxPrintableAreaX);
		fltLprintableAreaY = Math.max(fltLprintableAreaY, fltLmaxPrintableAreaY);
		fltLprintableAreaWidth = Math.min(fltLprintableAreaWidth, fltLmaxPrintableAreaWidth);
		fltLprintableAreaHeight = Math.min(fltLprintableAreaHeight, fltLmaxPrintableAreaHeight);

		if ((fltLprintableAreaX + fltLprintableAreaWidth > fltLmaxPrintableAreaX + fltLmaxPrintableAreaWidth) || (fltLprintableAreaWidth <= 0.0F)) {
			fltLprintableAreaX = fltLmaxPrintableAreaX;
			fltLprintableAreaWidth = fltLmaxPrintableAreaWidth;
			bolLinvalid = true;
		}
		if ((fltLprintableAreaY + fltLprintableAreaHeight > fltLmaxPrintableAreaY + fltLmaxPrintableAreaHeight) || (fltLprintableAreaHeight <= 0.0F)) {
			fltLprintableAreaY = fltLmaxPrintableAreaY;
			fltLprintableAreaHeight = fltLmaxPrintableAreaHeight;
			bolLinvalid = true;
		}

		if (bolLinvalid) {
			objLmediaPrintableArea =
										new MediaPrintableArea(	fltLprintableAreaX,
																fltLprintableAreaY,
																fltLprintableAreaWidth,
																fltLprintableAreaHeight,
																this.intGunits);
			this.objGprintJDialog.getAttributes().add(objLmediaPrintableArea);
		}

		/*
		 * We now have a valid printable area.
		 * Turn it into margins, using the mediaSize
		 */
		this.fltGleftMargin = fltLprintableAreaX;
		this.fltGtopMargin = fltLprintableAreaY;
		this.fltGrightMargin = objLmediaSize.getX(this.intGunits) - fltLprintableAreaX - fltLprintableAreaWidth;
		this.fltGbottomMargin = objLmediaSize.getY(this.intGunits) - fltLprintableAreaY - fltLprintableAreaHeight;

		this.objGleftMarginFloat = new Float(this.fltGleftMargin);
		this.objGrightMarginFloat = new Float(this.fltGrightMargin);
		this.objGtopMarginFloat = new Float(this.fltGtopMargin);
		this.objGbottomMarginFloat = new Float(this.fltGbottomMargin);

		OrientationRequested objLorientationRequested = (OrientationRequested) this.objGprintJDialog.getAttributes().get(OrientationRequested.class);

		if (objLorientationRequested == null) {
			objLorientationRequested =
										(OrientationRequested) this.objGprintJDialog.getPrintService()
																					.getDefaultAttributeValue(OrientationRequested.class);
		}

		if (objLorientationRequested == OrientationRequested.REVERSE_PORTRAIT) {
			Float objLvalueFloat = this.objGleftMarginFloat;
			this.objGleftMarginFloat = this.objGrightMarginFloat;
			this.objGrightMarginFloat = objLvalueFloat;
			objLvalueFloat = this.objGtopMarginFloat;
			this.objGtopMarginFloat = this.objGbottomMarginFloat;
			this.objGbottomMarginFloat = objLvalueFloat;
		} else if (objLorientationRequested == OrientationRequested.LANDSCAPE) {
			final Float objLvalueFloat = this.objGleftMarginFloat;
			this.objGleftMarginFloat = this.objGbottomMarginFloat;
			this.objGbottomMarginFloat = this.objGrightMarginFloat;
			this.objGrightMarginFloat = this.objGtopMarginFloat;
			this.objGtopMarginFloat = objLvalueFloat;
		} else if (objLorientationRequested == OrientationRequested.REVERSE_LANDSCAPE) {
			final Float objLvalueFloat = this.objGleftMarginFloat;
			this.objGleftMarginFloat = this.objGtopMarginFloat;
			this.objGtopMarginFloat = this.objGrightMarginFloat;
			this.objGrightMarginFloat = this.objGbottomMarginFloat;
			this.objGbottomMarginFloat = objLvalueFloat;
		}

		this.objGleftMarginJFormattedTextField.setValue(this.objGleftMarginFloat);
		this.objGrightMarginJFormattedTextField.setValue(this.objGrightMarginFloat);
		this.objGtopMarginJFormattedTextField.setValue(this.objGtopMarginFloat);
		this.objGbottomMarginJFormattedTextField.setValue(this.objGbottomMarginFloat);
	}

	final public void update(Object objPsourceObject) {
		if (objPsourceObject instanceof JFormattedTextField) {
			final JFormattedTextField objLsourceJFormattedTextField = (JFormattedTextField) objPsourceObject;
			final Float objLvalueFloat = (Float) objLsourceJFormattedTextField.getValue();
			if (objLvalueFloat == null || objLsourceJFormattedTextField == this.objGleftMarginJFormattedTextField
				&& objLvalueFloat.equals(this.objGleftMarginFloat) || objLsourceJFormattedTextField == this.objGrightMarginJFormattedTextField
				&& objLvalueFloat.equals(this.objGrightMarginFloat) || objLsourceJFormattedTextField == this.objGtopMarginJFormattedTextField
				&& objLvalueFloat.equals(this.objGtopMarginFloat) || objLsourceJFormattedTextField == this.objGbottomMarginJFormattedTextField
				&& objLvalueFloat.equals(this.objGbottomMarginFloat)) {
				return;
			}

			final Float objLleftMarginFloat = (Float) this.objGleftMarginJFormattedTextField.getValue();
			final Float objLrightMarginFloat = (Float) this.objGrightMarginJFormattedTextField.getValue();
			final Float objLtopMarginFloat = (Float) this.objGtopMarginJFormattedTextField.getValue();
			final Float objLbottomMarginFloat = (Float) this.objGbottomMarginJFormattedTextField.getValue();

			float fltLleftMargin = objLleftMarginFloat.floatValue();
			float fltLrightMargin = objLrightMarginFloat.floatValue();
			float fltLtopMargin = objLtopMarginFloat.floatValue();
			float fltLbottomMargin = objLbottomMarginFloat.floatValue();

			// Adjust for orientation :
			OrientationRequested objLorientationRequested =
															(OrientationRequested) this.objGprintJDialog.getAttributes()
																										.get(OrientationRequested.class);
			if (objLorientationRequested == null) {
				objLorientationRequested =
											(OrientationRequested) this.objGprintJDialog.getPrintService()
																						.getDefaultAttributeValue(OrientationRequested.class);
			}

			if (objLorientationRequested == OrientationRequested.REVERSE_PORTRAIT) {
				fltLleftMargin = fltLleftMargin - fltLrightMargin;
				fltLrightMargin = fltLleftMargin + fltLrightMargin;
				fltLleftMargin = fltLrightMargin - fltLleftMargin;
			} else if (objLorientationRequested == OrientationRequested.LANDSCAPE) {
				final float fltLvalue = fltLleftMargin;
				fltLleftMargin = fltLtopMargin;
				fltLtopMargin = fltLrightMargin;
				fltLrightMargin = fltLbottomMargin;
				fltLbottomMargin = fltLvalue;
			} else if (objLorientationRequested == OrientationRequested.REVERSE_LANDSCAPE) {
				final float fltLvalue = fltLleftMargin;
				fltLleftMargin = fltLbottomMargin;
				fltLbottomMargin = fltLrightMargin;
				fltLrightMargin = fltLtopMargin;
				fltLtopMargin = fltLvalue;
			}

			final MediaPrintableArea objLmediaPrintableArea = this.validate(fltLleftMargin, fltLrightMargin, fltLtopMargin, fltLbottomMargin);
			if (objLmediaPrintableArea != null) {
				this.objGprintJDialog.getAttributes().add(objLmediaPrintableArea);
				this.fltGleftMargin = fltLleftMargin;
				this.fltGrightMargin = fltLrightMargin;
				this.fltGtopMargin = fltLtopMargin;
				this.fltGbottomMargin = fltLbottomMargin;
				this.objGleftMarginFloat = objLleftMarginFloat;
				this.objGrightMarginFloat = objLrightMarginFloat;
				this.objGtopMarginFloat = objLtopMarginFloat;
				this.objGbottomMarginFloat = objLbottomMarginFloat;
			} else {
				if (this.objGleftMarginFloat != null && this.objGrightMarginFloat != null && this.objGtopMarginFloat != null
					&& this.objGrightMarginFloat != null) {
					this.objGleftMarginJFormattedTextField.setValue(this.objGleftMarginFloat);
					this.objGrightMarginJFormattedTextField.setValue(this.objGrightMarginFloat);
					this.objGtopMarginJFormattedTextField.setValue(this.objGtopMarginFloat);
					this.objGbottomMarginJFormattedTextField.setValue(this.objGbottomMarginFloat);

				}
			}
		}
	}

	/*
	 * This method either accepts the values and creates a new
	 * MediaPrintableArea, or does nothing.
	 * It should not attempt to create a printable area from anything
	 * other than the exact values passed in.
	 * But REMIND/TBD: it would be user friendly to replace margins the
	 * user entered but are out of bounds with the minimum.
	 * At that point this method will need to take responsibility for
	 * updating the "stored" values and the UI.
	 */
	final private MediaPrintableArea validate(float fltPleftMargin, float fltPrightMargin, float fltPtopMargin, float fltPbottomMargin) {

		if (fltPtopMargin >= 0.0F && fltPleftMargin >= 0.0F) {
			MediaSize objLmediaSize = null;
			Media objLmedia = (Media) this.objGprintJDialog.getAttributes().get(Media.class);
			if (objLmedia == null || !(objLmedia instanceof MediaSizeName)) {
				objLmedia = (Media) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(Media.class);
			}
			if (objLmedia != null && (objLmedia instanceof MediaSizeName)) {
				objLmediaSize = MediaSize.getMediaSizeForName((MediaSizeName) objLmedia);
			}
			if (objLmediaSize == null) {
				objLmediaSize = new MediaSize(8.5F, 11.0F, Size2DSyntax.INCH);
			}

			MediaPrintableArea objLmediaPrintableArea = null;
			if (objLmedia != null) {
				final PrintRequestAttributeSet objLprintRequestAttributeSet = new HashPrintRequestAttributeSet(this.objGprintJDialog.getAttributes());
				objLprintRequestAttributeSet.add(objLmedia);
				final Object objLvaluesObject =
												this.objGprintJDialog.getPrintService().getSupportedAttributeValues(MediaPrintableArea.class,
																													null,
																													objLprintRequestAttributeSet);
				if (objLvaluesObject instanceof MediaPrintableArea[] && ((MediaPrintableArea[]) objLvaluesObject).length > 0) {
					objLmediaPrintableArea = ((MediaPrintableArea[]) objLvaluesObject)[0];

				}
			}

			if (objLmediaPrintableArea == null) {
				objLmediaPrintableArea =
											new MediaPrintableArea(	0.0F,
																	0.0F,
																	objLmediaSize.getX(this.intGunits),
																	objLmediaSize.getY(this.intGunits),
																	this.intGunits);
			}

			final float fltLwidth = objLmediaSize.getX(this.intGunits) - fltPleftMargin - fltPrightMargin;
			final float fltLheight = objLmediaSize.getY(this.intGunits) - fltPtopMargin - fltPbottomMargin;

			if (fltPleftMargin >= objLmediaPrintableArea.getX(this.intGunits) && fltPtopMargin >= objLmediaPrintableArea.getY(this.intGunits)
				&& fltLwidth > 0.0F && fltLwidth <= objLmediaPrintableArea.getWidth(this.intGunits) && fltLheight > 0.0F
				&& fltLheight <= objLmediaPrintableArea.getHeight(this.intGunits)) {
				return new MediaPrintableArea(fltPleftMargin, fltPtopMargin, fltLwidth, fltLheight, this.intGunits);
			}
		}
		return null;
	}
}
