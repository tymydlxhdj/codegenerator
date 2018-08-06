package com.mqfdy.code.datasource.model;

import org.eclipse.core.runtime.IProgressMonitor;

public abstract class AbstractModelElement implements IModelElement {

	private IModelElement parent;
	private String name;

	protected AbstractModelElement(final IModelElement parent, final String name) {
		this.parent = parent;
		this.name = name;
	}

	public IModelElement getElementParent() {
		return parent;
	}

	public void setElementParent(final IModelElement parent) {
		this.parent = parent;
	}

	public IModelElement[] getElementChildren() {
		return NO_CHILDREN;
	}

	public String getElementName() {
		return name;
	}

	public void setElementName(final String name) {
		this.name = name;
	}

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

	protected String getUniqueElementName() {
		return getElementName();
	}

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
