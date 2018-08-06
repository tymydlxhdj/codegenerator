package com.mqfdy.code.shareModel.dialogs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.actions.ShareModelActionHttpclient;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.editor.utils.ModelObjectManageUtil;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.models.ModelMessage;
import com.mqfdy.code.designer.preferences.ModelPreferencePage;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;
import com.mqfdy.code.resource.BomManager;

public class FindModelDialog extends TitleAreaDialog {
	private static IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
			.getPreferenceStore();
	public static String URL="http://"+ store.getString(ModelPreferencePage.SHAREMODELIP)+":"+ store.getString(ModelPreferencePage.SHAREMODELPORT)+"/sguap-mdd";
//	public static String URL="http://localhost:8080/share_model";

	private static final String TITLE = "查询获取共享模型";
	public  String filepath=null;
	public  IProject project=null;
	
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();
	private Browser browser;
	public ImageManager imageManager=new ImageManager();
	public ModelMessage model=new ModelMessage();
	public static Object selectedObject;
	
	private GridLayout layout = new GridLayout(1, false);
	public Composite container ;
	public TabFolder folder;
	public ViewTablePage viewPage;
	public SerchTablePage serchPage;

	public FindModelDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		setTitle(TITLE);
		setTitleImage(imageManager.getImage(ImageKeys.IMG_SHARE_MODEL));

		
		GridData gridData=new GridData(GridData.FILL_BOTH);
		folder=new TabFolder(container, SWT.NONE);
		folder.setLayoutData(gridData);
		TabItem viewTabItem = new TabItem(folder, SWT.NONE); 
		viewTabItem.setText("分类浏览");		
		
		TabItem serachTabItem = new TabItem(folder, SWT.NONE); 
		serachTabItem.setText("搜索");		

		viewPage=new ViewTablePage(folder,this);
		serchPage=new SerchTablePage(folder,this);
		folder.getItem(0).setControl(viewPage);
		folder.getItem(1).setControl(serchPage);
		
		
//		browser = new Browser(container, SWT.NONE); 		
//        String url = "http://localhost:8080/SGUAP/treeTable/index.jsp";
//        browser.setJavascriptEnabled(true);
//        browser.setUrl(url);
//        browser.forward();
	    return parent; 
  }
	
	@Override
	protected void okPressed() {
		boolean bool=false;
		Object obj=null;
		if(0==folder.getSelectionIndex()&&viewPage.table.getSelection().length>0){
			obj=getModleDesc(viewPage.table.getSelection()[0].getText(0));
		}
		else if(1==folder.getSelectionIndex()&&serchPage.table.getSelection().length>0){
			obj=getModleDesc(serchPage.table.getSelection()[0].getText(0));
		}
		if(obj!=null&&!(obj instanceof BusinessObjectModel)){			
			  MessageBox messageBox = new MessageBox(this.getShell(), SWT.APPLICATION_MODAL | SWT.YES | SWT.NO);  
			   messageBox.setText("");  
			   messageBox.setMessage("存在相同的实体时,执行覆盖操作?");
			   if(messageBox.open() == SWT.YES){
				    	copyObjectToEditor(obj);
				    	super.okPressed();   
			   }else{
				   return;
			   }
			
		}
		else if(obj!=null&&obj instanceof BusinessObjectModel){
			ObjectModelNewDialog dialog=new ObjectModelNewDialog(new Shell(),this,(BusinessObjectModel) obj);
			if(dialog.open()==dialog.OK){				
				super.okPressed();			
			}else{
				return;
			}
		}
		else{
			setMessage("共享模型获取失败,请检查", IMessageProvider.ERROR);
		}
		super.okPressed();
	}

	
	/**
	 * 复制模型到设计器中
	 * @param obj
	 */
	private void copyObjectToEditor(Object obj) {
		if(obj instanceof Enumeration){
			if(manager.getBusinessObjectModel().getEnumerations().size()==0){
				DiagramElement ele=getDiagramElement(((Enumeration)obj).getId());
				manager.getBusinessObjectModel().getDiagrams().get(0).addElement(ele);
				((Enumeration) obj).setBelongPackage(manager.getBusinessObjectModel().getPackages().get(0));
				manager.getBusinessObjectModel().getEnumerations().add((Enumeration)obj);
				
//				manager.businessObjectModelChanged(new BusinessModelEvent(
//						BusinessModelEvent.MODEL_ELEMENT_ADD, (Enumeration) obj));
				
			
				BomManager.outputXmlFile(manager.getBusinessObjectModel(), manager.getPath());
		
			}
			else{
				for(int i=0;i<manager.getBusinessObjectModel().getEnumerations().size();i++ ){
					Enumeration e=manager.getBusinessObjectModel().getEnumerations().get(i);
					if(e.getId().equals(((Enumeration) obj).getId())||
							e.getName().equals(((Enumeration) obj).getName())){
						((Enumeration) obj).setBelongPackage(manager.getBusinessObjectModel().getPackages().get(0));
					    ((Enumeration) obj).setId(e.getId());
						
//						manager.businessObjectModelChanged(new BusinessModelEvent(
//								BusinessModelEvent.MODEL_ELEMENT_ADD, (Enumeration) obj));
//						manager.businessObjectModelChanged(new BusinessModelEvent(
//								BusinessModelEvent.MODEL_ELEMENT_DELETE, e));
						manager.getBusinessObjectModel().getEnumerations().remove(i);
						manager.getBusinessObjectModel().getEnumerations().add((Enumeration)obj);
					
						BomManager.outputXmlFile(manager.getBusinessObjectModel(), manager.getPath());
						break;
					}
					else if(i==manager.getBusinessObjectModel().getEnumerations().size()-1){
						DiagramElement ele=getDiagramElement(((Enumeration)obj).getId());
						manager.getBusinessObjectModel().getDiagrams().get(0).addElement(ele);
						((Enumeration) obj).setBelongPackage(manager.getBusinessObjectModel().getPackages().get(0));
//						manager.businessObjectModelChanged(new BusinessModelEvent(
//								BusinessModelEvent.MODEL_ELEMENT_ADD, (Enumeration) obj));
						
						manager.getBusinessObjectModel().getEnumerations().add((Enumeration)obj);
						BomManager.outputXmlFile(manager.getBusinessObjectModel(), manager.getPath());									
					}
				}
															
			}
		}
		else{
			if(manager.getBusinessObjectModel().getBusinessClasses().size()==0){
				DiagramElement ele=getDiagramElement(((BusinessClass)obj).getId());
				manager.getBusinessObjectModel().getDiagrams().get(0).addElement(ele);
				((BusinessClass) obj).setBelongPackage(manager.getBusinessObjectModel().getPackages().get(0));
				manager.getBusinessObjectModel().getBusinessClasses().add((BusinessClass)obj);
							
				BomManager.outputXmlFile(manager.getBusinessObjectModel(), manager.getPath());
		
			}
			else{
				for(int i=0;i<manager.getBusinessObjectModel().getBusinessClasses().size();i++ ){
					BusinessClass e=manager.getBusinessObjectModel().getBusinessClasses().get(i);
					if(e.getId().equals(((BusinessClass) obj).getId())||
							e.getName().equals(((BusinessClass) obj).getName())){
						((BusinessClass) obj).setBelongPackage(manager.getBusinessObjectModel().getPackages().get(0));
					    ((BusinessClass) obj).setId(e.getId());
						
						manager.getBusinessObjectModel().getBusinessClasses().remove(i);
						manager.getBusinessObjectModel().getBusinessClasses().add((BusinessClass)obj);
					
						BomManager.outputXmlFile(manager.getBusinessObjectModel(), manager.getPath());
						break;
					}
					else if(i==manager.getBusinessObjectModel().getBusinessClasses().size()-1){
						DiagramElement ele=getDiagramElement(((BusinessClass)obj).getId());
						manager.getBusinessObjectModel().getDiagrams().get(0).addElement(ele);
						((BusinessClass) obj).setBelongPackage(manager.getBusinessObjectModel().getPackages().get(0));
						
						manager.getBusinessObjectModel().getBusinessClasses().add((BusinessClass)obj);
						BomManager.outputXmlFile(manager.getBusinessObjectModel(), manager.getPath());									
					}
				}
															
			}
		}
		refreshEditor(manager.getPath());		
	}

	/**
	 * 构建图形
	 * @param id
	 * @return
	 */
	private DiagramElement getDiagramElement(String id){
		DiagramElement ele=new DiagramElement();
		int h = 0;
		for (DiagramElement de : manager.getBusinessObjectModel().getDiagrams().get(0).getElements()) {
			if (de.getStyle().getPositionY() > 0
					&& de.getStyle().getHeight() > 0)
				h = (int) Math.max(h, de.getStyle().getPositionY()
						+ de.getStyle().getHeight());
		}
		ele.setObjectId(id);
		ElementStyle style=new ElementStyle();
		style.setPositionX((float) 20.0);
		style.setPositionY((float) h);
		style.setWidth(160);
		style.setHeight(200);
		ele.setStyle(style);
		return ele;
	}
	
	/**
	 * 刷新或打开设计器
	 * @param filePath
	 */
	public void refreshEditor(String filePath){
	
		IPath path = new Path(filePath);
		IResource res = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path)[0];
		try {
			res.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		IWorkbenchPage[] pages = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();							
		path = new Path(filePath.substring(0,filePath.lastIndexOf("/")+1));
		path = path.makeRelativeTo(root.getLocation());
		IResource resource = root.findMember(path);
		IContainer container = (IContainer) resource;
		IFile file = container.getFile(new Path(filePath.split("/")[filePath.split("/").length-1]));	
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, file, true);
			EditorOperation.refreshNodeEditParts();
			EditorOperation.refreshEditorByFile(filePath.replaceAll("/", "\\\\"), pages);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取选中的模型
	 * @param id
	 * @return
	 */
	public static Object getModleDesc(String id){
		Map<String,String> params=new HashMap<String,String>();
		params.put("id", id);
		JSONArray jsonArr;
		JSONObject json;
		try {
			jsonArr = new JSONArray(ShareModelActionHttpclient.getDoPostResponseDataByURL(URL+"/CreateModelFileServlet", params ,  "utf-8" ,  true));		
			json = jsonArr.optJSONObject(0);
			if(json!=null&&!"fail".equals(json.getString("returnCode"))){
				String xmlStr=json.getString("objModelFile");
				if("0".equals(json.getString("modelType"))){
					 selectedObject = ModelObjectManageUtil.xml2Model(xmlStr);
					 return selectedObject;
				}else if("1".equals(json.getString("modelType"))){
					selectedObject=ModelObjectManageUtil.xml2BusinessClass(xmlStr);
					return selectedObject;
				}else{
					selectedObject=ModelObjectManageUtil.xml2Enumeration(xmlStr);
					return selectedObject;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return selectedObject;
	}
	
	
	
	public Object getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Object selectedObject) {
		this.selectedObject = selectedObject;
	}

	
	
	public String getNEWOMPATH() {
		return filepath;
	}

	public void setNEWOMPATH(String nEWOMPATH) {
		filepath = nEWOMPATH;
	}

	public IProject getPROJECT() {
		return project;
	}

	public void setPROJECT(IProject pROJECT) {
		project = pROJECT;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(1000, 700);
	}
	
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("共享模型库");
	}
	
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX |SWT.MIN ;
	}
	
	@Override
	public boolean isHelpAvailable() {
		return false;
	}
	
}
