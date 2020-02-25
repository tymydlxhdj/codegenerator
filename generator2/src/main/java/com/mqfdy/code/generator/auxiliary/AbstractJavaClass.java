package com.mqfdy.code.generator.auxiliary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.datasource.mapping.TypeMap;
import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.model.IVelocityMap;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.utils.HibernateDataType;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.scence.IScenceType;
// TODO: Auto-generated Javadoc

/**
 * The Class AbstractJavaClass.
 *
 * @author mqf
 */
public abstract class AbstractJavaClass implements IVelocityMap{
	
	/** import集合. */
	protected Set<String> importSet;
	
	/** 业务对象模型对象. */
	protected BusinessObjectModel bom;
	
	/** 业务对象. */
	protected BusinessClass bc;
	
	/** 项目对象. */
	protected IProject project;
	
	/** velocity map. */
	protected Map<String,Object> map;
	
	/** 持久化模型. */
	protected IPersistenceModel persistenceModel;
	
	/** 包前缀. */
	protected String packagePrefix;
	
	/** import语句 包前缀. */
	protected String importPackage;
	
	/** The is tree. */
	protected boolean isTree = false;    //是否是树
	
	/** The is slave. */
	protected boolean isSlave = false;    //是否是从节点
	
	/** The is master. */
	protected boolean isMaster = false;   //是否是主节点
	
	/** The parent id. */
	protected String parentId;           //parentId ：外键
	
	/** The scene type set. */
	protected Set<Integer> sceneTypeSet;  //场景类型集合
	
	/** The output folder path. */
	protected String outputFolderPath;
	
	
	/**
	 * Instantiates a new abstract java class.
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
	public AbstractJavaClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		this.persistenceModel = persistenceModel;
		this.bc = bc;
		this.bom = bom;
		this.project = project;
		map = new HashMap<String,Object>();
		importSet = new HashSet<String>();
		importPackage = persistenceModel.getJavaPackage() + "." + persistenceModel.getScenceName().toLowerCase(Locale.getDefault());
		packagePrefix = "package "+ importPackage;
		init();
	}
	
	/**
	 * Instantiates a new abstract java class.
	 *
	 * @param param
	 *            the param
	 */
	public AbstractJavaClass(ClassParam param){
		this.persistenceModel = param.getPersistenceModel();
		this.bc = param.getBc();
		this.bom = param.getBom();
		this.project = param.getProject();
		map = new HashMap<String,Object>();
		importSet = new HashSet<String>();
		importPackage = persistenceModel.getJavaPackage() + "." + persistenceModel.getScenceName().toLowerCase(Locale.getDefault());
		packagePrefix = "package "+ importPackage;
		sceneTypeSet = new HashSet<Integer>();
		sceneTypeSet.addAll(param.getSceneTypeSet());
		for(Integer sceneType : sceneTypeSet){
			if(IScenceType.TREE_SCENE_TYPE == sceneType){
				this.isTree = true;
			}else if(IScenceType.DOUBLE_TABLE_SCENE_TYPE_MASTER == sceneType){
				this.isMaster = true;
			}else if(IScenceType.DOUBLE_TABLE_SCENE_TYPE_SLAVE == sceneType){
				this.isSlave = true;
			}
		}
		this.parentId = param.getParentId();
		//this.outputFolderPath = param.getOutputPath();
		init();
	}

	/**
	 * 初始化.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public abstract void init();
	
	/**
	 * 初始化包.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
	public abstract void initPackage();
	
	/**
	 * 初始化域.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
	public abstract void initFields();
	
	/**
	 * 初始化import.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
	public abstract void initImports();
	
	/**
	 * 初始化方法.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
	public abstract void initMethods();
	
	/**
	 * 向importSet加import.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the data type
	 * @Date 2018-09-03 09:00
	 */
	protected void putToImportSet(String dataType) {
		if(StringUtils.isEmpty(dataType)){
			return;
		}
		if (HibernateDataType.originDataType.contains(dataType)) {// 判断是否基本类型
			if(DataType.Date.getValue_hibernet().equals(dataType)){
				importSet.add("import java.util.Date;\n");
			}
			if(DataType.Timestamp.getValue_hibernet().equals(dataType)){
				importSet.add("import java.sql.Timestamp;\n");
			}
			if(DataType.Time.getValue_hibernet().equals(dataType)){
				importSet.add("import java.sql.Time;\n");
			}
			if(DataType.Big_decimal.getValue_hibernet().equals(dataType)){
				importSet.add("import java.math.BigDecimal;\n");
			}
			/*String idSingleType = StringUtil.getClassNameFromFullName(TypeFactory.basic(dataType).getReturnedClass().getName());
			if (idSingleType.equals("BigDecimal")) {
				importSet.add("import java.math.BigDecimal;\n");
			}
			if (idSingleType.equals("Date")) {
				importSet.add("import java.util.Date;\n");
			}
			if (idSingleType.equals("Clob")) {
				importSet.add("import java.sql.Clob;\n");
			}
			if (idSingleType.equals("Blob")) {
				importSet.add("import java.sql.Blob;\n");
			}*/
		} else {
			
			if(DataType.List.getValue_hibernet().toString().equals(dataType)){
				importSet.add("import java.util.List;\n");
			}
			if(DataType.Map.getValue_hibernet().toString().equals(dataType)){
				importSet.add("import java.util.Map;\n");
			}
			if(DataType.Set.getValue_hibernet().toString().equals(dataType)){
				importSet.add("import java.util.Set;\n");
			}
			// 非基本类型
				if ("RequestCondition".equalsIgnoreCase(dataType)) {// RequestCondition类型
					importSet.add("import com.sgcc.uap.rest.support.RequestCondition;\n");
				} else if (HibernateDataType.collectionType.contains(dataType)) {// list,map等集合类型
					if ("list".equalsIgnoreCase(dataType)) {
						importSet.add("import java.util.List;\n");
					} else if ("set".equalsIgnoreCase(dataType)) {
						importSet.add("import java.util.Set;\n");
					} else if ("map".equalsIgnoreCase(dataType)) {
						importSet.add("import java.util.Map;\n");
					} else if ("collection".equalsIgnoreCase(dataType)) {
						importSet.add("import java.util.Collection;\n");
					}
				} else if ("IDRequestObject".equalsIgnoreCase(dataType)) {
					importSet.add("import com.sgcc.uap.rest.annotation.IdRequestBody;\n");
				} else {// pojo类型
					if ("User".equalsIgnoreCase(dataType) || "BusinessOrganization".equalsIgnoreCase(dataType)) {
						importSet.add("import com.sgcc.isc.core.orm.identity." + dataType + ";\n");
					} else {
						importSet.add("import "+bc.getBelongPackage().getFullName().toLowerCase(Locale.getDefault())+".domain."+dataType+";\n");
					}
				}
			}
		}
	
	/**
	 * 数据类型转换.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the data type
	 * @return String
	 * @Date 2018-09-03 09:00
	 */
	protected String getDataType(String dataType) {
		if(StringUtils.isEmpty(dataType)){
			return "void";
		}
		if (HibernateDataType.originDataType.contains(dataType) ||
				HibernateDataType.collectionType.contains(dataType)) {// 判断是否基本类型
			return TypeMap.getPropertyToJavaMap().get(dataType);
		}
		return dataType;
	}
	
	/**
	 * Gets the bc.
	 *
	 * @author mqfdy
	 * @return the bc
	 * @Date 2018-09-03 09:00
	 */
	public BusinessClass getBc() {
		return bc;
	}
	
	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject() {
		return project;
	}
	
	/**
	 * Gets the map.
	 *
	 * @author mqfdy
	 * @return the map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, Object> getMap() {
		return map;
	}
	
	/**
	 * Gets the persistence model.
	 *
	 * @author mqfdy
	 * @return the persistence model
	 * @Date 2018-09-03 09:00
	 */
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}
	
	/**
	 * Gets the bom.
	 *
	 * @author mqfdy
	 * @return the bom
	 * @Date 2018-09-03 09:00
	 */
	public BusinessObjectModel getBom() {
		return bom;
	}

	/**
	 * Gets the output folder path.
	 *
	 * @author mqfdy
	 * @return the output folder path
	 * @Date 2018-09-03 09:00
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}

	/**
	 * Sets the output folder path.
	 *
	 * @author mqfdy
	 * @param outputFolderPath
	 *            the new output folder path
	 * @Date 2018-09-03 09:00
	 */
	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}
	
	/**
	 * getVelocityMap
	 * @date 2019年5月15日 下午2:36:52
	 * @author mqfdy
	 */
	public Map<String,Object> getVelocityMap(){
		return this.map;
	}

}
