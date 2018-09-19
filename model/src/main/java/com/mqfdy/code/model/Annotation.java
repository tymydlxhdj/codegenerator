package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Annotation.
 *
 * @author mqfdy
 */
public class Annotation extends AbstractModelElement {

	/** The belong package. */
	private ModelPackage belongPackage;

	/** The content. */
	protected String content;
	
	/**
	 * Instantiates a new annotation.
	 */
	public Annotation() {
		super();
	}

	/**
	 * Instantiates a new annotation.
	 *
	 * @param id the id
	 */
	public Annotation(String id) {
		super(id);
	}

	/**
	 * Instantiates a new annotation.
	 *
	 * @param businessClassElement the business class element
	 */
	@SuppressWarnings("unchecked")
	public Annotation(Element businessClassElement) {
		initBasicAttributes(businessClassElement);
		String content = businessClassElement.elementTextTrim("Content");
		setContent(content);
	}

	/**
	 * @param bcsElement
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.AbstractModelElement#generateXmlElement(org.dom4j.Element)
	 */
	public Element generateXmlElement(Element bcsElement) {
		Element bcElement = bcsElement.addElement("Annotation");
		generateBasicAttributes(bcElement);
		bcElement.addElement("Content").addText(
				StringUtil.convertNull2EmptyStr(getContent()));
		
		return bcElement;
	}

	/**
	 * Gets the belong package.
	 *
	 * @author mqfdy
	 * @return the belong package
	 * @Date 2018-09-03 09:00
	 */
	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	/**
	 * Sets the belong package.
	 *
	 * @author mqfdy
	 * @param belongPackage
	 *            the new belong package
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}


	/**
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.IModelElement#getParent()
	 */
	public AbstractModelElement getParent() {
		return this.belongPackage.getAnnotationPackage();
	}


	/**
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.AbstractModelElement#getFullName()
	 */
	public String getFullName() {
		if (this.getParent() != null) {
			return this.getParent().getFullName() + ".annotation." + this.getName();
		} else {
			return this.getName();
		}

	}

	/**
	 * @param dest
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.AbstractModelElement#copy(com.mqfdy.code.model.AbstractModelElement)
	 */
	protected void copy(AbstractModelElement dest) {
		super.copy(dest);
		Annotation destBc = ((Annotation) dest);
		destBc.setContent(getContent());
		destBc.setBelongPackage(getBelongPackage());
	}

	/**
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.AbstractModelElement#clone()
	 */
	public Annotation clone() {
		Annotation bc = new Annotation();
		copy(bc);
		return bc;
	}

	/**
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.AbstractModelElement#cloneChangeId()
	 */
	public Annotation cloneChangeId() {
		Annotation bc = new Annotation();
		copyChangeId(bc);
		return bc;
	}

	/**
	 * @param dest
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.AbstractModelElement#copyChangeId(com.mqfdy.code.model.AbstractModelElement)
	 */
	protected void copyChangeId(AbstractModelElement dest) {
		super.copyChangeId(dest);
		Annotation destBc = ((Annotation) dest);
		destBc.setContent(getContent());
		destBc.setBelongPackage(getBelongPackage());

	}

	/**
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.mqfdy.code.model.IModelElement#getChildren()
	 */
	public List<?> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the content.
	 *
	 * @author mqfdy
	 * @return the content
	 * @Date 2018-09-03 09:00
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @author mqfdy
	 * @param content
	 *            the new content
	 * @Date 2018-09-03 09:00
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
