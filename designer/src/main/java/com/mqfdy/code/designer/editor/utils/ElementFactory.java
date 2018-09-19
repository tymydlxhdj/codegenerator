package com.mqfdy.code.designer.editor.utils;

import org.eclipse.gef.requests.CreationFactory;

// TODO: Auto-generated Javadoc
/**
 * 对应选项板上组件的工厂类.
 *
 * @author mqfdy
 */
public class ElementFactory implements CreationFactory {

	/** The template. */
	private Object template;

	/**
	 * Instantiates a new element factory.
	 *
	 * @param template
	 *            the template
	 */
	public ElementFactory(Object template) {
		this.template = template;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getNewObject() {
		try {
			return ((Class) template).newInstance();
		} catch (Exception e) {
			Logger.log(e);
			return null;
		}
	}

	/**
	 * @return
	 */
	public Object getObjectType() {
		return template;
	}
}
