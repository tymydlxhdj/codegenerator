package com.mqfdy.code.generator.auxiliary.generator;

import java.io.File;
import java.util.Locale;

import org.apache.velocity.util.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.GenProjectTypeUtilTools;
import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
import com.mqfdy.code.generator.entity.EntityGenerator;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
/**
 * 
 * @author mqf
 *
 */
public abstract class  BOMGenerator extends EntityGenerator {
	
	private String outputFolderPath;
	
	public BOMGenerator(IProject genProject, IPersistenceModel persistenceModel) {
		super(genProject, persistenceModel);
	}
	public BOMGenerator(AbstractJavaClass codeClass) {
		this(codeClass.getProject(), codeClass.getPersistenceModel());
		map.putAll(codeClass.getMap());
		this.outputFolderPath = codeClass.getOutputFolderPath();
	}
	/**
	 * 获取生成文件路径
	 */
	@Override
	protected String getOutputFolderPath() {
		
		String sceneName = persistenceModel.getScenceName().toLowerCase(Locale.getDefault());
			
		// 场景页设置的包路径
		String javaPkg = persistenceModel.getJavaPackage();

		String path = StringUtils.getPackageAsPath(javaPkg +"." + sceneName + getPackageExtention());
		IFolder folder = GenProjectTypeUtilTools.getOutputFolderPath(
				genProject, "java");
		if(outputFolderPath == null){
			outputFolderPath = folder.getLocation().toOSString() + File.separator + "main"
					+ File.separator + "java" + File.separator;
		}
		path = outputFolderPath + File.separator + path;
		return path;
	}

	public String getFileName() {
		return getFileNamePrefix() + persistenceModel.getJavaName()+getFileNameSuffix();
	}

	@Override
	protected String getFileNameWithoutExtension() {
		return getFileNamePrefix() + persistenceModel.getJavaName()+getFileNameSuffix();
	}
	
	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}
	/**
	 * 获取包名后缀
	 * @return
	 */
	public abstract String getPackageExtention();
	/**
	 * 获取java文件后缀
	 * @return
	 */
	public abstract String getFileNameSuffix();
	
	/**
	 * 获取java文件前缀
	 * @return
	 */
	public abstract String getFileNamePrefix();
	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}
}
