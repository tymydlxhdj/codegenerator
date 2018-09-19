package com.mqfdy.code.generator.entity;

import java.io.File;

import org.apache.velocity.util.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.GenProjectTypeUtilTools;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
// TODO: Auto-generated Javadoc

/**
 * The Class DomainGenerator.
 *
 * @author mqfdy
 */
public class DomainGenerator extends EntityGenerator {
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private static final String TEMPLATE_FILE_PATH = "template/domain/Bean.vm";

	/**
	 * Instantiates a new domain generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param persistenceModel
	 *            the persistence model
	 */
	public DomainGenerator(IProject genProject, IPersistenceModel persistenceModel) {
		super(genProject, persistenceModel);
	}



	/**
	 * getFileExtension.
	 *
	 * @author mqfdy
	 * @return the object
	 * @Date 2018-9-3 14:01:20
	 */
	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}


	/**
	 * getTemplatePath.
	 *
	 * @author mqfdy
	 * @return the object
	 * @Date 2018-9-3 15:53:34
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * 获取生成文件路径.
	 *
	 * @author mqfdy
	 * @return the output folder path
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected String getOutputFolderPath() {
		
		// 场景页设置的包路径
		String javaPkg = persistenceModel.getJavaPackage();
		
		// 场景名
		String scenceName = persistenceModel.getScenceName();
		String path = StringUtils.getPackageAsPath(javaPkg + "." + scenceName + ".domain");
		if(folder.equals("")){
			IFolder folder = GenProjectTypeUtilTools.getOutputFolderPath(
					genProject, "java");
			path = folder.getLocation().toOSString() + File.separator + "main"
					+ File.separator + "java" + File.separator + path;
			return path;
		} else{
			path = genProject.getLocation()+ File.separator + "main" + File.separator + "java" + File.separator +
					persistenceModel.getSrcFolder()+ File.separator + folder + File.separator;
			return path;
		}
	}


	/**
	 * getFileName.
	 *
	 * @return the object
	 */
	public String getFileName() {
		return persistenceModel.getJavaName();
	}

}
