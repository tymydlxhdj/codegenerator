package com.mqfdy.code.generator.persistence;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.datasource.mapping.Table;
import com.mqfdy.code.datasource.model.AbstractModel;

public class TableModel extends AbstractModel implements IPersistenceModel {
	
	private final Table table;
	
	private final List<IColumnModel> columnModelList = new ArrayList<IColumnModel>();
	
	private String javaPackage = "";

	private String poJavaPackage = "";
	
	private String srcFolderPath = "";

	private String scenceName = "";
	
	private final List<IForeignKeyModel> foreignKeyModelList = new ArrayList<IForeignKeyModel>();

	public TableModel(final Table table) {
		super(null, table.getName());
		this.table = table;
	}
	
	
	public String getPoJavaPackage() {
		return poJavaPackage;
	}

	public Table getTable() {
		return table;
	}

	@Override
	public int getElementType() {
		return IPersistenceModelType.TABLE_TYPE;
	}

	public void addColumnModel(final IColumnModel columnModel) {
		columnModelList.add(columnModel);

	}

	public List<IColumnModel> getColumnModels() {
		return columnModelList;
	}

	public String getJavaPackage() {
		return javaPackage;
	}

	public void setJavaPackage(String pojoPackage) {
		this.javaPackage = pojoPackage;
		this.poJavaPackage = this.getJavaPackage() + "po";
	}

	public String getJavaName() {
		return getTable().getJavaName();
	}

	public void setJavaName(String javaName) {
		getTable().setJavaName(javaName);

	}

	public String getSrcFolder() {
		return srcFolderPath;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolderPath = srcFolder;

	}

	public void addForeignKeyModel(final IForeignKeyModel foreignKeyModel) {
		foreignKeyModelList.add(foreignKeyModel);
	}

	public List<IForeignKeyModel> getForeignKeyModels() {
		return foreignKeyModelList;
	}

	public boolean hasForeignTable() {
		return false;
	}

	public IColumnModel getPrimaryKeyColumn() {
		for (IColumnModel col : getColumnModels()) {
			if (col.isPrimaryKey())
				return col;
		}
		return null;
	}

	public String getIdMethod() {
		return getTable().getIdMethod();
	}

	public String getDBName() {
		return getTable().getName();
	}

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

	public int hashCode() {
		int result = 17; // 任意素数
		result = 31 * result + table.hashCode(); // c1,c2是什么看下文解释
		return result;
	}


	public String getScenceName() {
		return this.scenceName;
	}


	public void setScenceName(String scenceName) {
		this.scenceName = scenceName;
	}

}
