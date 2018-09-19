package com.mqfdy.code.springboot.core.datasource;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.mqfdy.code.springboot.core.MicroProjectPlugin;


// TODO: Auto-generated Javadoc
/**
 *新建Springboot项目向导第一页的项目类型
 * @author mqfdy
 */
public class SampleProjectRegistry {
	
	/** The serverpath. */
	public static String serverpath = null; 
	
	private static final FilenameFilter IGNORED_NAMES = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".zip");
		}
	};

	private static SampleProjectRegistry instance = null;
	
	/**
	 * Gets the single instance of SampleProjectRegistry.
	 *
	 * @author mqfdy
	 * @return single instance of SampleProjectRegistry
	 * @Date 2018-09-03 09:00
	 */
	public static synchronized SampleProjectRegistry getInstance() {
		instance = null;
		if (instance==null) {
			instance = new SampleProjectRegistry();
		}
		return instance;
	}
	
	private SampleProjectRegistry() {
	}
	
	private ArrayList<SampleProject> samples = null; //Lazy initialised.
	
	/**
	 * Gets the all.
	 *
	 * @author mqfdy
	 * @return the all
	 * @Date 2018-09-03 09:00
	 */
	public synchronized List<SampleProject> getAll() {
		if (samples==null) {
			samples = new ArrayList<SampleProject>();
			registerLocalSamples();
		}
		return samples;
	}

	private void registerLocalSamples() {
		File localSamplesDir = getLocalSamplesDir();
		File[] projects = localSamplesDir.listFiles(IGNORED_NAMES);
		for (File project : projects) {
			register(new LocalSample(project.getName(), project));
		}
	}

	private File getLocalSamplesDir() {
		Bundle bundle = MicroProjectPlugin.context.getBundle();
		try {
			File bundleFile = FileLocator.getBundleFile(bundle);
			if (bundleFile != null && bundleFile.exists() && bundleFile.isDirectory()) {
				File samplesDir = new File(bundleFile, MicroProjectPlugin.RESOURCESPATH);
				File continer = new File(bundleFile, MicroProjectPlugin.CONTINERPATH);
				if(continer.exists()){
					serverpath = continer.getPath().replaceAll("\\\\", "/");
				}
				if (samplesDir.isDirectory()) {
					return samplesDir;
				} else {
					MicroProjectPlugin.log("Directory 'rescourse' not found in plugin "+MicroProjectPlugin.TOOL_PLUGIN_ID);
				}
			} else {
				MicroProjectPlugin.log("Couldn't access the plugin "+MicroProjectPlugin.TOOL_PLUGIN_ID+" as a directory. Maybe it is not installed as an 'exploded' bundle?");
			}
		} catch (IOException e) {
			MicroProjectPlugin.log(e);
		}
		return null;
	}

	private void register(SampleProject... toRegister) {
		for (SampleProject s : toRegister) {
			samples.add(s);
		}
	}

	/**
	 * Gets the.
	 *
	 * @author mqfdy
	 * @param sampleProjectName
	 *            the sample project name
	 * @return the sample project
	 * @Date 2018-09-03 09:00
	 */
	public SampleProject get(String sampleProjectName) {
		for (SampleProject candidate : getAll()) {
			if (candidate.getName().equals(sampleProjectName)) {
				return candidate;
			}
		}
		return null;
	}
	
}
