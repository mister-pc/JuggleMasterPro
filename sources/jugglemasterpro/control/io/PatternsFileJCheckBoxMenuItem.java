/*
 * @(#)PatternsFileJCheckBoxMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.io;

import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.MenusTools;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class PatternsFileJCheckBoxMenuItem extends JCheckBoxMenuItem implements ItemListener, MenuKeyListener {

	private static boolean		bolSaltDown			= false;

	private static boolean		bolScontrolDown		= false;

	private static boolean		bolSshiftDown		= false;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final public static String getModifiersKeysInfo() {
		return Strings.doConcat("PatternsFileJCheckBoxMenuItem.intS_KEY_MODIFIERS : ",
								PatternsFileJCheckBoxMenuItem.bolScontrolDown ? "Ctrl " : Strings.strS_EMPTY,
								PatternsFileJCheckBoxMenuItem.bolSaltDown ? "Alt " : Strings.strS_EMPTY,
								PatternsFileJCheckBoxMenuItem.bolSshiftDown ? "Shift " : Strings.strS_EMPTY);
	}

	final private static void setKeysModifiers(int intPkeysModifiers) {
		PatternsFileJCheckBoxMenuItem.bolScontrolDown = Tools.intersects(intPkeysModifiers, InputEvent.CTRL_DOWN_MASK | InputEvent.CTRL_MASK);
		PatternsFileJCheckBoxMenuItem.bolSshiftDown = Tools.intersects(intPkeysModifiers, InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK);
		PatternsFileJCheckBoxMenuItem.bolSaltDown =
													Tools.intersects(intPkeysModifiers, InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK
																						| InputEvent.ALT_GRAPH_MASK | InputEvent.ALT_GRAPH_DOWN_MASK);
		// Tools.debug(PatternsFileJCheckBoxMenuItem.getModifiersKeysInfo());
	}

	final private boolean		bolGnewLists;

	final private boolean		bolGsiteswaps;

	final private boolean		bolGstyles;

	final private ControlJFrame	objGcontrolJFrame;

	final private ImageIcon		objGimageIcon;

	final private String		strGfileName;

	final private String		strGfileTitle;

	public PatternsFileJCheckBoxMenuItem(ControlJFrame objPcontrolJFrame, String strPfileTitle, String strPjMenuItem, String strPfileName) {
		this(	objPcontrolJFrame,
				strPfileTitle,
				strPjMenuItem,
				strPfileName,
				objPcontrolJFrame.getFileImageIcon(true, true, true),
				true,
				true,
				true,
				Constants.bytS_UNCLASS_NO_VALUE);
	}

	public PatternsFileJCheckBoxMenuItem(	ControlJFrame objPcontrolJFrame,
											String strPfileTitle,
											String strPjMenuItem,
											String strPfileName,
											boolean bolPnewLists,
											boolean bolPsiteswaps,
											boolean bolPstyles) {

		this(	objPcontrolJFrame,
				strPfileTitle,
				strPjMenuItem,
				strPfileName,
				objPcontrolJFrame.getFileImageIcon(bolPnewLists, bolPsiteswaps, bolPstyles),
				bolPnewLists,
				bolPsiteswaps,
				bolPstyles,
				Constants.bytS_UNCLASS_NO_VALUE);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPfileTitle
	 * @param strPfileName
	 */
	public PatternsFileJCheckBoxMenuItem(	ControlJFrame objPcontrolJFrame,
											String strPfileTitle,
											String strPjMenuItem,
											String strPfileName,
											ImageIcon imgPicon) {
		this(objPcontrolJFrame, strPfileTitle, strPjMenuItem, strPfileName, imgPicon, true, true, true, Constants.bytS_UNCLASS_NO_VALUE);
	}

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 * @param strPfileTitle
	 * @param strPfileName
	 * @param bolPsiteswaps
	 * @param bolPstyles
	 */
	public PatternsFileJCheckBoxMenuItem(	ControlJFrame objPcontrolJFrame,
											String strPfileTitle,
											String strPjMenuItem,
											String strPfileName,
											ImageIcon icoP,
											boolean bolPnewLists,
											boolean bolPsiteswaps,
											boolean bolPstyles,
											int intPshortcutIndex) {
		super(strPjMenuItem);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.strGfileTitle = new String(strPfileTitle);
		this.strGfileName = new String(strPfileName);
		this.objGimageIcon = icoP;
		this.bolGnewLists = bolPnewLists;
		this.bolGsiteswaps = bolPsiteswaps;
		this.bolGstyles = bolPstyles;
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.addItemListener(this);
		this.addMenuKeyListener(this);
		if (intPshortcutIndex != Constants.bytS_UNCLASS_NO_VALUE) {
			this.setAccelerator(Constants.keyS_INDEX_NUMBER_A[intPshortcutIndex]);
		}
		MenusTools.setMenuMnemonic(this, strPfileTitle);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	final public boolean equals(PatternsFileJCheckBoxMenuItem objPpatternsFileJCheckBoxMenuItem) {

		return this == objPpatternsFileJCheckBoxMenuItem || objPpatternsFileJCheckBoxMenuItem != null
				&& this.bolGnewLists == objPpatternsFileJCheckBoxMenuItem.bolGnewLists
				&& this.bolGsiteswaps == objPpatternsFileJCheckBoxMenuItem.bolGsiteswaps
				&& this.bolGstyles == objPpatternsFileJCheckBoxMenuItem.bolGstyles
				&& this.getAbsoluteFileNameString().equals(objPpatternsFileJCheckBoxMenuItem.getAbsoluteFileNameString());
	}

	final public String getAbsoluteFileNameString() {
		try {
			return new File(this.strGfileName).getCanonicalPath();
		} catch (final Throwable objPthrowable) {
			return this.strGfileName;
		}
	}

	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {

		final byte bytLpatternsManagerType = this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType;
		final boolean bolLshiftOrAltDown = PatternsFileJCheckBoxMenuItem.bolSshiftDown || PatternsFileJCheckBoxMenuItem.bolSaltDown;

		final boolean bolLnewLists =
										!PatternsFileJCheckBoxMenuItem.bolScontrolDown && this.bolGnewLists
											|| bytLpatternsManagerType == Constants.bytS_MANAGER_JM_PATTERN
											|| bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_ABSTRACT_LANGUAGE
											|| bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN
											|| bytLpatternsManagerType == Constants.bytS_MANAGER_NO_PATTERN;
		Tools.out(	!PatternsFileJCheckBoxMenuItem.bolScontrolDown,
						this.bolGnewLists,
						bytLpatternsManagerType == Constants.bytS_MANAGER_JM_PATTERN,
						bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_ABSTRACT_LANGUAGE,
						bytLpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN,
						bytLpatternsManagerType == Constants.bytS_MANAGER_NO_PATTERN);
		final boolean bolLsiteswaps =
										this.bolGsiteswaps && !bolLshiftOrAltDown || PatternsFileJCheckBoxMenuItem.bolSaltDown
											|| bytLpatternsManagerType == Constants.bytS_MANAGER_JM_PATTERN;
		final boolean bolLstyles = this.bolGstyles && !bolLshiftOrAltDown || PatternsFileJCheckBoxMenuItem.bolSshiftDown;
		Tools.debug("PatternsFileJCheckBoxMenuItem.itemStateChanged(bolLnewLists = ",
					bolLnewLists,
					", bolLsiteswaps = ",
					bolLsiteswaps,
					", bolLstyles = ",
					bolLstyles,
					')');
		this.objGcontrolJFrame.doHidePopUps();
		if (bolLnewLists) {
			if (FileActions.doLoadJugglePatternsManager(this.objGcontrolJFrame,
														Constants.bytS_MANAGER_FILES_PATTERNS,
														this.strGfileName,
														this.strGfileTitle,
														this.objGimageIcon,
														true,
														bolLsiteswaps,
														bolLstyles)) {

				FileActions.doCheckPatternsMenuItems(this.objGcontrolJFrame);
				this.objGcontrolJFrame.getJuggleMasterPro().intGobjectIndex =
																				this.objGcontrolJFrame.objGobjectsJList.getPatternIndex(this.objGcontrolJFrame.objGobjectsJList.getFilteredPatternRenewedIndex(	this.objGcontrolJFrame	.getPatternsManager()
																																																										.getPatternsFileManager().intGstartObjectIndex,
																																																				true));
				this.objGcontrolJFrame.doRestartJuggling();
				if (objPitemEvent != null) {
					this.objGcontrolJFrame.objGobjectsJList.ensureIndexIsVisible(0);
				}
			}
		} else {
			FileActions.doImportJugglePatternsManager(this.objGcontrolJFrame, this.strGfileName, this.strGfileTitle, bolLsiteswaps, bolLstyles);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.MenuKeyListener#menuKeyPressed(javax.swing.event.MenuKeyEvent)
	 */
	@Override final public void menuKeyPressed(MenuKeyEvent objPmenuKeyEvent) {
		int intLkeysModifiers = objPmenuKeyEvent.getModifiers();
		final int intLaltMask = InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK | InputEvent.ALT_GRAPH_MASK | InputEvent.ALT_GRAPH_DOWN_MASK;
		if (Tools.intersects(intLkeysModifiers, intLaltMask)) {
			// Tools.debug("Yep !");
			intLkeysModifiers ^= intLaltMask;
			PatternsFileJCheckBoxMenuItem.setKeysModifiers(intLkeysModifiers);
			return;
		}
		PatternsFileJCheckBoxMenuItem.setKeysModifiers(objPmenuKeyEvent.getModifiers());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.MenuKeyListener#menuKeyReleased(javax.swing.event.MenuKeyEvent)
	 */
	@Override final public void menuKeyReleased(MenuKeyEvent objPmenuKeyEvent) {
		int intLkeysModifiers = objPmenuKeyEvent.getModifiers();
		final int intLaltMask = InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK | InputEvent.ALT_GRAPH_MASK | InputEvent.ALT_GRAPH_DOWN_MASK;
		if (Tools.intersects(intLkeysModifiers, intLaltMask)) {
			Tools.debug("Yep !");
			intLkeysModifiers ^= intLaltMask;
			PatternsFileJCheckBoxMenuItem.setKeysModifiers(intLkeysModifiers);
			return;
		}
		PatternsFileJCheckBoxMenuItem.setKeysModifiers(objPmenuKeyEvent.getModifiers());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.MenuKeyListener#menuKeyTyped(javax.swing.event.MenuKeyEvent)
	 */
	@Override final public void menuKeyTyped(MenuKeyEvent objPmenuKeyEvent) {
	// Nothing to do, this method does not concern modifier keys
	}

	final public void select(boolean bolPselected) {
		this.removeItemListener(this);
		this.setSelected(bolPselected);
		this.addItemListener(this);
	}

	@Override final public String toString() {
		return Strings.doConcat(this.strGfileTitle,
								" (",
								this.strGfileName,
								" [",
								this.getAbsoluteFileNameString(),
								"]) - ",
								this.bolGnewLists,
								", ",
								this.bolGsiteswaps,
								", ",
								this.bolGstyles);
	}
}

/*
 * @(#)PatternsFileJCheckBoxMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
