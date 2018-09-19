package com.mqfdy.code.datasource.provides;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.scence.IScenceType;

// TODO: Auto-generated Javadoc
/**
 * 表格字段LabelProvider.
 *
 * @author mqfdy
 */
public class TableFieldLabelProvider extends LabelProvider implements
ITableLabelProvider{
	
	/** The scence type. */
	private int scenceType;
	
	/** 字段是否使用驼峰格式. */
	private boolean isChangeStrategy;
	
	/**
	 * Instantiates a new table field label provider.
	 */
	public TableFieldLabelProvider(){}
	
	/**
	 * Instantiates a new table field label provider.
	 *
	 * @param scenceType
	 *            the scence type
	 */
	public TableFieldLabelProvider(int scenceType){
		this.scenceType = scenceType;
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 * @param element
	 * @param columnIndex
	 * @return TableFieldLabelProvider
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}


	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 * @param element
	 * @param columnIndex
	 * @return TableFieldLabelProvider
	 */
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

	/**
	 * Checks if is change strategy.
	 *
	 * @author mqfdy
	 * @return true, if is change strategy
	 * @Date 2018-09-03 09:00
	 */
	public boolean isChangeStrategy() {
		return isChangeStrategy;
	}

	/**
	 * Sets the change strategy.
	 *
	 * @author mqfdy
	 * @param isChangeStrategy
	 *            the new change strategy
	 * @Date 2018-09-03 09:00
	 */
	public void setChangeStrategy(boolean isChangeStrategy) {
		this.isChangeStrategy = isChangeStrategy;
	}

	/**
	 * Gets the scence type.
	 *
	 * @author mqfdy
	 * @return the scence type
	 * @Date 2018-09-03 09:00
	 */
	public int getScenceType() {
		return scenceType;
	}

	/**
	 * Sets the scence type.
	 *
	 * @author mqfdy
	 * @param scenceType
	 *            the new scence type
	 * @Date 2018-09-03 09:00
	 */
	public void setScenceType(int scenceType) {
		this.scenceType = scenceType;
	}
}
