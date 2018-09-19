package com.mqfdy.code.shareModel.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.actions.ShareModelActionHttpclient;
import com.mqfdy.code.designer.preferences.ModelPreferencePage;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeContentProvider;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeLabelProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ShareModelDialog.
 *
 * @author mqfdy
 */
public class ShareModelDialog extends TitleAreaDialog {
	
	/** The store. */
	private static IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
			.getPreferenceStore();
	
	/** The url. */
	public static String URL="http://"+ store.getString(ModelPreferencePage.SHAREMODELIP)+":"+ store.getString(ModelPreferencePage.SHAREMODELPORT)+"/sguap-mdd";
	
	/** The tree view. */
	private TreeViewer treeView;
	
	/** The tree. */
	private Tree tree;
	
	/** The tree scroll. */
	private ScrolledComposite treeScroll;
	
	/** The container. */
	public Composite container;
	
	/** The text. */
	public  Text text;
	
	/** The node tree group. */
	public  Group nodeTreeGroup;
	
	/** The node text group. */
	private Group nodeTextGroup;
	
	/** The label. */
	public  Label label;
	
	/** The xml. */
	public  String xml;
	
	/** The type. */
	public  String type;
	
	/** The model name. */
	public  String modelName;
	
	/**
	 * Instantiates a new share model dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public ShareModelDialog(Shell parentShell) {
		super(parentShell);
		
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
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area,SWT.NONE);
		setTitle("上传");
		GridLayout layout = new GridLayout(1,false);
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		createTreeDialog();
		return area;
	}
	
	/**
	 * Creates the tree dialog.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createTreeDialog() {
		
		GridData gd=new GridData(GridData.FILL_BOTH);
		nodeTreeGroup = new Group(container,SWT.NONE);
		nodeTreeGroup.setLayout(new GridLayout(1,false));
		nodeTreeGroup.setLayoutData(gd);
		nodeTreeGroup.setText("分类选择");

		treeView=new TreeViewer(nodeTreeGroup,SWT.BORDER);
		treeView.setLabelProvider(new ModelMessageTreeLabelProvider());
		treeView.setContentProvider(new ModelMessageTreeContentProvider());	
		
		tree=treeView.getTree();
		treeScroll=new ScrolledComposite(tree,SWT.BORDER);
		treeScroll.setContent(tree);		
		tree.setLayoutData(gd);
		
		 List<JSONObject> list=treeData();
		if(list.size()==0){
			
		}else{
			treeView.setInput(list);	
			treeView.expandToLevel(3);
		}
		
		gd=new GridData(GridData.FILL_HORIZONTAL);
	
		nodeTextGroup = new Group(container,SWT.NONE);
		nodeTextGroup.setLayout(new GridLayout(1,false));
		nodeTextGroup.setLayoutData(gd);
		nodeTextGroup.setText("模型描述");

		gd=new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint=100;
		text = new Text(nodeTextGroup, SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.WRAP);
		text.setLayoutData(gd);
		
	}
	
	/**
	 * 获取树节点信息.
	 *
	 * @author mqfdy
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public  List<JSONObject>  treeData(){
		List<JSONObject> list=new ArrayList<JSONObject>();
		String url = URL+"/CreateTreeServlet";
		
		JSONArray jsonArr;
		String jsonStr="";
		try {
			jsonStr=ShareModelActionHttpclient.getDoPostResponseDataByURL(url, null ,  "utf-8" ,  true);
			if(ShareModelActionHttpclient.FAILCONNECTION.equals(jsonStr)){
				setMessage(jsonStr, IMessageProvider.INFORMATION);
				return list;
			}
			jsonArr = new JSONArray(jsonStr);
			
			for(int i=0, len=jsonArr.length(); i<len; i++) {
				list.add(jsonArr.optJSONObject(i));				
			}
		
		} catch (Exception e) {
			setMessage(jsonStr, IMessageProvider.INFORMATION);
		}
		return list;
	}
	
	
	
	/**
	 * 
	 */
	protected void okPressed() {
			String url = URL+"/UploadServlet";
			if(tree.getSelection().length>0){
				JSONObject obj=(JSONObject) tree.getSelection()[0].getData();
			try {
				String id=obj.getString("id");
				String desc=text.getText();
				HttpClient httpClient = new HttpClient();
				PostMethod postMethod = new PostMethod(url);
			    //om文件
				postMethod.addParameter("xml",  xml);
				//对应数据库类型
				postMethod.addParameter("xmlType", type);
				//对应om文件disPlayName
				postMethod.addParameter("modelName",modelName);
				//tree的id值
				postMethod.addParameter("treeItem",id);
				//文件描述信息
				postMethod.addParameter("desc",desc);
				
				//文件描述信息
				postMethod.addParameter("person",System.getProperty("user.name"));
				//om文件中文乱码设置
				postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");

				httpClient.executeMethod(postMethod);
				
			postMethod.releaseConnection();
			super.okPressed();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * Gets the tree.
	 *
	 * @author mqfdy
	 * @return the tree
	 * @Date 2018-09-03 09:00
	 */
	public Tree getTree() {
		return tree;
	}

	/**
	 * Sets the tree.
	 *
	 * @author mqfdy
	 * @param tree
	 *            the new tree
	 * @Date 2018-09-03 09:00
	 */
	public void setTree(Tree tree) {
		this.tree = tree;
	}

	/**
	 * Gets the xml.
	 *
	 * @author mqfdy
	 * @return the xml
	 * @Date 2018-09-03 09:00
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * Sets the xml.
	 *
	 * @author mqfdy
	 * @param xml
	 *            the new xml
	 * @Date 2018-09-03 09:00
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

	/**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @author mqfdy
	 * @param type
	 *            the new type
	 * @Date 2018-09-03 09:00
	 */
	public void setType(String type) {
		this.type = type;
	}
	 
 	/**
		 * Gets the model name.
		 *
		 * @author mqfdy
		 * @return the model name
		 * @Date 2018-09-03 09:00
		 */
 	public String getModelName() {
			return modelName;
	}

	/**
	 * Sets the model name.
	 *
	 * @author mqfdy
	 * @param modelName
	 *            the new model name
	 * @Date 2018-09-03 09:00
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 600);
	}
	

	
	/**
	 * @return
	 */
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX |SWT.MIN ;
	}

	/**
	 * shell标题.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("共享模型");		
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean isHelpAvailable() {
		return false;
	}

}
