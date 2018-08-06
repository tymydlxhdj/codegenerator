package com.mqfdy.code.designer.views.properties.sources;

import java.util.HashMap;
import java.util.Map;

/**
 * 属性控制器
 * 
 * @author mqfdy
 * 
 */
public class PropertyManager {

	private PropertyManager() {
	}

	private static Map<String, Map<Object, ModelProperty>> maps = new HashMap<String, Map<Object, ModelProperty>>();

	public static Map<Object, ModelProperty> getProperties(String className) {
		return maps.get(className);
	}

	public static void putProperties(String className,
			Map<Object, ModelProperty> properties) {
		maps.put(className, properties);
	}

}
