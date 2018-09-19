package com.mqfdy.code.designer.editor.part.extensions;

// TODO: Auto-generated Javadoc
/**
 * The Class NodeModelElement.
 *
 * @author mqfdy
 */

public abstract class NodeModelElement {

	/** PropertyId used to fire an event when a child is added to this diagram. */
	public static final String CHILD_ADDED_PROP = "NodeModelElement.ChildAdded";

	/**
	 * PropertyId used to fire an event when a child is removed from this
	 * diagram.
	 */
	public static final String CHILD_REMOVED_PROP = "NodeModelElement.ChildRemoved";

	/**
	 * PropertyId used to fire an event when the location of this node is
	 * modified.
	 */
	public static final String LOCATION_PROP = "Node.Location";

	/**
	 * PropertyId for the X property value (used for by the corresponding
	 * property descriptor). Should be displayed.
	 * <p>
	 * Value: String
	 */
	// private static final String XPOS_PROP = "xpos";

	/**
	 * PropertyId for the Y property value (used for by the corresponding
	 * property descriptor). Should be displayed.
	 * <p>
	 * Value: String
	 */
	// private static final String YPOS_PROP = "ypos";

	/**
	 * PropertyId used to fire an event when the length of this node is
	 * modified.
	 */
	public static final String SIZE_PROP = "size";

	/**
	 * PropertyId for the Height property value (used for by the corresponding
	 * property descriptor). Should be displayed.
	 * <p>
	 * Value: String
	 */
	// private static final String HEIGHT_PROP = "height";

	/**
	 * PropertyId for the Width property value (used for by the corresponding
	 * property descriptor). Should be displayed.
	 * <p>
	 * Value: String
	 */
	// private static final String WIDTH_PROP = "width";

	/**
	 * Property ID used to fire an event when the list of outgoing connections
	 * is modified.
	 */
	public static final String SOURCE_CONNECTIONS_PROP = "Node.SourceConnections";

	/**
	 * Property ID used to fire an event when the list of incoming connections
	 * is modified.
	 */
	public static final String TARGET_CONNECTIONS_PROP = "Node.TargetConnections";

}
