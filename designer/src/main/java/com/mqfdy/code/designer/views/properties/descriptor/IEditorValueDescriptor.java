package com.mqfdy.code.designer.views.properties.descriptor;

/**
 * 编辑框接口
 * 
 * @author mqfdy
 * 
 */
public interface IEditorValueDescriptor<T> {

	T getEditorValue(T value);

	T getDefaultValue();
}
