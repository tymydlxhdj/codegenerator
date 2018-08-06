package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;

import com.mqfdy.code.designer.editor.part.AnnotationEditPart;
import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.EnumerationEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 剪切图形
 * 
 * @author mqfdy
 * 
 */
public class CutNodeCommand extends Command {
	// 选中的图形EditPart
	private List<AbstractGraphicalEditPart> parts = new ArrayList<AbstractGraphicalEditPart>();
	// 选中的图形及其所对应的关联关系的EditPart
	private List<AbstractGraphicalEditPart> allParts = new ArrayList<AbstractGraphicalEditPart>();
	// 选中的业务对象
	private List<AbstractModelElement> abList = new ArrayList<AbstractModelElement>();
	// 选中实体对应的关联关系
	private List<AbstractModelElement> connList = new ArrayList<AbstractModelElement>();
	// 选中实体对应的关联关系ID
	private List<String> connIdList = new ArrayList<String>();
	// 选中的图形对应的关联关系的EditPart
	private List<AbstractGraphicalEditPart> conParts = new ArrayList<AbstractGraphicalEditPart>();

	public CutNodeCommand(List<AbstractGraphicalEditPart> selectedObjects) {
		super();
		for (AbstractGraphicalEditPart part : selectedObjects) {
			if (part instanceof BusinessClassEditPart
					|| part instanceof EnumerationEditPart
					|| part instanceof AnnotationEditPart)
				parts.add(part);
		}
		for (AbstractGraphicalEditPart part : selectedObjects) {
			if (part instanceof BusinessClassEditPart)
				abList.add(((BusinessClassEditPart) part).getBusinessClass());
			else if (part instanceof EnumerationEditPart)
				abList.add(((EnumerationEditPart) part).getEnumeration());
			else if (part instanceof AnnotationEditPart)
				abList.add(((AnnotationEditPart) part).getAnno());
		}

	}

	@Override
	public boolean canExecute() {
		if (abList == null || abList.isEmpty())
			return false;
		return true;
	}

	@Override
	public void execute() {
		if (canExecute()) {
			redo();
		}
	}

	@Override
	public void redo() {
		if (canExecute()) {
			List<AbstractGraphicalEditPart> sources = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
			List<AbstractGraphicalEditPart> targets = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
			for (AbstractGraphicalEditPart part : parts) {
				sources.addAll(part.getSourceConnections());
				targets.addAll(part.getTargetConnections());
			}
			for (AbstractGraphicalEditPart part : sources) {
				if (targets.contains(part))
					conParts.add(part);
			}
			allParts.addAll(parts);
			allParts.addAll(conParts);
			// 加入剪切板
			Clipboard.getDefault().setContents(allParts);

			// 删除图元
			for (AbstractGraphicalEditPart part : parts) {
				((Diagram) BusinessModelUtil.getBusinessModelDiagramEditor()
						.getViewer().getContents().getModel()).getElements()
						.remove(part.getModel());
			}
			for (AbstractModelElement ab : abList) {
				// 删除关联关系
				if ((ab instanceof BusinessClass || ab instanceof ReferenceObject)) {
					BusinessClass tempBc = null;
					if (ab instanceof ReferenceObject) {
						tempBc = (BusinessClass) ((ReferenceObject) ab)
								.getReferenceObject();
					} else {
						tempBc = (BusinessClass) ab;
					}
					List<Association> assoications = BusinessModelUtil
							.getEditorBusinessModelManager()
							.getAssociationsByBusinessClass(tempBc);
					if (assoications != null) {
						for (Association association : assoications) {
							if(!connIdList.contains(association.getId())){
								connList.add(association);
								connIdList.add(association.getId());
							}
							
							BusinessModelUtil.getEditorBusinessModelManager()
									.getBusinessObjectModel()
									.removeModelElement(association);
							BusinessModelUtil.getEditorBusinessModelManager()
									.businessModelElementDelete(association);
						}
					}

					List<Inheritance> inheritances = BusinessModelUtil
							.getEditorBusinessModelManager()
							.getInheritancesByBusinessClass(tempBc);
					if (assoications != null) {
						for (Inheritance inheritance : inheritances) {
							if(!connIdList.contains(inheritance.getId())){
								connList.add(inheritance);
								connIdList.add(inheritance.getId());
							}
						
							BusinessModelUtil.getEditorBusinessModelManager()
									.getBusinessObjectModel()
									.removeModelElement(inheritance);
							BusinessModelUtil.getEditorBusinessModelManager()
									.businessModelElementDelete(inheritance);
						}
					}
				}
				// 删除注释
				if ((ab instanceof AbstractModelElement)) {
					AbstractModelElement tempBc = ab;
//					if (ab instanceof ReferenceObject) {
//						tempBc = ((ReferenceObject) ab).getReferenceObject();
//					}
					List<LinkAnnotation> annotations = BusinessModelUtil
							.getEditorBusinessModelManager()
							.getLinksByModel(tempBc);
					if (annotations != null) {
						for (LinkAnnotation annotation : annotations) {
							if(!connIdList.contains(annotation.getId())){
								connList.add(annotation);
								connIdList.add(annotation.getId());
							}
							BusinessModelUtil.getEditorBusinessModelManager()
									.getBusinessObjectModel()
									.removeModelElement(annotation);
							BusinessModelUtil.getEditorBusinessModelManager()
									.businessModelElementDelete(annotation);
						}
					}

				}
				// 删除对象
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_DELETE, ab);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
			}
		}
	}

	@Override
	public boolean canUndo() {
		return canExecute();
	}

	@Override
	public void undo() {
		// 图元、对象
		for (AbstractGraphicalEditPart part : parts) {
			((Diagram) BusinessModelUtil.getBusinessModelDiagramEditor()
					.getViewer().getContents().getModel()).getElements().add(
					(DiagramElement) part.getModel());
			part.refresh();
		}
		if (connList.size() > 0) {
			for (AbstractModelElement as : connList) {
				BusinessModelEvent event1 = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, as);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event1);
			}
		}
		for (AbstractModelElement ab : abList) {
			if(BusinessModelUtil.isCustomObjectModel(ab) || BusinessModelUtil.isReverseObjectModel(ab)){
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, ab);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
			}
			((DiagramEditPart) BusinessModelUtil
					.getBusinessModelDiagramEditor().getViewer().getContents())
					.firePropertyChange(NodeModelElement.CHILD_ADDED_PROP,
							null, ab);
		}
		EditorOperation.refreshNodeEditParts();
	}
}