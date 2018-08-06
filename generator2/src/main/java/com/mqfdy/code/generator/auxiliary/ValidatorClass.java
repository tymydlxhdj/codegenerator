package com.mqfdy.code.generator.auxiliary;

import java.util.Map;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.ParamType;
/**
 * 校验器类
 * @author mqfdy
 */ 
public class ValidatorClass extends AbstractBusinessClass {
	
	private String validatorClassName = "CustormValidator";
	public ValidatorClass(ClassParam param,Validator validator) {
		super(param);
		Map<String,String> params = validator.getValidatorParams();
		if(params != null && !params.isEmpty()){
			this.validatorClassName = params.get(ParamType.customValidatorClassName.getValue());
		}
		map.put("validatorClassName", validatorClassName);
	}

	@Override
	public void initPackage() {
		packageStr = packagePrefix + ".validator;";
	}

	@Override
	public void initFields() {
		
	}

	@Override
	public void initImports() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initMethods() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getCustomMethod(BusinessOperation bop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putToVelocityMap() {
		map.put("packageStr", packageStr);
	}

}
