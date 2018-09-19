package com.mqfdy.code.thirdparty.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


// TODO: Auto-generated Javadoc
/**
 * The Class ChooseFileWizardPage.
 *
 * @author mqfdy
 */
public class ChooseFileWizardPage extends WizardPage {
	
	/** The file path text. */
	private Text filePathText;
	
	/** The file path. */
	private String filePath;
	
	/**
	 * Instantiates a new choose file wizard page.
	 *
	 * @param name
	 *            the name
	 */
	protected ChooseFileWizardPage(String name) {
		super(name);
		setTitle("PDM文件");
		setDescription("导入*.pdm文件");
	}

	/**
	 * Creates the control.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		//GridData的组件占两列显示
		gridData.horizontalSpan = 2;
		// 元数据文件存放路径设置
		Label filePathlabel = new Label(container, SWT.NULL);
		filePathlabel.setText("打开文件路径：");

		filePathText = new Text(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		GridData gd_filePathText = new GridData(GridData.FILL_HORIZONTAL);
		filePathText.setLayoutData(gd_filePathText);
		

		Button browseButton = new Button(container, SWT.PUSH);
		browseButton.setText("浏览...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse2();//调用下面的方法
			}
		});
		// 初始化监听器
		initializeListener();

		setControl(container);
		
	}

	/**
	 * Sets the data.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void setData(){
		if(filePathText.getText()!=null && !"".equals(filePathText.getText())){
			this.filePath = filePathText.getText();
		}
	}
	
	/**
	 * 浏览文件夹hu.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void handleBrowse2() {
		FileDialog dlg = new FileDialog(this.getShell(), SWT.OPEN);
		// 初始的文件目录
		dlg.setFileName("c:/");
		String fileName = dlg.open();
		if (fileName!=null && fileName.endsWith("pdm")) {
			filePathText.setText(fileName);
			this.filePath = fileName;
			setData();
			((TableStructureWizardPage)getNextPage()).repaint();
		} else {
			MessageDialog.openInformation(getShell(), "提示",
					"请选择后缀为(*.pdm)文件类型！");
		}
		   
		
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage() {
		return !"".equals(filePathText.getText());
	}
	
	/**
	 * 初始化监听器.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initializeListener() {
		filePathText.addModifyListener(new DataValidateListener());
		//namespaceText.addModifyListener(new DataValidateListener());
		//modelNameText.addModifyListener(new DataValidateListener());
		//modelDisplayNameText.addModifyListener(new DataValidateListener());
	}

	/**
	 * 数据校验监听器.
	 *
	 * @see DataValidateEvent
	 */
	class DataValidateListener implements ModifyListener {
		
		/**
		 * Modify text.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		public void modifyText(ModifyEvent e) {
			validateData();
		}
	}

	/**
	 * @return
	 */
	@Override
	public IWizardPage getNextPage() {
		TableStructureWizardPage nextPage = (TableStructureWizardPage) super.getWizard().getPage("tsPage");
		/*if(nextPage!=null)
			nextPage.repaint();*/
		return nextPage;
	}

	/**
	 * Validate data.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void validateData() {
		String errorMessage = null;
		if(filePathText.getText()==null || "".equals(filePathText.getText())){
			setErrorMessage("请选择PDM文件");
		}
		setErrorMessage(errorMessage);
		setPageComplete(errorMessage == null);
	}
	
	/**
	 * Gets the file path.
	 *
	 * @author mqfdy
	 * @return the file path
	 * @Date 2018-09-03 09:00
	 */
	public String getFilePath(){
		return this.filePath;
	}
}
