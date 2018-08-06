package com.mqfdy.code.designer.editor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * 创建编辑器所需的菜单和操作
 * 
 * @author mqfdy
 * 
 */
public class BusinessModelEditorContributor extends
		MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;
	private ActionBarContributor contributorVisual = new BMEditorActionBarContributor();
	private SubActionBars actionBarsVisual;

	public BusinessModelEditorContributor() {
		super();
		createActions();
	}

	/**
	 * 获取注入的action
	 */
	protected IAction getAction(ITextEditor editor, String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}

	protected IAction getAction(BusinessModelDiagramEditor editor,
			String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}

	/*
	 * 设置活动页
	 */
	public void setActivePage(IEditorPart part) {
		if (activeEditorPart == part)
			return;
		activeEditorPart = part;
		getActionBars().clearGlobalActionHandlers();
		if (part instanceof BusinessModelDiagramEditor) {
			contributorVisual.setActiveEditor(part);
			setGlobalActionHandlers(actionBarsVisual);
			actionBarsVisual.activate();
		} else {
			actionBarsVisual.deactivate();
		}
		getActionBars().updateActionBars();
	}

	private void setGlobalActionHandlers(SubActionBars bar) {
		Map<?, ?> handlers = bar.getGlobalActionHandlers();
		if (handlers != null) {
			Set<?> keys = handlers.keySet();
			for (Iterator<?> iter = keys.iterator(); iter.hasNext();) {
				String id = (String) iter.next();
				getActionBars().setGlobalActionHandler(id,
						(IAction) handlers.get(id));
			}
		}
		getActionBars().updateActionBars();
	}

	private void createActions() {

	}

	public void init(IActionBars bars, IWorkbenchPage page) {
		actionBarsVisual = new SubActionBars(bars);
		contributorVisual.init(actionBarsVisual, page);
		super.init(bars, page);
	}

	public void dispose() {
		contributorVisual.dispose();
		actionBarsVisual.dispose();
		super.dispose();
	}
}
