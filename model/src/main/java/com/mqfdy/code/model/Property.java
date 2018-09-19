package com.mqfdy.code.model;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 业务实体属性.
 *
 * @author mqfdy
 */
public class Property extends AbstractModelElement {

	/** The parent. */
	private AbstractModelElement parent;

	/** The group. */
	private PropertyGroup group;

	/** 校验器列表. */
	private Vector<Validator> validators;

	/** 数据类型. */
	private String dataType;
	
	/** 是否可为null. */
	private boolean nullable;
	
	/** 是否只读. */
	private boolean readOnly;
	
	/** 是否主键. */
	private boolean isPrimaryKey;

	/** 属性编辑器. */
	private PropertyEditor editor;

	/**
	 * Instantiates a new property.
	 */
	public Property() {
		validators = new Vector<Validator>(10);
	}

	/**
	 * Instantiates a new property.
	 *
	 * @param id
	 *            the id
	 */
	public Property(String id) {
		super(id);
		validators = new Vector<Validator>(10);
	}

	/**
	 * 通过 XML 构造 Property.
	 *
	 * @param propertyElement
	 *            the property element
	 */
	@SuppressWarnings("unchecked")
	public Property(Element propertyElement) {
		validators = new Vector<Validator>(10);
		initBasicAttributes(propertyElement);
		String groupId = propertyElement.elementTextTrim("GroupId");// 组装PropertyGroup对象
		if (groupId != null) {
			setGroup(new PropertyGroup(groupId));
		}

		setDataType(propertyElement.elementTextTrim("DataType"));
		setOrderNum(StringUtil.string2Int(propertyElement
				.elementTextTrim("OrderNum")));
		
		setNullable(Boolean.parseBoolean(propertyElement.elementTextTrim("Nullable")));
		setReadOnly(Boolean.parseBoolean(propertyElement.elementTextTrim("ReadOnly")));
		setPrimaryKey(Boolean.parseBoolean(propertyElement.elementTextTrim("IsPrimaryKey")));
		// 获取Property节点下的Validators节点
		Element validatorsElement = propertyElement.element("Validators");
		if (validatorsElement != null) {
			List<Element> validatorElements = validatorsElement
					.elements("Validator");
			if (validatorElements != null) {
				for (Iterator<Element> iter = validatorElements.iterator(); iter
						.hasNext();) {
					Element validatorElement = iter.next();// Validator节点
					Validator validator = new Validator(validatorElement);
					validator.setBelongProperty(this);
					addValidator(validator);
				}
			}
		}

		Element editorElement = propertyElement.element("Editor");
		if (editorElement != null) {
			PropertyEditor editor = new PropertyEditor(editorElement);
			editor.setBelongProperty(this);
			setEditor(editor);
		}
	}

	/**
	 * @param propertiesElement
	 * @return
	 */
	public Element generateXmlElement(Element propertiesElement) {
		Element propertyElement = propertiesElement.addElement("Property");// 创建Property节点
		generateBasicAttributes(propertyElement);
		generateBasicPropertyElement(propertyElement);
		// 属性编辑器
		PropertyEditor editor = getEditor();
		if (editor != null) {
			editor.generateXmlElement(propertyElement);// Editor节点
			// propertyElement.addContent(editorElement);
		}
		return propertyElement;
	}

	/**
	 * Generate basic property element.
	 *
	 * @author mqfdy
	 * @param propertyElement
	 *            the property element
	 * @Date 2018-09-03 09:00
	 */
	public void generateBasicPropertyElement(Element propertyElement) {

		propertyElement.addElement("GroupId").addText(
				StringUtil.convertNull2EmptyStr(getGroup() == null ? ""
						: getGroup().getId()));// GroupId节点
		propertyElement.addElement("DataType").addText(
				StringUtil.convertNull2EmptyStr(getDataType()));// DataType节点
		propertyElement.addElement("OrderNum").addText(
				StringUtil.convertNull2EmptyStr(String.valueOf(getOrderNum())));// OrderNum节点

		List<Validator> validators = getValidators();

		if (validators != null && !validators.isEmpty()) {// 判断是否存在Validator校验器
			Element validatorsElement = propertyElement
					.addElement("Validators");// Validators节点
			for (Validator validator : validators) {
				validator.generateXmlElement(validatorsElement);
				// validatorsElement.addContent(validatorElement);
			}
		}

	}

	/**
	 * @param dest
	 */
	public void copy(AbstractModelElement dest) {
		super.copy(dest);
		((Property) dest).dataType = this.dataType;
		((Property) dest).group = this.group;
	}

	/**
	 * @return
	 */
	public Property clone() {
		Property property = new Property();
		copy(property);
		PropertyEditor editorClone = (PropertyEditor) ((PropertyEditor) getEditor())
				.clone();
		editorClone.setBelongProperty(property);
		property.setEditor(editorClone);
		Vector<Validator> vaList = getValidators();
		for (Validator va : vaList) {
			Validator val = va.clone();
			val.setBelongProperty(property);
			property.addValidator(val);
		}
		return property;
	}

	/**
	 * @param dest
	 */
	public void copyChangeId(AbstractModelElement dest) {
		super.copyChangeId(dest);
		((Property) dest).dataType = this.dataType;
		((Property) dest).group = this.group;
	}

	/**
	 * @return
	 */
	public Property cloneChangeId() {
		Property property = new Property();
		copyChangeId(property);
		PropertyEditor editorClone = (PropertyEditor) ((PropertyEditor) getEditor())
				.cloneChangeId();
		editorClone.setBelongProperty(property);
		property.setEditor(editorClone);
		Vector<Validator> vaList = getValidators();
		for (Validator va : vaList) {
			Validator val = va.cloneChangeId();
			val.setBelongProperty(property);
			property.addValidator(val);
		}
		return property;
	}

	/**
	 * Gets the group.
	 *
	 * @author mqfdy
	 * @return the group
	 * @Date 2018-09-03 09:00
	 */
	public PropertyGroup getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @author mqfdy
	 * @param group
	 *            the new group
	 * @Date 2018-09-03 09:00
	 */
	public void setGroup(PropertyGroup group) {
		this.group = group;
	}

	/**
	 * Gets the data type.
	 *
	 * @author mqfdy
	 * @return the data type
	 * @Date 2018-09-03 09:00
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the new data type
	 * @Date 2018-09-03 09:00
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the editor.
	 *
	 * @author mqfdy
	 * @return the editor
	 * @Date 2018-09-03 09:00
	 */
	public PropertyEditor getEditor() {
		return editor;
	}

	/**
	 * Sets the editor.
	 *
	 * @author mqfdy
	 * @param editor
	 *            the new editor
	 * @Date 2018-09-03 09:00
	 */
	public void setEditor(PropertyEditor editor) {
		this.editor = editor;
	}

	/**
	 * Checks if is persistence.
	 *
	 * @author mqfdy
	 * @return true, if is persistence
	 * @Date 2018-09-03 09:00
	 */
	public boolean isPersistence() {
		return false;
	}

	/**
	 * Gets the validators.
	 *
	 * @author mqfdy
	 * @return the validators
	 * @Date 2018-09-03 09:00
	 */
	public Vector<Validator> getValidators() {
		return validators;
	}

	/**
	 * Sets the validators.
	 *
	 * @author mqfdy
	 * @param validators
	 *            the new validators
	 * @Date 2018-09-03 09:00
	 */
	public void setValidators(Vector<Validator> validators) {
		this.validators.clear();
		this.validators = validators;
	}

	/**
	 * Adds the validator.
	 *
	 * @author mqfdy
	 * @param validator
	 *            the validator
	 * @Date 2018-09-03 09:00
	 */
	public void addValidator(Validator validator) {
		validators.add(validator);

	}

	/**
	 * Sets the parent.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the new parent
	 * @Date 2018-09-03 09:00
	 */
	public void setParent(AbstractModelElement parent) {
		this.parent = parent;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.parent;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	/**
	 * Checks if is nullable.
	 *
	 * @author mqfdy
	 * @return true, if is nullable
	 * @Date 2018-09-03 09:00
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Sets the nullable.
	 *
	 * @author mqfdy
	 * @param nullable
	 *            the new nullable
	 * @Date 2018-09-03 09:00
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * Checks if is read only.
	 *
	 * @author mqfdy
	 * @return true, if is read only
	 * @Date 2018-09-03 09:00
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * Sets the read only.
	 *
	 * @author mqfdy
	 * @param readOnly
	 *            the new read only
	 * @Date 2018-09-03 09:00
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Checks if is primary key.
	 *
	 * @author mqfdy
	 * @return true, if is primary key
	 * @Date 2018-09-03 09:00
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	 * Sets the primary key.
	 *
	 * @author mqfdy
	 * @param isPrimaryKey
	 *            the new primary key
	 * @Date 2018-09-03 09:00
	 */
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

}
