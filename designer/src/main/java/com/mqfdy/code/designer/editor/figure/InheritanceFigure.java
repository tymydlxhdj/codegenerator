package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.PointList;

import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.model.Inheritance;

/**
 * 
 * 关系连线对象的显示Figure
 * 
 * A figure representing 3 types of uml2 elements : aggregation, inheritance and
 * composition. A connecting figure is created between two node figures of a
 * class diagram. The connection is created using a PolylineConnection. The
 * figure can own :
 * <ul>
 * <li>a general description (captionLabel)</li>
 * <li>a start multiplicity (startMultiplicityLabel)</li>
 * <li>an end multiplicity (startMultiplicityLabel)</li>
 * <li>a description for each mulitplicity (startMultiplicityDescriptionLabel /
 * endMultiplicityDescriptionLabel)</li>
 * </ul>
 * 
 * 
 * @author mqfdy
 * 
 */
public class InheritanceFigure extends ConnectionFigure {
	// 箭头大小设置
	private static final int ARROW_SIZE = 2;

	// distance of startMultiplicity from startFigure
	private int startMultUDist = 8;

	private int startMultVDist = -5;

	// distance of startMultiplicityDescription from startFigure
	private int startMultDescUDist = 0;

	private int startMultDescVDist = 5;

	// distance of endMultiplicity from endFigure, depends on type
	private int endMultUDist = 8;

	private int endMultVDist = -5;

	// distance of endMultiplicityDescription from endFigure, depends on type
	private int endMultDescUDist = 0;

	private int endMultLabelVDist = 5;

	// possible Labels owned by connection (not all may be used)
	// multiplicity Labels
	private Label startMultiplicityLabel = new Label();

	private Label startMultiplicityDescriptionLabel = new Label();

	private Label endMultiplicityLabel = new Label();

	private Label endMultiplicityDescriptionLabel = new Label();

	// caption of connection
	private Label captionLabel = new Label();

	// decorations for connection
	private PolygonDecoration arrowDecoration;

	private PolygonDecoration aggregationDecoration;

	private PolygonDecoration compositionDecoration;

	private PolygonDecoration errorDecoration;
	
	private Inheritance inheritance;

	/**
	 * Simple constructor to create a directed inheritance, aggregation or
	 * composition with any labels.
	 * 
	 * @param type
	 *            连线类型
	 * @see IConstants
	 */
	public InheritanceFigure(String type) {
		this(true, type, null, null, null, null, null, false);
	}

	/**
	 * Constructor of a new InheritanceFigure. Creates a new PolylineConnetion,
	 * adds a decoration corresponding to the uml2-type to the connection
	 * 
	 * @param isDirected
	 *            indicates connection is directed. If so an arrow is added to
	 *            its end point.
	 * @param type
	 *            indicate the InheritanceFigureType : aggregation, inheritance
	 *            or composition
	 * @param startMultiplicity
	 *            the multiplicity type at the start point of the connection.
	 *            Must be uml2-conform. E.g. [0..1]
	 * @param endMultiplicity
	 *            the multiplicity type at the end point of the connection. Must
	 *            be uml2-conform. E.g. [0..1]
	 * @param startMultCaption
	 *            起始节点的重数
	 * @param endMultCaption
	 *            终止节点的重数
	 * @param caption
	 *            连接描述
	 * @param hasQualifier
	 *            目前未使用
	 */
	public InheritanceFigure(boolean isDirected, String type,
			String startMultiplicity, String endMultiplicity,
			String startMultCaption, String endMultCaption, String caption,
			boolean hasQualifier) {
		super();

		// set distances of endMultiplicities from endFigure
		endMultDescUDist = 0;
		endMultLabelVDist = 5;

		// define arrow
		PointList arrowPointList = new PointList();
		arrowPointList.addPoint(0, 0);
		// 给关系线加箭头
		arrowPointList.addPoint(-ARROW_SIZE, ARROW_SIZE);
		arrowPointList.addPoint(-ARROW_SIZE, -ARROW_SIZE);

		arrowDecoration = new PolygonDecoration();
		// 去掉连线上的箭头
		// 定制箭头的大小
		arrowDecoration.setTemplate(arrowPointList);
		arrowDecoration.setBackgroundColor(ColorConstants.white);

		// define decoration for aggregation : unfilled rhombus
		PointList aggregationPointList = new PointList();
		aggregationPointList.addPoint(0, 0);
		aggregationPointList.addPoint(-2, -3);
		aggregationPointList.addPoint(-4, 0);
		aggregationPointList.addPoint(-2, 3);
		aggregationDecoration = new PolygonDecoration();
		aggregationDecoration.setTemplate(aggregationPointList);
		aggregationDecoration.setBackgroundColor(ColorConstants.white);

		// define decoration for composition : filled rhombus
		PointList compositionPointList = new PointList();
		compositionPointList.addPoint(0, 0);
		compositionPointList.addPoint(-2, -3);
		compositionPointList.addPoint(-4, 0);
		compositionPointList.addPoint(-2, 3);
		compositionDecoration = new PolygonDecoration();
		compositionDecoration.setTemplate(compositionPointList);
		compositionDecoration.setBackgroundColor(ColorConstants.black);

		PointList errorPointList = new PointList();
		errorPointList.addPoint(0, 0);
		errorPointList.addPoint(-2, -3);
		errorPointList.addPoint(-4, 0);
		errorPointList.addPoint(-2, 3);
		errorDecoration = new PolygonDecoration();
		errorDecoration.setTemplate(errorPointList);
		errorDecoration.setBackgroundColor(ColorConstants.red);

		// set all values (if they exist) / fill all Labels with content
		setDirected(isDirected);
		setType(type);
		if (startMultiplicity != null) {
			setStartMultiplicity(startMultiplicity);
		}
		if (startMultCaption != null) {
			setStartMultiplicityDescription(startMultCaption);
		}
		if (endMultiplicity != null) {
			setEndMultiplicity(endMultiplicity);
		}
		if (endMultCaption != null) {
			setEndMultiplicityDescription(endMultCaption);
		}
		if (caption != null) {
			setCaption(caption);
		}

	}

	/**
	 * Sets the type of the connection : aggregation, inheritance or composition
	 * The corresponding decoration (nothing or a rhombus) is set in a certain
	 * distance.
	 * 
	 * @param type
	 *            the type of InheritanceFigure : aggreagtion, inheritance or
	 *            composition (uml2)
	 */
	public void setType(String type) {
		if (type.equals(IConstants.INHERITANCE)) {
			// inheritance -> no decoration
			// distance from endFigure; directly beside
			setForegroundColor(ColorConstants.black);
			endMultUDist = 8;
			endMultDescUDist = 0;
			setSourceDecoration(null);
		} else if (type.equals(IConstants.AGGREGATION_STR)) {
			// aggregation -> unfilled rhombus
			setSourceDecoration(aggregationDecoration);

			// set distance from endFigure;
			// concern rhombus at beginning of connection
			endMultUDist = 30;
			endMultDescUDist = 30;
		} else if (type.equals(IConstants.COMPOSITION_STR)) {
			setSourceDecoration(compositionDecoration);

			// set distance from endFigure;
			// concern rhombus at beginning of connection
			endMultUDist = 30;
			endMultDescUDist = 30;
		} else if (type.equals(IConstants.ERROR_STR)) {
			setForegroundColor(ColorConstants.red);
			// set distance from endFigure;
			// concern rhombus at beginning of connection
			endMultUDist = 30;
			endMultDescUDist = 30;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 设置外键连线的箭头
	 * 
	 * @param directed
	 *            true：外键单向关联，只有终点有箭头；false：外键双向关联，源点和终点都有箭头
	 * 
	 */
	public void setDirected(boolean directed) {

		if (directed) {
			// 外键单向关联时，只有终点有箭头
			setTargetDecoration(arrowDecoration);
		} else {
			// 外键双向关联时，源点和终点都有箭头
			setSourceDecoration(new PolygonDecoration());
			setTargetDecoration(arrowDecoration);
		}

	}

	/**
	 * Set the description of the InheritanceFigure. A Label is added in the
	 * middle of the connection. Attention: Should be called after all other
	 * set*() methods!
	 * 
	 * @param caption
	 */
	public void setCaption(String caption) {
		// create Label
		captionLabel.setText(caption);

		// place Label north of connection
		// get points which define the connection
		PointList points = getPoints();

		// get middle of connection
		int listLength = points.size();
		int middle = listLength / 2;

		// create new MidPointLocator at middle of connection
		MidpointLocator captionLocator = new MidpointLocator(this, middle - 1);

		if (this.getEnd().x == this.getStart().x) {
			// 如果是自关联就把标签放在连线的右边
			captionLocator.setRelativePosition(PositionConstants.EAST);

		} else {

			// set Label position north of connection
			captionLocator.setRelativePosition(PositionConstants.NORTH);
		}

		// add Label to connection
		add(captionLabel, captionLocator);
	}

	/**
	 * Set multiplicity at end point of connection.
	 * 
	 * @param endMultiplicity
	 *            the multiplicity type to be set.
	 */
	public void setEndMultiplicity(String endMultiplicity) {
		if (!endMultiplicity.equals("")) {
			addDecoration(endMultiplicityLabel, endMultiplicity, endMultUDist,
					endMultVDist, true);
		}
	}

	/**
	 * Set multiplicity type at start point of the connection.
	 * 
	 * @param startMultiplicity
	 *            the type of multiplicity to be set.
	 */
	public void setStartMultiplicity(String startMultiplicity) {
		if (!startMultiplicity.equals("")) {
			addDecoration(startMultiplicityLabel, startMultiplicity,
					startMultUDist, startMultVDist, false);
		}
	}

	/**
	 * Set description of multiplicity at end point of the connection.
	 * 
	 * @param endMultiplicityDescription
	 *            the description to be set.
	 */
	public void setEndMultiplicityDescription(String endMultiplicityDescription) {
		if (!endMultiplicityDescription.equals("")) {
			addDecoration(endMultiplicityDescriptionLabel,
					endMultiplicityDescription, endMultDescUDist,
					endMultLabelVDist, true);
		}
	}

	/**
	 * Set description of multiplicity at start point of the connection
	 * 
	 * @param startMultiplicityDescription
	 *            the description to be set.
	 */
	public void setStartMultiplicityDescription(
			String startMultiplicityDescription) {
		addDecoration(startMultiplicityDescriptionLabel,
				startMultiplicityDescription, startMultDescUDist,
				startMultDescVDist, false);
	}

	/**
	 * Construct an empty figure : a connection line without any decoration.
	 */
	public void emptyFigure() {
		setSourceDecoration(null);
		setTargetDecoration(null);
	}
	
	
	

	public Inheritance getInheritance() {
		return inheritance;
	}

	/**
	 * add text-decoration to connection
	 * 
	 * @param label
	 *            The label which should be (re)used.
	 * 
	 * @param caption
	 *            the string to be added to connection
	 * @param uDist
	 *            the distance in pixels from Connection's owner
	 * @param vDist
	 *            the distance in pixels from Connection
	 * @param end
	 *            determines if decoration is added to the end point or the
	 *            start point of the connection true - end point false - start
	 *            point
	 */
	private void addDecoration(Label label, String caption, int uDist,
			int vDist, boolean end) {
		label.setText(caption);

		// create new ConnectionEndpointLocator
		ConnectionEndpointLocator endpointLocator = new ConnectionEndpointLocator(
				this, !end);

		// set distance from endpoint
		endpointLocator.setUDistance(uDist);
		endpointLocator.setVDistance(vDist);

		// add Label to connection
		add(label, endpointLocator);
	}
}
