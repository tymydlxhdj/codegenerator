package com.mqfdy.code.generator.auxiliary;

import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.ClassParam;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.generator.utils.StringUtils;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.OperationParam;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.utils.StringUtil;
/**
 * 
 * @author mqf
 *
 */
public class ServiceClass extends AbstractBusinessClass {
	/**
	 * 数据字典Buffer
	 */
	private StringBuffer dictSb;
	/**
	 * 添加数据字典方法
	 */
	private String addDicts;
	/**
	 * 生成的getLt方法中 具体逻辑代码
	 */
	private StringBuffer returnSetSb;
	
	private String nodeText;
	
	
	public ServiceClass(BusinessClass bc, IPersistenceModel persistenceModel, IProject project, BusinessObjectModel bom) {
		super(bc, persistenceModel, project,bom);
	}

	public ServiceClass(ClassParam param) {
		super(param);
	}

	@Override
	public void initPackage() {
		packageStr = packagePrefix + ".services;";
	}

	@Override
	public void initFields() {
	
	}

	@Override
	public void initImports() {
		addDicts = ""; 
		nodeText = "";
		dictSb = new StringBuffer();
		returnSetSb = new StringBuffer();
		
		importSet.add("import "+importPackage+".repositories."+bc.getName()+"Repository;\n");
		
		importSet.add("import java.util.List;\n");
		importSet.add("import java.util.Optional;\n");
		importSet.add("import org.springframework.beans.factory.annotation.Autowired;\n");
		importSet.add("import org.springframework.data.domain.Page;\n");
		importSet.add("import org.springframework.data.domain.PageRequest;\n");
		importSet.add("import org.springframework.stereotype.Service;\n");
		importSet.add("import com.mqfdy.code.rest.support.SqlOperateType;\n");
		importSet.add("import org.springframework.web.client.RestClientException;\n");
		importSet.add("import com.mqfdy.code.rest.utils.ClassUtil;\n");
		
		importSet.add("import org.springframework.data.domain.Sort;\n");
		importSet.add("import org.springframework.data.domain.Sort.Direction;\n");
		importSet.add("import java.util.ArrayList;\n");
		importSet.add("import javax.persistence.criteria.CriteriaBuilder;\n");
		importSet.add("import javax.persistence.criteria.CriteriaQuery;\n");
		importSet.add("import javax.persistence.criteria.Predicate;\n");
		importSet.add("import com.mqfdy.code.rest.support.QueryFilter;\n");
		importSet.add("import javax.persistence.criteria.Root;\n");
		importSet.add("import javax.persistence.criteria.Path;\n");
		importSet.add("import org.springframework.data.jpa.domain.Specification;\n");
		importSet.add("import com.mqfdy.code.runtime.validate.ValidateService;\n");
		importSet.add("import com.mqfdy.code.utils.string.StringUtil;\n");
		if(isTree){
			importSet.add("import com.mqfdy.code.rest.support.TreeNode;\n");
			importSet.add("import "+importPackage+".domain."+bc.getName()+";\n");
			setTreeIdAndText();
		}
	}

	/**
	 * 设置树的id和text
	 */
	private void setTreeIdAndText() {
		String tempPkPName = null;
		for(Property p : bc.getProperties()){
			String pName = p.getName();
			if(pName.endsWith("name") || pName.endsWith("Name")){
				nodeText = pName;
				break;
			}
			if(p.isPrimaryKey()){
				tempPkPName = p.getName();
			}
			
		}
		if(StringUtils.isEmpty(nodeText)) {
			nodeText = tempPkPName;
		}
	}

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
						importSet.add("import com.mqfdy.code.rest.utils.CrudUtils;\n");
						importSet.add("import com.mqfdy.code.rest.utils.RestUtils;\n");
						importSet.add("import java.util.Map;\n");
					}else if(BusinessOperation.SYS_OPERATION_EDIT.equals(bop.getName())){
						hasSave = true;
						importSet.add("import com.mqfdy.code.rest.utils.CrudUtils;\n");
						importSet.add("import com.mqfdy.code.rest.utils.RestUtils;\n");
						importSet.add("import java.util.Map;\n");
					}else if(BusinessOperation.SYS_OPERATION_DELETE.equals(bop.getName())){
						hasDelete = true;
						importSet.add("import com.mqfdy.code.exception.NullArgumentException;\n");
						importSet.add("import com.mqfdy.code.rest.support.IDRequestObject;\n");
					}else if(BusinessOperation.SYS_OPERATION_GET.equals(bop.getName())){
						hasGet = true;
						importSet.add("import com.mqfdy.code.rest.support.QueryResultObject;\n");
						importSet.add("import "+importPackage+".domain."+bc.getName()+";\n");
					}else if(BusinessOperation.SYS_OPERATION_QUERY.equals(bop.getName())){
						hasQuery = true;
						importSet.add("import com.mqfdy.code.exception.NullArgumentException;\n");
						importSet.add("import com.mqfdy.code.rest.support.QueryResultObject;\n");
						importSet.add("import com.mqfdy.code.rest.support.RequestCondition;\n");
						importSet.add("import "+importPackage+".domain."+bc.getName()+";\n");
					}
				}
			}
		}
		initAssociations();
		initComboData();
	}

	private void initAssociations() {
		List<Association> assList = bom.getAssociations();
		if(assList != null && !assList.isEmpty()){
			for(Association ass : assList){
				if(ass.getClassAid().equals(bc.getId())){
					//cascadeDelete = ass.isCascadeDeleteClassB();
				}
				if(ass.getClassBid().equals(bc.getId())){
					//cascadeDelete = ass.isCascadeDeleteClassA();
				}
			}
			
		}
		
	}

	public String getCustomMethod(BusinessOperation bop) {
		importSet.add("import org.springframework.transaction.annotation.Transactional;\n");
		importSet.add("import org.springframework.transaction.annotation.Propagation;\n");
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
		sb.append("    @Transactional(propagation=Propagation."+bop.getTransactionType()+")\n");
		sb.append("    public ");
		
		sb.append(returnType);
		sb.append(" ");
		sb.append(bop.getName());
		sb.append("(");
		if(params != null && !params.isEmpty()){
			for(int i = 0; i < params.size();i++){
				OperationParam param = params.get(i);
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
		sb.append("){\n");
		if(!"void".equals(returnType)){
			sb.append("        return null;\n");
		}
		sb.append("    }\n");
		return sb.toString();
	}

	@Override
	public void putToVelocityMap() {
		putToVelocityMapDef();
		for(String field : fieldSet){
			fieldSb.append(field);
		}
		map.put("customMethods", customMethodSb.toString());
		map.put("hasSave", hasSave);
		map.put("hasDelete", hasDelete);
		map.put("hasQuery", hasQuery);
		map.put("hasGet", hasGet);
		map.put("addDicts",addDicts);
		map.put("dicts", dictSb.toString());
		map.put("fields", fieldSb.toString());
		map.put("hasDb", hasDb);
		map.put("hasEnum", hasEnums);
		map.put("returnSets", returnSetSb.toString());
		map.put("isSlave", isSlave);
		map.put("nodeText", nodeText);
	}
	/**
	 * 初始化下拉框数据
	 */
	private void initComboData() {
		for(Property p : bc.getProperties()){
			PropertyEditor editor = p.getEditor();
			int dsType = editor.getDataSourceType();
			if(PropertyEditor.DATASOURCE_TYPE_ENUM == dsType){
				// 枚举
				String dictEnumId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_ENUMERATION_ID);
				if (!StringUtil.isEmpty(dictEnumId)) {
					hasEnums = true;
					List<Enumeration> enums = bom.getEnumerations();
					String enumKey = "";
					for (Enumeration en : enums) {
						if (dictEnumId.equals(en.getId())) {
							enumKey = en.getName().toLowerCase(Locale.getDefault());
						}
					}
					dictSb.append("dicts.add(translateFromFile(\""
							+ p.getName() + "\","+enumKey+"));\n\t\t");
					//fieldSet 新增field
					fieldSet.add("@Value(\"${"+bc.getName().toUpperCase(Locale.getDefault())+"."+enumKey.toUpperCase(Locale.getDefault())+"}\")\n\tprivate String "+enumKey+";\n\t");
					importSet.add("import org.springframework.context.annotation.PropertySource;\n");
					importSet.add("import org.springframework.cloud.context.config.annotation.RefreshScope;\n");
					importSet.add("import java.util.Map;\n");
					importSet.add("import java.util.List;\n");
					importSet.add("import com.mqfdy.code.rest.utils.DictUtils;\n");
					importSet.add("import com.mqfdy.code.rest.support.DicItems;\n");
					importSet.add("import org.springframework.beans.factory.annotation.Value;\n");
				}
			
			}else if(PropertyEditor.DATASOURCE_TYPE_BUSINESS == dsType){
				hasDb = true;
				
				returnSetSb.append("if(\""+p.getName()+"\".equals(fieldName)){\n");
				returnSetSb.append("\t\t\treturn "+StringUtils.lowercaseFirstLetter(bc.getName())+"Repository.translate"+StringUtils.capitalizeFirstLetter(p.getName())+"FromDB();\n");
				returnSetSb.append("\t\t}\n\t\t");
				
				dictSb.append("dicts.add(translateFromDB(\""
						+ p.getName()
						+ "\"));\n\t\t");
				importSet.add("import java.util.Iterator;\n");
				importSet.add("import com.mqfdy.code.rest.utils.DictUtils;\n");
				importSet.add("import java.util.Map;\n");
				importSet.add("import java.util.LinkedList;\n");
				importSet.add("import java.util.List;\n");
				importSet.add("import com.mqfdy.code.rest.support.DicItems;\n");
			}
			
		}
		setAddDicts();
	}
	
	private void setAddDicts() {
		if((hasDb || hasEnums)){
			addDicts = ".addDicItems(wrapDictList())";
			importSet.add("import java.util.ArrayList;\n");
			importSet.add("import java.util.List;\n");
			importSet.add("import com.mqfdy.code.rest.support.DicItems;\n");
		}
		
	}
}
