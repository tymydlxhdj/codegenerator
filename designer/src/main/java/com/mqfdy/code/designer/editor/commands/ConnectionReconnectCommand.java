package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

import com.mqfdy.code.designer.editor.figure.anchor.BorderAnchor;
import com.mqfdy.code.designer.editor.figure.anchor.RectangleBorderAnchor;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 重新连接
 * 
 * A command to reconnect a connection to a different start point or end point.
 * The command can be undone or redone.
 * <p>
 * This command is designed to be used together with a GraphicalNodeEditPolicy.
 * To use this command propertly, following steps are necessary:
 * </p>
 * <ol>
 * <li>Create a subclass of GraphicalNodeEditPolicy.</li>
 * <li>Override the <tt>getReconnectSourceCommand(...)</tt> method. Here you
 * need to obtain the Connection model element from the ReconnectRequest, create
 * a new ConnectionReconnectCommand, set the new connection <i>source</i> by
 * calling the <tt>setNewSource(Shape)</tt> method and return the command
 * instance.
 * <li>Override the <tt>getReconnectTargetCommand(...)</tt> method.</li>
 * Here again you need to obtain the Connection model element from the
 * ReconnectRequest, create a new ConnectionReconnectCommand, set the new
 * connection <i>target</i> by calling the <tt>setNewTarget(Shape)</tt> method
 * and return the command instance.</li>
 * </ol>
 * 
 * @author mqfdy
 */
public class ConnectionReconnectCommand extends Command {

	/** The connection instance to reconnect. */
	private AbstractModelElement connection;

	/** The new source endpoint. */
	private AbstractModelElement newSource;

	/** The new target endpoint. */
	private AbstractModelElement newTarget;

	/** The original source endpoint. */
	private /*final*/ AbstractModelElement oldSource;

	/** The original target endpoint. */
	private /*final*/ AbstractModelElement oldTarget;

	private ReconnectRequest request;

	private DiagramElement conele = null;

	float oldPx = 0;
	float oldPy = 0;
	float newPx = 0;
	float newPy = 0;
	OmConnectionEditPart conn;
	boolean isTarget;

	/**
	 * Instantiate a command that can reconnect a Connection instance to a
	 * different source or target endpoint.
	 * 
	 * @param conn
	 *            the connection instance to reconnect (non-null)
	 * @param request
	 * @param request
	 * @throws IllegalArgumentException
	 *             if conn is null
	 */
	public ConnectionReconnectCommand(OmConnectionEditPart conn,
			ReconnectRequest request, boolean isTarget) {

		setLabel("reconnect connection");

		if (conn == null) {
			throw new IllegalArgumentException();
		}
		this.conn = conn;
		this.isTarget = isTarget;
		this.request = request;
		this.connection = (AbstractModelElement) conn.getModel();
		if(connection instanceof Association){
			this.oldSource = ((Association) connection).getClassA();
			this.oldTarget = ((Association) connection).getClassB();
		}
		else if(connection instanceof LinkAnnotation){
			this.oldSource = ((LinkAnnotation) connection).getClassA();
			this.oldTarget = ((LinkAnnotation) connection).getClassB();
		}
	}

	@Override
	public boolean canExecute() {
		if (isTarget
				&& BusinessModelUtil.getEditorBusinessModelManager()
						.queryObjectById(
								((DiagramElement) newTarget).getObjectId()) != oldTarget) {
			if (BusinessModelUtil
					.getEditorBusinessModelManager()
					.queryObjectById(((DiagramElement) newTarget).getObjectId()) instanceof ReferenceObject) {
				if (((ReferenceObject) (BusinessModelUtil
						.getEditorBusinessModelManager()
						.queryObjectById(((DiagramElement) newTarget)
								.getObjectId()))).getReferenceObject() != oldTarget) {
					return false;
				}
			} else if (BusinessModelManager.getBuildInOm().getModelElementById(
					((DiagramElement) newTarget).getObjectId()) != oldTarget) {
				return false;
			}
			return true;
		}
		if (!isTarget
				&& BusinessModelUtil.getEditorBusinessModelManager()
						.queryObjectById(
								((DiagramElement) newSource).getObjectId()) != oldSource) {
			if (BusinessModelUtil
					.getEditorBusinessModelManager()
					.queryObjectById(((DiagramElement) newSource).getObjectId()) instanceof ReferenceObject) {
				if (((ReferenceObject) (BusinessModelUtil
						.getEditorBusinessModelManager()
						.queryObjectById(((DiagramElement) newSource)
								.getObjectId()))).getReferenceObject() != oldSource) {
					return false;
				}
			} else if (BusinessModelManager.getBuildInOm().getModelElementById(
					((DiagramElement) newSource).getObjectId()) != oldSource) {
				return false;
			}
			return true;
		}
		return true;
	}

	/**
	 * Reconnect the connection to newSource (if setNewSource(...) was invoked
	 * before) or newTarget (if setNewTarget(...) was invoked before).
	 */
	@Override
	public void execute() {
		if (request instanceof ReconnectRequest) {
			ReconnectRequest r = (ReconnectRequest) request;
			OmConnectionEditPart con = (OmConnectionEditPart) r.getConnectionEditPart();
			Point loc = r.getLocation();
			Rectangle rect = Rectangle.SINGLETON;
			rect.setBounds(((GraphicalEditPart) r.getTarget()).getFigure()
					.getBounds());
			((GraphicalEditPart) r.getTarget()).getFigure()
					.translateToAbsolute(rect);
			Point ref = rect.getCenter();
			newPx = loc.x - ref.x;
			newPy = loc.y - ref.y;
			if (((GraphicalEditPart) r.getTarget()).getModel() instanceof DiagramElement) {
				conele = ((DiagramElement) ((GraphicalEditPart) r.getTarget())
						.getModel()).getBelongDiagram().getElementById(
						((AbstractModelElement) con.getModel()).getId());
				if (isTarget) {
					oldPx = conele.getStyle().getEndPositionX();
					oldPy = conele.getStyle().getEndPositionY();
				} else {
					oldPx = conele.getStyle().getPositionX();
					oldPy = conele.getStyle().getPositionY();
				}
			}
		}
		redo();
	}

	@Override
	public void redo() {
		if (request instanceof ReconnectRequest) {
			ReconnectRequest r = (ReconnectRequest) request;
			OmConnectionEditPart con = (OmConnectionEditPart) r.getConnectionEditPart();
			if (isTarget) {
				conele.getStyle().setEndPositionX(newPx);
				conele.getStyle().setEndPositionY(newPy);
				BorderAnchor anchor = new RectangleBorderAnchor(
						((GraphicalEditPart) con.getTarget()).getFigure());
				anchor.setAngle(Math.atan2(newPy, newPx));
				con.setTargetAnchor(anchor);
			} else {
				conele.getStyle().setPositionX(newPx);
				conele.getStyle().setPositionY(newPy);
				BorderAnchor anchor = new RectangleBorderAnchor(
						((GraphicalEditPart) con.getSource()).getFigure());
				anchor.setAngle(Math.atan2(newPy, newPx));
				con.setSourceAnchor(anchor);
			}
			con.refresh();
			conn.getSource().refresh();
			conn.getTarget().refresh();
		}
	}

	/**
	 * Set a new source endpoint for this connection. When execute() is invoked,
	 * the source endpoint of the connection will be attached to the supplied
	 * Shape instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>target</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionSource
	 *            a non-null Shape instance, to be used as a new source endpoint
	 * @throws IllegalArgumentException
	 *             if connectionSource is null
	 */
	public void setNewSource(AbstractModelElement connectionSource) {
		if (connectionSource == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection startpoint");
		newSource = connectionSource;
		newTarget = null;
	}

	/**
	 * Set a new target endpoint for this connection When execute() is invoked,
	 * the target endpoint of the connection will be attached to the supplied
	 * Shape instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>source</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionTarget
	 *            a non-null Shape instance, to be used as a new target endpoint
	 * @throws IllegalArgumentException
	 *             if connectionTarget is null
	 */
	public void setNewTarget(AbstractModelElement connectionTarget) {
		if (connectionTarget == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection endpoint");
		newSource = null;
		newTarget = connectionTarget;
	}

	/**
	 * Reconnect the connection to its original source and target endpoints.
	 */
	@Override
	public void undo() {
		if (isTarget) {
			conele.getStyle().setEndPositionX(oldPx);
			conele.getStyle().setEndPositionY(oldPy);
			OmConnectionEditPart con = (OmConnectionEditPart) conn;
			BorderAnchor anchor = new RectangleBorderAnchor(
					((GraphicalEditPart) con.getTarget()).getFigure());
			anchor.setAngle(Math.atan2(oldPy, oldPx));
			if (oldPy == 0.0&& oldPx == 0.0)
				anchor.setAngle(Double.MAX_VALUE);
			con.setTargetAnchor(anchor);
		} else {
			conele.getStyle().setPositionX(oldPx);
			conele.getStyle().setPositionY(oldPy);
			OmConnectionEditPart con = (OmConnectionEditPart) conn;
			BorderAnchor anchor = new RectangleBorderAnchor(
					((GraphicalEditPart) con.getSource()).getFigure());
			anchor.setAngle(Math.atan2(oldPy, oldPx));
			if (oldPy == 0.0&& oldPx == 0.0)
				anchor.setAngle(Double.MAX_VALUE);
			con.setSourceAnchor(anchor);
		}
		conn.refresh();
		conn.getTarget().refresh();
		conn.getSource().refresh();
	}

}
