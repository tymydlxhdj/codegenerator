package com.mqfdy.code.springboot.ui.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.validators.ValidationResult;


// TODO: Auto-generated Javadoc
/**
 * Wizard page section that allows the user to enter the name of a new project. 
 * 
 * @author lenovo
 */
public class NewProjectNameSection extends WizardPageSection {
	
	private static final int SIZING_TEXT_FIELD_WIDTH = 250;

	private Text projectNameField;

	private final LiveExpression<String> projectNameExp = new LiveExpression<String>(null) {
		protected String compute() {
			if (projectNameField!=null) {
				return projectNameField.getText();
			}
			return null;
		}
	};
	
	private final LiveExpression<ValidationResult> validator;

	/**
	 * Instantiates a new new project name section.
	 *
	 * @param owner
	 *            the owner
	 */
	public NewProjectNameSection(BaseInfoWizardPage owner) {
		super(owner);
		owner.operation.setProjectNameField(projectNameExp);
		this.validator = owner.operation.getProjectNameValidator();
	}

	@Override
	public LiveExpression<ValidationResult> getValidator() {
		return validator;
	}

	/**
	 * 向导第一个页面:添加项目名称
	 */
	@Override
	public void createContents(Composite page) {
        // project specification group
        Composite projectGroup = new Composite(page, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label projectLabel = new Label(projectGroup, SWT.NONE);
        projectLabel.setText("Project name:");

        projectNameField = new Text(projectGroup, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        projectNameField.setLayoutData(data);
        projectNameField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				projectNameExp.refresh();
			}
		});
        projectNameExp.refresh();
	}

	/**
	 * Gets the project name.
	 *
	 * @author mqfdy
	 * @return the project name
	 * @Date 2018-09-03 09:00
	 */
	public LiveExpression<String> getProjectName() {
		return projectNameExp;
	}

}
