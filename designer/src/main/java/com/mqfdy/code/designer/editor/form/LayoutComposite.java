package com.mqfdy.code.designer.editor.form;

import org.eclipse.swt.widgets.Composite;
// TODO: Auto-generated Javadoc
/**
 * The class overrides default method for computing size in Composite by
 * accepting size returned from layout managers as-is. The default code accepts
 * width or height hint assuming it is correct. However, it is possible that the
 * computation using the provided width hint results in a real size that is
 * larger. This can result in wrapped text widgets being clipped, asking to
 * render in bounds narrower than the longest word.
 *
 * @author mqfdy
 */
class LayoutComposite extends Composite {
	
	/**
	 * Instantiates a new layout composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public LayoutComposite(Composite parent, int style) {
		super(parent, style);
		setMenu(parent.getMenu());
	}
}

