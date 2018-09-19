package com.mqfdy.code.datasource.utils;

import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.ValidatorType;

// TODO: Auto-generated Javadoc
/**
 * 校验注解转字符串工具类.
 *
 * @author mqfdy
 */
public class ValidatorAnotationUtil {
	
	/**
	 * 获取校验注解字符串.
	 *
	 * @author mqfdy
	 * @param validator
	 *            the validator
	 * @return the validator anotation str
	 * @Date 2018-09-03 09:00
	 */
	public static String getValidatorAnotationStr(Validator validator) {
		StringBuilder sbValidatorAnotationStr = new StringBuilder();
		String validatorType = validator.getValidatorType();
		Property p = validator.getBelongProperty();
		String propertyDataType = p.getDataType();
		String anotation = "";
		if(ValidatorType.Nullable.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@NotBlank";
			if(!"String".equalsIgnoreCase(propertyDataType)){
			  anotation = "@NotNull";
			}
		}else if(ValidatorType.StringLength.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@Length";
		}else if(ValidatorType.CNString.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@ZNStr";
		}else if(ValidatorType.ENString.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@ENStr";
		}else if(ValidatorType.Email.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@Email";
		}else if(ValidatorType.URL.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@URL";
		}else if(ValidatorType.Integer.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@IntegerRange";
		}else if(ValidatorType.Long.getValue().equalsIgnoreCase(validatorType)){
	      anotation = "@LongRange";
	    }else if(ValidatorType.PastCode.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@Postcode";
		}else if(ValidatorType.Regular.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@Pattern";
		}else if(ValidatorType.DateTime.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@DateRange";
		}else if(ValidatorType.Number.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@Figure";
		}else if(ValidatorType.Custom.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@UserDefine";
		}/*else if(ValidatorType.Unique.getValue().equalsIgnoreCase(validatorType)){
			anotation = "@Unique";
		}*/
		constructCommonValidatorAnotationStr(validator, sbValidatorAnotationStr, anotation);
		return sbValidatorAnotationStr.toString();
	}

	/**
	 * Construct common validator anotation str.
	 *
	 * @author mqfdy
	 * @param validator
	 *            the validator
	 * @param sbValidatorAnotationStr
	 *            the sb validator anotation str
	 * @param anotation
	 *            the anotation
	 * @Date 2018-9-3 11:38:33
	 */
	private static void constructCommonValidatorAnotationStr(Validator validator, StringBuilder sbValidatorAnotationStr,
			String anotation) {
		sbValidatorAnotationStr.append(anotation);
		sbValidatorAnotationStr.append("(");
		if(validator.getValidatorParams().get("minDate") != null && validator.getValidatorParams().get("maxDate") != null){
			sbValidatorAnotationStr.append("min=\"");
			sbValidatorAnotationStr.append(validator.getValidatorParams().get("minDate"));
			sbValidatorAnotationStr.append("\",");
			sbValidatorAnotationStr.append("max=\"");
			sbValidatorAnotationStr.append(validator.getValidatorParams().get("maxDate"));
			sbValidatorAnotationStr.append("\",");
		}
		if(validator.getValidatorParams().get("minLength") != null && validator.getValidatorParams().get("maxLength") != null){
			String semi = "";
			if(ValidatorType.CNString.getValue().equalsIgnoreCase(validator.getValidatorType()) || 
					ValidatorType.ENString.getValue().equalsIgnoreCase(validator.getValidatorType())){
				semi = "\"";
			}
			sbValidatorAnotationStr.append("min=");
			sbValidatorAnotationStr.append(semi);
			sbValidatorAnotationStr.append(validator.getValidatorParams().get("minLength"));
			sbValidatorAnotationStr.append(semi);
			sbValidatorAnotationStr.append(",");
			sbValidatorAnotationStr.append("max=");
			sbValidatorAnotationStr.append(semi);
			sbValidatorAnotationStr.append(validator.getValidatorParams().get("maxLength"));
			sbValidatorAnotationStr.append(semi);
			sbValidatorAnotationStr.append(",");
		}
		if(validator.getValidatorParams().get("minValue") != null && validator.getValidatorParams().get("maxValue") != null){
			sbValidatorAnotationStr.append("min=\"");
			sbValidatorAnotationStr.append(validator.getValidatorParams().get("minValue"));
			sbValidatorAnotationStr.append("\",");
			sbValidatorAnotationStr.append("max=\"");
			sbValidatorAnotationStr.append(validator.getValidatorParams().get("maxValue"));
			sbValidatorAnotationStr.append("\",");
		}
		if(validator.getValidatorParams().get("expression") != null){
			sbValidatorAnotationStr.append("regexp=\"");
			String expression = validator.getValidatorParams().get("expression");
			expression = expression.replaceFirst("/", "").replaceFirst("/", "");
			expression = expression.replace("\\", "\\\\");
			sbValidatorAnotationStr.append(expression);
			sbValidatorAnotationStr.append("\",");
		}
		if(validator.getValidatorParams().get("customValidatorClassName") != null){
			sbValidatorAnotationStr.append("className=\"");
			Property belongProperty = validator.getBelongProperty();
			BusinessClass bc = (BusinessClass) belongProperty.getParent();
			sbValidatorAnotationStr.append(bc.getBelongPackage().getFullName());
			sbValidatorAnotationStr.append(".validator.");
			sbValidatorAnotationStr.append(validator.getValidatorParams().get("customValidatorClassName"));
			sbValidatorAnotationStr.append("\",");
		}
		sbValidatorAnotationStr.append("message=\"");
		sbValidatorAnotationStr.append(validator.getValidatorMessage());
		sbValidatorAnotationStr.append("\"");
		/*if(ValidatorType.Unique.getValue().equalsIgnoreCase(validator.getValidatorType())){
			sbValidatorAnotationStr.append(",");
			sbValidatorAnotationStr.append("primarykey=\"");
			Property belongProperty = validator.getBelongProperty();
			BusinessClass bc = (BusinessClass) belongProperty.getParent();
			sbValidatorAnotationStr.append(bc.getPkPropertyKey());
			sbValidatorAnotationStr.append("\"");
		}*/
		sbValidatorAnotationStr.append(")");
	}

}
