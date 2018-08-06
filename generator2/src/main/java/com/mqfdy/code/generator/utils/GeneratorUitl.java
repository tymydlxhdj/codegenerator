package com.mqfdy.code.generator.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 代码生成工具类
 * 
 * @author mqfdy
 */
public class GeneratorUitl {

	/**
	 * 获取路径下的文件
	 * 
	 * @param projectPath
	 * @return
	 */
	public static List<String> getAllFilesInTheDir(String projectPath) {
		List<String> fileList = new ArrayList<String>();
		File file = new File(projectPath);
		if (!file.exists()) {
			return fileList;
		} else {
			getAllFiles(file, fileList);
			return fileList;
		}
	}

	/**
	 * 递归调用获取文件名
	 * 
	 * @param f
	 * @param list
	 */
	private static void getAllFiles(File f, List<String> list) {
		if (f.isDirectory() && f.exists()) {
			File[] oms = f.listFiles();
			for (int i = 0; i < oms.length; i++) {
				if (oms[i].isFile()) {
					list.add(oms[i].getName());
				} else {
					getAllFiles(oms[i], list);
				}
			}
		}
	}

	/**
	 * 创建中间表名称
	 * 
	 * @param pc1
	 * @param pc2
	 * @return
	 */
	public static String createMiddleTableName(PersistentClass pc1, PersistentClass pc2) {
		if (pc1 == null || pc2 == null) {
			return null;
		} else if (StringUtil.isEmpty(pc1.getClassName()) || StringUtil.isEmpty(pc2.getClassName())) {
			return null;
		}
		String pc1Name = StringUtil.getClassNameFromFullName(pc1.getClassName());
		String pc2Name = StringUtil.getClassNameFromFullName(pc2.getClassName());
		if (pc1Name.compareTo(pc2Name) > 0) {
			return pc1Name + "_" + pc2Name;
		} else {
			return pc2Name + "_" + pc1Name;
		}

	}

	/**
	 * 创建中间表的外键列名称
	 * 
	 * @param pc1
	 * @return
	 */
	public static String createFKColName(PersistentClass pc1) {
		String thirdTableFKColName = null;
		if (pc1 != null) {
			thirdTableFKColName = StringUtil.getClassNameFromFullName(pc1.getClassName()) + "_id";
		} else {
			return null;
		}
		return thirdTableFKColName.toUpperCase(Locale.getDefault());
	}

	/**
	 * 创建映射文件中多方在一方存在的属性名称
	 * 
	 * @param pc1
	 * @return
	 */
	public static String createSetName(PersistentClass pc) {
		String setName = null;
		if (pc != null && !StringUtil.isEmpty(pc.getClassName())) {
			setName = StringUtil.getClassNameFromFullName(pc.getClassName().toLowerCase(Locale.getDefault())) + "s";
		}

		return setName;
	}

	/**
	 * 获取业务实体的主键名称
	 * 
	 * @param bc
	 * @return
	 */
	public static String getPkName(BusinessClass bc) {
		String pkName = "id";
		List<Property> properties = bc.getProperties();
		for (Property p : properties) {
			if (p instanceof PKProperty) {
				pkName = p.getName();
				break;
			}
		}
		return pkName;
	}

	/**
	 * 给定一组持久化实体，如果ddl脚本中存在和实体相关表的sql语句，就删除。
	 * @author rongxin.bian
	 * @param deletedPersistentClasses 一组持久化实体，这些实体不生成对应的DDL语句.
	 * @param ddlFilePath ddl 文件路径
	 * @param remainingWord 保留的词 如果当前ddl语句中含有这个remainingWord，那么不删除。可以传空值。
	 */
	public static void deleteSentencesFromDDL(BusinessObjectModel model, List<PersistentClass> deletedPersistentClasses, String ddlFilePath, String remainingWord) {
		
		//读取DDL文件中的内容
		String ddlFileContent = readFileContent(ddlFilePath);
		//以‘;’ 分割出一条条的sql语句
		String[] ddlSentences = ddlFileContent.split(";");
		
		List<String> newDDLSentences = new ArrayList<String>();
		
		//在模型中遍历所有多对多的关联关系，找出表名并记录在relationRableNameList中
		List<String> relationRableNameList = new ArrayList<String>();
		List<Association> associations = model.getAssociations();
		for(Association association: associations) {
			//如果是多对多关系,并且业务实体时逆向的,记录该表,用于删除由此生成的DDL语句
			if(association.getAssociationType().equals(AssociationType.mult2mult.getValue())&&
					IModelElement.STEREOTYPE_REVERSE.equals(association.getClassA().getStereotype())) {
				relationRableNameList.add(association.getPersistencePloyParams().get(Association.RELATIONTABLENAME));
			}
		}
		
		//遍历所有sql语句，在每一条sql语句中检索这一组持久化的实体
		for(String ddlSentence: ddlSentences) {
		
			boolean flag = true;
			for(PersistentClass deletedPersistentClasse: deletedPersistentClasses) {
				//如果当前ddl语句中含有不该有的表名, 跳过，否则就加入到要生成的字符串中。
				if(isDelete(ddlSentence, deletedPersistentClasse.getTable().getName(), remainingWord)) {
					flag = false;
					break;
				}
			}
			
			//如果当前语句中含有中间表，也需要删除 
			for(String relationRableName: relationRableNameList) {
				if(isDelete(ddlSentence, relationRableName, remainingWord)) {
					flag = false;
					break;
				}
			}
			
			if(flag) {
				newDDLSentences.add(ddlSentence);
			}
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		
		for(String newDDLSentence: newDDLSentences) {
			stringBuffer.append(newDDLSentence);
			if(!newDDLSentence.equals("\n")) {
				stringBuffer.append(";");
			}
		}
		//将要生成的字符串重新写入到DDL文件中
		writeFileContent(ddlFilePath, stringBuffer.toString());
	}

	/**
	 * 判断一条ddl语句中是否要删除
	 * @author rongxin.bian
	 * @param ddlSentence ddl语句
	 * @param tableName 给定的表名
	 * @param remainingWord 保留的词 如果当前ddl语句中含有这个remainingWord，那么不删除。可以传空值。
	 * @return 如果有返回true
	 */
	public static boolean isDelete(String ddlSentence, String tableName, String remainingWord) {
		
		String[] keywords = ddlSentence.split(" ");
		for(String keyword: keywords) {
			if(keyword.trim().toLowerCase(Locale.getDefault()).equals(tableName.toLowerCase(Locale.getDefault()))) {
				
				if(!StringUtil.isEmpty(remainingWord) && ddlSentence.toLowerCase(Locale.getDefault()).contains(remainingWord)) {
					if(ddlSentence.toLowerCase(Locale.getDefault()).contains(remainingWord) && ddlSentence.toLowerCase(Locale.getDefault()).contains(tableName.toLowerCase(Locale.getDefault()))) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			} else {
				if(keyword.indexOf(".") != -1) {
					String[] subKeywords = keyword.trim().split("\\.");
					for(String subKeyword: subKeywords) {
						if(subKeyword.trim().toLowerCase(Locale.getDefault()).equals(tableName.toLowerCase(Locale.getDefault()))) {
							if(!StringUtil.isEmpty(remainingWord) && ddlSentence.toLowerCase(Locale.getDefault()).contains(remainingWord)) {
//								if(ddlSentence.toLowerCase(Locale.getDefault()).contains(remainingWord + tableName.toLowerCase(Locale.getDefault()))) {
								if(ddlSentence.toLowerCase(Locale.getDefault()).contains(remainingWord) && ddlSentence.toLowerCase(Locale.getDefault()).contains(tableName.toLowerCase(Locale.getDefault()))) {
									return true;
								} else {
									return false;
								}
							} else {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 读取指定文件中的内容
	 * @author rongxin.bian
	 * @param filePath 文件路径
	 * @return 文件内容 字符串
	 */
	public static String readFileContent(String filePath) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			
			String currentString = "";
			
			StringBuffer stringBuffer = new StringBuffer();
			while((currentString = reader.readLine()) != null) {
				stringBuffer.append(currentString + "\n");
			}
			
			return stringBuffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 向指定文件中写入内容
	 * @author rongxin.bian
	 * @param filePath 文件路径
	 * @param content 文件内容 
	 */
	public static void writeFileContent(String filePath, String content) {
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(content);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 获取某节点所属的bom对象
	 * 
	 * @param element
	 *            任意节点
	 * @return
	 */
	public static BusinessObjectModel getBusinessModelOfElement(AbstractModelElement element) {
		if (element == null) {
			return null;
		} else if (element instanceof BusinessObjectModel) {
			return (BusinessObjectModel) element;
		}

		AbstractModelElement bom;
		bom = element.getParent();
		while (bom != null && !(bom instanceof BusinessObjectModel)) {
			bom = bom.getParent();
		}
		if (bom != null) {
			return (BusinessObjectModel) bom;
		} else {
			return null;
		}
	}

	/**
	 * 判断bc主键是否首字母小写且第二个为字母且大写
	 * 
	 * @param bcName
	 * @return
	 */
	public static boolean isSpecialId(String pkName) {
		if(pkName.length()<2){
			return false;
		}
		char firstLetter = pkName.charAt(0);
		char secondLetter = pkName.charAt(1);
		if ((firstLetter >= 97 && firstLetter <= 122) && (secondLetter >= 65 && secondLetter <= 90)) {//
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param src
	 * @param find
	 * @return
	 */
	public static int getOccur(String src, String find) {
		int o = 0;
		int index = -1;
		while ((index = src.indexOf(find, index)) > -1) {
			++index;
			++o;
		}
		return o;
	}

	/**
	 * 在一组持久化实体中检索当前持久化的对象，看是否存在。
	 * @author rongxin.bian
	 * @param objectClass
	 * @param persistentClasses
	 * @return 存在返回true
	 */
	public static boolean isExsitPersistentClass(PersistentClass objectClass, List<PersistentClass> persistentClasses) {
		for(PersistentClass persistentClass: persistentClasses) {
			if(objectClass.getClassName().equals(persistentClass.getClassName())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isExsitTable(Table table, List<PersistentClass> persistentClasses) {
		for(PersistentClass persistentClass: persistentClasses) {
			if(table.getName().equals(persistentClass.getTable().getName())) {
				return true;
			}
		}
		
		return false;
	}
}
