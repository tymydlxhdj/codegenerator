package com.mqfdy.code.designer.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.Request;

import com.mqfdy.code.designer.editor.figure.LinkAnnoFigure;
import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * @title:关系控制器
 * @description:控制业务实体间关联关系的模型与界面之间的交互
 * @author mqfdy
 */
public class LinkAnnoEditPart extends OmConnectionEditPart {
	LinkAnnotation link = null;
	private DiagramElement modelElement;

	public LinkAnnoEditPart(LinkAnnotation link, DiagramElement modelElement) {
		super();
		this.link = link;
		this.setModelElement(modelElement);
	}

	@Override
	public void performRequest(Request req) {
		super.performRequest(req);
	}

	/**
	 * Create a new RelationFigure to be used as the EditParts graphical
	 * representation. Only called if Figure has not been created.
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		figure = new LinkAnnoFigure(link, this);
		((PolylineConnection) figure)
				.setTargetDecoration(new PolygonDecoration());

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

		LinkAnnoFigure r = (LinkAnnoFigure) figure;
		r.emptyFigure();
	}

	public LinkAnnotation getLinkAnnotation() {
		return link;
	}

	public DiagramElement getModelElement() {
		return modelElement;
	}

	public void setModelElement(DiagramElement modelElement) {
		this.modelElement = modelElement;
	}

}
