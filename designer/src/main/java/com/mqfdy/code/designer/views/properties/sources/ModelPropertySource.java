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

/**
 * 视图资源抽象类
 * 
 * @author mqfdy
 * 
 */

public abstract class ModelPropertySource implements IPropertySource,
		IBusinessModelPropertyNames {

	protected IPropertyDescriptor[] descriptions = new IPropertyDescriptor[0];

	private Map<Object, ModelProperty> propertiesModel;

	public static final String NULL_PROPERTY = "";

	private static final String[] LIST_BOOLEAN = { "true", "false" };

	/**
	 * HandleNotifications is used to define some kind of "critical sections"
	 * where we don't want to handle notifications. For example when we change
	 * the name of an UML2 object so we don't need to handle the corresponding
	 * notification. We simply ignore it.
	 */
	private boolean handleNotifications = true;

	private transient PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

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

	protected void installModelProperty() {
		// addStringModelProperty(UUID, CATEGORY_COMMON, null,
		// true).setAdvanced(true);
		// addWidthHeightModelProperty(X, CATEGORY_SYSTEM, "");
		// addWidthHeightModelProperty(Y, CATEGORY_SYSTEM, "");

		// addStringModelProperty(PROPERTY_ID, CATEGORY_1, "",true,"01");
		// addStringModelProperty(AUTOINIT, "111", "");
		// addStringModelProperty(ALIAS, "111", "");
	}

	// 生成唯一的id
	public synchronized static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Determines if notification are handled at the moment.
	 * 
	 * @return true when notifications are handled
	 */
	protected boolean isHandleNotifications() {
		return handleNotifications;
	}

	/**
	 * When called all notification will be ignored from now on.
	 */
	protected void ignoreNotifications() {
		handleNotifications = false;
	}

	/**
	 * When called all notification will be handled again.
	 */
	protected void handleNotifications() {
		handleNotifications = true;
	}

	/**
	 * Attach a non-null PropertyChangeListener to this object.
	 * 
	 * @param propertyChangeListener
	 *            a non-null PropertyChangeListener instance
	 * @throws IllegalArgumentException
	 *             if the parameter is null
	 */
	public synchronized void addPropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener == null) {
			throw new IllegalArgumentException();
		}
		listeners.addPropertyChangeListener(propertyChangeListener);
	}

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
	 * @param property
	 *            the programmatic name of the property that changed
	 * @param oldValue
	 *            the old value of this property
	 * @param newValue
	 *            the new value of this property
	 */
	protected void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (listeners.hasListeners(property)) {
			listeners.firePropertyChange(property, oldValue, newValue);
		}
	}

	public Object getEditableValue() {
		return this;
	}

	/**
	 * Normally subclasses shouldn't override this, but implement
	 * {@link #initializeDescriptors()} to define which property ids it
	 * supports.
	 * 
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
	 * @see eclipse.ui.views.propertiesModel.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public abstract Object getPropertyValue(Object propertyId);

	/**
	 * Children should override this.
	 * 
	 * @see eclipse.ui.views.propertiesModel.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public abstract boolean isPropertySet(Object propertyId);

	/**
	 * Remove a PropertyChangeListener from this component.
	 * 
	 * @param propertyChangeListener
	 *            a PropertyChangeListener instance
	 */
	public synchronized void removePropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener != null) {
			listeners.removePropertyChangeListener(propertyChangeListener);
		}
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		if (listener != null)
			listeners.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Children should override this.
	 * 
	 * @see org.eclipse.ui.views.propertiesModel.IPropertySource#resetPropertyValue(java.lang.Object)
	 */
	public abstract void resetPropertyValue(Object propertyId);

	/**
	 * Children should override this.
	 * 
	 * @see eclipse.ui.views.propertiesModel.IPropertySource#setPropertyValue(java.lang.Object,
	 *      java.lang.Object)
	 */
	public abstract void setPropertyValue(Object propertyId, Object value);

	/**
	 * Subclasses should override this and initialize the {@link #descriptions}
	 * here. Each class has to initialize its own propertyIds (so each one that
	 * es declared in this class). To initialize the propertyIds of super
	 * classes, the super class' method should be called before.
	 */
	protected abstract void initializeDescriptors();

	// Property Methods
	protected ModelProperty addButtonModelProperty(String propertyName,
			String category, String defaultValue, String dialogType,
			AbstractModelElement node, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new ButtonPropertyDescriptor(propertyName, propertyName,
						defaultValue, dialogType, description, node), category));
	}

	protected ModelProperty addStringModelProperty(String propertyName,
			String category, String defaultValue, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new StringPropertyDescriptor(propertyName, propertyName,
						defaultValue, description), category));
	}

	/**
	 * 添加字符类型属性
	 * 
	 * @param propertyName
	 * @param category
	 * @param defaultValue
	 * @param readOnly
	 *            是否只读
	 * @param description
	 * @return
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

	protected ModelProperty addStringModelProperty(String propertyName,
			String category, String description) {
		return addStringModelProperty(propertyName, category, null, description);
	}

	protected ModelProperty addBooleanModelProperty(String propertyName,
			String category, boolean defaultValue, String description) {
		return addListModelProperty(propertyName, category, LIST_BOOLEAN,
				String.valueOf(defaultValue), description);
	}

	protected ModelProperty addBooleanModelProperty(String propertyName,
			String category) {
		return addListReadOnlyModelProperty(propertyName, category,
				LIST_BOOLEAN, "");
	}

	protected ModelProperty addNumberModelProperty(String propertyName,
			String category, Integer defaultValue, String description) {
		return addModelProperty(propertyName,
				new ModelProperty(propertyName, new StringPropertyDescriptor(
						propertyName, propertyName, defaultValue == null ? ""
								: String.valueOf(defaultValue), description),
						category));
	}

	protected ModelProperty addNumberModelProperty(String propertyName,
			String category, String description) {
		return addNumberModelProperty(propertyName, category, null, description);
	}

	protected ModelProperty addIntModelProperty(String propertyName,
			String category, int defaultValue, String description) {
		return addNumberModelProperty(propertyName, category, defaultValue,
				description);
	}

	protected ModelProperty addIntModelProperty(String propertyName,
			String category) {
		return addNumberModelProperty(propertyName, category, null);
	}

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

	protected ModelProperty addListModelProperty(String propertyName,
			String category, String[] list, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new StringListPropertyDescriptor(propertyName, propertyName,
						list, description), category));
	}

	protected ModelProperty addListReadOnlyModelProperty(String propertyName,
			String category, String[] list, String description) {
		return addModelProperty(propertyName, new ModelProperty(propertyName,
				new StringListReadOnlyPropertyDescriptor(propertyName,
						propertyName, list, description), category));
	}

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

	protected void removePropertySheet(String propertyName) {
		ModelProperty mp = getModelProperty(propertyName);
		mp.setDescriptor(null);
	}

	protected void setAdvancedToModel(String propertyName, boolean isAdvanced) {
		ModelProperty mp = getModelProperty(propertyName);
		mp.setAdvanced(isAdvanced);
	}

	protected ModelProperty addModelProperty(Object id, ModelProperty property) {
		propertiesModel.put(id, property);
		return property;
	}

	public ModelProperty getModelProperty(Object id) {
		if (propertiesModel == null) {
			return null;
		}
		return propertiesModel.get(id);
	}

	public Map<Object, ModelProperty> getModelProperties() {
		return propertiesModel;
	}

}
