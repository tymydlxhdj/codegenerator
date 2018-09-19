package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.PolylineConnection;

import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;

// TODO: Auto-generated Javadoc
/**
 * 关联关系图形 A figure representing a connection between two node figures of a class
 * diagram. The connection is created using a PolylineConnection
 *
 * @author mqfdy
 * @see org.eclipse.draw2d.PolylineConnection
 */

public abstract class ConnectionFigure extends PolylineConnection {

	/** The start figure. */
	protected NodeFigure startFigure;

	/** The end figure. */
	protected NodeFigure endFigure;

	/** The start anchor. */
	protected ChopboxAnchor startAnchor;

	/** The end anchor. */
	protected ChopboxAnchor endAnchor;
	
	/** The connection edit part. */
	private OmConnectionEditPart connectionEditPart;

	/**
	 * This is the constructor of a new ConnectionFigure. No argument is needed.
	 * The constructor creates a new PolylineConnection and that connection is
	 * added to the main figure.
	 * 
	 */
	public ConnectionFigure() {
		super();
	}
	
	/**
	 * Instantiates a new connection figure.
	 *
	 * @param connectionEditPart
	 *            the connection edit part
	 */
	public ConnectionFigure(OmConnectionEditPart connectionEditPart) {
		super();
		this.connectionEditPart = connectionEditPart;
	}
	
	/**
	 * Gets the edits the part.
	 *
	 * @author mqfdy
	 * @return the edits the part
	 * @Date 2018-09-03 09:00
	 */
	public OmConnectionEditPart getEditPart() {
		return connectionEditPart;
	}
}
