package com.mqfdy.code.springboot.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.springsource.ide.eclipse.gradle.core.actions.RefreshAllActionCore;
import org.springsource.ide.eclipse.gradle.core.classpathcontainer.GradleClassPathContainer;

import com.mqfdy.code.springboot.core.datasource.SampleProject;
import com.mqfdy.code.springboot.core.generator.AbstractGenerator;
import com.mqfdy.code.springboot.core.generator.CodeGenerationException;
import com.mqfdy.code.springboot.core.generator.GeneratorFactory;
import com.mqfdy.code.springboot.core.generator.TemplateConstantMessages;
import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.validators.MainProjectValidator;
import com.mqfdy.code.springboot.core.validators.NewProjectLocationValidator;
import com.mqfdy.code.springboot.core.validators.ProjectNameValidator;
import com.mqfdy.code.springboot.core.validators.SampleProjectValidator;
import com.mqfdy.code.springboot.core.validators.ValidationResult;
import com.mqfdy.code.springboot.core.validators.Validator;

/**
 * 新建Springboot项目向导操作类
 * 
 * @author mqfdy
 */
public class NewMicroProjectOperation {

	public static int progress = 1;

	public static int totalWork = 15;

	private LiveExpression<String> projectName = null;
	private ProjectNameValidator projectNameValidator = null;

	private LiveExpression<String> location = null;
	private NewProjectLocationValidator locationValidator = null;

	private LiveExpression<SampleProject> sampleProject = null;
	private Validator sampleProjectValidator = null;

	private LiveExpression<String> parentProjectLE = null;
	private MainProjectValidator parentProjectValidator = null;

	private String connectName;
	
	private String basePacakageName;
	
	private String projectType;


	public void setProjectNameField(LiveExpression<String> name) {
		Assert.isLegal(projectName == null && name != null);
		this.projectName = name;
		this.projectNameValidator = new ProjectNameValidator(projectName);
	}

	public void setLocationField(LiveExpression<String> location) {
		Assert.isLegal(this.location == null && location != null);
		this.location = location;
		this.locationValidator = new NewProjectLocationValidator("Location", location, projectName);
	}

	public LiveExpression<String> getProjectNameField() {
		return projectName;
	}

	public LiveExpression<String> getProjectLocation() {
		return location;
	}

	/**
	 * Verifies that all fields are wired up to some UI element (or another data
	 * source, e.g. mock data for unit testing).
	 */
	public void assertComplete() {
		// assert的作用是现计算表达式 expression ，如果其值为假（即为0），那么它先向stderr打印一条出错信息，然后通过调用
		// abort来终止程序运行。
		Assert.isLegal(projectName != null, "Please enter projectName.");
		Assert.isLegal(location != null, "Please choose a project location.");
		// Assert.isLegal(sampleProject!=null, "You must choose a project");
	}

	public boolean perform(IProgressMonitor mon) throws CoreException {
		assertComplete();
		MicroProject microProject = createProjectContents(mon);
		if (microProject != null && microProject.getProject() != null) {
			final IJavaProject javaProject = JavaCore.create(microProject.getProject());
			if(TemplateConstantMessages.GRADLE_PROJECT_TYPE.equalsIgnoreCase(projectType)){
				boolean isEnabled = GradleClassPathContainer.isOnClassPath(javaProject);
				if (!isEnabled) {
					GradleClassPathContainer.addTo(javaProject, mon);
				}
			} else {
				//maven将如
			}
			
			List<IProject> projects = new ArrayList<IProject>();
			projects.add(microProject.getProject());
			generateCode(microProject.getProject(),projects);
			return true;
		} else {
			return false;
		}
		
	}
	/**
	 * 生成代码
	 * @param genProject
	 */
	private void generateCode(IProject genProject,List<IProject> projects) throws CoreException {
		List<AbstractGenerator> gens = GeneratorFactory.createGenerators(genProject, basePacakageName,projectType);
		for(AbstractGenerator gen : gens){
			try {
				gen.generate();
			} catch (CodeGenerationException e) {
				e.printStackTrace();
			}
		}
		if(TemplateConstantMessages.GRADLE_PROJECT_TYPE.equalsIgnoreCase(projectType)){
			RefreshAllActionCore.callOn(projects);
		} else {
			
		}
		
	}
	
	/**
	 * 拷贝代码到本地目录下,并创建JAVA工程
	 * 
	 * @param monitor
	 * @throws CoreException
	 */
	private MicroProject createProjectContents(IProgressMonitor monitor) throws CoreException {

		MicroProject microProject = null;
		
		monitor.beginTask("Create project contents", 15);
		monitor.worked(1);
		try {
			File location = new File(getLocation() + File.separator + projectName.getValue());
			SampleProject sampleCode = getSampleProject();

			if (sampleCode != null) {
				sampleCode.createAt(location, getConnectName());// 将模板项目解压拷贝到向导第一页中的LOCATION位置
			}

			monitor.worked(3);
			// create Springboot projects
			File[] ps = dealProjects(location);
			for (File f : ps) {
				microProject = MicroProjectPlugin.getProjectManager().getOrCreate(f);
				if (!microProject.exists()) {
					microProject.newInstance(f, monitor);// 在ECLIPSE中创建JAVA工程.
				}
			}
		} finally {
			monitor.done();
		}
		
		return microProject;
	}

	// 返回文件列表:在ECLIPSE中需要创建的项目名称
	private File[] dealProjects(File location) {
		File parent = location.getParentFile();
		String fileName = location.getName();
		return new File[] { new File(parent, fileName) };
	}

	private SampleProject getSampleProject() {
		return sampleProject.getValue();
	}

	private String getLocation() {
		return location.getValue();
	}

	public LiveExpression<ValidationResult> getProjectNameValidator() {
		return projectNameValidator;
	}

	public Validator getLocationValidator() {
		return locationValidator;
	}

	public Validator getSampleProjectValidator() {
		return sampleProjectValidator;
	}

	public LiveExpression<String> getParentProjectLE() {
		return parentProjectLE;
	}

	public MainProjectValidator getParentProjectValidator() {
		return parentProjectValidator;
	}

	public void setParentProjectValidator(MainProjectValidator parentProjectValidator) {
		this.parentProjectValidator = parentProjectValidator;
	}

	public void setSampleProjectField(LiveExpression<SampleProject> sampleProject) {
		Assert.isLegal(this.sampleProject == null && sampleProject != null);
		this.sampleProject = sampleProject;
		this.sampleProjectValidator = new SampleProjectValidator("Project type", sampleProject);
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getBasePacakageName() {
		return basePacakageName;
	}

	public void setBasePacakageName(String basePacakageName) {
		this.basePacakageName = basePacakageName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
}
