package com.mqfdy.code.springboot.ui.wizards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.mqfdy.code.springboot.core.MicroProjectPlugin;
import com.mqfdy.code.springboot.core.NewMicroProjectOperation;
import com.mqfdy.code.springboot.core.datasource.DataSourceUtils;

public class DBWizardPage extends WizardPage {

	private static final ImageDescriptor WIZBAN_IMAGE = ImageDescriptor.createFromURL(
			DBWizardPage.class.getClassLoader().getResource("icons/wizban/boot_wizard.png"));
	
	public final NewMicroProjectOperation operation;
	
	private static final int SIZING_TEXT_FIELD_WIDTH = 200;
	
	private Combo sampleDataSourceField;
	
	private String connectName = null;

	
	protected DBWizardPage(NewMicroProjectOperation operation) {
		super("DBWizardPage", "新建数据源", WIZBAN_IMAGE);
		this.operation = operation;
	}
	
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite page = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        layout.marginHeight = 1;
        layout.marginWidth = 1;
        page.setLayout(layout);
        
        Group group = new Group(page, SWT.NONE);
		group.setText("Configuration: ");
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label projectLabel = new Label(group, SWT.NONE);
        projectLabel.setText("DataSource:");
        //数据源列表
        sampleDataSourceField = new Combo(group, SWT.DROP_DOWN|SWT.READ_ONLY);
        GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
        data2.widthHint = SIZING_TEXT_FIELD_WIDTH;
        sampleDataSourceField.setLayoutData(data2);
        sampleDataSourceField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setConnectName(sampleDataSourceField.getText());
				updateStatus();
			}
		});
        
		final Button addDataSource = new Button(group, SWT.NONE);
		GridData data = new GridData(GridData.END, GridData.CENTER, false,
				false);
		data.horizontalSpan = 1;
		addDataSource.setLayoutData(data);
		addDataSource.setText("Add DataSource");
		//添加数据源
		addDataSource.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IConnectionProfile dataSourceWizard = DataSourceUtils.newDataSourceWizard();
				if(dataSourceWizard != null) {
					String name = dataSourceWizard.getName();
					setConnectName(name);
					updateStatus();
					if(name!=null){
						updateButton(name);	
					}
				}
			}
			private void updateButton(String newText ) {
				int selIndex = sampleDataSourceField.getSelectionIndex();
				if (selIndex < 0 || !newText.equals(sampleDataSourceField.getItem(selIndex))) {
					String[] items = sampleDataSourceField.getItems();
					for (int i = 0; i < items.length; i++) {
						if (items[i].equals(newText)) {
							sampleDataSourceField.select(i);
							return;
						}
					}
				}
				String[] items = sampleDataSourceField.getItems();
				String[] newItems = new String[items.length + 1];
	            System.arraycopy( items, 0, newItems, 0, items.length );
	            newItems[items.length] = newText;
	            sampleDataSourceField.setItems(newItems);
	            sampleDataSourceField.select(items.length);
			}					
		});
		initDataSourceCombo();
		
		setControl(page);
		updateStatus();
		
	}
	
	private void updateStatus(){
		setErrorMessage(null);
		setMessage(null);
		setPageComplete(true);
//		if(connectName == null){
//			setErrorMessage("DataSource is null");
//			setPageComplete(false);
//		}
	}
	
	//保存数据源
	private void initDataSourceCombo(){
		Iterator<String> profileIterator = MicroProjectPlugin.instance().getConnectionProfileRepository().connectionProfileNames();
		List<String> list = new ArrayList<String>();
		while (profileIterator.hasNext()) {
			list.add(profileIterator.next());
		}
		sampleDataSourceField.setItems(list.toArray(new String[list.size()]));
			
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
		operation.setConnectName(connectName);
	}

}
