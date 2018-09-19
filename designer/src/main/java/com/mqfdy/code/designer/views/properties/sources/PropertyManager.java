package com.mqfdy.code.designer.views.properties.sources;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * 属性控制器.
 *
 * @author mqfdy
 */
public class PropertyManager {

	/**
	 * Instantiates a new property manager.
	 */
	private PropertyManager() {
	}

	/** The maps. */
	private static Map<String, Map<Object, ModelProperty>> maps = new HashMap<String, Map<Object, ModelProperty>>();

	/**
	 * Gets the properties.
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the properties
	 * @Date 2018-09-03 09:00
	 */
	public static Map<Object, ModelProperty> getProperties(String className) {
		return maps.get(className);
	}

	/**
	 * Put properties.
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @param properties
	 *            the properties
	 * @Date 2018-09-03 09:00
	 */
	public static void putProperties(String className,
			Map<Object, ModelProperty> properties) {
		maps.put(className, properties);
	}

}
