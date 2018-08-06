package com.mqfdy.code.designer.editor.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.swt.SWT;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.commands.MoveConnectionCommand;
import com.mqfdy.code.designer.editor.figure.ConnectionFigure;
import com.mqfdy.code.designer.editor.figure.anchor.BorderAnchor;
import com.mqfdy.code.designer.editor.figure.anchor.RectangleBorderAnchor;
import com.mqfdy.code.designer.editor.part.LinkAnnoEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.editor.part.RelationEditPart;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.Association;

/**
 * 连线终点移动以及连线整个拖动的编辑策略 显示连线拖动时的反馈
 * 
 * @author mqfdy
 * 
 */
public class MoveConnectionEndpointEditPolicy extends
		ConnectionEndpointEditPolicy {
	int x = 0;
	int y = 0;
	private PointList list = new PointList();
	private boolean isReLoad = true;

	/**
	 * 添加移动连线的MoveConnectionCommand
	 */
	@Override
	public Command getCommand(Request request) {
		if (REQ_MOVE.equals(request.getType())
				&& BusinessModelUtil.getBusinessModelDiagramEditor() != null
				&& BusinessModelUtil.getBusinessModelDiagramEditor()
						.getViewer().getSelectedEditParts().size() == 1
				&& BusinessModelUtil.getBusinessModelDiagramEditor()
						.getViewer().getSelectedEditParts().get(0) instanceof OmConnectionEditPart)
			return new MoveConnectionCommand(
					(OmConnectionEditPart) BusinessModelUtil
							.getBusinessModelDiagramEditor().getViewer()
							.getSelectedEditParts().get(0), request);
		return super.getCommand(request);
	}

	/**
	 * 在原基础上添加连线移动的反馈
	 */
	public void showSourceFeedback(Request request) {
		if (REQ_RECONNECT_SOURCE.equals(request.getType())
				|| REQ_RECONNECT_TARGET.equals(request.getType()))
			showConnectionMoveFeedback((ReconnectRequest) request);
		if (REQ_MOVE.equals(request.getType()))
			showConnectionMoveFeedbacks((ChangeBoundsRequest) request);
	}
	/**
	 * 显示连线移动时的反馈
	 * @param request
	 */
	protected void showConnectionMoveFeedbacks(ChangeBoundsRequest request) {
		OmConnectionEditPart node = null;
		if (request.getEditParts().get(0) instanceof RelationEditPart)
			node = (RelationEditPart) request.getEditParts().get(0);
		if (request.getEditParts().get(0) instanceof LinkAnnoEditPart)
			node = (LinkAnnoEditPart) request.getEditParts().get(0);
		if (node.getModel() instanceof Association) {
			if (((Association) (node.getModel())).getClassA() == ((Association) (node
					.getModel())).getClassB()) {
				return;
			}
		}
		ConnectionFigure rfg = (ConnectionFigure) node.getFigure();
		//记住points的所有值，防止一直变化
		if(list.size() == 0 || !isReLoad){
			isReLoad = true;
			list.removeAllPoints();
			for(int i = 0;i < rfg.getPoints().size();i++){
				list.addPoint(rfg.getPoints().getPoint(i).getCopy());
			}
		}
		Point refSource = rfg.getSourceAnchor().getReferencePoint();
		Point refTarget = rfg.getTargetAnchor().getReferencePoint();
		rfg.translateToRelative(refSource);
		rfg.translateToRelative(refTarget);
		PointList pl = ((Connection) node.getFigure()).getPoints();
		Point startPoint = pl.getFirstPoint();
		Point endPoint = pl.getLastPoint();
		int moveX = request.getMoveDelta().x - x - BusinessModelDiagramEditor.getRangeLocation().x;
		int moveY = request.getMoveDelta().y - y - BusinessModelDiagramEditor.getRangeLocation().y;
//		x = request.getMoveDelta().x;
//		y = request.getMoveDelta().y;
		List<Point> pli = new ArrayList<Point>();
		for (int index = 0;node!=null && index < node.getBendpoints().size(); index++) {
			Point p = list.getPoint(index + 1);
//			Point p = ((Connection) node.getFigure()).getPoints().getPoint(index + 1);
			
			Point newPoint = new Point(p.x + moveX, p.y + moveY);
			pli.add(newPoint);
			List constraint = (List) getConnection().getRoutingConstraint();
			getConnection().translateToRelative(newPoint);
			Bendpoint bp = new AbsoluteBendpoint(newPoint);
			constraint.set(index, bp);
			getConnection().setRoutingConstraint(constraint);
		}
//		Point ref = rfg.getSourceAnchor().getReferencePoint();
//		Point ref1 = rfg.getTargetAnchor().getReferencePoint();
//
//		rfg.translateToRelative(ref);
//		rfg.translateToRelative(ref1);
		pl.removeAllPoints();
		pl.addPoint(startPoint.x + moveX, startPoint.y + moveY);
		for (Point p : pli)
			pl.addPoint(p);
		pl.addPoint(endPoint.x + moveX, endPoint.y + moveY);
//		Rectangle startbounds = ((NodeEditPart)node.getSource()).getFigure().getBounds();
//		Rectangle endbounds = ((NodeEditPart)node.getTarget()).getFigure().getBounds();
		int startX = startPoint.x + moveX;// - refSource.x;
//		if(startX > startbounds.x + startbounds.width)
//			startX = startbounds.x + startbounds.width;
//		if(startX < startbounds.x)
//			startX = startbounds.x;
		startX = startX - refSource.x;
		
		int startY = startPoint.y + moveY;// - refSource.y;
//		if(startY > startbounds.y + startbounds.height)
//			startY = startbounds.y + startbounds.height;
//		if(startY < startbounds.y)
//			startY = startbounds.y;
		startY = startY - refSource.y;
		
		int endX = endPoint.x + moveX;// - refTarget.x;
//		if(endX > endbounds.x + endbounds.width)
//			endX = startbounds.x + endbounds.width;
//		if(endX < endbounds.x)
//			endX = endbounds.x;
		endX = endX - refTarget.x;
		
		int endY = endPoint.y + moveY;// - refTarget.y;
//		if(endY > endbounds.y + endbounds.height)
//			endY = endbounds.y + endbounds.height;
//		if(endY < endbounds.y)
//			endY = endbounds.y;
		endY = endY - refTarget.y;
		if(node!=null){
			BorderAnchor anchor = new RectangleBorderAnchor(
					((GraphicalEditPart) node.getSource()).getFigure());
			anchor.setAngle(Math.atan2(startY, startX));
			if (startY == 0.0&& startX == 0.0)
				anchor.setAngle(Double.MAX_VALUE);
			((Connection) node.getFigure()).setSourceAnchor(anchor);
			BorderAnchor anchor1 = new RectangleBorderAnchor(
					((GraphicalEditPart) node.getTarget()).getFigure());
			anchor1.setAngle(Math.atan2(endY, endX));
			if (endY == 0.0&& endX == 0.0)
				anchor1.setAngle(Double.MAX_VALUE);
			((Connection) node.getFigure()).setTargetAnchor(anchor1);
			((Shape) node.getFigure()).setLineStyle(SWT.LINE_DOT);//设置虚线
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setIsReLoad(boolean isReLoad) {
		this.isReLoad  = isReLoad;
	}

}
