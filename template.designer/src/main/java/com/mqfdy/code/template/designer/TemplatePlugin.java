package com.mqfdy.code.template.designer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


// TODO: Auto-generated Javadoc
/**
 * The Class BusinessModelEditorPlugin.
 *
 * @title:模型插件主类Activator
 */
public class TemplatePlugin extends AbstractUIPlugin {

	/** The Constant FULL_CONSISTENCY_CONST. */
	public static final int FULL_CONSISTENCY_CONST = 1;
	
	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "com.mqfdy.code.template.designer";

	/** The plugin. */
	// The shared instance.
	private static TemplatePlugin plugin;

	/** The drag and drop support map. */
	private Map<String, Object> dragAndDropSupportMap;

	/**
	 * Gets the drag and drop support map.
	 *
	 * @author mqfdy
	 * @return the drag and drop support map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, Object> getDragAndDropSupportMap() {
		if (dragAndDropSupportMap == null) {
			dragAndDropSupportMap = new HashMap<String, Object>();
		}
		return dragAndDropSupportMap;
	}

	/**
	 * Instantiates a new business model editor plugin.
	 */
	public TemplatePlugin() {
		plugin = this;
	}

	/**
	 * Gets the default.
	 *
	 * @author mqfdy
	 * @return the default
	 * @Date 2018-09-03 09:00
	 */
	public static TemplatePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 *
	 * @author mqfdy
	 * @param path
	 *            the path
	 * @return the image descriptor
	 * @Date 2018-09-03 09:00
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Method returns the plugin ID.
	 *
	 * @author mqfdy
	 * @return plgun ID
	 * @Date 2018-09-03 09:00
	 */
	public String getID() {
		return PLUGIN_ID;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		super.stop(context);

		plugin = null;
	}

	/**
	 * Gets the active workbench window.
	 *
	 * @author mqfdy
	 * @return the active workbench window
	 * @Date 2018-09-03 09:00
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}

	/**
	 * Gets the preference string.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the preference string
	 * @Date 2018-09-03 09:00
	 */
	public static String getPreferenceString(final String id) {
		return getDefault().getPreferenceStore().getString(id);
	}

	/**
	 * 获得Shell.
	 *
	 * @author mqfdy
	 * @return the shell
	 * @Date 2018-09-03 09:00
	 */
	public static Shell getShell() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow()
				.getShell();
	}
	
	/**
	 * Gets the adapter.
	 *
	 * @author mqfdy
	 * @param sourceObject
	 *            the source object
	 * @param adapter
	 *            the adapter
	 * @param activatePlugins
	 *            the activate plugins
	 * @return the adapter
	 * @Date 2018-09-03 09:00
	 */
	public static Object getAdapter(Object sourceObject, Class adapter, boolean activatePlugins) {
        if (sourceObject == null) {
            return null;
        }
        if (adapter.isInstance(sourceObject)) {
            return sourceObject;
        }

        if (sourceObject instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) sourceObject;

            Object result = adaptable.getAdapter(adapter);
            if (result != null) {
                // Sanity-check
                return result;
            }
        } 
        
        if (!(sourceObject instanceof PlatformObject)) {
        	Object result;
        	if (activatePlugins) {
        		result = Platform.getAdapterManager().loadAdapter(sourceObject, adapter.getName());
        	} else {
        		result = Platform.getAdapterManager().getAdapter(sourceObject, adapter);
        	}
            if (result != null) {
                return result;
            }
        }

        return null;
    }

}
