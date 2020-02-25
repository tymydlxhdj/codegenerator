package com.mqfdy.code.datasource.model;

import org.eclipse.core.runtime.IProgressMonitor;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractModelElement.
 *
 * @author mqfdy
 */
public abstract class AbstractModelElement implements IModelElement {

	/** The parent. */
	private IModelElement parent;
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new abstract model element.
	 *
	 * @param parent
	 *            the parent
	 * @param name
	 *            the name
	 */
	protected AbstractModelElement(final IModelElement parent, final String name) {
		this.parent = parent;
		this.name = name;
	}

	/**
	 * Gets the element parent.
	 *
	 * @return AbstractModelElement
	 * @see com.mqfdy.code.datasource.model.IModelElement#getElementParent()
	 */
	public IModelElement getElementParent() {
		return parent;
	}

	/**
	 * Sets the element parent.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the new element parent
	 * @Date 2018-09-03 09:00
	 */
	public void setElementParent(final IModelElement parent) {
		this.parent = parent;
	}

	/**
	 * Gets the element children.
	 *
	 * @return AbstractModelElement
	 * @see com.mqfdy.code.datasource.model.IModelElement#getElementChildren()
	 */
	public IModelElement[] getElementChildren() {
		return NO_CHILDREN;
	}

	/**
	 * Gets the element name.
	 *
	 * @return AbstractModelElement
	 * @see com.mqfdy.code.datasource.model.IModelElement#getElementName()
	 */
	public String getElementName() {
		return name;
	}

	/**
	 * Sets the element name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new element name
	 * @Date 2018-09-03 09:00
	 */
	public void setElementName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the element ID.
	 *
	 * @return AbstractModelElement
	 * @see com.mqfdy.code.datasource.model.IModelElement#getElementID()
	 */
	public String getElementID() {
		final StringBuffer id = new StringBuffer();
		if (getElementParent() != null) {
			id.append(getElementParent().getElementID());
			id.append(ID_DELIMITER);
		}
		id.append(getElementType());
		id.append(ID_SEPARATOR);
		if (getElementName() != null) {
			id.append(getUniqueElementName());
		} else {
			id.append(super.hashCode());
		}
		return id.toString();
	}

	/**
	 * Gets the unique element name.
	 *
	 * @author mqfdy
	 * @return the unique element name
	 * @Date 2018-09-03 09:00
	 */
	protected String getUniqueElementName() {
		return getElementName();
	}

	/**
	 * Gets the element.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the element
	 * @Date 2018-09-03 09:00
	 */
	public IModelElement getElement(String id) {
		final int sepPos = id.indexOf(ID_SEPARATOR);
		if (sepPos > 0) {
			try {
				final int type = Integer.valueOf(id.substring(0, sepPos))
						.intValue();
				if (type == getElementType()) {
					final int delPos = id.indexOf(ID_DELIMITER);
					if (delPos > 0) {
						final String name = id.substring(sepPos + 1, delPos);
						if (name.equals(getUniqueElementName())) {

							id = id.substring(delPos + 1);
							for (final IModelElement child : getElementChildren()) {
								if (child instanceof AbstractModelElement) {
									final IModelElement element = ((AbstractModelElement) child)
											.getElement(id);
									if (element != null) {
										return element;
									}
								}
							}
						}
					} else {
						final String name = id.substring(sepPos + 1);
						if (name.equals(getUniqueElementName())) {
							return this;
						}
					}
				}
			} catch (final NumberFormatException e) {
				ModelPlugin.log(e);
			}
		}
		return null;
	}

	/**
	 * Accept.
	 *
	 * @param visitor
	 *            the visitor
	 * @param monitor
	 *            AbstractModelElement
	 * @see com.mqfdy.code.datasource.model.IModelElement#accept(com.mqfdy.code.datasource.model.IModelElementVisitor,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void accept(final IModelElementVisitor visitor,
			final IProgressMonitor monitor) {

		if (!monitor.isCanceled()) {
			if (visitor.visit(this, monitor)) {
				for (final IModelElement element : getElementChildren()) {
					element.accept(visitor, monitor);
				}
			}
		}
	}
}
