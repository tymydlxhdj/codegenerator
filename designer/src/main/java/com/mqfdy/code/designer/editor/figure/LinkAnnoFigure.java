package com.mqfdy.code.designer.editor.figure;

import org.eclipse.swt.SWT;

import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.model.LinkAnnotation;

/**
 * 
 * 关系连线对象的显示Figure
 * 
 * @author mqfdy
 * 
 */
public class LinkAnnoFigure extends ConnectionFigure {

	private LinkAnnotation link;

	/**
	 * Simple constructor to create a directed association, aggregation or
	 * composition with any labels.
	 * 
	 */
	public LinkAnnoFigure(LinkAnnotation link,
			OmConnectionEditPart linkAnnoEditPart) {
		super(linkAnnoEditPart);
		this.link = link;
		setLineStyle(SWT.LINE_DOT);//设置虚线
	}


	/**
	 * Construct an empty figure : a connection line without any decoration.
	 */
	public void emptyFigure() {
		setSourceDecoration(null);
		setTargetDecoration(null);
	}

	public LinkAnnotation getLink() {
		return link;
	}
}
