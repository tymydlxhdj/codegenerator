package com.mqfdy.code.designer.editor.actions;

import org.eclipse.jface.action.Action;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class DiagramSelectAction.
 *
 * @author mqfdy
 */
public class DiagramSelectAction extends Action {
	
	/** The business model diagram editor. */
	BusinessModelDiagramEditor businessModelDiagramEditor;

	/** The menu creator. */
	private DiagramSelectMenuCreator menuCreator;
	
	/**
	 * Instantiates a new diagram select action.
	 *
	 * @param businessModelDiagramEditor
	 *            the business model diagram editor
	 */
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
