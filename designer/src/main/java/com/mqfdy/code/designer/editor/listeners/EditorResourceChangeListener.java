package com.mqfdy.code.designer.editor.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;

import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.graph.Diagram;

// TODO: Auto-generated Javadoc
/**
 * 工作空间资源管理器的资源监听器 透视图编辑器内容改变的监听器
 * 
 * 当用编辑器打开bom文件对其中的内容进行修改时此监听器开始工作.
 *
 * @author mqfdy
 */
public class EditorResourceChangeListener implements IResourceChangeListener {
	
	/** The editor part. */
	private IEditorPart editorPart;

	// private final IWorkspace workspace = ResourcesPlugin.getWorkspace();

	/**
	 * Instantiates a new editor resource change listener.
	 *
	 * @param editorPart
	 *            the editor part
	 */
	public EditorResourceChangeListener(IEditorPart editorPart) {
		this.editorPart = editorPart;
	}

	/**
	 * om文件不存在或者所在项目不存在时返回； 当om文件被其他编辑器修改后，给出提示，刷新编辑器.
	 *
	 * @param event
	 *            the event
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		// editorPart为ModelDiagramEditor 持久模型编辑器
		IEditorInput input = editorPart.getEditorInput();
		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			if (file == null)
				return;
			IProject project = file.getProject();
			if (project != null && !project.isAccessible()) {
				closeEditor();
				return;
			}
			if (file != null && !file.exists()) {
				closeEditor();
				return;
			}

			// 如果ModelDiagramEditor是打开的
			if (editorPart instanceof BusinessModelEditor) {
				if (((BusinessModelEditor) editorPart).getBuEditor() == null)
					return;
				// 获得md文件的时间戳
				long last = (((BusinessModelEditor) editorPart).getBuEditor())
						.getLastTimeStamp();
				long stamp = file.getLocalTimeStamp();

				// 如果文件被修改过
				if (last != stamp && stamp > last) {

					(((BusinessModelEditor) editorPart).getBuEditor())
							.setLastTimeStamp(stamp);
					IWorkbenchPage workbenchPage = editorPart.getSite()
							.getWorkbenchWindow().getActivePage();
					// IWorkbenchPage workbenchPage =
					// BusinessModelEditorPlugin.getActiveWorkbenchWindow().getActivePage();

					IEditorPart editor = workbenchPage.findEditor(input);

					// 如果文件是在BusinessModelEditor中进行编辑的，就直接返回不再处理
					if (editor instanceof BusinessModelEditor) {
						return;

					} else {// 如果是用其他编辑器对om文件进行编辑的
						// 把类型editorPart编辑器（持久模型编辑器）设为当前显示的编辑器
						workbenchPage.bringToTop(editorPart);
						boolean flag = MessageDialog
								.openConfirm(editorPart.getSite().getShell(),
										"提示",
										"这个文件已被其他程序修改，您想要重新载入吗？\n建议：为了能正确显示持久模型，请不要使用其他编辑器对.om文件进行编辑。");

						if (flag) {
							// try {
							((BusinessModelEditor) editorPart).setInputPub(input);
							((BusinessModelEditor) editorPart).getBuEditor().setInput(input);
							Diagram dia = ((BusinessModelEditor) editorPart).getBuEditor().getDia();
							((BusinessModelEditor) editorPart).getBuEditor().getViewer().setContents(dia);
							List<BusinessObjectModel> boms = new ArrayList<BusinessObjectModel>();
							BusinessObjectModel bom = ((BusinessModelEditor) editorPart)
									.getBuEditor().getBusinessModelManager()
									.getBusinessObjectModel();
							boms.add(bom);
							if(BusinessModelUtil.getOutlinePage() != null)
								BusinessModelUtil.getOutlinePage().getMrViewer().getTreeViewer().setInput(boms);
						}
					}
				}
			}
		}
	}

	/**
	 * Close editor.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void closeEditor() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				editorPart.getSite().getPage().closeEditor(editorPart, false);
			}
		});
	}

}
