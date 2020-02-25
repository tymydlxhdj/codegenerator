package com.mqfdy.code.generator.entity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.GenProjectTypeUtilTools;
import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.utils.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * java文件生成器的抽象基类.
 *
 * @author mqfdy
 */
public class JavaEntityGenerator extends AbstractGenerator {
	
	/** The template path. */
	private String templatePath;
	
	/** The gen project. */
	private IProject genProject;
	
	/** The file type. */
	private String fileType;
	
	/** The persistence model. */
	private IPersistenceModel persistenceModel;
	
	/** The Constant TEMPLATE_FILE_PATH_VO. */
	private static final String TEMPLATE_FILE_PATH_VO = "template/controller/Controller.vm";
	
	/** The Constant TEMPLATE_FILE_PATH_BIZC. */
	private static final String TEMPLATE_FILE_PATH_BIZC = "template/services/IService.vm";
	
	/** The Constant TEMPLATE_FILE_PATH_DAO. */
	private static final String TEMPLATE_FILE_PATH_DAO = "template/repositories/Repository.vm";
	
	/** The file name. */
	private String fileName = "";
	
	/**
	 * JavaEntityGenerator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param persistenceModel
	 *            the persistence model
	 * @param templatePath
	 *            the template path
	 * @param fileName
	 *            the file name
	 */
	public JavaEntityGenerator(IProject genProject, IPersistenceModel persistenceModel, String templatePath, String fileName){
		super(genProject);
		this.genProject = genProject;
		this.persistenceModel = persistenceModel;
		this.fileName = fileName;
		this.templatePath = templatePath;
	} 

	/**
	 * Gets the file name without extension.
	 *
	 * @return JavaEntityGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileNameWithoutExtension()
	 */
	@Override
	final protected String getFileNameWithoutExtension() {
		return null;
	}

	/**
	 * Gets the output file path.
	 *
	 * @return JavaEntityGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#getOutputFilePath()
	 */
	public String getOutputFilePath() {
		return getOutputFolderPath() + fileName + getFileExtension();
	}
	
	/**
	 * Gets the package path.
	 *
	 * @author mqfdy
	 * @param packageName
	 *            the package name
	 * @return the package path
	 * @Date 2018-09-03 09:00
	 */
	//通过带“.”的包名获得路径，以"\"结尾
	public String getPackagePath(String packageName){
		String packagePath = packageName.replace(".", "\\");
		return packagePath;
	}
	
	/**
	 * 获取生成文件路径.
	 *
	 * @author mqfdy
	 * @return the output folder path
	 * @Date 2018-9-3 11:38:36
	 */
	@Override
	protected String getOutputFolderPath() {
		
		String javaPkg = persistenceModel.getJavaPackage() + "." + persistenceModel.getScenceName();
		if (fileName.endsWith("Controller")) {
			javaPkg = javaPkg + ".controller";
		} else if(fileName.endsWith("Service")) {
			javaPkg = javaPkg + ".services";
		} else if(fileName.endsWith("Repository")) {
			javaPkg = javaPkg + ".repositories";
		}
		
		IFolder folder = GenProjectTypeUtilTools.getOutputFolderPath(
				genProject, "java");
		
		return folder.getLocation().toOSString() + File.separator + "main" + File.separator + "java" +
					File.separator + StringUtils.getPackageAsPath(javaPkg);
	}
	
	/**
	 * Gets the source map.
	 *
	 * @return JavaEntityGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getSourceMap()
	 */
	@Override
	public Map<String, Object> getSourceMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PERSISTENCE_MODEL_KEY, persistenceModel);
		return map;
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return JavaEntityGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileExtension()
	 */
	@Override
	final protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * Gets the template path.
	 *
	 * @return JavaEntityGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 */
	@Override
	public String getTemplatePath() {
		if (AbstractGenerator.JAVA_FILE_TYPE_VO.equals(fileType)) {
			return TEMPLATE_FILE_PATH_VO;
		} else if(AbstractGenerator.JAVA_FILE_TYPE_BIZC.equals(fileType)) {
			return TEMPLATE_FILE_PATH_BIZC;
		} else if(AbstractGenerator.JAVA_FILE_TYPE_DAO.equals(fileType)) {
			return TEMPLATE_FILE_PATH_DAO;
		} else {
			return templatePath;
		}
	}

	/**
	 * Sets the template path.
	 *
	 * @author mqfdy
	 * @param templatePath
	 *            the new template path
	 * @Date 2018-09-03 09:00
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * Gets the file name.
	 *
	 * @return JavaEntityGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#getFileName()
	 */
	public String getFileName() {
		return this.fileName;
	}
}
