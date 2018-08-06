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

/**
 * @author mqfdy
 *
 */
public class ModelMessageTreeContentProvider implements ITreeContentProvider {
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

	public Object getParent(Object element) {
		return null;
	}

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

	public void dispose() {
		//销毁
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
		
	}

	public Object[] getElements(Object inputElement) {
		// 打印出树的输入信息，通常用户可以通过输入信息构建树
					if(inputElement instanceof List){
						if(inputElement!=null)
						return ((List) inputElement).toArray();
					}
					return new Object[]{};
	}	
	
}
