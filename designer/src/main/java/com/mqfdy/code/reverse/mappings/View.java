package com.mqfdy.code.reverse.mappings;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class View.
 *
 * @author mqfdy
 */
public class View {

	/** The name. */
	private String name;
	
	/** The columns. */
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	
	/** The comment. */
	private String comment;
	
	/**
	 * Instantiates a new view.
	 */
	public View() {
	}

	/**
	 * Instantiates a new view.
	 *
	 * @param tbName
	 *            the tb name
	 */
	public View(String tbName) {
		this();
		setName(tbName);
	}
	
	/**
	 * Adds the column.
	 *
	 * @author mqfdy
	 * @param column
	 *            the column
	 * @Date 2018-09-03 09:00
	 */
	public void addColumn(Column column){
		
		columns.put(column.getName().toUpperCase(Locale.getDefault()), column);
	}
	
	/**
	 * Gets the column.
	 *
	 * @author mqfdy
	 * @param colName
	 *            the col name
	 * @return the column
	 * @Date 2018-09-03 09:00
	 */
	public Column getColumn(String colName){
		if(StringUtil.isEmpty(colName)){
			return null;
		}
		return (Column) columns.get(colName);
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
	 * Gets the columns.
	 *
	 * @author mqfdy
	 * @return the columns
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, Column> getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @author mqfdy
	 * @param columns
	 *            the columns
	 * @Date 2018-09-03 09:00
	 */
	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}
}
