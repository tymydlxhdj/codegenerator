package com.mqfdy.code.shareModel.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.Association;

/**
 * 业务实体信息页面(包含基本信息、操作、关联关系3个tab页)
 * @author mqfdy
 *
 */
public class BusinessClassMessageDailog extends TitleAreaDialog {
	private static final String TITLE = "查看业务实体信息";
	public ImageManager imageManager=new ImageManager();
	public Object obj=null;  //选中的对象
	public List<Association> assList=null;  //选中的对象
	
	private boolean hasAss=false;
	private GridLayout layout = new GridLayout(1, false);
	public Composite container ;
	public TabFolder folder;
	public BusinessClassBaiscInfoPage basicInfoPage;
	public BusinessClassOptionPage optionInfoPage;
	public AssociationPage assPage;
	public BusinessClassMessageDailog(Shell parentShell,boolean hasAss,Object obj,List<Association> assList) {
		super(parentShell);
		this.hasAss=hasAss;	
		this.obj=obj;
		this.assList=assList;
	}
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		setTitle(TITLE);
		setTitleImage(imageManager.getImage(ImageKeys.IMG_DIALOG_BUSINESSCLASS));

		
		GridData gridData=new GridData(GridData.FILL_BOTH);
		folder=new TabFolder(container, SWT.NONE);
		folder.setLayoutData(gridData);
		TabItem basicInfoTabItem = new TabItem(folder, SWT.NONE); 
		basicInfoTabItem.setText("基本信息");		
		
		TabItem optionTabItem = new TabItem(folder, SWT.NONE); 
		optionTabItem.setText("业务操作");	
		


		basicInfoPage=new BusinessClassBaiscInfoPage(folder);
		optionInfoPage=new BusinessClassOptionPage(folder);
		
		folder.getItem(0).setControl(basicInfoPage);
		folder.getItem(1).setControl(optionInfoPage);
		if(hasAss){
			TabItem assTabItem = new TabItem(folder, SWT.NONE); 
			assTabItem.setText("关联关系");
			assPage=new AssociationPage(folder);
			assPage.initAssInfoPage(assList);
			folder.getItem(2).setControl(assPage);
		}
		basicInfoPage.initBasicInfoPage(obj);	
		optionInfoPage.initOptionInfoPage(obj);
	    return parent; 
  }
	
	@Override
	protected Point getInitialSize() {
		return new Point(810, 550);
	}
	
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("业务实体详细信息");
	}
	
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX |SWT.MIN ;
	}
	
	@Override
	public boolean isHelpAvailable() {
		return false;
	}
}
