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

// TODO: Auto-generated Javadoc
/**
 * The Class ModelResourceContentProvider.
 *
 * @author mqfdy
 */
public class ModelResourceContentProvider implements ITreeContentProvider {

	
	/**
	 * Gets the children.
	 *
	 * @author mqfdy
	 * @param parentElement
	 *            the parent element
	 * @return the children
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the parent.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the parent
	 * @Date 2018-09-03 09:00
	 */
	public Object getParent(Object element) {
		AbstractModelElement modelElement = (AbstractModelElement) element;
		return modelElement.getParent();
	}

	/**
	 * Checks for children.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasChildren(Object element) {
		if (getChildren(element) != null && getChildren(element).length > 0) {
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/**
	 * Input changed.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param oldInput
	 *            the old input
	 * @param newInput
	 *            the new input
	 * @Date 2018-09-03 09:00
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the elements.
	 *
	 * @author mqfdy
	 * @param inputElement
	 *            the input element
	 * @return the elements
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			List<BusinessObjectModel> boms = (List<BusinessObjectModel>) inputElement;
			return boms.toArray();
		}
		return null;

	}

}
