package com.mqfdy.code.reverse.views.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;

public class SingleTreeLabelProvider extends LabelProvider implements ILabelProvider {

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
	
	@Override
	public String getText(Object element) {
		if (element instanceof TreeNode) {
			TreeNode node = (TreeNode) element;
			return node.getDisplayName();
		}
		return null;
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void removeListener(ILabelProviderListener listener) {
	}
}