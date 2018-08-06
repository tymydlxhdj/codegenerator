package com.mqfdy.code.resource.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BEPermission;
import com.mqfdy.code.model.BEStatus;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.OperationParam;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.PrimaryKeyPloyType;

/**
 * 校验规则类
 * 
 * @author mqfdy
 * 
 */
public class ValidatorRules {

	public static String ERROR = "Error";
	public static String WARNING = "Warning";
	/**
	 * 规则id
	 */
	private String id;

	/**
	 * 规则名称
	 */
	private String name;
	/**
	 * 所属的校验器
	 */
	private Validator validator;
	private boolean isSelectionValid;
	private List<String> idList;
	private List<String> nameList;
	private boolean flag = true;
	private List<ValiResult> resultList;

	public ValidatorRules(String id, String name, boolean isSelectionValid,
			Validator validator) {
		this.setId(id);
		this.setName(name);
		this.setSelectionValid(isSelectionValid);
		this.setValidator(validator);
	}

	public static List<ValidatorRules> getRules(Validator validator) {

		List<ValidatorRules> rules = new ArrayList<ValidatorRules>();
		if (validator.getId() == Validator.ID_MODEL) {
			rules.add(new ValidatorRules("01", "ID长度32位", true, validator));
		} else if (validator.getId() == Validator.ID_PACKAGE) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
		} else if (validator.getId() == Validator.ID_DIAGRAM) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
		} else if (validator.getId() == Validator.ID_DIAGRAME_ELEMENT) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
		} else if (validator.getId() == Validator.ID_BUSINESSCLASS) {
			rules.add(new ValidatorRules("01", "ID、名称、显示名称格式检查", true,
					validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_BUSINESSCLASS_PROPERTY) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_PROPERTY_GROUP) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_PROPERTY_EDITOR) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_ENUMERATION) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_DTO) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_ASSOCIATION) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_INHERITANCE) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		} else if (validator.getId() == Validator.ID_OPERATION) {
			rules.add(new ValidatorRules("01", "格式检查", true, validator));
			rules.add(new ValidatorRules("02", "全局唯一检查", true, validator));
		}
		return rules;
	}

	public boolean validate(BusinessObjectModel businessObjectModel,
			AbstractModelElement ele, ValidatorContext con,
			List<ValiResult> resultList) {
		this.idList = getAllIds(businessObjectModel);
		List<String> nameList = new ArrayList<String>();
		for (BusinessClass bu : businessObjectModel.getBusinessClasses()) {
			nameList.add(bu.getName());
		}
		this.nameList = nameList;
		this.resultList = resultList;
		if (ele instanceof BusinessClass) {
			validateBusinessClass(businessObjectModel, (BusinessClass) ele, con);
		} else if (ele instanceof ModelPackage) {
			validateModelPackage((ModelPackage) ele, con);
		} else if (ele instanceof Diagram) {
			validateDiagram((Diagram) ele, con);
		} else if (ele instanceof DiagramElement) {
			// validateDiagramElement((DiagramElement) ele, con);
		} else if (ele instanceof Property) {
			validateProperty((Property) ele, con);
		} else if (ele instanceof BusinessOperation) {
			validateOperation((BusinessOperation) ele, con);
		} else if (ele instanceof DataTransferObject) {
			validateDTO(businessObjectModel, (DataTransferObject) ele, con);
		} else if (ele instanceof Enumeration) {
			validateEnumeration((Enumeration) ele, con);
		} else if (ele instanceof PropertyGroup) {
			validatePropertyGroup((PropertyGroup) ele, con);
		} else if (ele instanceof PropertyEditor) {
			validatePropertyEditor((PropertyEditor) ele, con);
			validateEditorEnum(businessObjectModel,(PropertyEditor) ele, con);
		} else if (ele instanceof Association) {
			validateAssociation(businessObjectModel, (Association) ele, con);
		} else if (ele instanceof Inheritance) {
			validateInheritance(businessObjectModel, (Inheritance) ele, con);
		} else if (ele instanceof BusinessObjectModel) {
			validateAbstractModelElement(ele, con);
		}
		return flag;
	}
	
	private void validateEditorEnum(BusinessObjectModel businessObjectModel,PropertyEditor editor, ValidatorContext con){
		if("01".equals(this.getId()))
			if(businessObjectModel!=null){
				String enumId = editor.getEditorParams().get(PropertyEditor.PARAM_KEY_ENUMERATION_ID);
				boolean isFound = false;
				if(enumId!=null && !"".equals(enumId)){
					List<Enumeration> listEnums = businessObjectModel.getEnumerations();
					if(listEnums!=null){
						for(Enumeration enumer : listEnums){
							if(enumId  != null && enumId.equals(enumer.getId())){
								isFound = true;
							}
						}
					}
					
					if(!isFound){
						Property property = editor.getBelongProperty();
						con.printToConsole(
								"\t 属性来源配置的枚举丢失" + " "
										+ property.getName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR,
								property, "属性来源配置的枚举丢失",
								property.getName());
						resultList.add(rel);
					}
				}
				
			}
	}

	private void validateInheritance(BusinessObjectModel businessObjectModel,
			Inheritance ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tID为空:" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "ID为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "ID长度不是32位",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole(
							"\t名称为空:" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() != null) {
					if (!ValidatorUtil.valiName(ele.getName())) {
						con.printToConsole(
								"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称格式不正确",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getDisplayName() != null) {
					if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length() + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele, "显示名称太长",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole(
								"\t显示名称格式不正确" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
			} else if (this.getId().equals("02")) {
				if (ele.getId() != null) {
					int i = 0;
					for (String bu : this.idList) {
						if (bu != null) {
							if (bu.equals(ele.getId()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\tID不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele, "ID不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					int i = 0;
					for (Inheritance bu : businessObjectModel.getInheritances()) {
						if (bu.getName().equals(ele.getName()))
							i++;
					}
					if (i > 1) {
						con.printToConsole(
								"\t名称不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
			}
		}

	}

	private void validateAssociation(BusinessObjectModel businessObjectModel,
			Association ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tID为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "ID为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "ID长度不是32位",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole(
							"\t名称为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() != null) {
					if (!ValidatorUtil.valiNameLength(ele.getName())) {
						con.printToConsole("\t名称长度:" + ele.getName().length()
								+ "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele, "名称长度"
								+ ele.getName().length(), ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!ValidatorUtil.valiName(ele.getName())) {
						con.printToConsole(
								"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称格式不正确",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getDisplayName() != null) {
					if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length() + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele, "显示名称太长",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole(
								"\t显示名称格式不正确" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getClassA() == null) {
					con.printToConsole(
							"\t对应的ClassA不存在" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "对应的ClassA不存在",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
					return;
				}
				if (ele.getClassB() == null) {
					con.printToConsole(
							"\t对应的ClassB不存在" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "对应的ClassB不存在",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
					return;
				}

				if (ele.isNavigateToClassB()) {
					if (ele.getNavigateToClassBRoleName().trim().length() == 0) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"导航属性名称为空" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"导航属性名称为空", ele.getFullName());
						resultList.add(rel);
						flag = false;
					} else if (ele.getNavigateToClassBRoleName() != null
							&& !ValidatorUtil.valiName(ele.getNavigateToClassBRoleName())) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"导航属性名称格式不正确" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"导航属性名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (KeyWordsChecker.doCheckJava(ele
							.getNavigateToClassBRoleName())) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"导航属性名称是java关键字"
								+ "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"导航属性名称是java关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (ele.getNavigateToClassBRoleName().length() > 30) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"导航属性名称长度超过30" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								""+ ele.getClassA().getDisplayName() +"导航属性名称长度超过30", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!ValidatorUtil.checkRoleNamePropertyMult(ele.getClassA(),
							ele.getNavigateToClassBRoleName())) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"导航属性名称与"+ ele.getClassA().getDisplayName() +"属性名称重复"
								+ "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"导航属性名称与"+ ele.getClassA().getDisplayName() +"属性名称重复", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!ValidatorUtil.checkRoleNameMult(businessObjectModel, ele,
							ele.getClassA(), ele.getNavigateToClassBRoleName())) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"导航属性名称重复" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"导航属性名称重复", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}

				if (ele.isNavigateToClassA()) {
					if (ele.getNavigateToClassARoleName().trim().length() == 0) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"导航属性名称为空" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"导航属性名称为空", ele.getFullName());
						resultList.add(rel);
						flag = false;
					} else if (ele.getNavigateToClassARoleName() != null
							&& !ValidatorUtil.valiName(ele
									.getNavigateToClassARoleName())) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"导航属性名称格式不正确" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"导航属性名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (KeyWordsChecker.doCheckJava(ele
							.getNavigateToClassARoleName())) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"导航属性名称是Java关键字"
								+ "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"导航属性名称是Java关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (ele.getNavigateToClassARoleName().length() > 30) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"导航属性名称长度超过30" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								""+ ele.getClassB().getDisplayName() +"导航属性名称长度超过30", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!ValidatorUtil.checkRoleNamePropertyMult(ele.getClassB(),
							ele.getNavigateToClassARoleName())) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"导航属性名称与"+ ele.getClassB().getDisplayName() +"属性名称重复"
								+ "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"导航属性名称与"+ ele.getClassB().getDisplayName() +"属性名称重复", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!ValidatorUtil.checkRoleNameMult(businessObjectModel, ele,
							ele.getClassB(), ele.getNavigateToClassARoleName())) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"导航属性名称重复" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"导航属性名称重复", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}

				if (ele.getPersistencePloyParams()
						.get(Association.FOREIGNKEYINA).toUpperCase(Locale.getDefault())
						.equals("TRUE")) {
					String name = ele.getPersistencePloyParams().get(
							Association.FOREIGNKEYCOLUMNINA);
					if (name == null || name.trim().length() == 0) {
						con.printToConsole(
								"\t"+ ele.getClassA().getDisplayName() +"外键字段名为空" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"外键字段名为空", ele.getFullName());
						resultList.add(rel);
						flag = false;
					} else if (!ValidatorUtil.valiFirstNo_Name(name)) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"外键字段名格式不正确" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								""+ ele.getClassA().getDisplayName() +"外键字段名格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
							name != null && KeyWordsChecker.doCheckSql(name)) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"外键字段名是SQL关键字" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"外键字段名是SQL关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
							name != null && name.length() > 30) {
						con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"外键字段名长度超过30" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"外键字段名长度超过30", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if(IModelElement.STEREOTYPE_REVERSE.equals(ele.getClassA().getStereotype())
							&& IModelElement.STEREOTYPE_REVERSE.equals(ele.getClassB().getStereotype())){
						if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
								name != null && !ValidatorUtil.isFKExistInBu(ele.getClassA(), name)) {
							con.printToConsole("\t关系两端均为反向业务实体，"+ ele.getClassA().getDisplayName() +"外键字段名在数据库字段中不存在" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"关系两端均为反向业务实体，"+ ele.getClassA().getDisplayName() +"外键字段名在数据库字段中不存在", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}else{
						if (name != null&& !IModelElement.STEREOTYPE_REVERSE.equals(ele.getClassA().getStereotype())&& !ValidatorUtil.checkFKMult(ele.getClassA(), name,ele.getAssociationType())) {
							con.printToConsole("\t"+ ele.getClassA().getDisplayName() +"外键字段名与数据库字段名称重复" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									""+ ele.getClassA().getDisplayName() +"外键字段名与数据库字段名称重复", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}
					if (name != null
							&& !ValidatorUtil.checkFKMultInAll(ele,ele.getClassA(), name,
									businessObjectModel)) {
						con.printToConsole(
								"\t"+ ele.getClassA().getDisplayName() +"外键字段名重复" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassA().getDisplayName() +"外键字段名重复", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if(ele.getAssociationType().equals(AssociationType.mult2mult)){
					String nameA = ele.getPersistencePloyParams().get(
							Association.FOREIGNKEYCOLUMNINA);
					String nameB = ele.getPersistencePloyParams().get(
							Association.FOREIGNKEYCOLUMNINB);
					if(nameA.equals(nameB)){
						con.printToConsole(
								"\t两端外键字段名重复" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"两端外键字段名重复", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getPersistencePloyParams()
						.get(Association.FOREIGNKEYINB).toUpperCase(Locale.getDefault())
						.equals("TRUE")) {
					String name = ele.getPersistencePloyParams().get(
							Association.FOREIGNKEYCOLUMNINB);
					if (name == null || name.trim().length() == 0) {
						con.printToConsole(
								"\t"+ ele.getClassB().getDisplayName() +"外键字段名为空" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"外键字段名为空", ele.getFullName());
						resultList.add(rel);
						flag = false;
					} else if (name != null
							&& !ValidatorUtil.valiFirstNo_Name(name)) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"外键字段名格式不正确" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"外键字段名格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
							name != null && KeyWordsChecker.doCheckSql(name)) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"外键字段名是SQL关键字" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"外键字段名是SQL关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
							name != null && name.length() > 30) {
						con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"外键字段名长度超过30" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"外键字段名长度超过30", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if(IModelElement.STEREOTYPE_REVERSE.equals(ele.getClassA().getStereotype())
							&& IModelElement.STEREOTYPE_REVERSE.equals(ele.getClassB().getStereotype())){
						if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && name != null && !ValidatorUtil.isFKExistInBu(ele.getClassB(), name)) {
							con.printToConsole("\t关系两端均为反向业务实体，"+ ele.getClassB().getDisplayName() +"外键字段名在数据库字段中不存在" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"关系两端均为反向业务实体，"+ ele.getClassB().getDisplayName() +"外键字段名在数据库字段中不存在", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}else{
						if (name != null &&!IModelElement.STEREOTYPE_REVERSE.equals(ele.getClassB().getStereotype())&& !ValidatorUtil.checkFKMult(ele.getClassB(), name,ele.getAssociationType())) {
							con.printToConsole("\t"+ ele.getClassB().getDisplayName() +"外键字段名与数据库字段名称重复" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									""+ ele.getClassB().getDisplayName() +"外键字段名与数据库字段名称重复", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}
					if (name != null
							&& !ValidatorUtil.checkFKMultInAll(ele,ele.getClassB(), name,
									businessObjectModel)) {
						con.printToConsole(
								"\t"+ ele.getClassB().getDisplayName() +"外键字段名与重复" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								""+ ele.getClassB().getDisplayName() +"外键字段名重复", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if(ele.getAssociationType().equals(AssociationType.mult2mult.getValue())){
					String mdTableName = ele.getPersistencePloyParams().get(Association.RELATIONTABLENAME);
					if (mdTableName == null) {
						con.printToConsole(
								"\t中间表名为空" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "中间表名为空",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (mdTableName != null) {
						if (/* check.doCheckJava(ele.getTableName())|| */
								!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
								KeyWordsChecker.doCheckSql(mdTableName)) {
							con.printToConsole(
									"\t中间表名为SQL关键字" + "    <Model>"
											+ ele.getFullName(),
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"中间表名为SQL关键字", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
						// 首位不能是数字
						if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
								!ValidatorUtil.valiFirstNo_Name(mdTableName)) {
							con.printToConsole(
									"\t中间表名格式不正确" + "    <Model>"
											+ ele.getFullName(),
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele, "中间表名格式不正确",
									ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
						// 首位不能是数字
						if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
								!ValidatorUtil.valiNameLength(mdTableName)) {
							con.printToConsole(
									"\t中间表名长度不能超过30" + "    <Model>"
											+ ele.getFullName(),
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele, "中间表名长度不能超过30",
									ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}
				}
				
			} else if (this.getId().equals("02")) {
				if (ele.getId() != null) {
					int i = 0;
					for (String bu : this.idList) {
						if (bu != null) {
							if (bu.equals(ele.getId()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\tID不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "ID不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					int i = 0;
					for (Association bu : businessObjectModel.getAssociations()) {
						if (bu.getName().equals(ele.getName()))
							i++;
					}
					if (i > 1) {
						con.printToConsole(
								"\t名称不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if(ele.getAssociationType().equals(AssociationType.mult2mult.getValue())){
					String mdTableName = ele.getPersistencePloyParams().get(Association.RELATIONTABLENAME);
					if (mdTableName != null) {
						if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
								!ValidatorUtil.valiTableName(mdTableName,ele,businessObjectModel)) {
							con.printToConsole(
									"\t中间表名重复" + "    <Model>"
											+ ele.getFullName(),
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele, "中间表名重复",
									ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
						
					}
				}
			}
		}

	}

	public void validateAbstractModelElement(AbstractModelElement ele,
			ValidatorContext con) {
		con.printToConsole("校验模型:" + ele.getName(), ValidatorContext.INFO);
		if (this.isSelectionValid()) {
			// if(this.getId().equals("01")){
			if (ele.getId() == null) {
				con.printToConsole(
						"\tID为空" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				ValiResult rel = new ValiResult(ERROR, ele, "ID为空",
						ele.getFullName());
				resultList.add(rel);
				flag = false;
			} else if (ele.getId() != null && ele.getId().length() != 32) {
				con.printToConsole(
						"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				ValiResult rel = new ValiResult(WARNING, ele, "ID长度不是32位",
						ele.getFullName());
				resultList.add(rel);
				flag = false;
			} else {
				con.printToConsole("\tID长度32位", ValidatorContext.INFO);
			}
			if (ele.getName() == null) {
				con.printToConsole(
						"\t名称为空" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
						ele.getFullName());
				resultList.add(rel);
				flag = false;
			}
			if (ele.getName() != null) {
				if (!ValidatorUtil.valiName(ele.getName())) {
					con.printToConsole(
							"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if ((new KeyWordsChecker()).doCheckJava(ele.getName())) {
					con.printToConsole(
							"\t名称是Java关键字" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称是Java关键字",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}

			if (ele.getDisplayName() != null) {
				if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
					con.printToConsole("\t显示名称长度:"
							+ ele.getDisplayName().length() + "    <Model>"
							+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "显示名称长度太长",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}

				if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
					con.printToConsole(
							"\t显示名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "显示名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
			if (ele instanceof BusinessObjectModel) {
				String nameSpace = ((BusinessObjectModel) ele).getNameSpace();
				if (nameSpace == null || nameSpace.trim().equals("")) {
					con.printToConsole(
							"\t命名空间为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "命名空间为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				} else if (!ValidatorUtil.valiPackageName(nameSpace)) {
					con.printToConsole(
							"\t命名空间格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "命名空间格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				} else if (!ValidatorUtil.valiRemarkLength(nameSpace)) {
					con.printToConsole(
							"\t命名空间长度超过128个字符" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele,
							"命名空间长度超过128个字符", ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
		}
		// }
	}

	public void validateDiagram(AbstractModelElement ele, ValidatorContext con) {
		con.printToConsole("校验图:" + ele.getDisplayName(), ValidatorContext.INFO);
		if (this.isSelectionValid()) {
			// if(this.getId().equals("01")){
			if (ele.getId() == null) {
				con.printToConsole(
						"\tID为空" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				ValiResult rel = new ValiResult(ERROR, ele, "ID为空",
						ele.getFullName());
				resultList.add(rel);
				flag = false;
			} else if (ele.getId() != null && ele.getId().length() != 32) {
				con.printToConsole(
						"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				ValiResult rel = new ValiResult(WARNING, ele, "ID长度不是32位",
						ele.getFullName());
				resultList.add(rel);
				flag = false;
			} else {
				con.printToConsole("\tID长度32位", ValidatorContext.INFO);
			}
			if (ele.getName() == null) {
				con.printToConsole(
						"\t名称为空" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
						ele.getFullName());
				resultList.add(rel);
				flag = false;
			}
			if (ele.getName() != null) {
				if (!ValidatorUtil.valiName(ele.getName())) {
					con.printToConsole(
							"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
			if (ele.getDisplayName() != null) {
				if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
					con.printToConsole("\t显示名称长度:"
							+ ele.getDisplayName().length() + "    <Model>"
							+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "显示名称长度"
							+ ele.getDisplayName().length(), ele.getFullName());
					resultList.add(rel);
					flag = false;
				}

				if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
					con.printToConsole(
							"\t显示名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "显示名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
			// }else if(this.getId().equals("02")){
			if (ele.getId() != null) {
				int i = 0;
				for (String bu : this.idList) {
					if (bu != null) {
						if (bu.equals(ele.getId()))
							i++;
					}
				}
				if (i > 1) {
					con.printToConsole(
							"\tID不唯一" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "ID不唯一",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
			if (ele.getName() != null) {
				int i = 0;
				if (ele.getParent() instanceof ModelPackage) {
					for (AbstractModelElement dia : ((ModelPackage) ele
							.getParent()).getChildren()) {
						if (dia instanceof Diagram) {
							if (dia.getName().equals(ele.getName()))
								i++;
						}
					}
				}
				if (i > 1) {
					con.printToConsole(
							"\t名称不唯一" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
		}
		// }
	}

	public void validateModelPackage(ModelPackage ele, ValidatorContext con) {
		con.printToConsole("校验包:" + ele.getDisplayName(), ValidatorContext.INFO);
		if (this.isSelectionValid()) {
			// if(this.getId().equals("01")){
			if (ele.getId() == null) {
				con.printToConsole(
						"\tID为空" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				flag = false;
			}
			if (ele.getId() != null && ele.getId().length() != 32) {
				con.printToConsole(
						"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				flag = false;
			}
			if (ele.getName() == null) {
				con.printToConsole(
						"\t名称为空" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
						ele.getFullName());
				resultList.add(rel);
				flag = false;
			}
			if (ele.getName() != null) {
				if (!ValidatorUtil.valiNameLength(ele.getName())) {
					con.printToConsole("\t名称长度:" + ele.getName().length()
							+ "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "名称长度"
							+ ele.getName().length(), ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (!ValidatorUtil.valiName(ele.getName())) {
					con.printToConsole(
							"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}

			if (ele.getDisplayName() != null) {
				if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
					con.printToConsole("\t显示名称长度:"
							+ ele.getDisplayName().length() + "    <Model>"
							+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "显示名称长度"
							+ ele.getDisplayName().length(), ele.getFullName());
					resultList.add(rel);
					flag = false;
				}

				if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
					con.printToConsole(
							"\t显示名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "显示名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
			// }
			// else if(this.getId().equals("02")){
			if (ele.getId() != null) {
				int i = 0;
				for (String bu : this.idList) {
					if (bu != null) {
						if (bu.equals(ele.getId()))
							i++;
					}
				}
				if (i > 1) {
					con.printToConsole(
							"\tID不唯一" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "ID不唯一",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
			if (ele.getName() != null) {
				int i = 0;
				// for(String bu:this.nameList){
				// if(bu!=null){
				// if(bu.equals(ele.getName()))
				// i ++;
				// }
				// }
				if (ele.getParent() instanceof ModelPackage) {
					for (AbstractModelElement dia : ((ModelPackage) ele
							.getParent()).getChildren()) {
						if (dia instanceof ModelPackage) {
							if (dia.getName().equals(ele.getName()))
								i++;
						}
					}
				}
				if (ele.getParent() instanceof BusinessObjectModel) {
					for (AbstractModelElement dia : ((BusinessObjectModel) ele
							.getParent()).getChildren()) {
						if (dia instanceof ModelPackage) {
							if (dia.getName().equals(ele.getName()))
								i++;
						}
					}
				}
				if (i > 1) {
					con.printToConsole(
							"\t名称不唯一" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
		}
		// }
	}

	public void validateBusinessClass(BusinessObjectModel businessObjectModel,
			BusinessClass ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tID为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "ID为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "ID长度不是32位",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole(
							"\t名称为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() != null) {
					if (!ValidatorUtil.isFirstUppercase(ele.getName())) {
						con.printToConsole("\t名称格式不正确,首字母必须大写" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"名称格式不正确,首字母必须大写", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					if (KeyWordsChecker.doCheckJava(ele.getName())) {
						con.printToConsole(
								"\t名称为Java关键字" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"名称为Java关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (KeyWordsChecker.doCheckSguap(ele.getName())) {
						con.printToConsole("\t名称为SGUAP关键字" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"名称为SGUAP关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}

				if (ele.getTableName() == null) {
					con.printToConsole(
							"\t数据库表名为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "数据库表名为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getTableName() != null) {
					if (/* check.doCheckJava(ele.getTableName())|| */
							!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
							KeyWordsChecker.doCheckSql(ele.getTableName())) {
						con.printToConsole(
								"\t数据库表名为SQL关键字" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"数据库表名为SQL关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					// 首位不能是数字
					if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
							!ValidatorUtil.valiFirstNo_Name(ele.getTableName())) {
						con.printToConsole(
								"\t数据库表名格式不正确" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "数据库表名格式不正确",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (((BusinessClass) ele).getDisplayName() != null) {
					if (((BusinessClass) ele).getDisplayName().length() < 1
							|| ((BusinessClass) ele).getDisplayName().length() > 64) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length() + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称长度太长", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole(
								"\t显示名称格式不正确" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (!ele.hasPkProperty()) {
						con.printToConsole(
								"\t缺少主键属性" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "缺少主键属性",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
			} else if (this.getId().equals("02")) {
				if (ele.getName() != null) {
					int i = 0;
					// if (ele.getParent() != null) {
					// for (Object bu : ele.getParent().getChildren()) {
					// if (bu instanceof BusinessClass) {
					// if (((BusinessClass) bu).getName().equals(
					// ele.getName()))
					// i++;
					// }
					// }
					// }
					for (String bu : this.nameList) {
						if (bu != null) {
							if (bu.equalsIgnoreCase(ele.getName()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\t名称不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getTableName() != null) {
					if (!ValidatorUtil.valiTableName(ele.getTableName(),ele,businessObjectModel)) {
						con.printToConsole(
								"\t数据库表名重复" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "数据库表名重复",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					
				}
				if (ele.getProperties() == null
						|| ele.getProperties().size() == 0) {
					con.printToConsole(
							"\t业务实体中不包含任何属性" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "业务实体中不包含任何属性",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
		}
	}

	private void validateOperation(BusinessOperation ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tID为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "ID为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "ID长度不是32位",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole(
							"\t名称为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				// 首位不能是数字
				if (!ValidatorUtil.isFirstLowercase(ele.getName())) {
					con.printToConsole(
							"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getDisplayName() != null) {
					if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length() + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称长度太长", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole(
								"\t显示名称格式不正确" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
			} else if (this.getId().equals("02")) {
				if (ele.getId() != null) {
					int i = 0;
					for (String bu : this.idList) {
						if (bu != null) {
							if (bu.equals(ele.getId()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\tID不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "ID不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					int i = 0;
					if (ele.getParent() instanceof BusinessClass) {
						for (BusinessOperation pro : ((BusinessClass) ele
								.getParent()).getOperations()) {
							if (pro.getName().equals(ele.getName())) {
								i++;
							}
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\t名称不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				List<OperationParam> items = ele.getOperationParams();
				if (items != null) {
					for (int i = 0; i < items.size(); i++) {
						for (int j = i + 1; j < items.size(); j++) {
							OperationParam curParam = items.get(i);
							OperationParam temp = items.get(j);
							if (curParam.getName().equals(temp.getName())) {
								con.printToConsole("\t包含重复的操作参数"
										+ "    <Model>" + ele.getFullName(),
										ValidatorContext.ERROR);
								ValiResult rel = new ValiResult(ERROR, ele,
										"包含重复的操作参数", ele.getFullName());
								resultList.add(rel);
								flag = false;
							}
						}
					}
				}
			}
		}

	}

	public void validateProperty(Property ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole("\tID为空:" + ele.getName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "ID为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele, "ID长度不是32位",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole("\t名称为空:" + ele.getName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				// 首位不能是数字
				if (!ValidatorUtil.isFirstLowercase(ele.getName())) {
					con.printToConsole(
							"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称格式不正确",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getDisplayName() != null) {
					if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称长度太长", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole("\t显示名称格式不正确",
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele instanceof PKProperty) {
					if (((PKProperty) ele).getPrimaryKeyPloy() != null
							&& ((PKProperty) ele).getPrimaryKeyPloy().equals(
									PrimaryKeyPloyType.SEQUENCE.getValue())) {
						if (((PKProperty) ele).getDataType().equals(
								DataType.Big_decimal.getValue_hibernet())) {

						} else if (((PKProperty) ele).getDataType().equals(
								DataType.Double.getValue_hibernet())) {

						} else if (((PKProperty) ele).getDataType().equals(
								DataType.Float.getValue_hibernet())) {

						} else if (((PKProperty) ele).getDataType().equals(
								DataType.Integer.getValue_hibernet())) {

						} else if (((PKProperty) ele).getDataType().equals(
								DataType.Long.getValue_hibernet())) {

						} else if (((PKProperty) ele).getDataType().equals(
								DataType.Short.getValue_hibernet())) {

						} else {
							con.printToConsole("\t主键生成策略为SEQUENCE，属性类型不是数字型",
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"主键生成策略为SEQUENCE，属性类型不是数字型",
									ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					} else if (((PKProperty) ele).getPrimaryKeyPloy() != null
							&& ((PKProperty) ele).getPrimaryKeyPloy().equals(
									PrimaryKeyPloyType.UUID.getValue())) {
						try {
							if (!((PKProperty) ele).getDataType().equals(
									DataType.String.getValue_hibernet())) {
								con.printToConsole(
										"\t主键生成策略为UUID，属性类型不是string",
										ValidatorContext.ERROR);
								ValiResult rel = new ValiResult(ERROR, ele,
										"主键生成策略为UUID，属性类型不是string",
										ele.getFullName());
								resultList.add(rel);
								flag = false;
							} else if (Integer.parseInt(((PKProperty) ele)
									.getdBDataLength()) < 32) {
								con.printToConsole("\t主键生成策略为UUID，字段长度小于32位",
										ValidatorContext.ERROR);
								ValiResult rel = new ValiResult(ERROR, ele,
										"主键生成策略为UUID，字段长度小于32位",
										ele.getFullName());
								resultList.add(rel);
								flag = false;
							}
						} catch (Exception e) {
							con.printToConsole("\t字段长度不是数字" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"字段长度不是数字", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}
				}
			} else if (this.getId().equals("02")) {
				if (ele.getId() != null) {
					int i = 0;
					for (String bu : this.idList) {
						if (bu != null) {
							if (bu.equals(ele.getId()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole("\tID不唯一:" + ele.getId(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "ID不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					int i = 0;
					if (ele.getParent() instanceof BusinessClass) {
						for (Property pro : ((BusinessClass) ele.getParent())
								.getProperties()) {
							if (pro.getName().equals(ele.getName())) {
								i++;
							}
						}
					} else if (ele.getParent() instanceof DataTransferObject) {
						for (Property pro : ((DataTransferObject) ele
								.getParent()).getProperties()) {
							if (pro.getName().equals(ele.getName())) {
								i++;
							}
						}
					}
					if (i > 1) {
						con.printToConsole("\t名称不唯一:" + ele.getName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele instanceof PersistenceProperty) {
					if (((PersistenceProperty) ele).getDataType() == null) {
						con.printToConsole("\t属性类型为空", ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "属性类型为空",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (((PersistenceProperty) ele).getDataType() != null) {
						if (DataType.getDataType(((PersistenceProperty) ele)
								.getDataType()) == null) {
							con.printToConsole("\t属性类型不在支持的范围内",
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"属性类型不在支持的范围内", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
						if (DataType.getDataType(((PersistenceProperty) ele)
								.getDataType()) != null
								&& DataType.getDataType(
										((PersistenceProperty) ele)
												.getDataType())
										.isHasDataLength()) {
							if (((PersistenceProperty) ele).getdBDataLength() == null) {
								con.printToConsole("\t字段长度为空",
										ValidatorContext.ERROR);
								ValiResult rel = new ValiResult(ERROR, ele,
										"字段长度为空", ele.getFullName());
								resultList.add(rel);
								flag = false;
							}
						}
						// if(((PKProperty)ele).getDataType().equals(DataType.Double.getValue_java())||((PKProperty)ele).getDataType().equals(DataType.Big_decimal.getValue_java())){
						// if(((PKProperty)ele).getdBDataPrecision() == null){
						// con.printToConsole("\tdBDataLength为空",
						// ValidatorContext.ERROR);
						// }
						// }

					}
					if (((PersistenceProperty) ele).isPersistence()) {
						if (((PersistenceProperty) ele).getdBColumnName() == null) {
							con.printToConsole("\t数据库字段名为空",
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"数据库字段名为空", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}
					if (((PersistenceProperty) ele).getdBColumnName() != null) {
						if (!IModelElement.STEREOTYPE_REVERSE.equals(ele.getStereotype()) && 
								KeyWordsChecker.doCheckSql(((PersistenceProperty) ele)
										.getdBColumnName())) {
							con.printToConsole("\t数据库字段名为SQL关键字",
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"数据库字段名为SQL关键字", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
						// 首位不能是数字
						if (!ValidatorUtil.valiFirstNo_Name(((PersistenceProperty) ele)
								.getdBColumnName())) {
							con.printToConsole("\t数据库字段名称格式不正确" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"数据库字段名称格式不正确", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
						int i = 0;
						for (Property bu : ((BusinessClass) ele.getParent())
								.getProperties()) {
							if (bu instanceof PersistenceProperty) {
								if (((PersistenceProperty) ele)
										.getdBColumnName().equalsIgnoreCase(
												((PersistenceProperty) bu)
												.getdBColumnName()))
									i++;
							}
						}
						if (i > 1) {
							con.printToConsole("\t数据库字段名重复",
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"数据库字段名重复", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}

					}
					if(DataType.getDataType(
							((PersistenceProperty) ele).getDataType()) == null){
						ValiResult rel = new ValiResult(ERROR, ele,
								"属性类型不存在", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					else if (DataType.getDataType(
							((PersistenceProperty) ele).getDataType())
							.isHasDataLength()
							&& ((PersistenceProperty) ele).getdBDataLength() != null && ((PersistenceProperty) ele).getdBDataLength()
									.length() > 0) {
						try {
							int length = Integer.valueOf(((PersistenceProperty) ele)
									.getdBDataLength());
							if(length < 1 && ((PersistenceProperty) ele).getDataType().equals(DataType.Big_decimal.getValue_hibernet())){
								con.printToConsole("\tBig_decimal类型的字段长度小于1",
										ValidatorContext.ERROR);
								ValiResult rel = new ValiResult(ERROR, ele,
										"Big_decimal类型的字段长度小于1", ele.getFullName());
								resultList.add(rel);
								flag = false;
							}
							if(length > 38 && ((PersistenceProperty) ele).getDataType().equals(DataType.Big_decimal.getValue_hibernet())){
								con.printToConsole("\tBig_decimal类型的字段长度大于38",
										ValidatorContext.ERROR);
								ValiResult rel = new ValiResult(ERROR, ele,
										"Big_decimal类型的字段长度大于38", ele.getFullName());
								resultList.add(rel);
								flag = false;
							}
						} catch (Exception e) {
							con.printToConsole("\t字段长度不是数字",
									ValidatorContext.ERROR);
							ValiResult rel = new ValiResult(ERROR, ele,
									"字段长度不是数字", ele.getFullName());
							resultList.add(rel);
							flag = false;
						}
					}

					if (DataType.getDataType(
							((PersistenceProperty) ele).getDataType()) != null && DataType.getDataType(
									((PersistenceProperty) ele).getDataType())
							.isHasDataPrecision()) {
						if (((PersistenceProperty) ele).getdBDataPrecision() != null
								&& ((PersistenceProperty) ele)
										.getdBDataPrecision().length() > 0) {
							try {
								int precision = Integer.valueOf(((PersistenceProperty) ele)
										.getdBDataPrecision());
								if(precision < -84 && ((PersistenceProperty) ele).getDataType().equals(DataType.Big_decimal.getValue_hibernet())){
									con.printToConsole("\tBig_decimal类型的字段精度小于-84",
											ValidatorContext.ERROR);
									ValiResult rel = new ValiResult(ERROR, ele,
											"Big_decimal类型的字段精度小于-84", ele.getFullName());
									resultList.add(rel);
									flag = false;
								}
								if(precision > 127 && ((PersistenceProperty) ele).getDataType().equals(DataType.Big_decimal.getValue_hibernet())){
									con.printToConsole("\tBig_decimal类型的字段精度不能127",
											ValidatorContext.ERROR);
									ValiResult rel = new ValiResult(ERROR, ele,
											"Big_decimal类型的字段精度大于127", ele.getFullName());
									resultList.add(rel);
									flag = false;
								}
							} catch (Exception e) {
								con.printToConsole("\t字段精度不是数字或超出范围",
										ValidatorContext.ERROR);
								ValiResult rel = new ValiResult(ERROR, ele,
										"字段精度不是数字或超出范围", ele.getFullName());
								resultList.add(rel);
								flag = false;
							}
						}
					}
				}
			}
		}
	}

	public void validatePropertyGroup(PropertyGroup ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tID为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele,
							"ID为空", ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele,
							"ID长度不是32位", ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole(
							"\t名称为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele,
							"名称为空", ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				// if(ele.getName()!=null){
				// if (!ele.getName().trim().matches(NAMEREGEX))
				// {
				// con.printToConsole("\t名称格式不正确"+"    <Model>"+ele.getFullName(),
				// ValidatorContext.ERROR);
				// flag = false;
				// }
				// }
				if (ele.getDisplayName() != null) {
					if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length() + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						// ValiResult rel = new ValiResult(WARNING,ele,
						// "显示名称长度", ele.getFullName());
						// resultList.add(rel);
						ValiResult rel = new ValiResult(WARNING,
								ele, "显示名称长度太长", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole(
								"\t显示名称格式不正确" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING,
								ele, "属性分组显示名称格式不正确",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
			} else if (this.getId().equals("02")) {
				if (ele.getId() != null) {
					int i = 0;
					for (String bu : this.idList) {
						if (bu != null) {
							if (bu.equals(ele.getId()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\tID不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"ID不唯一", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					int i = 0;
					if (ele.getParent() instanceof BusinessClass) {
						for (PropertyGroup bu : ((BusinessClass) ele
								.getParent()).getGroups()) {
							if (bu.getName().equals(ele.getName()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\t名称不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"名称不唯一", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
			}
		}
	}

	public void validatePropertyEditor(PropertyEditor ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tID为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					flag = false;
				}
				// if(ele.getName()==null){
				// con.printToConsole("\t名称为空:"+ele.getName(),
				// ValidatorContext.ERROR);
				// flag = false;
				// }
				// if(ele.getDisplayName()!=null){
				// if(!valiDisplayNameLength(ele.getDisplayName())){
				// con.printToConsole("\t名称长度:"+ele.getName().length(),
				// ValidatorContext.ERROR);
				// flag = false;
				// }
				//
				// if
				// (!ele.getDisplayName().trim().matches("[a-z A-Z_0-9\u4E00-\u9FA5]*$"))
				// {
				// con.printToConsole("\t显示名称格式不正确"+"    <Model>"+ele.getFullName(),
				// ValidatorContext.ERROR);
				// flag = false;
				// }
				// }
				if (ele.getEditorType() != null) {
					if (EditorType.getEditorTypeByValue(ele.getEditorType()) == null) {
						con.printToConsole(
								"\t编辑器类型不支持" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR,
								ele.getBelongProperty(), "编辑器类型不支持",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					} else {
						if (EditorType
								.getEditorTypeByValue(ele.getEditorType()) != null) {
							String curDataType = ele.getBelongProperty()
									.getDataType();
							EditorType curEditorType = EditorType
									.getEditorTypeByValue(ele.getEditorType());
							if (curDataType == null || curEditorType == null) {
								flag = false;
							} else {
								if (ValidatorUtil.getEditorMap().get(curDataType) == null
										 || ValidatorUtil.getEditorMap().get(curDataType).get(
										curEditorType) == null) {
									ValiResult rel = new ValiResult(ERROR,
											ele.getBelongProperty(),
											"编辑器类型与属性类型不匹配", ele.getFullName());
									resultList.add(rel);
									flag = false;
								}
							}
						}
					}
				}
			} else if (this.getId().equals("02")) {
				if (ele.getId() != null) {
					int i = 0;
					for (String bu : this.idList) {
						if (bu != null) {
							if (bu.equals(ele.getId()))
								i++;
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\tID不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						flag = false;
					}
				}
				// if(ele.getName()!=null){
				// int i = 0;
				// BusinessClass businessClass = (BusinessClass)
				// ((Property)ele.getParent()).getParent();
				// for(Property bu:businessClass.getProperties()){
				// if(bu instanceof PersistenceProperty){
				// if(((PersistenceProperty) bu).getEditor()!=null &&
				// ((PersistenceProperty)
				// bu).getEditor().getName().equals(ele.getName()))
				// i ++;
				// }
				// }
				// if(i>1){
				// con.printToConsole("\t名称不唯一:"+ele.getName(),
				// ValidatorContext.ERROR);
				// flag = false;
				// }
				// }
			}
		}
	}

	public void validateDiagramElement(DiagramElement ele, ValidatorContext con) {
		con.printToConsole("    校验图元:" + ele.getId(), ValidatorContext.INFO);
		if (this.isSelectionValid()) {
			// if(this.getId().equals("01")){
			if (ele.getId() == null) {
				con.printToConsole("\tID为空:" + ele.getName(),
						ValidatorContext.ERROR);
				flag = false;
			}
			if (ele.getId() != null && ele.getId().length() != 32) {
				con.printToConsole(
						"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				flag = false;
			}
			if (ele.getObjectId() == null) {
				con.printToConsole(
						"\tObjectId为空" + "    <Model>" + ele.getFullName(),
						ValidatorContext.ERROR);
				flag = false;
			}
			// }
			// else if(this.getId().equals("02")){
			if (ele.getId() != null) {
				int i = 0;
				for (String bu : this.idList) {
					if (bu != null) {
						if (bu.equals(ele.getId()))
							i++;
					}
				}
				if (i > 1) {
					con.printToConsole("\tID不唯一:" + ele.getId(),
							ValidatorContext.ERROR);
					flag = false;
				}
			}
			if (ele.getObjectId() != null) {
				int i = 0;
				for (DiagramElement bu : ((Diagram) ele.getParent())
						.getElements()) {
					if (bu.getObjectId().equals(ele.getObjectId()))
						i++;
				}
				if (i > 1) {
					con.printToConsole(
							"\tObjectId重复:" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					flag = false;
				}
			}
			// }
		}
	}

	public void validateDTO(BusinessObjectModel businessObjectModel,
			DataTransferObject ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tid为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole("\t名称为空:" + ele.getName(),
							ValidatorContext.ERROR);
					flag = false;
				}
				if (ele.getName() != null) {
					if (!ValidatorUtil.valiName(ele.getName())) {
						con.printToConsole(
								"\t名称格式不正确" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					if (KeyWordsChecker.doCheckJava(ele.getName())) {
						con.printToConsole("\t名称为关键字" + ele.getName(),
								ValidatorContext.ERROR);
						flag = false;
					}
					if (KeyWordsChecker.doCheckSguap(ele.getName())) {
						con.printToConsole("\t名称为SGUAP关键字" + ele.getName(),
								ValidatorContext.ERROR);
						flag = false;
					}
				}

				if (ele.getDisplayName() != null) {
					if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length(),
								ValidatorContext.ERROR);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole("\t显示名称格式不正确",
								ValidatorContext.ERROR);
						flag = false;
					}
				}
			} else if (this.getId().equals("02")) {
				if (ele.getName() != null) {
					int i = 0;
					if (ele.getParent() != null) {
						for (Object bu : businessObjectModel.getDTOs()) {
							if (bu instanceof DataTransferObject) {
								if (((DataTransferObject) bu).getName().equals(
										ele.getName()))
									i++;
							}
						}
					}
					if (i > 1) {
						con.printToConsole("\t名称不唯一:" + ele.getName(),
								ValidatorContext.ERROR);
						flag = false;
					}
				}
			}
		}
	}

	public void validateEnumeration(Enumeration ele, ValidatorContext con) {
		if (this.isSelectionValid()) {
			if (this.getId().equals("01")) {
				if (ele.getId() == null) {
					con.printToConsole(
							"\tID为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					flag = false;
				}
				if (ele.getId() != null && ele.getId().length() != 32) {
					con.printToConsole(
							"\tID长度不是32位" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					flag = false;
				}
				if (ele.getName() == null) {
					con.printToConsole(
							"\t名称为空" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "名称为空",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
				if (ele.getName() != null) {
					if (!ValidatorUtil.isFirstUppercase(ele.getName())) {
						con.printToConsole("\t名称格式不正确,首字母必须大写" + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"名称格式不正确,首字母必须大写", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getName() != null) {
					if (KeyWordsChecker.doCheckJava(ele.getName())) {
						con.printToConsole(
								"\t名称为JAVA关键字" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"名称为JAVA关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
					if (KeyWordsChecker.doCheckSguap(ele.getName())) {
						con.printToConsole("\t名称为SGUAP关键字" + ele.getName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"名称为SGUAP关键字", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}

				if (ele.getDisplayName() != null) {
					if (!ValidatorUtil.valiDisplayNameLength(ele.getDisplayName())) {
						con.printToConsole("\t显示名称长度:"
								+ ele.getDisplayName().length() + "    <Model>"
								+ ele.getFullName(), ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称长度太长", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}

					if (!ValidatorUtil.valiDisplayName(ele.getDisplayName())) {
						con.printToConsole(
								"\t显示名称格式不正确" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(WARNING, ele,
								"显示名称格式不正确", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getRemark() != null
						&& !ValidatorUtil.valiRemarkLength(ele.getRemark())) {
					con.printToConsole(
							"\t备注长度超过128个字符" + "    <Model>"
									+ ele.getFullName(), ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(WARNING, ele,
							"备注长度超过128个字符", ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			} else if (this.getId().equals("02")) {
				if (ele.getName() != null) {
					int i = 0;
					if (ele.getParent() != null) {
						for (Object bu : ele.getParent().getChildren()) {
							if (bu instanceof Enumeration) {
								if (((Enumeration) bu).getName().equals(
										ele.getName()))
									i++;
							}
						}
					}
					if (i > 1) {
						con.printToConsole(
								"\t名称不唯一" + "    <Model>" + ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele, "名称不唯一",
								ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getElements() != null) {
					List<EnumElement> listElements = ele.getElements();
					int i = 0;
					for (int m = 0; m < listElements.size(); m++) {
						for (int n = m + 1; n < listElements.size(); n++) {
							if (listElements.get(m).getKey()
									.equals(listElements.get(n).getKey()))
								i++;
						}
					}
					if (i > 0) {
						con.printToConsole(
								"\t枚举Key值不唯一" + "    <Model>"
										+ ele.getFullName(),
								ValidatorContext.ERROR);
						ValiResult rel = new ValiResult(ERROR, ele,
								"枚举Key值不唯一", ele.getFullName());
						resultList.add(rel);
						flag = false;
					}
				}
				if (ele.getElements() == null || ele.getElements().size() == 0) {
					con.printToConsole(
							"\t枚举中不包含任何元素" + "    <Model>" + ele.getFullName(),
							ValidatorContext.ERROR);
					ValiResult rel = new ValiResult(ERROR, ele, "枚举中不包含任何元素",
							ele.getFullName());
					resultList.add(rel);
					flag = false;
				}
			}
		}
	}

	private List<String> getAllIds(BusinessObjectModel businessObjectModel) {
		List<String> idList = new ArrayList<String>();
		idList.add(businessObjectModel.getId());
		for (AbstractModelElement ab : businessObjectModel.getAssociations()) {
			idList.add(ab.getId());
		}
		for (AbstractModelElement ab : businessObjectModel.getInheritances()) {
			idList.add(ab.getId());
		}
		for (AbstractModelElement ab : businessObjectModel.getPackages()) {
			idList.add(ab.getId());
		}
		for (Diagram ab : businessObjectModel.getDiagrams()) {
			idList.add(ab.getId());
			for (DiagramElement diaele : ab.getElements()) {
				idList.add(diaele.getId());
			}
		}
		for (BusinessClass ab : businessObjectModel.getBusinessClasses()) {
			idList.add(ab.getId());
			for (Property pro : ab.getProperties()) {
				idList.add(pro.getId());
			}
			for (PropertyGroup group : ab.getGroups()) {
				idList.add(group.getId());
			}
			for (BusinessOperation op : ab.getOperations()) {
				idList.add(op.getId());
			}
			for (BEPermission bp : ab.getPermissions()) {
				idList.add(bp.getId());
			}
			for (BEStatus bs : ab.getStatuses()) {
				idList.add(bs.getId());
			}
		}
		for (DataTransferObject dto : businessObjectModel.getDTOs()) {
			idList.add(dto.getId());
			for (Property pro : dto.getProperties()) {
				idList.add(pro.getId());
			}
		}
		for (Enumeration enu : businessObjectModel.getEnumerations()) {
			idList.add(enu.getId());
			for (EnumElement enumele : enu.getElements()) {
				idList.add(enumele.getId());
			}
		}
		for (ReferenceObject re : businessObjectModel.getReferenceObjects()) {
			idList.add(re.getId());
		}
		return idList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelectionValid() {
		return isSelectionValid;
	}

	public void setSelectionValid(boolean isSelectionValid) {
		this.isSelectionValid = isSelectionValid;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	
}
