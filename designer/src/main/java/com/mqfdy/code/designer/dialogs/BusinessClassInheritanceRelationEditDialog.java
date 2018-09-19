package com.mqfdy.code.designer.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessClassInheritanceRelationEditDialog.
 *
 * @author mqfdy
 */
public class BusinessClassInheritanceRelationEditDialog extends
		ModelElementEditorDialog implements IBusinessClassEditorPage {
	
	/** The inheritance. */
	private Inheritance inheritance;// 当前关系类
	
	/** The child class. */
	BusinessClass childClass = null;// 关系相关BusinessClass
	
	/** The parent class. */
	BusinessClass parentClass = null;// 关系相关BusinessClass

	/** The group persist. */
	private Group groupPersist; // 持久化策略区域
	
	/** The lbl 5. */
	Label lbl5;
	
	/** The lbl 6. */
	Label lbl6;
	
	/** The btn A. */
	Button btnA;
	
	/** The btn B. */
	Button btnB;
	
	/** The btn find A. */
	Button btnFindA;
	
	/** The btn find B. */
	Button btnFindB;
	
	/** The text ass name. */
	private Text textAssName;// 关系名
	
	/** The text ass disp name. */
	private Text textAssDispName;// 关系显示名
	
	/** The text entity A. */
	private Text textEntityA;// 实体A
	
	/** The text entity B. */
	private Text textEntityB;// 实体B
	
	/** The styled text desc. */
	StyledText styledTextDesc;
	
	/** The check one table. */
	private Button checkOneTable;// 父子实体类生成同一张表
	
	/** The check two table. */
	private Button checkTwoTable;// 父类和子类各生成表
	
	/** The event type. */
	private int eventType;

	/** The title operation. */
	private String TITLE_OPERATION = "";
	
	/** The title type. */
	private String TITLE_TYPE = "继承关系";

	/** The canvas. */
	BusinessClassCanvas canvas;// 上方画布
	
	/** The manager. */
	BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	/** The Constant DIALOG_WIDTH. */
	public static final int DIALOG_WIDTH = 700;
	
	/** The Constant DIALOG_HEIGTH. */
	public static final int DIALOG_HEIGTH = 650;

	/**
	 * Instantiates a new business class inheritance relation edit dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param inheritance
	 *            the inheritance
	 * @param parent
	 *            the parent
	 * @param eventType
	 *            the event type
	 */
	public BusinessClassInheritanceRelationEditDialog(Shell parentShell,
			Inheritance inheritance, AbstractModelElement parent, int eventType) {
		super(parentShell);
		if (inheritance == null) {
			this.inheritance = new Inheritance();
			// this.inheritance.setParent(parent);
			this.TITLE_OPERATION = "创建 ";
			if (parent instanceof ModelPackage) {
				this.inheritance.setBelongPackage((ModelPackage) parent);
			} else if (parent instanceof Diagram) {
				this.inheritance.setBelongPackage((ModelPackage) (parent
						.getParent()));
			} else if (parent instanceof SolidifyPackage) {
				this.inheritance.setBelongPackage((ModelPackage) (parent
						.getParent()));
			}
		} else {
			this.inheritance = inheritance;
			this.childClass = this.getBusinessClassA();
			this.parentClass = this.getBusinessClassB();
			this.TITLE_OPERATION = "修改 ";
		}
		this.eventType = eventType;
	}

	/**
	 * @return
	 */
	@Override
	protected Point getInitialSize() {
		// TODO Auto-generated method stub
		return new Point(DIALOG_WIDTH, DIALOG_HEIGTH);
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
	@Override
	protected Control createDialogArea(Composite parent) {
		/*
		 * 初始化窗口
		 */
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		createContent(parent);
//		addListeners();
		initControlValue();
		return parent;
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

		if (BusinessModelEvent.MODEL_ELEMENT_ADD == eventType) {
			newShell.setText(TITLE_OPERATION + TITLE_TYPE);
		}
		if (BusinessModelEvent.MODEL_ELEMENT_UPDATE == eventType) {
			newShell.setText(TITLE_OPERATION + TITLE_TYPE
					+ this.inheritance.getDisplayName());
		}
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_INHERITANCE));
	}

	/**
	 * 
	 */
	public void create() {
		super.create();
		if (BusinessModelEvent.MODEL_ELEMENT_ADD == eventType) {
			setTitle(TITLE_TYPE);
			setMessage(TITLE_OPERATION + TITLE_TYPE);
		}
		if (BusinessModelEvent.MODEL_ELEMENT_UPDATE == eventType) {
			setTitle(TITLE_TYPE);
			setMessage(TITLE_OPERATION + inheritance.getDisplayName() + "信息");
		}

	}

	/**
	 * Creates the content.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	private void createContent(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginTop = 5;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.verticalSpacing = 5;
		layout.makeColumnsEqualWidth = false;
		parent.setLayout(layout);
		GridData data = new GridData();

		// 实体展示区域
		Group topGroup = new Group(parent, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1);
		topGroup.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginLeft = 80;
		gridLayout.marginRight = 80;
		topGroup.setLayout(gridLayout);

		btnA = new Button(topGroup, SWT.NONE);
		btnA.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnB = new Button(topGroup, SWT.NONE);
		btnB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		canvas = new BusinessClassCanvas(topGroup, DIALOG_WIDTH);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2,
				1));
		if (canvas != null) {
			canvas.setBackground(new Color(null,255, 255, 255));
			canvas.changeData(childClass, parentClass, null, null, null);
		}
		

		// 关联关系信息区域
		// Composite infoComposite = new Composite(parent, SWT.NONE);
		// infoComposite.setLayoutData( new GridData(SWT.FILL, SWT.CENTER, true,
		// false,1, 1));
		// infoComposite.setLayout(layout);//必须
		Group group_info = new Group(parent, SWT.NONE);

		group_info.setText("继承关系信息");
		GridLayout gl_group_info = new GridLayout();
		gl_group_info.numColumns = 6;
		gl_group_info.marginLeft = 8;
		group_info.setLayout(gl_group_info);
		group_info.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblAssName = new Label(group_info, SWT.NONE);
		lblAssName.setText("关系名称:");
		// lblAssName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
		// false,1, 1));

		textAssName = new Text(group_info, SWT.BORDER);
		textAssName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 2, 1));

		Label lblAssDispName = new Label(group_info, SWT.NONE);
		lblAssDispName.setText("显示名:");

		textAssDispName = new Text(group_info, SWT.BORDER);
		GridData gridData_textAssDispName = new GridData(SWT.FILL);
		gridData_textAssDispName.grabExcessHorizontalSpace = true;
		textAssDispName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 2, 1));

		Label lblEntityA = new Label(group_info, SWT.NONE);
		lblEntityA.setText("子实体:");
		GridData gd_lblEntityA = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_lblEntityA.widthHint = 56;
		lblEntityA.setLayoutData(gd_lblEntityA);

		textEntityA = new Text(group_info, SWT.BORDER);
		textEntityA.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		textEntityA.setEditable(false);

//		btnFindA = new Button(group_info, SWT.NONE);
//		btnFindA.setText("   查找     ");
//		btnFindA.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
//				1, 1));

		Label lblEntityB = new Label(group_info, SWT.NONE);
		lblEntityB.setText("父实体:");
		lblEntityB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		textEntityB = new Text(group_info, SWT.BORDER);
		textEntityB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		textEntityB.setEditable(false);

//		btnFindB = new Button(group_info, SWT.NONE);
//		btnFindB.setText("   查找     ");
//		btnFindB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
//				1, 1));

		// 持久化策略层
		GridLayout persistLayout = new GridLayout();
		persistLayout.numColumns = 2;
		groupPersist = new Group(parent, SWT.NONE);
		groupPersist.setText("持久化策略");
		groupPersist.setLayout(persistLayout);
		groupPersist.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

//		checkOneTable = new Button(groupPersist, SWT.RADIO);
//		checkOneTable.setLayoutData(new GridData(1, 1, true, true, 1, 1));
//		checkOneTable.setText("父子实体类生成同一张表");

		checkTwoTable = new Button(groupPersist, SWT.RADIO);
		checkTwoTable.setLayoutData(new GridData(1, 1, true, true, 1, 1));
		checkTwoTable.setText("父类和子类各生成表");

		Label lblDesc = new Label(parent, SWT.NONE);
		lblDesc.setText("备注：");

		styledTextDesc = new StyledText(parent, SWT.BORDER);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		data.heightHint = 60;
		styledTextDesc.setLayoutData(data);
	}

//	private void addListeners() {

		/*btnFindA.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				// BusinessClassSearchDialog dialog = new
				// BusinessClassSearchDialog(getShell());
				ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
						new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS },
						manager);
				if (dialog.open() == 0) {
					BusinessClass businessClass = (BusinessClass) dialog
							.getFirstResult();
					textEntityA.setText(businessClass.getDisplayName());
					childClass = businessClass;
					inheritance.setChildClass(childClass);
					fillFieldsA();
					canvas.changeData(businessClass, null, null, null, null);
				}
			}

			public void mouseUp(MouseEvent arg0) {

			}

		});

		btnFindB.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent arg0) {
				ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
						new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS },
						manager);
				if (dialog.open() == 0) {
					BusinessClass businessClass = (BusinessClass) dialog
							.getFirstResult();
					textEntityB.setText(businessClass.getDisplayName());
					parentClass = businessClass;
					inheritance.setParentClass(parentClass);
					fillFieldsB();
					canvas.changeData(null, businessClass, null, null, null);
				}
			}

			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		this.getShell().addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void controlResized(ControlEvent event) {
				canvas.onResized(BusinessClassInheritanceRelationEditDialog.this
						.getShell().getBounds().width);
			}

		});*/
//	}

	/**
 * 初始化数据.
 *
 * @author mqfdy
 * @Date 2018-09-03 09:00
 */
	private void initData() {
		if (inheritance != null) {

			if (BusinessModelEvent.MODEL_ELEMENT_ADD == eventType) {
				// 如果是新增
				String newName = manager.generateNextInheritanceName();
				textAssName.setText(newName);
				textAssDispName.setText(newName);
			} else {
				textAssName.setText(StringUtil.convertNull2EmptyStr(inheritance
						.getName()));
				textAssDispName.setText(StringUtil
						.convertNull2EmptyStr(inheritance.getDisplayName()));
			}

			styledTextDesc.setText(StringUtil.convertNull2EmptyStr(inheritance
					.getRemark()));
			if (inheritance.getPersistencePloyParams() != null) {
				String policy = inheritance.getPersistencePloy();
				if (Inheritance.TABLE_PER_CLASS_HIERARCHY.equals(policy)) {
					checkOneTable.setSelection(true);
				}
				if (Inheritance.TABLE_PER_CONCRETECLASS.equals(policy)) {
					checkTwoTable.setSelection(true);
				}
			}
		}
		if (childClass != null) {
			textEntityA.setText(StringUtil.convertNull2EmptyStr(childClass
					.getDisplayName()));
			btnA.setText("子实体");

		}
		if (parentClass != null) {
			textEntityB.setText(StringUtil.convertNull2EmptyStr(parentClass
					.getDisplayName()));
			btnB.setText("父实体");
		}

	}

	/**
	 * Fill fields A.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fillFieldsA() {
		if (childClass != null) {
			btnA.setText("子实体");
			textEntityA.setText(StringUtil.convertNull2EmptyStr(childClass
					.getDisplayName()));
		}
	}

	/**
	 * Fill fields B.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fillFieldsB() {
		if (parentClass != null) {
			btnB.setText("父实体");
			textEntityB.setText(StringUtil.convertNull2EmptyStr(parentClass
					.getDisplayName()));
		}
	}

	/**
	 * 控制创建外键区域显示与否，true为显示；false为不显示.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new dis play create FK
	 * @Date 2018-09-03 09:00
	 */
	private void setDisPlayCreateFK(boolean b) {
		((GridData) checkOneTable.getLayoutData()).exclude = !b;
		checkOneTable.setVisible(b);
		((GridData) checkTwoTable.getLayoutData()).exclude = !b;
		checkTwoTable.setVisible(b);
		groupPersist.layout();
	}

	/**
	 * Gets the business class A.
	 *
	 * @author mqfdy
	 * @return the business class A
	 * @Date 2018-09-03 09:00
	 */
	private BusinessClass getBusinessClassA() {
		if (inheritance.getChildClass() == null) {
			return null;
		}
		BusinessClass childClass = manager.queryObjectById(new BusinessClass(),
				inheritance.getChildClass().getId());
		if (childClass == null) {
			childClass = new BusinessClass();
		}
		return childClass;
	}

	/**
	 * Gets the business class B.
	 *
	 * @author mqfdy
	 * @return the business class B
	 * @Date 2018-09-03 09:00
	 */
	private BusinessClass getBusinessClassB() {
		if (inheritance.getParentClass() == null) {
			return null;
		}
		BusinessClass parentClass = manager.queryObjectById(
				new BusinessClass(), inheritance.getParentClass().getId());
		if (parentClass == null) {
			parentClass = new BusinessClass();
		}
		return parentClass;
	}

	/**
	 * Gets the inheritance.
	 *
	 * @author mqfdy
	 * @return the inheritance
	 * @Date 2018-09-03 09:00
	 */
	public Inheritance getInheritance() {
		return inheritance;
	}

	/**
	 * 
	 */
	public void initControlValue() {
		// 初始化数据
		initData();
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		if (textAssName.getText().trim().length() == 0) {
			this.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
			return false;
		}
		if (!textAssName.getText().trim().matches("^[a-zA-Z][a-zA-Z_0-9]*$")) {
			this.setErrorMessage(ERROR_MESSAGE_NAME_RULE);
			return false;
		}
		if (textAssDispName.getText().trim().length() == 0) {
			this.setErrorMessage(ERROR_MESSAGE_DISPLAYNAME_NULLABLE);
			return false;
		}
//		if (checkOneTable.getSelection() == false
//				&& checkTwoTable.getSelection() == false) {
//			this.setErrorMessage(ERROR_MESSAGE_PERSIST_POLICY_NULLABLE);
//			return false;
//		}

		if (childClass == null || parentClass == null) {
			this.setErrorMessage(ERROR_MESSAGE_CHILD_PARENT_NULLABLE);
			return false;
		}

		return true;
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {
		// 保存数据
		inheritance.setName(textAssName.getText().trim());
		inheritance.setDisplayName(textAssDispName.getText());
		inheritance.setParentClass(parentClass);
		inheritance.setChildClass(childClass);
		String policy = "";
//		if (checkOneTable.getSelection()) {
//			policy = Inheritance.TABLE_PER_CLASS_HIERARCHY;
//		}
		if (checkTwoTable.getSelection()) {
			policy = Inheritance.TABLE_PER_CONCRETECLASS;
		}

		inheritance.setPersistencePloy(policy);
		inheritance.setRemark(styledTextDesc.getText());
		manager.businessObjectModelChanged(new BusinessModelEvent(eventType,
				inheritance));
	}

	/**
	 * Creates the buttons for button bar.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	protected void createButtonsForButtonBar(Composite composite) {
		// if(operationType.equals(OPERATION_TYPE_EDIT))
		// createButton(composite, 12000, "重构", true);
		// if(operationType.equals(OPERATION_TYPE_ADD))
		createButton(composite, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(composite, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
//		createButton(composite, APPLY_ID, APPLY_LABEL, true);
	}

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}

	/**
	 * Applyl pressed.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void applylPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			isChaged = true;
		}

	}

	/**
	 * Button pressed.
	 *
	 * @author mqfdy
	 * @param buttonId
	 *            the button id
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void buttonPressed(int buttonId) {
//		if (APPLY_ID == buttonId) {
//			applylPressed();
//		} else {
		super.buttonPressed(buttonId);
//		}
	}

	/**
	 * Validate all input.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean validateAllInput() {
		boolean isOk = this.validateInput();
		return isOk;
	}

	/**
	 * Gets the title.
	 *
	 * @author mqfdy
	 * @return the title
	 * @Date 2018-09-03 09:00
	 */
	public String getTITLE() {
		return TITLE_OPERATION + TITLE_TYPE;
	}

}
