package com.mqfdy.code.designer.editor.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Display;

import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.editor.part.extensions.SelfConnectRouter;
import com.mqfdy.code.designer.editor.policies.OmContainerEditPolicy;
import com.mqfdy.code.designer.editor.policies.OmXYLayoutEditPolicy;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 图控制器
 * 
 * @author mqfdy
 * 
 */
public class DiagramEditPart extends NodeEditPart
		implements PropertyChangeListener {
	private Diagram bm;
	public DiagramEditPart(Diagram modelElement) {
		super(null);
		this.bm = modelElement;
	}

	@Override
	protected IFigure createFigure() {
		Figure f = new FreeformLayer();
		f.setLayoutManager(new FreeformLayout());
		f.setBorder(new MarginBorder(5));

		// Create the static router for the connection layer
		ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
        //connLayer.setConnectionRouter(new BendpointConnectionRouter());
		connLayer.setConnectionRouter(new SelfConnectRouter(bm));
		// connLayer.setConnectionRouter(new AbFanRouter());
		return f;
	}

	@Override
	protected void createEditPolicies() {
		// add container behaviour
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new OmContainerEditPolicy());
		// install layout role : responsible for moving, resizing, reparenting,
		// and creating children
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new OmXYLayoutEditPolicy());
		// avoids removal of this part
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
	}

	/**
	 * Handle property change events in the class diagram. For instant, the only
	 * implemented reactions are the adding or removing of children.
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		// a child has been added or removed to the class diagram : refresh all
		// children
		if (prop.equals(NodeModelElement.CHILD_ADDED_PROP)
				|| prop.equals(NodeModelElement.CHILD_REMOVED_PROP)) {
			// execute in SWT thread; must be synchrone
			Display.getDefault().syncExec(new Runnable() {
				public synchronized void run() {
					refreshChildren();
				}
			});
		}
	}
	protected AbstractModelElement getCastedModel() {
		return (AbstractModelElement) getModel();
	}

	@Override
	protected List getModelChildren() {
		List<AbstractModelElement> eleList = new ArrayList<AbstractModelElement>();

		AbstractModelElement ame = (Diagram) getCastedModel();
		do {
			ame = ame.getParent();
		} while (!(ame instanceof BusinessObjectModel));
		BusinessObjectModel businessObjectModel = (BusinessObjectModel) ame;
		List<DiagramElement> list = ((Diagram) getCastedModel()).getElements();
		BusinessObjectModel staticBom = null;
		staticBom = BusinessModelManager.getBuildInOm();
		// 只添加node 不添加connection
		if (list != null) {
			for (DiagramElement ele : list) {
				AbstractModelElement ab = (AbstractModelElement) (businessObjectModel
						.getModelElementById((ele.getObjectId())));
				if (ab != null && !(ab instanceof Association)
						&& !(ab instanceof Inheritance)
						&& !(ab instanceof LinkAnnotation))
					eleList.add(ele);
				if (staticBom != null) {
					ab = (AbstractModelElement) (staticBom
							.getModelElementById((ele.getObjectId())));
					if (ab != null && !(ab instanceof Association)
							&& !(ab instanceof Inheritance)
							&& !(ab instanceof LinkAnnotation))
						eleList.add(ele);
				}
//				if(ab==null){
//					for(AbstractModelElement element : businessObjectModel.getReferenceObjects()){
//						if(element.getId().equals(ele.getObjectId())){
//							ab = element;
//						}
//					}
//					if(ab!=null&&!(ab instanceof Association)&&!(ab instanceof Inheritance))
//						eleList.add(ele);
//				}
			}
		}
		return eleList;// ((Diagram)getCastedModel()).getElements();
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

	private transient PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

	/**
	 * Attach a non-null PropertyChangeListener to this object.
	 * 
	 * @param propertyChangeListener
	 *            a non-null PropertyChangeListener instance
	 * @throws IllegalArgumentException
	 *             if the parameter is null
	 */
	public synchronized void addPropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener == null) {
			throw new IllegalArgumentException();
		}
		listeners.addPropertyChangeListener(propertyChangeListener);
	}

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
	 * @param property
	 *            the programmatic name of the property that changed
	 * @param oldValue
	 *            the old value of this property
	 * @param newValue
	 *            the new value of this property
	 */
	public void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (listeners.hasListeners(property)) {
			listeners.firePropertyChange(property, oldValue, newValue);
		}
	}
	public synchronized void removePropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		if (propertyChangeListener != null) {
			listeners.removePropertyChangeListener(propertyChangeListener);
		}
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		if (listener != null)
			listeners.removePropertyChangeListener(propertyName, listener);
	}

	@Override
	public Object getAdapter(Class adapter) {
		// if (adapter == SnapToHelper.class) {
		// List snapStrategies = new ArrayList();
		// Boolean val =
		// (Boolean)getViewer().getProperty(RulerProvider.PROPERTY_RULER_VISIBILITY);
		// if (val != null && val.booleanValue())
		// snapStrategies.add(new SnapToGuides(this));
		// val =
		// (Boolean)getViewer().getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED);
		// if (val != null && val.booleanValue())
		// snapStrategies.add(new SnapToGeometry(this));
		// val =
		// (Boolean)getViewer().getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
		// if (val != null && val.booleanValue())
		// snapStrategies.add(new SnapToGrid(this));
		//
		// if (snapStrategies.size() == 0)
		// return null;
		// if (snapStrategies.size() == 1)
		// return snapStrategies.get(0);
		// SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
		// for (int i = 0; i < snapStrategies.size(); i++)
		// ss[i] = (SnapToHelper)snapStrategies.get(i);
		// return new CompoundSnapToHelper(ss);
		// }

		return super.getAdapter(adapter);
	}
	public static final String PROP_LAYOUT = "LAYOUT";

	@Override
	public void repaintFigure() {
	}

}
