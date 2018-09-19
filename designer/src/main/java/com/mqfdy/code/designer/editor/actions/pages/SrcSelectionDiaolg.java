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

// TODO: Auto-generated Javadoc
/**
 * 选择source folder对话框.
 *
 * @author mqfdy
 */
public class SrcSelectionDiaolg extends TrayDialog {

	
	/** The ok button. */
	private Button okButton;
	
	/** The label desc. */
	private Label labelDesc;
	
	/** The tree viewer. */
	private TreeViewer treeViewer;
	
	/** The select path. */
	private String selectPath;
	
	/** The src paths. */
	private List<String> srcPaths;

	/**
	 * Instantiates a new src selection diaolg.
	 *
	 * @param shell
	 *            the shell
	 * @param srcPaths
	 *            the src paths
	 */
	public SrcSelectionDiaolg(Shell shell,List<String> srcPaths) {
		super(shell);
		this.srcPaths = srcPaths;
	}

	/**
	 * 操作按钮.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
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
		newShell.setText("选择生成代码的位置");
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
	
	/**
	 * Gets the result.
	 *
	 * @author mqfdy
	 * @return the result
	 * @Date 2018-09-03 09:00
	 */
	protected String getResult(){
		return selectPath;
	}

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	/**
	 * Gets the ok button.
	 *
	 * @author mqfdy
	 * @return the ok button
	 * @Date 2018-09-03 09:00
	 */
	public Button getOkButton() {
		return okButton;
	}
	
	/**
	 * The Class MyTreeContenetProvider.
	 *
	 * @author mqfdy
	 */
	class MyTreeContenetProvider implements ITreeContentProvider{

        /**
		 * Gets the children.
		 *
		 * @author mqfdy
		 * @param parentElement
		 *            the parent element
		 * @return the children
		 * @Date 2018-09-03 09:00
		 */
        public Object[] getChildren(Object parentElement) {
            return null;
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
        	
        	if(element instanceof List){
        		Object [] objs = ((List)element).toArray(new Object[]{});
        		return objs;
        	}
            return null;
        }

        /**
         * 
         */
        public void dispose() {
            
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
	 * The Class MyTreeLableProvider.
	 *
	 * @author mqfdy
	 */
	class MyTreeLableProvider implements ILabelProvider{

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

		/**
		 * Gets the image.
		 *
		 * @author mqfdy
		 * @param arg0
		 *            the arg 0
		 * @return the image
		 * @Date 2018-09-03 09:00
		 */
		public Image getImage(Object arg0) {
			// TODO Auto-generated method stub
			return ImageManager.getInstance().getImageDescriptor(
					ImageKeys.IMG_TREE_FOLDER).createImage();
		}

		/**
		 * Gets the text.
		 *
		 * @author mqfdy
		 * @param obj
		 *            the obj
		 * @return the text
		 * @Date 2018-09-03 09:00
		 */
		public String getText(Object obj) {
			if(obj instanceof String){
				return (String)obj;
			}
			return "";
		}
	}
}
