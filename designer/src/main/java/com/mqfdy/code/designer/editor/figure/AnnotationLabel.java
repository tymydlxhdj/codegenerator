package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;

// TODO: Auto-generated Javadoc
/**
 * The Class AnnotationLabel.
 *
 * @author mqfdy
 */
public class AnnotationLabel extends FlowPage {
    
    /** The contents. */
    private TextFlow contents;
    
    /**
	 * Instantiates a new annotation label.
	 */
    public AnnotationLabel() {
        this("");
    }

    /**
	 * Instantiates a new annotation label.
	 *
	 * @param text
	 *            the text
	 */
    public AnnotationLabel(String text) {
    	setHorizontalAligment(PositionConstants.CENTER);
    	contents = new TextFlow();
        ParagraphTextLayout lay = new ParagraphTextLayout(contents, 
        		ParagraphTextLayout.WORD_WRAP_SOFT);
        contents.setLayoutManager(lay);
        contents.setText(text);
        Font f = JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT);
        
        contents.setFont(new Font(f.getDevice(), "", 10, 4));
        add(contents);
        
//        setBorder(new MarginBorder(3, 0, 0, 0) {
//			@Override
//			public void paint(IFigure figure, Graphics graphics, Insets insets) {
//				// get rectangle surrending the figure
//				Rectangle rec = getPaintRectangle(figure, insets);
//				// 设置字段间的分割线为虚线
////				 graphics.setLineStyle(Graphics.LINE_DOT);
//				 graphics.drawLine(rec.x, rec.y, rec.right(), rec.y);
//
//			}
//		});
    }

    /**
	 * Sets the text.
	 *
	 * @author mqfdy
	 * @param text
	 *            the new text
	 * @Date 2018-09-03 09:00
	 */
    public void setText(String text) {
        contents.setText(text);
    }

	/**
	 * Gets the text.
	 *
	 * @author mqfdy
	 * @return the text
	 * @Date 2018-09-03 09:00
	 */
	public String getText() {
        return contents.getText();
    }
}

