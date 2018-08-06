package com.mqfdy.code.generator.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.model.CodeGenerationException;

/**
 * web.xml的生成器
 * @author mqfdy
 *
 */
public class WebXmlGenerator extends AbstractGenerator {
	

	protected Map<String, Object> map = new HashMap<String, Object>();
	
	protected IProject genProject;
	
	protected String folder = "";
	
	public void setFolder(String f){
		this.folder = f;
	}
	
	public WebXmlGenerator(IProject genProject,String projectType,String version) {
		super(genProject);
		this.genProject = genProject;
		map.put("projectType", projectType);
		map.put("projectName", genProject.getName());
		map.put("version", version);
		map.put("version_", version.replace(".", "_"));
		String prefix = "<!--";
		String tail = "-->";
		String commentPrefix = "";
		String commentTail = "";
		if(!"3.0".equals(version)){
			prefix = "";
			tail = "";
			commentPrefix = "<!--";
			commentTail = "-->";
		}
		map.put("prefix", prefix);
		map.put("tail", tail);
		map.put("commentPrefix", commentPrefix);
		map.put("commentTail", commentTail);
	}

	@Override
	public Map<String, Object> getSourceMap() {
		return map;
	}

	public String getOutputFilePath() {
		return genProject.getLocation().toOSString()+ File.separator + "WebContent"+ File.separator + "WEB-INF" + File.separator + getFileNameWithoutExtension()
				+ getFileExtension();

	}
	private String getOutputFilePathReal() {
		return genProject.getLocation().toOSString()+ File.separator + "WebContent"+ File.separator + "WEB-INF" + File.separator + getFileNameWithoutExtension()
				+ ".xml";

	}
	
	public void generate(){
		try {
			super.generate();
			String realPath = getOutputFilePathReal();
			File fileOld = new File(realPath);
			if(fileOld.exists()){
				fileOld.delete();
			}
			File f = new File(getOutputFilePath());
			f.renameTo(new File(realPath));
		} catch (CodeGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取生成文件路径
	 */
	@Override
	protected String getOutputFolderPath() {
		return genProject.getLocation().toOSString();
	}

	protected String getFileNameWithoutExtension() {
		return "web";
	}
	
	public IProject getGenProject() {
		return genProject;
	}
	
	public void setGenProject(IProject genProject) {
		this.genProject = genProject;
	}
	
	public String getFileExtension(){
		return ".txt";
	}

	public String getFileName() {
		return "web";
	}

	@Override
	protected String getTemplatePath() {
		return "template/web/web.vm";
	}
}
