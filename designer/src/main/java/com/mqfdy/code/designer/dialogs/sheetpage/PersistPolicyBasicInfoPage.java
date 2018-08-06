package com.mqfdy.code.designer.dialogs.sheetpage;

import java.util.List;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.mqfdy.code.designer.dialogs.BusinessClassRelationEditDialog;
import com.mqfdy.code.designer.dialogs.IBusinessClassEditorPage;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.provider.AssociationConentProvider;
import com.mqfdy.code.designer.provider.AssociationLabelProvider;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Inheritance;

public class PersistPolicyBasicInfoPage extends Composite implements
		IBusinessClassEditorPage {
	private String GROUP_ENTITY_RELATION = "关联关系";
	// private String GROUP_ENTITY_INHERICATE = "继承关系";

	private Table associationTable = null;
	private TableViewer associationTableViewer = null;

	// private Table inherTable = null;
	// private TableViewer inherTableViewer = null;

	private PersistPolicySheetPage parentPage;

	BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	public PersistPolicyBasicInfoPage(Composite parent, int style,
			PersistPolicySheetPage parentPage) {
		super(parent, style);
		this.parentPage = parentPage;
		createContent(this);
	}

	private void createContent(Composite parent) {
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		ctreateGroup(parent);
		// ctreateGroup4(parent);
		addListeners();
	}

	private void addListeners() {
		associationTableViewer
				.addDoubleClickListener(new IDoubleClickListener() {

					public void doubleClick(DoubleClickEvent event) {
						// 当切换关联关系时,切换右侧checkbox显示
						IStructuredSelection is = (IStructuredSelection) event
								.getSelection();
						Object obj = is.getFirstElement();
						if (obj instanceof Association) {
							BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
									associationTableViewer.getControl()
											.getShell(), (Association) obj,
									null,
									BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
							if (dialog.open() == Window.OK) {
								associationTableViewer.refresh();
							}
						}
					}

				});
		/*
		 * inherTableViewer.addDoubleClickListener(new IDoubleClickListener(){
		 * 
		 * public void doubleClick(DoubleClickEvent event) {
		 * //当切换关联关系时,切换右侧checkbox显示 IStructuredSelection is =
		 * (IStructuredSelection) event .getSelection(); Object obj =
		 * is.getFirstElement(); if(obj instanceof Inheritance){
		 * BusinessClassInheritanceRelationEditDialog dialog = new
		 * BusinessClassInheritanceRelationEditDialog
		 * (inherTableViewer.getControl
		 * ().getShell(),(Inheritance)obj,null,BusinessModelEvent
		 * .MODEL_ELEMENT_UPDATE); dialog.open(); } }
		 * 
		 * });
		 */

	}

	private void ctreateGroup(Composite parent) {
		Group groupAssociation = new Group(parent, SWT.NONE);
		groupAssociation.setLayout(new GridLayout(1, true));
		groupAssociation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		groupAssociation.setText(GROUP_ENTITY_RELATION);
		createAssociationTable(groupAssociation);

	}

	/*
	 * private void ctreateGroup4(Composite parent){ Group groupInher = new
	 * Group(parent,SWT.NONE); groupInher.setLayout(new GridLayout(1,true));
	 * groupInher.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));
	 * groupInher.setText(GROUP_ENTITY_INHERICATE);
	 * createInherTable(groupInher); }
	 */

	private void createAssociationTable(Composite parent) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		// gridData.heightHint = 158;
		gridData.horizontalSpan = 1;
		gridData.verticalSpan = 1;

		associationTableViewer = new TableViewer(parent, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.SCROLL_LINE);
		associationTable = associationTableViewer.getTable();
		associationTable.setHeaderVisible(true);
		associationTable.setLinesVisible(true);
		associationTable.setLayoutData(gridData);

		associationTableViewer.setLabelProvider(new AssociationLabelProvider());
		associationTableViewer
				.setContentProvider(new AssociationConentProvider());

		String[] columnNames = new String[] { "名称", "实体A", "实体B", "关系类型",
				"导航关系" };

		int[] columnWidths = new int[] { 120, 120, 120, 120, 120 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT,
				SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(associationTable,
					columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

	}

	/*
	 * private void createInherTable(Composite parent){ GridData gridData = new
	 * GridData(); gridData.horizontalAlignment = SWT.FILL;
	 * gridData.verticalAlignment = SWT.NONE; gridData.grabExcessHorizontalSpace
	 * = true; gridData.grabExcessVerticalSpace = true; gridData.heightHint =
	 * 120; gridData.horizontalSpan=1; gridData.verticalSpan=1;
	 * 
	 * inherTableViewer = new
	 * TableViewer(parent,SWT.SINGLE|SWT.FULL_SELECTION|SWT
	 * .BORDER|SWT.SCROLL_LINE); inherTable = inherTableViewer.getTable();
	 * inherTable.setHeaderVisible(true); inherTable.setLinesVisible(true);
	 * inherTable.setLayoutData(gridData);
	 * 
	 * inherTableViewer.setLabelProvider(new InherLabelProvider());
	 * inherTableViewer.setContentProvider(new InherConentProvider());
	 * 
	 * String[] columnNames = new String[]{"名称","父实体","子实体"};
	 * 
	 * int[] columnWidths = new int[]{120,120,120};
	 * 
	 * int[] columnAligns = new int[]{SWT.LEFT,SWT.LEFT,SWT.LEFT};
	 * 
	 * for(int i = 0; i < columnNames.length; i++){ TableColumn tableColumn =
	 * new TableColumn(inherTable, columnAligns[i]);
	 * tableColumn.setText(columnNames[i]);
	 * tableColumn.setWidth(columnWidths[i]); }
	 * 
	 * }
	 */
	public void initControlValue() {
		List<Association> assList = getAssociationsByClass();
		associationTableViewer.setInput(assList);
		associationTableViewer.refresh();

		/*
		 * List<Inheritance> inherList = getInheritanceByClass();
		 * inherTableViewer.setInput(inherList); inherTableViewer.refresh();
		 */

	}

	private List<Association> getAssociationsByClass() {
		BusinessClass businessClass = parentPage.getBusinessClassEditorDialog()
				.getBusinessClassCopy();
		return manager.getAssociationsByBusinessClass(businessClass);
	}

	public boolean validateInput() {
		return true;
	}

	public void updateTheEditingElement() {
		BusinessClass businessClass = parentPage.getBusinessClassEditorDialog()
				.getBusinessClassCopy();
		if (businessClass != null) {
			BusinessModelManager manager = BusinessModelUtil
					.getEditorBusinessModelManager();
			manager.businessObjectModelChanged(new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_UPDATE, businessClass));
		}
	}

}
