package com.mqfdy.code.designer.views.properties;

/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Gunnar Wagenknecht - fix for bug 21756 [PropertiesView] property view sorting
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySheetEntryListener;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.views.properties.sources.BusinessOperationPropertySource;
import com.mqfdy.code.designer.views.properties.sources.BusinessPropertySource;
import com.mqfdy.code.designer.views.properties.sources.DTOPropertySource;
import com.mqfdy.code.designer.views.properties.sources.PKPropertySource;
import com.mqfdy.code.designer.views.properties.sources.PersistencePropertySource;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * <code>PropertySheetEntry</code> is an implementation of
 * <code>IPropertySheetEntry</code> which uses <code>IPropertySource</code> and
 * <code>IPropertyDescriptor</code> to interact with domain model objects.
 * <p>
 * Every property sheet entry has a single descriptor (except the root entry
 * which has none). This descriptor determines what property of its objects it
 * will display/edit.
 * </p>
 * <p>
 * Entries do not listen for changes in their objects. Since there is no
 * restriction on properties being independent, a change in one property may
 * affect other properties. The value of a parent's property may also change. As
 * a result we are forced to refresh the entire entry tree when a property
 * changes value.
 * </p>
 * 
 * @since 3.0 (was previously internal)
 */
public class BusinessPropertySheetEntry extends EventManager implements
		IPropertySheetEntry {
	/**
	 * The values we are displaying/editing. These objects repesent the value of
	 * one of the properties of the values of our parent entry. Except for the
	 * root entry where they represent the input (selected) objects.
	 */
	private Object[] values = new Object[0];

	/**
	 * The property sources for the values we are displaying/editing.
	 */
	private Map sources = new HashMap(0);

	/**
	 * The value of this entry is defined as the the first object in its value
	 * array or, if that object is an <code>IPropertySource</code>, the value it
	 * returns when sent <code>getEditableValue</code>.
	 */
	private Object editValue;

	/** The parent. */
	private BusinessPropertySheetEntry parent;

	/** The property source provider. */
	private IPropertySourceProvider propertySourceProvider;

	/** The descriptor. */
	private IPropertyDescriptor descriptor;

	/** The editor. */
	private CellEditor editor;

	/** The error text. */
	private String errorText;

	/** The child entries. */
	private BusinessPropertySheetEntry[] childEntries = null;

	/**
	 * Create the CellEditorListener for this entry. It listens for value
	 * changes in the CellEditor, and cancel and finish requests.
	 */
	private ICellEditorListener cellEditorListener = new ICellEditorListener() {
		public void editorValueChanged(boolean oldValidState,
				boolean newValidState) {
			if (!newValidState) {
				// currently not valid so show an error message
				setErrorText(editor.getErrorMessage());
			} else {
				// currently valid
				setErrorText(null);
			}
		}

		public void cancelEditor() {
			setErrorText(null);
		}

		public void applyEditorValue() {
			BusinessPropertySheetEntry.this.applyEditorValue();
		}
	};
	
	/** The business model manager. */
	private BusinessModelManager businessModelManager;

	/**
	 * Instantiates a new business property sheet entry.
	 *
	 * @param businessModelManager
	 *            the business model manager
	 */
	public BusinessPropertySheetEntry(BusinessModelManager businessModelManager) {
		super();
		this.businessModelManager = businessModelManager;
	}

	/**
	 * Adds the property sheet entry listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public void addPropertySheetEntryListener(
			IPropertySheetEntryListener listener) {
		addListenerObject(listener);
	}

	/**
	 * 
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public void applyEditorValue() {
		if (editor == null) {
			return;
		}

		// Check if editor has a valid value
		if (!editor.isValueValid()) {
			setErrorText(editor.getErrorMessage());
			return;
		}

		setErrorText(null);

		// See if the value changed and if so update
		Object newValue = editor.getValue();
		boolean changed = false;
		if (values.length > 1) {
			changed = true;
		} else if (editValue == null) {
			if (newValue != null) {
				changed = true;
			}
		} else if (!editValue.equals(newValue)) {
			changed = true;
		}

		// Set the editor value
		if (changed) {
			setValue(newValue);
		}
	}

	/**
	 * Return the unsorted intersection of all the
	 * <code>IPropertyDescriptor</code>s for the objects.
	 *
	 * @author mqfdy
	 * @return List
	 * @Date 2018-09-03 09:00
	 */
	private List computeMergedPropertyDescriptors() {
		if (values.length == 0) {
			return new ArrayList(0);
		}

		IPropertySource firstSource = getPropertySource(values[0]);
		if (firstSource == null) {
			return new ArrayList(0);
		}

		if (values.length == 1) {
			return Arrays.asList(firstSource.getPropertyDescriptors());
		}

		// get all descriptors from each object
		Map[] propertyDescriptorMaps = new Map[values.length];
		for (int i = 0; i < values.length; i++) {
			Object object = values[i];
			IPropertySource source = getPropertySource(object);
			if (source == null) {
				// if one of the selected items is not a property source
				// then we show no properties
				return new ArrayList(0);
			}
			// get the property descriptors keyed by id
			propertyDescriptorMaps[i] = computePropertyDescriptorsFor(source);
		}

		// intersect
		Map intersection = propertyDescriptorMaps[0];
		for (int i = 1; i < propertyDescriptorMaps.length; i++) {
			// get the current ids
			Object[] ids = intersection.keySet().toArray();
			for (int j = 0; j < ids.length; j++) {
				Object object = propertyDescriptorMaps[i].get(ids[j]);
				if (object == null ||
				// see if the descriptors (which have the same id) are
				// compatible
						!((IPropertyDescriptor) intersection.get(ids[j]))
								.isCompatibleWith((IPropertyDescriptor) object)) {
					intersection.remove(ids[j]);
				}
			}
		}

		// sorting is handled in the PropertySheetViewer, return unsorted (in
		// the original order)
		ArrayList result = new ArrayList(intersection.size());
		IPropertyDescriptor[] firstDescs = firstSource.getPropertyDescriptors();
		for (int i = 0; i < firstDescs.length; i++) {
			IPropertyDescriptor desc = firstDescs[i];
			if (intersection.containsKey(desc.getId())) {
				result.add(desc);
			}
		}
		return result;
	}

	/**
	 * Returns an map of property descritptors (keyed on id) for the given
	 * property source.
	 *
	 * @author mqfdy
	 * @param source
	 *            a property source for which to obtain descriptors
	 * @return a table of decriptors keyed on their id
	 * @Date 2018-09-03 09:00
	 */
	private Map computePropertyDescriptorsFor(IPropertySource source) {
		IPropertyDescriptor[] descriptors = source.getPropertyDescriptors();
		Map result = new HashMap(descriptors.length * 2 + 1);
		for (int i = 0; i < descriptors.length; i++) {
			result.put(descriptors[i].getId(), descriptors[i]);
		}
		return result;
	}

	/**
	 * Create our child entries.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createChildEntries() {
		// get the current descriptors
		List descriptors = computeMergedPropertyDescriptors();

		// rebuild child entries using old when possible
		BusinessPropertySheetEntry[] newEntries = new BusinessPropertySheetEntry[descriptors
				.size()];
		for (int i = 0; i < descriptors.size(); i++) {
			IPropertyDescriptor d = (IPropertyDescriptor) descriptors.get(i);
			// create new entry
			BusinessPropertySheetEntry entry = createChildEntry();
			entry.setDescriptor(d);
			entry.setParent(this);
			entry.setPropertySourceProvider(propertySourceProvider);
			entry.refreshValues();
			newEntries[i] = entry;
		}
		// only assign if successful
		childEntries = newEntries;
	}

	/**
	 * Factory method to create a new child <code>PropertySheetEntry</code>
	 * instance.
	 * <p>
	 * Subclasses may overwrite to create new instances of their own class.
	 * </p>
	 * 
	 * @return a new <code>PropertySheetEntry</code> instance for the descriptor
	 *         passed in
	 * @since 3.1
	 */
	protected BusinessPropertySheetEntry createChildEntry() {
		return new BusinessPropertySheetEntry(businessModelManager);
	}

	/**
	 * 
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public void dispose() {
		if (editor != null) {
			editor.dispose();
			editor = null;
		}
		// recursive call to dispose children
		BusinessPropertySheetEntry[] entriesToDispose = childEntries;
		childEntries = null;
		if (entriesToDispose != null) {
			for (int i = 0; i < entriesToDispose.length; i++) {
				// an error in a property source may cause refreshChildEntries
				// to fail. Since the Workbench handles such errors we
				// can be left in a state where a child entry is null.
				if (entriesToDispose[i] != null) {
					entriesToDispose[i].dispose();
				}
			}
		}
	}

	/**
	 * The child entries of this entry have changed (children added or removed).
	 * Notify all listeners of the change.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fireChildEntriesChanged() {
		Object[] array = getListeners();
		for (int i = 0; i < array.length; i++) {
			IPropertySheetEntryListener listener = (IPropertySheetEntryListener) array[i];
			listener.childEntriesChanged(this);
		}
	}

	/**
	 * The error message of this entry has changed. Notify all listeners of the
	 * change.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fireErrorMessageChanged() {
		Object[] array = getListeners();
		for (int i = 0; i < array.length; i++) {
			IPropertySheetEntryListener listener = (IPropertySheetEntryListener) array[i];
			listener.errorMessageChanged(this);
		}
	}

	/**
	 * The values of this entry have changed. Notify all listeners of the
	 * change.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fireValueChanged() {
		Object[] array = getListeners();
		for (int i = 0; i < array.length; i++) {
			IPropertySheetEntryListener listener = (IPropertySheetEntryListener) array[i];
			listener.valueChanged(this);
		}
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public String getCategory() {
		return descriptor.getCategory();
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public IPropertySheetEntry[] getChildEntries() {
		if (childEntries == null) {
			createChildEntries();
		}
		return childEntries;
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public String getDescription() {
		return descriptor.getDescription();
	}

	/**
	 * Returns the descriptor for this entry.
	 * 
	 * @return the descriptor for this entry
	 * @since 3.1 (was previously private)
	 */
	protected IPropertyDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public String getDisplayName() {
		return descriptor.getDisplayName();
	}

	/**
	 * Gets the editor.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the editor
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySheetEntry#getEditor(org.eclipse
	 * .swt.widgets.Composite)
	 */
	public CellEditor getEditor(Composite parent) {

		if (editor == null) {
			editor = descriptor.createPropertyEditor(parent);
			if (editor != null) {
				editor.addListener(cellEditorListener);
			}
		}
		if (editor != null) {
			editor.setValue(editValue);
			setErrorText(editor.getErrorMessage());
		}
		return editor;
	}

	/**
	 * Returns the edit value for the object at the given index.
	 *
	 * @author mqfdy
	 * @param index
	 *            the value object index
	 * @return the edit value for the object at the given index
	 * @Date 2018-09-03 09:00
	 */
	protected Object getEditValue(int index) {
		Object value = values[index];
		IPropertySource source = getPropertySource(value);
		if (source != null) {
			value = source.getEditableValue();
		}
		return value;
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public String getErrorText() {
		return errorText;
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public String getFilters()[] {
		return descriptor.getFilterFlags();
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public Object getHelpContextIds() {
		return descriptor.getHelpContextIds();
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public Image getImage() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider == null) {
			return null;
		}
		return provider.getImage(editValue);
	}

	/**
	 * Returns the parent of this entry.
	 * 
	 * @return the parent entry, or <code>null</code> if it has no parent
	 * @since 3.1
	 */
	protected BusinessPropertySheetEntry getParent() {
		return parent;
	}

	/**
	 * Returns an property source for the given object.
	 * 
	 * @param object
	 *            an object for which to obtain a property source or
	 *            <code>null</code> if a property source is not available
	 * @return an property source for the given object
	 * @since 3.1 (was previously private)
	 */
	protected IPropertySource getPropertySource(Object object) {
		if (sources.containsKey(object))
			return (IPropertySource) sources.get(object);

		IPropertySource result = null;
		IPropertySourceProvider provider = propertySourceProvider;

		if (provider == null && object != null) {
			provider = (IPropertySourceProvider) BusinessModelEditorPlugin.getAdapter(object,
					IPropertySourceProvider.class, false);
		}

		if (provider != null) {
			result = provider.getPropertySource(object);
		} else {
			result = (IPropertySource) BusinessModelEditorPlugin.getAdapter(object,
					IPropertySource.class, false);
		}

		sources.put(object, result);
		return result;
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public String getValueAsString() {
		if (editValue == null) {
			return "";//$NON-NLS-1$
		}
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider == null) {
			return editValue.toString();
		}
		String text = provider.getText(editValue);
		if (text == null) {
			return "";//$NON-NLS-1$
		}
		return text;
	}

	/**
	 * Returns the value objects of this entry.
	 * 
	 * @return the value objects of this entry
	 * @since 3.1 (was previously private)
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * @return
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public boolean hasChildEntries() {
		if (childEntries != null && childEntries.length > 0) {
			return true;
		}
		// see if we could have entires if we were asked
		return computeMergedPropertyDescriptors().size() > 0;
	}

	/**
	 * Update our child entries. This implementation tries to reuse child
	 * entries if possible (if the id of the new descriptor matches the
	 * descriptor id of the old entry).
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void refreshChildEntries() {
		if (childEntries == null) {
			// no children to refresh
			return;
		}

		// get the current descriptors
		List descriptors = computeMergedPropertyDescriptors();

		// cache old entries by their descriptor id
		Map entryCache = new HashMap(childEntries.length * 2 + 1);
		for (int i = 0; i < childEntries.length; i++) {
			BusinessPropertySheetEntry childEntry = childEntries[i];
			if (childEntry != null) {
				entryCache.put(childEntry.getDescriptor().getId(), childEntry);
			}
		}

		// create a list of entries to dispose
		List entriesToDispose = new ArrayList(Arrays.asList(childEntries));

		// clear the old entries
		this.childEntries = null;

		// rebuild child entries using old when possible
		BusinessPropertySheetEntry[] newEntries = new BusinessPropertySheetEntry[descriptors
				.size()];
		boolean entriesChanged = descriptors.size() != entryCache.size();
		for (int i = 0; i < descriptors.size(); i++) {
			IPropertyDescriptor d = (IPropertyDescriptor) descriptors.get(i);
			// see if we have an entry matching this descriptor
			BusinessPropertySheetEntry entry = (BusinessPropertySheetEntry) entryCache
					.get(d.getId());
			if (entry != null) {
				// reuse old entry
				entry.setDescriptor(d);
				entriesToDispose.remove(entry);
			} else {
				// create new entry
				entry = createChildEntry();
				entry.setDescriptor(d);
				entry.setParent(this);
				entry.setPropertySourceProvider(propertySourceProvider);
				entriesChanged = true;
			}
			entry.refreshValues();
			newEntries[i] = entry;
		}

		// only assign if successful
		this.childEntries = newEntries;

		if (entriesChanged) {
			fireChildEntriesChanged();
		}

		// Dispose of entries which are no longer needed
		for (int i = 0; i < entriesToDispose.size(); i++) {
			((IPropertySheetEntry) entriesToDispose.get(i)).dispose();
		}
	}

	/**
	 * Refresh the entry tree from the root down.
	 * 
	 * @since 3.1 (was previously private)
	 */
	protected void refreshFromRoot() {
		if (parent == null) {
			refreshChildEntries();
		} else {
			parent.refreshFromRoot();
		}
	}

	/**
	 * Update our value objects. We ask our parent for the property values based
	 * on our descriptor.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void refreshValues() {
		// get our parent's value objects
		Object[] currentSources = parent.getValues();

		// loop through the objects getting our property value from each
		Object[] newValues = new Object[currentSources.length];
		for (int i = 0; i < currentSources.length; i++) {
			IPropertySource source = parent
					.getPropertySource(currentSources[i]);
			newValues[i] = source.getPropertyValue(descriptor.getId());
		}

		// set our new values
		setValues(newValues);
	}

	/**
	 * Removes the property sheet entry listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public void removePropertySheetEntryListener(
			IPropertySheetEntryListener listener) {
		removeListenerObject(listener);
	}

	/**
	 * 
	 */
	/*
	 * (non-Javadoc) Method declared on IPropertySheetEntry.
	 */
	public void resetPropertyValue() {
		if (parent == null) {
			// root does not have a default value
			return;
		}

		// Use our parent's values to reset our values.
		boolean change = false;
		Object[] objects = parent.getValues();
		for (int i = 0; i < objects.length; i++) {
			IPropertySource source = getPropertySource(objects[i]);
			if (source.isPropertySet(descriptor.getId())) {
				// fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=21756
				if (source instanceof IPropertySource2) {
					IPropertySource2 extendedSource = (IPropertySource2) source;
					// continue with next if property is not resettable
					if (!extendedSource
							.isPropertyResettable(descriptor.getId())) {
						continue;
					}
				}
				source.resetPropertyValue(descriptor.getId());
				change = true;
			}
		}
		if (change) {
			refreshFromRoot();
		}
	}

	/**
	 * Set the descriptor.
	 *
	 * @author mqfdy
	 * @param newDescriptor
	 *            the new descriptor
	 * @Date 2018-09-03 09:00
	 */
	private void setDescriptor(IPropertyDescriptor newDescriptor) {
		// if our descriptor is changing, we have to get rid
		// of our current editor if there is one
		if (descriptor != newDescriptor && editor != null) {
			editor.dispose();
			editor = null;
		}
		descriptor = newDescriptor;
	}

	/**
	 * Set the error text. This should be set to null when the current value is
	 * valid, otherwise it should be set to a error string
	 *
	 * @author mqfdy
	 * @param newErrorText
	 *            the new error text
	 * @Date 2018-09-03 09:00
	 */
	private void setErrorText(String newErrorText) {
		errorText = newErrorText;
		// inform listeners
		fireErrorMessageChanged();
	}

	/**
	 * Sets the parent of the entry to be propertySheetEntry.
	 *
	 * @author mqfdy
	 * @param propertySheetEntry
	 *            the new parent
	 * @Date 2018-09-03 09:00
	 */
	private void setParent(BusinessPropertySheetEntry propertySheetEntry) {
		parent = propertySheetEntry;
	}

	/**
	 * Sets a property source provider for this entry. This provider is used to
	 * obtain an <code>IPropertySource</code> for each of this entries objects.
	 * If no provider is set then a default provider is used.
	 *
	 * @author mqfdy
	 * @param provider
	 *            IPropertySourceProvider
	 * @Date 2018-09-03 09:00
	 */
	public void setPropertySourceProvider(IPropertySourceProvider provider) {
		propertySourceProvider = provider;
	}

	/**
	 * Set the value for this entry.
	 * <p>
	 * We set the given value as the value for all our value objects. We then
	 * call our parent to update the property we represent with the given value.
	 * We then trigger a model refresh.
	 * <p>
	 *
	 * @author mqfdy
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	private void setValue(Object newValue) {
		// Set the value
		for (int i = 0; i < values.length; i++) {
			values[i] = newValue;
		}

		// Inform our parent
		parent.valueChanged(this);

		// Refresh the model
		refreshFromRoot();
	}

	/**
	 * The <code>PropertySheetEntry</code> implmentation of this method declared
	 * on<code>IPropertySheetEntry</code> will obtain an editable value for the
	 * given objects and update the child entries.
	 * <p>
	 * Updating the child entries will typically call this method on the child
	 * entries and thus the entire entry tree is updated
	 * </p>
	 *
	 * @author mqfdy
	 * @param objects
	 *            the new values for this entry
	 * @Date 2018-09-03 09:00
	 */
	public void setValues(Object[] objects) {
		values = objects;
		sources = new HashMap(values.length * 2 + 1);

		if (values.length == 0) {
			editValue = null;
		} else {
			// set the first value object as the entry's value
			Object newValue = values[0];

			// see if we should convert the value to an editable value
			IPropertySource source = getPropertySource(newValue);
			if (source != null) {
				newValue = source.getEditableValue();
			}
			if (newValue == null)
				newValue = "";
			editValue = newValue;
		}

		// update our child entries
		refreshChildEntries();

		// inform listeners that our value changed
		fireValueChanged();
	}

	/**
	 * The value of the given child entry has changed. Therefore we must set
	 * this change into our value objects.
	 * <p>
	 * We must inform our parent so that it can update its value objects
	 * </p>
	 * <p>
	 * Subclasses may override to set the property value in some custom way.
	 * </p>
	 *
	 * @author mqfdy
	 * @param child
	 *            the child entry that changed its value
	 * @Date 2018-09-03 09:00
	 */
	protected void valueChanged(BusinessPropertySheetEntry child) {
		AbstractModelElement newEle = null;
		for (int i = 0; i < values.length; i++) {
			IPropertySource source = getPropertySource(values[i]);
			source.setPropertyValue(child.getDescriptor().getId(),
					child.getEditValue(i));
			// HashMap<AbstractModelElement,IPropertySource> ss =
			// (HashMap<AbstractModelElement, IPropertySource>)
			// sources.entrySet().toArray()[i];
			// ss.keySet().toArray();
			if (source instanceof PKPropertySource) {
				newEle = ((PKPropertySource) source).pKProperty;
			} else if (source instanceof PersistencePropertySource) {
				newEle = ((PersistencePropertySource) source).persistenceProperty;
			} else if (source instanceof BusinessPropertySource) {
				newEle = ((BusinessPropertySource) source).property;
			} else if (source instanceof DTOPropertySource) {
				newEle = ((DTOPropertySource) source).dtoProperty;
			} else if (source instanceof BusinessOperationPropertySource) {
				newEle = ((BusinessOperationPropertySource) source).businessOperation;
			}
			if (values[i] instanceof Property && newEle != null) {
				Property pro = (Property) values[i];
				if (pro.getParent() instanceof BusinessClass) {
					BusinessClass bc = (BusinessClass) (pro.getParent());
					bc.getProperties().remove(pro);
					bc.addProperty((Property) newEle);
					values[i] = newEle;
					BusinessModelEvent event = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_UPDATE, bc);
					businessModelManager.businessObjectModelChanged(event);
				}
				if (pro instanceof DTOProperty) {
					// DataTransferObject bc =
					// (DataTransferObject)(pro.getParent());
					// bc.getProperties().remove(pro);
					// bc.addProperty((DTOProperty) newEle);
					values[i] = newEle;
					BusinessModelEvent event = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_UPDATE, pro);
					businessModelManager.businessObjectModelChanged(event);
				}
			} else if (values[i] instanceof AbstractGraphicalEditPart) {
				AbstractModelElement ele = (AbstractModelElement) ((AbstractGraphicalEditPart) values[i])
						.getModel();
				if (ele instanceof DiagramElement) {
					ele = businessModelManager
							.queryObjectById(((DiagramElement) ele)
									.getObjectId());
				}
				BusinessModelEvent businessModelEvent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_UPDATE,
						(AbstractModelElement) ele);
				businessModelManager
						.businessObjectModelChanged(businessModelEvent);
			} else if (values[i] instanceof BusinessOperation) {
				values[i] = newEle;
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_UPDATE, newEle);
				businessModelManager.businessObjectModelChanged(event);
			} else {
				BusinessModelEvent businessModelEvent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_UPDATE,
						(AbstractModelElement) values[i]);
				businessModelManager
						.businessObjectModelChanged(businessModelEvent);
			}
			// BusinessModelEvent businessModelEvent1 = new
			// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_UPDATE,
			// businessModelManager,newEle.getParent());
			// businessModelManager.businessObjectModelChanged(businessModelEvent1);
		}
		// inform our parent
		if (parent != null) {
			parent.valueChanged(this);
		}
	}
}
