package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 实体操作
 * 
 * @author LQR
 * 
 */
public class BusinessOperation extends AbstractModelElement {

	public static String SYS_OPERATION_ADD = "add";
	public static String SYS_OPERATION_ADD_DISPLAYNAME = "新增";

	public static String SYS_OPERATION_EDIT = "update";
	public static String SYS_OPERATION_EDIT_DISPLAYNAME = "编辑";

	public static String SYS_OPERATION_DELETE = "delete";
	public static String SYS_OPERATION_DELETE_DISPLAYNAME = "删除";

	public static String SYS_OPERATION_GET = "get";
	public static String SYS_OPERATION_GET_DISPLAYNAME = "获取详情";

	public static String SYS_OPERATION_QUERY = "query";
	public static String SYS_OPERATION_QUERY_DISPLAYNAME = "查询列表";

	/**
	 * 操作类型
	 */
	public static String OPERATION_TYPE_CUSTOM = "custom";// 自定义
	public static String OPERATION_TYPE_STANDARD = "standard";// 系统标准

	/**
	 * 所属业务实体
	 */
	private BusinessClass belongBusinessClass;

	/**
	 * 返回的数据类型
	 */
	private String returnDataType;

	/**
	 * 事务类型
	 */
	private String transactionType;

	/**
	 * 错误提示
	 */
	private String errorMessage;

	/**
	 * 操作类型 系统标准操作或是自定义操作
	 */
	private String operationType;

	/**
	 * 操作参数列表
	 */
	private List<OperationParam> operationParams;

	public BusinessOperation(String name, String displayName,
			String operationType) {
		super(name, displayName);
		this.operationType = operationType;
	}

	public BusinessOperation() {
		operationParams = new ArrayList<OperationParam>(10);
	}

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

	public void copy(AbstractModelElement dest) {
		((BusinessOperation) dest).returnDataType = this.returnDataType;
		((BusinessOperation) dest).transactionType = this.transactionType;
		((BusinessOperation) dest).errorMessage = this.errorMessage;
		((BusinessOperation) dest).operationType = this.operationType;
		((BusinessOperation) dest).operationParams = this.operationParams;
	}

	public BusinessOperation clone() {
		BusinessOperation operation = new BusinessOperation();
		super.copy(operation);
		copy(operation);
		return operation;
	}

	public BusinessOperation cloneChangeId() {
		BusinessOperation operation = new BusinessOperation();
		super.copyChangeId(operation);
		copy(operation);
		return operation;
	}

	public BusinessClass getBelongBusinessClass() {
		return belongBusinessClass;
	}

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

	public void setBelongBusinessClass(BusinessClass belongBusinessClass) {
		this.belongBusinessClass = belongBusinessClass;
	}

	public void addOperationParam(OperationParam operationParam) {
		operationParams.add(operationParam);
	}

	public String getReturnDataType() {
		return returnDataType;
	}

	public void setReturnDataType(String returnDataType) {
		this.returnDataType = returnDataType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<OperationParam> getOperationParams() {
		return operationParams;
	}

	public void setOperationParams(List<OperationParam> operationParams) {
		this.operationParams = operationParams;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public AbstractModelElement getParent() {
		return this.belongBusinessClass.getOperationPackage();
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}
}
