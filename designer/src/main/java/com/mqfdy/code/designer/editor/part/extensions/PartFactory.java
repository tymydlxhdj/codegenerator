package com.mqfdy.code.designer.editor.part.extensions;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.mqfdy.code.designer.editor.part.AnnotationEditPart;
import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.editor.part.DTOEditPart;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.EnumerationEditPart;
import com.mqfdy.code.designer.editor.part.InheritanceEditPart;
import com.mqfdy.code.designer.editor.part.LinkAnnoEditPart;
import com.mqfdy.code.designer.editor.part.ReferenceObjectEditPart;
import com.mqfdy.code.designer.editor.part.RelationEditPart;
import com.mqfdy.code.designer.editor.utils.ModuleExceptionLogOnly;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Annotation;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 根据模型类型创建控制器
 * 
 * @author mqfdy
 * 
 */
public class PartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object modelElement) {
		EditPart part = null;
		BusinessObjectModel businessObjectModel = null;
		if (modelElement instanceof BusinessClass) {
			AbstractModelElement ab = (AbstractModelElement) modelElement;
			do {
				ab = ab.getParent();
			} while (!(ab instanceof BusinessObjectModel));
			businessObjectModel = (BusinessObjectModel) ab;
		}
		if (modelElement instanceof Diagram) {
			part = new DiagramEditPart((Diagram) modelElement);
		} else if (modelElement instanceof DiagramElement) {
			AbstractModelElement ab = (DiagramElement) modelElement;
			do {
				ab = ab.getParent();
			} while (!(ab instanceof BusinessObjectModel));
			businessObjectModel = (BusinessObjectModel) ab;

			AbstractModelElement ele = (AbstractModelElement) businessObjectModel
					.getModelElementById(((DiagramElement) modelElement)
							.getObjectId());
			if (ele == null) {
				BusinessObjectModel staticBom;
				staticBom = BusinessModelManager.getBuildInOm();
				ele = (AbstractModelElement) staticBom
						.getModelElementById(((DiagramElement) modelElement)
								.getObjectId());
			}
			if (ele instanceof Annotation) {
				part = new AnnotationEditPart((Annotation) ele,
						(DiagramElement) modelElement, businessObjectModel);
			} else if (ele instanceof BusinessClass) {
				part = new BusinessClassEditPart((BusinessClass) ele,
						(DiagramElement) modelElement, businessObjectModel);
			} else if (ele instanceof ReferenceObject) {
				part = new ReferenceObjectEditPart((ReferenceObject) ele,
						(DiagramElement) modelElement, businessObjectModel);
			} else if (ele instanceof DataTransferObject) {
				part = new DTOEditPart((DataTransferObject) ele,
						(DiagramElement) modelElement);
			} else if (ele instanceof Enumeration) {
				part = new EnumerationEditPart((Enumeration) ele,
						(DiagramElement) modelElement);
				// }else if (ele instanceof ComplexDataType) {
				// part = new ComplexDataTypeEditPart((ComplexDataType)
				// ele,(DiagramElement)modelElement);
			} else if (ele instanceof Association) {
				part = new RelationEditPart((Association) ele,
						(DiagramElement) modelElement);
			} else if (ele instanceof LinkAnnotation) {
				part = new LinkAnnoEditPart((LinkAnnotation) ele,
						(DiagramElement) modelElement);
			} else if (ele instanceof Inheritance) {
				part = new InheritanceEditPart((Inheritance) ele,
						(DiagramElement) modelElement);
			}
		} else if (modelElement instanceof BusinessClass) {
			part = new BusinessClassEditPart((BusinessClass) modelElement,
					null, businessObjectModel);
		} else if (modelElement instanceof Annotation) {
			part = new AnnotationEditPart((Annotation) modelElement,
					null, businessObjectModel);
		} else if (modelElement instanceof DataTransferObject) {
			part = new DTOEditPart((DataTransferObject) modelElement, null);
		} else if (modelElement instanceof Enumeration) {
			part = new EnumerationEditPart((Enumeration) modelElement, null);
			// }else if (modelElement instanceof ComplexDataType) {
			// part = new ComplexDataTypeEditPart((ComplexDataType)
			// modelElement,null);
		} else if (modelElement instanceof Association) {
			part = new RelationEditPart((Association) modelElement, null);
		} else if (modelElement instanceof LinkAnnotation) {
			part = new LinkAnnoEditPart((LinkAnnotation) modelElement, null);
		} else if (modelElement instanceof Inheritance) {
			part = new InheritanceEditPart((Inheritance) modelElement, null);
		} else {
			throw new ModuleExceptionLogOnly("unknown model element "
					+ modelElement + ", could not create editpart");
		}

		part.setModel(modelElement);

		return part;
	}

}
