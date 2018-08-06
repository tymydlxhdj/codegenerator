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
/**
 * 
 * @author mqf
 *
 */
public class ControllerClass extends AbstractBusinessClass {
	
	public ControllerClass(BusinessClass bc, IPersistenceModel persistenceModel, IProject project, BusinessObjectModel bom) {
		super(bc, persistenceModel, project,bom);
	}
	public ControllerClass(ClassParam param) {
		super(param);
	}
	/**
	 * 设置 Velocity Map
	 */
	public void putToVelocityMap() {
		putToVelocityMapDef();
		//自定义方法
		map.put("customMethods", customMethodSb.toString());
		//标准方法 判断
		map.put("hasSave", hasSave);
		map.put("hasDelete", hasDelete);
		map.put("hasQuery", hasQuery);
		map.put("hasGet", hasGet);
		//下拉数据 两种方式  枚举 和 来源于业务对象
		map.put("hasEnums", hasEnums);
		map.put("hasDb", hasDb);
		//数据字典列表
		//map.put("dictList", dictSb.toString());
		
	}

	@Override
	public void initPackage() {
		packageStr = packagePrefix + ".controller;";
	}

	@Override
	public void initFields() {
		
	}

	@Override
	public void initImports() {
		
		importSet.add("import java.util.List;\n");
		importSet.add("import org.springframework.beans.factory.annotation.Autowired;\n");
		importSet.add("import org.springframework.transaction.annotation.Transactional;\n");
		importSet.add("import org.springframework.web.bind.annotation.RequestMapping;\n");
		importSet.add("import org.springframework.web.bind.annotation.RequestMethod;\n");
		importSet.add("import org.springframework.web.bind.annotation.RestController;\n");
		importSet.add("import "+importPackage+".services.I"+bc.getName()+"Service;\n");
		importSet.add("import com.mqfdy.code.rest.annotation.ColumnRequestParam;\n");
		importSet.add("import com.mqfdy.code.rest.annotation.attribute.ViewAttributeData;\n");
		importSet.add("import com.mqfdy.code.rest.support.ViewMetaData;\n");
		importSet.add("import com.mqfdy.code.rest.support.WrappedResult;\n");
		importSet.add("import com.mqfdy.code.rest.utils.ViewAttributeUtils;\n");
		importSet.add("import org.slf4j.Logger;\n");
		importSet.add("import org.slf4j.LoggerFactory;\n");
		importSet.add("import org.springframework.web.bind.WebDataBinder;\n");
		importSet.add("import org.springframework.web.bind.annotation.InitBinder;\n");
		importSet.add("import com.mqfdy.code.exception.NullArgumentException;\n");
		importSet.add("import org.springframework.beans.factory.annotation.Value;\n");
		
		if(isTree){
			importSet.add("import com.mqfdy.code.rest.support.TreeNode;\n");
			importSet.add("import java.util.List;\n");
			importSet.add("import com.mqfdy.code.rest.utils.WrappedTreeNodes;\n");
		}
	}

	@Override
	public void initMethods() {
		List<BusinessOperation> operations = bc.getOperations();
		if(operations != null && !operations.isEmpty()){
			for(BusinessOperation bop : operations){
				if(BusinessOperation.OPERATION_TYPE_CUSTOM.equals(bop.getOperationType())){
					String customMethod = getCustomMethod(bop);
					if(!StringUtils.isEmpty(customMethod)){
						customMethodSb.append(customMethod);
					}
				}else{
					if(BusinessOperation.SYS_OPERATION_ADD.equals(bop.getOperationType()) || 
							BusinessOperation.SYS_OPERATION_EDIT.equals(bop.getName())){
						hasSave = true;
						importSet.add("import com.mqfdy.code.exception.NullArgumentException;\n");
						importSet.add("import com.mqfdy.code.rest.support.FormRequestObject;\n");
						importSet.add("import com.mqfdy.code.rest.support.QueryResultObject;\n");
						importSet.add("import org.springframework.web.bind.annotation.RequestBody;\n");
						importSet.add("import java.util.Map;\n");
						importSet.add("import com.mqfdy.code.service.validator.ServiceValidatorBaseException;\n");
					}else if(BusinessOperation.SYS_OPERATION_DELETE.equals(bop.getName())){
						hasDelete = true;
						importSet.add("import org.springframework.web.bind.annotation.RequestBody;\n");
						importSet.add("import com.mqfdy.code.rest.support.IDRequestObject;\n");
					}else if(BusinessOperation.SYS_OPERATION_GET.equals(bop.getName())){
						hasGet = true;
						importSet.add("import com.mqfdy.code.rest.support.QueryResultObject;\n");
						importSet.add("import org.springframework.web.bind.annotation.PathVariable;\n");
					}else if(BusinessOperation.SYS_OPERATION_QUERY.equals(bop.getName())){
						hasQuery = true;
						importSet.add("import com.mqfdy.code.rest.support.RequestCondition;\n");
						importSet.add("import com.mqfdy.code.rest.support.QueryResultObject;\n");
					}
				}
			}
		}
	}

	/**
	 * 获取自定义方法
	 */
	protected String getCustomMethod(BusinessOperation bop) {
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
		String returnTypeStr = returnType;
		if("void".equals(returnTypeStr)){
			returnTypeStr = "";
		}
		sb.append("     * @return "+ returnTypeStr + "\n");
		sb.append("     * @date " + this.getDate() + "\n");
		sb.append("     * @author " + this.getAuthor() + "\n");
		sb.append("     */\n");
		sb.append("    @RequestMapping(value = \"/"+bop.getName()+"\", method = RequestMethod.POST)\n");
		sb.append("    public ");
		
		sb.append(returnType);
		sb.append(" ");
		sb.append(bop.getName());
		sb.append("(");
		StringBuffer sbServiceParam = new StringBuffer();
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
				putToImportSet(param.getDataType());
				String dataType = getDataType(param.getDataType());
				if(bc.getName().equals(dataType)){
					sb.append("@RequestBody ");
					importSet.add("import org.springframework.web.bind.annotation.RequestBody;\n");
				}
				sb.append(dataType);
				sb.append(" ");
				sb.append(param.getName());
				sbServiceParam.append(param.getName());
				if(i < params.size()-1){
					sb.append(", ");
					sbServiceParam.append(", ");
				}
			}
		}
		sb.append("){\n");
		if(!"void".equals(returnType)){
			sb.append("        try {\n");
			sb.append("            logger.info(\""+bop.getDisplayName()+" 成功\");\n");
			sb.append("            return "+StringUtils.lowercaseFirstLetter(bc.getName())
			+"Service."+bop.getName()+"("+sbServiceParam.toString()+");\n");
			sb.append("        } catch (Exception e) {\n");
			String errorMessage = bop.getErrorMessage();
			if(StringUtils.isEmpty(errorMessage)){
				sb.append("            logger.error(e.getMessage(), e);\n");
			}else{
				errorMessage = errorMessage + ":";
				sb.append("            logger.error(\""+errorMessage+"\"+e.getMessage(), e);\n");
			}
			
			sb.append("            return null;\n");
			sb.append("        } \n");
		}
		sb.append("    }\n");
		return sb.toString();
	}
}
