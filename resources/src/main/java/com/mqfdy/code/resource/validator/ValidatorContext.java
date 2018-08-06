package com.mqfdy.code.resource.validator;

/**
 * 模型校验接口，输出控制台
 * 
 * @author mqfdy
 * 
 */
public interface ValidatorContext {
	final static String ERROR = "error";
	final static String INFO = "info";

	/**
	 * 输出日志
	 * 
	 * @param mesg
	 * @param type
	 */
	void printToConsole(String msg, String type);
}
