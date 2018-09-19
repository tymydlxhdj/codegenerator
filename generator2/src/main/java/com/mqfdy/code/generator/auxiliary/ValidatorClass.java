package com.mqfdy.code.generator.auxiliary;

import java.util.Map;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.ParamType;
// TODO: Auto-generated Javadoc

/**
 * 校验器类.
 *
 * @author mqfdy
 */ 
public class ValidatorClass extends AbstractBusinessClass {
	
	/** The validator class name. */
	private String validatorClassName = "CustormValidator";
	
	/**
	 * Instantiates a new validator class.
	 *
	 * @param param
	 *            the param
	 * @param validator
	 *            the validator
	 */
	public ValidatorClass(ClassParam param,Validator validator) {
		super(param);
		Map<String,String> params = validator.getValidatorParams();
		if(params != null && !params.isEmpty()){
			this.validatorClassName = params.get(ParamType.customValidatorClassName.getValue());
		}
		map.put("validatorClassName", validatorClassName);
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initPackage() ValidatorClass
	 */
	@Override
	public void initPackage() {
		packageStr = packagePrefix + ".validator;";
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initFields() ValidatorClass
	 */
	@Override
	public void initFields() {
		
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initImports() ValidatorClass
	 */
	@Override
	public void initImports() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initMethods() ValidatorClass
	 */
	@Override
	public void initMethods() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#getCustomMethod(com.mqfdy.code.model.BusinessOperation)
	 * @param bop
	 * @return ValidatorClass
	 */
	@Override
	protected String getCustomMethod(BusinessOperation bop) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#putToVelocityMap() ValidatorClass
	 */
	@Override
	public void putToVelocityMap() {
		map.put("packageStr", packageStr);
	}

}
