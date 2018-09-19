package com.mqfdy.code.reverse.views.beans;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecialTable.
 *
 * @author mqfdy
 */
public class SpecialTable {

	/** The name. */
	private String name;		//表名		
	
	/** The problem type. */
	private int problemType;	//问题类型    IViewConstant
	
	/** The desc. */
	private String desc;		//问题描述
	
	/** The handle text. */
	private String handleText;	//处理方式（文本）
	
	/** The prompt. */
	private String prompt;		//提示
	
	/** The is handle. */
	private boolean isHandle;	//处理类型       true: 自动处理, false: 忽略
	
	/**
	 * Gets the name.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the desc.
	 *
	 * @author mqfdy
	 * @return the desc
	 * @Date 2018-09-03 09:00
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Sets the desc.
	 *
	 * @author mqfdy
	 * @param desc
	 *            the new desc
	 * @Date 2018-09-03 09:00
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Gets the problem type.
	 *
	 * @author mqfdy
	 * @return the problem type
	 * @Date 2018-09-03 09:00
	 */
	public int getProblemType() {
		return problemType;
	}
	
	/**
	 * Sets the problem type.
	 *
	 * @author mqfdy
	 * @param problemType
	 *            the new problem type
	 * @Date 2018-09-03 09:00
	 */
	public void setProblemType(int problemType) {
		this.problemType = problemType;
	}
	
	/**
	 * Gets the handle text.
	 *
	 * @author mqfdy
	 * @return the handle text
	 * @Date 2018-09-03 09:00
	 */
	public String getHandleText() {
		return handleText;
	}
	
	/**
	 * Sets the handle text.
	 *
	 * @author mqfdy
	 * @param handleText
	 *            the new handle text
	 * @Date 2018-09-03 09:00
	 */
	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}
	
	/**
	 * Checks if is handle.
	 *
	 * @author mqfdy
	 * @return true, if is handle
	 * @Date 2018-09-03 09:00
	 */
	public boolean isHandle() {
		return isHandle;
	}
	
	/**
	 * Sets the handle.
	 *
	 * @author mqfdy
	 * @param isHandle
	 *            the new handle
	 * @Date 2018-09-03 09:00
	 */
	public void setHandle(boolean isHandle) {
		this.isHandle = isHandle;
	}
	
	/**
	 * Gets the prompt.
	 *
	 * @author mqfdy
	 * @return the prompt
	 * @Date 2018-09-03 09:00
	 */
	public String getPrompt() {
		return prompt;
	}
	
	/**
	 * Sets the prompt.
	 *
	 * @author mqfdy
	 * @param prompt
	 *            the new prompt
	 * @Date 2018-09-03 09:00
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	
}
