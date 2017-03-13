package jugglemasterpro.control.io;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JToolTip;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.MenusTools;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class RecentFilesExtendedJMenu extends ExtendedJMenu {

	final private static long			serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final ArrayList<Boolean>	bolGnewListsAL;

	private final ArrayList<Boolean>	bolGsiteswapsAL;

	private final ArrayList<Boolean>	bolGstylesAL;

	private final ArrayList<ImageIcon>	imgGiconAL;
	private final ArrayList<String>		strGreferenceAL;

	private final ArrayList<String>		strGtitleAL;

	public RecentFilesExtendedJMenu(ControlJFrame objPcontrolJFrame) {
		super(objPcontrolJFrame, ExtendedJMenu.bytS_RECENT, Constants.intS_FILE_ICON_RECENT);
		this.strGreferenceAL = new ArrayList<String>(1);
		this.strGtitleAL = new ArrayList<String>(1);
		this.imgGiconAL = new ArrayList<ImageIcon>(1);
		this.bolGnewListsAL = new ArrayList<Boolean>(1);
		this.bolGsiteswapsAL = new ArrayList<Boolean>(1);
		this.bolGstylesAL = new ArrayList<Boolean>(1);

		this.setFont(this.objGcontrolJFrame.getFont());
		this.setOpaque(true);
	}

	// final public void doAddRecentFile(String strPfileName, String strPfileTitle, boolean bolPnewLists,
	// boolean bolPsiteswaps, boolean bolPstyles) {
	//
	// this.doAddRecentFile(
	// strPfileName,
	// strPfileTitle,
	// this.objGcontrolJFrame.getFileImageIcon(bolPnewLists, bolPsiteswaps, bolPstyles),
	// bolPnewLists,
	// bolPsiteswaps,
	// bolPstyles);
	// }
	//
	@Override final public JToolTip createToolTip() {
		return Tools.getJuggleToolTip(this.objGcontrolJFrame);
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPaction
	 */
	final public void doAddRecentFile(	String strPfileName,
										String strPfileTitle,
										ImageIcon icoP,
										boolean bolPnewLists,
										boolean bolPsiteswaps,
										boolean bolPstyles) {

		if (this.objGcontrolJFrame.getPatternsManager().bytGpatternsManagerType == Constants.bytS_MANAGER_FILES_PATTERNS) {
			this.strGreferenceAL.add(0, strPfileName);
			this.strGtitleAL.add(0, strPfileTitle);
			this.imgGiconAL.add(0, icoP);
			this.bolGnewListsAL.add(0, bolPnewLists);
			this.bolGsiteswapsAL.add(0, bolPsiteswaps);
			this.bolGstylesAL.add(0, bolPstyles);

			// Suppress multiple occurences of same files :
			int intLcomparedIndex = 0;
			while (intLcomparedIndex < this.strGreferenceAL.size()) {
				int intLcomparingIndex = intLcomparedIndex + 1;
				while (intLcomparingIndex < this.strGreferenceAL.size()) {
					if (this.strGreferenceAL.get(intLcomparingIndex).equals(this.strGreferenceAL.get(intLcomparedIndex))
						&& this.strGtitleAL.get(intLcomparingIndex).equals(this.strGtitleAL.get(intLcomparedIndex))
						&& this.bolGnewListsAL.get(intLcomparingIndex).equals(this.bolGnewListsAL.get(intLcomparedIndex))
						&& this.bolGsiteswapsAL.get(intLcomparingIndex).equals(this.bolGsiteswapsAL.get(intLcomparedIndex))
						&& this.bolGstylesAL.get(intLcomparingIndex).equals(this.bolGstylesAL.get(intLcomparedIndex))) {
						this.strGreferenceAL.remove(intLcomparingIndex);
						this.strGtitleAL.remove(intLcomparingIndex);
						this.imgGiconAL.remove(intLcomparingIndex);
						this.bolGnewListsAL.remove(intLcomparingIndex);
						this.bolGsiteswapsAL.remove(intLcomparingIndex);
						this.bolGstylesAL.remove(intLcomparingIndex);
					} else {
						intLcomparingIndex++;
					}
				}
				intLcomparedIndex++;
			}

			// Build the recent file menu :
			int intLrecentFilesNumber = this.strGreferenceAL.size();
			if (intLrecentFilesNumber > Constants.bytS_UNCLASS_MAXIMUM_RECENT_FILES_NUMBER) {
				for (int intLindex = Constants.bytS_UNCLASS_MAXIMUM_RECENT_FILES_NUMBER; intLindex < intLrecentFilesNumber; ++intLindex) {
					this.strGreferenceAL.remove(intLindex);
					this.strGtitleAL.remove(intLindex);
					this.imgGiconAL.remove(intLindex);
					this.bolGnewListsAL.remove(intLindex);
					this.bolGsiteswapsAL.remove(intLindex);
					this.bolGstylesAL.remove(intLindex);
				}
				intLrecentFilesNumber = Constants.bytS_UNCLASS_MAXIMUM_RECENT_FILES_NUMBER;
			}
			this.removeAll();
			for (int intLrecentFileIndex = 0; intLrecentFileIndex < intLrecentFilesNumber; ++intLrecentFileIndex) {
				final PatternsFileJCheckBoxMenuItem objLrecentPatternsFileJCheckBoxMenuItem =
																								new PatternsFileJCheckBoxMenuItem(	this.objGcontrolJFrame,
																																	this.strGtitleAL.get(intLrecentFileIndex),
																																	this.strGtitleAL.get(intLrecentFileIndex),
																																	this.strGreferenceAL.get(intLrecentFileIndex),
																																	this.imgGiconAL.get(intLrecentFileIndex),
																																	this.bolGnewListsAL.get(intLrecentFileIndex),
																																	this.bolGsiteswapsAL.get(intLrecentFileIndex),
																																	this.bolGstylesAL.get(intLrecentFileIndex),
																																	intLrecentFileIndex);
				MenusTools.setNoMnemonicLabel(objLrecentPatternsFileJCheckBoxMenuItem, this.strGtitleAL.get(intLrecentFileIndex));
				objLrecentPatternsFileJCheckBoxMenuItem.setIcon(this.imgGiconAL.get(intLrecentFileIndex));
				objLrecentPatternsFileJCheckBoxMenuItem.setToolTipText(this.strGreferenceAL.get(intLrecentFileIndex));
				this.add(objLrecentPatternsFileJCheckBoxMenuItem);
			}
		}
	}

	final public void setEnabled() {
		this.setEnabled(this.strGreferenceAL.size() > 0);
	}
}
