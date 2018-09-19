package com.mqfdy.code.designer.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;


// TODO: Auto-generated Javadoc
/**
 * The Class PreferenceInitializer.
 *
 * @author mqfdy
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * 
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault().getPreferenceStore();;
		store.setDefault(ModelPreferencePage.ISADD_CUSTOMOPERATION, true);
		
		store.setDefault(ModelPreferencePage.ISPRONAMEUPPERCASE, true);
		
		store.setDefault(ModelPreferencePage.ISTABLENAMEUPPERCASE, true);
	}

}