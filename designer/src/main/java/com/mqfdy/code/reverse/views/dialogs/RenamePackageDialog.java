package com.mqfdy.code.reverse.views.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.dialogs.IBusinessClassEditorPage;
import com.mqfdy.code.resource.validator.ValidatorUtil;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.models.TreeModel;

public class RenamePackageDialog extends TitleAreaDialog {

	public static final int DIALOG_WIDTH = 500;
	public static final int DIALOG_HEIGHT = 400;
	private Label remarkLabel;
	private Text remarkText;
	private Label nameLabel;
	private Text nameText;
	private Label displayNameLabel;
	private Text displayNameText;
	
	private TreeNode currentNode;
	private TreeViewer treeViewer;
	
	private String name;
	private String displayName;
	private String remarks;
	
	public TreeNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(TreeNode currentNode) {
		this.currentNode = currentNode;
	}

	public RenamePackageDialog(Shell parentShell, String name, String displayName, String remarks) {
		super(parentShell);
		this.name = name;
		this.displayName = displayName;
		this.remarks = remarks;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("修改业务模型包");
		//设置父容器的布局
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		parent.layout();
		
		//在父容器中创建子容器group1
		Group group = new Group(parent, SWT.NULL);
		
		//设置group1在父容器中的布局参数
		GridData gridData1 = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		gridData1.heightHint = 350;
		gridData1.widthHint = DIALOG_WIDTH;
		group.setLayoutData(gridData1);
				
		//创建每个分组内的布局
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 2;
				
		//设置group的布局
		group.setLayout(groupLayout);
		group.layout();
		
		nameLabel = new Label(group, SWT.NULL);
		nameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true));
		nameLabel.setText("名称:  ");
		
		nameText = new Text(group, SWT.BORDER);
		GridData textData = new GridData(SWT.LEFT, SWT.LEFT, true, true);
		textData.widthHint = 400;
		nameText.setLayoutData(textData);
		nameText.setText(this.name);
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				displayNameText.setText(nameText.getText());
			}
		});
		
		displayNameLabel = new Label(group, SWT.NULL);
		displayNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true));
		displayNameLabel.setText("显示名:  ");
		
		displayNameText = new Text(group, SWT.BORDER);
		displayNameText.setLayoutData(textData);
		displayNameText.setText(this.displayName);
		
		remarkLabel = new Label(group, SWT.NULL);
		remarkLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true));
		remarkLabel.setText("备注:  ");
		
		GridData remarkData = new GridData(SWT.LEFT, SWT.LEFT, true, true);
		remarkData.widthHint = 400;
		remarkData.heightHint = 100;
		remarkText = new Text(group, SWT.BORDER|SWT.WRAP| SWT.V_SCROLL);
		remarkText.setLayoutData(remarkData);
		if(this.remarks != null) {
			remarkText.setText(this.remarks);
		}
		
		return parent;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("修改业务模型包");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(DIALOG_WIDTH, DIALOG_HEIGHT);
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX | SWT.MIN;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void okPressed() {
		setMessage("");
		if(nameText.getText() == null || nameText.getText().trim().length() == 0) {
			setMessage("名称不能为空", IMessageProvider.ERROR);
			return ;
		} else if(displayNameText.getText() == null || displayNameText.getText().trim().length() == 0) {
			setMessage("显示名不能为空", IMessageProvider.ERROR);
			return ;
		} else if (!ValidatorUtil.valiName(nameText.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_RULE);
			return;
		} else if (!ValidatorUtil.valiDisplayName(displayNameText.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME);
			return;
		} else if (!ValidatorUtil.valiNameLength(nameText.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_LENGTH);
			return;
		} else if (!ValidatorUtil.valiDisplayNameLength(displayNameText.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME_LENGTH);
			return;
		} else if (!ValidatorUtil.valiRemarkLength(remarkText.getText())) {
			setErrorMessage(IBusinessClassEditorPage.TOOLONG_ERROR_REMARK);
			return;
		}
		
		if (currentNode != null) {
			if(currentNode.getChilds() != null) {
				List<TreeNode> brotherNodeList = currentNode.getChilds();
				for (TreeNode brotherNode : brotherNodeList) {
					if(brotherNode != currentNode && brotherNode.getName().equals(nameText.getText())) {
						setMessage("业务包名称已存在", IMessageProvider.ERROR);
						return ;
					}
				}
			}
		} else {
			List<TreeModel> modelList = (List<TreeModel>) treeViewer.getInput();
			
			for(TreeModel input: modelList) {
				TreeNode brotherNode = input.getRoot();
				if(brotherNode != currentNode && brotherNode.getName().equals(nameText.getText())) {
					setMessage("业务包名称已存在", IMessageProvider.ERROR);
					return ;
				}
			}
			
		}
		
		setName(nameText.getText());
		setDisplayName(displayNameText.getText());
		setRemarks(remarkText.getText());
		super.okPressed();
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	public void setTreeViewer(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
