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
// TODO: Auto-generated Javadoc

/**
 * 选择删除图形和对象.
 *
 * @author mqfdy
 */
public class SelectDeleteWayDialog extends IconAndMessageDialog {

	/** The del. */
	// private Button okButton;
	private boolean del;
	
	/** The del diagram element. */
	private Button delDiagramElement;
	
	/** The del object. */
	private Button delObject;

	/**
	 * Instantiates a new select delete way dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
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
	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
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
		delDiagramElement = new Button(parent, SWT.RADIO);
		delDiagramElement.setText("只删除图形");
		delDiagramElement.setSelection(true);
		delObject = new Button(parent, SWT.RADIO);
		delObject.setText("删除图形和对象");
		return parent;
	}

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		setDel(delObject.getSelection());
		super.okPressed();
	}

	/**
	 * @return
	 */
	@Override
	protected Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Checks if is del.
	 *
	 * @author mqfdy
	 * @return true, if is del
	 * @Date 2018-09-03 09:00
	 */
	public boolean isDel() {
		return del;
	}

	/**
	 * Sets the del.
	 *
	 * @author mqfdy
	 * @param del
	 *            the new del
	 * @Date 2018-09-03 09:00
	 */
	public void setDel(boolean del) {
		this.del = del;
	}
}
