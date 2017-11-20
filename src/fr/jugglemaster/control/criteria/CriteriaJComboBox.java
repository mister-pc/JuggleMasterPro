package fr.jugglemaster.control.criteria;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

final public class CriteriaJComboBox extends JComboBox<String> implements Runnable, ComboBoxEditor, DocumentListener {

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final boolean			bolGsequence;

	private final boolean			bolGsynchro;

	private final CriteriaJDialog	objGcriteriaJDialog;

	private final JTextField		objGjTextField;

	private CriteriaJComboBox(CriteriaJDialog objPcriteriaJDialog, boolean bolPsequence, boolean bolPsynchro, int intPtooltipIndex) {

		this.objGcriteriaJDialog = objPcriteriaJDialog;
		this.bolGsequence = bolPsequence;
		this.bolGsynchro = bolPsynchro;
		// this.strGuncompleted = this.strGautoCompleting = Constants.strS_UNCLASS_EMPTY;
		this.objGjTextField = new JTextField();
		this.objGjTextField.setFont(this.objGcriteriaJDialog.getJugglePanel().getFont());
		this.objGjTextField.setOpaque(true);
		this.objGjTextField.setBorder(Constants.objS_GRAPHICS_EMPTY_BORDER);
		this.objGjTextField.setDocument(new CriteriaPlainDocument(this.bolGsequence, this.bolGsynchro));
		this.objGjTextField.getDocument().addDocumentListener(this);

		this.setFont(this.objGcriteriaJDialog.getJugglePanel().getFont());
		this.setOpaque(true);
		this.setEditable(true);
		this.setRenderer(Constants.objS_GRAPHICS_DEFAULT_RENDERER);
		this.setEditor(this);
		this.setToolTipText(this.objGcriteriaJDialog.objGcontrolJFrame.getLanguageString(intPtooltipIndex));
	}

	public CriteriaJComboBox(CriteriaJDialog objPcriteriaJDialog, boolean bolPsynchro, int intPtooltipIndex) {
		this(objPcriteriaJDialog, true, bolPsynchro, intPtooltipIndex);
	}

	public CriteriaJComboBox(CriteriaJDialog objPcriteriaJDialog, int intPtooltipIndex) {
		this(objPcriteriaJDialog, false, false, intPtooltipIndex);
	}

	@Override final public void changedUpdate(DocumentEvent objPdocumentEvent) {}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcriteriaJDialog.objGcontrolJFrame);
	}

	final public void doAddCurrentItem() {
		this.setEditableText(this.bolGsequence ? this.objGcriteriaJDialog.getSequencesString(this.bolGsynchro)
												: Strings.undoubleSpaceTrim(this.getEditableText()));
		final String strLtyped = this.getEditableText();
		final int intLitemsNumber = this.getItemCount();
		if (strLtyped.length() > 0) {
			boolean bolLcontainsText = false;
			final ArrayList<String> strLitemAL = new ArrayList<String>(intLitemsNumber + 1);
			for (int intLitemIndex = 0; intLitemIndex < intLitemsNumber; ++intLitemIndex) {
				final String strL = this.getItemAt(intLitemIndex).toString();
				bolLcontainsText |= strL.equals(strLtyped);
				strLitemAL.add(this.getItemAt(intLitemIndex).toString());
			}
			if (!bolLcontainsText) {
				if (!bolLcontainsText) {
					strLitemAL.add(strLtyped);
				}
				Collections.sort(strLitemAL);
				this.removeAllItems();
				for (final String strL : strLitemAL) {
					this.addItem(strL);
				}
				this.setSelectedItem(strLtyped);
			}
		}
	}

	final public String getEditableText() {
		return (String) this.getItem();
	}

	@Override final public Component getEditorComponent() {
		return this.objGjTextField;
	}

	@Override final public Object getItem() {
		// JuggleTools.verbose("on rentre dans getItem avec ", this.objGjTextField.getText());
		// JuggleTools.verbose("On retourne ", JuggleTools.undoubleSpaceTrim(this.objGjTextField.getText()));
		return Strings.undoubleSpaceTrim(this.objGjTextField.getText());
	}

	final public String getRawText() {
		return this.objGjTextField.getText();
	}

	@Override final public void insertUpdate(DocumentEvent objPdocumentEvent) {
		if (this.bolGsequence) {
			this.objGcriteriaJDialog.setSequences(this.bolGsynchro);
		}
		this.objGcriteriaJDialog.setClearable();
		this.objGcriteriaJDialog.setButtons();

		if (this.objGjTextField.isFocusOwner()) {
			new Thread(this).start();
		}
	}

	@Override final public void removeUpdate(DocumentEvent objPdocumentEvent) {
		this.insertUpdate(objPdocumentEvent);
	}

	@Override final public void run() {
		// this.doAutoComplete();
		Tools.debug("CriteriaJComboBox.run()");
		this.objGcriteriaJDialog.setAble();
		this.objGcriteriaJDialog.setClearable();
	}

	@Override final public void selectAll() {
		this.objGjTextField.selectAll();
	}

	final public void setBackground(boolean bolPalright) {
		this.objGjTextField.setBackground(bolPalright ? Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR : Color.RED);
	}

	final public void setEditableText(String strP) {
		this.objGjTextField.setText(strP);
	}

	@Override final public void setItem(Object objPobject) {
		this.objGjTextField.setText(objPobject != null ? objPobject.toString() : Strings.strS_EMPTY);
	}

	@Override final public String toString() {
		return this.getEditableText();
	}
}
