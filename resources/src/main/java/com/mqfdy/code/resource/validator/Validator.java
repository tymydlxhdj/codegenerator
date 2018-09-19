package com.mqfdy.code.resource.validator;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * 模型校验校验器模型.
 *
 * @author mqfdy
 */
public class Validator {

	/** The id model. */
	public static int ID_MODEL = 1;

	/** The id package. */
	public static int ID_PACKAGE = 2;

	/** The id diagram. */
	public static int ID_DIAGRAM = 3;

	/** The id diagrame element. */
	public static int ID_DIAGRAME_ELEMENT = 4;

	/** The id businessclass. */
	public static int ID_BUSINESSCLASS = 5;

	/** The id businessclass property. */
	public static int ID_BUSINESSCLASS_PROPERTY = 6;

	/** The id property group. */
	public static int ID_PROPERTY_GROUP = 7;

	/** The id property editor. */
	public static int ID_PROPERTY_EDITOR = 8;

	/** The id enumeration. */
	public static int ID_ENUMERATION = 9;

	/** The id dto. */
	public static int ID_DTO = 10;

	/** The id association. */
	public static int ID_ASSOCIATION = 11;

	/** The id inheritance. */
	public static int ID_INHERITANCE = 12;

	/** The id operation. */
	public static int ID_OPERATION = 13;

	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	/** The is selection valid. */
	private boolean isSelectionValid;
	
	/** The validate rules. */
	private List<ValidatorRules> validateRules = new ArrayList<ValidatorRules>();

	/** The result list. */
	private List<ValiResult> resultList = new ArrayList<ValiResult>();

	/**
	 * Instantiates a new validator.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param isSelectionValid
	 *            the is selection valid
	 */
	public Validator(int id, String name, boolean isSelectionValid) {
		this.setId(id);
		this.setName(name);
		this.setSelectionValid(isSelectionValid);
		this.initValidateRules();
	}

	/**
	 * Instantiates a new validator.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 */
	public Validator(int id, String name) {
		this.setId(id);
		this.setName(name);
		this.setSelectionValid(true);
		this.initValidateRules();
	}

	/**
	 * Instantiates a new validator.
	 *
	 * @param id
	 *            the id
	 */
	public Validator(int id) {
		this.setId(id);
		this.setSelectionValid(true);
		this.initValidateRules();
	}

	/**
	 * Instantiates a new validator.
	 */
	public Validator() {

	}

	/**
	 * Instantiates a new validator.
	 *
	 * @param abe
	 *            the abe
	 */
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

	/**
	 * Gets the rules.
	 *
	 * @author mqfdy
	 * @return the rules
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the all rules.
	 *
	 * @author mqfdy
	 * @return the all rules
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Validate all rule.
	 *
	 * @author mqfdy
	 * @param businessObjectModel
	 *            the business object model
	 * @param abe
	 *            the abe
	 * @param con
	 *            the con
	 * @param resultList
	 *            the result list
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Validate.
	 *
	 * @author mqfdy
	 * @param businessObjectModel
	 *            the business object model
	 * @param ele
	 *            the ele
	 * @param con
	 *            the con
	 * @param resultList
	 *            the result list
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Inits the validate rules.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initValidateRules() {
		for (ValidatorRules r : ValidatorRules.getRules(this)) {
			if (r.getValidator() == this)
				this.validateRules.add(r);
		}
	}

	/**
	 * Gets the id.
	 *
	 * @author mqfdy
	 * @return the id
	 * @Date 2018-09-03 09:00
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the new id
	 * @Date 2018-09-03 09:00
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is selection valid.
	 *
	 * @author mqfdy
	 * @return true, if is selection valid
	 * @Date 2018-09-03 09:00
	 */
	public boolean isSelectionValid() {
		return isSelectionValid;
	}

	/**
	 * Sets the selection valid.
	 *
	 * @author mqfdy
	 * @param isSelectionValid
	 *            the new selection valid
	 * @Date 2018-09-03 09:00
	 */
	public void setSelectionValid(boolean isSelectionValid) {
		this.isSelectionValid = isSelectionValid;
	}

	/**
	 * Gets the validate rules.
	 *
	 * @author mqfdy
	 * @return the validate rules
	 * @Date 2018-09-03 09:00
	 */
	public List<ValidatorRules> getValidateRules() {
		return validateRules;
	}

	/**
	 * Sets the validate rules.
	 *
	 * @author mqfdy
	 * @param validateRules
	 *            the new validate rules
	 * @Date 2018-09-03 09:00
	 */
	public void setValidateRules(List<ValidatorRules> validateRules) {
		this.validateRules = validateRules;
	}

}
