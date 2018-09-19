package com.mqfdy.code.designer.editor.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ReconnectRequest;

import com.mqfdy.code.designer.editor.figure.anchor.BorderAnchor;
import com.mqfdy.code.designer.editor.figure.anchor.RectangleBorderAnchor;
import com.mqfdy.code.designer.editor.part.extensions.BusinessAbstractGraphicalEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.policies.OmComponentEditPolicy;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

// TODO: Auto-generated Javadoc
/**
 * node控制器.
 *
 * @author mqfdy
 */
public abstract class NodeEditPart extends BusinessAbstractGraphicalEditPart
		implements PropertyChangeListener, org.eclipse.gef.NodeEditPart {
	
	/** The model element. */
	private DiagramElement modelElement;
	
	/** The conele. */
	DiagramElement conele = null;

	/** The listeners. */
	private transient PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

	/**
	 * Instantiates a new node edit part.
	 *
	 * @param modelElement
	 *            the model element
	 */
	public NodeEditPart(DiagramElement modelElement){
		this.modelElement = modelElement;
	}
	
//	public ConnectionAnchor getSourceConnectionAnchor(
//			ConnectionEditPart connection) {
//		return new SourceAnchor(getFigure());
//	}
//
//	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
//		return new SourceAnchor(getFigure());
//	}
//
//	public ConnectionAnchor getTargetConnectionAnchor(
//			ConnectionEditPart connection) {
//		if (connection.getSource() == connection.getTarget()) {
//			if (((Association) connection.getModel()).getClassA() == ((Association) connection
//					.getModel()).getClassB()) {
//				TargetAnchor an = new TargetAnchor(getFigure());
//				an.place = new Point(1, 0);
//				return an;
//			}
//		}
//		return new TargetAnchor(getFigure());
//	}
//
//	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
//		return new TargetAnchor(getFigure());
//	}

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
	 * 
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new OmComponentEditPolicy());
	}

	/**
	 * Property change.
	 *
	 * @author mqfdy
	 * @param evt
	 *            the evt
	 * @Date 2018-09-03 09:00
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();

		if (NodeModelElement.SIZE_PROP.equals(prop)
				|| NodeModelElement.LOCATION_PROP.equals(prop)) {
			// refresh visuals
			refreshVisuals();
		} else if (NodeModelElement.SOURCE_CONNECTIONS_PROP.equals(prop)) {
			// refresh source connections
			refreshSourceConnections();
		} else if (NodeModelElement.TARGET_CONNECTIONS_PROP.equals(prop)) {
			// refresh target connections
			refreshTargetConnections();
		}/*
		 * else if (ModelElement.GENERAL_CHANGE_PROP.equals(prop)) { // general
		 * change occures : repaint NodeFigure, refresh visuals and children
		 * Display.getDefault().syncExec(new Runnable() { public void run() {
		 * repaintFigure(); refreshVisuals(); refreshChildren(); } }); }
		 */
	}
	
	/**
	 * Handle layout change.
	 *
	 * @author mqfdy
	 * @param evt
	 *            the evt
	 * @Date 2018-09-03 09:00
	 */
	protected void handleLayoutChange(PropertyChangeEvent evt)
	{
	}

	/**
	 * handles change in bounds, to be overridden by subclass.
	 *
	 * @author mqfdy
	 * @param evt
	 *            the evt
	 * @Date 2018-09-03 09:00
	 */
	protected void handleBoundsChange(PropertyChangeEvent evt)
	{
	}
	/*
	 * Casts primary model to NodeModelElement
	 */
	// protected abstract NodeModelElement getCastedModel();

	/**
	 * Gets the casted model.
	 *
	 * @author mqfdy
	 * @return the casted model
	 * @Date 2018-09-03 09:00
	 */
	protected abstract AbstractModelElement getCastedModel();

	/**
	 * Refreshes the NodeEditPart's visuals : resets the parent's layout
	 * constraint.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void refreshVisuals() {
	}

	/**
	 * Repaint figure.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public abstract void repaintFigure();

	/**
	 * @return
	 */
	@Override
	protected List getModelChildren() {
		return getCastedModel().getChildren();
	}

	/**
	 * Attach a non-null PropertyChangeListener to this object.
	 *
	 * @author mqfdy
	 * @param propertyChangeListener
	 *            a non-null PropertyChangeListener instance
	 * @throws IllegalArgumentException
	 *             if the parameter is null
	 * @Date 2018-09-03 09:00
	 */
	public synchronized void addPropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener == null) {
			throw new IllegalArgumentException();
		}
		listeners.addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * Adds the property change listener.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Report a property change to registered listeners (for example edit
	 * parts).
	 *
	 * @author mqfdy
	 * @param property
	 *            the programmatic name of the property that changed
	 * @param oldValue
	 *            the old value of this property
	 * @param newValue
	 *            the new value of this property
	 * @Date 2018-09-03 09:00
	 */
	public void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (listeners.hasListeners(property)) {
			listeners.firePropertyChange(property, oldValue, newValue);
		}
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
	 * Removes the property change listener.
	 *
	 * @author mqfdy
	 * @param propertyChangeListener
	 *            the property change listener
	 * @Date 2018-09-03 09:00
	 */
	public synchronized void removePropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener != null) {
			listeners.removePropertyChangeListener(propertyChangeListener);
		}
	}

	/**
	 * Removes the property change listener.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		if (listener != null)
			listeners.removePropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * @return
	 */
	@Override
	protected List getModelSourceConnections() {
		List<AbstractModelElement> eleList = new ArrayList<AbstractModelElement>();
		AbstractModelElement ab = getCastedModel();
		AbstractModelElement model = getCastedModel();

		do {
			model = model.getParent();
		} while (!(model instanceof BusinessObjectModel));
		BusinessObjectModel businessObjectModel = (BusinessObjectModel) model;

		List<String> list = new ArrayList<String>();
		if (ab instanceof DiagramElement) {
			for (DiagramElement ele : ((Diagram) (((DiagramElement) ab)
					.getParent())).getElements()) {
				list.add(ele.getObjectId());
			}
			ab = (AbstractModelElement) businessObjectModel
					.getModelElementById(((DiagramElement) modelElement)
							.getObjectId());
			if (ab == null) {
				ab = (AbstractModelElement) BusinessModelManager.getBuildInOm()
						.getModelElementById(
								((DiagramElement) modelElement).getObjectId());
			}
		}
		if (ab instanceof ReferenceObject) {
			for (Association a : businessObjectModel.getAssociations()) {
				if (a.getClassA() != null
						&& a.getClassA().equals(
								((ReferenceObject) ab).getReferenceObject())
						&& a.getClassB() != null
						&& list.contains(a.getClassB().getId())) {
					eleList.add(a);
				}
			}
			for (LinkAnnotation link : businessObjectModel.getLinkAnnotations()) {
				if (link.getClassA() != null && link.getClassA().equals(ab)
						&& link.getClassB() != null /*
												 * &&
												 * list.contains(a.getClassB()
												 * .getId())
												 */) {
					if (list.contains(link.getClassB().getId())) {
						eleList.add(link);
					} 
				}
			}
			for (Inheritance in : businessObjectModel.getInheritances()) {
				if (in.getChildClass() != null
						&& in.getChildClass().getId().equals(ab.getId())
						&& in.getParentClass() != null
						&& list.contains(in.getParentClass().getId())) {
					eleList.add(in);
				}
			}
		}else{
		// ab =
		// BusinessModelUtil.getEditorBusnessModelManager().queryObjectById(((DiagramElement)getCastedModel()).getObjectId());
//		if (ab instanceof BusinessClass) {
			for (Association a : businessObjectModel.getAssociations()) {
				if (a.getClassA() != null && a.getClassA().equals(ab)
						&& a.getClassB() != null /*
												 * &&
												 * list.contains(a.getClassB()
												 * .getId())
												 */) {
					if (list.contains(a.getClassB().getId())) {
						eleList.add(a);
					} else if (businessObjectModel.getReferenceObjects() != null) {
						for (ReferenceObject ro : businessObjectModel
								.getReferenceObjects()) {
							if (ro.getReferenceObject().getId()
									.equals(a.getClassB().getId())
									&& list.contains(ro.getId())) {
								eleList.add(a);
							}
						}
					}

				}
			}
			for (Inheritance in : businessObjectModel.getInheritances()) {
				if (in.getChildClass() != null
						&& in.getChildClass().getId().equals(ab.getId())
						&& in.getParentClass() != null
						&& list.contains(in.getParentClass().getId())) {
					eleList.add(in);
				}
			}
			for (LinkAnnotation link : businessObjectModel.getLinkAnnotations()) {
				if (link.getClassA() != null && link.getClassA().equals(ab)
						&& link.getClassB() != null /*
												 * &&
												 * list.contains(a.getClassB()
												 * .getId())
												 */) {
					if (list.contains(link.getClassB().getId())) {
						eleList.add(link);
					} 
				}
			}
		}
//		}
		for (AbstractModelElement a : eleList) {
			if (a instanceof Association || a instanceof LinkAnnotation || a instanceof Inheritance) {
				if (modelElement.getBelongDiagram().getElementById(a.getId()) == null) {
					DiagramElement ele = new DiagramElement();
					ele.setObjectId(a.getId());
					ElementStyle st = new ElementStyle();
					st.setPositionX(0);
					st.setPositionY(0);
					st.setEndPositionX(0);
					st.setEndPositionY(0);
					ele.setStyle(st);
					ele.setBelongDiagram(modelElement.getBelongDiagram());
					modelElement.getBelongDiagram().addElement(ele);
				}
			}
		}

		return eleList;// ((Diagram)getCastedModel()).getElements();
	}

	/**
	 * @return
	 */
	@Override
	protected List getModelTargetConnections() {
		List<AbstractModelElement> eleList = new ArrayList<AbstractModelElement>();
		AbstractModelElement ab = getCastedModel();
		AbstractModelElement model = getCastedModel();

		do {
			model = model.getParent();
		} while (!(model instanceof BusinessObjectModel));
		BusinessObjectModel businessObjectModel = (BusinessObjectModel) model;
		List<String> list = new ArrayList<String>();
		if (ab instanceof DiagramElement) {
			for (DiagramElement ele : ((Diagram) (((DiagramElement) ab)
					.getParent())).getElements()) {
				list.add(ele.getObjectId());
			}
			ab = (AbstractModelElement) businessObjectModel
					.getModelElementById(((DiagramElement) modelElement)
							.getObjectId());

			if (ab == null) {
				ab = (AbstractModelElement) BusinessModelManager.getBuildInOm()
						.getModelElementById(
								((DiagramElement) modelElement).getObjectId());
			}
		}
		if (ab instanceof ReferenceObject) {
			for (Association a : businessObjectModel.getAssociations()) {
				if (a.getClassB() != null
						&& a.getClassB().equals(
								((ReferenceObject) ab).getReferenceObject())
						&& a.getClassA() != null
						&& list.contains(a.getClassA().getId())) {
					eleList.add(a);
				}
			}
			for (LinkAnnotation a : businessObjectModel.getLinkAnnotations()) {
				if (a.getClassB() != null && a.getClassB().equals(ab)
						&& a.getClassA() != null /*
												 * &&
												 * list.contains(a.getClassB()
												 * .getId())
												 */) {
					if (list.contains(a.getClassA().getId())) {
						eleList.add(a);
					}
				}
			}
			for (Inheritance in : businessObjectModel.getInheritances()) {
				if (in.getParentClass() != null
						&& in.getParentClass().getId().equals(ab.getId())
						&& in.getChildClass() != null
						&& list.contains(in.getChildClass().getId())) {
					eleList.add(in);
				}
			}
		}
		else{
//		if (ab instanceof BusinessClass) {
			// for(Association a : businessObjectModel.getAssociations()){
			// if(a.getClassB()!=null && a.getClassB().equals(ab) &&
			// a.getClassA()!=null && list.contains(a.getClassA().getId())){
			// eleList.add(a);
			// }
			// }

			for (Association a : businessObjectModel.getAssociations()) {
				if (a.getClassB() != null && a.getClassB().equals(ab)
						&& a.getClassA() != null /*
												 * &&
												 * list.contains(a.getClassB()
												 * .getId())
												 */) {
					if (list.contains(a.getClassA().getId())) {
						eleList.add(a);
					} else if (businessObjectModel.getReferenceObjects() != null) {
						for (ReferenceObject ro : businessObjectModel
								.getReferenceObjects()) {
							if (ro.getReferenceObject().getId()
									.equals(a.getClassA().getId())
									&& list.contains(ro.getId())) {
								eleList.add(a);
							}
						}
					}

				}
			}

			for (Inheritance in : businessObjectModel.getInheritances()) {
				if (in.getParentClass() != null
						&& in.getParentClass().getId().equals(ab.getId())
						&& in.getChildClass() != null
						&& list.contains(in.getChildClass().getId())) {
					eleList.add(in);
				}
			}
			for (LinkAnnotation a : businessObjectModel.getLinkAnnotations()) {
				if (a.getClassB() != null && a.getClassB().equals(ab)
						&& a.getClassA() != null /*
												 * &&
												 * list.contains(a.getClassB()
												 * .getId())
												 */) {
					if (list.contains(a.getClassA().getId())) {
						eleList.add(a);
					}
				}
			}
		}
//		}
		for (AbstractModelElement a : eleList) {
			if (a instanceof Association || a instanceof LinkAnnotation || a instanceof Inheritance ) {
				if (modelElement.getBelongDiagram().getElementById(a.getId()) == null) {
					DiagramElement ele = new DiagramElement();
					ele.setObjectId(a.getId());
					ElementStyle st = new ElementStyle();
					st.setPositionX(0);
					st.setPositionY(0);
					st.setEndPositionX(0);
					st.setEndPositionY(0);
					ele.setStyle(st);
					ele.setBelongDiagram(modelElement.getBelongDiagram());
					modelElement.getBelongDiagram().addElement(ele);
				}
			}
		}
		return eleList;// ((Diagram)getCastedModel()).getElements();
	}

	/**
	 * Gets the source connection anchor.
	 *
	 * @author mqfdy
	 * @param connection
	 *            the connection
	 * @return the source connection anchor
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 获取连线锚点Source
	 */
	@SuppressWarnings("deprecation")
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		OmConnectionEditPart con = (OmConnectionEditPart) connection;
		BorderAnchor anchor = con.getSourceAnchor();
		if (anchor == null || anchor.getOwner() != getFigure()) {
			if (getModel() instanceof DiagramElement) {
				anchor = new RectangleBorderAnchor(getFigure());
				conele = ((DiagramElement) getModel()).getBelongDiagram()
						.getElementById(con.getCastedModel().getId());
				
				Point loc = new Point(conele.getStyle().getPositionX(), conele
						.getStyle().getPositionY());
				Rectangle rect = Rectangle.SINGLETON;
				rect.setBounds(((GraphicalEditPart) con.getSource())
						.getFigure().getBounds());
				getFigure().translateToAbsolute(rect);
				double dx = loc.x;// - ref.x;
				double dy = loc.y;// - ref.y;
				anchor.setAngle(Math.atan2(dy, dx));
				if (conele.getStyle().getPositionX() == 0.0
						&& conele.getStyle().getPositionY() == 0.0)
					anchor.setAngle(Double.MAX_VALUE);
			} else
				throw new IllegalArgumentException("unexpected model");

			con.setSourceAnchor(anchor);
			// con.getSource().refresh();
		}
		return anchor;
	}

	/**
	 * Gets the source connection anchor.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the source connection anchor
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 获取连线锚点Source
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		if (request instanceof ReconnectRequest) {
			ReconnectRequest r = (ReconnectRequest) request;
			OmConnectionEditPart con = (OmConnectionEditPart) r.getConnectionEditPart();
			BorderAnchor anchor = con.getSourceAnchor();
			GraphicalEditPart part = (GraphicalEditPart) r.getTarget();
			Point loc = r.getLocation();
			Rectangle rect = Rectangle.SINGLETON;
			rect.setBounds(getFigure().getBounds());
			getFigure().translateToAbsolute(rect);
			Point ref = rect.getCenter();
			float dx = loc.x - ref.x;
			float dy = loc.y - ref.y;
			if (anchor == null || anchor.getOwner() != part.getFigure()) {
				if (getModel() instanceof DiagramElement)
					anchor = new RectangleBorderAnchor(getFigure());
				else
					throw new IllegalArgumentException("unexpected model");
			}
			anchor.setAngle(Math.atan2(dy, dx));
			// con.setSourceAnchor(anchor);
			// con.getSource().refresh();
			return anchor;
		} /*
		 * else if (request instanceof CreateConnectionRequest) {
		 * CreateConnectionRequest r = (CreateConnectionRequest) request;
		 * RectangleBorderAnchor an = new RectangleBorderAnchor(getFigure());
		 * for(Object conn:this.getModelSourceConnections()){ if (getModel()
		 * instanceof DiagramElement){ for(DiagramElement
		 * ele:((DiagramElement)getModel()).getBelongDiagram().getElements()){
		 * if(ele.getObjectId().equals(((AbstractModelElement) conn).getId())){
		 * if
		 * (ele.getStyle().getPositionX()==0&&ele.getStyle().getPositionY()==0){
		 * an.setAngle(Math.atan2(1,0)); //
		 * ((DiagramElement)getModel()).getStyle().setPositionY(1); } } } } }
		 * return an; }
		 */else {
			if (getModel() instanceof DiagramElement)
				return new RectangleBorderAnchor(getFigure());
			else
				throw new IllegalArgumentException("unexpected model");
		}
	}

	/**
	 * Gets the target connection anchor.
	 *
	 * @author mqfdy
	 * @param connection
	 *            the connection
	 * @return the target connection anchor
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 获取连线锚点Target
	 */
	@SuppressWarnings("deprecation")
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		OmConnectionEditPart con = (OmConnectionEditPart) connection;
		BorderAnchor anchor = con.getTargetAnchor();
		if (anchor == null || anchor.getOwner() != getFigure()) {
			if (getModel() instanceof DiagramElement) {
				anchor = new RectangleBorderAnchor(getFigure());
				conele = ((DiagramElement) getModel()).getBelongDiagram()
						.getElementById(con.getCastedModel().getId());
				Point loc = new Point(conele.getStyle().getEndPositionX(),
						conele.getStyle().getEndPositionY());
				DiagramElement s = ((DiagramElement) getModel())
						.getBelongDiagram().getElementById(
								con.getCastedModel().getId());
				if (loc.x == 0.0 && loc.y == 0.0) {
					anchor.setAngle(Double.MAX_VALUE);
					if (connection.getSource() == connection.getTarget()) {
						if (((Association) connection.getModel()).getClassA() == ((Association) connection
								.getModel()).getClassB()) {
							((RectangleBorderAnchor) anchor).place = new Point(
									1, 0);
							return anchor;
						}
					}
				} else
					anchor.setAngle(Math.atan2(s.getStyle().getEndPositionY(),
							s.getStyle().getEndPositionX()));
			} else
				throw new IllegalArgumentException("unexpected model");
			con.setTargetAnchor(anchor);
			// con.refresh();
			// con.getTarget().refresh();
			return anchor;
		}
		return anchor;
	}
	
	/**
	 * Gets the target connection anchor.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the target connection anchor
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 获取连线锚点Target
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		if (request instanceof ReconnectRequest) {
			ReconnectRequest r = (ReconnectRequest) request;
			OmConnectionEditPart con = (OmConnectionEditPart) r.getConnectionEditPart();
			BorderAnchor anchor = con.getTargetAnchor();
			Point loc = r.getLocation();
			Rectangle rect = Rectangle.SINGLETON;
			rect.setBounds(getFigure().getBounds());
			getFigure().translateToAbsolute(rect);
			Point ref = rect.getCenter();
			GraphicalEditPart part = (GraphicalEditPart) r.getTarget();
			if (anchor == null || anchor.getOwner() != part.getFigure()) {
				// if(con.getModel() instanceof Association &&
				// ((Association)con.getModel()).getClassA()==((Association)con.getModel()).getClassB())
				// return new RectangleBorderAnchor(getFigure());

				if (getModel() instanceof DiagramElement)
					anchor = new RectangleBorderAnchor(getFigure());
				else
					throw new IllegalArgumentException("unexpected model");
			}
			float dx = loc.x - ref.x;
			float dy = loc.y - ref.y;
			anchor.setAngle(Math.atan2(dy, dx));
			// con.setTargetAnchor(anchor);
			// con.refresh();
			// con.getTarget().refresh();
			return anchor;
			// } else if (request instanceof CreateConnectionRequest) {
			// CreateConnectionRequest r = (CreateConnectionRequest) request;
			// return new RectangleBorderAnchor(getFigure());
		} else {
			if (getModel() instanceof DiagramElement)
				return new RectangleBorderAnchor(getFigure());
			else
				throw new IllegalArgumentException("unexpected model");
		}
	}
}
