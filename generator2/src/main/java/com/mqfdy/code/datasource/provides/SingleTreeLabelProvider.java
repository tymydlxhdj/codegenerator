package com.mqfdy.code.datasource.provides;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.datasource.constant.IViewConstant;
import com.mqfdy.code.datasource.mapping.TreeNode;
import com.mqfdy.code.datasource.model.ImageKeys;
import com.mqfdy.code.datasource.model.ImageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class SingleTreeLabelProvider.
 *
 * @author mqfdy
 */
public class SingleTreeLabelProvider extends LabelProvider implements ILabelProvider {

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 * @param element
	 * @return SingleTreeLabelProvider
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof TreeNode) {
			TreeNode node = (TreeNode) element;
			Image image = null;
			ImageManager manager = ImageManager.getInstance();
					
			switch (node.getType()) {
			case IViewConstant.TYPE_COLUMNS_FOLDER:
				image = manager.getImage(ImageKeys.IMG_TREE_FOLDER);
				break;
			case IViewConstant.TYPE_CONSTRAINT_FOLDER:
				image = manager.getImage(ImageKeys.IMG_TREE_FOLDER);
				break;
			case IViewConstant.TYPE_COLUMNS_NODE:
				image = manager.getImage(ImageKeys.IMG_MODEL_TYPE_PROPERTY);
				break;
			case IViewConstant.TYPE_DATASOURCE:
				image = manager.getImage(ImageKeys.IMG_DATASOURCE);
				break;
			case IViewConstant.TYPE_PRIMARY_KEY:
				image = manager.getImage(ImageKeys.IMG_PROPERTY_PRIMARYKEY);
				break;
			case IViewConstant.TYPE_REFERENCE_KEY:
				image = manager.getImage(ImageKeys.IMG_REFERENCE_KEY);
				break;
			case IViewConstant.TYPE_REPOSITORY:
				image = manager.getImage(ImageKeys.IMG_REPOSITORY);
				break;
			case IViewConstant.TYPE_SCHEMAS:
				image = manager.getImage(ImageKeys.IMG_TREE_FOLDER);
				break;
			case IViewConstant.TYPE_TABLE_FOLDER:
				image = manager.getImage(ImageKeys.IMG_TREE_FOLDER);
				break;
			case IViewConstant.TYPE_USER:
				image = manager.getImage(ImageKeys.IMG_USER);
				break;
			case IViewConstant.TYPE_VIEWS_FOLDER:
				image = manager.getImage(ImageKeys.IMG_TREE_FOLDER);
				break;
			case IViewConstant.TYPE_VIEWS_NODE:
				image = manager.getImage(ImageKeys.IMG_VIEW);
				break;
			case IViewConstant.TYPE_SEQUENCES_FOLDER:
				image = manager.getImage(ImageKeys.IMG_TREE_FOLDER);
				break;
			case IViewConstant.TYPE_SEQUENCES_NODE:
				image = manager.getImage(ImageKeys.IMG_SEQUENCES);
				break;
			case IViewConstant.TYPE_TABLE_NODE:
				image = manager.getImage(ImageKeys.IMG_TABLE);
				break;
			case IViewConstant.TYPE_PROJECTS:
				image = manager.getImage(ImageKeys.IMG_PROJECTS);
				break;
			case IViewConstant.TYPE_DIRECTORY:
				image = manager.getImage(ImageKeys.IMG_TREE_FOLDER);
				break;
			case IViewConstant.TYPE_OM_FILE:
				image = manager.getImage(ImageKeys.IMG_OM);
				break;
			case IViewConstant.TYPE_BUSINESSCLASS:
				image = manager.getImage(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS);
				break;
			case IViewConstant.TYPE_PROPERTY_NODE:
				image = manager.getImage(ImageKeys.IMG_MODEL_TYPE_OPERATION);
				break;
			case IViewConstant.TYPE_PACKAGE:
				image = manager.getImage(ImageKeys.IMG_PACKAGE);
				break;
			case IViewConstant.TYPE_PRIMARY_COLUMN:
				image = manager.getImage(ImageKeys.IMG_PROPERTY_PRIMARYKEY);
				break;
			case IViewConstant.TYPE_REFERENCE_COLUMN:
				image = manager.getImage(ImageKeys.IMG_REFERENCE_KEY);
				break;
			case IViewConstant.TYPE_PRIMARY_PROPERTY:
				image = manager.getImage(ImageKeys.IMG_PROPERTY_PRIMARYKEY);
				break;
			default:
				break;
			}
			
			return image;
		}
		return null;
	}
	
	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 * @param element
	 * @return SingleTreeLabelProvider
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof TreeNode) {
			TreeNode node = (TreeNode) element;
			return node.getDisplayName();
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose() SingleTreeLabelProvider
	 */
	public void dispose() {
	}

	/**
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 * @param element
	 * @param property
	 * @return SingleTreeLabelProvider
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 * @param listener SingleTreeLabelProvider
	 */
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 * @param listener SingleTreeLabelProvider
	 */
	public void removeListener(ILabelProviderListener listener) {
	}
}
