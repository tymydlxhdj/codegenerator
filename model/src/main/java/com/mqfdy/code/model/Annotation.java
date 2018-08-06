package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 注释
 * 
 * @author mqfdy
 * 
 */
public class Annotation extends AbstractModelElement {

	private ModelPackage belongPackage;

	protected String content;
	
	public Annotation() {
		super();
	}

	public Annotation(String id) {
		super(id);
	}

	/**
	 * 通过 XML 构造 BusinessClass
	 * 
	 * @param businessClassElement
	 */
	@SuppressWarnings("unchecked")
	public Annotation(Element businessClassElement) {
		initBasicAttributes(businessClassElement);
		String content = businessClassElement.elementTextTrim("Content");
		setContent(content);
	}

	public Element generateXmlElement(Element bcsElement) {
		Element bcElement = bcsElement.addElement("Annotation");
		generateBasicAttributes(bcElement);
		bcElement.addElement("Content").addText(
				StringUtil.convertNull2EmptyStr(getContent()));
		
		return bcElement;
	}

	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}


	public AbstractModelElement getParent() {
		return this.belongPackage.getAnnotationPackage();
	}


	public String getFullName() {
		if (this.getParent() != null) {
			return this.getParent().getFullName() + ".annotation." + this.getName();
		} else {
			return this.getName();
		}

	}

	protected void copy(AbstractModelElement dest) {
		super.copy(dest);
		Annotation destBc = ((Annotation) dest);
		destBc.setContent(getContent());
		destBc.setBelongPackage(getBelongPackage());
	}

	public Annotation clone() {
		Annotation bc = new Annotation();
		copy(bc);
		return bc;
	}

	public Annotation cloneChangeId() {
		Annotation bc = new Annotation();
		copyChangeId(bc);
		return bc;
	}

	protected void copyChangeId(AbstractModelElement dest) {
		super.copyChangeId(dest);
		Annotation destBc = ((Annotation) dest);
		destBc.setContent(getContent());
		destBc.setBelongPackage(getBelongPackage());

	}

	public List<?> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
