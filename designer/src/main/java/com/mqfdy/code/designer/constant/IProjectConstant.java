package com.mqfdy.code.designer.constant;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Interface IProjectConstant.
 *
 * @author mqfdy
 */
public interface IProjectConstant {
	
	/** The Constant BOM_NATURE_ID. */
	//BOM项目标识ID
	public static final String BOM_NATURE_ID = "com.mqfdy.bom.project.nature";
	
	/** The Constant BOM_DATASOURCE_XML_RALATIVE_PATH. */
	//bom项目的datasource.xml相对路径
	public static final String BOM_DATASOURCE_XML_RALATIVE_PATH = File.separator + "src" + File.separator + "configs" + File.separator + "datasource.xml";
}
