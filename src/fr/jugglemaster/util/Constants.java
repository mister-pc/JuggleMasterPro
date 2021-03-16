/*
 * @(#)Constants.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import fr.jugglemaster.control.util.DefaultRenderer;
import fr.jugglemaster.pattern.BallsColors;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
public class Constants {

	final private static void setBooleanGlobalControls() {

		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_TOOLTIPS] = "tooltips              ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS] = "menusTooltips         ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS] = "buttonsToolTips       ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS] = "fieldsToolTips        ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_NEW_FILE_INFORMATION] = "newFileInformation    ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_NAVIGATOR_CONFIGURATION] = "navigatorConfiguration";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_FILE_ACCESS_ERRORS] = "fileAccessErrors      ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_SCREEN_PLAY_INFORMATION] = "screenPlayInformation ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_FILE_EXPORT_INFORMATION] = "fileExportInformation ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_CLIPBOARD_INFORMATION] = "clipboardInformation  ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_BALLS_NUMBER] = "ballsNumber           ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_SKILL] = "skill                 ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_MARK] = "mark                  ";
		Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[Constants.bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS] = "invalidPatterns       ";
	}

	final private static void setBooleanLocalControls() {

		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][0] = "siteswapThrows        ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS][0] = "currentThrows         ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_CURRENT_THROWS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SOUNDS][0] = "sounds                ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SOUNDS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS][0] = "catchSounds           ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_CATCH_SOUNDS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS][0] = "throwSounds           ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_THROW_SOUNDS][1] = "BP";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_METRONOME][0] = "metronome             ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_METRONOME][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][0] = "siteswap              ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SITESWAP][1] = "PD";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP][0] = "reverseSiteswap       ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE][0] = "reverseStyle          ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS][0] = "alternateColors       ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][0] = "randomDefaults        ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_STYLE][0] = "style                 ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_STYLE][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_MIRROR][0] = "mirror                ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_MIRROR][1] = "MR";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS][0] = "randomColors          ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_RANDOM_COLORS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_LIGHT][0] = "light                 ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_LIGHT][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][0] = "juggler               ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_JUGGLER][1] = "HD";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL][0] = "jugglerTrail          ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL][1] = "trail                 ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_FX][0] = "fX                    ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_FX][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_FLASH][0] = "flash                 ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_FLASH][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_ROBOT][0] = "robot                 ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_ROBOT][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_ERRORS][0] = "errors                ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_ERRORS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_WARNINGS][0] = "warnings              ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_WARNINGS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_INFO][0] = "info                  ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_INFO][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][0] = "defaults              ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_DEFAULTS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_COLORS][0] = "colors                ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_COLORS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_BALLS][0] = "balls                 ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_BALLS][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_EDITION][0] = "edition               ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_EDITION][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SPEED][0] = "speed                 ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_SPEED][1] = null;
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_FLUIDITY][0] = "fluidity              ";
		Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[Constants.bytS_BOOLEAN_LOCAL_FLUIDITY][1] = null;
	}

	final private static void setByteLocalControls() {
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_SPEED][0] = "speed                 ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_SPEED][1] = "SP";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED][0] = "relativeSpeed         ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_RELATIVE_SPEED][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS][0] = "alternateColors       ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_ALTERNATE_COLORS][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_FLUIDITY][0] = "fluidity              ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_FLUIDITY][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT][0] = "nightJuggler          ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY][0] = "dayJuggler            ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_JUGGLER_BODY_DAY][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_HEIGHT][0] = "height                ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_HEIGHT][1] = "HR";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_STROBE][0] = "strobe                ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_STROBE][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_SKILL][0] = "skill                 ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_SKILL][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_MARK][0] = "mark                  ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_SKILL][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_DWELL][0] = "dwell                 ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_DWELL][1] = "DR";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_DEFAULTS][0] = "defaults              ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_DEFAULTS][1] = null;
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL][0] = "ballsTrail            ";
		Constants.strS_BYTE_LOCAL_CONTROL_A_A[Constants.bytS_BYTE_LOCAL_BALLS_TRAIL][1] = null;
	}

	final private static void setbytGlobalControls() {
		Constants.strS_BYTE_GLOBAL_CONTROL_A[Constants.bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER] = "lowBallsNumber        ";
		Constants.strS_BYTE_GLOBAL_CONTROL_A[Constants.bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER] = "highBallsNumber       ";
		Constants.strS_BYTE_GLOBAL_CONTROL_A[Constants.bytS_BYTE_GLOBAL_LOW_SKILL] = "lowSkill              ";
		Constants.strS_BYTE_GLOBAL_CONTROL_A[Constants.bytS_BYTE_GLOBAL_HIGH_SKILL] = "highSkill             ";
		Constants.strS_BYTE_GLOBAL_CONTROL_A[Constants.bytS_BYTE_GLOBAL_LOW_MARK] = "lowMark               ";
		Constants.strS_BYTE_GLOBAL_CONTROL_A[Constants.bytS_BYTE_GLOBAL_HIGH_MARK] = "highMark              ";
		Constants.strS_BYTE_GLOBAL_CONTROL_A[Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION] = "gammaCorrection       ";
	}

	final private static void setColors() {
		Color objLforegroundColor = (Color) UIManager.get("Label.disabledForeground");
		if (objLforegroundColor == null) {
			objLforegroundColor = Color.LIGHT_GRAY;
		}
		Constants.strS_COLORS_PEN_COLOR_A[Constants.bytS_ENGINE_LIGHT] = Tools.getPenColorString(objLforegroundColor, true, true);
		objLforegroundColor = (Color) UIManager.get("Label.enabledForeground");
		if (objLforegroundColor == null) {
			objLforegroundColor = Color.BLACK;
		}
		Constants.strS_COLORS_PEN_COLOR_A[Constants.bytS_ENGINE_DARK] = Tools.getPenColorString(objLforegroundColor, true, true);
	}

	final private static void setCommandLineParameters() {
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_FILE] = "    file";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_PATTERN] = "         pattern";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_OCCURRENCE] = "                 occurrence";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_LANGUAGE] = "    lang";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_EMBEDDED] = "embedded";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_CONSOLE] = " console";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_LOG] = "     log";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_ERRORS] = "         errors";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_DEBUG] = "          debug";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_LONG] = "    help";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_SHORT] = "h";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_HELP_QUESTION_MARK] = "?";
		Constants.strS_COMMAND_LINE_PARAMETER_A[Constants.bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS] = "catchAllExceptions";
	}

	final private static void setExtensions() {
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM] = "jm";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JMP] = "jmp";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JAP] = "jap";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_INI] = "ini";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_LANG_INI] = "lang.ini";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_PNG] = "png";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPG] = "jpg";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPEG] = "jpeg";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_GIF] = "gif";
		Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_BMP] = "bmp";
	}

	final private static void setFilesNames() {

		Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_DATA] = "data";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES] = "img";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_LIBRARY] = "lib";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_SOUNDS] = "snd";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_ABOUT] = "about.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_ALERT] = "alert.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_APPLY] = "apply.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_APPLY_BW] = "applyBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CANCEL] = "cancel.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CANCEL_BW] = "cancelBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CANCEL_FIND] = "cancelFind.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CANCEL_FIND_BW] = "cancelFindBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLEAR_FIELD] = "clearField.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLEAR_FIELD_BW] = "clearFieldBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLEAR_FILTERS] = "clearFilters.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLEAR_FILTERS_BW] = "clearFiltersBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLIPBOARD] = "clipboard.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_CLIPBOARD] = "closeClipboard.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_CLIPBOARD_BW] = "closeClipboardBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_CONSOLE] = "closeConsole.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_CONSOLE_BW] = "closeConsoleBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_DATA] = "closeData.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_DATA_BW] = "closeDataBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_DEVELOPMENT] = "closeDevelopment.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_DEVELOPMENT_BW] = "closeDevelopmentBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_FILTERS] = "closeFilters.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_FILTERS_BW] = "closeFiltersBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_FIND] = "closeFind.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CLOSE_FIND_BW] = "closeFindBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_COLORS] = "colors.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CONFIGURATION] = "configurationFile.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CONSOLE] = "console.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CONTENT_ADJUSTMENT] = "contentAdjustment.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_CONTENT_ADJUSTMENT_BW] = "contentAdjustmentBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_COPY_CLIPBOARD] = "copyClipboard.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_COPY_CLIPBOARD_BW] = "copyClipboardBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_COPY_CONSOLE] = "copyConsole.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_COPY_CONSOLE_BW] = "copyConsoleBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DATA] = "data.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DEFAULT_PATTERNS] = "defaultPatterns.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DETAILED_COPY] = "detailedCopy.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DETAILED_COPY_BW] = "detailedCopyBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DEVELOPMENT] = "development.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DISPLAY_CLIPBOARD] = "displayClipboard.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_FILTER] = "doNotFilter.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_FILTER_BW] = "doNotFilterBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_IMPORT_PATTERNS] = "doNotImportPatterns.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_IMPORT_SITESWAPS] = "doNotImportSiteswaps.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_IMPORT_STYLES] = "doNotImportStyles.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_LOAD_PATTERNS] = "doNotLoadPatterns.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_REFRESH_SITESWAP] = "doNotRefreshSiteswap.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_RELOAD_PATTERN] = "doNotReloadPattern.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_REVERSE_PATTERN] = "doNotReversePattern.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_REVERSE_SORT_STYLES] = "doNotReverseSortStyles.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DO_NOT_SORT_STYLES] = "doNotSortStyles.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DROP_DOWN_B] = "dropDownB.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DROP_DOWN_W] = "dropDownW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DROP_UP_B] = "dropUpB.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DROP_UP_W] = "dropUpW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_DUPLEX] = "duplex.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_ERROR] = "error.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_EXPORT] = "export.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_EXPORT_CURRENT_LANGUAGE] = "exportLanguage.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_EXPORT_PATTERNS] = "exportPatterns.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_EXPORT_SITESWAPS] = "exportSiteswaps.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_EXPORT_STYLES] = "exportStyles.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FILTER] = "filter.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FILTER_BW] = "filterBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FIND] = "find.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FIND_NEXT] = "findNext.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FIND_NEXT_BW] = "findNextBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FIND_PREVIOUS] = "findPrevious.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FIND_PREVIOUS_BW] = "findPreviousBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FORWARD_PATTERN] = "forwardPattern.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FORWARD_PATTERN_BW] = "forwardPatternBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FRAME] = "frame.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FREE_CLIPBOARD] = "freeClipboard.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FREE_CLIPBOARD_BW] = "freeClipboardBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FREE_CONSOLE] = "freeConsole.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_FREE_CONSOLE_BW] = "freeConsoleBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_HELP] = "help.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMAGE] = "imageFile.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMPORT] = "import.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMPORT_PATTERNS] = "importPatterns.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMPORT_PATTERNS_BW] = "importPatternsBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMPORT_SITESWAPS] = "importSiteswaps.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMPORT_SITESWAPS_BW] = "importSiteswapsBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMPORT_STYLES] = "importStyles.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_IMPORT_STYLES_BW] = "importStylesBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_INFORMATION] = "information.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JMP] = "jmp.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JAP] = "jap.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JMP_HEADER] = "patternsHeader.jmp";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JAP_HEADER] = "patternsHeader.jap";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JM] = "jm.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_JM_HEADER] = "patternsHeader.jm";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_LANDSCAPE] = "landscape.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_LANGUAGES] = "languages.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_LANGUAGE] = "lang.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_LICENCE] = "licence.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_LINKS] = "links.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_LOAD_PATTERNS] = "loadPatterns.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_MARK] = "mark.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_MARK_BW] = "markBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_NEW_DEFAULT_LANGUAGE] = "newDefaultLanguage.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_NEW_DEFAULT_LANGUAGE_BW] = "newDefaultLanguageBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_NEW_EMPTY_LANGUAGE] = "newEmptyLanguage.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_NEW_EMPTY_LANGUAGE_BW] = "newEmptyLanguageBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_NEW_LANGUAGE] = "newLanguage.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_NEW_PATTERN] = "newPattern.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_OK] = "ok.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_OK_BW] = "okBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_ONE_SIDE] = "oneSide.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PANEL] = "panel.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_NEW_PATTERNS_FILE] = "patternsFileModel.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PAUSE] = "pause.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PAUSE_BW] = "pauseBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PLAY] = "play.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PLAY_BW] = "playBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PORTRAIT] = "portrait.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PREFERENCES] = "preferences.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_PRINT] = "print.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_QUESTION] = "question.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_QUIT] = "quit.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RECENT] = "recentPatterns.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RECORDS] = "records.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REFRESH_SITESWAP] = "refreshSiteswap.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REFRESH_SITESWAP_BW] = "refreshSiteswapBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RELOAD] = "reload.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RELOAD_PATTERN] = "reloadPattern.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RELOAD_PATTERN_BW] = "reloadPatternBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RELOAD_PATTERN_YELLOW] = "reloadPatternYellow.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REPLAY] = "replay.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REPLAY_BW] = "replayBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RESET] = "default.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RESET_BW] = "defaultBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RESTORE] = "restore.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_RESTORE_BW] = "restoreBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_LANDSCAPE] = "reverseLandscape.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_PATTERN] = "reversePattern.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_PATTERN_BW] = "reversePatternBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_PORTRAIT] = "reversePortrait.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_SORT_STYLES] = "reverseSortStyles.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_REVERSE_SORT_STYLES_BW] = "reverseSortStylesBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SCREEN_PLAY] = "screenPlay.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SCREEN_SHOT] = "screenShot.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SIMPLE_COPY] = "simpleCopy.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SIMPLE_COPY_BW] = "simpleCopyBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SITESWAP_THEORY] = "siteswapTheory.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SORT_STYLES] = "sortStyles.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SORT_STYLES_BW] = "sortStylesBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_SOUNDS] = "sounds.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_START_SCREEN_PLAY] = "startScreenPlay.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_STOP] = "stop.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_STOP_BW] = "stopBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_STOP_SCREEN_PLAY] = "stopScreenPlay.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_TUMBLE] = "tumble.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_VALUES] = "values.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_VIDEO] = "video.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_WINDOW_ADJUSTMENT] = "windowAdjustment.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_ICON_WINDOW_ADJUSTMENT_BW] = "windowAdjustmentBW.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_IMAGE_ABOUT] = "about.jpg";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_IMAGE_ARROWS] = "arrows.png";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LOG] = "JuggleMasterPro.log";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_DEVELOPMENT] = "development.cfg";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_FONT] = "JuggleMasterPro.ttf";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_HELP] = "help.html";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LANGUAGES] = "langs.cfg";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LANGUAGE] = "lang.ini";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_LINKS] = "linksMenu.cfg";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_PATTERNS] = "patternsMenu.cfg";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_RECORDS] = "recordsMenu.cfg";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_SITESWAP_THEORY] = "siteswap.html";
		Constants.strS_FILE_NAME_A[Constants.intS_FILE_TEXT_VIDEO] = "videoMenu.cfg";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_CLOSE] = "close.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_LEFT_CATCH] = "catch.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_LEFT_THROW] = "throw.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_METRONOME] = "metronome.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_OPEN] = "open.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_RIGHT_CATCH] = "catch.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_RIGHT_THROW] = "throw.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_SCREEN_PLAY] = "screenPlay.wav";
		Constants.strS_FILE_SOUND_NAME_A[Constants.bytS_FILE_SOUND_SCREEN_SHOT] = "screenShot.wav";
	}

	final private static void setHashmap() {
		for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
			for (byte bytLbyteControlStringIndex = 0; bytLbyteControlStringIndex < 2; ++bytLbyteControlStringIndex) {
				if (Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLbyteControlIndex][bytLbyteControlStringIndex] != null) {
					Constants.objS_ENGINE_PARAMETERS_HASH_MAP.put(	Strings	.getRightTrimmedString(Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLbyteControlIndex][bytLbyteControlStringIndex])
																			.toLowerCase(),
																	Strings.doConcat('i', bytLbyteControlIndex));
				}
			}
		}

		for (byte bytLbyteControlIndex = 0; bytLbyteControlIndex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLbyteControlIndex) {
			for (byte bytLbooleanControlStringIndex = 0; bytLbooleanControlStringIndex < 2; ++bytLbooleanControlStringIndex) {
				if (Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLbyteControlIndex][bytLbooleanControlStringIndex] != null) {
					Constants.objS_ENGINE_PARAMETERS_HASH_MAP.put(	Strings	.getRightTrimmedString(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLbyteControlIndex][bytLbooleanControlStringIndex])
																			.toLowerCase(),
																	Strings.doConcat('b', bytLbyteControlIndex));
				}
			}
		}

		for (byte bytLstringControlIndex = 0; bytLstringControlIndex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLstringControlIndex) {
			if (Constants.strS_STRING_LOCAL_CONTROL_A[bytLstringControlIndex] != null) {
				Constants.objS_ENGINE_PARAMETERS_HASH_MAP.put(	Strings	.getRightTrimmedString(Constants.strS_STRING_LOCAL_CONTROL_A[bytLstringControlIndex])
																		.toLowerCase(),
																Strings.doConcat('s', bytLstringControlIndex));
			}
		}

		for (byte bytLotherControlIndex = 0; bytLotherControlIndex < Constants.bytS_OTHER_LOCAL_CONTROLS_NUMBER; ++bytLotherControlIndex) {
			for (byte bytLotherControlStringIndex = 0; bytLotherControlStringIndex < 2; ++bytLotherControlStringIndex) {
				if (Constants.strS_OTHER_LOCAL_CONTROL_A_A[bytLotherControlIndex][bytLotherControlStringIndex] != null) {
					Constants.objS_ENGINE_PARAMETERS_HASH_MAP.put(	Strings	.getRightTrimmedString(Constants.strS_OTHER_LOCAL_CONTROL_A_A[bytLotherControlIndex][bytLotherControlStringIndex])
																			.toLowerCase(),
																	Strings.doConcat('o', bytLotherControlIndex));
				}
			}
		}
	}

	final private static void setOtherLocalControls() {
		Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_GRAVITY][0] = "GA";
		Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_GRAVITY][1] = null;
		Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_START_PATTERN][0] = "startPattern          ";
		Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_START_PATTERN][1] = null;
		Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_BACKGROUND][0] = "background            ";
		Constants.strS_OTHER_LOCAL_CONTROL_A_A[Constants.bytS_OTHER_LOCAL_BACKGROUND][1] = "BC";
	}

	final private static void setPreferences() {

		// Boolean preferences :
		for (byte bytLindex = 0; bytLindex < Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER; ++bytLindex) {
			Constants.strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A[bytLindex] = Strings.doConcat(	Constants.strS_BOOLEAN_GLOBAL,
																								Strings.getRightTrimmedString(Constants.strS_BOOLEAN_GLOBAL_CONTROL_A[bytLindex]));
		}
		for (byte bytLindex = 0; bytLindex < Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER; ++bytLindex) {
			Constants.strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A[bytLindex] = Strings.doConcat(Constants.strS_BOOLEAN_LOCAL,
																							Strings.getRightTrimmedString(Constants.strS_BOOLEAN_LOCAL_CONTROL_A_A[bytLindex][0]));
		}

		// Byte preferences :
		for (byte bytLindex = 0; bytLindex < Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER; ++bytLindex) {
			Constants.strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A[bytLindex] = Strings.doConcat(	Constants.strS_BYTE_GLOBAL,
																							Strings.getRightTrimmedString(Constants.strS_BYTE_GLOBAL_CONTROL_A[bytLindex]));
		}
		for (byte bytLindex = 0; bytLindex < Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER; ++bytLindex) {
			Constants.strS_BYTE_LOCAL_CONTROL_PREFERENCE_A[bytLindex] = Strings.doConcat(	Constants.strS_BYTE_LOCAL,
																							Strings.getRightTrimmedString(Constants.strS_BYTE_LOCAL_CONTROL_A_A[bytLindex][0]));
		}

		// String preferences :
		for (byte bytLindex = 0; bytLindex < Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER; ++bytLindex) {
			Constants.strS_STRING_GLOBAL_CONTROL_PREFERENCE_A[bytLindex] = Strings.doConcat(Constants.strS_STRING_GLOBAL,
																							Strings.getRightTrimmedString(Constants.strS_STRING_GLOBAL_CONTROL_A[bytLindex]));
		}
		for (byte bytLindex = 0; bytLindex < Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER; ++bytLindex) {
			Constants.strS_STRING_LOCAL_CONTROL_PREFERENCE_A[bytLindex] = Strings.doConcat(	Constants.strS_STRING_LOCAL,
																							Strings.getRightTrimmedString(Constants.strS_STRING_LOCAL_CONTROL_A[bytLindex]));
		}
	}

	final private static void setStringGlobalControls() {
		Constants.strS_STRING_GLOBAL_CONTROL_A[Constants.bytS_STRING_GLOBAL_LANGUAGE] = "language              ";
	}

	final private static void setStringLocalControls() {
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_PATTERN] = "pattern               ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_STYLE] = "style                 ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_TOOLTIP] = "tooltip               ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_SITESWAP] = "siteswap              ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP] = "reverseSiteswap       ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_COLORS] = "colors                ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_REVERSE_COLORS] = "reverseColors         ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_SITESWAP_DAY] = "daySiteswapColor      ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_SITESWAP_NIGHT] = "nightSiteswapColor    ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_BACKGROUND_DAY] = "dayBackground         ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT] = "nightBackground       ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_JUGGLER_DAY] = "dayJugglerColor       ";
		Constants.strS_STRING_LOCAL_CONTROL_A[Constants.bytS_STRING_LOCAL_JUGGLER_NIGHT] = "nightJugglerColor     ";
	}

	public static boolean									bolS_UNCLASS_CATCH_ALL_EXCEPTIONS					= true;

	public static boolean									bolS_UNCLASS_CONSOLE								= false;
	public static boolean									bolS_UNCLASS_DEBUG									= false;
	public static boolean									bolS_UNCLASS_EMBEDDED								= false;
	public static boolean									bolS_UNCLASS_ERRORS									= false;

	public static boolean									bolS_UNCLASS_LOG									= true;
	final public static byte								bytS_BIT_0											= 1;

	final public static byte								bytS_BIT_1											= 2;
	final public static byte								bytS_BIT_2											= 4;
	final public static byte								bytS_BIT_3											= 8;
	final public static byte								bytS_BIT_VISIBILITY_ARMS							= 4;
	final public static byte								bytS_BIT_VISIBILITY_BALLS							= 1;
	final public static byte								bytS_BIT_VISIBILITY_HANDS							= 2;
	final public static byte								bytS_BIT_VISIBILITY_HEAD							= 8;
	final public static byte								bytS_BIT_VISIBILITY_NONE							= 0;
	final public static byte								bytS_BIT_VISIBILITY_PHANTOM							= 1;
	final public static byte								bytS_BIT_VISIBILITY_TRAIL							= 16;
	final public static byte								bytS_BOOLEAN_GLOBAL_BALLS_NUMBER					= 8;
	final public static byte								bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS				= 2;

	final public static byte								bytS_BOOLEAN_GLOBAL_CLIPBOARD_INFORMATION			= 12;
	final public static byte								bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER;
	final public static byte								bytS_BOOLEAN_GLOBAL_FIELDS_TOOLTIPS					= 3;
	final public static byte								bytS_BOOLEAN_GLOBAL_FILE_ACCESS_ERRORS				= 6;
	final public static byte								bytS_BOOLEAN_GLOBAL_FILE_EXPORT_INFORMATION			= 5;
	final public static byte								bytS_BOOLEAN_GLOBAL_INVALID_PATTERNS				= 11;
	final public static byte								bytS_BOOLEAN_GLOBAL_MARK							= 10;
	final public static byte								bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS					= 1;
	final public static byte								bytS_BOOLEAN_GLOBAL_NAVIGATOR_CONFIGURATION			= 13;
	final public static byte								bytS_BOOLEAN_GLOBAL_NEW_FILE_INFORMATION			= 4;
	final public static byte								bytS_BOOLEAN_GLOBAL_SCREEN_PLAY_INFORMATION			= 7;
	final public static byte								bytS_BOOLEAN_GLOBAL_SKILL							= 9;
	final public static byte								bytS_BOOLEAN_GLOBAL_TOOLTIPS						= 0;
	final public static byte								bytS_BOOLEAN_LOCAL_ALTERNATE_COLORS					= 14;
	final public static byte								bytS_BOOLEAN_LOCAL_BALLS							= 8;

	final public static byte								bytS_BOOLEAN_LOCAL_CATCH_SOUNDS						= 20;
	final public static byte								bytS_BOOLEAN_LOCAL_COLORS							= 12;
	final public static byte								bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER;
	final public static byte								bytS_BOOLEAN_LOCAL_CURRENT_THROWS					= 9;
	final public static byte								bytS_BOOLEAN_LOCAL_DEFAULTS							= 15;
	final public static byte								bytS_BOOLEAN_LOCAL_EDITION							= 0;
	final public static byte								bytS_BOOLEAN_LOCAL_ERRORS							= 28;
	final public static byte								bytS_BOOLEAN_LOCAL_FLASH							= 17;
	final public static byte								bytS_BOOLEAN_LOCAL_FLUIDITY							= 25;
	final public static byte								bytS_BOOLEAN_LOCAL_FX								= 6;
	final public static byte								bytS_BOOLEAN_LOCAL_INFO								= 26;
	final public static byte								bytS_BOOLEAN_LOCAL_JUGGLER							= 10;
	final public static byte								bytS_BOOLEAN_LOCAL_JUGGLER_TRAIL					= 11;
	final public static byte								bytS_BOOLEAN_LOCAL_LIGHT							= 23;
	final public static byte								bytS_BOOLEAN_LOCAL_METRONOME						= 22;
	final public static byte								bytS_BOOLEAN_LOCAL_MIRROR							= 1;
	final public static byte								bytS_BOOLEAN_LOCAL_RANDOM_COLORS					= 13;
	final public static byte								bytS_BOOLEAN_LOCAL_RANDOM_DEFAULTS					= 16;
	final public static byte								bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP					= 4;
	final public static byte								bytS_BOOLEAN_LOCAL_REVERSE_STYLE					= 7;
	final public static byte								bytS_BOOLEAN_LOCAL_ROBOT							= 18;
	final public static byte								bytS_BOOLEAN_LOCAL_SITESWAP							= 2;
	final public static byte								bytS_BOOLEAN_LOCAL_SITESWAP_THROWS					= 3;
	final public static byte								bytS_BOOLEAN_LOCAL_SOUNDS							= 19;
	final public static byte								bytS_BOOLEAN_LOCAL_SPEED							= 24;
	final public static byte								bytS_BOOLEAN_LOCAL_STYLE							= 5;
	final public static byte								bytS_BOOLEAN_LOCAL_THROW_SOUNDS						= 21;
	final public static byte								bytS_BOOLEAN_LOCAL_WARNINGS							= 27;
	final public static byte								bytS_BYTE_GLOBAL_BALLS_NUMBER_MAXIMUM_VALUE			= 9;
	final public static byte								bytS_BYTE_GLOBAL_BALLS_NUMBER_MINIMUM_VALUE			= 1;
	final public static byte								bytS_BYTE_GLOBAL_CONTROLS_NUMBER;
	final public static byte								bytS_BYTE_GLOBAL_GAMMA_CORRECTION					= 6;

	final public static byte								bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE		= 10;
	final public static byte								bytS_BYTE_GLOBAL_GAMMA_CORRECTION_MAXIMUM_VALUE		= 20;
	final public static byte								bytS_BYTE_GLOBAL_GAMMA_CORRECTION_MINIMUM_VALUE		= 1;
	final public static byte								bytS_BYTE_GLOBAL_HIGH_BALLS_NUMBER					= 1;
	final public static byte								bytS_BYTE_GLOBAL_HIGH_MARK							= 5;
	final public static byte								bytS_BYTE_GLOBAL_HIGH_SKILL							= 3;
	final public static byte								bytS_BYTE_GLOBAL_LOW_BALLS_NUMBER					= 0;
	final public static byte								bytS_BYTE_GLOBAL_LOW_MARK							= 4;
	final public static byte								bytS_BYTE_GLOBAL_LOW_SKILL							= 2;
	final public static byte								bytS_BYTE_LOCAL_ALTERNATE_COLORS					= 3;
	final public static byte								bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_CATCH			= 0;
	final public static byte								bytS_BYTE_LOCAL_ALTERNATE_COLORS_EACH_COUNT			= 1;
	final public static byte								bytS_BYTE_LOCAL_ALTERNATE_COLORS_MAXIMUM_VALUE		= 10;
	final public static byte								bytS_BYTE_LOCAL_ALTERNATE_COLORS_MINIMUM_RATE_VALUE	= 2;
	final public static byte								bytS_BYTE_LOCAL_BALLS_MAXIMUM_VALUE;
	final public static byte								bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE					= 0;
	final public static byte								bytS_BYTE_LOCAL_BALLS_TRAIL							= 0;
	final public static byte								bytS_BYTE_LOCAL_BALLS_TRAIL_DEFAULT_VALUE			= 0;
	final public static byte								bytS_BYTE_LOCAL_BALLS_TRAIL_FULL					= 51;
	final public static byte								bytS_BYTE_LOCAL_CONTROLS_NUMBER;
	final public static byte								bytS_BYTE_LOCAL_DEFAULTS							= 4;
	final public static byte								bytS_BYTE_LOCAL_DEFAULTS_MAXIMUM_VALUE				= 9;
	final public static byte								bytS_BYTE_LOCAL_DEFAULTS_MINIMUM_VALUE				= 1;
	final public static byte								bytS_BYTE_LOCAL_DWELL								= 7;
	final public static byte								bytS_BYTE_LOCAL_DWELL_DEFAULT_VALUE					= 70;
	final public static byte								bytS_BYTE_LOCAL_DWELL_MAXIMUM_VALUE					= 99;
	final public static byte								bytS_BYTE_LOCAL_DWELL_MINIMUM_VALUE					= 1;
	final public static byte								bytS_BYTE_LOCAL_FLUIDITY							= 12;
	final public static byte								bytS_BYTE_LOCAL_FLUIDITY_DEFAULT_VALUE				= 99;
	final public static byte								bytS_BYTE_LOCAL_FLUIDITY_MAXIMUM_VALUE				= 99;
	final public static byte								bytS_BYTE_LOCAL_FLUIDITY_MINIMUM_VALUE				= 1;
	final public static byte								bytS_BYTE_LOCAL_HEIGHT								= 6;
	final public static byte								bytS_BYTE_LOCAL_HEIGHT_DEFAULT_VALUE				= 12;
	final public static byte								bytS_BYTE_LOCAL_HEIGHT_MAXIMUM_VALUE				= 50;
	final public static byte								bytS_BYTE_LOCAL_HEIGHT_MINIMUM_VALUE				= 1;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_ALL					= 7;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS					= 2;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_ARMS_AND_HANDS			= 3;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_DAY					= 1;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_HANDS					= 1;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD					= 4;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_ARMS			= 6;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_HEAD_AND_HANDS			= 5;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_NIGHT					= 2;
	final public static byte								bytS_BYTE_LOCAL_JUGGLER_BODY_PHANTOM				= 0;
	final public static byte								bytS_BYTE_LOCAL_MARK								= 10;
	final public static byte								bytS_BYTE_LOCAL_MARK_DEFAULT_VALUE					= 2;
	final public static byte								bytS_BYTE_LOCAL_MARK_MAXIMUM_VALUE					= 4;
	final public static byte								bytS_BYTE_LOCAL_MARK_MINIMUM_VALUE					= 0;
	final public static byte								bytS_BYTE_LOCAL_RELATIVE_SPEED						= 11;
	final public static byte								bytS_BYTE_LOCAL_RELATIVE_SPEED_DEFAULT_VALUE		= 10;
	final public static byte								bytS_BYTE_LOCAL_RELATIVE_SPEED_MAXIMUM_VALUE		= 20;
	final public static byte								bytS_BYTE_LOCAL_RELATIVE_SPEED_MINIMUM_VALUE		= 1;
	final public static byte								bytS_BYTE_LOCAL_SKILL								= 9;
	final public static byte								bytS_BYTE_LOCAL_SKILL_AMATEUR						= 1;
	final public static byte								bytS_BYTE_LOCAL_SKILL_BEGINNER						= 0;
	final public static byte								bytS_BYTE_LOCAL_SKILL_CONFIRMED						= 2;
	final public static byte								bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE					= 4;
	final public static byte								bytS_BYTE_LOCAL_SKILL_PRO							= 3;
	final public static byte								bytS_BYTE_LOCAL_SKILLS_NUMBER;
	final public static byte								bytS_BYTE_LOCAL_SPEED								= 8;
	final public static byte								bytS_BYTE_LOCAL_SPEED_DEFAULT_VALUE					= 10;
	final public static byte								bytS_BYTE_LOCAL_SPEED_MAXIMUM_VALUE					= 30;

	final public static byte								bytS_BYTE_LOCAL_SPEED_MINIMUM_VALUE					= 1;
	final public static byte								bytS_BYTE_LOCAL_STROBE								= 5;
	final public static byte								bytS_BYTE_LOCAL_STROBE_EACH_CATCH					= 11;																// 11;
	final public static byte								bytS_BYTE_LOCAL_STROBE_EACH_COUNT					= 10;																// 10;
	final public static byte								bytS_BYTE_LOCAL_STROBE_MAXIMUM_RATE					= 9;																// 9;
	final public static byte								bytS_BYTE_LOCAL_STROBE_MINIMUM_RATE					= 1;

	final public static byte								bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS	= 12;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_CONSOLE					= 4;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_DEBUG					= 7;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_EMBEDDED				= 8;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_ERRORS					= 6;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_FILE					= 0;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_HELP_LONG				= 9;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_HELP_QUESTION_MARK		= 11;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_HELP_SHORT				= 10;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_LANGUAGE				= 3;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_LOG						= 5;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_OCCURRENCE				= 2;
	final public static byte								bytS_COMMAND_LINE_PARAMETER_PATTERN					= 1;
	final public static byte								bytS_COMMAND_LINE_PARAMETERS_NUMBER;
	final public static byte								bytS_ENGINE_ANY_NON_NULL_THROW_VALUE				= 72;
	final public static byte								bytS_ENGINE_ANY_THROW_VALUE							= -36;
	final public static byte								bytS_ENGINE_BALANCE_DEFAULT_VALUE					= 0;
	final public static byte								bytS_ENGINE_COORDONATES_NUMBER						= 3;
	final public static byte								bytS_ENGINE_DARK									= 0;
	final public static byte								bytS_ENGINE_DAY										= 1;
	final public static byte								bytS_ENGINE_LEFT_SIDE								= 0;
	final public static byte								bytS_ENGINE_LIGHT									= 1;
	final public static byte								bytS_ENGINE_NEUTRAL									= 2;
	final public static byte								bytS_ENGINE_NIGHT									= 0;
	final public static byte								bytS_ENGINE_RIGHT_SIDE								= 1;
	final public static byte								bytS_ENGINE_SOUNDS_BUFFERING						= 4;
	final public static byte								bytS_ENGINE_THICKNESS_NORMAL						= 1;
	final public static byte								bytS_ENGINE_THICKNESS_STRONG						= 2;
	final public static byte								bytS_ENGINE_THICKNESS_THIN							= 0;
	final public static byte								bytS_ENGINE_THROWS_VALUES_NUMBER					= 36;
	final public static byte								bytS_ENGINE_TRAIL_LENGTH;
	final public static byte								bytS_ENGINE_VOLUME_DEFAULT_VALUE;
	final public static byte								bytS_EXTENSION_BMP									= 8;
	final public static byte								bytS_EXTENSION_GIF									= 7;
	final public static byte								bytS_EXTENSION_INI									= 3;
	final public static byte								bytS_EXTENSION_JAP									= 2;
	final public static byte								bytS_EXTENSION_JM									= 1;
	final public static byte								bytS_EXTENSION_JMP									= 0;
	final public static byte								bytS_EXTENSION_JPEG									= 6;
	final public static byte								bytS_EXTENSION_JPG									= 5;
	final public static byte								bytS_EXTENSION_LANG_INI								= 3;
	final public static byte								bytS_EXTENSION_PNG									= 4;
	final public static byte								bytS_FILE_SOUND_CLOSE								= 1;
	final public static byte								bytS_FILE_SOUND_LEFT_CATCH							= 2;
	final public static byte								bytS_FILE_SOUND_LEFT_THROW							= 4;
	final public static byte								bytS_FILE_SOUND_METRONOME							= 8;
	final public static byte								bytS_FILE_SOUND_OPEN								= 0;
	final public static byte								bytS_FILE_SOUND_RIGHT_CATCH							= 3;
	final public static byte								bytS_FILE_SOUND_RIGHT_THROW							= 5;
	final public static byte								bytS_FILE_SOUND_SCREEN_PLAY							= 7;
	final public static byte								bytS_FILE_SOUND_SCREEN_SHOT							= 6;
	final public static byte								bytS_FILES_EXTENSIONS_NUMBER;
	final public static byte								bytS_FILES_SOUNDS_NUMBER;
	final public static byte								bytS_MANAGER_FILES_PATTERNS							= 3;
	final public static byte								bytS_MANAGER_JM_PATTERN								= 1;
	final public static byte								bytS_MANAGER_NEW_ABSTRACT_LANGUAGE					= 4;
	final public static byte								bytS_MANAGER_NEW_PATTERN							= 2;
	final public static byte								bytS_MANAGER_NO_PATTERN								= 0;
	final public static byte								bytS_OTHER_LOCAL_BACKGROUND							= 2;
	final public static byte								bytS_OTHER_LOCAL_CONTROLS_NUMBER;
	final public static byte								bytS_OTHER_LOCAL_GRAVITY							= 0;
	final public static byte								bytS_OTHER_LOCAL_START_PATTERN						= 1;
	final public static byte								bytS_STATE_ANIMATION_JUGGLING						= 5;
	final public static byte								bytS_STATE_ANIMATION_PAUSED							= 3;
	final public static byte								bytS_STATE_ANIMATION_PAUSING						= 4;
	final public static byte								bytS_STATE_ANIMATION_STOPPED						= 0;
	final public static byte								bytS_STATE_ANIMATION_STOPPING						= 1;
	final public static byte								bytS_STATE_ANIMATION_UNPLAYABLE						= 2;
	final public static byte								bytS_STATE_SITESWAP_ASYNCHRO						= 0;

	final public static byte								bytS_STATE_SITESWAP_EMPTY							= -3;
	// Siteswap status :
	final public static byte								bytS_STATE_SITESWAP_FORBIDDEN_CHAR					= -6;
	final public static byte								bytS_STATE_SITESWAP_MAYBE_PLAYABLE					= 0;
	final public static byte								bytS_STATE_SITESWAP_ODD_THROW_VALUE					= -4;
	final public static byte								bytS_STATE_SITESWAP_PLAYABLE						= 1;
	final public static byte								bytS_STATE_SITESWAP_SYNCHRO							= 1;
	final public static byte								bytS_STATE_SITESWAP_UNEXPECTED_CHAR					= -5;
	final public static byte								bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER			= -2;
	final public static byte								bytS_STATE_SITESWAP_UNPLAYABLE						= -1;
	// Siteswap comparisons :
	final public static byte								bytS_STATE_SITESWAPS_IDENTICAL						= 0;
	final public static byte								bytS_STATE_SITESWAPS_NON_IDENTICAL					= 2;
	final public static byte								bytS_STATE_SITESWAPS_NON_IDENTICAL_SPACES			= 1;
	final public static byte								bytS_STRING_GLOBAL_CONTROLS_NUMBER;

	final public static byte								bytS_STRING_GLOBAL_LANGUAGE							= 0;
	final public static byte								bytS_STRING_LOCAL_BACKGROUND_DAY					= 6;
	final public static byte								bytS_STRING_LOCAL_BACKGROUND_NIGHT					= 12;
	final public static byte								bytS_STRING_LOCAL_COLORS							= 2;
	final public static byte								bytS_STRING_LOCAL_CONTROLS_NUMBER;
	final public static byte								bytS_STRING_LOCAL_JUGGLER_DAY						= 4;
	final public static byte								bytS_STRING_LOCAL_JUGGLER_NIGHT						= 5;
	final public static byte								bytS_STRING_LOCAL_PATTERN							= 0;
	final public static byte								bytS_STRING_LOCAL_REVERSE_COLORS					= 3;
	final public static byte								bytS_STRING_LOCAL_REVERSE_SITESWAP					= 8;
	final public static byte								bytS_STRING_LOCAL_SITESWAP							= 7;
	final public static byte								bytS_STRING_LOCAL_SITESWAP_DAY						= 9;
	final public static byte								bytS_STRING_LOCAL_SITESWAP_NIGHT					= 10;
	final public static byte								bytS_STRING_LOCAL_STYLE								= 1;
	final public static byte								bytS_STRING_LOCAL_TOOLTIP							= 11;
	final public static byte								bytS_UNCLASS_CURRENT								= 1;
	final public static byte								bytS_UNCLASS_INITIAL								= 0;
	final public static byte								bytS_UNCLASS_MAXIMUM_RECENT_FILES_NUMBER			= 5;
	final public static byte								bytS_UNCLASS_NO_VALUE								= -1;
	final public static byte								bytS_UNCLASS_SAVE									= 2;
	final public static byte								bytS_UNCLASS_TEMPORARY_CURRENT						= 3;
	final public static int									intS_ACTION_CLEAR_ANIMATION_IMAGE					= 1;
	final public static int									intS_ACTION_DO_NOTHING								= 0;
	final public static int									intS_ACTION_DRAW_SITESWAP							= 8;
	final public static int									intS_ACTION_HIDE_DATA_FRAME							= 16384;
	final public static int									intS_ACTION_INIT_ANIMATION_PROPERTIES				= 65536;
	final public static int									intS_ACTION_INIT_DATA_FRAME							= 131072;
	final public static int									intS_ACTION_INIT_TITLES								= 262144;
	final public static int									intS_ACTION_RECREATE_ANIMATION_IMAGE				= 128;
	final public static int									intS_ACTION_RECREATE_BACKGROUND_IMAGES				= 64;
	final public static int									intS_ACTION_RECREATE_BALLS_ERASING_IMAGES			= 2048;
	final public static int									intS_ACTION_RECREATE_BALLS_IMAGES					= 512;
	final public static int									intS_ACTION_RECREATE_BALLS_TRAILS_IMAGES			= 1024;
	final public static int									intS_ACTION_RECREATE_HANDS_IMAGES					= 8192;
	final public static int									intS_ACTION_RECREATE_JUGGLER_TRAILS_IMAGES			= 4096;
	final public static int									intS_ACTION_RECREATE_SITESWAP_IMAGE					= 256;
	final public static int									intS_ACTION_REFRESH_DRAWING							= 32;
	final public static int									intS_ACTION_REINIT_COLORS							= 16;
	final public static int									intS_ACTION_RESET_BALLS								= 2;
	final public static int									intS_ACTION_RESET_STYLE								= 4;
	final public static int									intS_ACTION_SHOW_DATA_FRAME							= 32768;
	final public static int									intS_FILE_FOLDER_DATA								= 98;
	final public static int									intS_FILE_FOLDER_IMAGES								= 99;
	final public static int									intS_FILE_FOLDER_LIBRARY							= 154;
	final public static int									intS_FILE_FOLDER_SOUNDS								= 100;
	final public static int									intS_FILE_ICON_ABOUT								= 52;
	final public static int									intS_FILE_ICON_ALERT								= 74;
	final public static int									intS_FILE_ICON_APPLY								= 80;
	final public static int									intS_FILE_ICON_APPLY_BW								= 143;
	final public static int									intS_FILE_ICON_CANCEL								= 79;
	final public static int									intS_FILE_ICON_CANCEL_BW							= 144;
	final public static int									intS_FILE_ICON_CANCEL_FIND							= 121;
	final public static int									intS_FILE_ICON_CANCEL_FIND_BW						= 130;
	final public static int									intS_FILE_ICON_CLEAR_FIELD							= 125;
	final public static int									intS_FILE_ICON_CLEAR_FIELD_BW						= 126;
	final public static int									intS_FILE_ICON_CLEAR_FILTERS						= 123;
	final public static int									intS_FILE_ICON_CLEAR_FILTERS_BW						= 132;
	final public static int									intS_FILE_ICON_CLIPBOARD							= 66;
	final public static int									intS_FILE_ICON_CLOSE_CLIPBOARD						= 161;
	final public static int									intS_FILE_ICON_CLOSE_CLIPBOARD_BW					= 162;
	final public static int									intS_FILE_ICON_CLOSE_CONSOLE						= 85;
	final public static int									intS_FILE_ICON_CLOSE_CONSOLE_BW						= 141;
	final public static int									intS_FILE_ICON_CLOSE_DATA							= 146;
	final public static int									intS_FILE_ICON_CLOSE_DATA_BW						= 147;
	final public static int									intS_FILE_ICON_CLOSE_DEVELOPMENT					= 166;
	final public static int									intS_FILE_ICON_CLOSE_DEVELOPMENT_BW					= 167;
	final public static int									intS_FILE_ICON_CLOSE_FILTERS						= 122;
	final public static int									intS_FILE_ICON_CLOSE_FILTERS_BW						= 133;
	final public static int									intS_FILE_ICON_CLOSE_FIND							= 120;
	final public static int									intS_FILE_ICON_CLOSE_FIND_BW						= 129;
	final public static int									intS_FILE_ICON_COLORS								= 164;
	final public static int									intS_FILE_ICON_CONFIGURATION						= 112;
	final public static int									intS_FILE_ICON_CONSOLE								= 69;
	final public static int									intS_FILE_ICON_CONTENT_ADJUSTMENT					= 148;
	final public static int									intS_FILE_ICON_CONTENT_ADJUSTMENT_BW				= 150;
	final public static int									intS_FILE_ICON_COPY_CLIPBOARD						= 77;
	final public static int									intS_FILE_ICON_COPY_CLIPBOARD_BW					= 134;
	final public static int									intS_FILE_ICON_COPY_CONSOLE							= 84;
	final public static int									intS_FILE_ICON_COPY_CONSOLE_BW						= 139;
	final public static int									intS_FILE_ICON_DATA									= 114;
	final public static int									intS_FILE_ICON_DEFAULT_PATTERNS						= 76;
	final public static int									intS_FILE_ICON_DETAILED_COPY						= 56;
	final public static int									intS_FILE_ICON_DETAILED_COPY_BW						= 136;
	final public static int									intS_FILE_ICON_DEVELOPMENT							= 95;

	final public static int									intS_FILE_ICON_DISPLAY_CLIPBOARD					= 75;
	final public static int									intS_FILE_ICON_DO_NOT_FILTER						= 48;
	final public static int									intS_FILE_ICON_DO_NOT_FILTER_BW						= 131;
	final public static int									intS_FILE_ICON_DO_NOT_IMPORT_PATTERNS				= 36;
	final public static int									intS_FILE_ICON_DO_NOT_IMPORT_SITESWAPS				= 42;
	final public static int									intS_FILE_ICON_DO_NOT_IMPORT_STYLES					= 27;
	final public static int									intS_FILE_ICON_DO_NOT_LOAD_PATTERNS					= 50;
	final public static int									intS_FILE_ICON_DO_NOT_REFRESH_SITESWAP				= 11;
	final public static int									intS_FILE_ICON_DO_NOT_RELOAD_PATTERN				= 8;
	final public static int									intS_FILE_ICON_DO_NOT_REVERSE_PATTERN				= 14;
	final public static int									intS_FILE_ICON_DO_NOT_REVERSE_SORT_STYLES			= 39;
	final public static int									intS_FILE_ICON_DO_NOT_SORT_STYLES					= 33;
	final public static int									intS_FILE_ICON_DROP_DOWN_B							= 116;
	final public static int									intS_FILE_ICON_DROP_DOWN_W							= 115;
	final public static int									intS_FILE_ICON_DROP_UP_B							= 119;
	final public static int									intS_FILE_ICON_DROP_UP_W							= 118;
	final public static int									intS_FILE_ICON_DUPLEX								= 104;
	final public static int									intS_FILE_ICON_ERROR								= 94;
	final public static int									intS_FILE_ICON_EXPORT								= 67;
	final public static int									intS_FILE_ICON_EXPORT_CURRENT_LANGUAGE				= 124;
	final public static int									intS_FILE_ICON_EXPORT_PATTERNS						= 72;
	final public static int									intS_FILE_ICON_EXPORT_SITESWAPS						= 73;
	final public static int									intS_FILE_ICON_EXPORT_STYLES						= 152;
	final public static int									intS_FILE_ICON_FILTER								= 47;
	final public static int									intS_FILE_ICON_FILTER_BW							= 46;
	final public static int									intS_FILE_ICON_FIND									= 89;
	final public static int									intS_FILE_ICON_FIND_NEXT							= 90;
	final public static int									intS_FILE_ICON_FIND_NEXT_BW							= 127;
	final public static int									intS_FILE_ICON_FIND_PREVIOUS						= 91;
	final public static int									intS_FILE_ICON_FIND_PREVIOUS_BW						= 128;
	final public static int									intS_FILE_ICON_FORWARD_PATTERN						= 12;
	final public static int									intS_FILE_ICON_FORWARD_PATTERN_BW					= 24;

	final public static int									intS_FILE_ICON_FRAME								= 5;
	final public static int									intS_FILE_ICON_FREE_CLIPBOARD						= 57;
	final public static int									intS_FILE_ICON_FREE_CLIPBOARD_BW					= 135;
	final public static int									intS_FILE_ICON_FREE_CONSOLE							= 86;
	final public static int									intS_FILE_ICON_FREE_CONSOLE_BW						= 140;
	final public static int									intS_FILE_ICON_HELP									= 53;
	final public static int									intS_FILE_ICON_IMAGE								= 113;
	final public static int									intS_FILE_ICON_IMPORT								= 65;
	final public static int									intS_FILE_ICON_IMPORT_PATTERNS						= 34;
	final public static int									intS_FILE_ICON_IMPORT_PATTERNS_BW					= 35;
	final public static int									intS_FILE_ICON_IMPORT_SITESWAPS						= 40;
	final public static int									intS_FILE_ICON_IMPORT_SITESWAPS_BW					= 41;
	final public static int									intS_FILE_ICON_IMPORT_STYLES						= 25;
	final public static int									intS_FILE_ICON_IMPORT_STYLES_BW						= 26;
	final public static int									intS_FILE_ICON_INFORMATION							= 160;
	final public static int									intS_FILE_ICON_JAP									= 171;
	final public static int									intS_FILE_ICON_JAP_HEADER							= 172;
	final public static int									intS_FILE_ICON_JM									= 82;
	final public static int									intS_FILE_ICON_JM_HEADER							= 45;
	final public static int									intS_FILE_ICON_JMP									= 83;
	final public static int									intS_FILE_ICON_JMP_HEADER							= 28;
	final public static int									intS_FILE_ICON_LANDSCAPE							= 105;
	final public static int									intS_FILE_ICON_LANGUAGE								= 97;
	final public static int									intS_FILE_ICON_LANGUAGES							= 62;
	final public static int									intS_FILE_ICON_LICENCE								= 51;
	final public static int									intS_FILE_ICON_LINKS								= 61;
	final public static int									intS_FILE_ICON_LOAD_PATTERNS						= 49;
	final public static int									intS_FILE_ICON_MARK									= 169;
	final public static int									intS_FILE_ICON_MARK_BW								= 170;
	final public static int									intS_FILE_ICON_NEW_DEFAULT_LANGUAGE					= 157;
	final public static int									intS_FILE_ICON_NEW_DEFAULT_LANGUAGE_BW				= 158;
	final public static int									intS_FILE_ICON_NEW_EMPTY_LANGUAGE					= 155;
	final public static int									intS_FILE_ICON_NEW_EMPTY_LANGUAGE_BW				= 156;
	final public static int									intS_FILE_ICON_NEW_LANGUAGE							= 70;
	final public static int									intS_FILE_ICON_NEW_PATTERN							= 68;
	final public static int									intS_FILE_ICON_NEW_PATTERNS_FILE					= 71;
	final public static int									intS_FILE_ICON_OK									= 78;
	final public static int									intS_FILE_ICON_OK_BW								= 138;
	final public static int									intS_FILE_ICON_ONE_SIDE								= 106;
	final public static int									intS_FILE_ICON_PANEL								= 101;
	final public static int									intS_FILE_ICON_PAUSE								= 15;
	final public static int									intS_FILE_ICON_PAUSE_BW								= 16;
	final public static int									intS_FILE_ICON_PLAY									= 17;
	final public static int									intS_FILE_ICON_PLAY_BW								= 18;
	final public static int									intS_FILE_ICON_PORTRAIT								= 107;
	final public static int									intS_FILE_ICON_PREFERENCES							= 58;
	final public static int									intS_FILE_ICON_PRINT								= 103;
	final public static int									intS_FILE_ICON_QUESTION								= 159;
	final public static int									intS_FILE_ICON_QUIT									= 59;
	final public static int									intS_FILE_ICON_RECENT								= 117;
	final public static int									intS_FILE_ICON_RECORDS								= 63;
	final public static int									intS_FILE_ICON_REFRESH_SITESWAP						= 9;
	final public static int									intS_FILE_ICON_REFRESH_SITESWAP_BW					= 10;
	final public static int									intS_FILE_ICON_RELOAD								= 60;
	final public static int									intS_FILE_ICON_RELOAD_PATTERN						= 6;
	final public static int									intS_FILE_ICON_RELOAD_PATTERN_BW					= 7;
	final public static int									intS_FILE_ICON_RELOAD_PATTERN_YELLOW				= 111;
	final public static int									intS_FILE_ICON_REPLAY								= 19;

	final public static int									intS_FILE_ICON_REPLAY_BW							= 20;
	final public static int									intS_FILE_ICON_RESET								= 102;
	final public static int									intS_FILE_ICON_RESET_BW								= 142;
	final public static int									intS_FILE_ICON_RESTORE								= 81;
	final public static int									intS_FILE_ICON_RESTORE_BW							= 145;

	final public static int									intS_FILE_ICON_REVERSE_LANDSCAPE					= 108;
	final public static int									intS_FILE_ICON_REVERSE_PATTERN						= 13;
	final public static int									intS_FILE_ICON_REVERSE_PATTERN_BW					= 23;
	final public static int									intS_FILE_ICON_REVERSE_PORTRAIT						= 109;
	final public static int									intS_FILE_ICON_REVERSE_SORT_STYLES					= 38;
	final public static int									intS_FILE_ICON_REVERSE_SORT_STYLES_BW				= 37;
	final public static int									intS_FILE_ICON_SCREEN_PLAY							= 88;
	final public static int									intS_FILE_ICON_SCREEN_SHOT							= 87;
	final public static int									intS_FILE_ICON_SIMPLE_COPY							= 55;
	final public static int									intS_FILE_ICON_SIMPLE_COPY_BW						= 137;
	final public static int									intS_FILE_ICON_SITESWAP_THEORY						= 54;
	final public static int									intS_FILE_ICON_SORT_STYLES							= 31;
	final public static int									intS_FILE_ICON_SORT_STYLES_BW						= 32;
	final public static int									intS_FILE_ICON_SOUNDS								= 168;
	final public static int									intS_FILE_ICON_START_SCREEN_PLAY					= 92;
	final public static int									intS_FILE_ICON_STOP									= 21;
	final public static int									intS_FILE_ICON_STOP_BW								= 22;
	final public static int									intS_FILE_ICON_STOP_SCREEN_PLAY						= 93;

	final public static int									intS_FILE_ICON_TUMBLE								= 110;
	final public static int									intS_FILE_ICON_VALUES								= 165;
	final public static int									intS_FILE_ICON_VIDEO								= 64;
	final public static int									intS_FILE_ICON_WINDOW_ADJUSTMENT					= 149;
	final public static int									intS_FILE_ICON_WINDOW_ADJUSTMENT_BW					= 151;

	final public static int									intS_FILE_IMAGE_ABOUT								= 4;
	final public static int									intS_FILE_IMAGE_ARROWS								= 43;
	final public static int									intS_FILE_TEXT_DEVELOPMENT							= 96;
	final public static int									intS_FILE_TEXT_FONT									= 153;
	final public static int									intS_FILE_TEXT_HELP									= 44;
	final public static int									intS_FILE_TEXT_LANGUAGE								= 30;
	final public static int									intS_FILE_TEXT_LANGUAGES							= 29;
	final public static int									intS_FILE_TEXT_LINKS								= 2;
	final public static int									intS_FILE_TEXT_LOG									= 163;
	final public static int									intS_FILE_TEXT_PATTERNS								= 0;
	final public static int									intS_FILE_TEXT_RECORDS								= 1;
	final public static int									intS_FILE_TEXT_SITESWAP_THEORY						= 3;
	final public static int									intS_FILE_TEXT_VIDEO								= 173;
	final private static int								intS_FILES_TYPES_NUMBER;
	final public static int									intS_GRAPHICS_ANIMATION_DEFAULT_SIZE				= 512;
	final public static int									intS_GRAPHICS_BALL_MAXIMUM_RADIUS					= 11;
	final public static int									intS_GRAPHICS_CORRECTION_X							= 16;
	final public static int									intS_GRAPHICS_HORIZONTAL_CENTER						= 240;
	final public static int									intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE				= 512;
	final public static int									intS_GRAPHICS_MAXIMUM_COMBO_ROWS_NUMBER				= 16;
	final public static int									intS_GRAPHICS_MAXIMUM_DRAWING_SIZE					= 290;
	final public static int									intS_GRAPHICS_SCREEN_HEIGHT;
	final public static int									intS_GRAPHICS_SCREEN_WIDTH;
	final public static int									intS_GRAPHICS_SCREEN_X;
	final public static int									intS_GRAPHICS_SCREEN_Y;
	final public static int									intS_GRAPHICS_SITESWAP_HEIGHT						= 35;
	final public static int									intS_GRAPHICS_VERTICAL_CENTER						= 200;
	final public static int									intS_SHORTCUT_PLAY_PAUSE							= KeyEvent.VK_SPACE;
	final public static int									intS_UNCLASS_CONTROLS_STRINGS_LENGTH				= 25;
	final public static KeyStroke							keyS_ABOUT											=
																		KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_CONSOLE										=
																			KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_CURRENT_LANGUAGE_FILE							=
																						KeyStroke.getKeyStroke(	KeyEvent.VK_L,
																												ActionEvent.CTRL_MASK
																																| ActionEvent.ALT_MASK);
	final public static KeyStroke							keyS_DATA_FRAME										=
																			KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_DETAILED_COPY									=
																				KeyStroke.getKeyStroke(	KeyEvent.VK_C,
																										ActionEvent.CTRL_MASK	| ActionEvent.ALT_MASK
																														| ActionEvent.SHIFT_MASK);
	final public static KeyStroke							keyS_DEVELOPMENT									=
																				KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.SHIFT_MASK);
	final public static KeyStroke							keyS_DISPLAY_CLIPBOARD								=
																					KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK);
	final public static KeyStroke							keyS_EXPORT_PATTERNS								=
																					KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_EXPORT_SITESWAPS								=
																					KeyStroke.getKeyStroke(	KeyEvent.VK_S,
																											ActionEvent.CTRL_MASK
																															| ActionEvent.ALT_MASK);
	final public static KeyStroke							keyS_EXPORT_STYLES									=
																				KeyStroke.getKeyStroke(	KeyEvent.VK_S,
																										ActionEvent.CTRL_MASK
																														| ActionEvent.SHIFT_MASK);
	final public static KeyStroke							keyS_FILTERS										=
																			KeyStroke.getKeyStroke(	KeyEvent.VK_F,
																									ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK);
	final public static KeyStroke							keyS_FIND											=
																		KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_FIND_NEXT										=
																			KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0);
	final public static KeyStroke							keyS_FIND_PREVIOUS									=
																				KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.SHIFT_MASK);
	final public static KeyStroke							keyS_FREE_CLIPBOARD									=
																				KeyStroke.getKeyStroke(	KeyEvent.VK_X,
																										ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK);
	final public static KeyStroke							keyS_HELP											=
																		KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
	final public static KeyStroke							keyS_IMPORT_PATTERNS								=
																					KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_IMPORT_SITESWAPS								=
																					KeyStroke.getKeyStroke(	KeyEvent.VK_I,
																											ActionEvent.CTRL_MASK
																															| ActionEvent.ALT_MASK);
	final public static KeyStroke							keyS_IMPORT_STYLES									=
																				KeyStroke.getKeyStroke(	KeyEvent.VK_I,
																										ActionEvent.CTRL_MASK
																														| ActionEvent.SHIFT_MASK);
	final public static KeyStroke[]							keyS_INDEX_NUMBER_A;
	final public static KeyStroke							keyS_LICENCE										=
																			KeyStroke.getKeyStroke(	KeyEvent.VK_F1,
																									ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK);
	final public static KeyStroke							keyS_NEW_LANGUAGE_FILE								=
																					KeyStroke.getKeyStroke(	KeyEvent.VK_L,
																											ActionEvent.CTRL_MASK
																															| ActionEvent.SHIFT_MASK);
	final public static KeyStroke							keyS_NEW_PATTERN									=
																				KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_NEW_PATTERNS_FILE								=
																					KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_OPEN											=
																		KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_PREFERENCES									=
																				KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK);

	final public static KeyStroke							keyS_QUIT											=
																		KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK);
	final public static KeyStroke							keyS_RELOAD_FILE									=
																				KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK);										// +
	final public static KeyStroke							keyS_RELOAD_PATTERN									=
																				KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
	final public static KeyStroke							keyS_REVERSE_ALL									=
																				KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK);
	final public static int									keyS_SCREENSHOT										= KeyEvent.VK_PRINTSCREEN;											// +
	final public static KeyStroke							keyS_SIMPLE_COPY									=
																				KeyStroke.getKeyStroke(	KeyEvent.VK_C,
																										ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK);
	final public static KeyStroke							keyS_SITESWAP_THEORY								=
																					KeyStroke.getKeyStroke(	KeyEvent.VK_F1,
																											ActionEvent.CTRL_MASK
																															| ActionEvent.ALT_MASK);
	final public static long								lngS_ENGINE_START_DATE								= System.currentTimeMillis();
	final public static long								lngS_ENGINE_VERSION_NUMBER							= 430L;
	final public static Object								objS_ENGINE_COLORS_LOCK_OBJECT						= new Object();
	final public static Object								objS_ENGINE_FRAMES_LOCK_OBJECT						= new Object();
	final public static Object								objS_ENGINE_GRAPHICS_LOCK_OBJECT					= new Object();
	final public static HashMap<String, String>				objS_ENGINE_PARAMETERS_HASH_MAP;
	final public static Object								objS_ENGINE_START_PATTERN_LOCK_OBJECT				= new Object();
	final public static GraphicsConfiguration				objS_GRAPHICS_CONFIGURATION							=
																						GraphicsEnvironment	.getLocalGraphicsEnvironment()
																											.getDefaultScreenDevice()
																											.getDefaultConfiguration();
	final public static DefaultRenderer						objS_GRAPHICS_DEFAULT_RENDERER						= new DefaultRenderer();
	final public static Border								objS_GRAPHICS_EMPTY_BORDER							=
																						BorderFactory.createEmptyBorder(0, 0, 0, 0);
	public static FontMetrics								objS_GRAPHICS_FONT_METRICS							= null;

	final public static Border								objS_GRAPHICS_JUGGLE_BORDER							=
																						BorderFactory.createCompoundBorder(	BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
																															null);

	final public static Toolkit								objS_GRAPHICS_TOOLKIT								= Toolkit.getDefaultToolkit();

	final public static Color								objS_PEN_COLORS_DARK_GREEN_COLOR					= new Color(0, 64, 0);
	final public static Color								objS_PEN_COLORS_LIGHT_YELLOW_COLOR					= new Color(255, 255, 225);
	@SuppressWarnings("unused") final private static long	serialVersionUID;
	final public static String								strG_STRING_LOCAL_USER								= "user";
	final public static String								strS_BOOLEAN_GLOBAL									= "bolG";
	final public static String[]							strS_BOOLEAN_GLOBAL_CONTROL_A;
	final public static String[]							strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A;
	final public static String								strS_BOOLEAN_LOCAL									= "bolL";
	final public static String[][]							strS_BOOLEAN_LOCAL_CONTROL_A_A;
	final public static String[]							strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A;
	final public static String								strS_BYTE_GLOBAL									= "bytG";
	final public static String[]							strS_BYTE_GLOBAL_CONTROL_A;
	final public static String[]							strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A;
	final public static String								strS_BYTE_LOCAL										= "bytL";
	final public static String[][]							strS_BYTE_LOCAL_CONTROL_A_A;
	final public static String[]							strS_BYTE_LOCAL_CONTROL_PREFERENCE_A;
	final public static String[]							strS_COLORS_PEN_COLOR_A;
	final public static String[]							strS_COMMAND_LINE_PARAMETER_A;
	final public static String								strS_ENGINE_ARNAUD_BELO								= "Arnaud BeLO.";
	final public static String								strS_ENGINE_COPYRIGHT_YEARS							= "1998-2017";
	final public static String								strS_ENGINE_ESCAPE_ACTION							= "Escape";
	final public static String								strS_ENGINE_TET_DE_KRAN_STYLE						= "\tPrise De Têt' De Krän !\t";
	final public static String								strS_ENGINE_VERSION_NUMBER;
	// File extensions :
	final public static String[]							strS_FILE_EXTENSION_A;
	final public static String[]							strS_FILE_NAME_A;
	final public static String[]							strS_FILE_SOUND_NAME_A;

	final public static String[][]							strS_OTHER_LOCAL_CONTROL_A_A;

	final public static String								strS_STRING_GLOBAL									= "strG";

	final public static String[]							strS_STRING_GLOBAL_CONTROL_A;
	final public static String[]							strS_STRING_GLOBAL_CONTROL_PREFERENCE_A;

	final public static String								strS_STRING_GLOBAL_LANGUAGE_DEFAULT					= "fr";

	final public static String								strS_STRING_LOCAL									= "strL";

	final public static String								strS_STRING_LOCAL_BACKGROUND_DAY_DEFAULT			= "FFF";

	final public static String								strS_STRING_LOCAL_BACKGROUND_NIGHT_DEFAULT			= "000";

	final public static String								strS_STRING_LOCAL_COLORS_DEFAULT					=
																								BallsColors.getBallColorString(	BallsColors.intS_BALLS_COLORS_DEFAULT_VALUE,
																																true);

	final public static String[]							strS_STRING_LOCAL_CONTROL_A;

	final public static String[]							strS_STRING_LOCAL_CONTROL_PREFERENCE_A;

	final public static String								strS_STRING_LOCAL_JUGGLER_DAY_DEFAULT				= "444";

	final public static String								strS_STRING_LOCAL_JUGGLER_NIGHT_DEFAULT				= "CCC";

	final public static String								strS_STRING_LOCAL_SITESWAP_DAY_DEFAULT				= "F00";

	final public static String								strS_STRING_LOCAL_SITESWAP_DEFAULT					= "0";

	final public static String								strS_STRING_LOCAL_SITESWAP_NIGHT_DEFAULT			= "F22";

	final public static String								strS_STRING_LOCAL_STYLE_DEFAULT						= " ";

	public static String									strS_STRING_LOCAL_USER								= "user";

	static {

		// Versioning :
		serialVersionUID = Constants.lngS_ENGINE_VERSION_NUMBER;
		final int intLmajorVersion = (int) (Constants.lngS_ENGINE_VERSION_NUMBER / 100);
		final int intLminorVersion = (int) ((Constants.lngS_ENGINE_VERSION_NUMBER - intLmajorVersion * 100) / 10);
		final int intLpatchVersion = (int) (Constants.lngS_ENGINE_VERSION_NUMBER - intLmajorVersion * 100 - intLminorVersion * 10);
		strS_ENGINE_VERSION_NUMBER = Strings.doConcat(intLmajorVersion, '.', intLminorVersion, '.', intLpatchVersion);

		// System :
		final Dimension objLdimension = Constants.objS_GRAPHICS_TOOLKIT.getScreenSize();
		final Insets objLscreenInsets = Constants.objS_GRAPHICS_TOOLKIT.getScreenInsets(Constants.objS_GRAPHICS_CONFIGURATION);
		intS_GRAPHICS_SCREEN_X = objLscreenInsets.left;
		intS_GRAPHICS_SCREEN_Y = objLscreenInsets.top;
		intS_GRAPHICS_SCREEN_WIDTH = (int) objLdimension.getWidth() - objLscreenInsets.left - objLscreenInsets.right;
		intS_GRAPHICS_SCREEN_HEIGHT = (int) objLdimension.getHeight() - objLscreenInsets.top - objLscreenInsets.bottom;
		bytS_ENGINE_TRAIL_LENGTH = Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL - Constants.bytS_BYTE_LOCAL_BALLS_MINIMUM_VALUE;
		bytS_BYTE_LOCAL_SKILLS_NUMBER = Constants.bytS_BYTE_LOCAL_SKILL_IMPOSSIBLE + 1;
		bytS_ENGINE_VOLUME_DEFAULT_VALUE = Constants.bytS_UNCLASS_NO_VALUE;

		// Command line parameters :
		bytS_COMMAND_LINE_PARAMETERS_NUMBER = Constants.bytS_COMMAND_LINE_PARAMETER_CATCH_ALL_EXCEPTIONS + 1;
		strS_COMMAND_LINE_PARAMETER_A = new String[Constants.bytS_COMMAND_LINE_PARAMETERS_NUMBER];
		Constants.setCommandLineParameters();

		// Shortcuts numbers (from 1 to 9) :
		keyS_INDEX_NUMBER_A = new KeyStroke[9];
		for (byte bytLindex = 0; bytLindex < 9; ++bytLindex) {
			int intLkeyCode = Constants.bytS_UNCLASS_NO_VALUE;
			switch (bytLindex) {
				case 0:
					intLkeyCode = KeyEvent.VK_1;
					break;
				case 1:
					intLkeyCode = KeyEvent.VK_2;
					break;
				case 2:
					intLkeyCode = KeyEvent.VK_3;
					break;
				case 3:
					intLkeyCode = KeyEvent.VK_4;
					break;
				case 4:
					intLkeyCode = KeyEvent.VK_5;
					break;
				case 5:
					intLkeyCode = KeyEvent.VK_6;
					break;
				case 6:
					intLkeyCode = KeyEvent.VK_7;
					break;
				case 7:
					intLkeyCode = KeyEvent.VK_8;
					break;
				case 8:
					intLkeyCode = KeyEvent.VK_9;
					break;
			}
			Constants.keyS_INDEX_NUMBER_A[bytLindex] = KeyStroke.getKeyStroke(intLkeyCode, ActionEvent.CTRL_MASK);
		}

		// Files :
		intS_FILES_TYPES_NUMBER = Constants.intS_FILE_TEXT_VIDEO + 1;
		strS_FILE_NAME_A = new String[Constants.intS_FILES_TYPES_NUMBER];
		bytS_FILES_SOUNDS_NUMBER = Constants.bytS_FILE_SOUND_METRONOME + 1;
		strS_FILE_SOUND_NAME_A = new String[Constants.bytS_FILES_SOUNDS_NUMBER];
		Constants.setFilesNames();

		// Extensions :
		bytS_FILES_EXTENSIONS_NUMBER = Constants.bytS_EXTENSION_BMP + 1;
		strS_FILE_EXTENSION_A = new String[Constants.bytS_FILES_EXTENSIONS_NUMBER];
		Constants.setExtensions();

		// Colors :
		strS_COLORS_PEN_COLOR_A = new String[2];
		Constants.setColors();

		// Byte local controls :
		bytS_BYTE_LOCAL_CONTROLS_NUMBER = Constants.bytS_BYTE_LOCAL_FLUIDITY + 1;
		strS_BYTE_LOCAL_CONTROL_A_A = new String[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER][2];
		bytS_BYTE_LOCAL_BALLS_MAXIMUM_VALUE = Constants.bytS_BYTE_LOCAL_BALLS_TRAIL_FULL;
		Constants.setByteLocalControls();

		// Byte global controls :
		bytS_BYTE_GLOBAL_CONTROLS_NUMBER = Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION + 1;
		strS_BYTE_GLOBAL_CONTROL_A = new String[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		Constants.setbytGlobalControls();

		// Boolean local controls :
		bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER = Constants.bytS_BOOLEAN_LOCAL_ERRORS + 1;
		strS_BOOLEAN_LOCAL_CONTROL_A_A = new String[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER][2];
		Constants.setBooleanLocalControls();

		// Boolean global controls :
		bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER = Constants.bytS_BOOLEAN_GLOBAL_NAVIGATOR_CONFIGURATION + 1;
		strS_BOOLEAN_GLOBAL_CONTROL_A = new String[Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER];
		Constants.setBooleanGlobalControls();

		// String local controls :
		bytS_STRING_LOCAL_CONTROLS_NUMBER = Constants.bytS_STRING_LOCAL_BACKGROUND_NIGHT + 1;
		strS_STRING_LOCAL_CONTROL_A = new String[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];
		Constants.setStringLocalControls();

		// String global controls :
		bytS_STRING_GLOBAL_CONTROLS_NUMBER = Constants.bytS_STRING_GLOBAL_LANGUAGE + 1;
		strS_STRING_GLOBAL_CONTROL_A = new String[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER];
		Constants.setStringGlobalControls();

		// Other local controls :
		bytS_OTHER_LOCAL_CONTROLS_NUMBER = Constants.bytS_OTHER_LOCAL_BACKGROUND + 1;
		strS_OTHER_LOCAL_CONTROL_A_A = new String[Constants.bytS_OTHER_LOCAL_CONTROLS_NUMBER][2];
		Constants.setOtherLocalControls();

		// Hashmap :
		objS_ENGINE_PARAMETERS_HASH_MAP = new HashMap<String, String>(Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER
																			+ Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER
																		+ Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER
																		+ Constants.bytS_OTHER_LOCAL_CONTROLS_NUMBER);
		Constants.setHashmap();

		// Preferences :
		strS_STRING_LOCAL_CONTROL_PREFERENCE_A = new String[Constants.bytS_STRING_LOCAL_CONTROLS_NUMBER];
		strS_STRING_GLOBAL_CONTROL_PREFERENCE_A = new String[Constants.bytS_STRING_GLOBAL_CONTROLS_NUMBER];
		strS_BOOLEAN_LOCAL_CONTROL_PREFERENCE_A = new String[Constants.bytS_BOOLEAN_LOCAL_CONTROLS_NUMBER];
		strS_BOOLEAN_GLOBAL_CONTROL_PREFERENCE_A = new String[Constants.bytS_BOOLEAN_GLOBAL_CONTROLS_NUMBER];
		strS_BYTE_LOCAL_CONTROL_PREFERENCE_A = new String[Constants.bytS_BYTE_LOCAL_CONTROLS_NUMBER];
		strS_BYTE_GLOBAL_CONTROL_PREFERENCE_A = new String[Constants.bytS_BYTE_GLOBAL_CONTROLS_NUMBER];
		Constants.setPreferences();
	}
}

/*
 * @(#)Constants.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
