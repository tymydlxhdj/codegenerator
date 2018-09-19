package com.mqfdy.code.designer.editor.auto.algorithms;

/*******************************************************************************
 * Copyright 2006, CHISEL Group, University of Victoria, Victoria, BC, Canada.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Chisel Group, University of Victoria
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;

// TODO: Auto-generated Javadoc
/**
 * This layout shifts overlapping nodes to the right.
 * @author Ian Bull
 */
public class HorizontalShift extends AbstractLayoutAlgorithm {

	/** The Constant DELTA. */
	private static final double DELTA = 10;
	
	/** The Constant VSPACING. */
	private static final double VSPACING = 2;

	/**
	 * Instantiates a new horizontal shift.
	 *
	 * @param styles
	 *            the styles
	 */
	public HorizontalShift(int styles) {
		super(styles);
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
	protected void applyLayoutInternal(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider, double boundsX, double boundsY, double boundsWidth, double boundsHeight) {

		ArrayList row = new ArrayList();
		for (int i = 0; i < entitiesToLayout.length; i++) {
			addToRowList(entitiesToLayout[i], row);
		}

		int heightSoFar = 0;

		Collections.sort(row, new Comparator() {

			public int compare(Object arg0, Object arg1) {
				// TODO Auto-generated method stub
				List a0 = (List) arg0;
				List a1 = (List) arg1;
				LayoutEntity node0 = ((InternalNode) a0.get(0)).getLayoutEntity();
				LayoutEntity node1 = ((InternalNode) a1.get(0)).getLayoutEntity();
				return (int) (node0.getYInLayout() - (node1.getYInLayout()));
			}

		});

		Iterator iterator = row.iterator();
		while (iterator.hasNext()) {
			List currentRow = (List) iterator.next();
			Collections.sort(currentRow, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					return (int) (((InternalNode) arg1).getLayoutEntity().getYInLayout() - ((InternalNode) arg0).getLayoutEntity().getYInLayout());
				}
			});
			Iterator iterator2 = currentRow.iterator();
			int i = 0;
			int width = (int) ((boundsWidth / 2) - currentRow.size() * 75);

			heightSoFar += ((InternalNode) currentRow.get(0)).getLayoutEntity().getHeightInLayout() + VSPACING * 8;
			while (iterator2.hasNext()) {
				InternalNode currentNode = (InternalNode) iterator2.next();

				double location = width + 10 * ++i;
				currentNode.setLocation(location, heightSoFar);
				width += currentNode.getLayoutEntity().getWidthInLayout();
			}
		}
	}

	/**
	 * Adds the to row list.
	 *
	 * @author mqfdy
	 * @param node
	 *            the node
	 * @param list
	 *            the list
	 * @Date 2018-09-03 09:00
	 */
	private void addToRowList(InternalNode node, ArrayList list) {
		double layoutY = node.getLayoutEntity().getYInLayout();

		for (int i = 0; i < list.size(); i++) {
			List currentRow = (List) list.get(i);
			InternalNode currentRowNode = (InternalNode) currentRow.get(0);
			double currentRowY = currentRowNode.getLayoutEntity().getYInLayout();
			//double currentRowHeight = currentRowNode.getLayoutEntity().getHeightInLayout();
			if (layoutY >= (currentRowY - DELTA) && layoutY <= currentRowY + DELTA) {
				currentRow.add(node);
				//list.add(i, currentRow);
				return;
			}
		}
		List newRow = new ArrayList();
		newRow.add(node);
		list.add(newRow);
	}

	/**
	 * @return
	 */
	protected int getCurrentLayoutStep() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return
	 */
	protected int getTotalNumberOfLayoutSteps() {
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
	protected boolean isValidConfiguration(boolean asynchronous, boolean continuous) {
		// TODO Auto-generated method stub
		return true;
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
	protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider) {
		// TODO Auto-generated method stub
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
	protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider, double x, double y, double width, double height) {
		// TODO Auto-generated method stub

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
	public void setLayoutArea(double x, double y, double width, double height) {
		// TODO Auto-generated method stub
	}
}
