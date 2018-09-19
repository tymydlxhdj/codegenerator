package com.mqfdy.code.designer.editor.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.PropertySheet;

import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.properties.MultiPageEditorPropertySheetPage;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorOperation.
 *
 * @author mqfdy
 */
public class EditorOperation {
	
	/**
	 * 刷新图形节点.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public static void refreshNodeEditParts() {
		IWorkbenchPage acPage = BusinessModelEditorPlugin
				.getActiveWorkbenchWindow().getActivePage();
		BusinessModelEditor editor = (BusinessModelEditor) acPage
				.getActiveEditor();
		Iterator<?> it = editor.getBuEditor().getAllEditParts();
		List<NodeEditPart> list = new ArrayList<NodeEditPart>();
		while (it.hasNext()) {
			Object e = ((Entry<?, ?>) it.next()).getValue();
			if (e instanceof NodeEditPart) {
				if (((NodeEditPart) e).getModel() instanceof DiagramElement) {
					list.add((NodeEditPart) e);
				}
			}
		}
		for (NodeEditPart e : list) {
			e.refresh();
		}
	}
	
	/**
	 * 刷新属性视图.
	 *
	 * @author mqfdy
	 * @param sel
	 *            the sel
	 * @Date 2018-09-03 09:00
	 */
	public static void refreshProperties(ISelection sel) {
		IViewPart[] views = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViews();
		for (int i = 0; i < views.length; i++) {
			if (views[i] instanceof PropertySheet) {
				PropertySheet view = (PropertySheet) views[i];
				if ((((PropertySheet) views[i]).getCurrentPage()) instanceof MultiPageEditorPropertySheetPage) {
					MultiPageEditorPropertySheetPage page = ((MultiPageEditorPropertySheetPage) (((PropertySheet) views[i])
							.getCurrentPage()));
					page.selectionChanged(view, sel);
				}
			}
		}
	}
	
	/**
	 * 获取绝对路径.
	 *
	 * @author mqfdy
	 * @param path
	 *            the path
	 * @return the project path
	 * @Date 2018-09-03 09:00
	 */
	public static String getProjectPath(String path){
		String proName = "";
		String[] paths = path.split("/");//File.separator);
		if(paths.length == 1){
			paths = path.split("\\\\");
		}
		for(int i = 0;i<paths.length;i++){
			if(!paths[i].equals("")){
				proName = paths[i];
				paths[i] = "";
				break;
			}
		}
		StringBuffer p = new StringBuffer("");
		for(int i = 0;i<paths.length;i++){
			if(!paths[i].equals("")){
				p.append(File.separator+paths[i]);
			}
		}
		IProject[] resource = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for(int i = 0;i < resource.length;i ++){
			if(resource[i].getName().equals(proName)){
				return resource[i].getLocation().toOSString()+p;
			}
		}
		return "";
	}
	
	/**
	 * 查找文件被打开的编辑器.
	 *
	 * @author mqfdy
	 * @param om
	 *            the om
	 * @param pages
	 *            the pages
	 * @Date 2018-09-03 09:00
	 */
	public static void refreshEditorByFile(String om ,IWorkbenchPage[] pages){
		BusinessModelEditor editor = null;
		for(int i = 0;i < pages.length;i++){
			IEditorPart[] editors = pages[i].getEditors();
			for(int j = 0;j < editors.length;j++){
				if(editors[j].getEditorInput() instanceof FileEditorInput){
					IFile file = (IFile) ((FileEditorInput) editors[j].getEditorInput()).getStorage();
					if(file != null && file.getLocation() != null && file.getLocation().toOSString().equals(om)
							&& editors[j] instanceof BusinessModelEditor){
						editor = (BusinessModelEditor) editors[j];
						break;
					}
				}
			}
		}
		if(editor == null)
			return;
		IEditorInput input = editor.getEditorInput();
		editor.setInputPub(input);
		editor.getBuEditor().setInput(input);
		editor.getBuEditor().getViewer().setContents(editor.getBuEditor().getDia());
		editor.getBuEditor().getCommandStacks().flush();
		List<BusinessObjectModel> boms = new ArrayList<BusinessObjectModel>();
		BusinessObjectModel bom = editor
				.getBuEditor().getBusinessModelManager()
				.getBusinessObjectModel();
		boms.add(bom);
		if(BusinessModelUtil.getOutlinePage() != null){
		BusinessModelUtil.getOutlinePage().getMrViewer()
				.getTreeViewer().setInput(boms);
		}
	}
	 
}
