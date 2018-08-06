package com.mqfdy.code.designer.editor.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.commands.NodesCreateFromOutlineCommand;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.graph.Diagram;

/**
 * 从模型资源管理器树上拖动树节点到图形编辑器
 * 
 * @author mqfdy
 * 
 */
@SuppressWarnings("deprecation")
public class ModelOutlineDropTargetListener extends
		AbstractTransferDropTargetListener {

	// private final static int INTERVAL = 50;

	private static LocalSelectionTransfer transfer = LocalSelectionTransfer
			.getInstance();

	private List<AbstractModelElement> tableList = new ArrayList<AbstractModelElement>();

	public ModelOutlineDropTargetListener(EditPartViewer viewer, Transfer xfer) {
		super(viewer, xfer);
		// TODO Auto-generated constructor stub
	}

	public ModelOutlineDropTargetListener(EditPartViewer viewer) {
		super(viewer, transfer);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 封装所选对象
	 * 
	 * @author ZHANGHE
	 */
	public class BusinessElementFactory implements CreationFactory {

		private Object table;

		public BusinessElementFactory(Object table) {
			this.table = table;
		}

		public Object getNewObject() {
			return table;
		}

		public Object getObjectType() {
			return table.getClass();
		}
	}

	protected Request createTargetRequest() { // 创建请求
		CreateRequest request = new CreateRequest();

		if (transfer.getSelection() instanceof TreeSelection) {
			tableList.clear();
			TreeSelection selection = (TreeSelection) transfer.getSelection();
			// List<AbstractModelElement> tableList = new
			// ArrayList<AbstractModelElement>();

			for (Iterator<Object> it = selection.iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof AbstractModelElement) {
					tableList.add((AbstractModelElement) obj);// 保存进行拖拽处理的表对象
				}
			}
			// Sets the factory to be used when creating the new object.
			request.setFactory(new BusinessElementFactory(tableList));// 封装数据库表到factory中
			return tableList.size() > 0 ? request : null;
		}
		return null;
	}

	protected CreationFactory getFactory(Object template) {
		if (template instanceof CreationFactory)
			return ((CreationFactory) template);
		else
			return null;
	}

	@Override
	protected void updateTargetRequest() {// 创建请求，并设置坐标
		if (getTargetRequest() == null)
			return;
		((CreateRequest) getTargetRequest()).setLocation(getDropLocation());// 创建请求，并设置坐标
	}

	@Override
	protected void handleDragOver() {
		getCurrentEvent().detail = DND.DROP_COPY;
		super.handleDragOver();// 调用updateTargetRequest()
	}

	/**
	 * Updates the target Request and target EditPart, and performs the drop. By
	 * default, the drop is performed by asking the target EditPart for a
	 * Command using the target Request. This Command is then executed on the
	 * CommandStack.
	 */
	protected void handleDrop() {// 拖拽落地

		updateTargetRequest();// 创建请求，并设置坐标
		updateTargetEditPart();
		EditPart editPart = getTargetEditPart();// 取得当前EditPart
		CreateRequest createRequest = (CreateRequest) getTargetRequest();// 获取已有请求
		if (editPart != null && createRequest != null
				&& editPart instanceof DiagramEditPart) {
			Object obj = createRequest.getNewObject();// 取出封装的数据源表
			if (obj != null && obj instanceof ArrayList<?>) {
				executeCommand(editPart, createRequest, tableList);// 执行命令
			}
		} else
			getCurrentEvent().detail = DND.DROP_COPY;

	}

	/**
	 * 
	 * @param editPart
	 * @param createRequest
	 * @param tableElementList
	 * @param nodesList
	 */
	private void executeCommand(EditPart editPart, CreateRequest createRequest,
			List<AbstractModelElement> nodesList) {
		Diagram diagram = (Diagram) editPart.getModel();
		IEditorPart editor = BusinessModelEditorPlugin
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor instanceof BusinessModelEditor) {
			((BusinessModelEditor) editor).getBuEditor().setDirty(true);// 设置编辑状态
		}
		Command command = new NodesCreateFromOutlineCommand(nodesList, diagram,
				new Rectangle(createRequest.getLocation().getCopy(),
						new Dimension(-1, -1)), editPart);
		BusinessModelUtil.getBusinessModelDiagramEditor().getCommandStacks()
				.execute(command);

		getCurrentEvent().detail = DND.DROP_COPY;
	}
}
