/*
 * @(#)PreferenceStringGlobalJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pref;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Language;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PreferenceStringGlobalJComboBox extends JComboBox<Object> implements ItemListener {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	byte						bytGfilterType;

	final ControlJFrame			objGcontrolJFrame;

	final PreferencesJDialog	objGpreferencesJDialog;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param objPpreferencesJDialog
	 * @param bytPfilterType
	 */
	public PreferenceStringGlobalJComboBox(ControlJFrame objPcontrolJFrame, PreferencesJDialog objPpreferencesJDialog, byte bytPfilterType) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGfilterType = bytPfilterType;
		switch (this.bytGfilterType) {
			case Constants.bytS_STRING_GLOBAL_LANGUAGE:
				this.addItem(new StringBuilder(this.objGpreferencesJDialog.getLanguageString(Language.intS_COMBOBOX_DEFAULT_VALUE)));
				for (int intLindex = 0; intLindex < this.objGcontrolJFrame.getLanguagesNumber(); ++intLindex) {
					this.addItem(this.objGcontrolJFrame.objGlanguageA[intLindex]/* .getPropertyValueString(JuggleLanguage.intS_LANGUAGE_NAME) */);
				}
				final int intLselectedIndex =
												this.objGcontrolJFrame.getLanguageIndex(this.objGpreferencesJDialog.strGstringGlobalAA[this.bytGfilterType][Constants.bytS_UNCLASS_CURRENT]);
				this.setSelectedIndex(intLselectedIndex + 1);
				break;
		}
		this.setMaximumRowCount(this.getItemCount());
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setRenderer(Constants.objS_GRAPHICS_DEFAULT_RENDERER);
		this.setFocusable(false);
		this.addItemListener(this);
	}

	final public int getWidestValueIndex() {
		int intLwidestValue = 0;
		int intLwidestValueIndex = Constants.bytS_UNCLASS_NO_VALUE;
		final int intLitemsNumber = this.getItemCount();
		for (int intLvalueIndex = 0; intLvalueIndex < intLitemsNumber; ++intLvalueIndex) {
			final int intLwidth = Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(this.getItemAt(intLvalueIndex).toString());
			if (intLwidth > intLwidestValue) {
				intLwidestValue = intLwidth;
				intLwidestValueIndex = intLvalueIndex;
			}
		}
		return intLwidestValueIndex;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		Tools.debug("PreferenceStringGlobalJComboBox.itemStateChanged()");
		if (objPitemEvent.getStateChange() == ItemEvent.SELECTED) {
			final Object objLselectedObject = this.getSelectedItem();
			if (objLselectedObject != null) {
				this.objGpreferencesJDialog.doApplyStringGlobalComboChange(this.bytGfilterType, objLselectedObject);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPindex
	 */
	final public void selectIndex(int intPselectedIndex) {
		this.removeItemListener(this);
		super.setSelectedIndex(intPselectedIndex);
		this.addItemListener(this);
	}

	final public void selectItem(Object objPselectedObject) {
		this.removeItemListener(this);
		super.setSelectedItem(objPselectedObject);
		this.addItemListener(this);
	}

	final public void setList() {
		switch (this.bytGfilterType) {
			case Constants.bytS_STRING_GLOBAL_LANGUAGE:
				final StringBuilder objLstringBuilder = (StringBuilder) this.getItemAt(0);
				objLstringBuilder.setLength(0);
				objLstringBuilder.append(this.objGpreferencesJDialog.getLanguageString(Language.intS_COMBOBOX_DEFAULT_VALUE));
				break;
		}
	}
}

/*
 * @(#)PreferencebytGlobalJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
