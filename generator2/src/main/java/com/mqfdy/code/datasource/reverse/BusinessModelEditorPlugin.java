package com.mqfdy.code.datasource.reverse;

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

import com.mqfdy.code.datasource.model.ImageKeys;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessModelEditorPlugin.
 *
 * @title:模型插件主类Activator
 */
public class BusinessModelEditorPlugin extends AbstractUIPlugin {

	/** The Constant FULL_CONSISTENCY_CONST. */
	public static final int FULL_CONSISTENCY_CONST = 1;
	
	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "com.sgcc.uap.mdd.designer";

	/** The plugin. */
	// The shared instance.
	private static BusinessModelEditorPlugin plugin;

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
	public BusinessModelEditorPlugin() {
		plugin = this;
	}

	/**
	 * Gets the default.
	 *
	 * @author mqfdy
	 * @return the default
	 * @Date 2018-09-03 09:00
	 */
	public static BusinessModelEditorPlugin getDefault() {
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
	 * @Date 2018-9-3 11:38:31
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Method returns the plugin ID.
	 *
	 * @author mqfdy
	 * @return plgun ID
	 * @Date 2018-9-3 11:38:31
	 */
	public String getID() {
		return PLUGIN_ID;
	}


	/**
	 * {@inheritDoc}.
	 *
	 * @author mqfdy
	 * @param reg
	 *            the reg
	 * @Date 2018-9-3 11:38:31
	 */
	@Override
	protected void initializeImageRegistry(final ImageRegistry reg) {

		// add images here that are frequently used in the plugin
		reg.put(ImageKeys.IMG_DIALOG_PACKAGE,
				getImageDescriptor(ImageKeys.IMG_DIALOG_PACKAGE));
		reg.put(ImageKeys.IMG_MODEL_OPER_NEWELEMENT,
				getImageDescriptor(ImageKeys.IMG_MODEL_OPER_NEWELEMENT));
		reg.put(ImageKeys.IMG_MODEL_OPER_OPENREPOS,
				getImageDescriptor(ImageKeys.IMG_MODEL_OPER_OPENREPOS));
		reg.put(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION));

		reg.put(ImageKeys.IMG_MODEL_TYPE_INHERITANCE,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_INHERITANCE));
		reg.put(ImageKeys.IMG_MODEL_TYPE_LINK,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_LINK));
		reg.put(ImageKeys.IMG_MODEL_TYPE_ANNOTATION,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_ANNOTATION));

		reg.put(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2MULT,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2MULT));
		reg.put(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2ONE,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2ONE));
		reg.put(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT));
		reg.put(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2ONE,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2ONE));

		reg.put(ImageKeys.IMG_MODEL_TYPE_PROPERTY,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_PROPERTY));
		reg.put(ImageKeys.IMG_MODEL_TYPE_DTO,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_DTO));
		reg.put(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
		reg.put(ImageKeys.IMG_MODEL_TYPE_ENUMERATION,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_ENUMERATION));
		reg.put(ImageKeys.IMG_MODEL_TYPE_DIAGRAM,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_DIAGRAM));
		reg.put(ImageKeys.IMG_MODEL_TYPE_LOCAL,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_LOCAL));
		reg.put(ImageKeys.IMG_MODEL_TYPE_PACKAGE,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_PACKAGE));

		reg.put(ImageKeys.IMG_MODEL_TYPE_REPOSITORY,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_REPOSITORY));
		reg.put(ImageKeys.IMG_MODEL_TYPE_SOLIDIFYPACKAGE,
				getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_SOLIDIFYPACKAGE));
		reg.put(ImageKeys.IMG_OBJECT_OPER_ADD,
				getImageDescriptor(ImageKeys.IMG_OBJECT_OPER_ADD));
		reg.put(ImageKeys.IMG_OBJECT_OPER_DELETE,
				getImageDescriptor(ImageKeys.IMG_OBJECT_OPER_DELETE));

		reg.put(ImageKeys.IMG_NEW_FILE,
				getImageDescriptor(ImageKeys.IMG_NEW_FILE));
		reg.put(ImageKeys.IMG_DELETE, getImageDescriptor(ImageKeys.IMG_DELETE));
		reg.put(ImageKeys.IMG_SAVE, getImageDescriptor(ImageKeys.IMG_SAVE));
		reg.put(ImageKeys.IMG_TOP, getImageDescriptor(ImageKeys.IMG_TOP));
		reg.put(ImageKeys.IMG_UP, getImageDescriptor(ImageKeys.IMG_UP));
		reg.put(ImageKeys.IMG_DOWN, getImageDescriptor(ImageKeys.IMG_DOWN));
		reg.put(ImageKeys.IMG_BOTTOM, getImageDescriptor(ImageKeys.IMG_BOTTOM));
	}

	/**
	 * {@inheritDoc}.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @throws Exception
	 *             the exception
	 * @Date 2018-9-3 11:38:31
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * {@inheritDoc}.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @throws Exception
	 *             the exception
	 * @Date 2018-9-3 11:38:31
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
	public static Object getAdapter(Object sourceObject, Class<?> adapter, boolean activatePlugins) {
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
