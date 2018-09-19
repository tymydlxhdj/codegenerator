package com.mqfdy.code.shareModel.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelMessageTreeLabelProvider.
 *
 * @author mqfdy
 */
public class ModelMessageTreeLabelProvider implements ILabelProvider {
	
	/** The root business class. */
	public BusinessClass rootBusinessClass;
	
	/**
	 * Gets the image.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the image
	 * @Date 2018-09-03 09:00
	 */
	public Image getImage(Object element) {
		Image image = null;
		ImageManager manager = ImageManager.getInstance();
		 if(element instanceof JSONObject){
			try {
				if(((JSONObject)element).has("hasChildren")&&"true".equalsIgnoreCase(((JSONObject)element).getString("hasChildren"))){
					image = manager.getImage(ImageKeys.IMG_TREE_FOLDER); 
				 }else{
					 image = manager.getImage(ImageKeys.IMG_TREE_LEAF);
				 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		return image;
	}

	/**
	 * Gets the text.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the text
	 * @Date 2018-09-03 09:00
	 */
	public String getText(Object element) {
		 if(element instanceof JSONObject){
			try {
				return  ((JSONObject)element).getString("text");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }			

		return "";
	}

	/**
	 * Adds the listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addListener(ILabelProviderListener listener) {
		
	}

	/**
	 * 
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Checks if is label property.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return true, if is label property
	 * @Date 2018-09-03 09:00
	 */
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Removes the listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	} 
			
}
