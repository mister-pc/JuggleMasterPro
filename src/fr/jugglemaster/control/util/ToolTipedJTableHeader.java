package fr.jugglemaster.control.util;

import java.awt.event.MouseEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.util.Constants;

final public class ToolTipedJTableHeader extends JTableHeader {

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	int[]						intGtoolTipA;
	final private ControlJFrame	objGcontrolJFrame;

	public ToolTipedJTableHeader(ControlJFrame objPcontrolJFrame, TableColumnModel objPtableColumnModel) {
		this(objPcontrolJFrame, objPtableColumnModel, null);
	}

	public ToolTipedJTableHeader(ControlJFrame objPcontrolJFrame, TableColumnModel objPtableColumnModel, int[] intPtoolipLanguageIndexA) {
		super(objPtableColumnModel);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.intGtoolTipA = intPtoolipLanguageIndexA;
	}

	@Override final public String getToolTipText(MouseEvent objPmouseEvent) {
		final int intLcolumnIndex = this.columnAtPoint(objPmouseEvent.getPoint());
		final int intLmodelColumnIndex = this.getTable().convertColumnIndexToModel(intLcolumnIndex);
		String strLtooltip = null;
		try {
			strLtooltip = this.objGcontrolJFrame.getLanguageString(this.intGtoolTipA[intLmodelColumnIndex]);
		} catch (final Throwable objPthrowable) {
			strLtooltip = null;
		}
		if (strLtooltip == null) {
			// strLtooltip = super.getToolTipText(objPmouseEvent);
		}
		return strLtooltip;
	}

	final public void setToolTipsIndexes(int[] intPtoolTipA) {
		this.intGtoolTipA = intPtoolTipA;
	}
}
