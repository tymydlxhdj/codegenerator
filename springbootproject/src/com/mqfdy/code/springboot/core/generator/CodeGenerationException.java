package com.mqfdy.code.springboot.core.generator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.mqfdy.code.springboot.Logger;
/**
 * 生成代码抛出的异常
 * 
 * @author ZH
 */
public class CodeGenerationException extends CoreException {
	
	private static final long serialVersionUID = 1L;
	
	private String filePath;

	/**
	 * 
	 * @param filePath
	 *            要生成的文件全路径
	 * @param e
	 */
	public CodeGenerationException(String filePath, Exception e) {
		super(Logger.createStatus(IStatus.ERROR, IStatus.ERROR, "", e));
		this.filePath = filePath;

	}

	/**
	 * 得到要生成的文件的全路径
	 */
	public String getFilePath() {
		return filePath;
	}
}
