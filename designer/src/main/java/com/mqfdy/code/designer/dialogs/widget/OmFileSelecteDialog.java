package com.mqfdy.code.designer.dialogs.widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.resource.BomManager;
import com.mqfdy.code.utils.ProjectUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class OmFileSelecteDialog.
 *
 * @author mqfdy
 */
@SuppressWarnings("restriction")
public class OmFileSelecteDialog extends ElementTreeSelectionDialog {

	/** The om file. */
	private IFile omFile = null;
	
	/** The cur select om files. */
	private List<IFile> curSelectOmFiles = new ArrayList();
	
	/** The bom extend. */
	private static String bomExtend = ".bom";

	/** The viewer filter. */
	private ViewerFilter viewerFilter = new ViewerFilter() {
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element instanceof JarPackageFragmentRoot) {
				return false;
			}
			if (element instanceof PackageFragmentRoot) {
				return false;
			} else if (element instanceof PackageFragment) {
				return false;
			}
			else if (element instanceof JavaProject) {
				JavaProject jproject = (JavaProject) element;
				if(!ProjectUtil.isBOMProject(jproject.getProject())){
					return false;
				}
			} else if (element instanceof IFolder) {
				String name = ((IFolder) element).getName();
				if (!"model".equals(name) && !"bom".equals(name)) {
					return false;
				}
			} else if (element instanceof IFile) {
				if (((IFile) element).getName().endsWith(bomExtend)) {
					if (isExist(((IFile) element)) == true) {
						return false;
					}
					omFile = ((IFile) element);
					return !isExist(omFile);
					//return true;
				} else {
					return false;
				}

			}
			return true;
		}
	};

	/**
	 * Instantiates a new om file selecte dialog.
	 */
	public OmFileSelecteDialog() {
		super(null,new JavaElementLabelProvider(),
				new StandardJavaElementContentProvider());
		addFilter(viewerFilter);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		this.setInput(JavaCore.create(root));
		setMessage("请选择一个BOM文件:");
	}

	/**
	 * Creates the tree viewer.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the tree viewer
	 * @Date 2018-09-03 09:00
	 */
	protected TreeViewer createTreeViewer(Composite parent) {
		TreeViewer treeViewer = super.createTreeViewer(parent);
		makeAction();
		setTitleAndMessage();
		return treeViewer;
	}

	/**
	 * Make action.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeAction() {
		getTreeViewer().addSelectionChangedListener(
				new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						Object element = ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						getButton(OK).setEnabled(isTypeOk(element));
						
						curSelectOmFiles.clear();
						TreeItem[] items = getTreeViewer().getTree().getSelection();
						if (null == items || items.length == 0) {
							return;
						}
						for(TreeItem item : items){
							Object obj = item.getData();
							if(obj instanceof IFile && ((IFile) obj).getName().endsWith(bomExtend)){
								curSelectOmFiles.add((IFile)obj);
							}
						}
					}
				});
	}

	/**
	 * 
	 */
	protected void okPressed() {
		if (getResult() != null && getResult().length > 0) {
			Object element = getResult()[0];
			if (isTypeOk(element)) {
				super.okPressed();
			} else {
				getButton(OK).setEnabled(false);
				TreeItem[] items = getTreeViewer().getTree().getSelection();
				if (null == items || items.length == 0) {
					return;
				}
				if (items[0].getExpanded()) {
					items[0].setExpanded(false);
				} else {
					getTreeViewer().expandToLevel(element, 1); // 从选中的节点处展开
				}
			}
		} else {
			getButton(OK).setEnabled(false);
		}
	}

	/**
	 * Checks if is type ok.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return true, if is type ok
	 * @Date 2018-09-03 09:00
	 */
	private boolean isTypeOk(Object element) {
		if (element == null)
			return false;
		if (element instanceof IFile) {
			if (((IFile) element).getName().endsWith(bomExtend)) {
				if (isExist(((IFile) element)) == true) {
					return false;
				}
				omFile = ((IFile) element);
				return true;
			}
		}
		return false;
	}

	/**
	 * 文件是否已经加载过.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @return true, if is exist
	 * @Date 2018-09-03 09:00
	 */
	private boolean isExist(IFile file) {
		boolean result = false;
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();
		List<BusinessObjectModel> rboms = manager.getRepositoryModels();
		for (BusinessObjectModel bom : rboms) {
			try {
				String refModelId = bom.getId();
				String modelId = BomManager.getModelId(file.getContents());
				if (refModelId.equals(modelId)) {
					return true;
				}
			} catch (IOException e) {
				Logger.log(e);
			} catch (DocumentException e) {
				Logger.log(e);
			} catch (CoreException e) {
				Logger.log(e);
			}
		}
		try {
			BusinessObjectModel bom = manager.getBusinessObjectModel();
			String refModelId = bom.getId();
			String modelId;
			modelId = BomManager.getModelId(file.getContents());
			if (refModelId.equals(modelId)) {
				return true;
			}
		} catch (IOException e) {
			Logger.log(e);
		} catch (DocumentException e) {
			Logger.log(e);
		} catch (CoreException e) {
			Logger.log(e);
		}

		return result;
	}

	/**
	 * Gets the om file.
	 *
	 * @author mqfdy
	 * @return the om file
	 * @Date 2018-09-03 09:00
	 */
	public IFile getOmFile() {
		return omFile;
	}

	/**
	 * Gets the om files.
	 *
	 * @author mqfdy
	 * @return the om files
	 * @Date 2018-09-03 09:00
	 */
	public List<IFile> getOmFiles() {
		return this.curSelectOmFiles;
	}
	
	/**
	 * 设置标题和信息.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void setTitleAndMessage() {
		setTitle("dddd");
	}

	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("引用OM文件选择器");
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_OBJECTMODEL));
	}
}
