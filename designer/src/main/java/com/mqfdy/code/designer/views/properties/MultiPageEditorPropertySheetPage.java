package com.mqfdy.code.designer.views.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

/**
 * 属性页
 * 
 * @author mqfdy
 * 
 */
public class MultiPageEditorPropertySheetPage extends PropertySheetPage
		implements IPropertySheetPage {
	private PageBook pagebook;
	private Map recMap = new HashMap();
	private PropertySheetPage defaultPage;
	private IActionBars actionBars;
	private IPropertySheetPage currentPage;
	private boolean disposed = false;

	class PageRec {
		IPropertySheetPage page;
		SubActionBars bars;

		void setBarsActive(boolean active) {
			if (active)
				bars.activate();
			else
				bars.deactivate();
		}
	}

	public MultiPageEditorPropertySheetPage() {
		defaultPage = new BusinessPropertySheetPage(null);
	}

	public void updatePropertySheet(IEditorPart editor) {
		// System.out.print("\r\n updatePropertySheet: "+editor.getClass().getName());
		if (editor != null) {
			IPropertySheetPage propertySheetPage = (IPropertySheetPage) editor
					.getAdapter(IPropertySheetPage.class);
			if (propertySheetPage != null) {
				setPageActive(propertySheetPage);
			} else {
				setDefaultPageActive();
			}
		}
	}

	public void createControl(Composite parent) {
		pagebook = new PageBook(parent, SWT.NULL);
		defaultPage.createControl(pagebook);
		if (currentPage != null)
			setPageActive(currentPage);
	}

	private PageRec createPageRec(IPropertySheetPage page) {
		if (actionBars == null)
			return null;
		PageRec rec = new PageRec();
		rec.page = page;

		rec.bars = new SubActionBars(actionBars);
		getPageControl(page);

		page.setActionBars(rec.bars);
		recMap.put(page, rec);
		return rec;
	}

	public void dispose() {
		updateActionBars();

		if (pagebook != null && !pagebook.isDisposed())
			pagebook.dispose();
		pagebook = null;
		disposed = true;
		super.dispose();
	}

	public boolean isDisposed() {
		return disposed;
	}

	public Control getControl() {
		return pagebook;
	}

	private Control getPageControl(IPropertySheetPage page) {
		Control control = page.getControl();
		if (control == null || control.isDisposed()) {
			// first time
			page.createControl(pagebook);
			control = page.getControl();
		}
		return control;
	}

	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		if (currentPage != null)
			currentPage.selectionChanged(part, sel);
	}

	public void setActionBars(IActionBars bars) {
		this.actionBars = bars;

		createPageRec(defaultPage);

		if (currentPage != null) {
			PageRec rec = createPageRec(currentPage);
			setPageActive(rec);
			updateActionBars();
		}
	}

	public void setDefaultPageActive() {
		setPageActive(defaultPage);
	}

	public void setFocus() {
		if (currentPage != null)
			currentPage.setFocus();
	}

	private void setPageActive(PageRec pageRec) {
		IPropertySheetPage page = pageRec.page;
		Control control = getPageControl(page);
		pagebook.showPage(control);
		pageRec.setBarsActive(true);
	}

	public void setPageActive(IPropertySheetPage page) {
		IPropertySheetPage oldPage = currentPage;
		this.currentPage = page;
		if (pagebook == null) {
			return;
		}
		if (oldPage != null) {
			PageRec oldRec = (PageRec) recMap.get(oldPage);
			if (oldRec != null) {
				oldRec.setBarsActive(false);
			}
		}
		PageRec rec = (PageRec) recMap.get(page);
		if (rec == null) {
			rec = createPageRec(page);
		}
		if (rec != null) {
			setPageActive(rec);
			updateActionBars();
		}
	}

	private void updateActionBars() {
		actionBars.updateActionBars();
	}

}
