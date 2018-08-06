package com.mqfdy.code.resource;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.resource.validator.ValiResult;
import com.mqfdy.code.resource.validator.Validator;
import com.mqfdy.code.resource.validator.ValidatorContext;

/**
 * 模型校验Manager
 * 
 * @author mqfdy
 * 
 */
public class ValidatorManager {
	private List<ValiResult> resultList = new ArrayList<ValiResult>();

	/**
	 * 整体校验 校验所有的规则
	 * 
	 * @param bom
	 * @return
	 */
	public List<ValiResult> checkModel(BusinessObjectModel businessObjectModel,
			List<AbstractModelElement> checkElements, ValidatorContext con) {
		List<Validator> vaList = Validator.getRules();
		return checkModel(businessObjectModel, checkElements, vaList, con);
	}

	/**
	 * 按照自定义的校验规则列表进行校验
	 * 
	 * @param businessObjectModel
	 *            业务对象模型
	 * @param checkElements
	 *            要校验的模型元素
	 * @param vaList
	 *            自定义的校验规则列表
	 * @param con
	 * @return
	 */
	public List<ValiResult> checkModel(BusinessObjectModel businessObjectModel,
			List<AbstractModelElement> checkElements, List<Validator> vaList,
			ValidatorContext con) {
		for (AbstractModelElement abe : checkElements) {
			if (isNormalCheckElement(abe)) {
				(new Validator()).validateAllRule(businessObjectModel, abe,
						con, resultList);
			} else {
				validate(businessObjectModel, abe, vaList, con, resultList);
			}
		}
		return resultList;
	}

	private static boolean validate(BusinessObjectModel businessObjectModel,
			AbstractModelElement modelElement, List<Validator> vaList,
			ValidatorContext con, List<ValiResult> resultList) {
		boolean flag = true;
		int validatorId = 0;
		if (modelElement instanceof DataTransferObject) {
			con.printToConsole("校验DTO:" + modelElement.getDisplayName(),
					ValidatorContext.INFO);
			validatorId = Validator.ID_DTO;
		} else if (modelElement instanceof Enumeration) {
			con.printToConsole("校验枚举:" + modelElement.getDisplayName(),
					ValidatorContext.INFO);
			validatorId = Validator.ID_ENUMERATION;
		} else if (modelElement instanceof Association) {
			con.printToConsole("校验关联关系:" + modelElement.getDisplayName(),
					ValidatorContext.INFO);
			validatorId = Validator.ID_ASSOCIATION;
		} else if (modelElement instanceof Inheritance) {
			con.printToConsole("校验继承关系:" + modelElement.getDisplayName(),
					ValidatorContext.INFO);
			validatorId = Validator.ID_INHERITANCE;
		} else if (modelElement instanceof BusinessClass) {
			con.printToConsole("校验业务实体:" + modelElement.getDisplayName(),
					ValidatorContext.INFO);
			validatorId = Validator.ID_BUSINESSCLASS;
		}

		for (Validator valid : vaList) {
			if (valid.getId() == validatorId) {
				flag = valid.validate(businessObjectModel, modelElement, con,
						resultList);
				if (validatorId == Validator.ID_BUSINESSCLASS) {
					for (Property pro : ((BusinessClass) modelElement)
							.getProperties()) {
						Validator validor = null;
						con.printToConsole("    校验业务实体属性:" + pro.getName(),
								ValidatorContext.INFO);
						validor = new Validator(
								Validator.ID_BUSINESSCLASS_PROPERTY);
						validor.setValidateRules(valid.getValidateRules());
						boolean f = validor.validate(businessObjectModel, pro,
								con, resultList);
						if (!f)
							flag = f;
						PropertyGroup group = pro.getGroup();
						if (group != null && group.getId() != null
								&& group.getId().trim().length() != 0) {
							validor = new Validator(Validator.ID_PROPERTY_GROUP);
							group.setParent(pro);
							con.printToConsole(
									"    校验业务实体属性分组:" + group.getName(),
									ValidatorContext.INFO);
							validor.setValidateRules(valid.getValidateRules());
							f = validor.validate(businessObjectModel, group,
									con, resultList);
							if (!f)
								flag = f;
						}
						if (pro instanceof PersistenceProperty) {
							PropertyEditor ed = ((PersistenceProperty) pro)
									.getEditor();
							if (ed != null) {
								con.printToConsole(
										"    校验业务实体属性编辑器:" + ed.getEditorType(),
										ValidatorContext.INFO);
								validor = new Validator(
										Validator.ID_PROPERTY_EDITOR);
								validor.setValidateRules(valid
										.getValidateRules());
								f = validor.validate(businessObjectModel, ed,
										con, resultList);
								if (!f)
									flag = f;
							}
						}

					}
					for (BusinessOperation pro : ((BusinessClass) modelElement)
							.getOperations()) {
						Validator validor = null;
						con.printToConsole("    校验业务实体操作:" + pro.getName(),
								ValidatorContext.INFO);
						validor = new Validator(Validator.ID_OPERATION);
						validor.setValidateRules(valid.getValidateRules());
						boolean f = validor.validate(businessObjectModel, pro,
								con, resultList);
						if (!f)
							flag = f;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 是否是常规要校验的模型元素
	 * 
	 * @param element
	 * @return
	 */
	private static boolean isNormalCheckElement(AbstractModelElement element) {
		if (element instanceof ModelPackage) {
			return true;
		} else if (element instanceof BusinessObjectModel) {
			return true;
		} else if (element instanceof Diagram) {
			return true;
		}
		return false;
	}
}
