package com.mqfdy.code.designer.editor.actions.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;

// TODO: Auto-generated Javadoc
/**
 * 代码生成对话框.
 *
 * @author mqfdy
 */
public class FindObjectDiaolg extends TitleAreaDialog {
	
	/** 标签页对象. */
	private TabFolder tabFolder;
	
	/** The fo page. */
	private FindObjectItemsPage foPage;
	
	/** The ok button. */
	private Button okButton;
	
	/** The case sensitive. */
	private boolean caseSensitive;
	
	/** The search type. */
	private String searchType;
	
	/** The search name. */
	private String searchName;
	
	/** The search dis name. */
	private String searchDisName;

	/** The findobject searchtype. */
	public static String FINDOBJECT_SEARCHTYPE = "fo_searchType";
	
	/** The findobject searchname. */
	public static String FINDOBJECT_SEARCHNAME = "fo_searchName";
	
	/** The findobject searchdisname. */
	public static String FINDOBJECT_SEARCHDISNAME = "fo_searchDisName";
	
	/** The findobject casesensitive. */
	public static String FINDOBJECT_CASESENSITIVE = "fo_caseSensitive";

	/**
	 * Instantiates a new find object diaolg.
	 *
	 * @param shell
	 *            the shell
	 */
	public FindObjectDiaolg(Shell shell) {
		super(shell);
	}

	/**
	 * 操作按钮.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("模型查询");
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM);
		newShell.setImage(icon);
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
	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		setTitle("根据名称和显示名称查询模型");
		// setMessage("",1);
		// 创建标签页对象
		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("查询设置");
		// TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		// tabItem2.setText("对象选择");
		// tab 内容
		foPage = new FindObjectItemsPage(tabFolder, SWT.NONE);
		tabFolder.getItem(0).setControl(foPage);
		return parent;
	}

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		BusinessObjectModel model = BusinessModelUtil
				.getEditorBusinessModelManager().getBusinessObjectModel();
		List<AbstractModelElement> reList = new ArrayList<AbstractModelElement>();
		searchType = foPage.getType();
		searchName = foPage.getObjectName();
		searchDisName = foPage.getDisplayName();
		caseSensitive = foPage.isCaseSensitive();

		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		store.setValue(FINDOBJECT_SEARCHTYPE, searchType);
		store.setValue(FINDOBJECT_SEARCHNAME, searchName);
		store.setValue(FINDOBJECT_SEARCHDISNAME, searchDisName);
		store.setValue(FINDOBJECT_CASESENSITIVE, caseSensitive);
		BusinessModelEditorPlugin.getDefault().savePluginPreferences();
		if ("".equals(searchType) && "".equals(searchName)
				&& "".equals(searchDisName)) {
			reList.addAll(model.getPackages());
			reList.add(model);
			for (BusinessClass bu : model.getBusinessClasses()) {
				reList.add(bu);
				reList.addAll(bu.getProperties());
				for (BusinessOperation op : bu.getOperations()) {
					if (!op.getOperationType().equals(
							BusinessOperation.OPERATION_TYPE_STANDARD)) {
						reList.add(op);
					}
				}
			}
			reList.addAll(model.getAssociations());
			reList.addAll(model.getEnumerations());
			reList.addAll(model.getDiagrams());
			reList.addAll(model.getReferenceObjects());
		} else
			reList = getResult(model);
//		if(reList.size() == 0){
//			super.okPressed();
//			return;
//		}
		IWorkbenchWindow dw = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = dw.getActivePage();
		try {
			if (page != null) {
				// 打开视图
				page.showView(ValiView.VIEWID);
			}
		} catch (PartInitException e) {
			Logger.log(e);
		}
		BusinessModelUtil.getView(ValiView.class).setFilePath(BusinessModelUtil.getEditorBusinessModelManager().getPath());
		BusinessModelUtil.getView(ValiView.class).setFoData(reList);
		BusinessModelUtil.getBusinessModelDiagramEditor().setFindResult(reList);
		super.okPressed();
	}

	/**
	 * Gets the result.
	 *
	 * @author mqfdy
	 * @param model
	 *            the model
	 * @return the result
	 * @Date 2018-09-03 09:00
	 */
	private List<AbstractModelElement> getResult(BusinessObjectModel model) {
		List<AbstractModelElement> reList = new ArrayList<AbstractModelElement>();
		List<AbstractModelElement> resultList = new ArrayList<AbstractModelElement>();
		reList.add(model);
		reList.addAll(model.getAssociations());
		for (BusinessClass bu : model.getBusinessClasses()) {
			reList.add(bu);
			reList.addAll(bu.getProperties());
			for (BusinessOperation op : bu.getOperations()) {
				if (!op.getOperationType().equals(
						BusinessOperation.OPERATION_TYPE_STANDARD)) {
					reList.add(op);
				}
			}
		}
		reList.addAll(model.getDiagrams());
		reList.addAll(model.getEnumerations());
		reList.addAll(model.getPackages());
		reList.addAll(model.getReferenceObjects());
		for (AbstractModelElement ab : reList) {
			boolean flag1 = false;
			boolean flag2 = false;
			boolean flag3 = false;
			if ((!searchName.equals("") && isStringExist(searchName,
					ab.getName()))) {
				flag1 = true;
			} else if (searchName.equals("")) {
				flag1 = true;
			}
			if ((!searchDisName.equals("") && isStringExist(searchDisName,
					ab.getDisplayName()))) {
				flag2 = true;
			} else if (searchDisName.equals("")) {
				flag2 = true;
			}
			if ((!searchType.equals("") && searchType.equals(ab
					.getDisplayType()))) {
				flag3 = true;
			} else if (searchType.equals("")) {
				flag3 = true;
			} else if (searchType.equals("属性")
					&& "持久化属性".equals(ab.getDisplayType())) {
				flag3 = true;
			} else if (searchType.equals("属性")
					&& "主键属性".equals(ab.getDisplayType())) {
				flag3 = true;
			} else if (searchType.equals("持久化属性")
					&& "主键属性".equals(ab.getDisplayType())) {
				flag3 = true;
			}
			if (flag1 && flag2 && flag3) {
				resultList.add(ab);
			}
		}
		return resultList;
	}

	/**
	 * Checks if is string exist.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @param name
	 *            the name
	 * @return true, if is string exist
	 * @Date 2018-09-03 09:00
	 */
	public boolean isStringExist(String key, String name) {
		if (caseSensitive) {
			int first = name.indexOf(key);
			if (first != -1) {
				return true;
			} else {
				return false;
			}
		} else {
			int first = name.toLowerCase(Locale.getDefault()).indexOf(key.toLowerCase(Locale.getDefault()));
			if (first != -1) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Gets the ok button.
	 *
	 * @author mqfdy
	 * @return the ok button
	 * @Date 2018-09-03 09:00
	 */
	public Button getOkButton() {
		return okButton;
	}

}
