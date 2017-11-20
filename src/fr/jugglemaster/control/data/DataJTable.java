package fr.jugglemaster.control.data;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventObject;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ColumnFitAdapter;
import fr.jugglemaster.control.util.ExtendedTableCellRenderer;
import fr.jugglemaster.control.util.ToolTipedJTableHeader;
import fr.jugglemaster.gear.Ball;
import fr.jugglemaster.pattern.BallsColors;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Tools;

final public class DataJTable extends JTable {

	final public static int							intS_EXTRA_CELL_SPACE						= 8;

	final private static int						intS_MINIMUM_COLUMN_WIDTH					= 48;

	final private static ExtendedTableCellRenderer	objS_SELECTED_TABLE_CELL_RENDERER			= new ExtendedTableCellRenderer(Color.LIGHT_GRAY);

	final private static ExtendedTableCellRenderer	objS_STRONGLY_SELECTED_TABLE_CELL_RENDERER	= new ExtendedTableCellRenderer(Color.GRAY);

	final private static ExtendedTableCellRenderer	objS_UNSELECTED_TABLE_CELL_RENDERER			= new ExtendedTableCellRenderer();

	final private static long						serialVersionUID							= Constants.lngS_ENGINE_VERSION_NUMBER;

	private final int								intGcoloredIdentifier;

	private final int[]								intGheaderLanguageIndexA;

	private final int[]								intGidentifierColumnWidthA;

	public final ColumnFitAdapter					objGcolumnFitAdapter;

	private final ControlJFrame						objGcontrolJFrame;

	final DataJFrame								objGdataJFrame;

	public DataJTable(	ControlJFrame objPcontrolJFrame,
						DataJFrame objPdataJFrame,
						int intProwsNumber,
						int intPcolumnsNumber,
						int intPcoloredIdentifier,
						Object objPdefaultValueObject,
						int[] intPheaderLanguageIndexA,
						int[] intPheaderToolTipLanguageIndexA) {
		super(intProwsNumber, intPcolumnsNumber);
		// this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGdataJFrame = objPdataJFrame;
		this.intGcoloredIdentifier = intPcoloredIdentifier;
		this.intGheaderLanguageIndexA = intPheaderLanguageIndexA;
		this.intGidentifierColumnWidthA = new int[intPcolumnsNumber];
		Arrays.fill(this.intGidentifierColumnWidthA, DataJTable.intS_MINIMUM_COLUMN_WIDTH);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		this.setCellSelectionEnabled(true);
		this.setOpaque(true);
		this.setFocusable(false);
		this.setBorder(Constants.objS_GRAPHICS_JUGGLE_BORDER);
		this.objGcolumnFitAdapter = new ColumnFitAdapter(this.objGdataJFrame, this);
		final ToolTipedJTableHeader objLtoolTipedJTableHeader =
																new ToolTipedJTableHeader(	objPcontrolJFrame,
																							this.getColumnModel(),
																							intPheaderToolTipLanguageIndexA);
		objLtoolTipedJTableHeader.setFont(this.objGcontrolJFrame.getFont());
		objLtoolTipedJTableHeader.addMouseListener(this.objGcolumnFitAdapter);
		this.setTableHeader(objLtoolTipedJTableHeader);
		// this.getTableHeader().setFont(this.objGcontrolJFrame.getFont());
		// this.getTableHeader().addMouseListener(this.objGcolumnFitAdapter);
		this.getColumnModel().addColumnModelListener(this.objGcolumnFitAdapter);

		// Set the headers :
		final TableColumnModel objLtableColumnModel = this.getColumnModel();
		for (int intLcolumnIndex = 0; intLcolumnIndex < intPcolumnsNumber; ++intLcolumnIndex) {
			final TableColumn objLtableColumn = objLtableColumnModel.getColumn(intLcolumnIndex);
			objLtableColumn.setIdentifier(new Integer(intLcolumnIndex));
			// objLtableColumn.setHeaderValue(strPheaderA[intLcolumnIndex]);
		}

		// Set default values :
		for (int intLrowIndex = 0; intLrowIndex < intProwsNumber; ++intLrowIndex) {
			for (int intLcolumnIndex = 0; intLcolumnIndex < intPcolumnsNumber; ++intLcolumnIndex) {
				this.setValueAt(objPdefaultValueObject, intLrowIndex, intLcolumnIndex);
			}
		}
	}

	public DataJTable(	ControlJFrame objPcontrolJFrame,
						DataJFrame objPdataJFrame,
						int intProwsNumber,
						int intPcolumnsNumber,
						Object objPdefaultValueObject,
						int[] intPheaderLanguageIndexA,
						int[] intPheaderToolTipLanguageIndexA) {
		this(	objPcontrolJFrame,
				objPdataJFrame,
				intProwsNumber,
				intPcolumnsNumber,
				Constants.bytS_UNCLASS_NO_VALUE,
				objPdefaultValueObject,
				intPheaderLanguageIndexA,
				intPheaderToolTipLanguageIndexA);
	}

	@Override final public boolean editCellAt(int intProw, int intPcolumn, EventObject objPeventObject) {
		return false;
	}

	@Override final public TableCellRenderer getCellRenderer(int intProw, int intPcolumn) {

		// Selected row :
		if (intProw == this.getSelectedRow()) {
			// Selected cell :
			if (intPcolumn == this.getSelectedColumn()) {
				return DataJTable.objS_STRONGLY_SELECTED_TABLE_CELL_RENDERER;
			}
			return DataJTable.objS_SELECTED_TABLE_CELL_RENDERER;
		}
		// Selected column :
		if (intPcolumn == this.getSelectedColumn()) {
			return DataJTable.objS_SELECTED_TABLE_CELL_RENDERER;
		}

		// Unselected :
		Color objLbackgroundColor = null;
		Color objLcolor = null;
		if (this.intGcoloredIdentifier != Constants.bytS_UNCLASS_NO_VALUE && this.intGcoloredIdentifier == this.getColumnIdentifier(intPcolumn)) {
			try {
				if (intProw < 2) {
					objLbackgroundColor =
											Tools.getPenColor(this.objGcontrolJFrame.getControlString(Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_JUGGLER_DAY,
																																	this.objGcontrolJFrame.getJuggleMasterPro().bolGlight)));
				} else {
					final Ball objLball = this.objGcontrolJFrame.getJuggleMasterPro().objGballA[intProw - 2];
					final int intLcolorValue =
												this.objGcontrolJFrame.getJuggleMasterPro().isAlternateBallColor(intProw - 2)
																																? objLball.intGalternateColor
																																: objLball.intGcolor;
					objLbackgroundColor =
											BallsColors.getGammaBallColor(	intLcolorValue,
																			Constants.bytS_BYTE_GLOBAL_GAMMA_CORRECTION_DEFAULT_VALUE,
																			true,
																			false);
				}
			} catch (final Throwable objPthrowable) {
				objLbackgroundColor = null;
			}

			try {
				objLcolor = Tools.isLightColor(objLbackgroundColor) ? Color.BLACK : Color.WHITE;
			} catch (final Throwable objPthrowable) {
				objLcolor = null;
			}
		}
		return objLbackgroundColor == null ? DataJTable.objS_UNSELECTED_TABLE_CELL_RENDERER : new ExtendedTableCellRenderer(objLcolor,
																															objLbackgroundColor);
	}

	final private int getColumnIdentifier(int intPcolumnIndex) {

		int intLidentifier = Constants.bytS_UNCLASS_NO_VALUE;
		final String strLidentifierToken = ",text=";
		final Component objLcomponent =
										this.getTableHeader()
											.getDefaultRenderer()
											.getTableCellRendererComponent(	this,
																			this.getColumnModel().getColumn(intPcolumnIndex).getIdentifier(),
																			this.getSelectedColumn() == intPcolumnIndex,
																			false,
																			Constants.bytS_UNCLASS_NO_VALUE,
																			intPcolumnIndex);
		final String strLcomponent = objLcomponent.toString();
		int intLidentifierIndex = strLcomponent.indexOf(strLidentifierToken);
		if (intLidentifierIndex >= 0) {
			intLidentifierIndex += strLidentifierToken.length();
			int intLnextCommaIndex = strLcomponent.substring(intLidentifierIndex).indexOf(',');
			if (intLnextCommaIndex == Constants.bytS_UNCLASS_NO_VALUE) {
				intLnextCommaIndex = strLcomponent.length() - intLidentifierIndex;
			}
			try {
				intLidentifier = new Integer(strLcomponent.substring(intLidentifierIndex, intLidentifierIndex + intLnextCommaIndex));
				if (intLidentifier >= 0 && intLidentifier < this.getColumnCount()) {
					return intLidentifier;
				}
			} catch (final Throwable objPthrowable) {}
		}
		return Constants.bytS_UNCLASS_NO_VALUE;
	}

	final public int getColumnWidth(int intPcolumn) {
		return this.getIdentifierColumnWidth(this.getColumnIdentifier(intPcolumn));
	}

	final private int getIdentifierColumnWidth(int intPidentifier) {
		return this.intGidentifierColumnWidthA[intPidentifier];
	}

	@Override final public String getToolTipText(MouseEvent objPmouseEvent) {

		final Point objLpoint = objPmouseEvent.getPoint();
		final int intLrowIndex = this.rowAtPoint(objLpoint);
		final int intLcolumnIndex = this.convertColumnIndexToModel(this.columnAtPoint(objLpoint));
		return this.objGdataJFrame.getToolTipText(this, intLrowIndex, intLcolumnIndex);
	}

	final public int setColumnWidth(int intPcolumnIndex) {
		return this.setColumnWidth(intPcolumnIndex, Constants.bytS_UNCLASS_NO_VALUE);
	}

	final public int setColumnWidth(int intPcolumnIndex, int intPwidth) {
		final int intLidentifier = this.getColumnIdentifier(intPcolumnIndex);
		final JTableHeader objLjTableHeader = this.getTableHeader();
		final TableColumn objLtableColumn = this.getColumnModel().getColumn(intPcolumnIndex);
		objLjTableHeader.setResizingColumn(objLtableColumn);
		final int intLwidth =
								intPwidth == Constants.bytS_UNCLASS_NO_VALUE
																			? intLidentifier == Constants.bytS_UNCLASS_NO_VALUE
																																? Constants.bytS_UNCLASS_NO_VALUE
																																: this.intGidentifierColumnWidthA[intLidentifier]
																			: intPwidth;
		if (intLwidth != Constants.bytS_UNCLASS_NO_VALUE) {
			if (intLidentifier != Constants.bytS_UNCLASS_NO_VALUE) {
				this.intGidentifierColumnWidthA[intLidentifier] = intLwidth;
			}
			objLtableColumn.setWidth(intLwidth);
		}
		return intLwidth;
	}

	final public int setContentAdjustedColumnsWidths() {

		int intLtotalWidth = this.getIntercellSpacing().width * (this.getColumnCount() + 1);
		for (int intLcolumnIndex = 0, intLcolumnsNumber = this.getColumnCount(); intLcolumnIndex < intLcolumnsNumber; ++intLcolumnIndex) {
			intLtotalWidth += this.setContentAdjustedColumnWidth(intLcolumnIndex);
		}
		return intLtotalWidth;
	}

	final public int setContentAdjustedColumnWidth(int intPcolumnIndex) {
		if (intPcolumnIndex >= 0 && intPcolumnIndex < this.getColumnCount()) {
			int intLadjustedColumnWidth = DataJTable.intS_MINIMUM_COLUMN_WIDTH;

			// Calculate header width :
			final int intLidentifier = this.getColumnIdentifier(intPcolumnIndex);
			if (intLidentifier != Constants.bytS_UNCLASS_NO_VALUE) {
				intLadjustedColumnWidth =
											Math.max(	intLadjustedColumnWidth,
														DataJTable.intS_EXTRA_CELL_SPACE
															+ Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(this.objGcontrolJFrame.getLanguageString(this.intGheaderLanguageIndexA[intLidentifier])));
			}

			// Calculate value max width :
			for (int intLrowIndex = 0, intLrowsNumber = this.getRowCount(); intLrowIndex < intLrowsNumber; intLrowIndex++) {
				final Object objLvalue = this.getValueAt(intLrowIndex, intPcolumnIndex);
				final int intLwidth =
										objLvalue != null ? Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(objLvalue.toString())
															: DataJTable.intS_MINIMUM_COLUMN_WIDTH;
				intLadjustedColumnWidth = Math.max(intLadjustedColumnWidth, DataJTable.intS_EXTRA_CELL_SPACE + intLwidth);
			}
			if (intLidentifier != Constants.bytS_UNCLASS_NO_VALUE) {
				this.intGidentifierColumnWidthA[intLidentifier] = intLadjustedColumnWidth;
			}
			this.setColumnWidth(intPcolumnIndex, intLadjustedColumnWidth);
			return intLadjustedColumnWidth;
		}
		return DataJTable.intS_MINIMUM_COLUMN_WIDTH;
	}

	@Override final public void setValueAt(Object objPvalueObject, int intProw, int intPcolumn) {
		try {
			super.setValueAt(objPvalueObject, intProw, intPcolumn);
		} catch (final Throwable objPthrowable) {}
	}

	final public int setWindowAdjustedColumnsWidths(JScrollPane objPjScrollPane) {

		if (objPjScrollPane == null) {
			return Constants.bytS_UNCLASS_NO_VALUE;
		}

		final int intLjScrollPaneWidth = objPjScrollPane.getWidth() - this.getColumnCount() * this.getIntercellSpacing().width;
		Tools.debug("DataJTable.setWindowAdjustedColumnsWidths(", intLjScrollPaneWidth, ")");
		final int intLcolumnsNumber = this.getColumnCount();
		int intLtotalWidth = this.setContentAdjustedColumnsWidths();
		int intLdelta = intLtotalWidth - intLjScrollPaneWidth;
		boolean bolLchange = false;
		while (intLdelta > 0) {
			bolLchange = true;
			final float fltLratio = 1.1F * intLdelta / intLtotalWidth;
			intLtotalWidth = this.getIntercellSpacing().width * (this.getColumnCount() + 1);
			for (int intLidentifier = 0; intLidentifier < intLcolumnsNumber; ++intLidentifier) {
				if (this.intGidentifierColumnWidthA[intLidentifier] > DataJTable.intS_MINIMUM_COLUMN_WIDTH) {
					this.intGidentifierColumnWidthA[intLidentifier] -= fltLratio * this.intGidentifierColumnWidthA[intLidentifier];
				}
				intLtotalWidth += this.intGidentifierColumnWidthA[intLidentifier];
			}
			intLdelta = intLtotalWidth - intLjScrollPaneWidth;
		}

		if (intLtotalWidth < intLjScrollPaneWidth) {
			intLdelta = (intLjScrollPaneWidth - intLtotalWidth) / intLcolumnsNumber;
			if (intLdelta > 0) {
				bolLchange = true;
				for (int intLcolumnIndex = 0; intLcolumnIndex < intLcolumnsNumber; ++intLcolumnIndex) {
					final int intLidentifier = this.getColumnIdentifier(intLcolumnIndex);
					if (intLidentifier > Constants.bytS_UNCLASS_NO_VALUE && intLidentifier < this.intGidentifierColumnWidthA.length) {
						this.intGidentifierColumnWidthA[this.getColumnIdentifier(intLcolumnIndex)] += intLdelta;
					}
				}
				intLtotalWidth += intLdelta * intLcolumnsNumber;
			}
		}
		if (bolLchange) {
			// this.getColumnModel().removeColumnModelListener(this);
			for (int intLcolumnIndex = 0; intLcolumnIndex < intLcolumnsNumber; ++intLcolumnIndex) {
				this.setColumnWidth(intLcolumnIndex);
			}
			// this.getColumnModel().addColumnModelListener(this);
		}
		return intLtotalWidth;
	}

	@Override final public void valueChanged(ListSelectionEvent objPlistSelectionEvent) {
		this.repaint();
	}
}
