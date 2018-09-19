package com.mqfdy.code.springboot.core.datasource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.core.runtime.Assert;

import com.mqfdy.code.springboot.core.util.ZipFileUtil;


/**
 * A sample project the contents of which is copied from a location in the local file
 * system.
 * 
 * @author zjing
 */
public class LocalSample extends SampleProject {

	private String name;
	private File copyFrom;

	/**
	 * Instantiates a new local sample.
	 *
	 * @param name
	 *            the name
	 * @param copyFrom
	 *            the copy from
	 */
	public LocalSample(String name, File copyFrom) {
		Assert.isNotNull(name);
		Assert.isNotNull(copyFrom);
		this.name = name;
		this.copyFrom = copyFrom;
	}
	/**
	 * 向导从模板中拷贝后生成代码（入口）
	 */
	@Override
	public void createAt(File location,String connectName) {
		try {//将模板项目解压拷贝到向导第一页中的LOCATION位置
			ZipFileUtil.unzip(copyFrom.toURI().toURL(), location, getName(),connectName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return name.replace(".zip", "");
	}

}
