/**
 * 
 */
package com.mqfdy.code.shareModel.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class DataGridLabelProvider.
 *
 * @author mqfdy
 */
public class DataGridLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/**
	 * Gets the column image.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return the column image
	 * @Date 2018-09-03 09:00
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		ImageManager manager = ImageManager.getInstance();
		if(element instanceof JSONObject) {
			JSONObject json = (JSONObject) element;			   
			   switch(columnIndex) {
			   case 1:
				try {
					if("0".equals(json.getString("modelType"))){
						return  manager.getImage(ImageKeys.IMG_MODEL_TYPE_OBJECTMODEL); 
					}else if("1".equals(json.getString("modelType"))){
						return  manager.getImage(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS); 
					}else{
						return  manager.getImage(ImageKeys.IMG_MODEL_TYPE_ENUMERATION); 
					}
				  } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	 }
		return null;
	}
	
	/**
	 * Gets the column text.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return the column text
	 * @Date 2018-09-03 09:00
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof JSONObject) {
			JSONObject json = (JSONObject) element;			   
			   switch(columnIndex) {
			   case 0:
				try {
					return json.getString("id");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   case 1:
					return "";			   
			   case 2:
			    try {
					return json.getString("modelName");
				} catch (JSONException e) {
					e.printStackTrace();
				}		   
			    
			   case 3:
			    try {
					return json.getString("desc");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			    
			   case 4:
				try {
					return json.getString("sharePerson");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			   case 5:
					try {
						return json.getString("createTime");
					} catch (JSONException e) {
						e.printStackTrace();
					}
			 }
	  }
		return "";
	
	}

}
