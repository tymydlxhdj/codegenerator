package com.mqfdy.code.thirdparty;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.thirdparty.utils.PdmDataType;

/**
 * 将 PowerDesginser 15 文件 （pdm 文件）生成 eht model xml 文件
 * 
 * 
 */
public class PdmImport {
	private static SAXReader reader = new SAXReader();
	private static Document doc = null;
	private static List<Column> listColumns = new ArrayList<Column>();
	private static List<Table> listTables = new ArrayList<Table>();
	private static PdmDataType pdmDataType;

	private static Element getPrimaryKeyOfId(String id) {
//		List l = doc
//				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table/c:Keys/o:Key");
		
		List l = doc
				.selectNodes("//c:Keys//o:Key");
		for (Object obj : l) {
			Element e = (Element) obj;
			if (id.equals(e.attributeValue("Id"))) {
				return e;
			}
		}
		return null;
	}

	

	private static Column findColumn(String id) {
		for (Column column : listColumns) {
			if (column.getId().equals(id)) {
				return column;
			}
		}
		return null;
	}

	private static Table findTable(String id) {
		for (Table table : listTables) {
			if (table.getId().equals(id)) {
				return table;
			}
		}
		return null;
	}

	

	private static List<Element> getReferences() {
		List<Element> rtn = new ArrayList<Element>();
//		List l = doc
//				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:References/o:Reference");// 得到所有的表
		
		List l = doc
				.selectNodes("//c:References//o:Reference");// 得到所有的表
		return l;
	}



	public static List<Table> createModelXml(String filename,
			String createfile, boolean paginateFile) {
		FileReader frd = null;
		try {
			listColumns.clear();
			listTables.clear();
			frd=new FileReader(filename);
			doc = reader.read(frd);

			@SuppressWarnings("rawtypes")
//			List tables = doc
//					.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/child::*");// 得到所有的表
			
			List tables = (List) doc
					.selectNodes("//c:Tables//o:Table");// 得到所有的表

			@SuppressWarnings("rawtypes")
//			List tcolumns = doc
//					.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table/c:Columns/o:Column");// 得到所有的列
			
			List tcolumns = doc
					.selectNodes("//c:Columns//o:Column");// 得到所有的列

			Document wdoc = null;
			XMLWriter writer = null;
			Element root = null;

			if (paginateFile == false) {
				wdoc = DocumentHelper.createDocument();

				root = wdoc.addElement("model");
				root.addAttribute("xmlns:xsi",
						"http://www.w3.org/2001/XMLSchema-instance");
				root.addAttribute("xsi:noNamespaceSchemaLocation", "schema.xsd");
			}

			for (Object o : tcolumns) {
				Element columnElement = (Element) o;
				try {

					Column column = new Column();
					column.setId(columnElement.attributeValue("Id"));
					column.setName(KeyWordsChecker.doCheckSql(columnElement.element("Code").getTextTrim())==true?columnElement.element("Code").getTextTrim()+"_":columnElement.element("Code").getTextTrim() );
					if(columnElement.element("Comment")!=null){
						column.setComment(columnElement.element("Comment").getTextTrim());
					}
					
					
					if(columnElement.element("Length") != null){
						column.setLength(StringUtil.string2Int(columnElement.element("Length").getTextTrim()));
					}
					
					if(columnElement.element("DataType")!=null){
						 column.setSqlType(pdmDataType.getOmDataType(columnElement.element("DataType").getTextTrim()));
					}else{
						column.setSqlType(pdmDataType.getOmDataType(null));
					}           		    
					listColumns.add(column);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(frd!=null){
						frd.close();
					}
				}

			}

			for (Object elementTable : tables) {
				Element pdmtable = (Element) elementTable;
				Table tablepdm = new Table();
				tablepdm.setId(pdmtable.attributeValue("Id"));
				tablepdm.setName(KeyWordsChecker.doCheckSql(pdmtable.element("Code").getTextTrim())==true?pdmtable.element("Code").getTextTrim()+"_":pdmtable.element("Code").getTextTrim());
				if(pdmtable.element("Comment")!=null){
					tablepdm.setComment(pdmtable.element("Comment").getTextTrim());
				}
				ReverseContext.allTables.put(tablepdm.getName(), tablepdm);
				List elmentColumnss = pdmtable.elements("Columns");

				if (elmentColumnss != null && elmentColumnss.size() > 0) {
					Element columns = (Element) elmentColumnss.get(0);
					List elmentColumns = columns.elements("Column");

					for (Object obj : elmentColumns) {
						Element columnEle = (Element) obj;
						String columnId = columnEle.attributeValue("Id");

						for (Column column : listColumns) {
							if (column.getId().equals(columnId)) {
								tablepdm.addColumn(column);

							}
						}
					}
					Element pkElement = pdmtable.element("PrimaryKey");
					if (pkElement != null) {
						Element keyElement = getPrimaryKeyOfId(pkElement
								.element("Key").attributeValue("Ref"));

						Element fields = keyElement.element("Key.Columns");
						String keyName = keyElement.element("Code").getText();
						if (fields != null) {

							List<Element> pkCols = fields.elements("Column");
							List<Column> plColumns = new ArrayList();
							for (Object c : pkCols) {
								Column column = findColumn(((Element) c)
										.attributeValue("Ref"));
								plColumns.add(column);
							}
							PrimaryKey primaryKey = new PrimaryKey(keyName);
							primaryKey.setColumns(plColumns);

							tablepdm.setPrimaryKey(primaryKey);
						} else {
							// 没有主键
						}

					}
				} else {
					// 没有列

				}
				listTables.add(tablepdm);
			}
			List<Element> references = getReferences();
			if (references != null && references.size() > 0) {

				for (Element e : references) {
					ForeignKey foreignkey = new ForeignKey();
					foreignkey.setName(e.element("Code").getText());
					Element parentElement = e.element("ParentTable").element(
							"Table");
					Element childElement = e.element("ChildTable").element(
							"Table");

					Table referedTable = findTable(parentElement
							.attributeValue("Ref"));
					Table curTable = findTable(childElement
							.attributeValue("Ref"));

					List<Element> joinsElements = e.element("Joins").elements(
							"ReferenceJoin");

					List<Column> fkColumns = new ArrayList();
					for (Element join : joinsElements) {
						String fkColumnId = join.element("Object2")
								.element("Column").attributeValue("Ref");
						Column fkColumn = findColumn(fkColumnId);

						fkColumns.add(fkColumn);
					}
					foreignkey.setColumns(fkColumns);
					foreignkey.setTable(curTable);
					foreignkey.setReferencedTable(referedTable);
					curTable.getForeignKeys().put(foreignkey.getName(),
							foreignkey);

				}

			} else {
				// 没有外键
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.log(e.getMessage());
		}finally{
			if(frd!=null){
				try {
					frd.close();
				} catch (IOException e) {
					e.printStackTrace();
					Logger.log(e.getMessage());
				}
			}
		}
		return listTables;
	}

}