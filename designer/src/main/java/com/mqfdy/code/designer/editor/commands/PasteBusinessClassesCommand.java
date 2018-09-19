package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;
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

public class PasteBusinessClassesCommand extends Command {
	
	/** The business classes list. */
	// 内存中复制的业务实体
	List<AbstractModelElement> businessClassesList = new ArrayList<AbstractModelElement>();
	
	/** The new bus list. */
	// 复制出的对象
	List<AbstractModelElement> newBusList = new ArrayList<AbstractModelElement>();
	
	/** The bu map. */
	// 新旧模型对象的Map
	Map<AbstractModelElement, AbstractModelElement> buMap = new HashMap<AbstractModelElement, AbstractModelElement>();

	/**
	 * Instantiates a new paste business classes command.
	 */
	public PasteBusinessClassesCommand() {
		super();
		businessClassesList.clear();
		if (Clipboard.getDefault().getContents() != null)
			businessClassesList.addAll((List<AbstractModelElement>) Clipboard
					.getDefault().getContents());
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		if (businessClassesList.isEmpty())
			return false;
		if (BusinessModelUtil.getBusinessModelDiagramEditor() == null)
			return false;
		if (BusinessModelUtil.getGefViewer().getContents() == null)
			return false;
		List<AbstractGraphicalEditPart> list = BusinessModelUtil
				.getSelectedEditParts();
		if (list.size() > 0)
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
		Diagram diagram = ((Diagram) BusinessModelUtil.getGefViewer()
				.getContents().getModel());
		ModelPackage pck = diagram.getBelongPackage();
		int x = BusinessModelDiagramEditor.getMouseLocation().x;
		int y = BusinessModelDiagramEditor.getMouseLocation().y;
		// List<AbstractGraphicalEditPart> list =
		// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
		// BusinessClass oldBu = ((BusinessClassEditPart)
		// list.get(0)).getBusinessClass();;
		for (AbstractModelElement bu : businessClassesList) {
			if (bu instanceof BusinessClass) {
				BusinessClass newBu = ((BusinessClass) bu).cloneChangeId();
				newBu.setBelongPackage(pck);
				newBusList.add(newBu);
				String name = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextBusinessClassName(newBu.getName());
				newBu.setTableName(BusinessModelUtil
						.getEditorBusinessModelManager()
						.generateNextBusinessClassTableName(
								newBu.getTableName()));
				String disName = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextBusinessClassDisName(newBu.getDisplayName());
				
//				if (!name.equals(newBu.getName())) {
				newBu.setDisplayName(disName);
//				}
				DiagramElement ele = new DiagramElement();
				ele.setObjectId(newBu.getId());
				ElementStyle st = new ElementStyle();
				st.setHeight(200);
				st.setWidth(160);
				st.setPositionX(x);
				st.setPositionY(y);
				int yLength = 0;
				int xLength = 0;
				if (newBu instanceof BusinessClass) {
					yLength = ((BusinessClass) newBu).getOperations().size();
					yLength += ((BusinessClass) newBu).getProperties().size();
					for (Property pro : ((BusinessClass) newBu).getProperties()) {
						String proName = pro.getName();
						if (pro instanceof PKProperty)
							proName = proName + "<<PK>>";
						if (xLength < proName.length()) {
							xLength = proName.length();
						}
					}
					st.setHeight(yLength * 14 + 40);
					st.setWidth((xLength + 17) * 6);
				}
				x = x + st.getWidth() + 30;
				ele.setStyle(st);
				ele.setBelongDiagram(diagram);

				newBu.setName(name);
				buMap.put((BusinessClass) bu, newBu);

				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newBu);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);

				diagram.addElement(ele);
				((DiagramEditPart) BusinessModelUtil
						.getBusinessModelDiagramEditor().getViewer()
						.getContents()).firePropertyChange(
						NodeModelElement.CHILD_ADDED_PROP, null, newBu);
			} else if (bu instanceof ReferenceObject) {
				// 判断目标对象是否包含此业务实体的引用 包含则只复制图形 不包含则新建一个引用对象
				BusinessObjectModel businessObjectModel = BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager().getBusinessObjectModel();
				boolean flag = false;
				ReferenceObject busref = null;
				for (ReferenceObject ref : businessObjectModel
						.getReferenceObjects()) {
					if (ref.getReferenceObjectId().equals(
							((ReferenceObject) bu).getReferenceObjectId())) {
						flag = true;
						busref = ref;
					}
				}
				boolean flag1 = false;
				BusinessClass bus = null;
				for (BusinessClass bu1 : BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager().getBusinessObjectModel()
						.getBusinessClasses()) {
					if (bu1.getId()
							.equals(((ReferenceObject) bu).getReferenceObject()
									.getId())
							&& bu1.getName().equals(
									((ReferenceObject) bu).getReferenceObject()
											.getName())) {
						flag1 = true;
						bus = bu1;
					}
				}
				if (bus != null)
					buMap.put((BusinessClass) ((ReferenceObject) bu)
							.getReferenceObject(), bus);
				else if (busref != null)
					buMap.put((BusinessClass) ((ReferenceObject) bu)
							.getReferenceObject(), (BusinessClass) busref
							.getReferenceObject());
				else
					buMap.put((BusinessClass) ((ReferenceObject) bu)
							.getReferenceObject(),
							(BusinessClass) ((ReferenceObject) bu)
									.getReferenceObject());

				ReferenceObject ref = null;
				// 新建一个引用对象
				if (!flag && !flag1) {
					ref = new ReferenceObject(
							((ReferenceObject) bu).getReferenceObject());
					ref.setBelongPackage(pck);
					newBusList.add(ref);
					BusinessModelEvent event1 = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_ADD, ref);
					BusinessModelUtil.getEditorBusinessModelManager()
							.businessObjectModelChanged(event1);
					// buMap.put((BusinessClass) bu.getReferenceObject(),
					// (BusinessClass) bu.getReferenceObject());
				}
				if (!flag1 && diagram.getElementById(bu.getId()) == null) {
					DiagramElement ele = new DiagramElement();
					ElementStyle st = new ElementStyle();
					st.setHeight(200);
					st.setWidth(160);
					st.setPositionX(x);
					st.setPositionY(y);
					int yLength = 0;
					int xLength = 0;
					if (bu instanceof ReferenceObject) {
						yLength = ((BusinessClass) ((ReferenceObject) bu)
								.getReferenceObject()).getOperations().size();
						yLength += ((BusinessClass) ((ReferenceObject) bu)
								.getReferenceObject()).getProperties().size();
						for (Property pro : ((BusinessClass) ((ReferenceObject) bu)
								.getReferenceObject()).getProperties()) {
							String proName = pro.getName();
							if (pro instanceof PKProperty)
								proName = proName + "<<PK>>";
							if (xLength < proName.length()) {
								xLength = proName.length();
							}
						}
						st.setHeight(yLength * 14 + 50);
						st.setWidth((xLength + 17) * 6);
					}
					x = x + st.getWidth() + 30;
					ele.setStyle(st);
					ele.setBelongDiagram(diagram);

					if (ref == null) {
						ele.setObjectId(bu.getId());
						diagram.addElement(ele);
						((DiagramEditPart) BusinessModelUtil
								.getBusinessModelDiagramEditor().getViewer()
								.getContents()).firePropertyChange(
								NodeModelElement.CHILD_ADDED_PROP, null, bu);
					} else {
						ele.setObjectId(ref.getId());
						diagram.addElement(ele);
						((DiagramEditPart) BusinessModelUtil
								.getBusinessModelDiagramEditor().getViewer()
								.getContents()).firePropertyChange(
								NodeModelElement.CHILD_ADDED_PROP, null, ref);
					}
				}
			}else if (bu instanceof Enumeration) {
				Enumeration newBu = ((Enumeration) bu).cloneChangeId();
				newBu.setBelongPackage(pck);
				newBusList.add(newBu);
				String name = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextEnumerationName(newBu.getName());
				String disName = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextEnumerationDisName(newBu.getDisplayName());
//				if (!name.equals(newBu.getName())) {
				newBu.setDisplayName(disName);
//				}
				DiagramElement ele = new DiagramElement();
				ele.setObjectId(newBu.getId());
				ElementStyle st = new ElementStyle();
				st.setHeight(160);
				st.setWidth(160);
				st.setPositionX(x);
				st.setPositionY(y);
				x = x + st.getWidth() + 30;
				ele.setStyle(st);
				ele.setBelongDiagram(diagram);

				newBu.setName(name);
//				buMap.put((BusinessClass) bu, newBu);

				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newBu);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);

				diagram.addElement(ele);
				((DiagramEditPart) BusinessModelUtil
						.getBusinessModelDiagramEditor().getViewer()
						.getContents()).firePropertyChange(
						NodeModelElement.CHILD_ADDED_PROP, null, newBu);
			}
		}
		for (Object abObject : businessClassesList) {
			if (abObject instanceof Association) {
				Association as = ((Association) abObject).clone();

				if (as.getMajorClassId().equals(as.getClassA().getId())
						&& buMap.get(as.getClassA()) != null) {
					as.setMajorClassId(buMap.get(as.getClassA()).getId());
				} else if (as.getMajorClassId().equals(as.getClassB().getId())
						&& buMap.get(as.getClassB()) != null) {
					as.setMajorClassId(buMap.get(as.getClassB()).getId());
				}
				as.setClassA((BusinessClass) buMap.get(as.getClassA()));
				as.setClassB((BusinessClass) buMap.get(as.getClassB()));
				as.setBelongPackage(pck);
				String name = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextAssName(as.getName());
				if (!name.equals(as.getName())) {
					as.setDisplayName(name);
				}
				as.setName(name);
				newBusList.add(as);
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, as);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
			}
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean canUndo() {
		return canExecute();
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		for (AbstractModelElement ab : newBusList) {
			BusinessModelEvent bcAddevent = new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_DELETE, ab);
			BusinessModelUtil.getEditorBusinessModelManager()
					.businessObjectModelChanged(bcAddevent);
			((DiagramEditPart) BusinessModelUtil
					.getBusinessModelDiagramEditor().getViewer().getContents())
					.firePropertyChange(NodeModelElement.CHILD_REMOVED_PROP,
							ab, null);
		}
	}
}