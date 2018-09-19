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
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ModelPackage;

// TODO: Auto-generated Javadoc
/**
 * 代码生成对象选择页面.
 *
 * @author mqfdy
 */
public class GeneratorObjectSelectPage extends Composite {
	
	/** The business object model. */
	private BusinessObjectModel businessObjectModel;
	
	/** The bu page. */
	// private ModelSelectPage pkgPage;
	private ModelSelectPage buPage;
	
	/** The as page. */
	private ModelSelectPage asPage;
    
    /** The in page. */
    private ModelSelectPage inPage;
	
	/** The enum page. */
	private ModelSelectPage enumPage;
	
	/** The selected models. */
	// private ModelSelectPage dtoPage;
	private List<AbstractModelElement> selectedModels = new ArrayList<AbstractModelElement>();

	/**
	 * Instantiates a new generator object select page.
	 *
	 * @param businessObjectModel
	 *            the business object model
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public GeneratorObjectSelectPage(BusinessObjectModel businessObjectModel,
			Composite parent, int style) {
		super(parent, style);
		this.businessObjectModel = businessObjectModel;// BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel();
		createContents(this);
	}

	/**
	 * Creates the contents.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
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
		 TabItem tabItem4 = new TabItem(tabFolder, SWT.NONE);
		 tabItem4.setText("继承关系");
		// tab 内容
		buPage = new ModelSelectPage(businessObjectModel, tabFolder, SWT.NONE,
				"BusinessClass",this);
		enumPage = new ModelSelectPage(businessObjectModel, tabFolder,
				SWT.NONE, "Enumeration",this);
		// dtoPage = new ModelSelectPage(businessObjectModel,tabFolder,
		// SWT.NONE, this, "DTO");
		asPage = new ModelSelectPage(businessObjectModel, tabFolder, SWT.NONE,
				"Association",this);
		 inPage = new ModelSelectPage(businessObjectModel,tabFolder, SWT.NONE,
		 "Inheritance",this);
		tabFolder.getItem(0).setControl(buPage);
		tabFolder.getItem(1).setControl(enumPage);
		// tabFolder.getItem(2).setControl(dtoPage);
		tabFolder.getItem(2).setControl(asPage);
		 tabFolder.getItem(3).setControl(inPage);
	}

	/**
	 * Gets the selected models.
	 *
	 * @author mqfdy
	 * @return the selected models
	 * @Date 2018-09-03 09:00
	 */
	public List<AbstractModelElement> getSelectedModels() {
		// selectedModels.add(businessObjectModel);
		// selectedModels.addAll(businessObjectModel.getPackages());
		// selectedModels.addAll(businessObjectModel.getDiagrams());
		selectedModels.clear();
		selectedModels.addAll(buPage.getSelectedElements());
		selectedModels.addAll(enumPage.getSelectedElements());
		// selectedModels.addAll(dtoPage.getSelectedElements());
		selectedModels.addAll(asPage.getSelectedElements());
		selectedModels.addAll(inPage.getSelectedElements());
		return selectedModels;
	}

	/**
	 * Gets the business object model.
	 *
	 * @author mqfdy
	 * @return the business object model
	 * @Date 2018-09-03 09:00
	 */
	public BusinessObjectModel getBusinessObjectModel() {
		BusinessObjectModel newBom = new BusinessObjectModel(null, null);
		for (ModelPackage pkg : businessObjectModel.getPackages()) {
			newBom.addPackage(pkg);
		}
		newBom.setName(businessObjectModel.getName());
		newBom.setNameSpace(businessObjectModel.getNameSpace());
		if (businessObjectModel.getReferenceObjects() != null)
			newBom.getReferenceObjects().addAll(
					businessObjectModel.getReferenceObjects());
		for (AbstractModelElement ab : getSelectedModels()) {
			if (ab instanceof BusinessClass)
				newBom.addBusinessClass((BusinessClass) ab);
			else if (ab instanceof Association)
				newBom.addAssociation((Association) ab);
			else if (ab instanceof Enumeration)
				newBom.addEnumeration((Enumeration) ab);
			else if (ab instanceof Inheritance)
				newBom.addInheritance((Inheritance) ab);
			else if (ab instanceof DataTransferObject)
				newBom.addDTO((DataTransferObject) ab);
			// else if(ab instanceof ComplexDataType)
			// newBom.addDTO((ComplexDataType) ab);
		}
		return newBom;

	}

	/**
	 * Gets the bu page.
	 *
	 * @author mqfdy
	 * @return the bu page
	 * @Date 2018-09-03 09:00
	 */
	public ModelSelectPage getBuPage() {
		return buPage;
	}

	/**
	 * Gets the as page.
	 *
	 * @author mqfdy
	 * @return the as page
	 * @Date 2018-09-03 09:00
	 */
	public ModelSelectPage getAsPage() {
		return asPage;
	}
	
	/**
	 * Gets the enum page.
	 *
	 * @author mqfdy
	 * @return the enum page
	 * @Date 2018-09-03 09:00
	 */
	public ModelSelectPage getEnumPage() {
		return enumPage;
	}
	
}
