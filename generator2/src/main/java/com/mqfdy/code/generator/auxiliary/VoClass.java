package com.mqfdy.code.generator.auxiliary;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Property;
/**
 * 
 * @author mqf
 *
 */
public class VoClass extends AbstractBusinessClass {
	
	public VoClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		super(bc, persistenceModel, project,bom);
	}
	public VoClass(ClassParam param) {
		super(param);
	}
	/**
	 * 初始化Import
	 */
	public void initImports(){
		for(Property p : bc.getProperties()){
			putToImportSet(p.getDataType());
		}
	}
	/**
	 * 初始化包
	 */
	public void initPackage(){
		packageStr = packagePrefix + ".vo;";
	}
	@Override
	public void initFields() {
		importSet.add("import java.io.Serializable;\n");
		importSet.add("import com.sgcc.uap.rest.annotation.attribute.AttributeType;\n");
		importSet.add("import com.sgcc.uap.rest.annotation.attribute.EditorType;\n");
		importSet.add("import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;\n");
		importSet.add("import com.sgcc.uap.rest.support.ParentVO;\n");
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
