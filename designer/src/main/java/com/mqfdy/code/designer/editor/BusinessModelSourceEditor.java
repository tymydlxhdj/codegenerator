package com.mqfdy.code.designer.editor;

import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * 业务模型源文件编辑器(只读)
 * 
 * @author mqfdy
 * 
 */
public class BusinessModelSourceEditor extends StructuredTextEditor {

	private boolean isEditable = false;

	private boolean isReadOnly = true;

	public boolean isEditable() {
		return isEditable;
	}

	public boolean isEditorInputReadOnly() {
		return isReadOnly;
	}
}
