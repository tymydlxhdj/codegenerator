package com.mqfdy.code.resource.validator;

// TODO: Auto-generated Javadoc
/**
 * 模型校验接口，输出控制台.
 *
 * @author mqfdy
 */
public interface ValidatorContext {
	
	/** The Constant ERROR. */
	final static String ERROR = "error";
	
	/** The Constant INFO. */
	final static String INFO = "info";

	/**
	 * 输出日志.
	 *
	 * @author mqfdy
	 * @param msg
	 *            the msg
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	void printToConsole(String msg, String type);
}
