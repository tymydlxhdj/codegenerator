package com.mqfdy.code.shareModel.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelMessageTreeContentProvider.
 *
 * @author mqfdy
 */
public class ModelMessageTreeContentProvider implements ITreeContentProvider {
	
	/**
	 * Gets the children.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the children
	 * @Date 2018-09-03 09:00
	 */
	public Object[] getChildren(Object element) { 
		if(element instanceof JSONObject){
			JSONArray arr=((JSONObject)element).optJSONArray("childNodes");
			Object[] obj=new Object[arr.length()];
			for(int i=0;i<arr.length();i++){
				try {
					obj[i]=arr.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		    return obj;
		}
		return new Object[]{};
		
	}

	/**
	 * Gets the parent.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the parent
	 * @Date 2018-09-03 09:00
	 */
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * Checks for children.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasChildren(Object element) {
		if(element instanceof JSONObject){
			try {
				if(((JSONObject)element).has("hasChildren")&&"true".equalsIgnoreCase(((JSONObject)element).getString("hasChildren"))){
					return true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 
	 */
	public void dispose() {
		//销毁
	}

	/**
	 * Input changed.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param oldInput
	 *            the old input
	 * @param newInput
	 *            the new input
	 * @Date 2018-09-03 09:00
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
		
	}

	/**
	 * Gets the elements.
	 *
	 * @author mqfdy
	 * @param inputElement
	 *            the input element
	 * @return the elements
	 * @Date 2018-09-03 09:00
	 */
	public Object[] getElements(Object inputElement) {
		// 打印出树的输入信息，通常用户可以通过输入信息构建树
					if(inputElement instanceof List){
						if(inputElement!=null)
						return ((List) inputElement).toArray();
					}
					return new Object[]{};
	}	
	
}
