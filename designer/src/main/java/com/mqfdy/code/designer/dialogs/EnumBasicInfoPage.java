package com.mqfdy.code.designer.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;

/**
 * 枚举信息页
 * 
 * @author mqfdy
 * 
 */
public class EnumBasicInfoPage extends Composite implements
		IBusinessClassEditorPage {
	private Text textName;// 名称
	private Text textDispName;// 显示名
	StyledText styledTextDesc; // 备注

	private EnumEditDialog parentDialog;
	protected Object enumDispName;

	EnumBasicInfoPage(Composite parent, int style, EnumEditDialog parentDialog) {
		super(parent, style);
		this.parentDialog = parentDialog;
		createContent();
		addListeners();
		initControlValue();
	}

	private void createContent() {
		// 关联关系信息区域
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.marginLeft = 8;
		this.setLayout(layout);

		Label lblAssName = new Label(this, SWT.NONE);
		lblAssName.setText("名称:");

		textName = new Text(this, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		textName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (enumDispName == null
						|| enumDispName.equals(parentDialog.getEnumeration()
								.getName()))
					textDispName.setText(textName.getText());
			}
		});
		Label lblAssDispName = new Label(this, SWT.NONE);
		lblAssDispName.setText("显示名:");

		textDispName = new Text(this, SWT.BORDER);
		textDispName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		textDispName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				enumDispName = textDispName.getText();
			}
		});

		Label lblDesc = new Label(this, SWT.NONE);
		lblDesc.setText("备注：");

		styledTextDesc = new StyledText(this, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI 
				| SWT.VERTICAL | SWT.WRAP);
		styledTextDesc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));

		GridData data = new GridData();
		data.horizontalSpan = 3;
		data.widthHint = 80;
		data.heightHint = 60;
		data.horizontalAlignment = SWT.FILL;
		styledTextDesc.setLayoutData(data);
	}

	private void addListeners() {
		textName.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				if (parentDialog != null ) {
					
					parentDialog.setErrorMessage(null);
				}
				if (CheckerUtil.checkJava(textName.getText())) {
					parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
				}
				if (ModelElementEditorDialog.OPERATION_TYPE_ADD
						.equals(parentDialog.getOperationType())
						&& CheckerUtil.checkIsExist(textName.getText(),
								BusinessModelManager.ENUMERATION_NAME_PREFIX)) {
					parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
				}
			}
		});
	}

	public void initControlValue() {
		if (parentDialog.getEnumeration() != null) {
			Enumeration enu = parentDialog.getEnumeration();
			if (enu.getName() == null) {
				textName.setText(BusinessModelUtil
						.getEditorBusinessModelManager()
						.generateNextEnumerationName());
				textDispName.setText(textName.getText());
			} else {
				textName.setText(StringUtil.convertNull2EmptyStr(enu.getName()));
				textDispName.setText(StringUtil.convertNull2EmptyStr(enu
						.getDisplayName()));
				enumDispName = textDispName.getText();
			}
			styledTextDesc.setText(StringUtil.convertNull2EmptyStr(parentDialog
					.getEnumeration().getRemark()));
			String dataType = parentDialog.getEnumeration().getType();
		}

	}

	public boolean validateInput() {
		if (textName.getText().trim().length() == 0) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
			return false;
		}
		if (!ValidatorUtil.isFirstUppercase(textName.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_UPPER);
			return false;
		}
		if (CheckerUtil.checkJava(textName.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
			return false;
		}
		if (!ValidatorUtil.valiNameLength(textName.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_LENGTH);
			return false;
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_ADD.equals(parentDialog
				.getOperationType())) {
			if (CheckerUtil.checkIsExist(textName.getText(),
					BusinessModelManager.ENUMERATION_NAME_PREFIX)) {
				parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
				return false;
			}
		} else {
			if (!textName.getText().equalsIgnoreCase(
					parentDialog.getEnumeration().getName())) {
				// 编辑时如果改为其他名
				if (CheckerUtil.checkIsExist(textName.getText(),
						BusinessModelManager.ENUMERATION_NAME_PREFIX)) {
					parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
					return false;
				}
			}
		}
		if (!ValidatorUtil.valiDisplayNameLength(textDispName.getText())) {
			parentDialog.setErrorMessage(ERROR_DISPLAYNAME_LENGTH);
			return false;
		}
		if (!ValidatorUtil.valiRemarkLength(styledTextDesc.getText())) {
			parentDialog.setErrorMessage(TOOLONG_ERROR_REMARK);
			return false;
		}
		return true;
	}

	public void updateTheEditingElement() {
		String name = StringUtil
				.convertNull2EmptyStr(textName.getText().trim());
		String dispName = StringUtil.convertNull2EmptyStr(textDispName
				.getText());
		// textDefaultValue.setText(StringUtil.convertNull2EmptyStr(dtoPropertAddDialog.property.get));
		String remark = StringUtil.convertNull2EmptyStr(styledTextDesc
				.getText());

		parentDialog.getEnumeration().setName(name);
		parentDialog.getEnumeration().setDisplayName(dispName);
		parentDialog.getEnumeration().setRemark(remark);
	}

}
