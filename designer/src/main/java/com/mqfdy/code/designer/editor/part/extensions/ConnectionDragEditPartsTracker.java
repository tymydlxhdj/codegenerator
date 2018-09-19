package com.mqfdy.code.designer.editor.part.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.ToolUtilities;

// TODO: Auto-generated Javadoc
/**
 * 移动连线.
 *
 * @author mqfdy
 */
public class ConnectionDragEditPartsTracker extends
		org.eclipse.gef.tools.DragEditPartsTracker {

	/**
	 * Instantiates a new connection drag edit parts tracker.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 */
	public ConnectionDragEditPartsTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	/**
	 * 返回editpart，将relationEditPart加入移动List.
	 *
	 * @author mqfdy
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	protected List createOperationSet() {
		if (getCurrentViewer() != null) {
			List list = ToolUtilities
					.getSelectionWithoutDependants(getCurrentViewer());
			// ToolUtilities
			// .filterEditPartsUnderstanding(list, getTargetRequest());
			return list;
		}

		return new ArrayList();
	}

}
