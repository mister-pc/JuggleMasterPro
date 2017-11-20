/*
 * @(#)ObjectsJList.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.pattern;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JList;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.KeysAction;
import fr.jugglemaster.pattern.Pattern;
import fr.jugglemaster.pattern.PatternsManager;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ObjectsJList extends JList<Object> implements ListSelectionListener, KeyListener, MouseListener, MouseWheelListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ObjectsJList(ControlJFrame objPcontrolJFrame) {

		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGlastHeadPatternIndex = Constants.bytS_UNCLASS_NO_VALUE;

		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		this.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.resetDirection();
		this.setCellRenderer(new ObjectsJListCellRenderer(this.objGcontrolJFrame));
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KeyEvent.VK_ENTER);
		this.getActionMap().put(KeyEvent.VK_ENTER, new KeysAction(objPcontrolJFrame, KeysAction.bytS_OBJECTS_J_LIST, KeyEvent.VK_ENTER));
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), KeyEvent.VK_SPACE);
		this.getActionMap().put(KeyEvent.VK_SPACE, new KeysAction(objPcontrolJFrame, KeysAction.bytS_OBJECTS_J_LIST, KeyEvent.VK_SPACE));
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		this.addListSelectionListener(this);
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	final private void ensureIndexIsCentered(int intPindex) {

		final int intLrows = this.getLastVisibleIndex() - this.getFirstVisibleIndex() + 1;
		this.ensureIndexIsVisible(intPindex - intLrows / 2);
		this.ensureIndexIsVisible(intPindex + (intLrows - 1) / 2);
	}

	@Override final public void ensureIndexIsVisible(int intPindex) {
		final int intLindex = Math.min(Math.max(0, intPindex), this.intGfilteredObjectIndexA.length + 1);
		super.ensureIndexIsVisible(intLindex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public Object[] getFilteredObjectA() {

		Object[] objLfilteredObjectA = null;
		final int intLfilteredObjectsNumber = this.intGfilteredObjectIndexA.length;
		if (intLfilteredObjectsNumber > 0) {
			objLfilteredObjectA = new Object[intLfilteredObjectsNumber];
			for (int intLfilteredObjectIndex = 0; intLfilteredObjectIndex < intLfilteredObjectsNumber; ++intLfilteredObjectIndex) {
				objLfilteredObjectA[intLfilteredObjectIndex] =
																this.objGcontrolJFrame.getPatternsManager().objGobjectA[this.intGfilteredObjectIndexA[intLfilteredObjectIndex]];
			}
		}
		return objLfilteredObjectA;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @return
	 */
	final public int getFilteredObjectIndex(int intPobjectIndex) {

		final int intLfilteredObjectIndex = Arrays.binarySearch(this.intGfilteredObjectIndexA, intPobjectIndex);
		return intLfilteredObjectIndex >= 0 ? intLfilteredObjectIndex : Constants.bytS_UNCLASS_NO_VALUE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfilteredObjectIndex
	 * @return
	 */
	final public int getFilteredPatternIndex(int intPfilteredObjectIndex) {
		int intLfilteredPatternIndex = Math.min(Math.max(intPfilteredObjectIndex, 0), this.intGfilteredObjectIndexA.length - 1);

		// The selected line is a pattern :
		if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLfilteredPatternIndex])) {
			return intLfilteredPatternIndex;
		}

		// The selected line is a shortcut :
		if (this.objGcontrolJFrame.getPatternsManager().isShortcut(this.intGfilteredObjectIndexA[intLfilteredPatternIndex])
			&& this.bytGbrowsingDirection == ObjectsJList.bytS_NO_DIRECTION) {
			++intLfilteredPatternIndex;

		}

		switch (this.bytGbrowsingDirection) {
			case ObjectsJList.bytS_NO_DIRECTION:

				// Looking beetween two shortcuts :
				int intLupperShortcutFilteredIndex = Constants.bytS_UNCLASS_NO_VALUE;
				int intLlowerShortcutFilteredIndex = Constants.bytS_UNCLASS_NO_VALUE;
				for (int intLdelta = 1; intLupperShortcutFilteredIndex == Constants.bytS_UNCLASS_NO_VALUE
										|| intLlowerShortcutFilteredIndex == Constants.bytS_UNCLASS_NO_VALUE; ++intLdelta) {
					for (byte bytLsign = 1; bytLsign >= -1; bytLsign -= 2) {
						if (bytLsign > 0 && intLupperShortcutFilteredIndex == Constants.bytS_UNCLASS_NO_VALUE || bytLsign < 0
							&& intLlowerShortcutFilteredIndex == Constants.bytS_UNCLASS_NO_VALUE) {

							final int intLselectedFilteredPatternIndex = intLfilteredPatternIndex + bytLsign * intLdelta;

							// If filtered list limits are reached :
							if (intLselectedFilteredPatternIndex < 0) {
								intLlowerShortcutFilteredIndex = 0;
								continue;
							}
							if (intLselectedFilteredPatternIndex >= this.intGfilteredObjectIndexA.length) {
								intLupperShortcutFilteredIndex = this.intGfilteredObjectIndexA.length;
								continue;
							}

							// Pattern found :
							if (this.objGcontrolJFrame	.getPatternsManager()
														.isPattern(this.intGfilteredObjectIndexA[intLselectedFilteredPatternIndex])) {
								return intLselectedFilteredPatternIndex;

							}

							// Shortcut found :
							if (this.objGcontrolJFrame	.getPatternsManager()
														.isShortcut(this.intGfilteredObjectIndexA[intLselectedFilteredPatternIndex])) {
								if (bytLsign > 0) {
									intLupperShortcutFilteredIndex = intLselectedFilteredPatternIndex;
								} else {
									intLlowerShortcutFilteredIndex = intLselectedFilteredPatternIndex;
								}
							}
						}
					}
				}

				// Looking outside the shortcuts :
				for (int intLdelta =
										Math.min(intLfilteredPatternIndex - intLlowerShortcutFilteredIndex, intLupperShortcutFilteredIndex
																											- intLfilteredPatternIndex) + 1; intLfilteredPatternIndex
																																				- intLdelta >= 0
																																				|| intLfilteredPatternIndex
																																					+ intLdelta < this.intGfilteredObjectIndexA.length; ++intLdelta) {
					for (byte bytLsign = 1; bytLsign >= -1; bytLsign -= 2) {
						final int intLselectedFilteredPatternIndex = intLfilteredPatternIndex + bytLsign * intLdelta;

						// Pattern found :
						if (intLselectedFilteredPatternIndex >= 0
							&& intLselectedFilteredPatternIndex < this.intGfilteredObjectIndexA.length
							&& (bytLsign == 1 && intLselectedFilteredPatternIndex > intLupperShortcutFilteredIndex || bytLsign == -1
																														&& intLselectedFilteredPatternIndex < intLlowerShortcutFilteredIndex)) {
							if (this.objGcontrolJFrame	.getPatternsManager()
														.isPattern(this.intGfilteredObjectIndexA[intLselectedFilteredPatternIndex])) {
								return intLselectedFilteredPatternIndex;
							}
						}
					}
				}
				break;

			case ObjectsJList.bytS_DIRECTION_UP:
			case ObjectsJList.bytS_DIRECTION_DOWN:
				int intLdelta = 0;
				while (0 <= intLfilteredPatternIndex + intLdelta && intLfilteredPatternIndex + intLdelta <= this.intGfilteredObjectIndexA.length) {
					intLdelta += (this.bytGbrowsingDirection == ObjectsJList.bytS_DIRECTION_UP ? -1 : 1);

					if (intLfilteredPatternIndex + intLdelta >= 0 && intLfilteredPatternIndex + intLdelta < this.intGfilteredObjectIndexA.length
						&& this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLfilteredPatternIndex + intLdelta])) {
						return intLfilteredPatternIndex + intLdelta;
					}
				}
				intLdelta = 0;
				while (0 <= intLfilteredPatternIndex + intLdelta && intLfilteredPatternIndex + intLdelta <= this.intGfilteredObjectIndexA.length) {
					intLdelta += (this.bytGbrowsingDirection == ObjectsJList.bytS_DIRECTION_DOWN ? -1 : 1);

					if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLfilteredPatternIndex + intLdelta])) {

						return intLfilteredPatternIndex + intLdelta;
					}
				}
		}

		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPlastPatternIndex
	 * @return
	 */
	final public int getFilteredPatternRenewedIndex(int intPlastPatternIndex) {
		return this.getFilteredPatternRenewedIndex(intPlastPatternIndex, false);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPlastPatternIndex
	 * @param bolPstartPattern
	 * @return
	 */
	final public int getFilteredPatternRenewedIndex(int intPlastPatternIndex, boolean bolPstartPattern) {

		int intLfilteredPatternIndex = this.getFilteredObjectIndex(intPlastPatternIndex);

		// The pattern is still in the list :
		if (intLfilteredPatternIndex != Constants.bytS_UNCLASS_NO_VALUE
			&& this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLfilteredPatternIndex])) {
			return intLfilteredPatternIndex;
		}

		// Looking for the shortcut containing the last pattern :
		if (!bolPstartPattern) {
			int intLshortcutIndex = this.objGcontrolJFrame.getPatternsManager().getShortcutIndex(intPlastPatternIndex);
			if (this.objGcontrolJFrame.objGshortcutsJComboBox.isFilteredShortcut(intLshortcutIndex)) {
				final int intLstartObjectIndex = this.objGcontrolJFrame.getPatternsManager().getPatternsFileManager().intGstartObjectIndex;
				if (this.objGcontrolJFrame.getPatternsManager().getShortcutIndex(intLstartObjectIndex) == intLshortcutIndex) {
					intLshortcutIndex = intLstartObjectIndex;
				}
				this.setDirection(true);
				return this.getFilteredPatternIndex(this.getFilteredObjectIndex(intLshortcutIndex));
			}
		}

		// Trying to apply the start pattern :
		if (!bolPstartPattern) {
			intLfilteredPatternIndex =
										this.getFilteredObjectIndex(this.objGcontrolJFrame.getPatternsManager().getPatternsFileManager().intGstartObjectIndex);
			if (intLfilteredPatternIndex != Constants.bytS_UNCLASS_NO_VALUE
				&& this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLfilteredPatternIndex])) {
				return intLfilteredPatternIndex;
			}
		}

		// Apply the very first pattern of the filtered list :
		intLfilteredPatternIndex = 0;
		while (intLfilteredPatternIndex < this.intGfilteredObjectIndexA.length) {
			if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLfilteredPatternIndex])) {
				return intLfilteredPatternIndex;
			}
			++intLfilteredPatternIndex;
		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfilteredPatternIndex
	 * @return
	 */
	final public int getPatternIndex(int intPfilteredPatternIndex) {
		return (intPfilteredPatternIndex >= 0 && intPfilteredPatternIndex < this.intGfilteredObjectIndexA.length
																												? this.intGfilteredObjectIndexA[intPfilteredPatternIndex]
																												: Constants.bytS_UNCLASS_NO_VALUE);
	}

	final private boolean isIndexVisible(int intPindex) {
		return this.getFirstVisibleIndex() <= intPindex && intPindex <= this.getLastVisibleIndex();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyPressed(KeyEvent objPkeyEvent) {

		this.objGcontrolJFrame.doHidePopUps();
		final int intLkeyCode = objPkeyEvent.getKeyCode();
		final boolean bolLcontrolDown = objPkeyEvent.isControlDown();
		final boolean bolLshiftDown = objPkeyEvent.isShiftDown();
		switch (intLkeyCode) {
			case KeyEvent.VK_SPACE:
				objPkeyEvent.consume();
				return;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_PAGE_DOWN:
			case KeyEvent.VK_HOME:
			case KeyEvent.VK_END:

				final boolean bolLsearchDirection =
													(intLkeyCode == KeyEvent.VK_DOWN) || (intLkeyCode == KeyEvent.VK_PAGE_DOWN)
														|| (intLkeyCode == KeyEvent.VK_END);
				final int intLselectedIndex = this.getSelectedIndex();
				final int intLfirstVisibleIndex = this.getFirstVisibleIndex();
				final int intLlastVisibleIndex = this.getLastVisibleIndex();
				final int intLvisibleIndexes = intLlastVisibleIndex - intLfirstVisibleIndex + 1;
				int intLdelta =
								intLkeyCode == KeyEvent.VK_UP || intLkeyCode == KeyEvent.VK_DOWN
																								? 1
																								: bolLshiftDown
																												? Math.max(1, intLvisibleIndexes - 1)
																												: Math.min(	3,
																															Math.max(	1,
																																		intLvisibleIndexes / 2 - 1));
				this.setDirection(bolLsearchDirection);

				// Control key down :
				// //////////////////
				if (bolLcontrolDown && !bolLshiftDown) {
					this.intGlastHeadPatternIndex = Constants.bytS_UNCLASS_NO_VALUE;
					switch (intLkeyCode) {
						case KeyEvent.VK_UP:
						case KeyEvent.VK_PAGE_UP:
							// Watching up (Ctrl + (Page) Up) :
							objPkeyEvent.consume();
							this.ensureIndexIsVisible(intLfirstVisibleIndex - intLdelta);
							return;

						case KeyEvent.VK_DOWN:
						case KeyEvent.VK_PAGE_DOWN:
							// Watching down (Ctrl + (Page) down) :
							objPkeyEvent.consume();
							this.ensureIndexIsVisible(intLlastVisibleIndex + intLdelta);
							return;

						case KeyEvent.VK_HOME:
						case KeyEvent.VK_END:
							// Watching beginning/end (Ctrl + Home/End) :
							objPkeyEvent.consume();
							this.ensureIndexIsVisible(intLkeyCode == KeyEvent.VK_HOME ? 0 : this.intGfilteredObjectIndexA.length + 1);
							return;
					}
				}

				// Shift key down :
				// /////////////////
				if (bolLshiftDown && !bolLcontrolDown) {
					this.intGlastHeadPatternIndex = Constants.bytS_UNCLASS_NO_VALUE;
					switch (intLkeyCode) {
						case KeyEvent.VK_UP:
						case KeyEvent.VK_DOWN:
							// Moving up/down to the head of the current/next pattern block (Shift + Up / Down) :
							objPkeyEvent.consume();
							int intLshortcutIndex = this.getSelectedIndex() + Tools.getSignedBoolean(bolLsearchDirection);
							boolean bolLpatternToFind = true;
							WhileLoop: while (intLshortcutIndex >= 0 && intLshortcutIndex < this.intGfilteredObjectIndexA.length) {
								switch (intLkeyCode) {
									case KeyEvent.VK_UP:
										if (bolLpatternToFind) {
											if (this.objGcontrolJFrame	.getPatternsManager()
																		.isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
												bolLpatternToFind = false;
											}
										} else {
											if (!this.objGcontrolJFrame	.getPatternsManager()
																		.isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
												while (intLshortcutIndex > 0
														&& !this.objGcontrolJFrame	.getPatternsManager()
																					.isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex - 1])) {
													intLshortcutIndex--;
												}
												this.setDirection(true);
												break WhileLoop;
											}
										}
										break;

									case KeyEvent.VK_DOWN:
										if (!this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
											break WhileLoop;
										}
										break;
								}
								intLshortcutIndex += Tools.getSignedBoolean(bolLsearchDirection);
							}
							if (intLshortcutIndex < 0) {
								intLshortcutIndex = 0;
								this.ensureIndexIsVisible(0);
							}
							if (intLshortcutIndex == this.intGfilteredObjectIndexA.length) {
								intLshortcutIndex = this.intGfilteredObjectIndexA.length - 1;
								this.ensureIndexIsVisible(this.intGfilteredObjectIndexA.length + 1);
							}
							this.selectIndex(intLshortcutIndex);
							this.valueChanged(null);
							this.requestFocusInWindow();
							return;

						case KeyEvent.VK_PAGE_UP:
						case KeyEvent.VK_PAGE_DOWN:
							// Moving to previous/next page (Shift + Page up or down) :
							objPkeyEvent.consume();
							this.ensureIndexIsCentered(intLselectedIndex + intLdelta * Tools.getSignedBoolean(bolLsearchDirection));
							this.selectIndex(this.getFilteredPatternIndex(intLselectedIndex + intLdelta * Tools.getSignedBoolean(bolLsearchDirection)));
							this.valueChanged(null);
							this.requestFocusInWindow();
							return;

						case KeyEvent.VK_HOME:
						case KeyEvent.VK_END:
							// Moving to the beginning / end (Shift + Home/End) :
							objPkeyEvent.consume();
							this.ensureIndexIsVisible(intLkeyCode == KeyEvent.VK_HOME ? 0 : this.intGfilteredObjectIndexA.length + 1);
							this.setDirection(intLkeyCode == KeyEvent.VK_HOME);
							this.selectIndex(this.getFilteredPatternIndex(intLkeyCode == KeyEvent.VK_HOME
																											? 0
																											: this.intGfilteredObjectIndexA.length + 1));
							this.valueChanged(null);
							this.requestFocusInWindow();
							return;
					}
				}

				// Ctrl key + Shift key down :
				// ///////////////////////////
				if (bolLcontrolDown && bolLshiftDown) {
					int intLshortcutIndex = Constants.bytS_UNCLASS_NO_VALUE;
					if (this.intGlastHeadPatternIndex == Constants.bytS_UNCLASS_NO_VALUE) {
						if (this.isIndexVisible(this.getSelectedIndex())) {
							this.intGlastHeadPatternIndex = this.getSelectedIndex();
						} else {
							this.intGlastHeadPatternIndex = (this.getFirstVisibleIndex() + this.getLastVisibleIndex()) / 2;
						}
						while (this.intGlastHeadPatternIndex > 0
								&& this.objGcontrolJFrame	.getPatternsManager()
															.isPattern(this.intGfilteredObjectIndexA[this.intGlastHeadPatternIndex - 1])) {
							this.intGlastHeadPatternIndex--;
						}
					}
					intLshortcutIndex = this.intGlastHeadPatternIndex;
					Tools.out(this.intGlastHeadPatternIndex, "/", this.getSelectedIndex());
					switch (intLkeyCode) {
						case KeyEvent.VK_HOME:
						case KeyEvent.VK_END:
							// Watching to the beginning / end (Ctrl + Shift + Home/End) :
							objPkeyEvent.consume();
							this.ensureIndexIsVisible(intLkeyCode == KeyEvent.VK_HOME ? 0 : this.intGfilteredObjectIndexA.length + 1);
							return;
					}

					boolean bolLpatternToFind = true;
					WhileLoop: while (intLshortcutIndex >= 0 && intLshortcutIndex < this.intGfilteredObjectIndexA.length) {
						switch (intLkeyCode) {

							case KeyEvent.VK_UP:
								// Watching the head pattern of the current block (Ctrl + Shift + Up) :
								if (bolLpatternToFind) {
									if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										bolLpatternToFind = false;
									}
								} else {
									if (!this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										while (intLshortcutIndex > 0
												&& !this.objGcontrolJFrame	.getPatternsManager()
																			.isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex - 1])) {
											intLshortcutIndex--;
										}
										break WhileLoop;
									}
								}
								break;

							case KeyEvent.VK_PAGE_UP:
								// Watching up to the head pattern of the current shortcut (Ctrl + Shift + Page Up) :
								if (bolLpatternToFind) {
									if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										bolLpatternToFind = false;
									}
								} else {
									if (this.objGcontrolJFrame.getPatternsManager().isShortcut(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										break WhileLoop;
									}
								}
								break;

							// Watching the head pattern of the next block (Ctrl + Shift + Down) :
							case KeyEvent.VK_DOWN:
								if (bolLpatternToFind) {
									if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										bolLpatternToFind = false;
									}
								} else {
									if (!this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										intLdelta = 1;
										while (intLshortcutIndex + intLdelta < this.intGfilteredObjectIndexA.length - 1
												&& !this.objGcontrolJFrame	.getPatternsManager()
																			.isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex + intLdelta])) {
											intLdelta++;
										}

										if (intLshortcutIndex + intLdelta == this.intGfilteredObjectIndexA.length - 1) {
											while (!this.objGcontrolJFrame	.getPatternsManager()
																			.isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex + intLdelta])) {
												intLdelta--;
											}
										}
										intLshortcutIndex += intLdelta;
										break WhileLoop;
									}
								}
								break;

							case KeyEvent.VK_PAGE_DOWN:
								// Watching down to the head pattern of the next shortcut (Ctrl + Shift + Page Down) :
								if (bolLpatternToFind) {
									if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										bolLpatternToFind = false;
									}
								} else {
									if (this.objGcontrolJFrame.getPatternsManager().isShortcut(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										break WhileLoop;
									}
								}
								break;
						}
						intLshortcutIndex += Tools.getSignedBoolean(bolLsearchDirection);
					}
					objPkeyEvent.consume();
					this.intGlastHeadPatternIndex = Math.min(intLshortcutIndex, this.intGfilteredObjectIndexA.length - 1);
					this.ensureIndexIsCentered(intLshortcutIndex);
					return;
				}

				// No meta-key down :
				// ///////////////////
				switch (intLkeyCode) {

					case KeyEvent.VK_UP:
					case KeyEvent.VK_DOWN:
						// Moving up or down (Up / Down) :
						objPkeyEvent.consume();
						this.ensureIndexIsCentered(intLselectedIndex + 2 * Tools.getSignedBoolean(bolLsearchDirection));
						this.selectIndex(this.getFilteredPatternIndex(intLselectedIndex + Tools.getSignedBoolean(bolLsearchDirection)));
						this.valueChanged(null);
						this.requestFocusInWindow();
						return;

					case KeyEvent.VK_PAGE_UP:
					case KeyEvent.VK_PAGE_DOWN:
						// Moving up or down an half page (Page Up / Page Down) :
						objPkeyEvent.consume();
						this.ensureIndexIsCentered(intLselectedIndex + intLdelta * Tools.getSignedBoolean(bolLsearchDirection));
						this.selectIndex(this.getFilteredPatternIndex(intLselectedIndex + intLdelta * Tools.getSignedBoolean(bolLsearchDirection)));
						this.valueChanged(null);
						this.requestFocusInWindow();
						return;

					case KeyEvent.VK_HOME:
					case KeyEvent.VK_END:
						// Moving to the head of the current/next shortcut (Home/End) :
						int intLshortcutIndex = this.getSelectedIndex() + Tools.getSignedBoolean(bolLsearchDirection);
						boolean bolLrawSearchDone = false;
						this.setDirection(true);
						WhileLoop: while (intLshortcutIndex >= 0 && intLshortcutIndex < this.intGfilteredObjectIndexA.length) {
							switch (intLkeyCode) {

								case KeyEvent.VK_HOME:
									if (bolLrawSearchDone) {
										if (this.objGcontrolJFrame.getPatternsManager().isShortcut(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
											break WhileLoop;
										}
									} else {
										if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
											bolLrawSearchDone = true;
										}
									}
									break;

								case KeyEvent.VK_END:
									if (this.objGcontrolJFrame.getPatternsManager().isShortcut(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
										break WhileLoop;
									}
									break;
							}
							intLshortcutIndex += Tools.getSignedBoolean(bolLsearchDirection);
						}
						if (intLshortcutIndex < 0) {
							intLshortcutIndex = 0;
							this.ensureIndexIsVisible(0);
						}
						if (intLshortcutIndex == this.intGfilteredObjectIndexA.length) {
							intLshortcutIndex = this.intGfilteredObjectIndexA.length - 1;
							this.ensureIndexIsVisible(this.intGfilteredObjectIndexA.length + 1);
						}
						objPkeyEvent.consume();
						this.selectIndex(intLshortcutIndex);
						this.valueChanged(null);
				}
				break;

			case KeyEvent.VK_LEFT:
				if (objPkeyEvent.isControlDown()) {
					final byte bytLoldValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED);
					final byte bytLnewValue = (byte) Math.max(bytLoldValue - 1, Constants.bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE);
					this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_SPEED, bytLnewValue);
					if (bytLnewValue < bytLoldValue) {
						this.objGcontrolJFrame.doRestartJuggling();
					}
				} else {
					this.setDirection(false);
					this.selectIndex(this.getSelectedIndex() - 1);
					this.valueChanged(null);
				}
				this.requestFocusInWindow();
				return;

			case KeyEvent.VK_RIGHT:
				if (objPkeyEvent.isControlDown()) {
					final byte bytLoldValue = this.objGcontrolJFrame.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED);
					final byte bytLnewValue = (byte) Math.min(bytLoldValue + 1, Constants.bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE);
					this.objGcontrolJFrame.saveControlValue(Constants.bytS_BYTE_LOCAL_SPEED, bytLnewValue);
					if (bytLnewValue > bytLoldValue) {
						this.objGcontrolJFrame.doRestartJuggling();
					}
				} else {
					this.setDirection(true);
					this.selectIndex(this.getSelectedIndex() + 1);
					this.valueChanged(null);
				}
				this.requestFocusInWindow();
				return;
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyReleased(KeyEvent objPkeyEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPkeyEvent
	 */
	@Override final public void keyTyped(KeyEvent objPkeyEvent) {}

	@Override final public void mouseClicked(MouseEvent objPmouseEvent) {
		if (objPmouseEvent.getClickCount() == 2) {
			final int intLlastPatternIndex = this.getFilteredPatternIndex(this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex);
			final int intLnewPatternIndex = this.locationToIndex(objPmouseEvent.getPoint());
			if (intLnewPatternIndex == intLlastPatternIndex) {
				this.objGcontrolJFrame.doStartJuggling(intLnewPatternIndex);
			}
		}
	}

	@Override final public void mouseEntered(MouseEvent objPmouseEvent) {}

	@Override final public void mouseExited(MouseEvent objPmouseEvent) {}

	@Override final public void mousePressed(MouseEvent objPmouseEvent) {}

	@Override public void mouseReleased(MouseEvent objPmouseEvent) {}

	@Override public void mouseWheelMoved(MouseWheelEvent objPmouseWheelEvent) {

		final byte bytLdirection = Tools.getSign(objPmouseWheelEvent.getWheelRotation());
		final int intLfirstVisibleIndex = this.objGcontrolJFrame.objGobjectsJList.getFirstVisibleIndex();
		final int intLlastVisibleIndex = this.objGcontrolJFrame.objGobjectsJList.getLastVisibleIndex();
		final int intLvisibleIndexes = intLlastVisibleIndex - intLfirstVisibleIndex + 1;
		final boolean bolLcontrolDown = objPmouseWheelEvent.isControlDown();
		final boolean bolLshiftDown = objPmouseWheelEvent.isShiftDown();
		this.setDirection(bytLdirection > 0);

		// Moving to the previous/next page (Shift + wheel scroll) :
		if (bolLshiftDown && !bolLcontrolDown) {
			final int intLdelta = Math.max(1, intLvisibleIndexes - 1) * bytLdirection;
			if (bytLdirection > 0) {
				this.ensureIndexIsVisible(intLlastVisibleIndex + intLdelta);
			} else {
				this.ensureIndexIsVisible(intLfirstVisibleIndex + intLdelta);
			}
			return;
		}

		// Moving to the previous/next pattern (Ctrl + wheel scroll) :
		if (bolLcontrolDown && !bolLshiftDown) {
			if (bytLdirection > 0) {
				this.ensureIndexIsVisible(intLlastVisibleIndex + bytLdirection);
			} else {
				this.ensureIndexIsVisible(intLfirstVisibleIndex + bytLdirection);
			}
			return;
		}

		// Moving to the previous/next shortcut (Ctrl + Shift + wheel scroll) :
		if (bolLcontrolDown && bolLshiftDown) {
			int intLshortcutIndex = this.getSelectedIndex() + bytLdirection;
			boolean bolLrawSearchDone = false;
			this.setDirection(true);
			WhileLoop: while (intLshortcutIndex >= 0 && intLshortcutIndex < this.intGfilteredObjectIndexA.length) {
				switch (bytLdirection) {

					case ObjectsJList.bytS_DIRECTION_UP:
						if (bolLrawSearchDone) {
							if (this.objGcontrolJFrame.getPatternsManager().isShortcut(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
								break WhileLoop;
							}
						} else {
							if (this.objGcontrolJFrame.getPatternsManager().isPattern(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
								bolLrawSearchDone = true;
							}
						}
						break;

					case ObjectsJList.bytS_DIRECTION_DOWN:
						if (this.objGcontrolJFrame.getPatternsManager().isShortcut(this.intGfilteredObjectIndexA[intLshortcutIndex])) {
							break WhileLoop;
						}
						break;
				}
				intLshortcutIndex += bytLdirection;
			}
			if (intLshortcutIndex < 0 || intLshortcutIndex == this.intGfilteredObjectIndexA.length) {
				intLshortcutIndex = Math.max(0, Math.min(intLshortcutIndex, this.intGfilteredObjectIndexA.length - 1));
				this.ensureIndexIsVisible(intLshortcutIndex);
			}
			this.selectIndex(intLshortcutIndex);
			this.valueChanged(null);
			return;
		}

		// Wheel scroll :
		final int intLdelta = Math.min(3, Math.max(1, intLvisibleIndexes / 2 - 1)) * bytLdirection;
		if (bytLdirection > 0) {
			this.ensureIndexIsVisible(intLlastVisibleIndex + intLdelta);
		} else {
			this.ensureIndexIsVisible(intLfirstVisibleIndex + intLdelta);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void resetDirection() {
		this.bytGbrowsingDirection = ObjectsJList.bytS_NO_DIRECTION;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfilteredObjectIndex
	 * @param bolPensureVisibility
	 */
	final public void selectIndex(int intPfilteredObjectIndex) {
		if (intPfilteredObjectIndex >= 0 && intPfilteredObjectIndex < this.intGfilteredObjectIndexA.length) {
			this.removeListSelectionListener(this);

			this.setSelectedIndex(Constants.bytS_UNCLASS_NO_VALUE);
			this.setSelectedIndex(intPfilteredObjectIndex);
			this.ensureIndexIsCentered(intPfilteredObjectIndex);
			this.addListSelectionListener(this);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPdown
	 */
	final public void setDirection(boolean bolPdown) {
		this.bytGbrowsingDirection = bolPdown ? ObjectsJList.bytS_DIRECTION_DOWN : ObjectsJList.bytS_DIRECTION_UP;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPfilteredObjectAL
	 * @param objPfilteredObjectIndexAL
	 * @param intPfilteredObjectsNumber
	 */
	final public void setList(ArrayList<Object> objPfilteredObjectAL, ArrayList<Integer> objPfilteredObjectIndexAL, int intPfilteredObjectsNumber) {
		this.intGfilteredObjectIndexA = new int[intPfilteredObjectsNumber];
		for (int intLobjectIndex = 0; intLobjectIndex < intPfilteredObjectsNumber; ++intLobjectIndex) {
			this.intGfilteredObjectIndexA[intLobjectIndex] = objPfilteredObjectIndexAL.get(intLobjectIndex);
		}

		this.removeListSelectionListener(this);
		this.setListData(objPfilteredObjectAL.toArray());
		this.addListSelectionListener(this);
	}

	final public void setLists() {

		final byte bytL_STARTING_STATE = 0;
		final byte bytL_FILTERED_PATTERNS_STATE = 1;
		final byte bytL_COMMENTS_AFTER_FILTERED_PATTERNS_STATE = 2;
		final byte bytL_UNFILTERED_PATTERNS_BEFORE_FIRST_SHORTCUT_STATE = 3;
		final byte bytL_SHORTCUT_AND_COMMENTS_STATE = 4;
		final byte bytL_UNFILTERED_PATTERNS_AFTER_SHORTCUT_STATE = 5;
		final byte bytL_UNFILTERED_PATTERNS_AFTER_COMMENTS_STATE = 6;
		final byte bytL_UNFILTERED_PATTERN = 0;
		final byte bytL_COMMENT = 1;
		final byte bytL_SHORTCUT = 2;
		final byte bytL_FILTERED_PATTERN = 3;

		// Prepare treatment objects :
		final PatternsManager objLpatternsManager = this.objGcontrolJFrame.getPatternsManager();
		if (objLpatternsManager.objGobjectA != null) {
			final int intLobjectsNumber = objLpatternsManager.objGobjectA.length;
			final ArrayList<Object> objLfilteredObjectAL = new ArrayList<Object>(intLobjectsNumber + 2);
			final ArrayList<Integer> objLfilteredObjectIndexAL = new ArrayList<Integer>(intLobjectsNumber);
			final ArrayList<String> strLfilteredShortcutAL =
																new ArrayList<String>(objLpatternsManager.intGshortcutIndexA != null
																																	? objLpatternsManager.intGshortcutIndexA.length
																																	: 1);
			final ArrayList<Integer> objLfilteredShortcutStringIndexAL =
																			new ArrayList<Integer>(objLpatternsManager.intGshortcutIndexA != null
																																					? objLpatternsManager.intGshortcutIndexA.length
																																					: 1);
			final ArrayList<Integer> objLfilteredShortcutStringFilteredIndexAL =
																					new ArrayList<Integer>(objLpatternsManager.intGshortcutIndexA != null
																																							? objLpatternsManager.intGshortcutIndexA.length
																																							: 1);
			final ArrayList<Integer> objLcommentStringIndexAL = new ArrayList<Integer>(2);
			final ArrayList<String> strLcommentAL = new ArrayList<String>(2);
			final ArrayList<Integer> objLshortcutStringIndexAL = new ArrayList<Integer>(2);
			final ArrayList<String> strLshortcutAL = new ArrayList<String>(2);
			final ArrayList<Integer> objLfooterStringIndexAL = new ArrayList<Integer>(2);
			final ArrayList<String> strLfooterAL = new ArrayList<String>(2);

			// Set filter preference variables :
			final boolean bolLballsNumber = Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER);
			final byte bytLlowBallsNumber = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER);
			final byte bytLhighBallsNumber = Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER);
			final boolean bolLskill = Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_SKILL);
			final byte bytLlowSkill =
										bolLskill ? Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_SKILL)
													: Constants.bytS_UNCLASS_NO_VALUE;
			final byte bytLhighSkill =
										bolLskill ? Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_SKILL)
													: Constants.bytS_UNCLASS_NO_VALUE;
			final boolean bolLmark = Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MARK);
			final byte bytLlowMark =
										bolLmark ? Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_MARK)
												: Constants.bytS_UNCLASS_NO_VALUE;
			final byte bytLhighMark =
										bolLmark ? Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_MARK)
												: Constants.bytS_UNCLASS_NO_VALUE;
			final boolean bolLinvalidPatterns = Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS);

			// Set other variables :
			byte bytLstate = bytL_STARTING_STATE;
			byte bytLtype = Constants.bytS_UNCLASS_NO_VALUE;
			this.objGcontrolJFrame.setFilteredPatternsNumber(0);
			this.objGcontrolJFrame.setFilteredShortcutsNumber(0);
			this.objGcontrolJFrame.setExportCurrentPatterns(false);
			this.objGcontrolJFrame.setExportCurrentPatterns(false);

			// Treat each list object :
			Object objLobject = null;
			Pattern objLpattern = null;
			String strLcomment = null;
			for (int intLobjectIndex = 0, intLshortcutIndex = 0; intLobjectIndex < intLobjectsNumber; ++intLobjectIndex) {

				objLobject = objLpatternsManager.objGobjectA[intLobjectIndex];

				// Set object properties :
				if (objLobject instanceof Pattern) {

					// Set pattern :
					objLpattern = (Pattern) objLobject;

					// Set pattern type :
					final boolean bolLedition = objLpatternsManager.getPatternBooleanValue(intLobjectIndex, Constants.bytS_BOOLEAN_LOCAL_EDITION);
					final int intLballsNumber = objLpattern.intGballsNumberA[Constants.bytS_UNCLASS_CURRENT];
					final byte bytLskill = objLpattern.bytGlocalValueAA[Constants.bytS_BYTE_LOCAL_SKILL][Constants.bytS_UNCLASS_CURRENT];
					final byte bytLmark = objLpattern.bytGlocalValueAA[Constants.bytS_BYTE_LOCAL_MARK][Constants.bytS_UNCLASS_CURRENT];
					final boolean bolLplayable = objLpattern.bolGplayableA[Constants.bytS_UNCLASS_CURRENT];
					final boolean bolLballsNumberFiltered =
															!bolLballsNumber
																|| (intLballsNumber >= bytLlowBallsNumber && (intLballsNumber <= bytLhighBallsNumber || bytLhighBallsNumber == 9));
					final boolean bolLskillFiltered = !bolLskill || (bytLskill >= bytLlowSkill && bytLskill <= bytLhighSkill);
					final boolean bolLmarkFiltered = !bolLmark || (bytLmark >= bytLlowMark && bytLmark <= bytLhighMark);
					// JuggleTools.debug("Controle à partir de setLists pour la figure ", objLpattern);
					final Boolean bolLadvancedFilters = this.objGcontrolJFrame.objGfiltersJDialog.isPatternFiltered(objLpattern, bolLedition);
					bytLtype =
								bolLadvancedFilters
									&& (!bolLedition || bolLplayable && bolLballsNumberFiltered && bolLskillFiltered && bolLmarkFiltered || !bolLplayable
																																			&& (bolLinvalidPatterns || this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN))
																																																																					? bytL_FILTERED_PATTERN
																																																																					: bytL_UNFILTERED_PATTERN;
					this.objGcontrolJFrame.setExportCurrentPatterns(this.objGcontrolJFrame.isExportCurrentPatterns() | bolLedition);
					if (bytLtype == bytL_FILTERED_PATTERN) {
						this.objGcontrolJFrame.setExportCurrentPatterns(this.objGcontrolJFrame.isExportCurrentPatterns() | bolLedition);
						this.objGcontrolJFrame.setFilteredPatternsNumber(this.objGcontrolJFrame.getFilteredPatternsNumber() + 1);
					}
				} else {

					// Set comment string :
					strLcomment =
									this.objGcontrolJFrame	.getLanguage()
															.getTranslatedTokensString(	objLpatternsManager.getPatternStringValue(	intLobjectIndex,
																																	Constants.bytS_STRING_LOCAL_PATTERN,
																																	Constants.bytS_UNCLASS_INITIAL),
																						this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType);

					// Set comment type :
					if (objLpatternsManager.intGshortcutIndexA != null) {
						while (intLshortcutIndex < objLpatternsManager.intGshortcutIndexA.length
								&& objLpatternsManager.intGshortcutIndexA[intLshortcutIndex] < intLobjectIndex) {
							++intLshortcutIndex;
						}
						bytLtype =
									intLshortcutIndex < objLpatternsManager.intGshortcutIndexA.length
										&& objLpatternsManager.intGshortcutIndexA[intLshortcutIndex] == intLobjectIndex ? bytL_SHORTCUT
																														: bytL_COMMENT;
					} else {
						bytLtype = bytL_COMMENT;
					}
				}

				switch (bytLstate) {

					case bytL_STARTING_STATE:
						switch (bytLtype) {
							case bytL_UNFILTERED_PATTERN:
								bytLstate = bytL_UNFILTERED_PATTERNS_BEFORE_FIRST_SHORTCUT_STATE;
								break;
							case bytL_COMMENT:
								objLfilteredObjectAL.add(strLcomment);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								break;
							case bytL_SHORTCUT:
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(objLfilteredObjectAL.size());
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								bytLstate = bytL_SHORTCUT_AND_COMMENTS_STATE;
								break;
							case bytL_FILTERED_PATTERN:
								objLfilteredObjectAL.add(objLpattern);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								bytLstate = bytL_FILTERED_PATTERNS_STATE;
								break;
						}
						break;

					case bytL_FILTERED_PATTERNS_STATE:
						switch (bytLtype) {
							case bytL_UNFILTERED_PATTERN:
								break;
							case bytL_COMMENT:
								strLcommentAL.add(strLcomment);
								objLcommentStringIndexAL.add(intLobjectIndex);
								bytLstate = bytL_COMMENTS_AFTER_FILTERED_PATTERNS_STATE;
								break;
							case bytL_SHORTCUT:
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(objLfilteredObjectAL.size() + strLfooterAL.size());
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								bytLstate = bytL_SHORTCUT_AND_COMMENTS_STATE;
								break;
							case bytL_FILTERED_PATTERN:
								objLfilteredObjectAL.add(objLpattern);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								break;
						}
						break;

					case bytL_COMMENTS_AFTER_FILTERED_PATTERNS_STATE:
						switch (bytLtype) {
							case bytL_UNFILTERED_PATTERN:
								bytLstate = bytL_UNFILTERED_PATTERNS_AFTER_COMMENTS_STATE;
								break;
							case bytL_COMMENT:
								strLcommentAL.add(strLcomment);
								objLcommentStringIndexAL.add(intLobjectIndex);
								break;
							case bytL_SHORTCUT:
								strLfooterAL.addAll(strLcommentAL);
								objLfooterStringIndexAL.addAll(objLcommentStringIndexAL);
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(objLfilteredObjectAL.size() + strLfooterAL.size());
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								bytLstate = bytL_SHORTCUT_AND_COMMENTS_STATE;
								break;
							case bytL_FILTERED_PATTERN:
								objLfilteredObjectAL.addAll(strLcommentAL);
								objLfilteredObjectIndexAL.addAll(objLcommentStringIndexAL);
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								objLfilteredObjectAL.add(objLpattern);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								bytLstate = bytL_FILTERED_PATTERNS_STATE;
								break;
						}
						break;

					case bytL_UNFILTERED_PATTERNS_BEFORE_FIRST_SHORTCUT_STATE:
						switch (bytLtype) {
							case bytL_UNFILTERED_PATTERN:
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								break;
							case bytL_COMMENT:
								strLcommentAL.add(strLcomment);
								objLcommentStringIndexAL.add(intLobjectIndex);
								break;
							case bytL_SHORTCUT:
								strLfooterAL.addAll(strLcommentAL);
								objLfooterStringIndexAL.addAll(objLcommentStringIndexAL);
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(objLfilteredObjectAL.size() + strLfooterAL.size());
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								bytLstate = bytL_SHORTCUT_AND_COMMENTS_STATE;
								break;
							case bytL_FILTERED_PATTERN:
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								objLfilteredObjectAL.add(objLpattern);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								bytLstate = bytL_FILTERED_PATTERNS_STATE;
								break;
						}
						break;

					case bytL_SHORTCUT_AND_COMMENTS_STATE:
						switch (bytLtype) {
							case bytL_UNFILTERED_PATTERN:
								bytLstate = bytL_UNFILTERED_PATTERNS_AFTER_SHORTCUT_STATE;
								break;
							case bytL_COMMENT:
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(intLobjectIndex);
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								break;
							case bytL_SHORTCUT:
								strLshortcutAL.clear();
								objLshortcutStringIndexAL.clear();
								objLfilteredShortcutStringFilteredIndexAL.clear();
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(objLfilteredObjectAL.size() + strLfooterAL.size());
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								break;
							case bytL_FILTERED_PATTERN:
								objLfilteredObjectAL.addAll(strLfooterAL);
								objLfilteredObjectIndexAL.addAll(objLfooterStringIndexAL);
								strLfooterAL.clear();
								objLfooterStringIndexAL.clear();
								objLfilteredObjectAL.addAll(strLshortcutAL);
								objLfilteredObjectIndexAL.addAll(objLfilteredShortcutStringFilteredIndexAL);
								strLfilteredShortcutAL.add(strLshortcutAL.get(0));
								objLfilteredShortcutStringIndexAL.add(objLshortcutStringIndexAL.get(0));
								this.objGcontrolJFrame.setFilteredShortcutsNumber(this.objGcontrolJFrame.getFilteredShortcutsNumber() + 1);
								strLshortcutAL.clear();
								objLshortcutStringIndexAL.clear();
								objLfilteredShortcutStringFilteredIndexAL.clear();
								objLfilteredObjectAL.add(objLpattern);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								bytLstate = bytL_FILTERED_PATTERNS_STATE;
								break;
						}
						break;

					case bytL_UNFILTERED_PATTERNS_AFTER_SHORTCUT_STATE:
						switch (bytLtype) {
							case bytL_UNFILTERED_PATTERN:
								break;
							case bytL_COMMENT:
								break;
							case bytL_SHORTCUT:
								strLshortcutAL.clear();
								objLshortcutStringIndexAL.clear();
								objLfilteredShortcutStringFilteredIndexAL.clear();
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(objLfilteredObjectAL.size() + strLfooterAL.size());
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								bytLstate = bytL_SHORTCUT_AND_COMMENTS_STATE;
								break;
							case bytL_FILTERED_PATTERN:
								objLfilteredObjectAL.addAll(strLfooterAL);
								objLfilteredObjectIndexAL.addAll(objLfooterStringIndexAL);
								strLfooterAL.clear();
								objLfooterStringIndexAL.clear();
								objLfilteredObjectAL.addAll(strLshortcutAL);
								objLfilteredObjectIndexAL.addAll(objLfilteredShortcutStringFilteredIndexAL);
								strLfilteredShortcutAL.add(strLshortcutAL.get(0));
								objLfilteredShortcutStringIndexAL.add(objLshortcutStringIndexAL.get(0));
								this.objGcontrolJFrame.setFilteredShortcutsNumber(this.objGcontrolJFrame.getFilteredShortcutsNumber() + 1);
								strLshortcutAL.clear();
								objLshortcutStringIndexAL.clear();
								objLfilteredShortcutStringFilteredIndexAL.clear();
								objLfilteredObjectAL.add(objLpattern);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								bytLstate = bytL_FILTERED_PATTERNS_STATE;
								break;
						}
						break;

					case bytL_UNFILTERED_PATTERNS_AFTER_COMMENTS_STATE:
						switch (bytLtype) {
							case bytL_UNFILTERED_PATTERN:
								break;
							case bytL_COMMENT:
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								strLcommentAL.add(strLcomment);
								objLcommentStringIndexAL.add(intLobjectIndex);
								bytLstate = bytL_COMMENTS_AFTER_FILTERED_PATTERNS_STATE;
								break;
							case bytL_SHORTCUT:
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								strLshortcutAL.add(strLcomment);
								objLshortcutStringIndexAL.add(objLfilteredObjectAL.size() + strLfooterAL.size());
								objLfilteredShortcutStringFilteredIndexAL.add(intLobjectIndex);
								bytLstate = bytL_SHORTCUT_AND_COMMENTS_STATE;
								break;
							case bytL_FILTERED_PATTERN:
								objLfilteredObjectAL.addAll(strLcommentAL);
								objLfilteredObjectIndexAL.addAll(objLcommentStringIndexAL);
								strLcommentAL.clear();
								objLcommentStringIndexAL.clear();
								objLfilteredObjectAL.add(objLpattern);
								objLfilteredObjectIndexAL.add(intLobjectIndex);
								break;
						}
						break;
				}
			}

			// Append last comments :
			if (bytLstate == bytL_COMMENTS_AFTER_FILTERED_PATTERNS_STATE) {
				objLfilteredObjectAL.addAll(strLcommentAL);
				objLfilteredObjectIndexAL.addAll(objLcommentStringIndexAL);
			}

			// Append pattern selected number :
			int intLfilteredObjectsNumber = objLfilteredObjectIndexAL.size();
			if (this.objGcontrolJFrame.getFilteredPatternsNumber() > 0) {
				switch (objLpatternsManager.bytGpatternsManagerType) {
					case Constants.bytS_MANAGER_JM_PATTERN:
					case Constants.bytS_MANAGER_NEW_PATTERN:
					case Constants.bytS_MANAGER_NO_PATTERN:
						break;
					default:
						objLfilteredObjectAL.add(Strings.strS_SPACE);
						objLfilteredObjectIndexAL.add(intLfilteredObjectsNumber);
						objLfilteredObjectAL.add(Strings.doConcat(	"[ ",
																	this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_PATTERNS_TOTAL),
																	' ',
																	this.objGcontrolJFrame.getFilteredPatternsNumber(),
																	'/',
																	objLpatternsManager.intGpatternsNumber,
																	" ]"));
						objLfilteredObjectIndexAL.add(intLfilteredObjectsNumber);
				}
			} else {
				objLfilteredObjectAL.clear();
				objLfilteredObjectIndexAL.clear();

				objLfilteredObjectAL.add(Strings.strS_SPACE);
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);
				objLfilteredObjectAL.add(Strings.strS_SPACE);
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);

				objLfilteredObjectAL.add(// strLcomment =
				this.objGcontrolJFrame.getLanguage().getTranslatedTokensString(	Language.strS_TOKEN_A[Language.bytS_TOKEN_JUGGLE_MASTER_PRO],
																				this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType));
				// objLfilteredObjectAL.add(Language.strS_TOKEN_A[Language.bytS_TOKEN_JUGGLE_MASTER_PRO]);
				Tools.out("objLfilteredObjectAL.add(", Language.strS_TOKEN_A[Language.bytS_TOKEN_JUGGLE_MASTER_PRO], ")");
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);

				objLfilteredObjectAL.add(Strings.strS_SPACE);
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);

				objLfilteredObjectAL.add(Strings.doConcat("\251 ", Constants.strS_ENGINE_COPYRIGHT_YEARS, ' ', Constants.strS_ENGINE_ARNAUD_BELO));
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);
				objLfilteredObjectAL.add("___________");
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);

				objLfilteredObjectAL.add(Strings.strS_SPACE);
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);

				objLfilteredObjectAL.add(this.objGcontrolJFrame.getLanguageString(objLpatternsManager.intGpatternsNumber > 0
																															? Language.intS_MESSAGE_NO_FILTERED_PATTERN
																															: Language.intS_MESSAGE_NO_PATTERN_IN_THIS_FILE));
				objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);

				if (objLpatternsManager.intGpatternsNumber == 0) {
					objLfilteredObjectAL.add(Strings.strS_SPACE);
					objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);

					objLfilteredObjectAL.add(this.objGcontrolJFrame.getLanguageString(Language.intS_MESSAGE_LOOK_FOR_HELP));
					objLfilteredObjectIndexAL.add((int) Constants.bytS_UNCLASS_NO_VALUE);
				}
				intLfilteredObjectsNumber = objLfilteredObjectAL.size();
			}

			// Build object list :
			this.setList(objLfilteredObjectAL, objLfilteredObjectIndexAL, intLfilteredObjectsNumber);
			this.objGcontrolJFrame.objGobjectsJListJScrollPane.setViewportView(this);

			// Build shortcut list :
			this.objGcontrolJFrame.objGshortcutsJComboBox.setList(	strLfilteredShortcutAL,
																	objLfilteredShortcutStringIndexAL,
																	this.objGcontrolJFrame.getFilteredShortcutsNumber());
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPlistSelectionEvent
	 */
	@Override final public void valueChanged(ListSelectionEvent objPlistSelectionEvent) {

		final int intLselectedIndex = this.getSelectedIndex();
		final int intLfilteredPatternIndex = this.getFilteredPatternIndex(intLselectedIndex);
		final int intLlastPatternIndex = this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex;
		final int intLnewPatternIndex = this.getPatternIndex(intLfilteredPatternIndex);
		if (intLnewPatternIndex != intLlastPatternIndex) {
			// JuggleTools.debug("doRestartJuggling par ObjectsJList");
			this.selectIndex(intLfilteredPatternIndex);
			this.objGcontrolJFrame.doStartJuggling(intLnewPatternIndex);
		} else {
			switch (this.bytGbrowsingDirection) {
				case ObjectsJList.bytS_DIRECTION_UP:
					this.selectIndex(0);
					this.selectIndex(intLfilteredPatternIndex);
					break;
				case ObjectsJList.bytS_DIRECTION_DOWN:
					this.selectIndex(this.intGfilteredObjectIndexA.length - 1);
					this.selectIndex(intLfilteredPatternIndex);
					break;
				case ObjectsJList.bytS_NO_DIRECTION:
					this.selectIndex(intLfilteredPatternIndex);
			}
		}
		this.resetDirection();
	}

	private byte				bytGbrowsingDirection;

	public int[]				intGfilteredObjectIndexA;

	private int					intGlastHeadPatternIndex;

	// private int intGfilteredObjectIndex;
	final private ControlJFrame	objGcontrolJFrame;

	final private static byte	bytS_DIRECTION_DOWN	= 1;

	final private static byte	bytS_DIRECTION_UP	= -1;

	final private static byte	bytS_NO_DIRECTION	= 0;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ObjectsJList.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
