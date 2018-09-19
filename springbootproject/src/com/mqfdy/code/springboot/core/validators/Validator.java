package com.mqfdy.code.springboot.core.validators;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class Validator.
 *
 * @author lenovo
 */
public abstract class Validator extends LiveExpression<ValidationResult> {

	/**
	 * Instantiates a new validator.
	 */
	public Validator() {
		super(ValidationResult.OK);
	}

}
