package com.mqfdy.code.designer.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

import com.mqfdy.code.designer.dialogs.BusinessClassInheritanceRelationEditDialog;
import com.mqfdy.code.designer.editor.figure.InheritanceFigure;
import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.views.properties.MultiPageEditorPropertySheetPage;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * @title:关系控制器
 * @description:控制业务实体间继承关系的模型与界面之间的交互
 * @author mqfdy
 */
public class InheritanceEditPart extends OmConnectionEditPart {
	Inheritance in = null;
	private DiagramElement modelElement;
	// relation type can be association, aggregation or composition
	private String relationType;

	public InheritanceEditPart(Inheritance in, DiagramElement modelElement) {
		super();
		this.in = in;
		this.modelElement = modelElement;
	}

	@Override
	public void performRequest(Request req) {
		Shell shell = getViewer().getControl().getShell();
		// 如果是打开编辑框的请求
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			BusinessClassInheritanceRelationEditDialog dialog = new BusinessClassInheritanceRelationEditDialog(
					shell, (Inheritance) getModel(),
					((Inheritance) getModel()).getBelongPackage(),
					BusinessModelEvent.MODEL_ELEMENT_UPDATE);
			dialog.open();
			super.performRequest(req);
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
	}

	/**
	 * Create a new RelationFigure to be used as the EditParts graphical
	 * representation. Only called if Figure has not been created.
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		relationType = IConstants.INHERITANCE;

		figure = new InheritanceFigure(relationType);
		
		

		repaintFigure();
		return figure;
	}

	/*
	 * Casts primary model to AbstractModelElement
	 */
	@Override
	protected AbstractModelElement getCastedModel() {
		return (AbstractModelElement) getModel();
	}

	@Override
	public void repaintFigure() {

		InheritanceFigure r = (InheritanceFigure) figure;
		// empty RelationFigure
		r.emptyFigure();

		String relCaption = in.getDisplayName();
		r.setCaption(relCaption);

		// 获取关系类型，改变之前固化的做法
		// r.setType(relationElement.getPropertyValue(RelationElement.TYPE_PROP).toString());
		r.setType(IConstants.INHERITANCE);
		r.setDirected(true);
	}
}
