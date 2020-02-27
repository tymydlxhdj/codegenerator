package com.mqfdy.code.wizard.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.console.ConsoleFactory;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.generator.CodeGeneratorFactory;
import com.mqfdy.code.generator.GeneratorContext;
import com.mqfdy.code.generator.ICodeGenerator;
import com.mqfdy.code.generator.MddConfiguration;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.resource.BomManager;
import com.mqfdy.code.resource.ValidatorManager;
import com.mqfdy.code.resource.validator.ValiResult;
import com.mqfdy.code.resource.validator.ValidatorContext;
import com.mqfdy.code.wizard.wizardpages.MicroGeneratorWizardPage;


// TODO: Auto-generated Javadoc
/**
 * The Class MicroGeneratorConfigWizard.
 *
 * @author mqfdy
 */
@SuppressWarnings("restriction")
public class MicroGeneratorConfigWizard extends Wizard implements IMicroGeneratorConfigWizard, GeneratorContext,ValidatorContext {

	/** The micro generator wizard page. */
	private MicroGeneratorWizardPage microGeneratorWizardPage;
	
	/** The project. */
	private IProject project;
	
	/** The om path. */
	private String omPath;
	
	/** The business object model. */
	private BusinessObjectModel businessObjectModel;
	
	/** The is finished. */
	private boolean isFinished;
	
	/**
	 *  addPages
	 */
	public void addPages(){
		microGeneratorWizardPage = new MicroGeneratorWizardPage("microGeneratorWizardPage", project,businessObjectModel);
		addPage(microGeneratorWizardPage);
	}

	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @param workbench
	 *            the workbench
	 * @param selection
	 *            the selection
	 * @Date 2018-09-03 09:00
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("生成代码");
	}


	/**
	 * Initialize.
	 *
	 * @author mqfdy
	 * @param omPath
	 *            the om path
	 * @param project
	 *            the project
	 * @Date 2018-09-03 09:00
	 */
	public void initialize(String omPath,IProject project) {
		setOmPath(omPath);
		try {
			this.project = project;
			businessObjectModel = BomManager.xml2Model(omPath);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * performFinish
	 * @return the object
	 */
	public boolean performFinish() {
		try {
			if(!isFinished){
				doFinish();
				isFinished = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isFinished = false;
		}
		return true;
	}
	
	/**
	 * Do finish.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void doFinish(){
		microGeneratorWizardPage.validate();
		boolean flag = false;
		if (microGeneratorWizardPage.getParametersPage().getV()) {
			List<ValiResult> resultList = (new ValidatorManager())
					.checkModel(businessObjectModel, microGeneratorWizardPage
							.getObjectSelectPage().getSelectedModels(),
							this);
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
			BusinessModelUtil.getView(ValiView.class).setFilePath(omPath);
			BusinessModelUtil.getView(ValiView.class).setValiData(resultList);
			flag = resultList == null || resultList.size() == 0;
		}
		if (!flag) {
			MessageDialog d = new MessageDialog(getShell(), "提示", null,
					"模型验证有误，是否继续生成代码？", 0, new String[] { "确定", "取消" }, 0);
			
			if (d.open() == TitleAreaDialog.OK) {
				doGenerator();
			}
		} else {
			doGenerator();
		}
	}
	
	/**
	 * 生成代码.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void doGenerator() {
		String exportPackage = getExportPackage();
		ICodeGenerator engine = CodeGeneratorFactory
				.createCodeGenerator(project,microGeneratorWizardPage.getOption(),
						microGeneratorWizardPage.getPackageName(),microGeneratorWizardPage.getTemplateType());
		//fl/ag = validateModel(flag) && flag;
		genCode(true, exportPackage, engine);
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			Logger.log(e);
		}
	}
	
	/**
	 * 生成代码.
	 *
	 * @author mqfdy
	 * @param flag
	 *            the flag
	 * @param exportPackage
	 *            the export package
	 * @param engine
	 *            the engine
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void genCode(boolean flag, String exportPackage, ICodeGenerator engine) {
		Map map = microGeneratorWizardPage.getParsMap();
		MddConfiguration config = new MddConfiguration();

		config.setMap(map);
		config.setExportPath(microGeneratorWizardPage.getParametersPage().getCurPath());
		config.setContext(this);
		engine.generateCode(microGeneratorWizardPage.getObjectSelectPage()
				.getBusinessObjectModel(), config);
		// 输出信息
		ConsoleFactory.printToConsoleGenerate("代码生成完毕！", true, false);// 黑色
		if((Boolean) map.get("isSyncDbs")) {
			List<Map<String, String>> excuteDDLSentences = microGeneratorWizardPage.executeDDL();
			ConsoleFactory.printToConsoleGenerate("执行DDL文件:  ", true, false);
			if(excuteDDLSentences == null || excuteDDLSentences.size() == 0) {
				ConsoleFactory.printToConsoleGenerate("没有可执行的DDl脚本", true, false);
			} else {
				for(Map<String, String> excuteDDLSentence: excuteDDLSentences) {
					if(excuteDDLSentence.get("isSuccess").equals("true")) {
						ConsoleFactory.printToConsoleGenerate("\t"+ excuteDDLSentence.get("ddlSentence"), true, false);
					} else {
						ConsoleFactory.printToConsoleGenerate("\t"+ excuteDDLSentence.get("ddlSentence"), true, true);
					}
				}
			}
			ConsoleFactory.printToConsoleGenerate("执行DDL文件完毕!", true, false);
		}
	}
	
	/**
	 * Gets the export package.
	 *
	 * @author mqfdy
	 * @return the export package
	 * @Date 2018-09-03 09:00
	 */
	/*public boolean validateModel(boolean flag) {
		if (microGeneratorWizardPage.getParametersPage().getV()) {
			List<ValiResult> resultList = (new ValidatorManager())
					.checkModel(businessObjectModel, microGeneratorWizardPage
							.getObjectSelectPage().getSelectedModels(),
							this);
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
			BusinessModelUtil.getView(ValiView.class).setFilePath(omPath);
			BusinessModelUtil.getView(ValiView.class).setValiData(resultList);
			flag = resultList == null || resultList.size() == 0;
		}
		return flag;
	}*/
	@SuppressWarnings("unused")
	public String getExportPackage() {
		BusinessObjectModel bom=microGeneratorWizardPage.getBusinessObjectModel();
		String exportPackage="";
		 for(ModelPackage packages: bom.getPackages()){
			 packages.getChildren().get(2).getChildren();
			 for(AbstractModelElement ele: packages.getChildren()){
				 if(ele instanceof SolidifyPackage&&"businessClass".equals(((SolidifyPackage)ele).getName())&&
						 ((SolidifyPackage)ele).getChildren().size()>0){						 
					    List<AbstractModelElement> list=  ((SolidifyPackage)ele).getChildren();
					    	if(!exportPackage.contains(packages.getFullName()+ ".services")){
								 exportPackage=exportPackage.concat(packages.getFullName()+ ".services").concat(","); 
							 }
							 if(!exportPackage.contains(packages.getFullName()+ ".domain")){
								 exportPackage=exportPackage.concat(packages.getFullName()+ ".domain").concat(","); 
							 }
					break;
				 }
			 }
								
		 }
		 if(!"".equals(exportPackage)&&exportPackage.endsWith(",")){
			 exportPackage= exportPackage.substring(0,exportPackage.length()-1);
		 }
		return exportPackage;
	}
	
	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject() {
		return project;
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
	}
	
	/**
	 * Gets the om path.
	 *
	 * @author mqfdy
	 * @return the om path
	 * @Date 2018-09-03 09:00
	 */
	public String getOmPath() {
		return omPath;
	}
	
	/**
	 * Sets the om path.
	 *
	 * @author mqfdy
	 * @param omPath
	 *            the new om path
	 * @Date 2018-09-03 09:00
	 */
	public void setOmPath(String omPath) {
		this.omPath = omPath;
	}

	/**
	 * Prints the to console.
	 *
	 * @author mqfdy
	 * @param msg
	 *            the msg
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	public void printToConsole(String msg, String type) {
		if (type.equals(GeneratorContext.INFO))
			ConsoleFactory.printToConsole(msg, true, false);// 黑色
		if (type.equals(GeneratorContext.ERROR))
			ConsoleFactory.printToConsole(msg, true, true);// 黑色
	}
	
	/**
	 * Prints the.
	 *
	 * @author mqfdy
	 * @param mesg
	 *            the mesg
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	public void print(String mesg, String type) {
		if (type.equals(GeneratorContext.INFO))
			ConsoleFactory.printToConsoleGenerate(mesg, true, false);// 黑色
		if (type.equals(GeneratorContext.ERROR))
			ConsoleFactory.printToConsoleGenerate(mesg, true, true);// 黑色
	}


	/**
	 * Confirm.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public int confirm(String message) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		MessageDialog dialog = new MessageDialog(shell, "确认文件替换", null,
				message, MessageDialog.QUESTION, new String[] { "覆盖", "不覆盖",
						"全部覆盖" }, 0);

		return dialog.open();
	}
	
	/**
	 * 获取SRC路径列表.
	 *
	 * @author mqfdy
	 * @param project
	 *            the project
	 * @return the src path list
	 * @Date 2018-09-03 09:00
	 */
	public static List<String> getSrcPathList(IProject project){
		List<String> pathList = new ArrayList<String>();
	    IJavaProject javaProject = JavaCore.create(project);
	    String projectName = project.getName();
	    try {
            IPackageFragmentRoot[] packageFragmentRoot = javaProject.getPackageFragmentRoots();
            for (int i = 0; i < packageFragmentRoot.length; i++){
                if (packageFragmentRoot[i] instanceof PackageFragmentRoot && packageFragmentRoot[i].getKind() == IPackageFragmentRoot.K_SOURCE){
                	String srcPath = packageFragmentRoot[i].getPath().toOSString();
                	srcPath = srcPath.substring(projectName.length()+2);
                	pathList.add(srcPath);
                }
            }
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
		return pathList;
	}
}
