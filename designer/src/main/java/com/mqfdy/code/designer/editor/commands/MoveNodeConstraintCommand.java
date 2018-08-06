package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;

import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 设置节点的位置 方向键 包括图形被移动时和形状大小改变时的位置
 * 
 * Command to move or resize a node.
 * 
 * @author mqfdy
 * 
 */
public class MoveNodeConstraintCommand extends Command {

	private DiagramElement node;

	// private ChangeBoundsRequest req;

	private Rectangle oldBounds;

	private Rectangle newBounds;

	private NodeEditPart nodeEditPart;

	private int type;

	public MoveNodeConstraintCommand(NodeEditPart nodeEditPart, int type) {

		setLabel("change bounds");
		this.type = type;
		if (nodeEditPart == null) {
			throw new IllegalArgumentException();
		}
		this.nodeEditPart = nodeEditPart;
		this.node = (DiagramElement) nodeEditPart.getModel();
		// this.req = req;
	}

	@Override
	public boolean canExecute() {
		// Object type = req.getType();
		// return (RequestConstants.REQ_MOVE.equals(type)
		// || RequestConstants.REQ_MOVE_CHILDREN.equals(type)
		// || RequestConstants.REQ_RESIZE.equals(type)
		// || RequestConstants.REQ_RESIZE_CHILDREN.equals(type)
		// || RequestConstants.REQ_ALIGN.equals(type) ||
		// RequestConstants.REQ_ALIGN_CHILDREN
		// .equals(type));
		return true;
	}

	@Override
	public void execute() {
		oldBounds = new Rectangle(new Point(node.getStyle().getPositionX(),
				node.getStyle().getPositionY()), new Dimension(node.getStyle()
				.getWidth(), node.getStyle().getHeight()));
		Rectangle old = nodeEditPart.getFigure().getBounds();
		newBounds = new Rectangle();
		if (type == SWT.UP) {
			newBounds.setX(old.x);
			newBounds.setY(old.y - 1);
		} else if (type == SWT.DOWN) {
			newBounds.setX(old.x);
			newBounds.setY(old.y + 1);
		} else if (type == SWT.LEFT) {
			newBounds.setX(old.x - 1);
			newBounds.setY(old.y);
		} else if (type == SWT.RIGHT) {
			newBounds.setX(old.x + 1);
			newBounds.setY(old.y);
		}
		newBounds.setWidth(old.width);
		newBounds.setHeight(old.height);
		redo();
	}

	@Override
	public void redo() {
		node.getStyle().setPositionX(newBounds.getLocation().x);
		node.getStyle().setPositionY(newBounds.getLocation().y);
		// node.getStyle().setWidth(newBounds.getSize().width);
		// node.getStyle().setHeight(newBounds.getSize().height);
		((NodeEditPart) nodeEditPart).refresh();
	}

	@Override
	public void undo() {
		node.getStyle().setPositionX(oldBounds.getLocation().x);
		node.getStyle().setPositionY(oldBounds.getLocation().y);
		// node.getStyle().setWidth(oldBounds.getSize().width);
		// node.getStyle().setHeight(oldBounds.getSize().height);
		((NodeEditPart) nodeEditPart).refresh();
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return true;
	}

}
