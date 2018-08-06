package com.mqfdy.code.springboot.core.validators;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;

/**
 * @author lenovo
 */
public abstract class Validator extends LiveExpression<ValidationResult> {

	public Validator() {
		super(ValidationResult.OK);
	}

}
