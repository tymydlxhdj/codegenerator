package com.mqfdy.code.generator.exception;

/**
 * @title:自定义运行时异常
 * @description:
 * @author mqfdy
 * @date 2017-06-23
 */
public class CodeGeneratorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1165557335897086205L;

	public CodeGeneratorException(String message) {
		super(message);
	}

	public CodeGeneratorException(String message, Exception e) {
		super(message, e);
	}
}
