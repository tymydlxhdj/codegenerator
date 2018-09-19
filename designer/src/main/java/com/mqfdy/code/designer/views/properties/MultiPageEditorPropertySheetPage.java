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

// TODO: Auto-generated Javadoc
/**
 * 属性页.
 *
 * @author mqfdy
 */
public class MultiPageEditorPropertySheetPage extends PropertySheetPage
		implements IPropertySheetPage {
	
	/** The pagebook. */
	private PageBook pagebook;
	
	/** The rec map. */
	private Map recMap = new HashMap();
	
	/** The default page. */
	private PropertySheetPage defaultPage;
	
	/** The action bars. */
	private IActionBars actionBars;
	
	/** The current page. */
	private IPropertySheetPage currentPage;
	
	/** The disposed. */
	private boolean disposed = false;

	/**
	 * The Class PageRec.
	 *
	 * @author mqfdy
	 */
	class PageRec {
		
		/** The page. */
		IPropertySheetPage page;
		
		/** The bars. */
		SubActionBars bars;

		/**
		 * Sets the bars active.
		 *
		 * @author mqfdy
		 * @param active
		 *            the new bars active
		 * @Date 2018-09-03 09:00
		 */
		void setBarsActive(boolean active) {
			if (active)
				bars.activate();
			else
				bars.deactivate();
		}
	}

	/**
	 * Instantiates a new multi page editor property sheet page.
	 */
	public MultiPageEditorPropertySheetPage() {
		defaultPage = new BusinessPropertySheetPage(null);
	}

	/**
	 * Update property sheet.
	 *
	 * @author mqfdy
	 * @param editor
	 *            the editor
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Creates the control.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	public void createControl(Composite parent) {
		pagebook = new PageBook(parent, SWT.NULL);
		defaultPage.createControl(pagebook);
		if (currentPage != null)
			setPageActive(currentPage);
	}

	/**
	 * Creates the page rec.
	 *
	 * @author mqfdy
	 * @param page
	 *            the page
	 * @return the page rec
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * 
	 */
	public void dispose() {
		updateActionBars();

		if (pagebook != null && !pagebook.isDisposed())
			pagebook.dispose();
		pagebook = null;
		disposed = true;
		super.dispose();
	}

	/**
	 * Checks if is disposed.
	 *
	 * @author mqfdy
	 * @return true, if is disposed
	 * @Date 2018-09-03 09:00
	 */
	public boolean isDisposed() {
		return disposed;
	}

	/**
	 * @return
	 */
	public Control getControl() {
		return pagebook;
	}

	/**
	 * Gets the page control.
	 *
	 * @author mqfdy
	 * @param page
	 *            the page
	 * @return the page control
	 * @Date 2018-09-03 09:00
	 */
	private Control getPageControl(IPropertySheetPage page) {
		Control control = page.getControl();
		if (control == null || control.isDisposed()) {
			// first time
			page.createControl(pagebook);
			control = page.getControl();
		}
		return control;
	}

	/**
	 * Selection changed.
	 *
	 * @author mqfdy
	 * @param part
	 *            the part
	 * @param sel
	 *            the sel
	 * @Date 2018-09-03 09:00
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		if (currentPage != null)
			currentPage.selectionChanged(part, sel);
	}

	/**
	 * Sets the action bars.
	 *
	 * @author mqfdy
	 * @param bars
	 *            the new action bars
	 * @Date 2018-09-03 09:00
	 */
	public void setActionBars(IActionBars bars) {
		this.actionBars = bars;

		createPageRec(defaultPage);

		if (currentPage != null) {
			PageRec rec = createPageRec(currentPage);
			setPageActive(rec);
			updateActionBars();
		}
	}

	/**
	 * Sets the default page active.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void setDefaultPageActive() {
		setPageActive(defaultPage);
	}

	/**
	 * 
	 */
	public void setFocus() {
		if (currentPage != null)
			currentPage.setFocus();
	}

	/**
	 * Sets the page active.
	 *
	 * @author mqfdy
	 * @param pageRec
	 *            the new page active
	 * @Date 2018-09-03 09:00
	 */
	private void setPageActive(PageRec pageRec) {
		IPropertySheetPage page = pageRec.page;
		Control control = getPageControl(page);
		pagebook.showPage(control);
		pageRec.setBarsActive(true);
	}

	/**
	 * Sets the page active.
	 *
	 * @author mqfdy
	 * @param page
	 *            the new page active
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Update action bars.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void updateActionBars() {
		actionBars.updateActionBars();
	}

}
