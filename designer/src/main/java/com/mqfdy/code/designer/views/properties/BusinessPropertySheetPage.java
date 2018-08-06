package com.mqfdy.code.designer.views.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelManager;

/**
 * 属性页
 * 
 * @author mqfdy
 * 
 */
public class BusinessPropertySheetPage extends PropertySheetPage {

	// private static Tree tree;
	private BusinessModelManager businessModelManager;
	public static Map<String, Integer> componentCount = new HashMap<String, Integer>();

	public BusinessPropertySheetPage(BusinessModelManager businessModelManager) {
		super();
		this.businessModelManager = businessModelManager;
	}

	public static void addComponent(String componentName) {
		if (componentCount.containsKey(componentName)) {
			Integer count = componentCount.get(componentName);
			count++;
			componentCount.put(componentName, count);
		} else {
			componentCount.put(componentName, 1);
		}
	}

	public static int getComponentExistCount(String componentName) {
		int count = 0;
		if (componentCount.containsKey(componentName)) {
			count = componentCount.get(componentName);
		}
		return count;
	}

	public static void resetCount(Map<String, Integer> initMap) {
		componentCount = initMap;
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		try {
			this.setSorter(new BusinessPropertySheetSorter());
		} catch (Exception e) {
			Logger.log(e);
		}
		try {
			this.setRootEntry(new BusinessPropertySheetEntry(
					businessModelManager));
		} catch (Exception e) {
			Logger.log(e);
		}
		super.createControl(parent);
		// if (getControl() instanceof org.eclipse.swt.widgets.Tree) {
		// tree = (Tree) getControl();
		// }
		//
		// getControl().addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseDoubleClick(MouseEvent event) {
		// super.mouseDoubleClick(event);
		// Point pt = new Point(event.x, event.y);
		// TreeItem item = tree.getItem(pt);
		// if (item != null) {
		// // goToEventScript(item);
		// }
		// }
		//
		// });
	}

	// private void callDoubleClick() {
	// if (tree == null)
	// return;
	//
	// if (tree.getSelectionCount() > 0) {
	// TreeItem ti = tree.getSelection()[0];
	// PropertySheetEntry pse = (PropertySheetEntry) ti.getData();
	// // this.getSite().
	//
	// System.out.print("\r\n Category:" + pse.getCategory() + ",value:"
	// + pse.getValueAsString());
	// System.out.print(",name:" + pse.getDisplayName());
	//
	// }
	// }
}