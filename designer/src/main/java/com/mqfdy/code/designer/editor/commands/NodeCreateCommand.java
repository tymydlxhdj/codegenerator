package com.mqfdy.code.designer.editor.commands;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.dialogs.AnnotationEditorDialog;
import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.EnumEditDialog;
import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.preferences.ModelPreferencePage;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Annotation;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.ComplexDataType;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.VersionInfo;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

/**
 * 在编辑器中创建对象
 * 
 * Adds a new node to the diagram.
 * 
 * @author ZHANGHE
 * 
 */
public class NodeCreateCommand extends Command {

	private AbstractModelElement newNode;

	// can be model modelRoot or package
	private AbstractModelElement container;

	private Rectangle bounds;

	private EditPart editPart;

	private DiagramElement ele = new DiagramElement();

	private boolean canRedo = true;
	private boolean isdid = false;

	public NodeCreateCommand(AbstractModelElement newNode,
			AbstractModelElement container, Rectangle bounds, EditPart editPart) {
		if (newNode == null || container == null || bounds == null) {
			throw new IllegalArgumentException();
		}
		this.editPart = editPart;
		this.newNode = newNode;
		this.container = container;
		this.bounds = bounds;

		setLabel("add new node");
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return super.canUndo();
	}

	@Override
	public boolean canExecute() {
		if (newNode == null || container == null) {
			return false;
		}
		boolean f = container instanceof Diagram
				&& (newNode instanceof ComplexDataType
						|| newNode instanceof BusinessClass
						|| newNode instanceof Annotation
						|| newNode instanceof Enumeration || newNode instanceof DataTransferObject);
		return f;
	}

	@Override
	public void execute() {
		ele.setObjectId(newNode.getId());
		ElementStyle st = new ElementStyle();
		st.setHeight(bounds.height);
		st.setWidth(bounds.width);
		st.setPositionX(bounds.x);
		st.setPositionY(bounds.y);
		if (bounds.height == -1){
			st.setHeight(200);
			if(newNode instanceof Annotation)
				st.setHeight(100);
		}
		if (bounds.width == -1){
			st.setWidth(160);
		}
		ele.setStyle(st);
		ele.setBelongDiagram((Diagram) container);
		if (newNode instanceof BusinessClass) {
			String name = BusinessModelUtil
					.getEditorBusinessModelManager()
					.generateNextBusinessClassName();
			((BusinessClass) newNode).setName(name);
			((BusinessClass) newNode).setDisplayName(BusinessModelUtil
					.getEditorBusinessModelManager()
					.generateNextBusinessClassName());
			((BusinessClass) newNode).setTableName(BusinessModelUtil.getTableName(name));
			((BusinessClass) newNode)
					.setBelongPackage((ModelPackage) (container.getParent()));
			((BusinessClass) newNode).setVersionInfo(VersionInfo
					.getVersionInfo());
			((BusinessClass) newNode).setStereotype(IModelElement.STEREOTYPE_CUSTOM);
			// 添加标准操作
			IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
					.getPreferenceStore();
			String isAdd = store
					.getString(ModelPreferencePage.ISADD_CUSTOMOPERATION);
			if (isAdd.equals("")
					|| store.getBoolean(ModelPreferencePage.ISADD_CUSTOMOPERATION)) {
				List<BusinessOperation> opList = BusinessOperation
						.getStandardOperations();
				for (BusinessOperation operation : opList) {
					operation.setBelongBusinessClass(((BusinessClass) newNode));
					((BusinessClass) newNode).addOperation(operation);
				}
			}
			// BEStatus status = new BEStatus();
			// status.setOrderNum(1);
			// status.setName("non_status");
			// status.setDisplayName("缺省状态");
			// status.setNoneStatus(true);//标示当前为无状态
			// ((BusinessClass) newNode).addStatus(status);
			// status.setBelongBusinessClass(((BusinessClass) newNode));
		} else if (newNode instanceof DataTransferObject) {
			((DataTransferObject) newNode).setName(BusinessModelUtil
					.getEditorBusinessModelManager().generateNextDTOName());
			((DataTransferObject) newNode).setDisplayName(BusinessModelUtil
					.getEditorBusinessModelManager().generateNextDTOName());
			((DataTransferObject) newNode)
					.setBelongPackage((ModelPackage) (container.getParent()));
			((DataTransferObject) newNode).setVersionInfo(VersionInfo
					.getVersionInfo());
			// } else if (newNode instanceof ComplexDataType) {
			// ComplexDataType element = (ComplexDataType) newNode;
		} else if (newNode instanceof Enumeration) {
			((Enumeration) newNode).setName(BusinessModelUtil
					.getEditorBusinessModelManager()
					.generateNextEnumerationName());
			((Enumeration) newNode).setDisplayName(BusinessModelUtil
					.getEditorBusinessModelManager()
					.generateNextEnumerationName());
			((Enumeration) newNode).setBelongPackage((ModelPackage) (container
					.getParent()));
			((Enumeration) newNode)
					.setVersionInfo(VersionInfo.getVersionInfo());
			((Enumeration) newNode).setStereotype(IModelElement.STEREOTYPE_CUSTOM);
		}else if (newNode instanceof Annotation) {
			((Annotation) newNode).setBelongPackage((ModelPackage) (container
					.getParent()));
//			((Annotation) newNode).setStereotype(IModelElement.STEREOTYPE_CUSTOM);
		}
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		if (newNode instanceof Annotation) {
			final AnnotationEditorDialog dialog = new AnnotationEditorDialog(
					true,shell, newNode,
					((Annotation) newNode).getBelongPackage());
			int i = dialog.open();
			if (i == IDialogConstants.OK_ID) {
				isdid = true;
				canRedo = true;
				redo();
				// BusinessModelEvent event = new
				// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_ADD,dialog.getEditingElement());
				// BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event);
			} else if (i == IDialogConstants.CANCEL_ID) {
				if (dialog.isChanged() == false) {
					this.undo();
					// IEditorPart editor = BusinessModelEditorPlugin
					// .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					// BusinessModelDiagramEditor b = ((MultiPageEditor)
					// editor).getBuEditor();
					// b.getAction(ActionFactory.UNDO.getId()).run();
					canRedo = false;
				}
			}
		} else if (newNode instanceof BusinessClass) {
			final BusinessClassEditorDialog dialog = new BusinessClassEditorDialog(
					true,shell, newNode,
					((BusinessClass) newNode).getBelongPackage());
			int i = dialog.open();
			if (i == IDialogConstants.OK_ID) {
				isdid = true;
				canRedo = true;
				redo();
				// BusinessModelEvent event = new
				// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_ADD,dialog.getEditingElement());
				// BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event);
			} else if (i == IDialogConstants.CANCEL_ID) {
				if (dialog.isChanged() == false) {
					this.undo();
					// IEditorPart editor = BusinessModelEditorPlugin
					// .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					// BusinessModelDiagramEditor b = ((MultiPageEditor)
					// editor).getBuEditor();
					// b.getAction(ActionFactory.UNDO.getId()).run();
					canRedo = false;
				}
			}
		} else if (newNode instanceof Enumeration) {
			final EnumEditDialog dialog = new EnumEditDialog(true,shell,
					((Enumeration) newNode).getBelongPackage(),
					(Enumeration) newNode);
			int i = dialog.open();
			isdid = true;
			canRedo = true;
			// BusinessModelEvent bcAddevent = new
			// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_ADD,newNode);
			// BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(bcAddevent);

			if (i == IDialogConstants.CANCEL_ID) {
				if (dialog.isChanged() == false) {
					this.undo();
					// IEditorPart editor = BusinessModelEditorPlugin
					// .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					// BusinessModelDiagramEditor b = ((MultiPageEditor)
					// editor).getBuEditor();
					// b.getAction(ActionFactory.UNDO.getId()).run();
					isdid = false;
					canRedo = false;
				}
			} else {
				redo();
			}
		}

	}

	@Override
	public void redo() {
		if (canRedo) {
			((Diagram) container).addElement(ele);
			// Cursor cursor = new Cursor(null, null, 0, 0);
			IEditorPart editorPart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (editorPart instanceof BusinessModelEditor) {
				Cursor cur = ((BusinessModelEditor) editorPart).getBuEditor()
						.getViewer().getControl().getCursor();
				if (cur != null)
					cur.dispose();
			}
			BusinessModelEvent bcAddevent = new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_ADD, newNode);
			BusinessModelUtil.getEditorBusinessModelManager()
					.businessObjectModelChanged(bcAddevent);
			if (editPart instanceof NodeEditPart)
				((NodeEditPart) editPart).firePropertyChange(
						NodeModelElement.CHILD_ADDED_PROP, null, newNode);
			if (editPart instanceof DiagramEditPart)
				((DiagramEditPart) editPart).firePropertyChange(
						NodeModelElement.CHILD_ADDED_PROP, null, newNode);
		}

	}

	@Override
	public void undo() {
		if (canRedo) {
			((Diagram) container).getElements().remove(ele);
			BusinessModelEvent bcAddevent = new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_DELETE, newNode);
			BusinessModelUtil.getEditorBusinessModelManager()
					.businessObjectModelChanged(bcAddevent);
			if (editPart instanceof DiagramEditPart)
				((DiagramEditPart) editPart).firePropertyChange(
						NodeModelElement.CHILD_REMOVED_PROP, null, newNode);
		}
	}
}
