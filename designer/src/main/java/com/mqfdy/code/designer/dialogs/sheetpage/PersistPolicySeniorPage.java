package com.mqfdy.code.designer.dialogs.sheetpage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;

import com.mqfdy.code.designer.dialogs.IBusinessClassEditorPage;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.model.utils.StringUtil;

public class PersistPolicySeniorPage extends Composite implements
		IBusinessClassEditorPage {
	private String PERSIST_IMPL = "持久化实现机制";
	private String PERSIST_HIBERNATE = "Hibernate";
	private String IMPORT_LOCAL = "从本地导入映射描述文件";

	private Combo comboPersistImpl;// 持久化实现机制
	private Button buttonImport;
	private StyledText textFile;
	private PersistPolicySheetPage parentPage;

	public PersistPolicySeniorPage(Composite parent, int style,
			PersistPolicySheetPage parentPage) {
		super(parent, style);
		this.parentPage = parentPage;
		createContent(this);
		addListeners();
	}

	private void addListeners() {
		buttonImport.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseUp(MouseEvent arg0) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.xml", "*.hbm.xml" });
				String selectedDirectoryName = dialog.open();
				String content = readFileByLines(selectedDirectoryName);
				content = StringUtil.prettyXml(content);
				textFile.setText(content);
			}

		});

	}

	private void createContent(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label labelPersistImpl = new Label(parent, SWT.NONE);
		labelPersistImpl.setText(PERSIST_IMPL);

		comboPersistImpl = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		comboPersistImpl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label labelBlock = new Label(parent, SWT.NONE);
		labelBlock.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		buttonImport = new Button(parent, SWT.NONE);
		buttonImport.setText(IMPORT_LOCAL);
		buttonImport.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 3, 1));

		textFile = new StyledText(parent, SWT.BORDER | SWT.SCROLL_LINE);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 3;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;
		data.heightHint = 100;
		textFile.setLayoutData(data);
	}

	public void initControlValue() {
		comboPersistImpl.add(PERSIST_HIBERNATE, 0);
	}

	public boolean validateInput() {
		// TODO Auto-generated method stub
		return true;
	}

	public void updateTheEditingElement() {

	}

	public String readFromFile(String fileName) {
		String content = "";
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(fileName, true);
			writer.write(content);
			
		} catch (IOException e) {
			Logger.log(e);
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
		}
		return content;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				sb.append(tempString);
				line++;
			}
		} catch (IOException e) {
			Logger.log(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					Logger.log(e1);
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e1) {
					Logger.log(e1);
				}
			}
		}
		return sb.toString();
	}

	public PersistPolicySheetPage getParentPage() {
		return parentPage;
	}
}
