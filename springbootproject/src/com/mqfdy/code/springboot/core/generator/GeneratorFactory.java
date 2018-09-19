package com.mqfdy.code.springboot.core.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.model.GradleGenerator;
import com.mqfdy.code.springboot.core.generator.model.JavaGenerator;
import com.mqfdy.code.springboot.core.generator.model.PomGenerator;
/**
 * 生成器工厂
 * @author mqfdy
 *
 */
public class GeneratorFactory {
	
	/**
	 * 生成.
	 *
	 * @param genProject
	 *            the gen project
	 * @param basePackage
	 *            the base package
	 * @param projectType
	 *            the project type
	 * @return the list< abstract generator>
	 */
	public static List<AbstractGenerator> createGenerators(IProject genProject,String basePackage, String projectType){
		List<AbstractGenerator> gens = new ArrayList<AbstractGenerator>();
		gens.add(new JavaGenerator(genProject, basePackage, TemplateConstantMessages.application.trim(),
				TemplateConstantMessages.template_application.trim()));
		gens.add(new JavaGenerator(genProject, basePackage, TemplateConstantMessages.corsconfig.trim(),
				TemplateConstantMessages.template_corsconfig.trim()));
		if (TemplateConstantMessages.GRADLE_PROJECT_TYPE.equalsIgnoreCase(projectType)) {
			gens.add(new GradleGenerator(genProject, basePackage, TemplateConstantMessages.build.trim(),
					TemplateConstantMessages.template_build.trim()));
			gens.add(new GradleGenerator(genProject, basePackage, TemplateConstantMessages.settings.trim(),
					TemplateConstantMessages.template_settings.trim()));
		} else {
			gens.add(new PomGenerator(genProject, basePackage));
		}

		return gens;
	}

}
