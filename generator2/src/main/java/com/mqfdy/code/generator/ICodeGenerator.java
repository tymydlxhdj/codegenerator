package com.mqfdy.code.generator;

import com.mqfdy.code.model.BusinessObjectModel;

public interface ICodeGenerator {
	public void generateCode(BusinessObjectModel bom, MddConfiguration config);
}
