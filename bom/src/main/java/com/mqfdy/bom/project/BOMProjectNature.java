package com.mqfdy.bom.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class BOMProjectNature.
 *
 * @author mqfdy
 */
public class BOMProjectNature implements IProjectNature {  
	
	/** The Constant NATURE_ID. */
	public static final String NATURE_ID = "com.mqfdy.bom.project.nature";
	
    /** The project. */
    private IProject project;  
      
    /**
     * @throws CoreException
     */
    public void configure() throws CoreException {  
    }  
  
    /**
     * @throws CoreException
     */
    public void deconfigure() throws CoreException {  
    }  
  
    /**
     * @return
     */
    public IProject getProject() {  
        return project;  
    }  
  
    /**
     * @param project
     */
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
			Activator.log(e);
		}
		return false;
	}
} 