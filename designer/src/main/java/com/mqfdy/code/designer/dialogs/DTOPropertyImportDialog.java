package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceContentProvider;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceLabelProvider;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.AbstractPackage;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.Property;

/**
 * 导入属性对话框
 * 
 * @author mqfdy
 * 
 */
public class DTOPropertyImportDialog extends ModelElementEditorDialog implements
		IBusinessClassEditorPage {

	public String DIALOG_TITLE = "导入数据传输对象属性";

	private DTOPropertiesPage parentPage;

	private Group groupChoose;

	private Group groupHasChoose;

	private Button buttonToLeft;

	private Button buttonToRight;

	private TreeViewer treeViewer;

	private Table tableCurProperties;

	private TableViewer tableViewerCurProperties;

	private Table tableHasChooseProperties;

	private TableViewer tableViewerHasChooseProperties;

	private String[] modelTypes;

	private List<DTOProperty> dtoProperties = new ArrayList();

	BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	public DTOPropertyImportDialog(Shell parentShell, DTOPropertiesPage page,
			String[] modelTypes) {
		super(parentShell);
		this.parentPage = page;
		this.modelTypes = modelTypes;
	}

	@Override
	protected Control createDialogArea(Composite composite) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		composite.setLayout(gridLayout);
		composite.setSize(700, 500);

		setTitleAndMessage();
		createContent(composite);
		addFilter();
		addListeners();
		initControlValue();
		return composite;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(DIALOG_TITLE);
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_PACKAGE));
	}

	/**
	 * 设置标题和信息
	 */
	public void setTitleAndMessage() {
		setTitle(DIALOG_TITLE);
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		setMessage(DIALOG_TITLE);
	}

	private void createContent(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginTop = 2;
		layout.marginBottom = 2;
		layout.marginLeft = 2;
		layout.marginRight = 2;
		layout.verticalSpacing = 2;
		layout.makeColumnsEqualWidth = false;
		parent.setLayout(layout);

		createGroupChoose(parent);
		createButtons(parent);
		createGroupHasChoose(parent);
	}

	private void createButtons(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true,
				1, 1));

		buttonToRight = new Button(composite, SWT.BUTTON1);
		buttonToRight.setText(">>");
		buttonToRight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		buttonToLeft = new Button(composite, SWT.BUTTON1);
		buttonToLeft.setText("<<");
		buttonToLeft.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

	}

	/**
	 * 创建选择区域
	 */
	private void createGroupChoose(Composite parent) {
		groupChoose = new Group(parent, SWT.NONE);
		groupChoose.setLayout(new GridLayout(2, true));
		groupChoose.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		groupChoose.setText("实体属性选择");

		treeViewer = new TreeViewer(groupChoose);
		treeViewer.setLabelProvider(new ModelResourceLabelProvider());
		treeViewer.setContentProvider(new ModelResourceContentProvider());
		GridData griddata = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		griddata.heightHint = 300;
		treeViewer.getTree().setLayoutData(griddata);

		tableViewerCurProperties = new TableViewer(groupChoose, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.SCROLL_LINE);
		tableCurProperties = tableViewerCurProperties.getTable();
		tableCurProperties.setHeaderVisible(true);
		tableCurProperties.setLinesVisible(true);
		tableViewerCurProperties
				.setContentProvider(new CurPropertiesConentProvider());
		tableViewerCurProperties
				.setLabelProvider(new CurPropertiesLabelProvider());
		String[] columnNames = new String[] { "属性名", "属性显示名" };
		int[] columnWidths = new int[] { 90, 90 };
		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(tableCurProperties,
					columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		tableViewerCurProperties.getTable().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	/**
	 * 创建已选择区域
	 */
	private void createGroupHasChoose(Composite parent) {
		groupHasChoose = new Group(parent, SWT.NONE);
		groupHasChoose.setLayout(new GridLayout(1, true));
		groupHasChoose.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		groupHasChoose.setText("已选择属性");

		tableViewerHasChooseProperties = new TableViewer(groupHasChoose,
				SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.SCROLL_LINE);
		tableViewerHasChooseProperties.getTable().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tableHasChooseProperties = tableViewerHasChooseProperties.getTable();
		tableHasChooseProperties.setHeaderVisible(true);
		tableHasChooseProperties.setLinesVisible(true);
		tableViewerHasChooseProperties
				.setContentProvider(new HasChoosePropertiesConentProvider());
		tableViewerHasChooseProperties
				.setLabelProvider(new HasChoosePropertiesLabelProvider());
		String[] columnNames = new String[] { "实体名", "属性名", "属性显示名" };
		int[] columnWidths = new int[] { 90, 90, 90 };
		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(tableHasChooseProperties,
					columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		tableViewerHasChooseProperties.getTable().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	public void initControlValue() {
		List<BusinessObjectModel> boms = new ArrayList<BusinessObjectModel>();
		BusinessObjectModel bom = manager.getBusinessObjectModel();
		boms.add(bom);
		treeViewer.setInput(boms);

	}

	public boolean validateInput() {
		if (dtoProperties == null || dtoProperties.size() == 0) {
			this.setErrorMessage(ERROR_MESSAGE_PROPERTY_NULLABLE);
			return false;
		}

		return true;
	}

	public void updateTheEditingElement() {
		// 添加到父级页面的列表中
		parentPage.getListProperties().addAll(dtoProperties);
		parentPage.refreshTable();
	}

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

	@Override
	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}

	protected void applylPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
		}

	}

	@Override
	protected void buttonPressed(int buttonId) {
//		if (APPLY_ID == buttonId) {
//			applylPressed();
//		} else {
		super.buttonPressed(buttonId);
//		}
	}

	private boolean validateAllInput() {
		boolean isOk = this.validateInput();
		return isOk;
	}

	/**
	 * 监听
	 */
	private void addListeners() {
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = treeViewer.getSelection();
				AbstractModelElement element = (AbstractModelElement) ((IStructuredSelection) selection)
						.getFirstElement();
				if (element instanceof BusinessClass) {
					// curBusinessClass = (BusinessClass)element;
					changeCurPropertiesDisplay((BusinessClass) element);
				}
			}
		});
		tableViewerCurProperties
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = tableViewerCurProperties
								.getSelection();
						AbstractModelElement element = (AbstractModelElement) ((IStructuredSelection) selection)
								.getFirstElement();
						if (element instanceof Property) {
							// curProperty = (Property)element;
						}
					}
				});
		buttonToRight.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				ISelection selectionBusinessClass = treeViewer.getSelection();
				ISelection selectionProperty = tableViewerCurProperties
						.getSelection();
				if (selectionBusinessClass == null) {
					setErrorMessage(ERROR_MESSAGE_BUSINESSCLASS_NULLABLE);
					return;
				}
				if (selectionProperty == null) {
					setErrorMessage(ERROR_MESSAGE_PROPERTY_NULLABLE);
					return;
				}
				AbstractModelElement elementBusinessClass = (AbstractModelElement) ((IStructuredSelection) selectionBusinessClass)
						.getFirstElement();
				AbstractModelElement elementProperty = (AbstractModelElement) ((IStructuredSelection) selectionProperty)
						.getFirstElement();

				if (elementBusinessClass instanceof BusinessClass
						&& elementProperty instanceof Property) {
					// 当两项均选择时，添加到已选择列表
					if (dtoProperties == null) {
						dtoProperties = new ArrayList();
					}
					DTOProperty dtoProperty = new DTOProperty();
					dtoProperty.setDataType(((Property) elementProperty)
							.getDataType());
					dtoProperty.setName(elementProperty.getName());
					dtoProperty.setDisplayName(elementProperty.getDisplayName());
					dtoProperty.setSourceProperty((Property) elementProperty);
					dtoProperty
							.setSourceBusinessClass((BusinessClass) elementBusinessClass);
					if (!isExists(dtoProperty)) {
						dtoProperties.add(dtoProperty);
					} else {
						setErrorMessage(ERROR_MESSAGE_PROPERTY_REPEAT);
					}

				}
				tableViewerRefresh();
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				// TODO Auto-generated method stub

			}
		});

		buttonToLeft.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent event) {
				ISelection selectionHasChoose = tableViewerHasChooseProperties
						.getSelection();
				if (selectionHasChoose == null) {
					setErrorMessage(ERROR_MESSAGE_PROPERTY_NULLABLE);
					return;
				}
				AbstractModelElement elementDTOProperty = (AbstractModelElement) ((IStructuredSelection) selectionHasChoose)
						.getFirstElement();
				if (elementDTOProperty != null
						&& elementDTOProperty instanceof DTOProperty) {
					dtoProperties.remove((DTOProperty) elementDTOProperty);
					tableViewerRefresh();
				}
			}

		});
	}

	/**
	 * 判断当前需要添加的属性是否在右侧存在
	 * 
	 * @param dtoProperty
	 */
	private boolean isExists(DTOProperty dtoProperty) {
		boolean b = false;
		if (dtoProperties != null) {
			for (DTOProperty temp : dtoProperties) {
				if (temp.getSourceBusinessClass().getId()
						.equals(dtoProperty.getSourceBusinessClass().getId())
						&& temp.getSourceProperty()
								.getId()
								.equals(dtoProperty.getSourceProperty().getId())) {
					b = true;
				}
			}
		}
		return b;
	}

	private void tableViewerRefresh() {
		tableViewerHasChooseProperties.setInput(dtoProperties);
		tableViewerHasChooseProperties.refresh();
	}

	/**
	 * 为树添加过滤器
	 */
	private void addFilter() {
		treeViewer.addFilter(viewerFilter);
	}

	private void changeCurPropertiesDisplay(BusinessClass businessClass) {
		if (businessClass != null) {
			tableViewerCurProperties.setInput(businessClass.getProperties());
		}
		tableViewerCurProperties.refresh();
	}

	private boolean isContain(String[] modelTypes, String type) {
		for (int i = 0; i < modelTypes.length; i++) {
			if (modelTypes[i].equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 当前选择实体属性提供者
	 * 
	 * @author LQR
	 * 
	 */
	private class CurPropertiesConentProvider implements
			IStructuredContentProvider {
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("rawtypes")
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				Object[] objects = ((Collection) inputElement).toArray();

				return objects;
			}
			return new Object[0];
		}

	}

	/**
	 * 已选择实体属性提供者
	 * 
	 * @author LQR
	 * 
	 */
	private class HasChoosePropertiesLabelProvider extends LabelProvider
			implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			DTOProperty property = (DTOProperty) element;
			switch (columnIndex) {
			case 0:
				return property.getSourceBusinessClass().getDisplayName();
			case 1:
				return property.getName();
			case 2:
				return property.getDisplayName();
			}
			return "";
		}
	}

	/**
	 * 已选择实体属性提供者
	 * 
	 * @author LQR
	 * 
	 */
	private class HasChoosePropertiesConentProvider implements
			IStructuredContentProvider {
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("rawtypes")
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				Object[] objects = ((Collection) inputElement).toArray();

				return objects;
			}
			return new Object[0];
		}

	}

	/**
	 * 当前选择实体属性提供者
	 * 
	 * @author LQR
	 * 
	 */
	private class CurPropertiesLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			Property property = (Property) element;
			switch (columnIndex) {
			case 0:
				return property.getName();
			case 1:
				return property.getDisplayName();
			}
			return "";
		}
	}

	private ViewerFilter viewerFilter = new ViewerFilter() {
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if ((element instanceof BusinessObjectModel)
					|| (element instanceof AbstractPackage)) {
				return true;
			}
			if (modelTypes != null && modelTypes.length >= 0) {
				AbstractModelElement ame = (AbstractModelElement) element;
				String type = ame.getType();
				return isContain(modelTypes, type);
			}
			return true;
		}
	};
}
