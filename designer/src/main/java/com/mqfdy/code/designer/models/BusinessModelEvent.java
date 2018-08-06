package com.mqfdy.code.designer.models;

import java.util.EventObject;

import com.mqfdy.code.model.AbstractModelElement;

/**
 * 业务模型事件
 */
public class BusinessModelEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/**
	 * 事件类型-模型元素新增
	 */
	public static final int MODEL_ELEMENT_ADD = 0x11;

	/**
	 * 事件类型-模型元素修改
	 */
	public static final int MODEL_ELEMENT_UPDATE = 0x12;

	/**
	 * 事件类型-模型元素删除
	 */
	public static final int MODEL_ELEMENT_DELETE = 0x13;

	/**
	 * 事件类型-知识库模型新增
	 */
	public static final int REPOSITORY_MODEL_ADD = 0x14;

	/**
	 * 事件类型-模型元素保存
	 */
	public static final int MODEL_ELEMENT_SAVE = 0x15;

	/**
	 * 业务模型事件对象
	 */
	private AbstractModelElement modelElementChanged;

	private final int businessModelEventType;

	public BusinessModelEvent(int businessModelEventType,
			AbstractModelElement modelElementChanged) {
		super(modelElementChanged);
		this.businessModelEventType = businessModelEventType;
		this.modelElementChanged = modelElementChanged;
	}

	public BusinessModelEvent(int businessModelEventType,
			BusinessModelManager source,
			AbstractModelElement modelElementChanged) {
		super(source);
		this.businessModelEventType = businessModelEventType;
		this.modelElementChanged = modelElementChanged;
	}

	public int getEventType() {
		return this.businessModelEventType;
	}

	public AbstractModelElement getModelElementChanged() {
		return modelElementChanged;
	}
}
