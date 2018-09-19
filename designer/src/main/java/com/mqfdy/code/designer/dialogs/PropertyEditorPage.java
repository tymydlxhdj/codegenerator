package com.mqfdy.code.designer.dialogs;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.EditorTypeUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 属性编辑器页面.
 *
 * @author mqfdy
 */
public class PropertyEditorPage extends Composite implements
		IBusinessClassEditorPage {

	// private static final String ERROR_MESSAGE_EDITORTYPE_NULLABLE
	// ="请选择编辑器类型";

	/** The Constant GROUP_EDITORTYPELIST_TEXT. */
	public static final String GROUP_EDITORTYPELIST_TEXT = "编辑器类型选择";
	
	/** The Constant GROUP_EDITORPARAM_TEXT. */
	// public static final String GROUP_EDITORSHOW_TEXT = "编辑器效果";
	public static final String GROUP_EDITORPARAM_TEXT = "参数设置";

	/** The Constant DATASOURCE_LABEL_TEXT. */
	public static final String DATASOURCE_LABEL_TEXT = "数据来源：";

	/** The Constant TAB_ENUM_TITLE. */
	public static final String TAB_ENUM_TITLE = "来源于枚举";
	
	/** The Constant TAB_BUSINESS_TITLE. */
	public static final String TAB_BUSINESS_TITLE = "来源于业务数据";
	// public static final String TAB_CUSTOM_TITLE = "自定义";

	/** 数据源. */
	// public static final String[] datasources = new
	// String[]{TAB_ENUM_TITLE,TAB_BUSINESS_TITLE,TAB_CUSTOM_TITLE};
	public static final String[] datasources = new String[] { TAB_ENUM_TITLE,
			TAB_BUSINESS_TITLE };

	/** The parent dialog. */
	private PropertyEditorDialog parentDialog;

	/** The group editor type list. */
	private Group group_editorTypeList;
	
	/** The group editor param. */
	// private Group group_editorShow;
	private Group group_editorParam;
	
	/** The composite. */
	Composite composite;
	
	/** 图片显示容器. */
	// private Composite imageCantainer;

	/**
	 * 编辑器类型树
	 */
	private Tree tree;
	
	/** The tree viewer. */
	private TreeViewer treeViewer;

	/** The label data source type. */
	private Label label_dataSourceType;
	
	/** The combo data source type. */
	private Combo combo_dataSourceType;

	// private TabFolder tabFolder;

	/** 枚举数据源页面. */
	private DataSourceEnumPage enumPage;

	/** 业务数据源页面. */
	private DataSourceBusinessPage businessPage;

	/** The cur type. */
	EditorType curType = EditorType.TextEditor;

	/** 自定义数据源页面. */
	// private DataSourceCustomPage customPage;

	final StackLayout rootLayout = new StackLayout();// 堆栈布局方式;
	
	/** The tab 1. */
	private Composite tab1;
	
	/** The tab 2. */
	private Composite tab2;
	
	/** The root container. */
	private Composite rootContainer;

	// private Composite tab3;
	/**
	 * 编辑器类型树的标签显示提供者.
	 *
	 * @author LQR
	 */
	private class EditorTypeTreeLabelProvider extends LabelProvider implements
			ILabelProvider {
		
		/**
		 * Gets the image.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return the image
		 * @Date 2018-09-03 09:00
		 */
		public Image getImage(Object element) {
			if (element instanceof String) {
				return ImageManager.getInstance().getImage(
						ImageKeys.IMG_TREE_FOLDER);
			} else if (element instanceof EditorType) {
				return ImageManager.getInstance().getImage(
						ImageKeys.IMG_TREE_LEAF);
			}
			return null;
		}

		/**
		 * Gets the text.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @return the text
		 * @Date 2018-09-03 09:00
		 */
		public String getText(Object element) {
			if (element instanceof String) {
				return (String) element;
			} else if (element instanceof EditorType) {
				return ((EditorType) element).getDisplayValue();
			}
			return "Unknown";
		}
	}

	/**
	 * 编辑器类型树的内容提供者.
	 *
	 * @author LQR
	 */
	private class EditorTypeTreeContentProvider implements ITreeContentProvider {

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

		/**
		 * Gets the elements.
		 *
		 * @author mqfdy
		 * @param inputElement
		 *            the input element
		 * @return the elements
		 * @Date 2018-09-03 09:00
		 */
		public Object[] getElements(Object inputElement) {
			return EditorType.getEditorTypeGroups().toArray();
		}

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
			if (parentElement instanceof String) {
				List<EditorType> list = EditorType
						.getEditorTypes((String) parentElement);
				return list.toArray();
			}
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
			if (element instanceof EditorType) {
				return ((EditorType) element).getGroup();
			}
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
			if (element instanceof String) {
				return true;
			}
			return false;
		}

	}

	/**
	 * 构造函数.
	 *
	 * @param parent
	 *            上级容器
	 * @param style
	 *            样式
	 * @param parentDialog
	 *            属性编辑弹出窗
	 */
	public PropertyEditorPage(Composite parent, int style,
			PropertyEditorDialog parentDialog) {
		super(parent, style);
		this.parentDialog = parentDialog;
		createContents(this);
	}

	/**
	 * 创建页面内容.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createContents(Composite composite) {
		this.composite = composite;
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		group_editorTypeList = new Group(composite, SWT.NONE);
		group_editorTypeList.setText(GROUP_EDITORTYPELIST_TEXT);
		GridData treeGridData = new GridData(SWT.LEFT, SWT.FILL, false, true,
				1, 2);
		treeGridData.widthHint = 200;
		treeGridData.heightHint = 300;
		group_editorTypeList.setLayoutData(treeGridData);
		group_editorTypeList.setLayout(new FillLayout());

		// group_editorShow = new Group(composite, SWT.NONE);
		// group_editorShow.setText(GROUP_EDITORSHOW_TEXT);
		// group_editorShow.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
		// false, 1, 1));
		// imageCantainer = new Composite(group_editorShow, SWT.NONE);
		// group_editorShow.setLayout(new GridLayout(1,false));
		// GridData gridData_image = new
		// GridData(SWT.CENTER,SWT.CENTER,true,true);
		// gridData_image.widthHint = 100;
		// gridData_image.heightHint = 30;
		// imageCantainer.setLayoutData(gridData_image);

		group_editorParam = new Group(composite, SWT.NONE);
		group_editorParam.setText(GROUP_EDITORPARAM_TEXT);
		group_editorParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginLeft = 0;
		gridLayout.marginRight = 0;
		group_editorParam.setLayout(gridLayout);

		treeViewer = new TreeViewer(group_editorTypeList, SWT.NONE
				| SWT.V_SCROLL);
		tree = treeViewer.getTree();
		treeViewer.setContentProvider(new EditorTypeTreeContentProvider());
		treeViewer.setLabelProvider(new EditorTypeTreeLabelProvider());
		treeViewer.setInput("input");

		label_dataSourceType = new Label(group_editorParam, SWT.NONE);
		label_dataSourceType.setText(DATASOURCE_LABEL_TEXT);
		combo_dataSourceType = new Combo(group_editorParam, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		combo_dataSourceType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
		createTabFolder();
		addListeners();
		makeAction();
	}

	/**
	 * Adds the listeners.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void addListeners() {
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent arg0) {
				ISelection selection = treeViewer.getSelection();
				Object node = ((IStructuredSelection) selection)
						.getFirstElement();
				if (node != null && node instanceof EditorType) {
					setCurEditorType((EditorType) node);
				}
			}

		});
	}

	/**
	 * 初始化创建数据源标签页.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createTabFolder() {

		rootContainer = new Composite(group_editorParam, SWT.BORDER);
		rootContainer.setLayout(rootLayout);
		GridData g = new GridData();
		// g.verticalSpan = 2;
		g.horizontalSpan = 2;
		g.grabExcessHorizontalSpace = true;
		g.horizontalAlignment = SWT.FILL;
		g.verticalAlignment = SWT.FILL;
		g.heightHint = 300;
		rootContainer.setLayoutData(g);
		tab1 = new Composite(rootContainer, SWT.NULL);
		tab1.setLayout(new GridLayout());

		// tab3= new Composite(rootContainer, SWT.NULL);
		// tabFolder = new TabFolder(group_editorParam, SWT.BOTTOM);
		// tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
		// 2, 1));
		// for (int i = 0; i < 2; i++) {
		// TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		//
		// tabItem.setText(getTabTitle(i));
		// }
		// 创建数据源选择页面
		enumPage = new DataSourceEnumPage(tab1, SWT.NONE, this);
		// tabFolder.getItem(0).setControl(enumPage);
		tab2 = new Composite(rootContainer, SWT.NULL);
		tab2.setLayout(new GridLayout());
		businessPage = new DataSourceBusinessPage(tab2, SWT.NONE, this);
		// tabFolder.getItem(1).setControl(businessPage);

		/*
		 * customPage = new DataSourceCustomPage(tabFolder, SWT.NONE,this);
		 * tabFolder.getItem(2).setControl(customPage);
		 */
		rootLayout.topControl = tab1;
		// group_editorParam.layout();
	}

	/**
	 * 初始化化事件.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeAction() {
		// 编辑器类型选择树点击事件
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				Object node = selection.getFirstElement();
				if (node instanceof EditorType) {
					EditorType editorType = (EditorType) node;
					curType = editorType;
					resetEditor(editorType);
					// resetDataLength(editorType);
				}
			}
		});

		// 数据源下拉框选择事件
		combo_dataSourceType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				switch (combo_dataSourceType.getSelectionIndex()) {
				case 0:
					rootLayout.topControl = tab1;
					rootContainer.layout();
					break;
				case 1:
					rootLayout.topControl = tab2;
					rootContainer.layout();
					break;
				default:
					break;
				}

			}
		});

	}

	/**
	 * 重置基本信息页的数据长度.
	 *
	 * @author mqfdy
	 * @param editorType
	 *            编辑器类型枚举
	 * @Date 2018-09-03 09:00
	 */
	private void resetDataLength(EditorType editorType) {
		switch (editorType) {
		case MultTextEditor:
			parentDialog.getPropertyBasicInfoPage().resetDataLength(1000);
			break;
		case RichTextEditor:
			parentDialog.getPropertyBasicInfoPage().resetDataLength(1000);
			break;
		}
	}

	/**
	 * 重置编辑器.
	 *
	 * @author mqfdy
	 * @param editorType
	 *            编辑器类型枚举
	 * @Date 2018-09-03 09:00
	 */
	private void resetEditor(EditorType editorType) {
		if(editorType == null)
			return;
		Image editorImage = EditorTypeUtil.getImage(editorType);
		if (editorImage != null) {
			// imageCantainer.setBackgroundImage(editorImage);
			Rectangle rec = editorImage.getBounds();
			GridData gridData_image = new GridData(SWT.CENTER, SWT.CENTER,
					true, true);
			gridData_image.widthHint = rec.width;
			gridData_image.heightHint = rec.height;
			// imageCantainer.setLayoutData(gridData_image);
			this.layout(true);
		}
		if (editorType.isHasData() == true) {
			group_editorParam.setEnabled(true);
			// add by zhanghe 
			if(combo_dataSourceType.getSelectionIndex() == -1)
				combo_dataSourceType.select(0);
			// 变灰色，根据bug1876要求屏蔽变灰功能
			// setBackgrand(true);
			group_editorParam.setVisible(true);
		} else {
			group_editorParam.setEnabled(false);
			// setBackgrand(false);
			group_editorParam.setVisible(false);
		}
	}

	/**
	 * Sets the backgrand.
	 *
	 * @author mqfdy
	 * @param b
	 *            the new backgrand
	 * @Date 2018-09-03 09:00
	 */
	private void setBackgrand(boolean b) {
		Color color = null;
		if (b) {
			color = new Color(Display.getDefault(), 255, 255, 255);
		} else {
			color = new Color(Display.getDefault(), 240, 240, 240);
		}
		group_editorParam.setBackground(color);
		label_dataSourceType.setBackground(color);

		if (businessPage != null) {
			businessPage.setBackground(color);
			businessPage.setContentBackGrand(color);
		}
		if (enumPage != null) {
			enumPage.setBackground(color);
			enumPage.setContentBackGrand(color);
		}
	}

	/**
	 * 根据数据类型更改树的选中节点.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the data type
	 * @Date 2018-09-03 09:00
	 */
	public void change(String dataType) {
		DataType selectType = DataType.getDataType(dataType);
		EditorType type = getEditorTypeBySelect(selectType);// 返回null表示不需要切换选中项
		if(!parentDialog.getPropertyBasicInfoPage().isChooseDataType() && curType != null){
			//如果没有更改数据类型，并且编辑器树当前选择项不为空
			type = curType;
		}
		TreeItem[] items = treeViewer.getTree().getItems();
		for (TreeItem item : items) {
			checkItem(item, type);
			resetEditor(type);
		}
	}

	/**
	 * 为选择的数据类型计算默认编辑器.
	 *
	 * @author mqfdy
	 * @param selectType
	 *            the select type
	 * @return the editor type by select
	 * @Date 2018-09-03 09:00
	 */
	private EditorType getEditorTypeBySelect(DataType selectType) {
		EditorType type = EditorType.TextEditor;
		if(curType!=null){
			type = curType;
		}
		if (selectType == null) {
			// 返回已保存的编辑器类型
			if (parentDialog.getPropertyCopy().getEditor() != null) {
				type = EditorType
						.getEditorType((parentDialog.getPropertyCopy())
								.getEditor().getEditorType());
				return type;
			}
			if (type != null) {
				// 如果当前属性已经有了编辑器类型，则使用已有的
				setCurEditorType(type);
			}
			return type;
		}
		type = EditorTypeUtil.getEditorType(selectType);
		return type;
	}

	/**
	 * 判断当前节点是否应该被选中，如果是，则选中.
	 *
	 * @author mqfdy
	 * @param item
	 *            the item
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	private void checkItem(TreeItem item, EditorType type) {
		if (type == null) {
			return;
		}
		if (item != null) {
			Object element = item.getData();
			if (element instanceof String) {
				if (item.getItems() != null) {
					for (TreeItem temp : item.getItems()) {
						checkItem(temp, type);
					}
				}
			}

			if (element instanceof EditorType) {
				EditorType curType = (EditorType) element;
				if (curType.equals(type)) {
					setCurEditorType(curType);
					treeViewer.getTree().setSelection(item);
				}

			}
		}
	}

	/**
	 * 初始化控件的值和内容.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initControlValue() {
		combo_dataSourceType.setItems(datasources);
		treeViewer.expandAll();
		AbstractModelElement editingElement = parentDialog.getPropertyCopy();
		if (editingElement instanceof Property) {
			if (parentDialog.getOperationType().equals(
					ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
				PropertyEditor editor = parentDialog.getPropertyCopy()
						.getEditor();
				if (editor != null
						&& !StringUtil.isEmpty(editor.getEditorType())) {
					// 遍历树 选中默认节点
					TreeItem[] items = treeViewer.getTree().getItems();
					for (int i = 0; i < items.length; i++) {
						TreeItem[] subItems = items[i].getItems();
						for (int j = 0; j < subItems.length; j++) {
							EditorType editorType = (EditorType) subItems[j]
									.getData();
							if (editorType.getValue().equals(
									(parentDialog.getPropertyCopy())
											.getEditor().getEditorType())) {
								setCurEditorType(editorType);
								tree.select(subItems[j]);
								resetEditor(editorType);
								break;
							}
						}
					}
					if (editor.getDataSourceType() == PropertyEditor.DATASOURCE_TYPE_ENUM) {
						combo_dataSourceType.select(0);
						enumPage.initControlValue();
						switch (PropertyEditor.DATASOURCE_TYPE_ENUM) {
						case 0:
							rootLayout.topControl = tab1;
							rootContainer.layout();
							break;
						case 1:
							rootLayout.topControl = tab2;
							rootContainer.layout();
							break;
						default:
							break;
						}

					} else if (editor.getDataSourceType() == PropertyEditor.DATASOURCE_TYPE_BUSINESS) {
						combo_dataSourceType.select(1);
						businessPage.initControlValue();
						// tabFolder.setSelection(PropertyEditor.DATASOURCE_TYPE_BUSINESS);
						switch (PropertyEditor.DATASOURCE_TYPE_BUSINESS) {
						case 0:
							rootLayout.topControl = tab1;
							rootContainer.layout();
							break;
						case 1:
							rootLayout.topControl = tab2;
							rootContainer.layout();
							break;
						default:
							break;
						}
					}
					/*
					 * else if(editor.getDataSourceType() ==
					 * PropertyEditor.DATASOURCE_TYPE_CUSTOM) {
					 * customPage.initControlValue();
					 * tabFolder.setSelection(PropertyEditor
					 * .DATASOURCE_TYPE_CUSTOM); }
					 */
				}
			}
		}
	}

	/**
	 * 校验输入.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean validateInput() {
		// 是否选择编辑器类型
		ISelection selection = treeViewer.getSelection();
		Object node = ((IStructuredSelection) selection).getFirstElement();
		if (node != null && node instanceof EditorType) {
			if (((EditorType) node).isHasData()) {
				// 数据源来源于业务数据时的校验
				if (PropertyEditor.DATASOURCE_TYPE_BUSINESS == combo_dataSourceType
						.getSelectionIndex()) {
					String errorMessage = businessPage.validateInput();
					if (errorMessage != null) {
						parentDialog.setErrorMessage(errorMessage);
						return false;
					}
				} else if (PropertyEditor.DATASOURCE_TYPE_ENUM == combo_dataSourceType
						.getSelectionIndex()) {
					// 数据源来源于枚举时的校验
					String errorMessage = enumPage.validateInput();
					if (errorMessage != null) {
						parentDialog.setErrorMessage(errorMessage);
						return false;
					}
					
					if(DataType.Boolean.getValue_hibernet().equals(parentDialog.getPropertyBasicInfoPage().getCurDataType())){
						Enumeration enumeration = enumPage.getEnumeration();
						//判断当前是boolean类型时，枚举的key必须是true或false
						List<EnumElement> elements = enumeration.getElements();
						if(elements==null && elements.size()<1){
							errorMessage= "枚举为空，请重新选择！";
							parentDialog.setErrorMessage(errorMessage);
							return false;
						}else{
							for(EnumElement enumEle : elements){
								if(!"true".equals(enumEle.getKey()) && !"false".equals(enumEle.getKey())){
									errorMessage= "当前属性是boolean类型，来源于枚举的key只能是true或者false，请重新设置枚举或重新选择枚举！";
									parentDialog.setErrorMessage(errorMessage);
									return false;
								}
							}
						}
					}
				} else {
					parentDialog.setErrorMessage("请选择下拉框来源数据");
					return false;
				}
			}
		}
		if (parentDialog != null) {
			
			parentDialog.setErrorMessage(null);
		}
		return true;
	}

	/**
	 * 更新正在编辑的对象.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void updateTheEditingElement() {
		PropertyEditor editor = parentDialog.getPropertyCopy().getEditor();
		if (editor == null) {
			editor = new PropertyEditor();
			editor.setConditions(parentDialog.getConditions());
			parentDialog.getPropertyCopy().setEditor(editor);
		}
		editor.setBelongProperty(parentDialog.getPropertyCopy());
		String editorType = curType.getValue();
		editor.setEditorType(editorType);

		if (curType.isHasData()) {
			switch (combo_dataSourceType.getSelectionIndex()) {
			case PropertyEditor.DATASOURCE_TYPE_ENUM:
				Enumeration enumeration = enumPage.getEnumeration();
				if (enumeration != null) {
					editor.getEditorParams().put(
							PropertyEditor.PARAM_KEY_ENUMERATION_ID,
							enumeration.getId());
				}
				editor.setDataSourceType(PropertyEditor.DATASOURCE_TYPE_ENUM);
				break;
			case PropertyEditor.DATASOURCE_TYPE_BUSINESS:
				BusinessClass bc = businessPage.getBusinessClass();
				if (bc != null) {
					editor.getEditorParams().putAll(businessPage.getParamMap());
				}
				editor.setDataSourceType(PropertyEditor.DATASOURCE_TYPE_BUSINESS);
				break;
			/*
			 * case PropertyEditor.DATASOURCE_TYPE_CUSTOM:
			 * editor.setEditorData(customPage.getTableItems());
			 * editor.setDataSourceType(PropertyEditor.DATASOURCE_TYPE_CUSTOM);
			 * break;
			 */
			}
		}

	}

	/**
	 * Gets the editing property.
	 *
	 * @author mqfdy
	 * @return the editing property
	 * @Date 2018-09-03 09:00
	 */
	public Property getEditingProperty() {
		return parentDialog.getPropertyCopy();
	}

	/**
	 * 获取当前编辑器类型.
	 *
	 * @author mqfdy
	 * @return the cur type
	 * @Date 2018-09-03 09:00
	 */
	public EditorType getCurType() {
		return curType;
	}

	/**
	 * 设置当前编辑器类型.
	 *
	 * @author mqfdy
	 * @param curType
	 *            the new cur editor type
	 * @Date 2018-09-03 09:00
	 */
	private void setCurEditorType(EditorType curType) {
		this.curType = curType;
	}

	/**
	 * Gets the parent dialog.
	 *
	 * @author mqfdy
	 * @return the parent dialog
	 * @Date 2018-09-03 09:00
	 */
	public PropertyEditorDialog getParentDialog() {
		return parentDialog;
	}

}
