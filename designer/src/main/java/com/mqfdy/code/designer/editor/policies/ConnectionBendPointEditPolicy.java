package com.mqfdy.code.designer.editor.policies;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import com.mqfdy.code.designer.editor.commands.BendpointCommand;
import com.mqfdy.code.designer.editor.commands.CreateBendpointCommand;
import com.mqfdy.code.designer.editor.commands.DeleteBendpointCommand;
import com.mqfdy.code.designer.editor.commands.MoveBendpointCommand;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
// TODO: Auto-generated Javadoc

/**
 * 连线拐点的 编辑策略.
 *
 * @author mqfdy
 */
public class ConnectionBendPointEditPolicy extends BendpointEditPolicy {

	/**
	 * 创建.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the creates the bendpoint command
	 * @Date 2018-09-03 09:00
	 */
	protected Command getCreateBendpointCommand(BendpointRequest request) {
		CreateBendpointCommand cmd = new CreateBendpointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();

		conn.translateToRelative(p);

		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();

		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);

		cmd.setRelativeDimensions(p.getDifference(ref1), p.getDifference(ref2));
		cmd.setConnection((OmConnectionEditPart) request.getSource());
		cmd.setIndex(request.getIndex());
		cmd.setPoint(p);
		return cmd;
	}

	/**
	 * 删除.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the delete bendpoint command
	 * @Date 2018-09-03 09:00
	 */
	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		BendpointCommand cmd = new DeleteBendpointCommand();
//		Point p = request.getLocation();
		cmd.setConnection((OmConnectionEditPart) request.getSource());
		cmd.setIndex(request.getIndex());
		return cmd;
	}

	/**
	 * 移动.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the move bendpoint command
	 * @Date 2018-09-03 09:00
	 */
	protected Command getMoveBendpointCommand(BendpointRequest request) {
		Point p = request.getLocation();
		Connection conn = getConnection();

		conn.translateToRelative(p);
		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();

		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);
		MoveBendpointCommand cmd = new MoveBendpointCommand(conn.getPoints()
				.getPoint(request.getIndex() + 1));

		cmd.setRelativeDimensions(p.getDifference(ref1), p.getDifference(ref2));
		cmd.setConnection((OmConnectionEditPart) request.getSource());
		cmd.setIndex(request.getIndex());
		cmd.setPoint(p);
		return cmd;
	}

	/**
	 * Show move bendpoint feedback.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void showMoveBendpointFeedback(BendpointRequest request) {
		// TODO Auto-generated method stub
		super.showMoveBendpointFeedback(request);
//		RelationEditPart node = null;
//		if (request.getSource() instanceof RelationEditPart)
//			node = (RelationEditPart) request.getSource();
//		if (node.getModel() instanceof Association) {
//			if (((Association) (node.getModel())).getClassA() == ((Association) (node
//					.getModel())).getClassB()) {
//				return;
//			}
//		}
//		((Shape) node.getFigure()).setLineStyle(SWT.LINE_DOT);//设置虚线
	}

}
