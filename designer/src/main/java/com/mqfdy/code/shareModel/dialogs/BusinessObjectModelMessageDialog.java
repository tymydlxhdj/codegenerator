package com.mqfdy.code.shareModel.dialogs;

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

/**
 * 显示选中的模型文件的信息(包含业务实体和枚举2个tab页)
 * @author mqfdy
 *
 */
public class BusinessObjectModelMessageDialog extends TitleAreaDialog {
	private static final String TITLE = "模型文件详细信息";
	public ImageManager imageManager=new ImageManager();
	private Object obj;
	public Composite container ;
	public TabFolder folder;
	public BusinessClassInfoPage bsInfoPage;
	public EnumrationInfoPage enumInfoPage;
	private GridLayout layout = new GridLayout(1, false);
	
	
	public BusinessObjectModelMessageDialog(Shell parentShell,Object obj) {
		super(parentShell);
		this.obj=obj;
	}
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		setTitle(TITLE);
		setTitleImage(imageManager.getImage(ImageKeys.IMG_DIALOG_DIAGRAM));
		
		GridData gridData=new GridData(GridData.FILL_BOTH);
		folder=new TabFolder(container, SWT.NONE);
		folder.setLayoutData(gridData);
		TabItem basicInfoTabItem = new TabItem(folder, SWT.NONE); 
		basicInfoTabItem.setText("业务实体");		
		
		TabItem optionTabItem = new TabItem(folder, SWT.NONE); 
		optionTabItem.setText("枚举");	
		
		bsInfoPage=new BusinessClassInfoPage(folder);
		enumInfoPage=new EnumrationInfoPage(folder);
		
		folder.getItem(0).setControl(bsInfoPage);
		folder.getItem(1).setControl(enumInfoPage);
		
		bsInfoPage.initData(obj);
		enumInfoPage.initData(obj);
		return parent;		
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(800, 550);
	}
	
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("模型文件详细信息");
	}
	
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX |SWT.MIN ;
	}	
	
	@Override
	public boolean isHelpAvailable() {
		return false;
	}
}
