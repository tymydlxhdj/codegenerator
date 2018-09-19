package com.mqfdy.code.generator.model;

// TODO: Auto-generated Javadoc
/**
 * 生成代码接口.
 *
 * @author mqfdy
 */
public interface IGenerator {

	/**
	 * client调用此方法来完成生成代码的操作.
	 *
	 * @author mqfdy
	 * @throws CodeGenerationException
	 *             the code generation exception
	 * @Date 2018-09-03 09:00
	 */
	public void generate() throws CodeGenerationException;

	/**
	 * 客户端调用此方法来询问generator它要生成的文件在文件系统中是否已经存在了.
	 *
	 * @author mqfdy
	 * @return true 目标文件已经存在 false 要生成的文件在文件系统中还不存在
	 * @Date 2018-9-3 11:38:32
	 */
	public boolean isTargetFileExist();

	/**
	 * 获得要生成文件的全路径，抽象方法，由各个generator自己计算得出.
	 *
	 * @author mqfdy
	 * @return the output file path
	 * @Date 2018-09-03 09:00
	 */
	public String getOutputFilePath();
	
	/**
	 * 获取文件名称.
	 *
	 * @author mqfdy
	 * @return the file name
	 * @Date 2018-09-03 09:00
	 */
	public String getFileName();

	/**
	 * 判断一个文件是否会生成.
	 *
	 * @author mqfdy
	 * @return true, if is generate
	 * @Date 2018-09-03 09:00
	 */
	public boolean isGenerate();

	/**
	 * 设置一个文件是否要生成，可以根据用户的选择来生成相应的文件.
	 *
	 * @author mqfdy
	 * @param isGenerate
	 *            the new generate
	 * @Date 2018-09-03 09:00
	 */
	public void setGenerate(boolean isGenerate);
}
