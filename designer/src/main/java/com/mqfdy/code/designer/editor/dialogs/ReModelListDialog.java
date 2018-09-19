package com.mqfdy.code.designer.editor.dialogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;

// TODO: Auto-generated Javadoc
/**
 * 重复BOM对话框.
 *
 * @author mqfdy
 */
public class ReModelListDialog extends TitleAreaDialog {
	
	/** 标签页对象. */
	private TableViewer tableViewer;
	
	/** The ok button. */
	private Button okButton;
	
	/** The om list. */
	private List<IResource> omList;
	
	/** The table. */
	private Table table;

	/**
	 * Instantiates a new re model list dialog.
	 *
	 * @param shell
	 *            the shell
	 * @param omList
	 *            the om list
	 */
	public ReModelListDialog(Shell shell,List<IResource> omList) {
		super(shell);
		this.omList = omList;
	}

	/**
	 * 操作按钮.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("重复模型");
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_OBJECTMODEL);
		newShell.setImage(icon);
	}

	/**
	 * Creates the dialog area.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the control
	 * @Date 2018-09-03 09:00
	 */
	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		setTitle("请选择要保留ID的模型");
		setMessage("同目录下包含有与本模型的ID重复的OM文件，会影响UI中数据源的读取，" +
				"\n为保证创建UI打开时的正确性，请在列表中选择一个要保留的模型，修改其他模型的ID");
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
//		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
//		gridData.heightHint = 200;

		tableViewer = new TableViewer(parent, SWT.RADIO
				| SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);
		TableColumn tableColumn = new TableColumn(table,
				SWT.LEFT);
		tableColumn.setText("OM文件名称");
		tableColumn.setWidth(550);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new TableContentProvider());

		tableViewer.setInput(omList);
		tableViewer.refresh();
		
		
		return parent;
	}
	
	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		if(table.getSelection() == null || table.getSelection().length == 0){
			setErrorMessage("请选择一条记录！");
			return;
		}
		if(table.getSelection() != null && table.getSelection().length > 0
				&& table.getSelection()[0].getData() instanceof IResource){
			IResource file1 = (IResource) table.getSelection()[0].getData();
			omList.remove(file1);
			BusinessModelManager manager = BusinessModelUtil.getBusinessModelDiagramEditor().getBusinessModelManager();
			String curModelId = manager.getBusinessObjectModel().getId();
			for(IResource f : omList){
				replaceId(f, manager, curModelId);
			}
		}
		
		super.okPressed();
	}
	
	/**
	 * Replace id.
	 *
	 * @author mqfdy
	 * @param f
	 *            the f
	 * @param manager
	 *            the manager
	 * @param curModelId
	 *            the cur model id
	 * @Date 2018-09-03 09:00
	 */
	public void replaceId(IResource f, BusinessModelManager manager, CharSequence curModelId){
		String location = f.getLocation().toString();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");

		if(location.equals(manager.getPath())){
			manager.getBusinessObjectModel().setId(uuid);
			BusinessModelUtil.getBusinessModelDiagramEditor().doSave();
		}else{
			File reFile = new File(f.getLocation().toOSString());
			String cont = read(f.getLocation().toOSString());
			// 对得到的内容进行处理
			cont = cont.replace(curModelId, uuid);
			// 更新源文件
			write(cont, reFile);
			try {
				f.refreshLocal(0, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets the ok button.
	 *
	 * @author mqfdy
	 * @return the ok button
	 * @Date 2018-09-03 09:00
	 */
	public Button getOkButton() {
		return okButton;
	}
	
	/**
	 * Read.
	 *
	 * @author mqfdy
	 * @param path
	 *            the path
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String read(String path) {
		File file = new File(path);
		StringBuffer res = new StringBuffer();
		  String line = null;
		  BufferedReader reader = null;
		  FileReader fr = null;
		  try {
		   fr = new FileReader(file);
		   reader = new BufferedReader(fr);
		   while ((line = reader.readLine()) != null) {
		    res.append(line + "\n");
		   }
		   reader.close();
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  } finally {
			  if(reader != null){
				  try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  if(fr != null){
				  try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  }
		  return res.toString();
	 }

	 /**
	 * Write.
	 *
	 * @author mqfdy
	 * @param cont
	 *            the cont
	 * @param dist
	 *            the dist
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
 	public static boolean write(String cont, File dist) {
		 BufferedWriter writer = null;
		 FileWriter fr = null;
		 try {
		   fr = new FileWriter(dist);
		   writer = new BufferedWriter(fr);
		   writer.write(cont);
		   writer.flush();
		   writer.close();
		   return true;
		 } catch (IOException e) {
		   e.printStackTrace();
		   return false;
		 } finally {
			 if(writer != null){
				 try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 if(fr != null){
				 try {
					 fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
	}

}
