package fr.jugglemaster.control.io;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;

final public class ExtendedFileView extends FileView {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private final ImageIcon		icoGjap;
	private final ImageIcon		icoGjm;
	private final ImageIcon		icoGjmp;
	private final ImageIcon		imgGconfiguration;
	private final ImageIcon		imgGimage;

	public ExtendedFileView(ControlJFrame objPcontrolJFrame) {
		this.icoGjm = objPcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_JM, 0);
		this.icoGjmp = objPcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_JMP, 0);
		this.icoGjap = objPcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_JAP, 0);
		this.imgGconfiguration = objPcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_CONFIGURATION, 0);
		this.imgGimage = objPcontrolJFrame.getJuggleMasterPro().getImageIcon(Constants.intS_FILE_ICON_IMAGE, 0);
	}

	@Override final public Icon getIcon(File objPfile) {
		if (!objPfile.isDirectory()) {
			final String strLfileName = objPfile.getName().toLowerCase();
			if (strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JMP]))) {
				return this.icoGjmp;
			} else if (strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JM]))) {
				return this.icoGjm;
			} else if (strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JAP]))) {
				return this.icoGjap;
			} else if (strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_INI]))
						|| strLfileName.endsWith(".dat") || strLfileName.endsWith(".conf")) {
				return this.imgGconfiguration;
			} else if (strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_PNG]))
						|| strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPG]))
						|| strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_JPEG]))
						|| strLfileName.endsWith(Strings.doConcat('.', Constants.strS_FILE_EXTENSION_A[Constants.bytS_EXTENSION_GIF]))) {
				return this.imgGimage;
			}
		}
		return super.getIcon(objPfile);
	}
}
