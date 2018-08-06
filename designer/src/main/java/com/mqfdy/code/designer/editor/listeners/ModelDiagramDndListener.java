package com.mqfdy.code.designer.editor.listeners;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IEditorPart;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.utils.ElementFactory;

/**
 * 加入从选项板直接拖动组件到编辑区的功能，释放鼠标左键在编辑区释放选中对象时的监听器
 * 
 * @title:拖拽监听器
 * @description:从选项板直接拖动组件到编辑区
 * @author mqfdy
 */
public class ModelDiagramDndListener extends TemplateTransferDropTargetListener {

	public ModelDiagramDndListener(EditPartViewer viewer) {
		super(viewer);
	}

	// 拖拽进入时的处理
	@Override
	protected void handleDragOver() {
		getCurrentEvent().detail = DND.DROP_COPY;
		super.handleDragOver();
	}

	@Override
	protected void handleDrop() {
		updateTargetRequest();
		updateTargetEditPart();

		if (getTargetEditPart() != null) {
			Command command = getCommand();
			if (command != null && command.canExecute())
				getViewer().getEditDomain().getCommandStack().execute(command);
			else
				getCurrentEvent().detail = DND.DROP_COPY;
		} else
			getCurrentEvent().detail = DND.DROP_COPY;

	}

	@Override
	protected void updateTargetRequest() {
		((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
	}

	@Override
	protected Request createTargetRequest() {
		CreateRequest request = new CreateRequest();
		request.setFactory(getFactory(TemplateTransfer.getInstance()
				.getTemplate()));

		IEditorPart editor = BusinessModelEditorPlugin
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor instanceof BusinessModelDiagramEditor) {
			((BusinessModelDiagramEditor) editor).setDirty(true);
		}
		return request;
	}

	@Override
	public void drop(DropTargetEvent event) {
		setCurrentEvent(event);
		eraseTargetFeedback();
		handleDrop();
	}

	protected CreationFactory getFactory(Object template) {
		return new ElementFactory(template);
	}

}