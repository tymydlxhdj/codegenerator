package com.mqfdy.code.designer.editor.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveRightNodeAction.
 *
 * @author mqfdy
 */
public class MoveRightNodeAction extends MoveNodeAction {
	
	/** The type. */
	private int type = SWT.RIGHT;

	/**
	 * Instantiates a new move right node action.
	 *
	 * @param part
	 *            the part
	 */
	public MoveRightNodeAction(IWorkbenchPart part) {
		super(part);
		setType(type);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}

}