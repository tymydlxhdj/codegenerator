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

/**
 * 将 PowerDesginser 15 文件 （pdm 文件）生成 eht model xml 文件
 * 
 * 
 */
public class CIMImportor {
	private static SAXReader reader = new SAXReader();
	private static Document doc = null;
	private static List<Column> listColumns = new ArrayList<Column>();
	private static List<Table> listTables = new ArrayList<Table>();
	private static PdmDataType pdmDataType;

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

	

	private  Column findColumn(String id) {
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

	

	private  List<Element> getReferences() {
		List<Element> rtn = new ArrayList<Element>();
		List l = doc
				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:References/o:Reference");// 得到所有的表
		return l;
	}



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