package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.IEditorPart;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.figure.anchor.BorderAnchor;
import com.mqfdy.code.designer.editor.figure.anchor.RectangleBorderAnchor;
import com.mqfdy.code.designer.editor.part.InheritanceEditPart;
import com.mqfdy.code.designer.editor.part.LinkAnnoEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.editor.part.RelationEditPart;
import com.mqfdy.code.designer.editor.part.extensions.BusinessAbstractConnectionEditPart;
import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 删除连线
 * 
 * A command to disconnect a connection from its endpoints and remove it from
 * the model. The command can be undone or redone.
 * 
 * @author mqfdy
 * 
 */
public class ConnectionDeleteCommand extends Command {

	/** Connection instance to disconnect. */
	private AbstractModelElement connection;

	// private BusinessClass source;
	//
	// private BusinessClass target;

	private EditPart conEditPart;
	private EditPart targetEditPart;
	private EditPart sourceEditPart;
	private String type;
	// private List<RelationFigure> figureList = new
	// ArrayList<RelationFigure>();

	private DiagramElement reDia;
	
	private boolean isDeleted = true;

	/**
	 * Create a command that will disconnect a connection from its endpoints.
	 * 
	 * @param conn
	 *            the connection instance to disconnect (non-null)
	 * @param editPart
	 * @throws IllegalArgumentException
	 *             if conn is null
	 */
	public ConnectionDeleteCommand(AbstractModelElement conn, EditPart editPart) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}

		// source = conn.getClassA();
		// target = conn.getClassB();
		this.conEditPart = editPart;
		this.sourceEditPart = ((BusinessAbstractConnectionEditPart) conEditPart)
				.getSource();
		this.targetEditPart = ((BusinessAbstractConnectionEditPart) conEditPart)
				.getTarget();
		// sourceBusinessClass =
		// ((BusinessClassElement)source).getBusinessClass();
		// targetBusinessClass =
		// ((BusinessClassElement)target).getBusinessClass();

		type = IConstants.ASSOCIATION_STR;
		setLabel("delete connection");
		this.connection = conn;
	}

	public ConnectionDeleteCommand(AbstractModelElement conn,
			EditPart sourceEditPart, EditPart targetEditPart) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		// conn = (Association)conn;
		// source = ((Association)conn).getClassA();
		// target = ((Association)conn).getClassB();
		this.conEditPart = sourceEditPart;
		this.targetEditPart = targetEditPart;
		// sourceBusinessClass =
		// ((BusinessClassElement)source).getBusinessClass();
		// targetBusinessClass =
		// ((BusinessClassElement)target).getBusinessClass();

		type = IConstants.ASSOCIATION_STR;
		setLabel("delete connection");
		this.connection = conn;
	}

	@Override
	public void execute() {
		redo();
	}

	
	
	@Override
	public boolean canExecute() {
		if(conEditPart.getModel() instanceof Association
				&& IModelElement.STEREOTYPE_REVERSE.equals(((Association)conEditPart.getModel()).getStereotype())){
			isDeleted = false;
			return isDeleted;
		}
		return super.canExecute();
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return isDeleted;
	}

	@Override
	public void redo() {
		// BusinessObjectModel businessObjectModel =
		// BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
		// List<Association> all = businessObjectModel.getAssociations();
		// all.remove(connection);
		if (conEditPart instanceof OmConnectionEditPart) {
			if(conEditPart.getModel() instanceof Association
					&& IModelElement.STEREOTYPE_REVERSE.equals(((Association)conEditPart.getModel()).getStereotype())){
				isDeleted = false;
				return;
			}
			if (reDia == null) {
				Object ele = ((((OmConnectionEditPart) conEditPart).getTarget()))
						.getModel();
				if (ele instanceof DiagramElement) {
					reDia = ((DiagramElement) ele).getBelongDiagram()
							.getElementById(connection.getId());
					((DiagramElement) ele).getBelongDiagram().getElements()
							.remove(reDia);
				}
			} else {
				((DiagramElement) reDia).getBelongDiagram().getElements()
						.remove(reDia);
			}

		}
		
		BusinessModelEvent event = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_DELETE, connection);
		BusinessModelUtil.getEditorBusinessModelManager()
				.businessObjectModelChanged(event);
		if(targetEditPart != null)
			targetEditPart.refresh();
		if(sourceEditPart != null)
			sourceEditPart.refresh();

	}

	@Override
	public void undo() {
		// BusinessObjectModel businessObjectModel =
		// BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
		// List<Association> all = businessObjectModel.getAssociations();
		// all.add(connection);
		if(isDeleted){
			if (conEditPart instanceof RelationEditPart || conEditPart instanceof LinkAnnoEditPart) {
				((DiagramElement) reDia).getBelongDiagram().getElements()
						.add(reDia);
				BorderAnchor anchor = new RectangleBorderAnchor(
						((NodeEditPart) sourceEditPart).getFigure());
				anchor.setAngle(Math.atan2(reDia.getStyle().getPositionY(), reDia
						.getStyle().getPositionX()));
				((OmConnectionEditPart) conEditPart).setSourceAnchor(anchor);
				BorderAnchor anchor1 = new RectangleBorderAnchor(
						((AbstractGraphicalEditPart) targetEditPart).getFigure());
				anchor1.setAngle(Math.atan2(reDia.getStyle().getEndPositionY(),
						reDia.getStyle().getEndPositionX()));
				((OmConnectionEditPart) conEditPart).setTargetAnchor(anchor1);
				// conEditPart.refresh();
				// sourceEditPart.refresh();
				// targetEditPart.refresh();
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, connection);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event);
			}
			if (conEditPart instanceof InheritanceEditPart) {
				// ((ConnectionEditPart)sourceEditPart).getSource().refresh();
				// ((ConnectionEditPart)sourceEditPart).getTarget().refresh();
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, connection);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event);
				IEditorPart BmEditor = BusinessModelEditorPlugin
						.getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
				BusinessModelDiagramEditor editor = ((BusinessModelEditor) BmEditor)
						.getBuEditor();
				Iterator<?> it = editor.getAllEditParts();
				List<NodeEditPart> partList = new ArrayList<NodeEditPart>();
				while (it.hasNext()) {
					Object e = ((Entry<?, ?>) it.next()).getValue();
					if (e instanceof NodeEditPart) {
						if (((NodeEditPart) e).getModel() instanceof DiagramElement) {
							if (((Inheritance) ((InheritanceEditPart) conEditPart)
									.getModel())
									.getChildClass()
									.getId()
									.equals(((DiagramElement) (((NodeEditPart) e)
											.getModel())).getObjectId()))
								partList.add((NodeEditPart) e);
							if (((Inheritance) ((InheritanceEditPart) conEditPart)
									.getModel())
									.getParentClass()
									.getId()
									.equals(((DiagramElement) (((NodeEditPart) e)
											.getModel())).getObjectId()))
								partList.add((NodeEditPart) e);
						}
					}
				}
				for (NodeEditPart e : partList) {
					e.refresh();
				}
			}
			conEditPart.refresh();
//			if (targetEditPart != null)
//				targetEditPart.refresh();
//			if(sourceEditPart != null)
//				sourceEditPart.refresh();
		}
	}
}
