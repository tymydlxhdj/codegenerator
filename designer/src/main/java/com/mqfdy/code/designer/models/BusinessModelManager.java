package com.mqfdy.code.designer.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.DocumentException;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.OperationParam;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.VersionInfo;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.resource.BomManager;

/**
 * 业务对象模型管理器,负责管理
 * 
 * @author mqfdy
 * 
 */
public class BusinessModelManager implements IPropertyChangeListener {

	public static final String PACKAGE_NAME_PREFIX = "package";
	public static final String DIAGRAM_NAME_PREFIX = "diagram";
	public static final String BUSINESSCLASS_NAME_PREFIX = "BusinessClass";
	public static final String DTO_NAME_PREFIX = "Dto";
	public static final String ENUMERATION_NAME_PREFIX = "Enumeration";
	public static final String ASSOCIATION_NAME_PREFIX = "Assoiation";

	/**
	 * 当前编辑的业务模型对象;
	 */
	private BusinessObjectModel businessObjectModel;

	private String filePath;

	/**
	 * 被当前编辑的业务对象模型引用的知识库(其他模块)的业务对象模型
	 */
	private List<BusinessObjectModel> repositoryModels = new ArrayList<BusinessObjectModel>();

	/**
	 * 业务模型监听器列表
	 */
	private List<BusinessModelListener> listeners = new ArrayList<BusinessModelListener>();
	private BusinessModelDiagramEditor businessModelDiagramEditor;

	public BusinessObjectModel getBusinessObjectModel() {
		return businessObjectModel;
	}

	/**
	 * 当前打开的文件路径
	 * @return
	 */
	public String getPath() {
		return filePath;
	}

	public List<BusinessObjectModel> getRepositoryModels() {
		return this.repositoryModels;
	}

	public void addRepsitoryModel(BusinessObjectModel businessObjectModel) {
		repositoryModels.add(businessObjectModel);
	}

	public void updateRepsitoryModel(BusinessObjectModel businessObjectModel) {
		if (repositoryModels == null || businessObjectModel == null) {
			return;
		} else {
			for (BusinessObjectModel model : repositoryModels) {
				if (model != null
						&& model.getId().equals(businessObjectModel.getId())) {
					repositoryModels.remove(model);
					repositoryModels.remove(model);
					addRepsitoryModel(businessObjectModel);
				}
			}
		}
	}

	public void updateReferenceObjects() {
		BusinessModelUtil.assembReferenceObject(businessObjectModel,filePath);
	}

	public BusinessObjectModel getBusinessObjectModel(String filePath) {
		this.filePath = filePath;
		this.setBusinessModelDiagramEditor(businessModelDiagramEditor);
		try {
			businessObjectModel = BomManager.xml2Model(filePath);
			BusinessModelUtil.assembReferenceObject(businessObjectModel,filePath);
		} catch (DocumentException e) {
//			Logger.log(e);
		}
		return businessObjectModel;
	}
	public BusinessObjectModel getBusinessObjectModel(String filePath,
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		this.filePath = filePath;
		this.setBusinessModelDiagramEditor(businessModelDiagramEditor);
		try {
			businessObjectModel = BomManager.xml2Model(filePath);
		} catch (DocumentException e) {
//			Logger.log(e);
		}
		return businessObjectModel;
	}
	
	/**
	 * 获取上次关闭前打开的Diagram
	 * @return
	 */
	public Diagram getDefaultDiagram() {
		if(businessObjectModel == null)
			return null;
		for(Diagram dia : businessObjectModel.getDiagrams()){
			if(dia.isDefault())
				return dia;
		}
		return null;
	}
	/**
	 * 添加监听器
	 * 
	 * @param listener
	 */
	public void addBusinessModelListener(BusinessModelListener listener) {
		listeners.add(listener);
	}

	/**
	 * 移除监听器
	 * 
	 * @param listener
	 */
	public void removeBusinessModelListener(BusinessModelListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 当业务对象模型有变更时调用改方法来触发监听
	 * 
	 * @param changedModelElement
	 */
	public void businessObjectModelChanged(BusinessModelEvent businessModelEvent) {

		int enentType = businessModelEvent.getEventType();
		AbstractModelElement modelElement = businessModelEvent
				.getModelElementChanged();
		switch (enentType) {
		case BusinessModelEvent.MODEL_ELEMENT_ADD:
			// 更新模型中的列表
			this.businessObjectModel.addModelElement(modelElement);
			// 发布元素新增事件
			businessModelElementAdd(modelElement);
			break;

		case BusinessModelEvent.MODEL_ELEMENT_DELETE:
			// 更新模型中的列表
			if (modelElement instanceof BusinessClass
					|| modelElement instanceof ReferenceObject) {
				BusinessClass tempBc = null;
				if (modelElement instanceof ReferenceObject) {
					tempBc = (BusinessClass) ((ReferenceObject) modelElement)
							.getReferenceObject();
				} else {
					tempBc = (BusinessClass) modelElement;
				}
				List<Association> assoications = getAssociationsByBusinessClass(tempBc);
				if (assoications != null) {
					for (Association association : assoications) {
						this.businessObjectModel
								.removeModelElement(association);
						businessModelElementDelete(association);
					}
				}

				List<Inheritance> inheritances = getInheritancesByBusinessClass(tempBc);
				if (assoications != null) {
					for (Inheritance inheritance : inheritances) {
						this.businessObjectModel
								.removeModelElement(inheritance);
						businessModelElementDelete(inheritance);
					}
				}
			}
			// 删除连线
			List<LinkAnnotation> links = getLinksByModel(modelElement);
			if (links != null) {
				for (LinkAnnotation link : links) {
					this.businessObjectModel
							.removeModelElement(link);
					businessModelElementDelete(link);
				}
			}
			this.businessObjectModel.removeModelElement(modelElement);
			// 发布元素删除事件
			businessModelElementDelete(modelElement);
			break;

		case BusinessModelEvent.MODEL_ELEMENT_UPDATE:
			// 发布元素更新事件
			businessModelElementUpdate(modelElement);
			break;

		case BusinessModelEvent.REPOSITORY_MODEL_ADD:
			// 发布引用模型更新事件
			repositoryModelAdd(modelElement);
			break;

		case BusinessModelEvent.MODEL_ELEMENT_SAVE:
			// 发布事件
			businessModelElementSave(modelElement);
			break;
		}
	}

	/**
	 * 当业务对象模型元素新增时调用改方法来触发监听
	 * 
	 * @param modelElementChanged
	 */
	private void businessModelElementAdd(
			AbstractModelElement modelElementChanged) {
		Iterator<BusinessModelListener> iter;
		iter = listeners.iterator();
		while (iter.hasNext()) {
			iter.next().modelElementAdd(modelElementChanged);
		}
	}

	/**
	 * 当业务对象模型元素修改时调用改方法来触发监听
	 * 
	 * @param modelElementChanged
	 */
	private void businessModelElementUpdate(
			AbstractModelElement modelElementChanged) {
		Iterator<BusinessModelListener> iter;
		iter = listeners.iterator();
		while (iter.hasNext()) {
			iter.next().modelElementUpdate(modelElementChanged);
		}
	}

	private void businessModelElementSave(
			AbstractModelElement modelElementChanged) {
		Iterator<BusinessModelListener> iter;
		iter = listeners.iterator();
		while (iter.hasNext()) {
			iter.next().modelSave(modelElementChanged);
		}
	}

	/**
	 * 引用模型新增时调用该方法来触发监听
	 * 
	 * @param modelElementChanged
	 */
	private void repositoryModelAdd(AbstractModelElement modelElementChanged) {
		Iterator<BusinessModelListener> iter;
		iter = listeners.iterator();
		while (iter.hasNext()) {
			iter.next().repositoryModelAdd(modelElementChanged);
		}
	}
	
	/**
	 * 当业务对象模型元素删除时调用改方法来触发监听
	 * 
	 * @param modelElementChanged
	 */
	public void businessModelElementDelete(
			AbstractModelElement modelElementChanged) {
		Iterator<BusinessModelListener> iter;
		iter = listeners.iterator();
		while (iter.hasNext()) {
			iter.next().modelElementDelete(modelElementChanged);
		}
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		// fireBusinessModelChanged((AbstractModelElement)event.getSource());
	}

	/**
	 * 根据ID查询对象
	 * 
	 * @return
	 */
	public <T extends AbstractModelElement> T queryObjectById(T t, String id) {
		T tResult = (T) businessObjectModel.getModelElementById(t, id);
		return tResult;
	}

	/**
	 * 根据ID查询对象
	 * 
	 * @return
	 */
	public AbstractModelElement queryObjectById(String id) {
		if (id == null)
			return null;
		AbstractModelElement tResult = (AbstractModelElement) businessObjectModel
				.getModelElementById(id);
		return tResult;
	}

	
	
	/**
	 * 根据BusinessClass 获取与其相关的实体关系
	 * 
	 * @param businessClass
	 * @return
	 */
	public List<Association> getAssociationsByBusinessClass(
			BusinessClass businessClass) {
		List<Association> associations = businessObjectModel.getAssociations();
		List<Association> results = new ArrayList<Association>();
		if (associations == null || associations.size() < 1) {
			return results;
		}
		for (Association ass : associations) {
			if (ass != null && businessClass != null && ass.getClassA() != null
					&& ass.getClassB() != null) {
				if (ass.getClassA().getId().equals(businessClass.getId())
						|| ass.getClassB().getId()
								.equals(businessClass.getId())) {
					results.add(ass);
				}
			}
		}
		return results;
	}

	/**
	 * 根据BusinessClass 获取与其相关的连线
	 * 
	 * @param obj
	 * @return
	 */
	public List<LinkAnnotation> getLinksByModel(
			AbstractModelElement obj) {
		List<LinkAnnotation> annotations = businessObjectModel.getLinkAnnotations();
		List<LinkAnnotation> results = new ArrayList<LinkAnnotation>();
		if (annotations == null || annotations.size() < 1) {
			return results;
		}
		for (LinkAnnotation ass : annotations) {
			if (ass != null && obj != null && ass.getClassA() != null
					&& ass.getClassB() != null) {
				if (ass.getClassA().getId().equals(obj.getId())
						|| ass.getClassB().getId()
								.equals(obj.getId())) {
					results.add(ass);
				}
			}
		}
		return results;
	}
	
	/**
	 * 根据BusinessClass 获取与其相关的继承关系
	 * 
	 * @param businessClass
	 * @return
	 */
	public List<Inheritance> getInheritancesByBusinessClass(
			BusinessClass businessClass) {
		List<Inheritance> inheritances = businessObjectModel.getInheritances();
		List<Inheritance> results = new ArrayList<Inheritance>();
		if (inheritances == null || inheritances.size() < 1) {
			return results;
		}
		for (Inheritance inher : inheritances) {
			if (inher != null && businessClass != null
					&& inher.getParentClass() != null
					&& inher.getChildClass() != null) {
				if (inher.getParentClass().getId()
						.equals(businessClass.getId())
						|| inher.getChildClass().getId()
								.equals(businessClass.getId())) {
					results.add(inher);
				}
			}
		}
		return results;
	}

	public Map<String, Object> getRelationInfo(AbstractModelElement modelElement) {
		Map<String, Object> relationInfo = new HashMap<String, Object>();
		List<BusinessClass> bcs = businessObjectModel.getBusinessClasses();
		if (modelElement instanceof BusinessClass
				|| modelElement instanceof Enumeration) {
			for (BusinessClass bc : bcs) {
				List<Property> properties = bc.getProperties();
				for (Property prop : properties) {
					if (prop instanceof PersistenceProperty) {
						PropertyEditor editor = ((PersistenceProperty) prop)
								.getEditor();
						if (editor != null) {
							Map<String, String> editorParams = editor
									.getEditorParams();
							String id = "";
							if (editor.getDataSourceType() == PropertyEditor.DATASOURCE_TYPE_BUSINESS) {
								id = editorParams
										.get(PropertyEditor.PARAM_KEY_BUSINESSCLASS_ID);
								if (id != null
										&& id.endsWith(modelElement.getId())
										&& bc != modelElement) {
									String info = "该业务实体被业务实体 <"
											+ bc.getDisplayName() + "> 的属性 <"
											+ prop.getDisplayName()
											+ "> 的编辑器的业务数据来源所关联,请先将其删除。";
									relationInfo.put("info", info);
									relationInfo.put("BC", bc);
									return relationInfo;
								}
							} else if (editor.getDataSourceType() == PropertyEditor.DATASOURCE_TYPE_ENUM) {
								id = editorParams
										.get(PropertyEditor.PARAM_KEY_ENUMERATION_ID);
								if (id != null
										&& id.endsWith(modelElement.getId())
										&& bc != modelElement) {
									String info = "该枚举被业务实体 <"
											+ bc.getDisplayName() + "> 的属性 <"
											+ prop.getDisplayName()
											+ "> 的编辑器的枚举数据来源所关联,请先将其删除。";
									relationInfo.put("info", info);
									relationInfo.put("BC", bc);
									return relationInfo;
								}
							}
						}
					}
				}
			}
		}
		if (modelElement instanceof BusinessClass
				|| modelElement instanceof DataTransferObject) {
			for (BusinessClass bc : bcs) {
				List<BusinessOperation> operations = bc.getOperations();
				for (BusinessOperation oper : operations) {
					if (BusinessOperation.OPERATION_TYPE_CUSTOM.equals(oper
							.getOperationType())) {
						if (modelElement.getName().equals(
								oper.getReturnDataType())) {
							if (bc != modelElement) {
								String info = "该业务实体被业务实体 <"
										+ bc.getDisplayName() + "> 的操作 <"
										+ oper.getDisplayName()
										+ "> 的返回数据类型所关联,请先将其删除。";
								relationInfo.put("info", info);
								relationInfo.put("BC", bc);
								return relationInfo;
							}
						}
						List<OperationParam> operationParams = oper
								.getOperationParams();
						for (OperationParam param : operationParams) {
							if (modelElement.getName().equals(
									param.getDataType())) {
								if (bc != modelElement) {
									String info = "该业务实体被业务实体 <"
											+ bc.getDisplayName() + "> 的操作 <"
											+ oper.getDisplayName()
											+ "> 的参数 < "
											+ param.getDisplayName()
											+ "> 的数据类型所关联,请先将其删除。";
									relationInfo.put("info", info);
									relationInfo.put("BC", bc);
									return relationInfo;
								}
							}
						}
					}
				}
			}
		}
		return relationInfo;
	}

	@SuppressWarnings("rawtypes")
	public String getRelationInfoOfPackage(ModelPackage pack, String topPackId) {
		String info = "";
		List<AbstractModelElement> children = pack.getChildren();
		for (AbstractModelElement element : children) {
			if (element instanceof SolidifyPackage) {
				if (SolidifyPackage.SOLIDIFY_PACKAGE_BUSINESSCLASS
						.equals(element.getName())) {
					List beChildren = element.getChildren();
					for (Object be : beChildren) {
						Map<String, Object> relationInfo = getRelationInfo((AbstractModelElement) be);
						if (relationInfo.get("BC") != null) {
							BusinessClass relationBc = (BusinessClass) relationInfo
									.get("BC");
							AbstractModelElement topPackage;
							topPackage = relationBc.getParent();
							while (topPackage != null
									&& !topPackage.getId().equals(topPackId)) {
								topPackage = topPackage.getParent();
							}
							if (topPackage == null) {// 不在同一个分支下
								info = relationInfo.get("info").toString();
								return info;
							}
						}
					}
				}
			} else if (element instanceof ModelPackage) {
				info = getRelationInfoOfPackage((ModelPackage) element,
						topPackId);
				if (!info.equals("")) {
					return info;
				}
			}

		}
		return info;
	}

	/**
	 * 获取默认的生成的版本信息
	 * 
	 * @return
	 */
	public VersionInfo generateVersionInfo() {
		VersionInfo version = new VersionInfo();
		version.setCreatedTime(new Date());
		version.setChangedTime(new Date());
		version.setCreator(System.getProperty("user.name"));
		version.setModifier(System.getProperty("user.name"));
		version.setVersionNumber("1");
		return null;

	}

	/**
	 * 获取默认的包名
	 * 
	 * @return
	 */
	public String generateNextPackageName() {
		String name = "";
		List<ModelPackage> packages = businessObjectModel.getPackages();
		if (packages == null) {
			return PACKAGE_NAME_PREFIX + "1";
		}
		int size = packages.size() + 1;
		name = PACKAGE_NAME_PREFIX + size;

		while (isNameExist(name, packages)) {
			size++;
			name = PACKAGE_NAME_PREFIX + size;
		}
		return name;

	}

	/**
	 * 获取下一个默认的图名称
	 * 
	 * @return
	 */
	public String generateNextDiagramName() {
		String name = "";
		List<Diagram> list = businessObjectModel.getDiagrams();
		if (list == null) {
			return DIAGRAM_NAME_PREFIX + "1";
		}
		int size = list.size() + 1;
		name = DIAGRAM_NAME_PREFIX + size;

		while (isNameExist(name, list)) {
			size++;
			name = DIAGRAM_NAME_PREFIX + size;
		}
		return name;
	}

	public String generateNextBusinessClassName() {
		String name = "";
		List<BusinessClass> list = businessObjectModel.getBusinessClasses();
		if (list == null) {
			return BUSINESSCLASS_NAME_PREFIX + "1";
		}
		int size = list.size() + 1;
		name = BUSINESSCLASS_NAME_PREFIX + size;

		while (isNameExist(name, list)) {
			size++;
			name = BUSINESSCLASS_NAME_PREFIX + size;
		}
		return name;
	}

	/**
	 * 粘贴业务实体时，计算名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextBusinessClassName(String ref) {
		String name = ref;
		List<BusinessClass> list = businessObjectModel.getBusinessClasses();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	
	/**
	 * 粘贴业务实体时，计算名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextBusinessClassDisName(String ref) {
		String name = ref;
		List<BusinessClass> list = businessObjectModel.getBusinessClasses();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isDisplayNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	/**
	 * 粘贴枚举时，计算名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextEnumerationName(String ref) {
		String name = ref;
		List<Enumeration> list = businessObjectModel.getEnumerations();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	/**
	 * 粘贴枚举时，计算名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextEnumerationDisName(String ref) {
		String name = ref;
		List<Enumeration> list = businessObjectModel.getEnumerations();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isDisplayNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	/**
	 * 粘贴业务实体时，计算数据库表名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextBusinessClassTableName(String ref) {
		String name = ref;
		List<BusinessClass> list = businessObjectModel.getBusinessClasses();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isExistTableName(name, list) || isExistMiddleTableName(name, businessObjectModel.getAssociations())) {
			size++;
			name = ref + size;
		}
		return name;
	}

	/**
	 * 获取最新的关系名称
	 * 
	 * @return
	 */
	public String generateNextAssName() {
		String prefixName = "association";
		List<Association> associations = businessObjectModel.getAssociations();
		if (associations == null) {
			return prefixName + "1";
		}
		int count = associations.size();
		int newOrder = count + 1;
		boolean isFound = false;
		while (!isFound) {
			isFound = isNameExist(prefixName + newOrder,ASSOCIATION_NAME_PREFIX);
			if (!isFound) {
				break;
			} else {
				isFound = false;
				newOrder++;
			}
		}
		return prefixName + newOrder;
	}

	/**
	 * 获取最新的关系名称
	 * 
	 * @return
	 */
	public String generateNextAssName(String prefixName) {
		List<Association> associations = businessObjectModel.getAssociations();
		if (associations == null) {
			return prefixName;
		}
		int count = associations.size();
		int newOrder = count;
		boolean isFound = false;
		while (!isFound) {
			isFound = isNameExist(prefixName + newOrder,ASSOCIATION_NAME_PREFIX);
			if (!isFound) {
				break;
			} else {
				isFound = false;
				newOrder++;
			}
		}
		return prefixName + newOrder;
	}
	/**
	 * 获取最新的关系名称
	 * 
	 * @return
	 */
	public String generateNextAssDisName(String prefixName) {
		List<Association> associations = businessObjectModel.getAssociations();
		if (associations == null) {
			return prefixName;
		}
		int count = associations.size();
		int newOrder = count;
		boolean isFound = false;
		while (!isFound) {
			isFound = isDisplayNameExist(prefixName + newOrder,associations);
			if (!isFound) {
				break;
			} else {
				isFound = false;
				newOrder++;
			}
		}
		return prefixName + newOrder;
	}
	
	/**
	 * 获取最新的继承关系名称
	 * 
	 * @return
	 */
	public String generateNextInheritanceName() {
		String prefixName = "inheritance";
		List<Inheritance> inheritances = businessObjectModel.getInheritances();
		if (inheritances == null) {
			return prefixName + "1";
		}
		int count = inheritances.size();
		int newOrder = count + 1;
		boolean isFound = false;
		while (!isFound) {
			for (Inheritance inher : inheritances) {
				if ((prefixName + newOrder).equalsIgnoreCase(inher.getName())) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				break;
			} else {
				isFound = false;
				newOrder++;
			}
		}
		return prefixName + newOrder;
	}

	/**
	 * 粘贴业务实体时，计算操作的名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextOperationName(String ref, BusinessClass bu) {
		String name = ref;
		List<BusinessOperation> list = bu.getOperations();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isProOrOperNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	/**
	 * 粘贴业务实体时，计算操作的名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextOperationDisName(String ref, BusinessClass bu) {
		String name = ref;
		List<BusinessOperation> list = bu.getOperations();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isDisplayNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	/**
	 * 粘贴业务实体时，计算属性的名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextPropertyName(String ref, BusinessClass bu) {
		String name = ref;
		List<Property> list = bu.getProperties();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isProOrOperNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	/**
	 * 粘贴业务实体时，计算属性的名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextPropertyDisName(String ref, BusinessClass bu) {
		String name = ref;
		List<Property> list = bu.getProperties();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isDisplayNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}
	/**
	 * 粘贴业务实体时，计算属性的名称
	 * 
	 * @param ref
	 * @return name
	 */
	public String generateNextDbColumnName(String ref, BusinessClass bu) {
		String name = ref;
		List<Property> list = bu.getProperties();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (isDbColumnNameExist(name, list)) {
			size++;
			name = ref + size;
		}
		return name;
	}

	public String generateNextDTOName() {
		String name = "";
		List<DataTransferObject> list = businessObjectModel.getDTOs();
		if (list == null) {
			return DTO_NAME_PREFIX + "1";
		}
		int size = list.size() + 1;
		name = DTO_NAME_PREFIX + size;

		while (isNameExist(name, list)) {
			size++;
			name = DTO_NAME_PREFIX + size;
		}
		return name;
	}

	public String generateNextEnumerationName() {
		String name = "";
		List<Enumeration> list = businessObjectModel.getEnumerations();
		if (list == null) {
			return ENUMERATION_NAME_PREFIX + "1";
		}
		int size = list.size() + 1;
		name = ENUMERATION_NAME_PREFIX + size;

		while (isNameExist(name, list)) {
			size++;
			name = ENUMERATION_NAME_PREFIX + size;
		}
		return name;
	}

	/**
	 * 判断name 是否存在
	 * 
	 * @param name
	 * @param modelElementType
	 * @return
	 */
	public boolean isNameExist(String name, String modelElementType) {
		if (PACKAGE_NAME_PREFIX.equals(modelElementType)) {
			List<ModelPackage> list = businessObjectModel.getPackages();
			return isNameExist(name, list);
		} else if (DIAGRAM_NAME_PREFIX.equals(modelElementType)) {
			List<Diagram> list = businessObjectModel.getDiagrams();
			return isNameExist(name, list);
		} else if (BUSINESSCLASS_NAME_PREFIX.equals(modelElementType)) {
			List<BusinessClass> list = businessObjectModel.getBusinessClasses();
			return isNameExist(name, list);
		} else if (ENUMERATION_NAME_PREFIX.equals(modelElementType)) {
			List<Enumeration> list = businessObjectModel.getEnumerations();
			return isNameExist(name, list);
		} else if (DTO_NAME_PREFIX.equals(modelElementType)) {
			List<DataTransferObject> list = businessObjectModel.getDTOs();
			return isNameExist(name, list);
		} else if (ASSOCIATION_NAME_PREFIX.equals(modelElementType)) {
			List<Association> list = businessObjectModel.getAssociations();
			return isNameExist(name, list);
		} else {
			return false;
		}
	}

	private boolean isNameExist(String name, List<?> list) {
		boolean isExist = false;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (((AbstractModelElement) list.get(i)).getName() != null
						&& name.equalsIgnoreCase(
						((AbstractModelElement) list.get(i)).getName())) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}
	private boolean isDisplayNameExist(String name, List<?> list) {
		boolean isExist = false;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (name.toUpperCase(Locale.getDefault()).equals(
						((AbstractModelElement) list.get(i)).getDisplayName()
								.toUpperCase(Locale.getDefault()))) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}
	
	/**
	 * 判断属性或操作的名称是否已存在
	 * 
	 * @param name
	 * @param list
	 * @return
	 */
	private boolean isDbColumnNameExist(String name, List<?> list) {
		boolean isExist = false;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof PersistenceProperty
						&& name.toUpperCase(Locale.getDefault()).equals(
								((PersistenceProperty) list.get(i))
										.getdBColumnName().toUpperCase(Locale.getDefault()))) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	/**
	 * 判断属性或操作的名称是否已存在
	 * 
	 * @param name
	 * @param list
	 * @return
	 */
	private boolean isProOrOperNameExist(String name, List<?> list) {
		boolean isExist = false;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (name.toUpperCase(Locale.getDefault()).equals(
						((AbstractModelElement) list.get(i)).getName()
								.toUpperCase(Locale.getDefault()))) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}
	
//	public boolean isAssExist(String name) {
//		List<Association> associations = businessObjectModel.getAssociations();
//		boolean isFound = false;
//		for (Association ass : associations) {
//			if (name != null && name.equalsIgnoreCase(ass.getName())) {
//				isFound = true;
//				return isFound;
//			}
//		}
//		return isFound;
//	}
//	public boolean isAssDiaNameExist(String name) {
//		List<Association> associations = businessObjectModel.getAssociations();
//		boolean isFound = false;
//		for (Association ass : associations) {
//			if (name != null && name.equalsIgnoreCase(ass.getDisplayName())) {
//				isFound = true;
//				return isFound;
//			}
//		}
//		return isFound;
//	}
	
//	public boolean isDtoExist(String name) {
//		List<DataTransferObject> dtos = businessObjectModel.getDTOs();
//		boolean isFound = false;
//		for (DataTransferObject dto : dtos) {
//			if (name != null && name.equalsIgnoreCase(dto.getName())) {
//				isFound = true;
//				return isFound;
//			}
//		}
//		return isFound;
//	}
	/**
	 * 判断数据库表名是否已存在
	 * 
	 * @param name
	 * @param list
	 * @return
	 */
	public boolean isExistTableName(String name, List<BusinessClass> list) {
		boolean isExist = false;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (name.equalsIgnoreCase(
						((BusinessClass) list.get(i)).getTableName())) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}
	/**
	 * 判断数据库表名与关联关系的中间表名是否重复
	 * 
	 * @param name
	 * @param list
	 * @return
	 */
	public boolean isExistMiddleTableName(String name, List<Association> list) {
		boolean isExist = false;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (name.equalsIgnoreCase(
						((Association) list.get(i)).getPersistencePloyParams().get(Association.RELATIONTABLENAME)
								)) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	
	/**
	 * 获取内置业务对象模型
	 * 
	 * @return
	 */
	public static BusinessObjectModel getBuildInOm() {
		try {
			return BomManager.getBuildInOm();
		} catch (DocumentException e) {
			Logger.log(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存模型
	 * 
	 * @param businessObjectModel
	 * @param osString
	 */
	public static void saveModel(BusinessObjectModel businessObjectModel,
			String osString) {
		BomManager.outputXmlFile(businessObjectModel, osString);
	}

	public BusinessModelDiagramEditor getBusinessModelDiagramEditor() {
		return businessModelDiagramEditor;
	}

	public void setBusinessModelDiagramEditor(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		this.businessModelDiagramEditor = businessModelDiagramEditor;
	}

}
