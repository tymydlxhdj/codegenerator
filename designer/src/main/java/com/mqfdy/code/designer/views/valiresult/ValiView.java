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

// TODO: Auto-generated Javadoc
/**
 * The Class ValiView.
 *
 * @author mqfdy
 */
public class ValiView extends ViewPart {
	
	/** The tab folder. */
	private TabFolder tabFolder;
	
	/** The vali page. */
	private ValiResultPage valiPage;
	
	/** The fo page. */
	private FindObjectResultPage foPage;
	
	/** The viewid. */
	public static String VIEWID = "com.mqfdy.code.designer.views.valiresult.ValiView";

	/**
	 * Instantiates a new vali view.
	 */
	public ValiView() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates the part control.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void createPartControl(Composite parent) {
		createTable(parent);
	}

	/**
	 * Creates the table.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * 
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	/**
	 * Sets the file path.
	 *
	 * @author mqfdy
	 * @param path
	 *            the new file path
	 * @Date 2018-09-03 09:00
	 */
	public void setFilePath(String path){
		valiPage.setFilePath(path);
		foPage.setFilePath(path);
	}
	
	/**
	 * Sets the vali data.
	 *
	 * @author mqfdy
	 * @param valiResult
	 *            the new vali data
	 * @Date 2018-09-03 09:00
	 */
	public void setValiData(List<ValiResult> valiResult) {
		valiPage.setData(valiResult);
		tabFolder.setSelection(0);
	}

	/**
	 * Sets the fo data.
	 *
	 * @author mqfdy
	 * @param result
	 *            the new fo data
	 * @Date 2018-09-03 09:00
	 */
	public void setFoData(List<AbstractModelElement> result) {
		foPage.setData(result);
		tabFolder.setSelection(1);
	}

	/**
	 * Gets the vali page.
	 *
	 * @author mqfdy
	 * @return the vali page
	 * @Date 2018-09-03 09:00
	 */
	public ValiResultPage getValiPage() {
		return valiPage;
	}

	/**
	 * Gets the fo page.
	 *
	 * @author mqfdy
	 * @return the fo page
	 * @Date 2018-09-03 09:00
	 */
	public FindObjectResultPage getFoPage() {
		return foPage;
	}

}
