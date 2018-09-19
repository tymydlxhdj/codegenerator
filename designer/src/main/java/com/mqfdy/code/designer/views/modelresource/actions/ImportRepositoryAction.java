package com.mqfdy.code.designer.views.modelresource.actions;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TabFolder;

import com.mqfdy.code.designer.dialogs.widget.OmFileSelecteDialog;
import com.mqfdy.code.designer.editor.listeners.TreeResourceChangeListener;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.modelresource.tree.RepositoryModelView;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.utils.ModelUtil;
import com.mqfdy.code.resource.BomManager;

// TODO: Auto-generated Javadoc
/**
 * 导入知识库动作.
 *
 * @author mqfdy
 */
public class ImportRepositoryAction extends Action {

	/** 对应动作操作的树显示器. */
	private TreeViewer treeViewer;
	
	/** The repository model view. */
	private RepositoryModelView repositoryModelView;

	/**
	 * Instantiates a new import repository action.
	 *
	 * @param text
	 *            the text
	 * @param repositoryModelView
	 *            the repository model view
	 */
	public ImportRepositoryAction(String text,
			RepositoryModelView repositoryModelView) {
		super(text);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_OPENREPOS));
		this.treeViewer = repositoryModelView.getTreeViewer();
		this.repositoryModelView = repositoryModelView;
	}

	/**
	 * Instantiates a new import repository action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param treeViewer
	 *            the tree viewer
	 */
	public ImportRepositoryAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor);
		this.treeViewer = treeViewer;
	}

	/**
	 * 
	 */
	public void run() {
		OmFileSelecteDialog dialog = new OmFileSelecteDialog();
		int returnKey = dialog.open();
		if (returnKey == Window.OK) {
			try {
				List<IFile> omFiles = dialog.getOmFiles();
				
				if(omFiles!=null && omFiles.size()>0){
					for(IFile omFile : omFiles){
						String path = omFile.getFullPath().toString();
						BusinessObjectModel bom = BomManager.xml2Model(omFile
								.getContents());
						BusinessModelUtil.assembReferenceObject(bom,omFile.getLocation().toOSString());
						ModelUtil.transformModelStereotype(bom,
								IModelElement.STEREOTYPE_REFERENCE);
						bom.getExtendAttributies().put(IModelElement.REFMODELPATH, path);
						BusinessModelUtil.getBusinessModelDiagramEditor().addListener(
								new TreeResourceChangeListener(path, BusinessModelUtil
										.getOutlinePage()));
						BusinessModelUtil.getEditorBusinessModelManager()
								.addRepsitoryModel(bom);
						BusinessModelEvent event = new BusinessModelEvent(
								BusinessModelEvent.REPOSITORY_MODEL_ADD, bom);
						BusinessModelUtil.getEditorBusinessModelManager()
								.businessObjectModelChanged(event);
						if(repositoryModelView.getParent() instanceof TabFolder)
							((TabFolder)repositoryModelView.getParent()).setSelection(1);
					}
				}
			} catch (IOException e) {
				Logger.log(e);
			} catch (DocumentException e) {
				Logger.log(e);
			} catch (CoreException e) {
				Logger.log(e);
			}

		}

	}
}
