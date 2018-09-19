package com.mqfdy.code.designer.views.properties.descriptor;

// TODO: Auto-generated Javadoc
/**
 * 编辑框接口.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public interface IEditorValueDescriptor<T> {

	/**
	 * Gets the editor value.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the editor value
	 * @Date 2018-09-03 09:00
	 */
	T getEditorValue(T value);

	/**
	 * Gets the default value.
	 *
	 * @author mqfdy
	 * @return the default value
	 * @Date 2018-09-03 09:00
	 */
	T getDefaultValue();
}
