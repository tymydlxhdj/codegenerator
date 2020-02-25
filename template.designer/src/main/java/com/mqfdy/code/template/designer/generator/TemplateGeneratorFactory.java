/**
 * 
 */
package com.mqfdy.code.template.designer.generator;

/**
 * @author mqfdy
 *
 */
public class TemplateGeneratorFactory {
	
	
	public static ITemplateGenerator createTemplateGenerator(String type){
		
		if("velocity".equalsIgnoreCase(type)){
			return new VelocityTemplateGenerator();
		}
		return null;
	}

}
