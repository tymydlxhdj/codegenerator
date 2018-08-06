package com.mqfdy.code.designer.editor.actions.pages;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

/**
 * 自动布局对话框
 * 
 * @author mqfdy
 * 
 */
public class AutoLayoutDiaolg extends TrayDialog {

	private int style = 0;
	public AutoLayoutDiaolg(Shell shell) {
		super(shell);
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("自动布局");
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM);
		newShell.setImage(icon);
	}

	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
//		Group g = new Group(parent, SWT.None);
//		GridData gridlayout = new GridData(GridData.FILL_BOTH);
//		gridlayout.horizontalSpan = 1;
//		g.setLayoutData(gridlayout);
//		GridLayout l = new GridLayout();
////		l.horizontalSpacing = 20;
//		l.numColumns = 4;
//		g.setLayout(l);
//		GridData grid = new GridData();
//		grid.heightHint = 50;
//		grid.widthHint = 50;
//		Label label = new Label(g, SWT.NONE);
//		label.setImage(new Image(Display.getDefault(), "C:/Users/Administrator/Desktop/tree.png"));
//		label.setLayoutData(grid);
//		Button bTree = new Button(g, SWT.RADIO);
//		bTree.setText("树状布局");
//		bTree.addSelectionListener(new SelectionListener() {
//			
//			public void widgetSelected(SelectionEvent e) {
//				style = 1;
//			}
//			
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		bTree.setSelection(true);
//		style = 1;
//		Button bRad = new Button(g, SWT.RADIO);
//		bRad.setText("放射状布局");
//		bRad.addSelectionListener(new SelectionListener() {
//			
//			public void widgetSelected(SelectionEvent e) {
//				style = 0;
//			}
//			
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		Button bTree = new Button(parent, SWT.RADIO);
		bTree.setText("树状布局");
		bTree.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				style = 1;
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		bTree.setSelection(true);
		style = 1;
		Button bRad = new Button(parent, SWT.RADIO);
		bRad.setText("放射状布局");
		bRad.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				style = 0;
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		return parent;
	}
	

	public int getStyle() {
		return style;
	}
	
}
