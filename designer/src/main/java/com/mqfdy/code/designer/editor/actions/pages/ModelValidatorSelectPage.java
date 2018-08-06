package com.mqfdy.code.designer.editor.actions.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
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
import org.eclipse.swt.widgets.Tree;

import com.mqfdy.code.resource.validator.Validator;
import com.mqfdy.code.resource.validator.ValidatorRules;


/**
 * 校验项选择
 * 
 * @author mqfdy
 * 
 */
public class ModelValidatorSelectPage extends Composite {
	private CheckboxTreeViewer treeViewer;
	private List<Validator> vaList = new ArrayList<Validator>();

	public ModelValidatorSelectPage(Composite parent, int style) {
		super(parent, style);
		createContents(this);
	}

	private void createContents(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		treeViewer = new CheckboxTreeViewer(parent, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.CHECK);
		Tree tree = treeViewer.getTree();
		tree.setLayout(new GridLayout(1, true));
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setInput(Validator.getRules());
		treeViewer.expandAll();
		treeViewer.setAllChecked(true);
		Object[] obj = treeViewer.getCheckedElements();
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof Validator)
				vaList.add((Validator) obj[i]);
		}
		addListeners();
	}

	private void addListeners() {
		ICheckStateListener checkStateListener = new ICheckStateListener() {
			public void checkStateChanged(
					CheckStateChangedEvent checkstatechangedevent) {
				if (checkstatechangedevent.getChecked()) {
					treeViewer.setSubtreeChecked(
							checkstatechangedevent.getElement(), true); // 设置子节点也被选中
					if (checkstatechangedevent.getElement() instanceof Validator) {
						vaList.remove((Validator) checkstatechangedevent
								.getElement());
						((Validator) checkstatechangedevent.getElement())
								.setSelectionValid(true);
						vaList.add((Validator) checkstatechangedevent
								.getElement());
						for (ValidatorRules rule : ((Validator) checkstatechangedevent
								.getElement()).getValidateRules()) {
							rule.setSelectionValid(true);
						}
					}
					if (checkstatechangedevent.getElement() instanceof ValidatorRules) {
						vaList.remove(((ValidatorRules) checkstatechangedevent
								.getElement()).getValidator());
						((ValidatorRules) checkstatechangedevent.getElement())
								.setSelectionValid(true);
						vaList.add(((ValidatorRules) checkstatechangedevent
								.getElement()).getValidator());
						treeViewer.setChecked(
								((ValidatorRules) checkstatechangedevent
										.getElement()).getValidator(), true);
					}
				} else {
					treeViewer.setSubtreeChecked(
							checkstatechangedevent.getElement(), false); // 设置子节点不选中
					if (checkstatechangedevent.getElement() instanceof Validator) {
						vaList.remove((Validator) checkstatechangedevent
								.getElement());
						((Validator) checkstatechangedevent.getElement())
								.setSelectionValid(false);
						// vaList.add((Validator)
						// checkstatechangedevent.getElement());
						for (ValidatorRules rule : ((Validator) checkstatechangedevent
								.getElement()).getValidateRules()) {
							rule.setSelectionValid(false);
						}
					}
					if (checkstatechangedevent.getElement() instanceof ValidatorRules) {
						vaList.remove(((ValidatorRules) checkstatechangedevent
								.getElement()).getValidator());
						((ValidatorRules) checkstatechangedevent.getElement())
								.setSelectionValid(false);
						vaList.add(((ValidatorRules) checkstatechangedevent
								.getElement()).getValidator());
					}
				}

			}
		};

		treeViewer.addCheckStateListener(checkStateListener);
	}

	/**
	 * ContentProvider
	 * 
	 * @author admin
	 * 
	 */
	static class TreeContentProvider implements ITreeContentProvider {
		Validator rule;

		public Object[] getChildren(Object element) {
			// 返回树的下一级节点
			if (element instanceof Validator) {
				rule = (Validator) element;
				List<ValidatorRules> list = rule.getValidateRules();
				Object[] rules = new Object[list.size()];
				for (int i = 0; i < list.size(); i++)
					rules[i] = list.get(i);
				return rules;
			}

			return new Object[] {};
		}

		public Object getParent(Object element) {

			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof Validator) {
				if (((Validator) element).getValidateRules().size() > 0)
					return true;
				return false;
			}
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
	 * @author admin
	 * 
	 */
	class TreeLabelProvider implements ILabelProvider {

		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			if (element instanceof ValidatorRules) {
				return ((ValidatorRules) element).getName();
			}
			if (element instanceof Validator) {
				return ((Validator) element).getName();
			}
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

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	public List<Validator> getSelectedValisators() {
		return vaList;
	}
}