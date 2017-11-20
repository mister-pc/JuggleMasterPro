package fr.jugglemaster.control.data;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class DataJToggleButton extends JToggleButton implements ItemListener {

	public DataJToggleButton(DataJFrame objPdataJFrame, byte bytPtype) {
		this.objGdataJFrame = objPdataJFrame;
		this.objGcontrolJFrame = objPdataJFrame.objGcontrolJFrame;
		this.bytGtype = bytPtype;
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setText();
		this.setOpaque(true);
		this.setFocusable(false);
		this.addItemListener(this);
	}

	final public void doLoadImages() {

		int intLiconFileType = Constants.bytS_UNCLASS_NO_VALUE;
		switch (this.bytGtype) {
			case DataJToggleButton.bytS_CLOSE_WINDOW:
				intLiconFileType = Constants.intS_FILE_ICON_CLOSE_DATA_BW;
				break;
			case DataJToggleButton.bytS_CONTENT_ADJUSTMENT:
				intLiconFileType = Constants.intS_FILE_ICON_CONTENT_ADJUSTMENT_BW;
				break;
			case DataJToggleButton.bytS_WINDOW_ADJUSTMENT:
				intLiconFileType = Constants.intS_FILE_ICON_WINDOW_ADJUSTMENT_BW;
				break;
		}
		this.icoG = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFileType, 2);

		intLiconFileType = Constants.bytS_UNCLASS_NO_VALUE;
		switch (this.bytGtype) {
			case DataJToggleButton.bytS_CLOSE_WINDOW:
				intLiconFileType = Constants.intS_FILE_ICON_CLOSE_DATA;
				break;
			case DataJToggleButton.bytS_CONTENT_ADJUSTMENT:
				intLiconFileType = Constants.intS_FILE_ICON_CONTENT_ADJUSTMENT;
				break;
			case DataJToggleButton.bytS_WINDOW_ADJUSTMENT:
				intLiconFileType = Constants.intS_FILE_ICON_WINDOW_ADJUSTMENT;
				break;
		}
		this.icoGrollOver = this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(intLiconFileType, 2);
	}

	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {
		Tools.debug("DataJToggleButton.itemStateChanged()");
		switch (this.bytGtype) {
			case DataJToggleButton.bytS_CLOSE_WINDOW:
				this.objGdataJFrame.windowClosing(null);
				this.removeItemListener(this);
				this.setSelected(false);
				this.addItemListener(this);
				break;
			case DataJToggleButton.bytS_CONTENT_ADJUSTMENT:
			case DataJToggleButton.bytS_WINDOW_ADJUSTMENT:
				final boolean bolLselected = objPitemEvent.getStateChange() == ItemEvent.SELECTED;
				this.objGdataJFrame.doToggleJButtons(this.bytGtype, bolLselected);
				if (bolLselected) {
					this.objGdataJFrame.doAdjustFields(this.bytGtype == DataJToggleButton.bytS_WINDOW_ADJUSTMENT);
				}
				this.setText();
				break;
		}

	}

	final public void setImages() {
		Tools.setIcons(this, this.icoG, this.icoGrollOver);
	}

	final public void setText() {
		int intLlanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		int intLtooltipLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		final boolean bolLselected = this.isSelected();
		switch (this.bytGtype) {
			case DataJToggleButton.bytS_CLOSE_WINDOW:
				intLlanguageIndex = Language.intS_BUTTON_CLOSE;
				intLtooltipLanguageIndex = Language.intS_TOOLTIP_DO_NOT_DISPLAY_DATA;
				break;
			case DataJToggleButton.bytS_CONTENT_ADJUSTMENT:
				intLlanguageIndex = Language.intS_BUTTON_CONTENT_ADJUSTMENT;
				intLtooltipLanguageIndex =
											bolLselected ? Language.intS_TOOLTIP_DATA_NO_CONTENT_ADJUSTMENT
														: Language.intS_TOOLTIP_DATA_CONTENT_ADJUSTMENT;
				break;
			case DataJToggleButton.bytS_WINDOW_ADJUSTMENT:
				intLlanguageIndex = Language.intS_BUTTON_WINDOW_ADJUSTMENT;
				intLtooltipLanguageIndex =
											bolLselected ? Language.intS_TOOLTIP_DATA_NO_WINDOW_ADJUSTMENT
														: Language.intS_TOOLTIP_DATA_WINDOW_ADJUSTMENT;
				break;
		}
		this.setText(this.objGcontrolJFrame.getLanguageString(intLlanguageIndex));
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS)
																													? this.objGcontrolJFrame.getLanguageString(intLtooltipLanguageIndex)
																													: null);
	}

	final private byte			bytGtype;
	private ImageIcon			icoG;
	private ImageIcon			icoGrollOver;
	final private ControlJFrame	objGcontrolJFrame;

	private final DataJFrame	objGdataJFrame;

	final public static byte	bytS_CLOSE_WINDOW		= 0;

	final public static byte	bytS_CONTENT_ADJUSTMENT	= 1;

	final public static byte	bytS_WINDOW_ADJUSTMENT	= 2;

	final private static long	serialVersionUID		= Constants.lngS_ENGINE_VERSION_NUMBER;

}
