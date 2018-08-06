package com.mqfdy.code.designer.views.valiresult;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.ViewPart;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.resource.validator.ValiResult;

public class ValiView extends ViewPart {
	private TabFolder tabFolder;
	private ValiResultPage valiPage;
	private FindObjectResultPage foPage;
	public static String VIEWID = "com.mqfdy.code.designer.views.valiresult.ValiView";

	public ValiView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		createTable(parent);
	}

	private void createTable(Composite parent) {
//		BusinessModelManager manager = null;
//		if(BusinessModelUtil.getBusinessModelDiagramEditor() != null){
//			manager = BusinessModelUtil.getBusinessModelDiagramEditor().getBusinessModelManager();
//		}
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.heightHint = 150;
		// tab页
		tabFolder = new TabFolder(parent, SWT.BOTTOM);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("校验结果");
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		tabItem2.setText("查询结果");
		valiPage = new ValiResultPage(tabFolder, SWT.NONE);
		tabFolder.getItem(0).setControl(valiPage);
		foPage = new FindObjectResultPage(tabFolder, SWT.NONE);
		tabFolder.getItem(1).setControl(foPage);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	public void setFilePath(String path){
		valiPage.setFilePath(path);
		foPage.setFilePath(path);
	}
	public void setValiData(List<ValiResult> valiResult) {
		valiPage.setData(valiResult);
		tabFolder.setSelection(0);
	}

	public void setFoData(List<AbstractModelElement> result) {
		foPage.setData(result);
		tabFolder.setSelection(1);
	}

	public ValiResultPage getValiPage() {
		return valiPage;
	}

	public FindObjectResultPage getFoPage() {
		return foPage;
	}

}
