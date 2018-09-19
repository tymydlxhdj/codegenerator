package com.mqfdy.code.designer.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.mqfdy.code.designer.editor.commands.ConnectionCreateCommand;
import com.mqfdy.code.designer.editor.commands.ConnectionReconnectCommand;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 模型连线的编辑策略。在要求模型连线的应用中，一般要处理几种类型的请求，
 * 包括创建模型之间的连线、完成模型的连线、重定位连线输入端的位置和重定位连线输出端的位置.
 *
 * @author mqfdy
 * @since 1.5.2
 */
public class OmGraphicalNodeConnectionEditPolicy extends
		org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy {
	
	/**
	 * Gets the connection create command.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the connection create command
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 创建连线
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCreateCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		// get type of connection; transfered from
		// DiagramEditorPaletteFactory
		String type = request.getNewObjectType().toString();
		// get source model element
		AbstractModelElement source = (AbstractModelElement) getHost()
				.getModel();
		// create a new ConnectionCreateCommand
		// if(getHost() instanceof EditPart){
		// ((BusinessClassEditPart)getHost()).getModel();
		// }
		ConnectionCreateCommand cmd = new ConnectionCreateCommand(source, type,
				getHost());
		// set the start command of the request; only used to pass information
		request.setStartCommand(cmd);
		return cmd;
	}

	/**
	 * Gets the connection complete command.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the connection complete command
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 完成连线
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCompleteCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		// get start command
		ConnectionCreateCommand cmd = (ConnectionCreateCommand) request
				.getStartCommand();
		// get target model element
		AbstractModelElement target = (AbstractModelElement) getHost()
				.getModel();
		// set target in command
		// Set the target endpoint for the connection.
		cmd.setTarget(target, getHost());
		return cmd;
	}

	/**
	 * Gets the reconnect source command.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the reconnect source command
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 重定位输出连线起点
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// get ConnectionElement
		OmConnectionEditPart conn = (OmConnectionEditPart) request.getConnectionEditPart();
		// get new source NodeModelElement
		AbstractModelElement newSource = (AbstractModelElement) getHost()
				.getModel();
		// create new ConnectionReconnectCommand
		ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn,
				request, false);
		// set new source in ConnectionReconnectCommand
		cmd.setNewSource(newSource);
		return cmd;
	}

	/**
	 * Gets the reconnect target command.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the reconnect target command
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 重定位输入连线终点
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// get ConnectionElement
		OmConnectionEditPart conn = (OmConnectionEditPart) request.getConnectionEditPart();
		// get new target NodeModelElement
		AbstractModelElement newTarget = (AbstractModelElement) getHost()
				.getModel();
		// create new ConnectionReconnectCommand
		ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn,
				request, true);
		// set new target in ConnectionReconnectCommand
		cmd.setNewTarget(newTarget);
		return cmd;
	}
}
