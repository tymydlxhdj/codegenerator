package com.mqfdy.code.designer.editor.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

public class MoveDownNodeAction extends MoveNodeAction {
	private int type = SWT.DOWN;

	public MoveDownNodeAction(IWorkbenchPart part) {
		super(part);
		setType(type);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}
}