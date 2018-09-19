package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.dialogs.PatternFilter;

import com.mqfdy.code.designer.dialogs.widget.ModelFilterDialog;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.views.modelresource.page.ObjectModelOutlinePage;
import com.mqfdy.code.designer.views.modelresource.tree.FilteredTree;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceView;
import com.mqfdy.code.designer.views.modelresource.tree.RepositoryModelView;

// TODO: Auto-generated Javadoc
/**
 * 过滤树动作.
 *
 * @author mqfdy
 */
public class ModelFilterAction extends Action {

	/** 对应动作操作的树显示器. */
	private TreeViewer treeViewer;
	
	/** The object model outline page. */
	private ObjectModelOutlinePage objectModelOutlinePage;

	/**
	 * Instantiates a new model filter action.
	 *
	 * @param text
	 *            the text
	 * @param objectModelOutlinePage
	 *            the object model outline page
	 */
	public ModelFilterAction(String text,
			ObjectModelOutlinePage objectModelOutlinePage) {
		super(text);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_FILTER));
		this.treeViewer = objectModelOutlinePage.getMrViewer().getTreeViewer();
		this.objectModelOutlinePage = objectModelOutlinePage;
	}

	/**
	 * 
	 */
	public void run() {
		ModelFilterDialog dialog = new ModelFilterDialog(null);
		int returnKey = dialog.open();
		if (returnKey == Window.OK) {
			Object viewer = objectModelOutlinePage.getCurrentViewer();
			FilteredTree filteredTree = null;
			if(viewer instanceof ModelResourceView)
				filteredTree = (FilteredTree) ((ModelResourceView) viewer).getFilterTree();
			if(viewer instanceof RepositoryModelView)
				filteredTree = (FilteredTree) ((RepositoryModelView) viewer).getFilterTree();
			if(filteredTree!=null){
				filteredTree.textChanged(true);
			}
		}

	}
}
