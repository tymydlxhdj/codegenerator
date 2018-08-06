package com.mqfdy.code.springboot.core.validators;

import com.mqfdy.code.springboot.core.datasource.SampleProject;
import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.util.expression.ValueListener;

/**
 * @author lenovo
 */
public class SampleProjectValidator extends Validator implements ValueListener<SampleProject> {
	
	private LiveExpression<SampleProject> sampleProjectField;
	private String elementLabel;
	
	public SampleProjectValidator(String elementLabel, LiveExpression<SampleProject> sampleProjectField) {
		this.elementLabel = elementLabel;
		this.sampleProjectField = sampleProjectField;
		this.sampleProjectField.addListener(this);
	}

	@Override
	protected ValidationResult compute() {
		SampleProject sampleProject = sampleProjectField.getValue();
		if (sampleProject==null) {
			return ValidationResult.error("'"+elementLabel+"' is undefined. Please select one.");
		}
		return ValidationResult.OK;
	}

	public void gotValue(LiveExpression<SampleProject> exp, SampleProject value) {
		refresh();
	}

}
