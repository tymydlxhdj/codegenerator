package com.mqfdy.code.reverse;

// TODO: Auto-generated Javadoc
/**
 * The Class ReverseException.
 *
 * @author mqfdy
 */
public class ReverseException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -173871841561224253L;
	
	/** The Constant CODE_DEL_DS_EX. */
	public static final int CODE_DEL_DS_EX = 1;

	/** The exception code. */
	private int exceptionCode;
	
	/** The exception msg. */
	private String exceptionMsg;
	
	/**
	 * Gets the exception code.
	 *
	 * @author mqfdy
	 * @return the exception code
	 * @Date 2018-09-03 09:00
	 */
	public int getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * Sets the exception code.
	 *
	 * @author mqfdy
	 * @param exceptionCode
	 *            the new exception code
	 * @Date 2018-09-03 09:00
	 */
	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * Gets the exception msg.
	 *
	 * @author mqfdy
	 * @return the exception msg
	 * @Date 2018-09-03 09:00
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	/**
	 * Sets the exception msg.
	 *
	 * @author mqfdy
	 * @param exceptionMsg
	 *            the new exception msg
	 * @Date 2018-09-03 09:00
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	/**
	 * Instantiates a new reverse exception.
	 */
	public ReverseException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new reverse exception.
	 *
	 * @param message
	 *            the message
	 */
	public ReverseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new reverse exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public ReverseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new reverse exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ReverseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new reverse exception.
	 *
	 * @param cause
	 *            the cause
	 * @param exceptionCode
	 *            the exception code
	 * @param exceptionMsg
	 *            the exception msg
	 */
	public ReverseException(Throwable cause, int exceptionCode, String exceptionMsg) {
		super(cause);
		this.exceptionMsg = exceptionMsg;
		this.exceptionCode = exceptionCode;
	}

}
