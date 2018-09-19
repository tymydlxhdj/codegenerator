package com.mqfdy.code.springboot.core.validators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.util.expression.ValueListener;


// TODO: Auto-generated Javadoc
/**
 * A composite validator 'composes' the results of multiple validator by computing each
 * in order and only returning the worst result. If some results are equal in terms
 * of status priority then the first one is returned.
 * 
 * @author lenovo
 */
public class CompositeValidator extends LiveExpression<ValidationResult> implements ValueListener<ValidationResult> {

	/**
	 * Instantiates a new composite validator.
	 */
	public CompositeValidator() {
		super(ValidationResult.OK);
	}

	private List<LiveExpression<ValidationResult>> elements = new ArrayList<LiveExpression<ValidationResult>>();
	
	/**
	 * Adds the child.
	 *
	 * @author mqfdy
	 * @param child
	 *            the child
	 * @return the composite validator
	 * @Date 2018-09-03 09:00
	 */
	public CompositeValidator addChild(LiveExpression<ValidationResult> child) {
		elements.add(child);
		child.addListener(this);
		return this;
	}
	/**
	 * 向导页面项目名称输入和类型选择时调用
	 */
	@Override
	protected ValidationResult compute() {
		ValidationResult worst = ValidationResult.OK;
		for (LiveExpression<ValidationResult> v : elements) {
			ValidationResult r = v.getValue();
			if (r!=null && r.status>worst.status) {
				worst = r;
			}
			if (worst.status>=IStatus.ERROR) {
				//can't really get any worse than that
				return worst;
			}
		}
		return worst;
	}

	public void gotValue(LiveExpression<ValidationResult> exp, ValidationResult value) {
		refresh();
	}

}
