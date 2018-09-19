package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.actions.pages.AutoLayoutDiaolg;
import com.mqfdy.code.designer.editor.auto.algorithms.GXTreeLayoutAlgorithm;
import com.mqfdy.code.designer.editor.auto.algorithms.GraphOm;
import com.mqfdy.code.designer.editor.auto.algorithms.MyVerticalShift;
import com.mqfdy.code.designer.editor.auto.algorithms.MyXShift;
import com.mqfdy.code.designer.editor.auto.algorithms.RadialLayoutAlgorithm;
import com.mqfdy.code.designer.editor.auto.algorithms.ShiftDiagramLayoutAlgorithm;
import com.mqfdy.code.designer.editor.figure.anchor.BorderAnchor;
import com.mqfdy.code.designer.editor.figure.anchor.RectangleBorderAnchor;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

// TODO: Auto-generated Javadoc
/**
 * 布局图形.
 *
 * @author mqfdy
 */
public class DiagramAutoLayoutCommand extends Command {
	
	/** The parts. */
	// 图形EditPart
	List<AbstractGraphicalEditPart> parts = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
	
	/** The visited parts. */
	List<AbstractGraphicalEditPart> visitedParts = new ArrayList<AbstractGraphicalEditPart>();
	
	/** The diae. */
	DiagramEditPart diae;
	
	/** The style. */
	private int style;
	
	/** The dia. */
	private Diagram dia;
	
	/** The no conn node list. */
	private ArrayList<AbstractGraphicalEditPart> noConnNodeList;
	
	/** The conn edit list. */
	private ArrayList<OmConnectionEditPart> connEditList = new ArrayList<OmConnectionEditPart>();
	
	/** The y. */
	private int y = 0;
	
	/**
	 * Instantiates a new diagram auto layout command.
	 *
	 * @param diae
	 *            the diae
	 * @param style
	 *            the style
	 */
	public DiagramAutoLayoutCommand(DiagramEditPart diae,int style) {
		this.style = style;
		parts = diae.getChildren();
		this.diae = diae;
		dia = ((Diagram)diae.getModel()).clone();
	}

	/**
	 * @return
	 */
	@Override
	public boolean canExecute() {
		if (parts == null || parts.isEmpty())
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		if (canExecute()) {
			redo();
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean canUndo() {
		return true;
	}
	
	/**
	 * Gets the node.
	 *
	 * @author mqfdy
	 * @param node
	 *            the node
	 * @param graph
	 *            the graph
	 * @return the node
	 * @Date 2018-09-03 09:00
	 */
	public static GraphNode getNode(Object node, Graph graph) {
		for (Object gnode : graph.getNodes()) {
			if (node == ((GraphNode) gnode).getData())
				return (GraphNode) gnode;
		}
		return null;
	}
	
	/**
	 * Gets the graphs.
	 *
	 * @author mqfdy
	 * @param roots
	 *            the roots
	 * @param abList
	 *            the ab list
	 * @param connEditList2
	 *            the conn edit list 2
	 * @return the graphs
	 * @Date 2018-09-03 09:00
	 */
	private ArrayList<NodeEditPart>[] getGraphs(List roots, List<NodeEditPart> abList,ArrayList<OmConnectionEditPart> connEditList2){
		if (roots.size() == 0) {
			return null;
		}
		List<NodeEditPart> seenNodes = new ArrayList<NodeEditPart>();
		ArrayList<NodeEditPart>[] graphs = new ArrayList[roots.size()];
		for (int i = 0; i < roots.size(); i++) {
			NodeEditPart rootEntity = (NodeEditPart) roots.get(i);
			if(!seenNodes.contains(rootEntity)){
				graphs[i] = new ArrayList<NodeEditPart>();
				getGraphsRecursively(rootEntity, seenNodes, abList,graphs[i],connEditList2);
			}
		}
		return graphs;
	}
	
	/**
	 * Gets the graphs recursively.
	 *
	 * @author mqfdy
	 * @param rootEntity
	 *            the root entity
	 * @param seenNodes
	 *            the seen nodes
	 * @param abList
	 *            the ab list
	 * @param graphs
	 *            the graphs
	 * @param connEditList2
	 *            the conn edit list 2
	 * @return the graphs recursively
	 * @Date 2018-09-03 09:00
	 */
	private void getGraphsRecursively(NodeEditPart rootEntity,
			List<NodeEditPart> seenNodes, List<NodeEditPart> abList, List<NodeEditPart> graphs, ArrayList<OmConnectionEditPart> connEditList2) {
		if(!graphs.contains(rootEntity))
				graphs.add(rootEntity);
		for(OmConnectionEditPart rel : connEditList2){
			if(!graphs.contains(rel.getTarget()) && rootEntity.equals(rel.getSource())){
				graphs.add((NodeEditPart) rel.getTarget());
				getGraphsRecursively((NodeEditPart) rel.getTarget(), seenNodes, abList, graphs, connEditList2);
			}else if(!graphs.contains(rel.getSource()) && rootEntity.equals(rel.getTarget())){
				graphs.add((NodeEditPart) rel.getSource());
				getGraphsRecursively((NodeEditPart) rel.getSource(), seenNodes, abList, graphs, connEditList2);
			}
		}
		seenNodes.add(rootEntity);
//		for(NodeEditPart ab : graphs){
//			if(!seenNodes.contains(ab)){
//				getGraphsRecursively(ab, seenNodes, abList,graphs,connEditList2);
//				seenNodes.add(ab);
//			}
//		}
		
	}
	
	/**
	 * 
	 */
	@Override
	public void redo() {
		parts = BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getContents().getChildren();
//		List<AbstractGraphicalEditPart> allParts = new ArrayList<AbstractGraphicalEditPart>(parts);
		visitedParts = new ArrayList<AbstractGraphicalEditPart>();
		Shell shell = BusinessModelEditorPlugin.getActiveWorkbenchWindow()
				.getShell();
		noConnNodeList = new ArrayList<AbstractGraphicalEditPart>(parts);
		List<NodeEditPart> abList = new ArrayList<NodeEditPart>();
		List<NodeEditPart> roots = new ArrayList<NodeEditPart>();
		for (Object ab : parts) {
			if (ab instanceof NodeEditPart && (((NodeEditPart) ab).getTargetConnections().size() == 0 && ((NodeEditPart) ab).getSourceConnections().size()>0)) {
//				roots.add((NodeEditPart)ab);
			}
			if (ab instanceof NodeEditPart && (((NodeEditPart) ab).getSourceConnections().size()>0 || ((NodeEditPart) ab).getTargetConnections().size()>0)) {
				roots.add((NodeEditPart)ab);
				abList.add((NodeEditPart) ab);
				noConnNodeList.remove(ab);
				for (Object conn : ((NodeEditPart) ab).getSourceConnections()) {
					if (conn instanceof OmConnectionEditPart) {
						if(!connEditList.contains(conn))
							connEditList.add((OmConnectionEditPart) conn);
					}
				}
			}
		}
		
		ArrayList<NodeEditPart>[] graphs = getGraphs(roots, abList, connEditList);
		if(graphs != null && graphs.length > 0){
			GraphOm[] omGraphs = new GraphOm[graphs.length];
			Diagram diagram = null;
			for(int j = 0;j < graphs.length;j++){
				if(graphs[j] != null && graphs[j].size() > 0){
					omGraphs[j] = new GraphOm(shell, SWT.NONE);
					omGraphs[j].setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
					
					ArrayList<NodeEditPart> l = graphs[j];
					for(NodeEditPart ab : l){
						GraphNode node1 = new GraphNode(omGraphs[j],ZestStyles.NODES_FISHEYE, ab);
						node1.setSize(
								((NodeEditPart) ab).getFigure().getBounds().width,
								((NodeEditPart) ab).getFigure().getBounds().height);
					}
					for(NodeEditPart ab : l){
						for (Object conn : ab.getSourceConnections()) {
							if (conn instanceof OmConnectionEditPart) {
//								if(!connEditList.contains(conn))
//									connEditList.add((ConnectionEditPart) conn);
								new GraphConnection(omGraphs[j], SWT.NONE, getNode(
										((OmConnectionEditPart) conn).getSource(), omGraphs[j]),
										getNode(((OmConnectionEditPart) conn).getTarget(),
												omGraphs[j]));
								//清空point 起始点置零
								if(diagram == null)
									diagram = (Diagram) ((OmConnectionEditPart) conn).getSource().getParent().getModel();
								resetConnection(conn,diagram);
							}
						}
					}
					
				}
			}
			setLayoutAlgorithm(omGraphs);
			layoutGraph(omGraphs);
		}
		layoutNoConnNode();
	}
	
	/**
	 * 设置布局方式.
	 *
	 * @author mqfdy
	 * @param omGraphs
	 *            the new layout algorithm
	 * @Date 2018-09-03 09:00
	 */
	private void setLayoutAlgorithm(GraphOm[] omGraphs) {
		int layoutStyle = LayoutStyles.NO_LAYOUT_NODE_RESIZING;
		switch (style) {
		case 1:
			for(int i = 0;i < omGraphs.length;i++){
				if(omGraphs[i] != null){
					omGraphs[i].setLayoutAlgorithm(new CompositeLayoutAlgorithm(
							layoutStyle, new LayoutAlgorithm[] {
									new RadialLayoutAlgorithm(layoutStyle),//放射状
									new MyVerticalShift(layoutStyle),
//									new MyHorizontalShift(layoutStyle),
									new MyXShift(layoutStyle),
									new ShiftDiagramLayoutAlgorithm(layoutStyle) 
									}), true);
					omGraphs[i].applyLayoutInternal();
				}
			}
			break;
		case 0:
			for(int i = 0;i < omGraphs.length;i++){
				if(omGraphs[i] != null){
					omGraphs[i].setLayoutAlgorithm(new CompositeLayoutAlgorithm(
							layoutStyle, new LayoutAlgorithm[] {
									new GXTreeLayoutAlgorithm(layoutStyle),
//									new VHShift(layoutStyle),
//									new VerticalShift(layoutStyle),
//									new HorizontalShift(layoutStyle),
									new ShiftDiagramLayoutAlgorithm(layoutStyle) 
									}), true);
					omGraphs[i].applyLayoutInternal();
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Layout graph.
	 *
	 * @author mqfdy
	 * @param omGraphs
	 *            the om graphs
	 * @Date 2018-09-03 09:00
	 */
	private void layoutGraph(GraphOm[] omGraphs) {
		int x = 0;
		for(int i = 0;i < omGraphs.length;i++){
			if(omGraphs[i] != null){
		//		omGraphs.applyLayoutInternal();
				int l = x;
				for (Object node : omGraphs[i].getNodes()) {
					if (node instanceof GraphNode) {
						if (((GraphNode) node).getData() instanceof NodeEditPart) {
							NodeEditPart e = (NodeEditPart) ((GraphNode) node)
									.getData();
							int ph = ((GraphNode) node).getSize().height;
							int px = ((GraphNode) node).getLocation().x;
							int py = ((GraphNode) node).getLocation().y;
							if(py + ph > y)
								y = py + ph;
							e.getFigure().setLocation(((GraphNode) node).getLocation());
							((DiagramElement) e.getModel()).getStyle().setPositionX(px + l);
							((DiagramElement) e.getModel()).getStyle().setPositionY(py);
							x = Math.max(x, px + l + ((DiagramElement) e.getModel()).getStyle().getWidth());
							e.refresh();
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		parts = BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getContents().getChildren();
		for(Object node: parts){
			if (node instanceof NodeEditPart) {
				NodeEditPart e = (NodeEditPart)node;
				ElementStyle style = dia.getElementById(((DiagramElement) e.getModel()).getObjectId()).getStyle();
				e.getFigure().setLocation(new Point(style.getPositionX(),style.getPositionY()));
				((DiagramElement) e.getModel()).getStyle().setPositionX(style.getPositionX());
				((DiagramElement) e.getModel()).getStyle().setPositionY(style.getPositionY());
				e.refresh();
			}
		}
		Diagram dia1 = null;
		for(OmConnectionEditPart connEditPart : connEditList){
			if (connEditPart instanceof OmConnectionEditPart) {
				OmConnectionEditPart e = (OmConnectionEditPart)connEditPart;
				ElementStyle style = dia.getElementById(((AbstractModelElement) e.getModel()).getId()).getStyle();
				if(dia1 == null)
					dia1 = (Diagram) connEditPart.getSource().getParent().getModel();
				DiagramElement ele = dia1.getElementById(((AbstractModelElement) connEditPart.getModel()).getId());
				ele.getStyle().setPositionX(style.getPositionX());
				ele.getStyle().setPositionY(style.getPositionY());
				ele.getStyle().setEndPositionX(style.getEndPositionX());
				ele.getStyle().setEndPositionY(style.getEndPositionY());
				ele.getStyle().setPointList(style.getPointList());
				e.setBendpoints(style.getPointList());
				e.firePropertyChange(OmConnectionEditPart.PROP_BENDPOINT, null, null);
				e.refresh();
			}
		}
	}
	
	/**
	 * 布局没有关联关系的节点.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void layoutNoConnNode() {
		int x = 10;
		int num = 0;
		int maxHeight = 0;
		y = y + 20;
		//布局没有关联关系的节点
		for(AbstractGraphicalEditPart ab : noConnNodeList){
			if(ab instanceof NodeEditPart){
				if(maxHeight < ab.getFigure().getBounds().height)
					maxHeight = ab.getFigure().getBounds().height;
				if(num > 4){
					y = y + 20 + maxHeight;
					maxHeight = 0;
					x = 10;
					num = 0;
				}
				((DiagramElement) ab.getModel()).getStyle().setPositionX(x);
				((DiagramElement) ab.getModel()).getStyle().setPositionY(y);
				num ++;
				x = x + 10 + ab.getFigure().getBounds().width;
			}
			ab.refresh();
		}
	}
	
	/**
	 * 将连线的位置及节点信息置零.
	 *
	 * @author mqfdy
	 * @param conn
	 *            the conn
	 * @param diagram
	 *            the diagram
	 * @Date 2018-09-03 09:00
	 */
	private void resetConnection(Object conn, Diagram diagram) {
		if(((OmConnectionEditPart) conn).getModel() instanceof Association){
			Association as = (Association) ((OmConnectionEditPart) conn).getModel();
			if(as.getClassA() == as.getClassB())
				return;
		}
		DiagramElement ele = diagram.getElementById(((AbstractModelElement) ((OmConnectionEditPart) conn).getModel()).getId());
		ele.getStyle().setPositionX(0);
		ele.getStyle().setPositionY(0);
		ele.getStyle().setEndPositionX(0);
		ele.getStyle().setEndPositionY(0);
		ele.getStyle().setPointList("");
		OmConnectionEditPart connEditPart = (OmConnectionEditPart) conn;
		connEditPart.getBendpoints().clear();
		BorderAnchor anchor = new RectangleBorderAnchor(
				((GraphicalEditPart) connEditPart.getSource()).getFigure());
		anchor.setAngle(Double.MAX_VALUE);
		((OmConnectionEditPart) conn).setSourceAnchor(anchor);
		((OmConnectionEditPart) conn).setTargetAnchor(anchor);
		connEditPart.firePropertyChange(OmConnectionEditPart.PROP_BENDPOINT, null, null);
		connEditPart.getTarget().refresh();
		connEditPart.getSource().refresh();
		connEditPart.refresh();
	}
	
}