package com.mqfdy.code.generator.entity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.util.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.mqfdy.code.datasource.mapping.Table;
import com.mqfdy.code.generator.GenProjectTypeUtilTools;
import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.persistence.IPersistenceModel;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityGenerator.
 *
 * @author mqfdy
 */
public abstract class EntityGenerator extends AbstractGenerator {
	
	/** The persistence model. */
	protected IPersistenceModel persistenceModel;

	/** The map. */
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	/** The gen project. */
	protected IProject genProject;
	
	/** The folder. */
	protected String folder = "";
	
	/**
	 * Sets the folder.
	 *
	 * @author mqfdy
	 * @param f
	 *            the new folder
	 * @Date 2018-09-03 09:00
	 */
	public void setFolder(String f){
		this.folder = f;
	}
	
	/**
	 * Instantiates a new entity generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param persistenceModel
	 *            the persistence model
	 */
	public EntityGenerator(IProject genProject,
			IPersistenceModel persistenceModel) {
		super(genProject);
		this.persistenceModel = persistenceModel;
		this.genProject = genProject;
		map.put(PERSISTENCE_MODEL_KEY, persistenceModel );
	}

	/**
	 * Gets the source map.
	 *
	 * @return EntityGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getSourceMap()
	 */
	@Override
	public Map<String, Object> getSourceMap() {
		return map;
	}

	/**
	 * Gets the persistence model.
	 *
	 * @author mqfdy
	 * @return the persistence model
	 * @Date 2018-09-03 09:00
	 */
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}

	/**
	 * Sets the persistence model.
	 *
	 * @author mqfdy
	 * @param persistenceModel
	 *            the new persistence model
	 * @Date 2018-09-03 09:00
	 */
	public void setPersistenceModel(IPersistenceModel persistenceModel) {
		this.persistenceModel = persistenceModel;
	}

	/**
	 * Gets the table.
	 *
	 * @author mqfdy
	 * @return the table
	 * @Date 2018-09-03 09:00
	 */
	public Table getTable() {
		return persistenceModel.getTable();
	}

	/**
	 * Checks if is generate internal.
	 *
	 * @return true, if is generate internal
	 * @see AbstractGenerator#isGenerateInternal()
	 */
	@Override
	protected boolean isGenerateInternal() {
		return getTable().isGeneratePO();
	}

	/**
	 * Gets the output file path.
	 *
	 * @return EntityGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#getOutputFilePath()
	 */
	public String getOutputFilePath() {
		return getOutputFolderPath() + getFileNameWithoutExtension()
				+ getFileExtension();

	}

	/**
	 * 获取生成文件路径.
	 *
	 * @author mqfdy
	 * @return the output folder path
	 * @Date 2018-9-3 11:38:25
	 */
	@Override
	protected String getOutputFolderPath() {
		
		// 场景页设置的包路径
		String javaPkg = persistenceModel.getJavaPackage();
		
		// 场景名
		String scenceName = persistenceModel.getScenceName();
		String path = StringUtils.getPackageAsPath(javaPkg + "." + scenceName + ".po");
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
	 * Gets the file name without extension.
	 *
	 * @return EntityGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileNameWithoutExtension()
	 */
	@Override
	protected String getFileNameWithoutExtension() {
		return persistenceModel.getJavaName();
	}
	
	/**
	 * Gets the gen project.
	 *
	 * @return EntityGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getGenProject()
	 */
	public IProject getGenProject() {
		return genProject;
	}
	
	/**
	 * Sets the gen project.
	 *
	 * @author mqfdy
	 * @param genProject
	 *            the new gen project
	 * @Date 2018-09-03 09:00
	 */
	public void setGenProject(IProject genProject) {
		this.genProject = genProject;
	}
	
	
}
