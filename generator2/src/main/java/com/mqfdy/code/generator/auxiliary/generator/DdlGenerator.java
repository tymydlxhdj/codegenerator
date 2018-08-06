package com.mqfdy.code.generator.auxiliary.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.DependantValue;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.JoinedSubclass;
import org.hibernate.mapping.ManyToOne;
import org.hibernate.mapping.MetaAttribute;
import org.hibernate.mapping.OneToMany;
import org.hibernate.mapping.OneToOne;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.SimpleValue;
import org.hibernate.mapping.SingleTableSubclass;
import org.hibernate.mapping.Subclass;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.UnionSubclass;
import org.hibernate.mapping.Value;
import org.hibernate.tool.hbm2x.StringUtils;
import org.hibernate.type.TypeFactory;

import com.mqfdy.code.generator.exception.CodeGeneratorException;
import com.mqfdy.code.generator.utils.GeneratorUitl;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.model.utils.ValidatorType;

/**
 * 生成ddl类
 * 
 * @author mqf
 * 
 */
public class DdlGenerator {
	private Configuration configuration = new Configuration();// pojo的configuration对象（排除了dto，非持久化属性）
	private Mappings mappings;
	
	private Configuration hbmConfiguration = new Configuration();// hbm的configuration对象
	private Mappings hbmMappings;

	private static final String DEFAULT_ID_DATATYPE = Hibernate.STRING.getReturnedClass().getName();// java.lang.String
	private static final String DEFAULT_ID_NAME = "id";
	private static final String DEFAULT_ID_DBNAME = "ID";
	private static final String DEFAULT_ID_GENERATOR_STRATEGY = "uuid";
	private static final String DEFAULT_DISCRIMINATOR_TYPE_NAME = "TYPE";
	private static final String DELETE_STRING = "delete";

	private List<PersistentClass> dtoList = new ArrayList<PersistentClass>();// dto对象集合
	private List<PersistentClass> referenceSet = new ArrayList<PersistentClass>();// 引用业务实体集合
	private List<PersistentClass> reverseSet = new ArrayList<PersistentClass>();//反向业务实体集合

	private String dbType;
	public Map<BusinessClass, PersistentClass> map;
	private Map<Property, PersistentClass> notPersistentProperty = new HashMap<Property, PersistentClass>();
	private String loadPackage = null;
	private String exportHbmPath = null;

	private PersistentClass lastPc;
	
	private Map<String, PersistenceProperty> uncreatePropertiesMap = new HashMap<String, PersistenceProperty>();	//记录不生成的属性

	public DdlGenerator() {
		this.mappings = this.configuration.createMappings();
		this.hbmMappings = this.hbmConfiguration.createMappings();
	}

	/**
	 * 1、将所有的业务实体都生成PersistentClass对象并放到map中便于BusinessClass与PersistentClass对象对应
	 * 2、
	 */
	public void init(BusinessObjectModel bom, String exportHbmPath, String dbType,boolean isHBM) {
		this.loadPackage = StringUtil.getLoadPackage(exportHbmPath);// com.sgcc
		this.exportHbmPath = exportHbmPath;
		this.dbType = dbType;

		Map<BusinessClass, PersistentClass> map = this.buildRootclassWithAllSubclassByBom(bom,dbType);// 继承链的根集合
		this.buildAssociation(map, bom,isHBM);// 关联关系

		Set<BusinessClass> bcs = map.keySet();
		for (Iterator<BusinessClass> iterator = bcs.iterator(); iterator.hasNext();) {
			BusinessClass bc = iterator.next();
			if (IModelElement.STEREOTYPE_BUILDIN.equals(bc.getStereotype())) {
				continue;
			}
			PersistentClass pc = map.get(bc);
			this.createNormalProperty(pc, bc);
		}

		// 取出bom中所有DTO对象
		this.buildRootclassOfDTO(bom);

		this.createHbmConfiguration();
	}
	/**
	 * 创建dto对象的rootclass
	 * 
	 * @param bom
	 */
	private void buildRootclassOfDTO(BusinessObjectModel bom) {
		List<DataTransferObject> dtos = bom.getDTOs();
		for (DataTransferObject dto : dtos) {
			RootClass rc = new RootClass();
			rc.setTable(new Table("temp"));
			
			String fullPackagePath = this.loadPackage + "."+ dto.getParent().getParent().getName() + ".po.";
			String fullClassPath = fullPackagePath + dto.getName();
			rc.setEntityName(fullClassPath);// 映射文件名(包名+模块名+标示符+类名)
			rc.setClassName(fullClassPath);// 设置类名

			List<com.mqfdy.code.model.Property> dtoProps = dto.getProperties();
			for (com.mqfdy.code.model.Property dtoProp : dtoProps) {
				SimpleValue value = new SimpleValue();
				String typeName = TypeFactory.basic(dtoProp.getDataType()).getReturnedClass().getName();
				value.setTypeName(typeName);

				Property prop = makeProperty(dtoProp.getName(), value, true,true, false, null, null);
				rc.addProperty(prop);
			}
			dtoList.add(rc);
		}
	}

	/**
	 * 创建RootClass对象，默认构造单主键id
	 * 
	 * @param className
	 *            用于构造RootClass对象和Table对象，table名默认使用类名的全大写
	 * @return
	 */
	private RootClass buildRootClass(String className, BusinessClass bc,String dbType) {// 需要加入对类名的合法性校验
		RootClass rc = new RootClass();
		String fullPackagePath = null;
		if (IModelElement.STEREOTYPE_REFERENCE.equals(bc.getStereotype())) {
			BusinessObjectModel bom = GeneratorUitl.getBusinessModelOfElement(bc);
			fullPackagePath = bom.getNameSpace() + "." + bom.getName() + ".po.";
		} else {
			fullPackagePath = this.loadPackage + "."+ bc.getBelongPackage().getName() + ".po.";
		}
		String fullClassPath = getDomainFullName(bc);// fullPackagePath+bc.getName();
		rc.setEntityName(fullClassPath);// 映射文件名(包名+模块名+标示符+类名)
		rc.setClassName(fullClassPath);// 设置类名
		rc.setProxyInterfaceName(fullClassPath);
		rc.setLazy(true);

		if (IModelElement.STEREOTYPE_BUILDIN.equals(bc.getStereotype())) {
			return rc;
		}

		// 构建表对象
		String tableName = StringUtil.getClassNameFromFullName(className).toUpperCase(Locale.getDefault());// 表名
		if (!StringUtil.isEmpty(bc.getTableName())) {
			tableName = bc.getTableName().toUpperCase(Locale.getDefault());
		}

		Table table = this.hbmMappings.addTable(null, null, tableName, null,false);// new Table(tableName);//类名变成小写即为表名，默认
		table.setComment(bc.getDisplayName());
		rc.setTable(table);

		// 构造主键
		this.createIdProperty(rc, bc, table,dbType);

		return rc;
	}
	
	/**
	 *获取实体类名
	 * @return
	 */
	public String getDomainFullName( BusinessClass bc) {
		if (bc.getParent() != null) {
			return bc.getParent().getFullName() + ".domain." + bc.getName();
		} else {
			return bc.getName();
		}

	}

	/**
	 * 创建单主键，默认主键名为id
	 * 
	 * @param rc
	 */
	private void createIdProperty(RootClass rc, BusinessClass bc, Table table,String dbType) {

		List<com.mqfdy.code.model.Property> props = bc.getProperties();
		PKProperty pkProp = null;
		for (com.mqfdy.code.model.Property prop : props) {
			if (prop instanceof PKProperty) {
				pkProp = (PKProperty) prop;
			} else {
				continue;
			}
		}
		if (pkProp == null) {
			return;
		}
		String idDataType = DEFAULT_ID_DATATYPE;
		String idGeneratorStrategy = DEFAULT_ID_GENERATOR_STRATEGY;
		String idPropertyName = DEFAULT_ID_NAME;
		String idDbName = null;
		Properties properties = new Properties();
		String dblength = null;

		if (pkProp != null) {
			idDataType = pkProp.getDataType();
			idGeneratorStrategy = pkProp.getPrimaryKeyPloy().toLowerCase(Locale.getDefault());
			idPropertyName = pkProp.getName();
			idDbName = pkProp.getdBColumnName();
			
			//add by xuran
			if(dbType!= null && dbType.equals(DBType.MySQL.getDbType()) && idGeneratorStrategy.equals("sequence")){
				idGeneratorStrategy = "identity";
			}else{
			//add by xuran 
				if (!pkProp.getPrimaryKeyParams().isEmpty()) {
					Set<String> keySet = pkProp.getPrimaryKeyParams().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						String value = pkProp.getPrimaryKeyParams().get(key);
						properties.put("sequence", value);
					}
				}
			}
			dblength = pkProp.getdBDataLength();
		}

		SimpleValue idValue = new SimpleValue(rc.getTable());
		idValue.setTypeName(TypeFactory.basic(idDataType).getReturnedClass()
				.getName());// 主键数据类型
		idValue.setIdentifierGeneratorStrategy(idGeneratorStrategy);// 主键生成策略
		idValue.setIdentifierGeneratorProperties(properties);// 主键生成策略参数列表

		// 设置table主键 start
		Column stuIdCol = new Column(DEFAULT_ID_DBNAME);// 主键列
		if (!StringUtil.isEmpty(idDbName)) {
			stuIdCol = new Column(idDbName.toUpperCase(Locale.getDefault()));
		}
		// 给主键加注释
		stuIdCol.setComment(pkProp.getDisplayName());
		stuIdCol.setNullable(false);
		PrimaryKey pk = new PrimaryKey();// 设置主键
		pk.addColumn(stuIdCol);
		pk.setName("PK_" + table.getName().toUpperCase(Locale.getDefault()));

		table.setPrimaryKey(pk);
		table.setQuoted(false);
		if (StringUtil.isNumeric(dblength)) {
			stuIdCol.setLength(Integer.valueOf(dblength));// 主键列长度
		}
		table.addColumn(stuIdCol);
		// 设置table主键 end

		idValue.addColumn(stuIdCol);// 主键列名
		Property pkProperty = makeProperty(idPropertyName, idValue, true, true,
				false, null, null);
		this.addValidators(pkProp, rc, pkProperty, bc);
		rc.setIdentifierProperty(pkProperty);  
		rc.setIdentifier(idValue);
	}

	/**
	 * 取出属性中的所有验证，转换为javax的注解。不能对应的用正则表达式
	 * 
	 * @param prop
	 */
	private void addValidators(com.mqfdy.code.model.Property prop,
			PersistentClass pc, Property property, BusinessClass bc) {

		Map pcMetaAttributes = new HashMap();
		if (pc.getMetaAttributes() == null) {
			pc.setMetaAttributes(pcMetaAttributes);
		}

		MetaAttribute annotationMeta = new MetaAttribute("annotation-validator");
		MetaAttribute remarkMeta = new MetaAttribute("pojo-remark");
		remarkMeta.addValue("/** " + prop.getDisplayName() + "*/_+");
		pc.getMetaAttributes().put(property.getName(), annotationMeta);
		pc.getMetaAttributes().put(property.getName() + "1", remarkMeta);

		List<Validator> vs = prop.getValidators();
		for (Validator v : vs) {
			if (ValidatorType.Nullable.getValue().equals(v.getValidatorType())) {// 非空校验
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				annotationMeta
						.addValue("org.hibernate.validator.constraints.NotBlank(message=\""
								+ message + "\")+_");
			}
			if (ValidatorType.StringLength.getValue().equals(v.getValidatorType())) {// 长度
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String min = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("minLength"));
				String max = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("maxLength"));
				annotationMeta.addValue("org.hibernate.validator.constraints.Length(min=" + min + ",max=" + max + ",message=\"" + message + "\")+_");
			}
			if (ValidatorType.CNString.getValue().equals(v.getValidatorType())) {// 中文字符串
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String min = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("minLength"));
				String max = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("maxLength"));
				annotationMeta.addValue("com.sgcc.uap.service.validator.constraints.ZNStr(min=\"" + min + "\",max=\"" + max + "\",message=\"" + message + "\")+_");
			}
			if (ValidatorType.ENString.getValue().equals(v.getValidatorType())) {// 英文字符串
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String min = StringUtil.convertNull2EmptyStr(v
						.getValidatorParams().get("minLength"));
				String max = StringUtil.convertNull2EmptyStr(v
						.getValidatorParams().get("maxLength"));
				annotationMeta
						.addValue("com.sgcc.uap.service.validator.constraints.ENStr(min=\""
								+ min
								+ "\",max=\""
								+ max
								+ "\",message=\""
								+ message + "\")+_");
			}
			if (ValidatorType.DateTime.getValue().equals(v.getValidatorType())) {// 日期格式
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String min = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("minDate"));
				String max = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("maxDate"));
				
				if(!"".equals(min)){
					if(!min.contains("-")){
						min = "1970-1-1 "+min;
					}
				}
				if(!"".equals(max)){
					if(!max.contains("-")){
						max = "1970-1-1 "+max;
					}
				}
				
				annotationMeta.addValue("com.sgcc.uap.service.validator.constraints.DateRange(min=\""
								+ min
								+ "\",max=\""
								+ max
								+ "\",message=\""
								+ message + "\")+_");
			}
			if (ValidatorType.Email.getValue().equals(v.getValidatorType())) {// 邮箱地址
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				annotationMeta.addValue("org.hibernate.validator.constraints.Email(message=\""+ message + "\")+_");
			}
			if (ValidatorType.Integer.getValue().equals(v.getValidatorType())) {// 必须为整数
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String min = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("minValue"));
				String max = StringUtil.convertNull2EmptyStr(v.getValidatorParams().get("maxValue"));
				
				annotationMeta.addValue("com.sgcc.uap.service.validator.constraints.IntegerRange(min=\""
								+ min
								+ "\",max=\""
								+ max
								+ "\",message=\""
								+ message + "\")+_");
			}
			if (ValidatorType.Number.getValue().equals(v.getValidatorType())) {// 必须为数字
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String min = StringUtil.convertNull2EmptyStr(v
						.getValidatorParams().get("minValue"));
				String max = StringUtil.convertNull2EmptyStr(v
						.getValidatorParams().get("maxValue"));
				annotationMeta
						.addValue("com.sgcc.uap.service.validator.constraints.Figure(min=\""
								+ min
								+ "\",max=\""
								+ max
								+ "\",message=\""
								+ message + "\")+_");
			}
			if (ValidatorType.PastCode.getValue().equals(v.getValidatorType())) {// 邮编
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				annotationMeta
						.addValue("com.sgcc.uap.service.validator.constraints.Postcode(message=\""
								+ message + "\")+_");
			}
			if (ValidatorType.Regular.getValue().equals(v.getValidatorType())) {// 正则表达式
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String expression = v.getValidatorParams().get("expression");
				
				StringBuffer buffer = new StringBuffer();
				char[] charArr = expression.toCharArray();
				for (int i = 0; i < charArr.length - 1; i++) {
					char charactor = charArr[i];
					if (charactor == '\\') {
						if ((i + 1) < charArr.length && charArr[i + 1] == '\"') {
							buffer.append(charactor);
							buffer.append(charArr[i + 1]);
							i++;
						} else {
							buffer.append(charactor + "\\");
						}
					} else {
						buffer.append(charArr[i]);
					}
				}
				
				expression = buffer.toString();
				
				if (expression.startsWith("/")) {
					expression = expression.substring(1);
				}
				if (expression.endsWith("/")) {
					expression = expression.substring(0,
							expression.length() - 1);
				}

				annotationMeta
						.addValue("javax.validation.constraints.Pattern(regexp=\""
								+ expression
								+ "\",message=\""
								+ message
								+ "\")+_");
			}
			if (ValidatorType.URL.getValue().equals(v.getValidatorType())) {// URL地址
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				annotationMeta
						.addValue("org.hibernate.validator.constraints.URL(message=\""
								+ message + "\")+_");
			}
		/*	if (ValidatorType.Unique.getValue().equals(v.getValidatorType())) {// 唯一校验
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String priamryKeyName = bc.getPkPropertyKey();
				annotationMeta
						.addValue("com.sgcc.uap.service.validator.constraints.Unique(message=\""
								+ message
								+ "\",primarykey=\""
								+ priamryKeyName
								+ "\")+_");
			}*/
			if (ValidatorType.Custom.getValue().equals(v.getValidatorType())) {// 自定义校验
				String message = v.getValidatorMessage().replace("\r\n", "\\n");
				String className = v.getValidatorParams().get(
						"customValidatorClassName");
				BusinessObjectModel bom = GeneratorUitl
						.getBusinessModelOfElement(bc);
				String namespace = bom.getNameSpace();
				String bomName = bom.getName();
				String validatorStr = "validator";
				String validatorPath = namespace + "." + bomName + "."
						+ validatorStr + ".";
				annotationMeta
						.addValue("com.sgcc.uap.service.validator.constraints.UserDefine(className=\""
								+ validatorPath
								+ className
								+ "\",message=\""
								+ message + "\")+_");
			}
		}
	}

	/**
	 * 创建普通属性
	 * 
	 * @param pc
	 * @param bc
	 */
	@SuppressWarnings("static-access")
	private void createNormalProperty(PersistentClass pc, BusinessClass bc) {

		List<com.mqfdy.code.model.Property> props = bc.getProperties();
		for (com.mqfdy.code.model.Property prop : props) {
			if (prop instanceof PKProperty) {// 如果是主键就跳过，
				continue;
			}
			
			String propName = prop.getName();
			String dataType = prop.getDataType();
			String dbColName = null;
			String dbLength = "0";
			String dbPrecision = "0";
			String defaultValue = null;
			boolean nullable = true;
			boolean unique = false;
			boolean isPersistence = false;
			boolean isIndexedColumn = false;

			if (prop instanceof PersistenceProperty) {
				dbColName = ((PersistenceProperty) prop).getdBColumnName();
				dbLength = ((PersistenceProperty) prop).getdBDataLength();
				dbPrecision = ((PersistenceProperty) prop).getdBDataPrecision();
				defaultValue = ((PersistenceProperty) prop).getDefaultValue();
				nullable = ((PersistenceProperty) prop).isNullable();
				unique = ((PersistenceProperty) prop).isUnique();
				isIndexedColumn = ((PersistenceProperty) prop).isIndexedColumn();// 是否为索引列
				isPersistence = true;
			}

			Column col = new Column(propName.toUpperCase(Locale.getDefault()));
			if (!StringUtil.isEmpty(dbColName)) {
				col = new Column(dbColName.toUpperCase(Locale.getDefault()));
			}
			col.setUnique(unique);
			col.setNullable(nullable);
			// hibernate会默认将java.util.Date类型转为timestamp，在这里强制转为数据库的date类型
			// time和timestamp不做处理，默认转为timestamp类型
			if (dataType.equals("date")) {
				col.setSqlType("date");
			}

			if (dataType.equals("clob")) {
				col.setSqlType("clob");
			}

			if (dataType.equals("string") && Integer.valueOf(dbLength) >= 4000 && dbType.equalsIgnoreCase("oracle")) {//
				col.setSqlType("clob");
			}else if (dataType.equals("clob") && (dbType.equalsIgnoreCase(DBType.MySQL.getDbType())||
					dbType.equalsIgnoreCase(DBType.POSTGRESQL.getDbType()))) {//
				col.setSqlType("text");
			}
			col.setComment(prop.getDisplayName());

			SimpleValue value = new SimpleValue();

			if ("clob".equals(dataType)) {
				dataType = "string";
			}
			if (!StringUtil.isEmpty(dataType)) {
				String propType = TypeFactory.basic(dataType).getReturnedClass().getName();
				value.setTypeName(propType);
				if ("boolean".equals(dataType)) {
					value.setTypeName("boolean");
				}
			} else {
				throw new CodeGeneratorException("属性类型不能为空");
			}

			if (isIndexedColumn) {
				// Index index = new Index();
				// index.setName("name_index");
				// index.addColumn(col);
				//
				// Dialect dialect =
				// DialectFactory.buildDialect("org.hibernate.dialect.Oracle9Dialect");
				//
				// String indexStr = index.buildSqlCreateIndexString(dialect,
				// "name_index", pc.getTable(),
				// index.getColumnIterator(), false, null, null);
				// index.setTable(pc.getTable());
				//
				// pc.getTable().addIndex(index);

			}

			// 设置ddl列的default
			// defaultValue = StringUtil.handledefaultValue(dataType
			// ,defaultValue);
			// col.setDefaultValue(defaultValue);
			if (!StringUtil.isEmpty(dbLength) && StringUtil.isNumeric(dbLength)) {
				col.setLength(Integer.valueOf(dbLength));
				if (dbPrecision != null && StringUtil.isNumeric(dbPrecision)) {
					col.setPrecision(Integer.valueOf(dbLength));
					col.setScale(Integer.valueOf(dbPrecision));
				}
			}
			value.addColumn(col);
			if (isPersistence) {// 判断是否持久化属性，是则加入Table中生成ddl
				pc.getTable().addColumn(col);
			}

			if (StringUtil.isEmpty(propName)) {
				throw new CodeGeneratorException("属性名不能为空");
			}
			
			String propertyKey = bc.getBelongPackage().getName() + "." + bc.getName() + "." + prop.getName();
			if (uncreatePropertiesMap.get(propertyKey) != null) {
				continue;
			}
			
			Property property = makeProperty(propName, value, true, true, false, null, null);
			this.addValidators(prop, pc, property, bc);
			if (isPersistence) {// 判断是否持久化属性
				pc.addProperty(property);
				continue;
			}
			notPersistentProperty.put(property, pc);
		}
	}

	/**
	 * 将PersistentClass对象加入到Mappings对象中
	 * 
	 * @param pc
	 */
	private void addPcToMappings(PersistentClass pc) {
		if (pc == null) {
			throw new CodeGeneratorException("null 不能加入到mappings对象中");
		}
		this.mappings.addClass(pc);
	}

	/**
	 * 在一方创建set、key、one-to-many，需要判断是否一对一的情况（unique=true）
	 * 
	 * @param opc
	 *            一方
	 * @param mpc
	 *            多方
	 * @param isOne2One
	 *            是否一对一
	 * @param is2Way
	 *            是否单向的
	 */
	private Collection createOne2Many(PersistentClass opc, PersistentClass mpc,
			boolean isOne2One, String cascadeDelete, String oneRoleName,
			String foreignKeyColumnName) {

		OneToMany oneToMany = new OneToMany(opc);
		oneToMany.setReferencedEntityName(mpc.getClassName());// 子表类名
		oneToMany.setAssociatedClass(mpc);

		SimpleValue keyValue = new DependantValue(mpc.getTable(),opc.getIdentifier());
		String fkColName = GeneratorUitl.createFKColName(opc);
		if (mpc.equals(opc)) {
			fkColName = "parent_id";
		}
		if (!StringUtil.isEmpty(foreignKeyColumnName)) {
			fkColName = foreignKeyColumnName;
		}
		Column keyCol = new Column(fkColName);// 在多方表中加入外键列
		if (isOne2One) {// One2One
			keyCol.setUnique(true);
		}
		keyValue.addColumn(keyCol);

		List<Column> cols = new ArrayList<Column>();
		cols.add(keyCol);

		String fkName = "FK_"
				+ StringUtil.getClassNameFromFullName(mpc.getClassName()) + "_"
				+ StringUtil.getClassNameFromFullName(opc.getClassName()) + "_"
				+ GeneratorUitl.createFKColName(opc).replace("_", "");
		if (fkName.length() > 30) {
			fkName = null;
		}
		ForeignKey fk = mpc.getTable().createForeignKey(fkName, cols, opc.getEntityName());
		fk.setCascadeDeleteEnabled(false);
		mpc.getTable().addColumn(keyCol);

		Collection collection = new org.hibernate.mapping.Set(opc);
		collection.setCollectionTable(mpc.getTable());// 加入子表
		collection.setInverse(false);
		collection.setElement(oneToMany);
		collection.setFetchMode(FetchMode.SELECT);
		collection.setLazy(true);
		collection.setKey(keyValue);

		String propName = StringUtil.getClassNameFromFullName(mpc
				.getClassName().toLowerCase(Locale.getDefault())) + "s";// 多方类名+s，作为在多方的属性名称
		if (opc.equals(mpc)) {
			propName = "children";
		}
		if (!StringUtil.isEmpty(oneRoleName)) {// 如果有自定义的一方属性名
			propName = oneRoleName;
		}
		Property _1_m_property = makeProperty(propName, collection, true, true,
				true, cascadeDelete, null);
		opc.addProperty(_1_m_property);
		addPropertyTypeMeta(_1_m_property, mpc);

		return collection;
	}

	/**
	 * 创建多对多的情况
	 * 
	 * @param m1pc
	 *            多方其中的一个
	 * @param m2pc
	 *            多方其中的一个
	 */
	private Collection createMany2Many(PersistentClass mspc, PersistentClass mrpc, String cascadeDelete, String setName, String ownFkColName, String otherFkColName, String relationTableName) {
		// 创建中间表对象
		String thirdTableName = GeneratorUitl.createMiddleTableName(mspc, mrpc);
		if (!StringUtil.isEmpty(relationTableName)) {
			thirdTableName = relationTableName;
		}
		if (StringUtil.isEmpty(thirdTableName)) {
			throw new CodeGeneratorException("中间表创建错误");
		}
		Table middleTable = this.hbmMappings.addTable(null, null,
				thirdTableName.toUpperCase(Locale.getDefault()), null, false);

		// 创建ManyToOne对象
		ManyToOne manyToOne = new ManyToOne(mrpc.getTable());
		manyToOne.setReferencedEntityName(mrpc.getClassName());
		manyToOne.setEmbedded(true);
		manyToOne.setFetchMode(FetchMode.SELECT);

		String otherFkColName_ = GeneratorUitl.createFKColName(mrpc);
		if (!StringUtil.isEmpty(otherFkColName)) {

			otherFkColName_ = otherFkColName;
		}
		Column m2mCol = new Column(otherFkColName_);// 放置中间表中引用另一个表的外建名
		if (mspc.equals(mrpc)) {
			m2mCol = new Column("Attach_" + GeneratorUitl.createFKColName(mrpc));
		}
		manyToOne.addColumn(m2mCol);

		List<Column> cols1 = new ArrayList<Column>();
		cols1.add(m2mCol);
		ForeignKey fk = middleTable.createForeignKey(null, cols1, mrpc.getEntityName());
		fk.setCascadeDeleteEnabled(false);
		middleTable.addColumn(m2mCol);

		SimpleValue keyValue = new DependantValue(mspc.getTable(), mspc.getIdentifier());

		String ownFkColName_ = GeneratorUitl.createFKColName(mspc);
		if (!StringUtil.isEmpty(ownFkColName)) {
			ownFkColName_ = ownFkColName;
		}
		Column keyCol = new Column(ownFkColName_);// 放置第中间表中引用自己主键的外键名
		if (mspc.equals(mrpc)) {
			keyCol = new Column("Major_" + GeneratorUitl.createFKColName(mrpc));
		}
		keyValue.addColumn(keyCol);

		List<Column> cols2 = new ArrayList<Column>();
		cols2.add(keyCol);
		String fk2Name = "FK_"
				+ StringUtil.getClassNameFromFullName(middleTable.getName())
						.replace("_", "")
				+ "_"
				+ StringUtil.getClassNameFromFullName(mspc.getClassName())
						.replace("_", "") + "_"
				+ keyCol.getName().replace("_", "");
		String[] tns = middleTable.getName().split("_");
		while (fk2Name.length() > 30) {

			tns[0] = tns[0].substring(0, tns[0].length() - 1);
			tns[1] = tns[1].substring(0, tns[1].length() - 1);
			fk2Name = "FK_"+ (tns[0] + tns[1])+ "_"
					+ StringUtil.getClassNameFromFullName(mspc.getClassName()).replace("_", "") + "_"
					+ keyCol.getName().replace("_", "");
			if (tns[0].length() <= 1 || tns[1].length() <= 1) {
				fk2Name = null;
				break;
			}
		}
		middleTable.createForeignKey(fk2Name, cols2, mspc.getEntityName());
		middleTable.addColumn(keyCol);

		Collection collection = new org.hibernate.mapping.Set(mspc);
		collection.setCollectionTable(middleTable);
		collection.setElement(manyToOne);
		collection.setKey(keyValue);
		collection.setFetchMode(FetchMode.SELECT);
		collection.setLazy(true);
//		if (mspc.equals(mrpc)) {
//			if (mrpc == lastPc) {
//				String pName = "major_" + GeneratorUitl.createSetName(mrpc);
//				if (!StringUtil.isEmpty(setName)) {
//					pName = setName;
//				}
//				Property m_m_property = makeProperty(pName, collection, true,
//						true, false, cascadeDelete, null);
//				mspc.addProperty(m_m_property);
//			} else {
//				String pName = "attach_" + GeneratorUitl.createSetName(mrpc);
//				if (!StringUtil.isEmpty(setName)) {
//					pName = setName;
//				}
//				Property m_m_property = makeProperty(pName, collection, true,
//						true, false, cascadeDelete, null);
//				mspc.addProperty(m_m_property);
//				lastPc = mspc;
//			}
//		} else {
			String pName = GeneratorUitl.createSetName(mrpc);
			if (!StringUtil.isEmpty(setName)) {
				pName = setName;
			}
			Property m_m_property = makeProperty(pName, collection, true, true,
					true, cascadeDelete, null);
			mspc.addProperty(m_m_property);
			addPropertyTypeMeta(m_m_property, mrpc);
//		}

		return collection;
	}

	/**
	 * 在多方创建many-to-one，包含一对一的情况
	 * 
	 * @param mpc
	 *            多方
	 * @param opc
	 *            一方
	 * @param an 
	 */
	private void createMany2One(PersistentClass mpc, PersistentClass opc,
			boolean isOneToOne, String cascadeDelete, String setName,
			String foreignKeyColumnName, Association an) {

		ManyToOne m_1_value = new ManyToOne(mpc.getTable());
		m_1_value.setReferencedEntityName(opc.getClassName());
		m_1_value.setFetchMode(FetchMode.SELECT);

		String propertyName = StringUtil.getClassNameFromFullName(opc
				.getClassName().toLowerCase(Locale.getDefault()));// 外键属性名
		if (mpc.equals(opc)) {
			propertyName = "parent";
		}
		if (!StringUtil.isEmpty(setName)) {// 如果有自定义的一方属性名
			propertyName = setName;
		}

		String fkColName = GeneratorUitl.createFKColName(opc);// 外键列名
		if (mpc.equals(opc)) {
			fkColName = "parent_id";
		}
		if (!StringUtil.isEmpty(foreignKeyColumnName)) {
			fkColName = foreignKeyColumnName;
		}

		Column m_1_col = new Column(fkColName.toUpperCase(Locale.getDefault()));
		m_1_value.addColumn(m_1_col);
		if (isOneToOne) {
			m_1_col.setUnique(true);
		}

		// add not null
		// Association.NOTNULLFKA Association.NOTNULLFKB
		// TODO 外键非空设置
		// m_1_col.setNullable(false);
		boolean notNull = "true".equalsIgnoreCase(an.getPersistencePloyParams().get(Association.NOTNULLFKA))
				|| "true".equalsIgnoreCase(an.getPersistencePloyParams().get(Association.NOTNULLFKB));
		if(notNull)
			m_1_col.setNullable(false);
		List<Column> cols = new ArrayList<Column>();
		cols.add(m_1_col);
		String fkName = "FK_"
				+ StringUtil.getClassNameFromFullName(mpc.getClassName()) + "_"
				+ StringUtil.getClassNameFromFullName(opc.getClassName()) + "_"
				+ fkColName.replace("_", "");
		if (fkName.length() > 30) {
			fkName = null;
		}
		ForeignKey fk = mpc.getTable().createForeignKey(fkName, cols,
				opc.getEntityName());
		fk.setCascadeDeleteEnabled(false);
		mpc.getTable().addColumn(m_1_col);

		// opc.getClassName().toLowerCase(Locale.getDefault())为一方的类名的小写，作为在多方中的关联属性名
		Property m_1_property = makeProperty(propertyName, m_1_value, true,
				true, true, cascadeDelete, null);
		mpc.addProperty(m_1_property);
	}
	
	/**
	 * 创建主键关联一对一
	 */
	private void createPKOne2One(PersistentClass opc, PersistentClass mpc, String oPropName, String nPropName, String cascadeDelete,String port,Association an) {
		OneToOne o_o_value = new OneToOne(opc.getTable(), opc);
		o_o_value.setReferencedEntityName(mpc.getClassName());
		o_o_value.setFetchMode(FetchMode.SELECT);
		o_o_value.setCascadeDeleteEnabled(false);
		if(port.equals("zi")){
			o_o_value.setConstrained(true);
			
			SimpleValue idValue = (SimpleValue) opc.getIdentifier();
			((SimpleValue) idValue).setIdentifierGeneratorStrategy("foreign");// 主键生成策略
			
			Properties properties = new Properties();
			properties.put("property", an.getNavigateToClassARoleName());
			idValue.setIdentifierGeneratorProperties(properties);// 主键生成策略参数列表
		}

		String propertyName = StringUtil.getClassNameFromFullName(mpc.getClassName().toLowerCase(Locale.getDefault()));
		if (!StringUtil.isEmpty(oPropName)) {
			propertyName = oPropName;
		}
		Property c_1_property = makeProperty(propertyName, o_o_value, true, true, true, cascadeDelete, null);
		opc.addProperty(c_1_property);
	}

	/**
	 * 创建一对一(在一方创建one-to-one)
	 */
	private void createOne2One(PersistentClass opc, PersistentClass mpc, String oPropName, String nPropName, String cascadeDelete) {
		OneToOne o_o_value = new OneToOne(opc.getTable(), opc);
		o_o_value.setReferencedEntityName(mpc.getClassName());
		o_o_value.setFetchMode(FetchMode.SELECT);
		o_o_value.setReferencedPropertyName(nPropName);
		o_o_value.setCascadeDeleteEnabled(false);

		String propertyName = StringUtil.getClassNameFromFullName(mpc.getClassName().toLowerCase(Locale.getDefault()));
		if (!StringUtil.isEmpty(oPropName)) {
			propertyName = oPropName;
		}
		Property c_1_property = makeProperty(propertyName, o_o_value, true,
				true, true, cascadeDelete, null);
		opc.addProperty(c_1_property);
	}

	/**
	 * 追加业务模型中关联关系
	 * 
	 * @param bcs
	 */
	public void buildAssociation(Map<BusinessClass, PersistentClass> map, BusinessObjectModel bom,boolean isHBM) {
		List<Association> ans = bom.getAssociations();// bom中关联关系集合
		
		for (Association an : ans) {
			/*如果是hbm则执行完整代码
			 * 如果是生成DDL或pojo,并且关联关系是一方内置，另一方是反向，继续执行，否则跳过(原因:在生成hbm时不生成businessOrganizationID属性)
			 * 但是在pojo时需要去加上businessOrganizationID
			*/
			if(!isHBM){
				if(!((IModelElement.STEREOTYPE_BUILDIN.equals(an.getClassA().getStereotype())&&
						IModelElement.STEREOTYPE_REVERSE.equals(an.getClassB().getStereotype()))||
						(IModelElement.STEREOTYPE_BUILDIN.equals(an.getClassB().getStereotype())&&
								IModelElement.STEREOTYPE_REVERSE.equals(an.getClassA().getStereotype())))){
					continue;
				}
			}
			BusinessClass classA = an.getClassA();
			BusinessClass classB = an.getClassB();

			// TODO
			if (IModelElement.STEREOTYPE_BUILDIN.equals(classA.getStereotype()) || IModelElement.STEREOTYPE_BUILDIN.equals(classB.getStereotype())) {
				if (IModelElement.STEREOTYPE_BUILDIN.equals(classA.getStereotype())) {// A端为内置业务实体并且不是生成HBM文件、B端不为反向实体
					if(isHBM&&
							IModelElement.STEREOTYPE_REVERSE.equals(classB.getStereotype())){
						continue;
					}
					else{
						SimpleValue value = new SimpleValue();
						value.setTypeName(TypeFactory.basic("string").getReturnedClass().getName());
						Property property = makeProperty(StringUtil.unCapitalize(classA.getName() + "ID"), value, true, true, false, null, null);
						map.get(classB).addProperty(property);

						Column column = new Column(classA.getName() + "ID");
						value.addColumn(column);
						map.get(classB).getTable().addColumn(column);

						addBuiltinMeta(map.get(classB), classA.getName());// 加内置业务实体引用
						// 增加自定义的内置组织机构属性名（BusinessOrganizaiton）
						if ("BusinessOrganization".equals(classA.getName())) {
							addBuiltinOrgNameMeta(map.get(classB), an.getNavigateToClassARoleName());
						}
						// 增加自定义的内置组织结构属性名（User）
						if ("User".equals(classA.getName())) {
							addBuiltinUserNameMeta(map.get(classB), an.getNavigateToClassARoleName());
						}
					}
				}

				if (IModelElement.STEREOTYPE_BUILDIN.equals(classB.getStereotype())) {
					if(isHBM&&
							IModelElement.STEREOTYPE_REVERSE.equals(classA.getStereotype())){
						continue;
					}
					else{
						SimpleValue value = new SimpleValue();
						value.setTypeName(TypeFactory.basic("string").getReturnedClass().getName());
						Property property = makeProperty(StringUtils.uncapitalise(classB.getName() + "ID"), value, true, true, false, null, null);
						map.get(classA).addProperty(property);

						Column column = new Column(classB.getName() + "ID");
						value.addColumn(column);
						map.get(classA).getTable().addColumn(column);

						addBuiltinMeta(map.get(classA), classB.getName());
						// 增加自定义的内置组织机构属性名（BusinessOrganizaiton）
						if ("BusinessOrganization".equals(classB.getName())) {
							addBuiltinOrgNameMeta(map.get(classA), an.getNavigateToClassBRoleName());
						}
						// 增加自定义的内置组织结构属性名（User）
						if ("User".equals(classB.getName())) {
							addBuiltinUserNameMeta(map.get(classA), an.getNavigateToClassBRoleName());
						}
					}
				}
				continue;
			}

			PersistentClass pcA = null;
			PersistentClass pcB = null;
			
			//add by rongxin.bian
			BusinessClass bizClassA = null;
			BusinessClass bizClassB = null;
			
			Set<BusinessClass> keySet = map.keySet();
			for (Iterator<BusinessClass> iterator = keySet.iterator(); iterator.hasNext();) {
				BusinessClass bc = iterator.next();
				if (bc.getId().equals(classA.getId())) {
					pcA = map.get(bc);
					bizClassA = bc;
				}
				if (bc.getId().equals(classB.getId())) {
					pcB = map.get(bc);
					bizClassB = bc;
				}
			}

			boolean is2Way = false;
			boolean navigateToClassB = an.isNavigateToClassB();
			boolean navigateToClassA = an.isNavigateToClassA();
			boolean cascadeDeleteClassB = an.isCascadeDeleteClassB();
			boolean cascadeDeleteClassA = an.isCascadeDeleteClassA();
			String navigateToClassARoleName = an.getNavigateToClassARoleName();
			String navigateToClassBRoleName = an.getNavigateToClassBRoleName();
			String relationTableName = an.getPersistencePloyParams().get(Association.RELATIONTABLENAME);
			String foreignKeyColumnInA = an.getPersistencePloyParams().get(Association.FOREIGNKEYCOLUMNINA);
			String foreignKeyColumnInB = an.getPersistencePloyParams().get(Association.FOREIGNKEYCOLUMNINB);
			
			/**
			 * 在bizClassA中搜索与foreignKeyColumnInA名称相同的属性
			 * 如果存在，说明该关联关系不是用数据库约束维护的, 此时对应的属性的配置不需要在hbm.xml中生成
			 * 使用变量‘uncreatePropertiesMap’来记录不生成的属性, key:类全限定名 + '.' + 属性名称 , value: 属性
			 * */
			PersistenceProperty aProperty = getEqualColumnProperty(foreignKeyColumnInA, bizClassA);
			if(aProperty != null) {
				uncreatePropertiesMap.put(bizClassA.getBelongPackage().getName() + "." + bizClassA.getName() + "." + aProperty.getName(), aProperty);
			}
			
			PersistenceProperty bProperty = getEqualColumnProperty(foreignKeyColumnInB, bizClassB);
			if(bProperty != null) {
				uncreatePropertiesMap.put(bizClassB.getBelongPackage().getName() + "." + bizClassB.getName() + "." + bProperty.getName(), bProperty);
			}
			
			if (navigateToClassA && navigateToClassB) {
				is2Way = true;
			}

			String associationType = an.getAssociationType();
			if (AssociationType.one2one.getValue().equals(associationType)) {
				// 主键关联一对一（反向后均为双向，不考虑单向问题）
				if (isPkO2o(classA, pcA, classB, pcB, an)) {
					this.createPKOne2One(pcA, pcB, navigateToClassBRoleName, navigateToClassARoleName, cascadeDeleteClassB ? DELETE_STRING : null, "pa", an);
					this.createPKOne2One(pcB, pcA, navigateToClassARoleName, navigateToClassBRoleName, cascadeDeleteClassA ? DELETE_STRING : null, "zi", an);
					addPropMeta(pcB, pcA, navigateToClassARoleName);
				} else {
					// 单向1-1
					if (navigateToClassB && !navigateToClassA) {// 单向一对一
						this.createMany2One(pcA, pcB, true, cascadeDeleteClassB ? DELETE_STRING : null, navigateToClassBRoleName, foreignKeyColumnInA,an);
					} else if (navigateToClassA && !navigateToClassB) {// 单向一对一
						this.createMany2One(pcB, pcA, true, cascadeDeleteClassA ? DELETE_STRING : null, navigateToClassARoleName, foreignKeyColumnInB,an);
					} else if (is2Way) {// 双向一对一

						if (!StringUtil.isEmpty(foreignKeyColumnInA)) {// 如果createFkColNameInA不为空说明用户选择了B端为主控端，因此需要在A端加@注解
							this.createMany2One(pcA, pcB, true, cascadeDeleteClassB ? DELETE_STRING : null, navigateToClassBRoleName, foreignKeyColumnInA,an);
							this.createOne2One(pcB, pcA, navigateToClassARoleName, navigateToClassBRoleName, cascadeDeleteClassA ? DELETE_STRING : null);
							addPropMeta(pcA, pcB, navigateToClassBRoleName);
						} else {
							this.createMany2One(pcB, pcA, true, cascadeDeleteClassA ? DELETE_STRING : null, navigateToClassARoleName, foreignKeyColumnInB,an);
							this.createOne2One(pcA, pcB, navigateToClassBRoleName, navigateToClassARoleName, cascadeDeleteClassB ? DELETE_STRING : null);
							addPropMeta(pcB, pcA, navigateToClassARoleName);
						}
					}
				}

			} else if (AssociationType.one2mult.getValue().equals(associationType)) {
				// 创建1-m,A为一方 B为多方
				Collection cA = null;
				if (navigateToClassB) {
					cA = this.createOne2Many(pcA, pcB, false, cascadeDeleteClassB ? DELETE_STRING : null, navigateToClassBRoleName, foreignKeyColumnInB);
				}
				if (navigateToClassA) {
					this.createMany2One(pcB, pcA, false, cascadeDeleteClassA ? DELETE_STRING : null, navigateToClassARoleName, foreignKeyColumnInB,an);
				}

				if (is2Way) {// 如果是双向
					cA.setInverse(true);// 双向将
					if (pcA.equals(pcB)) {
						addSetMeta(pcA, pcB, navigateToClassBRoleName);
					}
				}
			} else if (AssociationType.mult2one.getValue().equals(associationType)) {
				// 创建m-1,A为多方 B为一方
				Collection cB = null;
				if (navigateToClassB) {
					this.createMany2One(pcA, pcB, false, cascadeDeleteClassB ? DELETE_STRING : null, navigateToClassBRoleName, foreignKeyColumnInA,an);
				}
				if (navigateToClassA) {
					cB = this.createOne2Many(pcB, pcA, false, cascadeDeleteClassA ? DELETE_STRING : null, navigateToClassARoleName, foreignKeyColumnInA);
				}

				if (is2Way) {// 如果是双向
					cB.setInverse(true);// 双向的话就设置为true
					if (pcA.equals(pcB)) {// 如果是自关联
						addSetMeta(pcB, pcA, navigateToClassARoleName);// 将将多方set名加入到一方中
					}
				}
			} else if (AssociationType.mult2mult.getValue().equals(associationType)) {
				// 创建m-n
				Collection cA = null;
				Collection cB = null;
				if (navigateToClassB) {
					cA = this.createMany2Many(pcA, pcB, cascadeDeleteClassB ? DELETE_STRING : null, navigateToClassBRoleName, foreignKeyColumnInB, foreignKeyColumnInA, relationTableName);
				}
				if (navigateToClassA) {
					cB = this.createMany2Many(pcB, pcA, cascadeDeleteClassA ? DELETE_STRING : null, navigateToClassARoleName, foreignKeyColumnInA, foreignKeyColumnInB, relationTableName);
				}
				if (is2Way) {// 如果是双向，指定主控方
					String major = an.getMajorClassId();
					if (!StringUtil.isEmpty(major)) {
						if (!StringUtil.isEmpty(major) && major.equals(classA.getId())) {// "A".equalsIgnoreCase(major)
							cA.setInverse(false);
							cB.setInverse(true);
							// addSetMeta(pcB,pcA,navigateToClassARoleName);
						} else { // 指定"B"或者不指定，都设置B为主控方
							cB.setInverse(false);
							cA.setInverse(true);
							// addSetMeta(pcA,pcB,navigateToClassBRoleName);
						}
					}
				}
			}
		  }
		
	}

	//判断是否主键关联一对一
	private boolean isPkO2o(BusinessClass classA,PersistentClass pcA,BusinessClass classB,PersistentClass pcB,Association an) {
		
		Table mTable = pcB.getTable();
		PrimaryKey pk = mTable.getPrimaryKey();
		String pkColumnName = pk.getColumn(0).getName();//主键列名
		
		String destRelationColumnName = an.getPersistencePloyParams().get(Association.FOREIGNKEYCOLUMNINB);//外键列名
		if(pkColumnName.equalsIgnoreCase(destRelationColumnName)){
			return true;
		}
		
		return false;
	}

	private void addBuiltinUserNameMeta(PersistentClass pcMajor, String userName) {
		Map<String, MetaAttribute> metaAttributes = new HashMap<String, MetaAttribute>();
		if (pcMajor.getMetaAttributes() != null) {
			metaAttributes = pcMajor.getMetaAttributes();
		}
		pcMajor.setMetaAttributes(metaAttributes);
		MetaAttribute orgMeta = new MetaAttribute("userName");
		orgMeta.addValue(userName);
		metaAttributes.put("userName", orgMeta);

	}

	/**
	 * 增加自定义的内置组织机构属性名
	 */
	private void addBuiltinOrgNameMeta(PersistentClass pcMajor, String orgName) {
		Map<String, MetaAttribute> metaAttributes = new HashMap<String, MetaAttribute>();
		if (pcMajor.getMetaAttributes() != null) {
			metaAttributes = pcMajor.getMetaAttributes();
		}
		pcMajor.setMetaAttributes(metaAttributes);
		MetaAttribute orgMeta = new MetaAttribute("orgName");
		orgMeta.addValue(orgName);
		metaAttributes.put("orgName", orgMeta);
	}

	/** 加入内置对象代码 */
	private void addBuiltinMeta(PersistentClass pcMajor, String builtinName) {

		Map<String, MetaAttribute> metaAttributes = new HashMap<String, MetaAttribute>();
		if (pcMajor.getMetaAttributes() != null) {
			metaAttributes = pcMajor.getMetaAttributes();
		}
		pcMajor.setMetaAttributes(metaAttributes);

		MetaAttribute builtinMeta = new MetaAttribute("builtinName");
		if (metaAttributes.get("builtinName") != null) {
			builtinMeta = metaAttributes.get("builtinName");
		}
		builtinMeta.addValue(builtinName + ",");
		metaAttributes.put("builtinName", builtinMeta);

		// 加入内置对象的import
		MetaAttribute importMeta = metaAttributes.get("extra-import");
		if (importMeta == null) {
			importMeta = new MetaAttribute("extra-import");
			metaAttributes.put("extra-import", importMeta);
		}
		
		if ("BusinessOrganization".equals(builtinName)) {
			importMeta.addValue("com.sgcc.isc.core.orm.organization.BusinessOrganization");
		}
		
		if ("User".equals(builtinName)) {
			importMeta.addValue("com.sgcc.isc.core.orm.identity.User");
		}
	}

	/**
	 * 由BusinessObjectModel中封装的继承关系创建RootClass和SingleSubClass
	 * 
	 * @param cr
	 */
	private Map<BusinessClass, PersistentClass> buildRootclassWithAllSubclassByBom(BusinessObjectModel bom ,String dbType) {
		List<BusinessClass> rcs = this.getRoots(bom);// 得到继承链根集合
		Map<BusinessClass, PersistentClass> map = new HashMap<BusinessClass, PersistentClass>();// 保存实体与其对应的PersistentClass对象

		for (BusinessClass rootBC : rcs) {
			RootClass rc = this.buildRootClass(rootBC.getName(), rootBC,dbType);//构建PersistentClass对象
			map.put(rootBC, rc);
			this.buildSubclass(rc, rootBC, bom, map);
			// 内置对象与引用业务实体不加入
			if (!IModelElement.STEREOTYPE_BUILDIN.equals(rootBC.getStereotype())) {
				addPcToMappings(rc);
				// 将引用的业务实体统计出来
				if(IModelElement.STEREOTYPE_REFERENCE.equals(rootBC.getStereotype())) {
					referenceSet.add(rc);
				}
				if(IModelElement.STEREOTYPE_REVERSE.equals(rootBC.getStereotype())){
					reverseSet.add(rc);
				}
			}
		}
		// 给每个类加上注释
		//TODO
		Set<BusinessClass> bcSet = map.keySet();
		for (Iterator<BusinessClass> iterator = bcSet.iterator(); iterator.hasNext();) {
			BusinessClass bc = iterator.next();
			PersistentClass pc = map.get(bc);
			
			Map pcMetaAttributes = new HashMap();
			if (pc.getMetaAttributes() == null) {
				pc.setMetaAttributes(pcMetaAttributes);
			}

			MetaAttribute classMeta = new MetaAttribute("class-remark");
			classMeta.addValue(bc.getDisplayName());
			
			MetaAttribute dateMeta = new MetaAttribute("date-remark");
			dateMeta.addValue(StringUtil.date2String2(new Date()));
			
			MetaAttribute authorMeta = new MetaAttribute("author-remark");
			authorMeta.addValue(System.getProperty("user.name"));
			//遇到继承时，主键默认隐藏，此方法就是解决让主键显示出来，配合模板；（含分号）
			MetaAttribute inheritanceMeta = new MetaAttribute("inheritance-remark");
			inheritanceMeta.addValue("private String "+bc.getPkProperty().getName()+";");
			//遇到继承时，主键默认隐藏，此方法就是解决让主键显示出来，配合模板；（不含分号）
			MetaAttribute inheritanceMetas = new MetaAttribute("inheritance-remarks");
			inheritanceMetas.addValue(bc.getPkProperty().getName());
			
			//主要是解决注解是 显示名
			MetaAttribute inheritanceMetan = new MetaAttribute("inheritance-remarkn");
			inheritanceMetan.addValue(bc.getPkProperty().getDisplayName());
						
			pc.getMetaAttributes().put("class-remark", classMeta);
			pc.getMetaAttributes().put("date-remark", dateMeta);
			pc.getMetaAttributes().put("author-remark", authorMeta);
			pc.getMetaAttributes().put("inheritance-remark", inheritanceMeta);
			pc.getMetaAttributes().put("inheritance-remarks", inheritanceMetas);
			pc.getMetaAttributes().put("inheritance-remarkn", inheritanceMetan);
		}
		return map;
	}

	/**
	 * 一个BusinessObjectModel对象中会出现多条继承链，该方法找出各个继承链的根返回
	 * 
	 * @param bom
	 * @return
	 */
	private List<BusinessClass> getRoots(BusinessObjectModel bom) {
		List<BusinessClass> rcs = new ArrayList<BusinessClass>();

		List<BusinessClass> bcs = bom.getBusinessClasses();
		List<Inheritance> inhs = bom.getInheritances();
		for (BusinessClass bc : bcs) {//找出根业务实体加入到rcs集合中
			bc.setStereotype("1");
			String bcId = bc.getId();
			boolean flag = true;
			for (Inheritance inh : inhs) {
				String inhId = inh.getChildClass().getId();
				if (bcId.equals(inhId)) {// 在继承关系中作为childClass存在就一定不是根
					flag = false;
				}
			}
			if (flag) {
				rcs.add(bc);
			}
		}
		List<Association> as = bom.getAssociations();
		// 找出在关联关系中出现的引用对象
		for (Association a : as) {
			if (IModelElement.STEREOTYPE_REFERENCE.equals(a.getClassA() .getStereotype())) {
				BusinessClass bca = a.getClassA();
				if (!rcs.contains(bca)) {
					rcs.add(bca);
				}
			} else if (IModelElement.STEREOTYPE_REFERENCE.equals(a.getClassB() .getStereotype())) {
				BusinessClass bcb = a.getClassB();
				if (!rcs.contains(bcb)) {
					rcs.add(bcb);
				}
			}
		}
		// 找出在关联关系中出现的内置对象
		for (Association a : as) {
			if (IModelElement.STEREOTYPE_BUILDIN.equals(a.getClassA() .getStereotype())) {
				BusinessClass bca = a.getClassA();
				if (!rcs.contains(bca)) {
					rcs.add(bca);
				}
			} else if (IModelElement.STEREOTYPE_BUILDIN.equals(a.getClassB() .getStereotype())) {
				BusinessClass bcb = a.getClassB();
				if (!rcs.contains(bcb)) {
					rcs.add(bcb);
				}
			}
		}
		return rcs;
	}

	/**
	 * 递归加入所有子类
	 * 
	 * @param pc
	 * @param bc
	 * @param inhs
	 */
	private void buildSubclass(PersistentClass pc, BusinessClass bc,
			BusinessObjectModel bom, Map<BusinessClass, PersistentClass> map) {
		String bcId = bc.getId();
		List<Inheritance> inhs = bom.getInheritances();
		for (Inheritance inh : inhs) {
			String parentClassId = inh.getParentClass().getId();
			if (!StringUtil.isEmpty(parentClassId) && parentClassId.equals(bcId)) {// 找到子类
				BusinessClass childBC = inh.getChildClass();
				Subclass sts = null;

				if ("1".equals(inh.getPersistencePloy())) {
					sts = addSingleTableSubclass(childBC, pc, bom);
				} else if ("2".equals(inh.getPersistencePloy())) {
					sts = addJoinedSubclass(childBC, pc, bom);
				} else if ("3".equals(inh.getPersistencePloy())) {
					sts = addUnionSubclass(childBC, pc, bom);
				}
				// Subclass sts = addJoinedSubclass(childBC, pc);
				// Subclass sts = addUnionSubclass(childBC, pc,bom);

				// if(!(sts instanceof UnionSubclass)){
				map.put(childBC, sts);
				// }

				this.buildSubclass(sts, childBC, bom, map);
				addPcToMappings(sts);
			}
		}
	}

	/**
	 * 创建SingleTableSubclass并设置到其上级中。策略一，与父表公用一张表
	 * 
	 * @param bc
	 * @param pc
	 *            父类对象
	 * @return 返回后用于递归
	 */
	private SingleTableSubclass addSingleTableSubclass(BusinessClass bc,
			PersistentClass pc, BusinessObjectModel bom) {

		Table table = pc.getTable();
		if (pc instanceof RootClass) {
			SimpleValue discrim = new SimpleValue(table);
			discrim.setTypeName(DEFAULT_ID_DATATYPE);
			Column discrimTypeCol = new Column(DEFAULT_DISCRIMINATOR_TYPE_NAME);
			discrimTypeCol.setLength(50);
			table.addColumn(discrimTypeCol);
			discrim.addColumn(discrimTypeCol);
			((RootClass) pc).setDiscriminator(discrim);
		}

		SingleTableSubclass sc = new SingleTableSubclass(pc);
		sc.setDiscriminatorValue(bc.getName().toLowerCase(Locale.getDefault()));

		String fullPackagePath = this.loadPackage + "."+ bc.getParent().getParent().getName() + ".po.";
		String fullClassPath = fullPackagePath + bc.getName();
		sc.setClassName(fullClassPath);
		sc.setEntityName(bc.getName());

		return sc;
	}

	/**
	 * 创建JoinedSubclass并设置到其上级中。策略二，与父表各用一表，但子表主键被主表主键约束
	 * 
	 * @param bc
	 * @param pc
	 * @return 返回后用于递归
	 */
	private JoinedSubclass addJoinedSubclass(BusinessClass bc,
			PersistentClass pc, BusinessObjectModel bom) {
		String tableName = StringUtil.convertFromClassName2TableName(bc
				.getName());
		Table subTable = this.hbmMappings.addTable(null, null, tableName, null,
				false);// 创建自己的表
		subTable.setComment(bc.getDisplayName());
		JoinedSubclass sc = new JoinedSubclass(pc);
		sc.setTable(subTable);

		String fullPackagePath = this.loadPackage + "."
				+ bc.getBelongPackage().getName() + ".po.";
		String fullClassPath = fullPackagePath + bc.getName();
		sc.setClassName(fullClassPath);
		sc.setEntityName(fullClassPath);

		// 给子表加入主键，由类名的大写+"_id"
		SimpleValue key = new DependantValue(subTable, sc.getIdentifier());
		Column keyCol = new Column(GeneratorUitl.createFKColName(pc));
		key.addColumn(keyCol);
		sc.setKey(key);

		PrimaryKey pk = new PrimaryKey();
		pk.addColumn(keyCol);
		subTable.setPrimaryKey(pk);
		subTable.addColumn(keyCol);
		
		List<Column> cols = new ArrayList<Column>();
		cols.add(keyCol);
		subTable.createForeignKey(null, cols, pc.getEntityName());

		return sc;
	}

	/**
	 * 创建具体类，其父类可为抽象类。策略三，将父表中的字段都加到自己的表中
	 * 
	 * @param bc
	 * @param pc
	 * @return 返回后用于递归
	 */
	private UnionSubclass addUnionSubclass(BusinessClass bc,
			PersistentClass pc, BusinessObjectModel bom) {

		String tableName = StringUtil.convertFromClassName2TableName(bc
				.getName());

		Table subTable = this.hbmMappings.addDenormalizedTable(null, null,
				tableName, false, null, pc.getTable());

		String fullPackagePath = this.loadPackage + "." + bom.getName() + "."
				+ bc.getParent().getParent().getName() + ".po.";
		String fullClassPath = fullPackagePath + bc.getName();

		UnionSubclass sc = new UnionSubclass(pc);
		sc.createForeignKey();
		sc.setTable(subTable);
		sc.setClassName(fullClassPath);
		sc.setEntityName(fullClassPath);
		pc.addSubclass(sc);

		return sc;
	}
	/**
	 * hbm文件组装的configuration用于生成ddl，bizc
	 * 
	 * @return
	 */
	private void createHbmConfiguration() {

		@SuppressWarnings("unchecked")
		Iterator<PersistentClass> iterator = this.configuration.getClassMappings();
		while (iterator.hasNext()) {
			PersistentClass pc = iterator.next();
			this.hbmMappings.addClass(pc);

		}
	}

	/**
	 * 返回hbmConfiguration对象
	 */
	public Configuration getHbmConfiguration() {
		Iterator<Table> iterator = this.hbmConfiguration.getTableMappings();
		while(iterator.hasNext()){
			Table t = iterator.next();
		}
		
		Iterator<PersistentClass> pcIterator = this.hbmConfiguration.getClassMappings();
		while(pcIterator.hasNext()){
			PersistentClass pc = pcIterator.next();
		}
		
		return this.hbmConfiguration;
	}


	/**
	 * 创建Property对象，用于主键和普通属性
	 * 
	 * @param table
	 * @param propertyName
	 * @param value
	 * @param insertable
	 * @param updatable
	 * @param lazy
	 * @param cascade
	 * @param propertyAccessorName
	 * @return
	 */
	private Property makeProperty(String propertyName, Value value,
			boolean insertable, boolean updatable, boolean lazy,
			String cascade, String propertyAccessorName) {

		Property prop = new Property();
		prop.setName(propertyName);
		prop.setValue(value);
		prop.setInsertable(insertable);
		prop.setUpdateable(updatable);
		prop.setLazy(true);
		prop.setCascade(cascade == null ? "none" : cascade);
		prop.setPropertyAccessorName(propertyAccessorName == null ? "property" : propertyAccessorName);
		Map<String, MetaAttribute> metaAttributes = new HashMap<String, MetaAttribute>();
		if (!(value instanceof Collection)) {

			// prop.setMetaAttributes(metaAttributes);
			// toString
			MetaAttribute toStringMa = new MetaAttribute("use-in-tostring");
			toStringMa.addValue("true");
			metaAttributes.put("use-in-tostring", toStringMa);
			// equals
			MetaAttribute equalsMa = new MetaAttribute("use-in-equals");
			equalsMa.addValue("true");
			metaAttributes.put("use-in-equals", equalsMa);

		}
		prop.setMetaAttributes(metaAttributes);

		return prop;
	}

	// 给set加泛型
	private void addPropertyTypeMeta(Property property, PersistentClass mpc) {
		Map<String, MetaAttribute> metaAttributes = new HashMap<String, MetaAttribute>();
		if (property.getMetaAttributes() != null) {
			metaAttributes = property.getMetaAttributes();
		}
		property.setMetaAttributes(metaAttributes);
		MetaAttribute setTypeMeta = new MetaAttribute("property-type");
		MetaAttribute setValueMeta = new MetaAttribute("default-value");
		//
		metaAttributes.put("property-type", setTypeMeta);
		metaAttributes.put("default-value", setValueMeta);

		String fullSetType = mpc.getClassName();
		setTypeMeta.addValue("java.util.Set<"+ StringUtil.getClassNameFromFullName(fullSetType) + ">");
		setValueMeta.addValue("new HashSet<"+ StringUtil.getClassNameFromFullName(fullSetType) + ">(0)");
		// 给所属的persistentClass中加extra-import
		PersistentClass pc = property.getPersistentClass();
		Map<String, MetaAttribute> pcMetaAttributes = pc.getMetaAttributes();
		if (pcMetaAttributes == null) {
			pcMetaAttributes = new HashMap<String, MetaAttribute>();
		}
		
		MetaAttribute importMeta = pcMetaAttributes.get("extra-import");
		if(importMeta == null){
			importMeta = new MetaAttribute("extra-import");
			pcMetaAttributes.put("extra-import", importMeta);
		}
		
		importMeta.addValue(fullSetType);
		importMeta.addValue("java.util.HashSet");
	}

	private void addSetMeta(PersistentClass pcMajor, PersistentClass ppc,
			String setName) {

		Map<String, MetaAttribute> metaAttributes = new HashMap<String, MetaAttribute>();
		if (pcMajor.getMetaAttributes() != null) {
			metaAttributes = pcMajor.getMetaAttributes();
		}
		pcMajor.setMetaAttributes(metaAttributes);

		MetaAttribute setNameMeta = new MetaAttribute("setName");
		if (metaAttributes.get("setName") != null) {
			setNameMeta = metaAttributes.get("setName");
		}
		setNameMeta.addValue(setName + ",");
		metaAttributes.put("setName", setNameMeta);
	}
	
	private void addPropMeta(PersistentClass pcMajor, PersistentClass ppc,String propName) {

		Map<String, MetaAttribute> metaAttributes = new HashMap<String, MetaAttribute>();
		if (pcMajor.getMetaAttributes() != null) {
			metaAttributes = pcMajor.getMetaAttributes();
		}
		pcMajor.setMetaAttributes(metaAttributes);

		MetaAttribute setNameMeta = new MetaAttribute("propName");
		if (metaAttributes.get("propName") != null) {
			setNameMeta = metaAttributes.get("propName");
		}
		setNameMeta.addValue(propName + ",");
		metaAttributes.put("propName", setNameMeta);
	}

	public List<PersistentClass> getReferenceSet() {
		return referenceSet;
	}
	
	public List<PersistentClass> getReverseSet() {
		return reverseSet;
	}

	public void setReverseSet(List<PersistentClass> reverseSet) {
		this.reverseSet = reverseSet;
	}
	
	/**
	 * 在业务实体中搜索与给定列名相同的属性
	 * @author rongxin.bian
	 * @param columnName 列名
	 * @param businessClass 业务实体
	 * @return 与给定列名相同的属性
	 */
	protected PersistenceProperty getEqualColumnProperty(String columnName, BusinessClass businessClass) {
		if(columnName == null || columnName.trim().length() == 0) {
			return null;
		}
		List<com.mqfdy.code.model.Property> propertieList = businessClass.getProperties();
		for(com.mqfdy.code.model.Property property: propertieList) {
			if(property instanceof PersistenceProperty) {
				PersistenceProperty persistenceProperty = (PersistenceProperty) property;
				if(columnName.equals(persistenceProperty.getdBColumnName())) {
					return persistenceProperty;
				}
			}
		}
		return null;
	}
	
}
