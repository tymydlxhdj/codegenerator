package com.mqfdy.code.designer.models;

import java.util.EventObject;

import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 业务模型事件.
 *
 * @author mqfdy
 */
public class BusinessModelEvent extends EventObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 事件类型-模型元素新增. */
	public static final int MODEL_ELEMENT_ADD = 0x11;

	/** 事件类型-模型元素修改. */
	public static final int MODEL_ELEMENT_UPDATE = 0x12;

	/** 事件类型-模型元素删除. */
	public static final int MODEL_ELEMENT_DELETE = 0x13;

	/** 事件类型-知识库模型新增. */
	public static final int REPOSITORY_MODEL_ADD = 0x14;

	/** 事件类型-模型元素保存. */
	public static final int MODEL_ELEMENT_SAVE = 0x15;

	/** 业务模型事件对象. */
	private AbstractModelElement modelElementChanged;

	/** The business model event type. */
	private final int businessModelEventType;

	/**
	 * Instantiates a new business model event.
	 *
	 * @param businessModelEventType
	 *            the business model event type
	 * @param modelElementChanged
	 *            the model element changed
	 */
	public BusinessModelEvent(int businessModelEventType,
			AbstractModelElement modelElementChanged) {
		super(modelElementChanged);
		this.businessModelEventType = businessModelEventType;
		this.modelElementChanged = modelElementChanged;
	}

	/**
	 * Instantiates a new business model event.
	 *
	 * @param businessModelEventType
	 *            the business model event type
	 * @param source
	 *            the source
	 * @param modelElementChanged
	 *            the model element changed
	 */
	public BusinessModelEvent(int businessModelEventType,
			BusinessModelManager source,
			AbstractModelElement modelElementChanged) {
		super(source);
		this.businessModelEventType = businessModelEventType;
		this.modelElementChanged = modelElementChanged;
	}

	/**
	 * Gets the event type.
	 *
	 * @author mqfdy
	 * @return the event type
	 * @Date 2018-09-03 09:00
	 */
	public int getEventType() {
		return this.businessModelEventType;
	}

	/**
	 * Gets the model element changed.
	 *
	 * @author mqfdy
	 * @return the model element changed
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getModelElementChanged() {
		return modelElementChanged;
	}
}
