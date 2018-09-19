package com.mqfdy.code.designer.editor.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.commands.CopyNodeCommand;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.shareModel.dialogs.FindModelDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class FindModelAction.
 *
 * @author mqfdy
 */
public class FindModelAction extends SelectionAction {
	
	/** The img reg. */
	private static ImageRegistry imgReg=null;
	
	/** The Constant FINDID. */
	public static final String FINDID="FIND";

	/**
	 * Instantiates a new find model action.
	 *
	 * @param part
	 *            the part
	 */
	public FindModelAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
	}
	
	/**
	 * Gets the img registry.
	 *
	 * @author mqfdy
	 * @return the img registry
	 * @Date 2018-09-03 09:00
	 */
	private static ImageRegistry getImgRegistry(){
		if(imgReg==null){
			imgReg=new ImageRegistry();
		}
		return imgReg;
	}
	
	/**
	 * 
	 */
	@Override
	protected void init() {
		super.init();
		
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("下载");
		setDescription("下载");
		setId(FINDID);
		setHoverImageDescriptor(ImageManager.getInstance().getImageDescriptor(ImageKeys.IMG_RESOURCE_FIND));
		setImageDescriptor(ImageManager.getInstance().getImageDescriptor(ImageKeys.IMG_RESOURCE_FIND));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
		setEnabled(true);
	}
	
	/**
	 * @return
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		FindModelDialog dialog=new FindModelDialog(new Shell());
		if(dialog.open()==dialog.OK){
//			EditorOperation.refreshProperties((ISelection) BusinessModelUtil.getBusinessModelDiagramEditor()
//					.getViewer().getSelectedEditParts());
		}

	}
	
}
