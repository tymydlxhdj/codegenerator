package com.mqfdy.code.reverse;

public class ReverseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -173871841561224253L;
	
	public static final int CODE_DEL_DS_EX = 1;

	private int exceptionCode;
	
	private String exceptionMsg;
	
	public int getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public ReverseException() {
		// TODO Auto-generated constructor stub
	}

	public ReverseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ReverseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ReverseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ReverseException(Throwable cause, int exceptionCode, String exceptionMsg) {
		super(cause);
		this.exceptionMsg = exceptionMsg;
		this.exceptionCode = exceptionCode;
	}

}
