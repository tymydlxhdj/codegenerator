package com.mqfdy.code.springboot.core.generator;

import org.eclipse.osgi.util.NLS;
/**
 * 模板信息类
 * 
 * @author mqfdy
 *
 */
public class TemplateConstantMessages extends NLS {
	
	private static String BUNDLE_NAME = "com.mqfdy.code.springboot.core.generator.messages";
	
	public final static String GRADLE_PROJECT_TYPE="gradle";
	
	public final static String MAVEN_PROJECT_TYPE="maven";
	/**
	 * 启动类名称
	 */
	public static String application;
	/**
	 * 跨域配置类名称
	 */
	public static String corsconfig;
	
	/**
	 * build.gradle文件名称
	 */
	public static String build;	
	/**
	 * settings.gradle文件名称
	 */
	public static String settings;
	/**
	 * 启动类模板
	 */
	public static String template_application;
	/**
	 * 跨域配置类模板
	 */
	public static String template_corsconfig;
	/**
	 * build模板
	 */
	public static String template_build;
	/**
	 * settings模板
	 */
	public static String template_settings;
	
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, TemplateConstantMessages.class);
	}

}
