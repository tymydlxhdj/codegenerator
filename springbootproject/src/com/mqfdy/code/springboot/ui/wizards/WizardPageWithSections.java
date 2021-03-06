package com.mqfdy.code.springboot.ui.wizards;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.springboot.core.util.expression.LiveExpression;
import com.mqfdy.code.springboot.core.util.expression.ValueListener;
import com.mqfdy.code.springboot.core.validators.CompositeValidator;
import com.mqfdy.code.springboot.core.validators.ValidationResult;
import com.mqfdy.code.springboot.ui.IPageWithSections;
import com.mqfdy.code.springboot.ui.util.PageSection;


// TODO: Auto-generated Javadoc
/**
 * The Class WizardPageWithSections.
 *
 * @author mqfdy
 */
public abstract class WizardPageWithSections extends WizardPage implements IPageWithSections, ValueListener<ValidationResult> {

	/**
	 * Instantiates a new wizard page with sections.
	 *
	 * @param pageName
	 *            the page name
	 * @param title
	 *            the title
	 * @param titleImage
	 *            the title image
	 */
	protected WizardPageWithSections(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	private List<WizardPageSection> sections = null;
	private CompositeValidator validator;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	//lenovo
	public void createControl(Composite parent) {
		Composite page = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        layout.marginHeight = 1;
        layout.marginWidth = 1;
        page.setLayout(layout);
        validator = new CompositeValidator();
        for (PageSection section : getSections()) {
			section.createContents(page);
			validator.addChild(section.getValidator());
		}
        validator.addListener(this);
        setControl(page);
        getContainer().updateButtons();
        getContainer().updateMessage();
	}
	
	/**
	 * Gets the sections.
	 *
	 * @author mqfdy
	 * @return the sections
	 * @Date 2018-09-03 09:00
	 */
	protected synchronized List<WizardPageSection> getSections() {
		if (sections==null) {
			sections = createSections();
		}
		return sections;
	}
	
	/**
	 * This method should be implemented to generate the contents of the page.
	 *
	 * @author mqfdy
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	protected abstract List<WizardPageSection> createSections();
	
	public void gotValue(LiveExpression<ValidationResult> exp, ValidationResult status) {
		setErrorMessage(null);
		setMessage(null);
		if (status.isOk()) {
		} else if (status.status == IStatus.ERROR) {
			setErrorMessage(status.msg);
		} else if (status.status == IStatus.WARNING) {
			setMessage(status.msg, IMessageProvider.WARNING);
		} else if (status.status == IStatus.INFO) {
			setMessage(status.msg, IMessageProvider.INFORMATION);
		} else {
			setMessage(status.msg, IMessageProvider.NONE);
		}
		setPageComplete(status.isOk());
	}

}
