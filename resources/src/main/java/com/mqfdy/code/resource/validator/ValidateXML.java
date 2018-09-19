package com.mqfdy.code.resource.validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
// TODO: Auto-generated Javadoc

/**
 * 根据xsd校验xml.
 *
 * @author mqfdy
 */
public class ValidateXML {
	
	/** The flag. */
	private boolean flag = true;
	
	/** The exception string. */
	private StringBuffer exceptionString = new StringBuffer("\n");

	/**
	 * Instantiates a new validate XML.
	 */
	public ValidateXML() {

	}

	/**
	 * Gets the exception string.
	 *
	 * @author mqfdy
	 * @return the exception string
	 * @Date 2018-09-03 09:00
	 */
	public StringBuffer getExceptionString() {
		return exceptionString;
	}

	/**
	 * Validate xml.
	 *
	 * @author mqfdy
	 * @param xmlpath
	 *            the xmlpath
	 * @return true, if successful
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-09-03 09:00
	 */
	public boolean ValidateXml(String xmlpath) throws SAXException, IOException {
		// return Validatexml(xmlpath);
		return validateXmlByXsd(xmlpath);
	}

	/**
	 * Validatexml.
	 *
	 * @author mqfdy
	 * @param xmlpath
	 *            the xmlpath
	 * @return true, if successful
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-09-03 09:00
	 */
	public boolean Validatexml(String xmlpath) throws SAXException, IOException {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());// Platform.getBundle(BusinessModelEditorPlugin.PLUGIN_ID);

		Enumeration<URL> xsdEnum = bundle.findEntries("valiText",
				"Md-OM_Schema.xsd", false);
		// InputStream xsdStream = null;
		URL javaUrl = null;
		if (xsdEnum.hasMoreElements()) {
			javaUrl = (URL) xsdEnum.nextElement();
			// xsdStream = javaUrl.openStream();
		}
		// 建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		// File schemaFile = new File(xsdpath);
		// 利用schema工厂，接收验证文档文件对象生成Schema对象
		if(javaUrl==null){
			return false;
		}
		Schema schema = schemaFactory.newSchema(javaUrl);
		// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
		Validator validator = schema.newValidator();
		// 得到验证的数据源
		Source source = new StreamSource(xmlpath);
		// 开始验证，成功输出success!!!，失败输出fail
		try {
			validator.validate(source);
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (SAXException ex) {
			ex.printStackTrace();
			return false;
		} 
		return true;
	}

	/**
	 * 使用xsd验证xml.
	 *
	 * @author mqfdy
	 * @param xmlfilename
	 *            String xml文件 包括路径
	 * @return boolean 成功返回true 失败返回false
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-09-03 09:00
	 */
	public boolean validateXmlByXsd(String xmlfilename) throws IOException {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());// Platform.getBundle(BusinessModelEditorPlugin.PLUGIN_ID);

		Enumeration<URL> xsdEnum = bundle.findEntries("valiText",
				"Md-OM_Schema.xsd", false);
		InputStream xsdStream = null;
		URL javaUrl = null;
		if (xsdEnum.hasMoreElements()) {
			javaUrl = (URL) xsdEnum.nextElement();
			xsdStream = javaUrl.openStream();
		}
		// 建立schema工厂
		// SchemaFactory schemaFactory =
		// SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		// File schemaFile = new File(xsdpath);
		// 利用schema工厂，接收验证文档文件对象生成Schema对象
		// Schema schema = schemaFactory.newSchema(javaUrl);
		// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
		// Validator validator = schema.newValidator();

		File xmlfile = new File(xmlfilename);
		// File xsdfile = new File(xsdStream);
		// 检测原文件和验证文件是否存在
		if (!((xmlfile.exists())))
			return false;
		final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
		final String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		boolean validatesuccess = true;
		XMLReader xmlReader = null;
		
		try {
			SAXParser parser = factory.newSAXParser();
			parser.setProperty(SCHEMA_LANGUAGE, XML_SCHEMA);
			parser.setProperty(SCHEMA_SOURCE, xsdStream);
			
			xmlReader = parser.getXMLReader();
			xmlReader.setContentHandler(new DefaultHandler());
			xmlReader.setFeature("http://xml.org/sax/features/external-general-entities",
            		false);
			xmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities",
            		false);
			xmlReader.setErrorHandler(new ErrorHandler() {
				public void warning(SAXParseException exception)
						throws SAXException {
	//					System.out.println("警告");
				}
	
				public void fatalError(SAXParseException exception)
						throws SAXException {
					flag = false;
					exceptionString.append("致命:"
							+ exception.getLocalizedMessage() + "line:"
							+ exception.getLineNumber() + "\n");
	//					System.out.println("致命");
				}
	
				public void error(SAXParseException exception)
						throws SAXException {
					flag = false;
					// exception.printStackTrace();
					exceptionString.append("错误:"
							+ exception.getLocalizedMessage() + "line:"
							+ exception.getLineNumber() + "\n");
	//					System.out.println("错误:" + exception.getLocalizedMessage()
	//							+ "line:" + exception.getLineNumber());
				}
			});
			// XML存放地址
			xmlReader.parse(xmlfile.getAbsolutePath().toString());
		} catch (SAXNotRecognizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}