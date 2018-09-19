package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;

import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;

// TODO: Auto-generated Javadoc
/**
 * 粘贴图形.
 *
 * @author mqfdy
 */

public class PasteOperationsCommand extends Command {
	
	/** The ops list. */
	// 内存中复制的业务实体操作
	List<BusinessOperation> opsList = new ArrayList<BusinessOperation>();
	
	/** The new op list. */
	// 复制出的对象
	List<AbstractModelElement> newOpList = new ArrayList<AbstractModelElement>();
	
	/** The old bu. */
	private BusinessClass oldBu;

	/**
	 * Instantiates a new paste operations command.
	 */
	public PasteOperationsCommand() {
		super();
		opsList.clear();
		if (Clipboard.getDefault().getContents() != null)
			opsList.addAll((List<BusinessOperation>) Clipboard.getDefault()
					.getContents());
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		if (opsList.isEmpty())
			return false;
		// 不是业务实体
		List<AbstractGraphicalEditPart> list = BusinessModelUtil
				.getSelectedEditParts();
		if (list.size() != 1
				|| !(list.get(0) instanceof BusinessClassEditPart)
				|| BusinessClass.STEREOTYPE_BUILDIN
						.equals(((BusinessClassEditPart) list.get(0))
								.getBusinessClass().getStereotype()))
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		if (!canExecute())
			return;
		redo();
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		List<AbstractGraphicalEditPart> list = BusinessModelUtil
				.getBusinessModelDiagramEditor().getViewer()
				.getSelectedEditParts();
		oldBu = ((BusinessClassEditPart) list.get(0)).getBusinessClass();
		;
		for (BusinessOperation op : opsList) {
			BusinessOperation newOper = op.cloneChangeId();
			newOper.setBelongBusinessClass(oldBu);

			String name = BusinessModelUtil.getBusinessModelDiagramEditor()
					.getBusinessModelManager()
					.generateNextOperationName(newOper.getName(), oldBu);
			String disName = BusinessModelUtil.getBusinessModelDiagramEditor()
					.getBusinessModelManager()
					.generateNextOperationDisName(newOper.getDisplayName(), oldBu);
			newOper.setDisplayName(disName);
//			if (!name.equals(newOper.getName())) {
//				newOper.setDisplayName(name);
//			}
			newOper.setName(name);
			newOper.setId(UUID.randomUUID().toString().replaceAll("-", ""));

			oldBu.addOperation(newOper);
			newOpList.add(newOper);

		}
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_UPDATE, oldBu);
		BusinessModelUtil.getEditorBusinessModelManager()
				.businessObjectModelChanged(bcAddevent);
	}

	/**
	 * @return
	 */
	@Override
	public boolean canUndo() {
		return canExecute();
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		oldBu.getOperations().removeAll(newOpList);
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_UPDATE, oldBu);
		BusinessModelUtil.getEditorBusinessModelManager()
				.businessObjectModelChanged(bcAddevent);
	}
}