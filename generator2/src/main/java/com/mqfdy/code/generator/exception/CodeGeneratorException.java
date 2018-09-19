package com.mqfdy.code.generator.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class CodeGeneratorException.
 *
 * @author mqfdy
 * @title:自定义运行时异常
 * @description:
 * @date 2017-06-23
 */
public class CodeGeneratorException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1165557335897086205L;

	/**
	 * Instantiates a new code generator exception.
	 *
	 * @param message
	 *            the message
	 */
	public CodeGeneratorException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new code generator exception.
	 *
	 * @param message
	 *            the message
	 * @param e
	 *            the e
	 */
	public CodeGeneratorException(String message, Exception e) {
		super(message, e);
	}
}
