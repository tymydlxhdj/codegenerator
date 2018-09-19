package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.swt.SWT;

import com.mqfdy.code.designer.editor.figure.ConnectionFigure;
import com.mqfdy.code.designer.editor.figure.anchor.BorderAnchor;
import com.mqfdy.code.designer.editor.figure.anchor.RectangleBorderAnchor;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.editor.part.RelationEditPart;
import com.mqfdy.code.designer.editor.policies.MoveConnectionEndpointEditPolicy;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * 移动连线.
 *
 * @author mqfdy
 */
public class MoveConnectionCommand extends Command {

	/** The connection instance to reconnect. */
//	private Association connection;

	private Request request;

	/** The conele. */
	private DiagramElement conele = null;

	/** The old px. */
	private float oldPx = 0;
	
	/** The old py. */
	private float oldPy = 0;
	
	/** The new px. */
	private float newPx = 0;
	
	/** The new py. */
	private float newPy = 0;
	
	/** The conn. */
	private OmConnectionEditPart conn;
	
	/** The new end px. */
	private float newEndPx = 0;
	
	/** The new end py. */
	private float newEndPy = 0;
	
	/** The old end px. */
	private float oldEndPx = 0;
	
	/** The old end py. */
	private float oldEndPy = 0;
	
	/** The old pointlist. */
	private String oldPointlist;
	
	/** The new pointlist. */
	private String newPointlist;

	/** The move Y. */
	private int moveY = 0;

	/** The move X. */
	private int moveX = 0;

	/**
	 * Instantiates a new move connection command.
	 *
	 * @param conn
	 *            the conn
	 * @param request
	 *            the request
	 */
	public MoveConnectionCommand(OmConnectionEditPart conn, Request request) {

		setLabel("move connection");
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		this.conn = conn;
		this.request = request;
//		this.connection = (Association) conn.getModel();
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		if (conn.getModel() instanceof Association) {
			if (((Association) (conn.getModel())).getClassA() == ((Association) (conn
					.getModel())).getClassB()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		if (!(request instanceof ChangeBoundsRequest))
			return;
		ChangeBoundsRequest r = (ChangeBoundsRequest) request;
		OmConnectionEditPart con = (OmConnectionEditPart) conn;
//		Point loc = r.getLocation();
		if (((GraphicalEditPart) con.getTarget()).getModel() instanceof DiagramElement) {
			conele = ((DiagramElement) ((GraphicalEditPart) con.getTarget())
					.getModel()).getBelongDiagram().getElementById(
					((AbstractModelElement) con.getModel()).getId());
			oldPx = conele.getStyle().getPositionX();
			oldPy = conele.getStyle().getPositionY();
			oldEndPx = conele.getStyle().getEndPositionX();
			oldEndPy = conele.getStyle().getEndPositionY();
			String pls = conele.getStyle().getPointList() + "";
			oldPointlist = pls;
		}
		moveX = r.getMoveDelta().x;
		moveY = r.getMoveDelta().y;
		ConnectionFigure rfg = (ConnectionFigure) conn.getFigure();
		Point ref1 = (rfg).getSourceAnchor().getReferencePoint();
		Point ref2 = (rfg).getTargetAnchor().getReferencePoint();
		
		(rfg).translateToRelative(ref1);
		(rfg).translateToRelative(ref2);
		newPx = ((Connection) conn.getFigure()).getPoints().getFirstPoint().x /*+ moveX */ - ref1.x;
		newPy = ((Connection) conn.getFigure()).getPoints().getFirstPoint().y /* + moveY */	- ref1.y;
		newEndPx = ((Connection) conn.getFigure()).getPoints().getLastPoint().x /* + moveX */ - ref2.x;
		newEndPy = ((Connection) conn.getFigure()).getPoints().getLastPoint().y /* + moveY */ - ref2.y;
		redo();
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		conele.getStyle().setPositionX(newPx);
		conele.getStyle().setPositionY(newPy);
		conele.getStyle().setEndPositionX(newEndPx);
		conele.getStyle().setEndPositionY(newEndPy);
		
		IFigure sourceFigure = ((GraphicalEditPart) conn.getSource()).getFigure();
		IFigure targetFigure = ((GraphicalEditPart) conn.getTarget()).getFigure();
		Rectangle rect = Rectangle.SINGLETON;
		rect.setBounds(sourceFigure.getBounds());
		sourceFigure.translateToAbsolute(rect);
//		Point ref1 = rect.getCenter();
		Rectangle rect1 = Rectangle.SINGLETON;
		rect1.setBounds(targetFigure.getBounds());
		targetFigure.translateToAbsolute(rect1);
//		Point ref2 = rect.getCenter();
		BorderAnchor anchor = new RectangleBorderAnchor(sourceFigure);
		anchor.setAngle(Math.atan2(newPy, newPx));
		conn.setSourceAnchor(anchor);
		BorderAnchor anchor1 = new RectangleBorderAnchor(targetFigure);
		anchor1.setAngle(Math.atan2(newEndPy, newEndPx));
		conn.setTargetAnchor(anchor1);
		conn.refresh();
		if(newPointlist == null || newPointlist.equals("")){
			PointList points1 = ((ConnectionFigure)conn.getFigure()).getPoints();
			String ls = "";
			for (int m = 1; m < points1.size() - 1; m++) {
				ls = (ls.equals("") ? "" : ls + ",") + "("
						+ points1.getPoint(m).x + " "
						+ points1.getPoint(m).y + ")";
			}
			newPointlist = ls;
		}
		conele.getStyle().setPointList(newPointlist);
		refreshPoints(newPointlist);
		conn.refresh();

		conn.getSource().refresh();
		conn.getTarget().refresh();
		MoveConnectionEndpointEditPolicy editPolicy = (MoveConnectionEndpointEditPolicy) conn
				.getEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE);
		editPolicy.setX(0);
		editPolicy.setY(0);
		editPolicy.setIsReLoad(false);
		if(conn instanceof RelationEditPart)
			((Shape) conn.getFigure()).setLineStyle(SWT.LINE_CUSTOM);//设置实线
	}

	/**
	 * Reconnect the connection to its original source and target endpoints.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void undo() {
		conele.getStyle().setPositionX(oldPx);
		conele.getStyle().setPositionY(oldPy);
		conele.getStyle().setEndPositionX(oldEndPx);
		conele.getStyle().setEndPositionY(oldEndPy);
		BorderAnchor anchor = new RectangleBorderAnchor(
				((GraphicalEditPart) conn.getSource()).getFigure());
		anchor.setAngle(Math.atan2(oldPy, oldPx));
		if (oldPy == 0.0&& oldPx == 0.0)
			anchor.setAngle(Double.MAX_VALUE);
		conn.setSourceAnchor(anchor);
		BorderAnchor eanchor = new RectangleBorderAnchor(
				((GraphicalEditPart) conn.getTarget()).getFigure());
		eanchor.setAngle(Math.atan2(oldEndPy, oldEndPx));
		if (oldEndPx == 0.0&& oldEndPy == 0.0)
			eanchor.setAngle(Double.MAX_VALUE);
		conn.setTargetAnchor(eanchor);
		conele.getStyle().setPointList(oldPointlist);
		
		refreshPoints(oldPointlist);
		
		conn.refresh();
		conn.getTarget().refresh();
		conn.getSource().refresh();
		EditorOperation.refreshNodeEditParts();
	}
	
	/**
	 * Refresh points.
	 *
	 * @author mqfdy
	 * @param pointsList
	 *            the points list
	 * @Date 2018-09-03 09:00
	 */
	public void refreshPoints(String pointsList){
		conn.getBendpoints().clear();
		((ConnectionFigure)conn.getFigure()).getPoints().removeAllPoints();
		PointList plist = new PointList();
		plist.addPoint(new Point(oldPx,oldPy));
		if (!pointsList.equals("")) {
			String[] s = pointsList.split(",");
			for (int i = 0; i < s.length; i++) {
				String[] po = s[i].split(" ");
				int x = Integer.parseInt(po[0].replace("(", ""));
				int y = Integer.parseInt(po[1].replace(")", ""));
				plist.addPoint(new Point(x,y));
			}
		}
		plist.addPoint(new Point(oldEndPx,oldEndPy));
		
		((Connection)conn.getFigure()).setPoints(plist);
		((Connection)conn.getFigure()).repaint();
	}
}
