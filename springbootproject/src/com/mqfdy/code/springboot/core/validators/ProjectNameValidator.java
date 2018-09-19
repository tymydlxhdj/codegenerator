package com.mqfdy.code.springboot.core.validators;

import static com.mqfdy.code.springboot.core.validators.ValidationResult.error;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.util.expression.ValueListener;


// TODO: Auto-generated Javadoc
/**
 * The Class ProjectNameValidator.
 *
 * @author zjing
 * 
 *         TODO: remove: duplicated in eclipse-integration-commons (reusable
 *         component!). Don't forget to move testing code as well.
 * 
 *         See
 *         org.springsource.ide.eclipse.commons.livexp.core.validators.NewProjectNameValidator
 */
public class ProjectNameValidator extends Validator implements ValueListener<String> {
	
	private final LiveExpression<String> projectNameField;

	/**
	 * Instantiates a new project name validator.
	 *
	 * @param projectNameField
	 *            the project name field
	 */
	public ProjectNameValidator(LiveExpression<String> projectNameField) {
		this.projectNameField = projectNameField;
		projectNameField.addListener(this);
	}

	private boolean isAllowedChar(char c) {
		return Character.isLetterOrDigit(c) 
			|| "-_.".indexOf(c)>=0;
	}
	
	/**
	 * Checks if is chinese character.
	 *
	 * @author mqfdy
	 * @param chineseStr
	 *            the chinese str
	 * @return true, if is chinese character
	 * @Date 2018-09-03 09:00
	 */
	//判断是否为中文
	public boolean isChineseCharacter(String chineseStr) {
		char[] charArray = chineseStr.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				// Java判断一个字符串是否有中文是利用Unicode编码来判断，
				// 因为中文的编码区间为：0x4e00--0x9fbb
				return true;
			}
		}
		return false;
	}

	@Override
	protected ValidationResult compute() {
		String projectName = projectNameField.getValue();
		if (projectName==null) {
			return error("Project name is undefined");
		} 
		//结果语句
		else if (isChineseCharacter(projectName)) {
			return error("Not for Chinese");
		}
		
		//Whether the length 
		else if(projectName.length() > 60){
			return error("The title is too long");
		}
		else if ("".equals(projectName)) {
			return error("Project name is empty");
		} else if (projectName.indexOf(' ')>=0) {
			return error("Project name contains spaces");
		} else if (existsInWorkspace(projectName)) {
			return error("A project with name '"+projectName+"' already exists in the workspace.");
		}else {
			for (int i = 0; i < projectName.length(); i++) {
				char c = projectName.charAt(i);
				if (!isAllowedChar(c)) {
					return error("Project name contains forbidden character '"+c+"'");
				}
			}
		}
		return ValidationResult.OK;
	}
	
	private boolean existsInWorkspace(String projectName) {
		IProject[] eclipseProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject p : eclipseProjects) {
			if (p.getName().equals(projectName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Called when the projectName is changed.
	 */
	public void gotValue(LiveExpression<String> exp, String value) {
		Assert.isLegal(exp==projectNameField);
		refresh();
	}

}
