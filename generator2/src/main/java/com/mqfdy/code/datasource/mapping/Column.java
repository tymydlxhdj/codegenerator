package com.mqfdy.code.datasource.mapping;

/**
 * 数据库表列信息类
 * @author mqfdy
 *
 */
public class Column implements Comparable<Column>{

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 字段长度
	 */
	private String length = null;
	
	/**
	 * 字段精度
	 */
	private Integer precision = null;
	
	/**
	 * 数值范围
	 */
	private Integer scale = 0;
	
	/**
	 * 字段名
	 */
	private String name;
	
	/**
	 * 是否可为空
	 */
	private boolean nullable = true;
	
	/**
	 * 是否只读
	 */
	private boolean readOnly = false;
	
	/**
	 * 是否唯一
	 */
	private boolean unique = false;
	
	private String sequenceName;
	
	/**
	 * 数据类型（数据库中）
	 */
	private String sqlType;
	
	/**
	 * 注释
	 */
	private String comment;
	
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	/**
	 * 属性名称(JAVA)
	 */
	private String javaName;
	
	/**
	 * 列类型（Java）
	 */
	private String javaType;
	
	/**
	 * 列类型（vo）
	 */
	private String voType;
	
	/**
	 * 是否为主键
	 */
	private boolean isPrimaryKey = false;
	
	/**
	 * 是否自增
	 */
	private boolean isAutoIncrement;
	
	/**
	 * 是否自动生成代码
	 */
	private boolean isGenerate = true;
	
	/**
	 * 是否是树
	 */
	private boolean isParent = false;
	
	/**
	 * 字段JavaName是否合规
	 */
	private boolean isCompliance = true;
	/**
	 * 主键类型 string 默认uuid,int double float 默认 自增长
	 */
	private String pkType;
	
	private String editorType;
	
	private String validatorStr;
	
	private boolean hasValidator = false;
	
	public String getValidatorStr() {
		return validatorStr;
	}

	public void setValidatorStr(String validatorStr) {
		this.validatorStr = validatorStr;
	}

	public boolean isHasValidator() {
		return hasValidator;
	}

	public void setHasValidator(boolean hasValidator) {
		this.hasValidator = hasValidator;
	}

	public Column() { };

	public Column(String columnName) {
		setName(columnName);
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 判断字段是否设置了默认值
	 * @return
	 */
	public boolean hasDefualtValue(){
		if(defaultValue != null && defaultValue.length()>0 
				&& !defaultValue.equalsIgnoreCase("NULL")){
			return true;
		}else{
			return false;
		}
	}
	
	public int compareTo(Column o) {
		if(this.name.compareTo(o.getName())>0){
			return -1;
		}else if(this.name.compareTo(o.getName())<0){
			return 1;
		}
		return 0;
	}

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public boolean isGenerate() {
		return isGenerate;
	}

	public void setGenerate(boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	public boolean isCompliance() {
		return isCompliance;
	}

	public void setCompliance(boolean isCompliance) {
		this.isCompliance = isCompliance;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getPkType() {
		return pkType;
	}

	public void setPkType(String pkType) {
		this.pkType = pkType;
	}

	public String getVoType() {
		return voType;
	}

	public void setVoType(String voType) {
		this.voType = voType;
	}

	public String getEditorType() {
		return editorType;
	}

	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isSequence() {
		return "sequence".equalsIgnoreCase(pkType);
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

}
