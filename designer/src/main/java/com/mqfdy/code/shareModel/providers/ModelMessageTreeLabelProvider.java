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

/**
 * @author mqfdy
 *
 */
public class ModelMessageTreeLabelProvider implements ILabelProvider {
	public BusinessClass rootBusinessClass;
	
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

	public void addListener(ILabelProviderListener listener) {
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	} 
			
}
