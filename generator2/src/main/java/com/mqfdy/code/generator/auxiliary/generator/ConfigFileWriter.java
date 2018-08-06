package com.mqfdy.code.generator.auxiliary.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mqfdy.code.datasource.utils.PropertiesUtil;
import com.mqfdy.code.generator.GeneratorContext;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.utils.StringUtil;
/**
 * 
 * @author mqf
 *
 */
public class ConfigFileWriter {

	private static final String seperator = File.separator;

	public void writeEnumToProperties(String exportPath, BusinessObjectModel bom, GeneratorContext context) throws IOException {
		String[] paths = exportPath.split("java");
		String path = exportPath.replace(bom.getNameSpace().replace(".", File.separator), "");
		if(paths != null && paths.length >1){
			path = paths[0] +"resources" +seperator;
		}
		File enumDir = new File(path + "enum");
		File enumFile = new File(path + "enum" +seperator+"enums.properties");
		if(!enumDir.exists()){
			boolean isMade = enumDir.mkdir();
			if(isMade){
				context.print("\t创建enums目录成功", GeneratorContext.INFO);
			}
		}
		if (!enumFile.exists()) {
			boolean isCreated = enumFile.createNewFile();
			if(isCreated){
				context.print("\tenums.properties 文件生成完成", GeneratorContext.INFO);
			}
		}
		Map<String,String> prop = new HashMap<String,String>();
		List<Enumeration> enumerations = bom.getEnumerations();
		if(enumerations != null && !enumerations.isEmpty()){
			for(BusinessClass bc : bom.getBusinessClasses()){
				List<String> enumIds = getEnumIds(bc);
				if(enumIds != null && !enumIds.isEmpty()){
					for(String enumId : enumIds){
						for (Enumeration enumeration : enumerations) {
							if(enumId.equals(enumeration.getId())){
								String enumKey = bc.getName().toUpperCase(Locale.getDefault()) + "." +enumeration.getName().toUpperCase(Locale.getDefault());
								String enumValue = "";
							
								for (int i = 0; i < enumeration.getElements().size(); i++) {
									EnumElement element = enumeration.getElements().get(i);

									String k = element.getKey();
									String v = element.getValue();

									if (i < enumeration.getElements().size() - 1) {
										enumValue = enumValue + k + ":" + v + ",";
									} else {
										enumValue = enumValue + k + ":" + v;
									}
								}
								prop.put(enumKey, enumValue);
							}
						}
					}
				}
			}
			PropertiesUtil.save(enumFile,prop);
		}
		
		
		
	}
	/**
	 * 获取枚举id集合
	 * @param bc
	 * @return
	 */
	private List<String> getEnumIds(BusinessClass bc) {
		List<String> enumIds = new ArrayList<String>();
		for(Property p : bc.getProperties()){
			PropertyEditor editor = p.getEditor();
			int dsType = editor.getDataSourceType();
			if(PropertyEditor.DATASOURCE_TYPE_ENUM == dsType){
				// 枚举
				String dictEnumId = editor.getEditorParams().get(
						PropertyEditor.PARAM_KEY_ENUMERATION_ID);
				if (!StringUtil.isEmpty(dictEnumId)) {
					enumIds.add(dictEnumId);
				}
			
			}
			
		}
		return enumIds;
	}
}
