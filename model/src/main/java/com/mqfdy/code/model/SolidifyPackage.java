package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 固化包(不能下挂包对象).
 *
 * @author mqfdy
 */
public class SolidifyPackage extends AbstractPackage {

	/** The Constant SOLIDIFY_PACKAGE_BUSINESSCLASS. */
	public static final String SOLIDIFY_PACKAGE_BUSINESSCLASS = "businessClass";
	
	/** The Constant SOLIDIFY_PACKAGE_BUSINESSCLASS_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_BUSINESSCLASS_DISPLAYNAME = "业务实体";

	/** The Constant SOLIDIFY_PACKAGE_ANNOTATION. */
	public static final String SOLIDIFY_PACKAGE_ANNOTATION = "annotation";
	
	/** The Constant SOLIDIFY_PACKAGE_ANNOTATION_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_ANNOTATION_DISPLAYNAME = "注释";

	/** The Constant SOLIDIFY_PACKAGE_LINK. */
	public static final String SOLIDIFY_PACKAGE_LINK = "link";
	
	/** The Constant SOLIDIFY_PACKAGE_LINK_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_LINK_DISPLAYNAME = "连线";

	/** The Constant SOLIDIFY_PACKAGE_ASSOCIATION. */
	public static final String SOLIDIFY_PACKAGE_ASSOCIATION = "association";
	
	/** The Constant SOLIDIFY_PACKAGE_ASSOCIATION_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_ASSOCIATION_DISPLAYNAME = "关联关系";

	/** The Constant SOLIDIFY_PACKAGE_INHERITANCE. */
	public static final String SOLIDIFY_PACKAGE_INHERITANCE = "inheritance";
	
	/** The Constant SOLIDIFY_PACKAGE_INHERITANCE_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_INHERITANCE_DISPLAYNAME = "继承关系";

	/** The Constant SOLIDIFY_PACKAGE_STATUS. */
	public static final String SOLIDIFY_PACKAGE_STATUS = "status";
	
	/** The Constant SOLIDIFY_PACKAGE_STATUS_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_STATUS_DISPLAYNAME = "状态";

	/** The Constant SOLIDIFY_PACKAGE_PROPERTY. */
	public static final String SOLIDIFY_PACKAGE_PROPERTY = "property";
	
	/** The Constant SOLIDIFY_PACKAGE_PROPERTY_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_PROPERTY_DISPLAYNAME = "属性";

	/** The Constant SOLIDIFY_PACKAGE_OPERATION. */
	public static final String SOLIDIFY_PACKAGE_OPERATION = "operation";
	
	/** The Constant SOLIDIFY_PACKAGE_OPERATION_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_OPERATION_DISPLAYNAME = "操作";

	/** The Constant SOLIDIFY_PACKAGE_PERMISSION. */
	public static final String SOLIDIFY_PACKAGE_PERMISSION = "permission";
	
	/** The Constant SOLIDIFY_PACKAGE_PERMISSION_DISPLAYNAME. */
	public static final String SOLIDIFY_PACKAGE_PERMISSION_DISPLAYNAME = "权限";

	/** The parent. */
	private AbstractModelElement parent;

	/**
	 * Instantiates a new solidify package.
	 */
	public SolidifyPackage() {
		super();
	}

	/**
	 * Instantiates a new solidify package.
	 *
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 */
	public SolidifyPackage(String name, String displayName) {
		super(name, displayName);
	}

	/**
	 * Instantiates a new solidify package.
	 *
	 * @param parent
	 *            the parent
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 */
	public SolidifyPackage(AbstractModelElement parent, String name,
			String displayName) {
		super(name, displayName);
		setParent(parent);
	}

	/**
	 * @return
	 */
	public int getPriority() {
		return IModelElement.PRIORITY_SOLIDIFYPACKAGE;
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
		List<AbstractModelElement> children = new ArrayList<AbstractModelElement>();
		if (this.parent instanceof ModelPackage) {
			AbstractModelElement bom;
			bom = getParent();
			while (bom != null && !(bom instanceof BusinessObjectModel)) {
				bom = bom.getParent();
			}
			if (bom != null) {
				BusinessObjectModel businessObjectModel = (BusinessObjectModel) bom;
				ModelPackage modelPackage = (ModelPackage) getParent();
				if (SOLIDIFY_PACKAGE_BUSINESSCLASS.equals(this.name)) {
					for (int i = 0; i < businessObjectModel
							.getBusinessClasses().size(); i++) {
						BusinessClass bc = businessObjectModel
								.getBusinessClasses().get(i);
						if (bc.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(bc);
						}
					}

					for (int i = 0; i < businessObjectModel.getDTOs().size(); i++) {
						DataTransferObject dto = businessObjectModel.getDTOs()
								.get(i);
						if (dto.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(dto);
						}
					}

					for (int i = 0; i < businessObjectModel.getEnumerations()
							.size(); i++) {
						Enumeration enumeration = businessObjectModel
								.getEnumerations().get(i);
						if (enumeration.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(enumeration);
						}
					}

					for (int i = 0; i < businessObjectModel
							.getReferenceObjects().size(); i++) {
						ReferenceObject ro = businessObjectModel
								.getReferenceObjects().get(i);
						if (ro.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(ro);
						}
					}
				} else if (SOLIDIFY_PACKAGE_ANNOTATION.equals(this.name)) {
					for (int i = 0; i < businessObjectModel
							.getAnnotations().size(); i++) {
						Annotation bc = businessObjectModel
								.getAnnotations().get(i);
						if (bc.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(bc);
						}
					}
				} else if (SOLIDIFY_PACKAGE_ASSOCIATION.equals(this.name)) {
					for (int i = 0; i < businessObjectModel.getAssociations()
							.size(); i++) {
						Association association = businessObjectModel
								.getAssociations().get(i);
						if (association.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(association);
						}
					}
				} else if (SOLIDIFY_PACKAGE_LINK.equals(this.name)) {
					for (int i = 0; i < businessObjectModel.getLinkAnnotations()
							.size(); i++) {
						LinkAnnotation association = businessObjectModel
								.getLinkAnnotations().get(i);
						if (association.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(association);
						}
					}
				} else if (SOLIDIFY_PACKAGE_INHERITANCE.equals(this.name)) {
					for (int i = 0; i < businessObjectModel.getInheritances()
							.size(); i++) {
						Inheritance inheritance = businessObjectModel
								.getInheritances().get(i);
						if (inheritance.getBelongPackage().getId()
								.equals(modelPackage.getId())) {
							children.add(inheritance);
						}
					}
				}
			}
		} else if (this.parent instanceof BusinessClass) {
			BusinessClass bc = (BusinessClass) parent;
			if (SOLIDIFY_PACKAGE_PROPERTY.equals(this.name)) {
				children.addAll(bc.getProperties());
			} else if (SOLIDIFY_PACKAGE_STATUS.equals(this.name)) {
				children.addAll(bc.getStatuses());
			}

			else if (SOLIDIFY_PACKAGE_OPERATION.equals(this.name)) {
				children.addAll(bc.getOperations());
			}

			else if (SOLIDIFY_PACKAGE_PERMISSION.equals(this.name)) {
				children.addAll(bc.getPermissions());
			}
		}
		return children;
	}

	/**
	 * @return
	 */
	public String getFullName() {
		return this.getParent().getFullName();
	}
}
