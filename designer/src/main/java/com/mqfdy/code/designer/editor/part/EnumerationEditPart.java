package com.mqfdy.code.designer.editor.part;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

import com.mqfdy.code.designer.dialogs.EnumEditDialog;
import com.mqfdy.code.designer.editor.figure.EnumerationFigure;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.policies.OmComponentEditPolicy;
import com.mqfdy.code.designer.editor.policies.OmFlowLayoutEditPolicy;
import com.mqfdy.code.designer.editor.policies.OmGraphicalNodeConnectionEditPolicy;
import com.mqfdy.code.designer.editor.policies.SimpleContainerEditPolicy;
import com.mqfdy.code.designer.views.properties.MultiPageEditorPropertySheetPage;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

/**
 * 透视图中枚举对象的控制器
 * 
 * @author mqfdy
 * 
 */
public class EnumerationEditPart extends NodeEditPart {
	DiagramElement modelElement;
	Enumeration ele;

	public Enumeration getEnumeration() {
		return ele;
	}

	public EnumerationEditPart(Enumeration ele, DiagramElement modelElement) {
		super(modelElement);
		this.ele = ele;
		this.modelElement = modelElement;
	}

	@Override
	protected IFigure createFigure() {
		String name = ele.getDisplayName();
		if (name == null || name.trim().length() == 0) {
			name = ele.getName();
		}
		figure = new EnumerationFigure(name, ele);
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
	 * 执行用户请求 点击透视图中的某一对象时，执行该方法 同时创建窗口
	 */
	@Override
	public void performRequest(final Request request) {
		final Shell shell = getViewer().getControl().getShell();
		super.performRequest(request);
		if (request.getType().equals(RequestConstants.REQ_OPEN)) {
			// 打开businClass信息窗口
			final EnumEditDialog dialog = new EnumEditDialog(shell,
					ele.getBelongPackage(), ele);
			dialog.open();
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
			// if (dialog.open() == IDialogConstants.OK_ID) {
			// repaintFigure();
			// }
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
				GraphicalEditPart parent = (GraphicalEditPart) getParent();
				if (parent != null)
					parent.setLayoutConstraint(this, getFigure(), bounds);
			}
		}
	}

	/**
	 * 重新绘制界面对象
	 */
	@Override
	public void repaintFigure() {
		final EnumerationFigure c = (EnumerationFigure) figure;
		String name = ele.getDisplayName();
		if (name == null || name.trim().length() == 0) {
			name = ele.getName();
		}
		c.setName(name);
		c.removeAll();// (c.getColumnsFigure());
		c.paintFigure(name, ele);
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
		 installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new
				 OmGraphicalNodeConnectionEditPolicy());
	}
}
