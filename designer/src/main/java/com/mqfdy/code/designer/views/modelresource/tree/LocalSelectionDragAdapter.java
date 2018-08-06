package com.mqfdy.code.designer.views.modelresource.tree;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.ComplexDataType;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ReferenceObject;

class LocalSelectionDragAdapter extends DragSourceAdapter {

	ISelectionProvider selectionProvider;

	public LocalSelectionDragAdapter(ISelectionProvider provider) {
		selectionProvider = provider;
	}

	public void dragFinished(DragSourceEvent event) {
		// TODO Auto-generated method stub
		super.dragFinished(event);
		// LocalSelectionTransfer.getInstance().setSelection(null);
	}

	public void dragSetData(DragSourceEvent event) {
		DragSource dragSource = (DragSource) event.widget;
		Control control = dragSource.getControl();
		if (control != control.getDisplay().getFocusControl()) {
			event.doit = false;
			return;
		}

		IStructuredSelection selection = (IStructuredSelection) selectionProvider
				.getSelection();

		if (selection.isEmpty()) {
			event.doit = false;
			return;
		}
		if (selection.getFirstElement() instanceof BusinessClass
				|| selection.getFirstElement() instanceof ReferenceObject
				|| selection.getFirstElement() instanceof Enumeration
				// || selection.getFirstElement() instanceof DataTransferObject
				|| selection.getFirstElement() instanceof ComplexDataType) {
			if (selection.getFirstElement() instanceof Enumeration) {
				if (IModelElement.STEREOTYPE_REFERENCE
						.equals(((AbstractModelElement) selection
								.getFirstElement()).getStereotype())) {
					LocalSelectionTransfer.getInstance().setSelection(null);
					event.doit = false;
					return;
				}
			}
			LocalSelectionTransfer.getInstance().setSelection(selection);
			event.doit = true;
		} else {
			LocalSelectionTransfer.getInstance().setSelection(null);
			event.doit = false;
			return;
		}
	}

	public void dragStart(DragSourceEvent event) {
		dragSetData(event);
	}
}
