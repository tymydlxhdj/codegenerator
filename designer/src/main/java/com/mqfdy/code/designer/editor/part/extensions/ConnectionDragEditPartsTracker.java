package com.mqfdy.code.designer.editor.part.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.ToolUtilities;

/**
 * 移动连线
 */
public class ConnectionDragEditPartsTracker extends
		org.eclipse.gef.tools.DragEditPartsTracker {

	public ConnectionDragEditPartsTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	/**
	 * 返回editpart，将relationEditPart加入移动List
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
