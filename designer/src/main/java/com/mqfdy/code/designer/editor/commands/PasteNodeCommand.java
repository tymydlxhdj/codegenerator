package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.part.AnnotationEditPart;
import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.EnumerationEditPart;
import com.mqfdy.code.designer.editor.part.LinkAnnoEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.editor.part.ReferenceObjectEditPart;
import com.mqfdy.code.designer.editor.part.RelationEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Annotation;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

// TODO: Auto-generated Javadoc
/**
 * 粘贴图形.
 *
 * @author mqfdy
 */

public class PasteNodeCommand extends Command {
	
	/** The edit part list. */
	// 内存中复制或剪切的对象AbstractGraphicalEditPart
	List editPartList = new ArrayList();
	
	/** The new bu list. */
	// 复制出的对象
	List<AbstractModelElement> newBuList = new ArrayList<AbstractModelElement>();
	
	/** The new con list. */
	// 复制出的关联关系
	List<AbstractModelElement> newConList = new ArrayList<AbstractModelElement>();
	
	/** The new dia list. */
	// 粘贴对象的图元
	List<DiagramElement> newDiaList = new ArrayList<DiagramElement>();
	
	/** The bu map. */
	// 新旧模型对象的Map
	Map<AbstractModelElement, AbstractModelElement> buMap = new HashMap<AbstractModelElement, AbstractModelElement>();
	// private DiagramElement newDiaEle;
	// private BusinessClass newBu;
	/** The px. */
	// 计算偏移
	private int px = 0;
	
	/** The py. */
	private int py = 0;

	/**
	 * Instantiates a new paste node command.
	 */
	public PasteNodeCommand() {
		super();
		if (Clipboard.getDefault().getContents() != null)
			editPartList.addAll((List<AbstractGraphicalEditPart>) Clipboard
					.getDefault().getContents());
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		if (editPartList.isEmpty())
			return false;
		
		for(Object editpart : editPartList){
			if(editpart instanceof NodeEditPart || editpart instanceof OmConnectionEditPart){
				
			}else{
				//有模型驱动外的其他模型，则不允许粘贴
				return false;
			}
		}
		
		if(BusinessModelUtil.getBusinessModelDiagramEditor() != null && BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts().size()>0)
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		if (!canExecute())
			return;
		redo();
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		Diagram diagram = ((Diagram) BusinessModelUtil
				.getBusinessModelDiagramEditor().getViewer().getContents()
				.getModel());
		ModelPackage pck = diagram.getBelongPackage();
		Diagram oldDiagram = null;
		for (Object obj : editPartList) {
			AbstractGraphicalEditPart editPart = (AbstractGraphicalEditPart)obj;
			if (editPart instanceof ReferenceObjectEditPart) {
				if (oldDiagram == null)
					oldDiagram = (Diagram) ((DiagramEditPart) editPart
							.getParent()).getModel();
				// 判断目标对象是否包含此业务实体的引用 包含则只复制图形 不包含则新建一个引用对象
				BusinessObjectModel businessObjectModel = BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager().getBusinessObjectModel();
				boolean flag = false;
				ReferenceObject busref = null;
				for (ReferenceObject ref : businessObjectModel
						.getReferenceObjects()) {
					if (ref.getReferenceObjectId().equals(
							((ReferenceObjectEditPart) editPart)
									.getReferenceObject()
									.getReferenceObjectId())) {
						flag = true;
						busref = ref;
					}
				}
				boolean flag1 = false;
				BusinessClass bus = null;
				for (BusinessClass bu : BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager().getBusinessObjectModel()
						.getBusinessClasses()) {
					if (bu.getId().equals(
							((ReferenceObjectEditPart) editPart)
									.getReferenceObject().getReferenceObject()
									.getId())
							&& bu.getName().equals(
									((ReferenceObjectEditPart) editPart)
											.getReferenceObject()
											.getReferenceObject().getName())) {
						flag1 = true;
						bus = bu;
					}
				}
				if (bus != null)
					buMap.put(
							(BusinessClass) ((ReferenceObjectEditPart) editPart)
									.getReferenceObject().getReferenceObject(),
							bus);
				else if (busref != null)
					buMap.put(
							(BusinessClass) ((ReferenceObjectEditPart) editPart)
									.getReferenceObject().getReferenceObject(),
							(BusinessClass) busref.getReferenceObject());
				else
					buMap.put(
							(BusinessClass) ((ReferenceObjectEditPart) editPart)
									.getReferenceObject().getReferenceObject(),
							(BusinessClass) ((ReferenceObjectEditPart) editPart)
									.getReferenceObject().getReferenceObject());

				ReferenceObject ref = null;
				// 新建一个引用对象
				if (!flag && !flag1) {
					ref = new ReferenceObject(
							((ReferenceObjectEditPart) editPart)
									.getReferenceObject().getReferenceObject());
					ref.setBelongPackage(pck);
					newBuList.add(ref);
					BusinessModelEvent event1 = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_ADD, ref);
					BusinessModelUtil.getEditorBusinessModelManager()
							.businessObjectModelChanged(event1);
					// buMap.put((BusinessClass) ((ReferenceObjectEditPart)
					// editPart).getReferenceObject().getReferenceObject(),
					// (BusinessClass) ((ReferenceObjectEditPart)
					// editPart).getReferenceObject().getReferenceObject());
				}
				if (!flag1
						&& diagram
								.getElementById(((ReferenceObjectEditPart) editPart)
										.getReferenceObject().getId()) == null) {
					if (ref == null) {
						copyDiagramElement(editPart, diagram,
								((ReferenceObjectEditPart) editPart)
										.getReferenceObject().getId());
						((DiagramEditPart) BusinessModelUtil
								.getBusinessModelDiagramEditor().getViewer()
								.getContents()).firePropertyChange(
								NodeModelElement.CHILD_ADDED_PROP, null,
								((ReferenceObjectEditPart) editPart)
										.getReferenceObject());
					} else {
						copyDiagramElement(editPart, diagram, ref.getId());
						((DiagramEditPart) BusinessModelUtil
								.getBusinessModelDiagramEditor().getViewer()
								.getContents()).firePropertyChange(
								NodeModelElement.CHILD_ADDED_PROP, null, ref);
					}
				}
			}
			else if (editPart instanceof BusinessClassEditPart) {
				if (oldDiagram == null)
					oldDiagram = ((DiagramElement) ((BusinessClassEditPart) editPart)
							.getModel()).getBelongDiagram();
				// oldDiagram = (Diagram)
				// ((DiagramEditPart)editPart.getParent()).getModel();
				if (BusinessClass.STEREOTYPE_BUILDIN
						.equals(((BusinessClassEditPart) editPart)
								.getBusinessClass().getStereotype())) {
					if (diagram
							.getElementById(((BusinessClassEditPart) editPart)
									.getBusinessClass().getId()) == null) {
						copyDiagramElement(editPart, diagram,
								((BusinessClassEditPart) editPart)
										.getBusinessClass().getId());
						((DiagramEditPart) BusinessModelUtil
								.getBusinessModelDiagramEditor().getViewer()
								.getContents()).firePropertyChange(
								NodeModelElement.CHILD_ADDED_PROP, null,
								((BusinessClassEditPart) editPart)
										.getBusinessClass());
					}
				} else {
					BusinessClass newBu = ((BusinessClassEditPart) editPart)
							.getBusinessClass().cloneChangeId();
					String name = BusinessModelUtil
							.getEditorBusinessModelManager()
							.generateNextBusinessClassName(newBu.getName());
					newBu.setTableName(BusinessModelUtil
							.getEditorBusinessModelManager()
							.generateNextBusinessClassTableName(
									newBu.getTableName()));
					String disName = BusinessModelUtil
							.getEditorBusinessModelManager()
							.generateNextBusinessClassDisName(newBu.getDisplayName());
					
//					if (!name.equals(newBu.getName())) {
					newBu.setDisplayName(disName);
//					}
					newBu.setName(name);
					newBu.setBelongPackage(pck);
					buMap.put(((BusinessClassEditPart) editPart)
							.getBusinessClass(), newBu);
					BusinessModelEvent bcAddevent = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_ADD, newBu);
					BusinessModelUtil.getEditorBusinessModelManager()
							.businessObjectModelChanged(bcAddevent);
					newBuList.add(newBu);
					copyDiagramElement(editPart, diagram, newBu.getId());
					((DiagramEditPart) BusinessModelUtil
							.getBusinessModelDiagramEditor().getViewer()
							.getContents()).firePropertyChange(
							NodeModelElement.CHILD_ADDED_PROP, null, newBu);
				}
			}else if (editPart instanceof EnumerationEditPart) {
				if (oldDiagram == null){
					oldDiagram = ((DiagramElement) ((EnumerationEditPart) editPart)
							.getModel()).getBelongDiagram();
				}
				Enumeration newEnum = ((EnumerationEditPart) editPart)
						.getEnumeration().cloneChangeId();
				String name = BusinessModelUtil
						.getEditorBusinessModelManager()
						.generateNextEnumerationName(newEnum.getName());
				newEnum.setName(name);
				String disName = BusinessModelUtil
						.getEditorBusinessModelManager()
						.generateNextEnumerationDisName(newEnum.getDisplayName());
				newEnum.setDisplayName(disName);
				newEnum.setBelongPackage(pck);
				buMap.put(((EnumerationEditPart) editPart)
						.getEnumeration(), newEnum);
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newEnum);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
				newBuList.add(newEnum);
				copyDiagramElement(editPart, diagram, newEnum.getId());
				((DiagramEditPart) BusinessModelUtil
						.getBusinessModelDiagramEditor().getViewer()
						.getContents()).firePropertyChange(
						NodeModelElement.CHILD_ADDED_PROP, null, newEnum);
			}
			// 注释
			else if (editPart instanceof AnnotationEditPart) {
				if (oldDiagram == null){
					oldDiagram = ((DiagramElement) ((AnnotationEditPart) editPart)
							.getModel()).getBelongDiagram();
				}
				Annotation newAnno = ((AnnotationEditPart) editPart)
						.getAnno().cloneChangeId();
				newAnno.setBelongPackage(pck);
				buMap.put(((AnnotationEditPart) editPart)
						.getAnno(), newAnno);
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newAnno);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
				newBuList.add(newAnno);
				copyDiagramElement(editPart, diagram, newAnno.getId());
				((DiagramEditPart) BusinessModelUtil
						.getBusinessModelDiagramEditor().getViewer()
						.getContents()).firePropertyChange(
						NodeModelElement.CHILD_ADDED_PROP, null, newAnno);
			}
		}
		for (Object obj : editPartList) {
			AbstractGraphicalEditPart editPart = (AbstractGraphicalEditPart)obj;
			if (editPart instanceof RelationEditPart) {
				Association as = ((RelationEditPart) editPart).getAssociation()
						.clone();

				DiagramElement newDiaEle = new DiagramElement();
				ElementStyle newStyle = new ElementStyle();
				if (oldDiagram != null && oldDiagram.getElementById(((RelationEditPart) editPart)
						.getAssociation().getId()) != null) {
					ElementStyle style = oldDiagram.getElementById(
							((RelationEditPart) editPart).getAssociation()
									.getId()).getStyle();
					newStyle.setHeight(style.getHeight());
					newStyle.setWidth(style.getWidth());
					newStyle.setPositionX(style.getPositionX());
					newStyle.setPositionY(style.getPositionY());
					newStyle.setEndPositionX(style.getEndPositionX());
					newStyle.setEndPositionY(style.getEndPositionY());
					String l = "";
					if (!style.getPointList().equals("")) {
						String[] s = style.getPointList().split(",");
						for (int i = 0; i < s.length; i++) {
							String[] po = s[i].split(" ");
							int x = Integer.parseInt(po[0].replace("(", ""));
							int y = Integer.parseInt(po[1].replace(")", ""));
							l = (l.equals("") ? "" : l + ",") + "(" + (x + px)
									+ " " + (y + py) + ")";
						}
					}
					newStyle.setPointList(l);
					newDiaEle.setStyle(newStyle);
					newDiaEle.setObjectId(as.getId());
					newDiaEle.setBelongDiagram(diagram);
					// if(diagram.getElementById(buMap.get(as.getClassA()).getId())
					// != null &&
					// diagram.getElementById(buMap.get(as.getClassB()).getId())
					// != null ){
					diagram.addElement(newDiaEle);
					newDiaList.add(newDiaEle);
					// }
				}
				if (as.getMajorClassId().equals(as.getClassA().getId())
						&& buMap.get(as.getClassA()) != null) {
					as.setMajorClassId(buMap.get(as.getClassA()).getId());
				} else if (as.getMajorClassId().equals(as.getClassB().getId())
						&& buMap.get(as.getClassB()) != null) {
					as.setMajorClassId(buMap.get(as.getClassB()).getId());
				}
				if (buMap.get(as.getClassA()) != null)
					as.setClassA((BusinessClass) buMap.get(as.getClassA()));
				if (buMap.get(as.getClassB()) != null)
					as.setClassB((BusinessClass) buMap.get(as.getClassB()));
				as.setBelongPackage(pck);
				String name = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextAssName(as.getName());
				if (!name.equals(as.getName())) {
					as.setDisplayName(name);
				}
				as.setName(name);
				newConList.add(as);
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, as);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
			}
			else if (editPart instanceof LinkAnnoEditPart) {
				LinkAnnotation as = ((LinkAnnoEditPart) editPart).getLinkAnnotation()
						.clone();

				DiagramElement newDiaEle = new DiagramElement();
				ElementStyle newStyle = new ElementStyle();
				if (oldDiagram != null && oldDiagram.getElementById(((LinkAnnoEditPart) editPart)
						.getLinkAnnotation().getId()) != null) {
					ElementStyle style = oldDiagram.getElementById(
							((LinkAnnoEditPart) editPart).getLinkAnnotation()
									.getId()).getStyle();
					newStyle.setHeight(style.getHeight());
					newStyle.setWidth(style.getWidth());
					newStyle.setPositionX(style.getPositionX());
					newStyle.setPositionY(style.getPositionY());
					newStyle.setEndPositionX(style.getEndPositionX());
					newStyle.setEndPositionY(style.getEndPositionY());
					String l = "";
					if (!style.getPointList().equals("")) {
						String[] s = style.getPointList().split(",");
						for (int i = 0; i < s.length; i++) {
							String[] po = s[i].split(" ");
							int x = Integer.parseInt(po[0].replace("(", ""));
							int y = Integer.parseInt(po[1].replace(")", ""));
							l = (l.equals("") ? "" : l + ",") + "(" + (x + px)
									+ " " + (y + py) + ")";
						}
					}
					newStyle.setPointList(l);
					newDiaEle.setStyle(newStyle);
					newDiaEle.setObjectId(as.getId());
					newDiaEle.setBelongDiagram(diagram);
					// if(diagram.getElementById(buMap.get(as.getClassA()).getId())
					// != null &&
					// diagram.getElementById(buMap.get(as.getClassB()).getId())
					// != null ){
					diagram.addElement(newDiaEle);
					newDiaList.add(newDiaEle);
					// }
				}
				if (buMap.get(as.getClassA()) != null)
					as.setClassA(buMap.get(as.getClassA()));
				if (buMap.get(as.getClassB()) != null)
					as.setClassB(buMap.get(as.getClassB()));
				as.setBelongPackage(pck);
				newConList.add(as);
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, as);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
			}
		}
		BusinessModelUtil.getBusinessModelDiagramEditor().getViewer()
				.getContents().refresh();
	}

	/**
	 * @return
	 */
	@Override
	public boolean canUndo() {
		return !(editPartList.isEmpty());
	}

	/**
	 * 
	 */
	@Override
	public void undo() {

		Diagram diagram = ((Diagram) BusinessModelUtil
				.getBusinessModelDiagramEditor().getViewer().getContents()
				.getModel());
		diagram.getElements().removeAll(newDiaList);
		for (AbstractModelElement ab : newBuList) {
			BusinessModelEvent bcAddevent = new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_DELETE, ab);
			BusinessModelUtil.getEditorBusinessModelManager()
					.businessObjectModelChanged(bcAddevent);
			((DiagramEditPart) BusinessModelUtil
					.getBusinessModelDiagramEditor().getViewer().getContents())
					.firePropertyChange(NodeModelElement.CHILD_REMOVED_PROP,
							ab, null);
		}
		for (AbstractModelElement ab : newConList) {
			BusinessModelEvent bcAddevent = new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_DELETE, ab);
			BusinessModelUtil.getEditorBusinessModelManager()
					.businessObjectModelChanged(bcAddevent);
			((DiagramEditPart) BusinessModelUtil
					.getBusinessModelDiagramEditor().getViewer().getContents())
					.firePropertyChange(NodeModelElement.CHILD_REMOVED_PROP,
							ab, null);
		}
		BusinessModelUtil.getBusinessModelDiagramEditor().getViewer()
				.getContents().refresh();
	}

	/**
	 * Copy diagram element.
	 *
	 * @author mqfdy
	 * @param editPart
	 *            the edit part
	 * @param diagram
	 *            the diagram
	 * @param objectId
	 *            the object id
	 * @Date 2018-09-03 09:00
	 */
	private void copyDiagramElement(AbstractGraphicalEditPart editPart,
			Diagram diagram, String objectId) {
		ElementStyle style = ((DiagramElement) editPart.getModel()).getStyle();
		DiagramElement newDiaEle = new DiagramElement();
		ElementStyle newStyle = new ElementStyle();
		newStyle.setHeight(style.getHeight());
		newStyle.setWidth(style.getWidth());
		// 鼠标在编辑器中的位置
		int newX = BusinessModelDiagramEditor.getMouseLocation().x;
		int newY = BusinessModelDiagramEditor.getMouseLocation().y;
		// 计算偏移
		if (px == 0 && py == 0) {
			px = (int) (newX - style.getPositionX());
			py = (int) (newY - style.getPositionY());
		}
		newStyle.setPositionX(style.getPositionX() + px);
		newStyle.setPositionY(style.getPositionY() + py);

		String l = "";
		if (!style.getPointList().equals("")) {
			String[] s = style.getPointList().split(",");
			for (int i = 0; i < s.length; i++) {
				String[] po = s[i].split(" ");
				int x = Integer.parseInt(po[0].replace("(", ""));
				int y = Integer.parseInt(po[1].replace(")", ""));
				l = (l.equals("") ? "" : l + ",") + "(" + (x + px) + " "
						+ (y + py) + ")";
			}
		}
		newStyle.setPointList(l);
		newDiaEle.setStyle(newStyle);
		newDiaEle.setObjectId(objectId);
		newDiaEle.setBelongDiagram(diagram);
		diagram.addElement(newDiaEle);
		newDiaList.add(newDiaEle);
	}
}