package com.mqfdy.code.designer.dialogs.table.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.mqfdy.code.model.BEStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class StatusSorter.
 *
 * @author mqfdy
 */
public class StatusSorter extends ViewerSorter {

	/**
	 * Compare.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param obj1
	 *            the obj 1
	 * @param obj2
	 *            the obj 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public int compare(Viewer viewer, Object obj1, Object obj2) {
		if (obj1 instanceof BEStatus && obj2 instanceof BEStatus) {
			BEStatus beStatus1 = (BEStatus) obj1;
			BEStatus beStatus2 = (BEStatus) obj2;
			Integer order1 = beStatus1.getOrderNum();
			Integer order2 = beStatus2.getOrderNum();
			return order1.compareTo(order2);
		}
		return super.compare(viewer, obj1, obj2);
	}

}
