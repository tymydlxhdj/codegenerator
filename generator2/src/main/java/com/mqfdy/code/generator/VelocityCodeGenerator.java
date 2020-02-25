/**
 * 
 */
package com.mqfdy.code.generator;

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

import com.mqfdy.code.datasource.mapping.TypeMap;
import com.mqfdy.code.datasource.utils.DateTimeUtil;
import com.mqfdy.code.generator.model.CodeFileMaterial;
import com.mqfdy.code.generator.model.CodeGenerationException;
import com.mqfdy.code.generator.model.ICodeFileMaterial;
import com.mqfdy.code.generator.model.IGenerator;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.utils.Logger;
import com.mqfdy.code.utils.PluginUtil;

// TODO: Auto-generated Javadoc
/**
 * 代码生成器. Velocity
 *
 * @author mqfdy
 */
public class VelocityCodeGenerator implements IGenerator{
	
	/** The code generator. */
	private static IGenerator codeGenerator = new VelocityCodeGenerator();
	
	/** The strings.对应注册到Context中东STRING_UTILS_KEY，也就是vm模板中的strings变量 */
	private static StringUtils strings = new StringUtils();
	
	/** The Constant TEMPLATE_FILE_ENCODEING. 模板文件的编码方式*/
	private static final String TEMPLATE_FILE_ENCODEING = "UTF-8";

	/** The default output file encoding. */
	private static String DEFAULT_OUTPUT_FILE_ENCODING = "UTF-8";

	/** The Constant TABLE_KEY. 对应vm模板中的table变量*/
	protected static final String TABLE_KEY = "table";
	
	/** The Constant STRING_UTILS_KEY.对应vm模板中的strings变量 */ 
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
	
	
	/** bom与java类型转换map. */
	protected static final String TYPE_MAP = "typeMap";
	
	/** The root. */
	private static String root;
	
	/** The properties. */
	private static Properties properties = new Properties();
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
	 * Instantiates a new code generator.
	 */
	private VelocityCodeGenerator(){} 
	
	/**
	 * Gets the single instance of CodeGenerator.
	 *
	 * @author mqfdy
	 * @return single instance of CodeGenerator
	 * @Date 2018-9-19 9:46:16
	 */
	public IGenerator getInstance(){
		return codeGenerator;
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
			VelocityContext context = new VelocityContext();
			initContext(context,material.getVelocityMap());
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
	
	/**
	 * 初始化VelocityContext对象，填充数据，子类可以通过重写此方法来完成自己的填充数据的逻辑
	 * 但是推荐子类重写getSourceMap（）方法来完成填充自己数据的目的.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param map
	 *            the map
	 * @Date 2018-9-3 11:38:38
	 */
	protected void initContext(VelocityContext context,Map<String,Object> map) {
		context.put(STRING_UTILS_KEY, strings);
		context.put(NOW_KEY, DateTimeUtil.date2String(new Date()));
		context.put(AUTHOR_KEY, System.getProperty("user.name"));
		context.put(D_QUOTE_KEY, D_QUOTE_VALUE);
		context.put(DOLLAR_KEY, DOLLAR_VALUE);
		context.put(LINE_SEPARATOR,"");
		TypeMap.initialize();
		
		context.put(TYPE_MAP,TypeMap.getPropertyToJavaMap());
		context.put(LINE_SEPARATOR,"");
		for (Entry<String, Object> entry : map.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void generate() throws CodeGenerationException {
		
		
	}

	@Override
	public boolean isTargetFileExist() {
		return new File(getOutputFilePath()).exists();
	}

	@Override
	public String getOutputFilePath() {
		return null;
	}

	@Override
	public String getFileName() {
		
		return null;
	}

	@Override
	public boolean isGenerate() {
		
		return false;
	}

	@Override
	public void setGenerate(boolean isGenerate) {
		
	}
}
