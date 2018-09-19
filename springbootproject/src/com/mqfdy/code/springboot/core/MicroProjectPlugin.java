package com.mqfdy.code.springboot.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

import com.mqfdy.code.springboot.core.util.ExceptionUtil;
import com.mqfdy.code.springboot.dbs.ConnectionProfileRepository;
import com.mqfdy.code.springboot.dbs.internal.DTPConnectionProfileRepository;



// TODO: Auto-generated Javadoc
/**
 * The activator class controls the plug-in life cycle
 * @author Kris De Volder
 */
public class MicroProjectPlugin extends AbstractUIPlugin {

	/** The Constant PLUGIN_ID. */
	// The plug-in ID
	public static final String PLUGIN_ID = "com.mqfdy.code.springboot.project"; 
	
	/** The Constant IMAGE_TARGET. */
	public static final String IMAGE_TARGET = "target"; 
	
	/** The Constant IMAGE_PROJECT_FOLDER. */
	public static final String IMAGE_PROJECT_FOLDER = "projectFolder"; 
	
	/** The Constant IMAGE_MULTIPROJECT_FOLDER. */
	public static final String IMAGE_MULTIPROJECT_FOLDER = "multiProjectFolder"; 
	
	/** The Constant IMAGE_MULTIPROJECT_FOLDER_DISABLED. */
	public static final String IMAGE_MULTIPROJECT_FOLDER_DISABLED = "multiProjectFolderDisabled"; 
	
	/** The Constant IMAGE_LAUNCH. */
	public static final String IMAGE_LAUNCH = "launch"; 
	
	/** The Constant TOOL_PLUGIN_ID. */
	public static final String TOOL_PLUGIN_ID = "com.mqfdy.code.springboot.project";

	/** The Constant RESOURCESPATH. */
	public static final String RESOURCESPATH = "template";
	
	/** The Constant CONTINERPATH. */
	public static final String CONTINERPATH = "lib";
	
	
	private static final Map<String, String> IMAGE_DESCRIPTOR_MAP = new HashMap<String, String>();
	
	/** The context. */
	public static BundleContext context;
	
	private DTPConnectionProfileRepository connectionProfileRepository;

	private IPreferenceStore preferenceStore = null;


	private static MicroProjectPlugin INSTANCE; // sorta-final

	static {
		IMAGE_DESCRIPTOR_MAP.put(IMAGE_TARGET, "icons/target.gif");
		IMAGE_DESCRIPTOR_MAP.put(IMAGE_PROJECT_FOLDER, "icons/microproject-proj-folder.png");
		IMAGE_DESCRIPTOR_MAP.put(IMAGE_MULTIPROJECT_FOLDER, "icons/microproject-multiproj-folder.png");
		IMAGE_DESCRIPTOR_MAP.put(IMAGE_MULTIPROJECT_FOLDER_DISABLED, "icons/microproject-multiproj-folder-disabled.png");
		IMAGE_DESCRIPTOR_MAP.put(IMAGE_LAUNCH, "icons/microproject-launch.png");
	}

	// The shared instance
	private static MicroProjectPlugin plugin;
	
	private static MicroProjectManager projectManager = new MicroProjectManager();
	
	/**
	 * Return the singleton JPT DB plug-in.
	 *
	 * @author mqfdy
	 * @return the micro project plugin
	 * @Date 2018-09-03 09:00
	 */
	public static MicroProjectPlugin instance() {
		return INSTANCE;
	}


	/**
	 * The constructor
	 */
	public MicroProjectPlugin() {
		super();
		INSTANCE = this;
	}

	public IPreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
			preferenceStore = new ScopedPreferenceStore(new InstanceScope(),
					PLUGIN_ID);
		}
		return preferenceStore;

	}

	/**
	 * Gets the connection profile repository.
	 *
	 * @author mqfdy
	 * @return the connection profile repository
	 * @Date 2018-09-03 09:00
	 */
	public ConnectionProfileRepository getConnectionProfileRepository() {
		return this.connectionProfileRepository;
	}
	
	/**
	 * Log message.
	 *
	 * @author mqfdy
	 * @param severity
	 *            the severity
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
	public static void logMessage(int severity, String message) {
		log(new Status(severity, PLUGIN_ID, message));
    }
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		MicroProjectPlugin.context = context;
		plugin = this;
		this.connectionProfileRepository = DTPConnectionProfileRepository
				.instance();
		this.connectionProfileRepository.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		this.connectionProfileRepository.stop();
		this.connectionProfileRepository = null;
		INSTANCE = null;
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static MicroProjectPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the active workbench shell or <code>null</code> if none
	 * 
	 * @return the active workbench shell or <code>null</code> if none
	 */
	public static Shell getActiveWorkbenchShell() {
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		if (window != null) {
			return window.getShell();
		}
		return null;
	}	

	/**
	 * Returns the active workbench window
	 * 
	 * @return the active workbench window
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);		
		for (Map.Entry<String, String> entry : IMAGE_DESCRIPTOR_MAP.entrySet()) {
	        URL url = FileLocator.find(plugin.getBundle(), new Path(entry.getValue()), null);
	        ImageDescriptor desc = ImageDescriptor.createFromURL(url);
	        reg.put(entry.getKey(), desc);
		}
	}
	
	

	/**
	 * Gets the project manager.
	 *
	 * @author mqfdy
	 * @return the project manager
	 * @Date 2018-09-03 09:00
	 */
	public static MicroProjectManager getProjectManager() {
		return projectManager;
	}

	/**
	 * Gets GradleProject associated with given IProject, if it exists. May
	 * return null if the project itself doesn't exist (has no location) or the
	 * GradleProject instance associated with thay project wasn't created yet.
	 *
	 * @author mqfdy
	 * @param project
	 *            the project
	 * @return the gradle project
	 * @Date 2018-09-03 09:00
	 */
	public static MicroProject getGradleProject(IProject project) {
		IPath location = project.getLocation();
		if (location!=null) {
			return projectManager.get(location.toFile());
		}
		return null;
	}

	/**
	 * Warn.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
	public static void warn(String message) {
		log(new Status(IStatus.WARNING, PLUGIN_ID, message));
	}
	
	/**
	 * Gets the resource file.
	 *
	 * @author mqfdy
	 * @param relatepath
	 *            the relatepath
	 * @return file
	 * @Date 2018-09-03 09:00
	 */
	public static String getResourceFile(String relatepath) {
		URL url = context.getBundle().getEntry(relatepath);
		String find = null;
		try {
			if (url != null) {
				find = FileLocator.toFileURL(url).getPath();
				File file = new File(find);
				if (file.exists())
					return file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public static void log(Throwable e) {
		IStatus s = ExceptionUtil.status(IStatus.ERROR, e);
		log(s);
	}
	
	/**
	 * Log info.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public static void logInfo(Throwable e) {
		IStatus s = ExceptionUtil.status(IStatus.INFO, e);
		log(s);
	}

	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @Date 2018-09-03 09:00
	 */
	public static void log(IStatus s) {
		plugin.getLog().log(s);
	}
	
	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param msg
	 *            the msg
	 * @Date 2018-09-03 09:00
	 */
	public static void log(String msg) {
		log(ExceptionUtil.coreException(msg));
	}
	
	
}
