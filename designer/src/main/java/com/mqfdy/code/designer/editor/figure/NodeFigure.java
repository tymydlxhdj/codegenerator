/* Sotower - Sotower is a UML Plugin for Eclipse
 *
 * Copyright (C) 2005-2006 Sotower-Group, sotower-dev@lists.binaervarianz.de
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;

// TODO: Auto-generated Javadoc
/**
 * 图形 Base figure for all nodes in a diagram, e.g. classes, interfaces, objects,
 * packages or comments.
 * 
 * @author mqfdy
 * 
 */

public abstract class NodeFigure extends Figure {

	/**
	 * Constructor for a new NodeFigure. Calls constructor of Figure and sets
	 * border to a line border of 1 pixel that surrenders the node.
	 * 
	 */
	public NodeFigure() {
		super();
		setBorder(new LineBorder());
	}

	/**
	 * Create a new empty NodeFigure. Does nothing.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public abstract void emptyFigure();

}
