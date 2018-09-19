package com.mqfdy.code.designer.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.commands.AutoLayoutCommand;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoLayoutAction.
 *
 * @author mqfdy
 */
public class AutoLayoutAction extends SelectionAction {
	
	/** The part. */
	private BusinessModelDiagramEditor part;

	/**
	 * Instantiates a new auto layout action.
	 *
	 * @param part
	 *            the part
	 */
	public AutoLayoutAction(IWorkbenchPart part) {
		super(part);
		this.part = (BusinessModelDiagramEditor) part;
		setLazyEnablementCalculation(true);
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
	 * Creates the auto layout command.
	 *
	 * @author mqfdy
	 * @param diae
	 *            the diae
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	private Command createAutoLayoutCommand(
			DiagramEditPart diae) {
		if (diae == null) {
			return null;
		}
		AutoLayoutCommand cmd = new AutoLayoutCommand(diae);
		return cmd;
	}

	/**
	 * @return
	 */
	@Override
	protected boolean calculateEnabled() {
//		Command cmd = createCopyCommand(part.getViewer().getContents().getChildren());
//		if (cmd == null)
//			return false;
//		return cmd.canExecute();
		return part.getViewer() != null && part.getViewer().getContents() != null
				&& part.getViewer().getContents().getChildren().size()>0;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		Command cmd = createAutoLayoutCommand((DiagramEditPart) part.getViewer().getContents());
		if (cmd != null && cmd.canExecute()) {
			part.getCommandStacks().execute(cmd);
//			cmd.execute();
		}
	}
}