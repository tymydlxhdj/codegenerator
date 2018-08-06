package com.mqfdy.code.designer.dialogs.widget;

import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * Text设值时把null转换为空字符串
 * 
 * @author mqfdy
 * 
 */
public class NullToEmptyText extends org.eclipse.swt.widgets.Text {

	public NullToEmptyText(Composite parent, int style) {
		super(parent, style);
	}

	public void setText(String string) {
		string = StringUtil.convertNull2EmptyStr(string);
		super.setText(string);
	}

	protected void checkSubclass() {

	}

}
