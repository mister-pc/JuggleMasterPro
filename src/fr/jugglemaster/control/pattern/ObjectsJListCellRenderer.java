/*
 * @(#)ObjectsJListCellRenderer.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.pattern;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolTip;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.pattern.Pattern;
import fr.jugglemaster.pattern.PatternsManager;
import fr.jugglemaster.pattern.Siteswap;
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
final public class ObjectsJListCellRenderer extends JLabel implements ListCellRenderer<Object> {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	final private ControlJFrame	objGcontrolJFrame;

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ObjectsJListCellRenderer(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
	}

	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPjList
	 * @param objPobject
	 * @param intPindex
	 * @param bolPselected
	 * @param bolPfocused
	 * @return
	 */
	@Override final public Component getListCellRendererComponent(	JList<? extends Object> objPjList,
																	Object objPobject,
																	int intPindex,
																	boolean bolPselected,
																	boolean bolPfocused) {

		final boolean bolLpattern = objPobject instanceof Pattern;
		final PatternsManager objLpatternsManager = this.objGcontrolJFrame.getPatternsManager();
		Font objLfont = objPjList.getFont();

		if (bolLpattern) {
			final StringBuilder objLstringBuilder = new StringBuilder(64);
			final Pattern objLpattern = (Pattern) objPobject;
			final boolean bolLpatternName =
											objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_NO_PATTERN
												&& objLpatternsManager.getPatternsFileManager().bolGstyleFound
												|| objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS;
			if (bolLpatternName) {
				if (objLpattern.bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_EDITION][Constants.bytS_UNCLASS_CURRENT]) {
					objLstringBuilder.append(Siteswap.getBallsNumberString(objLpattern.intGballsNumberA[Constants.bytS_UNCLASS_CURRENT], false));
					objLstringBuilder.append(" - ");
				}
				objLstringBuilder.append(objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_PATTERN][Constants.bytS_UNCLASS_CURRENT]);
			}
			this.setForeground(bolPselected ? Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR : objPjList.getForeground());
			this.setBackground(bolPselected ? objPjList.getSelectionBackground() : Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
			this.setEnabled(objLpattern.bolGplayableA[Constants.bytS_UNCLASS_CURRENT]);

			this.setText(Strings.doConcat(Strings.strS_SPACES, objLstringBuilder));
			if (objLpattern.bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_EDITION][Constants.bytS_UNCLASS_CURRENT]
				&& (!this.objGcontrolJFrame.isBooleanLocal(Constants.bytS_BOOLEAN_LOCAL_SITESWAP) || objLpattern.bolGlocalValueAA[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][Constants.bytS_UNCLASS_CURRENT])) {
				objLstringBuilder.append(Strings.doConcat(	" | ",
															objLpattern.strGlocalAA[Constants.bytS_STRING_LOCAL_SITESWAP][Constants.bytS_UNCLASS_CURRENT]));
			}
			this.setToolTipText(objLstringBuilder.toString());
		} else {
			objLfont = objLfont.deriveFont(Font.BOLD);
			this.setForeground(bolPselected ? Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR : Constants.objS_PEN_COLORS_DARK_GREEN_COLOR);
			this.setBackground(bolPselected ? objPjList.getSelectionBackground() : Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
			this.setText(Strings.doConcat(objPobject, Strings.strS_SPACE));
			this.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		}
		final boolean bolLspecialPatternsManager =
													this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_JM_PATTERN
														|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NO_PATTERN
														|| this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN
														|| this.objGcontrolJFrame.getFilteredPatternsNumber() == 0;
		this.setHorizontalAlignment(bolLspecialPatternsManager ? SwingConstants.CENTER : SwingConstants.LEFT);
		this.setFont(objLfont);
		this.setEnabled(!bolLspecialPatternsManager);
		this.setOpaque(true);
		return this;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strP
	 */
	@Override final public void setToolTipText(String strP) {
		if (Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS) && !Tools.isEmpty(strP)) {
			// && this.getFontMetrics(this.getFont()).stringWidth(strP) >
			// objGobjectsJListJScrollPane.getViewport().getView().getWidth()) {
			super.setToolTipText(strP);
		} else {
			super.setToolTipText(null);
		}
	}
}

/*
 * @(#)ObjectsJListCellRenderer.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
