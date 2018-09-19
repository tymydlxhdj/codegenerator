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

// TODO: Auto-generated Javadoc
/**
 * The Class LinkAnnoEditPart.
 *
 * @author mqfdy
 * @title:关系控制器
 * @description:控制业务实体间关联关系的模型与界面之间的交互
 */
public class LinkAnnoEditPart extends OmConnectionEditPart {
	
	/** The link. */
	LinkAnnotation link = null;
	
	/** The model element. */
	private DiagramElement modelElement;

	/**
	 * Instantiates a new link anno edit part.
	 *
	 * @param link
	 *            the link
	 * @param modelElement
	 *            the model element
	 */
	public LinkAnnoEditPart(LinkAnnotation link, DiagramElement modelElement) {
		super();
		this.link = link;
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
		figure = new LinkAnnoFigure(link, this);
		((PolylineConnection) figure)
				.setTargetDecoration(new PolygonDecoration());

		repaintFigure();

		return figure;
	}

	/**
	 * @return
	 */
	/*
	 * Casts primary model to AbstractModelElement
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

		LinkAnnoFigure r = (LinkAnnoFigure) figure;
		r.emptyFigure();
	}

	/**
	 * Gets the link annotation.
	 *
	 * @author mqfdy
	 * @return the link annotation
	 * @Date 2018-09-03 09:00
	 */
	public LinkAnnotation getLinkAnnotation() {
		return link;
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
