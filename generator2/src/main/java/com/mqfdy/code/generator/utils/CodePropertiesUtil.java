/**
 * 
 */
package com.mqfdy.code.generator.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mqfdy.code.generator.exception.CodeGeneratorException;

// TODO: Auto-generated Javadoc
/**
 * 代码文件属性工具类.
 *
 * @author mqfdy
 */
public class CodePropertiesUtil {
	
	/** The prop. */
	private static Properties prop;
	
	/** The Constant FILE_PATH. */
	private final static String FILE_PATH = "/conf/message.properties";
	
	/** The Constant LINE_BREAK. */
	private final static String LINE_BREAK = "\n";
	
	/** The Constant IMPORT_END_SYMBOL. */
	private final static String IMPORT_END_SYMBOL = ";\n";
	
	/** The Constant SEMI. */
	private final static String SEMI = ";";
	static{
		prop = new Properties();
		InputStream fis = null;
		try {
			fis = CodePropertiesUtil.class.getResourceAsStream(FILE_PATH);
			prop.load(fis);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Instantiates a new code properties util.
	 */
	private CodePropertiesUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据key获取message属性配置文件内容.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String get(String key)  {
		return prop.getProperty(key);
	}
	
	/**
	 * 根据类名获取import 字符串.
	 *
	 * @author mqfdy
	 * @param ClassName
	 *            the class name
	 * @return the import
	 * @Date 2018-09-03 09:00
	 */
	public static String getImport(String ClassName) {
		String packageName = get("import."+ClassName);
		String importStr = "import " + packageName;
		if(!CodePropertiesValidatorUtil.validatePackageName(packageName)){
			throw new CodeGeneratorException("文件" + FILE_PATH + 
					"中“"+ ClassName + "”属性值为java文件中引用的类名或包"
							+ "名,包名不应含有特殊字符和java关键字！");
		}
		if(importStr != null && !importStr.endsWith(IMPORT_END_SYMBOL)){
			if(importStr.endsWith(SEMI)){
				importStr = importStr + LINE_BREAK;
			} else if(importStr.endsWith(LINE_BREAK)){
				importStr = importStr.replace(LINE_BREAK, IMPORT_END_SYMBOL);
			} else {
				importStr = importStr +IMPORT_END_SYMBOL;
			}
		}
		return importStr;
	}
	
	/**
	 * 根据类名获取校验类型import 字符串.
	 *
	 * @author mqfdy
	 * @param ClassName
	 *            the class name
	 * @return the validator type import
	 * @Date 2018-09-03 09:00
	 */
	public static String getValidatorTypeImport(String ClassName) {
		return getImport("ValidatorType."+ClassName);
	}

}
