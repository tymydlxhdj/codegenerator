package com.mqfdy.code.springboot.ui.wizards;

import java.io.File;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.springboot.core.MicroProjectPlugin;

/**
 * This class provides the labels for the Springboot project tree.
 * @author lenovo
 */
public class ProjectTreeLabelProvider extends LabelProvider implements ILabelProvider {

	private final boolean USE_TRANSPARANT_ICONS;

	/**
	 * Constructs a FileTreeLabelProvider
	 * @param useTransparantIcons Determines whether transparant icons will be used when non-leaf project is already imported in the workspace.
	 */
	public ProjectTreeLabelProvider(boolean useTransparantIcons) {
		this.USE_TRANSPARANT_ICONS = useTransparantIcons;
	}
	
	/**
	 * Gets the image to display for a node in the tree
	 * 
	 * @param arg
	 *            the node
	 * @return Image
	 */
	public Image getImage(Object arg) {
		if(arg != null){
			return MicroProjectPlugin.getDefault().getImageRegistry().get(MicroProjectPlugin.IMAGE_MULTIPROJECT_FOLDER);
		}
		return null;
	}

	/**
	 * Gets the text to display for a node in the tree
	 * 
	 * @param arg0
	 *            the node
	 * @return String
	 */
	public String getText(Object arg) {
		if(arg != null){
			if(arg instanceof File){
				File rf = (File) arg;
				return rf.getName();
			}
		}
		return null;
	}

	/**
	 * Returns whether changes to the specified property on the specified
	 * element would affect the label for the element
	 * 
	 * @param arg0
	 *            the element
	 * @param arg1
	 *            the property
	 * @return boolean
	 */
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	/**
	 * Checks if is use transparant icons.
	 *
	 * @author mqfdy
	 * @return true, if is use transparant icons
	 * @Date 2018-09-03 09:00
	 */
	public boolean isUSE_TRANSPARANT_ICONS() {
		return USE_TRANSPARANT_ICONS;
	}
	
}
