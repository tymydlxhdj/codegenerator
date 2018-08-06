package com.mqfdy.code.designer.views.modelresource.tree;

import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.mqfdy.code.model.AbstractModelElement;

/**
 * 资源数排序器
 * 
 * @author mqfdy
 * 
 */
public class ModelResourceTreeSorter extends ViewerSorter {

	/**
	 * 比较器
	 */
	private Comparator<AbstractModelElement> modelComparator = new Comparator<AbstractModelElement>() {

		public int compare(AbstractModelElement element1,
				AbstractModelElement element2) {

			return element1.compareTo(element2);
		}
	};

	public int compare(Viewer viewer, Object model1, Object model2) {
		AbstractModelElement element1 = (AbstractModelElement) model1;
		AbstractModelElement element2 = (AbstractModelElement) model2;
		return modelComparator.compare(element1, element2);
	}
}
