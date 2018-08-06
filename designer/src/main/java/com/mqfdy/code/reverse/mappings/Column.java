package com.mqfdy.code.reverse.mappings;

public class Column implements Comparable<Column>{

	private String id;
	private Integer length=null;
	private Integer precision=null;
	private Integer scale=0;
	private String name;
	private boolean nullable=true;
	private boolean unique=false;
	private String sqlType;
	private String comment;
	private String defaultValue;
	
	public Column() { };

	public Column(String columnName) {
		setName(columnName);
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
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

	public int compareTo(Column o) {
		if(this.name.compareTo(o.getName())>0){
			return -1;
		}else if(this.name.compareTo(o.getName())<0){
			return 1;
		}
		return 0;
	}
	
	
	
}
