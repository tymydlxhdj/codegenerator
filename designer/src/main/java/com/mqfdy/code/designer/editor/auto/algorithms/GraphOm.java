package com.mqfdy.code.designer.editor.auto.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.draw2d.Animation;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.InvalidLayoutConfiguration;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import com.mqfdy.code.designer.editor.utils.Logger;

//布局管理器名称	描述
//CompositeLayoutAlgorithm	 组合其他布局方法来设置图形显示
//DirectedGraphLayoutAlgorithm	 以全部重叠的方式来设置图形显示
//GridLayoutAlgorithm	 以网格的布局方式来设置图形显示
//HorizontalLayoutAlgorithm	 以水平方式来设置图形显示
//HorizontalShift	 以重叠的方式依次向右水平来设置图形显示
//HorizontalTreeLayoutAlgorithm	 以水平树状方式来设置图形显示
//RadialLayoutAlgorithm	 以放射状的布局来设置图形显示
//SpringLayoutAlgorithm	 以相同关联长度，图形节点重叠最少来设置图形显示
//TreeLayoutAlgorithm	 以垂直树状方式来设置图形显示
//VerticalLayoutAlgorithm	 以垂直方式来设置图形的显示
public class GraphOm extends Graph {
	private int nodeStyle;
	LayoutAlgorithm layoutAlgorithm = null;
//	private Dimension preferredSize = null;

	public GraphOm(Composite parent, int style) {
		super(parent, style);
	}

	public void applyLayoutInternal() {

		if ((this.getNodes().size() == 0)) {
			return;
		}

		int layoutStyle = 0;

		if ((nodeStyle & ZestStyles.NODES_NO_LAYOUT_RESIZE) > 0) {
			layoutStyle = LayoutStyles.NO_LAYOUT_NODE_RESIZING;
		}

		if (layoutAlgorithm == null) {
			layoutAlgorithm = new TreeLayoutAlgorithm(layoutStyle);
		}

		layoutAlgorithm.setStyle(layoutAlgorithm.getStyle() | layoutStyle);

		// calculate the size for the layout algorithm
		// Dimension d = this.getViewport().getSize();
		Dimension d = new Dimension(100, 100);
		d.width = d.width - 10;
		d.height = d.height - 10;

		// if (this.preferredSize.width >= 0) {
		// d.width = preferredSize.width;
		// }
		// if (this.preferredSize.height >= 0) {
		// d.height = preferredSize.height;
		// }

		if (d.isEmpty()) {
			return;
		}
		try {
			Method m = Graph.class.getDeclaredMethod("getConnectionsToLayout",new Class[] { List.class });
			//m.setAccessible(true);
			LayoutRelationship[] connectionsToLayout = (LayoutRelationship[]) m.invoke(this, getNodes());
			Method m1 = Graph.class.getDeclaredMethod("getNodesToLayout",new Class[] { List.class });
			//m1.setAccessible(true);
			LayoutEntity[] nodesToLayout = (LayoutEntity[]) m1.invoke(this,getNodes());
			Animation.markBegin();
			layoutAlgorithm.applyLayout(nodesToLayout, connectionsToLayout, 0,
					0, d.width, d.height, false, false);
			Animation.run(10);//ANIMATION_TIME);延迟
			getLightweightSystem().getUpdateManager().performUpdate();
		} catch (SecurityException e) {
			Logger.log(e.getMessage());
		} catch (NoSuchMethodException e) {
			Logger.log(e.getMessage());
		} catch (IllegalArgumentException e) {
			Logger.log(e.getMessage());
		} catch (IllegalAccessException e) {
			Logger.log(e.getMessage());
		} catch (InvocationTargetException e) {
			Logger.log(e.getMessage());
		} catch (InvalidLayoutConfiguration e) {
			Logger.log(e.getMessage());
		}
	}
	@Override
	public void setLayoutAlgorithm(LayoutAlgorithm algorithm,
			boolean applyLayout) {
		layoutAlgorithm = algorithm;
		super.setLayoutAlgorithm(algorithm, applyLayout);
	}
}
