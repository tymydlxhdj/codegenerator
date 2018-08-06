package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑器类型
 * @author mqfdy
 *
 */
public enum EditorType {
	TextEditor("TextEditor","单行文本",false,"文本域"),
	MultTextEditor("MultTextEditor","多行文本",false,"文本域"),
	PasswordTextEditor("PasswordTextEditor","密码框",false,"文本域"),
	NumberEditor("NumberEditor","数字输入框",false,"文本域"),
	RichTextEditor("RichTextEditor","富文本框",false,"文本域"),
	
	DateTimeEditor("DateTimeEditor","日期时间编辑器",false,"文本域"),
	DateEditor("DateEditor","日期编辑器",false,"文本域"),
	TimeEditor("TimeEditor","时间编辑器",false,"文本域"),
	

	ComboBox("ComboBox","下拉框",true,"选择类"),
	CheckComboBox("CheckComboBox","多选下拉框",true,"选择类"),
	ListEditor("ListEditor","列表",true,"选择类"),
	CheckListEditor("CheckListEditor","多选列表",true,"选择类"),

	DropDownTreeEditor("DropDownTreeEditor","下拉树",false,"选择类"),
	DropDownCheckBoxTreeEditor("DropDownCheckBoxTreeEditor","多选树",false,"选择类"),
	DropDownGridEditor("DropDownGridEditor","下拉表格",true,"选择类"),

//	CheckBoxListEditor("CheckBoxListEditor","多选列表",true,"选择类"),
//	CheckBoxDropDownEditor("CheckBoxDropDownEditor","多选下拉列表",true,"选择类"),

	CheckEditor("CheckEditor","复选框",true,"选择类"),
	RadioEditor("RadioEditor","单选框",true,"选择类"),
	
	FileEditor("FileEditor","文件选择器",false,"其他"),
	LableEditor("LableEditor","标签编辑器",false,"其他"),
	LinkEditor("LinkEditor","链接编辑器",false,"其他"),
	CustomEditor("CustomEditor","自定义编辑器",false,"其他"),
	AutoCompleteEditor("AutoCompleteEditor","自动完成编辑器",false,"其他");
	
	//SignBox("SignBox","签名控件","resource/images/newBusinessClass_wiz.png",false,"文本域"),
	//IPAddressBox("IPAddressBox","IP地址输入框","resource/images/newBusinessClass_wiz.png",false,"文本域"),
	
	//ImageChoice("ImageChoice","图片选择器","resource/images/newBusinessClass_wiz.png",false,"其他"),
	//ObjectPropertiesPicker("ObjectPropertiesPicker","对象属性选择器","resource/images/newBusinessClass_wiz.png",false,"其他"),
	//UserPicker("UserPicker","人员选择器","resource/images/newBusinessClass_wiz.png",false,"其他"),
	//DepartmentPicker("DepartmentPicker","部门选择器","resource/images/newBusinessClass_wiz.png",false,"其他");
	
	private String value;
	
	private String displayValue;
	
	private String imagePath;
	
	private String group;
	
	private boolean hasData;
	
	EditorType(String value,String displayValue,String imagePath,boolean hasData,String group){
		this.value = value;
		this.displayValue = displayValue;
		this.imagePath = imagePath;
		this.group = group;
		this.hasData = hasData;
	}

	EditorType(String value,String displayValue,boolean hasData,String group){
		this.value = value;
		this.displayValue = displayValue;
		this.group = group;
		this.hasData = hasData;
	}
	
	
	public static List<EditorType> getEditorTypes(){
    	List<EditorType> list=new ArrayList<EditorType>();
    	for(int i = 0; i< EditorType.values().length; i++){
    		list.add(EditorType.values()[i]);
    	}
    	return list;
    }
	
	public static List<String> getEditorTypeGroups(){
		List<String> groups = new ArrayList<String>();
		groups.add("文本域");
		groups.add("选择类");
		//groups.add("其他");
		return groups;
	}
	
	public static List<EditorType> getEditorTypes(String group){
		List<EditorType> result = new ArrayList<EditorType>(); 
		List<EditorType> list= getEditorTypes();
		for(int i = 0;i< list.size();i++){
			if(list.get(i).getGroup().equals(group)){
				result.add(list.get(i));
			}
    	}
    	return result;
	}

    
    public static String[] getEditorTypesString(){
    	List<EditorType> list= getEditorTypes();
    	String[] s = new String[list.size()];
    	for(int i = 0;i< list.size();i++){
    		s[i] = list.get(i).displayValue;
    	}
    	return s;
    }
    
    public static EditorType getEditorType(String value) {
    	if(value == null){
    		return null; 
    	}
        for (EditorType t : EditorType.values()) {
            if (value.equals(t.getValue())) {
                return t;
            }
        }
        return null;
    }
    public static EditorType getEditorTypeByValue(String value) {
    	if(value == null){
    		return null; 
    	}
        for (EditorType t : EditorType.values()) {
            if (value.equals(t.getValue())) {
                return t;
            }
        }
        return null;
    }
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

 

	public boolean isHasData() {
		return hasData;
	}



	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
