package com.mqfdy.code.generator.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.mqfdy.code.utils.Logger;

// TODO: Auto-generated Javadoc
/**
 * 生成代码抛出的异常.
 *
 * @author mqfdy
 */
public class CodeGenerationException extends CoreException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The file path. */
	private String filePath;

	/**
	 * Instantiates a new code generation exception.
	 *
	 * @param filePath
	 *            要生成的文件全路径
	 * @param e
	 *            the e
	 */
	public CodeGenerationException(String filePath, Exception e) {
		super(Logger.createStatus(IStatus.ERROR, IStatus.ERROR, "", e));
		this.filePath = filePath;

	}

	/**
	 * 得到要生成的文件的全路径.
	 *
	 * @author mqfdy
	 * @return the file path
	 * @Date 2018-09-03 09:00
	 */
	public String getFilePath() {
		return filePath;
	}
}
