package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.dialogs.BusinessClassRelationEditDialog;
import com.mqfdy.code.designer.editor.part.AnnotationEditPart;
import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.designer.editor.utils.ModuleExceptionLogOnly;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.AssociationUtil;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.utils.AssociationType;

/**
 * 创建连线
 * 
 * A command to create a connection between two NodeModelElement. The command
 * can be undone or redone.
 * <p>
 * This command is designed to be used together with a GraphicalNodeEditPolicy.
 * To use this command properly, following steps are necessary:
 * </p>
 * <ol>
 * <li>Create a subclass of GraphicalNodeEditPolicy.</li>
 * <li>Override the <tt>getConnectionCreateCommand(...)</tt> method, to create a
 * new instance of this class and put it into the CreateConnectionRequest.</li>
 * <li>Override the <tt>getConnectionCompleteCommand(...)</tt> method, to obtain
 * the Command from the ConnectionRequest, call setTarget(...) to set the target
 * endpoint of the connection and return this command instance.</li>
 * </ol>
 * 
 * @author mqfdy
 */
public class ConnectionCreateCommand extends Command {

	/** The connection instance. */
	private AbstractModelElement connection;

	/** Start point for the connection. */
	private final AbstractModelElement source;

	/** Target point for the connection. */
	private AbstractModelElement target;

	private String type;

	private EditPart sourceEditPart;

	private EditPart targetEditPart;

	/**
	 * 内置模型
	 */
	private BusinessObjectModel bom = null;

	private AbstractModelElement sourceBusinessClass;

	private AbstractModelElement targetBusinessClass;

	private boolean canRedo = true;

	private boolean canUndo = true;

	/**
	 * Instantiate a command that can create a connection between two
	 * NodeModelElements.
	 * 
	 * @param source
	 *            the source endpoint
	 * @param type
	 *            The type of the connection. See {@link IConstants}.
	 * @param editPart
	 * @throws IllegalArgumentException
	 *             if source is null
	 */
	public ConnectionCreateCommand(AbstractModelElement source, String type,
			EditPart editPart) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		this.source = source;
		this.type = type;
		this.sourceEditPart = editPart;
		// set the label used to describe this command to the user
		setLabel("create connection");

		// set source for this connection
	}

	public AbstractModelElement getConnection() {
		return connection;
	}

	@Override
	public boolean canExecute() {

		bom = BusinessModelManager.getBuildInOm();
		boolean isSourceBuiltin = false;
		boolean isTargetBuiltin = false;
		// source不能是内置对象
		if (type.compareTo(IConstants.LINK) != 0){
			if (source instanceof AbstractModelElement) {
				if (bom != null
						&& bom.getModelElementById(((DiagramElement) source)
								.getObjectId()) != null) {
					isSourceBuiltin = true;
				}
				if (BusinessModelUtil.getEditorBusinessModelManager()
						.queryObjectById(((DiagramElement) source).getObjectId()) instanceof ReferenceObject) {
					isSourceBuiltin = true;
				}
			}

			if (target instanceof AbstractModelElement) {
				if (bom != null
						&& bom.getModelElementById(((DiagramElement) target)
								.getObjectId()) != null) {
					isTargetBuiltin = true;
				}
				if (BusinessModelUtil.getEditorBusinessModelManager()
						.queryObjectById(((DiagramElement) target).getObjectId()) instanceof ReferenceObject) {
					isTargetBuiltin = true;
				}
			}
		}
		if (type.compareTo(IConstants.ASSOCIATION_STR1_1) == 0
				|| type.compareTo(IConstants.ASSOCIATION_STR1_N) == 0
				|| type.compareTo(IConstants.ASSOCIATION_STRN_1) == 0
				|| type.compareTo(IConstants.ASSOCIATION_STRN_N) == 0
				|| type.compareTo(IConstants.ASSOCIATION_STR) == 0
				|| type.compareTo(IConstants.AGGREGATION_STR) == 0
				|| type.compareTo(IConstants.COMPOSITION_STR) == 0) {
			if (isSourceBuiltin && isTargetBuiltin) {
				return false;
			}
			return checkRelation();
		}
		if (type.compareTo(IConstants.INHERITANCE) == 0) {
			if (isSourceBuiltin && isTargetBuiltin) {
				return false;
			}
			return checkInheritance();
		}
		if (type.compareTo(IConstants.LINK) == 0) {
			return checkLink();
		}
		return false;
	}

	private boolean checkRelation() {
		if (((source instanceof AbstractModelElement))
				&& ((target instanceof AbstractModelElement))) {
			// List<Association> list = new ArrayList<Association>();
			sourceBusinessClass = BusinessModelUtil
					.getEditorBusinessModelManager().queryObjectById(
							((DiagramElement) source).getObjectId());
			if(sourceBusinessClass == null)
				sourceBusinessClass = (AbstractModelElement) bom.getModelElementById(((DiagramElement) source)
					.getObjectId());
			if (sourceBusinessClass instanceof ReferenceObject) {
				sourceBusinessClass = ((ReferenceObject) sourceBusinessClass).getReferenceObject();
			}
			if (sourceBusinessClass instanceof BusinessClass) {
				if (!((BusinessClass) sourceBusinessClass).hasPkProperty())
					return false;
			}else
				return false;
			targetBusinessClass = BusinessModelUtil.getEditorBusinessModelManager()
					.queryObjectById(((DiagramElement) target).getObjectId());
			if(targetBusinessClass == null)
				targetBusinessClass = (AbstractModelElement) bom.getModelElementById(((DiagramElement) target)
					.getObjectId());
			if (targetBusinessClass instanceof ReferenceObject) {
				targetBusinessClass = ((ReferenceObject) targetBusinessClass).getReferenceObject();
			}
			if (targetBusinessClass instanceof BusinessClass) {
				if (!((BusinessClass) targetBusinessClass).hasPkProperty())
					return false;
			}else
				return false;
			
//			1 自定义与自定义之间无约束
//			2 自定义与反向之间无约束
//			3 自定义和引用 多对一 一对一
			if (BusinessModelUtil.isCustomObjectModel(sourceBusinessClass) && 
					IModelElement.STEREOTYPE_REFERENCE.equals(targetBusinessClass.getStereotype())) {
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STRN_1) != 0)
					return false;
			}
//			4 自定义与内置 多对一 一对一
			if (BusinessModelUtil.isCustomObjectModel(sourceBusinessClass) && 
					IModelElement.STEREOTYPE_BUILDIN.equals(targetBusinessClass.getStereotype())) {
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STRN_1) != 0)
					return false;
			}
//			5 反向与自定义不限制
//			6 反向与反向
			//反向实体与反向实体不可创建N-N关系
			if(IModelElement.STEREOTYPE_REVERSE.equals(sourceBusinessClass.getStereotype()) 
					&& IModelElement.STEREOTYPE_REVERSE.equals(targetBusinessClass.getStereotype())){
				if(type.compareTo(IConstants.ASSOCIATION_STRN_N) == 0)
					return false;
				return true;
			}
//			7 反向和引用 多对一 一对一
			if(IModelElement.STEREOTYPE_REVERSE.equals(sourceBusinessClass.getStereotype()) 
					&& IModelElement.STEREOTYPE_REFERENCE.equals(targetBusinessClass.getStereotype())){
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STRN_1) != 0)
					return false;
				return true;
			}
//			8 反向与内置 多对一 一对一
			if(IModelElement.STEREOTYPE_REVERSE.equals(sourceBusinessClass.getStereotype()) && 
					IModelElement.STEREOTYPE_BUILDIN.equals(targetBusinessClass.getStereotype())){
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STRN_1) != 0)
					return false;
				return true;
			}
//			9 引用与自定义 1-1  1-N
//			source是引用
			if (IModelElement.STEREOTYPE_REFERENCE.equals(sourceBusinessClass.getStereotype()) &&
					BusinessModelUtil.isCustomObjectModel(targetBusinessClass)) {
				// 引用1-1  1-N
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STR1_N) != 0) {
					return false;
				}
			}
//			10 引用与反向 1-1  1-N
			if (IModelElement.STEREOTYPE_REFERENCE.equals(sourceBusinessClass.getStereotype()) &&
					IModelElement.STEREOTYPE_REVERSE.equals(targetBusinessClass.getStereotype())) {
				// 引用1-1  1-N
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STR1_N) != 0) {
					return false;
				}
			}
//			11 引用和引用 不支持
//			12 引用与内置 不支持
			
//			13 内置与自定义1-1  1-N
			if (IModelElement.STEREOTYPE_BUILDIN.equals(sourceBusinessClass.getStereotype()) &&
					BusinessModelUtil.isCustomObjectModel(targetBusinessClass)) {
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STR1_N) != 0) {
					return false;
				}
			}
//			14 内置与反向1-1  1-N
			if (IModelElement.STEREOTYPE_BUILDIN.equals(sourceBusinessClass.getStereotype()) &&
					IModelElement.STEREOTYPE_REVERSE.equals(targetBusinessClass.getStereotype())) {
				// 引用1-1  1-N
				if (type.compareTo(IConstants.ASSOCIATION_STR1_1) != 0
						&& type.compareTo(IConstants.ASSOCIATION_STR1_N) != 0) {
					return false;
				}
			}
//			15 内置和引用 不支持
//			16 内置与内置 不支持

			return true;
		} else {
			return false;
		}
	}

	private boolean checkInheritance() {
		if (((source instanceof AbstractModelElement))
				&& ((target instanceof AbstractModelElement))) {
			List<Inheritance> list = new ArrayList<Inheritance>();
			if (target instanceof DiagramElement)
				list = BusinessModelUtil.getEditorBusinessModelManager()
						.getInheritancesByBusinessClass(
								(BusinessClass) BusinessModelUtil
										.getEditorBusinessModelManager()
										.queryObjectById(
												((DiagramElement) target)
														.getObjectId()));
			if (source instanceof DiagramElement)
				list.addAll(BusinessModelUtil.getEditorBusinessModelManager()
						.getInheritancesByBusinessClass(
								(BusinessClass) BusinessModelUtil
										.getEditorBusinessModelManager()
										.queryObjectById(
												((DiagramElement) source)
														.getObjectId())));
			if(target != null && source != null){ //针对 单个model自继承 做限制  add by hu
				if (target == source) {
						return false;
				}
			
			for (Inheritance conn : list) {
				 if
				 (conn.getChildClass().getId().equals(((DiagramElement)target).getObjectId()))//针对循环继承 做限制 add by hu
				 {
				 return false;
				 }
				 if(conn.getChildClass().getId().equals(((DiagramElement)source).getObjectId()))//针对多继承 做限制 add by hu
				 {
					 return false;
				 }
				if (conn.getChildClass().getId()
						.equals(((DiagramElement) target).getObjectId())
						&& conn.getParentClass()
								.getId()
								.equals(((DiagramElement) source).getObjectId())) {
					return false;
				}
				if (conn.getParentClass().getId()
						.equals(((DiagramElement) target).getObjectId())
						&& conn.getChildClass()
								.getId()
								.equals(((DiagramElement) source).getObjectId())) {
					return false;
				}
			 }	
			}
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkLink() {
		if (((source instanceof AbstractModelElement))
				&& ((target instanceof AbstractModelElement))) {
			List<LinkAnnotation> list = new ArrayList<LinkAnnotation>();
			BusinessObjectModel bm = BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
			list = bm.getLinkAnnotations();
			String tid = "";
			String sid = "";
			if(!(sourceEditPart instanceof AnnotationEditPart) && !(targetEditPart instanceof AnnotationEditPart))
				return false;
			if (target instanceof DiagramElement){
				tid = ((DiagramElement) target).getObjectId();
			}
			if (source instanceof DiagramElement){
				sid = ((DiagramElement) source).getObjectId();
			}
			if(target != null && source != null){
				if (target == source) {
						return false;
				}
				for (LinkAnnotation conn : list) {
					if (conn.getClassA().getId().equals(tid)
							&& conn.getClassB().getId().equals(sid)) {
						return false;
					}
					if (conn.getClassB().getId().equals(tid)
							&& conn.getClassA().getId().equals(sid)) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void execute() {
//		if(canExecute())
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();
		AssociationType asType = null;
		if (source != null && target != null /* && source != target */) {
			if (type.compareTo(IConstants.ASSOCIATION_STR) == 0) {
				connection = new Association();
			} else if (type.compareTo(IConstants.ASSOCIATION_STR1_1) == 0) {
				if (source == target)
					return;
				connection = new Association();
				asType = AssociationType.one2one;
			} else if (type.compareTo(IConstants.ASSOCIATION_STR1_N) == 0) {
				connection = new Association();
				asType = AssociationType.one2mult;
			} else if (type.compareTo(IConstants.ASSOCIATION_STRN_1) == 0) {
				connection = new Association();
				asType = AssociationType.mult2one;
			} else if (type.compareTo(IConstants.ASSOCIATION_STRN_N) == 0) {
				if (source == target)
					return;
				connection = new Association();
				asType = AssociationType.mult2mult;
			} else if (type.compareTo(IConstants.LINK) == 0) {
				if (source == target)
					return;
				connection = new LinkAnnotation();
				
				
				AbstractModelElement classA = null;
//				if (manager.queryObjectById(((DiagramElement) source)
//						.getObjectId()) instanceof BusinessClass)
					classA = (AbstractModelElement) manager
							.queryObjectById(((DiagramElement) source)
									.getObjectId());
//				else if (manager.queryObjectById(((DiagramElement) source)
//						.getObjectId()) instanceof ReferenceObject)
//					classA = (BusinessClass) ((ReferenceObject) manager
//							.queryObjectById(((DiagramElement) source)
//									.getObjectId())).getReferenceObject();
				if (classA == null) {
					classA = (AbstractModelElement) bom
							.getModelElementById(((DiagramElement) source)
									.getObjectId());
				}
				AbstractModelElement classB = null;
//				if (manager.queryObjectById(((DiagramElement) target)
//						.getObjectId()) instanceof BusinessClass)
					classB = (AbstractModelElement) manager
							.queryObjectById(((DiagramElement) target)
									.getObjectId());
//				else if (manager.queryObjectById(((DiagramElement) target)
//						.getObjectId()) instanceof ReferenceObject)
//					classB = (BusinessClass) ((ReferenceObject) manager
//							.queryObjectById(((DiagramElement) target)
//									.getObjectId())).getReferenceObject();
				if (classB == null) {
					classB = (AbstractModelElement) bom
							.getModelElementById(((DiagramElement) target)
									.getObjectId());
				}
				
				((LinkAnnotation) connection).setClassB(classB);
				((LinkAnnotation) connection).setClassA(classA);
				((LinkAnnotation) connection)
						.setBelongPackage(((DiagramElement) source)
								.getBelongDiagram().getBelongPackage());
			} else if (type.compareTo(IConstants.INHERITANCE) == 0) {
				connection = new Inheritance();
				((Inheritance) connection)
						.setParentClass((BusinessClass) manager
								.queryObjectById(((DiagramElement) target)
										.getObjectId()));
				((Inheritance) connection)
						.setChildClass((BusinessClass) manager
								.queryObjectById(((DiagramElement) source)
										.getObjectId()));
				((Inheritance) connection)
						.setBelongPackage(((DiagramElement) source)
								.getBelongDiagram().getBelongPackage());
				((Inheritance) connection).setName(BusinessModelUtil
						.getEditorBusinessModelManager()
						.generateNextInheritanceName());
				((Inheritance) connection).setPersistencePloy("2");
				((Inheritance) connection).setDisplayName(BusinessModelUtil
						.getEditorBusinessModelManager()
						.generateNextInheritanceName());
			} /*
			 * else if (type.compareTo(IConstants.COMPOSITION_STR) == 0) {
			 * connection = new RelationElement(); connection =
			 * RelationUtil.createRelation
			 * (((BusinessClassElement)source),((BusinessClassElement)target));
			 * }
			 */else {
				throw new ModuleExceptionLogOnly(
						"No matching connection type found");
			}
			if (connection != null && connection instanceof Association) {
				BusinessClass classA = null;
				if (manager.queryObjectById(((DiagramElement) source)
						.getObjectId()) instanceof BusinessClass)
					classA = (BusinessClass) manager
							.queryObjectById(((DiagramElement) source)
									.getObjectId());
				else if (manager.queryObjectById(((DiagramElement) source)
						.getObjectId()) instanceof ReferenceObject)
					classA = (BusinessClass) ((ReferenceObject) manager
							.queryObjectById(((DiagramElement) source)
									.getObjectId())).getReferenceObject();
				if (classA != null) {
					// ((Association)connection).setClassB(classB);
				} else {
					// ((Association)connection).setClassB((BusinessClass)
					// bom.getModelElementById(((DiagramElement)target).getObjectId()));
					classA = (BusinessClass) bom
							.getModelElementById(((DiagramElement) source)
									.getObjectId());
				}
				BusinessClass classB = null;
				if (manager.queryObjectById(((DiagramElement) target)
						.getObjectId()) instanceof BusinessClass)
					classB = (BusinessClass) manager
							.queryObjectById(((DiagramElement) target)
									.getObjectId());
				else if (manager.queryObjectById(((DiagramElement) target)
						.getObjectId()) instanceof ReferenceObject)
					classB = (BusinessClass) ((ReferenceObject) manager
							.queryObjectById(((DiagramElement) target)
									.getObjectId())).getReferenceObject();
				if (classB != null) {
					// ((Association)connection).setClassB(classB);
				} else {
					// ((Association)connection).setClassB((BusinessClass)
					// bom.getModelElementById(((DiagramElement)target).getObjectId()));
					classB = (BusinessClass) bom
							.getModelElementById(((DiagramElement) target)
									.getObjectId());
				}
				connection = AssociationUtil.generateAssociation(classA,
						classB, asType);
				ModelPackage pkg = ((DiagramElement) source)
						.getBelongDiagram().getBelongPackage();
				((Association) connection).setBelongPackage(pkg);
				if(IModelElement.STEREOTYPE_REVERSE.equals(sourceBusinessClass.getStereotype()) || IModelElement.STEREOTYPE_REVERSE.equals(targetBusinessClass.getStereotype())){
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell();
					final BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
							shell, (Association) connection, pkg,
							BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
					int i = dialog.open();
					if (i == IDialogConstants.OK_ID) {
//						redo();
					} else if (i == IDialogConstants.CANCEL_ID) {
						canRedo = false;
						canUndo = false;
					}
				}
				//弹出编辑框
//				ModelPackage pkg = ((DiagramElement) source)
//						.getBelongDiagram().getBelongPackage();
//				((Association) connection).setBelongPackage(pkg);
//				((Association) connection).setAssociationType(asType.getValue());
//				((Association) connection).setClassA(classA);
//				((Association) connection).setClassB(classB);
//				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//						.getShell();
////				final BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
////						shell, (Association) connection, pkg,
////						BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
//				BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
//						shell, (Association) connection, pkg,
//						BusinessModelEvent.MODEL_ELEMENT_ADD, "");
//				int i = dialog.open();
//				if (i == IDialogConstants.OK_ID) {
////					redo();
//				} else if (i == IDialogConstants.CANCEL_ID) {
//					canRedo = false;
//					canUndo = false;
//				}
				
				
//				connection = AssociationUtil.generateAssociation(classA,
//						classB, asType);
				//有一方是反向实体 弹出编辑框
//				if(IModelElement.STEREOTYPE_REVERSE.equals(sourceBusinessClass.getStereotype()) 
//						|| IModelElement.STEREOTYPE_REVERSE.equals(targetBusinessClass.getStereotype())){
					/*Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell();
					final BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
							shell, (Association) connection, pkg,
							BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
					
					int i = dialog.open();
					if (i == IDialogConstants.OK_ID) {
//						redo();
					} else if (i == IDialogConstants.CANCEL_ID) {
						canRedo = false;
						canUndo = false;
					}*/
//				}
				
			}
		}
		redo();
	}

	@Override
	public void redo() {
		if(canRedo){
			BusinessModelManager manager = BusinessModelUtil
					.getEditorBusinessModelManager();
			if (source != null && target != null) {
				if (connection != null) {
					manager.businessObjectModelChanged(new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_ADD, connection));
				}
			}
		}
	}

	/**
	 * Set the target endpoint for the connection.
	 * 
	 * @param target
	 *            that target endpoint (a non-null Shape instance)
	 * @param editPart2
	 * @throws IllegalArgumentException
	 *             if target is null
	 */
	public void setTarget(AbstractModelElement target, EditPart editPart) {
		if (target == null) {
			throw new IllegalArgumentException();
		}
		this.target = target;
		this.targetEditPart = editPart;
	}

	@Override
	public void undo() {
		if(canUndo){
			ConnectionDeleteCommand deleteCmd = new ConnectionDeleteCommand(
					connection, sourceEditPart, targetEditPart);
			deleteCmd.execute();
			sourceEditPart.refresh();
			targetEditPart.refresh();
		}
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return super.canUndo();
	}
	
}
