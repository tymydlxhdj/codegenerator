package com.mqfdy.code.generator.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class IOUtils {
	
	public static boolean isExistGradle = false;
	
	public static void updateGradle(File file, String readContent) throws IOException {
		write(file, readContent);
    }
	
	public static void copyLib(File libDir, String libName, File destDir) throws IOException {
		if (libDir != null && libDir.exists() && libDir.isDirectory()) {
			for (File srcFile : libDir.listFiles()) {
				if (srcFile.getName().startsWith(libName)) {
					FileUtils.copyFileToDirectory(srcFile, destDir);
				}
			}
		}
    }
	
	/**
     * 读取文件内容
     * 
     * @param filePath
     * @return
     */
    public static String read(File file, String sign, String content) {
        BufferedReader br = null;
        String line = null;
        StringBuffer buf = new StringBuffer();
        
        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(file));
            
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                if (line.endsWith(content)) {
                	isExistGradle = true;
                	buf.delete(0, buf.length());
                	break;
                }
            	
            	// 此处根据实际需要修改某些行的内容
                if (line.startsWith(sign)) {
                    buf.append(line).append(System.getProperty(
                    		"line.separator")).append("\t").append(content);
                } else {
                    buf.append(line);
                }
                buf.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        return buf.toString();
    }
    
    /**
     * 将内容回写到文件中
     * 
     * @param filePath
     * @param content
     */
    public static void write(File file, String content) {
        BufferedWriter bw = null;
        
        try {
            // 根据文件路径创建缓冲输出流
            bw = new BufferedWriter(new FileWriter(file));
            // 将内容写入文件中
            bw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }
}
