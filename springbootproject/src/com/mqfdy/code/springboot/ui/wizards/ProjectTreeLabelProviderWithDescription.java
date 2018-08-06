package com.mqfdy.code.springboot.ui.wizards;

import java.io.File;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A label provider for a two column tree viewer that has a Springboot project tree in the first column and
 * descriptions in the second column.
 *  
 * @author lenovo
 */
public class ProjectTreeLabelProviderWithDescription extends BaseLabelProvider implements ILabelProvider, IBaseLabelProvider, ITableLabelProvider {
	
	private ProjectTreeLabelProvider wrapped;

	/**
	 * @param useTransparantIcons Determines whether transparant icon will be used when non-leaf project is already imported in the workspace.
	 */
	public ProjectTreeLabelProviderWithDescription(boolean useTransparantIcons) {
		wrapped = new ProjectTreeLabelProvider(useTransparantIcons);
	}

	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex==0) {
			return wrapped.getImage(element);
		} else {
			return null;
		}
	}

	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex==0) {
			return wrapped.getText(element);
		} else {
			String desc = null;
			if(element instanceof File){
				File project = (File) element;
				desc = project.getName();
			}
			return desc == null ? "" : desc;
		}
	}

	@Override
	public void dispose() {
		wrapped.dispose();
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return wrapped.isLabelProperty(element, property);
	}

	public Image getImage(Object element) {
		return wrapped.getImage(element);
	}

	public String getText(Object element) {
		return wrapped.getText(element);
	}

}
