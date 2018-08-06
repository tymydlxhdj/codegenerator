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
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 属性编辑器页面
 * 
 * @author mqfdy
 * 
 */
public class FkEditorPage extends Composite implements
		IBusinessClassEditorPage {

	// private static final String ERROR_MESSAGE_EDITORTYPE_NULLABLE
	// ="请选择编辑器类型";

	public static final String GROUP_EDITORTYPELIST_TEXT = "编辑器类型选择";
	// public static final String GROUP_EDITORSHOW_TEXT = "编辑器效果";
	public static final String GROUP_EDITORPARAM_TEXT = "参数设置";

	public static final String DATASOURCE_LABEL_TEXT = "数据来源：";

	public static final String TAB_ENUM_TITLE = "来源于枚举";
	public static final String TAB_BUSINESS_TITLE = "来源于业务数据";
	// public static final String TAB_CUSTOM_TITLE = "自定义";

	/**
	 * 数据源
	 */
	// public static final String[] datasources = new
	// String[]{TAB_ENUM_TITLE,TAB_BUSINESS_TITLE,TAB_CUSTOM_TITLE};
	public static final String[] datasources = new String[] { TAB_ENUM_TITLE,
			TAB_BUSINESS_TITLE };

	private FkEditorDialog parentDialog;

	private Group group_editorTypeList;
	// private Group group_editorShow;
	private Group group_editorParam;
	Composite composite;
	/**
	 * 图片显示容器
	 */
	// private Composite imageCantainer;

	/**
	 * 编辑器类型树
	 */
	private Tree tree;
	private TreeViewer treeViewer;

	private Label label_dataSourceType;
	private Combo combo_dataSourceType;

	// private TabFolder tabFolder;

	/**
	 * 枚举数据源页面
	 */
	private DataSourceEnumPage enumPage;

	/**
	 * 业务数据源页面
	 */
	private DataSourceBusinessPage businessPage;

	EditorType curType = EditorType.TextEditor;

	/**
	 * 自定义数据源页面
	 */
	// private DataSourceCustomPage customPage;

	final StackLayout rootLayout = new StackLayout();// 堆栈布局方式;
	private Composite tab1;
	private Composite tab2;
	private Composite rootContainer;

	// private Composite tab3;
	/**
	 * 构造函数
	 * 
	 * @param parent
	 *            上级容器
	 * @param style
	 *            样式
	 * @param fkEditorDialog
	 *            属性编辑弹出窗
	 */
	public FkEditorPage(Composite parent, int style,
			FkEditorDialog fkEditorDialog) {
		super(parent, style);
		this.parentDialog = fkEditorDialog;
		createContents(this);
	}

	/**
	 * 创建页面内容
	 * 
	 * @param composite
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
	 * 初始化创建数据源标签页
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

		// 创建数据源选择页面
		enumPage = new DataSourceEnumPage(tab1, SWT.NONE, this);
		tab2 = new Composite(rootContainer, SWT.NULL);
		tab2.setLayout(new GridLayout());
		businessPage = new DataSourceBusinessPage(tab2, SWT.NONE, this);
		rootLayout.topControl = tab1;
	}

	/**
	 * 初始化化事件
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
	 * 重置编辑器
	 * 
	 * @param editorType
	 *            编辑器类型枚举
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
			// add by mqfdy 
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
	 * 根据数据类型更改树的选中节点
	 * 
	 * @param dataType
	 */
	public void change(String dataType) {
		DataType selectType = DataType.getDataType(dataType);
		EditorType type = getEditorTypeBySelect(selectType);// 返回null表示不需要切换选中项
		if(curType != null){
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
	 * 为选择的数据类型计算默认编辑器
	 * 
	 * @param selectType
	 * @return
	 */
	private EditorType getEditorTypeBySelect(DataType selectType) {
		EditorType type = EditorType.TextEditor;
		if(curType!=null){
			type = curType;
		}
		if (selectType == null) {
			// 返回已保存的编辑器类型
			if (((Association) parentDialog.parent)
					.getEditor() != null) {
				type = EditorType
						.getEditorType(((Association) parentDialog.parent)
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
	 * 判断当前节点是否应该被选中，如果是，则选中
	 * 
	 * @param item
	 * @param type
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
	 * 初始化控件的值和内容
	 */
	public void initControlValue() {
		combo_dataSourceType.setItems(datasources);
		treeViewer.expandAll();
		if (parentDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
			PropertyEditor editor = ((Association) parentDialog.parent)
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
								(((Association) parentDialog.parent)
										.getEditor().getEditorType()))) {
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
			}
		}
		change(((Association) parentDialog.parent).getClassA().getPkProperty().getDataType());
	}

	/**
	 * 校验输入
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
	 * 更新正在编辑的对象
	 */
	public void updateTheEditingElement() {
		PropertyEditor editor = ((Association) parentDialog.parent)
				.getEditor();
		if (editor == null) {
			editor = new PropertyEditor();
			((Association) parentDialog.parent).setEditor(editor);
		}
		editor.setConditions(parentDialog.getConditions());
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
			}
		}

	}

//	public Property getEditingProperty() {
//		return parentDialog.getPropertyCopy();
//	}

	/**
	 * 获取当前编辑器类型
	 * 
	 * @return
	 */
	public EditorType getCurType() {
		return curType;
	}

	/**
	 * 设置当前编辑器类型
	 */
	private void setCurEditorType(EditorType curType) {
		this.curType = curType;
	}

	public FkEditorDialog getParentDialog() {
		return parentDialog;
	}
	/**
	 * 编辑器类型树的标签显示提供者
	 * 
	 * @author LQR
	 * 
	 */
	private class EditorTypeTreeLabelProvider extends LabelProvider implements
			ILabelProvider {
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
	 * 编辑器类型树的内容提供者
	 * 
	 * @author LQR
	 * 
	 */
	private class EditorTypeTreeContentProvider implements ITreeContentProvider {

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object inputElement) {
			return EditorType.getEditorTypeGroups().toArray();
		}

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof String) {
				List<EditorType> list = EditorType
						.getEditorTypes((String) parentElement);
				return list.toArray();
			}
			return null;
		}

		public Object getParent(Object element) {
			if (element instanceof EditorType) {
				return ((EditorType) element).getGroup();
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof String) {
				return true;
			}
			return false;
		}

	}

}
