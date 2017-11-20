/*
 * @(#)DataJFrame.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.data;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import fr.jugglemaster.control.ControlJFrame;
import fr.jugglemaster.control.util.ExtendedGridBagConstraints;
import fr.jugglemaster.engine.JuggleMasterPro;
import fr.jugglemaster.engine.window.AnimationJFrame;
import fr.jugglemaster.gear.Ball;
import fr.jugglemaster.gear.Hand;
import fr.jugglemaster.pattern.BallsColors;
import fr.jugglemaster.pattern.Style;
import fr.jugglemaster.user.Language;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class DataJFrame extends JFrame implements Runnable, WindowListener, ComponentListener {

	final private static byte	bytS_ANIMATION_TABLE_COLUMNS_NUMBER;

	final private static byte	bytS_ANIMATION_TABLE_FRAMES								= 1;

	final private static byte	bytS_ANIMATION_TABLE_FRAMES_REAL_REFRESH				= 3;

	final private static byte	bytS_ANIMATION_TABLE_FRAMES_THEORETICAL_REFRESH			= 2;

	final private static byte	bytS_ANIMATION_TABLE_HEIGHTS							= 5;

	final private static byte	bytS_ANIMATION_TABLE_THROWS								= 0;

	final private static byte	bytS_ANIMATION_TABLE_WIDTHS								= 4;

	final private static byte	bytS_ANIMATION_TABLE_ZOOM								= 6;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_COLOR						= 2;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_GRAPHIC_POSITION				= 7;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_LAST_AND_NEXT_CATCHES		= 5;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_OBJECT						= 0;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_STATE						= 1;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_THEORIC_DESTINATION			= 4;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_THICKNESS					= 9;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_THROW						= 3;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_THROWING_AND_CATCHING_HANDS	= 6;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_VISIBILITY					= 10;

	final private static byte	bytS_HANDS_AND_BALLS_TABLE_Z_ORDER						= 8;

	final private static int[]	intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A;
	final private static int[]	intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A;

	final private static int[]	intS_HAND_STRING_LANGUAGE_INDEX_A;

	final private static int[]	intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A;

	private static int[]		intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A;

	final private static long	serialVersionUID										= Constants.lngS_ENGINE_VERSION_NUMBER;

	static {

		bytS_ANIMATION_TABLE_COLUMNS_NUMBER = DataJFrame.bytS_ANIMATION_TABLE_ZOOM + 1;

		// Animation table header columns :
		intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A = new int[DataJFrame.bytS_ANIMATION_TABLE_COLUMNS_NUMBER];
		DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_THROWS] = Language.intS_LABEL_DATA_THROWS;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_FRAMES] = Language.intS_LABEL_DATA_FRAMES;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_FRAMES_THEORETICAL_REFRESH] =
																																Language.intS_LABEL_DATA_FRAMES_THEORETICAL_REFRESH;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_FRAMES_REAL_REFRESH] =
																														Language.intS_LABEL_DATA_FRAMES_REAL_REFRESH;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_WIDTHS] = Language.intS_LABEL_DATA_WIDTHS;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_HEIGHTS] = Language.intS_LABEL_DATA_HEIGHTS;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_ZOOM] = Language.intS_LABEL_DATA_ZOOM;

		// Animation table header tooltips :
		intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A = new int[DataJFrame.bytS_ANIMATION_TABLE_COLUMNS_NUMBER];
		DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_THROWS] = Language.intS_TOOLTIP_DATA_THROWS;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_FRAMES] = Language.intS_TOOLTIP_DATA_FRAMES;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_FRAMES_THEORETICAL_REFRESH] =
																																		Language.intS_TOOLTIP_DATA_FRAMES_THEORETICAL_REFRESH;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_FRAMES_REAL_REFRESH] =
																																Language.intS_TOOLTIP_DATA_FRAMES_REAL_REFRESH;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_WIDTHS] = Language.intS_TOOLTIP_DATA_WIDTHS;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_HEIGHTS] = Language.intS_TOOLTIP_DATA_HEIGHTS;
		DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_ANIMATION_TABLE_ZOOM] = Language.intS_TOOLTIP_DATA_ZOOM;

		bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER = DataJFrame.bytS_HANDS_AND_BALLS_TABLE_VISIBILITY + 1;
		// Ball-hand table header columns :
		intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A = new int[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER];
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_OBJECT] = Language.intS_LABEL_DATA_OBJECT;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_STATE] = Language.intS_LABEL_DATA_STATE;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLOR] = Language.intS_LABEL_DATA_COLOR;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROW] = Language.intS_LABEL_DATA_THROW;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THEORIC_DESTINATION] =
																																	Language.intS_LABEL_DATA_THEORIC_DESTINATION;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_LAST_AND_NEXT_CATCHES] =
																																		Language.intS_LABEL_DATA_LAST_AND_NEXT_CATCHES;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROWING_AND_CATCHING_HANDS] =
																																			Language.intS_LABEL_DATA_THROWING_AND_CATCHING_HANDS;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_GRAPHIC_POSITION] =
																																Language.intS_LABEL_DATA_GRAPHIC_POSITION;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_Z_ORDER] =
																														Language.intS_LABEL_DATA_Z_ORDER;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THICKNESS] =
																															Language.intS_LABEL_DATA_THICKNESS;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_VISIBILITY] =
																															Language.intS_LABEL_DATA_VISIBILITY;

		// Ball-hand table header tooltips :
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A = new int[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER];
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_OBJECT] =
																																Constants.bytS_UNCLASS_NO_VALUE;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_STATE] =
																																Language.intS_TOOLTIP_DATA_STATE;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLOR] =
																																Constants.bytS_UNCLASS_NO_VALUE;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROW] =
																																Language.intS_TOOLTIP_DATA_THROW;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THEORIC_DESTINATION] =
																																			Language.intS_TOOLTIP_DATA_THEORIC_DESTINATION;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_LAST_AND_NEXT_CATCHES] =
																																				Language.intS_TOOLTIP_DATA_LAST_AND_NEXT_CATCHES;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROWING_AND_CATCHING_HANDS] =
																																					Language.intS_TOOLTIP_DATA_THROWING_AND_CATCHING_HANDS;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_GRAPHIC_POSITION] =
																																		Language.intS_TOOLTIP_DATA_GRAPHIC_POSITION;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_Z_ORDER] =
																																Language.intS_TOOLTIP_DATA_Z_ORDER;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THICKNESS] =
																																	Language.intS_TOOLTIP_DATA_THICKNESS;
		DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_VISIBILITY] =
																																	Constants.bytS_UNCLASS_NO_VALUE;

		intS_HAND_STRING_LANGUAGE_INDEX_A = new int[2];
		DataJFrame.intS_HAND_STRING_LANGUAGE_INDEX_A[Constants.bytS_ENGINE_LEFT_SIDE] = Language.intS_LABEL_DATA_LEFT_HAND;
		DataJFrame.intS_HAND_STRING_LANGUAGE_INDEX_A[Constants.bytS_ENGINE_RIGHT_SIDE] = Language.intS_LABEL_DATA_RIGHT_HAND;
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private static char getZOrderChar(boolean bolPbodyZOrder, boolean bolPhandsZOrder) {
		if (bolPbodyZOrder) {
			if (bolPhandsZOrder) {
				return '#';
			}
			return '+';
		}
		if (bolPhandsZOrder) {
			return '-';
		}
		return '=';
	}

	private boolean					bolGalreadyDisplayed;

	private boolean					bolGmanyZValues;

	private boolean					bolGnegativeZValues;

	private boolean					bolGvisible;

	private int						intGballsNumber;

	private int						intGcatchFramesDelta;

	private int						intGframeHeight;
	private int						intGtheoricDestinationXLength;
	private int						intGtheoricDestinationYLength;
	private int						intGtheoricDestinationZLength;
	private int						intGthrowFramesDelta;
	public JScrollPane				objGanimationJScrollPane;

	public final DataJTable			objGanimationJTable;

	private final DataJToggleButton	objGcloseWindowJToggleButton;

	public final DataJToggleButton	objGcontentAdjustmentJToggleButton;

	public final ControlJFrame		objGcontrolJFrame;

	public JScrollPane				objGhandsAndBallsJScrollPane;

	public final DataJTable			objGhandsAndBallsJTable;

	private final JPanel			objGjPanel;

	private final JuggleMasterPro	objGjuggleMasterPro;

	public final DataJToggleButton	objGwindowAdjustmentJToggleButton;

	/**
	 * Constructs
	 * 
	 * @param objPjuggleMasterPro
	 * @param intPballsNumber
	 */
	public DataJFrame(ControlJFrame objPcontrolJFrame, JuggleMasterPro objPjuggleMasterPro) {
		this.objGcontrolJFrame = objPcontrolJFrame;
		this.objGjuggleMasterPro = objPjuggleMasterPro;
		this.bolGalreadyDisplayed = this.bolGvisible = false;
		this.intGballsNumber = this.intGframeHeight = 0;
		this.objGanimationJTable =
									new DataJTable(	this.objGcontrolJFrame,
													this,
													1,
													DataJFrame.bytS_ANIMATION_TABLE_COLUMNS_NUMBER,
													new Integer(0),
													DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A,
													DataJFrame.intS_ANIMATION_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A);
		this.objGhandsAndBallsJTable =
										new DataJTable(	this.objGcontrolJFrame,
														this,
														2,
														DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER,
														DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLOR,
														Strings.strS_EMPTY,
														DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A,
														DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_TOOLTIP_LANGUAGE_INDEX_A);

		this.objGcontentAdjustmentJToggleButton = new DataJToggleButton(this, DataJToggleButton.bytS_CONTENT_ADJUSTMENT);
		this.objGwindowAdjustmentJToggleButton = new DataJToggleButton(this, DataJToggleButton.bytS_WINDOW_ADJUSTMENT);
		this.objGcloseWindowJToggleButton = new DataJToggleButton(this, DataJToggleButton.bytS_CLOSE_WINDOW);

		this.setLayout(new GridBagLayout());
		this.objGjPanel = new JPanel(new GridBagLayout());
		this.objGjPanel.setOpaque(true);
		final JScrollPane objLjScrollPane =
											new JScrollPane(this.objGjPanel,
															ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
															ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		objLjScrollPane.setOpaque(true);
		this.add(objLjScrollPane, new ExtendedGridBagConstraints(0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0F, 1.0F));
		this.setHandsAndBallsTableSize();
		this.setHeaders();

		this.setIconImage(this.objGjuggleMasterPro.getImage(Constants.intS_FILE_ICON_FRAME, Constants.bytS_UNCLASS_NO_VALUE));
		this.setTitle(this.objGcontrolJFrame.getLanguageString(Language.intS_TITLE_DATA));
		this.addWindowListener(this);
		this.addComponentListener(this);
	}

	@Override final public void componentHidden(ComponentEvent objPcomponentEvent) {}

	@Override final public void componentMoved(ComponentEvent objPcomponentEvent) {}

	@Override final public void componentResized(ComponentEvent objPcomponentEvent) {
		if (this.getExtendedState() == Frame.MAXIMIZED_BOTH) {
			// this.setExtendedState(Frame.NORMAL);
			this.setSize(this.getWidth(), this.intGframeHeight);
		} else {
			this.intGframeHeight = this.getHeight();
		}
		if (this.objGwindowAdjustmentJToggleButton.isSelected()) {
			// this.objGanimationJTable.setWindowAdjustedColumnsWidths(this.objGanimationJScrollPane.getViewport().getWidth());
			// this.objGhandsAndBallsJTable.setWindowAdjustedColumnsWidths(this.objGhandsAndBallsJScrollPane.getViewport().getWidth());
			new Thread(this.objGanimationJTable.objGcolumnFitAdapter).start();
			new Thread(this.objGhandsAndBallsJTable.objGcolumnFitAdapter).start();
		}
	}

	@Override final public void componentShown(ComponentEvent objPcomponentEvent) {}

	final private void doAddWidgets() {
		ExtendedGridBagConstraints objLextendedGridBagConstraints = null;

		// Set animation scroll :
		if (!this.bolGalreadyDisplayed) {
			objLextendedGridBagConstraints =
												new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																				0,
																				1,
																				1,
																				GridBagConstraints.CENTER,
																				0,
																				0,
																				0,
																				10,
																				GridBagConstraints.HORIZONTAL,
																				1.0F,
																				0.0F);
			this.objGanimationJScrollPane =
											new JScrollPane(this.objGanimationJTable,
															ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
															ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.objGjPanel.add(this.objGanimationJScrollPane, objLextendedGridBagConstraints);
			objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 0.0F, 0.0F);
			final JPanel objLjPanel = new JPanel(new GridBagLayout());
			final ExtendedGridBagConstraints objLjPanelExtendedGridBagConstraints =
																					new ExtendedGridBagConstraints(	GridBagConstraints.RELATIVE,
																													0,
																													1,
																													1,
																													0,
																													0,
																													0,
																													10,
																													GridBagConstraints.VERTICAL,
																													0.0F,
																													1.0F);
			this.objGcontentAdjustmentJToggleButton.setImages();
			objLjPanel.add(this.objGcontentAdjustmentJToggleButton, objLjPanelExtendedGridBagConstraints);
			this.objGwindowAdjustmentJToggleButton.setImages();

			// TODO : windowAdjustmentJButton !
			// objLjPanel.add(this.objGwindowAdjustmentJToggleButton, objLjPanelExtendedGridBagConstraints);

			this.objGcloseWindowJToggleButton.setImages();
			objLjPanelExtendedGridBagConstraints.setMargins(0, 0, 0, 0);
			objLjPanel.add(this.objGcloseWindowJToggleButton, objLjPanelExtendedGridBagConstraints);
			this.objGjPanel.add(objLjPanel, objLextendedGridBagConstraints);
		}
		this.objGanimationJTable.doLayout();

		final int intLanimationWidth = this.objGanimationJTable.setContentAdjustedColumnsWidths();
		this.objGanimationJScrollPane.setPreferredSize(new Dimension(intLanimationWidth, (int) this.objGanimationJTable	.getCellRect(0, 0, true)
																														.getHeight() * 4));

		// Set hand & ball scroll :
		if (!this.bolGalreadyDisplayed) {
			this.objGhandsAndBallsJScrollPane =
												new JScrollPane(this.objGhandsAndBallsJTable,
																ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
																ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			if (objLextendedGridBagConstraints != null) {
				objLextendedGridBagConstraints.setFilling(GridBagConstraints.HORIZONTAL, 1.0F, 0.0F);
				objLextendedGridBagConstraints.setGridBounds(0, 1, 2, 1);
				objLextendedGridBagConstraints.setMargins(0, 0, 0, 0);
				this.objGjPanel.add(this.objGhandsAndBallsJScrollPane, objLextendedGridBagConstraints);
			}
		}
		this.objGhandsAndBallsJTable.doLayout();
		final int intLhandsAndBallsWidth = this.objGhandsAndBallsJTable.setContentAdjustedColumnsWidths();
		this.objGhandsAndBallsJScrollPane.setPreferredSize(new Dimension(	intLhandsAndBallsWidth,
																			(int) this.objGhandsAndBallsJTable.getCellRect(0, 0, true).getHeight()
																				* (5 + this.intGballsNumber)));
		this.doPack();

		// Set layout :
		if (!this.bolGalreadyDisplayed) {
			final Insets objLinsets = this.getInsets();
			final int intLwidth =
									Math.min(Constants.intS_GRAPHICS_SCREEN_WIDTH, Math.max(intLhandsAndBallsWidth, intLanimationWidth)
																					+ objLinsets.left + objLinsets.right + 64);
			this.intGframeHeight = Math.max(128, Constants.intS_GRAPHICS_SCREEN_HEIGHT - Constants.intS_GRAPHICS_JUGGLE_FRAME_DEFAULT_SIZE) / 2;
			this.setSize(intLwidth, this.intGframeHeight);
			this.setLocation(	Math.max(Constants.intS_GRAPHICS_SCREEN_X, (Constants.intS_GRAPHICS_SCREEN_WIDTH - intLwidth) / 2),
								Constants.intS_GRAPHICS_SCREEN_Y);
			this.bolGalreadyDisplayed = true;
		}
		// this.objGcloseWindowJToggleButton.requestFocusInWindow();
	}

	final public void doAdjustFields(boolean bolPfullWindow) {
		if (bolPfullWindow) {
			this.objGanimationJTable.setWindowAdjustedColumnsWidths(this.objGanimationJScrollPane);
			this.objGhandsAndBallsJTable.setWindowAdjustedColumnsWidths(this.objGhandsAndBallsJScrollPane);
		} else {
			this.objGanimationJTable.setContentAdjustedColumnsWidths();
			this.objGhandsAndBallsJTable.setContentAdjustedColumnsWidths();
		}
	}

	final public void doLoadImages() {
		this.objGcloseWindowJToggleButton.doLoadImages();
		this.objGcontentAdjustmentJToggleButton.doLoadImages();
		this.objGwindowAdjustmentJToggleButton.doLoadImages();
	}

	final private void doPack() {
		final Dimension objLdimension = this.getSize();
		this.validate();
		this.pack();
		this.setSize(objLdimension);
	}

	final public void doToggleJButtons(byte bytPjButtonType, boolean bolPselected) {
		if (bolPselected) {
			final DataJToggleButton objLdataJToggleButton =
															bytPjButtonType == DataJToggleButton.bytS_CONTENT_ADJUSTMENT
																														? this.objGwindowAdjustmentJToggleButton
																														: this.objGcontentAdjustmentJToggleButton;
			objLdataJToggleButton.removeItemListener(objLdataJToggleButton);
			objLdataJToggleButton.setSelected(false);
			objLdataJToggleButton.addItemListener(objLdataJToggleButton);
		}
	}

	final public String getToolTipText(DataJTable objPdataJTable, int intProwIndex, int intPcolumnIndex) {

		int intLtooltipLanguageIndex = Constants.bytS_UNCLASS_NO_VALUE;

		if (objPdataJTable == this.objGanimationJTable) {
			switch (intPcolumnIndex) {
				case DataJFrame.bytS_ANIMATION_TABLE_THROWS:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_THROW;
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_FRAMES:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_FRAMES;
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_FRAMES_THEORETICAL_REFRESH:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_FRAMES_THEORETICAL_REFRESH;
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_FRAMES_REAL_REFRESH:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_FRAMES_REAL_REFRESH;
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_WIDTHS:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_WIDTHS;
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_HEIGHTS:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_HEIGHTS;
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_ZOOM:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_ZOOM;
					break;
			}
		}

		if (objPdataJTable == this.objGhandsAndBallsJTable) {
			switch (intPcolumnIndex) {
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_OBJECT:
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_STATE:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_STATE;
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLOR:
					intLtooltipLanguageIndex = intProwIndex > 1 ? Language.intS_TOOLTIP_DATA_BALL_COLOR : Language.intS_TOOLTIP_DATA_HAND_COLOR;
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROW:
					if (intProwIndex > 1) {
						intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_THROW;
					}
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROWING_AND_CATCHING_HANDS:
					if (intProwIndex > 1) {
						intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_THROWING_AND_CATCHING_HANDS;
					}
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THEORIC_DESTINATION:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_THEORIC_DESTINATION;
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_LAST_AND_NEXT_CATCHES:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_LAST_AND_NEXT_CATCHES;
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_GRAPHIC_POSITION:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_GRAPHIC_POSITION;
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_Z_ORDER:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_Z_ORDER;
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THICKNESS:
					intLtooltipLanguageIndex = Language.intS_TOOLTIP_DATA_THICKNESS;
					break;
				case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_VISIBILITY:
					break;
			}
		}

		return this.objGcontrolJFrame.getLanguageString(intLtooltipLanguageIndex);
	}

	final public void initAnimationValues() {
		this.intGcatchFramesDelta = this.intGthrowFramesDelta = 0;
	}

	@Override public void run() {
		try {
			this.setValues();
		} catch (final Throwable objPthrowable) {
			Tools.err("Error while setting data frame values");
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setAnimationValues() {

		String strLvalue = null;
		final boolean bolLplayable = this.objGjuggleMasterPro.bytGanimationStatus != Constants.bytS_STATE_ANIMATION_UNPLAYABLE;
		final boolean bolLsiteswapBallsNumber =
												this.objGjuggleMasterPro.objGsiteswap == null
																								? false
																								: this.objGjuggleMasterPro.objGsiteswap.bytGstatus > Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER;

		// Set the catch/throw frames delta values :
		if (bolLplayable) {
			this.intGcatchFramesDelta = Math.min(this.intGcatchFramesDelta, this.objGjuggleMasterPro.intGdwellFramesIndexesDelta - 1);
			this.intGthrowFramesDelta = Math.max(this.intGthrowFramesDelta, this.objGjuggleMasterPro.intGdwellFramesIndexesDelta + 1);
		}

		// Fill the animation table :
		ColumnsLabel: for (int intLcolumnIndex = 0; intLcolumnIndex < DataJFrame.bytS_ANIMATION_TABLE_COLUMNS_NUMBER; ++intLcolumnIndex) {
			final int intLidentifier = (Integer) (this.objGanimationJTable.getColumnModel().getColumn(intLcolumnIndex).getIdentifier());
			switch (intLidentifier) {
				case DataJFrame.bytS_ANIMATION_TABLE_THROWS:
					if (bolLplayable) {
						strLvalue =
									Strings.doConcat(	this.objGjuggleMasterPro.lngGthrowsCount,
														Strings.strS_SPACE,
														'(',
														Strings.getFixedLengthString(	this.objGjuggleMasterPro.intGcurrentThrowIndex + 1,
																						Tools.getLength(Math.max(	1,
																													this.objGjuggleMasterPro.objGsiteswap.intGthrowsNumber)),
																						true,
																						false),
														" / ",
														this.objGjuggleMasterPro.objGsiteswap.intGthrowsNumber,
														')');
					} else {
						strLvalue =
									bolLsiteswapBallsNumber
															? Strings.doConcat("0 (0 / ", this.objGjuggleMasterPro.objGsiteswap.intGthrowsNumber, ')')
															: Strings.strS_EMPTY;
					}
					break;

				case DataJFrame.bytS_ANIMATION_TABLE_FRAMES:
					strLvalue =
								bolLplayable
											? Strings.doConcat(	this.objGjuggleMasterPro.lngGframesCount,
																" (",
																Strings.getFixedLengthString(this.intGcatchFramesDelta, 4, true, false),
																" < ",
																this.objGjuggleMasterPro.intGdwellFramesIndexesDelta >= 0 ? '+' : '-',
																Strings.getFixedLengthString(	Math.abs(this.objGjuggleMasterPro.intGdwellFramesIndexesDelta),
																								3,
																								true,
																								false,
																								'0'),
																" < ",
																Strings.getFixedLengthString(	Strings.doConcat('+', this.intGthrowFramesDelta),
																								4,
																								true,
																								false),
																')') : Strings.strS_EMPTY;
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_FRAMES_THEORETICAL_REFRESH:
					strLvalue =
								Strings.doConcat(	this.objGjuggleMasterPro.intGtheoricalMilliSecondsFramesDelay,
													" ms (",
													Strings.getFixedLengthString(	1000.0F / this.objGjuggleMasterPro.intGtheoricalMilliSecondsFramesDelay,
																					4,
																					true,
																					true,
																					'0'),
													" fps)");
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_FRAMES_REAL_REFRESH:
					if (bolLplayable) {
						final long lngLdelta =
												this.objGjuggleMasterPro.lngGrealMilliSecondsFramesNextTime
													- this.objGjuggleMasterPro.lngGrealMilliSecondsFramesLastTime;
						if (lngLdelta > 0) {
							strLvalue =
										Strings.doConcat(	Strings.getFixedLengthString(lngLdelta, 4, true, false, '0'),
															" ms (",
															Strings.getFixedLengthString((int) Math.floor(1000.0F / lngLdelta), 4, true, false, '0'),
															'.',
															Strings.getFixedLengthString(	10 * (1000.0F / lngLdelta - Math.floor(1000.0F / lngLdelta)),
																							1,
																							false,
																							true,
																							'0'),
															" fps)");
						} else {
							continue ColumnsLabel;
						}
					} else {
						strLvalue = Strings.strS_EMPTY;
					}
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_WIDTHS:
					AnimationJFrame objLanimationJFrame = this.objGjuggleMasterPro.getFrame();
					strLvalue =
								Strings.doConcat(	'[',
													objLanimationJFrame.intGmarginWidth,
													" px] ",
													objLanimationJFrame.intGanimationWidth,
													" px [",
													objLanimationJFrame.intGmarginWidth,
													" px] / ",
													objLanimationJFrame.intGbackgroundWidth,
													" px (",
													100 * objLanimationJFrame.intGanimationWidth / objLanimationJFrame.intGbackgroundWidth,
													"%)");
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_HEIGHTS:
					objLanimationJFrame = this.objGjuggleMasterPro.getFrame();
					strLvalue =
								Strings.doConcat(	'[',
													objLanimationJFrame.intGmarginHeight,
													" px] ",
													Constants.intS_GRAPHICS_SITESWAP_HEIGHT,
													" px + ",
													objLanimationJFrame.intGanimationHeight,
													" px [",
													objLanimationJFrame.intGmarginHeight,
													" px] / ",
													objLanimationJFrame.intGbackgroundHeight,
													" px (",
													100 * (Constants.intS_GRAPHICS_SITESWAP_HEIGHT + objLanimationJFrame.intGanimationHeight)
														/ objLanimationJFrame.intGbackgroundHeight,
													"%)");
					break;
				case DataJFrame.bytS_ANIMATION_TABLE_ZOOM:
					strLvalue = Strings.doConcat(Math.round(this.objGjuggleMasterPro.getFrame().fltGframeSizeRatio * 100), '%');
					break;
			}
			try {
				final String strLcurrent = Strings.toString(this.objGanimationJTable.getValueAt(0, intLcolumnIndex));
				if (!Strings.areNullEqual(strLvalue, strLcurrent)) {
					if (this.objGcontentAdjustmentJToggleButton.isSelected() && strLvalue != null) {
						final int intLcolumnWidth = Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(strLvalue) + DataJTable.intS_EXTRA_CELL_SPACE;
						if (intLcolumnWidth > this.objGanimationJTable.getColumnWidth(intLcolumnIndex)) {
							this.objGanimationJTable.setColumnWidth(intLcolumnIndex, intLcolumnWidth);
						}
					}
					this.objGanimationJTable.setValueAt(strLvalue, 0, intLcolumnIndex);

				}
			} catch (final Throwable objPthrowable) {
				this.objGanimationJTable.setValueAt(strLvalue, 0, intLcolumnIndex);
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setBallsValues() {

		Ball objLball = null;
		final String strLvalueAA[][] = new String[this.intGballsNumber + 2][DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER];
		final boolean bolLplayable = this.objGjuggleMasterPro.bytGanimationStatus != Constants.bytS_STATE_ANIMATION_UNPLAYABLE;
		final boolean bolLsiteswapBallsNumber =
												this.objGjuggleMasterPro.objGsiteswap == null
																								? false
																								: this.objGjuggleMasterPro.objGsiteswap.bytGstatus > Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER;

		// Calculate new values :
		if (bolLsiteswapBallsNumber) {
			for (int intLrowIndex = 2; intLrowIndex < 2 + this.intGballsNumber; ++intLrowIndex) {
				objLball = this.objGjuggleMasterPro.objGballA[intLrowIndex - 2];
				for (int intLcolumnIndex = 0; intLcolumnIndex < DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER; ++intLcolumnIndex) {
					int intLidentifier = Constants.bytS_UNCLASS_NO_VALUE;
					try {
						intLidentifier = (Integer) (this.objGhandsAndBallsJTable.getColumnModel().getColumn(intLcolumnIndex).getIdentifier());
					} catch (final Throwable objPthrowable) {
						Tools.err("Error while determining hand-ball table column identifier");
						continue;
					}
					switch (intLidentifier) {
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_OBJECT:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_BALL_INDEX),
																								intLrowIndex - 1);
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_STATE:
							if (bolLplayable) {
								String strLlabel = null;
								switch (objLball.bytGanimationState) {
									case 0:
										strLlabel =
													this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_THROW_CATCH_BALL_STATIC_IN_HAND);
										break;
									case 2:
										strLlabel =
													this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_CATCH_THROW_BALL_STATIC_IN_HAND);
										break;
									case 4:
										strLlabel =
													this.objGcontrolJFrame.getLanguageString(objLball.bytGthrow == 2
																													? Language.intS_LABEL_DATA_THROW_CATCH_BALL_MOVING_IN_HAND
																													: Language.intS_LABEL_DATA_THROW_CATCH_BALL_MOVING_IN_AIR);
										break;
									case 6:
										strLlabel =
													this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_CATCH_THROW_BALL_MOVING_IN_HAND);
										break;
									default:
										strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_UNKNOWN_STATE);
								}
								// strLvalueAA[intLrowIndex][intLcolumnIndex] = Strings.doConcat(objLball.bytGanimationState, " : ", strLlabel);
								strLvalueAA[intLrowIndex][intLcolumnIndex] = strLlabel;
							} else {
								strLvalueAA[intLrowIndex][intLcolumnIndex] = Strings.strS_EMPTY;
							}
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLOR:
							final int intLcolorValue =
														this.objGjuggleMasterPro.isAlternateBallColor(intLrowIndex - 2) ? objLball.intGalternateColor
																														: objLball.intGcolor;
							strLvalueAA[intLrowIndex][intLcolumnIndex] = BallsColors.getBallColorString(intLcolorValue, false);
							// final TableCellRenderer objLcolorCellRenderer =
							// this.objGhandsAndBallsJTable.getCellRenderer( intLrowIndex,
							// intLcolumnIndex);
							// if (objLcolorCellRenderer instanceof ExtendedTableCellRenderer) {
							// ((ExtendedTableCellRenderer) objLcolorCellRenderer).setForeground(BallsColors.getBallColor(intLcolorValue, false));
							// }

							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROW:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			bolLplayable ? Tools.getThrowValueString(objLball.bytGthrow)
																						: Strings.strS_EMPTY;
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THEORIC_DESTINATION:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			bolLplayable
																						? Strings.doConcat(	'(',
																											Strings.getFixedLengthString(	objLball.bytGtheoricDestinationX,
																																			this.intGtheoricDestinationXLength,
																																			true,
																																			false),
																											", ",
																											Strings.getFixedLengthString(	objLball.bytGtheoricDestinationY,
																																			this.intGtheoricDestinationYLength,
																																			true,
																																			false),
																											", ",
																											Strings.getFixedLengthString(	objLball.bytGtheoricDestinationZ,
																																			this.intGtheoricDestinationZLength,
																																			true,
																																			false),
																											')') : Strings.strS_EMPTY;
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_LAST_AND_NEXT_CATCHES:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			bolLplayable ? Strings.doConcat(objLball.intGlastCatchTimer,
																											" / (",
																											this.objGjuggleMasterPro.lngGthrowsCount,
																											") / ",
																											objLball.intGnextCatchTimer)
																						: Strings.strS_EMPTY;
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THROWING_AND_CATCHING_HANDS:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			bolLplayable
																						? Strings.doConcat(	this.objGcontrolJFrame.getLanguageString(DataJFrame.intS_HAND_STRING_LANGUAGE_INDEX_A[objLball.bytGthrowingHand]),
																											" / ",
																											this.objGcontrolJFrame.getLanguageString(DataJFrame.intS_HAND_STRING_LANGUAGE_INDEX_A[objLball.bytGcatchingHand]))
																						: Strings.strS_EMPTY;
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_GRAPHIC_POSITION:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			bolLplayable
																						? Strings.doConcat(	'(',
																											Strings.getFixedLengthString(	this.objGjuggleMasterPro.getFrame().intGmarginWidth
																																				+ Math.round((Constants.intS_GRAPHICS_CORRECTION_X + objLball.intGposX)
																																								* this.objGjuggleMasterPro.getFrame().fltGframeSizeRatio
																																								+ this.objGjuggleMasterPro.getFrame().intGballsWidth
																																								/ 2.0F),
																																			4,
																																			true,
																																			false),
																											" px, ",
																											Strings.getFixedLengthString(	this.objGjuggleMasterPro.getFrame().intGmarginHeight
																																				+ Constants.intS_GRAPHICS_SITESWAP_HEIGHT
																																				+ Math.round(objLball.intGposY
																																								* this.objGjuggleMasterPro.getFrame().fltGframeSizeRatio
																																								+ this.objGjuggleMasterPro.getFrame().intGballsWidth
																																								/ 2.0F),
																																			4,
																																			true,
																																			false),
																											" px, ",
																											Strings.getFixedLengthString(	this.bolGmanyZValues
																																								? Strings.doConcat(	objLball.fltGposZ >= 0
																																																			? '+'
																																																			: null,
																																													objLball.fltGposZ
																																														+ Tools.getSign(objLball.fltGposZ)
																																														* 0.0001F)
																																								: (int) objLball.fltGposZ,
																																			5,
																																			!this.bolGmanyZValues,
																																			true,
																																			this.bolGmanyZValues
																																								? '0'
																																								: ' '),
																											" px)") : Strings.strS_EMPTY;
							break;

						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_Z_ORDER:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			bolLplayable
																						? Strings.doConcat(DataJFrame.getZOrderChar(objLball.bolGbodyBallZOrder,
																																	objLball.bolGarmsBallZOrder))
																						: Strings.strS_EMPTY;
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THICKNESS:
							final float fltLvalue =
													(2 * Constants.intS_GRAPHICS_BALL_MAXIMUM_RADIUS * this.objGjuggleMasterPro.intGdrawingSize)
														/ Constants.intS_GRAPHICS_MAXIMUM_DRAWING_SIZE;
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			Strings.doConcat(	this.bolGnegativeZValues
																														? Strings.getFixedLengthString(	fltLvalue,
																																						4,
																																						true,
																																						true)
																														: fltLvalue,
																								" px");
							break;
						case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_VISIBILITY:
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			bolLplayable
																						? this.objGcontrolJFrame.getLanguageString(objLball.bolGvisible
																																						? Language.intS_BUTTON_YES
																																						: Language.intS_BUTTON_NO)
																						: Strings.strS_EMPTY;
							break;
						default:
							strLvalueAA[intLrowIndex][intLcolumnIndex] = Strings.strS_EMPTY;
					}
				}
			}

			// Set new values :
			for (int intLcolumnIndex = 0; intLcolumnIndex < DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER; ++intLcolumnIndex) {
				int intLcolumnWidth = 0;
				for (int intLrowIndex = 2; intLrowIndex < 2 + this.intGballsNumber; ++intLrowIndex) {

					// if (intLcolumnIndex == DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLOR) {
					// JLabel objLjLabel = (JLabel) this.objGhandsAndBallsJTable.getValueAt(intLrowIndex, intLcolumnIndex);
					// if (objLjLabel == null) {
					// objLjLabel = new JLabel();
					// this.objGhandsAndBallsJTable.setValueAt(objLjLabel, intLrowIndex, intLcolumnIndex);
					// }
					// if (!Strings.areNullEqual(strLvalueAA[intLrowIndex][intLcolumnIndex], objLjLabel.getText())) {
					// if (strLvalueAA[intLrowIndex][intLcolumnIndex] != null) {
					// intLcolumnWidth =
					// Math.max( Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(strLvalueAA[intLrowIndex][intLcolumnIndex])
					// + DataJTable.intS_EXTRA_CELL_SPACE,
					// intLcolumnWidth);
					// }
					// objLjLabel.setText(strLvalueAA[intLrowIndex][intLcolumnIndex]);
					// // objLjLabel.setForeground(objLball.intGcolor);
					// // this.objGhandsAndBallsJTable.setValueAt(new JLabel(strLvalueAA[intLrowIndex][intLcolumnIndex]), intLrowIndex, intLcolumnIndex);
					// }
					// continue;
					// }
					if (!Strings.areNullEqual(	strLvalueAA[intLrowIndex][intLcolumnIndex],
												Strings.toString(this.objGhandsAndBallsJTable.getValueAt(intLrowIndex, intLcolumnIndex)))) {
						if (strLvalueAA[intLrowIndex][intLcolumnIndex] != null) {
							intLcolumnWidth =
												Math.max(Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(strLvalueAA[intLrowIndex][intLcolumnIndex])
															+ DataJTable.intS_EXTRA_CELL_SPACE, intLcolumnWidth);
						}
						this.objGhandsAndBallsJTable.setValueAt(strLvalueAA[intLrowIndex][intLcolumnIndex], intLrowIndex, intLcolumnIndex);
					}
				}
				if (this.objGcontentAdjustmentJToggleButton.isSelected()
					&& intLcolumnWidth > this.objGhandsAndBallsJTable.getColumnWidth(intLcolumnIndex)) {
					this.objGhandsAndBallsJTable.setColumnWidth(intLcolumnIndex, intLcolumnWidth);
				}
			}
		}
	}

	final public void setButtonsJLabels() {
		this.objGcloseWindowJToggleButton.setText();
		this.objGcontentAdjustmentJToggleButton.setText();
		this.objGwindowAdjustmentJToggleButton.setText();
	}

	final private void setHandsAndBallsTableSize() {

		final int intLballsPreviousNumber = this.intGballsNumber;

		this.intGballsNumber =
								Math.max(	0,
											this.objGjuggleMasterPro.objGsiteswap == null
												|| this.objGjuggleMasterPro.objGsiteswap.bytGstatus <= Constants.bytS_STATE_SITESWAP_UNKNOWN_BALLS_NUMBER
																																							? 0
																																							: this.objGjuggleMasterPro.objGsiteswap.intGballsNumber);
		if (this.intGballsNumber != intLballsPreviousNumber) {
			final DefaultTableModel objLdefaultTableModel = (DefaultTableModel) this.objGhandsAndBallsJTable.getModel();
			if (this.intGballsNumber > intLballsPreviousNumber) {
				for (int intLballIndex = intLballsPreviousNumber; intLballIndex < this.intGballsNumber; ++intLballIndex) {
					objLdefaultTableModel.addRow(new String[DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER]);
				}
			} else {
				for (int intLballIndex = intLballsPreviousNumber; intLballIndex > this.intGballsNumber; --intLballIndex) {
					objLdefaultTableModel.removeRow(intLballIndex + 1);
				}
			}
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final private void setHandsValues() {

		Hand objLhand = null;
		final String strLvalueAA[][] = new String[2][DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER];
		final boolean bolLplayable = this.objGjuggleMasterPro.bytGanimationStatus != Constants.bytS_STATE_ANIMATION_UNPLAYABLE;

		// Calculate values :
		for (int intLrowIndex = 0; intLrowIndex < 2; ++intLrowIndex) {
			objLhand = this.objGjuggleMasterPro.objGhandA[intLrowIndex];
			for (int intLcolumnIndex = 0; intLcolumnIndex < DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER; ++intLcolumnIndex) {
				int intLidentifier = Constants.bytS_UNCLASS_NO_VALUE;
				try {
					intLidentifier = (Integer) (this.objGhandsAndBallsJTable.getColumnModel().getColumn(intLcolumnIndex).getIdentifier());
				} catch (final Throwable objPthrowable) {
					Tools.err("Error while determining hand-abll table column identifier");
					continue;
				}
				switch (intLidentifier) {
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_OBJECT:
						strLvalueAA[intLrowIndex][intLcolumnIndex] =
																		this.objGcontrolJFrame.getLanguageString(DataJFrame.intS_HAND_STRING_LANGUAGE_INDEX_A[intLrowIndex]);
						break;
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_STATE:
						if (bolLplayable) {
							String strLlabel = null;
							switch (objLhand.bytGanimationState) {
								case 1:
									strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_THROW_CATCH_HAND_STATIC);
									break;
								case 3:
									strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_CATCH_THROW_HAND_STATIC);
									break;
								case 5:
									strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_THROW_CATCH_HAND_MOVING);
									break;
								case 7:
									strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_CATCH_THROW_HAND_MOVING);
									break;
								case 9:
									strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_THROW_CATCH_HAND_MOVING_BEFORE_1);
									break;
								case 13:
									strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_THROW_CATCH_HAND_STATIC_BEFORE_1);
									break;
								default:
									strLlabel = this.objGcontrolJFrame.getLanguageString(Language.intS_LABEL_DATA_UNKNOWN_STATE);
							}

							// strLvalueAA[intLrowIndex][intLcolumnIndex] = Strings.doConcat(objLhand.bytGanimationState, " : ", strLlabel);
							strLvalueAA[intLrowIndex][intLcolumnIndex] = strLlabel;
						} else {
							strLvalueAA[intLrowIndex][intLcolumnIndex] = Strings.strS_EMPTY;
						}
						break;

					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLOR:
						final byte bytLjugglerColorControlIndex =
																	Tools.getEnlightedStringType(	Constants.bytS_STRING_LOCAL_JUGGLER_DAY,
																									this.objGcontrolJFrame.getJuggleMasterPro().bolGlight);
						strLvalueAA[intLrowIndex][intLcolumnIndex] =
																		Strings.doConcat(	'#',
																							this.objGcontrolJFrame.getControlString(bytLjugglerColorControlIndex));
						break;
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THEORIC_DESTINATION:
						strLvalueAA[intLrowIndex][intLcolumnIndex] =
																		bolLplayable
																					? Strings.doConcat(	'(',
																										Strings.getFixedLengthString(	objLhand.bytGtheoricDestinationX,
																																		this.intGtheoricDestinationXLength,
																																		true,
																																		false),
																										", ",
																										Strings.getFixedLengthString(	objLhand.bytGtheoricDestinationY,
																																		this.intGtheoricDestinationYLength,
																																		true,
																																		false),
																										", ",
																										Strings.getFixedLengthString(	objLhand.bytGtheoricDestinationZ,
																																		this.intGtheoricDestinationZLength,
																																		true,
																																		false),
																										')') : Strings.strS_EMPTY;
						break;
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_LAST_AND_NEXT_CATCHES:
						strLvalueAA[intLrowIndex][intLcolumnIndex] =
																		bolLplayable ? Strings.doConcat(objLhand.intGlastCatchTimer,
																										" / (",
																										this.objGjuggleMasterPro.lngGthrowsCount,
																										") / ",
																										objLhand.intGnextCatchTimer)
																					: Strings.strS_EMPTY;
						break;
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_GRAPHIC_POSITION:
						strLvalueAA[intLrowIndex][intLcolumnIndex] =
																		bolLplayable
																					? Strings.doConcat(	'(',
																										Strings.getFixedLengthString(	this.objGjuggleMasterPro.getFrame().intGmarginWidth
																																			+ Math.round((Constants.intS_GRAPHICS_CORRECTION_X + objLhand.intGposX)
																																							* this.objGjuggleMasterPro.getFrame().fltGframeSizeRatio
																																							+ this.objGjuggleMasterPro.getFrame().intGhandsWidth
																																							/ 2.0F),
																																		4,
																																		true,
																																		false),
																										" px, ",
																										Strings.getFixedLengthString(	this.objGjuggleMasterPro.getFrame().intGmarginHeight
																																			+ Constants.intS_GRAPHICS_SITESWAP_HEIGHT
																																			+ Math.round(objLhand.intGposY
																																							* this.objGjuggleMasterPro.getFrame().fltGframeSizeRatio
																																							+ this.objGjuggleMasterPro.getFrame().intGhandsHeight
																																							/ 2.0F),
																																		4,
																																		true,
																																		false),
																										" px, ",
																										Strings.getFixedLengthString(	this.bolGmanyZValues
																																							? Strings.doConcat(	objLhand.fltGposZ >= 0
																																																		? '+'
																																																		: null,
																																												objLhand.fltGposZ
																																													+ Tools.getSign(objLhand.fltGposZ)
																																													* 0.00001F)
																																							: (int) objLhand.fltGposZ,
																																		5,
																																		!this.bolGmanyZValues,
																																		true,
																																		this.bolGmanyZValues
																																							? '0'
																																							: ' '),
																										" px)") : Strings.strS_EMPTY;
						break;
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_Z_ORDER:
						strLvalueAA[intLrowIndex][intLcolumnIndex] =
																		bolLplayable
																					? Strings.doConcat(DataJFrame.getZOrderChar(objLhand.bolGbodyHandZOrder,
																																objLhand.bolGarmHandZOrder))
																					: Strings.strS_EMPTY;
						break;
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_THICKNESS:
						if (bolLplayable) {
							final float fltLvalue =
													this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT].getArmWidth(objLhand.fltGposZ,
																																	(byte) (objLhand.bolGarmHandZOrder
																																										? 0
																																										: 1),
																																	objLhand.bolGarmHandZOrder) + 0.0001F;
							strLvalueAA[intLrowIndex][intLcolumnIndex] =
																			Strings.doConcat(	Strings.getFixedLengthString(fltLvalue, 3, true, true),
																								" px");
						} else {
							strLvalueAA[intLrowIndex][intLcolumnIndex] = Strings.strS_EMPTY;
						}
						break;
					case DataJFrame.bytS_HANDS_AND_BALLS_TABLE_VISIBILITY:
						strLvalueAA[intLrowIndex][intLcolumnIndex] =
																		bolLplayable
																					? Strings.doConcat(objLhand.bolGarmVisible
																																? objLhand.bolGhandVisible
																																							? this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_YES)
																																							: "½-"
																																: objLhand.bolGhandVisible
																																							? "-½"
																																							: this.objGcontrolJFrame.getLanguageString(Language.intS_BUTTON_NO))
																					: Strings.strS_EMPTY;
						break;
					default:
						strLvalueAA[intLrowIndex][intLcolumnIndex] = Strings.strS_EMPTY;
						break;
				}
				try {
					if (Strings.areNullEqual(strLvalueAA[intLrowIndex][intLcolumnIndex], this.objGhandsAndBallsJTable.getValueAt(	intLrowIndex,
																																	intLcolumnIndex))) {
						strLvalueAA[intLrowIndex][intLcolumnIndex] = null;
					}
				} catch (final Throwable objPthrowable) {}
			}
		}

		// Set values :
		for (int intLcolumnIndex = 0; intLcolumnIndex < DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER; ++intLcolumnIndex) {
			int intLcolumnWidth = 0;
			for (int intLrowIndex = 0; intLrowIndex < 2; ++intLrowIndex) {
				if (strLvalueAA[intLrowIndex][intLcolumnIndex] != null) {
					intLcolumnWidth =
										Math.max(Constants.objS_GRAPHICS_FONT_METRICS.stringWidth(strLvalueAA[intLrowIndex][intLcolumnIndex])
													+ DataJTable.intS_EXTRA_CELL_SPACE, intLcolumnWidth);
					this.objGhandsAndBallsJTable.setValueAt(strLvalueAA[intLrowIndex][intLcolumnIndex], intLrowIndex, intLcolumnIndex);
				}
			}
			if (this.objGcontentAdjustmentJToggleButton.isSelected()
				&& intLcolumnWidth > this.objGhandsAndBallsJTable.getColumnWidth(intLcolumnIndex)) {
				this.objGhandsAndBallsJTable.setColumnWidth(intLcolumnIndex, intLcolumnWidth);
			}
		}

	}

	final public void setHeaders() {

		// Animation headers :
		try {
			final TableColumnModel objLtableColumnModel = this.objGanimationJTable.getColumnModel();
			for (int intLcolumnIndex = 0; intLcolumnIndex < DataJFrame.bytS_ANIMATION_TABLE_COLUMNS_NUMBER; ++intLcolumnIndex) {
				final TableColumn objLtableColumn = objLtableColumnModel.getColumn(intLcolumnIndex);
				final int intLidentifier = (Integer) objLtableColumn.getIdentifier();
				objLtableColumn.setHeaderValue(this.objGcontrolJFrame.getLanguageString(DataJFrame.intS_ANIMATION_TABLE_HEADER_LANGUAGE_INDEX_A[intLidentifier]));
				new JTableHeader();
			}
			this.objGanimationJTable.setContentAdjustedColumnsWidths();
		} catch (final Throwable objPthrowable) {}

		// Hand & ball headers :
		try {
			final TableColumnModel objLtableColumnModel = this.objGhandsAndBallsJTable.getColumnModel();
			for (int intLcolumnIndex = 0; intLcolumnIndex < DataJFrame.bytS_HANDS_AND_BALLS_TABLE_COLUMNS_NUMBER; ++intLcolumnIndex) {
				final TableColumn objLtableColumn = objLtableColumnModel.getColumn(intLcolumnIndex);
				final int intLidentifier = (Integer) objLtableColumn.getIdentifier();
				objLtableColumn.setHeaderValue(this.objGcontrolJFrame.getLanguageString(DataJFrame.intS_HANDS_AND_BALLS_TABLE_HEADER_LANGUAGE_INDEX_A[intLidentifier]));
			}
			this.objGhandsAndBallsJTable.setContentAdjustedColumnsWidths();
		} catch (final Throwable objPthrowable) {}
	}

	@Override final public void setSize(Dimension objPdimension) {
		this.setSize((int) objPdimension.getWidth(), (int) objPdimension.getHeight());
	}

	@Override final public void setSize(int intPwidth, int intPheight) {
		this.removeComponentListener(this);
		super.setSize(intPwidth, intPheight);
		this.addComponentListener(this);
	}

	final private void setTheoricDestinationDefaultLengths() {

		// Set value lengths :
		this.intGtheoricDestinationXLength = 1;
		this.intGtheoricDestinationYLength = 1;
		this.intGtheoricDestinationZLength = 1;
		byte bytLvalueZ = 0;
		final Style objLcurrentStyle = this.objGjuggleMasterPro.objGstyleA[Constants.bytS_UNCLASS_CURRENT];
		final byte bytLstyleA[] = objLcurrentStyle.bytGstyleA;
		this.bolGmanyZValues = false;
		this.bolGnegativeZValues = false;
		for (int intLstyleValueIndex = 0; intLstyleValueIndex < bytLstyleA.length; intLstyleValueIndex +=
																											(Constants.bytS_ENGINE_COORDONATES_NUMBER * 2)) {
			if (intLstyleValueIndex != 0) {
				this.bolGmanyZValues |= (bytLvalueZ != bytLstyleA[intLstyleValueIndex + 2]);
			}
			final int intLzOrderIndex = intLstyleValueIndex / (Constants.bytS_ENGINE_COORDONATES_NUMBER * 2);
			bytLvalueZ =
							(byte) (bytLstyleA[intLstyleValueIndex + 2] * Tools.getSignedBoolean((objLcurrentStyle.bolGbodyBallsZOrderA[intLzOrderIndex] || !objLcurrentStyle.bolGballsVisibleA[intLzOrderIndex])
																									&& (objLcurrentStyle.bolGbodyArmsZOrderA[intLzOrderIndex] || !objLcurrentStyle.bolGhandsVisibleA[intLzOrderIndex]
																																									&& !objLcurrentStyle.bolGarmsVisibleA[intLzOrderIndex])));
			this.bolGnegativeZValues |= bytLvalueZ < 0;
			this.intGtheoricDestinationXLength = Math.max(this.intGtheoricDestinationXLength, Tools.getLength(bytLstyleA[intLstyleValueIndex]));
			this.intGtheoricDestinationYLength = Math.max(this.intGtheoricDestinationYLength, Tools.getLength(bytLstyleA[intLstyleValueIndex + 1]));
			this.intGtheoricDestinationZLength = Math.max(this.intGtheoricDestinationZLength, Tools.getLength(bytLvalueZ));
		}
	}

	final public void setValues() {
		if (this.bolGvisible) {
			this.setAnimationValues();
			this.setHandsValues();
			this.setBallsValues();
		}
	}

	final public void setVisible() {
		this.setVisible(this.bolGvisible);
	}

	@Override final public void setVisible(boolean bolPvisible) {

		this.bolGvisible = bolPvisible;
		if (bolPvisible) {
			this.setHandsAndBallsTableSize();
			this.initAnimationValues();
			this.setValues();
			this.setTheoricDestinationDefaultLengths();
			this.doAddWidgets();
		}
		super.setVisible(bolPvisible);
		this.intGframeHeight = this.getHeight();
		this.objGcontrolJFrame.objGdataJCheckBoxMenuItem.selectState(bolPvisible);
		this.objGcontrolJFrame.objGdataJCheckBoxMenuItem.setLabels();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowActivated(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosed(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowClosing(WindowEvent objPwindowEvent) {
		this.objGcontrolJFrame.objGdataJCheckBoxMenuItem.selectState(false);
		this.objGjuggleMasterPro.doAddAction(Constants.intS_ACTION_HIDE_DATA_FRAME);
		// this.setVisible(false);
		// this.dispose();
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeactivated(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowDeiconified(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowIconified(WindowEvent objPwindowEvent) {}

	/**
	 * Method description
	 * 
	 * @see
	 * @param objPwindowEvent
	 */
	@Override final public void windowOpened(WindowEvent objPwindowEvent) {}
}

/*
 * @(#)DataJFrame.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
