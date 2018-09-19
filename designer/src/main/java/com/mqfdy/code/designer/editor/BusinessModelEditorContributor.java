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

// TODO: Auto-generated Javadoc
/**
 * 创建编辑器所需的菜单和操作.
 *
 * @author mqfdy
 */
public class BusinessModelEditorContributor extends
		MultiPageEditorActionBarContributor {
	
	/** The active editor part. */
	private IEditorPart activeEditorPart;
	
	/** The contributor visual. */
	private ActionBarContributor contributorVisual = new BMEditorActionBarContributor();
	
	/** The action bars visual. */
	private SubActionBars actionBarsVisual;

	/**
	 * Instantiates a new business model editor contributor.
	 */
	public BusinessModelEditorContributor() {
		super();
		createActions();
	}

	/**
	 * 获取注入的action.
	 *
	 * @author mqfdy
	 * @param editor
	 *            the editor
	 * @param actionID
	 *            the action ID
	 * @return the action
	 * @Date 2018-09-03 09:00
	 */
	protected IAction getAction(ITextEditor editor, String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}

	/**
	 * Gets the action.
	 *
	 * @author mqfdy
	 * @param editor
	 *            the editor
	 * @param actionID
	 *            the action ID
	 * @return the action
	 * @Date 2018-09-03 09:00
	 */
	protected IAction getAction(BusinessModelDiagramEditor editor,
			String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}

	/**
	 * Sets the active page.
	 *
	 * @author mqfdy
	 * @param part
	 *            the new active page
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Sets the global action handlers.
	 *
	 * @author mqfdy
	 * @param bar
	 *            the new global action handlers
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Creates the actions.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createActions() {

	}

	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @param bars
	 *            the bars
	 * @param page
	 *            the page
	 * @Date 2018-09-03 09:00
	 */
	public void init(IActionBars bars, IWorkbenchPage page) {
		actionBarsVisual = new SubActionBars(bars);
		contributorVisual.init(actionBarsVisual, page);
		super.init(bars, page);
	}

	/**
	 * 
	 */
	public void dispose() {
		contributorVisual.dispose();
		actionBarsVisual.dispose();
		super.dispose();
	}
}
