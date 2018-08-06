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

public abstract class EntityGenerator extends AbstractGenerator {
	
	protected IPersistenceModel persistenceModel;

	protected Map<String, Object> map = new HashMap<String, Object>();
	
	protected IProject genProject;
	
	protected String folder = "";
	
	public void setFolder(String f){
		this.folder = f;
	}
	
	public EntityGenerator(IProject genProject,
			IPersistenceModel persistenceModel) {
		super(genProject);
		this.persistenceModel = persistenceModel;
		this.genProject = genProject;
		map.put(PERSISTENCE_MODEL_KEY, persistenceModel );
	}

	@Override
	public Map<String, Object> getSourceMap() {
		return map;
	}

	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}

	public void setPersistenceModel(IPersistenceModel persistenceModel) {
		this.persistenceModel = persistenceModel;
	}

	public Table getTable() {
		return persistenceModel.getTable();
	}

	/**
	 * @see AbstractGenerator#isGenerateInternal()
	 */
	@Override
	protected boolean isGenerateInternal() {
		return getTable().isGeneratePO();
	}

	public String getOutputFilePath() {
		return getOutputFolderPath() + getFileNameWithoutExtension()
				+ getFileExtension();

	}

	/**
	 * 获取生成文件路径
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

	@Override
	protected String getFileNameWithoutExtension() {
		return persistenceModel.getJavaName();
	}
	
	public IProject getGenProject() {
		return genProject;
	}
	
	public void setGenProject(IProject genProject) {
		this.genProject = genProject;
	}
	
	
}
