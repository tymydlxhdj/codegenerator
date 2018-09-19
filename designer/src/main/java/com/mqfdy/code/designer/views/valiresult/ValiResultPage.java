package com.mqfdy.code.designer.views.valiresult;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.BusinessClassRelationEditDialog;
import com.mqfdy.code.designer.dialogs.EnumEditDialog;
import com.mqfdy.code.designer.dialogs.OperationEditorDialog;
import com.mqfdy.code.designer.dialogs.PropertyEditorDialog;
import com.mqfdy.code.designer.dialogs.actions.TableViewerAuthActionGroup;
import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.resource.validator.ValiResult;

// TODO: Auto-generated Javadoc
/**
 * The Class ValiResultPage.
 *
 * @author mqfdy
 */
public class ValiResultPage extends Composite {

	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The table. */
	private Table table;
	
	/** The vali result. */
	private List<ValiResult> valiResult;
	
	/** The file path. */
	private String filePath;
	
	/** The action group. */
	private TableViewerAuthActionGroup actionGroup;
	
	/** The menu. */
	private MenuManager menu;
	
	/** The delete action. */
	private Action deleteAction;
	
	/** The delete all action. */
	private Action deleteAllAction;
	
	/**
	 * Instantiates a new vali result page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public ValiResultPage(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
		createContents(this);
	}

	/**
	 * Creates the contents.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
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

		tableViewer.setLabelProvider(new ValiResultLabelProvider());
		tableViewer.setContentProvider(new ValiResultConentProvider());

		String[] columnNames = new String[] { "级别", "类型", "校验结果", "校验对象", "位置" };

		int[] columnWidths = new int[] { 60, 100, 120, 180, 280 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT,
				SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		
		deleteAction = new Action("删除",
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), "警告",
							"请选择要删除的对象");
				} else {
					TableItem[] items = tableViewer.getTable().getSelection();
					if(items!=null && items.length>0){
						for(int i=0;i<items.length;i++){
							tableViewer.getTable().remove(i);
						}
					}
				}
			}
		};
		
		deleteAllAction = new Action("删除全部",
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), "警告",
							"请选择要删除的对象");
				} else {
					TableItem[] items = tableViewer.getTable().getSelection();
					if(items!=null && items.length>0){
							tableViewer.getTable().removeAll();
					}
				}
			}
		};
		
		actionGroup = new TableViewerAuthActionGroup(tableViewer, 1, null);
		menu = new MenuManager();
		//actionGroup.fillContextMenu(menu, deleteAction);
		//actionGroup.fillContextMenu(menu, deleteAllAction);

		tableViewer.getTable().addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent event) {
				resetMenu();
			}

			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
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
//						IDE.openEditor(page, (IFile) resource, true);
//					} catch (PartInitException e) {
//						// TODO Auto-generated catch block
////						e.printStackTrace();
//					}

				}
				
				
				ValiResult element = (ValiResult) ((IStructuredSelection) selection)
						.getFirstElement();
				if (element.getEle() instanceof BusinessObjectModel
						|| element.getEle() instanceof ModelPackage
						|| element.getEle() instanceof Diagram) {
					Tree tree = BusinessModelUtil.getOutlinePage()
							.getMrViewer().getTreeViewer().getTree();
					TreeItem[] items = tree.getItems();
					for (int i = 0; i < items.length; i++) {
						selectTreeItem(items[i], element.getEle());
					}
				}
				if (element.getEle() instanceof BusinessClass) {
					BusinessClass businessClass = (BusinessClass) element
							.getEle();
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
				if (element.getEle() instanceof Association) {
					Association ass = (Association) element.getEle();
					final BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
							getShell(), ass, ass.getBelongPackage(),
							BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
					if (dialog.open() == IDialogConstants.OK_ID) {
					}
				}
				if (element.getEle() instanceof Enumeration) {
					Enumeration enumeration = (Enumeration) element.getEle();
					final EnumEditDialog dialog = new EnumEditDialog(
							getShell(), enumeration.getBelongPackage(),
							enumeration);
					if (dialog.open() == IDialogConstants.OK_ID) {
						// BusinessModelEvent event1 = new
						// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_UPDATE,dialog.getEditingElement());
						// BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event1);
					}
				}
				if (element.getEle() instanceof Property || element.getEle() instanceof PropertyGroup) {
					Property pro = null;
					if (element.getEle() instanceof PropertyGroup) {
						pro = (Property) ((PropertyGroup) element.getEle()).getParent();
						BusinessClass bc = (BusinessClass) (pro.getParent());
						for (Property p : bc.getProperties()) {
							if (p.getId().equals(pro.getId()))
								pro = p;
						}
//						element.setEle(pro);
						PropertyEditorDialog dialog = new PropertyEditorDialog(
								getShell(), pro, bc);
						int returnKey = dialog.open();
						if (returnKey == Window.OK
								|| (returnKey == Window.CANCEL && dialog
										.isChanged())) {
							bc.getProperties().remove(pro);
							bc.addProperty((Property) dialog.getEditingElement());
							BusinessModelEvent event1 = new BusinessModelEvent(
									BusinessModelEvent.MODEL_ELEMENT_UPDATE, bc);
							BusinessModelUtil.getEditorBusinessModelManager()
									.businessObjectModelChanged(event1);
						}
					}
					if (element.getEle() instanceof Property) {
						pro = (Property) element.getEle();
						BusinessClass bc = (BusinessClass) (pro.getParent());
						for (Property p : bc.getProperties()) {
							if (p.getId().equals(pro.getId()))
								pro = p;
						}
						element.setEle(pro);
						PropertyEditorDialog dialog = new PropertyEditorDialog(
								getShell(), pro, bc);
						int returnKey = dialog.open();
						if (returnKey == Window.OK
								|| (returnKey == Window.CANCEL && dialog
										.isChanged())) {
							bc.getProperties().remove(pro);
							bc.addProperty((Property) dialog.getEditingElement());
							BusinessModelEvent event1 = new BusinessModelEvent(
									BusinessModelEvent.MODEL_ELEMENT_UPDATE, bc);
							BusinessModelUtil.getEditorBusinessModelManager()
									.businessObjectModelChanged(event1);
						}
					}
				}
				if (element.getEle() instanceof BusinessOperation) {
					BusinessOperation oper = (BusinessOperation) element
							.getEle();
					BusinessClass bc = (BusinessClass) (oper
							.getBelongBusinessClass());
					for (BusinessOperation p : bc.getOperations()) {
						if (p.getId().equals(oper.getId()))
							oper = p;
					}
					element.setEle(oper);
					OperationEditorDialog dialog = new OperationEditorDialog(
							getShell(), oper, bc);
					int returnKey = dialog.open();
					if (returnKey == Window.OK
							|| (returnKey == Window.CANCEL && dialog
									.isChanged())) {
						// bc.getProperties().remove(oper);
						// bc.addProperty((Property)dialog.getEditingElement());
						BusinessModelEvent event1 = new BusinessModelEvent(
								BusinessModelEvent.MODEL_ELEMENT_UPDATE, bc);
						BusinessModelUtil.getEditorBusinessModelManager()
								.businessObjectModelChanged(event1);
					}
				}
			}

			int level = 0;

			private void selectTreeItem(TreeItem treeItem,
					AbstractModelElement abstractModelElement) {
				int j = 0;
				if (abstractModelElement == treeItem.getData()) {
					BusinessModelUtil.getOutlinePage().getMrViewer()
							.forceFocus();
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
	
	/**
	 * Reset menu.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void resetMenu(){
		menu.removeAll();
		
		boolean isAllowDelete = false;
		ISelection selection = tableViewer.getSelection();
		IStructuredSelection select = (IStructuredSelection) selection;
		Iterator it = select.iterator();
		if(it.hasNext()){
			isAllowDelete = true;
		}
		
		if(isAllowDelete){
			actionGroup.fillContextMenu(menu, deleteAction);
			actionGroup.fillContextMenu(menu, deleteAllAction);
		}
	}
	
	/**
	 * Refresh.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void refresh() {
		tableViewer.setInput(valiResult);
		tableViewer.refresh();
	}
	
	/**
	 * Sets the data.
	 *
	 * @author mqfdy
	 * @param valiResult
	 *            the new data
	 * @Date 2018-09-03 09:00
	 */
	public void setData(List<ValiResult> valiResult) {
		this.valiResult = valiResult;
		tableViewer.setInput(valiResult);
		tableViewer.refresh();
	}

	/**
	 * Sets the file path.
	 *
	 * @author mqfdy
	 * @param filePath
	 *            the new file path
	 * @Date 2018-09-03 09:00
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
