package com.mqfdy.code.designer.dialogs.table.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.mqfdy.code.model.BEStatus;

public class StatusSorter extends ViewerSorter {

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
