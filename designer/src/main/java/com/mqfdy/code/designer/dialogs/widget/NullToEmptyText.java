package com.mqfdy.code.designer.dialogs.widget;

import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * Text设值时把null转换为空字符串.
 *
 * @author mqfdy
 */
public class NullToEmptyText extends org.eclipse.swt.widgets.Text {

	/**
	 * Instantiates a new null to empty text.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public NullToEmptyText(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Sets the text.
	 *
	 * @author mqfdy
	 * @param string
	 *            the new text
	 * @Date 2018-09-03 09:00
	 */
	public void setText(String string) {
		string = StringUtil.convertNull2EmptyStr(string);
		super.setText(string);
	}

	/**
	 * 
	 */
	protected void checkSubclass() {

	}

}
