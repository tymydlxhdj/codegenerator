package com.mqfdy.code.springboot.core.datasource;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;

/**
 * There may be different ways to create/obtain the contents of a sample project. This class is meant to
 * hide the details from client code that needs to allow users to select some sample project and use it.
 * 
 * @author mqfdy
 */
public abstract class SampleProject {
	
	/**
	 * location target directory
	 */
	public abstract void createAt(File location,String connectName) throws CoreException;

	public static String getDefaultProjectLocation() {
		return Platform.getLocation().toOSString();
	}

	public abstract String getName();
	
}
