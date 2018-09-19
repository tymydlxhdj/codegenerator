package com.mqfdy.code.designer.editor.part;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.swt.widgets.Display;

import com.mqfdy.code.designer.editor.figure.ReferenceObjectFigure;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.policies.OmComponentEditPolicy;
import com.mqfdy.code.designer.editor.policies.OmFlowLayoutEditPolicy;
import com.mqfdy.code.designer.editor.policies.OmGraphicalNodeConnectionEditPolicy;
import com.mqfdy.code.designer.editor.policies.SimpleContainerEditPolicy;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

// TODO: Auto-generated Javadoc
/**
 * 透视图中引用业务实体的控制器.
 *
 * @author mqfdy
 */
public class ReferenceObjectEditPart extends NodeEditPart {
	
	/** The reference object. */
	ReferenceObject referenceObject;
	
	/** The model element. */
	DiagramElement modelElement;
	
	/** The business object model. */
	BusinessObjectModel businessObjectModel;

	/**
	 * Instantiates a new reference object edit part.
	 *
	 * @param bu
	 *            the bu
	 * @param modelElement
	 *            the model element
	 * @param businessObjectModel
	 *            the business object model
	 */
	public ReferenceObjectEditPart(ReferenceObject bu,
			DiagramElement modelElement, BusinessObjectModel businessObjectModel) {
		super(modelElement);
		this.referenceObject = bu;
		this.modelElement = modelElement;
		this.businessObjectModel = businessObjectModel;
	}

	/**
	 * @return
	 */
	@Override
	protected IFigure createFigure() {
		String name = referenceObject.getReferenceObject().getDisplayName();
		if (name == null || name.trim().length() == 0) {
			name = referenceObject.getReferenceObject().getName();
		}
		figure = new ReferenceObjectFigure(name, referenceObject);
		// repaintFigure();
		return figure;
	}

	/**
	 * 
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			addPropertyChangeListener(this);
		}
	}

	/**
	 * 
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			removePropertyChangeListener(this);
		}
	}

	/**
	 * 执行用户请求 点击透视图中的对象时，执行该方法 同时创建窗口.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void performRequest(final Request request) {

	}

	/**
	 * 取得模型DiagramElement.
	 *
	 * @author mqfdy
	 * @return the casted model
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected AbstractModelElement getCastedModel() {
		return (AbstractModelElement) getModel();
	}

	/**
	 * 
	 */
	@Override
	protected void refreshVisuals() {
		if (modelElement != null) {
			ElementStyle style = modelElement.getStyle();// =
															// ((DiagramElement)getCastedModel()).getStyle();
			if (style != null) {
				Point p = new Point(style.getPositionX(), style.getPositionY());
				Dimension m = new Dimension(style.getWidth(), style.getHeight());
				Rectangle bounds = new Rectangle(p, m);
				GraphicalEditPart parent = (GraphicalEditPart) this.getParent();
				if (parent != null) {
					parent.setLayoutConstraint(this, getFigure(), bounds);
				}
			}
		}
	}

	/**
	 * 重新绘制界面对象.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void repaintFigure() {
		final ReferenceObjectFigure c = (ReferenceObjectFigure) figure;
		String name = referenceObject.getReferenceObject().getDisplayName();
		if (name == null || name.trim().length() == 0) {
			name = referenceObject.getReferenceObject().getName();
		}
		c.setName(name);
		c.removeAll();
		c.paintFigure(name, referenceObject);
	}

	/**
	 * Property change.
	 *
	 * @author mqfdy
	 * @param evt
	 *            the evt
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyId = evt.getPropertyName();

		if (propertyId.equals(NodeModelElement.CHILD_ADDED_PROP)
				|| propertyId.equals(NodeModelElement.CHILD_REMOVED_PROP)) {
			Display.getDefault().syncExec(new Runnable() {
				public synchronized void run() {
					refreshChildren();
				}
			});
		} else {
			super.propertyChange(evt);
		}

	}

	/**
	 * 
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();

		installEditPolicy(EditPolicy.CONTAINER_ROLE,
				new SimpleContainerEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new OmComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new OmFlowLayoutEditPolicy());
		// 如果元素能够被连接附着，那么它应该使用GraphicalNodeEditPolicy
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new OmGraphicalNodeConnectionEditPolicy());
	}

	/**
	 * Gets the reference object.
	 *
	 * @author mqfdy
	 * @return the reference object
	 * @Date 2018-09-03 09:00
	 */
	public ReferenceObject getReferenceObject() {
		return referenceObject;
	}

}
