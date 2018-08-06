package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.resource.validator.ValidatorUtil;


/**
 * 图PropertyPropertySource
 * 
 * @author mqfdy
 * 
 */
public class DiagramPropertySource extends ModelPropertySource {
	public Diagram diagram;

	// private BusinessModelManager manager =
	// BusinessModelUtil.getEditorBusinessModelManager();
	public DiagramPropertySource(AbstractModelElement property) {
		super();
		this.diagram = (Diagram) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	protected void initializeDescriptors() {
	}

	protected void installModelProperty() {
		addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "02");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_NAME,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "03");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "04");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "05");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"","Diagram","06");
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		/*
		 * if (propertyId.equals(NAME_PROP)) { return getName(); } else if
		 * (propertyId.equals(DETAIL_PROP)) { return Integer.valueOf(details);
		 * }else{return getID();}
		 */
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return diagram.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return diagram.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return diagram.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return diagram.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return diagram.getDisplayType();
		} else
			return "";
	}

	public void setPropertyValue(Object propertyId, Object value) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException(propertyId.toString());
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			diagram.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiName(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				int i = 0;
				if (diagram.getParent() instanceof ModelPackage) {
					for (AbstractModelElement dia : ((ModelPackage) diagram
							.getParent()).getChildren()) {
						if (dia instanceof Diagram) {
							if (dia.getName().equals(diagram.getName())
									&& !dia.getId().equals(diagram.getId()))
								i++;
						}
					}
				}
				if (i < 1) {
					diagram.setName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				diagram.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// diagram.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				diagram.setDisplayName(value.toString());
			}
		} else {
			throw new IllegalArgumentException(propertyId.toString());
		}
	}

	public boolean isPropertySet(Object propertyId) {
		return true;
	}

	public void resetPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId.equals("bbb")) {
			diagram.setId("11111");
		} /*
		 * else if (propertyId.equals(DETAIL_PROP)) { // details = 0; }
		 */else {
			throw new IllegalArgumentException();
		}
	}

	public Object getEditableValue() {
		return this;
	}
}
