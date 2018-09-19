package com.mqfdy.code.designer.editor.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


// TODO: Auto-generated Javadoc
/**
 * The Class ShareModelActionHttpclient.
 *
 * @author mqfdy
 */
public class ShareModelActionHttpclient {
	
	/** The Constant FAILCONNECTION. */
	public static final String FAILCONNECTION="无法连接到共享模型库,请检查IP和端口是否配置正确或服务是否启动!";
	
	/**
	 * Gets the do post response data by URL.
	 *
	 * @author mqfdy
	 * @param url
	 *            the url
	 * @param params
	 *            the params
	 * @param charset
	 *            the charset
	 * @param pretty
	 *            the pretty
	 * @return the do post response data by URL
	 * @Date 2018-09-03 09:00
	 */
	/*public static void main(String[] args) throws HttpException, IOException{
		// TODO Auto-generated method stub
		String url = "http://localhost:8082/share_model/CreateTreeServlet";
	     
		getDoPostResponseDataByURL(url,  null ,  "utf-8" ,  true );
		
		//System.out.println("111====="+getDoPostResponseDataByURL(url,  null ,  "utf-8" ,  true ));
	}
	*/
	public   static  String getDoPostResponseDataByURL(String url,   
            Map<String, String> params, String charset,  boolean  pretty) { 
	  		
	  		StringBuffer response =  new  StringBuffer();   
      
	        HttpClient client =  new  HttpClient();
	        PostMethod method =  new  PostMethod(url); 
	        //设置Http Post数据    
	        if  (params !=  null ) {   
	             for  (Map.Entry<String, String> entry : params.entrySet()) {  
	            	 try {
				//		method.addParameter(entry.getKey(), new String(entry.getValue().getBytes("ISO-8859-1"),"UTF-8"));
	            		 method.addParameter(entry.getKey(), entry.getValue());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
	            }   
	        }  
	        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
	        BufferedReader reader = null;
	        InputStreamReader isr = null;
	        InputStream is = null;
	        try  { 
	            client.executeMethod(method);   
	             if  (method.getStatusCode() == HttpStatus.SC_OK) {   
	                 //读取为 InputStream，在网页内容数据量大时候推荐使用   
	            	is = method.getResponseBodyAsStream();
	            	isr =  new  InputStreamReader(is, charset);
	                reader =  new  BufferedReader(isr);   
	                String line;   
	                 while  ((line = reader.readLine()) !=  null ) {   
	                     if  (pretty)   
	                        response.append(line).append(System.getProperty( "line.separator" ));   
	                     else   
	                        response.append(line);   
	                }   
	                reader.close();   
	            }  else {
	            	return FAILCONNECTION;
	            } 
	        }  catch  (IOException e) {   
	        	return FAILCONNECTION;
	        }  finally  {   
	        	if(reader != null){
	        		try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        	if(isr != null){
	        		try {
	        			isr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        	if(is != null){
	        		try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	            method.releaseConnection();  
	        }   
	
	         return  response.toString();  
	         
	    } 

}
