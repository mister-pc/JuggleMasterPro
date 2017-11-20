package fr.jugglemaster.control.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import fr.jugglemaster.control.data.DataJFrame;
import fr.jugglemaster.control.data.DataJTable;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class ColumnFitAdapter extends MouseAdapter implements TableColumnModelListener, Runnable {

	@SuppressWarnings("unused")
	final private static long		serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
	private boolean					bolGthread;
	private long					lngGcurrentTimeMilliSeconds;
	private final DataJFrame	objGdataJFrame;
	private final DataJTable	objGdataJTable;
	private JScrollPane				objGjScrollPane;

	public ColumnFitAdapter(DataJFrame objPdataJFrame, DataJTable objPdataJTable) {
		this.objGdataJFrame = objPdataJFrame;
		this.objGdataJTable = objPdataJTable;
		this.lngGcurrentTimeMilliSeconds = 0;
		this.bolGthread = false;
	}

	@Override final public void columnAdded(TableColumnModelEvent objPtableColumnModelEvent) {}

	@Override final public void columnMarginChanged(ChangeEvent objPchangeEvent) {
		new Thread(this).start();
	}

	@Override final public void columnMoved(TableColumnModelEvent objPtableColumnModelEvent) {}

	@Override final public void columnRemoved(TableColumnModelEvent objPtableColumnModelEvent) {}

	@Override final public void columnSelectionChanged(ListSelectionEvent objPtableColumnModelEvent) {}

	final private TableColumn getResizingColumn(JTableHeader objLjTableHeader, Point objPpoint) {
		return this.getResizingColumn(objLjTableHeader, objPpoint, objLjTableHeader.columnAtPoint(objPpoint));
	}

	final private TableColumn getResizingColumn(JTableHeader objLjTableHeader, Point objPoint, int intPcolumnIndex) {
		if (intPcolumnIndex == -1) {
			return null;
		}
		final Rectangle objLrectangle = objLjTableHeader.getHeaderRect(intPcolumnIndex);
		objLrectangle.grow(-3, 0);
		if (objLrectangle.contains(objPoint)) {
			return null;
		}
		final int intLmiddlePoint = objLrectangle.x + objLrectangle.width / 2;
		int intLcolumnIndex = Constants.bytS_UNCLASS_NO_VALUE;
		if (objLjTableHeader.getComponentOrientation().isLeftToRight()) {
			intLcolumnIndex = objPoint.x < intLmiddlePoint ? intPcolumnIndex - 1 : intPcolumnIndex;
		} else {
			intLcolumnIndex = objPoint.x < intLmiddlePoint ? intPcolumnIndex : intPcolumnIndex - 1;
		}
		if (intLcolumnIndex == Constants.bytS_UNCLASS_NO_VALUE) {
			return null;
		}
		return objLjTableHeader.getColumnModel().getColumn(intLcolumnIndex);
	}

	@Override final public void mouseClicked(MouseEvent objPmouseEvent) {
		if (objPmouseEvent.getClickCount() == 2) {
			final JTableHeader objLjTableHeader = (JTableHeader) objPmouseEvent.getSource();
			final TableColumn objLtableColumn = this.getResizingColumn(objLjTableHeader, objPmouseEvent.getPoint());
			if (objLtableColumn != null) {
				final int intLcolumnIndex = objLjTableHeader.getColumnModel().getColumnIndex(objLtableColumn.getIdentifier());
				((DataJTable) objLjTableHeader.getTable()).setContentAdjustedColumnWidth(intLcolumnIndex);
			}
		}
	}

	@Override public void run() {
		this.lngGcurrentTimeMilliSeconds = System.currentTimeMillis();
		if (!this.bolGthread) {
			this.bolGthread = true;
			while (System.currentTimeMillis() - this.lngGcurrentTimeMilliSeconds < 250) {
				Tools.sleep(50);
			}
			if (this.objGjScrollPane == null) {
				if (this.objGdataJTable == this.objGdataJFrame.objGanimationJTable) {
					this.objGjScrollPane = this.objGdataJFrame.objGanimationJScrollPane;
				} else if (this.objGdataJTable == this.objGdataJFrame.objGhandsAndBallsJTable) {
					this.objGjScrollPane = this.objGdataJFrame.objGhandsAndBallsJScrollPane;
				}
			}
			if (this.objGjScrollPane != null) {
				final boolean bolLcontentAdjustment = this.objGdataJFrame.objGcontentAdjustmentJToggleButton.isSelected();
				final boolean bolLwindowAdjustment = this.objGdataJFrame.objGwindowAdjustmentJToggleButton.isSelected();
				if (bolLcontentAdjustment || bolLwindowAdjustment) {
					this.objGdataJTable.getColumnModel().removeColumnModelListener(this);
					if (bolLcontentAdjustment) {
						this.objGdataJTable.setContentAdjustedColumnsWidths();
					} else {
						this.objGdataJTable.setWindowAdjustedColumnsWidths(this.objGjScrollPane);
					}
					Tools.sleep(250);
					this.objGdataJTable.getColumnModel().addColumnModelListener(this);
				}
			}
			this.bolGthread = false;
		}
	}
}
