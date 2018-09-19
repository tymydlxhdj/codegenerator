package com.mqfdy.code.generator;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneratorPlugin.
 *
 * @author mqfdy
 */
public class GeneratorPlugin extends Plugin {

	/** natureId. */
	public static final String PLUGIN_ID = "com.mqfdy.code.generator";

	/** plugin. */
	private static GeneratorPlugin plugin;

	/**
	 * Instantiates a new generator plugin.
	 */
	public GeneratorPlugin() {
	}

	/**
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 * @param context
	 * @throws Exception GeneratorPlugin
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 * @param context
	 * @throws Exception GeneratorPlugin
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Gets the default.
	 *
	 * @author mqfdy
	 * @return the default
	 * @Date 2018-09-03 09:00
	 */
	public static GeneratorPlugin getDefault() {
		return plugin;
	}
}
