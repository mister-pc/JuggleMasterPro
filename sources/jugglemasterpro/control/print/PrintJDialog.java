/*
 * Copyright 2000-2005 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package jugglemasterpro.control.print;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.PrintService;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Fidelity;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ExtendedJButton;
import jugglemasterpro.control.window.JDialogWindowListener;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;
import jugglemasterpro.util.Tools;

final public class PrintJDialog extends JDialog implements ActionListener {

	final private static long					serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	private boolean								bolGvalidated;

	private final int							intGdefaultServiceIndex;

	private final ControlJFrame					objGcontrolJFrame;

	private final HashPrintRequestAttributeSet	objGhashPrintRequestAttributeSet;

	private final ExtendedJButton				objGoKJButton;

	private final PrintAppearanceJPanel			objGprintAppearanceJPanel;

	private final PrintGeneralJPanel			objGprintGeneralJPanel;

	private final PrintPageSetupJPanel			objGprintPageSetupJPanel;

	private final PrintService					objGprintService;
	private final PrintService[]				objGprintServiceA;

	public PrintJDialog(ControlJFrame objPcontrolJFrame, PrintService[] objPprintServiceA, int intPdefaultServiceIndex) {

		// Parse parameters :
		super(objPcontrolJFrame, Strings.doConcat(Tools.getLocaleString("dialog.printtitle"), ' ', Strings.strS_ELLIPSIS), true);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGprintServiceA = objPprintServiceA;
		this.intGdefaultServiceIndex = intPdefaultServiceIndex;
		this.objGprintService = objPprintServiceA[intPdefaultServiceIndex];
		this.bolGvalidated = true;
		this.objGhashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();

		// Set icon :
		final Image imgL = this.objGcontrolJFrame.getJuggleMasterPro().getImage(Constants.intS_FILE_ICON_PRINT, 0);
		this.setIconImage(imgL != null ? imgL : this.objGcontrolJFrame.getJuggleMasterPro().getFrame().imgGjmp);

		// Build tabbed panel :
		this.setLayout(new BorderLayout());
		final JTabbedPane objLjTabbedPane = new JTabbedPane();
		objLjTabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.objGprintGeneralJPanel = new PrintGeneralJPanel(this);
		objLjTabbedPane.add(Strings.doConcat(Strings.strS_SPACE, Tools.getLocaleString("tab.general"), Strings.strS_SPACE),
							this.objGprintGeneralJPanel);
		this.objGprintPageSetupJPanel = new PrintPageSetupJPanel(this, objPcontrolJFrame);
		objLjTabbedPane.add(Strings.doConcat(Strings.strS_SPACE, Tools.getLocaleString("tab.pagesetup"), Strings.strS_SPACE),
							this.objGprintPageSetupJPanel);
		this.objGprintAppearanceJPanel = new PrintAppearanceJPanel(this, objPcontrolJFrame);
		objLjTabbedPane.add(Strings.doConcat(Strings.strS_SPACE, Tools.getLocaleString("tab.appearance"), Strings.strS_SPACE),
							this.objGprintAppearanceJPanel);
		this.add(objLjTabbedPane, BorderLayout.CENTER);
		this.updatePanels();

		// Add OK and cancel buttons :
		final JPanel objLbuttonsJPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		objLbuttonsJPanel.setOpaque(true);
		this.objGoKJButton = new ExtendedJButton(this.objGcontrolJFrame, Tools.getLocaleString("button.print"));
		this.objGoKJButton.addActionListener(this);

		objLbuttonsJPanel.add(this.objGoKJButton);
		this.getRootPane().setDefaultButton(this.objGoKJButton);
		final ExtendedJButton objLcancelJButton = new ExtendedJButton(this.objGcontrolJFrame, Tools.getLocaleString("button.cancel"));
		objLcancelJButton.addActionListener(this);
		objLbuttonsJPanel.add(objLcancelJButton);
		this.add(objLbuttonsJPanel, BorderLayout.SOUTH);

		this.addWindowListener(new JDialogWindowListener(this.objGcontrolJFrame, this, false));
		this.setResizable(false);
		this.validate();
		this.pack();
		this.objGcontrolJFrame.setWindowBounds(this, this.objGcontrolJFrame, true);
	}

	@Override final public void actionPerformed(ActionEvent objPactionEvent) {
		this.bolGvalidated = objPactionEvent.getSource() == this.objGoKJButton;
		this.dispose();
	}

	final public HashPrintRequestAttributeSet getAttributes() {
		return this.objGhashPrintRequestAttributeSet;
	}

	final public ControlJFrame getControlJFrame() {
		return this.objGcontrolJFrame;
	}

	final public int getDefaultServiceIndex() {
		return this.intGdefaultServiceIndex;
	}

	final public PrintService getPrintService() {

		return this.bolGvalidated ? this.objGprintService : null;
	}

	final public PrintService[] getPrintServices() {
		return this.objGprintServiceA;
	}

	final private void removeUnsupportedAttributes() {

		final javax.print.attribute.AttributeSet objLunsupportedAttributeSet =
																				this.objGprintService.getUnsupportedAttributes(	null,
																																this.objGhashPrintRequestAttributeSet);
		if (objLunsupportedAttributeSet != null) {
			final Attribute[] objLattributeA = objLunsupportedAttributeSet.toArray();
			for (final Attribute objLattribute : objLattributeA) {
				final Class<? extends Attribute> objLcategoryClass = objLattribute.getCategory();
				if (this.objGprintService.isAttributeCategorySupported(objLcategoryClass)) {
					final Attribute objLdefaultAttribute = (Attribute) this.objGprintService.getDefaultAttributeValue(objLcategoryClass);
					if (objLdefaultAttribute != null) {
						this.objGhashPrintRequestAttributeSet.add(objLdefaultAttribute);
					} else {
						this.objGhashPrintRequestAttributeSet.remove(objLcategoryClass);
					}
				} else {
					this.objGhashPrintRequestAttributeSet.remove(objLcategoryClass);
				}
			}
		}
	}

	final public void setVisible() {

		this.setVisible(true);
		if (this.bolGvalidated) {
			final Fidelity objLfidelity = (Fidelity) this.objGhashPrintRequestAttributeSet.get(Fidelity.class);
			if (objLfidelity != null && objLfidelity == Fidelity.FIDELITY_TRUE) {
				this.removeUnsupportedAttributes();
			}
		}
	}

	/**
	 * Updates each of the top level panels
	 */
	final public void updatePanels() {
		this.objGprintGeneralJPanel.setValues();
		this.objGprintPageSetupJPanel.setValues();
		this.objGprintAppearanceJPanel.setValues();
	}
}
