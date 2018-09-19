package com.mqfdy.code.designer.editor.part.extensions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Ray;
import org.eclipse.draw2d.geometry.Rectangle;

import com.mqfdy.code.designer.editor.figure.ConnectionFigure;
import com.mqfdy.code.designer.editor.figure.InheritanceFigure;
import com.mqfdy.code.designer.editor.figure.LinkAnnoFigure;
import com.mqfdy.code.designer.editor.figure.RelationFigure;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfConnectRouter.
 *
 * @author mqfdy
 */
public class SelfConnectRouter extends BendpointConnectionRouter {
	
	/** The Constant A_POINT. */
	private static final PrecisionPoint A_POINT = new PrecisionPoint();
	
	/** The business object model. */
	private BusinessObjectModel businessObjectModel;
	
	/** The diagram. */
	private Diagram diagram;
	
	/** The as list. */
	private List<AbstractModelElement> asList = new ArrayList<AbstractModelElement>();

	/**
	 * Instantiates a new self connect router.
	 *
	 * @param bm
	 *            the bm
	 */
	public SelfConnectRouter(Diagram bm) {
		super();
		asList.clear();
		this.diagram = bm;
		AbstractModelElement ab = bm.getBelongPackage();
		do {
			ab = ab.getParent();
		} while (!(ab instanceof BusinessObjectModel));
		this.businessObjectModel = (BusinessObjectModel) ab;

	}

	/**
	 * Route.
	 *
	 * @author mqfdy
	 * @param conn
	 *            the conn
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void route(Connection conn) {
		List<OmConnectionEditPart> partList = new ArrayList<OmConnectionEditPart>();
		if (BusinessModelUtil.getBusinessModelDiagramEditor() != null) {
			Iterator<?> it = BusinessModelUtil.getBusinessModelDiagramEditor()
					.getAllEditParts();
			while (it.hasNext()) {
				Object e = ((Entry<?, ?>) it.next()).getValue();
				if (e instanceof OmConnectionEditPart) {
					partList.add((OmConnectionEditPart) e);
				}
			}
		}
//		if (conn instanceof ConnectionFigure) {
		if (conn instanceof ConnectionFigure && !(conn instanceof InheritanceFigure)) {
			DiagramElement conele = ((DiagramElement) (((ConnectionFigure) conn)
					.getEditPart().getTarget() == null ? ((ConnectionFigure) conn)
					.getEditPart().getSource() : ((ConnectionFigure) conn)
					.getEditPart().getTarget()).getModel()).getBelongDiagram()
					.getElementById(
							((AbstractModelElement) ((ConnectionFigure) conn)
									.getEditPart().getModel()).getId());
			if (conele != null
					&& ((ConnectionFigure) conn).getEditPart().getBendpoints() != null
					&& ((ConnectionFigure) conn).getEditPart().getBendpoints()
							.size() == 0
					&& conele.getStyle().getPointList() != null
					&& !conele.getStyle().getPointList().equals("")) {
				((ConnectionFigure) conn).getEditPart().setBendpoints(
						conele.getStyle().getPointList());
				// ((ConnectionFigure)
				// conn).getEditPart().firePropertyChange(ConnectionEditPart.PROP_BENDPOINT,
				// null, null);
				// return;
			}
			AbstractModelElement classA = null;
			AbstractModelElement classB = null;
			if (conn instanceof RelationFigure){
				classA = ((RelationFigure) conn).getAss().getClassA();
				classB = ((RelationFigure) conn).getAss().getClassB();
			}
			else if (conn instanceof LinkAnnoFigure){
				classA = ((LinkAnnoFigure) conn).getLink().getClassA();
				classB = ((LinkAnnoFigure) conn).getLink().getClassB();
			}
			if (classA != classB) {
				PointList points = conn.getPoints();
				points.removeAllPoints();
				List bendpoints = (List) getConstraint(conn);
				if (bendpoints == null)
					bendpoints = Collections.EMPTY_LIST;
				Point ref1, ref2;
				if (bendpoints.isEmpty()) {
					ref1 = conn.getTargetAnchor().getReferencePoint();
					ref2 = conn.getSourceAnchor().getReferencePoint();
				} else {
					ref1 = new Point(
							((Bendpoint) bendpoints.get(0)).getLocation());
					conn.translateToAbsolute(ref1);
					ref2 = new Point(((Bendpoint) bendpoints.get(bendpoints
							.size() - 1)).getLocation());
					conn.translateToAbsolute(ref2);
				}
				String l = "";
				A_POINT.setLocation(conn.getSourceAnchor().getLocation(ref1));
				conn.translateToRelative(A_POINT);
				points.addPoint(A_POINT);
				for (int i = 0; i < bendpoints.size(); i++) {
					Bendpoint bp = (Bendpoint) bendpoints.get(i);
					points.addPoint(bp.getLocation());
					if (l!=null && conele!=null && !l.equals(conele.getStyle().getPointList()))
						l = (l.equals("") ? "" : l + ",") + "("
								+ bp.getLocation().x + " " + bp.getLocation().y
								+ ")";
				}
				A_POINT.setLocation(conn.getTargetAnchor().getLocation(ref2));
				conn.translateToRelative(A_POINT);
				points.addPoint(A_POINT);
				if (conele != null && bendpoints.isEmpty() && conele.getStyle().getEndPositionX() == 0
						&& conele.getStyle().getEndPositionY() == 0
						&& conele.getStyle().getPositionX() == 0
						&& conele.getStyle().getPositionY() == 0) {
					List<Point> pol = getSEPoints(conn, partList,
							points.getFirstPoint(), points.getLastPoint(),classA,classB);
					points.removeAllPoints();
					points.addPoint(pol.get(0));
					points.addPoint(pol.get(1));
				}
				// update
//				conele.getStyle().setPointList(l);
				conn.setPoints(points);
				return;
			}
			// 自关联
			if (classA == classB) {
				if (conn.getSourceAnchor().getOwner() != null) {
					PointList points1 = getSelfPoints(conn);
					// 得到目标和源参考点
//					Point startPoint = getStartPoint(conn);
//					conn.translateToRelative(startPoint);
//					Point endPoint = getEndPoint(conn);
//					conn.translateToRelative(endPoint);
//					Ray start = new Ray(startPoint);
//					Ray end = new Ray(endPoint);
					if (points1 == null) {
						return;
					}
					conn.getPoints().removeAllPoints();
					conn.setPoints(points1);
					Ray start = new Ray(points1.getFirstPoint());
					Ray end = new Ray(points1.getLastPoint());
					//设置转折点
					String ls = "";
					for (int m = 1; m < points1.size() - 1; m++) {
						ls = (ls.equals("") ? "" : ls + ",") + "("
								+ points1.getPoint(m).x + " "
								+ points1.getPoint(m).y + ")";
					}
					if(conele!=null){
						if (!conele.getStyle().getPointList().equals(ls)) {
							((ConnectionFigure) conn).getEditPart().setBendpoints(ls);
						}
						conele.getStyle().setPointList(ls);
					}
					
					PointList points = conn.getPoints();
					points.removeAllPoints();
					List bendpoints = (List) getConstraint(conn);
					if (bendpoints == null)
						bendpoints = Collections.EMPTY_LIST;

					Point ref1, ref2;

					if (bendpoints.isEmpty()) {
						ref1 = conn.getTargetAnchor().getReferencePoint();
						ref2 = conn.getSourceAnchor().getReferencePoint();
					} else {
						ref1 = new Point(
								((Bendpoint) bendpoints.get(0)).getLocation());
						conn.translateToAbsolute(ref1);
						ref2 = new Point(((Bendpoint) bendpoints.get(bendpoints
								.size() - 1)).getLocation());
						conn.translateToAbsolute(ref2);
					}
					String l = "";
					A_POINT.setLocation(conn.getSourceAnchor()
							.getLocation(ref1));
					conn.translateToRelative(A_POINT);
					// points.addPoint(A_POINT);
					points.addPoint(new Point(start.x, start.y));
					for (int i = 0; i < bendpoints.size(); i++) {
						Bendpoint bp = (Bendpoint) bendpoints.get(i);
						points.addPoint(bp.getLocation());
						if (conele != null && !"".equals(conele.getStyle().getPointList()))
							l = (l.equals("") ? "" : l + ",") + "("
									+ bp.getLocation().x + " "
									+ bp.getLocation().y + ")";
					}
					A_POINT.setLocation(conn.getTargetAnchor()
							.getLocation(ref2));
					conn.translateToRelative(A_POINT);
					// points.addPoint(A_POINT);
					points.addPoint(new Point(end.x, end.y));
					if (!l.equals("") && conele!=null) {
						conele.getStyle().setPointList(l);
					}
					conn.setPoints(points);
				}
			}
		} else {
			PointList points = conn.getPoints();
			points.removeAllPoints();

			List bendpoints = (List) getConstraint(conn);
			if (bendpoints == null)
				bendpoints = Collections.EMPTY_LIST;

			Point ref1, ref2;

			if (bendpoints.isEmpty()) {
				ref1 = conn.getTargetAnchor().getReferencePoint();
				ref2 = conn.getSourceAnchor().getReferencePoint();
			} else {
				ref1 = new Point(((Bendpoint) bendpoints.get(0)).getLocation());
				conn.translateToAbsolute(ref1);
				ref2 = new Point(
						((Bendpoint) bendpoints.get(bendpoints.size() - 1))
								.getLocation());
				conn.translateToAbsolute(ref2);
			}

			A_POINT.setLocation(conn.getSourceAnchor().getLocation(ref1));
			conn.translateToRelative(A_POINT);
			points.addPoint(A_POINT);

			for (int i = 0; i < bendpoints.size(); i++) {
				Bendpoint bp = (Bendpoint) bendpoints.get(i);
				points.addPoint(bp.getLocation());
			}

			A_POINT.setLocation(conn.getTargetAnchor().getLocation(ref2));
			conn.translateToRelative(A_POINT);
			points.addPoint(A_POINT);
			conn.setPoints(points);
		}
	}

	/**
	 * Checks if is equal.
	 *
	 * @author mqfdy
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if is equal
	 * @Date 2018-09-03 09:00
	 */
	public boolean isEqual(int x, int y) {
		return (x - y > -3 && x - y < 3);
	}
	
	/**
	 * Gets the self points.
	 *
	 * @author mqfdy
	 * @param conn
	 *            the conn
	 * @return the self points
	 * @Date 2018-09-03 09:00
	 */
	public PointList getSelfPoints(Connection conn) {
		if (conn.getSourceAnchor().getOwner() != null) {
			Rectangle bounds = conn.getSourceAnchor().getOwner().getBounds();
			// 得到目标和源参考点
			Point startPoint = getStartPoint(conn);
			conn.translateToRelative(startPoint);
			Point endPoint = getEndPoint(conn);
			conn.translateToRelative(endPoint);
			Ray start = new Ray(startPoint);
			Ray end = new Ray(endPoint);
			PointList points1 = new PointList();
			// if(isEqual(start.x, bounds.x + bounds.width/2) &&
			// isEqual(start.y, bounds.y + bounds.height/2)){
			// start.x = bounds.x;
			// }
			// if(isEqual(end.x, bounds.x + bounds.width/2) && isEqual(end.y,
			// bounds.y + bounds.height/2)){
			// end.x = bounds.x;
			// }
			// 如果起点和终点在垂直线上，就添加两个转折点
			Point p = new Point(0, 0);
			int x = bounds.width / 2;
			int y = bounds.height / 2;
			int px = 30;//偏移
			if(isEqual(start.x,bounds.x + x) && isEqual(start.y, bounds.y + y)){
				start.x = bounds.x;
			}
			if(isEqual(end.x,end.x + x) && isEqual(end.y, end.y + y)){
				end.y = bounds.y;
			}
			
			// 添加起始点
			points1.addPoint(new Point(start.x, start.y));
			// 解决图形放大的缩小时的坐标误差
			if (((start.x - end.x > x - 3 && start.x - end.x < x + 3) || (start.x
					- end.x > -x - 3 && start.x - end.x < -x + 3))
					&& ((start.x - end.x > x - 3 && start.x - end.x < x + 3) || (start.y
							- end.y > -y - 3 && start.y - end.y < -y + 3))) {
				p.x = start.x + px;
				p.y = start.y;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = start.x + px;
				p.y = end.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x;
				p.y = end.y - px;
				points1.addPoint(p);
			} else if (start.x == end.x
					&& isEqual(start.x, bounds.x + bounds.width)) {
				// 右侧
				p.x = start.x + px;
				p.y = start.y;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x + px;
				p.y = end.y;
				points1.addPoint(p);
			} else if (start.y == end.y && start.y == bounds.y) {
				// 上
				p.x = start.x;
				p.y = start.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x;
				p.y = end.y - px;
				points1.addPoint(p);
			} else if (start.y == end.y && start.y == bounds.y + bounds.height) {
				// 下
				p.x = start.x;
				p.y = start.y + px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x;
				p.y = end.y + px;
				points1.addPoint(p);
			} else if (start.x == end.x && start.x == bounds.x) {
				// 左侧
				p.x = start.x - px;
				p.y = start.y;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x - px;
				p.y = end.y;
				points1.addPoint(p);
			} else if (isEqual(start.x, bounds.x + bounds.width)
					&& end.y == bounds.y) {
				// 右上
				p.x = start.x + px;
				p.y = start.y;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = start.x + px;
				p.y = end.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x;
				p.y = end.y - px;
				points1.addPoint(p);
			} else if (start.x == bounds.x && end.y == bounds.y) {
				// 左上
				p.x = start.x - px;
				p.y = start.y;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = start.x - px;
				p.y = end.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x;
				p.y = end.y - px;
				points1.addPoint(p);
			} else if (isEqual(end.x, bounds.x + bounds.width)
					&& start.y == bounds.y) {
				// 右上
				p.x = start.x;
				p.y = start.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x + px;
				p.y = start.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x + px;
				p.y = end.y;
				points1.addPoint(p);
			} else if (end.x == bounds.x && start.y == bounds.y) {
				// 左上
				p.x = start.x;
				p.y = start.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x - px;
				p.y = start.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x - px;
				p.y = end.y;
				points1.addPoint(p);
			} else if (isEqual(start.x, bounds.x)
					&& isEqual(end.x, (bounds.x + bounds.width))) {
				// 左右
				p.x = start.x - px;
				p.y = start.y;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = start.x - px;
				p.y = bounds.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x + px;
				p.y = bounds.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x + px;
				p.y = end.y;
				points1.addPoint(p);
			} else if (isEqual(end.x, bounds.x)
					&& isEqual(start.x, (bounds.x + bounds.width))) {
				// 左右
				p.x = start.x + px;
				p.y = start.y;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = start.x + px;
				p.y = bounds.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x - px;
				p.y = bounds.y - px;
				points1.addPoint(p);
				p = new Point(0, 0);
				p.x = end.x - px;
				p.y = end.y;
				points1.addPoint(p);
			} else if (isEqual(start.x, bounds.x)
					&& isEqual(end.y, bounds.y + bounds.height)) {
				// 左下
				p.x = start.x - px;
				p.y = start.y;
				points1.addPoint(p);
				p.x = start.x - px;
				p.y = end.y + px;
				points1.addPoint(p);
				p.x = end.x;
				p.y = end.y + px;
				points1.addPoint(p);
			} else if (isEqual(end.x, bounds.x)
					&& isEqual(start.y, bounds.y + bounds.height)) {
				// 左下
				p.x = start.x;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = end.x - px;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = end.x - px;
				p.y = end.y;
				points1.addPoint(p);
			}

			else if (isEqual(start.x, bounds.x + bounds.width)
					&& isEqual(end.y, bounds.y + bounds.height)) {
				// 右下
				p.x = start.x + px;
				p.y = start.y;
				points1.addPoint(p);
				p.x = start.x + px;
				p.y = end.y + px;
				points1.addPoint(p);
				p.x = end.x;
				p.y = end.y + px;
				points1.addPoint(p);
			} else if (isEqual(end.x, bounds.x + bounds.width)
					&& isEqual(start.y, bounds.y + bounds.height)) {
				// 右下
				p.x = start.x;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = end.x + px;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = end.x + px;
				p.y = end.y;
				points1.addPoint(p);
			} else if (isEqual(start.y, bounds.y + bounds.height)
					&& isEqual(end.y, bounds.y) && start.x < bounds.x + x
					&& end.x < bounds.x + x) {
				// 上下
				p.x = start.x;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = bounds.x - px;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = bounds.x - px;
				p.y = end.y - px;
				points1.addPoint(p);
				p.x = end.x;
				p.y = end.y - px;
				points1.addPoint(p);
			} else if (isEqual(start.y, bounds.y + bounds.height)
					&& isEqual(end.y, bounds.y) && start.x > bounds.x + x
					&& end.x > bounds.x + x) {
				// 上下
				p.x = start.x;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = bounds.x + bounds.width + px;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = bounds.x + bounds.width + px;
				p.y = end.y - px;
				points1.addPoint(p);
				p.x = end.x;
				p.y = end.y - px;
				points1.addPoint(p);
			} else if (isEqual(start.y, bounds.y + bounds.height)
					&& isEqual(end.y, bounds.y)) {
				// 上下
				p.x = start.x;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = bounds.x - px;
				p.y = start.y + px;
				points1.addPoint(p);
				p.x = bounds.x - px;
				p.y = end.y - px;
				points1.addPoint(p);
				p.x = end.x;
				p.y = end.y - px;
				points1.addPoint(p);
			}
			// 添加结束点
			points1.addPoint(new Point(end.x, end.y));
			return points1;
		}
		return null;
	}

	/**
	 * Gets the SE points.
	 *
	 * @author mqfdy
	 * @param conn
	 *            the conn
	 * @param partList
	 *            the part list
	 * @param point
	 *            the point
	 * @param point2
	 *            the point 2
	 * @param classA
	 *            the class A
	 * @param classB
	 *            the class B
	 * @return the SE points
	 * @Date 2018-09-03 09:00
	 */
	public List<Point> getSEPoints(Connection conn,
			List<OmConnectionEditPart> partList, Point point, Point point2, AbstractModelElement classA, AbstractModelElement classB) {
		AbstractModelElement connection = null;
		if (conn instanceof RelationFigure){
			connection = ((RelationFigure) conn).getAss();
		}
		else if (conn instanceof LinkAnnoFigure){
			connection = ((LinkAnnoFigure) conn).getLink();
		}
		else if (conn instanceof InheritanceFigure){
			connection = ((InheritanceFigure) conn).getInheritance();
		}
		if (!asList.contains(connection))
			asList.add(connection);
		List<Point> points = new ArrayList<Point>();
		Point start = point;
		Point end = point2;

		int position = end.getPosition(start);
		Ray ray;
		if (position == PositionConstants.SOUTH
				|| position == PositionConstants.EAST)
			ray = new Ray(start, end);
		else
			ray = new Ray(end, start);
//		double length = ray.length();

		double xSeparation = 10;
		// points.addPoint(new Point(start.x,start.y));
		Point sbendPoint = new Point();
		Point ebendPoint = new Point();
		int index = 0;
		/*
		 * if(partList.size()>0){ for(RelationEditPart pa : partList){ PointList
		 * pointsCopy = pa.getConnectionFigure().getPoints();
		 * if(pointsCopy.size()>1){ if(pointsCopy.getFirstPoint() ==
		 * points.getFirstPoint() || pointsCopy.getLastPoint().x ==
		 * A_POINT.x&&pointsCopy.getLastPoint().y == A_POINT.y){ index ++; } } }
		 * }else
		 */
		if (asList.size() > 0) {
			List<AbstractModelElement> copyList = new ArrayList<AbstractModelElement>();
			for (AbstractModelElement as : asList) {
				if(as instanceof Association){
					Association association = (Association) as;
					if (/*((ConnectionFigure) conn).getAss() != as
							&& */(association.getClassA() == classA
							&& association.getClassB() == classB)||(association.getClassA() == classB
											&& association.getClassB() == classA)) {
						DiagramElement ele = diagram.getElementById(as.getId());
						if (ele != null
								&& ele.getStyle().getPositionX() == 0
								&& ele.getStyle().getPositionY() == 0
								&& ele.getStyle().getEndPositionX() == 0
								&& ele.getStyle().getEndPositionY() == 0
								&& (ele.getStyle().getPointList() == null 
									|| ele.getStyle().getPointList().equals("")))
							copyList.add(as);
					}
				}
				else if(as instanceof LinkAnnotation){
					LinkAnnotation link = (LinkAnnotation) as;
					if (/*((ConnectionFigure) conn).getAss() != as
							&& */(link.getClassA() == classA
							&& link.getClassB() == classB)||(link.getClassA() == classB
											&& link.getClassB() == classA)) {
						DiagramElement ele = diagram.getElementById(as.getId());
						if (ele != null
								&& ele.getStyle().getPositionX() == 0
								&& ele.getStyle().getPositionY() == 0
								&& ele.getStyle().getEndPositionX() == 0
								&& ele.getStyle().getEndPositionY() == 0
								&& (ele.getStyle().getPointList() == null 
									|| ele.getStyle().getPointList().equals("")))
							copyList.add(as);
					}
				}
				
			}
			index = copyList.indexOf(connection) + 1;
		}
		String aId = classA.getId();
		ElementStyle eleA = null;
		if (diagram.getElementById(aId) != null)
			eleA = diagram.getElementById(aId).getStyle();
		else {
			for (ReferenceObject rf : businessObjectModel.getReferenceObjects()) {
				if (rf.getReferenceObjectId().equals(aId))
					eleA = diagram.getElementById(rf.getId()).getStyle();
			}
		}
		String bId = classB.getId();
		ElementStyle eleB = null;
		if (diagram.getElementById(bId) != null)
			eleB = diagram.getElementById(bId).getStyle();
		else {
			for (ReferenceObject rf : businessObjectModel.getReferenceObjects()) {
				if (rf.getReferenceObjectId().equals(bId))
					eleB = diagram.getElementById(rf.getId()).getStyle();
			}
		}
		int yA = 0;
		int ysA = 0;
		int xA =0;
		int xsA =0;
		if(eleA!=null){
			yA = (int) eleA.getPositionY();
			ysA = yA + eleA.getHeight();
			xA = (int) eleA.getPositionX();
			xsA = xA + eleA.getWidth();
		}
		
		int yB = 0;
		int ysB =0;
		int xB =0;
		int xsB =0;
		if(eleB!=null){
			yB = (int) eleB.getPositionY();
			ysB = yB + eleB.getHeight();
			
			xB = (int) eleB.getPositionX();
			xsB = xB + eleB.getWidth();
		}
		
		if (index % 2 == 0) {
			if(isEqual(start.x,xA) || isEqual(start.x,xsA) )
				sbendPoint = new Point(start.x, start.y + (index / 2) * xSeparation);
			else if(isEqual(start.y,yA) || isEqual(start.y , ysA) )
				sbendPoint = new Point(start.x  + (index / 2) * xSeparation, start.y);
			if(isEqual(end.x ,xB) || isEqual(end.x,xsB) )
				ebendPoint = new Point(end.x, end.y + (index / 2) * xSeparation);
			else if(isEqual(end.y ,yB) || isEqual(end.y,ysB) )
				ebendPoint = new Point(end.x + (index / 2) * xSeparation, end.y);
//			sbendPoint = new Point(start.x, start.y + (index / 2)
//					* (1 * xSeparation));
//			ebendPoint = new Point(end.x, end.y + (index / 2)
//					* (1 * xSeparation));
		} else {
			if(isEqual(start.x,xA) || isEqual(start.x,xsA) )
				sbendPoint = new Point(start.x, start.y + (index / 2) * (-1 * xSeparation));
			else if(isEqual(start.y,yA) || isEqual(start.y , ysA) )
				sbendPoint = new Point(start.x  + (index / 2) * (-1 * xSeparation), start.y);
			if(isEqual(end.x ,xB) || isEqual(end.x,xsB) )
				ebendPoint = new Point(end.x, end.y + (index / 2) * (-1 * xSeparation));
			else if(isEqual(end.y ,yB) || isEqual(end.y,ysB) )
				ebendPoint = new Point(end.x + (index / 2) * (-1 * xSeparation), end.y);
//			sbendPoint = new Point(start.x, start.y + (index / 2)
//					* (-1 * xSeparation));
//			ebendPoint = new Point(end.x, end.y + (index / 2)
//					* (-1 * xSeparation));
		}
		if (sbendPoint.y > ysA)
			sbendPoint = new Point(sbendPoint.x, ysA);
		if (sbendPoint.y < yA)
			sbendPoint = new Point(sbendPoint.x, yA);
		if (ebendPoint.y > ysB)
			ebendPoint = new Point(ebendPoint.x, ysB);
		if (ebendPoint.y < yB)
			ebendPoint = new Point(ebendPoint.x, yB);
		
		if (sbendPoint.x > xsA)
			sbendPoint = new Point(xsA, sbendPoint.y);
		if (sbendPoint.x < xA)
			sbendPoint = new Point(xA, sbendPoint.y);
		if (ebendPoint.x > xsB)
			ebendPoint = new Point(xsB, ebendPoint.y);
		if (ebendPoint.x < xB)
			ebendPoint = new Point(xB, ebendPoint.y);

		points.add(sbendPoint);
		points.add(ebendPoint);
		return points;
	}
}
