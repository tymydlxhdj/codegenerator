package com.mqfdy.code.resource.validator;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 模型校验校验器模型
 * 
 * @author mqfdy
 * 
 */
public class Validator {

	public static int ID_MODEL = 1;

	public static int ID_PACKAGE = 2;

	public static int ID_DIAGRAM = 3;

	public static int ID_DIAGRAME_ELEMENT = 4;

	public static int ID_BUSINESSCLASS = 5;

	public static int ID_BUSINESSCLASS_PROPERTY = 6;

	public static int ID_PROPERTY_GROUP = 7;

	public static int ID_PROPERTY_EDITOR = 8;

	public static int ID_ENUMERATION = 9;

	public static int ID_DTO = 10;

	public static int ID_ASSOCIATION = 11;

	public static int ID_INHERITANCE = 12;

	public static int ID_OPERATION = 13;

	private int id;
	private String name;
	private boolean isSelectionValid;
	private List<ValidatorRules> validateRules = new ArrayList<ValidatorRules>();

	private List<ValiResult> resultList = new ArrayList<ValiResult>();

	public Validator(int id, String name, boolean isSelectionValid) {
		this.setId(id);
		this.setName(name);
		this.setSelectionValid(isSelectionValid);
		this.initValidateRules();
	}

	public Validator(int id, String name) {
		this.setId(id);
		this.setName(name);
		this.setSelectionValid(true);
		this.initValidateRules();
	}

	public Validator(int id) {
		this.setId(id);
		this.setSelectionValid(true);
		this.initValidateRules();
	}

	public Validator() {

	}

	public Validator(AbstractModelElement abe) {

		if (abe instanceof ModelPackage) {
			this.setId(ID_PACKAGE);
		} else if (abe instanceof BusinessObjectModel) {
			this.setId(ID_MODEL);
		} else if (abe instanceof Diagram) {
			this.setId(ID_DIAGRAM);
		} else if (abe instanceof DiagramElement) {
			this.setId(ID_DIAGRAME_ELEMENT);
		}
		this.setSelectionValid(true);
		this.initValidateRules();
	}

	public static List<Validator> getRules() {
		List<Validator> rules = new ArrayList<Validator>();
		Validator va;
		va = new Validator(ID_BUSINESSCLASS, "业务实体");
		rules.add(va);

		va = new Validator(ID_ENUMERATION, "枚举");
		rules.add(va);

		// va = new Validator(10, "DTO",true);
		// rules.add(va);

		va = new Validator(ID_ASSOCIATION, "关联关系");
		rules.add(va);

		// va = new Validator(12, "继承关系",true);
		// rules.add(va);

		return rules;
	}

	public static List<Validator> getAllRules() {
		List<Validator> rules = new ArrayList<Validator>();
		Validator va;
		va = new Validator(ID_MODEL, "模型");
		rules.add(va);

		va = new Validator(ID_PACKAGE, "包");
		rules.add(va);

		va = new Validator(ID_DIAGRAM, "图");
		rules.add(va);

		va = new Validator(ID_DIAGRAME_ELEMENT, "图元");
		rules.add(va);

		va = new Validator(ID_BUSINESSCLASS, "业务实体");
		rules.add(va);

		va = new Validator(ID_BUSINESSCLASS_PROPERTY, "业务实体属性");
		rules.add(va);

		va = new Validator(ID_PROPERTY_GROUP, "业务实体属性分组");
		rules.add(va);

		va = new Validator(ID_PROPERTY_EDITOR, "业务实体属性编辑器");
		rules.add(va);

		va = new Validator(ID_ENUMERATION, "枚举");
		rules.add(va);

		// va = new Validator(10, "DTO",true);
		// rules.add(va);

		va = new Validator(ID_ASSOCIATION, "关联关系");
		rules.add(va);

		// va = new Validator(12, "继承关系",true);
		// rules.add(va);

		va = new Validator(ID_OPERATION, "业务实体操作");
		rules.add(va);

		return rules;
	}

	public boolean validateAllRule(BusinessObjectModel businessObjectModel,
			AbstractModelElement abe, ValidatorContext con,
			List<ValiResult> resultList) {
		boolean flag = true;
		Validator validor = new Validator(abe);
		if (abe instanceof ModelPackage) {
			boolean f = validor.validate(businessObjectModel, abe, con,
					resultList);
			if (!f)
				flag = f;
		} else if (abe instanceof BusinessObjectModel) {
			boolean f = validor.validate(businessObjectModel, abe, con,
					resultList);
			if (!f)
				flag = f;
		} else if (abe instanceof Diagram) {

			boolean f = validor.validate(businessObjectModel, abe, con,
					resultList);
			if (!f)
				flag = f;
			for (DiagramElement dia : ((Diagram) abe).getElements()) {
				validor = new Validator(dia);
				f = validor.validate(businessObjectModel, dia, con, resultList);
				if (!f)
					flag = f;
			}
		}
		return flag;
	}

	public boolean validate(BusinessObjectModel businessObjectModel,
			AbstractModelElement ele, ValidatorContext con,
			List<ValiResult> resultList) {
		this.resultList = resultList;
		boolean flag = true;
		for (ValidatorRules rule : this.getValidateRules()) {
			if (rule.isSelectionValid()) {
				boolean f = rule.validate(businessObjectModel, ele, con,
						resultList);
				if (!f)
					flag = f;
			}
		}
		return flag;
	}

	public void initValidateRules() {
		for (ValidatorRules r : ValidatorRules.getRules(this)) {
			if (r.getValidator() == this)
				this.validateRules.add(r);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelectionValid() {
		return isSelectionValid;
	}

	public void setSelectionValid(boolean isSelectionValid) {
		this.isSelectionValid = isSelectionValid;
	}

	public List<ValidatorRules> getValidateRules() {
		return validateRules;
	}

	public void setValidateRules(List<ValidatorRules> validateRules) {
		this.validateRules = validateRules;
	}

}
