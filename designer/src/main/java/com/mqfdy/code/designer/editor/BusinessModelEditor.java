package com.mqfdy.code.designer.editor;

//import java.io.IOException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import com.mqfdy.code.designer.editor.dialogs.ReModelListDialog;
import com.mqfdy.code.designer.editor.listeners.EditorResourceChangeListener;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.views.properties.MultiPageEditorPropertySheetPage;
import com.mqfdy.code.resource.BomManager;


/**
 * 业务模型编辑器，包括设计模式和源文件模式两个页面
 * 
 * @author mqfdy
 * 
 */
public class BusinessModelEditor extends MultiPageEditorPart implements
		IResourceChangeListener {

	/**
	 * 源文件编辑器
	 */
	private BusinessModelSourceEditor sourceEditor;

	private BusinessModelEditor businessModelEditor = this;
	/**
	 * 业务模型图形化编辑器
	 */
	private BusinessModelDiagramEditor bmdEditor;
	private IResourceChangeListener resourceChangeListener = new EditorResourceChangeListener(
			this);
	private MultiPageEditorPropertySheetPage propertySheetPage;

	private boolean flag;
	private boolean isHasErrorRefObj = false;
	private boolean isOpendReModelIdMes = false;
//	private ValiException exception;

	private String pathList = "";

//	public static final String ID = "com.mqfdy.code.designer.editor.MultiPageEditor";
	public static final String ID = "com.mqfdy.code.designer.editor.BusinessModelEditor";
//	private ModelPackage modelPackage;
	/**
	 * 创建业务模型编辑器
	 */
	public BusinessModelEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		propertySheetPage = new MultiPageEditorPropertySheetPage();
	}

	/**
	 * 创建第一页设计模式
	 */
	void createDiagramPage() {
		try {
			bmdEditor = new BusinessModelDiagramEditor(this);
			int index = addPage(bmdEditor, getEditorInput());
			setPageText(index, "设计模式");
		} catch (PartInitException e) {
			Logger.log(e);
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
	}

	/**
	 * 创建第二页 源文件模式
	 */
	void createSourcePage() {
		try {
			sourceEditor = new BusinessModelSourceEditor();
			int index = addPage(sourceEditor, getEditorInput());
			setPageText(index, "源文件模式");
		} catch (PartInitException e) {
			Logger.log(e);
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
	}
//	/**
//	 * 创建错误页
//	 */
//	void createErrorPage() {
//		try {
//			IStatus originalStatus = new Status(IStatus.ERROR,
//                    WorkbenchPlugin.PI_WORKBENCH, 0,
//                    "OM文件格式出错",//$NON-NLS-1$
//                    exception);
//            IStatus logStatus = StatusUtil.newStatus(originalStatus,
//                    NLS.bind("OM文件格式出错",  //$NON-NLS-1$
//                    		"", originalStatus.getMessage()));
//            IStatus displayStatus = StatusUtil.newStatus(originalStatus,
//            		NLS.bind(WorkbenchMessages.EditorManager_unableToCreateEditor,
//            				originalStatus.getMessage()));
//
//			// Pass the error to the status handling facility
//            StatusManager.getManager().handle(logStatus);
//            EditorDescriptor desc;
//            IEditorRegistry reg = WorkbenchPlugin.getDefault()
//                    .getEditorRegistry();
//            desc = (EditorDescriptor) reg.findEditor(this.getClass().toString());
//            
//            
//            ErrorEditorPart editor = (ErrorEditorPart) getEmptyEditor(desc, displayStatus);
//			int index = addPage(editor, getEditorInput());
//			setPageText(index, "文件错误");
//		} catch (PartInitException e) {
//			Logger.log(e);
//			ErrorDialog.openError(getSite().getShell(),
//					"Error creating nested text editor", null, e.getStatus());
//		}
//	}
//	/**
//	 * 返回空的错误页面
//	 */
//	public IEditorPart getEmptyEditor(EditorDescriptor descr, IStatus displayStatus) {
//        ErrorEditorPart part = new ErrorEditorPart(displayStatus);
//        IEditorInput input = null;
//        input = getEditorInput();
//        EditorSite site = (EditorSite) getSite();
//		part.init(site, input);
//
//        Composite parent = getContainer();
//        Composite content = new Composite(parent, SWT.NONE);
//        content.setLayout(new FillLayout());
//        
//        try {
//			part.createPartControl(content);
//		} catch (Exception e) {
//			content.dispose();
//			StatusManager.getManager().handle(
//					StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH, e));
//			return null;
//		}
//        if (displayStatus == null)
//        	part.setPartName("(Empty)"); //$NON-NLS-1$
//        
//        return part;
//	}
	/**
	 * 创建编辑器页面
	 */
	protected void createPages() {
//		if(!flag){
//			createErrorPage();
//			return;
//		}
		createDiagramPage();
		createSourcePage();
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(
				resourceChangeListener);
		bmdEditor.removeListener();
		super.dispose();
	}

	/**
	 * 保存
	 */
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	@Override
	public Object getAdapter(Class clazz) {
		if (IPropertySheetPage.class.equals(clazz)) {
			return propertySheetPage;
		}
		return this.getActiveEditor().getAdapter(clazz);
	}

	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				resourceChangeListener);
	}

	public void setInputPub(IEditorInput input) {
		setInput(input);
	}

	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");
//		ValidateXML validateXML = new ValidateXML();
		flag = true;
//		try {
//			flag = validateXML.ValidateXml(((IFileEditorInput) editorInput).getFile()
//					.getLocation().toString());
//		} catch (SAXException e1) {
//			Logger.log(e1);
//		} catch (IOException e1) {
//			Logger.log(e1);
//		}
//		if (!flag) {
//			try {
//				exception = new ValiException(validateXML.getExceptionString().toString());
//				throw new ValiException(validateXML.getExceptionString().toString());
//			} catch (ValiException e) {
//				Logger.log(e);
//			}
//			// return;
//		}
		super.init(site, editorInput);
		// setSite(site);
		// setInput(editorInput);
		// site.setSelectionProvider(new MultiPageSelectionProvider(this));
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * 页面切换时的操作
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		// 更新
		propertySheetPage.updatePropertySheet(getEditor(newPageIndex));

	}

	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
							.getPages();
					for (int i = 0; i < pages.length; i++) {
						if (sourceEditor != null &&  ((FileEditorInput) sourceEditor.getEditorInput())
								.getFile().getProject()
								.equals(event.getResource())) {
							IEditorPart editorPart = pages[i]
									.findEditor(sourceEditor.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	public BusinessModelDiagramEditor getBuEditor() {
		return bmdEditor;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		if(isHasErrorRefObj){
			String mes = "模型中包含错误的引用，可能是由于引用模型的位置改变，或者原模型被删除，原模型路径如下：\n"
					+ pathList + "如想打开该模型，请修改以上模型或者删除本模型中的引用， 是否删除这些引用？\n"
					+"点击取消将关闭编辑器!";
			SynErrorRefMesg syn = new SynErrorRefMesg(mes);
			BusinessModelEditorPlugin.getShell().getDisplay().asyncExec(syn);
		}
		if(!isOpendReModelIdMes){
			String fpath = getBuEditor().getBusinessModelManager().getPath();
			IPath path = new Path(fpath);
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			String curModelId = getBuEditor().getBusinessModelManager().getBusinessObjectModel().getId();
			IFile sourceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path.makeRelativeTo(root.getLocation()));
			IFolder sourceFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(sourceFile.getParent().getFullPath().makeRelativeTo(root.getLocation()));
			List<IResource> omList = new ArrayList<IResource>();
			try {
				IResource[] files = sourceFolder.members();
				for(IResource f : files){
					try {
						String newModelId = BomManager.getModelId(f.getLocation().toOSString());
						if(curModelId.equals(newModelId)){
							omList.add(f);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DocumentException e) {
						try {
							String newModelId = BomManager.getModelId(f.getLocationURI().toURL().getPath());
							if(curModelId.equals(newModelId)){
								omList.add(f);
							}
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (DocumentException e1) {
							e1.printStackTrace();
						}
					}
				}
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(omList.size() > 1){
				isOpendReModelIdMes = true;
				SynReModelIdMesg syn = new SynReModelIdMesg(omList);
				BusinessModelEditorPlugin.getShell().getDisplay().asyncExec(syn);
			}
		}
		
		super.setFocus();
	}

	public boolean isHasErrorRefObj() {
		return isHasErrorRefObj;
	}

	public void setHasErrorRefObj(boolean hasErrorRefObj) {
		this.isHasErrorRefObj = hasErrorRefObj;
	}

	public String getPathList() {
		return pathList;
	}

	public void setPathList(String pathList) {
		this.pathList = pathList;
	}
	//用于在模型内有错误的引用对象时提示一个对话框
	class SynErrorRefMesg extends Thread{
		String mes;
		int s = 1;
		public SynErrorRefMesg(String mes){
			this.mes = mes;
		}
		@Override
		public void run() {
			MessageDialog dia = new MessageDialog(
					BusinessModelEditorPlugin.getShell(), "提示", null, mes,
					0, new String[] { "删除", "取消" }, 0);
			
			s = dia.open();
			if (s == TitleAreaDialog.OK) {
				bmdEditor.doSave();
				isHasErrorRefObj = false;
			}else{
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						getSite().getPage().closeEditor(businessModelEditor, false);
					}
				});
			}
		}
	}
	
	//用于在模型id重复时提示一个对话框
	class SynReModelIdMesg extends Thread{
		List<IResource> omList;
		int s = 1;
		public SynReModelIdMesg(List<IResource> omList){
			this.omList = omList;
		}
		@Override
		public void run() {
			ReModelListDialog dia = new ReModelListDialog(
					BusinessModelEditorPlugin.getShell(),omList);
			
			s = dia.open();
		}
	}
	
//	public ModelPackage getModelPackage() {
//		return modelPackage;
//	}
	
}
