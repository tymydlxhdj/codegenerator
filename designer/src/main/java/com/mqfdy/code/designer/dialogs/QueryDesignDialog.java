package com.mqfdy.code.designer.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.QueryCondition;
import com.mqfdy.code.model.utils.DataType;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryDesignDialog.
 *
 * @author mqfdy
 */
public class QueryDesignDialog extends ModelElementEditorDialog {

	/** The title. */
	private String TITLE = "查询条件设计器";
	
	/** The business class. */
	private BusinessClass businessClass;
	
	/** The parent page. */
	DataSourceBusinessPage parentPage;
	
	/** The query design page. */
	private QueryDesignPage queryDesignPage;

	/**
	 * Instantiates a new query design dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public QueryDesignDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Instantiates a new query design dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param businessClass
	 *            the business class
	 * @param parentPage
	 *            the parent page
	 */
	public QueryDesignDialog(Shell parentShell, BusinessClass businessClass,
			DataSourceBusinessPage parentPage) {
		super(parentShell);
		this.businessClass = businessClass;
		this.parentPage = parentPage;
	}

	/**
	 * Sets the title and message.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void setTitleAndMessage() {
		setTitle(TITLE);
		setMessage("设计查询条件");
	}

	/**
	 * Creates the dialog area.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @return the control
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Control createDialogArea(Composite composite) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		composite.setLayout(gridLayout);
		setTitleAndMessage();
		if(parentPage.getParentPage() instanceof PropertyEditorPage){
			queryDesignPage = new QueryDesignPage(composite, SWT.NONE, this,
					((PropertyEditorPage) parentPage.getParentPage()).getParentDialog().getConditions());
		}
		if(parentPage.getParentPage() instanceof FkEditorPage){
			queryDesignPage = new QueryDesignPage(composite, SWT.NONE, this,
					((FkEditorPage) parentPage.getParentPage()).getParentDialog().getConditions());
		}
		
//		queryDesignPage = new QueryDesignPage(composite, SWT.NONE, this,
//				parentPage.getParentPage().getParentDialog().getConditions());
		return composite;
	}

	/**
	 * 操作按钮.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	protected void createButtonsForButtonBar(Composite composite) {
		createButton(composite, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(composite, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
//		createButton(composite, APPLY_ID, APPLY_LABEL, true);
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
		newShell.setText(TITLE);
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_LOCAL));
	}

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		if (queryDesignPage.validateInput()) {
			queryDesignPage.updateTheEditingElement();
			parentPage.setTextSql(generateCondition());
			super.okPressed();
		}
	}

	/**
	 * json:
	 * 
	 * filter:[ { junction:"or",//列内组合 columnJunction:"and",//列之间的组合
	 * 
	 * criterions:[ {fieldName:"a","operator":"=",value:"10251",sym:"and"},
	 * {fieldName:"a","operator":"^",value:"10252",sym:"or"} ] },
	 * 
	 * { junction:"or", columnJunction:"and", criterions:[
	 * {fieldName:"id","operator":"=",value:"10251"},
	 * {fieldName:"id","operator":"*",value:"10252"} ] },
	 * 
	 * { junction:"or", columnJunction:"and", criterions:[
	 * {fieldName:"id","operator":"=",value:"10251"},
	 * {fieldName:"id","operator":"*",value:"10252"} ] } ].
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	private String generateJson() {
		StringBuffer json = new StringBuffer();
		List<QueryCondition> allConditions = getParentPage().getEditor().getConditions();
		if (allConditions != null) {
			json.append("filter:[");

			for (int i = 0; i < allConditions.size(); i++) {
				QueryCondition condition = allConditions.get(i);
				if (i > 0) {
					json.append(",");
				}
				json.append("\n");
				json.append("\t{");
				json.append("\n");
				if (condition.getPreBraces().equals("(")
						&& condition.getAfterBraces().equals(")")) {
					json.append("\t\tbraces:\"\",");
				} else {
					if (condition.getPreBraces().equals("(")) {
						json.append("\t\tbraces:\"(\",");
					}
					if (condition.getAfterBraces().equals(")")) {
						json.append("\t\tbraces:\")\",");
					}
				}
				json.append("\n");
				json.append("\t\tjunction:\"or\",");
				json.append("\n");
				json.append("\t\tcolumnJunction:\""
						+ condition.getSqlLogicOperateType().getDispName()
						+ "\",");
				json.append("\n");
				json.append("\t\tcriterions:[");
				json.append("\n");
				json.append("\t\t\t{fieldName:\"" + condition.getPropertyName()
						+ "\",operator:\""
						+ condition.getSqlOperateType().getDispName()
						+ "\",value:\"" + condition.getValue1() + "\"}");
				json.append("\n");
				json.append("\t\t]");
				json.append("\n");
				json.append("\t}");
			}
			json.append("\n");
			json.append("]");
		} else {
			return "";
		}

		return json.toString();
	}

	/**
	 * Generate condition.
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	private String generateCondition() {
		StringBuffer conditionStr = new StringBuffer();
		List<QueryCondition> allConditions = queryDesignPage.getListTemp();//getParentPage().getEditor().getConditions();
		if (allConditions != null) {

			for (int i = 0; i < allConditions.size(); i++) {
				QueryCondition condition = allConditions.get(i);
				// 前括号
				if (condition.getPreBraces().equals("(")) {
					conditionStr.append(" (");
				}

				conditionStr.append(" " + businessClass.getName() + "."
						+ condition.getPropertyName());

				conditionStr.append(" "
						+ condition.getSqlOperateType().getDispName());

				DataType dataType = queryDesignPage.getDataType(condition
						.getPropertyName());

				if (condition.getSqlOperateType().Oracle_in.equals(condition
						.getSqlOperateType())
						|| condition.getSqlOperateType().Oracle_notIn
								.equals(condition.getSqlOperateType())) {
					conditionStr.append(" (");
				}

				switch (dataType) {
				case String:
					conditionStr.append(" '" + condition.getValue1() + "'");
					break;
				case Date:
					conditionStr.append(" to_date('" + condition.getValue1()
							+ "','yyyy-MM-dd HH:mi:ss')");
					break;
				case Time:
					conditionStr.append(" to_timestamp('"
							+ condition.getValue1()
							+ "','yyyy-MM-dd HH:mi:ss.ff')");
					break;
				case Timestamp:
					conditionStr.append(" to_timestamp('"
							+ condition.getValue1()
							+ "','yyyy-MM-dd HH:mi:ss.ff')");
					break;
				default:
					conditionStr.append(" " + condition.getValue1());
				}

				if (condition.getSqlOperateType().Oracle_in.equals(condition
						.getSqlOperateType())
						|| condition.getSqlOperateType().Oracle_notIn
								.equals(condition.getSqlOperateType())) {
					conditionStr.append(") ");
				}

				// 后括号
				if (condition.getAfterBraces().equals(")")) {
					conditionStr.append(" )");
				}

				// 逻辑运算符
				if (i != allConditions.size() - 1) {
					conditionStr.append(" "
							+ condition.getSqlLogicOperateType().getDispName());
				}
			}
		} else {
			return "";
		}

		return conditionStr.toString();
	}

	/**
	 * Button pressed.
	 *
	 * @author mqfdy
	 * @param buttonId
	 *            the button id
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void buttonPressed(int buttonId) {
//		if (buttonId == APPLY_ID) {
//			queryDesignPage.validateInput();
//		} else {
			super.buttonPressed(buttonId);
//		}

	}

	/**
	 * Gets the business class.
	 *
	 * @author mqfdy
	 * @return the business class
	 * @Date 2018-09-03 09:00
	 */
	public BusinessClass getBusinessClass() {
		return businessClass;
	}

	/**
	 * Gets the query design page.
	 *
	 * @author mqfdy
	 * @return the query design page
	 * @Date 2018-09-03 09:00
	 */
	public QueryDesignPage getQueryDesignPage() {
		return queryDesignPage;
	}

	/**
	 * Gets the parent page.
	 *
	 * @author mqfdy
	 * @return the parent page
	 * @Date 2018-09-03 09:00
	 */
	public DataSourceBusinessPage getParentPage() {
		return parentPage;
	}

}
