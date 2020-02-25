package com.mqfdy.code.generator.persistence;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.datasource.mapping.Table;
import com.mqfdy.code.datasource.model.AbstractModel;

// TODO: Auto-generated Javadoc
/**
 * The Class TableModel.
 *
 * @author mqfdy
 */
public class TableModel extends AbstractModel implements IPersistenceModel {
	
	/** The table. */
	private final Table table;
	
	/** The column model list. */
	private final List<IColumnModel> columnModelList = new ArrayList<IColumnModel>();
	
	/** The java package. */
	private String javaPackage = "";

	/** The po java package. */
	private String poJavaPackage = "";
	
	/** The src folder path. */
	private String srcFolderPath = "";

	/** The scence name. */
	private String scenceName = "";
	
	/** The foreign key model list. */
	private final List<IForeignKeyModel> foreignKeyModelList = new ArrayList<IForeignKeyModel>();

	/**
	 * Instantiates a new table model.
	 *
	 * @param table
	 *            the table
	 */
	public TableModel(final Table table) {
		super(null, table.getName());
		this.table = table;
	}
	
	
	/**
	 * Gets the po java package.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getPoJavaPackage()
	 */
	public String getPoJavaPackage() {
		return poJavaPackage;
	}

	/**
	 * Gets the table.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getTable()
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Gets the element type.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.datasource.model.AbstractModel#getElementType()
	 */
	@Override
	public int getElementType() {
		return IPersistenceModelType.TABLE_TYPE;
	}

	/**
	 * Adds the column model.
	 *
	 * @param columnModel
	 *            TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#addColumnModel(com.mqfdy.code.generator.persistence.IColumnModel)
	 */
	public void addColumnModel(final IColumnModel columnModel) {
		columnModelList.add(columnModel);

	}

	/**
	 * Gets the column models.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getColumnModels()
	 */
	public List<IColumnModel> getColumnModels() {
		return columnModelList;
	}

	/**
	 * Gets the java package.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getJavaPackage()
	 */
	public String getJavaPackage() {
		return javaPackage;
	}

	/**
	 * Sets the java package.
	 *
	 * @param pojoPackage
	 *            TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setJavaPackage(java.lang.String)
	 */
	public void setJavaPackage(String pojoPackage) {
		this.javaPackage = pojoPackage;
		this.poJavaPackage = this.getJavaPackage() + "po";
	}

	/**
	 * Gets the java name.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getJavaName()
	 */
	public String getJavaName() {
		return getTable().getJavaName();
	}

	/**
	 * Sets the java name.
	 *
	 * @param javaName
	 *            TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setJavaName(java.lang.String)
	 */
	public void setJavaName(String javaName) {
		getTable().setJavaName(javaName);

	}

	/**
	 * Gets the src folder.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getSrcFolder()
	 */
	public String getSrcFolder() {
		return srcFolderPath;
	}

	/**
	 * Sets the src folder.
	 *
	 * @param srcFolder
	 *            TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setSrcFolder(java.lang.String)
	 */
	public void setSrcFolder(String srcFolder) {
		this.srcFolderPath = srcFolder;

	}

	/**
	 * Adds the foreign key model.
	 *
	 * @param foreignKeyModel
	 *            TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#addForeignKeyModel(com.mqfdy.code.generator.persistence.IForeignKeyModel)
	 */
	public void addForeignKeyModel(final IForeignKeyModel foreignKeyModel) {
		foreignKeyModelList.add(foreignKeyModel);
	}

	/**
	 * Gets the foreign key models.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getForeignKeyModels()
	 */
	public List<IForeignKeyModel> getForeignKeyModels() {
		return foreignKeyModelList;
	}

	/**
	 * Checks for foreign table.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#hasForeignTable()
	 */
	public boolean hasForeignTable() {
		return false;
	}

	/**
	 * Gets the primary key column.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getPrimaryKeyColumn()
	 */
	public IColumnModel getPrimaryKeyColumn() {
		for (IColumnModel col : getColumnModels()) {
			if (col.isPrimaryKey())
				return col;
		}
		return null;
	}

	/**
	 * Gets the id method.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getIdMethod()
	 */
	public String getIdMethod() {
		return getTable().getIdMethod();
	}

	/**
	 * Gets the DB name.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getDBName()
	 */
	public String getDBName() {
		return getTable().getName();
	}

	/**
	 * Equals.
	 *
	 * @param object
	 *            the object
	 * @return TableModel
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || !(object instanceof TableModel)) {
			return false;
		}
		TableModel table = (TableModel) object;
		if (table.getDBName().equals(this.getDBName())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Hash code.
	 *
	 * @return TableModel
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17; // 任意素数
		result = 31 * result + table.hashCode(); // c1,c2是什么看下文解释
		return result;
	}


	/**
	 * Gets the scence name.
	 *
	 * @return TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getScenceName()
	 */
	public String getScenceName() {
		return this.scenceName;
	}


	/**
	 * Sets the scence name.
	 *
	 * @param scenceName
	 *            TableModel
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setScenceName(java.lang.String)
	 */
	public void setScenceName(String scenceName) {
		this.scenceName = scenceName;
	}

}
