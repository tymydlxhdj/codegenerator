package com.mqfdy.code.generator.auxiliary;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
// TODO: Auto-generated Javadoc

/**
 * The Class TransferClass.
 *
 * @author mqf
 */
public class TransferClass extends AbstractJavaClass {
	
	/** 包. */
	protected String packageStr;
	
	/**
	 * Instantiates a new transfer class.
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
	public TransferClass(BusinessClass bc, IPersistenceModel persistenceModel,IProject project, BusinessObjectModel bom){
		super(bc, persistenceModel, project,bom);
	}
	
	/**
	 * Instantiates a new transfer class.
	 *
	 * @param param
	 *            the param
	 */
	public TransferClass(ClassParam param) {
		super(param);
	}
	
	/**
	 * 初始化.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
	public void init(){
		initImports();
		initPackage();
	}
	
	/**
	 * 初始化Import.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
	public void initImports(){
		map.put("importStr1", "import "+importPackage+".domain."+bc.getName()+";\n");
	}
	
	/**
	 * 初始化包.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
	public void initPackage(){
		packageStr = packagePrefix + ".vo;";
		map.put("packageStr", packageStr+"\n");
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractJavaClass#initFields() TransferClass
	 */
	@Override
	public void initFields() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractJavaClass#initMethods() TransferClass
	 */
	@Override
	public void initMethods() {
		// TODO Auto-generated method stub
		
	}

}
