package com.mqfdy.code.generator.auxiliary;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Property;
// TODO: Auto-generated Javadoc

/**
 * The Class VoClass.
 *
 * @author mqf
 */
public class VoClass extends AbstractBusinessClass {
	
	/**
	 * Instantiates a new vo class.
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
	public VoClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		super(bc, persistenceModel, project,bom);
	}
	
	/**
	 * Instantiates a new vo class.
	 *
	 * @param param
	 *            the param
	 */
	public VoClass(ClassParam param) {
		super(param);
	}
	
	/**
	 * 初始化Import.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:31
	 */
	public void initImports(){
		for(Property p : bc.getProperties()){
			putToImportSet(p.getDataType());
		}
	}
	
	/**
	 * 初始化包.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:31
	 */
	public void initPackage(){
		packageStr = packagePrefix + ".vo;";
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initFields() VoClass
	 */
	@Override
	public void initFields() {
		importSet.add("import java.io.Serializable;\n");
		importSet.add("import com.sgcc.uap.rest.annotation.attribute.AttributeType;\n");
		importSet.add("import com.sgcc.uap.rest.annotation.attribute.EditorType;\n");
		importSet.add("import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;\n");
		importSet.add("import com.sgcc.uap.rest.support.ParentVO;\n");
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initMethods() VoClass
	 */
	@Override
	public void initMethods() {
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#getCustomMethod(com.mqfdy.code.model.BusinessOperation)
	 * @param bop
	 * @return VoClass
	 */
	@Override
	protected String getCustomMethod(BusinessOperation bop) {

		return null;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#putToVelocityMap() VoClass
	 */
	@Override
	public void putToVelocityMap() {
		putToVelocityMapDef();
	}

}
