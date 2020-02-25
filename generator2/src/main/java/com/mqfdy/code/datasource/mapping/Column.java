package com.mqfdy.code.datasource.mapping;

// TODO: Auto-generated Javadoc
/**
 * 数据库表列信息类.
 *
 * @author mqfdy
 */
public class Column implements Comparable<Column>{

	/** ID. */
	private String id;
	
	/** 字段长度. */
	private String length = null;
	
	/** 字段精度. */
	private Integer precision = null;
	
	/** 数值范围. */
	private Integer scale = 0;
	
	/** 字段名. */
	private String name;
	
	/** 是否可为空. */
	private boolean nullable = true;
	
	/** 是否只读. */
	private boolean readOnly = false;
	
	/** 是否唯一. */
	private boolean unique = false;
	
	/** The sequence name. */
	private String sequenceName;
	
	/** 数据类型（数据库中）. */
	private String sqlType;
	
	/** 注释. */
	private String comment;
	
	/** 默认值. */
	private String defaultValue;
	
	/** 属性名称(JAVA). */
	private String javaName;
	
	/** 列类型（Java）. */
	private String javaType;
	
	/** 列类型（vo）. */
	private String voType;
	
	/** 是否为主键. */
	private boolean isPrimaryKey = false;
	
	/** 是否自增. */
	private boolean isAutoIncrement;
	
	/** 是否自动生成代码. */
	private boolean isGenerate = true;
	
	/** 是否是树. */
	private boolean isParent = false;
	
	/** 字段JavaName是否合规. */
	private boolean isCompliance = true;
	
	/** 主键类型 string 默认uuid,int double float 默认 自增长. */
	private String pkType;
	
	/** The editor type. */
	private String editorType;
	
	/** The validator str. */
	private String validatorStr;
	
	/** The has validator. */
	private boolean hasValidator = false;
	
	/**
	 * Gets the validator str.
	 *
	 * @author mqfdy
	 * @return the validator str
	 * @Date 2018-09-03 09:00
	 */
	public String getValidatorStr() {
		return validatorStr;
	}

	/**
	 * Sets the validator str.
	 *
	 * @author mqfdy
	 * @param validatorStr
	 *            the new validator str
	 * @Date 2018-09-03 09:00
	 */
	public void setValidatorStr(String validatorStr) {
		this.validatorStr = validatorStr;
	}

	/**
	 * Checks if is checks for validator.
	 *
	 * @author mqfdy
	 * @return true, if is checks for validator
	 * @Date 2018-09-03 09:00
	 */
	public boolean isHasValidator() {
		return hasValidator;
	}

	/**
	 * Sets the checks for validator.
	 *
	 * @author mqfdy
	 * @param hasValidator
	 *            the new checks for validator
	 * @Date 2018-09-03 09:00
	 */
	public void setHasValidator(boolean hasValidator) {
		this.hasValidator = hasValidator;
	}

	/**
	 * Instantiates a new column.
	 */
	public Column() { };

	/**
	 * Instantiates a new column.
	 *
	 * @param columnName
	 *            the column name
	 */
	public Column(String columnName) {
		setName(columnName);
	}

	/**
	 * Gets the length.
	 *
	 * @author mqfdy
	 * @return the length
	 * @Date 2018-09-03 09:00
	 */
	public String getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @author mqfdy
	 * @param length
	 *            the new length
	 * @Date 2018-09-03 09:00
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * Gets the precision.
	 *
	 * @author mqfdy
	 * @return the precision
	 * @Date 2018-09-03 09:00
	 */
	public Integer getPrecision() {
		return precision;
	}

	/**
	 * Sets the precision.
	 *
	 * @author mqfdy
	 * @param precision
	 *            the new precision
	 * @Date 2018-09-03 09:00
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	/**
	 * Gets the scale.
	 *
	 * @author mqfdy
	 * @return the scale
	 * @Date 2018-09-03 09:00
	 */
	public Integer getScale() {
		return scale;
	}

	/**
	 * Sets the scale.
	 *
	 * @author mqfdy
	 * @param scale
	 *            the new scale
	 * @Date 2018-09-03 09:00
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
	}

	/**
	 * Gets the name.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is nullable.
	 *
	 * @author mqfdy
	 * @return true, if is nullable
	 * @Date 2018-09-03 09:00
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Sets the nullable.
	 *
	 * @author mqfdy
	 * @param nullable
	 *            the new nullable
	 * @Date 2018-09-03 09:00
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * Checks if is unique.
	 *
	 * @author mqfdy
	 * @return true, if is unique
	 * @Date 2018-09-03 09:00
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * Sets the unique.
	 *
	 * @author mqfdy
	 * @param unique
	 *            the new unique
	 * @Date 2018-09-03 09:00
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * Gets the sql type.
	 *
	 * @author mqfdy
	 * @return the sql type
	 * @Date 2018-09-03 09:00
	 */
	public String getSqlType() {
		return sqlType;
	}

	/**
	 * Sets the sql type.
	 *
	 * @author mqfdy
	 * @param sqlType
	 *            the new sql type
	 * @Date 2018-09-03 09:00
	 */
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	/**
	 * Gets the comment.
	 *
	 * @author mqfdy
	 * @return the comment
	 * @Date 2018-09-03 09:00
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @author mqfdy
	 * @param comment
	 *            the new comment
	 * @Date 2018-09-03 09:00
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the default value.
	 *
	 * @author mqfdy
	 * @return the default value
	 * @Date 2018-09-03 09:00
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 *
	 * @author mqfdy
	 * @param defaultValue
	 *            the new default value
	 * @Date 2018-09-03 09:00
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the id.
	 *
	 * @author mqfdy
	 * @return the id
	 * @Date 2018-09-03 09:00
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the new id
	 * @Date 2018-09-03 09:00
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 判断字段是否设置了默认值.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasDefualtValue(){
		if(defaultValue != null && defaultValue.length()>0 
				&& !defaultValue.equalsIgnoreCase("NULL")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Compare to.
	 *
	 * @param o
	 *            the o
	 * @return Column
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Column o) {
		if(this.name.compareTo(o.getName())>0){
			return -1;
		}else if(this.name.compareTo(o.getName())<0){
			return 1;
		}
		return 0;
	}

	/**
	 * Gets the java name.
	 *
	 * @author mqfdy
	 * @return the java name
	 * @Date 2018-09-03 09:00
	 */
	public String getJavaName() {
		return javaName;
	}

	/**
	 * Sets the java name.
	 *
	 * @author mqfdy
	 * @param javaName
	 *            the new java name
	 * @Date 2018-09-03 09:00
	 */
	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	/**
	 * Gets the java type.
	 *
	 * @author mqfdy
	 * @return the java type
	 * @Date 2018-09-03 09:00
	 */
	public String getJavaType() {
		return javaType;
	}

	/**
	 * Sets the java type.
	 *
	 * @author mqfdy
	 * @param javaType
	 *            the new java type
	 * @Date 2018-09-03 09:00
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	/**
	 * Checks if is primary key.
	 *
	 * @author mqfdy
	 * @return true, if is primary key
	 * @Date 2018-09-03 09:00
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	 * Sets the primary key.
	 *
	 * @author mqfdy
	 * @param isPrimaryKey
	 *            the new primary key
	 * @Date 2018-09-03 09:00
	 */
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	/**
	 * Checks if is auto increment.
	 *
	 * @author mqfdy
	 * @return true, if is auto increment
	 * @Date 2018-09-03 09:00
	 */
	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	/**
	 * Sets the auto increment.
	 *
	 * @author mqfdy
	 * @param isAutoIncrement
	 *            the new auto increment
	 * @Date 2018-09-03 09:00
	 */
	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	/**
	 * Checks if is generate.
	 *
	 * @author mqfdy
	 * @return true, if is generate
	 * @Date 2018-09-03 09:00
	 */
	public boolean isGenerate() {
		return isGenerate;
	}

	/**
	 * Sets the generate.
	 *
	 * @author mqfdy
	 * @param isGenerate
	 *            the new generate
	 * @Date 2018-09-03 09:00
	 */
	public void setGenerate(boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	/**
	 * Checks if is compliance.
	 *
	 * @author mqfdy
	 * @return true, if is compliance
	 * @Date 2018-09-03 09:00
	 */
	public boolean isCompliance() {
		return isCompliance;
	}

	/**
	 * Sets the compliance.
	 *
	 * @author mqfdy
	 * @param isCompliance
	 *            the new compliance
	 * @Date 2018-09-03 09:00
	 */
	public void setCompliance(boolean isCompliance) {
		this.isCompliance = isCompliance;
	}

	/**
	 * Checks if is parent.
	 *
	 * @author mqfdy
	 * @return true, if is parent
	 * @Date 2018-09-03 09:00
	 */
	public boolean isParent() {
		return isParent;
	}

	/**
	 * Sets the parent.
	 *
	 * @author mqfdy
	 * @param isParent
	 *            the new parent
	 * @Date 2018-09-03 09:00
	 */
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	/**
	 * Gets the pk type.
	 *
	 * @author mqfdy
	 * @return the pk type
	 * @Date 2018-09-03 09:00
	 */
	public String getPkType() {
		return pkType;
	}

	/**
	 * Sets the pk type.
	 *
	 * @author mqfdy
	 * @param pkType
	 *            the new pk type
	 * @Date 2018-09-03 09:00
	 */
	public void setPkType(String pkType) {
		this.pkType = pkType;
	}

	/**
	 * Gets the vo type.
	 *
	 * @author mqfdy
	 * @return the vo type
	 * @Date 2018-09-03 09:00
	 */
	public String getVoType() {
		return voType;
	}

	/**
	 * Sets the vo type.
	 *
	 * @author mqfdy
	 * @param voType
	 *            the new vo type
	 * @Date 2018-09-03 09:00
	 */
	public void setVoType(String voType) {
		this.voType = voType;
	}

	/**
	 * Gets the editor type.
	 *
	 * @author mqfdy
	 * @return the editor type
	 * @Date 2018-09-03 09:00
	 */
	public String getEditorType() {
		return editorType;
	}

	/**
	 * Sets the editor type.
	 *
	 * @author mqfdy
	 * @param editorType
	 *            the new editor type
	 * @Date 2018-09-03 09:00
	 */
	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	/**
	 * Checks if is read only.
	 *
	 * @author mqfdy
	 * @return true, if is read only
	 * @Date 2018-09-03 09:00
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * Sets the read only.
	 *
	 * @author mqfdy
	 * @param readOnly
	 *            the new read only
	 * @Date 2018-09-03 09:00
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Checks if is sequence.
	 *
	 * @author mqfdy
	 * @return true, if is sequence
	 * @Date 2018-09-03 09:00
	 */
	public boolean isSequence() {
		return "sequence".equalsIgnoreCase(pkType);
	}

	/**
	 * Gets the sequence name.
	 *
	 * @author mqfdy
	 * @return the sequence name
	 * @Date 2018-09-03 09:00
	 */
	public String getSequenceName() {
		return sequenceName;
	}

	/**
	 * Sets the sequence name.
	 *
	 * @author mqfdy
	 * @param sequenceName
	 *            the new sequence name
	 * @Date 2018-09-03 09:00
	 */
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

}
