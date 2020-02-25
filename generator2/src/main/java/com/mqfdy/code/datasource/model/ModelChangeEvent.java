package com.mqfdy.code.datasource.model;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelChangeEvent.
 *
 * @author mqfdy
 */
public class ModelChangeEvent extends EventObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The event type. */
	private int eventType;

	// public enum Type {
	// ADDED, REMOVED, CHANGED
	// };
	//
	// private Type type;

	/**
	 * Creates an new element change event.
	 *
	 * @param element
	 *            the changed model element
	 * @param eventType
	 *            the event type
	 */
	public ModelChangeEvent(IModelElement element, int eventType) {
		super(element);
		this.eventType = eventType;
	}

	/**
	 * Returns the modified element.
	 *
	 * @author mqfdy
	 * @return the element
	 * @Date 2018-09-03 09:00
	 */
	public IModelElement getElement() {
		return (IModelElement) getSource();
	}

	// /**
	// * Returns the type of modification.
	// */
	// public Type getType() {
	// return type;
	/**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	// }
	public int getType() {
		return eventType;
	}

	/**
	 * To string.
	 *
	 * @return ModelChangeEvent
	 * @see java.util.EventObject#toString()
	 */
	@Override
	public String toString() {
		StringBuffer text = new StringBuffer("Model element '");
		text.append(getElement().getElementName()).append("' (");
		text.append(getElement().getClass().getName()).append(") ");
		text.append(getType());
		return text.toString();
	}
}
