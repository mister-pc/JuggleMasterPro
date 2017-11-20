package fr.jugglemaster.pattern.io;

import java.util.ArrayList;
import fr.jugglemaster.pattern.PatternsManager;
import fr.jugglemaster.pattern.Style;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

final public class PatternsFileStyleParser {

	public PatternsFileStyleParser(PatternsFileParser objPpatternsFileParser, PatternsManager objPpatternsManager) {
		this.objGpatternsFileParser = objPpatternsFileParser;
		this.objGpatternsManager = objPpatternsManager;
	}

	final private void doAddDefaultValues(int intPvaluesNumber) {
		// TODO : 4° coordonnée - doAddDefaultValues
		switch (intPvaluesNumber) {
			case 1:
			case 2:
			case 3:
				for (int intLvalueIndex = 0; intLvalueIndex < intPvaluesNumber; ++intLvalueIndex) {
					this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
				}
				this.objGbodyArmsZOrderAL.add(true);
				this.objGarmsHandsZOrderAL.add(true);
				this.objGbodyBallsZOrderAL.add(true);
				this.objGarmsBallsZOrderAL.add(true);
				this.objGarmsVisibleAL.add(true);
				this.objGhandsVisibleAL.add(true);
				this.objGballsVisibleAL.add(true);
				this.bolGfrontArms = true;
				this.bolGfrontBalls = true;
				this.bolGarmsFrontBalls = true;
				break;
			default:
				return;
		}
		this.bytGstate = PatternsFileStyleParser.bytS_CLOSE_SECTION_STATE;
	}

	final private void doAddToConsole(int intPlanguageIndex, boolean bolPwholeStyle, boolean bolPskipStyleDefinition) {
		if (bolPwholeStyle) {
			this.objGpatternsFileParser.doAddToConsole(	intPlanguageIndex,
														null,
														this.strGstyleNameLine,
														this.intGstyleNameLineIndex,
														this.intGstyleNameIndex);
		} else {
			this.objGpatternsFileParser.doAddToConsole(intPlanguageIndex, this.objGpatternsFile, this.intGcursorIndex);
		}
		if (bolPskipStyleDefinition) {
			// this.objGpatternsFile.doSkipStyleDefinition();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPpatternsBufferedReader
	 * @param strPstyle
	 * @param intPstyleNameIndex
	 * @return
	 */
	final public boolean doParseStyle(String strPstyle, int intPstyleNameIndex, PatternsFile objPpatternsFile) {

		this.objGpatternsFile = objPpatternsFile;
		this.initValues(intPstyleNameIndex);
		byte bytLhandsMinValueZ = (byte) 0;
		boolean bolLyValues = false;
		boolean bolLzValues = false;
		boolean bolLbackBalls = false;
		boolean bolLbackArms = false;
		boolean bolLarmsBackBalls = false;

		for (final String strLwrongStyle : this.objGpatternsManager.getPatternsFileManager().objGwrongStyleAL) {
			if (strLwrongStyle.equals(strPstyle)) {
				this.bolGwrongStyleRedefinition = true;
				break;
			}
		}

		while (this.objGpatternsFile.setNextLineIndex()) {

			this.strGfileLine = Strings.uncomment(this.objGpatternsFile.getLineString());
			final int intLfileLineLength = this.strGfileLine != null ? this.strGfileLine.length() : 0;
			final char chrLfirstChar = intLfileLineLength > 0 ? this.strGfileLine.charAt(0) : ' ';
			if ((chrLfirstChar != '{' && chrLfirstChar != '*')) {
				this.objGpatternsFile.setPreviousLineIndex();
				break;
			}

			if (chrLfirstChar == '*') {
				if (intLfileLineLength == 1) {
					if (this.doStarStyle()) {
						continue;
					}
					return false;
				}
				this.intGcursorIndex = this.strGfileLine.indexOf(this.strGfileLine.substring(1).trim().charAt(0), 1);
				this.doAddToConsole(Language.intS_CONSOLE_STYLE_WRONG_DEFINITION, true, false);
				return false;
			}

			// Now parsing '{x, y, *z**}{x, y, *z**} :
			this.bytGstate = PatternsFileStyleParser.bytS_OPEN_SECTION_STATE;
			byte bytLmode = PatternsFileStyleParser.bytS_CATCH_MODE;
			boolean bolLbodyArmsZOrder = true;
			boolean bolLarmsHandsZOrder = true;
			boolean bolLbodyBallsZOrder = true;
			boolean bolLarmsBallsZOrder = true;

			boolean bolLarmsVisible = true;
			boolean bolLhandsVisible = true;
			boolean bolLballsVisible = true;

			char chrLzPrefix = Strings.chrS_UNCLASS_NULL_CHAR;
			byte bytLvalueZ = (byte) 0;

			final char chrLfileLineA[] = this.strGfileLine.toCharArray();
			for (this.intGcursorIndex = 1; (this.intGcursorIndex < chrLfileLineA.length)
											&& (this.bytGstate != PatternsFileStyleParser.bytS_ERROR_STATE); ++this.intGcursorIndex) {
				// Tools.verbose("Caractère lu : '", chrLfileLineA[intLcursorIndex], "' - État : ", bytLstate);
				if (chrLfileLineA[this.intGcursorIndex] == ';') {
					break;
				}
				switch (this.bytGstate) {

					// '{' :
					case PatternsFileStyleParser.bytS_OPEN_SECTION_STATE:

						// '{ ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{,' :
						if (chrLfileLineA[this.intGcursorIndex] == ',') {
							this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
							this.bytGstate = PatternsFileStyleParser.bytS_SEPARATOR_AFTER_X_STATE;

							break;
						}

						// '{}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.doAddDefaultValues(3);
							break;
						}

						// '{x' :
						if (Tools.isNumberStart(chrLfileLineA[this.intGcursorIndex])) {
							int intLnumberEndIndex;

							for (intLnumberEndIndex = this.intGcursorIndex
														+ 1; (intLnumberEndIndex < chrLfileLineA.length)
																&& Tools.isDigit(chrLfileLineA[intLnumberEndIndex]); ++intLnumberEndIndex) {
								;
							}

							final Byte bytLstyleValueXByte = this.getByteValue(intLnumberEndIndex, strPstyle);
							if (bytLstyleValueXByte == null) {
								return false;
							}
							this.bytGstyleValueByteAL.add(bytLstyleValueXByte);
							this.intGcursorIndex = intLnumberEndIndex - 1;
							this.bytGstate = PatternsFileStyleParser.bytS_VALUE_X_STATE;

							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x' :
					case PatternsFileStyleParser.bytS_VALUE_X_STATE:

						// '{x ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							this.bytGstate = PatternsFileStyleParser.bytS_SPACE_AFTER_X_STATE;

							break;
						}

						// '{x,' :
						if (chrLfileLineA[this.intGcursorIndex] == ',') {
							this.bytGstate = PatternsFileStyleParser.bytS_SEPARATOR_AFTER_X_STATE;

							break;
						}

						// '{x}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.doAddDefaultValues(2);
							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x ' :
					case PatternsFileStyleParser.bytS_SPACE_AFTER_X_STATE:

						// '{x ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x ,' :
						if (chrLfileLineA[this.intGcursorIndex] == ',') {
							this.bytGstate = PatternsFileStyleParser.bytS_SEPARATOR_AFTER_X_STATE;

							break;
						}

						// '{x }' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.doAddDefaultValues(2);
							break;
						}

						// '{x y' :
						if (Tools.isNumberStart(chrLfileLineA[this.intGcursorIndex])) {
							int intLnumberEndIndex;

							for (intLnumberEndIndex = this.intGcursorIndex
														+ 1; (intLnumberEndIndex < chrLfileLineA.length)
																&& Tools.isDigit(chrLfileLineA[intLnumberEndIndex]); ++intLnumberEndIndex) {
								;
							}

							final Byte bytLstyleValueYByte = this.getByteValue(intLnumberEndIndex, strPstyle);
							if (bytLstyleValueYByte == null) {
								return false;
							}
							this.bytGstyleValueByteAL.add(bytLstyleValueYByte);
							bolLyValues |= (bytLstyleValueYByte != 0);
							this.intGcursorIndex = intLnumberEndIndex - 1;
							this.bytGstate = PatternsFileStyleParser.bytS_VALUE_Y_STATE;

							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x,' :
					case PatternsFileStyleParser.bytS_SEPARATOR_AFTER_X_STATE:

						// '{x, ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x,,' :
						if (chrLfileLineA[this.intGcursorIndex] == ',') {
							this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
							this.bytGstate = PatternsFileStyleParser.bytS_SEPARATOR_AFTER_Y_STATE;
							break;
						}

						// '{x,}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.doAddDefaultValues(2);
							break;
						}

						// '{x,y' :
						if (Tools.isNumberStart(chrLfileLineA[this.intGcursorIndex])) {
							int intLnumberEndIndex;

							for (intLnumberEndIndex = this.intGcursorIndex
														+ 1; (intLnumberEndIndex < chrLfileLineA.length)
																&& Tools.isDigit(chrLfileLineA[intLnumberEndIndex]); ++intLnumberEndIndex) {
								;
							}

							final Byte bytLstyleValueYByte = this.getByteValue(intLnumberEndIndex, strPstyle);
							if (bytLstyleValueYByte == null) {
								return false;
							}
							this.bytGstyleValueByteAL.add(bytLstyleValueYByte);
							bolLyValues |= (bytLstyleValueYByte != 0);
							this.intGcursorIndex = intLnumberEndIndex - 1;
							this.bytGstate = PatternsFileStyleParser.bytS_VALUE_Y_STATE;

							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x,y' :
					case PatternsFileStyleParser.bytS_VALUE_Y_STATE:

						// '{x,y ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							this.bytGstate = PatternsFileStyleParser.bytS_SPACE_AFTER_Y_STATE;

							break;
						}

						// '{x,y,' :
						if (chrLfileLineA[this.intGcursorIndex] == ',') {
							this.bytGstate = PatternsFileStyleParser.bytS_SEPARATOR_AFTER_Y_STATE;

							break;
						}

						// '{x,y}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.doAddDefaultValues(1);
							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x,y ' :
					case PatternsFileStyleParser.bytS_SPACE_AFTER_Y_STATE:

						// '{x,y ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x,y ±' :
						if (Tools.isZSuffix(chrLfileLineA[this.intGcursorIndex])) {
							if (this.intGcursorIndex + 1 < chrLfileLineA.length && Tools.isDigit(chrLfileLineA[this.intGcursorIndex + 1])) {
								int intLnumberEndIndex;
								chrLzPrefix = chrLfileLineA[this.intGcursorIndex];

								for (intLnumberEndIndex = this.intGcursorIndex
															+ 2; (intLnumberEndIndex < chrLfileLineA.length)
																	&& Tools.isDigit(chrLfileLineA[intLnumberEndIndex]); ++intLnumberEndIndex) {
									;
								}

								this.intGcursorIndex++;
								final Byte bytLstyleValueZByte = this.getByteValue(intLnumberEndIndex, strPstyle);
								if (bytLstyleValueZByte == null) {
									return false;
								}
								this.intGcursorIndex--;
								bytLvalueZ = (byte) (Tools.getSignedCharValue(chrLzPrefix) * bytLstyleValueZByte);

								this.bytGstyleValueByteAL.add(bytLvalueZ);
								bolLzValues = (bytLvalueZ != 0);
								this.intGcursorIndex = intLnumberEndIndex - 1;
								this.bytGstate = PatternsFileStyleParser.bytS_VALUE_Z_STATE;

							} else {

								this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
								this.bytGstate = PatternsFileStyleParser.bytS_HANDS_Z_ORDER_STATE;
								bytLvalueZ = 0;
								bolLbodyArmsZOrder = Tools.isFront(chrLfileLineA[this.intGcursorIndex]);
								bolLarmsHandsZOrder = (chrLfileLineA[this.intGcursorIndex] == '#' || chrLfileLineA[this.intGcursorIndex] == '-');
								bolLzValues |= chrLfileLineA[this.intGcursorIndex] != '#';
								this.bolGfrontArms |= bolLbodyArmsZOrder;
								bolLbackArms |= !bolLbodyArmsZOrder;
							}
							break;
						}

						// '{x,y ,' :
						if (chrLfileLineA[this.intGcursorIndex] == ',') {
							this.bytGstate = PatternsFileStyleParser.bytS_SEPARATOR_AFTER_Y_STATE;

							break;
						}

						// '{x,y }' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.doAddDefaultValues(1);
							break;
						}

						// '{x,y z' :
						if (Tools.isDigit(chrLfileLineA[this.intGcursorIndex])) {
							int intLnumberEndIndex;

							for (intLnumberEndIndex = this.intGcursorIndex
														+ 1; (intLnumberEndIndex < chrLfileLineA.length)
																&& Tools.isDigit(chrLfileLineA[intLnumberEndIndex]); ++intLnumberEndIndex) {
								;
							}

							final Byte bytLstyleValueZByte = this.getByteValue(intLnumberEndIndex, strPstyle);
							if (bytLstyleValueZByte == null) {
								return false;
							}
							this.bytGstyleValueByteAL.add(bytLstyleValueZByte);
							bytLvalueZ = bytLstyleValueZByte;
							bolLzValues = (bytLvalueZ != 0);
							this.intGcursorIndex = intLnumberEndIndex - 1;
							this.bytGstate = PatternsFileStyleParser.bytS_VALUE_Z_STATE;

							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x,y,' :
					case PatternsFileStyleParser.bytS_SEPARATOR_AFTER_Y_STATE:

						// '{x,y, ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x,y,±' :
						if (Tools.isZSuffix(chrLfileLineA[this.intGcursorIndex])) {
							if (this.intGcursorIndex + 1 < chrLfileLineA.length && Tools.isDigit(chrLfileLineA[this.intGcursorIndex + 1])) {
								int intLnumberEndIndex;
								chrLzPrefix = chrLfileLineA[this.intGcursorIndex];

								for (intLnumberEndIndex = this.intGcursorIndex
															+ 2; (intLnumberEndIndex < chrLfileLineA.length)
																	&& Tools.isDigit(chrLfileLineA[intLnumberEndIndex]); ++intLnumberEndIndex) {
									;
								}

								this.intGcursorIndex++;
								final Byte bytLstyleValueZByte = this.getByteValue(intLnumberEndIndex, strPstyle);
								if (bytLstyleValueZByte == null) {
									return false;
								}
								this.intGcursorIndex--;
								bytLvalueZ = (byte) (Tools.getSignedCharValue(chrLzPrefix) * bytLstyleValueZByte);
								this.bytGstyleValueByteAL.add(bytLvalueZ);
								bolLzValues = (bytLvalueZ != 0);
								this.intGcursorIndex = intLnumberEndIndex - 1;
								this.bytGstate = PatternsFileStyleParser.bytS_VALUE_Z_STATE;

							} else {
								this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
								this.bytGstate = PatternsFileStyleParser.bytS_HANDS_Z_ORDER_STATE;
								bytLvalueZ = 0;
								bolLbodyArmsZOrder = Tools.isFront(chrLfileLineA[this.intGcursorIndex]);
								bolLarmsHandsZOrder = Tools.isSubFront(chrLfileLineA[this.intGcursorIndex]);
								bolLzValues |= chrLfileLineA[this.intGcursorIndex] != '#';
								this.bolGfrontArms |= bolLbodyArmsZOrder;
								bolLbackArms |= !bolLbodyArmsZOrder;
							}
							break;
						}

						// '{x,y,}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.doAddDefaultValues(1);
							break;
						}

						// '{x,y,z' :
						if (Tools.isDigit(chrLfileLineA[this.intGcursorIndex])) {
							int intLnumberEndIndex;

							for (intLnumberEndIndex = this.intGcursorIndex + 1; (intLnumberEndIndex < chrLfileLineA.length)
																					&& (chrLfileLineA[intLnumberEndIndex] >= '0')
																				&& (chrLfileLineA[intLnumberEndIndex] <= '9'); ++intLnumberEndIndex) {
								;
							}

							final Byte bytLstyleValueZByte = this.getByteValue(intLnumberEndIndex, strPstyle);
							if (bytLstyleValueZByte == null) {
								return false;
							}

							this.bytGstyleValueByteAL.add(bytLstyleValueZByte);
							this.bytGstate = PatternsFileStyleParser.bytS_VALUE_Z_STATE;
							this.intGcursorIndex = intLnumberEndIndex - 1;
							bytLvalueZ = bytLstyleValueZByte;
							bolLzValues |= (bytLvalueZ != 0);

							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x,y,z' :
					case PatternsFileStyleParser.bytS_VALUE_Z_STATE:

						// '{x,y,z ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x,y,z±' :
						if (Tools.isZSuffix(chrLfileLineA[this.intGcursorIndex])) {
							bolLbodyArmsZOrder = Tools.isFront(chrLfileLineA[this.intGcursorIndex]);
							bolLarmsHandsZOrder = Tools.isSubFront(chrLfileLineA[this.intGcursorIndex]);
							bolLzValues |= (chrLfileLineA[this.intGcursorIndex] != '#');
							switch (chrLzPrefix) {
								case '=':
									bolLarmsVisible = !bolLbodyArmsZOrder;
									bolLhandsVisible = !bolLbodyArmsZOrder;
									break;
								case '-':
									bolLarmsVisible = !bolLbodyArmsZOrder;
									bolLarmsHandsZOrder = chrLfileLineA[this.intGcursorIndex] == '#';
									break;
								case '+':
									bolLarmsVisible = bolLbodyArmsZOrder;
									bolLarmsHandsZOrder = chrLfileLineA[this.intGcursorIndex] != '=';
									break;
								case '#':
									bolLarmsVisible = bolLbodyArmsZOrder;
									bolLhandsVisible = bolLarmsHandsZOrder;
									break;
							}
							if (!Tools.isFront(chrLfileLineA[this.intGcursorIndex])) {
								bytLhandsMinValueZ = (byte) Math.min(bytLhandsMinValueZ, -1 * Math.abs(bytLvalueZ));
							}
							this.bytGstate = PatternsFileStyleParser.bytS_HANDS_Z_ORDER_STATE;
							break;
						}

						// '{x,y,z}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.objGbodyArmsZOrderAL.add(bytLvalueZ >= 0);
							this.objGarmsHandsZOrderAL.add(true);
							this.objGbodyBallsZOrderAL.add(bytLvalueZ >= 0);
							this.objGarmsBallsZOrderAL.add(true);
							this.objGarmsVisibleAL.add(bolLarmsVisible);
							this.objGhandsVisibleAL.add(bolLhandsVisible);
							this.objGballsVisibleAL.add(bolLballsVisible);
							this.bolGfrontArms |= (bytLvalueZ >= 0);
							bolLbackArms |= (bytLvalueZ < 0);
							this.bolGfrontBalls |= (bytLvalueZ >= 0);
							bolLbackBalls |= (bytLvalueZ < 0);
							this.bolGarmsFrontBalls = true;
							bytLhandsMinValueZ = (byte) Math.min(bytLhandsMinValueZ, bytLvalueZ);
							this.bytGstate = PatternsFileStyleParser.bytS_CLOSE_SECTION_STATE;

							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					// '{x,y,z±' :
					case PatternsFileStyleParser.bytS_HANDS_Z_ORDER_STATE:

						// '{x,y,z± ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x,y,z±}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.objGbodyArmsZOrderAL.add(bolLbodyArmsZOrder);
							this.objGarmsHandsZOrderAL.add(bolLarmsHandsZOrder);
							this.objGbodyBallsZOrderAL.add(bytLvalueZ >= 0);
							this.objGarmsBallsZOrderAL.add(true);
							this.objGarmsVisibleAL.add(bolLarmsVisible);
							this.objGhandsVisibleAL.add(bolLhandsVisible);
							this.objGballsVisibleAL.add(bolLballsVisible);
							this.bolGfrontArms |= bolLbodyArmsZOrder && (bolLarmsVisible || bolLhandsVisible);
							bolLbackArms |= !bolLbodyArmsZOrder && (bolLarmsVisible || bolLhandsVisible);
							bolLbackBalls |= (bytLvalueZ < 0) && bolLballsVisible;
							this.bolGfrontBalls |= (bytLvalueZ >= 0) && bolLballsVisible;
							this.bolGarmsFrontBalls = true;
							this.bytGstate = PatternsFileStyleParser.bytS_CLOSE_SECTION_STATE;
							break;
						}

						// '{x,y,z±±' :
						if (Tools.isZSuffix(chrLfileLineA[this.intGcursorIndex])) {
							bolLbodyBallsZOrder = Tools.isFront(chrLfileLineA[this.intGcursorIndex]);
							bolLarmsBallsZOrder = Tools.isSubFront(chrLfileLineA[this.intGcursorIndex]);
							switch (chrLzPrefix) {
								case '=':
								case '-':
									bolLballsVisible = !bolLbodyBallsZOrder;
									break;

								case '+':
								case '#':
									bolLballsVisible = bolLbodyBallsZOrder;
									break;
							}
							bolLzValues |= (chrLfileLineA[this.intGcursorIndex] == '#');
							this.bytGstate = PatternsFileStyleParser.bytS_BALLS_T_ORDER_STATE;
							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;
						break;

					// '{x,y,z±±' :
					case PatternsFileStyleParser.bytS_BALLS_T_ORDER_STATE:

						// '{x,y,z±± ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x,y,z±±}' :
						if (chrLfileLineA[this.intGcursorIndex] == '}') {
							this.objGbodyArmsZOrderAL.add(bolLbodyArmsZOrder);
							this.objGarmsHandsZOrderAL.add(bolLarmsHandsZOrder);
							this.objGbodyBallsZOrderAL.add(bolLbodyBallsZOrder);
							this.objGarmsBallsZOrderAL.add(bolLarmsBallsZOrder);
							this.objGarmsVisibleAL.add(bolLarmsVisible);
							this.objGhandsVisibleAL.add(bolLhandsVisible);
							this.objGballsVisibleAL.add(bolLballsVisible);
							bolLbackArms |= !bolLbodyArmsZOrder && (bolLarmsVisible || bolLhandsVisible);
							this.bolGfrontArms |= bolLbodyArmsZOrder && (bolLarmsVisible || bolLhandsVisible);
							bolLbackBalls |= !bolLbodyBallsZOrder && bolLballsVisible;
							this.bolGfrontBalls |= bolLbodyBallsZOrder && bolLballsVisible;
							bolLarmsBackBalls |= !bolLarmsBallsZOrder && bolLballsVisible;
							this.bolGarmsFrontBalls |= bolLarmsBallsZOrder && bolLballsVisible;
							this.bytGstate = PatternsFileStyleParser.bytS_CLOSE_SECTION_STATE;
							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;
						break;

					// '{x,y,z±±}' :
					case PatternsFileStyleParser.bytS_CLOSE_SECTION_STATE:
						bolLbodyArmsZOrder = true;
						bolLarmsHandsZOrder = true;
						bolLbodyBallsZOrder = true;
						bolLarmsBallsZOrder = true;
						bolLarmsVisible = true;
						bolLhandsVisible = true;
						bolLballsVisible = true;
						chrLzPrefix = Strings.chrS_UNCLASS_NULL_CHAR;

						// '{x,y,z±±} ' :
						if (chrLfileLineA[this.intGcursorIndex] == ' ') {
							break;
						}

						// '{x,y,z±±}{' :
						if (chrLfileLineA[this.intGcursorIndex] == '{' && bytLmode == PatternsFileStyleParser.bytS_CATCH_MODE) {
							this.bytGstate = PatternsFileStyleParser.bytS_OPEN_SECTION_STATE;
							bytLmode = PatternsFileStyleParser.bytS_THROW_MODE;

							break;
						}

						--this.intGcursorIndex;
						this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;

						break;

					case PatternsFileStyleParser.bytS_ERROR_STATE:
						break;
				}
			}

			// If still in catch mode and catch definition not finished :
			if ((bytLmode == PatternsFileStyleParser.bytS_CATCH_MODE) && (this.bytGstate != PatternsFileStyleParser.bytS_CLOSE_SECTION_STATE)) {
				this.bytGstate = PatternsFileStyleParser.bytS_ERROR_STATE;
			}

			// Append default missing values :
			switch (this.bytGstate) {
				case PatternsFileStyleParser.bytS_OPEN_SECTION_STATE:
					this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
					//$FALL-THROUGH$
				case PatternsFileStyleParser.bytS_VALUE_X_STATE:
				case PatternsFileStyleParser.bytS_SPACE_AFTER_X_STATE:
				case PatternsFileStyleParser.bytS_SEPARATOR_AFTER_X_STATE:
					this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
					//$FALL-THROUGH$
				case PatternsFileStyleParser.bytS_VALUE_Y_STATE:
				case PatternsFileStyleParser.bytS_SPACE_AFTER_Y_STATE:
				case PatternsFileStyleParser.bytS_SEPARATOR_AFTER_Y_STATE:
					this.bytGstyleValueByteAL.add(PatternsFileStyleParser.bytS_NULL_Byte);
					this.bolGfrontArms = true;
					this.bolGfrontBalls = true;
					this.bolGarmsFrontBalls = true;
					//$FALL-THROUGH$
				case PatternsFileStyleParser.bytS_VALUE_Z_STATE:
					bolLbodyArmsZOrder = (bytLvalueZ >= 0);
					bolLarmsHandsZOrder = true;
					//$FALL-THROUGH$
				case PatternsFileStyleParser.bytS_HANDS_Z_ORDER_STATE:
					bolLbodyBallsZOrder = (bytLvalueZ >= 0);
					bolLarmsBallsZOrder = true;
					//$FALL-THROUGH$
				case PatternsFileStyleParser.bytS_BALLS_T_ORDER_STATE:
					this.objGbodyArmsZOrderAL.add(bolLbodyArmsZOrder);
					this.objGarmsHandsZOrderAL.add(bolLarmsHandsZOrder);
					this.objGbodyBallsZOrderAL.add(bolLbodyBallsZOrder);
					this.objGarmsBallsZOrderAL.add(bolLarmsBallsZOrder);
					this.objGarmsVisibleAL.add(bolLarmsVisible);
					this.objGhandsVisibleAL.add(bolLhandsVisible);
					this.objGballsVisibleAL.add(bolLballsVisible);
					bolLbackArms |= !bolLbodyArmsZOrder;
					this.bolGfrontArms |= bolLbodyArmsZOrder;
					bolLbackBalls |= !bolLbodyBallsZOrder;
					this.bolGfrontBalls |= bolLbodyBallsZOrder;
					bolLarmsBackBalls |= !bolLarmsBallsZOrder;
					this.bolGarmsFrontBalls |= bolLarmsBallsZOrder;
					break;

				case PatternsFileStyleParser.bytS_CLOSE_SECTION_STATE:
					if (bytLmode == PatternsFileStyleParser.bytS_CATCH_MODE) {
						this.doAddDefaultValues(3);
					}

					break;

				case PatternsFileStyleParser.bytS_ERROR_STATE:
					if (this.bolGwrongStyleRedefinition) {
						this.doAddToConsole(Language.intS_CONSOLE_STYLE_WRONG_REDEFINITION, false, true);
					} else {
						this.objGpatternsManager.getPatternsFileManager().objGwrongStyleAL.add(strPstyle);
					}
					this.doAddToConsole(Language.intS_CONSOLE_STYLE_WRONG_DEFINITION, true, false);
					return false;
			}
		}

		// Style with no definition :
		if (this.bytGstyleValueByteAL.size() == 0) {

			// 'Normal' style case :
			if (strPstyle.equals("Normal")) {
				// TODO 4° coordonnée
				@SuppressWarnings("unused") final Style objLstyle = new Style(	strPstyle,
																				Constants.bytS_ENGINE_COORDONATES_NUMBER == 4	? new byte[] {	13, 0, 0,
																																				0, 4,
																																				0, 0,
																																				0 }
																																: new byte[] {	13, 0,
																																				0, 4,
																																				0,
																																				0 },
																				(byte) 0,
																				new boolean[] { true, true },
																				new boolean[] { true, true },
																				new boolean[] { true, true },
																				new boolean[] { true, true },
																				new boolean[] { true, true },
																				new boolean[] { true, true },
																				new boolean[] { true, true },
																				false,
																				false,
																				false,
																				true,
																				false,
																				true,
																				false,
																				true);

				this.objGpatternsManager.objGstylesHashMap.put(strPstyle, objLstyle);
				this.objGpatternsManager.strGstyleAL.add(strPstyle);

				this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_STYLE, strPstyle);
				return true;
			}
			if (this.bolGwrongStyleRedefinition) {

				// Wrong style reference :
				this.doAddToConsole(Language.intS_CONSOLE_STYLE_WRONG_REFERENCE, false, true);
				return false;
			}
			if (this.objGpatternsManager.objGstylesHashMap.containsKey(strPstyle)) {

				// Good reference :
				this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_STYLE, strPstyle);
				return true;
			}
			// Bad reference :
			this.doAddToConsole(Language.intS_CONSOLE_STYLE_MISSING_DEFINITION, true, true);
			this.objGpatternsManager.getPatternsFileManager().objGwrongStyleAL.add(strPstyle);
			return false;
		}

		// New style good definition :
		if (!this.objGpatternsManager.objGstylesHashMap.containsKey(strPstyle)) {

			// Wrong style good redefinition :
			if (this.bolGwrongStyleRedefinition) {
				this.doAddToConsole(Language.intS_CONSOLE_STYLE_WRONG_REDEFINITION, false, true);
				this.objGpatternsManager.getPatternsFileManager().objGwrongStyleAL.remove(strPstyle);
			}

			// Add the new style name :
			this.objGpatternsManager.strGstyleAL.add(strPstyle);
		}

		// Add the new style definition :
		final byte[] bytLstyleValuesA = new byte[this.bytGstyleValueByteAL.size()];

		for (int intLstyleValueIndex = 0; intLstyleValueIndex < bytLstyleValuesA.length; ++intLstyleValueIndex) {
			bytLstyleValuesA[intLstyleValueIndex] = this.bytGstyleValueByteAL.get(intLstyleValueIndex).byteValue();
			// TODO 4° coordonnée
			if (intLstyleValueIndex % 3 == 2) {
				bytLstyleValuesA[intLstyleValueIndex] = (byte) Math.abs(bytLstyleValuesA[intLstyleValueIndex]);
			}
		}

		final boolean[] bolLbodyArmsZOrderA = new boolean[this.objGbodyArmsZOrderAL.size()];
		final boolean[] bolLarmsHandsZOrderA = new boolean[this.objGarmsHandsZOrderAL.size()];
		final boolean[] bolLbodyBallsZOrderA = new boolean[this.objGbodyBallsZOrderAL.size()];
		final boolean[] bolLarmsBallsZOrderA = new boolean[this.objGarmsBallsZOrderAL.size()];
		final boolean[] bolLarmsVisibleA = new boolean[this.objGarmsVisibleAL.size()];
		final boolean[] bolLhandsVisibleA = new boolean[this.objGhandsVisibleAL.size()];
		final boolean[] bolLballsVisibleA = new boolean[this.objGballsVisibleAL.size()];

		for (int intLzOrderValueIndex = 0; intLzOrderValueIndex < bolLarmsHandsZOrderA.length; ++intLzOrderValueIndex) {
			bolLbodyArmsZOrderA[intLzOrderValueIndex] = this.objGbodyArmsZOrderAL.get(intLzOrderValueIndex).booleanValue();
			bolLarmsHandsZOrderA[intLzOrderValueIndex] = this.objGarmsHandsZOrderAL.get(intLzOrderValueIndex).booleanValue();
			bolLbodyBallsZOrderA[intLzOrderValueIndex] = this.objGbodyBallsZOrderAL.get(intLzOrderValueIndex).booleanValue();
			bolLarmsBallsZOrderA[intLzOrderValueIndex] = this.objGarmsBallsZOrderAL.get(intLzOrderValueIndex).booleanValue();
			bolLarmsVisibleA[intLzOrderValueIndex] = this.objGarmsVisibleAL.get(intLzOrderValueIndex).booleanValue();
			bolLhandsVisibleA[intLzOrderValueIndex] = this.objGhandsVisibleAL.get(intLzOrderValueIndex).booleanValue();
			bolLballsVisibleA[intLzOrderValueIndex] = this.objGballsVisibleAL.get(intLzOrderValueIndex).booleanValue();
		}

		final Style objLstyle = new Style(	strPstyle,
											bytLstyleValuesA,
											bytLhandsMinValueZ,
											bolLbodyArmsZOrderA,
											bolLarmsVisibleA,
											bolLarmsHandsZOrderA,
											bolLhandsVisibleA,
											bolLbodyBallsZOrderA,
											bolLarmsBallsZOrderA,
											bolLballsVisibleA,
											bolLyValues,
											bolLzValues,
											bolLbackArms,
											this.bolGfrontArms,
											bolLbackBalls,
											this.bolGfrontBalls,
											bolLarmsBackBalls,
											this.bolGarmsFrontBalls);

		// Style already existing :
		if (this.objGpatternsManager.objGstylesHashMap.containsKey(strPstyle) && !objLstyle.equals(this.objGpatternsManager.getStyle(strPstyle))) {
			this.doAddToConsole(Language.intS_CONSOLE_STYLE_REDEFINITION, false, true);
		}

		this.objGpatternsManager.objGstylesHashMap.put(strPstyle, objLstyle);

		this.objGpatternsManager.setString(Constants.bytS_STRING_LOCAL_STYLE, strPstyle);
		return true;
	}

	final private boolean doStarStyle() {
		final boolean bolLemptyStyle = (this.bytGstyleValueByteAL.size() == 0);
		if (this.objGbodyArmsZOrderAL.size() % 4 > 0) {
			this.doAddToConsole(Language.intS_CONSOLE_STYLE_ODD_LINES_NUMBER_DUPLICATION, true, true);
			return false;
		}
		if (bolLemptyStyle) {
			this.doAddToConsole(Language.intS_CONSOLE_STYLE_EMPTY_DUPLICATION, true, false);
			return false;
		}

		for (int intLstyleLineIndex = 0, intLstyleLinesNumber = this.objGbodyArmsZOrderAL.size()
																/ 2; intLstyleLineIndex < intLstyleLinesNumber; intLstyleLineIndex += 2) {
			for (byte bytLstyleLineDelta = 0; bytLstyleLineDelta < 2; ++bytLstyleLineDelta) {
				for (byte bytLvalueIndex = 0; bytLvalueIndex < 2 * Constants.bytS_ENGINE_COORDONATES_NUMBER; ++bytLvalueIndex) {
					this.bytGstyleValueByteAL.add(this.bytGstyleValueByteAL.get((intLstyleLineIndex - bytLstyleLineDelta + 1)	* 2
																				* Constants.bytS_ENGINE_COORDONATES_NUMBER + bytLvalueIndex));
				}
				for (byte bytLcatchOrThrow = 0; bytLcatchOrThrow < 2; ++bytLcatchOrThrow) {
					final int intLbooleanIndex = (intLstyleLineIndex - bytLstyleLineDelta + 1) * 2 + bytLcatchOrThrow;
					this.objGbodyArmsZOrderAL.add(this.objGbodyArmsZOrderAL.get(intLbooleanIndex));
					this.objGarmsHandsZOrderAL.add(this.objGarmsHandsZOrderAL.get(intLbooleanIndex));
					this.objGbodyBallsZOrderAL.add(this.objGbodyBallsZOrderAL.get(intLbooleanIndex));
					this.objGarmsBallsZOrderAL.add(this.objGarmsBallsZOrderAL.get(intLbooleanIndex));
					this.objGarmsVisibleAL.add(this.objGarmsVisibleAL.get(intLbooleanIndex));
					this.objGhandsVisibleAL.add(this.objGhandsVisibleAL.get(intLbooleanIndex));
					this.objGballsVisibleAL.add(this.objGballsVisibleAL.get(intLbooleanIndex));
				}
			}
		}
		return true;
	}

	final private Byte getByteValue(int intPnumberEndIndex, String strPstyle) {

		Byte bytLstyleValueByte = null;
		try {
			bytLstyleValueByte = Byte.parseByte(this.strGfileLine.substring(this.intGcursorIndex, intPnumberEndIndex));
		} catch (final Throwable objPthrowable) {
			bytLstyleValueByte = null;
			if (this.bolGwrongStyleRedefinition) {
				this.doAddToConsole(Language.intS_CONSOLE_STYLE_WRONG_REDEFINITION, false, true);
			} else {
				this.objGpatternsManager.getPatternsFileManager().objGwrongStyleAL.add(strPstyle);
			}
			this.doAddToConsole(Language.intS_CONSOLE_STYLE_VALUES_DEFINITION, true, false);
			return null;
		}
		return bytLstyleValueByte;
	}

	final private void initValues(int intPstyleNameIndex) {

		this.intGstyleNameIndex = intPstyleNameIndex;
		this.strGstyleNameLine = new String(this.objGpatternsFile.getLineString());
		this.intGstyleNameLineIndex = this.objGpatternsFile.getLineIndex();
		this.bytGstyleValueByteAL = new ArrayList<Byte>(48);
		this.objGbodyArmsZOrderAL = new ArrayList<Boolean>(16);
		this.objGarmsHandsZOrderAL = new ArrayList<Boolean>(16);
		this.objGbodyBallsZOrderAL = new ArrayList<Boolean>(16);
		this.objGarmsBallsZOrderAL = new ArrayList<Boolean>(16);
		this.objGarmsVisibleAL = new ArrayList<Boolean>(16);
		this.objGhandsVisibleAL = new ArrayList<Boolean>(16);
		this.objGballsVisibleAL = new ArrayList<Boolean>(16);
		this.bytGstate = PatternsFileStyleParser.bytS_VALUE_Z_STATE;
		this.bolGfrontArms = false;
		this.bolGfrontBalls = false;
		this.bolGarmsFrontBalls = false;
		this.intGcursorIndex = 0;
		this.bolGwrongStyleRedefinition = false;
	}

	private boolean											bolGarmsFrontBalls;

	private boolean											bolGfrontArms;

	private boolean											bolGfrontBalls;
	private boolean											bolGwrongStyleRedefinition;
	private byte											bytGstate;
	private ArrayList<Byte>									bytGstyleValueByteAL;
	private int												intGcursorIndex;
	private int												intGstyleNameIndex;
	private int												intGstyleNameLineIndex;
	private ArrayList<Boolean>								objGarmsBallsZOrderAL;
	private ArrayList<Boolean>								objGarmsHandsZOrderAL;
	private ArrayList<Boolean>								objGarmsVisibleAL;
	private ArrayList<Boolean>								objGballsVisibleAL;
	private ArrayList<Boolean>								objGbodyArmsZOrderAL;
	private ArrayList<Boolean>								objGbodyBallsZOrderAL;
	private ArrayList<Boolean>								objGhandsVisibleAL;
	private PatternsFile									objGpatternsFile;
	final private PatternsFileParser						objGpatternsFileParser;
	final private PatternsManager							objGpatternsManager;

	private String											strGfileLine;

	private String											strGstyleNameLine;

	final private static byte								bytS_BALLS_T_ORDER_STATE		= 41;
	final private static byte								bytS_CATCH_MODE					= 0;
	final private static byte								bytS_CLOSE_SECTION_STATE		= 6;

	final private static byte								bytS_ERROR_STATE				= 0;

	final private static byte								bytS_HANDS_Z_ORDER_STATE		= 33;
	final private static Byte								bytS_NULL_Byte					= (byte) 0;
	final private static byte								bytS_OPEN_SECTION_STATE			= 5;
	final private static byte								bytS_SEPARATOR_AFTER_X_STATE	= 12;
	final private static byte								bytS_SEPARATOR_AFTER_Y_STATE	= 22;
	@SuppressWarnings("unused") final private static byte	bytS_SEPARATOR_AFTER_Z_STATE	= 32;

	final private static byte								bytS_SPACE_AFTER_X_STATE		= 11;

	final private static byte								bytS_SPACE_AFTER_Y_STATE		= 21;

	@SuppressWarnings("unused") final private static byte	bytS_SPACE_AFTER_Z_STATE		= 31;

	final private static byte								bytS_THROW_MODE					= 1;

	@SuppressWarnings("unused") final private static byte	bytS_VALUE_T_STATE				= 40;

	final private static byte								bytS_VALUE_X_STATE				= 10;

	final private static byte								bytS_VALUE_Y_STATE				= 20;

	final private static byte								bytS_VALUE_Z_STATE				= 30;

	@SuppressWarnings("unused") final private static long	serialVersionUID				= Constants.lngS_ENGINE_VERSION_NUMBER;

}
