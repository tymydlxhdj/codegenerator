package com.mqfdy.code.datasource.provides;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.mqfdy.code.datasource.mapping.Column;

// TODO: Auto-generated Javadoc
/**
 * 表字段内容提供.
 *
 * @author mqfdy
 */
public class TableFieldContentProvider implements IStructuredContentProvider {

	/**
	 * 获取表格显示内容对象数组.
	 *
	 * @author mqfdy
	 * @param inputElement
	 *            输入List对象
	 * @return the elements
	 * @Date 2018-9-3 11:38:37
	 */
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		
		if (inputElement instanceof List) {
			return ((List<Column>) inputElement).toArray();
		} else {
			return null;
		}
	}

}
