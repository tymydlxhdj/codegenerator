package com.mqfdy.code.designer.editor.actions.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BEScene;
import com.mqfdy.code.model.BEStatus;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;

/**
 * 选择source folder对话框
 * 
 * @author mqfdy
 * 
 */
public class SrcSelectionDiaolg extends TrayDialog {

	
	private Button okButton;
	private Label labelDesc;
	private TreeViewer treeViewer;
	private String selectPath;
	private List<String> srcPaths;

	public SrcSelectionDiaolg(Shell shell,List<String> srcPaths) {
		super(shell);
		this.srcPaths = srcPaths;
	}

	/**
	 * 操作按钮
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("选择生成代码的位置");
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM);
		newShell.setImage(icon);
	}

	protected Control createDialogArea(Composite parent) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.heightHint = 280;
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		
		labelDesc = new Label(parent,SWT.NONE);
		labelDesc.setText("请选择代码生成的位置");
		
		treeViewer = new TreeViewer(parent,SWT.BORDER|SWT.SCROLL_LINE);
		//treeViewer.getTree().setHeaderVisible(true);
        //TreeColumn column = new TreeColumn(treeViewer.getTree(), SWT.LEFT);
        //column.setText("Source Folder");
        //column.setWidth(300);
        
        treeViewer.setContentProvider(new MyTreeContenetProvider());
        treeViewer.setLabelProvider(new MyTreeLableProvider());
        treeViewer.getTree().setLayoutData(gridData);
        treeViewer.setInput(srcPaths);
        treeViewer.addSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				Object node = ((IStructuredSelection) event.getSelection())
						.getFirstElement();
				if(node!=null && node instanceof String){
					selectPath = (String)node;
				}else{
					selectPath = "";
				}
			}
        	
        });
        treeViewer.addDoubleClickListener(new IDoubleClickListener(){

			public void doubleClick(DoubleClickEvent event) {
				Object node = ((IStructuredSelection) event.getSelection())
						.getFirstElement();
				if(node!=null && node instanceof String){
					selectPath = (String)node;
				}else{
					selectPath = "";
				}
				okPressed();
			}
        	
        });
        
		return parent;
	}
	
	protected String getResult(){
		return selectPath;
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	public Button getOkButton() {
		return okButton;
	}
	
	class MyTreeContenetProvider implements ITreeContentProvider{

        public Object[] getChildren(Object parentElement) {
            return null;
        }
        
        

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            return false;
        }

        public Object[] getElements(Object element) {
        	
        	if(element instanceof List){
        		Object [] objs = ((List)element).toArray(new Object[]{});
        		return objs;
        	}
            return null;
        }

        public void dispose() {
            
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            
        }
        
    }

	
	class MyTreeLableProvider implements ILabelProvider{

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

		public Image getImage(Object arg0) {
			// TODO Auto-generated method stub
			return ImageManager.getInstance().getImageDescriptor(
					ImageKeys.IMG_TREE_FOLDER).createImage();
		}

		public String getText(Object obj) {
			if(obj instanceof String){
				return (String)obj;
			}
			return "";
		}
	}
}
