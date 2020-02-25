package com.mqfdy.code.generator.auxiliary;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.utils.CodePropertiesUtil;
import com.mqfdy.code.generator.utils.ProjectPropertiesUtil;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.QueryCondition;
import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.SqlOperateType;
import com.mqfdy.code.model.utils.StringUtil;
// TODO: Auto-generated Javadoc

/**
 * The Class RepositoryClass.
 *
 * @author mqf
 */
public class RepositoryClass extends AbstractBusinessClass {
	
	
	/** The db type. */
	private String dbType;

	/**
	 * Instantiates a new repository class.
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
	public RepositoryClass(BusinessClass bc, IPersistenceModel persistenceModel, IProject project, BusinessObjectModel bom) {
		super(bc, persistenceModel, project,bom);
	}

	/**
	 * Instantiates a new repository class.
	 *
	 * @param param
	 *            the param
	 */
	public RepositoryClass(ClassParam param) {
		super(param);
	}

	/**
	 * Inits the package.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initPackage()
	 *      RepositoryClass
	 */
	@Override
	public void initPackage() {
		packageStr = packagePrefix +  ".repositories;";
	}

	/**
	 * Inits the fields.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initFields()
	 *      RepositoryClass
	 */
	@Override
	public void initFields() {
		
	}

	/**
	 * Inits the imports.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initImports()
	 *      RepositoryClass
	 */
	@Override
	public void initImports() {
		importSet.add("import org.springframework.data.jpa.repository.JpaRepository;\n");
		importSet.add("import org.springframework.data.jpa.repository.JpaSpecificationExecutor;\n");
		importSet.add("import " + importPackage+".domain."+bc.getName()+";\n");
		if(isTree){
			importSet.add(CodePropertiesUtil.get("import.List"));
		}
	}

	/**
	 * Inits the methods.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initMethods()
	 *      RepositoryClass
	 */
	@Override
	public void initMethods() {
		ProjectPropertiesUtil mppu = new ProjectPropertiesUtil(project);
		dbType = mppu.getDbType();
		for(Property p : bc.getProperties()){
			PropertyEditor editor = p.getEditor();
			int dsType = editor.getDataSourceType();
			if(PropertyEditor.DATASOURCE_TYPE_BUSINESS == dsType){
				hasDb = true;
				importSet.add("import org.springframework.data.jpa.repository.Query;\n");
				importSet.add(CodePropertiesUtil.get("import.List"));
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
				methodSb.append("\n");
				//methodSb.append("\t//@Query(\"select keyField,valueField from poName [where hqlWhere] [ order by hqlOrder]\")\n");
				methodSb.append("\t@Query(\"select "+dictKeyVarName+","+dictValueVarName+" from "+refrenceBcName+" "+filterSql+"\")\n");
				methodSb.append("\tpublic List<Object[]> translate"+StringUtils.capitalizeFirstLetter(p.getName())+"FromDB();\n");
			}
		}
	}

	/**
	 * Gets the custom method.
	 *
	 * @param bop
	 *            the bop
	 * @return RepositoryClass
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#getCustomMethod(com.mqfdy.code.model.BusinessOperation)
	 */
	@Override
	protected String getCustomMethod(BusinessOperation bop) {
		
		return null;
	}

	/**
	 * Put to velocity map.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#putToVelocityMap()
	 *      RepositoryClass
	 */
	@Override
	public void putToVelocityMap() {
		putToVelocityMapDef();
		map.put("hasDb", hasDb);
		map.put("methods", methodSb.toString());
		map.put("isSlave", isSlave);
	}
	
	/**
	 * 生成设计查询条件的where条件sql.
	 *
	 * @author rongxin.bian
	 * @param editor
	 *            编辑器
	 * @param dictBusinessClass
	 *            被当做数据字典的业务实体
	 * @return where条件sql
	 */
	private String getFilterSql(PropertyEditor editor,
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
	 * @param varchar
	 *            the varchar
	 * @param dbType
	 *            the db type
	 * @return the string
	 */
	private String convertToDate(String varchar, String dbType) {
		if (StringUtil.isEmpty(varchar)) {
			return null;
		}

		String result = "";

		if (DBType.Oracle.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
//		} else if (DBType.DB2.getDbType().equals(dbType)) {
//			result = "date(trim(char('" + varchar + "')))";
		}/* else if (DBType.DM.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
		} else if (DBType.KingBase.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
		} */else if (DBType.MsSQL.getDbType().equals(dbType)) {
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
	 * Convert to time.
	 *
	 * @author mqfdy
	 * @param varchar
	 *            the varchar
	 * @param dbType
	 *            the db type
	 * @return the string
	 * @Date 2018-9-3 11:38:38
	 */
	private String convertToTime(String varchar, String dbType) {
		if (StringUtil.isEmpty(varchar)) {
			return null;
		}

		String result = "";

		if (DBType.Oracle.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
//		} else if (DBType.DB2.getDbType().equals(dbType)) {
//			result = "date(trim(char('" + varchar + "')))";
		}/* else if (DBType.DM.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
		} else if (DBType.KingBase.getDbType().equals(dbType)) {
			result = "to_date('" + varchar + "','yyyy-MM-dd')";
		}*/ else if (DBType.MsSQL.getDbType().equals(dbType)) {
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
	 * Convert to time stamp.
	 *
	 * @author mqfdy
	 * @param varchar
	 *            the varchar
	 * @param dbType
	 *            the db type
	 * @return the string
	 * @Date 2018-9-3 11:38:38
	 */
	private String convertToTimeStamp(String varchar, String dbType) {
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
	 * Gets the db type.
	 *
	 * @author mqfdy
	 * @return the db type
	 * @Date 2018-09-03 09:00
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * Sets the db type.
	 *
	 * @author mqfdy
	 * @param dbType
	 *            the new db type
	 * @Date 2018-09-03 09:00
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

}
