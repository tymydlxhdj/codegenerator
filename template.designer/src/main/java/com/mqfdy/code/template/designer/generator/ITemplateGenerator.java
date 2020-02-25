/**
 * 
 */
package com.mqfdy.code.template.designer.generator;

import com.mqfdy.code.template.designer.model.SceneTemplateModel;
import com.mqfdy.code.template.designer.model.TemplateModel;

/**
 * @author mqfdy
 *
 */
public interface ITemplateGenerator {
	
	public void generate(SceneTemplateModel sceneTemplateModel);
	
	public void generate(TemplateModel templateModel,String pakcagePre);

}
