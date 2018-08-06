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
/**
 * 
 * @author mqfdy
 *
 */
public class ReconstructionDialog extends TitleAreaDialog {
	BusinessClass oldBusinessClass;
	BusinessClass newBusinessClass;

	public ReconstructionDialog(Shell parentShell,
			BusinessClass oldBusinessClass, BusinessClass newBusinessClass) {
		super(parentShell);
		this.oldBusinessClass = oldBusinessClass;
		this.newBusinessClass = newBusinessClass;
	}

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
	 * 操作按钮
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.BACK_ID,
				IDialogConstants.BACK_LABEL, true);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

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
	 * ContentProvider
	 * 
	 * @author mqfdy
	 * 
	 */
	static class TreeContentProvider implements ITreeContentProvider {
		OperationInfo rule;

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

		public Object getParent(Object element) {

			return null;
		}

		public boolean hasChildren(Object element) {
			// if(element instanceof Validator){
			// if(((Validator)element).getValidateRules().size()>0)
			// return true;
			// return false;
			// }
			return false;
		}

		public Object[] getElements(Object element) {
			// 打印出树的输入信息，通常用户可以通过输入信息构建树
			if (element instanceof List) {
				if (element != null)
					return ((List) element).toArray();
			}
			return new Object[] {};
		}

		public void dispose() {
			// 销毁
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * LabelProvider
	 * 
	 * @author mqfdy
	 * 
	 */
	class TreeLabelProvider implements ILabelProvider {

		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			if (element instanceof OperationInfo) {
				return ((OperationInfo) element).getOperationTarget();
			}
			// if(element instanceof Validator){
			// return ((Validator)element).getName();
			// }
			return "";
		}

		public void addListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub

		}

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public boolean isLabelProperty(Object arg0, String arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub

		}

	}
}
