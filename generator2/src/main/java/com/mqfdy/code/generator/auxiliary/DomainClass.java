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
/**
 * 
 * @author mqf
 *
 */
public class DomainClass extends AbstractBusinessClass {
	
	public DomainClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		super(bc, persistenceModel, project,bom);
	}
	public DomainClass(ClassParam param) {
		super(param);
	}
	/**
	 * 初始化Import
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
	 * 初始化
	 * @param p
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
	 * 初始化包
	 */
	public void initPackage(){
		packageStr = packagePrefix + ".domain;";
	}
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
	@Override
	public void initMethods() {
	}
	@Override
	protected String getCustomMethod(BusinessOperation bop) {
	
		return null;
	}
	@Override
	public void putToVelocityMap() {
		putToVelocityMapDef();
	}
}
