package com.mqfdy.code.reverse.mappings;

// TODO: Auto-generated Javadoc
/**
 * The Class Column.
 *
 * @author mqfdy
 */
public class Column implements Comparable<Column>{

	/** The id. */
	private String id;
	
	/** The length. */
	private Integer length=null;
	
	/** The precision. */
	private Integer precision=null;
	
	/** The scale. */
	private Integer scale=0;
	
	/** The name. */
	private String name;
	
	/** The nullable. */
	private boolean nullable=true;
	
	/** The unique. */
	private boolean unique=false;
	
	/** The sql type. */
	private String sqlType;
	
	/** The comment. */
	private String comment;
	
	/** The default value. */
	private String defaultValue;
	
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
	public Integer getLength() {
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
	public void setLength(Integer length) {
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
	 * Compare to.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public int compareTo(Column o) {
		if(this.name.compareTo(o.getName())>0){
			return -1;
		}else if(this.name.compareTo(o.getName())<0){
			return 1;
		}
		return 0;
	}
	
	
	
}
