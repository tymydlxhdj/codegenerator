package com.mqfdy.code.datasource.utils;

import org.eclipse.datatools.connectivity.ICategory;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.connectivity.internal.ConnectionProfileManager;
import org.eclipse.datatools.connectivity.internal.ui.wizards.CPWizardNode;
import org.eclipse.datatools.connectivity.internal.ui.wizards.NewCPWizard;
import org.eclipse.datatools.connectivity.internal.ui.wizards.ProfileWizardProvider;
import org.eclipse.datatools.connectivity.ui.wizards.IWizardCategoryProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.datasource.constant.IDataSource;

// TODO: Auto-generated Javadoc
/**
 * 创建数据源工具类.
 *
 * @author mqfdy
 */
public class DataSourceUtils implements IDataSource {

	/** The Constant DATABASE_CATEGORY_ID. */
	private static final String DATABASE_CATEGORY_ID = "org.eclipse.datatools.connectivity.db.category"; //$NON-NLS-1$

	/**
	 * Launch the DTP New Connection Profile wizard to create a new database
	 * connection profile.
	 * 
	 * Returns the name of the added profile, or null if the wizard is
	 * cancelled.
	 *
	 * @author mqfdy
	 * @return the i connection profile
	 * @Date 2018-09-03 09:00
	 */
	public static IConnectionProfile newDataSourceWizard() {
		NewCPWizard wizard;
		WizardDialog wizardDialog;

		// Filter datasource category
	  	ViewerFilter viewerFilter = new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				
				CPWizardNode wizardNode = (CPWizardNode) element;
				if( !(wizardNode.getProvider() instanceof IWizardCategoryProvider)) {
					ICategory cat = ConnectionProfileManager.getInstance().getProvider(
									((ProfileWizardProvider) wizardNode.getProvider()).getProfile()).getCategory();
					
					// Only display wizards belong to database category
					while(cat != null) {
						if(cat.getId().equals(DATABASE_CATEGORY_ID))
							return true;
						cat = cat.getParent();
					}
				}
				return false;
			}
		};
		wizard = new NewCPWizard(viewerFilter, null);
		Shell currentShell = Display.getCurrent().getActiveShell();
		wizardDialog = new WizardDialog(currentShell, wizard);
		wizardDialog.setBlockOnOpen(true);
		
		LocalProfileListener listener = new LocalProfileListener();
		ProfileManager.getInstance().addProfileListener(listener);
		
		if(wizardDialog.open() == Window.CANCEL) {
			ProfileManager.getInstance().removeProfileListener(listener);
			return null;
		}
		IConnectionProfile addedProfile = listener.addedProfile;
		ProfileManager.getInstance().removeProfileListener(listener);
		
		return addedProfile;
	}

	/**
	 * The listener interface for receiving localProfile events. The class that
	 * is interested in processing a localProfile event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's <code>addLocalProfileListener<code>
	 * method. When the localProfile event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @author mqfdy
	 * @see LocalProfileEvent
	 */
	static class LocalProfileListener implements IProfileListener {
		
		/** The added profile. */
		IConnectionProfile addedProfile;
		
		/**
		 * @see org.eclipse.datatools.connectivity.IProfileListener#profileAdded(org.eclipse.datatools.connectivity.IConnectionProfile)
		 * @param profile LocalProfileListener
		 */
		public void profileAdded( IConnectionProfile profile) {
			addedProfile = profile;
			//IJDBCDriverDefinitionConstants.URL_PROP_ID
		}
	
		/**
		 * @see org.eclipse.datatools.connectivity.IProfileListener#profileChanged(org.eclipse.datatools.connectivity.IConnectionProfile)
		 * @param profile LocalProfileListener
		 */
		public void profileChanged( IConnectionProfile profile) {
			// do nothing
		}
	
		/**
		 * @see org.eclipse.datatools.connectivity.IProfileListener#profileDeleted(org.eclipse.datatools.connectivity.IConnectionProfile)
		 * @param profile LocalProfileListener
		 */
		public void profileDeleted( IConnectionProfile profile) {
			// do nothing
		}
	}
	

	/**
	 * Not empty.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return return true is parameter is null and ""
	 * @Date 2018-09-03 09:00
	 */
	public static boolean notEmpty(String str){
		return str!=null && !"".equals(str);
	}

	
	
	
}
