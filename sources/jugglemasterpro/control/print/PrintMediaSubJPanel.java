package jugglemasterpro.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.MediaTray;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import jugglemasterpro.control.util.ExtendedGridBagConstraints;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;
import sun.print.SunAlternateMedia;

final public class PrintMediaSubJPanel extends JPanel implements ItemListener {

	final private static long				serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final ArrayList<MediaSizeName>	objGmediaSizeNameAL;

	private final PrintJDialog				objGprintJDialog;

	private final PrintMarginsSubJPanel		objGprintMarginsSubJPanel;
	private final JComboBox<String>			objGsizeJComboBox;
	private final JLabel					objGsizeJLabel;
	private final JComboBox<String>			objGsourceJComboBox;
	private final JLabel					objGsourceJLabel;
	private final ArrayList<MediaTray>		objGsourceMediaTrayAL;

	public PrintMediaSubJPanel(PrintJDialog objPprintJDialog, PrintMarginsSubJPanel objPprintMarginsSubJPanel) {

		this.objGprintJDialog = objPprintJDialog;
		this.objGprintMarginsSubJPanel = objPprintMarginsSubJPanel;
		this.objGmediaSizeNameAL = new ArrayList<MediaSizeName>();
		this.objGsourceMediaTrayAL = new ArrayList<MediaTray>();

		// Create widgets :
		this.objGsizeJComboBox = new JComboBox<String>();
		this.objGsizeJComboBox.setOpaque(true);
		this.objGsizeJComboBox.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGsizeJLabel = new JLabel(Tools.getLocaleString("label.size"), SwingConstants.TRAILING);
		this.objGsizeJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.size"));
		this.objGsizeJLabel.setLabelFor(this.objGsizeJComboBox);
		this.objGsizeJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGsizeJLabel.setOpaque(true);

		this.objGsourceJComboBox = new JComboBox<String>();
		this.objGsourceJComboBox.setOpaque(true);
		this.objGsourceJComboBox.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGsourceJLabel = new JLabel(Tools.getLocaleString("label.source"), SwingConstants.TRAILING);
		this.objGsourceJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.source"));
		this.objGsourceJLabel.setLabelFor(this.objGsourceJComboBox);
		this.objGsourceJLabel.setOpaque(true);
		this.objGsourceJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());

		// Add widgets :
		this.setOpaque(true);
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.media"), this.objGprintJDialog.getControlJFrame().getFont()));
		this.setLayout(new GridBagLayout());
		final ExtendedGridBagConstraints objLextendedGridBagConstraints =
																			new ExtendedGridBagConstraints(	0,
																											GridBagConstraints.RELATIVE,
																											1,
																											1,
																											GridBagConstraints.CENTER,
																											3,
																											3,
																											6,
																											6,
																											GridBagConstraints.VERTICAL,
																											0.0F,
																											1.0F);
		this.add(this.objGsizeJLabel, objLextendedGridBagConstraints);
		this.add(this.objGsourceJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, GridBagConstraints.RELATIVE);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.BOTH, 1.0F, 1.0F);
		this.add(this.objGsizeJComboBox, objLextendedGridBagConstraints);
		this.add(this.objGsourceJComboBox, objLextendedGridBagConstraints);
	}

	@Override final public void itemStateChanged(ItemEvent objPitemEvent) {
		final Object objLsourceObject = objPitemEvent.getSource();

		if (objPitemEvent.getStateChange() == ItemEvent.SELECTED) {
			if (objLsourceObject == this.objGsizeJComboBox) {
				final int intLselectedIndex = this.objGsizeJComboBox.getSelectedIndex();

				if ((intLselectedIndex >= 0) && (intLselectedIndex < this.objGmediaSizeNameAL.size())) {
					if (this.objGsourceJComboBox.getItemCount() > 1 && this.objGsourceJComboBox.getSelectedIndex() >= 1) {
						this.objGprintJDialog	.getAttributes()
												.add(new SunAlternateMedia(this.objGsourceMediaTrayAL.get(this.objGsourceJComboBox.getSelectedIndex() - 1)));
					}
					this.objGprintJDialog.getAttributes().add(this.objGmediaSizeNameAL.get(intLselectedIndex));
				}
			} else if (objLsourceObject == this.objGsourceJComboBox) {
				final int intLselectedIndex = this.objGsourceJComboBox.getSelectedIndex();

				if (intLselectedIndex >= 1 && intLselectedIndex < this.objGsourceMediaTrayAL.size() + 1) {
					this.objGprintJDialog.getAttributes().remove(SunAlternateMedia.class);
					final MediaTray objLmediaTray = this.objGsourceMediaTrayAL.get(intLselectedIndex - 1);
					final Media objLmedia = (Media) this.objGprintJDialog.getAttributes().get(Media.class);
					if (objLmedia == null || objLmedia instanceof MediaTray) {
						this.objGprintJDialog.getAttributes().add(objLmediaTray);
					} else if (objLmedia instanceof MediaSizeName) {
						final Media objLdefaultMedia = (Media) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(Media.class);
						if (objLdefaultMedia instanceof MediaSizeName && objLdefaultMedia.equals(objLmedia)) {
							this.objGprintJDialog.getAttributes().add(objLmediaTray);
						} else {
							// Non-default paper size :
							this.objGprintJDialog.getAttributes().add(new SunAlternateMedia(objLmediaTray));
						}
					}
				} else if (intLselectedIndex == 0) {
					this.objGprintJDialog.getAttributes().remove(SunAlternateMedia.class);
					if (this.objGsizeJComboBox.getItemCount() > 0) {
						this.objGprintJDialog.getAttributes().add(this.objGmediaSizeNameAL.get(this.objGsizeJComboBox.getSelectedIndex()));
					}
				}
			}
			// orientation affects display of margins.
			if (this.objGprintMarginsSubJPanel != null) {
				this.objGprintMarginsSubJPanel.setValues();
			}
		}
	}

	final public void setValues() {

		// Set enabled values :
		boolean objLmediaEnabled = false;
		this.objGsizeJComboBox.removeItemListener(this);
		this.objGsizeJComboBox.removeAllItems();
		this.objGsourceJComboBox.removeItemListener(this);
		this.objGsourceJComboBox.removeAllItems();
		this.objGsourceJComboBox.addItem(Tools.getMediaName("auto-select"));

		this.objGmediaSizeNameAL.clear();
		this.objGsourceMediaTrayAL.clear();

		if (this.objGprintJDialog.getPrintService().isAttributeCategorySupported(Media.class)) {
			objLmediaEnabled = true;

			final Object objLvaluesObject =
											this.objGprintJDialog	.getPrintService()
																	.getSupportedAttributeValues(	Media.class,
																									null,
																									this.objGprintJDialog.getAttributes());

			if (objLvaluesObject instanceof Media[]) {
				for (final Media objLmedia : (Media[]) objLvaluesObject) {
					if (objLmedia instanceof MediaSizeName) {
						this.objGmediaSizeNameAL.add((MediaSizeName) objLmedia);
						this.objGsizeJComboBox.addItem(Tools.getMediaName(objLmedia.toString()));
					} else if (objLmedia instanceof MediaTray) {
						this.objGsourceMediaTrayAL.add((MediaTray) objLmedia);
						this.objGsourceJComboBox.addItem(Tools.getMediaName(objLmedia.toString()));
					}
				}
			}
		}

		final boolean bolLmediaSizeEnabled = (objLmediaEnabled && (this.objGmediaSizeNameAL.size() > 0));
		this.objGsizeJLabel.setEnabled(bolLmediaSizeEnabled);
		this.objGsizeJComboBox.setEnabled(bolLmediaSizeEnabled);
		this.objGsourceJComboBox.setEnabled(objLmediaEnabled);

		// Set selected values :
		if (objLmediaEnabled) {

			Media objLmedia = (Media) this.objGprintJDialog.getAttributes().get(Media.class);

			// initialize size selection to default
			final Media objLdefaultMedia = (Media) this.objGprintJDialog.getPrintService().getDefaultAttributeValue(Media.class);
			if (objLdefaultMedia instanceof MediaSizeName) {
				this.objGsizeJComboBox.setSelectedIndex(this.objGmediaSizeNameAL.size() > 0 ? this.objGmediaSizeNameAL.indexOf(objLdefaultMedia)
																							: Constants.bytS_UNCLASS_NO_VALUE);
			}

			if (objLmedia == null
				|| !this.objGprintJDialog.getPrintService().isAttributeValueSupported(objLmedia, null, this.objGprintJDialog.getAttributes())) {
				objLmedia = objLdefaultMedia;
				if (objLmedia == null) {
					if (this.objGmediaSizeNameAL.size() > 0) {
						objLmedia = this.objGmediaSizeNameAL.get(0);
					}
				}
				if (objLmedia != null) {
					this.objGprintJDialog.getAttributes().add(objLmedia);
				}
			}
			if (objLmedia != null) {
				if (objLmedia instanceof MediaSizeName) {
					this.objGsizeJComboBox.setSelectedIndex(this.objGmediaSizeNameAL.indexOf(objLmedia));
				} else if (objLmedia instanceof MediaTray) {
					this.objGsourceJComboBox.setSelectedIndex(this.objGsourceMediaTrayAL.indexOf(objLmedia) + 1);
				}
			} else {
				this.objGsizeJComboBox.setSelectedIndex(this.objGmediaSizeNameAL.size() > 0 ? 0 : Constants.bytS_UNCLASS_NO_VALUE);
				this.objGsourceJComboBox.setSelectedIndex(0);
			}

			final SunAlternateMedia objLsunAlternateMedia = (SunAlternateMedia) this.objGprintJDialog.getAttributes().get(SunAlternateMedia.class);
			if (objLsunAlternateMedia != null) {
				final Media objLalternateMedia = objLsunAlternateMedia.getMedia();
				if (objLalternateMedia instanceof MediaTray) {
					this.objGsourceJComboBox.setSelectedIndex(this.objGsourceMediaTrayAL.indexOf(objLalternateMedia) + 1);
				}
			}

			int intLselectedIndex = this.objGsizeJComboBox.getSelectedIndex();
			if (intLselectedIndex >= 0 && intLselectedIndex < this.objGmediaSizeNameAL.size()) {
				this.objGprintJDialog.getAttributes().add(this.objGmediaSizeNameAL.get(intLselectedIndex));
			}

			intLselectedIndex = this.objGsourceJComboBox.getSelectedIndex();
			if (intLselectedIndex >= 1 && intLselectedIndex < (this.objGsourceMediaTrayAL.size() + 1)) {
				final MediaTray objLmediaTray = this.objGsourceMediaTrayAL.get(intLselectedIndex - 1);
				this.objGprintJDialog.getAttributes().add(objLmedia instanceof MediaTray ? objLmediaTray : new SunAlternateMedia(objLmediaTray));
			}
		}
		this.objGsizeJComboBox.addItemListener(this);
		this.objGsourceJComboBox.addItemListener(this);
	}
}
