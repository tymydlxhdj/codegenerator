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
import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.resource.validator.ValidatorUtil;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.models.TreeModel;

// TODO: Auto-generated Javadoc
/**
 * The Class InsertPackageDialog.
 *
 * @author mqfdy
 */
public class InsertPackageDialog extends TitleAreaDialog {

	/** The Constant DIALOG_WIDTH. */
	public static final int DIALOG_WIDTH = 500;
	
	/** The Constant DIALOG_HEIGHT. */
	public static final int DIALOG_HEIGHT = 400;
	
	/** The remark label. */
	private Label remarkLabel;
	
	/** The remark text. */
	private Text remarkText;
	
	/** The name label. */
	private Label nameLabel;
	
	/** The name text. */
	private Text nameText;
	
	/** The display name label. */
	private Label displayNameLabel;
	
	/** The display name text. */
	private Text displayNameText;
	
	/** The current node. */
	private TreeNode currentNode;
	
	/** The tree viewer. */
	private TreeViewer treeViewer;
	
	/** The name. */
	private String name;
	
	/** The display name. */
	private String displayName;
	
	/** The remarks. */
	private String remarks;
	
	/**
	 * Gets the current node.
	 *
	 * @author mqfdy
	 * @return the current node
	 * @Date 2018-09-03 09:00
	 */
	public TreeNode getCurrentNode() {
		return currentNode;
	}

	/**
	 * Sets the current node.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            the new current node
	 * @Date 2018-09-03 09:00
	 */
	public void setCurrentNode(TreeNode currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * Instantiates a new insert package dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public InsertPackageDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
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
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("创建业务模型包");
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
		nameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
		nameLabel.setText("名称:  ");
		
		nameText = new Text(group, SWT.BORDER);
		GridData textData = new GridData(SWT.LEFT, SWT.LEFT, true, false);
		textData.widthHint = 400;
		nameText.setLayoutData(textData);
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				displayNameText.setText(nameText.getText());
			}
		});
		
		displayNameLabel = new Label(group, SWT.NULL);
		displayNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
		displayNameLabel.setText("显示名:  ");
		
		displayNameText = new Text(group, SWT.BORDER);
		displayNameText.setLayoutData(textData);
		
		remarkLabel = new Label(group, SWT.NULL);
		remarkLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
		remarkLabel.setText("备注:  ");
		
		GridData remarkData = new GridData(SWT.LEFT, SWT.LEFT, true, false);
		remarkData.widthHint = 400;
		remarkData.heightHint = 100;
		remarkText = new Text(group, SWT.BORDER|SWT.WRAP| SWT.V_SCROLL);
		remarkText.setLayoutData(remarkData);
		
//		Scrollable
		return parent;
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
		super.configureShell(newShell);
		newShell.setText("创建业务模型包");
	}

	/**
	 * @return
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(DIALOG_WIDTH, DIALOG_HEIGHT);
	}

	/**
	 * @return
	 */
	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX | SWT.MIN;
	}
	
	/**
	 * 
	 */
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
		
		if(nameText.getText() == null || nameText.getText().trim().length() == 0) {
			String errorMessage = "名称不能为空 !";
			setMessage(errorMessage, IMessageProvider.ERROR);
			return ;
		}
		else if(nameText.getText().equalsIgnoreCase("java")){
			String errorMessage = "\"java\"不能做为命名空间名称";
			setMessage(errorMessage, IMessageProvider.ERROR);
			return ;
		}else {
			if(new KeyWordsChecker().doCheckJava(nameText.getText())) {
				String errorMessage = "名称不能是java关键字 !";
				setMessage(errorMessage, IMessageProvider.ERROR);
				return ;
			}
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

	/**
	 * Gets the tree viewer.
	 *
	 * @author mqfdy
	 * @return the tree viewer
	 * @Date 2018-09-03 09:00
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * Sets the tree viewer.
	 *
	 * @author mqfdy
	 * @param treeViewer
	 *            the new tree viewer
	 * @Date 2018-09-03 09:00
	 */
	public void setTreeViewer(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	/**
	 * Gets the name.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the display name.
	 *
	 * @author mqfdy
	 * @return the display name
	 * @Date 2018-09-03 09:00
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @author mqfdy
	 * @param displayName
	 *            the new display name
	 * @Date 2018-09-03 09:00
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the remarks.
	 *
	 * @author mqfdy
	 * @return the remarks
	 * @Date 2018-09-03 09:00
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the remarks.
	 *
	 * @author mqfdy
	 * @param remarks
	 *            the new remarks
	 * @Date 2018-09-03 09:00
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
