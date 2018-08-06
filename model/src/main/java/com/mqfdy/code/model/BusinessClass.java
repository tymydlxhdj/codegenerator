package com.mqfdy.code.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 业务实体
 * 
 * @author mqfdy
 * 
 */
public class BusinessClass extends AbstractModelElement {

	private ModelPackage belongPackage;

	/**
	 * 是否抽象实体(当前版本为false)
	 */
	private boolean IsAbstract = false;

	/**
	 * 是否配置了权限
	 */
	// private boolean iscRight = false;

	/**
	 * 对应数据库表名
	 */
	private String tableName;

	/**
	 * 属性列表
	 */
	private List<Property> properties;

	/**
	 * 属性分组列表
	 */
	private List<PropertyGroup> groups;

	/**
	 * 操作列表
	 */
	private List<BusinessOperation> operations;

	/**
	 * 未注册到权限系统，该字段不存储在om文件
	 */
	private boolean hasnotRegist = true;
	
	/**
	 * 实体状态列表
	 */
	private List<BEStatus> statuses;

	/**
	 * 实体权限列表
	 */
	private List<BEPermission> permissions;

	/**
	 * 版本信息
	 */
	private VersionInfo versionInfo;

	/**
	 * 固化包（状态列表）
	 */
	private SolidifyPackage statusPackage;

	/**
	 * 固化包（属性列表）
	 */
	private SolidifyPackage propertyPackage;

	/**
	 * 固化包（操作列表）
	 */
	private SolidifyPackage operationPackage;

	/**
	 * 固化包（权限列表）
	 */
	private SolidifyPackage permissionPackage;

	public BusinessClass() {
		super();
		initInternalObjects();

	}

	public BusinessClass(String id) {
		super(id);
		initInternalObjects();

	}

	public BusinessClass(String name, String displayName) {
		super(name, displayName);
		initInternalObjects();

	}

	public BusinessClass(ModelPackage belongPackage, String name,
			String displayName) {
		super(name, displayName);
		initInternalObjects();

	}

	/**
	 * 通过 XML 构造 BusinessClass
	 * 
	 * @param businessClassElement
	 */
	@SuppressWarnings("unchecked")
	public BusinessClass(Element businessClassElement) {

		initInternalObjects();

		initBasicAttributes(businessClassElement);

		// 组装BusinessEntity对象中的VersionInfo对象 ok
		Element versionInfoElement = businessClassElement
				.element("VersionInfo");
		if (versionInfoElement != null) {
			VersionInfo versionInfo = new VersionInfo(versionInfoElement);
			setVersionInfo(versionInfo);
		}

		// 组装BusinessClass对象中TableName，Remark，ISCRight，IsAbstract属性
		String tableName = businessClassElement.elementTextTrim("TableName");
		// String isAbstract =
		// businessClassElement.getChildTextTrim("IsAbstract");
		// String iSCRight = businessClassElement.getChildTextTrim("ISCRight");

		setTableName(tableName);
		// setIscRight(StringUtil.string2Boolean(iSCRight));

		// 组装BusinessEntity里的List<PropertyGroup> groups
		Element propertyGroupsElement = businessClassElement.element("Groups");
		if (propertyGroupsElement != null) {
			List<Element> groupElements = propertyGroupsElement
					.elements("Group");
			if (groupElements != null) {
				for (Iterator<Element> iter = groupElements.iterator(); iter
						.hasNext();) {
					Element groupElement = iter.next();// 获取Status节点
					PropertyGroup group = new PropertyGroup(groupElement);
					addGroup(group);// BEStatus对象加入到集合中
				}
			}
		}

		// 组装BusinessEntity里的List<Property> ok
		Element propertiesElement = businessClassElement.element("Properties");
		if (propertiesElement != null) {
			List<Element> propertyElements = propertiesElement
					.elements("Property");
			if (propertyElements != null) {
				for (Iterator<Element> iter = propertyElements.iterator(); iter
						.hasNext();) {
					Element propertyElement = iter.next();
					Property property = null;
					boolean isPrimaryKey = StringUtil
							.string2Boolean(propertyElement
									.elementTextTrim("IsPrimaryKey"));// getChildTextTrim
					boolean persistence = StringUtil
							.string2Boolean(propertyElement
									.elementTextTrim("Persistence"));
					if (persistence && isPrimaryKey) {// 是PersistenceProperty类型
						property = new PKProperty(propertyElement);
					} else if (persistence) {
						property = new PersistenceProperty(propertyElement);
					} else {
						property = new Property(propertyElement);
					}
					if (property.getGroup() != null) {
						PropertyGroup group = getGroupById(property.getGroup()
								.getId());
						if (group != null)
							property.setGroup(group);
					}
					property.setParent(this);
					addProperty(property);
				}
			}
		}

		// 获取获取BusinessClass节点下ExtendAttributies节点
		Element extendAttributiesElement = businessClassElement.element("ExtendAttributies");
		// 获取PersistencePloyParams节点下的Param节点列表
		if (extendAttributiesElement != null) {
			List<Element> paramElementList = extendAttributiesElement.elements("Param");
			for (Iterator<Element> iterator2 = paramElementList.iterator(); iterator2.hasNext();) {
				Element paramElement = iterator2.next();// Param节点
				// 获取Param节点下的name和value值
				String key = paramElement.elementTextTrim("name");
				String value = paramElement.elementTextTrim("value");
				this.getExtendAttributies().put(key, value);
			}
		}
		
		
		
		
		// 组装BusinessEntity里的List<BusinessOperation> ok
		Element operationsElement = businessClassElement.element("Operations");
		if (operationsElement != null) {
			List<Element> operationElements = operationsElement
					.elements("Operation");
			if (operationElements != null) {
				for (Iterator<Element> iter = operationElements.iterator(); iter
						.hasNext();) {
					Element operationElement = iter.next();// 获取Status节点
					BusinessOperation operation = new BusinessOperation(
							operationElement);
					operation.setBelongBusinessClass(this);
					addOperation(operation);
				}
			}
		}
	}

	public Element generateXmlElement(Element bcsElement) {
		Element bcElement = bcsElement.addElement("BusinessClass");
		generateBasicAttributes(bcElement);
		VersionInfo info = getVersionInfo();
		if (info != null) {
			info.generateXmlElement(bcElement);
		}
		// 在BusinessEntity节点下创建TableName节点
		bcElement.addElement("TableName").addText(
				StringUtil.convertNull2EmptyStr(getTableName()));
		// 在BusinessEntity节点下创建IsAbstract节点
		bcElement.addElement("IsAbstract").addText(
				isAbstract() ? "true" : "false");
		List<BEStatus> statuses = getStatuses();// 遍历
		if (statuses != null && !statuses.isEmpty()) {
			Element statusesElement = bcElement.addElement("Statuses");// Statuses节点
			for (BEStatus status : statuses) {
				status.generateXmlElement(statusesElement);
			}
		}

		List<BEPermission> permissions = getPermissions();// 遍历
		if (permissions != null && !permissions.isEmpty()) {
			Element permissionsElement = bcElement.addElement("Permissions");// Statuses节点
			// bcElement.addContent(permissionsElement);
			for (BEPermission permission : permissions) {
				List<BEPermissionItem> items = permission.getPermissionItems();
				for (BEPermissionItem item : items) {
					item.generateXmlElement(permission, permissionsElement);
					// permissionsElement.addContent(permissionElement);
				}
			}
		}
		// 在BusinessClass节点下创建Groups节点及其Group节点，没有就不创建
		List<PropertyGroup> groups = getGroups();
		if (groups != null && !groups.isEmpty()) {
			Element groupsElement = bcElement.addElement("Groups");// Groups节点
			for (PropertyGroup group : groups) {
				group.generateXmlElement(groupsElement);// 创建Group节点
			}
		}

		List<Property> properties = getProperties();
		if (properties != null && !properties.isEmpty()) {
			Element propertiesElement = bcElement.addElement("Properties");// Properties节点
			// bcElement.addContent(propertiesElement);
			for (Property property : properties) {// 遍历
				property.generateXmlElement(propertiesElement);// 创建Property节点
				// propertiesElement.addContent(propertyElement);
			}
		}

		Map<String, Object> params = this.getExtendAttributies();
		
		if (params != null) {
			Element extendAttributiesParamsElement = bcElement.addElement("ExtendAttributies");
			Map<String, String> param = new HashMap<String, String>();
			
			for(Entry<String, Object> entry: params.entrySet()) {
				if(entry.getValue() != null) {
					param.put(entry.getKey(), entry.getValue().toString());
				}
			}
			
			this.generateParam(extendAttributiesParamsElement, param);
		}
		
		
		List<BusinessOperation> operations = getOperations();
		if (operations != null && !operations.isEmpty()) {
			Element operationsElement = bcElement.addElement("Operations");// Properties节点
			// bcElement.addContent(operationsElement);
			for (BusinessOperation operation : operations) {// 遍历
				operation.generateXmlElement(operationsElement);// 创建Property节点
				// operationsElement.addContent(operationElement);
			}
		}
		return bcElement;
	}
	
	
	
	//构造一个dom4结构的xml样式
	public Document generateXmlElement() {
		Document document = DocumentHelper.createDocument();
		//Element bcsElement=null;
		Element bcElement = document.addElement("BusinessClass");
		
		generateBasicAttributes(bcElement);
		VersionInfo info = getVersionInfo();
		if (info != null) {
			info.generateXmlElement(bcElement);
		}
		// 在BusinessEntity节点下创建TableName节点
		bcElement.addElement("TableName").addText(
				StringUtil.convertNull2EmptyStr(getTableName()));
		// 在BusinessEntity节点下创建IsAbstract节点
		bcElement.addElement("IsAbstract").addText(
				isAbstract() ? "true" : "false");
		List<BEStatus> statuses = getStatuses();// 遍历
		if (statuses != null && !statuses.isEmpty()) {
			Element statusesElement = bcElement.addElement("Statuses");// Statuses节点
			for (BEStatus status : statuses) {
				status.generateXmlElement(statusesElement);
			}
		}

		List<BEPermission> permissions = getPermissions();// 遍历
		if (permissions != null && !permissions.isEmpty()) {
			Element permissionsElement = bcElement.addElement("Permissions");// Statuses节点
			// bcElement.addContent(permissionsElement);
			for (BEPermission permission : permissions) {
				List<BEPermissionItem> items = permission.getPermissionItems();
				for (BEPermissionItem item : items) {
					item.generateXmlElement(permission, permissionsElement);
					// permissionsElement.addContent(permissionElement);
				}
			}
		}
		// 在BusinessClass节点下创建Groups节点及其Group节点，没有就不创建
		List<PropertyGroup> groups = getGroups();
		if (groups != null && !groups.isEmpty()) {
			Element groupsElement = bcElement.addElement("Groups");// Groups节点
			for (PropertyGroup group : groups) {
				group.generateXmlElement(groupsElement);// 创建Group节点
			}
		}

		List<Property> properties = getProperties();
		if (properties != null && !properties.isEmpty()) {
			Element propertiesElement = bcElement.addElement("Properties");// Properties节点
			// bcElement.addContent(propertiesElement);
			for (Property property : properties) {// 遍历
				property.generateXmlElement(propertiesElement);// 创建Property节点
				// propertiesElement.addContent(propertyElement);
			}
		}

		Map<String, Object> params = this.getExtendAttributies();
		
		if (params != null) {
			Element extendAttributiesParamsElement = bcElement.addElement("ExtendAttributies");
			Map<String, String> param = new HashMap<String, String>();
			
			for(Entry<String, Object> entry: params.entrySet()) {
				if(entry.getValue() != null) {
					param.put(entry.getKey(), entry.getValue().toString());
				}
			}
			
			this.generateParam(extendAttributiesParamsElement, param);
		}
		
		
		List<BusinessOperation> operations = getOperations();
		if (operations != null && !operations.isEmpty()) {
			Element operationsElement = bcElement.addElement("Operations");// Properties节点
			// bcElement.addContent(operationsElement);
			for (BusinessOperation operation : operations) {// 遍历
				operation.generateXmlElement(operationsElement);// 创建Property节点
				// operationsElement.addContent(operationElement);
			}
		}
		return document;
	}
	//针对Document XML文件转字符串
	public String toXml(){
        String faceXml = "";
        
        OutputFormat format = new OutputFormat(" ",true,"UTF-8"); 
        StringWriter sw = new StringWriter(); 

        XMLWriter writer = new XMLWriter(sw,format); 
        try {
			writer.write(this.generateXmlElement());
			StringBuffer sb = sw.getBuffer();
			faceXml = sb.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sw!=null){
				try {
					sw.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        

		return faceXml;
	}
	

	/**
	 * 初始化内部列表对象
	 */
	private void initInternalObjects() {
		properties = new ArrayList<Property>(50);
		operations = new ArrayList<BusinessOperation>(50);
		statuses = new ArrayList<BEStatus>(50);
		permissions = new ArrayList<BEPermission>(50);
		groups = new ArrayList<PropertyGroup>(10);
	}

	public int getPriority() {
		return IModelElement.PRIORITY_BUSINESSCLASS;
	}

	public boolean isAbstract() {

		return IsAbstract;
	}

	/**
	 * 暂时忽略此功能 public void setAbstract(boolean isAbstract) { IsAbstract =
	 * isAbstract; }
	 */

	public String getTableName() {
		return tableName;
	}

	
	public SolidifyPackage getPropertyPackage() {
		if (propertyPackage == null) {
			propertyPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_PROPERTY,
					SolidifyPackage.SOLIDIFY_PACKAGE_PROPERTY_DISPLAYNAME);
			propertyPackage.setStereotype(getStereotype());
		}
		return propertyPackage;
	}

	public SolidifyPackage getOperationPackage() {
		if (operationPackage == null) {
			operationPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_OPERATION,
					SolidifyPackage.SOLIDIFY_PACKAGE_OPERATION_DISPLAYNAME);
			operationPackage.setStereotype(getStereotype());
		}
		return operationPackage;
	}

	public SolidifyPackage getPermissionPackage() {
		if (permissionPackage == null) {
			permissionPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_PERMISSION,
					SolidifyPackage.SOLIDIFY_PACKAGE_PERMISSION_DISPLAYNAME);
			permissionPackage.setStereotype(getStereotype());
		}
		return permissionPackage;
	}

	public SolidifyPackage getStatusPackage() {
		if (statusPackage == null) {
			statusPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_STATUS,
					SolidifyPackage.SOLIDIFY_PACKAGE_STATUS_DISPLAYNAME);
			statusPackage.setStereotype(getStereotype());
		}
		return statusPackage;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<BEStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<BEStatus> statuses) {
		this.statuses = statuses;
	}

	public void addStatus(BEStatus status) {
		this.statuses.add(status);
	}
	
	public void addStatus(List<BEStatus> status) {
		this.statuses.addAll(status);
	}

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void addProperty(Property property) {
		this.properties.add(property);
	}

	public List<BusinessOperation> getOperations() {
		return operations;
	}

	public void addOperation(BusinessOperation operation) {
		this.operations.add(operation);
	}

	public List<BEPermission> getPermissions() {
		return permissions;
	}

	public void addPermission(BEPermission permission) {
		this.permissions.add(permission);
	}

	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	public boolean isHasnotRegist() {
		return hasnotRegist;
	}

	public void setHasnotRegist(boolean hasnotRegist) {
		this.hasnotRegist = hasnotRegist;
	}

	public List<PropertyGroup> getGroups() {
		return groups;
	}

	public void addGroup(PropertyGroup group) {
		this.groups.add(group);
	}

	public PropertyGroup getGroupById(String groupId) {
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getId().equals(groupId)) {
				return groups.get(i);
			}
		}
		return null;
	}

	public String[] getPropertiesDisplayName() {
		String[] results = new String[properties.size()];
		for (int i = 0; i < properties.size(); i++) {
			results[i] = properties.get(i).getDisplayName();
		}
		return results;
	}

	public AbstractModelElement getParent() {
		return this.belongPackage.getEntityPackage();
	}

	public List<SolidifyPackage> getChildren() {
		List<SolidifyPackage> children = new ArrayList<SolidifyPackage>();

		if (this.properties != null && this.properties.size() > 0) {
			children.add(getPropertyPackage());
		}

		if (this.operations != null && this.operations.size() > 0) {
			children.add(getOperationPackage());
		}

		if (this.statuses != null && this.statuses.size() > 0) {
			children.add(getStatusPackage());
		}

		return children;
	}

	/**
	 * 判断业务实体是否包含主键属性
	 */
	public boolean hasPkProperty() {
		boolean flag = false;
		for (Property pro : getProperties()) {
			if (pro instanceof PKProperty) {
				flag = true;
			}
		}
		return flag;
	}

	public Property getPkProperty() {
		Property result = null;
		for (Property pro : getProperties()) {
			if (pro instanceof PKProperty) {
				result = pro;
				break;
			}
		}
		return result;
	}

	public String getPkPropertyKey() {
		return getPkProperty().getName();
	}

	public String getFullName() {
		if (this.getParent() != null) {
			return this.getParent().getFullName() + ".po." + this.getName();
		} else {
			return this.getName();
		}

	}

	protected void copy(AbstractModelElement dest) {
		super.copy(dest);
		BusinessClass destBc = ((BusinessClass) dest);
		destBc.tableName = this.tableName;
		destBc.initInternalObjects();
		for (Property property : this.properties) {
			Property pro = property.clone();
			pro.setParent(destBc);
			destBc.addProperty(pro);
		}

		for (BusinessOperation operation : this.operations) {
			BusinessOperation op = operation.clone();
			op.setBelongBusinessClass(destBc);
			destBc.addOperation(op);
		}

		destBc.setBelongPackage(getBelongPackage());

	}

	public BusinessClass clone() {
		BusinessClass bc = new BusinessClass();
		copy(bc);
		return bc;
	}

	public BusinessClass cloneChangeId() {
		BusinessClass bc = new BusinessClass();
		copyChangeId(bc);
		return bc;
	}

	protected void copyChangeId(AbstractModelElement dest) {
		super.copyChangeId(dest);
		BusinessClass destBc = ((BusinessClass) dest);
		destBc.tableName = this.tableName;
		destBc.initInternalObjects();
		for (Property property : this.properties) {
			Property pro = property.cloneChangeId();
			pro.setParent(destBc);
			destBc.addProperty(pro);
		}

		for (BusinessOperation operation : this.operations) {
			BusinessOperation op = operation.cloneChangeId();
			op.setBelongBusinessClass(destBc);
			destBc.addOperation(op);
		}
		for (PropertyGroup group : this.groups) {
			destBc.addGroup(group);
		}

		destBc.setBelongPackage(getBelongPackage());

	}
}
