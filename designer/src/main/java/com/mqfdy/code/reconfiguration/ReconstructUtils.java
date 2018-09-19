package com.mqfdy.code.reconfiguration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.model.BusinessClass;

// TODO: Auto-generated Javadoc
/**
 * 重构工具类.
 *
 * @author mqfdy
 */
@Deprecated
public class ReconstructUtils {
	
	/** The Constant EOL. */
	private static final String EOL = "\r\n";// windows操作系统换行

	/** The Constant INTERFACE. */
	public static final String INTERFACE = "interface";
	
	/** The Constant CLASS. */
	public static final String CLASS = "class";

	/**
	 * 判断该文件的类型（class、interface、enum）.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @return the file type
	 * @Date 2018-09-03 09:00
	 */
	public static String getFileType(File file) {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));

			byte[] buff = new byte[(int) file.length()];
			bis.read(buff);
			String text = new String(buff);
			String[] lines = text.split(EOL);
			for (String line : lines) {
				if (!line.startsWith("/*") && !line.startsWith("*")
						&& !line.startsWith("//")) {
					if (line.contains(INTERFACE)) {
						return INTERFACE;
					} else if (line.contains(CLASS)) {
						return CLASS;
					}
				}
			}

		} catch (FileNotFoundException e) {
			Logger.log(e);
		} catch (IOException e) {
			Logger.log(e);
		} finally {
			try {
				if(bis!=null){
					bis.close();
				}
			} catch (IOException e) {
				Logger.log(e);
			}
		}
		return null;
	}

	/**
	 * 逐行查找替换内容.
	 *
	 * @author mqfdy
	 * @param resource
	 *            文件
	 * @param oldValue
	 *            原值
	 * @param newValue
	 *            新值
	 * @param mark
	 *            标示
	 * @param num
	 *            the num
	 * @param redirect
	 *            the redirect
	 * @Date 2018-09-03 09:00
	 */
	public static void replaceFileLine(File resource, String oldValue,
			String newValue, String mark, int num, String redirect) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(resource));
			byte[] buff = new byte[(int) resource.length()];
			bis.read(buff);

			bos = new BufferedOutputStream(new FileOutputStream(resource));
			String text = new String(buff);

			if (!text.contains(oldValue)) {// 如果该文本中不存在
				bos.write(text.getBytes());
			} else {
				String[] lines = text.split("\n");
				for (int i = 0; i < lines.length; i++) {
					if (lines[i].contains(mark)) {
						if (Marks.Redirect.UPPER.equals(redirect)) {// 在标示行的上面
							lines[i - num] = lines[i - num].replace(oldValue,
									newValue);
						} else if (Marks.Redirect.NOW.equals(redirect)) {// 在标示行中
							lines[i] = lines[i].replace(oldValue, newValue);
						} else if (Marks.Redirect.LOWER.equals(redirect)) {// 在标示行下面一行
							lines[i + num] = lines[i + num].replace(oldValue,
									newValue);
						}
					}
				}
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < lines.length; i++) {
					sb.append(lines[i]).append(EOL);
				}
				bos.write(sb.toString().getBytes());
			}

		} catch (FileNotFoundException e) {
			Logger.log(e);
		} catch (IOException e) {
			Logger.log(e);
		} finally {
			try {
				if(bis!=null)
					bis.close();
			} catch (IOException e) {
				Logger.log(e);
			}
			try {
				if(bos!=null)
					bos.close();
			} catch (IOException e) {
				Logger.log(e);
			}
		}
	}

	/**
	 * 全替换文件内容.
	 *
	 * @author mqfdy
	 * @param resource
	 *            the resource
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @param modifySelf
	 *            文件是否须要更名
	 * @Date 2018-09-03 09:00
	 */
	public static void replaceFileAll(File resource, String oldValue,
			String newValue, boolean modifySelf) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileInputStream = new FileInputStream(resource);
			bis = new BufferedInputStream(fileInputStream);
			byte[] buff = new byte[(int) resource.length()];
			bis.read(buff);

			fileOutputStream = new FileOutputStream(resource);
			bos = new BufferedOutputStream(fileOutputStream);
			String text = new String(buff);
			text = text.replaceAll(oldValue, newValue);
			bos.write(text.getBytes());

			// 自身的java文件改名（特殊）
			if (modifySelf) {
				resource.renameTo(new File((resource.getPath()).replace(
						oldValue, newValue)));
			}
		} catch (FileNotFoundException e) {
			Logger.log(e);
		} catch (IOException e) {
			Logger.log(e);
		} finally {
			if(fileInputStream!=null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fileOutputStream != null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(bis!=null){
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fileOutputStream != null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 克隆业务实体.
	 *
	 * @author mqfdy
	 * @param bc
	 *            被克隆的业务实体对象
	 * @return the business class
	 * @Date 2018-09-03 09:00
	 */
	public static BusinessClass cloneBc(BusinessClass bc) {
		BusinessClass newBc = new BusinessClass();
		newBc.setBelongPackage(bc.getBelongPackage());
		newBc.setId(bc.getId());
		newBc.setName(bc.getName());
		newBc.setDisplayName(bc.getDisplayName());
		newBc.setOrderNum(bc.getOrderNum());
		newBc.setRemark(bc.getRemark());
		newBc.setStatuses(bc.getStatuses());
		newBc.setStereotype(bc.getStereotype());
		newBc.setTableName(bc.getTableName());
		newBc.setVersionInfo(bc.getVersionInfo());

		return newBc;
	}
}
