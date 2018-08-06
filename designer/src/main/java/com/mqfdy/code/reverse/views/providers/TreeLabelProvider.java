package com.mqfdy.code.reverse.views.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;

public class TreeLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof TreeNode) {
			TreeNode node = (TreeNode) element;
			Image image = null;
			ImageManager manager = ImageManager.getInstance();
			if(columnIndex == 0) {
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
				case IViewConstant.TYPE_RELATION:
					image = manager.getImage(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT);
					break;
				case IViewConstant.TYPE_BUSINESSCLASS_ERROR:
					image = manager.getImage(ImageKeys.IMG_BUSINESSCLASS_ERROR);
					break;
				case IViewConstant.TYPE_OPERATION_ERROR:
					image = manager.getImage(ImageKeys.IMG_OPERATION_ERROR);
					break;
				case IViewConstant.TYPE_PRIMARYKEY_ERROR:
					image = manager.getImage(ImageKeys.IMG_PRIMARYKEY_ERROR);
					break;
				case IViewConstant.TYPE_BUSINESSCLASS_CONFLICT:
					image = manager.getImage(ImageKeys.IMG_BUSINESSCLASS_CONFLICT);
					break;
				default:
					break;
				}
			}
			
			return image;
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof TreeNode) {
			TreeNode node = (TreeNode) element;
			switch (columnIndex) {
			case 0:
				return node.getDisplayName();
			case 1:
				Object object = node.getBackup();
				if(object instanceof Table) {
					Table table = (Table) object;
					return table.getComment();
				} else if(object instanceof Column) {
					Column column = (Column) object;
					return column.getComment();
				} else if(object instanceof Association) {
					Association association = (Association) object;
					return association.getDisplayName();
				}
				break;
			default:
				break;
			}
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
