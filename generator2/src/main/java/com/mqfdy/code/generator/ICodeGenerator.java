package com.mqfdy.code.generator;

import java.util.List;

import com.mqfdy.code.generator.model.CodeFileMaterial;
import com.mqfdy.code.generator.model.CodeGenerationException;
import com.mqfdy.code.model.BusinessObjectModel;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICodeGenerator.
 *
 * @author mqfdy
 */
public interface ICodeGenerator {
	
	/**
	 * Generate code.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @param config
	 *            the config
	 * @Date 2018-09-03 09:00
	 */
	public void generateCode(BusinessObjectModel bom, MddConfiguration config);
	
}
