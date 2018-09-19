package com.mqfdy.code.designer.editor;

import org.eclipse.wst.sse.ui.StructuredTextEditor;

// TODO: Auto-generated Javadoc
/**
 * 业务模型源文件编辑器(只读).
 *
 * @author mqfdy
 */
public class BusinessModelSourceEditor extends StructuredTextEditor {

	/** The is editable. */
	private boolean isEditable = false;

	/** The is read only. */
	private boolean isReadOnly = true;

	/**
	 * @return
	 */
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * @return
	 */
	public boolean isEditorInputReadOnly() {
		return isReadOnly;
	}
}
