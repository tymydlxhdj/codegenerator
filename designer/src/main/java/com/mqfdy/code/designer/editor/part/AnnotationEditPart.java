package com.mqfdy.code.designer.editor.part;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.dialogs.AnnotationEditorDialog;
import com.mqfdy.code.designer.editor.figure.AnnotationFigure;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.policies.OmComponentEditPolicy;
import com.mqfdy.code.designer.editor.policies.OmFlowLayoutEditPolicy;
import com.mqfdy.code.designer.editor.policies.OmGraphicalNodeConnectionEditPolicy;
import com.mqfdy.code.designer.editor.policies.SimpleContainerEditPolicy;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Annotation;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

/**
 * 透视图中业务实体的控制器
 * 
 * @author mqfdy
 * 
 */
public class AnnotationEditPart extends NodeEditPart {
	Annotation annotation;

	DiagramElement modelElement;
	BusinessObjectModel businessObjectModel;

	private Polygon figure1;
	public AnnotationEditPart(Annotation bu, DiagramElement modelElement,
			BusinessObjectModel businessObjectModel) {
		super(modelElement);
		this.annotation = bu;
		this.modelElement = modelElement;
		this.businessObjectModel = businessObjectModel;
	}

	public Annotation getAnno() {
		return annotation;
	}

	@Override
	protected IFigure createFigure() {
		// NodeModelElement classEl = getCastedModel();
		if (figure != null) {
			figure = new AnnotationFigure(annotation);
			if (figure != null) {
				figure.setBorder(null);
			}
		}
		// repaintFigure();
		return figure;
	}
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			addPropertyChangeListener(this);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			removePropertyChangeListener(this);
		}
	}

	/**
	 * 执行用户请求 点击透视图中的对象时，执行该方法 同时创建窗口
	 */
	@Override
	public void performRequest(final Request request) {
		final Shell shell = getViewer().getControl().getShell();
		super.performRequest(request);
		if (!IModelElement.STEREOTYPE_BUILDIN.equals(annotation.getStereotype()) 
				&& !IModelElement.STEREOTYPE_REFERENCE.equals(annotation.getStereotype())
				&& request.getType().equals(RequestConstants.REQ_OPEN)) {
			// 打开Annotation信息窗口
			final AnnotationEditorDialog dialog = new AnnotationEditorDialog(
					shell, annotation, annotation.getBelongPackage());
			if (dialog.open() == IDialogConstants.OK_ID) {
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_UPDATE,
						dialog.getAnnotationCopy());
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event);
				EditorOperation.refreshProperties((ISelection) this.getParent()
						.getViewer().getSelection());
				// repaintFigure();
			}
		}
	}

	/**
	 * 取得模型DiagramElement
	 */
	@Override
	protected AbstractModelElement getCastedModel() {
		return (AbstractModelElement) getModel();
	}

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
	 * 重新绘制界面对象
	 */
	@Override
	public void repaintFigure() {
		final AnnotationFigure c = (AnnotationFigure) figure;
		c.removeAll();
		c.paintFigure(annotation);
	}

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
}
