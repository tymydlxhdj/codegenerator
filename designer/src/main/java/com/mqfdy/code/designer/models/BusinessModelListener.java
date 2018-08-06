package com.mqfdy.code.designer.models;

import com.mqfdy.code.model.AbstractModelElement;

public interface BusinessModelListener {

	public void modelElementAdd(AbstractModelElement element);

	public void modelElementUpdate(AbstractModelElement element);

	public void modelElementDelete(AbstractModelElement element);

	public void modelSave(AbstractModelElement element);

	public void repositoryModelAdd(AbstractModelElement element);
}
