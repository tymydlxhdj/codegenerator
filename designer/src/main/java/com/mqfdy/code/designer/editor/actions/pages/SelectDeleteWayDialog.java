package com.mqfdy.code.designer.editor.actions.pages;

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
/**
 * 选择删除图形和对象
 * @author mqfdy
 *
 */
public class SelectDeleteWayDialog extends IconAndMessageDialog {

	// private Button okButton;
	private boolean del;
	private Button delDiagramElement;
	private Button delObject;

	public SelectDeleteWayDialog(Shell parentShell) {
		super(parentShell);
	}

	// /**
	// * 操作按钮
	// */
	// protected void createButtonsForButtonBar(Composite parent) {
	// okButton = createButton(parent, IDialogConstants.OK_ID,
	// IDialogConstants.OK_LABEL, true);
	// Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID,
	// IDialogConstants.CANCEL_LABEL, false);
	// }
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("请选择删除方式");
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM);
		newShell.setImage(icon);
	}

	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		delDiagramElement = new Button(parent, SWT.RADIO);
		delDiagramElement.setText("只删除图形");
		delDiagramElement.setSelection(true);
		delObject = new Button(parent, SWT.RADIO);
		delObject.setText("删除图形和对象");
		return parent;
	}

	@Override
	protected void okPressed() {
		setDel(delObject.getSelection());
		super.okPressed();
	}

	@Override
	protected Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}
}
