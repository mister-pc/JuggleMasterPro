package jugglemasterpro.control.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.control.util.ExtendedJButton;
import jugglemasterpro.control.util.ExtendedJLabel;
import jugglemasterpro.pattern.BallsColors;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class ColorChooserDropDownJButton extends ExtendedJButton implements /* PopupMenuListener, */ActionListener, /*
																														 * WindowListener,
																														 * WindowFocusListener
																														 * ,
																														 */ChangeListener {

	public ColorChooserDropDownJButton(ControlJFrame objPcontrolJFrame, byte bytPlocalStringType) {

		super(objPcontrolJFrame);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bytGlocalStringType = Tools.getEnlightedStringType(bytPlocalStringType, true);
		this.validate();
		this.setPopUp();
		final Dimension objLdimension = new Dimension(ColorChooserDropDownJButton.intS_SIZE, ColorChooserDropDownJButton.intS_SIZE);
		this.setSize(objLdimension);
		this.setPreferredSize(objLdimension);
		this.setMinimumSize(objLdimension);
		this.setMaximumSize(objLdimension);
		this.addActionListener(this);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ColorChooserDropDownJButton.actionPerformed()");
		this.setPopUpVisible(!this.objGjWindow.isVisible());
	}

	final public void doLoadImages() {
		this.imgGdownB = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_DROP_DOWN_B, 0);
		this.imgGdownW = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_DROP_DOWN_W, 0);
		this.imgGupB = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_DROP_UP_B, 0);
		this.imgGupW = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_DROP_UP_W, 0);
	}

	final private Color getGammaColor() {
		switch (this.bytGlocalStringType) {
			case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
			case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
			case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:

				return Tools.getPenGammaColor(	this.objGcontrolJFrame.getControlString(Tools.getEnlightedStringType(	this.bytGlocalStringType,
																														this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT))),
												Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION));
			case Constants.bytS_STRING_LOCAL_COLORS:
				return Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR;
		}
		return null;
	}

	@Override final public void paintComponent(Graphics objPgraphics) {

		// Draw a colored rectangle in place of the button :
		final BufferedImage imgLbuffer =
											Constants.objS_GRAPHICS_CONFIGURATION.createCompatibleImage(ColorChooserDropDownJButton.intS_SIZE,
																										ColorChooserDropDownJButton.intS_SIZE,
																										Transparency.OPAQUE);
		final Graphics2D objLgraphics2D = (Graphics2D) imgLbuffer.getGraphics();
		final Color objLcolor = this.getGammaColor();
		objLgraphics2D.setColor(objLcolor);
		objLgraphics2D.fillRect(1, 1, ColorChooserDropDownJButton.intS_SIZE - 2, ColorChooserDropDownJButton.intS_SIZE - 2);
		if (this.isEnabled()) {
			final Image imgL =
								Tools.isLightColor(objLcolor) ? (this.objGjWindow.isVisible() ? this.imgGupB : this.imgGdownB)
																: (this.objGjWindow.isVisible() ? this.imgGupW : this.imgGdownW);
			if (imgL != null) {
				objLgraphics2D.drawImage(imgL, 0, 0, null);
			}
		}
		objLgraphics2D.setColor(this.isEnabled() ? Color.DARK_GRAY : Color.LIGHT_GRAY);
		objLgraphics2D.drawRect(0, 0, ColorChooserDropDownJButton.intS_SIZE, ColorChooserDropDownJButton.intS_SIZE);
		objLgraphics2D.dispose();
		objPgraphics.drawImage(imgLbuffer, 0, 0, null);
	}

	final private void setBallsColorsPopUp() {
		final JPanel objLjPanel = new JPanel(new GridBagLayout());
		objLjPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		objLjPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																											0,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											0,
																											0);
		for (byte bytLshadeIndex = 0; bytLshadeIndex < BallsColors.bytS_BALLS_COLORS_SHADES_NUMBER; ++bytLshadeIndex) {
			for (byte bytLcolorLetterIndex = Constants.bytS_UNCLASS_NO_VALUE; bytLcolorLetterIndex <= BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER; ++bytLcolorLetterIndex) {
				objLextendedGridBagConstraints.setGridLocation(GridBagConstraints.RELATIVE, BallsColors.bytS_BALLS_COLORS_SHADES_NUMBER
																							- bytLshadeIndex);
				switch (bytLshadeIndex) {
					case 0:
					case BallsColors.bytS_BALLS_COLORS_SHADE_MAXIMUM_VALUE:
						final ExtendedJLabel objLjLabel =
															new ExtendedJLabel(	this.objGcontrolJFrame,
																				bytLcolorLetterIndex != Constants.bytS_UNCLASS_NO_VALUE
																					&& bytLcolorLetterIndex != BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER
																																							? Character.toString(BallsColors.chrS_BALLS_COLORS_LETTER_A[bytLcolorLetterIndex])
																																							: null);
						if (0 <= bytLcolorLetterIndex && bytLcolorLetterIndex < BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER) {
							objLjLabel.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																																			? BallsColors.strS_BALLS_COLORS_LETTER_A[bytLcolorLetterIndex]
																																			: null);
						}
						objLjLabel.setFont(objLjLabel.getFont().deriveFont(8.0F));
						objLjPanel.add(objLjLabel, objLextendedGridBagConstraints);
						break;
					default:
						final boolean bolLrowHeader =
														bytLcolorLetterIndex == Constants.bytS_UNCLASS_NO_VALUE
															|| bytLcolorLetterIndex == BallsColors.bytS_BALLS_COLORS_LETTERS_NUMBER;
						final JComponent objLjComponent =
															bolLrowHeader
																			? new ExtendedJLabel(	this.objGcontrolJFrame,
																									Byte.toString(bytLshadeIndex))
																			: new BallColorJButton(	this.objGcontrolJFrame,
																									BallsColors.getLogicalColorValue(	bytLcolorLetterIndex,
																																		bytLshadeIndex,
																																		true));

						objLjComponent.setFont(this.objGcontrolJFrame.getFont().deriveFont(8.0F));
						objLjComponent.setOpaque(true);
						if (bolLrowHeader) {
							objLextendedGridBagConstraints.setMargins(	0,
																		0,
																		bytLcolorLetterIndex == Constants.bytS_UNCLASS_NO_VALUE ? 0 : 3,
																		bytLcolorLetterIndex == Constants.bytS_UNCLASS_NO_VALUE ? 3 : 0);
						}
						objLjPanel.add(objLjComponent, objLextendedGridBagConstraints);
						if (bolLrowHeader) {
							objLextendedGridBagConstraints.setMargins(0, 0, 0, 0);
						}
				}
			}
		}
		this.objGjWindow.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.objGjWindow.setLayout(new GridBagLayout());
		this.objGjWindow.add(objLjPanel, new ExtendedGridBagConstraints(1, 1, 1, 1, GridBagConstraints.CENTER, 5, 5, 0, 0, 0, 0));
	}

	final public void setPopUp() {

		if (this.objGjWindow != null) {
			this.objGjWindow.dispose();
		}
		this.objGjWindow = new JWindow(this.objGcontrolJFrame.getJuggleMasterPro().getFrame());

		switch (this.bytGlocalStringType) {
			case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
			case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
			case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
				this.objGjColorChooser = new JColorChooser();
				final AbstractColorChooserPanel[] objLabstractColorChooserPanelA = this.objGjColorChooser.getChooserPanels();
				for (final AbstractColorChooserPanel objLabstractColorChooserPanel : objLabstractColorChooserPanelA) {
					// if
					// (objLabstractColorChooserPanel.getClass().getName().equals("javax.swing.colorchooser.DefaultHSBChooserPanel"))
					// {
					if (objLabstractColorChooserPanel.getClass().getName().equals("javax.swing.colorchooser.DefaultSwatchChooserPanel")) {
						objLabstractColorChooserPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						this.objGjWindow.add(objLabstractColorChooserPanel);
						break;
					}
				}
				break;
			case Constants.bytS_STRING_LOCAL_COLORS:
				this.setBallsColorsPopUp();
				//$FALL-THROUGH$
			default:
				this.objGjColorChooser = null;
				break;
		}

		this.objGjWindow.pack();
		this.objGjWindow.setLocation(	(int) Constants.objS_GRAPHICS_TOOLKIT.getScreenSize().getWidth(),
										(int) Constants.objS_GRAPHICS_TOOLKIT.getScreenSize().getHeight());
	}

	final public void setPopUpVisible(boolean bolPvisible) {
		final boolean bolLvisible = this.objGjWindow.isVisible();
		if (bolLvisible ^ bolPvisible) {
			if (bolPvisible) {
				ColorActions.doHideColorsChoosers(this.objGcontrolJFrame, this);
				switch (this.bytGlocalStringType) {
					case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
					case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
					case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
						this.objGjColorChooser.setColor(this.getGammaColor());
						this.objGjColorChooser.getSelectionModel().addChangeListener(this);
				}
				try {
					this.objGjWindow.setLocation(	(int) Math.max(Constants.intS_GRAPHICS_SCREEN_X, this.getLocationOnScreen().getX() + 16
																										- this.objGjWindow.getWidth()),
													(int) Math.max(Constants.intS_GRAPHICS_SCREEN_Y, this.getLocationOnScreen().getY() + 16));
				} catch (final Throwable objPthrowable) {
					Tools.err("Error while getting color chooser drop-down button location on screen");
				}
			} else {
				switch (this.bytGlocalStringType) {
					case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
					case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
					case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
						this.objGjColorChooser.getSelectionModel().removeChangeListener(this);
				}
			}
			this.objGjWindow.setVisible(bolPvisible);
			this.repaint();
		}
	}

	final public void setToolTipText() {

		int intLtooltip = Constants.bytS_UNCLASS_NO_VALUE;
		if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)) {
			switch (this.bytGlocalStringType) {
				case Constants.bytS_STRING_LOCAL_SITESWAP_DAY:
					intLtooltip = Language.intS_TOOLTIP_SITESWAP_COLOR;
					break;
				case Constants.bytS_STRING_LOCAL_JUGGLER_DAY:
					intLtooltip = Language.intS_TOOLTIP_JUGGLER_COLOR;
					break;
				case Constants.bytS_STRING_LOCAL_COLORS:
					intLtooltip = Language.intS_TOOLTIP_COLORS;
					break;
				case Constants.bytS_STRING_LOCAL_BACKGROUND_DAY:
					intLtooltip = Language.intS_TOOLTIP_BACKGROUND_COLOR;
					break;
			}
		}
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
							&& intLtooltip != Constants.bytS_UNCLASS_NO_VALUE ? this.objGcontrolJFrame.getLanguageString(intLtooltip) : null);
	}

	@Override final public void stateChanged(ChangeEvent objPpenNotDropDownButtonChangeEvent) {
		ColorActions.doSelectNewBallColorButtonValue(this, this.objGcontrolJFrame, objPpenNotDropDownButtonChangeEvent == null);
	}

	final public byte			bytGlocalStringType;

	private Image				imgGdownB			= null;

	private Image				imgGdownW			= null;

	private Image				imgGupB				= null;

	private Image				imgGupW				= null;

	final private ControlJFrame	objGcontrolJFrame;

	JColorChooser				objGjColorChooser;

	// final public void setAlreadyDisplayed(boolean bolPalreadyDisplayed) {
	// this.bolGalreadyDisplayed = bolPalreadyDisplayed;
	// }

	private JWindow				objGjWindow;

	final private static int	intS_SIZE			= 16;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
