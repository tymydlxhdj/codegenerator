package com.mqfdy.code.reconfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 受影响文件分析.
 *
 * @author mqfdy
 */
public class AffectedAnalyzer {

	/** The infos. */
	// 得到所有重构操作的集合
	private List<OperationInfo> infos;
	
	/** The project path. */
	// 工程路径
	private String projectPath;
	
	/** The namespace. */
	// 命名空间
	private String namespace;
	
	/** The model package name. */
	// 模型包名
	private String modelPackageName;

	/** The packagee. */
	// src下的包名
	private String packagee;

	/** The controller list. */
	// 工程路径下的所有生成Controller类文件集合
	private List<File> controllerList = new ArrayList<File>();
	
	/** The bizc interface list. */
	// 工程路径下的所有生成bizc接口文件集合
	private List<File> bizcInterfaceList = new ArrayList<File>();
	
	/** The bizc list. */
	// 工程路径下的所有生成bizc类文件集合
	private List<File> bizcList = new ArrayList<File>();
	
	/** The java list. */
	// 工程路径下的所有生成java类文件集合
	private List<File> javaList = new ArrayList<File>();
	
	/** The hbm list. */
	// 工程路径下的所有生成hbm文件集合
	private List<File> hbmList = new ArrayList<File>();
	
	/** The ddl list. */
	// ddl文件集合
	private List<File> ddlList = new ArrayList<File>();

	/** The associations. */
	private List<Association> associations;

	/**
	 * Instantiates a new affected analyzer.
	 *
	 * @param infos
	 *            the infos
	 * @param projectPath
	 *            the project path
	 * @param bom
	 *            the bom
	 */
	public AffectedAnalyzer(List<OperationInfo> infos, String projectPath,
			BusinessObjectModel bom) {
		this.infos = infos;
		this.projectPath = projectPath;
		this.namespace = bom.getNameSpace();
		this.modelPackageName = bom.getName();// 由于用了顶层modelpackage的名字来组装路径，而且顶层包名与bom名相同
		this.packagee = namespace + "." + modelPackageName;// 包名
		this.associations = bom.getAssociations();// 获得关联关系

		this.init();// 读取生成的代码文件
	}

	/**
	 * 初始化文件集合.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void init() {
		// 获取所有的Controller文件集合
		String controllerPath = projectPath + File.separator +"src" + File.separator
				+ namespace.replace(".", File.separator) + File.separator + modelPackageName + "\\";
		controllerList.addAll(StringUtil.listFile(controllerPath));

		// 获取所有的bizc接口，及bizc实现类文件
		String bizcPath = controllerPath + "bizc";
		for (File file : StringUtil.listFile(bizcPath)) {
			if ("interface".equals(ReconstructUtils.getFileType(file))) {// 如果是接口
				bizcInterfaceList.add(file);
			} else {// 其他的均为class
				bizcList.add(file);
			}
		}

		// 获取java类文件及其映射文件
		String javaPath = controllerPath + "po";
		for (File file : StringUtil.listFile(javaPath)) {
			if (file.getName().endsWith(".java")) {
				javaList.add(file);
			} else {
				hbmList.add(file);
			}
		}

		// 获取ddl文件
		String ddlPath = projectPath + "\\src\\ddl";
		ddlList.addAll(StringUtil.listFile(ddlPath));
	}

	/**
	 * 根据operationInfo集合分析受影响的文件及位置.
	 *
	 * @author mqfdy
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-09-03 09:00
	 */
	public void reconstruct() throws IOException {
		for (OperationInfo info : infos) {
			// 业务实体显示名改变
			if (OperationTarget.BusinessClass.DPNAME.equals(info
					.getOperationTarget())) {
				this.bcDisplayNameModified(info);
			}

			// 业务实体名称改变的重构
			if (OperationTarget.BusinessClass.BCNAME.equals(info
					.getOperationTarget())) {
				this.bcNameModified(info);
			}
		}
	}

	/**
	 * 业务实体显示名改变.
	 *
	 * @author mqfdy
	 * @param info
	 *            the info
	 * @Date 2018-09-03 09:00
	 */
	private void bcDisplayNameModified(OperationInfo info) {
		// 更改ddl中的表注释
		for (File file : ddlList) {
			ReconstructUtils.replaceFileLine(file,
					(String) info.getOriginalValue(),
					(String) info.getNewVlue(), Marks.tableComment
							+ info.getBelongBC().getTableName().toUpperCase(Locale.getDefault())
							+ " is", Marks.Num.ONE, Marks.Redirect.LOWER);
		}
		// 更改java中的类注释
		for (File file : javaList) {
			if (file.getName().equals(info.getBelongBC().getName() + ".java")) {
				ReconstructUtils.replaceFileAll(file,
						(String) info.getOriginalValue(),
						(String) info.getNewVlue(), false);
			}
		}
	}

	/**
	 * 业务实体名称被修改.
	 *
	 * @author mqfdy
	 * @param info
	 *            the info
	 * @Date 2018-09-03 09:00
	 */
	private void bcNameModified(OperationInfo info) {
		// 首先得到该业务实体文件
		for (File file : javaList) {
			if (file.getName().equals(info.getOriginalValue() + ".java")) {
				ReconstructUtils.replaceFileAll(file,
						(String) info.getOriginalValue(),
						(String) info.getNewVlue(), true);// 替换自己的java文件
			}
		}
		// 遍历关联关系（找出包含该业务实体引用的业务实体文件）
		for (Association association : associations) {
			if ((association.getClassA().getName()).equals(info
					.getOriginalValue())
					|| (association.getClassB().getName()).equals(info
							.getOriginalValue())) {

				// 如果是多对多关联关系
				if (association.getAssociationType().equals(
						AssociationType.mult2mult.getValue())) {
					if ((association.getClassA().getName()).equals(info
							.getOriginalValue())) {// 如果A端为本业务实体
						for (File file : javaList) {
							if (file.getName()
									.equals(association.getClassB().getName()
											+ ".java")) {
								ReconstructUtils.replaceFileAll(file,
										(String) info.getOriginalValue(),
										(String) info.getNewVlue(), false);// 替换多对多的java文件
								break;
							}
						}
					} else {
						for (File file : javaList) {
							if (file.getName()
									.equals(association.getClassA().getName()
											+ ".java")) {
								ReconstructUtils.replaceFileAll(file,
										(String) info.getOriginalValue(),
										(String) info.getNewVlue(), false);// 替换多对多的java文件
								break;
							}
						}
					}
				}
				// 如果是一对多关联关系
				if (association.getAssociationType().equals(
						AssociationType.one2mult.getValue())) {
					if ((association.getClassA().getName()).equals(info
							.getOriginalValue())) {// 本业务实体为一方
						if (association.isNavigateToClassA()) {// 不管单双向只要导航到A端，即包含一方的引用
							for (File file : javaList) {
								if (file.getName().equals(
										association.getClassA().getName()
												+ ".java")) {
									ReconstructUtils.replaceFileAll(file,
											(String) info.getOriginalValue(),
											(String) info.getNewVlue(), false);// 替换多方的java文件
									break;
								}
							}
						}
					} else {// 本业务实体为多方
						if (association.isNavigateToClassB()) {// 不管单双向只要导航到B端，即包含多方的引用
							for (File file : javaList) {
								if (file.getName().equals(
										association.getClassB().getName()
												+ ".java")) {
									ReconstructUtils.replaceFileAll(file,
											(String) info.getOriginalValue(),
											(String) info.getNewVlue(), false);// 替换多方的java文件
									break;
								}
							}
						}
					}
				}

				// 如果是多对一关联关系
				if (association.getAssociationType().equals(
						AssociationType.mult2one.getValue())) {
					if ((association.getClassA().getName()).equals(info
							.getOriginalValue())) {// 本业务实体为多方
						if (association.isNavigateToClassA()) {// 不管单双向只要导航到A端，即包含多方的引用
							for (File file : javaList) {
								if (file.getName().equals(
										association.getClassA().getName()
												+ ".java")) {
									ReconstructUtils.replaceFileAll(file,
											(String) info.getOriginalValue(),
											(String) info.getNewVlue(), false);// 替换多方的java文件
									break;
								}
							}
						}
					} else {
						if (association.isNavigateToClassB()) {// 不管单双向只要导航到B端，即包含一方的引用
							for (File file : javaList) {
								if (file.getName().equals(
										association.getClassB().getName()
												+ ".java")) {
									ReconstructUtils.replaceFileAll(file,
											(String) info.getOriginalValue(),
											(String) info.getNewVlue(), false);// 替换一方的java文件
									break;
								}
							}
						}
					}
				}

				// 如果是一对一关联关系
				if (association.getAssociationType().equals(
						AssociationType.one2one.getValue())) {
					if ((association.getClassA().getName()).equals(info
							.getOriginalValue())) {// 本业务实体在A端
						if (association.isNavigateToClassA()) {// 不管单双向只要导航到A端，即包含本业务实体的引用
							for (File file : javaList) {
								if (file.getName().equals(
										association.getClassA().getName()
												+ ".java")) {
									ReconstructUtils.replaceFileAll(file,
											(String) info.getOriginalValue(),
											(String) info.getNewVlue(), false);// 替换多方的java文件
									break;
								}
							}
						}
					} else {
						for (File file : javaList) {
							if (file.getName()
									.equals(association.getClassB().getName()
											+ ".java")) {
								ReconstructUtils.replaceFileAll(file,
										(String) info.getOriginalValue(),
										(String) info.getNewVlue(), false);// 替换多方的java文件
								break;
							}
						}
					}
				}
			}
		}
	}

}
