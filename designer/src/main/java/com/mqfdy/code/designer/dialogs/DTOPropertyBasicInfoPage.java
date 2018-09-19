package com.mqfdy.code.designer.dialogs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * DTO属性节本信息页.
 *
 * @author mqfdy
 */
public class DTOPropertyBasicInfoPage extends Composite implements
		IBusinessClassEditorPage {
	
	/** The text name. */
	private Text textName;// 名称
	
	/** The text disp name. */
	private Text textDispName;// 显示名
	
	/** The styled text desc. */
	StyledText styledTextDesc; // 备注
	
	/** The combo data type. */
	private Combo comboDataType;// 数据类型
	
	/** The text default value. */
	private Text textDefaultValue;// 缺省值
	
	/** The parent dialog. */
	DTOPropertyEditDialog parentDialog;

	/**
	 * Instantiates a new DTO property basic info page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param dtoDialog
	 *            the dto dialog
	 */
	DTOPropertyBasicInfoPage(Composite parent, int style,
			DTOPropertyEditDialog dtoDialog) {
		super(parent, style);
		this.parentDialog = dtoDialog;
		createContent();
		addListeners();
	}

	/**
	 * Creates the content.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
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

		Label lblAssDispName = new Label(this, SWT.NONE);
		lblAssDispName.setText("显示名:");

		textDispName = new Text(this, SWT.BORDER);
		textDispName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblAssType = new Label(this, SWT.NONE);
		lblAssType.setText("数据类型:");

		GridData data_comboAssType = new GridData(GridData.FILL_HORIZONTAL);
		data_comboAssType.grabExcessHorizontalSpace = true;
		comboDataType = new Combo(this, SWT.BORDER | SWT.FILL | SWT.READ_ONLY);
		comboDataType.setLayoutData(data_comboAssType);

		Label lbl1 = new Label(this, SWT.NONE);
		lbl1.setText("缺省值");
		textDefaultValue = new Text(this, SWT.BORDER);
		textDefaultValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblDesc = new Label(this, SWT.NONE);
		lblDesc.setText("备注：");

		styledTextDesc = new StyledText(this, SWT.BORDER | SWT.SCROLL_LINE);
		styledTextDesc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));

		GridData data = new GridData();
		data.horizontalSpan = 3;
		data.heightHint = 60;
		data.horizontalAlignment = SWT.FILL;
		styledTextDesc.setLayoutData(data);
	}

	/**
	 * Adds the listeners.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void addListeners() {
		textName.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				if (parentDialog != null) {
					parentDialog.setErrorMessage(null);
					
				}
				if (CheckerUtil.checkJava(textName.getText())) {
					parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
				}
			}

		});
	}

	/**
	 * 
	 */
	public void initControlValue() {
		if (parentDialog.getProperty() != null) {
			// 初始化下拉框的值
			comboDataType.setItems(DataType.getDataTypesString());
			comboDataType.select(DataType.getIndex(DataType.String
					.getValue_hibernet()));

			textName.setText(StringUtil.convertNull2EmptyStr(parentDialog
					.getProperty().getName()));
			textDispName.setText(StringUtil.convertNull2EmptyStr(parentDialog
					.getProperty().getDisplayName()));
			styledTextDesc.setText(StringUtil.convertNull2EmptyStr(parentDialog
					.getProperty().getRemark()));

			String dataType = parentDialog.getProperty().getDataType();
			comboDataType.setText(StringUtil.convertNull2EmptyStr(dataType));
		}

	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		if (textName.getText().trim().length() == 0) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
			return false;
		}
		// if (!textName.getText().trim().matches("^[a-zA-Z][a-zA-Z_0-9]*$"))
		// {
		// parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_RULE);
		// return false;
		// }
		if (!ValidatorUtil.valiName(textName.getText())) {
			parentDialog
					.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_RULE);
			return false;
		}
		if (CheckerUtil.checkJava(textName.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
			return false;
		}
		if (!ValidatorUtil.valiDisplayName(textDispName.getText())) {
			parentDialog
					.setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME);
			return false;
		}

		return true;
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {
		String name = StringUtil.convertNull2EmptyStr(textName.getText());
		String dispName = StringUtil.convertNull2EmptyStr(textDispName
				.getText());
		// textDefaultValue.setText(StringUtil.convertNull2EmptyStr(dtoPropertAddDialog.property.get));
		String remark = StringUtil.convertNull2EmptyStr(styledTextDesc
				.getText());
		String dataType = comboDataType.getText();

		parentDialog.getProperty().setName(name);
		parentDialog.getProperty().setDisplayName(dispName);
		parentDialog.getProperty().setRemark(remark);
		parentDialog.getProperty().setDataType(dataType);

		if (ModelElementEditorDialog.OPERATION_TYPE_ADD
				.equals(parentDialog.operationType)) {
			List<Property> properties = parentDialog.getParentPage()
					.getDtoDialog().dto.getProperties();
			if (properties == null || properties.size() == 0) {
				parentDialog.getProperty().setOrderNum(1);
			} else {
				parentDialog.getProperty().setOrderNum(properties.size() + 1);
			}
		}
	}

}
