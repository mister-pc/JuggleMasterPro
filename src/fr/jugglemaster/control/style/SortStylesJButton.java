/*
 * @(#)SortStylesJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.style;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class SortStylesJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public SortStylesJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addActionListener(this);
		this.setTransferHandler(new ExtendedTransferHandler(objPcontrolJFrame, false, true));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("SortStylesJButton.actionPerformed()");
		new SortStylesThread(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.icoGsortStyles = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_SORT_STYLES, 2, "Sort");

		this.icoGsortStylesBW = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_SORT_STYLES_BW, 1, "Sort");

		this.icoGdoNotSortStyles = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DO_NOT_SORT_STYLES, 0, "DoNot");

		this.icoGreverseSortStyles =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_REVERSE_SORT_STYLES,
																									2,
																									"Sort");

		this.icoGreverseSortStylesBW =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_REVERSE_SORT_STYLES_BW,
																									1,
																									"Sort");

		this.icoGdoNotReverseSortStyles =
											this.objGcontrolJFrame	.getJuggleMasterPro()
																	.getImageIcon(Constants.intS_FILE_ICON_DO_NOT_REVERSE_SORT_STYLES, 0, "DoNot");
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPascending
	 */
	final public void setImage(boolean bolPascending) {
		Tools.setIcons(	this,
						bolPascending ? this.icoGsortStylesBW : this.icoGreverseSortStylesBW,
						bolPascending ? this.icoGsortStyles : this.icoGreverseSortStyles,
						bolPascending ? this.icoGdoNotSortStyles : this.icoGdoNotReverseSortStyles);
	}

	final public void setToolTipText() {
		final boolean bolLedition = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && bolLedition
																																	? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_SORT_STYLES)
																																	: null);
	}

	ImageIcon					icoGdoNotReverseSortStyles;
	ImageIcon					icoGdoNotSortStyles;
	ImageIcon					icoGreverseSortStyles;
	ImageIcon					icoGreverseSortStylesBW;

	ImageIcon					icoGsortStyles;

	ImageIcon					icoGsortStylesBW;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)SortStylesJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
