package com.mqfdy.code.generator.auxiliary.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.mapping.ForeignKey;
import com.mqfdy.code.datasource.mapping.PrimaryKey;
import com.mqfdy.code.datasource.mapping.Table;
import com.mqfdy.code.datasource.mapping.TypeMap;
import com.mqfdy.code.datasource.utils.JavaNameUtil;
import com.mqfdy.code.datasource.utils.ValidatorAnotationUtil;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.persistence.PersistenceModelFactory;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.Validator;

public class ConvertUtil {
	/**
	 * oracle 序列
	 */
	private final static String SEQUENCE = "SEQUENCE";
	/**
	 * oracle 序列 名称
	 */
	private final static String SEQUENCE_NAME = "suquenceName";
	/**
	 * mx 编辑器：文本
	 */
	private final static String TEXT_EDITOR = "TextEditor";
	/**
	 * mx 编辑器：下拉框
	 */
	private final static String COMBO_EDITOR = "ComboEditor";
	/**
	 * mx 编辑器：主键策略类型map
	 */
	private static Map<String,String> pkTypeMap;
	
	static{
		pkTypeMap = new HashMap<String, String>();
		pkTypeMap.put("ASSIGNED", "identity");
		pkTypeMap.put("IDENTITY", "identity");
		pkTypeMap.put("UUID", "uuid");  
		pkTypeMap.put("SEQUENCE", "sequence");
	}
	
	/**
	 * 获取业务对象对应的表对象
	 * @param bussinessClass
	 * @param bom
	 * @return
	 */
	public static Table convertToTable(BusinessClass bussinessClass, BusinessObjectModel bom) {
		Table table = convertToTable(bussinessClass);
		addPkColumnToTable(table,bussinessClass,bom);
		return table;
	}
	/**
	 * 外键字段
	 * @param table
	 * @param bc
	 * @param bom
	 */
	private static void addPkColumnToTable(Table table, BusinessClass bc, BusinessObjectModel bom) {
		for(Association association: bom.getAssociations()){
			BusinessClass classA = association.getClassA();
			BusinessClass classB = association.getClassB();
			String fkColName = null;
			boolean isA = true;
			if(association.getClassAid().equals(association.getClassBid())){
				if(bc.getId().equals(classA.getId())){
					fkColName = association.getPersistencePloyParams().get("ForeignKeyColumnInA");
					if(StringUtils.isEmpty(fkColName)){
						fkColName = association.getPersistencePloyParams().get("ForeignKeyColumnInB");
					}
				}
			}else if(bc.getId().equals(classA.getId())){
				fkColName = association.getPersistencePloyParams().get("ForeignKeyColumnInA");
				isA = true;
			}else if(bc.getId().equals(classB.getId())){
				fkColName = association.getPersistencePloyParams().get("ForeignKeyColumnInB");
				isA = false;
			}
			
			if(!StringUtils.isEmpty(fkColName)){
				BusinessClass busClass = classA;
				if(isA){
					busClass = classB;
				}
				if (fkColName != null) {
					
				List<Property> proList = busClass.getProperties();
				for(Property p : proList){
					if (p != null && p instanceof PKProperty){
						Column col = new Column();
						col.setGenerate(true);
						col.setJavaType(TypeMap.getPropertyJava(p.getDataType()));
						col.setVoType(TypeMap.getOmVO(p.getDataType()));
						col.setId(p.getId()+"fk");
						col.setEditorType(COMBO_EDITOR);
						col.setNullable(p.isNullable());
						col.setReadOnly(p.isReadOnly());
						col.setPrimaryKey(false);
						col.setName(fkColName.toUpperCase(Locale.getDefault()));
						col.setJavaName(JavaNameUtil.getColJavaName(fkColName));
						col.setLength(((PersistenceProperty)p).getdBDataLength());
						col.setComment(fkColName);
						table.addColumn(col);
						
						ForeignKey fk = new ForeignKey();
						fk.setFkColumnJavaName(col.getJavaName());
						fk.addColumn(col);
						fk.setFkColumnName(col.getName().toUpperCase(Locale.getDefault()));
						fk.setName(association.getName());
						fk.setTable(table);
						fk.setForeignTableName(bc.getTableName());
						fk.setReferencedTable(table);
						table.addForeignKey(fk);
						break;
					}
				}
				}
			}
		}
		
	}

	/**
	 * 获取业务对象对应的表对象
	 * @param bussinessClass
	 * @return
	 */
	private static Table convertToTable(BusinessClass bussinessClass) {
		Table table = new Table();
		table.setName(bussinessClass.getTableName());
		table.setJavaName(bussinessClass.getName());
		List<Property> proList = bussinessClass.getProperties();
		for(Property p : proList){
			Column col = new Column();
			col.setGenerate(true);
			col.setJavaType(TypeMap.getPropertyJava(p.getDataType()));
			col.setVoType(TypeMap.getOmVO(p.getDataType()));
			col.setId(p.getId());
			PropertyEditor pe =p.getEditor();
			String typek = pe.getEditorType();
			String editorType = TypeMap.getEditorTypeVO(typek);
			if(StringUtils.isEmpty(editorType)){
				editorType = TEXT_EDITOR;
			}
			col.setEditorType(editorType);
			col.setNullable(p.isNullable());
			col.setReadOnly(p.isReadOnly());
			if (p instanceof PKProperty) {
				col.setPkType(pkTypeMap.get(((PKProperty) p).getPrimaryKeyPloy()));
				PrimaryKey pk = new PrimaryKey(col.getJavaName());
				pk.addColumn(col);
				table.setPrimaryKey(pk);
				col.setPrimaryKey(true);
				col.setName(((PKProperty)p).getdBColumnName().toUpperCase(Locale.getDefault()));
				col.setJavaName(((PKProperty)p).getName());
				col.setLength(((PKProperty)p).getdBDataLength());
				if(SEQUENCE.equalsIgnoreCase(((PKProperty)p).getPrimaryKeyPloy())){
					String sequenceName = ((PKProperty)p).getPrimaryKeyParams().get(SEQUENCE_NAME);
					col.setSequenceName(sequenceName);
				}
			}else{
				col.setPrimaryKey(false);
			}
			if (p instanceof PersistenceProperty) {
				col.setName(((PersistenceProperty)p).getdBColumnName().toUpperCase(Locale.getDefault()));
				col.setJavaName(((PersistenceProperty)p).getName());
				col.setLength(((PersistenceProperty)p).getdBDataLength());
				
			}
			col.setComment(p.getDisplayName());
			String validatorStr = null;
			List<Validator> validators = p.getValidators();
			StringBuilder sb = new StringBuilder();
			if(validators != null && !validators.isEmpty()){
				 for(int i = 0; i < validators.size(); i++){
					 String valiStr = ValidatorAnotationUtil.getValidatorAnotationStr(validators.get(i));
					 sb.append(valiStr);
					 if(i != validators.size() - 1){
						 sb.append("\n\t");
					 }
				 }
				 validatorStr = sb.toString();
			}
			if(validatorStr != null){
				col.setHasValidator(true);
				col.setValidatorStr(validatorStr);
			}
			table.addColumn(col);
		}
		return table;
	}
	
	/**
	 * 创建持久化实体模型
	 * @param table 持久化实体对应数据库表
	 * @return
	 */
	public static IPersistenceModel convertToPersistenceModel(Table table) {
		
		IPersistenceModel currentPersistenceModel = PersistenceModelFactory
				.createTableModel(table);
		if(currentPersistenceModel == null) return null;
		return currentPersistenceModel;
	}
	
	public static IPersistenceModel convertToPersistenceModel(BusinessClass bussinessClass, BusinessObjectModel bom){
		Table table = convertToTable(bussinessClass,bom);
		IPersistenceModel persistenceModel = convertToPersistenceModel(table);
		if(persistenceModel != null){
			persistenceModel.setScenceName(bussinessClass.getBelongPackage().getName());
			persistenceModel.setJavaPackage(bom.getNameSpace());;
		}
		return persistenceModel;
	}

}
