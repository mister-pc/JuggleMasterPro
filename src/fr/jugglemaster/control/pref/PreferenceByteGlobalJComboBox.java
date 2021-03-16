/*
 * @(#)PreferencebytGlobalJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.pref;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import fr.jugglemaster.control.ControlJFrame;
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
final public class PreferenceByteGlobalJComboBox extends JComboBox<Object> implements ItemListener {

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
	public PreferenceByteGlobalJComboBox(ControlJFrame objPcontrolJFrame, PreferencesJDialog objPpreferencesJDialog, byte bytPfilterType) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGpreferencesJDialog = objPpreferencesJDialog;
		this.bytGfilterType = bytPfilterType;
		switch (this.bytGfilterType) {
			case Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER:
			case Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER:
				for (byte bytLindex = Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE; bytLindex < Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MAXIMUM_VALUE; ++bytLindex) {
					this.addItem(Byte.valueOf(bytLindex).toString());
				}
				this.addItem(Strings.doConcat(Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MAXIMUM_VALUE, '+'));
				this.selectIndex(this.objGpreferencesJDialog.bytGbyteGlobalAA[this.bytGfilterType][Constants.bytS_UNCLASS_CURRENT] - 1);
				break;
			case Constants.bytS_BYTE_GLOBAL_LOW_MARK:
			case Constants.bytS_BYTE_GLOBAL_HIGH_MARK:
				for (byte bytLindex = Constants.bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE; bytLindex < Constants.bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE + 1; ++bytLindex) {
					this.addItem(Byte.valueOf((byte) (bytLindex + 1)).toString());
				}
				this.selectIndex(this.objGpreferencesJDialog.bytGbyteGlobalAA[this.bytGfilterType][Constants.bytS_UNCLASS_CURRENT]);
				break;
			case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
			case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
				for (final int intLskillLanguageIndex : Language.intS_LANGUAGE_SKILL_INDEX_A) {
					this.addItem(new StringBuilder(this.objGpreferencesJDialog.getLanguageString(intLskillLanguageIndex)));
				}
				this.selectIndex(this.objGpreferencesJDialog.bytGbyteGlobalAA[this.bytGfilterType][Constants.bytS_UNCLASS_CURRENT]);
				break;
		}
		this.setMaximumRowCount(this.getItemCount());
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setRenderer(Constants.objS_GRAPHICS_DEFAULT_RENDERER);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
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

		Tools.debug("PreferenceByteGlobalJComboBox.itemStateChanged()");
		// if (objPitemEvent.getStateChange() == ItemEvent.SELECTED) {
		this.objGpreferencesJDialog.doApplyByteGlobalComboChange(this.bytGfilterType, this.getSelectedIndex());
		// }
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPindex
	 */
	final public void selectIndex(int intPindex) {
		// if (this.bytGfilterType == Constants.bytS_BYTE_GLOBAL_HIGH_MARK) {
		// Tools.traces();
		// }
		this.removeItemListener(this);
		super.setSelectedIndex(intPindex);
		this.addItemListener(this);
	}

	final public void setList() {
		switch (this.bytGfilterType) {
			case Constants.bytS_BYTE_GLOBAL_LOW_SKILL:
			case Constants.bytS_BYTE_GLOBAL_HIGH_SKILL:
				for (int intLrowIndex = 0; intLrowIndex < Language.intS_LANGUAGE_SKILL_INDEX_A.length; ++intLrowIndex) {
					final StringBuilder objLstringBuilder = (StringBuilder) this.getItemAt(intLrowIndex);
					objLstringBuilder.setLength(0);
					objLstringBuilder.append(this.objGpreferencesJDialog.getLanguageString(Language.intS_LANGUAGE_SKILL_INDEX_A[intLrowIndex]));
				}
				break;
		}
	}
}

/*
 * @(#)PreferencebytGlobalJComboBox.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
