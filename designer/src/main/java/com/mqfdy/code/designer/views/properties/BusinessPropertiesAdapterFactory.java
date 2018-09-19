package com.mqfdy.code.designer.views.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.properties.sources.AbstractModelElementPropertySource;
import com.mqfdy.code.designer.views.properties.sources.AssociationPropertySource;
import com.mqfdy.code.designer.views.properties.sources.BEStatusPropertySource;
import com.mqfdy.code.designer.views.properties.sources.BusinessClassPropertySource;
import com.mqfdy.code.designer.views.properties.sources.BusinessObjectModelPropertySource;
import com.mqfdy.code.designer.views.properties.sources.BusinessOperationPropertySource;
import com.mqfdy.code.designer.views.properties.sources.BusinessPropertySource;
import com.mqfdy.code.designer.views.properties.sources.DTOPropertySource;
import com.mqfdy.code.designer.views.properties.sources.DataTransferObjectPropertySource;
import com.mqfdy.code.designer.views.properties.sources.DiagramElementSource;
import com.mqfdy.code.designer.views.properties.sources.DiagramPropertySource;
import com.mqfdy.code.designer.views.properties.sources.EnumElementPropertySource;
import com.mqfdy.code.designer.views.properties.sources.EnumerationPropertySource;
import com.mqfdy.code.designer.views.properties.sources.InheritancePropertySource;
import com.mqfdy.code.designer.views.properties.sources.ModelPackagePropertySource;
import com.mqfdy.code.designer.views.properties.sources.PKPropertySource;
import com.mqfdy.code.designer.views.properties.sources.PersistencePropertySource;
import com.mqfdy.code.designer.views.properties.sources.ReferenceObjectPropertySource;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BEStatus;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * 获取bean对应的IPropertySource.
 *
 * @author mqfdy
 */
public class BusinessPropertiesAdapterFactory implements IAdapterFactory {

	/**
	 * Gets the adapter.
	 *
	 * @author mqfdy
	 * @param adaptableObject
	 *            the adaptable object
	 * @param adapterType
	 *            the adapter type
	 * @return the adapter
	 * @Date 2018-09-03 09:00
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof AbstractGraphicalEditPart) {
			adaptableObject = ((AbstractGraphicalEditPart) adaptableObject)
					.getModel();
			Object ele = null;
			if (adaptableObject instanceof DiagramElement) {
				ele = BusinessModelUtil.getEditorBusinessModelManager()
						.queryObjectById(
								((DiagramElement) adaptableObject)
										.getObjectId());
				if (ele == null) {
					ele = BusinessModelManager.getBuildInOm()
							.getModelElementById(
									((DiagramElement) adaptableObject)
											.getObjectId());
				}
			} else if (adaptableObject instanceof Diagram) {
				ele = adaptableObject;
			} else if (adaptableObject instanceof AbstractModelElement) {
				ele = adaptableObject;
			}
			return new CustomPropertySourceProvider((AbstractModelElement) ele);
		}
		return new CustomPropertySourceProvider(
				(AbstractModelElement) adaptableObject);
	}

	/**
	 * @return
	 */
	public Class<AbstractModelElement>[] getAdapterList() {
		return new Class[] { AbstractModelElement.class };
	}
}

class CustomPropertySourceProvider implements IPropertySourceProvider {

	private Object editPart;

	public CustomPropertySourceProvider(AbstractModelElement editPart) {
		this.editPart = editPart;
	}

	public IPropertySource getPropertySource(Object object) {
		if (object instanceof BusinessClass)
			return new BusinessClassPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof Diagram)
			return new DiagramPropertySource((AbstractModelElement) editPart);
		if (object instanceof Association)
			return new AssociationPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof Inheritance)
			return new InheritancePropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof PKProperty)
			return new PKPropertySource((AbstractModelElement) editPart);
		if (object instanceof PersistenceProperty)
			return new PersistencePropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof BEStatus)
			return new BEStatusPropertySource((AbstractModelElement) editPart);
		if (object instanceof Enumeration)
			return new EnumerationPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof EnumElement)
			return new EnumElementPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof ReferenceObject)
			return new ReferenceObjectPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof DTOProperty)
			return new DTOPropertySource((AbstractModelElement) editPart);
		// if(object instanceof ComplexDataType)
		// return new ComplexDataTypePropertySource((AbstractModelElement)
		// editPart);
		if (object instanceof Property)
			return new BusinessPropertySource((AbstractModelElement) editPart);
		if (object instanceof DataTransferObject)
			return new DataTransferObjectPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof ModelPackage)
			return new ModelPackagePropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof BusinessOperation)
			return new BusinessOperationPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof BusinessObjectModel)
			return new BusinessObjectModelPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof DiagramElement)
			return new DiagramElementSource((AbstractModelElement) editPart);
		if (object instanceof AbstractModelElement)
			return new AbstractModelElementPropertySource(
					(AbstractModelElement) editPart);
		if (object instanceof AbstractGraphicalEditPart) {
			// object = ((AbstractGraphicalEditPart)object).getModel();
			if (editPart instanceof BusinessClass)
				return new BusinessClassPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof Diagram)
				return new DiagramPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof Association)
				return new AssociationPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof Inheritance)
				return new InheritancePropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof PKProperty)
				return new PKPropertySource((AbstractModelElement) editPart);
			if (editPart instanceof PersistenceProperty)
				return new PersistencePropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof BEStatus)
				return new BEStatusPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof Enumeration)
				return new EnumerationPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof EnumElement)
				return new EnumElementPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof ReferenceObject)
				return new ReferenceObjectPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof DTOProperty)
				return new DTOPropertySource((AbstractModelElement) editPart);
			// if(editPart instanceof ComplexDataType)
			// return new ComplexDataTypePropertySource((AbstractModelElement)
			// editPart);
			if (editPart instanceof Property)
				return new BusinessPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof DataTransferObject)
				return new DataTransferObjectPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof ModelPackage)
				return new ModelPackagePropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof BusinessOperation)
				return new BusinessOperationPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof BusinessObjectModel)
				return new BusinessObjectModelPropertySource(
						(AbstractModelElement) editPart);
			if (editPart instanceof DiagramElement)
				return new DiagramElementSource((AbstractModelElement) editPart);
			if (editPart instanceof AbstractModelElement)
				return new AbstractModelElementPropertySource(
						(AbstractModelElement) editPart);

		}
		return null;
	}
}
