package com.mqfdy.code.generator.auxiliary.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

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
import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.ValidatorType;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating FileCodeGenerator objects.
 *
 * @author mqf
 */
public class FileCodeGeneratorFactory {
	
	/** The class list. */
	@SuppressWarnings("rawtypes")
	private static List<Class> classList;
	static{
		classList = new ArrayList<Class>();
		classList.add(DomainClass.class);
		classList.add(RepositoryClass.class);
		classList.add(IServiceClass.class);
		classList.add(ServiceClass.class);
		classList.add(ControllerClass.class);
		classList.add(ValidatorClass.class);
	}
	
	/**
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param bom
	 *            the bom
	 * @param bc
	 *            the bc
	 * @param project
	 *            the project
	 * @return the list< abstract generator>
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
	 * @param param
	 *            the param
	 * @return the list< abstract generator>
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
	 * 创建校验生成器列表.
	 *
	 * @param param
	 *            the param
	 * @return the list< abstract generator>
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
	 * 创建代码文件生成器.
	 *
	 * @param param
	 *            the param
	 * @param c
	 *            the c
	 * @return the abstract generator
	 */
	private static AbstractGenerator createGenerator(ClassParam param, Class<?> c) {
		AbstractGenerator generator = null;
		AbstractJavaClass dc = null;
		if(DomainClass.class.equals(c)){
			dc = new DomainClass(param);
			generator = new DomainGenerator2(dc);
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
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param bom
	 *            the bom
	 * @param project
	 *            the project
	 * @return the list< abstract generator>
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
	 * Creates a new FileCodeGenerator object.
	 *
	 * @param bc
	 *            the bc
	 * @param persistenceModel
	 *            the persistence model
	 * @param project
	 *            the project
	 * @param bom
	 *            the bom
	 * @param c
	 *            the c
	 * @return the abstract generator
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
}
