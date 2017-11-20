/*
 * @(#)SplashScreenJWindow.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JWindow;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class SplashScreenJWindow extends JWindow implements Runnable {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean		bolGcomments;

	private int					intGclosingDelay	= 0;

	private ExtendedJLabel		objGcommentsJLabel;

	private final ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPmessage
	 */
	public SplashScreenJWindow(ControlJFrame objPcontrolJFrame, String strPmessage) {
		this(objPcontrolJFrame, strPmessage, false);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPmessage
	 * @param bolPcomments
	 */
	public SplashScreenJWindow(ControlJFrame objPcontrolJFrame, String strPmessage, boolean bolPcomments) {

		// Set panel :
		this.objGcontrolJFrame = objPcontrolJFrame;
		final JPanel objLjPanel = new JPanel(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																											0,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											5,
																											5,
																											10,
																											bolPcomments ? 5 : 10,
																											10,
																											10);

		// Add icon :
		final ExtendedJButton objLjButton = new ExtendedJButton(this.objGcontrolJFrame, 0);
		objLjButton.setBackground(Color.WHITE);
		objLjButton.setContentAreaFilled(false);
		objLjButton.setBorderPainted(false);
		objLjButton.setEnabled(false);
		final ImageIcon icoL =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_PANEL,
																									Constants.bytS_UNCLASS_NO_VALUE,
																									"JuggleMasterPro");
		Tools.setIcons(objLjButton, icoL, icoL, icoL);
		objLjPanel.add(objLjButton, objLextendedGridBagConstraints);

		// Add message :
		final ExtendedJLabel objLmessageJLabel =
													new ExtendedJLabel(	this.objGcontrolJFrame,
																		Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_JUGGLE_MASTER_PRO),
																							"  -  ",
																							strPmessage,
																							Strings.strS_ELLIPSIS),
																		Color.WHITE);
		objLmessageJLabel.setFont(objLmessageJLabel.getFont().deriveFont(Font.BOLD).deriveFont(	Font.ITALIC,
																								this.objGcontrolJFrame.getFont().getSize2D() * 2.5F));
		objLmessageJLabel.setForeground(Color.BLACK);
		objLjPanel.add(objLmessageJLabel, objLextendedGridBagConstraints);

		// Add comments :
		this.bolGcomments = bolPcomments;
		if (bolPcomments) {
			objLextendedGridBagConstraints.setGridBounds(0, 1, 2, 1);
			objLextendedGridBagConstraints.setMargins(0, 10, 10, 10);
			objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0F, 0F);
			this.objGcommentsJLabel = new ExtendedJLabel(this.objGcontrolJFrame, Strings.strS_EMPTY, Color.WHITE);
			objLjPanel.add(this.objGcommentsJLabel, objLextendedGridBagConstraints);
		}

		// Locate :
		objLjPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		objLjPanel.setBackground(Color.WHITE);
		objLjPanel.setOpaque(true);
		this.add(objLjPanel);
		this.validate();
		this.pack();
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			this.setAlwaysOnTop(true);
		} catch (final Throwable objPthrowable) {}
		this.setVisible(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void run() {
		Tools.sleep(this.intGclosingDelay, "Error while deferrizing splash screen termination");
		this.setVisible(false);
		this.dispose();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPcommentLanguageIndex
	 */
	final public void setComment(int intPcommentLanguageIndex) {
		if (this.bolGcomments) {
			this.objGcommentsJLabel.setText(Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(intPcommentLanguageIndex),
																Strings.strS_ELLIPSIS));
			Tools.sleep(100);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setInvisible() {
		this.setInvisible(0);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPclosingDelay
	 */
	final public void setInvisible(int intPclosingDelay) {
		this.intGclosingDelay = intPclosingDelay;
		new Thread(this).start();
	}
}

/*
 * @(#)SplashScreenJWindow.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
