package com.mqfdy.code.designer.editor.auto.algorithms;

import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;
// TODO: Auto-generated Javadoc

/**
 * 去除布局后的边界.
 *
 * @author mqfdy
 */
public class ShiftDiagramLayoutAlgorithm extends AbstractLayoutAlgorithm {

	/** The delta X. */
	private double deltaX;
	
	/** The delta Y. */
	private double deltaY;
	
	/**
	 * Instantiates a new shift diagram layout algorithm.
	 *
	 * @param styles
	 *            the styles
	 */
	public ShiftDiagramLayoutAlgorithm(int styles) {
		super(styles);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sets the layout area.
	 *
	 * @author mqfdy
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void setLayoutArea(double x, double y, double width, double height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Apply layout internal.
	 *
	 * @author mqfdy
	 * @param entitiesToLayout
	 *            the entities to layout
	 * @param relationshipsToConsider
	 *            the relationships to consider
	 * @param boundsX
	 *            the bounds X
	 * @param boundsY
	 *            the bounds Y
	 * @param boundsWidth
	 *            the bounds width
	 * @param boundsHeight
	 *            the bounds height
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void applyLayoutInternal(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double boundsX,
			double boundsY, double boundsWidth, double boundsHeight) {
		 for (InternalNode entity : entitiesToLayout) {
		        entity.setLocation(  entity.getCurrentX() + deltaX,  entity.getCurrentY() + deltaY);
		    }
		
	}

	/**
	 * Pre layout algorithm.
	 *
	 * @author mqfdy
	 * @param entitiesToLayout
	 *            the entities to layout
	 * @param relationshipsToConsider
	 *            the relationships to consider
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double x, double y,
			double width, double height) {
		double minX = Double.MAX_VALUE;
	    double minY = Double.MAX_VALUE;
	    for (InternalNode entity : entitiesToLayout) {
	        minX = Math.min(minX, entity.getCurrentX());
	        minY = Math.min(minY, entity.getCurrentY());
	    }
	    deltaX = 10 - minX;
	    deltaY = 10 - minY;

		
	}

	/**
	 * Post layout algorithm.
	 *
	 * @author mqfdy
	 * @param entitiesToLayout
	 *            the entities to layout
	 * @param relationshipsToConsider
	 *            the relationships to consider
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return
	 */
	@Override
	protected int getTotalNumberOfLayoutSteps() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return
	 */
	@Override
	protected int getCurrentLayoutStep() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Checks if is valid configuration.
	 *
	 * @author mqfdy
	 * @param asynchronous
	 *            the asynchronous
	 * @param continuous
	 *            the continuous
	 * @return true, if is valid configuration
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected boolean isValidConfiguration( boolean asynchronous, boolean continuous)
	{
	    return true;
	} 
}
