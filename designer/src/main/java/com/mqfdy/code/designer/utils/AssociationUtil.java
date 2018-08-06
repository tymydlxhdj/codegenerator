package com.mqfdy.code.designer.utils;

import java.util.List;
import java.util.Locale;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.AssociationType;

/**
 * 关联关系创建初始化
 * 
 * @author mqfdy
 * 
 */
public class AssociationUtil {

	public static Association generateAssociation(BusinessClass businessClassA,
			BusinessClass businessClassB, AssociationType associationType) {
		Association association = new Association();
		if (businessClassA != null && businessClassB != null) {
			setKnowed(association, businessClassA, businessClassB,
					associationType);
			updateAssociation(association, associationType);
			// 当新建图形时，A和B都不为空
			processBuildIn(association, businessClassA, businessClassB);
			processReference(association, businessClassA, businessClassB);
			processReverse(association, businessClassA, businessClassB);
			processCustom(association, businessClassA, businessClassB);
			processSelfAss(association, businessClassA, businessClassB,associationType);
		}
		association.setAssociationType(associationType.getValue());
		return association;
	}
	
	public static void processSelfAss(Association association,
			BusinessClass businessClassA, BusinessClass businessClassB,AssociationType associationType) {
		if (businessClassA != null && businessClassB != null
				&& businessClassA.getId().equals(businessClassB.getId())) {
			// 自关联
			if (AssociationType.one2mult.equals(associationType)) {
				association.setNavigateToClassB(true);
				association.setNavigateToClassA(true);
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, "children"));
				association.setNavigateToClassARoleName(getNewRoleName(
						businessClassB, "parent"));
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB,
						getNewDbColumnName(businessClassB, "PARENT_ID"));
			}

			if (AssociationType.mult2one.equals(associationType)) {
				association.setNavigateToClassB(true);
				association.setNavigateToClassA(true);
				association.setNavigateToClassARoleName(getNewRoleName(businessClassB, "children"));
				association.setNavigateToClassBRoleName(getNewRoleName(businessClassA, "parent"));
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA,
						getNewDbColumnName(businessClassA, "PARENT_ID"));
			}
		}
	}
	
	/**
	 * 处理出现反向对象的情况
	 * 
	 * @param association
	 * @param businessClassA
	 * @param businessClassB
	 */
	public static void processReverse(Association association,
			BusinessClass businessClassA, BusinessClass businessClassB) {
	
//		自定义实体与反向实体的关联关系约束
//	    1，可建多对多关系，与自定义实体之间多对多关系一致，均可编辑。
//	    2，可建一对多（多对一关系），但自定义必须为多方。单双向均可。
//			与自定义实体之间多对多关系一致，均可编辑。
//	    3，可建一对一关系，但必须限制外键在自定义实体中创建。
//			即，双向的话主控端在反向实体一边；单向的话只能由自定义实体导肮到反向实体。
		if (IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
			// 当A是反向实体时
			association.setNavigateToClassB(true);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINA, Association.TRUE);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINB, Association.FALSE);

			if (AssociationType.one2one.getValue().equals(association.getAssociationType())) {
				association.setMajorClassId(businessClassA.getId());
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())));
				
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.FALSE);
			}
			if (AssociationType.one2mult.getValue().equals(association.getAssociationType())) {
			
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.TRUE);
				
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, businessClassB.getName()
								.toLowerCase(Locale.getDefault())+ "s"));
			}
			if (AssociationType.mult2one.getValue().equals(association.getAssociationType())) {
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.FALSE);
				
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, businessClassB.getName()
								.toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.mult2mult.getValue().equals(association.getAssociationType())) {
				association.setNavigateToClassARoleName(getNewRoleName(
						businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())
								+ "s"));
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, businessClassB.getName()
								.toLowerCase(Locale.getDefault())+ "s"));
			}
		}
		if (IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())) {
			// 当B是反向时
			association.setMajorClassId(businessClassB.getId());
			association.setNavigateToClassA(true);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINA, Association.FALSE);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINB, Association.TRUE);

			if (AssociationType.one2one.getValue().equals(association.getAssociationType())) {
				
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())));
				
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA, "");
			}
			if (AssociationType.one2mult.getValue().equals(association.getAssociationType())) {
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA, "");
				
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault()) ));
			}
			if (AssociationType.mult2one.getValue().equals(association.getAssociationType())) {
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB, "");
				
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault()) + "s"));
			}
			if (AssociationType.mult2mult.getValue().equals(association.getAssociationType())) {
				association.setNavigateToClassARoleName(getNewRoleName(
						businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault()) + "s"));
			}
		}

	}
	
	
	public static void processCustom(Association association,
			BusinessClass businessClassA, BusinessClass businessClassB) {
	
		if (IModelElement.STEREOTYPE_CUSTOM.equals(businessClassA.getStereotype())) {
			// 当A是自定义实体时
			association.setNavigateToClassB(true);
			
			if (AssociationType.one2one.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(
						getNewRoleName(businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.one2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(
						getNewRoleName(businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())+"s"));
			}
			if (AssociationType.mult2one.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(
						getNewRoleName(businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.mult2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(
						getNewRoleName(businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())+"s"));
			}
			
		}
		if (IModelElement.STEREOTYPE_CUSTOM.equals(businessClassB.getStereotype())) {
			association.setNavigateToClassA(true);
		
			if (AssociationType.one2one.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.one2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.mult2one.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())+"s"));
			}
			if (AssociationType.mult2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassARoleName(
						getNewRoleName(businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())+"s"));
			}
		}
	}

	/**
	 * 处理出现内置对象的情况
	 * 
	 * @param association
	 * @param businessClassA
	 * @param businessClassB
	 */
	public static void processBuildIn(Association association,
			BusinessClass businessClassA, BusinessClass businessClassB) {
		if (IModelElement.STEREOTYPE_BUILDIN.equals(businessClassA
				.getStereotype())) {
			// 当A是内置时
			association.setMajorClassId(businessClassB.getId());
			association.setNavigateToClassB(false);
			association.setNavigateToClassA(true);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINA, Association.FALSE);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINB, Association.TRUE);
			association.setNavigateToClassBRoleName("");

			if (AssociationType.one2one.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassARoleName(getNewRoleName(
								businessClassB, businessClassA.getName()
										.toLowerCase(Locale.getDefault())));
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA, "");
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB,
						getNewDbColumnName(businessClassB, (businessClassA
								.getName() + "_ID").toUpperCase(Locale.getDefault())));
			}
			if (AssociationType.one2mult.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassARoleName(getNewRoleName(
								businessClassB, businessClassA.getName()
										.toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.mult2one.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassARoleName(getNewRoleName(
						businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())
								+ "s"));
			}
			if (AssociationType.mult2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassARoleName(getNewRoleName(
						businessClassB, businessClassA.getName().toLowerCase(Locale.getDefault())
								+ "s"));
			}
		}
		if (IModelElement.STEREOTYPE_BUILDIN.equals(businessClassB
				.getStereotype())) {
			// 当B是内置时
			association.setMajorClassId(businessClassB.getId());
			association.setNavigateToClassB(true);
			association.setNavigateToClassA(false);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINA, Association.TRUE);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINB, Association.FALSE);
			association.setNavigateToClassARoleName("");

			if (AssociationType.one2one.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassBRoleName(getNewRoleName(
								businessClassA, businessClassB.getName()
										.toLowerCase(Locale.getDefault())));

				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB, "");
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA,
						getNewDbColumnName(businessClassA, (businessClassB
								.getName() + "_ID").toUpperCase(Locale.getDefault())));
			}
			if (AssociationType.one2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())
								+ "s"));
			}
			if (AssociationType.mult2one.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassBRoleName(getNewRoleName(
								businessClassA, businessClassB.getName()
										.toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.mult2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())
								+ "s"));
			}
		}

	}

	/**
	 * 处理引用模型
	 * 
	 * @param association
	 * @param businessClassA
	 * @param businessClassB
	 */
	public static void processReference(Association association,
			BusinessClass businessClassA, BusinessClass businessClassB) {

		if (IModelElement.STEREOTYPE_REFERENCE.equals(businessClassA
				.getStereotype())) {
			// 当A是引用实体时
			association.setMajorClassId(businessClassB.getId());
			association.setNavigateToClassB(false);
			association.setNavigateToClassA(true);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINA, Association.FALSE);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINB, Association.TRUE);
			association.setNavigateToClassBRoleName("");

			if (AssociationType.one2one.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassARoleName(getNewRoleName(
								businessClassB, businessClassA.getName()
										.toLowerCase(Locale.getDefault())));
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA, "");
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB,
						getNewDbColumnName(businessClassB, (businessClassA
								.getName() + "_ID").toUpperCase(Locale.getDefault())));
			}
			if (AssociationType.one2mult.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassARoleName(getNewRoleName(
								businessClassB, businessClassA.getName()
										.toLowerCase(Locale.getDefault())));
			}

		}
		if (IModelElement.STEREOTYPE_REFERENCE.equals(businessClassB
				.getStereotype())) {
			// 当B是引用时
			association.setMajorClassId(businessClassA.getId());
			association.setNavigateToClassB(true);
			association.setNavigateToClassA(false);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINA, Association.TRUE);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINB, Association.FALSE);
			association.setNavigateToClassARoleName("");

			if (AssociationType.one2one.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassBRoleName(getNewRoleName(
								businessClassA, businessClassB.getName()
										.toLowerCase(Locale.getDefault())));

				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, Association.TRUE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, Association.FALSE);
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB, "");
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA,
						getNewDbColumnName(businessClassA, (businessClassB
								.getName() + "_ID").toUpperCase(Locale.getDefault())));
			}
			if (AssociationType.one2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())
								+ "s"));
			}
			if (AssociationType.mult2one.getValue().equals(
					association.getAssociationType())) {
				association
						.setNavigateToClassBRoleName(getNewRoleName(
								businessClassA, businessClassB.getName()
										.toLowerCase(Locale.getDefault())));
			}
			if (AssociationType.mult2mult.getValue().equals(
					association.getAssociationType())) {
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, businessClassB.getName().toLowerCase(Locale.getDefault())
								+ "s"));
			}
		}

	}

	/**
	 * 2
	 * 
	 * @return
	 */
	private static void updateAssociation(Association association,
			AssociationType associationType) {
		String classA = "";
		String classB = "";
		if (association.getClassA() != null) {
			classA = association.getClassA().getId();
		}
		if (association.getClassB() != null) {
			classB = association.getClassB().getId();
		}
		if (AssociationType.one2one.equals(associationType)) {
			setAssociation(association, classA, true, false, false, false,
					true, false);
			association.setMajorClassId(classA != null ? classA : "");
		}
		if (AssociationType.one2mult.equals(associationType)) {
			setAssociation(association, classA, true, true, false, false,
					false, true);
		}
		if (AssociationType.mult2one.equals(associationType)) {
			setAssociation(association, classB, true, true, false, false,
					true, false);
		}
		if (AssociationType.mult2mult.equals(associationType)) {
			setAssociation(association, classA, true, true, false, false,
					false, false);
			association.setMajorClassId(classA != null ? classA : "");
			association.getPersistencePloyParams().put(
					Association.RELATIONTABLENAME,
					generateTableName(association.getClassA(),
							association.getClassB()));
		}
	}

	private static void setKnowed(Association association,
			BusinessClass businessClassA, BusinessClass businessClassB,
			AssociationType associationType) {
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();
		String newName = manager.generateNextAssName();

		association.setName(newName);
		association.setDisplayName(newName);
		association.setClassA(businessClassA);
		association.setClassB(businessClassB);
		association.setAssociationType(associationType.getValue());
		association.setNavigateToClassB(true);
		association.setNavigateToClassA(false);

		if (businessClassA != null && businessClassB != null
				&& businessClassA.getId().equals(businessClassB.getId())) {
			// 自关联
			if (AssociationType.one2mult.equals(associationType)) {
				association.setNavigateToClassB(true);
				association.setNavigateToClassA(true);
				association.setNavigateToClassBRoleName(getNewRoleName(
						businessClassA, "children"));
				association.setNavigateToClassARoleName(getNewRoleName(
						businessClassB, "parent"));
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB,
						getNewDbColumnName(businessClassB, "PARENT_ID"));
			}

			if (AssociationType.mult2one.equals(associationType)) {
				association.setNavigateToClassB(true);
				association.setNavigateToClassA(true);
				association.setNavigateToClassARoleName(getNewRoleName(businessClassB, "children"));
				association.setNavigateToClassBRoleName(getNewRoleName(businessClassA, "parent"));
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA,
						getNewDbColumnName(businessClassA, "PARENT_ID"));
			}
		} else {
			// 不是自关联
			if (businessClassB != null) {
				if (AssociationType.one2one.equals(associationType)) {
					association.setNavigateToClassBRoleName(getNewRoleName(
							businessClassA, businessClassB.getName()
									.toLowerCase(Locale.getDefault())));
					association.getPersistencePloyParams().put(
							Association.FOREIGNKEYCOLUMNINA,
							getNewDbColumnName(businessClassA, (businessClassB
									.getName() + "_ID").toUpperCase(Locale.getDefault())));
				}
				if (AssociationType.one2mult.equals(associationType)) {
					association.setNavigateToClassB(true);
					association.setNavigateToClassA(true);
					association.setNavigateToClassBRoleName(getNewRoleName(
							businessClassA, businessClassB.getName()
									.toLowerCase(Locale.getDefault()) + "s"));
					association.setNavigateToClassARoleName(getNewRoleName(
							businessClassB, businessClassA.getName()
									.toLowerCase(Locale.getDefault())));
					association.getPersistencePloyParams().put(
							Association.FOREIGNKEYCOLUMNINB,
							getNewDbColumnName(businessClassB, (businessClassA
									.getName() + "_ID").toUpperCase(Locale.getDefault())));
				}
				if (AssociationType.mult2one.equals(associationType)) {
					association.setNavigateToClassB(true);
					association.setNavigateToClassA(true);
					association.setNavigateToClassBRoleName(getNewRoleName(
							businessClassA, businessClassB.getName()
									.toLowerCase(Locale.getDefault())));
					association.setNavigateToClassARoleName(getNewRoleName(
							businessClassB, businessClassA.getName()
									.toLowerCase(Locale.getDefault()) + "s"));
					association.getPersistencePloyParams().put(
							Association.FOREIGNKEYCOLUMNINA,
							getNewDbColumnName(businessClassA, (businessClassB
									.getName() + "_ID").toUpperCase(Locale.getDefault())));
				}
				if (AssociationType.mult2mult.equals(associationType)) {
					association.setNavigateToClassB(true);
					association.setNavigateToClassA(true);
					association.setMajorClassId(businessClassA.getId());
					association.setNavigateToClassBRoleName(getNewRoleName(
							businessClassA, businessClassB.getName()
									.toLowerCase(Locale.getDefault()) + "s"));
					association.setNavigateToClassARoleName(getNewRoleName(
							businessClassB, businessClassA.getName()
									.toLowerCase(Locale.getDefault()) + "s"));
					association.getPersistencePloyParams().put(
							Association.FOREIGNKEYCOLUMNINA,
							getNewDbColumnName(businessClassA, (businessClassB
									.getName() + "_ID").toUpperCase(Locale.getDefault())));
					association.getPersistencePloyParams().put(
							Association.FOREIGNKEYCOLUMNINB,
							getNewDbColumnName(businessClassB, (businessClassA
									.getName() + "_ID").toUpperCase(Locale.getDefault())));
				}
			}
		}
	}
	/**
	 * 设置默认值
	 * @param association
	 * @param majorClassId 主控端
	 * @param navigateToClassB 导航到B
	 * @param navigateToClassA 导航到A
	 * @param cascadeDeleteClassB 级联删除B
	 * @param cascadeDeleteClassA 级联删除A
	 * @param createFKA 在实体A中创建外键
	 * @param createFKB 在实体B中创建外键
	 */
	private static void setAssociation(Association association,
			String majorClassId, boolean navigateToClassB,
			boolean navigateToClassA, boolean cascadeDeleteClassB,
			boolean cascadeDeleteClassA, boolean createFKA, boolean createFKB) {
		association.setMajorClassId(majorClassId);
		association.setNavigateToClassB(navigateToClassB);
		association.setNavigateToClassA(navigateToClassA);
		association.setCascadeDeleteClassB(cascadeDeleteClassB);
		association.setCascadeDeleteClassA(cascadeDeleteClassA);
		// Association.TRUE

		association.getPersistencePloyParams().put(Association.FOREIGNKEYINA,
				createFKA ? Association.TRUE : Association.FALSE);
		association.getPersistencePloyParams().put(Association.FOREIGNKEYINB,
				createFKB ? Association.TRUE : Association.FALSE);
	}
	/**
	 * 计算导航属性名
	 * @param businessClass
	 * @param anotherClassName
	 * @return
	 */
	public static String getNewRoleName(BusinessClass businessClass,
			String anotherClassName) {
		return getNewRoleName(businessClass, anotherClassName, 0);
	}
	/**
	 * 计算导航属性名
	 * @param businessClass
	 * @param anotherClassName
	 * @param count
	 * @return
	 */
	private static String getNewRoleName(BusinessClass businessClass,
			String anotherClassName, int count) {
		String resultName = anotherClassName;
		if (count > 0) {
			resultName = resultName + "_" + count;
		}
		if (businessClass != null) {
			List<Property> properties = businessClass.getProperties();
			if (properties != null) {
				// int count = 0;
				for (int i = 0; i < properties.size(); i++) {
					Property property = properties.get(i);
					if (property instanceof PersistenceProperty
							&& resultName.equalsIgnoreCase(property.getName())) {
						count++;
						resultName = getNewRoleName(businessClass,anotherClassName, count);
					}
				}
				for (Association association : BusinessModelUtil
						.getEditorBusinessModelManager()
						.getBusinessObjectModel().getAssociations()) {
					if (association.getClassA() == businessClass
							&& resultName.equalsIgnoreCase(association
									.getNavigateToClassBRoleName())) {
						count++;
						resultName = getNewRoleName(businessClass,anotherClassName, count);
					} else if (association.getClassB() == businessClass
							&& resultName.equalsIgnoreCase(association
									.getNavigateToClassARoleName())) {
						count++;
						resultName = getNewRoleName(businessClass,anotherClassName, count);
					}
				}
			}
		}
		return resultName.toLowerCase(Locale.getDefault());
	}
	/**
	 * 外键字段名
	 * @param businessClass
	 * @param newName
	 * @return
	 */
	private static String getNewDbColumnName(BusinessClass businessClass,
			String newName) {
		String resultName = newName;
		if (businessClass != null) {
			List<Property> properties = businessClass.getProperties();
			if (properties != null) {
				int count = 0;
				for (int i = 0; i < properties.size(); i++) {
					Property property = properties.get(i);
					if (property instanceof PersistenceProperty
							&& resultName.equalsIgnoreCase(((PersistenceProperty) property)
											.getdBColumnName())) {
						count++;
						resultName = getNewRoleName(businessClass, newName
								+ "_" + count);
					}
				}
			}
		}
		return getNewFKColumnName(businessClass, resultName.toUpperCase(Locale.getDefault()));
	}
	/**
	 * 外键字段名
	 * @param businessClass
	 * @param newName
	 * @return
	 */
	private static String getNewFKColumnName(BusinessClass businessClass,
			String newName) {
		String resultName = newName;
		if (businessClass != null) {
			BusinessModelManager manager = BusinessModelUtil
					.getEditorBusinessModelManager();
			List<Association> asList = manager.getBusinessObjectModel()
					.getAssociations();
			int count = 0;
			for (Association as : asList) {
				if (as.getClassA() == businessClass) {
					if (newName.equalsIgnoreCase(as.getPersistencePloyParams()
							.get(Association.FOREIGNKEYCOLUMNINA))) {
						count++;
						resultName = getNewFKColumnName(businessClass, newName
								+ "_" + count);
					}

				}
				if (as.getClassB() == businessClass) {
					if (newName.equalsIgnoreCase(as.getPersistencePloyParams()
							.get(Association.FOREIGNKEYCOLUMNINB))) {
						count++;
						resultName = getNewFKColumnName(businessClass, newName
								+ "_" + count);
					}
				}
			}
		}
		return resultName.toUpperCase(Locale.getDefault());
	}
	/**
	 * 计算中间表名
	 * @param businessClassA
	 * @param businessClassB
	 * @return
	 */
	private static String generateTableName(BusinessClass businessClassA,
			BusinessClass businessClassB) {
		StringBuffer tableName = new StringBuffer();
		if (businessClassA != null && businessClassA.getName() != null) {
			String aName = businessClassA.getName();
			if(aName.length() > 12){
				aName = aName.substring(0,12);
			}
			tableName.append(aName);
		}
		if (businessClassB != null && businessClassB.getName() != null) {
			if (tableName.length() > 0) {
				tableName.append("_");
			}
			String bName = businessClassB.getName();
			if(bName.length() > 12){
				bName = bName.substring(0,12);
			}
			tableName.append(bName);
		}
		return generateNextMiddleTableName(tableName.toString());
	}
	public static String generateNextMiddleTableName(String ref) {
		String name = ref;
		BusinessModelManager manager = BusinessModelUtil.getEditorBusinessModelManager();
		List<BusinessClass> list = manager
				.getBusinessObjectModel().getBusinessClasses();
		if (list == null) {
			return ref;
		}
		int size = 0;

		while (manager.isExistTableName(name, list) || 
				manager.isExistMiddleTableName(name, manager.getBusinessObjectModel().getAssociations())) {
			size++;
			name = ref + size;
		}
		return name;
	}
}
