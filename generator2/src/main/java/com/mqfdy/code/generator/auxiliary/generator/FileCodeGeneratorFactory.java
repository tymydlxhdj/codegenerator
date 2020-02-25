package com.mqfdy.code.generator.auxiliary.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.GeneratorPlugin;
import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
import com.mqfdy.code.generator.auxiliary.ControllerClass;
import com.mqfdy.code.generator.auxiliary.DomainClass;
import com.mqfdy.code.generator.auxiliary.IServiceClass;
import com.mqfdy.code.generator.auxiliary.RepositoryClass;
import com.mqfdy.code.generator.auxiliary.ServiceClass;
import com.mqfdy.code.generator.auxiliary.TransferClass;
import com.mqfdy.code.generator.auxiliary.ValidatorClass;
import com.mqfdy.code.generator.auxiliary.VoClass;
import com.mqfdy.code.generator.auxiliary.convert.ConvertUtil;
import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.auxiliary.model.TemplateParam;
import com.mqfdy.code.generator.entity.FrontGenerator;
import com.mqfdy.code.generator.entity.JavaGenerator;
import com.mqfdy.code.generator.exception.CodeGeneratorException;
import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.ValidatorType;
import com.mqfdy.code.utils.PluginUtil;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating FileCodeGenerator objects.
 *
 * @author mqf
 */
public class FileCodeGeneratorFactory {
	
	/** classList. */
	@SuppressWarnings("rawtypes")
	private static List<Class> classList;
	static{
		classList = new ArrayList<>();
		classList.add(DomainClass.class);
		classList.add(VoClass.class);
		classList.add(TransferClass.class);
		classList.add(RepositoryClass.class);
		classList.add(IServiceClass.class);
		classList.add(ServiceClass.class);
		classList.add(ControllerClass.class);
		classList.add(ValidatorClass.class);
	}
	
	/**
	 * 方法描述：createGenerator.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @param bc bc
	 * @param project project
	 * @return List<AbstractGenerator>实例
	 * @Date 2018年8月31日 上午11:00:46
	 */
	public static List<AbstractGenerator> createGenerator(BusinessObjectModel bom,BusinessClass bc,IProject project){
		List<AbstractGenerator> gens = new ArrayList<AbstractGenerator>();
		IPersistenceModel persistenceModel = ConvertUtil.convertToPersistenceModel(bc,bom);
		for(Class<?> c : classList){
			AbstractGenerator gen = createGenerator(bc, persistenceModel, project,bom, c);
			gens.add(gen);
		}
		return gens;
	}
	
	/**
	 * 创建生成器.
	 *
	 * @author mqfdy
	 * @param param v
	 * @return List<AbstractGenerator>实例
	 * @Date 2018年8月31日 上午11:00:58
	 */
	public static List<AbstractGenerator> createGenerator(ClassParam param){
		List<AbstractGenerator> gens = new ArrayList<AbstractGenerator>();
		for(Class<?> c : classList){
			if(!ValidatorClass.class.equals(c)){
				AbstractGenerator gen = createGenerator(param, c);
				gens.add(gen);
			} else {
				List<AbstractGenerator> generatorList = createValidatorGenerators(param);
				if(generatorList != null && !generatorList.isEmpty()){
					gens.addAll(generatorList);
				}
			}
		}
		return gens;
	}
	
	/**
	 * 创建生成器.
	 *
	 * @author mqfdy
	 * @param param v
	 * @return List<AbstractGenerator>实例
	 * @Date 2018年8月31日 上午11:00:58
	 */
	public static List<AbstractGenerator> createJavaGenerators(ClassParam param,String templateType){
		if(templateType == null){
			templateType = "jpa";
		}
		List<AbstractGenerator> gens = new ArrayList<AbstractGenerator>();
		try {
			String scencePluginPath = PluginUtil
					.getPluginOSPath(GeneratorPlugin.PLUGIN_ID);
			File templateDir = new File(scencePluginPath + File.separator + "template"+File.separator + templateType);
			if(!templateDir.exists() && templateDir.isDirectory()){
				throw new CodeGeneratorException("模板异常，没有名为"+templateDir+"的模板");
			}
			File[] packageDirs = templateDir.listFiles();
			if(packageDirs != null){
				String templatePathPre = "template/" + templateType;
				List<AbstractGenerator> generators = createJavaGenerators(param, templatePathPre, packageDirs);
				gens.addAll(generators);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gens;
	}

	/**
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param param the param
	 * @param templateType the template type
	 * @param packageDirs the package dirs
	 * @return the list< abstract generator>
	 */
	public static List<AbstractGenerator> createJavaGenerators(ClassParam param, String templatePathPre,
			File[] files) {
		List<AbstractGenerator> generators = new ArrayList<AbstractGenerator>();
		for (File file : files) {
			if(file.isDirectory()){
				File[] templateFiles = file.listFiles();
				if(templateFiles != null){
					generators.addAll(createJavaGenerators(param,templatePathPre+"/"+file.getName(),templateFiles));
					/*for (File templateFile : templateFiles) {
						if(templateFile.exists()){
							AbstractGenerator gen = createJavaGenerator(param,templatePathPre+file.getName()+"/"+templateFile.getName());
							generators.add(gen);
						}
					}*/
				}
			} else{
				if(file.exists()&& file.getAbsolutePath().endsWith(".vm")){
					if(file.exists()&&!file.getName().contains("Validator")){
						AbstractGenerator gen = createJavaGenerator(param,templatePathPre+"/"+file.getName());
						generators.add(gen);
					}
				}
			}
			
			
		}
		return generators;
	}
	
	/**
	 * 创建校验生成器列表.
	 *
	 * @author mqfdy
	 * @param param param
	 * @return List<AbstractGenerator>实例
	 * @Date 2018年8月31日 上午11:01:07
	 */
	private static List<AbstractGenerator> createValidatorGenerators(ClassParam param) {
		BusinessClass bc = param.getBc();
		List<AbstractGenerator> validatorGenList = new ArrayList<AbstractGenerator>();
		if(bc != null){
			List<Property> pList = bc.getProperties();
			if(pList != null && !pList.isEmpty()){
				for(Property property : pList){
					List<Validator> vList = property.getValidators();
					if(vList != null && !vList.isEmpty()){
						for(Validator v : vList){
							if(ValidatorType.Custom.getValue().equals(v.getValidatorType())){
								AbstractJavaClass dc = new ValidatorClass(param, v);
								AbstractGenerator generator = new ValidatorGenerator(dc);
								validatorGenList.add(generator);
							}
						}
					}
				}
			}
		}
		return validatorGenList;
	}
	
	/**
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param param the param
	 * @param templatePath the template path
	 * @return the abstract generator
	 */
	public static AbstractGenerator createJavaGenerator(ClassParam param, String templatePath){
		return new JavaGenerator(param, templatePath);
	}
	
	/**
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param param the param
	 * @param templatePath the template path
	 * @return the abstract generator
	 *//*
	public static AbstractGenerator createJsGenerator(ClassParam param, String templatePath){
		return new JsGenerator(param, templatePath);
	}*/
	
	/**
	 * 创建代码文件生成器.
	 *
	 * @author mqfdy
	 * @param param param
	 * @param c c
	 * @return AbstractGenerator实例
	 * @Date 2018年8月31日 上午11:01:15
	 */
	private static AbstractGenerator createGenerator(ClassParam param, Class<?> c) {
		AbstractGenerator generator = null;
		AbstractJavaClass dc = null;
		if(DomainClass.class.equals(c)){
			dc = new DomainClass(param);
			generator = new DomainGenerator2(dc);
		}else if(VoClass.class.equals(c)){
			dc = new VoClass(param);
			generator = new VOGenerator(dc);
		}else if(TransferClass.class.equals(c)){
			dc = new TransferClass(param);
			generator = new TransferGenerator(dc);
		}else if(RepositoryClass.class.equals(c)){
			dc = new RepositoryClass(param);
			generator = new RepositoryGenerator(dc);
		}else if(ControllerClass.class.equals(c)){
			dc = new ControllerClass(param);
			generator = new ControllerGenerator(dc);
		}else if(IServiceClass.class.equals(c)){
			dc = new IServiceClass(param);
			generator = new IServiceGenerator(dc);
		}else if(ServiceClass.class.equals(c)){
			dc = new ServiceClass(param);
			generator = new ServiceGenerator(dc);
		}
		return generator;
	}

	/**
	 * 方法描述：createGenerator.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @param project project
	 * @return List<AbstractGenerator>实例
	 * @Date 2018年8月31日 上午11:01:40
	 */
	public static List<AbstractGenerator> createGenerator(BusinessObjectModel bom,IProject project){
		List<AbstractGenerator> generators = new ArrayList<AbstractGenerator>();
		if(bom != null && bom.getBusinessClasses() != null){
			for(BusinessClass bc : bom.getBusinessClasses()){
				List<AbstractGenerator> gens = createGenerator(bom,bc,project);
				generators.addAll(gens);
			}
		}
		return null;
	}
	
	/**
	 * 方法描述：createGenerator.
	 *
	 * @author mqfdy
	 * @param bc bc
	 * @param persistenceModel  persistenceModel
	 * @param project project
	 * @param bom bom
	 * @param c c
	 * @return AbstractGenerator实例
	 * @Date 2018年8月31日 上午11:01:47
	 */
	@SuppressWarnings("rawtypes")
	public static AbstractGenerator createGenerator(BusinessClass bc,IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom,Class c){
		AbstractGenerator generator = null;
		AbstractJavaClass dc = null;
		if(DomainClass.class.equals(c)){
			dc = new DomainClass(bc, persistenceModel, project,bom);
			generator = new DomainGenerator2(dc);
		}else if(VoClass.class.equals(c)){
			dc = new VoClass(bc, persistenceModel, project,bom);
			generator = new VOGenerator(dc);
		}else if(TransferClass.class.equals(c)){
			dc = new TransferClass(bc, persistenceModel, project,bom);
			generator = new TransferGenerator(dc);
		}else if(RepositoryClass.class.equals(c)){
			dc = new RepositoryClass(bc, persistenceModel, project,bom);
			generator = new RepositoryGenerator(dc);
		}else if(ControllerClass.class.equals(c)){
			dc = new ControllerClass(bc, persistenceModel, project,bom);
			generator = new ControllerGenerator(dc);
		}else if(IServiceClass.class.equals(c)){
			dc = new IServiceClass(bc, persistenceModel, project,bom);
			generator = new IServiceGenerator(dc);
		}else if(ServiceClass.class.equals(c)){
			dc = new ServiceClass(bc, persistenceModel, project,bom);
			generator = new ServiceGenerator(dc);
		}
		return generator;
	}
	
	/**
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param templateParamList the template param list
	 * @return the list< abstract generator>
	 */
	public static List<AbstractGenerator> createFrontGenerators(List<TemplateParam> templateParamList){
		List<AbstractGenerator> gens = new ArrayList<>();
		if(templateParamList != null && !templateParamList.isEmpty()){
			for (TemplateParam templateParam : templateParamList) {
				gens.add(createFrontGenerator(templateParam));
			}
		}
		return gens;
	}

	/**
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param templateParam the template param
	 * @return the abstract generator
	 */
	public static AbstractGenerator createFrontGenerator(TemplateParam templateParam) {
		return new FrontGenerator(templateParam);
	}
}
