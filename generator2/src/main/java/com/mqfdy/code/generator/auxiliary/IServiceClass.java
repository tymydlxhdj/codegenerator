package com.mqfdy.code.generator.auxiliary;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.OperationParam;
// TODO: Auto-generated Javadoc

/**
 * The Class IServiceClass.
 *
 * @author mqf
 */
public class IServiceClass extends AbstractBusinessClass {

	/**
	 * Instantiates a new i service class.
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
	public IServiceClass(BusinessClass bc, IPersistenceModel persistenceModel, IProject project, BusinessObjectModel bom) {
		super(bc, persistenceModel, project,bom);
	}

	/**
	 * Instantiates a new i service class.
	 *
	 * @param param
	 *            the param
	 */
	public IServiceClass(ClassParam param) {
		super(param);
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initPackage() IServiceClass
	 */
	@Override
	public void initPackage() {
		packageStr = packagePrefix + ".services;";
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initFields() IServiceClass
	 */
	@Override
	public void initFields() {
		
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initImports() IServiceClass
	 */
	@Override
	public void initImports() {
		if(isTree){
			importSet.add("import com.mqfdy.code.rest.support.TreeNode;\n");
			importSet.add("import java.util.List;\n");
		}
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#initMethods() IServiceClass
	 */
	@Override
	public void initMethods() {
		//方法
		List<BusinessOperation> operations = bc.getOperations();
		if(operations != null && !operations.isEmpty()){
			for(BusinessOperation bop : operations){
				if(BusinessOperation.OPERATION_TYPE_CUSTOM.equals(bop.getOperationType())){
					String customMethod = getCustomMethod(bop);
					if(!StringUtils.isEmpty(customMethod)){
						customMethodSb.append(customMethod);
					}
				}else{
					if(BusinessOperation.SYS_OPERATION_ADD.equals(bop.getOperationType())){
						hasSave = true;
						importSet.add("import java.util.Map;\n");
						importSet.add("import "+importPackage+".domain."+bc.getName()+";\n");
					}else if(BusinessOperation.SYS_OPERATION_EDIT.equals(bop.getName())){
						hasSave = true;
						importSet.add("import java.util.Map;\n");
						importSet.add("import "+importPackage+".domain."+bc.getName()+";\n");
					}else if(BusinessOperation.SYS_OPERATION_DELETE.equals(bop.getName())){
						hasDelete = true;
						importSet.add("import com.mqfdy.code.rest.support.IDRequestObject;\n");
					}else if(BusinessOperation.SYS_OPERATION_GET.equals(bop.getName())){
						hasGet = true;
						importSet.add("import com.mqfdy.code.rest.support.QueryResultObject;\n");
					}else if(BusinessOperation.SYS_OPERATION_QUERY.equals(bop.getName())){
						hasQuery = true;
						importSet.add("import com.mqfdy.code.rest.support.QueryResultObject;\n");
						importSet.add("import com.mqfdy.code.rest.support.RequestCondition;\n");
					}
				}
			}
		}
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#getCustomMethod(com.mqfdy.code.model.BusinessOperation)
	 * @param bop
	 * @return IServiceClass
	 */
	public String getCustomMethod(BusinessOperation bop) {
		StringBuffer sb = new StringBuffer();
		List<OperationParam> params = bop.getOperationParams();
		sb.append("    /**\n");
		sb.append("     * @"+bop.getName()+":");
		if(StringUtils.isEmpty(bop.getRemark())){
			sb.append(bop.getDisplayName());
		}else{
			sb.append(bop.getRemark());
		}
		sb.append("\n");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				sb.append("     * @param " + param.getName() +" " + param.getDisplayName());
				sb.append("\n");
			}
		}
		putToImportSet(bop.getReturnDataType());
		String returnType = getDataType(bop.getReturnDataType());
		sb.append("     * @return "+ returnType + "\n");
		sb.append("     * @date " + this.getDate() + "\n");
		sb.append("     * @author " + this.getAuthor() + "\n");
		sb.append("     */\n");
		sb.append("    public ");
		
		sb.append(returnType);
		sb.append(" ");
		sb.append(bop.getName());
		sb.append("(");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				//新增import，并返回java类型
				putToImportSet(param.getDataType());
				String paramType = getDataType(param.getDataType());
				sb.append(paramType);
				sb.append(" ");
				sb.append(param.getName());
				if(i < params.size()-1){
					sb.append(", ");
				}
			}
		}
		sb.append(");\n\n");
		return sb.toString();
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.AbstractBusinessClass#putToVelocityMap() IServiceClass
	 */
	@Override
	public void putToVelocityMap() {
		putToVelocityMapDef();
		map.put("hasSave", hasSave);
		map.put("hasDelete", hasDelete);
		map.put("hasQuery", hasQuery);
		map.put("hasGet", hasGet);
		map.put("customMethods", customMethodSb.toString());
	}
}
