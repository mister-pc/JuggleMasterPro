/*
 * @(#)ExtendedGridBagConstraints.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package fr.jugglemaster.control.util;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import fr.jugglemaster.util.Constants;
import fr.jugglemaster.util.Strings;
import fr.jugglemaster.util.Tools;

/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class ExtendedGridBagConstraints extends GridBagConstraints {

	public ExtendedGridBagConstraints() { // 0
		this(GridBagConstraints.RELATIVE, 0, 1, 1, GridBagConstraints.WEST, 0, 0, 0, 0, 0, 0, GridBagConstraints.NONE, 0.0F, 0.0F);
	}

	public ExtendedGridBagConstraints(int intPfill, double dblPweightX, double dblPweightY) { // 3
		this(	GridBagConstraints.RELATIVE,
				GridBagConstraints.RELATIVE,
				1,
				1,
				GridBagConstraints.WEST,
				0,
				0,
				0,
				0,
				0,
				0,
				intPfill,
				dblPweightX,
				dblPweightY);
	}

	public ExtendedGridBagConstraints( // 2
										int intPgridX,
										int intPgridY) {
		this(intPgridX, intPgridY, 1, 1, GridBagConstraints.WEST, 0, 0, 0, 0, 0, 0, GridBagConstraints.NONE, 0.0F, 0.0F);
	}

	public ExtendedGridBagConstraints( // 4
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight) {
		this(intPgridX, intPgridY, intPgridWidth, intPgridHeight, GridBagConstraints.WEST, 0, 0, 0, 0, 0, 0, GridBagConstraints.NONE, 0.0F, 0.0F);
	}

	// = new ExtendedGridBagConstraints(
	// [ intPgridX = ExtendedGridBagConstraints.RELATIVE,
	// intPgridY = 0,
	// [ intPgridWidth = 1,
	// intPgridHeight = 1,
	// [ intPinsideLocation = ExtendedGridBagConstraints.WEST,
	// [ intPinsideSpaceX = 0,
	// intPinsideSpaceY = 0
	// ]
	// ],
	// [ intPtopMargin = 0,
	// intPbottomMargin = 0,
	// intPleftMargin = 0,
	// intPrightMargin = 0
	// ],
	// [ intPfill = ExtendedGridBagConstraints.NONE,
	// dblPweightX = 0.0F,
	// dblPweightY = 0.0F
	// ]
	// ])

	public ExtendedGridBagConstraints( // 5
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation) {
		this(intPgridX, intPgridY, intPgridWidth, intPgridHeight, intPinsideLocation, 0, 0, 0, 0, 0, 0, GridBagConstraints.NONE, 0.0F, 0.0F);
	}

	public ExtendedGridBagConstraints( // 5 + 2
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPfill,
										double dblPweightX,
										double dblPweightY) {
		this(intPgridX, intPgridY, intPgridWidth, intPgridHeight, GridBagConstraints.WEST, 0, 0, 0, 0, 0, 0, intPfill, dblPweightX, dblPweightY);
	}

	public ExtendedGridBagConstraints( // 6 + 2
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation,
										int intPfill,
										double dblPweightX,
										double dblPweightY) {
		this(intPgridX, intPgridY, intPgridWidth, intPgridHeight, intPinsideLocation, 0, 0, 0, 0, 0, 0, intPfill, dblPweightX, dblPweightY);
	}

	public ExtendedGridBagConstraints( // 7
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation,
										int intPinsideSpaceX,
										int intPinsideSpaceY) {
		this(	intPgridX,
				intPgridY,
				intPgridWidth,
				intPgridHeight,
				intPinsideLocation,
				intPinsideSpaceX,
				intPinsideSpaceY,
				0,
				0,
				0,
				0,
				GridBagConstraints.NONE,
				0.0F,
				0.0F);
	}

	public ExtendedGridBagConstraints( // 8
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPtopMargin,
										int intPbottomMargin,
										int intPleftMargin,
										int intPrightMargin) {
		this(	intPgridX,
				intPgridY,
				intPgridWidth,
				intPgridHeight,
				GridBagConstraints.WEST,
				0,
				0,
				intPtopMargin,
				intPbottomMargin,
				intPleftMargin,
				intPrightMargin,
				GridBagConstraints.NONE,
				0.0F,
				0.0F);
	}

	public ExtendedGridBagConstraints( // 8 + 2
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation,
										int intPinsideSpaceX,
										int intPinsideSpaceY,
										int intPfill,
										double dblPweightX,
										double dblPweightY) {
		this(	intPgridX,
				intPgridY,
				intPgridWidth,
				intPgridHeight,
				intPinsideLocation,
				intPinsideSpaceX,
				intPinsideSpaceY,
				0,
				0,
				0,
				0,
				intPfill,
				dblPweightX,
				dblPweightY);
	}

	public ExtendedGridBagConstraints( // 9
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation,
										int intPtopMargin,
										int intPbottomMargin,
										int intPleftMargin,
										int intPrightMargin) {
		this(	intPgridX,
				intPgridY,
				intPgridWidth,
				intPgridHeight,
				intPinsideLocation,
				0,
				0,
				intPtopMargin,
				intPbottomMargin,
				intPleftMargin,
				intPrightMargin,
				GridBagConstraints.NONE,
				0.0F,
				0.0F);
	}

	public ExtendedGridBagConstraints( // 9 + 2
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPtopMargin,
										int intPbottomMargin,
										int intPleftMargin,
										int intPrightMargin,
										int intPfill,
										double dblPweightX,
										double dblPweightY) {
		this(	intPgridX,
				intPgridY,
				intPgridWidth,
				intPgridHeight,
				GridBagConstraints.WEST,
				0,
				0,
				intPtopMargin,
				intPbottomMargin,
				intPleftMargin,
				intPrightMargin,
				intPfill,
				dblPweightX,
				dblPweightY);
	}

	public ExtendedGridBagConstraints( // 10 + 2
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation,
										int intPtopMargin,
										int intPbottomMargin,
										int intPleftMargin,
										int intPrightMargin,
										int intPfill,
										double dblPweightX,
										double dblPweightY) {
		this(	intPgridX,
				intPgridY,
				intPgridWidth,
				intPgridHeight,
				intPinsideLocation,
				0,
				0,
				intPtopMargin,
				intPbottomMargin,
				intPleftMargin,
				intPrightMargin,
				intPfill,
				dblPweightX,
				dblPweightY);
	}

	public ExtendedGridBagConstraints( // 11
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation,
										int intPinsideSpaceX,
										int intPinsideSpaceY,
										int intPtopMargin,
										int intPbottomMargin,
										int intPleftMargin,
										int intPrightMargin) {
		this(	intPgridX,
				intPgridY,
				intPgridWidth,
				intPgridHeight,
				intPinsideLocation,
				intPinsideSpaceX,
				intPinsideSpaceY,
				intPtopMargin,
				intPbottomMargin,
				intPleftMargin,
				intPrightMargin,
				GridBagConstraints.NONE,
				0.0F,
				0.0F);
	}

	public ExtendedGridBagConstraints( // 12 + 2
										int intPgridX,
										int intPgridY,
										int intPgridWidth,
										int intPgridHeight,
										int intPinsideLocation,
										int intPinsideSpaceX,
										int intPinsideSpaceY,
										int intPtopMargin,
										int intPbottomMargin,
										int intPleftMargin,
										int intPrightMargin,
										int intPfill,
										double dblPweightX,
										double dblPweightY) {

		this.setGridBounds(intPgridX, intPgridY, intPgridWidth, intPgridHeight);
		this.setInside(intPinsideLocation, intPinsideSpaceX, intPinsideSpaceY);
		this.setMargins(intPtopMargin, intPbottomMargin, intPleftMargin, intPrightMargin);
		this.setFilling(intPfill, dblPweightX, dblPweightY);

	}

	final public int getBottomMargin() {
		return this.insets.bottom;
	}

	final public int getFillingMode() {
		return this.fill;
	}

	final Dimension getFillingWeights() {
		return new Dimension((int) this.weightx, (int) this.weighty);
	}

	final public Rectangle getGridBounds() {
		return new Rectangle(this.gridx, this.gridy, this.gridwidth, this.gridheight);
	}

	final public int getGridHeight() {
		return this.gridheight;
	}

	final public Dimension getGridLocation() {
		return new Dimension(this.gridx, this.gridy);
	}

	final public Dimension getGridSize() {
		return new Dimension(this.gridwidth, this.gridheight);
	}

	final public int getGridWidth() {
		return this.gridwidth;
	}

	final public int getGridX() {
		return this.gridx;
	}

	final public int getGridY() {
		return this.gridy;
	}

	final public Dimension getHorizontalMargins() {
		return new Dimension(this.insets.left, this.insets.right);
	}

	final public int getInsideLocation() {
		return this.anchor;
	}

	final public Dimension getInsideSpaces() {
		return new Dimension(this.ipadx, this.ipady);
	}

	final public int getLeftMargin() {
		return this.insets.left;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPtop
	 * @param intPbottom
	 * @param intPleft
	 * @param intPright
	 * @return
	 */
	final public Rectangle getMargins() {
		return new Rectangle(this.insets.top, this.insets.bottom, this.insets.left, this.insets.right);
	}

	final public int getRightMargin() {
		return this.insets.right;
	}

	final public int getTopMargin() {
		return this.insets.top;
	}

	final public Dimension getVerticalMargins() {
		return new Dimension(this.insets.top, this.insets.bottom);
	}

	final public ExtendedGridBagConstraints setBottomMargin(int intPbottom) {
		if (intPbottom >= 0) {
			this.insets = new Insets(this.insets.top, this.insets.left, intPbottom, this.insets.right);
		} else {
			Tools.err("bad grid bottom margin value : ", intPbottom);
		}
		return this;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPfill
	 * @param dblPweightX
	 * @param dblPweightY
	 * @return
	 */
	final public ExtendedGridBagConstraints setFilling(int intPfillingMode, double dblPweightX, double dblPweightY) {
		this.setWeights(dblPweightX, dblPweightY);
		this.setFillingMode(intPfillingMode);
		return this;
	}

	final public ExtendedGridBagConstraints setFillingMode(int intPfillingMode) {
		switch (intPfillingMode) {
			case GridBagConstraints.NONE:
				this.weightx = this.weighty = 0.0F;
				//$FALL-THROUGH$
			case GridBagConstraints.HORIZONTAL:
			case GridBagConstraints.VERTICAL:
			case GridBagConstraints.BOTH:
				this.fill = intPfillingMode;
				break;
			default:
				Tools.err("bad grid filling value : ", intPfillingMode);
				break;
		}
		return this;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPgridX
	 * @param intPgridY
	 * @param intPgridWidth
	 * @param intPgridHeight
	 * @return
	 */
	final public ExtendedGridBagConstraints setGridBounds(int intPgridX, int intPgridY, int intPgridWidth, int intPgridHeight) {
		this.setGridLocation(intPgridX, intPgridY);
		this.setGridSize(intPgridWidth, intPgridHeight);
		return this;
	}

	final public ExtendedGridBagConstraints setGridHeight(int intPgridHeight) {
		if (intPgridHeight > 0 || intPgridHeight == GridBagConstraints.REMAINDER || intPgridHeight == GridBagConstraints.RELATIVE) {
			this.gridheight = intPgridHeight;
		} else {
			Tools.err("Bad vertical width value : ", intPgridHeight);
		}
		return this;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPgridX
	 * @param intPgridY
	 * @return
	 */
	final public ExtendedGridBagConstraints setGridLocation(int intPgridX, int intPgridY) {
		this.setGridX(intPgridX);
		this.setGridY(intPgridY);
		return this;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPgridWidth
	 * @param intPgridHeight
	 * @return
	 */
	final public ExtendedGridBagConstraints setGridSize(int intPgridWidth, int intPgridHeight) {
		this.setGridWidth(intPgridWidth);
		this.setGridHeight(intPgridHeight);
		return this;
	}

	final public ExtendedGridBagConstraints setGridWidth(int intPgridWidth) {
		if (intPgridWidth > 0 || intPgridWidth == GridBagConstraints.REMAINDER || intPgridWidth == GridBagConstraints.RELATIVE) {
			this.gridwidth = intPgridWidth;
		} else {
			Tools.err("Bad horizontal width value : ", intPgridWidth);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setGridX(int intPgridX) {
		if (intPgridX >= 0 || intPgridX == GridBagConstraints.RELATIVE) {
			this.gridx = intPgridX;
		} else {
			Tools.err("bad horizontal grid position : ", intPgridX);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setGridY(int intPgridY) {
		if (intPgridY >= 0 || intPgridY == GridBagConstraints.RELATIVE) {
			this.gridy = intPgridY;
		} else {
			Tools.err("bad vertical grid position : ", intPgridY);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setHorizontalMargins(int intPhorizontalMargin) {
		if (intPhorizontalMargin >= 0) {
			this.insets = new Insets(this.insets.top, intPhorizontalMargin, this.insets.bottom, intPhorizontalMargin);
		} else {
			Tools.err("bad grid horizontal margin value : ", intPhorizontalMargin);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setHorizontalMargins(int intPleft, int intPright) {
		if (intPleft >= 0 && intPright >= 0) {
			this.insets = new Insets(this.insets.top, intPleft, this.insets.bottom, intPright);
		} else {
			Tools.err("bad grid horizontal margin value : (", intPleft, ", ", intPright, ')');
		}
		return this;
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param intPinsideLocation
	 * @param intPinsideSpaceX
	 * @param intPinsideSpaceY
	 * @return
	 */
	final public ExtendedGridBagConstraints setInside(int intPinsideLocation, int intPinsideSpaceX, int intPinsideSpaceY) {

		this.setInsideLocation(intPinsideLocation);
		this.setInsideSpaces(intPinsideSpaceX, intPinsideSpaceY);
		return this;
	}

	final public ExtendedGridBagConstraints setInsideLocation(int intPinsideLocation) {
		switch (intPinsideLocation) {
			case GridBagConstraints.PAGE_START:
			case GridBagConstraints.PAGE_END:
			case GridBagConstraints.LINE_START:
			case GridBagConstraints.LINE_END:
			case GridBagConstraints.FIRST_LINE_START:
			case GridBagConstraints.FIRST_LINE_END:
			case GridBagConstraints.LAST_LINE_START:
			case GridBagConstraints.LAST_LINE_END:
			case GridBagConstraints.BASELINE:
			case GridBagConstraints.BASELINE_LEADING:
			case GridBagConstraints.BASELINE_TRAILING:
			case GridBagConstraints.ABOVE_BASELINE:
			case GridBagConstraints.ABOVE_BASELINE_LEADING:
			case GridBagConstraints.ABOVE_BASELINE_TRAILING:
			case GridBagConstraints.BELOW_BASELINE:
			case GridBagConstraints.BELOW_BASELINE_LEADING:
			case GridBagConstraints.BELOW_BASELINE_TRAILING:
				Tools.err("strange grid anchor value : ", intPinsideLocation);
				//$FALL-THROUGH$
			case GridBagConstraints.CENTER:
			case GridBagConstraints.NORTH:
			case GridBagConstraints.NORTHWEST:
			case GridBagConstraints.NORTHEAST:
			case GridBagConstraints.SOUTH:
			case GridBagConstraints.SOUTHWEST:
			case GridBagConstraints.SOUTHEAST:
			case GridBagConstraints.WEST:
			case GridBagConstraints.EAST:
				this.anchor = intPinsideLocation;
				break;
			default:
				Tools.err("bad grid anchor value : ", intPinsideLocation);
		}

		return this;
	}

	final public ExtendedGridBagConstraints setInsideSpaces(int intPinsideSpaceX, int intPinsideSpaceY) {

		if (intPinsideSpaceX >= 0) {
			this.ipadx = intPinsideSpaceX;
		} else {
			Tools.err("bad horizontal padding value : ", intPinsideSpaceX);
		}
		if (intPinsideSpaceY >= 0) {
			this.ipady = intPinsideSpaceY;
		} else {
			Tools.err("bad horizontal padding value : ", intPinsideSpaceY);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setLeftMargin(int intPleft) {
		if (intPleft >= 0) {
			this.insets = new Insets(this.insets.top, intPleft, this.insets.bottom, this.insets.right);
		} else {
			Tools.err("bad grid left margin value : ", intPleft);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setMargins(int intPtop, int intPbottom, int intPleft, int intPright) {
		if (intPtop >= 0 && intPbottom >= 0 && intPleft >= 0 && intPright >= 0) {
			this.insets = new Insets(intPtop, intPleft, intPbottom, intPright);
		} else {
			Tools.err("bad grid margin value : (", intPtop, ", ", intPbottom, ", ", intPleft, ", ", intPright, ')');
		}
		return this;
	}

	final public ExtendedGridBagConstraints setRightMargin(int intPright) {
		if (intPright >= 0) {
			this.insets = new Insets(this.insets.top, this.insets.left, this.insets.bottom, intPright);
		} else {
			Tools.err("bad grid right margin value : ", intPright);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setTopMargin(int intPtop) {
		if (intPtop >= 0) {
			this.insets = new Insets(intPtop, this.insets.left, this.insets.bottom, this.insets.right);
		} else {
			Tools.err("bad grid top margin value : ", intPtop);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setVerticalMargins(int intPverticalMargin) {
		if (intPverticalMargin >= 0) {
			this.insets = new Insets(intPverticalMargin, this.insets.left, intPverticalMargin, this.insets.right);
		} else {
			Tools.err("bad grid vertical margin value : ", intPverticalMargin);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setVerticalMargins(int intPtop, int intPbottom) {
		if (intPtop >= 0 && intPbottom >= 0) {
			this.insets = new Insets(intPtop, this.insets.left, intPbottom, this.insets.right);
		} else {
			Tools.err("bad grid vertical margin value : (", intPtop, ", ", intPbottom, ')');
		}
		return this;
	}

	final public ExtendedGridBagConstraints setWeights(double dblPweight) {

		if (dblPweight >= 0) {
			this.setWeightX(dblPweight);
			this.setWeightY(dblPweight);
		} else {
			Tools.err("bad weight value : ", dblPweight);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setWeights(double dblPweightX, double dblPweightY) {

		if (dblPweightX >= 0 && dblPweightY >= 0) {
			this.setWeightX(dblPweightX);
			this.setWeightY(dblPweightY);
		} else {
			Tools.err("bad weight value : (", dblPweightX, ", ", dblPweightY, ')');
		}
		return this;
	}

	final public ExtendedGridBagConstraints setWeightX(double dblPweightX) {

		if (dblPweightX >= 0) {
			this.weightx = dblPweightX;
			this.fill =
						this.weighty > 0 ? dblPweightX > 0 ? GridBagConstraints.BOTH : GridBagConstraints.VERTICAL
										: dblPweightX > 0 ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;
		} else {
			Tools.err("bad weight x value : ", dblPweightX);
		}
		return this;
	}

	final public ExtendedGridBagConstraints setWeightY(double dblPweightY) {

		if (dblPweightY >= 0) {
			this.weighty = dblPweightY;
			this.fill =
						this.weightx > 0 ? dblPweightY > 0 ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL
										: dblPweightY > 0 ? GridBagConstraints.VERTICAL : GridBagConstraints.NONE;
		} else {
			Tools.err("bad weight y value : ", dblPweightY);
		}
		return this;
	}

	@Override final public String toString() {

		String strLinside = Strings.strS_EMPTY;
		switch (this.anchor) {
			case GridBagConstraints.CENTER:
				strLinside = "center";
				break;
			case GridBagConstraints.NORTH:
				strLinside = "north";
				break;
			case GridBagConstraints.NORTHWEST:
				strLinside = "northWest";
				break;
			case GridBagConstraints.NORTHEAST:
				strLinside = "northEast";
				break;
			case GridBagConstraints.SOUTH:
				strLinside = "south";
				break;
			case GridBagConstraints.SOUTHWEST:
				strLinside = "southWest";
				break;
			case GridBagConstraints.SOUTHEAST:
				strLinside = "southEast";
				break;
			case GridBagConstraints.WEST:
				strLinside = "west";
				break;
			case GridBagConstraints.EAST:
				strLinside = "east";
				break;
			default:
				strLinside = "unknown inside location";
		}

		return Strings.doConcat("gridPosition      = (",
								this.gridx == GridBagConstraints.RELATIVE ? "relative" : Integer.valueOf(this.gridx).toString(),
								", ",
								this.gridy == GridBagConstraints.RELATIVE ? "relative" : Integer.valueOf(this.gridy).toString(),
								')',
								Strings.strS_LINE_SEPARATOR,
								"gridSize          = (",
								this.gridwidth,
								", ",
								this.gridheight,
								')',
								Strings.strS_LINE_SEPARATOR,
								"inside            = (",
								strLinside,
								", ",
								this.ipadx,
								", ",
								this.ipady,
								')',
								Strings.strS_LINE_SEPARATOR,
								"verticalMargins   = (",
								this.insets.top,
								", ",
								this.insets.bottom,
								')',
								Strings.strS_LINE_SEPARATOR,
								"horizontalMargins = (",
								this.insets.left,
								", ",
								this.insets.right,
								')',
								Strings.strS_LINE_SEPARATOR,
								"fillMode          = ",
								this.fill == GridBagConstraints.BOTH
																	? "both"
																	: this.fill == GridBagConstraints.HORIZONTAL
																												? "horizontal"
																												: this.fill == GridBagConstraints.VERTICAL
																																							? "vertical"
																																							: "none",
								Strings.strS_LINE_SEPARATOR,
								"weights           = (",
								this.weightx,
								", ",
								this.weighty,
								')',
								Strings.strS_LINE_SEPARATOR);
	}

	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;
}

/*
 * @(#)ExtendedGridBagConstraints.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
