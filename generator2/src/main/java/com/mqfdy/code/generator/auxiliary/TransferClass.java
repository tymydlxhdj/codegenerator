package com.mqfdy.code.generator.auxiliary;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
/**
 * 
 * @author mqf
 *
 */
public class TransferClass extends AbstractJavaClass {
	
	/**
	 * 包
	 */
	protected String packageStr;
	
	public TransferClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		super(bc, persistenceModel, project,bom);
	}
	public TransferClass(ClassParam param) {
		super(param);
	}
	/**
	 * 初始化
	 * @param bom
	 */
	public void init(){
		initImports();
		initPackage();
	}
	/**
	 * 初始化Import
	 */
	public void initImports(){
		map.put("importStr1", "import "+importPackage+".domain."+bc.getName()+";\n");
	}
	/**
	 * 初始化包
	 */
	public void initPackage(){
		packageStr = packagePrefix + ".vo;";
		map.put("packageStr", packageStr+"\n");
	}
	@Override
	public void initFields() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initMethods() {
		// TODO Auto-generated method stub
		
	}

}
