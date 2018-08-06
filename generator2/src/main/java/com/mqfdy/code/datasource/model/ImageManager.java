package com.mqfdy.code.datasource.model;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.datasource.reverse.BusinessModelEditorPlugin;

/**
 * 图片管理器类(单例)，负责图片的管理、销毁
 * 
 * @author mqfdy
 * 
 */
public class ImageManager {
	private final static HashMap<ImageDescriptor, Image> imageMap = new HashMap<ImageDescriptor, Image>();
	private static ImageManager instance;

	public static ImageManager getInstance() {
		if (instance == null)
			instance = new ImageManager();
		return instance;
	}

	/**
	 * 获取图片
	 * 
	 * @param descriptor
	 * @return
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
	 * 获取图片
	 * 
	 * @param imageKey
	 * @return
	 */
	public Image getImage(String imageKey) {
		ImageDescriptor descriptor = BusinessModelEditorPlugin
				.getImageDescriptor(imageKey);
		return getImage(descriptor);
	}

	/**
	 * 获取图片描述
	 * 
	 * @param imageKey
	 * @return
	 */
	public ImageDescriptor getImageDescriptor(String imageKey) {
		ImageDescriptor descriptor = BusinessModelEditorPlugin
				.getImageDescriptor(imageKey);
		return descriptor;
	}

	/**
	 * 销毁图片
	 */
	public void dispose() {
		Iterator<Image> iter = imageMap.values().iterator();
		while (iter.hasNext())
			((Image) iter.next()).dispose();
		imageMap.clear();
	}
}
