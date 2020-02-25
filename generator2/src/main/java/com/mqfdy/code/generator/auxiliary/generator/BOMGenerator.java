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
// TODO: Auto-generated Javadoc

/**
 * The Class BOMGenerator.
 *
 * @author mqf
 */
public abstract class  BOMGenerator extends EntityGenerator {
	
	/** The output folder path. */
	private String outputFolderPath;
	
	/**
	 * Instantiates a new BOM generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param persistenceModel
	 *            the persistence model
	 */
	public BOMGenerator(IProject genProject, IPersistenceModel persistenceModel) {
		super(genProject, persistenceModel);
	}
	
	/**
	 * Instantiates a new BOM generator.
	 *
	 * @param codeClass
	 *            the code class
	 */
	public BOMGenerator(AbstractJavaClass codeClass) {
		this(codeClass.getProject(), codeClass.getPersistenceModel());
		map.putAll(codeClass.getMap());
		this.outputFolderPath = codeClass.getOutputFolderPath();
	}
	
	/**
	 * 获取生成文件路径.
	 *
	 * @author mqfdy
	 * @return the output folder path
	 * @Date 2018-9-3 11:38:30
	 */
	@Override
	public String getOutputFolderPath() {
		
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

	/**
	 * Gets the file name.
	 *
	 * @return BOMGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#getFileName()
	 */
	public String getFileName() {
		return getFileNamePrefix() + persistenceModel.getJavaName()+getFileNameSuffix();
	}

	/**
	 * Gets the file name without extension.
	 *
	 * @return BOMGenerator
	 * @see com.mqfdy.code.generator.entity.EntityGenerator#getFileNameWithoutExtension()
	 */
	@Override
	public String getFileNameWithoutExtension() {
		return getFileNamePrefix() + persistenceModel.getJavaName()+getFileNameSuffix();
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return BOMGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileExtension()
	 */
	@Override
	public String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}
	
	/**
	 * 获取包名后缀.
	 *
	 * @author mqfdy
	 * @return the package extention
	 * @Date 2018-09-03 09:00
	 */
	public abstract String getPackageExtention();
	
	/**
	 * 获取java文件后缀.
	 *
	 * @author mqfdy
	 * @return the file name suffix
	 * @Date 2018-09-03 09:00
	 */
	public abstract String getFileNameSuffix();
	
	/**
	 * 获取java文件前缀.
	 *
	 * @author mqfdy
	 * @return the file name prefix
	 * @Date 2018-09-03 09:00
	 */
	public abstract String getFileNamePrefix();
	
	/**
	 * Sets the output folder path.
	 *
	 * @author mqfdy
	 * @param outputFolderPath
	 *            the new output folder path
	 * @Date 2018-09-03 09:00
	 */
	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}
}
