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
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * DTO基本信息页签.
 *
 * @author mqfdy
 */
public class DTOBasicInfoPage extends Composite implements
		IBusinessClassEditorPage {
	
	/** The text dto name. */
	private Text textDtoName;// 名称
	
	/** The text dto disp name. */
	private Text textDtoDispName;// 显示名
	
	/** The styled text desc. */
	private StyledText styledTextDesc;
	
	/** The dto dialog. */
	private DTOEditDialog dtoDialog;

	/** The manager. */
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();
	
	/** The dto disp name. */
	protected Object dtoDispName;

	/**
	 * Instantiates a new DTO basic info page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param dtoDialog
	 *            the dto dialog
	 */
	DTOBasicInfoPage(Composite parent, int style, DTOEditDialog dtoDialog) {
		super(parent, style);
		this.dtoDialog = dtoDialog;
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

		/*
		 * Group group_info = new Group(this, SWT.NONE);
		 * group_info.setText("关联关系信息"); group_info.setLayout(new
		 * GridLayout(4,false)); group_info.setLayoutData(new GridData(SWT.FILL,
		 * SWT.CENTER, true, false,1, 1));
		 */

		Label lblAssName = new Label(this, SWT.NONE);
		lblAssName.setText("名称:");

		textDtoName = new Text(this, SWT.BORDER);
		textDtoName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		textDtoName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (dtoDispName == null)
					textDtoDispName.setText(textDtoName.getText());
			}
		});

		Label lblAssDispName = new Label(this, SWT.NONE);
		lblAssDispName.setText("显示名:");

		textDtoDispName = new Text(this, SWT.BORDER);
		GridData gridData_textAssDispName = new GridData(SWT.FILL);
		gridData_textAssDispName.grabExcessHorizontalSpace = true;
		textDtoDispName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		textDtoDispName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				dtoDispName = textDtoDispName.getText();
			}
		});
		Label lblDesc = new Label(this, SWT.NONE);
		lblDesc.setText("备注：");

		styledTextDesc = new StyledText(this, SWT.BORDER | SWT.V_SCROLL);

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
		textDtoName.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				if (dtoDialog != null) {
					
					dtoDialog.setErrorMessage(null);
				}
				if (CheckerUtil.checkJava(textDtoName.getText())) {
					dtoDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
				}
			}

		});
	}

	/**
	 * 
	 */
	public void initControlValue() {
		if (dtoDialog.dto != null) {
			DataTransferObject dto = dtoDialog.dto;
			if (dto.getName() == null) {
				textDtoName.setText(BusinessModelUtil
						.getEditorBusinessModelManager().generateNextDTOName());
				textDtoDispName.setText(textDtoName.getText());
			} else {
				textDtoName.setText(StringUtil.convertNull2EmptyStr(dto
						.getName()));
				textDtoDispName.setText(StringUtil.convertNull2EmptyStr(dto
						.getDisplayName()));
				dtoDispName = textDtoDispName.getText();
			}
			styledTextDesc.setText(StringUtil.convertNull2EmptyStr(dto
					.getRemark()));

		}
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		if (textDtoName.getText().trim().length() == 0) {
			dtoDialog.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
			return false;
		}
		// if (!textDtoName.getText().trim().matches("^[a-zA-Z][a-zA-Z_0-9]*$"))
		// {
		// dtoDialog.setErrorMessage(ERROR_MESSAGE_NAME_RULE);
		// return false;
		// }
		if (CheckerUtil.checkJava(textDtoName.getText())) {
			dtoDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
			return false;
		}
		if (!ValidatorUtil.valiName(textDtoName.getText())) {
			dtoDialog
					.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_RULE);
			return false;
		}
		if (!ValidatorUtil.valiDisplayName(textDtoDispName.getText())) {
			dtoDialog
					.setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME);
			return false;
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_ADD.equals(dtoDialog
				.getOperationType())) {
			if (CheckerUtil.checkIsExist(textDtoName.getText(),
					BusinessModelManager.DTO_NAME_PREFIX)
//					manager.isDtoExist(textDtoName.getText())
					) {
				dtoDialog.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {
		DataTransferObject dto = dtoDialog.dto;
		if (textDtoName.getText() != null) {
			dto.setName(textDtoName.getText());
		}
		if (textDtoDispName.getText() != null) {
			dto.setDisplayName(textDtoDispName.getText());
		}
		if (styledTextDesc.getText() != null) {
			dto.setRemark(styledTextDesc.getText());
		}

	}

}
