package com.mqfdy.code.designer.dialogs.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.ui.viewsupport.FilteredElementTreeSelectionDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceContentProvider;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceLabelProvider;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.AbstractPackage;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 模型元素选择器
 * 
 * @author mqfdy
 * 
 */
@SuppressWarnings("restriction")
public class ModelElementSelecterDailog extends
					FilteredElementTreeSelectionDialog {

	/**
	 * 可以选择的模型元素类型,为空时可选择所有类型
	 */
	private String[] modelTypes;
	/**
	 * 是否允许选择反向业务实体
	 */
	private boolean includedReverse;
	/**
	 * 内置模型
	 */
	private BusinessObjectModel inBom;

	private ViewerFilter viewerFilter = new ViewerFilter() {
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if ((element instanceof BusinessObjectModel)
					|| (element instanceof AbstractPackage)) {
				return true;
			}
			if (modelTypes != null && modelTypes.length > 0) {
				AbstractModelElement ame = (AbstractModelElement) element;
				String type = ame.getType();
				return StringUtil.isContain(modelTypes, type);
			}
			return true;
		}
	};

	public ModelElementSelecterDailog(BusinessModelManager businessModelManager) {
		this(businessModelManager, false);
	}

	public ModelElementSelecterDailog(
			BusinessModelManager businessModelManager, boolean includedBuildIn) {
		this(businessModelManager, false, true);
	}

	public ModelElementSelecterDailog(String[] modelTypes,
			BusinessModelManager businessModelManager) {
		this(modelTypes, businessModelManager, false, true);
	}
	/**
	 * 构造函数
	 * 
	 * @param businessModelManager
	 *            业务模型管理器
	 * @param includedBuildIn
	 *            是否包含内置对象
	 */
	public ModelElementSelecterDailog(
			BusinessModelManager businessModelManager, boolean includedBuildIn, boolean includedReverse) {
		super(null,new ModelResourceLabelProvider(),
				new ModelResourceContentProvider());
		this.includedReverse = includedReverse;
		// this.includedBuildIn = includedBuildIn;
		addFilter(viewerFilter);
		List<BusinessObjectModel> boms = new ArrayList<BusinessObjectModel>();
		BusinessObjectModel bom = businessModelManager.getBusinessObjectModel();
		boms.add(bom);

		if (includedBuildIn) {
			if (inBom == null) {
				inBom = BusinessModelManager.getBuildInOm();
			}
			//boms.add(inBom);
		}

		setInput(boms);
		setMessage("选择业务模型对象：");
	}
	/**
	 * 构造函数
	 * 
	 * @param modelTypes
	 *            可以选择的模型元素类型
	 * @param businessModelManager
	 *            业务模型管理器
	 * @param includedBuildIn
	 *            是否包含内置对象
	 */
	public ModelElementSelecterDailog(String[] modelTypes,
			BusinessModelManager businessModelManager, boolean includedBuildIn, boolean includedReverse) {
		super(null,new ModelResourceLabelProvider(),
				new ModelResourceContentProvider());
		this.modelTypes = modelTypes;
		this.includedReverse = includedReverse;
		// this.includedBuildIn = includedBuildIn;
		addFilter(viewerFilter);
		List<BusinessObjectModel> boms = new ArrayList<BusinessObjectModel>();
		BusinessObjectModel bom = businessModelManager.getBusinessObjectModel();
		boms.add(bom);

		if (includedBuildIn) {
			if (inBom == null) {
				inBom = BusinessModelManager.getBuildInOm();
			}
			//boms.add(inBom);
		}

		setInput(boms);
		setMessage("选择业务模型对象：");
	}

	protected TreeViewer createTreeViewer(Composite parent) {
		TreeViewer treeViewer = super.createTreeViewer(parent);
		makeAction();
		return treeViewer;
	}

	private void makeAction() {
		getTreeViewer().expandToLevel(4);
		getTreeViewer().addFilter(new ViewerFilter() {
			// 过滤反向业务实体
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				// TODO Auto-generated method stub
				if(element instanceof BusinessClass && !includedReverse &&
						IModelElement.STEREOTYPE_REVERSE.equals(((BusinessClass) element).getStereotype()))
					return false;
//				// 过滤掉属性和操作
//				if(element instanceof SolidifyPackage && (((SolidifyPackage)element).getDisplayName().equals("属性") || 
//						((SolidifyPackage)element).getDisplayName().equals("操作")))
//					return false;
				return true;
			}
		});
		getTreeViewer().addSelectionChangedListener(
				new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						AbstractModelElement element = (AbstractModelElement) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						if (element != null) {
							String type = element.getType();
							getButton(OK).setEnabled(isTypeOk(element,type));
						}
					}
				});
		getTreeViewer().getTree().setFocus();
	}

	private boolean isTypeOk(Object element, String type) {
		setMessage(null);
		
		if (modelTypes != null && modelTypes.length > 0) {
			for (int i = 0; i < modelTypes.length; i++) {
				if (modelTypes[i].equals(type)) {
					if(type.equals(IModelElement.MODEL_TYPE_BUSINESSCLASS)){
						if(IModelElement.STEREOTYPE_REVERSE.equals(((BusinessClass)element).getStereotype())
							&& !includedReverse	){
							setMessage("该对象类型不允许选择！");
							return false;
						}
					}
					return true;
				}
			}
			setMessage("该对象类型不允许选择！");
			return false;
		}
		return true;
	}

	protected void okPressed() {
		if (getResult() != null && getResult().length > 0) {
			AbstractModelElement element = ((AbstractModelElement) getResult()[0]);
			if (element != null) {
				String type = element.getType();
				if (isTypeOk(element, type)) {
					super.okPressed();
				} else {
					getButton(OK).setEnabled(false);
				}
			}
		}
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("业务对象选择");
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
	}

}
