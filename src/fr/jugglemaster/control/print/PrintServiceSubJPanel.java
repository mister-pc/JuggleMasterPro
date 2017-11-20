package fr.jugglemaster.control.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.print.ServiceUIFactory;
import javax.print.attribute.Attribute;
import javax.print.attribute.standard.PrinterInfo;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.print.attribute.standard.PrinterMakeAndModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.control.util.ExtendedJButton;
import fr.jugglemaster.control.util.ExtendedJLabel;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class PrintServiceSubJPanel extends JPanel implements ActionListener, ItemListener, PopupMenuListener {

	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean					bolGserviceChanged;

	private final JLabel			objGinfoJLabel;

	private final JComboBox<String>	objGnameJComboBox;

	private final PrintJDialog		objGprintJDialog;

	private final ExtendedJButton	objGpropertiesJButton;

	private ServiceUIFactory		objGserviceUIFactory;

	private final JLabel			objGstatusJLabel;
	private final JLabel			objGtypeJLabel;

	public PrintServiceSubJPanel(PrintJDialog objPprintJDialog) {

		this.objGprintJDialog = objPprintJDialog;
		this.bolGserviceChanged = false;
		this.objGserviceUIFactory = objPprintJDialog.getPrintService().getServiceUIFactory();
		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		this.setBorder(Tools.getTitledBorder(Tools.getLocaleString("border.printservice"), this.objGprintJDialog.getControlJFrame().getFont()));

		// Widgets :
		final String[] strLprintServiceA = new String[this.objGprintJDialog.getPrintServices().length];
		for (int intLprintServiceIndex = 0; intLprintServiceIndex < strLprintServiceA.length; intLprintServiceIndex++) {
			strLprintServiceA[intLprintServiceIndex] = this.objGprintJDialog.getPrintServices()[intLprintServiceIndex].getName();
		}
		this.objGnameJComboBox = new JComboBox<String>(strLprintServiceA);
		this.objGnameJComboBox.setSelectedIndex(this.objGprintJDialog.getDefaultServiceIndex());
		this.objGnameJComboBox.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.objGnameJComboBox.setOpaque(true);
		this.objGnameJComboBox.addItemListener(this);
		this.objGnameJComboBox.addPopupMenuListener(this);
		final JLabel objLnameJLabel = new JLabel(Tools.getLocaleString("label.psname"), SwingConstants.TRAILING);
		objLnameJLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		objLnameJLabel.setDisplayedMnemonic(Tools.getMnemonicChar("label.psname"));
		objLnameJLabel.setLabelFor(this.objGnameJComboBox);

		this.objGpropertiesJButton = new ExtendedJButton(this.objGprintJDialog.getControlJFrame(), Tools.getLocaleString("button.properties"));
		this.objGpropertiesJButton.setMnemonic(Tools.getMnemonicChar("button.properties"));
		this.objGpropertiesJButton.addActionListener(this);

		this.objGstatusJLabel = new ExtendedJLabel(this.objGprintJDialog.getControlJFrame());
		this.objGstatusJLabel.setLabelFor(null);
		this.objGtypeJLabel = new ExtendedJLabel(this.objGprintJDialog.getControlJFrame());
		this.objGtypeJLabel.setLabelFor(null);
		this.objGinfoJLabel = new ExtendedJLabel(this.objGprintJDialog.getControlJFrame());
		this.objGinfoJLabel.setLabelFor(null);

		// First column :
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
		this.add(objLnameJLabel, objLextendedGridBagConstraints);
		JLabel objLjLabel = new JLabel(Tools.getLocaleString("label.status"), SwingConstants.TRAILING);
		objLjLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.add(objLjLabel, objLextendedGridBagConstraints);
		objLjLabel = new JLabel(Tools.getLocaleString("label.pstype"), SwingConstants.TRAILING);
		objLjLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.add(objLjLabel, objLextendedGridBagConstraints);
		objLjLabel = new JLabel(Tools.getLocaleString("label.info"), SwingConstants.TRAILING);
		objLjLabel.setFont(this.objGprintJDialog.getControlJFrame().getFont());
		this.add(objLjLabel, objLextendedGridBagConstraints);

		// Second & third columns :
		objLextendedGridBagConstraints.setGridLocation(2, 0);
		this.add(this.objGpropertiesJButton, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setFilling(GridBagConstraints.BOTH, 1.0F, 1.0F);
		objLextendedGridBagConstraints.setGridLocation(1, 0);
		this.add(this.objGnameJComboBox, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridBounds(1, 1, 2, 1);
		this.add(this.objGstatusJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, 2);
		this.add(this.objGtypeJLabel, objLextendedGridBagConstraints);
		objLextendedGridBagConstraints.setGridLocation(1, 3);
		this.add(this.objGinfoJLabel, objLextendedGridBagConstraints);
	}

	final public void actionPerformed(ActionEvent objPactionEvent) {
		final Object objLsourceObject = objPactionEvent.getSource();

		if (objLsourceObject == this.objGpropertiesJButton) {
			if (this.objGserviceUIFactory != null) {
				final JDialog objLjDialog = (JDialog) this.objGserviceUIFactory.getUI(ServiceUIFactory.MAIN_UIROLE, ServiceUIFactory.JDIALOG_UI);

				if (objLjDialog != null) {
					objLjDialog.setVisible(true);
					objLjDialog.dispose();
				} else {
					this.objGpropertiesJButton.setEnabled(false);
				}
			}
		}
	}

	final public void itemStateChanged(ItemEvent objPitemEvent) {
		if (objPitemEvent.getStateChange() == ItemEvent.SELECTED) {
			final int intLindex = this.objGnameJComboBox.getSelectedIndex();

			if (intLindex >= 0 && intLindex < this.objGprintJDialog.getPrintServices().length) {
				if (!this.objGprintJDialog.getPrintServices()[intLindex].equals(this.objGprintJDialog.getPrintService())) {
					// TODO : pourquoi ça ???
					// PrintService objLprintService = this.objGprintJDialog.getPrintService();
					// objLprintService = this.objGprintJDialog.getPrintServices()[intLindex];
					this.objGserviceUIFactory = this.objGprintJDialog.getPrintService().getServiceUIFactory();
					this.bolGserviceChanged = true;
				}
			}
		}
	}

	final public void popupMenuCanceled(PopupMenuEvent objPpopupMenuEvent) {}

	final public void popupMenuWillBecomeInvisible(PopupMenuEvent objPpopupMenuEvent) {
		if (this.bolGserviceChanged) {
			this.bolGserviceChanged = false;
			this.objGprintJDialog.updatePanels();
		}
	}

	final public void popupMenuWillBecomeVisible(PopupMenuEvent objPpopupMenuEvent) {
		this.bolGserviceChanged = false;
	}

	final public void setValues() {

		final Attribute objLtypeAttribute = this.objGprintJDialog.getPrintService().getAttribute(PrinterMakeAndModel.class);
		this.objGtypeJLabel.setText(objLtypeAttribute != null ? objLtypeAttribute.toString() : null);

		final Attribute objLstatusAttribute = this.objGprintJDialog.getPrintService().getAttribute(PrinterIsAcceptingJobs.class);
		this.objGstatusJLabel.setText(objLstatusAttribute != null ? Tools.getLocaleString(objLstatusAttribute.toString()) : null);

		final Attribute objLinfoAttribute = this.objGprintJDialog.getPrintService().getAttribute(PrinterInfo.class);
		this.objGinfoJLabel.setText(objLinfoAttribute != null ? objLinfoAttribute.toString() : null);
		this.objGpropertiesJButton.setEnabled(this.objGserviceUIFactory != null);
	}
}
