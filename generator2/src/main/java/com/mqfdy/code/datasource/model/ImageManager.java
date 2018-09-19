package com.mqfdy.code.datasource.model;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.datasource.reverse.BusinessModelEditorPlugin;

// TODO: Auto-generated Javadoc
/**
 * 图片管理器类(单例)，负责图片的管理、销毁.
 *
 * @author mqfdy
 */
public class ImageManager {
	
	/** The Constant imageMap. */
	private final static HashMap<ImageDescriptor, Image> imageMap = new HashMap<ImageDescriptor, Image>();
	
	/** The instance. */
	private static ImageManager instance;

	/**
	 * Gets the single instance of ImageManager.
	 *
	 * @author mqfdy
	 * @return single instance of ImageManager
	 * @Date 2018-09-03 09:00
	 */
	public static ImageManager getInstance() {
		if (instance == null)
			instance = new ImageManager();
		return instance;
	}

	/**
	 * 获取图片.
	 *
	 * @author mqfdy
	 * @param descriptor
	 *            the descriptor
	 * @return the image
	 * @Date 2018-09-03 09:00
	 */
	public Image getImage(ImageDescriptor descriptor) {
		if (descriptor == null)
			return null;
		Image image = (Image) imageMap.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageMap.put(descriptor, image);
		}
		return image;
	}

	/**
	 * 获取图片.
	 *
	 * @author mqfdy
	 * @param imageKey
	 *            the image key
	 * @return the image
	 * @Date 2018-09-03 09:00
	 */
	public Image getImage(String imageKey) {
		ImageDescriptor descriptor = BusinessModelEditorPlugin
				.getImageDescriptor(imageKey);
		return getImage(descriptor);
	}

	/**
	 * 获取图片描述.
	 *
	 * @author mqfdy
	 * @param imageKey
	 *            the image key
	 * @return the image descriptor
	 * @Date 2018-09-03 09:00
	 */
	public ImageDescriptor getImageDescriptor(String imageKey) {
		ImageDescriptor descriptor = BusinessModelEditorPlugin
				.getImageDescriptor(imageKey);
		return descriptor;
	}

	/**
	 * 销毁图片.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:30
	 */
	public void dispose() {
		Iterator<Image> iter = imageMap.values().iterator();
		while (iter.hasNext())
			((Image) iter.next()).dispose();
		imageMap.clear();
	}
}
