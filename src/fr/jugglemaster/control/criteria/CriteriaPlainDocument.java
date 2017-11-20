package fr.jugglemaster.control.criteria;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

final public class CriteriaPlainDocument extends PlainDocument {

	public CriteriaPlainDocument(boolean bolPsequence, boolean bolPsynchro) {
		this.bolGsequence = bolPsequence;
		this.bolGsynchro = bolPsynchro;
	}

	@Override final public void insertString(int intPposition, String strP, AttributeSet objPattributeSet) throws BadLocationException {

		Tools.debug("CriteriaPlainDocument.insertString(", intPposition, ", '", strP, "')");
		if (Tools.isEmpty(strP == null)) {
			return;
		}

		// String strLoriginal = this.objGcriteriaJComboBox.getRawText();
		// boolean bolLspaceBefore = intPposition == 0 || strLoriginal.charAt(intPposition - 1) == ' ';
		// boolean bolLspaceAfter = intPposition == strLoriginal.length()
		// ? false
		// : strLoriginal.charAt(intPposition) == ' ';
		// boolean bolLspace = bolLspaceBefore;

		final char[] chrLstringA = Strings.undoubleSpace(strP).toCharArray();
		final StringBuilder objLinsertedStringBuilder = new StringBuilder(chrLstringA.length);
		boolean bolLbeep = false;

		for (int intLindex = 0; intLindex < chrLstringA.length; ++intLindex) {
			// if (chrLstringA[intLindex] == ' ') {
			// bolLspace = true;
			// } else
			if (!this.bolGsequence || Tools.isSequenceChar(chrLstringA[intLindex], this.bolGsynchro)) {

				// if (bolLspace) {
				// if (!bolLspaceBefore) {
				// objLinsertedStringBuilder.append(' ');
				// bolLspaceBefore = false;
				// }
				// bolLspace = false;
				// }

				chrLstringA[intLindex] = Strings.getUpperCaseChar(chrLstringA[intLindex]);
				if (!this.bolGsequence) {
					chrLstringA[intLindex] = Character.toLowerCase(chrLstringA[intLindex]);
				}
				if (this.bolGsequence && this.bolGsynchro && chrLstringA[intLindex] == 'X') {
					chrLstringA[intLindex] = '×';
				}
				objLinsertedStringBuilder.append(chrLstringA[intLindex]);
			} else {
				bolLbeep = true;
			}
		}
		// if (bolLspace && !bolLspaceBefore && !bolLspaceAfter) {
		// objLinsertedStringBuilder.append(' ');
		// }

		final String strLinserted = objLinsertedStringBuilder.toString();
		if (strLinserted.length() > 0) {
			super.insertString(intPposition, strLinserted, objPattributeSet);
		}
		if (bolLbeep) {
			Constants.objS_GRAPHICS_TOOLKIT.beep();
		}
	}

	private final boolean		bolGsequence;

	private final boolean		bolGsynchro;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}
