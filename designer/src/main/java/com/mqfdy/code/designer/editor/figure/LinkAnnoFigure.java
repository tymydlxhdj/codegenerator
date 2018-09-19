package com.mqfdy.code.designer.editor.figure;

import org.eclipse.swt.SWT;

import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.model.LinkAnnotation;

// TODO: Auto-generated Javadoc
/**
 * 关系连线对象的显示Figure.
 *
 * @author mqfdy
 */
public class LinkAnnoFigure extends ConnectionFigure {

	/** The link. */
	private LinkAnnotation link;

	/**
	 * Simple constructor to create a directed association, aggregation or
	 * composition with any labels.
	 *
	 * @param link
	 *            the link
	 * @param linkAnnoEditPart
	 *            the link anno edit part
	 */
	public LinkAnnoFigure(LinkAnnotation link,
			OmConnectionEditPart linkAnnoEditPart) {
		super(linkAnnoEditPart);
		this.link = link;
		setLineStyle(SWT.LINE_DOT);//设置虚线
	}


	/**
	 * Construct an empty figure : a connection line without any decoration.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void emptyFigure() {
		setSourceDecoration(null);
		setTargetDecoration(null);
	}

	/**
	 * Gets the link.
	 *
	 * @author mqfdy
	 * @return the link
	 * @Date 2018-09-03 09:00
	 */
	public LinkAnnotation getLink() {
		return link;
	}
}
