package com.mqfdy.code.designer.editor.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.mqfdy.code.designer.editor.commands.ConnectionDeleteCommand;
import com.mqfdy.code.designer.editor.figure.anchor.BorderAnchor;
import com.mqfdy.code.designer.editor.part.extensions.BusinessAbstractConnectionEditPart;
import com.mqfdy.code.designer.editor.part.extensions.ConnectionBendpoint;
import com.mqfdy.code.designer.editor.part.extensions.ConnectionDragEditPartsTracker;
import com.mqfdy.code.designer.editor.policies.ConnectionBendPointEditPolicy;
import com.mqfdy.code.designer.editor.policies.MoveConnectionEndpointEditPolicy;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * 连线控制器.
 *
 * @author mqfdy
 */

public abstract class OmConnectionEditPart extends
		BusinessAbstractConnectionEditPart implements PropertyChangeListener {
	
	/** The bendpoints. */
	protected List bendpoints = new ArrayList();

	/** The Constant PROP_BENDPOINT. */
	final public static String PROP_BENDPOINT = "BENDPOINT";

	/** The listeners. */
	PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	/** The source anchor. */
	private BorderAnchor sourceAnchor;
	
	/** The target anchor. */
	private BorderAnchor targetAnchor;

	
	/**
	 * constructor of a new ConnectionEditPart; does nothing.
	 */
	public OmConnectionEditPart() {

	}

	/**
	 * Sets the bendpoints.
	 *
	 * @author mqfdy
	 * @param pointList
	 *            the new bendpoints
	 * @Date 2018-09-03 09:00
	 */
	public void setBendpoints(String pointList) {
		bendpoints.clear();
		if (pointList.equals("")) {
			return;
		}
		String[] s = pointList.split(",");
		for (int i = 0; i < s.length; i++) {
			String[] po = s[i].split(" ");
			String x = po[0].replace("(", "");
			String y = po[1].replace(")", "");
			Point p = new Point(Integer.parseInt(x), Integer.parseInt(y));
			getBendpoints(p);
		}
	}

	/**
	 * 
	 */
	/*
	 * Installs all necessary EditPolicies; they determine the
	 * ConnectionEditParts reaction on events.
	 * 
	 * Adds EditPolicy.CONNECTION_ENDPOINTS_ROLE, EditPolicy.CONNECTION_ROLE
	 * 
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @see org.eclipse.gef.EditPolicy
	 */
	@Override
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new MoveConnectionEndpointEditPolicy());
		// installEditPolicy(EditPolicy.LAYOUT_ROLE, new
		// ConnectionXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
				new ConnectionBendPointEditPolicy());
		// Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ConnectionEditPolicy() {
					// returns a new ConnectionDeleteCommand
					@Override
					protected Command getDeleteCommand(GroupRequest request) {
						return new ConnectionDeleteCommand(getCastedModel(),
								getHost());
					}
				});
	}

	/*
	 * Casts primary model to AbstractModelElement
	 */

	/**
	 * Gets the casted model.
	 *
	 * @author mqfdy
	 * @return the casted model
	 * @Date 2018-09-03 09:00
	 */
	protected AbstractModelElement getCastedModel() {
		return (AbstractModelElement) getModel();
	}

	/**
	 * 
	 */
	@Override
	public void refresh() {
		super.refresh();
	}

	/**
	 * Repaints the connection figure.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public abstract void repaintFigure();

	/**
	 * 
	 */
	public void activate() {
		super.activate();
		addPropertyChangeListener(this);
	}

	/**
	 * 
	 */
	public void deactivate() {
		super.deactivate();
		removePropertyChangeListener(this);
	}

	/**
	 * Adds the property change listener.
	 *
	 * @author mqfdy
	 * @param l
	 *            the l
	 * @Date 2018-09-03 09:00
	 */
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.addPropertyChangeListener(l);
	}

	/**
	 * Fire property change.
	 *
	 * @author mqfdy
	 * @param prop
	 *            the prop
	 * @param old
	 *            the old
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	public void firePropertyChange(String prop, Object old, Object newValue) {
		listeners.firePropertyChange(prop, old, newValue);
	}

	/**
	 * Removes the property change listener.
	 *
	 * @author mqfdy
	 * @param l
	 *            the l
	 * @Date 2018-09-03 09:00
	 */
	public void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.removePropertyChangeListener(l);
	}

	/**
	 * Fire structure change.
	 *
	 * @author mqfdy
	 * @param prop
	 *            the prop
	 * @param child
	 *            the child
	 * @Date 2018-09-03 09:00
	 */
	protected void fireStructureChange(String prop, Object child) {
		listeners.firePropertyChange(prop, null, child);
	}

	/**
	 * Property change.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getPropertyName();
		if (PROP_BENDPOINT.equals(property)) {
			refreshBendpoints();
		}
	}

	/**
	 * Refresh bendpoints.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void refreshBendpoints() {
		List modelConstraint = getBendpoints();
		// if(modelConstraint == null || modelConstraint.size() == 0)
		// return;
		List figureConstraint = new ArrayList();
		for (int i = 0; i < modelConstraint.size(); i++) {
			ConnectionBendpoint cbp = (ConnectionBendpoint) modelConstraint
					.get(i);
			RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
			rbp.setRelativeDimensions(cbp.getFirstRelativeDimension(),
					cbp.getSecondRelativeDimension());
			rbp.setWeight((i + 1) / ((float) modelConstraint.size() + 1));
			figureConstraint.add(rbp);
		}
		getConnectionFigure().setRoutingConstraint(figureConstraint);
	}

	/**
	 * Gets the bendpoints.
	 *
	 * @author mqfdy
	 * @param p
	 *            the p
	 * @return the bendpoints
	 * @Date 2018-09-03 09:00
	 */
	public void getBendpoints(Point p) {
		Point ref1 = ((Connection) figure).getSourceAnchor()
				.getReferencePoint();
		Point ref2 = ((Connection) figure).getTargetAnchor()
				.getReferencePoint();

		this.figure.translateToRelative(ref1);
		this.figure.translateToRelative(ref2);

		ConnectionBendpoint cbp = new ConnectionBendpoint(p);
		cbp.setRelativeDimensions(p.getDifference(ref1), p.getDifference(ref2));

		bendpoints.add(cbp);
		firePropertyChange(PROP_BENDPOINT, null, null);
	}

	/**
	 * Gets the bendpoints.
	 *
	 * @author mqfdy
	 * @return the bendpoints
	 * @Date 2018-09-03 09:00
	 */
	public List getBendpoints() {
		if (bendpoints == null || bendpoints.size() == 0) {
			DiagramElement conele = ((DiagramElement) (getTarget() == null ? getSource()
					: getTarget()).getModel()).getBelongDiagram()
					.getElementById(getCastedModel().getId());
			if (conele.getStyle().getPointList() != null
					&& !conele.getStyle().getPointList().equals("")) {
				setBendpoints(conele.getStyle().getPointList());
				firePropertyChange(PROP_BENDPOINT, null, null);
			}
		}
		return bendpoints;
	}

	/**
	 * Sets the bendpoints.
	 *
	 * @author mqfdy
	 * @param bendpoints
	 *            the new bendpoints
	 * @Date 2018-09-03 09:00
	 */
	public void setBendpoints(List bendpoints) {
		this.bendpoints = bendpoints;
	}

	/**
	 * Adds the bendpoint.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @param point
	 *            the point
	 * @param point2
	 *            the point 2
	 * @Date 2018-09-03 09:00
	 */
	public void addBendpoint(int index, ConnectionBendpoint point, Point point2) {
		getBendpoints().add(index, point);
		DiagramElement conele = ((DiagramElement) getSource().getModel())
				.getBelongDiagram().getElementById(getCastedModel().getId());
		String l = "";
		if (!conele.getStyle().getPointList().equals("")) {
			String[] s = conele.getStyle().getPointList().split(",");
			for (int i = 0; i < (s.length > index ? s.length : index + 1); i++) {
				if (i == index) {
					l = (l.equals("") ? "" : l + ",") + "(" + point2.x + " "
							+ point2.y + ")";
				}
				if (i < s.length) {
					String[] po = s[i].split(" ");
					String x = po[0].replace("(", "");
					String y = po[1].replace(")", "");
					if (point2.x != Integer.parseInt(x)
							&& point2.y != Integer.parseInt(y))
						l = (l.equals("") ? "" : l + ",") + "(" + x + " " + y
								+ ")";
				}
			}
			conele.getStyle().setPointList(l);
		} else {
			conele.getStyle().setPointList(
					(conele.getStyle().getPointList().equals("") ? "" : conele
							.getStyle().getPointList() + ",")
							+ "(" + point2.x + " " + point2.y + ")");
		}
		firePropertyChange(PROP_BENDPOINT, null, null);
	}

	/**
	 * 为了在更新两个dimension后能发送事件，在MoveBendpointCommand要在用这个方法设置新坐标，
	 * 而不是直接用BendPoint里的方法。.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @param d1
	 *            the d 1
	 * @param d2
	 *            the d 2
	 * @param point
	 *            the point
	 * @Date 2018-09-03 09:00
	 */
	public void setBendpointRelativeDimensions(int index, Dimension d1,
			Dimension d2, Point point) {
		ConnectionBendpoint cbp = (ConnectionBendpoint) getBendpoints().get(
				index);
		cbp.setRelativeDimensions(d1, d2);

		DiagramElement conele = ((DiagramElement) getSource().getModel())
				.getBelongDiagram().getElementById(getCastedModel().getId());
		String l = "";
		if (!conele.getStyle().getPointList().equals("")) {
			String[] s = conele.getStyle().getPointList().split(",");
			for (int i = 0; i < s.length; i++) {
				String[] po = s[i].split(" ");
				if (i == index) {
					l = (l.equals("") ? "" : l + ",") + "(" + point.x + " "
							+ point.y + ")";
				} else {
					String x = po[0].replace("(", "");
					String y = po[1].replace(")", "");
					l = (l.equals("") ? "" : l + ",") + "(" + x + " " + y + ")";
				}
			}
			conele.getStyle().setPointList(l);
		}
		firePropertyChange(PROP_BENDPOINT, null, null);
	}

	/**
	 * Removes the bendpoint.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @Date 2018-09-03 09:00
	 */
	public void removeBendpoint(int index) {
		getBendpoints().remove(index);
		DiagramElement conele = ((DiagramElement) getSource().getModel())
				.getBelongDiagram().getElementById(getCastedModel().getId());
		String l = "";
		if (!conele.getStyle().getPointList().equals("")) {
			String[] s = conele.getStyle().getPointList().split(",");
			for (int i = 0; i < s.length; i++) {
				String[] po = s[i].split(" ");
				if (i == index) {
					// l = (l.equals("")?"":l+",")+ "(" + point.x + " " +
					// point.y + ")";
				} else {
					String x = po[0].replace("(", "");
					String y = po[1].replace(")", "");
					l = (l.equals("") ? "" : l + ",") + "(" + x + " " + y + ")";
				}
			}
			conele.getStyle().setPointList(l);
		}
		firePropertyChange(PROP_BENDPOINT, null, null);
	}
	
	/**
	 * 设置连线可以拖动.
	 *
	 * @author mqfdy
	 * @param req
	 *            the req
	 * @return the drag tracker
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public DragTracker getDragTracker(Request req) {
		return new ConnectionDragEditPartsTracker(this) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.gef.tools.DragEditPartsTracker#isMove()
			 */
			@Override
			protected boolean isMove() {
				return true;
			}
		};
	}

	/**
	 * Gets the target edit part.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the target edit part
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		// TODO Auto-generated method stub
		return this;// super.getTargetEditPart(request);
	}

	/**
	 * Gets the command.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public Command getCommand(Request request) {
		// TODO Auto-generated method stub
		// new MoveConnectionCommand(this,request);
		return super.getCommand(request);
	}
	
	/**
	 * Gets the source anchor.
	 *
	 * @author mqfdy
	 * @return the source anchor
	 * @Date 2018-09-03 09:00
	 */
	public BorderAnchor getSourceAnchor() {
		return sourceAnchor;
	}

	/**
	 * Sets the source anchor.
	 *
	 * @author mqfdy
	 * @param sourceAnchor
	 *            the new source anchor
	 * @Date 2018-09-03 09:00
	 */
	public void setSourceAnchor(BorderAnchor sourceAnchor) {
		this.sourceAnchor = sourceAnchor;
	}

	/**
	 * Gets the target anchor.
	 *
	 * @author mqfdy
	 * @return the target anchor
	 * @Date 2018-09-03 09:00
	 */
	public BorderAnchor getTargetAnchor() {
		return targetAnchor;
	}

	/**
	 * Sets the target anchor.
	 *
	 * @author mqfdy
	 * @param targetAnchor
	 *            the new target anchor
	 * @Date 2018-09-03 09:00
	 */
	public void setTargetAnchor(BorderAnchor targetAnchor) {
		this.targetAnchor = targetAnchor;
	}
}
