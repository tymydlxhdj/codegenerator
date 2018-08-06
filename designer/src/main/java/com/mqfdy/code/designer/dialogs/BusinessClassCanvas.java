package com.mqfdy.code.designer.dialogs;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.NavigationType;
/**
 * 业务模型画布
 * @author mqfdy
 *
 */
public class BusinessClassCanvas extends Canvas {

	private int DIALOG_WIDTH;
	private int CLASS_WIDTH = 100;
	private int CLASS_HEIGTH = 50;
	private int MARGIN_LEFT = 30;
//	private int MARGIN_RIGHT = 30;
	private int MARGIN_TOP = 10;
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGTH;

	private LightweightSystem lws = null;
	IFigure root;
	IFigure figureA;
	IFigure figureB;
	IFigure figureLine;
	BusinessClass businessClassA;
	BusinessClass businessClassB;
	String navigateToClassBRoleName;
	String navigateToClassARoleName;

	NavigationType type;

	AssociationType associationType;

	public BusinessClassCanvas(Composite parent, int width) {
		super(parent, SWT.NONE);
		DIALOG_WIDTH = width;
		computeDialogSize();
		computeClassSize();
		lws = new LightweightSystem(this);
		// 创建一个root figure，它包含简单的布局
		lws.setContents(createRoot(parent));
	}

	private void computeDialogSize() {
		CANVAS_WIDTH = DIALOG_WIDTH - 80 - 80;
		this.CANVAS_HEIGTH = 50;
		this.MARGIN_LEFT = CANVAS_WIDTH / 10;
//		this.MARGIN_RIGHT = CANVAS_WIDTH / 10;
		this.MARGIN_TOP = CANVAS_HEIGTH / 4;
	}

	private void computeClassSize() {
		this.CLASS_WIDTH = CANVAS_WIDTH / 4;
		this.CLASS_HEIGTH = CANVAS_HEIGTH / 10 * 8;
	}

	private IFigure createRoot(Composite parent) {
		root = new Figure();
		root.setFont(parent.getFont());
		return root;
	}

	public void createFigures() {
		// 添加实体机组
		if (businessClassA != null) {
			figureA = createRectangleFigure(businessClassA.getDisplayName());
			root.add(figureA);
		}
		if (businessClassB != null) {
			figureB = createRectangleFigure(businessClassB.getDisplayName());
			root.add(figureB);
		}

		if (businessClassA != null && businessClassB != null) {
			figureLine = createConnect(figureA, figureB);
			root.add(figureLine);
		}

		XYLayout layout = new XYLayout();
		root.setLayoutManager(layout);
		layout.setConstraint(figureA, new Rectangle(MARGIN_LEFT, MARGIN_TOP,
				CLASS_WIDTH, CLASS_HEIGTH));
		layout.setConstraint(figureB, new Rectangle(
				this.CANVAS_WIDTH / 20 * 12, MARGIN_TOP, CLASS_WIDTH,
				CLASS_HEIGTH));
	}

	public void changeData(BusinessClass businessClassA,
			BusinessClass businessClassB, AssociationType associationType,
			String navigateToClassBRoleName, String navigateToClassARoleName) {
		this.associationType = associationType;
		if (businessClassA != null) {
			this.businessClassA = businessClassA;
		}
		if (businessClassB != null) {
			this.businessClassB = businessClassB;
		}
		this.navigateToClassBRoleName = navigateToClassBRoleName;
		this.navigateToClassARoleName = navigateToClassARoleName;
		clearAll();
		createFigures();
		this.redraw();
	}

	public void onResized(int dialogWidth) {
		this.DIALOG_WIDTH = dialogWidth;
		computeDialogSize();
		clearAll();
		createFigures();
	}

	/**
	 * 创建长方形图形
	 * 
	 * @param name
	 * @return
	 */
	private IFigure createRectangleFigure(String name) {
		RectangleFigure rectangleFigure = new RectangleFigure();
		// rectangleFigure.setBackgroundColor(ColorConstants.lightGray);
		rectangleFigure.setBackgroundColor(new Color(this.getDisplay(), 236,
				209, 147));
		rectangleFigure.setLayoutManager(new ToolbarLayout());
		Label label = new Label(name);
		rectangleFigure.add(label);
		return rectangleFigure;
	}

	/**
	 * 创建连接线
	 * 
	 * @param figure1
	 * @param figure2
	 * @return
	 */
	private Connection createConnect(IFigure figure1, IFigure figure2) {
		PolylineConnection connection = new PolylineConnection();
		connection.setSourceAnchor(new ChopboxAnchor(figure1));
		connection.setTargetAnchor(new ChopboxAnchor(figure2));

		Label labelStartPoint = new Label();
		ConnectionEndpointLocator sourceStartPointLocator = new ConnectionEndpointLocator(
				connection, false);
		sourceStartPointLocator.setVDistance(-5);
		connection.add(labelStartPoint, sourceStartPointLocator);

		Label labelStartPointRole = new Label();
		ConnectionEndpointLocator sourceStartPointLocatorRole = new ConnectionEndpointLocator(
				connection, false);
		sourceStartPointLocatorRole.setVDistance(5);
		connection.add(labelStartPointRole, sourceStartPointLocatorRole);

		Label labelEndPoint = new Label();
		ConnectionEndpointLocator targetEndpointLocator = new ConnectionEndpointLocator(
				connection, true);
		targetEndpointLocator.setVDistance(5);
		connection.add(labelEndPoint, targetEndpointLocator);

		Label labelEndPointRole = new Label();
		ConnectionEndpointLocator sourceEndPointLocatorRole = new ConnectionEndpointLocator(
				connection, true);
		sourceEndPointLocatorRole.setVDistance(-5);
		connection.add(labelEndPointRole, sourceEndPointLocatorRole);
		// 设置箭头
		if (businessClassA != null
				&& BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassA
						.getStereotype())) {
			// A端是内置
			PolylineDecoration decoration = new PolylineDecoration();
			decoration.setTemplate(PolylineDecoration.TRIANGLE_TIP);
			connection.setSourceDecoration(decoration);
		} else if (businessClassB != null
				&& BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassB
						.getStereotype())) {
			// B端是内置
			PolylineDecoration decoration = new PolylineDecoration();
			decoration.setTemplate(PolylineDecoration.TRIANGLE_TIP);
			connection.setTargetDecoration(decoration);
		} else {
			if (NavigationType.TWO_WAY.equals(type)
					|| NavigationType.NON_WAY.equals(type)) {
				// 双向箭头
				PolylineDecoration decorationSource = new PolylineDecoration();
				PolylineDecoration decorationTarget = new PolylineDecoration();
				decorationSource.setTemplate(PolylineDecoration.TRIANGLE_TIP);
				decorationTarget.setTemplate(PolylineDecoration.TRIANGLE_TIP);
				connection.setSourceDecoration(decorationSource);
				connection.setTargetDecoration(decorationTarget);
			}
			if (NavigationType.A2B.equals(type)) {
				PolylineDecoration decoration = new PolylineDecoration();
				decoration.setTemplate(PolylineDecoration.TRIANGLE_TIP);
				connection.setTargetDecoration(decoration);
			}
			if (NavigationType.B2A.equals(type)) {
				PolylineDecoration decoration = new PolylineDecoration();
				decoration.setTemplate(PolylineDecoration.TRIANGLE_TIP);
				connection.setSourceDecoration(decoration);
			}
		}

		// 添加RoleName显示
		if (navigateToClassBRoleName != null
				&& !"".equals(navigateToClassBRoleName)) {
			labelEndPointRole.setText(navigateToClassBRoleName);
		}
		if (navigateToClassARoleName != null
				&& !"".equals(navigateToClassARoleName)) {
			labelStartPointRole.setText(navigateToClassARoleName);
		}

		if (associationType != null
				&& AssociationType.mult2one.equals(associationType)) {
			labelStartPoint.setText("*");
			labelEndPoint.setText("1");
			return connection;
		}
		if (associationType != null
				&& AssociationType.one2mult.equals(associationType)) {
			labelStartPoint.setText("1");
			labelEndPoint.setText("*");
			return connection;
		}
		if (associationType != null
				&& AssociationType.one2one.equals(associationType)) {
			labelStartPoint.setText("1");
			labelEndPoint.setText("1");
			return connection;
		}
		if (associationType != null
				&& AssociationType.mult2mult.equals(associationType)) {
			labelStartPoint.setText("*");
			labelEndPoint.setText("*");
			return connection;
		}

		// 继承关系
		PolygonDecoration decorationInher = new PolygonDecoration();
		PointList arrowPointList = new PointList();
		arrowPointList.addPoint(0, 0);
		arrowPointList.addPoint(-2, 2);
		arrowPointList.addPoint(-2, -2);
		decorationInher.setTemplate(PolygonDecoration.TRIANGLE_TIP);
		decorationInher.setBackgroundColor(ColorConstants.white);
		connection.setTargetDecoration(decorationInher);
		return connection;
	}

	private void clearAll() {
		if (root != null) {
			if (figureA != null) {
				root.remove(figureA);
			}
			if (figureB != null) {
				root.remove(figureB);
			}
			if (figureLine != null) {
				root.remove(figureLine);
			}
		}
	}

	public void setBusinessClassA(BusinessClass businessClassA) {
		this.businessClassA = businessClassA;
	}

	public void setBusinessClassB(BusinessClass businessClassB) {
		this.businessClassB = businessClassB;
	}

	public void setAssociationType(AssociationType associationType) {
		this.associationType = associationType;
	}

	public void setType(NavigationType type) {
		this.type = type;
	}

}
