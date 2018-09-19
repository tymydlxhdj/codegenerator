package com.mqfdy.code.springboot.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


// TODO: Auto-generated Javadoc
/**
 * The Class IOUtils.
 *
 * @author mqfdy
 */
public class IOUtils {
	
	/**
	 * 读取文件内容.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @param content
	 *            the content
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
    public static String read(File file, String content) {
        BufferedReader br = null;
        String line = null;
        StringBuffer buf = new StringBuffer();
        
        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(file));
            
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
            	buf.append(line);
            }
            
            if (buf.length() == 0) {
            	buf.append("include ");
            	
            } else {
            	buf.append(",");
            }
            buf.append(content);
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
	 * 将内容回写到文件中.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @param content
	 *            the content
	 * @Date 2018-09-03 09:00
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
