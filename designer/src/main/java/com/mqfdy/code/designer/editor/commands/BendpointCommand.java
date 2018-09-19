/**
 * Copied from GEF Logic example.
 */
package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;

import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;

// TODO: Auto-generated Javadoc
/**
 * The Class BendpointCommand.
 *
 * @author mqfdy
 */
public class BendpointCommand extends Command {

	/** The index. */
	protected int index;
	
	/** The connection. */
	protected OmConnectionEditPart connection;
	
	/** The d 2. */
	protected Dimension d1, d2;

	/**
	 * Sets the connection.
	 *
	 * @author mqfdy
	 * @param connection
	 *            the new connection
	 * @Date 2018-09-03 09:00
	 */
	public void setConnection(OmConnectionEditPart connection) {
		this.connection = connection;
	}

	/**
	 * 
	 */
	public void redo() {
		execute();
	}

	/**
	 * Sets the relative dimensions.
	 *
	 * @author mqfdy
	 * @param dim1
	 *            the dim 1
	 * @param dim2
	 *            the dim 2
	 * @Date 2018-09-03 09:00
	 */
	public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
		d1 = dim1;
		d2 = dim2;
	}

	/**
	 * Sets the index.
	 *
	 * @author mqfdy
	 * @param i
	 *            the new index
	 * @Date 2018-09-03 09:00
	 */
	public void setIndex(int i) {
		index = i;
	}

}
