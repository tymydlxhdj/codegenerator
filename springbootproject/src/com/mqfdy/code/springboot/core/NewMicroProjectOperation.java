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

// TODO: Auto-generated Javadoc
/**
 * 新建Springboot项目向导操作类
 * 
 * @author mqfdy
 */
public class NewMicroProjectOperation {

	/** The progress. */
	public static int progress = 1;

	/** The total work. */
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


	/**
	 * Sets the project name field.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new project name field
	 * @Date 2018-09-03 09:00
	 */
	public void setProjectNameField(LiveExpression<String> name) {
		Assert.isLegal(projectName == null && name != null);
		this.projectName = name;
		this.projectNameValidator = new ProjectNameValidator(projectName);
	}

	/**
	 * Sets the location field.
	 *
	 * @author mqfdy
	 * @param location
	 *            the new location field
	 * @Date 2018-09-03 09:00
	 */
	public void setLocationField(LiveExpression<String> location) {
		Assert.isLegal(this.location == null && location != null);
		this.location = location;
		this.locationValidator = new NewProjectLocationValidator("Location", location, projectName);
	}

	/**
	 * Gets the project name field.
	 *
	 * @author mqfdy
	 * @return the project name field
	 * @Date 2018-09-03 09:00
	 */
	public LiveExpression<String> getProjectNameField() {
		return projectName;
	}

	/**
	 * Gets the project location.
	 *
	 * @author mqfdy
	 * @return the project location
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Perform.
	 *
	 * @author mqfdy
	 * @param mon
	 *            the mon
	 * @return true, if successful
	 * @throws CoreException
	 *             the core exception
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the project name validator.
	 *
	 * @author mqfdy
	 * @return the project name validator
	 * @Date 2018-09-03 09:00
	 */
	public LiveExpression<ValidationResult> getProjectNameValidator() {
		return projectNameValidator;
	}

	/**
	 * Gets the location validator.
	 *
	 * @author mqfdy
	 * @return the location validator
	 * @Date 2018-09-03 09:00
	 */
	public Validator getLocationValidator() {
		return locationValidator;
	}

	/**
	 * Gets the sample project validator.
	 *
	 * @author mqfdy
	 * @return the sample project validator
	 * @Date 2018-09-03 09:00
	 */
	public Validator getSampleProjectValidator() {
		return sampleProjectValidator;
	}

	/**
	 * Gets the parent project LE.
	 *
	 * @author mqfdy
	 * @return the parent project LE
	 * @Date 2018-09-03 09:00
	 */
	public LiveExpression<String> getParentProjectLE() {
		return parentProjectLE;
	}

	/**
	 * Gets the parent project validator.
	 *
	 * @author mqfdy
	 * @return the parent project validator
	 * @Date 2018-09-03 09:00
	 */
	public MainProjectValidator getParentProjectValidator() {
		return parentProjectValidator;
	}

	/**
	 * Sets the parent project validator.
	 *
	 * @author mqfdy
	 * @param parentProjectValidator
	 *            the new parent project validator
	 * @Date 2018-09-03 09:00
	 */
	public void setParentProjectValidator(MainProjectValidator parentProjectValidator) {
		this.parentProjectValidator = parentProjectValidator;
	}

	/**
	 * Sets the sample project field.
	 *
	 * @author mqfdy
	 * @param sampleProject
	 *            the new sample project field
	 * @Date 2018-09-03 09:00
	 */
	public void setSampleProjectField(LiveExpression<SampleProject> sampleProject) {
		Assert.isLegal(this.sampleProject == null && sampleProject != null);
		this.sampleProject = sampleProject;
		this.sampleProjectValidator = new SampleProjectValidator("Project type", sampleProject);
	}

	/**
	 * Gets the connect name.
	 *
	 * @author mqfdy
	 * @return the connect name
	 * @Date 2018-09-03 09:00
	 */
	public String getConnectName() {
		return connectName;
	}

	/**
	 * Sets the connect name.
	 *
	 * @author mqfdy
	 * @param connectName
	 *            the new connect name
	 * @Date 2018-09-03 09:00
	 */
	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	/**
	 * Gets the base pacakage name.
	 *
	 * @author mqfdy
	 * @return the base pacakage name
	 * @Date 2018-09-03 09:00
	 */
	public String getBasePacakageName() {
		return basePacakageName;
	}

	/**
	 * Sets the base pacakage name.
	 *
	 * @author mqfdy
	 * @param basePacakageName
	 *            the new base pacakage name
	 * @Date 2018-09-03 09:00
	 */
	public void setBasePacakageName(String basePacakageName) {
		this.basePacakageName = basePacakageName;
	}

	/**
	 * Gets the project type.
	 *
	 * @author mqfdy
	 * @return the project type
	 * @Date 2018-09-03 09:00
	 */
	public String getProjectType() {
		return projectType;
	}

	/**
	 * Sets the project type.
	 *
	 * @author mqfdy
	 * @param projectType
	 *            the new project type
	 * @Date 2018-09-03 09:00
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
}
