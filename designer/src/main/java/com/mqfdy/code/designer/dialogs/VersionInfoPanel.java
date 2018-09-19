package com.mqfdy.code.designer.dialogs;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.model.VersionInfo;
import com.mqfdy.code.model.utils.DateTimeUtil;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 版本信息显示面板.
 *
 * @author mqfdy
 */
public class VersionInfoPanel extends Composite {

	/** The Constant GROUP_TITLE_VERSION. */
	public static final String GROUP_TITLE_VERSION = "版本信息";

	/** The Constant VERSIONNUMBER_LABEL_TEXT. */
	public static final String VERSIONNUMBER_LABEL_TEXT = "版本号：";
	
	/** The Constant CREATOR_LABEL_TEXT. */
	public static final String CREATOR_LABEL_TEXT = "创建人：";
	
	/** The Constant MODIFIER_LABEL_TEXT. */
	public static final String MODIFIER_LABEL_TEXT = "修改人：";
	
	/** The Constant CREATEDTIME_LABEL_TEXT. */
	public static final String CREATEDTIME_LABEL_TEXT = "创建时间：";
	
	/** The Constant CHANGEDTIME_LABEL_TEXT. */
	public static final String CHANGEDTIME_LABEL_TEXT = "修改时间：";
	
	/** The Constant DESCRIPTION_LABEL_TEXT. */
	public static final String DESCRIPTION_LABEL_TEXT = "描述：";

	/** The label version number. */
	private Label label_versionNumber;
	
	/** The text version number. */
	private Text text_versionNumber;

	/** The label creator. */
	private Label label_creator;
	
	/** The text creator. */
	private Text text_creator;

	/** The label modifier. */
	private Label label_modifier;
	
	/** The text modifier. */
	private Text text_modifier;

	/** The label created time. */
	private Label label_createdTime;
	
	/** The text created time. */
	private Text text_createdTime;

	/** The label changed time. */
	private Label label_changedTime;
	
	/** The text changed time. */
	private Text text_changedTime;

	// private Label label_description;
	// private Text text_description;

	/** The group version info. */
	private Group group_versionInfo;

	/** 版本信息. */
	private VersionInfo versionInfo;

	/**
	 * Instantiates a new version info panel.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public VersionInfoPanel(Composite parent, int style) {
		super(parent, style);
		createContent(this);
	}

	/**
	 * Creates the content.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	private void createContent(Composite parent) {
		parent.setLayout(new FillLayout());
		group_versionInfo = new Group(parent, SWT.NONE | SWT.FILL);
		group_versionInfo.setText(GROUP_TITLE_VERSION);

		// 设置布局
		GridLayout versionLayout = new GridLayout();
		versionLayout.numColumns = 4;
		group_versionInfo.setLayout(versionLayout);

		label_versionNumber = new Label(group_versionInfo, SWT.NONE);
		label_versionNumber.setText(VERSIONNUMBER_LABEL_TEXT);
		text_versionNumber = new Text(group_versionInfo, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		text_versionNumber.setLayoutData(gridData);
		text_versionNumber.setEnabled(false);

		label_creator = new Label(group_versionInfo, SWT.NONE);
		label_creator.setText(CREATOR_LABEL_TEXT);
		text_creator = new Text(group_versionInfo, SWT.BORDER);
		text_creator.setEnabled(false);
		GridData gridData_normal = new GridData();
		gridData_normal.horizontalAlignment = SWT.FILL;
		gridData_normal.grabExcessHorizontalSpace = true;
		text_creator.setLayoutData(gridData_normal);

		label_modifier = new Label(group_versionInfo, SWT.NONE);
		label_modifier.setText(MODIFIER_LABEL_TEXT);
		text_modifier = new Text(group_versionInfo, SWT.BORDER);
		text_modifier.setEnabled(false);
		text_modifier.setLayoutData(gridData_normal);

		label_createdTime = new Label(group_versionInfo, SWT.NONE);
		label_createdTime.setText(CREATEDTIME_LABEL_TEXT);
		text_createdTime = new Text(group_versionInfo, SWT.BORDER);
		text_createdTime.setEnabled(false);
		text_createdTime.setLayoutData(gridData_normal);

		label_changedTime = new Label(group_versionInfo, SWT.NONE);
		label_changedTime.setText(CHANGEDTIME_LABEL_TEXT);
		text_changedTime = new Text(group_versionInfo, SWT.BORDER);
		text_changedTime.setEnabled(false);
		text_changedTime.setLayoutData(gridData_normal);

		// label_description = new Label(group_versionInfo, SWT.NONE);
		// label_description.setText(DESCRIPTION_LABEL_TEXT);
		// text_description = new Text(group_versionInfo,SWT.BORDER);
		// text_description.setLayoutData(gridData);

	}

	/**
	 * Inits the control value.
	 *
	 * @author mqfdy
	 * @param versionInfo
	 *            the version info
	 * @Date 2018-09-03 09:00
	 */
	public void initControlValue(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
		if (versionInfo != null) {
			text_versionNumber.setText("V"
					+ StringUtil.convertNull2EmptyStr(versionInfo
							.getVersionNumber()));
			text_creator.setText(StringUtil.convertNull2EmptyStr(versionInfo
					.getCreator()));
			text_modifier.setText(StringUtil.convertNull2EmptyStr(versionInfo
					.getModifier()));
			text_createdTime.setText(DateTimeUtil.date2String(versionInfo
					.getCreatedTime()));
			text_changedTime.setText(DateTimeUtil.date2String(versionInfo
					.getChangedTime()));
			// text_description.setText(StringUtil.convertNull2EmptyStr(versionInfo.getDescription()));
		}
	}

	/**
	 * Gets the version info.
	 *
	 * @author mqfdy
	 * @return the version info
	 * @Date 2018-09-03 09:00
	 */
	public VersionInfo getVersionInfo() {
		if (this.versionInfo == null) {
			this.versionInfo = new VersionInfo();
			this.versionInfo.setCreatedTime(new Date());
			this.versionInfo.setChangedTime(new Date());
			this.versionInfo.setVersionNumber("1");
			this.versionInfo.setCreator(System.getProperty("user.name"));
			this.versionInfo.setModifier(System.getProperty("user.name"));
		} else {
			this.versionInfo.setModifier(System.getProperty("user.name"));
			this.versionInfo.setChangedTime(new Date());
			this.versionInfo.setVersionNumber((StringUtil
					.string2Int(this.versionInfo.getVersionNumber()) + 1) + "");
		}

		// this.versionInfo.setDescription(text_description.getText());
		return this.versionInfo;
	}
}
