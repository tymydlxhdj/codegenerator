package com.mqfdy.code.designer.editor.utils;

import org.eclipse.gef.requests.CreationFactory;

/**
 * 对应选项板上组件的工厂类
 * 
 * @author mqfdy
 */
public class ElementFactory implements CreationFactory {

	private Object template;

	public ElementFactory(Object template) {
		this.template = template;
	}

	@SuppressWarnings("unchecked")
	public Object getNewObject() {
		try {
			return ((Class) template).newInstance();
		} catch (Exception e) {
			Logger.log(e);
			return null;
		}
	}

	public Object getObjectType() {
		return template;
	}
}
