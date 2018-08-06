package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Font;

import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.model.Association;

/**
 * 
 * 关系连线对象的显示Figure
 * 
 * @author mqfdy
 * 
 */
public class RelationFigure extends ConnectionFigure {
	// 箭头大小设置
	// private static final int ARROW_SIZE = 2;

	// distance of startMultiplicity from startFigure
	private int startMultUDist = 8;

	private int startMultVDist = -5;

	private int startRoleNameUDist = 8;

	private int startRoleNameVDist = 5;

	private int endRoleNameUDist = 0;

	private int endRoleNameVDist = 5;

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
	private Label startRoleNameLabel = new Label();
	private Label endRoleNameLabel = new Label();

	private Label startMultiplicityDescriptionLabel = new Label();

	private Label endMultiplicityLabel = new Label();

	private Label endMultiplicityDescriptionLabel = new Label();

	// caption of connection
	private Label captionLabel = new Label();

	// decorations for connection
	private PolylineDecoration arrowDecoration;
	private PolylineDecoration arrowDecoration2;

	private PolygonDecoration aggregationDecoration;

	private PolygonDecoration compositionDecoration;

	private PolygonDecoration errorDecoration;
	private Association as;

	/**
	 * Simple constructor to create a directed association, aggregation or
	 * composition with any labels.
	 * 
	 * @param isDirected
	 * @param b
	 * 
	 * @param type
	 *            连线类型
	 * @param as
	 * @param relationEditPart
	 * @see IConstants
	 */
	public RelationFigure(String type, Association as,
			OmConnectionEditPart relationEditPart) {
		this(type, null, null, null, null, null, false,relationEditPart);
		this.as = as;
		if (startRoleNameLabel != null && endRoleNameLabel != null) {
			
			startRoleNameLabel.setFont(new Font(null,"", 9, 0));
			
			endRoleNameLabel.setFont(new Font(null,"", 9, 0));
		}
		if (captionLabel != null && startMultiplicityDescriptionLabel != null && endMultiplicityDescriptionLabel != null) {
			
		}
		captionLabel.setFont(new Font(null,"", 9, 0));
		startMultiplicityDescriptionLabel.setFont(new Font(null,"", 9, 0));
		endMultiplicityDescriptionLabel.setFont(new Font(null,"", 9, 0));
	}

	

	/**
	 * Constructor of a new RelationFigure. Creates a new PolylineConnetion,
	 * adds a decoration corresponding to the uml2-type to the connection
	 * 
	 * @param isDirected
	 *            indicates connection is directed. If so an arrow is added to
	 *            its end point.
	 * @param type
	 *            indicate the RelationFigureType : aggregation, association or
	 *            composition
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
	 * @param relationEditPart 
	 */
	public RelationFigure(String type, String startMultiplicity,
			String endMultiplicity, String startMultCaption,
			String endMultCaption, String caption, boolean hasQualifier, OmConnectionEditPart relationEditPart) {
		super(relationEditPart);

		// set distances of endMultiplicities from endFigure
		endMultDescUDist = 0;
		endMultLabelVDist = 5;

		// define arrow
		// PointList arrowPointList = new PointList();
		// arrowPointList.addPoint(0, 0);
		// 给关系线加箭头
		// arrowPointList.addPoint(-ARROW_SIZE, ARROW_SIZE);
		// arrowPointList.addPoint(-ARROW_SIZE, -ARROW_SIZE);

		arrowDecoration = new PolylineDecoration();
		arrowDecoration2 = new PolylineDecoration();
		// 去掉连线上的箭头
		// 定制箭头的大小
		// arrowDecoration.setTemplate(arrowPointList);
		// arrowDecoration.setBackgroundColor(ColorConstants.white);

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
		compositionDecoration.setBackgroundColor(ColorConstants.white);

		PointList errorPointList = new PointList();
		errorPointList.addPoint(0, 0);
		errorPointList.addPoint(-2, -3);
		errorPointList.addPoint(-4, 0);
		errorPointList.addPoint(-2, 3);
		errorDecoration = new PolygonDecoration();
		errorDecoration.setTemplate(errorPointList);
		errorDecoration.setBackgroundColor(ColorConstants.red);

		// set all values (if they exist) / fill all Labels with content
		// setDirected();
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
	 * Sets the type of the connection : aggregation, association or composition
	 * The corresponding decoration (nothing or a rhombus) is set in a certain
	 * distance.
	 * 
	 * @param type
	 *            the type of RelationFigure : aggreagtion, association or
	 *            composition (uml2)
	 */
	public void setType(String type) {
		if (type.equals(IConstants.ASSOCIATION_STR)) {
			// association -> no decoration
			// distance from endFigure; directly beside
			setForegroundColor(ColorConstants.black);
			endMultUDist = 8;
			endMultDescUDist = 0;
			setSourceDecoration(null);
		} else if (type.equals(IConstants.ASSOCIATION_STR1_1)) {
			// association -> no decoration
			// distance from endFigure; directly beside
			setForegroundColor(ColorConstants.black);
			endMultUDist = 8;
			endMultDescUDist = 0;
			setSourceDecoration(null);
		} else if (type.equals(IConstants.ASSOCIATION_STR1_N)) {
			// association -> no decoration
			// distance from endFigure; directly beside
			setForegroundColor(ColorConstants.black);
			endMultUDist = 8;
			endMultDescUDist = 0;
			setSourceDecoration(null);
		} else if (type.equals(IConstants.ASSOCIATION_STRN_1)) {
			// association -> no decoration
			// distance from endFigure; directly beside
			setForegroundColor(ColorConstants.black);
			endMultUDist = 8;
			endMultDescUDist = 0;
			setSourceDecoration(null);
		} else if (type.equals(IConstants.ASSOCIATION_STRN_N)) {
			// association -> no decoration
			// distance from endFigure; directly beside
			setForegroundColor(ColorConstants.black);
			endMultUDist = 8;
			endMultDescUDist = 0;
			setSourceDecoration(null);
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
	public void setDirected(boolean isDirectedA, boolean isDirectedB) {
		if (isDirectedA == isDirectedB) {
			// 外键双向关联时，源点和终点都有箭头
			setSourceDecoration(arrowDecoration);
			setTargetDecoration(arrowDecoration2);
		} else if (isDirectedA == true && isDirectedB == false) {
			setSourceDecoration(arrowDecoration);
		} else if (isDirectedB == true && isDirectedA == false) {
			setTargetDecoration(arrowDecoration2);
		}
	}

	/**
	 * Set the description of the RelationFigure. A Label is added in the middle
	 * of the connection. Attention: Should be called after all other set*()
	 * methods!
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

	public void setStartRoleName(String startRoleName) {
		if (startRoleName != null /* && !startRoleName.equals("") */) {
			addDecoration(startRoleNameLabel, startRoleName,
					startRoleNameUDist, startRoleNameVDist, false);
		}
	}

	public void setEndRoleName(String endRoleName) {
		if (endRoleName != null /* && !endRoleName.equals("") */) {
			addDecoration(endRoleNameLabel, endRoleName, endRoleNameUDist,
					endRoleNameVDist, true);
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

	public Association getAss() {
		return as;
	}
}
