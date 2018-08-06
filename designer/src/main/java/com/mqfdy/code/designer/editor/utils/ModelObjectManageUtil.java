package com.mqfdy.code.designer.editor.utils;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.BomManager;


/**
 * 
 * @author mqfdy
 * 
 */
public class ModelObjectManageUtil {

	/**
	 * XML格式字符串转换为模型
	 * 
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static BusinessObjectModel xml2Model(String xmlStr)
			throws DocumentException {
		BusinessObjectModel bom = null;
		Document document =DocumentHelper.parseText(xmlStr);
		org.dom4j.Element modelElement = document.getRootElement();
		bom = new BusinessObjectModel(modelElement);

		assembleAssociation(bom);
		assembleLinks(bom);
		return bom;

	}
	
	/**
	 * XML格式字符串转业务实体
	 * 
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static BusinessClass xml2BusinessClass(String xmlStr)
			throws DocumentException {
		BusinessClass bsClass = null;
		Document document =DocumentHelper.parseText(xmlStr);
		org.dom4j.Element businessClassElement = document.getRootElement();
		bsClass = new BusinessClass(businessClassElement);

		return bsClass;

	}
	
	/**
	 * XML格式字符串转枚举
	 * 
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static Enumeration xml2Enumeration(String xmlStr)
			throws DocumentException {
		Enumeration e = null;
		Document document =DocumentHelper.parseText(xmlStr);
		org.dom4j.Element enumerationElement = document.getRootElement();
		e = new Enumeration(enumerationElement);

		return e;

	}

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
					List<BusinessClass> bcs = BomManager.getBuildInOm()
							.getBusinessClasses();
					for (int i = 0; i < bcs.size(); i++) {
						if (!StringUtil.isEmpty(classAid)
								&& classAid.equals(bcs.get(i).getId())) {
							association.setClassA(bcs.get(i));
						}
					}
				}

				if (association.getClassB() == null) {
					List<BusinessClass> bcs = BomManager.getBuildInOm()
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
					List<BusinessClass> bcs = BomManager.getBuildInOm()
							.getBusinessClasses();
					for (int i = 0; i < bcs.size(); i++) {
						if (!StringUtil.isEmpty(classAid)
								&& classAid.equals(bcs.get(i).getId())) {
							association.setClassA(bcs.get(i));
						}
					}
				}

				if (association.getClassB() == null) {
					List<BusinessClass> bcs = BomManager.getBuildInOm()
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
