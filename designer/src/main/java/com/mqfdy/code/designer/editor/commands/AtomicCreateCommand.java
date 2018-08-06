package com.mqfdy.code.designer.editor.commands;

import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.ComplexDataType;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;

/**
 * 添加节点
 * 
 * Command to create new "atomic" elements. These are elements that cannot have
 * connections and don't need specific layout contraints in their parent (e.g.
 * ColumnElement with BusinessClassElement as parent).
 * 
 * @author mqfdy
 * 
 */
public class AtomicCreateCommand extends org.eclipse.gef.commands.Command {

	private AbstractModelElement child;

	private AbstractModelElement parent;

	/**
	 * The visibility of the object to create. See {@link IConstants} for
	 * possible values.
	 * <p>
	 * Only interesing for {@link ColumnElement} and {@link MethodElement}.
	 */
	// private String visibility;

	public AtomicCreateCommand(String visibility) {

		super("column create command");

		setLabel("create an column element");

		// this.visibility = visibility;
	}

	@Override
	public boolean canExecute() {
		if (child == null || parent == null) {
			return false;
		}
		// return /*&& child instanceof ColumnElement*/parent instanceof
		// DataTransferObject
		// ||parent instanceof BusinessClass||parent instanceof Enumeration
		// ||parent instanceof ComplexDataType;
		return child instanceof DataTransferObject
				|| child instanceof BusinessClass
				|| child instanceof Enumeration
				|| child instanceof ComplexDataType;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void redo() {
		// CommandUtil.computeNodeName(parent,child);

		// ((IContainerElement) parent).assimilate(child);

		// // set visiblity
		// if (child instanceof ColumnElement) {
		//
		// child.setPropertyValue(ColumnElement.VISIBILITY_PROP,
		// VisibilityPropertyDescriptor.stringToInteger(visibility));
		// }
	}

	public void setChild(AbstractModelElement subpart) {
		child = subpart;
	}

	public void setParent(AbstractModelElement newParent) {
		parent = newParent;
	}

	@Override
	public void undo() {
		// Element uml2Element = child.getUML2Element();
		// if (uml2Element != null) {
		// uml2Element.destroy();
		// } else {
		// parent.removeChild(child);
		// }
		// parent.removeChild(child);
	}
}
