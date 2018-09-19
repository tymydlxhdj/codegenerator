package com.mqfdy.code.designer.editor.actions;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class DiagramAutoLayoutAction.
 *
 * @author mqfdy
 */
@SuppressWarnings("restriction")
public class DiagramAutoLayoutAction extends SelectionAction {
	
	/** The part. */
	BusinessModelDiagramEditor part;

	/** The menu creator. */
	private LayoutAlgorithmSelectMenuCreator menuCreator;
	
	/**
	 * Instantiates a new diagram auto layout action.
	 *
	 * @param part
	 *            the part
	 */
	public DiagramAutoLayoutAction(IWorkbenchPart part) {
		super(part);
		this.part = (BusinessModelDiagramEditor) part;
		setLazyEnablementCalculation(true);
		menuCreator=new LayoutAlgorithmSelectMenuCreator(this.part);
		setMenuCreator(menuCreator);
	}

	/**
	 * 
	 */
	@Override
	protected void init() {
		super.init();
		setText("自动布局");
		setDescription("自动布局");
		setId(DiagramEditPart.PROP_LAYOUT);
		setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_AUYOLAYOUT));
		setDisabledImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_AUYOLAYOUT_DISABLE));
		setEnabled(false);
	}

	/**
	 * @return
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
//		part.getViewer() != null && part.getViewer().getContents() != null
//				&& part.getViewer().getContents().getChildren().size()>0;
	}

	
}
