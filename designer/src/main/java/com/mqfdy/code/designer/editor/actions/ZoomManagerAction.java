package com.mqfdy.code.designer.editor.actions;

import org.eclipse.gef.internal.InternalImages;
import org.eclipse.jface.action.Action;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;

// TODO: Auto-generated Javadoc
/**
 * The Class ZoomManagerAction.
 *
 * @author mqfdy
 */
@SuppressWarnings("restriction")
public class ZoomManagerAction extends Action {
	
	/** The business model diagram editor. */
	BusinessModelDiagramEditor businessModelDiagramEditor;

	/** The menu creator. */
	private ZoomSelectMenuCreator menuCreator;
	
	/**
	 * Instantiates a new zoom manager action.
	 *
	 * @param businessModelDiagramEditor
	 *            the business model diagram editor
	 */
	public ZoomManagerAction(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		super("com.sgcc.uap.mdd.designer.editor.actions.ZoomManagerAction", 
				InternalImages.DESC_ZOOM_IN);		
		this.setText("比例");
		this.setToolTipText("选择模型图比例");  
		this.businessModelDiagramEditor = businessModelDiagramEditor;  
		menuCreator=new ZoomSelectMenuCreator(businessModelDiagramEditor);
		setMenuCreator(menuCreator);
	}
}
