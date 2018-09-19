package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.mqfdy.code.designer.editor.actions.pages.SelectDeleteWayDialog;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Annotation;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * 删除
 * 
 * Recursively removes a node from the diagram (not from the UML2 model!). It's
 * the opposite of {@link NodeAddCommand}.
 * 
 * @author mqfdy
 * 
 */
public class NodesDeleteCommand extends Command {

	/** The edit part list. */
	private List<AbstractGraphicalEditPart> editPartList = new ArrayList<AbstractGraphicalEditPart>();

	/** The was removed. */
	private boolean wasRemoved = false;

	/** The was opened. */
	private boolean wasOpened = false;
	
	/** The s. */
	private int s = 0;
	
	/** The del way dlg. */
	private SelectDeleteWayDialog delWayDlg = null;

	/** The parent. */
	private AbstractModelElement parent;

	/** The edit part. */
	private EditPart editPart;// 图的EditPart

	/** The id string. */
	private List<String> idString = new ArrayList<String>();
	
	/** The conn list. */
	private List<AbstractModelElement> connList = new ArrayList<AbstractModelElement>();
	
	/** The del model list. */
	private List<AbstractModelElement> delModelList = new ArrayList<AbstractModelElement>();
	
	/** The con ele. */
	private List<DiagramElement> conEle = new ArrayList<DiagramElement>();
	// private AbstractModelElement node;

	/** The is del object. */
	private boolean isDelObject;

	/**
	 * Instantiates a new nodes delete command.
	 *
	 * @param list
	 *            the list
	 * @param parent
	 *            the parent
	 * @param editPart
	 *            the edit part
	 */
	public NodesDeleteCommand(List<AbstractGraphicalEditPart> list,
			AbstractModelElement parent, EditPart editPart) {
		if (list == null || parent == null) {
			throw new IllegalArgumentException();
		}
		setLabel("remove node");
		this.editPartList = list;
		this.parent = parent;
		this.editPart = editPart;

	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		return (editPartList != null && !wasRemoved);
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		connList.clear();
		delModelList.clear();
		BusinessModelManager manager = BusinessModelUtil.getEditorBusinessModelManager();
		
		for (AbstractGraphicalEditPart part : editPartList) {
			if(part.getModel() instanceof DiagramElement){
				AbstractModelElement node = manager.queryObjectById(
						((DiagramElement) part.getModel()).getObjectId());
				if (node == null) {
					node = (AbstractModelElement) BusinessModelManager
							.getBuildInOm().getModelElementById(
									((DiagramElement) part.getModel())
											.getObjectId());
				}
				if (node == null) {
					return;
				}
				boolean flag = false;
				for (DiagramElement diaEle : ((Diagram) parent).getElements()) {
					if (diaEle.getObjectId().equals(node.getId())) {
						flag = true;
					}
				}
				if (!flag)
					return;
				delModelList.add(node);
				if (!wasOpened && !(node instanceof Annotation)
						&& !IModelElement.STEREOTYPE_BUILDIN.equals(node
								.getStereotype())) {
					delWayDlg = new SelectDeleteWayDialog(this.editPart.getViewer()
							.getControl().getShell());
					s = delWayDlg.open();
					wasOpened = true;
				}
				if (!wasOpened
						&& IModelElement.STEREOTYPE_BUILDIN.equals(node
								.getStereotype())) {
					s = 0;
					wasOpened = true;
					isDelObject = true;
				}
				if (!wasOpened
						&& node instanceof Annotation) {
					s = 0;
					wasOpened = true;
					isDelObject = true;
				}
				if (s != 0) {
					return;
				}
				List<DiagramElement> list = ((Diagram) parent).getElements();
				if (wasRemoved)
					list.remove(part.getModel());
				if (wasRemoved == false)
					wasRemoved = list.remove(part.getModel());
				if (s == 0) {
					if (delWayDlg != null)
						isDelObject = delWayDlg.isDel();
						if ((node instanceof BusinessClass || node instanceof ReferenceObject)) {
							BusinessClass tempBc = null;
							if (node instanceof ReferenceObject) {
								tempBc = (BusinessClass) ((ReferenceObject) node)
										.getReferenceObject();
							} else {
								tempBc = (BusinessClass) node;
							}
							List<Association> assoications = manager.getAssociationsByBusinessClass(tempBc);
							if (assoications != null) {
								for (Association association : assoications) {
									connList.add(association);
									DiagramElement ele = ((Diagram) parent).getElementById(association.getId());
									if(ele != null){
										conEle.add(ele);
									}
									if (isDelObject ){
										manager.getBusinessObjectModel().removeModelElement(association);
										manager.businessModelElementDelete(association);
									}
								}
							}
							List<LinkAnnotation> links = manager.getLinksByModel(tempBc);
							if (links != null) {
								for (LinkAnnotation link : links) {
									if(!connList.contains(link))
										connList.add(link);
									DiagramElement ele = ((Diagram) parent).getElementById(link.getId());
									if(ele != null){
										conEle.add(ele);
									}
									if (isDelObject ){
										manager.getBusinessObjectModel().removeModelElement(link);
										manager.businessModelElementDelete(link);
									}
								}
							}
		
							List<Inheritance> inheritances = manager.getInheritancesByBusinessClass(tempBc);
							if (assoications != null) {
								for (Inheritance inheritance : inheritances) {
									connList.add(inheritance);
									DiagramElement ele = ((Diagram) parent).getElementById(inheritance.getId());
									if(ele != null){
										conEle.add(ele);
									}
									if (isDelObject ){
										manager.getBusinessObjectModel().removeModelElement(inheritance);
										manager.businessModelElementDelete(inheritance);
									}
								}
							}
						}
						else if(node instanceof Annotation ){
							List<LinkAnnotation> links = manager.getLinksByModel(node);
							if (links != null) {
								for (LinkAnnotation link : links) {
									if(!connList.contains(link))
										connList.add(link);
									DiagramElement ele = ((Diagram) parent).getElementById(link.getId());
									if(ele != null){
										conEle.add(ele);
									}
									manager.getBusinessObjectModel().removeModelElement(link);
									manager.businessModelElementDelete(link);
								}
							}
						}
						if (isDelObject ){
							BusinessModelEvent event = new BusinessModelEvent(
									BusinessModelEvent.MODEL_ELEMENT_DELETE, node);
							manager.businessObjectModelChanged(event);
						}else {
							if(node instanceof Annotation){
								BusinessModelEvent event = new BusinessModelEvent(
										BusinessModelEvent.MODEL_ELEMENT_DELETE, node);
								manager.businessObjectModelChanged(event);
							}
						}
					}
			}
			
		}
		((Diagram) parent).getElements().removeAll(conEle);
		editPart.refresh();
		BusinessModelUtil.getBusinessModelDiagramEditor().getViewer()
				.getContents().refresh();
		EditorOperation.refreshNodeEditParts();
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		BusinessModelManager manager = BusinessModelUtil.getEditorBusinessModelManager();
		if(delModelList.size() < 1)
			return;
		for (AbstractModelElement node : delModelList) {
			if (isDelObject
					&& !IModelElement.STEREOTYPE_BUILDIN.equals(
							node.getStereotype())) {
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, node);
				manager.businessObjectModelChanged(event);
			}else if(node instanceof Annotation){
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, node);
				manager.businessObjectModelChanged(event);
			}
		}
		if (connList.size() > 0) {
			for (AbstractModelElement con : connList) {
				if (isDelObject ){
					BusinessModelEvent event1 = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_ADD, con);
					manager.businessObjectModelChanged(event1);
				}else if(con instanceof LinkAnnotation){
					BusinessModelEvent event1 = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_ADD, con);
					manager.businessObjectModelChanged(event1);
				}
			}
		}
		for(DiagramElement diaele : conEle){
			((Diagram) parent).addElement(diaele);
		}
		for (AbstractGraphicalEditPart part : editPartList) {
			if(part.getModel() instanceof DiagramElement){
				for (DiagramElement diaEle : ((Diagram) parent).getElements()) {
					if (diaEle!= null && diaEle.getObjectId().equals(
							((DiagramElement) part.getModel()).getObjectId())) {
						return;
					}
				}
				AbstractModelElement node = manager.queryObjectById(
								((DiagramElement) part.getModel()).getObjectId());
				if (node == null) {
					node = (AbstractModelElement) BusinessModelManager
							.getBuildInOm().getModelElementById(
									((DiagramElement) part.getModel())
											.getObjectId());
				}
				if (node == null) {
					return;
				}
				((Diagram) parent).addElement((DiagramElement) part.getModel());
				((DiagramEditPart) editPart).firePropertyChange(
						NodeModelElement.CHILD_ADDED_PROP, null, node);
			}
			
		}
		editPart.refresh();
		EditorOperation.refreshNodeEditParts();
	}

	/**
	 * @return
	 */
	@Override
	public boolean canUndo() {
		return s == 0;
	}
}