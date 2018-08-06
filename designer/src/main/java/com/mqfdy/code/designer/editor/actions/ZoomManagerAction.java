package com.mqfdy.code.designer.editor.actions;

import org.eclipse.gef.internal.InternalImages;
import org.eclipse.jface.action.Action;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;

@SuppressWarnings("restriction")
public class ZoomManagerAction extends Action {
	BusinessModelDiagramEditor businessModelDiagramEditor;

	private ZoomSelectMenuCreator menuCreator;
	
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
