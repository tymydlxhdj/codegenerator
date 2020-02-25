package com.mqfdy.code.generator.auxiliary;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.utils.CodePropertiesUtil;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.ValidatorType;
// TODO: Auto-generated Javadoc

/**
 * The Class DomainClass.
 *
 * @author mqf
 */
public class DomainClass extends AbstractBusinessClass {
	
	/**
	 * Instantiates a new domain class.
	 *
	 * @param bc
	 *            the bc
	 * @param persistenceModel
	 *            the persistence model
	 * @param project
	 *            the project
	 * @param bom
	 *            the bom
	 */
	public DomainClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		super(bc, persistenceModel, project,bom);
	}
	
	/**
	 * Instantiates a new domain class.
	 *
	 * @param param
	 *            the param
	 */
	public DomainClass(ClassParam param) {
		super(param);
	}
	
	/**
	 * 初始化Import.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:36
	 */
	public void initImports(){
		for(Property p : bc.getProperties()){
			putToImportSet(p.getDataType());
			if(p instanceof PKProperty){
				if("sequence".equals(((PKProperty)p).getType())){
					importSet.add("import javax.persistence.SequenceGenerator;\n");
				}
			}
			initValidatorImports(p);
		}
	}
	
	/**
	 * 初始化.
	 *
	 * @author mqfdy
	 * @param p
	 *            the p
	 * @Date 2018-9-3 11:38:36
	 */
	private void initValidatorImports(Property p) {
		List<Validator> validators = p.getValidators();
		if(validators != null && !validators.isEmpty()){
			for(Validator v :validators){
				String suffix = "";
				if(ValidatorType.Nullable.getValue().equalsIgnoreCase(v.getValidatorType()) &&
						"string".equalsIgnoreCase(p.getDataType())){
					suffix = ".String";
				}
				importSet.add(CodePropertiesUtil.getValidatorTypeImport(v.getValidatorType()+suffix));
			}
		}
		
	}
	
	/**
	 * 初始化包.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:36
	 */
	public void initPackage(){
		packageStr = packagePrefix + ".domain;";
	}
	
	/**
	 * Inits the fields.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initFields()
	 *      DomainClass
	 */
	@Override
	public void initFields() {
		importSet.add("import java.io.Serializable;\n");
		importSet.add("import javax.persistence.Column;\n");
		importSet.add("import javax.persistence.Entity;\n");
		importSet.add("import javax.persistence.GeneratedValue;\n");
		importSet.add("import javax.persistence.Id;\n");
		importSet.add("import javax.persistence.Table;\n");
		importSet.add("import org.hibernate.annotations.GenericGenerator;\n");
		importSet.add("import javax.persistence.Transient;\n");

	}
	
	/**
	 * Inits the methods.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initMethods()
	 *      DomainClass
	 */
	@Override
	public void initMethods() {
	}
	
	/**
	 * Gets the custom method.
	 *
	 * @param bop
	 *            the bop
	 * @return DomainClass
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#getCustomMethod(com.mqfdy.code.model.BusinessOperation)
	 */
	@Override
	protected String getCustomMethod(BusinessOperation bop) {
	
		return null;
	}
	
	/**
	 * Put to velocity map.
	 *
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#putToVelocityMap()
	 *      DomainClass
	 */
	@Override
	public void putToVelocityMap() {
		putToVelocityMapDef();
	}
}
