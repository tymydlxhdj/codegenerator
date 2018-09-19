package com.mqfdy.code.reverse.views.pages;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.ReverseException;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.utils.ProjectUtil;
// TODO: Auto-generated Javadoc

/**
 * 数据库反向建模向导.
 *
 * @author mqfdy
 */
public class ReverseWizard extends Wizard implements INewWizard {
	
	/** The connection. */
	private Connection connection;
	
	/** The project. */
	private IProject project;
	
	/** The is all ignored. */
	private boolean isAllIgnored;
	
	/**
	 * 获取数据库连接对象 如果之前缓存过连接，并且该连接未关闭，就直接返回 否则用缓存的数据源信息重新申请连接对象。.
	 *
	 * @author mqfdy
	 * @return the connection
	 * @Date 2018-09-03 09:00
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			if(connection != null && !connection.isClosed()) {
				return connection;
			}
			
			conn = ReverseUtil.createConnection(ReverseContext.info);
			setConnection(conn);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Sets the connection.
	 *
	 * @author mqfdy
	 * @param connection
	 *            the new connection
	 * @Date 2018-09-03 09:00
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/** The rw page. */
	private DatasourceWizardPage rwPage;
	
	/** The ts page. */
	private TableStructureWizardPage tsPage;
	
	/** The st page. */
	private SpecialTableWizardPage stPage;
	
	/** The os page. */
	private OmSelectWizardPage osPage;
	
	/** The dn page. */
	private DuplicateNameWizardPage dnPage;
	
	/** The onrs page. */
	private OmNameReverseStrategyPage onrsPage;
	
	/** The pd page. */
	private PackageDispachPage pdPage;
	
	/** The om reverse. */
	private IOmReverse omReverse = new OmReverse();
	
	/** The Constant TOTAL_WORK. */
	public static final int TOTAL_WORK = 5;
	
	/** The is finish. */
	private boolean isFinish = false;
	
	/**
	 * Checks if is finish.
	 *
	 * @author mqfdy
	 * @return true, if is finish
	 * @Date 2018-09-03 09:00
	 */
	public boolean isFinish() {
		return isFinish;
	}

	/**
	 * Sets the finish.
	 *
	 * @author mqfdy
	 * @param isFinish
	 *            the new finish
	 * @Date 2018-09-03 09:00
	 */
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
	
	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @param arg0
	 *            the arg 0
	 * @param selection
	 *            the selection
	 * @Date 2018-09-03 09:00
	 */
	public void init(IWorkbench arg0, IStructuredSelection selection) {
		setWindowTitle("数据库反向生成bom - 向导");
		if (selection != null && !selection.isEmpty()) {
			Object object = selection.getFirstElement();
			if (object instanceof IProject) {
				project = (IProject) object;
			} else if (object instanceof IJavaProject) {
				project = ((IJavaProject) object).getProject();
			} else if (object instanceof IFolder) {
				project = ((IFolder) object).getProject();
			} else if (object instanceof IFile) {
				project = ((IFile) object).getProject();
			}
			if(!ProjectUtil.isBOMProject(project)){
				project = null;
			}
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean performFinish() {
		final IWorkbenchPage[] pages = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();
		try {
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(pdPage.getShell());
			progressMonitorDialog.run(true, false, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("正在转换到bom中......", TOTAL_WORK);
					if(!isAllIgnored){
						doFinish(pages, monitor);
					}
					monitor.done();
				}
			});
			
			MessageDialog.openInformation(getShell(), "成功", "转换成功! 共转换表" + (ReverseContext.lastTables.size()-pdPage.getUnselectedToOmTablesCount()) + "个");
			
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			
			IPath path = new Path(osPage.getOmPath());
			path = path.makeRelativeTo(root.getLocation());
				
			IResource resource = root.findMember(path);
			IContainer container = (IContainer) resource;
			IFile file = container.getFile(new Path(osPage.getOmName()));
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IDE.openEditor(page, file, true);
		} catch (Exception e) {
			MessageDialog.openError(pdPage.getShell(), "Error", e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		refresh(pages);
		return true;
	}

	/**
	 * Refresh.
	 *
	 * @author mqfdy
	 * @param pages
	 *            the pages
	 * @Date 2018-09-03 09:00
	 */
	public void refresh(final IWorkbenchPage[] pages) {
		if(ProjectUtil.isBOMProject(project)){
			try {
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean canFinish() {
		isAllIgnored = dnPage.valiFinish();
		if(isAllIgnored){
			return true;
		}
		if(isFinish) {
			return super.canFinish();
		}
		return false;
	}
	
	/**
	 * Do finish.
	 *
	 * @author mqfdy
	 * @param pages
	 *            the pages
	 * @param monitor
	 *            the monitor
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("deprecation")
	private void doFinish(IWorkbenchPage[] pages, IProgressMonitor monitor) {
		omReverse.createBom(ReverseContext.bom, monitor, pdPage);
		
		String filepath = osPage.getOmPath();
		IPath path = new Path(filepath);
		IResource res = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path)[0];
		try {
			res.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
//			EditorOperation.refreshEditorByFile(ReverseContext.OM_STORAGE_PATH, pages);
	}
	
	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject(){
		return project;
	}

	/**
	 * 
	 */
	@Override
	public void addPages() {
		try {
			rwPage = new DatasourceWizardPage(this, "rwPage");
		} catch (ReverseException e) {
			return ;
		}
		addPage(rwPage);
		
		tsPage = new TableStructureWizardPage("tsPage");
		addPage(tsPage);
		
		stPage = new SpecialTableWizardPage("stPage");
		addPage(stPage);
		
		osPage = new OmSelectWizardPage("osPage",project);
		addPage(osPage);
		
		dnPage = new DuplicateNameWizardPage("dnPage");
		addPage(dnPage);
		
		onrsPage = new OmNameReverseStrategyPage("onrsPage");
		addPage(onrsPage);
		
		pdPage = new PackageDispachPage("pdPage");
		addPage(pdPage);
		
	}
	
	/**
	 * 
	 */
	@Override
	public void dispose() {
		omReverse.clearMemery();
	}

	/**
	 * Sets the project.
	 *
	 * @author mqfdy
	 * @param project
	 *            the new project
	 * @Date 2018-09-03 09:00
	 */
	public void setProject(IProject project) {
		this.project = project;
		osPage.setProject(project);
	}

	/**
	 * Checks if is all ignored.
	 *
	 * @author mqfdy
	 * @return true, if is all ignored
	 * @Date 2018-09-03 09:00
	 */
	public boolean isAllIgnored() {
		return isAllIgnored;
	}

	/**
	 * Sets the all ignored.
	 *
	 * @author mqfdy
	 * @param isAllIgnored
	 *            the new all ignored
	 * @Date 2018-09-03 09:00
	 */
	public void setAllIgnored(boolean isAllIgnored) {
		this.isAllIgnored = isAllIgnored;
	}
	
	
	
}
