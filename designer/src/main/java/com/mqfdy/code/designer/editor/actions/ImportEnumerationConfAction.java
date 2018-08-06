package com.mqfdy.code.designer.editor.actions;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.extensions.NodeModelElement;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.importEnum.dialogs.ImportEnumDialog;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.VersionInfo;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;

public class ImportEnumerationConfAction extends SelectionAction {

	public static final String ENUMER = "enumerId";
	private Enumeration newNode;
	
	private AbstractModelElement container;
	private IEditorPart iEditorPart;
	private EditPart editPart;
	
	public ImportEnumerationConfAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
		this.newNode = new Enumeration();
	}

	protected void init() {
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setText("导入枚举");
		setDescription("导入枚举");
		setId(ENUMER);
		setHoverImageDescriptor(ImageManager.getInstance().getImageDescriptor(ImageKeys.IMG_RESOURCE_IMPORTEUNMERATION));
		setImageDescriptor(ImageManager.getInstance().getImageDescriptor(ImageKeys.IMG_RESOURCE_IMPORTEUNMERATION));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
		setEnabled(true);
	}


	@Override
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		IWorkbenchWindow workbenchWindow=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		iEditorPart= (workbenchWindow.getActivePage().getActiveEditor());
		Iterator<?> it =((BusinessModelEditor) iEditorPart).getBuEditor().getAllEditParts();
		while (it.hasNext()) {
			Object e = ((Entry<?, ?>) it.next()).getValue();
			if (e instanceof DiagramEditPart) {
				this.container = (AbstractModelElement) ((DiagramEditPart) e).getModel();
				this.editPart = (EditPart) e;
			}
		}
		
		newNode = new Enumeration();
		((Enumeration) newNode).setBelongPackage((ModelPackage) (container.getParent()));
		((Enumeration) newNode).setVersionInfo(VersionInfo.getVersionInfo());
		((Enumeration) newNode).setStereotype(IModelElement.STEREOTYPE_CUSTOM);

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ImportEnumDialog dialog = new ImportEnumDialog(shell,
				((Enumeration)newNode).getBelongPackage(), (Enumeration)newNode);
		int i = dialog.open();
		if (i == IDialogConstants.OK_ID) {
			List<Enumeration> enumerationList = dialog.getEnumBasicInfoEnumPage().getEnumerationList();
			int point= 0;
			int num = getRandom(1, 40);
			for(Enumeration newNode:enumerationList){
				point = point+25;
				addEnum(newNode,point,num);
			}
		}
	}


	/**
	 * 将Enumeration用图形的方式显示到编辑器
	 * @param newNode Enumeration
	 * @param point 偏移量大小
	 * @param num 指定范围的随机数
	 */
	public void addEnum(Enumeration newNode,int point,int num) {
		DiagramElement ele = new  DiagramElement();
		ele.setObjectId(newNode.getId());
		ElementStyle st = new ElementStyle();
		Rectangle bounds = new Rectangle();
		//Request request =new Request();
		st.setHeight(200);
		st.setWidth(160);
		st.setPositionX(70+num+point);
		st.setPositionY(20+num+point);
		if (bounds.height == -1){
			st.setHeight(200);
		}
		if (bounds.width == -1){
			st.setWidth(160);
		}
		ele.setStyle(st);
		ele.setBelongDiagram((Diagram) container);
		((Diagram) container).addElement(ele);

		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_ADD, newNode);
		BusinessModelUtil.getEditorBusinessModelManager()
				.businessObjectModelChanged(bcAddevent);
		if (editPart instanceof NodeEditPart)
			((NodeEditPart) editPart).firePropertyChange(
					NodeModelElement.CHILD_ADDED_PROP, null, newNode);
		if (editPart instanceof DiagramEditPart)
			((DiagramEditPart) editPart).firePropertyChange(
					NodeModelElement.CHILD_ADDED_PROP, null, newNode);
	}
	/**
	 * 获取指定范围的随机数
	 * @param begin 开始数值
	 * @param end   结束数值
	 * @return
	 */
	public int getRandom(int min,int max){
		SecureRandom random = new SecureRandom();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
}
