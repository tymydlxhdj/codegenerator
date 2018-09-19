package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 编辑器类型.
 *
 * @author mqfdy
 */
public enum EditorType {
	
	/** The Text editor. */
	TextEditor("TextEditor","单行文本",false,"文本域"),
	
	/** The Mult text editor. */
	MultTextEditor("MultTextEditor","多行文本",false,"文本域"),
	
	/** The Password text editor. */
	PasswordTextEditor("PasswordTextEditor","密码框",false,"文本域"),
	
	/** The Number editor. */
	NumberEditor("NumberEditor","数字输入框",false,"文本域"),
	
	/** The Rich text editor. */
	RichTextEditor("RichTextEditor","富文本框",false,"文本域"),
	
	/** The Date time editor. */
	DateTimeEditor("DateTimeEditor","日期时间编辑器",false,"文本域"),
	
	/** The Date editor. */
	DateEditor("DateEditor","日期编辑器",false,"文本域"),
	
	/** The Time editor. */
	TimeEditor("TimeEditor","时间编辑器",false,"文本域"),
	

	/** The Combo box. */
	ComboBox("ComboBox","下拉框",true,"选择类"),
	
	/** The Check combo box. */
	CheckComboBox("CheckComboBox","多选下拉框",true,"选择类"),
	
	/** The List editor. */
	ListEditor("ListEditor","列表",true,"选择类"),
	
	/** The Check list editor. */
	CheckListEditor("CheckListEditor","多选列表",true,"选择类"),

	/** The Drop down tree editor. */
	DropDownTreeEditor("DropDownTreeEditor","下拉树",false,"选择类"),
	
	/** The Drop down check box tree editor. */
	DropDownCheckBoxTreeEditor("DropDownCheckBoxTreeEditor","多选树",false,"选择类"),
	
	/** The Drop down grid editor. */
	DropDownGridEditor("DropDownGridEditor","下拉表格",true,"选择类"),

//	CheckBoxListEditor("CheckBoxListEditor","多选列表",true,"选择类"),
//	CheckBoxDropDownEditor("CheckBoxDropDownEditor","多选下拉列表",true,"选择类"),

	/** The Check editor. */
CheckEditor("CheckEditor","复选框",true,"选择类"),
	
	/** The Radio editor. */
	RadioEditor("RadioEditor","单选框",true,"选择类"),
	
	/** The File editor. */
	FileEditor("FileEditor","文件选择器",false,"其他"),
	
	/** The Lable editor. */
	LableEditor("LableEditor","标签编辑器",false,"其他"),
	
	/** The Link editor. */
	LinkEditor("LinkEditor","链接编辑器",false,"其他"),
	
	/** The Custom editor. */
	CustomEditor("CustomEditor","自定义编辑器",false,"其他"),
	
	/** The Auto complete editor. */
	AutoCompleteEditor("AutoCompleteEditor","自动完成编辑器",false,"其他");
	
	//SignBox("SignBox","签名控件","resource/images/newBusinessClass_wiz.png",false,"文本域"),
	//IPAddressBox("IPAddressBox","IP地址输入框","resource/images/newBusinessClass_wiz.png",false,"文本域"),
	
	//ImageChoice("ImageChoice","图片选择器","resource/images/newBusinessClass_wiz.png",false,"其他"),
	//ObjectPropertiesPicker("ObjectPropertiesPicker","对象属性选择器","resource/images/newBusinessClass_wiz.png",false,"其他"),
	//UserPicker("UserPicker","人员选择器","resource/images/newBusinessClass_wiz.png",false,"其他"),
	//DepartmentPicker("DepartmentPicker","部门选择器","resource/images/newBusinessClass_wiz.png",false,"其他");
	
	/** The value. */
	private String value;
	
	/** The display value. */
	private String displayValue;
	
	/** The image path. */
	private String imagePath;
	
	/** The group. */
	private String group;
	
	/** The has data. */
	private boolean hasData;
	
	/**
	 * Instantiates a new editor type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 * @param imagePath
	 *            the image path
	 * @param hasData
	 *            the has data
	 * @param group
	 *            the group
	 */
	EditorType(String value,String displayValue,String imagePath,boolean hasData,String group){
		this.value = value;
		this.displayValue = displayValue;
		this.imagePath = imagePath;
		this.group = group;
		this.hasData = hasData;
	}

	/**
	 * Instantiates a new editor type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 * @param hasData
	 *            the has data
	 * @param group
	 *            the group
	 */
	EditorType(String value,String displayValue,boolean hasData,String group){
		this.value = value;
		this.displayValue = displayValue;
		this.group = group;
		this.hasData = hasData;
	}
	
	
	/**
	 * Gets the editor types.
	 *
	 * @author mqfdy
	 * @return the editor types
	 * @Date 2018-09-03 09:00
	 */
	public static List<EditorType> getEditorTypes(){
    	List<EditorType> list=new ArrayList<EditorType>();
    	for(int i = 0; i< EditorType.values().length; i++){
    		list.add(EditorType.values()[i]);
    	}
    	return list;
    }
	
	/**
	 * Gets the editor type groups.
	 *
	 * @author mqfdy
	 * @return the editor type groups
	 * @Date 2018-09-03 09:00
	 */
	public static List<String> getEditorTypeGroups(){
		List<String> groups = new ArrayList<String>();
		groups.add("文本域");
		groups.add("选择类");
		//groups.add("其他");
		return groups;
	}
	
	/**
	 * Gets the editor types.
	 *
	 * @author mqfdy
	 * @param group
	 *            the group
	 * @return the editor types
	 * @Date 2018-09-03 09:00
	 */
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

    
    /**
	 * Gets the editor types string.
	 *
	 * @author mqfdy
	 * @return the editor types string
	 * @Date 2018-09-03 09:00
	 */
    public static String[] getEditorTypesString(){
    	List<EditorType> list= getEditorTypes();
    	String[] s = new String[list.size()];
    	for(int i = 0;i< list.size();i++){
    		s[i] = list.get(i).displayValue;
    	}
    	return s;
    }
    
    /**
	 * Gets the editor type.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the editor type
	 * @Date 2018-09-03 09:00
	 */
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
    
    /**
	 * Gets the editor type by value.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the editor type by value
	 * @Date 2018-09-03 09:00
	 */
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
    
    /**
	 * Gets the value.
	 *
	 * @author mqfdy
	 * @return the value
	 * @Date 2018-09-03 09:00
	 */
    public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @author mqfdy
	 * @param value
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the display value.
	 *
	 * @author mqfdy
	 * @return the display value
	 * @Date 2018-09-03 09:00
	 */
	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * Sets the display value.
	 *
	 * @author mqfdy
	 * @param displayValue
	 *            the new display value
	 * @Date 2018-09-03 09:00
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	
	/**
	 * Gets the image path.
	 *
	 * @author mqfdy
	 * @return the image path
	 * @Date 2018-09-03 09:00
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Sets the image path.
	 *
	 * @author mqfdy
	 * @param imagePath
	 *            the new image path
	 * @Date 2018-09-03 09:00
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

 

	/**
	 * Checks if is checks for data.
	 *
	 * @author mqfdy
	 * @return true, if is checks for data
	 * @Date 2018-09-03 09:00
	 */
	public boolean isHasData() {
		return hasData;
	}



	/**
	 * Gets the group.
	 *
	 * @author mqfdy
	 * @return the group
	 * @Date 2018-09-03 09:00
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @author mqfdy
	 * @param group
	 *            the new group
	 * @Date 2018-09-03 09:00
	 */
	public void setGroup(String group) {
		this.group = group;
	}
}
