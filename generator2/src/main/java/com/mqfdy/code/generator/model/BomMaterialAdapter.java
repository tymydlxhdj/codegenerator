/**
 * 
 */
package com.mqfdy.code.generator.model;

import java.util.Map;

import com.mqfdy.code.generator.auxiliary.generator.BOMGenerator;

/**
 * @author mqfdy
 *
 */
public class BomMaterialAdapter implements ICodeFileMaterial {
	
	/** The BOMGenerator. */
	private BOMGenerator generator;
	
	
	public BomMaterialAdapter(BOMGenerator generator){
		this.generator = generator;
	}

	@Override
	public String getTemplatePath() {
		
		return generator.getTemplatePath();
	}

	@Override
	public Map<String, Object> getVelocityMap() {
		return generator.getSourceMap();
	}

	@Override
	public String getOutputPath() {
		return generator.getOutputFolderPath();
	}

	@Override
	public String getFileName() {
		return generator.getFileNameWithoutExtension();
	}

	@Override
	public String getPre() {

		return generator.getFileNamePrefix();
	}

	@Override
	public String getSuf() {
		return generator.getFileNameSuffix();
	}

	@Override
	public String getExtension() {
		return generator.getFileExtension();
	}

}
