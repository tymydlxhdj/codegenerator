package com.mqfdy.code.designer.views.valiresult;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.BusinessClassRelationEditDialog;
import com.mqfdy.code.designer.dialogs.EnumEditDialog;
import com.mqfdy.code.designer.dialogs.OperationEditorDialog;
import com.mqfdy.code.designer.dialogs.PropertyEditorDialog;
import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.graph.Diagram;

public class FindObjectResultPage extends Composite {

	private TableViewer tableViewer;
	private Table table;
	private List<AbstractModelElement> result;
	private String filePath;
	
	public FindObjectResultPage(Composite parent, int style) {
		super(parent, style);
		createContents(this);
	}

	private void createContents(Composite composite) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.heightHint = 150;
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		tableViewer = new TableViewer(composite, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);

		tableViewer.setLabelProvider(new FindObjectLabelProvider());
		tableViewer.setContentProvider(new FindObjectConentProvider());

		String[] columnNames = new String[] { "类型", "名称", "显示名称", "位置" };

		int[] columnWidths = new int[] { 100, 120, 180, 280 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		// 表格双击事件
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = tableViewer.getSelection();
				if(BusinessModelUtil.getEditorBusinessModelManager() == null || 
						!BusinessModelUtil.getEditorBusinessModelManager().getPath().equals(filePath)){
					MessageDialog dlg = new MessageDialog(getShell(), "提示", null, 
							"当前结果对应的OM未激活，是否激活该OM模型？", 1, new String[]{"确定","取消"}, 0);
					int isOK = dlg.open();
					if(isOK != TitleAreaDialog.OK)
						return;
					IPath path = new Path(filePath);
					IResource resource = ResourcesPlugin.getWorkspace().getRoot()
							.findFilesForLocation(path)[0];
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					for(Object editor : page.getEditors()){
						if(editor instanceof BusinessModelEditor)
							if(((BusinessModelEditor) editor).getBuEditor().getBusinessModelManager().getPath().equals(filePath)){
								page.activate((BusinessModelEditor)editor);
							}
					}
//					try {
//						IDE.openEditor(page, (IFile) resource,BusinessModelEditor.ID, true);
//					} catch (PartInitException e) {
//						// TODO Auto-generated catch block
////						e.printStackTrace();
//					}
				}
				AbstractModelElement element = (AbstractModelElement) ((IStructuredSelection) selection)
						.getFirstElement();
				if (element instanceof BusinessObjectModel
						|| element instanceof ModelPackage
						|| element instanceof Diagram) {
					Tree tree = BusinessModelUtil.getOutlinePage()
							.getMrViewer().getTreeViewer().getTree();
					TreeItem[] items = tree.getItems();
					for (int i = 0; i < items.length; i++) {
						selectTreeItem(items[i], element);
					}
				}
				if (element instanceof BusinessClass) {
					BusinessClass businessClass = (BusinessClass) element;
					final BusinessClassEditorDialog dialog = new BusinessClassEditorDialog(
							getShell(), businessClass, businessClass
									.getBelongPackage());
					if (dialog.open() == IDialogConstants.OK_ID) {
						BusinessModelEvent event1 = new BusinessModelEvent(
								BusinessModelEvent.MODEL_ELEMENT_UPDATE, dialog
										.getEditingElement());
						BusinessModelUtil.getEditorBusinessModelManager()
								.businessObjectModelChanged(event1);
					}
				}
				if (element instanceof Association) {
					Association ass = (Association) element;
					final BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
							getShell(), ass, ass.getBelongPackage(),
							BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
					if (dialog.open() == IDialogConstants.OK_ID) {
					}
				}
				if (element instanceof Enumeration) {
					Enumeration enumeration = (Enumeration) element;
					final EnumEditDialog dialog = new EnumEditDialog(
							getShell(), enumeration.getBelongPackage(),
							enumeration);
					if (dialog.open() == IDialogConstants.OK_ID) {
						// BusinessModelEvent event1 = new
						// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_UPDATE,dialog.getEditingElement());
						// BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event1);
					}
				}
				if (element instanceof Property) {
					Property pro = (Property) element;
					BusinessClass bc = (BusinessClass) (pro.getParent());
					for (Property p : bc.getProperties()) {
						if (p.getId().equals(pro.getId()))
							pro = p;
					}
					element = pro;
					PropertyEditorDialog dialog = new PropertyEditorDialog(
							getShell(), pro, bc);
					int returnKey = dialog.open();
					if (returnKey == Window.OK
							|| (returnKey == Window.CANCEL && dialog
									.isChanged())) {
						bc.getProperties().remove(pro);
						int index = result.indexOf(pro);
						result.set(index, (Property) dialog.getEditingElement());
						bc.addProperty((Property) dialog.getEditingElement());
						BusinessModelEvent event1 = new BusinessModelEvent(
								BusinessModelEvent.MODEL_ELEMENT_UPDATE, bc);
						BusinessModelUtil.getEditorBusinessModelManager()
								.businessObjectModelChanged(event1);
					}

				}
				if (element instanceof BusinessOperation) {
					BusinessOperation oper = (BusinessOperation) element;
					BusinessClass bc = (BusinessClass) (oper
							.getBelongBusinessClass());
					for (BusinessOperation p : bc.getOperations()) {
						if (p.getId().equals(oper.getId()))
							oper = p;
					}
					element = oper;
					OperationEditorDialog dialog = new OperationEditorDialog(
							getShell(), oper, bc);
					int returnKey = dialog.open();
					if (returnKey == Window.OK
							|| (returnKey == Window.CANCEL && dialog
									.isChanged())) {
						// bc.getProperties().remove(oper);
						// bc.addProperty((Property)dialog.getEditingElement());
						int index = result.indexOf(oper);
						result.set(index, dialog.getEditingElement());
						BusinessModelEvent event1 = new BusinessModelEvent(
								BusinessModelEvent.MODEL_ELEMENT_UPDATE, bc);
						BusinessModelUtil.getEditorBusinessModelManager()
								.businessObjectModelChanged(event1);
					}
				}
			}

//			int level = 0;

			private void selectTreeItem(TreeItem treeItem,
					AbstractModelElement abstractModelElement) {
				int j = 0;
				if (abstractModelElement == treeItem.getData()) {
					BusinessModelUtil.getOutlinePage().getMrViewer()
							.forceFocus();
					if (!(abstractModelElement instanceof BusinessObjectModel))
						BusinessModelUtil
								.getOutlinePage()
								.getMrViewer()
								.getTreeViewer()
								.expandToLevel(abstractModelElement,
										AbstractTreeViewer.ALL_LEVELS);
					BusinessModelUtil.getOutlinePage().getMrViewer()
							.getTreeViewer().getTree().setSelection(treeItem);
					j++;
				}
				for (int i = 0; i < treeItem.getItems().length; i++) {
					if (j < 1
							&& abstractModelElement == treeItem.getItems()[i]
									.getData()) {
						BusinessModelUtil.getOutlinePage().getMrViewer()
								.forceFocus();
						if (!(abstractModelElement instanceof BusinessObjectModel))
							BusinessModelUtil
									.getOutlinePage()
									.getMrViewer()
									.getTreeViewer()
									.expandToLevel(abstractModelElement,
											AbstractTreeViewer.ALL_LEVELS);
						BusinessModelUtil.getOutlinePage().getMrViewer()
								.getTreeViewer().getTree()
								.setSelection(treeItem.getItems()[i]);
						j++;
					}
					if (j < 1) {
						selectTreeItem(treeItem.getItems()[i],
								abstractModelElement);
					}
				}
			}
		});
	}

	public void setData(List<AbstractModelElement> result) {
		this.result = result;
		tableViewer.setInput(result);
		tableViewer.refresh();
	}

	public void refresh() {
		tableViewer.setInput(result);
		tableViewer.refresh();
	}

	public List<AbstractModelElement> getResult() {
		return result;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
