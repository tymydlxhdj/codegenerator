package com.mqfdy.code.springboot.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.mqfdy.code.springboot.core.NewMicroProjectOperation;
import com.mqfdy.code.springboot.core.generator.utils.StringUtils;
import com.mqfdy.code.springboot.core.util.JobUtil;
import com.mqfdy.code.springboot.core.util.Operation2Runnable;


public class NewMicroProjectWizard extends Wizard implements INewWizard {

	private final NewMicroProjectOperation operation = new NewMicroProjectOperation();
	private BaseInfoWizardPage pageOne;
	private DBWizardPage pageTwo;
	private BasePackageWizardPage pageBasePackage;

	public NewMicroProjectWizard() {
		setWindowTitle("新建Springboot项目");
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	
	}

	@Override
	public void addPages() {
		super.addPages();
		//向导第一个页面:显示项目名称/保存位置/项目类型
		pageOne = new BaseInfoWizardPage(operation);
		
		pageBasePackage = new BasePackageWizardPage(operation);
		//向导第二个页面:新建数据源
		pageTwo = new DBWizardPage(operation);
		addPage(pageOne);
		addPage(pageBasePackage);
		addPage(pageTwo);
	}
	
	
	@Override
	public boolean canFinish() {
		return super.canFinish() && !StringUtils.isEmpty(operation.getBasePacakageName());
	}

	/**
	 * 向导页面点击完成时调用
	 */
	@Override
	public boolean performFinish() {
		JobUtil.userJob(new Operation2Runnable("Create Springboot project(s)") {
			@Override
			public void doit(IProgressMonitor monitor) throws OperationCanceledException, CoreException {
				operation.perform(monitor);
			}
		});
		return true;
	}
}
