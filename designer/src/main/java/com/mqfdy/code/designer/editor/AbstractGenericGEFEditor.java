package com.mqfdy.code.designer.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;

import com.mqfdy.code.designer.utils.BusinessModelUtil;

// TODO: Auto-generated Javadoc
/**
 * 继承GraphicalEditorWithFlyoutPalette 主要用于获取鼠标在当前编辑器中的相对位置.
 *
 * @author mqfdy
 */
public class AbstractGenericGEFEditor extends GraphicalEditorWithFlyoutPalette {
	
	/** The mouse location. */
	private static Point mouseLocation = new Point();
	
	/** The range location. */
	private static Point rangeLocation = new Point();

	/**
	 * @return
	 */
	@Override
	protected PaletteRoot getPaletteRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	@Override
	public Control getGraphicalControl() {
		Control graphicalControl = super.getGraphicalControl();
		graphicalControl.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
		        int scrollWidth = 0;//东西偏移量   
		        int scrollHeight = 0;//南北偏移量   
		  
		        FigureCanvas canvas = (FigureCanvas) BusinessModelUtil.getGefViewer().getContents().getRoot().getViewer().getControl(); // 得到滚动区域的画布   
		        scrollWidth = canvas.getViewport().getHorizontalRangeModel().getValue();   
		        scrollHeight = canvas.getViewport().getVerticalRangeModel().getValue();   
		        rangeLocation.x = scrollWidth;
		        rangeLocation.y = scrollHeight;
				mouseLocation.x = e.x + scrollWidth;
				mouseLocation.y = e.y + scrollHeight;
			}
		});
		return graphicalControl;
	}

	/**
	 * Do save.
	 *
	 * @author mqfdy
	 * @param monitor
	 *            the monitor
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取鼠标位置.
	 *
	 * @author mqfdy
	 * @return the mouse location
	 * @Date 2018-09-03 09:00
	 */
	public static Point getMouseLocation() {
		return mouseLocation;
	}
	
	/**
	 * 获取滚动位移.
	 *
	 * @author mqfdy
	 * @return the range location
	 * @Date 2018-09-03 09:00
	 */
	public static Point getRangeLocation() {
		return rangeLocation;
	}
}
