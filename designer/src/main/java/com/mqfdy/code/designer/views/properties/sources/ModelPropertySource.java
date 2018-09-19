package com.mqfdy.code.designer.views.properties.sources;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.mqfdy.code.designer.views.properties.descriptor.ButtonPropertyDescriptor;
import com.mqfdy.code.designer.views.properties.descriptor.ReadOnlyPropertyDescriptor;
import com.mqfdy.code.designer.views.properties.descriptor.StringListPropertyDescriptor;
import com.mqfdy.code.designer.views.properties.descriptor.StringListReadOnlyPropertyDescriptor;
import com.mqfdy.code.designer.views.properties.descriptor.StringPropertyDescriptor;
import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 视图资源抽象类.
 *
 * @author mqfdy
 */

public abstract class ModelPropertySource implements IPropertySource,
		IBusinessModelPropertyNames {

	/** The descriptions. */
	protected IPropertyDescriptor[] descriptions = new IPropertyDescriptor[0];

	/** The properties model. */
	private Map<Object, ModelProperty> propertiesModel;

	/** The Constant NULL_PROPERTY. */
	public static final String NULL_PROPERTY = "";

	/** The Constant LIST_BOOLEAN. */
	private static final String[] LIST_BOOLEAN = { "true", "false" };

	/**
	 * HandleNotifications is used to define some kind of "critical sections"
	 * where we don't want to handle notifications. For example when we change
	 * the name of an UML2 object so we don't need to handle the corresponding
	 * notification. We simply ignore it.
	 */
	private boolean handleNotifications = true;

	/** The listeners. */
	private transient PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

	/** The begin index. */
	protected int beginIndex;

	/**
	 * Default constructor for a ModelElement.
	 */
	public ModelPropertySource() {
		initializeDescriptors();
		// comboValueMap.put(IConstants.TRUE_STR, "1");
		// comboValueMap.put(IConstants.FALSE_STR, "0");
		propertiesModel = PropertyManager.getProperties(getClass().getName());

		if (propertiesModel == null) {
			propertiesModel = new HashMap<Object, ModelProperty>();
			// installModelProperty();
			PropertyManager
					.putProperties(getClass().getName(), propertiesModel);
		}
	}

	// 属性

	/**
	 * Install model property.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void installModelProperty() {
		// addStringModelProperty(UUID, CATEGORY_COMMON, null,
		// true).setAdvanced(true);
		// addWidthHeightModelProperty(X, CATEGORY_SYSTEM, "");
		// addWidthHeightModelProperty(Y, CATEGORY_SYSTEM, "");

		// addStringModelProperty(PROPERTY_ID, CATEGORY_1, "",true,"01");
		// addStringModelProperty(AUTOINIT, "111", "");
		// addStringModelProperty(ALIAS, "111", "");
	}

	/**
	 * Generate UUID.
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	// 生成唯一的id
	public synchronized static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Determines if notification are handled at the moment.
	 *
	 * @author mqfdy
	 * @return true when notifications are handled
	 * @Date 2018-09-03 09:00
	 */
	protected boolean isHandleNotifications() {
		return handleNotifications;
	}

	/**
	 * When called all notification will be ignored from now on.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void ignoreNotifications() {
		handleNotifications = false;
	}

	/**
	 * When called all notification will be handled again.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void handleNotifications() {
		handleNotifications = true;
	}

	/**
	 * Attach a non-null PropertyChangeListener to this object.
	 *
	 * @author mqfdy
	 * @param propertyChangeListener
	 *            a non-null PropertyChangeListener instance
	 * @throws IllegalArgumentException
	 *             if the parameter is null
	 * @Date 2018-09-03 09:00
	 */
	public synchronized void addPropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener == null) {
			throw new IllegalArgumentException();
		}
		listeners.addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * Adds the property change listener.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Report a property change to registered listeners (for example edit
	 * parts).
	 *
	 * @author mqfdy
	 * @param property
	 *            the programmatic name of the property that changed
	 * @param oldValue
	 *            the old value of this property
	 * @param newValue
	 *            the new value of this property
	 * @Date 2018-09-03 09:00
	 */
	protected void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (listeners.hasListeners(property)) {
			listeners.firePropertyChange(property, oldValue, newValue);
		}
	}

	/**
	 * @return
	 */
	public Object getEditableValue() {
		return this;
	}

	/**
	 * Normally subclasses shouldn't override this, but implement
	 * {@link #initializeDescriptors()} to define which property ids it
	 * supports.
	 *
	 * @return the property descriptors
	 * @see eclipse.ui.views.propertiesModel.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> descriptions = new ArrayList<IPropertyDescriptor>();

		for (Iterator<ModelProperty> ite = this.propertiesModel.values()
				.iterator(); ite.hasNext();) {
			ModelProperty property = ite.next();
			IPropertyDescriptor description = property.getDescriptor();
			if (description != null) {
				descriptions.add(description);
			}
		}

		return descriptions
				.toArray(new IPropertyDescriptor[descriptions.size()]);
		// return descriptions;
	}

	/**
	 * Children should override this.
	 *
	 * @param propertyId
	 *            the property id
	 * @return the property value
	 * @see eclipse.ui.views.propertiesModel.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public abstract Object getPropertyValue(Object propertyId);

	/**
	 * Children should override this.
	 *
	 * @param propertyId
	 *            the property id
	 * @return true, if is property set
	 * @see eclipse.ui.views.propertiesModel.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public abstract boolean isPropertySet(Object propertyId);

	/**
	 * Remove a PropertyChangeListener from this component.
	 *
	 * @author mqfdy
	 * @param propertyChangeListener
	 *            a PropertyChangeListener instance
	 * @Date 2018-09-03 09:00
	 */
	public synchronized void removePropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener != null) {
			listeners.removePropertyChangeListener(propertyChangeListener);
		}
	}

	/**
	 * Removes the property change listener.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		if (listener != null)
			listeners.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Children should override this.
	 *
	 * @param propertyId
	 *            the property id
	 * @see org.eclipse.ui.views.propertiesModel.IPropertySource#resetPropertyValue(java.lang.Object)
	 */
	public abstract void resetPropertyValue(Object propertyId);

	/**
	 * Children should override this.
	 *
	 * @param propertyId
	 *            the property id
	 * @param value
	 *            the value
	 * @see eclipse.ui.views.propertiesModel.IPropertySource#setPropertyValue(java.lang.Object,
	 *      java.lang.Object)
	 */
	public abstract void setPropertyValue(Object propertyId, Object value);

	/**
	 * Subclasses should override this and initialize the {@link #descriptions}
	 * here. Each class has to initialize its own propertyIds (so each one that
	 * es declared in this class). To initialize the propertyIds of super
	 * classes, the super class' method should be called before.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void initializeDescriptors();

	/**
	 * Adds the button model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param defaultValue
	 *            the default value
	 * @param dialogType
	 *            the dialog type
	 * @param node
	 *            the node
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	// Property Methods
	protected ModelProperty addButtonModelProperty(String propertyName,
			String category, String defaultValue, String dialogType,
			AbstractModelElement node, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new ButtonPropertyDescriptor(propertyName, propertyName,
						defaultValue, dialogType, description, node), category));
	}

	/**
	 * Adds the string model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addStringModelProperty(String propertyName,
			String category, String defaultValue, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new StringPropertyDescriptor(propertyName, propertyName,
						defaultValue, description), category));
	}

	/**
	 * 添加字符类型属性.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param defaultValue
	 *            the default value
	 * @param readOnly
	 *            是否只读
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addStringModelProperty(String propertyName,
			String category, String defaultValue, boolean readOnly,
			String description) {

		org.eclipse.ui.views.properties.PropertyDescriptor pd;
		if (readOnly)
			pd = new ReadOnlyPropertyDescriptor(propertyName, propertyName,
					defaultValue, description);
		else
			pd = new StringPropertyDescriptor(propertyName, propertyName,
					defaultValue, description);

		return addModelProperty(propertyName, new ModelProperty(propertyName,
				pd, category));
	}

	/**
	 * Adds the string model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addStringModelProperty(String propertyName,
			String category, String description) {
		return addStringModelProperty(propertyName, category, null, description);
	}

	/**
	 * Adds the boolean model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addBooleanModelProperty(String propertyName,
			String category, boolean defaultValue, String description) {
		return addListModelProperty(propertyName, category, LIST_BOOLEAN,
				String.valueOf(defaultValue), description);
	}

	/**
	 * Adds the boolean model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addBooleanModelProperty(String propertyName,
			String category) {
		return addListReadOnlyModelProperty(propertyName, category,
				LIST_BOOLEAN, "");
	}

	/**
	 * Adds the number model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addNumberModelProperty(String propertyName,
			String category, Integer defaultValue, String description) {
		return addModelProperty(propertyName,
				new ModelProperty(propertyName, new StringPropertyDescriptor(
						propertyName, propertyName, defaultValue == null ? ""
								: String.valueOf(defaultValue), description),
						category));
	}

	/**
	 * Adds the number model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addNumberModelProperty(String propertyName,
			String category, String description) {
		return addNumberModelProperty(propertyName, category, null, description);
	}

	/**
	 * Adds the int model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addIntModelProperty(String propertyName,
			String category, int defaultValue, String description) {
		return addNumberModelProperty(propertyName, category, defaultValue,
				description);
	}

	/**
	 * Adds the int model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addIntModelProperty(String propertyName,
			String category) {
		return addNumberModelProperty(propertyName, category, null);
	}

	/**
	 * Adds the double model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addDoubleModelProperty(String propertyName,
			String category, Double defaultValue, String description) {
		if (defaultValue == null) {
			return addModelProperty(propertyName, new ModelProperty(
					propertyName, new StringPropertyDescriptor(propertyName,
							propertyName, null, description), category));

		} else {
			return addModelProperty(propertyName, new ModelProperty(
					propertyName, new StringPropertyDescriptor(propertyName,
							propertyName, String.valueOf(defaultValue),
							description), category));
		}
	}

	/**
	 * Adds the list model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param list
	 *            the list
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addListModelProperty(String propertyName,
			String category, String[] list, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new StringListPropertyDescriptor(propertyName, propertyName,
						list, description), category));
	}

	/**
	 * Adds the list read only model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param list
	 *            the list
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addListReadOnlyModelProperty(String propertyName,
			String category, String[] list, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new StringListReadOnlyPropertyDescriptor(propertyName,
						propertyName, list, description), category));
	}

	/**
	 * Adds the list model property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param category
	 *            the category
	 * @param list
	 *            the list
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addListModelProperty(String propertyName,
			String category, String[] list, String defaultValue,
			String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new StringListPropertyDescriptor(propertyName, propertyName,
						list, defaultValue, description), category));
	}

	// protected ModelProperty addListModelProperty(String propertyName, String
	// category, String description) {
	// return addListModelProperty(propertyName, category, null,description);
	// }

	/**
	 * Removes the property sheet.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @Date 2018-09-03 09:00
	 */
	protected void removePropertySheet(String propertyName) {
		ModelProperty mp = getModelProperty(propertyName);
		mp.setDescriptor(null);
	}

	/**
	 * Sets the advanced to model.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param isAdvanced
	 *            the is advanced
	 * @Date 2018-09-03 09:00
	 */
	protected void setAdvancedToModel(String propertyName, boolean isAdvanced) {
		ModelProperty mp = getModelProperty(propertyName);
		mp.setAdvanced(isAdvanced);
	}

	/**
	 * Adds the model property.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @param property
	 *            the property
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	protected ModelProperty addModelProperty(Object id, ModelProperty property) {
		propertiesModel.put(id, property);
		return property;
	}

	/**
	 * Gets the model property.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the model property
	 * @Date 2018-09-03 09:00
	 */
	public ModelProperty getModelProperty(Object id) {
		if (propertiesModel == null) {
			return null;
		}
		return propertiesModel.get(id);
	}

	/**
	 * Gets the model properties.
	 *
	 * @author mqfdy
	 * @return the model properties
	 * @Date 2018-09-03 09:00
	 */
	public Map<Object, ModelProperty> getModelProperties() {
		return propertiesModel;
	}

}
