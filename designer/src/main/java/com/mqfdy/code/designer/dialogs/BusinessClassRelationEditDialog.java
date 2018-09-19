package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.dialogs.widget.ModelElementSelecterDailog;
import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.AssociationUtil;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.NavigationType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * 关联关系对话框.
 *
 * @author mqfdy
 */
public class BusinessClassRelationEditDialog extends ModelElementEditorDialog
		implements IBusinessClassEditorPage {
	
	/** The association. */
	private Association association;// 当前关系类
	
	/** The business class A. */
	private BusinessClass businessClassA = null;// 关系相关BusinessClass
	
	/** The business class B. */
	private BusinessClass businessClassB = null;// 关系相关BusinessClass
	
	/** The association type. */
	private AssociationType associationType;// 当前关系类型
	
	/** 持久化策略区域. */
	private Group groupPersist;
	
	/** The lbl table. */
	private Label lblTable;
	
	/** The lbl db field A. */
	private Label lblDbFieldA;
	
	/** The lbl db field B. */
	private Label lblDbFieldB;
	
	/** The lbl 5. */
	private Label lbl5;//占位label
	
	/** The lbl 6. */
	private Label lbl6;
	
	/** The lbl 7. */
	private Label lbl7;//占位label
	
	/** The lbl 8. */
	private Label lbl8;
	
	/** The lbl 7 1. */
	private Label lbl7_1;//占位label
	
	/** The lbl 8 1. */
	private Label lbl8_1;
	
	/** The lbl 9. */
	private Label lbl9;//占位label
	
	/** The lbl 0. */
	private Label lbl0;
	
	/** The lbl 9 1. */
	private Label lbl9_1;//占位label
	
	/** The lbl 0 1. */
	private Label lbl0_1;
	
	/** The lbl 11. */
	private Label lbl11;
	
	/** The lbl 12. */
	private Label lbl12;
	
	/** The lbl 11 1. */
	private Label lbl11_1;
	
	/** The lbl 12 1. */
	private Label lbl12_1;
	
	/** The lbl 13. */
	private Label lbl13;
	
	/** The lbl 14. */
	private Label lbl14;
	
	/** The lbl 13 1. */
	private Label lbl13_1;
	
	/** The lbl 14 1. */
	private Label lbl14_1;
	
	/** The btn A. */
	private Button btnA;//实体展示区域按钮
	
	/** The btn B. */
	private Button btnB;
	
	/** The btn find A. */
	private Button btnFindA;//查找
	
	/** The btn find B. */
	private Button btnFindB;
	
	/** The text ass name. */
	private Text textAssName;// 关系名
	
	/** The text ass disp name. */
	private Text textAssDispName;// 关系显示名
	
	/** The text entity A. */
	private Text textEntityA;// 实体A
	
	/** The text entity B. */
	private Text textEntityB;// 实体B
	
	/** 中间表. */
	private Text textTable;
	
	/** The styled text desc. */
	private NullToEmptyText styledTextDesc;
	
	/** 关联类型. */
	private Combo comboAssType;
	
	/** The check nav to B. */
	private Button checkNavToB;// 导航到B
	
	/** 级联删除B. */
	//private Button checkCascadDelB;
	/**
	 * 设为主控端
	 */
	private Button checkControlA;
	
	/** 导航到A. */
	private Button checkNavToA;
	
	/** 级联删除A. */
	//private Button checkCascadDelA;
	/**
	 * 设为主控端B
	 */
	private Button checkControlB;
	
	/** A的数据库关联字段. */
	private Combo combo_DbFieldA;//
	
	/** B的数据库关联字段. */
	private Combo combo_DbFieldB;
	
	/** 在实体A对应的数据库表中创建外键. */
	private Button checkCreateFKA;
	
	/** 在实体B对应的数据库表中创建外键. */
	private Button checkCreateFKB;
	
	/** The text FK column A. */
	private Text textFKColumnA;// A端外键字段名
	
	/** The text FK column B. */
	private Text textFKColumnB;// B端外键字段名
	
	/** The combo FK column A readonly. */
	private Combo comboFKColumnAReadonly;// ReadonlyA端外键字段名
	
	/** The combo FK column B readonly. */
	private Combo comboFKColumnBReadonly;// ReadonlyB端外键字段名
	
	/** The event type. */
	private int eventType = 0;
	
	/** The title operation. */
	private String TITLE_OPERATION = "";
	
	/** The title type. */
	private String TITLE_TYPE = "关联关系";
	
	/** 配置编辑器按钮. */
	private Button editorDeployA;
	
	/** The not null A. */
	private Button notNullA;
	
	/** 配置编辑器按钮. */
	private Button editorDeployB;
	
	/** The not null B. */
	private Button notNullB;
	
	/** A-B导航属性名. */
	private Text textNavigateToClassBRoleName;// 导航属性名 
	
	/** B-A导航属性名. */
	private Text textNavigateToClassARoleName;// 导航属性名 
	
	/** The is reverse A. */
	private boolean isReverseA;
	
	/** The is reverse B. */
	private boolean isReverseB;
	
	/** The is reverse ass. */
	private boolean isReverseAss;

	/** The canvas. */
	private BusinessClassCanvas canvas;// 上方画布

	/** The manager. */
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	/** The Constant DIALOG_WIDTH. */
	public static final int DIALOG_WIDTH = 900;
	
	/** The Constant DIALOG_HEIGTH. */
	public static final int DIALOG_HEIGTH = 730;

	/**
	 * Instantiates a new business class relation edit dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param association
	 *            the association
	 * @param parent
	 *            the parent
	 * @param eventType
	 *            the event type
	 * @param addAssType
	 *            the add ass type
	 */
	public BusinessClassRelationEditDialog(Shell parentShell,
			Association association, AbstractModelElement parent,
			int eventType, String addAssType) {
		super(parentShell);
		if (association == null) {
			this.operationType = ModelElementEditorDialog.OPERATION_TYPE_ADD;
			associationType = AssociationType.one2one;
			if (ActionTexts.ASSOCIATION_ONE2ONE_ADD.equals(addAssType)) {
				associationType = AssociationType.one2one;
			}
			if (ActionTexts.ASSOCIATION_ONE2MULT_ADD.equals(addAssType)) {
				associationType = AssociationType.one2mult;
			}
			if (ActionTexts.ASSOCIATION_MULT2ONE_ADD.equals(addAssType)) {
				associationType = AssociationType.mult2one;
			}
			if (ActionTexts.ASSOCIATION_MULT2MULT_ADD.equals(addAssType)) {
				associationType = AssociationType.mult2mult;
			}
			this.association = AssociationUtil.generateAssociation(null, null,associationType);
			TITLE_OPERATION = "创建";
			if (parent instanceof ModelPackage) {
				this.association.setBelongPackage((ModelPackage) parent);
			} else if (parent instanceof Diagram) {
				this.association.setBelongPackage((ModelPackage) (parent
						.getParent()));
			} else if (parent instanceof SolidifyPackage) {
				this.association.setBelongPackage((ModelPackage) (parent
						.getParent()));
			}
		} else if(association != null && BusinessModelEvent.MODEL_ELEMENT_ADD == eventType) {
			this.association = association;
			this.businessClassA = this.getBusinessClassA();
			this.businessClassB = this.getBusinessClassB();
			this.associationType = AssociationType
					.getAssociationType(association.getAssociationType());
			TITLE_OPERATION = "创建 ";
			operationType = ModelElementEditorDialog.OPERATION_TYPE_ADD;
		}else {
			this.association = association;
			this.businessClassA = this.getBusinessClassA();
			this.businessClassB = this.getBusinessClassB();
			this.associationType = AssociationType
					.getAssociationType(association.getAssociationType());
			TITLE_OPERATION = "修改 ";
			operationType = ModelElementEditorDialog.OPERATION_TYPE_EDIT;
		}
		this.eventType = eventType;
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
					+ this.association.getDisplayName());
		}
		Image img = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_ASSOCIATION);
		String associationType = association.getAssociationType();
		if (AssociationType.one2one.getValue().equals(associationType)) {
			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2ONE);
		} else if (AssociationType.one2mult.getValue().equals(associationType)) {
			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT);
		} else if (AssociationType.mult2one.getValue().equals(associationType)) {
			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2ONE);
		} else if (AssociationType.mult2mult.getValue().equals(associationType)) {
			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2MULT);
		}
		newShell.setImage(img);
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
		setTitleAndMessage();
		createContent(parent);
		addListeners();
		changeViewByAssociationType();
		initData();
		changeControl();
		changeCanvasDisplay();
		setEnableByStoreType();
		this.setErrorMessage(null);
		return parent;
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

		// 实体展示区域
		Group topGroup = new Group(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginLeft = 80;
		gridLayout.marginRight = 80;
		topGroup.setLayout(gridLayout);
		topGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1,
				1));

		btnA = new Button(topGroup, SWT.NONE);
		btnA.setText("实体A");
		btnA.setEnabled(false);
		btnA.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnB = new Button(topGroup, SWT.NONE);
		btnB.setText("实体B");
		btnB.setEnabled(false);
		btnB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		canvas = new BusinessClassCanvas(topGroup, 900);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		canvas.setBackground(new Color(null,255, 255, 255));
		// 关联关系信息
		Group group_info = new Group(parent, SWT.NONE);
		group_info.setText("关联关系信息");
		group_info.setLayout(new GridLayout(2, false));
		group_info.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		// 添加一个单独的composite
		Composite compBasic = new Composite(group_info, SWT.NONE);
		GridLayout basicLayout = new GridLayout();
		basicLayout.numColumns = 4;
		compBasic.setLayout(basicLayout);
		GridData basicLayoutdata = new GridData();
		basicLayoutdata.grabExcessHorizontalSpace = true;
		basicLayoutdata.horizontalAlignment = SWT.FILL;
		basicLayoutdata.horizontalSpan = 2;
		compBasic.setLayoutData(basicLayoutdata);

		GridData layoutData_normal = new GridData();
		layoutData_normal.horizontalAlignment = SWT.FILL;
		layoutData_normal.grabExcessHorizontalSpace = true;

		Label lblAssType = new Label(compBasic, SWT.NONE);
		lblAssType.setText("关联类型:");

		comboAssType = new Combo(compBasic, SWT.BORDER | SWT.FILL
				| SWT.READ_ONLY);

		comboAssType.setLayoutData(layoutData_normal);

		Label lbl1 = new Label(compBasic, SWT.NONE);
		lbl1.setText("");
		Label lbl2 = new Label(compBasic, SWT.NONE);
		lbl2.setText("");
		/*
		 * lbl1.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, true, false, 2,
		 * 1));
		 */

		Label lblAssName = new Label(compBasic, SWT.NONE);
		lblAssName.setText("关联名称:");
		textAssName = new Text(compBasic, SWT.BORDER);
		textAssName.setLayoutData(layoutData_normal);

		Label lblAssDispName = new Label(compBasic, SWT.NONE);
		lblAssDispName.setText("显示名称:");

		textAssDispName = new Text(compBasic, SWT.BORDER);
		GridData gridData_textAssDispName = new GridData(SWT.FILL);
		gridData_textAssDispName.grabExcessHorizontalSpace = true;
		textAssDispName.setLayoutData(layoutData_normal);

		// GourpA
		GridData aLayoutdata = new GridData();
		aLayoutdata.grabExcessHorizontalSpace = true;
		aLayoutdata.horizontalAlignment = SWT.FILL;
		aLayoutdata.horizontalSpan = 1;

		Group group_A = new Group(group_info, SWT.NONE);
		GridLayout gl_groupA_info = new GridLayout();
		gl_groupA_info.numColumns = 3;
		gl_groupA_info.marginLeft = 8;
		group_A.setLayout(gl_groupA_info);
		GridData groupALayoutdata = new GridData();
		groupALayoutdata.grabExcessHorizontalSpace = true;
		groupALayoutdata.horizontalAlignment = SWT.FILL;
		groupALayoutdata.horizontalSpan = 1;
		group_A.setLayoutData(groupALayoutdata);

		Label lblEntityA = new Label(group_A, SWT.NONE);
		lblEntityA.setText("实体A:");

		textEntityA = new Text(group_A, SWT.BORDER);
		textEntityA.setEditable(false);
		textEntityA.setLayoutData(aLayoutdata);

		btnFindA = new Button(group_A, SWT.NONE);
		btnFindA.setText("   查找     ");
		btnFindA.setLayoutData(aLayoutdata);

		/*checkCascadDelB = new Button(group_A, SWT.CHECK);
		checkCascadDelB.setText("级联删除B");
		checkCascadDelB.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
				false, 1, 1));*/
	/*	if(associationType.equals(AssociationType.one2mult.getValue()) && 
				"3".equals(association.getStereotype())){
			checkCascadDelB.setEnabled(false);
			checkCascadDelB.setSelection(true);
		}*/
		checkNavToB = new Button(group_A, SWT.CHECK);
		checkNavToB.setText("导航到实体B");
		checkNavToB.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
				1, 1));
		Label labell1 = new Label(group_A,SWT.NULL);
		checkControlA = new Button(group_A, SWT.RADIO);
		checkControlA.setText("设为主控端");
		checkControlA.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
				false, 1, 1));

		Label labelRoleNameA = new Label(group_A, SWT.NONE);
		labelRoleNameA.setText("导航属性名:");

		textNavigateToClassBRoleName = new Text(group_A, SWT.BORDER);
		textNavigateToClassBRoleName.setLayoutData(new GridData(SWT.FILL,
				SWT.NONE, true, false, 2, 1));

		// GourpB
		GridData bLayoutdata = new GridData();
		bLayoutdata.grabExcessHorizontalSpace = true;
		bLayoutdata.horizontalAlignment = SWT.FILL;
		bLayoutdata.horizontalSpan = 1;

		Group group_B = new Group(group_info, SWT.NONE);
		GridLayout gl_groupB_info = new GridLayout();
		gl_groupB_info.numColumns = 3;
		gl_groupB_info.marginLeft = 8;
		group_B.setLayout(gl_groupB_info);
		GridData groupBLayoutdata = new GridData();
		groupBLayoutdata.grabExcessHorizontalSpace = true;
		groupBLayoutdata.horizontalAlignment = SWT.FILL;
		groupBLayoutdata.horizontalSpan = 1;
		group_B.setLayoutData(groupBLayoutdata);

		Label lblEntityB = new Label(group_B, SWT.NONE);
		lblEntityB.setText("实体B:");

		textEntityB = new Text(group_B, SWT.BORDER);
		textEntityB.setEditable(false);
		textEntityB.setLayoutData(bLayoutdata);

		btnFindB = new Button(group_B, SWT.NONE);
		btnFindB.setText("   查找     ");
		btnFindB.setLayoutData(bLayoutdata);
		
		/*checkCascadDelA = new Button(group_B, SWT.CHECK);
		checkCascadDelA.setText("级联删除A");
		checkCascadDelA.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
				false, 1, 1));*/
		checkNavToA = new Button(group_B, SWT.CHECK);
		checkNavToA.setText("导航到实体A");
		checkNavToA.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
				1, 1));
		Label labell = new Label(group_B,SWT.NULL);
		

		checkControlB = new Button(group_B, SWT.RADIO);
		checkControlB.setText("设为主控端");
		checkControlB.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
				false, 1, 1));

		Label labelRoleNameB = new Label(group_B, SWT.NONE);
		labelRoleNameB.setText("导航属性名:");

		textNavigateToClassARoleName = new Text(group_B, SWT.BORDER);
		textNavigateToClassARoleName.setLayoutData(new GridData(SWT.FILL,
				SWT.NONE, true, false, 2, 1));

		// 持久化策略层
		GridLayout persistLayout = new GridLayout();
		persistLayout.numColumns = 8;
		groupPersist = new Group(parent, SWT.NONE);
		groupPersist.setText("持久化策略");
		groupPersist.setLayout(persistLayout);
		GridData persistLayoutdata = new GridData();
		persistLayoutdata.grabExcessHorizontalSpace = true;
		persistLayoutdata.horizontalAlignment = SWT.FILL;
		persistLayoutdata.horizontalSpan = 1;
		groupPersist.setLayoutData(persistLayoutdata);

		GridData persistNormallayoutData = new GridData();
		persistNormallayoutData.horizontalAlignment = SWT.FILL;
		persistNormallayoutData.grabExcessHorizontalSpace = true;

		lblTable = new Label(groupPersist, SWT.NONE);
		lblTable.setText("中间表名：");

		textTable = new Text(groupPersist, SWT.SINGLE | SWT.BORDER);
		textTable.setLayoutData(persistNormallayoutData);

		lbl5 = new Label(groupPersist, SWT.NONE);
		lbl6 = new Label(groupPersist, SWT.NONE);

		lbl7 = new Label(groupPersist, SWT.NONE);
		lbl8 = new Label(groupPersist, SWT.NONE);
		lbl7_1 = new Label(groupPersist, SWT.NONE);
		lbl8_1 = new Label(groupPersist, SWT.NONE);

		
		lblDbFieldA = new Label(groupPersist, SWT.NONE);
		lblDbFieldA.setText("实体A数据库关联字段：");
		combo_DbFieldA = new Combo(groupPersist, SWT.READ_ONLY);
		combo_DbFieldA.setLayoutData(persistNormallayoutData);
		lbl9 = new Label(groupPersist, SWT.NONE);
		lbl9_1 = new Label(groupPersist, SWT.NONE);

		lblDbFieldB = new Label(groupPersist, SWT.NONE);
		lblDbFieldB.setText("实体B数据库关联字段:");

		combo_DbFieldB = new Combo(groupPersist, SWT.READ_ONLY);
		combo_DbFieldB.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
				false, 1, 1));
		lbl0 = new Label(groupPersist, SWT.NONE);
		lbl0_1 = new Label(groupPersist, SWT.NONE);

		checkCreateFKA = new Button(groupPersist, SWT.RADIO);
		checkCreateFKA.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 2, 1));
		checkCreateFKA.setText("在实体A对应的数据库表中创建外键");
		lbl11 = new  Label(groupPersist, SWT.NONE);
		////
		lbl11_1 = new  Label(groupPersist, SWT.NONE);
		checkCreateFKB = new Button(groupPersist, SWT.RADIO);
		checkCreateFKB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 2, 1));
		checkCreateFKB.setText("在实体B对应的数据库表中创建外键");
		lbl12 = new Label(groupPersist, SWT.NONE);
		/////
		lbl12_1 = new Label(groupPersist, SWT.NONE);
		Label labelFKA = new Label(groupPersist, SWT.NONE);
		labelFKA.setText("外键字段名:");
		
		textFKColumnA = new Text(groupPersist, SWT.BORDER);
		textFKColumnA.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		comboFKColumnAReadonly = new Combo(groupPersist, SWT.READ_ONLY);
		comboFKColumnAReadonly.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		// add
		editorDeployA = new Button(groupPersist, SWT.NONE);
		editorDeployA.setText("编辑器...");
		notNullA = new Button(groupPersist, SWT.CHECK);
		notNullA.setText("非空");
		lbl13 = new Label(groupPersist, SWT.NONE);
		////
		lbl13_1 = new Label(groupPersist, SWT.NONE);
		Label labelFKB = new Label(groupPersist, SWT.NONE);
		labelFKB.setText("外键字段名:");
		textFKColumnB = new Text(groupPersist, SWT.BORDER);
		textFKColumnB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		comboFKColumnBReadonly = new Combo(groupPersist, SWT.READ_ONLY);
		comboFKColumnBReadonly.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		editorDeployB = new Button(groupPersist, SWT.NONE);
		editorDeployB.setText("编辑器...");
		notNullB = new Button(groupPersist, SWT.CHECK);
		notNullB.setText("非空");
		// add
		lbl14 = new Label(groupPersist, SWT.NONE);
		////
		lbl14_1 = new Label(groupPersist, SWT.NONE);
		Label lblDesc = new Label(parent, SWT.NONE);
		lblDesc.setText("备注：");

		styledTextDesc = new NullToEmptyText(parent, SWT.BORDER | SWT.MULTI
				| SWT.VERTICAL | SWT.WRAP);
		styledTextDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		group_info.layout();
		group_A.layout();
		group_B.layout();
	}

	/**
	 * 初始化数据.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initData() {
		chooseCombo();
		fillFieldsA();
		fillFieldsB();
		fillBasicInfo();
	}

	/**
	 * Adds the listeners.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void addListeners() {
		comboAssType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

			public void widgetSelected(SelectionEvent sel) {
				String showText = comboAssType.getText();
				AssociationType type = (AssociationType) comboAssType
						.getData(showText);
				if (!isAssociationTypeAllowChange(type)) {
					// 不允许切换
					chooseCombo();
					return;
				}
				if(!associationType.equals(type))
					association.setEditor(null);
				
				associationType = type;
				changeViewByAssociationType();
			}
		});

		btnFindA.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent arg0) {
				boolean includedBuildIn = isAllowChooseBuildIn(businessClassB, btnFindA);
				boolean includedReverse = isAllowChooseReverse(businessClassB, btnFindA);
				ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
						new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS,
								IModelElement.MODEL_TYPE_REFRENCE }, manager, includedBuildIn,includedReverse);
				if (dialog.open() == 0) {
					// 判断自关联

					AbstractModelElement object = (AbstractModelElement) dialog.getFirstResult();
					if (object instanceof ReferenceObject) {
						ReferenceObject ref = (ReferenceObject) object;
						object = ref.getReferenceObject();
						businessClassA = (BusinessClass) object;
					}
					textEntityA.setText(object.getName());
					businessClassA = (BusinessClass) object;
					association.setClassA(businessClassA);
					if(!validateByRelationType()){
						return;
					}
					fillFieldsA();
					changeTableName();
					setEditableByAssciationType();
					changeCanvasDisplay();
				}
			}

			public void mouseUp(MouseEvent arg0) {

			}

		});

		checkNavToB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeRoleNameByNavi();
				changeCanvasDisplay();
				changeCreateFKByType();
				// changeCheckBoxEnable();
			}
		});

		btnFindB.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent arg0) {

				boolean includedBuildIn = isAllowChooseBuildIn(businessClassA, btnFindB);
				boolean includedReverse = isAllowChooseReverse(businessClassA, btnFindB);
				ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
						new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS,
								IModelElement.MODEL_TYPE_REFRENCE }, manager, includedBuildIn,includedReverse);
				if (dialog.open() == 0) {

					// 判断自关联

					AbstractModelElement object = (AbstractModelElement) dialog
							.getFirstResult();
					if (object instanceof ReferenceObject) {
						ReferenceObject ref = (ReferenceObject) object;
						object = ref.getReferenceObject();
						businessClassB = (BusinessClass) object;
					}

					textEntityB.setText(object.getName());
					businessClassB = (BusinessClass) object;
					association.setClassB(businessClassB);
					if(!validateByRelationType()){
						return;
					}
					fillFieldsB();// 填充实体B部分
					changeTableName();// 更改表名
					setEditableByAssciationType();
					changeCanvasDisplay();
				}
			}

			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		checkNavToA.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeRoleNameByNavi();
				changeCanvasDisplay();
				changeCreateFKByType();
				// changeCheckBoxEnable();
			}
		});

		checkControlA.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkControlA.setSelection(true);
				checkControlB.setSelection(false);
				changeCreateFKByType();
			}
		});

		checkControlB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkControlB.setSelection(true);
				checkControlA.setSelection(false);
				changeCreateFKByType();
			}
		});

		this.getShell().addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void controlResized(ControlEvent event) {
//				canvas.onResized(BusinessClassRelationEditDialog.this
//						.getShell().getBounds().width);
			}

		});

		textAssName.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				setErrorMessage(null);
				if (CheckerUtil.checkSql(textAssName.getText())) {
					setErrorMessage(ERROR_MESSAGE_NAME_SQL);
				}
			}

		});

		checkCreateFKA.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				textFKA.setEditable(true);
				if(businessClassB != null && 
						BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
						&& businessClassA != null && 
								BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
					setFKColumnDisPlay();
					comboFKColumnAReadonly.setEnabled(true);
					comboFKColumnBReadonly.setEnabled(false);
					comboFKColumnBReadonly.select(0);
					return;
				}
				setFKColumnDisPlay();
				textFKColumnA.setEnabled(true);
				if (businessClassB != null) {
					if (textFKColumnA.getText() == null
							|| textFKColumnA.getText().equals("")) {
						textFKColumnA.setText((businessClassB.getTableName().toLowerCase(Locale.getDefault()) + "_id")
								.toUpperCase(Locale.getDefault()));
					}
				}
//				textFKB.setEditable(false);
				textFKColumnB.setEnabled(false);
				textFKColumnB.setText("");
			}
		});

		checkCreateFKB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(businessClassB != null && 
						BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
						&& businessClassA != null && 
								BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
					setFKColumnDisPlay();
					comboFKColumnAReadonly.setEnabled(false);
					comboFKColumnBReadonly.setEnabled(true);
					comboFKColumnAReadonly.select(0);
					return;
				}
				setFKColumnDisPlay();
				textFKColumnA.setEnabled(false);
				textFKColumnA.setText("");
				textFKColumnB.setEnabled(true);
				if (businessClassA != null) {
					if (textFKColumnB.getText() == null|| textFKColumnB.getText().equals("")) {
						textFKColumnB.setText((businessClassA.getTableName().toLowerCase(Locale.getDefault()) + "_id"));
					}
				}
			}
		});
		editorDeployA.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDown(MouseEvent e) {
				FkEditorDialog dialog;
				if(association.getEditor() != null)
					dialog = new FkEditorDialog(false,getShell(), association.getEditor(), association);
				else
					dialog = new FkEditorDialog(false,getShell(), association);
				if(checkCreateFKA.getSelection()){
					if (dialog.open() == 0) {
					
					}
				}
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		editorDeployB.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDown(MouseEvent e) {
				FkEditorDialog dialog;
				if(association.getEditor() != null)
					dialog = new FkEditorDialog(true,getShell(), association.getEditor(), association);
				else
					dialog = new FkEditorDialog(true,getShell(), association);
				if (dialog.open() == 0) {
				
				}
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * 当选择业务实体时，如果当前是内置实体，则更改导航关系.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeNavi() {
		checkNavToB.setEnabled(true);
		checkNavToA.setEnabled(true);
		if (businessClassA != null
				&& (BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype()) || BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassA.getStereotype()))) {
			checkNavToB.setEnabled(false);
			checkNavToA.setEnabled(false);
			checkNavToB.setSelection(false);
			checkNavToA.setSelection(true);
			return;
		}else{
			checkNavToB.setEnabled(true);
			checkNavToB.setSelection(true);
		}
		if (businessClassB != null
				&& (BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassB.getStereotype()) || BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassB.getStereotype()))) {
			checkNavToB.setEnabled(false);
			checkNavToA.setEnabled(false);
			checkNavToB.setSelection(true);
			checkNavToA.setSelection(false);
			return;
		}else{
			checkNavToA.setEnabled(true);
			checkNavToA.setSelection(true);
		}
		
		
		if (AssociationType.one2one.equals(associationType)) {
			checkNavToB.setSelection(true);
			checkNavToA.setSelection(false);
			checkNavToB.setEnabled(true);
		}
		else if (AssociationType.one2mult.equals(associationType)) {
			checkNavToB.setSelection(true);
			checkNavToA.setSelection(true);
			checkNavToB.setEnabled(true);
			checkNavToA.setEnabled(true);
		}
		else if (AssociationType.mult2one.equals(associationType)) {
			checkNavToB.setSelection(true);
			checkNavToA.setSelection(true);
			checkNavToB.setEnabled(true);
			checkNavToA.setEnabled(true);
		}
		else if (AssociationType.mult2mult.equals(associationType)) {
			checkNavToB.setSelection(true);
			checkNavToA.setSelection(true);
			checkNavToB.setEnabled(false);
			checkNavToA.setEnabled(false);
		}
		
		
	}

	/**
	 * 一对一关系初始化时默认是A端主控.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeControl() {
		if (AssociationType.one2one.equals(this.associationType) || AssociationType.mult2mult.equals(this.associationType)) {
			if (checkNavToB.getSelection() != checkNavToA.getSelection()) {
				setEditableControl(false);
			} else {
				setEditableControl(true);
			}
//			if(businessClassA != null && businessClassB != null 
//					&& (IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
//					|| IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype()))
//							){
//				setEditableControl(false);
//			}
		}
	}

	/**
	 * Gets the role name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @param bc
	 *            the bc
	 * @return the role name
	 * @Date 2018-09-03 09:00
	 */
	private String getRoleName(String name ,BusinessClass bc){
		if(name.trim().equalsIgnoreCase(bc.getName())){
			name = name+"_1";
			return name;
		}else{
			return name;
		}
	}
	
	/**
	 * 根据导航关系设置导航属性名.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeRoleNameByNavi() {
		if (checkNavToB != null && checkNavToB.getSelection()) {
			textNavigateToClassBRoleName.setEditable(true);
			if (textNavigateToClassBRoleName.getText() == null
					|| "".equals(textNavigateToClassBRoleName.getText())) {
				// 如果是空则，自动计算名称
				if (businessClassA != null
						&& businessClassB != null
						&& businessClassA.getId()
								.equals(businessClassB.getId())) {
					// 自关联
					if (AssociationType.one2mult.equals(associationType)) {
						String name = AssociationUtil.getNewRoleName(businessClassA, "children");
						textNavigateToClassBRoleName.setText(getRoleName(name ,businessClassA));
					
					}

					if (AssociationType.mult2one.equals(associationType)) {
						String name = AssociationUtil.getNewRoleName(businessClassA, "parent");
						textNavigateToClassBRoleName.setText(getRoleName(name ,businessClassA));
				
					}
				} else {
					// 不是自关联
					if (businessClassB != null && businessClassA!=null) {
						if (AssociationType.one2one.equals(associationType)) {
							textNavigateToClassBRoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassA, businessClassB
													.getName().toLowerCase(Locale.getDefault())));
						}
						if (AssociationType.one2mult.equals(associationType)) {
							textNavigateToClassBRoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassA, businessClassB
													.getName().toLowerCase(Locale.getDefault())
													+ "s"));
						}

						if (AssociationType.mult2one.equals(associationType)) {
							textNavigateToClassBRoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassA, businessClassB
													.getName().toLowerCase(Locale.getDefault())));
						}
						if (AssociationType.mult2mult.equals(associationType)) {
							textNavigateToClassBRoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassA, businessClassB
													.getName().toLowerCase(Locale.getDefault())
													+ "s"));
						}
					}
				}
			}
		} else {
			textNavigateToClassBRoleName.setEditable(false);
			textNavigateToClassBRoleName.setText("");
		}

		if (checkNavToA != null && checkNavToA.getSelection()) {
			textNavigateToClassARoleName.setEditable(true);
			if (textNavigateToClassARoleName.getText() == null
					|| "".equals(textNavigateToClassARoleName.getText())) {
				// 如果是空则，自动计算名称
				if (businessClassA != null
						&& businessClassB != null
						&& businessClassA.getId()
								.equals(businessClassB.getId())) {
					// 自关联
					if (AssociationType.one2mult.equals(associationType)) {
						String name = AssociationUtil.getNewRoleName(businessClassB, "parent");
						textNavigateToClassARoleName.setText(getRoleName(name, businessClassB));
					}

					if (AssociationType.mult2one.equals(associationType)) {
						String name = AssociationUtil.getNewRoleName(businessClassB, "children");
						textNavigateToClassARoleName.setText(getRoleName(name, businessClassB));
					}
				} else {
					if (businessClassB != null && businessClassA!=null) {
						if (AssociationType.one2one.equals(associationType)) {
							textNavigateToClassARoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassB, businessClassA
													.getName().toLowerCase(Locale.getDefault())));
						}
						if (AssociationType.one2mult.equals(associationType)) {
							textNavigateToClassARoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassB, businessClassA
													.getName().toLowerCase(Locale.getDefault())));
						}

						if (AssociationType.mult2one.equals(associationType)) {
							textNavigateToClassARoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassB, businessClassA
													.getName().toLowerCase(Locale.getDefault())
													+ "s"));
						}
						if (AssociationType.mult2mult.equals(associationType)) {
							textNavigateToClassARoleName
									.setText(AssociationUtil.getNewRoleName(
											businessClassB, businessClassA
													.getName().toLowerCase(Locale.getDefault())
													+ "s"));
						}
					}
				}
			}
		} else {
			textNavigateToClassARoleName.setEditable(false);
			textNavigateToClassARoleName.setText("");
		}
	}

	/**
	 * Change control by build in.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeControlByBuildIn() {
		setEditableControl(true);
		if (businessClassA != null
				&& BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassA
						.getStereotype())) {
			setEditableControl(false);
			checkControlA.setSelection(false);
			checkControlB.setSelection(true);
			return;
		}
		if (businessClassB != null
				&& BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassB
						.getStereotype())) {
			setEditableControl(false);
			checkControlA.setSelection(true);
			checkControlB.setSelection(false);
			return;
		}
		if (AssociationType.one2one.equals(associationType)) {
			setEditableControl(false);
		}
	}
	
	/**
	 * Sets the FK combo A display.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new FK combo A display
	 * @Date 2018-09-03 09:00
	 */
	private void setFKComboADisplay(boolean b){
		if(comboFKColumnAReadonly.getVisible()){
			comboFKColumnAReadonly.setEnabled(b);
			if(!b){
				comboFKColumnAReadonly.select(0);
			}
		}
	}
	
	/**
	 * Sets the FK combo B display.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new FK combo B display
	 * @Date 2018-09-03 09:00
	 */
	private void setFKComboBDisplay(boolean b){
		if(comboFKColumnBReadonly.getVisible()){
			comboFKColumnBReadonly.setEnabled(b);
			if(!b){
				comboFKColumnBReadonly.select(0);
			}
		}
	}
	
	/**
	 * Sets the FK text A display.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new FK text A display
	 * @Date 2018-09-03 09:00
	 */
	private void setFKTextADisplay(boolean b){
		if(textFKColumnA.getVisible()){
			textFKColumnA.setEnabled(b);
			textFKColumnA.setText("");
		}
	}
	
	/**
	 * Sets the FK text B display.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new FK text B display
	 * @Date 2018-09-03 09:00
	 */
	private void setFKTextBDisplay(boolean b){
		if(textFKColumnB.getVisible()){
			textFKColumnB.setEnabled(b);
			textFKColumnB.setText("");
		}
	}

	/**
	 * Change create FK by type.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeCreateFKByType() {
		this.setErrorMessage(null);
		if(IModelElement.STEREOTYPE_REVERSE.equals(association.getStereotype())){
			setEditableFK(false, false);
			return;
		}
		setEditableFK(true, true);
		setEditableCascade(true, true);
		
		setFKColumnDisPlay();
		
		if (businessClassA != null
				&& BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())
				&& !AssociationType.mult2mult.equals(associationType)) {
			//如果classA是反向模型，则直接将A端的外键选择控件置为combo
			setFKTextADisplay(false);
			setFKComboADisplay(true);
		}else{
			
		}
		
		if (businessClassB != null
				&& BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
				&& !AssociationType.mult2mult.equals(associationType)) {
			//如果classB是反向模型，则直接将B端的外键选择控件置为combo
			setFKTextADisplay(false);
			setFKTextBDisplay(false);
			setFKComboBDisplay(true);
		}
		
		if (businessClassA != null
				&& BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype())) {
			setEditableFK(false, false);
			setEditableCascade(false, false);
			checkCreateFKB.setSelection(true);
			checkCreateFKA.setSelection(false);
			setFKTextADisplay(false);
			setFKTextBDisplay(true);
			setFKComboADisplay(false);
			setFKComboBDisplay(true);
			if (textFKColumnB.getText() == null || textFKColumnB.getText().equals("")
					|| getFKColumnName(Association.FOREIGNKEYCOLUMNINB) == null) {
				textFKColumnB.setText((businessClassA.getName() + "_id")
						.toUpperCase(Locale.getDefault()));
			}

			return;
		}

		if (businessClassB != null
				&& BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassB
						.getStereotype())) {
			setEditableFK(false, false);
			setEditableCascade(false, false);
			checkCreateFKB.setSelection(false);
			checkCreateFKA.setSelection(true);
			setFKTextBDisplay(false);
			setFKTextADisplay(true);
			setFKComboBDisplay(false);
			setFKComboADisplay(true);
			if (textFKColumnA.getText() == null || textFKColumnA.getText().equals("")
					|| getFKColumnName(Association.FOREIGNKEYCOLUMNINA) == null) {
				textFKColumnA.setText((businessClassB.getName() + "_id")
						.toUpperCase(Locale.getDefault()));
			}

			return;
		}

		if(businessClassA != null
				&& BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassA.getStereotype())){
			//当业务实体是引用模型时，不允许级联删除
			setEditableCascade(false, false);
		}
		
		if(businessClassB != null
				&& BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassB.getStereotype())){
			setEditableCascade(false, false);
		}
		// 显示控制 add by xuran
		if (AssociationType.one2one.equals(associationType)) {
			// 一对一时不允许用户手动设置主控端，根据用户选择的导航关系自动确定主控端
			setEditableFK(false, false);
			setEditableControl(false);
//			setEditableCascade(true, true);
//			checkCreateFKA.setSelection(true);
//			if (businessClassB != null) {
//				if (textFKA.getText() == null || textFKA.getText().equals("")
//						|| getFKColumnName(Association.FOREIGNKEYCOLUMNINA) == null) {
//					textFKA.setText((businessClassB.getName() + "_id")
//							.toUpperCase(Locale.getDefault()));
//				}
//			}
//			checkCreateFKB.setSelection(false);
//			textFKB.setEditable(false);
//			textFKB.setText("");

			if (checkNavToB.getSelection() == checkNavToA.getSelection()) {
				// 一对一 双向导航
				setEditableControl(true);
				if(businessClassA != null && businessClassB != null &&
						IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())
						&& (BusinessModelUtil.isCustomObjectModel(businessClassB))
								){
					
					checkCreateFKA.setEnabled(false);
					checkCreateFKB.setEnabled(false);

					checkCreateFKA.setSelection(false);
					checkCreateFKB.setSelection(true);
					
					//textFKColumnA.setEnabled(false);
					//textFKColumnB.setEnabled(true);

					//comboFKColumnAReadonly.select(0);
					//comboFKColumnAReadonly.setEnabled(false);
					//comboFKColumnBReadonly.setEnabled(true);

					if (textFKColumnB.getText() == null
							|| textFKColumnB.getText().equals("") || getFKColumnName(Association.FOREIGNKEYCOLUMNINB) == null) {
						if(businessClassA != null){
							textFKColumnB.setText((businessClassA.getName() + "_id")
								.toUpperCase(Locale.getDefault()));
							textFKColumnA.setText("");
						}
					}
				} else if(businessClassA != null && businessClassB != null &&
						IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
						&& (BusinessModelUtil.isCustomObjectModel(businessClassA))
								){
					checkCreateFKA.setEnabled(false);
					checkCreateFKB.setEnabled(false);

					checkCreateFKA.setSelection(true);
					checkCreateFKB.setSelection(false);

					setFKTextADisplay(true);
					setFKTextBDisplay(false);
					
					setFKComboADisplay(true);
					setFKComboBDisplay(false);
					
					if (textFKColumnA.getText() == null
							|| textFKColumnA.getText().equals("") || getFKColumnName(Association.FOREIGNKEYCOLUMNINA) == null) {
						if(businessClassB != null){
							textFKColumnA.setText((businessClassB.getName() + "_id")
								.toUpperCase(Locale.getDefault()));
							textFKColumnB.setText("");
						}
					}
					
				}
				
				if (checkControlA.getSelection()) {
					// 如果主控端在A
					checkCreateFKA.setEnabled(true);
					checkCreateFKA.setSelection(true);
					checkCreateFKB.setEnabled(false);
					checkCreateFKB.setSelection(false);

					if(businessClassA != null && 
									BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
						setFKComboADisplay(true);
						setFKComboBDisplay(false);

						setFKTextADisplay(false);
						setFKTextBDisplay(false);
					}
					else{
						setFKComboADisplay(false);
						setFKComboBDisplay(false);
						
						setFKTextADisplay(true);
						setFKTextBDisplay(false);
						
						if (textFKColumnA.getText() == null
								|| textFKColumnA.getText().equals("") || getFKColumnName(Association.FOREIGNKEYCOLUMNINA) == null) {
							if(businessClassB != null)
								textFKColumnA.setText((businessClassB.getName() + "_id")
									.toUpperCase(Locale.getDefault()));
								textFKColumnB.setText("");
						}
					}
				}
				if (checkControlB.getSelection()) {
					// 如果主控端在B
					checkCreateFKA.setEnabled(false);
					checkCreateFKA.setSelection(false);
					checkCreateFKB.setEnabled(true);
					checkCreateFKB.setSelection(true);

					if(businessClassB != null && 
							BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())) {
						setFKComboADisplay(false);
						setFKComboBDisplay(true);
						
						setFKTextADisplay(false);
						setFKTextBDisplay(false);
					}
					else{
						setFKComboADisplay(false);
						setFKComboBDisplay(false);
						setFKTextADisplay(false);
						setFKTextBDisplay(true);

						if (textFKColumnB.getText() == null
								|| textFKColumnB.getText().equals("") || getFKColumnName(Association.FOREIGNKEYCOLUMNINB) == null) {
							if(businessClassA != null)
								textFKColumnB.setText((businessClassA.getName() + "_id")
									.toUpperCase(Locale.getDefault()));
								textFKColumnA.setText("");
						}
					}
				}
			}

			if (checkNavToB.getSelection() && !checkNavToA.getSelection()) {
				// 一对一 A2B导航
				checkCreateFKA.setEnabled(true);
				checkCreateFKB.setEnabled(false);
				checkCreateFKA.setSelection(true);
				checkCreateFKB.setSelection(false);
				checkControlA.setSelection(true);
				checkControlA.setEnabled(true);
				checkControlB.setSelection(false);
				checkControlB.setEnabled(false);

				setFKTextBDisplay(false);

				if(businessClassA != null && businessClassB != null && IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())
						&& !checkNavToA.getSelection()
					
								){
					//条件：A是反向，B是自定义，A2B
					changeRoleNameByNavi();

					setFKTextADisplay(false);
					setFKComboADisplay(true);
					setFKComboBDisplay(false);
					
				}else if(businessClassA != null && businessClassB != null 
						&& IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
								){
					//条件：A是自定义，B是反向，A2B
					checkCreateFKB.setSelection(false);
					checkCreateFKA.setSelection(true);

					setFKTextADisplay(true);
					setFKComboADisplay(false);
					setFKComboBDisplay(false);
					
					if (businessClassB != null) {
						if (textFKColumnA.getText() == null
								|| textFKColumnA.getText().equals("")) {
							textFKColumnA.setText((businessClassB.getName() + "_id")
									.toUpperCase(Locale.getDefault()));
							textFKColumnB.setText("");
						}
					}
					
				}else{
					checkControlA.setSelection(true);
					checkControlB.setSelection(false);
					if (businessClassB != null) {
						
						setFKComboADisplay(false);
						setFKComboBDisplay(false);
						setFKTextADisplay(true);
						
						if (textFKColumnA.getText() == null
								|| textFKColumnA.getText().equals("") || getFKColumnName(Association.FOREIGNKEYCOLUMNINA) == null) {
							textFKColumnA.setText((businessClassB.getName() + "_id")
									.toUpperCase(Locale.getDefault()));
							textFKColumnB.setText("");
						}
						
					}
					
					if(businessClassA == null && businessClassB == null){
						setFKTextBDisplay(false);
						setFKComboBDisplay(false);
					}
				}
			}
			if (checkNavToA.getSelection() && !checkNavToB.getSelection()) {
				// 一对一B2A单向导航
				checkCreateFKA.setSelection(false);
				checkCreateFKB.setSelection(true);
				checkControlA.setSelection(false);
				checkControlB.setSelection(true);
				
				if(businessClassA != null && businessClassB != null 
						&& !checkNavToB.getSelection()
						&& IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
						&& (BusinessModelUtil.isCustomObjectModel(businessClassA))
								){
					//条件：A是自定义，B是反向，B2A
					changeRoleNameByNavi();
					setFKTextADisplay(false);
					setFKTextBDisplay(false);
					setFKComboADisplay(false);
					setFKComboBDisplay(true);
				}
				else if(businessClassA != null && businessClassB != null 
						&& IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())
						&& (BusinessModelUtil.isCustomObjectModel(businessClassB))
								){
					//条件：Ａ是反向，Ｂ是自定义，Ｂ２Ａ
					checkCreateFKA.setSelection(false);
					checkCreateFKB.setSelection(true);
					setFKTextADisplay(false);
					setFKTextBDisplay(true);
					setFKComboADisplay(false);
					setFKComboBDisplay(true);
					if (businessClassA != null) {
						if (textFKColumnB.getText() == null
								|| textFKColumnB.getText().equals("")) {
							textFKColumnB.setText((businessClassA.getName() + "_id")
									.toUpperCase(Locale.getDefault()));
							textFKColumnA.setText("");
						}
					}
					
				}else{
					if (businessClassA != null) {
						setFKTextADisplay(false);
						setFKTextBDisplay(true);
						setFKComboADisplay(false);
						setFKComboBDisplay(true);
			
						if (textFKColumnB.getText() == null
								|| textFKColumnB.getText().equals("") || getFKColumnName(Association.FOREIGNKEYCOLUMNINB) == null) {
							textFKColumnB.setText((businessClassA.getName() + "_id").toUpperCase(Locale.getDefault()));
							textFKColumnA.setText("");
						}
						
					}
					checkControlA.setSelection(false);
					checkControlB.setSelection(true);
				}
			}
		}
		else if (AssociationType.one2mult.equals(associationType)) {
			setEditableFK(false, false);
//			if (checkNavToB.getSelection()) {
//				setEditableCascade(true, false);
//			} else {
//				setEditableCascade(false, false);
//			}
			checkCreateFKA.setSelection(false);
			checkCreateFKB.setSelection(true);
			
			setFKTextADisplay(false);
			setFKTextBDisplay(true);
			setFKComboADisplay(false);
			setFKComboBDisplay(true);
			
			if (businessClassA != null) {
				if (textFKColumnB.getText() == null || textFKColumnB.getText().equals("")
						|| getFKColumnName(Association.FOREIGNKEYCOLUMNINB) == null) {
					textFKColumnB.setText((businessClassA.getName() + "_id")
							.toUpperCase(Locale.getDefault()));
					textFKColumnA.setText("");
				}
			}
		}
		else if (AssociationType.mult2one.equals(associationType)) {
			setEditableFK(false, false);
//			if (checkNavToA.getSelection()) {
//				setEditableCascade(false, true);
//			} else {
//				setEditableCascade(false, false);
//			}

			checkCreateFKA.setSelection(true);
			checkCreateFKB.setSelection(false);
			
			setFKTextADisplay(true);
			setFKTextBDisplay(false);
			setFKComboADisplay(true);
			setFKComboBDisplay(false);
			if (businessClassB != null) {
				if (textFKColumnA.getText() == null || textFKColumnA.getText().equals("")
						|| getFKColumnName(Association.FOREIGNKEYCOLUMNINA) == null) {
					textFKColumnA.setText((businessClassB.getName() + "_id")
							.toUpperCase(Locale.getDefault()));
					textFKColumnB.setText("");
				}
			}
		}
		else if (AssociationType.mult2mult.equals(associationType)) {
			fillFieldsA();
			fillFieldsB();
//			setEditableCascade(true, true);
			setFKTextADisplay(true);
			setFKTextBDisplay(true);
			setFKComboADisplay(true);
			setFKComboBDisplay(true);
			if (businessClassB != null) {
				if (textFKColumnA.getText() == null || textFKColumnA.getText().equals("")
						|| getFKColumnName(Association.FOREIGNKEYCOLUMNINA) == null) {
					textFKColumnA.setText((businessClassB.getName() + "_id")
							.toUpperCase(Locale.getDefault()));
				}
			}
			if (businessClassA != null) {
				if (textFKColumnB.getText() == null || textFKColumnB.getText().equals("")
						|| getFKColumnName(Association.FOREIGNKEYCOLUMNINB) == null) {
					textFKColumnB.setText((businessClassA.getName() + "_id")
							.toUpperCase(Locale.getDefault()));
				}

			}
		}

		// 判断自关联
		if (businessClassA != null && businessClassB != null
				&& businessClassA.getId().equals(businessClassB.getId())) {
			if (AssociationType.mult2one.equals(associationType)) {
				textFKColumnA.setText("PARENT_ID");
			}
			if (AssociationType.one2mult.equals(associationType)) {
				textFKColumnB.setText("PARENT_ID");
			}
		}
//		if(businessClassA != null && businessClassB != null 
//				&& IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())
//				&& IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())){
//		}
	}
	
	/**
	 * 根据关联关系类型和实体类型判断合法性.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean validateByRelationType(){
		if(businessClassA!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassB.getStereotype())){
			//引用-引用
			this.setErrorMessage("引用实体和引用实体之间不能建立关联关系");
			return false;
		}
		if(businessClassA!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassB.getStereotype())){
			//内置-内置
			this.setErrorMessage("内置实体和内置实体之间不能建立关联关系");
			return false;
		}
		if(businessClassA!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassB.getStereotype())){
			//引用-内置
			this.setErrorMessage("引用实体和内置实体之间不能建立关联关系");
			return false;
		}
		if(businessClassA!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassB.getStereotype())){
			//内置-引用
			this.setErrorMessage("内置实体和引用实体之间不能建立关联关系");
			return false;
		}
		
		if (AssociationType.one2one.equals(associationType)) {
			
		}
		else if (AssociationType.one2mult.equals(associationType)) {
			
		}
		else if (AssociationType.mult2one.equals(associationType)) {
			
		}
		else if (AssociationType.mult2mult.equals(associationType)) {
			if(businessClassA!=null && IModelElement.STEREOTYPE_CUSTOM.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassB.getStereotype())){
				//自定义-引用
				this.setErrorMessage("自定义实体和引用实体之间不能建立多对多关系");
				return false;
			}
			if(businessClassA!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_CUSTOM.equals(businessClassB.getStereotype())){
				//引用-自定义
				this.setErrorMessage("引用实体和自定义实体之间不能建立多对多关系");
				return false;
			}
			
			if(businessClassA!=null && IModelElement.STEREOTYPE_CUSTOM.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassB.getStereotype())){
				//自定义-内置
				this.setErrorMessage("自定义实体和内置实体之间不能建立多对多关系");
				return false;
			}
			if(businessClassA!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_CUSTOM.equals(businessClassB.getStereotype())){
				//内置-自定义
				this.setErrorMessage("内置实体和自定义实体之间不能建立多对多关系");
				return false;
			}
			
			if(businessClassA!=null && IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())){
				//反向-反向
				this.setErrorMessage("数据库反向实体和数据库反向实体之间不能建立多对多关系");
				return false;
			}
			
			if(businessClassA!=null && IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassB.getStereotype())){
				//反向-引用
				this.setErrorMessage("数据库反向实体和引用实体之间不能建立多对多关系");
				return false;
			}
			if(businessClassA!=null && IModelElement.STEREOTYPE_REFERENCE.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())){
				//引用-反向
				this.setErrorMessage("引用实体和数据库反向实体之间不能建立多对多关系");
				return false;
			}
			
			if(businessClassA!=null && IModelElement.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassB.getStereotype())){
				//反向-内置
				this.setErrorMessage("数据库反向实体和内置实体之间不能建立多对多关系");
				return false;
			}
			if(businessClassA!=null && IModelElement.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype()) && businessClassB!=null && IModelElement.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())){
				//内置-反向
				this.setErrorMessage("内置实体和数据库反向实体之间不能建立多对多关系");
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据当前类型设置下拉框，如果改变的值不允许则还原原选项.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void chooseCombo() {
		if (this.associationType != null) {
			comboAssType.removeAll();
			List<AssociationType> typeList = AssociationType
					.getAssociationTypes();
			for (int i = 0; typeList != null && i < typeList.size(); i++) {
				AssociationType type = typeList.get(i);
				comboAssType.add(type.getDisplayValue());
				comboAssType.setData(type.getDisplayValue(), type);
				if (type.equals(associationType)) {
					comboAssType.select(i);
				}
			}
		}
	}

	/**
	 * 设置各个控件的初始值.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fillBasicInfo() {
		if (association != null) {
			if (ModelElementEditorDialog.OPERATION_TYPE_ADD
					.equals(operationType)) {
				// 如果是新增
				String newName = manager.generateNextAssName();
				textAssName.setText(newName);
				textAssDispName.setText(newName);
			} else {
				textAssName.setText(StringUtil.convertNull2EmptyStr(association.getName()));
				textAssDispName.setText(StringUtil
						.convertNull2EmptyStr(association.getDisplayName()));

				boolean bControlAtA = false;
				if (businessClassA != null) {
					bControlAtA = association.getMajorClassId().equals(
							businessClassA.getId());
				}
				boolean bFKinA = Association.TRUE.equals(association
						.getPersistencePloyParams().get(
								Association.FOREIGNKEYINA));
				String strFKA = StringUtil.convertNull2EmptyStr(getFKColumnName(Association.FOREIGNKEYCOLUMNINA));
				/*checkCascadDelB.setSelection(association
						.isCascadeDeleteClassB());*/
				checkNavToB.setSelection(association.isNavigateToClassB());
				checkControlA.setSelection(bControlAtA);
				checkCreateFKA.setSelection(bFKinA);
				textFKColumnA.setText(strFKA);
				comboFKColumnAReadonly.setText(strFKA);
				if (!AssociationType.mult2mult.equals(this.associationType)) {
					// 多对多时不用限制是否可编辑
					textFKColumnA.setEnabled(bFKinA);
					comboFKColumnAReadonly.setEnabled(bFKinA);
				}

				boolean bControlAtB = false;
				if (businessClassB != null) {
					bControlAtB = association.getMajorClassId().equals(
							businessClassB.getId());
				}
				boolean bFKinB = Association.TRUE.equals(association
						.getPersistencePloyParams().get(
								Association.FOREIGNKEYINB));
				String strFKB = StringUtil.convertNull2EmptyStr(getFKColumnName(Association.FOREIGNKEYCOLUMNINB));
				//checkCascadDelA.setSelection(association.isCascadeDeleteClassA());
				checkNavToA.setSelection(association.isNavigateToClassA());
				checkControlB.setSelection(bControlAtB);
				checkCreateFKB.setSelection(bFKinB);
				
				if (!AssociationType.mult2mult.equals(this.associationType)) {
					// 多对多时不用限制是否可编辑
					textFKColumnB.setEnabled(bFKinB);
					comboFKColumnBReadonly.setEnabled(bFKinB);
				}
				textFKColumnB.setText(strFKB);
				comboFKColumnBReadonly.setText(strFKB);
				styledTextDesc.setText(StringUtil.convertNull2EmptyStr(this.association.getRemark()));
				textTable.setText(StringUtil
						.convertNull2EmptyStr(this.association
								.getPersistencePloyParams().get(Association.RELATIONTABLENAME)));
				textNavigateToClassBRoleName.setText(StringUtil
						.convertNull2EmptyStr(this.association.getNavigateToClassBRoleName()));
				textNavigateToClassBRoleName.setEditable(association.isNavigateToClassB());
				textNavigateToClassARoleName.setText(StringUtil
						.convertNull2EmptyStr(this.association.getNavigateToClassARoleName()));
				textNavigateToClassARoleName.setEditable(association.isNavigateToClassA());

				// 编辑时禁用查找按钮
				if (businessClassA != null) {
					btnFindA.setEnabled(false);
				}
				if (businessClassB != null) {
					btnFindB.setEnabled(false);
				}
				boolean notNullFKA = Association.TRUE.equalsIgnoreCase(association
						.getPersistencePloyParams().get(
								Association.NOTNULLFKA));
				notNullA.setSelection(notNullFKA);
				boolean notNullFKB = Association.TRUE.equalsIgnoreCase(association
						.getPersistencePloyParams().get(
								Association.NOTNULLFKB));
				notNullB.setSelection(notNullFKB);
				setFKColumnDisPlay();
			}
		}
		
		
	}

	/**
	 * 实体改变时 改变中间表名.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeTableName() {
		StringBuffer tableName = new StringBuffer();
		List<String> relationTableName=new ArrayList<String>();  //存储已存在的中间表名
		String tName="";
		if (businessClassA != null && businessClassA.getName() != null) {
			tableName.append(businessClassA.getName());
		}
		if (businessClassB != null && businessClassB.getName() != null) {
			if (tableName.length() > 0) {
				tableName.append("_");
			}
			tableName.append(businessClassB.getName());
		}
		//如果自动生成的中间表名长度超过30,进行截取
		if(tableName.length()>30){
			tName=tableName.toString().substring(0,28);
		}
		else{
			tName=tableName.toString();
		}
		//遍历所有中间表名,如果以tName开头,记录该表名
		for(Association a:manager.getBusinessObjectModel().getAssociations()){
			if(a.getAssociationType().equals(AssociationType.mult2mult.getValue())){
				String rTableName=a.getPersistencePloyParams().get(Association.RELATIONTABLENAME);
				if(rTableName.startsWith(tName)){
					relationTableName.add(rTableName);
				}
			}
		}
		//如果已存在，截取前28位，预留2位进行数字填充
		if(relationTableName.contains(tName)){
			for(int i=0;i<relationTableName.size()+1;i++){
				if(!relationTableName.contains(tName.concat(String.valueOf(i)))){
					tName=tName.concat(String.valueOf(i));
					break;
				}
			}
		}
		textTable.setText(tName);
		fillFieldsA();
		fillFieldsB();
	}

	/**
	 * 改变缩略图显示.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeCanvasDisplay() {
		computeArrowTip();
		canvas.changeData(businessClassA, businessClassB, this.associationType,
				textNavigateToClassBRoleName.getText(),
				textNavigateToClassARoleName.getText());
	}

	/**
	 * 根据导航关系设置图形箭头指向.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void computeArrowTip() {
		if (checkNavToB.getSelection()) {
			// 导航到B
			if (checkNavToA.getSelection()) {
				// 即双向导航
				canvas.setType(NavigationType.TWO_WAY);
			} else {
				// A2B
				canvas.setType(NavigationType.A2B);
			}
		} else {
			// 不能导航到B
			if (checkNavToA.getSelection()) {
				// B2A
				canvas.setType(NavigationType.B2A);
			} else {
				// 双向无导航
				canvas.setType(NavigationType.NON_WAY);
			}
		}
	}

	/**
	 * Fill fields A.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fillFieldsA() {
		List<Property> propsA = null;
		if (businessClassA != null) {
			textEntityA.setText(StringUtil.convertNull2EmptyStr(businessClassA
					.getDisplayName()));
			propsA = this.getBusinessClassA().getProperties();
		}
		combo_DbFieldA.removeAll();
		comboFKColumnAReadonly.removeAll();
		comboFKColumnAReadonly.add("");
		for (int i = 0; propsA != null && propsA.size() > 0
				&& i < propsA.size(); i++) {
			Property property = propsA.get(i);
			if(property instanceof PersistenceProperty){
				combo_DbFieldA.add(property.getName());
				if (property instanceof PKProperty) {
					combo_DbFieldA.select(i);
				}
				else
					comboFKColumnAReadonly.add(((PersistenceProperty)property).getdBColumnName());
				if (association != null
						&& association.getPersistencePloyParams() != null) {
					if (property.getName().equals(
							this.association.getPersistencePloyParams().get(
									Association.SOURCERELATIONCOLUMN))) {
						combo_DbFieldA.select(i);
					}
					if (((PersistenceProperty)property).getdBColumnName().equals(
							this.association.getPersistencePloyParams().get(
									Association.FOREIGNKEYCOLUMNINA))) {
						comboFKColumnAReadonly.select(i+1);
					}
				}
			}
			
		}
	}

	/**
	 * Fill fields B.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void fillFieldsB() {
		List<Property> propsB = null;
		if (businessClassB != null) {
			textEntityB.setText(StringUtil.convertNull2EmptyStr(businessClassB
					.getDisplayName()));
			propsB = businessClassB.getProperties();
		}
		combo_DbFieldB.removeAll();
		comboFKColumnBReadonly.removeAll();
		comboFKColumnBReadonly.add("");
		for (int i = 0; propsB != null && propsB.size() > 0
				&& i < propsB.size(); i++) {
			Property property = propsB.get(i);
			if(property instanceof PersistenceProperty){
				combo_DbFieldB.add(property.getName());
				if (property instanceof PKProperty) {
					combo_DbFieldB.select(i);
				}else
					comboFKColumnBReadonly.add(((PersistenceProperty)property).getdBColumnName());
				if (association != null
						&& association.getPersistencePloyParams() != null) {
					if (property.getName().equals(
							this.association.getPersistencePloyParams().get(
									Association.DESTRELATIONCOLUMN))) {
						combo_DbFieldB.select(i);
					}
					if (((PersistenceProperty)property).getdBColumnName().equals(
							this.association.getPersistencePloyParams().get(
									Association.FOREIGNKEYCOLUMNINB))) {
						comboFKColumnBReadonly.select(i+1);
					}
				}
			}
		}
	}

	/**
	 * 根据关系类型改变视图.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void changeViewByAssociationType() {
		if (this.associationType == null) {
			associationType = AssociationType.one2one;
		}
		setDisplayByAssciationType();
		setEditableByAssciationType();
		changeCanvasDisplay();
	}

	/**
	 * 根据当前选择项判断是否允许更改关联类型.
	 *
	 * @author mqfdy
	 * @param type
	 *            the type
	 * @return true, if is association type allow change
	 * @Date 2018-09-03 09:00
	 */
	private boolean isAssociationTypeAllowChange(AssociationType type) {

		if (AssociationType.one2mult.equals(type)) {
			if (businessClassB != null && 
					BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassB.getStereotype())) {
				this.setErrorMessage("实体B是内置实体，不允许更改为一对多关系");
				return false;
			}
			/*if (businessClassB != null && 
					BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())) {
				if(businessClassB != null && 
						BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
						&& businessClassA != null && 
								BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
						return true;
				}
				this.setErrorMessage("实体B是数据库反向实体，不允许更改为一对多关系");
				return false;
			}*/
		}
		else if (AssociationType.mult2one.equals(type)) {
			if (businessClassA != null && 
					BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype())) {
				this.setErrorMessage("实体A是内置实体，不允许更改为多对一关系");
				return false;
			}
			/*if (businessClassA != null && 
					BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
				if(businessClassB != null && 
						BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
						&& businessClassA != null && 
								BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
						return true;
				}
				this.setErrorMessage("实体A是数据库反向实体，不允许更改为多对一关系");
				return false;
			}*/
		}
		else if (AssociationType.mult2mult.equals(type)) {
			if (businessClassB != null && 
					BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassB.getStereotype())) {
				this.setErrorMessage("实体B是内置实体，不允许更改为多对多关系");
				return false;
			}
			if (businessClassA != null && 
					BusinessClass.STEREOTYPE_BUILDIN.equals(businessClassA.getStereotype())) {
				this.setErrorMessage("实体A是内置实体，不允许更改为多对多关系");
				return false;
			}
			if(businessClassB != null && 
					BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype())
					&& businessClassA != null && 
							BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype())) {
					this.setErrorMessage("关系两端均为反向业务实体，不允许更改为多对多关系");
					return false;
			}
		}
		
		this.setErrorMessage(null);
		return true;
	}
	
	

	/**
	 * 判断是否允许自关联.
	 *
	 * @author mqfdy
	 * @param selBusinessClass
	 *            the sel business class
	 * @param otherBusinessClass
	 *            the other business class
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean checkAssSelf(BusinessClass selBusinessClass,
			BusinessClass otherBusinessClass) {
		// 判断自关联
		if (selBusinessClass != null && otherBusinessClass != null
				&& selBusinessClass.getId().equals(otherBusinessClass.getId())) {
			return true;
		}
		return false;
	}

	/**
	 * 获取外键字段名.
	 *
	 * @author mqfdy
	 * @param params
	 *            the params
	 * @return the FK column name
	 * @Date 2018-09-03 09:00
	 */
	private String getFKColumnName(String params) {
		if (association != null && association.getPersistencePloyParams() != null) {
			return association.getPersistencePloyParams().get(params);
		} else {
			return null;
		}
	}
	
	/**
	 * 仅允许建立业务实体与built-in实体的 1对1和多对1关联(单向关联).
	 *
	 * @author mqfdy
	 * @param anotherBusinessClass
	 *            the another business class
	 * @param button
	 *            the button
	 * @return true, if is allow choose build in
	 * @Date 2018-09-03 09:00
	 */
	private boolean isAllowChooseBuildIn(BusinessClass anotherBusinessClass,
			Button button) {
		if (anotherBusinessClass != null
				&& ( BusinessClass.STEREOTYPE_BUILDIN.equals(anotherBusinessClass.getStereotype())
						||BusinessClass.STEREOTYPE_REVERSE.equals(anotherBusinessClass.getStereotype()))) {// 内置
			// 如果另一个已经被选择是非自定义业务实体,则不允许当前选择平台内置
			return false;
		} else {
			if (AssociationType.one2one.equals(associationType)) {
				return true;
			}
			if (AssociationType.one2mult.equals(associationType)) {
				if (button.equals(btnFindA)) {
					// 如果是选择的ClassA
					return true;
				}
				if (button.equals(btnFindB)) {
					return false;
				}
			}
			if (AssociationType.mult2one.equals(associationType)) {
				if (button.equals(btnFindA)) {
					// 如果是选择的ClassA
					return false;
				}
				if (button.equals(btnFindB)) {
					return true;
				}
			}
			if (AssociationType.mult2mult.equals(associationType)) {
				return false;
			}
			return false;
		}
	}
	
	/**
	 * 仅允许建立自定义业务实体与反向实体的 1对1、多对多和多对1关联.
	 *
	 * @author mqfdy
	 * @param anotherBusinessClass
	 *            the another business class
	 * @param button
	 *            the button
	 * @return true, if is allow choose reverse
	 * @Date 2018-09-03 09:00
	 */
	private boolean isAllowChooseReverse(BusinessClass anotherBusinessClass,
			Button button) {
		if (anotherBusinessClass != null
				&& (BusinessClass.STEREOTYPE_REVERSE.equals(anotherBusinessClass.getStereotype())
						|| BusinessClass.STEREOTYPE_BUILDIN.equals(anotherBusinessClass.getStereotype()))
				) {
			if(BusinessClass.STEREOTYPE_REVERSE.equals(anotherBusinessClass.getStereotype())){
				if (AssociationType.mult2mult.equals(associationType)) {
					return false;
				}
				return true;
			}
			return true;
		} else {
			if (AssociationType.one2one.equals(associationType)) {
				return true;
			}
			if (AssociationType.one2mult.equals(associationType)) {
				if (button.equals(btnFindA)) {
					// 如果是选择的ClassA
					return true;
				}
				if (button.equals(btnFindB)) {
					return true;
				}
			}
			if (AssociationType.mult2one.equals(associationType)) {
				if (button.equals(btnFindA)) {
					// 如果是选择的ClassA
					return true;
				}
				if (button.equals(btnFindB)) {
					return true;
				}
			}
			if (AssociationType.mult2mult.equals(associationType)) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 根据实体类型和关联关系类型判断控件只读.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void setEnableByStoreType(){
		if(this.association != null 
				&& AbstractModelElement.STEREOTYPE_REVERSE.equals(this.association.getStereotype())){
			btnFindA.setEnabled(false);
			btnFindB.setEnabled(false);
			textEntityA.setEnabled(false);// 实体A
			textEntityB.setEnabled(false);// 实体B
			textTable.setEnabled(false);// 中间表
			styledTextDesc.setEnabled(true);
			comboAssType.setEnabled(false);// 关联类型
			checkNavToB.setEnabled(false);// 导航到B
			//checkCascadDelB.setEnabled(false);// 级联删除B
			checkNavToA.setEnabled(false);// 导航到A
			//checkCascadDelA.setEnabled(false);// 级联删除A
			checkControlA.setEnabled(false);// 设为主控端
			checkControlB.setEnabled(false);// 设为主控端
			if(associationType.equals(AssociationType.mult2mult)){
				checkControlA.setEnabled(true);// 设为主控端
				checkControlB.setEnabled(true);// 设为主控端
			}
			combo_DbFieldA.setEnabled(false);// A的数据库关联字段
			combo_DbFieldB.setEnabled(false);// B的数据库关联字段
			checkCreateFKA.setEnabled(false);// 在实体A对应的数据库表中创建外键
			checkCreateFKB.setEnabled(false);// 在实体B对应的数据库表中创建外键
			textFKColumnA.setEnabled(false);
			textFKColumnB.setEnabled(false);
			comboFKColumnAReadonly.setEnabled(false);
			comboFKColumnBReadonly.setEnabled(false);
			textNavigateToClassBRoleName.setEnabled(false);
			textNavigateToClassARoleName.setEnabled(false);
			if(association.getAssociationType().equals(AssociationType.one2mult.getValue())){
					//checkCascadDelB.setEnabled(true);// 级联删除B
					//checkCascadDelA.setEnabled(false);// 级联删除A
					checkNavToA.setEnabled(true);// 导航到A
					checkNavToB.setEnabled(true);// 导航到B
					textNavigateToClassBRoleName.setEnabled(true);
					textNavigateToClassARoleName.setEnabled(true);
			}
			else if(association.getAssociationType().equals(AssociationType.mult2one.getValue())){
					//checkCascadDelB.setEnabled(false);// 级联删除B
					//checkCascadDelA.setEnabled(true);// 级联删除A
					checkNavToA.setEnabled(true);// 导航到A
					checkNavToB.setEnabled(true);// 导航到B
					textNavigateToClassBRoleName.setEnabled(true);
					textNavigateToClassARoleName.setEnabled(true);
				}
			else if(association.getAssociationType().equals(AssociationType.one2one.getValue())){
				//checkCascadDelB.setEnabled(true);// 级联删除B
				//checkCascadDelA.setEnabled(true);// 级联删除A
				textNavigateToClassBRoleName.setEnabled(true);
				textNavigateToClassARoleName.setEnabled(true);
			}
			else if(association.getAssociationType().equals(AssociationType.mult2mult.getValue())){
				//checkCascadDelB.setEnabled(true);// 级联删除B
				//checkCascadDelA.setEnabled(true);// 级联删除A
				textNavigateToClassBRoleName.setEnabled(true);
				textNavigateToClassARoleName.setEnabled(true);
			}
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
		groupPersist.layout();
		((GridData) checkCreateFKA.getLayoutData()).exclude = !b;
		checkCreateFKA.setVisible(b);
		((GridData) checkCreateFKB.getLayoutData()).exclude = !b;
		checkCreateFKB.setVisible(b);
		((GridData) this.lbl11.getLayoutData()).exclude = !b;
		this.lbl11.setVisible(b);
		((GridData) this.lbl11_1.getLayoutData()).exclude = !b;
		this.lbl11_1.setVisible(b);
		((GridData) this.lbl12.getLayoutData()).exclude = !b;
		this.lbl12.setVisible(b);
		((GridData) this.lbl12_1.getLayoutData()).exclude = !b;
		this.lbl12_1.setVisible(b);
		groupPersist.layout();
	}
	
	/**
	 * 控制编辑器按钮 非空按钮 是否显示.
	 *
	 * @author mqfdy
	 * @param bA
	 *            the b A
	 * @param bB
	 *            the b B
	 * @Date 2018-09-03 09:00
	 */
	private void setEditorDisPlay(boolean bA,boolean bB) {
		setEditorDisPlayA(bA);
		setEditorDisPlayB(bB);
	}
	
	/**
	 * Sets the editor dis play A.
	 *
	 * @author mqfdy
	 * @param bA
	 *            the new editor dis play A
	 * @Date 2018-09-03 09:00
	 */
	private void setEditorDisPlayA(boolean bA) {
		((GridData) editorDeployA.getLayoutData()).exclude = !bA;
		editorDeployA.setVisible(bA);
		((GridData) notNullA.getLayoutData()).exclude = !bA;
		notNullA.setVisible(bA);
		((GridData) lbl13.getLayoutData()).exclude = bA;
		lbl13.setVisible(!bA);
		((GridData) lbl13_1.getLayoutData()).exclude = bA;
		lbl13_1.setVisible(!bA);
	}
	
	/**
	 * Sets the editor editable A.
	 *
	 * @author mqfdy
	 * @param bA
	 *            the new editor editable A
	 * @Date 2018-09-03 09:00
	 */
	private void setEditorEditableA(boolean bA) {
		editorDeployA.setEnabled(bA);
	}
	
	/**
	 * Sets the editor dis play B.
	 *
	 * @author mqfdy
	 * @param bB
	 *            the new editor dis play B
	 * @Date 2018-09-03 09:00
	 */
	private void setEditorDisPlayB(boolean bB) {
		((GridData) editorDeployB.getLayoutData()).exclude = !bB;
		editorDeployB.setVisible(bB);
		((GridData) notNullB.getLayoutData()).exclude = !bB;
		notNullB.setVisible(bB);
		((GridData) lbl14.getLayoutData()).exclude = bB;
		lbl14.setVisible(!bB);
		((GridData) lbl14_1.getLayoutData()).exclude = bB;
		lbl14_1.setVisible(!bB);
		groupPersist.layout();
	}
	
	/**
	 * Sets the editor editable B.
	 *
	 * @author mqfdy
	 * @param bB
	 *            the new editor editable B
	 * @Date 2018-09-03 09:00
	 */
	private void setEditorEditableB(boolean bB) {
		editorDeployB.setEnabled(bB);
	}
	
	/**
	 * 控制只读的外键字段名是否显示.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void setFKColumnDisPlay() {
		isReverseAss = BusinessClass.STEREOTYPE_REVERSE.equals(association.getStereotype());
		
		isReverseA = !BusinessClass.STEREOTYPE_REVERSE.equals(association.getStereotype()) && businessClassA != null && 
				BusinessClass.STEREOTYPE_REVERSE.equals(businessClassA.getStereotype());
		
		isReverseB = !BusinessClass.STEREOTYPE_REVERSE.equals(association.getStereotype()) && businessClassB != null && 
				BusinessClass.STEREOTYPE_REVERSE.equals(businessClassB.getStereotype());
		
		if(isReverseAss){
			((GridData) textFKColumnA.getLayoutData()).exclude = !isReverseAss;
			textFKColumnA.setVisible(isReverseAss);
			
			((GridData) comboFKColumnAReadonly.getLayoutData()).exclude = isReverseAss;
			comboFKColumnAReadonly.setVisible(!isReverseAss);
		
			((GridData) textFKColumnB.getLayoutData()).exclude = !isReverseAss;
			textFKColumnB.setVisible(isReverseAss);
			
			((GridData) comboFKColumnBReadonly.getLayoutData()).exclude = isReverseAss;
			comboFKColumnBReadonly.setVisible(!isReverseAss);
		}else if(AssociationType.mult2mult.equals(associationType)){
			((GridData) textFKColumnA.getLayoutData()).exclude = false;
			textFKColumnA.setVisible(true);
			
			((GridData) comboFKColumnAReadonly.getLayoutData()).exclude = true;
			comboFKColumnAReadonly.setVisible(false);
		
			((GridData) textFKColumnB.getLayoutData()).exclude = false;
			textFKColumnB.setVisible(true);
			
			((GridData) comboFKColumnBReadonly.getLayoutData()).exclude = true;
			comboFKColumnBReadonly.setVisible(false);
			
		}else{
			((GridData) textFKColumnA.getLayoutData()).exclude = isReverseA;
			textFKColumnA.setVisible(!isReverseA);
			
			((GridData) comboFKColumnAReadonly.getLayoutData()).exclude = !isReverseA;
			comboFKColumnAReadonly.setVisible(isReverseA);
		
			((GridData) textFKColumnB.getLayoutData()).exclude = isReverseB;
			textFKColumnB.setVisible(!isReverseB);
			
			((GridData) comboFKColumnBReadonly.getLayoutData()).exclude = !isReverseB;
			comboFKColumnBReadonly.setVisible(isReverseB);
			
		}
		
		if(associationType.getValue().equals(AssociationType.mult2one.getValue())){
				setEditorDisPlayA(true);
				setEditorEditableA(true);
				setEditorDisPlayB(false);
		}else if(associationType.getValue().equals(AssociationType.one2mult.getValue())){
				setEditorDisPlayA(false);
				setEditorDisPlayB(true);
				setEditorEditableB(true);
		}else{
			setEditorDisPlayA(false);
			setEditorDisPlayB(false);
		}
		
		groupPersist.layout();
	}
	
	/**
	 * 设置中间表区域是否显示.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new display table
	 * @Date 2018-09-03 09:00
	 */
	private void setDisplayTable(boolean b) {
		((GridData) lblTable.getLayoutData()).exclude = !b;
		lblTable.setVisible(b);
		((GridData) lblDbFieldA.getLayoutData()).exclude = !b;
		lblDbFieldA.setVisible(b);
		((GridData) lblDbFieldB.getLayoutData()).exclude = !b;
		lblDbFieldB.setVisible(b);

		((GridData) textTable.getLayoutData()).exclude = !b;
		textTable.setVisible(b);

		((GridData) this.combo_DbFieldA.getLayoutData()).exclude = !b;
		this.combo_DbFieldA.setVisible(b);

		((GridData) this.combo_DbFieldB.getLayoutData()).exclude = !b;
		this.combo_DbFieldB.setVisible(b);

		((GridData) this.lbl5.getLayoutData()).exclude = !b;
		this.lbl5.setVisible(b);

		((GridData) this.lbl6.getLayoutData()).exclude = !b;
		this.lbl6.setVisible(b);
		
		((GridData) this.lbl7.getLayoutData()).exclude = !b;
		this.lbl7.setVisible(b);
		
		((GridData) this.lbl8.getLayoutData()).exclude = !b;
		this.lbl8.setVisible(b);
		
		((GridData) this.lbl7_1.getLayoutData()).exclude = !b;
		this.lbl7_1.setVisible(b);
		
		((GridData) this.lbl8_1.getLayoutData()).exclude = !b;
		this.lbl8_1.setVisible(b);
		
		((GridData) this.lbl9.getLayoutData()).exclude = !b;
		this.lbl9.setVisible(b);
		
		((GridData) this.lbl0.getLayoutData()).exclude = !b;
		this.lbl0.setVisible(b);
		
		((GridData) this.lbl9_1.getLayoutData()).exclude = !b;
		this.lbl9_1.setVisible(b);
		
		((GridData) this.lbl0_1.getLayoutData()).exclude = !b;
		this.lbl0_1.setVisible(b);
		
		groupPersist.layout();
	}

	/**
	 * 设置主控端checkbox是否显示.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new display main control
	 * @Date 2018-09-03 09:00
	 */
	private void setDisplayMainControl(boolean b) {
		// ((GridData)checkControlA.getLayoutData()).exclude = !b;
		checkControlA.setVisible(b);
		// ((GridData)checkControlB.getLayoutData()).exclude = !b;
		checkControlB.setVisible(b);
	}

	/**
	 * Sets the display by assciation type.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void setDisplayByAssciationType() {
		// 显示控制 add by xuran
		if (associationType == null) {
			setDisPlayCreateFK(true);
			setDisplayTable(false);
			setDisplayMainControl(true);
		}
		if (AssociationType.one2one.equals(associationType)) {
			setDisPlayCreateFK(true);
			setDisplayTable(false);
			setDisplayMainControl(true);
			setEditorDisPlay(false,false);
		}
		if (AssociationType.one2mult.equals(associationType)) {
			setDisPlayCreateFK(true);
			setDisplayTable(false);
			setDisplayMainControl(false);
			setEditorDisPlay(false,true);
		}
		if (AssociationType.mult2one.equals(associationType)) {
			setDisPlayCreateFK(true);
			setDisplayTable(false);
			setDisplayMainControl(false);
			setEditorDisPlay(true,false);;
		}
		if (AssociationType.mult2mult.equals(associationType)) {
			setDisPlayCreateFK(false);
			setDisplayTable(true);
			setDisplayMainControl(true);
			setEditorDisPlay(false,false);
			changeTableName();// 多对多时，需要初始化表名
		}
		setFKColumnDisPlay();
		groupPersist.getParent().layout();
	}

	/**
	 * Sets the editable by assciation type.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void setEditableByAssciationType() {
		changeNavi();
		changeCreateFKByType();
		changeControl();
		changeRoleNameByNavi();
	}

	/**
	 * 设置创建外键的可编辑性.
	 *
	 * @author mqfdy
	 * @param bA
	 *            the b A
	 * @param bB
	 *            the b B
	 * @Date 2018-09-03 09:00
	 */
	private void setEditableFK(boolean bA, boolean bB) {
		checkCreateFKA.setEnabled(bA);
		checkCreateFKB.setEnabled(bB);
	}
	
	/**
	 * 设置级联删除的可编辑性.
	 *
	 * @author mqfdy
	 * @param bA
	 *            the b A
	 * @param bB
	 *            the b B
	 * @Date 2018-09-03 09:00
	 */
	private void setEditableCascade(boolean bA, boolean bB) {
		/*checkCascadDelB.setEnabled(bA);// 级联删除B
		checkCascadDelA.setEnabled(bB);
		checkCascadDelB.setSelection(false);
		checkCascadDelA.setSelection(false);*/
	}

	/**
	 * 设置主控端的可编辑性.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new editable control
	 * @Date 2018-09-03 09:00
	 */
	private void setEditableControl(boolean b) {
		checkControlA.setEnabled(b);
		checkControlB.setEnabled(b);
	}

	/**
	 * 设置标题和message.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void setTitleAndMessage() {
		if (BusinessModelEvent.MODEL_ELEMENT_ADD == eventType) {
			setTitle(TITLE_TYPE);
			setMessage(TITLE_OPERATION + TITLE_TYPE);
		}
		if (BusinessModelEvent.MODEL_ELEMENT_UPDATE == eventType) {
			setTitle(TITLE_TYPE);
			setMessage(TITLE_OPERATION + this.association.getDisplayName()
					+ "信息");
		}
	}

	/**
	 * Gets the business class A.
	 *
	 * @author mqfdy
	 * @return the business class A
	 * @Date 2018-09-03 09:00
	 */
	private BusinessClass getBusinessClassA() {
		if (association == null) {
			return null;
		}
		return association.getClassA();
	}

	/**
	 * Gets the business class B.
	 *
	 * @author mqfdy
	 * @return the business class B
	 * @Date 2018-09-03 09:00
	 */
	private BusinessClass getBusinessClassB() {
		if (association == null) {
			return null;
		}
		return association.getClassB();
	}

//	public void setAssociation(Association association) {
//		if (association == null) {
//			this.association = new Association();
//		} else {
//			this.association = association;
//		}
//	}
	
	/**
 * Gets the association.
 *
 * @author mqfdy
 * @return the association
 * @Date 2018-09-03 09:00
 */
public Association getAssociation() {
		return association;
	}

	/**
	 * 
	 */
	public void initControlValue() {
		initData();
	}
	
	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		if (validateInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}
	
	/**
	 * @return
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(DIALOG_WIDTH, DIALOG_HEIGTH);
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
	
	/**
	 * 
	 */
	public void updateTheEditingElement() {
		doSave();
		BusinessModelEvent event = new BusinessModelEvent(eventType,association);
		manager.businessObjectModelChanged(event);
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		this.setErrorMessage(null);
		
		if(!validateByRelationType()){
			return false;
		}
		
		if (textAssName.getText().trim().length() == 0) {
//			this.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
			this.setErrorMessage("关联名称不能为空");
			return false;
		}
		if (!ValidatorUtil.valiName(textAssName.getText())) {
//			this.setErrorMessage(ERROR_MESSAGE_NAME_RULE);
			this.setErrorMessage("关联名称只能包含字母、数字和下划线，且以字母或下划线开头");
			return false;
		}
		if (!ValidatorUtil.valiNameLength(textAssName.getText())) {
//			this.setErrorMessage(ERROR_MESSAGE_NAME_LENGTH);
			this.setErrorMessage("关联名称长度不能超过30！");
			return false;
		}
		if (CheckerUtil.checkSql(textAssName.getText())) {
//			this.setErrorMessage(ERROR_MESSAGE_NAME_SQL);
			this.setErrorMessage("关联名称不能是SQL关键字！");
			return false;
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_ADD
				.equals(getOperationType())) {
			if (CheckerUtil.checkIsExist(textAssName.getText(),
					BusinessModelManager.ASSOCIATION_NAME_PREFIX)
//					manager.isAssExist(textAssName.getText())
					) {
//				this.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
				this.setErrorMessage("关联名称已经存在");
				return false;
			}
		} else {
			// 编辑时
			if (!textAssName.getText().equalsIgnoreCase(association.getName())) {
				if (CheckerUtil.checkIsExist(textAssName.getText(),
						BusinessModelManager.ASSOCIATION_NAME_PREFIX)) {
//					this.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
					this.setErrorMessage("关联名称已经存在");
					return false;
				}
			}
		}
		if (!ValidatorUtil.valiDisplayName(textAssDispName.getText())) {
			this.setErrorMessage(ERROR_DISPLAYNAME);
			return false;
		}
		if (!ValidatorUtil.valiDisplayNameLength(textAssDispName.getText())) {
			this.setErrorMessage(ERROR_DISPLAYNAME_LENGTH);
			return false;
		}
		

		if (textAssDispName.getText().trim().length() == 0) {
			this.setErrorMessage(ERROR_MESSAGE_DISPLAYNAME_NULLABLE);
			return false;
		}
		if (textEntityA.getText().trim().length() == 0) {
			this.setErrorMessage(ERROR_MESSAGE_ENTITYA_NULLABLE);
			return false;
		}

		if (textEntityB.getText().trim().length() == 0) {
			this.setErrorMessage(ERROR_MESSAGE_ENTITYB_NULLABLE);
			return false;
		}

		if (checkAssSelf(businessClassA, businessClassB)) {
			if (AssociationType.one2one.equals(associationType)) {
				setErrorMessage("关联关系是一对一时不允许自关联");
				return false;
			}
			if (AssociationType.mult2mult.equals(associationType)) {
				setErrorMessage("关联关系是多对多时不允许自关联");
				return false;
			}
//			if (textNavigateToClassARoleName.) {
//				setErrorMessage("自关联时导航属性名称不可以和类名相同");
//				return false;
//			}
			if(businessClassA.getName().equalsIgnoreCase(textNavigateToClassBRoleName.getText())){
				setErrorMessage("自关联时导航属性名不能与业务实体名重复");
				return false;
			}
			if(businessClassA.getName().equalsIgnoreCase(textNavigateToClassARoleName.getText())){
				setErrorMessage("自关联时导航属性名不能与业务实体名重复");
				return false;
			}
		}
		
		
		if (AssociationType.one2one.equals(associationType)
				|| AssociationType.mult2mult.equals(associationType)) {
			// 主控端必须二选一
			if (checkNavToA.getSelection() == checkNavToB.getSelection()) {
				// 只有双向导航时
				if (!(checkControlA.getSelection() ^ checkControlB
						.getSelection())) {
					this.setErrorMessage(ERROR_MESSAGE_PERSIST_POLICY_CONTROL);
					return false;
				}
			}
		}

		if (AssociationType.one2mult.equals(associationType)
				|| AssociationType.mult2one.equals(associationType)) {
			if (checkCreateFKA.getSelection() == checkCreateFKB.getSelection()) {
				this.setErrorMessage(ERROR_MESSAGE_PERSIST_POLICY_FK);
				return false;
			}
		}

		if (AssociationType.mult2mult.equals(associationType)) {
			// 多对多时，中间表部分必填
			if (textTable.getText().trim().length() == 0) {
				this.setErrorMessage(ERROR_MESSAGE_TABLE_NULLABLE);
				return false;
			}
			if (/* check.doCheckJava(ele.getTableName())|| */
					!IModelElement.STEREOTYPE_REVERSE.equals(association.getStereotype()) && 
					KeyWordsChecker.doCheckSql(textTable.getText())) {
				this.setErrorMessage("中间表名不能是SQL关键字");
				return false;
			}
			if (/* check.doCheckJava(ele.getTableName())|| */
					!IModelElement.STEREOTYPE_REVERSE.equals(association.getStereotype()) && 
					!ValidatorUtil.valiNameLength(textTable.getText())) {
				this.setErrorMessage("中间表名长度不能超过30");
				return false;
			}
			// 首位不能是数字
			if (!IModelElement.STEREOTYPE_REVERSE.equals(association.getStereotype()) && 
					!ValidatorUtil.valiFirstNo_Name(textTable.getText())) {
				this.setErrorMessage("中间表名格式不正确，"+NAME_FIRST_NO_);
				return false;
			}
//			if (!IModelElement.STEREOTYPE_REVERSE.equals(association.getStereotype()) && 
//					!ValidatorUtil.valiFirstNo_Name(textTable.getText())) {
//				this.setErrorMessage("中间表名格式不正确");
//				return false;
//			}
			if (!IModelElement.STEREOTYPE_REVERSE.equals(association.getStereotype()) && 
					!ValidatorUtil.valiTableName(textTable.getText(), association, BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel())) {
				this.setErrorMessage("中间表名重复");
				return false;
			}
			
			if (combo_DbFieldA.getText().trim().length() == 0) {
				this.setErrorMessage(ERROR_MESSAGE_COLUMN_NULLABLE);
				return false;
			}
			if (combo_DbFieldB.getText().trim().length() == 0) {
				this.setErrorMessage(ERROR_MESSAGE_COLUMN_NULLABLE);
				return false;
			}
			// 主控端必须二选一
			if (!(checkControlA.getSelection() ^ checkControlB.getSelection())) {
				this.setErrorMessage(ERROR_MESSAGE_PERSIST_POLICY_CONTROL);
				return false;
			}
			// 强制设置双向导航
			if (!(checkNavToB.getSelection() && checkNavToA.getSelection())) {
				this.setErrorMessage(ERROR_MESSAGE_PERSIST_POLICY_TWO_WAY);
				return false;
			}
		}

		if (!checkNavToB.getSelection() && !checkNavToA.getSelection()) {
			this.setErrorMessage("请设置导航关系");
			return false;
		}

		if (checkNavToB.getSelection()) {
			if (textNavigateToClassBRoleName.getText().trim().length() == 0) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_NULLABLE);
				this.setErrorMessage("请填写导航到实体B的导航属性名");
				return false;
			}
			if (CheckerUtil.checkJava(textNavigateToClassBRoleName.getText())) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_JAVA);
				this.setErrorMessage("导航到实体B的导航属性名不能是java关键字");
				return false;
			}
			if (!ValidatorUtil
					.valiName(textNavigateToClassBRoleName.getText())) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_RULE);
				this.setErrorMessage("导航到实体B的导航属性名称格式不正确,"+NAME_MESSAGE);
				return false;
			}
			if (textNavigateToClassBRoleName.getText().length() > 20) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_LENGTH);
				this.setErrorMessage("导航到实体B的导航属性名长度不能超过20");
				return false;
			}
			if (!ValidatorUtil.checkRoleNameMult(manager.getBusinessObjectModel(), association, businessClassA,textNavigateToClassBRoleName.getText())) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_MULT);
				this.setErrorMessage("属性名称已经存在，请更改导航到实体B的导航属性名称");
				return false;
			}
		}

		if (checkNavToA.getSelection()) {
			if (textNavigateToClassARoleName.getText().trim().length() == 0) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_NULLABLE);
				this.setErrorMessage("请填写导航到实体A的导航属性名");
				return false;
			}
			if (CheckerUtil.checkJava(textNavigateToClassARoleName.getText())) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_JAVA);
				this.setErrorMessage("导航到实体A的导航属性名不能是java关键字");
				return false;
			}
			if (!ValidatorUtil.valiName(textNavigateToClassARoleName.getText())) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_RULE);
				this.setErrorMessage("导航到实体A的导航属性名称格式不正确,"+NAME_MESSAGE);
				return false;
			}
			if (textNavigateToClassARoleName.getText().length() > 20) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_LENGTH);
				this.setErrorMessage("导航到实体A的导航属性名长度不能超过20");
				return false;
			}
			if (!ValidatorUtil.checkRoleNameMult(manager.getBusinessObjectModel(), association, 
					businessClassB,textNavigateToClassARoleName.getText())) {
//				this.setErrorMessage(ERROR_MESSAGE_ROLENAME_MULT);
				this.setErrorMessage("属性名称已经存在，请更改导航到实体A的导航属性名称");
				return false;
			}
		}

		if (checkCreateFKA.getSelection()) {
			if(!isReverseAss && isReverseA && !AssociationType.mult2mult.equals(associationType)) {
				String name = comboFKColumnAReadonly.getText();
				if (name.trim().length() == 0) {
					this.setErrorMessage("请填写实体A的外键字段名");
					return false;
				}
				if (!ValidatorUtil.valiFirstNo_Name(name)) {
					this.setErrorMessage("实体A外键字段名格式不正确,"+NAME_FIRST_NO_);
					return false;
				}
				if (CheckerUtil.checkSql(name)) {
					this.setErrorMessage("实体A外键字段名不能是sql关键字!");
					return false;
				}
				if (name.length() > 30) {
					this.setErrorMessage("实体A外键字段名长度不能超过30！");
					return false;
				}
				if (!ValidatorUtil.isFKExistInBu(businessClassA, name)) {
					this.setErrorMessage("关系两端均为反向业务实体，请从下拉框中选择外键字段名！");
					return false;
				}
				if (AssociationType.mult2one.equals(associationType)
						|| AssociationType.one2one.equals(associationType)) {
					if (isFkEqualsPK(businessClassA, comboFKColumnAReadonly.getText())) {
						this.setErrorMessage("实体A外键字段名不可以是主键！");
						return false;
					}
				}
				if (!ValidatorUtil.checkFKMultInAll(this.getAssociation(),businessClassA, comboFKColumnAReadonly.getText(), manager.getBusinessObjectModel())) {
					this.setErrorMessage("外键字段名已经存在,请更改实体A外键字段名!");
					return false;
				}
			}else{
				if (textFKColumnA.getText().trim().length() == 0) {
					this.setErrorMessage("请填写实体A的外键字段名");
					return false;
				}
				if (!ValidatorUtil.valiFirstNo_Name(textFKColumnA.getText())) {
					this.setErrorMessage("实体A外键字段名格式不正确,"+NAME_FIRST_NO_);
					return false;
				}
				if (CheckerUtil.checkSql(textFKColumnA.getText())) {
					this.setErrorMessage("实体A外键字段名不能是sql关键字!");
					return false;
				}
				if (textFKColumnA.getText().length() > 30) {
					this.setErrorMessage("实体A外键字段名长度不能超过30！");
					return false;
				}
				if (!ValidatorUtil.checkFKMult(businessClassA, textFKColumnA.getText(),associationType.getValue())) {
					this.setErrorMessage("数据库字段名称已经存在,请更改实体A外键字段名!");
					return false;
				}
				if (!ValidatorUtil.checkFKMultInAll(this.getAssociation(),businessClassA, textFKColumnA.getText(), manager.getBusinessObjectModel())) {
					this.setErrorMessage("外键字段名已经存在,请更改实体A外键字段名!");
					return false;
				}
			}
			
		}

		if (checkCreateFKB.getSelection()) {
			if(!isReverseAss && isReverseB && !AssociationType.mult2mult.equals(associationType)){
				if (comboFKColumnBReadonly.getText().trim().length() == 0) {
					this.setErrorMessage("请填写实体B的外键字段名");
					return false;
				}
				if (!ValidatorUtil.valiFirstNo_Name(comboFKColumnBReadonly.getText())) {
					this.setErrorMessage("实体B外键字段名格式不正确,"+NAME_FIRST_NO_);
					return false;
				}
				if (CheckerUtil.checkSql(comboFKColumnBReadonly.getText())) {
					this.setErrorMessage("实体B外键字段名不能是sql关键字!");
					return false;
				}
				if (comboFKColumnBReadonly.getText().length() > 30) {
					this.setErrorMessage("实体B外键字段名长度不能超过30！");
					return false;
				}
				if (!ValidatorUtil.isFKExistInBu(businessClassB, comboFKColumnBReadonly.getText())) {
					this.setErrorMessage("关系两端均为反向业务实体，请从下拉框中选择外键字段名！");
					return false;
				}
				if (AssociationType.one2mult.equals(associationType)
						|| AssociationType.one2one.equals(associationType)) {
					if (isFkEqualsPK(businessClassB, comboFKColumnBReadonly.getText())) {
						this.setErrorMessage("实体B外键字段名不可以是主键！");
						return false;
					}
				}
				if (!ValidatorUtil.checkFKMultInAll(this.getAssociation(),businessClassB, comboFKColumnBReadonly.getText(), manager.getBusinessObjectModel())) {
					this.setErrorMessage("外键字段名已经存在,请更改实体B外键字段名!");
					return false;
				}
			}else {
				if (textFKColumnB.getText().trim().length() == 0) {
					this.setErrorMessage("请填写实体B的外键字段名");
					return false;
				}
				if (!ValidatorUtil.valiFirstNo_Name(textFKColumnB.getText())) {
					this.setErrorMessage("实体B外键字段名格式不正确,"+NAME_FIRST_NO_);
					return false;
				}
				if (CheckerUtil.checkSql(textFKColumnB.getText())) {
					this.setErrorMessage("实体B外键字段名不能是sql关键字!");
					return false;
				}
				if (textFKColumnB.getText().length() > 30) {
					this.setErrorMessage("实体B外键字段名长度不能超过30！");
					return false;
				}
				if (!ValidatorUtil.checkFKMult(businessClassB, textFKColumnB.getText(),associationType.getValue())) {
					this.setErrorMessage("数据库字段名称已经存在,请更改实体B外键字段名!");
					return false;
				}
				if (!ValidatorUtil.checkFKMultInAll(this.getAssociation(),businessClassB, textFKColumnB.getText(), manager.getBusinessObjectModel())) {
					this.setErrorMessage("外键字段名已经存在,请更改实体B外键字段名!");
					return false;
				}
			}
		}
		
		if (AssociationType.mult2mult.equals(associationType)) {
			if (textFKColumnA.getText().trim().length() == 0) {
				this.setErrorMessage("请填写实体A的外键字段名");
				return false;
			}
			if (!ValidatorUtil.valiFirstNo_Name(textFKColumnA.getText())) {
				this.setErrorMessage("实体A外键字段名格式不正确,"+NAME_FIRST_NO_);
				return false;
			}
			if (CheckerUtil.checkSql(textFKColumnA.getText())) {
				this.setErrorMessage("实体A外键字段名不能是sql关键字!");
				return false;
			}
			if (textFKColumnA.getText().length() > 30) {
				this.setErrorMessage("实体A外键字段名长度不能超过30！");
				return false;
			}
			
			if (textFKColumnB.getText().trim().length() == 0) {
				this.setErrorMessage("请填写实体B的外键字段名");
				return false;
			}
			if (!ValidatorUtil.valiFirstNo_Name(textFKColumnB.getText())) {
				this.setErrorMessage("实体B外键字段名格式不正确,"+NAME_FIRST_NO_);
				return false;
			}
			if (CheckerUtil.checkSql(textFKColumnB.getText())) {
				this.setErrorMessage("实体B外键字段名不能是sql关键字!");
				return false;
			}
			if (textFKColumnB.getText().length() > 30) {
				this.setErrorMessage("实体B外键字段名长度不能超过30！");
				return false;
			}
			if(textFKColumnA.getText().equalsIgnoreCase(textFKColumnB.getText())){
				this.setErrorMessage("两端外键字段名重复，请重新填写！");
				return false;
			}
		}
		if (!ValidatorUtil.valiRemarkLength(styledTextDesc.getText())) {
			this.setErrorMessage(TOOLONG_ERROR_REMARK);
			return false;
		}
		// modified by mqfdy
		
		if (BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassA
				.getStereotype()) && checkNavToB.getSelection()) {
			this.setErrorMessage("不可以从引用模型导航到其他模型");
			return false;
		}
		if (BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassB
				.getStereotype()) && checkNavToA.getSelection()) {
			this.setErrorMessage("不可以从引用模型导航到其他模型");
			return false;
		}
		if (AssociationType.one2mult.equals(associationType)) {
			if (BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassB
					.getStereotype())) {
				this.setErrorMessage("引用模型不可以设置在多端");
				return false;
			}
		}
		if (AssociationType.mult2one.equals(associationType)) {
			
			if (BusinessClass.STEREOTYPE_REFERENCE.equals(businessClassA
					.getStereotype())) {
				this.setErrorMessage("引用模型不可以设置在多端");
				return false;
			}
		}

		return true;
	}

	/**
	 * 保存数据.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void doSave() {
		String id = association == null ? null : association.getId();
		// association = new Association();
	
		String name = textAssName.getText().trim();// 关系名
		String displayName = textAssDispName.getText();// 关系显示名
		String tableName = textTable.getText().trim();// 中间表
		String remark = styledTextDesc.getText();
		AssociationType associationType = (AssociationType) comboAssType
				.getData(comboAssType.getText());// 关联类型
		boolean navigateToClassB = checkNavToB.getSelection();// 导航到B
		//boolean cascadeDeleteClassB = checkCascadDelB.getSelection();// 级联删除B
		boolean controlA = checkControlA.getSelection();// 设为主控端
		boolean navigateToClassA = checkNavToA.getSelection();// 导航到A
		//boolean cascadeDeleteClassA = checkCascadDelA.getSelection();// 级联删除A
		boolean controlB = checkControlB.getSelection();// 设为主控端
		String dbFieldA = combo_DbFieldA.getText();// A的数据库关联字段
		String dbFieldB = combo_DbFieldB.getText();// B的数据库关联字段
		String createFKA = checkCreateFKA.getSelection() ? 
				Association.TRUE : Association.FALSE;// 在实体A对应的数据库表中创建外键
		String createFKB = checkCreateFKB.getSelection() ? 
				Association.TRUE : Association.FALSE;// 在实体B对应的数据库表中创建外键
		String columnFKA = "";// 在实体A对应的数据库表中创建外键
		String columnFKB = "";// 在实体B对应的数据库表中创建外键		
			if(isReverseA && !AssociationType.mult2mult.equals(associationType)){
				columnFKA = comboFKColumnAReadonly.getText();// 在实体A对应的数据库表中创建外键
				
			}else{
				columnFKA = textFKColumnA.getText();
			}
			
			if(isReverseB  && !AssociationType.mult2mult.equals(associationType)){
				columnFKB = comboFKColumnBReadonly.getText();
			}else{
				columnFKB = textFKColumnB.getText();
			}
		if (association != null) {
			if (id != null) {
				association.setId(id);
			}
			association.setAssociationType(associationType.getValue());
			association.setName(name);
			association.setDisplayName(displayName);
			association.setNavigateToClassB(navigateToClassB);
			//association.setCascadeDeleteClassB(cascadeDeleteClassB);
			if (controlA) {
				association.setMajorClassId(businessClassA.getId());
			}
	
			association.setNavigateToClassA(navigateToClassA);
			//association.setCascadeDeleteClassA(cascadeDeleteClassA);
			if (controlB) {
				association.setMajorClassId(businessClassB.getId());
			}
			association.setRemark(remark);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINA, createFKA);
			association.getPersistencePloyParams().put(
					Association.FOREIGNKEYINB, createFKB);
	
			if(columnFKA!=null ){
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINA, columnFKA);
			}
			if(columnFKB!=null ){
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYCOLUMNINB, columnFKB);
			}
	
			if (AssociationType.one2one.equals(associationType)) {
				if (checkControlA.getSelection()) {
					association.setMajorClassId(businessClassA.getId());
				}
				if (checkControlB.getSelection()) {
					association.setMajorClassId(businessClassB.getId());
				}
			}
			else if (AssociationType.one2mult.equals(associationType)) {
				// 删除主控端
				association.setMajorClassId(businessClassA.getId());
				association.getPersistencePloyParams().put(
						Association.NOTNULLFKB, (notNullB.getSelection()+"").toUpperCase(Locale.getDefault()));
				association.getPersistencePloyParams().put(
						Association.NOTNULLFKA, "");
			}
			else if (AssociationType.mult2one.equals(associationType)) {
				// 删除主控端
				association.setMajorClassId(businessClassB.getId());
				association.getPersistencePloyParams().put(
						Association.NOTNULLFKA, (notNullA.getSelection()+"").toUpperCase(Locale.getDefault()));
				association.getPersistencePloyParams().put(
						Association.NOTNULLFKB, "");
			}
			else if (AssociationType.mult2mult.equals(associationType)) {
				// 添加持久化策略
				association.getPersistencePloyParams().put(
						Association.RELATIONTABLENAME, tableName);
				association.getPersistencePloyParams().put(
						Association.SOURCERELATIONCOLUMN, dbFieldA);
				association.getPersistencePloyParams().put(
						Association.DESTRELATIONCOLUMN, dbFieldB);
	
				if (checkControlA.getSelection()) {
					association.setMajorClassId(businessClassA.getId());
				}
				if (checkControlB.getSelection()) {
					association.setMajorClassId(businessClassB.getId());
				}
			}
			association.setClassA(businessClassA);
			association.setClassB(businessClassB);
			association.setAssociationType(associationType.getValue());
	
			if (checkNavToB.getSelection()) {
				association
						.setNavigateToClassBRoleName(textNavigateToClassBRoleName
								.getText());
			} else {
				association.setNavigateToClassBRoleName("");
			}
			if (checkNavToA.getSelection()) {
				association
						.setNavigateToClassARoleName(textNavigateToClassARoleName
								.getText());
			} else {
				association.setNavigateToClassARoleName("");
			}
		}
	}
	
	/**
	 * Checks if is fk equals PK.
	 *
	 * @author mqfdy
	 * @param businessClass
	 *            the business class
	 * @param fkName
	 *            the fk name
	 * @return true, if is fk equals PK
	 * @Date 2018-09-03 09:00
	 */
	private boolean  isFkEqualsPK(BusinessClass businessClass, String fkName){
		// 校验外键是否是主键
		
		List<Property> propsB = null;
		if (businessClass != null) {
			propsB = businessClass.getProperties();
		}
		for (int i = 0; propsB != null && propsB.size() > 0
				&& i < propsB.size(); i++) {
			Property property = propsB.get(i);
			if(property instanceof PersistenceProperty){
				if(fkName.equals(((PersistenceProperty)property).getdBColumnName())){
					if (property instanceof PKProperty) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

