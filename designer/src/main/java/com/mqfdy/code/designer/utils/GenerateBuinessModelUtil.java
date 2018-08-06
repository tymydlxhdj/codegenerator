package com.mqfdy.code.designer.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.mqfdy.code.model.utils.DateTimeUtil;

public class GenerateBuinessModelUtil {

	/**
	 * 生成文件初始化内容
	 * 
	 * @param namespace
	 * 
	 * @return
	 */
	public static void generateFileContent(String momFilePath) {
		File file = new File(momFilePath);
		String fileName = file.getName().replace(".bom", "");
		String[] fileNameArray = fileName.split(".");
		String modelName = fileName; 
		String namespace = "";
		if( fileNameArray != null && fileNameArray.length > 1){
			modelName = fileNameArray[fileNameArray.length - 1];
			StringBuffer sbNamespace = new StringBuffer();
			for(int i =0;i <fileNameArray.length-1;i++){
				sbNamespace.append(fileNameArray[i]);
			}
			namespace = sbNamespace.toString();
		}
		Document document = new Document();

		Element modelElement = new Element("Model");
		modelElement.setAttribute("id", generateUUID());
		modelElement.setAttribute("name", modelName);
		modelElement.setAttribute("nameSpace", namespace);
		modelElement.setAttribute("displayName", modelName);

		// VersionInfo
		Element versionInfoElement = new Element("VersionInfo");
		versionInfoElement.setAttribute("id", generateUUID());
		versionInfoElement.addContent(new Element("Version").setText("1.0"));
		versionInfoElement.addContent(new Element("CreatedTime")
				.setText(DateTimeUtil.date2String(new Date())));
		versionInfoElement.addContent(new Element("ChangedTime")
				.setText(DateTimeUtil.date2String(new Date())));
		versionInfoElement.addContent(new Element("Creator").setText(System
				.getProperty("user.name")));
		versionInfoElement.addContent(new Element("Modifier").setText(System
				.getProperty("user.name")));
		versionInfoElement.addContent(new Element("Description")
				.setText("This is a demo model."));
		modelElement.addContent(versionInfoElement);

		// Package
		Element packageElement = new Element("Package");
		packageElement.setAttribute("id", generateUUID());
		packageElement.setAttribute("name", namespace);
		packageElement.setAttribute("displayName", modelName);
//		packageElement.addContent(new Element("BusinessClasses"));
//		packageElement.addContent(new Element("Associations"));
//		packageElement.addContent(new Element("Inheritances"));
//		packageElement.addContent(new Element("Annotations"));
//		packageElement.addContent(new Element("Links"));
		Element dia = new Element("Diagrams");
		Element diaele = new Element("Diagram");
		diaele.setAttribute("id", generateUUID());
		diaele.setAttribute("name", modelName);
		diaele.setAttribute("displayName", modelName);
		dia.addContent(diaele);
		Element diastyle = new Element("DiagramStyle");
		Element backGroundColor = new Element("BackGroundColor");
		Element gridStyle = new Element("GridStyle");
		Element zoomScale = new Element("ZoomScale");
		backGroundColor.addContent("white");
		gridStyle.addContent("false");
		zoomScale.addContent("100%");
		diastyle.addContent(backGroundColor);
		diastyle.addContent(gridStyle);
		diastyle.addContent(zoomScale);
		diaele.addContent(diastyle);
		packageElement.addContent(dia);
		modelElement.addContent(packageElement);

		document.setRootElement(modelElement);

		Format format = Format.getPrettyFormat();
		format.setEncoding("utf-8");
		format.setIndent("    ");
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			XMLOutputter xmlOutputter = new XMLOutputter(format);
			fos.write(xmlOutputter.outputString(document)
					.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
