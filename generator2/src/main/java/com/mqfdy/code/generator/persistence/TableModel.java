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
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getPoJavaPackage()
	 * @return TableModel
	 */
	public String getPoJavaPackage() {
		return poJavaPackage;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getTable()
	 * @return TableModel
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @see com.mqfdy.code.datasource.model.AbstractModel#getElementType()
	 * @return TableModel
	 */
	@Override
	public int getElementType() {
		return IPersistenceModelType.TABLE_TYPE;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#addColumnModel(com.mqfdy.code.generator.persistence.IColumnModel)
	 * @param columnModel TableModel
	 */
	public void addColumnModel(final IColumnModel columnModel) {
		columnModelList.add(columnModel);

	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getColumnModels()
	 * @return TableModel
	 */
	public List<IColumnModel> getColumnModels() {
		return columnModelList;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getJavaPackage()
	 * @return TableModel
	 */
	public String getJavaPackage() {
		return javaPackage;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setJavaPackage(java.lang.String)
	 * @param pojoPackage TableModel
	 */
	public void setJavaPackage(String pojoPackage) {
		this.javaPackage = pojoPackage;
		this.poJavaPackage = this.getJavaPackage() + "po";
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getJavaName()
	 * @return TableModel
	 */
	public String getJavaName() {
		return getTable().getJavaName();
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setJavaName(java.lang.String)
	 * @param javaName TableModel
	 */
	public void setJavaName(String javaName) {
		getTable().setJavaName(javaName);

	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getSrcFolder()
	 * @return TableModel
	 */
	public String getSrcFolder() {
		return srcFolderPath;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setSrcFolder(java.lang.String)
	 * @param srcFolder TableModel
	 */
	public void setSrcFolder(String srcFolder) {
		this.srcFolderPath = srcFolder;

	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#addForeignKeyModel(com.mqfdy.code.generator.persistence.IForeignKeyModel)
	 * @param foreignKeyModel TableModel
	 */
	public void addForeignKeyModel(final IForeignKeyModel foreignKeyModel) {
		foreignKeyModelList.add(foreignKeyModel);
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getForeignKeyModels()
	 * @return TableModel
	 */
	public List<IForeignKeyModel> getForeignKeyModels() {
		return foreignKeyModelList;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#hasForeignTable()
	 * @return TableModel
	 */
	public boolean hasForeignTable() {
		return false;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getPrimaryKeyColumn()
	 * @return TableModel
	 */
	public IColumnModel getPrimaryKeyColumn() {
		for (IColumnModel col : getColumnModels()) {
			if (col.isPrimaryKey())
				return col;
		}
		return null;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getIdMethod()
	 * @return TableModel
	 */
	public String getIdMethod() {
		return getTable().getIdMethod();
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getDBName()
	 * @return TableModel
	 */
	public String getDBName() {
		return getTable().getName();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param object
	 * @return TableModel
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
	 * @see java.lang.Object#hashCode()
	 * @return TableModel
	 */
	public int hashCode() {
		int result = 17; // 任意素数
		result = 31 * result + table.hashCode(); // c1,c2是什么看下文解释
		return result;
	}


	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#getScenceName()
	 * @return TableModel
	 */
	public String getScenceName() {
		return this.scenceName;
	}


	/**
	 * @see com.mqfdy.code.generator.persistence.IPersistenceModel#setScenceName(java.lang.String)
	 * @param scenceName TableModel
	 */
	public void setScenceName(String scenceName) {
		this.scenceName = scenceName;
	}

}
