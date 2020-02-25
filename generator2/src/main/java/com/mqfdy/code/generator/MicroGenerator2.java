package com.mqfdy.code.generator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2x.Hbm2DDLExporter;

import com.mqfdy.code.generator.auxiliary.generator.ConfigFileWriter;
import com.mqfdy.code.generator.auxiliary.generator.DdlGenerator;
import com.mqfdy.code.generator.auxiliary.generator.FileCodeGeneratorFactory;
import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.exception.CodeGeneratorException;
import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.model.CodeFileMaterial;
import com.mqfdy.code.generator.model.CodeGenerationException;
import com.mqfdy.code.generator.model.IGenerator;
import com.mqfdy.code.generator.utils.GeneratorUitl;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroGenerator2.
 *
 * @author mqfdy
 */
public class MicroGenerator2 implements ICodeGenerator {
	
	/** hbmGenerator. */
	private DdlGenerator hbmGenerator;
	
	/** project. */
	private IProject project;

	/** packageName. */
	private String packageName;

	private String templateType;
	
	/** option. */
	public static int option = 0;   //用于标记是否全部不覆盖(0：覆盖;1:不覆盖;2:全部覆盖;-1:用户点击关闭按钮，执行全部不覆盖)
	
	/** overrideAll. */
	public static boolean overrideAll = false;
	
	/** pkTypeMap. */
	private static Map<String,String> pkTypeMap;
	static{
		pkTypeMap = new HashMap<String, String>();
		pkTypeMap.put("ASSIGNED", "assiged");
		pkTypeMap.put("IDENTIFY", "identity");
		pkTypeMap.put("UUID", "uuid");  
		pkTypeMap.put("SEQUENCE", "sequence");
	}
	
	/**
	 * Instantiates a new micro generator 2.
	 *
	 * @param project project
	 * @param option option
	 * @param packageName packageName
	 */
	public MicroGenerator2(IProject project, int option,String packageName) {
		this.project = project;
		MicroGenerator2.option = option;
		this.packageName = packageName;
	}
	/**
	 * Instantiates a new micro generator 2.
	 *
	 * @param project project
	 * @param option option
	 * @param packageName packageName
	 */
	public MicroGenerator2(IProject project, int option,String packageName,String templateType) {
		this(project, option, packageName);
		this.templateType = templateType;
	}
	
	/**
	 * 方法描述：generateCode.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @param config  config
	 * @Date 2018年8月31日 上午10:30:01
	 */
	@Override
	public void generateCode(BusinessObjectModel bom, MddConfiguration config) {
		String exportPath = config.getExportPath() + bom.getNameSpace().replace(".", "\\") +File.separator+bom.getName();
		Map<String, Object> map = config.getMap();
		GeneratorContext context = config.getContext();
		//context.print("\t生成代码文件路径：" + exportPath, GeneratorContext.INFO);
		boolean generatePO = true;
		boolean generateDDL = map.get("ddl")==null?false:(Boolean) map.get("ddl");
		boolean generateFk = map.get("generateFk")==null?false:(Boolean) map.get("generateFk");
		boolean isOverride = false;// true标志直接覆盖不给提示，false表示遇到同名文件提示框
		boolean generateConfig = true;
		context.print("生成文件的路径："+ exportPath, GeneratorContext.INFO);
		List<String> existFiles = GeneratorUitl.getAllFilesInTheDir(config.getExportPath());
		//context.print("\t生成代码路径下存在的文件：" + existFiles, GeneratorContext.INFO);
		overrideAll = false;

		if(option<0){
			return;
		}
		if(generatePO){
			context.print("生成微服务后端代码:", GeneratorContext.INFO);
			generateJava(bom,existFiles,isOverride,config);
		}
		if (generateConfig) {
			try {
				context.print("生成配置文件:", GeneratorContext.INFO);
				ConfigFileWriter configFileWriter = new ConfigFileWriter();
				configFileWriter.writeEnumToProperties(exportPath, bom, context);
			} catch (IOException e) {
				throw new CodeGeneratorException("配置文件创建错误", e);
			} catch (Exception e) {
				throw new CodeGeneratorException("配置文件解析错误", e);
			}
		}
		
		if (generateDDL) {
			// 创建ddl
			String path = config.getExportPath() + bom.getNameSpace();
			generateDDL(bom, config, path, map, context, generateFk, isOverride, existFiles);
		}
		

	}
	
	/**
	 * 方法描述：generateDDL.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @param config config
	 * @param exportPath exportPath
	 * @param map  map
	 * @param context context
	 * @param generateFk generateFk
	 * @param isOverride isOverride
	 * @param existFiles existFiles
	 * @Date 2018年8月31日 上午10:30:16
	 */
	private void generateDDL(BusinessObjectModel bom, MddConfiguration config, String exportPath,
			Map<String, Object> map, GeneratorContext context, boolean generateFk, boolean isOverride,
			List<String> existFiles) {
		List<BusinessClass> bcList = bom.getBusinessClasses();
		if(bcList != null && !bcList.isEmpty()){
			context.print("生成ddl文件:", GeneratorContext.INFO);
			String dbType = (String) map.get("dbType");
			
			String ddlFileName = bom.getFullName()+ "_" + dbType + ".DDL";
			if (overrideAll == false && isOverride == false) {// 提示覆盖
				if (existFiles.contains(ddlFileName)) {// 已存在

					int option = context.confirm(ddlFileName + " 文件已经存在，是否覆盖？");
					if(option==-1){
						return;
					}
					if (option == 0 || option == 2) {// 覆盖
						this.generateDDL(bom, dbType, exportPath);
						context.print("\t" + ddlFileName +" 文件生成完成", GeneratorContext.INFO);
						if (option == 2) {
							overrideAll = true;
						}
					}
				} else {// 不存在
					this.generateDDL(bom, dbType, exportPath);
					context.print("\t" + ddlFileName + " 文件生成完成", GeneratorContext.INFO);
				}
			} else {// 直接覆盖
				this.generateDDL(bom, dbType, exportPath);
				context.print("\t" + ddlFileName + " 文件生成完成", GeneratorContext.INFO);
			}
			
			String ddlFilePath = config.getExportPath() + "ddl" + File.separator + bom.getFullName() + "_" + (String) map.get("dbType") + ".ddl";
			
			// 删除生成的引用业务实体的相关ddl语句
			GeneratorUitl.deleteSentencesFromDDL(bom, this.hbmGenerator.getReferenceSet(), ddlFilePath, "alter table ");
			
			// 删除生成的反向业务实体的相关ddl语句
			GeneratorUitl.deleteSentencesFromDDL(bom, this.hbmGenerator.getReverseSet(), ddlFilePath, "alter table ");
			
			if (!generateFk) {
				GeneratorUitl.deleteSentencesFromDDL(bom, this.hbmGenerator.getReferenceSet(), ddlFilePath, null);
				GeneratorUitl.deleteSentencesFromDDL(bom, this.hbmGenerator.getReverseSet(), ddlFilePath, null);
			}
		}
	}
	
	/**
	 * 方法描述：generateJava.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @param existFiles existFiles
	 * @param isOverride isOverride
	 * @param config context
	 * @Date 2018年8月31日 上午10:30:44
	 */
	public void generateJava(BusinessObjectModel bom, List<String> existFiles, boolean isOverride,MddConfiguration config) {
		List<BusinessClass> bcList = bom.getBusinessClasses();
		if(bcList != null && !bcList.isEmpty()){
			for(BusinessClass bc : bcList){
				generateCode(bom,existFiles, isOverride, config, bc);
			}
		}
	}
	
/*	*//**
	 * 方法描述：generateJavaSingle.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @param bcList bcList
	 * @param existFiles existFiles
	 * @param isOverride isOverride
	 * @param context context
	 * @Date 2018年8月31日 上午10:30:57
	 *//*
	public void generateJavaSingle(BusinessObjectModel bom, List<BusinessClass> bcList,List<String> existFiles, boolean isOverride,GeneratorContext context) {
		if(bcList != null && !bcList.isEmpty()){
			for(BusinessClass bc : bcList){
				generateCode(bom,existFiles, isOverride, context, bc);
			}
		}
	}*/
	
	/**
	 * 方法描述：generateCode.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @param existFiles existFiles
	 * @param isOverride isOverride
	 * @param config context
	 * @param bc bc
	 * @Date 2018年8月31日 上午10:31:15
	 */
	private void generateCode(BusinessObjectModel bom,List<String> existFiles, boolean isOverride, MddConfiguration config, BusinessClass bc) {
		ClassParam param = new ClassParam(bc, project, bom,(String) config.getMap().get("dbType"));
		List<AbstractGenerator> generators = FileCodeGeneratorFactory.createJavaGenerators(param,this.templateType);
		//List<AbstractGenerator> generators = FileCodeGeneratorFactory.createGenerator(param);
		//List<AbstractGenerator> generators = CodeGeneratorFactory.createGenerator(bom,bc, project);
		GeneratorContext context = config.getContext();
		context.print("已经存在的文件："+ Arrays.toString(existFiles.toArray()), GeneratorContext.INFO);
		for(IGenerator gen : generators){
			try {
				String fileName = gen.getFileName()+".java";
				context.print("需要生成的文件："+ fileName, GeneratorContext.INFO);
				context.print("是否包含该文件："+ !existFiles.contains(fileName), GeneratorContext.INFO);
				context.print("是否全覆盖："+ overrideAll, GeneratorContext.INFO);
				if(!existFiles.contains(fileName) ||overrideAll){
					gen.generate();
					context.print("\t"+fileName + "生成完成", GeneratorContext.INFO);
				}else{
					int option = context.confirm(fileName+ "文件已经存在，是否覆盖");
					overrideAll = option==2;
					if(option == 0 || overrideAll){
						gen.generate();
						context.print("\t"+fileName + "生成完成", GeneratorContext.INFO);
					}
				}
			} catch (CodeGenerationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据数据库类型生成相应的ddl脚本文件.
	 *
	 * @author mqfdy
	 * @param bom            业务模型对象
	 * @param dbType            指定数据库
	 * @param exportDDLDir            生成路径
	 * @Date 2018年8月31日 上午10:31:43
	 */
	private void generateDDL(BusinessObjectModel bom, String dbType, String exportDDLDir) {
		if (this.hbmGenerator == null) {// 如果不为null说明hbm已经执行了一遍
			this.hbmGenerator = new DdlGenerator();
			hbmGenerator.init(bom, exportDDLDir, dbType,true);
		}
		hbmGenerator.buildAssociation(hbmGenerator.map, bom,false);// 关联关系
		Configuration configuration = this.hbmGenerator.getHbmConfiguration();

		File ddlDirFile = new File(StringUtil.getPorjectSourcePath(exportDDLDir) + "ddl");
		if (!ddlDirFile.exists()) {
			ddlDirFile.mkdirs();
		}
		
		Hbm2DDLExporter ddlExport = new Hbm2DDLExporter(configuration, ddlDirFile);
		ddlExport.setExport(false);
		ddlExport.setDrop(true);
		ddlExport.setCreate(true);
		ddlExport.setConsole(true);
		ddlExport.setFormat(true);
		ddlExport.setOutputFileName(bom.getFullName()+ "_" + dbType + ".DDL");
		configuration.getProperties().put("hibernate.dialect", DBType.getDialect(dbType));// "org.hibernate.dialect.Oracle9Dialect"
		ddlExport.start();
	}



}
