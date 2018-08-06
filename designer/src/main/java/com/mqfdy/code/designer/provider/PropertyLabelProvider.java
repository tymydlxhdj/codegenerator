package com.mqfdy.code.designer.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.StringUtil;

public class PropertyLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof PKProperty
					&& ((PKProperty) element).isPrimaryKey()) {
				return ImageManager.getInstance().getImage(
						ImageKeys.IMG_PROPERTY_PRIMARYKEY);
			}
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Property) {
			Property property = (Property) element;
			switch (columnIndex) {
			case 0:
				return property.getOrderNum() + "";
			case 10:
				if (property.getGroup() != null) {
					return property.getGroup().getDisplayName();
				} else {
					return "";
				}
			case 1:
				return property.getName();
			case 2:
				return property.getDisplayName();
			case 3:
				return property.getDataType();
			case 4:
				if (property.getEditor() != null) {
					EditorType type = EditorType.getEditorType(property
							.getEditor().getEditorType());
					if (type != null) {
						return type.getDisplayValue();
					} else {
						return property.getEditor().getEditorType();
					}
				} else {
					return "";
				}
			}
		}

		if (element instanceof PersistenceProperty) {
			PersistenceProperty persistenceProperty = (PersistenceProperty) element;
			switch (columnIndex) {
			case 5:
				return persistenceProperty.getdBColumnName();
			case 6:
				return StringUtil.boolean2String(persistenceProperty
						.isPrimaryKey());
			case 7:
				return StringUtil
						.boolean2String(persistenceProperty.isUnique());
			case 8:
				return StringUtil.boolean2String(persistenceProperty
						.isNullable());
			case 9:
				return StringUtil.boolean2String(persistenceProperty
						.isReadOnly());
			case 11:
				return persistenceProperty.getDefaultValue();
			case 12:
				return StringUtil.boolean2String(persistenceProperty
						.isIndexedColumn());
			}
		}

		return "";
	}

}
