package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 实体操作.
 *
 * @author LQR
 */
public class BusinessOperation extends AbstractModelElement {

	/** The sys operation add. */
	public static String SYS_OPERATION_ADD = "add";
	
	/** The sys operation add displayname. */
	public static String SYS_OPERATION_ADD_DISPLAYNAME = "新增";

	/** The sys operation edit. */
	public static String SYS_OPERATION_EDIT = "update";
	
	/** The sys operation edit displayname. */
	public static String SYS_OPERATION_EDIT_DISPLAYNAME = "编辑";

	/** The sys operation delete. */
	public static String SYS_OPERATION_DELETE = "delete";
	
	/** The sys operation delete displayname. */
	public static String SYS_OPERATION_DELETE_DISPLAYNAME = "删除";

	/** The sys operation get. */
	public static String SYS_OPERATION_GET = "get";
	
	/** The sys operation get displayname. */
	public static String SYS_OPERATION_GET_DISPLAYNAME = "获取详情";

	/** The sys operation query. */
	public static String SYS_OPERATION_QUERY = "query";
	
	/** The sys operation query displayname. */
	public static String SYS_OPERATION_QUERY_DISPLAYNAME = "查询列表";

	/** 操作类型. */
	public static String OPERATION_TYPE_CUSTOM = "custom";// 自定义
	
	/** The operation type standard. */
	public static String OPERATION_TYPE_STANDARD = "standard";// 系统标准

	/** 所属业务实体. */
	private BusinessClass belongBusinessClass;

	/** 返回的数据类型. */
	private String returnDataType;

	/** 事务类型. */
	private String transactionType;

	/** 错误提示. */
	private String errorMessage;

	/** 操作类型 系统标准操作或是自定义操作. */
	private String operationType;

	/** 操作参数列表. */
	private List<OperationParam> operationParams;

	/**
	 * Instantiates a new business operation.
	 *
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 * @param operationType
	 *            the operation type
	 */
	public BusinessOperation(String name, String displayName,
			String operationType) {
		super(name, displayName);
		this.operationType = operationType;
	}

	/**
	 * Instantiates a new business operation.
	 */
	public BusinessOperation() {
		operationParams = new ArrayList<OperationParam>(10);
	}

	/**
	 * Instantiates a new business operation.
	 *
	 * @param operationElement
	 *            the operation element
	 */
	@SuppressWarnings("unchecked")
	public BusinessOperation(Element operationElement) {

		initBasicAttributes(operationElement);

		// 取得operation的类型
		String operationType = operationElement.attributeValue("operationType");
		setOperationType(operationType);

		operationParams = new ArrayList<OperationParam>(10);

		// 获取Operation节点下的OrderNum，ReturnDataType，TransactionType，ErrorMessage，Remark子节点值
		String orderNumValue = operationElement.elementTextTrim("OrderNum");
		String returnDataTypeValue = operationElement
				.elementTextTrim("ReturnDataType");
		String transactionTypeValue = operationElement
				.elementTextTrim("TransactionType");
		String errorMessageValue = operationElement
				.elementTextTrim("ErrorMessage");
		setOrderNum(StringUtil.string2Int(orderNumValue));
		setReturnDataType(returnDataTypeValue);
		setTransactionType(transactionTypeValue);
		setErrorMessage(errorMessageValue);

		// 获取Operation节点下Params节点
		Element paramsElement = operationElement.element("Params");
		if (paramsElement != null) {
			List<Element> paramElements = paramsElement.elements("Param");// 获取Params节点下的Param节点集合
			if (paramElements != null) {
				for (Iterator<Element> iter = paramElements.iterator(); iter
						.hasNext();) {
					Element paramElement = iter.next();// Param节点
					OperationParam operationParam = new OperationParam(
							paramElement);
					addOperationParam(operationParam);
				}
			}

		}
	}

	/**
	 * @param operationsElement
	 * @return
	 */
	public Element generateXmlElement(Element operationsElement) {

		Element operationElement = operationsElement.addElement("Operation");// Operation节点
		generateBasicAttributes(operationElement);

		if (OPERATION_TYPE_STANDARD.equalsIgnoreCase(getOperationType())) {// 标准类型操作
			operationElement.addAttribute("operationType",
					OPERATION_TYPE_STANDARD);
		} else {// 自定义类型操作
				// 在Operation节点下添加OrderNum，ReturnDataType，TransactionType，ErrorMessage
			operationElement.addAttribute("operationType",
					OPERATION_TYPE_CUSTOM);
			operationElement.addElement("OrderNum").addText(getOrderNum() + "");
			operationElement.addElement("ReturnDataType").addText(
					StringUtil.convertNull2EmptyStr(getReturnDataType()));
			operationElement.addElement("TransactionType").addText(
					StringUtil.convertNull2EmptyStr(getTransactionType()));
			operationElement.addElement("ErrorMessage").addText(
					StringUtil.convertNull2EmptyStr(getErrorMessage()));
		}

		// 在Operation节点下创建Params节点，没有就不创建
		List<OperationParam> params = getOperationParams();
		if (params != null && !params.isEmpty()) {
			Element paramsElement = operationElement.addElement("Params");// Params节点
			// operationElement.addContent(paramsElement);
			for (OperationParam param : params) {
				param.generateXmlElement(paramsElement);
				// paramsElement.addContent(paramElement);
			}
		}
		return operationElement;
	}

	/**
	 * @param dest
	 */
	public void copy(AbstractModelElement dest) {
		((BusinessOperation) dest).returnDataType = this.returnDataType;
		((BusinessOperation) dest).transactionType = this.transactionType;
		((BusinessOperation) dest).errorMessage = this.errorMessage;
		((BusinessOperation) dest).operationType = this.operationType;
		((BusinessOperation) dest).operationParams = this.operationParams;
	}

	/**
	 * @return
	 */
	public BusinessOperation clone() {
		BusinessOperation operation = new BusinessOperation();
		super.copy(operation);
		copy(operation);
		return operation;
	}

	/**
	 * @return
	 */
	public BusinessOperation cloneChangeId() {
		BusinessOperation operation = new BusinessOperation();
		super.copyChangeId(operation);
		copy(operation);
		return operation;
	}

	/**
	 * Gets the belong business class.
	 *
	 * @author mqfdy
	 * @return the belong business class
	 * @Date 2018-09-03 09:00
	 */
	public BusinessClass getBelongBusinessClass() {
		return belongBusinessClass;
	}

	/**
	 * Gets the standard operations.
	 *
	 * @author mqfdy
	 * @return the standard operations
	 * @Date 2018-09-03 09:00
	 */
	public static List<BusinessOperation> getStandardOperations() {
		List<BusinessOperation> list = new ArrayList<BusinessOperation>();
		list.add(new BusinessOperation(SYS_OPERATION_ADD,
				SYS_OPERATION_ADD_DISPLAYNAME, OPERATION_TYPE_STANDARD));
		list.add(new BusinessOperation(SYS_OPERATION_EDIT,
				SYS_OPERATION_EDIT_DISPLAYNAME, OPERATION_TYPE_STANDARD));
		list.add(new BusinessOperation(SYS_OPERATION_DELETE,
				SYS_OPERATION_DELETE_DISPLAYNAME, OPERATION_TYPE_STANDARD));
		list.add(new BusinessOperation(SYS_OPERATION_GET,
				SYS_OPERATION_GET_DISPLAYNAME, OPERATION_TYPE_STANDARD));
		list.add(new BusinessOperation(SYS_OPERATION_QUERY,
				SYS_OPERATION_QUERY_DISPLAYNAME, OPERATION_TYPE_STANDARD));
		return list;
	}

	/**
	 * Sets the belong business class.
	 *
	 * @author mqfdy
	 * @param belongBusinessClass
	 *            the new belong business class
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongBusinessClass(BusinessClass belongBusinessClass) {
		this.belongBusinessClass = belongBusinessClass;
	}

	/**
	 * Adds the operation param.
	 *
	 * @author mqfdy
	 * @param operationParam
	 *            the operation param
	 * @Date 2018-09-03 09:00
	 */
	public void addOperationParam(OperationParam operationParam) {
		operationParams.add(operationParam);
	}

	/**
	 * Gets the return data type.
	 *
	 * @author mqfdy
	 * @return the return data type
	 * @Date 2018-09-03 09:00
	 */
	public String getReturnDataType() {
		return returnDataType;
	}

	/**
	 * Sets the return data type.
	 *
	 * @author mqfdy
	 * @param returnDataType
	 *            the new return data type
	 * @Date 2018-09-03 09:00
	 */
	public void setReturnDataType(String returnDataType) {
		this.returnDataType = returnDataType;
	}

	/**
	 * Gets the transaction type.
	 *
	 * @author mqfdy
	 * @return the transaction type
	 * @Date 2018-09-03 09:00
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets the transaction type.
	 *
	 * @author mqfdy
	 * @param transactionType
	 *            the new transaction type
	 * @Date 2018-09-03 09:00
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Gets the error message.
	 *
	 * @author mqfdy
	 * @return the error message
	 * @Date 2018-09-03 09:00
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @author mqfdy
	 * @param errorMessage
	 *            the new error message
	 * @Date 2018-09-03 09:00
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the operation params.
	 *
	 * @author mqfdy
	 * @return the operation params
	 * @Date 2018-09-03 09:00
	 */
	public List<OperationParam> getOperationParams() {
		return operationParams;
	}

	/**
	 * Sets the operation params.
	 *
	 * @author mqfdy
	 * @param operationParams
	 *            the new operation params
	 * @Date 2018-09-03 09:00
	 */
	public void setOperationParams(List<OperationParam> operationParams) {
		this.operationParams = operationParams;
	}

	/**
	 * Gets the operation type.
	 *
	 * @author mqfdy
	 * @return the operation type
	 * @Date 2018-09-03 09:00
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * Sets the operation type.
	 *
	 * @author mqfdy
	 * @param operationType
	 *            the new operation type
	 * @Date 2018-09-03 09:00
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongBusinessClass.getOperationPackage();
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}
}
