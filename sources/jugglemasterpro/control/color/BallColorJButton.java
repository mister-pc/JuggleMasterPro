package jugglemasterpro.control.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJButton;
import jugglemasterpro.pattern.BallsColors;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

final public class BallColorJButton extends ExtendedJButton implements ActionListener {

	public BallColorJButton(ControlJFrame objPcontrolJFrame, int intPcolorValue) {

		super(objPcontrolJFrame);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGlogicalBallColorValue = intPcolorValue;

		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																													? Strings.doConcat(	BallsColors.getBallColorString(	this.intGlogicalBallColorValue,
																																										true),
																																		" (",
																																		BallsColors.getBallColorHTMLString(	this.intGlogicalBallColorValue,
																																											true),
																																		')') : null);
		this.validate();
		final Dimension objLdimension = new Dimension(BallColorJButton.intS_SIZE, BallColorJButton.intS_SIZE);
		this.setSize(objLdimension);
		this.setPreferredSize(objLdimension);
		this.setMinimumSize(objLdimension);
		this.setMaximumSize(objLdimension);
		this.addActionListener(this);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("BallColorJButton.actionPerformed()");
		final BallsColorsJTextPane objLcolorsJTextPane = this.objGcontrolJFrame.objGcolorsJTextPane;
		final int intLstartSelection = objLcolorsJTextPane.getSelectionStart();
		final int intLendSelection = objLcolorsJTextPane.getSelectionEnd();
		final boolean bolLselection = (intLendSelection > intLstartSelection);
		if (bolLselection) {
			objLcolorsJTextPane.getColorsStyledDocument().remove(intLstartSelection, intLendSelection - intLstartSelection);
		}
		try {
			final String strLcolor = BallsColors.getBallColorString(this.intGlogicalBallColorValue, true);
			final int intLcaretStartPosition = objLcolorsJTextPane.getCaretPosition();
			final int intLcaretEndPosition = intLcaretStartPosition + strLcolor.length();
			objLcolorsJTextPane.getColorsStyledDocument().insertString(intLcaretStartPosition, strLcolor, null);
			objLcolorsJTextPane.setCaretPosition(intLcaretEndPosition);
			if (bolLselection) {
				objLcolorsJTextPane.setSelectionStart(intLcaretStartPosition);
				objLcolorsJTextPane.setSelectionEnd(intLcaretEndPosition);

			}
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while inserting ball color value");
		}
	}

	final private Color getGammaColor() {
		return BallsColors.getGammaBallColor(	this.intGlogicalBallColorValue,
												Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION),
												true,
												false);
	}

	@Override final public void paintComponent(Graphics objPgraphics) {

		// Draw a colored rectangle in place of the button :
		final BufferedImage imgLbuffer =
											Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(BallColorJButton.intS_SIZE,
																										BallColorJButton.intS_SIZE,
																										Transparency.OPAQUE);
		final Graphics2D objLgraphics2D = (Graphics2D) imgLbuffer.getGraphics();
		// objLgraphics2D.setBackground(JuggleBallsColors.objS_TRANSPARENT_COLOR);
		objLgraphics2D.setColor(this.getGammaColor());
		if (BallsColors.getBallColorLetterValue(this.intGlogicalBallColorValue) == BallsColors.bytS_BALLS_COLORS_LETTER_CRYSTAL) {
			final int intLsize = (BallColorJButton.intS_SIZE - 2) / 2;
			objLgraphics2D.fillRect(1, 1, intLsize, intLsize);
			objLgraphics2D.fillRect(intLsize + 1, intLsize + 1, intLsize, intLsize);
			objLgraphics2D.setColor(Color.GRAY);
			objLgraphics2D.fillRect(1, intLsize + 1, intLsize, intLsize);
			objLgraphics2D.fillRect(intLsize + 1, 1, intLsize, intLsize);
		} else {
			objLgraphics2D.fillRect(1, 1, BallColorJButton.intS_SIZE - 2, BallColorJButton.intS_SIZE - 2);
		}

		objLgraphics2D.setColor(Color.WHITE);
		objLgraphics2D.drawLine(0, 0, BallColorJButton.intS_SIZE, 0);
		objLgraphics2D.drawLine(0, 0, 0, BallColorJButton.intS_SIZE);

		objLgraphics2D.setColor(Color.GRAY);
		objLgraphics2D.drawLine(BallColorJButton.intS_SIZE, 0, BallColorJButton.intS_SIZE, BallColorJButton.intS_SIZE);
		objLgraphics2D.drawLine(0, BallColorJButton.intS_SIZE, BallColorJButton.intS_SIZE, BallColorJButton.intS_SIZE);
		objLgraphics2D.dispose();
		objPgraphics.drawImage(imgLbuffer, 0, 0, null);
	}

	final private int			intGlogicalBallColorValue;

	final private ControlJFrame	objGcontrolJFrame;

	final private static int	intS_SIZE			= 10;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
