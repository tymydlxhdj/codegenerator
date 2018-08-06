package com.mqfdy.code.designer.editor.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

public class MoveLeftNodeAction extends MoveNodeAction {
	private int type = SWT.LEFT;

	public MoveLeftNodeAction(IWorkbenchPart part) {
		super(part);
		setType(type);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}
}