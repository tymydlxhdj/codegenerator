package com.mqfdy.code.designer.editor.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveUpNodeAction.
 *
 * @author mqfdy
 */
public class MoveUpNodeAction extends MoveNodeAction {
	
	/** The type. */
	private int type = SWT.UP;

	/**
	 * Instantiates a new move up node action.
	 *
	 * @param part
	 *            the part
	 */
	public MoveUpNodeAction(IWorkbenchPart part) {
		super(part);
		setType(type);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}
}