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

// TODO: Auto-generated Javadoc
/**
 * 业务实体信息页面(包含基本信息、操作、关联关系3个tab页).
 *
 * @author mqfdy
 */
public class BusinessClassMessageDailog extends TitleAreaDialog {
	
	/** The Constant TITLE. */
	private static final String TITLE = "查看业务实体信息";
	
	/** The image manager. */
	public ImageManager imageManager=new ImageManager();
	
	/** The obj. */
	public Object obj=null;  //选中的对象
	
	/** The ass list. */
	public List<Association> assList=null;  //选中的对象
	
	/** The has ass. */
	private boolean hasAss=false;
	
	/** The layout. */
	private GridLayout layout = new GridLayout(1, false);
	
	/** The container. */
	public Composite container ;
	
	/** The folder. */
	public TabFolder folder;
	
	/** The basic info page. */
	public BusinessClassBaiscInfoPage basicInfoPage;
	
	/** The option info page. */
	public BusinessClassOptionPage optionInfoPage;
	
	/** The ass page. */
	public AssociationPage assPage;
	
	/**
	 * Instantiates a new business class message dailog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param hasAss
	 *            the has ass
	 * @param obj
	 *            the obj
	 * @param assList
	 *            the ass list
	 */
	public BusinessClassMessageDailog(Shell parentShell,boolean hasAss,Object obj,List<Association> assList) {
		super(parentShell);
		this.hasAss=hasAss;	
		this.obj=obj;
		this.assList=assList;
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
	
	/**
	 * @return
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(810, 550);
	}
	
	
	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("业务实体详细信息");
	}
	
	/**
	 * @return
	 */
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX |SWT.MIN ;
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean isHelpAvailable() {
		return false;
	}
}
