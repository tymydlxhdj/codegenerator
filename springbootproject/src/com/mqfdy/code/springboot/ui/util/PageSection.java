package com.mqfdy.code.springboot.ui.util;

import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.validators.ValidationResult;
import com.mqfdy.code.springboot.ui.IPageWithSections;


/**
 * @author lenovo
 */
public abstract class PageSection {
	
	protected final IPageWithSections owner;
	
	protected PageSection(IPageWithSections owner) {
		this.owner = owner;
	}

	public static final LiveExpression<ValidationResult> OK_VALIDATOR = LiveExpression.constant(ValidationResult.OK);
	public abstract LiveExpression<ValidationResult> getValidator();
	public abstract void createContents(Composite page);

}
