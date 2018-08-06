package com.mqfdy.code.designer.editor.actions;

import org.eclipse.jface.action.Action;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

public class DiagramSelectAction extends Action {
	BusinessModelDiagramEditor businessModelDiagramEditor;

	private DiagramSelectMenuCreator menuCreator;
	
	public DiagramSelectAction(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		super("com.sgcc.uap.mdd.designer.editor.actions.DiagramSelectAction", 
				ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_MODEL_TYPE_DIAGRAM_SELECT));		
		this.setText("模型图");
		this.setToolTipText("选择要展示的模型图");
		this.businessModelDiagramEditor = businessModelDiagramEditor;
		menuCreator=new DiagramSelectMenuCreator(businessModelDiagramEditor);
		setMenuCreator(menuCreator);
	}
}
