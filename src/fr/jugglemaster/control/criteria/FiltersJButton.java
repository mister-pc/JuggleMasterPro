/*
 * @(#)FiltersJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.criteria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import fr.jugglemaster.control.ControlJFrame;
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
final public class FiltersJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public FiltersJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {

		Tools.debug("FiltersJButton.actionPerformed()");
		if (this.objGcontrolJFrame.objGfiltersJDialog.isFiltering()) {
			this.objGcontrolJFrame.objGfiltersJDialog.doUnfilter();
			this.objGcontrolJFrame.objGfiltersJDialog.setVisible(false);
		} else {
			if (this.objGcontrolJFrame.objGfiltersJDialog.isAble()) {
				this.objGcontrolJFrame.objGfiltersJDialog.doFilter();
				this.objGcontrolJFrame.objGfiltersJDialog.setVisible(false);
			} else {
				if (this.objGcontrolJFrame.objGfiltersJDialog.isVisible()) {
					this.objGcontrolJFrame.objGfiltersJDialog.doUnfilter();
					this.objGcontrolJFrame.objGfiltersJDialog.setVisible(false);
				} else {
					this.objGcontrolJFrame.objGfiltersJDialog.setVisible(true);
				}
			}
		}
		this.objGcontrolJFrame.setFiltersControls();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.icoGbWFilter = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FILTER_BW, 1, "Filter");
		this.icoGfilter = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_FILTER, 2, "Filter");
		this.icoGdoNotFilter = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_DO_NOT_FILTER, 0, "DoNot");
	}

	final public void setImage(boolean bolPactivated) {
		Tools.setIcons(this, this.icoGbWFilter, bolPactivated ? this.icoGdoNotFilter : this.icoGfilter);
	}

	final public void setToolTipText() {
		final boolean bolLactivated = this.objGcontrolJFrame.objGfiltersJDialog.isFiltering();
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
							&& this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
																																			? this.objGcontrolJFrame.getLanguageString(bolLactivated
																																																	? Language.intS_TOOLTIP_DEACTIVATE_FILTERS
																																																	: this.objGcontrolJFrame.objGfiltersJDialog.isAble()
																																																														? Language.intS_TOOLTIP_APPLY_FILTERS
																																																														: this.objGcontrolJFrame.objGfiltersJDialog.isVisible()
																																																																												? Language.intS_TOOLTIP_DEACTIVATE_FILTERS
																																																																												: Language.intS_TOOLTIP_SET_FILTERS)
																																			: null);
	}

	private ImageIcon			icoGbWFilter;

	private ImageIcon			icoGdoNotFilter;

	private ImageIcon			icoGfilter;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)FiltersJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
