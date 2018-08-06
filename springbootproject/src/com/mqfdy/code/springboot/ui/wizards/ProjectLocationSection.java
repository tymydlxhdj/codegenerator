package com.mqfdy.code.springboot.ui.wizards;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.springboot.core.MicroProjectPlugin;
import com.mqfdy.code.springboot.core.datasource.SampleProject;
import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.util.expression.ValueListener;
import com.mqfdy.code.springboot.core.validators.ValidationResult;
import com.mqfdy.code.springboot.core.validators.Validator;

public class ProjectLocationSection extends WizardPageSection {
	
	private static final int SIZING_TEXT_FIELD_WIDTH = 250;
	
	private static final String SAVED_LOCATION_ATTR = "OUTSIDE_LOCATION"; //$NON-NLS-1$

	private Button useDefaultsButton;
	private Label locationLabel;
	private Text locationPathField;
	private Button browseButton;

	private LiveExpression<String> locationExp = new LiveExpression<String>("") {
		@Override
		protected String compute() {
			if (isDefault()) {
				return getDefaultPathDisplayString();
			}
			return locationPathField.getText();
		}
	};
	
	private final Validator validator;

	public ProjectLocationSection(BaseInfoWizardPage owner) {
		super(owner);
		owner.operation.setLocationField(locationExp);
		validator = owner.operation.getLocationValidator();
	}
	
	/**
	 * Return whether or not we are currently showing the default location for
	 * the project.
	 * 
	 * @return boolean
	 */
	public boolean isDefault() {
		if (useDefaultsButton != null) {
			return useDefaultsButton.getSelection();
		}
		return true;
	}

	public LiveExpression<String> getLocation() {
		return locationExp;
	}
	
	@Override
	public LiveExpression<ValidationResult> getValidator() {
		return validator;
	}

	/* (non-Javadoc)创建向导第一个页面
	 * @see org.springsource.ide.eclipse.gradle.ui.util.PageSection#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createContents(Composite page) {
		boolean defaultEnabled = true;
		int columns = 4;
		// project specification group
		Composite projectGroup = new Composite(page, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = columns;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		useDefaultsButton = new Button(projectGroup, SWT.CHECK | SWT.RIGHT);
		useDefaultsButton.setText("Use default location");
		useDefaultsButton.setSelection(defaultEnabled);
		// Springboot项目路径不可变更
		useDefaultsButton.setEnabled(false);
		GridData buttonData = new GridData();
		buttonData.horizontalSpan = columns;
		useDefaultsButton.setLayoutData(buttonData);

		createUserEntryArea(projectGroup, defaultEnabled);

		// 是否使用默认地址按钮
		useDefaultsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean useDefaults = useDefaultsButton.getSelection();
				if (useDefaults) {
					locationPathField.setText(TextProcessor.process(
							getDefaultPathDisplayString()));
				}
				setUserAreaEnabled(!useDefaults);
				locationExp.refresh();
			}
		});
		locationPathField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				locationExp.refresh();
			}
		});
		setUserAreaEnabled(!defaultEnabled);
		locationExp.refresh();
	}

	private String getDefaultPathDisplayString() {
		return SampleProject.getDefaultProjectLocation();
	}

	private LiveExpression<String> getParentProjectExp() {
		return ((BaseInfoWizardPage)owner).operation.getParentProjectLE();
	}

	/**
	 * Set the enablement state of the receiver.
	 * 
	 * @param enabled
	 */
	private void setUserAreaEnabled(boolean enabled) {
		locationLabel.setEnabled(enabled);
		locationPathField.setEnabled(enabled);
		browseButton.setEnabled(enabled);
	}
	
	private void createUserEntryArea(Composite composite, boolean defaultEnabled) {
		// location label
		locationLabel = new Label(composite, SWT.NONE);
		locationLabel.setText("Location:");
		// project location entry field
		locationPathField = new Text(composite, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		data.horizontalSpan = 2;
		locationPathField.setLayoutData(data);
		// browse button
		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText("Browse");
		//向导页面点击选择按钮时触发事件
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleLocationBrowseButtonPressed();
			}
		});
		//勾选为真或假时设置LOCATION的值
		if (defaultEnabled) {
			locationPathField.setText(
					TextProcessor.process(getDefaultPathDisplayString()));
		} else {
			locationPathField.setText("");
		}
	}
	
	private void handleLocationBrowseButtonPressed() {
		String selectedDirectory = null;
		String dirName = getPathFromLocationField();

		if (dirName!=null && !dirName.equals("")) {
			File dir = new File(dirName);
			if (!dir.exists()) {
				dirName = "";
			}
		}
		if (dirName==null || dirName.equals("")) {
			String value = getDialogSettings().get(SAVED_LOCATION_ATTR);
			if (value != null) {
				dirName = value;
			}
		}
		//弹出选择文件位置的对话框
		DirectoryDialog dialog = new DirectoryDialog(locationPathField.getShell(), SWT.SHEET);
		dialog.setMessage("Select the location directory");
		dialog.setFilterPath(dirName);
		selectedDirectory = dialog.open();
		if (selectedDirectory != null) {
			updateLocationField(selectedDirectory);
			getDialogSettings().put(SAVED_LOCATION_ATTR, selectedDirectory);
		}
	}

	private void updateLocationField(String selectedPath) {
		locationPathField.setText(TextProcessor.process(selectedPath));
	}
	
	/**
	 * Return the path on the location field.
	 * 
	 * @return the path or the field's text if the path is invalid
	 */
	private String getPathFromLocationField() {
		URI fieldURI;
		try {
			fieldURI = new URI(locationPathField.getText());
		} catch (URISyntaxException e) {
			return locationPathField.getText();
		}
		String path= fieldURI.getPath();
		return path != null ? path : locationPathField.getText();
	}

	private IDialogSettings getDialogSettings() {
		IDialogSettings ideDialogSettings = MicroProjectPlugin.getDefault().getDialogSettings();
		IDialogSettings result = ideDialogSettings.getSection(getClass().getName());
		if (result == null) {
			result = ideDialogSettings.addNewSection(getClass().getName());
		}
		return result;
	}
	
}
