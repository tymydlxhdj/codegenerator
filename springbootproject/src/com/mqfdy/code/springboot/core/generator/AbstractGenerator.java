package com.mqfdy.code.springboot.core.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
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

import com.mqfdy.code.springboot.Logger;
import com.mqfdy.code.springboot.core.MicroProjectPlugin;
import com.mqfdy.code.springboot.core.generator.utils.DateTimeUtil;
import com.mqfdy.code.springboot.core.generator.utils.StringUtils;
import com.mqfdy.code.utils.PluginUtil;

abstract public class AbstractGenerator implements IGenerator {

	// 对应vm模板中的ptableList变量
	public static final String PERSISTENCE_MODEL_LIST_KEY = "ptableList";
	
	// 对应vm模板中的JavaName变量
	protected static final String JavaName = "JavaName";
	// 对应vm模板中的ptable变量
	protected static final String PERSISTENCE_MODEL_KEY = "ptable";
	
	protected static final String PROJECT_NAME = "projectName";
	// 要生成的java文件的扩展名
	protected String JAVA_FILE_EXTENSION = ".java";

	private VelocityContext context;
	
	private String parentId;
	
	// 对应注册到Context中东STRING_UTILS_KEY，也就是vm模板中的strings变量
	private static StringUtils strings = new StringUtils();
	
	// 模板文件的编码方式
	private static final String TEMPLATE_FILE_ENCODEING = "UTF-8";
	// 是否要生成gererator要生成的代码
	private boolean isGenerate = true;
	private boolean isGenerateInternal = true;

	private static String DEFAULT_OUTPUT_FILE_ENCODING = "UTF-8";

	private IProject genProject;

	// 对应vm模板中的table变量
	protected static final String TABLE_KEY = "table";
	// 对应vm模板中的strings变量
	protected static final String STRING_UTILS_KEY = "strings";
	// 对应vm模板中的now变量
	protected static final String NOW_KEY = "now";
	// 对应vm模板中的autor变量
	protected static final String AUTHOR_KEY = "author";
	// 对应vm模板中的scene变量
	protected static final String SCENE_KEY = "scene";
	// 对应vm模板中的dquote变量
	protected static final String D_QUOTE_KEY = "dquote";
	protected static final String D_QUOTE_VALUE = "\"";
	// 对应vm模板中的dollar变量
	protected static final String DOLLAR_KEY = "dollar";
	protected static final String DOLLAR_VALUE = "$";
	
	protected static final String LINE_SEPARATOR = "separator";
	
	public static final String JAVA_FILE_TYPE_VO = "vo";
	
	public static final String JAVA_FILE_TYPE_BIZC = "bizc";
	
	public static final String JAVA_FILE_TYPE_DAO = "dao";
	
	/**
	 * 树场景查询 父节点 字段 变量
	 */
	public static final String  PARENT_ID= "parent_col_name";
	/**
	 * om与java类型转换map
	 */
	protected static final String TYPE_MAP = "typeMap";
	
	
	
	private static Properties properties = new Properties();
	static {
		
		// 设置velocity资源加载方式为file
		properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		
		//设置velocity资源加载方式为file时的处理类
		properties.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		String root;
		try {
			root = PluginUtil
					.getPluginOSPath(MicroProjectPlugin.PLUGIN_ID);
			properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
					root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 是否要生成gererator要生成的代码
	public AbstractGenerator(IProject curGenProject) {
		this.genProject = curGenProject;
	}

	public void generate() throws CodeGenerationException {
		String filePath = getOutputFilePath();
		if (!isGenerate()) {
			return;
		}
		OutputStream os = null;
		Writer streamWriter= null;
		Writer writer=null;
		try {
			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);
			initContext();
			mkOutputDir();
			String encoding = getEncoding();
			os = new FileOutputStream(filePath);
			streamWriter= new OutputStreamWriter(os, encoding);
			writer = new BufferedWriter(streamWriter);
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
			if(null!= os){
				try {
					os.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
			if(null!=streamWriter){
				try {
					streamWriter.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
		}
	}

	/**
	 * 创建输出目录
	 */
	private void mkOutputDir() {
		FileUtil.mkdir(getOutputFolderPath());
		File dir = new File(getOutputFolderPath());
		if (!dir.exists() && getOutputFolderPath().endsWith(File.separator)) {
			dir.mkdirs();
		}
	}

	/**
	 * 获取encoding
	 * @return
	 */
	private String getEncoding() {
		String encoding = DEFAULT_OUTPUT_FILE_ENCODING;
		try {
			encoding = getGenProject().getDefaultCharset();
		} catch (CoreException e) {
			encoding = DEFAULT_OUTPUT_FILE_ENCODING;
		}
		return encoding;
	}

	public boolean isGenerate() {
		return isGenerateInternal() && isGenerate;
	}

	public void setGenerate(boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	/**
	 * 判断是否要生成该generator对应的代码，默认是生成的，子类可通过重写此方法来根据自己的逻辑来判断
	 * 
	 * @return true 生成，false 不生成
	 */
	protected boolean isGenerateInternal() {
		return isGenerateInternal;
	}

	/**
	 * 初始化VelocityContext对象，填充数据，子类可以通过重写此方法来完成自己的填充数据的逻辑
	 * 但是推荐子类重写getSourceMap（）方法来完成填充自己数据的目的
	 */
	protected void initContext() {
		getContext().put(STRING_UTILS_KEY, strings);
		getContext().put(NOW_KEY, DateTimeUtil.date2String(new Date()));
		getContext().put(AUTHOR_KEY, System.getProperty("user.name"));
		getContext().put(D_QUOTE_KEY, D_QUOTE_VALUE);
		getContext().put(DOLLAR_KEY, DOLLAR_VALUE);
		getContext().put(LINE_SEPARATOR,"");
		getContext().put(LINE_SEPARATOR,"");
		Map<String, Object> map = getSourceMap();
		for (Entry<String, Object> entry : map.entrySet()) {
			getContext().put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 得到Velocity的VelocityContext对象，默认返回VelocityContext对象，
	 * 子类也可通过重写此方法返回自己定义的VelocityContext的子类对象
	 */
	protected VelocityContext getContext() {
		if (context == null)
			context = new VelocityContext();
		return context;
	}

	/**
	 * 获得要生成文件的文件名，不包括扩展名，由各个generator自己计算得出要生成的文件名称
	 */
	abstract protected String getFileNameWithoutExtension();

	/**
	 * 获得要生成文件的扩展名，要以'.'开始，由各个generator自己维护要生成的文件的扩展名
	 */
	abstract protected String getFileExtension();

	abstract protected String getOutputFolderPath();

	/**
	 * 获得generator要使用的vm模板所在的路径，有具体的generator维护自己的vm模板位置
	 */
	abstract protected String getTemplatePath();

	public boolean isTargetFileExist() {
		return new File(getOutputFilePath()).exists();
	}
	/**
	 * 获得要填充到VelocityContext中的数据，key应该对应vm模板中的变量 作用就是为vm中的各个变量赋值
	 */
	abstract protected Map<String, Object> getSourceMap();

	public IProject getGenProject() {
		return genProject;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
