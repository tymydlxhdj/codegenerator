package com.mqfdy.code.springboot.ui.util;

import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.validators.ValidationResult;
import com.mqfdy.code.springboot.ui.IPageWithSections;


// TODO: Auto-generated Javadoc
/**
 * The Class PageSection.
 *
 * @author lenovo
 */
public abstract class PageSection {
	
	/** The owner. */
	protected final IPageWithSections owner;
	
	/**
	 * Instantiates a new page section.
	 *
	 * @param owner
	 *            the owner
	 */
	protected PageSection(IPageWithSections owner) {
		this.owner = owner;
	}

	/** The Constant OK_VALIDATOR. */
	public static final LiveExpression<ValidationResult> OK_VALIDATOR = LiveExpression.constant(ValidationResult.OK);
	
	/**
	 * Gets the validator.
	 *
	 * @author mqfdy
	 * @return the validator
	 * @Date 2018-09-03 09:00
	 */
	public abstract LiveExpression<ValidationResult> getValidator();
	
	/**
	 * Creates the contents.
	 *
	 * @author mqfdy
	 * @param page
	 *            the page
	 * @Date 2018-09-03 09:00
	 */
	public abstract void createContents(Composite page);

}
