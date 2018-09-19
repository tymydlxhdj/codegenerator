package com.mqfdy.code.springboot.core.datasource;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;

// TODO: Auto-generated Javadoc
/**
 * There may be different ways to create/obtain the contents of a sample project. This class is meant to
 * hide the details from client code that needs to allow users to select some sample project and use it.
 * 
 * @author mqfdy
 */
public abstract class SampleProject {
	
	/**
	 * location target directory.
	 *
	 * @author mqfdy
	 * @param location
	 *            the location
	 * @param connectName
	 *            the connect name
	 * @throws CoreException
	 *             the core exception
	 * @Date 2018-09-03 09:00
	 */
	public abstract void createAt(File location,String connectName) throws CoreException;

	/**
	 * Gets the default project location.
	 *
	 * @author mqfdy
	 * @return the default project location
	 * @Date 2018-09-03 09:00
	 */
	public static String getDefaultProjectLocation() {
		return Platform.getLocation().toOSString();
	}

	/**
	 * Gets the name.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public abstract String getName();
	
}
