package com.mqfdy.code.designer.editor.actions.pages;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;

import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * TableViewer标签定义类 负责定义TableViewer中的列名称.
 *
 * @author mqfdy
 */
public class TableViewerProvider {
	
	/**
	 * Creates the viewer column.
	 *
	 * @author mqfdy
	 * @param tableViewer
	 *            the table viewer
	 * @Date 2018-09-03 09:00
	 */
	// private static String oldPageName;
	public static void createViewerColumn(final TableViewer tableViewer) {
		TableViewerColumn columnName = new TableViewerColumn(tableViewer,
				SWT.NONE);
		columnName.getColumn().setWidth(180);
		columnName.getColumn().setText("对象名称");

		columnName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((AbstractModelElement) element).getName();
			}
		});
		TableViewerColumn columnPageName = new TableViewerColumn(tableViewer,
				SWT.NONE);
		columnPageName.getColumn().setWidth(180);
		columnPageName.getColumn().setText("对象显示名称");

		columnPageName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((AbstractModelElement) element).getDisplayName();
			}
		});

		TableViewerColumn columnTypeName = new TableViewerColumn(tableViewer,
				SWT.NONE);
		columnTypeName.getColumn().setWidth(100);
		columnTypeName.getColumn().setText("对象类型");

		columnTypeName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// IViewColumnModel pers = (IViewColumnModel) element;
				// if (pers.getPageName().trim() == null
				// || pers.getPageName().trim().equals("")) {
				// pers.setPageName(per.getDBName());//为防止字段的显示名称为空值
				// tableViewer.refresh();
				// } else {
				return ((AbstractModelElement) element).getDisplayType();
				// }
				// return pers.getPageName();
			}
		});
	}

}
