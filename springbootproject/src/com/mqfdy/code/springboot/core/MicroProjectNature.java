package com.mqfdy.code.springboot.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroProjectNature.
 *
 * @author mqfdy
 */
public class MicroProjectNature implements IProjectNature {  
	
	/** The Constant NATURE_ID. */
	public static final String NATURE_ID = "com.mqfdy.springboot.nature";
	
	/** The Constant GRADLE_ID. */
	public static final String GRADLE_ID = "org.springsource.ide.eclipse.gradle.core.nature";
	
	/** The Constant MAVEN_ID. */
	public static final String MAVEN_ID = "org.springsource.ide.eclipse.gradle.core.nature";
	
    private IProject project;  
      
    public void configure() throws CoreException {  
    }  
  
    @Override  
    public void deconfigure() throws CoreException {  
    }  
  
    @Override  
    public IProject getProject() {  
        return project;  
    }  
  
    @Override  
    public void setProject(IProject project) {  
        this.project = project;  
    }
    
	/**
	 * Checks for nature.
	 *
	 * @author mqfdy
	 * @param p
	 *            the p
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean hasNature(IProject p) {
		try {
			return p!=null && p.isAccessible() && p.hasNature(NATURE_ID);
		} catch (CoreException e) {
			MicroProjectPlugin.log(e);
		}
		return false;
	}
} 