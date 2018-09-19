package com.mqfdy.code.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * BOM文件的管理类.
 *
 * @author mqfdy
 */
public class BomManager {

	/** The Constant STR_MODEL. */
	private static final String STR_MODEL = "model";
	
	/** The Constant STR_OM. */
	private static final String STR_OM = "bom";

	/** The build in model. */
	private static BusinessObjectModel buildInModel;

	/**
	 * 模型转换为XML文档.
	 *
	 * @author mqfdy
	 * @param bom
	 *            om模型
	 * @return the document
	 * @Date 2018-09-03 09:00
	 */
	private static Document model2XmlDoc(BusinessObjectModel bom) {
		Document document = DocumentHelper.createDocument();
		Element modelElement = bom.generateXmlElement(document);
		document.setRootElement(modelElement);
		return document;
	}

	/**
	 * 输出xml文件.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @param path
	 *            the path
	 * @Date 2018-09-03 09:00
	 */
	public static void outputXmlFile(BusinessObjectModel bom, String path) {
		Document doc = model2XmlDoc(bom);

		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = null;
		OutputStreamWriter osw = null;
		FileOutputStream fis = null;
		try {
			fis = new FileOutputStream(
					path);
			osw = new OutputStreamWriter(fis, "UTF-8");
			writer = new XMLWriter(osw, format);

			writer.write(doc);
			
		} catch (UnsupportedEncodingException e1) {
			throw new RuntimeException("编码格式不支持", e1);
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("未找到文件", e1);
		} catch (IOException e) {
			throw new RuntimeException("文件读写错误", e);
		} finally{
			if(osw != null){
				try {
					osw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * XML文档转换为模型.
	 *
	 * @author mqfdy
	 * @param path
	 *            xml文档路径
	 * @return the business object model
	 * @throws DocumentException
	 *             the document exception
	 * @Date 2018-09-03 09:00
	 */
	public static BusinessObjectModel xml2Model(String path)
			throws DocumentException {
		BusinessObjectModel bom = null;
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(path));
		org.dom4j.Element modelElement = document.getRootElement();
		bom = new BusinessObjectModel(modelElement);

		assembleAssociation(bom);
		assembleLinks(bom);
		return bom;

	}

	/**
	 * XML文档转换为模型.
	 *
	 * @author mqfdy
	 * @param is
	 *            xml文件流
	 * @return the business object model
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentException
	 *             the document exception
	 * @Date 2018-09-03 09:00
	 */
	public static BusinessObjectModel xml2Model(InputStream is)
			throws IOException, DocumentException {
		BusinessObjectModel bom = null;
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(is);
		Element modelElement = doc.getRootElement();

		bom = new BusinessObjectModel(modelElement);
		assembleAssociation(bom);
		assembleLinks(bom);
		return bom;
	}
		
		/**
		 * 获取内置的模型.
		 *
		 * @author mqfdy
		 * @return the builds the in om
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 * @throws DocumentException
		 *             the document exception
		 * @Date 2018-09-03 09:00
		 */
	public static BusinessObjectModel getBuildInOm() throws IOException,
			DocumentException {
		if (buildInModel == null) {
			Bundle bundle = FrameworkUtil.getBundle(BomManager.class);
			Enumeration<URL> e = bundle
					.findEntries("om", "built-in.xml", false);
			if (e.hasMoreElements()) {
				URL url = (URL) e.nextElement();
				URLConnection con = url.openConnection();
				con.setUseCaches(false);
				buildInModel = xml2Model(con.getInputStream());
			}
		}
		return buildInModel;
	}
	
	/**
	 * 获取内置的模型.
	 *
	 * @author mqfdy
	 * @param projectPath
	 *            the project path
	 * @param modelId
	 *            the model id
	 * @return the business object model
	 * @Date 2018-09-03 09:00
	 *//*
	public static BusinessObjectModel getBuildInOm() throws 
			DocumentException {
		if (buildInModel == null) {
			Bundle bundle = FrameworkUtil.getBundle(OmManager.class);
			Enumeration<URL> e = bundle
					.findEntries("om", "built-in.xml", false);
			if (e.hasMoreElements()) {
				URL url = (URL) e.nextElement();
				URLConnection con = null;
				InputStream is = null;
				try {
					con = url.openConnection();
					con.setUseCaches(false);
					SAXReader reader = new SAXReader(false);
					Document doc = reader.read(con.getInputStream());
					Element modelElement = doc.getRootElement();
					buildInModel = new BusinessObjectModel(modelElement);
					assembleAssociation(buildInModel);
					assembleLinks(buildInModel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					if(is != null){
						try {
							is.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
			}
		}
		return buildInModel;
	}*/

	/**
	 * 将指定om文件转换为BusinessObjectModel对象
	 * 
	 * @param projectPath
	 * @param modelId
	 * @return
	 */
	public BusinessObjectModel convertOmFileToModel(String projectPath,
			String modelId) {
		List<String> omList = getOMList(projectPath);
		BusinessObjectModel bom = null;
		for (String om : omList) {
			try {
				if (getModelId(om).equals(modelId)) {
					bom = xml2Model(om);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		if (bom != null) {
			assembleAssociation(bom);
			assembleLinks(bom);
		}
		return bom;
	}

	/**
	 * 获取模型文件的ID.
	 *
	 * @author mqfdy
	 * @param xmlPath
	 *            the xml path
	 * @return the model id
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentException
	 *             the document exception
	 * @Date 2018-09-03 09:00
	 */
	public static String getModelId(String xmlPath) throws IOException,
			DocumentException {
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(xmlPath);
		Element modelElement = doc.getRootElement();
		return modelElement.attributeValue("id");
	}

	/**
	 * 获取模型文件的ID.
	 *
	 * @author mqfdy
	 * @param is
	 *            the is
	 * @return the model id
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentException
	 *             the document exception
	 * @Date 2018-09-03 09:00
	 */
	public static String getModelId(InputStream is) throws IOException,
			DocumentException {
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(is);
		Element modelElement = doc.getRootElement();
		return modelElement.attributeValue("id");
	}
	
	/**
	 * 根据文件路径获取模型，不包含子节点.
	 *
	 * @author mqfdy
	 * @param xmlPath
	 *            the xml path
	 * @return the only model
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentException
	 *             the document exception
	 * @Date 2018-09-03 09:00
	 */
	public static BusinessObjectModel getOnlyModel(String xmlPath) throws IOException,
		DocumentException {
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(xmlPath);
		Element modelElement = doc.getRootElement();
		BusinessObjectModel bom = new BusinessObjectModel("","");
		String id = modelElement.attributeValue("id");
		String nameSpace = modelElement.attributeValue("nameSpace") == null ? ""
				: modelElement.attributeValue("nameSpace");// 取出namespace
		String name = modelElement.attributeValue("name");
		String stereotype = modelElement.attributeValue("stereotype");
		String displayName = modelElement.attributeValue("displayName");
		String remark = modelElement.elementTextTrim("Remark");
		bom.setId(id);
		bom.setName(name);
		bom.setStereotype(stereotype);
		bom.setDisplayName(displayName);
		bom.setRemark(remark);
		bom.setNameSpace(nameSpace);
		bom.setStereotype(IModelElement.STEREOTYPE_REFERENCE);
		HashMap<String, Object> extendAttributies = new HashMap<String, Object>();
//		extendAttributies.put(IModelElement.REFMODELPATH, xmlPath);
		bom.setExtendAttributies(extendAttributies);
		return bom;
	}
	
	/**
	 * 返回项目中存在的om文件集合.
	 *
	 * @author mqfdy
	 * @param projectPath
	 *            the project path
	 * @return the OM list
	 * @Date 2018-09-03 09:00
	 */
	public static List<String> getOMList(String projectPath) {
		List<String> omList = new ArrayList<String>();
		File omDir = new File(projectPath + File.separator + STR_MODEL
				+ File.separator + STR_OM);
		getAllOm(omDir, omList);
		return omList;
	}

	/**
	 * 递归获取OM文件路径.
	 *
	 * @author mqfdy
	 * @param f
	 *            the f
	 * @param omList
	 *            the om list
	 * @return the all om
	 * @Date 2018-09-03 09:00
	 */
	private static void getAllOm(File f, List<String> omList) {
		if (f.isDirectory() && f.exists()) {
			File[] oms = f.listFiles();
			for (int i = 0; i < oms.length; i++) {
				if (oms[i].isFile() && oms[i].toString().endsWith(STR_OM)) {
					omList.add(oms[i].getPath());
				} else {
					getAllOm(oms[i], omList);
				}
			}
		}
	}

	/**
	 * Assemble association.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @Date 2018-09-03 09:00
	 */
	private static void assembleAssociation(BusinessObjectModel bom) {
		List<Association> associations = bom.getAssociations();
		List<BusinessClass> entities = bom.getBusinessClasses();
		for (Association association : associations) {
			String classAid = association.getClassAid();
			String classBid = association.getClassBid();
			for (int i = 0; i < entities.size(); i++) {

				if (!StringUtil.isEmpty(classAid)
						&& classAid.equals(entities.get(i).getId())) {// 实体A
																		// id相同
					association.setClassA(entities.get(i));
				}
				if (!StringUtil.isEmpty(classBid)
						&& classBid.equals(entities.get(i).getId())) {// 实体B
																		// id相同
					association.setClassB(entities.get(i));
				}
				if (association.getClassA() != null
						&& association.getClassB() != null) {
					break;
				}
			}
			try {
				if (association.getClassA() == null) {
					List<BusinessClass> bcs = getBuildInOm()
							.getBusinessClasses();
					for (int i = 0; i < bcs.size(); i++) {
						if (!StringUtil.isEmpty(classAid)
								&& classAid.equals(bcs.get(i).getId())) {
							association.setClassA(bcs.get(i));
						}
					}
				}

				if (association.getClassB() == null) {
					List<BusinessClass> bcs = getBuildInOm()
							.getBusinessClasses();
					for (int i = 0; i < bcs.size(); i++) {
						if (!StringUtil.isEmpty(classBid)
								&& classBid.equals(bcs.get(i).getId())) {
							association.setClassB(bcs.get(i));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Assemble links.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @Date 2018-09-03 09:00
	 */
	private static void assembleLinks(BusinessObjectModel bom) {
		List<LinkAnnotation> links = bom.getLinkAnnotations();
		List<AbstractModelElement> ens = new ArrayList<AbstractModelElement>();
		List<BusinessClass> entities = bom.getBusinessClasses();
		ens.addAll(bom.getAnnotations());
		ens.addAll(bom.getEnumerations());
		ens.addAll(bom.getReferenceObjects());
		ens.addAll(entities);
		for (LinkAnnotation association : links) {
			String classAid = association.getClassAid();
			String classBid = association.getClassBid();
			for (int i = 0; i < ens.size(); i++) {

				if (!StringUtil.isEmpty(classAid)
						&& classAid.equals(ens.get(i).getId())) {// 实体A
																		// id相同
					association.setClassA(ens.get(i));
				}
				if (!StringUtil.isEmpty(classBid)
						&& classBid.equals(ens.get(i).getId())) {// 实体B
																		// id相同
					association.setClassB(ens.get(i));
				}
				if (association.getClassA() != null
						&& association.getClassB() != null) {
					break;
				}
			}
			try {
				if (association.getClassA() == null) {
					List<BusinessClass> bcs = getBuildInOm()
							.getBusinessClasses();
					for (int i = 0; i < bcs.size(); i++) {
						if (!StringUtil.isEmpty(classAid)
								&& classAid.equals(bcs.get(i).getId())) {
							association.setClassA(bcs.get(i));
						}
					}
				}

				if (association.getClassB() == null) {
					List<BusinessClass> bcs = getBuildInOm()
							.getBusinessClasses();
					for (int i = 0; i < bcs.size(); i++) {
						if (!StringUtil.isEmpty(classBid)
								&& classBid.equals(bcs.get(i).getId())) {
							association.setClassB(bcs.get(i));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
