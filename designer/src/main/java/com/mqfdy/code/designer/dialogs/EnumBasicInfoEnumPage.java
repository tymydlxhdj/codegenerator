package com.mqfdy.code.designer.dialogs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.importEnum.dialogs.EnumsPropertiesAnalyse;
import com.mqfdy.code.importEnum.dialogs.ImportEnumDialog;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * 枚举信息页.
 *
 * @author mqfdy
 */
public class EnumBasicInfoEnumPage extends Composite implements
		IBusinessClassEditorPage {

	/** The enum parent dialog. */
	private ImportEnumDialog enumParentDialog;//枚举导入对话框对象
	
	/** The enum properties. */
	private EnumsPropertiesAnalyse enumProperties;// 解析平台的enums.
	
	/** The list item. */
	private  org.eclipse.swt.widgets.List listItem;
	
	/** The list item right. */
	private  org.eclipse.swt.widgets.List listItemRight;
	
	/** The list. */
	private List<Enumeration> list;
	
	/** The button remove. */
	private Button buttonAdd,buttonRemove;
	
	/**
	 * Instantiates a new enum basic info enum page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param enumParentDialog
	 *            the enum parent dialog
	 */
	public EnumBasicInfoEnumPage(Composite parent, int style, ImportEnumDialog enumParentDialog) {
		super(parent, style);
		this.enumParentDialog = enumParentDialog;
		createContent();
		addButtonListeners();
		
	}

	/**
	 * Creates the content.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createContent() {
		// 关联关系信息区域
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginLeft = 5;
		this.setLayout(layout);

		
		//list控件：列表框
		listItem = new  org.eclipse.swt.widgets.List(this, SWT.BORDER |SWT.V_SCROLL|SWT.MULTI);
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
		grid.heightHint= 60;
		listItem.setLayoutData(grid);
	
		
		Composite group  = new Composite(this, SWT.None);
		GridLayout layoutGroup = new GridLayout(1,true);
		GridData grid11 = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
		group.setLayout(layoutGroup);
		group.setLayoutData(grid11);
		
		buttonAdd = new Button(group, SWT.NONE);
		buttonAdd.setText(">>");
		GridData gridCenter = new GridData(SWT.CENTER, SWT.TOP, true, false);
		gridCenter.heightHint=15;
		buttonAdd.setLayoutData(gridCenter);
		
		
	    buttonRemove = new Button(group, SWT.NONE);
		buttonRemove.setText("<<");
		gridCenter = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gridCenter.heightHint=15;
		buttonRemove.setLayoutData(gridCenter);
		
		listItemRight = new  org.eclipse.swt.widgets.List(this, SWT.BORDER |SWT.V_SCROLL|SWT.MULTI);
		GridData gridRight = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
		listItemRight.setLayoutData(gridRight);
		
	}

	/**
	 * 初始化基本信息值.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initControlValue()  {
		// 解析平台的enums.properties枚举文件
		enumProperties = new EnumsPropertiesAnalyse(getProject());
		int k = enumProperties.getKeys().size();
		List<String> fileKeyNameList = new ArrayList<String>();
		
		for (int j = 0; j < k; j++) {
			fileKeyNameList.add(enumProperties.getKeys().get(j).toString());
			listItem.add(enumProperties.getKeys().get(j).toString());
		}
		String[] items = (String[])fileKeyNameList.toArray(new String[fileKeyNameList.size()]);
		listItem.setItems(items);
		if(0!=listItem.getItemCount()){
			listItem.select(0);
			setEnumOrderKeyValue(0);
		}
		
		
		//添加监听事件 单击与双击
		listItem.addSelectionListener(new SelectionListener() {
			private int oldIndex = -1;
			private String oldContent = null;
			//双击
			public void widgetDefaultSelected(final SelectionEvent e) {
				for(int a :listItem.getSelectionIndices()){
					String name = StringUtil.convertNull2EmptyStr(listItem.getItem(a).toString().trim());
					listItemRight.add(name);
				}
				for(String s :listItem.getSelection()){
					listItem.remove(s);
				}
				if(0!=listItem.getItemCount()){
					listItem.select(0);
					setEnumOrderKeyValue(0);
					listItemRight.deselectAll();
				}
				if(0==listItem.getItemCount()&&0!=listItemRight.getItemCount()){
					listItemRight.select(0);
					setEnumOrderKeyValueRight(0);
				}
			}
			//单击
			public void widgetSelected(final SelectionEvent e) {
				if (oldIndex != -1){
					listItem.setItem(oldIndex, oldContent);
				}
				int index = listItem.getSelectionIndex();
				setEnumOrderKeyValue(index);
				listItemRight.deselectAll();
			}
		});
		
		
		listItemRight.addSelectionListener(new SelectionListener() {
			private int oldIndex = -1;
			private String oldContent = null;
			//listItemRight 双击
			public void widgetDefaultSelected(final SelectionEvent e) {
				
				for(int a :listItemRight.getSelectionIndices()){
					String name = StringUtil.convertNull2EmptyStr(listItemRight.getItem(a).toString().trim());
					listItem.add(name,0);
				}
				for(String s :listItemRight.getSelection()){
					listItemRight.remove(s);
				}
				if(0!=listItemRight.getItemCount()){
					listItemRight.select(0);
					setEnumOrderKeyValueRight(0);
					listItem.deselectAll();
				}
				
				if(0==listItemRight.getItemCount() && 0!=listItemRight.getItemCount()){
					listItem.select(0);
					setEnumOrderKeyValue(0);
				}
			}

			// listItemRight单击
			public void widgetSelected(final SelectionEvent e) {
				if (oldIndex != -1){
					listItemRight.setItem(oldIndex, oldContent);
				}
				int index = listItemRight.getSelectionIndex();
				setEnumOrderKeyValueRight(index);
				listItem.deselectAll();
			}
		});
	}
	
	/**
	 * 将序号,key,value插入tableview.
	 *
	 * @author mqfdy
	 * @param index
	 *            所选中项的序号
	 * @return the enum elements enum page
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("rawtypes")
	private EnumElementsEnumPage setEnumOrderKeyValue(int index){
		List<Map<String,String>> list = getKeyValueformEnumValue(listItem.getItem(index));
		EnumElementsEnumPage enumElementsEnumPage = enumParentDialog.getEnumElementsEnumPage();
		
		List<EnumElement> listElements = new ArrayList<EnumElement>();
		for(int k=0;k<list.size();k++){
			for (Object key :  ((HashMap) list.get(k)).keySet()) {
				String enumKey = key.toString();
				String enumValue = ((HashMap) list.get(k)).get(key).toString();
				EnumElement element = new EnumElement(enumKey,enumValue);
				element.setOrderNum(listElements.size()+1);
				listElements.add(element);
				
	        }
		}
		enumElementsEnumPage.setEnumList(listElements);
		return enumElementsEnumPage;
	}
	
	/**
	 * 将listItemRight 序号,key,value插入tableview.
	 *
	 * @author mqfdy
	 * @param index
	 *            所选中项的序号
	 * @return the enum elements enum page
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("rawtypes")
	private EnumElementsEnumPage setEnumOrderKeyValueRight(int index){
		List list = new ArrayList();
		list = getKeyValueformEnumValue(listItemRight.getItem(index));
		EnumElementsEnumPage enumElementsEnumPage = enumParentDialog.getEnumElementsEnumPage();
		List<EnumElement> listElements = new ArrayList<EnumElement>();
		for(int k=0;k<list.size();k++){
			for (Object key :  ((HashMap) list.get(k)).keySet()) {
				String enumKey = key.toString();
				String enumValue = ((HashMap) list.get(k)).get(key).toString();
				EnumElement element = new EnumElement(enumKey,enumValue);
				element.setOrderNum(listElements.size()+1);
				listElements.add(element);
				
	        }
		}
		enumElementsEnumPage.setEnumList(listElements);
		return enumElementsEnumPage;
	}
	
	
	/**
	 * 根据enumName名称 将其中的value 值进行处理.
	 *
	 * @author mqfdy
	 * @param enumName
	 *            枚举名称
	 * @return list
	 * @Date 2018-09-03 09:00
	 */
	private List<Map<String,String>> getKeyValueformEnumValue(String enumName) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			String values = enumProperties.getValues(enumName);
			String[] arr = values.split(",");
			for (String a : arr) {
				Map<String,String> mapkeyValue = new HashMap<String,String>();
				String k = "";
				String value = "";
				String[] brr = a.split(":");
				for (int i = 0; i < brr.length; i++) {
					if (i % 2 == 0) {
						k = brr[i];
					} else {
						value = brr[i];
					}
				}
				mapkeyValue.put(k, value);
				list.add(mapkeyValue);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	
	
	/**
	 * @return
	 */
	public boolean validateInput() {
		for(String a :listItemRight.getItems()){
			String textName = a.toString().trim();
			if (textName.trim().length() == 0) {
				enumParentDialog.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
				return false;
			}
			if (!ValidatorUtil.isFirstUppercase(textName)) {
				enumParentDialog.setErrorMessage(ERROR_MESSAGE_NAME_UPPER);
				return false;
			}
			if (CheckerUtil.checkJava(textName)) {
				enumParentDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
				return false;
			}
			if (!ValidatorUtil.valiNameLength(textName)) {
				enumParentDialog.setErrorMessage(ERROR_MESSAGE_NAME_LENGTH);
				return false;
			}
			//改变为枚举
			if (ModelElementEditorDialog.OPERATION_TYPE_IMPORT.equals(enumParentDialog
					.getOperationType())) {
				if (CheckerUtil.checkIsExist(textName,
						BusinessModelManager.ENUMERATION_NAME_PREFIX)) {
					enumParentDialog.setErrorMessage(textName+":"+ERROR_MESSAGE_NAME_EXIST);
					return false;
				}
			}else {
				if (!textName.equalsIgnoreCase(
						enumParentDialog.getEnumeration().getName())) {
					// 编辑时如果改为其他名
					if (CheckerUtil.checkIsExist(textName,
							BusinessModelManager.ENUMERATION_NAME_PREFIX)) {
						enumParentDialog.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 将选中的Enumeration处理,并加入list集合中.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void updateTheEditingElement() {
		List<Enumeration> list = new ArrayList<Enumeration>();
		int itemCount = listItemRight.getItemCount();
		
		for(int i=0;i<itemCount;i++){
			String name = StringUtil.convertNull2EmptyStr(listItemRight.getItem(i).toString().trim());
			Enumeration enumeration = new Enumeration();
			enumeration.setName(name);
			enumeration.setDisplayName(name);
			enumeration.setBelongPackage(enumParentDialog.getEnumeration().getBelongPackage());
			enumeration.setVersionInfo(enumParentDialog.getEnumeration().getVersionInfo());
			enumeration.setStereotype(enumParentDialog.getEnumeration().getStereotype());
			enumParentDialog.setEnumeration(enumeration);
			setEnumOrderKeyValueRight(i).updateTheEditingElement();
			list.add(enumeration);
		}
		setEnumerationList(list);
	}
	
	/**
	 * Sets the enumeration list.
	 *
	 * @author mqfdy
	 * @param list
	 *            the new enumeration list
	 * @Date 2018-09-03 09:00
	 */
	public void setEnumerationList(List<Enumeration> list){
		this.list  = list;
	} 
	
	/**
	 * Gets the enumeration list.
	 *
	 * @author mqfdy
	 * @return the enumeration list
	 * @Date 2018-09-03 09:00
	 */
	public List<Enumeration> getEnumerationList(){
		return this.list;
	} 
	
	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 获取项目名称
	 */
	@SuppressWarnings("restriction")
	public IProject getProject(){
        IProject project = null;   
        //1.根据当前编辑器获取工程   
        IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();   
        if(part != null){   
            Object object = part.getEditorInput().getAdapter(IFile.class);
            if(object != null){   
                project = ((IFile)object).getProject();   
            }   
        }   
           
        if(project == null){   
            ISelectionService selectionService =      
                    Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();  
            ISelection selection = selectionService.getSelection();
            if(selection instanceof IStructuredSelection) {     
                Object element = ((IStructuredSelection)selection).getFirstElement();     
         
                if (element instanceof IResource) {     
                    project= ((IResource)element).getProject();     
                } else if (element instanceof IJavaElement) {     
                    IJavaProject jProject= ((IJavaElement)element).getJavaProject();     
                    project = jProject.getProject();     
                } else if(element instanceof EditPart){   
                    IFile file = (IFile) ((DefaultEditDomain)((EditPart)element).getViewer().getEditDomain()).getEditorPart().getEditorInput().getAdapter(IFile.class);   
                    project = file.getProject();   
                }    
            }      
        }   
           
        return project;   
    }

	/**
	 * 添加按钮监听事件.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void addButtonListeners(){
		buttonAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for(int a :listItem.getSelectionIndices()){
					String name = StringUtil.convertNull2EmptyStr(listItem.getItem(a).toString().trim());
					listItemRight.add(name);
				}
				for(String s :listItem.getSelection()){
					listItem.remove(s);
				}
				
				listItem.select(0);
				setEnumOrderKeyValue(0);
				listItemRight.deselectAll();
			};
		});
		
		buttonRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for(int a :listItemRight.getSelectionIndices()){
					String name = StringUtil.convertNull2EmptyStr(listItemRight.getItem(a).toString().trim());
					listItem.add(name,0);
				}
				for(String s :listItemRight.getSelection()){
					listItemRight.remove(s);
				}
				
				listItemRight.select(0);
				setEnumOrderKeyValue(0);
				listItem.deselectAll();
			}
		});
	}

}
