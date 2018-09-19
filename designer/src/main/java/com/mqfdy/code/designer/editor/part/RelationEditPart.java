package com.mqfdy.code.designer.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

import com.mqfdy.code.designer.dialogs.BusinessClassRelationEditDialog;
import com.mqfdy.code.designer.editor.figure.RelationFigure;
import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.views.properties.MultiPageEditorPropertySheetPage;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.utils.AssociationType;

// TODO: Auto-generated Javadoc
/**
 * The Class RelationEditPart.
 *
 * @author mqfdy
 * @title:关系控制器
 * @description:控制业务实体间关联关系的模型与界面之间的交互
 */
public class RelationEditPart extends OmConnectionEditPart {
	
	/** The as. */
	Association as = null;
	
	/** The model element. */
	private DiagramElement modelElement;

	/** The relation type. */
	// relation type can be association, aggregation or composition
	private String relationType;
	
	/**
	 * Instantiates a new relation edit part.
	 *
	 * @param as
	 *            the as
	 * @param modelElement
	 *            the model element
	 */
	public RelationEditPart(Association as, DiagramElement modelElement) {
		super();
		this.as = as;
		this.setModelElement(modelElement);
	}

	/**
	 * Perform request.
	 *
	 * @author mqfdy
	 * @param req
	 *            the req
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void performRequest(Request req) {
		Shell shell = getViewer().getControl().getShell();
		// 如果是打开编辑框的请求
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
					shell, (Association) getModel(),
					((Association) getModel()).getBelongPackage(),
					BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
			dialog.open();
			repaintFigure();
			IViewPart[] views = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getViews();
			for (int i = 0; i < views.length; i++) {
				if (views[i] instanceof PropertySheet) {
					PropertySheet view = (PropertySheet) views[i];
					if ((((PropertySheet) views[i]).getCurrentPage()) instanceof MultiPageEditorPropertySheetPage) {
						MultiPageEditorPropertySheetPage page = ((MultiPageEditorPropertySheetPage) (((PropertySheet) views[i])
								.getCurrentPage()));
						page.selectionChanged(view, (ISelection) this
								.getParent().getViewer().getSelection());
					}
				}
			}
		}
		super.performRequest(req);
	}

	/**
	 * Create a new RelationFigure to be used as the EditParts graphical
	 * representation. Only called if Figure has not been created.
	 *
	 * @return the i figure
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		relationType = IConstants.ASSOCIATION_STR;
		figure = new RelationFigure(relationType, as, this);
		((PolylineConnection) figure)
				.setTargetDecoration(new PolygonDecoration());
		// ((PolylineConnection) figure).setConnectionRouter(new
		// BendpointConnectionRouter());

		repaintFigure();

		return figure;
	}

	/**
	 * @return
	 */
	/*
	 * Casts primary model to AbstractModelElement
	 * Association
	 */
	@Override
	protected AbstractModelElement getCastedModel() {
		return (AbstractModelElement) getModel();
	}

	/**
	 * 
	 */
	@Override
	public void repaintFigure() {

		// RelationElement relationElement = getCastedModel();
		Association association = as;
		RelationFigure r = (RelationFigure) figure;

		// empty RelationFigure
		r.emptyFigure();

		String relCaption = association.getDisplayName();
		r.setCaption(relCaption);
		r.setStartRoleName(as.getNavigateToClassBRoleName());
		r.setEndRoleName(as.getNavigateToClassARoleName());
		String startMult = "1";
		String endMult = "1";
		if (association.getAssociationType().equals(
				AssociationType.one2one.getValue())) {
			startMult = "1";
			endMult = "1";
		} else if (association.getAssociationType().equals(
				AssociationType.one2mult.getValue())) {
			startMult = "1";
			endMult = "*";
		} else if (association.getAssociationType().equals(
				AssociationType.mult2mult.getValue())) {
			startMult = "*";
			endMult = "*";
		} else if (association.getAssociationType().equals(
				AssociationType.mult2one.getValue())) {
			startMult = "*";
			endMult = "1";
		}
		r.setStartMultiplicity(endMult);
		r.setEndMultiplicity(startMult);

		// 获取关系类型，改变之前固化的做法
		// r.setType(relationElement.getPropertyValue(RelationElement.TYPE_PROP).toString());
		r.setType(IConstants.ASSOCIATION_STR);
		r.setDirected(as.isNavigateToClassA(), as.isNavigateToClassB());
	}

	/**
	 * Gets the association.
	 *
	 * @author mqfdy
	 * @return the association
	 * @Date 2018-09-03 09:00
	 */
	public Association getAssociation() {
		return as;
	}

	/**
	 * Gets the model element.
	 *
	 * @author mqfdy
	 * @return the model element
	 * @Date 2018-09-03 09:00
	 */
	public DiagramElement getModelElement() {
		return modelElement;
	}

	/**
	 * Sets the model element.
	 *
	 * @author mqfdy
	 * @param modelElement
	 *            the new model element
	 * @Date 2018-09-03 09:00
	 */
	public void setModelElement(DiagramElement modelElement) {
		this.modelElement = modelElement;
	}

}
