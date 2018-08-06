package com.mqfdy.code.generator;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * 
 * @author mqfdy
 */
public class GeneratorPlugin extends Plugin {

	/**
	 * natureId
	 */
	public static final String PLUGIN_ID = "com.mqfdy.code.generator";

	/**
	 * plugin
	 */
	private static GeneratorPlugin plugin;

	public GeneratorPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static GeneratorPlugin getDefault() {
		return plugin;
	}
}
