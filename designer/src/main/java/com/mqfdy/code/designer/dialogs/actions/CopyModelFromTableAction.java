package com.mqfdy.code.designer.dialogs.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessOperation;

/**
 * 从业务模型树上复制对象
 * 
 * @author mqfdy
 * 
 */
public class CopyModelFromTableAction extends Action {
	private List<AbstractModelElement> proList = new ArrayList<AbstractModelElement>();
	private TableViewer tableViewer;

	public CopyModelFromTableAction(TableViewer tableViewer) {
		super(ActionTexts.MODEL_ELEMENT_COPY);
		this.tableViewer = tableViewer;
		setId(ActionFactory.COPY.getId());
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	public void run() {
		if (!isEnabled())
			return;
		if (tableViewer != null && tableViewer.getSelection() != null) {
			proList.clear();
			TableItem[] items = tableViewer.getTable().getSelection();
			for (int j = 0; j < items.length; j++) {
				TableItem item = tableViewer.getTable().getSelection()[j];
				AbstractModelElement modelElement = (AbstractModelElement) item
						.getData();
				if (modelElement instanceof AbstractModelElement) {
					proList.add(modelElement);
				}
			}
			if (proList.size() > 0)
				Clipboard.getDefault().setContents(proList);
		}
	}

	@Override
	public boolean isEnabled() {
		TableItem[] items = tableViewer.getTable().getSelection();
		boolean flag = true;
		for (int i = 0; i < items.length; i++) {
			TableItem item = tableViewer.getTable().getSelection()[i];
			AbstractModelElement modelElement = (AbstractModelElement) item
					.getData();
			if (!(modelElement instanceof AbstractModelElement)) {
				flag = false;
			} else if (modelElement instanceof BusinessOperation
					&& BusinessOperation.OPERATION_TYPE_STANDARD
							.equals(((BusinessOperation) modelElement).getOperationType())) {
				flag = false;
			}
		}
		return flag;
	}
}