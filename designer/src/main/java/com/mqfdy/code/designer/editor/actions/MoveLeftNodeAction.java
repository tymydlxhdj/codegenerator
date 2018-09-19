package com.mqfdy.code.designer.editor.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveLeftNodeAction.
 *
 * @author mqfdy
 */
public class MoveLeftNodeAction extends MoveNodeAction {
	
	/** The type. */
	private int type = SWT.LEFT;

	/**
	 * Instantiates a new move left node action.
	 *
	 * @param part
	 *            the part
	 */
	public MoveLeftNodeAction(IWorkbenchPart part) {
		super(part);
		setType(type);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}
}