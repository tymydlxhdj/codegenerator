package com.mqfdy.code.designer.editor.auto.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.DisplayIndependentPoint;
import org.eclipse.zest.layouts.dataStructures.DisplayIndependentRectangle;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;
import org.eclipse.zest.layouts.exampleStructures.SimpleRelationship;

// TODO: Auto-generated Javadoc
/**
 * This layout will take the given entities, apply a tree layout to them, and
 * then display the tree in a circular fashion with the roots in the center.
 *
 * @author mqfdy
 */
public class RadialLayoutAlgorithm extends AbstractLayoutAlgorithm {
	
	/** The Constant MAX_DEGREES. */
	private static final double MAX_DEGREES = Math.PI * 2;
	
	/** The start degree. */
	private double startDegree;
	
	/** The end degree. */
	private double endDegree;
	
	/** The tree layout. */
	private TreeLayoutAlgorithm treeLayout;
	
	/** The roots. */
	private List roots;
	
	/** The Constant DEFAULT_WEIGHT. */
	private final static double DEFAULT_WEIGHT = 0;
	
	/** The Constant DEFAULT_MARKED. */
	private final static boolean DEFAULT_MARKED = false;

	/** The Constant AS_DESTINATION. */
	private final static boolean AS_DESTINATION = false;
	
	/** The Constant AS_SOURCE. */
	private final static boolean AS_SOURCE = true;

	/** The Constant NUM_DESCENDENTS_INDEX. */
	private final static int NUM_DESCENDENTS_INDEX = 0;
	
	/** The Constant NUM_LEVELS_INDEX. */
	private final static int NUM_LEVELS_INDEX = 1;

	/** The tree roots. */
	private ArrayList treeRoots;

	/** The bounds X. */
	private double boundsX;
	
	/** The bounds Y. */
	private double boundsY;
	
	/** The bounds width. */
	private double boundsWidth;
	
	/** The bounds height. */
	private double boundsHeight;
	
	/** The layout bounds. */
	private DisplayIndependentRectangle layoutBounds = null;

	/** The parent lists. */
	private List[] parentLists;
	
	/** The children lists. */
	private List[] childrenLists;
	
	/** The weights. */
	private double[] weights;
	
	/** The marked arr. */
	private boolean[] markedArr;

	/**
	 * Creates a radial layout with no style.
	 */
	public RadialLayoutAlgorithm() {
		this(LayoutStyles.NONE);
	}

	// TODO: This is a really strange pattern. It extends tree layout and it
	/**
	 * Instantiates a new radial layout algorithm.
	 *
	 * @param styles
	 *            the styles
	 */
	// contains a tree layout ?
	public RadialLayoutAlgorithm(int styles) {
		super(styles);
		treeLayout = new TreeLayoutAlgorithm(styles);
		startDegree = 0;
		endDegree = MAX_DEGREES;
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
		throw new RuntimeException("Operation not implemented");
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
	protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double x, double y,
			double width, double height) {
		// TODO Auto-generated method stub
		layoutBounds = new DisplayIndependentRectangle(x, y, width, height);
		// super.preLayoutAlgorithm(entitiesToLayout, relationshipsToConsider,
		// x, y, width, height);
		parentLists = new List[entitiesToLayout.length];
		childrenLists = new List[entitiesToLayout.length];
		weights = new double[entitiesToLayout.length];
		markedArr = new boolean[entitiesToLayout.length];
		for (int i = 0; i < entitiesToLayout.length; i++) {
			parentLists[i] = new ArrayList();
			childrenLists[i] = new ArrayList();
			weights[i] = DEFAULT_WEIGHT;
			markedArr[i] = DEFAULT_MARKED;
		}

		this.boundsHeight = height;
		this.boundsWidth = width;
		this.boundsX = x;
		this.boundsY = y;
		layoutBounds = new DisplayIndependentRectangle(boundsX, boundsY,
				boundsWidth, boundsHeight);
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
	protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider) {
		roots = treeLayout.getRoots();
//		double xl = 10;
//		List<Double> xsl = new ArrayList<Double>();
//		for(int i = 0; i <entitiesToLayout.length;i++){
//			double x = entitiesToLayout[i].getInternalLocation().x;
//			if(!xsl.contains(x));	
//			xsl.add(x);
//		}
//		for(Double d : xsl){
//			double width = 0;
//			for(int i = 0; i <entitiesToLayout.length;i++){
//				double x = entitiesToLayout[i].getInternalLocation().x;
//				if(x == d){
//					double w = entitiesToLayout[i].getWidthInLayout();
//					width = Math.max(width, w);
//				}
//			}
//			xl = xl + width + 40;
//		}
//		layoutBounds.width = xl;
		computeRadialPositions(roots,entitiesToLayout, layoutBounds,relationshipsToConsider);

		defaultFitWithinBounds(entitiesToLayout, layoutBounds);

		// super.postLayoutAlgorithm(entitiesToLayout, relationshipsToConsider);
		updateLayoutLocations(entitiesToLayout);
		fireProgressEvent(4, 4);
	}

	/**
	 * Set the range the radial layout will use when applyLayout is called. Both
	 * values must be in radians.
	 *
	 * @author mqfdy
	 * @param startDegree
	 *            the start degree
	 * @param endDegree
	 *            the end degree
	 * @Date 2018-09-03 09:00
	 */
	public void setRangeToLayout(double startDegree, double endDegree) {
		this.startDegree = startDegree;
		this.endDegree = endDegree;
	}

	/**
	 * Take the tree and make it round. This is done by determining the location
	 * of each entity in terms of its percentage in the tree layout. Then apply
	 * that percentage to the radius and distance from the center.
	 *
	 * @author mqfdy
	 * @param roots
	 *            the roots
	 * @param entities
	 *            the entities
	 * @param bounds2
	 *            the bounds 2
	 * @param relationshipsToConsider
	 *            the relationships to consider
	 * @Date 2018-09-03 09:00
	 */
	protected void computeRadialPositions(List roots, InternalNode[] entities,
			DisplayIndependentRectangle bounds2, InternalRelationship[] relationshipsToConsider) { // TODO TODO TODO
		
		DisplayIndependentRectangle bounds = new DisplayIndependentRectangle(getLayoutBounds(entities, true));
		bounds.height = bounds2.height;
		bounds.y = bounds2.y;
		for (int i = 0; i < entities.length; i++) {
			InternalNode entity = entities[i];
			double percentTheta = (entity.getInternalX() - bounds.x) / bounds.width;
			double distance = (entity.getInternalY() - bounds.y) / bounds.height;
			double theta = startDegree + Math.abs(endDegree - startDegree) * percentTheta;
			double newX = distance * Math.cos(theta);
			double newY = distance * Math.sin(theta);
			entity.setInternalLocation(newX, newY);
		}
		
	}

	/**
	 * Find the bounds in which the nodes are located. Using the bounds against
	 * the real bounds of the screen, the nodes can proportionally be placed
	 * within the real bounds. The bounds can be determined either including the
	 * size of the nodes or not. If the size is not included, the bounds will
	 * only be guaranteed to include the center of each node.
	 *
	 * @author mqfdy
	 * @param entitiesToLayout
	 *            the entities to layout
	 * @param includeNodeSize
	 *            the include node size
	 * @return the layout bounds
	 * @Date 2018-09-03 09:00
	 */
	protected DisplayIndependentRectangle getLayoutBounds(
			InternalNode[] entitiesToLayout, boolean includeNodeSize) {
		DisplayIndependentRectangle layoutBounds = super.getLayoutBounds(
				entitiesToLayout, includeNodeSize);
		DisplayIndependentPoint centerPoint = (roots != null) ? determineCenterPoint(roots)
				: new DisplayIndependentPoint(layoutBounds.x
						+ layoutBounds.width / 2, layoutBounds.y
						+ layoutBounds.height / 2);
		// The center entity is determined in applyLayout
		double maxDistanceX = Math.max(
				Math.abs(layoutBounds.x + layoutBounds.width - centerPoint.x),
				Math.abs(centerPoint.x - layoutBounds.x));
		double maxDistanceY = Math.max(
				Math.abs(layoutBounds.y + layoutBounds.height - centerPoint.y),
				Math.abs(centerPoint.y - layoutBounds.y));
		layoutBounds = new DisplayIndependentRectangle(centerPoint.x
				- maxDistanceX, centerPoint.y - maxDistanceY, maxDistanceX * 2,
				maxDistanceY * 2);
		return layoutBounds;
	}

	/**
	 * Find the center point between the roots.
	 *
	 * @author mqfdy
	 * @param roots
	 *            the roots
	 * @return the display independent point
	 * @Date 2018-09-03 09:00
	 */
	private DisplayIndependentPoint determineCenterPoint(List roots) {
		double totalX = 0, totalY = 0;
		for (Iterator iterator = roots.iterator(); iterator.hasNext();) {
			InternalNode entity = (InternalNode) iterator.next();
			totalX += entity.getInternalX();
			totalY += entity.getInternalY();
		}
		return new DisplayIndependentPoint(totalX / roots.size(), totalY
				/ roots.size());
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
	protected void applyLayoutInternal(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double boundsX,
			double boundsY, double boundsWidth, double boundsHeight) {

		if (entitiesToLayout.length > 0) {
			int totalProgress = 4;
			fireProgressEvent(1, totalProgress);

			// List roots = new ArrayList();
			treeRoots = new ArrayList();
			buildForest(treeRoots, entitiesToLayout, relationshipsToConsider);
			fireProgressEvent(2, totalProgress);
			computePositions(treeRoots, entitiesToLayout);
			fireProgressEvent(3, totalProgress);
			defaultFitWithinBounds(entitiesToLayout, layoutBounds);
		}
	}

	/**
	 * Returns the last found roots.
	 *
	 * @author mqfdy
	 * @param entity
	 *            the entity
	 * @param objectAsSource
	 *            the object as source
	 * @param relationshipsToConsider
	 *            the relationships to consider
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
//	public List getRoots() {
//		return treeRoots;
//	}

	/**
	 * Finds all the relationships in which the node <code>obj<code>
	 * plays the specified <code>role</code>.
	 * 
	 * @param entity
	 *            The node that concerns the relations to be found.
	 * @param role
	 *            The role played by the <code>obj</code>. Its type must be of
	 *            <code>ACTOR_ROLE</code> or <code>ACTEE_ROLE</code>.
	 * @see SimpleRelationship
	 */
	private Collection findRelationships(Object entity, boolean objectAsSource,
			InternalRelationship[] relationshipsToConsider) {
		Collection foundRels = new ArrayList();
		for (int i = 0; i < relationshipsToConsider.length; i++) {
			InternalRelationship rel = relationshipsToConsider[i];
			if (objectAsSource && rel.getSource().equals(entity)) {
				foundRels.add(rel);
			} else if (!objectAsSource && rel.getDestination().equals(entity)) {
				foundRels.add(rel);
			}
		}
		return foundRels;
	}

	/**
	 * Finds the relation that has the lowest index in the relation repository
	 * in which the node <code>obj<code> plays the specified
	 * <code>role</code>.
	 *
	 * @param entity
	 *            the entity
	 * @param objectAsSource
	 *            the object as source
	 * @param relationshipsToConsider
	 *            the relationships to consider
	 * @return the internal relationship
	 * @see SimpleRelationship
	 * @see SimpleRelationship#ACTOR_ROLE
	 * @see SimpleRelationship#ACTEE_ROLE
	 */
	private InternalRelationship findRelationship(Object entity,
			boolean objectAsSource,
			InternalRelationship[] relationshipsToConsider) {
		InternalRelationship relationship = null;
		for (int i = 0; i < relationshipsToConsider.length
				&& relationship == null; i++) {
			InternalRelationship possibleRel = relationshipsToConsider[i];
			if (objectAsSource && possibleRel.getSource().equals(entity)) {
				relationship = possibleRel;
			} else if (!objectAsSource && possibleRel.getDestination().equals(entity)) {
				relationship = possibleRel;
			}
		}
		return relationship;
	}

	// ///////////////////////////////////////////////////////////////////////
	// /// Private Methods /////
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Builds the tree forest that is used to calculate positions for each node
	 * in this TreeLayoutAlgorithm.
	 *
	 * @author mqfdy
	 * @param roots
	 *            the roots
	 * @param entities
	 *            the entities
	 * @param relationships
	 *            the relationships
	 * @Date 2018-09-03 09:00
	 */
	private void buildForest(List roots, InternalNode[] entities,
			InternalRelationship[] relationships) {
		List unplacedEntities = new ArrayList(Arrays.asList(entities));
		buildForestRecursively(roots, unplacedEntities, entities, relationships);
	}

	/**
	 * Builds the forest recursively. All entities will be placed somewhere in
	 * the forest.
	 *
	 * @author mqfdy
	 * @param roots
	 *            the roots
	 * @param unplacedEntities
	 *            the unplaced entities
	 * @param entities
	 *            the entities
	 * @param relationships
	 *            the relationships
	 * @Date 2018-09-03 09:00
	 */
	private void buildForestRecursively(List roots, List unplacedEntities,
			InternalNode[] entities, InternalRelationship[] relationships) {
		if (unplacedEntities.size() == 0) {
			return; // no more entities to place
		}

		// get the first entity in the list of unplaced entities, find its root,
		// and build this root's tree
		InternalNode layoutEntity = (InternalNode) unplacedEntities.get(0);
		InternalNode rootEntity = findRootObjectRecursive(layoutEntity,
				new HashSet(), relationships);
		int rootEntityIndex = indexOfInternalNode(entities, rootEntity);
		buildTreeRecursively(rootEntity, rootEntityIndex, 0, entities,relationships);
		roots.add(rootEntity);

		// now see which nodes are left to be placed in a tree somewhere
		List unmarkedCopy = new ArrayList(unplacedEntities);
		for (Iterator iter = unmarkedCopy.iterator(); iter.hasNext();) {
			InternalNode tmpEntity = (InternalNode) iter.next();
			int tmpEntityIndex = indexOfInternalNode(entities, tmpEntity);
			boolean isMarked = markedArr[tmpEntityIndex];
			if (isMarked) {
				unplacedEntities.remove(tmpEntity);
			}
		}
		buildForestRecursively(roots, unplacedEntities, entities, relationships);
	}

	/**
	 * Finds the root node that can be treated as the root of a tree. The found
	 * root node should be one of the unmarked nodes.
	 *
	 * @author mqfdy
	 * @param currentEntity
	 *            the current entity
	 * @param seenAlready
	 *            the seen already
	 * @param relationshipsToConsider
	 *            the relationships to consider
	 * @return the internal node
	 * @Date 2018-09-03 09:00
	 */
	private InternalNode findRootObjectRecursive(InternalNode currentEntity,
			Set seenAlready, InternalRelationship[] relationshipsToConsider) {
		InternalNode rootEntity = null;
		InternalRelationship rel = findRelationship(currentEntity,
				AS_DESTINATION, relationshipsToConsider);
		if (rel == null) {
			rootEntity = currentEntity;
		} else {
			InternalNode parentEntity = rel.getSource();
			if (!seenAlready.contains(parentEntity)) {
				seenAlready.add(parentEntity);
				rootEntity = findRootObjectRecursive(parentEntity, seenAlready,
						relationshipsToConsider);
			} else {
				rootEntity = currentEntity;
			}
		}
		return rootEntity;
	}

	/**
	 * Builds a tree of the passed in entity. The entity will pass a weight
	 * value to all of its children recursively.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param weight
	 *            the weight
	 * @param entities
	 *            the entities
	 * @param relationships
	 *            the relationships
	 * @Date 2018-09-03 09:00
	 */
	private void buildTreeRecursively(InternalNode layoutEntity, int i,
			double weight, InternalNode[] entities,
			final InternalRelationship[] relationships) {
		// No need to do further computation!
		if (layoutEntity == null) {
			return;
		}

		// A marked entity means that it has been added to the
		// forest, and its weight value needs to be modified.
		if (markedArr[i]) {
			modifyWeightRecursively(layoutEntity, i, weight, new HashSet(),
					entities, relationships);
			return; // No need to do further computation.
		}

		// Mark this entity, set its weight value and create a new tree node.
		markedArr[i] = true;
		weights[i] = weight;

		// collect the children of this entity and put them in order
		Collection rels = findRelationships(layoutEntity, AS_SOURCE,
				relationships);
		List children = new ArrayList();
		for (Iterator iter = rels.iterator(); iter.hasNext();) {
			InternalRelationship layoutRel = (InternalRelationship) iter.next();
			InternalNode childEntity = layoutRel.getDestination();
			children.add(childEntity);
		}

		if (comparator != null) {
			Collections.sort(children, comparator);
		} else {
			// sort the children by level, then by number of descendents, then
			// by number of children
			// TODO: SLOW
			Collections.sort(children, new Comparator() {
				public int compare(Object o1, Object o2) {
					InternalNode node1 = (InternalNode) o1;
					InternalNode node2 = (InternalNode) o2;
					int[] numDescendentsAndLevel1 = new int[2];
					int[] numDescendentsAndLevel2 = new int[2];
					int level1 = numDescendentsAndLevel1[NUM_LEVELS_INDEX];
					int level2 = numDescendentsAndLevel2[NUM_LEVELS_INDEX];
					if (level1 == level2) {
						getNumDescendentsAndLevel(node1, relationships,
								numDescendentsAndLevel1);
						getNumDescendentsAndLevel(node2, relationships,
								numDescendentsAndLevel2);
						int numDescendents1 = numDescendentsAndLevel1[NUM_DESCENDENTS_INDEX];
						int numDescendents2 = numDescendentsAndLevel2[NUM_DESCENDENTS_INDEX];
						if (numDescendents1 == numDescendents2) {
							int numChildren1 = getNumChildren(node1,
									relationships);
							int numChildren2 = getNumChildren(node1,
									relationships);
							return numChildren2 - numChildren1;
						} else {
							return numDescendents2 - numDescendents1;
						}
					} else {
						return level2 - level1;
					}
					// return getNumChildren(node2, relationships) -
					// getNumChildren(node1, relationships);
				}
			});
		}

		// map children to this parent, and vice versa
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			InternalNode childEntity = (InternalNode) iter.next();
			int childEntityIndex = indexOfInternalNode(entities, childEntity);
			if (!childrenLists[i].contains(childEntity)) {
				childrenLists[i].add(childEntity);
			}
			if (!parentLists[childEntityIndex].contains(layoutEntity)) {
				parentLists[childEntityIndex].add(layoutEntity);
			}
		}

		for (Iterator iter = children.iterator(); iter.hasNext();) {
			InternalNode childEntity = (InternalNode) iter.next();
			int childEntityIndex = indexOfInternalNode(entities, childEntity);
			buildTreeRecursively(childEntity, childEntityIndex, weight + 1,
					entities, relationships);
		}
	}

	/**
	 * Gets the num children.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param relationships
	 *            the relationships
	 * @return the num children
	 * @Date 2018-09-03 09:00
	 */
	private int getNumChildren(InternalNode layoutEntity,
			InternalRelationship[] relationships) {
		return findRelationships(layoutEntity, AS_SOURCE, relationships).size();
	}

	/**
	 * Gets the num descendents and level.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param relationships
	 *            the relationships
	 * @param numDescendentsAndLevel
	 *            the num descendents and level
	 * @return the num descendents and level
	 * @Date 2018-09-03 09:00
	 */
	private void getNumDescendentsAndLevel(InternalNode layoutEntity,
			InternalRelationship[] relationships, int[] numDescendentsAndLevel) {
		getNumDescendentsAndLevelRecursive(layoutEntity, relationships,
				new HashSet(), numDescendentsAndLevel, 0);
	}

	/**
	 * Gets the num descendents and level recursive.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param relationships
	 *            the relationships
	 * @param seenAlready
	 *            the seen already
	 * @param numDescendentsAndLevel
	 *            the num descendents and level
	 * @param currentLevel
	 *            the current level
	 * @return the num descendents and level recursive
	 * @Date 2018-09-03 09:00
	 */
	private void getNumDescendentsAndLevelRecursive(InternalNode layoutEntity,
			InternalRelationship[] relationships, Set seenAlready,
			int[] numDescendentsAndLevel, int currentLevel) {
		if (seenAlready.contains(layoutEntity)) {
			return;
		}
		seenAlready.add(layoutEntity);
		numDescendentsAndLevel[NUM_LEVELS_INDEX] = Math.max(
				numDescendentsAndLevel[NUM_LEVELS_INDEX], currentLevel);
		Collection rels = findRelationships(layoutEntity, AS_SOURCE,
				relationships);
		for (Iterator iter = rels.iterator(); iter.hasNext();) {
			InternalRelationship layoutRel = (InternalRelationship) iter.next();
			InternalNode childEntity = layoutRel.getDestination();
			numDescendentsAndLevel[NUM_DESCENDENTS_INDEX]++;
			getNumDescendentsAndLevelRecursive(childEntity, relationships,
					seenAlready, numDescendentsAndLevel, currentLevel + 1);

		}
	}

	/**
	 * Modifies the weight value of the marked node recursively.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param weight
	 *            the weight
	 * @param descendentsSeenSoFar
	 *            the descendents seen so far
	 * @param entities
	 *            the entities
	 * @param relationships
	 *            the relationships
	 * @Date 2018-09-03 09:00
	 */
	private void modifyWeightRecursively(InternalNode layoutEntity, int i,
			double weight, Set descendentsSeenSoFar, InternalNode[] entities,
			InternalRelationship[] relationships) {
		// No need to do further computation!
		if (layoutEntity == null) {
			return;
		}

		if (descendentsSeenSoFar.contains(layoutEntity)) {
			return; // No need to do further computation.
		}

		descendentsSeenSoFar.add(layoutEntity);
		// No need to do further computation!
		if (weight < weights[i]) {
			return;
		}

		weights[i] = weight;
		Collection rels = findRelationships(layoutEntity, AS_SOURCE,
				relationships);

		for (Iterator iter = rels.iterator(); iter.hasNext();) {
			InternalRelationship tmpRel = (InternalRelationship) iter.next();
			InternalNode tmpEntity = tmpRel.getDestination();
			int tmpEntityIndex = indexOfInternalNode(entities, tmpEntity);
			modifyWeightRecursively(tmpEntity, tmpEntityIndex, weight + 1,
					descendentsSeenSoFar, entities, relationships);
		}
	}

	/**
	 * Gets the maxium weight of a tree in the forest of this
	 * TreeLayoutAlgorithm.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param seenAlready
	 *            the seen already
	 * @param entities
	 *            the entities
	 * @return the maxium weight recursive
	 * @Date 2018-09-03 09:00
	 */
	private double getMaxiumWeightRecursive(InternalNode layoutEntity, int i,
			Set seenAlready, InternalNode[] entities) {
		double result = 0;
		if (seenAlready.contains(layoutEntity)) {
			return result;
		}
		seenAlready.add(layoutEntity);
		List children = childrenLists[i];
		if (children.isEmpty()) {
			result = weights[i];
		} else {
			// TODO: SLOW
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				InternalNode childEntity = (InternalNode) iter.next();
				int childEntityIndex = indexOfInternalNode(entities,
						childEntity);
				result = Math.max(
						result,
						getMaxiumWeightRecursive(childEntity, childEntityIndex,
								seenAlready, entities));
			}
		}
		return result;
	}

	/**
	 * Computes positions for each node in this TreeLayoutAlgorithm by
	 * referencing the forest that holds those nodes.
	 *
	 * @author mqfdy
	 * @param roots
	 *            the roots
	 * @param entities
	 *            the entities
	 * @Date 2018-09-03 09:00
	 */
	private void computePositions(List roots, InternalNode[] entities) {
		// No need to do further computation!
		if (roots.size() == 0) {
			return;
		}

		int totalLeafCount = 0;
		double maxWeight = 0;
		for (int i = 0; i < roots.size(); i++) {
			InternalNode rootEntity = (InternalNode) roots.get(i);
			int rootEntityIndex = indexOfInternalNode(entities, rootEntity);
			totalLeafCount = totalLeafCount
					+ getNumberOfLeaves(rootEntity, rootEntityIndex, entities);
			maxWeight = Math.max(
					maxWeight,
					getMaxiumWeightRecursive(rootEntity, rootEntityIndex,
							new HashSet(), entities) + 1.0);
		}

		double width = 1.0 / totalLeafCount;
		double height = 1.0 / maxWeight;

		int leafCountSoFar = 0;

		// TODO: SLOW!
		for (int i = 0; i < roots.size(); i++) {
			InternalNode rootEntity = (InternalNode) roots.get(i);
			int rootEntityIndex = indexOfInternalNode(entities, rootEntity);
			computePositionRecursively(rootEntity, rootEntityIndex,
					leafCountSoFar, width, height, new HashSet(), entities);
			leafCountSoFar = leafCountSoFar
					+ getNumberOfLeaves(rootEntity, rootEntityIndex, entities);
		}
	}
	
	/**
	 * Computes positions recursively until the leaf nodes are reached.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param relativePosition
	 *            the relative position
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param seenAlready
	 *            the seen already
	 * @param entities
	 *            the entities
	 * @Date 2018-09-03 09:00
	 */
	private void computePositionRecursively(InternalNode layoutEntity, int i,
			int relativePosition, double width, double height, Set seenAlready,
			InternalNode[] entities) {
		if (seenAlready.contains(layoutEntity)) {
			return;
		}
		seenAlready.add(layoutEntity);
		double level = getLevel(layoutEntity, i, entities);
		int breadth = getNumberOfLeaves(layoutEntity, i, entities);
		double absHPosition = relativePosition + breadth / 2.0;
		double absVPosition = (level + 0.5);

		double posx = absHPosition * width;
		double posy = absVPosition * height;
		double weight = weights[i];
		posy = posy + height * (weight - level);
		
		int relativeCount = 0;
		List children = childrenLists[i];
		layoutEntity.setInternalLocation(posx, posy);
		// TODO: Slow
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			InternalNode childEntity = (InternalNode) iter.next();
			int childEntityIndex = indexOfInternalNode(entities, childEntity);
			computePositionRecursively(childEntity, childEntityIndex,
					relativePosition + relativeCount, width, height,
					seenAlready, entities);
			relativeCount = relativeCount
					+ getNumberOfLeaves(childEntity, childEntityIndex, entities);
		}
	}
	
	/**
	 * Gets the number of leaves.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param entities
	 *            the entities
	 * @return the number of leaves
	 * @Date 2018-09-03 09:00
	 */
	private int getNumberOfLeaves(InternalNode layoutEntity, int i,
			InternalNode[] entities) {
		return getNumberOfLeavesRecursive(layoutEntity, i, new HashSet(),
				entities);
	}

	/**
	 * Gets the number of leaves recursive.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param seen
	 *            the seen
	 * @param entities
	 *            the entities
	 * @return the number of leaves recursive
	 * @Date 2018-09-03 09:00
	 */
	private int getNumberOfLeavesRecursive(InternalNode layoutEntity, int i,
			Set seen, InternalNode[] entities) {
		int numLeaves = 0;
		List children = childrenLists[i];
		if (children.size() == 0) {
			numLeaves = 1;
		} else {
			// TODO: SLOW!
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				InternalNode childEntity = (InternalNode) iter.next();
				if (!seen.contains(childEntity)) {
					seen.add(childEntity);
					int childEntityIndex = indexOfInternalNode(entities,
							childEntity);
					numLeaves += getNumberOfLeavesRecursive(childEntity,
							childEntityIndex, seen, entities);
				} else {
					numLeaves = 1;
				}
			}
		}
		return numLeaves;
	}

	/**
	 * Gets the level.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param entities
	 *            the entities
	 * @return the level
	 * @Date 2018-09-03 09:00
	 */
	private int getLevel(InternalNode layoutEntity, int i,
			InternalNode[] entities) {
		return getLevelRecursive(layoutEntity, i, new HashSet(), entities);
	}

	/**
	 * Gets the level recursive.
	 *
	 * @author mqfdy
	 * @param layoutEntity
	 *            the layout entity
	 * @param i
	 *            the i
	 * @param seen
	 *            the seen
	 * @param entities
	 *            the entities
	 * @return the level recursive
	 * @Date 2018-09-03 09:00
	 */
	private int getLevelRecursive(InternalNode layoutEntity, int i, Set seen,
			InternalNode[] entities) {
		if (seen.contains(layoutEntity)) {
			return 0;
		}
		seen.add(layoutEntity);
		List parents = parentLists[i];
		int maxParentLevel = 0;
		for (Iterator iter = parents.iterator(); iter.hasNext();) {
			InternalNode parentEntity = (InternalNode) iter.next();
			int parentEntityIndex = indexOfInternalNode(entities, parentEntity);
			int parentLevel = getLevelRecursive(parentEntity,
					parentEntityIndex, seen, entities) + 1;
			maxParentLevel = Math.max(maxParentLevel, parentLevel);
		}
		return maxParentLevel;
	}

	/**
	 * Note: Use this as little as possible! TODO limit the use of this method.
	 *
	 * @author mqfdy
	 * @param nodes
	 *            the nodes
	 * @param nodeToFind
	 *            the node to find
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	private int indexOfInternalNode(InternalNode[] nodes,
			InternalNode nodeToFind) {
		for (int i = 0; i < nodes.length; i++) {
			InternalNode node = nodes[i];
			if (node.equals(nodeToFind)) {
				return i;
			}
		}
		throw new RuntimeException("Couldn't find index of internal node: "
				+ nodeToFind);
	}

	/**
	 * @return
	 */
	protected int getCurrentLayoutStep() {
		return 0;
	}

	/**
	 * @return
	 */
	protected int getTotalNumberOfLayoutSteps() {
		return 4;
	}

	/**
	 * Checks if is valid configuration.
	 *
	 * @author mqfdy
	 * @param asynchronous
	 *            the asynchronous
	 * @param continueous
	 *            the continueous
	 * @return true, if is valid configuration
	 * @Date 2018-09-03 09:00
	 */
	protected boolean isValidConfiguration(boolean asynchronous,
			boolean continueous) {
		if (asynchronous && continueous) {
			return false;
		} else if (asynchronous && !continueous) {
			return true;
		} else if (!asynchronous && continueous) {
			return false;
		} else if (!asynchronous && !continueous) {
			return true;
		}

		return false;
	}
}
