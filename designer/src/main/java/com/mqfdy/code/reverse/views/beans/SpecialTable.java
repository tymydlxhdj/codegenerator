package com.mqfdy.code.reverse.views.beans;

public class SpecialTable {

	private String name;		//表名		
	private int problemType;	//问题类型    IViewConstant
	private String desc;		//问题描述
	private String handleText;	//处理方式（文本）
	private String prompt;		//提示
	private boolean isHandle;	//处理类型       true: 自动处理, false: 忽略
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getProblemType() {
		return problemType;
	}
	public void setProblemType(int problemType) {
		this.problemType = problemType;
	}
	public String getHandleText() {
		return handleText;
	}
	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}
	public boolean isHandle() {
		return isHandle;
	}
	public void setHandle(boolean isHandle) {
		this.isHandle = isHandle;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	
}
