package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;
// TODO: Auto-generated Javadoc
/**
 * 从outline视图中拖动对象到图中创建对象
 * 
 * Adds a new node to the diagram.
 * 
 * @author mqfdy
 * 
 */
public class NodesCreateFromOutlineCommand extends Command {

	/** The can del object. */
	// private AbstractModelElement newNode;
	private boolean canDelObject = false;
	
	/** The container. */
	// can be model modelRoot or package
	private AbstractModelElement container;

	/** The bounds. */
	private Rectangle bounds;

	/** The edit part. */
	private EditPart editPart;
	
	/** The old node list. */
	private List<AbstractModelElement> oldNodeList = new ArrayList<AbstractModelElement>();
	
	/** The new node list. */
	private List<AbstractModelElement> newNodeList = new ArrayList<AbstractModelElement>();
	
	/** The ele list. */
	private List<DiagramElement> eleList = new ArrayList<DiagramElement>();
	
	/** The ele. */
	private DiagramElement ele;
	
	/** The id string. */
	private List<String> idString = new ArrayList<String>();

	/**
	 * Instantiates a new nodes create from outline command.
	 *
	 * @param nodesList
	 *            the nodes list
	 * @param container
	 *            the container
	 * @param bounds
	 *            the bounds
	 * @param editPart
	 *            the edit part
	 */
	public NodesCreateFromOutlineCommand(List<AbstractModelElement> nodesList,
			AbstractModelElement container, Rectangle bounds, EditPart editPart) {
		if (nodesList == null || container == null || bounds == null) {
			throw new IllegalArgumentException();
		}
		this.editPart = editPart;
		this.oldNodeList = nodesList;
		this.container = container;
		this.bounds = bounds;

		setLabel("add new node");
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		return container instanceof Diagram;
		// if (newNode == null || container == null) {
		// return false;
		// }
		// return container instanceof Diagram && (newNode instanceof
		// ComplexDataType||newNode instanceof BusinessClass||newNode instanceof
		// ReferenceObject||newNode instanceof Enumeration||newNode instanceof
		// DataTransferObject);
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		redo();
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		int x = bounds.x;
		int y = bounds.y;
		for (AbstractModelElement newNode : oldNodeList) {
			if (newNode instanceof ReferenceObject) {
				BusinessObjectModel bom = BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
				// ReferenceObject ro = new ReferenceObject(newNode);
				boolean flag = true;
				((ReferenceObject) newNode).setBelongPackage(((Diagram) container).getBelongPackage());
				for (ReferenceObject referenceObject : bom.getReferenceObjects()) {
					if (referenceObject.getReferenceObjectId().equals(((ReferenceObject) newNode).getReferenceObjectId())) {
						flag = false;
					}
				}
				for (BusinessClass bu : bom.getBusinessClasses()) {
					if (bu.getId().equals(((ReferenceObject) newNode).getReferenceObjectId())) {
						flag = false;
					}
				}
				if (flag) {
					canDelObject = true;
					newNodeList.add(newNode);
					BusinessModelEvent event1 = new BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_ADD, newNode);
					BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event1);
				}
				// newNode = ro;
			} else if (newNode instanceof BusinessClass && IModelElement.STEREOTYPE_REFERENCE.equals(newNode.getStereotype())) {
				BusinessObjectModel bom = BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
				boolean flag = true;
				ReferenceObject ro = null;
				for (ReferenceObject referenceObject : bom.getReferenceObjects()) {
					if (referenceObject.getReferenceObjectId().equals(newNode.getId())) {
						flag = false;
						ro = referenceObject;
						// return;
					}
				}
				if (flag) {
					canDelObject = true;
					ro = new ReferenceObject(newNode);
					ro.setBelongPackage(((Diagram) container).getBelongPackage());
					newNodeList.add(ro);
					BusinessModelEvent event1 = new BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_ADD, ro);
					BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event1);
				}
				newNode = ro;
			}
			boolean isAddElement = true;
			for (DiagramElement diaEle : ((Diagram) container).getElements()) {
				if (newNode != null) {
				if (diaEle != null && diaEle.getObjectId().equals(newNode.getId())) {
					isAddElement = false;
				}
			}
			}
			if (isAddElement) {
				ele = new DiagramElement();
				if (ele != null) {
					
					ele.setObjectId(newNode.getId());
				}
				ElementStyle st = new ElementStyle();
				st.setHeight(bounds.height);
				st.setWidth(bounds.width);
				st.setPositionX(x);
				st.setPositionY(y);
				if (bounds.height == -1)
					st.setHeight(200);
				if (bounds.width == -1)
					st.setWidth(160);
				int yLength = 0;
				int xLength = 0;
				if (newNode instanceof BusinessClass) {
					yLength = ((BusinessClass) newNode).getOperations().size();
					yLength += ((BusinessClass) newNode).getProperties().size();
					for (Property pro : ((BusinessClass) newNode).getProperties()) {
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
				if (newNode instanceof ReferenceObject) {
					yLength = ((BusinessClass) ((ReferenceObject) newNode).getReferenceObject()).getOperations().size();
					yLength += ((BusinessClass) ((ReferenceObject) newNode).getReferenceObject()).getProperties().size();
					for (Property pro : ((BusinessClass) ((ReferenceObject) newNode).getReferenceObject()).getProperties()) {
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
				ele.setBelongDiagram((Diagram) container);
				((Diagram) container).addElement(ele);
				eleList.add(ele);
				// if(editPart instanceof NodeEditPart)
				// ((NodeEditPart)
				// editPart).firePropertyChange(NodeModelElement.
				// CHILD_ADDED_PROP, null, newNode);
				// if(editPart instanceof DiagramEditPart)
				// ((DiagramEditPart)
				// editPart).firePropertyChange(NodeModelElement.
				// CHILD_ADDED_PROP, null, newNode);

			}
		}
		EditorOperation.refreshNodeEditParts();
		BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getContents().refresh();

		
		//generateReferenceCode(newNodeList);
	}
	
	
	    
	/**
	 * 
	 */
	@Override
	public void undo() {
		for (AbstractModelElement newNode : newNodeList) {
			if (canDelObject
					&& newNode instanceof BusinessClass
					&& newNode.getStereotype().equals(
							IModelElement.STEREOTYPE_REFERENCE)) {
				((BusinessClass) newNode)
						.setBelongPackage(((Diagram) container)
								.getBelongPackage());
				BusinessModelEvent event1 = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_DELETE, newNode);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event1);
			} else if (canDelObject && newNode instanceof ReferenceObject) {
				((ReferenceObject) newNode)
						.setBelongPackage(((Diagram) container)
								.getBelongPackage());
				BusinessModelEvent event1 = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_DELETE, newNode);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event1);
			}
			((Diagram) container).getElements().remove(ele);
			if (editPart instanceof NodeEditPart)
				((NodeEditPart) editPart).firePropertyChange(
						NodeModelElement.CHILD_REMOVED_PROP, null, newNode);
			if (editPart instanceof DiagramEditPart)
				((DiagramEditPart) editPart).firePropertyChange(
						NodeModelElement.CHILD_REMOVED_PROP, null, newNode);
		}
		((Diagram) container).getElements().removeAll(eleList);
		// if(newNode instanceof BusinessClass){
		// List<Association> asList =
		// BusinessModelUtil.getEditorBusinessModelManager().getAssociationsByBusinessClass((BusinessClass)
		// newNode);
		// for(Association as:asList){
		// if(as.getClassA().equals(newNode)){
		// idString.add(as.getClassBid());
		// }else if(as.getClassB().equals(newNode)){
		// idString.add(as.getClassAid());
		// }
		// }
		EditorOperation.refreshNodeEditParts();
		BusinessModelUtil.getBusinessModelDiagramEditor().getViewer()
				.getContents().refresh();
		// }
		// BusinessObjectModel bu =
		// BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
		// bu.removeBusinessClass((BusinessClass) newNode);
		// IEditorPart editorPart =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		// if(editorPart instanceof MultiPageEditor){
		// ((MultiPageEditor)editorPart).getBuEditor().getViewer().setContents(container);
		// }
	}
}
