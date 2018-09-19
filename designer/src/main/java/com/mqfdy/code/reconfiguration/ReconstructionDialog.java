package com.mqfdy.code.reconfiguration;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceContentProvider;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceLabelProvider;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceTreeSorter;
import com.mqfdy.code.model.BusinessClass;
// TODO: Auto-generated Javadoc

/**
 * The Class ReconstructionDialog.
 *
 * @author mqfdy
 */
public class ReconstructionDialog extends TitleAreaDialog {
	
	/** The old business class. */
	BusinessClass oldBusinessClass;
	
	/** The new business class. */
	BusinessClass newBusinessClass;

	/**
	 * Instantiates a new reconstruction dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param oldBusinessClass
	 *            the old business class
	 * @param newBusinessClass
	 *            the new business class
	 */
	public ReconstructionDialog(Shell parentShell,
			BusinessClass oldBusinessClass, BusinessClass newBusinessClass) {
		super(parentShell);
		this.oldBusinessClass = oldBusinessClass;
		this.newBusinessClass = newBusinessClass;
	}

	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("代码重构");
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM);
		newShell.setImage(icon);
	}

	/**
	 * Creates the dialog area.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the control
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		setMessage("选择重构的类型");
		setTitle("选择重构的类型");
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		createTree(composite);
		createTree1(composite);
		return composite;
	}

	/**
	 * Creates the tree.
	 *
	 * @author mqfdy
	 * @param treeComposite
	 *            the tree composite
	 * @Date 2018-09-03 09:00
	 */
	private void createTree(Composite treeComposite) {
		TreeViewer treeViewer = new TreeViewer(treeComposite, SWT.NONE
				| SWT.V_SCROLL);
		Tree tree = treeViewer.getTree();
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setContentProvider(new TreeContentProvider());
		// treeViewer.setSorter(new ModelResourceTreeSorter());
		treeViewer.setInput(OperationInfo.getOps());
		treeViewer.expandToLevel(3);
		treeViewer.refresh();
	}

	/**
	 * Creates the tree 1.
	 *
	 * @author mqfdy
	 * @param treeComposite
	 *            the tree composite
	 * @Date 2018-09-03 09:00
	 */
	private void createTree1(Composite treeComposite) {
		TreeViewer treeViewer = new TreeViewer(treeComposite, SWT.CHECK
				| SWT.V_SCROLL);
		Tree tree = treeViewer.getTree();
		treeViewer.setLabelProvider(new ModelResourceLabelProvider());
		treeViewer.setContentProvider(new ModelResourceContentProvider());
		treeViewer.setSorter(new ModelResourceTreeSorter());
		// treeViewer.setInput(Validator.getRules());
		treeViewer.expandToLevel(3);
		treeViewer.refresh();
	}

	// @Override
	// protected Control createButtonBar(Composite parent) {
	// createButton(parent, IDialogConstants.BACK_ID,
	// IDialogConstants.BACK_LABEL, true);
	// createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
	// true);
	// createButton(parent, IDialogConstants.CANCEL_ID,
	// IDialogConstants.CANCEL_LABEL, false);
	// return parent;
	// }
	/**
	 * 操作按钮.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.BACK_ID,
				IDialogConstants.BACK_LABEL, true);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Button pressed.
	 *
	 * @author mqfdy
	 * @param buttonId
	 *            the button id
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.BACK_ID == buttonId) {
			super.buttonPressed(IDialogConstants.CANCEL_ID);
		} else if (IDialogConstants.OK_ID == buttonId) {

			IPath path = new Path(BusinessModelUtil
					.getEditorBusinessModelManager().getPath());
			IResource res = ResourcesPlugin.getWorkspace().getRoot()
					.findFilesForLocation(path)[0];

			OperationInfoAnalyzer opa = new OperationInfoAnalyzer(
					oldBusinessClass, newBusinessClass);
			opa.analyse();
			String projcetPath = res.getProject().getLocation().toOSString();
			AffectedAnalyzer aly = new AffectedAnalyzer(opa.getInfos(),
					projcetPath, BusinessModelUtil
							.getEditorBusinessModelManager()
							.getBusinessObjectModel());
			try {
				aly.reconstruct();
			} catch (IOException e) {
				Logger.log(e);
			}
			super.buttonPressed(IDialogConstants.OK_ID);
		} else {
			super.buttonPressed(buttonId);
		}
	}

	/**
	 * ContentProvider.
	 *
	 * @author mqfdy
	 */
	static class TreeContentProvider implements ITreeContentProvider {
		
		/** The rule. */
		OperationInfo rule;

		/**
		 * Gets the children.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return the children
		 * @Date 2018-09-03 09:00
		 */
		public Object[] getChildren(Object element) {
			// 返回树的下一级节点
			// if(element instanceof OperationInfo){
			// rule = (OperationInfo)element;
			// List<OperationInfo> list = new AffectedAnalyzer(rule,
			// projectPath, bom);
			// Object[] rules = new Object[list.size()];
			// for(int i=0;i<list.size();i++)
			// rules[i] = list.get(i);
			// return rules;
			// }

			return new Object[] {};
		}

		/**
		 * Gets the parent.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return the parent
		 * @Date 2018-09-03 09:00
		 */
		public Object getParent(Object element) {

			return null;
		}

		/**
		 * Checks for children.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		public boolean hasChildren(Object element) {
			// if(element instanceof Validator){
			// if(((Validator)element).getValidateRules().size()>0)
			// return true;
			// return false;
			// }
			return false;
		}

		/**
		 * Gets the elements.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return the elements
		 * @Date 2018-09-03 09:00
		 */
		public Object[] getElements(Object element) {
			// 打印出树的输入信息，通常用户可以通过输入信息构建树
			if (element instanceof List) {
				if (element != null)
					return ((List) element).toArray();
			}
			return new Object[] {};
		}

		/**
		 * 
		 */
		public void dispose() {
			// 销毁
		}

		/**
		 * Input changed.
		 *
		 * @author mqfdy
		 * @param viewer
		 *            the viewer
		 * @param oldInput
		 *            the old input
		 * @param newInput
		 *            the new input
		 * @Date 2018-09-03 09:00
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * LabelProvider.
	 *
	 * @author mqfdy
	 */
	class TreeLabelProvider implements ILabelProvider {

		/**
		 * Gets the image.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return the image
		 * @Date 2018-09-03 09:00
		 */
		public Image getImage(Object element) {
			return null;
		}

		/**
		 * Gets the text.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return the text
		 * @Date 2018-09-03 09:00
		 */
		public String getText(Object element) {
			if (element instanceof OperationInfo) {
				return ((OperationInfo) element).getOperationTarget();
			}
			// if(element instanceof Validator){
			// return ((Validator)element).getName();
			// }
			return "";
		}

		/**
		 * Adds the listener.
		 *
		 * @author mqfdy
		 * @param arg0
		 *            the arg 0
		 * @Date 2018-09-03 09:00
		 */
		public void addListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub

		}

		/**
		 * 
		 */
		public void dispose() {
			// TODO Auto-generated method stub

		}

		/**
		 * Checks if is label property.
		 *
		 * @author mqfdy
		 * @param arg0
		 *            the arg 0
		 * @param arg1
		 *            the arg 1
		 * @return true, if is label property
		 * @Date 2018-09-03 09:00
		 */
		public boolean isLabelProperty(Object arg0, String arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		/**
		 * Removes the listener.
		 *
		 * @author mqfdy
		 * @param arg0
		 *            the arg 0
		 * @Date 2018-09-03 09:00
		 */
		public void removeListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub

		}

	}
}
