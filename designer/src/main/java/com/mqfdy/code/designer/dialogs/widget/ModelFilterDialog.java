package com.mqfdy.code.designer.dialogs.widget;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelFilterDialog.
 *
 * @author mqfdy
 */
public class ModelFilterDialog extends TrayDialog {
	
	/** The is include property. */
	private boolean isIncludeProperty = false;;
	
	/** The is include operation. */
	private boolean isIncludeOperation = false;
	
	/** The is include enum element. */
	private boolean isIncludeEnumElement = false;
	
	/** The isincludeproperty. */
	public static String ISINCLUDEPROPERTY = "isIncludeProperty";;
	
	/** The isincludeoperation. */
	public static String ISINCLUDEOPERATION = "isIncludeOperation";
	
	/** The isincludeenumelement. */
	public static String ISINCLUDEENUMELEMENT = "isIncludeEnumElement";
	
	/**
	 * Instantiates a new model filter dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public ModelFilterDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("模型搜索项配置");
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_FILTER));
	}
	
	/**
	 * Creates the dialog area.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the control
	 * @Date 2018-09-03 09:00
	 */
	protected Control createDialogArea(Composite parent) {
		Composite com = new Composite(parent, SWT.NONE);
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.numColumns = 1;
		com.setLayout(gridLayout);
		Label l = new Label(com, SWT.NONE);
		l.setText("支持对以下元素进行搜索和显示：");
		final Button buPer = new Button(com, SWT.CHECK);
		buPer.setText("属性");
		buPer.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				isIncludeProperty = buPer.getSelection();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				isIncludeProperty = false;
			}
		});
		final Button buOper = new Button(com, SWT.CHECK);
		buOper.setText("操作");
		buOper.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				isIncludeOperation = buOper.getSelection();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				isIncludeOperation = false;
			}
		});
		final Button buEnum = new Button(com, SWT.CHECK);
		buEnum.setText("枚举元素");
		buEnum.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				isIncludeEnumElement = buEnum.getSelection();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				isIncludeEnumElement = false;
			}
		});
		
		
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		if (store.getBoolean(ISINCLUDEPROPERTY)){
			buPer.setSelection(true);
			isIncludeProperty = true;
		}
		
		if (store.getBoolean(ISINCLUDEOPERATION)){
			buOper.setSelection(true);
			isIncludeOperation = true;
		}
		
		if (store.getBoolean(ISINCLUDEENUMELEMENT)){
			buEnum.setSelection(true);
			isIncludeEnumElement = true;
		}
		
		return parent;
	}
	
	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		store.setValue(ISINCLUDEPROPERTY, isIncludeProperty);
		store.setValue(ISINCLUDEOPERATION, isIncludeOperation);
		store.setValue(ISINCLUDEENUMELEMENT, isIncludeEnumElement);
		BusinessModelEditorPlugin.getDefault().savePluginPreferences();
		
		
		
		
		super.okPressed();
	}

//	/**
//	 * 操作按钮
//	 */
//	protected void createButtonsForButtonBar(Composite parent) {
//		/*okButton = */createButton(parent, IDialogConstants.OK_ID,
//				IDialogConstants.OK_LABEL, true);
//		Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID,
//				IDialogConstants.CANCEL_LABEL, false);
//	}
}
