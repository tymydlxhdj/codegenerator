package com.mqfdy.code.thirdparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;

import com.mqfdy.code.designer.editor.auto.algorithms.GXTreeLayoutAlgorithm;
import com.mqfdy.code.designer.editor.auto.algorithms.GraphOm;
import com.mqfdy.code.designer.editor.auto.algorithms.ShiftDiagramLayoutAlgorithm;
import com.mqfdy.code.designer.editor.commands.AutoLayoutCommand;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;
import com.mqfdy.code.resource.BomManager;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.utils.ReverseContants;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.pages.PackageDispachPage;
import com.mqfdy.code.thirdparty.utils.PDMBinder;

// TODO: Auto-generated Javadoc
/**
 * The Class OmImport.
 *
 * @author mqfdy
 */
public class OmImport {
	
	/** The y. */
	private int y = 0;
	
	/**
	 * 获取schema下所有表.
	 *
	 * @author mqfdy
	 * @param filePath
	 *            the file path
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> fetchTables(String filePath) {
		
		List<Table> tables = new ArrayList();
		tables = PdmImport.createModelXml(filePath, "system", false);
		return tables;
	}

	
	/**
	 * 获取具有关联关系的表.
	 *
	 * @author mqfdy
	 * @param tableName
	 *            the table name
	 * @return the relative tables
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getRelativeTables(String tableName) {
		Table selectedTable = ReverseContext.allTables.get(tableName);
	
		
		List<Table> relativeTables = ReverseUtil.getRelativeTables(selectedTable);
		
		
		if(isManyToManyTable(selectedTable)){//如果选中的表就是中间表，那么下面的步骤就不用走了
			return relativeTables;
		}
		
		List<Table> otherTables = new ArrayList<Table>();
		for(Table rTable : relativeTables){
			if(isManyToManyTable(rTable)){
				Table otherTable = null;
				Map<String, ForeignKey> fks = rTable.getForeignKeys();
				for(Map.Entry<String, ForeignKey> mfk : fks.entrySet()){
					ForeignKey fk = mfk.getValue();
					if(!fk.getReferencedTable().getName().equalsIgnoreCase(tableName)){
						otherTable = fk.getReferencedTable();
						break;
					}
				}
				if(otherTable != null){
					otherTables.add(otherTable);
				}
			}
		}
		
		relativeTables.addAll(otherTables);
		return relativeTables;
	}
	
	
	/**
	 * 有且只有两列，且都为外键列才可判断为中间表 判断是中间表的依据: 当前表无主键,并且两个外键,仅有两个字段.
	 *
	 * @author mqfdy
	 * @param table
	 *            the table
	 * @return true, if is many to many table
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isManyToManyTable(Table table){
		Map<String, Column> columns = table.getColumns();
		
		PrimaryKey pk = table.getPrimaryKey();
		Map<String, ForeignKey> foreignKeys = table.getForeignKeys();
		
		if(foreignKeys.size()!=2){ 
			return false;
		}else if(columns.size()==2 && pk==null && foreignKeys.size()==2){
			return true;
		}
//		else if(pk.getColumns().size()==table.getColumns().size()-2 && foreignKeys.size()==2){
//			return true;
//		}
		return false;
	}
	
	/** The ignored special. */
	boolean ignoredSpecial;
	
	/**
	 * Find no pk table.
	 *
	 * @author mqfdy
	 * @param tableNames
	 *            the table names
	 * @param monitor
	 *            the monitor
	 * @return the map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, List<Table>> findNoPkTable(List<String> tableNames, IProgressMonitor monitor){
		ReverseContext.selectedTables.clear();
		ReverseContext.lastTables.clear();
		ignoredSpecial = false;
		
		Map<String, List<Table>> specialTables = new HashMap<String, List<Table>>();
		List<Table> noPkTables = new ArrayList<Table>();
		List<Table> mutilPkTables = new ArrayList<Table>();
		
		specialTables.put(ReverseContants.NOPKTABLE, noPkTables);
		specialTables.put(ReverseContants.MUTILPKTABLE, mutilPkTables);
		
		for(String tableName : tableNames){
			Table table = ReverseContext.allTables.get(tableName);
			//缓存选择的表
			ReverseContext.selectedTables.put(tableName, table);
			
			//缓存无主键表与复合主键表
			if(ReverseUtil.isNoPkTable(table)){
				ReverseContext.noPks.add(table.getName());
				noPkTables.add(table);
			}
			
			if(ReverseUtil.isMultiPkTable(table)){
				ReverseContext.noPks.add(table.getName());
				mutilPkTables.add(table);
			}
			
			String subTask = "正在分析表:  " + tableName;
			subTask       += "\n" + "共有" + tableNames.size() + "个表，已分析出不合规的表：" + (noPkTables.size() + mutilPkTables.size()) + "个"; 
			
			monitor.subTask(subTask);
			monitor.worked(1);
		}
		
		return specialTables;
	}
	
	
	/**
	 * Creates the bom.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @param monitor
	 *            the monitor
	 * @param pdPage
	 *            the pd page
	 * @Date 2018-09-03 09:00
	 */
	public void createBom(BusinessObjectModel bom, IProgressMonitor monitor,
			final PackageDispachPage pdPage) {

		List<BusinessClass> bcList = bom.getBusinessClasses();
		for (BusinessClass bc : bcList) {
			Table bcTable = ReverseContext.lastTables.get(bc.getTableName());
			if (bcTable != null) {
				ReverseContext.btMap.put(bc, bcTable);
				ReverseContext.tbMap.put(bcTable, bc);
			}
		}

		monitor.subTask("正在读取表信息  ......");
		PDMBinder jb = new PDMBinder();
		monitor.worked(1);
		monitor.subTask("正在将表信息转为业务实体对象  ......");
		jb.createBusinessClasses();
		monitor.worked(1);
		monitor.subTask("正在创建关联关系对象  ......");
		jb.createAssociation();
		monitor.worked(1);
		// 添加图元 并布局
		monitor.subTask("正在创建图元  ......");
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				createDiagramElement(pdPage);
			}
		});
		monitor.worked(1);
		
		monitor.subTask("正在写入xml文件  ......");
		BomManager.outputXmlFile(bom, ReverseContext.OM_STORAGE_PATH);
		monitor.worked(1);
	}
	
	/**
	 * Creates the diagram element.
	 *
	 * @author mqfdy
	 * @param pdPage
	 *            the pd page
	 * @Date 2018-09-03 09:00
	 */
	// 添加图元 并布局
			private void createDiagramElement(PackageDispachPage pdPage) {
				// 添加图元 并布局
				BusinessObjectModel bom = ReverseContext.bom;
				List<BusinessClass> newBuList = pdPage.getNewBuList();
				List<ModelPackage> newpkgList = new ArrayList<ModelPackage>();
				Map<ModelPackage, List<BusinessClass>> map = new HashMap<ModelPackage, List<BusinessClass>>();
				for (BusinessClass bu : newBuList) {
					ModelPackage pkg = bu.getBelongPackage();
					if (!newpkgList.contains(pkg)) {
						newpkgList.add(pkg);
						map.put(pkg, new ArrayList<BusinessClass>());
					}
					map.get(pkg).add(bu);
				}
				int layoutStyle = LayoutStyles.NO_LAYOUT_NODE_RESIZING;
				for (ModelPackage pkg : newpkgList) {
					List<BusinessClass> nonConBuList = new ArrayList<BusinessClass>();
					Shell shell = pdPage.getShell();
					GraphOm omGraph = new GraphOm(shell, SWT.NONE);
					Diagram dia = null;
					dia = getDiagram(pkg);
					if (dia == null)
						return;
					omGraph.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
					// 计算已存在的图形位置信息-Y的最大值
					int h = 0;
					for (DiagramElement de : dia.getElements()) {
						// Object obj = bom.getModelElementById(de.getObjectId());
						// if((obj instanceof BusinessClass) || (obj instanceof
						// Enumeration)
						// || (obj instanceof Annotation)
						// || (obj instanceof DataTransferObject))
						if (de.getStyle().getPositionY() > 0
								&& de.getStyle().getHeight() > 0)
							h = (int) Math.max(h, de.getStyle().getPositionY()
									+ de.getStyle().getHeight());
					}

					boolean isNonCon = true;
					for (BusinessClass bu : map.get(pkg)) {
						isNonCon = true;
						for (Association as : bom.getAssociations()) {
							if(as.getClassA() == null || as.getClassB() == null) {
								continue;
							}
						
							if ((bu.getId().equals(as.getClassA().getId()) && map.get(pkg).contains(as.getClassB())) 
									|| (bu.getId().equals(as.getClassB().getId()) && map.get(pkg).contains(as.getClassA()))) {
								boolean isExsit = false;
								for (Object node : omGraph.getNodes()) {
									if (node instanceof GraphNode) {
										if (((GraphNode) node).getData() == bu) {
											isExsit = true;
										}
									}
								}
								if (!isExsit) {
									GraphNode node1 = new GraphNode(omGraph, ZestStyles.NODES_FISHEYE, bu);
									node1.setSize(150, 200);
									isNonCon = false;
								}
							}
						}
						
						if (isNonCon) {
							nonConBuList.add(bu);
						}
						
					}
					
					for (Association as : bom.getAssociations()) {
						if(as.getClassA() == null || as.getClassB() == null) {
							continue;
						}
						
						if (map.get(pkg).contains(as.getClassA())
								&& map.get(pkg).contains(as.getClassB())) {
							new GraphConnection(omGraph, SWT.NONE,
									AutoLayoutCommand.getNode(as.getClassA(), omGraph),
									AutoLayoutCommand.getNode(as.getClassB(), omGraph));
							DiagramElement ele = new DiagramElement();
							ElementStyle style = new ElementStyle();
							ele.setObjectId(as.getId());
							ele.setStyle(style);
							dia.addElement(ele);
						}
					}
					omGraph.setLayoutAlgorithm(new CompositeLayoutAlgorithm(
							layoutStyle, new LayoutAlgorithm[] {
									new GXTreeLayoutAlgorithm(layoutStyle),
									new ShiftDiagramLayoutAlgorithm(layoutStyle) }),
							true);
					omGraph.applyLayoutInternal();

//					int x = 0;
					h += 20;
					y = h;
//					int l = x;
					for (Object node : omGraph.getNodes()) {
						if (node instanceof GraphNode) {
							if (((GraphNode) node).getData() instanceof BusinessClass) {
								BusinessClass e = (BusinessClass) ((GraphNode) node)
										.getData();
								int ph = 200;
								int px = ((GraphNode) node).getLocation().x;
								int py = ((GraphNode) node).getLocation().y;
								if (py + ph + h > y)
									y = py + ph + h;
								DiagramElement ele = new DiagramElement();
								ElementStyle style = new ElementStyle();
								ele.setObjectId(e.getId());
								style.setPositionX(px/* + l */);
								style.setPositionY(py + h);
								style.setHeight(200);
								style.setWidth(150);
								ele.setStyle(style);
								dia.addElement(ele);
								// x = Math.max(x, px + l + 150);
							}
						}
					}
					layoutNoConnNode(nonConBuList, dia, y);
				}

			}
			
			/**
			 * 布局没有关联关系的节点.
			 *
			 * @author mqfdy
			 * @param nonConBuList
			 *            the non con bu list
			 * @param dia
			 *            the dia
			 * @param y2
			 *            the y 2
			 * @Date 2018-09-03 09:00
			 */
			private void layoutNoConnNode(List<BusinessClass> nonConBuList,
					Diagram dia, int y2) {
				int x = 10;
				int num = 0;
				int maxHeight = 200;
				y = y2 + 20;
				// 布局没有关联关系的节点
				for (BusinessClass e : nonConBuList) {
					if (e instanceof BusinessClass) {
						if (num > 4) {
							y = y + 20 + maxHeight;
							maxHeight = 0;
							x = 10;
							num = 0;
						}
						DiagramElement ele = new DiagramElement();
						ElementStyle style = new ElementStyle();
						ele.setObjectId(e.getId());
						style.setPositionX(x);
						style.setPositionY(y);
						style.setHeight(200);
						style.setWidth(150);
						ele.setStyle(style);
						dia.addElement(ele);
						num++;
						x = x + 10 + 150;
					}
				}
			}
			
			/**
			 * Gets the diagram.
			 *
			 * @author mqfdy
			 * @param pkg
			 *            the pkg
			 * @return the diagram
			 * @Date 2018-09-03 09:00
			 */
			private Diagram getDiagram(ModelPackage pkg) {
				// TODO Auto-generated method stub
				for (AbstractModelElement ab : pkg.getChildren()) {
					if (ab instanceof Diagram) {
						return (Diagram) ab;
					}
				}
				return null;
			}
}
