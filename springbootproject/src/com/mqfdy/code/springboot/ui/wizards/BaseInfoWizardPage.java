package com.mqfdy.code.springboot.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;

import com.mqfdy.code.springboot.core.NewMicroProjectOperation;

/**
 * @author zjing
 */
public class BaseInfoWizardPage extends WizardPageWithSections {
	
	private static final ImageDescriptor WIZBAN_IMAGE = ImageDescriptor.createFromURL(
			BaseInfoWizardPage.class.getClassLoader().getResource("icons/wizban/boot_wizard.png"));
	
	public final NewMicroProjectOperation operation;
	
	public BaseInfoWizardPage(NewMicroProjectOperation operation) {
		super("BaseInfoWizardPage", "项目基本信息配置", WIZBAN_IMAGE);
		this.operation = operation;
	}

	@Override
	protected List<WizardPageSection> createSections() {
		List<WizardPageSection> sections = new ArrayList<WizardPageSection>();
		//sections.add(new SelectParentProjectSection(this));//微服务主项目
		sections.add(new NewProjectNameSection(this));//项目名称
		sections.add(new ProjectLocationSection(this));//保存位置
		sections.add(new ProjectTypeSection(this));//项目类型
		return sections;
	}

}
