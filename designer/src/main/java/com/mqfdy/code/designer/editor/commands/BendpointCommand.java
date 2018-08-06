/**
 * Copied from GEF Logic example.
 */
package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;

import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;

public class BendpointCommand extends Command {

	protected int index;
	protected OmConnectionEditPart connection;
	protected Dimension d1, d2;

	public void setConnection(OmConnectionEditPart connection) {
		this.connection = connection;
	}

	public void redo() {
		execute();
	}

	public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
		d1 = dim1;
		d2 = dim2;
	}

	public void setIndex(int i) {
		index = i;
	}

}
