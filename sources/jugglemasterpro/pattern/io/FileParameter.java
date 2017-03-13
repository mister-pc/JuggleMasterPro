/*
 * @(#)PatternsParameter.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.pattern.io;

import java.util.ArrayList;

import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class FileParameter {

	final public static byte	bytS_PARAMETER_TYPE_BOOLEAN	= 0;

	final public static byte	bytS_PARAMETER_TYPE_BYTE	= 1;

	final public static byte	bytS_PARAMETER_TYPE_FLOAT	= 2;

	final public static byte	bytS_PARAMETER_TYPE_LOCAL	= 3;

	final public static byte	bytS_PARAMETER_TYPE_STRING	= 4;

	final public static byte	bytS_PARAMETER_TYPE_USER	= 5;

	@SuppressWarnings("unused")
	final private static long	serialVersionUID			= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final byte[]		bytGvalueTypeA;

	private final int			intGnameIndex;

	private final int[]			intGvalueIndexA;

	// final private int intGlineIndex;
	// final private int intGcursorIndex;
	// final private boolean bolGerror;
	private final Object[]		objGvalueA;

	private final String		strGname;

	private final String[]		strGvalueA;														// TODO : à jarter ce champ, qui va devenir inutile

	/**
	 * Constructs
	 * 
	 * @param strPname
	 * @param intPnameIndex
	 * @param strPvalue
	 * @param intPvalueIndex
	 */
	public FileParameter(String strPname, int intPnameIndex, String strPvalues, int intPvaluesIndex) {
		this.strGname = new String(strPname);
		this.intGnameIndex = intPnameIndex;
		final ArrayList<Object> objLtokenAL = Strings.getTokens(strPvalues, true);
		final int intLtokensNumber = objLtokenAL.size();
		if (intLtokensNumber > 0) {
			this.strGvalueA = new String[intLtokensNumber / 2];
			this.intGvalueIndexA = new int[intLtokensNumber / 2];
			this.objGvalueA = new Object[intLtokensNumber / 2];
			this.bytGvalueTypeA = new byte[intLtokensNumber / 2];

			for (int intLtokenIndex = 0; intLtokenIndex < intLtokensNumber; intLtokenIndex += 2) {
				this.strGvalueA[intLtokenIndex / 2] = (String) objLtokenAL.get(intLtokenIndex);
				this.intGvalueIndexA[intLtokenIndex / 2] = intPvaluesIndex + (Integer) objLtokenAL.get(intLtokenIndex + 1);
				this.objGvalueA[intLtokenIndex / 2] = Tools.getParameterValueType((String) objLtokenAL.get(intLtokenIndex));
				this.bytGvalueTypeA[intLtokenIndex / 2] =
															this.objGvalueA[intLtokenIndex / 2] instanceof Byte
																												? FileParameter.bytS_PARAMETER_TYPE_BYTE
																												: this.objGvalueA[intLtokenIndex / 2] instanceof Boolean
																																										? FileParameter.bytS_PARAMETER_TYPE_BOOLEAN
																																										: this.objGvalueA[intLtokenIndex / 2] instanceof Float
																																																								? FileParameter.bytS_PARAMETER_TYPE_FLOAT
																																																								: this.objGvalueA[intLtokenIndex / 2] instanceof Character
																																																																							? FileParameter.bytS_PARAMETER_TYPE_LOCAL
																																																																							: this.objGvalueA[intLtokenIndex / 2] instanceof Short
																																																																																					? FileParameter.bytS_PARAMETER_TYPE_USER
																																																																																					: FileParameter.bytS_PARAMETER_TYPE_STRING;
			}
		} else {
			this.strGvalueA = new String[] { Strings.strS_EMPTY };
			this.intGvalueIndexA = new int[] { intPvaluesIndex };
			this.objGvalueA = new Object[] { Strings.strS_EMPTY };
			this.bytGvalueTypeA = new byte[] { FileParameter.bytS_PARAMETER_TYPE_STRING };
		}
	}

	final public boolean getBooleanValue(int intPvalueIndex) {
		return 0 <= intPvalueIndex && intPvalueIndex < this.bytGvalueTypeA.length
				&& this.bytGvalueTypeA[intPvalueIndex] == FileParameter.bytS_PARAMETER_TYPE_BOOLEAN
																											? (Boolean) this.objGvalueA[intPvalueIndex]
																											: false;
	}

	final public byte getByteValue(int intPvalueIndex, byte bytPminimumValue, byte bytPmaximumValue) {
		return (byte) (0 <= intPvalueIndex
						&& intPvalueIndex < this.bytGvalueTypeA.length
						&& (this.bytGvalueTypeA[intPvalueIndex] == FileParameter.bytS_PARAMETER_TYPE_BYTE || this.bytGvalueTypeA[intPvalueIndex] == FileParameter.bytS_PARAMETER_TYPE_FLOAT)
																																																			? Math.max(	bytPminimumValue,
																																																						Math.min(	this.bytGvalueTypeA[intPvalueIndex] == FileParameter.bytS_PARAMETER_TYPE_BYTE
																																																																															? (Byte) this.objGvalueA[intPvalueIndex]
																																																																															: (Float) this.objGvalueA[intPvalueIndex],
																																																									bytPmaximumValue))
																																																			: Constants.bytS_UNCLASS_NO_VALUE);
	}

	final public boolean getLocalValue(int intPvalueIndex) {
		return 0 <= intPvalueIndex && intPvalueIndex < this.bytGvalueTypeA.length
				&& this.bytGvalueTypeA[intPvalueIndex] == FileParameter.bytS_PARAMETER_TYPE_LOCAL
																											? (Character) this.objGvalueA[intPvalueIndex] == 'L'
																											: false;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getName() {
		return this.strGname;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public int getNameIndex() {
		return this.intGnameIndex;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public String getString() {
		String strL = Strings.strS_EMPTY;
		for (int intLvalueIndex = 0; intLvalueIndex < this.strGvalueA.length; ++intLvalueIndex) {
			strL = Strings.doConcat(strL, this.getString(intLvalueIndex), ' ');
		}
		return strL;
	}

	final public String getString(int intPvalueIndex) {
		return this.strGvalueA[intPvalueIndex];
	}

	final public String getStringValue(int intPvalueIndex) {
		return 0 <= intPvalueIndex && intPvalueIndex < this.bytGvalueTypeA.length
				&& this.bytGvalueTypeA[intPvalueIndex] == FileParameter.bytS_PARAMETER_TYPE_STRING ? (String) this.objGvalueA[intPvalueIndex]
																											: Strings.strS_EMPTY;
	}

	final public int getValueIndex(int intPvalueIndex) {
		return this.intGvalueIndexA[intPvalueIndex];
	}

	final public String[] getValues() {
		return this.strGvalueA;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public int getValuesIndex() {
		return this.getValueIndex(0);
	}

	final public int getValuesNumber() {
		return this.objGvalueA.length;
	}

	final public byte getValueType(int intPvalueIndex) {
		return 0 <= intPvalueIndex && intPvalueIndex < this.bytGvalueTypeA.length ? this.bytGvalueTypeA[intPvalueIndex]
																					: Constants.bytS_UNCLASS_NO_VALUE;
	}
}

/*
 * @(#)PatternsParameter.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
