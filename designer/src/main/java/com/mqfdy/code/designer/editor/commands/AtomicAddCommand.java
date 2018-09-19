package com.mqfdy.code.designer.editor.commands;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.ComplexDataType;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;

// TODO: Auto-generated Javadoc
/**
 * 添加节点请求.
 *
 * @author mqfdy
 */

public class AtomicAddCommand extends org.eclipse.gef.commands.Command {

	/** The child. */
	private AbstractModelElement child;

	/** The parent. */
	private AbstractModelElement parent;

	// int index = -1;

	/**
	 * Instantiates a new atomic add command.
	 */
	public AtomicAddCommand() {
		super("add an atomic element");
	}

	/**
	 * FIXME 这里直接把canExecute()设置成false了，永远也不能执行啊.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public boolean canExecute() {
		return false;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		redo();
	}

	/**
	 * Gets the parent.
	 *
	 * @author mqfdy
	 * @return the parent
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getParent() {
		return parent;
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		// parent.addChild(child);

	}

	/**
	 * Sets the child.
	 *
	 * @author mqfdy
	 * @param subpart
	 *            the new child
	 * @Date 2018-09-03 09:00
	 */
	public void setChild(AbstractModelElement subpart) {
		child = subpart;
	}

	/**
	 * Sets the parent.
	 *
	 * @author mqfdy
	 * @param newParent
	 *            the new parent
	 * @Date 2018-09-03 09:00
	 */
	public void setParent(AbstractModelElement newParent) {
		if (!(newParent instanceof BusinessClass)
				&& !(newParent instanceof ComplexDataType)
				&& !(newParent instanceof DataTransferObject)
				&& !(newParent instanceof Enumeration)) {
			// throw new IllegalArgumentException(
			// "wrong parent type in AtomicAddCommand: "
			// + newParent.getClass().getSimpleName());
			return;
		}

		parent = newParent;
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		// parent.removeChild(child);
	}
}
