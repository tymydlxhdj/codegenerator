package com.mqfdy.code.designer.editor.actions.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.views.modelresource.tree.FilteredTree;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.OperationParam;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;

// TODO: Auto-generated Javadoc
/**
 * 代码生成向导对象选择页面.
 *
 * @author mqfdy
 */
public class ModelSelectPage extends Composite {
	
	/** The business object model. */
	private BusinessObjectModel businessObjectModel;
	
	/** The per. */
	private Label per;// 显示选择数量
	
	/** The query checkbox table viewer. */
	private CheckboxTableViewer queryCheckboxTableViewer;

	/** The all elements. */
	List<AbstractModelElement> allElements = new ArrayList<AbstractModelElement>();
	
	/** The selected elements. */
	List<AbstractModelElement> selectedElements = new ArrayList<AbstractModelElement>();// 模型选择的元素
	
	/** The list type. */
	private String listType;
	
	/** The generator object select page. */
	private GeneratorObjectSelectPage generatorObjectSelectPage;
	
	/** The filter text. */
	private Text filterText;

	/**
	 * Instantiates a new model select page.
	 *
	 * @param businessObjectModel
	 *            the business object model
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param listType
	 *            the list type
	 * @param generatorObjectSelectPage
	 *            the generator object select page
	 */
	public ModelSelectPage(BusinessObjectModel businessObjectModel,
			Composite parent, int style, String listType, GeneratorObjectSelectPage generatorObjectSelectPage) {
		super(parent, style);
		this.listType = listType;
		this.generatorObjectSelectPage = generatorObjectSelectPage;
		this.businessObjectModel = businessObjectModel;// BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
		if (listType.equals("BusinessClass")) {
			this.allElements.addAll(businessObjectModel.getBusinessClasses());
		} else if (listType.equals("Association")) {
			this.allElements.addAll(businessObjectModel.getAssociations());
		} else if (listType.equals("Enumeration")) {
			this.allElements.addAll(businessObjectModel.getEnumerations());
		} else if (listType.equals("Inheritance")) {
			this.allElements.addAll(businessObjectModel.getInheritances());
		} else if (listType.equals("DTO")) {
			this.allElements.addAll(businessObjectModel.getDTOs());
		}
		createContents(this);

	}

	/**
	 * Creates the contents.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	public void createContents(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite filterComposite;
		filterComposite = new Composite(this, SWT.BORDER);
		filterComposite.setBackground(getDisplay().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND));
		GridLayout filterLayout = new GridLayout(3, false);
		filterLayout.marginHeight = 0;
		filterLayout.marginWidth = 0;
		filterComposite.setLayout(filterLayout);
		filterComposite.setFont(getFont());

		createFilterControls(filterComposite);
		GridData f = new GridData();
		f.grabExcessHorizontalSpace = true;
		f.grabExcessVerticalSpace = true;
		f.horizontalSpan = 3;
		f.horizontalAlignment = GridData.FILL;
		f.verticalAlignment = GridData.FILL;
		filterComposite.setLayoutData(f);
		
		/*
		 * 定义一个TableViewer对象。式样：MULTI可多选、H_SCROLL有水平
		 * 滚动条、V_SCROLL有垂直滚动条、BORDER有边框、FULL_SELECTION整行选择
		 */
		Table table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.CHECK);
		queryCheckboxTableViewer = new CheckboxTableViewer(table);// 对表格进行封装，
		queryCheckboxTableViewer.setContentProvider(new ListContentProvider());
		TableViewerProvider.createViewerColumn(queryCheckboxTableViewer);
		/*
		 * 通过TableViewer中的Table对其布局
		 */
		table.setHeaderVisible(true); // 显示表头
		table.setLinesVisible(true); // 显示表格线
		TableLayout tLayout = new TableLayout(); // 专用于表格的布局
		table.setLayout(tLayout);
		GridData gridData = new GridData();
		/**
		 * 为tableViewer设置大小以及布局样式
		 */
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.widthHint = 150;
		gridData.heightHint = 350;
		table.setLayoutData(gridData);// 给Table运用已经定义好的布局样式
		queryCheckboxTableViewer.setInput(allElements);
		selectedElements.addAll(allElements);
		queryCheckboxTableViewer.setAllChecked(true);
		/**
		 * 使用queryCheckboxTableViewer中的字段被选中时event.getChecked()返回值，
		 * 为IViewColumnModel的showType赋值
		 * 
		 * 表格中的选择框被选中或者取消选择时用的监听器
		 * 
		 */
		queryCheckboxTableViewer
				.addCheckStateListener(new ICheckStateListener() {

					public void checkStateChanged(CheckStateChangedEvent event) {
						if(event.getChecked()){
							if(!selectedElements.contains(event.getElement()))
								selectedElements.add((AbstractModelElement) event.getElement());
						}
						else{
							selectedElements.remove(event.getElement());
						}
						
						
						
						
						if(generatorObjectSelectPage == null)
							return;
						if (listType.equals("BusinessClass")) {
						/*	if(event.getChecked())
								return;*/
							// 反选业务实体时，取消对应的关联关系
							BusinessClass bu = (BusinessClass) event.getElement();
							TableItem[] itemsBus = generatorObjectSelectPage.getBuPage().queryCheckboxTableViewer.getTable().getItems();
							List<Association> asList = getAssociationsByBusinessClass(bu);
							CheckboxTableViewer tableViewer = generatorObjectSelectPage.getAsPage().queryCheckboxTableViewer;
						
							TableItem[] items = tableViewer.getTable().getItems();
							for(int i = 0; i < items.length; i++){
								if(asList.contains(items[i].getData())){
									List<BusinessClass> bcList = getBcList((Association)items[i].getData());
									if(event.getChecked()){
										if(!generatorObjectSelectPage.getAsPage().selectedElements.contains(items[i].getData()) && isBcChecked(bcList)){
											items[i].setChecked(true);
											generatorObjectSelectPage.getAsPage().selectedElements.add((AbstractModelElement) items[i].getData());
										}
											
									}
									if(!event.getChecked()){
										items[i].setChecked(false);
										generatorObjectSelectPage.getAsPage().selectedElements.remove((AbstractModelElement) items[i].getData());
									}
								}
								generatorObjectSelectPage.getAsPage().per.setText("已选择对象数："
										+ generatorObjectSelectPage.getAsPage().selectedElements.size()
										+ "/" + generatorObjectSelectPage.getAsPage().allElements.size());
							}
							//added by mqf 枚举
							List<Enumeration> enums = getEnums(bu);
							if(enums != null && !enums.isEmpty()){
								CheckboxTableViewer etableViewer = generatorObjectSelectPage.getEnumPage().queryCheckboxTableViewer;
								TableItem[] eitems = etableViewer.getTable().getItems();
								
								for(Enumeration en : enums){
									for(int i = 0; i < eitems.length; i++){
										AbstractModelElement itemEnum = (AbstractModelElement)eitems[i].getData();
										if(event.getChecked()){
											if(en.equals(itemEnum) && !generatorObjectSelectPage.getEnumPage().selectedElements.contains(itemEnum)){
												eitems[i].setChecked(true);
												generatorObjectSelectPage.getEnumPage().selectedElements.add(itemEnum);
											}
										}
									}
								}
								generatorObjectSelectPage.getEnumPage().per.setText("已选择对象数："
										+ generatorObjectSelectPage.getEnumPage().selectedElements.size()
										+ "/" + generatorObjectSelectPage.getEnumPage().allElements.size());
							}
							//add by xuran
							List<BusinessOperation> operations = bu.getOperations();
							if(operations!=null && operations.size()>0){
								for(BusinessOperation operation : operations){
									if(operation.getOperationType().equals(BusinessOperation.OPERATION_TYPE_CUSTOM)){
										//自定义方法
										List<OperationParam> paramMap = operation.getOperationParams();
										if(paramMap != null && paramMap.size()>0){
											for(OperationParam param : paramMap){
												String dateType = param.getDataType();//参数数据类型
												for(int i = 0; i < itemsBus.length; i++){
													BusinessClass bc = (BusinessClass)itemsBus[i].getData();
													if(dateType != null && dateType.equals(bc.getName())){
														//需要自动添加
														itemsBus[i].setChecked(event.getChecked());
														if(event.getChecked()){
															if(!generatorObjectSelectPage.getBuPage().selectedElements.contains(itemsBus[i].getData()))
																generatorObjectSelectPage.getBuPage().selectedElements.add((AbstractModelElement) itemsBus[i].getData());
														}
														if(!event.getChecked()){
															generatorObjectSelectPage.getBuPage().selectedElements.remove((AbstractModelElement) itemsBus[i].getData());
														}
													}
													
												}
											}
										}
									}
								}
							}
							//add by xuran
						} else if (listType.equals("Association")) {
							// 反选关联关系时，取消对应的业务实体
							Association as = (Association) event.getElement();
							CheckboxTableViewer tableViewer = generatorObjectSelectPage.getBuPage().queryCheckboxTableViewer;
						
							TableItem[] items = tableViewer.getTable().getItems();
							for(int i = 0; i < items.length; i++){
								if(items[i].getData() == as.getClassA()){
									items[i].setChecked(event.getChecked());
									if(event.getChecked()){
										if(!generatorObjectSelectPage.getBuPage().selectedElements.contains(items[i].getData()))
											generatorObjectSelectPage.getBuPage().selectedElements.add((AbstractModelElement) items[i].getData());
									}
									if(!event.getChecked() && !hasOtherAss(as,selectedElements,(BusinessClass) items[i].getData())){
										generatorObjectSelectPage.getBuPage().selectedElements.remove((AbstractModelElement) items[i].getData());
									}
								}
								if(items[i].getData() == as.getClassB()){
									items[i].setChecked(event.getChecked());
									if(event.getChecked()){
										if(!generatorObjectSelectPage.getBuPage().selectedElements.contains(items[i].getData()))
											generatorObjectSelectPage.getBuPage().selectedElements.add((AbstractModelElement) items[i].getData());
									}
									if(!event.getChecked() && !hasOtherAss(as,selectedElements,(BusinessClass) items[i].getData())){
										generatorObjectSelectPage.getBuPage().selectedElements.remove((AbstractModelElement) items[i].getData());
									}
								}
							}
							generatorObjectSelectPage.getBuPage().per.setText("已选择对象数："
									+ generatorObjectSelectPage.getBuPage().selectedElements.size()
									+ "/" + generatorObjectSelectPage.getBuPage().allElements.size());
						} /*else if (listType.equals("Enumeration")) {
						} else if (listType.equals("Inheritance")) {
						} else if (listType.equals("DTO")) {
						}*/
						
						per.setText("已选择对象数："
								+ selectedElements.size()
								+ "/" + allElements.size());
						
					}

				});
		Button selectAllButton = new Button(composite, SWT.PUSH);
		selectAllButton.setText("全部选中");
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			/*
			 * 全部选中表格中的内容
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				queryCheckboxTableViewer.setAllChecked(true);
//				selectedElements.removeAll(selectedElements);
				for (Object ab : queryCheckboxTableViewer.getCheckedElements()) {
					if(!selectedElements.contains(ab))
						selectedElements.add((AbstractModelElement) ab);
				}
				per.setText("已选择对象数："
						+ selectedElements.size()
						+ "/" + allElements.size());
//				if(generatorObjectSelectPage == null)
//					return;
//				if (listType.equals("BusinessClass")) {
//					// 全选业务实体时，选中关联关系
//					CheckboxTableViewer tableViewer = generatorObjectSelectPage.getAsPage().queryCheckboxTableViewer;
//				
//					TableItem[] items = tableViewer.getTable().getItems();
//					for(int i = 0; i < items.length; i++){
//						items[i].setChecked(true);
//					}
//				} else 
				if (listType.equals("Association")) {
				// 全选关联关系时，选中对应的业务实体
				CheckboxTableViewer tableViewer = generatorObjectSelectPage.getBuPage().queryCheckboxTableViewer;
//				generatorObjectSelectPage.getBuPage().selectedElements.clear();
				TableItem[] items = tableViewer.getTable().getItems();
				for(int i = 0; i < items.length; i++){
					items[i].setChecked(true);
					if(!generatorObjectSelectPage.getBuPage().selectedElements.contains(items[i].getData()))
						generatorObjectSelectPage.getBuPage().selectedElements.add((AbstractModelElement) items[i].getData());
				}
			}
			}

		});

		Button cancelAllButton = new Button(composite, SWT.PUSH);
		cancelAllButton.setText("全部取消");
		cancelAllButton.addSelectionListener(new SelectionAdapter() {
			/*
			 * 全部取消表格中选中的内容
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				
//				selectedElements.removeAll(selectedElements);
				for (Object ab : queryCheckboxTableViewer.getCheckedElements()) {
					selectedElements.remove((AbstractModelElement) ab);
				}
				per.setText("已选择对象数："
						+ selectedElements.size()
						+ "/" + allElements.size());
				queryCheckboxTableViewer.setAllChecked(false);
				if(generatorObjectSelectPage == null)
					return;
				if (listType.equals("BusinessClass")) {
					// 全部取消业务实体时，取消关联关系
					CheckboxTableViewer tableViewer = generatorObjectSelectPage.getAsPage().queryCheckboxTableViewer;
					generatorObjectSelectPage.getAsPage().selectedElements.clear();
					
					TableItem[] items = tableViewer.getTable().getItems();
					for(int i = 0; i < items.length; i++){
						items[i].setChecked(false);
					}
				} 
			}

		});

		per = new Label(composite, SWT.RIGHT);
		GridData gridData1 = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData1.grabExcessHorizontalSpace = true;
		per.setLayoutData(gridData1);
		per.setText("已选择对象数："
				+ selectedElements.size() + "/"
				+ allElements.size());
	}
	
	/**
	 * 获取当前业务对象相关的枚举对象集合.
	 *
	 * @author mqfdy
	 * @param bu
	 *            the bu
	 * @return the enums
	 * @Date 2018-09-03 09:00
	 */
	protected List<Enumeration> getEnums(BusinessClass bu) {
		List<Enumeration> enums = new ArrayList<Enumeration>();
		List<Enumeration> bomEnums = businessObjectModel.getEnumerations();
		for(Property p : bu.getProperties()){
			PropertyEditor pe = p.getEditor();
			if(pe.getDataSourceType()==0){
				String dictEnumId = pe.getEditorParams().get(
						PropertyEditor.PARAM_KEY_ENUMERATION_ID);
				if(dictEnumId != null){
					for (Enumeration en : bomEnums) {
						if (dictEnumId.equals(en.getId())) {
							enums.add(en);
						}
					}
				}
			}
		}
		return enums;
	}

	/**
	 * 判断当前选中的关联关系中除了as之外是否有与BusinessClass对象相关的关联关系.
	 *
	 * @author mqfdy
	 * @param as
	 *            the as
	 * @param selectedElements
	 *            the selected elements
	 * @param bc
	 *            the bc
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected boolean hasOtherAss(Association as, List<AbstractModelElement> selectedElements, BusinessClass bc) {
		for(AbstractModelElement me : selectedElements){
			Association ass2 = (Association)me;
			if(!as.equals(ass2) && (bc.equals(ass2.getClassA()) || bc.equals(ass2.getClassB()))){
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断业务对象是否被选中.
	 *
	 * @author mqfdy
	 * @param bcList
	 *            the bc list
	 * @return true, if is bc checked
	 * @Date 2018-09-03 09:00
	 */
	protected boolean isBcChecked(List<BusinessClass> bcList) {
		TableItem[] itemsBus = generatorObjectSelectPage.getBuPage().queryCheckboxTableViewer.getTable().getItems();
		for(BusinessClass bc : bcList){
			for(int i = 0; i < itemsBus.length; i++){
				BusinessClass curBc = (BusinessClass)itemsBus[i].getData();
				if(curBc.getId().equals(bc.getId()) && !itemsBus[i].getChecked()){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 获取关联关系相关的业务对象列表.
	 *
	 * @author mqfdy
	 * @param association
	 *            the association
	 * @return the bc list
	 * @Date 2018-09-03 09:00
	 */
	protected List<BusinessClass> getBcList(Association association) {
		List<BusinessClass> bcList = new ArrayList<BusinessClass>();
		bcList.add(association.getClassA());
		bcList.add(association.getClassB());
		return bcList;
	}

	/**
	 * Gets the selected elements.
	 *
	 * @author mqfdy
	 * @return the selected elements
	 * @Date 2018-09-03 09:00
	 */
	public List<AbstractModelElement> getSelectedElements() {
		return selectedElements;

	}
	
	/**
	 * Gets the associations by business class.
	 *
	 * @author mqfdy
	 * @param businessClass
	 *            the business class
	 * @return the associations by business class
	 * @Date 2018-09-03 09:00
	 */
	public List<Association> getAssociationsByBusinessClass(
			BusinessClass businessClass) {
		List<Association> associations = businessObjectModel.getAssociations();
		List<Association> results = new ArrayList<Association>();
		if (associations == null || associations.size() < 1) {
			return results;
		}
		for (Association ass : associations) {
			if (ass != null && businessClass != null && ass.getClassA() != null
					&& ass.getClassB() != null) {
				if (ass.getClassA().getId().equals(businessClass.getId())
						|| ass.getClassB().getId()
								.equals(businessClass.getId())) {
					results.add(ass);
				}
			}
		}
		return results;
	}
	
	/**
	 * Creates the filter controls.
	 *
	 * @author mqfdy
	 * @param filterComposite
	 *            the filter composite
	 * @Date 2018-09-03 09:00
	 */
	private void createFilterControls(Composite filterComposite) {
		createFilterText(filterComposite);
		createClearTextNew(filterComposite);
	}
	
	/**
	 * Creates the filter text.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	protected void createFilterText(Composite parent) {
		filterText = new Text(parent, SWT.SINGLE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0)
			gridData.horizontalSpan = 2;
		filterText.setLayoutData(gridData);
		filterText.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				filter();
			}
		});
	}
	
	/**
	 * Creates the clear text new.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	private void createClearTextNew(Composite parent) {
		final Image inactiveImage = ImageManager.getInstance().getImage(
				FilteredTree.getDisabledClearIcon());
		final Image activeImage = ImageManager.getInstance().getImage(
				FilteredTree.getClearIcon());
		final Image pressedImage = new Image(getDisplay(), activeImage,
				SWT.IMAGE_GRAY);

		final Label clearButton = new Label(parent, SWT.NONE);
		clearButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
				false, false));
		clearButton.setImage(inactiveImage);
		clearButton.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND));
		clearButton.setToolTipText("清空");
		clearButton.addMouseListener(new MouseAdapter() {

			public void mouseDown(MouseEvent e) {
				clearButton.setImage(pressedImage);
				filter();
			}

			public void mouseUp(MouseEvent e) {
				filterText.setText("");
				filter();
				filterText.setFocus();
			}

		});
	}
	
	/**
	 * Filter.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void filter(){
		String name = filterText.getText();
		if(name.trim().equals("")){
			queryCheckboxTableViewer.setInput(allElements);
		}else{
			List<AbstractModelElement> serElements = new ArrayList<AbstractModelElement>();
			for(AbstractModelElement ele : allElements){
				if(ele.getName().toUpperCase(Locale.getDefault()).contains(name.toUpperCase(Locale.getDefault()))
						|| ele.getDisplayName().toUpperCase(Locale.getDefault()).contains(name.toUpperCase(Locale.getDefault()))){
					serElements.add(ele);
				}
			}
			queryCheckboxTableViewer.setInput(serElements);
			queryCheckboxTableViewer.refresh();
		}
//		for(AbstractModelElement ele : selectedElements){
//			queryCheckboxTableViewer.setChecked(ele, true);
//		}
		TableItem[] items = queryCheckboxTableViewer.getTable().getItems();
		for(int i = 0; i < items.length; i++){
			if(selectedElements.contains(items[i].getData())){
				items[i].setChecked(true);
			}
		}
	}
}