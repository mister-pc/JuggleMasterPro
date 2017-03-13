/*
 * @(#)AboutJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.help;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenuItem;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

// import static java.lang.Math.*;
// import static java.lang.System.*;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class AboutJMenuItem extends JMenuItem implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public AboutJMenuItem(ControlJFrame objPcontrolJFrame) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGenabled = false;
		this.objGaboutJDialog = new AboutJDialog(this.objGcontrolJFrame);
		this.setContent();
		// this.objGcontrolJFrame.objGhelpExtendedJMenu.add(this);
		this.setOpaque(true);
		this.setFont(this.objGcontrolJFrame.getFont());
		this.addActionListener(this);
		this.setAccelerator(Constants.keyS_ABOUT);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("AboutJMenuItem.actionPerformed()");
		if (this.bolGenabled) {
			this.objGcontrolJFrame.doHidePopUps();
			final Insets objLinsets = this.objGaboutJDialog.getInsets();
			final int intLwidth = objLinsets.left + 362 + objLinsets.right;
			final int intLheight = objLinsets.top + 351 + objLinsets.bottom;
			this.objGcontrolJFrame.setWindowBounds(this.objGaboutJDialog, intLwidth, intLheight);
			this.objGaboutJDialog.setVisible(true);
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.setIcon(this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_ABOUT, 0));
		this.objGaboutJDialog.doLoadImages();
	}

	final private void setContent() {

		final String strLclosingDiv = "</div>";
		final String strLcopyright = "&copy;";
		final String strLorangeColorOpeningSpan = "<span style='color:#FF9900'>";
		final String strLredColorOpeningSpan = "<span style='color:#FF0000'>";
		final String strLwhiteColorOpeningSpan = "<span style='color:#FFFFFF'>";
		final String strLclosingSpan = "</span>";

		// Build version string :
		final char[] chrLversionNumberStringA = Constants.strS_ENGINE_VERSION_NUMBER.toCharArray();
		final StringBuilder objLversionStringBuilder =
														new StringBuilder(chrLversionNumberStringA.length + 2
																			* (strLorangeColorOpeningSpan.length() + strLclosingSpan.length()));
		for (final char chrLindexed : chrLversionNumberStringA) {
			final boolean bolLdot = chrLindexed == '.';
			if (bolLdot) {
				objLversionStringBuilder.append(strLorangeColorOpeningSpan);
			}
			objLversionStringBuilder.append(chrLindexed);
			if (bolLdot) {
				objLversionStringBuilder.append(strLclosingSpan);
			}
		}

		// Build background file string :
		String strLbackground = null;
		strLbackground =
							Strings.doConcat(	this.objGcontrolJFrame.getJuggleMasterPro().strS_CODE_BASE == null
																													? Strings.doConcat(	"file:///",
																																		new File(Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES])	.getAbsolutePath()
																																																				.replace(	'\\',
																																																							'/'))
																													: Strings.doConcat(	this.objGcontrolJFrame.getJuggleMasterPro().strS_CODE_BASE,
																																		Constants.strS_FILE_NAME_A[Constants.intS_FILE_FOLDER_IMAGES]),
												'/',
												Constants.strS_FILE_NAME_A[Constants.intS_FILE_IMAGE_ABOUT]);

		// Build HTML string :
		this.strS_HTML_FIRST_PART =
									Strings.doConcat(	Strings.strS_HTML_OPENING,
														"<head>",
														"<title>",
														Strings.strS_JUGGLE_MASTER,
														"</title>",
														"<style type='text/css'>\n",
														"<!--\n",
														"BODY { font-style: normal; font-family: Times New Roman, Times, serif; background-color: #DDDDDD; background-image: url(\"",
														strLbackground,
														"\"); background-repeat: no-repeat; color: #000000; white-space: nowrap; } ",
														"A { text-decoration: none; text-align: center; color: #000080; } A:hover { color:#FF9900; }\n",
														"-->\n",
														"</style>",
														"</head>",
														"<body>",
														"<div style='font-size: 5.0pt;'>",
														Strings.strS_HTML_BR,
														strLclosingDiv,
														"<div style='font-size: 32.0pt; vertical-align: bottom; '>&nbsp; &nbsp; &nbsp; &nbsp;",
														strLredColorOpeningSpan,
														'J',
														strLclosingSpan,
														strLwhiteColorOpeningSpan,
														"uggle",
														strLclosingSpan,
														'M',
														strLwhiteColorOpeningSpan,
														"aster&nbsp;",
														strLclosingSpan,
														strLorangeColorOpeningSpan,
														'P',
														strLclosingSpan,
														strLwhiteColorOpeningSpan,
														"ro&nbsp;",
														strLclosingSpan,
														objLversionStringBuilder,
														strLclosingDiv,
														"<div style='font-size: 14.0pt; text-align: center; '>",
														Strings.strS_HTML_BR,
														Strings.strS_HTML_BR,
														Strings.strS_HTML_BR,
														"<b>",
														strLcopyright,
														' ',
														Constants.strS_ENGINE_COPYRIGHT_YEARS,
														" <a href='mailto:contact@jugglemaster.fr?subject=[JuggleMaster Pro]: ' title='",
														Constants.strS_ENGINE_ARNAUD_BELO,
														"...'>",
														strLredColorOpeningSpan,
														'A',
														strLclosingSpan,
														"rnaud ",
														strLredColorOpeningSpan,
														'B',
														strLclosingSpan,
														"eLO",
														strLorangeColorOpeningSpan,
														".",
														strLclosingSpan,
														"</b></a> (contact",
														strLorangeColorOpeningSpan,
														"@",
														strLclosingSpan,
														"juggle",
														strLwhiteColorOpeningSpan,
														"master",
														strLclosingSpan,
														strLredColorOpeningSpan,
														".fr",
														strLclosingSpan,
														')',
														Strings.strS_HTML_BR,
														Strings.strS_HTML_BR,
														"<a href='",
														Strings.strS_JUGGLE_MASTER_URL,
														"' title='",
														Strings.strS_JUGGLE_MASTER,
														" Pro...' target='_blank'><b>",
														Strings.strS_JUGGLE_MASTER_URL,
														"</b></a>",
														Strings.strS_HTML_BR,
														Strings.strS_HTML_BR,
														strLclosingDiv,
														"<div style='font-size:10.0pt;text-align:center;'>",
														"<b>");
		this.strS_HTML_SECOND_PART =
										Strings.doConcat(	" :",
															Strings.strS_HTML_BR,
															Strings.strS_HTML_BR,
															Strings.strS_HTML_BR,
															Strings.strS_JUGGLE_MASTER,
															" Java 1.03 ",
															strLcopyright,
															" 1997-1999 Yuji KONISHI & Nobuhiko ASANUMA",
															Strings.strS_HTML_BR,
															Strings.strS_JUGGLE_MASTER,
															" X 0.42 ",
															strLcopyright,
															" 1996 <a href='mailto:emda@po.jah.ne.jp?subject=[JuggleMaster X]: ' title='Kazuyoshi MASUDA...'>Kazuyoshi MASUDA</a> (emda@po.jah.ne.jp)",
															Strings.strS_HTML_BR,
															Strings.strS_JUGGLE_MASTER,
															" 1.60 ",
															strLcopyright,
															" 1995-1996 <a href='mailto:GBA03100@nifty.ne.jp?subject=[JuggleMaster]: ' title='Ken MATSUOKA...'>Ken MATSUOKA</a> (GBA03100@nifty.ne.jp)</b>",
															strLclosingDiv,
															Strings.strS_HTML_BR,
															Strings.strS_HTML_BR,
															Strings.strS_HTML_BR,
															"<div style='font-size: 18.0pt; text-align: center;'><b> - ");
		this.strS_HTML_THIRD_PART = Strings.doConcat(" ! -</b>", strLclosingDiv, "</body>", Strings.strS_HTML_CLOSING);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setEnabled() {
		this.setEnabled(this.bolGenabled);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setLabel() {

		// Set menu item label :
		this.objGcontrolJFrame.setMenuItemLabel(this, Language.intS_MENU_ABOUT);
		this.objGcontrolJFrame.setMenuMnemonic(this, Language.intS_MENU_ABOUT);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_MENUS_TOOLTIPS)
																												? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_ABOUT_MENU)
																												: null);

		// Set editor pane :
		this.bolGenabled =
							this.objGaboutJDialog.setContent(Strings.doConcat(	this.strS_HTML_FIRST_PART,
																				"<span style='white-space: normal; '>",
																				this.objGcontrolJFrame.getLanguageString(	Language.intS_MESSAGE_SOFTWARE_TOKEN_IMPROVED_VERSION_OF_PREVIOUS_RELEASES,
																															Strings.doConcat(	Strings.strS_JUGGLE_MASTER,
																																				" Pro ",
																																				Constants.strS_ENGINE_VERSION_NUMBER)),

																				"</span>",
																				this.strS_HTML_SECOND_PART,
																				this.objGcontrolJFrame.getLanguageString(Language.intS_MESSAGE_HAVE_FUN),
																				this.strS_HTML_THIRD_PART));
	}

	private boolean				bolGenabled;

	final private AboutJDialog	objGaboutJDialog;

	final private ControlJFrame	objGcontrolJFrame;

	private String				strS_HTML_FIRST_PART;

	private String				strS_HTML_SECOND_PART;

	private String				strS_HTML_THIRD_PART;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)AboutJMenuItem.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
