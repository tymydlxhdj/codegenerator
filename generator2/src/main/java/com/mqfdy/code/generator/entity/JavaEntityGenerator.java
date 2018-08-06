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

/**
 * java文件生成器的抽象基类
 * 
 * @author mqfdy
 */
public class JavaEntityGenerator extends AbstractGenerator {
	
	private String templatePath;
	
	private IProject genProject;
	
	private String fileType;
	
	private IPersistenceModel persistenceModel;
	
	private static final String TEMPLATE_FILE_PATH_VO = "template/controller/Controller.vm";
	
	private static final String TEMPLATE_FILE_PATH_BIZC = "template/services/IService.vm";
	
	private static final String TEMPLATE_FILE_PATH_DAO = "template/repositories/Repository.vm";
	
	private String fileName = "";
	
	/**
	 * JavaEntityGenerator
	 * @param genProject
	 * @param scenePage
	 * @param modelPage
	 * @param templatePath
	 * @param persistenceModel
	 */
	public JavaEntityGenerator(IProject genProject, IPersistenceModel persistenceModel, String templatePath, String fileName){
		super(genProject);
		this.genProject = genProject;
		this.persistenceModel = persistenceModel;
		this.fileName = fileName;
		this.templatePath = templatePath;
	} 

	@Override
	final protected String getFileNameWithoutExtension() {
		return null;
	}

	public String getOutputFilePath() {
		return getOutputFolderPath() + fileName + getFileExtension();
	}
	
	//通过带“.”的包名获得路径，以"\"结尾
	public String getPackagePath(String packageName){
		String packagePath = packageName.replace(".", "\\");
		return packagePath;
	}
	
	/**
	 * 获取生成文件路径
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
	
	@Override
	public Map<String, Object> getSourceMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PERSISTENCE_MODEL_KEY, persistenceModel);
		return map;
	}
	
	@Override
	final protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

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

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getFileName() {
		return this.fileName;
	}
}
