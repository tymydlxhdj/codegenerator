package com.mqfdy.code.reverse.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.PrimaryKeyPloyType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.model.utils.ValidatorType;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.utils.ReverseUtil;

public class JDBCBinder {

	private int orderNum = 0;
	private List<String> newAssIds = new ArrayList<String>();// 保留新建关联关系id

	private BusinessObjectModel bom = null;
	private Map<String, Table> lastTables = ReverseContext.lastTables;
	private List<Table> allTableList = null;// 数据库表集合(不包含多对多的中间表)
	private List<Table> middleTables = new ArrayList<Table>();// 中间表集合
	private JDBCReader reader = new JDBCReader();

	public JDBCBinder() {
		this.bom = ReverseContext.bom;
		this.allTableList = new ArrayList<Table>(ReverseContext.tbMap.keySet());
		this.getMiddleTables();// 获取中间表信息
		// getDefaultValueAndUnique();
	}

	// 获取表的唯一约束列与字段默认值
	@SuppressWarnings("unused")
	/*private void getDefaultValueAndUnique() {
		for (Table table : allTableList) {
			try {
				Set<String> uniqueColNames = reader.getUniqueCols(table);
				for (String colName : table.getColumns().keySet()) {
					if (uniqueColNames != null && uniqueColNames.size() > 0 && uniqueColNames.contains(colName)) {
						table.getColumn(colName).setUnique(true);
					}
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}*/

	// 获取中间表信息
	private void getMiddleTables() {
		for (Map.Entry<String, Table> map : lastTables.entrySet()) {
			Table t = map.getValue();
			if (ReverseUtil.isManyToManyTable(t)) {// 中间表不转换为业务实体
				middleTables.add(t);
			}
		}
	}

	/**
	 * 将表信息转为业务实体对象
	 * 
	 * @param tableList
	 */
	public void createBusinessClasses() {
		// 生成新业务实体前，删除原有同名的业务实体。
		List<String> tihuan = new ArrayList<String>(bom.getExtendAttributies().keySet());// 需替换的业务实体
		List<BusinessClass> bcs = new ArrayList<BusinessClass>(this.bom.getBusinessClasses());
		for (BusinessClass bc : bcs) {
			if (tihuan.contains(bc.getId())) {
				this.bom.removeBusinessClass(bc);
				// ReverseContext.existBcs.remove(bc);
			}

			if (StringUtil.isEmpty(bc.getDisplayName())) {
				bc.setDisplayName(bc.getName());
			}
		}
		// 生成新关联关系前，删除原有关联关系
		if (tihuan.size() >= 2) {
			List<Association> assss = new ArrayList<Association>(this.bom.getAssociations());
			for (Association ass : assss) {
				if (ass.getClassA()!=null&&tihuan.contains(ass.getClassA().getId()) &&ass.getClassB()!=null&& tihuan.contains(ass.getClassB().getId())) {
					this.bom.removeAssociation(ass);
				}
			}
		}
		/**
		 * 在所有的表中检索需要替换的bom 
		 * 如果找到了，需要替换，则使用原有业务实体的id 
		 * 如果没找到，不需要替换，使用新生成的业务实体id
		 * */
		for (Table table : allTableList) {
			BusinessClass bc = ReverseContext.tbMap.get(table);
			// 新生成的业务实体id
			String id = bc.getId();// UUID.randomUUID().toString().replace("-",
									// "");

			// 找出被替换bc的id值, 即 ，原有bom中业务实体的id
			String bcId = null;
			for (Map.Entry<String, Object> m : this.bom.getExtendAttributies().entrySet()) {
				String tableName = (String) m.getValue();
				if (tableName.equalsIgnoreCase(table.getName())) {
					bcId = m.getKey();
					break;
				}
			}
			bc.setId(bcId == null ? id : bcId);
			bc.setStereotype(IModelElement.STEREOTYPE_REVERSE);
			Diagram dia=null; //保存当前实体所在的Diagram
			Diagram delDia=null;//待删除图形的Diagram
			DiagramElement e=null;
			List<Diagram> listDragram=this.bom.getDiagrams();
			for(int i=0;i<listDragram.size();i++){
				if(listDragram.get(i).getBelongPackage().getId().equals(bc.getBelongPackage().getId())){
					dia=listDragram.get(i);
				}
				List<DiagramElement> eles=listDragram.get(i).getElements();
				for(DiagramElement ele:eles){
					if(bc.getId().equals(ele.getObjectId())){
						delDia=listDragram.get(i);
						e=ele;
					}
				}
				if(delDia!=null&&dia!=null){
					break;
				}
			}
			
			if(e!=null){
				if(dia !=null){
					dia.addElement(e);
				}
				if(delDia !=null && delDia.getElements()!=null){
					delDia.getElements().remove(e);
				}
			}
			
			orderNum = 0;
			createProperties(bc, table);
			createOperation(bc);
		}
	}

	// 根据中间表创建多对多关联关系对象
	private void createManyToMany() {
		for (Table table : middleTables) {// 遍历中间表
			// 根据中间表的两个外键找出多对多两边的表
			Map<String, ForeignKey>  foreignKeys = table.getForeignKeys();
			Set<String> keyNames = foreignKeys.keySet();
			Table[] mnTable = new Table[2];
			ForeignKey[] fks = new ForeignKey[2];
			int i = 0;
			for (Iterator<String> iterator = keyNames.iterator(); iterator.hasNext();) {
				String keyName = iterator.next();
				ForeignKey fk = (ForeignKey) foreignKeys.get(keyName);
				fks[i] = fk;
				mnTable[i++] = fk.getReferencedTable();
			}

			// 找出对应的业务实体对象
			BusinessClass bcA = ReverseContext.tbMap.get(mnTable[0]);
			BusinessClass bcB = ReverseContext.tbMap.get(mnTable[1]);

			if (bcA == null || bcB == null) {
				continue;
			}
			// 创建关联关系对象
			Association mn = new Association();
			mn.setAssociationType(AssociationType.mult2mult.getValue());// 关联关系类型
			mn.setCascadeDeleteClassA(false);// 多对多默认不做级联删除
			mn.setCascadeDeleteClassB(false);
			mn.setClassA(bcA);
			mn.setClassB(bcB);
			mn.setName(ReverseUtil.getAssName(bcA.getName() + "_" + bcB.getName()));
			mn.setDisplayName(ReverseUtil.getAssName(bcA.getName() + "_" + bcB.getName()));// ????
			mn.setId(UUID.randomUUID().toString().replace("-", ""));
			mn.setMajorClassId(bcA.getId());// 任意定一个主控端
			mn.setNavigateToClassA(true);// 默认为双向
			mn.setNavigateToClassB(true);
			mn.setNavigateToClassARoleName(bcA.getName().toLowerCase(Locale.getDefault()) + "s");
			mn.setNavigateToClassBRoleName(bcB.getName().toLowerCase(Locale.getDefault()) + "s");
			mn.setStereotype("3");
			mn.getPersistencePloyParams().put(Association.RELATIONTABLENAME, table.getName());// 设置中间表名
			mn.getPersistencePloyParams().put(Association.DESTRELATIONCOLUMN, mnTable[0].getPrimaryKey().getColumn(0).getName());
			mn.getPersistencePloyParams().put(Association.SOURCERELATIONCOLUMN, mnTable[1].getPrimaryKey().getColumn(0).getName());
			mn.getPersistencePloyParams().put(Association.FOREIGNKEYCOLUMNINB, fks[0].getColumn(0).getName());
			mn.getPersistencePloyParams().put(Association.FOREIGNKEYCOLUMNINA, fks[1].getColumn(0).getName());
			mn.getPersistencePloyParams().put(Association.FOREIGNKEYINA, Association.FALSE);
			mn.getPersistencePloyParams().put(Association.FOREIGNKEYINB, Association.FALSE);

			mn.setBelongPackage(bcA.getBelongPackage());
			bom.addAssociation(mn);
			this.newAssIds.add(mn.getId());
		}
	}

	

	// 删除bc时同时删除相关的业务实体
	@SuppressWarnings("unused")
	private void deleteAss(BusinessClass bc) {
		List<Association> asss = new ArrayList<Association>(bom.getAssociations());
		for (Association ass : asss) {
			// 根据bc删除关联关系
			if (!ass.getAssociationType().equals(AssociationType.mult2mult.getValue())) {// 是不是多对多
				if (ass.getClassA().equals(bc) || ass.getClassB().equals(bc)) {
					bom.getAssociations().remove(ass);
				}
			} else {
				// 根据中间表删除关联关系
				for (Table table : middleTables) {
					if (table.getName().equals(ass.getPersistencePloyParams().get(Association.RELATIONTABLENAME))) {
						bom.getAssociations().remove(ass);
					}
				}
			}
		}
	}

	// 创建5个基本方法
	private void createOperation(BusinessClass bc) {
		// 新增
		BusinessOperation add = new BusinessOperation(BusinessOperation.SYS_OPERATION_ADD, BusinessOperation.SYS_OPERATION_ADD_DISPLAYNAME, BusinessOperation.OPERATION_TYPE_STANDARD);
		add.setBelongBusinessClass(bc);
		add.setStereotype("3");
		bc.addOperation(add);
		// 编辑
		BusinessOperation edit = new BusinessOperation(BusinessOperation.SYS_OPERATION_EDIT, BusinessOperation.SYS_OPERATION_EDIT_DISPLAYNAME, BusinessOperation.OPERATION_TYPE_STANDARD);
		edit.setBelongBusinessClass(bc);
		edit.setStereotype("3");
		bc.addOperation(edit);
		// 删除
		BusinessOperation delete = new BusinessOperation(BusinessOperation.SYS_OPERATION_DELETE, BusinessOperation.SYS_OPERATION_DELETE_DISPLAYNAME, BusinessOperation.OPERATION_TYPE_STANDARD);
		delete.setBelongBusinessClass(bc);
		delete.setStereotype("3");
		bc.addOperation(delete);
		// 获取详情
		BusinessOperation get = new BusinessOperation(BusinessOperation.SYS_OPERATION_GET, BusinessOperation.SYS_OPERATION_GET_DISPLAYNAME, BusinessOperation.OPERATION_TYPE_STANDARD);
		get.setBelongBusinessClass(bc);
		get.setStereotype("3");
		bc.addOperation(get);
		// 查询列表
		BusinessOperation query = new BusinessOperation(BusinessOperation.SYS_OPERATION_QUERY, BusinessOperation.SYS_OPERATION_QUERY_DISPLAYNAME, BusinessOperation.OPERATION_TYPE_STANDARD);
		query.setBelongBusinessClass(bc);
		query.setStereotype("3");
		bc.addOperation(query);
	}

	// 创建关联关系对象
	public void createAssociation() {
		createManyToMany();// 创建多对多关系
		createOneToMany();
		// 将重名的关联关系重命名
		renameRepeatAssName();
	}

	private void renameRepeatAssName() {
		Set<String> assNames = new HashSet<String>();
		for (Association ass : this.bom.getAssociations()) {
			assNames.add(ass.getName());
		}

		List<String> assNameList = new ArrayList<String>(assNames);

		for (int i = 0; i < assNameList.size(); i++) {
			int count = 0;
			for (Association ass : bom.getAssociations()) {
				if (ass.getName().equals(assNameList.get(i))) {
					if (count > 0) {
						ass.setName(ass.getName() + "_" + (count));
						ass.setDisplayName(ass.getDisplayName() + "_" + (count));
						ass.setNavigateToClassARoleName(ass.getNavigateToClassARoleName() + "_" + (count));
						ass.setNavigateToClassBRoleName(ass.getNavigateToClassBRoleName() + "_" + (count));
					}
					count++;
				}
			}
		}

	}

	// 创建一对多关联关系
	private void createOneToMany() {
		DatabaseMetaData dbmd = null;
		try {
			dbmd = ReverseContext.wizard.getConnection().getMetaData();
			for (Table table : allTableList) {
				if (middleTables.contains(table)) {
					continue;
				}
				/*if (!ReverseContext.existBcs.isEmpty()) {
					createOne2manyAssFromPaTable(table, dbmd);
				}*/
				createOne2manyAssFromChildTable(table, dbmd);

			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void createOne2manyAssFromChildTable(Table table, DatabaseMetaData metaData) {
		Map<String, ForeignKey> foreignKeys = table.getForeignKeys();
		for (Iterator<String> iterator = foreignKeys.keySet().iterator(); iterator.hasNext();) {
			String keyName = iterator.next();
			ForeignKey foreignKey = foreignKeys.get(keyName);

			Table oTable = foreignKey.getReferencedTable();// 得到主表
			
			if(oTable == null) {
				return ;
			}
			
			try {
				Column column = foreignKey.getColumns().get(0);
				Set<String> ukColumnNames = reader.getUniqueCols(table,metaData);
				
				if(ukColumnNames.contains(column.getName())) {
					column.setUnique(true);
				}
			} catch (SQLException e) {
				throw new RuntimeException("读取父表column信息出错->"+table.getName(), e);
			}

			BusinessClass mBc = ReverseContext.tbMap.get(table);// 多方
			BusinessClass oBc = ReverseContext.tbMap.get(oTable);// 一方
			if (oBc == null && !ReverseContext.existBcs.isEmpty()) {// 在bom中寻找已经存在业务实体，并创建之间的关联关系
				for (BusinessClass ebc : ReverseContext.existBcs) {
					if (oTable.getName().equalsIgnoreCase(ebc.getTableName())) {
						oBc = ebc;
						break;
					}
				}
			}
			
			if (mBc == null || oBc == null) {
				return;
			}
			
			// 如果覆盖的业务实体与已存在的业务实体之间已经存在一个关联关系，那么删除该关联关系，以保证与数据库的关系一致
			List<Association> associations = new ArrayList<Association>(bom.getAssociations());
			for (Association association : associations) {
				if(association.getClassA()==null||association.getClassB()==null){
					continue;
				}
				
				String businessClassAId = association.getClassA().getId();
				String businessClassBId = association.getClassB().getId();
				if ((businessClassAId.equals(mBc.getId()) || businessClassBId.equals(mBc.getId())) && (businessClassAId.equals(oBc.getId()) || businessClassBId.equals(oBc.getId()))) {
					if (ReverseContext.bcIds.contains(mBc.getId()) && !this.newAssIds.contains(association.getId())) {
						bom.removeAssociation(association);
					}
				}
			}

			if (foreignKey.getColumns().get(0).isUnique() || table.getPrimaryKey().getColumns().contains(foreignKey.getColumns().get(0))) {// 如果该外键列存在唯一约束，可确定为一对一
				createOneToOne(oBc, mBc, oTable, table, foreignKey);
			} else {
				Association ass = createOne2ManyAss(oBc, mBc, oTable, table, foreignKey);
				this.newAssIds.add(ass.getId());
			}
		}
	}

	@SuppressWarnings("unused")
	private void createOne2manyAssFromPaTable(Table table, DatabaseMetaData metaData) {
		Map<String, ForeignKey> foreignKeys = table.getForeignKeys();
		for (Iterator<String> iterator = foreignKeys.keySet().iterator(); iterator.hasNext();) {
			String keyName = iterator.next();
			ForeignKey foreignKey = foreignKeys.get(keyName);
			if(foreignKey!=null){
				Table mTable = foreignKey.getTable();// 得到从表
				Table oTable = foreignKey.getReferencedTable();// 得到主表
				if (oTable != null) {
					// TODO 此处可忽略
					try {
						reader.readColumn(oTable, reader.getUniqueCols(oTable, metaData));
					} catch (SQLException e) {
						throw new RuntimeException("读取父表column信息出错", e);
					}
//					reader.readConstraints(oTable);
					// TODO 此处可忽略
				}

				BusinessClass mBc = ReverseContext.tbMap.get(mTable);// 多方
				BusinessClass oBc = ReverseContext.tbMap.get(oTable);// 一方
				if (oBc == null && !ReverseContext.existBcs.isEmpty()) {// 在bom中寻找已经存在业务实体，并创建之间的关联关系
					for (BusinessClass ebc : ReverseContext.existBcs) {
						if (oTable.getName().equalsIgnoreCase(ebc.getTableName())) {
							oBc = ebc;
							break;
						}
					}
				}
				if (mBc == null || oBc == null) {
					return;
				}
				// 如果覆盖的业务实体与已存在的业务实体之间已经存在一个关联关系，那么删除该关联关系，以保证与数据库的关系一致
				List<Association> assss = new ArrayList<Association>(bom.getAssociations());// TODO
																							// 已经p
				for (Association ass : assss) {
					if ((ass.getClassA().getId().equals(mBc.getId()) || ass.getClassB().getId().equals(mBc.getId())) && (ass.getClassA().getId().equals(oBc.getId()) || ass.getClassB().getId().equals(oBc.getId()))) {
						if (ReverseContext.bcIds.contains(mBc.getId()) && !this.newAssIds.contains(ass.getId())) {
							bom.removeAssociation(ass);
						}
					}
				}

				if (mTable != null && oTable != null && foreignKey != null && (foreignKey.getColumns().get(0).isUnique() || mTable.getPrimaryKey().getColumns().contains(foreignKey.getColumns().get(0)))) {// 如果该外键列存在唯一约束，可确定为一对一
					createOneToOne(oBc, mBc, oTable, mTable, foreignKey);
					continue;
				}

				if (mBc != null && oBc != null && mTable != null && oTable != null && foreignKey != null) {
					Association ass = createOne2ManyAss(oBc, mBc, oTable, mTable, foreignKey);
					this.newAssIds.add(ass.getId());
				}
			}

		}
	}

	// 创建一对一关联关系
	private void createOneToOne(BusinessClass oBc, BusinessClass mBc, Table oTable, Table mTable, ForeignKey foreignKey) {

		Association ass = new Association();
		ass.setAssociationType(AssociationType.one2one.getValue());// 关联关系类型
		ass.setCascadeDeleteClassA(false);// 多对多默认不做级联删除
		ass.setCascadeDeleteClassB(false);
		ass.setClassA(oBc);
		ass.setClassB(mBc);
		ass.setDisplayName(ReverseUtil.getAssName(oBc.getName() + "_" + mBc.getName()));
		ass.setId(UUID.randomUUID().toString().replace("-", ""));
		ass.setMajorClassId(oBc.getId());// 设定一方为主控端
		ass.setName(ReverseUtil.getAssName(oBc.getName() + "_" + mBc.getName()));
		ass.setNavigateToClassA(true);// 默认为双向
		ass.setNavigateToClassB(true);
		ass.setNavigateToClassARoleName(oBc.getName().toLowerCase(Locale.getDefault()));
		ass.setNavigateToClassBRoleName(mBc.getName().toLowerCase(Locale.getDefault()));
		ass.setStereotype("3");
		ass.getPersistencePloyParams().put(Association.SOURCERELATIONCOLUMN, oTable.getPrimaryKey().getColumn(0).getName());
		ass.getPersistencePloyParams().put(Association.DESTRELATIONCOLUMN, mTable.getPrimaryKey().getColumn(0).getName());
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYCOLUMNINA, "");
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYCOLUMNINB, foreignKey.getColumn(0).getName());
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYINA, Association.FALSE);
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYINB, Association.TRUE);

		ass.setBelongPackage(oBc.getBelongPackage());
		bom.addAssociation(ass);
	}

	// 创建一对多关联关系
	private Association createOne2ManyAss(BusinessClass oBc, BusinessClass mBc, Table oTable, Table mTable, ForeignKey foreignKey) {
		Association ass = new Association();
		ass.setAssociationType(AssociationType.one2mult.getValue());// 关联关系类型
		ass.setCascadeDeleteClassA(false);// 多对多默认不做级联删除
		ass.setCascadeDeleteClassB(false);
		ass.setClassA(oBc);
		ass.setClassB(mBc);
		ass.setDisplayName(ReverseUtil.getAssName(oBc.getName() + "_" + mBc.getName()));
		ass.setId(UUID.randomUUID().toString().replace("-", ""));
		ass.setMajorClassId(oBc.getId());// 设定一方为主控端
		ass.setName(ReverseUtil.getAssName(oBc.getName() + "_" + mBc.getName()));
		ass.setNavigateToClassA(true);// 默认为双向
		ass.setNavigateToClassB(true);
		if(oTable.getName().equals(mTable.getName())){
			ass.setNavigateToClassARoleName("parent");
			ass.setNavigateToClassBRoleName("children");
		}else{
			ass.setNavigateToClassARoleName(oBc.getName().toLowerCase(Locale.getDefault()));
			ass.setNavigateToClassBRoleName(mBc.getName().toLowerCase(Locale.getDefault()) + "s");
		}
		
		ass.setStereotype("3");
		ass.getPersistencePloyParams().put(Association.SOURCERELATIONCOLUMN, oTable.getPrimaryKey().getColumn(0).getName());
		ass.getPersistencePloyParams().put(Association.DESTRELATIONCOLUMN, mTable.getPrimaryKey().getColumn(0).getName());
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYCOLUMNINA, "");
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYCOLUMNINB, foreignKey.getColumn(0).getName());
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYINA, Association.FALSE);
		ass.getPersistencePloyParams().put(Association.FOREIGNKEYINB, Association.TRUE);

		ass.setBelongPackage(oBc.getBelongPackage());
		bom.addAssociation(ass);

		return ass;
	}

	/**
	 * 创建业务实体属性
	 * 
	 * @param bc
	 */
	private void createProperties(BusinessClass bc, Table table) {

		this.createPkProperty(bc, table);// 绑定主键属性

		for (Property pp : bc.getProperties()) {
			Column column = table.getColumn(((PersistenceProperty) pp).getdBColumnName());
			if (!table.getPrimaryKey().getColumns().contains(column)) {
				this.createProperty((PersistenceProperty) pp, column);
			}
		}
	}

	/**
	 * 绑定主键列
	 */
	private void createPkProperty(BusinessClass bc, Table table) {
		// 创建主键属性
		PrimaryKey pk = table.getPrimaryKey();
		if (pk == null) {
			return;
		}
		List<Column> columns = pk.getColumns();
		Column pkColumn = (Column) columns.get(0);

		PKProperty pkProperty = (PKProperty) bc.getPkProperty();
		pkProperty.setStereotype(IModelElement.STEREOTYPE_REVERSE);
		pkProperty.setOrderNum(orderNum++);
		pkProperty.setPrimaryKey(true);
		pkProperty.setUnique(true);
		pkProperty.setNullable(false);
		pkProperty.setReadOnly(false);
		pkProperty.setIndexedColumn(false);

		if (pkColumn.getLength() == null) {
			if (pkColumn.getPrecision() == null) {
				pkProperty.setdBDataLength("");
				pkProperty.setdBDataPrecision("");
			} else {
				pkProperty.setdBDataLength(String.valueOf(pkColumn.getPrecision()));
				pkProperty.setdBDataPrecision(String.valueOf(pkColumn.getScale()));
			}
		} else {
			pkProperty.setdBDataLength(String.valueOf(pkColumn.getLength()));
			pkProperty.setdBDataPrecision("");
		}
		if (MiddleDataType.stringSet.contains(pkColumn.getSqlType())) {
			pkProperty.setdBDataLength(String.valueOf(pkColumn.getLength()));
		}
		String propDataType = MiddleDataType.getMiddleType(pkColumn.getSqlType(), pkColumn.getLength(), pkColumn.getScale());
		if ("string".equals(propDataType)) {
			if (Integer.valueOf(pkProperty.getdBDataLength()) >= 32) {
				pkProperty.setPrimaryKeyPloy(PrimaryKeyPloyType.UUID.getValue());
			} else if (Integer.valueOf(pkProperty.getdBDataLength()) < 32) {
				pkProperty.setPrimaryKeyPloy(PrimaryKeyPloyType.MANUAL.getValue());
			}

		} else {
			if ("long".equals(propDataType) || "integer".equals(propDataType) || "short".equals(propDataType) || "big_decimal".equals(propDataType) || "byte".equals(propDataType)) {
				propDataType = "long";
			}
			pkProperty.setPrimaryKeyPloy(PrimaryKeyPloyType.IDENTITY.getValue());
		}

		pkProperty.setDataType(propDataType);
		pkProperty.setdBColumnName(pkColumn.getName());
		pkProperty.setdBDataType("");
		pkProperty.setDefaultValue(pkColumn.getDefaultValue());
		pkProperty.setQueryProterty(false);
		if (StringUtil.isEmpty(pkProperty.getDisplayName())) {
			pkProperty.setDisplayName(StringUtil.isEmpty(pkColumn.getComment()) ? pkColumn.getName() : pkColumn.getComment());
		}
		pkProperty.setGroup(null);
		pkProperty.setId(UUID.randomUUID().toString().replace("-", ""));

		// 设置默认编辑器
		EditorType editorType = getEditorType(DataType.getDataType(propDataType));
		PropertyEditor editor = new PropertyEditor();
		editor.setBelongProperty(pkProperty);
		editor.setDataSourceType(0);
		editor.setEditorType(editorType.getValue());
		pkProperty.setEditor(editor);
	}

	private void createProperty(PersistenceProperty property, Column column) {

		property.setStereotype("3");
		property.setOrderNum(orderNum++);
		property.setPrimaryKey(false);
		property.setUnique(column.isUnique());
		property.setNullable(column.isNullable());
		property.setReadOnly(false);
		property.setIndexedColumn(false);

		property.setdBColumnName(column.getName());

		if (column.getLength() == null) {
			if (column.getPrecision() == null) {
				property.setdBDataLength("");
				property.setdBDataPrecision("");
			} else {
				property.setdBDataLength(String.valueOf(column.getPrecision()));
				property.setdBDataPrecision(String.valueOf(column.getScale()));
			}
		} else {
			property.setdBDataLength(String.valueOf(column.getLength()));
			property.setdBDataPrecision(String.valueOf(column.getScale()));
		}
		if (MiddleDataType.stringSet.contains(column.getSqlType())) {
			property.setdBDataLength(String.valueOf(column.getLength()));
		}
		String dataType = MiddleDataType.getMiddleType(column.getSqlType(), column.getLength(), column.getScale());
		property.setDataType(dataType);

		property.setdBDataType("");
		property.setDefaultValue(column.getDefaultValue());
		property.setQueryProterty(false);
		if (StringUtil.isEmpty(property.getDisplayName())) {
			property.setDisplayName(StringUtil.isEmpty(column.getComment()) ? column.getName() : column.getComment());
		}
		property.setGroup(null);
		property.setId(UUID.randomUUID().toString().replace("-", ""));

		// 设置默认编辑器
		EditorType editorType = getEditorType(DataType.getDataType(dataType));
		PropertyEditor editor = new PropertyEditor();
		editor.setStereotype("3");
		editor.setBelongProperty(property);
		editor.setDataSourceType(0);
		editor.setEditorType(editorType.getValue());
		property.setEditor(editor);

		/** 设置校验器 */
		// 非空校验器
		if (!property.isNullable()) {
			Validator nullable_v = new Validator();
			nullable_v.setBelongProperty(property);
			property.addValidator(nullable_v);
			nullable_v.setId(UUID.randomUUID().toString().replace("-", ""));
			nullable_v.setName(ValidatorType.Nullable.getValue());
			nullable_v.setDisplayName(ValidatorType.Nullable.getDisplayValue());
			nullable_v.setStereotype("3");
			nullable_v.setValidatorType(ValidatorType.Nullable.getValue());
			nullable_v.setValidatorMessage(ValidatorType.Nullable.getErrorMessage());
		}

		if (property.isUnique()) {
			/*// 唯一校验器
			Validator unique_v = new Validator();
			unique_v.setBelongProperty(property);
			property.addValidator(unique_v);
			unique_v.setId(UUID.randomUUID().toString().replace("-", ""));
			unique_v.setName(ValidatorType.Unique.getValue());
			unique_v.setDisplayName(ValidatorType.Unique.getDisplayValue());
			unique_v.setStereotype("3");
			unique_v.setValidatorType(ValidatorType.Unique.getValue());
			unique_v.setValidatorMessage(ValidatorType.Unique.getErrorMessage());*/
		}
	}

	/**
	 * 根据数据类型返回默认的编辑器类型
	 * 
	 * @param selectType
	 * @return
	 */
	static EditorType getEditorType(DataType selectType) {
		EditorType type = EditorType.TextEditor;
		switch (selectType) {
		case Long:
			type = EditorType.NumberEditor;
			break;
		case Short:
			type = EditorType.NumberEditor;
			break;
		case Byte:
			type = EditorType.NumberEditor;
			break;
		case Float:
			type = EditorType.NumberEditor;
			break;
		case Double:
			type = EditorType.NumberEditor;
			break;
		case Integer:
			type = EditorType.NumberEditor;
			break;
		case Big_decimal:
			type = EditorType.NumberEditor;
			break;
		case String:
			type = EditorType.TextEditor;
			break;
		case Character:
			type = EditorType.TextEditor;
			break;
		case Boolean:
			type = EditorType.ComboBox;
			break;
		case Date:
			type = EditorType.DateEditor;
			break;
		case Time:
			type = EditorType.TimeEditor;
			break;
		case Timestamp:
			type = EditorType.DateTimeEditor;
			break;
		case Clob:
			type = EditorType.RichTextEditor;
			break;
		/*
		 * case Blob: type = EditorType.FileEditor; break;
		 */
		}
		return type;
	}
}
