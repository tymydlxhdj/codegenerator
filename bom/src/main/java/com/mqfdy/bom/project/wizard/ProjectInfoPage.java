package com.mqfdy.bom.project.wizard;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
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

import com.mqfdy.bom.project.util.ValidateUtil;

public class ProjectInfoPage extends WizardPage {
	
	public final BOMProjectOperation operation;
	
	private static final ImageDescriptor WIZBAN_IMAGE = ImageDescriptor
			.createFromURL(ProjectInfoPage.class.getClassLoader().getResource(
					"icons/dropwizard-import-wizban.png"));
	private static final int SIZING_TEXT_FIELD_WIDTH = 250;
	private static final String SAVED_LOCATION_ATTR = "OUTSIDE_LOCATION"; //$NON-NLS-1$
	
	private Text projectNameField;
	private Button useDefaultsButton;
	private Label locationLabel;
	private Text locationPathField;
	private Button browseButton;

	protected ProjectInfoPage(BOMProjectOperation operation) {
		super("项目基本信息", "BOM模型项目", WIZBAN_IMAGE);
		setTitle("项目基本信息配置");
		this.operation = operation;
	}

	public void createControl(Composite parent) {
		Composite page = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        layout.marginHeight = 1;
        layout.marginWidth = 1;
        page.setLayout(layout);
        setControl(page);
        
        //createChooseProjectComp(page);
        createBasicContent(page);
        createLocationContent(page);
        validate();
	}
	/**
	 * 项目基本信息
	 * @param page 向导首页
	 */
	private void createBasicContent(Composite page) {
		Composite projectGroup = new Composite(page, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label projectLabel = new Label(projectGroup, SWT.NONE);
        projectLabel.setText("Project name:");

        projectNameField = new Text(projectGroup, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        projectNameField.setLayoutData(data);
        projectNameField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
	}
	
	/**
	 * 项目保存目录
	 * @param page 向导首页
	 */
	private void createLocationContent(Composite page) {
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
		GridData buttonData = new GridData();
		buttonData.horizontalSpan = columns;
		useDefaultsButton.setLayoutData(buttonData);
		useDefaultsButton.setEnabled(false);
		createUserEntryArea(projectGroup, defaultEnabled);

		// 是否使用默认地址按钮
		useDefaultsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean useDefaults = useDefaultsButton.getSelection();
				if (useDefaults) {
					locationPathField.setText(TextProcessor
							.process(ValidateUtil.getDefaultPathDisplayString(
									operation.getParentProject())));
				}
				setUserAreaEnabled(!useDefaults);
				validate();
			}
		});
		
		locationPathField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				operation.setLocation(ValidateUtil.getDefaultPathDisplayString(
						operation.getParentProject()));
				validate();
			}
		});
		setUserAreaEnabled(!defaultEnabled);
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
			locationPathField.setText(TextProcessor.process(
					ValidateUtil.getDefaultPathDisplayString(
							this.operation.getParentProject())));
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
			locationPathField.setText(TextProcessor.process(selectedDirectory));
			getDialogSettings().put(SAVED_LOCATION_ATTR, selectedDirectory);
		}
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
	
	
	protected void validate() {
		String perjectName = projectNameField.getText();
		if(null == perjectName || perjectName.length() == 0){
			setErrorMessage("BOM project name is empty");
			setPageComplete(false);
			projectNameField.setFocus();
			return;
		}
		if(ValidateUtil.isChineseCharacter(perjectName)){
			setErrorMessage("BOM project name can't into Chinese");
			setPageComplete(false);
			projectNameField.setFocus();
			return;
		}
		if(ValidateUtil.isnumber(perjectName)){
			setErrorMessage("BOM project name can't for digital");
			setPageComplete(false);
			projectNameField.setFocus();
			return;
		}
		
		if(perjectName.length() > 60){
			setErrorMessage("BOM project name is too long");
			setPageComplete(false);
			return;
		}
		
		for (int i = 0; i < perjectName.length(); i++) {
			char c = perjectName.charAt(i);
			if(!ValidateUtil.isAllowedChar(c)){
				setErrorMessage("BOM project name contains forbidden character");
				setPageComplete(false);
				projectNameField.setFocus();
				return;
			}
		}
		
		//判断工作空间是否存在以有项目
		if(ValidateUtil.existsInWorkspace(perjectName)){
			setErrorMessage("This project with name already exists in the workspace");
			setPageComplete(false);
			projectNameField.setFocus();
			return;
		}
			
		Pattern p = Pattern.compile("[A-Za-z0-9_]+"); // 正则表达式 
		Matcher m = p.matcher(perjectName);
		if(!m.find()){
			setErrorMessage("The project name cannot have special characters");
			setPageComplete(false);
			projectNameField.setFocus();
			return;
		}
		
		String path = locationPathField.getText();
		if (path==null || "".equals(path)) {
			setErrorMessage("Location should be defined");
			setPageComplete(false);
			locationPathField.setFocus();
			return;
		}
		String projectName = projectNameField.getText();
		File file = new File(path + File.separator + projectName);
		if (file.exists()) {
			if (file.isDirectory()) {
				if (!ValidateUtil.isEmptyDirectory(file)) {
					setErrorMessage("'"+file+"' is not empty (contains '"+ ValidateUtil.listFiles(file)[0]+"')");
					setPageComplete(false);
					projectNameField.setFocus();
					return;
				}
			} else {
				setErrorMessage("'"+file+"' exists but is not a directory");
				setPageComplete(false);
				projectNameField.setFocus();
				return;
			}
		}
		operation.setProjectName(projectNameField.getText());
		operation.setLocation(locationPathField.getText());
		setErrorMessage(null);
		setMessage(null);
		setPageComplete(true);
	}

}
