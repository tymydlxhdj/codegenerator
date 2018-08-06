package com.mqfdy.code.designer.editor.listeners;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;

import com.mqfdy.code.designer.views.modelresource.page.ObjectModelOutlinePage;

/**
 * 工作空间资源管理器的资源监听器 透视图编辑器内容改变的监听器
 * 
 * 当用编辑器打开bom文件对其中的内容进行修改时此监听器开始工作
 * 
 * @author mqfdy
 * 
 */
public class TreeResourceChangeListener implements IResourceChangeListener {
	private ObjectModelOutlinePage objectModelOutlinePage;
	private String path;

	public TreeResourceChangeListener(String path,
			ObjectModelOutlinePage objectModelOutlinePage) {
		this.objectModelOutlinePage = objectModelOutlinePage;
		this.path = path;
	}

	/**
	 * om文件不存在或者所在项目不存在时返回； 当om文件被其他编辑器修改后，给出提示，并先关闭再打开编辑器
	 * 
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta s = event.getDelta();
		if(s == null)
			return;
		while (s.getAffectedChildren().length > 0) {
			s = s.getAffectedChildren()[0];
		}
		if (path.equals(s.getFullPath().toString())) {
			objectModelOutlinePage.getRmViewer().initTreeViewerData(path);
		}
	}
}
