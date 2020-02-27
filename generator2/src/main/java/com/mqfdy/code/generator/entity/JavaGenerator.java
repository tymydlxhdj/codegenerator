package com.mqfdy.code.generator.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.hibernate.type.TypeFactory;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.mapping.TypeMap;
import com.mqfdy.code.datasource.utils.DateTimeUtil;
import com.mqfdy.code.generator.GenProjectTypeUtilTools;
import com.mqfdy.code.generator.GeneratorPlugin;
import com.mqfdy.code.generator.auxiliary.convert.ConvertUtil;
import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.model.CodeGenerationException;
import com.mqfdy.code.generator.model.ICodeFileMaterial;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.utils.CodePropertiesUtil;
import com.mqfdy.code.generator.utils.HibernateDataType;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.OperationParam;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.QueryCondition;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.SqlOperateType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.model.utils.ValidatorType;
import com.mqfdy.code.scence.IScenceType;
import com.mqfdy.code.utils.MicroserviceProjectPropertiesUtil;
import com.mqfdy.code.utils.PluginUtil;

// TODO: Auto-generated Javadoc
/**
 * java文件生成器的抽象基类.
 *
 * @author zhaidehui
 */
/**
 * JavaEntityGenerator类
 * @author mqfdy
 */
public class JavaGenerator extends AbstractGenerator {
	
	/** map. */
	protected Map<String, Object> map = new HashMap<String, Object>();
	/** import集合. */
	protected Set<String> importSet = new HashSet<String>();
	/** folder. */
	protected String folder = "";
	
	/** templatePath. */
	protected String templatePath;
	
	/** persistenceModel. */
	protected IPersistenceModel persistenceModel;
	
	/** fileName. */
	protected String fileName = "";
	
	/** The bc. */
	protected BusinessClass bc = null;
	
	/** The bom. */
	protected BusinessObjectModel bom = null;
	
	/** 包前缀. */
	protected String packageStr;
	
	/** import语句 包前缀. */
	protected String importPackage;
	
	/** isTree. */
	protected boolean isTree = false;    //是否是树
	
	/** isSlave. */
	protected boolean isSlave = false;    //是否是从节点
	
	/** isMaster. */
	protected boolean isMaster = false;   //是否是主节点
	
	/** parentId. */
	protected String parentId;           //parentId ：外键
	
	/** sceneTypeSet. */
	protected Set<Integer> sceneTypeSet;  //场景类型集合
	/** hasDb. */
	protected boolean hasDb = false;  //是否有来源于业务数据的属性
	
	/** hasEnums. */
	protected boolean hasEnums = false; //是否有来源于枚举的属性
	
	/** hasGet. */
	protected boolean hasGet = false;   //是否有get方法
	
	/** hasQuery. */
	protected boolean hasQuery = false;  //是否有query方法
	
	/** hasDelete. */
	protected boolean hasDelete = false; //是否有delete方法
	
	/** hasSave. */
	protected boolean hasSave = false;   //是否有save方法
	
	/** The custom method sb. */
	protected StringBuilder customMethodSb = new StringBuilder();
	/** 数据字典Buffer. */
	protected StringBuilder dictSb = new StringBuilder();
	
	/** 添加数据字典方法. */
	protected String addDicts = "";
	
	/** 生成的getLt方法中 具体逻辑代码. */
	protected StringBuilder returnSetSb = new StringBuilder();
	
	/** nodeText. */
	protected String nodeText;
	
	/** fieldSb. */
	protected StringBuffer fieldSb = new StringBuffer();        //域
	protected Set<String> fieldSet = new HashSet<>();
	protected String dbType;
	private String packageStrPre;
	private String paretColumnName;
	
	/**
	 * Instantiates a new java entity generator.
	 *
	 * @param genProject genProject
	 * @param persistenceModel persistenceModel
	 * @param templatePath templatePath
	 * @param fileName fileName
	 */
	public JavaGenerator(IProject genProject, IPersistenceModel persistenceModel, String templatePath, String fileName){
		super(genProject);
		this.genProject = genProject;
		this.persistenceModel = persistenceModel;
		this.templatePath = templatePath;
		this.fileName = this.getFileNamePrefix()+fileName+this.getFileNameSuffix();
	
	} 
	
	/**
	 * Instantiates a new java generator.
	 *
	 * @param classParam the class param
	 * @param templatePath the template path
	 */
	public JavaGenerator(ClassParam classParam,String templatePath) {
		this(classParam.getProject(), classParam.getPersistenceModel(), templatePath, classParam.getBc().getName());
		this.bc = classParam.getBc();
		this.bom = classParam.getBom();
		this.dbType = classParam.getDbType();
		init(classParam);
	}
	
	/**
	 * Modify pk strategy.
	 *
	 * @author Administrator
	 * @param map2 
	 * @Date 2019-12-5 17:09:44
	 */
	private void modifyPkStrategy(Map<String, Object> map2) {
		List<Column> pkColumns = this.persistenceModel.getTable().getPrimaryKey().getColumns();
		String[] pathStrArray = this.templatePath.split("/");
		if(pathStrArray != null && pathStrArray.length > 2){
			String templateType = pathStrArray[pathStrArray.length - 3];
			Map<String,String> map = new HashMap<>();
			if(templateType.contains("mybatis")){
				map.putAll(ConvertUtil.pkTypeJpaToMybatisMap);
			}
			if(pkColumns != null && !pkColumns.isEmpty()){
				for (Column column : pkColumns) {
					map2.put("pkType", map.get(column.getPkType()));
				}
			}
		}
		
	}

	/**
	 * Inits the.
	 *
	 * @author Administrator
	 * @param classParam the class param
	 * @Date 2019-11-26 15:55:13
	 */
	protected void init(ClassParam classParam) {
		initBaseMethodSetting();
		initPackage();
		initAssociations(classParam);
		setTreeIdAndText();
		initComboData();
		
	}

	public void initPackage() {
		importPackage = getjavaPkg();
		packageStr = "package "+ importPackage+";";
	}

	/**
	 * Inits the associations.
	 *
	 * @author Administrator
	 * @param classParam the class param
	 * @Date 2019-11-26 15:55:13
	 */
	public void initAssociations(ClassParam classParam) {
		sceneTypeSet = new HashSet<Integer>();
		sceneTypeSet.addAll(classParam.getSceneTypeSet());
		for(Integer sceneType : sceneTypeSet){
			if(IScenceType.TREE_SCENE_TYPE == sceneType){
				this.isTree = true;
			}else if(IScenceType.DOUBLE_TABLE_SCENE_TYPE_MASTER == sceneType){
				this.isMaster = true;
			}else if(IScenceType.DOUBLE_TABLE_SCENE_TYPE_SLAVE == sceneType){
				this.isSlave = true;
			}
		}
		this.parentId = classParam.getParentId();
		this.paretColumnName = classParam.getParetColumnName();
	}


	/**
	 * Inits the base method setting.
	 *
	 * @author Administrator
	 * @Date 2019-11-26 15:55:13
	 */
	protected void initBaseMethodSetting() {
		List<BusinessOperation> operations = bc.getOperations();
		if(operations != null && !operations.isEmpty()){
			for(BusinessOperation bop : operations){
				if(BusinessOperation.OPERATION_TYPE_CUSTOM.equals(bop.getOperationType())){
					String customMethod = "";
					if(templatePath.contains("Service")&&!templatePath.contains("IService")){
						customMethod = getServiceCustomMethod(bop);
					} else if(templatePath.contains("Controller")){
						customMethod = getControllerCustomMethod(bop);
					} else if(templatePath.contains("IService")){
						customMethod = getIServiceCustomMethod(bop);
					} 
					if(!StringUtils.isEmpty(customMethod)){
						customMethodSb .append(customMethod);
					}
				}else{
					if(BusinessOperation.SYS_OPERATION_ADD.equals(bop.getOperationType()) || 
							BusinessOperation.SYS_OPERATION_EDIT.equals(bop.getName())){
						hasSave = true;
					}else if(BusinessOperation.SYS_OPERATION_DELETE.equals(bop.getName())){
						hasDelete = true;
					}else if(BusinessOperation.SYS_OPERATION_GET.equals(bop.getName())){
						hasGet = true;
					}else if(BusinessOperation.SYS_OPERATION_QUERY.equals(bop.getName())){
						hasQuery = true;
					}
				}
			}
			initDaoMethods();
		}
	}

	/**
	 * Inits the dao methods.
	 *
	 * @author Administrator
	 * @Date 2019-12-6 15:06:07
	 */
	public void initDaoMethods() {
		String customMethod = "";
		if(templatePath.contains("Repository")){
			customMethod = getRepositoryCustomMethod();
		}else if(templatePath.contains("Dao") && templatePath.contains("mybatis")){
			customMethod = getDaoCustomMethod();
		}
		if(!StringUtils.isEmpty(customMethod)){
			customMethodSb .append(customMethod);
		}
	}
	/**
	 * Gets the repository custom method.
	 *
	 * @author Administrator
	 * @return the repository custom method
	 * @Date 2019-11-27 11:30:48
	 */
	private String getDaoCustomMethod(){
		MicroserviceProjectPropertiesUtil mppu = new MicroserviceProjectPropertiesUtil(this.genProject);
		dbType = mppu.getDbType();
		StringBuilder methodSb = new StringBuilder();
		for(Property p : bc.getProperties()){
			PropertyEditor editor = p.getEditor();
			int dsType = editor.getDataSourceType();
			if(PropertyEditor.DATASOURCE_TYPE_BUSINESS == dsType){
				hasDb = true;
//				importSet.add("import org.springframework.data.jpa.repository.Query;\n");
//				importSet.add(CodePropertiesUtil.get("import.List"));
				String dictBcId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_BUSINESSCLASS_ID);
				String dictKeyVarId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_KEY_PROPERTY_ID);
				String dictValueVarId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_VALUE_PROPERTY_ID);

				String filterSql = "";

				List<BusinessClass> bcList = bom.getBusinessClasses();
				String dictBcFullName = "";
				String dictKeyVarName = "";
				String dictValueVarName = "";
				String refrenceBcName = "";
				BusinessClass refrenceBc = null;
				for (BusinessClass bc : bcList) {
					if (!StringUtil.isEmpty(dictBcId)
							&& dictBcId.equals(bc.getId())) {// 找出作为下拉列表的业务实体（只在自己的bom中找）
						refrenceBc = bc;
						dictBcFullName = bc.getFullName().replace(".po.", ".domain.");// this.packagee+".domain."+bc.getName();
						dictBcFullName = dictBcFullName + " "
								+ bc.getName();
						List<Property> ps = bc.getProperties();
						for (Property py : ps) {
							if(py instanceof PersistenceProperty){
								PersistenceProperty pp = (PersistenceProperty) py;
								if (dictKeyVarId.equals(pp.getId())) {
									dictKeyVarName = pp.getdBColumnName();

								}
								if (dictValueVarId.equals(pp.getId())) {
									dictValueVarName = pp.getdBColumnName();
								}
							}
							
						}
						filterSql = getFilterSql(editor, bc);
						break;
					}
				}
				if(refrenceBc != null){
					refrenceBcName = refrenceBc.getTableName();
				} 
				if(!StringUtils.isEmpty(filterSql)){
					filterSql = " where "+filterSql;
				}
				
				methodSb .append("\n");
				//methodSb.append("\t//@Query(\"select keyField,valueField from poName [where hqlWhere] [ order by hqlOrder]\")\n");
				methodSb.append("\t@Select(\"select "+dictKeyVarName+","+dictValueVarName+" from "+refrenceBcName+" "+filterSql+"\")\n");
				methodSb.append("\t@Results(id=\"itemMaps\",value={"+StringUtils.concatStringMybatisResult("id", dictKeyVarName, "String")+","+StringUtils.concatStringMybatisResult("name", dictValueVarName, "String")+"})\n");
				methodSb.append("\tpublic List<DicItem> translate"+StringUtils.capitalizeFirstLetter(p.getName())+"FromDB();\n");
			}
		}
		return methodSb.toString();
	}
	/**
	 * Gets the repository custom method.
	 *
	 * @author Administrator
	 * @return the repository custom method
	 * @Date 2019-11-27 11:30:48
	 */
	private String getRepositoryCustomMethod(){
		MicroserviceProjectPropertiesUtil mppu = new MicroserviceProjectPropertiesUtil(this.genProject);
		dbType = mppu.getDbType();
		StringBuilder methodSb = new StringBuilder();
		for(Property p : bc.getProperties()){
			PropertyEditor editor = p.getEditor();
			int dsType = editor.getDataSourceType();
			if(PropertyEditor.DATASOURCE_TYPE_BUSINESS == dsType){
				hasDb = true;
//				importSet.add("import org.springframework.data.jpa.repository.Query;\n");
//				importSet.add(CodePropertiesUtil.get("import.List"));
				String dictBcId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_BUSINESSCLASS_ID);
				String dictKeyVarId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_KEY_PROPERTY_ID);
				String dictValueVarId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_VALUE_PROPERTY_ID);

				String filterSql = "";

				List<BusinessClass> bcList = bom.getBusinessClasses();
				String dictBcFullName = "";
				String dictKeyVarName = "";
				String dictValueVarName = "";
				String refrenceBcName = "";
				BusinessClass refrenceBc = null;
				for (BusinessClass bc : bcList) {
					if (!StringUtil.isEmpty(dictBcId)
							&& dictBcId.equals(bc.getId())) {// 找出作为下拉列表的业务实体（只在自己的bom中找）
						refrenceBc = bc;
						dictBcFullName = bc.getFullName().replace(".po.", ".domain.");// this.packagee+".domain."+bc.getName();
						dictBcFullName = dictBcFullName + " "
								+ bc.getName();
						List<Property> ps = bc.getProperties();
						for (Property py : ps) {
							if (dictKeyVarId.equals(py.getId())) {
								dictKeyVarName = py.getName();
							}
							if (dictValueVarId.equals(py.getId())) {
								dictValueVarName = py.getName();
							}
						}
						filterSql = getFilterSql(editor, bc);
						break;
					}
				}
				if(refrenceBc != null){
					refrenceBcName = refrenceBc.getName();
				} 
				if(!StringUtils.isEmpty(filterSql)){
					filterSql = " where "+filterSql;
				}
				
				methodSb .append("\n");
				//methodSb.append("\t//@Query(\"select keyField,valueField from poName [where hqlWhere] [ order by hqlOrder]\")\n");
				methodSb.append("\t@Query(\"select "+dictKeyVarName+","+dictValueVarName+" from "+refrenceBcName+" "+filterSql+"\")\n");
				methodSb.append("\tpublic List<Object[]> translate"+StringUtils.capitalizeFirstLetter(p.getName())+"FromDB();\n");
			}
		}
		return methodSb.toString();
	}
	
	/**
	 * 方法描述：initImports.
	 *
	 * @author mqfdy
	 * @Date 2018年8月31日 上午10:38:31
	 */
	public void initImports(){
		for(Property p : bc.getProperties()){
			//putToImportSet(p.getDataType());
			if(p instanceof PKProperty){
				if("sequence".equals(((PKProperty)p).getType())){
					importSet.add("import javax.persistence.SequenceGenerator;\n");
				}
			}
			initValidatorImports(p);
		}
	}
	
	/**
	 * 方法描述：initValidatorImports.
	 *
	 * @author mqfdy
	 * @param p p
	 * @Date 2018年8月31日 上午10:38:36
	 */
	private void initValidatorImports(Property p) {
		List<Validator> validators = p.getValidators();
		if(validators != null && !validators.isEmpty()){
			for(Validator v :validators){
				String suffix = "";
				if(ValidatorType.Nullable.getValue().equalsIgnoreCase(v.getValidatorType()) &&
						"string".equalsIgnoreCase(p.getDataType())){
					suffix = ".String";
				}
				importSet.add(CodePropertiesUtil.getValidatorTypeImport(v.getValidatorType()+suffix));
			}
		}
		
	}
	
	
	/**
	 * 生成设计查询条件的where条件sql.
	 *
	 * @author rongxin.bian
	 * @author mqfdy
	 * @param editor            编辑器
	 * @param dictBusinessClass            被当做数据字典的业务实体
	 * @return where条件sql
	 * @Date 2018年8月31日 上午10:41:19
	 */
	
	protected String getFilterSql(PropertyEditor editor,
			BusinessClass dictBusinessClass) {

		String filterSql = "";
		List<QueryCondition> queryConditions = editor.getConditions();

		int index = 0;
		for (QueryCondition condition : queryConditions) {
			// 前括号
			String preBrace = "";
			if (!StringUtil.isEmpty(condition.getPreBraces())) {
				preBrace = "(";
			}
			// 后括号
			String afterBrace = "";
			if (!StringUtil.isEmpty(condition.getAfterBraces())) {
				afterBrace = ")";
			}
			// 属性名称
			String propertyName = condition.getPropertyName();
			// 逻辑运算符
			String logicOperate = condition.getSqlLogicOperateType()
					.getDispName();
			// 操作符
			SqlOperateType operateType = condition.getSqlOperateType();
			String operateStr = operateType.getDispName();

			String value = condition.getValue1();

			String dataType = "";
			for (Property property : dictBusinessClass.getProperties()) {
				if (propertyName.equals(property.getName())) {
					if(this.templatePath.contains("mysql")){
						PersistenceProperty pp = (PersistenceProperty) property;
						propertyName = pp.getdBColumnName();
					}
					dataType = property.getDataType();
					break;
				}
			}

			// 如果操作符是'in' 'not in'的时候, 需要特殊处理, 把每一个字符类型的值加上单引号, 日期类型要to_date
			if (operateType.equals(SqlOperateType.Oracle_in)
					|| operateType.equals(SqlOperateType.Oracle_notIn)) {
				if (DataType.String.getValue_hibernet().equals(dataType)
						|| DataType.Character.getValue_hibernet().equals(
								dataType)) {
					String[] subValues = value.split(",");

					int idx = 0;
					StringBuffer buffer = new StringBuffer();
					for (String subValue : subValues) {
						if (idx == 0) {
							buffer.append("'" + subValue.trim() + "'");
						} else {
							buffer.append(", '" + subValue.trim() + "'");
						}
						idx++;
					}

					value = "(" + buffer.toString() + ")";
				} else if (DataType.Date.getValue_hibernet().equals(dataType)) {
					int idx = 0;
					StringBuffer buffer = new StringBuffer();

					String[] subValues = value.split(",");
					for (String subValue : subValues) {
						if (idx == 0) {
							buffer.append(convertToDate(subValue, getDbType()));
						} else {
							buffer.append(", "
									+ convertToDate(subValue, getDbType()));
						}
						idx++;
					}
					value = "(" + buffer.toString() + ")";
				} else if (DataType.Time.getValue_hibernet().equals(dataType)) {
					int idx = 0;
					StringBuffer buffer = new StringBuffer();

					String[] subValues = value.split(",");
					for (String subValue : subValues) {
						if (idx == 0) {
							buffer.append(convertToTime(subValue, getDbType()));
						} else {
							buffer.append(", "
									+ convertToTime(subValue, getDbType()));
						}
						idx++;
					}
					value = "(" + buffer.toString() + ")";
				} else if (DataType.Timestamp.getValue_hibernet().equals(
						dataType)) {
					int idx = 0;
					StringBuffer buffer = new StringBuffer();

					String[] subValues = value.split(",");
					for (String subValue : subValues) {
						if (idx == 0) {
							buffer.append(convertToTimeStamp(subValue,
									getDbType()));
						} else {
							buffer.append(", "
									+ convertToTimeStamp(subValue, getDbType()));
						}
						idx++;
					}
					value = "(" + buffer.toString() + ")";
				}
			} else {
				if (DataType.String.getValue_hibernet().equals(dataType)
						|| DataType.Character.getValue_hibernet().equals(
								dataType)) {
					value = "'" + value + "'";
				} else if (DataType.Date.getValue_hibernet().equals(dataType)) {
					value += convertToDate(value, dbType);
				} else if (DataType.Time.getValue_hibernet().equals(dataType)) {
					value += convertToTime(value, dbType);
				} else if (DataType.Timestamp.getValue_hibernet().equals(
						dataType)) {
					value += convertToTimeStamp(value, dbType);
				}
			}

			filterSql += preBrace + propertyName + " " + operateStr + " "
					+ value + afterBrace;

			if (index != queryConditions.size() - 1) {
				filterSql += " " + logicOperate + " ";
			}
			index++;
		}
		return filterSql;
	}
	
	/**
	 * 字符串转换到日期.
	 *
	 * @author rongxin.bian
	 * @author mqfdy
	 * @param varchar the varchar
	 * @param dbType the db type
	 * @return String
	 * @Date 2018年8月31日 上午10:41:19
	 */
	
	protected String convertToDate(String varchar, String dbType) {
		if (StringUtil.isEmpty(varchar)) {
			return null;
		}

		String result = "";

		if (DBType.Oracle.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
//		} else if (DBType.DB2.getDbType().equals(dbType)) {
//			result = "date(trim(char('" + varchar + "')))";
		} else if (DBType.MsSQL.getDbType().equals(dbType)) {
			result = "CONVERT(DATETIME, " + varchar + ", 'yyyy-MM-dd')";
		} else if (DBType.MySQL.getDbType().equals(dbType)) {
			result = "STR_TO_DATE('" + varchar + "','%Y-%m-%d')";
//		} else if (DBType.Sybase.getDbType().equals(dbType)) {
		} else {
			result = varchar;
		}


		return result;
	}

	/**
	 * 方法描述：convertToTime.
	 *
	 * @author mqfdy
	 * @param varchar varchar
	 * @param dbType dbType
	 * @return String实例
	 * @Date 2018年8月31日 上午10:41:44
	 */
	protected String convertToTime(String varchar, String dbType) {
		if (StringUtil.isEmpty(varchar)) {
			return null;
		}

		String result = "";

		if (DBType.Oracle.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
//		} else if (DBType.DB2.getDbType().equals(dbType)) {
//			result = "date(trim(char('" + varchar + "')))";
		}  else if (DBType.MsSQL.getDbType().equals(dbType)) {
			result = "CONVERT(DATETIME, " + varchar + ", 'yyyy-MM-dd')";
		} else if (DBType.MySQL.getDbType().equals(dbType)) {
			result = "STR_TO_DATE('" + varchar + "','%Y-%m-%d')";
//		} else if (DBType.Sybase.getDbType().equals(dbType)) {

		} else {
			result = varchar;
		}

		return result;
	}
	

	// 获取主键数据类型
	/**
	 * 方法描述：getPkType.
	 *
	 * @author mqfdy
	 * @return String实例
	 * @Date 2018年8月31日 上午10:41:52
	 */
	public String getPkType() {
		String pkType = "";
		for (Property p : bc.getProperties()) {
			if (p instanceof PKProperty) {
				pkType = p.getDataType();
				break;
			}
		}
		return TypeFactory.basic(pkType).getReturnedClass().getName();
	}

	/**
	 * 方法描述：convertToTimeStamp.
	 *
	 * @author mqfdy
	 * @param varchar varchar
	 * @param dbType dbType
	 * @return String实例
	 * @Date 2018年8月31日 上午10:41:55
	 */
	protected String convertToTimeStamp(String varchar, String dbType) {
		if (StringUtil.isEmpty(varchar)) {
			return null;
		}

		String result = "";

		if (DBType.Oracle.getDbType().equals(dbType)) {
			result = "to_timestamp('" + varchar + "','yyyy-MM-dd hh24:mi:ss')";
//		} else if (DBType.DB2.getDbType().equals(dbType)) {
//			result = "timestamp(trim(char('" + varchar + "')))";
		} else if (DBType.MsSQL.getDbType().equals(dbType)) {
			result = "CONVERT(DATETIME, " + varchar
					+ ", 'yyyy-MM-dd HH:mi:ss')";
		} else if (DBType.MySQL.getDbType().equals(dbType)) {
			result = "STR_TO_DATE('" + varchar + "','%Y-%m-%d %H:%i:%s')";
//		} else if (DBType.Sybase.getDbType().equals(dbType)) {

		}  else {
			result = varchar;
		}

		return result;
	}
	/**
	 * 方法描述：getFileNameWithoutExtension.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 下午2:15:49
	 */
	@Override
	final protected String getFileNameWithoutExtension() {
		return  this.fileName;
	}

	/**
	 * 方法描述：getOutputFilePath.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 下午2:15:56
	 */
	public String getOutputFilePath() {
		return getOutputFolderPath() + fileName + getFileExtension();
	}
	
	//通过带“.”的包名获得路径，以"\"结尾
	/**
	 * 方法描述：getPackagePath.
	 *
	 * @author mqfdy
	 * @param packageName packageName
	 * @return String实例
	 * @Date 2018年8月31日 下午2:16:00
	 */
	public String getPackagePath(String packageName){
		String packagePath = packageName.replace(".", "\\");
		return packagePath;
	}
	
	/**
	 * 获取生成文件路径
	 * 方法描述：getOutputFolderPath.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 下午2:16:09
	 */
	@Override
	protected String getOutputFolderPath() {
		
		String javaPkg = importPackage;
		IFolder folder = GenProjectTypeUtilTools.getOutputFolderPath(
				genProject, "java");
		String outputPath = folder.getLocation().toOSString();
		File mainDir = new File(outputPath + File.separator + "main");
		if(mainDir.exists()){
			outputPath = outputPath + File.separator + "main";
			mainDir = new File(outputPath + File.separator + "java");
			if(mainDir.exists()){
				outputPath = outputPath + File.separator + "java";
			}
		}
		return outputPath + File.separator + StringUtils.getPackageAsPath(javaPkg);
	}

	/**
	 * Gets the java pkg.
	 *
	 * @author Administrator
	 * @return the java pkg
	 * @Date 2019-11-26 15:54:29
	 */
	protected String getjavaPkg() {
		if(this.importPackage != null){
			return this.importPackage;
		}
		String javaPkg = persistenceModel.getJavaPackage() + "." + persistenceModel.getScenceName();
		packageStrPre = javaPkg;
		String[] strArray = templatePath.split("/");
		if(strArray.length > 2){
			for (int i = 2; i < strArray.length; i++) {
				if(i < (strArray.length -1)){
					javaPkg = javaPkg +"."+strArray[i].toLowerCase(Locale.getDefault());
				}
			}
		}
	/*	if (this.templatePath.contains("Controller")) {
			javaPkg = javaPkg + ".controller";
		} else if(this.templatePath.contains("Service")) {
			javaPkg = javaPkg + ".services";
		} else if(this.templatePath.contains("ServiceImpl")) {
			javaPkg = javaPkg + ".services.impl";
		} else if(this.templatePath.contains("Repository")) {
			javaPkg = javaPkg + ".repositories";
		} else if(this.templatePath.contains("Validator")) {
			javaPkg = javaPkg + ".validator";
		} else if(this.templatePath.contains("Dao") || this.templatePath.contains("DaoProvider")) {
			javaPkg = javaPkg + ".dao";
		} else if(this.templatePath.contains("VO") || this.templatePath.contains("Transfer")) {
			javaPkg = javaPkg + ".vo";
		}*/
		return javaPkg;
	}
	
	/**
	 * 方法描述：getSourceMap.
	 *
	 * @author mqfdy
	 * @return map
	 * @Date 2018年8月31日 下午2:16:18
	 */
	@Override
	public Map<String, Object> getSourceMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PERSISTENCE_MODEL_KEY, persistenceModel);
		map.put("isTree", isTree);
		map.put("parent_property_name", parentId);
		map.put("parent_col_name", this.paretColumnName);
		map.put("packageStrPre", packageStrPre);
		map.put("packageStr", packageStr);
		List<String> newList = new ArrayList<String>(importSet);
		StringBuilder importSb = new StringBuilder();
		for(String importStr : newList){
			importSb .append(importStr);
		}
		map.put("importStr", importSb.toString());
		map.put("customMethods", customMethodSb.toString());
		map.put("hasSave", hasSave);
		map.put("hasDelete", hasDelete);
		map.put("hasQuery", hasQuery);
		map.put("hasGet", hasGet);
		map.put("hasGet", hasGet);
		map.put("addDicts",addDicts);
		map.put("dicts", dictSb.toString());
		map.put("fields", fieldSb.toString());
		map.put("hasDb", hasDb);
		map.put("hasEnum", hasEnums);
		map.put("returnSets", returnSetSb.toString());
		map.put("isSlave", isSlave);
		map.put("nodeText", nodeText);
		map.put("dbType", dbType);
		if(!templatePath.contains("/jpa/") && !templatePath.contains("/hibernate/")){
			modifyPkStrategy(map);
		}
		return map;
	}
	
	/**
	 * 方法描述：getServiceCustomMethod.
	 *
	 * @author mqfdy
	 * @param bop bop
	 * @return String
	 * @Date 2018年8月31日 上午10:43:20
	 */
	protected String getServiceCustomMethod(BusinessOperation bop) {
//		importSet.add("import org.springframework.transaction.annotation.Transactional;\n");
//		importSet.add("import org.springframework.transaction.annotation.Propagation;\n");
		StringBuffer sb = new StringBuffer();
		List<OperationParam> params = bop.getOperationParams();
		sb.append("    /**\n");
		sb.append("     * @"+bop.getName()+":");
		if(StringUtils.isEmpty(bop.getRemark())){
			sb.append(bop.getDisplayName());
		}else{
			sb.append(bop.getRemark());
		}
		sb.append("\n");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				sb.append("     * @param " + param.getName() +" " + param.getDisplayName());
				sb.append("\n");
			}
		}
		//putToImportSet(bop.getReturnDataType());
		String returnType = getDataType(bop.getReturnDataType());
		sb.append("     * @return "+ returnType + "\n");
		sb.append("     * @date " + this.getDate() + "\n");
		sb.append("     * @author " + this.getAuthor() + "\n");
		sb.append("     */\n");
		sb.append("    @Transactional(propagation=Propagation."+bop.getTransactionType()+")\n");
		sb.append("    public ");
		
		sb.append(returnType);
		sb.append(" ");
		sb.append(bop.getName());
		sb.append("(");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				//putToImportSet(param.getDataType());
				String paramType = getDataType(param.getDataType());
				sb.append(paramType);
				sb.append(" ");
				sb.append(param.getName());
				if(i < params.size()-1){
					sb.append(", ");
				}
			}
		}
		sb.append("){\n");
		if(!"void".equals(returnType)){
			sb.append("        return null;\n");
		}
		sb.append("    }\n");
		return sb.toString();
	}
	
	/**
	 * 方法描述：getControllerCustomMethod.
	 *
	 * @author mqfdy
	 * @param bop bop
	 * @return the custom method
	 * @Date 2018年8月31日 上午10:38:08
	 */
	protected String getControllerCustomMethod(BusinessOperation bop) {
		StringBuffer sb = new StringBuffer();
		List<OperationParam> params = bop.getOperationParams();
		sb.append("    /**\n");
		sb.append("     * @"+bop.getName()+":");
		if(StringUtils.isEmpty(bop.getRemark())){
			sb.append(bop.getDisplayName());
		}else{
			sb.append(bop.getRemark());
		}
		sb.append("\n");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				sb.append("     * @param " + param.getName() +" " + param.getDisplayName());
				sb.append("\n");
			}
		}
		//putToImportSet(bop.getReturnDataType());
		String returnType = getDataType(bop.getReturnDataType());
		String returnTypeStr = returnType;
		if("void".equals(returnTypeStr)){
			returnTypeStr = "";
		}
		sb.append("     * @return "+ returnTypeStr + "\n");
		sb.append("     * @date " + this.getDate() + "\n");
		sb.append("     * @author " + this.getAuthor() + "\n");
		sb.append("     */\n");
		sb.append("    @RequestMapping(value = \"/"+bop.getName()+"\", method = RequestMethod.POST)\n");
		sb.append("    public ");
		
		sb.append(returnType);
		sb.append(" ");
		sb.append(bop.getName());
		sb.append("(");
		StringBuffer sbServiceParam = new StringBuffer();
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				//putToImportSet(param.getDataType());
				String dataType = getDataType(param.getDataType());
				if(bc.getName().equals(dataType)){
					sb.append("@RequestBody ");
					//importSet.add("import org.springframework.web.bind.annotation.RequestBody;\n");
				}
				sb.append(dataType);
				sb.append(" ");
				sb.append(param.getName());
				sbServiceParam.append(param.getName());
				if(i < params.size()-1){
					sb.append(", ");
					sbServiceParam.append(", ");
				}
			}
		}
		sb.append("){\n");
		if(!"void".equals(returnType)){
			sb.append("        try {\n");
			sb.append("            logger.info(\""+bop.getDisplayName()+" 成功\");\n");
			sb.append("            return "+StringUtils.lowercaseFirstLetter(bc.getName())
			+"Service."+bop.getName()+"("+sbServiceParam.toString()+");\n");
			sb.append("        } catch (Exception e) {\n");
			String errorMessage = bop.getErrorMessage();
			if(StringUtils.isEmpty(errorMessage)){
				sb.append("            logger.error(e.getMessage(), e);\n");
			}else{
				errorMessage = errorMessage + ":";
				sb.append("            logger.error(\""+errorMessage+"\"+e.getMessage(), e);\n");
			}
			
			sb.append("            return null;\n");
			sb.append("        } \n");
		}
		sb.append("    }\n");
		return sb.toString();
	}
	/**
	 * 方法描述：getCustomMethod.
	 *
	 * @author mqfdy
	 * @param bop bop
	 * @return String
	 * @Date 2018年8月31日 上午10:40:03
	 */
	public String getIServiceCustomMethod(BusinessOperation bop) {
		StringBuffer sb = new StringBuffer();
		List<OperationParam> params = bop.getOperationParams();
		sb.append("    /**\n");
		sb.append("     * @"+bop.getName()+":");
		if(StringUtils.isEmpty(bop.getRemark())){
			sb.append(bop.getDisplayName());
		}else{
			sb.append(bop.getRemark());
		}
		sb.append("\n");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				sb.append("     * @param " + param.getName() +" " + param.getDisplayName());
				sb.append("\n");
			}
		}
		//putToImportSet(bop.getReturnDataType());
		String returnType = getDataType(bop.getReturnDataType());
		sb.append("     * @return "+ returnType + "\n");
		sb.append("     * @date " + this.getDate() + "\n");
		sb.append("     * @author " + this.getAuthor() + "\n");
		sb.append("     */\n");
		sb.append("    public ");
		
		sb.append(returnType);
		sb.append(" ");
		sb.append(bop.getName());
		sb.append("(");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				//新增import，并返回java类型
				//putToImportSet(param.getDataType());
				String paramType = getDataType(param.getDataType());
				sb.append(paramType);
				sb.append(" ");
				sb.append(param.getName());
				if(i < params.size()-1){
					sb.append(", ");
				}
			}
		}
		sb.append(");\n\n");
		return sb.toString();
	}
	/**
	 * 方法描述：setTreeIdAndText.
	 *
	 * @author mqfdy
	 * @Date 2018年8月31日 上午10:43:07
	 */
	protected void setTreeIdAndText() {
		String tempPkPName = null;
		for(Property p : bc.getProperties()){
			String pName = p.getName();
			if(pName.endsWith("name") || pName.endsWith("Name")){
				nodeText = pName;
				break;
			}
			if(p.isPrimaryKey()){
				tempPkPName = p.getName();
			}
			
		}
		if(StringUtils.isEmpty(nodeText)) {
			nodeText = tempPkPName;
		}
	}
	/**
	 * 方法描述：getAuthor.
	 *
	 * @author mqfdy
	 * @return String实例
	 * @Date 2018年8月31日 上午10:35:09
	 */
	public String getAuthor(){
		return System.getProperty("user.name");
	}
	
	/**
	 * 方法描述：getDate.
	 *
	 * @author mqfdy
	 * @return String实例
	 * @Date 2018年8月31日 上午10:35:14
	 */
	public String getDate(){
		return DateTimeUtil.date2String(new Date());
	}
	
	/**
	 * 数据类型转换.
	 *
	 * @author mqfdy
	 * @param dataType the data type
	 * @return String
	 * @Date 2018年8月31日 上午10:36:31
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
	 * 方法描述：getFileExtension.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 下午2:16:26
	 */
	@Override
	final protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * 方法描述：getTemplatePath.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 下午2:16:32
	 */
	@Override
	public String getTemplatePath() {
		return templatePath;
	}


	/**
	 * 方法描述：getFileName.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 下午2:16:43
	 */
	@Override
	public String getFileName() {
		return this.fileName + getFileExtension();
	}
	
	/**
	 * 方法描述：getFileNameSuffix.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 上午10:49:04
	 */
	public String getFileNameSuffix() {
		String suffix = "";
		String templateNameWithoutExtension = this.templatePath.replace(".vm", "");
		if (templateNameWithoutExtension.contains("Controller")) {
			suffix = "Controller";
		} else if(templateNameWithoutExtension.contains("Service")) {
			suffix = "Service";
		} else if(templateNameWithoutExtension.endsWith("ServiceImpl")) {
			suffix = "ServiceImpl";
		} else if(templateNameWithoutExtension.contains("Repository")) {
			suffix = "Repository";
		} else if(templateNameWithoutExtension.endsWith("Dao") ) {
			suffix = "Dao";
		} else if(templateNameWithoutExtension.endsWith("DaoProvider")) {
			suffix = "DaoProvider";
		} else if(templateNameWithoutExtension.contains("VO")) {
			suffix = "VO";
		} else if(templateNameWithoutExtension.contains("Transfer")) {
			suffix = "Transfer";
		} else if(templateNameWithoutExtension.contains("Validator")) {
			suffix = "Validator";
		}
		return suffix;
	}
	
	/**
	 * 方法描述：getFileNamePrefix.
	 *
	 * @author mqfdy
	 * @return String
	 * @Date 2018年8月31日 上午10:49:09
	 */
	public String getFileNamePrefix() {
		String prefix = "";
		String scencePluginPath;
		String templateName = "";
		try {
			scencePluginPath = PluginUtil
					.getPluginOSPath(GeneratorPlugin.PLUGIN_ID);
			File templateFile = new File(scencePluginPath+File.separator+this.templatePath);
			if(templateFile.exists()&&templateFile.isFile()){
				templateName = templateFile.getName();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (templateName.startsWith("I")) {
			prefix = "I";
		} 
		return prefix;
	}
	
	/**
	 * 方法描述：initComboData.
	 *
	 * @author mqfdy
	 * @Date 2018年8月31日 上午10:43:37
	 */
	protected void initComboData() {
		for(Property p : bc.getProperties()){
			PropertyEditor editor = p.getEditor();
			int dsType = editor.getDataSourceType();
			if(PropertyEditor.DATASOURCE_TYPE_ENUM == dsType){
				// 枚举
				String dictEnumId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_ENUMERATION_ID);
				if (!StringUtil.isEmpty(dictEnumId)) {
					hasEnums = true;
					List<Enumeration> enums = bom.getEnumerations();
					String enumKey = "";
					for (Enumeration en : enums) {
						if (dictEnumId.equals(en.getId())) {
							enumKey = en.getName().toLowerCase(Locale.getDefault());
						}
					}
					dictSb.append("dicts.add(translateFromFile(\""
							+ p.getName() + "\","+enumKey+"));\n\t\t");
					//fieldSet 新增field
					fieldSet.add("@Value(\"${"+bc.getName().toUpperCase(Locale.getDefault())+"."+enumKey.toUpperCase(Locale.getDefault())+"}\")\n\tprivate String "+enumKey+";\n\t");
		}
			
			}else if(PropertyEditor.DATASOURCE_TYPE_BUSINESS == dsType){
				hasDb = true;
				String dbSuffix = "Repository";
				if(this.templatePath.contains("mybatis")){
					dbSuffix = "Dao";
				}
				returnSetSb.append("if(\""+p.getName()+"\".equals(fieldName)){\n");
				returnSetSb.append("\t\t\treturn "+StringUtils.lowercaseFirstLetter(bc.getName())+dbSuffix+".translate"+StringUtils.capitalizeFirstLetter(p.getName())+"FromDB();\n");
				returnSetSb.append("\t\t}\n\t\t");
				
				dictSb.append("dicts.add(translateFromDB(\""
						+ p.getName()
						+ "\"));\n\t\t");
			}
			
		}
		for (String fieldStr : fieldSet) {
			fieldSb.append(fieldStr);
		}
		setAddDicts();
	}
	
	/**
	 * 方法描述：setAddDicts.
	 *
	 * @author mqfdy
	 * @Date 2018年8月31日 上午10:43:44
	 */
	protected void setAddDicts() {
		addDicts = "";
		if((hasDb || hasEnums)){
			addDicts = ".addDicItems(wrapDictList())";
//			importSet.add("import java.util.ArrayList;\n");
//			importSet.add("import java.util.List;\n");
//			importSet.add("import com.sgcc.uap.rest.support.DicItems;\n");
		}
		
	}
	/**
	 * 方法描述：getDbType.
	 *
	 * @author mqfdy
	 * @return String实例
	 * @Date 2018年8月31日 上午10:42:04
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * 方法描述：setDbType.
	 *
	 * @author mqfdy
	 * @param dbType dbType
	 * @Date 2018年8月31日 上午10:42:07
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
}
