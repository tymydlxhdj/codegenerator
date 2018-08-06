package com.mqfdy.code.designer.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Annotation;

/**
 * 业务类编辑弹出框(用于新增和编辑)
 * 
 * @author mqfdy
 * 
 */
public class AnnotationEditorDialog extends TrayDialog {

	public static final String DIALOG_TITLE_ADD = "创建注释";
	public static final String DIALOG_TITLE_EDIT = "修改注释";

	private Annotation annotationCopy;

	private Text contentText;

	/**
	 * 从组件面板创建
	 */
	private boolean  createFromPlatter = false;
	protected boolean isChaged = false;
	private Object operationType;
	private Button okBut;

	/**
	 * 新增业务类时构造函数
	 * 
	 * @param parentShell
	 * @param parent
	 *            父节点
	 */
	public AnnotationEditorDialog(Shell parentShell,
			AbstractModelElement parent) {
		super(parentShell);
	}

	/**
	 * 编辑业务类时构造函数
	 * 
	 * @param parentShell
	 * @param editingElement
	 *            编辑的对象
	 * @param parent
	 *            父节点
	 */
	public AnnotationEditorDialog(Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell);
		this.annotationCopy = (Annotation) editingElement;
		this.operationType = ModelElementEditorDialog.OPERATION_TYPE_EDIT;
	}
	public AnnotationEditorDialog(boolean createFromPlatter,Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell);
		this.createFromPlatter = createFromPlatter;
		this.annotationCopy = (Annotation) editingElement;
		this.operationType = ModelElementEditorDialog.OPERATION_TYPE_ADD;
	}
	public AnnotationEditorDialog(Shell shell) {
		super(shell);
	}

	protected Control createDialogArea(Composite composite) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		composite.setLayout(gridLayout);

		contentText = new Text(composite, SWT.BORDER | SWT.MULTI
				| SWT.VERTICAL | SWT.WRAP);
		GridData layoutData_remark  = new GridData(GridData.FILL_HORIZONTAL);
		layoutData_remark.horizontalSpan = 3;
		layoutData_remark.widthHint = 440;
		layoutData_remark.heightHint = 140;
		contentText.setLayoutData(layoutData_remark);
		contentText.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				validateAllInput();
			}
		});
		initControlValue();

		return composite;
	}

	/**
	 * 操作按钮
	 */
	protected void createButtonsForButtonBar(Composite composite) {
		okBut = createButton(composite, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(composite, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		if(validateAllInput()){
			okBut.setEnabled(true);
		}else{
			okBut.setEnabled(false);
		}
		contentText.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				if(validateAllInput()){
					okBut.setEnabled(true);
				}else{
					okBut.setEnabled(false);
				}
			}
		});
	}

	protected void okPressed() {
		updateTheEditingElement();
		super.okPressed();
	}

	/**
	 * 验证输入数据是否合法
	 */
	private boolean validateAllInput() {
		boolean isOk = true;//basicInfoPage.validateInput();
		if(contentText.getText() == null 
				|| "".equals(contentText.getText().trim())){
			isOk = false;
		}
		return isOk;
	}

	/**
	 * 更新模型
	 */
	private void updateTheEditingElement() {
		((Annotation)annotationCopy).setContent(contentText.getText());
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (operationType.equals(ModelElementEditorDialog.OPERATION_TYPE_ADD)) {
			newShell.setText(DIALOG_TITLE_ADD);
		} else if (operationType.equals(ModelElementEditorDialog.OPERATION_TYPE_EDIT) && createFromPlatter) {
			newShell.setText(DIALOG_TITLE_ADD);
		} else {
			newShell.setText(DIALOG_TITLE_EDIT);
		}
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_ANNOTATION));
	}

	/**
	 * 初始化弹出框控件的值
	 */
	private void initControlValue() {

		if (/*ModelElementEditorDialog.OPERATION_TYPE_EDIT.equals(operationType) && */annotationCopy != null) {
//			if (editingElement instanceof Annotation) {
//				annotationCopy = (Annotation) editingElement;
//			}
		} else {
			annotationCopy = new Annotation();
			annotationCopy.setStereotype(Annotation.STEREOTYPE_CUSTOM);
			okBut.setEnabled(false);
		}
		contentText.setText(annotationCopy.getContent()== null ?"":annotationCopy.getContent());
	}

	public Annotation getAnnotationCopy() {
		return annotationCopy;
	}
	public boolean isChanged() {
		return this.isChaged;
	}

	@Override
	protected boolean isResizable() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
