package com.mqfdy.code.designer.editor.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import com.mqfdy.code.designer.editor.commands.NodesDeleteCommand;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;

// TODO: Auto-generated Javadoc
/**
 * 一组组合编辑策略 主要包含删除command.
 *
 * @author mqfdy
 */
public class OmComponentEditPolicy extends
		org.eclipse.gef.editpolicies.ComponentEditPolicy {
	
	/**
	 * Creates the delete command.
	 *
	 * @author mqfdy
	 * @param deleteRequest
	 *            the delete request
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object parent = getHost().getParent().getModel();// 图
//		 Object child = getHost().getModel();//图元
//		if (parent instanceof AbstractModelElement
//				&& child instanceof AbstractModelElement) {
//			Command command = new NodeDeleteCommand((AbstractModelElement) child,
//					(AbstractModelElement) parent,getHost().getParent());
//			return command;
//		}
		if (parent instanceof AbstractModelElement
				&& /* child instanceof AbstractModelElement */deleteRequest
						.getEditParts().size() > 0) {
			Command command = new NodesDeleteCommand(
					deleteRequest.getEditParts(),
					(AbstractModelElement) parent, getHost().getParent());
			return command;
		}
//		 call super method
		return super.createDeleteCommand(deleteRequest);
	}

}