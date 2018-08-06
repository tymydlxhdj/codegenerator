package com.mqfdy.code.designer.views.modelresource.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Annotation;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.SolidifyPackage;

public class ModelResourceContentProvider implements ITreeContentProvider {

	
	@SuppressWarnings({ "rawtypes" })
	public Object[] getChildren(Object parentElement) {
		if((parentElement instanceof SolidifyPackage)&&
				"关联关系".equals(((SolidifyPackage)parentElement).getDisplayName())&&
			"2".equals(((SolidifyPackage)parentElement).getStereotype())){
			return null;
		}
		List<?> children = new ArrayList();
		List<Object> remove = new ArrayList();
		AbstractModelElement modelElement = (AbstractModelElement) parentElement;
		children = modelElement.getChildren();
		// 屏蔽注释和连线，outline视图不显示
		for(Object ab : children){
			if(ab instanceof AbstractModelElement && 
					SolidifyPackage.SOLIDIFY_PACKAGE_ANNOTATION.equals(((AbstractModelElement) ab).getName())){
				remove.add(ab);
			}
		}
		children.removeAll(remove);
		for(Object ab : children){
			if(ab instanceof AbstractModelElement && 
					SolidifyPackage.SOLIDIFY_PACKAGE_LINK.equals(((AbstractModelElement) ab).getName())){
				remove.add(ab);
			}
		}
		children.removeAll(remove);
		if (children.size() > 0) {
			return children.toArray();
		}

		return null;
	}

	public Object getParent(Object element) {
		AbstractModelElement modelElement = (AbstractModelElement) element;
		return modelElement.getParent();
	}

	public boolean hasChildren(Object element) {
		if (getChildren(element) != null && getChildren(element).length > 0) {
			return true;
		}
		
		return false;
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			List<BusinessObjectModel> boms = (List<BusinessObjectModel>) inputElement;
			return boms.toArray();
		}
		return null;

	}

}
