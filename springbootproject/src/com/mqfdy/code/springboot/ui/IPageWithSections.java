package com.mqfdy.code.springboot.ui;

import org.eclipse.swt.widgets.Shell;

/**
 * Abstraction for the 'owner' of a UI 'Section' on a some page. This is so that we can
 * reuse UI widgetry more easily across different kinds of UI contexts (e.g. both on
 * a preferences page and a launch config editor.
 * 
 * @author lenovo
 */
public interface IPageWithSections {

	Shell getShell();

}
