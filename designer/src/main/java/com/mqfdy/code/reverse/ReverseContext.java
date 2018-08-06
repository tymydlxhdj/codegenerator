package com.mqfdy.code.reverse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Platform;

import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.mappings.View;
import com.mqfdy.code.reverse.views.pages.ReverseWizard;

/**
 * 缓存数据库连接
 * @author mqfdy
 */
public class ReverseContext {

//	public static Connection connection = null;
	public static ReverseWizard wizard = null;
	
	//uap的安装目录
	public final static String UAP_INSTALL_PATH = Platform.getInstallLocation().getURL().getFile();
	
	//缓存数据库所有表对象
	public static Map<String, Table> allTables = new HashMap<String, Table>();
	
	//缓存数据库所有视图对象
	public static Map<String, View> allViews = new HashMap<String, View>();
	
	//缓存选择的表
	public static Map<String, Table> selectedTables = new HashMap<String, Table>();
	
	//选择的表经过处理特殊表后缓存在此map中
	public static Map<String, Table> handleTables = new HashMap<String, Table>();
	//保存无主键表的集合
	public static Set<String> noPks = new HashSet<String>();
	
	//保存字段含有特殊字符的表的集合
	public static Set<String> specialChars = new HashSet<String>();
	//保存含有非法字段的表的集合
	public static Set<String> startWithFigures = new HashSet<String>();
	
	//去除重复表后得到的最终表集合
	public static Map<String, Table> lastTables = new HashMap<String, Table>();
	
	//缓存om对象
	public static BusinessObjectModel bom  = null;
	//如果选择了已经存在的om文件，缓存已经存在业务实体
	public static List<BusinessClass> existBcs = new ArrayList<BusinessClass>();
	public static List<String> bcIds = new ArrayList<String>();
	
	//缓存om目录
	public static String OM_STORAGE_PATH = null;
	
	//缓存table与bc的对应关系
	public static Map<Table, BusinessClass> tbMap = new HashMap<Table, BusinessClass>();
	public static Map<BusinessClass,Table> btMap = new HashMap<BusinessClass,Table>();
//	
//	//缓存column到property的缓存
//	public static Map<Column, Property> cpMap = new HashMap<Column, Property>();
	
	//缓存数据源信息
	public static DataSourceInfo info;
}
