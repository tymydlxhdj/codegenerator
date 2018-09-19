package com.mqfdy.code.thirdparty;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.thirdparty.utils.PdmDataType;

// TODO: Auto-generated Javadoc
/**
 * 将 PowerDesginser 15 文件 （pdm 文件）生成 eht model xml 文件.
 *
 * @author mqfdy
 */
public class CIMImportor {
	
	/** The reader. */
	private static SAXReader reader = new SAXReader();
	
	/** The doc. */
	private static Document doc = null;
	
	/** The list columns. */
	private static List<Column> listColumns = new ArrayList<Column>();
	
	/** The list tables. */
	private static List<Table> listTables = new ArrayList<Table>();
	
	/** The pdm data type. */
	private static PdmDataType pdmDataType;

	/**
	 * Gets the primary key of id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the primary key of id
	 * @Date 2018-09-03 09:00
	 */
	private  Element getPrimaryKeyOfId(String id) {
		List l = doc
				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table/c:Keys/o:Key");
		for (Object obj : l) {
			Element e = (Element) obj;
			if (id.equals(e.attributeValue("Id"))) {
				return e;
			}
		}
		return null;
	}

	

	/**
	 * Find column.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the column
	 * @Date 2018-09-03 09:00
	 */
	private  Column findColumn(String id) {
		for (Column column : listColumns) {
			if (column.getId().equals(id)) {
				return column;
			}
		}
		return null;
	}

	/**
	 * Find table.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the table
	 * @Date 2018-09-03 09:00
	 */
	private static Table findTable(String id) {
		for (Table table : listTables) {
			if (table.getId().equals(id)) {
				return table;
			}
		}
		return null;
	}

	

	/**
	 * Gets the references.
	 *
	 * @author mqfdy
	 * @return the references
	 * @Date 2018-09-03 09:00
	 */
	private  List<Element> getReferences() {
		List<Element> rtn = new ArrayList<Element>();
		List l = doc
				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:References/o:Reference");// 得到所有的表
		return l;
	}



	/**
	 * Creates the model xml.
	 *
	 * @author mqfdy
	 * @param filename
	 *            the filename
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static  List createModelXml(String filename) {
		
		List packages=new ArrayList();
		if(filename==null){
			return packages;
		}
		FileReader frd = null;
			try {
				frd = new FileReader(filename);
				doc = reader.read(frd);
				packages  = doc
						.selectNodes("Model/o:RootObject/c:Children/o:Model");// 得到所有的表

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} finally {
				if(frd != null){
					try {
						frd.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return packages;
	}
	
	/**
	 * Gets the CIM model.
	 *
	 * @author mqfdy
	 * @param filename
	 *            the filename
	 * @return the CIM model
	 * @Date 2018-09-03 09:00
	 */
	public static  List getCIMModel(String filename) {
		
		List packages=new ArrayList();
		if(filename==null){
			return packages;
		}
		FileReader frd = null;
			try {
				frd = new FileReader(filename);
				doc = reader.read(frd);
				packages  = doc
						.selectNodes("Model");// 得到整个模型描述

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}finally{
				if(frd != null){
					try {
						frd.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return packages;
	}

}