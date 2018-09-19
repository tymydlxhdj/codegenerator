package com.mqfdy.code.designer.editor.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveDownNodeAction.
 *
 * @author mqfdy
 */
public class MoveDownNodeAction extends MoveNodeAction {
	
	/** The type. */
	private int type = SWT.DOWN;

	/**
	 * Instantiates a new move down node action.
	 *
	 * @param part
	 *            the part
	 */
	public MoveDownNodeAction(IWorkbenchPart part) {
		super(part);
		setType(type);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}
}