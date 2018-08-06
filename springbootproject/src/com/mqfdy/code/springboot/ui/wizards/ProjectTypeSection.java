package com.mqfdy.code.springboot.ui.wizards;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.springboot.core.datasource.SampleProject;
import com.mqfdy.code.springboot.core.datasource.SampleProjectRegistry;
import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.validators.ValidationResult;
import com.mqfdy.code.springboot.core.validators.Validator;


/**向导第一个页面:项目类型
 * @author mqfdy
 */
public class ProjectTypeSection extends WizardPageSection {

	private static final int SIZING_TEXT_FIELD_WIDTH = 250;
	private SampleProjectRegistry samples = SampleProjectRegistry.getInstance();
	//private Combo sampleProjectField;
	private  Text sampleProjectField;
	private Validator validator;
	private LiveExpression<SampleProject> sampleProjectExp = new LiveExpression<SampleProject>(null) {
		@Override
		protected SampleProject compute() {
			if (sampleProjectField!=null) {
				String sampleProjectName = sampleProjectField.getText();
				return samples.get(sampleProjectName);
			}
			return null;
		}
	};

	public ProjectTypeSection(BaseInfoWizardPage owner) {
		super(owner);
		owner.operation.setSampleProjectField(sampleProjectExp);
		this.validator = owner.operation.getSampleProjectValidator();
	}

	@Override
	public LiveExpression<ValidationResult> getValidator() {
		return validator;
	}
    /**
     * 向导第一个页面:添加项目类型
     */
	@Override
	public void createContents(Composite page) {
        Composite group = new Composite(page, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        group.setLayout(layout);
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // 隐藏项目模板（2017年2月17日 10:45:48） 未删除
        group.setVisible(false);
        Label projectLabel = new Label(group, SWT.NONE);
        projectLabel.setText("Project type:");//项目类型
        sampleProjectField = new Text(group, SWT.BORDER);
        sampleProjectField.setText(getSampleProject());
        sampleProjectField.setEnabled(false);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        sampleProjectField.setLayoutData(data);
        sampleProjectField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				sampleProjectExp.refresh();
			}
		});
        sampleProjectExp.refresh();
	}
	
	private String getSampleProject(){
		List<SampleProject>sample = samples.getAll();
		String name =	sample.get(0).getName();
		return name;
	}
}
