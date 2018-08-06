package com.mqfdy.code.generator;

/**
 * 代码生成器的消费者上下文环境
 * 
 * @author mqfdy
 * 
 */
public interface GeneratorContext {
	final static String ERROR = "error";
	final static String INFO = "info";

	/**
	 * 代码生成过程中输出日志
	 * 
	 * @param mesg
	 * @param type
	 */
	void print(String mesg, String type);

	/**
	 * 代码生成过程中消息确认
	 * 
	 * @param message
	 * @return
	 */
	public int confirm(String message);
}
