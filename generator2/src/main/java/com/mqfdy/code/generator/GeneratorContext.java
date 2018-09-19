package com.mqfdy.code.generator;

// TODO: Auto-generated Javadoc
/**
 * 代码生成器的消费者上下文环境.
 *
 * @author mqfdy
 */
public interface GeneratorContext {
	
	/** The Constant ERROR. */
	final static String ERROR = "error";
	
	/** The Constant INFO. */
	final static String INFO = "info";

	/**
	 * 代码生成过程中输出日志.
	 *
	 * @author mqfdy
	 * @param mesg
	 *            the mesg
	 * @param type
	 *            the type
	 * @Date 2018-9-3 11:38:39
	 */
	void print(String mesg, String type);

	/**
	 * 代码生成过程中消息确认.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public int confirm(String message);
}
