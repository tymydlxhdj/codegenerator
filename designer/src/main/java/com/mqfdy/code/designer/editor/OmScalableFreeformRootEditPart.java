package com.mqfdy.code.designer.editor;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;

// TODO: Auto-generated Javadoc
/**
 * 继承ScalableFreeformRootEditPart，使用自定义的gridLayer.
 *
 * @author mqfdy
 */
public class OmScalableFreeformRootEditPart extends
		org.eclipse.gef.editparts.ScalableFreeformRootEditPart {

	/**
	 * 创建布局，使用自定义的GridLayer.
	 *
	 * @author mqfdy
	 * @return the scalable freeform layered pane
	 * @Date 2018-09-03 09:00
	 */
	protected ScalableFreeformLayeredPane createScaledLayers() {
		ScalableFreeformLayeredPane layers = new ScalableFreeformLayeredPane();
		layers.add(new LineGridLayer(), GRID_LAYER);
		layers.add(getPrintableLayers(), PRINTABLE_LAYERS);
		layers.add(new FeedbackLayer(), SCALED_FEEDBACK_LAYER);
		return layers;
	}

	/**
	 * The Class FeedbackLayer.
	 *
	 * @author mqfdy
	 */
	class FeedbackLayer extends FreeformLayer {
		
		/**
		 * Instantiates a new feedback layer.
		 */
		FeedbackLayer() {
			setEnabled(false);
		}
	}
}
