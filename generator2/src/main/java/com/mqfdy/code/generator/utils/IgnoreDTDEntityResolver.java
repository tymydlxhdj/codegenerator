package com.mqfdy.code.generator.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class IgnoreDTDEntityResolver.
 *
 * @author mqfdy
 */
public class IgnoreDTDEntityResolver implements EntityResolver{

	/**
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws SAXException
	 * @throws IOException IgnoreDTDEntityResolver
	 */
	public InputSource resolveEntity(String arg0, String arg1)
			throws SAXException, IOException {
		// TODO Auto-generated method stub
		return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
	}

}
