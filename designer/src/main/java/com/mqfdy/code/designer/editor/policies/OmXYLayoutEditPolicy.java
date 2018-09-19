package com.mqfdy.code.designer.editor.policies;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.mqfdy.code.designer.editor.commands.NodeAddCommand;
import com.mqfdy.code.designer.editor.commands.NodeCreateCommand;
import com.mqfdy.code.designer.editor.commands.NodeSetConstraintCommand;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 布局策略 Layout policy for the modelRoot edit part of the class diagram editor.
 *
 * @author mqfdy
 * @see org.eclipse.gef.editpolicies.XYLayoutEditPolicy
 */
public class OmXYLayoutEditPolicy extends
		org.eclipse.gef.editpolicies.XYLayoutEditPolicy {
	
	/** The child. */
	private NodeEditPart child;

	/**
	 * Constructor for a new XYLayoutPolicy. Simply calls super constructor.
	 * 
	 */
	public OmXYLayoutEditPolicy() {
		super();
	}

	/**
	 * Constructor for a new XYLayoutPolicy. Sets xyLayout to the given layout.
	 * 
	 * @param layout
	 *            the XYLayout to be set
	 * 
	 * @see org.eclipse.draw2d.XYLayout
	 */
	public OmXYLayoutEditPolicy(XYLayout layout) {
		super();

		// set xy layout
		setXyLayout(layout);
	}

	/**
	 * Returns command after a child's constraint has been changed.
	 * 
	 * 图形对象被改变位置或者大小改变时的命令
	 *
	 * @author mqfdy
	 * @param req
	 *            the req
	 * @param nodePart
	 *            the node part
	 * @param constraint
	 *            the constraint
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Command createChangeConstraintCommand(ChangeBoundsRequest req,
			EditPart nodePart, Object constraint) {
		// node's Edit Part is a NodeEditPart and constraint is a rectangle :
		// return sel implemented NodSetConstraintCommand
		if (nodePart instanceof NodeEditPart && constraint instanceof Rectangle) {
			return new NodeSetConstraintCommand((NodeEditPart) nodePart/*.getModel()*/,
					req, (Rectangle) constraint);
		}
		// return super's createChangeConstraintCommand
		return super.createChangeConstraintCommand(req, nodePart, constraint);
	}

	/**
	 * Never used but must be implemented.
	 *
	 * @author mqfdy
	 * @param child
	 *            the child
	 * @param constraint
	 *            the constraint
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		// not used here, new method
		// createChangeConstraintCommand(ChangeBoundsRequest,
		// EditPart, Object) is called instead
		return null;
	}

	/**
	 * Returns the command to be used after a new element has been created.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the creates the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {

		// element is a NodeModelElement : return new NodeCreateCommand.
		if (request.getNewObject() instanceof AbstractModelElement) {

			AbstractModelElement newNode = (AbstractModelElement) request
					.getNewObject();
			return new NodeCreateCommand(newNode,
					(AbstractModelElement) getHost().getModel(),
					(Rectangle) getConstraintFor(request), getHost());
		}
		return null;
	}

	/**
	 * Calculates the proper constraint for a child being added.
	 * createAddCommand is called afterwards.
	 *
	 * @author mqfdy
	 * @param generic
	 *            the generic
	 * @return the adds the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Command getAddCommand(Request generic) {
		// cast request to ChangeBoundsRequest
		ChangeBoundsRequest request = (ChangeBoundsRequest) generic;

		// get all Edit Parts of request
		List editparts = request.getEditParts();

		// create a new CompoundCommand
		CompoundCommand res = new CompoundCommand();
		Rectangle r;
		Object constraint;

		// for each Edit Part set constraint
		for (Iterator epIt = editparts.iterator(); epIt.hasNext();) {
			GraphicalEditPart part = (GraphicalEditPart) epIt.next();
			r = part.getFigure().getBounds().getCopy();
			// convert r to absolute from childpart figure
			part.getFigure().translateToAbsolute(r);
			r = request.getTransformedRectangle(r);
			// convert this figure to relative
			getLayoutContainer().translateToRelative(r);
			getLayoutContainer().translateFromParent(r);
			r.translate(getLayoutOrigin().getNegated());
			constraint = getConstraintFor(r);

			/*
			 * if ((part instanceof ColumnEditPart)) { return null; } else {
			 */
			res.add(createAddCommand(part, constraint));
			// }
		}
		return res.unwrap();
	}

	/**
	 * Command used when a child has been added. Creates a new NodeAddCommand.
	 *
	 * @author mqfdy
	 * @param child
	 *            the child
	 * @param constraint
	 *            the constraint
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Command createAddCommand(EditPart child, Object constraint) {
		// get parent model
		Object parentModel = getHost().getModel();
		this.child = (NodeEditPart) child;
		// get child model
		Object childModel = child.getModel();

		// create new add command
		NodeAddCommand addCommand = new NodeAddCommand(
				(AbstractModelElement) parentModel,
				(AbstractModelElement) childModel, (Rectangle) constraint);
		return addCommand;
		// return null;
	}

}
