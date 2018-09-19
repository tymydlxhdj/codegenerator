package com.mqfdy.code.designer.editor;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.PrintAction;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.SaveAction;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.mqfdy.code.designer.editor.actions.AlignmentAction;
import com.mqfdy.code.designer.editor.actions.CopyNodeAction;
import com.mqfdy.code.designer.editor.actions.CutNodeAction;
import com.mqfdy.code.designer.editor.actions.DeleteAction;
import com.mqfdy.code.designer.editor.actions.DiagramAutoLayoutAction;
import com.mqfdy.code.designer.editor.actions.DiagramSelectAction;
import com.mqfdy.code.designer.editor.actions.FindModelAction;
import com.mqfdy.code.designer.editor.actions.FindObjectAction;
import com.mqfdy.code.designer.editor.actions.GenerateAction;
import com.mqfdy.code.designer.editor.actions.ImportEnumerationConfAction;
import com.mqfdy.code.designer.editor.actions.MatchHeightAction;
import com.mqfdy.code.designer.editor.actions.MatchWidthAction;
import com.mqfdy.code.designer.editor.actions.ModelValidateAction;
import com.mqfdy.code.designer.editor.actions.MoveDownNodeAction;
import com.mqfdy.code.designer.editor.actions.MoveLeftNodeAction;
import com.mqfdy.code.designer.editor.actions.MoveRightNodeAction;
import com.mqfdy.code.designer.editor.actions.MoveUpNodeAction;
import com.mqfdy.code.designer.editor.actions.PasteNodeAction;
import com.mqfdy.code.designer.editor.actions.ShareModelAction;
import com.mqfdy.code.designer.editor.actions.ZoomManagerAction;
import com.mqfdy.code.designer.editor.form.OmForm;
import com.mqfdy.code.designer.editor.listeners.EditorResourceChangeListener;
import com.mqfdy.code.designer.editor.listeners.ModelDiagramDndListener;
import com.mqfdy.code.designer.editor.listeners.ModelOutlineDropTargetListener;
import com.mqfdy.code.designer.editor.listeners.TreeResourceChangeListener;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.editor.part.extensions.PartFactory;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelListenerAdapter;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.utils.GenerateBuinessModelUtil;
import com.mqfdy.code.designer.views.modelresource.page.ObjectModelOutlinePage;
import com.mqfdy.code.designer.views.properties.BusinessPropertySheetPage;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.LinkAnnotation;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.resource.validator.ValiResult;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessModelDiagramEditor.
 *
 * @author mqfdy
 * @title:业务模型编辑器
 * @description:编辑器入口，当保存编辑器内容时把内容存入xx.bom文件中
 */
public class BusinessModelDiagramEditor extends AbstractGenericGEFEditor {

	/** The Constant ID. */
	public static final String ID = "com.mqfdy.code.designer.editor.BusinessModelDiagramEditor";
	
	/** The dia. */
	private Diagram dia;
	
	/** The business object model. */
	private BusinessObjectModel businessObjectModel;
	
	/** The is dirty. */
	// 编辑器是否被修改过
	private boolean isDirty = false;
	
	/** The palette. */
	private static PaletteRoot palette;
	
	/** The source file. */
	private IFile sourceFile;
	
	/** The resource change listener. */
	private IResourceChangeListener resourceChangeListener = new EditorResourceChangeListener(
			this);
	
	/** The business model manager. */
	private BusinessModelManager businessModelManager;
	
	/** The list. */
	private List<TreeResourceChangeListener> list = new ArrayList<TreeResourceChangeListener>();
	
	/** The last time stamp. */
	// 当编辑器打开的是bom文件时，记录文件的时间戳
	private long lastTimeStamp;
	
	/** The toolkit. */
	private FormToolkit toolkit;
	
	/** The form. */
	private OmForm form;
	
	/** The page. */
	private IWorkbenchPage page;

/** The property sheet page. */
//	private ModelResourceView modelResourceView;
	private PropertySheetPage propertySheetPage;
	
	/** The out line page. */
	private ObjectModelOutlinePage outLinePage;
	
	/** The viewer. */
	private GraphicalViewer viewer;
	
	/** The bu editor. */
	private BusinessModelDiagramEditor buEditor = this;
	
	/** The find result. */
	// 模型查询结果
	private List<AbstractModelElement> findResult;
	
	/** The vali result. */
	// 模型校验结果
	private List<ValiResult> valiResult;
	
	/** The business model editor. */
	// 多页编辑器
	private BusinessModelEditor businessModelEditor;
//	private boolean fHasBeenActivated = false;

	/**
 * Instantiates a new business model diagram editor.
 *
 * @param businessModelEditor
 *            the business model editor
 */
public BusinessModelDiagramEditor(BusinessModelEditor businessModelEditor) {
		super();
		this.businessModelEditor = businessModelEditor;
		
	
		
		CopyNodeAction copyAction = new CopyNodeAction(this);
		getActionRegistry().registerAction(copyAction);
		getSelectionActions().add(copyAction.getId());
//		AutoLayoutAction autoAction = new AutoLayoutAction(this);
//		getActionRegistry().registerAction(autoAction);
//		getSelectionActions().add(autoAction.getId());
		CutNodeAction cutAction = new CutNodeAction(this);
		getActionRegistry().registerAction(cutAction);
		getSelectionActions().add(cutAction.getId());
		PasteNodeAction pasteAction = new PasteNodeAction(this);
		getActionRegistry().registerAction(pasteAction);
		getSelectionActions().add(pasteAction.getId());

		FindModelAction findAction=new FindModelAction(this);
		getActionRegistry().registerAction(findAction);
		getSelectionActions().add(FindModelAction.FINDID);
		

		
		ShareModelAction shareModelAction = new ShareModelAction(this);
		getActionRegistry().registerAction(shareModelAction);
		getSelectionActions().add(ShareModelAction.STR);	

//新增导入枚举		
		ImportEnumerationConfAction importEnumerationConfAction = new ImportEnumerationConfAction(this);
		getActionRegistry().registerAction(importEnumerationConfAction);
		getSelectionActions().add(ImportEnumerationConfAction.ENUMER);	
		

		getActionRegistry().registerAction(new UndoRetargetAction());
		getActionRegistry().registerAction(new RedoRetargetAction());
		DeleteRetargetAction ac = new DeleteRetargetAction();
		ac.setText("删除");
		ac.setDescription("删除");
		getActionRegistry().registerAction(ac);
		IAction action = new AlignmentAction(this,PositionConstants.LEFT);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId()); 
		action = new AlignmentAction(this,PositionConstants.CENTER);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId());
		action = new AlignmentAction(this,PositionConstants.RIGHT);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId());
		action = new AlignmentAction(this,PositionConstants.TOP);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId());
		action = new AlignmentAction(this,PositionConstants.MIDDLE);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId());
		action = new AlignmentAction(this,PositionConstants.BOTTOM);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId());
		action = new MatchWidthAction(this);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId());
		action = new MatchHeightAction(this);
		getActionRegistry().registerAction(action); 
		getSelectionActions().add(action.getId());

		setEditDomain(new DefaultEditDomain(this));
		// 不限制用户撤销操作次数
		getCommandStack().setUndoLimit(-1);
		businessModelManager = new BusinessModelManager();
		businessModelManager.addBusinessModelListener(bmListener);
	}

	/** 模型变化的监听. */
	private final BusinessModelListenerAdapter bmListener = new BusinessModelListenerAdapter() {

		public void modelElementAdd(AbstractModelElement element) {
			// IEditorPart editor = BusinessModelEditorPlugin
			// .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			// ((MultiPageEditor)
			// editor).getBuEditor().getViewer().setContents(element);

			// if (editor instanceof MultiPageEditor) {
			buEditor.setDirty(true);
			if (!(element instanceof Association)
					&& !(element instanceof Inheritance) && !(element instanceof LinkAnnotation)) {
				if (element instanceof Diagram) {
					if (businessObjectModel.getDiagrams().size() == 1) {
						((Diagram) element).setDefault(true);
						buEditor.getViewer().setContents(element);
					}
				}
				return;
			}
			if (element instanceof Association || element instanceof LinkAnnotation
					|| element instanceof Inheritance) {
				Iterator<?> it = buEditor.getAllEditParts();
				
				List<NodeEditPart> partList = new ArrayList<NodeEditPart>();
				while (it.hasNext()) {
					Object e = ((Entry<?, ?>) it.next()).getValue();
					if (e instanceof NodeEditPart) {
						if (((NodeEditPart) e).getModel() instanceof DiagramElement) {
							// if(((DiagramElement)(((NodeEditPart)e).getModel())).getObjectId().equals(((Association)element).getClassA().getId())){
							// partList.add((NodeEditPart)e);
							// }
							// else
							// if(((DiagramElement)(((NodeEditPart)e).getModel())).getObjectId().equals(((Association)element).getClassB().getId())){
							// partList.add((NodeEditPart)e);
							// }
							partList.add((NodeEditPart) e);
						}

					}
				}
				for (NodeEditPart e : partList) {
					e.refresh();
				}
			}
			// }
		}

		public void modelElementDelete(AbstractModelElement element) {
			// IEditorPart editor = BusinessModelEditorPlugin
			// .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			// if (editor instanceof MultiPageEditor) {
			if (BusinessModelUtil.getView(ValiView.class) != null
					&& BusinessModelUtil.getView(ValiView.class).getFoPage() != null
					&& BusinessModelUtil.getView(ValiView.class).getFoPage()
							.getResult() != null) {
				BusinessModelUtil.getView(ValiView.class).getFoPage()
						.getResult().remove(element);
				BusinessModelUtil.getView(ValiView.class).getFoPage().refresh();
			}
			buEditor.setDirty(true);
			Iterator<?> it = buEditor.getAllEditParts();
			if (element instanceof Property
					|| element instanceof BusinessOperation) {
				while (it.hasNext()) {
					Object e = ((Entry<?, ?>) it.next()).getValue();
					if (e instanceof NodeEditPart) {
						((NodeEditPart) e).repaintFigure();
					}
				}
			} else if (!(element instanceof Association)
					&& !(element instanceof Inheritance)
					&& !(element instanceof LinkAnnotation)
					&& !(element instanceof Diagram)) {
				for (Diagram dia : businessModelManager
						.getBusinessObjectModel().getDiagrams()) {
					dia.getElements().remove(
							dia.getElementById(element.getId()));
				}
				while (it.hasNext()) {
					Object e = ((Entry<?, ?>) it.next()).getValue();
					if (e instanceof DiagramEditPart) {
						((DiagramEditPart) e).refresh();
						// ((DiagramEditPart)
						// e).firePropertyChange(NodeModelElement.CHILD_ADDED_PROP,
						// element, null);
						return;
					}
					if (e instanceof NodeEditPart) {
						if (((DiagramElement) ((NodeEditPart) e).getModel())
								.getObjectId().equals(element.getId())) {
							((Diagram) buEditor.getViewer().getContents()
									.getModel()).getElements().remove(
									((NodeEditPart) e).getModel());
						}
					}
				}

			} else if (element instanceof Diagram) {
				if (buEditor.getViewer().getContents().getModel() == element) {
					getCommandStack().flush();
					if (businessObjectModel.getDiagrams().size() > 0) {
						buEditor.getViewer().setContents(
								businessObjectModel.getDiagrams().get(0));
						businessObjectModel.getDiagrams().get(0).setDefault(true);
					} else {
						if (buEditor != null) {
							
							buEditor.getViewer().setContents(null);
							
						}
					}
				}
			} else {
				List<NodeEditPart> partList = new ArrayList<NodeEditPart>();
				for (Diagram dia : businessModelManager
						.getBusinessObjectModel().getDiagrams()) {
					dia.getElements().remove(
							dia.getElementById(element.getId()));
				}
				while (it.hasNext()) {
					Object e = ((Entry<?, ?>) it.next()).getValue();
					if (e instanceof NodeEditPart) {
						// if(((NodeEditPart)e).getModel() instanceof
						// DiagramElement){
						// ((DiagramElement)(((NodeEditPart)e).getModel())).getObjectId().equals(anObject);
						// }
						partList.add((NodeEditPart) e);
					}
				}
				for (NodeEditPart e : partList) {
					e.refresh();
				}
			}
			// }
		}

		public void modelSave(AbstractModelElement element) {
			buEditor.saveToFile();
		}

		public void modelElementUpdate(AbstractModelElement element) {
			// //区分关联关系和业务类
			if (BusinessModelUtil.getView(ValiView.class) != null
					&& BusinessModelUtil.getView(ValiView.class).getFoPage() != null
					&& BusinessModelUtil.getView(ValiView.class).getFoPage()
							.getResult() != null) {
				BusinessModelUtil.getView(ValiView.class).getFoPage().refresh();
			}
			buEditor.setDirty(true);
			Iterator<?> it = buEditor.getAllEditParts();
			if (!(element instanceof Association)
					&& !(element instanceof Inheritance)) {
				while (it.hasNext()) {
					Object e = ((Entry<?, ?>) it.next()).getValue();
					if (e instanceof NodeEditPart) {
						((NodeEditPart) e).repaintFigure();
					}
				}
			} else {
				List<NodeEditPart> partList = new ArrayList<NodeEditPart>();
				while (it.hasNext()) {
					Object e = ((Entry<?, ?>) it.next()).getValue();
					if (e instanceof OmConnectionEditPart) {
						((OmConnectionEditPart) e).repaintFigure();
					}
					if (e instanceof NodeEditPart) {

						// if(((NodeEditPart)e).getModel() instanceof
						// DiagramElement){
						// ((DiagramElement)(((NodeEditPart)e).getModel())).getObjectId().equals(anObject);
						// }

						partList.add((NodeEditPart) e);
					}
				}
				for (NodeEditPart e : partList) {
					e.refresh();
				}
			}
			// while(it.hasNext()){
			// Object o = it.next();
			// Object e = ((Entry<?, ?>) o).getValue();
			// if(e instanceof NodeEditPart){
			// ((NodeEditPart)e).repaintFigure();
			// }
			// if(e instanceof ConnectionEditPart){
			// ((ConnectionEditPart)e).repaintFigure();
			// }
			// }
			// }
		}
	};

	/**
	 * Command stack changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	/**
	 * 为编辑器定制调色板布局.
	 *
	 * @author mqfdy
	 * @return the palette preferences
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return DiagramEditorPaletteFactory.createPalettePreferences();
	}

	/**
	 * 定制编辑器调色板的内容.
	 *
	 * @author mqfdy
	 * @return the palette root
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected PaletteRoot getPaletteRoot() {
		if (palette == null) {
			palette = DiagramEditorPaletteFactory.createPalette();
		}
		return palette;
	}

	/**
	 * 设置编辑器的SWT部分的Control.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		toolkit = new FormToolkit(parent.getDisplay());
//		form = toolkit.createForm(parent);
		form = new OmForm(parent, SWT.LEFT_TO_RIGHT);
		form.setBackground(toolkit.getColors().getBackground());
		form.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		form.setFont(JFaceResources.getHeaderFont());
	    form.setText("业务模型设计器");
		Display display = parent.getDisplay();// Display.getCurrent();
		form.setFont(display.getSystemFont());
		form.getBody().setLayout(new FillLayout());
		Composite composite = toolkit.createComposite(form.getBody());
		composite.setLayout(new FillLayout());
		super.createPartControl(composite);
	}

	/**
	 * 重写createActions方法 使用自定义的DeleteAction.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void createActions() {
//		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UndoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new RedoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new SelectAllAction(this);
		registry.registerAction(action);

		action = new DeleteAction((IWorkbenchPart) this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new SaveAction(this);
		registry.registerAction(action);
		getPropertyActions().add(action.getId());
		
		

		registry.registerAction(new PrintAction(this));
	}

	/**
	 * Gets the adapter.
	 *
	 * @author mqfdy
	 * @param type
	 *            the type
	 * @return the adapter
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) {
		if (type == ZoomManager.class)
			return getGraphicalViewer().getProperty(
					ZoomManager.class.toString());
		if (type == IPropertySheetPage.class) {
			if (propertySheetPage == null)
				propertySheetPage = new BusinessPropertySheetPage(
						businessModelManager);
			return propertySheetPage;
		}
		if (type == IContentOutlinePage.class) {
			if (outLinePage == null)
				outLinePage = new ObjectModelOutlinePage(
						this);
			this.addListenerObject(outLinePage);
			return outLinePage;
		}

		return super.getAdapter(type);
	}

	/**
	 * 
	 */
	@Override
	// 初始化EditPartViewer
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		viewer = getGraphicalViewer();
		// 鼠标滚轮+CTRL放大缩小
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL),
				MouseWheelZoomHandler.SINGLETON);
		// viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(0),
		// MouseWheelZoomHandler.SINGLETON);
		// 添加网格显示
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, new Boolean(true));
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, true);
		// RootEditPart是一种特殊的EditPart，它和你的模型没有任何关系，它的作用是把EditPartViewer和contents
		// （应用程序的最上层EditPart，一般代表一块画布）联系起来，可以把它想成是contents的容器
		OmScalableFreeformRootEditPart root = new OmScalableFreeformRootEditPart();
		getGraphicalViewer().setRootEditPart(root);
		getGraphicalViewer().setEditPartFactory(new PartFactory());
		IAction action;
		action = new ZoomInAction(root.getZoomManager());
		action.setToolTipText("放大");
		getActionRegistry().registerAction(action);
		getSite().getKeyBindingService().registerAction(action);
		action = new ZoomOutAction(root.getZoomManager());
		action.setToolTipText("缩小");
		getActionRegistry().registerAction(action);
		getSite().getKeyBindingService().registerAction(action);
		// --创建通用按钮---
		IToolBarManager toolBarManager = form.getToolBarManager();
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
		toolBarManager.add(getAction(ActionFactory.COPY.getId()));
		toolBarManager.add(getAction(ActionFactory.CUT.getId()));
		toolBarManager.add(getAction(ActionFactory.PASTE.getId()));

		/*toolBarManager.add(getAction(FindModelAction.FINDID));

		toolBarManager.add(getAction(ShareModelAction.STR));
		//新增导入枚举
		toolBarManager.add(getAction(ImportEnumerationConfAction.ENUMER));*/

		toolBarManager.add(new Separator());  
		//增加生成代码按钮
		toolBarManager.add(new GenerateAction(businessModelManager));
		
		toolBarManager.add(new FindObjectAction(businessModelManager));
		toolBarManager.add(new ModelValidateAction(businessModelManager));
		//toolBarManager.add(new RegistAuthAction(businessModelManager));
		
		toolBarManager.add(new Separator());  
	    
//		toolBarManager.add(getAction(DiagramEditPart.PROP_LAYOUT));
		// 匹配宽度   
	    toolBarManager.add(getAction(GEFActionConstants.MATCH_HEIGHT));  
	    toolBarManager.add(getAction(GEFActionConstants.MATCH_WIDTH));  
	    toolBarManager.add(new Separator());  
	    // 水平方向对齐按钮   
	    toolBarManager.add(getAction(GEFActionConstants.ALIGN_LEFT));  
	    toolBarManager.add(getAction(GEFActionConstants.ALIGN_CENTER));  
	    toolBarManager.add(getAction(GEFActionConstants.ALIGN_RIGHT));  
	    toolBarManager.add(new Separator());  
	    // 垂直方向对齐按钮   
	    toolBarManager.add(getAction(GEFActionConstants.ALIGN_TOP));  
	    toolBarManager.add(getAction(GEFActionConstants.ALIGN_MIDDLE));  
	    toolBarManager.add(getAction(GEFActionConstants.ALIGN_BOTTOM));  
	    // 加上分割条   
	    toolBarManager.add(new Separator());  
		// 放大缩小
//		form.getToolBarManager().add(getAction(GEFActionConstants.ZOOM_IN));
//		form.getToolBarManager().add(getAction(GEFActionConstants.ZOOM_OUT));
//		form.getToolBarManager().add(new ZoomComboContributionItem(this.getSite().getPage()));
//		form.getToolBarManager().add(new DiagramComboContributionItem(this.getSite().getPage(),this));
	    Action actionl = new DiagramAutoLayoutAction(this);
		form.getToolBarManager().add(actionl);
		Action dsAction = new DiagramSelectAction(this);
		form.getToolBarManager().add(dsAction);
		Action zoomAction = new ZoomManagerAction(this);
		form.getToolBarManager().add(zoomAction);
		form.updateToolBar();
//		toolkit.decorateFormHeading(form);
        FormColors colors = toolkit.getColors();
        Color top = colors.getColor(IFormColors.H_GRADIENT_END);
		Color bot = colors .getColor(IFormColors.H_GRADIENT_START);
		form.setTextBackground(new Color[] { top, bot }, new int[] { 100 },
				true);
		form.setHeadColor(IFormColors.H_BOTTOM_KEYLINE1, colors
				.getColor(IFormColors.H_BOTTOM_KEYLINE1));
		form.setHeadColor(IFormColors.H_BOTTOM_KEYLINE2, colors
				.getColor(IFormColors.H_BOTTOM_KEYLINE2));
		form.setHeadColor(IFormColors.H_HOVER_LIGHT, colors
				.getColor(IFormColors.H_HOVER_LIGHT));
		form.setHeadColor(IFormColors.H_HOVER_FULL, colors
				.getColor(IFormColors.H_HOVER_FULL));
		form.setHeadColor(IFormColors.TB_TOGGLE, colors
				.getColor(IFormColors.TB_TOGGLE));
		form.setHeadColor(IFormColors.TB_TOGGLE_HOVER, colors
				.getColor(IFormColors.TB_TOGGLE_HOVER));
		form.setSeparatorVisible(true);
		IWorkbenchWindow dw = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();

		page = dw.getActivePage();
		// if(businessModelManager.getBusinessObjectModel()==null){
		// if (page != null)
		// page.closeEditor(this, false);
		// return;
		// }
		try {
			if (page != null) {
				// 打开视图
				page.showView(IPageLayout.ID_OUTLINE);
				page.showView(IPageLayout.ID_PROP_SHEET);
			}
		} catch (PartInitException e) {
			Logger.log(e);
		}
		if (dia == null && viewer != null) {
			viewer.setContents(null);
		} else {
			viewer.setContents(dia);
			dia.setDefault(true);
		}

		ZoomManager zoomMgr = (ZoomManager) viewer
				.getProperty(ZoomManager.class.toString());
		if (zoomMgr != null && dia != null && dia.getDefaultStyle() != null) {
			zoomMgr.setZoom(((double) dia.getDefaultStyle().getZoomScale()) / 100);
		}
		// 加入从选项板直接拖动组件到编辑区的功能
		getGraphicalViewer().addDropTargetListener(
				new ModelDiagramDndListener(getGraphicalViewer()));
		getGraphicalViewer().addDropTargetListener(
				new ModelOutlineDropTargetListener(getGraphicalViewer()));

	}

	/**
	 * 定制EditPartViewer
	 * 在根节点接受到内容之前配置EditPartViewer,在这个方法中需要配置RootEditPart.此处我们把删除的键盘操作和
	 * 编辑器的右键菜单加入其中
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();
		PartFactory partFactory = new PartFactory();
		viewer.setEditPartFactory(partFactory);
		// 支持键盘删除操作
		BusinessModelGraphicalViewerKeyHandler keyHandler = new BusinessModelGraphicalViewerKeyHandler(
				viewer);
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(ActionFactory.DELETE.getId()));// 删除
		keyHandler.put(KeyStroke.getPressed((char) 0x06, 102, SWT.CTRL),
				new FindObjectAction(businessModelManager));// 查找

		keyHandler.put(KeyStroke.getReleased((char) 0x03, 99, SWT.CTRL),
				new CopyNodeAction(this)); // 复制
		keyHandler.put(KeyStroke.getReleased((char) 0x24, 119, SWT.CTRL),
				new CutNodeAction(this)); // 剪切
		keyHandler.put(KeyStroke.getReleased((char) 0x17, 118, SWT.CTRL),
				new PasteNodeAction(this)); // 黏贴
		
		keyHandler.remove(KeyStroke.getPressed(SWT.ARROW_UP, 0));
		
		// 支持键盘方向键移动图形
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, 0),
				new MoveUpNodeAction(this)); //
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, 0),
				new MoveDownNodeAction(this));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, 0),
				new MoveLeftNodeAction(this)); //
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, 0),
				new MoveRightNodeAction(this));
		viewer.setKeyHandler(keyHandler);
		// 配置编辑器的右键菜单
		ContextMenuProvider cmProvider = new DiagramEditorContextMenuProvider(
				viewer, getActionRegistry());
		viewer.setContextMenu(cmProvider);
	}

	/**
	 * 为透视图编辑器创建调色板.
	 *
	 * @author mqfdy
	 * @return the palette viewer provider
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			@Override
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(
						viewer));
			}
		};
	}

	/**
	 * 透视图编辑器的输入.
	 *
	 * @author mqfdy
	 * @param input
	 *            the new input
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void setInput(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			// 第一种情况：新建一个bom文件
			// 第二种情况：打开一个已有的透视图om文件
			sourceFile = ((IFileEditorInput) input).getFile();
			lastTimeStamp = sourceFile.getLocalTimeStamp();
			setPartName(sourceFile.getName());// 把om文件名称赋值给编辑器的显示标签
			String sourcePath = sourceFile.getLocation().toString();
			businessObjectModel = businessModelManager.getBusinessObjectModel(
					sourcePath, this);
			
			if(businessObjectModel == null){
				try {
					sourcePath = sourceFile.getLocationURI().toURL().getPath();
					businessObjectModel = businessModelManager.getBusinessObjectModel(
							sourcePath, this);
					if(businessObjectModel == null){
						GenerateBuinessModelUtil.generateFileContent(sourcePath);
						businessObjectModel = businessModelManager.getBusinessObjectModel(
								sourcePath, this);
					}
					
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			boolean isHasErrorRefObj = BusinessModelUtil.isHasReferenceObject(businessModelEditor,businessObjectModel,sourcePath);
			if(!isHasErrorRefObj){
				BusinessModelUtil.assembReferenceObject(businessObjectModel,sourcePath);
			}
			dia = null;
			dia = businessModelManager.getDefaultDiagram();
			if (dia == null) {
				if (businessObjectModel.getDiagrams() != null
						&& businessObjectModel.getDiagrams().size() > 0)
					dia = businessObjectModel.getDiagrams().get(0);
			}
		}
		super.setInput(input);
		// for(DiagramElement ele:dia.getElements()){
		// AbstractModelElement child =
		// BusinessModelManager.getManager().queryObjectById(ele.getObjectId());
		// diagram.addChild(child);
		// }
		// if(null!=diagram&&diagram.isModified()){
		// this.setDirty(true);
		// MessageDialog.openInformation(getSite().getShell(), "提示",
		// diagram.getTipInfor());
		// }
		// 为工作空间资源管理器添加资源监听器
		// ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	/**
	 * Adds the listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addListener(TreeResourceChangeListener listener) {
		list.add(listener);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
	}

	/**
	 * Removes the listener.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void removeListener() {
		for (TreeResourceChangeListener listener : list) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					listener);
		}
	}

	/**
	 * 
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(
				resourceChangeListener);
		if(valiResult != null)
			valiResult.clear();
		if(findResult != null)
			findResult.clear();
		ValiView view = BusinessModelUtil.getView(ValiView.class);
		if(view != null){
			view.getValiPage().refresh();
			view.getFoPage().refresh();
		}
		super.dispose();
	}

	/**
	 * 
	 */
	@Override
	public void doSaveAs() {
		saveToFile();

	}

	/**
	 * 当保存透视图内容时把内容存入xx.om文件中
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void saveToFile() {
		if (sourceFile == null) {
			SaveAsDialog dialog = new SaveAsDialog(getSite().getShell());
			// 赋默认值，不以.md结尾不能打开文件编辑
			dialog.setOriginalName("*.bom");
			if (dialog.open() != Dialog.OK) {
				return;
			}
			IPath path = dialog.getResult();
			sourceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		}
		ZoomManager zoomMgr = (ZoomManager) viewer
				.getProperty(ZoomManager.class.toString());
		if (zoomMgr != null && dia != null && dia.getDefaultStyle() != null) {
			dia.getDefaultStyle().setZoomScale((int) (zoomMgr.getZoom() * 100));
		}
		BusinessModelManager.saveModel(businessObjectModel, sourceFile
				.getLocation().toOSString());

		try {
			sourceFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			Logger.log(e);
		}//
		setDirty(false);
		// 在CommandStack中标记保存命名的堆栈位置
		getCommandStack().markSaveLocation();
	}

	/**
	 * Do save.
	 *
	 * @author mqfdy
	 * @param monitor
	 *            the monitor
	 * @Date 2018-09-03 09:00
	 */
	// 保存图形
	@Override
	public void doSave(IProgressMonitor monitor) {
		saveToFile();
	}
	
	/**
	 * Do save.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	// 保存图形
	public void doSave() {
		saveToFile();
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isDirty() {
		return isDirty || getCommandStack().isDirty();
	}

	/**
	 * 改变编辑器的修改状态.
	 *
	 * @author mqfdy
	 * @param dirty
	 *            the new dirty
	 * @Date 2018-09-03 09:00
	 */
	public void setDirty(boolean dirty) {
		if (isDirty != dirty) {
			isDirty = dirty;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	/**
	 * Gets the common key handler.
	 *
	 * @author mqfdy
	 * @return the common key handler
	 * @Date 2018-09-03 09:00
	 */
	protected KeyHandler getCommonKeyHandler() {
		return null;
	}

	/**
	 * Gets the last time stamp.
	 *
	 * @author mqfdy
	 * @return the last time stamp
	 * @Date 2018-09-03 09:00
	 */
	public long getLastTimeStamp() {
		return lastTimeStamp;
	}

	/**
	 * Sets the last time stamp.
	 *
	 * @author mqfdy
	 * @param lastTimeStamp
	 *            the new last time stamp
	 * @Date 2018-09-03 09:00
	 */
	public void setLastTimeStamp(long lastTimeStamp) {
		this.lastTimeStamp = lastTimeStamp;
	}

	/**
	 * 获取注册的action.
	 *
	 * @author mqfdy
	 * @param actionID
	 *            the action ID
	 * @return the action
	 * @Date 2018-09-03 09:00
	 */
	public IAction getAction(String actionID) {
		Assert.isNotNull(actionID);
		IAction action = (IAction) getActionRegistry().getAction(actionID);
		return action;
	}

	/**
	 * Selection changed.
	 *
	 * @author mqfdy
	 * @param part
	 *            the part
	 * @param selection
	 *            the selection
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// 解决多页编辑器中的一系列RetargetAction(cut,delete,paste等)都失效，
		// 而在单页编辑器(EditorPart)中可以正常使用的问题
		updateActions(getSelectionActions());
	}

	/**
	 * Gets the business model manager.
	 *
	 * @author mqfdy
	 * @return the business model manager
	 * @Date 2018-09-03 09:00
	 */
	public BusinessModelManager getBusinessModelManager() {
		return businessModelManager;
	}

	/**
	 * Gets the all edit parts.
	 *
	 * @author mqfdy
	 * @return the all edit parts
	 * @Date 2018-09-03 09:00
	 */
	public Iterator<?> getAllEditParts() {
		
		return getViewer().getEditPartRegistry().entrySet().iterator();
	}
	
	/**
	 * Gets the viewer.
	 *
	 * @author mqfdy
	 * @return the viewer
	 * @Date 2018-09-03 09:00
	 */
	public GraphicalViewer getViewer() {
		return viewer;
	}

	/**
	 * Gets the command stacks.
	 *
	 * @author mqfdy
	 * @return the command stacks
	 * @Date 2018-09-03 09:00
	 */
	public CommandStack getCommandStacks() {
		return getCommandStack();
	}

	/**
	 * Sets the dia.
	 *
	 * @author mqfdy
	 * @param dia
	 *            the new dia
	 * @Date 2018-09-03 09:00
	 */
	public void setDia(Diagram dia) {
		this.dia = dia;
	}

	/**
	 * Gets the dia.
	 *
	 * @author mqfdy
	 * @return the dia
	 * @Date 2018-09-03 09:00
	 */
	public Diagram getDia() {
		return dia;
	}

	/**
	 * Gets the find result.
	 *
	 * @author mqfdy
	 * @return the find result
	 * @Date 2018-09-03 09:00
	 */
	public List<AbstractModelElement> getFindResult() {
		return findResult;
	}

	/**
	 * Sets the find result.
	 *
	 * @author mqfdy
	 * @param findResult
	 *            the new find result
	 * @Date 2018-09-03 09:00
	 */
	public void setFindResult(List<AbstractModelElement> findResult) {
		this.findResult = findResult;
	}

	/**
	 * Gets the vali result.
	 *
	 * @author mqfdy
	 * @return the vali result
	 * @Date 2018-09-03 09:00
	 */
	public List<ValiResult> getValiResult() {
		return valiResult;
	}

	/**
	 * Sets the vali result.
	 *
	 * @author mqfdy
	 * @param valiResult
	 *            the new vali result
	 * @Date 2018-09-03 09:00
	 */
	public void setValiResult(List<ValiResult> valiResult) {
		this.valiResult = valiResult;
	}

	/**
	 * 
	 */
	@Override
	public void setFocus() {
//		if(BusinessModelUtil.getView(ValiView.class) != null){
//			BusinessModelUtil.getView(ValiView.class).getValiPage().setData(valiResult);
//			BusinessModelUtil.getView(ValiView.class).getFoPage().setData(findResult);
//		}
		super.setFocus();
	}
	
}
