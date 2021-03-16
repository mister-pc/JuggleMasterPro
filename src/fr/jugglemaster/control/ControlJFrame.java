/*
 * @(#)JugglePanel.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import fr.jugglemaster.control.ball.BallsJScrollBar;
import fr.jugglemaster.control.ball.BallsThreeStatesJCheckBox;
import fr.jugglemaster.control.color.AlternateColorsJCheckBox;
import fr.jugglemaster.control.color.AlternateColorsJScrollBar;
import fr.jugglemaster.control.color.BallsColorsJTextPane;
import fr.jugglemaster.control.color.BallsColorsThreeStatesJCheckBox;
import fr.jugglemaster.control.color.ColorActions;
import fr.jugglemaster.control.color.ColorChooserDropDownJButton;
import fr.jugglemaster.control.criteria.BallsNumberJCheckBox;
import fr.jugglemaster.control.criteria.BallsNumberJComboBox;
import fr.jugglemaster.control.criteria.FilterJLabel;
import fr.jugglemaster.control.criteria.FiltersJButton;
import fr.jugglemaster.control.criteria.FiltersJDialog;
import fr.jugglemaster.control.criteria.FiltersJMenuItem;
import fr.jugglemaster.control.criteria.FindJDialog;
import fr.jugglemaster.control.criteria.FindJMenuItem;
import fr.jugglemaster.control.criteria.InvalidPatternsJCheckBox;
import fr.jugglemaster.control.criteria.InvalidPatternsJLabel;
import fr.jugglemaster.control.criteria.MarkJButton;
import fr.jugglemaster.control.criteria.MarkJCheckBox;
import fr.jugglemaster.control.criteria.MarkJComboBox;
import fr.jugglemaster.control.criteria.SkillJCheckBox;
import fr.jugglemaster.control.criteria.SkillJComboBox;
import fr.jugglemaster.control.data.ClipboardJCheckBoxMenuItem;
import fr.jugglemaster.control.data.ClipboardJDialog;
import fr.jugglemaster.control.data.ConsoleJCheckBoxMenuItem;
import fr.jugglemaster.control.data.ConsoleJDialog;
import fr.jugglemaster.control.data.DataJCheckBoxMenuItem;
import fr.jugglemaster.control.data.DataJFrame;
import fr.jugglemaster.control.fx.CatchSoundsJCheckBox;
import fr.jugglemaster.control.fx.FXJCheckBox;
import fr.jugglemaster.control.fx.JugglerJScrollBar;
import fr.jugglemaster.control.fx.JugglerThreeStatesJCheckBox;
import fr.jugglemaster.control.fx.LightJCheckBox;
import fr.jugglemaster.control.fx.MetronomeJCheckBox;
import fr.jugglemaster.control.fx.MirrorJCheckBox;
import fr.jugglemaster.control.fx.SoundsJCheckBox;
import fr.jugglemaster.control.fx.StrobeJScrollBar;
import fr.jugglemaster.control.fx.StrobeThreeStatesJCheckBox;
import fr.jugglemaster.control.fx.ThrowSoundsJCheckBox;
import fr.jugglemaster.control.help.AboutJMenuItem;
import fr.jugglemaster.control.help.DevelopmentJMenuItem;
import fr.jugglemaster.control.help.LanguageJRadioButtonMenuItem;
import fr.jugglemaster.control.help.LicenceJMenuItem;
import fr.jugglemaster.control.help.LinksJMenuItemActionListener;
import fr.jugglemaster.control.io.ExtendedJMenu;
import fr.jugglemaster.control.io.ExtendedJMenuItem;
import fr.jugglemaster.control.io.ExtendedTransferHandler;
import fr.jugglemaster.control.io.FileActions;
import fr.jugglemaster.control.io.ImportPatternsJButton;
import fr.jugglemaster.control.io.LoadFileJMenuItem;
import fr.jugglemaster.control.io.NewPatternJMenuItem;
import fr.jugglemaster.control.io.QuitJMenuItem;
import fr.jugglemaster.control.io.RecentFilesExtendedJMenu;
import fr.jugglemaster.control.io.ReloadPatternsFilesJMenuItem;
import fr.jugglemaster.control.io.ScreenPlayJMenuItem;
import fr.jugglemaster.control.io.ScreenShotJMenuItem;
import fr.jugglemaster.control.io.WriteFileJMenuItem;
import fr.jugglemaster.control.motion.DefaultsJScrollBar;
import fr.jugglemaster.control.motion.DefaultsThreeStatesJCheckBox;
import fr.jugglemaster.control.motion.DwellJScrollBar;
import fr.jugglemaster.control.motion.FluidityJScrollBar;
import fr.jugglemaster.control.motion.HeightJScrollBar;
import fr.jugglemaster.control.motion.PlayPauseJButton;
import fr.jugglemaster.control.motion.SpeedJScrollBar;
import fr.jugglemaster.control.pattern.ObjectsJList;
import fr.jugglemaster.control.pattern.PatternJLabelMouseListener;
import fr.jugglemaster.control.pattern.PatternJTextField;
import fr.jugglemaster.control.pattern.ReloadPatternJButton;
import fr.jugglemaster.control.pattern.ReversePatternJButton;
import fr.jugglemaster.control.pattern.ShortcutsJComboBox;
import fr.jugglemaster.control.pref.PreferencesJDialog;
import fr.jugglemaster.control.pref.PreferencesJMenuItem;
import fr.jugglemaster.control.siteswap.ImportSiteswapsJButton;
import fr.jugglemaster.control.siteswap.RefreshSiteswapJButton;
import fr.jugglemaster.control.siteswap.ReverseSiteswapJCheckBox;
import fr.jugglemaster.control.siteswap.SiteswapJTextPane;
import fr.jugglemaster.control.siteswap.SiteswapThreeStatesJCheckBox;
import fr.jugglemaster.control.style.ImportStylesJButton;
import fr.jugglemaster.control.style.ReverseStyleJCheckBox;
import fr.jugglemaster.control.style.SortStylesJButton;
import fr.jugglemaster.control.style.StyleJCheckBox;
import fr.jugglemaster.control.style.StyleJComboBox;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.control.util.MenusTools;
import fr.jugglemaster.control.util.ThreeStatesButtonModel;
import fr.jugglemaster.control.window.PopUpJDialog;
import fr.jugglemaster.control.window.SplashScreenJWindow;
import fr.jugglemaster.engine.JuggleMasterPro;
import fr.jugglemaster.pattern.PatternsManager;
import fr.jugglemaster.pattern.Siteswap;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.user.Preferences;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ControlJFrame extends JFrame implements Runnable, Comparator<Language>, UncaughtExceptionHandler {

	/**
	 * Constructs
	 * 
	 * @param objPjuggleMasterPro
	 *            : JuggleMasterPro depending main object
	 */
	public ControlJFrame(JuggleMasterPro objPjuggleMasterPro) {

		// Field initializations :
		this.objGjuggleMasterPro = objPjuggleMasterPro;
		this.bolGexportInitialPatterns = false;
		this.setExportCurrentPatterns(false);
		this.bolGdefaultsFxPause = false;

		// Look and feel :
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while applying look and feel");
		}

		// Tooltip properties :
		this.setToolTipsProperties();

		UIManager.put("ToolTip.font", this.getFont());

		// Languages :
		this.intGlanguagesNumber = this.doLoadJuggleLanguages();

		// Program main exception :
		if (Constants.bolS_UNCLASS_CATCH_ALL_EXCEPTIONS) {
			try {
				Thread.setDefaultUncaughtExceptionHandler(this);
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while setting uncaught exception handler");
			}
		}

		// Frame properties :
		this.objGsplashScreenJWindow = new SplashScreenJWindow(this, this.getLanguageString(Language.intS_SPLASH_LOADING_PLEASE_WAIT), true);
		this.objGjuggleMasterPro.objGdataJFrame = new DataJFrame(this, this.objGjuggleMasterPro);

		this.setTransferHandler(new ExtendedTransferHandler(this, true, true));
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_LOADING_GRAPHICS);
		this.objGjuggleMasterPro.doCreateJuggleMasterProFrame(this);

		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_COMPONENTS);
		this.doCreateWidgets();

		// Create menus :
		this.doCreateMenuBar();
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_PATTERNS_MENU);
		this.doCreatePatternsMenu();
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_FILE_MENU);
		this.doCreateFileMenu();
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_EDITION_MENU);
		this.doCreateEditionMenu();
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_HELP_MENU);
		this.doCreateHelpMenu(this.intGlanguagesNumber);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPactive
	 * @param objPcomponent
	 */
	final private static void setWidgetPreferredSize(boolean bolPactive, Component objPcomponent) {
		ControlJFrame.setWidgetPreferredSize(bolPactive, objPcomponent, objPcomponent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPactive
	 * @param objPofComponent
	 * @param objPfromComponent
	 */
	final private static void setWidgetPreferredSize(boolean bolPactive, Component objPofComponent, Component objPfromComponent) {
		if (bolPactive) {
			Dimension objLdimension = objPfromComponent != null ? objPfromComponent.getSize() : new Dimension();
			if (objPofComponent instanceof JComboBox<?>) {
				objLdimension = new Dimension((int) objLdimension.getWidth() + 3, (int) objLdimension.getHeight());
			}
			objPofComponent.setMinimumSize(objLdimension);
			if (objPfromComponent != null) {
				objPofComponent.setPreferredSize(objLdimension);
			}
		} else {
			objPofComponent.setPreferredSize(null);
		}
	}

	@Override final public int compare(Language objPfirstJuggleLanguage, Language objPsecondJuggleLanguage) {
		return objPfirstJuggleLanguage	.getPropertyValueString(Language.intS_LANGUAGE_NAME)
										.compareToIgnoreCase(objPsecondJuggleLanguage.getPropertyValueString(Language.intS_LANGUAGE_NAME));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void dispose() {
		if (this.objGsplashScreenJWindow != null) {
			this.objGsplashScreenJWindow.setVisible(false);
			this.objGsplashScreenJWindow.dispose();
			this.objGsplashScreenJWindow = null;
		}
		if (this.objGconsoleJDialog != null) {
			this.objGconsoleJDialog.setVisible(false);
			this.objGconsoleJDialog.dispose();
			this.objGconsoleJDialog = null;
		}
		this.doHidePopUps();
		super.dispose();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doActivatePanel() {
		if (this.getExtendedState() != Frame.ICONIFIED) {
			if (this.objGjuggleMasterPro.getFrame().bolGdontFocusJuggleMasterPro) {
				this.objGjuggleMasterPro.getFrame().bolGdontFocusJuggleMasterPro = false;
				// try {
				if (!this.objGpatternJTextField.isFocusOwner()	&& !this.objGsiteswapJTextPane.isFocusOwner()
					&& !this.objGstyleJComboBox.isFocusOwner() && !this.objGcolorsJTextPane.isFocusOwner() && !this.objGballsJScrollBar.isFocusOwner()
					&& !this.objGjugglerJScrollBar.isFocusOwner() && !this.objGheightJScrollBar.isFocusOwner()
					&& !this.objGspeedJScrollBar.isFocusOwner() && !this.objGdwellJScrollBar.isFocusOwner()
					&& !this.objGfluidityJScrollBar.isFocusOwner()) {
					this.objGobjectsJList.requestFocusInWindow();
				}
				// } catch (final Throwable objPthrowable) {
				// Tools.log("Error while setting focus in window");
				// }
			} else {
				try {
					this.objGjuggleMasterPro.getFrame().setExtendedState(Frame.NORMAL);
					this.objGjuggleMasterPro.getFrame().setVisible(true);
				} catch (final Throwable objPthrowable) {}
			}
		}
	}

	final public void doAddAction(int intPaction) {
		this.objGjuggleMasterPro.doAddAction(intPaction);
	}

	final private JPanel doAddBottomWidgets() {

		// Bottom panel :
		final JPanel objLbottomJPanel = new JPanel(new GridBagLayout());
		objLbottomJPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		objLbottomJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLbottomPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0,
																													GridBagConstraints.RELATIVE,
																													1,
																													1,
																													GridBagConstraints.CENTER,
																													GridBagConstraints.HORIZONTAL,
																													1F,
																													0.F);

		// Filter button :
		final JPanel objLfiltersJPanel = new JPanel(new GridBagLayout());
		objLfiltersJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLfiltersPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																														0,
																														1,
																														1);

		objLfiltersJPanel.add(this.objGfiltersJButton, objLfiltersPanelExtendedGridBagConstraints);

		// Ball number :
		final JPanel objLballsNumberJPanel = new JPanel(new GridBagLayout());
		objLballsNumberJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLballsNumberPanelExtendedGridBagConstraints =
																						new ExtendedGridBagConstraints(	0,
																														0,
																														1,
																														1,
																														GridBagConstraints.CENTER);
		objLballsNumberJPanel.add(this.objGballsNumberJCheckBox, objLballsNumberPanelExtendedGridBagConstraints);
		objLballsNumberPanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLballsNumberJPanel.add(this.objGfromBallsNumberJLabel, objLballsNumberPanelExtendedGridBagConstraints);
		objLballsNumberPanelExtendedGridBagConstraints.setMargins(0, 0, 3, 0);
		objLballsNumberPanelExtendedGridBagConstraints.setGridLocation(2, 0);
		objLballsNumberJPanel.add(this.objGlowBallsNumberJComboBox, objLballsNumberPanelExtendedGridBagConstraints);
		objLballsNumberPanelExtendedGridBagConstraints.setGridLocation(3, 0);
		objLballsNumberPanelExtendedGridBagConstraints.setMargins(0, 0, 5, 0);
		objLballsNumberJPanel.add(this.objGtoBallsNumberJLabel, objLballsNumberPanelExtendedGridBagConstraints);
		objLballsNumberPanelExtendedGridBagConstraints.setGridLocation(4, 0);
		objLballsNumberJPanel.add(this.objGhighBallsNumberJComboBox, objLballsNumberPanelExtendedGridBagConstraints);
		objLballsNumberPanelExtendedGridBagConstraints.setGridLocation(5, 0);
		objLballsNumberJPanel.add(this.objGballsNumberJLabel, objLballsNumberPanelExtendedGridBagConstraints);

		objLfiltersPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.F);
		objLfiltersJPanel.add(new JPanel(), objLfiltersPanelExtendedGridBagConstraints);
		objLfiltersPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0F, 0.F);
		objLfiltersPanelExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLfiltersJPanel.add(objLballsNumberJPanel, objLfiltersPanelExtendedGridBagConstraints);

		// Skill :
		final JPanel objLskillJPanel = new JPanel(new GridBagLayout());
		objLskillJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLskillPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(	0,
																													0,
																													1,
																													1,
																													GridBagConstraints.EAST);
		objLskillJPanel.add(this.objGskillJCheckBox, objLskillPanelExtendedGridBagConstraints);
		objLskillPanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLskillJPanel.add(this.objGfromSkillJLabel, objLskillPanelExtendedGridBagConstraints);
		objLskillPanelExtendedGridBagConstraints.setMargins(0, 0, 3, 0);
		objLskillPanelExtendedGridBagConstraints.setGridLocation(2, 0);
		objLskillJPanel.add(this.objGlowSkillJComboBox, objLskillPanelExtendedGridBagConstraints);
		objLskillPanelExtendedGridBagConstraints.setGridLocation(3, 0);
		objLskillPanelExtendedGridBagConstraints.setMargins(0, 0, 5, 0);
		objLskillJPanel.add(this.objGtoSkillJLabel, objLskillPanelExtendedGridBagConstraints);
		objLskillPanelExtendedGridBagConstraints.setGridLocation(4, 0);
		objLskillJPanel.add(this.objGhighSkillJComboBox, objLskillPanelExtendedGridBagConstraints);

		objLfiltersPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.F);
		objLfiltersJPanel.add(new JPanel(), objLfiltersPanelExtendedGridBagConstraints);
		objLfiltersPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0F, 0.F);

		objLfiltersPanelExtendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		objLfiltersJPanel.add(objLskillJPanel, objLfiltersPanelExtendedGridBagConstraints);

		// Mark :
		final JPanel objLmarkJPanel = new JPanel(new GridBagLayout());
		objLmarkJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLmarkPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(	0,
																													0,
																													1,
																													1,
																													GridBagConstraints.EAST);
		objLmarkJPanel.add(this.objGmarkJCheckBox, objLmarkPanelExtendedGridBagConstraints);
		objLmarkPanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLmarkJPanel.add(this.objGfromMarkJLabel, objLmarkPanelExtendedGridBagConstraints);
		objLmarkPanelExtendedGridBagConstraints.setMargins(0, 0, 3, 0);
		objLmarkPanelExtendedGridBagConstraints.setGridLocation(2, 0);
		objLmarkJPanel.add(this.objGlowMarkJComboBox, objLmarkPanelExtendedGridBagConstraints);
		objLmarkPanelExtendedGridBagConstraints.setGridLocation(3, 0);
		objLmarkPanelExtendedGridBagConstraints.setMargins(0, 0, 5, 0);
		objLmarkJPanel.add(this.objGtoMarkJLabel, objLmarkPanelExtendedGridBagConstraints);
		objLmarkPanelExtendedGridBagConstraints.setGridLocation(4, 0);
		objLmarkJPanel.add(this.objGhighMarkJComboBox, objLmarkPanelExtendedGridBagConstraints);
		objLmarkPanelExtendedGridBagConstraints.setGridLocation(5, 0);
		objLmarkJPanel.add(this.objGglobalMarkJButton, objLmarkPanelExtendedGridBagConstraints);

		objLfiltersPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0.F);
		objLfiltersJPanel.add(new JPanel(), objLfiltersPanelExtendedGridBagConstraints);
		objLfiltersPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0F, 0.F);

		objLfiltersPanelExtendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		objLfiltersJPanel.add(objLmarkJPanel, objLfiltersPanelExtendedGridBagConstraints);

		objLbottomJPanel.add(objLfiltersJPanel, objLbottomPanelExtendedGridBagConstraints);

		// Shortcuts & invalid patterns :
		final JPanel objLshortcutsJPanel = new JPanel(new GridBagLayout());
		objLshortcutsJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLshortcutsPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																														0,
																														1,
																														1,
																														3,
																														0,
																														5,
																														0,
																														GridBagConstraints.HORIZONTAL,
																														1F,
																														0.F);
		objLshortcutsJPanel.add(this.objGshortcutsJComboBox, objLshortcutsPanelExtendedGridBagConstraints);
		objLshortcutsPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0F, 0F);
		objLshortcutsJPanel.add(this.objGinvalidPatternsJCheckBox, objLshortcutsPanelExtendedGridBagConstraints);
		objLshortcutsPanelExtendedGridBagConstraints.setMargins(0, 0, 0, 0);
		objLshortcutsJPanel.add(this.objGinvalidPatternsJLabel, objLshortcutsPanelExtendedGridBagConstraints);
		objLbottomJPanel.add(objLshortcutsJPanel, objLbottomPanelExtendedGridBagConstraints);

		// Object list :
		objLbottomPanelExtendedGridBagConstraints.setGridLocation(0, 2);
		objLbottomPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.BOTH, 1F, 1F);
		objLbottomJPanel.add(this.objGobjectsJListJScrollPane, objLbottomPanelExtendedGridBagConstraints);

		return objLbottomJPanel;
	}

	final private JPanel doAddMiddleWidgets() {

		// Middle panel :
		final JPanel objLmiddleJPanel = new JPanel(new GridBagLayout());
		objLmiddleJPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		objLmiddleJPanel.setOpaque(true);

		// Height, dwell, speed & fluidity labels :
		final ExtendedGridBagConstraints objLmiddlePanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0, 0, 1, 1, 0, 0, 5, 3);
		objLmiddleJPanel.add(this.objGheightJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(0, 1);
		objLmiddleJPanel.add(this.objGdwellJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setMargins(0, 0, 0, 3);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(5, 0);
		objLmiddleJPanel.add(this.objGspeedJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(5, 1);
		objLmiddleJPanel.add(this.objGfluidityJLabel, objLmiddlePanelExtendedGridBagConstraints);

		// Height, dwell, speed & fluidity values :
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(3, 0);
		objLmiddleJPanel.add(this.objGheightValueJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(3, 1);
		objLmiddleJPanel.add(this.objGdwellValueJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(8, 0);
		objLmiddlePanelExtendedGridBagConstraints.setMargins(0, 0, 0, 0);
		objLmiddleJPanel.add(this.objGspeedValueJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(8, 1);
		objLmiddleJPanel.add(this.objGfluidityValueJLabel, objLmiddlePanelExtendedGridBagConstraints);

		// Height, speed, dwell & fluidity colons :
		objLmiddlePanelExtendedGridBagConstraints.setMargins(0, 0, 0, 3);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLmiddleJPanel.add(this.objGheightColonsJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(6, 0);
		objLmiddleJPanel.add(this.objGspeedColonsJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(1, 1);
		objLmiddleJPanel.add(this.objGdwellColonsJLabel, objLmiddlePanelExtendedGridBagConstraints);
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(6, 1);
		objLmiddleJPanel.add(this.objGfluidityColonsJLabel, objLmiddlePanelExtendedGridBagConstraints);

		// Play/pause button :
		objLmiddlePanelExtendedGridBagConstraints.setGridBounds(4, 0, 1, 2);
		objLmiddlePanelExtendedGridBagConstraints.setMargins(0, 0, 3, 6);
		objLmiddleJPanel.add(this.objGplayPauseJButton, objLmiddlePanelExtendedGridBagConstraints);

		// Height scrollbar :
		objLmiddlePanelExtendedGridBagConstraints.setGridBounds(2, 0, 1, 1);
		objLmiddlePanelExtendedGridBagConstraints.setMargins(0, 0, 0, 3);
		objLmiddlePanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.5F, 0F);
		objLmiddleJPanel.add(this.objGheightJScrollBar, objLmiddlePanelExtendedGridBagConstraints);

		// Speed scrollbar :
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(7, 0);
		objLmiddleJPanel.add(this.objGspeedJScrollBar, objLmiddlePanelExtendedGridBagConstraints);

		// Dwell scrollbar :
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(2, 1);
		objLmiddleJPanel.add(this.objGdwellJScrollBar, objLmiddlePanelExtendedGridBagConstraints);

		// Fluidity scrollbar :
		objLmiddlePanelExtendedGridBagConstraints.setGridLocation(7, 1);
		objLmiddleJPanel.add(this.objGfluidityJScrollBar, objLmiddlePanelExtendedGridBagConstraints);

		return objLmiddleJPanel;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doAddTopLeftWidgets() {

		// Reload pattern button & pattern label :
		final JPanel objLpatternLeftJPanel = new JPanel(new GridBagLayout());
		objLpatternLeftJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLpatternLeftPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0, 0, 1, 1);
		objLpatternLeftJPanel.add(this.objGreloadPatternJButton, objLpatternLeftPanelExtendedGridBagConstraints);
		objLpatternLeftPanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLpatternLeftPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1F, 0F);
		objLpatternLeftPanelExtendedGridBagConstraints.setMargins(0, 0, 5, 0);
		objLpatternLeftJPanel.add(this.objGpatternJLabel, objLpatternLeftPanelExtendedGridBagConstraints);

		this.objGtopJLayeredPane = new JLayeredPane();
		this.objGtopJLayeredPane.setLayout(new GridBagLayout());
		this.objGtopJLayeredPane.setOpaque(true);
		final ExtendedGridBagConstraints objLtopLeftPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0, 0, 1, 1);
		this.objGtopJLayeredPane.add(objLpatternLeftJPanel, objLtopLeftPanelExtendedGridBagConstraints);

		// Siteswap checkbox :
		objLtopLeftPanelExtendedGridBagConstraints.setGridLocation(0, 1);
		this.objGtopJLayeredPane.add(this.objGsiteswapThreeStatesJCheckBox, objLtopLeftPanelExtendedGridBagConstraints);

		// Style checkboxes :
		final JPanel objLstyleJPanel = new JPanel(new GridBagLayout());
		objLstyleJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLdoubleLeftPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0, 0, 1, 1);
		objLstyleJPanel.add(this.objGstyleJCheckBox, objLdoubleLeftPanelExtendedGridBagConstraints);
		objLdoubleLeftPanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLstyleJPanel.add(this.objGfXJCheckBox, objLdoubleLeftPanelExtendedGridBagConstraints);
		objLtopLeftPanelExtendedGridBagConstraints.setGridLocation(0, 2);
		this.objGtopJLayeredPane.add(objLstyleJPanel, objLtopLeftPanelExtendedGridBagConstraints);

		// Ball checkboxes :
		objLtopLeftPanelExtendedGridBagConstraints.setGridLocation(0, 3);
		this.objGtopJLayeredPane.add(this.objGballsThreeStatesJCheckBox, objLtopLeftPanelExtendedGridBagConstraints);
		// objLdoubleLeftPanelExtendedGridBagConstraints.setGridLocation(1, 1);
		// objLdoubleLeftJPanel.add(this.objGthrowsJCheckBox, objLdoubleLeftPanelExtendedGridBagConstraints);
		// objLtopLeftPanelExtendedGridBagConstraints.setGridLocation(0, 3);
		// this.objGtopJLayeredPane.add(objLdoubleLeftJPanel, objLtopLeftPanelExtendedGridBagConstraints);

		// Color checkboxes :
		// JPanel objLcolorsJPanel = new JPanel(new GridBagLayout());
		// objLdoubleLeftPanelExtendedGridBagConstraints.setGridLocation(0, 0);
		// objLcolorsJPanel.add(this.objGcolorsJCheckBox, objLdoubleLeftPanelExtendedGridBagConstraints);
		// objLdoubleLeftPanelExtendedGridBagConstraints.setGridLocation(1, 0);
		// objLcolorsJPanel.add(this.objGlightColorsJCheckBox, objLdoubleLeftPanelExtendedGridBagConstraints);
		objLtopLeftPanelExtendedGridBagConstraints.setGridLocation(0, 4);
		this.objGtopJLayeredPane.add(this.objGcolorsThreeStatesJCheckBox, objLtopLeftPanelExtendedGridBagConstraints);

		// Defaults :
		objLtopLeftPanelExtendedGridBagConstraints.setGridLocation(0, 5);
		this.objGtopJLayeredPane.add(this.objGdefaultsThreeStatesJCheckBox, objLtopLeftPanelExtendedGridBagConstraints);

		// Sound checkboxes :
		objLtopLeftPanelExtendedGridBagConstraints.setGridBounds(0, 6, 1, 1);
		this.objGtopJLayeredPane.add(this.objGsoundsJCheckBox, objLtopLeftPanelExtendedGridBagConstraints);

		// Top panel colons :
		final ExtendedGridBagConstraints objLtopPanelColonsExtendedGridBagConstraints = new ExtendedGridBagConstraints(1, 0, 1, 1, 0, 0, 3, 3);
		this.objGtopJLayeredPane.add(this.objGpatternColonsJLabel, objLtopPanelColonsExtendedGridBagConstraints);
		objLtopPanelColonsExtendedGridBagConstraints.setGridLocation(1, 1);
		this.objGtopJLayeredPane.add(this.objGsiteswapColonsJLabel, objLtopPanelColonsExtendedGridBagConstraints);
		objLtopPanelColonsExtendedGridBagConstraints.setGridLocation(1, 2);
		this.objGtopJLayeredPane.add(this.objGstyleColonsJLabel, objLtopPanelColonsExtendedGridBagConstraints);
		objLtopPanelColonsExtendedGridBagConstraints.setGridLocation(1, 3);
		this.objGtopJLayeredPane.add(this.objGballsColonsJLabel, objLtopPanelColonsExtendedGridBagConstraints);
		objLtopPanelColonsExtendedGridBagConstraints.setGridLocation(1, 4);
		this.objGtopJLayeredPane.add(this.objGcolorsColonsJLabel, objLtopPanelColonsExtendedGridBagConstraints);
		objLtopPanelColonsExtendedGridBagConstraints.setGridLocation(1, 5);
		this.objGtopJLayeredPane.add(this.objGdefaultsColonsJLabel, objLtopPanelColonsExtendedGridBagConstraints);
		objLtopPanelColonsExtendedGridBagConstraints.setGridLocation(1, 6);
		this.objGtopJLayeredPane.add(this.objGsoundsColonsJLabel, objLtopPanelColonsExtendedGridBagConstraints);
	}

	final private void doAddTopMiddleWidgets() {

		// Pattern reverse button, pattern textfield & pattern import button :
		final JPanel objLpatternMiddleJPanel = new JPanel(new GridBagLayout());
		objLpatternMiddleJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLpatternMiddlePanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0, 0, 1, 1);
		objLpatternMiddleJPanel.add(this.objGreversePatternJButton, objLpatternMiddlePanelExtendedGridBagConstraints);
		objLpatternMiddlePanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1F, 0F);
		objLpatternMiddlePanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLpatternMiddleJPanel.add(this.objGpatternJTextField, objLpatternMiddlePanelExtendedGridBagConstraints);
		objLpatternMiddlePanelExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0F, 0F);
		objLpatternMiddlePanelExtendedGridBagConstraints.setGridLocation(2, 0);
		objLpatternMiddleJPanel.add(this.objGskillJComboBox, objLpatternMiddlePanelExtendedGridBagConstraints);
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			objLpatternMiddlePanelExtendedGridBagConstraints.setGridLocation(3, 0);
			objLpatternMiddleJPanel.add(this.objGimportPatternsJButton, objLpatternMiddlePanelExtendedGridBagConstraints);
		}

		// Refresh siteswap button, siteswap textpane & siteswap import button :
		final JPanel objLsiteswapMiddleJPanel = new JPanel(new GridBagLayout());
		objLsiteswapMiddleJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLsiteswapMiddlePanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0, 0, 1, 1);
		objLsiteswapMiddleJPanel.add(this.objGrefreshSiteswapJButton, objLsiteswapMiddlePanelExtendedGridBagConstraints);
		objLsiteswapMiddlePanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1F, 0F);
		objLsiteswapMiddlePanelExtendedGridBagConstraints.setGridLocation(1, 0);

		objLsiteswapMiddleJPanel.add(this.objGsiteswapJTextPane, objLsiteswapMiddlePanelExtendedGridBagConstraints);
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			objLsiteswapMiddlePanelExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0F, 0F);
			objLsiteswapMiddlePanelExtendedGridBagConstraints.setGridLocation(2, 0);
			objLsiteswapMiddleJPanel.add(this.objGimportSiteswapsJButton, objLsiteswapMiddlePanelExtendedGridBagConstraints);
		}

		// Style sort button, style choice & style import button :
		final JPanel objLstyleMiddleJPanel = new JPanel(new GridBagLayout());
		objLstyleMiddleJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLstyleMiddlePanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(0, 0, 1, 1);
		objLstyleMiddleJPanel.add(this.objGsortStylesJButton, objLstyleMiddlePanelExtendedGridBagConstraints);
		objLstyleMiddlePanelExtendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1F, 0F);
		objLstyleMiddlePanelExtendedGridBagConstraints.setGridLocation(1, 0);
		objLstyleMiddleJPanel.add(this.objGstyleJComboBox, objLstyleMiddlePanelExtendedGridBagConstraints);
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			objLstyleMiddlePanelExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0F, 0F);
			objLstyleMiddlePanelExtendedGridBagConstraints.setGridLocation(2, 0);
			objLstyleMiddleJPanel.add(this.objGimportStylesJButton, objLstyleMiddlePanelExtendedGridBagConstraints);
		}

		// Textfield zones :
		final ExtendedGridBagConstraints objLtopTextFieldsPanelsExtendedGridBagConstraints = new ExtendedGridBagConstraints(2,
																															0,
																															5,
																															1,
																															GridBagConstraints.CENTER,
																															GridBagConstraints.HORIZONTAL,
																															1F,
																															0F);
		this.objGtopJLayeredPane.add(objLpatternMiddleJPanel, objLtopTextFieldsPanelsExtendedGridBagConstraints);
		objLtopTextFieldsPanelsExtendedGridBagConstraints.setGridLocation(2, 1);
		this.objGtopJLayeredPane.add(objLsiteswapMiddleJPanel, objLtopTextFieldsPanelsExtendedGridBagConstraints);
		objLtopTextFieldsPanelsExtendedGridBagConstraints.setGridLocation(2, 2);
		this.objGtopJLayeredPane.add(objLstyleMiddleJPanel, objLtopTextFieldsPanelsExtendedGridBagConstraints);

		// Start help message :
		this.objGtopJLayeredPane.add(	this.objGstartJPanel,
										new ExtendedGridBagConstraints(0, 0, 7, 3, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1F, 1F));
		this.objGtopJLayeredPane.setLayer(this.objGstartJPanel, JLayeredPane.PALETTE_LAYER);
	}

	final private void doAddTopRightWidgets() {

		final ExtendedGridBagConstraints objLtopRightPanelCheckBoxesExtendedGridBagConstraints = new ExtendedGridBagConstraints(7,
																																GridBagConstraints.RELATIVE,
																																1,
																																1);
		this.objGtopJLayeredPane.add(this.objGmirrorJCheckBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridLocation(8, GridBagConstraints.RELATIVE);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		this.objGtopJLayeredPane.add(this.objGmarkJComboBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridLocation(9, GridBagConstraints.RELATIVE);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setMargins(0, 0, 3, 0);
		this.objGtopJLayeredPane.add(this.objGlocalMarkJButton, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setMargins(0, 0, 0, 0);

		// Reverse siteswap :
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(7, GridBagConstraints.RELATIVE, 1, 1);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setFilling(GridBagConstraints.NONE, 0.0F, 0.0F);
		this.objGtopJLayeredPane.add(this.objGreverseSiteswapJCheckBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);

		// Siteswap color :
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(8, GridBagConstraints.RELATIVE, 1, 1);
		this.objGtopJLayeredPane.add(this.objGsiteswapColorChooserDropDownJButton, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(9, GridBagConstraints.RELATIVE, 1, 1);
		this.objGtopJLayeredPane.add(this.objGsiteswapColorJLabel, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);

		// Reverse style :
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(7, GridBagConstraints.RELATIVE, 3, 1);
		this.objGtopJLayeredPane.add(this.objGreverseStyleJCheckBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);

		// Juggler visibility :
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(7, GridBagConstraints.RELATIVE, 1, 1);
		this.objGtopJLayeredPane.add(this.objGjugglerThreeStatesJCheckBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(8, GridBagConstraints.RELATIVE, 1, 1);

		// Juggler color :
		this.objGtopJLayeredPane.add(this.objGjugglerColorChooserDropDownJButton, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(9, GridBagConstraints.RELATIVE, 1, 1);
		this.objGtopJLayeredPane.add(this.objGjugglerColorJLabel, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);

		// Alternate colors :
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(7, GridBagConstraints.RELATIVE, 3, 1);
		this.objGtopJLayeredPane.add(this.objGalternateColorsJCheckBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		this.objGtopJLayeredPane.add(this.objGstrobeThreeStatesJCheckBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);

		// Light :
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(7, GridBagConstraints.RELATIVE, 1, 1);
		this.objGtopJLayeredPane.add(this.objGlightJCheckBox, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);

		// Background color :
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(8, GridBagConstraints.RELATIVE, 1, 1);
		this.objGtopJLayeredPane.add(this.objGbackgroundColorChooserDropDownJButton, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setInside(GridBagConstraints.WEST, 0, 0);
		objLtopRightPanelCheckBoxesExtendedGridBagConstraints.setGridBounds(9, GridBagConstraints.RELATIVE, 1, 1);
		this.objGtopJLayeredPane.add(this.objGbackgroundColorJLabel, objLtopRightPanelCheckBoxesExtendedGridBagConstraints);
	}

	final private void doAddTopScrollBars() {

		// Ball left-scrollbar :
		final ExtendedGridBagConstraints objLtopScrollBarsPanelsExtendedGridBagConstraints = new ExtendedGridBagConstraints(2,
																															3,
																															1,
																															1,
																															0,
																															0,
																															0,
																															3,
																															GridBagConstraints.HORIZONTAL,
																															0.5F,
																															0F);
		this.objGtopJLayeredPane.add(this.objGballsJScrollBar, objLtopScrollBarsPanelsExtendedGridBagConstraints);

		// Color left-textfield :
		objLtopScrollBarsPanelsExtendedGridBagConstraints.setGridLocation(2, 4);
		// JPanel objLjPanel = new JPanel(new GridBagLayout());
		// JScrollPane objLcolorsJScrollPane = new JScrollPane(
		// this.objGcolorsJTextPane,
		// JScrollPane.VERTICAL_SCROLLBAR_NEVER,
		// JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// objLcolorsJScrollPane.setBorder(Constants.objS_GRAPHICS_EMPTY_BORDER);
		// this.objGcolorsJScrollPane.setBackground(Color.YELLOW);

		// this.objGcolorsJScrollPane.setRowHeader(null);
		// this.objGcolorsJScrollPane.setColumnHeader(null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.LOWER_LEADING_CORNER, null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.LOWER_TRAILING_CORNER, null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.UPPER_LEADING_CORNER, null);
		// this.objGcolorsJScrollPane.setCorner(ScrollPaneConstants.UPPER_TRAILING_CORNER, null);
		// this.objGcolorsJScrollPane.add(this.objGcolorsJScrollPane, new ExtendedGridBagConstraints(
		// 0,
		// 0,
		// 1,
		// 1,
		// ExtendedGridBagConstraints.BOTH,
		// 1.0F,
		// 1.0F));
		// objLjPanel.add(this.objGcolorsJScrollPane, new ExtendedGridBagConstraints(
		// 0,
		// 0,
		// 1,
		// 1,
		// ExtendedGridBagConstraints.NONE,
		// 1.0F,
		// 1.0F));

		// this.objGtopJLayeredPane.add(
		// this.objGcolorsJScrollPane,
		// objLtopScrollBarsPanelsExtendedGridBagConstraints);
		this.objGtopJLayeredPane.add(this.objGcolorsJTextPane, objLtopScrollBarsPanelsExtendedGridBagConstraints);

		// JScrollPane objLjScrollPane = new JScrollPane(
		// objGcolorsJTextPane,
		// JScrollPane.VERTICAL_SCROLLBAR_NEVER,
		// JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// objLjScrollPane.setLayout(new BoxLayout(objLjScrollPane, BoxLayout.X_AXIS));
		// objLjScrollPane.setOpaque(true);
		// objLjScrollPane.setBackground(Color.YELLOW);
		// objLjScrollPane.setBorder(Constants.objS_GRAPHICS_EMPTY_BORDER);

		// JPanel objLcolorsContainerJPanel = new JPanel(new BorderLayout());
		// objLcolorsContainerJPanel.setOpaque(true);
		// objLcolorsContainerJPanel.add(objGcolorsJTextPane);
		// JScrollPane objLjScrollPane = new JScrollPane(objLcolorsContainerJPanel,
		// JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// objLjScrollPane.setOpaque(true);

		// Default left-scrollbar :
		objLtopScrollBarsPanelsExtendedGridBagConstraints.setGridLocation(2, 5);
		this.objGtopJLayeredPane.add(this.objGdefaultsJScrollBar, objLtopScrollBarsPanelsExtendedGridBagConstraints);

		// Juggler right-scrollbar :
		objLtopScrollBarsPanelsExtendedGridBagConstraints.setGridLocation(6, 3);
		objLtopScrollBarsPanelsExtendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		objLtopScrollBarsPanelsExtendedGridBagConstraints.setMargins(0, 0, 3, 0);
		this.objGtopJLayeredPane.add(this.objGjugglerJScrollBar, objLtopScrollBarsPanelsExtendedGridBagConstraints);

		// Alternate color right-scrollbar :
		objLtopScrollBarsPanelsExtendedGridBagConstraints.setGridLocation(6, 4);
		this.objGtopJLayeredPane.add(this.objGalternateColorsJScrollBar, objLtopScrollBarsPanelsExtendedGridBagConstraints);

		// Strobe right-scrollbar :
		objLtopScrollBarsPanelsExtendedGridBagConstraints.setGridLocation(6, 5);
		this.objGtopJLayeredPane.add(this.objGstrobeJScrollBar, objLtopScrollBarsPanelsExtendedGridBagConstraints);

		// Ball left-value :
		final ExtendedGridBagConstraints objLtopJLabelsPanelsExtendedGridBagConstraints = new ExtendedGridBagConstraints(3, 3, 1, 1, 0, 0, 0, 3);
		this.objGtopJLayeredPane.add(this.objGballsValueJLabel, objLtopJLabelsPanelsExtendedGridBagConstraints);

		// Color left-value and button :
		objLtopJLabelsPanelsExtendedGridBagConstraints.setGridLocation(3, 4);
		final JPanel objLcolorsValueAndChoiceJPanel = new JPanel(new GridBagLayout());
		final ExtendedGridBagConstraints objLcolorsValueAndChoiceExtendedGridBagConstraints =
																							new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																															0,
																															1,
																															1,
																															GridBagConstraints.HORIZONTAL,
																															0.5F,
																															0.0F);
		objLcolorsValueAndChoiceJPanel.add(this.objGcolorsValueJLabel, objLcolorsValueAndChoiceExtendedGridBagConstraints);
		objLcolorsValueAndChoiceJPanel.add(this.objGballColorChooserDropDownJButton, objLcolorsValueAndChoiceExtendedGridBagConstraints);
		this.objGtopJLayeredPane.add(objLcolorsValueAndChoiceJPanel, objLtopJLabelsPanelsExtendedGridBagConstraints);

		// Default left-value :
		objLtopJLabelsPanelsExtendedGridBagConstraints.setGridLocation(3, 5);
		this.objGtopJLayeredPane.add(this.objGdefaultsValueJLabel, objLtopJLabelsPanelsExtendedGridBagConstraints);

		// Juggler right-value :
		objLtopJLabelsPanelsExtendedGridBagConstraints.setInside(GridBagConstraints.EAST, 0, 0);
		objLtopJLabelsPanelsExtendedGridBagConstraints.setGridLocation(5, 3);
		this.objGtopJLayeredPane.add(this.objGjugglerValueJLabel, objLtopJLabelsPanelsExtendedGridBagConstraints);

		// Alternate color right-value :
		objLtopJLabelsPanelsExtendedGridBagConstraints.setGridLocation(5, 4);
		this.objGtopJLayeredPane.add(this.objGalternateColorsValueJLabel, objLtopJLabelsPanelsExtendedGridBagConstraints);

		// Strobe right-value :
		objLtopJLabelsPanelsExtendedGridBagConstraints.setGridLocation(5, 5);
		this.objGtopJLayeredPane.add(this.objGstrobeValueJLabel, objLtopJLabelsPanelsExtendedGridBagConstraints);

		// Top panel middle specific sounds :
		final JPanel objLtopPanelMiddleSoundsJPanel = new JPanel(new GridBagLayout());
		objLtopPanelMiddleSoundsJPanel.setOpaque(true);
		final ExtendedGridBagConstraints objLtopPanelMiddleSoundsExtendedGridBagConstraints =
																							new ExtendedGridBagConstraints(	0,
																															0,
																															1,
																															1,
																															GridBagConstraints.HORIZONTAL,
																															0.25F,
																															0F);
		objLtopPanelMiddleSoundsJPanel.add(this.objGcatchSoundsJCheckBox, objLtopPanelMiddleSoundsExtendedGridBagConstraints);
		objLtopPanelMiddleSoundsExtendedGridBagConstraints.setGridLocation(1, 0);
		objLtopPanelMiddleSoundsJPanel.add(this.objGthrowSoundsJCheckBox, objLtopPanelMiddleSoundsExtendedGridBagConstraints);
		objLtopPanelMiddleSoundsExtendedGridBagConstraints.setGridLocation(2, 0);
		objLtopPanelMiddleSoundsExtendedGridBagConstraints.setMargins(0, 0, 0, 3);
		objLtopPanelMiddleSoundsJPanel.add(this.objGmetronomeJCheckBox, objLtopPanelMiddleSoundsExtendedGridBagConstraints);
		objLtopPanelMiddleSoundsExtendedGridBagConstraints.setGridLocation(3, 0);
		objLtopPanelMiddleSoundsExtendedGridBagConstraints.setMargins(0, 0, 0, 0);
		objLtopPanelMiddleSoundsExtendedGridBagConstraints.setInside(GridBagConstraints.CENTER, 0, 0);
		objLtopPanelMiddleSoundsJPanel.add(this.objGampersandJLabel, objLtopPanelMiddleSoundsExtendedGridBagConstraints);
		this.objGtopJLayeredPane.add(	objLtopPanelMiddleSoundsJPanel,
										new ExtendedGridBagConstraints(2, 6, 5, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 1F, 0F));
		this.objGtopJLayeredPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}

	final private void doAddWidgets() {

		this.doAddTopLeftWidgets();
		this.doAddTopMiddleWidgets();
		this.doAddTopScrollBars();
		this.doAddTopRightWidgets();
		final JPanel objLmiddleJPanel = this.doAddMiddleWidgets();
		final JPanel objLbottomJPanel = this.doAddBottomWidgets();

		// Main panel :
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLmainPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(	0,
																													0,
																													1,
																													1,
																													GridBagConstraints.CENTER,
																													10,
																													0,
																													10,
																													10,
																													GridBagConstraints.HORIZONTAL,
																													1F,
																													0.F);
		this.add(this.objGtopJLayeredPane, objLmainPanelExtendedGridBagConstraints);
		objLmainPanelExtendedGridBagConstraints.setGridLocation(0, 1);
		this.add(objLmiddleJPanel, objLmainPanelExtendedGridBagConstraints);
		objLmainPanelExtendedGridBagConstraints.setFilling(GridBagConstraints.BOTH, 1F, 1F);
		objLmainPanelExtendedGridBagConstraints.setGridLocation(0, 2);
		this.add(objLbottomJPanel, objLmainPanelExtendedGridBagConstraints);
		objLmainPanelExtendedGridBagConstraints.setGridLocation(0, 3);

		// this.objGleftJPanel = new JPanel(new GridBagLayout());
		// ExtendedGridBagConstraints objLmainPanelExtendedGridBagConstraints = new ExtendedGridBagConstraints(
		// 0,
		// 0,
		// 1,
		// 1,
		// ExtendedGridBagConstraints.CENTER,
		// 10,
		// 0,
		// 10,
		// 10,
		// ExtendedGridBagConstraints.HORIZONTAL,
		// 1F,
		// 0.F);
		// this.objGleftJPanel.add(this.objGtopJLayeredPane, objLmainPanelExtendedGridBagConstraints);
		// objLmainPanelExtendedGridBagConstraints.setGridLocation(0, 1);
		// this.objGleftJPanel.add(objLmiddleJPanel, objLmainPanelExtendedGridBagConstraints);
		// objLmainPanelExtendedGridBagConstraints.setFilling(ExtendedGridBagConstraints.BOTH, 1F, 1F);
		// objLmainPanelExtendedGridBagConstraints.setGridLocation(0, 2);
		// this.objGleftJPanel.add(objLbottomJPanel, objLmainPanelExtendedGridBagConstraints);

		// Canvas objLrightCanvas = new Canvas();
		// objLrightCanvas.setMinimumSize(new Dimension(20, 20));
		// this.objGjSplitPane = new PanelJSplitPane(this.objGleftJPanel, objLrightCanvas);
		// this.add(this.objGjSplitPane);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateEditionMenu() {

		// New pattern menu item :
		this.objGnewPatternJMenuItem = new NewPatternJMenuItem(this);

		// Reload pattern menu item :
		this.objGreloadPatternJuggleJMenuItem = new ExtendedJMenuItem(	this,
																		ExtendedJMenuItem.bytS_RELOAD_PATTERN,
																		Constants.intS_FILE_ICON_RELOAD_PATTERN_YELLOW,
																		this.objGreloadPatternJButton);

		// Find menu :
		this.objGfindJMenuItem = new FindJMenuItem(this, true, true);
		this.objGfindPreviousJMenuItem = new FindJMenuItem(this, false, false);
		this.objGfindNextJMenuItem = new FindJMenuItem(this, false, true);
		this.objGfindJDialog = new FindJDialog(this);

		// Filter menu :
		this.objGfiltersJMenuItem = new FiltersJMenuItem(this);
		this.objGfiltersJDialog = new FiltersJDialog(this);

		// Clipboard menu :
		this.objGclipboardJCheckBoxMenuItem = new ClipboardJCheckBoxMenuItem(this);
		this.objGclipboardJDialog = new ClipboardJDialog(this);
		this.objGclipboardExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_CLIPBOARD, Constants.intS_FILE_ICON_CLIPBOARD);
		this.objGsimpleCopyJuggleJMenuItem = new ExtendedJMenuItem(	this,
																	ExtendedJMenuItem.bytS_SIMPLE_COPY,
																	Constants.intS_FILE_ICON_SIMPLE_COPY,
																	this.objGclipboardJDialog.getClipboardJButton(false, false));

		this.objGdetailedCopyJuggleJMenuItem = new ExtendedJMenuItem(	this,
																		ExtendedJMenuItem.bytS_DETAILED_COPY,
																		Constants.intS_FILE_ICON_DETAILED_COPY,
																		this.objGclipboardJDialog.getClipboardJButton(true, false));

		this.objGfreeClipboardJuggleJMenuItem = new ExtendedJMenuItem(	this,
																		ExtendedJMenuItem.bytS_FREE_CLIPBOARD,
																		Constants.intS_FILE_ICON_FREE_CLIPBOARD,
																		this.objGclipboardJDialog.getClipboardJButton(false, true));
		this.objGfreeClipboardJuggleJMenuItem.setEnabled(false);

		// Media capture menu :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGscreenShotJMenuItem = new ScreenShotJMenuItem(this);
			this.objGscreenPlayExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_SCREEN_PLAY, Constants.intS_FILE_ICON_SCREEN_PLAY);
			this.objGstartScreenPlayJMenuItem = new ScreenPlayJMenuItem(this, true);
			this.objGstopScreenPlayJMenuItem = new ScreenPlayJMenuItem(this, false);
		}

		// Console menu item :
		this.objGdataJCheckBoxMenuItem = new DataJCheckBoxMenuItem(this);
		this.objGconsoleJCheckBoxMenuItem = new ConsoleJCheckBoxMenuItem(this);
		this.objGconsoleJDialog = new ConsoleJDialog(this);

		// Preference menu item :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGpreferencesJMenuItem = new PreferencesJMenuItem(this);
		}

		// Build menu tree :
		this.objGeditionExtendedJMenu.add(this.objGnewPatternJMenuItem);
		this.objGeditionExtendedJMenu.add(this.objGreloadPatternJuggleJMenuItem);
		this.objGeditionExtendedJMenu.addSeparator();
		this.objGeditionExtendedJMenu.add(this.objGfindJMenuItem);
		this.objGeditionExtendedJMenu.add(this.objGfindNextJMenuItem);
		this.objGeditionExtendedJMenu.add(this.objGfindPreviousJMenuItem);
		this.objGeditionExtendedJMenu.add(this.objGfiltersJMenuItem);
		this.objGeditionExtendedJMenu.addSeparator();
		this.objGclipboardExtendedJMenu.add(this.objGsimpleCopyJuggleJMenuItem);
		this.objGclipboardExtendedJMenu.add(this.objGdetailedCopyJuggleJMenuItem);
		this.objGclipboardExtendedJMenu.addSeparator();
		this.objGclipboardExtendedJMenu.add(this.objGclipboardJCheckBoxMenuItem);
		this.objGclipboardExtendedJMenu.add(this.objGfreeClipboardJuggleJMenuItem);
		this.objGeditionExtendedJMenu.add(this.objGclipboardExtendedJMenu);
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGscreenPlayExtendedJMenu.add(this.objGstartScreenPlayJMenuItem);
			this.objGscreenPlayExtendedJMenu.add(this.objGstopScreenPlayJMenuItem);
			this.objGeditionExtendedJMenu.addSeparator();
			this.objGeditionExtendedJMenu.add(this.objGscreenShotJMenuItem);
			this.objGeditionExtendedJMenu.add(this.objGscreenPlayExtendedJMenu);
		}
		this.objGeditionExtendedJMenu.addSeparator();
		this.objGeditionExtendedJMenu.add(this.objGdataJCheckBoxMenuItem);
		this.objGeditionExtendedJMenu.add(this.objGconsoleJCheckBoxMenuItem);
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGeditionExtendedJMenu.add(this.objGpreferencesJMenuItem);
		}
	}

	private void doCreateFileMenu() {

		// New file menu item :
		this.objGnewPatternsFileJMenuItem = new WriteFileJMenuItem(	this,
																	WriteFileJMenuItem.bytS_NEW_PATTERNS_FILE,
																	Constants.intS_FILE_ICON_NEW_PATTERNS_FILE);

		// Open pattern file menu item :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGopenFileJMenuItem = new LoadFileJMenuItem(this, true, true, true);
		}

		// Reload file menu item :
		this.objGreloadFileJMenuItem = new ReloadPatternsFilesJMenuItem(this);

		// Import menu items :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGimportStylesJMenuItem = new LoadFileJMenuItem(this, false, false, true);
			this.objGimportPatternsJMenuItem = new LoadFileJMenuItem(this, false, true, true);
			this.objGimportSiteswapsJMenuItem = new LoadFileJMenuItem(this, false, true, false);
			this.objGimportExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_IMPORT, Constants.intS_FILE_ICON_IMPORT);
		}

		// Recent menu :
		this.objGrecentFilesJuggleJMenu = new RecentFilesExtendedJMenu(this);

		// Export file menu items :
		this.objGexportExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_EXPORT, Constants.intS_FILE_ICON_EXPORT);

		this.objGexportPatternsFileJMenuItem = new WriteFileJMenuItem(	this,
																		WriteFileJMenuItem.bytS_PATTERNS_FILE,
																		Constants.intS_FILE_ICON_EXPORT_PATTERNS);
		this.objGexportSiteswapsFileJMenuItem = new WriteFileJMenuItem(	this,
																		WriteFileJMenuItem.bytS_SITESWAPS_FILE,
																		Constants.intS_FILE_ICON_EXPORT_SITESWAPS);

		this.objGexportStylesFileJMenuItem = new WriteFileJMenuItem(this,
																	WriteFileJMenuItem.bytS_STYLES_FILE,
																	Constants.intS_FILE_ICON_EXPORT_STYLES);

		// Quit menu item :
		this.objGquitJMenuItem = new QuitJMenuItem(this);

		// Build menu tree :
		this.objGfileExtendedJMenu.add(this.objGnewPatternsFileJMenuItem);
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGfileExtendedJMenu.add(this.objGopenFileJMenuItem);
			this.objGimportExtendedJMenu.add(this.objGimportPatternsJMenuItem);
			this.objGimportExtendedJMenu.add(this.objGimportSiteswapsJMenuItem);
			this.objGimportExtendedJMenu.add(this.objGimportStylesJMenuItem);
			this.objGfileExtendedJMenu.add(this.objGimportExtendedJMenu);
			this.objGfileExtendedJMenu.addSeparator();
		}
		this.objGexportExtendedJMenu.add(this.objGexportPatternsFileJMenuItem);
		this.objGexportExtendedJMenu.add(this.objGexportSiteswapsFileJMenuItem);
		this.objGexportExtendedJMenu.add(this.objGexportStylesFileJMenuItem);
		this.objGfileExtendedJMenu.add(this.objGexportExtendedJMenu);
		this.objGfileExtendedJMenu.add(this.objGreloadFileJMenuItem);
		this.objGfileExtendedJMenu.addSeparator();
		this.objGfileExtendedJMenu.add(this.objGrecentFilesJuggleJMenu);
		this.objGfileExtendedJMenu.addSeparator();
		this.objGfileExtendedJMenu.add(this.objGquitJMenuItem);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPlanguagesNumber
	 */
	final private void doCreateHelpMenu(int intPlanguagesNumber) {

		final String strLdataFolder = Strings.doConcat(	this.objGjuggleMasterPro.strS_CODE_BASE,
														Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA],
														File.separatorChar,
														this.getLanguageString(Language.intS_LANGUAGE_ISO_639_1_CODE),
														File.separatorChar);

		// Help menu item :
		String strLlink = Strings.doConcat(strLdataFolder, Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_HELP]);
		this.objGhelpJMenuItem = new ExtendedJMenuItem(this, ExtendedJMenuItem.bytS_HELP, Constants.intS_FILE_ICON_HELP, null);
		this.objGhelpJMenuItem.addActionListener(new LinksJMenuItemActionListener(this, this.objGhelpJMenuItem, strLlink, false));
		this.objGhelpExtendedJMenu.add(this.objGhelpJMenuItem);

		// Siteswap menu item :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_SITESWAP_THEORY_MENU);
		strLlink = Strings.doConcat(strLdataFolder, Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_SITESWAP_THEORY]);
		this.objGsiteswapJuggleJMenuItem = new ExtendedJMenuItem(	this,
																	ExtendedJMenuItem.bytS_SITESWAP_THEORY,
																	Constants.intS_FILE_ICON_SITESWAP_THEORY,
																	null);
		this.objGsiteswapJuggleJMenuItem.addActionListener(new LinksJMenuItemActionListener(this, this.objGsiteswapJuggleJMenuItem, strLlink, false));
		this.objGhelpExtendedJMenu.add(this.objGsiteswapJuggleJMenuItem);

		// Language Menu items :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_LANGUAGES_MENU);
		this.objGlanguageExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_LANGUAGE, Constants.intS_FILE_ICON_LANGUAGES);
		this.objGlanguageJRadioButtonMenuItemA = new LanguageJRadioButtonMenuItem[intPlanguagesNumber];
		final ButtonGroup objLbuttonGroup = new ButtonGroup();
		for (int intLlanguageIndex = 0; intLlanguageIndex < intPlanguagesNumber; ++intLlanguageIndex) {
			this.objGlanguageJRadioButtonMenuItemA[intLlanguageIndex] = new LanguageJRadioButtonMenuItem(this, intLlanguageIndex);
			objLbuttonGroup.add(this.objGlanguageJRadioButtonMenuItemA[intLlanguageIndex]);
			this.objGlanguageExtendedJMenu.add(this.objGlanguageJRadioButtonMenuItemA[intLlanguageIndex]);
		}
		this.objGlanguageExtendedJMenu.addSeparator();
		this.objGnewLanguageJMenuItem = new WriteFileJMenuItem(	this,
																WriteFileJMenuItem.bytS_NEW_LANGUAGE_FILE,
																Constants.intS_FILE_ICON_NEW_LANGUAGE);
		this.objGexportCurrentLanguageJMenuItem = new WriteFileJMenuItem(	this,
																			WriteFileJMenuItem.bytS_CURRENT_LANGUAGE_FILE,
																			Constants.intS_FILE_ICON_EXPORT_CURRENT_LANGUAGE);
		this.objGlanguageExtendedJMenu.add(this.objGexportCurrentLanguageJMenuItem);
		this.objGlanguageExtendedJMenu.add(this.objGnewLanguageJMenuItem);
		this.objGhelpExtendedJMenu.add(this.objGlanguageExtendedJMenu);

		// Record menu item :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_LOADING_RECORDS);
		this.objGrecordsLinksExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_RECORDS_LINKS, Constants.intS_FILE_ICON_RECORDS);
		MenusTools.doCreateComponentsMenu(this, this.objGrecordsLinksExtendedJMenu, strLdataFolder, Constants.intS_FILE_TEXT_RECORDS, false);
		if (this.objGrecordsLinksExtendedJMenu.getItemCount() > 0) {
			this.objGhelpExtendedJMenu.add(this.objGrecordsLinksExtendedJMenu);
		}

		// Video menu item :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_LOADING_VIDEOS);
		this.objGvideoLinksExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_VIDEO_LINKS, Constants.intS_FILE_ICON_VIDEO);
		MenusTools.doCreateComponentsMenu(this, this.objGvideoLinksExtendedJMenu, strLdataFolder, Constants.intS_FILE_TEXT_VIDEO, true);
		if (this.objGvideoLinksExtendedJMenu.getItemCount() > 0) {
			this.objGhelpExtendedJMenu.add(this.objGvideoLinksExtendedJMenu);
		}

		// Web link menu item :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_LOADING_WEB_LINKS);
		this.objGwebLinksExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_WEB_LINKS, Constants.intS_FILE_ICON_LINKS);
		MenusTools.doCreateComponentsMenu(this, this.objGwebLinksExtendedJMenu, strLdataFolder, Constants.intS_FILE_TEXT_LINKS, true);
		if (this.objGwebLinksExtendedJMenu.getItemCount() > 0) {
			this.objGhelpExtendedJMenu.add(this.objGwebLinksExtendedJMenu);
		}

		this.objGhelpExtendedJMenu.addSeparator();

		// Development menu item :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_DEVELOPMENT_MENU);
		this.objGdevelopmentJMenuItem = new DevelopmentJMenuItem(this);
		this.objGhelpExtendedJMenu.add(this.objGdevelopmentJMenuItem);

		// Licence menu item :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_LICENCE_MENU);
		this.objGlicenceJMenuItem = new LicenceJMenuItem(this);
		this.objGhelpExtendedJMenu.add(this.objGlicenceJMenuItem);

		// About menu item :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_ABOUT_CONTENT);
		this.objGaboutJMenuItem = new AboutJMenuItem(this);
		this.objGhelpExtendedJMenu.add(this.objGaboutJMenuItem);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateMenuBar() {
		this.objGfileExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_FILE, Constants.bytS_UNCLASS_NO_VALUE);
		this.objGeditionExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_EDITION, Constants.bytS_UNCLASS_NO_VALUE);
		this.objGpatternsExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_PATTERNS, Constants.bytS_UNCLASS_NO_VALUE);
		this.objGhelpExtendedJMenu = new ExtendedJMenu(this, ExtendedJMenu.bytS_HELP, Constants.bytS_UNCLASS_NO_VALUE);
		this.objGjMenuBar = new JMenuBar();
		this.objGjMenuBar.setOpaque(true);
		this.objGjMenuBar.add(this.objGfileExtendedJMenu);
		this.objGjMenuBar.add(this.objGeditionExtendedJMenu);
		this.objGjMenuBar.add(this.objGpatternsExtendedJMenu);
		this.objGjMenuBar.add(Box.createGlue());
		this.objGjMenuBar.add(this.objGhelpExtendedJMenu);
		this.setJMenuBar(this.objGjMenuBar);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreatePatternsMenu() {

		// Pattern menu items :
		this.objGpatternsExtendedJMenu.removeAll();
		this.objGpatternsExtendedJMenu.setVisible(MenusTools.doCreateComponentsMenu(this,
																					this.objGpatternsExtendedJMenu,
																					Strings.doConcat(	this.objGjuggleMasterPro.strS_CODE_BASE,
																										Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA],
																										File.separatorChar,
																										this.getLanguageString(Language.intS_LANGUAGE_ISO_639_1_CODE),
																										File.separatorChar),
																					Constants.intS_FILE_TEXT_PATTERNS,
																					true));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void doCreateWidgets() {

		this.objGstartJPanel = this.getStartHelpJPanel();

		// Pattern line :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_PATTERN_COMPONENTS);
		this.objGreloadPatternJButton = new ReloadPatternJButton(this);
		this.objGpatternJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_REPLAY_PATTERN);
		this.objGpatternJLabel.addMouseListener(new PatternJLabelMouseListener(this));
		this.objGpatternColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGreversePatternJButton = new ReversePatternJButton(this);
		this.objGpatternJTextField = new PatternJTextField(this);
		this.objGskillJComboBox = new SkillJComboBox(this, Constants.bytS_BYTE_LOCAL_SKILL, false);
		this.objGmirrorJCheckBox = new MirrorJCheckBox(this);
		this.objGmarkJComboBox = new MarkJComboBox(this, Constants.bytS_BYTE_LOCAL_MARK, false);
		this.objGlocalMarkJButton = new MarkJButton(this, Language.intS_TOOLTIP_MARK);

		// Siteswap line :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_SITESWAP_COMPONENTS);
		this.objGsiteswapThreeStatesJCheckBox = new SiteswapThreeStatesJCheckBox(this);
		this.objGsiteswapColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGsiteswapColonsJLabel.setTransferHandler(new ExtendedTransferHandler(this, true, false));
		this.objGrefreshSiteswapJButton = new RefreshSiteswapJButton(this);
		this.objGsiteswapJTextPane = new SiteswapJTextPane(this);
		this.objGreverseSiteswapJCheckBox = new ReverseSiteswapJCheckBox(this);
		this.objGsiteswapColorChooserDropDownJButton = new ColorChooserDropDownJButton(this, Constants.bytS_STRING_LOCAL_SITESWAP_DAY);
		this.objGsiteswapColorJLabel = new ExtendedJLabel(this, "SS", Language.intS_TOOLTIP_SITESWAP_COLOR);

		// Style line :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_STYLE_COMPONENTS);
		this.objGstyleJCheckBox = new StyleJCheckBox(this);
		this.objGfXJCheckBox = new FXJCheckBox(this);
		this.objGstyleColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGstyleColonsJLabel.setTransferHandler(new ExtendedTransferHandler(this, false, true));
		this.objGsortStylesJButton = new SortStylesJButton(this);
		this.objGstyleJComboBox = new StyleJComboBox(this);
		this.objGreverseStyleJCheckBox = new ReverseStyleJCheckBox(this);

		// Import buttons :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGimportPatternsJButton = new ImportPatternsJButton(this);
			this.objGimportStylesJButton = new ImportStylesJButton(this);
			this.objGimportSiteswapsJButton = new ImportSiteswapsJButton(this);
		}

		// Ball & juggler line :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_BALLS_COMPONENTS);
		this.objGballsThreeStatesJCheckBox = new BallsThreeStatesJCheckBox(this);
		this.objGballsColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGballsJScrollBar = new BallsJScrollBar(this);
		this.objGballsValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_BALLS_TRACE);
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_JUGGLER_COMPONENTS);
		this.objGjugglerValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_JUGGLER_BODY);
		this.objGjugglerValueJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.objGjugglerJScrollBar = new JugglerJScrollBar(this);
		this.objGjugglerThreeStatesJCheckBox = new JugglerThreeStatesJCheckBox(this);
		this.objGjugglerColorChooserDropDownJButton = new ColorChooserDropDownJButton(this, Constants.bytS_STRING_LOCAL_JUGGLER_DAY);
		this.objGjugglerColorJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_JUGGLER_COLOR);

		// Color line :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_COLORS_COMPONENTS);
		this.objGcolorsThreeStatesJCheckBox = new BallsColorsThreeStatesJCheckBox(this);
		this.objGcolorsColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGcolorsJTextPane = new BallsColorsJTextPane(this);
		this.objGballColorChooserDropDownJButton = new ColorChooserDropDownJButton(this, Constants.bytS_STRING_LOCAL_COLORS);
		this.objGcolorsValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_COLORS);
		this.objGalternateColorsValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_ALTERNATE_COLORS_RATIO);
		this.objGalternateColorsValueJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.objGalternateColorsJScrollBar = new AlternateColorsJScrollBar(this);
		this.objGalternateColorsJCheckBox = new AlternateColorsJCheckBox(this);

		// Default & strobe line :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_DFAULTS_COMPONENTS);
		this.objGdefaultsThreeStatesJCheckBox = new DefaultsThreeStatesJCheckBox(this);
		this.objGdefaultsColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGdefaultsJScrollBar = new DefaultsJScrollBar(this);
		this.objGdefaultsValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_DEFAULTS);
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_STROBE_COMPONENTS);
		this.objGstrobeValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_FLASH_RATIO);
		this.objGstrobeValueJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.objGstrobeJScrollBar = new StrobeJScrollBar(this);
		this.objGstrobeThreeStatesJCheckBox = new StrobeThreeStatesJCheckBox(this);

		// Sound & light line :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_LIGHT_AND_SOUND_COMPONENTS);
		this.objGsoundsJCheckBox = new SoundsJCheckBox(this);
		this.objGsoundsColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGcatchSoundsJCheckBox = new CatchSoundsJCheckBox(this);
		this.objGthrowSoundsJCheckBox = new ThrowSoundsJCheckBox(this);
		this.objGmetronomeJCheckBox = new MetronomeJCheckBox(this);
		this.objGampersandJLabel = new ExtendedJLabel(this, "&");
		this.objGlightJCheckBox = new LightJCheckBox(this);
		this.objGbackgroundColorChooserDropDownJButton = new ColorChooserDropDownJButton(this, Constants.bytS_STRING_LOCAL_BACKGROUND_DAY);
		this.objGbackgroundColorJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_BACKGROUND_COLOR);

		// Height, speed, dwell, fluidity & play/pause lines :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_MOTION_CONTROL_COMPONENTS);
		this.objGheightJLabel = new ExtendedJLabel(this);
		this.objGheightColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGheightJScrollBar = new HeightJScrollBar(this);
		this.objGheightValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_HEIGHT);
		this.objGspeedJLabel = new ExtendedJLabel(this);
		this.objGspeedColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGspeedJScrollBar = new SpeedJScrollBar(this);
		this.objGspeedValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_SPEED);
		this.objGdwellJLabel = new ExtendedJLabel(this);
		this.objGdwellColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGdwellJScrollBar = new DwellJScrollBar(this);
		this.objGdwellValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_DWELL);
		this.objGfluidityJLabel = new ExtendedJLabel(this);
		this.objGfluidityColonsJLabel = new ExtendedJLabel(this, ":");
		this.objGfluidityJScrollBar = new FluidityJScrollBar(this);
		this.objGfluidityValueJLabel = new ExtendedJLabel(this, Language.intS_TOOLTIP_FLUIDITY);
		this.objGplayPauseJButton = new PlayPauseJButton(this);

		// Filters :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_FILTERS_COMPONENTS);
		this.objGfiltersJButton = new FiltersJButton(this);
		this.objGinvalidPatternsJCheckBox = new InvalidPatternsJCheckBox(this);
		this.objGinvalidPatternsJLabel = new InvalidPatternsJLabel(this);
		this.objGfromBallsNumberJLabel = new FilterJLabel(this, Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER);
		this.objGtoBallsNumberJLabel = new FilterJLabel(this, Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER);
		this.objGballsNumberJCheckBox = new BallsNumberJCheckBox(this);
		this.objGfromBallsNumberJLabel.setLabelFor(this.objGballsNumberJCheckBox);
		this.objGlowBallsNumberJComboBox = new BallsNumberJComboBox(this, Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER);
		this.objGtoBallsNumberJLabel.setLabelFor(this.objGballsNumberJCheckBox);
		this.objGhighBallsNumberJComboBox = new BallsNumberJComboBox(this, Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER);
		this.objGballsNumberJLabel = new FilterJLabel(this, Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER);
		this.objGballsNumberJLabel.setLabelFor(this.objGballsNumberJCheckBox);

		this.objGfromSkillJLabel = new FilterJLabel(this, Constants.bytS_BOOLEAN_GLOBAL_SKILL);
		this.objGtoSkillJLabel = new FilterJLabel(this, Constants.bytS_BOOLEAN_GLOBAL_SKILL);
		this.objGskillJCheckBox = new SkillJCheckBox(this);
		this.objGfromSkillJLabel.setLabelFor(this.objGskillJCheckBox);
		this.objGlowSkillJComboBox = new SkillJComboBox(this, Constants.bytS_BYTE_GLOBAL_LOW_SKILL, true);
		this.objGtoSkillJLabel.setLabelFor(this.objGskillJCheckBox);
		this.objGhighSkillJComboBox = new SkillJComboBox(this, Constants.bytS_BYTE_GLOBAL_HIGH_SKILL, true);

		this.objGfromMarkJLabel = new FilterJLabel(this, Constants.bytS_BOOLEAN_GLOBAL_MARK);
		this.objGtoMarkJLabel = new FilterJLabel(this, Constants.bytS_BOOLEAN_GLOBAL_MARK);
		this.objGmarkJCheckBox = new MarkJCheckBox(this);
		this.objGfromMarkJLabel.setLabelFor(this.objGmarkJCheckBox);
		this.objGlowMarkJComboBox = new MarkJComboBox(this, Constants.bytS_BYTE_GLOBAL_LOW_MARK, true);
		this.objGtoMarkJLabel.setLabelFor(this.objGmarkJCheckBox);
		this.objGhighMarkJComboBox = new MarkJComboBox(this, Constants.bytS_BYTE_GLOBAL_HIGH_MARK, true);
		this.objGglobalMarkJButton = new MarkJButton(	this,
														true,
														false,
														Language.intS_TOOLTIP_ACTIVATE_MARK_FILTER,
														Language.intS_TOOLTIP_DEACTIVATE_MARK_FILTER);

		// Object list :
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_PATTERNS_LIST_COMPONENTS);
		this.objGshortcutsJComboBox = new ShortcutsJComboBox(this);
		this.objGobjectsJList = new ObjectsJList(this);
		this.objGobjectsJListJScrollPane = new JScrollPane(	this.objGobjectsJList,
															ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
															ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.objGobjectsJListJScrollPane.setOpaque(true);
		this.objGobjectsJListJScrollPane.setViewportBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.objGobjectsJListJScrollPane.setWheelScrollingEnabled(false);
		this.objGobjectsJListJScrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public boolean doDisplayComingSoonPopUp() {
		new PopUpJDialog(	this,
							Constants.bytS_UNCLASS_NO_VALUE,
							Constants.intS_FILE_ICON_ALERT,
							this.getLanguageString(Language.intS_TITLE_NOT_IMPLEMENTED_FUNCTIONALITY),
							this.getLanguageString(Language.intS_MESSAGE_FUNCTIONALITY_COMING_SOON),
							null,
							false);
		return true;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doDisplayFrames() {
		SwingUtilities.invokeLater(new Thread(this));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doHidePopUps() {
		if (this.objGstartJPanel != null) {
			this.objGtopJLayeredPane.remove(this.objGstartJPanel);
			this.objGtopJLayeredPane.validate();
			this.objGstartJPanel = null;
			ColorActions.doHideColorsChoosers(this);
			this.repaint();
		}
	}

	final private void doLoadImages() {
		this.objGreloadPatternJButton.doLoadImages();
		this.objGreversePatternJButton.doLoadImages();
		this.objGrefreshSiteswapJButton.doLoadImages();
		this.objGlocalMarkJButton.doLoadImages();
		this.objGsortStylesJButton.doLoadImages();
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGimportPatternsJButton.doLoadImages();
			this.objGimportStylesJButton.doLoadImages();
			this.objGimportSiteswapsJButton.doLoadImages();
		}
		this.objGballColorChooserDropDownJButton.doLoadImages();
		this.objGsiteswapColorChooserDropDownJButton.doLoadImages();
		this.objGjugglerColorChooserDropDownJButton.doLoadImages();
		this.objGbackgroundColorChooserDropDownJButton.doLoadImages();
		this.objGplayPauseJButton.doLoadImages();
		this.objGfiltersJButton.doLoadImages();
		this.objGglobalMarkJButton.doLoadImages();

		this.objGreloadPatternJuggleJMenuItem.doLoadImages();
		this.objGreloadFileJMenuItem.doLoadImages();
		this.objGrecentFilesJuggleJMenu.doLoadImages();
		this.objGnewPatternJMenuItem.doLoadImages();
		this.objGquitJMenuItem.doLoadImages();
		this.objGaboutJMenuItem.doLoadImages();
		this.objGlicenceJMenuItem.doLoadImages();
		this.objGdevelopmentJMenuItem.doLoadImages();
		this.objGreloadFileJMenuItem.doLoadImages();
		this.objGrecentFilesJuggleJMenu.doLoadImages();
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGopenFileJMenuItem.doLoadImages();
			this.objGimportStylesJMenuItem.doLoadImages();
			this.objGimportPatternsJMenuItem.doLoadImages();
			this.objGimportSiteswapsJMenuItem.doLoadImages();
			this.objGpreferencesJMenuItem.doLoadImages();
		}
		this.objGdataJCheckBoxMenuItem.doLoadImages();
		this.getJuggleMasterPro().objGdataJFrame.doLoadImages();
		this.objGconsoleJCheckBoxMenuItem.doLoadImages();
		this.objGconsoleJDialog.doLoadImages();

		this.objGfindJMenuItem.doLoadImages();
		this.objGfindPreviousJMenuItem.doLoadImages();
		this.objGfindNextJMenuItem.doLoadImages();
		this.objGfindJDialog.doLoadImages();
		this.objGfiltersJMenuItem.doLoadImages();
		this.objGfiltersJDialog.doLoadImages();
		this.objGclipboardJCheckBoxMenuItem.doLoadImages();
		this.objGhelpJMenuItem.doLoadImages();
		this.objGsiteswapJuggleJMenuItem.doLoadImages();
		this.objGsimpleCopyJuggleJMenuItem.doLoadImages();
		this.objGdetailedCopyJuggleJMenuItem.doLoadImages();
		this.objGclipboardJCheckBoxMenuItem.doLoadImages();
		this.objGfreeClipboardJuggleJMenuItem.doLoadImages();
		this.objGclipboardJDialog.doLoadImages();
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGscreenShotJMenuItem.doLoadImages();
			this.objGscreenPlayExtendedJMenu.doLoadImages();
			this.objGstartScreenPlayJMenuItem.doLoadImages();
			this.objGstopScreenPlayJMenuItem.doLoadImages();
			this.objGimportExtendedJMenu.doLoadImages();
		}
		for (int intLmenuItemIndex = 0; intLmenuItemIndex < this.objGlanguageJRadioButtonMenuItemA.length; ++intLmenuItemIndex) {
			this.objGlanguageJRadioButtonMenuItemA[intLmenuItemIndex].doLoadImages();
		}
		this.objGlanguageExtendedJMenu.doLoadImages();
		this.objGrecordsLinksExtendedJMenu.doLoadImages();
		this.objGvideoLinksExtendedJMenu.doLoadImages();
		this.objGwebLinksExtendedJMenu.doLoadImages();
		if (this.objGpatternsExtendedJMenu != null) {
			this.objGpatternsExtendedJMenu.doLoadImages();
		}
		this.objGeditionExtendedJMenu.doLoadImages();
		this.objGhelpExtendedJMenu.doLoadImages();
		this.objGclipboardExtendedJMenu.doLoadImages();
		this.objGexportExtendedJMenu.doLoadImages();
		this.objGnewLanguageJMenuItem.doLoadImages();
		this.objGexportCurrentLanguageJMenuItem.doLoadImages();
		this.objGnewPatternsFileJMenuItem.doLoadImages();
		this.objGexportPatternsFileJMenuItem.doLoadImages();
		this.objGexportSiteswapsFileJMenuItem.doLoadImages();
		this.objGexportStylesFileJMenuItem.doLoadImages();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final private int doLoadJuggleLanguages() {

		// Build lang.ini buffered reader :
		final BufferedReader objLlanguagesConfigBufferedReader = Tools.getBufferedReader(Strings.doConcat(	this.objGjuggleMasterPro.strS_CODE_BASE,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA],
																											File.separatorChar,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LANGUAGES]));

		// Get language configuration :
		final ArrayList<String> strLlanguageAL = new ArrayList<String>(5);
		if (objLlanguagesConfigBufferedReader != null) {
			String strLfileLine = new String();
			while (true) {
				try {
					strLfileLine = Strings.uncommentUntabTrim(objLlanguagesConfigBufferedReader.readLine());
					if (strLfileLine == null) {
						break;
					}
				} catch (final Throwable objPthrowable) {
					break;
				}
				final StringBuilder objLlanguageStringBuilder = new StringBuilder(32);
				final char chrLfileLineStringA[] = strLfileLine.toCharArray();
				for (final char chrLindexed : chrLfileLineStringA) {
					if (chrLindexed == ' ') {
						if (objLlanguageStringBuilder.length() > 0) {
							strLlanguageAL.add(objLlanguageStringBuilder.toString());
							objLlanguageStringBuilder.setLength(0);
						}
					} else {
						objLlanguageStringBuilder.append(chrLindexed);
					}
				}
				if (objLlanguageStringBuilder.length() > 0) {
					strLlanguageAL.add(objLlanguageStringBuilder.toString());
				}
			}
			try {
				objLlanguagesConfigBufferedReader.close();
			} catch (final Throwable objPthrowable) {
				Tools.err("Error while closing file descriptor");
			}
		}

		int intLlanguagesNumber = strLlanguageAL.size();
		// this.objGdefaultJuggleLanguage = null;
		this.intGlanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
		if (intLlanguagesNumber == 0) {
			intLlanguagesNumber = 1;
			this.objGlanguageA = new Language[intLlanguagesNumber];
			this.intGlanguageIndex = 0;
			this.objGlanguageA[this.intGlanguageIndex] = new Language();
		} else {
			this.objGlanguageA = new Language[intLlanguagesNumber];
			for (int intLlanguageIndex = 0; intLlanguageIndex < intLlanguagesNumber; ++intLlanguageIndex) {
				final String strLfilePath = Strings.doConcat(	this.objGjuggleMasterPro.strS_CODE_BASE,
																Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA],
																File.separatorChar,
																strLlanguageAL.get(intLlanguageIndex),
																File.separatorChar);

				final BufferedReader objLlanguageBufferedReader = Tools.getBufferedReader(Strings.doConcat(	strLfilePath,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LANGUAGE]));
				final BufferedReader objLjMHeaderBufferedReader = Tools.getBufferedReader(Strings.doConcat(	strLfilePath,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JM_HEADER]));
				final BufferedReader objLjMPHeaderBufferedReader = Tools.getBufferedReader(Strings.doConcat(strLfilePath,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JMP_HEADER]));
				final BufferedReader objLjAPHeaderBufferedReader = Tools.getBufferedReader(Strings.doConcat(strLfilePath,
																											Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JAP_HEADER]));

				this.objGlanguageA[intLlanguageIndex] = new Language(	strLlanguageAL.get(intLlanguageIndex),
																		objLlanguageBufferedReader,
																		objLjMHeaderBufferedReader,
																		objLjMPHeaderBufferedReader,
																		objLjAPHeaderBufferedReader);
			}
			Arrays.sort(this.objGlanguageA, this);

			// Set default and current languages :
			int intLdefaultLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;
			for (int intLlanguageIndex = 0; intLlanguageIndex < intLlanguagesNumber; ++intLlanguageIndex) {

				final String strLiSO6391Code = this.objGlanguageA[intLlanguageIndex].getPropertyValueString(Language.intS_LANGUAGE_ISO_639_1_CODE);
				if (strLiSO6391Code.length() > 0) {
					if (strLiSO6391Code.equalsIgnoreCase(Preferences.getGlobalStringPreference(Constants.bytS_STRING_GLOBAL_LANGUAGE))) {
						intLdefaultLanguageIndex = intLlanguageIndex;
						// this.objGdefaultJuggleLanguage = this.objGlanguageA[intLdefaultLanguageIndex];
					}
					if (strLiSO6391Code.equalsIgnoreCase(this.getJuggleMasterPro().strGcommandLineLanguageParameter)) {
						this.intGlanguageIndex = intLlanguageIndex;
					}
				}
			}
			this.getJuggleMasterPro().strGcommandLineLanguageParameter = null;

			// Set preferred language :
			if (intLdefaultLanguageIndex != Constants.bytS_UNCLASS_NO_VALUE) {
				this.intGlanguageIndex = intLdefaultLanguageIndex;
			} else {
				Preferences.writeGlobalStringPreference(Constants.bytS_STRING_GLOBAL_LANGUAGE, Strings.strS_EMPTY);
				if (this.intGlanguageIndex == Constants.bytS_UNCLASS_NO_VALUE) {
					this.intGlanguageIndex = 0;
				}
				// this.objGdefaultJuggleLanguage = this.objGlanguageA[this.intGlanguageIndex];
			}
		}
		return intLlanguagesNumber;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doRestartJuggling() {
		this.doHidePopUps();
		this.doStartJuggling(this.getJuggleMasterPro().intGobjectIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPpatternIndex
	 */
	final public void doStartJuggling(int intPpatternIndex) {
		this.bolGdefaultsFxPause = false;
		ColorActions.doHideColorsChoosers(this);
		this.objGobjectsJList.selectIndex(this.objGobjectsJList.getFilteredObjectIndex(intPpatternIndex));
		this.objGjuggleMasterPro.doStartPattern(intPpatternIndex);
		this.doAddAction(Constants.intS_ACTION_REFRESH_DRAWING);
		this.setJButtonsEnabled(true);
		this.setMenusEnabled();
		this.objGcolorsJTextPane.getColorsStyledDocument().doRemoveStyles();
		this.setWidgetsControls();
		// this.objGobjectsJList.requestFocusInWindow();
	}

	/**
	 * @return the intGlanguageIndex
	 */
	public int geLanguageIndex() {
		return this.intGlanguageIndex;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param intPlanguageStringIndex
	 * @param bolPenabled
	 * @return
	 */
	final private String getBooleanControlLabelString(byte bytPcontrolType, int intPlanguageStringIndex, boolean bolPenabled) {
		final boolean bolLlocal = this.isBooleanLocal(bytPcontrolType);
		return Strings.doConcat(Strings.strS_HTML_OPENING,
								Strings.strS_HTML_OPENING_NO_BREAK,
								Strings.strS_HTML_OPENING_SPAN_STYLE,
								Strings.strS_HTML_FONT_STYLE,
								bolLlocal ? Strings.strS_HTML_ITALIC_STYLE : Strings.strS_HTML_NORMAL_STYLE,
								Strings.strS_HTML_COLOR_STYLE,
								Constants.strS_COLORS_PEN_COLOR_A[Tools.getNumberedBoolean(!bolPenabled)],
								Strings.strS_HTML_CLOSING_SPAN_STYLE,
								this.getLanguageString(intPlanguageStringIndex),
								this.hasBooleanControlChanged(bytPcontrolType) ? Strings.strS_RIGHT_ASTERIX : null,
								Strings.strS_HTML_CLOSING_SPAN,
								Strings.strS_HTML_CLOSING_NO_BREAK,
								Strings.strS_HTML_CLOSING);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param strPvalue
	 * @param bolPenabled
	 * @return
	 */
	final private String getByteControlLabelString(byte bytPcontrolType, String strPvalue, boolean bolPleft, boolean bolPenabled) {
		final boolean bolLlocal = this.isByteLocal(bytPcontrolType);
		final Boolean objLstarPosition = this.hasByteControlChanged(bytPcontrolType) ? bolPleft ? Boolean.TRUE : Boolean.FALSE : null;
		return Strings.doConcat(Strings.strS_HTML_OPENING,
								Strings.strS_HTML_OPENING_NO_BREAK,
								Strings.strS_HTML_OPENING_SPAN_STYLE,
								Strings.strS_HTML_FONT_STYLE,
								bolLlocal ? Strings.strS_HTML_ITALIC_STYLE : Strings.strS_HTML_NORMAL_STYLE,
								Strings.strS_HTML_COLOR_STYLE,
								Constants.strS_COLORS_PEN_COLOR_A[Tools.getNumberedBoolean(!bolPenabled)],
								Strings.strS_HTML_CLOSING_SPAN_STYLE,
								objLstarPosition == Boolean.FALSE ? Strings.strS_LEFT_ASTERIX : null,
								strPvalue,
								objLstarPosition == Boolean.TRUE ? Strings.strS_RIGHT_ASTERIX : null,
								Strings.strS_HTML_CLOSING_SPAN,
								Strings.strS_HTML_CLOSING_NO_BREAK,
								Strings.strS_HTML_CLOSING);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPvalue
	 * @param bolPlocal
	 * @param bolPenabled
	 * @param bolPhasChanged
	 * @return
	 */
	final private String getControlLabelString(String strPvalue, boolean bolPlocal, boolean bolPenabled, boolean bolPhasChanged) {
		return Strings.doConcat(Strings.strS_HTML_OPENING,
								Strings.strS_HTML_OPENING_NO_BREAK,
								Strings.strS_HTML_OPENING_SPAN_STYLE,
								Strings.strS_HTML_FONT_STYLE,
								bolPlocal ? Strings.strS_HTML_ITALIC_STYLE : Strings.strS_HTML_NORMAL_STYLE,
								Strings.strS_HTML_COLOR_STYLE,
								Constants.strS_COLORS_PEN_COLOR_A[Tools.getNumberedBoolean(!bolPenabled)],
								Strings.strS_HTML_CLOSING_SPAN_STYLE,
								strPvalue,
								bolPhasChanged ? Strings.strS_RIGHT_ASTERIX : null,
								Strings.strS_HTML_CLOSING_SPAN,
								Strings.strS_HTML_CLOSING_NO_BREAK,
								Strings.strS_HTML_CLOSING);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public String getControlString(byte bytPcontrolType) {
		return this.objGjuggleMasterPro.getPatternStringValue(bytPcontrolType);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public String getControlString(byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGjuggleMasterPro.getPatternStringValue(bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public String getControlString(int intPobjectIndex, byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGjuggleMasterPro.getPatternStringValue(intPobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public byte getControlValue(byte bytPcontrolType) {
		return this.objGjuggleMasterPro.getControlValue(bytPcontrolType);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getControlValue(byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGjuggleMasterPro.getControlValue(bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getControlValue(int intPobjectIndex, byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGjuggleMasterPro.getPatternByteValue(intPobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	final public ImageIcon getFileImageIcon(boolean bolPnewLists, boolean bolPsiteswaps, boolean bolPstyles) {

		final ImageIcon icoL = this	.getJuggleMasterPro()
									.getImageIcon(	bolPnewLists	? Constants.intS_FILE_ICON_LOAD_PATTERNS
																: bolPsiteswaps	? bolPstyles	? Constants.intS_FILE_ICON_IMPORT_PATTERNS
																								: Constants.intS_FILE_ICON_IMPORT_SITESWAPS
																				: Constants.intS_FILE_ICON_IMPORT_STYLES,
													0);
		return icoL;
	}

	/**
	 * @return the intGfilteredPatternsNumber
	 */
	public int getFilteredPatternsNumber() {
		return this.intGfilteredPatternsNumber;
	}

	/**
	 * @return the intGfilteredShortcutsNumber
	 */
	public int getFilteredShortcutsNumber() {
		return this.intGfilteredShortcutsNumber;
	}

	@Override final public Font getFont() {

		if (this.objGjuggleMasterProFont == null) {
			try {
				this.objGjuggleMasterProFont = Font	.createFont(Font.TRUETYPE_FONT,
																new File(Strings.doConcat(	this.objGjuggleMasterPro.strS_CODE_BASE,
																							Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_LIBRARY],
																							'/',
																							Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_FONT])))
													.deriveFont(11.0F);
			} catch (final Throwable objPthrowable) {
				this.objGjuggleMasterProFont = new JLabel().getFont();
				Tools.err("Error while loading customized font");
			}
			// GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Constants.objS_GRAPHICS_DEFAULT_FONT);
		}
		return this.objGjuggleMasterProFont;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public JuggleMasterPro getJuggleMasterPro() {
		return this.objGjuggleMasterPro;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public byte getJugglerValue() {
		return this.objGjuggleMasterPro.getJugglerValue();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public byte getJugglerValue(byte bytPinitialOrCurrent) {
		return this.objGjuggleMasterPro.getJugglerValue(bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public Language getLanguage() {
		return this.intGlanguageIndex != Constants.bytS_UNCLASS_NO_VALUE ? this.objGlanguageA[this.intGlanguageIndex] : new Language();
	}

	final public Language getLanguage(String strPiSO6391Code) {
		for (final Language objLlanguage : this.objGlanguageA) {
			if (objLlanguage.getPropertyValueString(Language.intS_LANGUAGE_ISO_639_1_CODE).equalsIgnoreCase(strPiSO6391Code)) {
				return objLlanguage;
			}
		}
		return null;
	}

	final public int getLanguageIndex(String strPlanguageISOCode) {
		if (strPlanguageISOCode != null) {
			for (int intLlanguageIndex = 0; intLlanguageIndex < this.intGlanguagesNumber; ++intLlanguageIndex) {
				if (strPlanguageISOCode.equalsIgnoreCase(this.objGlanguageA[intLlanguageIndex].getPropertyValueString(Language.intS_LANGUAGE_ISO_639_1_CODE))) {
					return intLlanguageIndex;
				}
			}
		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	final public String getLanguagePreferenceString() {
		final String strLpreferenceLanguage = Preferences.readGlobalStringPreference(Constants.bytS_STRING_GLOBAL_LANGUAGE);
		if (!Tools.isEmpty(strLpreferenceLanguage)) {
			final String strLpreferenceUpperLanguage = Strings.getUpperCaseString(strLpreferenceLanguage);
			for (final Language objLlanguage : this.objGlanguageA) {
				if (strLpreferenceUpperLanguage.equals(Strings.getUpperCaseString(objLlanguage.getPropertyValueString(Language.intS_LANGUAGE_ISO_639_1_CODE)))) {
					return strLpreferenceLanguage;
				}
			}
		}
		return Strings.strS_EMPTY;
	}

	/**
	 * @return
	 */
	final public int getLanguagesNumber() {
		return this.intGlanguagesNumber;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPpropertyIndex
	 * @return
	 */
	final public String getLanguageString(int intPpropertyIndex) {
		return this.getLanguage().getPropertyValueString(intPpropertyIndex, null);
	}

	final public String getLanguageString(int intPpropertyIndex, String strPtokenValue) {
		return this.getLanguage().getPropertyValueString(intPpropertyIndex, strPtokenValue);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final public PatternsManager getPatternsManager() {
		return this.objGjuggleMasterPro.objGpatternsManager;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	final private JPanel getStartHelpJPanel() {
		final JPanel objLjPanel = new JPanel();
		objLjPanel.setOpaque(true);
		objLjPanel.setLayout(new BoxLayout(objLjPanel, BoxLayout.LINE_AXIS));
		objLjPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		objLjPanel.setBackground(Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		final ImageIcon imgL = this.objGjuggleMasterPro.getImageIcon(Constants.intS_FILE_IMAGE_ARROWS, Constants.bytS_UNCLASS_NO_VALUE);
		objLjPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		ExtendedJLabel objLjLabel = new ExtendedJLabel(this, imgL, Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		objLjPanel.add(objLjLabel);

		objLjLabel = new ExtendedJLabel(this,
										Strings.doConcat(	"<HTML>",
															this.getLanguageString(Language.intS_MESSAGE_CHOOSE_PATTERNS_OR_FIND_SOME_HELP_1),
															"<BR>",
															this.getLanguageString(Language.intS_MESSAGE_CHOOSE_PATTERNS_OR_FIND_SOME_HELP_2),
															"</HTML>"),
										Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR);
		final Font objLfont = objLjLabel.getFont();
		objLjLabel.setFont(objLfont.deriveFont(objLfont.getSize2D() * 1.8F));
		objLjLabel.setIconTextGap(20);
		objLjPanel.add(Box.createHorizontalGlue());
		objLjPanel.add(objLjLabel); // ,
		objLjPanel.add(Box.createHorizontalGlue());
		return objLjPanel;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean hasBooleanControlChanged(byte bytPcontrolType) {
		return this.isControlSelected(bytPcontrolType, Constants.bytS_UNCLASS_INITIAL) != this.isControlSelected(	bytPcontrolType,
																													Constants.bytS_UNCLASS_CURRENT)
				&& this.getPatternsManager().bolGbooleanIsFileDefinedA[bytPcontrolType];
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean hasByteControlChanged(byte bytPcontrolType) {
		return this.getControlValue(bytPcontrolType, Constants.bytS_UNCLASS_INITIAL) != this.getControlValue(	bytPcontrolType,
																												Constants.bytS_UNCLASS_CURRENT)
				&& this.getPatternsManager().bolGbyteIsFileDefinedA[bytPcontrolType];
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean hasStringControlChanged(byte bytPcontrolType) {
		return !Strings	.getRightTrimmedString(this	.getLanguage()
													.getTranslatedTokensString(	this.getControlString(bytPcontrolType, Constants.bytS_UNCLASS_INITIAL),
																				this.getPatternsManager().bytGpatternsManagerType))
						.equals(Strings.getRightTrimmedString(this	.getLanguage()
																	.getTranslatedTokensString(	this.getControlString(	bytPcontrolType,
																														Constants.bytS_UNCLASS_CURRENT),
																								this.getPatternsManager().bytGpatternsManagerType)))
				&& this.getPatternsManager().bolGstringIsFileDefinedA[bytPcontrolType];
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean isBooleanLocal(byte bytPcontrolType) {
		return this.objGjuggleMasterPro.isBooleanLocal(bytPcontrolType);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean isByteLocal(byte bytPcontrolType) {
		return this.objGjuggleMasterPro.isByteLocal(bytPcontrolType);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean isControlSelected(byte bytPcontrolType) {
		return this.objGjuggleMasterPro.getPatternBooleanValue(bytPcontrolType);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public boolean isControlSelected(byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGjuggleMasterPro.getPatternBooleanValue(bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPobjectIndex
	 * @param bytPcontrolType
	 * @param bytPinitialOrCurrent
	 * @return
	 */
	final public boolean isControlSelected(int intPobjectIndex, byte bytPcontrolType, byte bytPinitialOrCurrent) {
		return this.objGjuggleMasterPro.getPatternBooleanValue(intPobjectIndex, bytPcontrolType, bytPinitialOrCurrent);
	}

	/**
	 * @return
	 */
	public boolean isExportCurrentPatterns() {
		return this.bolGexportCurrentPatterns;
	}

	/**
	 * @return the bolGexportInitialPatterns
	 */
	public boolean isExportInitialPatterns() {
		return this.bolGexportInitialPatterns;
	}

	/**
	 * @return the bolGloadingPatternsFile
	 */
	public boolean isLoadingPatternsFile() {
		return this.bolGloadingPatternsFile;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @return
	 */
	final public boolean isStringLocal(byte bytPcontrolType) {
		return this.objGjuggleMasterPro.isStringLocal(bytPcontrolType);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	@Override final public void run() {

		final int intLmiddleX = Constants.intS_GRAPHICS_SCREEN_X + Constants.intS_GRAPHICS_SCREEN_WIDTH / 2;
		final int intLmiddleY = Constants.intS_GRAPHICS_SCREEN_Y + Constants.intS_GRAPHICS_SCREEN_HEIGHT / 2;
		final int intLposX = Math.max(Constants.intS_GRAPHICS_SCREEN_X, intLmiddleX - Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE);
		final int intLposY = Math.max(Constants.intS_GRAPHICS_SCREEN_Y, intLmiddleY - Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE / 2);
		this.setBounds(intLposX, intLposY, intLmiddleX - intLposX, 2 * (intLmiddleY - intLposY));
		this.setIconImage(this.objGjuggleMasterPro.getFrame().imgGjmp);

		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_LOADING_ICONS);
		this.doLoadImages();
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_ADDING_COMPONENTS);
		this.doAddWidgets();

		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_CREATING_LISTS);
		this.objGstyleJComboBox.setSortOrderAscending(true);
		this.objGstyleJComboBox.setList();

		this.objGobjectsJList.setLists();
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_SETTING_COMPONENTS);
		this.setWidgetsControls();
		this.setJButtonsEnabled(true);
		this.setMenusEnabled();

		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_SETTING_LANGUAGE);
		this.setLanguageJLabels(false);
		this.objGsplashScreenJWindow.setComment(Language.intS_SPLASH_DISPLAYING_FRAMES);
		this.setVisible(true);

		try {
			this.objGjuggleMasterPro.objGmediaTracker.waitForAll();
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while waiting for all media to be loaded");
		}

		// Frame listeners :
		this.objGcontrolJFrameListener = new ControlJFrameListener(this);
		this.addWindowListener(this.objGcontrolJFrameListener);
		this.addComponentListener(this.objGcontrolJFrameListener);

		this.objGjuggleMasterPro.getFrame().setVisible(true);
		this.objGsplashScreenJWindow.setInvisible();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bolPenabled
	 */
	final public void saveControlSelected(byte bytPcontrolType, boolean bolPenabled) {
		this.objGjuggleMasterPro.setControlSelected(bytPcontrolType, bolPenabled);
		this.getPatternsManager().bolGbooleanIsUserDefinedA[bytPcontrolType] = true;
		if (!this.getPatternsManager().bolGbooleanIsLocalA[bytPcontrolType]) {
			Preferences.setLocalBooleanPreference(bytPcontrolType, bolPenabled);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param strP
	 */
	final public void saveControlString(byte bytPcontrolType, String strP) {
		this.objGjuggleMasterPro.setControlString(bytPcontrolType, strP);

		this.getPatternsManager().bolGstringIsUserDefinedA[bytPcontrolType] = true;
		if (!this.getPatternsManager().bolGstringIsLocalA[bytPcontrolType]) {
			Preferences.setLocalStringPreference(bytPcontrolType, strP);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bytPcontrolType
	 * @param bytPvalue
	 */
	final public void saveControlValue(byte bytPcontrolType, byte bytPvalue) {
		this.objGjuggleMasterPro.setControlValue(bytPcontrolType, bytPvalue);
		this.getPatternsManager().bolGbyteIsUserDefinedA[bytPcontrolType] = true;
		if (!this.getPatternsManager().bolGbyteIsLocalA[bytPcontrolType]) {
			Preferences.setLocalBytePreference(bytPcontrolType, bytPvalue);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setBallsControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Ball checkbox :
		this.objGballsThreeStatesJCheckBox.setEnabled(bolLedition);
		final Boolean objLstateValue = this.objGballsThreeStatesJCheckBox.getSelected();
		this.objGballsThreeStatesJCheckBox.setText(this.getBooleanControlLabelString(	objLstateValue == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN	? Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS
																																								: Constants.bytS_BOOLEAN_LOCAL_BALLS,
																						objLstateValue == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN	? Language.intS_CHECKBOX_BALLS_AND_CURRENT_THROWS
																																								: Language.intS_CHECKBOX_BALLS,
																						bolLedition));
		this.objGballsThreeStatesJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS)	? ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
																														: this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_BALLS)	? ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN
																																														: ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		this.objGballsThreeStatesJCheckBox.setToolTipText();
		this.objGballsColonsJLabel.setEnabled(bolLedition);

		// Ball scrollbar :
		String strLsymbol;
		final byte bytLballs = this.getControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL);
		switch (bytLballs) {
			case Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE:
				strLsymbol = "\267";
				break;
			case Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL:
				strLsymbol = "\u221e";
				break;
			default:
				strLsymbol = Strings.doConcat(bytLballs, " ~");
		}
		this.objGballsJScrollBar.setEnabled(bolLedition);
		this.objGballsJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL));
		this.objGballsJScrollBar.setToolTipText();
		final boolean bolLenabled = bolLedition	&& !this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)
									&& this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_BALLS);
		this.objGballsValueJLabel.setEnabled(bolLenabled);
		this.objGballsValueJLabel.setToolTipText();
		this.objGballsValueJLabel.setText(this.getByteControlLabelString(Constants.bytS_BYTE_LOCAL_BALLS_TRAIL, strLsymbol, true, bolLenabled));
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPcontrolJFrameRectangle
	 * @param objPjuggleMasterProJFrameRectangle
	 */
	final public void setBounds(Rectangle objPcontrolJFrameRectangle, Rectangle objPjuggleMasterProJFrameRectangle) {

		this.setBounds(objPcontrolJFrameRectangle);
		this.repaint();
		this.doAddAction(Constants.intS_ACTION_REFRESH_DRAWING | Constants.intS_ACTION_CLEAR_ANIMATION_IMAGE);
		ColorActions.doHideColorsChoosers(this);
		this.objGjuggleMasterPro.getFrame().setBounds(objPjuggleMasterProJFrameRectangle);
		this.objGjuggleMasterPro.getFrame().initBounds();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setColorsControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Color label :
		this.objGcolorsThreeStatesJCheckBox.setEnabled(bolLedition);
		this.objGcolorsThreeStatesJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS)	? ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
																														: this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_COLORS)	? ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN
																																														: ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		this.objGcolorsThreeStatesJCheckBox.setText(this.getBooleanControlLabelString(	this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS)	? Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS
																																							: Constants.bytS_BOOLEAN_LOCAL_COLORS,
																						this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS)	? Language.intS_CHECKBOX_RANDOM_COLORS
																																							: Language.intS_CHECKBOX_COLORS,
																						bolLedition));
		this.objGcolorsThreeStatesJCheckBox.setToolTipText();
		this.objGcolorsColonsJLabel.setEnabled(bolLedition);

		// Ball color textpane :
		this.objGcolorsJTextPane.setEnabled(bolLedition);
		// this.objGcolorsJTextPane.selectText("");
		// this.objGcolorsJTextPane.requestFocusInWindow();

		// Dimension objLdimension = this.objGcolorsJScrollPane.getViewport().getExtentSize();
		// Tools.verbose("Color Scroll Size : ", objLdimension);
		// Tools.verbose("Color Text Size : ", this.objGcolorsJTextPane.getSize());
		this.objGcolorsJTextPane.setText(/*
											 * this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
											 * ?
											 */this.getControlString(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)	? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
																																		: Constants.bytS_STRING_LOCAL_COLORS)
		/* : JuggleStrings.strS_EMPTY */);
		// if (objLdimension.getWidth() > 0 && objLdimension.getHeight() > 0) {

		// Dimension objLdimension = new Dimension(this.objGballsJScrollBar.getWidth(),
		// this.objGalternateColorsJScrollBar.getHeight());
		// this.objGcolorsJScrollPane.setPreferredSize(objLdimension);
		// this.objGcolorsJScrollPane.getViewport().setExtentSize(objLdimension);
		// JuggleTools.debug("New Color Size : ", this.objGcolorsJScrollPane.getViewport().getExtentSize());

		// this.objGcolorsJTextPane.setPreferredSize(objLdimension);
		this.objGcolorsJTextPane.setToolTipText();
		this.objGcolorsJTextPane.revalidate();
		// }

		// Ball color button :
		this.objGballColorChooserDropDownJButton.setEnabled(bolLedition);
		this.objGballColorChooserDropDownJButton.repaint();
		this.objGballColorChooserDropDownJButton.setToolTipText();
		final byte bytLcolorsStringType =
										this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)	? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
																												: Constants.bytS_STRING_LOCAL_COLORS;
		final boolean bolLlocal = this.isStringLocal(bytLcolorsStringType);

		final boolean bolLenabled = bolLedition	&& !this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)
									&& this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_COLORS);
		this.objGcolorsValueJLabel.setEnabled(bolLedition	&& !this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)
												&& this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_COLORS));
		this.objGcolorsValueJLabel.setText(this.getControlLabelString(	null,
																		bolLlocal,
																		bolLenabled,
																		this.hasStringControlChanged(bytLcolorsStringType)));
		this.objGcolorsValueJLabel.setToolTipText();

		// Alternate color value :
		final byte bytLalternateColors = this.getControlValue(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS);
		String strLalternateColors;
		switch (bytLalternateColors) {
			case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH:
				strLalternateColors = "  \370";
				break;
			case Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_COUNT:
				strLalternateColors = "/T";
				break;
			default:
				strLalternateColors = Strings.doConcat('/', Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE - bytLalternateColors + 1);
				break;
		}
		final boolean bolLalternateColors = bolLedition && this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS);
		this.objGalternateColorsValueJLabel.setEnabled(bolLalternateColors);
		this.objGalternateColorsValueJLabel.setText(this.getByteControlLabelString(	Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS,
																					strLalternateColors,
																					false,
																					bolLalternateColors));
		this.objGalternateColorsValueJLabel.setToolTipText();
		this.objGalternateColorsJScrollBar.setEnabled(bolLedition);
		this.objGalternateColorsJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS));
		this.objGalternateColorsJScrollBar.setToolTipText();

		// Alternate color checkbox :
		this.objGalternateColorsJCheckBox.setEnabled(bolLedition);
		this.objGalternateColorsJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS));
		this.objGalternateColorsJCheckBox.setText(this.getBooleanControlLabelString(Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS,
																					Language.intS_CHECKBOX_ALTERNATE_COLORS,
																					bolLedition));
		this.objGalternateColorsJCheckBox.setToolTipText();

		// Siteswap color button :
		this.objGsiteswapColorChooserDropDownJButton.setEnabled(bolLedition);
		this.objGsiteswapColorChooserDropDownJButton.repaint();
		final byte bytLstringSiteswapIndex = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT)	? Constants.bytS_STRING_LOCAL_SITESWAP_DAY
																										: Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT;
		this.objGsiteswapColorJLabel.setText(this.getControlLabelString(Strings.strS_HTML_SPACE,
																		this.isStringLocal(bytLstringSiteswapIndex),
																		bolLedition,
																		this.hasStringControlChanged(bytLstringSiteswapIndex)));

		// Juggler color button :
		this.objGjugglerColorChooserDropDownJButton.setEnabled(bolLedition);
		this.objGjugglerColorChooserDropDownJButton.repaint();
		final byte bytLstringJugglerIndex = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT)	? Constants.bytS_STRING_LOCAL_JUGGLER_DAY
																										: Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT;
		this.objGjugglerColorJLabel.setText(this.getControlLabelString(	Strings.strS_HTML_SPACE,
																		this.isStringLocal(bytLstringJugglerIndex),
																		bolLedition,
																		this.hasStringControlChanged(bytLstringJugglerIndex)));

		// Background color button :
		this.objGbackgroundColorChooserDropDownJButton.setEnabled(bolLedition);
		this.objGbackgroundColorChooserDropDownJButton.repaint();
		this.objGbackgroundColorChooserDropDownJButton.setToolTipText();
		final byte bytLstringBackgroundIndex = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT)	? Constants.bytS_STRING_LOCAL_BACKGROUND_DAY
																											: Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT;
		this.objGbackgroundColorJLabel.setText(this.getControlLabelString(	Strings.strS_HTML_SPACE,
																			this.isStringLocal(bytLstringBackgroundIndex),
																			bolLedition,
																			this.hasStringControlChanged(bytLstringBackgroundIndex)));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setDefaultsControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		final boolean bolLfX = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX);

		// Default checkbox :
		this.objGdefaultsThreeStatesJCheckBox.setEnabled(bolLedition);
		this.objGdefaultsThreeStatesJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS)	? ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
																															: this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_DEFAULTS)	? ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN
																																															: ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		this.objGdefaultsThreeStatesJCheckBox.setText(this.getBooleanControlLabelString(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS)	? Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS
																																								: Constants.bytS_BOOLEAN_LOCAL_DEFAULTS,
																						this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS)	? Language.intS_CHECKBOX_RELATIVE_DEFAULTS
																																								: Language.intS_CHECKBOX_DEFAULTS,
																						bolLedition));
		this.objGdefaultsThreeStatesJCheckBox.setToolTipText();
		this.objGdefaultsColonsJLabel.setEnabled(bolLedition);

		// Default value :
		this.objGdefaultsJScrollBar.setEnabled(bolLedition);
		this.objGdefaultsJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_DEFAULTS));
		this.objGdefaultsJScrollBar.setToolTipText();
		final boolean bolLdefaults = bolLedition && this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_DEFAULTS) && !bolLfX;
		this.objGdefaultsValueJLabel.setEnabled(bolLdefaults);
		this.objGdefaultsValueJLabel.setText(this.getByteControlLabelString(Constants.bytS_BYTE_LOCAL_DEFAULTS,
																			Strings.doConcat(	"\273 ",
																								this.getControlValue(Constants.bytS_BYTE_LOCAL_DEFAULTS)),
																			true,
																			bolLdefaults));
		this.objGdefaultsValueJLabel.setToolTipText();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setDwellControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Dwell label :
		this.objGdwellJLabel.setEnabled(bolLedition);
		this.objGdwellJLabel.setText(this.getByteControlLabelString(Constants.bytS_BYTE_LOCAL_DWELL,
																	this.getLanguageString(Language.intS_LABEL_DWELL),
																	true,
																	bolLedition));
		this.objGdwellColonsJLabel.setEnabled(bolLedition);

		// Dwell value :
		final byte bytLdwellValue = this.getControlValue(Constants.bytS_BYTE_LOCAL_DWELL);
		final boolean bolLdwellLocal = this.isByteLocal(Constants.bytS_BYTE_LOCAL_DWELL);
		this.objGdwellJScrollBar.setEnabled(bolLedition);
		this.objGdwellJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_DWELL));
		this.objGdwellJScrollBar.setToolTipText();
		this.objGdwellValueJLabel.setEnabled(bolLedition);
		this.objGdwellValueJLabel.setText(this.getControlLabelString(	Strings.doConcat(bytLdwellValue < 10 ? '0' : null, bytLdwellValue, " %"),
																		bolLdwellLocal,
																		bolLedition,
																		false));
		this.objGdwellValueJLabel.setToolTipText();

		// objGdwellValueJLabel.setText(JuggleStrings.doConcat(bolLdwellLocal ? strS_OPENING_HTML_ITALIC
		// : null, bytLdwellValue < 10 ? '0'
		// : null, bytLdwellValue, " %", bolLdwellLocal ? strS_CLOSING_HTML_ITALIC
		// : null), bolLedition);
	}

	/**
	 * @param b
	 */
	public void setExportCurrentPatterns(boolean bolP) {
		this.bolGexportCurrentPatterns = bolP;

	}

	/**
	 * @param intGfilteredPatternsNumber
	 *            the intGfilteredPatternsNumber to set
	 */
	public void setFilteredPatternsNumber(int intPfilteredPatternsNumber) {
		this.intGfilteredPatternsNumber = intPfilteredPatternsNumber;
	}

	/**
	 * @param i
	 */
	public void setFilteredShortcutsNumber(int intPfilteredShortcutsNumber) {
		this.intGfilteredShortcutsNumber = intPfilteredShortcutsNumber;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setFiltersControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)
									|| this.isBooleanLocal(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Filter button :
		this.objGfiltersJButton.setEnabled(this.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
		final boolean bolLactivated = this.objGfiltersJDialog.isFiltering();
		this.objGfiltersJButton.setImage(bolLactivated);
		this.objGfiltersJButton.setToolTipText();

		// Ball number :
		this.objGballsNumberJCheckBox.setEnabled(bolLedition);
		this.objGballsNumberJCheckBox.select(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER));
		this.objGballsNumberJCheckBox.setToolTipText();

		this.objGlowBallsNumberJComboBox.setEnabled(bolLedition);
		this.objGlowBallsNumberJComboBox.selectIndex(Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER)
														- Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
		this.objGlowBallsNumberJComboBox.setToolTipText();
		this.objGhighBallsNumberJComboBox.setEnabled(bolLedition);
		this.objGhighBallsNumberJComboBox.selectIndex(Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER)
														- Constants.bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE);
		this.objGhighBallsNumberJComboBox.setToolTipText();

		boolean bolLenabled = bolLedition && Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER);
		this.objGfromBallsNumberJLabel.setEnabled(bolLenabled);
		this.objGfromBallsNumberJLabel.setText(this.getLanguageString(Language.intS_LABEL_FROM));
		this.objGfromBallsNumberJLabel.setToolTipText();
		this.objGtoBallsNumberJLabel.setEnabled(bolLenabled);
		this.objGtoBallsNumberJLabel.setText(this.getLanguageString(Language.intS_LABEL_TO));
		this.objGtoBallsNumberJLabel.setToolTipText();
		this.objGballsNumberJLabel.setEnabled(bolLenabled);
		this.objGballsNumberJLabel.setText(this.getLanguageString(Language.intS_LABEL_BALLS));
		this.objGballsNumberJLabel.setToolTipText();

		// Skill :
		bolLenabled = bolLedition && Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_SKILL);
		this.objGskillJCheckBox.setEnabled(bolLedition);
		this.objGskillJCheckBox.select(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_SKILL));
		this.objGskillJCheckBox.setToolTipText();

		this.objGfromSkillJLabel.setEnabled(bolLenabled);
		this.objGfromSkillJLabel.setText(this.getLanguageString(Language.intS_LABEL_FROM));
		this.objGfromSkillJLabel.setToolTipText();

		this.objGlowSkillJComboBox.setItems();
		this.objGlowSkillJComboBox.setEnabled(bolLedition);
		this.objGlowSkillJComboBox.selectIndex(Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_SKILL));
		this.objGlowSkillJComboBox.setToolTipText();

		this.objGtoSkillJLabel.setEnabled(bolLenabled);
		this.objGtoSkillJLabel.setText(this.getLanguageString(Language.intS_LABEL_TO));
		this.objGtoSkillJLabel.setToolTipText();

		this.objGhighSkillJComboBox.setItems();
		this.objGhighSkillJComboBox.setEnabled(bolLedition);
		this.objGhighSkillJComboBox.selectIndex(Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_SKILL));
		this.objGhighSkillJComboBox.setToolTipText();

		// Mark :
		bolLenabled = bolLedition && Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MARK);
		this.objGmarkJCheckBox.setEnabled(bolLedition);
		this.objGmarkJCheckBox.select(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MARK));
		this.objGmarkJCheckBox.setToolTipText();

		this.objGfromMarkJLabel.setEnabled(bolLenabled);
		this.objGfromMarkJLabel.setText(this.getLanguageString(Language.intS_LABEL_FROM));
		this.objGfromMarkJLabel.setToolTipText();

		this.objGlowMarkJComboBox.setItems();
		this.objGlowMarkJComboBox.setEnabled(bolLedition);
		this.objGlowMarkJComboBox.selectIndex(Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_LOW_MARK));
		this.objGlowMarkJComboBox.setToolTipText();

		this.objGtoMarkJLabel.setEnabled(bolLenabled);
		this.objGtoMarkJLabel.setText(this.getLanguageString(Language.intS_LABEL_TO));
		this.objGtoMarkJLabel.setToolTipText();

		this.objGhighMarkJComboBox.setItems();
		this.objGhighMarkJComboBox.setEnabled(bolLedition);
		this.objGhighMarkJComboBox.selectIndex(Preferences.getGlobalBytePreference(Constants.bytS_BYTE_GLOBAL_HIGH_MARK));
		this.objGhighMarkJComboBox.setToolTipText();

		this.objGglobalMarkJButton.setEnabled(bolLenabled);

		// Shortcuts :
		final int intLfilteredPatternIndex = this.objGobjectsJList.getFilteredObjectIndex(this.getJuggleMasterPro().intGobjectIndex);
		this.objGshortcutsJComboBox.selectShortcut(intLfilteredPatternIndex);
		this.objGshortcutsJComboBox.setToolTipText();

		// Invalid patterns :
		switch (this.getPatternsManager().bytGpatternsManagerType) {
			case Constants.bytS_MANAGER_FILES_PATTERNS:
			case Constants.bytS_MANAGER_NEW_PATTERN:
				bolLenabled = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
				break;
			case Constants.bytS_MANAGER_JM_PATTERN:
			case Constants.bytS_MANAGER_NO_PATTERN:
			case Constants.bytS_MANAGER_NEW_ABSTRACT_LANGUAGE:
			default:
				bolLenabled = false;
		}
		this.objGinvalidPatternsJCheckBox.setEnabled(bolLenabled);
		this.objGinvalidPatternsJCheckBox.select(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS));
		this.objGinvalidPatternsJCheckBox.setToolTipText();
		this.objGinvalidPatternsJLabel.setEnabled(bolLenabled);
		this.objGinvalidPatternsJLabel.setText(this.getLanguageString(Language.intS_CHECKBOX_INVALID_PATTERNS));
		this.objGinvalidPatternsJLabel.setToolTipText();

		// Pattern list :
		this.objGobjectsJList.setLists();
		this.objGobjectsJList.selectIndex(intLfilteredPatternIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setFluidityControls() {

		final boolean bolLfluidity = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLUIDITY)
										|| this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Fluidity label :
		this.objGfluidityJLabel.setEnabled(bolLfluidity);
		this.objGfluidityJLabel.setText(this.getByteControlLabelString(	Constants.bytS_BYTE_LOCAL_FLUIDITY,
																		this.getLanguageString(Language.intS_LABEL_FLUIDITY),
																		false,
																		bolLfluidity));
		this.objGfluidityColonsJLabel.setEnabled(bolLfluidity);

		// Fluidity value :
		final byte bytLfluidityValue = this.getControlValue(Constants.bytS_BYTE_LOCAL_FLUIDITY);
		final boolean bolLfluidityLocal = this.isByteLocal(Constants.bytS_BYTE_LOCAL_FLUIDITY);
		this.objGfluidityJScrollBar.setEnabled(bolLfluidity);
		this.objGfluidityJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_FLUIDITY));
		this.objGfluidityJScrollBar.setToolTipText();
		this.objGfluidityValueJLabel.setEnabled(bolLfluidity);
		this.objGfluidityValueJLabel.setText(this.getControlLabelString(Strings.doConcat(	bytLfluidityValue < 10 ? '0' : null,
																							bytLfluidityValue,
																							" %"),
																		bolLfluidityLocal,
																		bolLfluidity,
																		this.hasByteControlChanged(Constants.bytS_BYTE_LOCAL_FLUIDITY)));
		this.objGfluidityValueJLabel.setToolTipText();

		// objGfluidityValueJLabel.setText(JuggleStrings.doConcat(bolLfluidityLocal ? strS_OPENING_HTML_ITALIC
		// : null, bytLfluidityValue < 10 ? '0'
		// : null, bytLfluidityValue, " %", bolLfluidityLocal ? strS_CLOSING_HTML_ITALIC
		// : null), bolLfluidity);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setHeightControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Height label :
		this.objGheightJLabel.setEnabled(bolLedition);
		this.objGheightJLabel.setText(this.getByteControlLabelString(	Constants.bytS_BYTE_LOCAL_HEIGHT,
																		this.getLanguageString(Language.intS_LABEL_HEIGHT),
																		true,
																		bolLedition));
		this.objGheightColonsJLabel.setEnabled(bolLedition);

		// Height value :
		final boolean bolLheightLocal = this.isByteLocal(Constants.bytS_BYTE_LOCAL_HEIGHT);
		this.objGheightJScrollBar.setEnabled(bolLedition);
		this.objGheightJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_HEIGHT));
		this.objGheightJScrollBar.setToolTipText();
		this.objGheightValueJLabel.setEnabled(bolLedition);
		this.objGheightValueJLabel.setText(this.getControlLabelString(	Strings.doConcat(Strings.getValueString(	this.getControlValue(Constants.bytS_BYTE_LOCAL_HEIGHT),
																												2,
																												0,
																												2,
																												false),
																						"  ^"),
																		bolLheightLocal,
																		bolLedition,
																		false));
		this.objGheightValueJLabel.setToolTipText();

		// objGheightValueJLabel.setText(JuggleStrings.doConcat(bolLheightLocal ? strS_OPENING_HTML_ITALIC
		// : null, JuggleTools.getValueString(getControlValue(Constants.bytS_BYTE_LOCAL_HEIGHT), 2, 0, 2, false),
		// " ^",
		// bolLheightLocal ? strS_CLOSING_HTML_ITALIC
		// : null), bolLedition);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public void setJButtonsEnabled(boolean bolPenabled) {
		final PatternsManager objLpatternsManager = this.getPatternsManager();
		final boolean bolLeditionAndPatternOrStyleFound = bolPenabled
																&& objLpatternsManager.getPatternBooleanValue(	this.objGjuggleMasterPro.intGobjectIndex,
																											Constants.bytS_BOOLEAN_LOCAL_EDITION,
																											Constants.bytS_UNCLASS_INITIAL)
															&& (this.intGfilteredPatternsNumber > 0
																|| objLpatternsManager.getPatternsFileManager().bolGstyleFound);

		this.objGclipboardJDialog.setCopyEnabled(bolLeditionAndPatternOrStyleFound);
		this.objGplayPauseJButton.setEnabled(bolLeditionAndPatternOrStyleFound
												&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPpatternsManager
	 */
	final public void setJugglePatternsManager(PatternsManager objPpatternsManager) {
		this.objGjuggleMasterPro.setPatternsManager(objPpatternsManager);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setJugglerControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		String strLjugglerValue;
		switch (this.getJugglerValue()) {
			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS:
				strLjugglerValue = "?";
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS:
				strLjugglerValue = "¦&nbsp;";
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS:
				strLjugglerValue = "÷";
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD:
				strLjugglerValue = "?";// "\u00b1";
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS:
				strLjugglerValue = "±";
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS:
				strLjugglerValue = "?";
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_ALL:
				strLjugglerValue = "#";
				break;

			case Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM:
			default:
				strLjugglerValue = "&nbsp;\267";
		}

		this.objGjugglerValueJLabel.setEnabled(bolLedition);
		this.objGjugglerValueJLabel.setText(this.getByteControlLabelString(	this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT)	? Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY
																																		: Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT,
																			strLjugglerValue,
																			false,
																			bolLedition	&& !this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX)
																					&& this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER)));
		this.objGjugglerValueJLabel.setToolTipText();
		this.objGjugglerJScrollBar.setEnabled(bolLedition);
		this.objGjugglerJScrollBar.selectValue(this.getJugglerValue());
		this.objGjugglerJScrollBar.setToolTipText();
		this.objGjugglerThreeStatesJCheckBox.setEnabled(bolLedition);
		this.objGjugglerThreeStatesJCheckBox.setText(this.getBooleanControlLabelString(	this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)	? Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL
																																							: Constants.bytS_BOOLEAN_LOCAL_JUGGLER,
																						this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)	? Language.intS_CHECKBOX_JUGGLER_TRAIL
																																							: Language.intS_CHECKBOX_JUGGLER,
																						bolLedition));
		this.objGjugglerThreeStatesJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL)	? ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
																														: this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_JUGGLER)	? ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN
																																														: ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		this.objGjugglerThreeStatesJCheckBox.setToolTipText();

		// Juggler color button :
		this.objGjugglerColorChooserDropDownJButton.setEnabled(bolLedition);
		this.objGjugglerColorChooserDropDownJButton.repaint();
		this.objGjugglerColorChooserDropDownJButton.setToolTipText();
		this.objGjugglerColorJLabel.setEnabled(bolLedition);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPlanguageIndex
	 */
	final public void setLanguage(int intPlanguageIndex, boolean bolPhideJugglePanel) {

		// Modify frames :
		// final SplashScreenJWindow objLsplashScreenJWindow =
		// new SplashScreenJWindow(this,
		// this.getLanguageString(JuggleLanguage.intS_SPLASH_APPLYING_THE_NEW_LANGUAGE));
		// objLsplashScreenJWindow.setInvisible(2560);
		this.doHidePopUps();

		// Load JM pattern manager :
		this.getJuggleMasterPro().doStopPattern();

		// Change language :
		this.intGlanguageIndex = intPlanguageIndex;
		this.objGrecentFilesJuggleJMenu.removeAll();
		this.doCreatePatternsMenu();
		this.setLanguageJLabels(bolPhideJugglePanel);
		final PatternsManager objLpatternsManager = new PatternsManager(Constants.bytS_MANAGER_JM_PATTERN);
		this.setJugglePatternsManager(objLpatternsManager);
		this.objGobjectsJList.setLists();
		this.doStartJuggling(objLpatternsManager.getPatternsFileManager().intGstartObjectIndex);
	}

	/**
	 * @param intGlanguageIndex
	 *            the intGlanguageIndex to set
	 */
	public void setLanguageIndex(int intPlanguageIndex) {
		this.intGlanguageIndex = intPlanguageIndex;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLanguageJLabels(boolean bolPhideControlJFrame) {

		final Rectangle objLcontrolJFrameRectangle = this.getBounds();

		// Save the current configuration :
		final PatternsManager objLsavedJugglePatternsManager = this.getPatternsManager();
		final byte bytLsavedAnimationStatus = this.objGjuggleMasterPro.bytGanimationStatus;
		final int intLsavedObjectIndex = this.objGjuggleMasterPro.intGobjectIndex;

		// Set the special language pattern manager :
		final PatternsManager objLlanguageJugglePatternsManager = new PatternsManager(Constants.bytS_MANAGER_NEW_ABSTRACT_LANGUAGE);
		this.objGjuggleMasterPro.bytGanimationStatus = Constants.bytS_STATE_ANIMATION_PAUSED;
		this.objGjuggleMasterPro.intGobjectIndex = objLlanguageJugglePatternsManager.getPatternsFileManager().intGstartObjectIndex;
		this.setJugglePatternsManager(objLlanguageJugglePatternsManager);

		// Set dialog language :
		try {
			final Locale objLlocale = new Locale(this.getLanguageString(Language.intS_LANGUAGE_ISO_639_1_CODE));
			JComponent.setDefaultLocale(objLlocale);
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while trying to apply ISO 639-1 language code");
		}

		// Place the widgets :
		this.setWidgetsPreferredSizes(false);
		this.setWidgetsControls();
		if (bolPhideControlJFrame) {
			this.setVisible(false);
			this.getJuggleMasterPro().getFrame().setVisible(false);
		}
		this.validate();
		this.pack();
		this.setWidgetsPreferredSizes(true);

		final int intLoldWidth = (int) objLcontrolJFrameRectangle.getWidth();
		final int intLnewWidth = Math.max(this.getWidth(), intLoldWidth);
		if (intLnewWidth > intLoldWidth) {
			this.setBounds(	Math.max(0, this.getX() - (intLnewWidth - intLoldWidth)),
							this.getY(),
							intLnewWidth,
							(int) objLcontrolJFrameRectangle.getHeight());
		} else {
			this.setBounds(	Math.max(0, (int) objLcontrolJFrameRectangle.getX()),
							this.getY(),
							intLoldWidth,
							(int) objLcontrolJFrameRectangle.getHeight());
		}
		if (bolPhideControlJFrame) {
			this.setVisible(true);
			this.getJuggleMasterPro().getFrame().setVisible(true);
			this.getJuggleMasterPro().getFrame().repaint();
		}

		// Restore the previous configuration :
		this.setJugglePatternsManager(objLsavedJugglePatternsManager);
		this.objGjuggleMasterPro.intGobjectIndex = intLsavedObjectIndex;
		this.objGjuggleMasterPro.bytGanimationStatus = bytLsavedAnimationStatus;
		this.setWidgetsControls();
		this.setJButtonsEnabled(true);
		this.repaint();

		// Set the menus :
		this.setMenusJLabels();
		this.setMenusEnabled();
		this.objGjuggleMasterPro.objGdataJFrame.setHeaders();
		this.objGjuggleMasterPro.objGdataJFrame.setButtonsJLabels();
	}

	final public void setLightAndSoundsControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Sound label :
		this.objGsoundsJCheckBox.setEnabled(bolLedition);
		this.objGsoundsJCheckBox.setText(this.getBooleanControlLabelString(	Constants.bytS_BOOLEAN_LOCAL_SOUNDS,
																			Language.intS_CHECKBOX_SOUNDS,
																			bolLedition));
		final boolean bolLselected = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SOUNDS);
		this.objGsoundsJCheckBox.select(bolLselected);
		this.objGsoundsJCheckBox.setToolTipText();
		this.objGsoundsColonsJLabel.setEnabled(bolLedition);

		// Catch sound label :
		this.objGcatchSoundsJCheckBox.setEnabled(bolLedition);
		this.objGcatchSoundsJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS));
		this.objGcatchSoundsJCheckBox.setText(this.getBooleanControlLabelString(Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS,
																				Language.intS_CHECKBOX_CATCH_SOUNDS,
																				bolLedition));
		this.objGcatchSoundsJCheckBox.setToolTipText();

		// Throw sound label :
		this.objGthrowSoundsJCheckBox.setEnabled(bolLedition);
		this.objGthrowSoundsJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS));
		this.objGthrowSoundsJCheckBox.setText(this.getBooleanControlLabelString(Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS,
																				Language.intS_CHECKBOX_THROW_SOUNDS,
																				bolLedition));
		this.objGthrowSoundsJCheckBox.setToolTipText();

		// Metronome sound label :
		this.objGmetronomeJCheckBox.setEnabled(bolLedition);
		this.objGmetronomeJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_METRONOME));
		this.objGmetronomeJCheckBox.setText(this.getBooleanControlLabelString(	Constants.bytS_BOOLEAN_LOCAL_METRONOME,
																				Language.intS_CHECKBOX_METRONOME,
																				bolLedition));
		this.objGmetronomeJCheckBox.setToolTipText();

		this.objGampersandJLabel.setEnabled(bolLedition);

		// Light label :
		this.objGlightJCheckBox.setEnabled(bolLedition);
		this.objGlightJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_LIGHT));
		this.objGlightJCheckBox.setText(this.getBooleanControlLabelString(	Constants.bytS_BOOLEAN_LOCAL_LIGHT,
																			Language.intS_CHECKBOX_LIGHT,
																			bolLedition));
		this.objGlightJCheckBox.setToolTipText();

		// Background color button :
		this.objGbackgroundColorChooserDropDownJButton.setEnabled(bolLedition);
		this.objGbackgroundColorChooserDropDownJButton.repaint();
		this.objGbackgroundColorJLabel.setEnabled(bolLedition);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setListsEnabled() {
		this.objGshortcutsJComboBox.setEnabled(this.intGfilteredShortcutsNumber > 0);
		this.objGobjectsJList.setEnabled(this.intGfilteredPatternsNumber > 0);
	}

	/**
	 * @param bolGloadingPatternsFile
	 *            the bolGloadingPatternsFile to set
	 */
	public void setLoadingPatternsFile(boolean bolPloadingPatternsFile) {
		this.bolGloadingPatternsFile = bolPloadingPatternsFile;
	}

	final public void setMenuItemLabel(JMenuItem objPjMenuItem, int intPlanguageIndex) {
		this.setMenuItemLabel(objPjMenuItem, intPlanguageIndex, null);
	}

	final public void setMenuItemLabel(JMenuItem objPjMenuItem, int intPlanguageIndex, String strPsuffix) {
		MenusTools.setNoMnemonicLabel(objPjMenuItem, Strings.doConcat(this.getLanguageString(intPlanguageIndex), strPsuffix));
	}

	final public void setMenuMnemonic(JMenuItem objPjMenuItem, int intPlanguageIndex) {
		MenusTools.setMenuMnemonic(objPjMenuItem, this.getLanguageString(intPlanguageIndex));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setMenusEnabled() {

		final PatternsManager objLpatternsManager = this.getPatternsManager();
		final boolean bolLedition = (objLpatternsManager.getPatternBooleanValue(this.objGjuggleMasterPro.intGobjectIndex,
																				Constants.bytS_BOOLEAN_LOCAL_EDITION));
		final boolean bolLeditionAndPatternOrStyleFound = (bolLedition && (this.intGfilteredPatternsNumber > 0
																			|| objLpatternsManager.getPatternsFileManager().bolGstyleFound));

		// Pattern menu :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGimportExtendedJMenu.setEnabled((objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
														|| objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN));
			this.objGimportStylesJMenuItem.setEnabled((objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
														|| objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN));
			this.objGimportPatternsJMenuItem.setEnabled((objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS));
			this.objGimportSiteswapsJMenuItem.setEnabled((objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS));
		}
		// this.objGexportPatternsFileJMenuItem.setEnabled(this.bolGexportInitialPatterns);
		this.objGexportPatternsFileJMenuItem.setEnabled(this.isExportCurrentPatterns());
		this.objGexportSiteswapsFileJMenuItem.setEnabled(this.isExportCurrentPatterns());
		this.objGexportStylesFileJMenuItem.setEnabled(this.isExportCurrentPatterns());
		this.objGreloadPatternJuggleJMenuItem.setEnabled(bolLeditionAndPatternOrStyleFound);
		this.objGreloadFileJMenuItem.setEnabled(objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
		this.objGrecentFilesJuggleJMenu.setEnabled();

		// Edition menu :
		this.objGfindJMenuItem.setEnabled(objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
		this.objGfindNextJMenuItem.setEnabled(objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
		this.objGfindPreviousJMenuItem.setEnabled(objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
		this.objGfiltersJMenuItem.setEnabled(objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
		this.objGclipboardExtendedJMenu.setEnabled(bolLeditionAndPatternOrStyleFound || !this.objGclipboardJDialog.isEmpty());
		this.objGsimpleCopyJuggleJMenuItem.setEnabled(bolLeditionAndPatternOrStyleFound);
		this.objGdetailedCopyJuggleJMenuItem.setEnabled(bolLeditionAndPatternOrStyleFound);
		this.objGclipboardJCheckBoxMenuItem.setEnabled(bolLeditionAndPatternOrStyleFound || !this.objGclipboardJDialog.isEmpty());
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			final boolean bolLplayable = (this.objGjuggleMasterPro.objGsiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE);
			this.objGscreenShotJMenuItem.setEnabled(bolLplayable && !this.objGjuggleMasterPro.bolGscreenPlay);
			this.objGscreenPlayExtendedJMenu.setEnabled(bolLplayable);
			this.objGstartScreenPlayJMenuItem.setEnabled(bolLplayable);
			this.objGstopScreenPlayJMenuItem.setEnabled(bolLplayable && this.objGjuggleMasterPro.bolGscreenPlay);
		}
		this.objGdataJCheckBoxMenuItem.setEnabled(bolLeditionAndPatternOrStyleFound);
		if (!bolLeditionAndPatternOrStyleFound) {
			this.objGdataJCheckBoxMenuItem.selectState(false);
			this.doAddAction(Constants.intS_ACTION_HIDE_DATA_FRAME);
		}
		this.objGaboutJMenuItem.setEnabled();

		// Avoid an hidden bug which sometimes leaves the mouse cursor in a waiting state :
		this.setMouseCursorEnabled(true);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setMenusJLabels() {

		// Pattern menu :
		if (this.objGpatternsExtendedJMenu != null) {
			this.objGpatternsExtendedJMenu.setLabel();
		}
		this.objGfileExtendedJMenu.setLabel();
		this.objGnewPatternJMenuItem.setLabel();
		this.objGreloadPatternJuggleJMenuItem.setLabel();
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGopenFileJMenuItem.setLabel();
			this.objGimportPatternsJMenuItem.setLabel();
			this.objGimportSiteswapsJMenuItem.setLabel();
			this.objGimportStylesJMenuItem.setLabel();
			this.objGimportExtendedJMenu.setLabel();
		}
		this.objGreloadFileJMenuItem.setLabel();
		this.objGrecentFilesJuggleJMenu.setLabel();

		this.objGconsoleJCheckBoxMenuItem.setLabel();
		this.objGconsoleJDialog.setContent(null, false);
		this.objGconsoleJDialog.setLabels();
		this.objGnewPatternsFileJMenuItem.setLabel();
		this.objGexportExtendedJMenu.setLabel();
		this.objGexportPatternsFileJMenuItem.setLabel();
		this.objGexportSiteswapsFileJMenuItem.setLabel();
		this.objGexportStylesFileJMenuItem.setLabel();
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGpreferencesJMenuItem.setLabel();
		}
		this.objGquitJMenuItem.setLabel();

		// Edition menu :
		this.objGeditionExtendedJMenu.setLabel();
		this.objGfindJMenuItem.setLabel();
		this.objGfindNextJMenuItem.setLabel();
		this.objGfindPreviousJMenuItem.setLabel();
		this.objGfiltersJMenuItem.setLabel();
		this.objGfindJDialog.setLabels();
		this.objGfiltersJDialog.setLabels();
		this.objGclipboardExtendedJMenu.setLabel();
		this.objGsimpleCopyJuggleJMenuItem.setLabel();
		this.objGdetailedCopyJuggleJMenuItem.setLabel();
		this.objGclipboardJCheckBoxMenuItem.setLabel();
		this.objGfreeClipboardJuggleJMenuItem.setLabel();
		this.objGclipboardJDialog.setTitle(this.getLanguageString(Language.intS_TITLE_CLIPBOARD));
		this.objGclipboardJDialog.setSimpleText(this.getLanguageString(Language.intS_BUTTON_SIMPLE_COPY));
		this.objGclipboardJDialog.setDetailedText(this.getLanguageString(Language.intS_BUTTON_DETAILED_COPY));
		this.objGclipboardJDialog.setFreeText(this.getLanguageString(Language.intS_BUTTON_FREE));
		this.objGclipboardJDialog.setCopyText(this.getLanguageString(Language.intS_BUTTON_COPY));
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGscreenShotJMenuItem.setLabel();
			this.objGscreenPlayExtendedJMenu.setLabel();
			this.objGstartScreenPlayJMenuItem.setLabel(this.objGjuggleMasterPro.bolGscreenPlay);
			this.objGstopScreenPlayJMenuItem.setLabel(this.objGjuggleMasterPro.bolGscreenPlay);
		}
		this.objGdataJCheckBoxMenuItem.setLabels();

		// Help menu :
		this.objGhelpExtendedJMenu.setLabel();
		this.objGhelpJMenuItem.setLabel();
		this.objGsiteswapJuggleJMenuItem.setLabel();
		this.objGlanguageExtendedJMenu.setLabel();
		for (int intLmenuItemIndex = 0; intLmenuItemIndex < this.objGlanguageJRadioButtonMenuItemA.length; ++intLmenuItemIndex) {
			this.objGlanguageJRadioButtonMenuItemA[intLmenuItemIndex].setLabel();
		}
		this.objGnewLanguageJMenuItem.setLabel();
		this.objGexportCurrentLanguageJMenuItem.setLabel();
		this.objGrecordsLinksExtendedJMenu.setLabel();
		this.objGvideoLinksExtendedJMenu.setLabel();
		this.objGwebLinksExtendedJMenu.setLabel();
		this.objGdevelopmentJMenuItem.setLabel();
		this.objGlicenceJMenuItem.setLabel();
		this.objGaboutJMenuItem.setLabel();
	}

	final public void setMotionControls() {
		this.objGplayPauseJButton.setImage(false);
		this.objGplayPauseJButton.setToolTipText();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public void setMouseCursorEnabled(boolean bolPenabled) {
		this.objGjuggleMasterPro.getFrame().setCursor(Cursor.getPredefinedCursor(bolPenabled ? Cursor.DEFAULT_CURSOR : Cursor.WAIT_CURSOR));
		this.setCursor(Cursor.getPredefinedCursor(bolPenabled ? Cursor.DEFAULT_CURSOR : Cursor.WAIT_CURSOR));
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setNewPatternManager() {
		this.doHidePopUps();
		this.objGjuggleMasterPro.doStopPattern();
		FileActions.doLoadJugglePatternsManager(this,
												Constants.bytS_MANAGER_NEW_PATTERN,
												Strings.strS_EMPTY,
												this.getLanguageString(Language.intS_TITLE_NEW_PATTERN_FILE),
												null,
												true);
		FileActions.doCheckPatternsMenuItems(this);
		this.doStartJuggling(this.getPatternsManager().getPatternsFileManager().intGstartObjectIndex);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setPatternControls() {

		final PatternsManager objLpatternsManager = this.getPatternsManager();
		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		// Reload pattern :
		this.objGreloadPatternJButton.setEnabled(bolLedition);
		this.objGreloadPatternJButton.setToolTipText();

		// Pattern label :
		this.objGpatternJLabel.setEnabled(bolLedition);
		this.objGpatternJLabel.setText(this.getControlLabelString(	Strings.doConcat(this.getLanguageString(Language.intS_LABEL_PATTERN),
																					bolLedition	? Strings.doConcat(	"�(",
																													Siteswap.getBallsNumberString(	this.objGjuggleMasterPro.objGsiteswap.intGballsNumber,
																																					true),
																													')')
																								: null),
																	false,
																	bolLedition,
																	bolLedition && this.hasStringControlChanged(Constants.bytS_STRING_LOCAL_PATTERN)));
		this.objGpatternJLabel.setToolTipText();

		this.objGpatternColonsJLabel.setEnabled(bolLedition);

		// Reverse pattern button :
		this.objGreversePatternJButton.setEnabled(bolLedition);
		this.objGreversePatternJButton.setImage();
		this.objGreversePatternJButton.setToolTipText();

		// Pattern textfield :
		this.objGpatternJTextField.setEnabled(bolLedition);
		final String strLcurrentPattern = this.getLanguage().getTranslatedTokensString(	this.getControlString(Constants.bytS_STRING_LOCAL_PATTERN),
																						this.getPatternsManager().bytGpatternsManagerType);
		if (!strLcurrentPattern.equals(this.objGpatternJTextField.getText())) {
			this.objGpatternJTextField.setTextString(strLcurrentPattern);
		}
		this.objGpatternJTextField.setToolTipText(this.objGpatternJTextField.getText());

		// Pattern skill :
		this.objGskillJComboBox.setItems();
		this.objGskillJComboBox.setEnabled(bolLedition);
		this.objGskillJComboBox.selectIndex(this.getControlValue(Constants.bytS_BYTE_LOCAL_SKILL));
		this.objGskillJComboBox.setToolTipText();

		// Pattern mark :
		this.objGmarkJComboBox.setItems();
		this.objGmarkJComboBox.setEnabled(bolLedition);
		this.objGmarkJComboBox.selectIndex(this.getControlValue(Constants.bytS_BYTE_LOCAL_MARK));
		this.objGmarkJComboBox.setToolTipText();
		this.objGlocalMarkJButton.setEnabled(bolLedition);

		// Import patterns :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGimportPatternsJButton.setEnabled(objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
			this.objGimportPatternsJButton.setToolTipText();
		}

		// Mirror checkbox :
		this.objGmirrorJCheckBox.setEnabled(bolLedition);
		this.objGmirrorJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_MIRROR));
		this.objGmirrorJCheckBox.setText(this.getBooleanControlLabelString(	Constants.bytS_BOOLEAN_LOCAL_MIRROR,
																			Language.intS_CHECKBOX_MIRROR,
																			bolLedition));
		this.objGmirrorJCheckBox.setToolTipText();

		// Frame titles :
		this.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setSiteswapControls() {

		final PatternsManager objLpatternsManager = this.getPatternsManager();
		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		final boolean bolLrefreshSiteswap = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION)	&& this.intGfilteredPatternsNumber > 0
											&& this.objGjuggleMasterPro.objGsiteswap.bytGstatus >= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER
											&& (!this.objGjuggleMasterPro.objGsiteswap	.toString()
																						.equals(this.getControlString(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)	? Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP
																																															: Constants.bytS_STRING_LOCAL_SITESWAP))
												|| this.objGjuggleMasterPro.objGsiteswap.bolGsymmetric);

		// Siteswap checkbox :
		this.objGsiteswapThreeStatesJCheckBox.setEnabled(bolLedition);
		this.objGsiteswapThreeStatesJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS)	? ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
																															: this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SITESWAP)	? ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN
																																															: ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		final byte bytLcontrolType = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS)	? Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS
																											: Constants.bytS_BOOLEAN_LOCAL_SITESWAP;
		this.objGsiteswapThreeStatesJCheckBox.setText(this.getBooleanControlLabelString(bytLcontrolType,
																						bytLcontrolType == Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS	? Language.intS_CHECKBOX_SITESWAP_AND_THROWS_VALUES
																																						: Language.intS_CHECKBOX_SITESWAP,
																						bolLedition));
		this.objGsiteswapThreeStatesJCheckBox.setToolTipText();
		this.objGsiteswapColonsJLabel.setEnabled(bolLedition);

		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGimportSiteswapsJButton.setEnabled(objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS);
		}

		this.objGrefreshSiteswapJButton.setEnabled(bolLrefreshSiteswap);
		this.objGrefreshSiteswapJButton.setToolTipText();

		// Siteswap textpane :
		this.objGsiteswapJTextPane.setEnabled(bolLedition);
		Color objLbackgroundColor = null;
		switch (this.objGjuggleMasterPro.objGsiteswap.bytGstatus) {

			case Constants.bytS_STATE_SITESWAP_EMPTY:
			case Constants.bytS_STATE_SITESWAP_PLAYABLE:
				objLbackgroundColor = Constants.objS_PEN_COLORS_LIGHT_YELLOW_COLOR;
				break;

			case Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER:
			case Constants.bytS_STATE_SITESWAP_UNPLAYABLE:
				objLbackgroundColor = Color.ORANGE;
				break;

			case Constants.bytS_STATE_SITESWAP_UNEXPECTED_CHAR:
			case Constants.bytS_STATE_SITESWAP_ODD_THROW_VALUE:
			default:
				objLbackgroundColor = Color.RED;
		}
		this.objGsiteswapJTextPane.setBackground(objLbackgroundColor);
		try {
			this.objGsiteswapJTextPane.selectText(bolLedition	? this.getControlString(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)	? Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP
																																							: Constants.bytS_STRING_LOCAL_SITESWAP)
																: Strings.strS_EMPTY);
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while setting siteswap text");
		}
		this.objGsiteswapJTextPane.setToolTipText();

		// Import siteswap button :
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			this.objGimportSiteswapsJButton.setToolTipText();
		}

		// Reverse siteswap checkbox :
		this.objGreverseSiteswapJCheckBox.setEnabled(bolLedition);
		this.objGreverseSiteswapJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP));
		this.objGreverseSiteswapJCheckBox.setText(this.getBooleanControlLabelString(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP,
																					Language.intS_CHECKBOX_REVERSE_SITESWAP,
																					bolLedition));
		this.objGreverseSiteswapJCheckBox.setToolTipText();

		// Siteswap color button :
		this.objGsiteswapColorChooserDropDownJButton.setEnabled(bolLedition);
		this.objGsiteswapColorChooserDropDownJButton.repaint();
		this.objGsiteswapColorChooserDropDownJButton.setToolTipText();
		this.objGsiteswapColorJLabel.setEnabled(bolLedition);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setSpeedControls() {

		final boolean bolLspeed = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_SPEED)
									|| this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Speed label :
		this.objGspeedJLabel.setEnabled(bolLspeed);
		this.objGspeedJLabel.setText(this.getByteControlLabelString(Constants.bytS_BYTE_LOCAL_SPEED,
																	this.getLanguageString(Language.intS_LABEL_SPEED),
																	true,
																	bolLspeed));
		this.objGspeedColonsJLabel.setEnabled(bolLspeed);

		// Speed value :
		final boolean bolLspeedLocal = this.isByteLocal(Constants.bytS_BYTE_LOCAL_SPEED);
		this.objGspeedJScrollBar.setEnabled(bolLspeed);
		this.objGspeedJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED));
		this.objGspeedJScrollBar.setToolTipText();
		this.objGspeedValueJLabel.setEnabled(bolLspeed);
		this.objGspeedValueJLabel.setText(this.getControlLabelString(	Strings.doConcat("\327 ",
																						PatternsManager.getByteParameterValueString(Constants.bytS_BYTE_LOCAL_SPEED,
																																	this.getControlValue(Constants.bytS_BYTE_LOCAL_SPEED),
																																	Constants.bytS_EXTENSION_JMP)),
																		bolLspeedLocal,
																		bolLspeed,
																		this.hasByteControlChanged(Constants.bytS_BYTE_LOCAL_SPEED)));
		this.objGspeedValueJLabel.setToolTipText();
		// objGspeedValueJLabel.setText(JuggleStrings.doConcat(bolLspeedLocal ? strS_OPENING_HTML_ITALIC
		// : null, "\327 ",
		// JugglePatternsManager.getByteParameterValueString(
		// Constants.bytS_BYTE_LOCAL_SPEED, getControlValue(Constants.bytS_BYTE_LOCAL_SPEED),
		// true), bolLspeedLocal ? strS_CLOSING_HTML_ITALIC
		// : null), bolLspeed);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setStrobeControls() {

		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);

		// Strobe value :
		final byte bytLstrobe = this.getControlValue(Constants.bytS_BYTE_LOCAL_STROBE);
		final String strLstrobeValue = bytLstrobe == Constants.bytS_BYTE_LOCAL_STROBE_EACH_COUNT	? "   /T"
																									: bytLstrobe == Constants.bytS_BYTE_LOCAL_STROBE_EACH_CATCH	? "    \370"
																																								: Strings.doConcat(	'/',
																																													bytLstrobe);

		final boolean bolLflashOrRobot = bolLedition
												&& (this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLASH)
												|| this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_ROBOT))
											&& !this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX);
		this.objGstrobeValueJLabel.setEnabled(bolLflashOrRobot);
		this.objGstrobeValueJLabel.setText(this.getByteControlLabelString(	Constants.bytS_BYTE_LOCAL_STROBE,
																			strLstrobeValue,
																			false,
																			bolLflashOrRobot));
		this.objGstrobeValueJLabel.setToolTipText();
		this.objGstrobeJScrollBar.setEnabled(bolLedition);
		this.objGstrobeJScrollBar.selectValue(this.getControlValue(Constants.bytS_BYTE_LOCAL_STROBE));
		this.objGstrobeJScrollBar.setToolTipText();

		// Strobe checkbox :
		this.objGstrobeThreeStatesJCheckBox.setEnabled(bolLedition);
		this.objGstrobeThreeStatesJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLASH)	? ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN
																												: this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_ROBOT)	? ThreeStatesButtonModel.bolS_SELECTED_BOOLEAN
																																												: ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		final byte bytLcontrolType = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FLASH)	? Constants.bytS_BOOLEAN_LOCAL_FLASH
																								: Constants.bytS_BOOLEAN_LOCAL_ROBOT;
		this.objGstrobeThreeStatesJCheckBox.setText(this.getBooleanControlLabelString(	bytLcontrolType,
																						bytLcontrolType == Constants.bytS_BOOLEAN_LOCAL_FLASH	? Language.intS_CHECKBOX_ROBOT_AND_FLASH
																																				: Language.intS_CHECKBOX_ROBOT,
																						bolLedition));
		this.objGstrobeThreeStatesJCheckBox.setToolTipText();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setStyleControls() {

		final PatternsManager objLpatternsManager = this.getPatternsManager();
		final boolean bolLlocal = this.isBooleanLocal(Constants.bytS_BOOLEAN_LOCAL_STYLE);
		final boolean bolLedition = this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		final boolean bolLeditionAndStyle = bolLedition && objLpatternsManager.getPatternsFileManager().bolGstyleFound;
		final boolean bolLeditionAndPatternOrStyle = bolLedition && (this.intGfilteredPatternsNumber > 0
																		|| objLpatternsManager.getPatternsFileManager().bolGstyleFound);

		// Style checkbox :
		this.objGstyleJCheckBox.setEnabled(bolLeditionAndStyle);
		this.objGstyleJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_STYLE));
		this.objGstyleJCheckBox.setText(this.getControlLabelString(	this.getLanguageString(Language.intS_CHECKBOX_STYLE),
																	bolLlocal,
																	bolLeditionAndStyle,
																	this.hasBooleanControlChanged(Constants.bytS_BOOLEAN_LOCAL_STYLE)
																							|| this.hasStringControlChanged(Constants.bytS_STRING_LOCAL_STYLE)));
		this.objGstyleJCheckBox.setToolTipText();

		// FX checkbox :
		this.objGfXJCheckBox.setEnabled(bolLedition);
		this.objGfXJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_FX));
		this.objGfXJCheckBox.setText(this.getBooleanControlLabelString(Constants.bytS_BOOLEAN_LOCAL_FX, Language.intS_CHECKBOX_FX, bolLedition));
		this.objGfXJCheckBox.setToolTipText();
		this.objGstyleColonsJLabel.setEnabled(bolLeditionAndStyle);

		this.objGsortStylesJButton.setEnabled(bolLedition);
		this.objGsortStylesJButton.setToolTipText();

		this.objGstyleJComboBox.setEnabled(bolLeditionAndPatternOrStyle);
		this.objGstyleJComboBox.selectItem(bolLedition	? this.getControlString(Constants.bytS_STRING_LOCAL_STYLE)
														: Constants.strS_STRING_LOCAL_STYLE_DEFAULT);
		this.objGstyleJComboBox.setToolTipText();
		if (this.objGjuggleMasterPro.bolGprogramTrusted) {
			final boolean bolLenabled = objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS
										|| objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN;
			this.objGimportStylesJButton.setEnabled(bolLenabled);
			this.objGimportStylesJButton.setToolTipText(bolLenabled ? this.getLanguageString(Language.intS_TOOLTIP_IMPORT_STYLES) : null);
		}

		// Reverse style checkbox :
		this.objGreverseStyleJCheckBox.setEnabled(bolLeditionAndPatternOrStyle);
		this.objGreverseStyleJCheckBox.select(this.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE));
		this.objGreverseStyleJCheckBox.setText(this.getBooleanControlLabelString(	Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE,
																					Language.intS_CHECKBOX_REVERSE_STYLE,
																					bolLeditionAndPatternOrStyle));
		this.objGreverseStyleJCheckBox.setToolTipText();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setTitles() {

		// Panel title :
		final PatternsManager objLpatternsManager = this.getPatternsManager();
		this.setTitle(MenusTools.getNoMnemonicLabel(Strings.doConcat(	"\242 ",
																		objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_NO_PATTERN
																					|| objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS ? objLpatternsManager.getPatternsFileManager().getTitlesString() : objLpatternsManager.bytGpatternsManagerType == Constants.bytS_MANAGER_NEW_PATTERN ? this.getLanguageString(Language.intS_TITLE_NEW_PATTERN_FILE) : this.getLanguageString(Language.intS_TITLE_CONSOLE),
																		" - ",
																		this.getLanguageString(Language.intS_TITLE_JUGGLE_MASTER_PRO))));

		// Frame Title :
		try {
			final String strLpatternName = Strings.doConcat(this.getLanguage()
																.getTranslatedTokensString(	this.getControlString(Constants.bytS_STRING_LOCAL_PATTERN),
																							objLpatternsManager.bytGpatternsManagerType),
															objLpatternsManager.hasPatternChanged(this.objGjuggleMasterPro.intGobjectIndex)	? Strings.strS_RIGHT_ASTERIX
																																			: null);

			this.objGjuggleMasterPro.getFrame()
									.setTitle(Strings.doConcat(	"\370 ",
																strLpatternName	.trim()
																				.length() > 0	? strLpatternName
																								: this.getLanguageString(Language.intS_TITLE_VISUAL),
																" - ",
																this.getLanguageString(Language.intS_TITLE_JUGGLE_MASTER_PRO)));
		} catch (final Throwable objPthrowable) {
			this.objGjuggleMasterPro.getFrame()
									.setTitle(Strings.doConcat(	"\370 ",
																this.getLanguageString(Language.intS_TITLE_VISUAL),
																" - ",
																this.getLanguageString(Language.intS_TITLE_JUGGLE_MASTER_PRO)));
		}
	}

	final public void setToolTipsProperties() {
		final boolean bolLdisplayToolTips = Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_TOOLTIPS);
		ToolTipManager.sharedInstance().setInitialDelay(bolLdisplayToolTips ? 0 : Integer.MAX_VALUE);
		ToolTipManager.sharedInstance().setDismissDelay(bolLdisplayToolTips ? Integer.MAX_VALUE : 0);
		ToolTipManager.sharedInstance().setReshowDelay(bolLdisplayToolTips ? 0 : Integer.MAX_VALUE);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	public final void setWidgetsControls() {

		this.setPatternControls();
		this.setSiteswapControls();
		this.setStyleControls();
		this.setBallsControls();
		this.setJugglerControls();
		this.setColorsControls();
		this.setDefaultsControls();
		this.setStrobeControls();
		this.setLightAndSoundsControls();
		this.setHeightControls();
		this.setSpeedControls();
		this.setDwellControls();
		this.setFluidityControls();
		this.setMotionControls();
		this.setFiltersControls();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setWidgetsPreferredSizes(boolean bolPactive) {

		// Pattern line :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGpatternJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGpatternColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGpatternJTextField);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGskillJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGmirrorJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGmarkJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGlocalMarkJButton);

		// Siteswap line :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGsiteswapThreeStatesJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGsiteswapColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGsiteswapJTextPane);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGreverseSiteswapJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGsiteswapColorJLabel);

		// Style line :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGstyleJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfXJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGstyleColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGstyleJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGreverseStyleJCheckBox);

		// Ball & juggler line :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGballsThreeStatesJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGballsColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGballsJScrollBar, null);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGballsValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGjugglerValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGjugglerJScrollBar, null);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGjugglerThreeStatesJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGjugglerColorJLabel);

		// Color line :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGcolorsThreeStatesJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGcolorsColonsJLabel);

		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGcolorsJTextPane);
		this.objGcolorsJTextPane.setMinimumSize(new Dimension(0, this.objGcolorsJTextPane.getHeight()));
		// ControlJFrame.setWidgetPreferredSize(this.objGcolorsJScrollPane);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGcolorsValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGalternateColorsValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGalternateColorsJScrollBar, null);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGalternateColorsJCheckBox);

		// Default & strobe line :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdefaultsThreeStatesJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdefaultsColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdefaultsJScrollBar, null);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdefaultsValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGstrobeValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGstrobeJScrollBar, null);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGstrobeThreeStatesJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGbackgroundColorJLabel);

		// Sound & light line :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGsoundsJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGsoundsColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGcatchSoundsJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGthrowSoundsJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGmetronomeJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGampersandJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGlightJCheckBox);

		// Height, speed, dwell, fluidity & play/pause lines :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGheightJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGheightColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGheightJScrollBar);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGheightValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGspeedJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGspeedColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGspeedJScrollBar);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGspeedValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdwellJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdwellColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdwellJScrollBar);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGdwellValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfluidityJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfluidityColonsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfluidityJScrollBar);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfluidityValueJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGplayPauseJButton);

		// Filters :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfiltersJButton);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGinvalidPatternsJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGinvalidPatternsJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGballsNumberJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfromBallsNumberJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGlowBallsNumberJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGtoBallsNumberJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGhighBallsNumberJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGballsNumberJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGskillJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfromSkillJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGlowSkillJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGtoSkillJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGhighSkillJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGmarkJCheckBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGfromMarkJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGlowMarkJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGtoMarkJLabel);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGhighMarkJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGglobalMarkJButton);

		// Pattern list :
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGshortcutsJComboBox);
		ControlJFrame.setWidgetPreferredSize(bolPactive, this.objGobjectsJListJScrollPane);
	}

	final public void setWindowBounds(Window objPwindow) {
		this.setWindowBounds(objPwindow, null, false);
	}

	final public void setWindowBounds(Window objPwindow, int intPwidth, int intPheight) {
		this.setWindowBounds(objPwindow, intPwidth, intPheight, null, false);
	}

	final public void setWindowBounds(Window objPwindow, int intPwidth, int intPheight, Window objPparentWindow) {
		this.setWindowBounds(objPwindow, intPwidth, intPheight, objPparentWindow, false);
	}

	final public void setWindowBounds(Window objPwindow, int intPwidth, int intPheight, Window objPparentWindow, boolean bolPrightJustified) {

		final int intLwidth = Math.min(intPwidth, Constants.intS_GRAPHICS_SCREEN_WIDTH);
		final int intLheight = Math.min(intPheight, Constants.intS_GRAPHICS_SCREEN_HEIGHT);

		final int intLparentFrameX = objPparentWindow != null ? (int) objPparentWindow.getLocation().getX() : (int) this.getLocation().getX();
		final int intLparentFrameY = objPparentWindow != null ? (int) objPparentWindow.getLocation().getY() : (int) this.getLocation().getY();
		final int intLparentFrameWidth = objPparentWindow != null	? (int) objPparentWindow.getSize().getWidth()
																	: this.getWidth() + this.getJuggleMasterPro().getFrame().getWidth();
		final int intLparentFrameHeight = objPparentWindow != null ? objPparentWindow.getHeight() : this.getHeight();

		objPwindow.setBounds(	Math.max(Constants.intS_GRAPHICS_SCREEN_X,
										Math.min(intLparentFrameX	+ (intLparentFrameWidth - intLwidth) / 2,
													(bolPrightJustified	? intLparentFrameX + intLparentFrameWidth
																		: Constants.intS_GRAPHICS_SCREEN_X + Constants.intS_GRAPHICS_SCREEN_WIDTH)
																								- intLwidth)),
								Math.max(	Constants.intS_GRAPHICS_SCREEN_Y,
											Math.min(intLparentFrameY	+ (intLparentFrameHeight - intLheight) / 2,
														Constants.intS_GRAPHICS_SCREEN_HEIGHT - intLheight)),
								intLwidth,
								intLheight);
	}

	final public void setWindowBounds(Window objPwindow, Window objPparentWindow) {
		this.setWindowBounds(objPwindow, objPparentWindow, false);
	}

	final public void setWindowBounds(Window objPwindow, Window objPparentWindow, boolean bolPrightJustified) {
		this.setWindowBounds(objPwindow, objPwindow.getWidth(), objPwindow.getHeight(), objPparentWindow, bolPrightJustified);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPthread
	 * @param objPthrowable
	 */
	@Override final public void uncaughtException(Thread objPthread, Throwable objPthrowable) {

		if (this.bolGloadingPatternsFile) {

			// Exception while loading a pattern file :
			// /////////////////////////////////////////
			this.bolGloadingPatternsFile = false;
			this.setJButtonsEnabled(true);
			this.setMenusEnabled();
			this.setMouseCursorEnabled(true);
			if (Preferences.readLocalBooleanPreference(Constants.bytS_BOOLEAN_LOCAL_ERRORS)) {
				new PopUpJDialog(	this,
									Constants.bytS_UNCLASS_NO_VALUE,
									Constants.intS_FILE_ICON_ERROR,
									this.getLanguageString(Language.intS_TITLE_MEMORY_OVERFLOW),
									this.getLanguageString(Language.intS_MESSAGE_MEMORY_OVERFLOW_WHILE_LOADING_PATTERNS_FILES),
									null,
									false);
			}
		} else {

			// Other exception :
			// //////////////////
			Tools.err(Strings.doConcat("Exception in ", objPthread.getName(), " occured:"));
			if (Constants.bolS_UNCLASS_CONSOLE && Constants.bolS_UNCLASS_DEBUG) {
				objPthrowable.printStackTrace();
			}
		}
	}

	public boolean							bolGdefaultsFxPause;
	private boolean							bolGexportCurrentPatterns;
	private final boolean					bolGexportInitialPatterns;
	private boolean							bolGloadingPatternsFile;
	private int								intGfilteredPatternsNumber;
	private int								intGfilteredShortcutsNumber;
	private int								intGlanguageIndex;
	private final int						intGlanguagesNumber;
	public AboutJMenuItem					objGaboutJMenuItem;
	public AlternateColorsJCheckBox			objGalternateColorsJCheckBox;
	public AlternateColorsJScrollBar		objGalternateColorsJScrollBar;
	private ExtendedJLabel					objGalternateColorsValueJLabel;
	private ExtendedJLabel					objGampersandJLabel;
	public ColorChooserDropDownJButton		objGbackgroundColorChooserDropDownJButton;
	private ExtendedJLabel					objGbackgroundColorJLabel;
	public ColorChooserDropDownJButton		objGballColorChooserDropDownJButton;
	private ExtendedJLabel					objGballsColonsJLabel;
	public BallsJScrollBar					objGballsJScrollBar;
	public BallsNumberJCheckBox				objGballsNumberJCheckBox;
	private FilterJLabel					objGballsNumberJLabel;
	public BallsThreeStatesJCheckBox		objGballsThreeStatesJCheckBox;
	private ExtendedJLabel					objGballsValueJLabel;
	public CatchSoundsJCheckBox				objGcatchSoundsJCheckBox;
	public ExtendedJMenu					objGclipboardExtendedJMenu;
	public ClipboardJCheckBoxMenuItem		objGclipboardJCheckBoxMenuItem;
	public ClipboardJDialog					objGclipboardJDialog;
	private ExtendedJLabel					objGcolorsColonsJLabel;
	public BallsColorsJTextPane				objGcolorsJTextPane;
	public BallsColorsThreeStatesJCheckBox	objGcolorsThreeStatesJCheckBox;
	private ExtendedJLabel					objGcolorsValueJLabel;
	public ConsoleJCheckBoxMenuItem			objGconsoleJCheckBoxMenuItem;
	public ConsoleJDialog					objGconsoleJDialog;
	private ControlJFrameListener			objGcontrolJFrameListener;
	public DataJCheckBoxMenuItem			objGdataJCheckBoxMenuItem;
	private ExtendedJLabel					objGdefaultsColonsJLabel;
	public DefaultsJScrollBar				objGdefaultsJScrollBar;
	public DefaultsThreeStatesJCheckBox		objGdefaultsThreeStatesJCheckBox;

	private ExtendedJLabel					objGdefaultsValueJLabel;

	public ExtendedJMenuItem				objGdetailedCopyJuggleJMenuItem;

	public DevelopmentJMenuItem				objGdevelopmentJMenuItem;

	private ExtendedJLabel					objGdwellColonsJLabel;

	private ExtendedJLabel					objGdwellJLabel;

	public DwellJScrollBar					objGdwellJScrollBar;

	private ExtendedJLabel					objGdwellValueJLabel;

	public ExtendedJMenu					objGeditionExtendedJMenu;

	public WriteFileJMenuItem				objGexportCurrentLanguageJMenuItem;

	private ExtendedJMenu					objGexportExtendedJMenu;

	public WriteFileJMenuItem				objGexportPatternsFileJMenuItem;

	public WriteFileJMenuItem				objGexportSiteswapsFileJMenuItem;

	private WriteFileJMenuItem				objGexportStylesFileJMenuItem;

	private ExtendedJMenu					objGfileExtendedJMenu;

	public FiltersJButton					objGfiltersJButton;

	public FiltersJDialog					objGfiltersJDialog;

	public FiltersJMenuItem					objGfiltersJMenuItem;

	public FindJDialog						objGfindJDialog;

	public FindJMenuItem					objGfindJMenuItem;

	public FindJMenuItem					objGfindNextJMenuItem;

	public FindJMenuItem					objGfindPreviousJMenuItem;

	private ExtendedJLabel					objGfluidityColonsJLabel;

	private ExtendedJLabel					objGfluidityJLabel;

	public FluidityJScrollBar				objGfluidityJScrollBar;

	private ExtendedJLabel					objGfluidityValueJLabel;

	public ExtendedJMenuItem				objGfreeClipboardJuggleJMenuItem;

	private FilterJLabel					objGfromBallsNumberJLabel;

	public FilterJLabel						objGfromMarkJLabel;

	public FilterJLabel						objGfromSkillJLabel;

	public FXJCheckBox						objGfXJCheckBox;

	private MarkJButton						objGglobalMarkJButton;

	private ExtendedJLabel					objGheightColonsJLabel;

	private ExtendedJLabel					objGheightJLabel;

	public HeightJScrollBar					objGheightJScrollBar;

	private ExtendedJLabel					objGheightValueJLabel;

	private ExtendedJMenu					objGhelpExtendedJMenu;

	public ExtendedJMenuItem				objGhelpJMenuItem;

	public BallsNumberJComboBox				objGhighBallsNumberJComboBox;

	public MarkJComboBox					objGhighMarkJComboBox;

	public SkillJComboBox					objGhighSkillJComboBox;

	private ExtendedJMenu					objGimportExtendedJMenu;

	public ImportPatternsJButton			objGimportPatternsJButton;

	public LoadFileJMenuItem				objGimportPatternsJMenuItem;

	public ImportSiteswapsJButton			objGimportSiteswapsJButton;

	public LoadFileJMenuItem				objGimportSiteswapsJMenuItem;

	public ImportStylesJButton				objGimportStylesJButton;

	public LoadFileJMenuItem				objGimportStylesJMenuItem;

	public InvalidPatternsJCheckBox			objGinvalidPatternsJCheckBox;

	public InvalidPatternsJLabel			objGinvalidPatternsJLabel;

	private JMenuBar						objGjMenuBar;

	private final JuggleMasterPro			objGjuggleMasterPro;

	private Font							objGjuggleMasterProFont;

	public ColorChooserDropDownJButton		objGjugglerColorChooserDropDownJButton;

	public ExtendedJLabel					objGjugglerColorJLabel;

	public JugglerJScrollBar				objGjugglerJScrollBar;

	public JugglerThreeStatesJCheckBox		objGjugglerThreeStatesJCheckBox;

	private ExtendedJLabel					objGjugglerValueJLabel;

	public Language[]						objGlanguageA;

	private ExtendedJMenu					objGlanguageExtendedJMenu;

	// private PanelJSplitPane objGjSplitPane;
	public LanguageJRadioButtonMenuItem[]	objGlanguageJRadioButtonMenuItemA;

	public LicenceJMenuItem					objGlicenceJMenuItem;

	public LightJCheckBox					objGlightJCheckBox;

	private MarkJButton						objGlocalMarkJButton;

	public BallsNumberJComboBox				objGlowBallsNumberJComboBox;

	public MarkJComboBox					objGlowMarkJComboBox;

	public SkillJComboBox					objGlowSkillJComboBox;

	public MarkJCheckBox					objGmarkJCheckBox;

	public MarkJComboBox					objGmarkJComboBox;

	public MetronomeJCheckBox				objGmetronomeJCheckBox;

	public MirrorJCheckBox					objGmirrorJCheckBox;

	public WriteFileJMenuItem				objGnewLanguageJMenuItem;

	public NewPatternJMenuItem				objGnewPatternJMenuItem;

	public WriteFileJMenuItem				objGnewPatternsFileJMenuItem;

	public ObjectsJList						objGobjectsJList;

	public JScrollPane						objGobjectsJListJScrollPane;

	public LoadFileJMenuItem				objGopenFileJMenuItem;

	private ExtendedJLabel					objGpatternColonsJLabel;

	private ExtendedJLabel					objGpatternJLabel;

	public PatternJTextField				objGpatternJTextField;

	public ExtendedJMenu					objGpatternsExtendedJMenu;

	public PlayPauseJButton					objGplayPauseJButton;

	public PreferencesJDialog				objGpreferencesJDialog;

	public PreferencesJMenuItem				objGpreferencesJMenuItem;


	public QuitJMenuItem					objGquitJMenuItem;

	public RecentFilesExtendedJMenu			objGrecentFilesJuggleJMenu;

	public ExtendedJMenu					objGrecordsLinksExtendedJMenu;

	public RefreshSiteswapJButton			objGrefreshSiteswapJButton;

	public ReloadPatternsFilesJMenuItem		objGreloadFileJMenuItem;

	public ReloadPatternJButton				objGreloadPatternJButton;

	public ExtendedJMenuItem				objGreloadPatternJuggleJMenuItem;

	public ReversePatternJButton			objGreversePatternJButton;

	public ReverseSiteswapJCheckBox			objGreverseSiteswapJCheckBox;

	public ReverseStyleJCheckBox			objGreverseStyleJCheckBox;

	public ExtendedJMenu					objGscreenPlayExtendedJMenu;

	// private JuggleLanguage objGdefaultJuggleLanguage;

	public ScreenShotJMenuItem				objGscreenShotJMenuItem;

	public ShortcutsJComboBox				objGshortcutsJComboBox;

	public ExtendedJMenuItem				objGsimpleCopyJuggleJMenuItem;

	private ExtendedJLabel					objGsiteswapColonsJLabel;

	public ColorChooserDropDownJButton		objGsiteswapColorChooserDropDownJButton;

	private ExtendedJLabel					objGsiteswapColorJLabel;

	public SiteswapJTextPane				objGsiteswapJTextPane;

	public ExtendedJMenuItem				objGsiteswapJuggleJMenuItem;

	public SiteswapThreeStatesJCheckBox		objGsiteswapThreeStatesJCheckBox;

	public SkillJCheckBox					objGskillJCheckBox;

	public SkillJComboBox					objGskillJComboBox;

	public SortStylesJButton				objGsortStylesJButton;

	private ExtendedJLabel					objGsoundsColonsJLabel;

	public SoundsJCheckBox					objGsoundsJCheckBox;

	private ExtendedJLabel					objGspeedColonsJLabel;

	private ExtendedJLabel					objGspeedJLabel;

	public SpeedJScrollBar					objGspeedJScrollBar;

	private ExtendedJLabel					objGspeedValueJLabel;

	public SplashScreenJWindow				objGsplashScreenJWindow;

	private JPanel							objGstartJPanel;

	public ScreenPlayJMenuItem				objGstartScreenPlayJMenuItem;

	public ScreenPlayJMenuItem				objGstopScreenPlayJMenuItem;

	public StrobeJScrollBar					objGstrobeJScrollBar;

	public StrobeThreeStatesJCheckBox		objGstrobeThreeStatesJCheckBox;

	private ExtendedJLabel					objGstrobeValueJLabel;

	private ExtendedJLabel					objGstyleColonsJLabel;

	public StyleJCheckBox					objGstyleJCheckBox;

	public StyleJComboBox					objGstyleJComboBox;

	public ThrowSoundsJCheckBox				objGthrowSoundsJCheckBox;

	private FilterJLabel					objGtoBallsNumberJLabel;

	public FilterJLabel						objGtoMarkJLabel;

	private JLayeredPane					objGtopJLayeredPane;

	public FilterJLabel						objGtoSkillJLabel;

	public ExtendedJMenu					objGvideoLinksExtendedJMenu;

	public ExtendedJMenu					objGwebLinksExtendedJMenu;

	// File types :
	final private static long				serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)JugglePanel.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
