package com.mqfdy.code.datasource.provides;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.scence.IScenceType;

/**
 * 表格字段LabelProvider
 * @author mqfdy
 *
 */
public class TableFieldLabelProvider extends LabelProvider implements
ITableLabelProvider{
	
	private int scenceType;
	
	/**
	 * 字段是否使用驼峰格式
	 */
	private boolean isChangeStrategy;
	
	public TableFieldLabelProvider(){}
	
	public TableFieldLabelProvider(int scenceType){
		this.scenceType = scenceType;
	}
	
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}


	public String getColumnText(Object element, int columnIndex) {
		
		Column column = (Column) element;
		switch (columnIndex) {
		case 1:
			if(IScenceType.TREE_SCENE_TYPE == scenceType){
				break;
			}
			return column.getName();
		case 2:
			if(IScenceType.TREE_SCENE_TYPE == scenceType){
				return column.getName();
			}
			return column.getSqlType();
		case 3:
			if(IScenceType.TREE_SCENE_TYPE == scenceType){
				return column.getSqlType();
			}
			return column.getJavaName();
		case 4:
			if(IScenceType.TREE_SCENE_TYPE == scenceType){
				return column.getJavaName();
			}
			return column.getJavaType();
		case 5:
			if(IScenceType.TREE_SCENE_TYPE == scenceType){
				return column.getJavaType();
			}
			return column.getComment();
		case 6:
			return column.getComment();
		default:
			break;
		}
		return null;
	}

	public boolean isChangeStrategy() {
		return isChangeStrategy;
	}

	public void setChangeStrategy(boolean isChangeStrategy) {
		this.isChangeStrategy = isChangeStrategy;
	}

	public int getScenceType() {
		return scenceType;
	}

	public void setScenceType(int scenceType) {
		this.scenceType = scenceType;
	}
}
