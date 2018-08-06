package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.PolylineConnection;

import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;

/**
 * 关联关系图形 A figure representing a connection between two node figures of a class
 * diagram. The connection is created using a PolylineConnection
 * 
 * @see org.eclipse.draw2d.PolylineConnection
 * 
 * @author mqfdy
 * 
 */

public abstract class ConnectionFigure extends PolylineConnection {

	protected NodeFigure startFigure;

	protected NodeFigure endFigure;

	protected ChopboxAnchor startAnchor;

	protected ChopboxAnchor endAnchor;
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
	public ConnectionFigure(OmConnectionEditPart connectionEditPart) {
		super();
		this.connectionEditPart = connectionEditPart;
	}
	public OmConnectionEditPart getEditPart() {
		return connectionEditPart;
	}
}
