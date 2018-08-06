package com.mqfdy.code.shareModel.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;

/**
 * 显示枚举文件信息
 * @author mqfdy
 *
 */
public class EnumMessageDialog extends TitleAreaDialog {
	private static final String TITLE = "枚举实体详细信息";
	private Object obj;
	public ImageManager imageManager=new ImageManager();
	private GridLayout layout = new GridLayout(1, false);
	public Composite container ;
	private Group basicInfoGroup;
	private Group propertyGroup;
	private Label nameLabel;
	private Text nameText;
	
	private Label displayNameLabel;
	private Text displayNameText;
	private TableViewer tableViewer;
	public Table table;
	
	public EnumMessageDialog(Shell parentShell,Object obj) {
		super(parentShell);
		this.obj=obj;
	}

	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		setTitle(TITLE);
		setTitleImage(imageManager.getImage(ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		
		basicInfoGroup=new Group(container,SWT.None);
		GridLayout layout=new GridLayout(4,false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		basicInfoGroup.setText("基本信息");
		basicInfoGroup.setLayout(layout);
		basicInfoGroup.setLayoutData(gridData);
				
		gridData = new GridData(GridData.BEGINNING);
		gridData.widthHint=60;
		nameLabel =new Label(basicInfoGroup,SWT.NONE);
		nameLabel.setText("名称:");
		nameLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		nameText =new Text(basicInfoGroup,SWT.BORDER|SWT.READ_ONLY);
		nameText.setLayoutData(gridData);
		
		gridData = new GridData(GridData.BEGINNING);
		gridData.widthHint=60;
		displayNameLabel =new Label(basicInfoGroup,SWT.NONE);
		displayNameLabel.setText("显示名:");
		displayNameLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		displayNameText =new Text(basicInfoGroup,SWT.BORDER|SWT.READ_ONLY);
		displayNameText.setLayoutData(gridData);
		
		propertyGroup=new Group(container,SWT.None);
		GridLayout layout2=new GridLayout(1,false);
		gridData = new GridData(GridData.FILL_BOTH);
		propertyGroup.setText("枚举信息");
		propertyGroup.setLayout(layout2);
		propertyGroup.setLayoutData(gridData);
		
		table = new Table(propertyGroup, SWT.BORDER|SWT.FULL_SELECTION|SWT.SINGLE);
		gridData = new GridData(GridData.FILL_BOTH);
		ScrolledComposite tableScroll = new ScrolledComposite(table,
					SWT.BORDER);
		tableScroll.setContent(table);

		tableViewer = new TableViewer(table);
		gridData.heightHint = 380;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);
		
		TableColumn name = new TableColumn(table, SWT.CENTER);
		name.setText("名称");
		name.setWidth(110);
		
		TableColumn displayName = new TableColumn(table, SWT.CENTER);
		displayName.setText("显示名");
		displayName.setWidth(110);
		
		initData();
		return parent; 
	}
	
	/**
	 * 初始化数据	
	 */
	private void initData() {
		Enumeration e=(Enumeration) obj;
		nameText.setText(e.getName());
		displayNameText.setText(e.getDisplayName());
		
		for(EnumElement ele: e.getElements()){
			  TableItem item=new TableItem(table, SWT.NONE);
			  item.setData(ele);
			  item.setText(0, ele.getKey());
			  item.setText(1, ele.getDisplayName());
		}
	}

	@Override
	protected Point getInitialSize() {
		return new Point(400, 450);
	}
	
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("查看枚举信息");
	}
	
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX |SWT.MIN ;
	}	
	
	@Override
	public boolean isHelpAvailable() {
		return false;
	}
}
