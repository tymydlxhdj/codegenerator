package com.mqfdy.code.designer.editor.actions.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;

/**
 * 模型校验对象选择
 * 
 * @author mqfdy
 * 
 */
public class ValidatorObjectSelectPage extends Composite {
	private BusinessObjectModel businessObjectModel;
	// private ModelSelectPage pkgPage;
	private ModelSelectPage buPage;
	private ModelSelectPage asPage;
	// private ModelSelectPage inPage;
	private ModelSelectPage enumPage;
	// private ModelSelectPage dtoPage;
	private List<AbstractModelElement> selectedModels = new ArrayList<AbstractModelElement>();

	public ValidatorObjectSelectPage(BusinessObjectModel businessObjectModel,
			Composite parent, int style) {
		super(parent, style);
		this.businessObjectModel = businessObjectModel;// BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
		createContents(this);
	}

	private void createContents(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		// 创建标签页
		TabFolder tabFolder = new TabFolder(parent, SWT.BOTTOM);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("实体");
		TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
		tabItem1.setText("枚举");
		// TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		// tabItem2.setText("DTO");
		TabItem tabItem3 = new TabItem(tabFolder, SWT.NONE);
		tabItem3.setText("关联关系");
		// TabItem tabItem4 = new TabItem(tabFolder, SWT.NONE);
		// tabItem4.setText("继承关系");
		// tab 内容
		buPage = new ModelSelectPage(businessObjectModel, tabFolder, SWT.NONE,
				"BusinessClass",null);
		enumPage = new ModelSelectPage(businessObjectModel, tabFolder,
				SWT.NONE, "Enumeration",null);
		// dtoPage = new ModelSelectPage(businessObjectModel,tabFolder,
		// SWT.NONE, this, "DTO");
		asPage = new ModelSelectPage(businessObjectModel, tabFolder, SWT.NONE,
				"Association",null);
		// inPage = new ModelSelectPage(businessObjectModel,tabFolder, SWT.NONE,
		// this, "Inheritance");
		tabFolder.getItem(0).setControl(buPage);
		tabFolder.getItem(1).setControl(enumPage);
		// tabFolder.getItem(2).setControl(dtoPage);
		tabFolder.getItem(2).setControl(asPage);
		// tabFolder.getItem(3).setControl(inPage);
	}

	public List<AbstractModelElement> getSelectedModels() {
		selectedModels.clear();
		selectedModels.add(businessObjectModel);
		selectedModels.addAll(businessObjectModel.getPackages());
		selectedModels.addAll(businessObjectModel.getDiagrams());
		selectedModels.addAll(buPage.getSelectedElements());
		selectedModels.addAll(enumPage.getSelectedElements());
		// selectedModels.addAll(dtoPage.getSelectedElements());
		selectedModels.addAll(asPage.getSelectedElements());
		// selectedModels.addAll(inPage.getSelectedElements());
		return selectedModels;
	}
}
