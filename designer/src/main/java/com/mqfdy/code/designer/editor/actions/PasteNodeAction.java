package com.mqfdy.code.designer.editor.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.commands.PasteBusinessClassesCommand;
import com.mqfdy.code.designer.editor.commands.PasteNodeCommand;
import com.mqfdy.code.designer.editor.commands.PasteOperationsCommand;
import com.mqfdy.code.designer.editor.commands.PastePropertiesCommand;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Property;

// TODO: Auto-generated Javadoc
/**
 * 粘贴图形.
 *
 * @author mqfdy
 */
public class PasteNodeAction extends SelectionAction {
	
	/**
	 * Instantiates a new paste node action.
	 *
	 * @param part
	 *            the part
	 */
	public PasteNodeAction(IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}

	/**
	 * 
	 */
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("粘贴");
		setDescription("粘贴");
		setId(ActionFactory.PASTE.getId());
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(false);
	}

	/**
	 * Creates the paste command.
	 *
	 * @author mqfdy
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	private Command createPasteCommand() {
		List<Object> list = new ArrayList<Object>();
		if (Clipboard.getDefault().getContents() == null)
			return null;
		list.addAll((List<Object>) Clipboard.getDefault().getContents());
		if (list.size() > 0 && list.get(0) instanceof Property)
			return new PastePropertiesCommand();
		else if (list.size() > 0 && list.get(0) instanceof BusinessOperation)
			return new PasteOperationsCommand();
		else if (list.size() > 0
				&& (list.get(0) instanceof BusinessClass || list.get(0) instanceof Enumeration))//ReferenceObject))
			return new PasteBusinessClassesCommand();
		return new PasteNodeCommand();
	}

	/**
	 * @return
	 */
	@Override
	protected boolean calculateEnabled() {

		Command command = createPasteCommand();
		if (command == null || !command.canExecute())
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		Command command = createPasteCommand();
		if (command != null && command.canExecute())
			BusinessModelUtil.getBusinessModelDiagramEditor().getCommandStacks().execute(command);
//			execute(command);
	}
}
