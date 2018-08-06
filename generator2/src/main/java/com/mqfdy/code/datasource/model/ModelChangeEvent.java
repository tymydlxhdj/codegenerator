package com.mqfdy.code.datasource.model;

import java.util.EventObject;

/**
 * 
 * @author mqfdy
 *
 */
public class ModelChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
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
	 * @param type
	 *            the type of modification (ADDED, REMOVED, CHANGED) this event
	 *            contains
	 */
	public ModelChangeEvent(IModelElement element, int eventType) {
		super(element);
		this.eventType = eventType;
	}

	/**
	 * Returns the modified element.
	 */
	public IModelElement getElement() {
		return (IModelElement) getSource();
	}

	// /**
	// * Returns the type of modification.
	// */
	// public Type getType() {
	// return type;
	// }
	public int getType() {
		return eventType;
	}

	@Override
	public String toString() {
		StringBuffer text = new StringBuffer("Model element '");
		text.append(getElement().getElementName()).append("' (");
		text.append(getElement().getClass().getName()).append(") ");
		text.append(getType());
		return text.toString();
	}
}
