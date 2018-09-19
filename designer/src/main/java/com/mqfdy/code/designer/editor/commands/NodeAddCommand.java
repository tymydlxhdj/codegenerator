package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 添加节点 Command to add a child to a parent (in the model). Adding is not
 * creating, but only a change in the parent-children-relation of existing
 * elements.
 * 
 * @author mqfdy
 * 
 */
public class NodeAddCommand extends Command {

	/** The parent. */
	private AbstractModelElement parent;

	/** The child. */
	private AbstractModelElement child;

	/** The constraint. */
	private Rectangle constraint;

//	private Point oldPosition;

	/**
 * Instantiates a new node add command.
 *
 * @param parent
 *            the parent
 * @param child
 *            the child
 * @param constraint
 *            the constraint
 */
public NodeAddCommand(AbstractModelElement parent,
			AbstractModelElement child, Rectangle constraint) {
		this.parent = parent;
		this.child = child;
		this.constraint = constraint;
		setLabel("add command");
		if (child != null) {
			// oldPosition = child.getLocation();
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		return parent != null && child != null && constraint != null;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		redo();
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		// parent.addChild(child);
		Point pos = constraint.getLocation();
		// child.setLocation(pos);
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		// parent.removeChild(child);
		// child.setLocation(oldPosition);
	}

}
