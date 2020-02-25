/**
 * 
 */
package com.mqfdy.code.template.designer.generator;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.mqfdy.code.template.designer.model.SceneTemplateModel;
import com.mqfdy.code.template.designer.model.TemplateModel;

/**
 * @author mqfdy
 *
 */
public class VelocityTemplateGenerator implements ITemplateGenerator {
	
	
	public VelocityTemplateGenerator(){
		
	}

	/**
	 * generate
	 * @date 2019年6月5日 上午11:30:11
	 * @author mqfdy
	 */
	public void generate(SceneTemplateModel sceneTemplateModel) {
		String sourceCodePath = sceneTemplateModel.getSourceCodePath();
		File fr = new File(sourceCodePath);
		List<TemplateModel> templateModelList = new ArrayList<TemplateModel>();
		generateATemplateModel(templateModelList, sceneTemplateModel, fr);
		for(TemplateModel tm : templateModelList){
			generate(tm,sceneTemplateModel.getPackagePre());
		}
	}
	
	private void generateTemplateModels(File directory, List<TemplateModel> templateModelList,SceneTemplateModel sceneTemplateModel) {
		
		File[] files = directory.listFiles();
		for(File fr :files){
			generateATemplateModel(templateModelList, sceneTemplateModel, fr);
		}
	}

	private void generateATemplateModel(List<TemplateModel> templateModelList, SceneTemplateModel sceneTemplateModel, File fr) {
		if(!fr.exists()){
			
		} else if(fr.isDirectory()){
			generateTemplateModels(fr,templateModelList,sceneTemplateModel);
		} else if(fr.isFile()){
			TemplateModel tm = new TemplateModel();
			String outputPath = sceneTemplateModel.getOutputPath();
			tm.setPackageSuf("packageSuf");
			tm.setSourceCodePath(fr.getAbsolutePath());
			String fileName = fr.getName();
			String[] fileNameArray = fileName.split(".");
			fileName = fileNameArray + ".vm";
			tm.setOutputPath(outputPath+File.separator + fileName);
			templateModelList.add(tm);
			
		}
	}

	public void generate(TemplateModel templateModel,String pakcagePre) {
		if(!templateModel.getSourceCodePath().endsWith("java")){
			return;
		}
		CompilationUnit compUnit = getCompilationUnit(templateModel.getSourceCodePath());
		AST ast = compUnit.getAST();
		PackageDeclaration packageDeclaration = compUnit.getPackage();
		String oldPackage = packageDeclaration.getName().toString();
		packageDeclaration.setName(ast.newSimpleName(pakcagePre+oldPackage));

		List typeList = compUnit.types();
		for(Object o:typeList){
			if(o instanceof TypeDeclaration){
				TypeDeclaration td = (TypeDeclaration) o;
				td.getName().setIdentifier("Test");
			}
		}
		String javaCode = compUnit.toString();
				
		javaCode = javaCode.replace("packagePre", "${packagePre}");

		Writer out = null;
		BufferedWriter bw = null;
		File writeFile = new File(templateModel.getOutputPath());
		createParentDirectory(writeFile);
		try {
			out = new FileWriter(writeFile);
			bw = new BufferedWriter(out);
			bw.write(javaCode);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(bw != null){
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	
	private void createParentDirectory(File writeFile) {
		// TODO Auto-generated method stub
		File parent = writeFile.getParentFile();
		if(!parent.exists()){
			parent.mkdir();
		}
	}

	/**
    * get compilation unit of source code
    * @param javaFilePath 
    * @return CompilationUnit
    */
    public static CompilationUnit getCompilationUnit(String javaFilePath){
        byte[] input = getInput(javaFilePath);
        
		ASTParser astParser = ASTParser.newParser(AST.JLS8);
        astParser.setSource(new String(input).toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
        
        return result;
    }

	/**
	 * Gets the input.
	 *
	 * @author mqfdy
	 * @param javaFilePath
	 *            the java file path
	 * @return the input
	 * @Date 2019-6-10 15:22:52
	 */
	private static byte[] getInput(String javaFilePath) {
		byte[] input = null;
		BufferedInputStream bufferedInputStream = null;
		try {
		    bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
		    input = new byte[bufferedInputStream.available()];
		    bufferedInputStream.read(input);
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(bufferedInputStream != null){
					bufferedInputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return input;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CompilationUnit compUnit = getCompilationUnit("F:\\mqf\\work\\codegenerator\\template.designer\\src\\main\\java\\com\\mqfdy\\code\\template\\designer\\model\\SceneTemplateModel.java");
		String javaCode = compUnit.toString();
		System.out.println(javaCode);

	}

	

}
