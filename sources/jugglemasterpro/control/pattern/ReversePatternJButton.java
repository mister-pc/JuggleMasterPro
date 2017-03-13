/*
 * @(#)ReversePatternJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.control.pattern;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJButton;
import jugglemasterpro.user.Language;
import jugglemasterpro.user.Preferences;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ReversePatternJButton extends ExtendedJButton implements ActionListener {

	/**
	 * Constructs
	 * 
	 * @param objPcontrolJFrame
	 */
	public ReversePatternJButton(ControlJFrame objPcontrolJFrame) {

		super(objPcontrolJFrame, 0);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.bolGbuttonReversingPattern = true;
		this.addActionListener(this);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPactionEvent
	 */
	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		Tools.debug("ReversePatternJButton.actionPerformed(): PatternActions.doReversePattern()");
		PatternActions.doReversePattern(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void doLoadImages() {
		this.icoGreversePattern =
									this.objGcontrolJFrame	.getJuggleMasterPro()
															.getImageIcon(	Constants.intS_FILE_ICON_REVERSE_PATTERN,
																			2,
																			this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_REVERSE_PATTERN));
		this.icoGreversePatternBW =
									this.objGcontrolJFrame	.getJuggleMasterPro()
															.getImageIcon(	Constants.intS_FILE_ICON_REVERSE_PATTERN_BW,
																			1,
																			this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_REVERSE_PATTERN));
		this.icoGforwardPattern =
									this.objGcontrolJFrame	.getJuggleMasterPro()
															.getImageIcon(	Constants.intS_FILE_ICON_FORWARD_PATTERN,
																			2,
																			this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_FORWARD_PATTERN));
		this.icoGforwardPatternBW =
									this.objGcontrolJFrame	.getJuggleMasterPro()
															.getImageIcon(	Constants.intS_FILE_ICON_FORWARD_PATTERN_BW,
																			1,
																			this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_FORWARD_PATTERN));
		this.icoGdoNotReversePattern =
										this.objGcontrolJFrame.getJuggleMasterPro().getImageIcon(	Constants.intS_FILE_ICON_DO_NOT_REVERSE_PATTERN,
																									0,
																									"DoNot");
	}

	final public void doSwitchReversing() {
		this.bolGbuttonReversingPattern = !this.bolGbuttonReversingPattern;
	}

	final public boolean isReversingPattern() {
		return this.bolGbuttonReversingPattern;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public void setImage() {

		if (this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_MIRROR)
			&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
			&& this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE) && this.bolGbuttonReversingPattern) {
			this.bolGbuttonReversingPattern = false;
		}
		if (!this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_MIRROR)
			&& !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
			&& !this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_STYLE) && !this.bolGbuttonReversingPattern) {
			this.bolGbuttonReversingPattern = true;
		}

		Tools.setIcons(	this,
						this.bolGbuttonReversingPattern ? this.icoGreversePatternBW : this.icoGforwardPatternBW,
						this.bolGbuttonReversingPattern ? this.icoGreversePattern : this.icoGforwardPattern,
						this.icoGdoNotReversePattern);
	}

	final public void setToolTipText() {

		final boolean bolLedition = this.objGcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_EDITION);
		this.setToolTipText(Preferences.getGlobalBooleanPreference(Constants.bytS_BOOLEAN_GLOBAL_BUTTONS_TOOLTIPS) && bolLedition
																																	? this.bolGbuttonReversingPattern
																																										? this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_REVERSE_PATTERN)
																																										: this.objGcontrolJFrame.getLanguageString(Language.intS_TOOLTIP_FORWARD_PATTERN)
																																	: null);
	}

	private boolean				bolGbuttonReversingPattern;

	private ImageIcon			icoGdoNotReversePattern;
	private ImageIcon			icoGforwardPattern;

	private ImageIcon			icoGforwardPatternBW;

	private ImageIcon			icoGreversePattern;

	private ImageIcon			icoGreversePatternBW;

	final private ControlJFrame	objGcontrolJFrame;

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ReversePatternJButton.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
