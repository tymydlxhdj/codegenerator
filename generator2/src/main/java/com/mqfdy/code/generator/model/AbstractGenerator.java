package com.mqfdy.code.generator.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.texen.util.FileUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.mqfdy.code.datasource.mapping.TypeMap;
import com.mqfdy.code.datasource.utils.DateTimeUtil;
import com.mqfdy.code.generator.GeneratorPlugin;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.utils.Logger;
import com.mqfdy.code.utils.PluginUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGenerator.
 *
 * @author mqfdy
 */
abstract public class AbstractGenerator implements IGenerator {

	/** The Constant PERSISTENCE_MODEL_LIST_KEY. */
	// 对应vm模板中的ptableList变量
	public static final String PERSISTENCE_MODEL_LIST_KEY = "ptableList";
	
	/** The Constant JavaName. */
	// 对应vm模板中的JavaName变量
	protected static final String JavaName = "JavaName";
	
	/** The Constant PERSISTENCE_MODEL_KEY. */
	// 对应vm模板中的ptable变量
	protected static final String PERSISTENCE_MODEL_KEY = "ptable";
	
	/** The java file extension. */
	// 要生成的java文件的扩展名
	protected String JAVA_FILE_EXTENSION = ".java";

	/** The context. */
	private VelocityContext context;
	
	/** The parent id. */
	private String parentId;
	
	/** The strings. */
	// 对应注册到Context中东STRING_UTILS_KEY，也就是vm模板中的strings变量
	private static StringUtils strings = new StringUtils();
	
	/** The Constant TEMPLATE_FILE_ENCODEING. */
	// 模板文件的编码方式
	private static final String TEMPLATE_FILE_ENCODEING = "UTF-8";
	
	/** The is generate. */
	// 是否要生成gererator要生成的代码
	private boolean isGenerate = true;
	
	/** The is generate internal. */
	private boolean isGenerateInternal = true;

	/** The default output file encoding. */
	private static String DEFAULT_OUTPUT_FILE_ENCODING = "UTF-8";

	/** The gen project. */
	protected IProject genProject;

	/** The Constant TABLE_KEY. */
	// 对应vm模板中的table变量
	protected static final String TABLE_KEY = "table";
	
	/** The Constant STRING_UTILS_KEY. */
	// 对应vm模板中的strings变量
	protected static final String STRING_UTILS_KEY = "strings";
	
	/** The Constant NOW_KEY. */
	// 对应vm模板中的now变量
	protected static final String NOW_KEY = "now";
	
	/** The Constant AUTHOR_KEY. */
	// 对应vm模板中的autor变量
	protected static final String AUTHOR_KEY = "author";
	
	/** The Constant SCENE_KEY. */
	// 对应vm模板中的scene变量
	protected static final String SCENE_KEY = "scene";
	
	/** The Constant D_QUOTE_KEY. */
	// 对应vm模板中的dquote变量
	protected static final String D_QUOTE_KEY = "dquote";
	
	/** The Constant D_QUOTE_VALUE. */
	protected static final String D_QUOTE_VALUE = "\"";
	
	/** The Constant DOLLAR_KEY. */
	// 对应vm模板中的dollar变量
	protected static final String DOLLAR_KEY = "dollar";
	
	/** The Constant DOLLAR_VALUE. */
	protected static final String DOLLAR_VALUE = "$";
	
	/** The Constant LINE_SEPARATOR. */
	protected static final String LINE_SEPARATOR = "separator";
	
	/** The Constant JAVA_FILE_TYPE_VO. */
	public static final String JAVA_FILE_TYPE_VO = "vo";
	
	/** The Constant JAVA_FILE_TYPE_BIZC. */
	public static final String JAVA_FILE_TYPE_BIZC = "bizc";
	
	/** The Constant JAVA_FILE_TYPE_DAO. */
	public static final String JAVA_FILE_TYPE_DAO = "dao";
	
	/** 树场景查询 父节点 字段 变量. */
	public static final String  PARENT_ID= "parent_col_name";
	
	/** om与java类型转换map. */
	protected static final String TYPE_MAP = "typeMap";
	
	/** The root. */
	private static String root;
	
	/** The properties. */
	protected static Properties properties = new Properties();
	static {
		try {
			root = PluginUtil
					.getPluginOSPath(GeneratorPlugin.PLUGIN_ID);
			properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
					root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates a new abstract generator.
	 *
	 * @param curGenProject
	 *            the cur gen project
	 */
	// 是否要生成gererator要生成的代码
	public AbstractGenerator(IProject curGenProject) {
		this.genProject = curGenProject;
	}

	/**
	 * Generate.
	 *
	 * @throws CodeGenerationException
	 *             AbstractGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#generate()
	 */
	public void generate() throws CodeGenerationException {
		String filePath = getOutputFilePath();
		if (!isGenerate()) {
			return;
		}
		Writer writer=null;
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		FileUtil.mkdir(getOutputFolderPath());
		//////////////////////////////////////
		File dir = new File(getOutputFolderPath());
		if (!dir.exists() && getOutputFolderPath().endsWith(File.separator)) {
			dir.mkdirs() ;// 创建单个目录
		}
		String encoding = DEFAULT_OUTPUT_FILE_ENCODING;
		try {
			// encoding = getUapProject().getProject().getDefaultCharset();
			encoding = getGenProject().getDefaultCharset();
		} catch (CoreException e) {
			encoding = DEFAULT_OUTPUT_FILE_ENCODING;
		}
		try {
			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);
			initContext();
			outputStream = new FileOutputStream(filePath);
			outputStreamWriter = new OutputStreamWriter(outputStream,encoding);
			writer = new BufferedWriter(outputStreamWriter);
			Template t = ve.getTemplate(getTemplatePath(), TEMPLATE_FILE_ENCODEING);
			t.merge(getContext(), writer);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CodeGenerationException(getOutputFilePath(), e);
		}finally{
			if(null!=writer){
				try {
					writer.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
			if(null!=outputStream){
				try {
					outputStream.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
			if(null!=outputStreamWriter){
				try {
					outputStreamWriter.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
		}
	}
	
	/**
	 * Checks if is generate.
	 *
	 * @return AbstractGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#isGenerate()
	 */
	public boolean isGenerate() {
		return isGenerateInternal() && isGenerate;
	}

	/**
	 * Sets the generate.
	 *
	 * @param isGenerate
	 *            AbstractGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#setGenerate(boolean)
	 */
	public void setGenerate(boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	/**
	 * 判断是否要生成该generator对应的代码，默认是生成的，子类可通过重写此方法来根据自己的逻辑来判断.
	 *
	 * @author mqfdy
	 * @return true 生成，false 不生成
	 * @Date 2018-9-3 11:38:38
	 */
	protected boolean isGenerateInternal() {
		return isGenerateInternal;
	}

	/**
	 * 初始化VelocityContext对象，填充数据，子类可以通过重写此方法来完成自己的填充数据的逻辑
	 * 但是推荐子类重写getSourceMap（）方法来完成填充自己数据的目的.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:38
	 */
	protected void initContext() {
		getContext().put(STRING_UTILS_KEY, strings);
		getContext().put(NOW_KEY, DateTimeUtil.date2String(new Date()));
		getContext().put(AUTHOR_KEY, System.getProperty("user.name"));
		getContext().put(D_QUOTE_KEY, D_QUOTE_VALUE);
		getContext().put(DOLLAR_KEY, DOLLAR_VALUE);
		getContext().put(LINE_SEPARATOR,"");
		if(!StringUtils.isEmpty(parentId)){
			getContext().put(PARENT_ID,parentId);
		}
		TypeMap.initialize();
		getContext().put(TYPE_MAP,TypeMap.getPropertyToJavaMap());
		getContext().put(LINE_SEPARATOR,"");
		Map<String, Object> map = getSourceMap();
		for (Entry<String, Object> entry : map.entrySet()) {
			getContext().put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 得到Velocity的VelocityContext对象，默认返回VelocityContext对象，
	 * 子类也可通过重写此方法返回自己定义的VelocityContext的子类对象.
	 *
	 * @author mqfdy
	 * @return the context
	 * @Date 2018-09-03 09:00
	 */
	protected VelocityContext getContext() {
		if (context == null)
			context = new VelocityContext();
		return context;
	}

	/**
	 * 获得要生成文件的文件名，不包括扩展名，由各个generator自己计算得出要生成的文件名称.
	 *
	 * @author mqfdy
	 * @return the file name without extension
	 * @Date 2018-09-03 09:00
	 */
	abstract protected String getFileNameWithoutExtension();

	/**
	 * 获得要生成文件的扩展名，要以'.'开始，由各个generator自己维护要生成的文件的扩展名
	 *
	 * @author mqfdy
	 * @return the file extension
	 * @Date 2018-09-03 09:00
	 */
	abstract protected String getFileExtension();

	/**
	 * Gets the output folder path.
	 *
	 * @author mqfdy
	 * @return the output folder path
	 * @Date 2018-09-03 09:00
	 */
	abstract protected String getOutputFolderPath();

	/**
	 * 获得generator要使用的vm模板所在的路径，有具体的generator维护自己的vm模板位置.
	 *
	 * @author mqfdy
	 * @return the template path
	 * @Date 2018-09-03 09:00
	 */
	abstract protected String getTemplatePath();

	/**
	 * Checks if is target file exist.
	 *
	 * @return AbstractGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#isTargetFileExist()
	 */
	public boolean isTargetFileExist() {
		return new File(getOutputFilePath()).exists();
	}
	
	/**
	 * 获得要填充到VelocityContext中的数据，key应该对应vm模板中的变量 作用就是为vm中的各个变量赋值.
	 *
	 * @author mqfdy
	 * @return the source map
	 * @Date 2018-09-03 09:00
	 */
	abstract protected Map<String, Object> getSourceMap();

	/**
	 * Gets the gen project.
	 *
	 * @author mqfdy
	 * @return the gen project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getGenProject() {
		return genProject;
	}

	/**
	 * Gets the parent id.
	 *
	 * @author mqfdy
	 * @return the parent id
	 * @Date 2018-09-03 09:00
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent id.
	 *
	 * @author mqfdy
	 * @param parentId
	 *            the new parent id
	 * @Date 2018-09-03 09:00
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * Generate.
	 *
	 * @author mqfdy
	 * @param materals
	 *            the materals
	 * @throws CodeGenerationException
	 *             the code generation exception
	 * @Date 2018-9-19 15:09:29
	 */
	public void generate(List<ICodeFileMaterial> materals) throws CodeGenerationException {
		if(materals != null && !materals.isEmpty()){
			for(ICodeFileMaterial cfm : materals){
				this.generate(cfm);
			}
		}
	}
	
	/**
	 * Generate.
	 *
	 * @author mqfdy
	 * @param material
	 *            the material
	 * @throws CodeGenerationException
	 *             the code generation exception
	 * @Date 2018-9-19 10:17:01
	 */
	public void generate(ICodeFileMaterial material) throws CodeGenerationException {
		String outputPath = material.getOutputPath();
		File dir = new File(outputPath);
		if (!dir.exists() && outputPath.endsWith(File.separator)) {
			dir.mkdirs() ;// 创建单个目录
		}
		String filePath = material.getOutputFilePath();
		String encoding = DEFAULT_OUTPUT_FILE_ENCODING;
		Writer writer=null;
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		try {
			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);
			Map<String, Object> map = material.getVelocityMap();
			for (Entry<String, Object> entry : map.entrySet()) {
				getContext().put(entry.getKey(), entry.getValue());
			}
			
			outputStream = new FileOutputStream(filePath);
			outputStreamWriter = new OutputStreamWriter(outputStream,encoding);
			writer = new BufferedWriter(outputStreamWriter);
			Template t = ve.getTemplate(material.getTemplatePath(), TEMPLATE_FILE_ENCODEING);
			t.merge(context, writer);
			writer.flush();
		} catch (IOException e) {
			throw new CodeGenerationException(material.getOutputFilePath(), e);
		}finally{
			if(null!=writer){
				try {
					writer.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
			if(null!=outputStream){
				try {
					outputStream.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
			if(null!=outputStreamWriter){
				try {
					outputStreamWriter.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
		}
	}
}
