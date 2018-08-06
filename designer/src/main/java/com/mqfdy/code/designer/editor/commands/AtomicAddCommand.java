package com.mqfdy.code.designer.editor.commands;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.ComplexDataType;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;

/**
 * 添加节点请求
 * 
 * @author mqfdy
 * 
 */

public class AtomicAddCommand extends org.eclipse.gef.commands.Command {

	private AbstractModelElement child;

	private AbstractModelElement parent;

	// int index = -1;

	public AtomicAddCommand() {
		super("add an atomic element");
	}

	/**
	 * FIXME 这里直接把canExecute()设置成false了，永远也不能执行啊
	 */
	@Override
	public boolean canExecute() {
		return false;
	}

	@Override
	public void execute() {
		redo();
	}

	public AbstractModelElement getParent() {
		return parent;
	}

	@Override
	public void redo() {
		// parent.addChild(child);

	}

	public void setChild(AbstractModelElement subpart) {
		child = subpart;
	}

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

	@Override
	public void undo() {
		// parent.removeChild(child);
	}
}
