package com.mqfdy.code.generator.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.datasource.utils.DateTimeUtil;
import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractBusinessClass.
 *
 * @author mqf
 */
public abstract class AbstractBusinessClass extends AbstractJavaClass {
	
	/** 自定义方法集合. */
	protected Set<String> methodSet;
	
	/** 自定义属性集合. */
	protected Set<String> fieldSet;
	
	/** 包. */
	protected String packageStr;
	
	/** The import sb. */
	protected StringBuffer importSb;       //import
	
	/** The method sb. */
	protected StringBuffer methodSb;       //方法
	
	/** The field sb. */
	protected StringBuffer fieldSb;        //域
	
	/** The custom method sb. */
	protected StringBuffer customMethodSb; //自定义方法
	
	/** The has db. */
	protected boolean hasDb = false;  //是否有来源于业务数据的属性
	
	/** The has enums. */
	protected boolean hasEnums = false; //是否有来源于枚举的属性
	
	/** The has get. */
	protected boolean hasGet = false;   //是否有get方法
	
	/** The has query. */
	protected boolean hasQuery = false;  //是否有query方法
	
	/** The has delete. */
	protected boolean hasDelete = false; //是否有delete方法
	
	/** The has save. */
	protected boolean hasSave = false;   //是否有save方法
	
	
	/**
	 * Instantiates a new abstract business class.
	 *
	 * @param bc
	 *            the bc
	 * @param persistenceModel
	 *            the persistence model
	 * @param project
	 *            the project
	 * @param bom
	 *            the bom
	 */
	public AbstractBusinessClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		super(bc, persistenceModel, project,bom);
	}
	
	/**
	 * Instantiates a new abstract business class.
	 *
	 * @param param
	 *            the param
	 */
	public AbstractBusinessClass(ClassParam param){
		super(param);
	}
	
	/**
	 * Inits the.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractJavaClass#init()
	 *      AbstractBusinessClass
	 */
	public void init(){
		methodSet = new HashSet<String>();
		fieldSet = new HashSet<String>();
		importSb = new StringBuffer();
		methodSb = new StringBuffer();
		fieldSb = new StringBuffer();
		customMethodSb = new StringBuffer();
		initPackage();
		initImports();
		initFields();
		initMethods();
		putToVelocityMap();
		
	}
	
	/**
	 * 初始化包.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:40
	 */
	public abstract void initPackage();
	
	/**
	 * 初始化域.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:40
	 */
	public abstract void initFields();
	
	/**
	 * 初始化import.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:40
	 */
	public abstract void initImports();
	
	/**
	 * 初始化方法.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:40
	 */
	public abstract void initMethods();
	
	/**
	 * 获取自定义方法.
	 *
	 * @author mqfdy
	 * @param bop
	 *            the bop
	 * @return the custom method
	 * @Date 2018-09-03 09:00
	 */
	protected abstract String getCustomMethod(BusinessOperation bop);
	
	/**
	 * 设置 Velocity Map.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:40
	 */
	public abstract void putToVelocityMap();
	
	/**
	 * 设置 默认的Velocity Map.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:40
	 */
	protected void putToVelocityMapDef(){
		map.put("isTree", isTree);
		map.put("parent_col_name", parentId);
		List<String> newList = new ArrayList<String>(importSet);
		for(String importStr : getImoprtSortList(newList)){
			importSb.append(importStr);
		}
		map.put("importStr1", importSb.toString());
		map.put("packageStr", packageStr);
	}
	
	/**
	 * 获取Import排序List.
	 *
	 * @author mqfdy
	 * @param list
	 *            the list
	 * @return the imoprt sort list
	 * @Date 2018-9-3 11:38:40
	 */
	private List<String> getImoprtSortList(List<String> list) {
		String[] array = new String[list.size()];
		list.toArray(array);
		Arrays.sort(array);
		List<String> newListJava = new ArrayList<String>();
		List<String> newListJavax = new ArrayList<String>();
		List<String> newListOrg = new ArrayList<String>();
		List<String> newListOther = new ArrayList<String>();
		//List<String> newListOther = new ArrayList<String>();
		
		for(int i = 0; i < array.length; i++){
			String importStr = array[i];
			if(importStr.startsWith("import java.") ){
				newListJava.add(importStr);
			} else if(importStr.startsWith("import javax.")){
				newListJavax.add(importStr);
			} else if(importStr.startsWith("import org.")){
				newListOrg.add(importStr);
			} else {
				newListOther.add(importStr);
			}
		}
		if(!newListJava.isEmpty()){
			newListJava.add("\n");
		}
		if(!newListJavax.isEmpty()){
			newListJava.addAll(newListJavax);
			newListJava.add("\n");
		}
		if(!newListOrg.isEmpty()){
			newListJava.addAll(newListOrg);
		}
		newListOther = getGroupImportList(newListOther);
		if(!newListOther.isEmpty()){
			newListJava.addAll(newListOther);
		}
		return newListJava;
	}
	
	/**
	 * 获取分组排序List.
	 *
	 * @author mqfdy
	 * @param importList
	 *            the import list
	 * @return the group import list
	 * @Date 2018-9-3 11:38:40
	 */
	private List<String> getGroupImportList(List<String> importList) {
		List<String> newList = new ArrayList<String>();
		String groupflag = null;
		for(String importStr : importList){
			String[] importCharArray = importStr.split("\\.");
			if(importCharArray.length > 0){
				String headStr = importCharArray[0];
				if(!headStr.equals(groupflag)){
					newList.add("\n");
					groupflag = headStr;
				}
				newList.add(importStr);
			}
		}
		return newList;
	}
	
	/**
	 * 获取作者.
	 *
	 * @author mqfdy
	 * @return the author
	 * @Date 2018-09-03 09:00
	 */
	public String getAuthor(){
		return System.getProperty("user.name");
	}
	
	/**
	 * 获取当前日期.
	 *
	 * @author mqfdy
	 * @return the date
	 * @Date 2018-09-03 09:00
	 */
	public String getDate(){
		return DateTimeUtil.date2String(new Date());
	}
	
	/**
	 * Gets the output folder path.
	 *
	 * @return AbstractBusinessClass
	 * @see com.mqfdy.code.generator.auxiliary.AbstractJavaClass#getOutputFolderPath()
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}
	
	/**
	 * Sets the output folder path.
	 *
	 * @param outputFolderPath
	 *            AbstractBusinessClass
	 * @see com.mqfdy.code.generator.auxiliary.AbstractJavaClass#setOutputFolderPath(java.lang.String)
	 */
	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}

}
