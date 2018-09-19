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
// TODO: Auto-generated Javadoc

/**
 * 业务模型画布.
 *
 * @author mqfdy
 */
public class BusinessClassCanvas extends Canvas {

	/** The dialog width. */
	private int DIALOG_WIDTH;
	
	/** The class width. */
	private int CLASS_WIDTH = 100;
	
	/** The class heigth. */
	private int CLASS_HEIGTH = 50;
	
	/** The margin left. */
	private int MARGIN_LEFT = 30;

/** The margin top. */
//	private int MARGIN_RIGHT = 30;
	private int MARGIN_TOP = 10;
	
	/** The canvas width. */
	private int CANVAS_WIDTH;
	
	/** The canvas heigth. */
	private int CANVAS_HEIGTH;

	/** The lws. */
	private LightweightSystem lws = null;
	
	/** The root. */
	IFigure root;
	
	/** The figure A. */
	IFigure figureA;
	
	/** The figure B. */
	IFigure figureB;
	
	/** The figure line. */
	IFigure figureLine;
	
	/** The business class A. */
	BusinessClass businessClassA;
	
	/** The business class B. */
	BusinessClass businessClassB;
	
	/** The navigate to class B role name. */
	String navigateToClassBRoleName;
	
	/** The navigate to class A role name. */
	String navigateToClassARoleName;

	/** The type. */
	NavigationType type;

	/** The association type. */
	AssociationType associationType;

	/**
	 * Instantiates a new business class canvas.
	 *
	 * @param parent
	 *            the parent
	 * @param width
	 *            the width
	 */
	public BusinessClassCanvas(Composite parent, int width) {
		super(parent, SWT.NONE);
		DIALOG_WIDTH = width;
		computeDialogSize();
		computeClassSize();
		lws = new LightweightSystem(this);
		// 创建一个root figure，它包含简单的布局
		lws.setContents(createRoot(parent));
	}

	/**
	 * Compute dialog size.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void computeDialogSize() {
		CANVAS_WIDTH = DIALOG_WIDTH - 80 - 80;
		this.CANVAS_HEIGTH = 50;
		this.MARGIN_LEFT = CANVAS_WIDTH / 10;
//		this.MARGIN_RIGHT = CANVAS_WIDTH / 10;
		this.MARGIN_TOP = CANVAS_HEIGTH / 4;
	}

	/**
	 * Compute class size.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void computeClassSize() {
		this.CLASS_WIDTH = CANVAS_WIDTH / 4;
		this.CLASS_HEIGTH = CANVAS_HEIGTH / 10 * 8;
	}

	/**
	 * Creates the root.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the i figure
	 * @Date 2018-09-03 09:00
	 */
	private IFigure createRoot(Composite parent) {
		root = new Figure();
		root.setFont(parent.getFont());
		return root;
	}

	/**
	 * Creates the figures.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Change data.
	 *
	 * @author mqfdy
	 * @param businessClassA
	 *            the business class A
	 * @param businessClassB
	 *            the business class B
	 * @param associationType
	 *            the association type
	 * @param navigateToClassBRoleName
	 *            the navigate to class B role name
	 * @param navigateToClassARoleName
	 *            the navigate to class A role name
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * On resized.
	 *
	 * @author mqfdy
	 * @param dialogWidth
	 *            the dialog width
	 * @Date 2018-09-03 09:00
	 */
	public void onResized(int dialogWidth) {
		this.DIALOG_WIDTH = dialogWidth;
		computeDialogSize();
		clearAll();
		createFigures();
	}

	/**
	 * 创建长方形图形.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return the i figure
	 * @Date 2018-09-03 09:00
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
	 * 创建连接线.
	 *
	 * @author mqfdy
	 * @param figure1
	 *            the figure 1
	 * @param figure2
	 *            the figure 2
	 * @return the connection
	 * @Date 2018-09-03 09:00
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

	/**
	 * Clear all.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Sets the business class A.
	 *
	 * @author mqfdy
	 * @param businessClassA
	 *            the new business class A
	 * @Date 2018-09-03 09:00
	 */
	public void setBusinessClassA(BusinessClass businessClassA) {
		this.businessClassA = businessClassA;
	}

	/**
	 * Sets the business class B.
	 *
	 * @author mqfdy
	 * @param businessClassB
	 *            the new business class B
	 * @Date 2018-09-03 09:00
	 */
	public void setBusinessClassB(BusinessClass businessClassB) {
		this.businessClassB = businessClassB;
	}

	/**
	 * Sets the association type.
	 *
	 * @author mqfdy
	 * @param associationType
	 *            the new association type
	 * @Date 2018-09-03 09:00
	 */
	public void setAssociationType(AssociationType associationType) {
		this.associationType = associationType;
	}

	/**
	 * Sets the type.
	 *
	 * @author mqfdy
	 * @param type
	 *            the new type
	 * @Date 2018-09-03 09:00
	 */
	public void setType(NavigationType type) {
		this.type = type;
	}

}
