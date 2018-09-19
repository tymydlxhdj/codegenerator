package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * 设置节点的位置
 * 
 * 包括图形被移动时和形状大小改变时的位置
 * 
 * Command to move or resize a node.
 * 
 * @author mqfdy
 * 
 */
public class NodeSetConstraintCommand extends Command {

	/** The node. */
	private DiagramElement node;

	/** The req. */
	private ChangeBoundsRequest req;

	/** The old bounds. */
	private Rectangle oldBounds;

	/** The new bounds. */
	private Rectangle newBounds;

	/** The node edit part. */
	private NodeEditPart nodeEditPart;

	/**
	 * Instantiates a new node set constraint command.
	 *
	 * @param nodeEditPart
	 *            the node edit part
	 * @param req
	 *            the req
	 * @param newBounds
	 *            the new bounds
	 */
	public NodeSetConstraintCommand(NodeEditPart nodeEditPart,
			ChangeBoundsRequest req, Rectangle newBounds) {

		setLabel("change bounds");

		if (nodeEditPart == null || req == null || newBounds == null) {
			throw new IllegalArgumentException();
		}
		this.nodeEditPart = nodeEditPart;
		this.node = (DiagramElement) nodeEditPart.getModel();
		this.req = req;
		this.newBounds = newBounds;
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		Object type = req.getType();
		return (RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				|| RequestConstants.REQ_RESIZE.equals(type)
				|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type)
				|| RequestConstants.REQ_ALIGN.equals(type) || RequestConstants.REQ_ALIGN_CHILDREN
					.equals(type));
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		oldBounds = new Rectangle(new Point(node.getStyle().getPositionX(),
				node.getStyle().getPositionY()), new Dimension(node.getStyle()
				.getWidth(), node.getStyle().getHeight()));
		redo();
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		node.getStyle().setPositionX(newBounds.getLocation().x);
		node.getStyle().setPositionY(newBounds.getLocation().y);
		node.getStyle().setWidth(newBounds.getSize().width);
		node.getStyle().setHeight(newBounds.getSize().height);
//		 ((NodeEditPart)nodeEditPart).refresh();
		EditorOperation.refreshNodeEditParts();
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		node.getStyle().setPositionX(oldBounds.getLocation().x);
		node.getStyle().setPositionY(oldBounds.getLocation().y);
		node.getStyle().setWidth(oldBounds.getSize().width);
		node.getStyle().setHeight(oldBounds.getSize().height);
//		 ((NodeEditPart)nodeEditPart).refresh();
		EditorOperation.refreshNodeEditParts();
	}
}
